package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface AccountService3
{
  public static final int ERROR_INVALID_ACCOUNT = 18000;
  public static final int SERVICE_ERROR_INVALID_SIGNON = 18001;
  public static final int SERVICE_ERROR_NO_DATABASE_CONNECTION = 18002;
  public static final int SERVICE_ERROR_UNABLE_TO_COMPLETE_REQUEST = 18003;
  public static final int SERVICE_ERROR_INVALID_REQUEST = 18004;
  public static final int SERVICE_ERROR_NO_DATA_FOUND = 18005;
  public static final int SERVICE_ERROR_INVALID_DIRECTORY_ID = 18006;
  
  public abstract void initialize()
    throws ProfileException;
  
  public abstract Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Account getAccount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyAccountById(SecureUser paramSecureUser, int paramInt, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteAccount(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Accounts mergeAccounts(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Accounts getAccountsById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void addAccounts(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService3
 * JD-Core Version:    0.7.0.1
 */