package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.ACHFI;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.ExternalTransferCompany;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.handler.backend.TransferBackendHandlerFactory;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ITransferBackendHandler;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class TransferHandler
  implements DBConsts, FFSConst, BPWResource
{
  private ITransferBackendHandler i0 = null;
  private ArrayList iN = null;
  private boolean iX = false;
  private PropertyConfig iL = null;
  private String i1 = null;
  BPWFIInfo iR = null;
  ACHFIInfo iO = null;
  boolean iU = false;
  String iZ = null;
  int iT = 0;
  int iV = 1;
  int iY = 0;
  ArrayList iP = null;
  private final String iQ = "BPW_FI_INFO";
  private final String iW = "ACH_FI_INFO";
  private final String iS = "DO_AUDIT_LOG";
  private final String iM = "PROCESS_ID";
  
  public TransferHandler()
    throws Exception
  {
    int i = this.iL.LogLevel;
    this.iT = this.iL.BatchSize;
    this.iX = (i >= 4);
    try
    {
      this.i0 = TransferBackendHandlerFactory.createBackendHandler();
    }
    catch (BPWException localBPWException)
    {
      String str = "Error while getting ITransferBackendHandler implementation";
      FFSDebug.throwing(str, localBPWException);
      throw localBPWException;
    }
    this.iP = new ArrayList();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("TransferHandler.eventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false, false);
    FFSDebug.log("TransferHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("TransferHandler.resubmitEventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    boolean bool1 = false;
    boolean bool2 = false;
    if (paramEventInfoArray._array[0].ScheduleFlag == -1) {
      bool1 = true;
    } else {
      bool2 = true;
    }
    FFSDebug.log("TransferHandler.resubmitEventHandler: reRunCutOff =" + bool1 + ", crashRecovery =" + bool2, 6);
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool1, bool2);
    FFSDebug.log("TransferHandler.resubmitEventHandler: end", 6);
  }
  
  private void a(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    FFSDebug.log("TransferHandler.processEvents: begin, eventSeq: " + paramInt + ", reRunCutOff: " + paramBoolean1 + ", crashRecovery: " + paramBoolean2 + ", length: " + paramEventInfoArray._array.length, 6);
    if (paramInt == 0) {
      firstEventHandler(paramFFSConnectionHolder, paramEventInfoArray, paramBoolean1);
    } else if (paramInt == 2) {
      lastEventHandler(paramFFSConnectionHolder, paramEventInfoArray, paramBoolean1, paramBoolean2);
    }
    FFSDebug.log("TransferHandler.processEvents: end", 6);
  }
  
  public void firstEventHandler(FFSConnectionHolder paramFFSConnectionHolder, EventInfoArray paramEventInfoArray, boolean paramBoolean)
    throws Exception
  {
    String str = paramEventInfoArray._array[0].FIId;
    this.i1 = paramEventInfoArray._array[0].processId;
    this.iU = paramEventInfoArray._array[0].createEmptyFile;
    this.iR = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str);
    this.iO = d(paramFFSConnectionHolder, str);
    this.iY = paramEventInfoArray._array[0].fileBasedRecovery;
    HashMap localHashMap = new HashMap();
    localHashMap.put("BPW_FI_INFO", this.iR);
    localHashMap.put("ACH_FI_INFO", this.iO);
    localHashMap.put("DO_AUDIT_LOG", new Boolean(this.iX));
    localHashMap.put("RERUN_CUTOFF", new Boolean(paramBoolean));
    localHashMap.put("PROCESS_ID", this.i1);
    this.i0.startProcessTransfer(paramEventInfoArray._array[0], localHashMap);
    this.iZ = DBConnCache.getNewBatchKey();
    DBConnCache.bind(this.iZ, paramFFSConnectionHolder);
    this.iN = new ArrayList();
    TransferInfo localTransferInfo = a(paramEventInfoArray, this.iR, 0);
    this.iN.add(localTransferInfo);
  }
  
  public void getTransfers(FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    Object localObject1;
    int j;
    Object localObject2;
    if (paramBoolean1 == true)
    {
      localObject1 = Transfer.getProcessedTransfersForFIId(paramFFSConnectionHolder, this.i1, this.iR.getFIId(), this.iV, this.iT);
      localArrayList.addAll((Collection)localObject1);
    }
    else if (paramBoolean2 == true)
    {
      if (this.iY == 1)
      {
        localObject1 = Transfer.getPrenoteTransferByProcessId(paramFFSConnectionHolder, this.i1, this.iV);
        j = localObject1.length;
        for (int k = 0; k < j; k++)
        {
          localObject1[k].setPrcStatus("CANCELEDON");
          Transfer.updateStatus(paramFFSConnectionHolder, localObject1[k], false);
          paramFFSConnectionHolder.conn.commit();
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizableMessage(1060, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          a(paramFFSConnectionHolder, localObject1[k], paramBoolean1, localLocalizableString1, "Server crashed last time. Cancel this entry first and then re-process prenote.");
          ExtTransferAcctInfo localExtTransferAcctInfo = null;
          if (localObject1[k].getTransferDest().compareTo("ITOE") == 0) {
            localExtTransferAcctInfo = localObject1[k].getAccountToInfo();
          } else {
            localExtTransferAcctInfo = localObject1[k].getAccountFromInfo();
          }
          jdMethod_if(paramFFSConnectionHolder, localExtTransferAcctInfo, null, null);
          paramFFSConnectionHolder.conn.commit();
          a(paramFFSConnectionHolder, localExtTransferAcctInfo);
        }
        localObject2 = Transfer.getAllTransfersForBackendByFIId(paramFFSConnectionHolder, this.i1, this.iR.getFIId(), this.iV, this.iT);
        localArrayList.addAll((Collection)localObject2);
        i = 1;
      }
      else
      {
        localArrayList.addAll(Transfer.getUnProcessedTransfersForFIId(paramFFSConnectionHolder, this.iR.getFIId(), this.iT));
      }
    }
    else
    {
      localArrayList.addAll(Transfer.getUnProcessedTransfersForFIId(paramFFSConnectionHolder, this.iR.getFIId(), this.iT));
    }
    if (localArrayList != null)
    {
      localObject1 = new ArrayList();
      j = localArrayList.size();
      localObject2 = "" + DBUtil.getCurrentStartDate();
      boolean bool = ab();
      for (int m = 0; m < j; m++)
      {
        TransferInfo localTransferInfo = (TransferInfo)localArrayList.get(m);
        if ((i == 1) && (localTransferInfo.getLastProcessId() != null) && (localTransferInfo.getLastProcessId().equals(this.i1))) {
          localTransferInfo.setPossibleDuplicate(true);
        }
        localTransferInfo.setLastProcessId(this.i1);
        localTransferInfo.setProcessNumber(this.iV);
        Transfer.updateProcessInfo(paramFFSConnectionHolder, localTransferInfo);
        paramFFSConnectionHolder.conn.commit();
        localTransferInfo.setEventId("1");
        localTransferInfo.setDbTransKey(this.iZ);
        localTransferInfo.setPrcStatus("INPROCESS");
        Transfer.updateStatus(paramFFSConnectionHolder, localTransferInfo, false);
        if (localTransferInfo.getDateToPost().compareTo((String)localObject2) < 0)
        {
          if (!bool)
          {
            jdMethod_try(paramFFSConnectionHolder, localTransferInfo);
            continue;
          }
          ((ArrayList)localObject1).add(localTransferInfo);
        }
        else
        {
          ((ArrayList)localObject1).add(localTransferInfo);
        }
        LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizableMessage(1061, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        a(paramFFSConnectionHolder, localTransferInfo, paramBoolean1, localLocalizableString2, "Successfully processed an external transfer.");
      }
      this.iN.addAll((Collection)localObject1);
    }
  }
  
  private void jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws Exception
  {
    paramTransferInfo.setPrcStatus("BACKENDFAILED");
    Transfer.updateStatus(paramFFSConnectionHolder, paramTransferInfo, false);
    LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1063, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    jdMethod_int(paramFFSConnectionHolder, paramTransferInfo, localLocalizableString);
  }
  
  private boolean ab()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = localPropertyConfig.otherProperties.getProperty("bpw.transfer.backend.processOldEntries", "false");
    return !str.equals("false");
  }
  
  public void lastEventHandler(FFSConnectionHolder paramFFSConnectionHolder, EventInfoArray paramEventInfoArray, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    try
    {
      String str = this.iR.getFIId();
      processExtAccountWithPrenote(paramFFSConnectionHolder, str);
      if ((paramBoolean1) || (paramBoolean2)) {
        this.iV = (Transfer.getMaxProcessNumberByProcessId(paramFFSConnectionHolder, this.i1) + 1);
      }
      getTransfers(paramFFSConnectionHolder, paramBoolean1, paramBoolean2);
      for (int i = this.iN.size(); i > 0; i = this.iN.size())
      {
        a(paramFFSConnectionHolder, this.iN);
        this.iN.clear();
        if (i < this.iT) {
          break;
        }
        getTransfers(paramFFSConnectionHolder, paramBoolean1, paramBoolean2);
      }
      TransferInfo localTransferInfo = a(paramEventInfoArray, this.iR, 2);
      this.iN.add(localTransferInfo);
      a(paramFFSConnectionHolder, this.iN);
      DBConnCache.unbind(this.iZ);
    }
    catch (Exception localException1)
    {
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2) {}
      throw localException1;
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList)
    throws Exception
  {
    String str1 = "com.ffusion.ffs.bpw.handler.TransferHandler.processTrans";
    TransferInfo[] arrayOfTransferInfo = (TransferInfo[])paramArrayList.toArray(new TransferInfo[0]);
    HashMap localHashMap = new HashMap();
    try
    {
      this.i0.processTransfer(arrayOfTransferInfo, localHashMap);
    }
    catch (Exception localException)
    {
      String str2 = str1 + ":Exception thrown by " + this.i0.getClass().getName();
      FFSDebug.log(localException, str2);
      paramFFSConnectionHolder.conn.rollback();
    }
    paramFFSConnectionHolder.conn.commit();
  }
  
  private ACHFIInfo d(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ACHFIInfo localACHFIInfo = ACHFI.getCutOffACHFIInfo(paramFFSConnectionHolder, paramString);
    if ((localACHFIInfo == null) || (localACHFIInfo.getStatusCode() != 0)) {
      throw new FFSException(localACHFIInfo.getStatusCode(), "Failed to get ACHFI with CutOff for FIId: " + paramString + ". " + localACHFIInfo.getStatusMsg());
    }
    return localACHFIInfo;
  }
  
  public void processExtAccountWithPrenote(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str1 = "TransferHandler.processExtAccountWithPrenote: ";
    FFSDebug.log(str1 + "begins", 6);
    Calendar localCalendar = Calendar.getInstance();
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    String str2 = localSimpleDateFormat1.format(localCalendar.getTime());
    int i = Integer.parseInt(str2);
    for (int j = 0; j < 6; j++) {
      i = SmartCalendar.getACHBusinessDay(paramString, i, false);
    }
    String str3 = new Integer(i).toString();
    Date localDate = localSimpleDateFormat1.parse(str3);
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    localSimpleDateFormat2.setLenient(false);
    String str4 = localSimpleDateFormat2.format(localDate);
    jdMethod_goto(paramFFSConnectionHolder, paramString, str4);
    e(paramFFSConnectionHolder, paramString);
    paramFFSConnectionHolder.conn.commit();
    FFSDebug.log(str1 + " ends", 6);
  }
  
  private void jdMethod_goto(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    ExtTransferAcctInfo[] arrayOfExtTransferAcctInfo = null;
    if (this.iX) {
      arrayOfExtTransferAcctInfo = ExternalTransferAccount.getMaturedExtTransferAcctInfo(paramFFSConnectionHolder, paramString1, paramString2);
    }
    if (arrayOfExtTransferAcctInfo == null) {
      return;
    }
    ExternalTransferAccount.updateMaturedExtAcctPrenoteStatus(paramFFSConnectionHolder, paramString1, paramString2);
    int i = 0;
    if (arrayOfExtTransferAcctInfo != null) {
      i = arrayOfExtTransferAcctInfo.length;
    }
    if ((this.iX) && (i > 0)) {
      for (int j = 0; j < i; j++)
      {
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1065, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        ExternalTransferAccount.logExtTransferAccountTransAuditLog(paramFFSConnectionHolder, arrayOfExtTransferAcctInfo[j], localLocalizableString, "Update prenote status of an External Account", 5251);
      }
    }
    paramFFSConnectionHolder.conn.commit();
  }
  
  private TransferInfo a(EventInfoArray paramEventInfoArray, BPWFIInfo paramBPWFIInfo, int paramInt)
  {
    String str1 = paramEventInfoArray._array[0].FIId;
    String str2 = paramEventInfoArray._array[0].processId;
    String str3 = paramEventInfoArray._array[0].InstructionID;
    TransferInfo localTransferInfo = new TransferInfo();
    localTransferInfo.setAmount("0");
    localTransferInfo.setSrvrTId(str3);
    localTransferInfo.setFIId(str1);
    localTransferInfo.setDateCreate(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setDateDue(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setDateToPost(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setLastProcessId(str2);
    localTransferInfo.setTransferType("Current");
    localTransferInfo.setTransferDest("ETOI");
    localTransferInfo.setBankFromRtn(paramBPWFIInfo.getFIRTN());
    localTransferInfo.setAccountFromNum("DummyAccount");
    localTransferInfo.setPrcStatus("POSTEDON");
    localTransferInfo.setStatusCode(0);
    localTransferInfo.setStatusMsg("Success");
    localTransferInfo.setEventId("" + paramInt);
    localTransferInfo.setDbTransKey(this.iZ);
    return localTransferInfo;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean, ILocalizable paramILocalizable, String paramString)
    throws FFSException
  {
    String str1 = "TransferHandler.doTransAuditLog:";
    try
    {
      if (paramBoolean == true) {
        return;
      }
      if (this.iX)
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
        String str3 = paramString + " Transfer server TID  = " + paramTransferInfo.getSrvrTId();
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
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramILocalizable;
        arrayOfObject[1] = paramTransferInfo.getSrvrTId();
        paramILocalizable = BPWLocaleUtil.getLocalizableMessage(1059, arrayOfObject, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramTransferInfo.getSubmittedBy(), null, null, paramILocalizable, paramTransferInfo.getLogId(), j, i, localBigDecimal, paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str5, str6, str7, str8, 0);
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
  
  private void e(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    ExtTransferAcctInfo[] arrayOfExtTransferAcctInfo = ExternalTransferAccount.getUnMaturedExtTransferAcctInfo(paramFFSConnectionHolder, paramString, false);
    TransferInfo localTransferInfo = null;
    if (arrayOfExtTransferAcctInfo != null)
    {
      int i = arrayOfExtTransferAcctInfo.length;
      String str = FFSUtil.getDateString("yyyy/MM/dd HH:mm");
      for (int j = 0; j < i; j++)
      {
        ExtTransferAcctInfo localExtTransferAcctInfo = arrayOfExtTransferAcctInfo[j];
        if (jdMethod_for(localExtTransferAcctInfo) == true)
        {
          jdMethod_if(paramFFSConnectionHolder, localExtTransferAcctInfo, "Pending", str);
          localTransferInfo = a(paramFFSConnectionHolder, localExtTransferAcctInfo);
          this.iP.add(localTransferInfo);
        }
      }
      paramFFSConnectionHolder.conn.commit();
    }
  }
  
  private TransferInfo a(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    TransferInfo localTransferInfo = new TransferInfo();
    localTransferInfo.setAmount("0");
    localTransferInfo.setCustomerId(paramExtTransferAcctInfo.getCustomerId());
    localTransferInfo.setFIId(this.iR.getFIId());
    localTransferInfo.setDateCreate(FFSUtil.getDateString("yyyyMMdd"));
    localTransferInfo.setDateDue(FFSUtil.getDateString("yyyyMMdd"));
    String str1 = "" + DBUtil.getCurrentStartDate();
    localTransferInfo.setDateToPost(str1);
    localTransferInfo.setProcessDate(str1);
    localTransferInfo.setExternalAcctId(paramExtTransferAcctInfo.getAcctId());
    localTransferInfo.setLogId(paramExtTransferAcctInfo.getLogId());
    localTransferInfo.setSubmittedBy(paramExtTransferAcctInfo.getSubmittedBy());
    localTransferInfo.setTransferType("Current");
    localTransferInfo.setTransferCategory("PRENOTE_ENTRY");
    localTransferInfo.setBankFromRtn(this.iR.getFIRTN());
    localTransferInfo.setAccountFromNum("PrenoteAccount");
    localTransferInfo.setAccountFromType(paramExtTransferAcctInfo.getAcctType());
    localTransferInfo.setPrcStatus("WILLPROCESSON");
    String str2 = paramExtTransferAcctInfo.getPrenoteType();
    if (str2 != null)
    {
      if (str2.equals("CCD - Credit"))
      {
        localTransferInfo.setTypeDetail("CCD");
        localTransferInfo.setTransferDest("ITOE");
      }
      else if (str2.equals("CCD - Debit"))
      {
        localTransferInfo.setTypeDetail("CCD");
        localTransferInfo.setTransferDest("ETOI");
      }
      else if (str2.equals("PPD - Credit"))
      {
        localTransferInfo.setTypeDetail("PPD");
        localTransferInfo.setTransferDest("ITOE");
      }
      else if (str2.equals("PPD - Debit"))
      {
        localTransferInfo.setTypeDetail("PPD");
        localTransferInfo.setTransferDest("ETOI");
      }
      else if (str2.equals("WEB"))
      {
        localTransferInfo.setTypeDetail("WEB");
        localTransferInfo.setTransferDest("ETOI");
      }
      else if (str2.equals("TEL"))
      {
        localTransferInfo.setTypeDetail("TEL");
        localTransferInfo.setTransferDest("ETOI");
      }
    }
    else
    {
      localTransferInfo.setTransferDest("ITOE");
      if (paramExtTransferAcctInfo.getRecipientType().equals("BUSINESS")) {
        localTransferInfo.setTypeDetail("CCD");
      } else {
        localTransferInfo.setTypeDetail("PPD");
      }
    }
    if (localTransferInfo.getTransferDest().compareTo("ITOE") == 0) {
      localTransferInfo.setAccountToInfo(paramExtTransferAcctInfo);
    } else {
      localTransferInfo.setAccountFromInfo(paramExtTransferAcctInfo);
    }
    ExtTransferCompanyInfo[] arrayOfExtTransferCompanyInfo = ExternalTransferCompany.getETFCompanyByFIIDAndCustomerId(paramFFSConnectionHolder, this.iR.getFIId(), paramExtTransferAcctInfo.getCustomerId());
    if (arrayOfExtTransferCompanyInfo != null)
    {
      int i = arrayOfExtTransferCompanyInfo.length;
      if (i == 1) {
        localTransferInfo.setExtTransferCompanyInfo(arrayOfExtTransferCompanyInfo[0]);
      } else {
        for (int j = 0; j < i; j++) {
          if (arrayOfExtTransferCompanyInfo[j].getCustomerId().equals("-1"))
          {
            localTransferInfo.setExtTransferCompanyInfo(arrayOfExtTransferCompanyInfo[j]);
            break;
          }
        }
      }
    }
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(paramExtTransferAcctInfo.getCustomerId(), paramFFSConnectionHolder, paramExtTransferAcctInfo);
    localTransferInfo = Transfer.addTransferFromAdapter(paramFFSConnectionHolder, localTransferInfo, false, this.iR, localCustomerInfo);
    if (localTransferInfo.getStatusCode() != 0) {
      throw new FFSException(localTransferInfo.getStatusCode(), localTransferInfo.getStatusMsg());
    }
    LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1062, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    a(paramFFSConnectionHolder, localTransferInfo, false, localLocalizableString, "Successfully created prenote entry for this external account.");
    return localTransferInfo;
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ExtTransferAcctInfo paramExtTransferAcctInfo, String paramString1, String paramString2)
    throws FFSException
  {
    paramExtTransferAcctInfo.setPrenoteStatus(paramString1);
    paramExtTransferAcctInfo.setPrenoteSubDate(paramString2);
    paramExtTransferAcctInfo = ExternalTransferAccount.modifyPrenote(paramFFSConnectionHolder, paramExtTransferAcctInfo, true);
  }
  
  private boolean jdMethod_for(ExtTransferAcctInfo paramExtTransferAcctInfo)
  {
    String str1 = "TransferHandler.needToProcessPrenote: ";
    String str2 = paramExtTransferAcctInfo.getPrenoteStatus();
    FFSDebug.log(str1, " prenote=", paramExtTransferAcctInfo.getPrenote(), 6);
    FFSDebug.log(str1, " prenoteStatus=", str2, 6);
    if ((paramExtTransferAcctInfo.getPrenote() != null) && (paramExtTransferAcctInfo.getPrenote().trim().equalsIgnoreCase("Y"))) {
      return str2 == null;
    }
    return false;
  }
  
  private void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
  {
    String str1 = "TransferHandler.doAuditLog: ";
    String str2 = null;
    int i = 0;
    if (paramTransferInfo == null) {
      return;
    }
    try
    {
      str2 = paramTransferInfo.getAmount();
      if ((str2 == null) || (str2.trim().length() == 0)) {
        str2 = "-1";
      }
      if ((paramTransferInfo.getCustomerId() != null) && (paramTransferInfo.getCustomerId().length() > 0)) {
        if (paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())) {
          i = 0;
        } else {
          i = Integer.parseInt(paramTransferInfo.getCustomerId());
        }
      }
      String str3 = null;
      str4 = null;
      String str5 = null;
      String str6 = null;
      ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
      String str7 = null;
      if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str7 = paramTransferInfo.getAccountToId();
      } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str7 = paramTransferInfo.getAccountFromId();
      }
      localExtTransferAcctInfo.setAcctId(str7);
      localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
      if (localExtTransferAcctInfo.getStatusCode() == 0) {
        if ("ITOE".equals(paramTransferInfo.getTransferDest()))
        {
          str3 = localExtTransferAcctInfo.buildAcctId();
          str4 = localExtTransferAcctInfo.getAcctBankRtn();
        }
        else if ("ETOI".equals(paramTransferInfo.getTransferDest()))
        {
          str5 = localExtTransferAcctInfo.buildAcctId();
          str6 = localExtTransferAcctInfo.getAcctBankRtn();
        }
      }
      if (str3 == null)
      {
        str3 = paramTransferInfo.buildToAcctId();
        str4 = paramTransferInfo.getBankToRtn();
      }
      if (str5 == null)
      {
        str5 = paramTransferInfo.buildFromAcctId();
        str6 = paramTransferInfo.getBankFromRtn();
      }
      AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramTransferInfo.getSubmittedBy(), null, null, paramILocalizable, paramTransferInfo.getLogId(), 5258, i, new BigDecimal(str2), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str3, str4, str5, str6, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      localException = localException;
      String str4 = str1 + " failed " + localException.toString();
      FFSDebug.log(str4 + FFSDebug.stackTrace(localException), 0);
    }
    finally {}
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.TransferHandler
 * JD-Core Version:    0.7.0.1
 */