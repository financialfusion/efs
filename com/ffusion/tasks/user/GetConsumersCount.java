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

public class GetConsumersCount
  extends BaseTask
  implements UserTask
{
  private String xA = "UserService";
  private String xz = "SearchConsumer";
  private String xy = "ConsumersCount";
  protected String dataNotFoundURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.taskErrorURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    User localUser = (User)localHttpSession.getAttribute(this.xz);
    String str2 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      str2 = UserAdmin.getConsumersCount(localSecureUser, localUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.xy, str2);
      str1 = this.successURL;
    }
    else if (this.error == 3009)
    {
      if ((this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() == 0)) {
        str1 = this.dataNotFoundURL;
      } else {
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setSearchUserSessionName(String paramString)
  {
    this.xz = paramString;
  }
  
  public void setUsersCountSessionName(String paramString)
  {
    this.xy = paramString;
  }
  
  public void setDataNotFoundURL(String paramString)
  {
    this.dataNotFoundURL = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.xA = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetConsumersCount
 * JD-Core Version:    0.7.0.1
 */