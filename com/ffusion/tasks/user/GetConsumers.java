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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetConsumers
  extends BaseTask
  implements UserTask
{
  private String xx = "UserService";
  private String xw = "SearchConsumer";
  private String xu = "Consumers";
  protected String dataNotFoundURL;
  private boolean xv = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    User localUser = (User)localHttpSession.getAttribute(this.xw);
    Users localUsers1 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localUsers1 = UserAdmin.getConsumers(localSecureUser, localUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      Users localUsers2 = null;
      if (this.xv)
      {
        localUsers2 = (Users)localHttpSession.getAttribute(this.xu);
        if (localUsers2 != null) {
          localUsers1.addAll(localUsers2);
        }
      }
      localHttpSession.setAttribute(this.xu, localUsers1);
      str = this.successURL;
    }
    else if (this.error == 3009)
    {
      if ((this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() == 0)) {
        str = this.dataNotFoundURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSearchUserSessionName(String paramString)
  {
    this.xw = paramString;
  }
  
  public void setUsersSessionName(String paramString)
  {
    this.xu = paramString;
  }
  
  public void setDataNotFoundURL(String paramString)
  {
    this.dataNotFoundURL = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.xx = paramString;
  }
  
  public void setAppendTo(boolean paramBoolean)
  {
    this.xv = paramBoolean;
  }
  
  public void setAppendTo(String paramString)
  {
    this.xv = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getAppendToBoolean()
  {
    return this.xv;
  }
  
  public String getAppendTo()
  {
    return this.xv == true ? "true" : "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetConsumers
 * JD-Core Version:    0.7.0.1
 */