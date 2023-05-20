package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetContactPoints
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
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    ContactPoints localContactPoints = null;
    try
    {
      localContactPoints = UserAdmin.getContactPoints(localSecureUser, localSecureUser.getProfileID(), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (localContactPoints != null) {
      localHttpSession.setAttribute(this._sessionName, localContactPoints);
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetContactPoints
 * JD-Core Version:    0.7.0.1
 */