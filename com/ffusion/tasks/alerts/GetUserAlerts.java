package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
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

public class GetUserAlerts
  extends BaseTask
  implements Task
{
  protected String _collectionName = "Alerts";
  protected int _directoryId = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this._collectionName == null) || (this._collectionName.trim().length() == 0))
    {
      this.error = 19305;
      str = this.taskErrorURL;
    }
    else if (this._directoryId == -1)
    {
      this.error = 19306;
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
          UserAlerts localUserAlerts = Alerts.getUserAlerts(localSecureUser, this._directoryId, localHashMap);
          localHttpSession.setAttribute(this._collectionName, localUserAlerts);
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
  
  public void setCollectionSessionName(String paramString)
  {
    this._collectionName = paramString;
  }
  
  public String getCollectionSessionName()
  {
    return this._collectionName;
  }
  
  public void setDirectoryId(String paramString)
  {
    this._directoryId = Integer.parseInt(paramString);
  }
  
  public String getDirectoryId()
  {
    return this._collectionName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.GetUserAlerts
 * JD-Core Version:    0.7.0.1
 */