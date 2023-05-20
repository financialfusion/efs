package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Alerts;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyUserAlert
  extends AddUserAlert
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this._alertName == null) || (this._alertName.trim().length() == 0))
    {
      this.error = 19304;
      str = this.taskErrorURL;
    }
    else
    {
      UserAlert localUserAlert = (UserAlert)localHttpSession.getAttribute(this._alertName);
      if (localUserAlert == null)
      {
        this.error = 19001;
        str = this.taskErrorURL;
      }
      else if (!verifyAlertAdditionalProperties(localUserAlert))
      {
        str = this.taskErrorURL;
      }
      else
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        if (localSecureUser == null)
        {
          this.error = 38;
          str = this.taskErrorURL;
        }
        else
        {
          this.error = 0;
          HashMap localHashMap = new HashMap();
          try
          {
            Alerts.modifyUserAlert(localSecureUser, localUserAlert, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
            str = this.serviceErrorURL;
          }
        }
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.ModifyUserAlert
 * JD-Core Version:    0.7.0.1
 */