package com.ffusion.alert.plugins.fileImport;

import com.ffusion.alert.factory.AEDeliveryInfoFactory;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.plugins.AlertUtil;
import com.ffusion.beans.DateTime;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.util.HfnEncrypt;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

public class FileCleanupChannel
  implements IAEChannel
{
  public static final String CLEAN_LIST = "CLEAN_LIST";
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_do;
  private static final String jdField_for = "FileCleanup";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_do = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing FileCleanupChannel plugin start", this.jdField_do, this.a);
    this.jdField_if = paramProperties;
    a locala = new a(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine);
    locala.start();
    AlertUtil.logEntry("Initializing FileCleanupChannel plugin end", this.jdField_do, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing FileCleanUp Alert...", this.jdField_do, this.a);
    String str1 = paramProperties.getProperty("fileType");
    AlertUtil.logEntry("Processing FileCleanUp Alert. File type=" + str1, this.jdField_do, this.a);
    if ((str1 == null) || (str1.length() <= 0))
    {
      AlertUtil.logEntry("No file type specified. Alert processing failed.", this.jdField_do, this.a);
      return -2000;
    }
    String str2 = paramProperties.getProperty("status");
    AlertUtil.logEntry("Processing FileCleanUp Alert. File status=" + str2, this.jdField_do, this.a);
    if ((str2 == null) || (str2.length() <= 0))
    {
      AlertUtil.logEntry("No file status specified. Alert processing failed.", this.jdField_do, this.a);
      return -2000;
    }
    String str3 = paramProperties.getProperty("age");
    AlertUtil.logEntry("Processing FileCleanUp Alert. File age=" + str3, this.jdField_do, this.a);
    if ((str3 == null) || (str3.length() <= 0))
    {
      AlertUtil.logEntry("No file age specified. Alert processing failed.", this.jdField_do, this.a);
      return -2000;
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(str3);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      AlertUtil.logEntry("Invalid file age setting: An integer representing the number of days required. Alert processing failed.", this.jdField_do, this.a);
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
      localICSILEJB.cleanupFIFiles(str1, str2, i, localHashMap);
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int j;
      AlertUtil.logEntry("Processing  FileCleanUpAlert end", this.jdField_do, this.a);
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
    AlertUtil.logEntry("Stopping FileCleanupChannel plugin start", this.jdField_do, this.a);
    this.jdField_if = null;
    this.a = null;
    AlertUtil.logEntry("Stopping FileCleanupChannel plugin end", this.jdField_do, this.a);
  }
  
  protected String getPluginName()
  {
    return "FileCleanup";
  }
  
  class a
    extends Thread
  {
    private Properties a;
    private String jdField_try;
    private PrintWriter jdField_new = null;
    private boolean jdField_do;
    private IAEAlertEngine jdField_int;
    private String jdField_if;
    private AEApplicationInfo jdField_for;
    
    a(Properties paramProperties, String paramString, PrintWriter paramPrintWriter, IAEAlertEngine paramIAEAlertEngine)
    {
      this.a = paramProperties;
      this.jdField_try = paramString;
      this.jdField_new = paramPrintWriter;
      this.jdField_int = paramIAEAlertEngine;
      this.jdField_do = Boolean.valueOf(this.a.getProperty("DEBUG")).booleanValue();
    }
    
    public void run()
    {
      try
      {
        sleep(15000L);
      }
      catch (Exception localException)
      {
        if (this.jdField_do) {
          this.jdField_new.println("Exception: " + localException);
        }
      }
      a();
    }
    
    private void a(Properties paramProperties, IAEAlertDefinition paramIAEAlertDefinition)
    {
      AlertUtil.logEntry("Creating system alert...", this.jdField_do, this.jdField_new);
      long l = Long.parseLong(this.a.getProperty("FREQ", Long.toString(86400000L)));
      String str1 = " " + this.a.getProperty("SCHEDHOUR", "02:00");
      String str2 = this.a.getProperty("STARTDATEFORMAT", "M/dd/yyyy");
      String str3 = this.a.getProperty("ENDDATEFORMAT", "M/dd/yyyy hh:mm:ss a");
      int i = Integer.parseInt(this.a.getProperty("WEEKS", "2600"));
      int j = Integer.parseInt(this.a.getProperty("MAXDELAY", "18000000"));
      try
      {
        Date localDate1 = null;
        DateTime localDateTime1 = new DateTime();
        localDateTime1.setFormat(str2);
        DateTime localDateTime2 = new DateTime(localDateTime1.toString() + str1, Locale.getDefault(), str3);
        localDateTime2.setFormat(str3);
        if (localDateTime2.before(new DateTime())) {
          localDate1 = new Date(localDateTime2.getTime().getTime() + 86400000L);
        } else {
          localDate1 = new Date(localDateTime2.getTime().getTime());
        }
        Date localDate2 = new Date(localDate1.getTime() + i * 604800000L);
        TimeZone localTimeZone = TimeZone.getDefault();
        AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(localDate1, localDate2, l, 0, localTimeZone, true);
        IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = { AEDeliveryInfoFactory.create(this.jdField_try, 1, j, paramProperties) };
        int k;
        if (paramIAEAlertDefinition == null)
        {
          k = this.jdField_int.createAlert(this.jdField_for, this.jdField_if, 2, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo, "Processing...");
          AlertUtil.logEntry("New system alertID: " + k, this.jdField_do, this.jdField_new);
        }
        else
        {
          k = paramIAEAlertDefinition.getId();
          this.jdField_int.updateAlert(this.jdField_for, k, this.jdField_if, 2, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo, "Processing...");
          AlertUtil.logEntry("Updated alert with ID: " + k, this.jdField_do, this.jdField_new);
        }
      }
      catch (Exception localException)
      {
        if (this.jdField_do) {
          localException.printStackTrace(this.jdField_new);
        }
      }
    }
    
    private void a()
    {
      try
      {
        IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
        this.jdField_if = (this.jdField_try + "Alert");
        try
        {
          String str1 = HfnEncrypt.decryptHexEncode(this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
          if (str1 == null) {
            str1 = this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
          }
          this.jdField_for = new AEApplicationInfo(this.a.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str1);
        }
        catch (Exception localException2)
        {
          this.jdField_for = new AEApplicationInfo(this.a.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
        }
        arrayOfIAEAlertDefinition = this.jdField_int.getUserAlerts(this.jdField_for, this.jdField_if, true);
        ArrayList localArrayList = new ArrayList();
        Object localObject2;
        if ((arrayOfIAEAlertDefinition != null) && (arrayOfIAEAlertDefinition.length > 0)) {
          for (int i = 0; i < arrayOfIAEAlertDefinition.length; i++)
          {
            localObject1 = arrayOfIAEAlertDefinition[i].getDeliveryInfo()[0].getDeliveryProperties();
            localObject2 = arrayOfIAEAlertDefinition[i].getDeliveryInfo()[0].getDeliveryChannelName();
            if (((String)localObject2).equals(this.jdField_try)) {
              localArrayList.add(arrayOfIAEAlertDefinition[i]);
            }
          }
        }
        boolean bool = Boolean.valueOf(this.a.getProperty("UPDATE")).booleanValue();
        if ((!localArrayList.isEmpty()) && (!bool)) {
          return;
        }
        Object localObject1 = new StringTokenizer(this.a.getProperty("CLEAN_LIST"), "|");
        while (((StringTokenizer)localObject1).hasMoreTokens())
        {
          localObject2 = new StringTokenizer(((StringTokenizer)localObject1).nextToken(), "_");
          String str2 = ((StringTokenizer)localObject2).nextToken();
          String str3 = ((StringTokenizer)localObject2).nextToken();
          String str4 = ((StringTokenizer)localObject2).nextToken();
          Properties localProperties1 = new Properties();
          localProperties1.setProperty("fileType", str2);
          localProperties1.setProperty("status", str3);
          if ((str4 == null) || (str4.length() == 0)) {
            localProperties1.setProperty("age", "7");
          } else {
            localProperties1.setProperty("age", str4);
          }
          Object localObject3 = null;
          for (int j = 0; j < localArrayList.size(); j++)
          {
            IAEAlertDefinition localIAEAlertDefinition = (IAEAlertDefinition)localArrayList.get(j);
            Properties localProperties2 = localIAEAlertDefinition.getDeliveryInfo()[0].getDeliveryProperties();
            if ((str2.equals(localProperties2.getProperty("fileType"))) && (str4.equals(localProperties2.getProperty("age"))) && (str3.equals(localProperties2.getProperty("status"))))
            {
              localObject3 = localIAEAlertDefinition;
              break;
            }
          }
          a(localProperties1, localObject3);
        }
      }
      catch (Exception localException1)
      {
        if (this.jdField_do) {
          this.jdField_new.println("Exception: " + localException1);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.fileImport.FileCleanupChannel
 * JD-Core Version:    0.7.0.1
 */