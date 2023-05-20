package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.db.ACHCompany;
import com.ffusion.ffs.bpw.db.ACHFile;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFileHist;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSStringTokenizer;
import com.ffusion.ffs.util.FFSUtil;

public class ACHFileProcessor
  implements DBConsts, FFSConst, OFXConsts, BPWResource, ACHConsts
{
  private PropertyConfig at = null;
  private int as = 1;
  
  public ACHFileInfo addACHFile(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    int i = 0;
    int j = 0;
    String str1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = "ACHFileProcessor.addACHFile: ";
    FFSDebug.log(str2 + "start", 6);
    if (paramACHFileInfo == null)
    {
      paramACHFileInfo = new ACHFileInfo();
      paramACHFileInfo.setStatusCode(16000);
      str3 = "ACHFileInfo object  is null";
      paramACHFileInfo.setStatusMsg(str3);
      FFSDebug.log(str2 + str3, 0);
      return paramACHFileInfo;
    }
    if (paramACHFileInfo.getFileContent() == null)
    {
      paramACHFileInfo.setStatusCode(16000);
      str3 = "File Content  is null";
      paramACHFileInfo.setStatusMsg(str3);
      FFSDebug.log(str2 + str3, 0);
      return paramACHFileInfo;
    }
    if (!a(paramACHFileInfo)) {
      return paramACHFileInfo;
    }
    if (!checkFileAuditLogInfo(paramACHFileInfo))
    {
      FFSDebug.log(str2 + ": " + paramACHFileInfo.getStatusMsg());
      return paramACHFileInfo;
    }
    if ((paramACHFileInfo.getFileSize() > 0L) && (paramACHFileInfo.getFilePageSize() > 0L) && (paramACHFileInfo.getFileSize() > paramACHFileInfo.getFilePageSize())) {
      j = 1;
    }
    if (paramACHFileInfo.getFileCursor() == 0L) {
      i = 1;
    }
    if ((j != 0) && (i == 0) && (paramACHFileInfo.getFileId() == null))
    {
      paramACHFileInfo.setStatusCode(22890);
      paramACHFileInfo.setStatusMsg("Error, file id is missing in the ACHFileInfo");
      return paramACHFileInfo;
    }
    if ((i != 0) && (paramACHFileInfo.getFileId() != null))
    {
      paramACHFileInfo.setStatusCode(23090);
      paramACHFileInfo.setStatusMsg("First Time, File Id should be Null");
      return paramACHFileInfo;
    }
    if (i != 0)
    {
      str1 = ACHFile.generateUniqueId();
      paramACHFileInfo.setFileId(str1);
      FFSDebug.log("Generated achFile.FileId =" + str1, 6);
    }
    str1 = paramACHFileInfo.getFileId();
    String str3 = paramACHFileInfo.getFileContent();
    FFSStringTokenizer localFFSStringTokenizer = null;
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if ((i != 0) && (paramACHFileInfo.getFileHeader() == null))
    {
      if (FFSStringTokenizer.hasDelimeter(str3, ACHConsts.REC_DELIMS)) {
        localFFSStringTokenizer = new FFSStringTokenizer(str3, ACHConsts.REC_DELIMS);
      } else {
        localFFSStringTokenizer = new FFSStringTokenizer(str3, 94);
      }
      localObject1 = localFFSStringTokenizer.nextToken();
      if (ACHAdapterUtil.allowsMinimumHeaderControl())
      {
        if (((String)localObject1).length() < 40)
        {
          paramACHFileInfo.setStatusCode(26024);
          paramACHFileInfo.setStatusMsg("ACH file header is too short");
          return paramACHFileInfo;
        }
        if (((String)localObject1).length() < 94) {
          localObject1 = ACHAgent.padRecord((String)localObject1);
        }
      }
      localObject2 = new ACHRecordInfo();
      ((ACHRecordInfo)localObject2).setRecordContent((String)localObject1);
      localObject3 = localFFSStringTokenizer.nextToken();
      localFFSStringTokenizer = null;
      String str4 = ACHAgent.getStdEntryClassCode((String)localObject3);
      if (str4 == null)
      {
        paramACHFileInfo.setStatusCode(16000);
        paramACHFileInfo.setStatusMsg("Class Code  is null");
        return paramACHFileInfo;
      }
      ((ACHRecordInfo)localObject2).setClassCode(str4);
      ACHAgent.parse((ACHRecordInfo)localObject2);
      paramACHFileInfo.setFileHeader((ACHRecordInfo)localObject2);
      if (!str4.equalsIgnoreCase("ADV")) {
        paramACHFileInfo.setFileHeaderType("NONADV");
      } else {
        paramACHFileInfo.setFileHeaderType("ADV");
      }
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (j == 0)
      {
        jdMethod_if(paramACHFileInfo);
        if (paramACHFileInfo.getStatusCode() != 0)
        {
          localObject1 = paramACHFileInfo;
          return localObject1;
        }
        ACHFile.addACHFile(paramACHFileInfo, localFFSConnectionHolder);
        if (paramACHFileInfo.getStatusCode() != 0)
        {
          localFFSConnectionHolder.conn.rollback();
          localObject1 = paramACHFileInfo;
          return localObject1;
        }
        ACHFile.addACHFlatFile(paramACHFileInfo, localFFSConnectionHolder);
        a(paramACHFileInfo, localFFSConnectionHolder);
        a(localFFSConnectionHolder, paramACHFileInfo, true);
      }
      else
      {
        if (i != 0)
        {
          ACHFile.addACHFile(paramACHFileInfo, localFFSConnectionHolder);
          if (paramACHFileInfo.getStatusCode() != 0)
          {
            localFFSConnectionHolder.conn.rollback();
            localObject1 = paramACHFileInfo;
            return localObject1;
          }
        }
        ACHFile.addACHFlatFile(paramACHFileInfo, localFFSConnectionHolder);
        if (paramACHFileInfo.getStatusCode() != 0)
        {
          localFFSConnectionHolder.conn.rollback();
          localObject1 = paramACHFileInfo;
          return localObject1;
        }
        long l = paramACHFileInfo.getFileCursor() + paramACHFileInfo.getFilePageSize();
        if (l > paramACHFileInfo.getFileSize()) {
          l = paramACHFileInfo.getFileSize();
        }
        paramACHFileInfo.setFileCursor(l);
        if (paramACHFileInfo.getFileCursor() == paramACHFileInfo.getFileSize())
        {
          jdMethod_if(paramACHFileInfo);
          ACHFile.updateACHFile(paramACHFileInfo, localFFSConnectionHolder);
          if (paramACHFileInfo.getStatusCode() != 0)
          {
            localFFSConnectionHolder.conn.rollback();
            localObject3 = paramACHFileInfo;
            return localObject3;
          }
          a(paramACHFileInfo, localFFSConnectionHolder);
          a(localFFSConnectionHolder, paramACHFileInfo, false);
        }
      }
      if (paramACHFileInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        ACHFileInfo localACHFileInfo = paramACHFileInfo;
        return localACHFileInfo;
      }
      logTransAuditLog(localFFSConnectionHolder, paramACHFileInfo, "Add a new ACHFile ChunkID: " + paramACHFileInfo.getChunkId() + ", File size: " + paramACHFileInfo.getFileSize() + ", Memo: " + paramACHFileInfo.getMemo(), 4221);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log(str2 + "failed. " + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "*** ACHFileProcessor.addACHFile failed: ";
      localObject3 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject3, 0);
      throw new FFSException(localException, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      localFFSConnectionHolder = null;
    }
    return paramACHFileInfo;
  }
  
  public ACHFileInfo canACHFile(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    if ((paramACHFileInfo.getFileId() == null) && (paramACHFileInfo.getFileHeader() == null))
    {
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("FileId and Header  is null");
      return paramACHFileInfo;
    }
    if (!checkFileAuditLogInfo(paramACHFileInfo))
    {
      FFSDebug.log("ACHFileProcessor.canACHFile: " + paramACHFileInfo.getStatusMsg(), 0);
      return paramACHFileInfo;
    }
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      String str1 = paramACHFileInfo.getFileId();
      if (str1 == null) {
        str1 = paramACHFileInfo.getFileHeader().getFileId();
      }
      if (str1 == null)
      {
        paramACHFileInfo = ACHFile.findFileByHeader(paramACHFileInfo, localFFSConnectionHolder);
        if (paramACHFileInfo.getFileId() == null)
        {
          paramACHFileInfo.setStatusCode(16000);
          paramACHFileInfo.setStatusMsg("File Id  is null");
          FFSDebug.log("*** ACHFileProcessor.canACHFile failed:  File id is Null ", 0);
          localFFSConnectionHolder.conn.commit();
          ACHFileInfo localACHFileInfo1 = paramACHFileInfo;
          return localACHFileInfo1;
        }
      }
      boolean bool = jdMethod_if(localFFSConnectionHolder, paramACHFileInfo);
      if (!bool)
      {
        paramACHFileInfo.setStatusCode(17204);
        str3 = "Can't cancel ACHFile. It doesn't exist, or has been processed, or is being processed, FileId :" + paramACHFileInfo.getFileId();
        paramACHFileInfo.setStatusMsg(str3);
        FFSDebug.log("*** ACHFileProcessor.canACHFile " + str3, 0);
        localFFSConnectionHolder.conn.rollback();
        ACHFileInfo localACHFileInfo2 = paramACHFileInfo;
        return localACHFileInfo2;
      }
      ACHFile.updateFileStatus(paramACHFileInfo, "CANCELEDON", localFFSConnectionHolder);
      logTransAuditLog(localFFSConnectionHolder, paramACHFileInfo, "Successfully cancel an ACH file", 4223);
      deleteFileSchedule(paramACHFileInfo.getFileId(), localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** ACHFileProcessor.canACHFile failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      try
      {
        a(localFFSConnectionHolder, paramACHFileInfo);
      }
      catch (Exception localException2)
      {
        localFFSConnectionHolder.conn.rollback();
        String str4 = "*** ACHFileProcessor.canACHFile failed: ";
        String str5 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str5, 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      localFFSConnectionHolder = null;
    }
    return paramACHFileInfo;
  }
  
  public ACHFileInfo getACHFile(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramACHFileInfo = ACHFile.getACHFlatFileInfo(paramACHFileInfo, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "*** ACHFileProcessor.getACHFile failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      localFFSConnectionHolder = null;
    }
    return paramACHFileInfo;
  }
  
  public ACHFileHist getACHFileHistory(ACHFileHist paramACHFileHist)
    throws FFSException
  {
    Object localObject1 = null;
    Object localObject2 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramACHFileHist == null)
    {
      paramACHFileHist = new ACHFileHist();
      paramACHFileHist.setStatusCode(16000);
      String str1 = "ACHFileHist object  is null";
      FFSDebug.log("ACHFileProcessor.getACHFileHistory, " + str1, 0);
      paramACHFileHist.setStatusMsg(str1);
      return paramACHFileHist;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramACHFileHist = ACHFile.getACHFileHistory(paramACHFileHist, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** ACHFileProcessor.getACHFileHistory failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
      localFFSConnectionHolder = null;
    }
    return paramACHFileHist;
  }
  
  private void a(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramACHFileInfo.getFiId();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramACHFileInfo.getDtProcessed());
    localScheduleInfo.LogID = paramACHFileInfo.getLogId();
    localScheduleInfo.InstanceCount = 1;
    try
    {
      String str1 = ScheduleInfo.createSchedule(paramFFSConnectionHolder, "ACHFILETRN", paramACHFileInfo.getFileId(), localScheduleInfo);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFileProcessor.createSchedule failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  private static boolean a(ACHFileInfo paramACHFileInfo)
  {
    boolean bool = true;
    String str1 = paramACHFileInfo.getDueDate();
    String str2;
    if (!BPWUtil.checkDateBeanFormat(str1))
    {
      paramACHFileInfo.setStatusCode(17203);
      str2 = "Invalid Due Date  :" + str1 + ", Format should be yyyymmdd";
      paramACHFileInfo.setStatusMsg(str2);
      FFSDebug.log("ACHFileProcessor.validateFileDueDate, " + str2, 0);
      bool = false;
    }
    else if (FFSUtil.isOlderThanToday(paramACHFileInfo.getDueDate()))
    {
      paramACHFileInfo.setStatusCode(17203);
      str2 = "Invalid Due Date : Due date = " + str1 + " is older than today !";
      paramACHFileInfo.setStatusMsg(str2);
      FFSDebug.log("ACHFileProcessor.validateFileDueDate, " + str2, 0);
      bool = false;
    }
    else
    {
      try
      {
        paramACHFileInfo.setDtProcessed(DBUtil.getACHPayday(paramACHFileInfo.getFiId(), str1));
        FFSDebug.log("ACHFileProcessor.validateFileDueDate,  DtProcessed is being set as = " + paramACHFileInfo.getDtProcessed(), 6);
      }
      catch (Exception localException)
      {
        paramACHFileInfo.setStatusCode(17203);
        String str3 = "Invalid Due Date :" + str1 + ". Exception: " + localException.getMessage();
        paramACHFileInfo.setStatusMsg(str3);
        FFSDebug.log("ACHFileProcessor.validateFileDueDate, " + str3, 0);
        bool = false;
      }
    }
    return bool;
  }
  
  private boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    return ScheduleInfo.startModifyingSingleSchedule(paramFFSConnectionHolder, "ACHFILETRN", paramACHFileInfo.getFileId(), paramACHFileInfo.getFiId());
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    ScheduleInfo.endModifyingSingleSchedule(paramFFSConnectionHolder, "ACHFILETRN", paramACHFileInfo.getFileId(), paramACHFileInfo.getFiId());
  }
  
  private void jdMethod_if(ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    String str1 = paramACHFileInfo.getFileContent();
    int i = paramACHFileInfo.getFileContent().indexOf("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
    if (i != -1)
    {
      if ((i % 94 != 0) && (!jdMethod_do(str1))) {
        i += 94 - i % 94;
      }
      str1 = str1.substring(0, i);
    }
    str1 = ACHAgent.trimNewLines(str1);
    if (str1.length() > 0)
    {
      int j = str1.lastIndexOf(ACHConsts.REC_DELIMS);
      String str2 = ACHAgent.trimNewLines(str1.substring(j + 1));
      if (ACHAdapterUtil.allowsMinimumHeaderControl())
      {
        if (str2.length() < 55)
        {
          paramACHFileInfo.setStatusCode(26025);
          paramACHFileInfo.setStatusMsg("ACH file control is too short");
          return;
        }
        if (str2.length() < 94) {
          str2 = ACHAgent.padRecord(str2);
        }
      }
      ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
      localACHRecordInfo.setRecordContent(str2);
      ACHAgent.parse(localACHRecordInfo);
      paramACHFileInfo.setFileControl(localACHRecordInfo);
      paramACHFileInfo.setStatusCode(0);
    }
  }
  
  public static void deleteFileSchedule(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.deleteFileSchedule: start, fileId =" + paramString, 6);
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, "ACHFILETRN", paramString);
    FFSDebug.log("ACHFile.deleteFileSchedule: done ", 6);
  }
  
  private static boolean jdMethod_do(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    return (paramString.indexOf("\n") != -1) || (paramString.indexOf("\r") != -1);
  }
  
  public static boolean checkFileAuditLogInfo(ACHFileInfo paramACHFileInfo)
  {
    String str1 = paramACHFileInfo.getLogId();
    if (str1 == null)
    {
      paramACHFileInfo.setStatusCode(23870);
      paramACHFileInfo.setStatusMsg("LogID field is null");
      return false;
    }
    if (str1.length() > 32)
    {
      paramACHFileInfo.setStatusCode(23880);
      paramACHFileInfo.setStatusMsg("LogID field is too long: " + str1);
      return false;
    }
    String str2 = paramACHFileInfo.getSubmittedBy();
    if (str2 == null)
    {
      paramACHFileInfo.setStatusCode(23890);
      paramACHFileInfo.setStatusMsg("SubmittedBy field is null");
      return false;
    }
    if (str2.length() > 32)
    {
      paramACHFileInfo.setStatusCode(23900);
      paramACHFileInfo.setStatusMsg("SubmittedBy field is too long: " + str2);
      return false;
    }
    return true;
  }
  
  public void logTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, String paramString, int paramInt)
    throws FFSException
  {
    if (this.as >= 3) {
      ACHFile.doTransAuditLog(paramFFSConnectionHolder, paramACHFileInfo, paramACHFileInfo.getSubmittedBy(), paramString, paramInt);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, boolean paramBoolean)
    throws FFSException
  {
    String str = "ACHFileProcessor.addFileBatchAndEntry: ";
    FFSDebug.log(str + "start, fileId =" + paramACHFileInfo.getFileId() + ", hasFullContent =" + paramBoolean, 6);
    ACHFileInfo localACHFileInfo = new ACHFileInfo();
    if (!paramBoolean)
    {
      localACHFileInfo.setFileId(paramACHFileInfo.getFileId());
      localACHFileInfo.setFileCursor(0L);
      localACHFileInfo.setFilePageSize(0L);
      localACHFileInfo = ACHFile.getACHFlatFileInfo(localACHFileInfo, paramFFSConnectionHolder);
    }
    else
    {
      localACHFileInfo.setFileContent(paramACHFileInfo.getFileContent());
    }
    ACHAgent.parse(localACHFileInfo);
    localACHFileInfo.setFiId(paramACHFileInfo.getFiId());
    if (!validateBatchesEffectiveDates(paramFFSConnectionHolder, localACHFileInfo))
    {
      FFSDebug.log(str + localACHFileInfo.getStatusMsg());
      paramACHFileInfo.setStatusCode(localACHFileInfo.getStatusCode());
      paramACHFileInfo.setStatusMsg(localACHFileInfo.getStatusMsg());
      return;
    }
    ACHFile.addFileBatchAndEntry(paramFFSConnectionHolder, localACHFileInfo, paramACHFileInfo.getFileId());
    localACHFileInfo = null;
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    FFSDebug.log(str + "end", 6);
  }
  
  public boolean validateBatchesEffectiveDates(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramACHFileInfo.getFiId());
    if (localBPWFIInfo.getStatusCode() != 0)
    {
      paramACHFileInfo.setStatusCode(localBPWFIInfo.getStatusCode());
      paramACHFileInfo.setStatusMsg(localBPWFIInfo.getStatusMsg());
      return false;
    }
    ACHBatchInfo[] arrayOfACHBatchInfo = paramACHFileInfo.getBatches();
    if ((arrayOfACHBatchInfo != null) && (arrayOfACHBatchInfo.length > 0))
    {
      int i = arrayOfACHBatchInfo.length;
      for (int j = 0; j < i; j++)
      {
        ACHBatchInfo localACHBatchInfo = arrayOfACHBatchInfo[j];
        if (localACHBatchInfo.getFiId() == null) {
          localACHBatchInfo.setFiId(paramACHFileInfo.getFiId());
        }
        String str;
        if (localACHBatchInfo.getCompId() == null)
        {
          str = localACHBatchInfo.getBatchHeaderFieldValueString(5);
          localObject = ACHCompany.getACHCompanyByACHId(str, paramFFSConnectionHolder);
          if (((ACHCompanyInfo)localObject).getStatusCode() != 0)
          {
            paramACHFileInfo.setStatusCode(27009);
            paramACHFileInfo.setStatusMsg("Failed to get ACHCompanyInfo for a batch in ACHFileInfo: " + ((ACHCompanyInfo)localObject).getStatusMsg());
            return false;
          }
          localACHBatchInfo.setCompId(((ACHCompanyInfo)localObject).getCompId());
          localACHBatchInfo.setCompAchId(str);
        }
        if (localACHBatchInfo.getClassCode() == null)
        {
          str = localACHBatchInfo.getBatchHeaderFieldValueString(6);
          localACHBatchInfo.setClassCode(str);
        }
        int k = DBUtil.getNextRunDate(paramFFSConnectionHolder, localACHBatchInfo.getFiId(), "ACHFILETRN");
        Object localObject = new ACHBatchProcessor();
        if (!((ACHBatchProcessor)localObject).validateEffectiveDate(paramFFSConnectionHolder, localACHBatchInfo, localBPWFIInfo, k))
        {
          paramACHFileInfo.setStatusCode(27010);
          paramACHFileInfo.setStatusMsg("Failed to validate Effective Entry Date of a batch in ACHFileInfo: " + localACHBatchInfo.getStatusMsg());
          return false;
        }
      }
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHFileProcessor
 * JD-Core Version:    0.7.0.1
 */