package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import java.io.Serializable;
import java.text.Collator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class AffiliateBank
  extends AffiliateBankI18N
  implements Comparable, Serializable, CollectionElement
{
  public static final String BANK_ID = "BANK_ID";
  public static final String BANK_NAME = "BANK_NAME";
  public static final String ROUTING_NUM = "ROUTING_NUM";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String ENTITLEMENT_GROUP_ID = "ENTITLEMENT_GROUP_ID";
  public static final String SWIFT_BIC = "SWIFT_BIC";
  public static final String NATIONAL_ID = "NATIONAL_ID";
  public static final String CHIPS_ID = "CHIPS_ID";
  public static final String AFFILIATE_BANK_ID = "AFFILIATE_BANK_ID";
  public static final String FI_BPW_ID = "AFFILIATE_FI_BPW_ID";
  public static final String CURRENCY_CODE = "CURRENCY_CODE";
  public static final String CORPORATE_URL = "CORPORATE_URL";
  public static final String CONSUMER_URL = "CONSUMER_URL";
  public static final String EMAIL_SUBJECT = "EMAIL_SUBJECT";
  public static final String EMAIL_MEMO = "EMAIL_MEMO";
  public static final String EMAIL_FROM = "EMAIL_FROM";
  public static final String BANK_TYPE = "BANK_TYPE";
  public static final String WAREHOUSING_TYPE = "WAREHOUSING_TYPE";
  public static final String ACH_MAX_FUTURE_DAYS = "ACH_MAX_FUTURE_DAYS";
  public static final String ACH_SAMEDAY_EFF_DATING = "ACH_SAMEDAY_EFF_DATING";
  public static final String BEAN_NAME = AffiliateBank.class.getName();
  public static String AFFILIATE_BANK_CALCULATIONS = "CALCULATIONS";
  public static final int TYPE_INVALID = -1;
  public static final int TYPE_AFFILIATE = 1;
  public static final int TYPE_HOLDING = 2;
  public static final int TYPE_EXTERNAL = 3;
  public static final int ACH_NUMBER_FUTURE_DAYS_LOWER_BOUND = 1;
  public static final int ACH_NUMBER_FUTURE_DAYS_UPPER_BOUND = 90;
  public static final String DEFAULT_LANGUAGE = "en_US";
  private String qe;
  private String p3;
  private int p8;
  private String pT;
  private String pP;
  private int p7;
  private String pJ;
  private String pW;
  private String pQ;
  private String p6;
  private String qc;
  private String pL;
  private String pZ;
  private String p2;
  private String pR;
  private String p1;
  private String pU;
  private String pK;
  private int qa = 1;
  private int pO;
  private boolean pV;
  private String pM;
  private int qd;
  private int pN = 99;
  private int pS = 1;
  private String qf;
  private ExtTransferCompany p4;
  private String p0;
  private String p5;
  private String qb;
  protected int _termsVersion = 1;
  protected DateTime _termsResetDate;
  public static final String KEY_ACCOUNT_TYPE = "AccountType";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.accounts.resources";
  private String pY = "en_US";
  private String pX = "en_US";
  private HashMap p9;
  
  public AffiliateBank()
  {
    setLanguage("en_US");
  }
  
  public AffiliateBank(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public void setBankID(String paramString)
  {
    this.qe = paramString;
  }
  
  public String getBankID()
  {
    return this.qe;
  }
  
  public void setBrandID(String paramString)
  {
    this.pK = paramString;
  }
  
  public String getBrandID()
  {
    return this.pK;
  }
  
  public void setAffiliateBankName(String paramString)
  {
    this.p3 = paramString;
  }
  
  public String getAffiliateBankName()
  {
    return this.p3;
  }
  
  public void setBankType(String paramString)
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramString);
      setBankTypeValue(i);
    }
    catch (Exception localException) {}
  }
  
  public void setBankTypeValue(int paramInt)
  {
    switch (paramInt)
    {
    case 1: 
    case 2: 
    case 3: 
      this.p8 = paramInt;
    }
  }
  
  public String getBankType()
  {
    return Integer.toString(this.p8);
  }
  
  public int getBankTypeValue()
  {
    return this.p8;
  }
  
  public void setAffiliateRoutingNum(String paramString)
  {
    this.pT = paramString;
  }
  
  public String getAffiliateRoutingNum()
  {
    return this.pT;
  }
  
  public void setDescription(String paramString)
  {
    this.pP = paramString;
  }
  
  public String getDescription()
  {
    return this.pP;
  }
  
  public void setEntitlementGroupID(int paramInt)
  {
    this.p7 = paramInt;
  }
  
  public int getEntitlementGroupID()
  {
    return this.p7;
  }
  
  public void setSwiftBIC(String paramString)
  {
    this.pJ = paramString;
  }
  
  public String getSwiftBIC()
  {
    return this.pJ;
  }
  
  public void setNationalID(String paramString)
  {
    this.pW = paramString;
  }
  
  public String getNationalID()
  {
    return this.pW;
  }
  
  public void setChipsID(String paramString)
  {
    this.pQ = paramString;
  }
  
  public String getChipsID()
  {
    return this.pQ;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.pR = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.pR;
  }
  
  public void setFIBPWID(String paramString)
  {
    this.p6 = paramString;
  }
  
  public String getFIBPWID()
  {
    return this.p6;
  }
  
  public void setACHImmediateOrigin(String paramString)
  {
    this.qc = paramString;
  }
  
  public String getACHImmediateOrigin()
  {
    return this.qc;
  }
  
  public void setACHImmediateOriginName(String paramString)
  {
    this.pL = paramString;
  }
  
  public String getACHImmediateOriginName()
  {
    return this.pL;
  }
  
  public void setACHDestination(String paramString)
  {
    this.pZ = paramString;
  }
  
  public String getACHDestination()
  {
    return this.pZ;
  }
  
  public void setACHDestinationName(String paramString)
  {
    this.p2 = paramString;
  }
  
  public String getACHDestinationName()
  {
    return this.p2;
  }
  
  public String getId()
  {
    return Integer.toString(getAffiliateBankID());
  }
  
  public String getCorporateURL()
  {
    return this.p1;
  }
  
  public void setCorporateURL(String paramString)
  {
    this.p1 = paramString;
  }
  
  public String getConsumerURL()
  {
    return this.pU;
  }
  
  public void setConsumerURL(String paramString)
  {
    this.pU = paramString;
  }
  
  public void setWarehousingType(int paramInt)
  {
    this.qa = paramInt;
  }
  
  public int getWarehousingType()
  {
    return this.qa;
  }
  
  public void setAchMaxFutureDays(int paramInt)
  {
    this.pO = paramInt;
  }
  
  public int getAchMaxFutureDays()
  {
    return this.pO;
  }
  
  public void setAchSameDayEffDating(boolean paramBoolean)
  {
    this.pV = paramBoolean;
  }
  
  public boolean getAchSameDayEffDating()
  {
    return this.pV;
  }
  
  public void setEmailSubject(String paramString)
  {
    AffiliateBankI18N localAffiliateBankI18N = h();
    if (localAffiliateBankI18N == null) {
      localAffiliateBankI18N = jdMethod_null(this.pX);
    }
    if (localAffiliateBankI18N == this) {
      super.setEmailSubject(paramString);
    } else if (localAffiliateBankI18N != null) {
      localAffiliateBankI18N.setEmailSubject(paramString);
    }
  }
  
  public void setEmailSubject(String paramString1, String paramString2)
  {
    AffiliateBankI18N localAffiliateBankI18N = jdMethod_null(paramString1);
    if (localAffiliateBankI18N == this) {
      super.setEmailSubject(paramString2);
    } else if (localAffiliateBankI18N != null) {
      localAffiliateBankI18N.setEmailSubject(paramString2);
    }
  }
  
  public String getEmailSubject()
  {
    AffiliateBankI18N localAffiliateBankI18N = h();
    if (localAffiliateBankI18N == null) {
      return null;
    }
    if (localAffiliateBankI18N == this) {
      return super.getEmailSubject();
    }
    return localAffiliateBankI18N.getEmailSubject();
  }
  
  public String getEmailSubject(String paramString)
  {
    AffiliateBankI18N localAffiliateBankI18N = jdMethod_void(paramString);
    if (localAffiliateBankI18N == null) {
      return null;
    }
    if (localAffiliateBankI18N == this) {
      return super.getEmailSubject();
    }
    return localAffiliateBankI18N.getEmailSubject();
  }
  
  public void setEmailMemo(String paramString)
  {
    AffiliateBankI18N localAffiliateBankI18N = h();
    if (localAffiliateBankI18N == null) {
      localAffiliateBankI18N = jdMethod_null(this.pX);
    }
    if (localAffiliateBankI18N == this) {
      super.setEmailMemo(paramString);
    } else if (localAffiliateBankI18N != null) {
      localAffiliateBankI18N.setEmailMemo(paramString);
    }
  }
  
  public void setEmailMemo(String paramString1, String paramString2)
  {
    AffiliateBankI18N localAffiliateBankI18N = jdMethod_null(paramString1);
    if (localAffiliateBankI18N == this) {
      super.setEmailMemo(paramString2);
    } else if (localAffiliateBankI18N != null) {
      localAffiliateBankI18N.setEmailMemo(paramString2);
    }
  }
  
  public String getEmailMemo()
  {
    AffiliateBankI18N localAffiliateBankI18N = h();
    if (localAffiliateBankI18N == null) {
      return null;
    }
    if (localAffiliateBankI18N == this) {
      return super.getEmailMemo();
    }
    return localAffiliateBankI18N.getEmailMemo();
  }
  
  public String getEmailMemo(String paramString)
  {
    AffiliateBankI18N localAffiliateBankI18N = jdMethod_void(paramString);
    if (localAffiliateBankI18N == null) {
      return null;
    }
    if (localAffiliateBankI18N == this) {
      return super.getEmailMemo();
    }
    return localAffiliateBankI18N.getEmailMemo();
  }
  
  public void setEmailFrom(String paramString)
  {
    AffiliateBankI18N localAffiliateBankI18N = h();
    if (localAffiliateBankI18N == null) {
      localAffiliateBankI18N = jdMethod_null(this.pX);
    }
    if (localAffiliateBankI18N == this) {
      super.setEmailFrom(paramString);
    } else if (localAffiliateBankI18N != null) {
      localAffiliateBankI18N.setEmailFrom(paramString);
    }
  }
  
  public void setEmailFrom(String paramString1, String paramString2)
  {
    AffiliateBankI18N localAffiliateBankI18N = jdMethod_null(paramString1);
    if (localAffiliateBankI18N == this) {
      super.setEmailFrom(paramString2);
    } else if (localAffiliateBankI18N != null) {
      localAffiliateBankI18N.setEmailFrom(paramString2);
    }
  }
  
  public String getEmailFrom()
  {
    AffiliateBankI18N localAffiliateBankI18N = h();
    if (localAffiliateBankI18N == null) {
      return null;
    }
    if (localAffiliateBankI18N == this) {
      return super.getEmailFrom();
    }
    return localAffiliateBankI18N.getEmailFrom();
  }
  
  public String getEmailFrom(String paramString)
  {
    AffiliateBankI18N localAffiliateBankI18N = jdMethod_void(paramString);
    if (localAffiliateBankI18N == null) {
      return null;
    }
    if (localAffiliateBankI18N == this) {
      return super.getEmailFrom();
    }
    return localAffiliateBankI18N.getEmailFrom();
  }
  
  public String getEtfAccountNum()
  {
    return this.pM;
  }
  
  public void setEtfAccountNum(String paramString)
  {
    this.pM = paramString;
  }
  
  public void setEtfAccountType(String paramString)
  {
    try
    {
      this.qd = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      if ("Checking".equals(paramString)) {
        this.qd = 1;
      } else if ("Savings".equals(paramString)) {
        this.qd = 2;
      }
    }
  }
  
  public void setEtfAccountType(int paramInt)
  {
    this.qd = paramInt;
  }
  
  public int getEtfAccountTypeValue()
  {
    return this.qd;
  }
  
  public String getEtfAccountTypeValueAsString()
  {
    return String.valueOf(this.qd);
  }
  
  public String getEtfAccountType()
  {
    return ResourceUtil.getString("AccountType" + this.qd, "com.ffusion.beans.accounts.resources", this.locale);
  }
  
  public int getEtfMinimumDepositAmountValue()
  {
    return this.pS;
  }
  
  public void setEtfMinimumDepositAmountValue(int paramInt)
  {
    this.pS = paramInt;
  }
  
  public int getEtfMaximumDepositAmountValue()
  {
    return this.pN;
  }
  
  public void setEtfMaximumDepositAmountValue(int paramInt)
  {
    this.pN = paramInt;
  }
  
  public String getEtfVirtualUserID()
  {
    return this.qf;
  }
  
  public void setEtfVirtualUserID(String paramString)
  {
    this.qf = paramString;
  }
  
  public ExtTransferCompany getEtfCompany()
  {
    return this.p4;
  }
  
  public void setEtfCompany(ExtTransferCompany paramExtTransferCompany)
  {
    this.p4 = paramExtTransferCompany;
  }
  
  public String getEtfCompanyName()
  {
    return this.p0;
  }
  
  public void setEtfCompanyName(String paramString)
  {
    this.p0 = paramString;
  }
  
  public String getEtfCompanyID()
  {
    return this.p5;
  }
  
  public void setEtfCompanyID(String paramString)
  {
    this.p5 = paramString;
  }
  
  public String getEtfCompanyBatchDescription()
  {
    return this.qb;
  }
  
  public void setEtfCompanyBatchDescription(String paramString)
  {
    this.qb = paramString;
  }
  
  public void set(AffiliateBank paramAffiliateBank)
  {
    setBankID(paramAffiliateBank.getBankID());
    setAffiliateBankName(paramAffiliateBank.getAffiliateBankName());
    setBankTypeValue(paramAffiliateBank.getBankTypeValue());
    setAffiliateRoutingNum(paramAffiliateBank.getAffiliateRoutingNum());
    setDescription(paramAffiliateBank.getDescription());
    setEntitlementGroupID(paramAffiliateBank.getEntitlementGroupID());
    setSwiftBIC(paramAffiliateBank.getSwiftBIC());
    setNationalID(paramAffiliateBank.getNationalID());
    setChipsID(paramAffiliateBank.getChipsID());
    setAffiliateBankID(paramAffiliateBank.getAffiliateBankID());
    setFIBPWID(paramAffiliateBank.getFIBPWID());
    setACHImmediateOrigin(paramAffiliateBank.getACHImmediateOrigin());
    setACHImmediateOriginName(paramAffiliateBank.getACHImmediateOriginName());
    setACHDestination(paramAffiliateBank.getACHDestination());
    setACHDestinationName(paramAffiliateBank.getACHDestinationName());
    setCurrencyCode(paramAffiliateBank.getCurrencyCode());
    setCorporateURL(paramAffiliateBank.getCorporateURL());
    setConsumerURL(paramAffiliateBank.getConsumerURL());
    setBrandID(paramAffiliateBank.getBrandID());
    setWarehousingType(paramAffiliateBank.getWarehousingType());
    setAchMaxFutureDays(paramAffiliateBank.getAchMaxFutureDays());
    setAchSameDayEffDating(paramAffiliateBank.getAchSameDayEffDating());
    setEmailSubject("en_US", paramAffiliateBank.getEmailSubject("en_US"));
    setEmailMemo("en_US", paramAffiliateBank.getEmailMemo("en_US"));
    setEmailFrom("en_US", paramAffiliateBank.getEmailFrom("en_US"));
    setSearchLanguage(paramAffiliateBank.getSearchLanguage());
    setCurrentLanguage(paramAffiliateBank.getCurrentLanguage());
    Iterator localIterator = paramAffiliateBank.getLanguages();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        setEmailSubject(str, paramAffiliateBank.getEmailSubject(str));
        setEmailMemo(str, paramAffiliateBank.getEmailMemo(str));
        setEmailFrom(str, paramAffiliateBank.getEmailFrom(str));
      }
    }
    setEtfAccountNum(paramAffiliateBank.getEtfAccountNum());
    setEtfAccountType(paramAffiliateBank.getEtfAccountType());
    setEtfMaximumDepositAmountValue(paramAffiliateBank.getEtfMaximumDepositAmountValue());
    setEtfMinimumDepositAmountValue(paramAffiliateBank.getEtfMinimumDepositAmountValue());
    setEtfVirtualUserID(paramAffiliateBank.getEtfVirtualUserID());
    setEtfCompany(paramAffiliateBank.getEtfCompany());
    setEtfCompanyName(paramAffiliateBank.getEtfCompanyName());
    setEtfCompanyID(paramAffiliateBank.getEtfCompanyID());
    setEtfCompanyBatchDescription(paramAffiliateBank.getEtfCompanyBatchDescription());
    setTermsVersion(paramAffiliateBank.getTermsVersion());
    setTermsResetDate(paramAffiliateBank.getTermsResetDate());
    super.set(paramAffiliateBank);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "BANK_NAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    AffiliateBank localAffiliateBank = (AffiliateBank)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("BANK_ID")) && (this.qe != null) && (localAffiliateBank.getBankID() != null)) {
      return localCollator.compare(getBankID(), localAffiliateBank.getBankID());
    }
    if ((paramString.equals("BANK_NAME")) && (this.p3 != null) && (localAffiliateBank.getAffiliateBankName() != null)) {
      return localCollator.compare(getAffiliateBankName(), localAffiliateBank.getAffiliateBankName());
    }
    if ((paramString.equals("ROUTING_NUM")) && (this.pT != null) && (localAffiliateBank.getAffiliateRoutingNum() != null)) {
      return localCollator.compare(getAffiliateRoutingNum(), localAffiliateBank.getAffiliateRoutingNum());
    }
    if (paramString.equals("DESCRIPTION")) {
      return localCollator.compare(getDescription(), localAffiliateBank.getDescription());
    }
    if (paramString.equals("ENTITLEMENT_GROUP_ID")) {
      return getEntitlementGroupID() - localAffiliateBank.getEntitlementGroupID();
    }
    if ((paramString.equals("SWIFT_BIC")) && (this.pJ != null) && (localAffiliateBank.getSwiftBIC() != null)) {
      return localCollator.compare(getSwiftBIC(), localAffiliateBank.getSwiftBIC());
    }
    if ((paramString.equals("NATIONAL_ID")) && (this.pW != null) && (localAffiliateBank.getNationalID() != null)) {
      return localCollator.compare(getNationalID(), localAffiliateBank.getNationalID());
    }
    if ((paramString.equals("CHIPS_ID")) && (this.pQ != null) && (localAffiliateBank.getChipsID() != null)) {
      return localCollator.compare(getChipsID(), localAffiliateBank.getChipsID());
    }
    if ((paramString.equals("AFFILIATE_FI_BPW_ID")) && (this.p6 != null) && (localAffiliateBank.getFIBPWID() != null)) {
      return localCollator.compare(getFIBPWID(), localAffiliateBank.getFIBPWID());
    }
    if ((paramString.equals("CURRENCY_CODE")) && (this.pR != null) && (localAffiliateBank.getCurrencyCode() != null)) {
      return localCollator.compare(getCurrencyCode(), localAffiliateBank.getCurrencyCode());
    }
    if (paramString.equals("AFFILIATE_BANK_ID")) {
      return getAffiliateBankID() - localAffiliateBank.getAffiliateBankID();
    }
    if (paramString.equals("BANK_TYPE")) {
      return getBankTypeValue() - localAffiliateBank.getBankTypeValue();
    }
    if (paramString.equals("WAREHOUSING_TYPE")) {
      return getWarehousingType() - localAffiliateBank.getWarehousingType();
    }
    if (paramString.equals("ACH_MAX_FUTURE_DAYS")) {
      return getAchMaxFutureDays() - localAffiliateBank.getAchMaxFutureDays();
    }
    if (paramString.equals("ACH_SAMEDAY_EFF_DATING")) {
      return (getAchSameDayEffDating()) && (localAffiliateBank.getAchSameDayEffDating()) ? 0 : 1;
    }
    return super.compare(paramObject, paramString);
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("BANK_ID")) && (getBankID() != null)) {
      return isFilterable(getBankID(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANK_NAME")) && (getAffiliateBankName() != null)) {
      return isFilterable(getAffiliateBankName(), paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTING_NUM")) && (getAffiliateRoutingNum() != null)) {
      return isFilterable(getAffiliateRoutingNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("DESCRIPTION")) && (getDescription() != null)) {
      return isFilterable(getDescription(), paramString2, paramString3);
    }
    if (paramString1.equals("ENTITLEMENT_GROUP_ID")) {
      return isFilterable(String.valueOf(getEntitlementGroupID()), paramString2, paramString3);
    }
    if ((paramString1.equals("SWIFT_BIC")) && (getSwiftBIC() != null)) {
      return isFilterable(getSwiftBIC(), paramString2, paramString3);
    }
    if ((paramString1.equals("NATIONAL_ID")) && (getNationalID() != null)) {
      return isFilterable(getNationalID(), paramString2, paramString3);
    }
    if ((paramString1.equals("CHIPS_ID")) && (getChipsID() != null)) {
      return isFilterable(getChipsID(), paramString2, paramString3);
    }
    if ((paramString1.equals("AFFILIATE_FI_BPW_ID")) && (getFIBPWID() != null)) {
      return isFilterable(getFIBPWID(), paramString2, paramString3);
    }
    if ((paramString1.equals("CURRENCY_CODE")) && (getCurrencyCode() != null)) {
      return isFilterable(getCurrencyCode(), paramString2, paramString3);
    }
    if (paramString1.equals("AFFILIATE_BANK_ID")) {
      return isFilterable(String.valueOf(getAffiliateBankID()), paramString2, paramString3);
    }
    if ((paramString1.equals("BANK_TYPE")) && (getBankType() != null)) {
      return isFilterable(getBankType(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACH_MAX_FUTURE_DAYS")) && (getBankType() != null)) {
      return isFilterable(String.valueOf(getAchMaxFutureDays()), paramString2, paramString3);
    }
    if ((paramString1.equals("ACH_SAMEDAY_EFF_DATING")) && (getBankType() != null)) {
      return isFilterable(String.valueOf(getAchSameDayEffDating()), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setTermsVersion(int paramInt)
  {
    this._termsVersion = paramInt;
  }
  
  public void setTermsVersion(String paramString)
  {
    this._termsVersion = Integer.valueOf(paramString).intValue();
  }
  
  public void setTermsResetDate(DateTime paramDateTime)
  {
    this._termsResetDate = paramDateTime;
  }
  
  public void setTermsResetDate(String paramString)
  {
    try
    {
      if (this._termsResetDate == null) {
        this._termsResetDate = new DateTime(paramString, this.locale, "MM/dd/yyyy HH:mm:ss");
      } else {
        this._termsResetDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public int getTermsVersion()
  {
    return this._termsVersion;
  }
  
  public DateTime getTermsResetDate()
  {
    return this._termsResetDate;
  }
  
  public void setDateFormat(String paramString)
  {
    if (this._termsResetDate != null) {
      this._termsResetDate.setFormat(paramString);
    }
    super.setDateFormat(paramString);
  }
  
  public Iterator getLanguages()
  {
    if (this.p9 == null) {
      return null;
    }
    return this.p9.keySet().iterator();
  }
  
  public String getCurrentLanguage()
  {
    return this.pX;
  }
  
  public void setCurrentLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.pX = null;
    } else {
      this.pX = paramString;
    }
  }
  
  public String getSearchLanguage()
  {
    return this.pY;
  }
  
  public void setSearchLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.pY = null;
    } else {
      this.pY = paramString;
    }
  }
  
  private AffiliateBankI18N h()
  {
    if (this.pX == null) {
      return this;
    }
    return jdMethod_void(this.pX);
  }
  
  private AffiliateBankI18N jdMethod_void(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.equalsIgnoreCase("en_US")) {
      return this;
    }
    if (this.p9 == null) {
      return null;
    }
    return (AffiliateBankI18N)this.p9.get(paramString);
  }
  
  private AffiliateBankI18N jdMethod_null(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return null;
    }
    if (paramString.equalsIgnoreCase("en_US")) {
      return this;
    }
    if (this.p9 == null) {
      this.p9 = new HashMap();
    }
    AffiliateBankI18N localAffiliateBankI18N = (AffiliateBankI18N)this.p9.get(paramString);
    if (localAffiliateBankI18N == null)
    {
      localAffiliateBankI18N = new AffiliateBankI18N();
      localAffiliateBankI18N.setAffiliateBankID(getAffiliateBankID());
      localAffiliateBankI18N.setLanguage(paramString);
      this.p9.put(paramString, localAffiliateBankI18N);
    }
    return localAffiliateBankI18N;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.AffiliateBank
 * JD-Core Version:    0.7.0.1
 */