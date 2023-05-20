package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckACHSECEntitlement
  extends BaseTask
{
  private boolean UO = false;
  private String UR = null;
  private String UN = null;
  private String UQ = null;
  private String UM = null;
  private boolean UP = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = super.getSuccessURL();
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if ((this.UN != null) && (this.UN.trim().length() > 0))
      {
        ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.UN);
        this.UO = ACH.checkACHSECEntitlement(localSecureUser, localACHBatch, this.UR, new HashMap());
      }
      else if ((this.UQ != null) && (this.UQ.trim().length() > 0) && (this.UM != null) && (this.UM.trim().length() > 0))
      {
        this.UO = ACH.checkACHSECEntitlement(localSecureUser, this.UQ, this.UM, this.UR, this.UP, new HashMap());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setOperationName(String paramString)
  {
    this.UR = paramString;
  }
  
  public String getOperationName()
  {
    return this.UR;
  }
  
  public void setACHBatchName(String paramString)
  {
    this.UN = paramString;
  }
  
  public String getACHBatchName()
  {
    return this.UN;
  }
  
  public String getIsEntitledTo()
  {
    return String.valueOf(this.UO).toUpperCase();
  }
  
  public String getSECCode()
  {
    return this.UQ;
  }
  
  public void setSECCode(String paramString)
  {
    this.UQ = paramString;
  }
  
  public void setSECResourceCode(String paramString)
  {
    this.UQ = paramString.substring(0, 3);
  }
  
  public void setACHCompanyID(String paramString)
  {
    this.UM = paramString;
  }
  
  public String getACHCompanyID()
  {
    return this.UM;
  }
  
  public void setHasAddenda(String paramString)
  {
    this.UP = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getHasAddenda()
  {
    return String.valueOf(this.UP).toUpperCase();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.CheckACHSECEntitlement
 * JD-Core Version:    0.7.0.1
 */