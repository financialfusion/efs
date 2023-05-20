package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAffiliateBanks
  extends BaseTask
  implements AffiliateBankTask
{
  protected boolean _entBypass = false;
  private String aPY = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    if (this._entBypass) {
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
    }
    localHashMap.put("LANGUAGE", this.aPY);
    AffiliateBanks localAffiliateBanks = new AffiliateBanks((Locale)localHttpSession.getAttribute("java.util.Locale"));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localAffiliateBanks = AffiliateBankAdmin.getAffiliateBanks(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this._entBypass = false;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("AffiliateBanks", localAffiliateBanks);
      str = this.successURL;
    }
    return str;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.aPY = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.aPY;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetAffiliateBanks
 * JD-Core Version:    0.7.0.1
 */