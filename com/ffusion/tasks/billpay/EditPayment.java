package com.ffusion.tasks.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.BankIdentifier;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditPayment
  extends RecPayment
  implements Task
{
  public static final String DUPLICATEPAYMENT = "DUPLICATEPAYMENT";
  public static final String TEMPLATENAME = "TEMPLATENAME";
  protected String dateWasChangedURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String minAmount = "1.00";
  protected String maxAmount = null;
  protected String paymentDate;
  protected int minPayments = 2;
  protected Payment originalPayment;
  protected Accounts accounts;
  protected Payment currentPayment;
  protected String payeeID;
  protected String paymentSessionName = "Payment";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    this.accounts = ((Accounts)localHttpSession.getAttribute("BillPayAccounts"));
    if (this.initFlag)
    {
      initProcess(localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      }
    }
    else
    {
      validateInput(localHttpSession);
      if (this.error == 0) {
        if (this.processFlag)
        {
          this.processFlag = false;
          str = doProcess(localHttpSession);
        }
        else
        {
          str = this.successURL;
        }
      }
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("PaymentsUpdated", "true");
    }
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    if (paramHttpSession.getAttribute(this.paymentSessionName) == null)
    {
      this.error = 2006;
    }
    else if ((paramHttpSession.getAttribute(this.paymentSessionName) instanceof RecPayment))
    {
      set((RecPayment)paramHttpSession.getAttribute(this.paymentSessionName));
      this.originalPayment = ((RecPayment)paramHttpSession.getAttribute(this.paymentSessionName));
    }
    else if ((paramHttpSession.getAttribute(this.paymentSessionName) instanceof Payment))
    {
      set((Payment)paramHttpSession.getAttribute(this.paymentSessionName));
      this.frequency = 0;
      this.originalPayment = ((Payment)paramHttpSession.getAttribute(this.paymentSessionName));
    }
    else
    {
      this.error = 2009;
    }
    if (this.payee != null)
    {
      this.payeeID = this.payee.getID();
      paramHttpSession.setAttribute("Payee", this.payee);
    }
    else
    {
      paramHttpSession.removeAttribute("Payee");
    }
    if (getAccount() != null) {
      paramHttpSession.setAttribute("Account", getAccount());
    } else {
      paramHttpSession.removeAttribute("Account");
    }
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    Object localObject = paramHttpSession.getAttribute(this.paymentSessionName);
    if ((localObject instanceof RecPayment))
    {
      editRecPayment(paramHttpSession, (RecPayment)localObject);
    }
    else if ((localObject instanceof Payment))
    {
      editPayment(paramHttpSession, (Payment)localObject);
    }
    else
    {
      this.error = 2006;
      return this.taskErrorURL;
    }
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  protected void editRecPayment(HttpSession paramHttpSession, RecPayment paramRecPayment)
  {
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    localHashMap = new HashMap();
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("ACCOUNT", getAccount());
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      String str = null;
      if (getAccount() != null) {
        str = getAccount().getCurrencyCode();
      }
      localHashMap.put("Curdef", str);
      Object localObject;
      if ((getPaymentType().equals("TEMPLATE")) || (getPaymentType().equals("RECTEMPLATE")))
      {
        localObject = Billpay.modifyPaymentTemplate(localSecureUser, this, (RecPayment)this.originalPayment, localHashMap);
        jdMethod_for((Payment)localObject, paramHttpSession);
        set((Payment)localObject);
      }
      else
      {
        localObject = Billpay.modifyRecPayment(localSecureUser, this, (RecPayment)this.originalPayment, localHashMap);
        jdMethod_for((Payment)localObject, paramHttpSession);
        set((RecPayment)localObject);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    if (this.error == 0) {
      paramRecPayment.set(this);
    }
  }
  
  protected void editPayment(HttpSession paramHttpSession, Payment paramPayment)
  {
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    localHashMap = new HashMap();
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("ACCOUNT", getAccount());
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str = null;
    if (getAccount() != null) {
      str = getAccount().getCurrencyCode();
    }
    localHashMap.put("Curdef", str);
    this.error = 0;
    try
    {
      Payment localPayment1 = null;
      if ((getPaymentType().equals("TEMPLATE")) || (getPaymentType().equals("RECTEMPLATE")))
      {
        Payments localPayments = new Payments(this.locale);
        Payment localPayment2 = (Payment)localPayments.create();
        localPayment2.set(this);
        localPayment1 = Billpay.modifyPaymentTemplate(localSecureUser, localPayment2, this.originalPayment, localHashMap);
      }
      else
      {
        localPayment1 = Billpay.modifyPayment(localSecureUser, this, this.originalPayment, localHashMap);
      }
      jdMethod_for(localPayment1, paramHttpSession);
      set(localPayment1);
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    if (this.error == 0) {
      paramPayment.set(this);
    }
  }
  
  private void jdMethod_for(Payment paramPayment, HttpSession paramHttpSession)
  {
    if (paramPayment.getPayee() == null)
    {
      Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
      if (localPayees != null)
      {
        this.payee = localPayees.getByID(this.payeeID);
        paramPayment.setPayee(this.payee);
      }
    }
  }
  
  protected void validateInput(HttpSession paramHttpSession)
  {
    if (this.validate != null)
    {
      if ((this.memo != null) && (this.memo.trim().length() > 64)) {
        this.error = 2032;
      }
      if (this.validate.indexOf("ACCOUNTID") != -1) {
        validateAccount(paramHttpSession);
      }
      if ((this.error == 0) && (this.validate.indexOf("AMOUNT") != -1)) {
        validateAmount(paramHttpSession);
      }
      if ((this.error == 0) && (this.validate.indexOf("PAYDATE") != -1)) {
        validateDate(paramHttpSession);
      }
      if ((this.error == 0) && (this.validate.indexOf("NUMBERPAYMENTS") != -1)) {
        validateNumberPayments(paramHttpSession);
      }
      if ((this.error == 0) && (this.validate.indexOf("DUPLICATEPAYMENT") != -1)) {
        validateDuplicatePayment(paramHttpSession);
      }
      if ((this.error == 0) && (this.validate.indexOf("TEMPLATENAME") != -1)) {
        validateTemplateName(paramHttpSession);
      }
      if ((this.error == 0) && (this.validate.indexOf("PAYEEID") != -1)) {
        validatePayee(paramHttpSession);
      }
      this.validate = null;
    }
  }
  
  protected void validateAccount(HttpSession paramHttpSession)
  {
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    if (localAccounts != null)
    {
      Account localAccount = localAccounts.getByID(this.accountID);
      if (localAccount != null)
      {
        setAccount(localAccount);
        paramHttpSession.setAttribute("Account", localAccount);
      }
      else
      {
        paramHttpSession.removeAttribute("Account");
        this.error = 2007;
      }
    }
    else
    {
      this.error = 2005;
    }
  }
  
  protected void validatePayee(HttpSession paramHttpSession)
  {
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    if (localPayees != null)
    {
      this.payee = localPayees.getByID(this.payeeID);
      if (this.payee != null)
      {
        setPayee(this.payee);
        paramHttpSession.setAttribute("Payee", this.payee);
      }
      else
      {
        this.error = 2023;
        paramHttpSession.removeAttribute("Payee");
      }
    }
    else
    {
      this.error = 2002;
    }
  }
  
  protected void validateAmount(HttpSession paramHttpSession)
  {
    String str = "USD";
    if (this.account != null) {
      str = this.account.getCurrencyCode();
    }
    if ((this.amount == null) || (this.amount.equals(0.0D)))
    {
      this.error = 2018;
      return;
    }
    Currency localCurrency1 = null;
    try
    {
      localCurrency1 = new Currency(this.minAmount, str, this.locale);
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      this.error = 2015;
      return;
    }
    if (this.amount.compareTo(localCurrency1) == -1)
    {
      this.error = 2019;
      return;
    }
    if (this.maxAmount != null)
    {
      Currency localCurrency2 = null;
      try
      {
        localCurrency2 = new Currency(this.maxAmount, str, this.locale);
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        this.error = 2016;
        return;
      }
      if (this.amount.compareTo(localCurrency2) == 1)
      {
        this.error = 2020;
        return;
      }
    }
    if (this.amount.getAmountValue().compareTo(Task.MAX_AMOUNT_ALLOWED) > 0)
    {
      this.error = 2020;
      return;
    }
    this.amount.setCurrencyCode(str);
  }
  
  protected void validateDate(HttpSession paramHttpSession)
  {
    if ((this.paymentDate == null) || (this.paymentDate.trim().length() == 0))
    {
      this.payDate = null;
      this.error = 2021;
    }
    else
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      DateTime localDateTime1 = new DateTime(localLocale);
      try
      {
        if (this.payDate == null) {
          this.payDate = new DateTime(this.paymentDate, localLocale, this.datetype);
        } else {
          this.payDate.fromString(this.paymentDate);
        }
        DateTime localDateTime2 = (DateTime)this.payDate.clone();
        localDateTime2.set(1, localDateTime1.get(1));
        localDateTime2.set(2, localDateTime1.get(2));
        localDateTime2.set(5, localDateTime1.get(5));
        if (this.payDate.before(localDateTime2))
        {
          this.payDate = localDateTime2;
          this.error = 2017;
        }
        try
        {
          BankIdentifier localBankIdentifier = new BankIdentifier(localLocale);
          SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          AffiliateBank localAffiliateBank = (AffiliateBank)paramHttpSession.getAttribute("AffiliateBank");
          if (localAffiliateBank != null) {
            localBankIdentifier.setBankDirectoryId(localAffiliateBank.getAffiliateRoutingNum());
          } else {
            try
            {
              localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), null);
              localBankIdentifier.setBankDirectoryId(localAffiliateBank.getAffiliateRoutingNum());
            }
            catch (Exception localException)
            {
              localBankIdentifier.setBankDirectoryId(this.account.getRoutingNum());
            }
          }
          Date localDate = SmartCalendar.getTransactionDay(localSecureUser, localBankIdentifier, this.payDate.getTime(), new HashMap());
          this.payDate = new DateTime(localDate, localLocale, this.datetype);
          int i = 0;
          if (this.payee == null)
          {
            localObject = (Payees)paramHttpSession.getAttribute("Payees");
            if (localObject != null)
            {
              this.payee = ((Payees)localObject).getByID(this.payeeID);
              if (this.payee != null) {
                setPayee(this.payee);
              }
            }
          }
          if (this.payee != null) {
            i = this.payee.getDaysToPayValue();
          }
          Object localObject = SmartCalendar.getOffsetBankingDay(localSecureUser, localBankIdentifier, this.payDate.getTime(), i, new HashMap());
          this.deliverByDate = new DateTime((Date)localObject, localLocale, this.datetype);
        }
        catch (CSILException localCSILException) {}
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.payDate = null;
        this.error = 2021;
      }
    }
  }
  
  protected void validateNumberPayments(HttpSession paramHttpSession)
  {
    if ((this.numberPayments > 1) && (this.frequency == 0)) {
      this.error = 2022;
    } else if (this.numberPayments < this.minPayments) {
      this.error = 2022;
    }
  }
  
  protected void validateDuplicatePayment(HttpSession paramHttpSession)
  {
    Payments localPayments = null;
    Iterator localIterator = null;
    if (getNumberPaymentsValue() > 1)
    {
      localPayments = (Payments)paramHttpSession.getAttribute("RecPayments");
      localIterator = localPayments.iterator();
    }
    else
    {
      localPayments = (Payments)paramHttpSession.getAttribute("Payments");
      localIterator = localPayments.iterator();
    }
    while ((localIterator != null) && (localIterator.hasNext()))
    {
      Payment localPayment = (Payment)localIterator.next();
      Double localDouble1 = new Double(getAmountValue().doubleValue());
      Double localDouble2 = new Double(localPayment.getAmountValue().doubleValue());
      if ((localDouble1.equals(localDouble2)) && (localPayment.compare(this, "PAYEEID") == 0) && (localPayment.getAccountID().equals(getAccountID())))
      {
        DateTime localDateTime1 = new DateTime(this.payDate, getLocale());
        DateTime localDateTime2 = new DateTime(localPayment.getPayDateValue(), getLocale());
        if ((localDateTime1.toString().equals(localDateTime2.toString())) && ((!(localPayment instanceof RecPayment)) || ((compare((RecPayment)localPayment, "NUMBERPAYMENTS") == 0) && (compare((RecPayment)localPayment, "FREQUENCY") == 0))) && (((this instanceof AddPayment)) || (localPayment.compare(this, "ID") != 0))) {
          this.error = 2029;
        }
      }
    }
  }
  
  protected void validateTemplateName(HttpSession paramHttpSession)
  {
    if ((this.templateName == null) || (this.templateName.length() < 1) || (this.templateName.length() > 45))
    {
      this.error = 2038;
    }
    else
    {
      ArrayList localArrayList = (ArrayList)paramHttpSession.getAttribute("PaymentTemplates");
      Object localObject1;
      Object localObject2;
      if ((localArrayList instanceof Payments))
      {
        localObject1 = (Payments)paramHttpSession.getAttribute("PaymentTemplates");
        localObject2 = (Payment)((Payments)localObject1).getFirstByFilter("TemplateName=" + this.templateName);
        if (localObject2 != null) {
          if ((this instanceof AddPayment)) {
            this.error = 2039;
          } else if ((this.originalPayment != null) && (!this.templateName.equals(this.originalPayment.getTemplateName()))) {
            this.error = 2039;
          }
        }
      }
      else
      {
        localObject1 = (FundsTransactions)paramHttpSession.getAttribute("PaymentTemplates");
        localObject2 = (FundsTransaction)((FundsTransactions)localObject1).getFirstByFilter("TemplateName=" + this.templateName);
        if (localObject2 != null) {
          if ((this instanceof AddPayment)) {
            this.error = 2039;
          } else if ((this.originalPayment != null) && (!this.templateName.equals(this.originalPayment.getTemplateName()))) {
            this.error = 2039;
          }
        }
      }
    }
  }
  
  public void setPayDate(String paramString)
  {
    this.paymentDate = paramString;
  }
  
  public String getPayDate()
  {
    if (this.payDate != null) {
      return this.payDate.toString();
    }
    return this.paymentDate;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
    if ((paramString != null) && (this.account != null) && (!paramString.equals(this.account.getID())) && (this.accounts != null)) {
      this.account = this.accounts.getByID(paramString);
    }
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public String getPayeeID()
  {
    return this.payeeID;
  }
  
  public void setMinimumAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public void setMaximumAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public void setMinPayments(String paramString)
  {
    try
    {
      setMinPayments(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setMinPayments(2);
    }
  }
  
  public void setMinPayments(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.minPayments = paramInt;
  }
  
  public void setDateWasChangedURL(String paramString)
  {
    this.dateWasChangedURL = paramString;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getProcess()
  {
    if (this.processFlag) {
      return "true";
    }
    return "false";
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!"".equals(paramString)) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setCurrentPayment(Payment paramPayment)
  {
    this.currentPayment = paramPayment;
  }
  
  public void setPaymentSessionName(String paramString)
  {
    this.paymentSessionName = paramString;
  }
  
  public String getPaymentSessionName()
  {
    return this.paymentSessionName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.EditPayment
 * JD-Core Version:    0.7.0.1
 */