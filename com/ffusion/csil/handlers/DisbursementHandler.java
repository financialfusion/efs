package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.Disbursements4;
import com.ffusion.services.disbursements.DisbException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.Calendar;
import java.util.HashMap;

public class DisbursementHandler
{
  private static final String jdField_if = "Disbursements";
  private static Disbursements4 a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "DisbursementsHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Disbursements", str, 20107);
    a = (Disbursements4)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize();
    }
    catch (DisbException localDisbException)
    {
      CSILException localCSILException = new CSILException(20107, localDisbException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static DisbursementSummaries getSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Object localObject = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject == null)
      {
        localObject = AccountHandler.getAccountService();
        paramHashMap.put("SERVICE", localObject);
      }
      DisbursementAccounts localDisbursementAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localDisbursementAccounts = a(paramSecureUser, localDisbursementAccounts, paramHashMap);
      return a.getSummaries(localDisbursementAccounts, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  public static DisbursementSummaries getSummaries(SecureUser paramSecureUser, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Object localObject = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject == null)
      {
        localObject = AccountHandler.getAccountService();
        paramHashMap.put("SERVICE", localObject);
      }
      DisbursementAccounts localDisbursementAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localDisbursementAccounts = a(paramSecureUser, localDisbursementAccounts, paramHashMap);
      return a.getSummaries(localDisbursementAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  public static DisbursementSummaries getSummariesForPresentment(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Object localObject = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject == null)
      {
        localObject = AccountHandler.getAccountService();
        paramHashMap.put("SERVICE", localObject);
      }
      DisbursementAccounts localDisbursementAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localDisbursementAccounts = a(paramSecureUser, localDisbursementAccounts, paramHashMap);
      return a.getSummariesForPresentment(localDisbursementAccounts, paramString, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  public static DisbursementSummaries getSummariesForPresentment(SecureUser paramSecureUser, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Object localObject = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject == null)
      {
        localObject = AccountHandler.getAccountService();
        paramHashMap.put("SERVICE", localObject);
      }
      DisbursementAccounts localDisbursementAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localDisbursementAccounts = a(paramSecureUser, localDisbursementAccounts, paramHashMap);
      return a.getSummariesForPresentment(localDisbursementAccounts, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  public static DisbursementPresentmentSummaries getPresentmentSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Object localObject = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject == null)
      {
        localObject = AccountHandler.getAccountService();
        paramHashMap.put("SERVICE", localObject);
      }
      DisbursementAccounts localDisbursementAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localDisbursementAccounts = a(paramSecureUser, localDisbursementAccounts, paramHashMap);
      return a.getPresentmentSummaries(localDisbursementAccounts, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  public static DisbursementPresentmentSummaries getPresentmentSummaries(SecureUser paramSecureUser, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Object localObject = (AccountService3)paramHashMap.get("SERVICE");
      if (localObject == null)
      {
        localObject = AccountHandler.getAccountService();
        paramHashMap.put("SERVICE", localObject);
      }
      DisbursementAccounts localDisbursementAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localDisbursementAccounts = a(paramSecureUser, localDisbursementAccounts, paramHashMap);
      return a.getPresentmentSummaries(localDisbursementAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  private static int a(DisbException paramDisbException)
  {
    return paramDisbException.getCode();
  }
  
  public static DisbursementTransactions getTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    return getTransactions(paramSecureUser, paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  public static DisbursementTransactions getTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    DisbursementTransactions localDisbursementTransactions = null;
    try
    {
      localDisbursementTransactions = a.getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getPagedTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    localPagingContext.setMap(new HashMap());
    return getPagedTransactions(paramSecureUser, paramDisbursementAccount, localPagingContext, paramHashMap);
  }
  
  public static DisbursementTransactions getPagedTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    DisbursementTransactions localDisbursementTransactions = null;
    try
    {
      localDisbursementTransactions = a.getPagedTransactions(paramDisbursementAccount, paramPagingContext, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getRecentTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getRecentTransactions(paramDisbursementAccount, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(22000, localDisbException);
    }
  }
  
  public static DisbursementTransactions getNextTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getNextTransactions(paramDisbursementAccount, paramPagingContext, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(22000, localDisbException);
    }
  }
  
  public static DisbursementTransactions getPreviousTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getPreviousTransactions(paramDisbursementAccount, paramPagingContext, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(22000, localDisbException);
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (DisbException localDisbException)
    {
      throw new CSILException(-1009, a(localDisbException), localDisbException);
    }
  }
  
  private static DisbursementAccounts a(SecureUser paramSecureUser, DisbursementAccounts paramDisbursementAccounts, HashMap paramHashMap)
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { null });
    DisbursementAccounts localDisbursementAccounts = new DisbursementAccounts();
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    for (int i = 0; i < paramDisbursementAccounts.size(); i++)
    {
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((DisbursementAccount)paramDisbursementAccounts.get(i));
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          localDisbursementAccounts.add(paramDisbursementAccounts.get(i));
        }
      }
      catch (Exception localException) {}
    }
    return localDisbursementAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.DisbursementHandler
 * JD-Core Version:    0.7.0.1
 */