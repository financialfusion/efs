package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGroupsAdministratorOf
  extends BaseTask
  implements BankEmployeeTask
{
  private boolean ul = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      EntitlementAdmin localEntitlementAdmin = null;
      EntitlementGroup localEntitlementGroup = null;
      HashMap localHashMap = new HashMap(1);
      if (this.ul) {
        localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localEntitlementGroups = BankEmployeeAdmin.getJobTypes(localSecureUser, localHashMap);
      localEntitlementAdmin = new EntitlementAdmin(com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(localSecureUser), 0);
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        localEntitlementAdmin.setTargetGroupId(localEntitlementGroup.getGroupId());
        if (!Entitlements.canAdminister(localEntitlementAdmin)) {
          localIterator.remove();
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this.ul = false;
    }
    if ((this.error == 0) || (this.error == 12))
    {
      localHttpSession.setAttribute("AdministratorOfGroups", localEntitlementGroups);
      str = this.successURL;
    }
    return str;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.ul = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.ul);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetGroupsAdministratorOf
 * JD-Core Version:    0.7.0.1
 */