package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Accounts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountAddress
  extends BaseTask
  implements Task
{
  protected String _accountName = "Account";
  private String j0 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Account localAccount = (Account)localHttpSession.getAttribute(this._accountName);
    if (localAccount == null)
    {
      this.error = 18004;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        Accounts.getAccountAddress(localSecureUser, localAccount, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getAccountName()
  {
    return this._accountName;
  }
  
  public void setAccountName(String paramString)
  {
    this._accountName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetAccountAddress
 * JD-Core Version:    0.7.0.1
 */