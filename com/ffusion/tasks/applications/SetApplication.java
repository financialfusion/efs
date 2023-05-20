package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Applications;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetApplication
  extends BaseTask
  implements Task
{
  protected String appID = "-1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Applications localApplications = (Applications)localHttpSession.getAttribute("Applications");
    if (localApplications == null)
    {
      this.error = 7200;
      return this.taskErrorURL;
    }
    Application localApplication = localApplications.getByID(this.appID);
    if (localApplication != null)
    {
      localHttpSession.setAttribute("Application", localApplication);
      return this.successURL;
    }
    this.error = 7201;
    return this.taskErrorURL;
  }
  
  public void setAppID(String paramString)
  {
    this.appID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SetApplication
 * JD-Core Version:    0.7.0.1
 */