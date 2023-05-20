package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
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

public class GetEmployeeJobGroupByEntitlement
  extends BaseTask
  implements BankEmployeeTask
{
  private String tv = "EmployeeJobGroups";
  private String tw;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.tw == null) || (this.tw.length() == 0))
    {
      this.error = 5526;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Object localObject = null;
    try
    {
      Entitlement localEntitlement = new Entitlement(this.tw, null, null);
      EntitlementGroups localEntitlementGroups1 = Entitlements.getEntitlementGroupsByType("EmployeeType");
      EntitlementGroups localEntitlementGroups2 = new EntitlementGroups();
      Iterator localIterator = localEntitlementGroups1.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
        if (Entitlements.checkEntitlement(localEntitlementGroup.getGroupId(), localEntitlement)) {
          localEntitlementGroups2.add(localEntitlementGroup);
        }
      }
      localHttpSession.setAttribute(this.tv, localEntitlementGroups2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setJobEntitlement(String paramString)
  {
    this.tw = paramString;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.tv = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.tv;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetEmployeeJobGroupByEntitlement
 * JD-Core Version:    0.7.0.1
 */