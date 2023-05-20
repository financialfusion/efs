package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAlert
  extends ModifyAlert
{
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
      str = processAddAlert(paramHttpServletRequest, localHttpSession);
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
    return this.error == 0;
  }
  
  protected String processAddAlert(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = addAlert(paramHttpSession);
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
  
  protected String addAlert(HttpSession paramHttpSession)
  {
    String str1 = null;
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str2 = ((DeliveryInfo)this.deliveryInfos.get(0)).getPropertiesValue().getProperty("AlertType", "");
    User localUser = (User)paramHttpSession.getAttribute("User");
    int i = 0;
    int j = 0;
    int k = Integer.parseInt((String)paramHttpSession.getAttribute("SecureAlert"));
    int m = Integer.parseInt((String)paramHttpSession.getAttribute("DeliveryInfo1"));
    int n = Integer.parseInt((String)paramHttpSession.getAttribute("DeliveryInfo2"));
    if ((k == 0) && (m == 0) && (n == 0))
    {
      this.error = 19004;
      return this.taskErrorURL;
    }
    Object localObject;
    for (int i1 = 0; i1 < this.deliveryInfos.size(); i1++)
    {
      localObject = (DeliveryInfo)this.deliveryInfos.get(i1);
      if (((((DeliveryInfo)localObject).getChannelName() == null) || (((DeliveryInfo)localObject).getChannelName().length() == 0)) && ((((DeliveryInfo)localObject).getPropertiesString() == null) || (((DeliveryInfo)localObject).getPropertiesString().length() == 0)))
      {
        if (i1 < this.deliveryInfos.size() - 1)
        {
          this.deliveryInfos.remove(i1);
          i1--;
        }
        else
        {
          this.deliveryInfos.remove(i1);
        }
      }
      else
      {
        if (str2.length() > 0)
        {
          ((DeliveryInfo)localObject).setPropertyKey("AlertType");
          ((DeliveryInfo)localObject).setPropertyValue(str2);
        }
        ((DeliveryInfo)localObject).setPropertyKey("SECUREUSER");
        ((DeliveryInfo)localObject).setPropertyValue(localSecureUser.getXML());
        ((DeliveryInfo)localObject).setPropertyKey("PREFERREDLANGUAGE");
        ((DeliveryInfo)localObject).setPropertyValue(localUser.getPreferredLanguage());
        if (((DeliveryInfo)localObject).getOrderValue() < j) {
          i = 1;
        }
        j = ((DeliveryInfo)localObject).getOrderValue();
      }
    }
    if (i != 0) {
      this.deliveryInfos.setSortedBy("ORDER");
    }
    HashMap localHashMap = null;
    if (localAlerts != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
    }
    this.error = 0;
    try
    {
      localObject = new Alert();
      localObject = com.ffusion.csil.core.Alerts.addAlert(localSecureUser, this, localHashMap);
      set((Alert)localObject);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      Alert localAlert = new Alert(this.locale);
      localAlert.set(this);
      paramHttpSession.setAttribute(this.alertName, localAlert);
      com.ffusion.beans.alerts.Alerts localAlerts1 = (com.ffusion.beans.alerts.Alerts)paramHttpSession.getAttribute(this.alertsName);
      if (localAlerts1 != null) {
        localAlerts1.add(localAlert);
      }
      str1 = this.successURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.AddAlert
 * JD-Core Version:    0.7.0.1
 */