package com.ffusion.services.banksim2;

import com.ffusion.banksim.BankSim;
import com.ffusion.banksim.interfaces.BSException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.Lockbox3;
import com.ffusion.services.lockbox.LBoxException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

public class LockboxService
  implements Lockbox3
{
  private HashMap jdField_case = new HashMap();
  
  public void initialize()
    throws LBoxException
  {}
  
  public LockboxAccounts getLockboxAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws LBoxException
  {
    Object localObject1 = paramHashMap.get(AccountService3.class);
    LockboxAccounts localLockboxAccounts = new LockboxAccounts();
    if ((localObject1 != null) && ((localObject1 instanceof AccountService3)))
    {
      localObject2 = (AccountService3)localObject1;
      Accounts localAccounts = null;
      Object localObject3;
      try
      {
        localAccounts = ((AccountService3)localObject2).getAccountsByBusinessEmployee(paramSecureUser, null);
      }
      catch (ProfileException localProfileException)
      {
        localObject3 = new LBoxException(39500);
        DebugLog.throwing("LockboxService.getLockboxAccounts", (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
      for (int i = 0; i < localAccounts.size(); i++)
      {
        localObject3 = (Account)localAccounts.get(i);
        int j = ((Account)localObject3).getTypeValue();
        if ((j == 1) || (j == 2))
        {
          LockboxAccount localLockboxAccount = new LockboxAccount();
          localLockboxAccount.setAccountID(((Account)localObject3).getID());
          localLockboxAccount.setAccountNumber(((Account)localObject3).getNumber());
          localLockboxAccount.setBankID(((Account)localObject3).getBankID());
          localLockboxAccount.setNickname(((Account)localObject3).getNickName());
          localLockboxAccount.setCurrencyType(((Account)localObject3).getCurrencyCode());
          localLockboxAccount.setRoutingNumber(((Account)localObject3).getRoutingNum());
          localLockboxAccounts.add(localLockboxAccount);
        }
      }
      return localLockboxAccounts;
    }
    Object localObject2 = new LBoxException(39500);
    DebugLog.throwing("LockboxService.getLockboxAccounts", (Throwable)localObject2);
    throw ((Throwable)localObject2);
  }
  
  public LockboxSummaries getSummaries(LockboxAccounts paramLockboxAccounts, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      Calendar localCalendar = Calendar.getInstance();
      localObject = (Calendar)localCalendar.clone();
      localCalendar.set(11, 0);
      localCalendar.set(12, 0);
      localCalendar.set(13, 0);
      localCalendar.set(14, 0);
      ((Calendar)localObject).set(11, 23);
      ((Calendar)localObject).set(12, 59);
      ((Calendar)localObject).set(13, 59);
      ((Calendar)localObject).set(14, 999);
      LockboxSummaries localLockboxSummaries1 = new LockboxSummaries();
      LockboxSummaries localLockboxSummaries2 = null;
      for (int i = 0; i < paramLockboxAccounts.size(); i++)
      {
        localLockboxSummaries2 = BankSim.getLockboxSummaries((LockboxAccount)paramLockboxAccounts.get(i), localCalendar, (Calendar)localObject, paramHashMap);
        localLockboxSummaries1.addAll(localLockboxSummaries2);
      }
      return localLockboxSummaries1;
    }
    catch (BSException localBSException)
    {
      Object localObject = new LBoxException("LockboxService getSummaries failed.", 39501, localBSException);
      DebugLog.throwing("LockboxService.getSummaries", localBSException);
      throw ((Throwable)localObject);
    }
  }
  
  public LockboxSummaries getSummaries(LockboxAccounts paramLockboxAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      LockboxSummaries localLockboxSummaries = new LockboxSummaries();
      localObject = null;
      for (int i = 0; i < paramLockboxAccounts.size(); i++)
      {
        localObject = BankSim.getLockboxSummaries((LockboxAccount)paramLockboxAccounts.get(i), paramCalendar1, paramCalendar2, paramHashMap);
        localLockboxSummaries.addAll((Collection)localObject);
      }
      return localLockboxSummaries;
    }
    catch (BSException localBSException)
    {
      Object localObject = new LBoxException("LockboxService getSummaries failed.", 39501, localBSException);
      DebugLog.throwing("LockboxService.getSummaries", localBSException);
      throw ((Throwable)localObject);
    }
  }
  
  public LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getLockboxTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getTransactions failed.", 39503, localBSException);
      DebugLog.throwing("LockboxService.getTransactions", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getPagedLockboxTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPagedTransactions failed.", 39503, localBSException);
      DebugLog.throwing("LockboxService.getPagedTransactions", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getRecentTransactions(LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getRecentLockboxTransactions(paramLockboxAccount, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getRecentTransactions failed.", 39503, localBSException);
      DebugLog.throwing("LockboxService.getRecentTransactions", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getNextLockboxTransactions(paramLockboxAccount, paramLong, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getNextTransactions failed.", 39503, localBSException);
      DebugLog.throwing("LockboxService.getNextTransactions", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getPreviousLockboxTransactions(paramLockboxAccount, paramLong, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPreviousTransactions failed.", 39503, localBSException);
      DebugLog.throwing("LockboxService.getPreviousTransactions", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getLockboxCreditItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getCreditItems failed.", 39504, localBSException);
      DebugLog.throwing("LockboxService.getCreditItems", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getPagedLockboxCreditItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPagedCreditItems failed.", 39504, localBSException);
      DebugLog.throwing("LockboxService.getPagedCreditItems", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getRecentCreditItems(LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getRecentLockboxCreditItems(paramLockboxAccount, paramString, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getRecentCreditItems failed.", 39504, localBSException);
      DebugLog.throwing("LockboxService.getRecentCreditItems", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getNextLockboxCreditItems(paramLockboxAccount, paramString, paramLong, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getNextCreditItems failed.", 39504, localBSException);
      DebugLog.throwing("LockboxService.getNextCreditItems", localBSException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return BankSim.getPreviousLockboxCreditItems(paramLockboxAccount, paramString, paramLong, paramHashMap);
    }
    catch (BSException localBSException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPreviousCreditItems failed.", 39504, localBSException);
      DebugLog.throwing("LockboxService.getPreviousCreditItems", localBSException);
      throw localLBoxException;
    }
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws LBoxException
  {
    throw new LBoxException("LockboxService getReportData failed.", 39507);
  }
  
  public LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    return null;
  }
  
  public LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    return null;
  }
  
  public LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    return null;
  }
  
  public LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    return null;
  }
  
  public LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    return null;
  }
  
  public LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.LockboxService
 * JD-Core Version:    0.7.0.1
 */