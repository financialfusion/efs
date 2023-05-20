package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRestrictedCalculations
  extends BaseTask
  implements AffiliateBankTask
{
  private int aPJ = -1;
  private String aPK = null;
  private String aPI = "RestrictedCalculations";
  protected boolean _entBypass = false;
  
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
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ArrayList localArrayList = null;
    AffiliateBank localAffiliateBank = new AffiliateBank();
    if ((this.aPJ == -1) && (this.aPK == null))
    {
      this.error = 25516;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        int i = this.aPJ;
        if (this.aPK != null)
        {
          localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByRoutingNumber(localSecureUser, this.aPK, localHashMap);
          i = localAffiliateBank.getAffiliateBankID();
        }
        localArrayList = AffiliateBankAdmin.getRestrictedCalculations(localSecureUser, i, localHashMap);
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
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.aPI, localArrayList);
      str = this.successURL;
    }
    return str;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.aPJ = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aPJ = -1;
    }
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.aPK = paramString;
  }
  
  public void setRestrictedCalculationsListName(String paramString)
  {
    this.aPI = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetRestrictedCalculations
 * JD-Core Version:    0.7.0.1
 */