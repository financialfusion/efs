package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHOffsetAccount
  extends BaseTask
  implements Task
{
  protected String offsetAccountsName = "ACHOffsetAccounts";
  protected String offsetAccountName = "ACHOffsetAccount";
  protected String offsetAccountID;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHOffsetAccounts localACHOffsetAccounts = (ACHOffsetAccounts)localHttpSession.getAttribute(this.offsetAccountsName);
    if (localACHOffsetAccounts == null)
    {
      this.error = 16013;
    }
    else
    {
      ACHOffsetAccount localACHOffsetAccount = localACHOffsetAccounts.getByID(this.offsetAccountID);
      if (localACHOffsetAccount == null)
      {
        this.error = 16014;
      }
      else
      {
        localHttpSession.setAttribute(this.offsetAccountName, localACHOffsetAccount);
        str = this.successURL;
      }
    }
    this.offsetAccountID = null;
    return str;
  }
  
  public void setOffsetAccountID(String paramString)
  {
    this.offsetAccountID = paramString.trim();
  }
  
  public void setOffsetAccountInSessionName(String paramString)
  {
    this.offsetAccountName = paramString.trim();
  }
  
  public final void setOffsetAccountsInSessionName(String paramString)
  {
    this.offsetAccountsName = paramString;
  }
  
  public final String getOffsetAccountsInSessionName()
  {
    return this.offsetAccountsName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetACHOffsetAccount
 * JD-Core Version:    0.7.0.1
 */