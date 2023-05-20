package com.ffusion.beans.business;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.Contact;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import java.util.logging.Level;

public class Business
  extends Contact
  implements BusinessDefines, Comparable, Serializable, CollectionElement
{
  public static String BUSINESS = "BUSINESS";
  public static String ID = "ID";
  public static String BUSINESSNAME = "BUSINESSNAME";
  public static String PASSWORD = "PASSWORD";
  public static String STATUS = "STATUS";
  public static String TAXID = "TAXID";
  public static String BANKID = "BANKID";
  public static String BUSINESSCIF = "BUSINESSCIF";
  public static String PERSONALCIF = "PERSONALCIF";
  public static String ENTITLEMENTGROUPID = "ENTITLEMENTGROUPID";
  public static String DESCRIPTION = "DESCRIPTION";
  public static String APPROVALGROUP = "APPROVALGROUP";
  public static String SERVICESPACKAGEID = "SERVICESPACKAGEID";
  public static String ACHALLOWZERODAYS = "ACHALLOWZERODAYS";
  public static String ACHCREDITLEADDAYS = "ACHCREDITLEADDAYS";
  public static String ACHDEBITLEADDAYS = "ACHDEBITLEADDAYS";
  public static String TRANSACTIONLIMITS = "TRANSACTIONLIMITS";
  public static String AFFILIATE_BANK_ID = "AFFILIATE_BANK_ID";
  public static String ACH_BATCH_TYPE = "ACH_BATCH_TYPE";
  public static String PPAY_DEFAULT_DECISION = "PPAY_DEFAULT_DECISION";
  public static String TRACKING_ID = "TRACKING_ID";
  public static String ACTIVATION_DATE = "ACTIVATION_DATE";
  public static String PERSONAL_BANKER = "PERSONAL_BANKER";
  public static String ACCOUNT_REP = "ACCOUNT_REP";
  public static String ACCOUNT_NICKNAME = "ACCOUNT_NICKNAME";
  public static String APPROVED_BY = "APPROVED_BY";
  public static String ACCOUNTS = "ACCOUNTS";
  public static String ADMINISTRATOR = "ADMINISTRATOR";
  public static String ACCOUNT_CALCULATIONS = "CALCULATIONS";
  public static String ACCOUNT_GROUP = "ACCOUNT_GROUP";
  public static String PRIMARY_CONTACT_PERMS = "PRIMARY_CONTACT_PERMS";
  public static String SECONDARY_CONTACT_PERMS = "SECONDARY_CONTACT_PERMS";
  public static String LOCATIONID_PLACEMENT = "LOCATIONID_PLACEMENT";
  public static final String BEAN_NAME = Business.class.getName();
  public static final String SERVICESLOOKUPFAIL = "ServicesLookupFail";
  public static final String ENTITLEGROUPLOOKUPFAIL = "EntitleGroupLookupFail";
  public static final String BANKNAMELOOKUPFAIL = "BankNameLookupFail";
  public static final String EMPLOYEELOOKUPFAIL = "EmployeeLookupFail";
  public static final String CUSTOMERID = "CustomerID";
  public static final String ACH_RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  public static final String BIZ_RESOURCE_BUNDLE = "com.ffusion.beans.business.resources";
  public static final String RESOURCE_KEY_LOCATION_ID_PLACEMENT = "LocationIDPlacement";
  protected String businessName;
  int fZ;
  String fX;
  String f2;
  String fS;
  String fU;
  String fY;
  protected String businessCIF;
  protected String personalCIF;
  int f0;
  EntitlementGroup f1;
  protected int servicesPackageId;
  String fR;
  DateTime fW;
  String fQ;
  String fV;
  String f3;
  protected String approvalGroup;
  protected int affiliateBankID;
  TransactionLimits fT;
  protected int achBatchType;
  protected boolean achAllowZeroDays = false;
  protected int achCreditLeadDays = 2;
  protected int achDebitLeadDays = 1;
  protected String pPayDefaultDecision = "None";
  protected int primaryContactPerms;
  protected int secondaryContactPerms;
  protected int locationIdPlacement;
  
  public Business()
  {
    setKeysNotToLog("CUST_ID");
    setPreferredLanguage("en_US");
  }
  
  public Business(Locale paramLocale)
  {
    this();
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    setLocale(paramLocale);
    setPreferredLanguage(paramLocale.toString());
  }
  
  public String getTrackingID()
  {
    return this.fY;
  }
  
  public void setTrackingID(String paramString)
  {
    this.fY = paramString;
  }
  
  public String getEmail()
  {
    if (this.email != null) {
      return this.email.toString();
    }
    return "";
  }
  
  public String getPhone()
  {
    if (this.phone != null) {
      return this.phone.toString();
    }
    return "";
  }
  
  public String getPhone2()
  {
    if (this.phone2 != null) {
      return this.phone2.toString();
    }
    return "";
  }
  
  public String getZipCode()
  {
    if (this.zipCode != null) {
      return this.zipCode.toString();
    }
    return "";
  }
  
  public String getBusinessName()
  {
    return this.businessName;
  }
  
  public void setBusinessName(String paramString)
  {
    this.businessName = paramString;
  }
  
  public String getBusinessNameLowerCase()
  {
    return this.businessName != null ? this.businessName.toLowerCase() : null;
  }
  
  public String getPassword()
  {
    return this.fX;
  }
  
  public void setPassword(String paramString)
  {
    this.fX = paramString;
  }
  
  public String getCustId()
  {
    return (String)get("CUST_ID");
  }
  
  public void setCustId(String paramString)
  {
    put("CUST_ID", paramString);
  }
  
  public String getId()
  {
    return String.valueOf(this.fZ);
  }
  
  public int getIdValue()
  {
    return this.fZ;
  }
  
  public void setId(int paramInt)
  {
    this.fZ = paramInt;
  }
  
  public void setId(String paramString)
  {
    this.fZ = Integer.valueOf(paramString).intValue();
  }
  
  public void setStatus(String paramString)
  {
    this.fU = paramString;
  }
  
  public void setBankId(String paramString)
  {
    this.f2 = paramString;
  }
  
  public String getBankId()
  {
    return this.f2;
  }
  
  public int getBankIdValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.f2);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setTaxId(String paramString)
  {
    this.fS = paramString;
  }
  
  public String getTaxId()
  {
    return this.fS;
  }
  
  public String getStatus()
  {
    return this.fU;
  }
  
  public int getStatusValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.fU);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setBusinessCIF(String paramString)
  {
    this.businessCIF = paramString;
  }
  
  public String getBusinessCIF()
  {
    return this.businessCIF;
  }
  
  public void setPersonalCIF(String paramString)
  {
    this.personalCIF = paramString;
  }
  
  public String getPersonalCIF()
  {
    return this.personalCIF;
  }
  
  public void setEntitlementGroupId(int paramInt)
  {
    this.f0 = paramInt;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    try
    {
      this.f0 = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getEntitlementGroupId()
  {
    return String.valueOf(this.f0);
  }
  
  public int getEntitlementGroupIdValue()
  {
    return this.f0;
  }
  
  public void setServicesPackageId(int paramInt)
  {
    this.servicesPackageId = paramInt;
  }
  
  public void setServicesPackageId(String paramString)
  {
    try
    {
      this.servicesPackageId = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getServicesPackageId()
  {
    return String.valueOf(this.servicesPackageId);
  }
  
  public int getServicesPackageIdValue()
  {
    return this.servicesPackageId;
  }
  
  public void setDescription(String paramString)
  {
    this.fR = paramString;
  }
  
  public String getDescription()
  {
    return this.fR;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.fW != null) {
      this.fW.setFormat(paramString);
    }
  }
  
  public void setActivationDate(DateTime paramDateTime)
  {
    this.fW = paramDateTime;
  }
  
  public DateTime getActivationDate()
  {
    return this.fW;
  }
  
  public void setPersonalBanker(String paramString)
  {
    this.fQ = paramString;
  }
  
  public String getPersonalBanker()
  {
    return this.fQ;
  }
  
  public void setAccountRep(String paramString)
  {
    this.fV = paramString;
  }
  
  public String getAccountRep()
  {
    return this.fV;
  }
  
  public void setApprovedBy(String paramString)
  {
    this.f3 = paramString;
  }
  
  public String getApprovedBy()
  {
    return this.f3;
  }
  
  public void setApprovalGroup(String paramString)
  {
    this.approvalGroup = paramString;
  }
  
  public String getApprovalGroup()
  {
    return this.approvalGroup;
  }
  
  public int getApprovalGroupValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.approvalGroup);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setTransactionLimits(TransactionLimits paramTransactionLimits)
  {
    this.fT = paramTransactionLimits;
  }
  
  public TransactionLimits getTransactionLimits()
  {
    return this.fT;
  }
  
  public void setACHAllowZeroDays(boolean paramBoolean)
  {
    this.achAllowZeroDays = paramBoolean;
  }
  
  public void setACHAllowZeroDays(String paramString)
  {
    if (paramString != null) {
      this.achAllowZeroDays = ((paramString.equalsIgnoreCase("TRUE")) || (paramString.equalsIgnoreCase("T")) || (paramString.equalsIgnoreCase("YES")) || (paramString.equalsIgnoreCase("Y")));
    }
  }
  
  public String getACHAllowZeroDays()
  {
    return "" + this.achAllowZeroDays;
  }
  
  public boolean getACHAllowZeroDaysValue()
  {
    return this.achAllowZeroDays;
  }
  
  public void setACHBatchType(int paramInt)
  {
    this.achBatchType = paramInt;
  }
  
  public void setACHBatchType(String paramString)
  {
    try
    {
      this.achBatchType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getACHBatchType()
  {
    return ResourceUtil.getString("ACHBatchType" + this.achBatchType, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int getACHBatchTypeValue()
  {
    return this.achBatchType;
  }
  
  public void setAchCreditLeadDays(int paramInt)
  {
    this.achCreditLeadDays = paramInt;
  }
  
  public int getAchCreditLeadDaysValue()
  {
    return this.achCreditLeadDays;
  }
  
  public void setAchCreditLeadDays(String paramString)
  {
    this.achCreditLeadDays = Integer.parseInt(paramString);
  }
  
  public String getAchCreditLeadDays()
  {
    return String.valueOf(this.achCreditLeadDays);
  }
  
  public void setAchDebitLeadDays(int paramInt)
  {
    this.achDebitLeadDays = paramInt;
  }
  
  public int getAchDebitLeadDaysValue()
  {
    return this.achDebitLeadDays;
  }
  
  public void setAchDebitLeadDays(String paramString)
  {
    this.achDebitLeadDays = Integer.parseInt(paramString);
  }
  
  public String getAchDebitLeadDays()
  {
    return String.valueOf(this.achDebitLeadDays);
  }
  
  public void setPPayDefaultDecision(String paramString)
  {
    this.pPayDefaultDecision = paramString;
  }
  
  public String getPPayDefaultDecision()
  {
    return this.pPayDefaultDecision;
  }
  
  public void setPrimaryContactPerms(int paramInt)
  {
    this.primaryContactPerms = paramInt;
  }
  
  public void setPrimaryContactPerms(String paramString)
  {
    try
    {
      this.primaryContactPerms = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getPrimaryContactPerms()
  {
    return String.valueOf(this.primaryContactPerms);
  }
  
  public int getPrimaryContactPermsValue()
  {
    return this.primaryContactPerms;
  }
  
  public void setSecondaryContactPerms(int paramInt)
  {
    this.secondaryContactPerms = paramInt;
  }
  
  public void setSecondaryContactPerms(String paramString)
  {
    try
    {
      this.secondaryContactPerms = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getSecondaryContactPerms()
  {
    return String.valueOf(this.secondaryContactPerms);
  }
  
  public int getSecondaryContactPermsValue()
  {
    return this.secondaryContactPerms;
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, BUSINESS);
    XMLHandler.appendTag(localStringBuffer, ID, this.fZ);
    XMLHandler.appendTag(localStringBuffer, PASSWORD, this.fX);
    XMLHandler.appendTag(localStringBuffer, BUSINESSNAME, this.businessName);
    XMLHandler.appendTag(localStringBuffer, STATUS, this.fU);
    XMLHandler.appendTag(localStringBuffer, TAXID, this.fS);
    XMLHandler.appendTag(localStringBuffer, BANKID, this.f2);
    XMLHandler.appendTag(localStringBuffer, BUSINESSCIF, this.businessCIF);
    XMLHandler.appendTag(localStringBuffer, PERSONALCIF, this.personalCIF);
    XMLHandler.appendTag(localStringBuffer, ENTITLEMENTGROUPID, Integer.toString(this.f0));
    XMLHandler.appendTag(localStringBuffer, DESCRIPTION, this.fR);
    XMLHandler.appendTag(localStringBuffer, ACHALLOWZERODAYS, getACHAllowZeroDays());
    XMLHandler.appendTag(localStringBuffer, ACHCREDITLEADDAYS, getAchCreditLeadDaysValue());
    XMLHandler.appendTag(localStringBuffer, ACHDEBITLEADDAYS, getAchDebitLeadDaysValue());
    XMLHandler.appendTag(localStringBuffer, PRIMARY_CONTACT_PERMS, getPrimaryContactPerms());
    XMLHandler.appendTag(localStringBuffer, SECONDARY_CONTACT_PERMS, getSecondaryContactPerms());
    XMLHandler.appendTag(localStringBuffer, LOCATIONID_PLACEMENT, getLocationIdPlacementValue());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, BUSINESS);
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals(ID)) {
      this.fZ = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals(BUSINESSNAME)) {
      this.businessName = paramString2;
    } else if (paramString1.equals(PASSWORD)) {
      this.fX = paramString2;
    } else if (paramString1.equals(STATUS)) {
      this.fU = paramString2;
    } else if (paramString1.equals(TAXID)) {
      this.fS = paramString2;
    } else if (paramString1.equals(BANKID)) {
      this.f2 = paramString2;
    } else if (paramString1.equals(BUSINESSCIF)) {
      this.businessCIF = paramString2;
    } else if (paramString1.equals(PERSONALCIF)) {
      this.personalCIF = paramString2;
    } else if (paramString1.equals(ENTITLEMENTGROUPID)) {
      this.f0 = Integer.parseInt(paramString2);
    } else if (paramString1.equals(DESCRIPTION)) {
      this.fR = paramString2;
    } else if (paramString1.equals(AFFILIATE_BANK_ID)) {
      this.affiliateBankID = Integer.parseInt(paramString2);
    } else if (paramString1.equals(ACH_BATCH_TYPE)) {
      this.achBatchType = Integer.parseInt(paramString2);
    } else if (paramString1.equals(PPAY_DEFAULT_DECISION)) {
      this.pPayDefaultDecision = paramString2;
    } else if (paramString1.equals(ACHALLOWZERODAYS)) {
      setACHAllowZeroDays(paramString2);
    } else if (paramString1.equals(PRIMARY_CONTACT_PERMS)) {
      setPrimaryContactPerms(paramString2);
    } else if (paramString1.equals(SECONDARY_CONTACT_PERMS)) {
      setSecondaryContactPerms(paramString2);
    } else if (paramString1.equals(LOCATIONID_PLACEMENT)) {
      setLocationIdPlacement(Integer.parseInt(paramString2));
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(Business paramBusiness)
  {
    setBusinessName(paramBusiness.getBusinessName());
    setId(paramBusiness.getIdValue());
    setPassword(paramBusiness.getPassword());
    setStatus(paramBusiness.getStatus());
    setTaxId(paramBusiness.getTaxId());
    setBankId(paramBusiness.getBankId());
    setBusinessCIF(paramBusiness.getBusinessCIF());
    setPersonalCIF(paramBusiness.getPersonalCIF());
    setEntitlementGroupId(paramBusiness.getEntitlementGroupId());
    setDescription(paramBusiness.getDescription());
    setActivationDate(paramBusiness.getActivationDate());
    setPersonalBanker(paramBusiness.getPersonalBanker());
    setAccountRep(paramBusiness.getAccountRep());
    setApprovedBy(paramBusiness.getApprovedBy());
    setApprovalGroup(paramBusiness.getApprovalGroup());
    setTransactionLimits(paramBusiness.getTransactionLimits());
    setServicesPackageId(paramBusiness.getServicesPackageId());
    setAffiliateBankID(paramBusiness.getAffiliateBankID());
    setACHBatchType(paramBusiness.getACHBatchTypeValue());
    setPPayDefaultDecision(paramBusiness.getPPayDefaultDecision());
    setACHAllowZeroDays(paramBusiness.getACHAllowZeroDaysValue());
    setAchCreditLeadDays(paramBusiness.getAchCreditLeadDaysValue());
    setAchDebitLeadDays(paramBusiness.getAchDebitLeadDaysValue());
    setPrimaryContactPerms(paramBusiness.getPrimaryContactPermsValue());
    setSecondaryContactPerms(paramBusiness.getSecondaryContactPermsValue());
    setLocationIdPlacement(paramBusiness.getLocationIdPlacementValue());
    super.set(paramBusiness);
  }
  
  public EntitlementGroup getEntitlementGroup()
  {
    return this.f1;
  }
  
  public void setEntitlementGroup(EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    setEntitlementGroupId(paramEntitlementGroup.getGroupId());
    if (paramEntitlementGroup.getEntGroupType().equals("BusinessAdmin")) {
      setServicesPackageId(paramEntitlementGroup.getParentId());
    } else if (paramEntitlementGroup.getEntGroupType().equals("Business")) {
      setServicesPackageId(Entitlements.getEntitlementGroup(paramEntitlementGroup.getParentId()).getParentId());
    }
    this.f1 = paramEntitlementGroup;
  }
  
  public EntitlementGroupMember getEntitlementGroupMember()
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(getId());
    localEntitlementGroupMember.setEntitlementGroupId(getEntitlementGroupIdValue());
    localEntitlementGroupMember.setMemberType("BUSINESS");
    localEntitlementGroupMember.setMemberSubType("0");
    return localEntitlementGroupMember;
  }
  
  public int getAffiliateBankID()
  {
    return this.affiliateBankID;
  }
  
  public String getAffiliateBankIDStr()
  {
    return Integer.toString(this.affiliateBankID);
  }
  
  public void setAffiliateBankID(int paramInt)
  {
    this.affiliateBankID = paramInt;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.affiliateBankID = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getLocationIdPlacement()
  {
    String str = "";
    try
    {
      str = ResourceUtil.getString("LocationIDPlacement_" + this.locationIdPlacement, "com.ffusion.beans.business.resources", this.locale);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.WARNING, "Unable to retrieve value of " + getLocationIdPlacementValue() + " from " + "com.ffusion.beans.business.resources" + ".");
    }
    return str;
  }
  
  public int getLocationIdPlacementValue()
  {
    return this.locationIdPlacement;
  }
  
  public void setLocationIdPlacement(int paramInt)
  {
    this.locationIdPlacement = paramInt;
  }
  
  public void setLocationIdPlacement(String paramString)
  {
    try
    {
      this.locationIdPlacement = Integer.valueOf(paramString).intValue();
    }
    catch (Exception localException) {}
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    Business localBusiness = (Business)paramObject;
    int i = 1;
    if ((paramString.equalsIgnoreCase(BUSINESSNAME)) && (getBusinessName() != null) && (localBusiness.getBusinessName() != null)) {
      i = localCollator.compare(getBusinessName().toLowerCase(this.locale), localBusiness.getBusinessName().toLowerCase(this.locale));
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase(BUSINESSNAME)) && (getBusinessName() != null)) {
      return isFilterable(getBusinessName(), paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase(STATUS)) && (getStatus() != null)) {
      return isFilterable(getStatus(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Business localBusiness = (Business)paramObject;
    int i;
    if (this == localBusiness) {
      i = 0;
    } else {
      i = getBusinessName().compareTo(localBusiness.getBusinessName());
    }
    return i;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Business paramBusiness, String paramString)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str1 = "";
    String str2 = "";
    paramHistoryTracker.detectChange(BEAN_NAME, ID, paramBusiness.fZ, this.fZ, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, BUSINESSNAME, paramBusiness.businessName, this.businessName, paramString);
    if ((paramBusiness.fX != this.fX) && ((this.fX == null) || (!this.fX.equals(paramBusiness.fX)))) {
      paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, PASSWORD), (String)null, (String)null, paramString);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, STATUS, ResourceUtil.getString(paramBusiness.fU, "com.ffusion.beans.business.resources", this.locale), ResourceUtil.getString(this.fU, "com.ffusion.beans.business.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, TAXID, paramBusiness.fS, this.fS, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, BANKID, paramBusiness.f2, this.f2, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, BUSINESSCIF, paramBusiness.businessCIF, this.businessCIF, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, PERSONALCIF, paramBusiness.personalCIF, this.personalCIF, paramString);
    if ((paramBusiness.f0 > 0) && (this.f0 > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, ENTITLEMENTGROUPID, Entitlements.getEntitlementGroup(paramBusiness.f0).getGroupName(), Entitlements.getEntitlementGroup(this.f0).getGroupName(), paramString);
      }
      catch (CSILException localCSILException1)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, ENTITLEMENTGROUPID, paramBusiness.f0, this.f0, ResourceUtil.getString("EntitleGroupLookupFail", "com.ffusion.beans.business.resources", this.locale));
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, ENTITLEMENTGROUPID, paramBusiness.f0, this.f0, ResourceUtil.getString("EntitleGroupLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, DESCRIPTION, paramBusiness.fR, this.fR, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, APPROVALGROUP, paramBusiness.approvalGroup, this.approvalGroup, paramString);
    if ((paramBusiness.servicesPackageId > 0) && (this.servicesPackageId > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, Entitlements.getEntitlementGroup(paramBusiness.servicesPackageId).getGroupName(), Entitlements.getEntitlementGroup(this.servicesPackageId).getGroupName(), paramString);
      }
      catch (CSILException localCSILException2)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramBusiness.servicesPackageId, this.servicesPackageId, ResourceUtil.getString("ServicesLookupFail", "com.ffusion.beans.business.resources", this.locale));
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramBusiness.servicesPackageId, this.servicesPackageId, ResourceUtil.getString("ServicesLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    try
    {
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATE_BANK_ID, AffiliateBankAdapter.getAffiliateBankByID(null, paramBusiness.affiliateBankID).getAffiliateBankName(), AffiliateBankAdapter.getAffiliateBankByID(null, this.affiliateBankID).getAffiliateBankName(), paramString);
    }
    catch (ProfileException localProfileException1)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATE_BANK_ID, paramBusiness.affiliateBankID, this.affiliateBankID, ResourceUtil.getString("BankNameLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, ACH_BATCH_TYPE, ResourceUtil.getString("ACHBatchType" + paramBusiness.achBatchType, "com.ffusion.beans.ach.resources", this.locale), ResourceUtil.getString("ACHBatchType" + this.achBatchType, "com.ffusion.beans.ach.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, PPAY_DEFAULT_DECISION, paramBusiness.pPayDefaultDecision, this.pPayDefaultDecision, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, TRACKING_ID, paramBusiness.fY, this.fY, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, ACTIVATION_DATE, paramBusiness.fW, this.fW, paramString);
    try
    {
      if ((paramBusiness.fQ == null) || (paramBusiness.fQ.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBusiness.fQ);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.fQ == null) || (this.fQ.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.fQ);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, PERSONAL_BANKER, str1, str2, paramString);
    }
    catch (ProfileException localProfileException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, PERSONAL_BANKER, paramBusiness.fQ, this.fQ, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    try
    {
      if ((paramBusiness.fV == null) || (paramBusiness.fV.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBusiness.fV);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.fV == null) || (this.fV.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.fV);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, ACCOUNT_REP, str1, str2, paramString);
    }
    catch (ProfileException localProfileException3)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, ACCOUNT_REP, paramBusiness.fV, this.fV, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    try
    {
      if ((paramBusiness.f3 == null) || (paramBusiness.f3.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBusiness.f3);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.f3 == null) || (this.f3.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.f3);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, APPROVED_BY, str1, str2, paramString);
    }
    catch (ProfileException localProfileException4)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, APPROVED_BY, paramBusiness.f3, this.f3, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, ACHALLOWZERODAYS, paramBusiness.getACHAllowZeroDays(), getACHAllowZeroDays(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, ACHCREDITLEADDAYS, paramBusiness.getAchCreditLeadDays(), getAchCreditLeadDays(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, ACHDEBITLEADDAYS, paramBusiness.getAchDebitLeadDays(), getAchDebitLeadDays(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, PRIMARY_CONTACT_PERMS, paramBusiness.getPrimaryContactPerms(), getPrimaryContactPerms(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, SECONDARY_CONTACT_PERMS, paramBusiness.getSecondaryContactPerms(), getSecondaryContactPerms(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, ResourceUtil.getString("CustomerID", "com.ffusion.beans.business.resources", this.locale), paramBusiness.getCustId(), getCustId(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, LOCATIONID_PLACEMENT, paramBusiness.getLocationIdPlacement(), getLocationIdPlacement(), paramString);
    super.logChanges(paramHistoryTracker, paramBusiness, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Business paramBusiness, ILocalizable paramILocalizable)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str1 = "";
    String str2 = "";
    paramHistoryTracker.detectChange(BEAN_NAME, ID, paramBusiness.fZ, this.fZ, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, BUSINESSNAME, paramBusiness.businessName, this.businessName, paramILocalizable);
    if ((paramBusiness.fX != this.fX) && ((this.fX == null) || (!this.fX.equals(paramBusiness.fX)))) {
      paramHistoryTracker.logChange(BEAN_NAME, PASSWORD, (String)null, (String)null, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, STATUS, ResourceUtil.getString(paramBusiness.fU, "com.ffusion.beans.business.resources", this.locale), ResourceUtil.getString(this.fU, "com.ffusion.beans.business.resources", this.locale), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, TAXID, paramBusiness.fS, this.fS, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, BANKID, paramBusiness.f2, this.f2, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, BUSINESSCIF, paramBusiness.businessCIF, this.businessCIF, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, PERSONALCIF, paramBusiness.personalCIF, this.personalCIF, paramILocalizable);
    if ((paramBusiness.f0 > 0) && (this.f0 > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, ENTITLEMENTGROUPID, Entitlements.getEntitlementGroup(paramBusiness.f0).getGroupName(), Entitlements.getEntitlementGroup(this.f0).getGroupName(), paramILocalizable);
      }
      catch (CSILException localCSILException1)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, ENTITLEMENTGROUPID, paramBusiness.f0, this.f0, new LocalizableString("com.ffusion.beans.business.resources", "EntitleGroupLookupFail", null));
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, ENTITLEMENTGROUPID, paramBusiness.f0, this.f0, new LocalizableString("com.ffusion.beans.business.resources", "EntitleGroupLookupFail", null));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, DESCRIPTION, paramBusiness.fR, this.fR, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, APPROVALGROUP, paramBusiness.approvalGroup, this.approvalGroup, paramILocalizable);
    if ((paramBusiness.servicesPackageId > 0) && (this.servicesPackageId > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, Entitlements.getEntitlementGroup(paramBusiness.servicesPackageId).getGroupName(), Entitlements.getEntitlementGroup(this.servicesPackageId).getGroupName(), paramILocalizable);
      }
      catch (CSILException localCSILException2)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramBusiness.servicesPackageId, this.servicesPackageId, new LocalizableString("com.ffusion.beans.business.resources", "ServicesLookupFail", null));
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramBusiness.servicesPackageId, this.servicesPackageId, new LocalizableString("com.ffusion.beans.business.resources", "ServicesLookupFail", null));
    }
    try
    {
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATE_BANK_ID, AffiliateBankAdapter.getAffiliateBankByID(null, paramBusiness.affiliateBankID).getAffiliateBankName(), AffiliateBankAdapter.getAffiliateBankByID(null, this.affiliateBankID).getAffiliateBankName(), paramILocalizable);
    }
    catch (ProfileException localProfileException1)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, AFFILIATE_BANK_ID, paramBusiness.affiliateBankID, this.affiliateBankID, new LocalizableString("com.ffusion.beans.business.resources", "BankNameLookupFail", null));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, ACH_BATCH_TYPE, ResourceUtil.getString("ACHBatchType" + paramBusiness.achBatchType, "com.ffusion.beans.ach.resources", this.locale), ResourceUtil.getString("ACHBatchType" + this.achBatchType, "com.ffusion.beans.ach.resources", this.locale), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, PPAY_DEFAULT_DECISION, paramBusiness.pPayDefaultDecision, this.pPayDefaultDecision, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, TRACKING_ID, paramBusiness.fY, this.fY, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, ACTIVATION_DATE, new LocalizableDate(paramBusiness.fW, true), new LocalizableDate(this.fW, true), paramILocalizable);
    try
    {
      if ((paramBusiness.fQ == null) || (paramBusiness.fQ.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBusiness.fQ);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.fQ == null) || (this.fQ.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.fQ);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, PERSONAL_BANKER, str1, str2, paramILocalizable);
    }
    catch (ProfileException localProfileException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, PERSONAL_BANKER, paramBusiness.fQ, this.fQ, new LocalizableString("com.ffusion.beans.business.resources", "EmployeeLookupFail", null));
    }
    try
    {
      if ((paramBusiness.fV == null) || (paramBusiness.fV.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBusiness.fV);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.fV == null) || (this.fV.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.fV);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, ACCOUNT_REP, str1, str2, paramILocalizable);
    }
    catch (ProfileException localProfileException3)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, ACCOUNT_REP, paramBusiness.fV, this.fV, new LocalizableString("com.ffusion.beans.business.resources", "EmployeeLookupFail", null));
    }
    try
    {
      if ((paramBusiness.f3 == null) || (paramBusiness.f3.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBusiness.f3);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.f3 == null) || (this.f3.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.f3);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, APPROVED_BY, str1, str2, paramILocalizable);
    }
    catch (ProfileException localProfileException4)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, APPROVED_BY, paramBusiness.f3, this.f3, new LocalizableString("com.ffusion.beans.business.resources", "EmployeeLookupFail", null));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, ACHALLOWZERODAYS, paramBusiness.getACHAllowZeroDays(), getACHAllowZeroDays(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, ACHCREDITLEADDAYS, paramBusiness.getAchCreditLeadDays(), getAchCreditLeadDays(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, ACHDEBITLEADDAYS, paramBusiness.getAchDebitLeadDays(), getAchDebitLeadDays(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, PRIMARY_CONTACT_PERMS, paramBusiness.getPrimaryContactPerms(), getPrimaryContactPerms(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, SECONDARY_CONTACT_PERMS, paramBusiness.getSecondaryContactPerms(), getSecondaryContactPerms(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, ResourceUtil.getString("CustomerID", "com.ffusion.beans.business.resources", this.locale), paramBusiness.getCustId(), getCustId(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, LOCATIONID_PLACEMENT, paramBusiness.getLocationIdPlacement(), getLocationIdPlacement(), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramBusiness, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, BUSINESSNAME, this.businessName, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, ID, this.fZ, paramILocalizable);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      Business.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.Business
 * JD-Core Version:    0.7.0.1
 */