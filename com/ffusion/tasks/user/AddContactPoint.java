package com.ffusion.tasks.user;

import com.ffusion.beans.Email;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddContactPoint
  extends BaseTask
{
  public static final String CONTACT_POINT = "CONTACT_POINT";
  protected String _sessionName = "CONTACT_POINT";
  protected int _maxNumberOfCharsForContactPointName = 40;
  protected int _maxNumberOfCharsForContactPointAddress = 255;
  protected int[] _validContactPointTypes = { 1, 2 };
  
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
    try
    {
      UserAdmin.addContactPoint(localSecureUser, localContactPoint, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateContactPoint(ContactPoint paramContactPoint)
  {
    boolean bool = true;
    Arrays.sort(this._validContactPointTypes);
    if (Arrays.binarySearch(this._validContactPointTypes, paramContactPoint.getContactPointType()) < 0)
    {
      this.error = 3547;
      return false;
    }
    if ((paramContactPoint.getName() == null) || (paramContactPoint.getName().equals("")))
    {
      this.error = 3539;
      return false;
    }
    if (paramContactPoint.getName().length() > this._maxNumberOfCharsForContactPointName)
    {
      this.error = 3538;
      return false;
    }
    if ((paramContactPoint.getAddress() != null) && (paramContactPoint.getAddress().length() > this._maxNumberOfCharsForContactPointAddress))
    {
      this.error = 3546;
      return false;
    }
    if (paramContactPoint.getContactPointType() == 2)
    {
      if ((paramContactPoint.getAddress() == null) || (paramContactPoint.getAddress().equals("")))
      {
        this.error = 3543;
        return false;
      }
      Email localEmail = new Email(paramContactPoint.getAddress().trim());
      if (!localEmail.isValid())
      {
        this.error = 3545;
        return false;
      }
    }
    return bool;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AddContactPoint
 * JD-Core Version:    0.7.0.1
 */