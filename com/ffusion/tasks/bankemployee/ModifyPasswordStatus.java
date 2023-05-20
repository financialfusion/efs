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

public class ModifyPasswordStatus
  extends BaseTask
  implements BankEmployeeTask
{
  protected String id = "";
  protected String statusValue = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Users localUsers = (Users)localHttpSession.getAttribute("Users");
    if (localUsers != null)
    {
      User localUser1 = localUsers.getByID(this.id);
      User localUser2 = (User)localHttpSession.getAttribute("OldUser");
      if ((localUser1 != null) && (localUser2 != null))
      {
        if (!localUser1.getPassword().equals(localUser2.getPassword()))
        {
          User localUser3 = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
          localUser3.setId(localUser1.getId());
          localUser3.put("PASSWORD_STATUS", this.statusValue);
          try
          {
            HashMap localHashMap = null;
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            UserAdmin.modifyPasswordStatus(localSecureUser, localUser3, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
            str = this.serviceErrorURL;
          }
        }
      }
      else
      {
        if (localUser1 == null) {
          this.error = 5510;
        } else {
          this.error = 5523;
        }
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public void setStatusValue(String paramString)
  {
    this.statusValue = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.ModifyPasswordStatus
 * JD-Core Version:    0.7.0.1
 */