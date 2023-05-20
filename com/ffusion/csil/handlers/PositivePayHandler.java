package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayAccounts;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.beans.positivepay.PPayDecision;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.positivepay.PPayException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.PositivePay2;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Locale;

public class PositivePayHandler
{
  public static final String JAVA_CLASS = "JAVA_CLASS";
  public static final String PPAY_SERVICE_NAME = "Positive Pay";
  public static final String PPAY_ACCOUNTS = "PPAY_ACCOUNTS";
  private static PositivePay2 a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.initialize";
    if (a == null)
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Positive Pay", str, 20107);
      a = (PositivePay2)HandlerUtil.instantiateService(localHashMap, str, 20107);
      try
      {
        a.initialize();
      }
      catch (PPayException localPPayException)
      {
        throw new CSILException(-1009, a(localPPayException), localPPayException);
      }
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static PPaySummaries getSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.getSummaries";
    a(str);
    Object localObject = (AccountService3)paramHashMap.get("SERVICE");
    if (localObject == null)
    {
      localObject = AccountHandler.getAccountService();
      paramHashMap.put("SERVICE", localObject);
    }
    try
    {
      PPayAccounts localPPayAccounts = a(paramSecureUser, paramHashMap);
      return a.getSummaries(localPPayAccounts, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static int getNumIssues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.getNumIssues";
    a(str);
    Object localObject = (AccountService3)paramHashMap.get("SERVICE");
    if (localObject == null)
    {
      localObject = AccountHandler.getAccountService();
      paramHashMap.put("SERVICE", localObject);
    }
    try
    {
      PPayAccounts localPPayAccounts = a(paramSecureUser, paramHashMap);
      return a.getNumIssues(localPPayAccounts, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static PPayIssues getIssues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.getIssues";
    Locale localLocale = a(paramHashMap);
    a(str);
    Object localObject = (AccountService3)paramHashMap.get("SERVICE");
    if (localObject == null)
    {
      localObject = AccountHandler.getAccountService();
      paramHashMap.put("SERVICE", localObject);
    }
    try
    {
      PPayAccounts localPPayAccounts = a(paramSecureUser, paramHashMap);
      PPayIssues localPPayIssues = new PPayIssues(localLocale);
      for (int i = 0; i < localPPayAccounts.size(); i++) {
        localPPayIssues.addAll(a.getIssuesForAccount((PPayAccount)localPPayAccounts.get(i), paramHashMap));
      }
      return localPPayIssues;
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static PPayIssues getIssuesForAccount(SecureUser paramSecureUser, PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.getIssuesForAccount";
    a(str);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Positive Pay" });
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramPPayAccount) });
    if (EntitlementsUtil.checkAccountEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localMultiEntitlement, paramSecureUser.getBusinessID()) != null) {
      throw new CSILException(str, 20001);
    }
    try
    {
      return a.getIssuesForAccount(paramPPayAccount, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.getReportData";
    a(str);
    Object localObject = (AccountService3)paramHashMap.get("SERVICE");
    if (localObject == null)
    {
      localObject = AccountHandler.getAccountService();
      paramHashMap.put("SERVICE", localObject);
    }
    try
    {
      PPayAccounts localPPayAccounts = a(paramSecureUser, paramHashMap);
      paramHashMap.put("accounts", localPPayAccounts);
      return a.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static void submitDecisions(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.submitDecisions";
    a(str);
    try
    {
      a.submitDecisions(paramSecureUser, paramPPayDecisions, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static void submitCheckRecords(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.submitCheckRecords";
    a(str);
    try
    {
      paramPPayCheckRecords = a(paramSecureUser, paramPPayCheckRecords, paramHashMap);
      a.submitCheckRecords(paramSecureUser, paramPPayCheckRecords, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static void cleanup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PositivePayHandler.cleanup";
    a(str);
    try
    {
      a.cleanup(paramInt, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  public static PPayAccounts removeUnentitledAccounts(Business paramBusiness, PPayAccounts paramPPayAccounts, HashMap paramHashMap)
  {
    PPayAccounts localPPayAccounts = new PPayAccounts(paramPPayAccounts.getLocale());
    if ((paramPPayAccounts == null) || (paramPPayAccounts.size() <= 0)) {
      return localPPayAccounts;
    }
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Positive Pay" });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    for (int i = 0; i < paramPPayAccounts.size(); i++)
    {
      PPayAccount localPPayAccount = (PPayAccount)paramPPayAccounts.get(i);
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localPPayAccount);
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(paramBusiness.getEntitlementGroupIdValue(), localMultiEntitlement, paramBusiness.getIdValue()) == null) {
          localPPayAccounts.add(localPPayAccount);
        }
      }
      catch (Exception localException) {}
    }
    return localPPayAccounts;
  }
  
  public static void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a.submitDefaultDecisions(paramInt, paramHashMap);
    }
    catch (PPayException localPPayException)
    {
      throw new CSILException(-1009, a(localPPayException), localPPayException);
    }
  }
  
  private static void a(String paramString)
    throws CSILException
  {
    if (a == null)
    {
      DebugLog.log("The Positive Pay Handler must be initialized before any calls are made to the handler method " + paramString + ".");
      throw new CSILException(-1009, 22001);
    }
  }
  
  private static Locale a(HashMap paramHashMap)
  {
    Locale localLocale = (Locale)paramHashMap.get("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    return localLocale;
  }
  
  private static PPayAccounts a(SecureUser paramSecureUser, PPayAccounts paramPPayAccounts, HashMap paramHashMap)
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Positive Pay" });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    PPayAccounts localPPayAccounts = new PPayAccounts(a(paramHashMap));
    if ((paramPPayAccounts == null) || (paramPPayAccounts.isEmpty())) {
      return localPPayAccounts;
    }
    for (int i = 0; i < paramPPayAccounts.size(); i++)
    {
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((PPayAccount)paramPPayAccounts.get(i));
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          localPPayAccounts.add(paramPPayAccounts.get(i));
        }
      }
      catch (Exception localException) {}
    }
    return localPPayAccounts;
  }
  
  private static PPayCheckRecords a(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Positive Pay" });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    PPayCheckRecords localPPayCheckRecords = new PPayCheckRecords(a(paramHashMap));
    for (int i = 0; i < paramPPayCheckRecords.size(); i++)
    {
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((PPayCheckRecord)paramPPayCheckRecords.get(i));
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          localPPayCheckRecords.add(paramPPayCheckRecords.get(i));
        }
      }
      catch (Exception localException) {}
    }
    return localPPayCheckRecords;
  }
  
  private static PPayDecisions a(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Positive Pay" });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    PPayDecisions localPPayDecisions = new PPayDecisions(a(paramHashMap));
    for (int i = 0; i < paramPPayDecisions.size(); i++)
    {
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(((PPayDecision)paramPPayDecisions.get(i)).getCheckRecord());
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          localPPayDecisions.add(paramPPayDecisions.get(i));
        }
      }
      catch (Exception localException) {}
    }
    return localPPayDecisions;
  }
  
  private static int a(PPayException paramPPayException)
  {
    switch (paramPPayException.getErrorCode())
    {
    case 1: 
      return 20107;
    case 2: 
      return 22004;
    case 3: 
      return 22005;
    case 25: 
      return 22027;
    case 4: 
      return 22006;
    case 5: 
      return 22007;
    case 6: 
      return 22008;
    case 7: 
      return 22009;
    case 8: 
      return 22010;
    case 9: 
      return 22011;
    case 10: 
      return 22012;
    case 11: 
      return 22013;
    case 12: 
      return 22014;
    case 13: 
      return 22015;
    case 14: 
      return 22016;
    case 15: 
      return 22017;
    case 16: 
      return 22018;
    case 17: 
      return 22019;
    case 18: 
      return 29101;
    case 19: 
      return 22021;
    case 20: 
      return 22022;
    case 21: 
      return 22023;
    case 22: 
      return 22024;
    case 23: 
      return 22025;
    case 24: 
      return 22026;
    case 29: 
      return 22028;
    case 28: 
      return 22030;
    }
    return paramPPayException.getErrorCode();
  }
  
  private static PPayAccounts a(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PPayException
  {
    PPayAccounts localPPayAccounts = null;
    if (paramHashMap.get("PPAY_ACCOUNTS") == null)
    {
      localPPayAccounts = a.getAccounts(paramSecureUser, paramHashMap);
      localPPayAccounts = a(paramSecureUser, localPPayAccounts, paramHashMap);
      paramHashMap.put("PPAY_ACCOUNTS", localPPayAccounts);
    }
    else
    {
      localPPayAccounts = (PPayAccounts)paramHashMap.get("PPAY_ACCOUNTS");
    }
    return localPPayAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.PositivePayHandler
 * JD-Core Version:    0.7.0.1
 */