package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGroupsAdministeredBy
  extends BaseTask
  implements Task
{
  private int ag = 0;
  private String af;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if ((this.af == null) && (this.ag == 0))
    {
      this.error = 35003;
      return this.taskErrorURL;
    }
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      if (this.af == null)
      {
        localEntitlementGroups = Entitlements.getGroupsAdministeredBy(this.ag);
      }
      else
      {
        Object localObject = localHttpSession.getAttribute(this.af);
        EntitlementGroupMember localEntitlementGroupMember = null;
        if ((localObject instanceof EntitlementGroupMember)) {
          localEntitlementGroupMember = (EntitlementGroupMember)localObject;
        } else if ((localObject instanceof BusinessEmployee)) {
          localEntitlementGroupMember = ((BusinessEmployee)localObject).getEntitlementGroupMember();
        } else if ((localObject instanceof SecureUser)) {
          localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject);
        }
        if (localEntitlementGroupMember == null)
        {
          this.error = 35026;
          return this.taskErrorURL;
        }
        localEntitlementGroups = Entitlements.getGroupsAdministeredBy(localEntitlementGroupMember);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Entitlement_EntitlementGroups", localEntitlementGroups);
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
    this.af = null;
    this.ag = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.ag);
  }
  
  public void setUserSessionName(String paramString)
  {
    this.ag = 0;
    this.af = paramString;
  }
  
  public String getUserSessionName()
  {
    return this.af;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetGroupsAdministeredBy
 * JD-Core Version:    0.7.0.1
 */