package com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback;

import com.ffusion.ffs.interfaces.FFSException;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface OFX200CallbackHome
  extends EJBHome
{
  public abstract IOFX200Callback create()
    throws CreateException, RemoteException, FFSException;
}


/* Location:           D:\drops\jd\jars\OFX200Callback.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackHome
 * JD-Core Version:    0.7.0.1
 */