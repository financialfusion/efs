package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFilteredBusinessesCount
  extends BaseTask
  implements BusinessTask
{
  String hf;
  String g8;
  String he;
  String hb;
  String ha;
  String hc;
  String g9;
  private String hd = "BusinessesSearchListCount";
  
  public void setBusinessName(String paramString)
  {
    this.hf = paramString;
  }
  
  public void setStatus(String paramString)
  {
    this.g8 = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.he = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.hb = paramString;
  }
  
  public void setPersonalBanker(String paramString)
  {
    this.ha = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.g9 = paramString;
  }
  
  public void setAccountRep(String paramString)
  {
    this.hc = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Businesses localBusinesses = new Businesses((Locale)localHttpSession.getAttribute("java.util.Locale"));
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBusiness.setBankId(localSecureUser.getBankID());
    localBusiness.setBusinessName(this.hf);
    localBusiness.setStatus(this.g8);
    localBusiness.setPersonalBanker(this.ha);
    localBusiness.setAccountRep(this.hc);
    if (this.g9 != null) {
      localBusiness.setAffiliateBankID(this.g9);
    }
    BusinessEmployee localBusinessEmployee = null;
    if ((this.he != null) || (this.hb != null))
    {
      localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setFirstName(this.he);
      localBusinessEmployee.setLastName(this.hb);
    }
    int i = 0;
    try
    {
      HashMap localHashMap = new HashMap(2);
      localHashMap.put("BUSINESSES", localBusinesses);
      i = com.ffusion.csil.core.Business.getFilteredBusinessesCount(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.hd, String.valueOf(i));
      str = this.successURL;
    }
    return str;
  }
  
  public void setSearchListName(String paramString)
  {
    this.hd = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetFilteredBusinessesCount
 * JD-Core Version:    0.7.0.1
 */