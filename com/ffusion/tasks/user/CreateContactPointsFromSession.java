package com.ffusion.tasks.user;

import com.ffusion.beans.Email;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateContactPointsFromSession
  extends BaseTask
{
  public static final String CONTACT_POINTS = "CONTACT_POINTS";
  protected String _sessionName = "CONTACT_POINTS";
  protected int _maxNumberOfCharsForContactPointName = 40;
  protected int _maxNumberOfCharsForContactPointAddress = 255;
  protected int[] _validContactPointTypes = { 1, 2 };
  protected boolean _validationOn = true;
  protected String _idSessionName = "ID";
  protected String _nameSessionName = "Name";
  protected String _typeSessionName = "Type";
  protected String _addressSessionName = "Address";
  protected String _secureSessionName = "Secure";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoints localContactPoints = new ContactPoints(localSecureUser.getLocale());
    for (int i = 1;; i++)
    {
      String str2 = (String)localHttpSession.getAttribute(this._idSessionName + i);
      int j = -1;
      if (str2 != null) {
        try
        {
          j = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          str1 = this.taskErrorURL;
        }
      }
      String str3 = (String)localHttpSession.getAttribute(this._nameSessionName + i);
      String str4 = (String)localHttpSession.getAttribute(this._typeSessionName + i);
      int k = -1;
      if (str4 != null) {
        try
        {
          k = Integer.parseInt(str4);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          str1 = this.taskErrorURL;
        }
      }
      String str5 = (String)localHttpSession.getAttribute(this._addressSessionName + i);
      String str6 = (String)localHttpSession.getAttribute(this._secureSessionName + i);
      boolean bool = new Boolean(str6).booleanValue();
      if ((str3 == null) && (str4 == null) && (str5 == null) && (str6 == null)) {
        break;
      }
      ContactPoint localContactPoint = new ContactPoint(localSecureUser.getLocale());
      localContactPoint.setContactPointID(j);
      localContactPoint.setName(str3);
      localContactPoint.setContactPointType(k);
      localContactPoint.setAddress(str5);
      localContactPoint.setSecure(bool);
      localContactPoint.setDirectoryID(localSecureUser.getProfileID());
      if ((this._validationOn) && (!validateContactPoint(localContactPoint))) {
        str1 = this.taskErrorURL;
      }
      localContactPoints.add(localContactPoint);
      localHttpSession.removeAttribute(this._idSessionName + i);
      localHttpSession.removeAttribute(this._nameSessionName + i);
      localHttpSession.removeAttribute(this._typeSessionName + i);
      localHttpSession.removeAttribute(this._addressSessionName + i);
      localHttpSession.removeAttribute(this._secureSessionName + i);
    }
    if ((this._validationOn) && (localContactPoints.size() == 0))
    {
      this.error = 3544;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(this._sessionName, localContactPoints);
    return str1;
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
  
  public void setValidation(String paramString)
  {
    this._validationOn = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.CreateContactPointsFromSession
 * JD-Core Version:    0.7.0.1
 */