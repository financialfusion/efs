package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.UserAlert;
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

public class GetUserAlert
  extends BaseTask
  implements Task
{
  protected String _alertSessionName = "Alert";
  protected int _id = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this._alertSessionName == null) || (this._alertSessionName.trim().length() == 0))
    {
      this.error = 19304;
      str = this.taskErrorURL;
    }
    else if (this._id == -1)
    {
      this.error = 19303;
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
          UserAlert localUserAlert = Alerts.getUserAlert(localSecureUser, this._id, localHashMap);
          localHttpSession.setAttribute(this._alertSessionName, localUserAlert);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this._alertSessionName = paramString;
  }
  
  public String getSessionName()
  {
    return this._alertSessionName;
  }
  
  public void setId(String paramString)
  {
    this._id = Integer.parseInt(paramString);
  }
  
  public String getId()
  {
    return Integer.toString(this._id);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.GetUserAlert
 * JD-Core Version:    0.7.0.1
 */