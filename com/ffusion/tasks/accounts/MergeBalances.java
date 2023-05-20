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

public class MergeBalances
  extends BaseTask
  implements Task
{
  private String ns = "BankingAccounts";
  private String nr = "Accounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.ns);
    if (localAccounts1 == null)
    {
      this.error = 39;
      return super.getTaskErrorURL();
    }
    Accounts localAccounts2 = (Accounts)localHttpSession.getAttribute(this.nr);
    if (localAccounts2 == null)
    {
      this.error = 39;
      return super.getTaskErrorURL();
    }
    Account localAccount1 = null;
    Account localAccount2 = null;
    String str = localAccounts1.getFilter();
    localAccounts1.setFilter("All");
    localAccounts2.setFilter("All");
    Iterator localIterator = localAccounts1.iterator();
    while (localIterator.hasNext())
    {
      localAccount1 = (Account)localIterator.next();
      localAccount2 = localAccounts2.getByID(localAccount1.getID());
      if (localAccount2 != null)
      {
        localAccount1.setCurrentBalance(localAccount2.getCurrentBalance());
        localAccount1.setAvailableBalance(localAccount2.getAvailableBalance());
        localAccount1.setIntradayCurrentBalance(localAccount2.getIntradayCurrentBalance());
        localAccount1.setIntradayAvailableBalance(localAccount2.getIntradayAvailableBalance());
        localAccount1.putAll(localAccount2.getHash());
        for (int i = 0; i < Account.acctTransactionFilters.length; i++) {
          if (localAccount2.isFilterable(Account.acctTransactionFilters[i])) {
            localAccount1.setFilterable(Account.acctTransactionFilters[i]);
          }
        }
      }
    }
    localAccounts1.setFilter(str);
    return super.getSuccessURL();
  }
  
  public void setProfileAccountsName(String paramString)
  {
    if (paramString != null) {
      this.ns = paramString;
    }
  }
  
  public void setBackendAccountsName(String paramString)
  {
    if (paramString != null) {
      this.nr = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.MergeBalances
 * JD-Core Version:    0.7.0.1
 */