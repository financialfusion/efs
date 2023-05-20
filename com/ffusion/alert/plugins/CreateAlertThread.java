package com.ffusion.alert.plugins;

import com.ffusion.alert.factory.AEDeliveryInfoFactory;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.beans.DateTime;
import com.ffusion.util.HfnEncrypt;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class CreateAlertThread
  extends Thread
{
  private Properties jdField_for = new Properties();
  private Properties a;
  private String jdField_int;
  private PrintWriter jdField_do = null;
  private boolean jdField_case;
  private boolean jdField_char;
  private IAEAlertEngine jdField_try;
  private String jdField_if;
  private AEApplicationInfo jdField_byte;
  private int jdField_new = 0;
  
  public CreateAlertThread(Properties paramProperties1, String paramString, PrintWriter paramPrintWriter, IAEAlertEngine paramIAEAlertEngine, Properties paramProperties2)
  {
    this.a = paramProperties1;
    this.jdField_int = paramString;
    this.jdField_do = paramPrintWriter;
    this.jdField_try = paramIAEAlertEngine;
    this.jdField_case = Boolean.valueOf(this.a.getProperty("DEBUG", "FALSE")).booleanValue();
    this.jdField_for = paramProperties2;
  }
  
  public void run()
  {
    AlertUtil.logEntry("Executing run method of alert thread", this.jdField_case, this.jdField_do);
    try
    {
      sleep(15000L);
    }
    catch (Exception localException)
    {
      AlertUtil.logEntry("Exception occurred with message: " + localException.getLocalizedMessage(), this.jdField_case, this.jdField_do);
      localException.printStackTrace(this.jdField_do);
    }
    jdField_if();
  }
  
  private void a()
  {
    AlertUtil.logEntry("Creating system alert...", this.jdField_case, this.jdField_do);
    long l = Long.parseLong(this.a.getProperty("FREQ", Long.toString(86400000L)));
    String str1 = this.a.getProperty("SCHEDHOUR", "02:00:00 AM");
    str1 = " " + str1;
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
        localDate1 = new Date(localDateTime2.getTime().getTime());
      } else {
        localDate1 = new Date(localDateTime2.getTime().getTime());
      }
      Date localDate2 = new Date(localDate1.getTime() + i * 604800000L);
      TimeZone localTimeZone = TimeZone.getDefault();
      AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(localDate1, localDate2, l, 0, localTimeZone, true);
      IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = { AEDeliveryInfoFactory.create(this.jdField_int, 1, j, this.jdField_for) };
      AlertUtil.logEntry("Delivery Channel: " + this.jdField_int, this.jdField_case, this.jdField_do);
      if (!this.jdField_char)
      {
        int k = this.jdField_try.createAlert(this.jdField_byte, this.jdField_if, 2, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo, "Processing...");
        AlertUtil.logEntry("New system alertID: " + k, this.jdField_case, this.jdField_do);
      }
      else
      {
        this.jdField_try.updateAlert(this.jdField_byte, this.jdField_new, this.jdField_if, 2, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo, "Processing...");
        AlertUtil.logEntry("Updated alert with ID: " + this.jdField_new, this.jdField_case, this.jdField_do);
      }
    }
    catch (Exception localException)
    {
      if (this.jdField_case)
      {
        AlertUtil.logEntry("Exception occurred with message : " + localException.getMessage(), this.jdField_case, this.jdField_do);
        localException.printStackTrace(this.jdField_do);
      }
    }
  }
  
  private void jdField_if()
  {
    try
    {
      IAEAlertDefinition[] arrayOfIAEAlertDefinition = null;
      this.jdField_if = (this.jdField_int + "Alert");
      try
      {
        String str1 = HfnEncrypt.decryptHexEncode(this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
        if (str1 == null) {
          str1 = this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
        }
        this.jdField_byte = new AEApplicationInfo(this.a.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str1);
      }
      catch (Exception localException2)
      {
        this.jdField_byte = new AEApplicationInfo(this.a.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), this.a.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
      }
      arrayOfIAEAlertDefinition = this.jdField_try.getUserAlerts(this.jdField_byte, this.jdField_if, true);
      if ((arrayOfIAEAlertDefinition != null) && (arrayOfIAEAlertDefinition.length > 0)) {
        for (int i = 0; i < arrayOfIAEAlertDefinition.length; i++)
        {
          String str2 = arrayOfIAEAlertDefinition[i].getDeliveryInfo()[0].getDeliveryChannelName();
          if (str2.equals(this.jdField_int))
          {
            this.jdField_new = arrayOfIAEAlertDefinition[i].getId();
            this.jdField_char = true;
            break;
          }
        }
      }
      boolean bool = Boolean.valueOf(this.a.getProperty("UPDATE")).booleanValue();
      if (this.jdField_char)
      {
        if (bool) {
          a();
        }
      }
      else {
        a();
      }
    }
    catch (Exception localException1)
    {
      if (this.jdField_case) {
        localException1.printStackTrace(this.jdField_do);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.CreateAlertThread
 * JD-Core Version:    0.7.0.1
 */