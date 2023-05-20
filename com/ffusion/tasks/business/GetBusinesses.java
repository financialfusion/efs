package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
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

public class GetBusinesses
  extends BaseTask
  implements BusinessTask
{
  String gW;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Businesses localBusinesses = new Businesses((Locale)localHttpSession.getAttribute("java.util.Locale"));
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBusiness.setStatus(this.gW);
    localBusiness.setBankId((String)localHttpSession.getAttribute("BankId"));
    try
    {
      HashMap localHashMap = new HashMap(1);
      localHashMap.put("BUSINESSES", localBusinesses);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localBusinesses = com.ffusion.csil.core.Business.getFilteredBusinesses(localSecureUser, localBusiness, null, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Businesses", localBusinesses);
      localHttpSession.setAttribute("BusinessesSearchList", localBusinesses);
      str = this.successURL;
    }
    return str;
  }
  
  public void setStatus(String paramString)
  {
    this.gW = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusinesses
 * JD-Core Version:    0.7.0.1
 */