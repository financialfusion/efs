package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccounts
  extends BaseTask
  implements Task
{
  private String Hu;
  private boolean Hv = false;
  private String Hy;
  private String Hx;
  private String Hz;
  private Account Hw;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    Object localObject = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    String str = null;
    if ((localObject == null) || (this.Hv)) {
      if (this.Hu != null)
      {
        Accounts localAccounts = new Accounts(localLocale);
        localObject = (Accounts)localHttpSession.getAttribute(this.Hu);
        if (localObject != null)
        {
          ((Accounts)localObject).setFilter("All");
          localAccounts.addAll((Collection)localObject);
          localObject = localAccounts;
        }
      }
      else
      {
        localObject = getAccounts(localHttpSession);
      }
    }
    if (localObject == null)
    {
      if (this.Hu != null)
      {
        this.error = 2005;
        str = this.taskErrorURL;
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    else
    {
      ((Accounts)localObject).setLocale(localLocale);
      localHttpSession.setAttribute("BillPayAccounts", localObject);
      str = setBillPayAccount(localHttpSession);
    }
    return str;
  }
  
  protected Accounts getAccounts(HttpSession paramHttpSession)
  {
    Accounts localAccounts = new Accounts((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
      localHashMap.put("ACCOUNTS", localAccounts);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localAccounts = Billpay.getAccounts(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error != 0) {
      localAccounts = null;
    }
    return localAccounts;
  }
  
  protected String setBillPayAccount(HttpSession paramHttpSession)
  {
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BillPayAccounts");
    localAccounts.setFilter("All");
    for (int i = localAccounts.size() - 1; i >= 0; i--)
    {
      localObject1 = (Account)localAccounts.get(i);
      if (!((Account)localObject1).isFilterable("BillPay")) {
        localAccounts.remove(localObject1);
      }
    }
    localAccounts.setFilter("BillPay");
    i = 0;
    Object localObject1 = localAccounts.iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Account)((Iterator)localObject1).next();
      i++;
      if (i == 1) {
        paramHttpSession.setAttribute("BillPayAccount", localObject2);
      }
    }
    if (i == 0) {
      localObject2 = this.Hy;
    } else if (i == 1) {
      localObject2 = this.Hx;
    } else {
      localObject2 = this.Hz;
    }
    return localObject2;
  }
  
  public void setUseAccounts(String paramString)
  {
    this.Hu = paramString;
  }
  
  public String getUseAccounts()
  {
    return this.Hu;
  }
  
  public void setAccountReload(String paramString)
  {
    this.Hv = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setNoBillPayAccountsURL(String paramString)
  {
    this.Hy = paramString;
  }
  
  public String getNoBillPayAccountsURL()
  {
    return this.Hy;
  }
  
  public void setOneBillPayAccountURL(String paramString)
  {
    this.Hx = paramString;
  }
  
  public String getOneBillPayAccountURL()
  {
    return this.Hx;
  }
  
  public void setMultipleBillPayAccountsURL(String paramString)
  {
    this.Hz = paramString;
  }
  
  public String getMultipleBillPayAccountsURL()
  {
    return this.Hz;
  }
  
  public void setCurrentAccount(Account paramAccount)
  {
    this.Hw = paramAccount;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetAccounts
 * JD-Core Version:    0.7.0.1
 */