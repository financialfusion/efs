package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
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

public class GetFilteredBusinessEmployeesCount
  extends BaseTask
  implements UserTask
{
  private String wG = "BusinessEmployeesCount";
  protected String dataNotFoundURL;
  String wI;
  String wD;
  String wF;
  String wE;
  String wH;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.taskErrorURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    String str2 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = null;
    BusinessEmployee localBusinessEmployee = null;
    if ((this.wI != null) || (this.wD != null))
    {
      localBusiness = new Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
      if (this.wI != null) {
        localBusiness.setBusinessName(this.wI);
      }
      if (this.wD != null) {
        localBusiness.setAffiliateBankID(this.wD);
      }
    }
    if ((this.wF != null) || (this.wE != null) || (this.wH != null))
    {
      localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setFirstName(this.wF);
      localBusinessEmployee.setLastName(this.wE);
      localBusinessEmployee.setUserName(this.wH);
    }
    try
    {
      str2 = UserAdmin.getFilteredBusinessEmployeesCount(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.wG, str2);
      str1 = this.successURL;
    }
    else if (this.error == 3009)
    {
      if ((this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() > 0)) {
        str1 = this.dataNotFoundURL;
      } else {
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setCountSessionName(String paramString)
  {
    this.wG = paramString;
  }
  
  public void setDataNotFoundURL(String paramString)
  {
    this.dataNotFoundURL = paramString;
  }
  
  public void setBusinessName(String paramString)
  {
    this.wI = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.wD = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.wF = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.wE = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.wH = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetFilteredBusinessEmployeesCount
 * JD-Core Version:    0.7.0.1
 */