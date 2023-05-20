package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface OFX200BPWServicesHome
  extends EJBHome
{
  public abstract IOFX200BPWServices create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome
 * JD-Core Version:    0.7.0.1
 */