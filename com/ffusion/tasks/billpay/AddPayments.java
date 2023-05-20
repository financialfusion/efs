package com.ffusion.tasks.billpay;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddPayments
  extends Payments
  implements Task
{
  protected String dateWasChangedURL;
  protected int errorCode = 0;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean accountChangedFlag = false;
  protected String accountID;
  protected String minAmount = "1.00";
  protected String maxAmount = "10000.0";
  protected Payment currentPayment;
  protected String paymentsTotal = "0";
  protected Account account;
  protected String sortBy = "NAME";
  protected int paymentRetry = 14;
  public static final String DUPLICATEPAYMENT = "DUPLICATEPAYMENT";
  protected Payments sessionPayments = null;
  private Boolean Hq = Boolean.FALSE;
  private boolean Ht = false;
  private String Hr = null;
  private String Hs = null;
  private Currency Hp = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    synchronized (this)
    {
      if ((!this.Hq.booleanValue()) && (!this.Ht))
      {
        this.Hq = Boolean.TRUE;
        i = 1;
      }
    }
    if (i != 0)
    {
      try
      {
        this.errorCode = 0;
        this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
        if (localHttpSession.getAttribute("Payees") == null)
        {
          this.errorCode = 2002;
          str = this.taskErrorURL;
        }
        else if (localHttpSession.getAttribute("BillPayAccounts") == null)
        {
          this.errorCode = 2005;
          str = this.taskErrorURL;
        }
        else if (this.initFlag)
        {
          this.errorCode = initPayments(localHttpSession);
          if (this.errorCode != 0) {
            str = this.serviceErrorURL;
          }
        }
        else if (this.accountChangedFlag)
        {
          this.errorCode = accountChangedPayments(localHttpSession);
          if (this.errorCode != 0) {
            str = this.serviceErrorURL;
          }
        }
        else if (this.accountID == null)
        {
          this.errorCode = 2007;
          str = this.taskErrorURL;
        }
        else
        {
          this.sessionPayments = ((Payments)localHttpSession.getAttribute("Payments"));
          ??? = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
          this.account = ((Accounts)???).getByID(this.accountID);
          this.errorCode = validateInput();
          if (this.errorCode == 0)
          {
            if (size() > 0)
            {
              o();
              if (this.processFlag)
              {
                this.processFlag = false;
                if (this.account != null)
                {
                  removePayments();
                  this.errorCode = doProcess(localHttpSession, this.account);
                  str = this.Hr;
                  if (this.errorCode == 0)
                  {
                    this.Hr = str;
                    this.Ht = true;
                  }
                }
                else
                {
                  this.errorCode = 2007;
                  str = this.taskErrorURL;
                }
              }
            }
            else
            {
              this.errorCode = 2003;
              str = this.taskErrorURL;
            }
          }
          else {
            str = this.taskErrorURL;
          }
        }
        this.Hr = str;
      }
      catch (Exception localException1)
      {
        this.errorCode = 1;
        this.Hr = this.serviceErrorURL;
      }
      finally
      {
        this.Hq = Boolean.FALSE;
      }
    }
    else
    {
      long l1 = System.currentTimeMillis();
      long l2 = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      while ((!this.Ht) && (this.Hq.booleanValue() == true))
      {
        if (System.currentTimeMillis() - l1 > l2)
        {
          if (this.errorCode != 0) {
            break;
          }
          this.errorCode = 1;
          break;
        }
        try
        {
          Thread.sleep(2000L);
        }
        catch (Exception localException2) {}
      }
      str = this.Hr;
    }
    return str;
  }
  
  protected int initPayments(HttpSession paramHttpSession)
  {
    int i = 0;
    this.initFlag = false;
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    Iterator localIterator = localPayees.iterator();
    localPayees.setSortedBy(this.sortBy);
    this.paymentRetry = 14;
    int j = 1;
    while (localIterator.hasNext())
    {
      Payee localPayee = (Payee)localIterator.next();
      Payment localPayment = (Payment)create();
      localPayment.setPayee(localPayee);
      localPayment.setPayeeName(localPayee.getName());
      localPayment.setID(String.valueOf(j++));
      localPayment.setAmount("0");
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      localPayment.setPayDate(localGregorianCalendar);
      localPayment.setStatus(1);
    }
    this.Hq = Boolean.FALSE;
    this.Ht = false;
    return i;
  }
  
  protected int accountChangedPayments(HttpSession paramHttpSession)
  {
    int i = 0;
    this.accountChangedFlag = false;
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    if (localAccounts != null) {
      this.account = localAccounts.getByID(this.accountID);
    } else {
      return i;
    }
    if (this.account == null) {
      return i;
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      if (localPayment.getAmountValue() != null) {
        localPayment.getAmountValue().setCurrencyCode(this.account.getCurrencyCode());
      }
    }
    return i;
  }
  
  protected int doProcess(HttpSession paramHttpSession, Account paramAccount)
  {
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    int i = processAddPayment(localBillPay, paramHttpSession, paramAccount);
    return i;
  }
  
  protected int processAddPayment(BillPay paramBillPay, HttpSession paramHttpSession, Account paramAccount)
  {
    Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
    int i = 0;
    this.Hr = this.successURL;
    Payments localPayments = new Payments(getLocale());
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment1 = (Payment)localIterator.next();
      localPayment1.setAccount(paramAccount);
      if ((localPayment1.getStatus() == 1) || (localPayment1.getErrorValue() != 0))
      {
        setCurrentPayment(localPayment1.getID());
        localPayment1.setError(0);
        localPayments.add(localPayment1);
      }
    }
    Object localObject1;
    Object localObject2;
    for (int j = 0; (localPayments.size() > 0) && (j <= this.paymentRetry); j++)
    {
      localObject1 = new HashMap();
      if (paramBillPay != null) {
        ((HashMap)localObject1).put("SERVICE", paramBillPay);
      }
      ((HashMap)localObject1).put("ACCOUNT", paramAccount);
      ((HashMap)localObject1).put("PAYEES", localPayees);
      localObject2 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      if (paramHttpSession != null) {
        this.Hs = paramAccount.getCurrencyCode();
      }
      ((HashMap)localObject1).put("Curdef", this.Hs);
      try
      {
        localPayments = Billpay.sendPayments((SecureUser)localObject2, localPayments, (HashMap)localObject1);
      }
      catch (CSILException localCSILException)
      {
        if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
          paramHttpSession.setAttribute("ExceededLimits", ((HashMap)localObject1).get("ExceededLimits"));
        }
        if (localCSILException.getCode() == -1009) {
          this.Hr = this.serviceErrorURL;
        } else {
          this.Hr = this.taskErrorURL;
        }
        i = MapError.mapError(localCSILException);
      }
      for (int k = localPayments.size() - 1; k >= 0; k--)
      {
        Payment localPayment2 = (Payment)localPayments.get(k);
        if ((localPayment2.getErrorValue() != 2004) && (localPayment2.getErrorValue() != 0) && (i == 0))
        {
          i = localPayment2.getErrorValue();
          if (i != 0)
          {
            if (i == 20003) {
              paramHttpSession.setAttribute("ExceededLimits", ((HashMap)localObject1).get("ExceededLimits"));
            }
            this.Hr = this.serviceErrorURL;
          }
        }
        if (localPayment2.getStatus() != 8)
        {
          localPayments.remove(k);
        }
        else
        {
          DateTime localDateTime = localPayment2.getPayDateValue();
          localDateTime.add(5, 1);
          int m = localDateTime.get(7);
          if (m == 7) {
            localDateTime.add(5, 2);
          } else if (m == 1) {
            localDateTime.add(5, 1);
          }
          localPayment2.setPayDate(localDateTime);
        }
      }
    }
    localIterator = iterator();
    localPayments = (Payments)paramHttpSession.getAttribute("Payments");
    while (localIterator.hasNext())
    {
      localObject1 = (Payment)localIterator.next();
      if (((Payment)localObject1).getErrorValue() == 0)
      {
        localObject2 = (String)((Payment)localObject1).get("AlreadyAdded");
        if ((localObject2 == null) || (((String)localObject2).length() == 0))
        {
          ((Payment)localObject1).setAccount(paramAccount);
          ((Payment)localObject1).setAccountID(paramAccount.getID());
          ((Payment)localObject1).set("AlreadyAdded", "true");
          localPayments.add(localObject1);
        }
      }
      else
      {
        setCurrentPayment(((Payment)localObject1).getID());
      }
    }
    return i;
  }
  
  protected int validateInput()
  {
    int i = 0;
    if (this.validate != null)
    {
      int j = 0;
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Payment localPayment = (Payment)localIterator.next();
        if (localPayment.get("BadAmount") != null) {
          i = 2018;
        }
        if ((localPayment.getAmount() == null) || (localPayment.getAmount().length() == 0) || (localPayment.getAmountValue().doubleValue() == 0.0D))
        {
          j++;
        }
        else
        {
          if ((localPayment.getAmountValue() != null) && (this.account != null)) {
            localPayment.getAmountValue().setCurrencyCode(this.account.getCurrencyCode());
          }
          if ((i == 0) && (this.validate.indexOf("AMOUNT") != -1)) {
            i = validateAmount(localPayment.getAmountValue());
          }
          if ((i == 0) && (this.validate.indexOf("PAYDATE") != -1)) {
            i = validateDate(localPayment.getPayDateValue());
          }
          if ((i == 0) && (this.validate.indexOf("DUPLICATEPAYMENT") != -1)) {
            i = validateDuplicatePayment(localPayment);
          }
          if (i != 0) {
            break;
          }
        }
      }
      if ((i == 0) && (j == size())) {
        i = 2006;
      }
    }
    this.validate = null;
    return i;
  }
  
  protected int validateAmount(Currency paramCurrency)
  {
    int i = 0;
    Currency localCurrency1 = new Currency(this.minAmount, this.locale);
    Currency localCurrency2 = new Currency(this.maxAmount, this.locale);
    if (localCurrency1 == null) {
      i = 2015;
    } else if (localCurrency2 == null) {
      i = 2016;
    } else if (paramCurrency == null) {
      i = 2018;
    } else if (paramCurrency.compareTo(localCurrency1) == -1) {
      i = 2015;
    } else if (paramCurrency.compareTo(localCurrency2) == 1) {
      i = 2016;
    }
    return i;
  }
  
  protected void removePayments()
  {
    Currency localCurrency1 = new Currency(this.minAmount, this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      Currency localCurrency2 = localPayment.getAmountValue();
      if ((localCurrency2 == null) || (localCurrency2.compareTo(localCurrency1) == -1)) {
        remove(localPayment);
      }
    }
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
  
  protected int validateDuplicatePayment(Payment paramPayment)
  {
    Payments localPayments = this.sessionPayments;
    if (localPayments == null) {
      return 0;
    }
    Iterator localIterator = localPayments.iterator();
    int i = 0;
    while ((localIterator != null) && (localIterator.hasNext()))
    {
      Payment localPayment = (Payment)localIterator.next();
      Double localDouble1 = new Double(paramPayment.getAmountValue().doubleValue());
      Double localDouble2 = new Double(localPayment.getAmountValue().doubleValue());
      if ((localDouble1.equals(localDouble2)) && (localPayment.compare(paramPayment, "PAYEEID") == 0) && (localPayment.getAccountID().equals(this.account.getID())))
      {
        DateTime localDateTime1 = new DateTime(paramPayment.getPayDateValue(), localPayment.getLocale());
        DateTime localDateTime2 = new DateTime(localPayment.getPayDateValue(), localPayment.getLocale());
        if (localDateTime1.toString().equals(localDateTime2.toString())) {
          i = 2029;
        }
      }
    }
    return i;
  }
  
  private void o()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payment localPayment = (Payment)localIterator.next();
      if (localPayment.getAmountValue() != null) {
        d += localPayment.getAmountValue().doubleValue();
      }
    }
    this.Hp = new Currency(new BigDecimal(d), this.locale);
    this.paymentsTotal = this.Hp.toString();
  }
  
  public String getPaymentsTotal()
  {
    return this.paymentsTotal;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setMinimumAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public void setMaximumAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public void setDateWasChangedURL(String paramString)
  {
    this.dateWasChangedURL = paramString;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAccountChanged(String paramString)
  {
    this.accountChangedFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPaymentRetry(String paramString)
  {
    this.paymentRetry = Integer.valueOf(paramString).intValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
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
  
  public String getError()
  {
    return String.valueOf(this.errorCode);
  }
  
  public String getPayeeID()
  {
    if (this.currentPayment != null)
    {
      Payee localPayee = this.currentPayment.getPayee();
      if (localPayee != null) {
        return localPayee.getID();
      }
    }
    return "";
  }
  
  public String getAccountName()
  {
    if (this.account != null) {
      return this.account.getConsumerDisplayText();
    }
    return "";
  }
  
  public String getAccountBalance()
  {
    if (this.account != null) {
      return this.account.getCurrentBalance().getAmount();
    }
    return "";
  }
  
  public String getUserAccountNumber()
  {
    if (this.currentPayment != null)
    {
      Payee localPayee = this.currentPayment.getPayee();
      if (localPayee != null) {
        return localPayee.getUserAccountNumber();
      }
    }
    return "";
  }
  
  public void setCurrentPayment(String paramString)
  {
    this.currentPayment = getByID(paramString);
  }
  
  public void setPayNow(String paramString)
  {
    if (this.currentPayment != null) {
      this.currentPayment.set("PayNow", paramString);
    }
  }
  
  public void setPaymentAmount(String paramString)
  {
    if (this.currentPayment != null)
    {
      this.currentPayment.remove("BadAmount");
      this.currentPayment.setAmount(paramString);
      if ((this.currentPayment.getAmountValue().doubleValue() == 0.0D) && (paramString != null)) {
        for (int i = 0; i < paramString.length(); i++)
        {
          char c = paramString.charAt(i);
          if ((!Character.isDigit(c)) && (c != '.'))
          {
            this.currentPayment.set("BadAmount", paramString);
            break;
          }
        }
      }
    }
  }
  
  public void setPaymentDate(String paramString)
  {
    if (this.currentPayment != null) {
      this.currentPayment.setPayDate(paramString);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    if (this.currentPayment != null) {
      this.currentPayment.setDateFormat(paramString);
    }
  }
  
  public void setSortBy(String paramString)
  {
    this.sortBy = paramString;
  }
  
  public void setCurrency(Currency paramCurrency)
  {
    this.Hp = paramCurrency;
  }
  
  public Currency getCurrency()
  {
    return this.Hp;
  }
  
  public String getPaymentsTotalInCurrency()
  {
    return this.Hp.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.AddPayments
 * JD-Core Version:    0.7.0.1
 */