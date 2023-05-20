package com.ffusion.tasks.portal;

import com.ffusion.services.PortalData2;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DataInitialize
  extends InitService
  implements Task
{
  public static final String PORTAL_DATA_INIT_SERVICE = "PortalDataInitService";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    PortalData2 localPortalData2 = null;
    String str = this.successURL;
    if (this.className.trim().equals(""))
    {
      this.error = 9001;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        Class localClass = Class.forName(this.className);
        localPortalData2 = (PortalData2)localClass.newInstance();
        if ((this.error = localPortalData2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          HttpSession localHttpSession = paramHttpServletRequest.getSession();
          localHttpSession.setAttribute("PortalData", localPortalData2);
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        this.error = 9000;
        str = this.taskErrorURL;
      }
      catch (Exception localException)
      {
        this.error = 9002;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.DataInitialize
 * JD-Core Version:    0.7.0.1
 */