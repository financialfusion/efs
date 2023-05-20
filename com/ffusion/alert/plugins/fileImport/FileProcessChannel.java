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
import java.net.InetAddress;
import java.net.UnknownHostException;
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

public class FileProcessChannel
  implements IAEChannel
{
  public static final String PROCESS_LIST = "PROCESS_LIST";
  private Properties jdField_if = null;
  private PrintWriter a = null;
  private boolean jdField_for;
  private Properties jdField_do = null;
  private static final String jdField_int = "FileProcessing";
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.jdField_for = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    this.a = paramPrintWriter;
    AlertUtil.logEntry("Initializing FileProcessChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = paramProperties;
    a locala = new a(paramProperties, getPluginName(), paramPrintWriter, paramIAEAlertEngine);
    locala.start();
    AlertUtil.logEntry("Initializing FileProcessChannel plugin end", this.jdField_for, this.a);
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing FileProcessingAlert...", this.jdField_for, this.a);
    String str = paramProperties.getProperty("fileType");
    AlertUtil.logEntry("Processing FileProcessingAlert. File type=" + str, this.jdField_for, this.a);
    if ((str == null) || (str.length() <= 0))
    {
      AlertUtil.logEntry("No file type specified. Alert processing failed.", this.jdField_for, this.a);
      return -1;
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
      return -1002;
    }
    catch (CreateException localCreateException)
    {
      if (this.jdField_for) {
        localCreateException.printStackTrace(this.a);
      }
      return -1003;
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
      localHashMap.put("HOST_NAME", InetAddress.getLocalHost().getHostName());
      localICSILEJB.processFIFiles(str, localHashMap);
      try
      {
        localICSILEJB.remove();
      }
      catch (Exception localException1) {}
      int i;
      AlertUtil.logEntry("Processing FileProcessingAlert end", this.jdField_for, this.a);
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
    catch (UnknownHostException localUnknownHostException)
    {
      if (this.jdField_for) {
        localUnknownHostException.printStackTrace(this.a);
      }
      i = -2001;
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
      catch (Exception localException6) {}
    }
    return 0;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping FileProcessChannel plugin start", this.jdField_for, this.a);
    this.jdField_if = null;
    this.a = null;
    AlertUtil.logEntry("Stopping FileProcessChannel plugin end", this.jdField_for, this.a);
  }
  
  protected String getPluginName()
  {
    return "FileProcessing";
  }
  
  class a
    extends Thread
  {
    static final int jdField_if = 15000;
    private Properties a;
    private String jdField_int;
    private PrintWriter jdField_for = null;
    private boolean jdField_byte;
    private IAEAlertEngine jdField_new;
    private String jdField_do;
    private AEApplicationInfo jdField_try;
    
    a(Properties paramProperties, String paramString, PrintWriter paramPrintWriter, IAEAlertEngine paramIAEAlertEngine)
    {
      this.a = paramProperties;
      this.jdField_int = paramString;
      this.jdField_for = paramPrintWriter;
      this.jdField_new = paramIAEAlertEngine;
      this.jdField_byte = Boolean.valueOf(this.a.getProperty("DEBUG", "FALSE")).booleanValue();
    }
    
    public void run()
    {
      try
      {
        sleep(15000L);
      }
      catch (Exception localException)
      {
        if (this.jdField_byte)
        {
          this.jdField_for.println("Exception: " + localException);
          localException.printStackTrace(this.jdField_for);
        }
      }
      a();
    }
    
    private void a(Properties paramProperties, IAEAlertDefinition paramIAEAlertDefinition)
    {
      AlertUtil.logEntry("Creating system alert...", this.jdField_byte, this.jdField_for);
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
        IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = { AEDeliveryInfoFactory.create(this.jdField_int, 1, j, paramProperties) };
        if (paramIAEAlertDefinition == null)
        {
          int k = this.jdField_new.createAlert(this.jdField_try, this.jdField_do, 2, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo, "Processing...");
          AlertUtil.logEntry("New system alertID: " + k, this.jdField_byte, this.jdField_for);
        }
        else
        {
          this.jdField_new.updateAlert(this.jdField_try, paramIAEAlertDefinition.getId(), this.jdField_do, 2, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo, "Processing...");
          AlertUtil.logEntry("Updated alert with ID: " + paramIAEAlertDefinition.getId(), this.jdField_byte, this.jdField_for);
        }
      }
      catch (Exception localException)
      {
        if (this.jdField_byte) {
          localException.printStackTrace(this.jdField_for);
        }
      }
    }
    
    private void a()
    {
      try
      {
        IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
        this.jdField_do = (this.jdField_int + "Alert");
        try
        {
          String str1 = HfnEncrypt.decryptHexEncode(this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
          if (str1 == null) {
            str1 = this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
          }
          this.jdField_try = new AEApplicationInfo(this.a.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str1);
        }
        catch (Exception localException2)
        {
          this.jdField_try = new AEApplicationInfo(this.a.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
        }
        ArrayList localArrayList = new ArrayList();
        arrayOfIAEAlertDefinition = this.jdField_new.getUserAlerts(this.jdField_try, this.jdField_do, true);
        boolean bool = Boolean.valueOf(this.a.getProperty("UPDATE")).booleanValue();
        String str2;
        if ((arrayOfIAEAlertDefinition != null) && (arrayOfIAEAlertDefinition.length > 0)) {
          for (int i = 0; i < arrayOfIAEAlertDefinition.length; i++)
          {
            Properties localProperties1 = arrayOfIAEAlertDefinition[i].getDeliveryInfo()[0].getDeliveryProperties();
            str2 = arrayOfIAEAlertDefinition[i].getDeliveryInfo()[0].getDeliveryChannelName();
            if (str2.equals(this.jdField_int))
            {
              localArrayList.add(arrayOfIAEAlertDefinition[i]);
              break;
            }
          }
        }
        if ((!localArrayList.isEmpty()) && (!bool)) {
          return;
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(this.a.getProperty("PROCESS_LIST"), "|");
        while (localStringTokenizer.hasMoreTokens())
        {
          str2 = localStringTokenizer.nextToken();
          Properties localProperties2 = new Properties();
          localProperties2.setProperty("fileType", str2);
          Object localObject = null;
          for (int j = 0; j < localArrayList.size(); j++)
          {
            IAEAlertDefinition localIAEAlertDefinition = (IAEAlertDefinition)localArrayList.get(j);
            Properties localProperties3 = localIAEAlertDefinition.getDeliveryInfo()[0].getDeliveryProperties();
            if (str2.equals(localProperties3.getProperty("fileType")))
            {
              localObject = localIAEAlertDefinition;
              break;
            }
          }
          a(localProperties2, localObject);
        }
      }
      catch (Exception localException1)
      {
        if (this.jdField_byte)
        {
          this.jdField_for.println("Exception: " + localException1);
          localException1.printStackTrace(this.jdField_for);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.fileImport.FileProcessChannel
 * JD-Core Version:    0.7.0.1
 */