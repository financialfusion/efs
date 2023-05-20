package com.ffusion.alert.adminEJB;

import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.AEServerInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface IAEAlertAdmin
  extends EJBObject
{
  public abstract void destroyRepository(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException;
  
  public abstract void checkRepository(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException;
  
  public abstract void createRepository(AEDBParams paramAEDBParams, boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract void init(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException;
  
  public abstract void init(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void initServerAll(AEDBParams paramAEDBParams)
    throws RemoteException, EJBException, AEException;
  
  public abstract void start(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void startServerNamed(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void startServerAll()
    throws RemoteException, EJBException, AEException;
  
  public abstract void stop(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void stopServerAll()
    throws RemoteException, EJBException, AEException;
  
  public abstract void suspend(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void suspendServerAll()
    throws RemoteException, EJBException, AEException;
  
  public abstract void resume(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void resumeServerAll()
    throws RemoteException, EJBException, AEException;
  
  public abstract void shutdown(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void shutdownServerAll()
    throws RemoteException, EJBException, AEException;
  
  public abstract void shutdown()
    throws RemoteException, EJBException, AEException;
  
  public abstract void start()
    throws RemoteException, EJBException, AEException;
  
  public abstract void stop()
    throws RemoteException, EJBException, AEException;
  
  public abstract void suspend()
    throws RemoteException, EJBException, AEException;
  
  public abstract void resume()
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isInitialized()
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isSuspended()
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isRunning()
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isSuspended(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isInitialized(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isRunning(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isClusterStopped()
    throws RemoteException, EJBException, AEException;
  
  public abstract boolean isClusterRunning()
    throws RemoteException, EJBException, AEException;
  
  public abstract void addApplication(AEApplicationInfo paramAEApplicationInfo)
    throws RemoteException, EJBException, AEException;
  
  public abstract void removeApplication(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void updateApplication(String paramString, AEApplicationInfo paramAEApplicationInfo)
    throws RemoteException, EJBException, AEException;
  
  public abstract AEApplicationInfo[] getApplications()
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo installDeliveryChannel(String paramString1, String paramString2, Properties paramProperties, int paramInt, String paramString3)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo uninstallDeliveryChannel(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo loadDeliveryChannel(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo unloadDeliveryChannel(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo updateDeliveryChannel(IAEChannelInfo paramIAEChannelInfo)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo[] getInstalledDeliveryChannels(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo[] getLoadedDeliveryChannels(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getAllAlerts(boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getAppAlerts(String paramString, boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getAppAlertsForChannelPaged(String paramString1, boolean paramBoolean, String paramString2, int paramInt1, int paramInt2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getUserAlerts(String paramString1, String paramString2, boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getUserAlerts(String paramString, boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition getAlert(int paramInt)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getAlerts(int[] paramArrayOfInt)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition updateAlert(int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition updateAlert(int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws RemoteException, EJBException, AEException;
  
  public abstract void cancelAlert(int paramInt)
    throws RemoteException, EJBException, AEException;
  
  public abstract void cancelUserAlerts(String paramString1, String paramString2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAuditInfo[] getAuditInfo(Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAuditInfo[] getAppAuditInfo(String paramString, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAuditInfo[] getUserAuditInfo(String paramString1, String paramString2, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAuditInfo[] getUserAuditInfo(String paramString, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException;
  
  public abstract void setEngineProperties(Properties paramProperties)
    throws RemoteException, EJBException, AEException;
  
  public abstract Properties getEngineProperties()
    throws RemoteException, EJBException, AEException;
  
  public abstract AEServerInfo[] getServers()
    throws RemoteException, EJBException, AEException;
  
  public abstract AEServerInfo addServer(AEServerInfo paramAEServerInfo)
    throws RemoteException, EJBException, AEException;
  
  public abstract AEServerInfo updateServer(String paramString, AEServerInfo paramAEServerInfo)
    throws RemoteException, EJBException, AEException;
  
  public abstract void removeServer(String paramString)
    throws RemoteException, EJBException, AEException;
  
  public abstract void setPrimaryServer(String paramString)
    throws RemoteException, EJBException, AEException;
}


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.IAEAlertAdmin
 * JD-Core Version:    0.7.0.1
 */