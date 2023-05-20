package com.ffusion.tasks.cashcon;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.cashcon.CashConAccount;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CashConAccountsDelta
  extends BaseTask
  implements Task
{
  String zS;
  String zQ;
  String zR;
  private String zP = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.zS);
    CashConAccounts localCashConAccounts = (CashConAccounts)localHttpSession.getAttribute(this.zQ);
    if (localAccounts1 != null)
    {
      Accounts localAccounts2 = (Accounts)localAccounts1.clone();
      if (localCashConAccounts != null)
      {
        Iterator localIterator1 = localAccounts1.iterator();
        Account localAccount = null;
        while (localIterator1.hasNext())
        {
          localAccount = (Account)localIterator1.next();
          if ((localAccount.getTypeValue() < 1) || (localAccount.getTypeValue() > 2))
          {
            localAccounts2.remove(localAccount);
          }
          else
          {
            Iterator localIterator2 = localCashConAccounts.iterator();
            while (localIterator2.hasNext())
            {
              CashConAccount localCashConAccount = (CashConAccount)localIterator2.next();
              if ((localAccount.getNumber().equals(localCashConAccount.getNumber())) && (localAccount.getTypeValue() == localCashConAccount.getType()) && (localAccount.getRoutingNum().equals(localCashConAccount.getRoutingNumber()))) {
                localAccounts2.remove(localAccount);
              }
            }
          }
        }
        localIterator1 = null;
      }
      localHttpSession.setAttribute(this.zR, localAccounts2);
    }
    else
    {
      this.error = 0;
      str = this.taskErrorURL;
      this.zP = this.taskErrorURL;
    }
    return str;
  }
  
  public void setAccountsPrimaryName(String paramString)
  {
    this.zS = paramString;
  }
  
  public void setAccountsSecondaryName(String paramString)
  {
    this.zQ = paramString;
  }
  
  public void setAccountsDeltaName(String paramString)
  {
    this.zR = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.CashConAccountsDelta
 * JD-Core Version:    0.7.0.1
 */