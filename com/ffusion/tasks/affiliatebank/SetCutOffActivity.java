package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffActivities;
import com.ffusion.beans.affiliatebank.CutOffActivity;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetCutOffActivity
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  public SetCutOffActivity()
  {
    this.beanSessionName = "CutOffActivity";
    this.collectionSessionName = "CutOffActivities";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 26020;
      return this.taskErrorURL;
    }
    CutOffActivities localCutOffActivities = (CutOffActivities)localHttpSession.getAttribute(this.collectionSessionName);
    if ((localCutOffActivities == null) || (localCutOffActivities.size() == 0))
    {
      this.error = 26021;
      return this.taskErrorURL;
    }
    CutOffActivity localCutOffActivity = (CutOffActivity)localCutOffActivities.getFirstByFilter("ProcessId=" + this.id);
    if (localCutOffActivity == null)
    {
      this.error = 26022;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localCutOffActivity);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.SetCutOffActivity
 * JD-Core Version:    0.7.0.1
 */