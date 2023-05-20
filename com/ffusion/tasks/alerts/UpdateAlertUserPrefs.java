package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateAlertUserPrefs
  extends ModifyAlert
{
  public static final String FROMEMAIL = "alerts@bank.com";
  private String lb = "alerts@bank.com";
  protected String secureAlertOrder;
  protected String deliveryInfo1Order;
  protected String deliveryInfo2Order;
  protected String deliveryInfo1To;
  protected String deliveryInfo2To;
  private String k8;
  private String k7;
  private String lc;
  private String k9;
  private String la;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = UpdateAlertUserPrefs(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.processFlag = true;
    initUserPrefs(paramHttpSession);
    return true;
  }
  
  protected String UpdateAlertUserPrefs(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag) {
        str = updateAlerts(paramHttpServletRequest, paramHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String updateAlerts(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    com.ffusion.beans.alerts.Alerts localAlerts1 = (com.ffusion.beans.alerts.Alerts)paramHttpSession.getAttribute(this.alertsName);
    HashMap localHashMap = null;
    if (localAlerts != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    str = this.successURL;
    try
    {
      if (!updateUserPrefs(paramHttpSession)) {
        return str;
      }
      Iterator localIterator = localAlerts1.iterator();
      ArrayList localArrayList = new ArrayList();
      while (localIterator.hasNext())
      {
        Alert localAlert1 = new Alert();
        Alert localAlert2 = (Alert)localIterator.next();
        updateAlert(localAlert2, paramHttpSession);
        localAlert1 = com.ffusion.csil.core.Alerts.modifyAlert(localSecureUser, localAlert2, localHashMap);
        localArrayList.add(localAlert1);
      }
      paramHttpSession.setAttribute(this.alertsName, localArrayList);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      str = this.serviceErrorURL;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, Integer.toString(localSecureUser.getProfileID()));
    localHistoryTracker.detectChange(User.BEAN_NAME, "SECURE_ALERT", this.k8, this.secureAlertOrder, (ILocalizable)null);
    localHistoryTracker.detectChange(User.BEAN_NAME, "DELIVERY_INFO_1", this.k7, this.deliveryInfo1Order, (ILocalizable)null);
    localHistoryTracker.detectChange(User.BEAN_NAME, "DELIVERY_INFO_2", this.lc, this.deliveryInfo2Order, (ILocalizable)null);
    localHistoryTracker.detectChange(User.BEAN_NAME, "DELIVERY_INFO_1_TO", this.k9, this.deliveryInfo1To, (ILocalizable)null);
    localHistoryTracker.detectChange(User.BEAN_NAME, "DELIVERY_INFO_2_TO", this.la, this.deliveryInfo2To, (ILocalizable)null);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for UpdateAlertUserPrefs: " + localProfileException.toString());
    }
    str = this.successURL;
    return str;
  }
  
  protected boolean updateAlert(Alert paramAlert, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    User localUser = (User)paramHttpSession.getAttribute("User");
    DeliveryInfos localDeliveryInfos = paramAlert.getDeliveryInfosValue();
    int i = 0;
    int j = 0;
    int k = 0;
    Object localObject2;
    Object localObject3;
    for (int m = 0; m < localDeliveryInfos.size(); m++)
    {
      localObject2 = (DeliveryInfo)localDeliveryInfos.get(m);
      if (((DeliveryInfo)localObject2).getChannelName().equalsIgnoreCase("MessageCenter"))
      {
        i = 1;
        ((DeliveryInfo)localObject2).setOrder(this.secureAlertOrder);
      }
      if (((DeliveryInfo)localObject2).getChannelName().equalsIgnoreCase("Email")) {
        if (j == 0)
        {
          j = 1;
          ((DeliveryInfo)localObject2).setOrder(this.deliveryInfo1Order);
          localObject3 = ((DeliveryInfo)localObject2).getPropertiesValue();
          setProperty((Properties)localObject3, "to", this.deliveryInfo1To);
          setProperty((Properties)localObject3, "from", getEmailFromAddress());
          setProperty((Properties)localObject3, "PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
          ((DeliveryInfo)localObject2).setProperties((Properties)localObject3);
        }
        else
        {
          k = 1;
          ((DeliveryInfo)localObject2).setOrder(this.deliveryInfo2Order);
          localObject3 = ((DeliveryInfo)localObject2).getPropertiesValue();
          setProperty((Properties)localObject3, "to", this.deliveryInfo2To);
          setProperty((Properties)localObject3, "from", getEmailFromAddress());
          setProperty((Properties)localObject3, "PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
          ((DeliveryInfo)localObject2).setProperties((Properties)localObject3);
        }
      }
    }
    if ((i == 0) && (!this.secureAlertOrder.equalsIgnoreCase("0")))
    {
      localObject1 = new DeliveryInfo(this.locale);
      localDeliveryInfos.add(localObject1);
      ((DeliveryInfo)localObject1).setChannelName("MessageCenter");
      ((DeliveryInfo)localObject1).setOrder(this.secureAlertOrder);
      ((DeliveryInfo)localObject1).setMaxDelay(-1);
      ((DeliveryInfo)localObject1).setSuspended(false);
      localObject2 = new Properties();
      ((Properties)localObject2).setProperty("SECUREUSER", localSecureUser.getXML());
      ((Properties)localObject2).setProperty("PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
      ((Properties)localObject2).setProperty("secure", Boolean.TRUE.toString());
      ((DeliveryInfo)localObject1).setProperties((Properties)localObject2);
    }
    if ((j == 0) && (!this.deliveryInfo1Order.equalsIgnoreCase("0")))
    {
      localObject1 = new DeliveryInfo(this.locale);
      localDeliveryInfos.add(localObject1);
      ((DeliveryInfo)localObject1).setChannelName("Email");
      ((DeliveryInfo)localObject1).setOrder(this.deliveryInfo1Order);
      ((DeliveryInfo)localObject1).setMaxDelay(-1);
      ((DeliveryInfo)localObject1).setSuspended(false);
      localObject2 = new Properties();
      ((Properties)localObject2).setProperty("SECUREUSER", localSecureUser.getXML());
      ((Properties)localObject2).setProperty("PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
      ((Properties)localObject2).setProperty("to", this.deliveryInfo1To);
      ((Properties)localObject2).setProperty("from", getEmailFromAddress());
      ((DeliveryInfo)localObject1).setProperties((Properties)localObject2);
    }
    if ((k == 0) && (!this.deliveryInfo2Order.equalsIgnoreCase("0")))
    {
      localObject1 = new DeliveryInfo(this.locale);
      localDeliveryInfos.add(localObject1);
      ((DeliveryInfo)localObject1).setChannelName("Email");
      ((DeliveryInfo)localObject1).setOrder(this.deliveryInfo2Order);
      ((DeliveryInfo)localObject1).setMaxDelay(-1);
      ((DeliveryInfo)localObject1).setSuspended(false);
      localObject2 = new Properties();
      ((Properties)localObject2).setProperty("SECUREUSER", localSecureUser.getXML());
      ((Properties)localObject2).setProperty("PREFERREDLANGUAGE", "" + localUser.getPreferredLanguage());
      ((Properties)localObject2).setProperty("to", this.deliveryInfo2To);
      ((Properties)localObject2).setProperty("from", getEmailFromAddress());
      ((DeliveryInfo)localObject1).setProperties((Properties)localObject2);
    }
    Object localObject1 = new DeliveryInfos(this.locale);
    for (int n = 0; n < localDeliveryInfos.size(); n++)
    {
      localObject3 = (DeliveryInfo)localDeliveryInfos.get(n);
      if (((!((DeliveryInfo)localObject3).getChannelName().equalsIgnoreCase("Email")) && (!((DeliveryInfo)localObject3).getChannelName().equalsIgnoreCase("MessageCenter"))) || (!((DeliveryInfo)localObject3).getOrder().equalsIgnoreCase("0"))) {
        ((DeliveryInfos)localObject1).add(localObject3);
      }
    }
    ((DeliveryInfos)localObject1).setSortedBy("ORDER");
    paramAlert.setDeliveryInfos((DeliveryInfos)localObject1);
    return true;
  }
  
  protected boolean updateUserPrefs(HttpSession paramHttpSession)
  {
    boolean bool = false;
    String str1 = (String)paramHttpSession.getAttribute("SecureAlert");
    String str2 = (String)paramHttpSession.getAttribute("DeliveryInfo1");
    String str3 = (String)paramHttpSession.getAttribute("DeliveryInfo2");
    String str4 = (String)paramHttpSession.getAttribute("DeliveryInfo1To");
    String str5 = (String)paramHttpSession.getAttribute("DeliveryInfo2To");
    if ((str1.equalsIgnoreCase(this.secureAlertOrder)) && (str2.equalsIgnoreCase(this.deliveryInfo1Order)) && (str3.equalsIgnoreCase(this.deliveryInfo2Order)) && (str4.equalsIgnoreCase(this.deliveryInfo1To)) && (str5.equalsIgnoreCase(this.deliveryInfo2To)))
    {
      bool = false;
    }
    else
    {
      this.secureAlertOrder = str1;
      this.deliveryInfo1Order = str2;
      this.deliveryInfo2Order = str3;
      this.deliveryInfo1To = str4;
      this.deliveryInfo2To = str5;
      bool = true;
    }
    return bool;
  }
  
  protected void initUserPrefs(HttpSession paramHttpSession)
  {
    this.secureAlertOrder = ((String)paramHttpSession.getAttribute("SecureAlert"));
    this.deliveryInfo1Order = ((String)paramHttpSession.getAttribute("DeliveryInfo1"));
    this.deliveryInfo2Order = ((String)paramHttpSession.getAttribute("DeliveryInfo2"));
    this.deliveryInfo1To = ((String)paramHttpSession.getAttribute("DeliveryInfo1To"));
    this.deliveryInfo2To = ((String)paramHttpSession.getAttribute("DeliveryInfo2To"));
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    return true;
  }
  
  public static void setProperty(Properties paramProperties, String paramString1, String paramString2)
  {
    if (paramProperties.containsKey(paramString1)) {
      paramProperties.remove(paramString1);
    }
    String str1 = paramString1.toLowerCase();
    if (paramProperties.containsKey(str1)) {
      paramProperties.remove(str1);
    }
    String str2 = paramString1.toUpperCase();
    if (paramProperties.containsKey(str2)) {
      paramProperties.remove(str2);
    }
    paramProperties.setProperty(paramString1, paramString2);
  }
  
  public void setOldSecureAlert(String paramString)
  {
    this.k8 = paramString;
  }
  
  public void setOldDeliveryInfo1(String paramString)
  {
    this.k7 = paramString;
  }
  
  public void setOldDeliveryInfo2(String paramString)
  {
    this.lc = paramString;
  }
  
  public void setOldDeliveryInfo1To(String paramString)
  {
    this.k9 = paramString;
  }
  
  public void setOldDeliveryInfo2To(String paramString)
  {
    this.la = paramString;
  }
  
  public String getEmailFromAddress()
  {
    return this.lb;
  }
  
  public void setEmailFromAddress(String paramString)
  {
    this.lb = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.UpdateAlertUserPrefs
 * JD-Core Version:    0.7.0.1
 */