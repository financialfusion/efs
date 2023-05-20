package com.ffusion.services.FFServer.OFX151;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.IOFX151Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillPayMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillPayMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillPayMsgsRqV1MessageUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillPayMsgsRqV1PayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeDelRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeDelRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtCancRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtCancRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtCancRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtCancRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.ValueSetCurrencyEnum;
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
  private static String bf = "DefaultDaysToPay";
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
    XMLHandler.appendTag(localStringBuffer, bf, this.defaultDaysToPay);
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
  
  private void jdMethod_else()
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
    jdMethod_else();
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
    jdMethod_else();
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
    jdMethod_else();
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
    jdMethod_else();
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
    jdMethod_else();
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
    jdMethod_else();
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
    jdMethod_else();
    this.status = returnStatus();
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
    jdMethod_else();
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
    jdMethod_else();
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
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".deleteRecPayment:");
    }
    this.recpayment = paramRecPayment;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".deleteRecPayment:");
    }
    formatDeleteRecPaymentRequest();
    processOFXRequest();
    jdMethod_else();
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
    jdMethod_else();
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
    localTypePayeeSyncRqV1Aggregate.SyncRqV1Cm = new TypeSyncRqV1Cm();
    localTypePayeeSyncRqV1Aggregate.SyncRqV1Cm.RejectIfMissing = false;
    localTypePayeeSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un = new TypeTokenRqV1Un();
    localTypePayeeSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un.__memberName = "Refresh";
    localTypePayeeSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un.Refresh = true;
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
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un = new TypePayeeTrnRqV1Un();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.__memberName = "PayeeRq";
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq = new TypePayeeRqV1Aggregate();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayAcct = new String[1];
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayAcct[0] = str;
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un = new TypePayeeRqV1Un();
    if ((paramPayee.getHostID() != null) && (paramPayee.getHostID().length() > 0))
    {
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.__memberName = "PayeeID";
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeID = paramPayee.getHostID();
    }
    else
    {
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.__memberName = "PayeeRqV1Cm";
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm = new TypePayeeRqV1Cm();
      paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee = new TypePayeeV1Aggregate();
      formatPAYEE(paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeRq.PayeeRqV1Un.PayeeRqV1Cm.Payee, paramPayee);
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
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un = new TypePayeeTrnRqV1Un();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.__memberName = "PayeeModRq";
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq = new TypePayeeModRqV1Aggregate();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayAcct = new String[1];
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayAcct[0] = str;
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayeeLstID = paramPayee.getID();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1CmExists = true;
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm = new TypePayeeModRqV1Cm();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.Payee = new TypePayeeV1Aggregate();
    formatPAYEE(paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeModRq.PayeeModRqV1Cm.Payee, paramPayee);
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
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un = new TypePayeeTrnRqV1Un();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.__memberName = "PayeeDelRq";
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeDelRq = new TypePayeeDelRqV1Aggregate();
    paramTypePayeeTrnRqV1Aggregate.PayeeTrnRqV1Un.PayeeDelRq.PayeeLstID = paramPayee.getID();
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
        localTypeRecPmtSyncRqV1Aggregate.SyncRqV1Cm = new TypeSyncRqV1Cm();
        localTypeRecPmtSyncRqV1Aggregate.SyncRqV1Cm.RejectIfMissing = false;
        localTypeRecPmtSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un = new TypeTokenRqV1Un();
        localTypeRecPmtSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un.__memberName = "Token";
        localTypeRecPmtSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un.Token = "0";
        localTypeRecPmtSyncRqV1Aggregate.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
        formatBANKACCTFROM(localTypeRecPmtSyncRqV1Aggregate.BankAcctFrom, localAccount);
        j++;
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j] = new TypeBillPayMsgsRqV1MessageUn();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].__memberName = "PmtSyncRq";
        TypePmtSyncRqV1Aggregate localTypePmtSyncRqV1Aggregate = new TypePmtSyncRqV1Aggregate();
        localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[j].PmtSyncRq = localTypePmtSyncRqV1Aggregate;
        localTypePmtSyncRqV1Aggregate.SyncRqV1Cm = new TypeSyncRqV1Cm();
        localTypePmtSyncRqV1Aggregate.SyncRqV1Cm.RejectIfMissing = false;
        localTypePmtSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un = new TypeTokenRqV1Un();
        localTypePmtSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un.__memberName = "Token";
        localTypePmtSyncRqV1Aggregate.SyncRqV1Cm.TokenRqV1Un.Token = "0";
        localTypePmtSyncRqV1Aggregate.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
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
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un = new TypePmtTrnRqV1Un();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.__memberName = "PmtRq";
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtRq = new TypePmtRqV1Aggregate();
      TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = new TypePmtInfoV1Aggregate();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtRq.PmtInfo = localTypePmtInfoV1Aggregate;
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
          localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtRq.CurDef = localEnumCurrencyEnum;
          localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtRq.CurDefExists = true;
        }
      }
      formatPmtInfo(localTypePmtInfoV1Aggregate, this.account, localPayment, paramInt);
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
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un = new TypePmtTrnRqV1Un();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.__memberName = "PmtCancRq";
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtCancRq = new TypePmtCancRqV1Aggregate();
      localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtCancRq.SrvrTID = localPayment.getID();
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
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un = new TypeRecPmtTrnRqV1Un();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.__memberName = "RecPmtRq";
      TypeRecPmtRqV1Aggregate localTypeRecPmtRqV1Aggregate = new TypeRecPmtRqV1Aggregate();
      localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.RecPmtRq = localTypeRecPmtRqV1Aggregate;
      localTypeRecPmtRqV1Aggregate.RecurrInst = formatRECURRINST(localRecPayment.getFrequencyValue(), localRecPayment.getNumberPaymentsValue());
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
          localTypeRecPmtRqV1Aggregate.CurDef = localEnumCurrencyEnum;
          localTypeRecPmtRqV1Aggregate.CurDefExists = true;
        }
      }
      localTypeRecPmtRqV1Aggregate.PmtInfo = new TypePmtInfoV1Aggregate();
      formatPmtInfo(localTypeRecPmtRqV1Aggregate.PmtInfo, this.account, localRecPayment, -1);
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
    localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un = new TypePmtTrnRqV1Un();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.__memberName = "PmtModRq";
    TypePmtModRqV1Aggregate localTypePmtModRqV1Aggregate = new TypePmtModRqV1Aggregate();
    localTypePmtTrnRqV1Aggregate.PmtTrnRqV1Un.PmtModRq = localTypePmtModRqV1Aggregate;
    localTypePmtModRqV1Aggregate.SrvrTID = this.payment.getID();
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
        localTypePmtModRqV1Aggregate.CurDef = localEnumCurrencyEnum;
        localTypePmtModRqV1Aggregate.CurDefExists = true;
      }
    }
    localTypePmtModRqV1Aggregate.PmtInfo = new TypePmtInfoV1Aggregate();
    formatPmtInfo(localTypePmtModRqV1Aggregate.PmtInfo, this.account, this.payment, -1);
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
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un = new TypeRecPmtTrnRqV1Un();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.__memberName = "RecPmtModRq";
    TypeRecPmtModRqV1Aggregate localTypeRecPmtModRqV1Aggregate = new TypeRecPmtModRqV1Aggregate();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.RecPmtModRq = localTypeRecPmtModRqV1Aggregate;
    localTypeRecPmtModRqV1Aggregate.RecSrvrTID = this.recpayment.getID();
    localTypeRecPmtModRqV1Aggregate.RecurrInst = formatRECURRINST(this.recpayment.getFrequencyValue(), this.recpayment.getNumberPaymentsValue());
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
        localTypeRecPmtModRqV1Aggregate.CurDef = localEnumCurrencyEnum;
        localTypeRecPmtModRqV1Aggregate.CurDefExists = true;
      }
    }
    localTypeRecPmtModRqV1Aggregate.PmtInfo = new TypePmtInfoV1Aggregate();
    formatPmtInfo(localTypeRecPmtModRqV1Aggregate.PmtInfo, this.account, this.recpayment, -1);
    localTypeRecPmtModRqV1Aggregate.ModPending = true;
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
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un = new TypeRecPmtTrnRqV1Un();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.__memberName = "RecPmtCancRq";
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.RecPmtCancRq = new TypeRecPmtCancRqV1Aggregate();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.RecPmtCancRq.RecSrvrTID = this.recpayment.getID();
    localTypeRecPmtTrnRqV1Aggregate.RecPmtTrnRqV1Un.RecPmtCancRq.CanPending = true;
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
  
  protected void processPmtInfo(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, Payment paramPayment)
  {
    String str1 = paramTypePmtInfoV1Aggregate.BankAcctFrom.AcctID;
    EnumAccountEnum localEnumAccountEnum = paramTypePmtInfoV1Aggregate.BankAcctFrom.AcctType;
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
    paramPayment.setPayDate(getDate(paramTypePmtInfoV1Aggregate.DtDue));
    if (paramTypePmtInfoV1Aggregate.MemoExists) {
      paramPayment.setMemo(paramTypePmtInfoV1Aggregate.Memo);
    }
    paramPayment.setAmount(new BigDecimal(paramTypePmtInfoV1Aggregate.TrnAmt));
    String str2 = null;
    String str3 = null;
    if (paramTypePmtInfoV1Aggregate.PayeeLstIDExists) {
      str2 = paramTypePmtInfoV1Aggregate.PayeeLstID;
    }
    if (paramTypePmtInfoV1Aggregate.PayeeUn != null) {
      if (paramTypePmtInfoV1Aggregate.PayeeUn.__memberName.equals("PayeeID")) {
        str3 = paramTypePmtInfoV1Aggregate.PayeeUn.PayeeID;
      } else if (paramTypePmtInfoV1Aggregate.PayeeUn.__memberName.equals("Payee")) {
        paramPayment.setPayeeName(paramTypePmtInfoV1Aggregate.PayeeUn.Payee.Name);
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
  
  protected void processPAYEE(TypePayeeV1Aggregate paramTypePayeeV1Aggregate, Payee paramPayee)
  {
    paramPayee.setName(paramTypePayeeV1Aggregate.Name);
    paramPayee.setStreet(paramTypePayeeV1Aggregate.AddressCm.Addr1);
    if (paramTypePayeeV1Aggregate.AddressCm.SubAddressCmExists) {
      paramPayee.setStreet2(paramTypePayeeV1Aggregate.AddressCm.SubAddressCm.Addr2);
    }
    paramPayee.setCity(paramTypePayeeV1Aggregate.City);
    if (paramTypePayeeV1Aggregate.CountryExists) {
      paramPayee.setCountry(paramTypePayeeV1Aggregate.Country);
    }
    paramPayee.setState(paramTypePayeeV1Aggregate.State);
    paramPayee.setZipCode(paramTypePayeeV1Aggregate.PostalCode);
    paramPayee.setPhone(paramTypePayeeV1Aggregate.Phone);
  }
  
  protected void processOFXRequest()
  {
    TypeOFXRequest localTypeOFXRequest = this.rqmes;
    this.objStatus.clear();
    getHandler();
    try
    {
      if (localTypeOFXRequest.OFXRequest.SignOnMsgsRqUn.__memberName.equals("SignOnMsgsRqV1")) {
        processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqUn.SignOnMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.BillPayMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.BillPayMsgsRqUn.__memberName.equals("BillPayMsgsRqV1"))) {
        processTransactionsInBillPayMsgsRqV1(localTypeOFXRequest.OFXRequest.BillPayMsgsRqUn.BillPayMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.SignUpMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.SignUpMsgsRqUn.__memberName.equals("SignUpMsgsRqV1"))) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.BankMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.BankMsgsRqUn.__memberName.equals("BankMsgsRqV1"))) {
        processTransactionsInBankMsgsRqV1(localTypeOFXRequest.OFXRequest.BankMsgsRqUn.BankMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.CreditCardMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.CreditCardMsgsRqUn.__memberName.equals("CreditCardMsgsRqV1"))) {
        processTransactionsInCreditCardMsgsRqV1(localTypeOFXRequest.OFXRequest.CreditCardMsgsRqUn.CreditCardMsgsRqV1);
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
    processSTATUS(paramTypePayeeTrnRsV1Aggregate.TrnRsV1Cm.Status);
    if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1UnExists)
    {
      Payee localPayee = null;
      String str1 = null;
      String str2 = "NA";
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = paramTypePayeeTrnRsV1Aggregate.TrnRsV1Cm.TrnUID;
      int i = -1;
      TypePayeeV1Aggregate localTypePayeeV1Aggregate = null;
      if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.__memberName.equals("PayeeRs"))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.PayeeLstID;
        if ((paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.PayAcct != null) && (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.PayAcct.length > 0)) {
          str2 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.PayAcct[0];
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayeeExists)
        {
          i = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.DaysToPay;
          if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1CmExists)
          {
            str4 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.Name;
            str3 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID;
            if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.IDScope.value() == 0) {
              str5 = "GLOBAL";
            } else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.ExtdPayee.ExtdPayeeV1Cm.IDScope.value() == 1) {
              str5 = "USER";
            }
          }
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1CmExists) {
          localTypePayeeV1Aggregate = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeRs.PayeeRsV1Cm.Payee;
        }
      }
      else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.__memberName.equals("PayeeModRs"))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.PayeeLstID;
        if ((paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.PayAcct != null) && (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.PayAcct.length > 0)) {
          str2 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.PayAcct[0];
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.ExtdPayeeExists) {
          i = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.ExtdPayee.DaysToPay;
        }
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1CmExists) {
          localTypePayeeV1Aggregate = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeModRs.PayeeModRsV1Cm.Payee;
        }
      }
      else if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.__memberName.equals("PayeeDelRs"))
      {
        str1 = paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.PayeeDelRs.PayeeLstID;
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
        if (paramTypePayeeTrnRsV1Aggregate.PayeeTrnRsV1Un.__memberName.equals("PayeeDelRs"))
        {
          localPayee.setStatus(3);
        }
        else
        {
          if (localTypePayeeV1Aggregate != null) {
            processPAYEE(localTypePayeeV1Aggregate, localPayee);
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
    processSTATUS(paramTypePmtTrnRsV1Aggregate.TrnRsV1Cm.Status);
    TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = null;
    String str1 = null;
    TypePmtPrcStsAggregate localTypePmtPrcStsAggregate = null;
    String str2 = paramTypePmtTrnRsV1Aggregate.TrnRsV1Cm.TrnUID;
    String str3 = null;
    if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists)
    {
      if (paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName.equals("PmtRs"))
      {
        localTypePmtInfoV1Aggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo;
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
        localTypePmtInfoV1Aggregate = paramTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtInfo;
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
        if (localTypePmtInfoV1Aggregate != null) {
          processPmtInfo(localTypePmtInfoV1Aggregate, this.payment);
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
    processSTATUS(paramTypeRecPmtTrnRsV1Aggregate.TrnRsV1Cm.Status);
    String str1 = null;
    String str2 = paramTypeRecPmtTrnRsV1Aggregate.TrnRsV1Cm.TrnUID;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = null;
    TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = null;
    String str3 = null;
    String str4 = null;
    if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1UnExists)
    {
      if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName.equals("RecPmtRs"))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst;
        localTypePmtInfoV1Aggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo;
        if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        str3 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.PayeeLstID;
      }
      else if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName.equals("RecPmtModRs"))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.RecurrInst;
        localTypePmtInfoV1Aggregate = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.PmtInfo;
        if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
      }
      else if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName.equals("RecPmtCancRs"))
      {
        str1 = paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtCancRs.RecSrvrTID;
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
      if (paramTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName.equals("RecPmtCancRs"))
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
        if (localTypePmtInfoV1Aggregate != null) {
          processPmtInfo(localTypePmtInfoV1Aggregate, this.recpayment);
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
      if (paramString1.equals(BillPay.bf)) {
        BillPay.this.defaultDaysToPay = Integer.valueOf(paramString2).intValue();
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX151.BillPay
 * JD-Core Version:    0.7.0.1
 */