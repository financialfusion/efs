package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetUser
  extends User
  implements UserTask
{
  private String pm = "UserService";
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    User localUser = null;
    Users localUsers = (Users)localHttpSession.getAttribute("Users");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localUsers == null)
    {
      try
      {
        localUser = UserAdmin.getUserById(localSecureUser, this, null);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("User", localUser);
      }
    }
    else
    {
      localUser = localUsers.getByID(getId());
      if (localUser != null)
      {
        localHttpSession.setAttribute("User", localUser);
      }
      else
      {
        try
        {
          localUser = UserAdmin.getUserById(localSecureUser, this, null);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
          str = this.serviceErrorURL;
        }
        if (this.error == 0) {
          localHttpSession.setAttribute("User", localUser);
        }
      }
    }
    return str;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setUserServiceName(String paramString)
  {
    this.pm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetUser
 * JD-Core Version:    0.7.0.1
 */