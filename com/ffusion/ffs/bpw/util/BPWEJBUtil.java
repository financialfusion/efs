package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.adminEJB.BPWAdminHome;
import com.ffusion.ffs.bpw.adminEJB.IBPWAdmin;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BPWEJBUtil
{
  public static IBPWAdmin getAdminBean(PropertyConfig paramPropertyConfig)
    throws Exception
  {
    BPWAdminHome localBPWAdminHome = jdMethod_do(paramPropertyConfig);
    return createAdminBean(paramPropertyConfig, localBPWAdminHome);
  }
  
  public static BPWServices getServiceBean(PropertyConfig paramPropertyConfig)
    throws Exception
  {
    BPWServicesHome localBPWServicesHome = a(paramPropertyConfig);
    return createServiceBean(paramPropertyConfig, localBPWServicesHome);
  }
  
  private static BPWAdminHome jdMethod_do(PropertyConfig paramPropertyConfig)
    throws Exception
  {
    Context localContext = jdMethod_if(paramPropertyConfig);
    BPWAdminHome localBPWAdminHome = null;
    String str1 = paramPropertyConfig.BPWServ_jndiName;
    try
    {
      localBPWAdminHome = (BPWAdminHome)localContext.lookup(str1);
    }
    catch (NamingException localNamingException)
    {
      String str2 = "Unable to find the corresponding EJB. Pleace check that there is a deployed EJB with JNDI name " + str1 + " on the server.";
      FFSDebug.log(localNamingException, str2, 0);
      throw localNamingException;
    }
    return localBPWAdminHome;
  }
  
  private static BPWServicesHome a(PropertyConfig paramPropertyConfig)
    throws Exception
  {
    Context localContext = jdMethod_if(paramPropertyConfig);
    BPWServicesHome localBPWServicesHome = null;
    String str1 = paramPropertyConfig.BPWServ_jndiName;
    try
    {
      localBPWServicesHome = (BPWServicesHome)localContext.lookup(str1);
    }
    catch (NamingException localNamingException)
    {
      String str2 = "Unable to find the corresponding EJB. Pleace check that there is a deployed EJB with JNDI name " + str1 + " on the server.";
      FFSDebug.log(localNamingException, str2, 0);
      throw localNamingException;
    }
    return localBPWServicesHome;
  }
  
  private static Context jdMethod_if(PropertyConfig paramPropertyConfig)
    throws Exception
  {
    FFSProperties localFFSProperties = new FFSProperties();
    InitialContext localInitialContext = null;
    String str1 = paramPropertyConfig.getServerProtocol() + "://" + paramPropertyConfig.getServerHost() + ":" + paramPropertyConfig.getServerPort();
    String str2 = paramPropertyConfig.getServerUserName();
    String str3 = paramPropertyConfig.getServerPassword();
    String str4 = paramPropertyConfig.getNameContext();
    String str5 = paramPropertyConfig.serverName;
    localFFSProperties.put("serverName", str5);
    if ((str4 == null) || (str4.equals("")))
    {
      String str6 = "A failure occured when creating initial context factory: No initial context factory specified.";
      throw new Exception(str6);
    }
    try
    {
      localFFSProperties.put("java.naming.provider.url", str1);
      localFFSProperties.put("java.naming.factory.initial", str4);
      if ((str2 == null) || (str2.equals("")))
      {
        localFFSProperties.put("java.naming.security.principal", "");
        localFFSProperties.put("java.naming.security.credentials", "");
      }
      else
      {
        localFFSProperties.put("java.naming.security.principal", str2);
        if (str3 == null) {
          localFFSProperties.put("java.naming.security.credentials", "");
        } else {
          localFFSProperties.put("java.naming.security.credentials", str3);
        }
      }
      localInitialContext = new InitialContext(localFFSProperties);
    }
    catch (NamingException localNamingException)
    {
      String str7 = "Unable to connect to the server at " + str1;
      FFSDebug.log(localNamingException, str7, 0);
      throw localNamingException;
    }
    return localInitialContext;
  }
  
  public static IBPWAdmin createAdminBean(PropertyConfig paramPropertyConfig, BPWAdminHome paramBPWAdminHome)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = paramBPWAdminHome.create();
    }
    catch (CreateException localCreateException)
    {
      str1 = paramPropertyConfig.getServerProtocol() + "://" + paramPropertyConfig.getServerHost() + ":" + paramPropertyConfig.getServerPort();
      str2 = "An error occured while creating remote objects: Unable to create EJB on the server at " + str1;
      FFSDebug.log(localCreateException, str2, 0);
      throw localCreateException;
    }
    catch (RemoteException localRemoteException)
    {
      String str1 = paramPropertyConfig.getServerProtocol() + "://" + paramPropertyConfig.getServerHost() + ":" + paramPropertyConfig.getServerPort();
      String str2 = "An error occured while creating remote objects: Error while accessing the server at " + str1;
      FFSDebug.log(localRemoteException, str2, 0);
      throw localRemoteException;
    }
    return localIBPWAdmin;
  }
  
  public static BPWServices createServiceBean(PropertyConfig paramPropertyConfig, BPWServicesHome paramBPWServicesHome)
    throws Exception
  {
    BPWServices localBPWServices = null;
    try
    {
      localBPWServices = paramBPWServicesHome.create();
    }
    catch (CreateException localCreateException)
    {
      str1 = paramPropertyConfig.getServerProtocol() + "://" + paramPropertyConfig.getServerHost() + ":" + paramPropertyConfig.getServerPort();
      str2 = "An error occured while creating remote objects: Unable to create EJB on the server at " + str1;
      FFSDebug.log(localCreateException, str2, 0);
      throw localCreateException;
    }
    catch (RemoteException localRemoteException)
    {
      String str1 = paramPropertyConfig.getServerProtocol() + "://" + paramPropertyConfig.getServerHost() + ":" + paramPropertyConfig.getServerPort();
      String str2 = "An error occured while creating remote objects: Error while accessing the server at " + str1;
      FFSDebug.log(localRemoteException, str2, 0);
      throw localRemoteException;
    }
    return localBPWServices;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BPWEJBUtil
 * JD-Core Version:    0.7.0.1
 */