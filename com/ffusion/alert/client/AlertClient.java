package com.ffusion.alert.client;

import com.ffusion.alert.engine.AEInstance;
import com.ffusion.alert.engine.AlertEngine;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.shared.AEResourceBundle;
import java.util.Date;

public class AlertClient
  implements IAEErrConstants
{
  static AEResourceBundle a = new AEResourceBundle("com.ffusion.alert.client.lang.AEErrStrings");
  
  public static int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString1, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException
  {
    a();
    return AEInstance.if.createAlert(paramAEApplicationInfo, paramString1, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
  }
  
  public static int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    a();
    return AEInstance.if.createAlert(paramAEApplicationInfo, paramString, paramInt1, paramInt2, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
  }
  
  public static void cancelAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws AEException
  {
    a();
    AEInstance.if.cancelAlert(paramAEApplicationInfo, paramInt);
  }
  
  public static IAEAlertDefinition getAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws AEException
  {
    a();
    return AEInstance.if.getAlert(paramAEApplicationInfo, paramInt);
  }
  
  public static IAEAlertDefinition[] getUserAlerts(AEApplicationInfo paramAEApplicationInfo, String paramString, boolean paramBoolean)
    throws AEException
  {
    a();
    return AEInstance.if.getUserAlerts(paramAEApplicationInfo, paramString, paramBoolean);
  }
  
  public static IAEAlertDefinition[] getAppAlerts(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws AEException
  {
    a();
    return AEInstance.if.getAppAlerts(paramAEApplicationInfo, paramBoolean);
  }
  
  public static IAEAlertDefinition[] getAppAlertsForChannelPaged(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean, String paramString, int paramInt1, int paramInt2)
    throws AEException
  {
    a();
    return AEInstance.if.getAppAlertsForChannelPaged(paramAEApplicationInfo, paramBoolean, paramString, paramInt1, paramInt2);
  }
  
  public static IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException
  {
    a();
    return AEInstance.if.updateAlert(paramAEApplicationInfo, paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
  }
  
  public static IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    a();
    return AEInstance.if.updateAlert(paramAEApplicationInfo, paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
  }
  
  public static IAEAuditInfo[] getUserAuditInfo(AEApplicationInfo paramAEApplicationInfo, String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    a();
    return AEInstance.if.a(paramAEApplicationInfo, paramString, paramDate1, paramDate2);
  }
  
  public static IAEChannelInfo[] getInstalledDeliveryChannels(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    a();
    return AEInstance.if.jdMethod_do(paramAEApplicationInfo);
  }
  
  public static IAEChannelInfo[] getLoadedDeliveryChannels(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    a();
    return AEInstance.if.jdMethod_if(paramAEApplicationInfo);
  }
  
  private static void a()
    throws AEException
  {
    if (AEInstance.if == null) {
      throw new AEException(3000, a.a("ENGINE_NOT_RUNNING"));
    }
    if (AEInstance.if.C())
    {
      if (!AEInstance.if.I()) {
        throw new AEException(3000, a.a("ENGINE_NOT_RUNNING"));
      }
    }
    else if (!AEInstance.if.p()) {
      throw new AEException(3000, a.a("ENGINE_NOT_RUNNING"));
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.client.AlertClient
 * JD-Core Version:    0.7.0.1
 */