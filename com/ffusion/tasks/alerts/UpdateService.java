package com.ffusion.tasks.alerts;

import com.ffusion.services.Alerts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateService
  extends BaseTask
  implements Task
{
  private String k2;
  private String k0;
  private String k1;
  protected String serviceName = "com.ffusion.services.Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Alerts localAlerts = (Alerts)localHttpSession.getAttribute(this.serviceName);
    String str = null;
    if (localAlerts != null)
    {
      if (this.k2 != null) {
        localAlerts.setUserName(this.k2);
      }
      if (this.k0 != null) {
        localAlerts.setPassword(this.k0);
      }
      if (this.k1 != null) {
        localAlerts.setSettings(this.k1);
      }
      str = this.successURL;
    }
    else
    {
      this.error = 19000;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this.k2 = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.k0 = paramString;
  }
  
  public void setSettings(String paramString)
  {
    this.k1 = paramString;
  }
  
  public String getUserName()
  {
    return this.k2;
  }
  
  public String getPassword()
  {
    return this.k0;
  }
  
  public String getSettings()
  {
    return this.k1;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.UpdateService
 * JD-Core Version:    0.7.0.1
 */