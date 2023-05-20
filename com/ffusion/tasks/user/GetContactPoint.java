package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.ContactPoint;
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

public class GetContactPoint
  extends BaseTask
{
  public static final String CONTACT_POINT = "CONTACT_POINT";
  protected String _sessionName = "CONTACT_POINT";
  protected int _maxNumberOfCharsForContactPointName = 40;
  protected int _contactPointID = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (this._contactPointID == -1)
    {
      this.error = 3541;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    ContactPoint localContactPoint = null;
    try
    {
      localContactPoint = UserAdmin.getContactPoint(localSecureUser, this._contactPointID, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (localContactPoint != null) {
      localHttpSession.setAttribute(this._sessionName, localContactPoint);
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
  
  public void setContactPointID(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (i != -1) {
      this._contactPointID = i;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetContactPoint
 * JD-Core Version:    0.7.0.1
 */