package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.Institutions;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;

public class AccountAggregation
  extends Initialize
{
  private static final String ap1 = "com.ffusion.util.logging.audit.accountaggregation";
  private static final String apZ = "AuditMessage_1";
  private static final String ap7 = "AuditMessage_2";
  private static final String ap4 = "AuditMessage_3";
  private static final String ap2 = "AuditEntFault_1";
  private static final String ap8 = "AuditEntFault_2";
  private static final String apY = "AuditEntFault_3";
  private static final String apV = "AuditEntFault_4";
  private static final String aqa = "AuditEntFault_5";
  private static final String apX = "AuditEntFault_6";
  private static final String apU = "AuditEntFault_7";
  private static final String apW = "AuditEntFault_8";
  private static final String ap5 = "AuditEntFault_9";
  private static final String aqd = "AuditEntFault_10";
  private static final String aqc = "AuditEntFault_11";
  private static final String ap9 = "AuditEntFault_12";
  private static final String ap3 = "AuditEntFault_13";
  private static final String ap6 = "AuditEntFault_14";
  private static final String aqb = "AuditEntFault_15";
  private static Entitlement ap0 = new Entitlement("AccountAggregation", null, null);
  
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.AccountAggregation.initialize");
    com.ffusion.csil.handlers.AccountAggregation.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.AccountAggregation.getService();
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.AccountAggregation.SignOn");
    String str = "AccountAggregation.SignOn";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      paramSecureUser = com.ffusion.csil.handlers.AccountAggregation.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramSecureUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Institutions getInstitutions(SecureUser paramSecureUser, Institution paramInstitution, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.GetInstitutions";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      Institutions localInstitutions = com.ffusion.csil.handlers.AccountAggregation.getInstitutions(paramSecureUser, paramInstitution, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localInstitutions;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_2", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Institution getInstitutionAccountTypes(SecureUser paramSecureUser, Institution paramInstitution, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.GetInstitutionAccountTypes";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      Institution localInstitution = com.ffusion.csil.handlers.AccountAggregation.getInstitutionAccountTypes(paramSecureUser, paramInstitution, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localInstitution;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_3", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static com.ffusion.beans.aggregation.Account getRequiredAccountFields(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.GetRequiredAccountFields";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.handlers.AccountAggregation.getRequiredAccountFields(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccount;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_4", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static com.ffusion.beans.aggregation.Account addAccount(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AccountAggregation.AddAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.handlers.AccountAggregation.addAccount(paramSecureUser, paramAccount, paramHashMap);
      com.ffusion.beans.accounts.Account localAccount1 = new com.ffusion.beans.accounts.Account();
      jdMethod_int(paramAccount, localAccount1);
      AccountHandler.addAccount(paramSecureUser, localAccount1, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = O(paramAccount.getID());
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditMessage_1", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 1000);
      return localAccount;
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_5", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.aggregation.Account modifyAccount(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AccountAggregation.ModifyAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.handlers.AccountAggregation.modifyAccount(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = O(paramAccount.getID());
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditMessage_2", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 1001);
      return localAccount;
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_6", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.aggregation.Account deleteAccount(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AccountAggregation.DeleteAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.handlers.AccountAggregation.deleteAccount(paramSecureUser, paramAccount, paramHashMap);
      com.ffusion.beans.accounts.Account localAccount1 = new com.ffusion.beans.accounts.Account();
      jdMethod_int(paramAccount, localAccount1);
      if (paramSecureUser.getBusinessID() == 0) {
        AccountHandler.deleteAccount(paramSecureUser, localAccount1, paramSecureUser.getProfileID(), paramHashMap);
      } else {
        AccountHandler.deleteAccount(paramSecureUser, localAccount1, paramSecureUser.getBusinessID(), paramHashMap);
      }
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = O(paramAccount.getID());
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditMessage_3", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 1002);
      return localAccount;
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_7", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "AccountAggregation.GetAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      Accounts localAccounts = com.ffusion.csil.handlers.AccountAggregation.getAccounts(paramSecureUser, paramHashMap1, paramHashMap2);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccounts;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static com.ffusion.beans.aggregation.Account refreshAccount(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.RefreshAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      com.ffusion.beans.aggregation.Account localAccount = com.ffusion.csil.handlers.AccountAggregation.refreshAccount(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccount;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_9", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.GetTransactions";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0))
    {
      long l = System.currentTimeMillis();
      Transactions localTransactions = com.ffusion.csil.handlers.AccountAggregation.getTransactions(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localTransactions;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_10", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Transactions getTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.GetTransactions";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), new Entitlement("AccountAggregation", null, null)))
    {
      long l = System.currentTimeMillis();
      Transactions localTransactions = com.ffusion.csil.handlers.AccountAggregation.getTransactions(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localTransactions;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_11", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static AccountSummaries getSummary(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.getSummary";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0)) {
      return com.ffusion.csil.handlers.AccountAggregation.getSummary(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_12", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Transactions getPagedTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.getPagedTransactions";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0)) {
      return com.ffusion.csil.handlers.AccountAggregation.getPagedTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_13", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Transactions getNextTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.getNextTransactions";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0)) {
      return com.ffusion.csil.handlers.AccountAggregation.getNextTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_14", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Transactions getPreviousTransactions(SecureUser paramSecureUser, com.ffusion.beans.aggregation.Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountAggregation.PreviousTransactions";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ap0)) {
      return com.ffusion.csil.handlers.AccountAggregation.getPreviousTransactions(paramSecureUser, paramAccount, paramPagingContext, paramHashMap);
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = O(paramAccount.getID());
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountaggregation", "AuditEntFault_15", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  private static void jdMethod_int(com.ffusion.beans.aggregation.Account paramAccount, com.ffusion.beans.accounts.Account paramAccount1)
  {
    if ((paramAccount == null) || (paramAccount1 == null)) {
      return;
    }
    paramAccount1.setLocale(paramAccount.getLocale());
    paramAccount1.setType(com.ffusion.beans.aggregation.Account.getAccountTypeFromAggregationType(paramAccount.getTypeValue()));
    paramAccount1.setID(paramAccount.getNumber(), String.valueOf(paramAccount1.getTypeValue()));
    paramAccount1.setStatus(paramAccount.getStatus());
    paramAccount1.setNickName(paramAccount.getNickName());
    paramAccount1.setNumber(paramAccount.getNumber());
    paramAccount1.setBankName(paramAccount.getInstitutionName());
    paramAccount1.setRoutingNum(paramAccount.getInstitutionID());
    paramAccount1.setCurrencyCode((String)paramAccount.get("CURRENCY_CODE"));
    paramAccount1.setCoreAccount("0");
  }
  
  private static String jdMethod_case(String paramString, Locale paramLocale)
  {
    String str;
    try
    {
      str = AccountUtil.buildAccountDisplayText(paramString, paramLocale);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.log(Level.WARNING, "Unable to build account display text for " + paramString);
      localUtilException.printStackTrace();
      str = paramString;
    }
    return str;
  }
  
  private static Object O(String paramString)
  {
    try
    {
      return AccountUtil.buildLocalizableAccountID(paramString);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramString);
      localUtilException.printStackTrace();
    }
    return paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AccountAggregation
 * JD-Core Version:    0.7.0.1
 */