package com.ffusion.tasks.accounts;

import com.ffusion.beans.Balance;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountKey;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.banking.Task;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FillAccountBalances
  extends BaseTask
  implements Task
{
  String ei;
  String eh;
  String ej;
  String eg;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.ei);
    AccountSummaries localAccountSummaries = (AccountSummaries)localHttpSession.getAttribute(this.ej);
    AccountHistories localAccountHistories = (AccountHistories)localHttpSession.getAttribute(this.eh);
    if (localAccounts == null)
    {
      this.error = 1001;
      return this.taskErrorURL;
    }
    for (int i = 0; i < localAccounts.size(); i++)
    {
      Account localAccount = (Account)localAccounts.get(i);
      AccountKey localAccountKey1 = new AccountKey(localAccount.getID(), localAccount.getBankID(), localAccount.getRoutingNum());
      Object localObject1 = null;
      Object localObject3;
      for (int j = 0; j < localAccountSummaries.size(); j++)
      {
        AccountSummary localAccountSummary = (AccountSummary)localAccountSummaries.get(j);
        localObject3 = new AccountKey(localAccountSummary.getAccountID(), localAccountSummary.getBankID(), localAccountSummary.getRoutingNumber());
        if (localAccountKey1.equals(localObject3))
        {
          localObject1 = localAccountSummary;
          break;
        }
      }
      Object localObject2 = null;
      for (int k = 0; k < localAccountHistories.size(); k++)
      {
        localObject3 = (AccountHistory)localAccountHistories.get(k);
        AccountKey localAccountKey2 = new AccountKey(((AccountHistory)localObject3).getAccountID(), ((AccountHistory)localObject3).getBankID(), ((AccountHistory)localObject3).getRoutingNumber());
        if (localAccountKey1.equals(localAccountKey2))
        {
          localObject2 = localObject3;
          break;
        }
      }
      fillAccountBalances(localAccount, localObject1, localObject2);
    }
    str = this.successURL;
    return str;
  }
  
  public void setSummariesName(String paramString)
  {
    this.ej = paramString;
  }
  
  public String getSummariesName()
  {
    return this.ej;
  }
  
  public void setHistoriesName(String paramString)
  {
    this.eh = paramString;
  }
  
  public String getHistoriesName()
  {
    return this.eh;
  }
  
  public String getAccountsName()
  {
    return this.ei;
  }
  
  public void setAccountsName(String paramString)
  {
    this.ei = paramString;
  }
  
  protected void fillAccountBalances(Account paramAccount, AccountSummary paramAccountSummary, AccountHistory paramAccountHistory)
  {
    if (this.eg.equals("P")) {
      fillAccountBalancesFromPreviousDayData(paramAccount, paramAccountSummary, paramAccountHistory);
    } else if (this.eg.equals("I")) {
      fillAccountBalancesFromIntraDayData(paramAccount, paramAccountSummary, paramAccountHistory);
    }
  }
  
  protected void fillAccountBalancesFromIntraDayData(Account paramAccount, AccountSummary paramAccountSummary, AccountHistory paramAccountHistory)
  {
    int i = Account.getAccountSystemTypeFromGroup(paramAccount.getAccountGroup());
    int j = paramAccount.getTypeValue();
    Balance localBalance1;
    Balance localBalance2;
    if ((i == 1) || (j == 12))
    {
      localBalance1 = null;
      localBalance2 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(paramAccountHistory.getCurrentLedger());
      }
      else if ((paramAccountSummary != null) && ((paramAccountSummary instanceof DepositAcctSummary)) && (((DepositAcctSummary)paramAccountSummary).getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(((DepositAcctSummary)paramAccountSummary).getCurrentLedger());
      }
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentAvail() != null))
      {
        localBalance2 = new Balance(paramAccount.getLocale());
        localBalance2.setAmount(paramAccountHistory.getCurrentAvail());
      }
      else if ((paramAccountSummary != null) && ((paramAccountSummary instanceof DepositAcctSummary)) && (((DepositAcctSummary)paramAccountSummary).getCurrentAvailBal() != null))
      {
        localBalance2 = new Balance(paramAccount.getLocale());
        localBalance2.setAmount(((DepositAcctSummary)paramAccountSummary).getCurrentAvailBal());
      }
      if (localBalance1 != null) {
        paramAccount.setIntradayCurrentBalance(localBalance1);
      }
      if (localBalance2 != null) {
        paramAccount.setIntradayAvailableBalance(localBalance2);
      }
    }
    else if (i == 2)
    {
      localBalance1 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(paramAccountHistory.getCurrentLedger());
      }
      else if ((paramAccountSummary != null) && (((AssetAcctSummary)paramAccountSummary).getMarketValue() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(((AssetAcctSummary)paramAccountSummary).getMarketValue());
      }
      if (localBalance1 != null) {
        paramAccount.setIntradayCurrentBalance(localBalance1);
      }
    }
    else if (i == 4)
    {
      localBalance1 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(paramAccountHistory.getCurrentLedger());
      }
      else if ((paramAccountSummary != null) && (((CreditCardAcctSummary)paramAccountSummary).getCurrentBalance() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(((CreditCardAcctSummary)paramAccountSummary).getCurrentBalance());
      }
      if (localBalance1 != null) {
        paramAccount.setIntradayCurrentBalance(localBalance1);
      }
    }
    else if (i == 3)
    {
      localBalance1 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(paramAccountHistory.getCurrentLedger());
      }
      else if ((paramAccountSummary != null) && (((LoanAcctSummary)paramAccountSummary).getCurrentBalance() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(((LoanAcctSummary)paramAccountSummary).getCurrentBalance());
      }
      if (localBalance1 != null) {
        paramAccount.setIntradayCurrentBalance(localBalance1);
      }
    }
    else if (i == 99)
    {
      localBalance1 = null;
      localBalance2 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(paramAccountHistory.getCurrentLedger());
      }
      else if ((paramAccountSummary != null) && ((paramAccountSummary instanceof DepositAcctSummary)) && (((DepositAcctSummary)paramAccountSummary).getCurrentLedger() != null))
      {
        localBalance1 = new Balance(paramAccount.getLocale());
        localBalance1.setAmount(((DepositAcctSummary)paramAccountSummary).getCurrentLedger());
      }
      if ((paramAccountHistory != null) && (paramAccountHistory.getCurrentAvail() != null))
      {
        localBalance2 = new Balance(paramAccount.getLocale());
        localBalance2.setAmount(paramAccountHistory.getCurrentAvail());
      }
      else if ((paramAccountSummary != null) && ((paramAccountSummary instanceof DepositAcctSummary)) && (((DepositAcctSummary)paramAccountSummary).getCurrentAvailBal() != null))
      {
        localBalance2 = new Balance(paramAccount.getLocale());
        localBalance2.setAmount(((DepositAcctSummary)paramAccountSummary).getCurrentAvailBal());
      }
      if (localBalance1 != null) {
        paramAccount.setIntradayCurrentBalance(localBalance1);
      }
      if (localBalance2 != null) {
        paramAccount.setIntradayAvailableBalance(localBalance2);
      }
    }
  }
  
  protected void fillAccountBalancesFromPreviousDayData(Account paramAccount, AccountSummary paramAccountSummary, AccountHistory paramAccountHistory)
  {
    int i = Account.getAccountSystemTypeFromGroup(paramAccount.getAccountGroup());
    int j = paramAccount.getTypeValue();
    Balance localBalance;
    if ((i == 1) || (j == 12))
    {
      localBalance = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(paramAccountHistory.getClosingLedger());
      }
      else if ((paramAccountSummary != null) && ((paramAccountSummary instanceof DepositAcctSummary)) && (((DepositAcctSummary)paramAccountSummary).getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(((DepositAcctSummary)paramAccountSummary).getClosingLedger());
      }
      if (localBalance != null) {
        paramAccount.setClosingBalance(localBalance);
      }
    }
    else if (i == 2)
    {
      localBalance = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(paramAccountHistory.getClosingLedger());
      }
      else if ((paramAccountSummary != null) && (((AssetAcctSummary)paramAccountSummary).getMarketValue() != null))
      {
        localBalance.setAmount(((AssetAcctSummary)paramAccountSummary).getMarketValue());
      }
      if (localBalance != null) {
        paramAccount.setClosingBalance(localBalance);
      }
    }
    else if (i == 4)
    {
      localBalance = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(paramAccountHistory.getClosingLedger());
        paramAccount.setClosingBalance(localBalance);
      }
    }
    else if (i == 3)
    {
      localBalance = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(paramAccountHistory.getClosingLedger());
        paramAccount.setClosingBalance(localBalance);
      }
    }
    else if (i == 99)
    {
      localBalance = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(paramAccountHistory.getClosingLedger());
      }
      else if ((paramAccountSummary != null) && ((paramAccountSummary instanceof DepositAcctSummary)) && (((DepositAcctSummary)paramAccountSummary).getClosingLedger() != null))
      {
        localBalance = new Balance(paramAccount.getLocale());
        localBalance.setAmount(((DepositAcctSummary)paramAccountSummary).getClosingLedger());
      }
      if (localBalance != null) {
        paramAccount.setClosingBalance(localBalance);
      }
    }
  }
  
  public String getDataClassification()
  {
    return this.eg;
  }
  
  public void setDataClassification(String paramString)
  {
    this.eg = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.FillAccountBalances
 * JD-Core Version:    0.7.0.1
 */