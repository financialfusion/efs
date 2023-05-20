package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSupervisorFor
  extends BaseTask
  implements AdminTask
{
  private String Xj = null;
  private BusinessEmployee Xk;
  private boolean Xl = false;
  private String Xm = "Supervisor";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    Object localObject = null;
    this.Xk = null;
    this.Xl = false;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if ((this.Xj == null) || (this.Xj.equals("0")))
    {
      this.error = 4513;
      return super.getTaskErrorURL();
    }
    try
    {
      int i = Integer.parseInt(this.Xj);
      EntitlementAdmins localEntitlementAdmins = Entitlements.getAdminsForGroup(i);
      if ((localEntitlementAdmins != null) && (localEntitlementAdmins.size() > 0))
      {
        Iterator localIterator = localEntitlementAdmins.iterator();
        while (localIterator.hasNext())
        {
          EntitlementAdmin localEntitlementAdmin = (EntitlementAdmin)localIterator.next();
          String str1 = localEntitlementAdmin.getGranteeMemberId();
          if (str1 != null)
          {
            String str2 = localEntitlementAdmin.getGranteeMemberType();
            String str3 = localEntitlementAdmin.getGranteeMemberSubType();
            if ((str2 != null) && (str2.equals("USER")) && (str3 != null) && (str3.equals(String.valueOf(1))) && (localEntitlementAdmin.canAdminister()))
            {
              BusinessEmployee localBusinessEmployee = new BusinessEmployee();
              localBusinessEmployee.setBankId(localSecureUser.getBankID());
              localBusinessEmployee.setId(str1);
              BusinessEmployees localBusinessEmployees = UserAdmin.getBusinessEmployees(localSecureUser, localBusinessEmployee, null);
              if ((localBusinessEmployees != null) && (localBusinessEmployees.size() > 0))
              {
                this.Xk = ((BusinessEmployee)localBusinessEmployees.get(0));
                this.Xl = true;
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
    if (this.error == 0)
    {
      if (this.Xl) {
        localHttpSession.setAttribute(this.Xm, this.Xk);
      }
      return super.getSuccessURL();
    }
    return super.getSuccessURL();
  }
  
  public void setGroupId(String paramString)
  {
    this.Xj = paramString;
  }
  
  public String getGroupId()
  {
    return this.Xj;
  }
  
  public String getName()
  {
    if (this.Xk != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(this.Xk.getFirstName());
      localStringBuffer.append(' ');
      localStringBuffer.append(this.Xk.getLastName());
      return localStringBuffer.toString();
    }
    return "";
  }
  
  public String getSupervisorFound()
  {
    return this.Xl ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
  }
  
  public void setSessionName(String paramString)
  {
    this.Xm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetSupervisorFor
 * JD-Core Version:    0.7.0.1
 */