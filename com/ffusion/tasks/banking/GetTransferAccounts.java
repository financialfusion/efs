package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.AccountService;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransferAccounts
  extends ExtendedBaseTask
  implements Task
{
  protected boolean reload = false;
  protected String accountsName = "Accounts";
  protected String sessionName = "TransferAccounts";
  protected String serviceName = "com.ffusion.service.AccountService";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.accountsName);
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.sessionName);
    if (this.reload)
    {
      localAccounts2 = null;
      localHttpSession.removeAttribute(this.sessionName);
      this.reload = false;
    }
    if (localAccounts2 == null)
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      AccountService localAccountService = (AccountService)localHttpSession.getAttribute(this.serviceName);
      this.error = 0;
      try
      {
        HashMap localHashMap = null;
        localHashMap = new HashMap();
        if (localAccountService != null) {
          localHashMap.put("SERVICE", localAccountService);
        }
        localHashMap.put("ACCOUNTS", localAccounts1);
        this.error = 0;
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localAccounts2 = Banking.getTransferAccounts(localSecureUser, localHashMap);
        localAccounts2.setLocale(localLocale);
        Iterator localIterator = localAccounts2.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          if ((localAccount.getTypeValue() == 0) && ("1".equals(localAccount.getCoreAccount()))) {
            localIterator.remove();
          }
        }
        localHttpSession.setAttribute(this.sessionName, localAccounts2);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getSessionName()
  {
    return this.sessionName;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public String getAccountsName()
  {
    return this.accountsName;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetTransferAccounts
 * JD-Core Version:    0.7.0.1
 */