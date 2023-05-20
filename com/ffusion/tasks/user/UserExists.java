package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserExists
  extends User
  implements UserTask
{
  private String oG = "UserService";
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String failureURL;
  protected int error;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      UserAdmin.userExists(localSecureUser, localSecureUser.getUserName(), localSecureUser.getBankID(), null);
    }
    catch (CSILException localCSILException)
    {
      if (localCSILException.getCode() == -1009)
      {
        this.error = localCSILException.getServiceError();
        str = this.taskErrorURL;
      }
      else
      {
        this.error = localCSILException.getCode();
        str = this.failureURL;
      }
    }
    return str;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setFailureURL(String paramString)
  {
    this.failureURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setUserServiceName(String paramString)
  {
    this.oG = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.UserExists
 * JD-Core Version:    0.7.0.1
 */