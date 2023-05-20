package com.ffusion.tasks.bankemployee;

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

public class GetAssignedUsers
  extends BaseTask
  implements BankEmployeeTask
{
  private String tM;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    User localUser = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localUser.setPersonalBanker(this.tM);
    Users localUsers1 = new Users((Locale)localHttpSession.getAttribute("java.util.Locale"));
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localUsers1 = UserAdmin.getUserList(localSecureUser, localUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) || (this.error == 12))
    {
      str = this.successURL;
      localHttpSession.setAttribute("AssignedUsers", localUsers1);
      Users localUsers2 = new Users((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localUsers2 = (Users)localUsers1.clone();
      localHttpSession.setAttribute("TempAssignedUsers", localUsers2);
    }
    return str;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.tM = paramString;
  }
  
  public String getEmployeeId()
  {
    return this.tM;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetAssignedUsers
 * JD-Core Version:    0.7.0.1
 */