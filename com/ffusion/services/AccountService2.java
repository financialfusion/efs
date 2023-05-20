package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;

public abstract interface AccountService2
  extends AccountService
{
  public static final int SERVICE_ERROR_INVALID_DIRECTORY_ID = 18006;
  
  public abstract int getAccounts(Accounts paramAccounts, Account paramAccount);
  
  public abstract int getAccountsById(Accounts paramAccounts, int paramInt);
  
  public abstract int getAccountsByBusinessEmployee(Accounts paramAccounts, SecureUser paramSecureUser);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService2
 * JD-Core Version:    0.7.0.1
 */