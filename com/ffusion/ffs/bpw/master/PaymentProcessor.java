package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.FundsAllocator;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PaymentBatch;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.enums.FrequencyType;
import com.ffusion.ffs.bpw.enums.PaymentType;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
import com.ffusion.ffs.bpw.interfaces.LastPaymentInfo;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.bpw.util.BillPayOFX200PaymentMapper;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class PaymentProcessor
  implements DBConsts, FFSConst, BPWResource
{
  private PropertyConfig bN = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int bI = 1;
  private boolean bH = false;
  private boolean bM = false;
  private boolean bK = false;
  private boolean bL = false;
  private String bJ = null;
  
  public PmtInfo addBillPayment(PmtInfo paramPmtInfo)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.addBillPayment:", " Start ", 6);
    if (paramPmtInfo == null)
    {
      paramPmtInfo = new PmtInfo();
      paramPmtInfo.setStatusCode(16000);
      localObject1 = "PmtInfo object  is null";
      paramPmtInfo.setStatusMsg((String)localObject1);
      FFSDebug.log("PaymentProcessor.addBillPayment:", (String)localObject1, 0);
      return paramPmtInfo;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
      Object localObject2;
      if (((FFSConnectionHolder)localObject1).conn == null)
      {
        localObject2 = "PaymentProcessor.addBillPayment: Can not get DB Connection.";
        FFSDebug.log((String)localObject2, 0);
        throw new BPWException((String)localObject2);
      }
      paramPmtInfo = jdMethod_goto((FFSConnectionHolder)localObject1, paramPmtInfo);
      if (paramPmtInfo.getStatusCode() != 0)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        localObject2 = paramPmtInfo;
        return localObject2;
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException1)
    {
      if (((FFSConnectionHolder)localObject1).conn != null) {
        ((FFSConnectionHolder)localObject1).conn.rollback();
      }
      FFSDebug.log(localException1, "*** PaymentProcessor.addBillPayment failed:" + localException1.getMessage());
      throw new BPWException("Add BillPayment failed: " + localException1.getMessage());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.addBillPayment failed:" + localException2.getMessage());
      }
    }
    FFSDebug.log("PaymentProcessor.addBillPayment:", " end ", 6);
    return paramPmtInfo;
  }
  
  private CustPayeeRoute jdMethod_if(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    try
    {
      return CustPayeeRoute.getActiveCustPayeeRoute(paramString, paramInt, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
  }
  
  private PmtInfo jdMethod_goto(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    String str = "PaymentProcessor.addBillPayment(conn, ..):";
    FFSDebug.log(str, " Start ", 6);
    if (paramPmtInfo.getPaymentType() == null)
    {
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "PmtInfo", "PaymentType" }, "PAYMENT_MESSAGE"));
      return paramPmtInfo;
    }
    PaymentType localPaymentType = PaymentType.getEnum(paramPmtInfo.getPaymentType());
    if (localPaymentType == null)
    {
      paramPmtInfo.setStatusCode(26034);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(26034, new String[] { paramPmtInfo.getPaymentType() }, "PAYMENT_MESSAGE"));
      return paramPmtInfo;
    }
    if (localPaymentType.isTemplate()) {
      return jdMethod_long(paramFFSConnectionHolder, paramPmtInfo);
    }
    return (paramPmtInfo instanceof RecPmtInfo) ? a(paramFFSConnectionHolder, (RecPmtInfo)paramPmtInfo) : jdMethod_char(paramFFSConnectionHolder, paramPmtInfo);
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    FFSDebug.log("^^^ PaymentProcessor.addRecPayment: started ^^^", 6);
    if (!jdMethod_do(paramFFSConnectionHolder, paramRecPmtInfo)) {
      return paramRecPmtInfo;
    }
    if (paramRecPmtInfo.getLogId() == null) {
      paramRecPmtInfo.setLogId(BPWTrackingIDGenerator.getNextId());
    }
    paramRecPmtInfo = (RecPmtInfo)a(paramFFSConnectionHolder, paramRecPmtInfo);
    paramRecPmtInfo.setStartDate(a(paramRecPmtInfo.getStartDate()));
    if (paramRecPmtInfo.getStatusCode() != 0) {
      return paramRecPmtInfo;
    }
    RecPmtInstruction localRecPmtInstruction = new RecPmtInstruction();
    FFSDebug.log("Computing end date: startDate = " + paramRecPmtInfo.getStartDate(), 6);
    FFSDebug.log("Computing end date: frequency = " + paramRecPmtInfo.getRecFrequencyValue(), 6);
    FFSDebug.log("Computing end date: frequency = " + paramRecPmtInfo.getInstanceCount(), 6);
    paramRecPmtInfo.setEndDate(CommonProcessor.getEndDate(paramRecPmtInfo.getStartDate(), paramRecPmtInfo.getRecFrequencyValue(), paramRecPmtInfo.getInstanceCount()));
    paramRecPmtInfo.DateCreate = paramRecPmtInfo.getOriginatedDate();
    paramRecPmtInfo.setStatus("WILLPROCESSON");
    FFSDebug.log("end date set to " + paramRecPmtInfo.getEndDate(), 6);
    localRecPmtInstruction.setRecPmtInfo(paramRecPmtInfo);
    localRecPmtInstruction.setRecSrvrTID();
    FFSDebug.log("FIID = " + paramRecPmtInfo.getFIID(), 6);
    FFSDebug.log("Adding rec pmt info " + paramRecPmtInfo, 6);
    a(paramFFSConnectionHolder, localRecPmtInstruction);
    jdMethod_if(paramFFSConnectionHolder, paramRecPmtInfo);
    PmtInfo localPmtInfo = jdMethod_char(paramFFSConnectionHolder, a(paramRecPmtInfo));
    if (localPmtInfo.getStatusCode() != 0)
    {
      paramRecPmtInfo.setStatusCode(localPmtInfo.getStatusCode());
      paramRecPmtInfo.setStatusMsg(localPmtInfo.getStatusMsg());
      return paramRecPmtInfo;
    }
    try
    {
      RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, paramRecPmtInfo.getRecSrvrTID(), localPmtInfo.getSrvrTID());
    }
    catch (Exception localException1)
    {
      throw new BPWException("Unable to add RecSrvrTIDToSrvrTID: " + localException1.getMessage());
    }
    if (this.bI >= 3)
    {
      int i = Integer.parseInt(localRecPmtInstruction.getCustomerID());
      BigDecimal localBigDecimal = FFSUtil.getBigDecimal(localRecPmtInstruction.getAmt());
      String str1 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
      String str2 = BPWLocaleUtil.getMessage(811, null, "BILLPAY_AUDITLOG_MESSAGE");
      String[] arrayOfString = { str2 };
      LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
      try
      {
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramRecPmtInfo.getSubmittedBy(), paramRecPmtInfo.getAgentId(), paramRecPmtInfo.getAgentType(), localLocalizableString, paramRecPmtInfo.getLogId(), 4408, i, localBigDecimal, localRecPmtInstruction.getRecPmtInfo().CurDef, paramRecPmtInfo.getRecSrvrTID(), localRecPmtInstruction.getStatus(), localRecPmtInstruction.getPayAcct(), null, str1, null, 0);
      }
      catch (Exception localException2)
      {
        throw new BPWException("Unable to log rec pmt add to audit log: " + localException2.getMessage());
      }
    }
    return paramRecPmtInfo;
  }
  
  private PmtInfo a(RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    PmtInfo localPmtInfo = new PmtInfo();
    try
    {
      localPmtInfo.AcctDebitID = paramRecPmtInfo.AcctDebitID;
      localPmtInfo.AcctDebitType = paramRecPmtInfo.AcctDebitType;
      localPmtInfo.setAmt(paramRecPmtInfo.getAmt());
      localPmtInfo.BankID = paramRecPmtInfo.BankID;
      localPmtInfo.CurDef = paramRecPmtInfo.CurDef;
      localPmtInfo.CustomerID = paramRecPmtInfo.CustomerID;
      localPmtInfo.FIID = paramRecPmtInfo.FIID;
      localPmtInfo.Memo = paramRecPmtInfo.Memo;
      localPmtInfo.PayAcct = paramRecPmtInfo.PayAcct;
      localPmtInfo.PayeeID = paramRecPmtInfo.PayeeID;
      localPmtInfo.payeeInfo = paramRecPmtInfo.payeeInfo;
      localPmtInfo.PayeeListID = paramRecPmtInfo.PayeeListID;
      localPmtInfo.paymentName = paramRecPmtInfo.paymentName;
      localPmtInfo.PaymentType = PaymentType.PMTTYPE_RECURRING.getName();
      localPmtInfo.StartDate = paramRecPmtInfo.StartDate;
      localPmtInfo.submittedBy = paramRecPmtInfo.submittedBy;
      localPmtInfo.Status = paramRecPmtInfo.Status;
      localPmtInfo.setRecSrvrTID(paramRecPmtInfo.getRecSrvrTID());
      localPmtInfo.setSrvrTID(null);
      localPmtInfo.setLogId(BPWTrackingIDGenerator.getNextId());
      return localPmtInfo;
    }
    catch (Exception localException)
    {
      throw new BPWException("Unable to convert rec payment to single payment: " + localException.getMessage());
    }
  }
  
  private PmtInfo jdMethod_char(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    FFSDebug.log("^^^ PaymentProcessor.addPayment: started ^^^", 6);
    if (!jdMethod_null(paramFFSConnectionHolder, paramPmtInfo))
    {
      FFSDebug.log("Payment validation failed, status = " + paramPmtInfo.getStatusMsg(), 6);
      return paramPmtInfo;
    }
    paramPmtInfo = a(paramFFSConnectionHolder, paramPmtInfo);
    if (paramPmtInfo.getStatusCode() != 0) {
      return paramPmtInfo;
    }
    a(paramPmtInfo, paramFFSConnectionHolder);
    paramPmtInfo.setStartDate(a(paramPmtInfo.getStartDate()));
    paramPmtInfo.setSrvrTID(DBUtil.getNextIndexString("SrvrTID"));
    String str1 = jdMethod_new(paramFFSConnectionHolder, paramPmtInfo);
    if (str1.equalsIgnoreCase("APPROVAL_NOT_ALLOWED")) {
      return paramPmtInfo;
    }
    FFSDebug.log("PaymentProcessor.addPayment: after limit check, status = " + str1, 6);
    FFSDebug.log("Payment start date = " + paramPmtInfo.getStartDate(), 6);
    if ((paramPmtInfo.getImmediateFundAllocation().booleanValue()) && (str1.equals("WILLPROCESSON"))) {
      paramPmtInfo.setStatus("FUNDSALLOCACTIVE");
    } else {
      paramPmtInfo.setStatus(str1);
    }
    PmtInstruction localPmtInstruction = new PmtInstruction(paramPmtInfo);
    a(paramFFSConnectionHolder, localPmtInstruction);
    FFSDebug.log("PaymentProcessor.addPayment: created PmtInstruction", 6);
    Object localObject;
    if ((paramPmtInfo.getImmediateFundAllocation().booleanValue()) && (str1.equals("WILLPROCESSON")))
    {
      try
      {
        paramFFSConnectionHolder.conn.commit();
      }
      catch (Throwable localThrowable)
      {
        paramFFSConnectionHolder.conn.rollback();
        localObject = FFSDebug.stackTrace(localThrowable);
        FFSDebug.log("*** PaymentProcessor.addPayment: (imm) failed:" + (String)localObject);
        paramPmtInfo.setStatusCode(2000);
        paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2000, null, "API_MESSAGE"));
        return paramPmtInfo;
      }
      try
      {
        a(paramPmtInfo, paramPmtInfo.getPayeeInfo(), paramFFSConnectionHolder, new HashMap());
      }
      catch (Exception localException)
      {
        throw new BPWException("Unable to perform immediate funds allocation: " + localException.getMessage());
      }
    }
    else if ("WILLPROCESSON".equalsIgnoreCase(str1))
    {
      jdMethod_try(paramFFSConnectionHolder, paramPmtInfo);
    }
    if (this.bI >= 3)
    {
      FFSDebug.log("PaymentProcessor.addPayment: adding payment to audit log", 6);
      int i = Integer.parseInt(localPmtInstruction.getCustomerID());
      localObject = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
      String str2 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
      String str3 = BPWLocaleUtil.getMessage(803, null, "BILLPAY_AUDITLOG_MESSAGE");
      String[] arrayOfString = { str3 };
      LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
      try
      {
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramPmtInfo.getSubmittedBy(), paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, paramPmtInfo.getLogId(), 4400, i, (BigDecimal)localObject, localPmtInstruction.getPmtInfo().CurDef, paramPmtInfo.getSrvrTID(), localPmtInstruction.getStatus(), localPmtInstruction.getPayAcct(), null, str2, null, 0);
        FFSDebug.log("PaymentProcessor.addPayment: payment logged", 6);
      }
      catch (FFSException localFFSException)
      {
        FFSDebug.log(localFFSException, "Unable to log Payment Addition to Audit log");
        throw new BPWException(localFFSException.getMessage());
      }
    }
    FFSDebug.log("vvv PaymentProcessor.addPayment: ends vvv", 6);
    return paramPmtInfo;
  }
  
  private int a(int paramInt)
  {
    if (paramInt == 0) {
      return paramInt;
    }
    String str = String.valueOf(paramInt).substring(0, 8) + "00";
    FFSDebug.log("Formatted start date = " + str, 6);
    return Integer.parseInt(str);
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    paramPmtInfo.setAction("add");
    if ((paramPmtInfo.getLogId() == null) || ((paramPmtInfo.getRecSrvrTID() != null) && (paramPmtInfo.getRecSrvrTID().length() > 0))) {
      paramPmtInfo.setLogId(BPWTrackingIDGenerator.getNextId());
    }
    paramPmtInfo.setOriginatedDate(DBUtil.getCurrentLogDate());
    FFSDebug.log("Checking duplicate payment for log ID " + paramPmtInfo.getLogId(), 6);
    paramPmtInfo = jdMethod_for(paramFFSConnectionHolder, paramPmtInfo);
    if (paramPmtInfo.getStatusCode() != 0) {
      return paramPmtInfo;
    }
    FFSDebug.log("PaymentProcessor.addPayment: using logID " + paramPmtInfo.getLogId(), 6);
    PayeeInfo localPayeeInfo = jdMethod_do(paramFFSConnectionHolder, paramPmtInfo);
    if (localPayeeInfo == null)
    {
      paramPmtInfo.setStatusCode(10501);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(10501, null, "API_MESSAGE"));
      return paramPmtInfo;
    }
    if (localPayeeInfo.getStatusCode() != 0)
    {
      paramPmtInfo.setStatusCode(localPayeeInfo.getStatusCode());
      paramPmtInfo.setStatusMsg(localPayeeInfo.getStatusMsg());
      return paramPmtInfo;
    }
    FFSDebug.log("PaymentProcessor.addPayment: payee processed", 6);
    FFSDebug.log("PaymentProcessor.addPayment: payeeID = " + paramPmtInfo.PayeeID, 6);
    FFSDebug.log("PaymentProcessor.addPayment: payeeListID = " + paramPmtInfo.getPayeeListID(), 6);
    return paramPmtInfo;
  }
  
  private PmtInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    String str1 = paramPmtInfo.getTrnId();
    if ((str1 != null) && (!str1.equals("0"))) {
      try
      {
        if (Trans.checkDuplicateTIDAndSaveTID(str1))
        {
          if (this.bI >= 3)
          {
            int i = Integer.parseInt(paramPmtInfo.getCustomerID());
            BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
            String str2 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
            String str3 = BPWLocaleUtil.getMessage(804, null, "BILLPAY_AUDITLOG_MESSAGE");
            String[] arrayOfString = { str3 };
            LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
            logTransAuditLogError(paramPmtInfo.getSubmittedBy(), paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, str1, 4401, i, localBigDecimal, paramPmtInfo.getCurDef(), paramPmtInfo.getSrvrTID(), "DUPLICATE", paramPmtInfo.getPayAcct(), null, str2, null);
          }
          paramPmtInfo.setStatusCode(2019);
          paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2019, null, "API_MESSAGE"));
          FFSDebug.log("Duplicate payment found, returning status " + paramPmtInfo.getStatusCode() + ":" + paramPmtInfo.getStatusMsg(), 6);
          return paramPmtInfo;
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log("Error checking duplicate id: " + localException.getMessage(), 6);
        throw new BPWException("Unable to determine if duplicate id: " + localException.getMessage());
      }
    }
    FFSDebug.log("Tran ID is unique", 6);
    paramPmtInfo.setStatusCode(0);
    return paramPmtInfo;
  }
  
  private void jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    try
    {
      ScheduleInfo localScheduleInfo = a(paramPmtInfo);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FUNDTRN", paramPmtInfo.getSrvrTID(), localScheduleInfo);
      FFSDebug.log("PaymentProcessor.addPayment: created FUNDTRN schedule", 6);
      localScheduleInfo.Frequency = 11;
      localScheduleInfo.InstanceCount = this.bN.getFundsAllocRetries();
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      ScheduleInfo.moveNextInstanceDate(localScheduleInfo);
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECFUNDTRN", paramPmtInfo.getSrvrTID(), localScheduleInfo);
      FFSDebug.log("PaymentProcessor.addPayment: created RECFUNDTRN schedule", 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Unable to create ScheduleInfo for payment");
      throw new BPWException(localException.getMessage());
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    try
    {
      ScheduleInfo localScheduleInfo = a(paramRecPmtInfo);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECPMTTRN", paramRecPmtInfo.getRecSrvrTID(), localScheduleInfo);
      FFSDebug.log("PaymentProcessor.addPayment: created RECPMTTRN schedule", 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Unable to create ScheduleInfo for payment");
      throw new BPWException(localException.getMessage());
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, PmtInstruction paramPmtInstruction)
    throws BPWException
  {
    paramPmtInstruction.storeToDB(paramFFSConnectionHolder);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = generateOFXAddResponse(paramPmtInstruction.getPmtInfo());
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    localAuditAgent.savePmtRsV1(localTypePmtTrnRsV1, paramPmtInstruction.getCustomerID(), paramPmtInstruction.getStatus(), paramFFSConnectionHolder);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, RecPmtInstruction paramRecPmtInstruction)
    throws BPWException
  {
    paramRecPmtInstruction.storeToDB(paramFFSConnectionHolder);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = generateOFXAddRecResponse(paramRecPmtInstruction.getRecPmtInfo());
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    localAuditAgent.saveRecPmtRsV1(localTypeRecPmtTrnRsV1, paramRecPmtInstruction.getCustomerID(), paramFFSConnectionHolder);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, boolean paramBoolean)
    throws BPWException
  {
    try
    {
      Object localObject1;
      Object localObject2;
      if (paramBoolean)
      {
        localObject1 = (RecPmtInfo)paramPmtInfo;
        ((RecPmtInfo)localObject1).DateCreate = ((RecPmtInfo)localObject1).getOriginatedDate();
        ((RecPmtInfo)localObject1).setStartDate(a(((RecPmtInfo)localObject1).getStartDate()));
        ((RecPmtInfo)localObject1).setEndDate(CommonProcessor.getEndDate(((RecPmtInfo)localObject1).getStartDate(), ((RecPmtInfo)localObject1).getRecFrequencyValue(), ((RecPmtInfo)localObject1).getInstanceCount()));
        RecPmtInstruction.updateRecPayment((RecPmtInfo)localObject1, paramFFSConnectionHolder);
        if (!((RecPmtInfo)localObject1).getPaymentTypeEnum().isTemplate())
        {
          localObject2 = generateOFXModRecResponse((RecPmtInfo)localObject1);
          FFSDebug.log("Generated ofx mod response", 6);
          AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
          if (localAuditAgent == null) {
            throw new BPWException("AuditAgent is null!!");
          }
          localAuditAgent.modRecPmtRsV1((TypeRecPmtTrnRsV1)localObject2, (RecPmtInfo)localObject1, paramFFSConnectionHolder);
          FFSDebug.log("Saved payment ofx response", 6);
        }
      }
      else
      {
        paramPmtInfo.setStartDate(a(paramPmtInfo.getStartDate()));
        PmtInstruction.updatePayment(paramPmtInfo, paramFFSConnectionHolder);
        FFSDebug.log("Saved pmt to db", 6);
        if (!paramPmtInfo.getPaymentTypeEnum().isTemplate())
        {
          localObject1 = generateOFXModResponse(paramPmtInfo);
          FFSDebug.log("Generated ofx mod response", 6);
          localObject2 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
          if (localObject2 == null) {
            throw new BPWException("AuditAgent is null!!");
          }
          ((AuditAgent)localObject2).modPmtRsV1((TypePmtTrnRsV1)localObject1, paramPmtInfo, paramFFSConnectionHolder);
        }
        FFSDebug.log("Saved payment ofx response", 6);
      }
    }
    catch (BPWException localBPWException)
    {
      throw localBPWException;
    }
    catch (Exception localException)
    {
      FFSDebug.log("Unable to store payment to db: " + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(localException.getMessage());
    }
  }
  
  private String jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    try
    {
      String str1 = RecPmtProcessor2.doLimitChecking(paramFFSConnectionHolder, paramPmtInfo, new HashMap(), false);
      if ((!"WILLPROCESSON".equals(str1)) && (!"APPROVAL_PENDING".equals(str1)) && (this.bI >= 3))
      {
        int i = Integer.parseInt(paramPmtInfo.getCustomerID());
        BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
        String str2 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(400, null, "BILLPAY_AUDITLOG_MESSAGE");
        logTransAuditLogError(paramPmtInfo.getSubmittedBy(), paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, paramPmtInfo.getLogID(), 4403, i, localBigDecimal, paramPmtInfo.CurDef, paramPmtInfo.getSrvrTID(), str1, paramPmtInfo.getPayAcct(), null, str2, null);
      }
      paramPmtInfo.setStatus(str1);
      return str1;
    }
    catch (FFSException localFFSException)
    {
      throw new BPWException(localFFSException.getMessage());
    }
  }
  
  private PayeeInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.processPayee start", 6);
    int i = paramPmtInfo.getPayeeListID() > 0 ? 1 : 0;
    int j = (paramPmtInfo.getPayeeID() != null) && (paramPmtInfo.getPayeeID().length() > 0) ? 1 : 0;
    int k = paramPmtInfo.getPayeeInfo() != null ? 1 : 0;
    Payee localPayee = new Payee();
    if (i != 0)
    {
      FFSDebug.log("PaymentProcessor.processPayee: payeeListID = " + paramPmtInfo.getPayeeListID(), 6);
      localPayeeInfo = null;
      if (j != 0)
      {
        FFSDebug.log("PaymentProcessor.processPayee: payeeID exists, looking up payee", 6);
        localPayeeInfo = jdMethod_case(paramFFSConnectionHolder, paramPmtInfo);
      }
      else if (k != 0)
      {
        FFSDebug.log("PaymentProcessor.processPayee: payee exists, modifying payee", 6);
        localPayeeInfo = jdMethod_else(paramFFSConnectionHolder, paramPmtInfo);
      }
      localObject = jdMethod_if(paramPmtInfo.getCustomerID(), paramPmtInfo.getPayeeListID(), paramFFSConnectionHolder);
      paramPmtInfo.setRouteId(((CustPayeeRoute)localObject).RouteID);
      FFSDebug.log("RouteID set to " + paramPmtInfo.getRouteId(), 6);
      return localPayeeInfo;
    }
    FFSDebug.log("PaymentProcessor.processPayee: payeeListID not set", 6);
    PayeeInfo localPayeeInfo = null;
    Object localObject = null;
    if (j != 0)
    {
      localObject = paramPmtInfo.getPayeeID();
      if (!this.bM)
      {
        FFSDebug.log("PaymentProcessor.processPayee: Looking up payee by id " + (String)localObject, 6);
        localPayeeInfo = Payee.findPayeeByID((String)localObject, paramFFSConnectionHolder);
      }
      else
      {
        FFSDebug.log("PaymentProcessor.processPayee: Looking up payee by ext ID " + (String)localObject, 6);
        localPayeeInfo = Payee.findPayeeByExtendedID((String)localObject, paramFFSConnectionHolder);
      }
      if (localPayeeInfo == null)
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.setStatusCode(10510);
        localPayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(10510, null, "API_MESSAGE"));
        FFSDebug.log("PaymentProcessor.processPayee: Payee info not found for payee ID " + (String)localObject, 6);
        return localPayeeInfo;
      }
      if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED")))
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.setStatusCode(10501);
        localPayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(10501, null, "API_MESSAGE"));
        FFSDebug.log("PaymentProcessor.processPayee: Payee " + (String)localObject + " is not Active", 6);
        return localPayeeInfo;
      }
      localPayee.setPayeeInfo(localPayeeInfo);
    }
    else if (k != 0)
    {
      localPayeeInfo = paramPmtInfo.getPayeeInfo();
      localPayee.setPayeeInfo(localPayeeInfo);
      if (!this.bL)
      {
        FFSDebug.log("PaymentProcessor.processPayee: Adding new payee", 6);
        localObject = localPayee.addPayee(paramFFSConnectionHolder, paramPmtInfo.getLogID());
      }
      else
      {
        FFSDebug.log("PaymentProcessor.processPayee: Matching payee", 6);
        localObject = localPayee.matchPayee(Payee.findGlobalPayeeByName(localPayeeInfo.PayeeName, paramFFSConnectionHolder));
        if (localObject == null)
        {
          FFSDebug.log("PaymentProcessor.processPayee: No match found", 6);
          localPayee.addPayeeNoMatch(localPayeeInfo, paramFFSConnectionHolder, paramPmtInfo.getLogID());
          localObject = localPayee.getPayeeID();
        }
      }
      if (localPayee.getRouteID() == 0) {
        localPayee.setRouteID(jdMethod_case());
      }
      FFSDebug.log("PaymentProcessor.processPayee: Added payee with id " + (String)localObject, 6);
      localPayeeInfo = Payee.findPayeeByID((String)localObject, paramFFSConnectionHolder);
      if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED")))
      {
        localPayeeInfo.setStatusCode(10501);
        localPayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(10501, null, "API_MESSAGE"));
        return localPayeeInfo;
      }
      paramPmtInfo.PayeeID = ((String)localObject);
      paramPmtInfo.setPayeeInfo(localPayeeInfo);
    }
    else
    {
      throw new BPWException("Unsupported request type", 2000);
    }
    String str = paramPmtInfo.PayAcct;
    a(paramFFSConnectionHolder, paramPmtInfo, str, localPayee, (String)localObject);
    FFSDebug.log("PaymentProcessor.processPayee ends", 6);
    paramPmtInfo.setRouteId(localPayee.getRouteID());
    FFSDebug.log("RouteID set to " + paramPmtInfo.getRouteId(), 6);
    localPayeeInfo.setStatusCode(0);
    localPayeeInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "API_MESSAGE"));
    return localPayeeInfo;
  }
  
  private PayeeInfo jdMethod_case(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    CustPayee localCustPayee = new CustPayee();
    localCustPayee.findCustPayeeByPayeeListID(paramPmtInfo.getCustomerID(), paramPmtInfo.getPayeeListID(), paramFFSConnectionHolder);
    String str = jdMethod_if(paramFFSConnectionHolder, paramPmtInfo, localCustPayee);
    PayeeInfo localPayeeInfo = Payee.findPayeeByID(str, paramFFSConnectionHolder);
    if (localPayeeInfo == null) {
      throw new BPWException(" ", 10519);
    }
    if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
      throw new BPWException("Payee is not Active", 2000);
    }
    int i = Payee.findRoute(str, paramFFSConnectionHolder);
    if (i == -1) {
      throw new BPWException(" ", 10519);
    }
    return localPayeeInfo;
  }
  
  private String jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, CustPayee paramCustPayee)
    throws BPWException
  {
    String str1 = paramCustPayee.getStatus();
    if ((str1 == null) || (str1.equals("CLOSED"))) {
      throw new BPWException(" ", 10519);
    }
    CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(paramPmtInfo.getCustomerID(), paramPmtInfo.getPayeeListID(), paramFFSConnectionHolder);
    if ((localCustPayeeRoute == null) || (localCustPayeeRoute.Status.equals("CLOSED")) || (localCustPayeeRoute.Status.equals("CANC")) || (localCustPayeeRoute.Status.equals("PENDING")) || (localCustPayeeRoute.Status.equals("CANC_INPROCESS")) || (localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR"))) {
      throw new BPWException(" ", 10519);
    }
    String str2 = paramCustPayee.getPayeeID();
    if (str2 == null) {
      throw new BPWException(" ", 10519);
    }
    if ((paramCustPayee.getPayAcct() != null) && (!paramCustPayee.getPayAcct().equals(paramPmtInfo.PayAcct))) {
      throw new BPWException(" ", 10503);
    }
    return str2;
  }
  
  private PayeeInfo jdMethod_else(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo(paramPmtInfo.getCustomerID(), "", paramPmtInfo.getPayeeListID(), paramPmtInfo.getPayAcct(), null, "", 0, "", "", -1, -1);
    String str1 = localCustomerPayeeInfo.CustomerID;
    int i = localCustomerPayeeInfo.PayeeListID;
    CustPayee localCustPayee = new CustPayee();
    localCustPayee.findCustPayeeByPayeeListID(str1, i, paramFFSConnectionHolder);
    String str2 = null;
    str2 = localCustPayee.getPayeeID();
    if (str2 == null) {
      throw new BPWException(" ", 10519);
    }
    FFSDebug.log("===In the mod payee mothed : payeeID = " + str2, 6);
    PayeeInfo localPayeeInfo = Payee.findPayeeByID(str2, paramFFSConnectionHolder);
    if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
      throw new BPWException("Payee is not Active", 2000);
    }
    jdMethod_if(paramFFSConnectionHolder, paramPmtInfo, localCustPayee);
    return a(paramFFSConnectionHolder, paramPmtInfo, localCustPayee);
  }
  
  private PayeeInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, CustPayee paramCustPayee)
    throws BPWException
  {
    int i = 0;
    String str1 = paramCustPayee.getPayAcct();
    String str2 = paramCustPayee.getPayeeID();
    PayeeInfo localPayeeInfo1 = paramPmtInfo.getPayeeInfo();
    String str3 = paramPmtInfo.CustomerID;
    int j = paramPmtInfo.PayeeListID;
    CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(str3, j, paramFFSConnectionHolder);
    PayeeInfo localPayeeInfo2 = Payee.findPayeeByID(str2, paramFFSConnectionHolder);
    String str4 = paramPmtInfo.getPayAcct();
    if (str1 == null)
    {
      if ((str4 == null) || (str4.equals(""))) {
        i = 0;
      } else {
        i = 1;
      }
    }
    else if (str1.equals(str4)) {
      i = 0;
    } else {
      i = 1;
    }
    int k = 0;
    int m = jdMethod_case();
    Payee localPayee = new Payee(localPayeeInfo1);
    String str5 = localPayee.matchPayee(Payee.findGlobalPayeeByName(localPayeeInfo1.PayeeName, paramFFSConnectionHolder));
    if (str5 == null)
    {
      localPayee.setRouteID(m);
      localPayee.addPayeeNoMatch(localPayeeInfo1, paramFFSConnectionHolder, paramPmtInfo.getLogID());
      str5 = localPayee.getPayeeID();
    }
    PayeeProcessor2 localPayeeProcessor2 = new PayeeProcessor2();
    if (paramCustPayee.getPayeeID().equals(str5))
    {
      try
      {
        if ((i != 0) || (k != 0))
        {
          paramCustPayee.modCustPayee(str4, null, str1, str2, "MODACCT", paramFFSConnectionHolder);
          if ((localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR"))) {
            throw new OFXException(" ", 10515);
          }
          if (!localCustPayeeRoute.Status.equals("NEW")) {
            CustPayeeRoute.updateCustPayeeRouteStatus(str3, j, m, "MODACCT", paramFFSConnectionHolder);
          }
          localPayeeProcessor2.updateWithPayAcct(str3, j, paramCustPayee.getPayAcct(), null, paramFFSConnectionHolder);
        }
      }
      catch (Exception localException)
      {
        throw new BPWException(localException.getMessage());
      }
    }
    else
    {
      if ((localPayeeInfo1.PayeeType == 0) || (localPayeeInfo2.PayeeType == 1) || (localPayeeInfo2.PayeeType == 2)) {
        throw new BPWException(" ", 10515);
      }
      if ((localCustPayeeRoute.Status.equals("NEW")) || (localCustPayeeRoute.Status.equals("FAILEDON")) || (localCustPayeeRoute.Status.equals("ERROR")))
      {
        CustPayeeRoute.updateCustPayeeRouteStatus(str3, j, m, "CLOSED", paramFFSConnectionHolder);
        CustPayee.updateStatus(str3, j, "CLOSED", paramFFSConnectionHolder);
      }
      else
      {
        CustPayeeRoute.updateCustPayeeRouteStatus(str3, j, m, "CANC", paramFFSConnectionHolder);
      }
      str2 = a(paramFFSConnectionHolder, paramPmtInfo, str4, localPayee, str5);
    }
    PayeeInfo localPayeeInfo3 = Payee.findPayeeByID(str2, paramFFSConnectionHolder);
    return localPayeeInfo3;
  }
  
  private String a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, String paramString1, Payee paramPayee, String paramString2)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str1 = null;
    PayeeProcessor2 localPayeeProcessor2 = new PayeeProcessor2();
    String str2 = paramPmtInfo.CustomerID;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      CustPayee localCustPayee = new CustPayee();
      paramPmtInfo.PayeeListID = localCustPayee.addCustPayee(str2, paramString2, paramString1, null, paramPayee.getRouteID(), paramFFSConnectionHolder, localFFSConnectionHolder);
      localPayeeProcessor2.updateWithPayAcct(str2, paramPmtInfo.PayeeListID, paramString1, null, paramFFSConnectionHolder);
      str1 = paramString2;
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.getMessage());
    }
    finally
    {
      localFFSConnectionHolder.conn.rollback();
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.addCustPayee failed:" + localException2.toString());
      }
    }
    return str1;
  }
  
  private int jdMethod_case()
    throws BPWException
  {
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new BPWException("No Fulfillment system.");
    }
    return localFulfillAgent.getDefaultFulfillment();
  }
  
  private PmtInfo jdMethod_long(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    String str1 = "PaymentProcessor.addTemplate(conn, ..):";
    FFSDebug.log("^^^ " + str1 + "Adding template ^^^", 6);
    if (!jdMethod_int(paramFFSConnectionHolder, paramPmtInfo))
    {
      FFSDebug.log("vvv template validation failed vvv", 6);
      return paramPmtInfo;
    }
    FFSDebug.log(str1 + "validation succeeded", 6);
    if ((paramPmtInfo.getBatchID() == null) && (!a(paramFFSConnectionHolder, paramPmtInfo, false)))
    {
      paramPmtInfo.setStatusCode(26030);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(26030, null, "PAYMENT_MESSAGE"));
      FFSDebug.log("vvv duplicate template name", 6);
      return paramPmtInfo;
    }
    FFSDebug.log("Template name not duplicate", 6);
    PaymentType localPaymentType = paramPmtInfo.getPaymentTypeEnum();
    try
    {
      if (localPaymentType.isRecurring())
      {
        RecPmtInfo localRecPmtInfo = (RecPmtInfo)paramPmtInfo;
        RecPmtInstruction.insertRecPayment(localRecPmtInfo, paramFFSConnectionHolder);
      }
      else
      {
        PmtInstruction.insertPayment(paramPmtInfo, paramFFSConnectionHolder);
      }
      if (this.bI >= 3)
      {
        int i = Integer.parseInt(paramPmtInfo.getCustomerID());
        localObject = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
        str2 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
        String str3 = BPWLocaleUtil.getMessage(817, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str3 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        String str4 = null;
        String str5 = null;
        if (localPaymentType.isRecurring())
        {
          str4 = ((RecPmtInfo)paramPmtInfo).getSubmittedBy();
          str5 = paramPmtInfo.getRecSrvrTID();
        }
        else
        {
          str4 = paramPmtInfo.getSubmittedBy();
          str5 = paramPmtInfo.getSrvrTID();
        }
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), str4, paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, paramPmtInfo.getLogID(), 2012, i, (BigDecimal)localObject, paramPmtInfo.getCurDef(), str5, paramPmtInfo.getStatus(), paramPmtInfo.getPayAcct(), null, str2, null, 0);
      }
      paramPmtInfo.setStatusCode(0);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
    }
    catch (Exception localException)
    {
      Object localObject = "*** PaymentProcessor.addTemplate failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject + str2, 0);
      throw new BPWException((String)localObject);
    }
    FFSDebug.log("vvv " + str1, " end vvv", 6);
    return paramPmtInfo;
  }
  
  public PmtInfo modifyBillPayment(PmtInfo paramPmtInfo)
    throws BPWException
  {
    boolean bool = false;
    FFSDebug.log("PaymentProcessor.modifyBillPayment:", " Start ", 6);
    if (paramPmtInfo == null)
    {
      paramPmtInfo = new PmtInfo();
      paramPmtInfo.setStatusCode(16000);
      String str = "PmtInfo object  is null";
      paramPmtInfo.setStatusMsg(str);
      FFSDebug.log("PaymentProcessor.modifyBillPayment:", str, 0);
      return paramPmtInfo;
    }
    if ((paramPmtInfo instanceof RecPmtInfo)) {
      bool = true;
    }
    paramPmtInfo = jdMethod_do(paramPmtInfo, bool);
    FFSDebug.log("PaymentProcessor.modifyBillPayment:", " end ", 6);
    return paramPmtInfo;
  }
  
  private PmtInfo jdMethod_do(PmtInfo paramPmtInfo, boolean paramBoolean)
    throws BPWException
  {
    String str = "PaymentProcessor.modifyBillPayment(.., boolean):";
    FFSDebug.log(str, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Object localObject1;
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str + " Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new BPWException((String)localObject1);
      }
      paramPmtInfo = a(localFFSConnectionHolder, paramPmtInfo, paramBoolean, false);
      if (paramPmtInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramPmtInfo;
        return localObject1;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** PaymentProcessor.modifyBillPayment failed:" + FFSDebug.stackTrace(localException1), 0);
      paramPmtInfo.setStatusCode(2000);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2000, null, "API_MESSAGE"));
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.modifyBillPayment failed:" + localException2.toString());
      }
    }
    FFSDebug.log(str, " end ", 6);
    return paramPmtInfo;
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws BPWException
  {
    String str1 = "PaymentProcessor.modifyBillPayment(conn, ..):";
    FFSDebug.log("^^^^ " + str1, " Start ^^^^", 6);
    if (paramPmtInfo.getPaymentType() == null)
    {
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "PmtInfo", "PaymentType" }, "PAYMENT_MESSAGE"));
      return paramPmtInfo;
    }
    PaymentType localPaymentType = PaymentType.getEnum(paramPmtInfo.getPaymentType());
    if (localPaymentType == null)
    {
      paramPmtInfo.setStatusCode(26034);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(26034, new String[] { paramPmtInfo.getPaymentType() }, "PAYMENT_MESSAGE"));
      return paramPmtInfo;
    }
    try
    {
      String str2 = paramBoolean1 ? paramPmtInfo.getRecSrvrTID() : paramPmtInfo.getSrvrTID();
      localObject = getBillPaymentById(str2, paramPmtInfo.getPaymentType());
      if (localObject == null)
      {
        paramPmtInfo.setStatusCode(16020);
        paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, null, "PAYMENT_MESSAGE"));
        return paramPmtInfo;
      }
      if (((PmtInfo)localObject).getStatusCode() != 0)
      {
        paramPmtInfo.setStatusCode(((PmtInfo)localObject).getStatusCode());
        paramPmtInfo.setStatusMsg(((PmtInfo)localObject).getStatusMsg());
        return paramPmtInfo;
      }
      if (((PmtInfo)localObject).Status.equals("CANCELEDON"))
      {
        paramPmtInfo.setStatusCode(2017);
        paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2017, null, "API_MESSAGE"));
        return paramPmtInfo;
      }
      FFSDebug.log("old payment type = " + PaymentType.getEnum(((PmtInfo)localObject).getPaymentType()), 6);
      if ((((PmtInfo)localObject).getPaymentType() != null) && (PaymentType.getEnum(((PmtInfo)localObject).getPaymentType()).isTemplate()) && (!((PmtInfo)localObject).getStatus().equalsIgnoreCase("ACTIVE")))
      {
        paramPmtInfo.setStatusCode(26041);
        paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(26041, null, "PAYMENT_MESSAGE"));
        return paramPmtInfo;
      }
      if (!paramBoolean2)
      {
        FFSDebug.log("Not from batch", 6);
        if ((((PmtInfo)localObject).getBatchID() != null) && (((PmtInfo)localObject).getBatchID().trim().length() != 0))
        {
          String str3 = BPWLocaleUtil.getMessage(26039, null, "PAYMENT_MESSAGE");
          paramPmtInfo.setStatusCode(26039);
          paramPmtInfo.setStatusMsg(str3);
          return paramPmtInfo;
        }
      }
      boolean bool;
      if (localPaymentType.isTemplate())
      {
        bool = jdMethod_int(paramFFSConnectionHolder, paramPmtInfo);
        if ((((PmtInfo)localObject).getPaymentName() != null) && (paramPmtInfo.getPaymentName() != null) && (!((PmtInfo)localObject).getPaymentName().equals(paramPmtInfo.getPaymentName())) && (!a(paramFFSConnectionHolder, paramPmtInfo, true)))
        {
          paramPmtInfo.setStatusCode(26030);
          paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(26030, null, "PAYMENT_MESSAGE"));
          return paramPmtInfo;
        }
      }
      else
      {
        bool = paramBoolean1 ? jdMethod_do(paramFFSConnectionHolder, (RecPmtInfo)paramPmtInfo) : jdMethod_null(paramFFSConnectionHolder, paramPmtInfo);
      }
      FFSDebug.log("validPayment = " + bool, 6);
      if (!bool)
      {
        FFSDebug.log("Payment invalid, status msg = " + paramPmtInfo.getStatusMsg(), 6);
        return paramPmtInfo;
      }
      if (!localPaymentType.isTemplate()) {
        paramPmtInfo = a(paramFFSConnectionHolder, paramPmtInfo, (PmtInfo)localObject, paramBoolean1);
      }
      if (paramPmtInfo.getStatusCode() != 0) {
        return paramPmtInfo;
      }
      FFSDebug.log("Saving to db, srvrTID = " + paramPmtInfo.getSrvrTID(), 6);
      jdMethod_if(paramFFSConnectionHolder, paramPmtInfo, paramBoolean1);
      if (this.bI >= 3)
      {
        FFSDebug.log("Saving to audit log", 6);
        int i = Integer.parseInt(paramPmtInfo.getCustomerID());
        BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
        String str5 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
        String str6 = BPWLocaleUtil.getMessage(818, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str6 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        String str7 = paramPmtInfo.getSubmittedBy();
        String str8 = paramBoolean1 ? paramPmtInfo.getRecSrvrTID() : paramPmtInfo.getSrvrTID();
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), str7, paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, paramPmtInfo.getLogID(), 2013, i, localBigDecimal, paramPmtInfo.getCurDef(), str8, paramPmtInfo.getStatus(), paramPmtInfo.getPayAcct(), null, str5, null, 0);
      }
      paramPmtInfo.setStatusCode(0);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
    }
    catch (Exception localException)
    {
      Object localObject = "*** PaymentProcessor.modifyBillPayment failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject + str4, 0);
      throw new BPWException((String)localObject);
    }
    FFSDebug.log("vvvv " + str1, " end vvvv", 6);
    return paramPmtInfo;
  }
  
  private String jdMethod_if(PmtInfo paramPmtInfo, boolean paramBoolean)
  {
    return paramBoolean ? paramPmtInfo.getRecSrvrTID() : paramPmtInfo.getSrvrTID();
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo1, PmtInfo paramPmtInfo2, boolean paramBoolean)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.modifyPayment() start", 6);
    paramPmtInfo1.setAction("mod");
    paramPmtInfo1 = jdMethod_for(paramFFSConnectionHolder, paramPmtInfo1);
    if (paramPmtInfo1.getStatusCode() != 0) {
      return paramPmtInfo1;
    }
    PayeeInfo localPayeeInfo = jdMethod_do(paramFFSConnectionHolder, paramPmtInfo1);
    if (localPayeeInfo == null)
    {
      FFSDebug.log("PayeeInfo is null", 6);
      paramPmtInfo1.setStatusCode(10501);
      paramPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(10501, null, "API_MESSAGE"));
      return paramPmtInfo1;
    }
    FFSDebug.log("Processed payee, status = " + localPayeeInfo.getStatusMsg(), 6);
    if (localPayeeInfo.getStatusCode() != 0)
    {
      paramPmtInfo1.setStatusCode(localPayeeInfo.getStatusCode());
      paramPmtInfo1.setStatusMsg(localPayeeInfo.getStatusMsg());
      return paramPmtInfo1;
    }
    if (paramBoolean)
    {
      FFSDebug.log("Modifying RECPMTTRN schedule", 6);
      try
      {
        String str1 = ((RecPmtInfo)paramPmtInfo1).getRecSrvrTID();
        int i = ScheduleInfo.checkScheduleStatus(str1, paramPmtInfo1.getFIID(), "RECPMTTRN", paramFFSConnectionHolder);
        FFSDebug.log("Mod payment, schedule status = " + i, 6);
        if (i == 0)
        {
          ScheduleInfo.modifySchedule(paramFFSConnectionHolder, "RECPMTTRN", str1, a(paramPmtInfo1));
          FFSDebug.log("Modified RECPMTTRN schedule", 6);
        }
        else
        {
          paramPmtInfo1.setStatusCode(2016);
          paramPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(2016, null, "API_MESSAGE"));
          FFSDebug.log("Unable to modify RECPMTTRN schedule, status = " + i, 6);
          return paramPmtInfo1;
        }
        String[] arrayOfString = RecSrvrTIDToSrvrTID.getSrvrTIDs(paramFFSConnectionHolder, paramPmtInfo1.getRecSrvrTID());
        if (arrayOfString != null)
        {
          FFSDebug.log("Found " + arrayOfString.length + " pending payments", 6);
          for (int j = 0; j < arrayOfString.length; j++)
          {
            PmtInfo localPmtInfo2 = getBillPaymentById(arrayOfString[j], PaymentType.PMTTYPE_RECURRING.getName());
            if ((localPmtInfo2 != null) && (localPmtInfo2.getStatusCode() == 0))
            {
              FFSDebug.log("Modifying pending payment " + localPmtInfo2.getSrvrTID(), 6);
              localPmtInfo2.setLogId(BPWTrackingIDGenerator.getNextId());
              String str3 = localPmtInfo2.getStatus();
              if ((str3 != null) && (("WILLPROCESSON".equalsIgnoreCase(str3) == true) || ("APPROVAL_PENDING".equalsIgnoreCase(str3) == true) || ("APPROVAL_REJECTED".equals(str3) == true) || ("APPROVAL_NOT_ALLOWED".equals(str3) == true)))
              {
                jdMethod_if(paramFFSConnectionHolder, localPmtInfo2, false, false);
                RecSrvrTIDToSrvrTID.delete(paramFFSConnectionHolder, paramPmtInfo1.getRecSrvrTID(), arrayOfString[j]);
              }
            }
          }
        }
        PmtInfo localPmtInfo1 = a((RecPmtInfo)paramPmtInfo1);
        jdMethod_char(paramFFSConnectionHolder, localPmtInfo1);
        try
        {
          RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, paramPmtInfo1.getRecSrvrTID(), localPmtInfo1.getSrvrTID());
        }
        catch (Exception localException2)
        {
          throw new BPWException("Unable to add RecSrvrTIDToSrvrTID: " + localException2.getMessage());
        }
      }
      catch (Exception localException1)
      {
        FFSDebug.log("Unable to modify recurring schedule: " + localException1.getMessage(), 6);
        throw new BPWException(localException1.getMessage());
      }
    }
    else
    {
      FFSDebug.log("Cancelling current schedule", 6);
      paramPmtInfo1 = a(paramFFSConnectionHolder, paramPmtInfo1, paramBoolean, paramPmtInfo2);
      FFSDebug.log("Schedule cancelled, status = " + paramPmtInfo1.getStatusMsg(), 6);
      if (paramPmtInfo1.getStatusCode() != 0) {
        return paramPmtInfo1;
      }
      String str2 = jdMethod_new(paramFFSConnectionHolder, paramPmtInfo1);
      if ("WILLPROCESSON".equals(str2)) {
        jdMethod_try(paramFFSConnectionHolder, paramPmtInfo1);
      }
    }
    paramPmtInfo1.setLastModified("yyyyMMddHHmmss");
    return paramPmtInfo1;
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo1, boolean paramBoolean, PmtInfo paramPmtInfo2)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.cancelSchedule start", 6);
    int i = 1;
    if (!"APPROVAL_PENDING".equals(paramPmtInfo1.Status))
    {
      if (!"APPROVAL_REJECTED".equals(paramPmtInfo1.Status))
      {
        boolean bool = jdMethod_if(paramFFSConnectionHolder, jdMethod_if(paramPmtInfo1, paramBoolean), paramPmtInfo1.getFIID());
        FFSDebug.log("schedules cancelled = " + bool, 6);
        if (!bool)
        {
          FFSDebug.log("Cannot cancel pmt in progress", 6);
          throw new BPWException("Cannot cancel pmt in progress");
        }
      }
      if ((!"WILLPROCESSON".equals(paramPmtInfo1.Status)) && (!"APPROVAL_PENDING".equals(paramPmtInfo1.Status))) {
        if (("APPROVAL_REJECTED".equals(paramPmtInfo1.Status)) || ("APPROVAL_NOT_ALLOWED".equals(paramPmtInfo1.Status)))
        {
          FFSDebug.log("Approval rejected or not allowed, no limit revert required", 6);
          i = 0;
        }
        else
        {
          FFSDebug.log("Payment already commited", 6);
          paramPmtInfo1.setStatusCode(2016);
          paramPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(2016, null, "API_MESSAGE"));
          return paramPmtInfo1;
        }
      }
    }
    if (i != 0) {
      jdMethod_if(paramFFSConnectionHolder, paramPmtInfo2);
    }
    paramPmtInfo1.setStatusCode(0);
    paramPmtInfo1.setStatusMsg("Success");
    return paramPmtInfo1;
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    a(paramFFSConnectionHolder, paramPmtInfo, false);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1 = "";
    FFSDebug.log("Performing limit reversion", 6);
    try
    {
      str1 = RecPmtProcessor2.deleteLimit(paramFFSConnectionHolder, paramPmtInfo, new HashMap(), "CANCELEDON", paramBoolean);
      FFSDebug.log("limit revert successful, limit check status = " + str1, 6);
    }
    catch (FFSException localFFSException) {}finally
    {
      if (!"LIMIT_REVERT_SUCCEEDED".equals(str1))
      {
        FFSDebug.log("PaymentProcessor.modifyPayment: Faled to revert limit", 0);
        if (this.bI >= 3)
        {
          int i = Integer.parseInt(paramPmtInfo.getCustomerID());
          BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
          String str2 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(402, null, "BILLPAY_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramPmtInfo.getSubmittedBy(), paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, paramPmtInfo.getLogId(), 4403, i, localBigDecimal, paramPmtInfo.CurDef, paramPmtInfo.getSrvrTID(), str1, paramPmtInfo.getPayAcct(), null, str2, null);
        }
        throw new BPWException(paramPmtInfo.getStatusMsg(), 2000);
      }
    }
  }
  
  private boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    try
    {
      return (jdMethod_do(paramString1, paramString2, "FUNDTRN", paramFFSConnectionHolder) != 0) && (jdMethod_do(paramString1, paramString2, "RECFUNDTRN", paramFFSConnectionHolder) != 0);
    }
    catch (Exception localException)
    {
      throw new BPWException("Unable to cancelSchedule: " + localException.getMessage());
    }
  }
  
  private int jdMethod_do(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    try
    {
      int i = ScheduleInfo.checkScheduleStatus(paramString1, paramString2, paramString3, paramFFSConnectionHolder);
      if (i == 0) {
        return ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, paramString3, paramString1);
      }
      return 0;
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
  }
  
  public PmtInfo deleteBillPayment(PmtInfo paramPmtInfo)
    throws BPWException
  {
    boolean bool = false;
    FFSDebug.log("PaymentProcessor.deleteBillPayment:", " Start ", 6);
    if (paramPmtInfo == null)
    {
      paramPmtInfo = new PmtInfo();
      paramPmtInfo.setStatusCode(16000);
      String str1 = "PmtInfo object  is null";
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("PaymentProcessor.deleteBillPayment:", str1, 0);
      return paramPmtInfo;
    }
    if ((paramPmtInfo instanceof RecPmtInfo)) {
      bool = true;
    }
    try
    {
      paramPmtInfo = a(paramPmtInfo, bool);
    }
    catch (Exception localException)
    {
      String str2 = "*** PaymentProcessor.deleteBillPayment failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2 + str3, 0);
      paramPmtInfo.setStatusCode(2000);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2000, null, "API_MESSAGE"));
      return paramPmtInfo;
    }
    FFSDebug.log("PaymentProcessor.deleteBillPayment:", " end ", 6);
    return paramPmtInfo;
  }
  
  private PmtInfo a(PmtInfo paramPmtInfo, boolean paramBoolean)
    throws BPWException
  {
    String str = "PaymentProcessor.deleteBillPayment(.., boolean):";
    FFSDebug.log(str, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Object localObject1;
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str + " Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new BPWException((String)localObject1);
      }
      paramPmtInfo = jdMethod_if(localFFSConnectionHolder, paramPmtInfo, paramBoolean, false);
      if (paramPmtInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramPmtInfo;
        return localObject1;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** PaymentProcessor.deleteBillPayment failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.deleteBillPayment failed:" + localException2.toString());
      }
    }
    FFSDebug.log(str, " end ", 6);
    return paramPmtInfo;
  }
  
  private PmtInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws BPWException
  {
    String str1 = "PaymentProcessor.deleteBillPayment(conn, ..):";
    FFSDebug.log(str1, " Start ", 6);
    if (paramPmtInfo.getPaymentType() == null)
    {
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(16010, new String[] { "PmtInfo", "PaymentType" }, "PAYMENT_MESSAGE"));
      return paramPmtInfo;
    }
    FFSDebug.log("payment type = " + paramPmtInfo.getPaymentType(), 6);
    PaymentType localPaymentType = PaymentType.getEnum(paramPmtInfo.getPaymentType());
    if (localPaymentType == null)
    {
      paramPmtInfo.setStatusCode(26034);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(26034, new String[] { paramPmtInfo.getPaymentType() }, "PAYMENT_MESSAGE"));
      return paramPmtInfo;
    }
    try
    {
      String str2 = paramBoolean1 ? paramPmtInfo.getRecSrvrTID() : paramPmtInfo.getSrvrTID();
      if (str2 == null)
      {
        localObject = paramBoolean1 ? "RecSrvrTID" : "SrvrTID";
        paramPmtInfo.setStatusCode(16000);
        str3 = BPWLocaleUtil.getMessage(16000, new String[] { localObject }, "PAYMENT_MESSAGE");
        paramPmtInfo.setStatusMsg(str3);
        FFSDebug.log("PaymentProcessor.deleteBillPayment(), " + str3, 0);
        return paramPmtInfo;
      }
      localObject = getBillPaymentById(str2, paramPmtInfo.getPaymentType());
      if (localObject == null)
      {
        paramPmtInfo.setStatusCode(16020);
        paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(16020, null, "PAYMENT_MESSAGE"));
        return paramPmtInfo;
      }
      if (((PmtInfo)localObject).getStatusCode() != 0)
      {
        paramPmtInfo.setStatusCode(((PmtInfo)localObject).getStatusCode());
        paramPmtInfo.setStatusMsg(((PmtInfo)localObject).getStatusMsg());
        return paramPmtInfo;
      }
      if ((!paramBoolean2) && (paramPmtInfo.getBatchID() != null) && (paramPmtInfo.getBatchID().trim().length() != 0))
      {
        str3 = BPWLocaleUtil.getMessage(26039, null, "PAYMENT_MESSAGE");
        paramPmtInfo.setStatusCode(26039);
        paramPmtInfo.setStatusMsg(str3);
        return paramPmtInfo;
      }
      paramPmtInfo = a(paramFFSConnectionHolder, paramPmtInfo, (PmtInfo)localObject, paramBoolean1, paramBoolean2);
      if (0 != paramPmtInfo.getStatusCode()) {
        return paramPmtInfo;
      }
      str3 = paramPmtInfo.getSubmittedBy();
      if (this.bI >= 3)
      {
        int i = Integer.parseInt(paramPmtInfo.getCustomerID());
        BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramPmtInfo.getAmt());
        String str4 = BPWUtil.getAccountIDWithAccountType(paramPmtInfo.getAcctDebitID(), paramPmtInfo.getAcctDebitType());
        String str5 = BPWLocaleUtil.getMessage(819, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str5 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), str3, paramPmtInfo.getAgentId(), paramPmtInfo.getAgentType(), localLocalizableString, paramPmtInfo.getLogID(), 2014, i, localBigDecimal, paramPmtInfo.getCurDef(), str2, paramPmtInfo.getStatus(), paramPmtInfo.getPayAcct(), null, str4, null, 0);
      }
      paramPmtInfo.setStatusCode(0);
      paramPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
    }
    catch (Exception localException)
    {
      Object localObject = "*** PaymentProcessor.deleteBillPayment failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject + str3, 0);
      throw new BPWException((String)localObject);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramPmtInfo;
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo1, PmtInfo paramPmtInfo2, boolean paramBoolean1, boolean paramBoolean2)
    throws BPWException
  {
    String str1 = paramPmtInfo1.getStatus();
    PaymentType localPaymentType = PaymentType.getEnum(paramPmtInfo1.getPaymentType());
    String str2 = paramBoolean1 ? paramPmtInfo1.getRecSrvrTID() : paramPmtInfo1.getSrvrTID();
    paramPmtInfo1.setAction("del");
    paramPmtInfo1 = jdMethod_for(paramFFSConnectionHolder, paramPmtInfo1);
    if (paramPmtInfo1.getStatusCode() != 0) {
      return paramPmtInfo1;
    }
    int i = 1;
    int j = 1;
    String str3 = "CANCELEDON";
    if (localPaymentType.isTemplate())
    {
      if (!"ACTIVE".equalsIgnoreCase(str1))
      {
        paramPmtInfo1.setStatusCode(26041);
        paramPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(26041, null, "PAYMENT_MESSAGE"));
        return paramPmtInfo1;
      }
      i = 0;
      j = 0;
    }
    else
    {
      Object localObject;
      if (paramBoolean1)
      {
        try
        {
          if (jdMethod_do(paramPmtInfo1.getRecSrvrTID(), paramPmtInfo1.getFIID(), "RECPMTTRN", paramFFSConnectionHolder) == 0) {
            throw new BPWException("Cannot cancel rec pmt in process!!");
          }
          FFSDebug.log("Updating rec pmt instruction to CANCELEDON", 6);
          RecPmtInstruction.updateStatus(paramFFSConnectionHolder, str2, "CANCELEDON");
          String[] arrayOfString = RecSrvrTIDToSrvrTID.getSrvrTIDs(paramFFSConnectionHolder, str2);
          if (arrayOfString != null)
          {
            FFSDebug.log("Found " + arrayOfString.length + " pending payments", 6);
            for (int m = 0; m < arrayOfString.length; m++)
            {
              localObject = getBillPaymentById(arrayOfString[m], PaymentType.PMTTYPE_RECURRING.getName());
              if ((localObject != null) && (((PmtInfo)localObject).getStatusCode() == 0))
              {
                FFSDebug.log("Deleting pending payment " + ((PmtInfo)localObject).getSrvrTID(), 6);
                ((PmtInfo)localObject).setLogId(BPWTrackingIDGenerator.getNextId());
                jdMethod_if(paramFFSConnectionHolder, (PmtInfo)localObject, false, paramBoolean2);
                RecSrvrTIDToSrvrTID.delete(paramFFSConnectionHolder, str2, arrayOfString[m]);
              }
            }
          }
        }
        catch (Exception localException1)
        {
          throw new BPWException("Cannot cancel pending schedule: " + localException1.getMessage());
        }
      }
      else
      {
        if (("FUNDSREVERTACTIVE".equals(str1)) || (str1.equals("CANCELEDON")))
        {
          paramPmtInfo1.setStatusCode(2017);
          paramPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(2017, null, "API_MESSAGE"));
          return paramPmtInfo1;
        }
        if (("WILLPROCESSON".equals(str1)) || ("NOFUNDSON".equals(str1)) || ("NOFUNDSON_NOTIF".equals(str1)))
        {
          if (!jdMethod_if(paramFFSConnectionHolder, str2, paramPmtInfo1.getFIID())) {
            throw new BPWException("Cannot cancel payment in process");
          }
        }
        else if (!"APPROVAL_PENDING".equals(str1)) {
          if (("APPROVAL_REJECTED".equals(str1)) || ("APPROVAL_NOT_ALLOWED".equals(str1)))
          {
            i = 0;
          }
          else if ("INFUNDSALLOC".equals(str1))
          {
            str3 = "CANCELFUNDS";
            j = 0;
          }
          else
          {
            int k;
            FulfillAgent localFulfillAgent;
            if ("FUNDSALLOCATED".equals(str1))
            {
              i = 0;
              k = paramPmtInfo1.getRouteId();
              localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
              localObject = localFulfillAgent.findPaymentInstructionType(paramPmtInfo1.getFIID(), k);
              if (jdMethod_do(str2, paramPmtInfo1.getFIID(), (String)localObject, paramFFSConnectionHolder) == 0) {
                throw new BPWException("Cannot cancel pmt in process!!");
              }
              str3 = "FUNDSREVERTACTIVE";
            }
            else if ("FUNDSALLOCACTIVE".equals(str1))
            {
              i = 0;
              k = paramPmtInfo1.getRouteId();
              localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
              localObject = localFulfillAgent.findPaymentInstructionType(paramPmtInfo1.getFIID(), k);
              if (jdMethod_do(str2, paramPmtInfo1.getFIID(), (String)localObject, paramFFSConnectionHolder) == 0) {
                throw new BPWException("Cannot cancel pmt in process!!");
              }
              if (!jdMethod_if(paramFFSConnectionHolder, str2, paramPmtInfo1.getFIID())) {
                throw new BPWException("Cannot cancel payment in progress");
              }
              str3 = "FUNDSREVERTACTIVE";
            }
            else
            {
              paramPmtInfo1.setStatusCode(2016);
              paramPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(2016, null, "API_MESSAGE"));
            }
          }
        }
      }
    }
    paramPmtInfo1 = a(paramFFSConnectionHolder, paramPmtInfo1, str3, paramBoolean1);
    if (i != 0) {
      jdMethod_if(paramFFSConnectionHolder, paramPmtInfo2);
    }
    if (paramPmtInfo2.getStatus().equalsIgnoreCase("LIMIT_REVERT_SUCCEEDED")) {
      paramPmtInfo1.setStatus(str3);
    }
    try
    {
      if (("FUNDSALLOCATED".equals(str1)) || ("FUNDSALLOCACTIVE".equals(str1)))
      {
        paramFFSConnectionHolder.conn.commit();
        String str4 = DBConnCache.save(paramFFSConnectionHolder);
        boolean bool = "FUNDSALLOCACTIVE".equals(str1);
        int n = PmtProcessor.revertFunds(str2, str4, bool, paramFFSConnectionHolder);
        DBConnCache.unbind(str4);
        if (n == 0)
        {
          int i1 = PmtInstruction.updateStatus(paramFFSConnectionHolder, str2, "CANCELEDON");
          if (i1 <= 0)
          {
            FFSDebug.log("*** PaymentProcessor.cancelPayment: Payment status is not updated!!  SrvrTID=" + str2 + "Status=" + "CANCELEDON", 0);
            StringWriter localStringWriter = new StringWriter();
            new Exception("Stack Trace").printStackTrace(new PrintWriter(localStringWriter));
            String str5 = localStringWriter.toString();
            FFSDebug.log(str5, 0);
          }
          a(paramFFSConnectionHolder, paramPmtInfo2, true);
        }
        else
        {
          FundsAllocRslt localFundsAllocRslt = new FundsAllocRslt(paramPmtInfo1.getLogId(), str2, n);
          FundAllocRsltProcessor.ProcessOneFundRevertRslt(localFundsAllocRslt, paramFFSConnectionHolder);
        }
      }
    }
    catch (Exception localException2)
    {
      throw new BPWException(localException2.getMessage());
    }
    if (j != 0)
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localAuditAgent == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      if (paramBoolean1) {
        localAuditAgent.cancelOFX200RecPmtRsV1(str2, paramFFSConnectionHolder);
      } else {
        localAuditAgent.cancelOFX200PmtRsV1(str2, paramFFSConnectionHolder);
      }
    }
    return paramPmtInfo1;
  }
  
  private PmtInfo a(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, String paramString, boolean paramBoolean)
    throws BPWException
  {
    String str = FFSUtil.getDateString("yyyyMMddHHmmss");
    paramPmtInfo.setLastModified(str);
    paramPmtInfo.setStatus(paramString);
    if (paramBoolean) {
      RecPmtInstruction.updateStatusLastModified(paramFFSConnectionHolder, paramPmtInfo.getRecSrvrTID(), paramString, str);
    } else {
      PmtInstruction.updateStatusLastModified(paramFFSConnectionHolder, paramPmtInfo.getSrvrTID(), paramString, str);
    }
    return paramPmtInfo;
  }
  
  public PmtInfo getBillPaymentById(String paramString1, String paramString2)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PmtInfo localPmtInfo1 = null;
    String str = "PaymentProcessor.getBillPaymentById(String id, String paymentType):";
    FFSDebug.log(str, " Start ", 6);
    FFSDebug.log(str, "id = " + paramString1 + ", paymentType = " + paramString2, 6);
    Object localObject1;
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      localPmtInfo1 = new PmtInfo();
      localPmtInfo1.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "srvrIId" }, "PAYMENT_MESSAGE");
      localPmtInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log("PaymentProcessor.getBillPaymentById(String id, String paymentType), " + (String)localObject1, 0);
      return localPmtInfo1;
    }
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      localPmtInfo1 = new PmtInfo();
      localPmtInfo1.setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "paymentType" }, "PAYMENT_MESSAGE");
      localPmtInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log("PaymentProcessor.getBillPaymentById(String id, String paymentType), " + (String)localObject1, 0);
      return localPmtInfo1;
    }
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localObject1 = PaymentType.getEnum(paramString2);
      if (localObject1 == null)
      {
        localPmtInfo1 = new PmtInfo();
        localPmtInfo1.setStatusCode(26034);
        localObject2 = BPWLocaleUtil.getMessage(26034, new String[] { paramString2 }, "PAYMENT_MESSAGE");
        localPmtInfo1.setStatusMsg((String)localObject2);
        FFSDebug.log("PaymentProcessor.getBillPaymentById(String id, String paymentType), " + (String)localObject2, 0);
        PmtInfo localPmtInfo2 = localPmtInfo1;
        return localPmtInfo2;
      }
      localPmtInfo1 = ((PaymentType)localObject1).isRecurring() ? RecPmtInstruction.getRecPmtInfo(paramString1, localFFSConnectionHolder) : PmtInstruction.getPmtInfo(paramString1, localFFSConnectionHolder);
      if (localPmtInfo1 == null)
      {
        localPmtInfo1 = new PmtInfo();
        localPmtInfo1.setStatusCode(16020);
        localPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16020, null, "PAYMENT_MESSAGE"));
      }
      else
      {
        localPmtInfo1.setStatusCode(0);
        localPmtInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
      }
      Object localObject2 = localPmtInfo1;
      return localObject2;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PaymentProcessor.getBillPaymentById failed:" + localException1.toString());
      throw new BPWException(localException1, localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.getBillPaymentById failed:" + localException2.toString());
        throw new BPWException(localException2.toString());
      }
    }
  }
  
  public PaymentBatchInfo addPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.addPaymentBatch:", " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = null;
    PmtInfo[] arrayOfPmtInfo = null;
    String str = null;
    int i = 0;
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    int j = 1;
    try
    {
      Object localObject1;
      Object localObject2;
      if (paramPaymentBatchInfo == null)
      {
        localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "PaymentBatchInfo" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo = new PaymentBatchInfo();
        paramPaymentBatchInfo.setStatusCode(16000);
        paramPaymentBatchInfo.setStatusMsg((String)localObject1);
        localObject2 = paramPaymentBatchInfo;
        return localObject2;
      }
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      a(localFFSConnectionHolder, paramPaymentBatchInfo);
      if (paramPaymentBatchInfo.getStatusCode() != 0)
      {
        localObject1 = paramPaymentBatchInfo;
        return localObject1;
      }
      str = paramPaymentBatchInfo.getBatchType();
      arrayOfPmtInfo = paramPaymentBatchInfo.getPayments();
      if (str.equals("PAYMENT"))
      {
        i = arrayOfPmtInfo.length;
        paramPaymentBatchInfo.setStatusCode(0);
        paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
        for (int k = 0; k < i; k++) {
          if (arrayOfPmtInfo[k].getStatusCode() == 0)
          {
            arrayOfPmtInfo[k] = addBillPayment(arrayOfPmtInfo[k]);
            if (arrayOfPmtInfo[k].getStatusCode() == 0)
            {
              localObject2 = FFSUtil.getBigDecimal(arrayOfPmtInfo[k].getAmt());
              localBigDecimal = localBigDecimal.add((BigDecimal)localObject2);
              j = 0;
            }
          }
          else
          {
            paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[k].getStatusCode());
            paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[k].getStatusMsg());
            localObject2 = paramPaymentBatchInfo;
            return localObject2;
          }
        }
        if ((i > 0) && (j != 0))
        {
          paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[0].getStatusCode());
          paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[0].getStatusMsg());
          PaymentBatchInfo localPaymentBatchInfo1 = paramPaymentBatchInfo;
          return localPaymentBatchInfo1;
        }
        paramPaymentBatchInfo.setTotalAmount(String.valueOf(localBigDecimal.setScale(4, 4)));
        paramPaymentBatchInfo.setStatusCode(0);
        paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
      }
      else if (str.equals("TEMPLATE"))
      {
        int m = (arrayOfPmtInfo[0] instanceof RecPmtInfo) ? 1 : 0;
        Object localObject4;
        for (int n = 1; n < i; n++)
        {
          i1 = (arrayOfPmtInfo[n] instanceof RecPmtInfo) ? 1 : 0;
          if (m != i1)
          {
            paramPaymentBatchInfo.setStatusCode(26040);
            paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(26040, null, "PAYMENT_MESSAGE"));
            localObject4 = paramPaymentBatchInfo;
            return localObject4;
          }
        }
        if (!a(localFFSConnectionHolder, paramPaymentBatchInfo, false))
        {
          paramPaymentBatchInfo.setStatusCode(26030);
          paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(26030, null, "PAYMENT_MESSAGE"));
          localObject3 = paramPaymentBatchInfo;
          return localObject3;
        }
        Object localObject3 = DBUtil.getNewTransId("SrvrTID", 18);
        paramPaymentBatchInfo.setBatchID((String)localObject3);
        i = arrayOfPmtInfo.length;
        for (int i1 = 0; i1 < i; i1++)
        {
          localObject4 = FFSUtil.getBigDecimal(arrayOfPmtInfo[i1].getAmt());
          localBigDecimal = localBigDecimal.add((BigDecimal)localObject4);
        }
        paramPaymentBatchInfo.setTotalAmount(String.valueOf(localBigDecimal.setScale(4, 4)));
        paramPaymentBatchInfo = PaymentBatch.addPaymentBatch(localFFSConnectionHolder, paramPaymentBatchInfo);
        if (paramPaymentBatchInfo.getStatusCode() != 0)
        {
          localFFSConnectionHolder.conn.rollback();
          PaymentBatchInfo localPaymentBatchInfo2 = paramPaymentBatchInfo;
          return localPaymentBatchInfo2;
        }
        for (int i2 = 0; i2 < i; i2++)
        {
          arrayOfPmtInfo[i2].setBatchID((String)localObject3);
          arrayOfPmtInfo[i2] = jdMethod_goto(localFFSConnectionHolder, arrayOfPmtInfo[i2]);
          if (arrayOfPmtInfo[i2].getStatusCode() != 0)
          {
            paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[i2].getStatusCode());
            paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[i2].getStatusMsg());
            localFFSConnectionHolder.conn.rollback();
            localObject4 = paramPaymentBatchInfo;
            return localObject4;
          }
        }
        if (this.bI >= 3)
        {
          i2 = Integer.parseInt(paramPaymentBatchInfo.getCustomerId());
          localObject4 = BPWLocaleUtil.getMessage(820, null, "BILLPAY_AUDITLOG_MESSAGE");
          String[] arrayOfString = { localObject4 };
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramPaymentBatchInfo.getSubmittedBy(), paramPaymentBatchInfo.getAgentId(), paramPaymentBatchInfo.getAgentType(), localLocalizableString, paramPaymentBatchInfo.getLogId(), 2015, i2, localBigDecimal, paramPaymentBatchInfo.getAmountCurrency(), paramPaymentBatchInfo.getBatchID(), paramPaymentBatchInfo.getBatchStatus(), null, null, null, null, -1);
        }
        localFFSConnectionHolder.conn.commit();
        paramPaymentBatchInfo.setStatusCode(0);
        paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
      }
    }
    catch (Exception localException1)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      FFSDebug.log("*** PaymentProcessor.addPaymentBatch failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSConnectionHolder.conn != null) {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.addPaymentBatch failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PaymentProcessor.addPaymentBatch:", " end ", 6);
    return paramPaymentBatchInfo;
  }
  
  public PaymentBatchInfo modifyPaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.modifyPaymentBatch:", " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PaymentBatchInfo localPaymentBatchInfo1 = null;
    PmtInfo[] arrayOfPmtInfo = null;
    BigDecimal localBigDecimal1 = null;
    int i = 0;
    String str1 = null;
    try
    {
      Object localObject1;
      PaymentBatchInfo localPaymentBatchInfo2;
      if (paramPaymentBatchInfo == null)
      {
        localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "PaymentBatchInfo" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo = new PaymentBatchInfo();
        paramPaymentBatchInfo.setStatusCode(16000);
        paramPaymentBatchInfo.setStatusMsg((String)localObject1);
        localPaymentBatchInfo2 = paramPaymentBatchInfo;
        return localPaymentBatchInfo2;
      }
      str1 = paramPaymentBatchInfo.getBatchID();
      if ((str1 == null) || (str1.trim().length() == 0))
      {
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "BatchID" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo.setStatusCode(16010);
        paramPaymentBatchInfo.setStatusMsg((String)localObject1);
        localPaymentBatchInfo2 = paramPaymentBatchInfo;
        return localPaymentBatchInfo2;
      }
      localPaymentBatchInfo1 = getPaymentBatchById(str1);
      if (localPaymentBatchInfo1.getStatusCode() != 0)
      {
        paramPaymentBatchInfo.setStatusCode(localPaymentBatchInfo1.getStatusCode());
        paramPaymentBatchInfo.setStatusMsg(localPaymentBatchInfo1.getStatusMsg());
        localObject1 = paramPaymentBatchInfo;
        return localObject1;
      }
      if (!localPaymentBatchInfo1.getBatchStatus().equals("ACTIVE"))
      {
        localObject1 = BPWLocaleUtil.getMessage(26038, null, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo.setStatusCode(26038);
        paramPaymentBatchInfo.setStatusMsg((String)localObject1);
        localPaymentBatchInfo2 = paramPaymentBatchInfo;
        return localPaymentBatchInfo2;
      }
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      a(localFFSConnectionHolder, paramPaymentBatchInfo);
      if (paramPaymentBatchInfo.getStatusCode() != 0)
      {
        localObject1 = paramPaymentBatchInfo;
        return localObject1;
      }
      if ((localPaymentBatchInfo1.getBatchName() != null) && (!localPaymentBatchInfo1.getBatchName().equals(paramPaymentBatchInfo.getBatchName())) && (!a(localFFSConnectionHolder, paramPaymentBatchInfo, true)))
      {
        paramPaymentBatchInfo.setStatusCode(26030);
        paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(26030, null, "PAYMENT_MESSAGE"));
        localObject1 = paramPaymentBatchInfo;
        return localObject1;
      }
      i = Integer.parseInt(localPaymentBatchInfo1.getPaymentCount());
      localBigDecimal1 = new BigDecimal(localPaymentBatchInfo1.getTotalAmount());
      arrayOfPmtInfo = paramPaymentBatchInfo.getPayments();
      if ((arrayOfPmtInfo == null) || (arrayOfPmtInfo.length == 0))
      {
        paramPaymentBatchInfo.setStatusCode(26029);
        paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(26029, null, "PAYMENT_MESSAGE"));
        localObject1 = paramPaymentBatchInfo;
        return localObject1;
      }
      int j = (arrayOfPmtInfo[0] instanceof RecPmtInfo) ? 1 : 0;
      Object localObject2;
      for (int k = 1; k < arrayOfPmtInfo.length; k++)
      {
        int n = (arrayOfPmtInfo[k] instanceof RecPmtInfo) ? 1 : 0;
        if (j != n)
        {
          paramPaymentBatchInfo.setStatusCode(26040);
          paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(26040, null, "PAYMENT_MESSAGE"));
          localObject2 = paramPaymentBatchInfo;
          return localObject2;
        }
      }
      String str2;
      Object localObject3;
      for (k = 0; k < arrayOfPmtInfo.length; k++)
      {
        str2 = arrayOfPmtInfo[k].getAction();
        if ((str2 == null) || (str2.trim().length() == 0))
        {
          arrayOfPmtInfo[k].setStatusCode(0);
        }
        else if (str2.equalsIgnoreCase("add"))
        {
          arrayOfPmtInfo[k].setBatchID(paramPaymentBatchInfo.getBatchID());
          if (arrayOfPmtInfo[k].getFIID() == null) {
            arrayOfPmtInfo[k].setFIID(paramPaymentBatchInfo.getFIID());
          }
          arrayOfPmtInfo[k] = jdMethod_goto(localFFSConnectionHolder, arrayOfPmtInfo[k]);
          if (arrayOfPmtInfo[k].getStatusCode() != 0)
          {
            paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[k].getStatusCode());
            paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[k].getStatusMsg());
            localFFSConnectionHolder.conn.rollback();
            localObject2 = paramPaymentBatchInfo;
            return localObject2;
          }
          i++;
          localObject2 = FFSUtil.getBigDecimal(arrayOfPmtInfo[k].getAmt());
          localBigDecimal1 = localBigDecimal1.add((BigDecimal)localObject2);
        }
        else if (str2.equalsIgnoreCase("mod"))
        {
          localObject2 = getBillPaymentById(arrayOfPmtInfo[k].getSrvrTID(), "TEMPLATE");
          if (((PmtInfo)localObject2).getStatusCode() != 0)
          {
            paramPaymentBatchInfo.setStatusCode(((PmtInfo)localObject2).getStatusCode());
            paramPaymentBatchInfo.setStatusMsg(((PmtInfo)localObject2).getStatusMsg());
            localFFSConnectionHolder.conn.rollback();
            localObject3 = paramPaymentBatchInfo;
            return localObject3;
          }
          arrayOfPmtInfo[k].setBatchID(paramPaymentBatchInfo.getBatchID());
          arrayOfPmtInfo[k] = a(localFFSConnectionHolder, arrayOfPmtInfo[k], false, true);
          if (arrayOfPmtInfo[k].getStatusCode() != 0)
          {
            paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[k].getStatusCode());
            paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[k].getStatusMsg());
            localFFSConnectionHolder.conn.rollback();
            localObject3 = paramPaymentBatchInfo;
            return localObject3;
          }
          localObject3 = FFSUtil.getBigDecimal(((PmtInfo)localObject2).getAmt());
          localBigDecimal1 = localBigDecimal1.subtract((BigDecimal)localObject3);
          BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal(arrayOfPmtInfo[k].getAmt());
          localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
        }
        else if (str2.equalsIgnoreCase("del"))
        {
          arrayOfPmtInfo[k].setBatchID(paramPaymentBatchInfo.getBatchID());
          arrayOfPmtInfo[k] = jdMethod_if(localFFSConnectionHolder, arrayOfPmtInfo[k], false, true);
          if (arrayOfPmtInfo[k].getStatusCode() != 0)
          {
            paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[k].getStatusCode());
            paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[k].getStatusMsg());
            localFFSConnectionHolder.conn.rollback();
            localObject2 = paramPaymentBatchInfo;
            return localObject2;
          }
          i--;
          localObject2 = FFSUtil.getBigDecimal(arrayOfPmtInfo[k].getAmt());
          localBigDecimal1 = localBigDecimal1.subtract((BigDecimal)localObject2);
        }
        else
        {
          arrayOfPmtInfo[k].setStatusCode(17180);
          arrayOfPmtInfo[k].setStatusMsg(BPWLocaleUtil.getMessage(17180, null, "PAYMENT_MESSAGE"));
          paramPaymentBatchInfo.setStatusCode(arrayOfPmtInfo[k].getStatusCode());
          paramPaymentBatchInfo.setStatusMsg(arrayOfPmtInfo[k].getStatusMsg());
          localFFSConnectionHolder.conn.rollback();
          localObject2 = paramPaymentBatchInfo;
          return localObject2;
        }
      }
      PaymentBatchInfo localPaymentBatchInfo3;
      if (i == 0)
      {
        localFFSConnectionHolder.conn.rollback();
        paramPaymentBatchInfo.setStatusCode(26029);
        paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(26029, null, "PAYMENT_MESSAGE"));
        localPaymentBatchInfo3 = paramPaymentBatchInfo;
        return localPaymentBatchInfo3;
      }
      paramPaymentBatchInfo.setPaymentCount(Integer.toString(i));
      paramPaymentBatchInfo.setTotalAmount(String.valueOf(localBigDecimal1.setScale(4, 4)));
      paramPaymentBatchInfo = PaymentBatch.modifyPaymentBatch(localFFSConnectionHolder, paramPaymentBatchInfo);
      if (paramPaymentBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localPaymentBatchInfo3 = paramPaymentBatchInfo;
        return localPaymentBatchInfo3;
      }
      if (this.bI >= 3)
      {
        int m = Integer.parseInt(paramPaymentBatchInfo.getCustomerId());
        str2 = BPWLocaleUtil.getMessage(821, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject2 = new String[] { str2 };
        localObject3 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramPaymentBatchInfo.getSubmittedBy(), paramPaymentBatchInfo.getAgentId(), paramPaymentBatchInfo.getAgentType(), (ILocalizable)localObject3, paramPaymentBatchInfo.getLogId(), 2016, m, localBigDecimal1, paramPaymentBatchInfo.getAmountCurrency(), paramPaymentBatchInfo.getBatchID(), paramPaymentBatchInfo.getBatchStatus(), null, null, null, null, -1);
      }
      localFFSConnectionHolder.conn.commit();
      paramPaymentBatchInfo.setStatusCode(0);
      paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** PaymentProcessor.modifyPaymentBatch failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.modifyPaymentBatch failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PaymentProcessor.modifyPaymentBatch:", " end ", 6);
    return paramPaymentBatchInfo;
  }
  
  public PaymentBatchInfo deletePaymentBatch(PaymentBatchInfo paramPaymentBatchInfo)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      if (paramPaymentBatchInfo == null)
      {
        str = BPWLocaleUtil.getMessage(16000, new String[] { "PaymentBatchInfo" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo = new PaymentBatchInfo();
        paramPaymentBatchInfo.setStatusCode(16000);
        paramPaymentBatchInfo.setStatusMsg(str);
        localObject1 = paramPaymentBatchInfo;
        return localObject1;
      }
      String str = paramPaymentBatchInfo.getBatchID();
      Object localObject2;
      if ((str == null) || (str.trim().length() == 0))
      {
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "BatchID" }, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo.setStatusCode(16010);
        paramPaymentBatchInfo.setStatusMsg((String)localObject1);
        localObject2 = paramPaymentBatchInfo;
        return localObject2;
      }
      Object localObject1 = getPaymentBatchById(str);
      if (((PaymentBatchInfo)localObject1).getStatusCode() != 0)
      {
        paramPaymentBatchInfo.setStatusCode(((PaymentBatchInfo)localObject1).getStatusCode());
        paramPaymentBatchInfo.setStatusMsg(((PaymentBatchInfo)localObject1).getStatusMsg());
        localObject2 = paramPaymentBatchInfo;
        return localObject2;
      }
      Object localObject3;
      if (!((PaymentBatchInfo)localObject1).getBatchStatus().equals("ACTIVE"))
      {
        localObject2 = BPWLocaleUtil.getMessage(26038, null, "PAYMENT_MESSAGE");
        paramPaymentBatchInfo.setStatusCode(26038);
        paramPaymentBatchInfo.setStatusMsg((String)localObject2);
        localObject3 = paramPaymentBatchInfo;
        return localObject3;
      }
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      paramPaymentBatchInfo = PaymentBatch.cancelPaymentBatch(localFFSConnectionHolder, paramPaymentBatchInfo);
      if (paramPaymentBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramPaymentBatchInfo;
        return localObject2;
      }
      PmtInstruction.updateStatusLastModifiedByBatchId(localFFSConnectionHolder, paramPaymentBatchInfo.getBatchID(), "CANCELEDON");
      RecPmtInstruction.updateStatusLastModifiedByBatchId(localFFSConnectionHolder, paramPaymentBatchInfo.getBatchID(), "CANCELEDON");
      if (this.bI >= 3)
      {
        int i = Integer.parseInt(paramPaymentBatchInfo.getCustomerId());
        localObject3 = BPWLocaleUtil.getMessage(822, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { localObject3 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramPaymentBatchInfo.getSubmittedBy(), paramPaymentBatchInfo.getAgentId(), paramPaymentBatchInfo.getAgentType(), localLocalizableString, paramPaymentBatchInfo.getLogId(), 2017, i, new BigDecimal(paramPaymentBatchInfo.getTotalAmount()), paramPaymentBatchInfo.getAmountCurrency(), paramPaymentBatchInfo.getBatchID(), paramPaymentBatchInfo.getBatchStatus(), null, null, null, null, -1);
      }
      localFFSConnectionHolder.conn.commit();
      paramPaymentBatchInfo.setStatusCode(0);
      paramPaymentBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** PaymentProcessor.deletePaymentBatch failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.deletePaymentBatch failed:" + localException2.toString());
      }
    }
    return paramPaymentBatchInfo;
  }
  
  public PaymentBatchInfo getPaymentBatchById(String paramString)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    PaymentBatchInfo localPaymentBatchInfo1 = null;
    try
    {
      Object localObject1;
      if ((paramString == null) || (paramString.trim().length() == 0))
      {
        localPaymentBatchInfo1 = new PaymentBatchInfo();
        localPaymentBatchInfo1.setStatusCode(16000);
        localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "BatchId" }, "PAYMENT_MESSAGE");
        localPaymentBatchInfo1.setStatusMsg((String)localObject1);
        FFSDebug.log("PaymentProcessor.getPaymentBatchById(String id), " + (String)localObject1, 0);
        PaymentBatchInfo localPaymentBatchInfo2 = localPaymentBatchInfo1;
        return localPaymentBatchInfo2;
      }
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localPaymentBatchInfo1 = PaymentBatch.getPaymentBatch(localFFSConnectionHolder, paramString);
      if (localPaymentBatchInfo1 == null)
      {
        localPaymentBatchInfo1 = new PaymentBatchInfo();
        localPaymentBatchInfo1.setStatusCode(16020);
        localPaymentBatchInfo1.setStatusMsg(BPWLocaleUtil.getMessage(16020, new String[] { "PaymentBatch" }, "PAYMENT_MESSAGE"));
      }
      else
      {
        localObject1 = PmtInstruction.getPaymentsByBatchId(localFFSConnectionHolder, paramString);
        localPaymentBatchInfo1.setPayments((PmtInfo[])((ArrayList)localObject1).toArray(new PmtInfo[((ArrayList)localObject1).size()]));
        localPaymentBatchInfo1.setStatusCode(0);
        localPaymentBatchInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE"));
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PaymentProcessor.getPaymentBatchById failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.getPaymentBatchById failed:" + localException2.toString());
      }
    }
    return localPaymentBatchInfo1;
  }
  
  public LastPaymentInfo getLastPayments(LastPaymentInfo paramLastPaymentInfo)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      String str1 = null;
      CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = paramLastPaymentInfo.getCustPayeeList();
      ArrayList localArrayList = new ArrayList();
      str1 = paramLastPaymentInfo.getFiId();
      Object localObject2;
      if (str1 == null)
      {
        paramLastPaymentInfo.setStatusCode(16000);
        localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "FIID" }, "PAYMENT_MESSAGE");
        paramLastPaymentInfo.setStatusMsg((String)localObject1);
        localObject2 = paramLastPaymentInfo;
        return localObject2;
      }
      Object localObject1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, str1);
      if (((BPWFIInfo)localObject1).getStatusCode() != 0)
      {
        paramLastPaymentInfo.setStatusCode(((BPWFIInfo)localObject1).getStatusCode());
        paramLastPaymentInfo.setStatusMsg(((BPWFIInfo)localObject1).getStatusMsg());
        localObject2 = paramLastPaymentInfo;
        return localObject2;
      }
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if ((arrayOfCustomerPayeeInfo == null) || (arrayOfCustomerPayeeInfo.length == 0))
      {
        localObject2 = paramLastPaymentInfo.getCustomerId();
        if ((localObject2 == null) || (((String)localObject2).trim().length() == 0))
        {
          paramLastPaymentInfo.setStatusCode(16000);
          localObject3 = BPWLocaleUtil.getMessage(16000, new String[] { "CustomerId" }, "PAYMENT_MESSAGE");
          paramLastPaymentInfo.setStatusMsg((String)localObject3);
          localObject4 = paramLastPaymentInfo;
          return localObject4;
        }
        localObject3 = Customer.getCustomerByID((String)localObject2, localFFSConnectionHolder);
        if ((localObject3 == null) || (!((CustomerInfo)localObject3).fIID.equals(str1)))
        {
          localObject4 = BPWLocaleUtil.getMessage(19130, new String[] { localObject2 }, "PAYMENT_MESSAGE");
          paramLastPaymentInfo.setStatusCode(19130);
          paramLastPaymentInfo.setStatusMsg((String)localObject4);
          localObject5 = paramLastPaymentInfo;
          return localObject5;
        }
        arrayOfCustomerPayeeInfo = CustPayee.getCustPayeeByUID((String)localObject2, localFFSConnectionHolder);
        if ((arrayOfCustomerPayeeInfo == null) || (arrayOfCustomerPayeeInfo.length == 0))
        {
          paramLastPaymentInfo.setStatusCode(16000);
          localObject4 = BPWLocaleUtil.getMessage(16000, new String[] { "CustomerPayeeInfo" }, "PAYMENT_MESSAGE");
          paramLastPaymentInfo.setStatusMsg((String)localObject4);
          localObject5 = paramLastPaymentInfo;
          return localObject5;
        }
        paramLastPaymentInfo.setCustPayeeList(arrayOfCustomerPayeeInfo);
      }
      for (int i = 0; i < arrayOfCustomerPayeeInfo.length; i++)
      {
        localObject3 = null;
        localObject4 = arrayOfCustomerPayeeInfo[i];
        localObject5 = ((CustomerPayeeInfo)localObject4).CustomerID;
        int j = ((CustomerPayeeInfo)localObject4).PayeeListID;
        localObject3 = PmtInstruction.getLastPaymentByPayeeId(localFFSConnectionHolder, str1, (String)localObject5, j);
        if (localObject3 != null) {
          localArrayList.add(localObject3);
        }
      }
      String str2;
      if (localArrayList.size() == 0)
      {
        paramLastPaymentInfo.setLastPayments(null);
        paramLastPaymentInfo.setStatusCode(26027);
        str2 = BPWLocaleUtil.getMessage(26027, null, "PAYMENT_MESSAGE");
        paramLastPaymentInfo.setStatusMsg(str2);
      }
      else
      {
        paramLastPaymentInfo.setLastPayments((PmtInfo[])localArrayList.toArray(new PmtInfo[0]));
        paramLastPaymentInfo.setStatusCode(0);
        str2 = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
        paramLastPaymentInfo.setStatusMsg(str2);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PaymentProcessor.getLastPayments failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** PaymentProcessor.getLastPayments failed:" + localException2.toString());
      }
    }
    return paramLastPaymentInfo;
  }
  
  private ScheduleInfo a(PmtInfo paramPmtInfo)
    throws BPWException
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    String str = Integer.toString(paramPmtInfo.getStartDate()).substring(0, 8) + "00";
    localScheduleInfo.FIId = paramPmtInfo.getFIID();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.StartDate = Integer.parseInt(str);
    localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
    if ((paramPmtInfo instanceof RecPmtInfo)) {
      localScheduleInfo.Frequency = paramPmtInfo.getRecFrequencyValue();
    } else {
      localScheduleInfo.Frequency = FrequencyType.ONCE.getValue();
    }
    if ((paramPmtInfo instanceof RecPmtInfo)) {
      localScheduleInfo.InstanceCount = ((RecPmtInfo)paramPmtInfo).InstanceCount;
    } else {
      localScheduleInfo.InstanceCount = 1;
    }
    localScheduleInfo.LogID = paramPmtInfo.getLogID();
    localScheduleInfo.CurInstanceNum = 1;
    return localScheduleInfo;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, PaymentBatchInfo paramPaymentBatchInfo)
    throws BPWException
  {
    String str1 = null;
    String str2 = paramPaymentBatchInfo.getFIID();
    String str3 = paramPaymentBatchInfo.getCustomerId();
    String str4 = paramPaymentBatchInfo.getBatchName();
    String str5 = paramPaymentBatchInfo.getBatchType();
    String str6 = paramPaymentBatchInfo.getAmountCurrency();
    String str7 = paramPaymentBatchInfo.getPaymentCount();
    String str8 = paramPaymentBatchInfo.getSubmittedBy();
    String str9 = paramPaymentBatchInfo.getLogId();
    PmtInfo[] arrayOfPmtInfo = paramPaymentBatchInfo.getPayments();
    if (jdMethod_try(str2))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "FIID" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if (jdMethod_try(str3))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "CustomerID" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    CustomerInfo localCustomerInfo = Customer.getCustomerByID(str3, paramFFSConnectionHolder);
    if (localCustomerInfo == null)
    {
      str1 = BPWLocaleUtil.getMessage(19130, new String[] { str3 }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(19130);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if (!localCustomerInfo.fIID.equals(str2)) {
      try
      {
        BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str2);
        if (localBPWFIInfo.getStatusCode() != 0)
        {
          paramPaymentBatchInfo.setStatusCode(localBPWFIInfo.getStatusCode());
          paramPaymentBatchInfo.setStatusMsg(localBPWFIInfo.getStatusMsg());
          return;
        }
      }
      catch (Exception localException1)
      {
        throw new BPWException(localException1.getMessage());
      }
    }
    if ((this.bH) && (localCustomerInfo.status.equalsIgnoreCase("INACTIVE")))
    {
      str1 = "This customer, customerID=" + str3 + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (jdMethod_try(str5))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "BatchType" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if ((!str5.equals("TEMPLATE")) && (!str5.equals("PAYMENT")))
    {
      str1 = BPWLocaleUtil.getMessage(26031, null, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(26031);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if ((str5.equals("TEMPLATE")) && (jdMethod_try(str4)))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "BatchName" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if (jdMethod_try(str6)) {
      paramPaymentBatchInfo.setAmountCurrency("USD");
    }
    if (jdMethod_try(str8))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "SubmittedBy" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if (jdMethod_try(str9))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "logId" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if (jdMethod_try(str7))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "PaymentBatchInfo", "PaymentCount" }, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(16010);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(str7);
      if (i < 1) {
        throw new Exception();
      }
    }
    catch (Exception localException2)
    {
      str1 = BPWLocaleUtil.getMessage(26036, null, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(26036);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if ((arrayOfPmtInfo == null) || (arrayOfPmtInfo.length == 0))
    {
      str1 = BPWLocaleUtil.getMessage(26029, null, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(26029);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    if (arrayOfPmtInfo.length != i)
    {
      str1 = BPWLocaleUtil.getMessage(26037, null, "PAYMENT_MESSAGE");
      paramPaymentBatchInfo.setStatusCode(26037);
      paramPaymentBatchInfo.setStatusMsg(str1);
      return;
    }
    for (int j = 0; j < arrayOfPmtInfo.length; j++)
    {
      if (!jdMethod_try(arrayOfPmtInfo[j].getFIID()))
      {
        if (!arrayOfPmtInfo[j].getFIID().equals(str2))
        {
          str1 = BPWLocaleUtil.getMessage(26032, null, "PAYMENT_MESSAGE");
          arrayOfPmtInfo[j].setStatusCode(26032);
          arrayOfPmtInfo[j].setStatusMsg(str1);
          paramPaymentBatchInfo.setStatusCode(26032);
          paramPaymentBatchInfo.setStatusMsg(str1);
          return;
        }
        arrayOfPmtInfo[j].setStatusCode(0);
        arrayOfPmtInfo[j].setStatusMsg(null);
      }
      else
      {
        arrayOfPmtInfo[j].setFIID(str2);
        arrayOfPmtInfo[j].setStatusCode(0);
        arrayOfPmtInfo[j].setStatusMsg(null);
      }
      if (!jdMethod_try(arrayOfPmtInfo[j].getCustomerID()))
      {
        if (!arrayOfPmtInfo[j].getCustomerID().equals(str3))
        {
          str1 = BPWLocaleUtil.getMessage(26033, null, "PAYMENT_MESSAGE");
          arrayOfPmtInfo[j].setStatusCode(26033);
          arrayOfPmtInfo[j].setStatusMsg(str1);
          paramPaymentBatchInfo.setStatusCode(26033);
          paramPaymentBatchInfo.setStatusMsg(str1);
          return;
        }
        arrayOfPmtInfo[j].setStatusCode(0);
        arrayOfPmtInfo[j].setStatusMsg(null);
      }
      else
      {
        arrayOfPmtInfo[j].setCustomerID(str3);
        arrayOfPmtInfo[j].setStatusCode(0);
        arrayOfPmtInfo[j].setStatusMsg(null);
      }
    }
    paramPaymentBatchInfo.setStatusCode(0);
    paramPaymentBatchInfo.setStatusMsg(null);
  }
  
  public static TypePmtTrnRsV1 generateOFXAddResponse(PmtInfo paramPmtInfo)
    throws BPWException
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = generateOFXRequest(paramPmtInfo);
    try
    {
      return new PmtProcessor2().buildPmtAddRs(localTypePmtTrnRqV1, paramPmtInfo.getFIID(), paramPmtInfo.getSrvrTID(), String.valueOf(paramPmtInfo.getPayeeListID()), paramPmtInfo.getPayeeInfo(), paramPmtInfo.getCurDef());
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Unable to generate ofx response for add operation");
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static TypeRecPmtTrnRsV1 generateOFXAddRecResponse(RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = generateOFXRecRequest(paramRecPmtInfo);
    try
    {
      return new RecPmtProcessor2().buildRecPmtAddRsV1(localTypeRecPmtTrnRqV1, paramRecPmtInfo.getRecSrvrTID(), String.valueOf(paramRecPmtInfo.getPayeeListID()), paramRecPmtInfo.getPayeeInfo(), paramRecPmtInfo.getCurDef());
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Unable to generate ofx response for add rec operation");
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static TypePmtTrnRsV1 generateOFXModResponse(PmtInfo paramPmtInfo)
    throws BPWException
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = generateOFXRequest(paramPmtInfo);
    try
    {
      return new PmtProcessor2().buildPmtModRs(localTypePmtTrnRqV1, paramPmtInfo.getFIID(), String.valueOf(paramPmtInfo.getPayeeListID()), paramPmtInfo.getPayeeInfo(), paramPmtInfo.getCurDef());
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Unable to generate ofx response for mod operation");
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static TypeRecPmtTrnRsV1 generateOFXModRecResponse(RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = generateOFXRecRequest(paramRecPmtInfo);
    try
    {
      return new RecPmtProcessor2().buildRecPmtModRsV1(localTypeRecPmtTrnRqV1, String.valueOf(paramRecPmtInfo.getPayeeListID()), paramRecPmtInfo.getPayeeInfo(), paramRecPmtInfo.getCurDef());
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Unable to generate ofx response for mod rec operation");
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static TypePmtTrnRqV1 generateOFXRequest(PmtInfo paramPmtInfo)
    throws BPWException
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1();
    TypeUserData localTypeUserData = new TypeUserData();
    try
    {
      BillPayOFX200PaymentMapper.mapPmtInfoToPmtTrnRq(paramPmtInfo, localTypePmtTrnRqV1, localTypeUserData);
      return localTypePmtTrnRqV1;
    }
    catch (Exception localException)
    {
      throw new BPWException("Unable to convert to ofx request: " + localException.getMessage());
    }
  }
  
  public static TypeRecPmtTrnRqV1 generateOFXRecRequest(RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    TypeRecPmtTrnRqV1 localTypeRecPmtTrnRqV1 = new TypeRecPmtTrnRqV1();
    TypeUserData localTypeUserData = new TypeUserData();
    try
    {
      BillPayOFX200PaymentMapper.mapRecPmtInfoToRecPmtTrnRq(paramRecPmtInfo, localTypeRecPmtTrnRqV1, localTypeUserData);
      return localTypeRecPmtTrnRqV1;
    }
    catch (Exception localException)
    {
      throw new BPWException("Unable to convert to ofx rec request: " + localException.getMessage());
    }
  }
  
  public static TypeUserData generateUserData(PmtInfo paramPmtInfo)
    throws BPWException
  {
    TypeUserData localTypeUserData = new TypeUserData();
    localTypeUserData._ofxHeader = "100";
    localTypeUserData._version = "200";
    localTypeUserData._security = "NONE";
    localTypeUserData._oldFileUID = "";
    localTypeUserData._newFileUID = "";
    localTypeUserData._data = "OFXSGML";
    localTypeUserData._encoding = "USASCII";
    localTypeUserData._charSet = "1252";
    localTypeUserData._compression = "";
    localTypeUserData._org = "";
    localTypeUserData._fid = paramPmtInfo.FIID;
    localTypeUserData._uid = paramPmtInfo.CustomerID;
    localTypeUserData._userDefined = null;
    localTypeUserData._submittedBy = (paramPmtInfo.submittedBy == null ? localTypeUserData._uid : paramPmtInfo.submittedBy);
    localTypeUserData._tran_id = paramPmtInfo.LogID;
    return localTypeUserData;
  }
  
  private boolean jdMethod_null(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    FFSDebug.log("Validating payment info", 6);
    String str = null;
    if (paramPmtInfo.getStartDate() <= 0)
    {
      FFSDebug.log("Validation fails: Payment start date not set", 6);
      str = BPWLocaleUtil.getMessage(100000, null, "API_MESSAGE");
      paramPmtInfo.setStatusCode(100000);
      paramPmtInfo.setStatusMsg(str);
      return false;
    }
    return jdMethod_byte(paramFFSConnectionHolder, paramPmtInfo);
  }
  
  private boolean jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, RecPmtInfo paramRecPmtInfo)
    throws BPWException
  {
    if (FFSUtil.isNegative(paramRecPmtInfo.getAmt()))
    {
      paramRecPmtInfo.setStatusCode(2012);
      paramRecPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2012, null, "API_MESSAGE"));
      return false;
    }
    if (FFSUtil.isNegative(paramRecPmtInfo.getInitialAmount()))
    {
      paramRecPmtInfo.setStatusCode(2012);
      paramRecPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2012, null, "API_MESSAGE"));
      return false;
    }
    if (FFSUtil.isNegative(paramRecPmtInfo.getFinalAmount()))
    {
      paramRecPmtInfo.setStatusCode(2012);
      paramRecPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(2012, null, "API_MESSAGE"));
      return false;
    }
    if ((paramRecPmtInfo.getInstanceCount() == 1) || (paramRecPmtInfo.getInstanceCount() < 0))
    {
      paramRecPmtInfo.setStatusCode(100001);
      paramRecPmtInfo.setStatusMsg(BPWLocaleUtil.getMessage(100001, null, "API_MESSAGE"));
      return false;
    }
    return jdMethod_null(paramFFSConnectionHolder, paramRecPmtInfo);
  }
  
  private boolean jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    String str1 = null;
    PaymentType localPaymentType = PaymentType.getEnum(paramPmtInfo.getPaymentType());
    String str2 = localPaymentType.isRecurring() ? "RecPmtInfo" : "PmtInfo";
    if (jdMethod_try(paramPmtInfo.getFIID()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "FIID" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation fails: fiid is null", 6);
      return false;
    }
    if (jdMethod_try(paramPmtInfo.getCustomerID()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "CustomerID" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation fails: customer id is null", 6);
      return false;
    }
    CustomerInfo localCustomerInfo = Customer.getCustomerByID(paramPmtInfo.getCustomerID(), paramFFSConnectionHolder);
    if (localCustomerInfo == null)
    {
      str1 = BPWLocaleUtil.getMessage(19130, new String[] { paramPmtInfo.getCustomerID() }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(19130);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation fails, customer info is null", 6);
      return false;
    }
    if (!localCustomerInfo.fIID.equals(paramPmtInfo.getFIID())) {
      try
      {
        BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramPmtInfo.getFIID());
        if (localBPWFIInfo.getStatusCode() != 0)
        {
          paramPmtInfo.setStatusCode(localBPWFIInfo.getStatusCode());
          paramPmtInfo.setStatusMsg(localBPWFIInfo.getStatusMsg());
          FFSDebug.log("Validation fails: unable to read bpw fi", 6);
          return false;
        }
      }
      catch (Exception localException)
      {
        throw new BPWException(localException.getMessage());
      }
    }
    if ((this.bH) && (localCustomerInfo.status.equalsIgnoreCase("INACTIVE")))
    {
      str1 = "This customer, customerID=" + paramPmtInfo.getCustomerID() + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
      FFSDebug.log(str1, 0);
      throw new BPWException(str1);
    }
    if (jdMethod_try(paramPmtInfo.getBankID()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "BankID" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: bank id is null", 6);
      return false;
    }
    if (jdMethod_try(paramPmtInfo.getAcctDebitID()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "AcctDebitID" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: acct debit id is null", 6);
      return false;
    }
    if (jdMethod_try(paramPmtInfo.getAcctDebitType()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "AcctDebitType" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: acct debit type is null", 6);
      return false;
    }
    if (jdMethod_try(paramPmtInfo.getLogID()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "LogID" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: log id is null", 6);
      return false;
    }
    if ((localPaymentType.isRecurring()) && ((paramPmtInfo instanceof RecPmtInfo)))
    {
      if (jdMethod_try(((RecPmtInfo)paramPmtInfo).getSubmittedBy()))
      {
        str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "SubmittedBy" }, "PAYMENT_MESSAGE");
        paramPmtInfo.setStatusCode(16010);
        paramPmtInfo.setStatusMsg(str1);
        FFSDebug.log("Validation failed: payment type is null", 6);
        return false;
      }
    }
    else if (jdMethod_try(paramPmtInfo.getSubmittedBy()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "submittedBy" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: submitted by is null", 6);
      return false;
    }
    if (!FFSUtil.isPositive(paramPmtInfo.getAmt()))
    {
      str1 = BPWLocaleUtil.getMessage(24720, null, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(24720);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: payment amount is not positive", 6);
      return false;
    }
    str1 = BPWLocaleUtil.getMessage(0, null, "PAYMENT_MESSAGE");
    paramPmtInfo.setStatusCode(0);
    paramPmtInfo.setStatusMsg(str1);
    FFSDebug.log("Payment validation succeeded", 6);
    return true;
  }
  
  private boolean jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    String str1 = null;
    PaymentType localPaymentType = PaymentType.getEnum(paramPmtInfo.getPaymentType());
    String str2 = localPaymentType.isRecurring() ? "RecPmtInfo" : "PmtInfo";
    if ((paramPmtInfo.getBatchID() == null) && (jdMethod_try(paramPmtInfo.getPaymentName())))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "PaymentName" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      FFSDebug.log("Validation failed: batch id and payment name both null", 6);
      return false;
    }
    if ((paramPmtInfo.getBatchID() != null) && (!jdMethod_try(paramPmtInfo.getTemplateID())))
    {
      str1 = BPWLocaleUtil.getMessage(26035, null, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(26035);
      paramPmtInfo.setStatusMsg(str1);
      return false;
    }
    if (jdMethod_try(paramPmtInfo.getPayeeID()))
    {
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { str2, "PayeeID" }, "PAYMENT_MESSAGE");
      paramPmtInfo.setStatusCode(16010);
      paramPmtInfo.setStatusMsg(str1);
      return false;
    }
    return jdMethod_byte(paramFFSConnectionHolder, paramPmtInfo);
  }
  
  private boolean a(PmtInfo paramPmtInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PaymentProcessor.configureImmediatePayment:", " Start ", 6);
    FulfillmentInfo localFulfillmentInfo = null;
    int i = paramPmtInfo.getStartDate();
    int j = 0;
    if (i % 100 != 0) {
      j = 1;
    }
    if (j != 0)
    {
      String str;
      if (paramPmtInfo.getImmediateFundAllocation() == null)
      {
        localFulfillmentInfo = Fulfillment.findByRouteID(paramPmtInfo.getRouteId(), paramFFSConnectionHolder);
        if (localFulfillmentInfo != null)
        {
          str = localFulfillmentInfo.getImmediateFundAllocation();
          if ((str != null) && (str.trim().length() > 0)) {
            if (str.equalsIgnoreCase("Y")) {
              paramPmtInfo.setImmediateFundAllocation(new Boolean(true));
            } else if (str.equalsIgnoreCase("N")) {
              paramPmtInfo.setImmediateFundAllocation(new Boolean(false));
            }
          }
        }
        if (paramPmtInfo.getImmediateFundAllocation() == null) {
          paramPmtInfo.setImmediateFundAllocation(new Boolean(this.bK));
        }
      }
      if (paramPmtInfo.getImmediateProcessing() == null)
      {
        if (localFulfillmentInfo == null)
        {
          str = paramPmtInfo.getPayeeID();
          int k = Payee.findRoute(str, paramFFSConnectionHolder);
          localFulfillmentInfo = Fulfillment.findByRouteID(k, paramFFSConnectionHolder);
        }
        if (localFulfillmentInfo != null)
        {
          str = localFulfillmentInfo.getImmediateProcessing();
          if ((str != null) && (str.trim().length() > 0)) {
            if (str.equalsIgnoreCase("Y")) {
              paramPmtInfo.setImmediateProcessing(new Boolean(true));
            } else if (str.equalsIgnoreCase("N")) {
              paramPmtInfo.setImmediateProcessing(new Boolean(false));
            }
          }
        }
        if (paramPmtInfo.getImmediateProcessing() == null) {
          paramPmtInfo.setImmediateProcessing(new Boolean(false));
        }
      }
    }
    else
    {
      paramPmtInfo.setImmediateFundAllocation(new Boolean(false));
      paramPmtInfo.setImmediateProcessing(new Boolean(false));
    }
    FFSDebug.log("PaymentProcessor.configureImmediatePayment:", " End ", 6);
    return false;
  }
  
  int a(PmtInfo paramPmtInfo, PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("PaymentProcessor.doImmediateFundsAllocation:", " Start ", 6);
    BankInfo localBankInfo = BPWRegistryUtil.getBankInfo(paramPmtInfo.getBankID());
    String str1 = null;
    if (localBankInfo == null)
    {
      if (this.bJ.equalsIgnoreCase("STRICT"))
      {
        localObject = "*** PaymentProcessor.doImmediateFundsAllocation: failed : could not find the BankID in BPW_Bank, bpw.pmt.bankinfo.lookup.level = STRICT ";
        FFSDebug.console((String)localObject);
        FFSDebug.log((String)localObject, 0);
        throw new Exception((String)localObject);
      }
      if (this.bJ.equalsIgnoreCase("LENIENT"))
      {
        localObject = "*** PaymentProcessor.doImmediateFundsAllocation: : bpw.pmt.bankinfo.lookup.level = LENIENT ";
        FFSDebug.log((String)localObject, 1);
      }
      else if (this.bJ.equalsIgnoreCase("NONE"))
      {
        localObject = "*** PaymentProcessor.doImmediateFundsAllocation: : bpw.pmt.bankinfo.lookup.level = NONE ";
        FFSDebug.log((String)localObject, 6);
      }
      else
      {
        localObject = "*** PaymentProcessor.doImmediateFundsAllocation: failed: Incorrect value for bpw.pmt.bankinfo.lookup.level =" + this.bJ;
        FFSDebug.console((String)localObject);
        FFSDebug.log((String)localObject);
        throw new Exception((String)localObject);
      }
      str1 = String.valueOf(-1);
    }
    else
    {
      str1 = localBankInfo.debitGLAcct;
    }
    Object localObject = new PmtInstruction(paramPmtInfo);
    String str2 = DBConnCache.save(paramFFSConnectionHolder);
    FundsAllocInfo localFundsAllocInfo = new FundsAllocInfo(paramPmtInfo.FIID, paramPmtInfo.CustomerID, paramPmtInfo.BankID, paramPmtInfo.AcctDebitID, paramPmtInfo.AcctDebitType, paramPmtInfo.getAmt(), paramPmtInfo.CurDef, paramPmtInfo.SrvrTID, paramPmtInfo.PayeeID, paramPayeeInfo.PayeeName, 1, false, str1, str2, paramPmtInfo.RecSrvrTID, paramPmtInfo);
    FundsAllocator localFundsAllocator = new FundsAllocator();
    int i = -1;
    String str3 = "WILLPROCESSON";
    try
    {
      i = localFundsAllocator.processFundsAllocation(localFundsAllocInfo);
      str3 = i == 0 ? "FUNDSALLOCATED" : "WILLPROCESSON";
      FundsAllocRslt localFundsAllocRslt = new FundsAllocRslt(paramPmtInfo.CustomerID, paramPmtInfo.SrvrTID, i, str2);
      if ((paramHashMap != null) && (paramHashMap.size() != 0)) {
        localFundsAllocInfo.extraFields = paramHashMap;
      }
      FundAllocRsltProcessor.ProcessOneFundAllocRsltImmediate(localFundsAllocRslt, (PmtInstruction)localObject, paramFFSConnectionHolder);
    }
    catch (Throwable localThrowable)
    {
      int j = 1;
      if ((str3.equals("FUNDSALLOCATED")) || (PmtInstruction.getStatus(paramPmtInfo.getSrvrTID(), paramFFSConnectionHolder).equals("FUNDSALLOCATED")))
      {
        paramPmtInfo.setStatus("INFUNDSREVERT");
        PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtInfo.getSrvrTID(), "INFUNDSREVERT");
        int k = FundsAllocProcessor.revertFundImmediate(paramPmtInfo, str2, false, this.bJ, paramFFSConnectionHolder);
        FFSDebug.log("doImmediateFundsAllocation Fund reverted for: " + paramPmtInfo.getSrvrTID(), 0);
        String str4 = PmtInstruction.getStatus(paramPmtInfo.getSrvrTID(), paramFFSConnectionHolder);
        if ((!str4.equalsIgnoreCase("FUNDSREVERTED")) && (!str4.equalsIgnoreCase("FUNDSREVERTFAILED")) && ((k == 0) || (k == 2000)))
        {
          BackendProcessor localBackendProcessor = new BackendProcessor();
          PmtTrnRslt localPmtTrnRslt = new PmtTrnRslt();
          localPmtTrnRslt.status = k;
          localPmtTrnRslt.srvrTid = paramPmtInfo.getSrvrTID();
          localPmtTrnRslt.customerID = paramPmtInfo.getCustomerID();
          localPmtTrnRslt.logID = paramPmtInfo.getLogID();
          localBackendProcessor.processOneFundsRevertRslt(localPmtTrnRslt, paramFFSConnectionHolder);
          j = 0;
        }
      }
      else
      {
        paramPmtInfo.setStatus("FUNDSALLOCACTIVE");
        PmtInstruction.updateStatus(paramFFSConnectionHolder, ((PmtInstruction)localObject).getSrvrTID(), "FUNDSALLOCACTIVE");
      }
      if (j != 0) {
        FundsAllocProcessor.revertLimit(paramFFSConnectionHolder, paramPmtInfo, 4451);
      }
      paramFFSConnectionHolder.conn.commit();
      throw new Exception("AddPayment Failed" + FFSDebug.stackTrace(localThrowable));
    }
    DBConnCache.unbind(str2);
    FFSDebug.log("PaymentProcessor.doImmediateFundsAllocation:", " End ", 6);
    return i;
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, Object paramObject, boolean paramBoolean)
    throws BPWException
  {
    boolean bool = true;
    FFSResultSet localFFSResultSet = null;
    String[] arrayOfString = { "SELECT RecSrvrTID FROM BPW_RecPmtInstruction where PaymentName = ?  AND SubmittedBy = ? AND Status != 'CANCELEDON'", "SELECT SrvrTID FROM BPW_PmtInstruction where PaymentName = ?  AND SubmittedBy = ? AND Status != 'CANCELEDON'", "SELECT BatchID FROM BPW_PaymentBatch where BatchName = ?  AND SubmittedBy = ? AND  BatchStatus != 'CANCELEDON'" };
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if ((paramObject instanceof PmtInfo))
    {
      str1 = ((PmtInfo)paramObject).getPaymentName();
      str2 = ((PmtInfo)paramObject).getSubmittedBy();
      str3 = ((PmtInfo)paramObject).getSrvrTID();
    }
    else if ((paramObject instanceof RecPmtInfo))
    {
      str1 = ((RecPmtInfo)paramObject).getPaymentName();
      str2 = ((RecPmtInfo)paramObject).getSubmittedBy();
      str3 = ((RecPmtInfo)paramObject).getRecSrvrTID();
    }
    else
    {
      str1 = ((PaymentBatchInfo)paramObject).getBatchName();
      str2 = ((PaymentBatchInfo)paramObject).getSubmittedBy();
      str3 = ((PaymentBatchInfo)paramObject).getBatchID();
    }
    Object[] arrayOfObject = { str1, str2 };
    try
    {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, arrayOfString[i], arrayOfObject);
        if (localFFSResultSet.getNextRow())
        {
          if ((str3 != null) && (str3.equals(localFFSResultSet.getColumnString(1))) && (paramBoolean))
          {
            bool = true;
            break;
          }
          bool = false;
          break;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PaymentProcessor.isNameUnique failed: " + localException1.getMessage());
      throw new BPWException(localException1.toString());
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
        FFSDebug.log("*** PaymentProcessor.isNameUnique failed:" + localException2.toString());
        throw new BPWException(localException2.toString());
      }
    }
    return bool;
  }
  
  private static boolean jdMethod_try(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
  
  public static void logTransAuditLogError(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
    throws BPWException
  {
    String str = "PmProcessor2.logTransAuditLogError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str + ":starts");
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str + ":Can not get DB Connection.");
      }
      TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramString1, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, 0);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + ":has failed" + FFSDebug.stackTrace(localException), 0);
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      throw new BPWException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable)
        {
          FFSDebug.log(str, " Failed to free the connection ", 0);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PaymentProcessor
 * JD-Core Version:    0.7.0.1
 */