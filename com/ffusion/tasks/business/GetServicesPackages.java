package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetServicesPackages
  extends BaseTask
  implements BusinessTask
{
  protected String sessionName = "ServicesPackages";
  protected String marketSegmentsSession = "MarketSegments";
  protected String affBank = null;
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServicesPackages localServicesPackages = null;
    try
    {
      HashMap localHashMap = new HashMap(1);
      if (this._entBypass) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      MarketSegments localMarketSegments = (MarketSegments)localHttpSession.getAttribute(this.marketSegmentsSession);
      if ((this.affBank == null) || (this.affBank.length() == 0)) {
        this.affBank = null;
      }
      if ((localMarketSegments == null) || (localMarketSegments.size() == 0)) {
        localServicesPackages = Business.getServicesPackages(localSecureUser, this.affBank, localHashMap);
      } else {
        localServicesPackages = Business.getServicesPackagesForMarketSegments(localSecureUser, localMarketSegments, this.affBank, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 4138;
      str = this.taskErrorURL;
    }
    finally
    {
      this._entBypass = false;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.sessionName, localServicesPackages);
    }
    return str;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public void setMarketSegments(String paramString)
  {
    this.marketSegmentsSession = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.affBank = paramString;
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
 * Qualified Name:     com.ffusion.tasks.business.GetServicesPackages
 * JD-Core Version:    0.7.0.1
 */