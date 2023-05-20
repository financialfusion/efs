package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

public class ExternalTransferAdmin
  extends Initialize
{
  private static Entitlement atE = new Entitlement("External Transfers", null, null);
  private static Entitlement atT = new Entitlement("External Transfers To", null, null);
  private static Entitlement atO = new Entitlement("BC External Transfers Management", null, null);
  private static Entitlement atJ = new Entitlement("External Transfers", null, null);
  private static final String atM = "com.ffusion.util.logging.audit.exttransfer";
  private static final String atU = "AuditMessage_1";
  private static final String atV = "AuditMessage_2";
  private static final String atY = "AuditMessage_3";
  private static final String atN = "AuditMessage_4";
  private static final String atX = "AuditMessage_5";
  private static final String atI = "AuditMessage_6";
  private static final String at0 = "AuditMessage_7";
  private static final String atG = "AuditMessage_8";
  private static final String atR = "AuditMessage_9";
  private static final String atK = "AuditMessage_10";
  private static final String atQ = "AuditMessage_11";
  private static final String atH = "AuditMessage_12";
  private static final String atW = "AuditMessage_13";
  private static final String atF = "AuditMessage_14";
  private static final String atD = "AuditMessage_15";
  private static final String atZ = "AuditMessage_16";
  private static final String atP = "AuditMessage_17";
  private static final String atL = "AuditMessage_18";
  private static final String atS = "AuditMessage_19";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.ExternalTransferAdmin.initialize");
    com.ffusion.csil.handlers.ExternalTransferAdmin.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.ExternalTransferAdmin.getService();
  }
  
  public static Accounts getAccountsForExtTransfer(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ExternalTransferAdmin.GetAccountsForExtTransfer";
    boolean bool1 = false;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO))
    {
      long l = System.currentTimeMillis();
      Accounts localAccounts1 = new Accounts(paramSecureUser.getLocale());
      Accounts localAccounts2 = com.ffusion.csil.handlers.ExternalTransferAdmin.getAccountsForExtTransfer(paramSecureUser, paramInt, paramHashMap);
      Iterator localIterator;
      Object localObject;
      Account localAccount;
      if (localAccounts2 != null)
      {
        localAccounts2.setFilter("All");
        boolean bool2 = false;
        if ((paramHashMap != null) && (paramHashMap.get("isConsumer") != null)) {
          bool2 = Boolean.valueOf((String)paramHashMap.get("isConsumer")).booleanValue();
        }
        localIterator = localAccounts2.iterator();
        while (localIterator.hasNext())
        {
          localObject = (Account)localIterator.next();
          localAccount = (Account)((Account)localObject).clone();
          int i = 0;
          if (localAccount.getCoreAccount().equals("2")) {
            i = 1;
          } else if ((!bool2) && (localAccount.getCoreAccount().equals("0"))) {
            i = 1;
          }
          if ((paramHashMap != null) && (paramHashMap.get("useDirectoryId") != null)) {
            bool1 = Boolean.valueOf((String)paramHashMap.get("useDirectoryId")).booleanValue();
          }
          EntitlementGroupMember localEntitlementGroupMember;
          if (bool1)
          {
            localEntitlementGroupMember = new EntitlementGroupMember();
            localEntitlementGroupMember.setId(Integer.toString(paramInt));
            localEntitlementGroupMember.setMemberType("USER");
            localEntitlementGroupMember.setMemberSubType(Integer.toString(1));
            localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
          }
          else
          {
            localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
          }
          if (i != 0)
          {
            MultiEntitlement localMultiEntitlement = new MultiEntitlement();
            localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId((Account)localObject) });
            localMultiEntitlement.setOperations(new String[] { "External Transfers From" });
            if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramInt) == null) {
              localAccount.setFilterable("ExternalTransferFrom");
            }
            localMultiEntitlement.setOperations(new String[] { "External Transfers To" });
            if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramInt) == null) {
              localAccount.setFilterable("ExternalTransferTo");
            }
          }
          if ((localAccount.isFilterable("ExternalTransferFrom")) || (localAccount.isFilterable("ExternalTransferTo"))) {
            localAccounts1.add(localAccount);
          }
        }
      }
      ExtTransferAccounts localExtTransferAccounts = (ExtTransferAccounts)paramHashMap.get("ExternalTransferAccounts");
      if ((localExtTransferAccounts != null) && (localExtTransferAccounts.size() > 0))
      {
        localIterator = localExtTransferAccounts.iterator();
        while (localIterator.hasNext())
        {
          localObject = (ExtTransferAccount)localIterator.next();
          localAccount = localAccounts1.getByIDAndBankIDAndRoutingNum(((ExtTransferAccount)localObject).getNumber() + "-" + ((ExtTransferAccount)localObject).getTypeValue(), ((ExtTransferAccount)localObject).getBankId(), ((ExtTransferAccount)localObject).getRoutingNumber());
          localAccounts1.remove(localAccount);
        }
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccounts1;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ExtTransferCompanies getExtTransferCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ExternalTransferAdmin.GetTransferCompanies";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO))
    {
      long l = System.currentTimeMillis();
      ExtTransferCompanies localExtTransferCompanies = null;
      localExtTransferCompanies = com.ffusion.csil.handlers.ExternalTransferAdmin.getExtTransferCompanies(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localExtTransferCompanies;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ExtTransferCompany addExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.addTransferCompany";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO))
    {
      long l = System.currentTimeMillis();
      ExtTransferCompany localExtTransferCompany = null;
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5270;
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        localExtTransferCompany = com.ffusion.csil.handlers.ExternalTransferAdmin.addExtTransferCompany(paramSecureUser, paramExtTransferCompany, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_3", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramExtTransferCompany.getCustID(), str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = localExtTransferCompany.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_4", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, localExtTransferCompany.getCustID(), str2, i);
      PerfLog.log(str1, l, true);
      String str3 = localExtTransferCompany.getBpwID();
      try
      {
        Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str3.length();
        if (j > 9) {
          str3 = str3.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 24, str3, str2);
      paramExtTransferCompany.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
      return localExtTransferCompany;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void modifyExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany1, ExtTransferCompany paramExtTransferCompany2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.modifyTransferCompany";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5271;
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        com.ffusion.csil.handlers.ExternalTransferAdmin.modifyExtTransferCompany(paramSecureUser, paramExtTransferCompany1, paramExtTransferCompany2, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_5", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramExtTransferCompany1.getCustID(), str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferCompany1.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_6", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, paramExtTransferCompany1.getCustID(), str2, i);
      PerfLog.log(str1, l, true);
      String str3 = paramExtTransferCompany1.getBpwID();
      try
      {
        Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str3.length();
        if (j > 9) {
          str3 = str3.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 24, str3, str2);
      paramExtTransferCompany1.logChanges(localHistoryTracker, paramExtTransferCompany2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.deleteTransferCompany";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5272;
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        com.ffusion.csil.handlers.ExternalTransferAdmin.deleteExtTransferCompany(paramSecureUser, paramExtTransferCompany, paramString, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_7", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramExtTransferCompany.getCustID(), str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferCompany.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_8", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, paramExtTransferCompany.getCustID(), str2, i);
      PerfLog.log(str1, l, true);
      String str3 = paramExtTransferCompany.getBpwID();
      try
      {
        Integer.parseInt(str3);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str3.length();
        if (j > 9) {
          str3 = str3.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 24, str3, str2);
      paramExtTransferCompany.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void addExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.addTransferAccount";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atJ)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5273;
      String str3 = paramExtTransferCompany.getCustID();
      com.ffusion.beans.business.Business localBusiness = Business.jdMethod_int(paramSecureUser, Integer.parseInt(str3), paramHashMap);
      if (localBusiness == null) {
        str3 = null;
      }
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        com.ffusion.csil.handlers.ExternalTransferAdmin.addExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_9", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, str3, str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferAccount.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_10", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str3, str2, i);
      PerfLog.log(str1, l, true);
      String str4 = paramExtTransferAccount.getBpwID();
      try
      {
        Integer.parseInt(str4);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str4.length();
        if (j > 9) {
          str4 = str4.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 25, str4, str2);
      paramExtTransferAccount.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ExternalTransferAdmin.GetExtTransferAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO))
    {
      long l = System.currentTimeMillis();
      ExtTransferAccounts localExtTransferAccounts = null;
      localExtTransferAccounts = com.ffusion.csil.handlers.ExternalTransferAdmin.getExtTransferAccounts(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramString, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localExtTransferAccounts;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "ExternalTransferAdmin.GetExtTransferAccounts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atE))
    {
      long l = System.currentTimeMillis();
      ExtTransferAccounts localExtTransferAccounts = null;
      localExtTransferAccounts = com.ffusion.csil.handlers.ExternalTransferAdmin.getExtTransferAccounts(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localExtTransferAccounts;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.deleteTransferAccount";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atJ)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5275;
      String str3 = paramExtTransferCompany.getCustID();
      com.ffusion.beans.business.Business localBusiness = Business.jdMethod_int(paramSecureUser, Integer.parseInt(str3), paramHashMap);
      if (localBusiness == null) {
        str3 = null;
      }
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        com.ffusion.csil.handlers.ExternalTransferAdmin.deleteExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramExtTransferCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_13", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, str3, str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferAccount.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_14", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str3, str2, i);
      PerfLog.log(str1, l, true);
      String str4 = paramExtTransferAccount.getBpwID();
      try
      {
        Integer.parseInt(str4);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str4.length();
        if (j > 9) {
          str4 = str4.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 25, str4, str2);
      paramExtTransferAccount.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void modifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount1, ExtTransferAccount paramExtTransferAccount2, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.modifyTransferAccount";
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atO)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atJ)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5274;
      String str3 = paramExtTransferCompany.getCustID();
      com.ffusion.beans.business.Business localBusiness = Business.jdMethod_int(paramSecureUser, Integer.parseInt(str3), paramHashMap);
      if (localBusiness == null) {
        str3 = null;
      }
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        com.ffusion.csil.handlers.ExternalTransferAdmin.modifyExtTransferAccount(paramSecureUser, paramExtTransferAccount1, paramExtTransferCompany, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_11", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, str3, str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferAccount1.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_12", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str3, str2, i);
      PerfLog.log(str1, l, true);
      String str4 = paramExtTransferAccount1.getBpwID();
      try
      {
        Integer.parseInt(str4);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        int j = str4.length();
        if (j > 9) {
          str4 = str4.substring(j - 9);
        }
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 25, str4, str2);
      paramExtTransferAccount1.logChanges(localHistoryTracker, paramExtTransferAccount2, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static ExtTransferAccount verifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, int[] paramArrayOfInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.modifyTransferAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atJ))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5277;
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        paramExtTransferAccount = com.ffusion.csil.handlers.ExternalTransferAdmin.verifyExtTransferAccount(paramSecureUser, paramExtTransferAccount, paramArrayOfInt, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_15", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramSecureUser.getBusinessID(), str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferAccount.getBpwID();
      if (paramExtTransferAccount.getVerifyStatusValue() == 4) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_16", arrayOfObject);
      } else {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_17", arrayOfObject);
      }
      audit(paramSecureUser, localLocalizableString2, paramSecureUser.getBusinessID(), str2, i);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    return paramExtTransferAccount;
  }
  
  public static ExtTransferAccount depositAmountsForVerify(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "ExternalTransferAdmin.modifyTransferAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atJ))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = 5276;
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString2 = null;
      try
      {
        paramExtTransferAccount = com.ffusion.csil.handlers.ExternalTransferAdmin.depositAmountsForVerify(paramSecureUser, paramExtTransferAccount, paramAffiliateBank, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = "unknown";
        if ((localCSILException.why != null) && (localCSILException.why.length() > 0)) {
          arrayOfObject[0] = localCSILException.why;
        }
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_18", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramSecureUser.getBusinessID(), str2, i);
        throw localCSILException;
      }
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramExtTransferAccount.getBpwID();
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_19", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, paramSecureUser.getBusinessID(), str2, i);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.exttransfer", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    return paramExtTransferAccount;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.ExternalTransferAdmin
 * JD-Core Version:    0.7.0.1
 */