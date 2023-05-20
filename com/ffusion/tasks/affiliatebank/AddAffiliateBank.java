package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.Strings;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAffiliateBank
  extends BaseTask
  implements AffiliateBankTask
{
  protected AffiliateBank theBank = new AffiliateBank();
  private boolean aPZ = true;
  protected String calendarName = null;
  protected String etfMinimumDepositAmount = null;
  protected String etfMaximumDepositAmount = null;
  protected String _taskWarningURL = null;
  protected String _languageWarningURL = null;
  private String aP0 = "host:port";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.theBank.setBankID(localSecureUser.getBankID());
    convertEmptyStringsToNull(this.theBank);
    try
    {
      if (validateAffiliateBank(localHttpSession))
      {
        AffiliateBankAdmin.addAffiliateBank(localSecureUser, this.theBank, localHashMap);
        AffiliateBankAdmin.updateVirtualUserAndETFCompany(localSecureUser, this.theBank, localHashMap);
        if (this.error == 0) {
          str = this.successURL;
        }
      }
      else if (this.error == 0)
      {
        str = this._taskWarningURL;
      }
      else
      {
        str = this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateAffiliateBank(HttpSession paramHttpSession)
    throws CSILException
  {
    this._taskWarningURL = null;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str1 = this.theBank.getAffiliateBankName();
    if (str1 != null) {
      str1 = str1.trim();
    }
    String str2 = this.theBank.getDescription();
    if (str2 != null) {
      str2 = str2.trim();
    }
    String str3 = this.theBank.getAffiliateRoutingNum();
    if (str3 != null) {
      str3 = str3.trim();
    }
    String str4 = this.theBank.getSwiftBIC();
    if (str4 != null) {
      str4 = str4.trim();
    }
    String str5 = this.theBank.getACHImmediateOrigin();
    if (str5 != null) {
      str5.trim();
    }
    String str6 = this.theBank.getACHImmediateOriginName();
    if (str6 != null) {
      str6 = str6.trim();
    }
    String str7 = this.theBank.getACHDestination();
    if (str7 != null) {
      str7 = str7.trim();
    }
    String str8 = this.theBank.getACHDestinationName();
    if (str8 != null) {
      str8 = str8.trim();
    }
    String str9 = this.theBank.getStreet();
    String str10 = this.theBank.getStreet2();
    String str11 = this.theBank.getStreet3();
    String str12 = this.theBank.getCity();
    String str13 = this.theBank.getState();
    String str14 = this.theBank.getCountry();
    String str15 = this.theBank.getZipCode();
    String str16 = this.theBank.getBrandID();
    String str17 = this.theBank.getConsumerURL();
    String str18 = this.theBank.getCorporateURL();
    String str19 = this.theBank.getEtfCompanyName();
    String str20 = this.theBank.getEtfCompanyID();
    String str21 = this.theBank.getEtfCompanyBatchDescription();
    String str22 = this.theBank.getEtfAccountNum();
    String str23 = this.theBank.getEtfAccountType();
    boolean bool = false;
    if ((str1 == null) || (str1.length() == 0))
    {
      this.error = 25502;
      return false;
    }
    if ((str3 == null) || (str3.length() == 0))
    {
      this.error = 25503;
      return false;
    }
    try
    {
      bool = CommBankIdentifier.isValidCheckZeros(str3);
    }
    catch (Exception localException1)
    {
      bool = false;
    }
    if (!bool)
    {
      this.error = 25507;
      return false;
    }
    if ((str4 != null) && (str4.length() > 0) && (((str4.length() != 8) && (str4.length() != 11)) || (Strings.hasSpecialChars(str4))))
    {
      this.error = 25530;
      return false;
    }
    if (this.theBank.getWarehousingType() == 0)
    {
      this.error = 25517;
      return false;
    }
    if ((this.theBank.getWarehousingType() != 2) && (this.theBank.getWarehousingType() != 1))
    {
      this.error = 25518;
      return false;
    }
    if (this.aPZ)
    {
      this.error = 25519;
      return false;
    }
    if ((!this.aPZ) && ((this.theBank.getAchMaxFutureDays() < 1) || (this.theBank.getAchMaxFutureDays() > 90)))
    {
      this.error = 25520;
      return false;
    }
    if (((str5 != null) && (str5.length() > 0)) || ((str6 != null) && (str6.length() > 0)) || ((str7 != null) && (str7.length() > 0)) || ((str8 != null) && (str8.length() > 0)))
    {
      if ((str5 == null) || (str5.length() == 0))
      {
        this.error = 25508;
        return false;
      }
      try
      {
        bool = CommBankIdentifier.isValidCheckZeros(str5);
      }
      catch (Exception localException2)
      {
        bool = false;
      }
      if (!bool)
      {
        this.error = 25512;
        return false;
      }
      if ((str6 == null) || (str6.length() == 0))
      {
        this.error = 25509;
        return false;
      }
      if (str6.length() > 23)
      {
        this.error = 25514;
        return false;
      }
      if ((str7 == null) || (str7.length() == 0))
      {
        this.error = 25510;
        return false;
      }
      try
      {
        bool = CommBankIdentifier.isValidCheckZeros(str7);
      }
      catch (Exception localException3)
      {
        bool = false;
      }
      if (!bool)
      {
        this.error = 25513;
        return false;
      }
      if ((str8 == null) || (str8.length() == 0))
      {
        this.error = 25511;
        return false;
      }
      if (str8.length() > 23)
      {
        this.error = 25515;
        return false;
      }
    }
    if ((null != str16) && (str16.length() > 255))
    {
      this.error = 83;
      return false;
    }
    if ((null != str9) && (str9.length() > 35))
    {
      this.error = 23;
      return false;
    }
    if ((null != str10) && (str10.length() > 35))
    {
      this.error = 52;
      return false;
    }
    if ((null != str11) && (str11.length() > 35))
    {
      this.error = 82;
      return false;
    }
    if ((null != str12) && (str12.length() > 32))
    {
      this.error = 24;
      return false;
    }
    if ((null != str13) && (str13.length() > 32))
    {
      this.error = 25;
      return false;
    }
    if ((null != str15) && (str15.length() > 10))
    {
      this.error = 26;
      return false;
    }
    if ((str14 != null) && (str14.length() > 0))
    {
      HashMap localHashMap = new HashMap();
      localObject1 = Util.getCodeForBankLookupCountryName(localSecureUser, str14, localHashMap);
      if ((null != str15) && (str15.length() > 0))
      {
        String str24 = Util.validateZipCodeFormat(localSecureUser, (String)localObject1, str15, localHashMap);
        if (str24 == null)
        {
          this.error = 26;
          return false;
        }
        if (!str24.equals("")) {
          this.theBank.getZipCodeValue().setFormat(str24);
        }
      }
    }
    int i = 0;
    Object localObject1 = null;
    localObject1 = Util.getLanguageList(localSecureUser, new HashMap());
    Object localObject2;
    for (int j = 0; j < ((LanguageDefns)localObject1).size(); j++)
    {
      LanguageDefn localLanguageDefn = (LanguageDefn)((LanguageDefns)localObject1).get(j);
      localObject2 = localLanguageDefn.getLanguage();
      String str25 = this.theBank.getEmailSubject((String)localObject2);
      String str26 = this.theBank.getEmailMemo((String)localObject2);
      String str27 = this.theBank.getEmailFrom((String)localObject2);
      if (((str25 != null) && ((str26 == null) || (str27 == null))) || ((str26 != null) && ((str25 == null) || (str27 == null))) || ((str27 != null) && ((str26 == null) || (str25 == null))))
      {
        this.error = 90;
        return false;
      }
      if ((str25 != null) && (str25.trim().length() > 0) && (str26 != null) && (str26.trim().length() > 0) && (str27 != null) && (str27.trim().length() > 0))
      {
        if (str25.trim().length() > 255)
        {
          this.error = 91;
          return false;
        }
        if (str26.trim().length() > 1024)
        {
          this.error = 92;
          return false;
        }
        if (str27.trim().length() > 255)
        {
          this.error = 93;
          return false;
        }
      }
      if ((str25 != null) && (str25.trim().length() > 0)) {
        i++;
      }
    }
    if ((this._languageWarningURL != null) && (this._taskWarningURL == null) && (i > 0) && (i != ((LanguageDefns)localObject1).size()))
    {
      this._taskWarningURL = this._languageWarningURL;
      this._languageWarningURL = null;
    }
    j = 0;
    int k = 0;
    if (((null != str21) && (str21.length() > 0)) || ((null != str20) && (str20.length() > 0)) || ((null != str19) && (str19.length() > 0))) {
      j = 1;
    }
    if ((null != str19) && (str19.length() > 0))
    {
      if (str19.length() > 16) {
        this.error = 25521;
      }
    }
    else if (j != 0) {
      k = 1;
    }
    if ((null != str20) && (str20.length() > 0))
    {
      if (str20.length() > 10) {
        this.error = 25522;
      }
    }
    else if (j != 0) {
      k = 1;
    }
    if ((null != str21) && (str21.length() > 0))
    {
      if (str21.length() > 10) {
        this.error = 25523;
      }
    }
    else if (j != 0) {
      k = 1;
    }
    if ((this.error == 0) && (k != 0)) {
      this.error = 25524;
    }
    if ((null != str22) && (str22.length() > 0)) {
      if (!Strings.isValidAccountNumber(str22, localSecureUser.getLocale())) {
        this.error = 25526;
      } else if (str22.length() > 17) {
        this.error = 25525;
      }
    }
    try
    {
      localObject2 = new BigDecimal("" + this.etfMinimumDepositAmount);
      localObject2 = ((BigDecimal)localObject2).movePointRight(2);
      this.theBank.setEtfMinimumDepositAmountValue(((BigDecimal)localObject2).intValue());
    }
    catch (Exception localException4)
    {
      this.error = 25529;
    }
    try
    {
      BigDecimal localBigDecimal = new BigDecimal("" + this.etfMaximumDepositAmount);
      localBigDecimal = localBigDecimal.movePointRight(2);
      this.theBank.setEtfMaximumDepositAmountValue(localBigDecimal.intValue());
    }
    catch (Exception localException5)
    {
      this.error = 25529;
    }
    int m = this.theBank.getEtfMinimumDepositAmountValue();
    int n = this.theBank.getEtfMaximumDepositAmountValue();
    if (this.error == 0) {
      if ((m < 0) || (n < 0)) {
        this.error = 25527;
      } else if (m > n) {
        this.error = 25528;
      }
    }
    if (this.error != 0) {
      return false;
    }
    return this._taskWarningURL == null;
  }
  
  public void setLanguageWarningURL(String paramString)
  {
    this._languageWarningURL = paramString;
  }
  
  public void setBrandID(String paramString)
  {
    this.theBank.setBrandID(paramString);
  }
  
  public String getBrandID()
  {
    return this.theBank.getBrandID();
  }
  
  public void setAffiliateBankName(String paramString)
  {
    this.theBank.setAffiliateBankName(paramString);
  }
  
  public String getAffiliateBankName()
  {
    return this.theBank.getAffiliateBankName();
  }
  
  public void setBankType(String paramString)
  {
    this.theBank.setBankType(paramString);
  }
  
  public String getBankType()
  {
    return this.theBank.getBankType();
  }
  
  public void setDescription(String paramString)
  {
    this.theBank.setDescription(paramString);
  }
  
  public String getDescription()
  {
    return this.theBank.getDescription();
  }
  
  public void setAffiliateRoutingNumber(String paramString)
  {
    this.theBank.setAffiliateRoutingNum(paramString);
  }
  
  public String getAffiliateRoutingNumber()
  {
    return this.theBank.getAffiliateRoutingNum();
  }
  
  public void setACHImmediateOrigin(String paramString)
  {
    this.theBank.setACHImmediateOrigin(paramString);
  }
  
  public String getACHImmediateOrigin()
  {
    return this.theBank.getACHImmediateOrigin();
  }
  
  public void setACHImmediateOriginName(String paramString)
  {
    this.theBank.setACHImmediateOriginName(paramString);
  }
  
  public String getACHImmediateOriginName()
  {
    return this.theBank.getACHImmediateOriginName();
  }
  
  public void setACHDestination(String paramString)
  {
    this.theBank.setACHDestination(paramString);
  }
  
  public String getACHDestination()
  {
    return this.theBank.getACHDestination();
  }
  
  public void setACHDestinationName(String paramString)
  {
    this.theBank.setACHDestinationName(paramString);
  }
  
  public String getACHDestinationName()
  {
    return this.theBank.getACHDestinationName();
  }
  
  public void setWarehousingType(String paramString)
  {
    this.theBank.setWarehousingType(Integer.parseInt(paramString));
  }
  
  public String getWarehousingType()
  {
    return String.valueOf(this.theBank.getWarehousingType());
  }
  
  public void setAchMaxFutureDays(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      this.aPZ = true;
    }
    else
    {
      this.aPZ = false;
      this.theBank.setAchMaxFutureDays(Integer.parseInt(paramString));
    }
  }
  
  public String getAchMaxFutureDays()
  {
    if (this.theBank.getAchMaxFutureDays() <= 0) {
      return "";
    }
    return String.valueOf(this.theBank.getAchMaxFutureDays());
  }
  
  public void setAchSameDayEffDating(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.theBank.setAchSameDayEffDating(true);
    } else {
      this.theBank.setAchSameDayEffDating(false);
    }
  }
  
  public String getAchSameDayEffDating()
  {
    return String.valueOf(this.theBank.getAchSameDayEffDating());
  }
  
  public void setSwiftBIC(String paramString)
  {
    this.theBank.setSwiftBIC(paramString);
  }
  
  public String getSwiftBIC()
  {
    return this.theBank.getSwiftBIC();
  }
  
  public void setNationalID(String paramString)
  {
    this.theBank.setNationalID(paramString);
  }
  
  public String getNationalID()
  {
    return this.theBank.getNationalID();
  }
  
  public void setChipsID(String paramString)
  {
    this.theBank.setChipsID(paramString);
  }
  
  public String getChipsID()
  {
    return this.theBank.getChipsID();
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.theBank.setCurrencyCode(paramString);
  }
  
  public String getCurrencyCode()
  {
    return this.theBank.getCurrencyCode();
  }
  
  public void setStreet(String paramString)
  {
    this.theBank.setStreet(paramString);
  }
  
  public String getStreet()
  {
    return this.theBank.getStreet();
  }
  
  public void setStreet2(String paramString)
  {
    this.theBank.setStreet2(paramString);
  }
  
  public String getStreet2()
  {
    return this.theBank.getStreet2();
  }
  
  public void setStreet3(String paramString)
  {
    this.theBank.setStreet3(paramString);
  }
  
  public String getStreet3()
  {
    return this.theBank.getStreet3();
  }
  
  public void setCity(String paramString)
  {
    this.theBank.setCity(paramString);
  }
  
  public String getCity()
  {
    return this.theBank.getCity();
  }
  
  public void setState(String paramString)
  {
    this.theBank.setState(paramString);
  }
  
  public void setStateCode(String paramString)
  {
    this.theBank.setStateCode(paramString);
  }
  
  public String getState()
  {
    return this.theBank.getState();
  }
  
  public String getStateCode()
  {
    return this.theBank.getStateCode();
  }
  
  public void setCountry(String paramString)
  {
    this.theBank.setCountry(paramString);
  }
  
  public String getCountry()
  {
    return this.theBank.getCountry();
  }
  
  public void setZipCode(String paramString)
  {
    this.theBank.setZipCode(paramString);
  }
  
  public void setZipCode(ZipCode paramZipCode)
  {
    this.theBank.setZipCode(paramZipCode);
  }
  
  public void setZipCodeFormat(String paramString)
  {
    this.theBank.setZipCodeFormat(paramString);
  }
  
  public void setZipCodeAllNumbers(String paramString)
  {
    this.theBank.setZipCodeAllNumbers(paramString);
  }
  
  public boolean isLastSetZipCodeValid()
  {
    return this.theBank.isLastSetZipCodeValid();
  }
  
  public String getZipCode()
  {
    return this.theBank.getZipCode();
  }
  
  public ZipCode getZipCodeValue()
  {
    return this.theBank.getZipCodeValue();
  }
  
  public void setPhone(String paramString)
  {
    this.theBank.setPhone(paramString);
  }
  
  public void setPhone(Phone paramPhone)
  {
    this.theBank.setPhone(paramPhone);
  }
  
  public void setPhoneAllNumbers(String paramString)
  {
    this.theBank.setPhoneAllNumbers(paramString);
  }
  
  public void setPhoneNumberFormat(String paramString)
  {
    this.theBank.setPhoneNumberFormat(paramString);
  }
  
  public boolean isLastSetPhoneValid()
  {
    return this.theBank.isLastSetPhoneValid();
  }
  
  public String getPhone()
  {
    return this.theBank.getPhone();
  }
  
  public Phone getPhoneValue()
  {
    return this.theBank.getPhoneValue();
  }
  
  public void setPhone2(String paramString)
  {
    this.theBank.setPhone2(paramString);
  }
  
  public void setPhone2(Phone paramPhone)
  {
    this.theBank.setPhone2(paramPhone);
  }
  
  public void setPhoneNumber2Format(String paramString)
  {
    this.theBank.setPhoneNumber2Format(paramString);
  }
  
  public void setPhone2AllNumbers(String paramString)
  {
    this.theBank.setPhone2AllNumbers(paramString);
  }
  
  public boolean isLastSetPhone2Valid()
  {
    return this.theBank.isLastSetPhone2Valid();
  }
  
  public String getPhone2()
  {
    return this.theBank.getPhone2();
  }
  
  public Phone getPhone2Value()
  {
    return this.theBank.getPhone2Value();
  }
  
  public void setEmail(String paramString)
  {
    this.theBank.setEmail(paramString);
  }
  
  public void setEmail(Email paramEmail)
  {
    this.theBank.setEmail(paramEmail);
  }
  
  public boolean isLastSetEmailValid()
  {
    return this.theBank.isLastSetEmailValid();
  }
  
  public String getEmail()
  {
    return this.theBank.getEmail();
  }
  
  public Email getEmailValue()
  {
    return this.theBank.getEmailValue();
  }
  
  public void setDataPhone(String paramString)
  {
    this.theBank.setDataPhone(paramString);
  }
  
  public void setDataPhone(Phone paramPhone)
  {
    this.theBank.setDataPhone(paramPhone);
  }
  
  public void setDataPhoneAllNumbers(String paramString)
  {
    this.theBank.setDataPhoneAllNumbers(paramString);
  }
  
  public void setDataPhoneNumberFormat(String paramString)
  {
    this.theBank.setDataPhoneNumberFormat(paramString);
  }
  
  public boolean isLastSetDataPhoneValid()
  {
    return this.theBank.isLastSetDataPhoneValid();
  }
  
  public String getDataPhone()
  {
    return this.theBank.getDataPhone();
  }
  
  public Phone getDataPhoneValue()
  {
    return this.theBank.getDataPhoneValue();
  }
  
  public void setFaxPhone(String paramString)
  {
    this.theBank.setFaxPhone(paramString);
  }
  
  public void setFaxPhone(Phone paramPhone)
  {
    this.theBank.setFaxPhone(paramPhone);
  }
  
  public void setFaxPhoneAllNumbers(String paramString)
  {
    this.theBank.setFaxPhoneAllNumbers(paramString);
  }
  
  public void setFaxPhoneNumberFormat(String paramString)
  {
    this.theBank.setFaxPhoneNumberFormat(paramString);
  }
  
  public boolean isLastSetFaxPhoneValid()
  {
    return this.theBank.isLastSetFaxPhoneValid();
  }
  
  public String getFaxPhone()
  {
    return this.theBank.getFaxPhone();
  }
  
  public Phone getFaxPhoneValue()
  {
    return this.theBank.getFaxPhoneValue();
  }
  
  public void setPreferredContactMethod(String paramString)
  {
    this.theBank.setPreferredContactMethod(paramString);
  }
  
  public String getPreferredContactMethod()
  {
    return this.theBank.getPreferredContactMethod();
  }
  
  public void setCorporateURL(String paramString)
  {
    this.theBank.setCorporateURL(paramString);
  }
  
  public String getCorporateURL()
  {
    return this.theBank.getCorporateURL();
  }
  
  public void setConsumerURL(String paramString)
  {
    this.theBank.setConsumerURL(paramString);
  }
  
  public String getConsumerURL()
  {
    return this.theBank.getConsumerURL();
  }
  
  public void setCurrentLanguage(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.theBank.setCurrentLanguage(paramString.trim());
    } else {
      this.theBank.setCurrentLanguage(null);
    }
  }
  
  public String getCurrentLanguage()
  {
    return this.theBank.getCurrentLanguage();
  }
  
  public void setEmailSubject(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.theBank.setEmailSubject(paramString.trim());
    } else {
      this.theBank.setEmailSubject(null);
    }
  }
  
  public String getEmailSubject()
  {
    return this.theBank.getEmailSubject();
  }
  
  public void setEmailMemo(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.theBank.setEmailMemo(paramString.trim());
    } else {
      this.theBank.setEmailMemo(null);
    }
  }
  
  public String getEmailMemo()
  {
    return this.theBank.getEmailMemo();
  }
  
  public void setEmailFrom(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.theBank.setEmailFrom(paramString.trim());
    } else {
      this.theBank.setEmailFrom(null);
    }
  }
  
  public String getEmailFrom()
  {
    return this.theBank.getEmailFrom();
  }
  
  public String getCalendarName()
  {
    return this.calendarName;
  }
  
  public void setCalendarName(String paramString)
  {
    this.calendarName = paramString;
  }
  
  protected static void convertEmptyStringsToNull(AffiliateBank paramAffiliateBank)
  {
    String str = "";
    if (paramAffiliateBank != null)
    {
      if ("".equals(paramAffiliateBank.getDescription())) {
        paramAffiliateBank.setDescription(null);
      }
      if ("".equals(paramAffiliateBank.getBrandID())) {
        paramAffiliateBank.setBrandID(null);
      }
      if ("".equals(paramAffiliateBank.getConsumerURL())) {
        paramAffiliateBank.setConsumerURL(null);
      }
      if ("".equals(paramAffiliateBank.getCorporateURL())) {
        paramAffiliateBank.setCorporateURL(null);
      }
    }
  }
  
  public String getEtfMinimumDepositAmount()
  {
    BigDecimal localBigDecimal = new BigDecimal("" + this.theBank.getEtfMinimumDepositAmountValue());
    localBigDecimal = localBigDecimal.movePointLeft(2);
    return localBigDecimal.toString();
  }
  
  public void setEtfMinimumDepositAmount(String paramString)
  {
    this.etfMinimumDepositAmount = paramString;
  }
  
  public String getEtfMaximumDepositAmount()
  {
    BigDecimal localBigDecimal = new BigDecimal("" + this.theBank.getEtfMaximumDepositAmountValue());
    localBigDecimal = localBigDecimal.movePointLeft(2);
    return localBigDecimal.toString();
  }
  
  public void setEtfMaximumDepositAmount(String paramString)
  {
    this.etfMaximumDepositAmount = paramString;
  }
  
  public String getEtfAccountNum()
  {
    return this.theBank.getEtfAccountNum();
  }
  
  public void setEtfAccountNum(String paramString)
  {
    String str = paramString.trim();
    str = Strings.removeChars(str, ' ');
    this.theBank.setEtfAccountNum(str);
  }
  
  public String getEtfAccountType()
  {
    return this.theBank.getEtfAccountType();
  }
  
  public String getEtfAccountTypeValue()
  {
    return this.theBank.getEtfAccountTypeValueAsString();
  }
  
  public void setEtfAccountType(String paramString)
  {
    this.theBank.setEtfAccountType(paramString);
  }
  
  public String getEtfCompanyName()
  {
    return this.theBank.getEtfCompanyName();
  }
  
  public void setEtfCompanyName(String paramString)
  {
    this.theBank.setEtfCompanyName(paramString);
  }
  
  public String getEtfCompanyID()
  {
    return this.theBank.getEtfCompanyID();
  }
  
  public void setEtfCompanyID(String paramString)
  {
    this.theBank.setEtfCompanyID(paramString);
  }
  
  public String getEtfCompanyBatchDescription()
  {
    return this.theBank.getEtfCompanyBatchDescription();
  }
  
  public void setEtfCompanyBatchDescription(String paramString)
  {
    this.theBank.setEtfCompanyBatchDescription(paramString);
  }
  
  public String getBankID()
  {
    return this.theBank.getBankID();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.AddAffiliateBank
 * JD-Core Version:    0.7.0.1
 */