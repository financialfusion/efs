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
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFilteredBusinessEmployeesByEntGroups
  extends BaseTask
  implements UserTask
{
  private String xj = "BusinessEmployees";
  private String xe = "Entitlement_EntitlementGroups";
  String xh;
  String xf;
  String xi;
  String xg;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    Object localObject = null;
    BusinessEmployee localBusinessEmployee1 = null;
    if (((this.xh != null) && (this.xh.trim().length() != 0)) || ((this.xf != null) && (this.xf.trim().length() != 0)) || ((this.xg != null) && (this.xg.trim().trim().length() != 0)))
    {
      localBusinessEmployee1 = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee1.setFirstName(this.xh);
      localBusinessEmployee1.setLastName(this.xf);
      localBusinessEmployee1.setUserName(this.xi);
      if ((this.xg != null) && (this.xg.length() > 0)) {
        localBusinessEmployee1.setAffiliateBankID(this.xg);
      }
    }
    if (localBusinessEmployee1 == null)
    {
      localHttpSession.removeAttribute(this.xj);
      return str;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroups localEntitlementGroups = (EntitlementGroups)localHttpSession.getAttribute(this.xe);
    ListIterator localListIterator1 = localEntitlementGroups.listIterator();
    while (localListIterator1.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localListIterator1.next();
      int i = localEntitlementGroup.getGroupId();
      try
      {
        BusinessEmployees localBusinessEmployees = UserAdmin.getFilteredBusinessEmployeesByEntGroupId(localSecureUser, i, localBusinessEmployee1, localHashMap);
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
            BusinessEmployee localBusinessEmployee2 = (BusinessEmployee)localListIterator2.next();
            j = 0;
            localListIterator3 = localObject.listIterator();
            while (localListIterator3.hasNext()) {
              if (localBusinessEmployee2.getId() == ((BusinessEmployee)localListIterator3.next()).getId()) {
                j = 1;
              }
            }
            if (j == 0) {
              localObject.add(localBusinessEmployee2);
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
      localHttpSession.setAttribute(this.xj, localObject);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.xj = paramString;
  }
  
  public void setEntitlementGroupsSessionName(String paramString)
  {
    this.xe = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.xh = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.xf = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.xi = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.xg = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetFilteredBusinessEmployeesByEntGroups
 * JD-Core Version:    0.7.0.1
 */