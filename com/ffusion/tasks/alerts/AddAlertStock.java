package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAlertStock
  extends ModifyAlertStock
{
  protected boolean addFlag = false;
  
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
    else if (!validateSymbol()) {
      str = this.taskErrorURL;
    } else {
      str = processAddAlertStock(localHttpSession);
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
    this.error = 0;
    Alert localAlert = getStockAlert(paramHttpSession);
    if (localAlert == null)
    {
      this.addFlag = true;
      this.alertStocks = new AlertStocks(this.locale);
      createStockAlert(paramHttpSession);
    }
    else
    {
      set(localAlert);
      this.alertStocks = getStocks();
    }
    return this.error == 0;
  }
  
  public String processAddAlertStock(HttpSession paramHttpSession)
    throws IOException
  {
    String str = null;
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    HashMap localHashMap = null;
    if (localAlerts != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
    }
    if (!this.alertStocks.contains(this.alertStock))
    {
      this.alertStocks.add(this.alertStock);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        Alert localAlert = new Alert();
        setStocks(this.alertStocks);
        Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
        if (this.addFlag)
        {
          localAlert = com.ffusion.csil.core.Alerts.addAlert(localSecureUser, this, localHashMap);
          paramHttpSession.setAttribute(this.alertName, localAlert);
        }
        else
        {
          localAlert = com.ffusion.csil.core.Alerts.modifyAlert(localSecureUser, this, localHashMap);
          paramHttpSession.setAttribute(this.alertName, localAlert);
        }
        com.ffusion.beans.alerts.Alerts localAlerts1 = new com.ffusion.beans.alerts.Alerts(localLocale);
        localHashMap.put("ALERTSLIST", localAlerts1);
        localAlerts1 = com.ffusion.csil.core.Alerts.getAlerts(localSecureUser, localHashMap);
        paramHttpSession.setAttribute(this.alertsName, localAlerts1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        localCSILException.printStackTrace();
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        str = this.successURL;
      }
    }
    return str;
  }
  
  protected Alert getStockAlert(HttpSession paramHttpSession)
  {
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    com.ffusion.beans.alerts.Alerts localAlerts1 = new com.ffusion.beans.alerts.Alerts(localLocale);
    HashMap localHashMap = new HashMap();
    if (localAlerts != null) {
      localHashMap.put("SERVICE", localAlerts);
    }
    localHashMap.put("ALERTSLIST", localAlerts1);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localAlerts1 = com.ffusion.csil.core.Alerts.getAlerts(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      localCSILException.printStackTrace();
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.alertsName, localAlerts1);
    }
    Iterator localIterator1 = localAlerts1.iterator();
    while (localIterator1.hasNext())
    {
      Alert localAlert = (Alert)localIterator1.next();
      DeliveryInfos localDeliveryInfos = localAlert.getDeliveryInfosValue();
      Iterator localIterator2 = localDeliveryInfos.iterator();
      while (localIterator2.hasNext())
      {
        String str = ((DeliveryInfo)localIterator2.next()).getChannelName();
        if (str.equalsIgnoreCase("StockPortfolio")) {
          return localAlert;
        }
      }
    }
    return null;
  }
  
  protected boolean createStockAlert(HttpSession paramHttpSession)
  {
    this.error = 0;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    setDeliveryInfoCount("4");
    DeliveryInfo localDeliveryInfo1 = getDeliveryInfoByID("1");
    DeliveryInfo localDeliveryInfo2 = getDeliveryInfoByID("2");
    DeliveryInfo localDeliveryInfo3 = getDeliveryInfoByID("3");
    DeliveryInfo localDeliveryInfo4 = getDeliveryInfoByID("4");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    User localUser = (User)paramHttpSession.getAttribute("User");
    setPriority(1);
    setDateFormat("MM/dd/yyyy");
    setEndDate("01/01/2100");
    setInterval("86400000");
    setTimeZone("EST");
    setDateFormat("MM/dd/yyyy/H:mm");
    String str1 = DateFormatUtil.getFormatter("MM/dd/yyyy").format(new Date());
    setStartDate(str1 + "/14:01");
    setAppInfo("ApplicationAlerts");
    setMessage("StockPrice of " + str1 + ":");
    setType(2);
    Properties localProperties1 = localDeliveryInfo1.getPropertiesValue();
    localProperties1.setProperty("AlertType", "StockPortfolio");
    localProperties1.setProperty("subject", "Stock Alert: ");
    localProperties1.setProperty("SECUREUSER", "" + localSecureUser.getXML());
    localProperties1.setProperty("PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
    localDeliveryInfo1.setMaxDelay("14400000");
    localDeliveryInfo1.setSuspended(false);
    localDeliveryInfo1.setChannelName("StockPortfolio");
    String str2 = (String)paramHttpSession.getAttribute("SecureAlert");
    String str3 = (String)paramHttpSession.getAttribute("DeliveryInfo1");
    String str4 = (String)paramHttpSession.getAttribute("DeliveryInfo2");
    String str5 = (String)paramHttpSession.getAttribute("DeliveryInfo1To");
    String str6 = (String)paramHttpSession.getAttribute("DeliveryInfo2To");
    localDeliveryInfo2.setOrder(str2);
    localDeliveryInfo2.setChannelName("MessageCenter");
    localDeliveryInfo2.setMaxDelay(-1);
    localDeliveryInfo2.setSuspended(true);
    Properties localProperties2 = new Properties();
    localProperties2.setProperty("AlertType", "StockPortfolio");
    localProperties2.setProperty("SECUREUSER", localSecureUser.getXML());
    localProperties2.setProperty("PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
    localDeliveryInfo2.setProperties(localProperties2);
    localDeliveryInfo3.setChannelName("Email");
    localDeliveryInfo3.setOrder(str3);
    localDeliveryInfo3.setMaxDelay(-1);
    localDeliveryInfo3.setSuspended(true);
    localDeliveryInfo4.setChannelName("Email");
    localDeliveryInfo4.setOrder(str4);
    localDeliveryInfo4.setMaxDelay(-1);
    localDeliveryInfo4.setSuspended(true);
    Properties localProperties3 = new Properties();
    localProperties3.setProperty("AlertType", "StockPortfolio");
    localProperties3.setProperty("to", str5);
    localProperties3.setProperty("from", "alert@bank.com");
    localDeliveryInfo3.setProperties(localProperties3);
    Properties localProperties4 = new Properties();
    localProperties4.setProperty("AlertType", "StockPortfolio");
    localProperties4.setProperty("to", str6);
    localProperties4.setProperty("from", "alert@bank.com");
    localDeliveryInfo4.setProperties(localProperties4);
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.AddAlertStock
 * JD-Core Version:    0.7.0.1
 */