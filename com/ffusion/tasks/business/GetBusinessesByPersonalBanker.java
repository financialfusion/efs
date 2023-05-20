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

public class GetBusinessesByPersonalBanker
  extends BaseTask
  implements BusinessTask
{
  String g3;
  String gZ;
  String g4;
  private boolean g1 = false;
  String g0 = "BusinessesSearchListByPersonalBanker";
  String g2 = "TempAssignedPbBusinesses";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Businesses localBusinesses1 = null;
    if (this.g1) {
      localBusinesses1 = (Businesses)localHttpSession.getAttribute(this.g0);
    }
    Businesses localBusinesses2 = new Businesses((Locale)localHttpSession.getAttribute("java.util.Locale"));
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBusiness.setBankId(localSecureUser.getBankID());
    localBusiness.setStatus(this.gZ);
    if ((this.g4 != null) && (this.g4.length() > 0)) {
      localBusiness.setAffiliateBankID(this.g4);
    }
    localBusiness.setPersonalBanker(this.g3);
    try
    {
      HashMap localHashMap = new HashMap(1);
      localHashMap.put("BUSINESSES", localBusinesses2);
      localBusinesses2 = com.ffusion.csil.core.Business.getFilteredBusinesses(localSecureUser, localBusiness, null, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      if ((this.g1) && (localBusinesses1 != null)) {
        localBusinesses2.addAll(localBusinesses1);
      }
      localHttpSession.setAttribute(this.g0, localBusinesses2);
      Businesses localBusinesses3 = (Businesses)localBusinesses2.clone();
      localHttpSession.setAttribute(this.g2, localBusinesses3);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBankEmployeeId(String paramString)
  {
    this.g3 = paramString;
  }
  
  public void setStatus(String paramString)
  {
    this.gZ = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.g4 = paramString;
  }
  
  public void setAppendTo(boolean paramBoolean)
  {
    this.g1 = paramBoolean;
  }
  
  public void setAppendTo(String paramString)
  {
    this.g1 = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getAppendToBoolean()
  {
    return this.g1;
  }
  
  public String getAppendTo()
  {
    return this.g1 == true ? "true" : "false";
  }
  
  public void setListName(String paramString)
  {
    this.g0 = paramString;
  }
  
  public void setTempListName(String paramString)
  {
    this.g2 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusinessesByPersonalBanker
 * JD-Core Version:    0.7.0.1
 */