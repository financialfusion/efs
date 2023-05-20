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

public class ModifyAdministratorGroup
  extends BaseTask
  implements Task
{
  private int H;
  private int F;
  private int K;
  private boolean E;
  private boolean I;
  private static final String J = "FALSE";
  private static final String G = "TRUE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.H == 0)
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    if (this.F == 0)
    {
      this.error = 35032;
      return this.taskErrorURL;
    }
    if (this.K == 0)
    {
      this.error = 35033;
      return this.taskErrorURL;
    }
    if ((!this.I) && (!this.E))
    {
      this.error = 35036;
      return this.taskErrorURL;
    }
    EntitlementAdmin localEntitlementAdmin1 = new EntitlementAdmin(this.H, this.F);
    EntitlementAdmin localEntitlementAdmin2 = new EntitlementAdmin(this.K, this.F);
    if (this.I) {
      localEntitlementAdmin2.setExtend(this.I);
    }
    if (this.E) {
      localEntitlementAdmin2.setAdminister(this.E);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Entitlements.modifyAdministratorGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementAdmin1, localEntitlementAdmin2);
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
    this.H = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.H);
  }
  
  public void setTargetId(String paramString)
  {
    this.F = Integer.parseInt(paramString);
  }
  
  public String getTargetId()
  {
    return Integer.toString(this.F);
  }
  
  public void setNewGroupId(String paramString)
  {
    this.K = Integer.parseInt(paramString);
  }
  
  public String getNewGroupId()
  {
    return Integer.toString(this.K);
  }
  
  public void setCanAdmin(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.E = true;
    } else {
      this.E = false;
    }
  }
  
  public String getCanAdmin()
  {
    if (this.E) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCanExtend(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.I = true;
    } else {
      this.I = false;
    }
  }
  
  public String getCanExtend()
  {
    if (this.I) {
      return "TRUE";
    }
    return "FALSE";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.ModifyAdministratorGroup
 * JD-Core Version:    0.7.0.1
 */