package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessEmployeesByEntAdmins
  extends BaseTask
  implements UserTask
{
  private String xl = "BusinessEmployees";
  private String xm = "EntitlementAdmins";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    BusinessEmployees localBusinessEmployees1 = new BusinessEmployees(localLocale);
    EntitlementAdmins localEntitlementAdmins1 = (EntitlementAdmins)localHttpSession.getAttribute(this.xm);
    ListIterator localListIterator1 = localEntitlementAdmins1.listIterator();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    EntitlementAdmins localEntitlementAdmins2 = new EntitlementAdmins();
    try
    {
      Object localObject2;
      Object localObject3;
      Object localObject4;
      while (localListIterator1.hasNext())
      {
        localObject1 = (EntitlementAdmin)localListIterator1.next();
        if ((((EntitlementAdmin)localObject1).getGranteeMemberType() == null) && (((EntitlementAdmin)localObject1).getGranteeMemberSubType() == null) && (((EntitlementAdmin)localObject1).getGranteeMemberId() == null))
        {
          int i = ((EntitlementAdmin)localObject1).getGranteeGroupId();
          BusinessEmployees localBusinessEmployees2 = UserAdmin.getBusinessEmployeesByEntGroupId(localSecureUser, i, localHashMap);
          localObject2 = localBusinessEmployees2.listIterator();
          localObject3 = null;
          int m = 0;
          while (((ListIterator)localObject2).hasNext())
          {
            localObject4 = (BusinessEmployee)((ListIterator)localObject2).next();
            m = 0;
            localObject3 = localBusinessEmployees1.listIterator();
            while (((ListIterator)localObject3).hasNext()) {
              if (((BusinessEmployee)localObject4).getId() == ((BusinessEmployee)((ListIterator)localObject3).next()).getId()) {
                m = 1;
              }
            }
            if (m == 0) {
              localBusinessEmployees1.add(localObject4);
            }
          }
          localArrayList2.add(new Integer(i));
        }
        else
        {
          localEntitlementAdmins2.add(localObject1);
          Integer localInteger = new Integer(((EntitlementAdmin)localObject1).getGranteeGroupId());
          if (!localArrayList1.contains(localInteger)) {
            localArrayList1.add(localInteger);
          }
        }
      }
      Object localObject1 = localArrayList2.listIterator();
      while (((ListIterator)localObject1).hasNext())
      {
        int j = localArrayList1.indexOf((Integer)((ListIterator)localObject1).next());
        if (j != -1) {
          localArrayList1.remove(j);
        }
      }
      ListIterator localListIterator2 = localArrayList1.listIterator();
      while (localListIterator2.hasNext())
      {
        int k = ((Integer)localListIterator2.next()).intValue();
        localObject2 = UserAdmin.getBusinessEmployeesByEntGroupId(localSecureUser, k, localHashMap);
        localListIterator1 = localEntitlementAdmins2.listIterator();
        while (localListIterator1.hasNext())
        {
          localObject3 = (EntitlementAdmin)localListIterator1.next();
          ListIterator localListIterator3 = ((BusinessEmployees)localObject2).listIterator();
          localObject4 = null;
          int n = 0;
          while (localListIterator3.hasNext())
          {
            BusinessEmployee localBusinessEmployee = (BusinessEmployee)localListIterator3.next();
            if (("USER".equals(((EntitlementAdmin)localObject3).getGranteeMemberType())) && (Integer.toString(1).equals(((EntitlementAdmin)localObject3).getGranteeMemberSubType())) && (localBusinessEmployee.getId().equals(((EntitlementAdmin)localObject3).getGranteeMemberId())) && (localBusinessEmployee.getEntitlementGroupId() == ((EntitlementAdmin)localObject3).getGranteeGroupId()))
            {
              n = 0;
              localObject4 = localBusinessEmployees1.listIterator();
              while (((ListIterator)localObject4).hasNext()) {
                if (localBusinessEmployee.getId() == ((BusinessEmployee)((ListIterator)localObject4).next()).getId()) {
                  n = 1;
                }
              }
              if (n == 0) {
                localBusinessEmployees1.add(localBusinessEmployee);
              }
            }
          }
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
      localHttpSession.setAttribute(this.xl, localBusinessEmployees1);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.xl = paramString;
  }
  
  public void setEntitlementAdminsSessionName(String paramString)
  {
    this.xm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetBusinessEmployeesByEntAdmins
 * JD-Core Version:    0.7.0.1
 */