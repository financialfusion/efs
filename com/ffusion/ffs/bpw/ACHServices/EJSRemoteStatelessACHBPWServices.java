package com.ffusion.ffs.bpw.ACHServices;

import com.ibm.ejs.container.EJSDeployedSupport;
import java.rmi.RemoteException;

public class EJSRemoteStatelessACHBPWServices
  extends EJSRemoteStatelessACHBPWServices_e0156d61
  implements ACHBPWServices
{
  public EJSRemoteStatelessACHBPWServices()
    throws RemoteException
  {}
  
  public EJSDeployedSupport getDeployedSupport()
  {
    return new EJSDeployedSupport();
  }
  
  public void putDeployedSupport(EJSDeployedSupport paramEJSDeployedSupport) {}
}


/* Location:           D:\drops\jd\jars\Deployed_ACHBPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.EJSRemoteStatelessACHBPWServices
 * JD-Core Version:    0.7.0.1
 */