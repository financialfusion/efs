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

public class GetCumulativeDataRetentionSettings
  extends BaseTask
  implements SAConstants, SystemAdminTask
{
  private int Vj = -1;
  private int Vi = -1;
  private String Vh = "DRSettings";
  private boolean Vg = false;
  
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
    else if (this.Vj == -1)
    {
      this.error = 38100;
      str = this.taskErrorURL;
    }
    else if (this.Vi == -1)
    {
      this.error = 38101;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        localHashMap = new HashMap();
        localHashMap.put("DefaultSettingsFlag", new Boolean(this.Vg));
        localDRSettings = new DRSettings(SystemAdmin.getCumulativeDataRetentionSettings(localSecureUser, this.Vi, this.Vj, localHashMap));
        localHttpSession.setAttribute(this.Vh, localDRSettings);
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
    return Integer.toString(this.Vj);
  }
  
  public String getDataRetentionType()
  {
    return Integer.toString(this.Vi);
  }
  
  public String getDRSettingsSessionName()
  {
    return this.Vh;
  }
  
  public String getDefaultSettingsFlag()
  {
    return this.Vg ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
  }
  
  public void setDataRetentionID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Vj = -1;
    } else {
      try
      {
        this.Vj = Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        this.Vj = -1;
      }
    }
  }
  
  public void setDataRetentionType(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Vi = -1;
    } else {
      try
      {
        this.Vi = Integer.parseInt(paramString);
      }
      catch (Exception localException)
      {
        this.Vi = -1;
      }
    }
  }
  
  public void setDRSettingsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.Vh = "DRSettings";
    } else {
      this.Vh = paramString;
    }
  }
  
  public void setDefaultSettingsFlag(String paramString)
  {
    this.Vg = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.systemadmin.GetCumulativeDataRetentionSettings
 * JD-Core Version:    0.7.0.1
 */