package com.ffusion.tasks.portal;

import com.ffusion.services.PortalContentProvider;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ContentProviderInitService
  extends InitService
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    PortalContentProvider localPortalContentProvider = null;
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
        localPortalContentProvider = (PortalContentProvider)localClass.newInstance();
        HttpSession localHttpSession = paramHttpServletRequest.getSession();
        localHttpSession.setAttribute("PortalContentProvider", localPortalContentProvider);
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
 * Qualified Name:     com.ffusion.tasks.portal.ContentProviderInitService
 * JD-Core Version:    0.7.0.1
 */