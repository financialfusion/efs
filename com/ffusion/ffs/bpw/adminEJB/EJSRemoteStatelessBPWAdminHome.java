package com.ffusion.ffs.bpw.adminEJB;

import com.ibm.ejs.container.EJSDeployedSupport;
import java.rmi.RemoteException;

public class EJSRemoteStatelessBPWAdminHome
  extends EJSRemoteStatelessBPWAdminHome_06cdc419
  implements BPWAdminHome
{
  public EJSRemoteStatelessBPWAdminHome()
    throws RemoteException
  {}
  
  public EJSDeployedSupport getDeployedSupport()
  {
    return new EJSDeployedSupport();
  }
  
  public void putDeployedSupport(EJSDeployedSupport paramEJSDeployedSupport) {}
}


/* Location:           D:\drops\jd\jars\Deployed_BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.EJSRemoteStatelessBPWAdminHome
 * JD-Core Version:    0.7.0.1
 */