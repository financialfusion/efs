package com.ffusion.beans.business;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.util.ILocalizable;
import java.io.Serializable;
import java.util.Locale;

public class MarketSegment
  extends ExtendABean
  implements BusinessDefines, Serializable
{
  public static final String BEAN_NAME = MarketSegment.class.getName();
  public static String MARKETSEGMENTNAME = "Name";
  public static String DESCRIPTION = "Description";
  public static String SERVICESADMINGROUP = "ServicesAdminGroup";
  public static String AFFILIATEBANKID = "AffiliateBankId";
  public static String AFFILIATEBANK = "AffiliateBank";
  public static String ID = "ID";
  protected int id;
  protected String bankId;
  protected String affiliateBankId;
  protected String marketSegmentName;
  protected String description;
  protected String servicesAdminGroupId;
  
  public MarketSegment() {}
  
  public MarketSegment(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public String getMarketSegmentName()
  {
    return this.marketSegmentName;
  }
  
  public void setMarketSegmentName(String paramString)
  {
    this.marketSegmentName = paramString;
  }
  
  public String getId()
  {
    return String.valueOf(this.id);
  }
  
  public int getIdValue()
  {
    return this.id;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setId(String paramString)
  {
    try
    {
      this.id = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.affiliateBankId = paramString;
  }
  
  public String getAffiliateBankId()
  {
    return this.affiliateBankId;
  }
  
  public void setDescription(String paramString)
  {
    if (paramString != null) {
      this.description = paramString;
    } else {
      this.description = "";
    }
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setServicesAdminGroupId(String paramString)
  {
    this.servicesAdminGroupId = paramString;
  }
  
  public String getServicesAdminGroupId()
  {
    return this.servicesAdminGroupId;
  }
  
  public int getServicesAdminGroupIdValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.servicesAdminGroupId);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void set(MarketSegment paramMarketSegment)
  {
    setId(paramMarketSegment.getIdValue());
    setBankId(paramMarketSegment.getBankId());
    setAffiliateBankId(paramMarketSegment.getAffiliateBankId());
    setMarketSegmentName(paramMarketSegment.getMarketSegmentName());
    setDescription(paramMarketSegment.getDescription());
    setServicesAdminGroupId(paramMarketSegment.getServicesAdminGroupId());
  }
  
  public void setEntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    setId(paramEntitlementGroup.getGroupId());
    setBankId(paramEntitlementGroup.getSvcBureauId());
    setMarketSegmentName(paramEntitlementGroup.getGroupName());
    EntitlementGroupProperties localEntitlementGroupProperties = paramEntitlementGroup.getProperties();
    localEntitlementGroupProperties.setCurrentProperty("Description");
    setDescription(localEntitlementGroupProperties.getValueOfCurrentProperty());
    localEntitlementGroupProperties.setCurrentProperty("AffiliateBankId");
    setAffiliateBankId(localEntitlementGroupProperties.getValueOfCurrentProperty());
  }
  
  public EntitlementGroup getEntitlementGroup()
  {
    EntitlementGroup localEntitlementGroup = new EntitlementGroup();
    localEntitlementGroup.setGroupId(getIdValue());
    localEntitlementGroup.setGroupName(getMarketSegmentName());
    localEntitlementGroup.setSvcBureauId(getBankId());
    localEntitlementGroup.setEntGroupType("MarketSegment");
    EntitlementGroupProperties localEntitlementGroupProperties = localEntitlementGroup.getProperties();
    if ((this.description != null) && (this.description.length() != 0))
    {
      localEntitlementGroupProperties.setCurrentProperty("Description");
      localEntitlementGroupProperties.setValueOfCurrentProperty(getDescription());
    }
    if ((this.affiliateBankId != null) && (this.affiliateBankId.length() != 0))
    {
      localEntitlementGroupProperties.setCurrentProperty("AffiliateBankId");
      localEntitlementGroupProperties.setValueOfCurrentProperty(getAffiliateBankId());
    }
    return localEntitlementGroup;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, MarketSegment paramMarketSegment, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, ID, paramMarketSegment.id, this.id, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, MARKETSEGMENTNAME, paramMarketSegment.marketSegmentName, this.marketSegmentName, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, DESCRIPTION, paramMarketSegment.description, this.description, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, SERVICESADMINGROUP, paramMarketSegment.servicesAdminGroupId, this.servicesAdminGroupId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATEBANKID, paramMarketSegment.affiliateBankId, this.affiliateBankId, paramString);
    super.logChanges(paramHistoryTracker, paramMarketSegment, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, ID, this.id, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, ID, this.id, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, MARKETSEGMENTNAME, this.marketSegmentName, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, DESCRIPTION, this.description, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, SERVICESADMINGROUP, this.servicesAdminGroupId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, AFFILIATEBANKID, this.affiliateBankId, paramString);
    super.logDeletion(paramHistoryTracker, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, MarketSegment paramMarketSegment, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, ID, paramMarketSegment.id, this.id, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, MARKETSEGMENTNAME, paramMarketSegment.marketSegmentName, this.marketSegmentName, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, DESCRIPTION, paramMarketSegment.description, this.description, paramILocalizable);
    try
    {
      String str1 = (this.affiliateBankId == null) || (this.affiliateBankId.length() == 0) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(this.affiliateBankId)).getAffiliateBankName();
      String str2 = (paramMarketSegment.affiliateBankId == null) || (paramMarketSegment.affiliateBankId.length() == 0) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(paramMarketSegment.affiliateBankId)).getAffiliateBankName();
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATEBANK, str2, str1, paramILocalizable);
    }
    catch (Exception localException)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATEBANKID, paramMarketSegment.affiliateBankId, this.affiliateBankId, paramILocalizable);
    }
    super.logChanges(paramHistoryTracker, paramMarketSegment, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, ID, this.id, paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, ID, this.id, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, MARKETSEGMENTNAME, this.marketSegmentName, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, DESCRIPTION, this.description, paramILocalizable);
    try
    {
      String str = (this.affiliateBankId == null) || (this.affiliateBankId.length() == 0) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(this.affiliateBankId)).getAffiliateBankName();
      paramHistoryTracker.logDelete(BEAN_NAME, AFFILIATEBANK, str, paramILocalizable);
    }
    catch (Exception localException)
    {
      paramHistoryTracker.logDelete(BEAN_NAME, AFFILIATEBANKID, this.affiliateBankId, paramILocalizable);
    }
    super.logDeletion(paramHistoryTracker, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.MarketSegment
 * JD-Core Version:    0.7.0.1
 */