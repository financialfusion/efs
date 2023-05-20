package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.BlockedAccounts;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.logging.Level;

public class BlockedAccts
  extends Initialize
{
  private static final Entitlement ax5 = new Entitlement("BC Blocked Accounts Create/Edit/Delete", null, null);
  private static final Entitlement ax1 = new Entitlement("BC Blocked Accounts View", null, null);
  private static final String ayc = "com.ffusion.util.logging.audit.blockedaccounts";
  private static final String ax4 = "AuditMessage_1";
  private static final String aya = "AuditMessage_2";
  private static final String ayd = "AuditMessage_3";
  private static final String ayb = "AuditMessage_4";
  private static final String ax6 = "AuditMessage_5";
  private static final String ax9 = "AuditMessage_6";
  private static final String ax2 = "AuditMessage_7";
  private static final String ax7 = "AuditMessage_8";
  private static final String ax3 = "AuditMessage_9";
  private static final String ax8 = "AuditMessage_10";
  public static final String USER_NAME_KEY = "UserName";
  public static final String NEW_USER_NAME_KEY = "NewUserName";
  public static final String OLD_USER_NAME_KEY = "OldUserName";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.BlockedAccts.initialize");
    BlockedAccounts.initialize(paramHashMap);
  }
  
  public static void addBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    long l = 0L;
    String str1 = "com.ffusion.csil.core.BlockedAccts.addBlockedAccount";
    String str2 = null;
    if (paramSecureUser != null)
    {
      if ((paramSecureUser.getUserType() != 2) || ((paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ax5))))
      {
        l = System.currentTimeMillis();
        BlockedAccounts.addBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
        PerfLog.log(str1, l, true);
        if (paramHashMap == null) {
          str2 = null;
        } else {
          str2 = (String)paramHashMap.get("UserName");
        }
        jdMethod_for(paramSecureUser, paramBlockedAccount, str2);
      }
      else
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", "AuditMessage_1", null);
        Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    else
    {
      String str3 = "The SecureUser specified is the wrong type or is null.";
      DebugLog.log(Level.FINE, str3);
      throw new CSILException(str1, 103, str3);
    }
  }
  
  public static void deleteBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    long l = 0L;
    String str1 = "com.ffusion.csil.core.BlockedAccts.deleteBlockedAccount";
    String str2 = null;
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ax5))
      {
        l = System.currentTimeMillis();
        BlockedAccounts.deleteBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
        PerfLog.log(str1, l, true);
        if (paramHashMap == null) {
          str2 = null;
        } else {
          str2 = (String)paramHashMap.get("UserName");
        }
        jdMethod_new(paramSecureUser, paramBlockedAccount, str2);
      }
      else
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", "AuditMessage_2", null);
        Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    else
    {
      String str3 = "The SecureUser specified is the wrong type or is null.";
      DebugLog.log(Level.FINE, str3);
      throw new CSILException(str1, 103, str3);
    }
  }
  
  public static void modifyBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount1, BlockedAccount paramBlockedAccount2, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    long l = 0L;
    String str1 = "com.ffusion.csil.core.BlockedAccts.modifyBlockedAccount";
    String str2 = null;
    String str3 = null;
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ax5))
      {
        l = System.currentTimeMillis();
        BlockedAccounts.modifyBlockedAccount(paramSecureUser, paramBlockedAccount1, paramBlockedAccount2, paramHashMap);
        PerfLog.log(str1, l, true);
        if (paramHashMap == null)
        {
          str2 = null;
          str3 = null;
        }
        else
        {
          str2 = (String)paramHashMap.get("NewUserName");
          str3 = (String)paramHashMap.get("OldUserName");
        }
        jdMethod_for(paramSecureUser, paramBlockedAccount1, paramBlockedAccount2, str3, str2);
      }
      else
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", "AuditMessage_3", null);
        Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    else
    {
      String str4 = "The SecureUser specified is the wrong type or is null.";
      DebugLog.log(Level.FINE, str4);
      throw new CSILException(str1, 103, str4);
    }
  }
  
  public static int getNumBlockedAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    int i = 0;
    long l = 0L;
    String str1 = "com.ffusion.csil.core.BlockedAccts.getNumBlockedAccounts";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ax1))
      {
        l = System.currentTimeMillis();
        i = BlockedAccounts.getNumBlockedAccounts(paramSecureUser, paramHashMap);
        PerfLog.log(str1, l, true);
        return i;
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", "AuditMessage_4", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    String str2 = "The SecureUser specified is the wrong type or is null.";
    DebugLog.log(Level.FINE, str2);
    throw new CSILException(str1, 103, str2);
  }
  
  public static BlockedAccountSearchResults getBlockedAccounts(SecureUser paramSecureUser, BlockedAccountSearchCriteria paramBlockedAccountSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    BlockedAccountSearchResults localBlockedAccountSearchResults = null;
    long l = 0L;
    String str1 = "com.ffusion.csil.core.BlockedAccts.getBlockedAccounts";
    if ((paramSecureUser != null) && (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ax1))
      {
        l = System.currentTimeMillis();
        localBlockedAccountSearchResults = BlockedAccounts.getBlockedAccounts(paramSecureUser, paramBlockedAccountSearchCriteria, paramHashMap);
        PerfLog.log(str1, l, true);
        return localBlockedAccountSearchResults;
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", "AuditMessage_4", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    String str2 = "The SecureUser specified is the wrong type or is null.";
    DebugLog.log(Level.FINE, str2);
    throw new CSILException(str1, 103, str2);
  }
  
  public static boolean isBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    long l = 0L;
    String str = "com.ffusion.csil.core.BlockedAccts.isBlockedAccount";
    l = System.currentTimeMillis();
    bool = BlockedAccounts.isBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
    PerfLog.log(str, l, true);
    return bool;
  }
  
  public static String getStrippedAccountNumber(String paramString, HashMap paramHashMap)
    throws CSILException
  {
    long l = 0L;
    String str1 = null;
    String str2 = "com.ffusion.csil.core.BlockedAccts.getStrippedAccountNumber";
    l = System.currentTimeMillis();
    str1 = BlockedAccounts.getStrippedAccountNumber(paramString, paramHashMap);
    PerfLog.log(str2, l, true);
    return str1;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, String paramString)
  {
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = new Object[3];
    String str = null;
    if (paramString == null) {
      str = "AuditMessage_6";
    } else {
      str = "AuditMessage_5";
    }
    arrayOfObject[0] = paramBlockedAccount.getRoutingNumber().trim();
    arrayOfObject[1] = paramBlockedAccount.getAccountNumber().trim();
    arrayOfObject[2] = paramString;
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", str, arrayOfObject);
    Initialize.audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 6100);
  }
  
  private static void jdMethod_new(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, String paramString)
  {
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = new Object[3];
    String str = null;
    if (paramString == null) {
      str = "AuditMessage_8";
    } else {
      str = "AuditMessage_7";
    }
    arrayOfObject[0] = paramBlockedAccount.getRoutingNumber().trim();
    arrayOfObject[1] = paramBlockedAccount.getAccountNumber().trim();
    arrayOfObject[2] = paramString;
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", str, arrayOfObject);
    Initialize.audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 6101);
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, String paramString)
  {
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = new Object[4];
    String str = null;
    if (paramString == null) {
      str = "AuditMessage_10";
    } else {
      str = "AuditMessage_9";
    }
    arrayOfObject[0] = paramBlockedAccount.getRoutingNumber().trim();
    arrayOfObject[1] = paramBlockedAccount.getAccountNumber().trim();
    arrayOfObject[2] = paramString;
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.blockedaccounts", str, arrayOfObject);
    Initialize.audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 6102);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount1, BlockedAccount paramBlockedAccount2, String paramString1, String paramString2)
  {
    if (paramBlockedAccount2.isEquivalentExcludingBankName(paramBlockedAccount1))
    {
      jdMethod_int(paramSecureUser, paramBlockedAccount2, paramString2);
    }
    else
    {
      jdMethod_new(paramSecureUser, paramBlockedAccount1, paramString1);
      jdMethod_for(paramSecureUser, paramBlockedAccount2, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BlockedAccts
 * JD-Core Version:    0.7.0.1
 */