package com.ffusion.tasks.alerts;

import com.ffusion.services.Alerts;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Initialize
  extends InitService
  implements Task
{
  private String lm = "com.ffusion.services.Alerts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    if (this.className == null)
    {
      this.error = 19300;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        Alerts localAlerts = (Alerts)localClass.newInstance();
        if ((this.error = localAlerts.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute(this.lm, localAlerts);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 19301;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setServiceName(String paramString)
  {
    this.lm = paramString;
  }
  
  public String getServiceName()
  {
    return this.lm;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.Initialize
 * JD-Core Version:    0.7.0.1
 */