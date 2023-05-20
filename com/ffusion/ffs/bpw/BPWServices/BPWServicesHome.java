package com.ffusion.ffs.bpw.BPWServices;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface BPWServicesHome
  extends EJBHome
{
  public abstract BPWServices create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.BPWServicesHome
 * JD-Core Version:    0.7.0.1
 */