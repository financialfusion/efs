package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MergeExternalTransferAccounts
  extends BaseTask
  implements Task
{
  protected String accountsName = "Accounts";
  protected String extTransferAccountsName = "ExternalTransferAccounts";
  public static final String HIDE = "HIDE";
  public static final String SHOWACCOUNT = "0";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.accountsName);
    ExtTransferAccounts localExtTransferAccounts = (ExtTransferAccounts)localHttpSession.getAttribute(this.extTransferAccountsName);
    if (localAccounts == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else if (localExtTransferAccounts == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else
    {
      Iterator localIterator = localAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        ExtTransferAccount localExtTransferAccount = localExtTransferAccounts.getByAccountNumberAndTypeAndRTN(localAccount.getNumber(), localAccount.getTypeValue(), localAccount.getRoutingNum());
        localAccount.put("ExternalTransferACCOUNT", localExtTransferAccount);
      }
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setExtTransferAccountsName(String paramString)
  {
    this.extTransferAccountsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.MergeExternalTransferAccounts
 * JD-Core Version:    0.7.0.1
 */