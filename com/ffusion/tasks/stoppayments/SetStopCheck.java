package com.ffusion.tasks.stoppayments;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetStopCheck
  extends BaseTask
  implements Task
{
  protected String stopCheckID;
  protected String collectionName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    StopChecks localStopChecks = null;
    if ((this.collectionName == null) || (this.collectionName.trim().length() == 0)) {
      localStopChecks = (StopChecks)localHttpSession.getAttribute("StopChecks");
    } else {
      localStopChecks = (StopChecks)localHttpSession.getAttribute(this.collectionName.trim());
    }
    if ((this.stopCheckID == null) || (this.stopCheckID.length() == 0))
    {
      this.error = 13022;
      str = this.taskErrorURL;
    }
    else if (localStopChecks != null)
    {
      StopCheck localStopCheck = localStopChecks.getByID(this.stopCheckID);
      if (localStopCheck != null)
      {
        localStopCheck.setLocale(localLocale);
        localHttpSession.setAttribute("StopCheck", localStopCheck);
      }
      else
      {
        this.error = 13021;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 13020;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.stopCheckID = paramString;
  }
  
  public void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.SetStopCheck
 * JD-Core Version:    0.7.0.1
 */