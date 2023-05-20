package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfers;
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

public class GetTransfers
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BankingAccounts");
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
    String str;
    if (localAccounts == null)
    {
      this.error = 1001;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      if (getTransfers(localHttpSession, localAccounts, localBanking)) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  protected boolean getTransfers(HttpSession paramHttpSession, Accounts paramAccounts, com.ffusion.services.Banking paramBanking)
  {
    this.error = 0;
    Transfers localTransfers = (Transfers)paramHttpSession.getAttribute("Transfers");
    if (this.reload)
    {
      localTransfers = null;
      paramHttpSession.removeAttribute("Transfers");
      this.reload = false;
    }
    if (localTransfers == null)
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localTransfers = new Transfers(localLocale);
      HashMap localHashMap = new HashMap();
      if (paramBanking != null) {
        localHashMap.put("SERVICE", paramBanking);
      }
      localHashMap.put("TRANSFERS", localTransfers);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      synchronized (this)
      {
        try
        {
          localTransfers = com.ffusion.csil.core.Banking.getTransfers(localSecureUser, paramAccounts, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
        }
      }
      if (this.error == 0) {
        paramHttpSession.setAttribute("Transfers", localTransfers);
      }
    }
    return this.error == 0;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetTransfers
 * JD-Core Version:    0.7.0.1
 */