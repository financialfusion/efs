package com.ffusion.tasks.messages;

import com.ffusion.services.Messaging2;
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
  private String kB = "com.ffusion.services.Messaging3";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    if (this.className == null)
    {
      this.error = 8008;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        Messaging2 localMessaging2 = (Messaging2)localClass.newInstance();
        if ((this.error = localMessaging2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute(this.kB, localMessaging2);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 8009;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setMessagingServiceName(String paramString)
  {
    this.kB = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.Initialize
 * JD-Core Version:    0.7.0.1
 */