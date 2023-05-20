package com.ffusion.tasks.register;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRegisterAccountsData
  extends ExtendedBaseTask
  implements Task
{
  int E2 = 0;
  
  public SetRegisterAccountsData()
  {
    this.collectionSessionName = "BankingAccounts";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.collectionSessionName);
    if (localAccounts == null)
    {
      this.error = 20002;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    Iterator localIterator = localAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (RegisterUtil.isDebitAccount(localAccount.getTypeValue())) {
        localAccount.set("DEBIT", "true");
      } else {
        localAccount.set("DEBIT", "false");
      }
      if (RegisterUtil.isRegisterEnabled(localAccount)) {
        localAccount.set("REG_ENABLED", "true");
      } else {
        localAccount.set("REG_ENABLED", "false");
      }
      if (RegisterUtil.isRegisterDefault(localAccount)) {
        localAccount.set("REG_DEFAULT", "true");
      } else {
        localAccount.set("REG_DEFAULT", "false");
      }
      DateTime localDateTime = (DateTime)localAccount.get("REG_RETRIEVAL_DATE");
      if ((localDateTime != null) && (this.E2 != 0)) {
        localDateTime.add(6, this.E2 * -1);
      }
    }
    return this.successURL;
  }
  
  public void setOverlapDays(String paramString)
  {
    try
    {
      this.E2 = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getOverlapDays()
  {
    return String.valueOf(this.E2);
  }
  
  public int getOverlapDaysValue()
  {
    return this.E2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetRegisterAccountsData
 * JD-Core Version:    0.7.0.1
 */