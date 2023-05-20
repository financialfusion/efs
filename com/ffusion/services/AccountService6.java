package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Businesses;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface AccountService6
  extends AccountService5
{
  public abstract Businesses getBusinessesForAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService6
 * JD-Core Version:    0.7.0.1
 */