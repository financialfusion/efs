package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.handlers.AutoEntitleHandler;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.db.ConnectionHolder;
import com.ffusion.util.db.ReopenableDBCookie;
import com.ffusion.util.logging.PerfLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AutoEntitleAdmin
  extends Initialize
{
  private static Entitlement arT = new Entitlement("BankView", null, null);
  private static Entitlement ar3 = new Entitlement("BankModify", null, null);
  private static Entitlement ar9 = new Entitlement("BusinessProfileView", null, null);
  private static Entitlement arJ = new Entitlement("BusinessProfileEdit", null, null);
  private static Entitlement arI = new Entitlement("BusinessAdmin", null, null);
  private static final String asa = "ENT_TYPE_PROP_LIST_KEY";
  private static final String arK = "HISTORY_COLLECTION_KEY";
  private static final int arM = 1000;
  public static final int ENTITLE_ACCOUNT = 1;
  public static final int ENTITLE_ACHCOMPANY = 2;
  public static final int ENTITLE_WIRETEMPLATE = 3;
  public static final int ENTITLE_LOCATION = 4;
  public static final int ENTITLE_PERMISSIONS = 5;
  public static final int ENTITLE_ACCOUNT_GROUP = 6;
  private static final String ash = "com.ffusion.util.logging.audit.autoentitleadmin";
  private static final String ar4 = "AuditMessage_1";
  private static final String arU = "AuditMessage_2";
  private static final String arE = "AuditMessage_3";
  private static final String arS = "AuditMessage_4";
  private static final String ar0 = "AuditMessage_5";
  private static final String asc = "AuditMessage_6";
  private static final String ar7 = "AuditMessage_7";
  private static final String arB = "AuditMessage_8";
  private static final String arC = "AuditMessage_9";
  private static final String ar2 = "AuditMessage_10";
  private static final String arN = "AuditMessage_11";
  private static final String arG = "AuditMessage_12";
  private static final String arX = "AuditMessage_13";
  private static final String arV = "AuditMessage_14";
  private static final String arO = "AuditMessage_15";
  private static final String ar5 = "AuditMessage_16";
  private static final String arL = "AuditMessage_17";
  private static final String ar1 = "AuditMessage_18";
  private static final String arD = "AuditMessage_19";
  private static final String arZ = "AuditMessage_20";
  private static final String arQ = "AuditMessage_21";
  private static final String arP = "AuditMessage_22";
  private static final String asb = "AuditMessage_23";
  private static final String arY = "AuditMessage_24";
  private static final String arF = "AuditMessage_25";
  private static final String arR = "AuditMessage_26";
  private static final String arH = "AuditMessage_27";
  private static final String ar8 = "AuditMessage_28";
  private static final String ar6 = "AuditMessage_29";
  private static final String asg = "AuditMessage_30";
  private static final String asf = "AuditMessage_31";
  private static final String ase = "AuditMessage_32";
  private static final String asd = "AuditMessage_33";
  private static final String arW = "Ent_Group_Type.";
  public static final String EXTRA_AUTO_ENTITLE_SETTINGS_MAP = "AutoEntitleSettingsMap";
  public static final String EXTRA_ENTITLEMENT_ADAPTER = "EntitlementAdapter";
  public static final String EXTRA_MEMBER_CONN_HOLDER = "MemberConnHolder";
  public static final String EXTRA_OBJECT_TYPE_CONN_HOLDER = "ObjectTypeConnHolder";
  public static final String EXTRA_REOPENABLE_COOKIES = "ReopenableDBCookies";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.AutoEntitleAdmin.initialize");
    AutoEntitleHandler.initialize(paramHashMap);
  }
  
  public static AutoEntitle getSettings(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getSettings(paramSecureUser, paramAffiliateBank, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getSettings(paramSecureUser, paramEntitlementGroup, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getSettings(paramSecureUser, paramEntitlementGroupMember, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getSettings(SecureUser paramSecureUser, AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getSettings";
    long l = System.currentTimeMillis();
    paramAutoEntitle = AutoEntitleHandler.getSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getCumulativeSettings(paramSecureUser, paramAffiliateBank, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getCumulativeSettings(paramSecureUser, paramEntitlementGroup, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getCumulativeSettings(paramSecureUser, paramEntitlementGroupMember, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeSettings";
    long l = System.currentTimeMillis();
    paramAutoEntitle = AutoEntitleHandler.getCumulativeSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeParentSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getCumulativeParentSettings(paramSecureUser, paramEntitlementGroup, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeParentSettings";
    long l = System.currentTimeMillis();
    AutoEntitle localAutoEntitle = AutoEntitleHandler.getCumulativeParentSettings(paramSecureUser, paramEntitlementGroupMember, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAutoEntitle;
  }
  
  public static AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.getCumulativeParentSettings";
    long l = System.currentTimeMillis();
    if (paramAutoEntitle.getAffiliateBank() == null)
    {
      paramAutoEntitle = AutoEntitleHandler.getCumulativeParentSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return paramAutoEntitle;
    }
    throw new CSILException(str, 40102);
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.deleteSettings";
    long l = System.currentTimeMillis();
    if (((paramSecureUser.getUserType() == 2) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ar3))) || (paramSecureUser.getUserType() == 1))
    {
      AutoEntitleHandler.deleteSettings(paramSecureUser, paramAffiliateBank, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_1", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.deleteSettings";
    long l = System.currentTimeMillis();
    if (paramEntitlementGroup != null)
    {
      if ((paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arJ)))
      {
        localObject1 = new Object[0];
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_3", (Object[])localObject1);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 20001);
      }
      localObject1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      localObject2 = new EntitlementAdmin((EntitlementGroupMember)localObject1, paramEntitlementGroup.getGroupId());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject2))
      {
        AutoEntitleHandler.deleteSettings(paramSecureUser, paramEntitlementGroup, paramHashMap);
        PerfLog.log(str, l, true);
        debug(paramSecureUser, str);
        return;
      }
    }
    Object localObject1 = new Object[0];
    Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_2", (Object[])localObject1);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20007);
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AutoEntitleAdmin.deleteSettings";
    long l = System.currentTimeMillis();
    if ((paramSecureUser.getUserType() != 2) && (paramEntitlementGroupMember != null))
    {
      int i = paramEntitlementGroupMember.getEntitlementGroupId();
      localObject = Entitlements.getEntitlementGroup(i);
      String str2 = ((EntitlementGroup)localObject).getEntGroupType();
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(localEntitlementGroupMember, i);
      if (Entitlements.canAdminister(localEntitlementAdmin))
      {
        AutoEntitleHandler.deleteSettings(paramSecureUser, paramEntitlementGroupMember, paramHashMap);
        PerfLog.log(str1, l, true);
        debug(paramSecureUser, str1);
        return;
      }
    }
    Object[] arrayOfObject = new Object[0];
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_4", arrayOfObject);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20007);
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AutoEntitleAdmin.deleteSettings";
    long l = System.currentTimeMillis();
    AffiliateBank localAffiliateBank = paramAutoEntitle.getAffiliateBank();
    EntitlementGroupMember localEntitlementGroupMember = paramAutoEntitle.getEntitlementGroupMember();
    EntitlementGroup localEntitlementGroup1 = paramAutoEntitle.getEntitlementGroup();
    if ((localAffiliateBank == null) && (localEntitlementGroupMember == null) && (localEntitlementGroup1 == null)) {
      throw new CSILException(str1, 40101);
    }
    int i = 0;
    String str2 = null;
    String str3 = null;
    int j = -1;
    int k = -1;
    EntitlementGroup localEntitlementGroup2 = null;
    if (localEntitlementGroupMember != null)
    {
      k = localEntitlementGroupMember.getEntitlementGroupId();
      localEntitlementGroup2 = Entitlements.getEntitlementGroup(k);
      str3 = localEntitlementGroup2.getEntGroupType();
    }
    if (localEntitlementGroup1 != null)
    {
      j = localEntitlementGroup1.getGroupId();
      str2 = localEntitlementGroup1.getEntGroupType();
    }
    if (paramSecureUser.getUserType() == 2)
    {
      if ((localAffiliateBank != null) || ((localEntitlementGroup1 != null) && (str2.equals("BusinessAdmin")))) {
        i = 1;
      }
      if (i == 0)
      {
        localObject1 = new Object[0];
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_5", (Object[])localObject1);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
      if (localAffiliateBank != null) {
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ar3))
        {
          i = 1;
        }
        else
        {
          i = 0;
          localObject1 = new Object[0];
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_6", (Object[])localObject1);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      if (localEntitlementGroup1 != null) {
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arJ))
        {
          i = 1;
        }
        else
        {
          i = 0;
          localObject1 = new Object[0];
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_7", (Object[])localObject1);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
    }
    if (paramSecureUser.getUserType() == 1)
    {
      if ((localEntitlementGroupMember != null) || ((localEntitlementGroup1 != null) && ((str2.equals("BusinessAdmin")) || (str2.equals("Division")) || (str2.equals("Group"))))) {
        i = 1;
      }
      localObject1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      localObject2 = null;
      int m = 1;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      if (i == 0)
      {
        arrayOfObject = new Object[0];
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_8", arrayOfObject);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
      if (localEntitlementGroup1 != null)
      {
        localObject2 = new EntitlementAdmin((EntitlementGroupMember)localObject1, j);
        if (Entitlements.canAdminister((EntitlementAdmin)localObject2))
        {
          i = 1;
        }
        else
        {
          i = 0;
          arrayOfObject = new Object[1];
          arrayOfObject[0] = localEntitlementGroup1.getGroupName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_9", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      if (localEntitlementGroupMember != null)
      {
        localObject2 = new EntitlementAdmin((EntitlementGroupMember)localObject1, k);
        if (Entitlements.canAdminister((EntitlementAdmin)localObject2))
        {
          i = 1;
        }
        else
        {
          i = 0;
          arrayOfObject = new Object[1];
          arrayOfObject[0] = (localEntitlementGroup2 == null ? "" : localEntitlementGroup2.getGroupName());
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_10", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
    }
    if (i != 0)
    {
      AutoEntitleHandler.deleteSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object localObject1 = new Object[0];
    Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_11", (Object[])localObject1);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void setSettings(SecureUser paramSecureUser, AutoEntitle paramAutoEntitle1, AutoEntitle paramAutoEntitle2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AutoEntitleAdmin.setSettings";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    AffiliateBank localAffiliateBank = paramAutoEntitle1.getAffiliateBank();
    EntitlementGroupMember localEntitlementGroupMember = paramAutoEntitle1.getEntitlementGroupMember();
    EntitlementGroup localEntitlementGroup1 = paramAutoEntitle1.getEntitlementGroup();
    if ((localAffiliateBank == null) && (localEntitlementGroupMember == null) && (localEntitlementGroup1 == null)) {
      throw new CSILException(str1, 40101);
    }
    int i = 0;
    String str3 = null;
    String str4 = null;
    int j = -1;
    int k = -1;
    EntitlementGroup localEntitlementGroup2 = null;
    if (localEntitlementGroupMember != null)
    {
      k = localEntitlementGroupMember.getEntitlementGroupId();
      localEntitlementGroup2 = Entitlements.getEntitlementGroup(k);
      str4 = localEntitlementGroup2.getEntGroupType();
    }
    if (localEntitlementGroup1 != null)
    {
      j = localEntitlementGroup1.getGroupId();
      str3 = localEntitlementGroup1.getEntGroupType();
    }
    Object localObject1;
    Object localObject2;
    if (paramSecureUser.getUserType() == 2)
    {
      if ((localAffiliateBank != null) || ((localEntitlementGroup1 != null) && (str3.equals("BusinessAdmin")))) {
        i = 1;
      }
      if (i == 0)
      {
        localObject1 = new Object[0];
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_12", (Object[])localObject1);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
      if (localAffiliateBank != null) {
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ar3))
        {
          i = 1;
        }
        else
        {
          i = 0;
          localObject1 = new Object[0];
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_13", (Object[])localObject1);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      if (localEntitlementGroup1 != null) {
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), arJ))
        {
          i = 1;
        }
        else
        {
          i = 0;
          localObject1 = new Object[0];
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_14", (Object[])localObject1);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
    }
    Object localObject3;
    Object localObject5;
    if (paramSecureUser.getUserType() == 1)
    {
      if ((localEntitlementGroupMember != null) || ((localEntitlementGroup1 != null) && ((str3.equals("Business")) || (str3.equals("Division")) || (str3.equals("Group"))))) {
        i = 1;
      }
      localObject1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      localObject2 = null;
      int n = 1;
      if (i == 0)
      {
        localObject3 = new Object[0];
        localObject5 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_15", (Object[])localObject3);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject5, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
      if (localEntitlementGroup1 != null)
      {
        localObject2 = new EntitlementAdmin((EntitlementGroupMember)localObject1, j);
        if (Entitlements.canAdminister((EntitlementAdmin)localObject2))
        {
          i = 1;
        }
        else
        {
          i = 0;
          localObject3 = new Object[1];
          localObject3[0] = localEntitlementGroup1.getGroupName();
          localObject5 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_16", (Object[])localObject3);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject5, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
      if (localEntitlementGroupMember != null)
      {
        localObject2 = new EntitlementAdmin((EntitlementGroupMember)localObject1, k);
        if (Entitlements.canAdminister((EntitlementAdmin)localObject2))
        {
          i = 1;
        }
        else
        {
          i = 0;
          localObject3 = new Object[1];
          localObject3[0] = (localEntitlementGroup2 == null ? "" : localEntitlementGroup2.getGroupName());
          localObject5 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_17", (Object[])localObject3);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject5, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 20001);
        }
      }
    }
    if (i != 0)
    {
      AutoEntitleHandler.setSettings(paramSecureUser, paramAutoEntitle1, paramAutoEntitle2, paramHashMap);
      PerfLog.log(str1, l, true);
      if (localAffiliateBank != null) {
        if (localAffiliateBank.getAffiliateBankName() != null)
        {
          localObject1 = new Object[1];
          localObject1[0] = localAffiliateBank.getAffiliateBankName();
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_28", (Object[])localObject1);
          audit(paramSecureUser, (ILocalizable)localObject2, paramSecureUser.getBusinessID(), str2, 5602);
        }
        else
        {
          localObject1 = new Object[0];
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_29", (Object[])localObject1);
          audit(paramSecureUser, (ILocalizable)localObject2, paramSecureUser.getBusinessID(), str2, 5602);
        }
      }
      if (localEntitlementGroupMember != null)
      {
        localObject1 = new Object[1];
        localObject1[0] = "";
        localObject2 = null;
        try
        {
          StringList localStringList = new StringList();
          localStringList.setAdd(localEntitlementGroupMember.getId());
          localObject3 = UserAdminHandler.getBusinessEmployeesByIds(paramSecureUser, localStringList, paramHashMap);
          localObject5 = (BusinessEmployee)((BusinessEmployees)localObject3).get(0);
          localObject1[0] = ((BusinessEmployee)localObject5).getUserName();
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_32", (Object[])localObject1);
        }
        catch (Exception localException1)
        {
          localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_33", (Object[])localObject1);
        }
        audit(paramSecureUser, (ILocalizable)localObject2, paramSecureUser.getBusinessID(), str2, 5602);
      }
      int i1;
      Object localObject4;
      if (localEntitlementGroup1 != null)
      {
        localObject1 = new Object[2];
        localObject2 = new Object[0];
        localObject1[0] = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "Ent_Group_Type." + localEntitlementGroup1.getEntGroupType().toLowerCase(), (Object[])localObject2);
        localObject1[1] = "";
        i1 = Business.getProfileID(localEntitlementGroup1);
        if (localEntitlementGroup1.getGroupName() != null)
        {
          if (localEntitlementGroup1.getEntGroupType().equals("BusinessAdmin")) {
            try
            {
              localObject3 = new com.ffusion.beans.business.Business();
              ((com.ffusion.beans.business.Business)localObject3).setId(i1);
              localObject3 = com.ffusion.csil.handlers.Business.getBusiness(paramSecureUser, (com.ffusion.beans.business.Business)localObject3, paramHashMap);
              localObject1[1] = ((com.ffusion.beans.business.Business)localObject3).getBusinessName();
            }
            catch (Exception localException2)
            {
              localObject1[1] = localEntitlementGroup1.getGroupName();
            }
          } else {
            localObject1[1] = localEntitlementGroup1.getGroupName();
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_30", (Object[])localObject1);
          audit(paramSecureUser, (ILocalizable)localObject4, i1, str2, 5602);
        }
        else
        {
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_31", (Object[])localObject1);
          audit(paramSecureUser, (ILocalizable)localObject4, i1, str2, 5602);
        }
      }
      debug(paramSecureUser, str1);
      if ((localEntitlementGroup1 != null) || (localEntitlementGroupMember != null))
      {
        int m = -1;
        localObject2 = null;
        if (localEntitlementGroup1 != null)
        {
          if ((localEntitlementGroup1.getEntGroupType().equals("Business")) || (localEntitlementGroup1.getEntGroupType().equals("BusinessAdmin")))
          {
            m = 2;
            localObject2 = Integer.toString(Business.getProfileID(localEntitlementGroup1));
          }
          else if (localEntitlementGroup1.getEntGroupType().equals("Division"))
          {
            m = 6;
            localObject2 = Integer.toString(localEntitlementGroup1.getGroupId());
          }
          else if (localEntitlementGroup1.getEntGroupType().equals("Group"))
          {
            m = 7;
            localObject2 = Integer.toString(localEntitlementGroup1.getGroupId());
          }
        }
        else
        {
          m = 1;
          localObject2 = localEntitlementGroupMember.getId();
        }
        i1 = 0;
        if (paramSecureUser.getUserType() == 1) {
          i1 = 1;
        } else if (paramSecureUser.getUserType() == 2) {
          i1 = 0;
        }
        localObject4 = new HistoryTracker(paramSecureUser.getLocale(), i1, paramSecureUser.getProfileID(), paramSecureUser.getUserName(), m, (String)localObject2, str2);
        paramAutoEntitle1.logChanges((HistoryTracker)localObject4, paramAutoEntitle2, (ILocalizable)null);
        if ((paramHashMap != null) && (paramHashMap.get("HISTORY_COLLECTION_KEY") != null))
        {
          localObject5 = (Histories)paramHashMap.get("HISTORY_COLLECTION_KEY");
          ((Histories)localObject5).addAll(((HistoryTracker)localObject4).getHistories());
        }
        else
        {
          try
          {
            HistoryAdapter.addHistory(((HistoryTracker)localObject4).getHistories());
          }
          catch (ProfileException localProfileException)
          {
            Object[] arrayOfObject2 = new Object[0];
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_19", arrayOfObject2);
            logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
            throw new CSILException(str1, 40103);
          }
        }
      }
    }
    else
    {
      Object[] arrayOfObject1 = new Object[0];
      localObject2 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_20", arrayOfObject1);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void autoEntitleFeatures(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, int paramInt, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AutoEntitleAdmin.autoEntitleFeatures";
    String str2 = TrackingIDGenerator.GetNextID();
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    int i = 0;
    Histories localHistories = new Histories(paramSecureUser.getLocale());
    paramHashMap.put("HISTORY_COLLECTION_KEY", localHistories);
    try
    {
      paramHashMap.put("AutoEntitleSettingsMap", new HashMap());
      paramHashMap.put("EntitlementAdapter", Entitlements.U());
      paramHashMap.put("MemberConnHolder", new ConnectionHolder());
      paramHashMap.put("ObjectTypeConnHolder", new ConnectionHolder());
      paramHashMap.put("ReopenableDBCookies", new ArrayList());
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
      paramHashMap.put("ENT_TYPE_PROP_LIST_KEY", localEntitlementTypePropertyLists);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
      Iterator localIterator = paramEntitlements.iterator();
      EntitlementTypePropertyList localEntitlementTypePropertyList = null;
      Entitlement localEntitlement = null;
      Object localObject2;
      while (localIterator.hasNext())
      {
        localEntitlement = (Entitlement)localIterator.next();
        localObject1 = localEntitlement.getOperationName();
        if (localObject1 != null)
        {
          localEntitlementTypePropertyList = localEntitlementTypePropertyLists.getByOperationName((String)localObject1);
          if (localEntitlementTypePropertyList != null)
          {
            localObject2 = localEntitlementTypePropertyList.getPropertyValue("hide", 0);
            if ((localObject2 == null) || (!((String)localObject2).equals("yes"))) {
              localEntitlements.add(localEntitlement);
            }
          }
          else
          {
            localEntitlements.add(localEntitlement);
          }
        }
        else
        {
          localEntitlements.add(localEntitlement);
        }
      }
      Object localObject1 = jdMethod_for(localEntitlements);
      LocalizableString localLocalizableString;
      if (!paramBoolean)
      {
        if (paramEntitlementGroup.getEntGroupType().equals("ServicesPackage"))
        {
          Entitlements.T();
          i = 1;
          paramHashMap.put("HaveWriteLock", "yes");
        }
        jdMethod_for(paramSecureUser, paramEntitlementGroup, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, str2, 0, paramHashMap);
        localObject2 = new Object[0];
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_21", (Object[])localObject2);
        if (paramSecureUser.getUserType() == 1) {
          Initialize.audit(paramSecureUser, localLocalizableString, paramSecureUser.getBusinessID(), str2, 5601);
        } else if (paramSecureUser.getUserType() == 2) {
          Initialize.audit(paramSecureUser, localLocalizableString, str2, 5601);
        }
      }
      else
      {
        if ((paramEntitlementGroup.getEntGroupType() != null) && (paramEntitlementGroup.getEntGroupType().equals("ServicesPackage")))
        {
          paramHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(paramHashMap);
          paramHashMap.put("AFFILIATE_BANK_KEY", AffiliateBankAdmin.getAffiliateBankNames(paramSecureUser, paramHashMap));
        }
        if (paramEntitlementGroup.getEntGroupType().equals("ServicesPackage"))
        {
          Entitlements.T();
          i = 1;
          paramHashMap.put("HaveWriteLock", "yes");
        }
        jdMethod_for(paramSecureUser, paramEntitlementGroup, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, paramInt, str2, 0, paramHashMap);
        localObject2 = new Object[0];
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_22", (Object[])localObject2);
        if (paramSecureUser.getUserType() == 1) {
          Initialize.audit(paramSecureUser, localLocalizableString, paramSecureUser.getBusinessID(), str2, 5600);
        } else if (paramSecureUser.getUserType() == 2) {
          Initialize.audit(paramSecureUser, localLocalizableString, str2, 5600);
        }
      }
    }
    finally
    {
      if (i != 0)
      {
        Entitlements.S();
        paramHashMap.remove("HaveWriteLock");
      }
      paramHashMap.remove("ENT_TYPE_PROP_LIST_KEY");
      if (localHistories.size() > 0) {
        try
        {
          HistoryAdapter.addHistory(localHistories);
        }
        catch (ProfileException localProfileException)
        {
          localObject5 = new Object[0];
          localObject6 = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_23", (Object[])localObject5);
          logEntitlementFault(paramSecureUser, (ILocalizable)localObject6, TrackingIDGenerator.GetNextID());
          throw new CSILException(str1, 40103);
        }
      }
      paramHashMap.remove("HISTORY_COLLECTION_KEY");
      paramHashMap.remove("AutoEntitleSettingsMap");
      paramHashMap.remove("EntitlementAdapter");
      ConnectionHolder localConnectionHolder = (ConnectionHolder)paramHashMap.get("MemberConnHolder");
      localConnectionHolder.close();
      paramHashMap.remove("MemberConnHolder");
      localConnectionHolder = (ConnectionHolder)paramHashMap.get("ObjectTypeConnHolder");
      localConnectionHolder.close();
      paramHashMap.remove("ObjectTypeConnHolder");
      Object localObject5 = (ArrayList)paramHashMap.get("ReopenableDBCookies");
      Object localObject6 = ((ArrayList)localObject5).iterator();
      while (((Iterator)localObject6).hasNext()) {
        ((ReopenableDBCookie)((Iterator)localObject6).next()).uninitialize();
      }
    }
  }
  
  public static void restrictFeatures(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AutoEntitleAdmin.restrictFeatures";
    String str2 = TrackingIDGenerator.GetNextID();
    Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramEntitlementGroup.getGroupId(), paramEntitlements, !"yes".equals(paramHashMap.get("HaveWriteLock")));
    jdMethod_for(paramSecureUser, paramEntitlementGroup, paramEntitlements, str2, paramHashMap);
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_21", arrayOfObject);
    if (paramSecureUser.getUserType() == 1) {
      Initialize.audit(paramSecureUser, localLocalizableString, paramSecureUser.getBusinessID(), str2, 5601);
    } else {
      Initialize.audit(paramSecureUser, localLocalizableString, str2, 5601);
    }
  }
  
  public static void restrictFeatures(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "AutoEntitleAdmin.restrictFeatures";
    String str2 = TrackingIDGenerator.GetNextID();
    Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramEntitlementGroupMember, paramEntitlements, !"yes".equals(paramHashMap.get("HaveWriteLock")));
    jdMethod_for(paramSecureUser, paramEntitlementGroupMember, paramEntitlements, str2, paramHashMap);
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_21", arrayOfObject);
    if (paramSecureUser.getUserType() == 1) {
      Initialize.audit(paramSecureUser, localLocalizableString, paramSecureUser.getBusinessID(), str2, 5601);
    } else {
      Initialize.audit(paramSecureUser, localLocalizableString, str2, 5601);
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.restrictFeatures";
    ReopenableDBCookie localReopenableDBCookie = jdMethod_for(paramInt, paramHashMap);
    for (EntitlementGroup localEntitlementGroup = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId(), localReopenableDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId(), localReopenableDBCookie))
    {
      localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
      localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
      jdMethod_for(localEntitlementGroup, paramEntitlements, localEntitlements, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, paramHashMap);
      Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroup.getGroupId(), localEntitlements, !"yes".equals(paramHashMap.get("HaveWriteLock")));
      jdMethod_for(paramSecureUser, localEntitlementGroup, localEntitlements, paramString, paramHashMap);
      if (((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).size() > 0) {
        jdMethod_for(paramSecureUser, localEntitlementGroup, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, paramString, paramInt + 1, paramHashMap);
      }
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = jdMethod_for(paramEntitlements, paramHashMap);
    Object localObject1 = (EntitlementCachedAdapter)paramHashMap.get("EntitlementAdapter");
    EntitlementGroupMembers localEntitlementGroupMembers;
    try
    {
      localEntitlementGroupMembers = ((EntitlementCachedAdapter)localObject1).getMembers(paramEntitlementGroup.getGroupId(), (ConnectionHolder)paramHashMap.get("MemberConnHolder"));
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, Entitlements.i(localEntitlementException.code), localEntitlementException);
    }
    Iterator localIterator = localEntitlementGroupMembers.iterator();
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject2 = (EntitlementGroupMember)localIterator.next();
      Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), (EntitlementGroupMember)localObject2, localEntitlements, !"yes".equals(paramHashMap.get("HaveWriteLock")));
      jdMethod_for(paramSecureUser, (EntitlementGroupMember)localObject2, localEntitlements, paramString, paramHashMap);
    }
    if (paramHashMap != null)
    {
      localObject2 = (Histories)paramHashMap.get("HISTORY_COLLECTION_KEY");
      if (((Histories)localObject2).size() > 1000) {
        try
        {
          HistoryAdapter.addHistory((Histories)localObject2);
          ((Histories)localObject2).clear();
        }
        catch (ProfileException localProfileException)
        {
          Object[] arrayOfObject = new Object[0];
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_24", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str, 40103);
        }
      }
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, int paramInt1, String paramString, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.entitleFeatures";
    ReopenableDBCookie localReopenableDBCookie = jdMethod_for(paramInt2, paramHashMap);
    Object localObject3;
    for (EntitlementGroup localEntitlementGroup = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId(), localReopenableDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId(), localReopenableDBCookie))
    {
      localObject1 = getCumulativeSettings(paramSecureUser, localEntitlementGroup, paramHashMap);
      if (!AutoEntitleUtil.isPermissionEnabled((AutoEntitle)localObject1, paramInt1))
      {
        localObject2 = new com.ffusion.csil.beans.entitlements.Entitlements();
        localObject3 = new com.ffusion.csil.beans.entitlements.Entitlements();
        jdMethod_for(localEntitlementGroup, paramEntitlements, (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3, paramHashMap);
        Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroup.getGroupId(), (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, !"yes".equals(paramHashMap.get("HaveWriteLock")));
        jdMethod_for(paramSecureUser, localEntitlementGroup, (com.ffusion.csil.beans.entitlements.Entitlements)localObject2, paramString, paramHashMap);
        if (((com.ffusion.csil.beans.entitlements.Entitlements)localObject3).size() > 0) {
          jdMethod_for(paramSecureUser, localEntitlementGroup, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3, paramString, paramInt2 + 1, paramHashMap);
        }
      }
      else
      {
        jdMethod_for(paramSecureUser, localEntitlementGroup, paramEntitlements, paramInt1, paramString, paramInt2 + 1, paramHashMap);
      }
    }
    Object localObject1 = jdMethod_for(paramEntitlements, paramHashMap);
    Object localObject2 = (EntitlementCachedAdapter)paramHashMap.get("EntitlementAdapter");
    try
    {
      localObject3 = ((EntitlementCachedAdapter)localObject2).getMembers(paramEntitlementGroup.getGroupId(), (ConnectionHolder)paramHashMap.get("MemberConnHolder"));
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, Entitlements.i(localEntitlementException.code), localEntitlementException);
    }
    Iterator localIterator = ((EntitlementGroupMembers)localObject3).iterator();
    Object localObject4;
    while (localIterator.hasNext())
    {
      localObject4 = (EntitlementGroupMember)localIterator.next();
      AutoEntitle localAutoEntitle = getCumulativeSettings(paramSecureUser, (EntitlementGroupMember)localObject4, paramHashMap);
      if (!AutoEntitleUtil.isPermissionEnabled(localAutoEntitle, paramInt1))
      {
        Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), (EntitlementGroupMember)localObject4, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, !"yes".equals(paramHashMap.get("HaveWriteLock")));
        jdMethod_for(paramSecureUser, (EntitlementGroupMember)localObject4, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, paramString, paramHashMap);
      }
    }
    if (paramHashMap != null)
    {
      localObject4 = (Histories)paramHashMap.get("HISTORY_COLLECTION_KEY");
      if (((Histories)localObject4).size() > 1000) {
        try
        {
          HistoryAdapter.addHistory((Histories)localObject4);
          ((Histories)localObject4).clear();
        }
        catch (ProfileException localProfileException)
        {
          Object[] arrayOfObject = new Object[0];
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_25", arrayOfObject);
          logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
          throw new CSILException(str, 40103);
        }
      }
    }
  }
  
  private static ReopenableDBCookie jdMethod_for(int paramInt, HashMap paramHashMap)
  {
    ArrayList localArrayList = (ArrayList)paramHashMap.get("ReopenableDBCookies");
    ReopenableDBCookie localReopenableDBCookie;
    if (localArrayList.size() > paramInt)
    {
      localReopenableDBCookie = (ReopenableDBCookie)localArrayList.get(paramInt);
      localReopenableDBCookie.reset();
    }
    else
    {
      localReopenableDBCookie = new ReopenableDBCookie();
      localArrayList.add(localReopenableDBCookie);
    }
    return localReopenableDBCookie;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.trackGroupHistory";
    int i = -1;
    int j = paramEntitlementGroup.getGroupId();
    if ((paramEntitlementGroup.getEntGroupType().equals("Business")) || (paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")))
    {
      i = 2;
      j = Business.getProfileID(paramEntitlementGroup);
    }
    else if (paramEntitlementGroup.getEntGroupType().equals("Division"))
    {
      i = 6;
    }
    else if (paramEntitlementGroup.getEntGroupType().equals("Group"))
    {
      i = 7;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser.getLocale(), 2, -1, "Auto Entitle", i, Integer.toString(j), paramString);
    if ((paramHashMap != null) && (paramHashMap.get("HISTORY_COLLECTION_KEY") != null))
    {
      localHistoryTracker.logEntitlementAdd(paramEntitlements, (EntitlementTypePropertyLists)paramHashMap.get("ENT_TYPE_PROP_LIST_KEY"));
      Histories localHistories = (Histories)paramHashMap.get("HISTORY_COLLECTION_KEY");
      localHistories.addAll(localHistoryTracker.getHistories());
    }
    else
    {
      try
      {
        localHistoryTracker.logEntitlementAdd(paramEntitlements, Entitlements.getEntitlementTypesWithProperties(new HashMap()));
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        Object[] arrayOfObject = new Object[0];
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_26", arrayOfObject);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 40103);
      }
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitleAdmin.trackGroupMemberHistory";
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser.getLocale(), 2, -1, "Auto Entitle", 1, paramEntitlementGroupMember.getId(), paramString);
    if ((paramHashMap != null) && (paramHashMap.get("HISTORY_COLLECTION_KEY") != null))
    {
      localHistoryTracker.logEntitlementAdd(paramEntitlements, (EntitlementTypePropertyLists)paramHashMap.get("ENT_TYPE_PROP_LIST_KEY"));
      Histories localHistories = (Histories)paramHashMap.get("HISTORY_COLLECTION_KEY");
      localHistories.addAll(localHistoryTracker.getHistories());
    }
    else
    {
      try
      {
        localHistoryTracker.logEntitlementAdd(paramEntitlements, Entitlements.getEntitlementTypesWithProperties(new HashMap()));
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        Object[] arrayOfObject = new Object[0];
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.autoentitleadmin", "AuditMessage_27", arrayOfObject);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 40103);
      }
    }
  }
  
  private static com.ffusion.csil.beans.entitlements.Entitlements jdMethod_for(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    return AutoEntitleHandler.filterEntitlements(paramEntitlements);
  }
  
  private static void jdMethod_for(EntitlementGroup paramEntitlementGroup, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements1, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements2, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements3, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = null;
    if (paramEntitlementGroup.getEntGroupType().equals("Group")) {
      str1 = "per group";
    } else if (paramEntitlementGroup.getEntGroupType().equals("Division")) {
      str1 = "per division";
    } else if (paramEntitlementGroup.getEntGroupType().equals("Business")) {
      str1 = "per company";
    } else if (paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")) {
      str1 = "per business";
    }
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    if (paramHashMap != null) {
      localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHashMap.get("ENT_TYPE_PROP_LIST_KEY");
    }
    if (localEntitlementTypePropertyLists == null) {
      localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Entitlement localEntitlement = null;
    String str2 = null;
    for (int i = 0; i < paramEntitlements1.size(); i++)
    {
      localEntitlement = (Entitlement)paramEntitlements1.get(i);
      str2 = localEntitlement.getOperationName();
      if (str2 == null)
      {
        paramEntitlements2.add(localEntitlement);
      }
      else
      {
        localEntitlementTypePropertyList = localEntitlementTypePropertyLists.getByOperationName(str2);
        if ((localEntitlementTypePropertyList != null) && (localEntitlementTypePropertyList.isPropertySet("category", str1))) {
          paramEntitlements2.add(localEntitlement);
        } else {
          paramEntitlements3.add(localEntitlement);
        }
      }
    }
  }
  
  private static com.ffusion.csil.beans.entitlements.Entitlements jdMethod_for(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    if (paramHashMap != null) {
      localEntitlementTypePropertyLists = (EntitlementTypePropertyLists)paramHashMap.get("ENT_TYPE_PROP_LIST_KEY");
    }
    if (localEntitlementTypePropertyLists == null) {
      localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    EntitlementTypePropertyList localEntitlementTypePropertyList = null;
    Entitlement localEntitlement = null;
    String str = null;
    for (int i = 0; i < paramEntitlements.size(); i++)
    {
      localEntitlement = (Entitlement)paramEntitlements.get(i);
      str = localEntitlement.getOperationName();
      if (str == null)
      {
        localEntitlements.add(localEntitlement);
      }
      else
      {
        localEntitlementTypePropertyList = localEntitlementTypePropertyLists.getByOperationName(str);
        if ((localEntitlementTypePropertyList != null) && (localEntitlementTypePropertyList.isPropertySet("category", "per user"))) {
          localEntitlements.add(localEntitlement);
        }
      }
    }
    return localEntitlements;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AutoEntitleAdmin
 * JD-Core Version:    0.7.0.1
 */