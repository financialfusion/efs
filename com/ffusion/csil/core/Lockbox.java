package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxSummaryRpt;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.LockboxHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.PerfLog;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class Lockbox
  extends Initialize
{
  private static Entitlement axx = new Entitlement("Lockbox", null, null);
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    LockboxHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return LockboxHandler.getService();
  }
  
  public static LockboxSummaries getSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getSummaries";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxSummaries localLockboxSummaries = LockboxHandler.getSummaries(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxSummaries;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxSummaries getSummaries(SecureUser paramSecureUser, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getSummaries";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxSummaries localLockboxSummaries = LockboxHandler.getSummaries(paramSecureUser, paramCalendar1, paramCalendar2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxSummaries;
    }
    throw new CSILException(str, 20001);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, LockboxSummaries paramLockboxSummaries)
  {
    String str = "Lockbox.filterSummaries";
    Iterator localIterator = paramLockboxSummaries.iterator();
    while (localIterator.hasNext()) {
      LockboxSummary localLockboxSummary = (LockboxSummary)localIterator.next();
    }
  }
  
  public static LockboxTransactions getTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getTransactions";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxTransactions localLockboxTransactions = LockboxHandler.getTransactions(paramSecureUser, paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxTransactions;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxTransactions getPagedTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    return getPagedTransactions(paramSecureUser, paramLockboxAccount, localPagingContext, paramHashMap);
  }
  
  public static LockboxTransactions getPagedTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getPagedTransactions";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxTransactions localLockboxTransactions = LockboxHandler.getPagedTransactions(paramSecureUser, paramLockboxAccount, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxTransactions;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxTransactions getRecentTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws CSILException
  {
    return getPagedTransactions(paramSecureUser, paramLockboxAccount, null, null, paramHashMap);
  }
  
  public static LockboxTransactions getNextTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    return getNextTransactions(paramSecureUser, paramLockboxAccount, localPagingContext, paramHashMap);
  }
  
  public static LockboxTransactions getNextTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getNextTransactions";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxTransactions localLockboxTransactions = LockboxHandler.getNextTransactions(paramSecureUser, paramLockboxAccount, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxTransactions;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxTransactions getPreviousTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    return getPreviousTransactions(paramSecureUser, paramLockboxAccount, localPagingContext, paramHashMap);
  }
  
  public static LockboxTransactions getPreviousTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getPreviousTransactions";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxTransactions localLockboxTransactions = LockboxHandler.getPreviousTransactions(paramSecureUser, paramLockboxAccount, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxTransactions;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxCreditItems getCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getCreditItems";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxCreditItems localLockboxCreditItems = LockboxHandler.getCreditItems(paramSecureUser, paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxCreditItems;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxCreditItems getPagedCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    return getPagedCreditItems(paramSecureUser, paramLockboxAccount, localPagingContext, paramHashMap);
  }
  
  public static LockboxCreditItems getPagedCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getPagedCreditItems";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxCreditItems localLockboxCreditItems = LockboxHandler.getPagedCreditItems(paramSecureUser, paramLockboxAccount, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxCreditItems;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxCreditItems getRecentCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return getPagedCreditItems(paramSecureUser, paramLockboxAccount, paramString, null, null, paramHashMap);
  }
  
  public static LockboxCreditItems getNextCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    return getNextCreditItems(paramSecureUser, paramLockboxAccount, localPagingContext, paramHashMap);
  }
  
  public static LockboxCreditItems getNextCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getNextCreditItems";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxCreditItems localLockboxCreditItems = LockboxHandler.getNextCreditItems(paramSecureUser, paramLockboxAccount, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxCreditItems;
    }
    throw new CSILException(str, 20001);
  }
  
  public static LockboxCreditItems getPreviousCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    return getPreviousCreditItems(paramSecureUser, paramLockboxAccount, localPagingContext, paramHashMap);
  }
  
  public static LockboxCreditItems getPreviousCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Lockbox.getPreviousCreditItems";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      LockboxCreditItems localLockboxCreditItems = LockboxHandler.getPreviousCreditItems(paramSecureUser, paramLockboxAccount, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLockboxCreditItems;
    }
    throw new CSILException(str, 20001);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Lockbox.getReportData";
    if (jdMethod_void(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
      IReportResult localIReportResult = LockboxHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      Properties localProperties = paramReportCriteria.getReportOptions();
      String str2 = localProperties.getProperty("REPORTTYPE");
      EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
      if ((localIReportResult instanceof LockboxSummaryRpt))
      {
        localObject = Accounts.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
        LockboxSummaryRpt localLockboxSummaryRpt = (LockboxSummaryRpt)localIReportResult;
        LockboxSummaries localLockboxSummaries = localLockboxSummaryRpt.getLockboxSummaries();
        Iterator localIterator = localLockboxSummaries.iterator();
        while (localIterator.hasNext())
        {
          LockboxSummary localLockboxSummary = (LockboxSummary)localIterator.next();
          LockboxAccount localLockboxAccount = localLockboxSummary.getLockboxAccount();
          Account localAccount = ((com.ffusion.beans.accounts.Accounts)localObject).getByIDAndBankIDAndRoutingNum(localLockboxAccount.getAccountID(), localLockboxAccount.getBankID(), localLockboxAccount.getRoutingNumber());
          if (localAccount != null) {
            localLockboxAccount.setNickname(localAccount.getNickName());
          }
        }
      }
      Object localObject = paramReportCriteria.getReportOptions().getProperty("TITLE");
      if ((localObject == null) || (((String)localObject).length() == 0)) {
        localObject = ReportConsts.getReportName(str2, Locale.getDefault());
      }
      audit(paramSecureUser, "Generate " + (String)localObject + ".", TrackingIDGenerator.GetNextID(), 1908);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localIReportResult;
    }
    throw new CSILException(str1, 20001);
  }
  
  private static boolean jdMethod_void(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axx);
    if (!bool) {
      logEntitlementFault(paramSecureUser, "The user is not entitled to use lockbox services.", TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Lockbox
 * JD-Core Version:    0.7.0.1
 */