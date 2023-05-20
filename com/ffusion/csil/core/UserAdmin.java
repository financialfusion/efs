package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTagValuesList;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.ffs.bpw.enums.UserType;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.logging.Level;

public class UserAdmin
  extends Initialize
{
  private static Entitlement azg = new Entitlement("UserAdmin", null, null);
  private static Entitlement ayQ = new Entitlement("Manage Users", null, null);
  private static Entitlement ayu = new Entitlement("BusinessEmployeeCrud", null, null);
  private static Entitlement ayT = new Entitlement("BusinessEmployeeView", null, null);
  private static Entitlement ayM = new Entitlement("Account Register", null, null);
  private static Entitlement ayf = new Entitlement("History", null, null);
  private static final String azk = "com.ffusion.util.logging.audit.useradmin";
  private static final String ayh = "AuditMessage_1";
  private static final String ayP = "AuditMessage_2";
  private static final String azy = "AuditMessage_2.1";
  private static final String azl = "AuditMessage_3";
  private static final String ayK = "AuditMessage_3.1";
  private static final String azw = "AuditMessage_4";
  private static final String azu = "AuditMessage_5";
  private static final String ayr = "AuditMessage_6";
  private static final String ayj = "AuditMessage_7";
  private static final String ayL = "AuditMessage_8";
  private static final String aza = "AuditMessage_9";
  private static final String ay6 = "AuditMessage_10";
  private static final String azs = "AuditMessage_11";
  private static final String azf = "AuditMessage_12";
  private static final String azB = "AuditMessage_13";
  private static final String ayA = "AuditMessage_14";
  private static final String azo = "AuditMessage_15";
  private static final String azm = "AuditMessage_16";
  private static final String ayZ = "AuditMessage_17";
  private static final String ayq = "AuditMessage_18";
  private static final String azj = "AuditMessage_19";
  private static final String ay8 = "AuditMessage_20";
  private static final String ayJ = "AuditMessage_21";
  private static final String ayB = "AuditMessage_22";
  private static final String ayv = "AuditMessage_23";
  private static final String ayo = "AuditMessage_24";
  private static final String ay7 = "AuditMessage_25";
  private static final String ays = "AuditMessage_26";
  private static final String ayW = "AuditMessage_27";
  private static final String ayw = "AuditMessage_28";
  private static final String azb = "AuditMessage_29";
  private static final String ay1 = "AuditMessage_30";
  private static final String azC = "AuditMessage_30.1";
  private static final String ayC = "AuditMessage_30.2";
  private static final String ay9 = "AuditMessage_30.3";
  private static final String azn = "AuditMessage_31";
  private static final String ayR = "AuditMessage_31.1";
  private static final String ayV = "AuditMessage_32";
  private static final String ayk = "AuditMessage_33";
  private static final String ayY = "AuditMessage_34";
  private static final String azz = "AuditMessage_35";
  private static final String aye = "AuditMessage_36";
  private static final String ayO = "AuditMessage_37";
  private static final String ayI = "AuditMessage_37.1";
  private static final String azd = "AuditMessage_38";
  private static final String ayp = "AuditMessage_39";
  private static final String ayE = "AuditMessage_40";
  private static final String ayH = "AuditEntFault_1";
  private static final String ayn = "AuditEntFault_2";
  private static final String aze = "AuditEntFault_3";
  private static final String azE = "AuditEntFault_4";
  private static final String ayz = "AuditEntFault_5";
  private static final String ayG = "AuditEntFault_6";
  private static final String ayl = "AuditEntFault_7";
  private static final String ayD = "AuditEntFault_8";
  private static final String azx = "AuditEntFault_9";
  private static final String ayN = "AuditEntFault_10";
  private static final String ayx = "AuditEntFault_11";
  private static final String azr = "AuditEntFault_12";
  private static final String azp = "AuditEntFault_13";
  private static final String aym = "AuditEntFault_14";
  private static final String azA = "AuditEntFault_15";
  private static final String ayS = "AuditEntFault_16";
  private static final String azv = "AuditEntFault_17";
  private static final String ayX = "AuditEntFault_18";
  private static final String ayt = "AuditEntFault_19";
  private static final String azi = "AuditEntFault_20";
  private static final String ay5 = "AuditEntFault_21";
  private static final String ay3 = "AuditEntFault_22";
  private static final String ay0 = "AuditEntFault_23";
  private static final String azq = "AuditEntFault_24";
  private static final String ayg = "AuditEntFault_25";
  private static final String ay2 = "AuditEntFault_26";
  private static final String ayF = "AuditEntFault_27";
  private static final String ayy = "AuditEntFault_28";
  private static final String azc = "AuditEntFault_29";
  private static final String ayU = "AuditEntFault_30";
  private static final String azt = "AuditEntFault_31";
  private static final String azD = "AuditEntFault_32";
  private static final String ayi = "AuditEntFault_33";
  private static final String ay4 = "com.ffusion.beans.user.resources";
  private static final String azh = "TTY";
  public static final int USER_TYPE_USER = 0;
  public static final int USER_TYPE_RETAIL = 1;
  public static final int USER_TYPE_CORPORATE = 2;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.UserAdmin.initialize");
    UserAdminHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return UserAdminHandler.getService();
  }
  
  public static User getUserInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getUserInfoFromBackend";
    long l = System.currentTimeMillis();
    User localUser = UserAdminHandler.getUserInfoFromBackend(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUser;
  }
  
  public static Users getUserList(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getUserList";
    long l = System.currentTimeMillis();
    Users localUsers = UserAdminHandler.getUserList(paramSecureUser, paramUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUsers;
  }
  
  public static Users getFilteredUsers(SecureUser paramSecureUser, User paramUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getFilteredUsers";
    long l = System.currentTimeMillis();
    Users localUsers = UserAdminHandler.getFilteredUsers(paramSecureUser, paramUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUsers;
  }
  
  public static Users getUsersByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetUsersByIds";
    long l = System.currentTimeMillis();
    Users localUsers = UserAdminHandler.getUsersByIds(paramSecureUser, paramArrayList, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUsers;
  }
  
  public static User modifyPasswordStatus(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getFilteredUsers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      User localUser = UserAdminHandler.modifyPasswordStatus(paramSecureUser, paramUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static SecureUser authenticate(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.Authenticate";
    if (paramSecureUser.getUserName() == null) {
      throw new CSILException(str1, 19001);
    }
    paramSecureUser.updateUserName(paramSecureUser.getUserName().toLowerCase());
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Object localObject1 = new String("");
    try
    {
      if (TTYAdmin.isEnabled(paramHashMap)) {
        localObject1 = new LocalizableString("com.ffusion.beans.user.resources", "TTY", null);
      }
      paramSecureUser = UserAdminHandler.authenticate(paramSecureUser, paramHashMap);
      if (TTYAdmin.isEnabled(paramHashMap)) {
        TTYAdmin.login(paramSecureUser, paramHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      int i = localCSILException.getCode();
      localObject2 = new Object[3];
      localObject2[0] = localObject1;
      switch (i)
      {
      case 100: 
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_1", (Object[])localObject2);
        audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        break;
      case 3005: 
        localObject2[1] = paramSecureUser.getUserName();
        if (SignonSettings.allowDuplicateUserNames())
        {
          localObject2[2] = paramSecureUser.getBusinessCustId();
          localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_2.1", (Object[])localObject2);
          audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        }
        else
        {
          localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_2", (Object[])localObject2);
          audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        }
        break;
      case 3008: 
        localObject2[1] = paramSecureUser.getUserName();
        if (SignonSettings.allowDuplicateUserNames())
        {
          localObject2[2] = paramSecureUser.getBusinessCustId();
          localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_3.1", (Object[])localObject2);
          audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        }
        else
        {
          localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_3", (Object[])localObject2);
          audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        }
        break;
      case 19002: 
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_4", (Object[])localObject2);
        audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        break;
      case 3021: 
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_5", (Object[])localObject2);
        audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
        break;
      default: 
        localObject3 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_6", (Object[])localObject2);
        audit(paramSecureUser, (ILocalizable)localObject3, str2, 3202);
      }
      paramSecureUser.invalidate();
      throw localCSILException;
    }
    PerfLog.log(str1, l, true);
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    paramSecureUser.setEntitlementID(localEntitlementGroupMember.getEntitlementGroupId());
    Integer localInteger = (Integer)paramSecureUser.get("AUTHENTICATE");
    Object localObject2 = (Boolean)paramSecureUser.get("ISAUTHENTICATED");
    Object localObject3 = (Boolean)paramSecureUser.get("OBO");
    boolean bool1 = localObject2 == null ? false : ((Boolean)localObject2).booleanValue();
    boolean bool2 = localObject3 == null ? false : ((Boolean)localObject3).booleanValue();
    int j;
    LocalizableString localLocalizableString2;
    if (localInteger != null)
    {
      LocalizableString localLocalizableString1;
      if ((localInteger.intValue() == 3007) && (paramSecureUser.getAgent() == null))
      {
        localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_7", null);
        audit(paramSecureUser, localLocalizableString1, str2, 3204);
      }
      else if (localInteger.intValue() == 3006)
      {
        localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_8", null);
        audit(paramSecureUser, localLocalizableString1, str2, 3206);
      }
      else if (localInteger.intValue() == 3017)
      {
        localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_9", null);
        audit(paramSecureUser, localLocalizableString1, str2, 3205);
      }
      else if ((localInteger.intValue() == 3018) && (!bool1) && (!bool2))
      {
        j = Password.validatePassword(paramSecureUser.getPassword(), 1, paramHashMap);
        if (j != 0)
        {
          paramSecureUser.put("AUTHENTICATE", new Integer(3024));
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_26", null);
          audit(paramSecureUser, localLocalizableString2, str2, 3204);
        }
        else
        {
          jdMethod_for(paramSecureUser, str2);
          paramSecureUser.put("ISAUTHENTICATED", Boolean.TRUE);
        }
      }
      else if ((paramSecureUser.getAgent() != null) && (!bool1))
      {
        jdMethod_for(paramSecureUser, str2);
        paramSecureUser.put("ISAUTHENTICATED", Boolean.TRUE);
      }
    }
    else if ((!bool1) && (!bool2))
    {
      j = Password.validatePassword(paramSecureUser.getPassword(), 1, paramHashMap);
      if (j != 0)
      {
        paramSecureUser.put("AUTHENTICATE", new Integer(3024));
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_26", null);
        audit(paramSecureUser, localLocalizableString2, str2, 3204);
      }
      else
      {
        jdMethod_for(paramSecureUser, str2);
        paramSecureUser.put("ISAUTHENTICATED", Boolean.TRUE);
      }
    }
    else if ((!bool1) && (bool2) && (paramSecureUser.getAgent() != null))
    {
      jdMethod_for(paramSecureUser, str2);
      paramSecureUser.put("ISAUTHENTICATED", Boolean.TRUE);
    }
    debug(paramSecureUser, str1);
    return paramSecureUser;
  }
  
  public static String identifyUser(SecureUser paramSecureUser)
  {
    if (paramSecureUser != null)
    {
      String str;
      if (paramSecureUser.getBusinessID() == 0) {
        str = "Retail user ";
      } else {
        str = "Corporate user ";
      }
      return str + paramSecureUser.getUserName();
    }
    return "User ";
  }
  
  public static int identifyUserType(SecureUser paramSecureUser)
  {
    if (paramSecureUser != null)
    {
      int i;
      if (paramSecureUser.getBusinessID() == 0) {
        i = 1;
      } else {
        i = 2;
      }
      return i;
    }
    return 0;
  }
  
  public static boolean userExists(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.userExists";
    long l = System.currentTimeMillis();
    boolean bool = UserAdminHandler.userExists(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return bool;
  }
  
  public static User addUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.AddUser";
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(localEntitlementGroupMember1, paramUser.getServicesPackageIdValue());
    if ((Entitlements.checkEntitlement(localEntitlementGroupMember1, azg)) && (Entitlements.canExtend(localEntitlementAdmin1)))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      User localUser = UserAdminHandler.addUser(paramSecureUser, paramUser, paramHashMap);
      EntitlementGroup localEntitlementGroup1 = new EntitlementGroup();
      localEntitlementGroup1.setGroupName("Admin_" + localUser.getUserName());
      localEntitlementGroup1.setEntGroupType("ConsumerAdmin");
      localEntitlementGroup1.setSvcBureauId(localUser.getBankId());
      localEntitlementGroup1.setParentId(localUser.getServicesPackageIdValue());
      localEntitlementGroup1.setGroupId(localUser.getEntitlementGroupId());
      localEntitlementGroup1 = Entitlements.addEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup1);
      int i = localEntitlementGroup1.getGroupId();
      EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(localEntitlementGroupMember1.getEntitlementGroupId(), i);
      EntitlementAdmin localEntitlementAdmin3 = new EntitlementAdmin(localEntitlementGroupMember1.getEntitlementGroupId(), i);
      localEntitlementAdmin3.setAdminister(true);
      localEntitlementAdmin3.setExtend(true);
      Entitlements.modifyAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin2, localEntitlementAdmin3);
      EntitlementAdmin localEntitlementAdmin4 = new EntitlementAdmin(0, i);
      EntitlementGroup localEntitlementGroup2 = null;
      EntitlementGroups localEntitlementGroups = Entitlements.getEntitlementGroupsByType("EmployeeType");
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup2 = (EntitlementGroup)localIterator.next();
        localEntitlementAdmin4.setGranteeGroupId(localEntitlementGroup2.getGroupId());
        if ((localEntitlementGroupMember1.getEntitlementGroupId() != localEntitlementGroup2.getGroupId()) && (Entitlements.checkEntitlement(localEntitlementGroup2.getGroupId(), azg)))
        {
          localEntitlementAdmin4.setAdminister(true);
          localEntitlementAdmin4.setExtend(false);
          Entitlements.addAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin4);
        }
      }
      EntitlementGroup localEntitlementGroup3 = new EntitlementGroup();
      localEntitlementGroup3.setGroupName(localUser.getUserName());
      localEntitlementGroup3.setEntGroupType("USER");
      localEntitlementGroup3.setSvcBureauId(localUser.getBankId());
      localEntitlementGroup3.setParentId(i);
      localEntitlementGroup3 = Entitlements.addEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup3);
      int j = localEntitlementGroup3.getGroupId();
      EntitlementAdmin localEntitlementAdmin5 = new EntitlementAdmin(j, j);
      localEntitlementAdmin5.setAdminister(true);
      localEntitlementAdmin5.setExtend(true);
      Entitlements.addAdministratorGroup(localEntitlementGroupMember1, localEntitlementAdmin5);
      EntitlementGroupMember localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setId(Integer.toString(localUser.getIdValue()));
      localEntitlementGroupMember2.setMemberType("USER");
      localEntitlementGroupMember2.setMemberSubType(Integer.toString(1));
      localEntitlementGroupMember2.setEntitlementGroupId(j);
      Entitlements.addMember(localEntitlementGroupMember1, localEntitlementGroupMember2);
      localUser.setEntitlementGroupId(i);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramUser.getUserName();
      arrayOfObject[1] = paramUser.getId();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_12", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3214);
      debug(paramSecureUser, str1);
      return localUser;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_2", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void addSecondaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.AddSecondaryUser";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, ayQ))
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_3", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    UserAdminHandler.addSecondaryUser(paramSecureUser, paramUser, paramHashMap);
    PerfLog.log(str1, l, true);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramUser.getUserName();
    arrayOfObject[1] = paramUser.getId();
    LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_12", arrayOfObject);
    audit(paramSecureUser, localLocalizableString2, str2, 3214);
    debug(paramSecureUser, str1);
  }
  
  public static User getUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getUser";
    long l = System.currentTimeMillis();
    paramUser = UserAdminHandler.getUser(paramSecureUser, paramUser, paramHashMap);
    jdMethod_for(paramSecureUser, paramUser);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramUser;
  }
  
  public static Users getSecondaryUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getSecondaryUsers";
    long l = System.currentTimeMillis();
    Users localUsers = UserAdminHandler.getSecondaryUsers(paramSecureUser, paramUser, paramHashMap);
    Iterator localIterator = localUsers.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      jdMethod_for(paramSecureUser, localUser);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUsers;
  }
  
  public static User getPrimaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getPrimaryUser";
    long l = System.currentTimeMillis();
    User localUser = UserAdminHandler.getPrimaryUser(paramSecureUser, paramUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUser;
  }
  
  public static User getUserById(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.getUserById";
    long l = System.currentTimeMillis();
    paramUser = UserAdminHandler.getUserById(paramSecureUser, paramUser, paramHashMap);
    if ((paramSecureUser.getUserType() == 2) && (paramSecureUser.getAffiliateIDValue() != 0) && (paramSecureUser.getAffiliateIDValue() != paramUser.getAffiliateBankID()))
    {
      DebugLog.log(Level.SEVERE, "UserAdmin.getUserById.  Attempt to access a user that the secure user is not entitled to access.");
      throw new CSILException(str, 104);
    }
    if ((!paramUser.getAccountStatus().equals("8")) || (paramUser.getCustomerType().equals(User.CUSTOMER_TYPE_CONSUMER))) {
      jdMethod_for(paramSecureUser, paramUser);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return paramUser;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, User paramUser)
    throws CSILException
  {
    int i = 0;
    Object localObject;
    if (paramUser.get("BUSINESS_ID") != null)
    {
      localObject = new BusinessEmployee();
      ((BusinessEmployee)localObject).set(paramUser);
      ((BusinessEmployee)localObject).setBusinessId((String)paramUser.get("BUSINESS_ID"));
      jdMethod_try((User)localObject);
      paramUser.setEntitlementGroupId(((BusinessEmployee)localObject).getEntitlementGroupId());
    }
    else
    {
      if (paramSecureUser.getUserType() == 1)
      {
        if (paramSecureUser.getEntitlementID() != 0)
        {
          i = paramSecureUser.getEntitlementID();
        }
        else
        {
          localObject = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
          localObject = Entitlements.getMember((EntitlementGroupMember)localObject);
          i = ((EntitlementGroupMember)localObject).getEntitlementGroupId();
          paramSecureUser.setEntitlementID(i);
        }
      }
      else
      {
        localObject = new SecureUser();
        ((SecureUser)localObject).setProfileID(paramUser.getIdValue());
        ((SecureUser)localObject).setUserType(1);
        EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject);
        localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
        i = localEntitlementGroupMember.getEntitlementGroupId();
      }
      paramUser.setEntitlementGroupId(i);
      while (i != 0)
      {
        localObject = Entitlements.getEntitlementGroup(i);
        if (localObject == null) {
          break;
        }
        if (((EntitlementGroup)localObject).getEntGroupType().equals("ServicesPackage"))
        {
          paramUser.setServicesPackageId(i);
          break;
        }
        i = ((EntitlementGroup)localObject).getParentId();
      }
    }
  }
  
  public static Users getUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetUsers";
    long l = System.currentTimeMillis();
    Users localUsers = UserAdminHandler.getUsers(paramSecureUser, paramUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUsers;
  }
  
  public static String getUsersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetUsersCount";
    long l = System.currentTimeMillis();
    String str2 = UserAdminHandler.getUsersCount(paramSecureUser, paramUser, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return str2;
  }
  
  public static String getUserDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "UserAdmin.getUserDisplayCount";
    long l = System.currentTimeMillis();
    String str2 = UserAdminHandler.getUserDisplayCount();
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return str2;
  }
  
  public static String getConsumerDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "UserAdmin.getConsumerDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      String str2 = UserAdminHandler.getConsumerDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_4", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getUserMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "UserAdmin.getUserMaxResultCount";
    long l = System.currentTimeMillis();
    String str2 = UserAdminHandler.getUserMaxResultCount();
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return str2;
  }
  
  public static String getConsumerMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "UserAdmin.getConsumerMaxResultCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      String str2 = UserAdminHandler.getConsumerMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_5", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static User modifyUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ModifyUser";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      User localUser = new User();
      localUser.set(paramUser);
      localUser = UserAdminHandler.getUserById(paramSecureUser, localUser, paramHashMap);
      jdMethod_for(paramSecureUser, localUser);
      return modifyUser(paramSecureUser, paramUser, localUser, paramHashMap);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_6", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static User modifyUser(SecureUser paramSecureUser, User paramUser1, User paramUser2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ModifyUser";
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    int i = 1;
    Object localObject1;
    if ((paramUser2 != null) && (paramUser2.getServicesPackageIdValue() != paramUser1.getServicesPackageIdValue()))
    {
      localObject1 = new EntitlementAdmin(localEntitlementGroupMember1, paramUser1.getServicesPackageIdValue());
      if ((!Entitlements.checkEntitlement(localEntitlementGroupMember1, azg)) || (!Entitlements.canExtend((EntitlementAdmin)localObject1))) {
        i = 0;
      }
    }
    if ((i != 0) && (Entitlements.checkEntitlement(localEntitlementGroupMember1, azg)))
    {
      if (paramSecureUser.getProfileID() == paramUser1.getIdValue())
      {
        localObject1 = (Integer)paramSecureUser.get("AUTHENTICATE");
        if ((localObject1 == null) || (((Integer)localObject1).intValue() != 3023))
        {
          int j = Password.validatePassword(paramUser1.getPassword(), 1, paramHashMap);
          if (j != 0) {
            throw new CSILException(str1, 19002);
          }
        }
      }
      if (!jdMethod_int(paramSecureUser, paramUser1))
      {
        DebugLog.log(Level.SEVERE, "UserAdmin.ModifyUser.  Attempt to modify a user that the secure user is not entitled to modify.");
        throw new CSILException(str1, 104);
      }
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      if ((paramUser2 != null) && (paramUser2.getAffiliateBankID() != paramUser1.getAffiliateBankID()))
      {
        paramUser1.setTermsAccepted(0);
        paramUser1.setTermsAcceptedDate((DateTime)null);
      }
      User localUser = UserAdminHandler.modifyUser(paramSecureUser, paramUser1, paramHashMap);
      String str3 = (String)paramUser1.get("BUSINESS_ID");
      try
      {
        String str4 = paramUser1.getUserName();
        if ((str3 == null) && ((!paramUser2.getUserName().equals(str4)) || (paramUser2.getServicesPackageIdValue() != paramUser1.getServicesPackageIdValue())))
        {
          localObject2 = null;
          EntitlementGroup localEntitlementGroup = null;
          SecureUser localSecureUser = new SecureUser();
          localSecureUser.setProfileID(paramUser1.getIdValue());
          localSecureUser.setUserType(1);
          EntitlementGroupMember localEntitlementGroupMember2 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          localEntitlementGroupMember2 = Entitlements.getMember(localEntitlementGroupMember2);
          localObject2 = Entitlements.getEntitlementGroup(localEntitlementGroupMember2.getEntitlementGroupId());
          if ((localObject2 != null) && (((EntitlementGroup)localObject2).getEntGroupType().equals("USER")))
          {
            localEntitlementGroup = Entitlements.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            if (!paramUser2.getUserName().equals(str4))
            {
              ((EntitlementGroup)localObject2).setGroupName(str4);
              Entitlements.jdMethod_for(localEntitlementGroupMember1, (EntitlementGroup)localObject2);
              localEntitlementGroup.setGroupName("Admin_" + str4);
              Entitlements.jdMethod_for(localEntitlementGroupMember1, localEntitlementGroup);
            }
            if (paramUser2.getServicesPackageIdValue() != paramUser1.getServicesPackageIdValue())
            {
              localEntitlementGroup.setParentId(paramUser1.getServicesPackageIdValue());
              Entitlements.modifyEntitlementGroup(localEntitlementGroupMember1, localEntitlementGroup);
            }
          }
        }
      }
      catch (Exception localException) {}
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramUser1.getUserName();
      Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_13", arrayOfObject);
      if (str3 == null) {
        audit(paramSecureUser, (ILocalizable)localObject2, str2, 3215);
      } else {
        audit(paramSecureUser, (ILocalizable)localObject2, str3, str2, 3215);
      }
      debug(paramSecureUser, str1);
      return localUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static User modifyUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ModifyUserPassword";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      int i = Password.validatePassword(paramString1, 1, paramHashMap);
      if (i != 0) {
        throw new CSILException(str1, 19002);
      }
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      User localUser = UserAdminHandler.modifyUserPassword(paramSecureUser, paramUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramUser.getUserName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_14", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3220);
      debug(paramSecureUser, str1);
      return localUser;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_8", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.AddAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Account localAccount = UserAdminHandler.addAccount(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAccount.buildLocalizableAccountID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_15", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3208);
      debug(paramSecureUser, str1);
      return localAccount;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_9", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetAccounts";
    long l = System.currentTimeMillis();
    Accounts localAccounts = UserAdminHandler.getAccounts(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ModifyAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Account localAccount = UserAdminHandler.modifyAccount(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAccount.buildLocalizableAccountID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_16", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3209);
      debug(paramSecureUser, str1);
      return localAccount;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_10", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.DeleteAccount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Account localAccount = UserAdminHandler.deleteAccount(paramSecureUser, paramAccount, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAccount.buildLocalizableAccountID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_17", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3210);
      debug(paramSecureUser, str1);
      return localAccount;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_11", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void addAdditionalData(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.AddAdditionalData";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      UserAdminHandler.addAdditionalData(paramSecureUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_12", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetAdditionalData";
    long l = System.currentTimeMillis();
    String str2 = UserAdminHandler.getAdditionalData(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return str2;
  }
  
  public static Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.AddHistory";
    long l = System.currentTimeMillis();
    Histories localHistories = UserAdminHandler.addHistory(paramSecureUser, paramHistories, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localHistories;
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetHistory";
    long l = System.currentTimeMillis();
    Histories localHistories = UserAdminHandler.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localHistories;
  }
  
  public static Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetHistory";
    long l = System.currentTimeMillis();
    Histories localHistories = UserAdminHandler.getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramInt1, paramInt2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localHistories;
  }
  
  public static CustomTags addCustomTags(SecureUser paramSecureUser, CustomTags paramCustomTags, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.AddCustomTags";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      CustomTags localCustomTags = UserAdminHandler.addCustomTags(paramSecureUser, paramCustomTags, paramUser, paramHashMap);
      PerfLog.log(str1, l, true);
      ListIterator localListIterator = paramCustomTags.listIterator();
      while (localListIterator.hasNext())
      {
        String str2 = TrackingIDGenerator.GetNextID();
        CustomTag localCustomTag = (CustomTag)localListIterator.next();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localCustomTag.getTagName();
        arrayOfObject[1] = paramUser.getUserName();
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_18", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, str2, 3217);
      }
      debug(paramSecureUser, str1);
      return localCustomTags;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_13", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static CustomTags getCustomTags(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetCustomTags";
    long l = System.currentTimeMillis();
    CustomTags localCustomTags = UserAdminHandler.getCustomTags(paramSecureUser, paramUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localCustomTags;
  }
  
  public static CustomTag modifyCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ModifyCustomTags";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      CustomTag localCustomTag = UserAdminHandler.modifyCustomTag(paramSecureUser, paramCustomTag, paramUser, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramCustomTag.getTagName();
      arrayOfObject[1] = paramUser.getUserName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_19", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3218);
      debug(paramSecureUser, str1);
      return localCustomTag;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_14", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.DeleteCustomTags";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      CustomTag localCustomTag = UserAdminHandler.deleteCustomTag(paramSecureUser, paramCustomTag, paramUser, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramCustomTag.getTagName();
      arrayOfObject[1] = paramUser.getUserName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_20", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 3219);
      debug(paramSecureUser, str1);
      return;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_15", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ArrayList getExtraFields(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.getExtraFields";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    ArrayList localArrayList = UserAdminHandler.getExtraFields(paramSecureUser, paramHashMap);
    PerfLog.log(str1, l, true);
    return localArrayList;
  }
  
  public static CustomTagValuesList getCustomTagValuesList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.getCustomTagValuesList";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    CustomTagValuesList localCustomTagValuesList = UserAdminHandler.getCustomTagValuesList(paramSecureUser, paramHashMap);
    PerfLog.log(str1, l, true);
    return localCustomTagValuesList;
  }
  
  public static User deleteUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.DeleteUser";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      User localUser = UserAdminHandler.deleteUser(paramSecureUser, paramUser, paramHashMap);
      if (paramUser.getEntitlementGroupId() != 0)
      {
        localObject1 = Entitlements.getEntitlementGroup(paramUser.getEntitlementGroupId());
        if ((localObject1 != null) && (((EntitlementGroup)localObject1).getEntGroupType().equals("ConsumerAdmin")))
        {
          localObject2 = new SecureUser();
          ((SecureUser)localObject2).setProfileID(paramUser.getIdValue());
          ((SecureUser)localObject2).setUserType(1);
          EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject2);
          localEntitlementGroupMember1 = Entitlements.getMember(localEntitlementGroupMember1);
          EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroupMember1.getEntitlementGroupId());
          if ((localEntitlementGroup != null) && (localEntitlementGroup.getEntGroupType().equals("USER")))
          {
            EntitlementGroupMember localEntitlementGroupMember2 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
            Entitlements.removeMember(localEntitlementGroupMember2, localEntitlementGroupMember1);
            Entitlements.deleteEntitlementGroup(localEntitlementGroupMember2, (EntitlementGroup)localObject1);
          }
        }
      }
      PerfLog.log(str1, l, true);
      Object localObject1 = new Object[1];
      localObject1[0] = paramUser.getUserName();
      Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_21", (Object[])localObject1);
      audit(paramSecureUser, (ILocalizable)localObject2, str2, 3216);
      debug(paramSecureUser, str1);
      return localUser;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_16", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BusinessEmployee addBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.AddBusinessEmployee";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, ayu))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      int i = paramBusinessEmployee.getEntitlementGroupId();
      if (i <= 0)
      {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_17", null);
        logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20025);
      }
      Object localObject = new EntitlementAdmin(localEntitlementGroupMember, i);
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        BusinessEmployee localBusinessEmployee = UserAdminHandler.addBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramBusinessEmployee.getUserName();
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_22", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, paramBusinessEmployee.getBusinessId(), str2, 3211);
        debug(paramSecureUser, str1);
        return localBusinessEmployee;
      }
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_18", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BusinessEmployee modifyBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ModifyBusinessEmployee";
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember1, ayu))
    {
      if (paramSecureUser.getProfileID() == paramBusinessEmployee.getIdValue())
      {
        localObject1 = (Integer)paramSecureUser.get("AUTHENTICATE");
        if ((localObject1 == null) || (((Integer)localObject1).intValue() != 3023))
        {
          int i = Password.validatePassword(paramBusinessEmployee.getPassword(), 1, paramHashMap);
          if (i != 0) {
            throw new CSILException(str1, 19002);
          }
        }
      }
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      localObject1 = paramBusinessEmployee.getEntitlementGroupMember();
      EntitlementGroupMember localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.set((EntitlementGroupMember)localObject1);
      Entitlements.getMember(localEntitlementGroupMember2);
      boolean bool = ((EntitlementGroupMember)localObject1).getEntitlementGroupId() != localEntitlementGroupMember2.getEntitlementGroupId();
      EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(localEntitlementGroupMember1, ((EntitlementGroupMember)localObject1).getEntitlementGroupId());
      EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(localEntitlementGroupMember1, localEntitlementGroupMember2.getEntitlementGroupId());
      if ((!bool) || ((Entitlements.canAdminister(localEntitlementAdmin1)) && (Entitlements.canAdminister(localEntitlementAdmin2))))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        if (bool)
        {
          paramHashMap.put("ENT_GROUP_CHANGED_KEY", new Boolean(bool));
          localObject2 = Entitlements.getEntitlementGroup(localEntitlementGroupMember2.getEntitlementGroupId());
          if (("Business".equals(((EntitlementGroup)localObject2).getEntGroupType())) && (Entitlements.getNumMembers(localEntitlementGroupMember2.getEntitlementGroupId()) <= 1)) {
            throw new CSILException(str1, 3015);
          }
          Entitlements.modifyMember(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramBusinessEmployee.getEntitlementGroupMember());
        }
        Object localObject2 = UserAdminHandler.modifyBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramBusinessEmployee.getUserName();
        LocalizableString localLocalizableString = paramBusinessEmployee.getAccountStatus().equals(Integer.toString(8)) ? new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_24", arrayOfObject) : new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_23", arrayOfObject);
        if (paramBusinessEmployee.getAccountStatus().equals(Integer.toString(8))) {
          audit(paramSecureUser, localLocalizableString, paramBusinessEmployee.getBusinessId(), str2, 3213);
        } else {
          audit(paramSecureUser, localLocalizableString, paramBusinessEmployee.getBusinessId(), str2, 3212);
        }
        debug(paramSecureUser, str1);
        return localObject2;
      }
    }
    Object localObject1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_19", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BusinessEmployees getBusinessEmployees(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetBusinessEmployees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      BusinessEmployees localBusinessEmployees = UserAdminHandler.getBusinessEmployees(paramSecureUser, paramBusinessEmployee, paramHashMap);
      jdMethod_for(localBusinessEmployees);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinessEmployees;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_20", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetBusinessEmployees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = UserAdminHandler.getBusinessEmployeesIDs(paramSecureUser, paramBusinessEmployee, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_21", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetBusinessEmployees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = UserAdminHandler.getBusinessEmployeesIDs(paramSecureUser, paramArrayList, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_22", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Users getConsumers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetConsumers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      Users localUsers = UserAdminHandler.getConsumers(paramSecureUser, paramUser, paramHashMap);
      jdMethod_for(localUsers);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localUsers;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_23", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getConsumersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetConsumersCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      String str2 = UserAdminHandler.getConsumersCount(paramSecureUser, paramUser, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_24", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static BusinessEmployee deleteBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.DeleteBusinessEmployee";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, ayu))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      localObject = new EntitlementAdmin(localEntitlementGroupMember, paramBusinessEmployee.getEntitlementGroupId());
      if (Entitlements.canAdminister((EntitlementAdmin)localObject))
      {
        long l = System.currentTimeMillis();
        String str2 = TrackingIDGenerator.GetNextID();
        BusinessEmployee localBusinessEmployee = UserAdminHandler.deleteBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
        Entitlements.removeMember(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localBusinessEmployee.getEntitlementGroupMember());
        PerfLog.log(str1, l, true);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramBusinessEmployee.getUserName();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_24", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, paramBusinessEmployee.getBusinessId(), str2, 3213);
        debug(paramSecureUser, str1);
        return localBusinessEmployee;
      }
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_25", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static boolean businessIdExists(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.businessExists";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      boolean bool = UserAdminHandler.businessIdExists(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return bool;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_26", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static BusinessEmployees getFilteredBusinessEmployees(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetFilteredBusinessEmployees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      BusinessEmployees localBusinessEmployees = UserAdminHandler.getFilteredBusinessEmployees(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
      jdMethod_for(localBusinessEmployees);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinessEmployees;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_27", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getFilteredBusinessEmployeesCount(SecureUser paramSecureUser, com.ffusion.beans.business.Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.getFilteredBusinessEmployeesCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      String str2 = UserAdminHandler.getFilteredBusinessEmployeesCount(paramSecureUser, paramBusiness, paramBusinessEmployee, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_28", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  private static int jdMethod_for(EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    if ((paramEntitlementGroup != null) && ((paramEntitlementGroup.getEntGroupType().equals("Group")) || (paramEntitlementGroup.getEntGroupType().equals("Division")) || (paramEntitlementGroup.getEntGroupType().equals("Business"))))
    {
      for (EntitlementGroup localEntitlementGroup = paramEntitlementGroup; !localEntitlementGroup.getEntGroupType().equals("Business"); localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId())) {}
      return localEntitlementGroup.getGroupId();
    }
    return -1;
  }
  
  private static int j(int paramInt)
    throws CSILException
  {
    return jdMethod_for(Entitlements.getEntitlementGroup(paramInt));
  }
  
  public static BusinessEmployees getBusinessEmployeesByEntGroupId(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetBusinessEmployeesByEntGroupId";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      if (paramSecureUser.getUserType() == 1)
      {
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt);
        if (((localEntitlementGroup.getEntGroupType().equals("Group")) || (localEntitlementGroup.getEntGroupType().equals("Division")) || (localEntitlementGroup.getEntGroupType().equals("Business"))) && (j(paramSecureUser.getEntitlementID()) != jdMethod_for(localEntitlementGroup)))
        {
          DebugLog.log(Level.SEVERE, "UserAdmin.getBusinessEmployeesByEntGroupId.  Attempt to access business employees that the secure user is not entitled to access.");
          throw new CSILException(str1, 104);
        }
      }
      long l = System.currentTimeMillis();
      EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(paramInt);
      ArrayList localArrayList = new ArrayList();
      if (localEntitlementGroupMembers != null)
      {
        localObject = localEntitlementGroupMembers.iterator();
        while (((Iterator)localObject).hasNext())
        {
          EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)((Iterator)localObject).next();
          String str2 = localEntitlementGroupMember.getMemberType();
          String str3 = localEntitlementGroupMember.getMemberSubType();
          if ((str2 != null) && (str2.compareTo("USER") == 0) && (str3 != null) && (str3.compareTo(String.valueOf(1)) == 0)) {
            localArrayList.add(String.valueOf(localEntitlementGroupMember.getId()));
          }
        }
      }
      Object localObject = UserAdminHandler.getBusinessEmployeesByIds(paramSecureUser, localArrayList, paramHashMap);
      jdMethod_for((BusinessEmployees)localObject);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_29", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  private static void jdMethod_try(User paramUser)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = Entitlements.getMember(paramUser.getEntitlementGroupMember());
    paramUser.setEntitlementGroupId(localEntitlementGroupMember.getEntitlementGroupId());
  }
  
  private static void jdMethod_for(Users paramUsers)
    throws CSILException
  {
    Iterator localIterator = paramUsers.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      try
      {
        jdMethod_try(localUser);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("WARNING: Unable to retrieve EntitlementGroup for User with ID = " + localUser.getId());
      }
    }
  }
  
  private static void jdMethod_for(BusinessEmployees paramBusinessEmployees)
    throws CSILException
  {
    Iterator localIterator = paramBusinessEmployees.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      try
      {
        jdMethod_try(localUser);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("WARNING: Unable to retrieve EntitlementGroup for User with ID = " + localUser.getId());
      }
    }
  }
  
  public static BusinessEmployees getBusinessEmployeeByServicesPackageId(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetBusinessEmployeesByServicesPackageId";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      BusinessEmployees localBusinessEmployees = null;
      if (paramInt == 0)
      {
        localBusinessEmployees = UserAdminHandler.getFilteredBusinessEmployees(paramSecureUser, null, null, paramHashMap);
      }
      else
      {
        localBusinessEmployees = new BusinessEmployees(paramSecureUser.getLocale());
        BusinessEmployee localBusinessEmployee = new BusinessEmployee();
        localBusinessEmployee.setBankId(paramSecureUser.getBankID());
        Businesses localBusinesses = Business.getBusinessesByServicesPackage(paramSecureUser, paramInt, paramHashMap);
        Iterator localIterator = localBusinesses.iterator();
        while (localIterator.hasNext())
        {
          com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)localIterator.next();
          localBusinessEmployee.setBusinessId(localBusiness.getId());
          localBusinessEmployees.addAll(getBusinessEmployees(paramSecureUser, localBusinessEmployee, paramHashMap));
        }
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localBusinessEmployees;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_30", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getBusinessEmployeesIdByServicesPackageId(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetBusinessEmployeesIdByServicesPackageId";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList1 = null;
      if (paramInt == 0)
      {
        localArrayList1 = UserAdminHandler.getFilteredBusinessEmployeesIDs(paramSecureUser, null, null, paramHashMap);
      }
      else
      {
        BusinessEmployee localBusinessEmployee = new BusinessEmployee();
        localBusinessEmployee.setBankId(paramSecureUser.getBankID());
        ArrayList localArrayList2 = Business.getBusinessNamesByServicesPackage(paramSecureUser, paramInt, paramHashMap);
        localArrayList1 = getBusinessEmployeesIDs(paramSecureUser, localArrayList2, paramHashMap);
      }
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localArrayList1;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_31", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Users getUsersByMarketSegment(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetUsersByMarketSegment";
    long l = System.currentTimeMillis();
    Users localUsers1 = new Users(Locale.getDefault());
    EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(paramInt, "ServicesPackage");
    Iterator localIterator = localEntitlementGroups.iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
      Users localUsers2 = getUsersByServicesPackage(paramSecureUser, localEntitlementGroup.getGroupId(), paramHashMap);
      localUsers1.addAll(localUsers2);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localUsers1;
  }
  
  public static Users getUsersByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetUsersByServicesPackage";
    long l = System.currentTimeMillis();
    Users localUsers1 = new Users(Locale.getDefault());
    Object localObject;
    if (paramInt == 0)
    {
      localObject = new User();
      ((User)localObject).setBankId(paramSecureUser.getBankID());
      localUsers1 = UserAdminHandler.getUserList(paramSecureUser, (User)localObject, paramHashMap);
    }
    else
    {
      localObject = Entitlements.getChildrenByGroupType(paramInt, "ConsumerAdmin");
      Iterator localIterator = ((EntitlementGroups)localObject).iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator.next();
        try
        {
          User localUser1 = null;
          EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "USER");
          EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localEntitlementGroups.get(0);
          String str2 = localEntitlementGroup2.getGroupName();
          User localUser2 = new User();
          localUser2.setBankId(paramSecureUser.getBankID());
          localUser2.setUserName(str2);
          Users localUsers2 = UserAdminHandler.getUsers(paramSecureUser, localUser2, paramHashMap);
          if ((localUsers2 != null) && (localUsers2.size() > 0))
          {
            localUser1 = (User)localUsers2.get(0);
            localUser1.setEntitlementGroupId(localEntitlementGroup2.getGroupId());
            localUser1.setServicesPackageId(paramInt);
            localUsers1.add(localUser1);
          }
        }
        catch (CSILException localCSILException) {}catch (Exception localException)
        {
          DebugLog.log("WARNING: Exception thrown from getUsersByServicesPackage" + localException.getMessage());
        }
      }
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localUsers1;
  }
  
  public static ArrayList getUserIDsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetUsersByServicesPackage";
    long l = System.currentTimeMillis();
    ArrayList localArrayList1 = new ArrayList();
    Object localObject;
    if (paramInt == 0)
    {
      localObject = new User();
      ((User)localObject).setBankId(paramSecureUser.getBankID());
      localArrayList1 = UserAdminHandler.getUsersIDs(paramSecureUser, (User)localObject, paramHashMap);
    }
    else
    {
      localObject = Entitlements.getChildrenByGroupType(paramInt, "ConsumerAdmin");
      ArrayList localArrayList2 = new ArrayList();
      Iterator localIterator = ((EntitlementGroups)localObject).iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
        try
        {
          String str2 = localEntitlementGroup.getGroupName();
          int i = str2.indexOf("Admin_");
          if (i == -1) {
            throw new CSILException(str1, 26001);
          }
          str2 = str2.substring(i + "Admin_".length());
          localArrayList2.add(str2);
        }
        catch (CSILException localCSILException) {}catch (Exception localException)
        {
          DebugLog.log("WARNING: Exception thrown from getUsersByServicesPackage" + localException.getMessage());
        }
      }
      return UserAdminHandler.getUserIDsByUserNames(paramSecureUser, localArrayList2, paramHashMap);
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localArrayList1;
  }
  
  public static User resetUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.ResetUserPassword";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azg))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if (!jdMethod_int(paramSecureUser, paramUser))
      {
        DebugLog.log(Level.SEVERE, "UserAdmin.ResetUserPassword.  Attempt to modify a user that the secure user is not entitled to modify.");
        throw new CSILException(str1, 104);
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      User localUser = UserAdminHandler.resetUserPassword(paramSecureUser, paramUser, paramString1, paramString2, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramUser.getUserName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_25", arrayOfObject);
      if ((paramUser instanceof BusinessEmployee))
      {
        BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramUser;
        int j = localBusinessEmployee.getBusinessId();
        audit(paramSecureUser, localLocalizableString2, j, str2, 3221);
      }
      else if (paramUser.get("BUSINESS_ID") != null)
      {
        try
        {
          int i = Integer.parseInt((String)paramUser.get("BUSINESS_ID"));
          audit(paramSecureUser, localLocalizableString2, i, str2, 3221);
        }
        catch (Exception localException)
        {
          audit(paramSecureUser, localLocalizableString2, str2, 3221);
        }
      }
      else
      {
        audit(paramSecureUser, localLocalizableString2, str2, 3221);
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 1, paramUser.getId());
      localHistoryTracker.logChange(localHistoryTracker.lookupField(User.BEAN_NAME, "PASSWORD"), (String)null, (String)null, null);
      if (!localHistoryTracker.getHistories().isEmpty()) {
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for " + str1 + ": " + localProfileException.toString());
        }
      }
      debug(paramSecureUser, str1);
      return localUser;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_32", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "UserAdmin.GetBankIdsByServicesPackage";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = null;
    localArrayList = UserAdminHandler.getBankIdsByServicesPackage(paramSecureUser, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localArrayList;
  }
  
  public static BusinessEmployees getFilteredBusinessEmployeesByEntGroupId(SecureUser paramSecureUser, int paramInt, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.GetBusinessEmployeesByEntGroupId";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ayT))
    {
      long l = System.currentTimeMillis();
      EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(paramInt);
      ArrayList localArrayList = new ArrayList();
      if (localEntitlementGroupMembers != null)
      {
        localObject = localEntitlementGroupMembers.iterator();
        while (((Iterator)localObject).hasNext())
        {
          EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)((Iterator)localObject).next();
          String str2 = localEntitlementGroupMember.getMemberType();
          String str3 = localEntitlementGroupMember.getMemberSubType();
          if ((str2 != null) && (str2.compareTo("USER") == 0) && (str3 != null) && (str3.compareTo(String.valueOf(1)) == 0)) {
            localArrayList.add(String.valueOf(localEntitlementGroupMember.getId()));
          }
        }
      }
      Object localObject = UserAdminHandler.getFilteredBusinessEmployeesByIds(paramSecureUser, localArrayList, paramBusinessEmployee, paramHashMap);
      jdMethod_for((BusinessEmployees)localObject);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return localObject;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditEntFault_33", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static boolean getInfoForAuditing(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return UserAdminHandler.getInfoForAuditing(paramSecureUser, paramHashMap);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, String paramString)
  {
    int i = identifyUserType(paramSecureUser);
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    if (paramSecureUser.getAgent() == null)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramSecureUser.getUserName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_10", arrayOfObject);
    }
    else
    {
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramSecureUser.getUserName();
      arrayOfObject[1] = paramSecureUser.getAgent().getUserName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_11", arrayOfObject);
    }
    audit(paramSecureUser, localLocalizableString, paramString, 3201);
  }
  
  public static void registerUserWithBPW(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    CustomerInfo localCustomerInfo = jdMethod_for(paramSecureUser, paramUser, paramHashMap);
    CustomerInfo[] arrayOfCustomerInfo = new CustomerInfo[1];
    arrayOfCustomerInfo[0] = localCustomerInfo;
    BPTW.addCustomers(paramSecureUser, arrayOfCustomerInfo, paramHashMap);
  }
  
  public static void modifyUserRegisteredWithBPW(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    CustomerInfo localCustomerInfo = jdMethod_for(paramSecureUser, paramUser, paramHashMap);
    CustomerInfo[] arrayOfCustomerInfo = new CustomerInfo[1];
    arrayOfCustomerInfo[0] = localCustomerInfo;
    BPTW.modifyCustomers(paramSecureUser, arrayOfCustomerInfo, paramHashMap);
  }
  
  public static void activateUserRegisteredWithBPW(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramUser.getId();
    BPTW.activateCustomers(paramSecureUser, arrayOfString, paramHashMap);
  }
  
  public static void deactivateUserRegisteredWithBPW(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramUser.getId();
    BPTW.deactivateCustomers(paramSecureUser, arrayOfString, paramHashMap);
  }
  
  public static void deleteUserRegisteredWithBPW(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramUser.getId();
    BPTW.deleteCustomers(paramSecureUser, arrayOfString, paramHashMap);
  }
  
  private static CustomerInfo jdMethod_for(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws CSILException
  {
    CustomerInfo localCustomerInfo = new CustomerInfo();
    localCustomerInfo.customerID = paramUser.getId();
    localCustomerInfo.firstName = paramUser.getFirstName();
    localCustomerInfo.initial = paramUser.getMiddleInitial();
    localCustomerInfo.lastName = paramUser.getLastName();
    localCustomerInfo.ssn = paramUser.getSSN();
    localCustomerInfo.addressLine1 = paramUser.getStreet();
    localCustomerInfo.addressLine2 = paramUser.getStreet2();
    localCustomerInfo.city = paramUser.getCity();
    localCustomerInfo.state = paramUser.getStateCode();
    localCustomerInfo.zipcode = paramUser.getZipCode();
    localCustomerInfo.country = paramUser.getCountry();
    localCustomerInfo.phone1 = paramUser.getPhone();
    localCustomerInfo.phone2 = paramUser.getPhone2();
    localCustomerInfo.email = paramUser.getEmail();
    localCustomerInfo.setUserType(UserType.CONSUMER);
    AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(paramSecureUser, paramUser.getAffiliateBankID(), paramHashMap);
    localCustomerInfo.fIID = localAffiliateBank.getFIBPWID();
    return localCustomerInfo;
  }
  
  public static DateTime getLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.core.UserAdmin.getLastExportedDateTime";
    DateTime localDateTime = null;
    jdMethod_char(paramSecureUser, paramString, str);
    long l = System.currentTimeMillis();
    localDateTime = UserAdminHandler.getLastExportedDateTime(paramSecureUser, paramAccount, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localDateTime;
  }
  
  public static void setLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.core.UserAdmin.setLastExportedDateTime";
    jdMethod_char(paramSecureUser, paramString, str);
    long l = System.currentTimeMillis();
    UserAdminHandler.setLastExportedDateTime(paramSecureUser, paramAccount, paramString, paramDateTime, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  private static void jdMethod_char(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = null;
    if ("ConsumerAccountHistory".equals(paramString1)) {
      localEntitlement = ayf;
    } else if ("ConsumerAccountRegister".equals(paramString1)) {
      localEntitlement = ayM;
    }
    if ((localEntitlement != null) && (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)))
    {
      debug("User:" + paramSecureUser.getUserName() + " not entitled for " + paramString2);
      throw new CSILException(paramString2, 20001);
    }
  }
  
  public static void addContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.addContactPoint(SecureUser, ContactPoint, HashMap)";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    Object[] arrayOfObject = new Object[2];
    String str2 = TrackingIDGenerator.GetNextID();
    if (paramContactPoint.getDirectoryID() == -1) {
      throw new CSILException(str1, 20001, "The directory id was not specified in the contact point.  The directory id of the caller must be specified in the contact point in order for this operation to proceed.");
    }
    LocalizableString localLocalizableString;
    if (paramSecureUser.getProfileID() != paramContactPoint.getDirectoryID())
    {
      arrayOfObject[0] = paramSecureUser.getUserName();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Integer(paramContactPoint.getDirectoryID()).toString());
      Users localUsers = UserAdminHandler.getUsersByIds(paramSecureUser, localArrayList, paramHashMap);
      if (localUsers.size() > 0)
      {
        User localUser = (User)localUsers.get(0);
        arrayOfObject[1] = localUser.getUserName();
      }
      else
      {
        arrayOfObject[1] = new Integer(paramContactPoint.getDirectoryID()).toString();
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_29", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 2401);
      throw new CSILException(str1, 20001, "The directory id of the specified contact point does not match the directory ID of the user requesting this operation.  The directory id of the caller and the contact point must be the same.");
    }
    long l = System.currentTimeMillis();
    UserAdminHandler.addContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
    if (paramContactPoint.getContactPointType() == 2)
    {
      arrayOfObject[0] = paramContactPoint.getName();
      arrayOfObject[1] = paramContactPoint.getAddress();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_27", arrayOfObject);
    }
    else if (paramContactPoint.getContactPointType() == 1)
    {
      arrayOfObject[0] = paramContactPoint.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_28", arrayOfObject);
    }
    else
    {
      arrayOfObject[0] = paramContactPoint.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_36", arrayOfObject);
    }
    audit(paramSecureUser, localLocalizableString, str2, 3229);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void addContactPoints(SecureUser paramSecureUser, ContactPoints paramContactPoints, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.addContactPoint(SecureUser, ContactPoint, HashMap)";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    Object[] arrayOfObject = new Object[2];
    String str2 = TrackingIDGenerator.GetNextID();
    Object localObject;
    LocalizableString localLocalizableString;
    for (int i = 0; i < paramContactPoints.size(); i++)
    {
      ContactPoint localContactPoint = (ContactPoint)paramContactPoints.get(i);
      if (localContactPoint.getDirectoryID() == -1) {
        throw new CSILException(str1, 20001, "The directory id was not specified in the contact point.  The directory id of the caller must be specified in the contact point in order for this operation to proceed.");
      }
      if (paramSecureUser.getProfileID() != localContactPoint.getDirectoryID())
      {
        arrayOfObject[0] = paramSecureUser.getUserName();
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new Integer(localContactPoint.getDirectoryID()).toString());
        localObject = UserAdminHandler.getUsersByIds(paramSecureUser, localArrayList, paramHashMap);
        if (((Users)localObject).size() > 0)
        {
          User localUser = (User)((Users)localObject).get(0);
          arrayOfObject[1] = localUser.getUserName();
        }
        else
        {
          arrayOfObject[1] = new Integer(localContactPoint.getDirectoryID()).toString();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_29", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 2401);
        throw new CSILException(str1, 20001, "The directory id of the specified contact point does not match the directory ID of the user requesting this operation.  The directory id of the caller and the contact point must be the same.");
      }
    }
    long l = System.currentTimeMillis();
    UserAdminHandler.addContactPoints(paramSecureUser, paramContactPoints, paramHashMap);
    for (int j = 0; j < paramContactPoints.size(); j++)
    {
      localObject = (ContactPoint)paramContactPoints.get(j);
      if (((ContactPoint)localObject).getContactPointType() == 2)
      {
        arrayOfObject[0] = ((ContactPoint)localObject).getName();
        arrayOfObject[1] = ((ContactPoint)localObject).getAddress();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_27", arrayOfObject);
      }
      else if (((ContactPoint)localObject).getContactPointType() == 1)
      {
        arrayOfObject[0] = ((ContactPoint)localObject).getName();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_28", arrayOfObject);
      }
      else
      {
        arrayOfObject[0] = ((ContactPoint)localObject).getName();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_36", arrayOfObject);
      }
      audit(paramSecureUser, localLocalizableString, str2, 3229);
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void modifyContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint1, ContactPoint paramContactPoint2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdmin.modifyContactPoint(SecureUser, ContactPoint, HashMap)";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    ArrayList localArrayList1 = new ArrayList();
    String str2 = TrackingIDGenerator.GetNextID();
    if (paramContactPoint2.getDirectoryID() == -1) {
      throw new CSILException(str1, 20001, "The directory id was not specified in the contact point.  The directory id of the caller must be specified in the contact point in order for this operation to proceed.");
    }
    Object localObject;
    LocalizableString localLocalizableString;
    if (paramSecureUser.getProfileID() != paramContactPoint2.getDirectoryID())
    {
      localArrayList1.add(paramSecureUser.getUserName());
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add(new Integer(paramContactPoint2.getDirectoryID()).toString());
      Users localUsers = UserAdminHandler.getUsersByIds(paramSecureUser, localArrayList2, paramHashMap);
      if (localUsers.size() > 0)
      {
        localObject = (User)localUsers.get(0);
        localArrayList1.add(((User)localObject).getUserName());
      }
      else
      {
        localArrayList1.add(new Integer(paramContactPoint2.getDirectoryID()).toString());
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_32", localArrayList1.toArray());
      audit(paramSecureUser, localLocalizableString, str2, 2401);
      throw new CSILException(str1, 20001, "The directory id of the specified contact point does not match the directory ID of the user requesting this operation.  The directory id of the caller and the contact point must be the same.");
    }
    long l = System.currentTimeMillis();
    UserAdminHandler.modifyContactPoint(paramSecureUser, paramContactPoint2, paramHashMap);
    if (paramContactPoint2.getContactPointType() == 2)
    {
      localArrayList1.add(paramContactPoint1.getName());
      if ((paramContactPoint1.getName() != paramContactPoint2.getName()) && ((paramContactPoint1.getAddress() == null) || (!paramContactPoint1.getName().equals(paramContactPoint2.getName())))) {
        localArrayList1.add(paramContactPoint2.getName());
      }
      if ((paramContactPoint1.getAddress() != paramContactPoint2.getAddress()) && ((paramContactPoint1.getAddress() == null) || (!paramContactPoint1.getAddress().equals(paramContactPoint2.getAddress()))))
      {
        localArrayList1.add(paramContactPoint1.getAddress() == null ? "" : paramContactPoint1.getAddress());
        localArrayList1.add(paramContactPoint2.getAddress() == null ? "" : paramContactPoint2.getAddress());
      }
      localObject = null;
      if (localArrayList1.size() == 4) {
        localObject = "AuditMessage_30";
      } else if (localArrayList1.size() == 3) {
        localObject = "AuditMessage_30.1";
      } else if (localArrayList1.size() == 2) {
        localObject = "AuditMessage_30.2";
      } else if (localArrayList1.size() == 1) {
        localObject = "AuditMessage_30.3";
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", (String)localObject, localArrayList1.toArray());
    }
    else if (paramContactPoint2.getContactPointType() == 1)
    {
      localArrayList1.add(paramContactPoint1.getName());
      if (!paramContactPoint1.getName().equals(paramContactPoint2.getName())) {
        localArrayList1.add(paramContactPoint2.getName());
      }
      localObject = null;
      if (localArrayList1.size() == 2) {
        localObject = "AuditMessage_31";
      } else if (localArrayList1.size() == 1) {
        localObject = "AuditMessage_31.1";
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", (String)localObject, localArrayList1.toArray());
    }
    else
    {
      localArrayList1.add(paramContactPoint1.getName());
      if (!paramContactPoint1.getName().equals(paramContactPoint2.getName())) {
        localArrayList1.add(paramContactPoint2.getName());
      }
      localObject = null;
      if (localArrayList1.size() == 2) {
        localObject = "AuditMessage_37";
      } else if (localArrayList1.size() == 1) {
        localObject = "AuditMessage_37.1";
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", (String)localObject, localArrayList1.toArray());
    }
    audit(paramSecureUser, localLocalizableString, str2, 3230);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static void deleteContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdminHandler.deleteContactPoint(SecureUser, ContactPoint,HashMap)";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    Object[] arrayOfObject = new Object[2];
    String str2 = TrackingIDGenerator.GetNextID();
    if (paramContactPoint.getDirectoryID() == -1) {
      throw new CSILException(str1, 20001, "The directory id was not specified in the contact point.  The directory id of the caller must be specified in the contact point in order for this operation to proceed.");
    }
    LocalizableString localLocalizableString;
    if (paramSecureUser.getProfileID() != paramContactPoint.getDirectoryID())
    {
      arrayOfObject[0] = paramSecureUser.getUserName();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Integer(paramContactPoint.getDirectoryID()).toString());
      Users localUsers = UserAdminHandler.getUsersByIds(paramSecureUser, localArrayList, paramHashMap);
      if (localUsers.size() > 0)
      {
        User localUser = (User)localUsers.get(0);
        arrayOfObject[1] = localUser.getUserName();
      }
      else
      {
        arrayOfObject[1] = new Integer(paramContactPoint.getDirectoryID()).toString();
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_35", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 2401);
      throw new CSILException(str1, 20001, "The directory id of the specified contact point does not match the directory ID of the user requesting this operation.  The directory id of the caller and the contact point must be the same.");
    }
    long l = System.currentTimeMillis();
    UserAdminHandler.deleteContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
    if (paramContactPoint.getContactPointType() == 2)
    {
      arrayOfObject[0] = paramContactPoint.getName();
      arrayOfObject[1] = paramContactPoint.getAddress();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_33", arrayOfObject);
    }
    else if (paramContactPoint.getContactPointType() == 1)
    {
      arrayOfObject[0] = paramContactPoint.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_34", arrayOfObject);
    }
    else
    {
      arrayOfObject[0] = paramContactPoint.getName();
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_38", arrayOfObject);
    }
    audit(paramSecureUser, localLocalizableString, str2, 3231);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
  }
  
  public static ContactPoints getContactPoints(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdminHandler.getContactPoints(SecureUser, HashMap)";
    long l = System.currentTimeMillis();
    if (paramSecureUser.getProfileID() != paramInt)
    {
      localObject1 = new Object[2];
      localObject1[0] = paramSecureUser.getUserName();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Integer(paramInt).toString());
      Users localUsers = UserAdminHandler.getUsersByIds(paramSecureUser, localArrayList, paramHashMap);
      if (localUsers.size() > 0)
      {
        localObject2 = (User)localUsers.get(0);
        localObject1[1] = ((User)localObject2).getUserName();
      }
      else
      {
        localObject1[1] = new Integer(paramInt).toString();
      }
      Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_40", (Object[])localObject1);
      String str2 = TrackingIDGenerator.GetNextID();
      audit(paramSecureUser, (ILocalizable)localObject2, str2, 2401);
      throw new CSILException(str1, 20001, "The user is attempting to retrieve transactions for a user other than him/herself.");
    }
    Object localObject1 = UserAdminHandler.getContactPoints(paramSecureUser, paramInt, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localObject1;
  }
  
  public static ContactPoint getContactPoint(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UserAdminHandler.getContactPoint(SecureUser, int, HashMap)";
    long l = System.currentTimeMillis();
    ContactPoint localContactPoint = UserAdminHandler.getContactPoint(paramSecureUser, paramInt, paramHashMap);
    if ((localContactPoint != null) && (localContactPoint.getDirectoryID() != paramSecureUser.getProfileID()))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramSecureUser.getUserName();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new Integer(localContactPoint.getDirectoryID()).toString());
      Users localUsers = UserAdminHandler.getUsersByIds(paramSecureUser, localArrayList, paramHashMap);
      if (localUsers.size() > 0)
      {
        localObject = (User)localUsers.get(0);
        arrayOfObject[1] = ((User)localObject).getUserName();
      }
      else
      {
        arrayOfObject[1] = new Integer(localContactPoint.getDirectoryID()).toString();
      }
      Object localObject = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_39", arrayOfObject);
      String str2 = TrackingIDGenerator.GetNextID();
      audit(paramSecureUser, (ILocalizable)localObject, str2, 2401);
      throw new CSILException(str1, 20001, "The user attempted to retrieve a contact point that did not belong to him or her.");
    }
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localContactPoint;
  }
  
  private static boolean jdMethod_int(SecureUser paramSecureUser, User paramUser)
    throws CSILException
  {
    boolean bool = true;
    if ((paramSecureUser.getUserType() != 1) || (paramSecureUser.getProfileID() != paramUser.getIdValue()))
    {
      bool = false;
      HashMap localHashMap = new HashMap();
      User localUser = new User();
      localUser.setId(paramUser.getId());
      localUser = UserAdminHandler.getUserById(paramSecureUser, localUser, localHashMap);
      if (paramSecureUser.getUserType() == 1)
      {
        Object localObject;
        if (localUser.getCustomerTypeValue() == 2)
        {
          if (localUser.getPrimarySecondaryValue() == 2)
          {
            localObject = getPrimaryUser(paramSecureUser, localUser, localHashMap);
            if (((User)localObject).getIdValue() == paramSecureUser.getProfileID()) {
              bool = true;
            }
          }
        }
        else
        {
          localObject = (String)localUser.get("BUSINESS_ID");
          int i = 0;
          try
          {
            i = Integer.parseInt((String)localObject);
          }
          catch (NumberFormatException localNumberFormatException) {}
          if (paramSecureUser.getBusinessID() == i) {
            bool = true;
          }
        }
      }
      else if ((paramSecureUser.getAffiliateIDValue() == 0) || (paramSecureUser.getAffiliateIDValue() == localUser.getAffiliateBankID()))
      {
        bool = true;
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.UserAdmin
 * JD-Core Version:    0.7.0.1
 */