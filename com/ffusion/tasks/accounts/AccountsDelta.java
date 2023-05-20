package com.ffusion.tasks.accounts;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccountsDelta
  extends BaseTask
  implements Task
{
  private String jZ = "PrimaryAccounts";
  private String jX = "SecondaryAccounts";
  private String jY = "DeltaAccounts";
  private String jW = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.jW = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.jZ);
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.jX);
    if (localAccounts1 != null)
    {
      Accounts localAccounts3 = (Accounts)localAccounts1.clone();
      if (localAccounts2 != null)
      {
        Iterator localIterator1 = localAccounts1.iterator();
        Account localAccount1 = null;
        while (localIterator1.hasNext())
        {
          localAccount1 = (Account)localIterator1.next();
          Iterator localIterator2 = localAccounts2.iterator();
          while (localIterator2.hasNext())
          {
            Account localAccount2 = (Account)localIterator2.next();
            if (localAccount1.equals(localAccount2)) {
              localAccounts3.remove(localAccount1);
            }
          }
        }
      }
      localHttpSession.setAttribute(this.jY, localAccounts3);
    }
    else
    {
      this.error = 18006;
      this.jW = this.taskErrorURL;
      this.jW = this.taskErrorURL;
    }
    return this.jW;
  }
  
  public void setAccountsPrimaryName(String paramString)
  {
    this.jZ = paramString;
  }
  
  public void setAccountsSecondaryName(String paramString)
  {
    this.jX = paramString;
  }
  
  public void setAccountsDeltaName(String paramString)
  {
    this.jY = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.AccountsDelta
 * JD-Core Version:    0.7.0.1
 */