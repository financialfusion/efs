package com.ffusion.alert.plugins.positivepay;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.plugins.AlertUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class PPayDefaultDecisions
  implements IAEChannel
{
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_do;
  private static final String jdField_for = "PositivePayDefaultDecisions";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_do = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing PPayDefaultDecisions plugin start", this.jdField_do, this.a);
    this.jdField_if = paramProperties;
    AlertUtil.logEntry("Initializing PPayDefaultDecisions plugin end", this.jdField_do, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing PPayCleanUp Alert...", this.jdField_do, this.a);
    String str = paramProperties.getProperty("PPAY_AFFILIATE_BANK_ID");
    int i = 0;
    if ((str == null) || (str.length() <= 0))
    {
      AlertUtil.logEntry("Error: No affiliate bank ID defined.", this.jdField_do, this.a);
      return -2000;
    }
    try
    {
      i = Integer.parseInt(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      if (this.jdField_do)
      {
        AlertUtil.logEntry("NumberFormatException: the affiliate bank ID must be an integer.", this.jdField_do, this.a);
        localNumberFormatException.printStackTrace(this.a);
      }
      return -2000;
    }
    ICSILEJB localICSILEJB = null;
    try
    {
      localICSILEJB = AlertUtil.connectToCSILEJB(this.jdField_if);
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
      localICSILEJB.submitDefaultDecisions(i, localHashMap);
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int j;
      AlertUtil.logEntry("Processing  PPayCleanUpAlert end", this.jdField_do, this.a);
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
    AlertUtil.logEntry("Stopping PPayDefaultDecisions plugin start", this.jdField_do, this.a);
    this.jdField_if = null;
    this.a = null;
    AlertUtil.logEntry("Stopping PPayDefaultDecisions plugin end", this.jdField_do, this.a);
  }
  
  protected String getPluginName()
  {
    return "PositivePayDefaultDecisions";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.positivepay.PPayDefaultDecisions
 * JD-Core Version:    0.7.0.1
 */