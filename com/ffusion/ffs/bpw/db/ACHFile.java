package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileHist;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSStringTokenizer;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVFileHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileHeaderRecord;
import com.ffusion.util.logging.AuditLogRecord;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import oracle.sql.CLOB;

public class ACHFile
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  private static final String kj = " ORDER BY FileId ASC";
  private static final String ki = " ORDER BY ChunkId ASC ";
  
  public static ACHFileInfo addACHFile(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFile.addACHFile: ACHFileInfo is Null ! ", 0);
      paramACHFileInfo = new ACHFileInfo();
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("ACHFileInfo  is null");
      return paramACHFileInfo;
    }
    FFSDebug.log("ACHFile.addACHFile: start, fileId =" + paramACHFileInfo.getFileId(), 6);
    String str1 = "INSERT INTO ACH_File (FileId, CustomerId, RDFIACHId, ODFIACHId, ReferenceCODE, OrgCreateDate, BPWCreateDate, DueDate,SubmitDate, FileHeaderType, FileStatus, BatchCount,BlockCount,RecordCount, TotalDebit, TotalCredit, LogId, SubmittedBy, Memo,DtProcessed, UploadFileName ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    if (paramACHFileInfo.getFileHeader().getRecord() == null)
    {
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("Header Record Object  is null");
      return paramACHFileInfo;
    }
    checkCustomer(paramACHFileInfo, paramFFSConnectionHolder);
    if (paramACHFileInfo.getStatusCode() != 0) {
      return paramACHFileInfo;
    }
    String str2 = paramACHFileInfo.getFileHeaderFieldValueObject(3);
    String str3 = paramACHFileInfo.getFileHeaderFieldValueObject(5) + paramACHFileInfo.getFileHeaderFieldValueObject(6) + paramACHFileInfo.getFileHeaderFieldValueObject(4);
    if (a(str3, paramACHFileInfo, paramFFSConnectionHolder)) {
      return paramACHFileInfo;
    }
    String str4 = str3;
    int i = BPWUtil.getDateDBFormat(paramACHFileInfo.getDueDate());
    int j = BPWUtil.getDateDBFormat(paramACHFileInfo.getDtProcessed());
    String str5 = paramACHFileInfo.getFileHeaderFieldValueObject(7);
    Integer localInteger1 = null;
    Integer localInteger2 = null;
    Integer localInteger3 = null;
    Long localLong1 = new Long(0L);
    Long localLong2 = new Long(0L);
    String str6 = null;
    if (paramACHFileInfo.getFileControl() != null)
    {
      localInteger1 = (Integer)paramACHFileInfo.getFileControlFieldValueObject(9);
      localInteger2 = (Integer)paramACHFileInfo.getFileControlFieldValueObject(8);
      localInteger3 = (Integer)paramACHFileInfo.getFileControlFieldValueObject(10);
      localLong1 = (Long)paramACHFileInfo.getFileControlFieldValueObject(22);
      localLong2 = (Long)paramACHFileInfo.getFileControlFieldValueObject(23);
      str6 = "WILLPROCESSON";
    }
    else
    {
      str6 = "ACHTEMPORARY";
    }
    paramACHFileInfo.setSubmitDate(FFSUtil.getDateString());
    if ((paramACHFileInfo.getUploadFileName() != null) && (paramACHFileInfo.getUploadFileName().length() > 128)) {
      paramACHFileInfo.setUploadFileName(paramACHFileInfo.getUploadFileName().substring(0, 128));
    }
    Object[] arrayOfObject = { paramACHFileInfo.getFileId(), paramACHFileInfo.getCustomerId(), paramACHFileInfo.getRdfiId(), paramACHFileInfo.getOdfiId(), str2, str3, str4, new Integer(i), paramACHFileInfo.getSubmitDate(), str5, str6, localInteger1, localInteger2, localInteger3, String.valueOf(localLong1), String.valueOf(localLong2), paramACHFileInfo.getLogId(), paramACHFileInfo.getSubmittedBy(), FFSUtil.hashtableToString(paramACHFileInfo.getMemo()), new Integer(j), paramACHFileInfo.getUploadFileName() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str7 = "*** ACHFile.addACHFile : failed: ";
      String str8 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str8, 0);
      throw new FFSException(localException, str7);
    }
    paramACHFileInfo.setFileStatus(str6);
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    FFSDebug.log("ACHFile.addACHFile: done ", 6);
    return paramACHFileInfo;
  }
  
  public static ACHFileInfo addACHFlatFile(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.addACHFlatFile: start, fileId =" + paramACHFileInfo.getFileId(), 6);
    String str1 = "INSERT INTO ACH_FlatFile (FileId, ChunkId, CumChunkSize, FileContent, SubmitDate, FileStatus, FileSize, FileType, LogId) VALUES (?,?,?,?,?,?,?,?,?) ";
    try
    {
      String str2 = paramFFSConnectionHolder.conn.getDatabaseType();
      if (str2.indexOf("ORACLE") != -1)
      {
        jdMethod_do(paramACHFileInfo, paramFFSConnectionHolder);
      }
      else
      {
        localObject = new Object[] { paramACHFileInfo.getFileId(), paramACHFileInfo.getChunkId() != null ? new Integer(paramACHFileInfo.getChunkId()) : new Integer(0), new Long(paramACHFileInfo.getCumulativeChunkSize()), paramACHFileInfo.getFileContent(), paramACHFileInfo.getSubmitDate(), "WILLPROCESSON", paramACHFileInfo.getFileSize() + "", null, paramACHFileInfo.getLogId() };
        DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = "*** ACHFile.addACHFlatFile: failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, (String)localObject);
    }
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    FFSDebug.log("ACHFile.addACHFlatFile: done ", 6);
    return paramACHFileInfo;
  }
  
  public static ACHFileInfo updateACHFile(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.updateACHFile: start, fileId =" + paramACHFileInfo.getFileId(), 6);
    if (paramACHFileInfo.getFileControl() == null)
    {
      paramACHFileInfo.setStatusCode(22670);
      paramACHFileInfo.setStatusMsg("Invalid file, File control is missing:");
      return paramACHFileInfo;
    }
    String str1 = "Update ACH_File set FileStatus = ?, BatchCount = ?, BlockCount = ?, RecordCount = ?, TotalDebit = ?,  TotalCredit = ?  WHERE FileId = ? ";
    Integer localInteger1 = (Integer)paramACHFileInfo.getFileControlFieldValueObject(9);
    Integer localInteger2 = (Integer)paramACHFileInfo.getFileControlFieldValueObject(8);
    Integer localInteger3 = (Integer)paramACHFileInfo.getFileControlFieldValueObject(10);
    Long localLong1 = (Long)paramACHFileInfo.getFileControlFieldValueObject(22);
    Long localLong2 = (Long)paramACHFileInfo.getFileControlFieldValueObject(23);
    String str2 = "WILLPROCESSON";
    Object[] arrayOfObject = { str2, localInteger1, localInteger2, localInteger3, String.valueOf(localLong1), String.valueOf(localLong2), paramACHFileInfo.getFileId() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** ACHFile.updateACHFile: failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    paramACHFileInfo.setFileStatus(str2);
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    FFSDebug.log("ACHFile.updateACHFile: done ", 6);
    return paramACHFileInfo;
  }
  
  public static ACHFileInfo findFileByHeader(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.findFileByHeader: start ", 6);
    String str1 = "SELECT FileId from ACH_File  WHERE BPWCreateDate = ? AND CustomerId = ? AND ODFIACHId = ? AND RDFIACHId = ?";
    FFSResultSet localFFSResultSet = null;
    ACHRecordInfo localACHRecordInfo = paramACHFileInfo.getFileHeader();
    if (localACHRecordInfo.getRecord() == null)
    {
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("MB Object  is null");
      return paramACHFileInfo;
    }
    Object[] arrayOfObject = { paramACHFileInfo.getFileHeaderFieldValueObject(5) + paramACHFileInfo.getFileHeaderFieldValueObject(6) + paramACHFileInfo.getFileHeaderFieldValueObject(4), paramACHFileInfo.getCustomerId(), paramACHFileInfo.getFileHeaderFieldValueObject(2), paramACHFileInfo.getFileHeaderFieldValueObject(1) };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        paramACHFileInfo.setFileId(localFFSResultSet.getColumnString("FileId"));
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHFile.findFileByHeader: failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHFile.findFileByHeader  failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log("ACHFile.findFileByHeader: done fileId =" + paramACHFileInfo.getFileId(), 6);
    return paramACHFileInfo;
  }
  
  public static int deleteACHFile(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.deleteACHFile: start, fileId =" + paramString, 6);
    String str1 = "Delete from ACH_File  WHERE FileId = ? ";
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFile.deleteACHFile: failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log("ACHFile.deleteACHFile: done ", 6);
    return i;
  }
  
  public static int deleteACHFlatFile(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.deleteACHFlatFile: start, fileId =" + paramString, 6);
    String str1 = "Delete from ACH_FlatFile  WHERE FileId = ?";
    Object[] arrayOfObject = { paramString };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFile.deleteACHFlatFile: failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log("ACHFile.deleteACHFlatFile: done ", 6);
    return i;
  }
  
  public static String getFileStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getFileStatus: start, fileId =" + paramString, 6);
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      FFSDebug.log("ACHFile.getFileStatus: FileId is Null ! ", 0);
      return null;
    }
    String str1 = "Select FileStatus from ACH_File  WHERE FileId = ? ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("FileStatus");
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** ACHFile.getFileStatus: failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHFile.getFileStatus  failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log("ACHFile.getFileStatus: done , status =" + str2, 6);
    return str2;
  }
  
  public static ACHFileInfo updateFileStatus(ACHFileInfo paramACHFileInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFile.updateFileStatus: ACHFileInfo is Null ! ", 0);
      paramACHFileInfo = new ACHFileInfo();
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("ACHFileInfo  is null");
      return paramACHFileInfo;
    }
    FFSDebug.log("ACHFile.updateFileStatus: start, fileId =" + paramACHFileInfo.getFileId() + ", Status = " + paramString, 6);
    if (paramString.equals("CANCELEDON"))
    {
      str1 = getFileStatus(paramACHFileInfo.getFileId(), paramFFSConnectionHolder);
      if (str1 == null)
      {
        paramACHFileInfo.setStatusCode(23200);
        paramACHFileInfo.setStatusMsg("The Specified File Id Does Not Exist , ID =" + paramACHFileInfo.getFileId());
        return paramACHFileInfo;
      }
      if (!str1.equalsIgnoreCase("WILLPROCESSON"))
      {
        paramACHFileInfo.setStatusCode(23100);
        paramACHFileInfo.setStatusMsg("Status is Not  WILLPROCESSON,  Cannot Cancel");
        return paramACHFileInfo;
      }
      deleteACHFlatFile(paramACHFileInfo.getFileId(), paramFFSConnectionHolder);
    }
    String str1 = "Update ACH_File set FileStatus = ?  WHERE FileId = ? ";
    Object[] arrayOfObject = { paramString, paramACHFileInfo.getFileId() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFile.updateFileStatus: failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    paramACHFileInfo.setFileStatus(paramString);
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    FFSDebug.log("ACHFile.updateFileStatus: done ", 6);
    return paramACHFileInfo;
  }
  
  public static ACHFileInfo updateFilePostedOn(ACHFileInfo paramACHFileInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFile.updateFilePostedOn: ACHFileInfo is Null ! ", 0);
      paramACHFileInfo = new ACHFileInfo();
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("ACHFileInfo  is null");
      return paramACHFileInfo;
    }
    FFSDebug.log("ACHFile.updateFilePostedOn: start, fileId =" + paramACHFileInfo.getFileId() + ", processId = " + paramString + ", exportFileName = " + paramACHFileInfo.getExportFileName(), 6);
    String str1 = "Update ACH_File set FileStatus = 'POSTEDON' , ProcessId = ? , ExportFileName = ?  WHERE FileId = ? ";
    Object[] arrayOfObject = { paramString, paramACHFileInfo.getExportFileName(), paramACHFileInfo.getFileId() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFile.updateFilePostedOn: failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    paramACHFileInfo.setFileStatus("POSTEDON");
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    FFSDebug.log("ACHFile.updateFilePostedOn: done ", 6);
    return paramACHFileInfo;
  }
  
  public static String generateUniqueId()
    throws FFSException
  {
    try
    {
      return DBUtil.getNextIndexStringWithPadding("FileID", 32, '0');
    }
    catch (Exception localException)
    {
      String str1 = "*** ACHFile.generateUniqueId failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
  }
  
  public static ACHFileInfo getACHFlatFileInfo(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFile.getACHFlatFileInfo: ACHFileInfo is Null ! ", 0);
      paramACHFileInfo = new ACHFileInfo();
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("ACHFileInfo  is null");
      return paramACHFileInfo;
    }
    if (paramACHFileInfo.getFileId() == null)
    {
      FFSDebug.log("ACHFile.getACHFlatFileInfo: File Id is Null ! ", 0);
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("FileId  is null");
      return paramACHFileInfo;
    }
    FFSDebug.log("ACHFile.getACHFlatFileInfo: start, fileId =" + paramACHFileInfo.getFileId(), 6);
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = paramACHFileInfo.getFileCursor();
    long l5 = paramACHFileInfo.getFilePageSize();
    long l6 = l4 + l5;
    try
    {
      if (paramACHFileInfo.getFileCursor() == 0L)
      {
        paramACHFileInfo = jdMethod_for(paramFFSConnectionHolder, paramACHFileInfo);
        if (paramACHFileInfo.getStatusCode() != 0) {
          return paramACHFileInfo;
        }
      }
      Long[] arrayOfLong = jdMethod_byte(paramACHFileInfo.getFileId(), paramFFSConnectionHolder);
      if (arrayOfLong.length == 0)
      {
        paramACHFileInfo.setStatusCode(16020);
        paramACHFileInfo.setStatusMsg(" record not found");
        return paramACHFileInfo;
      }
      boolean bool = false;
      if (l5 == 0L)
      {
        l1 = arrayOfLong[(arrayOfLong.length - 1)].longValue();
        bool = true;
      }
      else
      {
        l1 = a(l4 + 1L, arrayOfLong);
        l2 = a(l6, arrayOfLong);
      }
      if ((l1 == 0L) || (l5 == 0L)) {
        l3 = 0L;
      } else {
        l3 = a(l4, bool, l1, arrayOfLong);
      }
      str2 = a(l1, l2, l5);
      paramACHFileInfo = a(paramACHFileInfo, str2, l3, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      String str1 = "*** ACHFile.getACHFlatFileInfo: failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    FFSDebug.log("ACHFile.getACHFlatFileInfo: done ", 6);
    return paramACHFileInfo;
  }
  
  public static ACHFileHist getACHFileHistory(ACHFileHist paramACHFileHist, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    paramACHFileHist = a(paramACHFileHist, "getACHFileHistory");
    if (paramACHFileHist.getStatusCode() != 0) {
      return paramACHFileHist;
    }
    FFSDebug.log("ACHFile.getACHFileHistory: start, start date =" + paramACHFileHist.getStartDate() + ", end date =" + paramACHFileHist.getEndDate(), 6);
    if (paramACHFileHist.getFileIdList() != null)
    {
      paramACHFileHist = getACHFileHistoryByIds(paramACHFileHist, paramFFSConnectionHolder);
    }
    else
    {
      ArrayList localArrayList = new ArrayList();
      StringBuffer localStringBuffer = new StringBuffer("SELECT FileId, DueDate, DtProcessed, FileStatus  FROM ACH_File  WHERE FileStatus != 'CANCELEDON' AND FileStatus != 'ACHTEMPORARY' ");
      localStringBuffer.append(a(null, paramACHFileHist, localArrayList));
      localStringBuffer.append(" ORDER BY FileId ASC");
      Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
      paramACHFileHist = a(paramACHFileHist, localStringBuffer.toString(), arrayOfObject, paramFFSConnectionHolder);
    }
    return paramACHFileHist;
  }
  
  private static ACHFileInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    return getACHFileInfo(paramFFSConnectionHolder, paramACHFileInfo, true);
  }
  
  public static ACHFileHist getACHFileHistoryByIds(ACHFileHist paramACHFileHist, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getACHFileHistoryByIds start ", 6);
    if (paramACHFileHist == null)
    {
      paramACHFileHist = new ACHFileHist();
      paramACHFileHist.setStatusCode(16000);
      localObject = "ACHFileHist  is null";
      FFSDebug.log("ACHFile.getACHFileHistoryByIds, " + (String)localObject, 0);
      paramACHFileHist.setStatusMsg((String)localObject);
      return paramACHFileHist;
    }
    if (paramACHFileHist.getFileIdList() == null)
    {
      paramACHFileHist.setStatusCode(16000);
      localObject = "FileIdList Array  is null";
      FFSDebug.log("ACHFile.getACHFileHistoryByIds, " + (String)localObject, 0);
      paramACHFileHist.setStatusMsg((String)localObject);
      return paramACHFileHist;
    }
    Object localObject = new ArrayList();
    for (int i = 0; i < paramACHFileHist.getFileIdList().length; i++) {
      ((ArrayList)localObject).add(getACHFileInfo(paramACHFileHist.getFileIdList()[i], paramFFSConnectionHolder));
    }
    if (((ArrayList)localObject).size() == 0)
    {
      paramACHFileHist.setStatusCode(16020);
      paramACHFileHist.setStatusMsg(" record not found");
    }
    else
    {
      paramACHFileHist.setFiles((ACHFileInfo[])((ArrayList)localObject).toArray(new ACHFileInfo[0]));
      paramACHFileHist.setStatusCode(0);
      paramACHFileHist.setStatusMsg("Success");
    }
    FFSDebug.log("ACHFile.getACHFileHistoryByIds done", 6);
    return paramACHFileHist;
  }
  
  public static ACHFileInfo getACHFileInfo(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getACHFileInfo: start.", 6);
    String str1 = paramACHFileInfo.getFileId();
    FFSDebug.log("ACHFile.getACHFileInfo: fileId:" + str1, 6);
    String str2 = "SELECT FileId, CustomerId, ReferenceCODE, RDFIACHId, ODFIACHId,  OrgCreateDate,BPWCreateDate, DueDate, FileHeaderType,SubmitDate, FileStatus, BatchCount,BlockCount,RecordCount,TotalDebit, TotalCredit, LogId, SubmittedBy, Memo, DtProcessed, NumberOfCredits,  NumberOfDebits, ExportFileName, UploadFileName  FROM ACH_File  WHERE FileId = ? AND FileStatus != 'CANCELEDON'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { str1 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramACHFileInfo = a(localFFSResultSet, paramACHFileInfo, paramBoolean);
      }
      else
      {
        paramACHFileInfo.setStatusCode(16020);
        paramACHFileInfo.setStatusMsg("ACHFileInfo  record not found");
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** ACHFile.getACHFileInfo: failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHFile.getACHFileInfo  failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log("ACHFile.getACHFileInfo: done ", 6);
    return paramACHFileInfo;
  }
  
  public static ACHFileInfo getACHFileInfo(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getACHFileInfo: start, fileId =" + paramString, 6);
    ACHFileInfo localACHFileInfo = new ACHFileInfo();
    localACHFileInfo.setFileId(paramString);
    return jdMethod_for(paramFFSConnectionHolder, localACHFileInfo);
  }
  
  public static int updateStatusByODFIACHId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ACHFile.updateStatusByODFIACHId: start, oDFIACHId =" + paramString1 + ", status =" + paramString2, 6);
    if ((paramString1 == null) || (paramString2 == null))
    {
      FFSDebug.log("ACHFile.updateStatusByODFIACHId: start, oDFIACHId or Status is Null", 6);
      return -1;
    }
    String str1 = "Update ACH_File set FileStatus = ?  WHERE ODFIACHId = ? ";
    Object[] arrayOfObject = { paramString2, paramString1 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFile.updateStatusByODFIACHId: failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log("ACHFile.updateStatusByODFIACHId: done, rows" + i, 6);
    return i;
  }
  
  private static ACHFileInfo a(FFSResultSet paramFFSResultSet, ACHFileInfo paramACHFileInfo, boolean paramBoolean)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo1 = null;
    ACHRecordInfo localACHRecordInfo2 = null;
    paramACHFileInfo.setFileId(paramFFSResultSet.getColumnString("FileId"));
    paramACHFileInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
    paramACHFileInfo.setDueDate(paramFFSResultSet.getColumnString("DueDate"));
    paramACHFileInfo.setDtProcessed(paramFFSResultSet.getColumnString("DtProcessed"));
    paramACHFileInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
    paramACHFileInfo.setFileStatus(paramFFSResultSet.getColumnString("FileStatus"));
    paramACHFileInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
    paramACHFileInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    if (paramBoolean == true)
    {
      String str = paramFFSResultSet.getColumnString("FileHeaderType");
      paramACHFileInfo.setFileHeaderType(str);
      localACHRecordInfo1 = new ACHRecordInfo();
      paramACHFileInfo.setFileHeader(localACHRecordInfo1);
      if (str.equals("ADV"))
      {
        localObject = new TypeADVFileHeaderRecord();
        localACHRecordInfo1.setRecord(localObject);
      }
      else
      {
        localObject = new TypeFileHeaderRecord();
        localACHRecordInfo1.setRecord(localObject);
      }
      a(paramFFSResultSet.getColumnString("RDFIACHId"), paramFFSResultSet.getColumnString("ODFIACHId"), paramFFSResultSet.getColumnString("ReferenceCODE"), paramFFSResultSet.getColumnString("OrgCreateDate"), paramFFSResultSet.getColumnString("BPWCreateDate"), paramFFSResultSet.getColumnString("FileHeaderType"), paramACHFileInfo);
      localACHRecordInfo2 = new ACHRecordInfo();
      paramACHFileInfo.setFileControl(localACHRecordInfo2);
      Object localObject = new TypeFileControlRecord();
      localACHRecordInfo2.setRecord(localObject);
      a(paramFFSResultSet.getColumnString("BatchCount"), paramFFSResultSet.getColumnString("BlockCount"), paramFFSResultSet.getColumnString("RecordCount"), paramFFSResultSet.getColumnString("TotalDebit"), paramFFSResultSet.getColumnString("TotalCredit"), paramACHFileInfo);
    }
    paramACHFileInfo.setMemo(FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo")));
    paramACHFileInfo.setOdfiId(paramFFSResultSet.getColumnString("ODFIACHId"));
    paramACHFileInfo.setRdfiId(paramFFSResultSet.getColumnString("RDFIACHId"));
    paramACHFileInfo.setExportFileName(paramFFSResultSet.getColumnString("ExportFileName"));
    paramACHFileInfo.setUploadFileName(paramFFSResultSet.getColumnString("UploadFileName"));
    paramACHFileInfo.setStatusCode(0);
    paramACHFileInfo.setStatusMsg("Success");
    return paramACHFileInfo;
  }
  
  private static void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFile.setHeaderInfo: Could not set Header Info as ACHFileInfo object is Null ", 0);
      return;
    }
    paramACHFileInfo.setFileHeaderFieldValueObject(1, paramString1);
    if ((paramString2 != null) && (paramString2.length() == 8)) {
      paramString2 = paramString2 + BPWUtil.calculateCheckDigit(paramString2);
    }
    paramACHFileInfo.setFileHeaderFieldValueObject(2, paramString2);
    paramACHFileInfo.setFileHeaderFieldValueObject(3, paramString3);
    paramACHFileInfo.setFileHeaderFieldValueObject(4, paramString5.substring(paramString5.length() - 1));
    paramACHFileInfo.setFileHeaderFieldValueObject(5, paramString5.substring(0, 6));
    paramACHFileInfo.setFileHeaderFieldValueObject(6, paramString5.substring(6, 10));
  }
  
  private static void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ACHFileInfo paramACHFileInfo)
  {
    paramACHFileInfo.setFileControlFieldValueObject(8, paramString1);
    paramACHFileInfo.setFileControlFieldValueObject(9, paramString2);
    paramACHFileInfo.setFileControlFieldValueObject(10, paramString3);
    paramACHFileInfo.setFileControlFieldValueObject(22, paramString4);
    paramACHFileInfo.setFileControlFieldValueObject(23, paramString5);
  }
  
  private static ACHFileInfo a(ACHFileInfo paramACHFileInfo, String paramString, long paramLong, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramACHFileInfo.getFileId() };
    StringBuffer localStringBuffer1 = new StringBuffer();
    String str1 = paramFFSConnectionHolder.conn.getDatabaseType();
    Clob localClob = null;
    long l1 = paramACHFileInfo.getFilePageSize();
    String str2 = "SELECT FileContent, SubmitDate, FileStatus, FileSize, FileType, ChunkId FROM ACH_FlatFile  WHERE FileId = ? ";
    str2 = str2 + paramString + " ORDER BY ChunkId ASC ";
    FFSDebug.log("ACHFile.getRecordsFromDatabaseQuery stmt =" + str2 + "; skipRecords =" + paramLong, 6);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        if (str1.indexOf("ORACLE") != -1)
        {
          localClob = localFFSResultSet.getColumnClob("FileContent");
          localStringBuffer1.append(localClob.getSubString(1L, (int)localClob.length()));
        }
        else
        {
          localStringBuffer1.append(localFFSResultSet.getColumnString("FileContent"));
        }
        paramACHFileInfo.setSubmitDate(localFFSResultSet.getColumnString("SubmitDate"));
        paramACHFileInfo.setFileSize(localFFSResultSet.getColumnLong("FileSize"));
        paramACHFileInfo.setChunkId(localFFSResultSet.getColumnString("ChunkId"));
      }
      String str3 = localStringBuffer1.toString();
      localObject1 = null;
      if (FFSStringTokenizer.hasDelimeter(str3, ACHConsts.REC_DELIMS)) {
        localObject1 = new FFSStringTokenizer(str3, ACHConsts.REC_DELIMS);
      } else {
        localObject1 = new FFSStringTokenizer(str3, 94);
      }
      str4 = "";
      if (str3.indexOf("\r\n") != -1) {
        str4 = "\r\n";
      } else if (str3.indexOf("\r") != -1) {
        str4 = "\r";
      } else if (str3.indexOf("\n") != -1) {
        str4 = "\n";
      }
      for (int i = 0; (i < paramLong) && (((FFSStringTokenizer)localObject1).hasMoreTokens()); i++) {
        ((FFSStringTokenizer)localObject1).nextToken();
      }
      if (l1 == 0L) {
        l1 = ((FFSStringTokenizer)localObject1).countTokens();
      }
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int j = 0; (j < l1) && (((FFSStringTokenizer)localObject1).hasMoreTokens()); j++)
      {
        if (j > 0) {
          localStringBuffer2.append(str4);
        }
        localStringBuffer2.append(((FFSStringTokenizer)localObject1).nextToken());
      }
      if ((str3.endsWith(str4)) && (localStringBuffer2.toString().length() > 0)) {
        localStringBuffer2.append(str4);
      }
      paramACHFileInfo.setFileContent(localStringBuffer2.toString());
      long l2 = paramACHFileInfo.getFileSize();
      long l3 = 0L;
      if (l1 == 0L)
      {
        l3 = l2;
      }
      else
      {
        l3 = paramACHFileInfo.getFileCursor() + l1;
        if (l3 >= l2) {
          l3 = l2;
        }
      }
      paramACHFileInfo.setFileCursor(l3);
      paramACHFileInfo.setStatusCode(0);
      paramACHFileInfo.setStatusMsg("Success");
    }
    catch (Exception localException1)
    {
      Object localObject1 = "*** ACHFile.getRecordsFromDatabase: failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHFile.getRecordsFromDatabase  failed:" + localException2.toString(), 0);
      }
    }
    return paramACHFileInfo;
  }
  
  private static long a(long paramLong1, boolean paramBoolean, long paramLong2, Long[] paramArrayOfLong)
  {
    FFSDebug.log("ACHFile.calculateRecordsToSkip: start, cursor = " + paramLong1 + ", startChunkSize = " + paramLong2, 6);
    int i = 0;
    long l1 = 0L;
    if (paramLong1 == 0L)
    {
      FFSDebug.log("ACHFile.calculateRecordsToSkip:  recordsToSkip =" + paramLong1, 6);
      return 0L;
    }
    if (paramBoolean)
    {
      FFSDebug.log("ACHFile.calculateRecordsToSkip:  recordsToSkip =" + paramLong1, 6);
      return paramLong1;
    }
    for (i = 0; (i < paramArrayOfLong.length) && (paramArrayOfLong[i].longValue() != paramLong2); i++) {}
    long l2 = 0L;
    if (i == 0) {
      l2 = 0L;
    } else if (i != paramArrayOfLong.length) {
      l2 = paramArrayOfLong[(i - 1)].longValue();
    }
    l1 = Math.abs(paramLong1 - l2);
    FFSDebug.log("ACHFile.calculateRecordsToSkip:  recordsToSkip =" + l1, 6);
    return l1;
  }
  
  private static long a(long paramLong, Long[] paramArrayOfLong)
    throws FFSException
  {
    int i = 0;
    long l = 0L;
    int j = paramArrayOfLong.length;
    try
    {
      if (paramLong == 0L) {
        return l;
      }
      for (i = 0; i < j; i++)
      {
        l = paramArrayOfLong[i].longValue();
        if (paramLong <= l) {
          return l;
        }
      }
      if (i == j) {
        l = paramArrayOfLong[(i - 1)].longValue();
      }
    }
    catch (Exception localException)
    {
      String str1 = "*** ACHFile.calculateChunkSizeForQuery: failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    return l;
  }
  
  private static Long[] jdMethod_byte(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    String str1 = "SELECT CumChunkSize  FROM ACH_FlatFile  WHERE FileId = ? ";
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(new Long(localFFSResultSet.getColumnLong("CumChunkSize")));
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHFile.getCumulativeChunkSizeList: failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHFile.getCumulativeChunkSizeList close rset failed : " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    return (Long[])localArrayList.toArray(new Long[0]);
  }
  
  private static void jdMethod_do(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    Writer localWriter = null;
    FFSResultSet localFFSResultSet = null;
    String str1 = "INSERT INTO ACH_FlatFile (FileId, ChunkId, CumChunkSize, FileContent, SubmitDate, FileStatus, FileSize, FileType, LogId) VALUES (?,?,?,?,?,?,?,?,?) ";
    try
    {
      Object[] arrayOfObject = { paramACHFileInfo.getFileId(), paramACHFileInfo.getChunkId(), new Long(paramACHFileInfo.getCumulativeChunkSize()), " EMPTY_CLOB()", paramACHFileInfo.getSubmitDate(), "WILLPROCESSON", paramACHFileInfo.getFileSize() + "", null, paramACHFileInfo.getLogId() };
      try
      {
        DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      }
      catch (Exception localException2)
      {
        localObject2 = "*** ACHFile.insertClobForOracle: failed: ";
        String str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log(str2, 0);
        throw new FFSException(localException2, (String)localObject2);
      }
      str1 = "SELECT FileContent, SubmitDate, FileStatus, FileSize, FileType, ChunkId FROM ACH_FlatFile  WHERE FileId = ? AND ChunkId = ?";
      str1 = str1 + " FOR UPDATE NOWAIT";
      localObject1 = new Object[] { paramACHFileInfo.getFileId(), paramACHFileInfo.getChunkId() != null ? new Integer(paramACHFileInfo.getChunkId()) : new Integer(0) };
      localObject2 = null;
      try
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, (Object[])localObject1);
        while (localFFSResultSet.getNextRow()) {
          localObject2 = (CLOB)localFFSResultSet.getColumnObject("FileContent");
        }
      }
      catch (Exception localException3)
      {
        String str3 = "*** ACHFile.insertClobForOracle: failed: ";
        String str4 = FFSDebug.stackTrace(localException3);
        FFSDebug.log(str4, 0);
        throw new FFSException(localException3, str3);
      }
      char[] arrayOfChar = paramACHFileInfo.getFileContent().toCharArray();
      localWriter = ((CLOB)localObject2).getCharacterOutputStream();
      localWriter.write(arrayOfChar);
    }
    catch (Exception localException1)
    {
      Object localObject1 = "*** ACHFile.insertClobForOracle: failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localWriter != null)
        {
          localWriter.flush();
          localWriter.close();
        }
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException4)
      {
        FFSDebug.log("*** ACHFile.insertClobForOracle  failed:" + localException4.toString(), 0);
      }
    }
  }
  
  private static String a(long paramLong1, long paramLong2, long paramLong3)
  {
    String str = "";
    if (paramLong3 == 0L) {
      str = " AND CumChunkSize <= " + paramLong1;
    } else if (paramLong1 == 0L) {
      str = " AND CumChunkSize <= " + paramLong2;
    } else if (paramLong2 == paramLong1) {
      str = " AND CumChunkSize = " + paramLong1;
    } else {
      str = " AND CumChunkSize >= " + paramLong1 + " AND CumChunkSize <= " + paramLong2;
    }
    return str;
  }
  
  private static ACHFileHist a(ACHFileHist paramACHFileHist, String paramString, Object[] paramArrayOfObject, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getHistoryByCursorAndHistId: start, stmt =" + paramString + ", cursorId =" + paramACHFileHist.getCursorId() + ", histId =" + paramACHFileHist.getHistId(), 6);
    String str1 = paramACHFileHist.getCursorId();
    String str2 = paramACHFileHist.getHistId();
    if (str1 == null) {
      str1 = "0";
    }
    if ((str2 == null) || (str2.equals("0")) || (str2.trim().length() == 0))
    {
      str2 = a(paramString, paramArrayOfObject, paramACHFileHist, paramFFSConnectionHolder);
      str1 = "0";
    }
    a(str2, str1, paramACHFileHist.getFilePageSize(), paramACHFileHist, paramFFSConnectionHolder);
    if (paramACHFileHist.getFiles().length > 0)
    {
      paramACHFileHist.setStatusCode(0);
      paramACHFileHist.setStatusMsg("Success");
    }
    else
    {
      paramACHFileHist.setStatusCode(16020);
      paramACHFileHist.setStatusMsg(" record not found");
    }
    return paramACHFileHist;
  }
  
  private static String a(String paramString, Object[] paramArrayOfObject, ACHFileHist paramACHFileHist, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    ACHFileInfo localACHFileInfo = null;
    FFSResultSet localFFSResultSet = null;
    int j = 0;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int k = localPropertyConfig.getBatchSize();
      str1 = DBUtil.getNextIndexString("HistID");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString, paramArrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        int i = Integer.parseInt(localFFSResultSet.getColumnString("DtProcessed"));
        localACHFileInfo = new ACHFileInfo();
        localACHFileInfo.setFileId(localFFSResultSet.getColumnString("FileId"));
        localACHFileInfo.setDueDate(localFFSResultSet.getColumnString("DueDate"));
        localACHFileInfo.setFileStatus(localFFSResultSet.getColumnString("FileStatus"));
        localACHFileInfo.setDtProcessed(i + "");
        str2 = localACHFileInfo.getFileId();
        str3 = "ACH";
        j++;
        str4 = DBUtil.getCursor(i, j);
        TempHist.create(paramFFSConnectionHolder, str1, str4, str2, str3, i, null);
        if (j % k == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramACHFileHist.setHistId(str1);
      paramACHFileHist.setTotalFiles(j);
    }
    catch (Throwable localThrowable)
    {
      String str5 = "*** ACHFile.makeHistory failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, 0);
      throw new FFSException(localThrowable, str5);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** ACHFile.makeHistory failed:" + localException.toString(), 0);
      }
    }
    return str1;
  }
  
  private static void a(String paramString1, String paramString2, long paramLong, ACHFileHist paramACHFileHist, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getHistory start, histId =" + paramString1 + ", cursorId =" + paramString2 + ", pageSize =" + paramLong, 6);
    ArrayList localArrayList = new ArrayList();
    ACHFileInfo localACHFileInfo = null;
    String str1 = null;
    try
    {
      TempHist[] arrayOfTempHist = TempHist.get(paramFFSConnectionHolder, paramString1, paramString2, paramLong);
      localObject = null;
      for (int i = 0; i < arrayOfTempHist.length; i++)
      {
        localObject = arrayOfTempHist[i];
        str1 = ((TempHist)localObject).RecordExtId;
        localACHFileInfo = getACHFileInfo(str1, paramFFSConnectionHolder);
        localArrayList.add(localACHFileInfo);
      }
      if (arrayOfTempHist.length > 0) {
        paramACHFileHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
      }
      paramACHFileHist.setFiles((ACHFileInfo[])localArrayList.toArray(new ACHFileInfo[0]));
      if (paramACHFileHist.getTotalFiles() == 0L) {
        paramACHFileHist.setTotalFiles(TempHist.getCount(paramFFSConnectionHolder, paramString1));
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      Object localObject = "*** ACHFile.getHistory failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log("ACHFile.getHistory done,", 6);
  }
  
  public static int getMaxACHFileChunkId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getMaxACHFileChunkId start ...", 6);
    int i = -1;
    String str1 = "SELECT max( ChunkId ) from ACH_FlatFile where FileId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      FFSDebug.log("ACHFile.getMaxACHFileChunkId end, value: " + i, 6);
      int j = i;
      return j;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** ACHPayee.getMaxACHFileChunkId failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** ACHFile.getMaxACHFileChunkId close rset failed : " + FFSDebug.stackTrace(localException), 0);
      }
    }
  }
  
  public static int getMinACHFileChunkId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getMinACHFileChunkId start ...", 6);
    int i = -1;
    String str1 = "SELECT min( ChunkId ) from ACH_FlatFile where FileId = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      FFSDebug.log("ACHFile.getMinACHFileChunkId end, value: " + i, 6);
      int j = i;
      return j;
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** ACHFile.getMinACHFileChunkId failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** ACHFile.getMinACHFileChunkId close rset failed : " + FFSDebug.stackTrace(localException), 0);
      }
    }
  }
  
  public static String getACHFileChunkByFileIdAndChunkId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getACHFileChunkByFileAndChunkId start ...", 6);
    String str1 = "";
    String str2 = "SELECT FileContent from ACH_FlatFile where FileId = ? and ChunkId = ?";
    Object[] arrayOfObject = { paramString, new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        if (paramFFSConnectionHolder.conn.getDatabaseType().indexOf("ORACLE") != -1)
        {
          localObject1 = localFFSResultSet.getColumnClob("FileContent");
          str1 = ((Clob)localObject1).getSubString(1L, (int)((Clob)localObject1).length());
        }
        else
        {
          str1 = localFFSResultSet.getColumnString("FileContent");
        }
      }
      FFSDebug.log("ACHFile.getACHFileChunkByFileAndChunkId end, value: " + str1, 6);
      Object localObject1 = str1;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** ACHFile.getACHFileChunkByFileAndChunkId failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          FFSDebug.log("ACHFile.getACHFileChunkByFileAndChunkId closing rset. ", 6);
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** ACHFile.getACHFileChunkByFileAndChunkId close rset failed : " + FFSDebug.stackTrace(localException), 0);
      }
    }
  }
  
  public static String getACHFileExportFileName(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ACHFile.getACHFileExportFileName start ...", 6);
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "SELECT ODFIACHId, OrgCreateDate from ACH_File where FileId = ?";
    Object[] arrayOfObject = { paramString1 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str2 = localFFSResultSet.getColumnString("ODFIACHId");
        str3 = localFFSResultSet.getColumnString("OrgCreateDate");
      }
      else
      {
        str5 = "*** ACHFile.getACHFileExportFileName failed: Can't find orgCreateDate.";
        FFSDebug.log(str5, 0);
        str6 = null;
        return str6;
      }
      if ((str3 == null) || (str3.length() != 11))
      {
        str5 = "*** ACHFile.getACHFileExportFileName failed: Invalid orgCreateDate: " + str3;
        FFSDebug.log(str5, 0);
        str6 = null;
        return str6;
      }
      String str5 = str3.substring(0, 6);
      str6 = str3.substring(6, 10);
      String str7 = str3.substring(10, 11);
      str1 = str2 + paramString2 + str5 + paramString2 + str6 + paramString2 + str7;
      FFSDebug.log("ACHFile.getACHFileExportFileName end, value: " + str1, 6);
      String str8 = str1;
      return str8;
    }
    catch (Throwable localThrowable)
    {
      String str6 = "*** ACHFile.getACHFileExportFileName failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str6, 0);
      throw new FFSException(localThrowable, str6);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** ACHFile.getACHFileExportFileName close rset failed : " + FFSDebug.stackTrace(localException), 0);
      }
    }
  }
  
  public static String updateACHFileChunkStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ACHFile.updateACHFileChunkStatus start ...", 6);
    String str1 = "";
    String str2 = "UPDATE ACH_FlatFile set FileStatus = ? where FileId = ? and ChunkId = ?";
    Object[] arrayOfObject = { paramString2, paramString1, new Integer(paramInt) };
    Object localObject1 = null;
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      FFSDebug.log("ACHFile.updateACHFileChunkStatus end, value: " + paramString2, 6);
      String str3 = str1;
      return str3;
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** ACHFile.updateACHFileChunkStatus failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      try
      {
        if (localObject1 != null)
        {
          localObject1.close();
          localObject1 = null;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** ACHFile.updateACHFileChunkStatus close rset failed : " + FFSDebug.stackTrace(localException), 0);
      }
    }
  }
  
  private static ACHFileHist a(ACHFileHist paramACHFileHist, String paramString)
  {
    if (paramACHFileHist == null)
    {
      paramACHFileHist = new ACHFileHist();
      paramACHFileHist.setStatusCode(16000);
      paramACHFileHist.setStatusMsg("ACHFileHist  is null");
      FFSDebug.log("ACHFile." + paramString + " : " + "Null ACHFileHist passed", 0);
      return paramACHFileHist;
    }
    a(paramACHFileHist);
    String str;
    if (!BPWUtil.checkFrontEndDateFormat(paramACHFileHist.getStartDate()))
    {
      paramACHFileHist.setStatusCode(17205);
      str = paramString + ":" + "Invalid start date value for getting histories." + ": " + paramACHFileHist.getStartDate();
      paramACHFileHist.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return paramACHFileHist;
    }
    if (!BPWUtil.checkFrontEndDateFormat(paramACHFileHist.getEndDate()))
    {
      paramACHFileHist.setStatusCode(17206);
      str = paramString + ":" + "Invalid end date value for getting histories." + ": " + paramACHFileHist.getEndDate();
      paramACHFileHist.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return paramACHFileHist;
    }
    FFSDebug.log("ACHFile." + paramString + " : StartDate :", paramACHFileHist.getStartDate(), "EndDate :", paramACHFileHist.getEndDate(), 0);
    if (Integer.parseInt(paramACHFileHist.getStartDate()) > Integer.parseInt(paramACHFileHist.getEndDate()))
    {
      paramACHFileHist.setStatusCode(17080);
      paramACHFileHist.setStatusMsg("Start date cannot be after end date");
      FFSDebug.log("ACHFile." + paramString + " : " + " Start Date is later than End Date", 0);
      return paramACHFileHist;
    }
    paramACHFileHist.setStatusCode(0);
    paramACHFileHist.setStatusMsg("Success");
    return paramACHFileHist;
  }
  
  private static void a(ACHFileHist paramACHFileHist)
  {
    if ((paramACHFileHist.getStartDate() == null) || (paramACHFileHist.getStartDate().trim().length() == 0)) {
      paramACHFileHist.setStartDate(new String("0000000000"));
    } else if (paramACHFileHist.getStartDate().trim().length() == 8) {
      paramACHFileHist.setStartDate(paramACHFileHist.getStartDate().trim() + "00");
    }
    if ((paramACHFileHist.getEndDate() == null) || (paramACHFileHist.getEndDate().trim().length() == 0))
    {
      Integer localInteger = new Integer(DBUtil.getCurrentStartDate() + 1000000);
      paramACHFileHist.setEndDate(localInteger.toString());
    }
    else if (paramACHFileHist.getEndDate().trim().length() == 8)
    {
      paramACHFileHist.setEndDate(paramACHFileHist.getEndDate().trim() + "00");
    }
  }
  
  public static void checkCustomer(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHFileInfo == null)
    {
      FFSDebug.log("ACHFile.checkCustomer: ACHFileInfo is Null ! ", 0);
      paramACHFileInfo = new ACHFileInfo();
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("ACHFileInfo  is null");
      return;
    }
    if (paramACHFileInfo.getCustomerId() == null)
    {
      FFSDebug.log("ACHFile.checkCustomer: ACHFileInfo.CustomerId is Null ! ", 0);
      paramACHFileInfo.setStatusCode(16000);
      paramACHFileInfo.setStatusMsg("ACHFileInfo.CustomerId  is null");
      return;
    }
    try
    {
      String str1 = null;
      localObject = Customer.getCustomerInfo(paramACHFileInfo.getCustomerId(), paramFFSConnectionHolder, paramACHFileInfo);
      String str2;
      if (localObject == null)
      {
        str2 = BPWLocaleUtil.getMessage(19130, new String[] { paramACHFileInfo.getCustomerId() }, "TRANSFER_MESSAGE");
        throw new FFSException(19130, str2);
      }
      str1 = ((CustomerInfo)localObject).status;
      if ((str1 == null) || (str1.equals("CLOSED")))
      {
        str2 = "ACHFile.checkCustomer: No customer exists with CustomerId = " + paramACHFileInfo.getCustomerId();
        FFSDebug.log(str2, 0);
        paramACHFileInfo.setStatusCode(16020);
        paramACHFileInfo.setStatusMsg(str2);
      }
      else
      {
        paramACHFileInfo.setFiId(((CustomerInfo)localObject).fIID);
        paramACHFileInfo.setStatusCode(0);
        paramACHFileInfo.setStatusMsg("Success");
        paramACHFileInfo.setRdfiId(paramACHFileInfo.getFileHeaderFieldValueObject(1));
        paramACHFileInfo.setOdfiId(paramACHFileInfo.getFileHeaderFieldValueObject(2));
        str2 = ACHAdapterUtil.getProperty("bpw.ach.fileupload.validate.achfi", "Y");
        if (str2.compareTo("Y") == 0)
        {
          ACHFIInfo localACHFIInfo = ACHFI.getACHFIInfo(paramFFSConnectionHolder, paramACHFileInfo.getOdfiId());
          if ((localACHFIInfo == null) || (localACHFIInfo.getStatusCode() == 16020))
          {
            paramACHFileInfo.setStatusCode(23170);
            paramACHFileInfo.setStatusMsg("FIId does not exist :" + paramACHFileInfo.getOdfiId());
            FFSDebug.log("ACHFile.checkCustomer failed, FIID does not exist with ODFIACHId =" + paramACHFileInfo.getOdfiId(), 0);
            return;
          }
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject = "***ACHFile.checkCustomer : " + localException.toString();
      throw new FFSException(localException, (String)localObject);
    }
  }
  
  public static int cleanIncompleteACHFiles(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo[] paramArrayOfACHFileInfo)
    throws FFSException
  {
    if (paramFFSConnectionHolder == null)
    {
      String str1 = "FFSConnectionHolder object  is null";
      FFSDebug.log("ACHFile.cleanIncompleteACHFiles failed, " + str1, 0);
      throw new FFSException(16000, str1);
    }
    if ((paramArrayOfACHFileInfo == null) || (paramArrayOfACHFileInfo.length == 0)) {
      return 0;
    }
    int i = 0;
    try
    {
      int j = paramArrayOfACHFileInfo.length;
      for (int k = 0; k < j; k++)
      {
        deleteACHFlatFile(paramArrayOfACHFileInfo[k].getFileId(), paramFFSConnectionHolder);
        i += deleteACHFile(paramArrayOfACHFileInfo[k].getFileId(), paramFFSConnectionHolder);
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str2 = "*** ACHFile.cleanIncompleteACHFiles: failed:";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log("ACHFile.cleanIncompleteACHFiles: done, No of rows  deleted =" + i, 6);
    return i;
  }
  
  private static boolean a(String paramString, ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log("ACHFile.isFileAlreadyPresent: start, orgCreateDate =" + paramString + ", ODFIACHID =" + paramACHFileInfo.getOdfiId(), 6);
    boolean bool = false;
    if ((paramString == null) || (paramString.length() < 11))
    {
      str1 = "orgCreateDate is null or length is less than 11.";
      FFSDebug.log("*** ACHFile.isFileAlreadyPresent: " + str1, 6);
      paramACHFileInfo.setStatusCode(24071);
      paramACHFileInfo.setStatusMsg("Invalid Org Create Date. (Position 24-29)" + str1);
      return false;
    }
    String str1 = "SELECT count(FileId)  FROM ACH_File  WHERE ODFIACHId = ? AND OrgCreateDate = ? AND CustomerId = ?  AND FileStatus != 'CANCELEDON' AND FileStatus != 'ACHTEMPORARY'";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramACHFileInfo.getOdfiId(), paramString, paramACHFileInfo.getCustomerId() };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        int i = localFFSResultSet.getColumnInt(1);
        bool = i > 0;
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHFile.isFileAlreadyPresent failed: ";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHFile.isFileAlreadyPresent failed: " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    if (bool == true)
    {
      paramACHFileInfo.setStatusCode(23300);
      paramACHFileInfo.setStatusMsg("ACH File exists with same Create Date, Create Time, File Modifier and  ODFIACHID ");
    }
    else
    {
      paramACHFileInfo.setStatusCode(0);
      paramACHFileInfo.setStatusMsg("Success");
    }
    FFSDebug.log("ACHFile.isFileAlreadyPresent: done, File Present =" + bool + ", status =" + paramACHFileInfo.getStatusMsg(), 6);
    return bool;
  }
  
  private static String a(String paramString, ACHFileHist paramACHFileHist, ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer(jdMethod_if(paramString, paramACHFileHist, paramArrayList));
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHFileHist.getStatusList(), " AND FileStatus IN (", paramArrayList);
    Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramACHFileHist.getStartDate()));
    Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramACHFileHist.getEndDate()));
    DBUtil.appendToCondition(localStringBuffer, localInteger1, " AND DtProcessed >= ?", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, localInteger2, " AND DtProcessed <= ?", paramArrayList);
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_if(String paramString, ACHFileHist paramACHFileHist, ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    DBUtil.appendToCondition(localStringBuffer, paramACHFileHist.getFiId(), " AND ODFIACHId = ? ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHFileHist.getCustomerId(), " AND CustomerId = ? ", paramArrayList);
    return localStringBuffer.toString();
  }
  
  public static void doTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    String str = paramACHFileInfo.getCustomerId();
    Object localObject1 = paramACHFileInfo.getFileControlFieldValueObject(22);
    if (localObject1 == null) {
      localObject1 = new Long(0L);
    }
    Object localObject2 = paramACHFileInfo.getFileControlFieldValueObject(23);
    if (localObject2 == null) {
      localObject2 = new Long(0L);
    }
    long l = ((Long)localObject1).longValue() + ((Long)localObject2).longValue();
    BigDecimal localBigDecimal = BigDecimal.valueOf(l).movePointLeft(2);
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramString1, paramACHFileInfo.getAgentId(), paramACHFileInfo.getAgentType(), paramString2, paramACHFileInfo.getLogId(), paramInt, Integer.parseInt(str), localBigDecimal, null, paramACHFileInfo.getFileId(), paramACHFileInfo.getFileStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
  }
  
  public static ACHFileInfo[] getACHFileInfoToBeDeleted(FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, String paramString)
    throws FFSException
  {
    String str1 = "ACHFile.getACHFileInfoToBeDeleted: ";
    FFSDebug.log(str1 + "start. Time: " + paramString, 6);
    String str2 = "SELECT FileId, CustomerId, ReferenceCODE, RDFIACHId, ODFIACHId,  OrgCreateDate,BPWCreateDate, DueDate, FileHeaderType,SubmitDate, FileStatus, BatchCount,BlockCount,RecordCount,TotalDebit, TotalCredit, LogId, SubmittedBy, Memo, DtProcessed, NumberOfCredits, NumberOfDebits, ExportFileName, UploadFileName  FROM ACH_File  WHERE FileStatus = 'ACHTEMPORARY' AND SubmitDate < ?";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        ACHFileInfo localACHFileInfo = new ACHFileInfo();
        localACHFileInfo = a(localFFSResultSet, localACHFileInfo, paramBoolean);
        localArrayList.add(localACHFileInfo);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + ": failed: ";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** " + str1 + " failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + "done ", 6);
    return (ACHFileInfo[])localArrayList.toArray(new ACHFileInfo[0]);
  }
  
  public static int cancelIncompleteACHFiles(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHFile.cancelIncompleteACHFiles: ";
    FFSDebug.log(str1 + "start.", 6);
    String str2 = "UPDATE ACH_File SET FileStatus = 'CANCELEDON' WHERE FileStatus = 'ACHTEMPORARY' AND SubmitDate < ?";
    Object[] arrayOfObject = { paramString };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + ": failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "done ", 6);
    return i;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "ACHFile.cleanup ";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str6 = localSimpleDateFormat.format(localCalendar.getTime());
    FFSDebug.log(str1 + "AgeInDate format = " + str6, 6);
    try
    {
      str2 = "DELETE FROM ACH_File WHERE (FileStatus = 'POSTEDON' AND DueDate <= ? AND CustomerId = ?)";
      str3 = "DELETE FROM ACH_FlatFile WHERE (FileId IN (SELECT B.FileId FROM ACH_File B WHERE B.FileStatus = 'POSTEDON' AND B.DueDate <= ? AND B.CustomerId = ?))";
      str4 = "DELETE FROM ACH_FileBatch WHERE (FileId IN (SELECT B.FileId FROM ACH_File B WHERE B.FileStatus = 'POSTEDON' AND B.DueDate <= ? AND B.CustomerId = ?))";
      str5 = "DELETE FROM ACH_FileEntry WHERE (BatchId IN (SELECT B.BatchId FROM ACH_FileBatch B WHERE (B.FileId IN (SELECT C.FileId FROM ACH_File C WHERE C.FileStatus = 'POSTEDON' AND C.DueDate <= ? AND C.CustomerId = ?))))";
      Object[] arrayOfObject = { new Integer(str6), paramString };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM SCH_EventInfoLog WHERE (InstructionID IN (SELECT B.FileId FROM ACH_File B WHERE B.FileStatus = 'POSTEDON' AND B.DueDate <= ? AND B.CustomerId = ?))", arrayOfObject);
      i += DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      i += DBUtil.executeStatement(paramFFSConnectionHolder, str5, arrayOfObject);
      i += DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
      i += DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str7 = "*** " + str1 + " failed: ";
      str8 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str7, str8, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str7 = "***  " + str1 + ": failed:";
      String str8 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str8, 0);
      throw new FFSException(localException, str7);
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  public static void addFileBatchAndEntry(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, String paramString)
    throws FFSException
  {
    String str = "ACHFile.addFileBatchAndEntry: ";
    FFSDebug.log(str + "start, fileId =" + paramString + ", aCHFile.fileId =" + paramACHFileInfo.getFileId(), 6);
    ACHBatchInfo[] arrayOfACHBatchInfo = paramACHFileInfo.getBatches();
    int i = arrayOfACHBatchInfo.length;
    for (int j = 0; j < i; j++) {
      a(paramFFSConnectionHolder, paramACHFileInfo, arrayOfACHBatchInfo[j], paramString);
    }
    paramACHFileInfo.setFileId(paramString);
    jdMethod_do(paramFFSConnectionHolder, paramACHFileInfo);
    FFSDebug.log(str + "end", 6);
  }
  
  private static String a(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, ACHBatchInfo paramACHBatchInfo, String paramString)
    throws FFSException
  {
    String str1 = "ACHFile.addFileBatch: ";
    FFSDebug.log(str1 + "start, fileId =" + paramString, 6);
    String str2 = DBUtil.getNextIndexStringWithPadding("FileBatchID", 32, '0');
    int i = 0;
    int j = 0;
    String str3 = "INSERT into ACH_FileBatch (BatchId, FileId, CompanyName, CompanyIdentification,StdEntryClassCode, EffectiveEntryDate, EntryAddendaCount, TotalDebits,NumberOfDebits, TotalCredits, NumberOfCredits, BatchNumber )  Values (?,?,?,?,?,?,?,?,?,?,?,?) ";
    Object[] arrayOfObject = { str2, paramString, paramACHBatchInfo.getBatchHeaderFieldValueObject(3), paramACHBatchInfo.getBatchHeaderFieldValueObject(5), paramACHBatchInfo.getClassCode(), paramACHBatchInfo.getBatchHeaderFieldValueObject(9), paramACHBatchInfo.getBatchControlFieldValueObject(20), paramACHBatchInfo.getBatchControlFieldValueObject(22), new Integer(i), paramACHBatchInfo.getBatchControlFieldValueObject(23), new Integer(j), paramACHBatchInfo.getBatchControlFieldValueObject(12) };
    FFSDebug.log(str1 + " args =" + Arrays.asList(arrayOfObject), 6);
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed: ";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    ACHRecordInfo[] arrayOfACHRecordInfo = paramACHBatchInfo.getRecords();
    int k = arrayOfACHRecordInfo.length;
    for (int m = 0; m < k; m++)
    {
      a(paramFFSConnectionHolder, arrayOfACHRecordInfo[m], str2);
      if (ACHAgent.isDebit(ACHAgent.getTransactionCode(arrayOfACHRecordInfo[m].getRecordContent()))) {
        i++;
      } else {
        j++;
      }
    }
    paramACHFileInfo.setNumberOfDebits(paramACHFileInfo.getNumberOfDebits() + i);
    paramACHFileInfo.setNumberOfCredits(paramACHFileInfo.getNumberOfCredits() + j);
    a(paramFFSConnectionHolder, str2, j, i);
    FFSDebug.log(str1 + "done ", 6);
    return str2;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, String paramString)
    throws FFSException
  {
    String str1 = "ACHFile.addFileEntry: ";
    FFSDebug.log(str1 + "start, batchId =" + paramString, 6);
    String str2 = DBUtil.getNextIndexStringWithPadding("FileEntryID", 32, '0');
    String str3 = "INSERT into ACH_FileEntry (EntryId, BatchId, TransactionCode, RecvDFIIdentification,DFIAccountNumber, RcvCompIndvName, IdentificationNumber, Amount,TraceNumber)  Values (?,?,?,?,?,?,?,?,?) ";
    ACHPayeeInfo localACHPayeeInfo = paramACHRecordInfo.getPayeeInfoFromMBRecord();
    Object[] arrayOfObject = { str2, paramString, String.valueOf(paramACHRecordInfo.getFieldValueObject("Transaction_Code")), localACHPayeeInfo.getBankRTN(), localACHPayeeInfo.getBankAcctId(), localACHPayeeInfo.getPayeeName(), localACHPayeeInfo.getPayAcct(), String.valueOf(paramACHRecordInfo.getFieldValueObject("Amount")), String.valueOf(paramACHRecordInfo.getFieldValueObject("Trace_Number")) };
    FFSDebug.log(str1 + " args =" + Arrays.asList(arrayOfObject), 6);
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str4 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed: ";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    FFSDebug.log(str1 + "done, rows =" + i, 6);
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "ACHFile.updateNumberOfCreditsAndDebits: ";
    FFSDebug.log(str1 + "start, batchId =" + paramString + ", NumberOfCredits =" + paramInt1 + ", NumberOfDebits =" + paramInt2, 6);
    String str2 = "UPDATE ACH_FileBatch set NumberOfCredits = ?, NumberOfDebits = ? Where BatchId = ?";
    Object[] arrayOfObject = { new Integer(paramInt1), new Integer(paramInt2), paramString };
    FFSDebug.log(str1 + " args =" + Arrays.asList(arrayOfObject), 6);
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + ": failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "done, rows =" + i, 6);
  }
  
  private static void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo)
    throws FFSException
  {
    String str1 = "ACHFile.updateACHFileNumberOfCreditsDebits: ";
    FFSDebug.log(str1 + "start, fileId =" + paramACHFileInfo.getFileId() + ", NumberOfCredits =" + paramACHFileInfo.getNumberOfCredits() + ", NumberOfDebits =" + paramACHFileInfo.getNumberOfDebits(), 6);
    String str2 = "UPDATE ACH_File set NumberOfCredits = ?, NumberOfDebits = ? Where FileId = ?";
    Object[] arrayOfObject = { new Integer(paramACHFileInfo.getNumberOfCredits()), new Integer(paramACHFileInfo.getNumberOfDebits()), paramACHFileInfo.getFileId() };
    FFSDebug.log(str1 + " args =" + Arrays.asList(arrayOfObject), 6);
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + ": failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + "done, rows =" + i, 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHFile
 * JD-Core Version:    0.7.0.1
 */