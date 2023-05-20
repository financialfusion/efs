package com.ffusion.ffs.bpw.adminEJB;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BPWAutoStartService
{
  private String _initialContextFactoryName = "com.sybase.ejb.InitialContextFactory";
  private static final String AUTOSTART_PROP_URL = "serverURL";
  private static final String AUTO_START_ADMIN_JNDI_NAME = "adminJNDIName";
  private String AUTOSTART_PROPS_FILE = "AutoStartBPW_locale.properties";
  
  public void start() {}
  
  public void run()
  {
    Properties localProperties = null;
    Object localObject;
    try
    {
      ClassLoader localClassLoader = BPWAutoStartService.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      localObject = localClassLoader.getResourceAsStream(this.AUTOSTART_PROPS_FILE);
      if (localObject == null)
      {
        System.out.println("BPW AutoStart: Could not find " + this.AUTOSTART_PROPS_FILE + " in classpath.");
      }
      else
      {
        localProperties = new Properties();
        localProperties.load((InputStream)localObject);
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("BPW AutoStart: Error reading BPW autostart properties.");
      localThrowable.printStackTrace(System.out);
    }
    try
    {
      String str1 = (String)localProperties.get("serverURL");
      if (str1 == null)
      {
        System.out.println("BPW AutoStart: No serverURL specified, can not run BPW AutoStart.");
      }
      else
      {
        localObject = getInitialContext(str1);
        String str2 = (String)localProperties.get("adminJNDIName");
        if (str2 == null)
        {
          System.out.println("BPW AutoStart: No adminJNDIName specified, can not run BPW AutoStart.");
        }
        else
        {
          System.out.println("EAServer Service Component running");
          BPWAdminHome localBPWAdminHome = (BPWAdminHome)((Context)localObject).lookup(str2);
          IBPWAdmin localIBPWAdmin = localBPWAdminHome.create();
          localIBPWAdmin.ping();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void stop() {}
  
  public static void main(String[] paramArrayOfString)
  {
    BPWAutoStartService localBPWAutoStartService = new BPWAutoStartService();
    localBPWAutoStartService.run();
  }
  
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


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.BPWAutoStartService
 * JD-Core Version:    0.7.0.1
 */