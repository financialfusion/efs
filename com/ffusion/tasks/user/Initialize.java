package com.ffusion.tasks.user;

import com.ffusion.services.UserAdmin2;
import com.ffusion.tasks.InitService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Initialize
  extends InitService
  implements UserTask
{
  private String kD = "UserService";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    UserAdmin2 localUserAdmin2 = null;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      if (this.className == null)
      {
        this.error = 40;
        str = this.taskErrorURL;
      }
      else
      {
        Class localClass = Class.forName(this.className);
        localUserAdmin2 = (UserAdmin2)localClass.newInstance();
        if (this.initURL == null)
        {
          this.error = 35;
          str = this.taskErrorURL;
        }
        else if ((this.error = localUserAdmin2.initialize(this.initURL)) != 0)
        {
          str = this.serviceErrorURL;
        }
        else
        {
          localHttpSession.setAttribute(this.kD, localUserAdmin2);
          str = this.successURL;
        }
      }
    }
    catch (Exception localException)
    {
      this.error = 3516;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.kD = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.Initialize
 * JD-Core Version:    0.7.0.1
 */