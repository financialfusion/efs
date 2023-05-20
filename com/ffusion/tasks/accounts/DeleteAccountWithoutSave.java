package com.ffusion.tasks.accounts;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAccountWithoutSave
  extends BaseTask
  implements Task
{
  private String mQ;
  private String mP = "BackendAccounts";
  private String mO = "Accounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.mP);
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.mO);
    if ((localAccounts1 == null) || (localAccounts2 == null))
    {
      this.error = 18006;
      return this.taskErrorURL;
    }
    if ((this.mQ != null) && (this.mQ.length() > 0))
    {
      Account localAccount = localAccounts2.getByID(this.mQ);
      localAccounts1.add(localAccount);
      localAccounts2.removeByID(this.mQ);
    }
    return str;
  }
  
  public void setAccountId(String paramString)
  {
    this.mQ = paramString;
  }
  
  public void setBackendAccountKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.mP = paramString;
    }
  }
  
  public String getBackendAccountKey()
  {
    return this.mP;
  }
  
  public void setProfileAccountKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.mO = paramString;
    }
  }
  
  public String getProfileAccountKey()
  {
    return this.mO;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.DeleteAccountWithoutSave
 * JD-Core Version:    0.7.0.1
 */