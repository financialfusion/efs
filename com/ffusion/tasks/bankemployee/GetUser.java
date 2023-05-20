package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
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

public class GetUser
  extends BaseTask
  implements BankEmployeeTask
{
  protected String id;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.id != null) && (this.id.length() != 0))
    {
      User localUser = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localUser.setId(this.id);
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localUser = UserAdmin.getUser(localSecureUser, localUser, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("User", localUser);
      }
    }
    else
    {
      this.error = 3514;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetUser
 * JD-Core Version:    0.7.0.1
 */