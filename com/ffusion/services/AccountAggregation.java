package com.ffusion.services;

import com.ffusion.beans.aggregation.Account;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.Institutions;
import java.util.HashMap;

public abstract interface AccountAggregation
  extends SignOn2
{
  public static final int SERVICE_ERROR_ACCOUNT_LOGIN_FAILED = 11000;
  public static final int SERVICE_ERROR_ACCOUNT_FORMAT_CHANGED = 11001;
  public static final int SERVICE_ERROR_ACCOUNT_NUMBER_ERROR = 11002;
  public static final int SERVICE_ERROR_UPDATE_ACCOUNT_TIMEOUT = 11003;
  public static final int SERVICE_ERROR_CONNECTION_ERROR = 11004;
  
  public abstract int initialize(String paramString);
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract int getInstitutions(Institutions paramInstitutions, Institution paramInstitution);
  
  public abstract int getInstitutionAccountTypes(Institution paramInstitution);
  
  public abstract int getRequiredAccountFields(Account paramAccount);
  
  public abstract int addAccount(Account paramAccount);
  
  public abstract int modifyAccount(Account paramAccount);
  
  public abstract int deleteAccount(Account paramAccount);
  
  public abstract int getAccounts(Accounts paramAccounts, HashMap paramHashMap);
  
  public abstract int refreshAccount(Account paramAccount);
  
  public abstract int getTransactions(Account paramAccount);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AccountAggregation
 * JD-Core Version:    0.7.0.1
 */