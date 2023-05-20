package com.ffusion.tasks.banking;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMostRecentSummary
  extends BaseTask
  implements Task
{
  String xE = "Account";
  String xD = "AccountSummaries";
  String xC = "AccountSummary";
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xE = "Account";
    } else {
      this.xE = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.xE;
  }
  
  public void setSummariesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xD = "AccountSummaries";
    } else {
      this.xD = paramString;
    }
  }
  
  public String getSummariesName()
  {
    return this.xD;
  }
  
  public void setSummaryName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xC = "AccountSummary";
    } else {
      this.xC = paramString;
    }
  }
  
  public String getSummaryName()
  {
    return this.xC;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.xE);
    AccountSummaries localAccountSummaries = (AccountSummaries)localHttpSession.getAttribute(this.xD);
    if (localAccount == null) {
      localAccount = (Account)paramHttpServletRequest.getAttribute(this.xE);
    }
    if (localAccount == null)
    {
      this.error = 1002;
    }
    else if (localAccountSummaries == null)
    {
      this.error = 51;
    }
    else
    {
      String str2 = localAccount.getNumber();
      String str3 = localAccount.getBankID();
      Object localObject = null;
      if ((str2 != null) && (str3 != null)) {
        for (int i = 0; i < localAccountSummaries.size(); i++)
        {
          AccountSummary localAccountSummary = (AccountSummary)localAccountSummaries.get(i);
          if ((str2.equals(localAccountSummary.getAccountNumber())) && (str3.equals(localAccountSummary.getBankID())))
          {
            localObject = localAccountSummary;
            break;
          }
        }
      }
      localHttpSession.setAttribute(this.xC, localObject);
      str1 = this.successURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetMostRecentSummary
 * JD-Core Version:    0.7.0.1
 */