package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Strings;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUsers
  extends BaseTask
  implements UserTask
{
  private String vW = "UserService";
  private String vV = "User";
  private String vU = "Users";
  protected String userNotFoundURL;
  protected int USER_MAX_NAME_LENGTH = 40;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    User localUser = (User)localHttpSession.getAttribute(this.vV);
    if (!jdMethod_new(localUser))
    {
      str = this.taskErrorURL;
      return str;
    }
    Users localUsers = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localUsers = UserAdmin.getUsers(localSecureUser, localUser, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error != 0) && (this.error == 3005) && (this.userNotFoundURL != null) && (this.userNotFoundURL.length() == 0)) {
      str = this.userNotFoundURL;
    }
    localHttpSession.setAttribute(this.vU, localUsers);
    return str;
  }
  
  public void setUserSessionName(String paramString)
  {
    this.vV = paramString;
  }
  
  public void setUsersSessionName(String paramString)
  {
    this.vU = paramString;
  }
  
  public void setUserNotFoundURL(String paramString)
  {
    this.userNotFoundURL = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.vW = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
  
  private boolean jdMethod_new(User paramUser)
  {
    if (paramUser == null) {
      return setError(3513);
    }
    if ((paramUser.getFirstName() != null) && (paramUser.getFirstName().length() > this.USER_MAX_NAME_LENGTH)) {
      return setError(193);
    }
    if ((paramUser.getLastName() != null) && (paramUser.getLastName().length() > this.USER_MAX_NAME_LENGTH)) {
      return setError(195);
    }
    String str = paramUser.getUserName();
    if (str != null)
    {
      if (Strings.verifyStringWithNonAscii(str)) {
        return setError(199);
      }
      if (paramUser.getUserName().length() > 20) {
        return setError(194);
      }
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUsers
 * JD-Core Version:    0.7.0.1
 */