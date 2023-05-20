package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.ListIterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessEmployeesByEntGroups
  extends BaseTask
  implements UserTask
{
  private String xt = "BusinessEmployees";
  private String xs = "Entitlement_EntitlementGroups";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    Object localObject = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroups localEntitlementGroups = (EntitlementGroups)localHttpSession.getAttribute(this.xs);
    ListIterator localListIterator1 = localEntitlementGroups.listIterator();
    while (localListIterator1.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localListIterator1.next();
      int i = localEntitlementGroup.getGroupId();
      try
      {
        BusinessEmployees localBusinessEmployees = UserAdmin.getBusinessEmployeesByEntGroupId(localSecureUser, i, localHashMap);
        if (localObject == null)
        {
          localObject = localBusinessEmployees;
        }
        else
        {
          ListIterator localListIterator2 = localBusinessEmployees.listIterator();
          ListIterator localListIterator3 = null;
          int j = 0;
          while (localListIterator2.hasNext())
          {
            BusinessEmployee localBusinessEmployee = (BusinessEmployee)localListIterator2.next();
            j = 0;
            localListIterator3 = localObject.listIterator();
            while (localListIterator3.hasNext()) {
              if (localBusinessEmployee.getId() == ((BusinessEmployee)localListIterator3.next()).getId()) {
                j = 1;
              }
            }
            if (j == 0) {
              localObject.add(localBusinessEmployee);
            }
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.xt, localObject);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.xt = paramString;
  }
  
  public void setEntitlementGroupsSessionName(String paramString)
  {
    this.xs = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetBusinessEmployeesByEntGroups
 * JD-Core Version:    0.7.0.1
 */