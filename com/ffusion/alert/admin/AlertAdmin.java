package com.ffusion.alert.admin;

import com.ffusion.alert.engine.AEInstance;
import com.ffusion.alert.engine.AlertEngine;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.AEServerInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.shared.AEResourceBundle;
import java.util.Date;
import java.util.Properties;

public class AlertAdmin
  implements IAEErrConstants
{
  private static Object a = new Object();
  static AEResourceBundle jdField_if = new AEResourceBundle("com.ffusion.alert.admin.lang.AEErrStrings");
  
  public static void destroyRepository(AEDBParams paramAEDBParams)
    throws AEException
  {
    synchronized (a)
    {
      a(false);
      AlertEngine.a(paramAEDBParams);
    }
  }
  
  public static void checkRepository(AEDBParams paramAEDBParams)
    throws AEException
  {
    synchronized (a)
    {
      a(false);
      AlertEngine.jdField_if(paramAEDBParams);
    }
  }
  
  public static void createRepository(AEDBParams paramAEDBParams, boolean paramBoolean)
    throws AEException
  {
    synchronized (a)
    {
      a(false);
      AlertEngine.a(paramAEDBParams, paramBoolean);
    }
  }
  
  public static void init(AEDBParams paramAEDBParams)
    throws AEException
  {
    synchronized (a)
    {
      if (AEInstance.jdField_if != null) {
        return;
      }
      AEInstance.jdField_if = new AlertEngine(paramAEDBParams);
    }
  }
  
  public static void init(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.jdMethod_new(paramString);
    }
  }
  
  public static void initServerAll(AEDBParams paramAEDBParams)
    throws AEException
  {
    synchronized (a)
    {
      if (AEInstance.jdField_if == null) {
        AEInstance.jdField_if = new AlertEngine(paramAEDBParams);
      }
      AEInstance.jdField_if.w();
    }
  }
  
  public static void start(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.jdMethod_try(paramString);
    }
  }
  
  public static void startServerNamed(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.i(paramString);
    }
  }
  
  public static void startServerAll()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.s();
    }
  }
  
  public static void stop(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.jdMethod_goto(paramString);
    }
  }
  
  public static void stopServerAll()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.e();
    }
  }
  
  public static void suspend(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.jdMethod_char(paramString);
    }
  }
  
  public static void suspendServerAll()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.t();
    }
  }
  
  public static void resume(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.jdMethod_case(paramString);
    }
  }
  
  public static void resumeServerAll()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.f();
    }
  }
  
  public static void shutdown(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      boolean bool = AEInstance.jdField_if.jdMethod_null(paramString);
      if (bool) {
        AEInstance.jdField_if = null;
      }
    }
  }
  
  public static void shutdownServerAll()
    throws AEException
  {
    synchronized (a)
    {
      if (AEInstance.jdField_if == null) {
        return;
      }
      AlertEngine localAlertEngine = AEInstance.jdField_if;
      AEInstance.jdField_if = null;
      try
      {
        localAlertEngine.D();
      }
      catch (AEException localAEException)
      {
        if ((localAlertEngine.p()) || (localAlertEngine.B()) || (localAlertEngine.l())) {
          AEInstance.jdField_if = localAlertEngine;
        }
        throw localAEException;
      }
    }
  }
  
  public static void shutdown()
    throws AEException
  {
    synchronized (a)
    {
      if (AEInstance.jdField_if == null) {
        return;
      }
      AlertEngine localAlertEngine = AEInstance.jdField_if;
      AEInstance.jdField_if = null;
      try
      {
        localAlertEngine.J();
      }
      catch (AEException localAEException)
      {
        AEInstance.jdField_if = localAlertEngine;
        throw localAEException;
      }
    }
  }
  
  public static void start()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.n();
    }
  }
  
  public static void stop()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      AEInstance.jdField_if.g();
    }
  }
  
  public static void suspend()
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.y();
  }
  
  public static void resume()
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.K();
  }
  
  public static boolean isInitialized()
    throws AEException
  {
    synchronized (a)
    {
      return AEInstance.jdField_if != null;
    }
  }
  
  public static boolean isSuspended()
    throws AEException
  {
    synchronized (a)
    {
      return (AEInstance.jdField_if != null) && (AEInstance.jdField_if.B());
    }
  }
  
  public static boolean isRunning()
    throws AEException
  {
    synchronized (a)
    {
      return (AEInstance.jdField_if != null) && (AEInstance.jdField_if.p());
    }
  }
  
  public static boolean isSuspended(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      return AEInstance.jdField_if.jdMethod_else(paramString);
    }
  }
  
  public static boolean isInitialized(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      return AEInstance.jdField_if.b(paramString);
    }
  }
  
  public static boolean isRunning(String paramString)
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      return AEInstance.jdField_if.d(paramString);
    }
  }
  
  public static boolean isClusterStopped()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      return AEInstance.jdField_if.m();
    }
  }
  
  public static boolean isClusterRunning()
    throws AEException
  {
    synchronized (a)
    {
      a(true);
      return AEInstance.jdField_if.x();
    }
  }
  
  public static void addApplication(AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.a(paramAEApplicationInfo);
  }
  
  public static void removeApplication(String paramString)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.g(paramString);
  }
  
  public static void updateApplication(String paramString, AEApplicationInfo paramAEApplicationInfo)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.a(paramString, paramAEApplicationInfo);
  }
  
  public static AEApplicationInfo[] getApplications()
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_void();
  }
  
  public static IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString1, paramString2, paramProperties, paramInt);
  }
  
  public static IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt, String paramString3)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString1, paramString2, paramProperties, paramInt, paramString3);
  }
  
  public static IAEChannelInfo uninstallDeliveryChannel(String paramString)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_int(paramString);
  }
  
  public static IAEChannelInfo loadDeliveryChannel(String paramString)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.f(paramString);
  }
  
  public static IAEChannelInfo unloadDeliveryChannel(String paramString)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.c(paramString);
  }
  
  public static IAEChannelInfo updateDeliveryChannel(IAEChannelInfo paramIAEChannelInfo)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramIAEChannelInfo);
  }
  
  public static IAEChannelInfo[] getInstalledDeliveryChannels(String paramString)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_void(paramString);
  }
  
  public static IAEChannelInfo[] getLoadedDeliveryChannels(String paramString)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_byte(paramString);
  }
  
  public static IAEAlertDefinition[] getAllAlerts(boolean paramBoolean)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_do(paramBoolean);
  }
  
  public static IAEAlertDefinition[] getAppAlerts(String paramString, boolean paramBoolean)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdField_if(paramString, paramBoolean);
  }
  
  public static IAEAlertDefinition[] getAppAlertsForChannelPaged(String paramString1, boolean paramBoolean, String paramString2, int paramInt1, int paramInt2)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString1, paramBoolean, paramString2, paramInt1, paramInt2);
  }
  
  public static IAEAlertDefinition[] getUserAlerts(String paramString1, String paramString2, boolean paramBoolean)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString1, paramString2, paramBoolean);
  }
  
  public static IAEAlertDefinition[] getUserAlerts(String paramString, boolean paramBoolean)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString, paramBoolean);
  }
  
  public static IAEAlertDefinition getAlert(int paramInt)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_null(paramInt);
  }
  
  public static IAEAlertDefinition[] getAlerts(int[] paramArrayOfInt)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramArrayOfInt);
  }
  
  public static IAEAlertDefinition updateAlert(int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
  }
  
  public static IAEAlertDefinition updateAlert(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
  }
  
  public static void cancelAlert(int paramInt)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.jdMethod_void(paramInt);
  }
  
  public static void cancelUserAlerts(String paramString1, String paramString2)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.jdField_if(paramString1, paramString2);
  }
  
  public static IAEAuditInfo[] getAuditInfo(Date paramDate1, Date paramDate2)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdField_if(paramDate1, paramDate2);
  }
  
  public static IAEAuditInfo[] getAppAuditInfo(String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString, paramDate1, paramDate2);
  }
  
  public static IAEAuditInfo[] getUserAuditInfo(String paramString1, String paramString2, Date paramDate1, Date paramDate2)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString1, paramString2, paramDate1, paramDate2);
  }
  
  public static IAEAuditInfo[] getUserAuditInfo(String paramString, Date paramDate1, Date paramDate2)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdField_if(paramString, paramDate1, paramDate2);
  }
  
  public static void setEngineProperties(Properties paramProperties)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.a(paramProperties);
  }
  
  public static Properties getEngineProperties()
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.v();
  }
  
  private static void a(boolean paramBoolean)
    throws AEException
  {
    if ((paramBoolean) && (AEInstance.jdField_if == null)) {
      throw new AEException(2001, jdField_if.a("ENGINE_NOT_INITIALIZED"));
    }
    if ((!paramBoolean) && (AEInstance.jdField_if != null)) {
      throw new AEException(2000, jdField_if.a("ENGINE_ALREADY_INITIALIZED"));
    }
  }
  
  public static AEServerInfo[] getServers()
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.jdMethod_long();
  }
  
  public static AEServerInfo addServer(AEServerInfo paramAEServerInfo)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramAEServerInfo);
  }
  
  public static AEServerInfo updateServer(String paramString, AEServerInfo paramAEServerInfo)
    throws AEException
  {
    a(true);
    return AEInstance.jdField_if.a(paramString, paramAEServerInfo);
  }
  
  public static void removeServer(String paramString)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.h(paramString);
  }
  
  public static void setPrimaryServer(String paramString)
    throws AEException
  {
    a(true);
    AEInstance.jdField_if.j(paramString);
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.admin.AlertAdmin
 * JD-Core Version:    0.7.0.1
 */