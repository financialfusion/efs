package com.ffusion.services;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Banking
  extends SignOn
{
  public static final int ERROR_FROM_ACCOUNT_NOT_FOUND = 1000;
  public static final int ERROR_TO_ACCOUNT_NOT_FOUND = 1001;
  public static final int ERROR_CANNOT_XFER_FROM_ACCOUNT = 1002;
  public static final int ERROR_CANNOT_XFER_TO_ACCOUNT = 1003;
  public static final int ERROR_BOTH_XFER_ACCOUNTS_SAME = 1004;
  public static final int ERROR_TRANSACTIONS_NOT_SUPPORTED = 1005;
  public static final int ERROR_INSUFFICIENT_FUNDS = 1006;
  public static final int ERROR_AMOUNT_TOO_LARGE = 1007;
  public static final int ERROR_AMOUNT_TOO_SMALL = 1008;
  public static final int ERROR_DATE_TOO_SOON = 1009;
  public static final int ERROR_DATE_TOO_DISTANT = 1010;
  public static final int ERROR_INVALID_ACCOUNT = 1011;
  public static final int ERROR_ALREADY_COMMITTED = 1012;
  public static final int ERROR_INVALID_FREQUENCY = 1013;
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract int getAccounts(Accounts paramAccounts);
  
  public abstract int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2);
  
  public abstract int getTransactions(Account paramAccount);
  
  public abstract int getTransfers(Accounts paramAccounts, Transfers paramTransfers);
  
  public abstract int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers);
  
  public abstract int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer);
  
  public abstract int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap);
  
  public abstract int cancelTransfer(Transfer paramTransfer);
  
  public abstract int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer);
  
  public abstract int cancelRecTransfer(RecTransfer paramRecTransfer);
  
  public abstract int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer);
  
  public abstract int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer);
  
  public abstract int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap);
  
  public abstract int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking
 * JD-Core Version:    0.7.0.1
 */