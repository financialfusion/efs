package com.ffusion.csil.utilejb;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;

public abstract interface UtilEJBHome
  extends EJBHome
{
  public abstract IUtilEJB create()
    throws CreateException, RemoteException, EJBException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.utilejb.UtilEJBHome
 * JD-Core Version:    0.7.0.1
 */