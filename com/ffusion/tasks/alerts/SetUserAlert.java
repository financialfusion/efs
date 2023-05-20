package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetUserAlert
  extends BaseTask
  implements Task
{
  private String k6;
  protected String alertName = "Alert";
  protected String alertsName = "Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = setAlert(localHttpSession);
    if (this.error != 0) {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public int setAlert(HttpSession paramHttpSession)
  {
    int i = 0;
    UserAlerts localUserAlerts = (UserAlerts)paramHttpSession.getAttribute(this.alertsName);
    if (localUserAlerts != null)
    {
      UserAlert localUserAlert = localUserAlerts.getById(this.k6);
      if (localUserAlert != null) {
        paramHttpSession.setAttribute(this.alertName, localUserAlert);
      } else {
        i = 19200;
      }
    }
    else
    {
      i = 19002;
    }
    return i;
  }
  
  public final void setID(String paramString)
  {
    this.k6 = paramString;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.SetUserAlert
 * JD-Core Version:    0.7.0.1
 */