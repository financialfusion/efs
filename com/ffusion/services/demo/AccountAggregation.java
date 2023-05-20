package com.ffusion.services.demo;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.aggregation.Account;
import com.ffusion.beans.aggregation.AccountNVPairs;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.Institutions;
import com.ffusion.beans.aggregation.Transaction;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.util.XMLHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

public class AccountAggregation
  extends Service
  implements com.ffusion.services.AccountAggregation
{
  private boolean aN = false;
  private boolean aP = false;
  private Accounts aO;
  private Institutions aM;
  private AccountNVPairs aL;
  
  public int initialize(String paramString)
  {
    int i = super.initialize(paramString);
    return i;
  }
  
  public void setForceMustChangePassword(String paramString)
  {
    this.aN = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setForceInvalidPassword(String paramString)
  {
    this.aP = Boolean.valueOf(paramString).booleanValue();
  }
  
  public int getAccounts(Accounts paramAccounts, HashMap paramHashMap)
  {
    if (this.aO == null)
    {
      this.aO = new Accounts(paramAccounts.getLocale());
      jdMethod_for(true);
      if (this.aO.size() > 0)
      {
        GregorianCalendar localGregorianCalendar = new GregorianCalendar();
        int i = localGregorianCalendar.get(1);
        int j = localGregorianCalendar.get(2);
        Iterator localIterator = this.aO.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          Balance localBalance = localAccount.getCurrentBalance();
          DateTime localDateTime;
          if (localBalance != null)
          {
            localDateTime = localBalance.getDateValue();
            localDateTime.set(1, i);
            localDateTime.set(2, j);
          }
          localBalance = localAccount.getAvailableBalance();
          if (localBalance != null)
          {
            localDateTime = localBalance.getDateValue();
            localDateTime.set(1, i);
            localDateTime.set(2, j);
          }
        }
        paramAccounts.setXML(this.aO.getXML());
      }
      return 0;
    }
    return 1;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    if (this.aN) {
      this.errorCode = 102;
    }
    if (this.aP) {
      this.errorCode = 101;
    }
    this.aN = false;
    this.aP = false;
    return this.errorCode;
  }
  
  public int getInstitutions(Institutions paramInstitutions, Institution paramInstitution)
  {
    this.aM = paramInstitutions;
    jdMethod_for(false);
    paramInstitutions.setFilter("TYPE=" + paramInstitution.getType());
    this.aM = null;
    return 0;
  }
  
  public int getInstitutionAccountTypes(Institution paramInstitution)
  {
    return 0;
  }
  
  public int getRequiredAccountFields(Account paramAccount)
  {
    this.aL = paramAccount.getAccountNVPairs();
    jdMethod_for(false);
    return 0;
  }
  
  public int addAccount(Account paramAccount)
  {
    int i = 0;
    if (this.aO != null) {
      this.aO.add(paramAccount);
    } else {
      i = 1;
    }
    return i;
  }
  
  public int modifyAccount(Account paramAccount)
  {
    int i = 0;
    if (this.aO != null)
    {
      if ((paramAccount.getID() != null) && (paramAccount.getID().length() > 0))
      {
        Iterator localIterator = this.aO.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          if (localAccount.getID().equalsIgnoreCase(paramAccount.getID()))
          {
            this.aO.removeByID(paramAccount.getID());
            this.aO.add(paramAccount);
            break;
          }
        }
      }
    }
    else {
      i = 1;
    }
    return i;
  }
  
  public int deleteAccount(Account paramAccount)
  {
    int i = 0;
    if (this.aO != null)
    {
      if ((paramAccount.getID() != null) && (paramAccount.getID().length() > 0))
      {
        Iterator localIterator = this.aO.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          if (localAccount.getID().equalsIgnoreCase(paramAccount.getID()))
          {
            this.aO.removeByID(paramAccount.getID());
            break;
          }
        }
      }
    }
    else {
      i = 1;
    }
    return i;
  }
  
  public int refreshAccount(Account paramAccount)
  {
    if (paramAccount != null)
    {
      Balance localBalance = paramAccount.getCurrentBalance();
      if (localBalance != null)
      {
        Currency localCurrency = localBalance.getAmountValue();
        if (localCurrency != null)
        {
          double d = localCurrency.doubleValue() + 150.0D;
          localBalance.setAmount(String.valueOf(d));
          Date localDate = new Date();
          GregorianCalendar localGregorianCalendar = new GregorianCalendar();
          localGregorianCalendar.setTime(localDate);
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append(localGregorianCalendar.get(1) + "-");
          localStringBuffer.append(localGregorianCalendar.get(2) + 1 + "-");
          localStringBuffer.append(localGregorianCalendar.get(5) + " ");
          localStringBuffer.append(localGregorianCalendar.get(10) + ":");
          localStringBuffer.append(localGregorianCalendar.get(12) + ":");
          localStringBuffer.append(localGregorianCalendar.get(13) + ".0");
          paramAccount.set("AS_OF_DATE", localStringBuffer.toString());
        }
      }
    }
    return 0;
  }
  
  public int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return getTransactions(paramAccount);
  }
  
  public int getTransactions(Account paramAccount)
  {
    if (this.aO == null)
    {
      this.aO = new Accounts(paramAccount.getLocale());
      jdMethod_for(true);
    }
    Account localAccount = this.aO.getByID(paramAccount.getID());
    if (localAccount != null)
    {
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      int i = localGregorianCalendar.get(1);
      int j = localGregorianCalendar.get(2);
      Transactions localTransactions1 = localAccount.getTransactions();
      Transactions localTransactions2 = paramAccount.getTransactions();
      if ((localTransactions1 != null) && (localTransactions2 != null)) {
        for (int k = 0; k < localTransactions1.size(); k++)
        {
          Transaction localTransaction = (Transaction)localTransactions1.get(k);
          DateTime localDateTime = localTransaction.getDateValue();
          localDateTime.set(1, i);
          localDateTime.set(2, j);
          localTransactions2.add(localTransaction);
        }
      }
    }
    if ((paramAccount.getTransactions() != null) && (paramAccount.getTransactions().size() > 0)) {
      return 0;
    }
    return 0;
  }
  
  private void jdMethod_for(boolean paramBoolean)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramBoolean), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  protected class a
    extends XMLHandler
  {
    protected boolean jdField_int;
    
    public a(boolean paramBoolean)
    {
      this.jdField_int = paramBoolean;
    }
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("ACCOUNTS")) && (AccountAggregation.this.aO != null)) {
        AccountAggregation.this.aO.continueXMLParsing(getHandler(), this.jdField_int);
      }
      if ((paramString.equals("INSTITUTIONS")) && (AccountAggregation.this.aM != null)) {
        AccountAggregation.this.aM.continueXMLParsing(getHandler());
      }
      if ((paramString.equals("ACCOUNTNVPAIRLIST")) && (AccountAggregation.this.aL != null)) {
        AccountAggregation.this.aL.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.AccountAggregation
 * JD-Core Version:    0.7.0.1
 */