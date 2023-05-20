package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
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

public class GetBusinessByEmployee
  extends BaseTask
{
  String RF;
  String RE;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localHttpSession.removeAttribute("Business");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBusiness.setStatus(this.RE);
    BusinessEmployee localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBusiness.setBankId(localSecureUser.getBankID());
    localBusinessEmployee.setId(this.RF);
    Businesses localBusinesses = null;
    try
    {
      HashMap localHashMap = null;
      localBusinesses = com.ffusion.csil.core.Business.getFilteredBusinesses(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) && (localBusinesses != null))
    {
      Iterator localIterator = localBusinesses.iterator();
      if (localIterator.hasNext()) {
        localHttpSession.setAttribute("Business", localIterator.next());
      }
    }
    return str;
  }
  
  public void setBusinessEmployeeId(String paramString)
  {
    this.RF = paramString;
  }
  
  public void setStatus(String paramString)
  {
    this.RE = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusinessByEmployee
 * JD-Core Version:    0.7.0.1
 */