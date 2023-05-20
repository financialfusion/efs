package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class TreasuryDirect
  extends Initialize
{
  private static final String aEa = "com.ffusion.util.logging.audit.treasurydirect";
  private static final Entitlement aD3 = new Entitlement("BusinessProfileEdit", null, null);
  private static final Entitlement aD0 = new Entitlement("BusinessProfileView", null, null);
  private static final Entitlement aD1 = new Entitlement("Information Reporting", null, null);
  private static final String aEb = "AuditMessage_1";
  private static final String aD5 = "AuditMessage_2";
  private static final String aDZ = "AuditMessage_3";
  private static final String aD7 = "AuditMessage_4";
  private static final String aD9 = "AuditMessage_5";
  private static final String aD8 = "AuditMessage_6";
  private static final String aD2 = "AuditMessage_7";
  private static final String aD4 = "com.ffusion.util.logging.audit.banking";
  private static final String aD6 = "AuditMessage_31";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.TreasuryDirect.initialize");
    com.ffusion.csil.handlers.TreasuryDirect.initialize(paramHashMap);
  }
  
  public static void addSubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.core.TreasuryDirect.addSubAccounts";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str1, 300, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD3)))
    {
      localObject = new CSILException(str1, 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_4", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.TreasuryDirect.addSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject = new Object[5];
      LocalizableString localLocalizableString2 = null;
      Account localAccount = null;
      arrayOfObject[4] = paramBusiness.getBusinessName();
      arrayOfObject[2] = paramAccount.getRoutingNum();
      try
      {
        arrayOfObject[3] = AccountUtil.buildLocalizableAccountID(paramAccount.getID());
      }
      catch (UtilException localUtilException1)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramAccount.getID());
        localUtilException1.printStackTrace();
        arrayOfObject[3] = paramAccount.getID();
      }
      int i = 0;
      int j = paramAccounts.size();
      while (i < j)
      {
        localAccount = (Account)paramAccounts.get(i);
        arrayOfObject[0] = localAccount.getRoutingNum();
        try
        {
          arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount.getID());
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount.getID());
          localUtilException2.printStackTrace();
          arrayOfObject[1] = localAccount.getID();
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_3", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramBusiness.getIdValue(), str2, 6000);
        localLocalizableString2 = null;
        i++;
      }
    }
  }
  
  public static void deleteSubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.core.TreasuryDirect.deleteSubAccounts";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str1, 300, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD3)))
    {
      localObject = new CSILException(str1, 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_4", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.TreasuryDirect.deleteSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject = new Object[5];
      LocalizableString localLocalizableString2 = null;
      Account localAccount = null;
      arrayOfObject[4] = paramBusiness.getBusinessName();
      arrayOfObject[2] = paramAccount.getRoutingNum();
      try
      {
        arrayOfObject[3] = AccountUtil.buildLocalizableAccountID(paramAccount.getID());
      }
      catch (UtilException localUtilException1)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramAccount.getID());
        localUtilException1.printStackTrace();
        arrayOfObject[3] = paramAccount.getID();
      }
      int i = 0;
      int j = paramAccounts.size();
      while (i < j)
      {
        localAccount = (Account)paramAccounts.get(i);
        arrayOfObject[0] = localAccount.getRoutingNum();
        try
        {
          arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount.getID());
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount.getID());
          localUtilException2.printStackTrace();
          arrayOfObject[1] = localAccount.getID();
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_5", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramBusiness.getIdValue(), str2, 6001);
        localLocalizableString2 = null;
        i++;
      }
    }
  }
  
  public static void deleteMasterAccount(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.core.TreasuryDirect.deleteMasterAccount";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str1, 300, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD3)))
    {
      localObject = new CSILException(str1, 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_4", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.TreasuryDirect.deleteMasterAccount(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[3];
    LocalizableString localLocalizableString2 = null;
    arrayOfObject[2] = paramBusiness.getBusinessName();
    arrayOfObject[0] = paramAccount.getRoutingNum();
    try
    {
      arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(paramAccount.getID());
    }
    catch (UtilException localUtilException)
    {
      DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramAccount.getID());
      localUtilException.printStackTrace();
      arrayOfObject[1] = paramAccount.getID();
    }
    localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_6", arrayOfObject);
    audit(paramSecureUser, localLocalizableString2, paramBusiness.getIdValue(), str2, 6002);
  }
  
  public static Accounts getSubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    Accounts localAccounts = null;
    String str = "com.ffusion.csil.core.TreasuryDirect.getSubAccounts";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str, 300, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localObject = new CSILException(str, 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    localAccounts = com.ffusion.csil.handlers.TreasuryDirect.getSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static void modifySubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.core.TreasuryDirect.modifySubAccounts";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str1, 300, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD3)))
    {
      localObject = new CSILException(str1, 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_4", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.TreasuryDirect.modifySubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject = new Object[5];
      LocalizableString localLocalizableString2 = null;
      Account localAccount = null;
      arrayOfObject[4] = paramBusiness.getBusinessName();
      arrayOfObject[2] = paramAccount.getRoutingNum();
      try
      {
        arrayOfObject[3] = AccountUtil.buildLocalizableAccountID(paramAccount.getID());
      }
      catch (UtilException localUtilException1)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramAccount.getID());
        localUtilException1.printStackTrace();
        arrayOfObject[3] = paramAccount.getID();
      }
      int i = 0;
      int j = paramAccounts.size();
      while (i < j)
      {
        localAccount = (Account)paramAccounts.get(i);
        arrayOfObject[0] = localAccount.getRoutingNum();
        try
        {
          arrayOfObject[1] = AccountUtil.buildLocalizableAccountID(localAccount.getID());
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + localAccount.getID());
          localUtilException2.printStackTrace();
          arrayOfObject[1] = localAccount.getID();
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_7", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramBusiness.getIdValue(), str2, 6003);
        localLocalizableString2 = null;
        i++;
      }
    }
  }
  
  public static Accounts filterAvailableSubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.core.TreasuryDirect.filterAvailableSubAccounts";
    Accounts localAccounts = null;
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str, 300, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localObject = new CSILException(str, 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    localAccounts = com.ffusion.csil.handlers.TreasuryDirect.filterAvailableSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static int getNumMasterAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "TreasuryDirect.getNumMasterAccounts";
    LocalizableString localLocalizableString;
    if ((paramSecureUser.getUserType() == 1) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD1)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    if ((paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    int i = com.ffusion.csil.handlers.TreasuryDirect.getNumMasterAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return i;
  }
  
  public static int getNumSubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "TreasuryDirect.getNumSubAccounts";
    LocalizableString localLocalizableString;
    if ((paramSecureUser.getUserType() == 1) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD1)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    if ((paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    int i = com.ffusion.csil.handlers.TreasuryDirect.getNumSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return i;
  }
  
  public static Accounts filterNonSubAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "TreasuryDirect.filterNonSubAccounts";
    LocalizableString localLocalizableString;
    if ((paramSecureUser.getUserType() == 1) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD1)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    if ((paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Accounts localAccounts = com.ffusion.csil.handlers.TreasuryDirect.filterNonSubAccounts(paramSecureUser, paramBusiness, paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static HashMap getMasterSubStatistics(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "TreasuryDirect.getMasterSubStatistics";
    LocalizableString localLocalizableString;
    if ((paramSecureUser.getUserType() == 1) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD1)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    if ((paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    HashMap localHashMap = com.ffusion.csil.handlers.TreasuryDirect.getMasterSubStatistics(paramSecureUser, paramBusiness, paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localHashMap;
  }
  
  public static Accounts getMasterAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.core.TreasuryDirect.getMasterAccounts";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str, 103, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localObject = new CSILException(str, 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    Accounts localAccounts = com.ffusion.csil.handlers.TreasuryDirect.getMasterAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static Account getSubAccount(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Account paramAccount1, Account paramAccount2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.core.TreasuryDirect.getSubAccount";
    Object localObject;
    if (paramSecureUser == null)
    {
      localObject = "The SecureUser specified is null.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new CSILException(str, 103, (String)localObject);
    }
    if ((paramSecureUser.getUserType() != 2) || (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aD0)))
    {
      localObject = new CSILException(str, 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.treasurydirect", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw ((Throwable)localObject);
    }
    long l = System.currentTimeMillis();
    Account localAccount = com.ffusion.csil.handlers.TreasuryDirect.getSubAccount(paramSecureUser, paramBusiness, paramAccount1, paramAccount2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccount;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.core.TreasuryDirect.getReportData";
    IReportResult localIReportResult = null;
    long l = System.currentTimeMillis();
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
    localBusiness1.setId(paramSecureUser.getBusinessID());
    localBusiness1.setAffiliateBankID(paramSecureUser.getAffiliateIDValue());
    com.ffusion.beans.business.Business localBusiness2 = com.ffusion.csil.handlers.Business.getBusiness(paramSecureUser, localBusiness1, paramHashMap);
    if (localBusiness2 == null) {
      throw new CSILException(str1, 4011);
    }
    Locale localLocale = paramSecureUser.getLocale();
    Businesses localBusinesses = new Businesses(localLocale);
    localBusinesses.add(localBusiness2);
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("BusinessCollection", localBusinesses);
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    localIReportResult = com.ffusion.csil.handlers.TreasuryDirect.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    int i = -1;
    String str3 = paramReportCriteria.getReportOptions().getProperty("TITLE");
    String str4 = (String)paramReportCriteria.getSearchCriteria().get("DataClassification");
    String str5 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = str3;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banking", "AuditMessage_31", arrayOfObject);
    if ((str4 != null) && (str4.equals("I"))) {
      i = 5418;
    } else {
      i = 5438;
    }
    audit(paramSecureUser, localLocalizableString, str5, i);
    PerfLog.log(str1, l, true);
    return localIReportResult;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.TreasuryDirect
 * JD-Core Version:    0.7.0.1
 */