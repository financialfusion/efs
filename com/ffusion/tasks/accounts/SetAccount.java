package com.ffusion.tasks.accounts;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
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
  protected String accountsName = "Accounts";
  protected String accountName = "Account";
  protected String id;
  protected String number;
  protected String bankId;
  protected String routingNum;
  protected boolean isDefault;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.accountsName);
    if ((localAccounts != null) && (localAccounts.size() > 0))
    {
      Account localAccount = null;
      if (this.isDefault) {
        localAccount = (Account)localAccounts.get(0);
      } else if ((this.id != null) && (this.bankId != null) && (this.routingNum != null) && (!this.routingNum.trim().equals(""))) {
        localAccount = localAccounts.getByIDAndBankIDAndRoutingNum(this.id, this.bankId, this.routingNum);
      } else if ((this.number != null) && (this.bankId != null)) {
        localAccount = localAccounts.getByNumberAndBankID(this.number, this.bankId);
      } else if ((this.number != null) && (this.routingNum != null)) {
        localAccount = localAccounts.getByNumberAndRoutingNum(this.number, this.routingNum);
      } else if ((this.id != null) && (this.id.length() > 0)) {
        localAccount = localAccounts.getByID(this.id);
      } else {
        this.error = 18015;
      }
      if (this.error == 0) {
        if (localAccount != null)
        {
          localHttpSession.setAttribute(this.accountName, localAccount);
          str = this.successURL;
        }
        else
        {
          this.error = 18003;
        }
      }
    }
    else
    {
      this.error = 39;
    }
    this.id = null;
    this.bankId = null;
    this.routingNum = null;
    this.number = null;
    this.isDefault = false;
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setAccountName(String paramString)
  {
    this.accountName = paramString;
  }
  
  public String getAccountName()
  {
    return this.accountName;
  }
  
  public void setIsDefault(String paramString)
  {
    this.isDefault = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getIsDefault()
  {
    return String.valueOf(this.isDefault);
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setNumber(String paramString)
  {
    this.number = paramString;
  }
  
  public String getNumber()
  {
    return this.number;
  }
  
  public String getBankID()
  {
    return this.bankId;
  }
  
  public void setBankID(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.routingNum;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.routingNum = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.SetAccount
 * JD-Core Version:    0.7.0.1
 */