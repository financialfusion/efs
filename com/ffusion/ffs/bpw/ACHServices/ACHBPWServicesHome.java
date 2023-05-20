package com.ffusion.ffs.bpw.ACHServices;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public abstract interface ACHBPWServicesHome
  extends EJBHome
{
  public abstract ACHBPWServices create()
    throws CreateException, RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.ACHBPWServicesHome
 * JD-Core Version:    0.7.0.1
 */