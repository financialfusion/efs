package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAncestorGroupByType
  extends BaseTask
  implements Task
{
  private static final int aKp = -1;
  private String aKq = null;
  private String aKn = "Entitlement_EntitlementGroup";
  private int aKo = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute("Entitlement_EntitlementGroup");
      if ((localEntitlementGroup == null) || (localEntitlementGroup.getGroupId() != this.aKo)) {
        if (this.aKo == -1) {
          localEntitlementGroup = Entitlements.getEntitlementGroup(localSecureUser.getEntitlementID());
        }
      }
      for (localEntitlementGroup = Entitlements.getEntitlementGroup(this.aKo); !localEntitlementGroup.getEntGroupType().equals(this.aKq); localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId())) {
        if (localEntitlementGroup.getParentId() == 0)
        {
          localEntitlementGroup = null;
          break;
        }
      }
      if (localEntitlementGroup == null) {
        localHttpSession.removeAttribute(this.aKn);
      } else {
        localHttpSession.setAttribute(this.aKn, localEntitlementGroup);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    this.aKn = "Entitlement_EntitlementGroup";
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    this.aKo = Integer.parseInt(paramString);
  }
  
  public void setGroupType(String paramString)
  {
    this.aKq = paramString;
  }
  
  public void setEntGroupSessionName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.aKn = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetAncestorGroupByType
 * JD-Core Version:    0.7.0.1
 */