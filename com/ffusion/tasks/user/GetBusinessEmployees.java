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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessEmployees
  extends BaseTask
  implements UserTask
{
  private String v4 = "UserService";
  private String v3 = "SearchBusinessEmployee";
  private String v2 = "BusinessEmployees";
  protected String dataNotFoundURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute(this.v3);
    BusinessEmployees localBusinessEmployees = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localBusinessEmployees = UserAdmin.getBusinessEmployees(localSecureUser, localBusinessEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.v2, localBusinessEmployees);
      str = this.successURL;
    }
    else if (this.error == 3009)
    {
      if ((this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() == 0)) {
        str = this.dataNotFoundURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSearchBusinessEmployeeSessionName(String paramString)
  {
    this.v3 = paramString;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.v2 = paramString;
  }
  
  public void setDataNotFoundURL(String paramString)
  {
    this.dataNotFoundURL = paramString;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.v4 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetBusinessEmployees
 * JD-Core Version:    0.7.0.1
 */