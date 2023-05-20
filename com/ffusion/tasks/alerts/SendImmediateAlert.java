package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Alerts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendImmediateAlert
  extends BaseTask
{
  public static final String CONTACT_POINT = "CONTACT_POINT";
  protected String _sessionName = "CONTACT_POINT";
  protected String _message = null;
  protected String _subject = null;
  
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
    if (!validateContactPoint(localContactPoint)) {
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("AlertType", Integer.toString(8));
    try
    {
      Alerts.sendImmediateAlert(localSecureUser, localContactPoint, this._subject, this._message, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
  
  public void setMessage(String paramString)
  {
    this._message = paramString;
  }
  
  public void setSubject(String paramString)
  {
    this._subject = paramString;
  }
  
  protected boolean validateContactPoint(ContactPoint paramContactPoint)
  {
    if ((paramContactPoint.getContactPointType() == 2) && ((paramContactPoint.getAddress() == null) || (paramContactPoint.getAddress().equals(""))))
    {
      this.error = 3543;
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.SendImmediateAlert
 * JD-Core Version:    0.7.0.1
 */