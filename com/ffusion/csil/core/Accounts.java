package com.ffusion.csil.core;

import com.ffusion.accountgroups.adapter.AccountGroupAdapter;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.csil.handlers.HandlerUtil;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.efs.adapters.entitlements.EntitlementUtil;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Accounts
  extends Initialize
{
  public static final String EXTRA_IS_ENROLLING_BUSINESS_FLAG = "IsEnrollingBusinessFlag";
  private static Entitlement aui = new Entitlement("BusinessProfileView", null, null);
  private static Entitlement aun = new Entitlement("BusinessProfileEdit", null, null);
  private static Entitlement auA = new Entitlement("BusinessProfileCreate", null, null);
  private static Entitlement auI = new Entitlement("BusinessProfileView", null, null);
  private static Entitlement auq = new Entitlement("UserAdmin", null, null);
  private static String auw;
  private static String aup;
  private static final String auy = "com.ffusion.util.logging.audit.accounts";
  private static final String auz = "AuditMessage_1";
  private static final String auh = "AuditMessage_2";
  private static final String aum = "AuditMessage_3";
  private static final String auk = "AuditMessage_4";
  private static final String auM = "AuditMessage_5";
  private static final String aul = "AuditMessage_6";
  private static final String aut = "AuditMessage_7";
  private static final String auK = "AuditMessage_8";
  private static final String aus = "AuditMessage_9";
  private static final String aug = "AuditMessage_10";
  private static final String aur = "AuditMessage_11";
  private static final String aux = "AuditMessage_12";
  private static final String auC = "AuditMessage_13";
  private static final String auH = "AuditMessage_14";
  private static final String auB = "AuditMessage_15";
  private static final String auG = "AuditMessage_16";
  private static final String auL = "AuditMessage_17";
  private static final String auE = "AuditMessage_18";
  private static final String auv = "AuditMessage_19";
  private static final String auD = "AuditMessage_20";
  private static final String auo = "AuditMessage_21";
  private static final String auF = "AuditMessage_22";
  private static final String auu = "AuditMessage_23";
  private static final String auj = "AuditMessage_24";
  private static final String auJ = "AuditMessage_25";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Accounts.initialize");
    AccountHandler.initialize(paramHashMap);
    auw = HandlerUtil.getGlobalAccountDisplaySize(paramHashMap);
    aup = HandlerUtil.getGlobalAccountMaxResultSize(paramHashMap);
  }
  
  public static Object getService()
  {
    return AccountHandler.getService();
  }
  
  public static String getAccountMaxResultSize()
  {
    return aup;
  }
  
  public static String getAccountDisplaySize()
  {
    return auw;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if (localObject != null)
      {
        String str = "Accounts.SignOn";
        long l = System.currentTimeMillis();
        paramSecureUser = AccountHandler.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
        PerfLog.log(str, l, true);
        debug(paramSecureUser, str);
      }
    }
    return paramSecureUser;
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if (localObject != null)
      {
        String str = "Accounts.ChangePIN";
        EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
        long l = System.currentTimeMillis();
        paramSecureUser = AccountHandler.changePIN(paramSecureUser, paramString1, paramString2, paramHashMap);
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_1", null);
        audit(paramSecureUser, localLocalizableString, null, 1200);
        PerfLog.log(str, l, true);
        debug(paramSecureUser, str);
      }
    }
    return paramSecureUser;
  }
  
  public static Account getAccountAddress(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Accounts.GetAccountAddress";
    if ((paramSecureUser.getUserType() == 1) || ((paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auI))))
    {
      long l = System.currentTimeMillis();
      paramAccount = AccountHandler.getAccountAddress(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramAccount;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_9", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static com.ffusion.beans.accounts.Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetAccounts";
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts = AccountHandler.getAccounts(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static com.ffusion.beans.accounts.Accounts searchAccounts(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Accounts.searchAccounts";
    if ((paramSecureUser.getProfileID() != paramInt) && (paramSecureUser.getPrimaryUserID() != paramInt) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aui)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aun)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auA)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auq)))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_10", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts = AccountHandler.searchAccounts(paramSecureUser, paramAccount, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    User localUser1 = new User();
    localUser1.setId(Integer.toString(paramSecureUser.getProfileID()));
    User localUser2 = UserAdminHandler.getUserById(paramSecureUser, localUser1, paramHashMap);
    String str = "Banking.addAccount";
    long l = System.currentTimeMillis();
    paramAccount = AccountHandler.addAccount(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramAccount;
  }
  
  public static void addAccounts(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.addAccount";
    addAccounts(paramSecureUser, paramAccounts, true, true, paramInt, paramHashMap);
  }
  
  public static void addAccounts(SecureUser paramSecureUser, com.ffusion.beans.accounts.Accounts paramAccounts, boolean paramBoolean1, boolean paramBoolean2, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Banking.addAccount";
    if ((paramSecureUser.getProfileID() != paramInt) && (paramSecureUser.getPrimaryUserID() != paramInt) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aun)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auA)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auq)))
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_12", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.beans.business.Business localBusiness = null;
    AccountHandler.addAccounts(paramSecureUser, paramAccounts, paramInt, paramHashMap);
    Object localObject2;
    Object localObject1;
    if (!paramBoolean2)
    {
      localBusiness = Business.jdMethod_int(paramSecureUser, paramInt, paramHashMap);
      if (localBusiness != null)
      {
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
        for (int j = 0; j < paramAccounts.size(); j++)
        {
          localObject2 = new Entitlement();
          ((Entitlement)localObject2).setObjectType("Account");
          ((Entitlement)localObject2).setOperationName("Access");
          ((Entitlement)localObject2).setObjectId(EntitlementsUtil.getEntitlementObjectId((Account)paramAccounts.get(j)));
          localEntitlements.add(localObject2);
        }
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, localBusiness.getEntitlementGroup(), localEntitlements, 1, paramBoolean1, paramHashMap);
        if (!paramBoolean1)
        {
          localObject1 = localBusiness.getEntitlementGroup();
          localObject2 = AutoEntitleUtil.buildPerAccountEntitlementsList(paramAccounts, EntitlementsUtil.getTypePropertyCategoryValue(((EntitlementGroup)localObject1).getEntGroupType()));
          AutoEntitleAdmin.restrictFeatures(paramSecureUser, (EntitlementGroup)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, paramHashMap);
        }
      }
    }
    PerfLog.log(str1, l, true);
    if (!paramBoolean2) {
      for (int i = 0; i < paramAccounts.size(); i++)
      {
        localObject1 = (Account)paramAccounts.get(i);
        localObject2 = new Object[2];
        localObject2[0] = ((Account)localObject1).getRoutingNum();
        localObject2[1] = ((Account)localObject1).buildLocalizableAccountID();
        String str2 = TrackingIDGenerator.GetNextID();
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_23", (Object[])localObject2);
        audit(paramSecureUser, localLocalizableString2, localBusiness == null ? null : localBusiness.getId(), str2, 1203);
      }
    }
    debug(paramSecureUser, str1);
  }
  
  public static Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.modifyAccount";
    User localUser1 = new User();
    localUser1.setId(Integer.toString(paramSecureUser.getProfileID()));
    User localUser2 = UserAdminHandler.getUserById(paramSecureUser, localUser1, paramHashMap);
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    paramAccount = AccountHandler.modifyAccount(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString = null;
    if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getBusinessID() == 0))
    {
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramAccount.getDisplayText();
      arrayOfObject[1] = paramAccount.getNickName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_2", arrayOfObject);
    }
    else
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAccount.getDisplayText();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_3", arrayOfObject);
    }
    com.ffusion.beans.business.Business localBusiness = null;
    if (paramHashMap != null) {
      localBusiness = (com.ffusion.beans.business.Business)paramHashMap.get("MODIFY_ACCOUNT_BUSINESS");
    }
    if (localBusiness != null) {
      audit(paramSecureUser, localLocalizableString, localBusiness.getId(), TrackingIDGenerator.GetNextID(), 1201);
    } else {
      audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 1201);
    }
    debug(paramSecureUser, str);
    return paramAccount;
  }
  
  public static ArrayList getRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Accounts.saveRestrictedCalculations";
    StringBuffer localStringBuffer = new StringBuffer();
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aui)) {
      return AccountHandler.getRestrictedCalculations(paramSecureUser, paramAccount, paramHashMap);
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramAccount.getRoutingNum();
    arrayOfObject[1] = paramAccount.getID();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_14", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void saveRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, ArrayList paramArrayList, HashMap paramHashMap, com.ffusion.beans.business.Business paramBusiness)
    throws CSILException
  {
    String str1 = "Accounts.saveRestrictedCalculations";
    StringBuffer localStringBuffer = new StringBuffer();
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aun))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      AccountHandler.saveRestrictedCalculations(paramSecureUser, paramAccount, paramArrayList, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramAccount.getRoutingNum();
      try
      {
        arrayOfObject2[1] = AccountUtil.buildAccountDisplayText(paramAccount.getID(), paramSecureUser.getLocale());
      }
      catch (UtilException localUtilException2)
      {
        DebugLog.throwing("Error while constructing account display string.", localUtilException2);
        arrayOfObject2[1] = paramAccount.getID();
      }
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_4", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, paramBusiness.getIdValue(), str2, 1202);
      debug(paramSecureUser, str1);
    }
    else
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramAccount.getRoutingNum();
      try
      {
        arrayOfObject1[1] = AccountUtil.buildAccountDisplayText(paramAccount.getID(), paramSecureUser.getLocale());
      }
      catch (UtilException localUtilException1)
      {
        DebugLog.throwing("Error while constructing account display string.", localUtilException1);
        arrayOfObject1[1] = paramAccount.getID();
      }
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_15", arrayOfObject1);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static Account modifyAccountById(SecureUser paramSecureUser, int paramInt, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.modifyAccountById";
    if ((paramSecureUser.getProfileID() != paramInt) && (paramSecureUser.getPrimaryUserID() != paramInt) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aun)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auA)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auq)))
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_13", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    paramAccount = AccountHandler.modifyAccountById(paramSecureUser, paramInt, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_4", arrayOfObject);
    if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getBusinessID() == 0))
    {
      arrayOfObject = new Object[3];
      arrayOfObject[0] = paramAccount.getRoutingNum();
      arrayOfObject[1] = paramAccount.getID();
      arrayOfObject[2] = paramAccount.getNickName();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_5", arrayOfObject);
    }
    else
    {
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramAccount.getRoutingNum();
      arrayOfObject[1] = paramAccount.getID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_6", arrayOfObject);
    }
    audit(paramSecureUser, localLocalizableString2, "" + paramInt, TrackingIDGenerator.GetNextID(), 1201);
    return paramAccount;
  }
  
  public static Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Account localAccount = null;
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    String str1 = "Banking.deleteAccount";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    if ((paramSecureUser.getProfileID() != paramInt) && (paramSecureUser.getPrimaryUserID() != paramInt) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aun)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auA)) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), auq)))
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_16", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    if (paramAccount != null)
    {
      str2 = paramAccount.getID();
      if ((str2 == null) || (str2.length() == 0)) {
        localAccounts = getAccountsById(paramSecureUser, paramInt, paramHashMap);
      } else {
        try
        {
          str3 = paramAccount.getBankID();
          if ((str3 == null) || (str3.trim().length() == 0)) {
            paramAccount.setBankID(paramSecureUser.getBankID());
          }
          localAccounts = searchAccounts(paramSecureUser, paramAccount, paramInt, paramHashMap);
          if ((localAccounts == null) || (localAccounts.size() != 1)) {
            throw new CSILException(str1, 18000);
          }
          localAccount = (Account)localAccounts.get(0);
          str4 = paramAccount.getRoutingNum();
          if (((str4 != null) && (str4.equals(localAccount.getRoutingNum()))) || ((str4 == null) && (localAccount.getRoutingNum() == null))) {
            localAccounts = null;
          } else {
            throw new CSILException(str1, 18000);
          }
        }
        catch (CSILException localCSILException)
        {
          throw new CSILException(str1, 18000);
        }
      }
    }
    else
    {
      localAccounts = getAccountsById(paramSecureUser, paramInt, paramHashMap);
    }
    paramAccount = AccountHandler.deleteAccount(paramSecureUser, paramAccount, paramInt, paramHashMap);
    if (localAccounts != null) {
      try
      {
        EntitlementGroup localEntitlementGroup1 = f(paramInt);
        if (localEntitlementGroup1 != null)
        {
          Iterator localIterator = localAccounts.iterator();
          while (localIterator.hasNext())
          {
            localAccount = (Account)localIterator.next();
            if (localAccount != null) {
              EntitlementsUtil.removeEntitlementsAndLimitsForObjectUnsafe(localEntitlementGroup1.getGroupId(), "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
            }
          }
        }
      }
      catch (Exception localException1) {}
    } else {
      try
      {
        EntitlementGroup localEntitlementGroup2 = f(paramInt);
        if (localEntitlementGroup2 != null) {
          EntitlementsUtil.removeEntitlementsAndLimitsForObjectUnsafe(localEntitlementGroup2.getGroupId(), "Account", EntitlementsUtil.getEntitlementObjectId(paramAccount));
        }
      }
      catch (Exception localException2) {}
    }
    PerfLog.log(str1, l, true);
    Boolean localBoolean = (Boolean)paramHashMap.get("IsEnrollingBusinessFlag");
    boolean bool = localBoolean == null ? true : localBoolean.booleanValue();
    if (!bool)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramAccount.getRoutingNum();
      arrayOfObject[1] = paramAccount.buildLocalizableAccountID();
      String str5 = TrackingIDGenerator.GetNextID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_24", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, "" + paramInt, str5, 1204);
    }
    debug(paramSecureUser, str1);
    return paramAccount;
  }
  
  public static com.ffusion.beans.accounts.Accounts getAccountsById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetAccountsById";
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts = AccountHandler.getAccountsById(paramSecureUser, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static com.ffusion.beans.accounts.Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.GetAccountsByBusinessEmployee";
    long l = System.currentTimeMillis();
    com.ffusion.beans.accounts.Accounts localAccounts = AccountHandler.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static com.ffusion.beans.accounts.Accounts getEntitledAccountsByBusinessEmployeeNoCache(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getEntitledAccountsByBusinessEmployeeNoCache";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = Entitlements.getCumulativeEntitlementsNoCache(localEntitlementGroupMember);
    Object localObject1 = AccountHandler.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { null });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    com.ffusion.beans.accounts.Accounts localAccounts = new com.ffusion.beans.accounts.Accounts();
    Iterator localIterator = ((com.ffusion.beans.accounts.Accounts)localObject1).iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      EntitlementsUtil.appendAccountGroups(localMultiEntitlement, paramSecureUser.getBusinessID());
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2;
      try
      {
        localEntitlements2 = EntitlementUtil.getEntitlements(localMultiEntitlement);
      }
      catch (EntitlementException localEntitlementException)
      {
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.accounts", "AuditMessage_22", null);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 20001);
      }
      int i = 1;
      Object localObject2 = localEntitlements2.iterator();
      while (((Iterator)localObject2).hasNext()) {
        if (!jdMethod_for(localEntitlementGroupMember, (Entitlement)((Iterator)localObject2).next(), localEntitlements1)) {
          i = 0;
        }
      }
      if (i != 0) {
        localAccounts.add(localAccount);
      }
    }
    localObject1 = localAccounts;
    return localObject1;
  }
  
  public static Account isAccountEntitledNoCache(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Accounts.isAccountEntitledNoCache";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = Entitlements.getCumulativeEntitlementsNoCache(localEntitlementGroupMember);
    Entitlement localEntitlement1 = new Entitlement();
    localEntitlement1.setOperationName("Access");
    localEntitlement1.setObjectType("Account");
    localEntitlement1.setObjectId(EntitlementsUtil.getEntitlementObjectId(paramAccount));
    int i = 0;
    if (jdMethod_for(localEntitlementGroupMember, localEntitlement1, localEntitlements))
    {
      i = 1;
    }
    else
    {
      ArrayList localArrayList1 = null;
      Object localObject;
      try
      {
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(paramAccount.getRoutingNum() + "-" + paramAccount.getID());
        localArrayList1 = AccountGroupAdapter.getAccountGroupIds(paramSecureUser.getBusinessID(), localArrayList2, new HashMap());
      }
      catch (BusinessAccountGroupException localBusinessAccountGroupException)
      {
        localObject = new CSILException(20034, localBusinessAccountGroupException.code, localBusinessAccountGroupException);
        DebugLog.throwing(str, (Throwable)localObject);
        throw ((Throwable)localObject);
      }
      if (localArrayList1 != null)
      {
        Entitlement localEntitlement2 = new Entitlement();
        localEntitlement2.setOperationName("Access");
        localEntitlement2.setObjectType("AccountGroup");
        localObject = localArrayList1.iterator();
        while (((Iterator)localObject).hasNext())
        {
          localEntitlement2.setObjectId((String)((Iterator)localObject).next());
          if (jdMethod_for(localEntitlementGroupMember, localEntitlement2, localEntitlements)) {
            i = 1;
          }
        }
      }
    }
    if (i != 0) {
      return paramAccount;
    }
    return null;
  }
  
  private static boolean jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
  {
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      if (!jdMethod_int(paramEntitlement, localEntitlement)) {
        return false;
      }
    }
    return true;
  }
  
  private static boolean jdMethod_int(Entitlement paramEntitlement1, Entitlement paramEntitlement2)
  {
    int i = 0;
    if (paramEntitlement2.getOperationName() != null) {
      i += 4;
    }
    if (paramEntitlement2.getObjectType() != null) {
      i += 2;
    }
    if (paramEntitlement2.getObjectId() != null) {
      i++;
    }
    switch (i)
    {
    case 0: 
      return false;
    case 2: 
      return !paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType());
    case 3: 
      return (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType())) || (!paramEntitlement2.getObjectId().equals(paramEntitlement1.getObjectId()));
    case 4: 
      return !paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName());
    case 6: 
      return (!paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName())) || (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType()));
    case 7: 
      return (!paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName())) || (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType())) || (!paramEntitlement2.getObjectId().equals(paramEntitlement1.getObjectId()));
    }
    return false;
  }
  
  public static com.ffusion.beans.accounts.Accounts getEntitledAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getEntitledAccountsByBusinessEmployee";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    long l = System.currentTimeMillis();
    Object localObject = getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { null });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    com.ffusion.beans.accounts.Accounts localAccounts = new com.ffusion.beans.accounts.Accounts();
    Iterator localIterator = ((com.ffusion.beans.accounts.Accounts)localObject).iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
        localAccounts.add(localAccount);
      }
    }
    localObject = localAccounts;
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localObject;
  }
  
  public static com.ffusion.beans.accounts.Accounts syncBusinessAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Object paramObject, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.syncBusinessAccounts";
    long l = System.currentTimeMillis();
    HashMap localHashMap = new HashMap();
    localHashMap.put("SERVICE", paramObject);
    com.ffusion.beans.accounts.Accounts localAccounts1 = Banking.getAccounts(paramSecureUser, localHashMap);
    com.ffusion.beans.accounts.Accounts localAccounts2 = AccountHandler.mergeAccounts(paramSecureUser, localAccounts1, paramBusiness.getIdValue(), paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts2;
  }
  
  public static com.ffusion.beans.accounts.Accounts getBackendAccounts(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, Object paramObject, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getBusinessAccounts";
    long l = System.currentTimeMillis();
    HashMap localHashMap = new HashMap();
    localHashMap.put("SERVICE", paramObject);
    com.ffusion.beans.accounts.Accounts localAccounts = Banking.getAccounts(paramSecureUser, localHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  private static EntitlementGroup f(int paramInt)
    throws Exception
  {
    EntitlementGroup localEntitlementGroup = null;
    com.ffusion.beans.business.Business localBusiness = null;
    localBusiness = BusinessAdapter.getBusiness(paramInt);
    if (localBusiness != null)
    {
      EntitlementGroupMember localEntitlementGroupMember = Entitlements.getMember(localBusiness.getEntitlementGroupMember());
      localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroupMember.getEntitlementGroupId());
    }
    return localEntitlementGroup;
  }
  
  private static void jdMethod_for(StringBuffer paramStringBuffer, Account paramAccount)
  {
    paramStringBuffer.append(paramAccount.getRoutingNum());
    paramStringBuffer.append(":");
    try
    {
      paramStringBuffer.append(AccountUtil.buildAccountDisplayText(paramAccount.getID(), paramAccount.getLocale()));
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing("Error while constructing account display string.", localUtilException);
      paramStringBuffer.append(paramAccount.getID());
    }
  }
  
  public static Businesses getBusinessesForAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Accounts.getBusinessesForAccount";
    long l = System.currentTimeMillis();
    Businesses localBusinesses = AccountHandler.getBusinessesForAccount(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localBusinesses;
  }
  
  public static void fillAccountCollection(com.ffusion.beans.accounts.Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Accounts.fillAccountCollection(Account, HashMap)";
    long l = System.currentTimeMillis();
    AccountHandler.fillAccountCollection(paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Accounts
 * JD-Core Version:    0.7.0.1
 */