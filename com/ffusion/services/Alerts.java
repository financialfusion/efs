package com.ffusion.services;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.LogMessages;

public abstract interface Alerts
{
  public static final int SUCCESS = 19000;
  public static final int ERROR_INVALID_USER_NAME = 19001;
  public static final int ERROR_INVALID_PASSWORD = 19002;
  public static final int ERROR_NOT_SUPPORTED = 19003;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 19004;
  public static final int ERROR_INVALID_INIT_FILE = 19005;
  public static final int ERROR_REMOTE_EXCEPTION = 19006;
  public static final int ERROR_EJB_NOT_FOUND = 19007;
  public static final int ERROR_NO_ALERT_SENT = 19008;
  public static final int ERROR_ALERT_ENGINE = 19009;
  public static final int ERROR_AE_CHANNEL_NAME = 19010;
  public static final int ERROR_AE_NOT_RUNNING = 19011;
  public static final int ERROR_INVALID_ALERT_DATA = 19012;
  
  public abstract int initialize(String paramString);
  
  public abstract void setUserName(String paramString);
  
  public abstract void setPassword(String paramString);
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract int addAlert(Alert paramAlert);
  
  public abstract int deleteAlert(Alert paramAlert);
  
  public abstract int getAlerts(com.ffusion.beans.alerts.Alerts paramAlerts);
  
  public abstract int getLogMessages(LogMessages paramLogMessages);
  
  public abstract int modifyAlert(Alert paramAlert);
  
  public abstract int sendAlertToUser(String paramString1, String paramString2);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Alerts
 * JD-Core Version:    0.7.0.1
 */