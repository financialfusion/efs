package com.ffusion.beans.autoentitle;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class AutoEntitle
  extends ExtendABean
{
  public static final String BEAN_NAME = "com.ffusion.beans.autoentitle.AutoEntitle";
  public static final String AUTO_ENTITLE = "AUTO_ENTITLE";
  public static final String ENABLE_ACCOUNTS = "ENABLE_ACCOUNTS";
  public static final String ENABLE_ACHCOMPANIES = "ENABLE_ACHCOMPANIES";
  public static final String ENABLE_ACCOUNT_GROUPS = "ENABLE_ACCOUNT_GROUPS";
  public static final String ENABLE_LOCATIONS = "ENABLE_LOCATIONS";
  public static final String ENABLE_PERMISSIONS = "ENABLE_PERMISSIONS";
  public static final String ENABLE_WIRE_TEMPLATES = "ENABLE_WIRE_TEMPLATES";
  public static final String ENTITLEMENT_GROUP = "ENTITLEMENT_GROUP";
  public static final String ENTITLEMENT_GROUP_MEMBER = "ENTITLEMENT_GROUP_MEMBER";
  public static final String AFFILIATE_BANK = "AFFILIATE_BANK";
  private AffiliateBank aXx = null;
  private EntitlementGroup aXz = null;
  private EntitlementGroupMember aXv = null;
  private int aXB = -1;
  private String aXC;
  private boolean aXt = true;
  private boolean aXu = true;
  private boolean aXw = true;
  private boolean aXy = true;
  private boolean aXs = true;
  private boolean aXA = true;
  
  public boolean getEnableAccounts()
  {
    return this.aXt;
  }
  
  public void setEnableAccounts(boolean paramBoolean)
  {
    this.aXt = paramBoolean;
  }
  
  public boolean getEnableACHCompanies()
  {
    return this.aXu;
  }
  
  public void setEnableACHCompanies(boolean paramBoolean)
  {
    this.aXu = paramBoolean;
  }
  
  public boolean getEnablePermissions()
  {
    return this.aXy;
  }
  
  public void setEnablePermissions(boolean paramBoolean)
  {
    this.aXy = paramBoolean;
  }
  
  public boolean getEnableLocations()
  {
    return this.aXw;
  }
  
  public void setEnableLocations(boolean paramBoolean)
  {
    this.aXw = paramBoolean;
  }
  
  public boolean getEnableWireTemplates()
  {
    return this.aXs;
  }
  
  public void setEnableWireTemplates(boolean paramBoolean)
  {
    this.aXs = paramBoolean;
  }
  
  public boolean getEnableAccountGroups()
  {
    return this.aXA;
  }
  
  public void setEnableAccountGroups(boolean paramBoolean)
  {
    this.aXA = paramBoolean;
  }
  
  public void setAffiliateBank(AffiliateBank paramAffiliateBank)
  {
    this.aXx = paramAffiliateBank;
    this.aXz = null;
    this.aXv = null;
    this.aXB = 1;
    this.aXC = String.valueOf(paramAffiliateBank.getAffiliateBankID());
  }
  
  public AffiliateBank getAffiliateBank()
  {
    return this.aXx;
  }
  
  public EntitlementGroup getEntitlementGroup()
  {
    return this.aXz;
  }
  
  public EntitlementGroupMember getEntitlementGroupMember()
  {
    return this.aXv;
  }
  
  public void setEntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    this.aXz = paramEntitlementGroup;
    this.aXx = null;
    this.aXv = null;
    if (paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")) {
      this.aXB = 2;
    } else if (paramEntitlementGroup.getEntGroupType().equals("Business")) {
      this.aXB = 4;
    } else if (paramEntitlementGroup.getEntGroupType().equals("Division")) {
      this.aXB = 5;
    } else if (paramEntitlementGroup.getEntGroupType().equals("Group")) {
      this.aXB = 6;
    }
    this.aXC = String.valueOf(paramEntitlementGroup.getGroupId());
  }
  
  public void setEntitlementGroupMember(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.aXv = paramEntitlementGroupMember;
    this.aXx = null;
    this.aXz = null;
    this.aXB = 7;
    this.aXC = paramEntitlementGroupMember.getId();
  }
  
  public int getObjectType()
  {
    return this.aXB;
  }
  
  public String getObjectID()
  {
    return this.aXC;
  }
  
  public void set(AutoEntitle paramAutoEntitle)
  {
    super.set(paramAutoEntitle);
    setEnableAccounts(paramAutoEntitle.getEnableAccounts());
    setEnableACHCompanies(paramAutoEntitle.getEnableACHCompanies());
    setEnableLocations(paramAutoEntitle.getEnableLocations());
    setEnablePermissions(paramAutoEntitle.getEnablePermissions());
    setEnableWireTemplates(paramAutoEntitle.getEnableWireTemplates());
    setEnableAccountGroups(paramAutoEntitle.getEnableAccountGroups());
    AffiliateBank localAffiliateBank = paramAutoEntitle.getAffiliateBank();
    if (localAffiliateBank != null) {
      setAffiliateBank((AffiliateBank)localAffiliateBank.clone());
    }
    EntitlementGroup localEntitlementGroup = paramAutoEntitle.getEntitlementGroup();
    if (localEntitlementGroup != null) {
      setEntitlementGroup((EntitlementGroup)localEntitlementGroup.clone());
    }
    EntitlementGroupMember localEntitlementGroupMember = paramAutoEntitle.getEntitlementGroupMember();
    if (localEntitlementGroupMember != null) {
      setEntitlementGroupMember((EntitlementGroupMember)localEntitlementGroupMember.clone());
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    bool = super.set(paramString1, paramString2);
    return bool;
  }
  
  public Object clone()
  {
    try
    {
      AutoEntitle localAutoEntitle = (AutoEntitle)super.clone();
      if (this.aXx != null) {
        localAutoEntitle.setAffiliateBank((AffiliateBank)this.aXx.clone());
      }
      if (this.aXz != null) {
        localAutoEntitle.setEntitlementGroup((EntitlementGroup)this.aXz.clone());
      }
      if (this.aXv != null) {
        localAutoEntitle.setEntitlementGroupMember((EntitlementGroupMember)this.aXv.clone());
      }
      return localAutoEntitle;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    AutoEntitle localAutoEntitle = (AutoEntitle)paramObject;
    int i = 0;
    if (this == localAutoEntitle) {
      i = 0;
    } else if (this.aXx != null) {
      i = this.aXx.compareTo(localAutoEntitle.getAffiliateBank());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    AutoEntitle localAutoEntitle = (AutoEntitle)paramObject;
    int i = 1;
    EntitlementGroup localEntitlementGroup = localAutoEntitle.getEntitlementGroup();
    i = super.compare(paramObject, paramString);
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("ENTITLEMENT_GROUP_MEMBER")) && (this.aXz != null)) {
      return isFilterable(String.valueOf(this.aXz.getGroupId()), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "AUTO_ENTITLE");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "AUTO_ENTITLE");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, AutoEntitle paramAutoEntitle, String paramString)
  {
    logChanges(paramHistoryTracker, "com.ffusion.beans.autoentitle.AutoEntitle", paramAutoEntitle, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, AutoEntitle paramAutoEntitle, String paramString2)
  {
    if (!"com.ffusion.beans.autoentitle.AutoEntitle".equals(paramString1)) {
      paramString1 = paramString1 + "," + "com.ffusion.beans.autoentitle.AutoEntitle";
    }
    paramHistoryTracker.detectChange(paramString1, "ENABLE_ACCOUNTS", paramAutoEntitle.getEnableAccounts(), this.aXt, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENABLE_ACCOUNT_GROUPS", paramAutoEntitle.getEnableAccountGroups(), this.aXA, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENABLE_ACHCOMPANIES", paramAutoEntitle.getEnableACHCompanies(), this.aXu, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENABLE_LOCATIONS", paramAutoEntitle.getEnableLocations(), this.aXw, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENABLE_PERMISSIONS", paramAutoEntitle.getEnablePermissions(), this.aXy, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENABLE_WIRE_TEMPLATES", paramAutoEntitle.getEnableWireTemplates(), this.aXs, paramString2);
    paramHistoryTracker.detectChange(paramString1, "AFFILIATE_BANK", paramAutoEntitle.getAffiliateBank(), this.aXx, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENTITLEMENT_GROUP", paramAutoEntitle.getEntitlementGroup(), this.aXz, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ENTITLEMENT_GROUP_MEMBER", paramAutoEntitle.getEntitlementGroupMember(), this.aXv, paramString2);
    super.logChanges(paramHistoryTracker, paramAutoEntitle, paramString2);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, AutoEntitle paramAutoEntitle, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, "com.ffusion.beans.autoentitle.AutoEntitle", paramAutoEntitle, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, AutoEntitle paramAutoEntitle, ILocalizable paramILocalizable)
  {
    if (!"com.ffusion.beans.autoentitle.AutoEntitle".equals(paramString)) {
      paramString = paramString + "," + "com.ffusion.beans.autoentitle.AutoEntitle";
    }
    paramHistoryTracker.detectChange(paramString, "ENABLE_ACCOUNTS", paramAutoEntitle.getEnableAccounts(), this.aXt, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ENABLE_ACCOUNT_GROUPS", paramAutoEntitle.getEnableAccountGroups(), this.aXA, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ENABLE_ACHCOMPANIES", paramAutoEntitle.getEnableACHCompanies(), this.aXu, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ENABLE_LOCATIONS", paramAutoEntitle.getEnableLocations(), this.aXw, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ENABLE_PERMISSIONS", paramAutoEntitle.getEnablePermissions(), this.aXy, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ENABLE_WIRE_TEMPLATES", paramAutoEntitle.getEnableWireTemplates(), this.aXs, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "AFFILIATE_BANK", paramAutoEntitle.getAffiliateBank() == null ? null : paramAutoEntitle.getAffiliateBank().getAffiliateBankName(), this.aXx == null ? null : this.aXx.getAffiliateBankName(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ENTITLEMENT_GROUP", paramAutoEntitle.getEntitlementGroup() == null ? null : paramAutoEntitle.getEntitlementGroup().getGroupName(), this.aXz == null ? null : this.aXz.getGroupName(), paramILocalizable);
    try
    {
      User localUser1 = this.aXv == null ? null : CustomerAdapter.getUserById(Integer.parseInt(this.aXv.getId()));
      User localUser2 = paramAutoEntitle.getEntitlementGroupMember() == null ? null : CustomerAdapter.getUserById(Integer.parseInt(paramAutoEntitle.getEntitlementGroupMember().getId()));
      paramHistoryTracker.detectChange(paramString, "ENTITLEMENT_GROUP_MEMBER", localUser2 == null ? null : localUser2.getUserName(), localUser1 == null ? null : localUser1.getUserName(), paramILocalizable);
    }
    catch (Exception localException)
    {
      paramHistoryTracker.detectChange(paramString, "ENTITLEMENT_GROUP_MEMBER", paramAutoEntitle.getEntitlementGroupMember() == null ? null : paramAutoEntitle.getEntitlementGroupMember().getId(), this.aXv == null ? null : this.aXv.getId(), paramILocalizable);
    }
    super.logChanges(paramHistoryTracker, paramAutoEntitle, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.autoentitle.AutoEntitle
 * JD-Core Version:    0.7.0.1
 */