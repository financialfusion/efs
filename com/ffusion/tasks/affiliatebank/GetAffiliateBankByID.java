package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAffiliateBankByID
  extends BaseTask
  implements AffiliateBankTask
{
  protected int affiliateBankID = -1;
  private String aPX = "AffiliateBank";
  protected boolean _entBypass = false;
  private String aPW = "en_US";
  
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
    localHashMap.put("LANGUAGE", this.aPW);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    AffiliateBank localAffiliateBank = new AffiliateBank();
    try
    {
      localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, this.affiliateBankID, localHashMap);
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
      localHttpSession.setAttribute(this.aPX, localAffiliateBank);
      str = this.successURL;
    }
    return str;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.affiliateBankID = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.affiliateBankID = -1;
    }
  }
  
  public void setAffiliateBankSessionName(String paramString)
  {
    this.aPX = paramString;
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
    this.aPW = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.aPW;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetAffiliateBankByID
 * JD-Core Version:    0.7.0.1
 */