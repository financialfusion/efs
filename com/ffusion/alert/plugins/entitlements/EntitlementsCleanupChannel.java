package com.ffusion.alert.plugins.entitlements;

import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.plugins.AlertUtil;
import com.ffusion.alert.plugins.CreateAlertThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class EntitlementsCleanupChannel
  implements IAEChannel
{
  public static final String PERIOD_DAY = "day";
  public static final String PERIOD_WEEK = "week";
  public static final String PERIOD_MONTH = "month";
  public static final String DEFAULT_DAYS_NUMDAYS = "7";
  public static final String DEFAULT_WEEK_NUMDAYS = "14";
  public static final String DEFAULT_MONTH_NUMDAYS = "60";
  private Properties jdField_int = null;
  private PrintWriter jdField_for = null;
  private boolean a = true;
  private ArrayList jdField_do = null;
  private Properties jdField_new = null;
  private static final String jdField_if = "EntitlementsCleanup";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.a = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.jdField_for = paramPrintWriter;
    AlertUtil.logEntry("Initializing EntitlementsCleanupChannel plugin start", this.a, this.jdField_for);
    this.jdField_int = paramProperties;
    this.jdField_do = new ArrayList();
    this.jdField_new = new Properties();
    this.jdField_do.add("day");
    this.jdField_new.setProperty("day", paramProperties.getProperty("day", "7"));
    this.jdField_do.add("week");
    this.jdField_new.setProperty("week", paramProperties.getProperty("week", "14"));
    this.jdField_do.add("month");
    this.jdField_new.setProperty("month", paramProperties.getProperty("month", "60"));
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, this.jdField_new);
    localCreateAlertThread.start();
    AlertUtil.logEntry("Initializing EntitlementsCleanupChannel plugin end", this.a, this.jdField_for);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing EntitlementsCleanup Alert...", this.a, this.jdField_for);
    ICSILEJB localICSILEJB = null;
    try
    {
      localICSILEJB = AlertUtil.connectToCSILEJB(this.jdField_int);
    }
    catch (NamingException localNamingException)
    {
      if (this.a) {
        localNamingException.printStackTrace(this.jdField_for);
      }
      return -2000;
    }
    catch (CreateException localCreateException)
    {
      if (this.a) {
        localCreateException.printStackTrace(this.jdField_for);
      }
      return -2000;
    }
    catch (EJBException localEJBException1)
    {
      if (this.a) {
        localEJBException1.printStackTrace(this.jdField_for);
      }
      return -1000;
    }
    catch (RemoteException localRemoteException1)
    {
      if (this.a) {
        localRemoteException1.printStackTrace(this.jdField_for);
      }
      return -1001;
    }
    catch (CSILException localCSILException1)
    {
      if (this.a)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException1.getCode(), this.a, this.jdField_for);
        localCSILException1.printStackTrace(this.jdField_for);
      }
      return -1004;
    }
    String str1 = null;
    String str2 = null;
    int i = 0;
    HashMap localHashMap = null;
    try
    {
      Iterator localIterator = this.jdField_do.iterator();
      int j;
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str2 = paramProperties.getProperty(str1);
        if ((str2 != null) && (str2.length() > 0))
        {
          AlertUtil.logEntry("Processing EntitlementsCleanup Alert. period=" + str1, this.a, this.jdField_for);
          AlertUtil.logEntry("Processing EntitlementsCleanup Alert. age=" + str2, this.a, this.jdField_for);
          i = Integer.parseInt(str2);
          if (str1.equals("day"))
          {
            j = 2;
          }
          else if (str1.equals("week"))
          {
            j = 3;
          }
          else
          {
            if (!str1.equals("month")) {
              continue;
            }
            j = 4;
          }
          localHashMap = new HashMap();
          localICSILEJB.cleanupEntitlements(j, i, localHashMap);
        }
      }
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      AlertUtil.logEntry("Processing EntitlementsCleanupAlert end", this.a, this.jdField_for);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      AlertUtil.logEntry("Invalid record age setting for " + str1 + ": " + "An integer representing the number of days is required. " + "Alert processing failed.", this.a, this.jdField_for);
      j = -2000;
      return j;
    }
    catch (EJBException localEJBException2)
    {
      if (this.a) {
        localEJBException2.printStackTrace(this.jdField_for);
      }
      j = -1000;
      return j;
    }
    catch (RemoteException localRemoteException2)
    {
      if (this.a) {
        localRemoteException2.printStackTrace(this.jdField_for);
      }
      j = -1001;
      return j;
    }
    catch (CSILException localCSILException2)
    {
      if (this.a)
      {
        AlertUtil.logEntry("Alert processing failed. Error code=" + localCSILException2.getCode(), this.a, this.jdField_for);
        localCSILException2.printStackTrace(this.jdField_for);
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
      catch (Exception localException6) {}
    }
    return 0;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping EntitlementsCleanupChannel plugin start", this.a, this.jdField_for);
    this.jdField_int = null;
    this.jdField_for = null;
    this.jdField_do = null;
    AlertUtil.logEntry("Stopping EntitlementsCleanupChannel plugin end", this.a, this.jdField_for);
  }
  
  protected String getPluginName()
  {
    return "EntitlementsCleanup";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.entitlements.EntitlementsCleanupChannel
 * JD-Core Version:    0.7.0.1
 */