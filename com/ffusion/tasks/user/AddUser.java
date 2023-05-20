package com.ffusion.tasks.user;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Email;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.SocialSecurity;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.tasks.ValidatingTask;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUser
  extends User
  implements ValidatingTask, UserTask
{
  protected static final String DEFAULT_ADDITIONAL_FILE_NAME = "PORTAL_SETTINGS";
  protected static final String CURRENT_PASSWORD = "CURRENT_PASSWORD";
  protected static final String NEW_PASSWORD = "NEW_PASSWORD";
  public static final String PASSWORD_MASK = "****";
  protected int minLetters = 1;
  protected int minNumbers = 1;
  protected int minPasswordLength = 3;
  protected int ADD_USER_MAX_NAME_LENGTH = 35;
  protected int ADD_USER_MAX_STREET_LENGTH = 40;
  protected int ADD_USER_MAX_CITY_LENGTH = 20;
  protected int ADD_USER_MAX_ZIPCODE_LENGTH = 11;
  protected int ADD_USER_MAX_STATE_LENGTH = 2;
  protected int ADD_USER_MAX_COUNTRY_LENGTH = 30;
  protected int ADD_USER_MAX_CUSTID_LENGTH = 32;
  protected int ADD_USER_MAX_PHONE_LENGTH = 30;
  protected int ADD_USER_MAX_DATAPHONE_LENGTH = 30;
  protected int ADD_USER_MAX_EMAIL_LENGTH = 40;
  protected int ADD_USER_MAX_SSN_LENGTH = 20;
  protected int ADD_USER_MAX_TITLE_LENGTH = 10;
  protected int ADD_USER_MAX_GREETING_LENGTH = 20;
  protected int ADD_USER_MAX_USERNAME_LENGTH = 18;
  protected int ADD_USER_MAX_PASSWORD_LENGTH = 50;
  protected int ADD_USER_MAX_PASSWORD_CLUE_LENGTH = 50;
  protected int ADD_USER_MAX_PASSWORD_REMINDER_LENGTH = 50;
  protected int ADD_USER_MAX_BIRTHDATE_LENGTH = 26;
  protected int ADD_USER_MAX_PREFERRED_LANGUAGE_LENGTH = 5;
  protected int ADD_USER_MAX_GREETING_TYPE_LENGTH = 10;
  protected int ADD_USER_MAX_FAMILY_LENGTH = 10;
  protected int ADD_USER_MAX_RESIDENCE_LENGTH = 10;
  protected int ADD_USER_MAX_INCOME_LENGTH = 10;
  protected int ADD_USER_MAX_INTENDED_USE_LENGTH = 10;
  protected int ADD_USER_MAX_GENDER_LENGTH = 10;
  protected int ADD_USER_MAX_AFFILIATE_BANK_ID_LENGTH = 10;
  protected int ADD_USER_MAX_PERSONAL_BANKER_LENGTH = 10;
  protected int ADD_USER_MAX_ACCOUNT_STATUS_LENGTH = 10;
  protected int ADD_USER_MAX_TIME_OUT_LENGTH = 10;
  protected int minUserNameLength = 3;
  protected int maxUserNameLength = 20;
  protected boolean noSpecialChars = true;
  protected String currentPassword;
  protected String newPassword;
  protected String password2;
  protected String confirmPasswordReminder;
  protected String confirmPasswordReminder2;
  protected String newPasswordReminder;
  protected String newPasswordReminder2;
  protected String oldPasswordReminder;
  protected String oldPasswordReminder2;
  protected String verifyEmail;
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validInputURL;
  protected String validate;
  protected String verifyFormat;
  protected boolean processFlag = false;
  protected int error;
  protected String additionalSettingsFile;
  protected String additionalSettingsNameSpace;
  protected boolean addUserFromSession;
  protected String userSessionName = "User";
  protected String usersSessionName = "Users";
  protected String oldUserSessionName = "OldUser";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.user.resources";
  protected String nextURL = null;
  private SecureUser pb = null;
  private String o3 = null;
  private String o7 = null;
  private String o5 = null;
  private String o9 = null;
  private Properties o8 = new Properties();
  private String o4 = "";
  protected String _birthDate;
  protected int _userType = 1;
  private HashMap o2 = new HashMap();
  private String pa;
  private String o6;
  public static final String BACKENDUSERINFO = "BACKENDUSERINFO";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.nextURL = this.successURL;
    this.error = 0;
    this.pb = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    try
    {
      if (this.addUserFromSession)
      {
        User localUser = (User)localHttpSession.getAttribute("User");
        if (localUser != null) {
          set(localUser);
        }
      }
      convertEmptyStringsToNull(this);
      if (validateInput(localHttpSession))
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          if (addUser(localHttpSession))
          {
            if ((this.additionalSettingsFile != null) && (this.additionalSettingsFile.trim().length() > 0) && (addAdditionalSettings() != 0)) {
              this.nextURL = this.serviceErrorURL;
            }
          }
          else {
            this.nextURL = this.serviceErrorURL;
          }
        }
        else
        {
          this.nextURL = this.validInputURL;
        }
      }
      else if ((this.serviceErrorURL != null) && (!this.serviceErrorURL.equals(this.nextURL))) {
        this.nextURL = this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    return this.nextURL;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (!validateFieldLength(paramHttpSession)) {
      return false;
    }
    if (this.validate != null) {
      bool = validationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = verifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    if ((bool) && (aPasswordEntered())) {
      this.password = this.newPassword;
    }
    checkPasswordRemindersEntered();
    Iterator localIterator1 = this.o2.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      HashMap localHashMap = (HashMap)this.o2.get(str1);
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        a locala = (a)localHashMap.get(str2);
        if ((locala != null) && (locala.jdMethod_if()) && ((locala.a() == null) || (locala.a().length() == 0))) {
          return setError(154);
        }
        if ((locala == null) || (!Currency.isValid(locala.a(), this.locale))) {
          return setError(16117);
        }
      }
    }
    return bool;
  }
  
  public void setStateValidationResourceFile(String paramString)
  {
    this.o3 = paramString;
  }
  
  public String getStateValidationResourceFile()
  {
    return this.o3;
  }
  
  public void setStateValidationResourceName(String paramString)
  {
    this.o7 = paramString;
  }
  
  public String getStateValidationResourceName()
  {
    return this.o7;
  }
  
  public void setCountryValidationResourceFile(String paramString)
  {
    this.o5 = paramString;
  }
  
  public String getCountryValidationResourceFile()
  {
    return this.o5;
  }
  
  public void setCountryValidationResourceName(String paramString)
  {
    this.o9 = paramString;
  }
  
  public String getCountryValidationResourceName()
  {
    return this.o9;
  }
  
  protected boolean validateFieldLength(HttpSession paramHttpSession)
  {
    if ((getFirstName() != null) && (getFirstName().length() > this.ADD_USER_MAX_NAME_LENGTH)) {
      return setError(160);
    }
    if ((getMiddleName() != null) && (getMiddleName().length() > this.ADD_USER_MAX_NAME_LENGTH)) {
      return setError(161);
    }
    if ((getLastName() != null) && (getLastName().length() > this.ADD_USER_MAX_NAME_LENGTH)) {
      return setError(162);
    }
    if ((this.street != null) && (this.street.length() > this.ADD_USER_MAX_STREET_LENGTH)) {
      return setError(163);
    }
    if ((this.street2 != null) && (this.street2.length() > this.ADD_USER_MAX_STREET_LENGTH)) {
      return setError(163);
    }
    if ((this.city != null) && (this.city.length() > this.ADD_USER_MAX_CITY_LENGTH)) {
      return setError(164);
    }
    if ((this.zipCode != null) && (this.zipCode.getString().length() > this.ADD_USER_MAX_ZIPCODE_LENGTH)) {
      return setError(165);
    }
    if ((this.state != null) && (this.state.length() > this.ADD_USER_MAX_STATE_LENGTH)) {
      return setError(166);
    }
    if ((this.country != null) && (this.country.length() > this.ADD_USER_MAX_COUNTRY_LENGTH)) {
      return setError(167);
    }
    if ((this.custId != null) && (this.custId.length() > this.ADD_USER_MAX_CUSTID_LENGTH)) {
      return setError(168);
    }
    if ((this.phone != null) && (this.phone.getString().length() > this.ADD_USER_MAX_PHONE_LENGTH)) {
      return setError(169);
    }
    if ((this.phone2 != null) && (this.phone2.getString().length() > this.ADD_USER_MAX_PHONE_LENGTH)) {
      return setError(169);
    }
    if ((this.faxPhone != null) && (this.faxPhone.getString().length() > this.ADD_USER_MAX_PHONE_LENGTH)) {
      return setError(180);
    }
    if ((this.dataPhone != null) && (this.dataPhone.getString().length() > this.ADD_USER_MAX_DATAPHONE_LENGTH)) {
      return setError(170);
    }
    if ((this.email != null) && (this.email.getString().length() > this.ADD_USER_MAX_EMAIL_LENGTH)) {
      return setError(171);
    }
    if ((this.verifyEmail != null) && (this.verifyEmail.length() > this.ADD_USER_MAX_EMAIL_LENGTH)) {
      return setError(171);
    }
    if ((this.ssn != null) && (this.ssn.getString().length() > this.ADD_USER_MAX_SSN_LENGTH)) {
      return setError(172);
    }
    if ((this.title != null) && (this.title.length() > this.ADD_USER_MAX_TITLE_LENGTH)) {
      return setError(173);
    }
    if ((this.greeting != null) && (this.greeting.length() > this.ADD_USER_MAX_GREETING_LENGTH)) {
      return setError(174);
    }
    if (this.userName != null)
    {
      if (Strings.verifyStringWithNonAscii(this.userName)) {
        return setError(199);
      }
      if (this.userName.length() > this.ADD_USER_MAX_USERNAME_LENGTH) {
        return setError(175);
      }
    }
    if ((this.newPassword != null) && (this.newPassword.length() > this.ADD_USER_MAX_PASSWORD_LENGTH)) {
      return setError(176);
    }
    if ((this.password2 != null) && (this.password2.length() > this.ADD_USER_MAX_PASSWORD_LENGTH)) {
      return setError(176);
    }
    if ((this.currentPassword != null) && (this.currentPassword.length() > this.ADD_USER_MAX_PASSWORD_LENGTH)) {
      return setError(176);
    }
    if ((this.passwordClue != null) && (this.passwordClue.length() > this.ADD_USER_MAX_PASSWORD_CLUE_LENGTH)) {
      return setError(177);
    }
    if ((this.newPasswordReminder != null) && (this.newPasswordReminder.length() > this.ADD_USER_MAX_PASSWORD_REMINDER_LENGTH)) {
      return setError(178);
    }
    if ((this.confirmPasswordReminder != null) && (this.confirmPasswordReminder.length() > this.ADD_USER_MAX_PASSWORD_REMINDER_LENGTH)) {
      return setError(178);
    }
    if ((this.passwordClue2 != null) && (this.passwordClue2.length() > this.ADD_USER_MAX_PASSWORD_CLUE_LENGTH)) {
      return setError(177);
    }
    if ((this.newPasswordReminder2 != null) && (this.newPasswordReminder2.length() > this.ADD_USER_MAX_PASSWORD_REMINDER_LENGTH)) {
      return setError(178);
    }
    if ((this.confirmPasswordReminder2 != null) && (this.confirmPasswordReminder2.length() > this.ADD_USER_MAX_PASSWORD_REMINDER_LENGTH)) {
      return setError(178);
    }
    if ((this._birthDate != null) && (this._birthDate.length() > this.ADD_USER_MAX_BIRTHDATE_LENGTH)) {
      return setError(179);
    }
    if ((this.preferredLanguage != null) && (this.preferredLanguage.length() > this.ADD_USER_MAX_PREFERRED_LANGUAGE_LENGTH)) {
      return setError(181);
    }
    if ((this.greetingType != null) && (this.greetingType.length() > this.ADD_USER_MAX_GREETING_TYPE_LENGTH)) {
      return setError(182);
    }
    if (((String)get("FAMILY") != null) && (((String)get("FAMILY")).length() > this.ADD_USER_MAX_FAMILY_LENGTH)) {
      return setError(183);
    }
    if (((String)get("RESIDENCE") != null) && (((String)get("RESIDENCE")).length() > this.ADD_USER_MAX_RESIDENCE_LENGTH)) {
      return setError(184);
    }
    if (((String)get("INCOME") != null) && (((String)get("INCOME")).length() > this.ADD_USER_MAX_INCOME_LENGTH)) {
      return setError(185);
    }
    if (((String)get("INTENDED_USE") != null) && (((String)get("INTENDED_USE")).length() > this.ADD_USER_MAX_INTENDED_USE_LENGTH)) {
      return setError(186);
    }
    if ((this.affiliateBankID >= 0) && (Integer.toString(this.affiliateBankID).length() > this.ADD_USER_MAX_AFFILIATE_BANK_ID_LENGTH)) {
      return setError(188);
    }
    if ((this.personalBanker != null) && (this.personalBanker.length() > this.ADD_USER_MAX_PERSONAL_BANKER_LENGTH)) {
      return setError(189);
    }
    if ((this.accountStatus != null) && (this.accountStatus.length() > this.ADD_USER_MAX_ACCOUNT_STATUS_LENGTH)) {
      return setError(190);
    }
    if ((this.timeout != null) && (this.timeout.length() > this.ADD_USER_MAX_TIME_OUT_LENGTH)) {
      return setError(191);
    }
    return true;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
  {
    boolean bool = true;
    StringTokenizer localStringTokenizer = new StringTokenizer(this.validate, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken().trim();
      if (("FIRST".equals(str)) && ((getFirstName() == null) || (getFirstName().length() == 0))) {
        return setError(28);
      }
      if (("MIDDLE".equals(str)) && ((getMiddleName() == null) || (getMiddleName().length() == 0))) {
        return setError(29);
      }
      if (("LAST".equals(str)) && ((getLastName() == null) || (getLastName().length() == 0))) {
        return setError(30);
      }
      if (("STREET".equals(str)) && ((this.street == null) || (this.street.length() == 0))) {
        return setError(23);
      }
      if (("STREET2".equals(str)) && ((this.street2 == null) || (this.street2.length() == 0))) {
        return setError(23);
      }
      if (("CITY".equals(str)) && ((this.city == null) || (this.city.length() == 0))) {
        return setError(24);
      }
      if (("ZIPCODE".equals(str)) && ((this.zipCode == null) || (this.zipCode.getString().length() == 0))) {
        return setError(26);
      }
      if (("STATE".equals(str)) && ((this.state == null) || (this.state.length() == 0))) {
        return setError(25);
      }
      if (("COUNTRY".equals(str)) && ((this.country == null) || (this.country.length() == 0) || (!validateCountry(paramHttpSession)))) {
        return setError(53);
      }
      if ("CUSTOMER_ID".equals(str))
      {
        if ((this.custId == null) || (this.custId.length() == 0)) {
          return setError(132);
        }
      }
      else
      {
        if (("PHONE".equals(str)) && ((this.phone == null) || (this.phone.getString().length() == 0))) {
          return setError(27);
        }
        if (("PHONE2".equals(str)) && ((this.phone2 == null) || (this.phone2.getString().length() == 0))) {
          return setError(27);
        }
        if (("DATAPHONE".equals(str)) && ((this.dataPhone == null) || (this.dataPhone.getString().length() == 0))) {
          return setError(120);
        }
        if (("EMAIL".equals(str)) && ((this.email == null) || (this.email.getString().length() == 0))) {
          return setError(31);
        }
        if (("SSN".equals(str)) && ((this.ssn == null) || (this.ssn.getString().length() == 0))) {
          return setError(48);
        }
        if (("TITLE".equals(str)) && ((this.title == null) || (this.title.length() == 0))) {
          return setError(32);
        }
        if (("GREETING".equals(str)) && ((this.greeting == null) || (this.greeting.length() == 0))) {
          return setError(33);
        }
        if ("USERNAME".equals(str))
        {
          if ((this.userName == null) || (this.userName.trim().length() == 0)) {
            return setError(1);
          }
          if (!this.userName.toLowerCase().equals(this.userName)) {
            return setError(1);
          }
          if (this.userName.length() < this.minUserNameLength) {
            return setError(66);
          }
          if (duplicateUserName(paramHttpSession)) {
            return setError(3512);
          }
          if (this.nextURL == this.serviceErrorURL) {
            return false;
          }
        }
        else
        {
          if (("PASSWORD".equals(str)) && ((this.newPassword == null) || (this.newPassword.length() == 0))) {
            return setError(3);
          }
          if (("NEW_PASSWORD".equals(str)) && ((this.newPassword == null) || (this.newPassword.length() == 0))) {
            return setError(3);
          }
          if (("CURRENT_PASSWORD".equals(str)) && ((this.currentPassword == null) || (this.currentPassword.length() == 0))) {
            return setError(3);
          }
          if (("PASSWORDCLUE".equals(str)) && ((this.passwordClue == null) || (this.passwordClue.length() == 0))) {
            return setError(70);
          }
          if (("PASSWORDREMINDER".equals(str)) && ((this.newPasswordReminder == null) || (this.newPasswordReminder.length() == 0))) {
            return setError(69);
          }
          if (("PASSWORDREMINDER".equals(str)) && (this.newPasswordReminder != null) && (!this.newPasswordReminder.equals(this.confirmPasswordReminder))) {
            return setError(141);
          }
          if (("PASSWORDCLUE2".equals(str)) && ((this.passwordClue2 == null) || (this.passwordClue2.length() == 0))) {
            return setError(138);
          }
          if (("PASSWORDREMINDER2".equals(str)) && ((this.newPasswordReminder2 == null) || (this.newPasswordReminder2.length() == 0))) {
            return setError(137);
          }
          if (("PASSWORDREMINDER2".equals(str)) && (this.newPasswordReminder2 != null) && (!this.newPasswordReminder2.equals(this.confirmPasswordReminder2))) {
            return setError(142);
          }
          if (("AFFILIATE_BANK_ID".equals(str)) && (this.affiliateBankID <= 0)) {
            return setError(4138);
          }
          if ((SERVICESPACKAGEID.equals(str)) && ((this.servicesPackageId == -1) || (this.servicesPackageId == 0))) {
            return setError(134);
          }
          if (("PASSWORDCLUE".equals(str)) && (this.passwordClue != null) && (this.passwordClue.length() > 0))
          {
            if (this.passwordClue.equals(this.passwordClue2)) {
              return setError(139);
            }
          }
          else if (("PASSWORDCLUE2".equals(str)) && (this.passwordClue2 != null) && (this.passwordClue2.length() > 0))
          {
            if (this.passwordClue2.equals(this.passwordClue)) {
              return setError(139);
            }
          }
          else if (("PASSWORD".equals(str)) || ("NEW_PASSWORD".equals(str)))
          {
            bool = verifyPassword(paramHttpSession);
            if (!bool) {
              return bool;
            }
          }
          else if ("BACKENDUSERINFO".equals(str))
          {
            bool = true;
            try
            {
              User localUser = UserAdmin.getUserInfoFromBackend(this.pb, getCustId(), (String)get("PROCESSOR_PIN"), new HashMap());
              if (localUser == null) {
                bool = false;
              }
            }
            catch (CSILException localCSILException)
            {
              bool = false;
            }
            if (!bool) {
              return setError(3548);
            }
          }
          else
          {
            if (("WORKPHONE".equals(str)) && ((this.phone2 == null) || (this.phone2.getString().length() == 0))) {
              return setError(151);
            }
            if (("CELLPHONE".equals(str)) && ((this.dataPhone == null) || (this.dataPhone.getString().length() == 0))) {
              return setError(152);
            }
          }
        }
      }
    }
    this.validate = null;
    return bool;
  }
  
  protected boolean validateState(HttpSession paramHttpSession)
  {
    if (this.state == null) {
      return setError(25);
    }
    return verifyState(paramHttpSession);
  }
  
  protected boolean validateCountry(HttpSession paramHttpSession)
  {
    if (this.country == null) {
      return setError(53);
    }
    return verifyState(paramHttpSession);
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    this.pb = ((SecureUser)paramHttpSession.getAttribute("SecureUser"));
    StringTokenizer localStringTokenizer = new StringTokenizer(this.verifyFormat, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      localObject = localStringTokenizer.nextToken().trim();
      if (("PASSWORD".equals(localObject)) || ("NEW_PASSWORD".equals(localObject)))
      {
        bool = verifyPassword(paramHttpSession);
        if (!bool) {
          return bool;
        }
      }
      else
      {
        String str1;
        if (("ZIPCODE".equals(localObject)) && (this.zipCode != null) && (this.zipCode.getString().length() > 0))
        {
          str1 = Util.validateZipCodeFormat(this.pb, this.country, this.zipCode.getString(), new HashMap());
          if (str1 == null) {
            return setError(26);
          }
          if (!str1.equals("")) {
            this.zipCode.setFormat(str1);
          }
        }
        else if (("PHONE".equals(localObject)) && (this.phone != null) && (this.phone.getString().length() > 0))
        {
          str1 = Util.validatePhoneFormat(this.pb, this.country, this.phone.getString(), new HashMap());
          if (str1 == null) {
            return setError(27);
          }
          if (!str1.equals("")) {
            this.phone.setFormat(str1);
          }
        }
        else if (("PHONE2".equals(localObject)) && (this.phone2 != null) && (this.phone2.getString().length() > 0))
        {
          str1 = Util.validatePhoneFormat(this.pb, this.country, this.phone2.getString(), new HashMap());
          if (str1 == null) {
            return setError(27);
          }
          if (!str1.equals("")) {
            this.phone2.setFormat(str1);
          }
        }
        else if (("DATAPHONE".equals(localObject)) && (this.dataPhone != null) && (this.dataPhone.getString().length() > 0))
        {
          str1 = Util.validatePhoneFormat(this.pb, this.country, this.dataPhone.getString(), new HashMap());
          if (str1 == null) {
            return setError(120);
          }
          if (!str1.equals("")) {
            this.dataPhone.setFormat(str1);
          }
        }
        else if (("FAXPHONE".equals(localObject)) && (this.faxPhone != null) && (this.faxPhone.getString().length() > 0))
        {
          str1 = Util.validatePhoneFormat(this.pb, this.country, this.faxPhone.getString(), new HashMap());
          if (str1 == null) {
            return setError(119);
          }
          if (!str1.equals("")) {
            this.faxPhone.setFormat(str1);
          }
        }
        else if (("SSN".equals(localObject)) && (this.ssn != null) && (this.ssn.getString().length() > 0))
        {
          str1 = Util.validateSSNFormat(this.pb, this.country, this.ssn.getString(), new HashMap());
          if (str1 == null) {
            return setError(48);
          }
          if (!str1.equals("")) {
            this.ssn.setFormat(str1);
          }
        }
        else
        {
          if (("EMAIL".equals(localObject)) && (this.email != null) && (this.email.getString().length() > 0) && (!this.email.isValid())) {
            return setError(31);
          }
          if (("VERIFY_EMAIL".equals(localObject)) && (this.email != null) && (this.verifyEmail != null) && (this.email.getString().length() > 0))
          {
            if (!this.email.getString().equals(this.verifyEmail)) {
              return setError(131);
            }
            if (!this.email.isValid()) {
              return setError(31);
            }
          }
          else
          {
            if (("VERIFY_EMAIL".equals(localObject)) && (this.verifyEmail != null) && (this.verifyEmail.length() > 0)) {
              return setError(131);
            }
            if (("STATE".equals(localObject)) && (this.state != null) && (this.state.length() > 0) && (!verifyState(paramHttpSession))) {
              return false;
            }
            if (("COUNTRY".equals(localObject)) && (this.country != null) && (this.country.length() > 0) && (!verifyCountry(paramHttpSession))) {
              return false;
            }
            if (("USERNAME".equals(localObject)) && (this.userName != null) && (this.userName.length() < this.minUserNameLength)) {
              return setError(1);
            }
            if ("BIRTH_DATE".equals(localObject))
            {
              int i;
              if (this._birthDate == null) {
                i = 0;
              } else if (jdMethod_for(this._birthDate)) {
                i = 1;
              } else {
                try
                {
                  DateTime localDateTime1 = new DateTime(this._birthDate, getLocale(), getDateFormat());
                  DateTime localDateTime2 = new DateTime(getLocale());
                  i = 1;
                  if (localDateTime1.compare(localDateTime2) > 0) {
                    i = 0;
                  }
                }
                catch (InvalidDateTimeException localInvalidDateTimeException)
                {
                  i = 0;
                }
              }
              if (i == 0) {
                return setError(135);
              }
            }
            else
            {
              if (("PAGER".equals(localObject)) && (!objectIsValid("PAGER", "PHONE"))) {
                return setError(27);
              }
              if ("CURRENT_PASSWORD".equals(localObject)) {
                return verifyCurrentPassword(paramHttpSession);
              }
              if ("GREETING".equals(localObject))
              {
                if ((this.greetingType != null) && (!this.greetingType.equals("4")) && (this.greeting != null) && (!this.greeting.equals(""))) {
                  return setError(140);
                }
              }
              else
              {
                String str2;
                if (("WORKPHONE".equals(localObject)) && (this.phone2 != null) && (this.phone2.getString().length() > 0))
                {
                  str2 = Util.validatePhoneFormat(this.pb, this.country, this.phone2.getString(), new HashMap());
                  if (str2 == null) {
                    return setError(151);
                  }
                  if (!str2.equals("")) {
                    this.phone2.setFormat(str2);
                  }
                }
                else if (("CELLPHONE".equals(localObject)) && (this.dataPhone != null) && (this.dataPhone.getString().length() > 0))
                {
                  str2 = Util.validatePhoneFormat(this.pb, this.country, this.dataPhone.getString(), new HashMap());
                  if (str2 == null) {
                    return setError(152);
                  }
                  if (!str2.equals("")) {
                    this.dataPhone.setFormat(str2);
                  }
                }
              }
            }
          }
        }
      }
    }
    Object localObject = this.verifyFormat.split(",");
    if ((jdMethod_for((String[])localObject, "PASSWORDCLUE")) && (jdMethod_for((String[])localObject, "PASSWORDREMINDER")))
    {
      if ((this.passwordClue != null) && (this.passwordClue.length() > 0) && ((this.passwordReminder == null) || (this.passwordReminder.length() == 0))) {
        return setError(69);
      }
      if ((this.passwordReminder != null) && (this.passwordReminder.length() > 0) && ((this.passwordClue == null) || (this.passwordClue.length() == 0))) {
        return setError(70);
      }
    }
    this.verifyFormat = null;
    return bool;
  }
  
  private boolean jdMethod_for(String[] paramArrayOfString, String paramString)
  {
    boolean bool = false;
    if (paramString == null) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equals(paramArrayOfString[i])) {
        return true;
      }
    }
    return bool;
  }
  
  protected boolean verifyCurrentPassword(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (!this.currentPassword.equals(this.password)) {
      bool = setError(4);
    }
    return bool;
  }
  
  protected boolean verifyPassword(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (aPasswordEntered())
    {
      if (!this.newPassword.equals(this.password2)) {
        bool = setError(5);
      }
      int i = PasswordUtil.validatePassword(this.newPassword, this._userType);
      if (i != 0) {
        bool = setError(i);
      }
    }
    return bool;
  }
  
  protected boolean aPasswordEntered()
  {
    return ((this.newPassword != null) && (this.newPassword.length() > 0)) || ((this.password2 != null) && (this.password2.length() > 0));
  }
  
  protected boolean verifyState(HttpSession paramHttpSession)
  {
    if (this.state == null) {
      return true;
    }
    StateProvinceDefns localStateProvinceDefns = null;
    try
    {
      localStateProvinceDefns = Util.getStatesForCountry(this.pb, getCountry(), null);
    }
    catch (CSILException localCSILException)
    {
      localStateProvinceDefns = null;
    }
    if (localStateProvinceDefns == null) {
      return i(paramHttpSession);
    }
    if (localStateProvinceDefns.isEmpty()) {
      return true;
    }
    Iterator localIterator = localStateProvinceDefns.iterator();
    while (localIterator.hasNext())
    {
      StateProvinceDefn localStateProvinceDefn = (StateProvinceDefn)localIterator.next();
      if (localStateProvinceDefn.getStateKey().equalsIgnoreCase(this.state)) {
        return true;
      }
    }
    return setError(25);
  }
  
  private boolean i(HttpSession paramHttpSession)
  {
    if (this.state == null) {
      return true;
    }
    Locale localLocale;
    ResourceBundle localResourceBundle;
    String str1;
    if ((this.o3 == null) && (this.o7 == null))
    {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
      if (localResourceBundle == null) {
        return setError(43);
      }
      str1 = localResourceBundle.getString("StateAbbr").toUpperCase();
      String str2 = localResourceBundle.getString("StateNames").toUpperCase();
      if (this.state.trim().length() < 2) {
        return setError(25);
      }
      if ((this.state.length() == 2) && (str1.indexOf(this.state.toUpperCase()) == -1)) {
        return setError(25);
      }
      if ((this.state.length() > 2) && (str2.indexOf(this.state.toUpperCase()) == -1)) {
        return setError(25);
      }
    }
    else if ((this.o3 != null) && (this.o7 != null))
    {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localResourceBundle = ResourceUtil.getBundle(this.o3, localLocale);
      if (localResourceBundle == null) {
        return setError(43);
      }
      str1 = null;
      try
      {
        str1 = localResourceBundle.getString(this.o7).toUpperCase();
      }
      catch (MissingResourceException localMissingResourceException)
      {
        return setError(43);
      }
      if (str1.indexOf(this.state.toUpperCase()) == -1) {
        return setError(25);
      }
    }
    else
    {
      return setError(43);
    }
    return true;
  }
  
  protected boolean verifyCountry(HttpSession paramHttpSession)
  {
    if (this.country == null) {
      return true;
    }
    Locale localLocale;
    ResourceBundle localResourceBundle;
    String str1;
    if ((this.o5 == null) && (this.o9 == null))
    {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
      if (localResourceBundle == null) {
        return setError(43);
      }
      str1 = localResourceBundle.getString("CountryAbbr").toUpperCase();
      String str2 = localResourceBundle.getString("CountryList").toUpperCase();
      String str3 = localResourceBundle.getString("CountryNames").toUpperCase();
      if (this.country.trim().length() < 2) {
        return setError(53);
      }
      if ((this.country.length() == 2) && (str1.indexOf(this.country.toUpperCase()) == -1)) {
        return setError(53);
      }
      if ((this.country.length() == 3) && (str2.indexOf(this.country.toUpperCase()) == -1)) {
        return setError(53);
      }
      if ((this.country.length() > 3) && (str3.indexOf(this.country.toUpperCase()) == -1)) {
        return setError(53);
      }
    }
    else if ((this.o5 != null) && (this.o9 != null))
    {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localResourceBundle = ResourceUtil.getBundle(this.o5, localLocale);
      if (localResourceBundle == null) {
        return setError(43);
      }
      str1 = null;
      try
      {
        str1 = localResourceBundle.getString(this.o9).toUpperCase();
      }
      catch (MissingResourceException localMissingResourceException)
      {
        return setError(43);
      }
      if (str1.indexOf(this.country.toUpperCase()) == -1) {
        return setError(53);
      }
    }
    else
    {
      return setError(43);
    }
    return true;
  }
  
  protected boolean duplicateUserName(HttpSession paramHttpSession)
  {
    User localUser1 = (User)paramHttpSession.getAttribute(this.oldUserSessionName);
    int i = 1;
    boolean bool = false;
    if ((localUser1 != null) && (localUser1.getUserName().equals(getUserName()))) {
      i = 0;
    }
    if (i != 0)
    {
      HashMap localHashMap = new HashMap();
      User localUser2 = new User((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localUser2.setUserName(this.userName);
      String str = (String)get("BANK_ID");
      localUser2.set("BANK_ID", str);
      localHashMap.put("USER", localUser2);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        bool = UserAdmin.userExists(localSecureUser, this.userName, str, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.nextURL = this.serviceErrorURL;
      }
    }
    return bool;
  }
  
  protected boolean objectIsValid(String paramString1, String paramString2)
  {
    Object localObject1;
    Object localObject2;
    if (paramString2.equals("DATETIME"))
    {
      localObject1 = getHash().get(paramString1);
      if ((localObject1 != null) && ((localObject1 instanceof String)) && (!jdMethod_for(localObject1))) {
        try
        {
          localObject1 = new DateTime((String)localObject1, this.locale);
        }
        catch (InvalidDateTimeException localInvalidDateTimeException)
        {
          return false;
        }
      }
      if ((localObject1 != null) && ((localObject1 instanceof DateTime)))
      {
        localObject2 = (DateTime)localObject1;
        if ((!((DateTime)localObject2).getString().equals("")) && (!((DateTime)localObject2).isValid())) {
          return false;
        }
      }
      else if ((localObject1 != null) && (!jdMethod_for(localObject1)))
      {
        return false;
      }
    }
    else if (paramString2.equals("PHONE"))
    {
      localObject1 = getHash().get(paramString1);
      if ((localObject1 != null) && ((localObject1 instanceof Phone)))
      {
        localObject2 = (Phone)localObject1;
        if ((!((Phone)localObject2).getString().equals("")) && (!((Phone)localObject2).isValid())) {
          return false;
        }
      }
      else if ((localObject1 != null) && (!jdMethod_for(localObject1)))
      {
        return false;
      }
    }
    return true;
  }
  
  private boolean jdMethod_for(Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof String))) {
      return ((String)paramObject).equals("");
    }
    return false;
  }
  
  protected boolean addUser(HttpSession paramHttpSession)
  {
    this.error = 0;
    boolean bool = false;
    HashMap localHashMap = new HashMap();
    try
    {
      UserAdmin.addUser(this.pb, this, localHashMap);
      if (getPrimarySecondary() == User.USER_TYPE_PRIMARY) {
        UserAdmin.registerUserWithBPW(this.pb, this, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      SecureUser localSecureUser = null;
      try
      {
        localSecureUser = getAddedSecureUser();
      }
      catch (Exception localException) {}
      if (localSecureUser != null) {
        setEntitlementGroupId(localSecureUser.getEntitlementID());
      }
      processEntitlements(this.pb, null);
      processLimits(this.pb);
      paramHttpSession.setAttribute(this.userSessionName, this);
      bool = true;
      addUserHistory(paramHttpSession, "Add", this, null);
      Users localUsers = (Users)paramHttpSession.getAttribute(this.usersSessionName);
      if (localUsers != null) {
        localUsers.add(this);
      }
    }
    return bool;
  }
  
  protected void processEntitlements(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker)
  {
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    try
    {
      localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupId());
      localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements3 = new com.ffusion.csil.beans.entitlements.Entitlements();
    Enumeration localEnumeration = this.o8.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = this.o8.getProperty(str1);
      if (str2 != null)
      {
        Entitlement localEntitlement1 = new Entitlement();
        localEntitlement1.setOperationName(str1);
        int i = 0;
        Iterator localIterator = localEntitlements1.iterator();
        while ((localIterator.hasNext()) && (i == 0))
        {
          Entitlement localEntitlement2 = (Entitlement)localIterator.next();
          if (localEntitlement1.equals(localEntitlement2)) {
            i = 1;
          }
        }
        if ((i != 0) && (str2.equalsIgnoreCase("true")))
        {
          localEntitlements3.add(localEntitlement1);
          if (paramHistoryTracker != null) {
            paramHistoryTracker.logEntitlementDelete(localEntitlement1, localEntitlementTypePropertyLists);
          }
        }
        if ((i == 0) && (str2.equalsIgnoreCase("false")))
        {
          localEntitlements2.add(localEntitlement1);
          if (this.o2 != null) {
            this.o2.remove(str1);
          }
          if (paramHistoryTracker != null) {
            paramHistoryTracker.logEntitlementAdd(localEntitlement1, localEntitlementTypePropertyLists);
          }
        }
      }
    }
    if (localEntitlements2.size() > 0) {
      try
      {
        com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupId(), localEntitlements2);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
    }
    if (localEntitlements3.size() > 0) {
      try
      {
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupId(), localEntitlements3);
      }
      catch (CSILException localCSILException3)
      {
        this.error = MapError.mapError(localCSILException3);
      }
    }
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public String getValidate()
  {
    return this.validate;
  }
  
  public void setVerifyFormat(String paramString)
  {
    this.verifyFormat = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.verifyFormat = paramString.toUpperCase();
    }
  }
  
  public String getVerifyFormat()
  {
    return this.verifyFormat;
  }
  
  public void setValidInputURL(String paramString)
  {
    this.validInputURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public String getCurrentPassword()
  {
    return this.currentPassword;
  }
  
  public String getCurrentPasswordForDisplay()
  {
    return (this.currentPassword == null) || (this.currentPassword.length() == 0) ? "" : "****";
  }
  
  public void setCurrentPassword(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.currentPassword = paramString;
    }
  }
  
  public String getPasswordForDisplay()
  {
    return (this.password == null) || (this.password.length() == 0) ? "" : "****";
  }
  
  public void setPassword(String paramString)
  {
    if (!"****".equals(paramString))
    {
      this.newPassword = paramString;
      this.password = paramString;
    }
  }
  
  public String getNewPassword()
  {
    return this.newPassword;
  }
  
  public String getNewPasswordForDisplay()
  {
    return (this.newPassword == null) || (this.newPassword.length() == 0) ? "" : "****";
  }
  
  public void setNewPassword(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.newPassword = paramString;
    }
  }
  
  public void setPassword2(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.password2 = paramString;
    }
  }
  
  public void setConfirmPassword(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.password2 = paramString;
    }
  }
  
  public String getConfirmPassword()
  {
    return this.password2;
  }
  
  public String getConfirmPasswordForDisplay()
  {
    return (this.password2 == null) || (this.password2.length() == 0) ? "" : "****";
  }
  
  public String getConfirmPasswordReminder()
  {
    return this.confirmPasswordReminder;
  }
  
  public void setConfirmPasswordReminder(String paramString)
  {
    this.confirmPasswordReminder = paramString;
  }
  
  public String getConfirmPasswordReminder2()
  {
    return this.confirmPasswordReminder2;
  }
  
  public void setConfirmPasswordReminder2(String paramString)
  {
    this.confirmPasswordReminder2 = paramString;
  }
  
  public String getNewPasswordReminder()
  {
    return this.newPasswordReminder;
  }
  
  public void setNewPasswordReminder(String paramString)
  {
    this.newPasswordReminder = paramString;
  }
  
  public String getNewPasswordReminder2()
  {
    return this.newPasswordReminder2;
  }
  
  public void setNewPasswordReminder2(String paramString)
  {
    this.newPasswordReminder2 = paramString;
  }
  
  public void setOldPasswordReminder(String paramString)
  {
    this.oldPasswordReminder = paramString;
  }
  
  public void setOldPasswordReminder2(String paramString)
  {
    this.oldPasswordReminder2 = paramString;
  }
  
  public void setMinLetterLength(String paramString)
  {
    this.minLetters = Integer.valueOf(paramString).intValue();
  }
  
  public void setMinNumberLength(String paramString)
  {
    this.minNumbers = Integer.valueOf(paramString).intValue();
  }
  
  public void setMinPasswordLength(String paramString)
  {
    this.minPasswordLength = Integer.parseInt(paramString);
  }
  
  public void setMinPasswordLength(int paramInt)
  {
    this.minPasswordLength = paramInt;
  }
  
  public void setMinUserNameLength(String paramString)
  {
    this.minUserNameLength = Integer.parseInt(paramString);
  }
  
  public void setMinUserNameLength(int paramInt)
  {
    this.minUserNameLength = paramInt;
  }
  
  public void setNoSpecialCharacters(String paramString)
  {
    this.noSpecialChars = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected int countNumbers(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isDigit(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  protected int countLetters(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isLetter(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  protected boolean hasSpecialChars(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (!Character.isLetterOrDigit(paramString.charAt(j))) {
        return true;
      }
    }
    return false;
  }
  
  protected int addAdditionalSettings()
  {
    HashMap localHashMap = new HashMap(1);
    try
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, this.additionalSettingsFile);
      String str = Strings.streamToString(localInputStream);
      if (this.additionalSettingsNameSpace == null) {
        this.additionalSettingsNameSpace = "PORTAL_SETTINGS";
      }
      try
      {
        SecureUser localSecureUser = getAddedSecureUser();
        localHashMap.put("USER", localSecureUser);
        UserAdmin.addAdditionalData(this.pb, this.additionalSettingsNameSpace, str, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.nextURL = this.serviceErrorURL;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("AddUser Task Exception: ", localThrowable);
      this.error = 16002;
    }
    return this.error;
  }
  
  protected SecureUser getAddedSecureUser()
    throws Exception
  {
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setBankID(getBankId());
    localSecureUser.setUserName(this.userName);
    localSecureUser.setProfileID(this.id);
    localSecureUser.setUserType(1);
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    localEntitlementGroupMember = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember);
    localSecureUser.setEntitlementID(localEntitlementGroupMember.getEntitlementGroupId());
    return localSecureUser;
  }
  
  public void setAdditionalSettingsFile(String paramString)
  {
    this.additionalSettingsFile = paramString;
  }
  
  public void setAdditionalSettingsNameSpace(String paramString)
  {
    this.additionalSettingsNameSpace = paramString;
  }
  
  public void setAddUserFromSession(String paramString)
  {
    this.addUserFromSession = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUserSessionName(String paramString)
  {
    this.userSessionName = paramString;
  }
  
  public void setUsersSessionName(String paramString)
  {
    this.usersSessionName = paramString;
  }
  
  public void setUserServiceName(String paramString) {}
  
  public void setPropName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.o4 = paramString;
    }
  }
  
  public void setPropData(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.o8.setProperty(this.o4, paramString);
    } else {
      this.o8.remove(this.o4);
    }
  }
  
  public String getPropData()
  {
    return this.o8.getProperty(this.o4, "");
  }
  
  public void setVerifyEmail(String paramString)
  {
    if (paramString == null) {
      this.verifyEmail = "";
    } else {
      this.verifyEmail = paramString;
    }
  }
  
  public String getVerifyEmail()
  {
    return this.verifyEmail;
  }
  
  public void set(User paramUser)
  {
    super.set(paramUser);
    Object localObject = paramUser.get("BIRTH_DATE");
    if (localObject != null)
    {
      if ((localObject instanceof String)) {
        try
        {
          localObject = new DateTime((String)localObject, getLocale(), "MM/dd/yyyy");
        }
        catch (InvalidDateTimeException localInvalidDateTimeException) {}
      }
      if ((localObject instanceof DateTime))
      {
        DateTime localDateTime = (DateTime)localObject;
        localDateTime.setFormat(getDateFormat() == null ? "MM/dd/yyyy" : getDateFormat());
        this._birthDate = localDateTime.toString();
      }
    }
  }
  
  public void setBirthDate(String paramString)
  {
    this._birthDate = paramString;
    Object localObject = paramString;
    if ((this._birthDate != null) && (!this._birthDate.trim().equals(""))) {
      try
      {
        localObject = new DateTime(this._birthDate, getLocale(), getDateFormat());
      }
      catch (InvalidDateTimeException localInvalidDateTimeException) {}
    }
    put("BIRTH_DATE", localObject);
  }
  
  public String getBirthDate()
  {
    Object localObject = get("BIRTH_DATE");
    if (localObject == null) {
      localObject = "";
    } else if ((localObject instanceof String)) {
      try
      {
        localObject = new DateTime((String)localObject, getLocale(), "MM/dd/yyyy");
      }
      catch (InvalidDateTimeException localInvalidDateTimeException) {}
    }
    if ((localObject instanceof DateTime)) {
      ((DateTime)localObject).setFormat(getDateFormat());
    }
    return localObject.toString();
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this._userType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._userType = 1;
    }
  }
  
  protected boolean addUserHistory(HttpSession paramHttpSession, String paramString, User paramUser1, User paramUser2)
  {
    return addUserHistory(paramHttpSession, paramString, paramUser1, paramUser2, null);
  }
  
  protected boolean addUserHistory(HttpSession paramHttpSession, String paramString, User paramUser1, User paramUser2, Histories paramHistories)
  {
    boolean bool = true;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, paramUser1.getId());
    if (paramString.equals("Add"))
    {
      paramUser1.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      if (paramHistories != null) {
        localHistoryTracker.getHistories().addAll(paramHistories);
      }
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException1)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for AddUser: " + localProfileException1.toString());
      }
      if (this.error == 0) {
        paramHttpSession.setAttribute("UserHistories", localHistoryTracker.getHistories());
      } else {
        bool = false;
      }
    }
    else
    {
      paramUser1.logChanges(localHistoryTracker, paramUser2, localHistoryTracker.buildLocalizableComment(17));
      if (paramHistories != null) {
        localHistoryTracker.getHistories().addAll(paramHistories);
      }
      if (localHistoryTracker.getHistories().size() > 0)
      {
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException2)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for AddUser: " + localProfileException2.toString());
        }
        if (this.error == 0)
        {
          Histories localHistories = (Histories)paramHttpSession.getAttribute("UserHistories");
          if (localHistories != null)
          {
            localHistories.addAll(localHistoryTracker.getHistories());
            paramHttpSession.setAttribute("UserHistories", localHistories);
          }
        }
        else
        {
          bool = false;
        }
      }
    }
    return bool;
  }
  
  public static History buildHistory(HttpSession paramHttpSession, User paramUser, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    DateTime localDateTime = new DateTime(localLocale);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str1 = "" + localSecureUser.getProfileID();
    String str2 = localSecureUser.getUserName();
    int i = 1;
    BankEmployee localBankEmployee = (BankEmployee)paramHttpSession.getAttribute("BankEmployee");
    if (localBankEmployee != null)
    {
      str1 = localBankEmployee.getId();
      i = 0;
      str2 = localBankEmployee.getFirstName() + " " + localBankEmployee.getLastName();
    }
    else if (localSecureUser.getAgent() != null)
    {
      localObject = localSecureUser.getAgent();
      str1 = "" + ((SecureUser)localObject).getProfileID();
      i = 0;
    }
    Object localObject = new History(localLocale);
    ((History)localObject).setId(paramUser.getId());
    ((History)localObject).setIdType(1);
    ((History)localObject).setModifierId(str1);
    ((History)localObject).setModifierName(str2);
    ((History)localObject).setModifierType(i);
    ((History)localObject).setDateChanged(localDateTime);
    String str3 = null;
    try
    {
      str3 = ResourceUtil.getString(paramString1, "com.ffusion.beans.user.resources", localLocale);
    }
    catch (Exception localException1) {}
    if (str3 == null) {
      ((History)localObject).setDataChanged(paramString1);
    } else {
      ((History)localObject).setDataChanged(str3);
    }
    String str4 = null;
    try
    {
      str4 = ResourceUtil.getString(paramString1 + "." + paramString2, "com.ffusion.beans.user.resources", localLocale);
    }
    catch (Exception localException2) {}
    String str5 = null;
    try
    {
      str5 = ResourceUtil.getString(paramString1 + "." + paramString3, "com.ffusion.beans.user.resources", localLocale);
    }
    catch (Exception localException3) {}
    if (str4 != null) {
      ((History)localObject).setOldValue(str4);
    } else {
      ((History)localObject).setOldValue(paramString2);
    }
    if (str5 != null) {
      ((History)localObject).setNewValue(str5);
    } else {
      ((History)localObject).setNewValue(paramString3);
    }
    if ((paramString4 != null) && (paramString4.length() > 0)) {
      ((History)localObject).setComment(paramString4);
    }
    return localObject;
  }
  
  protected static void convertEmptyStringsToNull(User paramUser)
  {
    String str = "";
    if (paramUser != null)
    {
      if ("".equals(paramUser.getPasswordReminder())) {
        paramUser.setPasswordReminder(null);
      }
      if ("".equals(paramUser.getTitle())) {
        paramUser.setTitle(null);
      }
      if ("".equals(paramUser.getGreeting())) {
        paramUser.setGreeting(null);
      }
    }
  }
  
  protected void checkPasswordRemindersEntered()
  {
    if (((this.newPasswordReminder == null) || (this.newPasswordReminder.length() == 0)) && ((this.confirmPasswordReminder == null) || (this.confirmPasswordReminder.length() == 0))) {
      this.passwordReminder = this.oldPasswordReminder;
    } else {
      this.passwordReminder = this.newPasswordReminder;
    }
    if (((this.newPasswordReminder2 == null) || (this.newPasswordReminder2.length() == 0)) && ((this.confirmPasswordReminder2 == null) || (this.confirmPasswordReminder2.length() == 0))) {
      this.passwordReminder2 = this.oldPasswordReminder2;
    } else {
      this.passwordReminder2 = this.newPasswordReminder2;
    }
  }
  
  public void setCurrentPeriod(String paramString)
  {
    this.pa = paramString;
  }
  
  public void setCurrentEntitlement(String paramString)
  {
    this.o6 = paramString;
  }
  
  public void setLimitData(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      paramString = "";
    }
    HashMap localHashMap = (HashMap)this.o2.get(this.o6);
    if (localHashMap == null)
    {
      localHashMap = new HashMap(6);
      this.o2.put(this.o6, localHashMap);
    }
    a locala = (a)localHashMap.get(this.pa);
    if (locala == null) {
      locala = new a(null);
    }
    locala.a(paramString);
    localHashMap.put(this.pa, locala);
  }
  
  public String getLimitData()
  {
    if ((this.pa == null) || (this.o6 == null)) {
      return "";
    }
    HashMap localHashMap = (HashMap)this.o2.get(this.o6);
    if (localHashMap == null) {
      return "";
    }
    a locala = (a)localHashMap.get(this.pa);
    if ((locala != null) && (locala.a() != null)) {
      return locala.a();
    }
    return "";
  }
  
  public void setLimitAllowApproval(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      paramString = "";
    }
    HashMap localHashMap = (HashMap)this.o2.get(this.o6);
    if (localHashMap == null)
    {
      localHashMap = new HashMap(6);
      this.o2.put(this.o6, localHashMap);
    }
    a locala = (a)localHashMap.get(this.pa);
    if (locala == null) {
      locala = new a(null);
    }
    locala.a(Boolean.valueOf(paramString).booleanValue());
    localHashMap.put(this.pa, locala);
  }
  
  public String getLimitAllowApproval()
  {
    if ((this.pa == null) || (this.o6 == null)) {
      return "";
    }
    HashMap localHashMap = (HashMap)this.o2.get(this.o6);
    if (localHashMap == null) {
      return "";
    }
    a locala = (a)localHashMap.get(this.pa);
    if (locala != null) {
      return String.valueOf(locala.jdMethod_if());
    }
    return "";
  }
  
  protected void initializeLimits()
  {
    try
    {
      Limits localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(getEntitlementGroupId());
      for (int i = 0; i < localLimits.size(); i++)
      {
        Limit localLimit = (Limit)localLimits.get(i);
        if (localLimit.getEntitlement().getObjectType() == null)
        {
          HashMap localHashMap = (HashMap)this.o2.get(localLimit.getEntitlement().getOperationName());
          if (localHashMap == null)
          {
            localHashMap = new HashMap(6);
            this.o2.put(localLimit.getEntitlement().getOperationName(), localHashMap);
          }
          localHashMap.put("" + localLimit.getPeriod(), new a(localLimit.getData(), localLimit.isAllowedApproval(), null));
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Unable to get ALL Limit List: " + localException.toString());
    }
  }
  
  protected void processLimits(SecureUser paramSecureUser)
  {
    Iterator localIterator1 = this.o2.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      HashMap localHashMap = (HashMap)this.o2.get(str1);
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        int i = Integer.parseInt(str2);
        a locala = (a)localHashMap.get(str2);
        String str3 = null;
        boolean bool = false;
        if (locala != null)
        {
          str3 = locala.a();
          bool = locala.jdMethod_if();
        }
        if (!Currency.isValid(str3, this.locale))
        {
          this.error = 16117;
          break;
        }
        try
        {
          Entitlement localEntitlement = new Entitlement(str1, null, null);
          Limit localLimit1 = new Limit();
          localLimit1.setEntitlement(localEntitlement);
          localLimit1.setObjectType("");
          localLimit1.setObjectId("");
          localLimit1.setPeriod(i);
          Limits localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupId(), localLimit1, null);
          localLimit1.setObjectType(null);
          localLimit1.setObjectId(null);
          Iterator localIterator3 = localLimits.iterator();
          localLimit1.setRunningTotalType('U');
          Object localObject1 = null;
          Object localObject2 = null;
          Limit localLimit2 = null;
          if (localIterator3.hasNext())
          {
            localLimit2 = (Limit)localIterator3.next();
            if ((str3 == null) || (str3.length() <= 0))
            {
              com.ffusion.csil.core.Entitlements.deleteGroupLimit(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localLimit2);
            }
            else
            {
              localLimit1.setData(str3);
              localLimit1.setAllowApproval(bool);
              if ((localLimit1.getAmount().toString().equals(localLimit2.getAmount().toString())) && (localLimit1.getEntitlement().equals(localLimit2.getEntitlement())) && (localLimit1.getAllowApproval() == localLimit2.getAllowApproval())) {
                continue;
              }
              localLimit2.setData(str3);
              localLimit2.setAllowApproval(bool);
              localLimit2 = com.ffusion.csil.core.Entitlements.modifyGroupLimit(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localLimit2);
            }
          }
          else if ((str3 != null) && (str3.length() > 0))
          {
            localLimit2 = localLimit1;
            localLimit2.setGroupId(getEntitlementGroupId());
            localLimit2.setData(str3);
            localLimit2.setAllowApproval(bool);
            localLimit2 = com.ffusion.csil.core.Entitlements.addGroupLimit(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localLimit2);
          }
          this.nextURL = this.successURL;
        }
        catch (CSILException localCSILException)
        {
          localCSILException.printStackTrace(System.out);
        }
      }
    }
  }
  
  private class a
  {
    private String a;
    private boolean jdField_if;
    
    private a()
    {
      this.a = "";
      this.jdField_if = false;
    }
    
    private a(String paramString, boolean paramBoolean)
    {
      this.a = paramString;
      this.jdField_if = paramBoolean;
    }
    
    private void a(String paramString)
    {
      this.a = paramString;
    }
    
    private void a(boolean paramBoolean)
    {
      this.jdField_if = paramBoolean;
    }
    
    private String a()
    {
      return this.a;
    }
    
    private boolean jdField_if()
    {
      return this.jdField_if;
    }
    
    a(AddUser.1 param1)
    {
      this();
    }
    
    a(String paramString, boolean paramBoolean, AddUser.1 param1)
    {
      this(paramString, paramBoolean);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AddUser
 * JD-Core Version:    0.7.0.1
 */