package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
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

public class GetSecondaryUsers
  extends BaseTask
{
  protected String _secondaryUsersName = "SecondaryUsers";
  protected String _userId;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str = this.successURL;
    this.error = 0;
    if (localSecureUser == null) {
      this.error = 38;
    } else {
      try
      {
        HashMap localHashMap = new HashMap();
        User localUser = new User();
        if (this._userId != null) {
          localUser.setId(this._userId);
        } else {
          localUser.setId(String.valueOf(localSecureUser.getProfileID()));
        }
        Users localUsers = UserAdmin.getSecondaryUsers(localSecureUser, localUser, localHashMap);
        localHttpSession.setAttribute(this._secondaryUsersName, localUsers);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSecondaryUsersName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this._secondaryUsersName = paramString.trim();
    } else {
      this._secondaryUsersName = "SecondaryUsers";
    }
  }
  
  public String getSecondaryUsersName()
  {
    return this._secondaryUsersName;
  }
  
  public void setPrimaryUserId(String paramString)
  {
    if ((paramString != null) && (!paramString.trim().equals(""))) {
      this._userId = paramString;
    }
  }
  
  public String getPrimaryUserId()
  {
    return this._userId;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.GetSecondaryUsers
 * JD-Core Version:    0.7.0.1
 */