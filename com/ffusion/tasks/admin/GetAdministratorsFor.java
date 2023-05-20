package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAdministratorsFor
  extends BaseTask
  implements AdminTask
{
  private String aeH = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    BusinessEmployees localBusinessEmployees1 = null;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    BusinessEmployees localBusinessEmployees2 = new BusinessEmployees(localLocale);
    if ((this.aeH == null) || (this.aeH.equals("0")))
    {
      this.error = 4513;
      return super.getTaskErrorURL();
    }
    try
    {
      int i = Integer.parseInt(this.aeH);
      EntitlementAdmins localEntitlementAdmins = Entitlements.getAdminsForGroup(i);
      if ((localEntitlementAdmins != null) && (localEntitlementAdmins.size() > 0))
      {
        Iterator localIterator1 = localEntitlementAdmins.iterator();
        while (localIterator1.hasNext())
        {
          EntitlementAdmin localEntitlementAdmin = (EntitlementAdmin)localIterator1.next();
          localBusinessEmployees1 = UserAdmin.getBusinessEmployeesByEntGroupId(localSecureUser, localEntitlementAdmin.getGranteeGroupId(), localHashMap);
          EntitlementGroupMember localEntitlementGroupMember = null;
          if (localEntitlementAdmin.getGranteeMemberType() != null)
          {
            localEntitlementGroupMember = new EntitlementGroupMember();
            localEntitlementGroupMember.setMemberType(localEntitlementAdmin.getGranteeMemberType());
            localEntitlementGroupMember.setMemberSubType(localEntitlementAdmin.getGranteeMemberSubType());
            localEntitlementGroupMember.setId(localEntitlementAdmin.getGranteeMemberId());
            localEntitlementGroupMember.setEntitlementGroupId(localEntitlementAdmin.getGranteeGroupId());
          }
          if ((localBusinessEmployees1 != null) && (localBusinessEmployees1.size() > 0))
          {
            Iterator localIterator2 = localBusinessEmployees1.iterator();
            while (localIterator2.hasNext())
            {
              BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator2.next();
              if (((localEntitlementGroupMember == null) || (localEntitlementGroupMember.equals(localBusinessEmployee.getEntitlementGroupMember()))) && (localBusinessEmployees2.getByID(localBusinessEmployee.getId()) == null)) {
                localBusinessEmployees2.add(localBusinessEmployee);
              }
            }
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    catch (Exception localException)
    {
      this.error = 26001;
      return super.getServiceErrorURL();
    }
    if ((this.error == 0) || (this.error == 26000))
    {
      localHttpSession.setAttribute("Administrators", localBusinessEmployees2);
      return super.getSuccessURL();
    }
    return super.getSuccessURL();
  }
  
  public void setGroupId(String paramString)
  {
    this.aeH = paramString;
  }
  
  public String getGroupId()
  {
    return this.aeH;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetAdministratorsFor
 * JD-Core Version:    0.7.0.1
 */