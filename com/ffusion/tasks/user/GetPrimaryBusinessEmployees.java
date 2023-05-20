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
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPrimaryBusinessEmployees
  extends BaseTask
  implements UserTask
{
  private String vx = "UserService";
  private String vv = "PrimaryBusinessEmployee";
  private String vw = "SecondaryBusinessEmployee";
  private int vt;
  private String vu;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHttpSession.removeAttribute(this.vv);
    localHttpSession.removeAttribute(this.vw);
    str = jdMethod_for(localHttpSession, "1", this.vv, localSecureUser, null);
    if ((this.error == 0) || (this.error == 18005))
    {
      str = jdMethod_for(localHttpSession, "2", this.vw, localSecureUser, null);
      if (this.error == 18005)
      {
        this.error = 0;
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setPrimaryBusinessEmployeeSessionName(String paramString)
  {
    this.vv = paramString;
  }
  
  public void setSecondaryBusinessEmployeeSessionName(String paramString)
  {
    this.vw = paramString;
  }
  
  public void setBusinessId(String paramString)
  {
    try
    {
      this.vt = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setBankId(String paramString)
  {
    this.vu = paramString;
  }
  
  private String jdMethod_for(HttpSession paramHttpSession, String paramString1, String paramString2, SecureUser paramSecureUser, HashMap paramHashMap)
  {
    String str = this.taskErrorURL;
    this.error = 0;
    BusinessEmployee localBusinessEmployee = new BusinessEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    localBusinessEmployee.setBusinessId(this.vt);
    localBusinessEmployee.set("BANK_ID", this.vu);
    localBusinessEmployee.setPrimaryUser(paramString1);
    BusinessEmployees localBusinessEmployees = new BusinessEmployees((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    try
    {
      localBusinessEmployees = UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      Iterator localIterator = localBusinessEmployees.iterator();
      if (localIterator.hasNext()) {
        paramHttpSession.setAttribute(paramString2, localIterator.next());
      }
      str = this.successURL;
    }
    return str;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.vx = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetPrimaryBusinessEmployees
 * JD-Core Version:    0.7.0.1
 */