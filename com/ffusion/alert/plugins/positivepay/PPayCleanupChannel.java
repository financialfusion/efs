package com.ffusion.alert.plugins.positivepay;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.plugins.AlertUtil;
import com.ffusion.alert.plugins.CreateAlertThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class PPayCleanupChannel
  implements IAEChannel
{
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_for;
  private static final String jdField_int = "PositivePayCleanup";
  private Properties jdField_do = null;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_for = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing PPayCleanupChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = paramProperties;
    this.jdField_do = new Properties();
    this.jdField_do.setProperty("age.days", paramProperties.getProperty("age.days", "7"));
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, this.jdField_do);
    localCreateAlertThread.start();
    AlertUtil.logEntry("Initializing PPayCleanupChannel plugin end", this.jdField_for, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing PPayCleanUp Alert...", this.jdField_for, this.a);
    String str = paramProperties.getProperty("age.days");
    AlertUtil.logEntry("Processing PPayCleanUp Alert. Entry age=" + str, this.jdField_for, this.a);
    if ((str == null) || (str.length() <= 0))
    {
      AlertUtil.logEntry("No file age specified. Alert processing failed.", this.jdField_for, this.a);
      return -2000;
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      AlertUtil.logEntry("Invalid file age setting: An integer representing the number of days required. Alert processing failed.", this.jdField_for, this.a);
      return -2000;
    }
    ICSILEJB localICSILEJB = null;
    try
    {
      localICSILEJB = AlertUtil.connectToCSILEJB(this.jdField_if);
    }
    catch (NamingException localNamingException)
    {
      if (this.jdField_for) {
        localNamingException.printStackTrace(this.a);
      }
      return -2000;
    }
    catch (CreateException localCreateException)
    {
      if (this.jdField_for) {
        localCreateException.printStackTrace(this.a);
      }
      return -2000;
    }
    catch (EJBException localEJBException1)
    {
      if (this.jdField_for) {
        localEJBException1.printStackTrace(this.a);
      }
      return -1000;
    }
    catch (RemoteException localRemoteException1)
    {
      if (this.jdField_for) {
        localRemoteException1.printStackTrace(this.a);
      }
      return -1001;
    }
    catch (CSILException localCSILException1)
    {
      if (this.jdField_for)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException1.getCode(), this.jdField_for, this.a);
        localCSILException1.printStackTrace(this.a);
      }
      return -1004;
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localICSILEJB.cleanupPPay(i, localHashMap);
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int j;
      AlertUtil.logEntry("Processing  PPayCleanUpAlert end", this.jdField_for, this.a);
    }
    catch (EJBException localEJBException2)
    {
      if (this.jdField_for) {
        localEJBException2.printStackTrace(this.a);
      }
      j = -1000;
      return j;
    }
    catch (RemoteException localRemoteException2)
    {
      if (this.jdField_for) {
        localRemoteException2.printStackTrace(this.a);
      }
      j = -1001;
      return j;
    }
    catch (CSILException localCSILException2)
    {
      if (this.jdField_for)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException2.getCode(), this.jdField_for, this.a);
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
    AlertUtil.logEntry("Stopping PPayCleanupChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = null;
    this.a = null;
    AlertUtil.logEntry("Stopping PPayCleanupChannel plugin end", this.jdField_for, this.a);
  }
  
  protected String getPluginName()
  {
    return "PositivePayCleanup";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.positivepay.PPayCleanupChannel
 * JD-Core Version:    0.7.0.1
 */