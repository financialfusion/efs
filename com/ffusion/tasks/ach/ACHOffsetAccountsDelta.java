package com.ffusion.tasks.ach;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ACHOffsetAccountsDelta
  extends BaseTask
  implements Task
{
  String z8;
  String z6;
  String z7;
  private String z5 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.z8);
    ACHOffsetAccounts localACHOffsetAccounts = (ACHOffsetAccounts)localHttpSession.getAttribute(this.z6);
    if (localAccounts1 != null)
    {
      Accounts localAccounts2 = (Accounts)localAccounts1.clone();
      if (localACHOffsetAccounts != null)
      {
        Iterator localIterator1 = localAccounts1.iterator();
        Account localAccount = null;
        while (localIterator1.hasNext())
        {
          localAccount = (Account)localIterator1.next();
          if (((localAccount.getTypeValue() < 1) || (localAccount.getTypeValue() >= 4)) && (localAccount.getTypeValue() != 16))
          {
            localAccounts2.remove(localAccount);
          }
          else
          {
            Iterator localIterator2 = localACHOffsetAccounts.iterator();
            while (localIterator2.hasNext())
            {
              ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)localIterator2.next();
              if ((localAccount.getNumber().equals(localACHOffsetAccount.getNumber())) && ((localAccount.getTypeValue() == localACHOffsetAccount.getTypeValue()) || ((localAccount.getTypeValue() == 16) && (localACHOffsetAccount.getTypeValue() == 4))) && (localAccount.getRoutingNum().equals(localACHOffsetAccount.getRoutingNum()))) {
                localAccounts2.remove(localAccount);
              }
            }
          }
        }
      }
      localHttpSession.setAttribute(this.z7, localAccounts2);
    }
    else
    {
      this.error = 0;
      str = this.taskErrorURL;
      this.z5 = this.taskErrorURL;
    }
    return str;
  }
  
  public void setAccountsPrimaryName(String paramString)
  {
    this.z8 = paramString;
  }
  
  public void setAccountsSecondaryName(String paramString)
  {
    this.z6 = paramString;
  }
  
  public void setAccountsDeltaName(String paramString)
  {
    this.z7 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ACHOffsetAccountsDelta
 * JD-Core Version:    0.7.0.1
 */