package com.ffusion.tasks.register;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.billpay.Task;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsRegisterAccount
  extends ExtendedBaseTask
  implements Task
{
  public IsRegisterAccount()
  {
    this.beanSessionName = "RegisterEnabled";
    this.collectionSessionName = "BillPayAccounts";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.collectionSessionName);
    String str1 = this.successURL;
    String str2 = "false";
    Account localAccount = null;
    if (localAccounts == null)
    {
      this.error = 2005;
      str1 = this.taskErrorURL;
    }
    else
    {
      if ((this.id == null) || (this.id.length() == 0)) {
        localAccount = (Account)localAccounts.get(0);
      } else {
        localAccount = localAccounts.getByID(this.id);
      }
      if (localAccount == null)
      {
        this.error = 2007;
        str1 = this.taskErrorURL;
      }
      else
      {
        String str3 = (String)localAccount.get("REG_ENABLED");
        if ((str3 != null) && (str3.equalsIgnoreCase("true"))) {
          str2 = "true";
        }
      }
    }
    localHttpSession.setAttribute(this.beanSessionName, str2);
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.IsRegisterAccount
 * JD-Core Version:    0.7.0.1
 */