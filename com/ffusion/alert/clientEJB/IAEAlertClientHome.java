package com.ffusion.alert.clientEJB;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface IAEAlertClientHome
  extends EJBHome
{
  public abstract IAEAlertClient create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\UAEClientEJB20.jar
 * Qualified Name:     com.ffusion.alert.clientEJB.IAEAlertClientHome
 * JD-Core Version:    0.7.0.1
 */