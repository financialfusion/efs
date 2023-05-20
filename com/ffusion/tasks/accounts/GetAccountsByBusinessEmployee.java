package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountsByBusinessEmployee
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String accountsName = "Accounts";
  private Boolean jV = Boolean.FALSE;
  private boolean jU = false;
  private String jT = this.taskErrorURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.beans.accounts.Accounts localAccounts = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.accountsName);
    if (this.reload)
    {
      localAccounts = null;
      localHttpSession.removeAttribute(this.accountsName);
      this.reload = false;
      this.jU = false;
    }
    if (localAccounts == null)
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      localAccounts = new com.ffusion.beans.accounts.Accounts(localLocale);
      int i = 0;
      this.error = 0;
      synchronized (this)
      {
        if ((!this.jV.booleanValue()) && (!this.jU))
        {
          this.jV = Boolean.TRUE;
          i = 1;
        }
      }
      if (i != 0)
      {
        try
        {
          ??? = null;
          this.error = 0;
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          localAccounts = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(localSecureUser, ???);
          localHttpSession.setAttribute(this.accountsName, localAccounts);
          this.jT = this.successURL;
          this.jU = true;
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
          this.jT = this.serviceErrorURL;
        }
        finally
        {
          this.jV = Boolean.FALSE;
        }
      }
      else
      {
        long l1 = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
        long l2 = System.currentTimeMillis();
        while ((!this.jU) && (this.jV.booleanValue() == true))
        {
          if (System.currentTimeMillis() - l2 > l1)
          {
            if (this.error != 0) {
              break;
            }
            this.error = 1;
            break;
          }
          try
          {
            Thread.sleep(2000L);
          }
          catch (Exception localException) {}
        }
        str = this.jT;
      }
    }
    return str;
  }
  
  public String getAccountsName()
  {
    return this.accountsName;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetAccountsByBusinessEmployee
 * JD-Core Version:    0.7.0.1
 */