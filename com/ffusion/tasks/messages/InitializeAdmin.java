package com.ffusion.tasks.messages;

import com.ffusion.services.MessagingAdmin2;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitializeAdmin
  extends InitService
  implements Task
{
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
        MessagingAdmin2 localMessagingAdmin2 = (MessagingAdmin2)localClass.newInstance();
        if ((this.error = localMessagingAdmin2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute("com.ffusion.services.MessagingAdmin3", localMessagingAdmin2);
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.InitializeAdmin
 * JD-Core Version:    0.7.0.1
 */