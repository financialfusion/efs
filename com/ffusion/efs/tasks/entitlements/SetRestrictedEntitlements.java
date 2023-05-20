package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRestrictedEntitlements
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute("Entitlement_EntitlementGroup");
    if (localEntitlementGroup == null)
    {
      this.error = 35001;
      return this.taskErrorURL;
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = (com.ffusion.csil.beans.entitlements.Entitlements)localHttpSession.getAttribute("Entitlement_Restricted_list");
    if (localEntitlements == null)
    {
      this.error = 35023;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup.getGroupId(), localEntitlements);
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.SetRestrictedEntitlements
 * JD-Core Version:    0.7.0.1
 */