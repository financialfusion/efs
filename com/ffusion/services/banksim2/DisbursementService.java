package com.ffusion.services.banksim2;

import com.ffusion.banksim.BankSim;
import com.ffusion.banksim.interfaces.BSException;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.Banking3;
import com.ffusion.services.Disbursements4;
import com.ffusion.services.disbursements.DisbException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DisbursementService
  implements Disbursements4
{
  public static final Locale currentLocale = ;
  
  public void initialize()
    throws DisbException
  {}
  
  public DisbursementAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getAccounts";
    Accounts localAccounts = null;
    AccountService3 localAccountService3 = (AccountService3)paramHashMap.get("SERVICE");
    if (localAccountService3 == null) {
      throw new DisbException(str, 609, "Accounts service must be in the extra hashmap");
    }
    try
    {
      localAccounts = localAccountService3.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log("Accounts could not be retrieved.");
      throw new DisbException(str, 610, localProfileException);
    }
    DisbursementAccounts localDisbursementAccounts = new DisbursementAccounts();
    for (int i = 0; i < localAccounts.size(); i++)
    {
      Account localAccount = (Account)localAccounts.get(i);
      if (localAccount.getTypeValue() == 1) {
        localDisbursementAccounts.add(a((Account)localAccounts.get(i)));
      }
    }
    return localDisbursementAccounts;
  }
  
  public DisbursementSummaries getSummaries(DisbursementAccounts paramDisbursementAccounts, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getSummaries";
    DisbursementSummaries localDisbursementSummaries1 = new DisbursementSummaries();
    Banking3 localBanking3 = null;
    try
    {
      localBanking3 = (Banking3)paramHashMap.get("BANKING_SERVICE");
    }
    catch (Exception localException1) {}
    if (localBanking3 == null)
    {
      localObject = new DisbException(str, 611, "Banking service must be in the extra hashmap");
      DebugLog.throwing(str, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    Object localObject = Calendar.getInstance();
    ((Calendar)localObject).set(11, 0);
    ((Calendar)localObject).set(12, 0);
    ((Calendar)localObject).set(13, 0);
    ((Calendar)localObject).set(14, 0);
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    localCalendar.add(5, 1);
    localCalendar.add(14, -1);
    if (paramDisbursementAccounts != null) {
      for (int i = 0; i < paramDisbursementAccounts.size(); i++) {
        try
        {
          DisbursementSummaries localDisbursementSummaries2 = BankSim.getDisbursementSummaries((DisbursementAccount)paramDisbursementAccounts.get(i), (Calendar)localObject, localCalendar, paramHashMap);
          if ((localDisbursementSummaries2 != null) && (localDisbursementSummaries2.size() > 0))
          {
            DisbursementSummary localDisbursementSummary = (DisbursementSummary)localDisbursementSummaries2.get(0);
            try
            {
              AccountHistories localAccountHistories = localBanking3.getHistory((Account)paramDisbursementAccounts.get(i), (Calendar)localObject, (Calendar)localObject, paramHashMap);
              if ((localAccountHistories != null) && (localAccountHistories.size() > 0)) {
                localDisbursementSummary.setCurrentBalance(((AccountHistory)localAccountHistories.get(0)).getCurrentLedger());
              }
            }
            catch (Exception localException2) {}
            localDisbursementSummaries1.add(localDisbursementSummary);
          }
        }
        catch (BSException localBSException)
        {
          DisbException localDisbException = new DisbException(str + " failed.", 600, localBSException);
          DebugLog.throwing(str, localBSException);
          throw localDisbException;
        }
      }
    }
    return localDisbursementSummaries1;
  }
  
  public DisbursementSummaries getSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getSummaries";
    DisbursementSummaries localDisbursementSummaries1 = new DisbursementSummaries();
    Banking3 localBanking3 = null;
    try
    {
      localBanking3 = (Banking3)paramHashMap.get("BANKING_SERVICE");
    }
    catch (Exception localException1) {}
    Object localObject;
    if (localBanking3 == null)
    {
      localObject = new DisbException(str, 611, "Banking service must be in the extra hashmap");
      DebugLog.throwing(str, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    if (paramDisbursementAccounts != null)
    {
      localObject = Calendar.getInstance();
      ((Calendar)localObject).set(11, 0);
      ((Calendar)localObject).set(12, 0);
      ((Calendar)localObject).set(13, 0);
      ((Calendar)localObject).set(14, 0);
      Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
      localCalendar.add(5, 1);
      localCalendar.add(14, -1);
      for (int i = 0; i < paramDisbursementAccounts.size(); i++) {
        try
        {
          DisbursementSummaries localDisbursementSummaries2 = BankSim.getDisbursementSummaries((DisbursementAccount)paramDisbursementAccounts.get(i), paramCalendar1, paramCalendar2, paramHashMap);
          try
          {
            Account localAccount = a((DisbursementAccount)paramDisbursementAccounts.get(i));
            AccountHistories localAccountHistories = localBanking3.getHistory(localAccount, (Calendar)localObject, (Calendar)localObject, paramHashMap);
            for (int j = 0; j < localDisbursementSummaries2.size(); j++)
            {
              DisbursementSummary localDisbursementSummary = (DisbursementSummary)localDisbursementSummaries2.get(j);
              DateTime localDateTime = localDisbursementSummary.getSummaryDate();
              if ((localAccountHistories != null) && (localAccountHistories.size() != 0)) {
                localDisbursementSummary.setCurrentBalance(((AccountHistory)localAccountHistories.get(0)).getCurrentLedger());
              }
              localDisbursementSummaries1.add(localDisbursementSummary);
            }
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        }
        catch (BSException localBSException)
        {
          DisbException localDisbException = new DisbException(str + " failed.", 600, localBSException);
          DebugLog.throwing(str, localBSException);
          throw localDisbException;
        }
      }
    }
    return localDisbursementSummaries1;
  }
  
  public DisbursementPresentmentSummaries getPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getPresentmentSummaries";
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.set(11, 0);
    localCalendar1.set(12, 0);
    localCalendar1.set(13, 0);
    localCalendar1.set(14, 0);
    Calendar localCalendar2 = (Calendar)localCalendar1.clone();
    localCalendar2.add(5, 1);
    localCalendar2.add(14, -1);
    try
    {
      return BankSim.getDisbursementPresentmentSummaries(paramDisbursementAccounts, localCalendar1, localCalendar2, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 602, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementPresentmentSummaries getPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getPresentmentSummaries";
    try
    {
      return BankSim.getDisbursementPresentmentSummaries(paramDisbursementAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 602, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementSummaries getSummariesForPresentment(DisbursementAccounts paramDisbursementAccounts, String paramString, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getSummariesForPresentment";
    DisbursementSummaries localDisbursementSummaries1 = new DisbursementSummaries();
    Banking3 localBanking3 = null;
    try
    {
      localBanking3 = (Banking3)paramHashMap.get("BANKING_SERVICE");
    }
    catch (Exception localException1) {}
    Object localObject;
    if (localBanking3 == null)
    {
      localObject = new DisbException(str, 611, "Banking service must be in the extra hashmap");
      DebugLog.throwing(str, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    if (paramDisbursementAccounts != null)
    {
      localObject = Calendar.getInstance();
      ((Calendar)localObject).set(11, 0);
      ((Calendar)localObject).set(12, 0);
      ((Calendar)localObject).set(13, 0);
      ((Calendar)localObject).set(14, 0);
      Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
      localCalendar.add(5, 1);
      localCalendar.add(14, -1);
      for (int i = 0; i < paramDisbursementAccounts.size(); i++) {
        try
        {
          DisbursementSummaries localDisbursementSummaries2 = BankSim.getDisbursementSummariesForPresentment((DisbursementAccount)paramDisbursementAccounts.get(i), paramString, (Calendar)localObject, localCalendar, paramHashMap);
          if ((localDisbursementSummaries2 != null) && (localDisbursementSummaries2.size() > 0))
          {
            DisbursementSummary localDisbursementSummary = (DisbursementSummary)localDisbursementSummaries2.get(0);
            try
            {
              AccountHistories localAccountHistories = localBanking3.getHistory((Account)paramDisbursementAccounts.get(i), (Calendar)localObject, (Calendar)localObject, paramHashMap);
              if ((localAccountHistories != null) && (localAccountHistories.size() > 0)) {
                localDisbursementSummary.setCurrentBalance(((AccountHistory)localAccountHistories.get(0)).getCurrentLedger());
              }
            }
            catch (Exception localException2) {}
            localDisbursementSummaries1.add(localDisbursementSummary);
          }
        }
        catch (BSException localBSException)
        {
          DisbException localDisbException = new DisbException(str + " failed.", 602, localBSException);
          DebugLog.throwing(str, localBSException);
          throw localDisbException;
        }
      }
    }
    return localDisbursementSummaries1;
  }
  
  public DisbursementSummaries getSummariesForPresentment(DisbursementAccounts paramDisbursementAccounts, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getSummariesForPresentment";
    DisbursementSummaries localDisbursementSummaries1 = new DisbursementSummaries();
    Banking3 localBanking3 = null;
    try
    {
      localBanking3 = (Banking3)paramHashMap.get("BANKING_SERVICE");
    }
    catch (Exception localException1) {}
    if (localBanking3 == null)
    {
      DisbException localDisbException1 = new DisbException(str, 611, "Banking service must be in the extra hashmap");
      DebugLog.throwing(str, localDisbException1);
      throw localDisbException1;
    }
    if (paramDisbursementAccounts != null) {
      for (int i = 0; i < paramDisbursementAccounts.size(); i++) {
        try
        {
          DisbursementSummaries localDisbursementSummaries2 = BankSim.getDisbursementSummariesForPresentment((DisbursementAccount)paramDisbursementAccounts.get(i), paramString, paramCalendar1, paramCalendar2, paramHashMap);
          try
          {
            Account localAccount = a((DisbursementAccount)paramDisbursementAccounts.get(i));
            AccountHistories localAccountHistories = localBanking3.getHistory(localAccount, paramCalendar1, paramCalendar2, paramHashMap);
            for (int j = 0; j < localDisbursementSummaries2.size(); j++)
            {
              DisbursementSummary localDisbursementSummary = (DisbursementSummary)localDisbursementSummaries2.get(j);
              DateTime localDateTime = localDisbursementSummary.getSummaryDate();
              if ((localAccountHistories != null) && (localAccountHistories.size() != 0)) {
                localDisbursementSummary.setCurrentBalance(((AccountHistory)localAccountHistories.get(0)).getCurrentLedger());
              }
              localDisbursementSummaries1.add(localDisbursementSummary);
            }
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        }
        catch (BSException localBSException)
        {
          DisbException localDisbException2 = new DisbException(str + " failed.", 602, localBSException);
          DebugLog.throwing(str, localBSException);
          throw localDisbException2;
        }
      }
    }
    return localDisbursementSummaries1;
  }
  
  public DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    return getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  public DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getTransactions";
    try
    {
      return BankSim.getDisbursementTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 607, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException
  {
    return getPagedTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  public DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getPagedTransactions";
    if (paramCalendar1 != null)
    {
      paramCalendar1.set(11, 0);
      paramCalendar1.set(12, 0);
      paramCalendar1.set(13, 0);
      paramCalendar1.set(14, 0);
    }
    if (paramCalendar2 != null)
    {
      paramCalendar2.set(11, 0);
      paramCalendar2.set(12, 0);
      paramCalendar2.set(13, 0);
      paramCalendar2.set(14, 0);
      paramCalendar2.add(5, 1);
      paramCalendar2.add(14, -1);
    }
    try
    {
      return BankSim.getPagedDisbursementTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 607, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DisbException
  {
    return null;
  }
  
  public DisbursementTransactions getRecentTransactions(DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getRecentTransactions";
    try
    {
      return BankSim.getRecentDisbursementTransactions(paramDisbursementAccount, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 607, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getNextTransactions";
    try
    {
      return BankSim.getNextDisbursementTransactions(paramDisbursementAccount, paramLong, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 607, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DisbException
  {
    return null;
  }
  
  public DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DisbException
  {
    String str = "DisbursementService.getNextTransactions";
    try
    {
      return BankSim.getPreviousDisbursementTransactions(paramDisbursementAccount, paramLong, paramHashMap);
    }
    catch (BSException localBSException)
    {
      DisbException localDisbException = new DisbException(str + " failed.", 607, localBSException);
      DebugLog.throwing(str, localBSException);
      throw localDisbException;
    }
  }
  
  public DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DisbException
  {
    return null;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DisbException
  {
    throw new DisbException("DisbursementService.getReportData failed.", 612);
  }
  
  private static DisbursementAccount a(Account paramAccount)
  {
    DisbursementAccount localDisbursementAccount = new DisbursementAccount();
    localDisbursementAccount.setAccountID(paramAccount.getID());
    localDisbursementAccount.setAccountNumber(paramAccount.getNumber());
    localDisbursementAccount.setAccountName(paramAccount.getNickName());
    localDisbursementAccount.setRoutingNumber(paramAccount.getRoutingNum());
    localDisbursementAccount.setBankID(paramAccount.getBankID());
    localDisbursementAccount.setCurrencyType(paramAccount.getCurrencyCode());
    return localDisbursementAccount;
  }
  
  private static Account a(DisbursementAccount paramDisbursementAccount)
  {
    Account localAccount = new Account();
    localAccount.setNumber(paramDisbursementAccount.getAccountNumber());
    localAccount.setNickName(paramDisbursementAccount.getAccountName());
    localAccount.setRoutingNum(paramDisbursementAccount.getRoutingNumber());
    localAccount.setBankID(paramDisbursementAccount.getBankID());
    localAccount.setCurrencyCode(paramDisbursementAccount.getCurrencyType());
    return localAccount;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.DisbursementService
 * JD-Core Version:    0.7.0.1
 */