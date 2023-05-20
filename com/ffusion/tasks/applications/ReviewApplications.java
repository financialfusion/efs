package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Applications;
import com.ffusion.beans.util.StringList;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReviewApplications
  extends BaseTask
  implements Task
{
  protected StringList appIDs = new StringList();
  
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
    if (this.appIDs.size() == 0)
    {
      this.error = 7211;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute("ReviewApplicationIDs", this.appIDs);
    return this.successURL;
  }
  
  public void setAppIDs(String paramString)
  {
    if (paramString.indexOf(",") != -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens()) {
        this.appIDs.add(localStringTokenizer.nextToken());
      }
    }
    else if (!paramString.equals(""))
    {
      this.appIDs.add(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ReviewApplications
 * JD-Core Version:    0.7.0.1
 */