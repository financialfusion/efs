package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAdministratorGroup
  extends BaseTask
  implements Task
{
  private int K3;
  private int K2;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.K3 == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    if (this.K2 == 0)
    {
      this.error = 35017;
      return this.taskErrorURL;
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(this.K3, this.K2);
    localEntitlementAdmin.setAdminister(true);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Entitlements.addAdministratorGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementAdmin);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setGroupId(String paramString)
  {
    this.K3 = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.K3);
  }
  
  public void setCanAdminGroupId(String paramString)
  {
    this.K2 = Integer.parseInt(paramString);
  }
  
  public String getCanAdminGroupId()
  {
    return Integer.toString(this.K2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.AddAdministratorGroup
 * JD-Core Version:    0.7.0.1
 */