package com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback;

import com.ffusion.ffs.interfaces.FFSException;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface OFX151CallbackHome
  extends EJBHome
{
  public abstract IOFX151Callback create()
    throws CreateException, RemoteException, FFSException;
}


/* Location:           D:\drops\jd\jars\OFX151Callback.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackHome
 * JD-Core Version:    0.7.0.1
 */