package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitledLocations
  extends BaseTask
  implements Task
{
  private int MP;
  private String MO;
  private LocationSearchCriteria ML = new LocationSearchCriteria();
  private String MM = "Locations";
  private String MN = "Location";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.MP == 0) {
      return str;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.ML.setDivisionID(this.MP);
    this.ML.setLocationName(this.MO);
    LocationSearchResults localLocationSearchResults1 = null;
    LocationSearchResults localLocationSearchResults2 = new LocationSearchResults();
    try
    {
      localLocationSearchResults1 = CashCon.getLocations(localSecureUser, this.ML, localHashMap);
      Entitlement localEntitlement1 = new Entitlement("Cash Con - Disbursement Request", "Location", null);
      Entitlement localEntitlement2 = new Entitlement("Cash Con - Deposit Entry", "Location", null);
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      Iterator localIterator = localLocationSearchResults1.iterator();
      while (localIterator.hasNext())
      {
        localObject = (LocationSearchResult)localIterator.next();
        localEntitlement2.setObjectId(((LocationSearchResult)localObject).getLocationBPWID());
        localEntitlement1.setObjectId(((LocationSearchResult)localObject).getLocationBPWID());
        if ((Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement2)) || (Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement1))) {
          localLocationSearchResults2.add(localObject);
        }
      }
      localHttpSession.setAttribute(this.MM, localLocationSearchResults2);
      Object localObject = "AllLocations";
      if (localLocationSearchResults2.size() == 1)
      {
        LocationSearchResult localLocationSearchResult = (LocationSearchResult)localLocationSearchResults2.get(0);
        localObject = localLocationSearchResult.getLocationBPWID();
      }
      localHttpSession.setAttribute(this.MN, localObject);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDivEntId(String paramString)
  {
    try
    {
      this.MP = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.MP = 0;
    }
  }
  
  public String getDivEntId()
  {
    return Integer.toString(this.MP);
  }
  
  public void setCollectionName(String paramString)
  {
    this.MM = paramString;
  }
  
  public String getCollectionName()
  {
    return this.MM;
  }
  
  public void setLocationBPWId(String paramString)
  {
    this.MN = paramString;
  }
  
  public String getLocationBPWId()
  {
    return this.MN;
  }
  
  public String getLocationName()
  {
    return this.MO;
  }
  
  public void setLocationName(String paramString)
  {
    DebugLog.log(Level.INFO, "Setting Location Name = " + paramString);
    this.MO = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.GetEntitledLocations
 * JD-Core Version:    0.7.0.1
 */