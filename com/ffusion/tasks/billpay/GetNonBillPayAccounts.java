package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetNonBillPayAccounts
  extends BaseTask
{
  protected String _nonBillPayAccountsName = "NonBillPayAccounts";
  protected String _accountsName = "BankingAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Account localAccount1 = null;
    Account localAccount2 = null;
    Accounts localAccounts1 = null;
    Accounts localAccounts2 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Iterator localIterator = null;
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localAccounts1 = (Accounts)localHttpSession.getAttribute(this._accountsName);
    if (localSecureUser == null)
    {
      this.error = 38;
    }
    else if (localAccounts1 == null)
    {
      this.error = 39;
    }
    else
    {
      localAccounts2 = new Accounts(localSecureUser.getLocale());
      localAccounts1.setFilter("All");
      if (!localAccounts1.isEmpty())
      {
        localIterator = localAccounts1.iterator();
        while (localIterator.hasNext())
        {
          localAccount2 = (Account)localIterator.next();
          if (isAccountEligibleForBillPay(localAccount2))
          {
            localAccount1 = localAccounts2.create(localAccount2.getBankID(), localAccount2.getID(), localAccount2.getNumber(), localAccount2.getTypeValue());
            localAccount1.set(localAccount2);
          }
        }
      }
      localHttpSession.setAttribute(this._nonBillPayAccountsName, localAccounts2);
    }
    return str;
  }
  
  public void setNonBillPayAccountsName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._nonBillPayAccountsName = paramString.trim();
    } else {
      this._nonBillPayAccountsName = "NonBillPayAccounts";
    }
  }
  
  public String getNonBillPayAccountsName()
  {
    return this._nonBillPayAccountsName;
  }
  
  public void setAccountsName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._accountsName = paramString.trim();
    } else {
      this._accountsName = "NonBillPayAccounts";
    }
  }
  
  public String getAccountsName()
  {
    return this._accountsName;
  }
  
  protected boolean isAccountEligibleForBillPay(Account paramAccount)
  {
    boolean bool = true;
    if ((paramAccount == null) || (paramAccount.isFilterable("BillPay")))
    {
      bool = false;
    }
    else
    {
      int i = paramAccount.getTypeValue();
      if ((i != 1) && (i != 2)) {
        bool = false;
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetNonBillPayAccounts
 * JD-Core Version:    0.7.0.1
 */