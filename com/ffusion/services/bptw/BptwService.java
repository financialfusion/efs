package com.ffusion.services.bptw;

import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome;
import com.ffusion.services.InitFileHandler;
import com.microstar.xml.HandlerBase;
import java.io.PrintStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;

public class BptwService
  implements Serializable
{
  public static final int ERROR_UNKNOWN = 1;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7;
  public static final int ERROR_INVALID_INIT_FILE = 8;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_BPTW_NOT_AVAILABLE = 30000;
  public static final int ERROR_BPTW_INIT_FILE_NOT_VALID = 30001;
  private static final String jdField_new = "CallBackBeanURL";
  private static final String jdField_if = "ContextFactory";
  private static final String jdField_for = "CallBackJNDIName";
  private static final String jdField_int = "ContextUserName";
  private static final String jdField_do = "ContextPassword";
  protected String providerUrl = "t3://localhost:7001";
  protected String contextFactory = "weblogic.jndi.WLInitialContextFactory";
  protected String jndiName = "OFX200.OFX200BPWServices";
  protected String contextUsername = "system";
  protected String contextPassword = "weblogic";
  private IOFX200BPWServices a = null;
  
  public int initialize(String paramString)
  {
    a locala = new a();
    int i = 0;
    try
    {
      InitFileHandler localInitFileHandler = new InitFileHandler();
      i = localInitFileHandler.initialize(paramString, locala);
      if (i == 0) {
        i = a();
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
      i = 30001;
    }
    return i;
  }
  
  private int a()
  {
    Context localContext = null;
    try
    {
      localContext = a(this.providerUrl, this.contextUsername, this.contextPassword);
      Object localObject = localContext.lookup(this.jndiName);
      OFX200BPWServicesHome localOFX200BPWServicesHome = (OFX200BPWServicesHome)PortableRemoteObject.narrow(localObject, OFX200BPWServicesHome.class);
      this.a = localOFX200BPWServicesHome.create();
    }
    catch (TransactionRolledbackException localTransactionRolledbackException)
    {
      System.out.println("Unexpected TransactionRolledbackException: " + localTransactionRolledbackException.toString());
    }
    catch (RemoteException localRemoteException)
    {
      System.out.println("RMI RemoteException: " + localRemoteException.toString());
    }
    catch (CreateException localCreateException)
    {
      System.out.println("EJB CreateException: " + localCreateException.toString());
    }
    catch (RemoveException localRemoveException)
    {
      System.out.println("EJB RemoteException: " + localRemoveException.toString());
    }
    catch (ObjectNotFoundException localObjectNotFoundException)
    {
      System.out.println("EJB ObjecNotFound Exception: " + localObjectNotFoundException.toString());
    }
    catch (NamingException localNamingException)
    {
      System.out.println("NamingException: " + localNamingException.toString());
    }
    catch (Throwable localThrowable)
    {
      System.out.println("UNKNOWN: " + localThrowable.toString());
    }
    if (this.a == null) {
      return 30000;
    }
    return 0;
  }
  
  private Context a(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    Properties localProperties = new Properties();
    localProperties.put("java.naming.factory.initial", this.contextFactory);
    localProperties.put("java.naming.provider.url", paramString1);
    localProperties.put("java.naming.security.principal", paramString2);
    localProperties.put("java.naming.security.credentials", paramString3);
    return new InitialContext(localProperties);
  }
  
  public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
  {
    try
    {
      if (this.a != null) {
        return this.a.addCustomers(paramArrayOfCustomerInfo);
      }
      return 30000;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return 1;
  }
  
  public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo)
  {
    try
    {
      if (this.a != null) {
        return this.a.modifyCustomers(paramArrayOfCustomerInfo);
      }
      return 30000;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return 1;
  }
  
  public int deleteCustomers(String[] paramArrayOfString)
  {
    try
    {
      if (this.a != null) {
        return this.a.deleteCustomers(paramArrayOfString);
      }
      return 30000;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return 1;
  }
  
  public int deactivateCustomers(String[] paramArrayOfString)
  {
    try
    {
      if (this.a != null) {
        return this.a.deactivateCustomers(paramArrayOfString);
      }
      return 30000;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return 1;
  }
  
  public int activateCustomers(String[] paramArrayOfString)
  {
    try
    {
      if (this.a != null) {
        return this.a.activateCustomers(paramArrayOfString);
      }
      return 30000;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return 1;
  }
  
  public CustomerInfo[] getCustomersInfo(String[] paramArrayOfString)
  {
    try
    {
      if (this.a != null) {
        return this.a.getCustomersInfo(paramArrayOfString);
      }
      return null;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public PayeeInfo[] getLinkedPayees()
  {
    try
    {
      if (this.a != null) {
        return this.a.getLinkedPayees();
      }
      return null;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public PayeeInfo[] getMostUsedPayees(int paramInt)
  {
    try
    {
      if (this.a != null) {
        return this.a.getMostUsedPayees(paramInt);
      }
      return null;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public PayeeInfo[] getPreferredPayees(String paramString)
  {
    try
    {
      if (this.a != null) {
        return this.a.getPreferredPayees(paramString);
      }
      return null;
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public class a
    extends HandlerBase
  {
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("CallBackBeanURL")) {
        BptwService.this.providerUrl = paramString2;
      } else if (paramString1.equals("ContextFactory")) {
        BptwService.this.contextFactory = paramString2;
      } else if (paramString1.equals("CallBackJNDIName")) {
        BptwService.this.jndiName = paramString2;
      } else if (paramString1.equals("ContextUserName")) {
        BptwService.this.contextUsername = paramString2;
      } else if (paramString1.equals("ContextPassword")) {
        BptwService.this.contextPassword = paramString2;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.BptwService
 * JD-Core Version:    0.7.0.1
 */