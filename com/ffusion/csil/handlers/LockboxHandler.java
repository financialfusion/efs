package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.AccountService6;
import com.ffusion.services.Lockbox3;
import com.ffusion.services.lockbox.LBoxException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.HashMap;

public class LockboxHandler
{
  private static final String jdField_if = "Lockbox";
  private static Lockbox3 a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "LockboxHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Lockbox", str, 20107);
    a = (Lockbox3)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize();
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing(str, localLBoxException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static LockboxSummaries getSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    AccountService6 localAccountService6 = AccountHandler.getAccountService();
    paramHashMap.put(AccountService3.class, localAccountService6);
    try
    {
      LockboxAccounts localLockboxAccounts = a.getLockboxAccounts(paramSecureUser, paramHashMap);
      localLockboxAccounts = a(paramSecureUser, localLockboxAccounts, paramHashMap);
      paramHashMap.put(LockboxAccounts.class, localLockboxAccounts);
      return a.getSummaries(localLockboxAccounts, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getSummaries", localLBoxException);
      throw localCSILException;
    }
  }
  
  public static LockboxSummaries getSummaries(SecureUser paramSecureUser, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    AccountService6 localAccountService6 = AccountHandler.getAccountService();
    paramHashMap.put(AccountService3.class, localAccountService6);
    try
    {
      LockboxAccounts localLockboxAccounts = a.getLockboxAccounts(paramSecureUser, paramHashMap);
      localLockboxAccounts = a(paramSecureUser, localLockboxAccounts, paramHashMap);
      paramHashMap.put(LockboxAccounts.class, localLockboxAccounts);
      return a.getSummaries(localLockboxAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getSummaries", localLBoxException);
      throw localCSILException;
    }
  }
  
  public static LockboxTransactions getTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getTransactions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxTransactions getPagedTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getPagedTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getPagedTransactions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxTransactions getRecentTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getRecentTransactions(paramLockboxAccount, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getRecentTransactions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxTransactions getNextTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getNextTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getNextTransactions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxTransactions getPreviousTransactions(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getPreviousTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getPreviousTransactions", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxCreditItems getCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getCreditItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getCreditItems", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxCreditItems getPagedCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getPagedCreditItems(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getPagedCreditItems", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxCreditItems getRecentCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getRecentCreditItems(paramLockboxAccount, paramString, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getRecentCreditItems", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxCreditItems getNextCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getNextCreditItems(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getNextCreditItems", localCSILException);
      throw localCSILException;
    }
  }
  
  public static LockboxCreditItems getPreviousCreditItems(SecureUser paramSecureUser, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getPreviousCreditItems(paramLockboxAccount, paramPagingContext, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getPreviousCreditItems", localLBoxException);
      throw localCSILException;
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (LBoxException localLBoxException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localLBoxException), localLBoxException);
      DebugLog.throwing("LockboxHandler.getReportData", localLBoxException);
      throw localCSILException;
    }
  }
  
  private static LockboxAccounts a(SecureUser paramSecureUser, LockboxAccounts paramLockboxAccounts, HashMap paramHashMap)
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { null });
    LockboxAccounts localLockboxAccounts = new LockboxAccounts();
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    for (int i = 0; i < paramLockboxAccounts.size(); i++)
    {
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((LockboxAccount)paramLockboxAccounts.get(i));
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          localLockboxAccounts.add(paramLockboxAccounts.get(i));
        }
      }
      catch (Exception localException) {}
    }
    return localLockboxAccounts;
  }
  
  private static int a(LBoxException paramLBoxException)
  {
    return paramLBoxException.getCode();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.LockboxHandler
 * JD-Core Version:    0.7.0.1
 */