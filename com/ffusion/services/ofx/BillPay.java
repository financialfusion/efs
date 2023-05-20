package com.ffusion.services.ofx;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.services.BillPay2;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class BillPay
  extends BillPayBase
  implements BillPay2, BillPayDefines
{
  private static String bQ = "DefaultDaysToPay";
  private static final int bN = 0;
  private static final int bO = 1;
  private static final int bP = 2;
  protected RecPayments recpayments;
  protected RecPayment recpayment;
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new BillPayXMLHandler());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLPAYSERVICE");
    XMLHandler.appendTag(localStringBuffer, bQ, this.defaultDaysToPay);
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
  
  protected boolean handleResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    this.objStatus.clear();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".handleResponse:");
    }
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (str.equals("BILLPAYMSGSRSV1")) {
          parseBILLPAYMSGSRSV1();
        } else if (!handleResponseNotHandled(str)) {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        bool = true;
      }
    }
    return bool;
  }
  
  private boolean e()
  {
    this.accounts = null;
    this.account = null;
    this.payees = null;
    this.payee = null;
    this.payments = null;
    this.payment = null;
    this.recpayments = null;
    this.recpayment = null;
    return checkStatus();
  }
  
  public int getPayees(Payees paramPayees)
  {
    boolean bool = false;
    this.payees = paramPayees;
    logStart(getClass().getName() + ".getPayees:");
    char[] arrayOfChar = sendRequest(formatGetPayeesRequest());
    bool = handleResponse(arrayOfChar);
    e();
    this.status = 0;
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int addPayees(Payees paramPayees)
  {
    boolean bool = false;
    this.payees = paramPayees;
    logStart(getClass().getName() + ".addPayees:");
    char[] arrayOfChar = sendRequest(formatAddPayeesRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    e();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int modifyPayees(Payees paramPayees)
  {
    boolean bool = false;
    this.payees = paramPayees;
    logStart(getClass().getName() + ".modifyPayees:");
    char[] arrayOfChar = sendRequest(formatModifyPayeesRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    e();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int deletePayees(Payees paramPayees)
  {
    boolean bool = false;
    this.payees = paramPayees;
    logStart(getClass().getName() + ".deletePayees:");
    char[] arrayOfChar = sendRequest(formatDeletePayeesRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    e();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return sendPayments(paramAccount, paramPayments, paramPayees);
  }
  
  public int sendPayments(Account paramAccount, Payments paramPayments, Payees paramPayees)
  {
    boolean bool = false;
    this.account = paramAccount;
    this.payees = paramPayees;
    this.payments = paramPayments;
    logStart(getClass().getName() + ".sendPayments:");
    char[] arrayOfChar = sendRequest(formatSendPaymentsRequest(0));
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!e()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment, HashMap paramHashMap)
  {
    return modifyPayment(paramAccount, paramPayment);
  }
  
  public int modifyPayment(Account paramAccount, Payment paramPayment)
  {
    boolean bool = false;
    this.account = paramAccount;
    this.payment = paramPayment;
    logStart(getClass().getName() + ".modifyPayment:");
    char[] arrayOfChar = sendRequest(formatModifyPaymentRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!e()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int cancelPayments(Payments paramPayments)
  {
    boolean bool = false;
    this.payments = paramPayments;
    logStart(getClass().getName() + ".cancelPayments:");
    char[] arrayOfChar = sendRequest(formatDeletePaymentsRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!e()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, RecPayments paramRecPayments, Payees paramPayees)
  {
    boolean bool = false;
    this.accounts = paramAccounts;
    this.payees = paramPayees;
    this.payments = paramPayments;
    this.recpayments = paramRecPayments;
    logStart(getClass().getName() + ".getPayments:");
    char[] arrayOfChar = sendRequest(formatGetPaymentsRequest());
    bool = handleResponse(arrayOfChar);
    this.status = 0;
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    e();
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getPayments(Accounts paramAccounts, Payments paramPayments, Payees paramPayees)
  {
    boolean bool = false;
    this.accounts = paramAccounts;
    this.payees = paramPayees;
    this.payments = paramPayments;
    logStart(getClass().getName() + ".getPayments:");
    char[] arrayOfChar = sendRequest(formatGetSinglePaymentsRequest());
    bool = handleResponse(arrayOfChar);
    this.status = 0;
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    e();
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getRecPayments(Accounts paramAccounts, RecPayments paramRecPayments, Payees paramPayees)
  {
    boolean bool = false;
    this.accounts = paramAccounts;
    this.payees = paramPayees;
    this.recpayments = paramRecPayments;
    logStart(getClass().getName() + ".getPayments:");
    char[] arrayOfChar = sendRequest(formatGetRecPaymentsRequest());
    bool = handleResponse(arrayOfChar);
    this.status = 0;
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    e();
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees, HashMap paramHashMap)
  {
    return sendRecPayments(paramAccount, paramRecPayments, paramPayees);
  }
  
  public int sendRecPayments(Account paramAccount, RecPayments paramRecPayments, Payees paramPayees)
  {
    boolean bool = false;
    this.account = paramAccount;
    this.payees = paramPayees;
    this.recpayments = paramRecPayments;
    logStart(getClass().getName() + ".sendRecPayments:");
    char[] arrayOfChar = sendRequest(formatSendRecPaymentsRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status == 0)) {
      this.status = returnStatus();
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!e()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int deleteRecPayment(RecPayment paramRecPayment)
  {
    boolean bool = false;
    this.recpayment = paramRecPayment;
    logStart(getClass().getName() + ".deleteRecPayment:");
    char[] arrayOfChar = sendRequest(formatDeleteRecPaymentRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!e()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment, HashMap paramHashMap)
  {
    return modifyRecPayment(paramAccount, paramRecPayment);
  }
  
  public int modifyRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    boolean bool = false;
    this.account = paramAccount;
    this.recpayment = paramRecPayment;
    logStart(getClass().getName() + ".modifyRecPayment:");
    char[] arrayOfChar = sendRequest(formatModifyRecPaymentRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!e()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int skipRecPayment(Account paramAccount, RecPayment paramRecPayment)
  {
    int i = 0;
    if (i != 0) {
      return 0;
    }
    return this.lastError;
  }
  
  protected char[] formatGetPayeesRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSignOnRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendBeginTag(localStringBuffer, "PAYEESYNCRQ");
    if ((this.m_PayeeToken != null) && (this.m_PayeeToken.length() > 0)) {
      appendTag(localStringBuffer, "TOKEN", this.m_PayeeToken);
    } else {
      appendTag(localStringBuffer, "REFRESH", "Y");
    }
    appendTag(localStringBuffer, "REJECTIFMISSING", "N");
    appendEndTag(localStringBuffer, "PAYEESYNCRQ");
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatAddPayeesRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatAddPayeesRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.set("TRNUID", getUniqueSeqNum());
      String str = localPayee.getUserAccountNumber();
      if ((str == null) || (str.length() == 0)) {
        str = "NA";
      }
      appendBeginTag(localStringBuffer, "PAYEETRNRQ");
      appendTag(localStringBuffer, "TRNUID", (String)localPayee.get("TRNUID"));
      appendBeginTag(localStringBuffer, "PAYEERQ");
      if ((localPayee.getHostID() == null) || (localPayee.getHostID().length() == 0)) {
        formatPAYEE(localStringBuffer, localPayee);
      } else {
        appendTag(localStringBuffer, "PAYEEID", localPayee.getHostID());
      }
      appendTag(localStringBuffer, "PAYACCT", str);
      appendEndTag(localStringBuffer, "PAYEERQ");
      appendEndTag(localStringBuffer, "PAYEETRNRQ");
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatModifyPayeesRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyPayeesRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.set("TRNUID", getUniqueSeqNum());
      String str = localPayee.getUserAccountNumber();
      if ((str == null) || (str.length() == 0)) {
        str = "NA";
      }
      appendBeginTag(localStringBuffer, "PAYEETRNRQ");
      appendTag(localStringBuffer, "TRNUID", (String)localPayee.get("TRNUID"));
      appendBeginTag(localStringBuffer, "PAYEEMODRQ");
      appendTag(localStringBuffer, "PAYEELSTID", localPayee.getID());
      formatPAYEE(localStringBuffer, localPayee);
      appendTag(localStringBuffer, "PAYACCT", str);
      appendEndTag(localStringBuffer, "PAYEEMODRQ");
      appendEndTag(localStringBuffer, "PAYEETRNRQ");
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatDeletePayeesRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeletePayeesRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    for (int i = 0; i < this.payees.size(); i++)
    {
      Payee localPayee = (Payee)this.payees.get(i);
      localPayee.set("TRNUID", getUniqueSeqNum());
      String str = localPayee.getUserAccountNumber();
      if ((str == null) || (str.length() == 0)) {
        str = "NA";
      }
      appendBeginTag(localStringBuffer, "PAYEETRNRQ");
      appendTag(localStringBuffer, "TRNUID", (String)localPayee.get("TRNUID"));
      appendBeginTag(localStringBuffer, "PAYEEDELRQ");
      appendTag(localStringBuffer, "PAYEELSTID", localPayee.getID());
      appendEndTag(localStringBuffer, "PAYEEDELRQ");
      appendEndTag(localStringBuffer, "PAYEETRNRQ");
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetPaymentsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    int i = this.accounts.size();
    for (int j = 0; j < i; j++)
    {
      Account localAccount = (Account)this.accounts.get(j);
      if (localAccount.isFilterable("BillPay"))
      {
        appendBeginTag(localStringBuffer, "RECPMTSYNCRQ");
        if ((this.m_RecPaymentToken != null) && (this.m_RecPaymentToken.length() > 0)) {
          appendTag(localStringBuffer, "TOKEN", this.m_RecPaymentToken);
        } else {
          appendTag(localStringBuffer, "TOKEN", "0");
        }
        appendTag(localStringBuffer, "REJECTIFMISSING", "N");
        formatFromAccount(localStringBuffer, localAccount);
        appendEndTag(localStringBuffer, "RECPMTSYNCRQ");
        appendBeginTag(localStringBuffer, "PMTSYNCRQ");
        if ((this.m_PaymentToken != null) && (this.m_PaymentToken.length() > 0)) {
          appendTag(localStringBuffer, "TOKEN", this.m_PaymentToken);
        } else {
          appendTag(localStringBuffer, "TOKEN", "0");
        }
        appendTag(localStringBuffer, "REJECTIFMISSING", "N");
        formatFromAccount(localStringBuffer, localAccount);
        appendEndTag(localStringBuffer, "PMTSYNCRQ");
      }
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetSinglePaymentsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetSinglePaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    int i = this.accounts.size();
    for (int j = 0; j < i; j++)
    {
      Account localAccount = (Account)this.accounts.get(j);
      if (localAccount.isFilterable("BillPay"))
      {
        appendBeginTag(localStringBuffer, "PMTSYNCRQ");
        if ((this.m_PaymentToken != null) && (this.m_PaymentToken.length() > 0)) {
          appendTag(localStringBuffer, "TOKEN", this.m_PaymentToken);
        } else {
          appendTag(localStringBuffer, "TOKEN", "0");
        }
        appendTag(localStringBuffer, "REJECTIFMISSING", "N");
        formatFromAccount(localStringBuffer, localAccount);
        appendEndTag(localStringBuffer, "PMTSYNCRQ");
      }
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetRecPaymentsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    int i = this.accounts.size();
    for (int j = 0; j < i; j++)
    {
      Account localAccount = (Account)this.accounts.get(j);
      if (localAccount.isFilterable("BillPay"))
      {
        appendBeginTag(localStringBuffer, "RECPMTSYNCRQ");
        if ((this.m_RecPaymentToken != null) && (this.m_RecPaymentToken.length() > 0)) {
          appendTag(localStringBuffer, "TOKEN", this.m_RecPaymentToken);
        } else {
          appendTag(localStringBuffer, "TOKEN", "0");
        }
        appendTag(localStringBuffer, "REJECTIFMISSING", "N");
        formatFromAccount(localStringBuffer, localAccount);
        appendEndTag(localStringBuffer, "RECPMTSYNCRQ");
      }
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSendPaymentsRequest(int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendPaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    for (int i = 0; i < this.payments.size(); i++)
    {
      Payment localPayment = (Payment)this.payments.get(i);
      localPayment.set("TRNUID", getUniqueSeqNum());
      appendBeginTag(localStringBuffer, "PMTTRNRQ");
      appendTag(localStringBuffer, "TRNUID", (String)localPayment.get("TRNUID"));
      appendBeginTag(localStringBuffer, "PMTRQ");
      formatPaymentRequest(localStringBuffer, this.account, localPayment, paramInt);
      appendEndTag(localStringBuffer, "PMTRQ");
      appendEndTag(localStringBuffer, "PMTTRNRQ");
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatDeletePaymentsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeletePaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    for (int i = 0; i < this.payments.size(); i++)
    {
      Payment localPayment = (Payment)this.payments.get(i);
      localPayment.set("TRNUID", getUniqueSeqNum());
      appendBeginTag(localStringBuffer, "PMTTRNRQ");
      appendTag(localStringBuffer, "TRNUID", (String)localPayment.get("TRNUID"));
      appendBeginTag(localStringBuffer, "PMTCANCRQ");
      appendTag(localStringBuffer, "SRVRTID", localPayment.getID());
      appendEndTag(localStringBuffer, "PMTCANCRQ");
      appendEndTag(localStringBuffer, "PMTTRNRQ");
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSendRecPaymentsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecPaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    for (int i = 0; i < this.recpayments.size(); i++)
    {
      RecPayment localRecPayment = (RecPayment)this.recpayments.get(i);
      localRecPayment.set("TRNUID", getUniqueSeqNum());
      appendBeginTag(localStringBuffer, "RECPMTTRNRQ");
      appendTag(localStringBuffer, "TRNUID", (String)localRecPayment.get("TRNUID"));
      appendBeginTag(localStringBuffer, "RECPMTRQ");
      formatRECURRINST(localStringBuffer, localRecPayment.getNumberPaymentsValue(), getFrequency(localRecPayment.getFrequencyValue()));
      formatPaymentRequest(localStringBuffer, this.account, localRecPayment, -1);
      appendEndTag(localStringBuffer, "RECPMTRQ");
      appendEndTag(localStringBuffer, "RECPMTTRNRQ");
    }
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatModifyRecPaymentRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyRecPaymentRequest:");
    }
    this.recpayment.set("TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendBeginTag(localStringBuffer, "RECPMTTRNRQ");
    appendTag(localStringBuffer, "TRNUID", (String)this.recpayment.get("TRNUID"));
    appendBeginTag(localStringBuffer, "RECPMTMODRQ");
    appendTag(localStringBuffer, "RECSRVRTID", this.recpayment.getID());
    formatRECURRINST(localStringBuffer, this.recpayment.getNumberPaymentsValue(), getFrequency(this.recpayment.getFrequencyValue()));
    formatPaymentRequest(localStringBuffer, this.account, this.recpayment, -1);
    appendTag(localStringBuffer, "MODPENDING", "Y");
    appendEndTag(localStringBuffer, "RECPMTMODRQ");
    appendEndTag(localStringBuffer, "RECPMTTRNRQ");
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatModifyPaymentRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyPaymentRequest:");
    }
    this.payment.set("TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendBeginTag(localStringBuffer, "PMTTRNRQ");
    appendTag(localStringBuffer, "TRNUID", (String)this.payment.get("TRNUID"));
    appendBeginTag(localStringBuffer, "PMTMODRQ");
    appendTag(localStringBuffer, "SRVRTID", this.payment.getID());
    formatPaymentRequest(localStringBuffer, this.account, this.payment, 0);
    appendEndTag(localStringBuffer, "PMTMODRQ");
    appendEndTag(localStringBuffer, "PMTTRNRQ");
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatDeleteRecPaymentRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeleteRecPaymentRequest:");
    }
    this.recpayment.set("TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendBeginTag(localStringBuffer, "RECPMTTRNRQ");
    appendTag(localStringBuffer, "TRNUID", (String)this.recpayment.get("TRNUID"));
    appendBeginTag(localStringBuffer, "RECPMTCANCRQ");
    appendTag(localStringBuffer, "RECSRVRTID", this.recpayment.getID());
    appendTag(localStringBuffer, "CANPENDING", "Y");
    appendEndTag(localStringBuffer, "RECPMTCANCRQ");
    appendEndTag(localStringBuffer, "RECPMTTRNRQ");
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  public void setRecPayment()
  {
    if (this.savedValues.get("RECSRVRTID") != null)
    {
      String str1 = (String)this.savedValues.get("TRNUID");
      if ((str1 != null) && (this.recpayments != null) && (!str1.equals("0"))) {
        this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("TRNUID=" + str1));
      }
      if ((this.recpayment == null) && (this.recpayments != null)) {
        this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("ID=" + (String)this.savedValues.get("RECSRVRTID")));
      }
      Object localObject;
      if ((this.recpayment == null) && (this.recpayments != null))
      {
        localObject = (String)this.savedValues.get("TRNAMT");
        if ((localObject != null) && (((String)localObject).length() != 0)) {
          this.recpayment = ((RecPayment)this.recpayments.create());
        }
      }
      if (this.savedValues.get("NINSTS") == null) {
        this.savedValues.put("NINSTS", "999");
      }
      if (this.recpayment != null)
      {
        localObject = this.savedValues.keySet().iterator();
        String str2;
        while (((Iterator)localObject).hasNext())
        {
          str2 = (String)((Iterator)localObject).next();
          setRecPayment(str2, (String)this.savedValues.get(str2));
        }
        if (this.savedValues.get("NINSTS") != null)
        {
          str2 = (String)this.savedValues.get("NINSTS");
          if (Integer.parseInt(str2) < 0) {
            this.recpayment.setStatus(3);
          }
        }
      }
    }
    this.savedValues.clear();
  }
  
  public void setRecPayment(String paramString1, String paramString2)
  {
    if (this.recpayment != null)
    {
      if (paramString1.equals("RECSRVRTID"))
      {
        this.recpayment.setID(paramString2);
        this.recpayment.setRecPaymentID(paramString2);
      }
      else if ((paramString1.equals("TRNAMT")) || (paramString1.equals("NAME")) || (paramString1.equals("DTDUE")) || (paramString1.equals("MEMO")) || (paramString1.equals("TRNUID")) || (paramString1.equals("STATUS")) || (paramString1.equals("PAYEELSTID")) || (paramString1.equals("ACCTTYPE")))
      {
        setPaymentCommon(this.recpayment, paramString1, paramString2);
      }
      else if (paramString1.equals("FREQ"))
      {
        this.recpayment.setFrequency(getFrequency(paramString2));
      }
      else if (paramString1.equals("NINSTS"))
      {
        this.recpayment.setNumberPayments(Integer.parseInt(paramString2));
      }
      else if (paramString1.equals("TOKEN"))
      {
        this.m_RecPaymentToken = paramString2;
      }
      else if (this.extendedInfo)
      {
        saveTagInExtendABean(paramString1, paramString2, this.recpayment);
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  public void parseBILLPAYMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BILLPAYMSGSRSV1"))) {
      if (str.equals("PAYEETRNRS")) {
        parsePAYEETRNRS();
      } else if (str.equals("PAYEESYNCRS")) {
        parsePAYEESYNCRS();
      } else if (str.equals("PMTTRNRS")) {
        parsePMTTRNRS();
      } else if (str.equals("RECPMTTRNRS")) {
        parseRECPMTTRNRS();
      } else if (str.equals("PMTINQTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("PMTMAILTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("PMTMAILSYNCRS")) {
        parseNotSupported(str);
      } else if (str.equals("PMTSYNCRS")) {
        parsePMTSYNCRS();
      } else if (str.equals("RECPMTSYNCRS")) {
        parseRECPMTSYNCRS();
      } else {
        throw new IllegalArgumentException("parseBILLPAYMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  public void parsePAYEESYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PAYEESYNCRS"))) {
      if (str.equals("PAYEETRNRS")) {
        parsePAYEETRNRS();
      } else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC"))) {
        skipField();
      } else {
        throw new IllegalArgumentException("parsePAYEESYNCRS failed on tag=" + str);
      }
    }
  }
  
  public void parsePAYEETRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PAYEETRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS(0);
      } else if ((str.equals("PAYEERS")) || (str.equals("PAYEEMODRS"))) {
        parsePAYEERS(str);
      } else if (str.equals("PAYEEDELRS")) {
        parsePAYEEDELRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setPayee(str, getField());
      } else {
        throw new IllegalArgumentException("parsePAYEETRNRS failed on tag=" + str);
      }
    }
  }
  
  public void parsePAYEERS(String paramString)
  {
    this.payee = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals(paramString))) {
      if (str.equals("PAYEE")) {
        parsePAYEE();
      } else if (str.equals("EXTDPAYEE")) {
        parseEXTDPAYEE();
      } else if (str.equals("BANKACCTTO")) {
        parseBANKACCTTO();
      } else if ((str.equals("PAYEELSTID")) || (str.equals("PAYACCT"))) {
        setPayee(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePAYEERS failed on tag=" + str);
      }
    }
    setPayee();
  }
  
  public void parsePAYEEDELRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PAYEEDELRS"))) {
      if (str.equals("PAYEELSTID"))
      {
        this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + getField()));
        if (this.payee != null) {
          this.payee.setStatus(3);
        }
      }
      else
      {
        throw new IllegalArgumentException("parsePAYEEDELRS failed on tag=" + str);
      }
    }
  }
  
  public void parsePMTSYNCRS()
  {
    this.payment = null;
    this.recpayment = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTSYNCRS"))) {
      if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
      }
      else if (str.equals("PMTTRNRS"))
      {
        parsePMTTRNRS();
      }
      else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC")))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parsePMTSYNCRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECPMTSYNCRS()
  {
    this.payment = null;
    this.recpayment = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECPMTSYNCRS"))) {
      if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
      }
      else if (str.equals("RECPMTTRNRS"))
      {
        parseRECPMTTRNRS();
      }
      else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC")))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parseRECPMTSYNCRS failed on tag=" + str);
      }
    }
  }
  
  public void parsePMTTRNRS()
  {
    this.payment = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS(1);
      } else if (str.equals("PMTRS")) {
        parsePMTRS();
      } else if (str.equals("PMTMODRS")) {
        parsePMTMODRS();
      } else if (str.equals("PMTCANCRS")) {
        parsePMTCANCRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setPayment(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTTRNRS failed on tag=" + str);
      }
    }
    setPayment();
  }
  
  public void parsePMTRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTRS"))) {
      if (str.equals("PMTINFO")) {
        parsePMTINFO();
      } else if (str.equals("EXTDPAYEE")) {
        parseEXTDPAYEE();
      } else if (str.equals("PMTPRCSTS")) {
        parsePMTPRCSTS();
      } else if ((str.equals("SRVRTID")) || (str.equals("PAYEELSTID")) || (str.equals("CURDEF")) || (str.equals("CHECKNUM")) || (str.equals("RECSRVRTID"))) {
        setPayment(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTRS failed on tag=" + str);
      }
    }
  }
  
  public void parsePMTMODRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTMODRS"))) {
      if (str.equals("PMTINFO")) {
        parsePMTINFO();
      } else if (str.equals("PMTPRCSTS")) {
        parsePMTPRCSTS();
      } else if (str.equals("SRVRTID")) {
        setPayment(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTMODRS failed on tag=" + str);
      }
    }
  }
  
  public void parsePMTPRCSTS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTPRCSTS"))) {
      if ((str.equals("PMTPRCCODE")) || (str.equals("DTPMTPRC"))) {
        setPayment(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTPRCSTS failed on tag=" + str);
      }
    }
  }
  
  public void parsePMTCANCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTCANCRS"))) {
      if (str.equals("SRVRTID"))
      {
        str = getField();
        if (this.payment == null) {
          this.payment = ((Payment)this.payments.getFirstByFilter("ID=" + str));
        }
        if (this.payment != null) {
          this.payment.setStatus(3);
        }
        this.savedValues.put("SRVRTID", str);
      }
      else
      {
        throw new IllegalArgumentException("parsePMTCANCRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECPMTTRNRS()
  {
    this.payment = null;
    if (this.recpayments != null) {
      this.recpayment = null;
    }
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECPMTTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS(2);
      } else if (str.equals("RECPMTRS")) {
        parseRECPMTRS();
      } else if (str.equals("RECPMTMODRS")) {
        parseRECPMTMODRS();
      } else if (str.equals("RECPMTCANCRS")) {
        parseRECPMTCANCRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setRecPayment(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECPMTTRNRS failed on tag=" + str);
      }
    }
    if (this.recpayments != null) {
      setRecPayment();
    }
  }
  
  public void parseRECPMTRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECPMTRS"))) {
      if (str.equals("PMTINFO")) {
        parsePMTINFO();
      } else if (str.equals("EXTDPAYEE")) {
        parseEXTDPAYEE();
      } else if (str.equals("RECURRINST")) {
        parseRECURRINST();
      } else if ((str.equals("RECSRVRTID")) || (str.equals("PAYEELSTID")) || (str.equals("CURDEF")) || (str.equals("INITIALAMT")) || (str.equals("FINALAMT"))) {
        setRecPayment(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECPMTRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECPMTMODRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECPMTMODRS"))) {
      if (str.equals("PMTINFO")) {
        parsePMTINFO();
      } else if (str.equals("RECURRINST")) {
        parseRECURRINST();
      } else if ((str.equals("RECSRVRTID")) || (str.equals("INITIALAMT")) || (str.equals("FINALAMT")) || (str.equals("MODPENDING"))) {
        setRecPayment(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECPMTMODRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECPMTCANCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECPMTCANCRS"))) {
      if (str.equals("RECSRVRTID"))
      {
        str = getField();
        if (this.recpayment == null) {
          this.recpayment = ((RecPayment)this.recpayments.getFirstByFilter("ID=" + str));
        }
        if (this.recpayment != null) {
          this.recpayment.setStatus(3);
        }
        this.savedValues.put("RECSRVRTID", str);
      }
      else if (str.equals("CANPENDING"))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parseRECPMTCANCRS failed on tag=" + str);
      }
    }
  }
  
  public void parseEXTDPAYEE()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("EXTDPAYEE"))) {
      if ((str.equals("PAYEEID")) || (str.equals("IDSCOPE")) || (str.equals("NAME")) || (str.equals("DAYSTOPAY"))) {
        setPayee(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseEXTDPAYEE failed on tag=" + str);
      }
    }
  }
  
  public void parseSTATUS(int paramInt)
  {
    super.parseSTATUS();
    switch (paramInt)
    {
    case 0: 
      setPayee("STATUS", String.valueOf(this.status));
      break;
    case 1: 
      setPayment("STATUS", String.valueOf(this.status));
      break;
    case 2: 
      setRecPayment("STATUS", String.valueOf(this.status));
    }
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
  
  public class BillPayXMLHandler
    extends Base.ServiceXMLHandler
  {
    public BillPayXMLHandler()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals(BillPay.bQ)) {
        BillPay.this.defaultDaysToPay = Integer.valueOf(paramString2).intValue();
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.BillPay
 * JD-Core Version:    0.7.0.1
 */