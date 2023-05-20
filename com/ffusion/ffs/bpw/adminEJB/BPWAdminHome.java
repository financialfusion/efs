package com.ffusion.ffs.bpw.adminEJB;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface BPWAdminHome
  extends EJBHome
{
  public abstract IBPWAdmin create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.BPWAdminHome
 * JD-Core Version:    0.7.0.1
 */