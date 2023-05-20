package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddACHCompany
  extends ModifyACHCompany
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      if (init(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = addACHCompany(localHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.initFlag = false;
    return true;
  }
  
  protected String addACHCompany(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.achCompaniesName);
    if (localACHCompanies == null)
    {
      this.error = 16505;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, Integer.parseInt(this.affiliateBankID), localHashMap);
        ACHCompany localACHCompany = ACH.addACHCompany(localSecureUser, this, localAffiliateBank.getFIBPWID(), this._autoEntitleACHCompany, localHashMap);
        processEntitlements(localSecureUser);
        set(localACHCompany);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        paramHttpSession.setAttribute(this.achCompanyName, this);
        localACHCompanies.add(this);
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddACHCompany
 * JD-Core Version:    0.7.0.1
 */