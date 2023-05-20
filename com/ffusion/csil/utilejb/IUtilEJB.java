package com.ffusion.csil.utilejb;

import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.ffs.interfaces.FFSException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface IUtilEJB
  extends EJBObject
{
  public abstract void createApprovalItem(int paramInt1, int paramInt2, Serializable paramSerializable, Limits paramLimits, HashMap paramHashMap)
    throws FFSException, EJBException, RemoteException;
  
  public abstract void removeApprovalItem(int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws FFSException, EJBException, RemoteException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.utilejb.IUtilEJB
 * JD-Core Version:    0.7.0.1
 */