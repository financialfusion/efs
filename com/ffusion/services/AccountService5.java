package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface AccountService5
  extends AccountService4
{
  public abstract ArrayList getRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void saveRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Account getAccountAddress(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountService5
 * JD-Core Version:    0.7.0.1
 */