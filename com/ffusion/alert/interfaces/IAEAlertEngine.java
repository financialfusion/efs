package com.ffusion.alert.interfaces;

public abstract interface IAEAlertEngine
{
  public abstract int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString1, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException;
  
  public abstract int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException;
  
  public abstract void cancelAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws AEException;
  
  public abstract IAEAlertDefinition getAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws AEException;
  
  public abstract IAEAlertDefinition[] getUserAlerts(AEApplicationInfo paramAEApplicationInfo, String paramString, boolean paramBoolean)
    throws AEException;
  
  public abstract IAEAlertDefinition[] getAppAlerts(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws AEException;
  
  public abstract IAEAlertDefinition[] getAppAlertsForChannelPaged(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean, String paramString, int paramInt1, int paramInt2)
    throws AEException;
  
  public abstract IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException;
  
  public abstract IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException;
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEAlertEngine
 * JD-Core Version:    0.7.0.1
 */