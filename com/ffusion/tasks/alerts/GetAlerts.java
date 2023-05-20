package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAlerts
  extends BaseTask
  implements Task
{
  protected String serviceName = "com.ffusion.services.Alerts";
  protected String alertsName = "Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)localHttpSession.getAttribute(this.serviceName);
    String str;
    if (getAlerts(localHttpSession, localAlerts)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getAlerts(HttpSession paramHttpSession, com.ffusion.services.Alerts paramAlerts)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    com.ffusion.beans.alerts.Alerts localAlerts = new com.ffusion.beans.alerts.Alerts(localLocale);
    HashMap localHashMap = new HashMap();
    if (paramAlerts != null) {
      localHashMap.put("SERVICE", paramAlerts);
    }
    localHashMap.put("ALERTSLIST", localAlerts);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localAlerts = com.ffusion.csil.core.Alerts.getAlerts(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.alertsName, localAlerts);
    }
    return this.error == 0;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
  
  public final void setAlertsName(String paramString)
  {
    this.alertsName = paramString;
  }
  
  public final String getAlertsName()
  {
    return this.alertsName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.GetAlerts
 * JD-Core Version:    0.7.0.1
 */