package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class AccountGroup
  extends Initialize
{
  private static final String azV = "com.ffusion.util.logging.audit.accountgroup";
  private static final String azW = "AuditMessage_1";
  private static final String azS = "AuditMessage_2";
  private static final String azT = "AuditMessage_3";
  private static final String azN = "AuditEntFault_1";
  private static final String azR = "AuditEntFault_2";
  private static final String azU = "AuditEntFault_3";
  private static final String azM = "AuditEntFault_4";
  private static final String azP = "AuditEntFault_5";
  private static final String azO = "AuditEntFault_6";
  private static Entitlement azQ = new Entitlement("BC Account Groups Management", null, null);
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.AccountGroup.initialize");
    com.ffusion.csil.handlers.AccountGroup.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.AccountGroup.getService();
  }
  
  public static BusinessAccountGroups getAccountGroups(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.getAccountGroups";
    int i = 0;
    if ((paramSecureUser.getUserType() == 3) || (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azQ)) {
        i = 1;
      }
    }
    else if (paramSecureUser.getBusinessID() == paramInt) {
      i = 1;
    }
    if (i != 0)
    {
      long l = System.currentTimeMillis();
      BusinessAccountGroups localBusinessAccountGroups = com.ffusion.csil.handlers.AccountGroup.getAccountGroups(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinessAccountGroups;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditEntFault_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getAccountGroupIds(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.getAccountGroupIds";
    int i = 0;
    if ((paramSecureUser.getUserType() == 3) || (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azQ)) {
        i = 1;
      }
    }
    else if (paramSecureUser.getBusinessID() == paramInt) {
      i = 1;
    }
    if (i != 0)
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = com.ffusion.csil.handlers.AccountGroup.getAccountGroupIds(paramSecureUser, paramInt, paramArrayList, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditEntFault_2", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BusinessAccountGroup getAccountGroup(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AccountGroup.getAccountGroup";
    BusinessAccountGroup localBusinessAccountGroup = null;
    int i = 0;
    if ((paramSecureUser.getUserType() == 3) || (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azQ)) {
        i = 1;
      }
    }
    else {
      i = 1;
    }
    if (i != 0)
    {
      long l = System.currentTimeMillis();
      localBusinessAccountGroup = com.ffusion.csil.handlers.AccountGroup.getAccountGroup(paramSecureUser, paramInt, paramHashMap);
      if ((paramSecureUser.getUserType() != 3) && (paramSecureUser.getUserType() != 2) && (localBusinessAccountGroup.getBusDirId() != paramSecureUser.getBusinessID()))
      {
        i = 0;
      }
      else
      {
        PerfLog.log(str, l, true);
        debug(paramSecureUser, str);
      }
    }
    if (i == 0)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditEntFault_3", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(str, 20001);
    }
    return localBusinessAccountGroup;
  }
  
  public static BusinessAccountGroup addAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AccountGroup.addAccountGroup";
    int i = 0;
    String str2 = TrackingIDGenerator.GetNextID();
    if ((paramSecureUser.getUserType() == 3) || (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azQ)) {
        i = 1;
      }
    }
    else if (paramSecureUser.getBusinessID() == paramBusinessAccountGroup.getBusDirId()) {
      i = 1;
    }
    if (i != 0)
    {
      long l = System.currentTimeMillis();
      BusinessAccountGroup localBusinessAccountGroup = com.ffusion.csil.handlers.AccountGroup.addAccountGroup(paramSecureUser, paramBusinessAccountGroup, paramHashMap);
      com.ffusion.beans.business.Business localBusiness = Business.jdMethod_int(paramSecureUser, localBusinessAccountGroup.getBusDirId(), paramHashMap);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
      Entitlement localEntitlement = new Entitlement();
      localEntitlement.setObjectType("AccountGroup");
      localEntitlement.setObjectId(Integer.toString(localBusinessAccountGroup.getId()));
      localEntitlement.setOperationName("Access");
      localEntitlements.add(localEntitlement);
      AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, localBusiness.getEntitlementGroup(), localEntitlements, 6, paramBoolean, paramHashMap);
      if (!paramBoolean)
      {
        localObject1 = new BusinessAccountGroups();
        ((BusinessAccountGroups)localObject1).add(localBusinessAccountGroup);
        localObject2 = localBusiness.getEntitlementGroup();
        localObject3 = AutoEntitleUtil.buildPerAccountEntitlementsList((BusinessAccountGroups)localObject1, EntitlementsUtil.getTypePropertyCategoryValue(((EntitlementGroup)localObject2).getEntGroupType()));
        AutoEntitleAdmin.restrictFeatures(paramSecureUser, (EntitlementGroup)localObject2, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3, paramHashMap);
      }
      PerfLog.log(str1, l, true);
      Object localObject1 = new Object[2];
      localObject1[0] = paramBusinessAccountGroup.getName();
      localObject1[1] = paramBusinessAccountGroup.getAcctGroupId();
      Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditMessage_1", (Object[])localObject1);
      audit(paramSecureUser, (ILocalizable)localObject2, paramBusinessAccountGroup.getBusDirId(), TrackingIDGenerator.GetNextID(), 5500);
      debug(paramSecureUser, str1);
      Object localObject3 = new HistoryTracker(paramSecureUser, 2, String.valueOf(paramBusinessAccountGroup.getBusDirId()), str2);
      paramBusinessAccountGroup.logCreation((HistoryTracker)localObject3, ((HistoryTracker)localObject3).buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(((HistoryTracker)localObject3).getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
      return localBusinessAccountGroup;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditEntFault_4", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, str2);
    throw new CSILException(str1, 20001);
  }
  
  public static void modifyAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup1, BusinessAccountGroup paramBusinessAccountGroup2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AccountGroup.modifyAccountGroup";
    String str2 = TrackingIDGenerator.GetNextID();
    int i = 0;
    if ((paramSecureUser.getUserType() == 3) || (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azQ)) {
        i = 1;
      }
    }
    else if (paramSecureUser.getBusinessID() == paramBusinessAccountGroup2.getBusDirId()) {
      i = 1;
    }
    if (i != 0)
    {
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.AccountGroup.modifyAccountGroup(paramSecureUser, paramBusinessAccountGroup1, paramBusinessAccountGroup2, paramHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramBusinessAccountGroup1.getName();
      arrayOfObject[1] = paramBusinessAccountGroup1.getAcctGroupId();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditMessage_2", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, paramBusinessAccountGroup1.getBusDirId(), TrackingIDGenerator.GetNextID(), 5501);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      HistoryTracker localHistoryTracker1 = new HistoryTracker(paramSecureUser, 2, String.valueOf(paramBusinessAccountGroup2.getBusDirId()), str2);
      HistoryTracker localHistoryTracker2 = new HistoryTracker(paramSecureUser, 26, String.valueOf(paramBusinessAccountGroup2.getId()), str2);
      paramBusinessAccountGroup2.logModified(localHistoryTracker1, localHistoryTracker2, paramBusinessAccountGroup1, localHistoryTracker1.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker1.getHistories());
        HistoryAdapter.addHistory(localHistoryTracker2.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditEntFault_5", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteAccountGroup(SecureUser paramSecureUser, BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AccountGroup.deleteAccountGroup";
    String str2 = TrackingIDGenerator.GetNextID();
    int i = 0;
    if ((paramSecureUser.getUserType() == 3) || (paramSecureUser.getUserType() == 2))
    {
      if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azQ)) {
        i = 1;
      }
    }
    else if (paramSecureUser.getBusinessID() == paramBusinessAccountGroup.getBusDirId()) {
      i = 1;
    }
    if (i != 0)
    {
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.AccountGroup.deleteAccountGroup(paramSecureUser, paramBusinessAccountGroup, paramHashMap);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramBusinessAccountGroup.getName();
      arrayOfObject[1] = paramBusinessAccountGroup.getAcctGroupId();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditMessage_3", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, paramBusinessAccountGroup.getBusDirId(), TrackingIDGenerator.GetNextID(), 5502);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 2, String.valueOf(paramBusinessAccountGroup.getBusDirId()), str2);
      paramBusinessAccountGroup.logDeletion(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
      }
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.accountgroup", "AuditEntFault_6", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, str2);
      throw new CSILException(str1, 20001);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AccountGroup
 * JD-Core Version:    0.7.0.1
 */