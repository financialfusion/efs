package com.ffusion.services.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.TreasuryDirect;
import com.ffusion.treasurydirect.TDException;
import com.ffusion.treasurydirect.TreasuryDirectAdapter;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class TreasuryDirectService
  implements TreasuryDirect
{
  public void initialize(HashMap paramHashMap)
    throws TDException
  {
    TreasuryDirectAdapter.initialize(paramHashMap);
  }
  
  public void addSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    TreasuryDirectAdapter.addSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
  }
  
  public void deleteSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    TreasuryDirectAdapter.deleteSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
  }
  
  public void deleteMasterAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    TreasuryDirectAdapter.deleteMasterAccount(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
  }
  
  public Accounts getSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    Accounts localAccounts = null;
    localAccounts = TreasuryDirectAdapter.getSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    return localAccounts;
  }
  
  public void modifySubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    TreasuryDirectAdapter.modifySubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
  }
  
  public Accounts filterAvailableSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    Accounts localAccounts = null;
    localAccounts = TreasuryDirectAdapter.filterAvailableSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    return localAccounts;
  }
  
  public int getNumMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    return TreasuryDirectAdapter.getNumMasterAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
  }
  
  public int getNumSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    return TreasuryDirectAdapter.getNumSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
  }
  
  public Accounts filterNonSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    return TreasuryDirectAdapter.filterNonSubAccounts(paramSecureUser, paramBusiness, paramAccounts, paramHashMap);
  }
  
  public HashMap getMasterSubStatistics(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    return TreasuryDirectAdapter.getMasterSubStatistics(paramSecureUser, paramBusiness, paramAccounts, paramHashMap);
  }
  
  public Accounts getMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    return TreasuryDirectAdapter.getMasterAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
  }
  
  public Account getSubAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount1, Account paramAccount2, HashMap paramHashMap)
    throws TDException
  {
    return TreasuryDirectAdapter.getSubAccount(paramSecureUser, paramBusiness, paramAccount1, paramAccount2, paramHashMap);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws TDException
  {
    Locale localLocale = paramSecureUser.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Accounts localAccounts = null;
      Reporting.calculateDateRange(paramSecureUser, localAccounts, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      a(paramReportCriteria, localLocale);
      localReportResult = (ReportResult)TreasuryDirectAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      String str = "Unable to calculate the date ranges. CB Non-zero Balance Report cannot be run.";
      DebugLog.log(str);
      throw new TDException(80000, localCSILException);
    }
    return localReportResult;
  }
  
  private void a(ReportCriteria paramReportCriteria, Locale paramLocale)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str1 = localProperties.getProperty("Display");
    if (str1.equals("DisplaySummaryOnly")) {
      paramReportCriteria.setDisplayValue("Display", ReportConsts.getText(10603, paramLocale));
    } else if (str1.equals("DisplaySummaryAndDetail")) {
      paramReportCriteria.setDisplayValue("Display", ReportConsts.getText(10604, paramLocale));
    }
    String str2 = localProperties.getProperty("Rolled-up NZB Accounts");
    if (str2.equals("Include")) {
      paramReportCriteria.setDisplayValue("Rolled-up NZB Accounts", ReportConsts.getText(10606, paramLocale));
    } else if (str2.equals("Exclude")) {
      paramReportCriteria.setDisplayValue("Rolled-up NZB Accounts", ReportConsts.getText(10607, paramLocale));
    }
    paramReportCriteria.setHiddenSearchCriterion("Affiliate Bank", true);
    paramReportCriteria.setHiddenSearchCriterion("Business", true);
    paramReportCriteria.setHiddenSearchCriterion("DataClassification", true);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.treasurydirect.TreasuryDirectService
 * JD-Core Version:    0.7.0.1
 */