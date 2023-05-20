package com.ffusion.ffs.bpw.BPWServices;

import com.ibm.ejs.container.EJSDeployedSupport;
import java.rmi.RemoteException;

public class EJSRemoteStatelessBPWServices
  extends EJSRemoteStatelessBPWServices_e88b14a1
  implements BPWServices
{
  public EJSRemoteStatelessBPWServices()
    throws RemoteException
  {}
  
  public EJSDeployedSupport getDeployedSupport()
  {
    return new EJSDeployedSupport();
  }
  
  public void putDeployedSupport(EJSDeployedSupport paramEJSDeployedSupport) {}
}


/* Location:           D:\drops\jd\jars\Deployed_BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServices.EJSRemoteStatelessBPWServices
 * JD-Core Version:    0.7.0.1
 */