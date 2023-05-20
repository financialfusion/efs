package com.ffusion.tasks.user;

import com.ffusion.beans.Email;
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

public class ModifyContactPoint
  extends BaseTask
{
  public static final String ORIGINAL_CONTACT_POINT = "ORIGINAL_CONTACT_POINT";
  public static final String MODIFIED_CONTACT_POINT = "MODIFIED_CONTACT_POINT";
  protected String _originalContactSessionName = "ORIGINAL_CONTACT_POINT";
  protected String _modifiedContactSessionName = "MODIFIED_CONTACT_POINT";
  protected int _maxNumberOfCharsForContactPointName = 40;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoint localContactPoint1 = (ContactPoint)localHttpSession.getAttribute(this._originalContactSessionName);
    ContactPoint localContactPoint2 = (ContactPoint)localHttpSession.getAttribute(this._modifiedContactSessionName);
    if ((localContactPoint1 == null) || (localContactPoint2 == null))
    {
      this.error = 3540;
      return this.taskErrorURL;
    }
    if (!validateContactPoint(localContactPoint2)) {
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      UserAdmin.modifyContactPoint(localSecureUser, localContactPoint1, localContactPoint2, localHashMap);
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
    if ((paramContactPoint.getName() == null) || (paramContactPoint.getName().equals("")))
    {
      this.error = 3539;
      bool = false;
    }
    else if (paramContactPoint.getName().length() > this._maxNumberOfCharsForContactPointName)
    {
      this.error = 3538;
      bool = false;
    }
    else if (paramContactPoint.getContactPointType() == 2)
    {
      if ((paramContactPoint.getAddress() == null) || (paramContactPoint.getAddress().equals("")))
      {
        this.error = 3543;
        bool = false;
      }
      Email localEmail = new Email(paramContactPoint.getAddress().trim());
      if (!localEmail.isValid())
      {
        this.error = 3545;
        bool = false;
      }
    }
    return bool;
  }
  
  public void setOriginalContactPointSessionName(String paramString)
  {
    this._originalContactSessionName = paramString;
  }
  
  public void setModifiedContactPointSessionName(String paramString)
  {
    this._modifiedContactSessionName = paramString;
  }
  
  public void setMaxNumCharsForContactPointName(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (i != -1) {
      this._maxNumberOfCharsForContactPointName = i;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.ModifyContactPoint
 * JD-Core Version:    0.7.0.1
 */