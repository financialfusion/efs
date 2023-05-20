package com.ffusion.ffs.bpw.fulfill.achadapter;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.custimpl.MacGenerator;
import com.ffusion.ffs.bpw.db.ACHFI;
import com.ffusion.ffs.bpw.db.BPWBankAcct;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.CashCon;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWBankAcctInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.CCBatchHistInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CCCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.bpw.interfaces.CCLocationInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.ICashConAdapter;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.ACHAdapterConsts;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleHistory;
import com.ffusion.ffs.util.ACHBatchCache;
import com.ffusion.ffs.util.ACHFileCache;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDEntryDetailRecord;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CashConAdapter
  implements ICashConAdapter, ACHAdapterConsts
{
  private int f4;
  ACHFileCache f9 = null;
  private String f2;
  private String f5;
  private String f8;
  private String f1;
  private ACHFIInfo gc;
  private CutOffInfo f7;
  private BPWFIInfo gb;
  private String gd;
  private ACHAgent ga = null;
  private String f6 = "MsgSet";
  private String fZ = null;
  private int f0 = 0;
  private String fX = null;
  private boolean fY = false;
  private boolean f3 = false;
  private int ge = 2;
  
  public void start()
    throws Exception
  {
    String str1 = "CashConAdapter.start: ";
    FFSDebug.log(str1 + "is called", 6);
    String str2 = ACHAdapterUtil.getProperty("ach.export.dir", "export");
    this.f2 = ACHAdapterUtil.getFileNameBase(str2);
    String str3 = this.f2 + "temp";
    this.f5 = ACHAdapterUtil.getFileNameBase(str3);
    String str4 = ACHAdapterUtil.getProperty("ach.error.dir", "error");
    this.f8 = ACHAdapterUtil.getFileNameBase(str4);
    this.f9 = new ACHFileCache();
    this.f6 = ACHAdapterUtil.getProperty("ach.version", "MsgSet");
    this.ga = ((ACHAgent)FFSRegistry.lookup("BPWACHAGENT"));
    this.fZ = ACHAdapterUtil.getProperty("bpw.cashcon.fileExtension", "CC");
    if (this.ga == null)
    {
      FFSDebug.log("CashConAdapter.start: ACHAgent has not been started! Terminating process!", 0);
      throw new FFSException("CashConAdapter.start: ACHAgent has not been started! Terminating process!");
    }
    this.f0 = ACHAdapterUtil.getPropertyInt("BatchSize", 1000);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.f4 = localPropertyConfig.LogLevel;
    this.ge = ACHAdapterUtil.getPropertyInt("bpw.cashcon.batch.effective.future.days", 2);
    FFSDebug.log(str1 + "successful", 6);
  }
  
  public void processOneCutOff(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    String str1 = "CashConAdapter.processOneCutOff: ";
    FFSDebug.log(str1 + "start. CutOffId = " + paramCutOffInfo.getCutOffId() + ". FIID = " + paramString1, 6);
    this.f7 = paramCutOffInfo;
    this.gd = paramString2;
    this.fY = paramBoolean2;
    this.f3 = paramBoolean3;
    this.gc = jdMethod_long(paramFFSConnectionHolder, paramString1);
    this.gb = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramString1);
    this.f1 = jdMethod_if(this.gc);
    this.fX = BPWUtil.getDateInNewFormat(paramCutOffInfo.getNextProcessTime(), "yyyy/MM/dd HH:mm", "yyyyMMdd");
    this.fX += "00";
    String str2 = "0";
    for (CCCompanyCutOffInfo[] arrayOfCCCompanyCutOffInfo = CashCon.getCCCompanyCutOffArrayByCutOffId(paramFFSConnectionHolder, paramCutOffInfo.getCutOffId(), str2, this.f0); arrayOfCCCompanyCutOffInfo != null; arrayOfCCCompanyCutOffInfo = CashCon.getCCCompanyCutOffArrayByCutOffId(paramFFSConnectionHolder, paramCutOffInfo.getCutOffId(), str2, this.f0))
    {
      int i = arrayOfCCCompanyCutOffInfo.length;
      if (i == 0) {
        break;
      }
      for (int j = 0; j < i; j++)
      {
        String str3 = arrayOfCCCompanyCutOffInfo[j].getTransactionType();
        if (str3 != null) {
          jdMethod_else(paramFFSConnectionHolder, arrayOfCCCompanyCutOffInfo[j].getCompId(), str3);
        }
      }
      if (i < this.f0) {
        break;
      }
      str2 = arrayOfCCCompanyCutOffInfo[(i - 1)].getCompId();
    }
    if ((paramBoolean1 == true) || (this.f9.fileBatchCount > 0))
    {
      H();
      jdMethod_long(paramFFSConnectionHolder);
    }
    else
    {
      I();
    }
    FFSDebug.log(str1 + "done.", 6);
  }
  
  public void processRunNow(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    String str1 = "CashConAdapter.processRunNow: ";
    FFSDebug.log(str1 + "start. FIID = " + paramString1, 6);
    this.gd = paramString2;
    this.fY = paramBoolean2;
    this.f3 = paramBoolean3;
    this.gc = jdMethod_long(paramFFSConnectionHolder, paramString1);
    this.gb = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramString1);
    this.f1 = jdMethod_if(this.gc);
    this.f7 = new CutOffInfo();
    this.f7.setFIId(paramString1);
    this.f7.setInstructionType("CASHCONTRN");
    this.f7.setProcessTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm"));
    this.f7.setNextProcessTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm"));
    this.fX = BPWUtil.getDateInNewFormat(this.f7.getNextProcessTime(), "yyyy/MM/dd HH:mm", "yyyyMMdd");
    this.fX += "00";
    String str2 = "0";
    for (CCCompanyInfo[] arrayOfCCCompanyInfo = CashCon.getCCCompanyArrayByFIId(paramFFSConnectionHolder, paramString1, str2, this.f0); arrayOfCCCompanyInfo != null; arrayOfCCCompanyInfo = CashCon.getCCCompanyArrayByFIId(paramFFSConnectionHolder, paramString1, str2, this.f0))
    {
      int i = arrayOfCCCompanyInfo.length;
      if (i == 0) {
        break;
      }
      for (int j = 0; j < i; j++)
      {
        jdMethod_else(paramFFSConnectionHolder, arrayOfCCCompanyInfo[j].getCompId(), "CONCENTRATION");
        jdMethod_else(paramFFSConnectionHolder, arrayOfCCCompanyInfo[j].getCompId(), "DISBURSEMENT");
      }
      if (i < this.f0) {
        break;
      }
      str2 = arrayOfCCCompanyInfo[(i - 1)].getCompId();
    }
    if ((paramBoolean1 == true) || (this.f9.fileBatchCount > 0)) {
      H();
    } else {
      I();
    }
    jdMethod_long(paramFFSConnectionHolder);
    FFSDebug.log(str1 + "done.", 6);
  }
  
  public void shutdown()
    throws Exception
  {
    FFSDebug.log("CashConAdapter.shutdown is called", 6);
    this.f9 = null;
    this.f2 = null;
    this.f5 = null;
    this.f8 = null;
    this.f1 = null;
    this.gc = null;
    this.f7 = null;
    this.gb = null;
    this.gd = null;
    this.ga = null;
    this.fZ = null;
  }
  
  private ACHFIInfo jdMethod_long(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ACHFIInfo localACHFIInfo = ACHFI.getCutOffACHFIInfo(paramFFSConnectionHolder, paramString);
    if ((localACHFIInfo == null) || (localACHFIInfo.getStatusCode() != 0)) {
      throw new FFSException(localACHFIInfo.getStatusCode(), "Failed to get ACHFI with CurOff for FIId: " + paramString + ". " + localACHFIInfo.getStatusMsg());
    }
    return localACHFIInfo;
  }
  
  private String jdMethod_if(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    if (this.f1 == null)
    {
      this.f1 = ACHAdapterUtil.prepareExportFile(paramACHFIInfo, this.f5, this.f8, false, this.ga, this.f6, this.fZ);
      this.f9.recCount += 1L;
    }
    return this.f1;
  }
  
  private void jdMethod_else(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashConAdapter.processOneBatch: ";
    FFSDebug.log(str1 + "start. CompId= " + paramString1, 6);
    int i = 0;
    CCCompanyInfo localCCCompanyInfo = new CCCompanyInfo();
    localCCCompanyInfo.setCompId(paramString1);
    localCCCompanyInfo = CashCon.getCCCompany(paramFFSConnectionHolder, localCCCompanyInfo);
    this.f9.batchCache = new ACHBatchCache();
    ACHRecordInfo localACHRecordInfo1 = null;
    ArrayList localArrayList = new ArrayList();
    int j = 1;
    String str2 = localCCCompanyInfo.getBatchType();
    CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
    localCCCompanyAcctInfo.setCompId(paramString1);
    localCCCompanyAcctInfo.setTransactionType(paramString2);
    if (str2 == null)
    {
      j = 0;
    }
    else if (str2.compareTo("BatchBalancedBatch") == 0)
    {
      if (paramString2.equals("DISBURSEMENT") == true) {
        localCCCompanyAcctInfo.setAcctId(localCCCompanyInfo.getDisburseAcctId());
      } else {
        localCCCompanyAcctInfo.setAcctId(localCCCompanyInfo.getConcentrateAcctId());
      }
      localCCCompanyAcctInfo = CashCon.getCCCompanyAcct(paramFFSConnectionHolder, localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo.getStatusCode() != 0) {
        j = 0;
      }
    }
    String str3 = "0";
    for (CCLocationInfo[] arrayOfCCLocationInfo = jdMethod_char(paramFFSConnectionHolder, paramString1, str3); (arrayOfCCLocationInfo != null) && (arrayOfCCLocationInfo.length > 0); arrayOfCCLocationInfo = jdMethod_char(paramFFSConnectionHolder, paramString1, str3))
    {
      int k = arrayOfCCLocationInfo.length;
      if (k == 0) {
        break;
      }
      int m = 0;
      for (int n = 0; n < k; n++) {
        if (j == 1)
        {
          a(paramFFSConnectionHolder, arrayOfCCLocationInfo[n], localCCCompanyInfo, localArrayList, paramString2);
          m = localArrayList.size();
          if ((i == 0) && (m > 0))
          {
            i = 1;
            this.f9.fileBatchCount += 1;
            localACHRecordInfo1 = a(localCCCompanyInfo);
            this.f9.recCount += 1L;
            localArrayList.add(0, localACHRecordInfo1);
          }
          if (m > this.f0)
          {
            jdMethod_if(localArrayList);
            localArrayList = new ArrayList();
            m = 0;
            paramFFSConnectionHolder.conn.commit();
          }
        }
        else
        {
          String str4 = "Can not find offset account for this CashCon company.";
          jdMethod_if(paramFFSConnectionHolder, arrayOfCCLocationInfo[n], paramString2, str4);
        }
      }
      if (k < this.f0) {
        break;
      }
      str3 = arrayOfCCLocationInfo[(k - 1)].getLocationId();
    }
    if ((j == 1) && (i == 1))
    {
      if (str2.compareTo("BatchBalancedBatch") == 0) {
        a(paramFFSConnectionHolder, localCCCompanyInfo, localArrayList, localCCCompanyAcctInfo);
      }
      ACHRecordInfo localACHRecordInfo2 = a(localCCCompanyInfo, localACHRecordInfo1);
      localArrayList.add(localACHRecordInfo2);
      jdMethod_if(localArrayList);
      if (!this.fY) {
        a(paramFFSConnectionHolder, localCCCompanyInfo, paramString2);
      }
      this.f9.fileHash += this.f9.batchCache.batchHash;
    }
    FFSDebug.log(str1 + "done. ", 6);
  }
  
  private void jdMethod_if(ArrayList paramArrayList)
    throws FFSException
  {
    if ((paramArrayList != null) && (this.f1 != null))
    {
      int i = paramArrayList.size();
      for (int j = 0; j < i; j++) {
        ACHAdapterUtil.writeACHRecord(this.f1, (ACHRecordInfo)paramArrayList.get(j), true, this.ga);
      }
    }
  }
  
  private ACHRecordInfo a(CCCompanyInfo paramCCCompanyInfo)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeBatchHeaderRecord localTypeBatchHeaderRecord = new TypeBatchHeaderRecord();
    localTypeBatchHeaderRecord.Record_Type_Code = 5;
    localTypeBatchHeaderRecord.Service_Class_Code = 200;
    localTypeBatchHeaderRecord.Company_Entry_Description = "Cash Con";
    localTypeBatchHeaderRecord.Effective_Entry_Date = jdMethod_for(this.gc.getFIId(), this.ge);
    localTypeBatchHeaderRecord.Company_Name = paramCCCompanyInfo.getCompName();
    localTypeBatchHeaderRecord.Company_Identification = paramCCCompanyInfo.getCCCompId();
    localTypeBatchHeaderRecord.Standard_Entry_Class_Code = "CCD";
    localTypeBatchHeaderRecord.Originating_DFI_Identification = this.gc.getODFIACHId().substring(0, 8);
    localTypeBatchHeaderRecord.Originator_Status_Code = "1";
    localTypeBatchHeaderRecord.Batch_Number = this.f9.fileBatchCount;
    localACHRecordInfo.setRecordType(5);
    localACHRecordInfo.setClassCode("CCD");
    localACHRecordInfo.setAchVersion(this.f6);
    localACHRecordInfo.setRecord(localTypeBatchHeaderRecord);
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo a(CCCompanyInfo paramCCCompanyInfo, ACHRecordInfo paramACHRecordInfo)
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeBatchControlRecord localTypeBatchControlRecord = new TypeBatchControlRecord();
    localTypeBatchControlRecord.Record_Type_Code = 8;
    localTypeBatchControlRecord.Message_Authentication_Code = MacGenerator.generateMac(this.gc.getODFIACHId());
    localTypeBatchControlRecord.Message_Authentication_CodeExists = true;
    localTypeBatchControlRecord.Entry_Addenda_Count = this.f9.batchCache.batchEntryCount;
    localTypeBatchControlRecord.Total_Debits = this.f9.batchCache.batchDebitSum.longValue();
    localTypeBatchControlRecord.Total_Credits = this.f9.batchCache.batchCreditSum.longValue();
    localTypeBatchControlRecord.Company_Identification = paramCCCompanyInfo.getCCCompId();
    localTypeBatchControlRecord.Batch_Number = this.f9.fileBatchCount;
    TypeBatchHeaderRecord localTypeBatchHeaderRecord = (TypeBatchHeaderRecord)paramACHRecordInfo.getRecord();
    localTypeBatchControlRecord.Service_Class_Code = localTypeBatchHeaderRecord.Service_Class_Code;
    localTypeBatchControlRecord.Reserved6 = "";
    localTypeBatchControlRecord.Originating_DFI_Identification = localTypeBatchHeaderRecord.Originating_DFI_Identification;
    localTypeBatchControlRecord.Company_Identification = localTypeBatchHeaderRecord.Company_Identification;
    localTypeBatchControlRecord.Entry_Hash = this.f9.batchCache.batchHash;
    localACHRecordInfo.setRecordType(8);
    localACHRecordInfo.setClassCode("CCD");
    localACHRecordInfo.setAchVersion(this.f6);
    localACHRecordInfo.setRecord(localTypeBatchControlRecord);
    this.f9.recCount += 1L;
    return localACHRecordInfo;
  }
  
  private String jdMethod_for(String paramString, int paramInt)
    throws FFSException
  {
    Calendar localCalendar = Calendar.getInstance();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    int i = Integer.parseInt(str1);
    try
    {
      if (SmartCalendar.isTodayNonACHBusinessDay(paramString)) {
        i = SmartCalendar.getACHBusinessDay(paramString, i, true);
      }
    }
    catch (Exception localException1)
    {
      throw new FFSException(localException1, "Failed to check if today is not a business day of FIID: " + paramString);
    }
    for (int j = 0; j < paramInt; j++) {
      try
      {
        i = SmartCalendar.getACHBusinessDay(paramString, i, true);
      }
      catch (Exception localException2)
      {
        throw new FFSException(localException2, "Failed to get business day of FIID: " + paramString + ". Date: " + str1);
      }
    }
    String str2 = Integer.toString(i);
    return str2.substring(2);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, String paramString)
    throws FFSException
  {
    String str = "CashConAdapter.processOneLocation: ";
    FFSDebug.log(str + "start. LocationId: " + paramCCLocationInfo.getLocationId() + ". Transaction Type: " + paramString, 6);
    if (this.fY == true) {
      jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString);
    } else {
      jdMethod_for(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString);
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, String paramString)
    throws FFSException
  {
    String str = "CashConAdapter.processOneLocationReRunCutOff: ";
    FFSDebug.log(str + "start. LocationId: " + paramCCLocationInfo.getLocationId() + ". Transaction Type: " + paramString, 6);
    int i = paramArrayList.size();
    FFSDebug.log(str + ": Before processing, current number of entries: " + i, 6);
    CCEntryInfo[] arrayOfCCEntryInfo = jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo, paramString);
    int j = 0;
    if (arrayOfCCEntryInfo != null) {
      j = arrayOfCCEntryInfo.length;
    }
    if (j == 1) {
      a(paramFFSConnectionHolder, paramCCLocationInfo, arrayOfCCEntryInfo[0], paramCCCompanyInfo, paramArrayList);
    } else if (j != 0) {
      if (paramString.equals("CONCENTRATION") == true)
      {
        long l = paramCCLocationInfo.getThresholdDeposAmtLong();
        if (l != 0L) {
          a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, arrayOfCCEntryInfo);
        } else if ((paramCCLocationInfo.getConsolidateDepos() != null) && (paramCCLocationInfo.getConsolidateDepos().equals("Y"))) {
          jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, arrayOfCCEntryInfo);
        } else {
          a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString, arrayOfCCEntryInfo);
        }
      }
      else
      {
        a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString, arrayOfCCEntryInfo);
      }
    }
    FFSDebug.log(str + "done. Current number of entries: " + paramArrayList.size(), 6);
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, String paramString)
    throws FFSException
  {
    String str1 = "CashConAdapter.processOneLocationNormal: ";
    FFSDebug.log(str1 + "start. LocationId: " + paramCCLocationInfo.getLocationId() + ". Transaction Type: " + paramString, 6);
    int i = paramArrayList.size();
    FFSDebug.log(str1 + ": Before processing, current number of entries: " + i, 6);
    CCEntryInfo[] arrayOfCCEntryInfo = a(paramFFSConnectionHolder, paramCCLocationInfo, paramString);
    int j = 1;
    String str2 = paramCCCompanyInfo.getBatchType();
    CCCompanyAcctInfo localCCCompanyAcctInfo = new CCCompanyAcctInfo();
    localCCCompanyAcctInfo.setCompId(paramCCCompanyInfo.getCompId());
    localCCCompanyAcctInfo.setTransactionType(paramString);
    if (str2 == null)
    {
      j = 0;
    }
    else if (str2.compareTo("EntryBalancedBatch") == 0)
    {
      if (paramString.equals("DISBURSEMENT") == true) {
        localCCCompanyAcctInfo.setAcctId(paramCCLocationInfo.getDisburseAcctId());
      } else {
        localCCCompanyAcctInfo.setAcctId(paramCCLocationInfo.getConcentrateAcctId());
      }
      localCCCompanyAcctInfo = CashCon.getCCCompanyAcct(paramFFSConnectionHolder, localCCCompanyAcctInfo);
      if (localCCCompanyAcctInfo.getStatusCode() != 0) {
        j = 0;
      }
    }
    if (j == 1)
    {
      if (jdMethod_int(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString) == true)
      {
        FFSDebug.log(str1 + ": Prenote is not mature, fail entries. ", 6);
        String str3 = "Failed to process CCEntry because its location's prenote has not matured.";
        a(paramFFSConnectionHolder, paramCCLocationInfo, arrayOfCCEntryInfo, paramString, str3);
      }
      else
      {
        long l;
        if (arrayOfCCEntryInfo.length == 0)
        {
          l = paramCCLocationInfo.getAnticipatoryDeposLong();
          if ((paramString.equals("CONCENTRATION") == true) && (l != 0L))
          {
            FFSDebug.log(str1 + ": Creating AnticipatoryDepos: " + l, 6);
            a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, l);
          }
        }
        else if (paramString.equals("CONCENTRATION") == true)
        {
          l = paramCCLocationInfo.getThresholdDeposAmtLong();
          if (l != 0L) {
            a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, arrayOfCCEntryInfo);
          } else if ((paramCCLocationInfo.getConsolidateDepos() != null) && (paramCCLocationInfo.getConsolidateDepos().equals("Y"))) {
            jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, arrayOfCCEntryInfo);
          } else {
            a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString, arrayOfCCEntryInfo);
          }
        }
        else
        {
          a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString, arrayOfCCEntryInfo);
        }
      }
      if (paramArrayList.size() > i) {
        CashCon.updateLastRequestTimeOfCCLocation(paramFFSConnectionHolder, paramCCLocationInfo);
      }
    }
    else
    {
      String str4 = "Can not find offset account for this CashCon location.";
      a(paramFFSConnectionHolder, paramCCLocationInfo, arrayOfCCEntryInfo, paramString, str4);
    }
    FFSDebug.log(str1 + "done. Current number of entries: " + paramArrayList.size(), 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, String paramString, CCEntryInfo[] paramArrayOfCCEntryInfo)
    throws FFSException
  {
    String str = "CashConAdapter.processUserEntries: ";
    FFSDebug.log(str + "start. LocationId: " + paramCCLocationInfo.getLocationId(), 6);
    int i = paramArrayOfCCEntryInfo.length;
    for (int j = 0; j < i; j++) {
      a(paramFFSConnectionHolder, paramCCLocationInfo, paramArrayOfCCEntryInfo[j], paramCCCompanyInfo, paramArrayList);
    }
    FFSDebug.log(str + "done.", 6);
  }
  
  private boolean jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, String paramString)
    throws FFSException
  {
    boolean bool = false;
    if ((paramString.equals("CONCENTRATION")) && (paramCCLocationInfo.getDepositPrenote() != null) && (paramCCLocationInfo.getDepositPrenote().equals("Y"))) {
      if (paramCCLocationInfo.getDepPrenoteStatus() == null)
      {
        jdMethod_do(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString);
        bool = true;
      }
      else if (!paramCCLocationInfo.getDepPrenoteStatus().equals("Success"))
      {
        bool = true;
      }
    }
    if ((paramString.equals("DISBURSEMENT")) && (paramCCLocationInfo.getDisbursePrenote() != null) && (paramCCLocationInfo.getDisbursePrenote().equals("Y"))) {
      if (paramCCLocationInfo.getDisPrenoteStatus() == null)
      {
        jdMethod_do(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramString);
        bool = true;
      }
      else if (!paramCCLocationInfo.getDisPrenoteStatus().equals("Success"))
      {
        bool = true;
      }
    }
    String str = ACHAdapterUtil.getProperty("bpw.cashcon.enforce.prenote.period", "N");
    if (!str.equalsIgnoreCase("Y")) {
      bool = false;
    }
    return bool;
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, String paramString)
    throws FFSException
  {
    String str = "CashConAdapter.createPrenote: ";
    FFSDebug.log(str + "start", 6);
    CCEntryInfo localCCEntryInfo = a(paramCCLocationInfo, paramString, "0", "PRENOTE_ENTRY");
    short s = jdMethod_do(paramCCLocationInfo.getAccountType(), paramString, "PRENOTE_ENTRY");
    ACHRecordInfo localACHRecordInfo = a(paramCCLocationInfo, localCCEntryInfo, s);
    paramArrayList.add(localACHRecordInfo);
    if (localCCEntryInfo.getLastModifier() == null) {
      localCCEntryInfo.setLastModifier(localCCEntryInfo.getSubmittedBy());
    }
    localCCEntryInfo = CashCon.addCCEntryFromAdapter(paramFFSConnectionHolder, localCCEntryInfo);
    if (localCCEntryInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str + "failed. Reason: " + localCCEntryInfo.getStatusMsg(), 6);
      throw new FFSException(localCCEntryInfo.getStatusCode(), localCCEntryInfo.getStatusMsg());
    }
    a(paramFFSConnectionHolder, localCCEntryInfo, "Successfully processed prenote CCEntry.");
    if (paramString.equals("CONCENTRATION") == true) {
      a(paramFFSConnectionHolder, paramCCLocationInfo, "Pending", FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
    } else {
      jdMethod_do(paramFFSConnectionHolder, paramCCLocationInfo, "Pending", FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
    }
    FFSDebug.log(str + "done.", 6);
  }
  
  private CCEntryInfo a(CCLocationInfo paramCCLocationInfo, String paramString1, String paramString2, String paramString3)
  {
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    localCCEntryInfo.setLocationId(paramCCLocationInfo.getLocationId());
    localCCEntryInfo.setAgentId(paramCCLocationInfo.getAgentId());
    localCCEntryInfo.setAgentType(paramCCLocationInfo.getAgentType());
    localCCEntryInfo.setAmount(paramString2);
    localCCEntryInfo.setEntryCategory(paramString3);
    localCCEntryInfo.setLastProcessId(this.gd);
    localCCEntryInfo.setDueDate(FFSUtil.getDateString("yyyyMMdd") + "00");
    localCCEntryInfo.setStatus("POSTEDON");
    localCCEntryInfo.setSubmittedBy(paramCCLocationInfo.getSubmittedBy());
    localCCEntryInfo.setTransactionType(paramString1);
    localCCEntryInfo.setLogId(BPWTrackingIDGenerator.getNextId());
    localCCEntryInfo.setWillProcessTime(FFSUtil.getDateString());
    localCCEntryInfo.setProcessedTime(FFSUtil.getDateString());
    return localCCEntryInfo;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString1, String paramString2)
    throws FFSException
  {
    paramCCLocationInfo.setDepPrenoteStatus(paramString1);
    paramCCLocationInfo.setDepPrenSubDate(paramString2);
    CashCon.updateDepositPrenoteOfCCLocation(paramFFSConnectionHolder, paramCCLocationInfo);
    if (paramCCLocationInfo.getStatusCode() != 0)
    {
      FFSDebug.log("Failed to update deposit prenote information in location: " + paramCCLocationInfo.getLocationId() + ". Reason: " + paramCCLocationInfo.getStatusMsg(), 0);
      throw new FFSException(paramCCLocationInfo.getStatusCode(), paramCCLocationInfo.getStatusMsg());
    }
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString1, String paramString2)
    throws FFSException
  {
    paramCCLocationInfo.setDisPrenoteStatus(paramString1);
    paramCCLocationInfo.setDisPrenSubDate(paramString2);
    CashCon.updateDisbursePrenoteOfCCLocation(paramFFSConnectionHolder, paramCCLocationInfo);
    if (paramCCLocationInfo.getStatusCode() != 0)
    {
      FFSDebug.log("Failed to update disburse prenote information in location: " + paramCCLocationInfo.getLocationId() + ". Reason: " + paramCCLocationInfo.getStatusMsg(), 0);
      throw new FFSException(paramCCLocationInfo.getStatusCode(), paramCCLocationInfo.getStatusMsg());
    }
  }
  
  private CCEntryInfo[] a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString)
    throws FFSException
  {
    String str = "CashConAdapter.getUserEntries: ";
    FFSDebug.log(str + "start. LocationId: " + paramCCLocationInfo.getLocationId(), 6);
    ArrayList localArrayList = new ArrayList();
    int i;
    int j;
    if (this.f3 == true)
    {
      arrayOfCCEntryInfo1 = jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo, paramString);
      if (arrayOfCCEntryInfo1 != null)
      {
        i = arrayOfCCEntryInfo1.length;
        for (j = 0; j < i; j++) {
          if (arrayOfCCEntryInfo1[j].getEntryCategory().equals("PRENOTE_ENTRY") == true)
          {
            CashCon.updateEntryStatus(paramFFSConnectionHolder, "CANCELEDON", arrayOfCCEntryInfo1[j].getEntryId());
            arrayOfCCEntryInfo1[j].setStatus("CANCELEDON");
            a(paramFFSConnectionHolder, arrayOfCCEntryInfo1[j], "Server crashes last time. Cancel this entry first and the re-proess prenote.");
            if (paramString.equals("CONCENTRATION") == true) {
              a(paramFFSConnectionHolder, paramCCLocationInfo, null, null);
            } else {
              jdMethod_do(paramFFSConnectionHolder, paramCCLocationInfo, null, null);
            }
          }
          else if (arrayOfCCEntryInfo1[j].getEntryCategory().equals("ANTICIPATORY_ENTRY") == true)
          {
            CashCon.updateEntryStatus(paramFFSConnectionHolder, "CANCELEDON", arrayOfCCEntryInfo1[j].getEntryId());
            arrayOfCCEntryInfo1[j].setStatus("CANCELEDON");
            a(paramFFSConnectionHolder, arrayOfCCEntryInfo1[j], "Server crashes last time. Cancel this entry first and the re-proess anticipatory entry.");
          }
          else
          {
            localArrayList.add(arrayOfCCEntryInfo1[j]);
          }
        }
      }
    }
    CCEntryInfo[] arrayOfCCEntryInfo1 = jdMethod_do(paramFFSConnectionHolder, paramCCLocationInfo, paramString);
    if (arrayOfCCEntryInfo1 != null)
    {
      i = arrayOfCCEntryInfo1.length;
      for (j = 0; j < i; j++) {
        localArrayList.add(arrayOfCCEntryInfo1[j]);
      }
    }
    CCEntryInfo[] arrayOfCCEntryInfo2 = (CCEntryInfo[])localArrayList.toArray(new CCEntryInfo[0]);
    FFSDebug.log(str + "done. Total user entries: " + arrayOfCCEntryInfo2.length, 6);
    return arrayOfCCEntryInfo2;
  }
  
  private CCEntryInfo[] jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString)
    throws FFSException
  {
    return CashCon.getUnprocessedEntries(paramFFSConnectionHolder, paramCCLocationInfo.getLocationId(), this.fX, paramString);
  }
  
  private CCEntryInfo[] jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString)
    throws FFSException
  {
    return CashCon.getProcessedEntries(paramFFSConnectionHolder, paramCCLocationInfo.getLocationId(), this.fX, this.gd, paramString);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, long paramLong)
    throws FFSException
  {
    String str1 = "CashConAdapter.processAnticipatoryEntryForLocation: ";
    FFSDebug.log(str1 + "start. LocationId = " + paramCCLocationInfo.getLocationId(), 6);
    CCEntryInfo localCCEntryInfo = jdMethod_if(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, paramLong);
    if (localCCEntryInfo == null) {
      return;
    }
    localCCEntryInfo.setStatus("POSTEDON");
    localCCEntryInfo = CashCon.addCCEntryFromAdapter(paramFFSConnectionHolder, localCCEntryInfo);
    if (localCCEntryInfo.getStatusCode() != 0)
    {
      str2 = "Failed to create anticiaptory entry for batch. CompId: " + paramCCCompanyInfo.getCompId() + "; LocationId: " + paramCCLocationInfo.getLocationId() + ". Reason: " + localCCEntryInfo.getStatusMsg();
      FFSDebug.log(str1 + str2, 6);
      throw new FFSException(localCCEntryInfo.getStatusCode(), str2);
    }
    a(paramFFSConnectionHolder, localCCEntryInfo, "Successfully processed anticipatory CCEntry.");
    String str2 = paramCCCompanyInfo.getBatchType();
    if ((str2 != null) && (str2.equals("EntryBalancedBatch"))) {
      a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, localCCEntryInfo, paramArrayList);
    }
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private CCLocationInfo[] jdMethod_char(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    return CashCon.getCCLocationArrayByCompId(paramFFSConnectionHolder, paramString1, paramString2, this.f0);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, CCEntryInfo paramCCEntryInfo, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "CashConAdapter.createOffsetEntry: ";
    FFSDebug.log(str1 + "start. For entry " + paramCCEntryInfo.getEntryId(), 6);
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    BPWBankAcctInfo localBPWBankAcctInfo = null;
    String str2 = null;
    short s = 0;
    if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION"))
    {
      localCCEntryInfo = a(paramCCLocationInfo, "DISBURSEMENT", paramCCEntryInfo.getAmount(), "USER_ENTRY");
      str2 = paramCCLocationInfo.getConcentrateAcctId();
      localBPWBankAcctInfo = jdMethod_null(paramFFSConnectionHolder, str2);
      s = jdMethod_do(localBPWBankAcctInfo.getAcctType(), "DISBURSEMENT", "USER_ENTRY");
    }
    else
    {
      localCCEntryInfo = a(paramCCLocationInfo, "CONCENTRATION", paramCCEntryInfo.getAmount(), "USER_ENTRY");
      str2 = paramCCLocationInfo.getDisburseAcctId();
      localBPWBankAcctInfo = jdMethod_null(paramFFSConnectionHolder, str2);
      s = jdMethod_do(localBPWBankAcctInfo.getAcctType(), "CONCENTRATION", "USER_ENTRY");
    }
    ACHRecordInfo localACHRecordInfo = a(paramFFSConnectionHolder, localCCEntryInfo, paramCCCompanyInfo, localBPWBankAcctInfo, s);
    paramArrayList.add(localACHRecordInfo);
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, CCCompanyAcctInfo paramCCCompanyAcctInfo)
    throws FFSException
  {
    String str = "CashConAdapter.createOffsetEntryForBatch: ";
    FFSDebug.log(str + "start. CompId: " + paramCCCompanyInfo.getCompId(), 6);
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    short s = 0;
    BigDecimal localBigDecimal1 = this.f9.batchCache.batchCreditSum;
    BigDecimal localBigDecimal2 = this.f9.batchCache.batchDebitSum;
    if (localBigDecimal1.compareTo(localBigDecimal2) == 0) {
      return;
    }
    if (localBigDecimal1.compareTo(localBigDecimal2) > 0)
    {
      localCCEntryInfo.setAmount(localBigDecimal1.subtract(localBigDecimal2).toString());
      localCCEntryInfo.setTransactionType("CONCENTRATION");
      s = jdMethod_do(paramCCCompanyAcctInfo.getAcctType(), localCCEntryInfo.getTransactionType(), "USER_ENTRY");
    }
    else
    {
      localCCEntryInfo.setAmount(localBigDecimal2.subtract(localBigDecimal1).toString());
      localCCEntryInfo.setTransactionType("DISBURSEMENT");
      s = jdMethod_do(paramCCCompanyAcctInfo.getAcctType(), localCCEntryInfo.getTransactionType(), "USER_ENTRY");
    }
    ACHRecordInfo localACHRecordInfo = a(paramFFSConnectionHolder, localCCEntryInfo, paramCCCompanyInfo, paramCCCompanyAcctInfo, s);
    paramArrayList.add(localACHRecordInfo);
    FFSDebug.log(str + "done.", 6);
  }
  
  private void H()
    throws FFSException
  {
    this.f9.recCount += 1L;
    ACHAdapterUtil.writeFileControl(this.f1, this.f9, this.ga, this.f6);
  }
  
  private void I()
    throws Exception
  {
    File localFile = new File(this.f1);
    localFile.delete();
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCEntryInfo paramCCEntryInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "CashConAdapter.processOneEntry: ";
    FFSDebug.log(str1 + "start. EntryID: " + paramCCEntryInfo.getEntryId(), 6);
    short s = jdMethod_do(paramCCLocationInfo.getAccountType(), paramCCEntryInfo.getTransactionType(), paramCCEntryInfo.getEntryCategory());
    ACHRecordInfo localACHRecordInfo = a(paramCCLocationInfo, paramCCEntryInfo, s);
    paramArrayList.add(localACHRecordInfo);
    String str2 = paramCCCompanyInfo.getBatchType();
    if ((str2 != null) && (str2.equals("EntryBalancedBatch")) && (!paramCCEntryInfo.getEntryCategory().equals("PRENOTE_ENTRY"))) {
      a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramCCEntryInfo, paramArrayList);
    }
    if (!this.fY)
    {
      a(paramFFSConnectionHolder, paramCCEntryInfo);
      String str3 = "Successfully processed CCEntry";
      a(paramFFSConnectionHolder, paramCCEntryInfo, str3);
    }
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo)
    throws FFSException
  {
    paramCCEntryInfo.setLastProcessId(this.gd);
    paramCCEntryInfo.setProcessedTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm"));
    paramCCEntryInfo.setStatus("POSTEDON");
    CashCon.updateCCEntryInfoFromAdapter(paramFFSConnectionHolder, paramCCEntryInfo);
    if (paramCCEntryInfo.getStatusCode() != 0) {
      throw new FFSException(paramCCEntryInfo.getStatusCode(), paramCCEntryInfo.getStatusMsg());
    }
  }
  
  private short jdMethod_do(String paramString1, String paramString2, String paramString3)
  {
    short s = 0;
    if ((paramString1 != null) && (paramString2 != null)) {
      if (paramString1.equalsIgnoreCase("Checking"))
      {
        if (paramString2.equals("CONCENTRATION"))
        {
          if (paramString3.equals("PRENOTE_ENTRY")) {
            s = 28;
          } else {
            s = 27;
          }
        }
        else if (paramString3.equals("PRENOTE_ENTRY")) {
          s = 23;
        } else {
          s = 22;
        }
      }
      else if (paramString2.equals("CONCENTRATION"))
      {
        if (paramString3.equals("PRENOTE_ENTRY")) {
          s = 38;
        } else {
          s = 37;
        }
      }
      else if (paramString3.equals("PRENOTE_ENTRY")) {
        s = 33;
      } else {
        s = 32;
      }
    }
    return s;
  }
  
  private CCEntryInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, long paramLong)
    throws FFSException
  {
    String str1 = "CashConAdapter.createAnticipatoryEntry: ";
    FFSDebug.log(str1 + "start. LocationId: " + paramCCLocationInfo.getLocationId() + ". Anticipatory deposit amount: " + paramLong, 6);
    String str2 = new Long(paramLong).toString();
    CCEntryInfo localCCEntryInfo = null;
    localCCEntryInfo = a(paramCCLocationInfo, "CONCENTRATION", str2, "ANTICIPATORY_ENTRY");
    boolean bool = LimitCheckApprovalProcessor.checkEntitlementCCEntry(localCCEntryInfo, paramCCCompanyInfo.getCustomerId(), null);
    if (!bool)
    {
      FFSDebug.log(str1 + ": Failed entitlement check for anticipatory deposit entry", 6);
      return null;
    }
    ACHRecordInfo localACHRecordInfo = null;
    if (paramCCLocationInfo.getAccountType().equalsIgnoreCase("Checking")) {
      localACHRecordInfo = a(paramCCLocationInfo, localCCEntryInfo, (short)27);
    } else {
      localACHRecordInfo = a(paramCCLocationInfo, localCCEntryInfo, (short)37);
    }
    paramArrayList.add(localACHRecordInfo);
    FFSDebug.log(str1 + "done.", 6);
    return localCCEntryInfo;
  }
  
  private ACHRecordInfo a(CCLocationInfo paramCCLocationInfo, CCEntryInfo paramCCEntryInfo, short paramShort)
    throws FFSException
  {
    if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
      a(paramCCEntryInfo);
    } else {
      jdMethod_if(paramCCEntryInfo);
    }
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeCCDEntryDetailRecord localTypeCCDEntryDetailRecord = new TypeCCDEntryDetailRecord();
    localTypeCCDEntryDetailRecord.Receiving_Company_Name = BPWUtil.truncateString(paramCCLocationInfo.getLocationName(), 22);
    localTypeCCDEntryDetailRecord.Record_Type_Code = 6;
    localTypeCCDEntryDetailRecord.Transaction_Code = paramShort;
    localTypeCCDEntryDetailRecord.Receiving_DFI_Identification = paramCCLocationInfo.getBankRtn().substring(0, 8);
    localTypeCCDEntryDetailRecord.Check_Digit = BPWUtil.calculateCheckDigit(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    localTypeCCDEntryDetailRecord.DFI_Account_Number = paramCCLocationInfo.getAccountNum();
    localTypeCCDEntryDetailRecord.Amount = G(paramCCEntryInfo.getAmount());
    localTypeCCDEntryDetailRecord.Identification_Number = BPWUtil.truncateString(paramCCLocationInfo.getCCLocationId(), 15);
    localTypeCCDEntryDetailRecord.Identification_NumberExists = true;
    try
    {
      localTypeCCDEntryDetailRecord.Trace_Number = BPWUtil.composeTraceNum(this.gb.getFIRTN().substring(0, 8), FFSUtil.padWithChar(Long.toString(this.f9.fileEntryCount), 10, true, '0')).longValue();
    }
    catch (Exception localException) {}
    localACHRecordInfo.setRecord(localTypeCCDEntryDetailRecord);
    localACHRecordInfo.setRecordType(6);
    localACHRecordInfo.setClassCode("CCD");
    localACHRecordInfo.setAchVersion(this.f6);
    this.f9.batchCache.batchHash += G(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo, CCCompanyInfo paramCCCompanyInfo, BPWBankAcctInfo paramBPWBankAcctInfo, short paramShort)
    throws FFSException
  {
    if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
      a(paramCCEntryInfo);
    } else {
      jdMethod_if(paramCCEntryInfo);
    }
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeCCDEntryDetailRecord localTypeCCDEntryDetailRecord = new TypeCCDEntryDetailRecord();
    localTypeCCDEntryDetailRecord.Identification_Number = BPWUtil.truncateString(paramCCCompanyInfo.getCCCompId(), 15);
    localTypeCCDEntryDetailRecord.Identification_NumberExists = true;
    localTypeCCDEntryDetailRecord.Receiving_Company_Name = BPWUtil.truncateString(paramCCCompanyInfo.getCompName(), 22);
    localTypeCCDEntryDetailRecord.Record_Type_Code = 6;
    localTypeCCDEntryDetailRecord.Transaction_Code = paramShort;
    localTypeCCDEntryDetailRecord.DFI_Account_Number = paramBPWBankAcctInfo.getAcctNumber();
    localTypeCCDEntryDetailRecord.Receiving_DFI_Identification = paramBPWBankAcctInfo.getBankRTN().substring(0, 8);
    localTypeCCDEntryDetailRecord.Check_Digit = BPWUtil.calculateCheckDigit(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    localTypeCCDEntryDetailRecord.Amount = G(paramCCEntryInfo.getAmount());
    try
    {
      localTypeCCDEntryDetailRecord.Trace_Number = BPWUtil.composeTraceNum(this.gb.getFIRTN().substring(0, 8), FFSUtil.padWithChar(Long.toString(this.f9.fileEntryCount), 10, true, '0')).longValue();
    }
    catch (Exception localException) {}
    localACHRecordInfo.setRecord(localTypeCCDEntryDetailRecord);
    localACHRecordInfo.setRecordType(6);
    localACHRecordInfo.setClassCode("CCD");
    localACHRecordInfo.setAchVersion(this.f6);
    this.f9.batchCache.batchHash += G(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    return localACHRecordInfo;
  }
  
  private BPWBankAcctInfo jdMethod_null(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    BPWBankAcctInfo localBPWBankAcctInfo = BPWBankAcct.getBPWBankAcctInfoById(paramFFSConnectionHolder, paramString);
    if (localBPWBankAcctInfo.getStatusCode() != 0) {
      throw new FFSException(localBPWBankAcctInfo.getStatusCode(), localBPWBankAcctInfo.getStatusMsg());
    }
    return localBPWBankAcctInfo;
  }
  
  private void jdMethod_if(CCEntryInfo paramCCEntryInfo)
  {
    if (paramCCEntryInfo != null)
    {
      this.f9.recCount += 1L;
      this.f9.fileEntryCount += 1L;
      this.f9.batchCache.batchEntryCount += 1;
      this.f9.creditCount += 1L;
      this.f9.batchCache.creditCount += 1L;
      this.f9.fileCreditSum = this.f9.fileCreditSum.add(FFSUtil.getBigDecimal(paramCCEntryInfo.getAmount()));
      this.f9.batchCache.batchCreditSum = this.f9.batchCache.batchCreditSum.add(FFSUtil.getBigDecimal(paramCCEntryInfo.getAmount()));
    }
  }
  
  private long G(String paramString)
  {
    double d = Double.parseDouble(paramString);
    return new Double(d).longValue();
  }
  
  private void a(CCEntryInfo paramCCEntryInfo)
  {
    if (paramCCEntryInfo != null)
    {
      this.f9.recCount += 1L;
      this.f9.fileEntryCount += 1L;
      this.f9.batchCache.batchEntryCount += 1;
      this.f9.debitCount += 1L;
      this.f9.batchCache.debitCount += 1L;
      this.f9.fileDebitSum = this.f9.fileDebitSum.add(FFSUtil.getBigDecimal(paramCCEntryInfo.getAmount()));
      this.f9.batchCache.batchDebitSum = this.f9.batchCache.batchDebitSum.add(FFSUtil.getBigDecimal(paramCCEntryInfo.getAmount()));
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCCompanyInfo paramCCCompanyInfo, String paramString)
    throws FFSException
  {
    CCBatchHistInfo localCCBatchHistInfo = new CCBatchHistInfo();
    localCCBatchHistInfo.setBatchNumber(Integer.toString(this.f9.fileBatchCount));
    localCCBatchHistInfo.setCompId(paramCCCompanyInfo.getCompId());
    localCCBatchHistInfo.setEffectiveEntryDate(this.fX);
    localCCBatchHistInfo.setProcessId(this.gd);
    localCCBatchHistInfo.setSubmittedBy(paramCCCompanyInfo.getSubmittedBy());
    localCCBatchHistInfo.setLogId(paramCCCompanyInfo.getLogId());
    localCCBatchHistInfo.setNumberOfDeposits(new Long(this.f9.batchCache.debitCount).intValue());
    localCCBatchHistInfo.setNumberOfDisburses(new Long(this.f9.batchCache.creditCount).intValue());
    localCCBatchHistInfo.setTotalCreditAmount(this.f9.batchCache.batchCreditSum.toString());
    localCCBatchHistInfo.setTotalDebitAmount(this.f9.batchCache.batchDebitSum.toString());
    localCCBatchHistInfo.setTransactionType(paramString);
    localCCBatchHistInfo = CashCon.addCCBatchHistInfo(paramFFSConnectionHolder, localCCBatchHistInfo);
    if (localCCBatchHistInfo.getStatusCode() != 0) {
      throw new FFSException(localCCBatchHistInfo.getStatusCode(), localCCBatchHistInfo.getStatusMsg());
    }
    localCCBatchHistInfo.setStatusCode(0);
    localCCBatchHistInfo.setStatusMsg("Success");
  }
  
  private void jdMethod_long(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "CashConAdapter.getExportFileName: ";
    File localFile1 = new File(this.f1);
    String str2 = localFile1.getName();
    String str3 = this.f2 + str2;
    File localFile2 = new File(str3);
    if (localFile2.exists())
    {
      FFSDebug.log(str1 + " export ACH file exist: " + str3, 0);
      String str4 = this.f8 + str2 + ".ACH" + "." + String.valueOf(System.currentTimeMillis());
      File localFile3 = new File(str4);
      localFile2.renameTo(localFile3);
      FFSDebug.log(str1 + " the existing file has been moved to  " + str4, 0);
    }
    localFile1.renameTo(localFile2);
    ACHAdapterUtil.doFMLoggingForACH(paramFFSConnectionHolder, localFile2.getCanonicalPath(), str1);
    jdMethod_goto(paramFFSConnectionHolder, localFile2.getCanonicalPath());
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, String paramString1, String paramString2)
    throws FFSException
  {
    if (!this.fY)
    {
      CCEntryInfo[] arrayOfCCEntryInfo = a(paramFFSConnectionHolder, paramCCLocationInfo, paramString1);
      a(paramFFSConnectionHolder, paramCCLocationInfo, arrayOfCCEntryInfo, paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCEntryInfo[] paramArrayOfCCEntryInfo, String paramString1, String paramString2)
    throws FFSException
  {
    String str = "CashConAdapter.failUserEntriesForLocation: ";
    FFSDebug.log(str + "LocationId: " + paramCCLocationInfo.getLocationId() + ". Transaction type: " + paramString1, 6);
    if ((paramArrayOfCCEntryInfo != null) && (paramArrayOfCCEntryInfo.length > 0))
    {
      int i = paramArrayOfCCEntryInfo.length;
      for (int j = 0; j < i; j++) {
        a(paramFFSConnectionHolder, paramCCLocationInfo, paramArrayOfCCEntryInfo[j], paramString1, paramString2);
      }
    }
    FFSDebug.log(str + "done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCEntryInfo paramCCEntryInfo, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "CashConAdapter.failOneUserEntryForLocation: ";
    FFSDebug.log(str1 + "LocationId: " + paramCCLocationInfo.getLocationId() + ". Transaction type: " + paramString1, 6);
    paramCCEntryInfo.setProcessedTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm"));
    paramCCEntryInfo.setLastProcessId(this.gd);
    int i = LimitCheckApprovalProcessor.processCCEntryDelete(paramFFSConnectionHolder, paramCCEntryInfo, null);
    String str2 = "FAILEDON";
    if (i == 5) {
      str2 = "LIMIT_REVERT_FAILED";
    }
    paramCCEntryInfo.setStatus(str2);
    CashCon.updateCCEntryInfoFromAdapter(paramFFSConnectionHolder, paramCCEntryInfo);
    a(paramFFSConnectionHolder, paramCCEntryInfo, paramString2);
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, CCEntryInfo[] paramArrayOfCCEntryInfo)
    throws FFSException
  {
    String str = "CashConAdapter.processDepositEntriesWithThresholdAmount: ";
    FFSDebug.log(str + "start. LocationID: " + paramCCLocationInfo.getLocationId() + ". Threshold Deposit Amount: " + paramCCLocationInfo.getThresholdDeposAmt(), 6);
    long l = a(paramArrayOfCCEntryInfo);
    if (l >= paramCCLocationInfo.getThresholdDeposAmtLong())
    {
      a(paramFFSConnectionHolder, l, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList);
      if (!this.fY) {
        a(paramFFSConnectionHolder, paramArrayOfCCEntryInfo);
      }
    }
    else if (this.fY == true)
    {
      a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList, "CONCENTRATION", paramArrayOfCCEntryInfo);
    }
    FFSDebug.log(str + "done.", 6);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList, CCEntryInfo[] paramArrayOfCCEntryInfo)
    throws FFSException
  {
    String str = "CashConAdapter.processDepositEntriesWithConsolidateDeposit: ";
    FFSDebug.log(str + "start. LocationID: " + paramCCLocationInfo.getLocationId() + ". Consolidate Deposit Flag On: " + paramCCLocationInfo.getConsolidateDepos(), 6);
    long l = a(paramArrayOfCCEntryInfo);
    a(paramFFSConnectionHolder, l, paramCCLocationInfo, paramCCCompanyInfo, paramArrayList);
    if (!this.fY) {
      a(paramFFSConnectionHolder, paramArrayOfCCEntryInfo);
    }
    FFSDebug.log(str + "done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, long paramLong, CCLocationInfo paramCCLocationInfo, CCCompanyInfo paramCCCompanyInfo, ArrayList paramArrayList)
    throws FFSException
  {
    CCEntryInfo localCCEntryInfo = new CCEntryInfo();
    localCCEntryInfo.setAmount(Long.toString(paramLong));
    localCCEntryInfo.setLocationId(paramCCLocationInfo.getLocationId());
    localCCEntryInfo.setTransactionType("CONCENTRATION");
    localCCEntryInfo.setEntryCategory("USER_ENTRY");
    short s = jdMethod_do(paramCCLocationInfo.getAccountType(), localCCEntryInfo.getTransactionType(), localCCEntryInfo.getEntryCategory());
    ACHRecordInfo localACHRecordInfo = a(paramCCLocationInfo, localCCEntryInfo, s);
    paramArrayList.add(localACHRecordInfo);
    String str = paramCCCompanyInfo.getBatchType();
    if ((str != null) && (str.equals("EntryBalancedBatch"))) {
      a(paramFFSConnectionHolder, paramCCLocationInfo, paramCCCompanyInfo, localCCEntryInfo, paramArrayList);
    }
  }
  
  private long a(CCEntryInfo[] paramArrayOfCCEntryInfo)
  {
    long l = 0L;
    if (paramArrayOfCCEntryInfo != null)
    {
      int i = paramArrayOfCCEntryInfo.length;
      for (int j = 0; j < i; j++) {
        l += G(paramArrayOfCCEntryInfo[j].getAmount());
      }
    }
    return l;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo[] paramArrayOfCCEntryInfo)
    throws FFSException
  {
    if (paramArrayOfCCEntryInfo != null)
    {
      int i = paramArrayOfCCEntryInfo.length;
      for (int j = 0; j < i; j++)
      {
        a(paramFFSConnectionHolder, paramArrayOfCCEntryInfo[j]);
        a(paramFFSConnectionHolder, paramArrayOfCCEntryInfo[j], "Successfully processed CCEntry");
      }
    }
  }
  
  private void jdMethod_goto(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    if (this.fY) {
      return;
    }
    ScheduleHist localScheduleHist = null;
    try
    {
      localScheduleHist = ScheduleHistory.getLastScheduleHist(this.gc.getFIId(), "CASHCONTRN");
      localScheduleHist.FileName = paramString;
      localScheduleHist.EventType = "ProcessingFileGenerated";
      ScheduleHistory.createScheduleHist(paramFFSConnectionHolder, localScheduleHist);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log("*** CashConAdapter.logScheduleHist: exception:" + str, 6);
      return;
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CCEntryInfo paramCCEntryInfo, String paramString)
    throws FFSException
  {
    if (this.f4 > 3)
    {
      int i = 3610;
      if (paramCCEntryInfo.getStatus().equals("POSTEDON") == true)
      {
        if (paramCCEntryInfo.getTransactionType().equals("CONCENTRATION")) {
          i = 3610;
        } else {
          i = 3609;
        }
      }
      else {
        i = 5350;
      }
      CashCon.logCCEntryTransAuditLog(paramFFSConnectionHolder, paramCCEntryInfo, paramString, i);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.achadapter.CashConAdapter
 * JD-Core Version:    0.7.0.1
 */