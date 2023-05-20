package com.ffusion.services.bptw;

import com.ffusion.ffs.bpw.adminEJB.BPWAdminHome;
import com.ffusion.ffs.bpw.adminEJB.IBPWAdmin;
import com.ffusion.ffs.bpw.db.InstructionActivityLog;
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
import javax.transaction.TransactionRolledbackException;

public class Log
  implements Serializable
{
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7;
  public static final int ERROR_INVALID_INIT_FILE = 8;
  private static final String jdField_do = "CallBackBeanURL";
  private static final String jdField_if = "ContextFactory";
  private static final String a = "AdminJNDIName";
  private static final String jdField_for = "ContextUserName";
  private static final String jdField_try = "ContextPassword";
  protected String providerUrl = "t3://localhost:7001";
  protected String contextFactory = "weblogic.jndi.WLInitialContextFactory";
  protected String jndiName = "bpw.BPWAdminHome";
  protected String contextUsername = "system";
  protected String contextPassword = "weblogic";
  private BPWAdminHome jdField_new = null;
  private IBPWAdmin jdField_int = null;
  
  public int initialize(String paramString)
  {
    a locala = new a();
    int i = 0;
    try
    {
      InitFileHandler localInitFileHandler = new InitFileHandler();
      i = localInitFileHandler.initialize(paramString, locala);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
      i = 8;
    }
    if (i == 0) {
      a();
    }
    return i;
  }
  
  private void a()
  {
    Context localContext = null;
    try
    {
      localContext = a(this.providerUrl, this.contextUsername, this.contextPassword);
      this.jdField_new = ((BPWAdminHome)localContext.lookup(this.jndiName));
      this.jdField_int = this.jdField_new.create();
      if (this.jdField_int == null) {
        System.out.println("admin bean is null");
      }
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
  
  public InstructionActivityLog[] getLogInfo(String paramString1, String paramString2)
  {
    InstructionActivityLog[] arrayOfInstructionActivityLog = null;
    return arrayOfInstructionActivityLog;
  }
  
  public class a
    extends HandlerBase
  {
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("CallBackBeanURL")) {
        Log.this.providerUrl = paramString2;
      } else if (paramString1.equals("ContextFactory")) {
        Log.this.contextFactory = paramString2;
      } else if (paramString1.equals("AdminJNDIName")) {
        Log.this.jndiName = paramString2;
      } else if (paramString1.equals("ContextUserName")) {
        Log.this.contextUsername = paramString2;
      } else if (paramString1.equals("ContextPassword")) {
        Log.this.contextPassword = paramString2;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.Log
 * JD-Core Version:    0.7.0.1
 */