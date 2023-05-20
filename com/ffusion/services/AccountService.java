package com.ffusion.services;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;

public abstract interface AccountService
  extends SignOn2
{
  public static final int ERROR_INVALID_ACCOUNT = 18000;
  public static final int SERVICE_ERROR_INVALID_SIGNON = 18001;
  public static final int SERVICE_ERROR_NO_DATABASE_CONNECTION = 18002;
  public static final int SERVICE_ERROR_UNABLE_TO_COMPLETE_REQUEST = 18003;
  public static final int SERVICE_ERROR_INVALID_REQUEST = 18004;
  public static final int SERVICE_ERROR_NO_DATA_FOUND = 18005;
  
  public abstract int initialize(String paramString);
  
  public abstract int getAccounts(Accounts paramAccounts);
  
  public abstract int getAccount(Account paramAccount);
  
  public abstract int addAccount(Account paramAccount);
  
  public abstract int modifyAccount(Account paramAccount);
  
  public abstract int deleteAccount(Account paramAccount);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService
 * JD-Core Version:    0.7.0.1
 */