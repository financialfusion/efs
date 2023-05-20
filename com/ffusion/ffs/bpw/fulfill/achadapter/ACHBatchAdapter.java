package com.ffusion.ffs.bpw.fulfill.achadapter;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.ACHCompany;
import com.ffusion.ffs.bpw.db.ACHFI;
import com.ffusion.ffs.bpw.db.ACHPayee;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.IACHBatchAdapter;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.master.ACHBatchProcessor;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.ACHAdapterConsts;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.ACHFileCache;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.logging.AuditLogRecord;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ACHBatchAdapter
  implements IACHBatchAdapter, ACHAdapterConsts
{
  private String gq = "";
  private String gw = "";
  private String gt = "";
  private int go = 1000;
  private String gu = "MsgSet";
  private HashMap gy = null;
  private HashMap gA = null;
  String gC = null;
  String gp = null;
  private HashMap gn = null;
  private HashMap gr = null;
  private ACHBatchProcessor gv = null;
  private ACHAgent gz = null;
  private int gs;
  private String gx = "";
  private String gE = null;
  private BPWFIInfo gB = null;
  private String gD = null;
  
  public void start(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean, String paramString3)
    throws Exception
  {
    FFSDebug.log("ACHBatchAdapter.start is called", 6);
    this.gE = paramString2;
    this.go = ACHAdapterUtil.getPropertyInt("BatchSize", 1000);
    this.gu = ACHAdapterUtil.getProperty("ach.version", "MsgSet");
    this.gC = ACHAdapterUtil.getProperty("ach.export.dir", "export");
    this.gq = ACHAdapterUtil.getFileNameBase(this.gC);
    this.gp = (this.gq + "temp");
    this.gt = ACHAdapterUtil.getFileNameBase(this.gp);
    String str = ACHAdapterUtil.getProperty("ach.error.dir", "error");
    this.gw = ACHAdapterUtil.getFileNameBase(str);
    this.gy = new HashMap();
    this.gA = new HashMap();
    this.gn = new HashMap();
    this.gr = new HashMap();
    this.gz = ((ACHAgent)FFSRegistry.lookup("BPWACHAGENT"));
    if (this.gz == null)
    {
      FFSDebug.log("ACHBatchAdapter.start: ACHAgent has not been started! Terminating process!", 0);
      throw new FFSException("ACHBatchAdapter.start: ACHAgent has not been started! Terminating process!");
    }
    this.gv = new ACHBatchProcessor();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.gs = localPropertyConfig.LogLevel;
    this.gx = ACHAdapterUtil.getProperty("ach.export.achbatch.FileExtension", "");
    if (paramBoolean) {
      processEmptyFile(paramFFSConnectionHolder, paramString3);
    }
    this.gB = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramString3);
    this.gD = ACHAdapterUtil.getProperty("bpw.ach.payee.enforce.prenote.period", "N");
  }
  
  public void processACHBatches(ACHBatchInfo[] paramArrayOfACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ACHBatchAdapter.processACHBatches is called", 6);
    if (paramArrayOfACHBatchInfo == null) {
      return;
    }
    BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal("0.0");
    BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal("0.0");
    for (int i = 0; i < paramArrayOfACHBatchInfo.length; i++)
    {
      a(paramArrayOfACHBatchInfo[i], paramFFSConnectionHolder);
      BigDecimal localBigDecimal3 = FFSUtil.setDefaultScale(BigDecimal.valueOf(paramArrayOfACHBatchInfo[i].getBatchControlFieldValueLong(22))).movePointLeft(2);
      BigDecimal localBigDecimal4 = FFSUtil.setDefaultScale(BigDecimal.valueOf(paramArrayOfACHBatchInfo[i].getBatchControlFieldValueLong(23))).movePointLeft(2);
      localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
      localBigDecimal2 = localBigDecimal2.add(localBigDecimal4);
      String str;
      if (localBigDecimal1.compareTo(ACHConsts.MAX_TOTAL_DEBITS_BD) > 0)
      {
        str = "Can't generate a NACHA file for batches because the total debits exceeds the limit: " + localBigDecimal1;
        FFSDebug.log("ACHBatchAdapter.processACHBatches failed: " + str);
        throw new Exception(str);
      }
      if (localBigDecimal2.compareTo(ACHConsts.MAX_TOTAL_CREDITS_BD) > 0)
      {
        str = "Can't generate a NACHA file for batches because the total credits exceeds the limit: " + localBigDecimal2;
        FFSDebug.log("ACHBatchAdapter.processACHBatches failed: " + str);
        throw new Exception(str);
      }
    }
  }
  
  public void shutdown(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ACHBatchAdapter.shutdown is called", 6);
    a(this.gy, this.gn);
    a(this.gA, this.gr);
    a(this.gy, paramFFSConnectionHolder);
    a(this.gA, paramFFSConnectionHolder);
    this.gy.clear();
    this.gA.clear();
    this.gy = null;
    this.gA = null;
    this.gn.clear();
    this.gr.clear();
    this.gn = null;
    this.gr = null;
    this.gD = null;
  }
  
  private void a(HashMap paramHashMap, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "ACHBatchAdapter.moveFilesToExport";
    if ((paramHashMap != null) && (paramHashMap.size() > 0))
    {
      Object[] arrayOfObject = paramHashMap.values().toArray();
      for (int i = 0; i < arrayOfObject.length; i++)
      {
        File localFile1 = new File((String)arrayOfObject[i]);
        File localFile2 = new File(this.gq + localFile1.getName());
        if (localFile2.exists())
        {
          FFSDebug.log("Export file exists " + localFile2.getCanonicalPath(), 0);
          String str2 = this.gw + localFile2.getName() + "." + String.valueOf(System.currentTimeMillis()) + "." + this.gx;
          File localFile3 = new File(str2);
          localFile2.renameTo(localFile3);
          localFile2 = new File(this.gq + localFile1.getName());
          FFSDebug.log("The existing export file has been moved to  " + localFile3.getCanonicalPath(), 0);
        }
        localFile1.renameTo(localFile2);
        ACHAdapterUtil.doFMLoggingForACH(paramFFSConnectionHolder, localFile2.getCanonicalPath(), str1);
      }
    }
  }
  
  private void a(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramACHBatchInfo == null) {
      return;
    }
    String str1 = "ACHBatchAdapter.processACHBatch: ";
    String str2 = paramACHBatchInfo.getBatchId();
    FFSDebug.log(str1 + " is called. Batch id: " + str2, 6);
    paramACHBatchInfo.setBatchPageSize(this.go);
    paramACHBatchInfo.setRecordCursor(0L);
    paramACHBatchInfo = this.gv.getBatchTXFromAdapter(paramFFSConnectionHolder, paramACHBatchInfo, false, true, false);
    if (paramACHBatchInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str1 + "Can't find ACH Batch Info for this id! Id: " + str2, 0);
      FFSDebug.log(str1 + "Can't find ACH Batch Info for this id! error code: " + paramACHBatchInfo.getStatusCode(), 0);
      FFSDebug.log(str1 + "Can't find ACH Batch Info for this id! error message: " + paramACHBatchInfo.getStatusMsg(), 0);
      FFSDebug.log(str1 + "This batch is skipped. Id: " + str2, 0);
      return;
    }
    String str3 = paramACHBatchInfo.getCompId();
    ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(str3, paramFFSConnectionHolder);
    if (localACHCompanyInfo.getStatusCode() != 0)
    {
      str4 = "Cannot find ACH Company for this batch. This company could have been deleted.Batch Id: " + paramACHBatchInfo.getBatchId();
      FFSDebug.log(str1 + str4, 0);
      a(paramACHBatchInfo, paramFFSConnectionHolder, str4);
      paramACHBatchInfo.setStatusCode(17512);
      paramACHBatchInfo.setStatusMsg(str4);
      return;
    }
    if (!"ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory()))
    {
      jdMethod_int(paramFFSConnectionHolder, paramACHBatchInfo);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg() + " Can not process this batch. Id: " + str2, 0);
        a(paramACHBatchInfo, paramFFSConnectionHolder, paramACHBatchInfo.getStatusMsg());
        return;
      }
    }
    if ((this.gD.compareTo("Y") == 0) && (!"ACH_BATCH_PRENOTE".equals(paramACHBatchInfo.getBatchCategory())) && (!ACHPayee.checkPayeePrenoteForACHBatch(paramFFSConnectionHolder, str2, paramACHBatchInfo.getBatchType())))
    {
      str4 = "This batch includes prenote failed payees.";
      FFSDebug.log(str1 + str4 + " Id: " + str2, 0);
      a(paramACHBatchInfo, paramFFSConnectionHolder, str4);
      return;
    }
    String str4 = localACHCompanyInfo.getODFIACHId();
    ACHFIInfo localACHFIInfo = ACHFI.getACHFIInfo(paramFFSConnectionHolder, str4);
    if ((localACHFIInfo == null) || (localACHFIInfo.getFIStatus() == null) || (localACHFIInfo.getFIStatus().equals("CLOSED")))
    {
      str5 = "FIId does not exist :" + str4;
      FFSDebug.log(str1, " failed:", str5, 0);
      a(paramACHBatchInfo, paramFFSConnectionHolder, str5);
      return;
    }
    String str5 = null;
    ACHFileCache localACHFileCache = null;
    int i = paramACHBatchInfo.getBatchHeaderFieldValueShort(2);
    if (i != 280)
    {
      str5 = a(paramACHBatchInfo, this.gy, this.gn, localACHFIInfo, false);
      localACHFileCache = (ACHFileCache)this.gn.get(str4);
    }
    else
    {
      str5 = a(paramACHBatchInfo, this.gA, this.gr, localACHFIInfo, true);
      localACHFileCache = (ACHFileCache)this.gr.get(str4);
    }
    FFSDebug.log("ACHBatchAdapter.processACHBatch:  Export file name : " + str5, 6);
    localACHFileCache.fileBatchCount += 1;
    String str6 = new Integer(localACHFileCache.fileBatchCount).toString();
    paramACHBatchInfo.setBatchHeaderFieldValueObject(12, str6);
    paramACHBatchInfo.setBatchControlFieldValueObject(12, str6);
    String str7 = ((String)paramACHBatchInfo.getBatchHeaderFieldValueObject(3)).toUpperCase();
    String str8 = ((String)paramACHBatchInfo.getBatchHeaderFieldValueObject(7)).toUpperCase();
    paramACHBatchInfo.setBatchHeaderFieldValueObject(3, str7);
    paramACHBatchInfo.setBatchHeaderFieldValueObject(7, str8);
    ACHAdapterUtil.writeACHRecord(str5, paramACHBatchInfo.getBatchHeader(), true, this.gz);
    localACHFileCache.recCount += 1L;
    boolean bool = true;
    int j = 1;
    ACHPayeeInfo localACHPayeeInfo;
    while (j != 0)
    {
      localObject = paramACHBatchInfo.getRecords();
      if ((localObject != null) && (localObject.length != 0))
      {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int m = 0; m < localObject.length; m++)
        {
          localACHPayeeInfo = localObject[m];
          localStringBuffer.append(localACHPayeeInfo.getRecordContent());
          localACHFileCache.recCount += 1L;
          ACHAddendaInfo[] arrayOfACHAddendaInfo = localObject[m].getAddenda();
          if (arrayOfACHAddendaInfo != null)
          {
            int n = arrayOfACHAddendaInfo.length;
            for (int i1 = 0; i1 < n; i1++)
            {
              localStringBuffer.append(arrayOfACHAddendaInfo[i1].getAddendaContent());
              localACHFileCache.recCount += 1L;
            }
          }
        }
        ACHAdapterUtil.writeRecordContents(str5, localStringBuffer.toString(), bool);
        paramACHBatchInfo.setStartRecordId(localObject[(localObject.length - 1)].getRecordId());
      }
      else
      {
        j = 0;
      }
      if (paramACHBatchInfo.isLastPage() == true) {
        j = 0;
      }
      if (j != 0) {
        paramACHBatchInfo = ACHBatch.getACHBatch(paramACHBatchInfo, paramFFSConnectionHolder, false, true, false);
      }
    }
    Object localObject = paramACHBatchInfo.getBatchControl();
    ACHAdapterUtil.writeACHRecord(str5, (ACHRecordInfo)localObject, true, this.gz);
    int k = str5.lastIndexOf(File.separator);
    String str9 = str5.substring(k + 1, str5.length());
    ACHBatch.updateACHBatchPostedOn(paramFFSConnectionHolder, paramACHBatchInfo, str9, localACHFileCache.fileBatchCount, this.gE);
    a(paramFFSConnectionHolder, paramACHBatchInfo, "Successfully process an ACH Batch");
    if ((paramACHBatchInfo.getBatchCategory() != null) && (paramACHBatchInfo.getBatchCategory().compareTo("ACH_BATCH_PRENOTE") == 0) && (paramACHBatchInfo.getRecords() != null) && (paramACHBatchInfo.getRecords().length != 0) && (paramACHBatchInfo.getRecords()[0] != null))
    {
      localACHPayeeInfo = new ACHPayeeInfo();
      localACHPayeeInfo.setPayeeID(paramACHBatchInfo.getRecords()[0].getPayeeId());
      localACHPayeeInfo.setPrenoteSubmitDate(FFSUtil.getDateString(new SimpleDateFormat("yyyy/MM/dd")));
      ACHPayee.updateACHPayeePrenoteSubmitDate(paramFFSConnectionHolder, localACHPayeeInfo);
    }
    localACHFileCache.recCount += 1L;
    localACHFileCache.fileEntryCount += paramACHBatchInfo.getBatchControlFieldValueInt(20);
    localACHFileCache.fileHash += paramACHBatchInfo.getBatchControlFieldValueLong(21);
    localACHFileCache.fileDebitSum = localACHFileCache.fileDebitSum.add(BigDecimal.valueOf(paramACHBatchInfo.getBatchControlFieldValueLong(22)));
    localACHFileCache.fileCreditSum = localACHFileCache.fileCreditSum.add(BigDecimal.valueOf(paramACHBatchInfo.getBatchControlFieldValueLong(23)));
  }
  
  private String a(ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap1, HashMap paramHashMap2, ACHFIInfo paramACHFIInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = paramACHBatchInfo.getExportFileName();
    if ((str1 != null) && (str1.length() != 0))
    {
      localObject = new File(this.gt + str1);
      ((File)localObject).deleteOnExit();
    }
    Object localObject = paramACHFIInfo.getODFIACHId();
    String str2 = (String)paramHashMap1.get(localObject);
    if (str2 == null)
    {
      str2 = ACHAdapterUtil.prepareExportFile(paramACHFIInfo, this.gt, this.gw, paramBoolean, this.gz, this.gu, this.gx);
      paramHashMap1.put(localObject, str2);
      ACHFileCache localACHFileCache = new ACHFileCache();
      paramHashMap2.put(localObject, localACHFileCache);
      localACHFileCache.recCount += 1L;
    }
    return str2;
  }
  
  private void a(HashMap paramHashMap1, HashMap paramHashMap2)
    throws FFSException
  {
    Iterator localIterator = paramHashMap1.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)paramHashMap1.get(str1);
      if (str2 == null)
      {
        FFSDebug.log("ACHBatchAdapter.writeFileControls: Can't find fullFileName for this ODFI ACH id! id: " + str1, 0);
      }
      else
      {
        ACHFileCache localACHFileCache = (ACHFileCache)paramHashMap2.get(str1);
        if (localACHFileCache == null)
        {
          FFSDebug.log("ACHBatchAdapter.writeFileControls: Can't find ACHFileCache for this ODFI ACH id! Id: " + str1, 0);
        }
        else
        {
          localACHFileCache.recCount += 1L;
          ACHAdapterUtil.writeFileControl(str2, localACHFileCache, this.gz, this.gu);
        }
      }
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, String paramString)
    throws FFSException
  {
    String str1 = "ACHBatchAdapter.doTransAuditLog:";
    if (this.gs >= 4)
    {
      long l = paramACHBatchInfo.getNonOffBatchCreditSum() + paramACHBatchInfo.getNonOffBatchDebitSum();
      BigDecimal localBigDecimal = BigDecimal.valueOf(l).movePointLeft(2);
      int i = 0;
      try
      {
        i = Integer.parseInt(paramACHBatchInfo.getCustomerId());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str3 = str1 + " CustomerId is not an integer - " + paramACHBatchInfo.getCustomerId() + " - " + localNumberFormatException.toString();
        FFSDebug.log(str3 + FFSDebug.stackTrace(localNumberFormatException), 0);
        throw new FFSException(localNumberFormatException, str3);
      }
      String str2 = paramString + ", Batch Category = " + paramACHBatchInfo.getBatchCategory() + ", Batch type = " + paramACHBatchInfo.getBatchType() + ", Batch balanced type= " + paramACHBatchInfo.getBatchBalanceType();
      FFSDebug.log(str1 + str2, 6);
      int j = 4301;
      if (paramACHBatchInfo.getBatchStatus().equals("POSTEDON"))
      {
        if ((paramACHBatchInfo.getBatchCategory() != null) && (paramACHBatchInfo.getBatchCategory().compareTo("ACH_BATCH_TAX") == 0)) {
          j = 3602;
        } else {
          j = 3600;
        }
      }
      else if ((paramACHBatchInfo.getBatchCategory() != null) && (paramACHBatchInfo.getBatchCategory().equals("ACH_BATCH_PRENOTE"))) {
        j = 4305;
      }
      AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramACHBatchInfo.getSubmittedBy(), null, null, str2, paramACHBatchInfo.getLogId(), j, i, localBigDecimal, null, paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getBatchStatus(), null, null, null, null, 0);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
  }
  
  private void a(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    int i = LimitCheckApprovalProcessor.processACHBatchDelete(paramFFSConnectionHolder, paramACHBatchInfo, null);
    String str = "FAILEDON";
    if (i == 5) {
      str = "LIMIT_REVERT_FAILED";
    }
    ACHBatch.updateACHBatchStatus(paramACHBatchInfo, str, paramFFSConnectionHolder, false);
    paramACHBatchInfo.setBatchStatus(str);
    a(paramFFSConnectionHolder, paramACHBatchInfo, "Failed to process this ACHBatch: " + paramString);
  }
  
  private void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    int i = Integer.parseInt(FFSUtil.getDateString("yyyyMMdd"));
    this.gv.validateEffectiveDate(paramFFSConnectionHolder, paramACHBatchInfo, this.gB, i);
  }
  
  public void processEmptyFile(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    ACHFIInfo[] arrayOfACHFIInfo = ACHFI.getACHFIInfosByFIID(paramFFSConnectionHolder, paramString);
    if ((arrayOfACHFIInfo != null) && (arrayOfACHFIInfo.length > 0)) {
      for (int i = 0; i < arrayOfACHFIInfo.length; i++)
      {
        String str = arrayOfACHFIInfo[i].getODFIACHId();
        Object localObject = this.gn.get(str);
        if (localObject == null) {
          createEmptyFile(arrayOfACHFIInfo[i]);
        }
      }
    }
  }
  
  public void createEmptyFile(ACHFIInfo paramACHFIInfo)
    throws Exception
  {
    String str = ACHAdapterUtil.prepareExportFile(paramACHFIInfo, this.gt, this.gw, false, this.gz, this.gu, this.gx);
    this.gy.put(paramACHFIInfo.getODFIACHId(), str);
    ACHFileCache localACHFileCache = new ACHFileCache();
    localACHFileCache.recCount += 1L;
    this.gn.put(paramACHFIInfo.getODFIACHId(), localACHFileCache);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.achadapter.ACHBatchAdapter
 * JD-Core Version:    0.7.0.1
 */