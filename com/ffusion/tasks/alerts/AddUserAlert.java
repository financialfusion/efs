package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Alerts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUserAlert
  extends BaseTask
  implements Task
{
  protected String _alertName = "Alert";
  protected String _maxPropertyNameLength = "100";
  protected String _maxPropertyValueLength = "1024";
  
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
            Alerts.addUserAlert(localSecureUser, localUserAlert, localHashMap);
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
  
  protected boolean verifyAlertAdditionalProperties(UserAlert paramUserAlert)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this._maxPropertyNameLength);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      this.error = 19312;
      return false;
    }
    int j = 0;
    try
    {
      j = Integer.parseInt(this._maxPropertyValueLength);
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      this.error = 19313;
      return false;
    }
    Properties localProperties = paramUserAlert.getAdditionalProperties();
    if (localProperties != null)
    {
      Iterator localIterator = localProperties.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        if ((str1 != null) && (str1.length() > i))
        {
          this.error = 19314;
          return false;
        }
        String str2 = (String)localProperties.get(str1);
        if ((str2 != null) && (str2.length() > j))
        {
          this.error = 19315;
          return false;
        }
      }
    }
    return true;
  }
  
  public void setAlertSessionName(String paramString)
  {
    this._alertName = paramString;
  }
  
  public String getAlertSessionName()
  {
    return this._alertName;
  }
  
  public void setMaxAlertPropertyNameLength(String paramString)
  {
    this._maxPropertyNameLength = paramString;
  }
  
  public void setMaxAlertPropertyValueLength(String paramString)
  {
    this._maxPropertyValueLength = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.AddUserAlert
 * JD-Core Version:    0.7.0.1
 */