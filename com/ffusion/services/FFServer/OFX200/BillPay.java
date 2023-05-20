package com.ffusion.services.FFServer.OFX200;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.IOFX200Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1MessageUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1PayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeDelRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeDelRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetCurrencyEnum;
import com.ffusion.services.BillPay2;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;

public class BillPay
  extends BillPayBase
  implements BillPay2, BillPayDefines
{
  private static String bH = "DefaultDaysToPay";
  protected RecPayments recpayments;
  protected RecPayment recpayment;
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new a());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLPAYSERVICE");
    XMLHandler.appendTag(localStringBuffer, bH, this.defaultDaysToPay);
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "BILLPAYSERVICE");
    return localStringBuffer.toString();
  }
  
  public void setUserName(String paramString)
  {
    setUserID(paramString);
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  private void jdMethod_void()
  {
    this.accounts = null;
    this.account = null;
    this.payees = null;
    this.payee = null;
    this.payments = null;
    this.payment = null;
    this.recpayments = null;
    this.recpayment = null;
  }
  
  public int getPayees(Payees paramPayees)
  {
    this.payees = paramPayees;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getPayees:");
    }
    formatGetPayeesRequest();
    processOFXRequest();
    jdMethod_void();
    this.status = 0;
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int addPayees(Payees paramPayees)
  {
    this.payees = paramPayees;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".addPayees:");
    }
    formatAddPayeesRequest();
    processOFXRequest();
    this.status = returnStatus();
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int modifyPayees(Payees paramPayees)
  {
    this.payees = paramPayees;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyPayees:");
    }
    formatModifyPayeesRequest();
    processOFXRequest();
    this.status = returnStatus();
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int deletePayees(Payees paramPayees)
  {
    this.payees = paramPayees;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".deletePayees:");
    }
    formatDeletePayeesRequest();
    processOFXRequest();
    this.status = returnStatus();
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return sendPayments(paramAccount, paramPayments, paramPayees);
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees)
  {
    this.account = paramAccount;
    this.payees = paramPayees;
    this.payments = paramPayments;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendPayments:");
    }
    formatSendPaymentsRequest(0);
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this.status = returnStatus();
    this._ud._privateTagContainer = null;
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment, HashMap paramHashMap)
  {
    return modifyPayment(paramAccount, paramPayment);
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment)
  {
    this.account = paramAccount;
    this.payment = paramPayment;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyPayment:");
    }
    formatModifyPaymentRequest();
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this.status = returnStatus();
    this._ud._privateTagContainer = null;
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int cancelPayments(Payments paramPayments)
  {
    this.payments = paramPayments;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".cancelPayments:");
    }
    formatDeletePaymentsRequest();
    processOFXRequest();
    this.status = returnStatus();
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees)
  {
    this.accounts = paramAccounts;
    this.payees = paramPayees;
    this.payments = paramPayments;
    this.recpayments = paramRecPayments;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getPayments:");
    }
    formatGetPaymentsRequest();
    processOFXRequest();
    this.status = 0;
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return sendRecPayments(paramAccount, paramRecPayments, paramPayees);
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees)
  {
    this.account = paramAccount;
    this.payees = paramPayees;
    this.recpayments = paramRecPayments;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendRecPayments:");
    }
    formatSendRecPaymentsRequest();
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this.status = returnStatus();
    this._ud._privateTagContainer = null;
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int deleteRecPayment(RecPayment paramRecPayment)
  {
    this.recpayment = paramRecPayment;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".deleteRecPayment:");
    }
    formatDeleteRecPaymentRequest();
    processOFXRequest();
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
  {
    return modifyRecPayment(paramAccount, paramRecPayment);
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    this.account = paramAccount;
    this.recpayment = paramRecPayment;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyRecPayment:");
    }
    formatModifyRecPaymentRequest();
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this._ud._privateTagContainer = null;
    jdMethod_void();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int skipRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    int i = 0;
    if (i != 0) {
      return 0;
    }
    return this.lastError;
  }
  
  protected void formatGetPayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[1];
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[0] = new TypeBillPayMsgsRqV1PayeeUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[0].__memberName = "PayeeSyncRq";
    TypePayeeSyncRqV1Aggregate localTypePayeeSyncRqV1Aggregate = new TypePayeeSyncRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[0].PayeeSyncRq = localTypePayeeSyncRqV1Aggregate;
    localTypePayeeSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
    localTypePayeeSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
    localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
    localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Refresh";
    localTypePayeeSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Refresh = true;
  }
  
  protected void formatPayeeRq(TypePayeeTrnRqV1Aggregate paramTypePayeeTrnRqV1Aggregate, Payee paramPayee)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatPayeeRq:");
    }
    String str = paramPayee.getUserAccountNumber();
    if ((str == null) || (str.length() == 0)) {
      str = "NA";
    }
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = new TypePayeeTrnRqUn();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.__memberName = "PayeeRq";
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq = new TypePayeeRqAggregate();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayAcct = new String[1];
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayAcct[0] = str;
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn = new TypePayeeRqUn();
    if ((paramPayee.getHostID() != null) && (paramPayee.getHostID().length() > 0))
    {
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.__memberName = "PayeeID";
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.PayeeID = paramPayee.getHostID();
    }
    else
    {
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.__memberName = "Payee";
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.Payee = new TypePayeeAggregate();
      formatPAYEE(paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeRq.PayeeRqUn.Payee, paramPayee);
    }
  }
  
  protected void formatAddPayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatAddPayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[this.payees.size()];
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.set("TRNUID", getUniqueSeqNum());
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i] = new TypeBillPayMsgsRqV1PayeeUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].__memberName = "PayeeTrnRq";
      TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].PayeeTrnRq = localTypePayeeTrnRqV1Aggregate;
      localTypePayeeTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePayeeTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localPayee.get("TRNUID"));
      formatPayeeRq(localTypePayeeTrnRqV1Aggregate, localPayee);
    }
  }
  
  protected void formatPayeeModRq(TypePayeeTrnRqV1Aggregate paramTypePayeeTrnRqV1Aggregate, Payee paramPayee)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatPayeeModRq:");
    }
    String str = paramPayee.getUserAccountNumber();
    if ((str == null) || (str.length() == 0)) {
      str = "NA";
    }
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = new TypePayeeTrnRqUn();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.__memberName = "PayeeModRq";
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq = new TypePayeeModRqAggregate();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayAcct = new String[1];
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayAcct[0] = str;
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeLstID = paramPayee.getID();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCmExists = true;
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm = new TypePayeeModRqCm();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm.Payee = new TypePayeeAggregate();
    formatPAYEE(paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeModRq.PayeeModRqCm.Payee, paramPayee);
  }
  
  protected void formatModifyPayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyPayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[this.payees.size()];
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.set("TRNUID", getUniqueSeqNum());
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i] = new TypeBillPayMsgsRqV1PayeeUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].__memberName = "PayeeTrnRq";
      TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].PayeeTrnRq = localTypePayeeTrnRqV1Aggregate;
      localTypePayeeTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePayeeTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localPayee.get("TRNUID"));
      formatPayeeModRq(localTypePayeeTrnRqV1Aggregate, localPayee);
    }
  }
  
  protected void formatPayeeDelRq(TypePayeeTrnRqV1Aggregate paramTypePayeeTrnRqV1Aggregate, Payee paramPayee)
  {
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn = new TypePayeeTrnRqUn();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.__memberName = "PayeeDelRq";
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeDelRq = new TypePayeeDelRqAggregate();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqUn.PayeeDelRq.PayeeLstID = paramPayee.getID();
  }
  
  protected void formatDeletePayeesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeletePayeesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn = new TypeBillPayMsgsRqV1PayeeUn[this.payees.size()];
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.set("TRNUID", getUniqueSeqNum());
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i] = new TypeBillPayMsgsRqV1PayeeUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].__memberName = "PayeeTrnRq";
      TypePayeeTrnRqV1Aggregate localTypePayeeTrnRqV1Aggregate = new TypePayeeTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[i].PayeeTrnRq = localTypePayeeTrnRqV1Aggregate;
      localTypePayeeTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePayeeTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localPayee.get("TRNUID"));
      formatPayeeDelRq(localTypePayeeTrnRqV1Aggregate, localPayee);
    }
  }
  
  protected void formatGetPaymentsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    int i = this.accounts.size();
    int j = 0;
    Account localAccount;
    for (int k = 0; k < i; k++)
    {
      localAccount = (Account)this.accounts.get(k);
      if (localAccount.isFilterable("BillPay")) {
        j++;
      }
    }
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[j * 2];
    j = 0;
    for (k = 0; k < i; k++)
    {
      localAccount = (Account)this.accounts.get(k);
      if (localAccount.isFilterable("BillPay"))
      {
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j] = new TypeBillPayMsgsRqV1MessageUn();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].__memberName = "RecPmtSyncRq";
        TypeRecPmtSyncRqV1Aggregate localTypeRecPmtSyncRqV1Aggregate = new TypeRecPmtSyncRqV1Aggregate();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].RecPmtSyncRq = localTypeRecPmtSyncRqV1Aggregate;
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Token";
        localTypeRecPmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = "0";
        localTypeRecPmtSyncRqV1Aggregate.BankAcctFrom = new TypeBankAcctFromAggregate();
        formatBANKACCTFROM(localTypeRecPmtSyncRqV1Aggregate.BankAcctFrom, localAccount);
        j++;
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j] = new TypeBillPayMsgsRqV1MessageUn();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].__memberName = "PmtSyncRq";
        TypePmtSyncRqV1Aggregate localTypePmtSyncRqV1Aggregate = new TypePmtSyncRqV1Aggregate();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].PmtSyncRq = localTypePmtSyncRqV1Aggregate;
        localTypePmtSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
        localTypePmtSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
        localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
        localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Token";
        localTypePmtSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Token = "0";
        localTypePmtSyncRqV1Aggregate.BankAcctFrom = new TypeBankAcctFromAggregate();
        formatBANKACCTFROM(localTypePmtSyncRqV1Aggregate.BankAcctFrom, localAccount);
        j++;
      }
    }
  }
  
  protected void formatSendPaymentsRequest(int paramInt)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendPaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[this.payments.size()];
    for (int i = 0; i < this.payments.size(); i++)
    {
      Payment localPayment = (Payment)this.payments.get(i);
      localPayment.set("TRNUID", getUniqueSeqNum());
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i] = new TypeBillPayMsgsRqV1MessageUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName = "PmtTrnRq";
      TypePmtTrnRqV1Aggregate localTypePmtTrnRqV1Aggregate = new TypePmtTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].PmtTrnRq = localTypePmtTrnRqV1Aggregate;
      localTypePmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localPayment.get("TRNUID"));
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn = new TypePmtTrnRqUn();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.__memberName = "PmtRq";
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq = new TypePmtRqAggregate();
      TypePmtInfoAggregate localTypePmtInfoAggregate = new TypePmtInfoAggregate();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq.PmtInfo = localTypePmtInfoAggregate;
      String str = localPayment.getAmtCurrency();
      if (str == null) {
        str = localPayment.getAccount().getCurrencyCode();
      }
      if (str != null)
      {
        EnumCurrencyEnum localEnumCurrencyEnum = null;
        try
        {
          int j = ValueSetCurrencyEnum.getIndex(str);
          localEnumCurrencyEnum = EnumCurrencyEnum.from_int(j);
        }
        catch (Throwable localThrowable) {}
        if (localEnumCurrencyEnum != null)
        {
          localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq.CurDef = localEnumCurrencyEnum;
          localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtRq.CurDefExists = true;
        }
      }
      formatPmtInfo(localTypePmtInfoAggregate, this.account, localPayment, paramInt);
    }
  }
  
  protected void formatDeletePaymentsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeletePaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[this.payments.size()];
    for (int i = 0; i < this.payments.size(); i++)
    {
      Payment localPayment = (Payment)this.payments.get(i);
      localPayment.set("TRNUID", getUniqueSeqNum());
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i] = new TypeBillPayMsgsRqV1MessageUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName = "PmtTrnRq";
      TypePmtTrnRqV1Aggregate localTypePmtTrnRqV1Aggregate = new TypePmtTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].PmtTrnRq = localTypePmtTrnRqV1Aggregate;
      localTypePmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypePmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localPayment.get("TRNUID"));
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn = new TypePmtTrnRqUn();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.__memberName = "PmtCancRq";
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtCancRq = new TypePmtCancRqAggregate();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtCancRq.SrvrTID = localPayment.getID();
    }
  }
  
  protected void formatSendRecPaymentsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecPaymentsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[this.recpayments.size()];
    for (int i = 0; i < this.recpayments.size(); i++)
    {
      RecPayment localRecPayment = (RecPayment)this.recpayments.get(i);
      localRecPayment.set("TRNUID", getUniqueSeqNum());
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i] = new TypeBillPayMsgsRqV1MessageUn();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName = "RecPmtTrnRq";
      TypeRecPmtTrnRqV1Aggregate localTypeRecPmtTrnRqV1Aggregate = new TypeRecPmtTrnRqV1Aggregate();
      localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].RecPmtTrnRq = localTypeRecPmtTrnRqV1Aggregate;
      localTypeRecPmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
      localTypeRecPmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localRecPayment.get("TRNUID"));
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.__memberName = "RecPmtRq";
      TypeRecPmtRqAggregate localTypeRecPmtRqAggregate = new TypeRecPmtRqAggregate();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtRq = localTypeRecPmtRqAggregate;
      localTypeRecPmtRqAggregate.RecurrInst = formatRECURRINST(localRecPayment.getFrequencyValue(), localRecPayment.getNumberPaymentsValue());
      String str = localRecPayment.getAmtCurrency();
      if (str == null) {
        str = localRecPayment.getAccount().getCurrencyCode();
      }
      if (str != null)
      {
        EnumCurrencyEnum localEnumCurrencyEnum = null;
        try
        {
          int j = ValueSetCurrencyEnum.getIndex(str);
          localEnumCurrencyEnum = EnumCurrencyEnum.from_int(j);
        }
        catch (Throwable localThrowable) {}
        if (localEnumCurrencyEnum != null)
        {
          localTypeRecPmtRqAggregate.CurDef = localEnumCurrencyEnum;
          localTypeRecPmtRqAggregate.CurDefExists = true;
        }
      }
      localTypeRecPmtRqAggregate.PmtInfo = new TypePmtInfoAggregate();
      formatPmtInfo(localTypeRecPmtRqAggregate.PmtInfo, this.account, localRecPayment, -1);
    }
  }
  
  protected void formatModifyPaymentRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyPaymentRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    this.payment.set("TRNUID", getUniqueSeqNum());
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "PmtTrnRq";
    TypePmtTrnRqV1Aggregate localTypePmtTrnRqV1Aggregate = new TypePmtTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].PmtTrnRq = localTypePmtTrnRqV1Aggregate;
    localTypePmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)this.payment.get("TRNUID"));
    localTypePmtTrnRqV1Aggregate.PmtTrnRqUn = new TypePmtTrnRqUn();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.__memberName = "PmtModRq";
    TypePmtModRqAggregate localTypePmtModRqAggregate = new TypePmtModRqAggregate();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqUn.PmtModRq = localTypePmtModRqAggregate;
    localTypePmtModRqAggregate.SrvrTID = this.payment.getID();
    String str = this.payment.getAmtCurrency();
    if (str == null) {
      str = this.payment.getAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypePmtModRqAggregate.CurDef = localEnumCurrencyEnum;
        localTypePmtModRqAggregate.CurDefExists = true;
      }
    }
    localTypePmtModRqAggregate.PmtInfo = new TypePmtInfoAggregate();
    formatPmtInfo(localTypePmtModRqAggregate.PmtInfo, this.account, this.payment, -1);
  }
  
  protected void formatModifyRecPaymentRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyRecPaymentRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    this.recpayment.set("TRNUID", getUniqueSeqNum());
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "RecPmtTrnRq";
    TypeRecPmtTrnRqV1Aggregate localTypeRecPmtTrnRqV1Aggregate = new TypeRecPmtTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].RecPmtTrnRq = localTypeRecPmtTrnRqV1Aggregate;
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)this.recpayment.get("TRNUID"));
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.__memberName = "RecPmtModRq";
    TypeRecPmtModRqAggregate localTypeRecPmtModRqAggregate = new TypeRecPmtModRqAggregate();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtModRq = localTypeRecPmtModRqAggregate;
    localTypeRecPmtModRqAggregate.RecSrvrTID = this.recpayment.getID();
    localTypeRecPmtModRqAggregate.RecurrInst = formatRECURRINST(this.recpayment.getFrequencyValue(), this.recpayment.getNumberPaymentsValue());
    String str = this.recpayment.getAmtCurrency();
    if (str == null) {
      str = this.recpayment.getAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypeRecPmtModRqAggregate.CurDef = localEnumCurrencyEnum;
        localTypeRecPmtModRqAggregate.CurDefExists = true;
      }
    }
    localTypeRecPmtModRqAggregate.PmtInfo = new TypePmtInfoAggregate();
    formatPmtInfo(localTypeRecPmtModRqAggregate.PmtInfo, this.account, this.recpayment, -1);
    localTypeRecPmtModRqAggregate.ModPending = true;
  }
  
  protected void formatDeleteRecPaymentRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetTransactionsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    this.recpayment.set("TRNUID", getUniqueSeqNum());
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "RecPmtTrnRq";
    TypeRecPmtTrnRqV1Aggregate localTypeRecPmtTrnRqV1Aggregate = new TypeRecPmtTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].RecPmtTrnRq = localTypeRecPmtTrnRqV1Aggregate;
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeRecPmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)this.recpayment.get("TRNUID"));
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn = new TypeRecPmtTrnRqUn();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.__memberName = "RecPmtCancRq";
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtCancRq = new TypeRecPmtCancRqAggregate();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtCancRq.RecSrvrTID = this.recpayment.getID();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqUn.RecPmtCancRq.CanPending = true;
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 2000: 
      i = 1;
      break;
    case 2001: 
      i = 2000;
      break;
    case 2002: 
      i = 2000;
      break;
    case 2003: 
      i = 2000;
      break;
    case 2004: 
      i = 2000;
      break;
    case 2005: 
      i = 2000;
      break;
    case 2006: 
      i = 2000;
      break;
    case 2007: 
      i = 2000;
      break;
    case 2008: 
      i = 2000;
      break;
    case 2009: 
      i = 2000;
      break;
    case 2010: 
      i = 2000;
      break;
    case 2011: 
      i = 2000;
      break;
    case 2012: 
      i = 2003;
      break;
    case 2014: 
      i = 2004;
      break;
    case 2015: 
      i = 2005;
      break;
    case 2016: 
      i = 2006;
      break;
    case 2017: 
      i = 2006;
      break;
    case 2018: 
      i = 3;
      break;
    case 2019: 
      i = 2006;
      break;
    case 10500: 
      i = 2007;
      break;
    case 10501: 
      i = 2008;
      break;
    case 10502: 
      i = 2009;
      break;
    case 10503: 
      i = 2008;
      break;
    case 10505: 
      i = 2;
      break;
    case 10508: 
      i = 2010;
      break;
    case 10510: 
      i = 2008;
      break;
    case 10511: 
      i = 2009;
      break;
    case 10512: 
      i = 2009;
      break;
    case 10513: 
      i = 2009;
      break;
    case 10514: 
      i = 2006;
      break;
    case 10515: 
      i = 2;
      break;
    case 10517: 
      i = 2008;
      break;
    case 10518: 
      i = 3;
      break;
    case 10519: 
      i = 2008;
      break;
    default: 
      i = super.mapError(paramInt);
    }
    return i;
  }
  
  protected void processPmtInfo(TypePmtInfoAggregate paramTypePmtInfoAggregate, Payment paramPayment)
  {
    String str1 = paramTypePmtInfoAggregate.BankAcctFrom.AcctID;
    EnumAccountEnum localEnumAccountEnum = paramTypePmtInfoAggregate.BankAcctFrom.AcctType;
    int i = getAccountType(localEnumAccountEnum);
    if (this.accounts != null)
    {
      this.account = this.accounts.getByAccountNumberAndType(str1, i);
      if (this.account != null) {
        paramPayment.setAccount(this.account);
      } else {
        paramPayment.setAccountID(str1);
      }
      this.account = null;
    }
    paramPayment.setPayDate(getDate(paramTypePmtInfoAggregate.DtDue));
    if (paramTypePmtInfoAggregate.MemoExists) {
      paramPayment.setMemo(paramTypePmtInfoAggregate.Memo);
    }
    paramPayment.setAmount(new BigDecimal(paramTypePmtInfoAggregate.TrnAmt));
    String str2 = null;
    String str3 = null;
    if (paramTypePmtInfoAggregate.PayeeLstIDExists) {
      str2 = paramTypePmtInfoAggregate.PayeeLstID;
    }
    if (paramTypePmtInfoAggregate.PayeeUn != null) {
      if (paramTypePmtInfoAggregate.PayeeUn.__memberName.equals("PayeeID")) {
        str3 = paramTypePmtInfoAggregate.PayeeUn.PayeeID;
      } else if (paramTypePmtInfoAggregate.PayeeUn.__memberName.equals("Payee")) {
        paramPayment.setPayeeName(paramTypePmtInfoAggregate.PayeeUn.Payee.Name);
      }
    }
    if (((str2 != null) || (str3 != null)) && (this.payees != null))
    {
      if (str3 != null) {
        this.payee = ((Payee)this.payees.getFirstByFilter("HOSTID=" + str3));
      } else {
        this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + str2));
      }
      paramPayment.setPayee(this.payee);
      this.payee = null;
    }
  }
  
  protected void processPAYEE(TypePayeeAggregate paramTypePayeeAggregate, Payee paramPayee)
  {
    paramPayee.setName(paramTypePayeeAggregate.Name);
    paramPayee.setStreet(paramTypePayeeAggregate.AddressCm.Addr1);
    if (paramTypePayeeAggregate.AddressCm.SubAddressCmExists)
    {
      paramPayee.setStreet2(paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr2);
      if (paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists) {
        paramPayee.setStreet3(paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3);
      }
    }
    paramPayee.setCity(paramTypePayeeAggregate.City);
    if (paramTypePayeeAggregate.CountryExists) {
      paramPayee.setCountry(paramTypePayeeAggregate.Country);
    }
    paramPayee.setState(paramTypePayeeAggregate.State);
    paramPayee.setZipCode(paramTypePayeeAggregate.PostalCode);
    paramPayee.setPhone(paramTypePayeeAggregate.Phone);
  }
  
  protected void processOFXRequest()
  {
    TypeOFXRequest localTypeOFXRequest = this.rqmes;
    this.objStatus.clear();
    getHandler();
    try
    {
      processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqV1);
      if (localTypeOFXRequest.OFXRequest.BillPayMsgsRqV1Exists) {
        processTransactionsInBillPayMsgsRqV1(localTypeOFXRequest.OFXRequest.BillPayMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1Exists) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.BankMsgsRqV1Exists) {
        processTransactionsInBankMsgsRqV1(localTypeOFXRequest.OFXRequest.BankMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.CreditCardMsgsRqV1Exists) {
        processTransactionsInCreditCardMsgsRqV1(localTypeOFXRequest.OFXRequest.CreditCardMsgsRqV1);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (this.signonProcessed == true) {
      processDisconnectRq(null);
    }
    removeHandler();
  }
  
  protected void processTransactionsInBillPayMsgsRqV1(TypeBillPayMsgsRqV1Aggregate paramTypeBillPayMsgsRqV1Aggregate)
  {
    int i = 0;
    int j = 0;
    if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn != null) {
      i = paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn.length;
    }
    if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn != null) {
      j = paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn.length;
    }
    Object localObject1;
    Object localObject2;
    for (int k = 0; k < i; k++)
    {
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].__memberName.equals("PayeeTrnRq"))
      {
        localObject1 = new TypePayeeTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].PayeeTrnRq);
        localObject2 = null;
        localObject2 = processPayeeTrnRqV1((TypePayeeTrnRqV1)localObject1);
        if (localObject2 != null) {
          processPayeeTrnRs(((TypePayeeTrnRsV1)localObject2).PayeeTrnRs);
        }
      }
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].__memberName.equals("PayeeSyncRq"))
      {
        localObject1 = new TypePayeeSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1PayeeUn[k].PayeeSyncRq);
        localObject2 = null;
        localObject2 = processPayeeSyncRqV1((TypePayeeSyncRqV1)localObject1);
        if (localObject2 != null) {
          processPayeeSyncRs(((TypePayeeSyncRsV1)localObject2).PayeeSyncRs);
        }
      }
    }
    for (k = 0; k < j; k++)
    {
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName.equals("PmtTrnRq"))
      {
        localObject1 = new TypePmtTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].PmtTrnRq);
        localObject2 = null;
        localObject2 = processPmtTrnRqV1((TypePmtTrnRqV1)localObject1);
        if (localObject2 != null) {
          processPmtTrnRs(((TypePmtTrnRsV1)localObject2).PmtTrnRs);
        }
      }
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName.equals("RecPmtTrnRq"))
      {
        localObject1 = new TypeRecPmtTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].RecPmtTrnRq);
        localObject2 = null;
        localObject2 = processRecPmtTrnRqV1((TypeRecPmtTrnRqV1)localObject1);
        if (localObject2 != null) {
          processRecPmtTrnRs(((TypeRecPmtTrnRsV1)localObject2).RecPmtTrnRs);
        }
      }
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName.equals("PmtSyncRq"))
      {
        localObject1 = new TypePmtSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].PmtSyncRq);
        localObject2 = null;
        localObject2 = processPmtSyncRqV1((TypePmtSyncRqV1)localObject1);
        if (localObject2 != null) {
          processPmtSyncRs(((TypePmtSyncRsV1)localObject2).PmtSyncRs);
        }
      }
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].__memberName.equals("RecPmtSyncRq"))
      {
        localObject1 = new TypeRecPmtSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[k].RecPmtSyncRq);
        localObject2 = null;
        localObject2 = processRecPmtSyncRqV1((TypeRecPmtSyncRqV1)localObject1);
        if (localObject2 != null) {
          processRecPmtSyncRs(((TypeRecPmtSyncRsV1)localObject2).RecPmtSyncRs);
        }
      }
    }
  }
  
  protected void processPayeeTrnRs(TypePayeeTrnRsV1Aggregate paramTypePayeeTrnRsV1Aggregate)
  {
    processSTATUS(paramTypePayeeTrnRsV1Aggregate.TrnRsCm.Status);
    if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUnExists)
    {
      Payee localPayee = null;
      String str1 = null;
      String str2 = "NA";
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = paramTypePayeeTrnRsV1Aggregate.TrnRsCm.TrnUID;
      int i = -1;
      TypePayeeAggregate localTypePayeeAggregate = null;
      if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName.equals("PayeeRs"))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayeeLstID;
        if ((paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayAcct != null) && (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayAcct.length > 0)) {
          str2 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayAcct[0];
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayeeExists)
        {
          i = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.DaysToPay;
          if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCmExists)
          {
            str4 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.Name;
            str3 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.PayeeID;
            if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.IDScope.value() == 0) {
              str5 = "GLOBAL";
            } else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.ExtdPayee.ExtdPayeeCm.IDScope.value() == 1) {
              str5 = "USER";
            }
          }
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayeeRsCmExists) {
          localTypePayeeAggregate = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeRs.PayeeRsCm.Payee;
        }
      }
      else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName.equals("PayeeModRs"))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayeeLstID;
        if ((paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayAcct != null) && (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayAcct.length > 0)) {
          str2 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayAcct[0];
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.ExtdPayeeExists) {
          i = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.ExtdPayee.DaysToPay;
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayeeModRsCmExists) {
          localTypePayeeAggregate = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeModRs.PayeeModRsCm.Payee;
        }
      }
      else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName.equals("PayeeDelRs"))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.PayeeDelRs.PayeeLstID;
      }
      if ((str1 != null) || (str6 != null))
      {
        if ((str6 != null) && (!str6.equals("0"))) {
          localPayee = (Payee)this.payees.getFirstByFilter("TRNUID=" + str6);
        }
        if (localPayee == null) {
          localPayee = (Payee)this.payees.getFirstByFilter("ID=" + str1);
        }
        if (localPayee == null) {
          localPayee = this.payees.create();
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsUn.__memberName.equals("PayeeDelRs"))
        {
          localPayee.setStatus(3);
        }
        else
        {
          if (localTypePayeeAggregate != null) {
            processPAYEE(localTypePayeeAggregate, localPayee);
          }
          if (str2 != null) {
            localPayee.setUserAccountNumber(str2);
          }
          if (i != -1) {
            localPayee.setDaysToPay(i);
          }
          if (str3 != null) {
            localPayee.setHostID(str3);
          }
          if (str4 != null) {
            localPayee.setName(str4);
          }
          if (str5 != null) {
            localPayee.set("IDScope", str5);
          }
          localPayee.setStatus(2);
        }
        localPayee.setID(str1);
        Integer localInteger = new Integer(this.status);
        this.objStatus.put(str1, localInteger.toString());
      }
    }
  }
  
  protected void processPayeeSyncRs(TypePayeeSyncRsV1Aggregate paramTypePayeeSyncRsV1Aggregate)
  {
    if ((paramTypePayeeSyncRsV1Aggregate != null) && (paramTypePayeeSyncRsV1Aggregate.PayeeTrnRs != null))
    {
      TypePayeeTrnRsV1Aggregate[] arrayOfTypePayeeTrnRsV1Aggregate = paramTypePayeeSyncRsV1Aggregate.PayeeTrnRs;
      for (int i = 0; i < arrayOfTypePayeeTrnRsV1Aggregate.length; i++) {
        processPayeeTrnRs(arrayOfTypePayeeTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processPmtTrnRs(TypePmtTrnRsV1Aggregate paramTypePmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypePmtTrnRsV1Aggregate.TrnRsCm.Status);
    TypePmtInfoAggregate localTypePmtInfoAggregate = null;
    String str1 = null;
    TypePmtPrcStsAggregate localTypePmtPrcStsAggregate = null;
    String str2 = paramTypePmtTrnRsV1Aggregate.TrnRsCm.TrnUID;
    String str3 = null;
    if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists)
    {
      if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName.equals("PmtRs"))
      {
        localTypePmtInfoAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo;
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        str1 = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID;
        localTypePmtPrcStsAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts;
      }
      else if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName.equals("PmtModRs"))
      {
        localTypePmtInfoAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtInfo;
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        str1 = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.SrvrTID;
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcStsExists) {
          localTypePmtPrcStsAggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcSts;
        }
      }
      else if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName.equals("PmtCancRs"))
      {
        str1 = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtCancRs.SrvrTID;
      }
      if (this.payments != null)
      {
        if ((str2 != null) && (!str2.equals("0"))) {
          this.payment = ((Payment)this.payments.getFirstByFilter("TRNUID=" + str2));
        }
        if ((this.payment == null) && (str1 != null)) {
          this.payment = ((Payment)this.payments.getFirstByFilter("ID=" + str1));
        }
        if (this.payment == null) {
          this.payment = ((Payment)this.payments.create());
        }
      }
      this.payment.setID(str1);
      Integer localInteger = new Integer(this.status);
      this.objStatus.put(str1, localInteger.toString());
      if (str3 != null) {
        this.payment.setAmtCurrency(str3);
      }
      if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName.equals("PmtCancRs"))
      {
        this.payment.setStatus(3);
        this.payment.setError(mapError(this.status));
      }
      else
      {
        if ((this.status == 0) && (this.payment.getStatus() == 1)) {
          this.payment.setStatus(2);
        }
        if (localTypePmtInfoAggregate != null) {
          processPmtInfo(localTypePmtInfoAggregate, this.payment);
        }
        if (localTypePmtPrcStsAggregate != null)
        {
          this.payment.set("PayProcessDate", DateFormatUtil.getFormatter("MM/dd/yyyy").format(getDate(localTypePmtPrcStsAggregate.DtPmtPrc).getTime()));
          if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 4) {
            this.payment.setStatus(2);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 3) {
            this.payment.setStatus(5);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 2) {
            this.payment.setStatus(6);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 1) {
            this.payment.setStatus(6);
          } else if (localTypePmtPrcStsAggregate.PmtPrcCode.value() == 0) {
            this.payment.setStatus(3);
          }
        }
        if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName.equals("PmtRs"))
        {
          if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists) {
            this.payment.setRecPaymentID(paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID);
          }
          if (this.payees != null)
          {
            this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID));
            if (this.payee != null) {
              this.payment.setPayee(this.payee);
            }
            this.payee = null;
          }
          if (this.payment.getReferenceNumber() == null) {
            this.payment.setReferenceNumber(str1);
          }
        }
        this.payment.setError(mapError(this.status));
        if (this.status == 2014) {
          this.payment.setStatus(8);
        }
      }
    }
  }
  
  protected void processPmtSyncRs(TypePmtSyncRsV1Aggregate paramTypePmtSyncRsV1Aggregate)
  {
    if ((paramTypePmtSyncRsV1Aggregate != null) && (paramTypePmtSyncRsV1Aggregate.PmtTrnRs != null))
    {
      TypePmtTrnRsV1Aggregate[] arrayOfTypePmtTrnRsV1Aggregate = paramTypePmtSyncRsV1Aggregate.PmtTrnRs;
      for (int i = 0; i < arrayOfTypePmtTrnRsV1Aggregate.length; i++)
      {
        this.payment = null;
        processPmtTrnRs(arrayOfTypePmtTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processRecPmtTrnRs(TypeRecPmtTrnRsV1Aggregate paramTypeRecPmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeRecPmtTrnRsV1Aggregate.TrnRsCm.Status);
    String str1 = null;
    String str2 = paramTypeRecPmtTrnRsV1Aggregate.TrnRsCm.TrnUID;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = null;
    TypePmtInfoAggregate localTypePmtInfoAggregate = null;
    String str3 = null;
    String str4 = null;
    if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists)
    {
      if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName.equals("RecPmtRs"))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.RecurrInst;
        localTypePmtInfoAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PmtInfo;
        if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        str3 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PayeeLstID;
      }
      else if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName.equals("RecPmtModRs"))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.RecurrInst;
        if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        localTypePmtInfoAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.PmtInfo;
      }
      else if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName.equals("RecPmtCancRs"))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtCancRs.RecSrvrTID;
      }
      if (this.recpayments != null)
      {
        if ((str2 != null) && (!str2.equals("0"))) {
          this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("TRNUID=" + str2));
        }
        if (this.recpayment == null) {
          this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("ID=" + str1));
        }
        if (this.recpayment == null) {
          this.recpayment = ((RecPayment)this.recpayments.create());
        }
      }
      this.recpayment.setID(str1);
      this.recpayment.setRecPaymentID(str1);
      Integer localInteger = new Integer(this.status);
      this.objStatus.put(str1, localInteger.toString());
      if (str4 != null) {
        this.recpayment.setAmtCurrency(str4);
      }
      if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName.equals("RecPmtCancRs"))
      {
        this.recpayment.setStatus(3);
      }
      else
      {
        if ((this.status == 0) && (this.recpayment.getStatus() == 1)) {
          this.recpayment.setStatus(2);
        }
        if (localTypeRecurrInstAggregate != null)
        {
          if (localTypeRecurrInstAggregate.NInstsExists)
          {
            this.recpayment.setNumberPayments(localTypeRecurrInstAggregate.NInsts);
            if (localTypeRecurrInstAggregate.NInsts < 0) {
              this.recpayment.setStatus(3);
            }
          }
          else
          {
            this.recpayment.setNumberPayments(999);
          }
          this.recpayment.setFrequency(getFrequency(localTypeRecurrInstAggregate.Freq));
        }
        if (localTypePmtInfoAggregate != null) {
          processPmtInfo(localTypePmtInfoAggregate, this.recpayment);
        }
        if ((this.payees != null) && (str3 != null))
        {
          this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + str3));
          if (this.payee != null) {
            this.recpayment.setPayee(this.payee);
          }
          this.payee = null;
        }
        if (this.recpayment.getReferenceNumber() == null) {
          this.recpayment.setReferenceNumber(str1);
        }
      }
    }
  }
  
  protected void processRecPmtSyncRs(TypeRecPmtSyncRsV1Aggregate paramTypeRecPmtSyncRsV1Aggregate)
  {
    if ((paramTypeRecPmtSyncRsV1Aggregate != null) && (paramTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs != null))
    {
      TypeRecPmtTrnRsV1Aggregate[] arrayOfTypeRecPmtTrnRsV1Aggregate = paramTypeRecPmtSyncRsV1Aggregate.RecPmtTrnRs;
      for (int i = 0; i < arrayOfTypeRecPmtTrnRsV1Aggregate.length; i++)
      {
        this.recpayment = null;
        processRecPmtTrnRs(arrayOfTypeRecPmtTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected TypePayeeTrnRsV1 processPayeeTrnRqV1(TypePayeeTrnRqV1 paramTypePayeeTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPayeeTrnRqV1(paramTypePayeeTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPayeeTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypePayeeSyncRsV1 processPayeeSyncRqV1(TypePayeeSyncRqV1 paramTypePayeeSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPayeeSyncRqV1(paramTypePayeeSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPayeeSyncRqV1", localException);
    }
    return null;
  }
  
  protected TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPmtTrnRqV1(paramTypePmtTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPmtTrnRqV1(paramTypePmtTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPmtTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processRecPmtTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPmtSyncRqV1(paramTypePmtSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPmtSyncRqV1(paramTypePmtSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPmtSyncRqV1", localException);
    }
    return null;
  }
  
  protected TypeRecPmtSyncRsV1 processRecPmtSyncRqV1(TypeRecPmtSyncRqV1 paramTypeRecPmtSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processRecPmtSyncRqV1(paramTypeRecPmtSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processRecPmtSyncRqV1", localException);
    }
    return null;
  }
  
  public class a
    extends Base.a
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals(BillPay.bH)) {
        BillPay.this.defaultDaysToPay = Integer.valueOf(paramString2).intValue();
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.BillPay
 * JD-Core Version:    0.7.0.1
 */