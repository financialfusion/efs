package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
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

public class GetFilteredBusinessEmployees
  extends BaseTask
  implements UserTask
{
  private String ve = "BusinessEmployees";
  protected String dataNotFoundURL;
  String vf;
  String u9;
  String vc;
  String va;
  String vd;
  String vb;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    BusinessEmployees localBusinessEmployees = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = null;
    BusinessEmployee localBusinessEmployee = null;
    if (this.vf != null)
    {
      localBusiness = new Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusiness.setBusinessName(this.vf);
    }
    if ((this.vc != null) || (this.va != null) || (this.vb != null))
    {
      localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setFirstName(this.vc);
      localBusinessEmployee.setLastName(this.va);
      localBusinessEmployee.setUserName(this.vd);
      if ((this.vb != null) && (this.vb.length() > 0)) {
        localBusinessEmployee.setAffiliateBankID(this.vb);
      }
    }
    if (this.u9 != null)
    {
      if (localBusinessEmployee == null) {
        localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      }
      localBusinessEmployee.setBusinessId(this.u9);
    }
    try
    {
      localBusinessEmployees = UserAdmin.getFilteredBusinessEmployees(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.ve, localBusinessEmployees);
      str = this.successURL;
    }
    else if (this.error == 3009)
    {
      if ((this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() > 0)) {
        str = this.dataNotFoundURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBusinessEmployeesSessionName(String paramString)
  {
    this.ve = paramString;
  }
  
  public void setDataNotFoundURL(String paramString)
  {
    this.dataNotFoundURL = paramString;
  }
  
  public void setBusinessName(String paramString)
  {
    this.vf = paramString;
  }
  
  public void setBusinessId(String paramString)
  {
    this.u9 = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.vc = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.va = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.vd = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.vb = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetFilteredBusinessEmployees
 * JD-Core Version:    0.7.0.1
 */