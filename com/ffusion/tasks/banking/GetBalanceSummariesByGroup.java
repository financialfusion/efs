package com.ffusion.tasks.banking;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FX;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBalanceSummariesByGroup
  extends BaseTask
  implements Task
{
  String yl = "BankingAccounts";
  String ym = "BalanceSummmariesByGroup";
  
  public void setAccountsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.yl = "BankingAccounts";
    } else {
      this.yl = paramString;
    }
  }
  
  public String getAccountsName()
  {
    return this.yl;
  }
  
  public void setBalanceSummariesByGroup(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.ym = "BalanceSummmariesByGroup";
    } else {
      this.ym = paramString;
    }
  }
  
  public String getBalanceSummariesByGroup()
  {
    return this.ym;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts1 = (Accounts)localHttpSession.getAttribute(this.yl);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccounts1 == null)
    {
      this.error = 1002;
    }
    else
    {
      this.error = 0;
      Accounts localAccounts2 = new Accounts(localSecureUser.getLocale());
      localAccounts2.setSecureUser(localSecureUser);
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      BigDecimal localBigDecimal3 = null;
      BigDecimal localBigDecimal4 = null;
      Iterator localIterator = null;
      Account localAccount1 = null;
      Account localAccount2 = null;
      Balance localBalance1 = null;
      Balance localBalance2 = null;
      String str2 = localAccounts1.getFilter();
      for (int i = 1; i < 5; i++)
      {
        localAccounts1.setFilter("ACCOUNTGROUP=" + i);
        localIterator = localAccounts1.iterator();
        int j = 0;
        localBigDecimal1 = new BigDecimal(0.0D);
        localBigDecimal2 = new BigDecimal(0.0D);
        localBigDecimal3 = new BigDecimal(0.0D);
        localBigDecimal4 = new BigDecimal(0.0D);
        localAccount1 = null;
        while (localIterator.hasNext())
        {
          j++;
          localAccount1 = (Account)localIterator.next();
          if (localAccount1.getCurrentBalance() != null)
          {
            localBigDecimal3 = localAccount1.getCurrentBalance().getAmountValue().getAmountValue();
            localBigDecimal4 = localAccount1.getAvailableBalance().getAmountValue().getAmountValue();
            localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
            localBigDecimal2 = localBigDecimal2.add(localBigDecimal4);
          }
        }
        if (j > 0)
        {
          localAccount2 = localAccounts2.create(Integer.toString(j));
          localAccount2.setAccountGroup(i);
          localBalance1 = new Balance();
          localBalance2 = new Balance();
          HashMap localHashMap = null;
          Currency localCurrency1 = new Currency(localBigDecimal1, localAccount1.getCurrencyCode(), Locale.getDefault());
          Currency localCurrency2 = new Currency(localBigDecimal2, localAccount1.getCurrencyCode(), Locale.getDefault());
          try
          {
            if (!localSecureUser.getBaseCurrency().equals(localCurrency1.getCurrencyCode())) {
              localCurrency1 = FX.convertToBaseCurrency(localSecureUser, localCurrency1, localHashMap);
            }
            localBalance1.setAmount(localCurrency1);
            localAccount2.setCurrentBalance(localBalance1);
            if (!localSecureUser.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
              localCurrency2 = FX.convertToBaseCurrency(localSecureUser, localCurrency2, localHashMap);
            }
            localBalance2.setAmount(localCurrency2);
            localAccount2.setAvailableBalance(localBalance2);
          }
          catch (CSILException localCSILException)
          {
            this.error = 1002;
          }
        }
      }
      localAccounts1.setFilter(str2);
      localHttpSession.setAttribute(this.ym, localAccounts2);
      str1 = this.successURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetBalanceSummariesByGroup
 * JD-Core Version:    0.7.0.1
 */