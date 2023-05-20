package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWXferExtraInfo;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.CustRoute;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PayeeEditMask;
import com.ffusion.ffs.bpw.db.PayeeToRoute;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustPayeeRslt;
import com.ffusion.ffs.bpw.interfaces.CustomerRouteInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRslt;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class BackendProcessor
  implements FFSConst, DBConsts, BPWResource
{
  private PropertyConfig bE = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int bD = 1;
  
  public void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws Exception
  {
    if (paramArrayOfIntraTrnRslt == null) {
      throw new Exception("Invalid IntraTrnRslt array is passed: null");
    }
    if (paramArrayOfIntraTrnRslt.length == 0)
    {
      FFSDebug.log("Empty IntraTrnRslt array list");
      return;
    }
    FFSDebug.log("BackendProcessor.processIntraTrnRslt: start, rsltArray.length=" + paramArrayOfIntraTrnRslt.length, 6);
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramArrayOfIntraTrnRslt[0].batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramArrayOfIntraTrnRslt[0].batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int j = 0; j < paramArrayOfIntraTrnRslt.length; j++) {
        if (paramArrayOfIntraTrnRslt[j].eventSequence == 1) {
          a(paramArrayOfIntraTrnRslt[j], localFFSConnectionHolder, null, false);
        }
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "Exception in BackendProcessor.processIntraTrnRslt: " + localException.toString();
      throw new BPWException(str);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log("BackendProcessor.processIntraTrnRslt: done", 6);
  }
  
  public void processImmIntraTrnRslt(FFSConnectionHolder paramFFSConnectionHolder, IntraTrnRslt paramIntraTrnRslt, String paramString)
    throws Exception
  {
    FFSDebug.log("BackendProcessor.processImmIntraTrnRslt: start", 6);
    try
    {
      a(paramIntraTrnRslt, paramFFSConnectionHolder, paramString, true);
    }
    catch (Exception localException)
    {
      String str = "Exception in BackendProcessor.processImmIntraTrnRslt: " + localException.toString();
      throw new BPWException(str);
    }
    FFSDebug.log("BackendProcessor.processImmIntraTrnRslt: done", 6);
  }
  
  private void a(IntraTrnRslt paramIntraTrnRslt, FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws Exception
  {
    AuditLogRecord localAuditLogRecord = null;
    if ((paramIntraTrnRslt.srvrTid == null) || (paramIntraTrnRslt.srvrTid.trim().equals("")))
    {
      FFSDebug.log("BackendProcessor.processOneRslt: call, SrvrTID=null or empty", 0);
      return;
    }
    FFSDebug.log("BackendProcessor.processOneRslt: call, SrvrTID=" + paramIntraTrnRslt.srvrTid, 6);
    a locala = new a(null);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("BackendProcessor.processOneRslt:AuditAgent is null!!");
    }
    locala.a = paramIntraTrnRslt.srvrTid;
    locala.jdField_int = paramIntraTrnRslt.status;
    locala.jdField_if = paramIntraTrnRslt.getStatusMsg();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    if (!paramBoolean)
    {
      locala.jdField_for = str1;
    }
    else
    {
      int i = Integer.parseInt(str1.substring(0, 8));
      int j = SmartCalendar.getPayday(paramString, i, "BookTransfer");
      locala.jdField_for = (Integer.toString(j) + "000000");
    }
    locala.jdField_do = XferProcessor.readStatusFromCode(locala.jdField_int);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if ((("FAILEDON".equalsIgnoreCase(locala.jdField_do)) || ("NOFUNDSON".equalsIgnoreCase(locala.jdField_do))) && (!paramBoolean))
    {
      FFSDebug.log("BackendProcessor.processOneRslt: Reverting limits for a failed transaction: ", locala.a, 6);
      localObject1 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, XferInstruction.getXferInstruction(paramFFSConnectionHolder, locala.a), new HashMap(), locala.jdField_do, true);
      FFSDebug.log("BackendProcessor.processOneRslt: Reverted limits for a failed transaction. status ", (String)localObject1, 6);
    }
    else
    {
      localObject1 = XferInstruction.getXferInstruction(paramFFSConnectionHolder, locala.a);
      TransferInfo localTransferInfo = new TransferInfo();
      localObject2 = null;
      localObject3 = null;
      localObject2 = paramIntraTrnRslt.getFromAmount() == null ? ((XferInstruction)localObject1).Amount : paramIntraTrnRslt.getFromAmount();
      localObject3 = paramIntraTrnRslt.getToAmount() == null ? ((XferInstruction)localObject1).ToAmount : paramIntraTrnRslt.getToAmount();
      localTransferInfo.setSrvrTId(locala.a);
      localTransferInfo.setPrcStatus(locala.jdField_do);
      localTransferInfo.setConfirmNum(paramIntraTrnRslt.confirmationNum);
      localTransferInfo.setAmount((String)localObject2);
      localTransferInfo.setToAmount((String)localObject3);
      XferInstruction.updateStatusAndConfirmNum(paramFFSConnectionHolder, localTransferInfo);
    }
    a(paramIntraTrnRslt, paramFFSConnectionHolder);
    if (!SrvrTrans.updateTransferStatus(paramFFSConnectionHolder, paramBoolean, locala.jdField_for, locala.jdField_do, paramIntraTrnRslt)) {
      return;
    }
    if ((locala.jdField_do.compareTo("POSTEDON") == 0) || (this.bD >= 4))
    {
      localObject1 = XferInstruction.getXferInstruction(paramFFSConnectionHolder, locala.a);
      localObject2 = null;
      int k;
      if (!paramBoolean)
      {
        k = 4650;
        if ((locala.jdField_int != 0) && (locala.jdField_if != null) && (locala.jdField_if.trim().length() > 0))
        {
          localObject3 = new String[] { locala.jdField_if };
          localObject2 = BPWLocaleUtil.getLocalizedMessage(926, (String[])localObject3, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        }
        else
        {
          localObject2 = BPWLocaleUtil.getLocalizedMessage(903, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        }
      }
      else
      {
        k = 4607;
        if ((locala.jdField_int != 0) && (locala.jdField_if != null) && (locala.jdField_if.trim().length() > 0))
        {
          localObject3 = new Object[1];
          localObject3[0] = locala.jdField_if;
          localObject2 = BPWLocaleUtil.getLocalizableMessage(927, (Object[])localObject3, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        }
        else
        {
          localObject2 = BPWLocaleUtil.getLocalizableMessage(904, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        }
      }
      if (locala.jdField_do.equals("POSTEDON")) {
        k = 3603;
      }
      localObject3 = new BigDecimal(((XferInstruction)localObject1).Amount);
      String str2 = BPWUtil.getAccountIDWithAccountType(((XferInstruction)localObject1).AcctDebitID, ((XferInstruction)localObject1).AcctDebitType);
      String str3 = BPWUtil.getAccountIDWithAccountType(((XferInstruction)localObject1).AcctCreditID, ((XferInstruction)localObject1).AcctCreditType);
      localAuditLogRecord = new AuditLogRecord(((XferInstruction)localObject1).SubmittedBy, 0, null, null, (ILocalizable)localObject2, ((XferInstruction)localObject1).LogID, k, Integer.parseInt(((XferInstruction)localObject1).CustomerID), (BigDecimal)localObject3, ((XferInstruction)localObject1).CurDef, new BigDecimal(((XferInstruction)localObject1).ToAmount), ((XferInstruction)localObject1).ToAmtCurrency, ((XferInstruction)localObject1).userAssignedAmount, ((XferInstruction)localObject1).SrvrTID, locala.jdField_do, str3, ((XferInstruction)localObject1).BankID, str2, ((XferInstruction)localObject1).fromBankID, 0);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
  }
  
  public void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws Exception
  {
    if (paramArrayOfPmtTrnRslt == null) {
      throw new Exception("Invalid PmtTrnRslt array is passed: null");
    }
    if (paramArrayOfPmtTrnRslt.length == 0)
    {
      FFSDebug.log("Empty PmtTrnRslt array list");
      return;
    }
    FFSDebug.log("BackendProcessor.processPmtTrnRslt: start, rsltArray.length=" + paramArrayOfPmtTrnRslt.length, 6);
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramArrayOfPmtTrnRslt[0].batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramArrayOfPmtTrnRslt[0].batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int j = 0; j < paramArrayOfPmtTrnRslt.length; j++) {
        processOnePmtRslt(paramArrayOfPmtTrnRslt[j], localFFSConnectionHolder);
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "Exception in BackendProcessor.processPmtTrnRslt: " + localException.toString();
      throw new BPWException(str);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log("BackendProcessor.processPmtTrnRslt: done", 6);
  }
  
  public void processOnePmtRslt(PmtTrnRslt paramPmtTrnRslt)
    throws Exception
  {
    if (paramPmtTrnRslt == null) {
      throw new Exception("Invalid PmtTrnRslt is passed: null");
    }
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramPmtTrnRslt.batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramPmtTrnRslt.batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      processOnePmtRslt(paramPmtTrnRslt, localFFSConnectionHolder);
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "Exception in BackendProcessor.processOnePmtRslt: " + localException.toString();
      throw new BPWException(str);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public void processOnePmtRslt(PmtTrnRslt paramPmtTrnRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
    PmtInfo localPmtInfo = localPmtInstruction.getPmtInfo();
    processOnePmtRslt(paramPmtTrnRslt, localPmtInfo.LogID, localPmtInfo.Status, localPmtInfo.FIID, paramFFSConnectionHolder);
  }
  
  public void processOneFailedPmt(PmtTrnRslt paramPmtTrnRslt, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    a(paramPmtTrnRslt, paramString1, paramString2, paramFFSConnectionHolder);
  }
  
  public void processOnePmtRslt(PmtTrnRslt paramPmtTrnRslt, String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    if ((!paramString2.equals("BATCH_INPROCESS")) && (!paramString2.equals("PROCESSEDON")))
    {
      FFSDebug.log("*** BackendProcessor.processOnePmtRslt: pmt not in BATCH_INPROCESS/PROCESSEDON state, SrvrTID=" + paramPmtTrnRslt.srvrTid + ",Status=" + paramString2);
      FFSDebug.console("*** BackendProcessor.processOnePmtRslt: pmt not in BATCH_INPROCESS/PROCESSEDON state, SrvrTID=" + paramPmtTrnRslt.srvrTid + ",Status=" + paramString2);
      return;
    }
    a(paramPmtTrnRslt, paramString1, paramString3, paramFFSConnectionHolder);
  }
  
  private void a(PmtTrnRslt paramPmtTrnRslt, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("BackendProcessor.processOnePmtRslt:AuditAgent is null!!");
    }
    String str1;
    Object localObject3;
    if (paramPmtTrnRslt.status == 99999)
    {
      localObject1 = PmtInstruction.getPmtInstr(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
      localObject2 = ((PmtInstruction)localObject1).getPmtInfo().getImmediateProcessing();
      if ((localObject2 != null) && (((Boolean)localObject2).booleanValue()))
      {
        localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
        if (localFulfillAgent == null) {
          throw new BPWException("FulfillAgent is null!!");
        }
        str1 = localFulfillAgent.findPaymentInstructionType(paramString2, ((PmtInstruction)localObject1).getRouteID());
        localObject3 = new ScheduleInfo();
        ((ScheduleInfo)localObject3).FIId = paramString2;
        ((ScheduleInfo)localObject3).Status = "Active";
        ((ScheduleInfo)localObject3).Frequency = 10;
        ((ScheduleInfo)localObject3).StartDate = ((PmtInstruction)localObject1).getStartDate();
        ((ScheduleInfo)localObject3).InstanceCount = 1;
        ((ScheduleInfo)localObject3).LogID = paramString1;
        try
        {
          ScheduleInfo.createSchedule(paramFFSConnectionHolder, str1, paramPmtTrnRslt.srvrTid, (ScheduleInfo)localObject3);
        }
        catch (Exception localException2)
        {
          throw new BPWException(localException2.getMessage());
        }
        PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtTrnRslt.srvrTid, "FUNDSALLOCATED");
        return;
      }
    }
    Object localObject1 = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    Object localObject2 = SrvrTrans.findResponseBySrvrTID(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
    FulfillAgent localFulfillAgent = localObject2[1];
    if (localObject2[0] == null) {
      throw new BPWException(" ** Response not found!");
    }
    try
    {
      str1 = null;
      if (localObject2[0].startsWith("OFX151"))
      {
        localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)((BPWMsgBroker)localObject1).parseMsg(localFulfillAgent, "PmtTrnRsV1", "OFX151");
        str1 = updatePmtStatus((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject3, paramPmtTrnRslt);
        localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject3, paramFFSConnectionHolder, str1);
      }
      else if (localObject2[0].startsWith("OFX200"))
      {
        localObject3 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)((BPWMsgBroker)localObject1).parseMsg(localFulfillAgent, "PmtTrnRsV1", "OFX200");
        str1 = updatePmtStatus((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject3, paramPmtTrnRslt);
        localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject3, paramFFSConnectionHolder, str1);
      }
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtTrnRslt.srvrTid, str1, paramPmtTrnRslt.extdPmtInfo);
      PmtInfo localPmtInfo;
      Object localObject4;
      String str3;
      if (str1.equals("FAILEDON"))
      {
        localObject3 = PmtInstruction.getPmtInstr(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
        localPmtInfo = ((PmtInstruction)localObject3).getPmtInfo();
        localObject4 = localPmtInfo.getImmediateFundAllocation();
        Object localObject5;
        if ((localObject4 == null) || (!((Boolean)localObject4).booleanValue()))
        {
          boolean bool1 = ScheduleInfo.checkExist(paramString2, "REVERTFUNDTRN", paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
          boolean bool2 = EventInfo.checkExist(paramFFSConnectionHolder, "REVERTFUNDTRN", paramPmtTrnRslt.srvrTid);
          boolean bool3 = EventInfoLog.checkExist(paramFFSConnectionHolder, "REVERTFUNDTRN", paramPmtTrnRslt.srvrTid);
          if ((!bool1) && (!bool2) && (!bool3))
          {
            localObject5 = new ScheduleInfo();
            ((ScheduleInfo)localObject5).FIId = paramString2;
            ((ScheduleInfo)localObject5).Status = "Active";
            ((ScheduleInfo)localObject5).Frequency = 10;
            ((ScheduleInfo)localObject5).StartDate = DBUtil.getCurrentStartDate();
            ((ScheduleInfo)localObject5).InstanceCount = 1;
            ((ScheduleInfo)localObject5).LogID = paramString1;
            ((ScheduleInfo)localObject5).CurInstanceNum = 1;
          }
        }
        else
        {
          int i = FundsAllocProcessor.revertFundImmediate(localPmtInfo, paramPmtTrnRslt.batchKey, false, null, paramFFSConnectionHolder);
          FFSDebug.log("BackendProcessor.processOnePmtRslt Fund reverted for: " + localPmtInfo.getSrvrTID(), 0);
          str3 = PmtInstruction.getStatus(localPmtInfo.getSrvrTID(), paramFFSConnectionHolder);
          if ((!str3.equalsIgnoreCase("FUNDSREVERTED")) && (!str3.equalsIgnoreCase("FUNDSREVERTFAILED")) && ((i == 0) || (i == 2000)))
          {
            BackendProcessor localBackendProcessor = new BackendProcessor();
            localObject5 = new PmtTrnRslt();
            ((PmtTrnRslt)localObject5).status = i;
            ((PmtTrnRslt)localObject5).srvrTid = localPmtInfo.getSrvrTID();
            ((PmtTrnRslt)localObject5).customerID = localPmtInfo.getCustomerID();
            ((PmtTrnRslt)localObject5).logID = localPmtInfo.getLogID();
            localBackendProcessor.processOneFundsRevertRslt((PmtTrnRslt)localObject5, paramFFSConnectionHolder);
          }
        }
      }
      localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      if (((PropertyConfig)localObject3).LogLevel >= 4)
      {
        localPmtInfo = PmtInstruction.getPmtInfo(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
        localObject4 = localPmtInfo.PayAcct;
        String str2 = localPmtInfo.AcctDebitID;
        str3 = localPmtInfo.AcctDebitType;
        int j = 4454;
        if (str1.equals("PROCESSEDON")) {
          j = 3601;
        }
        int k = Integer.parseInt(paramPmtTrnRslt.customerID);
        String str4 = BPWUtil.getAccountIDWithAccountType(str2, str3);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(802, null, "BILLPAY_AUDITLOG_MESSAGE");
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(300, arrayOfObject, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), localPmtInfo.submittedBy, null, null, localLocalizableString, paramString1, j, k, null, null, paramPmtTrnRslt.srvrTid, str1, (String)localObject4, null, str4, null, 0);
      }
    }
    catch (Exception localException1)
    {
      localObject3 = "Exception in BackendProcessor.processOnePmtRslt: " + localException1.toString();
      FFSDebug.log("Exception in BackendProcessor.processOnePmtRslt: " + FFSDebug.stackTrace(localException1));
      throw new BPWException((String)localObject3);
    }
  }
  
  public String updatePmtStatus(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtTrnRslt paramPmtTrnRslt)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = null;
    if (paramPmtTrnRslt.status == 0)
    {
      str2 = "PROCESSEDON";
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.PROCESSEDON;
    }
    else
    {
      str2 = "FAILEDON";
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
    }
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.TrnUID = "0";
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Code = paramPmtTrnRslt.status;
    if (paramPmtTrnRslt.status == 0)
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO;
    }
    else
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.MessageExists = true;
      if ((paramPmtTrnRslt.message != null) && (!paramPmtTrnRslt.message.equals(""))) {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Message = paramPmtTrnRslt.message;
      } else {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Message = BPWLocaleUtil.getMessage(2000, null, "OFX_MESSAGE");
      }
    }
    return str2;
  }
  
  public String updatePmtStatus(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtTrnRslt paramPmtTrnRslt)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = null;
    if (paramPmtTrnRslt.status == 0)
    {
      str2 = "PROCESSEDON";
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.PROCESSEDON;
    }
    else
    {
      str2 = "FAILEDON";
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
      paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
    }
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.TrnUID = "0";
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Code = paramPmtTrnRslt.status;
    if (paramPmtTrnRslt.status == 0)
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
    }
    else
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.ERROR;
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.MessageExists = true;
      if ((paramPmtTrnRslt.message != null) && (!paramPmtTrnRslt.message.equals(""))) {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Message = paramPmtTrnRslt.message;
      } else {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Message = BPWLocaleUtil.getMessage(2000, null, "OFX_MESSAGE");
      }
    }
    return str2;
  }
  
  public void processOneFundsRslt(PmtTrnRslt paramPmtTrnRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BackendProcessor.processOneFundsRslt SrvrTID=" + paramPmtTrnRslt.srvrTid, 6);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("BackendProcessor.processOneFundsRslt:AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
    String str1 = arrayOfString[1];
    if (arrayOfString[0] == null) {
      throw new BPWException(" ** Response not found!");
    }
    try
    {
      String str2 = null;
      if (arrayOfString[0].startsWith("OFX151"))
      {
        localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX151");
        str2 = updateFundsStatus((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject, paramPmtTrnRslt);
        localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject, paramFFSConnectionHolder, str2);
      }
      else if (arrayOfString[0].startsWith("OFX200"))
      {
        localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX200");
        str2 = updateFundsStatus((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject, paramPmtTrnRslt);
        localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject, paramFFSConnectionHolder, str2);
      }
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtTrnRslt.srvrTid, str2, paramPmtTrnRslt.extdPmtInfo);
      localObject = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      if (((PropertyConfig)localObject).LogLevel >= 4)
      {
        PmtInfo localPmtInfo = PmtInstruction.getPmtInfo(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
        if (localPmtInfo != null)
        {
          int i = Integer.parseInt(paramPmtTrnRslt.customerID);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(704, null, "BILLPAY_AUDITLOG_MESSAGE");
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(300, arrayOfObject, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), localPmtInfo.submittedBy, null, null, localLocalizableString, paramPmtTrnRslt.logID, 4450, i, null, null, paramPmtTrnRslt.srvrTid, str2, null, null, null, null, 0);
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject = "Exception in BackendProcessor.processOneFundsRslt: " + localException.toString();
      throw new BPWException((String)localObject);
    }
  }
  
  public static String updateFundsStatus(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtTrnRslt paramPmtTrnRslt)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = null;
    if (paramPmtTrnRslt.status != 0) {
      if (paramPmtTrnRslt.status == 10504)
      {
        str2 = "NOFUNDSON";
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.NOFUNDSON;
      }
      else if (paramPmtTrnRslt.status == 99998)
      {
        str2 = "PROCESSEDON";
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.PROCESSEDON;
      }
      else
      {
        str2 = "FUNDSFAILEDON";
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
      }
    }
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.TrnUID = "0";
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Code = paramPmtTrnRslt.status;
    if (paramPmtTrnRslt.status == 0)
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO;
    }
    else
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.MessageExists = true;
      if ((paramPmtTrnRslt.message != null) && (!paramPmtTrnRslt.message.equals(""))) {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Message = paramPmtTrnRslt.message;
      } else {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsV1Cm.Status.Message = BPWLocaleUtil.getMessage(17000, null, "OFX_MESSAGE");
      }
    }
    return str2;
  }
  
  public static String updateFundsStatus(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1 paramTypePmtTrnRsV1, PmtTrnRslt paramPmtTrnRslt)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = null;
    if (paramPmtTrnRslt.status != 0) {
      if (paramPmtTrnRslt.status == 10504)
      {
        str2 = "NOFUNDSON";
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.NOFUNDSON;
      }
      else if (paramPmtTrnRslt.status == 99998)
      {
        str2 = "PROCESSEDON";
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.PROCESSEDON;
      }
      else
      {
        str2 = "FUNDSFAILEDON";
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
        paramTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
      }
    }
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.TrnUID = "0";
    paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Code = paramPmtTrnRslt.status;
    if (paramPmtTrnRslt.status == 0)
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
    }
    else
    {
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.ERROR;
      paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.MessageExists = true;
      if ((paramPmtTrnRslt.message != null) && (!paramPmtTrnRslt.message.equals(""))) {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Message = paramPmtTrnRslt.message;
      } else {
        paramTypePmtTrnRsV1.PmtTrnRs.TrnRsCm.Status.Message = BPWLocaleUtil.getMessage(17000, null, "OFX_MESSAGE");
      }
    }
    return str2;
  }
  
  public static void updatePmtStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("updatePmtStatus starts. srvrTID; " + paramString1 + ", status: " + paramString2, 6);
    if ((paramString1 == null) || (paramString2 == null))
    {
      FFSDebug.log("updatePmtStatus failed. srvrTID; " + paramString1 + ", status: " + paramString2, 0);
      return;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String str1 = localSimpleDateFormat.format(new Date());
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString1, paramFFSConnectionHolder);
    String str2 = arrayOfString[1];
    Object localObject;
    if (arrayOfString[0].startsWith("OFX151"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str2, "PmtTrnRsV1", "OFX151");
      if (localObject != null)
      {
        if ("WILLPROCESSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.WILLPROCESSON;
        }
        else if ("PROCESSEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.PROCESSEDON;
        }
        else if ("NOFUNDSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.NOFUNDSON;
        }
        else if ("FUNDSFAILEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
        }
        else
        {
          throw new BPWException("Unsupported payment status: " + paramString2);
        }
      }
      else {
        throw new BPWException("No response found! srvrTID: " + paramString1);
      }
    }
    else if (arrayOfString[0].startsWith("OFX200"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str2, "PmtTrnRsV1", "OFX200");
      if (localObject != null)
      {
        if ("WILLPROCESSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.WILLPROCESSON;
        }
        else if ("NOFUNDSON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.NOFUNDSON;
        }
        else if ("FAILEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
        }
        else if ("PROCESSEDON".equalsIgnoreCase(paramString2))
        {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str1;
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.PROCESSEDON;
        }
        else
        {
          throw new BPWException("Unsupported payment status: " + paramString2);
        }
      }
      else {
        throw new BPWException("No response found! srvrTID: " + paramString1);
      }
    }
    FFSDebug.log("updatePmtStatus ends. srvrTID; " + paramString1 + ", status: " + paramString2, 6);
  }
  
  public void processOneFundsRevertRslt(PmtTrnRslt paramPmtTrnRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("BackendProcessor.processOneFundsRevertRslt SrvrTID=" + paramPmtTrnRslt.srvrTid, 6);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("BackendProcessor.processOneFundsRevertRslt:AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
    String str1 = arrayOfString[1];
    if (arrayOfString[0] == null) {
      throw new BPWException(" ** Response not found!");
    }
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
      str2 = localSimpleDateFormat.format(new Date());
      String str3 = paramPmtTrnRslt.status == 0 ? "FUNDSREVERTED" : "FUNDSREVERTFAILED";
      if (arrayOfString[0].startsWith("OFX151"))
      {
        localObject1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX151");
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject1).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str2;
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject1).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum.FAILEDON;
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject1).PmtTrnRs.TrnRsV1Cm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject1).PmtTrnRs.TrnRsV1Cm.Status.MessageExists = true;
        if ((paramPmtTrnRslt.message != null) && (!paramPmtTrnRslt.message.equals(""))) {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject1).PmtTrnRs.TrnRsV1Cm.Status.Message = paramPmtTrnRslt.message;
        }
        localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1)localObject1, paramFFSConnectionHolder, str3);
      }
      else if (arrayOfString[0].startsWith("OFX200"))
      {
        localObject1 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX200");
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject1).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = str2;
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject1).PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum.FAILEDON;
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject1).PmtTrnRs.TrnRsCm.Status.Severity = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.ERROR;
        ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject1).PmtTrnRs.TrnRsCm.Status.MessageExists = true;
        if ((paramPmtTrnRslt.message != null) && (!paramPmtTrnRslt.message.equals(""))) {
          ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject1).PmtTrnRs.TrnRsCm.Status.Message = paramPmtTrnRslt.message;
        }
        localAuditAgent.updatePmtRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1)localObject1, paramFFSConnectionHolder, str3);
      }
      Object localObject1 = null;
      if ("FUNDSREVERTED".equalsIgnoreCase(str3))
      {
        localObject1 = PmtInstruction.getPmtInfo(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
        FFSDebug.log("BackendProcessor.processOneFundsRevertRslt", ": Reverting limits for a failed transaction: ", ((PmtInfo)localObject1).SrvrTID, 6);
        localObject2 = RecPmtProcessor2.deleteLimit(paramFFSConnectionHolder, (PmtInfo)localObject1, new HashMap(), str3, true);
        FFSDebug.log("BackendProcessor.processOneFundsRevertRslt", ": Reverted limits for a failed transaction. status ", (String)localObject2, 6);
      }
      else
      {
        PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtTrnRslt.srvrTid, str3);
      }
      Object localObject2 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      if (((PropertyConfig)localObject2).LogLevel >= 4)
      {
        if (localObject1 == null) {
          localObject1 = PmtInstruction.getPmtInfo(paramPmtTrnRslt.srvrTid, paramFFSConnectionHolder);
        }
        if (localObject1 != null)
        {
          int i = Integer.parseInt(paramPmtTrnRslt.customerID);
          BigDecimal localBigDecimal = new BigDecimal(((PmtInfo)localObject1).getAmt());
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(706, null, "BILLPAY_AUDITLOG_MESSAGE");
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(300, arrayOfObject, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((PmtInfo)localObject1).submittedBy, null, null, localLocalizableString, paramPmtTrnRslt.logID, 4453, i, localBigDecimal, ((PmtInfo)localObject1).getCurDef(), paramPmtTrnRslt.srvrTid, str3, null, null, null, null, 0);
        }
      }
    }
    catch (Exception localException)
    {
      String str2 = "Exception in BackendProcessor.processOneFundsRevertRslt: " + localException.toString();
      throw new BPWException(str2);
    }
  }
  
  public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws Exception
  {
    if (paramArrayOfCustPayeeRslt == null) {
      throw new Exception("Invalid CustPayeeRslt array is passed: null");
    }
    if (paramArrayOfCustPayeeRslt.length == 0)
    {
      FFSDebug.log("Empty CustPayeeRslt array list");
      return;
    }
    FFSDebug.log("BackendProcessor.processCustPayeeRslt: start, rsltArray.length=" + paramArrayOfCustPayeeRslt.length, 6);
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramArrayOfCustPayeeRslt[0].batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramArrayOfCustPayeeRslt[0].batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int j = 0; j < paramArrayOfCustPayeeRslt.length; j++) {
        processOneCustPayeeRslt(paramArrayOfCustPayeeRslt[j], localFFSConnectionHolder);
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "Exception in BackendProcessor.processCustPayeeRslt: " + localException.toString();
      throw new BPWException(str);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log("BackendProcessor.processCustPayeeRslt: done", 6);
  }
  
  public void processOneCustPayeeRslt(CustPayeeRslt paramCustPayeeRslt)
    throws Exception
  {
    if (paramCustPayeeRslt == null) {
      throw new Exception("Invalid CustPayeeRslt is passed: null");
    }
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramCustPayeeRslt.batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramCustPayeeRslt.batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      processOneCustPayeeRslt(paramCustPayeeRslt, localFFSConnectionHolder);
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "Exception in BackendProcessor.processOneCustPayeeRslt: " + localException.toString();
      throw new BPWException(str);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    if (paramArrayOfCustPayeeRslt == null) {
      return;
    }
    FFSDebug.log("BackendProcessor.processCustPayeeRslt: start, rsltArray.length=" + paramArrayOfCustPayeeRslt.length, 6);
    for (int i = 0; i < paramArrayOfCustPayeeRslt.length; i++) {
      processOneCustPayeeRslt(paramArrayOfCustPayeeRslt[i], paramFFSConnectionHolder);
    }
    FFSDebug.log("BackendProcessor.processCustPayeeRslt: done", 6);
  }
  
  public void processOneCustPayeeRslt(CustPayeeRslt paramCustPayeeRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = null;
    if (paramCustPayeeRslt.status == 0)
    {
      if (paramCustPayeeRslt.action.equals("CANC")) {
        str1 = "CLOSED";
      } else {
        str1 = "ACTIVE";
      }
    }
    else {
      str1 = "ERROR";
    }
    try
    {
      CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getCustPayeeRoute2(paramCustPayeeRslt.customerID, paramCustPayeeRslt.payeeListID, paramCustPayeeRslt.routeID, paramFFSConnectionHolder);
      if (localCustPayeeRoute != null)
      {
        str2 = localCustPayeeRoute.Status;
        if ((!str2.startsWith("MOD")) && (!str2.equals("CANC")))
        {
          CustPayeeRoute.updateCustPayeeRouteStatus(paramCustPayeeRslt.customerID, paramCustPayeeRslt.payeeListID, paramCustPayeeRslt.routeID, str1, paramFFSConnectionHolder);
          if (str1.equals("CLOSED")) {
            CustPayee.updateStatus(paramCustPayeeRslt.customerID, paramCustPayeeRslt.payeeListID, str1, paramCustPayeeRslt.status, paramCustPayeeRslt.message, paramFFSConnectionHolder);
          }
        }
      }
    }
    catch (Exception localException)
    {
      String str2 = "Exception in BackendProcessor.processOneCustPayeeRslt: " + localException.toString();
      throw new BPWException(str2);
    }
  }
  
  public String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws BPWException
  {
    if (paramPayeeInfo.PayeeType == 3) {
      throw new BPWException("addPayeeFromBackend: PayeeType should not be PERSONAL payee!");
    }
    paramPayeeInfo.trim();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str = null;
    try
    {
      Payee localPayee = new Payee(paramPayeeInfo);
      PayeeInfo[] arrayOfPayeeInfo = Payee.findGlobalPayeeByName(paramPayeeInfo.PayeeName, localFFSConnectionHolder);
      str = localPayee.matchPayee(arrayOfPayeeInfo);
      if (str == null)
      {
        localPayee.setPayeeID();
        localPayee.storeToDB(localFFSConnectionHolder);
        str = localPayee.getPayeeID();
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localBPWException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return str;
  }
  
  public PayeeInfo[] updatePayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws Exception
  {
    if (paramPayeeInfo.PayeeType == 3) {
      throw new BPWException("updatePayeeFromBackend: PayeeType should not be PERSONAL payee!");
    }
    paramPayeeInfo.trim();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      Payee localPayee = new Payee(paramPayeeInfo);
      PayeeInfo localPayeeInfo;
      if ((paramPayeeInfo.PayeeID != null) && (!paramPayeeInfo.PayeeID.equals("0")))
      {
        localPayeeInfo = Payee.findPayeeByID(paramPayeeInfo.PayeeID, localFFSConnectionHolder);
        if (localPayeeInfo != null)
        {
          localPayee.update(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
          if (localPayeeInfo.getRouteID() != paramPayeeInfo.getRouteID()) {
            a(paramPayeeInfo.PayeeID, paramPayeeInfo.getRouteID(), localFFSConnectionHolder);
          }
        }
      }
      else if ((paramPayeeInfo.ExtdPayeeID != null) && (!paramPayeeInfo.ExtdPayeeID.equals("0")))
      {
        localPayeeInfo = Payee.findPayeeByExtendedID(paramPayeeInfo.ExtdPayeeID, localFFSConnectionHolder);
        if (localPayeeInfo != null)
        {
          localPayee.setPayeeID(localPayeeInfo.PayeeID);
          localPayee.update(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
          if (localPayeeInfo.getRouteID() != paramPayeeInfo.getRouteID()) {
            a(paramPayeeInfo.PayeeID, paramPayeeInfo.getRouteID(), localFFSConnectionHolder);
          }
        }
      }
      else
      {
        arrayOfPayeeInfo = Payee.findPayeeByName(paramPayeeInfo.PayeeName, "exact", localFFSConnectionHolder);
        if (arrayOfPayeeInfo.length == 0)
        {
          localPayee.setPayeeID();
          localPayee.storeToDB(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
        }
        else if (arrayOfPayeeInfo.length == 1)
        {
          localPayee.setPayeeID(arrayOfPayeeInfo[0].PayeeID);
          localPayee.setExtdPayeeID(arrayOfPayeeInfo[0].ExtdPayeeID);
          localPayee.update(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
          if (arrayOfPayeeInfo[0].getRouteID() != paramPayeeInfo.getRouteID()) {
            a(paramPayeeInfo.PayeeID, paramPayeeInfo.getRouteID(), localFFSConnectionHolder);
          }
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localBPWException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  private void a(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID,Status,PayeeListID FROM BPW_CustomerPayee WHERE PayeeID=?", new Object[] { paramString });
    while (localFFSResultSet.getNextRow())
    {
      String str1 = localFFSResultSet.getColumnString(1);
      int i = localFFSResultSet.getColumnInt(3);
      CustPayeeRoute[] arrayOfCustPayeeRoute1 = CustPayeeRoute.getCustPayeeRoute(str1, i, paramFFSConnectionHolder);
      int j = 0;
      for (int k = 0; k < arrayOfCustPayeeRoute1.length; k++) {
        if (arrayOfCustPayeeRoute1[k].RouteID == paramInt)
        {
          j = 1;
          if ((!arrayOfCustPayeeRoute1[k].Status.equals("NEW")) && (!arrayOfCustPayeeRoute1[k].Status.equals("ACTIVE")) && (!arrayOfCustPayeeRoute1[k].Status.equals("INPROCESS")) && (arrayOfCustPayeeRoute1[k].Status.indexOf("MOD") == -1)) {
            if (arrayOfCustPayeeRoute1[k].Status.equals("CANC"))
            {
              CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, arrayOfCustPayeeRoute1[k].RouteID, "ACTIVE", paramFFSConnectionHolder);
            }
            else if (arrayOfCustPayeeRoute1[k].Status.equals("CANC_INPROCESS"))
            {
              CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, arrayOfCustPayeeRoute1[k].RouteID, "CLOSED", paramFFSConnectionHolder);
              j = 0;
            }
            else
            {
              throw new BPWException("updatePayeeFromBackend: CustPayeeRoute Unknown status=" + arrayOfCustPayeeRoute1[k].Status + ",CustomerID=" + str1 + ",PayeeListID=" + i + ",PayeeID=" + paramString);
            }
          }
        }
        else
        {
          if ((arrayOfCustPayeeRoute1[k].Status.equals("ACTIVE")) || (arrayOfCustPayeeRoute1[k].Status.equals("INPROCESS")) || (arrayOfCustPayeeRoute1[k].Status.indexOf("MOD") != -1)) {
            CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, arrayOfCustPayeeRoute1[k].RouteID, "CANC", paramFFSConnectionHolder);
          } else if (arrayOfCustPayeeRoute1[k].Status.equals("NEW")) {
            CustPayeeRoute.updateCustPayeeRouteStatus(str1, i, arrayOfCustPayeeRoute1[k].RouteID, "CLOSED", paramFFSConnectionHolder);
          }
          String str2 = BPWRegistryUtil.getProperty("AutoDeenroll", "false");
          FFSDebug.log("changePayeeRouteID: autoDeenroll=" + str2, 6);
          if ((str2.equalsIgnoreCase("true")) && (CustRoute.getCustomerRoute(str1, arrayOfCustPayeeRoute1[k].RouteID, paramFFSConnectionHolder) != null))
          {
            CustPayeeRoute[] arrayOfCustPayeeRoute2 = CustPayeeRoute.getAllCustPayeeRoute(str1, paramFFSConnectionHolder);
            int m = 0;
            for (int n = 0; n < arrayOfCustPayeeRoute2.length; n++) {
              if ((arrayOfCustPayeeRoute2[n].Status.indexOf("CANC") == -1) && (!arrayOfCustPayeeRoute2[n].Status.equals("CLOSED")))
              {
                m++;
                break;
              }
            }
            if (m == 0)
            {
              CustomerRouteInfo localCustomerRouteInfo = CustRoute.getCustomerRoute(str1, arrayOfCustPayeeRoute1[k].RouteID, paramFFSConnectionHolder);
              if (localCustomerRouteInfo.Status.equals("NEW")) {
                CustRoute.deleteCustRoute(str1, arrayOfCustPayeeRoute1[k].RouteID, paramFFSConnectionHolder);
              } else if (localCustomerRouteInfo.Status.indexOf("CANC") == -1) {
                CustRoute.updateCustRouteStatus(str1, arrayOfCustPayeeRoute1[k].RouteID, "CANC", paramFFSConnectionHolder);
              }
            }
          }
        }
      }
      if (j == 0)
      {
        CustPayeeRoute.addCustPayeeRoute(str1, i, paramInt, paramFFSConnectionHolder);
        if ((CustRoute.getCustomerRoute(str1, paramInt, paramFFSConnectionHolder) == null) && (PmtInstruction.hasPendingPmt(str1, i, paramFFSConnectionHolder))) {
          CustRoute.create(paramFFSConnectionHolder, str1, paramInt);
        }
      }
    }
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
      FFSDebug.log("*** BackendProcessor.changePayeeRouteID failed to close:" + localException.toString(), 0);
    }
  }
  
  public void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeRouteInfo);
      localPayeeToRoute.storeToDB(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
    throws Exception
  {}
  
  public PmtInfo[] getFailedPmt(String paramString)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    Vector localVector1;
    try
    {
      localVector1 = PmtInstruction.getPmtByStatusAndCustomerID("FAILEDON", paramString, localFFSConnectionHolder);
      Vector localVector2 = PmtInstruction.getPmtByStatusAndCustomerID("NOFUNDSON", paramString, localFFSConnectionHolder);
      Vector localVector3 = PmtInstruction.getPmtByStatusAndCustomerID("FUNDSFAILEDON", paramString, localFFSConnectionHolder);
      Vector localVector4 = PmtInstruction.getPmtByStatusAndCustomerID("FAILEDON_NOTIF", paramString, localFFSConnectionHolder);
      Vector localVector5 = PmtInstruction.getPmtByStatusAndCustomerID("NOFUNDSON_NOTIF", paramString, localFFSConnectionHolder);
      Vector localVector6 = PmtInstruction.getPmtByStatusAndCustomerID("FUNDSFAILEDON_NOTIF", paramString, localFFSConnectionHolder);
      localVector1.addAll(localVector2);
      localVector1.addAll(localVector3);
      localVector1.addAll(localVector4);
      localVector1.addAll(localVector5);
      localVector1.addAll(localVector6);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return (PmtInfo[])localVector1.toArray(new PmtInfo[0]);
  }
  
  public PmtInfo[] getNewFailedPmt()
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    Vector localVector1;
    try
    {
      localVector1 = PmtInstruction.getPmtByStatus("FAILEDON", localFFSConnectionHolder);
      Vector localVector2 = PmtInstruction.getPmtByStatus("NOFUNDSON", localFFSConnectionHolder);
      Vector localVector3 = PmtInstruction.getPmtByStatus("FUNDSFAILEDON", localFFSConnectionHolder);
      localVector1.addAll(localVector2);
      localVector1.addAll(localVector3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return (PmtInfo[])localVector1.toArray(new PmtInfo[0]);
  }
  
  public IntraTrnInfo[] getFailedXfer(String paramString)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    Vector localVector1;
    try
    {
      localVector1 = XferInstruction.getXferByStatusAndCustomerID("FAILEDON", paramString, localFFSConnectionHolder);
      Vector localVector2 = XferInstruction.getXferByStatusAndCustomerID("NOFUNDSON", paramString, localFFSConnectionHolder);
      Vector localVector3 = XferInstruction.getXferByStatusAndCustomerID("FAILEDON_NOTIF", paramString, localFFSConnectionHolder);
      Vector localVector4 = XferInstruction.getXferByStatusAndCustomerID("NOFUNDSON_NOTIF", paramString, localFFSConnectionHolder);
      localVector1.addAll(localVector2);
      localVector1.addAll(localVector3);
      localVector1.addAll(localVector4);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return (IntraTrnInfo[])localVector1.toArray(new IntraTrnInfo[0]);
  }
  
  public IntraTrnInfo[] getNewFailedXfer()
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    Vector localVector1;
    try
    {
      localVector1 = XferInstruction.getXferByStatus("FAILEDON", localFFSConnectionHolder);
      Vector localVector2 = XferInstruction.getXferByStatus("NOFUNDSON", localFFSConnectionHolder);
      localVector1.addAll(localVector2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return (IntraTrnInfo[])localVector1.toArray(new IntraTrnInfo[0]);
  }
  
  public String getPmtStatus(String paramString)
    throws BPWException
  {
    String str1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      str1 = PmtInstruction.getStatus(paramString, localFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      String str2 = "Exception in BackendProcessor.getPmtStatus: " + FFSDebug.stackTrace(localException);
      throw new BPWException(str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return str1;
  }
  
  private String[] jdMethod_new(String paramString)
    throws BPWException
  {
    String[] arrayOfString = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      arrayOfString = PayeeEditMask.getPayeeEditMask(paramString, localFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      String str = "Exception in BackendProcessor.getPayeeEditMask: " + localException.toString();
      throw new BPWException(str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfString;
  }
  
  public boolean checkPayeeEditMask(String paramString1, String paramString2)
    throws Exception
  {
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      return false;
    }
    String[] arrayOfString = jdMethod_new(paramString1);
    if (arrayOfString != null) {
      for (int i = 0; i < arrayOfString.length; i++) {
        if (paramString2.length() == arrayOfString[i].length())
        {
          char[] arrayOfChar1 = arrayOfString[i].toCharArray();
          char[] arrayOfChar2 = paramString2.toCharArray();
          int j = 1;
          for (int k = 0; k < arrayOfChar1.length; k++) {
            if (!a(arrayOfChar1[k], arrayOfChar2[k])) {
              j = 0;
            }
          }
          if (j != 0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  private boolean a(char paramChar1, char paramChar2)
  {
    if (paramChar1 == '*') {
      return Character.isLetter(paramChar2);
    }
    if (paramChar1 == '&') {
      return Character.isLetterOrDigit(paramChar2);
    }
    if (Character.isDigit(paramChar1)) {
      return paramChar2 == paramChar1;
    }
    if (Character.isLetterOrDigit(paramChar1)) {
      return paramChar2 == paramChar1;
    }
    return false;
  }
  
  public PayeeInfo[] getPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.getPayees(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public String[] getPayeeNames(String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String[] arrayOfString = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfString = Payee.getPayeeNames(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayeeNames failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfString;
  }
  
  public String[] getPayeeIDs(String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String[] arrayOfString = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfString = Payee.getPayeeIDs(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayeeIDs failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfString;
  }
  
  public void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Payee.updatePayee(localFFSConnectionHolder, paramPayeeInfo);
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeRouteInfo);
      localPayeeToRoute.update(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***BackendProcessor.updatePayee failed: " + FFSDebug.stackTrace(localException1));
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      throw localException1;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public void deletePayee(String paramString)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      CommonProcessor.deletePayee(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***BackendProcessor.deletePayee failed: " + FFSDebug.stackTrace(localException1));
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      throw localException1;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public void deletePayees(String[] paramArrayOfString)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      for (int i = 0; i < paramArrayOfString.length; i++) {
        CommonProcessor.deletePayee(localFFSConnectionHolder, paramArrayOfString[i]);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***BackendProcessor.deletePayees failed: " + FFSDebug.stackTrace(localException1));
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      throw localException1;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public PayeeInfo findPayeeByID(String paramString)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo localPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localPayeeInfo = Payee.findPayeeByID(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.findPayeeByID failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localPayeeInfo;
  }
  
  public void setPayeeStatus(String paramString1, String paramString2)
    throws Exception
  {
    if (paramString2.equalsIgnoreCase("ACTIVE")) {
      paramString2 = "ACTIVE";
    } else if (paramString2.equalsIgnoreCase("INACTIVE")) {
      paramString2 = "INACTIVE";
    } else {
      throw new Exception("***BackendProcessor.setPayeeStatus failed: Status=" + paramString2 + " is not acceptable");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      PayeeInfo localPayeeInfo = null;
      localPayeeInfo = Payee.findPayeeByID(localFFSConnectionHolder, paramString1);
      if (localPayeeInfo == null) {
        throw new Exception("***BackendProcessor.setPayeeStatus failed: PayeeID=" + paramString1 + " not found");
      }
      Payee.updateStatus(paramString1, paramString2, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***BackendProcessor.setPayeeStatus failed: " + localException1.toString());
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      throw localException1;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public PayeeInfo[] getMostUsedPayees(int paramInt)
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      arrayOfPayeeInfo = Payee.getMostUsedPayees(localFFSConnectionHolder, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getMostUsedPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getLinkedPayees()
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      arrayOfPayeeInfo = Payee.getLinkedPayees(localFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getLinkedPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] searchGlobalPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.searchGlobalPayees(paramString, localFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.searchGlobalPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getPreferedPayees(String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.getPreferedPayees(localFFSConnectionHolder, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPreferedPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getPayees(String paramString, int paramInt)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = getPayees(localFFSConnectionHolder, paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getPayees(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      arrayOfPayeeInfo = Payee.getPayees(paramFFSConnectionHolder, paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo[] getGlobalPayees(String paramString, int paramInt)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfPayeeInfo = Payee.getGlobalPayees(localFFSConnectionHolder, paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayees failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public String[] getPayeeNames(String paramString, int paramInt)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String[] arrayOfString = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfString = Payee.getPayeeNames(localFFSConnectionHolder, paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("***BackendProcessor.getPayeeNames failed: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfString;
  }
  
  public String addPayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws Exception
  {
    if (paramPayeeInfo.PayeeType == 3) {
      throw new BPWException("addPayee: PayeeType should not be PERSONAL payee!");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str = null;
    try
    {
      Payee localPayee = new Payee(paramPayeeInfo);
      PayeeInfo[] arrayOfPayeeInfo = Payee.getGlobalPayees(localFFSConnectionHolder, paramPayeeInfo.PayeeName, paramInt);
      if (arrayOfPayeeInfo != null)
      {
        str = localPayee.matchPayee(arrayOfPayeeInfo);
        FFSDebug.log("Found payee with the same name and address. Payee will not be added");
      }
      if (str == null)
      {
        localPayee.setPayeeID();
        localPayee.storeToDB(localFFSConnectionHolder);
        str = localPayee.getPayeeID();
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localBPWException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return str;
  }
  
  public PayeeInfo[] updatePayee(PayeeInfo paramPayeeInfo, int paramInt)
    throws Exception
  {
    if (paramPayeeInfo.PayeeType == 3) {
      throw new BPWException("updatePayee: PayeeType should not be PERSONAL payee!");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    PayeeInfo[] arrayOfPayeeInfo = null;
    try
    {
      Payee localPayee = new Payee(paramPayeeInfo);
      PayeeInfo localPayeeInfo;
      if ((paramPayeeInfo.PayeeID != null) && (!paramPayeeInfo.PayeeID.equals("0")))
      {
        localPayeeInfo = Payee.findPayeeByID(paramPayeeInfo.PayeeID, localFFSConnectionHolder);
        if (localPayeeInfo != null)
        {
          localPayee.update(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
        }
      }
      else if ((paramPayeeInfo.ExtdPayeeID != null) && (!paramPayeeInfo.ExtdPayeeID.equals("0")))
      {
        localPayeeInfo = Payee.findPayeeByExtendedID(paramPayeeInfo.ExtdPayeeID, localFFSConnectionHolder);
        if (localPayeeInfo != null)
        {
          localPayee.setPayeeID(localPayeeInfo.PayeeID);
          localPayee.update(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
        }
      }
      else
      {
        arrayOfPayeeInfo = getPayees(localFFSConnectionHolder, paramPayeeInfo.PayeeName, paramInt);
        if (arrayOfPayeeInfo.length == 0)
        {
          localPayee.setPayeeID();
          localPayee.storeToDB(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
        }
        else if (arrayOfPayeeInfo.length == 1)
        {
          localPayee.setPayeeID(arrayOfPayeeInfo[0].PayeeID);
          localPayee.setExtdPayeeID(arrayOfPayeeInfo[0].ExtdPayeeID);
          localPayee.update(localFFSConnectionHolder);
          arrayOfPayeeInfo = new PayeeInfo[1];
          arrayOfPayeeInfo[0] = localPayee.getPayeeInfo();
        }
        else
        {
          FFSDebug.log("Warning: More than one payee with the same info found!");
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localBPWException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new BPWException(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfPayeeInfo;
  }
  
  public PayeeInfo getPayeeByListId(String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("BackendProcessor.getPayeeByListId start...", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PayeeInfo localPayeeInfo = null;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localPayeeInfo = Payee.getPayeeByListId(localFFSConnectionHolder, paramString1, paramString2);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("***BackendProcessor.getPayeeByListId failed: " + FFSDebug.stackTrace(localThrowable));
      throw new Exception("BackendProcessor.getPayeeByListId failed");
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log("BackendProcessor.getPayeeByListId end...", 6);
    return localPayeeInfo;
  }
  
  private void a(IntraTrnRslt paramIntraTrnRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (paramIntraTrnRslt != null)
    {
      HashMap localHashMap = (HashMap)paramIntraTrnRslt.extraFields;
      if ((localHashMap != null) && (localHashMap.size() > 0))
      {
        Set localSet = localHashMap.keySet();
        Iterator localIterator = localSet.iterator();
        ValueInfo localValueInfo = null;
        String str = null;
        while (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          if ((str != null) && (str.trim().length() > 0))
          {
            localValueInfo = (ValueInfo)localHashMap.get(str);
            if ((localValueInfo != null) && (localValueInfo.getAction() != null) && (localValueInfo.getAction().trim().length() != 0)) {
              if ("Add".equalsIgnoreCase(localValueInfo.getAction())) {
                BPWXferExtraInfo.insertValue(paramIntraTrnRslt.srvrTid, str, (String)localValueInfo.getValue(), paramFFSConnectionHolder);
              } else if ("Mod".equalsIgnoreCase(localValueInfo.getAction())) {
                BPWXferExtraInfo.updateValue(paramIntraTrnRslt.srvrTid, str, (String)localValueInfo.getValue(), paramFFSConnectionHolder);
              } else if ("Del".equalsIgnoreCase(localValueInfo.getAction())) {
                BPWXferExtraInfo.deleteValue(paramIntraTrnRslt.srvrTid, str, paramFFSConnectionHolder);
              }
            }
          }
        }
      }
    }
  }
  
  private class a
  {
    public String a;
    public String jdField_for;
    public String jdField_do;
    public int jdField_int;
    public String jdField_if;
    
    private a() {}
    
    a(BackendProcessor.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.BackendProcessor
 * JD-Core Version:    0.7.0.1
 */