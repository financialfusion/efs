package com.ffusion.tasks.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditPaymentBatch
  extends PaymentBatch
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  private boolean currentlyProcessing = false;
  protected String serviceName = "com.ffusion.services.BillPay";
  protected PaymentBatch originalPaymentBatch;
  protected boolean accountChanged = false;
  protected String minAmount = "1.00";
  protected String maxAmount = null;
  protected String accountID;
  protected Account account;
  protected Payment currentPayment;
  
  public EditPaymentBatch()
  {
    this.error = 0;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      initProcess(localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = validateInput(localHttpSession);
      if (this.accountChanged) {
        this.error = accountChangedPayments(localHttpSession);
      }
      if (this.error == 0)
      {
        if (this.processFlag)
        {
          if (this.error == 0)
          {
            this.processFlag = false;
            if (!this.currentlyProcessing)
            {
              this.currentlyProcessing = true;
              removePayments();
              this.error = doProcess(localHttpSession);
              if (this.error == 0) {
                str = this.successURL;
              } else {
                str = this.serviceErrorURL;
              }
              this.currentlyProcessing = false;
            }
            else
            {
              this.error = 2030;
              str = this.taskErrorURL;
            }
          }
          else
          {
            str = this.taskErrorURL;
          }
        }
        else {
          str = this.successURL;
        }
      }
      else {
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    PaymentBatch localPaymentBatch = (PaymentBatch)paramHttpSession.getAttribute("PaymentBatch");
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    if (localPaymentBatch == null)
    {
      this.error = 2003;
    }
    else
    {
      String str = getDateFormat();
      set(localPaymentBatch);
      setDateFormat(str);
      Payments localPayments = getPayments();
      Iterator localIterator = localPayees.iterator();
      int i = 1;
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      int j = 0;
      if (localSecureUser.getBusinessID() == 0) {
        j = localSecureUser.getPrimaryUserID();
      } else {
        j = localSecureUser.getBusinessID();
      }
      Object localObject;
      while (localIterator.hasNext())
      {
        localObject = (Payee)localIterator.next();
        if (localPayments.getFirstByFilter("PAYEEID=" + ((Payee)localObject).getID()) == null)
        {
          Payment localPayment = (Payment)localPayments.create();
          localPayment.setPaymentType("TEMPLATE");
          localPayment.setPayee((Payee)localObject);
          localPayment.setPayeeName(((Payee)localObject).getName());
          localPayment.setID(String.valueOf(i++));
          localPayment.setDateFormat(getDateFormat());
          localPayment.setCustomerID(j);
          localPayment.setStatus(1);
        }
      }
      this.originalPaymentBatch = localPaymentBatch;
      localIterator = getPayments().iterator();
      while (localIterator.hasNext())
      {
        localObject = (Payment)localIterator.next();
        if (((Payment)localObject).getAccount() != null)
        {
          this.account = ((Payment)localObject).getAccount();
          this.accountID = ((Payment)localObject).getAccountID();
          break;
        }
      }
    }
  }
  
  protected int validateInput(HttpSession paramHttpSession)
  {
    int i = 0;
    if (this.validate != null)
    {
      if (this.validate.indexOf("TEMPLATENAME") != -1) {
        i = validateTemplateName(paramHttpSession);
      }
      if (i == 0)
      {
        int j = 0;
        Iterator localIterator = getPayments().iterator();
        while (localIterator.hasNext())
        {
          Payment localPayment = (Payment)localIterator.next();
          localPayment.resetValidationErrors();
          if ((localPayment.getAmount() == null) || (localPayment.getAmount().length() == 0) || (localPayment.getAmountValue().doubleValue() == 0.0D))
          {
            j++;
          }
          else
          {
            if ((localPayment.getMemo() != null) && (localPayment.getMemo().trim().length() > 64)) {
              i = 2032;
            }
            if ((i == 0) && (this.validate.indexOf("AMOUNT") != -1)) {
              i = validateAmount(localPayment.getAmountValue());
            }
            if ((i == 0) && (this.validate.indexOf("PAYDATE") != -1))
            {
              i = validateDate(localPayment.getPayDateValue());
              if (i == 0) {
                try
                {
                  SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
                  BankIdentifier localBankIdentifier = new BankIdentifier(this.locale);
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
                  Date localDate1 = SmartCalendar.getTransactionDay(localSecureUser, localBankIdentifier, localPayment.getPayDateValue().getTime(), new HashMap());
                  Calendar localCalendar = Calendar.getInstance();
                  localCalendar.setTime(localDate1);
                  localPayment.setPayDate(localCalendar);
                  Date localDate2 = SmartCalendar.getOffsetBankingDay(localSecureUser, localBankIdentifier, localPayment.getPayDateValue().getTime(), localPayment.getPayee().getDaysToPayValue(), new HashMap());
                  localCalendar.setTime(localDate2);
                  localPayment.setDeliverByDate(localCalendar);
                }
                catch (CSILException localCSILException) {}
              }
            }
            if (i != 0) {
              break;
            }
          }
        }
        if ((i == 0) && (j == this.payments.size())) {
          i = 2006;
        }
      }
    }
    this.validate = null;
    return i;
  }
  
  protected int validateAmount(Currency paramCurrency)
  {
    if ((paramCurrency == null) || (paramCurrency.equals(0.0D))) {
      return 2018;
    }
    Currency localCurrency1 = null;
    try
    {
      localCurrency1 = new Currency(this.minAmount, this.locale);
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      return 2015;
    }
    if (paramCurrency.compareTo(localCurrency1) == -1) {
      return 2019;
    }
    if (this.maxAmount != null)
    {
      Currency localCurrency2 = null;
      try
      {
        localCurrency2 = new Currency(this.maxAmount, this.locale);
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        return 2016;
      }
      if (paramCurrency.compareTo(localCurrency2) == 1) {
        return 2020;
      }
    }
    if (paramCurrency.getAmountValue().compareTo(Task.MAX_AMOUNT_ALLOWED) > 0) {
      return 2020;
    }
    return 0;
  }
  
  protected static int validateDate(Calendar paramCalendar)
  {
    int i = 0;
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    if (paramCalendar == null)
    {
      paramCalendar = localGregorianCalendar1;
      i = 2021;
    }
    else
    {
      GregorianCalendar localGregorianCalendar2 = (GregorianCalendar)paramCalendar.clone();
      localGregorianCalendar2.set(1, localGregorianCalendar1.get(1));
      localGregorianCalendar2.set(2, localGregorianCalendar1.get(2));
      localGregorianCalendar2.set(5, localGregorianCalendar1.get(5));
      if (paramCalendar.before(localGregorianCalendar2))
      {
        paramCalendar = localGregorianCalendar2;
        i = 2017;
      }
    }
    return i;
  }
  
  protected int validateTemplateName(HttpSession paramHttpSession)
  {
    if ((this.templateName == null) || (this.templateName.length() < 1) || (this.templateName.length() > 45)) {
      return 2038;
    }
    FundsTransactions localFundsTransactions = (FundsTransactions)paramHttpSession.getAttribute("PaymentTemplates");
    FundsTransaction localFundsTransaction = (FundsTransaction)localFundsTransactions.getFirstByFilter("TemplateName=" + this.templateName);
    if (localFundsTransaction != null)
    {
      if ((this instanceof AddPaymentBatch)) {
        return 2039;
      }
      if ((this.originalPaymentBatch != null) && (!this.templateName.equals(this.originalPaymentBatch.getTemplateName()))) {
        return 2039;
      }
    }
    return 0;
  }
  
  protected void removePayments()
  {
    Currency localCurrency1 = new Currency("0.00", this.locale);
    Payments localPayments = getPayments();
    if ((localPayments != null) && (localPayments.size() > 0)) {
      for (int i = getPayments().size() - 1; i >= 0; i--)
      {
        Payment localPayment = (Payment)localPayments.get(i);
        if (localPayment.getStatus() == 1)
        {
          Currency localCurrency2 = localPayment.getAmountValue();
          if ((localCurrency2 == null) || (localCurrency2.compareTo(localCurrency1) == 0)) {
            localPayments.remove(i);
          }
        }
      }
    }
  }
  
  protected int accountChangedPayments(HttpSession paramHttpSession)
  {
    int i = 0;
    this.accountChanged = false;
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    if (localAccounts != null)
    {
      this.account = localAccounts.getByID(this.accountID);
    }
    else
    {
      i = 2005;
      return i;
    }
    if (this.account == null)
    {
      i = 2007;
      return i;
    }
    Iterator localIterator = getPayments().iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      if (localPayment.getAmountValue() != null) {
        localPayment.getAmountValue().setCurrencyCode(this.account.getCurrencyCode());
      }
    }
    return i;
  }
  
  protected int doProcess(HttpSession paramHttpSession)
  {
    int i = 0;
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    localHashMap.put("PAYEES", paramHttpSession.getAttribute("Payees"));
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    localHashMap.put("ACCOUNTS", localAccounts);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localAccounts != null) {
      this.account = localAccounts.getByID(this.accountID);
    }
    Iterator localIterator = getPayments().iterator();
    while (localIterator.hasNext())
    {
      localObject = (Payment)localIterator.next();
      ((Payment)localObject).setAccount(this.account);
    }
    Object localObject = null;
    try
    {
      localObject = Billpay.modifyPaymentBatch(localSecureUser, this, this.originalPaymentBatch, localHashMap);
      set((PaymentBatch)localObject);
      paramHttpSession.setAttribute("PaymentBatch", localObject);
    }
    catch (CSILException localCSILException)
    {
      i = MapError.mapError(localCSILException);
    }
    return i;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
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
  
  public void setMinimumAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public void setMaximumAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public void setCurrentPayment(Payment paramPayment)
  {
    this.currentPayment = paramPayment;
  }
  
  public void setCurrentPayment(String paramString)
  {
    if (getPayments() != null) {
      this.currentPayment = getPayments().getByID(paramString);
    }
  }
  
  public String getPaymentDate()
  {
    if (this.currentPayment != null) {
      return this.currentPayment.getPayDate();
    }
    return null;
  }
  
  public void setPaymentDate(String paramString)
  {
    if (this.currentPayment != null) {
      if (hasValue(paramString)) {
        this.currentPayment.setPayDate(paramString);
      } else {
        this.currentPayment.setPayDate((com.ffusion.util.beans.DateTime)null);
      }
    }
  }
  
  public String getPaymentMemo()
  {
    if (this.currentPayment != null) {
      return this.currentPayment.getMemo();
    }
    return null;
  }
  
  public void setPaymentMemo(String paramString)
  {
    if (this.currentPayment != null) {
      this.currentPayment.setMemo(paramString);
    }
  }
  
  public String getPaymentCategory()
  {
    if (this.currentPayment != null) {
      return (String)this.currentPayment.get("REGISTER_CATEGORY_ID");
    }
    return null;
  }
  
  public void setPaymentCategory(String paramString)
  {
    if (this.currentPayment != null) {
      this.currentPayment.set("REGISTER_CATEGORY_ID", paramString);
    }
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public String getPaymentAmount()
  {
    if (this.currentPayment != null) {
      return this.currentPayment.getAmount();
    }
    return null;
  }
  
  public void setPaymentAmount(String paramString)
  {
    if (this.currentPayment != null) {
      this.currentPayment.setAmount(paramString);
    }
  }
  
  public void setAccountChanged(String paramString)
  {
    this.accountChanged = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean hasValue(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if ((paramObject instanceof String))
    {
      if (((String)paramObject).trim().equals("")) {
        return false;
      }
    }
    else if (((paramObject instanceof Currency)) && (((Currency)paramObject).getIsZero())) {
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.EditPaymentBatch
 * JD-Core Version:    0.7.0.1
 */