package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.services.alerts.AlertsException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface Alerts2
  extends Alerts
{
  public abstract void initialize(HashMap paramHashMap)
    throws AlertsException;
  
  public abstract void addUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract void modifyUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract void deleteUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract UserAlerts getUserAlerts(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract UserAlert getUserAlert(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract ArrayList getAlertsForAccounts(Accounts paramAccounts, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract void sendImmediateAlert(SecureUser paramSecureUser, ContactPoint paramContactPoint, String paramString1, String paramString2, HashMap paramHashMap)
    throws AlertsException;
  
  public abstract LogMessages getLogMessages(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Alerts2
 * JD-Core Version:    0.7.0.1
 */