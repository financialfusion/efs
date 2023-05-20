package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Alerts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUserAlert
  extends BaseTask
  implements Task
{
  protected String _id;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this._id == null) || (this._id.trim().length() == 0))
    {
      this.error = 19303;
      str = this.taskErrorURL;
    }
    else
    {
      UserAlerts localUserAlerts = (UserAlerts)localHttpSession.getAttribute("Alerts");
      if (localUserAlerts == null)
      {
        this.error = 19002;
        str = this.taskErrorURL;
      }
      else
      {
        UserAlert localUserAlert = localUserAlerts.getById(this._id);
        if (localUserAlert == null)
        {
          this.error = 19001;
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
              Alerts.deleteUserAlert(localSecureUser, localUserAlert, localHashMap);
            }
            catch (CSILException localCSILException)
            {
              this.error = MapError.mapError(localCSILException);
              str = this.serviceErrorURL;
            }
          }
        }
      }
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    this._id = paramString;
  }
  
  public String getId()
  {
    return this._id;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.DeleteUserAlert
 * JD-Core Version:    0.7.0.1
 */