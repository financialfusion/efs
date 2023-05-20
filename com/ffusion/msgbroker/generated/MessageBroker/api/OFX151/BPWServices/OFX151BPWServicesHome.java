package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface OFX151BPWServicesHome
  extends EJBHome
{
  public abstract IOFX151BPWServices create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.BPWServices.OFX151BPWServicesHome
 * JD-Core Version:    0.7.0.1
 */