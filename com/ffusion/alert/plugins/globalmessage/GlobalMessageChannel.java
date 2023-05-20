package com.ffusion.alert.plugins.globalmessage;

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

public class GlobalMessageChannel
  implements IAEChannel
{
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_for;
  private static final String jdField_int = "GlobalMessage";
  private Properties jdField_do = null;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_for = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing GlobalMessageChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = paramProperties;
    this.jdField_do = new Properties();
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, this.jdField_do);
    localCreateAlertThread.start();
    AlertUtil.logEntry("Initializing GlobalMessageChannel plugin end", this.jdField_for, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing GlobalMessage Alert", this.jdField_for, this.a);
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
      localICSILEJB.processGlobalMessages();
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int i;
      AlertUtil.logEntry("Processing  GlobalMessageAlert end", this.jdField_for, this.a);
    }
    catch (EJBException localEJBException2)
    {
      if (this.jdField_for) {
        localEJBException2.printStackTrace(this.a);
      }
      i = -1000;
      return i;
    }
    catch (RemoteException localRemoteException2)
    {
      if (this.jdField_for) {
        localRemoteException2.printStackTrace(this.a);
      }
      i = -1001;
      return i;
    }
    catch (CSILException localCSILException2)
    {
      if (this.jdField_for)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException2.getCode(), this.jdField_for, this.a);
        localCSILException2.printStackTrace(this.a);
      }
      i = -1004;
      return i;
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
    AlertUtil.logEntry("Stopping GlobalMessageChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = null;
    this.a = null;
    AlertUtil.logEntry("Stopping GlobalMessageChannel plugin end", this.jdField_for, this.a);
  }
  
  protected String getPluginName()
  {
    return "GlobalMessage";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.globalmessage.GlobalMessageChannel
 * JD-Core Version:    0.7.0.1
 */