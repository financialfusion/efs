package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFilteredBusinessEmployeesByEntGroupId
  extends BaseTask
  implements UserTask
{
  private String wp = "BusinessEmployees";
  int wk;
  String wn;
  String wl;
  String wo;
  String wm;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    BusinessEmployees localBusinessEmployees = null;
    BusinessEmployee localBusinessEmployee = null;
    if (((this.wn != null) && (this.wn.trim().length() != 0)) || ((this.wl != null) && (this.wl.trim().length() != 0)) || ((this.wm != null) && (this.wm.trim().trim().length() != 0)))
    {
      localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setFirstName(this.wn);
      localBusinessEmployee.setLastName(this.wl);
      localBusinessEmployee.setUserName(this.wo);
      if ((this.wm != null) && (this.wm.length() > 0)) {
        localBusinessEmployee.setAffiliateBankID(this.wm);
      }
    }
    if (localBusinessEmployee == null)
    {
      localHttpSession.removeAttribute(this.wp);
      return str;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if (this.wk != 0) {
        localBusinessEmployees = UserAdmin.getFilteredBusinessEmployeesByEntGroupId(localSecureUser, this.wk, localBusinessEmployee, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      if (localBusinessEmployees != null) {
        localHttpSession.setAttribute(this.wp, localBusinessEmployees);
      }
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.wp = paramString;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    try
    {
      this.wk = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setFirstName(String paramString)
  {
    this.wn = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.wl = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.wo = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.wm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetFilteredBusinessEmployeesByEntGroupId
 * JD-Core Version:    0.7.0.1
 */