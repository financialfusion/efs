package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessEmployee
  extends BaseTask
  implements UserTask
{
  private String xk = null;
  protected String dataNotFoundURL = this.serviceErrorURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessEmployee localBusinessEmployee = new BusinessEmployee();
    localBusinessEmployee.setId(this.xk);
    localBusinessEmployee.set("BANK_ID", localSecureUser.getBankID());
    BusinessEmployees localBusinessEmployees = null;
    try
    {
      localBusinessEmployees = UserAdmin.getBusinessEmployees(localSecureUser, localBusinessEmployee, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    localBusinessEmployee = localBusinessEmployees.getByID(String.valueOf(this.xk));
    if ((this.error == 0) && (localBusinessEmployee == null)) {
      this.error = 3009;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("BusinessEmployee", localBusinessEmployee);
      str = this.successURL;
    }
    else if ((this.error == 3009) && (this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() != 0))
    {
      str = this.dataNotFoundURL;
    }
    return str;
  }
  
  public void setProfileId(String paramString)
  {
    this.xk = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetBusinessEmployee
 * JD-Core Version:    0.7.0.1
 */