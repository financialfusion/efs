package com.ffusion.tasks.user;

import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBusinessEmployee
  extends BusinessEmployee
  implements UserTask
{
  protected String businessEmployeeSessionName = "BusinessEmployee";
  protected String businessEmployeesSessionName = "BusinessEmployees";
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    BusinessEmployee localBusinessEmployee = null;
    localHttpSession.removeAttribute(this.businessEmployeeSessionName);
    BusinessEmployees localBusinessEmployees = (BusinessEmployees)localHttpSession.getAttribute(this.businessEmployeesSessionName);
    if (localBusinessEmployees != null)
    {
      localBusinessEmployee = localBusinessEmployees.getByID(getId());
      if (localBusinessEmployee != null) {
        localHttpSession.setAttribute(this.businessEmployeeSessionName, localBusinessEmployee);
      }
      str = this.successURL;
    }
    else
    {
      this.error = 3521;
    }
    return str;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setBusinessEmployeeSessionName(String paramString)
  {
    this.businessEmployeeSessionName = paramString;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.businessEmployeesSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetBusinessEmployee
 * JD-Core Version:    0.7.0.1
 */