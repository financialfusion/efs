package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetCutOffTime
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  public SetCutOffTime()
  {
    this.collectionSessionName = "ScheduleCategory";
    this.beanSessionName = "CutOffTime";
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
      this.error = 81;
      str = this.taskErrorURL;
    }
    ScheduleCategory localScheduleCategory = (ScheduleCategory)localHttpSession.getAttribute(this.collectionSessionName);
    if (localScheduleCategory == null)
    {
      this.error = 26002;
      str = this.taskErrorURL;
    }
    CutOffTimes localCutOffTimes = localScheduleCategory.getCutOffTimes();
    CutOffTime localCutOffTime = (CutOffTime)localCutOffTimes.getFirstByFilter("CutOffId=" + this.id);
    if (localCutOffTime == null)
    {
      this.error = 26004;
      str = this.taskErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localCutOffTime);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.SetCutOffTime
 * JD-Core Version:    0.7.0.1
 */