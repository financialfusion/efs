package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CanDeleteEntitlementGroup
  extends BaseTask
  implements AdminTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute("Entitlement_EntitlementGroup");
    if (localEntitlementGroup == null)
    {
      this.error = 4524;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      EntitlementGroups localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(localEntitlementGroup.getGroupId());
      if (localEntitlementGroups.size() > 0)
      {
        this.error = 4528;
        return this.taskErrorURL;
      }
      int i = Entitlements.getNumMembers(localEntitlementGroup.getGroupId());
      if (i > 0)
      {
        this.error = 4527;
        return this.taskErrorURL;
      }
      if (Approvals.isGroupInUse(localEntitlementGroup.getGroupId(), null))
      {
        this.error = 4525;
        return this.taskErrorURL;
      }
      if ("Division".equals(localEntitlementGroup.getEntGroupType()))
      {
        LocationSearchCriteria localLocationSearchCriteria = new LocationSearchCriteria();
        localLocationSearchCriteria.setDivisionID(localEntitlementGroup.getGroupId());
        LocationSearchResults localLocationSearchResults = CashCon.getLocations(localSecureUser, localLocationSearchCriteria, new HashMap());
        if ((localLocationSearchResults != null) && (localLocationSearchResults.size() > 0))
        {
          this.error = 4546;
          return this.taskErrorURL;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.CanDeleteEntitlementGroup
 * JD-Core Version:    0.7.0.1
 */