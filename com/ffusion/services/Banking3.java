package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.PagingContext;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Banking3
  extends Banking2
{
  public static final int ERROR_SERVER_NOT_STARTED = 1020;
  
  public abstract FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException;
  
  public abstract void updateFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transactions getRecentTransactions(Account paramAccount, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException;
  
  public abstract int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException;
  
  public abstract AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException;
  
  public abstract AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException;
  
  public abstract ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getPagedPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getNextPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getNextCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking3
 * JD-Core Version:    0.7.0.1
 */