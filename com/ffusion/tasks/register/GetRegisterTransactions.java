package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRegisterTransactions
  extends ExtendedBaseTask
  implements Task
{
  protected DateTime fromDate = null;
  protected DateTime toDate = null;
  protected String refNum = null;
  protected boolean includeUnreconciled = false;
  protected String futureCollectionSessionName = null;
  protected long timeToProcess = 0L;
  
  public GetRegisterTransactions()
  {
    this.beanSessionName = "Account";
    this.collectionSessionName = "RegisterTransactions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    Account localAccount = (Account)localHttpSession.getAttribute(this.beanSessionName);
    if (localAccount == null)
    {
      this.error = 20001;
      return this.taskErrorURL;
    }
    if (((this.fromDate != null) && ((!this.fromDate.isValid()) || (this.fromDate.toString().length() > 10))) || ((this.toDate != null) && ((!this.toDate.isValid()) || (this.toDate.toString().length() > 10))))
    {
      this.error = 44;
      return this.taskErrorURL;
    }
    if ((this.fromDate != null) && (this.toDate != null) && (this.fromDate.after(this.toDate)))
    {
      this.error = 79;
      return this.taskErrorURL;
    }
    RegisterTransaction localRegisterTransaction = new RegisterTransaction();
    localRegisterTransaction.set("ACCOUNT", localAccount.getID());
    localRegisterTransaction.set("CURRENCY_CODE", localAccount.getCurrencyCode());
    RegisterTransactions localRegisterTransactions1 = new RegisterTransactions();
    localRegisterTransactions1.setLocale(localSecureUser.getLocale());
    localRegisterTransactions1.add(localRegisterTransaction);
    HashMap localHashMap = new HashMap();
    localHashMap.put("REGISTERTRANSACTIONS", localRegisterTransactions1);
    if ((this.refNum != null) && (!this.refNum.trim().equals("")))
    {
      localRegisterTransaction.setReferenceNumber(this.refNum);
      try
      {
        localRegisterTransactions1 = Register.getRegisterTransactions(localSecureUser, null, null, localAccount.getID(), this.refNum, this.includeUnreconciled, localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
      }
      if (this.error != 0) {
        return this.serviceErrorURL;
      }
    }
    else
    {
      try
      {
        localRegisterTransactions1 = Register.getRegisterTransactions(localSecureUser, this.fromDate, this.toDate, localAccount.getID(), null, this.includeUnreconciled, localHashMap);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
      if (this.error != 0) {
        return this.serviceErrorURL;
      }
    }
    localHttpSession.setAttribute(this.collectionSessionName, localRegisterTransactions1);
    if (this.includeUnreconciled)
    {
      RegisterTransactions localRegisterTransactions2 = getFutureTransactions(localRegisterTransactions1, localAccount);
      if (localRegisterTransactions2 != null) {
        localHttpSession.setAttribute(this.futureCollectionSessionName, localRegisterTransactions2);
      }
    }
    return this.successURL;
  }
  
  protected RegisterTransactions getFutureTransactions(RegisterTransactions paramRegisterTransactions, Account paramAccount)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    DateTime localDateTime = new DateTime(Locale.getDefault());
    localDateTime.set(11, 23);
    localDateTime.set(12, 59);
    localDateTime.set(13, 59);
    localDateTime.set(14, 999);
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction != null) && (localRegisterTransaction.getStatusValue() != 1) && (localRegisterTransaction.getDateIssuedValue().after(localDateTime)))
      {
        localRegisterTransactions.add(localRegisterTransaction);
        localIterator.remove();
      }
    }
    return localRegisterTransactions;
  }
  
  protected Currency fixAmount(Transfer paramTransfer, Account paramAccount)
  {
    boolean bool = Boolean.valueOf((String)paramAccount.get("DEBIT")).booleanValue();
    Currency localCurrency = (Currency)paramTransfer.getAmountValue().clone();
    if (((bool) && (paramTransfer.getFromAccountID().equals(paramAccount.getID()))) || ((!bool) && (paramTransfer.getToAccountID().equals(paramAccount.getID())))) {
      localCurrency.setAmount(localCurrency.getAmountValue().negate());
    }
    return localCurrency;
  }
  
  protected Currency fixAmount(Payment paramPayment, Account paramAccount)
  {
    Currency localCurrency = (Currency)paramPayment.getAmountValue().clone();
    if (Boolean.valueOf((String)paramAccount.get("DEBIT")).booleanValue()) {
      localCurrency.setAmount(localCurrency.getAmountValue().negate());
    }
    return localCurrency;
  }
  
  public void setFromDate(String paramString)
  {
    try
    {
      if ((paramString == null) || (paramString.length() <= 0) || (paramString.equalsIgnoreCase("MM/DD/YYYY")))
      {
        this.fromDate = null;
      }
      else
      {
        if (this.fromDate == null) {
          this.fromDate = new DateTime(this.locale);
        }
        this.fromDate.setFormat(this.dateFormat);
        this.fromDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setFromDate(DateTime paramDateTime)
  {
    this.fromDate = paramDateTime;
  }
  
  public String getFromDate()
  {
    if (this.fromDate == null) {
      return null;
    }
    this.fromDate.setFormat(this.dateFormat);
    return this.fromDate.toString();
  }
  
  public DateTime getFromDateValue()
  {
    return this.fromDate;
  }
  
  public void setToDate(String paramString)
  {
    try
    {
      if ((paramString == null) || (paramString.length() <= 0) || (paramString.equalsIgnoreCase("MM/DD/YYYY")))
      {
        this.toDate = null;
      }
      else
      {
        if (this.toDate == null) {
          this.toDate = new DateTime(this.locale);
        }
        this.toDate.setFormat(this.dateFormat);
        this.toDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setToDate(DateTime paramDateTime)
  {
    this.toDate = paramDateTime;
  }
  
  public String getToDate()
  {
    if (this.toDate == null) {
      return null;
    }
    this.toDate.setFormat(this.dateFormat);
    return this.toDate.toString();
  }
  
  public DateTime getToDateValue()
  {
    return this.toDate;
  }
  
  public void setReferenceNumber(String paramString)
  {
    this.refNum = paramString;
  }
  
  public String getReferenceNumber()
  {
    return this.refNum;
  }
  
  public void setIncludeUnreconciled(String paramString)
  {
    this.includeUnreconciled = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getIncludeUnreconciled()
  {
    return this.includeUnreconciled;
  }
  
  public void setFutureCollectionSessionName(String paramString)
  {
    this.futureCollectionSessionName = paramString;
  }
  
  public String getFutureCollectionSessionName()
  {
    return this.futureCollectionSessionName;
  }
  
  public String getTimeToProcess()
  {
    return String.valueOf(this.timeToProcess);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetRegisterTransactions
 * JD-Core Version:    0.7.0.1
 */