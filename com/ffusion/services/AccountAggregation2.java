package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.aggregation.Account;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.services.aggregation.AggregationException;
import com.ffusion.util.beans.PagingContext;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface AccountAggregation2
  extends AccountAggregation
{
  public abstract void initialize(HashMap paramHashMap);
  
  public abstract boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws AggregationException;
  
  public abstract int addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws AggregationException;
  
  public abstract int deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws AggregationException;
  
  public abstract AccountSummaries getSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws AggregationException;
  
  public abstract Transactions getPagedTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws AggregationException;
  
  public abstract Transactions getNextTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws AggregationException;
  
  public abstract Transactions getPreviousTransactions(SecureUser paramSecureUser, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws AggregationException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountAggregation2
 * JD-Core Version:    0.7.0.1
 */