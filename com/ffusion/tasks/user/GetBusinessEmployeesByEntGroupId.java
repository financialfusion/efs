package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessEmployeesByEntGroupId
  extends BaseTask
  implements UserTask
{
  private String wj = "BusinessEmployees";
  int wi;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    BusinessEmployees localBusinessEmployees = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if (this.wi != 0) {
        localBusinessEmployees = UserAdmin.getBusinessEmployeesByEntGroupId(localSecureUser, this.wi, localHashMap);
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
        localHttpSession.setAttribute(this.wj, localBusinessEmployees);
      }
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.wj = paramString;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    try
    {
      this.wi = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetBusinessEmployeesByEntGroupId
 * JD-Core Version:    0.7.0.1
 */