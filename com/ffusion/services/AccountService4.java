package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface AccountService4
  extends AccountService3
{
  public abstract Accounts searchAccounts(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService4
 * JD-Core Version:    0.7.0.1
 */