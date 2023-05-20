package com.ffusion.tasks.user;

import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddContactPointToList
  extends BaseTask
{
  public static final String CONTACT_POINTS = "CONTACT_POINTS";
  protected String _sessionName = "CONTACT_POINTS";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    ContactPoints localContactPoints = (ContactPoints)localHttpSession.getAttribute(this._sessionName);
    if (localContactPoints == null)
    {
      this.error = 3542;
      return this.taskErrorURL;
    }
    localContactPoints.add(new ContactPoint(localContactPoints.getLocale()));
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AddContactPointToList
 * JD-Core Version:    0.7.0.1
 */