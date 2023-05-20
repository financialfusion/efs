package com.ffusion.tasks.enroll;

import com.ffusion.services.Enroll2;
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
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    if (this.className == null)
    {
      this.error = 7030;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        Enroll2 localEnroll2 = (Enroll2)localClass.newInstance();
        if ((this.error = localEnroll2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute("com.ffusion.services.Enroll", localEnroll2);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 7031;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.enroll.Initialize
 * JD-Core Version:    0.7.0.1
 */