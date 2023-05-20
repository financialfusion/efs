package com.ffusion.alert.plugins.filemonitor;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.plugins.AlertUtil;
import com.ffusion.alert.plugins.CreateAlertThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.CSILEJBHome;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.HfnEncrypt;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FileMonitorCleanupChannel
  implements IAEChannel
{
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_do;
  private static final String jdField_for = "FileMonitorCleanup";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_do = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing FileMonitorCleanupChannel plugin start", this.jdField_do, this.a);
    this.jdField_if = paramProperties;
    Properties localProperties = new Properties();
    localProperties.setProperty("age.days", paramProperties.getProperty("age.days", "7"));
    String str = paramProperties.getProperty("file.type");
    if (str == null) {
      str = new String("ALL");
    }
    localProperties.setProperty("file.type", str);
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, localProperties);
    localCreateAlertThread.start();
    AlertUtil.logEntry("Initializing FileMonitorCleanupChannel plugin end", this.jdField_do, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing FileMonitorCleanUp Alert...", this.jdField_do, this.a);
    String str1 = paramProperties.getProperty("age.days");
    AlertUtil.logEntry("Processing FileMonitorCleanUp Alert. Entry age=" + str1, this.jdField_do, this.a);
    if ((str1 == null) || (str1.length() <= 0))
    {
      AlertUtil.logEntry("No file age specified. Alert processing failed.", this.jdField_do, this.a);
      return -2000;
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(str1);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      AlertUtil.logEntry("Invalid file age setting: A positive number representing the number of days required. Alert processing failed.", this.jdField_do, this.a);
      return -2000;
    }
    if (i < 0)
    {
      AlertUtil.logEntry("The file age can not be a negative number", this.jdField_do, this.a);
      return -2000;
    }
    String str2 = paramProperties.getProperty("file.type");
    if (str2 == null) {
      str2 = "ALL";
    }
    ICSILEJB localICSILEJB = null;
    try
    {
      localICSILEJB = a();
    }
    catch (NamingException localNamingException)
    {
      if (this.jdField_do) {
        localNamingException.printStackTrace(this.a);
      }
      return -2000;
    }
    catch (CreateException localCreateException)
    {
      if (this.jdField_do) {
        localCreateException.printStackTrace(this.a);
      }
      return -2000;
    }
    catch (EJBException localEJBException1)
    {
      if (this.jdField_do) {
        localEJBException1.printStackTrace(this.a);
      }
      return -1000;
    }
    catch (RemoteException localRemoteException1)
    {
      if (this.jdField_do) {
        localRemoteException1.printStackTrace(this.a);
      }
      return -1001;
    }
    catch (CSILException localCSILException1)
    {
      if (this.jdField_do)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException1.getCode(), this.jdField_do, this.a);
        localCSILException1.printStackTrace(this.a);
      }
      return -1004;
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localICSILEJB.cleanupFM(str2, i, localHashMap);
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int j;
      AlertUtil.logEntry("Processing  FileMonitorCleanUpAlert end", this.jdField_do, this.a);
    }
    catch (EJBException localEJBException2)
    {
      if (this.jdField_do) {
        localEJBException2.printStackTrace(this.a);
      }
      j = -1000;
      return j;
    }
    catch (RemoteException localRemoteException2)
    {
      if (this.jdField_do) {
        localRemoteException2.printStackTrace(this.a);
      }
      j = -1001;
      return j;
    }
    catch (CSILException localCSILException2)
    {
      if (this.jdField_do)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException2.getCode(), this.jdField_do, this.a);
        localCSILException2.printStackTrace(this.a);
      }
      j = -1004;
      return j;
    }
    finally
    {
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException5) {}
    }
    return 0;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping FileMonitorCleanupChannel plugin start", this.jdField_do, this.a);
    this.jdField_if = null;
    this.a = null;
    AlertUtil.logEntry("Stopping FileMonitorCleanupChannel plugin end", this.jdField_do, this.a);
  }
  
  private ICSILEJB a()
    throws EJBException, NamingException, RemoteException, CreateException, CSILException
  {
    String str1 = this.jdField_if.getProperty("csil.provider.url", "iiop://localhost:900");
    String str2 = this.jdField_if.getProperty("csil.initial.context.factory", "com.ibm.websphere.naming.WsnInitialContextFactory");
    String str3 = this.jdField_if.getProperty("csil.jndi.name", "CSILEJBHome");
    String str4 = this.jdField_if.getProperty("csil.user");
    String str5 = null;
    if ((str4 == null) || (str4.length() <= 0)) {
      str4 = null;
    } else {
      try
      {
        str5 = HfnEncrypt.decryptHexEncode(this.jdField_if.getProperty("csil.password"));
        if (str5 == null) {
          str5 = this.jdField_if.getProperty("csil.password");
        }
      }
      catch (Exception localException)
      {
        str5 = this.jdField_if.getProperty("csil.password");
      }
    }
    Properties localProperties = new Properties();
    localProperties.put("java.naming.factory.initial", str2);
    localProperties.put("java.naming.provider.url", str1);
    if (str4 != null)
    {
      localProperties.put("java.naming.security.principal", str4);
      localProperties.put("java.naming.security.credentials", str5);
    }
    InitialContext localInitialContext = new InitialContext(localProperties);
    CSILEJBHome localCSILEJBHome = (CSILEJBHome)localInitialContext.lookup(str3);
    ICSILEJB localICSILEJB = localCSILEJBHome.create();
    return localICSILEJB;
  }
  
  protected String getPluginName()
  {
    return "FileMonitorCleanup";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.filemonitor.FileMonitorCleanupChannel
 * JD-Core Version:    0.7.0.1
 */