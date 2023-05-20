package com.ffusion.ffs.bpw.fulfill.achadapter;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.MacGenerator;
import com.ffusion.ffs.bpw.db.ACHFI;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.ExternalTransferCompany;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHEntryInfo;
import com.ffusion.ffs.bpw.interfaces.ETFACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.IExternalTransferAdapter;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.ACHAdapterConsts;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDEntryDetailRecord;
import com.ffusion.util.logging.AuditLogRecord;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ExternalTransferAdapter
  implements IExternalTransferAdapter, ACHAdapterConsts
{
  private int fL;
  ACHFileCache fQ = null;
  private String fI;
  private String fM;
  private String fP;
  private String fF;
  private ACHFIInfo fU;
  private CutOffInfo fO;
  private BPWFIInfo fT;
  private String fV;
  private ACHAgent fR = null;
  private String fN = "MsgSet";
  private String fG = null;
  private int fE = 0;
  private ArrayList fH;
  private String fC = null;
  private boolean fD = false;
  private boolean fJ = false;
  private String fW = null;
  private ACHRecordInfo fS = null;
  private String fK = null;
  
  public void start()
    throws Exception
  {
    String str1 = "InterAdapter.start: ";
    FFSDebug.log(str1 + "is called", 6);
    String str2 = ACHAdapterUtil.getProperty("ach.export.dir", "export");
    this.fI = ACHAdapterUtil.getFileNameBase(str2);
    String str3 = this.fI + "temp";
    this.fM = ACHAdapterUtil.getFileNameBase(str3);
    String str4 = ACHAdapterUtil.getProperty("ach.error.dir", "error");
    this.fP = ACHAdapterUtil.getFileNameBase(str4);
    this.fQ = new ACHFileCache();
    this.fN = ACHAdapterUtil.getProperty("ach.version", "MsgSet");
    this.fR = ((ACHAgent)FFSRegistry.lookup("BPWACHAGENT"));
    this.fG = ACHAdapterUtil.getProperty("bpw.externalTransfer.fileExtension", "ETF");
    if (this.fR == null)
    {
      FFSDebug.log("InterAdapter.start: ACHAgent has not been started! Terminating process!", 0);
      throw new FFSException("InterAdapter.start: ACHAgent has not been started! Terminating process!");
    }
    this.fE = ACHAdapterUtil.getPropertyInt("BatchSize", 1000);
    this.fE = 1;
    this.fH = new ArrayList();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.fL = localPropertyConfig.LogLevel;
    FFSDebug.log(str1 + "successful", 6);
  }
  
  public void processOneCutOff(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    String str1 = "ExternalTransferAdapter.processOneCutOff: ";
    FFSDebug.log(str1 + "start. CutOffId = " + paramCutOffInfo.getCutOffId() + ". FIID = " + paramString1, 6);
    ETFACHFileInfo localETFACHFileInfo = null;
    this.fO = paramCutOffInfo;
    this.fV = paramString2;
    this.fD = paramBoolean2;
    this.fJ = paramBoolean3;
    this.fW = paramString1;
    this.fU = jdMethod_else(paramFFSConnectionHolder, paramString1);
    this.fT = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramString1);
    this.fF = a(this.fU);
    FFSDebug.log(str1 + "crashRecovery" + paramBoolean3, 6);
    if (paramBoolean3 == true) {
      try
      {
        FFSDebug.log(str1 + "Deleting ETFACHFile by processId=" + this.fV, 6);
        Transfer.deleteETFACHFileByProcessId(paramFFSConnectionHolder, this.fV);
      }
      catch (FFSException localFFSException1)
      {
        FFSDebug.log(str1 + "failed to delete ETF ACHFile record " + localFFSException1.toString(), 0);
        throw localFFSException1;
      }
    }
    FFSDebug.log(str1 + "reRunCutOff" + paramBoolean2, 6);
    if (!paramBoolean2)
    {
      localETFACHFileInfo = new ETFACHFileInfo();
      localETFACHFileInfo.setCreateDate(DBUtil.getCurrentLogDate());
      localETFACHFileInfo.setProcessId(this.fV);
      localETFACHFileInfo.setFileStatus("PROCESSING");
      int i = 0;
      if (this.fM != null) {
        i = this.fM.length();
      }
      localObject = this.fF.substring(i);
      localETFACHFileInfo.setExportFileName((String)localObject);
      try
      {
        FFSDebug.log(str1 + "adding ETFACHFile", 6);
        localETFACHFileInfo = Transfer.addETFACHFile(paramFFSConnectionHolder, localETFACHFileInfo);
        this.fK = localETFACHFileInfo.getFileId();
      }
      catch (FFSException localFFSException2)
      {
        FFSDebug.log(str1 + "failed to create ETF ACHFile record " + localFFSException2.toString(), 0);
        throw localFFSException2;
      }
    }
    String str2 = "0";
    Object localObject = ExternalTransferCompany.getExtTransferCompArrayByFIId(paramFFSConnectionHolder, paramString1, str2, this.fE);
    FFSDebug.log(str1 + "extCompList.length=" + localObject.length, 0);
    while (localObject != null)
    {
      int j = localObject.length;
      if (j == 0) {
        break;
      }
      for (int k = 0; k < j; k++)
      {
        try
        {
          a(paramFFSConnectionHolder, localObject[k]);
        }
        catch (FFSException localFFSException5)
        {
          FFSDebug.log(str1 + "failed to process customer " + localObject[k].getCustomerId() + ". Error: " + localFFSException5.toString(), 0);
          throw localFFSException5;
        }
        a(paramFFSConnectionHolder, this.fH, localObject[k].getCustomerId(), paramBoolean2);
        this.fH = new ArrayList();
        paramFFSConnectionHolder.conn.commit();
      }
      if (j < this.fE) {
        break;
      }
      str2 = localObject[(j - 1)].getCompId();
      localObject = ExternalTransferCompany.getExtTransferCompArrayByFIId(paramFFSConnectionHolder, paramString1, str2, this.fE);
    }
    if ((paramBoolean1 == true) || (this.fQ.fileBatchCount > 0))
    {
      F();
      jdMethod_goto(paramFFSConnectionHolder);
      if (!paramBoolean2)
      {
        localETFACHFileInfo.setBatchCount(String.valueOf(this.fQ.fileBatchCount));
        localETFACHFileInfo.setRecordCount(String.valueOf(this.fQ.fileEntryCount));
        localETFACHFileInfo.setTotalDebits(String.valueOf(this.fQ.fileDebitSum));
        localETFACHFileInfo.setNumberOfDebits(String.valueOf(this.fQ.debitCount));
        localETFACHFileInfo.setTotalCredits(String.valueOf(this.fQ.fileCreditSum));
        localETFACHFileInfo.setNumberOfCredits(String.valueOf(this.fQ.creditCount));
        localETFACHFileInfo.setFileStatus("POSTEDON");
        try
        {
          FFSDebug.log(str1 + "updating ETFACHFile", 6);
          localETFACHFileInfo = Transfer.updateETFACHFileWithCtrlInfo(paramFFSConnectionHolder, localETFACHFileInfo);
        }
        catch (FFSException localFFSException3)
        {
          FFSDebug.log(str1 + "failed to update ETF ACHFile record " + localFFSException3.toString(), 0);
          throw localFFSException3;
        }
      }
    }
    else
    {
      G();
    }
    if (this.fQ.fileBatchCount == 0) {
      try
      {
        FFSDebug.log(str1 + "deleting blank  ETFACHFile", 6);
        localETFACHFileInfo = Transfer.deleteETFACHFile(paramFFSConnectionHolder, localETFACHFileInfo);
      }
      catch (FFSException localFFSException4)
      {
        FFSDebug.log(str1 + "failed to delete ETF ACHFile record " + localFFSException4.toString(), 0);
        throw localFFSException4;
      }
    }
    FFSDebug.log(str1 + "done.", 6);
  }
  
  private void a(CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    if (this.fD == true) {
      this.fC = FFSUtil.getDateString("yyyyMMdd");
    } else {
      this.fC = BPWUtil.getDateInNewFormat(this.fO.getNextProcessTime(), "yyyy/MM/dd HH:mm", "yyyyMMdd");
    }
    int i = Integer.parseInt(this.fC);
    int j = Transfer.getBatchLeadDays(paramCustomerInfo);
    for (int k = 0; k < j; k++) {
      try
      {
        i = SmartCalendar.getACHBusinessDay(this.fW, i, true);
      }
      catch (Exception localException)
      {
        String str = FFSDebug.stackTrace(localException);
        FFSDebug.log(str, 0);
        throw new FFSException(localException, str);
      }
    }
    i *= 100;
    this.fC = Integer.toString(i);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferCompanyInfo paramExtTransferCompanyInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAdapter.processOneCustomer: ";
    String str2 = paramExtTransferCompanyInfo.getCustomerId();
    FFSDebug.log(str1 + "start, CustomerId =" + str2, 6);
    FFSDebug.log(str1 + " External CompanyACHId : " + paramExtTransferCompanyInfo.getCompACHId(), 6);
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(str2, paramFFSConnectionHolder, paramExtTransferCompanyInfo);
    if (localCustomerInfo == null)
    {
      String str3 = BPWLocaleUtil.getMessage(19130, new String[] { str2 }, "TRANSFER_MESSAGE");
      throw new FFSException(19130, str3);
    }
    FFSDebug.log(str1 + " customerId : " + localCustomerInfo.customerID, 6);
    a(paramFFSConnectionHolder, localCustomerInfo, paramExtTransferCompanyInfo, "BUSINESS");
    a(paramFFSConnectionHolder, localCustomerInfo, paramExtTransferCompanyInfo, "PERSONAL");
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CustomerInfo paramCustomerInfo, ExtTransferCompanyInfo paramExtTransferCompanyInfo, String paramString)
    throws FFSException
  {
    String str = "ExternalTransferAdapter.processOneBatch: ";
    FFSDebug.log(str + "start, recipientType =" + paramString, 6);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramCustomerInfo.customerID;
    ExtTransferAcctList localExtTransferAcctList = new ExtTransferAcctList();
    localExtTransferAcctList.setCustomerId(arrayOfString);
    localExtTransferAcctList.setRecipientType(paramString);
    localExtTransferAcctList = ExternalTransferAccount.getExternalTransferAccountList(paramFFSConnectionHolder, localExtTransferAcctList);
    if (localExtTransferAcctList.getStatusCode() != 0)
    {
      FFSDebug.log(str + ": no external accounts found for this customer: customerId=" + paramCustomerInfo.customerID, 6);
      return;
    }
    ExtTransferAcctInfo[] arrayOfExtTransferAcctInfo = localExtTransferAcctList.getExtTransferAccts();
    FFSDebug.log(str + "extAcctInfos length=" + arrayOfExtTransferAcctInfo.length, 6);
    this.fQ.batchHeaderFound = false;
    this.fQ.batchCtrlFound = false;
    if (arrayOfExtTransferAcctInfo != null) {
      for (int i = 0; i < arrayOfExtTransferAcctInfo.length; i++) {
        if (!this.fD) {
          jdMethod_if(paramFFSConnectionHolder, paramCustomerInfo, paramExtTransferCompanyInfo, paramString, arrayOfExtTransferAcctInfo[i]);
        } else {
          a(paramFFSConnectionHolder, paramCustomerInfo, paramExtTransferCompanyInfo, paramString, arrayOfExtTransferAcctInfo[i]);
        }
      }
    }
    if (this.fQ.batchHeaderFound == true)
    {
      ACHRecordInfo localACHRecordInfo = a(paramExtTransferCompanyInfo, this.fS);
      this.fH.add(localACHRecordInfo);
      this.fQ.batchCtrlFound = true;
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, CustomerInfo paramCustomerInfo, ExtTransferCompanyInfo paramExtTransferCompanyInfo, String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAdapter.processOneAccount: ";
    FFSDebug.log(str1 + "start, accountId =" + paramExtTransferAcctInfo.getAcctId(), 6);
    a(paramCustomerInfo);
    TransferInfo[] arrayOfTransferInfo = a(paramFFSConnectionHolder, paramCustomerInfo.customerID, paramExtTransferAcctInfo);
    if (jdMethod_do(paramExtTransferAcctInfo) == true) {
      a(paramFFSConnectionHolder, paramExtTransferAcctInfo, paramExtTransferCompanyInfo, paramString, paramCustomerInfo);
    }
    if (!a(paramExtTransferAcctInfo))
    {
      FFSDebug.log(str1 + ": Prenote is not mature, fail transfers. ", 6);
      String str2 = "Failed to process Transfer because its account's prenote has not matured.";
      a(paramFFSConnectionHolder, arrayOfTransferInfo, str2);
    }
    else
    {
      a(paramFFSConnectionHolder, paramCustomerInfo, paramExtTransferCompanyInfo, paramString, paramExtTransferAcctInfo, arrayOfTransferInfo);
    }
    FFSDebug.log(str1 + "done. Current number of transfers: " + this.fH.size(), 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CustomerInfo paramCustomerInfo, ExtTransferCompanyInfo paramExtTransferCompanyInfo, String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str = "ExternalTransferAdapter.processOneAccountRerunCutOff: ";
    FFSDebug.log(str + "start, accountId =" + paramExtTransferAcctInfo.getAcctId(), 6);
    TransferInfo[] arrayOfTransferInfo = Transfer.getProcessedTransfersForAccount(paramFFSConnectionHolder, paramExtTransferAcctInfo.getAcctId(), this.fV);
    a(paramFFSConnectionHolder, paramCustomerInfo, paramExtTransferCompanyInfo, paramString, paramExtTransferAcctInfo, arrayOfTransferInfo);
    FFSDebug.log(str + "done. Current number of transfers: " + this.fH.size(), 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CustomerInfo paramCustomerInfo, ExtTransferCompanyInfo paramExtTransferCompanyInfo, String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo, TransferInfo[] paramArrayOfTransferInfo)
    throws FFSException
  {
    String str = "ExternalTransferAdapter.processUserTransfers: ";
    FFSDebug.log(str + "start, accountId =" + paramExtTransferAcctInfo.getAcctId(), 6);
    if (paramArrayOfTransferInfo == null) {
      return;
    }
    int i = paramArrayOfTransferInfo.length;
    FFSDebug.log(str + "start, len =" + i, 6);
    for (int j = 0; j < i; j++)
    {
      TransferInfo localTransferInfo = paramArrayOfTransferInfo[j];
      if (a(paramFFSConnectionHolder, localTransferInfo, paramCustomerInfo))
      {
        localTransferInfo.setAccountToNum(paramExtTransferAcctInfo.getAcctNum());
        localTransferInfo.setAccountToType(paramExtTransferAcctInfo.getAcctType());
        if (!this.fQ.batchHeaderFound)
        {
          this.fS = a(paramExtTransferCompanyInfo, paramString);
          this.fH.add(this.fS);
          this.fQ.batchHeaderFound = true;
        }
        a(paramFFSConnectionHolder, paramCustomerInfo, localTransferInfo, paramExtTransferAcctInfo);
      }
    }
  }
  
  private boolean jdMethod_do(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    String str1 = "ExternalTransferAdapter.needToProcessPrenote: ";
    String str2 = paramExtTransferAcctInfo.getPrenoteStatus();
    FFSDebug.log(str1, " prenote=", paramExtTransferAcctInfo.getPrenote(), 6);
    FFSDebug.log(str1, " prenoteStatus=", str2, 6);
    if ((paramExtTransferAcctInfo.getPrenote() != null) && (paramExtTransferAcctInfo.getPrenote().trim().equalsIgnoreCase("Y"))) {
      return str2 == null;
    }
    return false;
  }
  
  private boolean a(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    String str1 = "ExternalTransferAdapter.isPrenoteMatured: ";
    String str2 = paramExtTransferAcctInfo.getPrenoteStatus();
    FFSDebug.log(str1, " prenote=", paramExtTransferAcctInfo.getPrenote(), 6);
    FFSDebug.log(str1, " prenoteStatus=", str2, 6);
    return (paramExtTransferAcctInfo.getPrenote() == null) || (!paramExtTransferAcctInfo.getPrenote().trim().equalsIgnoreCase("Y")) || ((str2 != null) && (str2.equalsIgnoreCase("Success")));
  }
  
  private TransferInfo[] a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    String str1 = "ExternalTransferAdapter.getTransfersForAccount: ";
    String str2 = paramExtTransferAcctInfo.getAcctId();
    FFSDebug.log(str1 + "start. AccountId: " + paramExtTransferAcctInfo.getAcctId(), 6);
    ArrayList localArrayList = new ArrayList();
    int i;
    int j;
    if (this.fJ == true)
    {
      arrayOfTransferInfo1 = Transfer.getProcessedTransfersForAccount(paramFFSConnectionHolder, str2, this.fV);
      if (arrayOfTransferInfo1 != null)
      {
        i = arrayOfTransferInfo1.length;
        for (j = 0; j < i; j++) {
          if (arrayOfTransferInfo1[j].getTransferCategory().equals("PRENOTE_ENTRY") == true)
          {
            arrayOfTransferInfo1[j].setPrcStatus("CANCELEDON");
            Transfer.updateStatus(paramFFSConnectionHolder, arrayOfTransferInfo1[j], false);
            a(paramFFSConnectionHolder, arrayOfTransferInfo1[j], "Server crashes last time. Cancel this entry first and the re-proess prenote.");
            a(paramFFSConnectionHolder, paramExtTransferAcctInfo, null, null);
          }
          else
          {
            localArrayList.add(arrayOfTransferInfo1[j]);
          }
        }
      }
    }
    TransferInfo[] arrayOfTransferInfo1 = Transfer.getUnprocessedTransfersForAcct(paramFFSConnectionHolder, str2, paramString, this.fC);
    if (arrayOfTransferInfo1 != null)
    {
      i = arrayOfTransferInfo1.length;
      for (j = 0; j < i; j++) {
        localArrayList.add(arrayOfTransferInfo1[j]);
      }
    }
    TransferInfo[] arrayOfTransferInfo2 = (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
    FFSDebug.log(str1 + "done. Total user entries: " + arrayOfTransferInfo2.length, 6);
    return arrayOfTransferInfo2;
  }
  
  public void processRunNow(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    String str = "ExternalTransferAdapter.processRunNow: ";
    FFSDebug.log(str + "start. FIID = " + paramString1, 6);
    this.fV = paramString2;
    this.fD = paramBoolean2;
    this.fJ = paramBoolean3;
    this.fU = jdMethod_else(paramFFSConnectionHolder, paramString1);
    this.fT = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramString1);
    this.fF = a(this.fU);
    this.fO = new CutOffInfo();
    this.fO.setFIId(paramString1);
    this.fO.setInstructionType("ETFTRN");
    this.fO.setProcessTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm"));
    this.fO.setNextProcessTime(FFSUtil.getDateString("yyyy/MM/dd HH:mm"));
    processOneCutOff(paramFFSConnectionHolder, this.fO, paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3);
    FFSDebug.log(str + "done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, ExtTransferCompanyInfo paramExtTransferCompanyInfo, String paramString, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = null;
    if (!this.fQ.batchHeaderFound)
    {
      this.fS = a(paramExtTransferCompanyInfo, paramString);
      TypeBatchHeaderRecord localTypeBatchHeaderRecord = (TypeBatchHeaderRecord)this.fS.getRecord();
      localTypeBatchHeaderRecord.Service_Class_Code = 220;
      this.fH.add(this.fS);
      this.fQ.batchHeaderFound = true;
    }
    if (paramString.compareTo("BUSINESS") == 0) {
      localACHRecordInfo = a(paramFFSConnectionHolder, paramExtTransferAcctInfo, paramCustomerInfo);
    } else {
      localACHRecordInfo = jdMethod_do(paramFFSConnectionHolder, paramExtTransferAcctInfo, paramCustomerInfo);
    }
    this.fH.add(localACHRecordInfo);
    a(paramFFSConnectionHolder, paramExtTransferAcctInfo, "Pending", FFSUtil.getDateString("yyyy/MM/dd HH:mm:ss"));
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, String paramString1, String paramString2)
    throws FFSException
  {
    paramExtTransferAcctInfo.setPrenoteStatus(paramString1);
    paramExtTransferAcctInfo.setPrenoteSubDate(paramString2);
    paramExtTransferAcctInfo = ExternalTransferAccount.modify(paramFFSConnectionHolder, paramExtTransferAcctInfo);
  }
  
  private ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    TransferInfo localTransferInfo = jdMethod_if(paramFFSConnectionHolder, paramExtTransferAcctInfo, paramCustomerInfo);
    short s = jdMethod_if(paramExtTransferAcctInfo);
    ACHRecordInfo localACHRecordInfo = jdMethod_if(localTransferInfo, paramExtTransferAcctInfo, s);
    return localACHRecordInfo;
  }
  
  private short jdMethod_if(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    short s = 0;
    String str = paramExtTransferAcctInfo.getAcctType();
    if ((str != null) && (str.equalsIgnoreCase("Checking"))) {
      s = 23;
    } else {
      s = 33;
    }
    return s;
  }
  
  private ACHRecordInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    TransferInfo localTransferInfo = jdMethod_if(paramFFSConnectionHolder, paramExtTransferAcctInfo, paramCustomerInfo);
    short s = 0;
    String str = paramExtTransferAcctInfo.getAcctType();
    if ((str != null) && (str.equalsIgnoreCase("Checking"))) {
      s = 23;
    } else {
      s = 33;
    }
    ACHRecordInfo localACHRecordInfo = a(localTransferInfo, paramExtTransferAcctInfo, s);
    return localACHRecordInfo;
  }
  
  private TransferInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    TransferInfo localTransferInfo = new TransferInfo();
    localTransferInfo.setAmount("0");
    localTransferInfo.setCustomerId(paramExtTransferAcctInfo.getCustomerId());
    localTransferInfo.setDateCreate(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setDateDue(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setDateToPost(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setExternalAcctId(paramExtTransferAcctInfo.getAcctId());
    localTransferInfo.setLastProcessId(this.fV);
    localTransferInfo.setLogId(paramExtTransferAcctInfo.getLogId());
    localTransferInfo.setSubmittedBy(paramExtTransferAcctInfo.getSubmittedBy());
    localTransferInfo.setTransferType("Current");
    localTransferInfo.setTransferCategory("PRENOTE_ENTRY");
    localTransferInfo.setTransferDest("INTER");
    localTransferInfo.setBankFromRtn(this.fT.getFIRTN());
    localTransferInfo.setAccountFromNum("PrenoteAccount");
    localTransferInfo.setAccountFromType(paramExtTransferAcctInfo.getAcctType());
    localTransferInfo.setPrcStatus("POSTEDON");
    localTransferInfo = Transfer.addTransferFromAdapter(paramFFSConnectionHolder, localTransferInfo, false, this.fT, paramCustomerInfo);
    if (localTransferInfo.getStatusCode() != 0) {
      throw new FFSException(localTransferInfo.getStatusCode(), localTransferInfo.getStatusMsg());
    }
    a(paramFFSConnectionHolder, localTransferInfo, "Successfully created prenote entry for this external accounts.");
    return localTransferInfo;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, CustomerInfo paramCustomerInfo, TransferInfo paramTransferInfo, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo1 = null;
    ACHRecordInfo localACHRecordInfo2 = null;
    String str1 = null;
    String str2 = paramTransferInfo.getTransferCategory();
    short s1 = jdMethod_for(paramExtTransferAcctInfo.getAcctType(), str2);
    short s2 = 0;
    if (paramExtTransferAcctInfo.getAcctType().equalsIgnoreCase("Checking")) {
      s2 = 27;
    } else {
      s2 = 37;
    }
    if (paramExtTransferAcctInfo.getRecipientType().equals("BUSINESS"))
    {
      localACHRecordInfo1 = jdMethod_if(paramTransferInfo, paramExtTransferAcctInfo, s1);
      str1 = paramTransferInfo.getMemo();
      if ((str1 != null) && (str1.trim().length() > 0)) {
        jdMethod_if(paramTransferInfo, localACHRecordInfo1);
      }
      if (!str2.equals("PRENOTE_ENTRY")) {
        localACHRecordInfo2 = a(paramCustomerInfo, paramTransferInfo, s2);
      }
    }
    else
    {
      localACHRecordInfo1 = a(paramTransferInfo, paramExtTransferAcctInfo, s1);
      str1 = paramTransferInfo.getMemo();
      if ((str1 != null) && (str1.trim().length() > 0)) {
        a(paramTransferInfo, localACHRecordInfo1);
      }
      if (!str2.equals("PRENOTE_ENTRY")) {
        localACHRecordInfo2 = jdMethod_if(paramCustomerInfo, paramTransferInfo, s2);
      }
    }
    this.fH.add(localACHRecordInfo1);
    if (!str2.equals("PRENOTE_ENTRY"))
    {
      this.fH.add(localACHRecordInfo2);
      TypeBatchHeaderRecord localTypeBatchHeaderRecord = (TypeBatchHeaderRecord)this.fS.getRecord();
      localTypeBatchHeaderRecord.Service_Class_Code = 200;
    }
    if (!this.fD)
    {
      paramTransferInfo.setLastProcessId(this.fV);
      paramTransferInfo.setDatePosted(FFSUtil.getDateString());
      paramTransferInfo.setPrcStatus("POSTEDON");
      paramTransferInfo = Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, paramTransferInfo);
      if (paramTransferInfo.getStatusCode() != 0) {
        throw new FFSException(paramTransferInfo.getStatusCode(), paramTransferInfo.getStatusMsg());
      }
      a(paramFFSConnectionHolder, paramTransferInfo, "Transfer processed");
    }
  }
  
  private ACHRecordInfo jdMethod_if(TransferInfo paramTransferInfo, ExtTransferAcctInfo paramExtTransferAcctInfo, short paramShort)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeCCDEntryDetailRecord localTypeCCDEntryDetailRecord = new TypeCCDEntryDetailRecord();
    localTypeCCDEntryDetailRecord.Identification_Number = BPWUtil.truncateString(paramExtTransferAcctInfo.getRecipientId(), 15);
    localTypeCCDEntryDetailRecord.Identification_NumberExists = true;
    localTypeCCDEntryDetailRecord.Receiving_Company_Name = BPWUtil.truncateString(paramExtTransferAcctInfo.getRecipientName(), 22);
    localTypeCCDEntryDetailRecord.Record_Type_Code = 6;
    localTypeCCDEntryDetailRecord.Transaction_Code = paramShort;
    localTypeCCDEntryDetailRecord.Receiving_DFI_Identification = paramExtTransferAcctInfo.getAcctBankRtn().substring(0, 8);
    localTypeCCDEntryDetailRecord.Check_Digit = BPWUtil.calculateCheckDigit(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    localTypeCCDEntryDetailRecord.DFI_Account_Number = paramExtTransferAcctInfo.getAcctNum17();
    localTypeCCDEntryDetailRecord.Amount = F(paramTransferInfo.getAmount());
    try
    {
      localTypeCCDEntryDetailRecord.Trace_Number = BPWUtil.composeTraceNum(this.fT.getFIRTN().substring(0, 8), FFSUtil.padWithChar(Long.toString(this.fQ.fileEntryCount), 10, true, '0')).longValue();
    }
    catch (Exception localException) {}
    localACHRecordInfo.setRecord(localTypeCCDEntryDetailRecord);
    localACHRecordInfo.setRecordType(6);
    localACHRecordInfo.setClassCode("CCD");
    localACHRecordInfo.setAchVersion(this.fN);
    localACHRecordInfo.setLogId(paramTransferInfo.getLogId());
    this.fQ.batchCache.batchHash += Long.parseLong(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    jdMethod_long(paramTransferInfo);
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo a(CustomerInfo paramCustomerInfo, TransferInfo paramTransferInfo, short paramShort)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeCCDEntryDetailRecord localTypeCCDEntryDetailRecord = new TypeCCDEntryDetailRecord();
    localTypeCCDEntryDetailRecord.Identification_Number = "Balanced";
    localTypeCCDEntryDetailRecord.Identification_NumberExists = true;
    localTypeCCDEntryDetailRecord.Receiving_Company_Name = BPWUtil.truncateString(paramCustomerInfo.firstName, 22);
    localTypeCCDEntryDetailRecord.Record_Type_Code = 6;
    localTypeCCDEntryDetailRecord.Transaction_Code = paramShort;
    localTypeCCDEntryDetailRecord.Receiving_DFI_Identification = paramTransferInfo.getBankFromRtn().substring(0, 8);
    localTypeCCDEntryDetailRecord.Check_Digit = BPWUtil.calculateCheckDigit(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    localTypeCCDEntryDetailRecord.DFI_Account_Number = paramTransferInfo.getAccountFromNum17();
    localTypeCCDEntryDetailRecord.Amount = F(paramTransferInfo.getAmount());
    try
    {
      localTypeCCDEntryDetailRecord.Trace_Number = BPWUtil.composeTraceNum(this.fT.getFIRTN().substring(0, 8), FFSUtil.padWithChar(Long.toString(this.fQ.fileEntryCount), 10, true, '0')).longValue();
    }
    catch (Exception localException) {}
    localACHRecordInfo.setRecord(localTypeCCDEntryDetailRecord);
    localACHRecordInfo.setRecordType(6);
    localACHRecordInfo.setClassCode("CCD");
    localACHRecordInfo.setAchVersion(this.fN);
    localACHRecordInfo.setLogId(paramTransferInfo.getLogId());
    this.fQ.batchCache.batchHash += Long.parseLong(localTypeCCDEntryDetailRecord.Receiving_DFI_Identification);
    jdMethod_null(paramTransferInfo);
    return localACHRecordInfo;
  }
  
  private ACHAddendaInfo jdMethod_if(TransferInfo paramTransferInfo, ACHRecordInfo paramACHRecordInfo)
  {
    ACHAddendaInfo localACHAddendaInfo = new ACHAddendaInfo();
    TypeCCDAddendaRecord localTypeCCDAddendaRecord = new TypeCCDAddendaRecord();
    localTypeCCDAddendaRecord.Record_Type_Code = 7;
    localTypeCCDAddendaRecord.Addenda_Type_Code = 5;
    try
    {
      localTypeCCDAddendaRecord.Payment_Related_Information = FFSUtil.padWithChar(paramTransferInfo.getMemo(), 80, false, ' ');
      localTypeCCDAddendaRecord.Payment_Related_InformationExists = true;
    }
    catch (Exception localException)
    {
      localTypeCCDAddendaRecord.Payment_Related_InformationExists = false;
    }
    localTypeCCDAddendaRecord.Addenda_Sequence_Number = 1;
    localTypeCCDAddendaRecord.Entry_Detail_Sequence_Number = BPWUtil.composeAddendaEntryDetailSeqNum(String.valueOf((Long)paramACHRecordInfo.getFieldValueObject("Trace_Number"))).intValue();
    localACHAddendaInfo.setAddenda(localTypeCCDAddendaRecord);
    localACHAddendaInfo.setClassCode("CCD");
    localACHAddendaInfo.setAchVersion(this.fN);
    short s = 1;
    paramACHRecordInfo.setFieldValueObject("Addenda_Record_Indicator", new Short(s));
    paramACHRecordInfo.setAddenda(new ACHAddendaInfo[] { localACHAddendaInfo });
    paramACHRecordInfo.setLogId(paramTransferInfo.getLogId());
    this.fQ.recCount += 1L;
    this.fQ.fileEntryCount += 1L;
    this.fQ.batchCache.batchEntryCount += 1;
    return localACHAddendaInfo;
  }
  
  private ACHRecordInfo a(TransferInfo paramTransferInfo, ExtTransferAcctInfo paramExtTransferAcctInfo, short paramShort)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypePPDEntryDetailRecord localTypePPDEntryDetailRecord = new TypePPDEntryDetailRecord();
    localTypePPDEntryDetailRecord.Record_Type_Code = 6;
    localTypePPDEntryDetailRecord.Transaction_Code = paramShort;
    localTypePPDEntryDetailRecord.Receiving_DFI_Identification = paramExtTransferAcctInfo.getAcctBankRtn().substring(0, 8);
    localTypePPDEntryDetailRecord.Check_Digit = BPWUtil.calculateCheckDigit(localTypePPDEntryDetailRecord.Receiving_DFI_Identification);
    localTypePPDEntryDetailRecord.DFI_Account_Number = paramExtTransferAcctInfo.getAcctNum17();
    localTypePPDEntryDetailRecord.Amount = F(paramTransferInfo.getAmount());
    localTypePPDEntryDetailRecord.Individual_Identification_Number = BPWUtil.truncateString(paramExtTransferAcctInfo.getRecipientId(), 15);
    localTypePPDEntryDetailRecord.Individual_Identification_NumberExists = true;
    localTypePPDEntryDetailRecord.Individual_Name = BPWUtil.truncateString(paramExtTransferAcctInfo.getRecipientName(), 22);
    try
    {
      localTypePPDEntryDetailRecord.Trace_Number = BPWUtil.composeTraceNum(this.fT.getFIRTN().substring(0, 8), FFSUtil.padWithChar(Long.toString(this.fQ.fileEntryCount), 10, true, '0')).longValue();
    }
    catch (Exception localException) {}
    localACHRecordInfo.setRecord(localTypePPDEntryDetailRecord);
    localACHRecordInfo.setRecordType(6);
    localACHRecordInfo.setClassCode("PPD");
    localACHRecordInfo.setAchVersion(this.fN);
    localACHRecordInfo.setLogId(paramTransferInfo.getLogId());
    this.fQ.batchCache.batchHash += Long.parseLong(localTypePPDEntryDetailRecord.Receiving_DFI_Identification);
    jdMethod_long(paramTransferInfo);
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo jdMethod_if(CustomerInfo paramCustomerInfo, TransferInfo paramTransferInfo, short paramShort)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypePPDEntryDetailRecord localTypePPDEntryDetailRecord = new TypePPDEntryDetailRecord();
    localTypePPDEntryDetailRecord.Record_Type_Code = 6;
    localTypePPDEntryDetailRecord.Transaction_Code = paramShort;
    localTypePPDEntryDetailRecord.Receiving_DFI_Identification = paramTransferInfo.getBankFromRtn().substring(0, 8);
    localTypePPDEntryDetailRecord.Check_Digit = BPWUtil.calculateCheckDigit(localTypePPDEntryDetailRecord.Receiving_DFI_Identification);
    localTypePPDEntryDetailRecord.DFI_Account_Number = paramTransferInfo.getAccountFromNum17();
    localTypePPDEntryDetailRecord.Amount = F(paramTransferInfo.getAmount());
    localTypePPDEntryDetailRecord.Individual_Identification_Number = "Balanced";
    localTypePPDEntryDetailRecord.Individual_Identification_NumberExists = true;
    localTypePPDEntryDetailRecord.Individual_Name = BPWUtil.truncateString(paramCustomerInfo.firstName, 22);
    try
    {
      localTypePPDEntryDetailRecord.Trace_Number = BPWUtil.composeTraceNum(this.fT.getFIRTN().substring(0, 8), FFSUtil.padWithChar(Long.toString(this.fQ.fileEntryCount), 10, true, '0')).longValue();
    }
    catch (Exception localException) {}
    localACHRecordInfo.setRecord(localTypePPDEntryDetailRecord);
    localACHRecordInfo.setRecordType(6);
    localACHRecordInfo.setClassCode("PPD");
    localACHRecordInfo.setAchVersion(this.fN);
    localACHRecordInfo.setLogId(paramTransferInfo.getLogId());
    this.fQ.batchCache.batchHash += Long.parseLong(localTypePPDEntryDetailRecord.Receiving_DFI_Identification);
    jdMethod_null(paramTransferInfo);
    return localACHRecordInfo;
  }
  
  private ACHAddendaInfo a(TransferInfo paramTransferInfo, ACHRecordInfo paramACHRecordInfo)
  {
    ACHAddendaInfo localACHAddendaInfo = new ACHAddendaInfo();
    TypePPDAddendaRecord localTypePPDAddendaRecord = new TypePPDAddendaRecord();
    localTypePPDAddendaRecord.Record_Type_Code = 7;
    localTypePPDAddendaRecord.Addenda_Type_Code = 5;
    try
    {
      localTypePPDAddendaRecord.Payment_Related_Information = FFSUtil.padWithChar(paramTransferInfo.getMemo(), 80, false, ' ');
      localTypePPDAddendaRecord.Payment_Related_InformationExists = true;
    }
    catch (Exception localException)
    {
      localTypePPDAddendaRecord.Payment_Related_InformationExists = false;
    }
    localTypePPDAddendaRecord.Addenda_Sequence_Number = 1;
    localTypePPDAddendaRecord.Entry_Detail_Sequence_Number = BPWUtil.composeAddendaEntryDetailSeqNum(String.valueOf((Long)paramACHRecordInfo.getFieldValueObject("Trace_Number"))).intValue();
    localACHAddendaInfo.setAddenda(localTypePPDAddendaRecord);
    localACHAddendaInfo.setClassCode("PPD");
    localACHAddendaInfo.setAchVersion(this.fN);
    short s = 1;
    paramACHRecordInfo.setFieldValueObject("Addenda_Record_Indicator", new Short(s));
    paramACHRecordInfo.setAddenda(new ACHAddendaInfo[] { localACHAddendaInfo });
    this.fQ.recCount += 1L;
    this.fQ.fileEntryCount += 1L;
    this.fQ.batchCache.batchEntryCount += 1;
    return localACHAddendaInfo;
  }
  
  public void shutdown()
    throws Exception
  {
    FFSDebug.log("InterAdapter.shutdown is called", 6);
  }
  
  private ACHFIInfo jdMethod_else(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ACHFIInfo localACHFIInfo = ACHFI.getCutOffACHFIInfo(paramFFSConnectionHolder, paramString);
    if ((localACHFIInfo == null) || (localACHFIInfo.getStatusCode() != 0)) {
      throw new FFSException(localACHFIInfo.getStatusCode(), "Failed to get ACHFI with CurOff for FIId: " + paramString + ". " + localACHFIInfo.getStatusMsg());
    }
    return localACHFIInfo;
  }
  
  private String a(ACHFIInfo paramACHFIInfo)
    throws FFSException
  {
    if (this.fF == null)
    {
      this.fF = ACHAdapterUtil.prepareExportFile(paramACHFIInfo, this.fM, this.fP, false, this.fR, this.fN, this.fG);
      this.fQ.recCount += 1L;
    }
    return this.fF;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ExternalTransferAdapter.writeRecordsInFile: ";
    FFSDebug.log(str1, "start", 6);
    ACHRecordInfo localACHRecordInfo = null;
    String str2 = null;
    ETFACHBatchInfo localETFACHBatchInfo = null;
    if ((paramArrayList != null) && (this.fF != null))
    {
      int i = paramArrayList.size();
      for (int j = 0; j < i; j++)
      {
        localACHRecordInfo = (ACHRecordInfo)paramArrayList.get(j);
        ACHAdapterUtil.writeACHRecord(this.fF, localACHRecordInfo, true, this.fR);
        FFSDebug.log(str1 + "reRunCutOff" + paramBoolean, 6);
        if (!paramBoolean)
        {
          Object localObject1;
          if (5 == localACHRecordInfo.getRecordType())
          {
            localObject1 = (TypeBatchHeaderRecord)localACHRecordInfo.getRecord();
            localETFACHBatchInfo = new ETFACHBatchInfo();
            localETFACHBatchInfo.setFileId(this.fK);
            localETFACHBatchInfo.setCustomerId(paramString);
            localETFACHBatchInfo.setCompanyName(((TypeBatchHeaderRecord)localObject1).Company_Name);
            localETFACHBatchInfo.setCompanyIdentification(((TypeBatchHeaderRecord)localObject1).Company_Identification);
            localETFACHBatchInfo.setStdEntryClassCode(((TypeBatchHeaderRecord)localObject1).Standard_Entry_Class_Code);
            localETFACHBatchInfo.setEffectiveEntryDate(((TypeBatchHeaderRecord)localObject1).Effective_Entry_Date);
            localETFACHBatchInfo.setBatchNumber(String.valueOf(((TypeBatchHeaderRecord)localObject1).Batch_Number));
            try
            {
              FFSDebug.log(str1 + "adding ETFACHBatch", 6);
              localETFACHBatchInfo = Transfer.addETFACHBatch(paramFFSConnectionHolder, localETFACHBatchInfo);
              str2 = localETFACHBatchInfo.getBatchId();
            }
            catch (FFSException localFFSException1)
            {
              FFSDebug.log(str1 + "failed to create ETF ACHBatch record " + localFFSException1.toString(), 0);
              throw localFFSException1;
            }
          }
          else if (8 == localACHRecordInfo.getRecordType())
          {
            localObject1 = (TypeBatchControlRecord)localACHRecordInfo.getRecord();
            localETFACHBatchInfo.setEntryAddendaCount(String.valueOf(((TypeBatchControlRecord)localObject1).Entry_Addenda_Count));
            localETFACHBatchInfo.setTotalDebits(String.valueOf(((TypeBatchControlRecord)localObject1).Total_Debits));
            localETFACHBatchInfo.setNumberOfDebits(String.valueOf(this.fQ.debitCount));
            localETFACHBatchInfo.setTotalCredits(String.valueOf(((TypeBatchControlRecord)localObject1).Total_Credits));
            localETFACHBatchInfo.setNumberOfCredits(String.valueOf(this.fQ.creditCount));
            try
            {
              FFSDebug.log(str1 + "updating ETFACHBatch", 6);
              localETFACHBatchInfo = Transfer.updateETFACHBatchWithCtrlInfo(paramFFSConnectionHolder, localETFACHBatchInfo);
            }
            catch (FFSException localFFSException2)
            {
              FFSDebug.log(str1 + "failed to update ETF ACHBatch record " + localFFSException2.toString(), 0);
              throw localFFSException2;
            }
          }
          else
          {
            localObject1 = new ETFACHEntryInfo();
            Object localObject2;
            if ("CCD".equals(localACHRecordInfo.getClassCode()))
            {
              localObject2 = (TypeCCDEntryDetailRecord)localACHRecordInfo.getRecord();
              ((ETFACHEntryInfo)localObject1).setBatchId(str2);
              ((ETFACHEntryInfo)localObject1).setTransactionCode(String.valueOf(((TypeCCDEntryDetailRecord)localObject2).Transaction_Code));
              ((ETFACHEntryInfo)localObject1).setRecvDFIIdentification(((TypeCCDEntryDetailRecord)localObject2).Receiving_DFI_Identification);
              ((ETFACHEntryInfo)localObject1).setDFIAccountNumber(((TypeCCDEntryDetailRecord)localObject2).DFI_Account_Number);
              ((ETFACHEntryInfo)localObject1).setRcvCompIndvName(((TypeCCDEntryDetailRecord)localObject2).Receiving_Company_Name);
              ((ETFACHEntryInfo)localObject1).setIdentificationNumber(((TypeCCDEntryDetailRecord)localObject2).Identification_Number);
              ((ETFACHEntryInfo)localObject1).setAmount(String.valueOf(((TypeCCDEntryDetailRecord)localObject2).Amount));
              ((ETFACHEntryInfo)localObject1).setTraceNumber(String.valueOf(((TypeCCDEntryDetailRecord)localObject2).Trace_Number));
            }
            else
            {
              localObject2 = (TypePPDEntryDetailRecord)localACHRecordInfo.getRecord();
              ((ETFACHEntryInfo)localObject1).setBatchId(str2);
              ((ETFACHEntryInfo)localObject1).setTransactionCode(String.valueOf(((TypePPDEntryDetailRecord)localObject2).Transaction_Code));
              ((ETFACHEntryInfo)localObject1).setRecvDFIIdentification(((TypePPDEntryDetailRecord)localObject2).Receiving_DFI_Identification);
              ((ETFACHEntryInfo)localObject1).setDFIAccountNumber(((TypePPDEntryDetailRecord)localObject2).DFI_Account_Number);
              ((ETFACHEntryInfo)localObject1).setRcvCompIndvName(((TypePPDEntryDetailRecord)localObject2).Individual_Name);
              ((ETFACHEntryInfo)localObject1).setIdentificationNumber(((TypePPDEntryDetailRecord)localObject2).Individual_Identification_Number);
              ((ETFACHEntryInfo)localObject1).setAmount(String.valueOf(((TypePPDEntryDetailRecord)localObject2).Amount));
              ((ETFACHEntryInfo)localObject1).setTraceNumber(String.valueOf(((TypePPDEntryDetailRecord)localObject2).Trace_Number));
            }
            ((ETFACHEntryInfo)localObject1).setLogId(localACHRecordInfo.getLogId());
            try
            {
              FFSDebug.log(str1 + "adding ETFACHEntry", 6);
              localObject1 = Transfer.addETFACHEntry(paramFFSConnectionHolder, (ETFACHEntryInfo)localObject1);
            }
            catch (FFSException localFFSException3)
            {
              FFSDebug.log(str1 + "failed to create ETF ACHEntry record " + localFFSException3.toString(), 0);
              throw localFFSException3;
            }
          }
        }
      }
    }
  }
  
  private ACHRecordInfo a(ExtTransferCompanyInfo paramExtTransferCompanyInfo, String paramString)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    this.fQ.fileBatchCount += 1;
    TypeBatchHeaderRecord localTypeBatchHeaderRecord = new TypeBatchHeaderRecord();
    localTypeBatchHeaderRecord.Record_Type_Code = 5;
    localTypeBatchHeaderRecord.Service_Class_Code = 220;
    localTypeBatchHeaderRecord.Effective_Entry_Date = this.fC.substring(2, this.fC.length() - 2);
    localTypeBatchHeaderRecord.Company_Name = paramExtTransferCompanyInfo.getCompName();
    localTypeBatchHeaderRecord.Company_Identification = paramExtTransferCompanyInfo.getCompACHId();
    localTypeBatchHeaderRecord.Originating_DFI_Identification = this.fU.getODFIACHId8();
    localTypeBatchHeaderRecord.Originator_Status_Code = "1";
    localTypeBatchHeaderRecord.Batch_Number = this.fQ.fileBatchCount;
    if (paramString.compareTo("BUSINESS") == 0)
    {
      localTypeBatchHeaderRecord.Company_Entry_Description = "Business";
      localTypeBatchHeaderRecord.Standard_Entry_Class_Code = "CCD";
    }
    else
    {
      localTypeBatchHeaderRecord.Company_Entry_Description = "Personal";
      localTypeBatchHeaderRecord.Standard_Entry_Class_Code = "PPD";
    }
    localACHRecordInfo.setRecordType(5);
    localACHRecordInfo.setRecord(localTypeBatchHeaderRecord);
    this.fQ.recCount += 1L;
    this.fQ.batchCache = new ACHBatchCache();
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo a(ExtTransferCompanyInfo paramExtTransferCompanyInfo, ACHRecordInfo paramACHRecordInfo)
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeBatchControlRecord localTypeBatchControlRecord = new TypeBatchControlRecord();
    localTypeBatchControlRecord.Record_Type_Code = 8;
    localTypeBatchControlRecord.Message_Authentication_Code = MacGenerator.generateMac(this.fU.getODFIACHId());
    localTypeBatchControlRecord.Message_Authentication_CodeExists = true;
    localTypeBatchControlRecord.Entry_Addenda_Count = this.fQ.batchCache.batchEntryCount;
    localTypeBatchControlRecord.Total_Debits = this.fQ.batchCache.batchDebitSum.longValue();
    localTypeBatchControlRecord.Total_Credits = this.fQ.batchCache.batchCreditSum.longValue();
    localTypeBatchControlRecord.Company_Identification = paramExtTransferCompanyInfo.getCompACHId();
    localTypeBatchControlRecord.Batch_Number = this.fQ.fileBatchCount;
    TypeBatchHeaderRecord localTypeBatchHeaderRecord = (TypeBatchHeaderRecord)paramACHRecordInfo.getRecord();
    localTypeBatchControlRecord.Service_Class_Code = localTypeBatchHeaderRecord.Service_Class_Code;
    localTypeBatchControlRecord.Reserved6 = "";
    localTypeBatchControlRecord.Originating_DFI_Identification = localTypeBatchHeaderRecord.Originating_DFI_Identification;
    localTypeBatchControlRecord.Company_Identification = localTypeBatchHeaderRecord.Company_Identification;
    localTypeBatchControlRecord.Entry_Hash = this.fQ.batchCache.batchHash;
    localACHRecordInfo.setRecordType(8);
    localACHRecordInfo.setRecord(localTypeBatchControlRecord);
    this.fQ.recCount += 1L;
    this.fQ.fileHash += this.fQ.batchCache.batchHash;
    return localACHRecordInfo;
  }
  
  private void F()
    throws FFSException
  {
    this.fQ.recCount += 1L;
    ACHAdapterUtil.writeFileControl(this.fF, this.fQ, this.fR, this.fN);
  }
  
  private void G()
    throws Exception
  {
    File localFile = new File(this.fF);
    localFile.delete();
  }
  
  private short jdMethod_for(String paramString1, String paramString2)
  {
    short s = 0;
    if ((paramString1 != null) && (paramString2 != null)) {
      if (paramString1.equalsIgnoreCase("Checking"))
      {
        if (paramString2.equals("PRENOTE_ENTRY")) {
          s = 23;
        } else {
          s = 22;
        }
      }
      else if (paramString2.equals("PRENOTE_ENTRY")) {
        s = 33;
      } else {
        s = 32;
      }
    }
    return s;
  }
  
  private void jdMethod_long(TransferInfo paramTransferInfo)
  {
    if (paramTransferInfo != null)
    {
      this.fQ.recCount += 1L;
      this.fQ.fileEntryCount += 1L;
      this.fQ.creditCount += 1L;
      this.fQ.fileCreditSum = this.fQ.fileCreditSum.add(FFSUtil.getBigDecimal(paramTransferInfo.getAmount()));
      this.fQ.batchCache.creditCount += 1L;
      this.fQ.batchCache.batchEntryCount += 1;
      this.fQ.batchCache.batchCreditSum = this.fQ.batchCache.batchCreditSum.add(FFSUtil.getBigDecimal(paramTransferInfo.getAmount()));
    }
  }
  
  private void jdMethod_null(TransferInfo paramTransferInfo)
  {
    if (paramTransferInfo != null)
    {
      this.fQ.recCount += 1L;
      this.fQ.fileEntryCount += 1L;
      this.fQ.debitCount += 1L;
      this.fQ.fileDebitSum = this.fQ.fileDebitSum.add(FFSUtil.getBigDecimal(paramTransferInfo.getAmount()));
      this.fQ.batchCache.batchEntryCount += 1;
      this.fQ.batchCache.debitCount += 1L;
      this.fQ.batchCache.batchDebitSum = this.fQ.batchCache.batchDebitSum.add(FFSUtil.getBigDecimal(paramTransferInfo.getAmount()));
    }
  }
  
  private void jdMethod_goto(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "InterAdapter.getExportFileName: ";
    File localFile1 = new File(this.fF);
    String str2 = localFile1.getName();
    String str3 = this.fI + str2;
    File localFile2 = new File(str3);
    if (localFile2.exists())
    {
      FFSDebug.log(str1 + " export ACH file exist: " + str3, 0);
      String str4 = this.fP + str2 + ".ACH" + "." + String.valueOf(System.currentTimeMillis());
      File localFile3 = new File(str4);
      localFile2.renameTo(localFile3);
      FFSDebug.log(str1 + " the existing file has been moved to  " + str4, 0);
    }
    localFile1.renameTo(localFile2);
    if (!this.fD)
    {
      ACHAdapterUtil.doFMLoggingForACH(paramFFSConnectionHolder, localFile2.getCanonicalPath(), str1);
      jdMethod_char(paramFFSConnectionHolder, localFile2.getCanonicalPath());
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo[] paramArrayOfTransferInfo, String paramString)
    throws FFSException
  {
    if (paramArrayOfTransferInfo != null)
    {
      int i = paramArrayOfTransferInfo.length;
      for (int j = 0; j < i; j++) {
        jdMethod_if(paramFFSConnectionHolder, paramArrayOfTransferInfo[j], paramString);
      }
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, String paramString)
    throws FFSException
  {
    String str1 = "ExternalTransferAdapter.failTransferInfo: ";
    FFSDebug.log(str1 + "Failed to process transfer: " + paramTransferInfo.getSrvrTId(), 6);
    FFSDebug.log(str1 + "Failed to process transfer: " + paramString, 6);
    paramTransferInfo.setPrcStatus("FAILEDON");
    paramTransferInfo.setLastProcessId(this.fV);
    paramTransferInfo = Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, paramTransferInfo);
    if (paramTransferInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str1 + paramTransferInfo.getStatusMsg(), 0);
      throw new FFSException(paramTransferInfo.getStatusCode(), paramTransferInfo.getStatusMsg());
    }
    int i = LimitCheckApprovalProcessor.processExternalTransferDelete(paramFFSConnectionHolder, paramTransferInfo, null);
    String str2 = "FAILEDON";
    if (i == 5) {
      str2 = "LIMIT_REVERT_FAILED";
    }
    paramTransferInfo.setPrcStatus(str2);
    paramTransferInfo = Transfer.updateStatus(paramFFSConnectionHolder, paramTransferInfo, false);
    a(paramFFSConnectionHolder, paramTransferInfo, "Failed to process a transfer:" + paramString);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, String paramString)
    throws FFSException
  {
    String str1 = "ExternalTransferAdapter.doTransAuditLog:";
    try
    {
      if (this.fD == true) {
        return;
      }
      if (this.fL >= 4)
      {
        BigDecimal localBigDecimal = new BigDecimal(paramTransferInfo.getAmount());
        int i = 0;
        try
        {
          i = Integer.parseInt(paramTransferInfo.getCustomerId());
        }
        catch (NumberFormatException localNumberFormatException)
        {
          String str4 = str1 + " CustomerId is not an integer - " + paramTransferInfo.getCustomerId() + " - " + localNumberFormatException.toString();
          FFSDebug.log(str4 + FFSDebug.stackTrace(localNumberFormatException), 0);
          throw new FFSException(localNumberFormatException, str4);
        }
        String str3 = paramString + ", Transfer server TID  = " + paramTransferInfo.getSrvrTId();
        FFSDebug.log(str1 + str3, 6);
        int j = 5251;
        if (paramTransferInfo.getPrcStatus().equals("POSTEDON") == true) {
          j = 3611;
        }
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
        String str9 = null;
        if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
          str9 = paramTransferInfo.getAccountToId();
        } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
          str9 = paramTransferInfo.getAccountFromId();
        }
        localExtTransferAcctInfo.setAcctId(str9);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
        if (localExtTransferAcctInfo.getStatusCode() == 0) {
          if ("ITOE".equals(paramTransferInfo.getTransferDest()))
          {
            str5 = localExtTransferAcctInfo.buildAcctId();
            str6 = localExtTransferAcctInfo.getAcctBankRtn();
          }
          else if ("ETOI".equals(paramTransferInfo.getTransferDest()))
          {
            str7 = localExtTransferAcctInfo.buildAcctId();
            str8 = localExtTransferAcctInfo.getAcctBankRtn();
          }
        }
        if (str5 == null)
        {
          str5 = paramTransferInfo.buildToAcctId();
          str6 = paramTransferInfo.getBankToRtn();
        }
        if (str7 == null)
        {
          str7 = paramTransferInfo.buildFromAcctId();
          str8 = paramTransferInfo.getBankFromRtn();
        }
        AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramTransferInfo.getSubmittedBy(), null, null, str3, paramTransferInfo.getLogId(), j, i, localBigDecimal, paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str5, str6, str7, str8, 0);
        TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
      }
    }
    catch (Exception localException)
    {
      String str2 = str1 + " failed " + localException.toString();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str2);
    }
  }
  
  private void jdMethod_char(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    if (this.fD) {
      return;
    }
    ScheduleHist localScheduleHist = null;
    try
    {
      localScheduleHist = ScheduleHistory.getLastScheduleHist(this.fW, "ETFTRN");
      localScheduleHist.FileName = paramString;
      localScheduleHist.EventType = "ProcessingFileGenerated";
      ScheduleHistory.createScheduleHist(paramFFSConnectionHolder, localScheduleHist);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log("*** ScheduleRunnable.getLastScheduleHist: exception:" + str, 6);
      return;
    }
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, CustomerInfo paramCustomerInfo)
    throws FFSException
  {
    return this.fD == true;
  }
  
  private long F(String paramString)
  {
    return FFSUtil.getBigDecimal(paramString).movePointRight(2).longValue();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.achadapter.ExternalTransferAdapter
 * JD-Core Version:    0.7.0.1
 */