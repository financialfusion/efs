package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUser
  extends BaseTask
  implements UserTask
{
  private String vT = "UserService";
  private String vS = "";
  protected boolean bProcess = false;
  protected String usersSessionName = "Users";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (this.bProcess == true)
    {
      User localUser = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localUser.setId(this.vS);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      try
      {
        localUser = UserAdmin.getUserById(localSecureUser, localUser, localHashMap);
        UserAdmin.deleteUser(localSecureUser, localUser, localHashMap);
        if (localUser.getPrimarySecondary() == User.USER_TYPE_PRIMARY) {
          UserAdmin.deleteUserRegisteredWithBPW(localSecureUser, localUser, localHashMap);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Users localUsers = (Users)localHttpSession.getAttribute(this.usersSessionName);
        if (localUsers != null)
        {
          localUsers.removeByID(localUser.getId());
          localHttpSession.setAttribute(this.usersSessionName, localUsers);
          this.bProcess = false;
        }
      }
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.vS = paramString;
  }
  
  public String getID()
  {
    return this.vS;
  }
  
  public void setProcess(String paramString)
  {
    this.bProcess = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUsersSessionName(String paramString)
  {
    this.usersSessionName = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.vT = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.DeleteUser
 * JD-Core Version:    0.7.0.1
 */