package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.MarketSegments;
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

public class GetMarketSegments
  extends BaseTask
  implements BusinessTask
{
  protected String userType = "Business";
  protected String sessionName = "MarketSegments";
  protected String affiliateBankID = "";
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MarketSegments localMarketSegments = null;
    try
    {
      HashMap localHashMap = new HashMap(1);
      if (this._entBypass) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if ((this.affiliateBankID == null) || (this.affiliateBankID.length() == 0)) {
        this.affiliateBankID = null;
      }
      if ((this.userType == null) || (this.userType.length() == 0)) {
        localMarketSegments = Business.getMarketSegments(localSecureUser, this.affiliateBankID, localHashMap);
      } else {
        localMarketSegments = Business.getMarketSegmentsByUserType(localSecureUser, this.userType, this.affiliateBankID, localHashMap);
      }
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
    if (this.error == 0) {
      localHttpSession.setAttribute(this.sessionName, localMarketSegments);
    }
    return str;
  }
  
  public void setUserType(String paramString)
  {
    this.userType = paramString;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this.affiliateBankID = paramString;
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
 * Qualified Name:     com.ffusion.tasks.business.GetMarketSegments
 * JD-Core Version:    0.7.0.1
 */