package com.ffusion.alert.interfaces;

import java.io.Serializable;

public abstract interface IAEAlertDefinition
  extends Serializable
{
  public static final int IA_TYPE_IMMEDIATE = 0;
  public static final int IA_TYPE_SCHEDULED = 1;
  public static final int IA_TYPE_RECURRING = 2;
  public static final int IA_STATUS_ACTIVE = 0;
  public static final int IA_STATUS_COMPLETED = 1;
  public static final int IA_STATUS_CANCELLED = 2;
  
  public abstract int getId();
  
  public abstract int getType();
  
  public abstract int getPriority();
  
  public abstract String getApplicationName();
  
  public abstract String getUserId();
  
  public abstract String getMessage();
  
  public abstract byte[] getMessageBytes();
  
  public abstract AEScheduleInfo getScheduleInfo();
  
  public abstract IAEDeliveryInfo[] getDeliveryInfo();
  
  public abstract int getStatus();
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEAlertDefinition
 * JD-Core Version:    0.7.0.1
 */