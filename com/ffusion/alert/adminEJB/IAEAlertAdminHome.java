package com.ffusion.alert.adminEJB;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface IAEAlertAdminHome
  extends EJBHome
{
  public abstract IAEAlertAdmin create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.IAEAlertAdminHome
 * JD-Core Version:    0.7.0.1
 */