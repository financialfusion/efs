package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.treasurydirect.TDException;
import java.util.HashMap;

public abstract interface TreasuryDirect
{
  public abstract void initialize(HashMap paramHashMap)
    throws TDException;
  
  public abstract void addSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException;
  
  public abstract void deleteSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException;
  
  public abstract void deleteMasterAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException;
  
  public abstract Accounts getSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException;
  
  public abstract void modifySubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException;
  
  public abstract Accounts filterAvailableSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException;
  
  public abstract int getNumMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException;
  
  public abstract int getNumSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException;
  
  public abstract Accounts filterNonSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException;
  
  public abstract HashMap getMasterSubStatistics(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException;
  
  public abstract Accounts getMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException;
  
  public abstract Account getSubAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount1, Account paramAccount2, HashMap paramHashMap)
    throws TDException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws TDException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.TreasuryDirect
 * JD-Core Version:    0.7.0.1
 */