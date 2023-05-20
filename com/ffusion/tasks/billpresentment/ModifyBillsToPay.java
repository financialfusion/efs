package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBillsToPay
  extends BillSummaries
  implements Task
{
  protected static String AMOUNTPAID = "AMOUNTPAID";
  protected static String PAYMENTDATE = "PAYMENTDATE";
  protected static String PAYMENTACCOUNT = "PAYMENTACCOUNT";
  private String I1;
  private String I3;
  private String IZ;
  private int I5;
  private String I6;
  private boolean I7 = false;
  private boolean IY = false;
  private String I2 = "1.00";
  private String I0 = "10000.0";
  protected String paymentsTotal = "0";
  private String I4;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    BillSummaries localBillSummaries = (BillSummaries)localHttpSession.getAttribute("BillsToPay");
    if (localBillSummaries == null)
    {
      str = this.I1;
      this.I5 = 6516;
    }
    else if (this.IY)
    {
      if (!init(localHttpSession)) {
        str = this.I1;
      } else {
        str = this.IZ;
      }
    }
    else
    {
      str = processModifyBillsToPay(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    clear();
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    BillSummaries localBillSummaries = (BillSummaries)paramHttpSession.getAttribute("BillsToPay");
    if (localBillSummaries == null)
    {
      this.I5 = 6516;
      this.IY = false;
      return false;
    }
    Iterator localIterator = localBillSummaries.iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      add(localBillSummary);
    }
    this.IY = false;
    return true;
  }
  
  protected String processModifyBillsToPay(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.I7)
      {
        totalPayments();
        this.I7 = false;
        str = this.IZ;
        paramHttpSession.setAttribute("BillsToPay", this);
      }
      else
      {
        str = this.IZ;
      }
    }
    else {
      str = this.I1;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.I6 != null)
    {
      if ((bool) && (this.I6.indexOf(AMOUNTPAID) != -1)) {
        bool = validateAmountPaid();
      }
      if ((bool) && (this.I6.indexOf(PAYMENTDATE) != -1)) {
        bool = validatePaymentDate();
      }
      if ((bool) && (this.I6.indexOf(PAYMENTACCOUNT) != -1)) {
        bool = validatePaymentAccount(paramHttpSession);
      }
      this.I6 = null;
    }
    return bool;
  }
  
  protected boolean validatePaymentAccount(HttpSession paramHttpSession)
  {
    this.I5 = 0;
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    localAccounts.setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      Account localAccount = localAccounts.getByID(localBillSummary.getPaymentAccountID());
      if (localAccount == null)
      {
        this.I5 = 6603;
        break;
      }
    }
    return this.I5 == 0;
  }
  
  protected boolean validateAmountPaid()
  {
    Currency localCurrency1 = new Currency(this.I2, getLocale());
    Currency localCurrency2 = new Currency(this.I0, getLocale());
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      Currency localCurrency3 = localBillSummary.getAmountPaidValue();
      if (localCurrency3 == null)
      {
        this.I5 = 6604;
        break;
      }
      if (localCurrency3.compareTo(localCurrency1) == -1)
      {
        this.I5 = 6605;
        break;
      }
      if (localCurrency3.compareTo(localCurrency2) == 1)
      {
        this.I5 = 6606;
        break;
      }
      this.I5 = 0;
    }
    return this.I5 == 0;
  }
  
  protected boolean validatePaymentDate()
  {
    this.I5 = 0;
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      Object localObject = localBillSummary.getPaymentDateValue();
      if (localObject == null)
      {
        localObject = localGregorianCalendar1;
        this.I5 = 6600;
        return false;
      }
      GregorianCalendar localGregorianCalendar2 = (GregorianCalendar)((Calendar)localObject).clone();
      localGregorianCalendar2.set(1, localGregorianCalendar1.get(1));
      localGregorianCalendar2.set(2, localGregorianCalendar1.get(2));
      localGregorianCalendar2.set(5, localGregorianCalendar1.get(5));
      if (((Calendar)localObject).before(localGregorianCalendar2))
      {
        localObject = localGregorianCalendar2;
        this.I5 = 6601;
        return false;
      }
    }
    return this.I5 == 0;
  }
  
  protected void totalPayments()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localObject = (BillSummary)localIterator.next();
      if (((BillSummary)localObject).getAmountPaidValue() != null) {
        d += ((BillSummary)localObject).getAmountPaidValue().doubleValue();
      }
    }
    Object localObject = new Currency(new BigDecimal(d), getLocale());
    this.paymentsTotal = ((Currency)localObject).toString();
  }
  
  public String getPaymentsTotal()
  {
    return this.paymentsTotal;
  }
  
  public final void setMinimumAmount(String paramString)
  {
    this.I2 = paramString;
  }
  
  public final void setMaximumAmount(String paramString)
  {
    this.I0 = paramString;
  }
  
  public final String getMinimumAmount()
  {
    return this.I2;
  }
  
  public final String getMaximumAmount()
  {
    return this.I0;
  }
  
  public final void setID(String paramString)
  {
    this.I4 = paramString;
  }
  
  public void setAmountPaid(String paramString)
  {
    BillSummary localBillSummary = getByBillSummaryID(this.I4);
    if (localBillSummary != null) {
      if ((paramString == null) || (paramString.trim().length() == 0)) {
        localBillSummary.setAmountPaid(new Currency("0", getLocale()));
      } else {
        localBillSummary.setAmountPaid(paramString);
      }
    }
  }
  
  public final void setPaymentAccountID(String paramString)
  {
    BillSummary localBillSummary = getByBillSummaryID(this.I4);
    if (localBillSummary != null) {
      localBillSummary.setPaymentAccountID(paramString);
    }
  }
  
  public final void setPaymentDate(String paramString)
  {
    BillSummary localBillSummary = getByBillSummaryID(this.I4);
    if (localBillSummary != null) {
      localBillSummary.setPaymentDate(paramString);
    }
  }
  
  public final void setStatusCode(String paramString)
  {
    BillSummary localBillSummary = getByBillSummaryID(this.I4);
    if (localBillSummary != null) {
      localBillSummary.setStatusCode(paramString);
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.IZ = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.I1 = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.I3 = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.I5);
  }
  
  public final void setInitialize(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.IY = true;
    } else {
      this.IY = false;
    }
  }
  
  public final void setInitialize(boolean paramBoolean)
  {
    this.IY = paramBoolean;
  }
  
  public final String getInitialize()
  {
    return String.valueOf(this.IY);
  }
  
  public final boolean getInitializeValue()
  {
    return this.IY;
  }
  
  public final void setProcess(String paramString)
  {
    this.I7 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setProcess(boolean paramBoolean)
  {
    this.I7 = paramBoolean;
  }
  
  public final String getProcess()
  {
    return String.valueOf(this.I7);
  }
  
  public final boolean getProcessValue()
  {
    return this.I7;
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.I6 = paramString.toUpperCase();
    } else {
      this.I6 = null;
    }
  }
  
  public final String getValidate()
  {
    return this.I6;
  }
  
  public final void setError(String paramString)
  {
    this.I5 = Integer.parseInt(paramString);
  }
  
  public final void setError(int paramInt)
  {
    this.I5 = paramInt;
  }
  
  public final int getErrorValue()
  {
    return this.I5;
  }
  
  public final String getSuccessURL()
  {
    return this.IZ;
  }
  
  public final String getServiceErrorURL()
  {
    return this.I3;
  }
  
  public final String getTaskErrorURL()
  {
    return this.I1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyBillsToPay
 * JD-Core Version:    0.7.0.1
 */