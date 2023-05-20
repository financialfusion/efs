package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateTXLimitsApprovalGroup
  extends BaseTask
  implements BusinessTask
{
  private String hy = "Bank_defined";
  private String hx = null;
  private String hw = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (!jdMethod_case(this.hx))
    {
      this.error = 4108;
      return this.taskErrorURL;
    }
    if (!jdMethod_case(this.hw))
    {
      this.error = 4108;
      return this.taskErrorURL;
    }
    try
    {
      Business.updateTXLimitsApprovalGroup(localSecureUser, this.hy, Integer.parseInt(this.hx), Integer.parseInt(this.hw), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setFlowLevelBusiness(String paramString)
  {
    setFlowLevelType("Bank_defined");
  }
  
  public void setFlowLevelType(String paramString)
  {
    this.hy = paramString;
  }
  
  public void setApprovalGroupID(String paramString)
  {
    this.hx = paramString;
  }
  
  public String getApprovalGroupID()
  {
    return this.hx;
  }
  
  public void setEntityID(String paramString)
  {
    this.hw = paramString;
  }
  
  public String getEntityID()
  {
    return this.hw;
  }
  
  private boolean jdMethod_case(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return false;
    }
    try
    {
      int i = Integer.parseInt(paramString);
      if (i <= 0) {
        return false;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.UpdateTXLimitsApprovalGroup
 * JD-Core Version:    0.7.0.1
 */