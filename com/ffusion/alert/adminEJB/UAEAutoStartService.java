package com.ffusion.alert.adminEJB;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class UAEAutoStartService
{
  private String _initialContextFactoryName = "com.sybase.ejb.InitialContextFactory";
  private static final String AUTO_START_ADMIN_JNDI_NAME = "adminJNDIName";
  
  public void start() {}
  
  public void run()
  {
    Properties localProperties = null;
    Object localObject;
    try
    {
      ClassLoader localClassLoader = UAEAutoStartService.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      localObject = localClassLoader.getResourceAsStream("AutoStartUAE.properties");
      if (localObject == null)
      {
        System.out.println("UAE AutoStart: Could not find AutoStartUAE.properties");
      }
      else
      {
        localProperties = new Properties();
        localProperties.load((InputStream)localObject);
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("UAE AutoStart: Error reading UAE autostart properties.");
      localThrowable.printStackTrace(System.out);
    }
    if (localProperties != null) {
      try
      {
        String str1 = (String)localProperties.get("serverURL");
        if (str1 == null)
        {
          System.out.println("UAE AutoStart: No serverURL specified, can not run UAE AutoStart.");
        }
        else
        {
          localObject = getInitialContext(str1);
          String str2 = (String)localProperties.get("adminJNDIName");
          if (str2 == null) {
            System.out.println("UAE AutoStart: No adminJNDIName specified, can not run UAE AutoStart.");
          } else {
            IAEAlertAdminHome localIAEAlertAdminHome = (IAEAlertAdminHome)((Context)localObject).lookup(str2);
          }
        }
      }
      catch (NamingException localNamingException)
      {
        localNamingException.printStackTrace();
      }
    }
  }
  
  public void stop() {}
  
  private Context getInitialContext(String paramString)
  {
    Properties localProperties = new Properties();
    InitialContext localInitialContext = null;
    localProperties.put("serverName", paramString);
    try
    {
      localProperties.put("java.naming.provider.url", paramString);
      localProperties.put("java.naming.factory.initial", this._initialContextFactoryName);
      localProperties.put("java.naming.security.principal", "");
      localProperties.put("java.naming.security.credentials", "");
      localInitialContext = new InitialContext(localProperties);
    }
    catch (NamingException localNamingException)
    {
      localNamingException.printStackTrace();
    }
    return localInitialContext;
  }
}


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.UAEAutoStartService
 * JD-Core Version:    0.7.0.1
 */