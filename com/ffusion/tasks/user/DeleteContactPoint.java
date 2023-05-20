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

public class DeleteContactPoint
  extends BaseTask
{
  public static final String CONTACT_POINT = "CONTACT_POINT";
  protected String _sessionName = "CONTACT_POINT";
  protected int _maxNumberOfCharsForContactPointName = 40;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoint localContactPoint = (ContactPoint)localHttpSession.getAttribute(this._sessionName);
    if (localContactPoint == null)
    {
      this.error = 3540;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      UserAdmin.deleteContactPoint(localSecureUser, localContactPoint, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.DeleteContactPoint
 * JD-Core Version:    0.7.0.1
 */