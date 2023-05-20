package com.ffusion.tasks.banking;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateTransferAccountInfo
  extends BaseTask
{
  private String aP8 = "BankingAccounts";
  private String aP6 = "Transfers";
  private Accounts aP7 = null;
  private Transfers aP5 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    str1 = u(localHttpSession);
    if (this.error == 0)
    {
      Transfer localTransfer = null;
      String str2 = null;
      String str3 = null;
      Account localAccount1 = null;
      Account localAccount2 = null;
      Iterator localIterator = this.aP5.iterator();
      while (localIterator.hasNext())
      {
        localTransfer = (Transfer)localIterator.next();
        str2 = localTransfer.getFromAccountID();
        str3 = localTransfer.getToAccountID();
        localAccount1 = this.aP7.getByID(str2);
        localAccount2 = this.aP7.getByID(str3);
        Account localAccount3;
        if (localAccount1 != null)
        {
          localAccount3 = new Account();
          localAccount3.setXML(localAccount1.getXML());
          localTransfer.setFromAccount(localAccount3);
        }
        if (localAccount2 != null)
        {
          localAccount3 = new Account();
          localAccount3.setXML(localAccount2.getXML());
          localTransfer.setToAccount(localAccount3);
        }
      }
    }
    return str1;
  }
  
  private String u(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    int i = 1;
    if (i != 0)
    {
      this.aP7 = ((Accounts)paramHttpSession.getAttribute(this.aP8));
      if (this.aP7 == null)
      {
        this.error = 1001;
        str = this.taskErrorURL;
        i = 0;
      }
    }
    if (i != 0)
    {
      this.aP5 = ((Transfers)paramHttpSession.getAttribute(this.aP6));
      if (this.aP5 == null)
      {
        this.error = 1003;
        str = this.taskErrorURL;
        i = 0;
      }
    }
    return str;
  }
  
  public void setAccountsSessionName(String paramString)
  {
    String str = paramString == null ? null : paramString.trim();
    if ((str == null) || (str.length() == 0)) {
      this.aP8 = "BankingAccounts";
    } else {
      this.aP8 = str;
    }
  }
  
  public void setTransfersSessionName(String paramString)
  {
    String str = paramString == null ? null : paramString.trim();
    if ((str == null) || (str.length() == 0)) {
      this.aP6 = "Transfers";
    } else {
      this.aP6 = str;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.UpdateTransferAccountInfo
 * JD-Core Version:    0.7.0.1
 */