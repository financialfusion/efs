package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUsersCount
  extends BaseTask
  implements UserTask
{
  private String v7 = "UserService";
  private String v6 = "User";
  private String v5 = "UsersCount";
  protected String userNotFoundURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = null;
    this.error = 0;
    User localUser = (User)localHttpSession.getAttribute(this.v6);
    String str2 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      str2 = UserAdmin.getUsersCount(localSecureUser, localUser, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if ((this.error != 0) && (this.error == 3005) && (this.userNotFoundURL != null) && (this.userNotFoundURL.length() == 0)) {
      str1 = this.userNotFoundURL;
    }
    localHttpSession.setAttribute(this.v5, str2);
    return str1;
  }
  
  public void setUserSessionName(String paramString)
  {
    this.v6 = paramString;
  }
  
  public void setUsersCountSessionName(String paramString)
  {
    this.v5 = paramString;
  }
  
  public void setUserNotFoundURL(String paramString)
  {
    this.userNotFoundURL = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.v7 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUsersCount
 * JD-Core Version:    0.7.0.1
 */