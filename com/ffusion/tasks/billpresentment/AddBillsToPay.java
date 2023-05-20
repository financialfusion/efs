package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBillsToPay
  extends ModifyBillsToPay
{
  private String I8;
  private String I9;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (getInitializeValue())
    {
      if (!init(localHttpSession)) {
        str = getTaskErrorURL();
      } else {
        str = addBillsToPay(localHttpSession);
      }
    }
    else {
      str = processAddBillsToPay(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    BillSummaries localBillSummaries = (BillSummaries)paramHttpSession.getAttribute("BillSummaries");
    if (localBillSummaries == null)
    {
      setError(6505);
      setInitialize(false);
      return false;
    }
    Iterator localIterator = localBillSummaries.iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      if (this.I9 == null) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("NEW") != -1) && (localBillSummary.getStatusCode().indexOf("NEW") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("UNVIEW") != -1) && (localBillSummary.getStatusCode().indexOf("UNVIEW") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("VIEWED") != -1) && (localBillSummary.getStatusCode().indexOf("VIEWED") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("FILED") != -1) && (localBillSummary.getStatusCode().indexOf("FILED") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("AFILED") != -1) && (localBillSummary.getStatusCode().indexOf("AFILED") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("DELETE") != -1) && (localBillSummary.getStatusCode().indexOf("DELETE") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("MARKED") != -1) && (localBillSummary.getStatusCode().indexOf("MARKED") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("ERROR") != -1) && (localBillSummary.getStatusCode().indexOf("ERROR") != -1)) {
        add(localBillSummary);
      } else if ((this.I9.indexOf("PEND") != -1) && (localBillSummary.getStatusCode().indexOf("PEND") != -1)) {
        add(localBillSummary);
      }
    }
    setInitialize(false);
    return true;
  }
  
  protected String processAddBillsToPay(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    removeNonPayments(paramHttpSession);
    if (validateInput(paramHttpSession))
    {
      if (getProcessValue())
      {
        totalPayments();
        if (size() == 0)
        {
          setError(6900);
          str = getTaskErrorURL();
        }
        else
        {
          setProcess(false);
          str = addBillsToPay(paramHttpSession);
        }
      }
      else
      {
        str = getSuccessURL();
      }
    }
    else {
      str = getTaskErrorURL();
    }
    return str;
  }
  
  protected String addBillsToPay(HttpSession paramHttpSession)
  {
    String str = getSuccessURL();
    paramHttpSession.setAttribute("BillsToPay", this);
    return str;
  }
  
  protected void removeNonPayments(HttpSession paramHttpSession)
  {
    BillSummaries localBillSummaries = new BillSummaries(getLocale());
    Iterator localIterator = iterator();
    BillSummary localBillSummary;
    while (localIterator.hasNext())
    {
      localBillSummary = (BillSummary)localIterator.next();
      Currency localCurrency = localBillSummary.getAmountPaidValue();
      if ((localCurrency != null) && (localCurrency.doubleValue() != 0.0D)) {
        if ((this.I8 != null) && (this.I8.indexOf(PAYMENTDATE) == -1)) {
          localBillSummaries.add(localBillSummary);
        } else if ((localBillSummary.getPaymentDate() != null) && (localBillSummary.getPaymentDate().length() > 0)) {
          localBillSummaries.add(localBillSummary);
        }
      }
    }
    clear();
    localIterator = localBillSummaries.iterator();
    while (localIterator.hasNext())
    {
      localBillSummary = (BillSummary)localIterator.next();
      add(localBillSummary);
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (getValidate() != null)
    {
      if ((bool) && ((this.I8.indexOf(AMOUNTPAID) != -1) || (getValidate().indexOf(AMOUNTPAID) != -1))) {
        bool = validateAmountPaid();
      }
      if ((bool) && ((this.I8.indexOf(PAYMENTDATE) != -1) || (getValidate().indexOf(PAYMENTDATE) != -1))) {
        bool = validatePaymentDate();
      }
      if ((bool) && ((this.I8.indexOf(PAYMENTACCOUNT) != -1) || (getValidate().indexOf(PAYMENTACCOUNT) != -1))) {
        bool = validatePaymentAccount(paramHttpSession);
      }
      setValidate("");
    }
    return bool;
  }
  
  protected boolean validatePaymentAccount(HttpSession paramHttpSession)
  {
    setError(0);
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    localAccounts.setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      Account localAccount = localAccounts.getByID(localBillSummary.getPaymentAccountID());
      if ((localAccount == null) && (this.I8.indexOf(PAYMENTACCOUNT) != -1))
      {
        remove(localBillSummary);
        break;
      }
      if (localAccount == null)
      {
        setError(6603);
        break;
      }
    }
    return getErrorValue() == 0;
  }
  
  protected boolean validateAmountPaid()
  {
    Currency localCurrency1 = new Currency(getMinimumAmount(), getLocale());
    Currency localCurrency2 = new Currency(getMaximumAmount(), getLocale());
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      Currency localCurrency3 = localBillSummary.getAmountPaidValue();
      if (localCurrency3 == null)
      {
        setError(6604);
        break;
      }
      if (localCurrency3.compareTo(localCurrency1) == -1)
      {
        setError(6605);
        break;
      }
      if (localCurrency3.compareTo(localCurrency2) == 1)
      {
        setError(6606);
        break;
      }
      setError(0);
    }
    return getErrorValue() == 0;
  }
  
  protected boolean validatePaymentDate()
  {
    setError(0);
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      Object localObject = localBillSummary.getPaymentDateValue();
      if ((localObject == null) && (this.I8.indexOf(PAYMENTDATE) != -1))
      {
        remove(localBillSummary);
        break;
      }
      if (localObject == null)
      {
        localObject = localGregorianCalendar1;
        setError(6600);
        return false;
      }
      GregorianCalendar localGregorianCalendar2 = (GregorianCalendar)((Calendar)localObject).clone();
      localGregorianCalendar2.set(1, localGregorianCalendar1.get(1));
      localGregorianCalendar2.set(2, localGregorianCalendar1.get(2));
      localGregorianCalendar2.set(5, localGregorianCalendar1.get(5));
      if (((Calendar)localObject).before(localGregorianCalendar2))
      {
        localObject = localGregorianCalendar2;
        setError(6601);
        return false;
      }
    }
    return getErrorValue() == 0;
  }
  
  public final void setRequiredFields(String paramString)
  {
    if (!paramString.equals("")) {
      this.I8 = paramString.toUpperCase();
    } else {
      this.I8 = null;
    }
  }
  
  public final void setRequiredStatus(String paramString)
  {
    if (!paramString.equals("")) {
      this.I9 = paramString.toUpperCase();
    } else {
      this.I9 = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.AddBillsToPay
 * JD-Core Version:    0.7.0.1
 */