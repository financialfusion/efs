package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.Alerts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAlert
  extends BaseTask
  implements Task
{
  private String kZ;
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
    Alerts localAlerts = (Alerts)paramHttpSession.getAttribute(this.alertsName);
    if (localAlerts != null)
    {
      Alert localAlert = localAlerts.getByID(this.kZ);
      if (localAlert != null) {
        paramHttpSession.setAttribute(this.alertName, localAlert);
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
    this.kZ = paramString;
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
 * Qualified Name:     com.ffusion.tasks.alerts.SetAlert
 * JD-Core Version:    0.7.0.1
 */