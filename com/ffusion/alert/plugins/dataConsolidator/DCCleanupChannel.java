package com.ffusion.alert.plugins.dataConsolidator;

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

public class DCCleanupChannel
  implements IAEChannel
{
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_for = true;
  private ArrayList jdField_new = null;
  private Properties jdField_do = null;
  private static final String jdField_int = "DataConsolidatorCleanup";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_for = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing DCCleanupChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = paramProperties;
    this.jdField_new = new ArrayList();
    this.jdField_new.add("All");
    this.jdField_new.add("Account histories");
    this.jdField_new.add("Account summaries");
    this.jdField_new.add("Account transactions");
    this.jdField_new.add("Lockbox summaries");
    this.jdField_new.add("Lockbox transactions");
    this.jdField_new.add("Lockbox credit items");
    this.jdField_new.add("Disbursement summaries");
    this.jdField_new.add("Disbursement transactions");
    this.jdField_do = new Properties();
    Iterator localIterator = this.jdField_new.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      this.jdField_do.setProperty(str, paramProperties.getProperty(str, "7"));
    }
    this.jdField_do.setProperty("Data Classificaiton", paramProperties.getProperty("Data Classificaiton", "All"));
    CreateAlertThread localCreateAlertThread = new CreateAlertThread(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine, this.jdField_do);
    localCreateAlertThread.start();
    AlertUtil.logEntry("Initializing DCCleanupChannel plugin end", this.jdField_for, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing DataConsolidatorCleanUp Alert...", this.jdField_for, this.a);
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
    String str1 = null;
    String str2 = null;
    int i = 0;
    HashMap localHashMap = null;
    try
    {
      Iterator localIterator = this.jdField_new.iterator();
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str2 = paramProperties.getProperty(str1);
        if ((str2 != null) && (str2.length() > 0))
        {
          String str3 = paramProperties.getProperty("Data Classificaiton", "Previous Day");
          AlertUtil.logEntry("Processing DataConsolidatorCleanUp Alert. Record type=" + str1, this.jdField_for, this.a);
          AlertUtil.logEntry("Processing DataConsolidatorCleanUp Alert. Record age=" + str2, this.jdField_for, this.a);
          AlertUtil.logEntry("Processing DataConsolidatorCleanUp Alert. Record dataClassification=" + str3, this.jdField_for, this.a);
          i = Integer.parseInt(str2);
          localHashMap = new HashMap();
          if (str3.equals("All")) {
            localHashMap.put("DATA_CLASSIFICATION", "A");
          } else if (str3.equals("Intra Day")) {
            localHashMap.put("DATA_CLASSIFICATION", "I");
          } else if (str3.equals("Previous Day")) {
            localHashMap.put("DATA_CLASSIFICATION", "P");
          }
          localICSILEJB.cleanupDC(str1, i, localHashMap);
        }
      }
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int j;
      AlertUtil.logEntry("Processing DataConsolidatorCleanUpAlert end", this.jdField_for, this.a);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      AlertUtil.logEntry("Invalid record age setting for " + str1 + ": " + "An integer representing the number of days is required. " + "Alert processing failed.", this.jdField_for, this.a);
      j = -2000;
      return j;
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
      catch (Exception localException6) {}
    }
    return 0;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping DataConsolidatorCleanupChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = null;
    this.a = null;
    this.jdField_new = null;
    AlertUtil.logEntry("Stopping DataConsolidatorCleanupChannel plugin end", this.jdField_for, this.a);
  }
  
  protected String getPluginName()
  {
    return "DataConsolidatorCleanup";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.dataConsolidator.DCCleanupChannel
 * JD-Core Version:    0.7.0.1
 */