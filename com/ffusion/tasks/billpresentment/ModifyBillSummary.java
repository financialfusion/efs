package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBillSummary
  extends BillSummary
  implements Task
{
  private static String AMOUNTPAID = "AMOUNTPAID";
  private static String PAYMENTDATE = "PAYMENTDATE";
  private static String J5 = "PAYMENTACCOUNT";
  private int Kb = 0;
  private String J6 = null;
  private String J8 = null;
  private String Ka = null;
  private String Kc;
  private boolean Kd = false;
  private boolean J4 = false;
  private String J9 = "1.00";
  private String J7 = "10000.0";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.J4)
    {
      if (initProcess(localHttpSession)) {
        str = this.J6;
      } else {
        str = this.J8;
      }
    }
    else {
      str = processModifyBillSummary(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.J4 = false;
    BillSummary localBillSummary = (BillSummary)paramHttpSession.getAttribute("BillSummary");
    if (localBillSummary == null)
    {
      this.Kb = 6504;
    }
    else
    {
      this.Kb = 0;
      set(localBillSummary);
    }
    return this.Kb == 0;
  }
  
  protected String processModifyBillSummary(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.Kd)
      {
        this.Kd = false;
        str = modifyBillSummary(paramHttpSession);
      }
      else
      {
        str = this.J6;
      }
    }
    else {
      str = this.J8;
    }
    return str;
  }
  
  protected String modifyBillSummary(HttpSession paramHttpSession)
  {
    String str = null;
    BillSummary localBillSummary1 = (BillSummary)paramHttpSession.getAttribute("BillSummary");
    if (localBillSummary1 == null)
    {
      this.Kb = 6504;
      str = this.J8;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.Kb = 0;
      try
      {
        BillSummary localBillSummary2 = BillPresentment.modifyBillSummary(localSecureUser, this, null, localHashMap);
        set(localBillSummary2);
      }
      catch (CSILException localCSILException)
      {
        this.Kb = MapError.mapError(localCSILException);
      }
      if (this.Kb == 0)
      {
        localBillSummary1.set(this);
        str = this.J6;
      }
      else
      {
        str = this.Ka;
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.Kc != null)
    {
      if ((bool) && (this.Kc.indexOf("STATUSCODE") != -1) && ((getStatusCode() == null) || (getStatusCode().length() == 0)))
      {
        this.Kb = 6610;
        bool = false;
      }
      if ((bool) && (this.Kc.indexOf(AMOUNTPAID) != -1)) {
        bool = validateAmountPaid();
      }
      if ((bool) && (this.Kc.indexOf(PAYMENTDATE) != -1)) {
        bool = validatePaymentDate();
      }
      if ((bool) && (this.Kc.indexOf(J5) != -1)) {
        bool = validatePaymentAccount(paramHttpSession, getPaymentAccountID());
      }
      this.Kc = null;
    }
    return bool;
  }
  
  protected boolean validatePaymentAccount(HttpSession paramHttpSession, String paramString)
  {
    this.Kb = 0;
    if ((paramString == null) || (paramString.length() == 0))
    {
      this.Kb = 6602;
    }
    else
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
      localAccounts.setFilter("All");
      Account localAccount = localAccounts.getByID(paramString);
      if (localAccount == null) {
        this.Kb = 6603;
      }
    }
    return this.Kb == 0;
  }
  
  protected boolean validateAmountPaid()
  {
    Currency localCurrency1 = new Currency(this.J9, getLocale());
    Currency localCurrency2 = new Currency(this.J7, getLocale());
    Currency localCurrency3 = getAmountPaidValue();
    if (localCurrency3 == null) {
      this.Kb = 6604;
    } else if (localCurrency3.compareTo(localCurrency1) == -1) {
      this.Kb = 6605;
    } else if (localCurrency3.compareTo(localCurrency2) == 1) {
      this.Kb = 6606;
    } else {
      this.Kb = 0;
    }
    return this.Kb == 0;
  }
  
  protected boolean validatePaymentDate()
  {
    this.Kb = 0;
    DateTime localDateTime1 = new DateTime(getLocale());
    if (getPaymentDate() == null)
    {
      setPaymentDate(localDateTime1);
      this.Kb = 6600;
    }
    else
    {
      DateTime localDateTime2 = (DateTime)getPaymentDateValue().clone();
      localDateTime2.set(1, localDateTime1.get(1));
      localDateTime2.set(2, localDateTime1.get(2));
      localDateTime2.set(5, localDateTime1.get(5));
    }
    return this.Kb == 0;
  }
  
  public final void setMinimumAmount(String paramString)
  {
    this.J9 = paramString;
  }
  
  public final void setMaximumAmount(String paramString)
  {
    this.J7 = paramString;
  }
  
  public final void setInitialize(String paramString)
  {
    this.J4 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setProcess(String paramString)
  {
    this.Kd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.Kc = paramString.toUpperCase();
    } else {
      this.Kc = null;
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.J6 = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.J8 = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Ka = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Kb);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyBillSummary
 * JD-Core Version:    0.7.0.1
 */