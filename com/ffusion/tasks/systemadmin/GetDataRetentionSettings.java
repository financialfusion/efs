package com.ffusion.tasks.systemadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.systemadmin.DRSettings;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.SystemAdmin;
import com.ffusion.systemadmin.SAConstants;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDataRetentionSettings
  extends BaseTask
  implements SAConstants, SystemAdminTask
{
  private int Ve = -1;
  private int Vd = -1;
  private String Vc = "DRSettings";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = null;
    HashMap localHashMap = null;
    DRSettings localDRSettings = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      str = this.taskErrorURL;
    }
    else if (this.Ve == -1)
    {
      this.error = 38100;
      str = this.taskErrorURL;
    }
    else if (this.Vd == -1)
    {
      this.error = 38101;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        localDRSettings = new DRSettings(SystemAdmin.getDataRetentionSettings(localSecureUser, this.Vd, this.Ve, localHashMap));
        localHttpSession.setAttribute(this.Vc, localDRSettings);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getDataRetentionID()
  {
    return Integer.toString(this.Ve);
  }
  
  public String getDataRetentionType()
  {
    return Integer.toString(this.Vd);
  }
  
  public String getDRSettingsSessionName()
  {
    return this.Vc;
  }
  
  public void setDataRetentionID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Ve = -1;
    } else {
      try
      {
        this.Ve = Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        this.Ve = -1;
      }
    }
  }
  
  public void setDataRetentionType(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Vd = -1;
    } else {
      try
      {
        this.Vd = Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        this.Vd = -1;
      }
    }
  }
  
  public void setDRSettingsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Vc = "DRSettings";
    } else {
      this.Vc = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.systemadmin.GetDataRetentionSettings
 * JD-Core Version:    0.7.0.1
 */