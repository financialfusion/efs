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

public class DeleteAdministratorGroup
  extends BaseTask
  implements Task
{
  private int D;
  private int C;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.D == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    if (this.C == 0)
    {
      this.error = 35017;
      return this.taskErrorURL;
    }
    EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin(this.D, this.C);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Entitlements.deleteAdministratorGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementAdmin);
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
    this.D = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.D);
  }
  
  public void setCanAdminGroupId(String paramString)
  {
    this.C = Integer.parseInt(paramString);
  }
  
  public String getCanAdminGroupId()
  {
    return Integer.toString(this.C);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.DeleteAdministratorGroup
 * JD-Core Version:    0.7.0.1
 */