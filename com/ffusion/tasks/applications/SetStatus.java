package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetStatus
  extends BaseTask
  implements Task
{
  protected String statusID = "-1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Statuses localStatuses = (Statuses)localHttpSession.getAttribute("Statuses");
    if (localStatuses == null)
    {
      this.error = 7270;
      return this.taskErrorURL;
    }
    Status localStatus = localStatuses.getByID(this.statusID);
    if (localStatus != null)
    {
      localHttpSession.setAttribute("Status", localStatus);
      return this.successURL;
    }
    this.error = 7271;
    return this.taskErrorURL;
  }
  
  public void setStatusID(String paramString)
  {
    this.statusID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SetStatus
 * JD-Core Version:    0.7.0.1
 */