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

public class GetUsersByPersonalBanker
  extends BaseTask
  implements UserTask
{
  String vL;
  String vJ;
  private boolean vK = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Users localUsers1 = null;
    if (this.vK) {
      localUsers1 = (Users)localHttpSession.getAttribute("UsersSearchListByPersonalBanker");
    }
    Users localUsers2 = new Users((Locale)localHttpSession.getAttribute("java.util.Locale"));
    User localUser = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localUser.setBankId(localSecureUser.getBankID());
    localUser.setPersonalBanker(this.vL);
    try
    {
      HashMap localHashMap = new HashMap(1);
      localHashMap.put("USERS", localUsers2);
      localUsers2 = UserAdmin.getFilteredUsers(localSecureUser, localUser, null, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      if ((this.vK) && (localUsers1 != null)) {
        localUsers2.addAll(localUsers1);
      }
      localHttpSession.setAttribute("UsersSearchListByPersonalBanker", localUsers2);
      Users localUsers3 = (Users)localUsers2.clone();
      localHttpSession.setAttribute("TempAssignedPbUsers", localUsers3);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBankEmployeeId(String paramString)
  {
    this.vL = paramString;
  }
  
  public void setStatus(String paramString)
  {
    this.vJ = paramString;
  }
  
  public void setAppendTo(boolean paramBoolean)
  {
    this.vK = paramBoolean;
  }
  
  public void setAppendTo(String paramString)
  {
    this.vK = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getAppendToBoolean()
  {
    return this.vK;
  }
  
  public String getAppendTo()
  {
    return this.vK == true ? "true" : "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUsersByPersonalBanker
 * JD-Core Version:    0.7.0.1
 */