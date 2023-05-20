package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.util.XMLHandler;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

public class AccountService
  extends Service
  implements com.ffusion.services.AccountService
{
  private Accounts accounts;
  
  public int initialize(String paramString)
  {
    int i = 0;
    try
    {
      i = super.initialize(paramString);
      if (i == 0)
      {
        XMLHandler localXMLHandler = new XMLHandler();
        localXMLHandler.start(new a(), getXMLReader(this, paramString));
        jdMethod_int();
      }
    }
    catch (Throwable localThrowable)
    {
      i = 8;
    }
    return i;
  }
  
  private void jdMethod_int()
  {
    if (this.accounts != null)
    {
      Iterator localIterator = this.accounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        if (localAccount != null)
        {
          GregorianCalendar localGregorianCalendar = new GregorianCalendar();
          int i = localGregorianCalendar.get(1);
          int j = localGregorianCalendar.get(2);
          Transactions localTransactions = localAccount.getTransactions();
          for (int k = 0; k < localTransactions.size(); k++)
          {
            Transaction localTransaction = (Transaction)localTransactions.get(k);
            DateTime localDateTime = localTransaction.getDateValue();
            localDateTime.set(1, i);
            localDateTime.set(2, j);
            if (localDateTime.after(localGregorianCalendar)) {
              localDateTime.add(2, -1);
            }
          }
          localTransactions.setSortedBy("DATE");
        }
      }
    }
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    if (this.accounts != null)
    {
      Iterator localIterator = this.accounts.iterator();
      while (localIterator.hasNext()) {
        paramAccounts.add(localIterator.next());
      }
    }
    return 0;
  }
  
  public int getAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int addAccount(Account paramAccount)
  {
    return 0;
  }
  
  public int modifyAccount(Account paramAccount)
  {
    return 0;
  }
  
  public int deleteAccount(Account paramAccount)
  {
    return 0;
  }
  
  protected class a
    extends XMLHandler
  {
    protected a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("ACCOUNTS"))
      {
        if (AccountService.this.accounts == null) {
          AccountService.this.accounts = new Accounts(Locale.getDefault());
        }
        AccountService.this.accounts.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.AccountService
 * JD-Core Version:    0.7.0.1
 */