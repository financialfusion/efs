package com.ffusion.tasks.user;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements UserTask
{
  protected String serviceName = "UserService";
  private String n9;
  private String n8;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (validateInput(localHttpSession))
    {
      this.error = signOn(paramHttpServletRequest);
      switch (this.error)
      {
      case 0: 
        str = this.successURL;
        break;
      case 3500: 
        str = this.taskErrorURL;
        break;
      case 3004: 
        str = this.invalidPasswordURL;
        break;
      case 3007: 
        str = this.mustChangePasswordURL;
        break;
      case 3006: 
        str = this.n9;
        break;
      case 3008: 
        str = this.n8;
        break;
      default: 
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    return 0;
  }
  
  public void setMustReauthenticateURL(String paramString)
  {
    this.n9 = paramString;
  }
  
  public void setLockedPasswordURL(String paramString)
  {
    this.n8 = paramString;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SignOn
 * JD-Core Version:    0.7.0.1
 */