package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
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

public class GetPrimaryUser
  extends BaseTask
{
  public static final String PRIMARY_USER_NAME = "PrimaryUser";
  protected String _primaryUserName = "PrimaryUser";
  public static final String SECONDARY_USER_NAME = "SecondaryUser";
  protected String _secondaryUserName = "SecondaryUser";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    User localUser1 = (User)localHttpSession.getAttribute(this._secondaryUserName);
    String str = this.successURL;
    this.error = 0;
    if ((localSecureUser == null) || (localUser1 == null)) {
      this.error = 38;
    } else {
      try
      {
        HashMap localHashMap = new HashMap();
        User localUser2 = UserAdmin.getPrimaryUser(localSecureUser, localUser1, localHashMap);
        localHttpSession.setAttribute(this._primaryUserName, localUser2);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setPrimaryUserName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this._primaryUserName = paramString.trim();
    } else {
      this._primaryUserName = "PrimaryUser";
    }
  }
  
  public String getPrimaryUserName()
  {
    return this._primaryUserName;
  }
  
  public void setSecondaryUserName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this._secondaryUserName = paramString.trim();
    } else {
      this._secondaryUserName = "SecondaryUser";
    }
  }
  
  public String getSecondaryUserName()
  {
    return this._secondaryUserName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.GetPrimaryUser
 * JD-Core Version:    0.7.0.1
 */