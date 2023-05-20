package com.ffusion.csil.ejb;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;

public abstract interface CSILEJBHome
  extends EJBHome
{
  public abstract ICSILEJB create()
    throws CreateException, RemoteException, EJBException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.ejb.CSILEJBHome
 * JD-Core Version:    0.7.0.1
 */