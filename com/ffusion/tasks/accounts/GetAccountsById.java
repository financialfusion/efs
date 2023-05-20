package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountsById
  extends BaseTask
  implements Task
{
  boolean m9 = false;
  protected String serviceName = "com.ffusion.service.AccountService";
  String m7 = "Accounts";
  private Boolean m8 = Boolean.FALSE;
  private boolean m5 = false;
  private String m4 = null;
  int m6 = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.beans.accounts.Accounts localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.m7);
    if (this.m9)
    {
      localAccounts = null;
      localHttpSession.removeAttribute(this.m7);
      this.m9 = false;
      this.m5 = false;
    }
    if (localAccounts == null)
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      localAccounts = new com.ffusion.beans.accounts.Accounts(localLocale);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.error = 0;
      int i = 0;
      synchronized (this)
      {
        if ((!this.m8.booleanValue()) && (!this.m5))
        {
          this.m8 = Boolean.TRUE;
          i = 1;
        }
      }
      if (i != 0)
      {
        try
        {
          ??? = null;
          ??? = new HashMap();
          ((HashMap)???).put("ACCOUNTS", localAccounts);
          this.error = 0;
          localAccounts = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, this.m6, (HashMap)???);
          localHttpSession.setAttribute(this.m7, localAccounts);
          localHttpSession.setAttribute("OriginalAccounts", localAccounts.clone());
          this.m4 = this.successURL;
          this.m5 = true;
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
          this.m4 = this.serviceErrorURL;
        }
        this.m8 = Boolean.FALSE;
      }
      else
      {
        while ((!this.m5) && (this.m8.booleanValue() == true))
        {
          Thread.currentThread();
          Thread.yield();
        }
        str = this.m4;
      }
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.m7 = paramString;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.m9 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDirectoryId(String paramString)
  {
    try
    {
      this.m6 = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetAccountsById
 * JD-Core Version:    0.7.0.1
 */