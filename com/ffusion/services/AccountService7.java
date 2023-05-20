package com.ffusion.services;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface AccountService7
  extends AccountService6
{
  public abstract void fillAccountCollection(Accounts paramAccounts, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService7
 * JD-Core Version:    0.7.0.1
 */