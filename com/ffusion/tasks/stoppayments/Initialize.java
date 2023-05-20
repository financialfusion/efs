package com.ffusion.tasks.stoppayments;

import com.ffusion.services.Stops;
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
  private String kY = "com.ffusion.services.Stops";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    if (this.className == null)
    {
      this.error = 13400;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        Stops localStops = (Stops)localClass.newInstance();
        if ((this.error = localStops.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute(this.kY, localStops);
        }
      }
      catch (Exception localException)
      {
        this.error = 13401;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.Initialize
 * JD-Core Version:    0.7.0.1
 */