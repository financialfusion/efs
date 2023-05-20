package com.ffusion.alert.plugins;

import com.ffusion.alert.factory.AEDeliveryInfoFactory;
import com.ffusion.alert.interfaces.AEApplicationInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEAlertEngine;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.ejb.ICSILEJB;
import com.ffusion.services.PortalData3;
import com.ffusion.util.HfnEncrypt;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLUtil;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class StockPortfolio
  implements IAEChannel, AlertChannelConstants
{
  protected Properties initProps;
  protected boolean _threshold = false;
  protected AEApplicationInfo appInfo;
  protected IAEAlertEngine engine;
  protected PrintWriter writer;
  protected PortalData3 portalData;
  protected boolean debugService;
  protected static final String THIS_PLUGIN_NAME = "StockPortfolio";
  protected String portalDataClassName = "";
  protected String portalDataInitURL = "";
  protected static final String APP_INFO_USERNAME = "APP_INFO_USERNAME";
  protected static final String APP_INFO_PASSWORD = "APP_INFO_PASSWORD";
  protected static final String PORTAL_DATA_CLASS_NAME = "PORTAL_DATA_CLASS_NAME";
  protected static final String PORTAL_DATA_INIT_URL = "PORTAL_DATA_INIT_URL";
  protected static final String THRESHOLD_SET = "THRESHOLD_SET";
  public static final String ONETIME = "ONETIME";
  public static final String CRITERIA = "Criteria";
  public static final String AMOUNT = "Amount";
  public static final String SUBJECT = "subject";
  protected boolean b_useCsilEJB = false;
  protected ICSILEJB csilejb;
  
  public void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception
  {
    this.engine = paramIAEAlertEngine;
    this.writer = paramPrintWriter;
    this.initProps = paramProperties;
    this.debugService = Boolean.valueOf(paramProperties.getProperty("DEBUG")).booleanValue();
    AlertUtil.logEntry("Initializing StockPortfolio plugin...", this.debugService, paramPrintWriter);
    this._threshold = Boolean.valueOf(paramProperties.getProperty("THRESHOLD_SET")).booleanValue();
    try
    {
      String str1 = HfnEncrypt.decryptHexEncode(paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
      if (str1 == null) {
        str1 = paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts");
      }
      this.appInfo = new AEApplicationInfo(paramProperties.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), str1);
    }
    catch (Exception localException1)
    {
      this.appInfo = new AEApplicationInfo(paramProperties.getProperty("APP_INFO_USERNAME", "ApplicationAlerts"), paramProperties.getProperty("APP_INFO_PASSWORD", "ApplicationAlerts"));
    }
    int i = 0;
    this.portalDataClassName = paramProperties.getProperty("PORTAL_DATA_CLASS_NAME");
    this.portalDataInitURL = paramProperties.getProperty("PORTAL_DATA_INIT_URL");
    try
    {
      String str2 = paramProperties.getProperty("USECSIL", "");
      if ((str2 != null) && (str2.equalsIgnoreCase("True")))
      {
        this.b_useCsilEJB = true;
        this.csilejb = AlertUtil.connectToCSILEJB(paramProperties);
      }
    }
    catch (Exception localException2)
    {
      if (this.debugService) {
        localException2.printStackTrace(paramPrintWriter);
      }
      return;
    }
    try
    {
      if ((this.portalDataClassName != null) && (this.portalDataClassName.length() > 0) && (this.portalDataInitURL != null) && (this.portalDataInitURL.length() > 0))
      {
        Class localClass = Class.forName(this.portalDataClassName);
        PortalData3 localPortalData3 = (PortalData3)localClass.newInstance();
        i = localPortalData3.initialize(this.portalDataInitURL);
        if (i != 0)
        {
          AlertUtil.logEntry("ERROR: Unable to initialize PortalData service.", this.debugService, paramPrintWriter);
          return;
        }
        this.portalData = localPortalData3;
        AlertUtil.logEntry("Successfully initialized PortalData service.", this.debugService, paramPrintWriter);
      }
    }
    catch (Exception localException3)
    {
      if (this.debugService) {
        localException3.printStackTrace(paramPrintWriter);
      }
    }
  }
  
  public int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties)
  {
    AlertUtil.logEntry("Processing Alert...", this.debugService, this.writer);
    int i = 0;
    int j = 0;
    Properties localProperties = null;
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = paramIAEAlertInstance.getDeliveryInfo();
    Object localObject2;
    for (int k = 0; k < arrayOfIAEDeliveryInfo.length; k++)
    {
      localObject2 = arrayOfIAEDeliveryInfo[k];
      if (((IAEDeliveryInfo)localObject2).getDeliveryChannelName().equals(getPluginName()))
      {
        localProperties = ((IAEDeliveryInfo)localObject2).getDeliveryProperties();
        j = k;
        break;
      }
    }
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    Object localObject9;
    Object localObject10;
    Object localObject1;
    if (this._threshold)
    {
      AlertUtil.logEntry("Processing Alert... ejb process", this.debugService, this.writer);
      if (localProperties != null)
      {
        localObject3 = new SecureUser();
        ((SecureUser)localObject3).setXML(localProperties.getProperty("SECUREUSER"));
        localObject4 = localProperties.getProperty("PREFERREDLANGUAGE");
        if ((localObject4 != null) && (((String)localObject4).trim().length() > 0)) {
          ((SecureUser)localObject3).setLocale((String)localObject4);
        }
        localObject5 = ((SecureUser)localObject3).getLocale();
        localObject6 = convertAlert(paramIAEAlertInstance, (Locale)localObject5);
        localObject7 = ((Alert)localObject6).getStocks();
        localObject8 = new AlertStocks();
        localObject9 = ((AlertStocks)localObject7).iterator();
        localObject10 = new StringBuffer();
        int m = 0;
        while (((Iterator)localObject9).hasNext())
        {
          localObject2 = (AlertStock)((Iterator)localObject9).next();
          localObject1 = ((AlertStock)localObject2).getSymbol();
          int n = 0;
          Stock localStock = null;
          try
          {
            if (this.csilejb == null) {
              this.csilejb = AlertUtil.connectToCSILEJB(this.initProps);
            }
            HashMap localHashMap = new HashMap();
            if (this.portalData != null) {
              localHashMap.put("SERVICE", this.portalData);
            }
            localStock = this.csilejb.getStock((SecureUser)localObject3, (String)localObject1, localHashMap);
            if ((localStock == null) || (localStock.getLastSalePrice() == null))
            {
              Object[] arrayOfObject = { localObject1 };
              ((StringBuffer)localObject10).append(MessageFormat.format(ResourceUtil.getString("bodyStock", "com.ffusion.beans.alerts.resources", (Locale)localObject5), arrayOfObject));
              m = 1;
              continue;
            }
          }
          catch (Exception localException3)
          {
            if (this.debugService) {
              localException3.printStackTrace(this.writer);
            }
          }
          if ((localStock != null) && (localStock.getLastSalePrice() != null))
          {
            double d1 = ((AlertStock)localObject2).getAmountValue();
            String str1 = ((AlertStock)localObject2).getCriteria();
            double d2 = Double.parseDouble(localStock.getLastSalePrice());
            if ((str1.equalsIgnoreCase("More Than")) && (d1 < d2))
            {
              m = 1;
              n = 1;
              ((StringBuffer)localObject10).append("The last price of stock " + (String)localObject1 + " is $" + d2 + ", higher than $" + d1 + ".\n");
            }
            else if ((str1.equalsIgnoreCase("Less Than")) && (d1 > d2))
            {
              m = 1;
              n = 1;
              ((StringBuffer)localObject10).append("The last price of stock " + (String)localObject1 + " is $" + d2 + ", below $" + d1 + ".\n");
            }
            else if (str1.equalsIgnoreCase("ANY AMOUNT"))
            {
              m = 1;
              ((StringBuffer)localObject10).append("The last price of stock " + (String)localObject1 + " is $" + d2 + ".\n");
              n = 1;
            }
            else
            {
              n = 0;
            }
            if (n != 0)
            {
              String str2 = ((AlertStock)localObject2).getOnetime();
              if ((str2 != null) && (str2.length() != 0) && (Boolean.valueOf(str2).booleanValue() == true)) {
                ((AlertStocks)localObject8).add(localObject2);
              }
            }
          }
        }
        Object localObject11;
        if (m != 0)
        {
          localObject11 = new StringBuffer();
          ((StringBuffer)localObject11).append("Your stock alert");
          AlertUtil.setProperty(paramIAEAlertInstance, "subject", ((StringBuffer)localObject11).toString());
          AlertUtil.setProperty(paramIAEAlertInstance, "body", ((StringBuffer)localObject10).toString());
          createAlert(paramIAEAlertInstance, ((StringBuffer)localObject10).toString(), j);
        }
        if (((AlertStocks)localObject8).size() > 0)
        {
          localObject11 = ((AlertStocks)localObject8).iterator();
          while (((Iterator)localObject11).hasNext())
          {
            localObject2 = (AlertStock)((Iterator)localObject11).next();
            ((AlertStocks)localObject7).remove(localObject2);
          }
          ((Alert)localObject6).setStocks((AlertStocks)localObject7);
          try
          {
            modifyAlert(paramIAEAlertInstance.getUserId(), this.appInfo, (Alert)localObject6, this.engine, j);
          }
          catch (Exception localException2)
          {
            if (this.debugService) {
              localException2.printStackTrace(this.writer);
            }
          }
        }
      }
      return 0;
    }
    try
    {
      localObject1 = new SecureUser();
      ((SecureUser)localObject1).setXML(localProperties.getProperty("SECUREUSER"));
      localObject2 = localProperties.getProperty("PREFERREDLANGUAGE");
      if ((localObject2 != null) && (((String)localObject2).trim().length() > 0)) {
        ((SecureUser)localObject1).setLocale((String)localObject2);
      }
      localObject3 = ((SecureUser)localObject1).getLocale();
      localObject4 = new Stocks();
      if (this.csilejb == null) {
        this.csilejb = AlertUtil.connectToCSILEJB(this.initProps);
      }
      localObject5 = new HashMap();
      if (this.portalData != null) {
        ((HashMap)localObject5).put("SERVICE", this.portalData);
      }
      localObject6 = XMLUtil.XMLDecode(this.csilejb.getAdditionalData((SecureUser)localObject1, "PORTAL_SETTINGS", (HashMap)localObject5));
      if (((String)localObject6).length() > 0)
      {
        localObject7 = null;
        localObject7 = new StringReader((String)localObject6);
        ((Stocks)localObject4).startXMLParsing((Reader)localObject7);
        ((Reader)localObject7).close();
      }
      localObject7 = new StringBuffer();
      if (((Stocks)localObject4).size() == 0)
      {
        ((StringBuffer)localObject7).append("There are no stocks in your stock portfolio");
        createAlert(paramIAEAlertInstance, ((StringBuffer)localObject7).toString(), j);
      }
      else
      {
        localObject8 = this.csilejb.getStocks((SecureUser)localObject1, (Stocks)localObject4, (HashMap)localObject5);
        if ((localObject8 != null) && (((Stocks)localObject8).size() > 0))
        {
          localObject9 = paramIAEAlertInstance.getMessage();
          localObject10 = generateMessage((Stocks)localObject8, null, (Locale)localObject3);
          if ((localObject9 != null) && (((String)localObject9).length() != 0))
          {
            ((StringBuffer)localObject7).append((String)localObject9);
            ((StringBuffer)localObject7).append('\n');
            ((StringBuffer)localObject7).append((String)localObject10);
          }
          else
          {
            ((StringBuffer)localObject7).append((String)localObject10);
          }
          createAlert(paramIAEAlertInstance, ((StringBuffer)localObject7).toString(), j);
        }
        else
        {
          ((StringBuffer)localObject7).append("There are no stocks in your stock portfolio");
          createAlert(paramIAEAlertInstance, ((StringBuffer)localObject7).toString(), j);
        }
      }
    }
    catch (Exception localException1)
    {
      if (this.debugService) {
        localException1.printStackTrace(this.writer);
      }
    }
    return i;
  }
  
  public void stop()
  {
    AlertUtil.logEntry("Stopping StockPortfolio plugin...", this.debugService, this.writer);
  }
  
  protected void createAlert(IAEAlertDefinition paramIAEAlertDefinition, String paramString, int paramInt)
  {
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo1 = paramIAEAlertDefinition.getDeliveryInfo();
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo2 = new IAEDeliveryInfo[arrayOfIAEDeliveryInfo1.length - 1];
    int i = 0;
    for (int j = 0; i < arrayOfIAEDeliveryInfo1.length - 1; j++)
    {
      if (i == paramInt) {
        j++;
      }
      arrayOfIAEDeliveryInfo2[i] = arrayOfIAEDeliveryInfo1[j];
      i++;
    }
    try
    {
      Date localDate1 = new Date(System.currentTimeMillis() + 60000L);
      Date localDate2 = new Date(localDate1.getTime() + 300000L);
      long l = 60000L;
      TimeZone localTimeZone = TimeZone.getDefault();
      AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(localDate1, localDate2, l, 0, localTimeZone, true);
      int k = this.engine.createAlert(this.appInfo, paramIAEAlertDefinition.getUserId(), 0, 1, localAEScheduleInfo, arrayOfIAEDeliveryInfo2, paramString);
      AlertUtil.logEntry("New alertID: " + k, this.debugService, this.writer);
    }
    catch (Exception localException)
    {
      if (this.debugService) {
        localException.printStackTrace(this.writer);
      }
    }
  }
  
  protected String generateMessage(Stocks paramStocks, String paramString, Locale paramLocale)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramStocks.iterator();
    while (localIterator.hasNext())
    {
      Stock localStock = (Stock)localIterator.next();
      if (localStock != null) {
        localStringBuffer.append(localStock.getSymbol().toUpperCase() + "\t" + localStock.getLastSalePrice() + "\n");
      }
    }
    return localStringBuffer.toString();
  }
  
  protected Alert convertAlert(IAEAlertDefinition paramIAEAlertDefinition, Locale paramLocale)
  {
    Alert localAlert = new Alert(paramLocale);
    localAlert.setID(String.valueOf(paramIAEAlertDefinition.getId()));
    localAlert.setType(paramIAEAlertDefinition.getType());
    localAlert.setPriority(paramIAEAlertDefinition.getPriority());
    Date localDate1 = paramIAEAlertDefinition.getScheduleInfo().getStartDate();
    Calendar localCalendar1 = Calendar.getInstance(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone(), paramLocale);
    localCalendar1.setTime(localDate1);
    Date localDate2 = paramIAEAlertDefinition.getScheduleInfo().getEndDate();
    Calendar localCalendar2 = Calendar.getInstance(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone(), paramLocale);
    localCalendar2.setTime(localDate2);
    localAlert.setStartDate(new DateTime(localCalendar1, paramLocale));
    localAlert.setEndDate(new DateTime(localCalendar2, paramLocale));
    localAlert.setInterval(paramIAEAlertDefinition.getScheduleInfo().getInterval());
    localAlert.setTimeZone(paramIAEAlertDefinition.getScheduleInfo().getRegularTimeZone());
    localAlert.setAppInfo(paramIAEAlertDefinition.getApplicationName());
    int i = paramIAEAlertDefinition.getDeliveryInfo().length;
    DeliveryInfos localDeliveryInfos = new DeliveryInfos();
    for (int j = 0; j < i; j++)
    {
      DeliveryInfo localDeliveryInfo = new DeliveryInfo(localAlert.getLocale());
      localDeliveryInfo.setID(String.valueOf(j + 1));
      localDeliveryInfo.setChannelName(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryChannelName());
      localDeliveryInfo.setOrder(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryOrder());
      localDeliveryInfo.setMaxDelay(String.valueOf(paramIAEAlertDefinition.getDeliveryInfo()[j].getMaxDelay()));
      localDeliveryInfo.setProperties(paramIAEAlertDefinition.getDeliveryInfo()[j].getDeliveryProperties());
      if (paramIAEAlertDefinition.getDeliveryInfo()[j].isSuspended()) {
        localDeliveryInfo.setSuspended(true);
      }
      localDeliveryInfos.add(localDeliveryInfo);
    }
    localAlert.setDeliveryInfos(localDeliveryInfos);
    localAlert.setMessage(paramIAEAlertDefinition.getMessage());
    return localAlert;
  }
  
  protected void modifyAlert(String paramString, AEApplicationInfo paramAEApplicationInfo, Alert paramAlert, IAEAlertEngine paramIAEAlertEngine, int paramInt)
  {
    IAEAlertDefinition localIAEAlertDefinition = null;
    AEScheduleInfo localAEScheduleInfo = new AEScheduleInfo(paramAlert.getStartDateValue().getTime(), paramAlert.getEndDateValue().getTime(), paramAlert.getIntervalValue(), paramAlert.getIntervalType(), paramAlert.getRegularTimeZoneValue(), paramAlert.getRespectDST());
    Locale localLocale = paramAlert.getLocale();
    if (localLocale == null) {
      localLocale = new Locale("en", "us");
    }
    int i = paramAlert.getDeliveryInfosValue().size();
    Properties[] arrayOfProperties = new Properties[i];
    IAEDeliveryInfo[] arrayOfIAEDeliveryInfo = new IAEDeliveryInfo[i];
    for (int j = 0; j < i; j++)
    {
      DeliveryInfo localDeliveryInfo = (DeliveryInfo)paramAlert.getDeliveryInfosValue().get(j);
      arrayOfProperties[j] = new Properties();
      arrayOfProperties[j] = localDeliveryInfo.getPropertiesValue();
      arrayOfIAEDeliveryInfo[j] = AEDeliveryInfoFactory.create(localDeliveryInfo.getChannelName(), localDeliveryInfo.getOrderValue(), localDeliveryInfo.getMaxDelayValue(), localDeliveryInfo.getPropertiesValue());
      if (j == paramInt) {
        arrayOfIAEDeliveryInfo[j].setSuspended(false);
      } else {
        arrayOfIAEDeliveryInfo[j].setSuspended(true);
      }
    }
    try
    {
      localIAEAlertDefinition = paramIAEAlertEngine.updateAlert(paramAEApplicationInfo, Integer.parseInt(paramAlert.getID()), paramString, paramAlert.getTypeValue(), paramAlert.getPriorityValue(), localAEScheduleInfo, arrayOfIAEDeliveryInfo, paramAlert.getMessage());
    }
    catch (AEException localAEException)
    {
      if (this.debugService) {
        localAEException.printStackTrace(this.writer);
      }
    }
  }
  
  protected String getPluginName()
  {
    return "StockPortfolio";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.StockPortfolio
 * JD-Core Version:    0.7.0.1
 */