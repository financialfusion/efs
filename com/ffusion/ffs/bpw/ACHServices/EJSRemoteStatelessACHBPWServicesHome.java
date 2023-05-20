package com.ffusion.ffs.bpw.ACHServices;

import com.ibm.ejs.container.EJSDeployedSupport;
import java.rmi.RemoteException;

public class EJSRemoteStatelessACHBPWServicesHome
  extends EJSRemoteStatelessACHBPWServicesHome_e0156d61
  implements ACHBPWServicesHome
{
  public EJSRemoteStatelessACHBPWServicesHome()
    throws RemoteException
  {}
  
  public EJSDeployedSupport getDeployedSupport()
  {
    return new EJSDeployedSupport();
  }
  
  public void putDeployedSupport(EJSDeployedSupport paramEJSDeployedSupport) {}
}


/* Location:           D:\drops\jd\jars\Deployed_ACHBPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.EJSRemoteStatelessACHBPWServicesHome
 * JD-Core Version:    0.7.0.1
 */