package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;

public class Alerts
  extends Initialize
{
  private static Entitlement ast = new Entitlement("Alerts", null, null);
  private static final String asu = "com.ffusion.util.logging.audit.alerts";
  private static final String asp = "AuditMessage_1";
  private static final String ask = "AuditMessage_2";
  private static final String asr = "AuditMessage_3";
  private static final String asq = "AuditMessage_4";
  private static final String asv = "AuditMessage_5";
  private static final String ass = "AuditMessage_6";
  private static final String asm = "AuditMessage_7";
  private static final String aso = "AuditMessage_8";
  private static final String asj = "AuditMessage_9";
  private static final String asn = "AuditMessage_10";
  private static final String asl = "AuditMessage_11";
  private static final String asi = "AuditMessage_14";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Alerts.initialize");
    com.ffusion.csil.handlers.Alerts.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Alerts.getService();
  }
  
  public static Alert addAlert(SecureUser paramSecureUser, Alert paramAlert, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.AddAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Alert localAlert = com.ffusion.csil.handlers.Alerts.addAlert(paramSecureUser, paramAlert, paramHashMap);
      PerfLog.log(str1, l, true);
      AlertAuditLogger.log(paramSecureUser, paramAlert, str2, 1500);
      debug(paramSecureUser, str1);
      return localAlert;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_1", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Alert deleteAlert(SecureUser paramSecureUser, Alert paramAlert, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.DeleteAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Alert localAlert = com.ffusion.csil.handlers.Alerts.deleteAlert(paramSecureUser, paramAlert, paramHashMap);
      PerfLog.log(str1, l, true);
      AlertAuditLogger.log(paramSecureUser, paramAlert, str2, 1502);
      debug(paramSecureUser, str1);
      return localAlert;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_2", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.alerts.Alerts getAlerts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.GetAlerts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      long l = System.currentTimeMillis();
      com.ffusion.beans.alerts.Alerts localAlerts = com.ffusion.csil.handlers.Alerts.getAlerts(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAlerts;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_3", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static LogMessages getLogMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.GetLogMessages";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      long l = System.currentTimeMillis();
      LogMessages localLogMessages = com.ffusion.csil.handlers.Alerts.getLogMessages(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLogMessages;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_4", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Alert modifyAlert(SecureUser paramSecureUser, Alert paramAlert, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.ModifyAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Alert localAlert = com.ffusion.csil.handlers.Alerts.modifyAlert(paramSecureUser, paramAlert, paramHashMap);
      PerfLog.log(str1, l, true);
      AlertAuditLogger.log(paramSecureUser, paramAlert, str2, 1501);
      debug(paramSecureUser, str1);
      return localAlert;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_5", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void sendAlertToUser(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.SendAlertToUser";
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Alerts.sendAlertToUser(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static void addUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.addUserAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser == null) || (paramUserAlert == null) || (paramSecureUser.getProfileID() != paramUserAlert.getDirectoryIdValue()))
      {
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_8", null);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 19013);
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.Alerts.addUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_9", new Object[] { paramUserAlert.getUserAlertId() });
      audit(paramSecureUser, localLocalizableString3, str2, 1503);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void modifyUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.modifyUserAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser == null) || (paramUserAlert == null) || (paramSecureUser.getProfileID() != paramUserAlert.getDirectoryIdValue()))
      {
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_8", null);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 19013);
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.Alerts.modifyUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_10", new Object[] { paramUserAlert.getUserAlertId() });
      audit(paramSecureUser, localLocalizableString3, str2, 1504);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_5", null);
      logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.deleteUserAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser == null) || (paramUserAlert == null) || (paramSecureUser.getProfileID() != paramUserAlert.getDirectoryIdValue()))
      {
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_8", null);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 19013);
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.Alerts.deleteUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_11", new Object[] { paramUserAlert.getUserAlertId() });
      audit(paramSecureUser, localLocalizableString3, str2, 1505);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_2", null);
      logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static UserAlerts getUserAlerts(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.getUserAlerts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser == null) || (paramSecureUser.getProfileID() != paramInt))
      {
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_8", null);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 19013);
      }
      long l = System.currentTimeMillis();
      UserAlerts localUserAlerts = com.ffusion.csil.handlers.Alerts.getUserAlerts(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localUserAlerts;
    }
    LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static UserAlert getUserAlert(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.getUserAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      UserAlert localUserAlert = com.ffusion.csil.handlers.Alerts.getUserAlert(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      if ((localUserAlert == null) || (localUserAlert.getDirectoryIdValue() != paramSecureUser.getProfileID()))
      {
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_8", null);
        logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
        throw new CSILException(str, 19013);
      }
      debug(paramSecureUser, str);
      return localUserAlert;
    }
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static ArrayList getAlertsForAccounts(Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.getAlertsForAccount";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = com.ffusion.csil.handlers.Alerts.getAlertsForAccounts(paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
    return localArrayList;
  }
  
  public static UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.getPagedUserAlerts";
    long l = System.currentTimeMillis();
    UserAlerts localUserAlerts = com.ffusion.csil.handlers.Alerts.getPagedUserAlerts(paramInt1, paramInt2, paramInt3, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
    return localUserAlerts;
  }
  
  public static void sendImmediateAlert(SecureUser paramSecureUser, ContactPoint paramContactPoint, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Alerts.sendImmediateAlert";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if ((paramSecureUser == null) || (paramContactPoint == null) || (paramContactPoint.getDirectoryID() != paramSecureUser.getProfileID()))
      {
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_8", null);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 19013);
      }
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.Alerts.sendImmediateAlert(paramSecureUser, paramContactPoint, paramString1, paramString2, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = { paramContactPoint.getName(), paramString1, paramString2 };
      LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_14", arrayOfObject);
      audit(paramSecureUser, localLocalizableString3, str2, 1506);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_6", null);
      logEntitlementFault(paramSecureUser, localLocalizableString2, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static LogMessages getLogMessages(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.getLogMessages";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ast))
    {
      long l = System.currentTimeMillis();
      LogMessages localLogMessages = com.ffusion.csil.handlers.Alerts.getLogMessages(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localLogMessages;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alerts", "AuditMessage_4", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Alerts
 * JD-Core Version:    0.7.0.1
 */