package com.ffusion.beans.business;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.util.ILocalizable;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class ServicesPackage
  extends ExtendABean
  implements BusinessDefines, Serializable
{
  public static final String BEAN_NAME = ServicesPackage.class.getName();
  public static String TRANSACTIONLIMITS = "TRANSACTIONLIMITS";
  public static String SERVICESPACKAGENAME = "Name";
  public static String MARKETSEGMENT = "MarketSegment";
  public static String AFFILIATEBANKID = "AffiliateBankId";
  public static String AFFILIATEBANK = "AffiliateBank";
  public static String DESCRIPTION = "Description";
  public static String SERVICESADMINGROUP = "ServicesAdminGroup";
  public static String SERVICESFEATURES = "ServiceFeatures";
  public static String ID = "ID";
  protected int id;
  protected String servicesPackageName;
  protected String marketSegmentId;
  protected String affiliateBankId;
  protected String bankId;
  protected String description;
  protected String servicesAdminGroupId;
  protected ServiceFeatures serviceFeatures;
  protected ServiceFeatures chargeableFeatures;
  protected TransactionLimits transactionLimits;
  protected boolean auth_login_token = false;
  protected boolean auth_login_scratch = false;
  protected boolean auth_login_challenge = false;
  protected boolean auth_trans_token = false;
  protected boolean auth_trans_scratch = false;
  protected boolean auth_trans_challenge = false;
  protected boolean auth_priv_token = false;
  protected boolean auth_priv_scratch = false;
  protected boolean auth_priv_challenge = false;
  
  public ServicesPackage() {}
  
  public ServicesPackage(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public String getServicesPackageName()
  {
    return this.servicesPackageName;
  }
  
  public void setServicesPackageName(String paramString)
  {
    this.servicesPackageName = paramString;
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
  
  public void setMarketSegmentId(String paramString)
  {
    this.marketSegmentId = paramString;
  }
  
  public void setMarketSegmentId(int paramInt)
  {
    this.marketSegmentId = Integer.toString(paramInt);
  }
  
  public String getMarketSegmentId()
  {
    return this.marketSegmentId;
  }
  
  public int getMarketSegmentIdValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.marketSegmentId);
    }
    catch (Exception localException) {}
    return i;
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
  
  public void setServicesAdminGroupId(int paramInt)
  {
    this.servicesAdminGroupId = Integer.toString(paramInt);
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
  
  public void setServiceFeatures(ServiceFeatures paramServiceFeatures)
  {
    this.serviceFeatures = paramServiceFeatures;
  }
  
  public ServiceFeatures getServiceFeatures()
  {
    return this.serviceFeatures;
  }
  
  public void setChargeableFeatures(ServiceFeatures paramServiceFeatures)
  {
    this.chargeableFeatures = paramServiceFeatures;
  }
  
  public ServiceFeatures getChargeableFeatures()
  {
    return this.chargeableFeatures;
  }
  
  public void setTransactionLimits(TransactionLimits paramTransactionLimits)
  {
    this.transactionLimits = paramTransactionLimits;
  }
  
  public TransactionLimits getTransactionLimits()
  {
    return this.transactionLimits;
  }
  
  public void set(ServicesPackage paramServicesPackage)
  {
    setId(paramServicesPackage.getIdValue());
    setMarketSegmentId(paramServicesPackage.getMarketSegmentId());
    setAffiliateBankId(paramServicesPackage.getAffiliateBankId());
    setServicesPackageName(paramServicesPackage.getServicesPackageName());
    setBankId(paramServicesPackage.getBankId());
    setDescription(paramServicesPackage.getDescription());
    setServicesAdminGroupId(paramServicesPackage.getServicesAdminGroupId());
    setServiceFeatures(paramServicesPackage.getServiceFeatures());
    setChargeableFeatures(paramServicesPackage.getChargeableFeatures());
    setTransactionLimits(paramServicesPackage.getTransactionLimits());
    setEntitlementGroup(paramServicesPackage.getEntitlementGroup());
  }
  
  public void setEntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    setId(paramEntitlementGroup.getGroupId());
    setBankId(paramEntitlementGroup.getSvcBureauId());
    setMarketSegmentId(Integer.toString(paramEntitlementGroup.getParentId()));
    setServicesPackageName(paramEntitlementGroup.getGroupName());
    EntitlementGroupProperties localEntitlementGroupProperties = paramEntitlementGroup.getProperties();
    Enumeration localEnumeration = localEntitlementGroupProperties.getPropertyNames();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      localEntitlementGroupProperties.setCurrentProperty(str);
      if (str.equals("AuthLoginToken")) {
        this.auth_login_token = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthLoginScratch")) {
        this.auth_login_scratch = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthLoginChallenge")) {
        this.auth_login_challenge = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthTransToken")) {
        this.auth_trans_token = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthTransScratch")) {
        this.auth_trans_scratch = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthTransChallenge")) {
        this.auth_trans_challenge = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthPrivToken")) {
        this.auth_priv_token = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthPrivScratch")) {
        this.auth_priv_scratch = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("AuthPrivChallenge")) {
        this.auth_priv_challenge = Boolean.valueOf(localEntitlementGroupProperties.getValueOfCurrentProperty()).booleanValue();
      } else if (str.equals("Description")) {
        setDescription(localEntitlementGroupProperties.getValueOfCurrentProperty());
      } else if (str.equals("AffiliateBankId")) {
        setAffiliateBankId(localEntitlementGroupProperties.getValueOfCurrentProperty());
      } else {
        set(str, localEntitlementGroupProperties.getValueOfCurrentProperty());
      }
    }
  }
  
  public EntitlementGroup getEntitlementGroup()
  {
    EntitlementGroup localEntitlementGroup = new EntitlementGroup();
    localEntitlementGroup.setGroupId(getIdValue());
    localEntitlementGroup.setGroupName(getServicesPackageName());
    localEntitlementGroup.setSvcBureauId(getBankId());
    localEntitlementGroup.setEntGroupType("ServicesPackage");
    localEntitlementGroup.setParentId(getMarketSegmentIdValue());
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
    localEntitlementGroupProperties.setCurrentProperty("AuthLoginToken");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_login_token));
    localEntitlementGroupProperties.setCurrentProperty("AuthLoginScratch");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_login_scratch));
    localEntitlementGroupProperties.setCurrentProperty("AuthLoginChallenge");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_login_challenge));
    localEntitlementGroupProperties.setCurrentProperty("AuthTransToken");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_trans_token));
    localEntitlementGroupProperties.setCurrentProperty("AuthTransScratch");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_trans_scratch));
    localEntitlementGroupProperties.setCurrentProperty("AuthTransChallenge");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_trans_challenge));
    localEntitlementGroupProperties.setCurrentProperty("AuthPrivToken");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_priv_token));
    localEntitlementGroupProperties.setCurrentProperty("AuthPrivScratch");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_priv_scratch));
    localEntitlementGroupProperties.setCurrentProperty("AuthPrivChallenge");
    localEntitlementGroupProperties.setValueOfCurrentProperty(String.valueOf(this.auth_priv_challenge));
    Iterator localIterator = keySet().iterator();
    Object localObject1;
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = localIterator.next();
      if ((localObject1 instanceof String))
      {
        localObject2 = get(localObject1);
        if ((localObject2 instanceof String))
        {
          localEntitlementGroupProperties.setCurrentProperty((String)localObject1);
          localEntitlementGroupProperties.setValueOfCurrentProperty((String)localObject2);
        }
      }
    }
    if (this.chargeableFeatures != null)
    {
      localObject1 = this.chargeableFeatures.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ServiceFeature)((Iterator)localObject1).next();
        String str = ((ServiceFeature)localObject2).getServiceCharge();
        if ((str != null) && (str.length() != 0))
        {
          localEntitlementGroupProperties.setCurrentProperty(((ServiceFeature)localObject2).getFeatureName());
          localEntitlementGroupProperties.setValueOfCurrentProperty(str);
        }
      }
    }
    return localEntitlementGroup;
  }
  
  public com.ffusion.csil.beans.entitlements.Entitlements getRestrictedEntitlements(EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    if (this.serviceFeatures != null)
    {
      Iterator localIterator = this.serviceFeatures.iterator();
      while (localIterator.hasNext())
      {
        ServiceFeature localServiceFeature = (ServiceFeature)localIterator.next();
        if (!localServiceFeature.isSelected())
        {
          Entitlement localEntitlement1 = new Entitlement();
          localEntitlement1.setOperationName(localServiceFeature.getFeatureName());
          localEntitlements.add(localEntitlement1);
          EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(localServiceFeature.getFeatureName());
          if ((localEntitlementTypePropertyList != null) && (localEntitlementTypePropertyList.isPropertySet("admin partner")))
          {
            Entitlement localEntitlement2 = new Entitlement();
            localEntitlement2.setOperationName(localEntitlementTypePropertyList.getPropertyValue("admin partner", 0));
            localEntitlements.add(localEntitlement2);
          }
        }
      }
    }
    return localEntitlements;
  }
  
  public boolean getAuthLoginToken()
  {
    return this.auth_login_token;
  }
  
  public boolean getAuthLoginScratch()
  {
    return this.auth_login_scratch;
  }
  
  public boolean getAuthLoginChallenge()
  {
    return this.auth_login_challenge;
  }
  
  public boolean getAuthTransToken()
  {
    return this.auth_trans_token;
  }
  
  public boolean getAuthTransScratch()
  {
    return this.auth_trans_scratch;
  }
  
  public boolean getAuthTransChallenge()
  {
    return this.auth_trans_challenge;
  }
  
  public boolean getAuthPrivToken()
  {
    return this.auth_priv_token;
  }
  
  public boolean getAuthPrivScratch()
  {
    return this.auth_priv_scratch;
  }
  
  public boolean getAuthPrivChallenge()
  {
    return this.auth_priv_challenge;
  }
  
  public void setAuthLoginToken(String paramString)
  {
    this.auth_login_token = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthLoginScratch(String paramString)
  {
    this.auth_login_scratch = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthLoginChallenge(String paramString)
  {
    this.auth_login_challenge = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthTransToken(String paramString)
  {
    this.auth_trans_token = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthTransScratch(String paramString)
  {
    this.auth_trans_scratch = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthTransChallenge(String paramString)
  {
    this.auth_trans_challenge = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthPrivToken(String paramString)
  {
    this.auth_priv_token = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthPrivScratch(String paramString)
  {
    this.auth_priv_scratch = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthPrivChallenge(String paramString)
  {
    this.auth_priv_challenge = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAuthLoginToken(boolean paramBoolean)
  {
    this.auth_login_token = paramBoolean;
  }
  
  public void setAuthLoginScratch(boolean paramBoolean)
  {
    this.auth_login_scratch = paramBoolean;
  }
  
  public void setAuthLoginChallenge(boolean paramBoolean)
  {
    this.auth_login_challenge = paramBoolean;
  }
  
  public void setAuthTransToken(boolean paramBoolean)
  {
    this.auth_trans_token = paramBoolean;
  }
  
  public void setAuthTransScratch(boolean paramBoolean)
  {
    this.auth_trans_scratch = paramBoolean;
  }
  
  public void setAuthTransChallenge(boolean paramBoolean)
  {
    this.auth_trans_challenge = paramBoolean;
  }
  
  public void setAuthPrivToken(boolean paramBoolean)
  {
    this.auth_priv_token = paramBoolean;
  }
  
  public void setAuthPrivScratch(boolean paramBoolean)
  {
    this.auth_priv_scratch = paramBoolean;
  }
  
  public void setAuthPrivChallenge(boolean paramBoolean)
  {
    this.auth_priv_challenge = paramBoolean;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ServicesPackage paramServicesPackage, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, ID, paramServicesPackage.id, this.id, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGENAME, paramServicesPackage.servicesPackageName, this.servicesPackageName, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, MARKETSEGMENT, paramServicesPackage.marketSegmentId, this.marketSegmentId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATEBANKID, paramServicesPackage.affiliateBankId, this.affiliateBankId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, DESCRIPTION, paramServicesPackage.description, this.description, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, SERVICESADMINGROUP, paramServicesPackage.servicesAdminGroupId, this.servicesAdminGroupId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthLoginToken", paramServicesPackage.getAuthLoginToken(), this.auth_login_token, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthLoginScratch", paramServicesPackage.getAuthLoginScratch(), this.auth_login_scratch, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthLoginChallenge", paramServicesPackage.getAuthLoginChallenge(), this.auth_login_challenge, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthTransToken", paramServicesPackage.getAuthTransToken(), this.auth_trans_token, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthTransScratch", paramServicesPackage.getAuthTransScratch(), this.auth_trans_scratch, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthTransChallenge", paramServicesPackage.getAuthTransChallenge(), this.auth_trans_challenge, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthPrivToken", paramServicesPackage.getAuthPrivToken(), this.auth_priv_token, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthPrivScratch", paramServicesPackage.getAuthPrivScratch(), this.auth_priv_scratch, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthPrivChallenge", paramServicesPackage.getAuthPrivChallenge(), this.auth_priv_challenge, paramString);
    super.logChanges(paramHistoryTracker, paramServicesPackage, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, ID, this.id, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, ID, this.id, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, SERVICESPACKAGENAME, this.servicesPackageName, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, MARKETSEGMENT, this.marketSegmentId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, AFFILIATEBANKID, this.affiliateBankId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, DESCRIPTION, this.description, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, SERVICESADMINGROUP, this.servicesAdminGroupId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthLoginToken", new Boolean(this.auth_login_token).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthLoginScratch", new Boolean(this.auth_login_scratch).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthLoginChallenge", new Boolean(this.auth_login_challenge).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthTransToken", new Boolean(this.auth_trans_token).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthTransScratch", new Boolean(this.auth_trans_scratch).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthTransChallenge", new Boolean(this.auth_trans_challenge).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthPrivToken", new Boolean(this.auth_priv_token).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthPrivScratch", new Boolean(this.auth_priv_scratch).toString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthPrivChallenge", new Boolean(this.auth_priv_challenge).toString(), paramString);
    super.logDeletion(paramHistoryTracker, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ServicesPackage paramServicesPackage, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, ID, paramServicesPackage.id, this.id, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGENAME, paramServicesPackage.servicesPackageName, this.servicesPackageName, paramILocalizable);
    try
    {
      paramHistoryTracker.detectChange(BEAN_NAME, MARKETSEGMENT, com.ffusion.csil.core.Entitlements.getEntitlementGroup(Integer.parseInt(paramServicesPackage.marketSegmentId)).getGroupName(), com.ffusion.csil.core.Entitlements.getEntitlementGroup(Integer.parseInt(this.marketSegmentId)).getGroupName(), paramILocalizable);
    }
    catch (Exception localException1)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, MARKETSEGMENT, paramServicesPackage.marketSegmentId, this.marketSegmentId, paramILocalizable);
    }
    try
    {
      String str1 = (this.affiliateBankId == null) || (this.affiliateBankId.length() == 0) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(this.affiliateBankId)).getAffiliateBankName();
      String str2 = (paramServicesPackage.affiliateBankId == null) || (paramServicesPackage.affiliateBankId.length() == 0) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(paramServicesPackage.affiliateBankId)).getAffiliateBankName();
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATEBANK, str2, str1, paramILocalizable);
    }
    catch (Exception localException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATEBANKID, paramServicesPackage.affiliateBankId, this.affiliateBankId, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, DESCRIPTION, paramServicesPackage.description, this.description, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthLoginToken", paramServicesPackage.getAuthLoginToken(), this.auth_login_token, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthLoginScratch", paramServicesPackage.getAuthLoginScratch(), this.auth_login_scratch, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthLoginChallenge", paramServicesPackage.getAuthLoginChallenge(), this.auth_login_challenge, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthTransToken", paramServicesPackage.getAuthTransToken(), this.auth_trans_token, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthTransScratch", paramServicesPackage.getAuthTransScratch(), this.auth_trans_scratch, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthTransChallenge", paramServicesPackage.getAuthTransChallenge(), this.auth_trans_challenge, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthPrivToken", paramServicesPackage.getAuthPrivToken(), this.auth_priv_token, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthPrivScratch", paramServicesPackage.getAuthPrivScratch(), this.auth_priv_scratch, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AuthPrivChallenge", paramServicesPackage.getAuthPrivChallenge(), this.auth_priv_challenge, paramILocalizable);
    super.logChanges(paramHistoryTracker, paramServicesPackage, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, ID, this.id, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, SERVICESPACKAGENAME, this.servicesPackageName, paramILocalizable);
    try
    {
      paramHistoryTracker.logDelete(BEAN_NAME, MARKETSEGMENT, com.ffusion.csil.core.Entitlements.getEntitlementGroup(Integer.parseInt(this.marketSegmentId)).getGroupName(), paramILocalizable);
    }
    catch (Exception localException1)
    {
      paramHistoryTracker.logDelete(BEAN_NAME, MARKETSEGMENT, this.marketSegmentId, paramILocalizable);
    }
    try
    {
      String str = (this.affiliateBankId == null) || (this.affiliateBankId.length() == 0) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(this.affiliateBankId)).getAffiliateBankName();
      paramHistoryTracker.logDelete(BEAN_NAME, AFFILIATEBANK, str, paramILocalizable);
    }
    catch (Exception localException2)
    {
      paramHistoryTracker.logDelete(BEAN_NAME, AFFILIATEBANKID, this.affiliateBankId, paramILocalizable);
    }
    paramHistoryTracker.logDelete(BEAN_NAME, DESCRIPTION, this.description, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthLoginToken", new Boolean(this.auth_login_token).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthLoginScratch", new Boolean(this.auth_login_scratch).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthLoginChallenge", new Boolean(this.auth_login_challenge).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthTransToken", new Boolean(this.auth_trans_token).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthTransScratch", new Boolean(this.auth_trans_scratch).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthTransChallenge", new Boolean(this.auth_trans_challenge).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthPrivToken", new Boolean(this.auth_priv_token).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthPrivScratch", new Boolean(this.auth_priv_scratch).toString(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AuthPrivChallenge", new Boolean(this.auth_priv_challenge).toString(), paramILocalizable);
    super.logDeletion(paramHistoryTracker, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.ServicesPackage
 * JD-Core Version:    0.7.0.1
 */