package com.ffusion.alert.clientEJB;

import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAuditInfo;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import java.rmi.RemoteException;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface IAEAlertClient
  extends EJBObject
{
  public abstract int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString1, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws RemoteException, EJBException, AEException;
  
  public abstract int createAlert(AEApplicationInfo paramAEApplicationInfo, String paramString, int paramInt1, int paramInt2, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws RemoteException, EJBException, AEException;
  
  public abstract void cancelAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition getAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getUserAlerts(AEApplicationInfo paramAEApplicationInfo, String paramString, boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getAppAlerts(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition[] getAppAlertsForChannelPaged(AEApplicationInfo paramAEApplicationInfo, boolean paramBoolean, String paramString, int paramInt1, int paramInt2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString1, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, String paramString2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAlertDefinition updateAlert(AEApplicationInfo paramAEApplicationInfo, int paramInt1, String paramString, int paramInt2, int paramInt3, AEScheduleInfo paramAEScheduleInfo, IAEDeliveryInfo[] paramArrayOfIAEDeliveryInfo, byte[] paramArrayOfByte)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEAuditInfo[] getUserAuditInfo(AEApplicationInfo paramAEApplicationInfo, String paramString, Date paramDate1, Date paramDate2)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo[] getInstalledDeliveryChannels(AEApplicationInfo paramAEApplicationInfo)
    throws RemoteException, EJBException, AEException;
  
  public abstract IAEChannelInfo[] getLoadedDeliveryChannels(AEApplicationInfo paramAEApplicationInfo)
    throws RemoteException, EJBException, AEException;
}


/* Location:           D:\drops\jd\jars\UAEClientEJB20.jar
 * Qualified Name:     com.ffusion.alert.clientEJB.IAEAlertClient
 * JD-Core Version:    0.7.0.1
 */