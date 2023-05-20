package com.ffusion.tasks.aggregation;

import com.ffusion.beans.aggregation.Account;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAccount
  extends BaseTask
  implements Task
{
  private String io = "AccountAggregationCollection";
  private String ip = "AggregatedAccount";
  private String iq;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.io);
    if (localAccounts != null)
    {
      Account localAccount = localAccounts.getByID(this.iq);
      if (localAccount != null)
      {
        localHttpSession.setAttribute(this.ip, localAccount);
        str = this.successURL;
      }
      else
      {
        this.error = 50;
      }
    }
    else
    {
      this.error = 39;
    }
    return str;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.io = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.io;
  }
  
  public void setAccountInSessionName(String paramString)
  {
    this.ip = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.ip;
  }
  
  public void setID(String paramString)
  {
    this.iq = paramString;
  }
  
  public String getID()
  {
    return this.iq;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.SetAccount
 * JD-Core Version:    0.7.0.1
 */