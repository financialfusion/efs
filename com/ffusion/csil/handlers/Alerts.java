package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Alerts2;
import com.ffusion.services.alerts.AlertsException;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class Alerts
  extends Initialize
{
  public static final String SERVICE_NAME = "Alerts";
  public static Alerts2 _alertsService = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Alerts.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Alerts", str, 20107);
    CSILException localCSILException;
    try
    {
      _alertsService = (Alerts2)HandlerUtil.instantiateService(localHashMap, str, 20107);
    }
    catch (ClassCastException localClassCastException)
    {
      localCSILException = new CSILException(20107, localClassCastException);
      DebugLog.throwing(str, localClassCastException);
      throw localCSILException;
    }
    try
    {
      _alertsService.initialize(paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      localCSILException = new CSILException(20107, localAlertsException.getCode(), localAlertsException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static com.ffusion.services.Alerts jdMethod_for(HashMap paramHashMap, int paramInt)
    throws CSILException
  {
    Object localObject = null;
    if (_alertsService == null) {
      checkExtra(paramHashMap);
    }
    try
    {
      if (paramHashMap != null) {
        localObject = (com.ffusion.services.Alerts)paramHashMap.get("SERVICE");
      }
    }
    catch (ClassCastException localClassCastException)
    {
      debug("Invalid required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    if (localObject == null) {
      localObject = _alertsService;
    }
    if (localObject == null)
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    switch (paramInt)
    {
    case 2: 
      if (!(localObject instanceof Alerts2))
      {
        debug("Alerts service does not implement the required interface.");
        throwing(-1009, -1010);
      }
      break;
    }
    return localObject;
  }
  
  public static Object getService()
  {
    return _alertsService;
  }
  
  public static Alert addAlert(SecureUser paramSecureUser, Alert paramAlert, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Alerts.addAlert");
    checkExtra(paramHashMap);
    com.ffusion.services.Alerts localAlerts = jdMethod_for(paramHashMap, 1);
    int i = localAlerts.addAlert(paramAlert);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAlert;
  }
  
  public static Alert deleteAlert(SecureUser paramSecureUser, Alert paramAlert, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Alerts.deleteAlert");
    checkExtra(paramHashMap);
    com.ffusion.services.Alerts localAlerts = jdMethod_for(paramHashMap, 1);
    int i = localAlerts.deleteAlert(paramAlert);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAlert;
  }
  
  public static com.ffusion.beans.alerts.Alerts getAlerts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Alerts.getAlerts");
    checkExtra(paramHashMap);
    com.ffusion.services.Alerts localAlerts = jdMethod_for(paramHashMap, 1);
    com.ffusion.beans.alerts.Alerts localAlerts1 = (com.ffusion.beans.alerts.Alerts)paramHashMap.get("ALERTSLIST");
    if (localAlerts1 == null)
    {
      debug("Missing required parameter: extra.'ALERTSLIST' - creating new Alerts()");
      localAlerts1 = new com.ffusion.beans.alerts.Alerts();
    }
    int i = localAlerts.getAlerts(localAlerts1);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localAlerts1;
  }
  
  public static LogMessages getLogMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Alerts.getLogMessages");
    checkExtra(paramHashMap);
    com.ffusion.services.Alerts localAlerts = jdMethod_for(paramHashMap, 1);
    LogMessages localLogMessages = (LogMessages)paramHashMap.get("LOGMESSAGES");
    if (localLogMessages == null)
    {
      debug("Missing required parameter: extra.'LOGMESSAGES' - creating new LogMessage()");
      localLogMessages = new LogMessages();
    }
    int i = localAlerts.getLogMessages(localLogMessages);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localLogMessages;
  }
  
  public static Alert modifyAlert(SecureUser paramSecureUser, Alert paramAlert, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Alerts.modifyAlert");
    checkExtra(paramHashMap);
    com.ffusion.services.Alerts localAlerts = jdMethod_for(paramHashMap, 1);
    int i = localAlerts.modifyAlert(paramAlert);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAlert;
  }
  
  public static void sendAlertToUser(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Alerts.sendAlertToUser");
    checkExtra(paramHashMap);
    com.ffusion.services.Alerts localAlerts = jdMethod_for(paramHashMap, 1);
    int i = localAlerts.sendAlertToUser(paramString1, paramString2);
    if (i != 0) {
      throwing(-1009, i);
    }
  }
  
  public static void addUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      localAlerts2.addUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static void modifyUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      localAlerts2.modifyUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static void deleteUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      localAlerts2.deleteUserAlert(paramSecureUser, paramUserAlert, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static UserAlerts getUserAlerts(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      return localAlerts2.getUserAlerts(paramSecureUser, paramInt, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static UserAlert getUserAlert(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      return localAlerts2.getUserAlert(paramSecureUser, paramInt, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static ArrayList getAlertsForAccounts(Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      return localAlerts2.getAlertsForAccounts(paramAccounts, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      return localAlerts2.getPagedUserAlerts(paramInt1, paramInt2, paramInt3, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static void sendImmediateAlert(SecureUser paramSecureUser, ContactPoint paramContactPoint, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      localAlerts2.sendImmediateAlert(paramSecureUser, paramContactPoint, paramString1, paramString2, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
  
  public static LogMessages getLogMessages(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Alerts2 localAlerts2 = (Alerts2)jdMethod_for(paramHashMap, 2);
    try
    {
      return localAlerts2.getLogMessages(paramSecureUser, paramInt, paramHashMap);
    }
    catch (AlertsException localAlertsException)
    {
      throw new CSILException(-1009, localAlertsException.getCode(), localAlertsException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Alerts
 * JD-Core Version:    0.7.0.1
 */