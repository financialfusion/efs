package com.ffusion.tasks.user;

import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetUserInSession
  extends BaseTask
  implements UserTask
{
  private String wh;
  private String wg = "User";
  private String wf = "Users";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    Users localUsers = (Users)localHttpSession.getAttribute(this.wf);
    if (localUsers == null)
    {
      this.error = 3519;
    }
    else
    {
      User localUser = localUsers.getByID(this.wh);
      if (localUser == null)
      {
        this.error = 3513;
      }
      else
      {
        localHttpSession.setAttribute(this.wg, localUser);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public final void setId(String paramString)
  {
    this.wh = paramString;
  }
  
  public void setUserSessionName(String paramString)
  {
    this.wg = paramString;
  }
  
  public void setUsersSessionName(String paramString)
  {
    this.wf = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetUserInSession
 * JD-Core Version:    0.7.0.1
 */