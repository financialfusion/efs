package com.ffusion.alert.adminEJB;

import com.ffusion.alert.admin.AlertAdmin;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.AEServerInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import java.io.InputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AEAlertAdminBean
  implements SessionBean
{
  public static final String AUTOSTART_PROPS_FILE = "AutoStartUAE.properties";
  public static final String AUTOSTART_PROP_STATE = "startState";
  public static final String AUTOSTART_PROP_URL = "serverURL";
  public static final String AUTOSTART_STATE_STOP = "stopped";
  public static final String AUTOSTART_STATE_INIT = "initialized";
  public static final String AUTOSTART_STATE_START = "started";
  private SessionContext _ctx = null;
  
  public void setSessionContext(SessionContext paramSessionContext)
    throws RemoteException, EJBException
  {
    this._ctx = paramSessionContext;
  }
  
  public void ejbRemove()
    throws RemoteException, EJBException
  {}
  
  public void ejbPassivate()
    throws RemoteException, EJBException
  {}
  
  public void ejbActivate()
    throws RemoteException, EJBException
  {}
  
  public void ejbCreate()
    throws RemoteException, CreateException
  {}
  
  public void destroyRepository(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.destroyRepository(paramAEDBParams);
  }
  
  public void checkRepository(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.checkRepository(paramAEDBParams);
  }
  
  public void createRepository(AEDBParams paramAEDBParams, boolean paramBoolean)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.createRepository(paramAEDBParams, paramBoolean);
  }
  
  public void init(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.init(paramAEDBParams);
  }
  
  public void init(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.init(paramString);
  }
  
  public void initServerAll(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.initServerAll(paramAEDBParams);
  }
  
  public void start(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.start(paramString);
  }
  
  public void startServerNamed(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.startServerNamed(paramString);
  }
  
  public void startServerAll()
    throws RemoteException, EJBException, AEException
  {}
  
  public void stop(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.stop(paramString);
  }
  
  public void stopServerAll()
    throws RemoteException, EJBException, AEException
  {}
  
  public void suspend(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.suspend(paramString);
  }
  
  public void suspendServerAll()
    throws RemoteException, EJBException, AEException
  {}
  
  public void resume(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.resume(paramString);
  }
  
  public void resumeServerAll()
    throws RemoteException, EJBException, AEException
  {}
  
  public void shutdown(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.shutdown(paramString);
  }
  
  public void shutdownServerAll()
    throws RemoteException, EJBException, AEException
  {}
  
  public void shutdown()
    throws RemoteException, EJBException, AEException
  {}
  
  public void start()
    throws RemoteException, EJBException, AEException
  {}
  
  public void suspend()
    throws RemoteException, EJBException, AEException
  {}
  
  public void stop()
    throws RemoteException, EJBException, AEException
  {}
  
  public void resume()
    throws RemoteException, EJBException, AEException
  {}
  
  public boolean isSuspended()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isSuspended();
  }
  
  public boolean isInitialized()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isInitialized();
  }
  
  public boolean isRunning()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isRunning();
  }
  
  public boolean isSuspended(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isSuspended(paramString);
  }
  
  public boolean isInitialized(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isInitialized(paramString);
  }
  
  public boolean isRunning(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isRunning(paramString);
  }
  
  public boolean isClusterStopped()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isClusterStopped();
  }
  
  public boolean isClusterRunning()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.isClusterRunning();
  }
  
  public void addApplication(AEApplicationInfo paramAEApplicationInfo)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.addApplication(paramAEApplicationInfo);
  }
  
  public void removeApplication(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.removeApplication(paramString);
  }
  
  public void updateApplication(String paramString, AEApplicationInfo paramAEApplicationInfo)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.updateApplication(paramString, paramAEApplicationInfo);
  }
  
  public AEApplicationInfo[] getApplications()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getApplications();
  }
  
  public IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.installDeliveryChannel(paramString1, paramString2, paramProperties, paramInt);
  }
  
  public IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt, String paramString3)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.installDeliveryChannel(paramString1, paramString2, paramProperties, paramInt, paramString3);
  }
  
  public IAEChannelInfo uninstallDeliveryChannel(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.uninstallDeliveryChannel(paramString);
  }
  
  public IAEChannelInfo loadDeliveryChannel(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.loadDeliveryChannel(paramString);
  }
  
  public IAEChannelInfo unloadDeliveryChannel(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.unloadDeliveryChannel(paramString);
  }
  
  public IAEChannelInfo updateDeliveryChannel(IAEChannelInfo paramIAEChannelInfo)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.updateDeliveryChannel(paramIAEChannelInfo);
  }
  
  public IAEChannelInfo[] getInstalledDeliveryChannels(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getInstalledDeliveryChannels(paramString);
  }
  
  public IAEChannelInfo[] getLoadedDeliveryChannels(String paramString)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getLoadedDeliveryChannels(paramString);
  }
  
  public IAEAlertDefinition[] getAllAlerts(boolean paramBoolean)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAllAlerts(paramBoolean);
  }
  
  public IAEAlertDefinition[] getAppAlerts(String paramString, boolean paramBoolean)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAppAlerts(paramString, paramBoolean);
  }
  
  public IAEAlertDefinition[] getAppAlertsForChannelPaged(String paramString1, boolean paramBoolean, String paramString2, int paramInt1, int paramInt2)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAppAlertsForChannelPaged(paramString1, paramBoolean, paramString2, paramInt1, paramInt2);
  }
  
  public IAEAlertDefinition[] getUserAlerts(String paramString1, String paramString2, boolean paramBoolean)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getUserAlerts(paramString1, paramString2, paramBoolean);
  }
  
  public IAEAlertDefinition[] getUserAlerts(String paramString, boolean paramBoolean)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getUserAlerts(paramString, paramBoolean);
  }
  
  public IAEAlertDefinition getAlert(int paramInt)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAlert(paramInt);
  }
  
  public IAEAlertDefinition[] getAlerts(int[] paramArrayOfInt)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAlerts(paramArrayOfInt);
  }
  
  public IAEAlertDefinition updateAlert(int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.updateAlert(paramInt1, paramString1, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramString2);
  }
  
  public IAEAlertDefinition updateAlert(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.updateAlert(paramInt1, paramString, paramInt2, paramInt3, paramAEScheduleInfo, paramArrayOfIAEDeliveryInfo, paramArrayOfByte);
  }
  
  public void cancelAlert(int paramInt)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.cancelAlert(paramInt);
  }
  
  public void cancelUserAlerts(String paramString1, String paramString2)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.cancelUserAlerts(paramString1, paramString2);
  }
  
  public IAEAuditInfo[] getAuditInfo(Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAuditInfo(paramDate1, paramDate2);
  }
  
  public IAEAuditInfo[] getAppAuditInfo(String paramString, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getAppAuditInfo(paramString, paramDate1, paramDate2);
  }
  
  public IAEAuditInfo[] getUserAuditInfo(String paramString1, String paramString2, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getUserAuditInfo(paramString1, paramString2, paramDate1, paramDate2);
  }
  
  public IAEAuditInfo[] getUserAuditInfo(String paramString, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getUserAuditInfo(paramString, paramDate1, paramDate2);
  }
  
  public void setEngineProperties(Properties paramProperties)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.setEngineProperties(paramProperties);
  }
  
  public Properties getEngineProperties()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getEngineProperties();
  }
  
  public AEServerInfo[] getServers()
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.getServers();
  }
  
  public AEServerInfo addServer(AEServerInfo paramAEServerInfo)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.addServer(paramAEServerInfo);
  }
  
  public AEServerInfo updateServer(String paramString, AEServerInfo paramAEServerInfo)
    throws RemoteException, EJBException, AEException
  {
    return AlertAdmin.updateServer(paramString, paramAEServerInfo);
  }
  
  public void removeServer(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.removeServer(paramString);
  }
  
  public void setPrimaryServer(String paramString)
    throws RemoteException, EJBException, AEException
  {
    AlertAdmin.setPrimaryServer(paramString);
  }
  
  static
  {
    Properties localProperties = null;
    try
    {
      ClassLoader localClassLoader = AEAlertAdminBean.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      InputStream localInputStream = localClassLoader.getResourceAsStream("AutoStartUAE.properties");
      if (localInputStream == null)
      {
        System.out.println("UAE AutoStart: Could not find AutoStartUAE.properties");
      }
      else
      {
        localProperties = new Properties();
        localProperties.load(localInputStream);
      }
    }
    catch (Throwable localThrowable1)
    {
      System.out.println("UAE AutoStart: Error reading UAE autostart properties.");
      localThrowable1.printStackTrace(System.out);
    }
    if (localProperties != null)
    {
      String str1 = localProperties.getProperty("startState", "stopped");
      System.out.println("UAE AutoStart: initial state set to: " + str1);
      if (!str1.equalsIgnoreCase("stopped"))
      {
        int i = 0;
        try
        {
          AEDBParams localAEDBParams = AEDBParams.createFromProperties(localProperties);
          AlertAdmin.init(localAEDBParams);
          System.out.println("UAE AutoStart: server initialized.");
          i = 1;
        }
        catch (Throwable localThrowable2)
        {
          System.out.println("UAE AutoStart: Error initializing server.");
          localThrowable2.printStackTrace(System.out);
        }
        if (i != 0) {
          try
          {
            String str2 = null;
            if (str1.equalsIgnoreCase("started"))
            {
              str2 = localProperties.getProperty("serverURL");
              if (str2 == null)
              {
                System.out.println("UAE AutoStart: No serverURL specified, starting in non-clustered mode.");
                AlertAdmin.start();
              }
              else
              {
                System.out.println("UAE AutoStart: Starting server " + str2);
                AlertAdmin.start(str2);
              }
            }
            System.out.println("UAE AutoStart: server " + (str2 == null ? "" : new StringBuffer().append(str2).append(" ").toString()) + "started.");
          }
          catch (Throwable localThrowable3)
          {
            System.out.println("UAE AutoStart: Error starting server.");
            localThrowable3.printStackTrace(System.out);
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.AEAlertAdminBean
 * JD-Core Version:    0.7.0.1
 */