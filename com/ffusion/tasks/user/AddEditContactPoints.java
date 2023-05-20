package com.ffusion.tasks.user;

import com.ffusion.beans.Email;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.ValidateString;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddEditContactPoints
  extends BaseTask
{
  public static final String ORIGINAL_CONTACT_POINTS = "ORIGINAL_CONTACT_POINTS";
  public static final String MODIFIED_CONTACT_POINTS = "MODIFIED_CONTACT_POINTS";
  protected String _originalContactPointsSessionName = "ORIGINAL_CONTACT_POINTS";
  protected String _modifiedContactPointsSessionName = "MODIFIED_CONTACT_POINTS";
  protected int _maxNumberOfCharsForContactPointName = 40;
  protected int _maxNumberOfCharsForContactPointAddress = 255;
  protected int[] _validContactPointTypes = { 1, 2 };
  private String aKX = "ContactPoint_ALERTS_CENTER";
  private String aKY = "com.ffusion.beans.alerts.resources";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoints localContactPoints1 = (ContactPoints)localHttpSession.getAttribute(this._originalContactPointsSessionName);
    ContactPoints localContactPoints2 = (ContactPoints)localHttpSession.getAttribute(this._modifiedContactPointsSessionName);
    if ((localContactPoints1 == null) || (localContactPoints2 == null))
    {
      this.error = 3542;
      return this.taskErrorURL;
    }
    if (localContactPoints2.size() == 0)
    {
      this.error = 3544;
      return this.taskErrorURL;
    }
    int i = 1;
    ContactPoint localContactPoint1;
    for (int j = 0; j < localContactPoints2.size(); j++)
    {
      localContactPoint1 = (ContactPoint)localContactPoints2.get(j);
      if (!validateContactPoint(localContactPoint1)) {
        return this.taskErrorURL;
      }
    }
    for (j = 0; j < localContactPoints1.size(); j++)
    {
      localContactPoint1 = (ContactPoint)localContactPoints1.get(j);
      if (localContactPoint1.getContactPointType() == 1) {
        i = 0;
      }
    }
    if (i != 0)
    {
      localObject = new ContactPoint(localSecureUser.getLocale());
      ((ContactPoint)localObject).setDirectoryID(localSecureUser.getProfileID());
      ((ContactPoint)localObject).setName(ResourceUtil.getString(this.aKX, this.aKY, localSecureUser.getLocale()));
      ((ContactPoint)localObject).setContactPointType(1);
      ((ContactPoint)localObject).setSecure(true);
      localContactPoints2.add(localObject);
    }
    Object localObject = new ContactPoints(localContactPoints2.getLocale());
    for (int k = 0; k < localContactPoints2.size(); k++)
    {
      ContactPoint localContactPoint2 = (ContactPoint)localContactPoints2.get(k);
      if (localContactPoint2.getContactPointID() == -1)
      {
        ((ContactPoints)localObject).add(localContactPoint2);
      }
      else
      {
        ContactPoint localContactPoint3 = localContactPoints1.getContactPointByID(localContactPoint2.getContactPointID());
        if (!localContactPoint2.equals(localContactPoint3))
        {
          HashMap localHashMap2 = new HashMap();
          try
          {
            UserAdmin.modifyContactPoint(localSecureUser, localContactPoint3, localContactPoint2, localHashMap2);
          }
          catch (CSILException localCSILException2)
          {
            MapError.mapError(localCSILException2);
            return this.serviceErrorURL;
          }
        }
      }
    }
    HashMap localHashMap1 = new HashMap();
    try
    {
      UserAdmin.addContactPoints(localSecureUser, (ContactPoints)localObject, localHashMap1);
    }
    catch (CSILException localCSILException1)
    {
      MapError.mapError(localCSILException1);
      return this.serviceErrorURL;
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
    this.error = X(paramContactPoint.getName());
    if (this.error != 0) {
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
  
  private int X(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return 3539;
    }
    if (paramString.length() > this._maxNumberOfCharsForContactPointName) {
      return 3538;
    }
    if (!ValidateString.validateName(paramString)) {
      return 3549;
    }
    return 0;
  }
  
  public void setOriginalContactPointsSessionName(String paramString)
  {
    this._originalContactPointsSessionName = paramString;
  }
  
  public void setModifiedContactPointsSessionName(String paramString)
  {
    this._modifiedContactPointsSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AddEditContactPoints
 * JD-Core Version:    0.7.0.1
 */