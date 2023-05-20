package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUserToUserList
  extends BaseTask
  implements BankEmployeeTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    Users localUsers = (Users)localHttpSession.getAttribute("Users");
    if (localUsers == null)
    {
      localUsers = new Users((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localHttpSession.setAttribute("Users", localUsers);
    }
    User localUser = (User)localHttpSession.getAttribute("User");
    if (localUser == null)
    {
      this.error = 38;
      str = this.taskErrorURL;
    }
    else if (!localUsers.contains(localUser))
    {
      localUsers.add(localUser);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.AddUserToUserList
 * JD-Core Version:    0.7.0.1
 */