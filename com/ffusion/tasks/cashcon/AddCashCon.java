package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCashCon
  extends ModifyCashCon
{
  protected String nextURL = null;
  
  public AddCashCon()
  {
    this.datetype = "SHORT";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(localHttpSession);
    } else {
      str = addCashCon(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    loadEntitledLocations(localSecureUser);
    removeInactiveCompaniesFromLocations(paramHttpSession);
    Object localObject;
    if ((this.divisionList != null) && (this.divisionList.size() > 0))
    {
      localObject = (EntitlementGroup)this.divisionList.get(0);
      setDivisionID("" + ((EntitlementGroup)localObject).getGroupId());
      setDivisionName(((EntitlementGroup)localObject).getGroupName());
    }
    if ((this.locationList != null) && (this.locationList.size() > 0))
    {
      localObject = (LocationSearchResult)this.locationList.get(0);
      setLocationID("" + ((LocationSearchResult)localObject).getLocationBPWID());
      setLocationName(((LocationSearchResult)localObject).getLocationName());
      setCompanyID(((LocationSearchResult)localObject).getCompID());
    }
    setAmount("0.00");
    return this.successURL;
  }
  
  protected String addCashCon(HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = doProcess(paramHttpSession);
        this.nextURL = str;
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
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = null;
    com.ffusion.beans.cashcon.CashCon localCashCon = new com.ffusion.beans.cashcon.CashCon(this.locale);
    localCashCon.set(this);
    HashMap localHashMap = new HashMap();
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      localCashCon = com.ffusion.csil.core.CashCon.addCashCon(localSecureUser, localCashCon, localHashMap);
      set(localCashCon);
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      CashCons localCashCons = (CashCons)paramHttpSession.getAttribute(this.cashconsName);
      if (localCashCons != null) {
        localCashCons.add(localCashCon);
      }
      paramHttpSession.setAttribute("CashCon", localCashCon);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.AddCashCon
 * JD-Core Version:    0.7.0.1
 */