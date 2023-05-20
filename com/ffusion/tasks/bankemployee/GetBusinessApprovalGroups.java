package com.ffusion.tasks.bankemployee;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessApprovalGroups
  extends BaseTask
  implements BankEmployeeTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementGroups localEntitlementGroups1 = new EntitlementGroups();
    Entitlement localEntitlement = new Entitlement("Bank Approve Limits", null, null);
    try
    {
      EntitlementGroup localEntitlementGroup = null;
      EntitlementGroups localEntitlementGroups2 = Entitlements.getEntitlementGroupsByType("EmployeeType");
      Iterator localIterator = localEntitlementGroups2.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        if (Entitlements.checkEntitlement(localEntitlementGroup.getGroupId(), localEntitlement)) {
          localEntitlementGroups1.add(localEntitlementGroup);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("BusinessApprovalGroups", localEntitlementGroups1);
      str = this.successURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetBusinessApprovalGroups
 * JD-Core Version:    0.7.0.1
 */