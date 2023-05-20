package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAdminsForGroup
  extends BaseTask
  implements Task
{
  private String MU = null;
  private String MV = "EntitlementAdmins";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementAdmins localEntitlementAdmins = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((this.MU == null) || (this.MU.equals("0")))
    {
      this.error = 35003;
      return super.getTaskErrorURL();
    }
    try
    {
      int i = Integer.parseInt(this.MU);
      localEntitlementAdmins = Entitlements.getAdminsForGroup(i);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    catch (Exception localException)
    {
      this.error = 26001;
      return super.getServiceErrorURL();
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.MV, localEntitlementAdmins);
      return super.getSuccessURL();
    }
    return super.getSuccessURL();
  }
  
  public void setGroupId(String paramString)
  {
    this.MU = paramString;
  }
  
  public String getGroupId()
  {
    return this.MU;
  }
  
  public void setSessionName(String paramString)
  {
    this.MV = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetAdminsForGroup
 * JD-Core Version:    0.7.0.1
 */