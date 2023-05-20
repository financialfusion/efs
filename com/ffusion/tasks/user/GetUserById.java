package com.ffusion.tasks.user;

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

public class GetUserById
  extends BaseTask
  implements UserTask
{
  protected String userSessionName = "User";
  protected String profileId;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.profileId != null) && (this.profileId.length() != 0))
    {
      User localUser = new User();
      localUser.setId(this.profileId);
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localUser = UserAdmin.getUserById(localSecureUser, localUser, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute(this.userSessionName, localUser);
      }
    }
    else
    {
      this.error = 3514;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setProfileId(String paramString)
  {
    this.profileId = paramString;
  }
  
  public void setUserSessionName(String paramString)
  {
    this.userSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUserById
 * JD-Core Version:    0.7.0.1
 */