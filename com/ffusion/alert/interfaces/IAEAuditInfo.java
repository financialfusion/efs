package com.ffusion.alert.interfaces;

import java.io.Serializable;
import java.util.Date;

public abstract interface IAEAuditInfo
  extends Serializable
{
  public static final int AUDIT_ALERT_DISPATCH = 1;
  public static final int AUDIT_DELIVERY_STATUS = 2;
  public static final int AUDIT_DELIVERY_COMPLETED = 3;
  
  public abstract int getAlertId();
  
  public abstract int getAlertSequence();
  
  public abstract String getChannelName();
  
  public abstract IAEDeliveryInfo getDeliveryInfo();
  
  public abstract Date getAuditDate();
  
  public abstract int getAuditType();
  
  public abstract int getAuditStatus();
  
  public abstract long getTimeInfo();
  
  public abstract String getApplicationName();
  
  public abstract String getUserId();
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEAuditInfo
 * JD-Core Version:    0.7.0.1
 */