package com.ffusion.beans.user;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.Email;
import com.ffusion.beans.Person;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.SocialSecurity;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.LocalizableString;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

public class User
  extends Person
  implements UserDefines, Comparable, Serializable, CollectionElement
{
  public static final String USERINFO = "USERINFO";
  public static final String ID = "ID";
  public static final String CUSTID = "CUSTID";
  public static final String PASSWORD = "PASSWORD";
  public static final String PASSWORDREMINDER = "PASSWORDREMINDER";
  public static final String PASSWORDREMINDER2 = "PASSWORDREMINDER2";
  public static final String USERNAME = "USERNAME";
  public static final String TITLE = "TITLE";
  public static final String SSN = "SSN";
  public static final String ACCESSMODE = "ACCESSMODE";
  public static final String ACCOUNTSTATUS = "ACCOUNTSTATUS";
  public static final String SERVICELEVEL = "SERVICELEVEL";
  public static final String GREETINGTYPE = "GREETINGTYPE";
  public static final String PREFERRED_GREETING = "PREFERRED_GREETING";
  public static final String GREETING = "GREETING";
  public static final String TIMEOUT = "TIMEOUT";
  public static final String GENDER = "GENDER";
  public static final String PERSONALBANKER = "PERSONALBANKER";
  public static final String PASSWORDCLUE = "PASSWORDCLUE";
  public static final String PASSWORDCLUE2 = "PASSWORDCLUE2";
  public static final String BANK_ID = "BANK_ID";
  public static final String AFFILIATE_BANK_ID = "AFFILIATE_BANK_ID";
  public static final String CUSTOMER_TYPE = "CUSTOMER_TYPE";
  public static final String ENTITLMENT_GROUP_ID = "EntitlementGroupId";
  public static final String EMPLOYEELOOKUPFAIL = "EmployeeLookupFail";
  public static final String ENTITLEGROUPLOOKUPFAIL = "EntitleGroupLookupFail";
  public static final String SERVICESLOOKUPFAIL = "ServicesLookupFail";
  public static final String BANKNAMELOOKUPFAIL = "BankNameLookupFail";
  public static final String VERIFY_EMAIL = "VERIFY_EMAIL";
  public static final String SORTABLE_FULL_NAME = "SORTABLE_FULL_NAME";
  public static final String SORTABLE_FULL_NAME_WITH_LOGIN_ID = "SORTABLE_FULL_NAME_WITH_LOGIN_ID";
  public static final String TERMS_ACCEPTED_DATE = "TERMS_ACCEPTED_DATE";
  public static final String BIZ_RESOURCE_BUNDLE = "com.ffusion.beans.business.resources";
  public static final String USER_RESOURCE_BUNDLE = "com.ffusion.beans.user.resources";
  public static final String HISTORY_VALUE_KEY_TIMEOUT = "TIMEOUT";
  public static final String IGNORE_PASSWORD = "IgnorePassword";
  public static String SERVICESPACKAGEID = "SERVICESPACKAGEID";
  public static final String BEAN_NAME = User.class.getName();
  public static final String STATUS_ENROLLED = String.valueOf(0);
  public static final String STATUS_ACTIVE = String.valueOf(1);
  public static final String STATUS_INACTIVE = String.valueOf(2);
  public static final String STATUS_TEMP_INACTIVE = String.valueOf(64);
  public static final String STATUS_DELETED = String.valueOf(8);
  public static final String CUSTOMER_TYPE_EMPLOYEE = String.valueOf(1);
  public static final String CUSTOMER_TYPE_CONSUMER = String.valueOf(2);
  public static final String USER_TYPE_PRIMARY = String.valueOf(1);
  public static final String USER_TYPE_SECONDARY = String.valueOf(2);
  public static final int NON_CONSUMER_ID = 0;
  protected String id;
  protected String custId;
  protected String userName;
  protected String accountStatus;
  protected String title;
  protected String greeting;
  protected String password;
  protected String passwordReminder;
  protected String passwordReminder2;
  protected String serviceLevel;
  protected String currentService;
  protected String personalBanker;
  protected String accessMode;
  protected String greetingType;
  protected String timeout;
  protected String gender;
  protected CustomTag currentTag;
  protected CustomTags customTags;
  protected SocialSecurity ssn;
  protected String trackingID;
  protected String passwordClue;
  protected String passwordClue2;
  protected int entitlementGroupId;
  protected int servicesPackageId;
  protected int affiliateBankID = -1;
  protected String customerType;
  protected String primarySecondary;
  protected int termsAccepted = 0;
  protected DateTime termsAcceptedDate;
  
  public User()
  {
    super.setKeysNotToLog("PASSWORD_STATUS");
  }
  
  public User(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    super.setKeysNotToLog("PASSWORD_STATUS");
  }
  
  public Object clone()
  {
    User localUser = (User)super.clone();
    localUser.setKeysNotToLog("PASSWORD_STATUS");
    if (this.ssn != null) {
      localUser.ssn = ((SocialSecurity)this.ssn.clone());
    }
    return localUser;
  }
  
  public void setBankId(String paramString)
  {
    set("BANK_ID", paramString);
  }
  
  public String getBankId()
  {
    return (String)get("BANK_ID");
  }
  
  public int getBankIdIntValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(getBankId());
    }
    catch (Exception localException) {}
    return i;
  }
  
  public String getPasswordStatus()
  {
    return (String)get("PASSWORD_STATUS");
  }
  
  public int getPasswordStatusIntValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(getPasswordStatus());
    }
    catch (Exception localException) {}
    return i;
  }
  
  public String getPasswordStatusStr()
  {
    return ResourceUtil.getString("PASSWORD_STATUS." + getPasswordStatusIntValue(), "com.ffusion.beans.user.resources", this.locale);
  }
  
  public void setEntitlementGroupId(int paramInt)
  {
    this.entitlementGroupId = paramInt;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.entitlementGroupId = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.entitlementGroupId;
  }
  
  public SecureUser getSecureUser()
  {
    SecureUser localSecureUser = new SecureUser(this.locale);
    localSecureUser.setId(getCustId());
    localSecureUser.setUserName(getUserName());
    localSecureUser.setPassword(getPassword());
    localSecureUser.setBankID(getBankId());
    localSecureUser.setProfileID(getId());
    localSecureUser.setEntitlementID(getEntitlementGroupId());
    localSecureUser.setUserType(1);
    if ((getCustomerType() == null) || (!getCustomerType().equals(CUSTOMER_TYPE_CONSUMER))) {
      localSecureUser.setPrimaryUserID(0);
    }
    return localSecureUser;
  }
  
  public EntitlementGroupMember getEntitlementGroupMember()
  {
    return EntitlementsUtil.getEntitlementGroupMember(getSecureUser());
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.currentTag != null) {
      this.currentTag.setLocale(paramLocale);
    }
    if (this.customTags != null) {
      this.customTags.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.currentTag != null) {
      this.currentTag.setDateFormat(paramString);
    }
    if (this.customTags != null) {
      this.customTags.setDateFormat(paramString);
    }
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public int getIdValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.id);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setCustomerType(String paramString)
  {
    this.customerType = paramString;
  }
  
  public String getCustomerType()
  {
    return this.customerType;
  }
  
  public int getCustomerTypeValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.customerType);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setPrimarySecondary(String paramString)
  {
    this.primarySecondary = paramString;
  }
  
  public String getPrimarySecondary()
  {
    return this.primarySecondary;
  }
  
  public int getPrimarySecondaryValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.primarySecondary);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
  }
  
  public String getCustId()
  {
    return this.custId;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPasswordReminder(String paramString)
  {
    this.passwordReminder = paramString;
  }
  
  public void setPasswordReminder2(String paramString)
  {
    this.passwordReminder2 = paramString;
  }
  
  public String getPasswordReminder()
  {
    return this.passwordReminder;
  }
  
  public String getPasswordReminder2()
  {
    return this.passwordReminder2;
  }
  
  public void setPasswordClue(String paramString)
  {
    if (paramString == null) {
      this.passwordClue = null;
    } else {
      this.passwordClue = XMLUtil.XMLDecode(paramString);
    }
    if (this.passwordClue != null) {
      this.passwordClue = this.passwordClue.trim();
    }
  }
  
  public void setPasswordClue2(String paramString)
  {
    if (paramString == null) {
      this.passwordClue2 = null;
    } else {
      this.passwordClue2 = XMLUtil.XMLDecode(paramString);
    }
  }
  
  public String getPasswordClue()
  {
    return this.passwordClue;
  }
  
  public String getPasswordClue2()
  {
    return this.passwordClue2;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setGreeting(String paramString)
  {
    this.greeting = paramString;
  }
  
  public String getGreeting()
  {
    return this.greeting;
  }
  
  public void setGender(String paramString)
  {
    this.gender = paramString;
  }
  
  public String getGender()
  {
    return this.gender;
  }
  
  public void setPersonalBanker(String paramString)
  {
    this.personalBanker = paramString;
  }
  
  public String getPersonalBanker()
  {
    return this.personalBanker;
  }
  
  public void setAccessMode(String paramString)
  {
    this.accessMode = paramString;
  }
  
  public String getAccessMode()
  {
    return this.accessMode;
  }
  
  public void setAccountStatus(String paramString)
  {
    this.accountStatus = paramString;
  }
  
  public String getAccountStatus()
  {
    return this.accountStatus;
  }
  
  public void setTimeout(String paramString)
  {
    this.timeout = paramString;
  }
  
  public String getTimeout()
  {
    return this.timeout;
  }
  
  public void setGreetingType(String paramString)
  {
    this.greetingType = paramString;
  }
  
  public String getGreetingType()
  {
    return this.greetingType;
  }
  
  public String getPreferredGreeting()
  {
    if ((this.greetingType == null) || (this.greetingType.equals("0"))) {
      return getName();
    }
    if (this.greetingType.equals("1")) {
      return (getTitle() == null ? "" : new StringBuffer().append(getTitle()).append(" ").toString()) + getLastName();
    }
    if (this.greetingType.equals("2")) {
      return getName();
    }
    if (this.greetingType.equals("3")) {
      return getFirstName();
    }
    return getGreeting();
  }
  
  public void setSSN(String paramString)
  {
    if (paramString == null) {
      this.ssn = null;
    } else if (this.ssn == null) {
      this.ssn = new SocialSecurity(paramString, true);
    } else {
      this.ssn.fromString(paramString);
    }
  }
  
  public boolean isLastSetSSNValid()
  {
    return this.ssn.isValid();
  }
  
  public String getSSN()
  {
    if (this.ssn != null) {
      return this.ssn.toString();
    }
    return null;
  }
  
  public SocialSecurity getSSNValue()
  {
    return this.ssn;
  }
  
  public String getServiceLevel()
  {
    return this.serviceLevel;
  }
  
  public void setServiceLevel(String paramString)
  {
    this.serviceLevel = paramString;
  }
  
  public void setCurrentService(String paramString)
  {
    this.currentService = paramString;
  }
  
  public String getHasService()
  {
    int i = Integer.parseInt(this.currentService);
    int j = Integer.parseInt(this.serviceLevel);
    if ((i & j) > 0) {
      return "true";
    }
    return "false";
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
  
  public int getAffiliateBankID()
  {
    return this.affiliateBankID;
  }
  
  public void setAffiliateBankID(int paramInt)
  {
    this.affiliateBankID = paramInt;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this.affiliateBankID = Integer.parseInt(paramString);
  }
  
  public String getFullName()
  {
    return UserUtil.getFullName(getFirstName(), getLastName(), this.locale);
  }
  
  public String getFullNameWithLoginId()
  {
    return UserUtil.getFullNameWithLoginId(getFirstName(), getLastName(), this.userName, this.custId, this.locale);
  }
  
  public String getSortableFullName()
  {
    return UserUtil.getSortableFullName(getFirstName(), getLastName(), this.locale);
  }
  
  public String getSortableFullNameWithLoginId()
  {
    return UserUtil.getSortableFullNameWithLoginId(getFirstName(), getLastName(), this.userName, this.custId, this.locale);
  }
  
  public CustomTags getCustomTags()
  {
    return this.customTags;
  }
  
  public void setCustomTags(CustomTags paramCustomTags)
  {
    this.customTags = paramCustomTags;
  }
  
  public void setCurrentTag(String paramString)
  {
    if (this.customTags != null) {
      this.currentTag = this.customTags.getByTagName(paramString);
    }
  }
  
  public CustomTag getCurrentTag()
  {
    return this.currentTag;
  }
  
  public void setCurrentTagAndValue(String paramString)
  {
    int i = paramString.indexOf(",");
    String str1 = paramString.substring(0, i);
    String str2 = paramString.substring(i + 1, paramString.length());
    if (this.customTags == null) {
      this.customTags = new CustomTags(this.locale);
    }
    this.currentTag = this.customTags.getByTagName(str1);
    if (this.currentTag != null)
    {
      this.currentTag.setValue(str2);
    }
    else
    {
      CustomTag localCustomTag = this.customTags.add(str1);
      localCustomTag.setValue(str2);
    }
  }
  
  public String getCurrentTagValue()
  {
    if (this.currentTag != null) {
      return this.currentTag.getValue();
    }
    return "";
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    User localUser = (User)paramObject;
    int i;
    if (this == localUser) {
      i = 0;
    } else if ((getLastName() == null) && (localUser.getLastName() == null)) {
      i = 0;
    } else if ((getLastName() == null) && (localUser.getLastName() != null)) {
      i = -1;
    } else if ((getLastName() != null) && (localUser.getLastName() == null)) {
      i = 1;
    } else {
      i = getLastName().toLowerCase().compareTo(localUser.getLastName().toLowerCase());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    if (!(paramObject instanceof User)) {
      return super.compare(paramObject, paramString);
    }
    User localUser = (User)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ID")) && (getId() != null) && (localUser.getId() != null))
    {
      i = localCollator.compare(getId(), localUser.getId());
    }
    else if ((paramString.equals("CUSTID")) && (getCustId() != null) && (localUser.getCustId() != null))
    {
      i = localCollator.compare(getCustId(), localUser.getCustId());
    }
    else if ((paramString.equals("USERNAME")) && (getUserName() != null) && (localUser.getUserName() != null))
    {
      i = localCollator.compare(getUserName(), localUser.getUserName());
    }
    else if ((paramString.equals("SSN")) && (getSSN() != null) && (localUser.getSSN() != null))
    {
      i = localCollator.compare(getSSN(), localUser.getSSN());
    }
    else if ((paramString.equals("SORTABLE_FULL_NAME")) && (getLastName() != null) && (localUser.getLastName() != null))
    {
      i = localCollator.compare(getLastName(), localUser.getLastName());
      if ((i == 0) && (getFirstName() != null) && (localUser.getFirstName() != null)) {
        i = localCollator.compare(getFirstName(), localUser.getFirstName());
      }
    }
    else if ((paramString.equals("SORTABLE_FULL_NAME_WITH_LOGIN_ID")) && (getLastName() != null) && (localUser.getLastName() != null))
    {
      i = localCollator.compare(getLastName(), localUser.getLastName());
      if ((i == 0) && (getFirstName() != null) && (localUser.getFirstName() != null)) {
        i = localCollator.compare(getFirstName(), localUser.getFirstName());
      }
      if ((i == 0) && (getUserName() != null) && (localUser.getUserName() != null)) {
        i = localCollator.compare(getUserName(), localUser.getUserName());
      }
      if ((i == 0) && (getCustId() != null) && (localUser.getCustId() != null)) {
        i = localCollator.compare(getCustId(), localUser.getCustId());
      }
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getId() != null)) {
      return isFilterable(getId(), paramString2, paramString3);
    }
    if ((paramString1.equals("CUSTID")) && (getCustId() != null)) {
      return isFilterable(getCustId(), paramString2, paramString3);
    }
    if ((paramString1.equals("USERNAME")) && (getUserName() != null)) {
      return isFilterable(getUserName(), paramString2, paramString3);
    }
    if ((paramString1.equals("SSN")) && (getSSN() != null)) {
      return isFilterable(getSSN(), paramString2, paramString3);
    }
    if ((paramString1.equals("PERSONALBANKER")) && (getPersonalBanker() != null)) {
      return isFilterable(getPersonalBanker(), paramString2, paramString3);
    }
    if ("ACCOUNTSTATUS".equals(paramString1)) {
      return isFilterable(getAccountStatus(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(User paramUser)
  {
    synchronized (this)
    {
      setId(paramUser.getId());
      setCustId(paramUser.getCustId());
      setUserName(paramUser.getUserName());
      setGender(paramUser.getGender());
      setPersonalBanker(paramUser.getPersonalBanker());
      setGreetingType(paramUser.getGreetingType());
      setGreeting(paramUser.getGreeting());
      setTitle(paramUser.getTitle());
      setAccessMode(paramUser.getAccessMode());
      setAccountStatus(paramUser.getAccountStatus());
      setServiceLevel(paramUser.getServiceLevel());
      setTimeout(paramUser.getTimeout());
      setSSN(paramUser.getSSN());
      this.password = paramUser.getPassword();
      setPasswordReminder(paramUser.getPasswordReminder());
      setPasswordReminder2(paramUser.getPasswordReminder2());
      setPasswordClue(paramUser.getPasswordClue());
      setPasswordClue2(paramUser.getPasswordClue2());
      setEntitlementGroupId(paramUser.getEntitlementGroupId());
      setServicesPackageId(paramUser.getServicesPackageIdValue());
      copyTags(paramUser.getCustomTags());
      setAffiliateBankID(paramUser.getAffiliateBankID());
      setCustomerType(paramUser.getCustomerType());
      setTermsAccepted(paramUser.getTermsAccepted());
      setTermsAcceptedDate(paramUser.getTermsAcceptedDate());
      setPrimarySecondary(paramUser.getPrimarySecondary());
      super.set(paramUser);
    }
  }
  
  public void copyTags(CustomTags paramCustomTags)
  {
    if (paramCustomTags != null)
    {
      this.customTags = new CustomTags(this.locale);
      for (int i = 0; i < paramCustomTags.size(); i++)
      {
        CustomTag localCustomTag1 = (CustomTag)paramCustomTags.get(i);
        CustomTag localCustomTag2 = this.customTags.add();
        localCustomTag2.set(localCustomTag1);
      }
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this.id = paramString2;
    } else if (paramString1.equals("CUSTID")) {
      this.custId = paramString2;
    } else if (paramString1.equals("PASSWORD")) {
      this.password = paramString2;
    } else if (paramString1.equals("PASSWORDREMINDER")) {
      this.passwordReminder = paramString2;
    } else if (paramString1.equals("PASSWORDREMINDER2")) {
      this.passwordReminder2 = paramString2;
    } else if (paramString1.equals("USERNAME")) {
      this.userName = paramString2;
    } else if (paramString1.equals("SSN")) {
      setSSN(paramString2);
    } else if (paramString1.equals("TITLE")) {
      this.title = paramString2;
    } else if (paramString1.equals("GREETING")) {
      this.greeting = paramString2;
    } else if (paramString1.equals("ACCESSMODE")) {
      this.accessMode = paramString2;
    } else if (paramString1.equals("ACCOUNTSTATUS")) {
      this.accountStatus = paramString2;
    } else if (paramString1.equals("SERVICELEVEL")) {
      setServiceLevel(paramString2);
    } else if (paramString1.equals("GREETINGTYPE")) {
      this.greetingType = paramString2;
    } else if (paramString1.equals("TIMEOUT")) {
      this.timeout = paramString2;
    } else if (paramString1.equals("GENDER")) {
      this.gender = paramString2;
    } else if (paramString1.equals("PERSONALBANKER")) {
      this.personalBanker = paramString2;
    } else if (paramString1.equals("PASSWORDCLUE")) {
      this.passwordClue = paramString2;
    } else if (paramString1.equals("PASSWORDCLUE2")) {
      this.passwordClue2 = paramString2;
    } else if (paramString1.equals("AFFILIATE_BANK_ID")) {
      this.affiliateBankID = Integer.parseInt(paramString2);
    } else if (paramString1.equals("CUSTOMER_TYPE")) {
      this.customerType = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "USERINFO");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "CUSTID", this.custId);
    XMLHandler.appendTag(localStringBuffer, "USERNAME", this.userName);
    XMLHandler.appendTag(localStringBuffer, "PASSWORD", this.password);
    XMLHandler.appendTag(localStringBuffer, "PASSWORDREMINDER", this.passwordReminder);
    XMLHandler.appendTag(localStringBuffer, "PASSWORDCLUE", this.passwordClue);
    XMLHandler.appendTag(localStringBuffer, "PASSWORDREMINDER2", this.passwordReminder2);
    XMLHandler.appendTag(localStringBuffer, "PASSWORDCLUE2", this.passwordClue2);
    XMLHandler.appendTag(localStringBuffer, "PERSONALBANKER", this.personalBanker);
    XMLHandler.appendTag(localStringBuffer, "TITLE", this.title);
    XMLHandler.appendTag(localStringBuffer, "GREETING", this.greeting);
    if (this.ssn != null) {
      XMLHandler.appendTag(localStringBuffer, "SSN", this.ssn.toString());
    } else {
      XMLHandler.appendTag(localStringBuffer, "SSN", "");
    }
    XMLHandler.appendTag(localStringBuffer, "GENDER", this.gender);
    XMLHandler.appendTag(localStringBuffer, "ACCESSMODE", this.accessMode);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTSTATUS", this.accountStatus);
    XMLHandler.appendTag(localStringBuffer, "SERVICELEVEL", getServiceLevel());
    XMLHandler.appendTag(localStringBuffer, "TIMEOUT", this.timeout);
    XMLHandler.appendTag(localStringBuffer, "GREETINGTYPE", this.greetingType);
    XMLHandler.appendTag(localStringBuffer, "AFFILIATE_BANK_ID", String.valueOf(this.affiliateBankID));
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_TYPE", String.valueOf(this.customerType));
    if (this.email != null) {
      XMLHandler.appendTag(localStringBuffer, "EMAIL", this.email.toString());
    } else {
      XMLHandler.appendTag(localStringBuffer, "EMAIL", "");
    }
    if (this.customTags != null) {
      localStringBuffer.append(this.customTags.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "EntitlementGroupId", this.entitlementGroupId);
    XMLHandler.appendTag(localStringBuffer, "ServicePackageId", this.servicesPackageId);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "USERINFO");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, User paramUser, String paramString)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str1 = "";
    String str2 = "";
    Integer localInteger1 = null;
    Integer localInteger2 = null;
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramUser.id, this.id, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERNAME", paramUser.userName, this.userName, paramString);
    if ((paramUser.password != this.password) && ((this.password == null) || (!this.password.equals(paramUser.password)))) {
      paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, "PASSWORD"), (String)null, (String)null, paramString);
    }
    if ((paramUser.passwordReminder != this.passwordReminder) && ((this.passwordReminder == null) || (!this.passwordReminder.equals(paramUser.passwordReminder)))) {
      paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, "PASSWORDREMINDER"), (String)null, (String)null, paramString);
    }
    if ((paramUser.passwordReminder2 != this.passwordReminder2) && ((this.passwordReminder2 == null) || (!this.passwordReminder2.equals(paramUser.passwordReminder2)))) {
      paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, "PASSWORDREMINDER2"), (String)null, (String)null, paramString);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "PASSWORDCLUE", paramUser.passwordClue, this.passwordClue, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PASSWORDCLUE2", paramUser.passwordClue2, this.passwordClue2, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTSTATUS", ResourceUtil.getString("ACCOUNTSTATUS." + paramUser.accountStatus, "com.ffusion.beans.user.resources", this.locale), ResourceUtil.getString("ACCOUNTSTATUS." + this.accountStatus, "com.ffusion.beans.user.resources", this.locale), paramString);
    if ((paramUser.entitlementGroupId > 0) && (this.entitlementGroupId > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, "EntitlementGroupId", Entitlements.getEntitlementGroup(paramUser.entitlementGroupId).getGroupName(), Entitlements.getEntitlementGroup(this.entitlementGroupId).getGroupName(), paramString);
      }
      catch (Exception localException1)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, "EntitlementGroupId", paramUser.entitlementGroupId, this.entitlementGroupId, ResourceUtil.getString("EntitleGroupLookupFail", "com.ffusion.beans.business.resources", this.locale));
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, "EntitlementGroupId", paramUser.entitlementGroupId, this.entitlementGroupId, ResourceUtil.getString("EntitleGroupLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTID", paramUser.custId, this.custId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TITLE", paramUser.title, this.title, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "GREETING", paramUser.greeting, this.greeting, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "GREETINGTYPE", ResourceUtil.getString("PREFERRED_GREETING." + paramUser.greetingType, "com.ffusion.beans.user.resources", this.locale), ResourceUtil.getString("PREFERRED_GREETING." + this.greetingType, "com.ffusion.beans.user.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "GENDER", ResourceUtil.getString("GENDER." + paramUser.gender, "com.ffusion.beans.user.resources", this.locale), ResourceUtil.getString("GENDER." + this.gender, "com.ffusion.beans.user.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SERVICELEVEL", ResourceUtil.getString("SERVICELEVEL." + paramUser.serviceLevel, "com.ffusion.beans.user.resources", this.locale), ResourceUtil.getString("SERVICELEVEL." + this.serviceLevel, "com.ffusion.beans.user.resources", this.locale), paramString);
    try
    {
      if ((paramUser.personalBanker == null) || (paramUser.personalBanker.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramUser.personalBanker);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.personalBanker == null) || (this.personalBanker.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.personalBanker);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "PERSONALBANKER", str1, str2, paramString);
    }
    catch (Exception localException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "PERSONALBANKER", paramUser.personalBanker, this.personalBanker, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "SSN", paramUser.ssn, this.ssn, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCESSMODE", paramUser.accessMode, this.accessMode, paramString);
    localInteger1 = new Integer(paramUser.timeout);
    localInteger2 = new Integer(this.timeout);
    paramHistoryTracker.detectChange(BEAN_NAME, "TIMEOUT", localInteger1.intValue() / 60 + "minutes", localInteger2.intValue() / 60 + "minutes", paramString);
    try
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "AFFILIATE_BANK_ID", AffiliateBankAdapter.getAffiliateBankByID(null, paramUser.affiliateBankID).getAffiliateBankName(), AffiliateBankAdapter.getAffiliateBankByID(null, this.affiliateBankID).getAffiliateBankName(), paramString);
    }
    catch (Exception localException3)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "AFFILIATE_BANK_ID", paramUser.affiliateBankID, this.affiliateBankID, ResourceUtil.getString("BankNameLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTOMER_TYPE", paramUser.customerType, this.customerType, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENT_SERVICE", paramUser.currentService, this.currentService, paramString);
    if ((paramUser.servicesPackageId > 0) && (this.servicesPackageId > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, Entitlements.getEntitlementGroup(paramUser.servicesPackageId).getGroupName(), Entitlements.getEntitlementGroup(this.servicesPackageId).getGroupName(), paramString);
      }
      catch (Exception localException4)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramUser.servicesPackageId, this.servicesPackageId, ResourceUtil.getString("ServicesLookupFail", "com.ffusion.beans.business.resources", this.locale));
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramUser.servicesPackageId, this.servicesPackageId, ResourceUtil.getString("ServicesLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKING_ID", paramUser.trackingID, this.trackingID, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PASSWORD_STATUS", paramUser.getPasswordStatusStr(), getPasswordStatusStr(), paramString);
    super.logChanges(paramHistoryTracker, paramUser, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "USERNAME", this.userName, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "ID", this.id, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "USERNAME", this.userName, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDCLUE", this.passwordClue, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDREMINDER", this.passwordReminder, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDCLUE2", this.passwordClue2, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDREMINDER2", this.passwordReminder2, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ACCOUNTSTATUS", this.accountStatus, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "EntitlementGroupId", this.entitlementGroupId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CUSTID", this.custId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "TITLE", this.title, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "GREETING", this.greeting, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "GREETINGTYPE", this.greetingType, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "GENDER", this.gender, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "SERVICELEVEL", this.serviceLevel, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PERSONALBANKER", this.personalBanker, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "SSN", this.ssn, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ACCESSMODE", this.accessMode, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "TIMEOUT", this.timeout, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AFFILIATE_BANK_ID", this.affiliateBankID, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CUSTOMER_TYPE", this.customerType, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CURRENT_SERVICE", this.currentService, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, SERVICESPACKAGEID, this.servicesPackageId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "TRACKING_ID", this.trackingID, paramString);
    super.logDeletion(paramHistoryTracker, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, User paramUser, ILocalizable paramILocalizable)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str1 = "";
    String str2 = "";
    Integer localInteger1 = null;
    Integer localInteger2 = null;
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramUser.id, this.id, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERNAME", paramUser.userName, this.userName, paramILocalizable);
    if ((paramUser.password != this.password) && ((this.password == null) || (!this.password.equals(paramUser.password)))) {
      paramHistoryTracker.logChange(BEAN_NAME, "PASSWORD", (String)null, (String)null, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "PASSWORDCLUE", paramUser.passwordClue, this.passwordClue, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PASSWORDCLUE2", paramUser.passwordClue2, this.passwordClue2, paramILocalizable);
    if ((paramUser.passwordReminder != this.passwordReminder) && ((this.passwordReminder == null) || (!this.passwordReminder.equals(paramUser.passwordReminder)))) {
      paramHistoryTracker.logChange(BEAN_NAME, "PASSWORDREMINDER", (String)null, (String)null, paramILocalizable);
    }
    if ((paramUser.passwordReminder2 != this.passwordReminder2) && ((this.passwordReminder2 == null) || (!this.passwordReminder2.equals(paramUser.passwordReminder2)))) {
      paramHistoryTracker.logChange(BEAN_NAME, "PASSWORDREMINDER2", (String)null, (String)null, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTSTATUS", paramUser.accountStatus == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "ACCOUNTSTATUS." + paramUser.accountStatus, null), this.accountStatus == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "ACCOUNTSTATUS." + this.accountStatus, null), paramILocalizable);
    if ((paramUser.entitlementGroupId > 0) && (this.entitlementGroupId > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, "EntitlementGroupId", Entitlements.getEntitlementGroup(paramUser.entitlementGroupId).getGroupName(), Entitlements.getEntitlementGroup(this.entitlementGroupId).getGroupName(), paramILocalizable);
      }
      catch (Exception localException1)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, "EntitlementGroupId", paramUser.entitlementGroupId, this.entitlementGroupId, paramILocalizable);
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, "EntitlementGroupId", paramUser.entitlementGroupId, this.entitlementGroupId, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTID", paramUser.custId, this.custId, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TITLE", paramUser.title, this.title, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "GREETING", paramUser.greeting, this.greeting, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "GREETINGTYPE", paramUser.greetingType == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_GREETING." + paramUser.greetingType, null), this.greetingType == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_GREETING." + this.greetingType, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "GENDER", paramUser.gender == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "GENDER." + paramUser.gender, null), this.gender == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "GENDER." + this.gender, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "SERVICELEVEL", paramUser.serviceLevel == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "SERVICELEVEL." + paramUser.serviceLevel, null), this.serviceLevel == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "SERVICELEVEL." + this.serviceLevel, null), paramILocalizable);
    try
    {
      if ((paramUser.personalBanker == null) || (paramUser.personalBanker.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramUser.personalBanker);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.personalBanker == null) || (this.personalBanker.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.personalBanker);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "PERSONALBANKER", str1, str2, paramILocalizable);
    }
    catch (Exception localException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "PERSONALBANKER", paramUser.personalBanker, this.personalBanker, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "SSN", paramUser.ssn == null ? null : paramUser.ssn.toString(), this.ssn == null ? null : this.ssn.toString(), paramILocalizable);
    localInteger1 = new Integer(paramUser.timeout);
    localInteger2 = new Integer(this.timeout);
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = new Integer(localInteger1.intValue() / 60);
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.beans.user.resources", "TIMEOUT", arrayOfObject1);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = new Integer(localInteger2.intValue() / 60);
    LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.user.resources", "TIMEOUT", arrayOfObject2);
    paramHistoryTracker.detectChange(BEAN_NAME, "TIMEOUT", localLocalizableString1, localLocalizableString2, paramILocalizable);
    try
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "AFFILIATE_BANK_ID", AffiliateBankAdapter.getAffiliateBankByID(null, paramUser.affiliateBankID).getAffiliateBankName(), AffiliateBankAdapter.getAffiliateBankByID(null, this.affiliateBankID).getAffiliateBankName(), paramILocalizable);
    }
    catch (Exception localException3)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "AFFILIATE_BANK_ID", paramUser.affiliateBankID, this.affiliateBankID, paramILocalizable);
    }
    if ((paramUser.servicesPackageId > 0) && (this.servicesPackageId > 0)) {
      try
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, Entitlements.getEntitlementGroup(paramUser.servicesPackageId).getGroupName(), Entitlements.getEntitlementGroup(this.servicesPackageId).getGroupName(), paramILocalizable);
      }
      catch (Exception localException4)
      {
        paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramUser.servicesPackageId, this.servicesPackageId, paramILocalizable);
      }
    } else {
      paramHistoryTracker.detectChange(BEAN_NAME, SERVICESPACKAGEID, paramUser.servicesPackageId, this.servicesPackageId, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKING_ID", paramUser.trackingID, this.trackingID, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PASSWORD_STATUS", new LocalizableString("com.ffusion.beans.user.resources", "PASSWORD_STATUS." + paramUser.getPasswordStatusIntValue(), null), new LocalizableString("com.ffusion.beans.user.resources", "PASSWORD_STATUS." + getPasswordStatusIntValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TERMS_VERSION", paramUser.getTermsAccepted(), getTermsAccepted(), paramILocalizable);
    DateTime localDateTime1 = paramUser.getTermsAcceptedDate();
    DateTime localDateTime2 = getTermsAcceptedDate();
    String str3 = "";
    String str4 = "";
    if (localDateTime1 != null) {
      str3 = localDateTime1.toString();
    }
    if (localDateTime2 != null) {
      str4 = localDateTime2.toString();
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "TERMS_VERSION", str3, str4, paramILocalizable);
    super.logChanges(paramHistoryTracker, paramUser, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "USERNAME", this.userName, paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str = "";
    Integer localInteger = null;
    paramHistoryTracker.logDelete(BEAN_NAME, "ID", this.id, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "USERNAME", this.userName, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDCLUE", this.passwordClue, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDREMINDER", this.passwordReminder, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDCLUE2", this.passwordClue2, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PASSWORDREMINDER2", this.passwordReminder2, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "ACCOUNTSTATUS", this.accountStatus == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "ACCOUNTSTATUS." + this.accountStatus, null), paramILocalizable);
    if (this.entitlementGroupId > 0) {
      try
      {
        paramHistoryTracker.logDelete(BEAN_NAME, "EntitlementGroupId", Entitlements.getEntitlementGroup(this.entitlementGroupId).getGroupName(), paramILocalizable);
      }
      catch (Exception localException1)
      {
        paramHistoryTracker.logDelete(BEAN_NAME, "EntitlementGroupId", this.entitlementGroupId, paramILocalizable);
      }
    } else {
      paramHistoryTracker.logDelete(BEAN_NAME, "EntitlementGroupId", this.entitlementGroupId, paramILocalizable);
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "CUSTID", this.custId, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "TITLE", this.title, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "GREETING", this.greeting, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "GREETINGTYPE", this.greetingType == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "PREFERRED_GREETING." + this.greetingType, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "GENDER", this.gender == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "GENDER." + this.gender, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "SERVICELEVEL", this.serviceLevel == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "SERVICELEVEL." + this.serviceLevel, null), paramILocalizable);
    try
    {
      if ((this.personalBanker == null) || (this.personalBanker.equals("0")))
      {
        str = "";
      }
      else
      {
        localBankEmployee.setId(this.personalBanker);
        str = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.logDelete(BEAN_NAME, "PERSONALBANKER", str, paramILocalizable);
    }
    catch (Exception localException2)
    {
      paramHistoryTracker.logDelete(BEAN_NAME, "PERSONALBANKER", this.personalBanker, paramILocalizable);
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "SSN", this.ssn == null ? null : this.ssn.toString(), paramILocalizable);
    localInteger = new Integer(this.timeout);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new Integer(localInteger.intValue() / 60);
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.user.resources", "TIMEOUT", arrayOfObject);
    paramHistoryTracker.logDelete(BEAN_NAME, "TIMEOUT", localLocalizableString, paramILocalizable);
    try
    {
      paramHistoryTracker.logDelete(BEAN_NAME, "AFFILIATE_BANK_ID", AffiliateBankAdapter.getAffiliateBankByID(null, this.affiliateBankID).getAffiliateBankName(), paramILocalizable);
    }
    catch (Exception localException3)
    {
      paramHistoryTracker.logDelete(BEAN_NAME, "AFFILIATE_BANK_ID", this.affiliateBankID, paramILocalizable);
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "CUSTOMER_TYPE", this.customerType == null ? null : new LocalizableString("com.ffusion.beans.user.resources", "CUSTOMER_TYPE." + this.customerType, null), paramILocalizable);
    if (this.servicesPackageId > 0) {
      try
      {
        paramHistoryTracker.logDelete(BEAN_NAME, SERVICESPACKAGEID, Entitlements.getEntitlementGroup(this.servicesPackageId).getGroupName(), paramILocalizable);
      }
      catch (Exception localException4)
      {
        paramHistoryTracker.logDelete(BEAN_NAME, SERVICESPACKAGEID, this.servicesPackageId, paramILocalizable);
      }
    } else {
      paramHistoryTracker.logDelete(BEAN_NAME, SERVICESPACKAGEID, this.servicesPackageId, paramILocalizable);
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "TRACKING_ID", this.trackingID, paramILocalizable);
    super.logDeletion(paramHistoryTracker, paramILocalizable);
  }
  
  public void setTermsAccepted(int paramInt)
  {
    this.termsAccepted = paramInt;
  }
  
  public void setTermsAccepted(String paramString)
  {
    this.termsAccepted = Integer.valueOf(paramString).intValue();
  }
  
  public void setTermsAcceptedDate(DateTime paramDateTime)
  {
    this.termsAcceptedDate = paramDateTime;
  }
  
  public void setTermsAcceptedDate(String paramString)
  {
    try
    {
      if (this.termsAcceptedDate == null)
      {
        this.termsAcceptedDate = new DateTime(paramString, this.locale, getDateFormat());
      }
      else
      {
        this.termsAcceptedDate.setFormat(getDateFormat());
        this.termsAcceptedDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public int getTermsAccepted()
  {
    return this.termsAccepted;
  }
  
  public DateTime getTermsAcceptedDate()
  {
    if (this.termsAcceptedDate != null) {
      this.termsAcceptedDate.setFormat(getDateFormat());
    }
    return this.termsAcceptedDate;
  }
  
  public class a
    extends ExtendABean.InternalXMLHandler
  {
    public a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("TAG_GROUP"))
      {
        User.this.customTags = new CustomTags(User.this.locale);
        User.this.customTags.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.User
 * JD-Core Version:    0.7.0.1
 */