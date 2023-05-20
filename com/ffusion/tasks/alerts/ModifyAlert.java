package com.ffusion.tasks.alerts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Email;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAlert
  extends Alert
  implements Task
{
  protected static String SCHEDULE = "SCHEDULE";
  protected static String PRIMARYDEST = "PRIMARYDEST";
  protected static String BACKUPDEST = "BACKUPDEST";
  protected static String STOCKINFO = "STOCK";
  protected static String MORETHAN = "More Than";
  protected static String LESSTHAN = "Less Than";
  protected static String ANYAMOUNT = "ANY AMOUNT";
  protected static String CRITERIA = "Criteria";
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String alertName = "Alert";
  protected String alertsName = "Alerts";
  protected String serviceName = "com.ffusion.services.Alerts";
  protected String maxAmountValue = String.valueOf(1.7976931348623157E+308D);
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processModifyAlert(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    if (paramHttpSession.getAttribute(this.alertName) == null)
    {
      this.error = 19001;
    }
    else
    {
      this.error = 0;
      set((Alert)paramHttpSession.getAttribute(this.alertName));
    }
    return this.error == 0;
  }
  
  protected String processModifyAlert(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyAlert(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String modifyAlert(HttpSession paramHttpSession)
  {
    String str = null;
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    if (paramHttpSession.getAttribute(this.alertName) == null)
    {
      this.error = 19001;
      str = this.taskErrorURL;
    }
    else
    {
      for (int i = 0; i < this.deliveryInfos.size(); i++)
      {
        localObject = (DeliveryInfo)this.deliveryInfos.get(i);
        if (((((DeliveryInfo)localObject).getChannelName() == null) || (((DeliveryInfo)localObject).getChannelName().length() == 0)) && ((((DeliveryInfo)localObject).getPropertiesString() == null) || (((DeliveryInfo)localObject).getPropertiesString().length() == 0))) {
          if (i < this.deliveryInfos.size() - 1)
          {
            this.deliveryInfos.remove(i);
            i--;
          }
          else
          {
            this.deliveryInfos.remove(i);
          }
        }
      }
      HashMap localHashMap = null;
      if (localAlerts != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localAlerts);
      }
      Object localObject = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        Alert localAlert1 = new Alert();
        localAlert1 = com.ffusion.csil.core.Alerts.modifyAlert((SecureUser)localObject, this, localHashMap);
        set(localAlert1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Alert localAlert2 = (Alert)paramHttpSession.getAttribute(this.alertName);
        localAlert2.set(this);
        str = this.successURL;
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((bool) && (this.validate.indexOf("TYPE") != -1)) {
        bool = validateType();
      }
      if ((bool) && (this.validate.indexOf("PRIORITY") != -1)) {
        bool = validatePriority();
      }
      if ((bool) && (this.validate.indexOf("STARTDATE") != -1)) {
        bool = validateStartDate();
      }
      if ((bool) && (this.validate.indexOf("ENDDATE") != -1)) {
        bool = validateEndDate();
      }
      if ((bool) && (this.validate.indexOf("INTERVAL") != -1)) {
        bool = validateInterval();
      }
      if ((bool) && (this.validate.indexOf("TIMEZONE") != -1)) {
        bool = validateTimeZone();
      }
      if ((bool) && (this.validate.indexOf("APPINFO") != -1)) {
        bool = validateAppInfo();
      }
      if ((bool) && (this.validate.indexOf("MESSAGE") != -1)) {
        bool = validateMessage();
      }
      if ((bool) && (this.validate.indexOf("DELIVERYINFOS") != -1)) {
        bool = validateDeliveryInfos();
      }
      if ((bool) && (this.validate.indexOf(PRIMARYDEST) != -1)) {
        bool = validatePrimaryDestination();
      }
      if ((bool) && (this.validate.indexOf(BACKUPDEST) != -1)) {
        bool = validateBackupDestination();
      }
      if ((bool) && (this.validate.indexOf(STOCKINFO) != -1)) {
        bool = validateStockInfo(paramHttpSession);
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateType()
  {
    this.error = 0;
    if (this.type < 0) {
      this.error = 19100;
    }
    return this.error == 0;
  }
  
  protected boolean validatePriority()
  {
    this.error = 0;
    if (this.priority < 0) {
      this.error = 19101;
    }
    return this.error == 0;
  }
  
  protected boolean validateStartDate()
  {
    this.error = 0;
    if ((this.startDate == null) || (!this.startDate.isValid())) {
      this.error = 19102;
    }
    return this.error == 0;
  }
  
  protected boolean validateEndDate()
  {
    this.error = 0;
    if ((this.endDate == null) || (!this.endDate.isValid())) {
      this.error = 19103;
    }
    return this.error == 0;
  }
  
  protected boolean validateInterval()
  {
    this.error = 0;
    if (this.interval < 1000L) {
      this.error = 19104;
    }
    return this.error == 0;
  }
  
  protected boolean validateTimeZone()
  {
    this.error = 0;
    if ((this.timeZone == null) || (this.timeZone.toString().length() == 0)) {
      this.error = 19105;
    }
    return this.error == 0;
  }
  
  protected boolean validateAppInfo()
  {
    this.error = 0;
    if ((this.appInfo == null) || (this.appInfo.trim().length() == 0)) {
      this.error = 19116;
    }
    return this.error == 0;
  }
  
  protected boolean validateMessage()
  {
    this.error = 0;
    if ((this.message == null) || (this.message.trim().length() == 0)) {
      this.error = 19107;
    }
    return this.error == 0;
  }
  
  protected boolean validateDeliveryInfos()
  {
    this.error = 0;
    Currency localCurrency1 = new Currency(this.maxAmountValue, Locale.getDefault());
    if (this.deliveryInfos == null)
    {
      this.error = 19108;
    }
    else
    {
      Iterator localIterator1 = this.deliveryInfos.iterator();
      int i = 0;
      double d1 = 0.0D;
      double d2 = 0.0D;
      while (localIterator1.hasNext())
      {
        DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator1.next();
        Properties localProperties = localDeliveryInfo.getPropertiesValue();
        if (localProperties != null)
        {
          Enumeration localEnumeration = localProperties.keys();
          while (localEnumeration.hasMoreElements())
          {
            String str2 = (String)localEnumeration.nextElement();
            String str1;
            if (str2.equals("ACCOUNTNUMBER"))
            {
              str1 = localProperties.getProperty("ACCOUNTNUMBER");
              if ((str1 == null) || (str1.trim().length() == 0))
              {
                this.error = 19109;
                return false;
              }
            }
            else if (str2.equals("MINAMOUNT"))
            {
              i = 1;
              str1 = localProperties.getProperty("MINAMOUNT");
              if ((str1 != null) && (str1.length() != 0))
              {
                try
                {
                  Currency localCurrency2 = new Currency(str1, Locale.getDefault());
                  if (localCurrency2.validateCurrency(str1))
                  {
                    d2 = localCurrency2.getAmountValue().doubleValue();
                    if (localCurrency2.compareTo(localCurrency1) > 0)
                    {
                      this.error = 19110;
                      return false;
                    }
                  }
                  else
                  {
                    this.error = 19110;
                    return false;
                  }
                }
                catch (Exception localException1)
                {
                  this.error = 19110;
                  return false;
                }
                if (d2 < 0.0D)
                {
                  this.error = 19110;
                  return false;
                }
              }
            }
            else if (str2.equals("MAXAMOUNT"))
            {
              i = 1;
              str1 = localProperties.getProperty("MAXAMOUNT");
              if ((str1 != null) && (str1.length() != 0))
              {
                try
                {
                  Currency localCurrency3 = new Currency(str1, Locale.getDefault());
                  if (localCurrency3.validateCurrency(str1))
                  {
                    d1 = localCurrency3.getAmountValue().doubleValue();
                    if (localCurrency3.compareTo(localCurrency1) > 0)
                    {
                      this.error = 19111;
                      return false;
                    }
                  }
                  else
                  {
                    this.error = 19111;
                    return false;
                  }
                }
                catch (Exception localException2)
                {
                  this.error = 19111;
                  return false;
                }
                if (d1 < 0.0D)
                {
                  this.error = 19111;
                  return false;
                }
              }
            }
            else if (str2.equals("Amount"))
            {
              str1 = localProperties.getProperty("Amount");
              if ((str1 != null) && (str1.length() != 0)) {
                try
                {
                  Currency localCurrency4 = new Currency(str1, Locale.getDefault());
                  if (localCurrency4.validateCurrency(str1))
                  {
                    if (localCurrency4.compareTo(localCurrency1) > 0)
                    {
                      this.error = 19120;
                      return false;
                    }
                  }
                  else
                  {
                    this.error = 19120;
                    return false;
                  }
                  if (localCurrency4.getAmountValue().doubleValue() < 0.0D)
                  {
                    this.error = 19120;
                    return false;
                  }
                }
                catch (Exception localException3)
                {
                  this.error = 19120;
                  return false;
                }
              }
            }
            else if (str2.equals("ONETIME"))
            {
              str1 = localProperties.getProperty("ONETIME");
              if ((str1 == null) || (str1.length() == 0) || ((!str1.equalsIgnoreCase("true")) && (!str1.equalsIgnoreCase("false"))))
              {
                this.error = 19112;
                return false;
              }
            }
            else
            {
              Iterator localIterator2 = localProperties.values().iterator();
              while (localIterator2.hasNext())
              {
                String str3 = (String)localIterator2.next();
                if (str3.equals("ACCOUNTNUMBER"))
                {
                  this.error = 19109;
                  return false;
                }
                if (str3.equals("MINAMOUNT"))
                {
                  this.error = 19110;
                  return false;
                }
                if (str3.equals("MAXAMOUNT"))
                {
                  this.error = 19111;
                  return false;
                }
                if (str3.equals("ONETIME"))
                {
                  this.error = 19112;
                  return false;
                }
              }
            }
          }
        }
      }
      if (i != 0)
      {
        if ((d2 == 0.0D) && (d1 == 0.0D))
        {
          this.error = 19116;
          return false;
        }
        if ((d1 != 0.0D) && (d2 > d1))
        {
          this.error = 19117;
          return false;
        }
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateStockInfo(HttpSession paramHttpSession)
  {
    String str1 = (String)get(CRITERIA);
    if ((str1 == null) || ((str1 != null) && (str1.trim().length() == 0)))
    {
      this.error = 19121;
      return false;
    }
    String str2 = getAmount();
    if ((str2 == null) || ((str2 != null) && (str2.trim().length() == 0)))
    {
      if (str1.equals(ANYAMOUNT)) {
        return true;
      }
      this.error = 19120;
      return false;
    }
    try
    {
      Double localDouble = Double.valueOf(str2);
      if ((localDouble.isNaN()) || (localDouble.isInfinite()))
      {
        this.error = 19120;
        return false;
      }
    }
    catch (Exception localException1)
    {
      this.error = 19120;
      return false;
    }
    Currency localCurrency1 = new Currency(this.maxAmountValue, Locale.getDefault());
    double d;
    try
    {
      Currency localCurrency2 = new Currency(str2, Locale.getDefault());
      d = localCurrency2.getAmountValue().doubleValue();
      if (localCurrency2.compareTo(localCurrency1) > 0)
      {
        this.error = 19120;
        return false;
      }
    }
    catch (Exception localException2)
    {
      this.error = 19120;
      return false;
    }
    if (d < 0.0D)
    {
      this.error = 19120;
      return false;
    }
    return true;
  }
  
  public final void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean validatePrimaryDestination()
  {
    this.error = 19115;
    if (this.deliveryInfos == null)
    {
      this.error = 19108;
    }
    else
    {
      Iterator localIterator = this.deliveryInfos.iterator();
      while (localIterator.hasNext())
      {
        DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator.next();
        Properties localProperties = localDeliveryInfo.getPropertiesValue();
        if (localProperties != null)
        {
          String str1 = localProperties.getProperty("Primary");
          if ((str1 != null) && (str1.equals("True"))) {
            if ((localDeliveryInfo.getChannelName().equals("MessageCenter")) && (localDeliveryInfo.getSuspended().equals("false")))
            {
              this.error = 0;
            }
            else if (localDeliveryInfo.getChannelName().equals("Email"))
            {
              String str2 = localProperties.getProperty("to");
              if ((str2 != null) && (!str2.equals("")))
              {
                Email localEmail = new Email(str2);
                if (localEmail.isValid()) {
                  this.error = 0;
                } else {
                  this.error = 19118;
                }
              }
            }
          }
        }
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateBackupDestination()
  {
    this.error = 0;
    if (this.deliveryInfos == null)
    {
      this.error = 19108;
    }
    else
    {
      Iterator localIterator = this.deliveryInfos.iterator();
      while (localIterator.hasNext())
      {
        DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator.next();
        Properties localProperties = localDeliveryInfo.getPropertiesValue();
        if (localProperties != null)
        {
          String str1 = localProperties.getProperty("Backup");
          if ((str1 != null) && (str1.equals("True"))) {
            if (localDeliveryInfo.getChannelName().equals("Email"))
            {
              String str2 = localProperties.getProperty("to");
              if ((str2 != null) && (!str2.equals("")))
              {
                Email localEmail = new Email(str2);
                if (localEmail.isValid()) {
                  this.error = 0;
                } else {
                  this.error = 19118;
                }
              }
            }
          }
        }
      }
    }
    return this.error == 0;
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
  
  public final void setAlertName(String paramString)
  {
    this.alertName = paramString;
  }
  
  public final String getAlertName()
  {
    return this.alertName;
  }
  
  public final void setAlertsName(String paramString)
  {
    this.alertsName = paramString;
  }
  
  public final String getAlertsName()
  {
    return this.alertsName;
  }
  
  public void setAddStockSymbol(String paramString)
  {
    AlertStock localAlertStock = new AlertStock();
    localAlertStock.setSymbol(paramString);
    localAlertStock.setCriteria("ANY AMOUNT");
    super.addStock(localAlertStock);
  }
  
  public void setMaxAmountValue(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.maxAmountValue = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.ModifyAlert
 * JD-Core Version:    0.7.0.1
 */