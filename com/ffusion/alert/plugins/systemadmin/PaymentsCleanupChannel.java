package com.ffusion.alert.plugins.systemadmin;

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

public class PaymentsCleanupChannel
  implements IAEChannel
{
  private static final String jdField_int = "PaymentsCleanup";
  private boolean jdField_for = false;
  private Properties a = null;
  private Properties jdField_do = null;
  private PrintWriter jdField_if = null;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    CreateAlertThread localCreateAlertThread = null;
    this.jdField_for = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.jdField_if = paramPrintWriter;
    AlertUtil.logEntry("Initializing PaymentsCleanupChannel plugin start", this.jdField_for, this.jdField_if);
    this.a = paramProperties;
    this.jdField_do = new Properties();
    localCreateAlertThread = new CreateAlertThread(this.a, getPluginName(), this.jdField_if, paramIAEAlertEngine, this.jdField_do);
    localCreateAlertThread.start();
    AlertUtil.logEntry("Initializing PaymentsCleanupChannel plugin end", this.jdField_for, this.jdField_if);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    int i = 0;
    ICSILEJB localICSILEJB = null;
    AlertUtil.logEntry("Processing PaymentsCleanup Alert start ...", this.jdField_for, this.jdField_if);
    try
    {
      Object localObject1 = null;
      Object localObject2 = null;
      int j = 0;
      HashMap localHashMap = null;
      localICSILEJB = AlertUtil.connectToCSILEJB(this.a);
      localHashMap = new HashMap();
      localICSILEJB.cleanupPayments(localHashMap);
    }
    catch (NamingException localNamingException)
    {
      if (this.jdField_for) {
        localNamingException.printStackTrace(this.jdField_if);
      }
      i = -2000;
    }
    catch (CreateException localCreateException)
    {
      if (this.jdField_for) {
        localCreateException.printStackTrace(this.jdField_if);
      }
      i = -2000;
    }
    catch (EJBException localEJBException)
    {
      if (this.jdField_for) {
        localEJBException.printStackTrace(this.jdField_if);
      }
      i = -1000;
    }
    catch (RemoteException localRemoteException)
    {
      if (this.jdField_for) {
        localRemoteException.printStackTrace(this.jdField_if);
      }
      i = -1001;
    }
    catch (CSILException localCSILException)
    {
      if (this.jdField_for)
      {
        AlertUtil.logEntry("Alert processing failed. Error code = " + localCSILException.getCode(), this.jdField_for, this.jdField_if);
        localCSILException.printStackTrace(this.jdField_if);
      }
      i = -1004;
    }
    finally
    {
      try
      {
        if (localICSILEJB != null) {
          localICSILEJB.remove();
        }
      }
      catch (Exception localException) {}
    }
    AlertUtil.logEntry("Processing PaymentsCleanup Alert end.", this.jdField_for, this.jdField_if);
    return i;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping PaymentsCleanupChannel plugin start ...", this.jdField_for, this.jdField_if);
    this.a = null;
    this.jdField_if = null;
    AlertUtil.logEntry("Stopping PaymentsCleanupChannel plugin end.", this.jdField_for, this.jdField_if);
  }
  
  protected String getPluginName()
  {
    return "PaymentsCleanup";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.systemadmin.PaymentsCleanupChannel
 * JD-Core Version:    0.7.0.1
 */