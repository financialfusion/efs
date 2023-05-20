package com.ffusion.services.bptw;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices;
import com.ffusion.services.IBPTW;
import com.ffusion.services.banksim2.Base;
import com.ffusion.services.banksim2.Base.a;
import com.ffusion.util.ContextPool;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class BPTW
  extends Base
  implements Serializable, IBPTW
{
  public static final int ERROR_UNKNOWN = 1;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7;
  public static final int ERROR_INVALID_INIT_FILE = 8;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_BPTW_NOT_AVAILABLE = 30000;
  public static final int ERROR_BPTW_INIT_FILE_NOT_VALID = 30001;
  private static final String g = "CallBackJNDIName";
  private static final String n = "AdminJNDIName";
  private String j = "BPWServices";
  private String f = "bpw.BPWAdminHome";
  private static final String m = "CallBackBeanURL";
  private static final String h = "ContextFactory";
  private static final String k = "CallBackJNDIName";
  private static final String l = "ContextUserName";
  private static final String i = "ContextPassword";
  protected String providerUrl = "t3://localhost:7001";
  protected String contextFactory = "weblogic.jndi.WLInitialContextFactory";
  protected String jndiName = "OFX200.OFX200BPWServices";
  protected String contextUsername = "system";
  protected String contextPassword = "weblogic";
  private IOFX200BPWServices e = null;
  
  public int initialize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "bptw.xml";
    }
    int i1 = initialize(paramString, new a());
    return i1;
  }
  
  public void processOFXRequest() {}
  
  public void addCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws BPTWException
  {
    String str1 = "BPTW.addCustomers";
    BPWServices localBPWServices = getBPWHandler();
    String str2 = "";
    try
    {
      int i1 = localBPWServices.addCustomers(paramArrayOfCustomerInfo);
      if (i1 != 1) {
        throw new BPTWException(25019);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("BPTW.addCustomers exception: " + localThrowable.getMessage());
      String str3 = localThrowable.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new BPTWException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void modifyCustomers(SecureUser paramSecureUser, CustomerInfo[] paramArrayOfCustomerInfo, HashMap paramHashMap)
    throws BPTWException
  {
    String str1 = "BPTW.modifyCustomers";
    BPWServices localBPWServices = getBPWHandler();
    String str2 = "";
    try
    {
      int i1 = localBPWServices.modifyCustomers(paramArrayOfCustomerInfo);
      if (i1 != 1) {
        throw new BPTWException(25023);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("BPTW.modifyCustomers exception: " + localThrowable.getMessage());
      String str3 = localThrowable.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new BPTWException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void deleteCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException
  {
    String str1 = "BPTW.deleteCustomers";
    BPWServices localBPWServices = getBPWHandler();
    String str2 = "";
    try
    {
      localBPWServices.deleteCustomers(paramArrayOfString);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("BPTW.deleteCustomers exception: " + localThrowable.getMessage());
      String str3 = localThrowable.toString();
      if ((str3 != null) && (str3.indexOf("Server is not running!") > 0)) {
        throw new BPTWException(25018);
      }
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void deactivateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      if (localBPWServices != null) {
        localBPWServices.deactivateCustomers(paramArrayOfString);
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("BPTW.deactivateCustomers exception: " + localRemoteException.getMessage());
      throw new BPTWException(25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("BPTW.deactivateCustomers exception: " + localException.getMessage());
      throw new BPTWException(25026, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public void activateCustomers(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException
  {
    BPWServices localBPWServices = getBPWHandler();
    try
    {
      if (localBPWServices != null) {
        localBPWServices.activateCustomers(paramArrayOfString);
      }
    }
    catch (RemoteException localRemoteException)
    {
      DebugLog.log("BPTW.activateCustomers exception: " + localRemoteException.getMessage());
      throw new BPTWException(25018, localRemoteException);
    }
    catch (Exception localException)
    {
      DebugLog.log("BPTW.activateCustomers exception: " + localException.getMessage());
      throw new BPTWException(25025, localException);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
  }
  
  public CustomerInfo[] getCustomersInfo(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws BPTWException
  {
    throw new BPTWException(3200);
  }
  
  public PayeeInfo[] getLinkedPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BPTWException
  {
    throw new BPTWException(3200);
  }
  
  public PayeeInfo[] getMostUsedPayees(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BPTWException
  {
    throw new BPTWException(3200);
  }
  
  public PayeeInfo[] getPreferredPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws BPTWException
  {
    throw new BPTWException(3200);
  }
  
  protected BPWServices getBPWHandler()
    throws BPTWException
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i1 = 0; i1 < this.provider_url_list.size(); i1++)
    {
      String str = (String)this.provider_url_list.get(i1);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Ping BPW Server ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        if (!ping(str)) {
          continue;
        }
      }
      catch (CSILException localCSILException)
      {
        continue;
      }
      try
      {
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.j);
        localObject2 = (BPWServicesHome)PortableRemoteObject.narrow(localObject1, BPWServicesHome.class);
        localBPWServices = ((BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.j);
            BPWServicesHome localBPWServicesHome = (BPWServicesHome)PortableRemoteObject.narrow(localObject2, BPWServicesHome.class);
            localBPWServices = localBPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localBPWServices;
          }
          catch (Exception localException)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    throw new BPTWException(25018);
  }
  
  protected void removeBPWHandler(BPWServices paramBPWServices)
  {
    if (paramBPWServices != null) {
      try
      {
        paramBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  public class a
    extends Base.a
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("CallBackJNDIName")) {
        BPTW.this.j = paramString2;
      } else if (paramString1.equals("AdminJNDIName")) {
        BPTW.this.f = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.BPTW
 * JD-Core Version:    0.7.0.1
 */