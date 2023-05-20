package com.ffusion.services.demo;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.alerts.LogMessage;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.util.XMLHandler;
import java.io.PrintStream;
import java.util.Iterator;

public class AlertsDemo
  extends Service
  implements com.ffusion.services.Alerts
{
  protected com.ffusion.beans.alerts.Alerts alerts;
  protected LogMessages logMessages;
  
  public void setUserName(String paramString) {}
  
  public void setPassword(String paramString) {}
  
  public int getAlerts(com.ffusion.beans.alerts.Alerts paramAlerts)
  {
    if ((this.alerts == null) || (this.alerts.size() == 0))
    {
      this.alerts = new com.ffusion.beans.alerts.Alerts();
      if ((paramAlerts != null) && (paramAlerts.getLocale() != null)) {
        this.alerts.setLocale(paramAlerts.getLocale());
      }
      a();
    }
    if (paramAlerts == null) {
      paramAlerts = new com.ffusion.beans.alerts.Alerts();
    }
    paramAlerts.clear();
    Iterator localIterator = this.alerts.iterator();
    while (localIterator.hasNext())
    {
      Alert localAlert = (Alert)localIterator.next();
      paramAlerts.add(localAlert);
    }
    return 0;
  }
  
  public String getSettings()
  {
    return "";
  }
  
  public int addAlert(Alert paramAlert)
  {
    if ((this.alerts == null) || (this.alerts.size() == 0)) {
      getAlerts(null);
    }
    this.alerts.setSortedBy("ID");
    paramAlert.setID(String.valueOf(jdMethod_if()));
    DeliveryInfos localDeliveryInfos = paramAlert.getDeliveryInfosValue();
    DeliveryInfo localDeliveryInfo = (DeliveryInfo)localDeliveryInfos.get(0);
    localDeliveryInfo.setSuspended(false);
    paramAlert.setDeliveryInfos(localDeliveryInfos);
    if (paramAlert.getTypeValue() != 0) {
      this.alerts.add(paramAlert);
    }
    return 0;
  }
  
  public int deleteAlert(Alert paramAlert)
  {
    if ((this.alerts == null) || (this.alerts.size() == 0)) {
      getAlerts(null);
    }
    this.alerts.remove(paramAlert);
    return 0;
  }
  
  public int getLogMessages(LogMessages paramLogMessages)
  {
    if ((this.logMessages == null) || (this.logMessages.size() == 0))
    {
      this.logMessages = new LogMessages();
      if ((paramLogMessages != null) && (paramLogMessages.getLocale() != null)) {
        this.logMessages.setLocale(paramLogMessages.getLocale());
      }
      a();
    }
    if (paramLogMessages == null) {
      paramLogMessages = new LogMessages();
    }
    paramLogMessages.clear();
    Iterator localIterator = this.logMessages.iterator();
    while (localIterator.hasNext())
    {
      LogMessage localLogMessage = (LogMessage)localIterator.next();
      paramLogMessages.add(localLogMessage);
    }
    return 0;
  }
  
  public int modifyAlert(Alert paramAlert)
  {
    if ((this.alerts == null) || (this.alerts.size() == 0)) {
      getAlerts(null);
    }
    Alert localAlert = this.alerts.getByID(paramAlert.getID());
    localAlert.set(paramAlert);
    return 0;
  }
  
  public int sendAlertToUser(String paramString1, String paramString2)
  {
    return 0;
  }
  
  private String jdMethod_if()
  {
    int i = 0;
    for (int j = 0; j < this.alerts.size(); j++)
    {
      Alert localAlert = (Alert)this.alerts.get(j);
      if (i < Integer.parseInt(localAlert.getID())) {
        i = Integer.parseInt(localAlert.getID());
      }
    }
    return String.valueOf(i + 1);
  }
  
  private void a()
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  protected class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      try
      {
        if ((paramString.equals("ALERTS")) && (AlertsDemo.this.alerts != null)) {
          AlertsDemo.this.alerts.continueXMLParsing(getHandler());
        }
        if ((paramString.equals("LOGMESSAGES")) && (AlertsDemo.this.logMessages != null)) {
          AlertsDemo.this.logMessages.continueXMLParsing(getHandler());
        } else {
          super.startElement(paramString);
        }
      }
      catch (Exception localException)
      {
        System.out.println("Exception: " + localException);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.AlertsDemo
 * JD-Core Version:    0.7.0.1
 */