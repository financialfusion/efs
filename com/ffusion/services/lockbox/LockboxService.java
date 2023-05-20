package com.ffusion.services.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.dataconsolidator.adapter.DCAdapter;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.AccountService3;
import com.ffusion.services.Lockbox3;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class LockboxService
  implements Lockbox3
{
  private HashMap jdField_byte = new HashMap();
  
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
          localLockboxAccount.setRoutingNumber(((Account)localObject3).getRoutingNum());
          localLockboxAccount.setBankID(((Account)localObject3).getBankID());
          localLockboxAccount.setNickname(((Account)localObject3).getNickName());
          localLockboxAccount.setCurrencyType(((Account)localObject3).getCurrencyCode());
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
      LockboxSummaries localLockboxSummaries = new LockboxSummaries();
      for (int i = 0; i < paramLockboxAccounts.size(); i++) {
        localLockboxSummaries.addAll(DCUtil.combineLockboxSummariesByDate(DCAdapter.getLockboxSummaries((LockboxAccount)paramLockboxAccounts.get(i), localCalendar, (Calendar)localObject, paramHashMap)));
      }
      return localLockboxSummaries;
    }
    catch (DCException localDCException)
    {
      Object localObject = new LBoxException("LockboxService getSummaries failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getSummaries", localDCException);
      throw ((Throwable)localObject);
    }
  }
  
  public LockboxSummaries getSummaries(LockboxAccounts paramLockboxAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      LockboxSummaries localLockboxSummaries = new LockboxSummaries();
      localLBoxException = null;
      for (int i = 0; i < paramLockboxAccounts.size(); i++) {
        localLockboxSummaries.addAll(DCUtil.combineLockboxSummariesByDate(DCAdapter.getLockboxSummaries((LockboxAccount)paramLockboxAccounts.get(i), paramCalendar1, paramCalendar2, paramHashMap)));
      }
      return localLockboxSummaries;
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getSummaries failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getSummaries", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getLockboxTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPagedLockboxTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPagedTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPagedTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPagedLockboxTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPagedTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPagedTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getRecentTransactions(LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getRecentLockboxTransactions(paramLockboxAccount, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getRecentTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getRecentTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getNextLockboxTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getNextTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getNextTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getNextLockboxTransactions(paramLockboxAccount, paramLong, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getNextTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getNextTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPreviousLockboxTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPreviousTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPreviousTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPreviousLockboxTransactions(paramLockboxAccount, paramLong, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPreviousTransactions failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPreviousTransactions", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getLockboxCreditItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPagedLockboxCreditItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPagedCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPagedCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPagedLockboxCreditItems(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPagedCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPagedCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getRecentCreditItems(LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getRecentLockboxCreditItems(paramLockboxAccount, paramString, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getRecentCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getRecentCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getNextLockboxCreditItems(paramLockboxAccount, paramString, paramLong, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getNextCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getNextCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getNextLockboxCreditItems(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getNextCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getNextCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPreviousLockboxCreditItems(paramLockboxAccount, paramString, paramLong, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPreviousCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPreviousCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      return DCAdapter.getPreviousLockboxCreditItems(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (DCException localDCException)
    {
      LBoxException localLBoxException = new LBoxException("LockboxService getPreviousCreditItems failed.", localDCException.getCode(), localDCException);
      DebugLog.throwing("LockboxService.getPreviousCreditItems", localDCException);
      throw localLBoxException;
    }
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws LBoxException
  {
    try
    {
      Properties localProperties = paramReportCriteria.getSearchCriteria();
      if (localProperties == null) {
        throw new LBoxException(39508);
      }
      String str1 = localProperties.getProperty("LockboxNumber");
      if (str1 == null) {
        str1 = "ALL";
      }
      Object localObject1;
      Object localObject2;
      Object localObject5;
      if (a(str1))
      {
        localObject1 = new Accounts();
        LockboxAccounts localLockboxAccounts = (LockboxAccounts)paramHashMap.get("LOCKBOX_ACCOUNTS");
        for (int j = 0; j < localLockboxAccounts.size(); j++)
        {
          localObject3 = (LockboxAccount)localLockboxAccounts.get(j);
          Account localAccount = a((Accounts)localObject1, (LockboxAccount)localObject3);
        }
        localObject2 = new HashMap();
        Object localObject3 = new HashMap();
        try
        {
          Reporting.calculateDateRange(paramSecureUser, (Accounts)localObject1, paramReportCriteria, (HashMap)localObject2, (HashMap)localObject3, paramHashMap);
          a((Accounts)localObject1, (HashMap)localObject2, (HashMap)localObject3, paramReportCriteria);
          paramHashMap.put("StartDates", localObject2);
          paramHashMap.put("EndDates", localObject3);
        }
        catch (CSILException localCSILException1)
        {
          localObject5 = "Unable to calculate the date ranges. A report cannot be run.";
          DebugLog.log((String)localObject5);
          if (localCSILException1.getCode() == -1009) {
            throw new LBoxException(localCSILException1.getServiceError());
          }
          throw new LBoxException(localCSILException1.getCode());
        }
      }
      else
      {
        localObject1 = new StringTokenizer(str1, ",");
        int i = ((StringTokenizer)localObject1).countTokens();
        if (i < 1) {
          throw new LBoxException(39510);
        }
        localObject2 = new Accounts();
        for (int k = 0; k < i; k++)
        {
          localObject4 = ((StringTokenizer)localObject1).nextToken();
          localObject5 = new StringTokenizer((String)localObject4, ":");
          int m = ((StringTokenizer)localObject5).countTokens();
          if (m < 2)
          {
            localObject6 = "Value for SearchCriteria key LockboxNumber has incorrect format.";
            DebugLog.log((String)localObject6);
            throw new LBoxException(39510);
          }
          Object localObject6 = new Account();
          ((Account)localObject6).setID(((StringTokenizer)localObject5).nextToken());
          ((Account)localObject6).setBankID(((StringTokenizer)localObject5).nextToken());
          String str3 = null;
          Object localObject7 = null;
          if (m >= 3) {
            str3 = ((StringTokenizer)localObject5).nextToken();
          }
          if (m == 4) {
            ((Account)localObject6).setRoutingNum(((StringTokenizer)localObject5).nextToken());
          }
          ((Accounts)localObject2).add(localObject6);
        }
        HashMap localHashMap = new HashMap();
        Object localObject4 = new HashMap();
        try
        {
          Reporting.calculateDateRange(paramSecureUser, (Accounts)localObject2, paramReportCriteria, localHashMap, (HashMap)localObject4, paramHashMap);
          a((Accounts)localObject2, localHashMap, (HashMap)localObject4, paramReportCriteria);
          paramHashMap.put("StartDates", localHashMap);
          paramHashMap.put("EndDates", localObject4);
        }
        catch (CSILException localCSILException2)
        {
          String str2 = "Unable to calculate the date ranges. A report cannot be run.";
          DebugLog.log(str2);
          if (localCSILException2.getCode() == -1009) {
            throw new LBoxException(localCSILException2.getServiceError());
          }
          throw new LBoxException(localCSILException2.getCode());
        }
      }
      return DCAdapter.getLockboxReportData(paramReportCriteria, paramHashMap);
    }
    catch (DCException localDCException)
    {
      throw a(localDCException);
    }
  }
  
  private void a(Accounts paramAccounts, HashMap paramHashMap1, HashMap paramHashMap2, ReportCriteria paramReportCriteria)
    throws LBoxException
  {
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      Account localAccount = (Account)paramAccounts.get(i);
      String str = localAccount.getRoutingNum();
      if (str != null)
      {
        DateTime localDateTime1 = (DateTime)paramHashMap1.get(str);
        DateTime localDateTime2 = (DateTime)paramHashMap2.get(str);
        Object localObject;
        DateTime localDateTime3;
        if ((localDateTime1 == null) && (localDateTime2 == null))
        {
          localObject = new DateTime(Locale.getDefault());
          ((DateTime)localObject).add(5, 1);
          ((DateTime)localObject).add(14, -1);
          localDateTime3 = new DateTime((Calendar)localObject, Locale.getDefault());
          localDateTime3.add(5, -1 * a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          paramHashMap1.put(str, localDateTime3);
          paramHashMap2.put(str, localObject);
        }
        else if ((localDateTime1 != null) && (localDateTime2 == null))
        {
          localDateTime3 = new DateTime(localDateTime1, Locale.getDefault());
          localDateTime3.add(5, a(paramReportCriteria));
          localDateTime3.add(5, 1);
          localDateTime3.add(14, -1);
          paramHashMap2.put(str, localDateTime3);
        }
        else if ((localDateTime1 == null) && (localDateTime2 != null))
        {
          localDateTime3 = new DateTime(localDateTime2, Locale.getDefault());
          localDateTime3.add(5, -1 * a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          paramHashMap1.put(str, localDateTime3);
        }
        else
        {
          localDateTime3 = new DateTime(localDateTime2, Locale.getDefault());
          localDateTime3.add(5, -1 * a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          if (localDateTime1.before(localDateTime3))
          {
            localObject = "The date range for routing number " + str + " is too long - " + "the maximum range allowed is " + a(paramReportCriteria) + " days.";
            LBoxException localLBoxException = new LBoxException("LockboxService.updateDates", 39530, (String)localObject);
            DebugLog.throwing("LockboxService.updateDates", localLBoxException);
            throw localLBoxException;
          }
        }
      }
    }
  }
  
  private static Account a(Accounts paramAccounts, LockboxAccount paramLockboxAccount)
  {
    Account localAccount = paramAccounts.create(paramLockboxAccount.getBankID(), paramLockboxAccount.getAccountID(), paramLockboxAccount.getAccountNumber(), 0);
    localAccount.setRoutingNum(paramLockboxAccount.getRoutingNumber());
    localAccount.setNickName(paramLockboxAccount.getNickname());
    localAccount.setCurrencyCode(paramLockboxAccount.getCurrencyType());
    return localAccount;
  }
  
  private static LBoxException a(RptException paramRptException)
  {
    if ((paramRptException.getChained() instanceof LBoxException)) {
      return (LBoxException)paramRptException.getChained();
    }
    LBoxException localLBoxException = new LBoxException("LockboxService.getReportData", 39505, paramRptException);
    return localLBoxException;
  }
  
  private static LBoxException a(DCException paramDCException)
  {
    int i;
    if (paramDCException.getCode() == 314) {
      i = 39508;
    } else if (paramDCException.getCode() == 315) {
      i = 39537;
    } else if (paramDCException.getCode() == 316) {
      i = 39538;
    } else if (paramDCException.getCode() == 318) {
      i = 39539;
    } else if (paramDCException.getCode() == 317) {
      i = 39521;
    } else if (paramDCException.getCode() == 342) {
      i = 39540;
    } else if (paramDCException.getCode() == 343) {
      i = 39541;
    } else if (paramDCException.getCode() == 329) {
      i = 39510;
    } else if (paramDCException.getCode() == 322) {
      i = 39531;
    } else if (paramDCException.getCode() == 323) {
      i = 39532;
    } else if (paramDCException.getCode() == 328) {
      i = 39542;
    } else if (paramDCException.getCode() == 320) {
      i = 39533;
    } else if (paramDCException.getCode() == 321) {
      i = 39534;
    } else if (paramDCException.getCode() == 358) {
      i = 39535;
    } else if (paramDCException.getCode() == 359) {
      i = 39536;
    } else if (paramDCException.getCode() == 357) {
      i = 39530;
    } else if (paramDCException.getCode() == 330) {
      i = 39543;
    } else if (paramDCException.getCode() == 331) {
      i = 39544;
    } else if (paramDCException.getCode() == 344) {
      i = 39545;
    } else if (paramDCException.getCode() == 345) {
      i = 39546;
    } else if (paramDCException.getCode() == 332) {
      i = 39552;
    } else if (paramDCException.getCode() == 333) {
      i = 39553;
    } else if (paramDCException.getCode() == 346) {
      i = 39547;
    } else if (paramDCException.getCode() == 347) {
      i = 39548;
    } else if (paramDCException.getCode() == 348) {
      i = 39554;
    } else if (paramDCException.getCode() == 355) {
      i = 39555;
    } else if (paramDCException.getCode() == 356) {
      i = 39556;
    } else if (paramDCException.getCode() == 349) {
      i = 39549;
    } else if (paramDCException.getCode() == 350) {
      i = 39550;
    } else if (paramDCException.getCode() == 351) {
      i = 39551;
    } else if (paramDCException.getCode() == 352) {
      i = 39557;
    } else if (paramDCException.getCode() == 353) {
      i = 39558;
    } else if (paramDCException.getCode() == 354) {
      i = 39559;
    } else {
      i = 39505;
    }
    LBoxException localLBoxException = new LBoxException("LockboxService.getReportData", i, paramDCException);
    return localLBoxException;
  }
  
  private static boolean a(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if ("ALL".equals(str)) {
        return true;
      }
    }
    return false;
  }
  
  private static int a(ReportCriteria paramReportCriteria)
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str = localProperties.getProperty("MAX_DAYS_IN_DATE_RANGE");
    int i = 31;
    if (str != null) {
      try
      {
        i = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.WARNING, "The maximum number of days in a date range report option has been specified incorrectly. The value provided ( " + str + " ) is not a valid integer.");
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.lockbox.LockboxService
 * JD-Core Version:    0.7.0.1
 */