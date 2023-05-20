package com.ffusion.tasks.cashcon;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyCashCon
  extends com.ffusion.beans.cashcon.CashCon
  implements Task
{
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String locationDisplayCount = null;
  protected String cashconID = null;
  protected String cashconName = "CashCon";
  protected String cashconsName = "CashCons";
  protected String minAmount = "1.00";
  protected String maxAmount = "99999999.99";
  protected String _cashconcompaniesName = "CashConCompanies";
  protected com.ffusion.beans.cashcon.CashCon originalCashCon = null;
  protected EntitlementGroups divisionList = null;
  protected LocationSearchResults locationList = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag) {
      str = initProcess(localHttpSession);
    } else {
      str = doProcess(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.initFlag = false;
    this.error = 0;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    com.ffusion.beans.cashcon.CashCon localCashCon = null;
    if ((this.cashconID != null) && (this.cashconID.length() > 0))
    {
      localObject = (CashCons)paramHttpSession.getAttribute(this.cashconsName);
      if (localObject != null) {
        localCashCon = ((CashCons)localObject).getByID(this.cashconID);
      }
    }
    if (localCashCon == null) {
      localCashCon = (com.ffusion.beans.cashcon.CashCon)paramHttpSession.getAttribute(this.cashconName);
    }
    if (localCashCon == null)
    {
      this.error = 24000;
      str = this.taskErrorURL;
    }
    else
    {
      set(localCashCon);
      this.originalCashCon = localCashCon;
    }
    Object localObject = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    loadEntitledLocations((SecureUser)localObject);
    removeInactiveCompaniesFromLocations(paramHttpSession);
    return str;
  }
  
  protected void removeInactiveCompaniesFromLocations(HttpSession paramHttpSession)
  {
    CashConCompanies localCashConCompanies = (CashConCompanies)paramHttpSession.getAttribute(this._cashconcompaniesName);
    ArrayList localArrayList = new ArrayList();
    if ((localCashConCompanies != null) && (this.locationList != null) && (this.locationList.size() > 0))
    {
      Iterator localIterator = this.locationList.iterator();
      while (localIterator.hasNext())
      {
        LocationSearchResult localLocationSearchResult = (LocationSearchResult)localIterator.next();
        if ((this.originalCashCon == null) || (!this.originalCashCon.getLocationID().equals(String.valueOf(localLocationSearchResult.getLocationBPWID()))))
        {
          CashConCompany localCashConCompany = localCashConCompanies.getByID(localLocationSearchResult.getCompID());
          if ((localCashConCompany != null) && (!localCashConCompany.getActive())) {
            localArrayList.add(localLocationSearchResult);
          }
        }
      }
      if (localArrayList.size() > 0) {
        this.locationList.removeAll(localArrayList);
      }
    }
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyCashCon(paramHttpSession);
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
  
  protected String modifyCashCon(HttpSession paramHttpSession)
  {
    String str = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      com.ffusion.beans.cashcon.CashCon localCashCon1 = null;
      localCashCon1 = com.ffusion.csil.core.CashCon.modifyCashCon(localSecureUser, this, this.originalCashCon, localHashMap);
      set(localCashCon1);
      if ((this.cashconsName != null) && (this.cashconsName.length() > 0))
      {
        CashCons localCashCons = (CashCons)paramHttpSession.getAttribute(this.cashconsName);
        com.ffusion.beans.cashcon.CashCon localCashCon2 = localCashCons.getByID(localCashCon1.getID());
        if (localCashCon2 != null) {
          localCashCon2.set(localCashCon1);
        } else {
          localCashCons.add(localCashCon1);
        }
      }
      str = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if (this.validate.indexOf("AMOUNT") != -1) {
        bool = validateAmount();
      }
      this.validate = null;
    }
    if ((bool) && ((this.divisionID == null) || (this.divisionID.length() == 0)))
    {
      bool = false;
      this.error = 24010;
    }
    if ((bool) && ((this.locationID == null) || (this.locationID.length() == 0)))
    {
      this.error = 24005;
      bool = false;
    }
    return bool;
  }
  
  protected boolean validateAmount()
  {
    Currency localCurrency1 = new Currency(this.minAmount, this.locale);
    Currency localCurrency2 = new Currency(this.maxAmount, this.locale);
    if (this.amount == null) {
      this.error = 24007;
    } else if (this.amount.compareTo(localCurrency1) == -1) {
      this.error = 24008;
    } else if (this.amount.compareTo(localCurrency2) == 1) {
      this.error = 24009;
    } else {
      this.error = 0;
    }
    return this.error == 0;
  }
  
  public final void setMinimumAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public final void setMaximumAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public final void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setValidate(String paramString)
  {
    if (!"".equals(paramString)) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.error);
  }
  
  public final void setCashConID(String paramString)
  {
    this.cashconID = paramString;
  }
  
  public final void setCashConName(String paramString)
  {
    this.cashconName = paramString;
  }
  
  public final String getCashConName()
  {
    return this.cashconName;
  }
  
  public final void setCollectionName(String paramString)
  {
    this.cashconsName = paramString;
  }
  
  public void loadEntitledLocations(SecureUser paramSecureUser)
  {
    this.locationList = new LocationSearchResults();
    int i = -1;
    this.divisionList = new EntitlementGroups();
    try
    {
      for (EntitlementGroup localEntitlementGroup1 = Entitlements.getEntitlementGroup(paramSecureUser.getEntitlementID()); !"Business".equals(localEntitlementGroup1.getEntGroupType()); localEntitlementGroup1 = Entitlements.getEntitlementGroup(localEntitlementGroup1.getParentId())) {
        if ("Division".equals(localEntitlementGroup1.getEntGroupType()))
        {
          i = localEntitlementGroup1.getGroupId();
          this.divisionList.add(localEntitlementGroup1);
          break;
        }
      }
      if ((localEntitlementGroup1 != null) && (i == -1)) {
        jdMethod_for(localEntitlementGroup1, this.divisionList);
      }
    }
    catch (CSILException localCSILException1)
    {
      localCSILException1.printStackTrace(System.out);
    }
    Iterator localIterator1 = this.divisionList.iterator();
    LocationSearchCriteria localLocationSearchCriteria = new LocationSearchCriteria();
    while (localIterator1.hasNext())
    {
      EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator1.next();
      localEntitlementGroup2.put("DivisionName", localEntitlementGroup2.getGroupName());
      LocationSearchResults localLocationSearchResults = null;
      localLocationSearchCriteria.setDivisionID(localEntitlementGroup2.getGroupId());
      try
      {
        localLocationSearchResults = com.ffusion.csil.core.CashCon.getLocations(paramSecureUser, localLocationSearchCriteria, new HashMap());
        if (localLocationSearchResults != null)
        {
          Iterator localIterator2 = localLocationSearchResults.iterator();
          while (localIterator2.hasNext())
          {
            LocationSearchResult localLocationSearchResult = (LocationSearchResult)localIterator2.next();
            String str1 = "";
            if (getType() == 16) {
              str1 = "Cash Con - Disbursement Request";
            }
            if (getType() == 15) {
              str1 = "Cash Con - Deposit Entry";
            }
            Entitlement localEntitlement1 = new Entitlement(str1, "Location", localLocationSearchResult.getLocationBPWID());
            Entitlement localEntitlement2 = new Entitlement();
            localEntitlement2.setObjectType("CashConCompany");
            localEntitlement2.setObjectId(localLocationSearchResult.getCompID());
            localEntitlement2.setOperationName(str1);
            if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement1)) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement2)))
            {
              localLocationSearchResult.put("DivisionName", localEntitlementGroup2.getGroupName());
              Location localLocation = com.ffusion.csil.core.CashCon.getLocation(paramSecureUser, localLocationSearchResult.getLocationBPWID(), new HashMap());
              if (localLocation != null)
              {
                String str2 = localLocation.getActive();
                if ((str2 != null) && ("TRUE".equalsIgnoreCase(str2))) {
                  this.locationList.add(localLocationSearchResult);
                }
              }
            }
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        localCSILException2.printStackTrace(System.out);
      }
    }
  }
  
  private void jdMethod_for(EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups)
    throws CSILException
  {
    if (paramEntitlementGroup.getEntGroupType().equals("Division")) {
      paramEntitlementGroups.add(paramEntitlementGroup);
    }
    Iterator localIterator = null;
    EntitlementGroups localEntitlementGroups = null;
    EntitlementGroup localEntitlementGroup = null;
    localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
    if (localEntitlementGroups != null)
    {
      localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        localEntitlementGroup = (EntitlementGroup)localIterator.next();
        jdMethod_for(localEntitlementGroup, paramEntitlementGroups);
      }
    }
  }
  
  public EntitlementGroups getDivisionList()
  {
    return this.divisionList;
  }
  
  public String getLocationSearchCount()
  {
    if (this.locationDisplayCount == null) {
      this.locationDisplayCount = com.ffusion.csil.handlers.CashCon.getDisplayCount();
    }
    return this.locationDisplayCount;
  }
  
  public void setLocationSearch(String paramString)
  {
    if (this.locationList != null)
    {
      StringBuffer localStringBuffer = new StringBuffer(this.locationList.getFilter());
      localStringBuffer.append(",and,").append("LOCATION_NAME").append(">>").append(paramString).append(",").append("LOCATION_NAME").append("<<").append(paramString).append("zzz");
      this.locationList.setFilter(localStringBuffer.toString());
    }
  }
  
  public LocationSearchResults getLocationList()
  {
    return this.locationList;
  }
  
  public void setDivisionID(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    if (i != -1) {
      try
      {
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(i);
        super.setDivisionID("" + localEntitlementGroup.getGroupId());
        setDivisionName(localEntitlementGroup.getGroupName());
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
      }
    }
  }
  
  public final void setCashConCompaniesSessionName(String paramString)
  {
    this._cashconcompaniesName = paramString;
  }
  
  public final String getCashConCompaniesSessionName()
  {
    return this._cashconcompaniesName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.ModifyCashCon
 * JD-Core Version:    0.7.0.1
 */