package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitlementGroup
  extends BaseTask
  implements Task
{
  private int KU;
  private String KV = "Entitlement_EntitlementGroup";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.KU == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    EntitlementGroup localEntitlementGroup = null;
    try
    {
      localEntitlementGroup = Entitlements.getEntitlementGroup(this.KU);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.KV, localEntitlementGroup);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.KU = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.KU);
  }
  
  public void setSessionName(String paramString)
  {
    this.KV = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitlementGroup
 * JD-Core Version:    0.7.0.1
 */