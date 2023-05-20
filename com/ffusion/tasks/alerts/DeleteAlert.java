package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAlert
  extends BaseTask
  implements Task
{
  protected String serviceName = "com.ffusion.services.Alerts";
  protected String alertName = "Alert";
  protected String alertsName = "Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)localHttpSession.getAttribute(this.serviceName);
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    Alert localAlert = (Alert)localHttpSession.getAttribute(this.alertName);
    if (localAlert == null)
    {
      this.error = 19001;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap = null;
      if (localAlerts != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localAlerts);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        localAlert = com.ffusion.csil.core.Alerts.deleteAlert(localSecureUser, localAlert, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        if ((this.alertsName == null) || (this.alertsName.trim().length() == 0)) {
          this.alertsName = "Alerts";
        }
        com.ffusion.beans.alerts.Alerts localAlerts1 = (com.ffusion.beans.alerts.Alerts)localHttpSession.getAttribute(this.alertsName);
        if (localAlerts1 == null)
        {
          this.error = 19002;
          str = this.taskErrorURL;
        }
        else
        {
          localAlerts1.remove(localAlert);
        }
        str = this.successURL;
      }
    }
    return str;
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
  
  public final void setAlertName(String paramString)
  {
    this.alertName = paramString;
  }
  
  public final String getAlertName()
  {
    return this.alertName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.DeleteAlert
 * JD-Core Version:    0.7.0.1
 */