package com.ffusion.tasks.bptw;

import com.ffusion.services.bptw.BptwService;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitializeBptwService
  extends InitService
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (localHttpSession.getAttribute("BptwService") != null) {
      return this.successURL;
    }
    if (this.className == null)
    {
      this.error = 17000;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        BptwService localBptwService = (BptwService)localClass.newInstance();
        if ((this.error = localBptwService.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          localHttpSession.setAttribute("BptwService", localBptwService);
          str = this.successURL;
        }
      }
      catch (Exception localException)
      {
        this.error = 17001;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.InitializeBptwService
 * JD-Core Version:    0.7.0.1
 */