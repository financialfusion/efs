package com.ffusion.tasks.business;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.services.Alerts;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBusiness
  extends com.ffusion.beans.business.Business
  implements BusinessTask
{
  public static final int MAX_BUS_NAME_SIZE = 32;
  protected int maxBusinessNameLength = 32;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected String verifyFormat;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int error = 0;
  private String gd = this.successURL;
  private Properties gj = new Properties();
  private String gi = "";
  private String gb = "";
  private String gf = "";
  private String go = "";
  private String ge = "";
  private String gm = "";
  protected boolean addBusinessToBPWOnActivate = false;
  protected boolean enforceUnqiueCIF = true;
  protected boolean _autoEntitleACHPermissions = false;
  protected boolean _autoEntitleFlag = true;
  public static int MAX_CIF_LEN = 12;
  private String gc = "";
  private Properties f8 = new Properties();
  private static final String gg = "com.ffusion.util.logging.audit.task";
  private static final String gl = "AuditMessage_ModifyBusiness_1";
  private static final String gn = "AuditMessage_ModifyBusiness_2";
  private static final String gk = "AuditMessage_ModifyBusiness_3";
  private static final String gh = "AuditMessage_ModifyBusiness_4";
  private static final String f9 = "PRIMARY_CONTACT";
  private static final String ga = "SECONDARY_CONTACT";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    boolean bool1 = true;
    this.gd = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.initFlag) {
      bool1 = init(localHttpSession);
    }
    if (bool1)
    {
      boolean bool2 = false;
      try
      {
        bool2 = validateInput(localHttpSession);
      }
      catch (CSILException localCSILException)
      {
        MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
      if (bool2)
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          if (!jdMethod_case(localHttpSession)) {
            return this.gd;
          }
          TransactionLimits localTransactionLimits = (TransactionLimits)localHttpSession.getAttribute("BusinessTransactionLimits");
          if (localTransactionLimits != null)
          {
            Enumeration localEnumeration = this.gj.keys();
            while (localEnumeration.hasMoreElements())
            {
              String str1 = (String)localEnumeration.nextElement();
              String str2 = this.gj.getProperty(str1);
              localTransactionLimits.setLimitData(str1, str2, 1);
            }
          }
          setTransactionLimits(localTransactionLimits);
          jdMethod_byte(localHttpSession);
        }
      }
      else if ((this.gd == null) || (this.gd.equals(this.successURL))) {
        this.gd = this.taskErrorURL;
      }
    }
    else
    {
      this.gd = this.taskErrorURL;
    }
    return this.gd;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    com.ffusion.beans.business.Business localBusiness1 = (com.ffusion.beans.business.Business)paramHttpSession.getAttribute("Business");
    if (localBusiness1 == null)
    {
      this.error = 4104;
      return false;
    }
    set(localBusiness1);
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    com.ffusion.beans.business.Business localBusiness2 = new com.ffusion.beans.business.Business();
    localBusiness2.set(localBusiness1);
    paramHttpSession.setAttribute("OldBusiness", localBusiness2);
    this.initFlag = false;
    return true;
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
    return bool;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap(2);
    if (this.validate == null) {
      return bool;
    }
    Object localObject2;
    Object localObject3;
    if (this.validate.indexOf(BUSINESSNAME) != -1)
    {
      if ((this.businessName == null) || (this.businessName.trim().length() == 0)) {
        return setError(4100);
      }
      if (this.businessName.length() > this.maxBusinessNameLength)
      {
        bool = setError(4150);
        return bool;
      }
      localObject1 = (com.ffusion.beans.business.Business)paramHttpSession.getAttribute("OldBusiness");
      int i = 1;
      if ((localObject1 != null) && (((com.ffusion.beans.business.Business)localObject1).getBusinessName().equals(getBusinessName()) == true)) {
        i = 0;
      }
      if (i == 1)
      {
        localObject2 = new Businesses((Locale)paramHttpSession.getAttribute("java.util.Locale"));
        localObject3 = new com.ffusion.beans.business.Business((Locale)paramHttpSession.getAttribute("java.util.Locale"));
        ((com.ffusion.beans.business.Business)localObject3).setBankId(localSecureUser.getBankID());
        ((com.ffusion.beans.business.Business)localObject3).setBusinessName(this.businessName);
        try
        {
          localHashMap.put("BUSINESSES", localObject2);
          localHashMap.put("UseLikeComparison", "false");
          int j = com.ffusion.csil.core.Business.getFilteredBusinessesCount(localSecureUser, (com.ffusion.beans.business.Business)localObject3, null, localHashMap);
          if (j > 0) {
            return setError(4102);
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          this.gd = this.serviceErrorURL;
          return false;
        }
      }
    }
    if ((this.validate.indexOf("PRIMARY_CONTACT") != -1) && ((this.gb.equals("")) || (this.gb.equals("none")))) {
      return setError(4164);
    }
    if ((this.validate.indexOf("SECONDARY_CONTACT") != -1) && (this.gb.equals(this.gf))) {
      return setError(4165);
    }
    Object localObject1 = getCustId();
    if (this.validate.indexOf("CUST_ID") != -1)
    {
      if ((localObject1 != null) && (((String)localObject1).trim().length() > 20)) {
        return setError(4139);
      }
      if ((SignonSettings.allowDuplicateUserNames()) && ((localObject1 == null) || (((String)localObject1).trim().length() == 0))) {
        return setError(4139);
      }
    }
    if (this.validate.indexOf(TRANSACTIONLIMITS) != -1)
    {
      Enumeration localEnumeration = this.gj.keys();
      while (localEnumeration.hasMoreElements())
      {
        localObject2 = (String)localEnumeration.nextElement();
        localObject3 = this.gj.getProperty((String)localObject2);
        if (!Currency.isValid((String)localObject3, this.locale)) {
          return setError(4135);
        }
      }
    }
    bool = otherValidationCheck(paramHttpSession);
    this.validate = null;
    return bool;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.verifyFormat == null) {
      return bool;
    }
    bool = otherVerifyFormatCheck(paramHttpSession);
    this.verifyFormat = null;
    return bool;
  }
  
  protected boolean validateOtherInput(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate != null) {
      bool = otherValidationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = otherVerifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    return bool;
  }
  
  protected boolean otherValidationCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate == null) {
      return bool;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap(2);
    String str1 = getCustId();
    if ((str1 != null) && (str1.trim().length() > 0)) {
      try
      {
        localHashMap = new HashMap();
        if (!com.ffusion.csil.core.Business.uniqueCustId(localSecureUser, this, localHashMap)) {
          return setError(4144);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.gd = this.serviceErrorURL;
        return false;
      }
    }
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", this.locale);
    String str2 = localResourceBundle.getString("StateAbbr").toUpperCase();
    String str3 = localResourceBundle.getString("StateNames").toUpperCase();
    if ((this.validate.indexOf("STREET") != -1) && ((this.street == null) || (this.street.trim().length() == 0))) {
      return setError(23);
    }
    if ((this.validate.indexOf("STREET2") != -1) && ((this.street2 == null) || (this.street2.trim().length() == 0))) {
      return setError(23);
    }
    if ((this.validate.indexOf("CITY") != -1) && ((this.city == null) || (this.city.trim().length() == 0))) {
      return setError(24);
    }
    if ((this.validate.indexOf("STATE") != -1) && ((this.state == null) || (this.state.trim().length() < 2))) {
      return setError(25);
    }
    if ((this.validate.indexOf("STATE") != -1) && (this.state.length() == 2) && (str2.indexOf(this.state.toUpperCase()) == -1)) {
      return setError(25);
    }
    if ((this.validate.indexOf("STATE") != -1) && (this.state.length() > 2) && (str3.indexOf(this.state.toUpperCase()) == -1)) {
      return setError(25);
    }
    if ((this.validate.indexOf("ZIPCODE") != -1) && ((this.zipCode == null) || (this.zipCode.getString().length() == 0))) {
      return setError(26);
    }
    if ((this.validate.indexOf("PHONE") != -1) && ((this.phone == null) || (this.phone.getString().length() == 0))) {
      return setError(27);
    }
    if ((this.validate.indexOf("PHONE2") != -1) && ((this.phone2 == null) || (this.phone2.getString().length() == 0))) {
      return setError(27);
    }
    if ((this.validate.indexOf("FAXPHONE") != -1) && ((this.faxPhone == null) || (this.faxPhone.getString().length() == 0))) {
      return setError(119);
    }
    if ((this.validate.indexOf("EMAIL") != -1) && ((this.email == null) || (this.email.getString().length() == 0))) {
      return setError(31);
    }
    if ((this.validate.indexOf(BUSINESSCIF) != -1) && ((this.businessCIF == null) || (this.businessCIF.trim().length() == 0) || (this.businessCIF.trim().length() > MAX_CIF_LEN))) {
      return setError(4132);
    }
    if ((this.validate.indexOf(PERSONALCIF) != -1) && ((this.personalCIF == null) || (this.personalCIF.trim().length() == 0) || (this.personalCIF.trim().length() > MAX_CIF_LEN))) {
      return setError(4133);
    }
    if ((this.validate.indexOf(SERVICESPACKAGEID) != -1) && (this.servicesPackageId == 0)) {
      return setError(4134);
    }
    if ((this.validate.indexOf(AFFILIATE_BANK_ID) != -1) && (this.affiliateBankID <= 0)) {
      return setError(4138);
    }
    if ((this.validate.indexOf(ACHCREDITLEADDAYS) != -1) && ((this.achCreditLeadDays < 1) || (this.achCreditLeadDays > 5))) {
      return setError(4147);
    }
    if ((this.validate.indexOf(ACHDEBITLEADDAYS) != -1) && ((this.achDebitLeadDays < 1) || (this.achDebitLeadDays > 5))) {
      return setError(4148);
    }
    if ((this.validate.indexOf(LOCATIONID_PLACEMENT) != -1) && (this.locationIdPlacement != 1) && (this.locationIdPlacement != 2)) {
      return setError(4163);
    }
    this.validate = null;
    return bool;
  }
  
  protected boolean otherVerifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.verifyFormat == null) {
      return bool;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap(2);
    String str1 = getCustId();
    if ((str1 != null) && (str1.trim().length() > 0)) {
      try
      {
        localHashMap = new HashMap();
        if (!com.ffusion.csil.core.Business.uniqueCustId(localSecureUser, this, localHashMap)) {
          return setError(4144);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.gd = this.serviceErrorURL;
        return false;
      }
    }
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", this.locale);
    String str2 = localResourceBundle.getString("StateAbbr").toUpperCase();
    String str3 = localResourceBundle.getString("StateNames").toUpperCase();
    if ((this.verifyFormat.indexOf("ZIPCODE") != -1) && (this.zipCode != null) && (this.zipCode.getString().length() > 0) && (!jdMethod_int(localSecureUser))) {
      return setError(26);
    }
    if ((this.verifyFormat.indexOf("PHONE") != -1) && (this.phone != null) && (this.phone.getString().length() > 0) && (!jdMethod_int(localSecureUser, this.phone))) {
      return setError(27);
    }
    if ((this.verifyFormat.indexOf("PHONE2") != -1) && (this.phone2 != null) && (this.phone2.getString().length() > 0) && (!jdMethod_int(localSecureUser, this.phone2))) {
      return setError(27);
    }
    if ((this.verifyFormat.indexOf("FAXPHONE") != -1) && (this.faxPhone != null) && (this.faxPhone.getString().length() > 0) && (!jdMethod_int(localSecureUser, this.faxPhone))) {
      return setError(119);
    }
    if ((this.verifyFormat.indexOf("EMAIL") != -1) && (this.email != null) && (this.email.getString().length() > 0) && (!this.email.isValid())) {
      return setError(31);
    }
    if ((this.verifyFormat.indexOf(BUSINESSCIF) != -1) && (this.businessCIF != null) && (this.businessCIF.trim().length() > MAX_CIF_LEN)) {
      return setError(4132);
    }
    if ((this.verifyFormat.indexOf(PERSONALCIF) != -1) && (this.personalCIF != null) && (this.personalCIF.trim().length() > MAX_CIF_LEN)) {
      return setError(4133);
    }
    if ((this.verifyFormat.indexOf(SERVICESPACKAGEID) != -1) && (this.servicesPackageId == 0)) {
      return setError(4134);
    }
    if ((this.verifyFormat.indexOf(AFFILIATE_BANK_ID) != -1) && (this.affiliateBankID <= 0)) {
      return setError(4138);
    }
    if ((this.verifyFormat.indexOf(ACHCREDITLEADDAYS) != -1) && ((this.achCreditLeadDays < 1) || (this.achCreditLeadDays > 5))) {
      return setError(4147);
    }
    if ((this.verifyFormat.indexOf(ACHDEBITLEADDAYS) != -1) && ((this.achDebitLeadDays < 1) || (this.achDebitLeadDays > 5))) {
      return setError(4148);
    }
    this.verifyFormat = null;
    return bool;
  }
  
  private boolean jdMethod_int(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = Util.validateZipCodeFormat(paramSecureUser, null, this.zipCode.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    this.zipCode.setFormat(str);
    return true;
  }
  
  private boolean jdMethod_int(SecureUser paramSecureUser, Phone paramPhone)
    throws CSILException
  {
    String str = Util.validatePhoneFormat(paramSecureUser, null, paramPhone.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    paramPhone.setFormat(str);
    return true;
  }
  
  private void jdMethod_byte(HttpSession paramHttpSession)
  {
    String str1 = null;
    try
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      com.ffusion.beans.business.Business localBusiness1 = (com.ffusion.beans.business.Business)paramHttpSession.getAttribute("OldBusiness");
      if (localBusiness1 == null)
      {
        this.error = 4104;
        this.gd = this.taskErrorURL;
        return;
      }
      if (this.enforceUnqiueCIF) {
        localHashMap.put("CheckCIFUnique", "true");
      }
      localHashMap.put("AUTOENTITLE_FLAG_KEY", Boolean.valueOf(this._autoEntitleFlag));
      localHashMap.put("PRIMARY_CONTACT_ID", this.gb);
      localHashMap.put("SECONDARY_CONTACT_ID", this.gf);
      localHashMap.put("OLD_PRIMARY_CONTACT_ID", this.go);
      localHashMap.put("OLD_SECONDARY_CONTACT_ID", this.ge);
      com.ffusion.beans.business.Business localBusiness2 = com.ffusion.csil.core.Business.modifyBusiness(localSecureUser, this, localBusiness1, localHashMap);
      Object localObject1;
      Object localObject2;
      if ((!this.gb.equals(this.go)) || (!this.gf.equals(this.ge)))
      {
        localObject1 = new HistoryTracker(localSecureUser, 2, getId());
        String str2 = ModifyBusiness.class.getName();
        ILocalizable localILocalizable = null;
        String str3;
        int i;
        String str4;
        String str5;
        LocalizableString localLocalizableString;
        if (!this.gb.equals(this.go))
        {
          str3 = "PRIMARY_CONTACT";
          try
          {
            i = Integer.parseInt(this.go);
          }
          catch (Exception localException1)
          {
            i = -1;
          }
          localObject2 = Util.getBusinessEmployeeFromProfileID(localSecureUser, i, null);
          if (localObject2 == null) {
            str4 = "";
          } else {
            str4 = ((BusinessEmployee)localObject2).getUserName();
          }
          try
          {
            i = Integer.parseInt(this.gb);
          }
          catch (Exception localException2)
          {
            i = -1;
          }
          localObject2 = Util.getBusinessEmployeeFromProfileID(localSecureUser, i, null);
          if (localObject2 == null) {
            str5 = "";
          } else {
            str5 = ((BusinessEmployee)localObject2).getUserName();
          }
          ((HistoryTracker)localObject1).logChange(str2, str3, str4, str5, localILocalizable);
          String str6 = TrackingIDGenerator.GetNextID();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyBusiness_3", null);
          Initialize.audit(localSecureUser, localLocalizableString, getIdValue(), str6, 2200);
        }
        if (!this.gf.equals(this.ge))
        {
          str3 = "SECONDARY_CONTACT";
          try
          {
            i = Integer.parseInt(this.ge);
          }
          catch (Exception localException3)
          {
            i = -1;
          }
          localObject2 = Util.getBusinessEmployeeFromProfileID(localSecureUser, i, null);
          if (localObject2 == null) {
            str4 = "";
          } else {
            str4 = ((BusinessEmployee)localObject2).getUserName();
          }
          try
          {
            i = Integer.parseInt(this.gf);
          }
          catch (Exception localException4)
          {
            i = -1;
          }
          localObject2 = Util.getBusinessEmployeeFromProfileID(localSecureUser, i, null);
          if (localObject2 == null) {
            str5 = "";
          } else {
            str5 = ((BusinessEmployee)localObject2).getUserName();
          }
          ((HistoryTracker)localObject1).logChange(str2, str3, str4, str5, localILocalizable);
          String str7 = TrackingIDGenerator.GetNextID();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyBusiness_4", null);
          Initialize.audit(localSecureUser, localLocalizableString, getIdValue(), str7, 2200);
        }
        try
        {
          HistoryAdapter.addHistory(((HistoryTracker)localObject1).getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for ModifyBusiness: " + localProfileException.toString());
        }
      }
      if (this.addBusinessToBPWOnActivate)
      {
        if ((localBusiness2.getStatusValue() == 1) && (localBusiness1.getStatusValue() == 0)) {
          try
          {
            localObject1 = new HashMap();
            localObject1 = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass((HashMap)localObject1);
            com.ffusion.csil.core.Business.registerBusinessWithBPW(localSecureUser, localBusiness2, (HashMap)localObject1);
          }
          catch (CSILException localCSILException2)
          {
            localObject2 = new com.ffusion.beans.business.Business();
            ((com.ffusion.beans.business.Business)localObject2).set(localBusiness2);
            localBusiness2.setStatus("0");
            com.ffusion.csil.core.Business.modifyBusiness(localSecureUser, localBusiness2, (com.ffusion.beans.business.Business)localObject2, localHashMap);
            throw localCSILException2;
          }
        } else if ((localBusiness2.getStatusValue() == 1) && (localBusiness1.getStatusValue() == 1)) {
          com.ffusion.csil.core.Business.modifyBusinessRegisteredWithBPW(localSecureUser, localBusiness2, null);
        }
      }
      else if (ACH.isBusinessRegisteredWithBPW(localSecureUser, localBusiness2.getId(), null)) {
        if ((localBusiness2.getStatusValue() == 1) && (localBusiness1.getStatusValue() != 1))
        {
          ACH.activateCustomers(localSecureUser, new String[] { localBusiness2.getId() }, localHashMap);
          com.ffusion.csil.core.Business.modifyBusinessRegisteredWithBPW(localSecureUser, localBusiness2, null);
        }
        else if ((localBusiness2.getStatusValue() != 1) && (localBusiness1.getStatusValue() == 1))
        {
          com.ffusion.csil.core.Business.modifyBusinessRegisteredWithBPW(localSecureUser, localBusiness2, null);
          ACH.deactivateCustomers(localSecureUser, new String[] { localBusiness2.getId() }, localHashMap);
        }
        else if (localBusiness2.getStatusValue() == localBusiness1.getStatusValue())
        {
          if ((localBusiness1.getStatusValue() == 0) || (localBusiness1.getStatusValue() == 1) || (localBusiness1.getStatusValue() == 32))
          {
            com.ffusion.csil.core.Business.modifyBusinessRegisteredWithBPW(localSecureUser, localBusiness2, null);
          }
          else if (localBusiness1.getStatusValue() == 2)
          {
            ACH.activateCustomers(localSecureUser, new String[] { localBusiness2.getId() }, localHashMap);
            com.ffusion.csil.core.Business.modifyBusinessRegisteredWithBPW(localSecureUser, localBusiness2, null);
            ACH.deactivateCustomers(localSecureUser, new String[] { localBusiness2.getId() }, localHashMap);
          }
        }
        else if ((localBusiness2.getStatusValue() != localBusiness1.getStatusValue()) && (localBusiness1.getStatusValue() == 32) && (localBusiness2.getStatusValue() == 0))
        {
          com.ffusion.csil.core.Business.modifyBusinessRegisteredWithBPW(localSecureUser, localBusiness2, null);
        }
      }
      paramHttpSession.setAttribute("Business", localBusiness2);
      str1 = localBusiness1.getBankId();
      processEntitlements(localSecureUser);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      this.gd = this.serviceErrorURL;
    }
    finally
    {
      if (this.gd != this.serviceErrorURL) {
        paramHttpSession.removeAttribute("OldBusiness");
      }
    }
    jdMethod_for(paramHttpSession, str1);
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, String paramString)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (paramString != null)
    {
      if ((!this.gb.equals("")) && (!this.go.equals("")) && (!this.gb.equals(this.go)))
      {
        if (!this.gb.equals("none")) {
          jdMethod_for(paramHttpSession, localSecureUser, this.gb, paramString, "1");
        }
        if (!this.go.equals("none")) {
          jdMethod_for(paramHttpSession, localSecureUser, this.go, paramString, "0");
        }
      }
      if (this.gb.equals(this.gf)) {
        this.gf = "none";
      }
      if ((!this.gf.equals("")) && (!this.ge.equals("")) && (!this.gf.equals(this.ge)))
      {
        if (!this.gf.equals("none")) {
          jdMethod_for(paramHttpSession, localSecureUser, this.gf, paramString, "2");
        }
        if ((!this.ge.equals("none")) && (!this.ge.equals(this.gb))) {
          jdMethod_for(paramHttpSession, localSecureUser, this.ge, paramString, "0");
        }
      }
      if (getStatus().equals("1"))
      {
        if (!this.gb.equals("")) {
          jdMethod_for(paramHttpSession, localSecureUser, this.gb, paramString);
        }
        if (!this.gf.equals("")) {
          jdMethod_for(paramHttpSession, localSecureUser, this.gf, paramString);
        }
      }
    }
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, SecureUser paramSecureUser, String paramString1, String paramString2)
  {
    try
    {
      HashMap localHashMap = null;
      BusinessEmployee localBusinessEmployee = new BusinessEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.set("BANK_ID", paramString2);
      localBusinessEmployee.setId(paramString1);
      BusinessEmployees localBusinessEmployees = UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, localHashMap);
      if (!localBusinessEmployees.isEmpty())
      {
        localBusinessEmployee = (BusinessEmployee)localBusinessEmployees.get(0);
        if (!localBusinessEmployee.getAccountStatus().equals("1"))
        {
          localBusinessEmployee.setAccountStatus("1");
          Alerts localAlerts = null;
          localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
          if (localAlerts == null) {
            localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
          }
          if (localHashMap == null) {
            localHashMap = new HashMap();
          }
          localHashMap.put("SERVICE", localAlerts);
          UserAdmin.modifyBusinessEmployee(paramSecureUser, localBusinessEmployee, localHashMap);
        }
        return true;
      }
      return false;
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace();
    }
    return false;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      HashMap localHashMap = null;
      BusinessEmployee localBusinessEmployee = new BusinessEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.set("BANK_ID", paramString2);
      localBusinessEmployee.setId(paramString1);
      BusinessEmployees localBusinessEmployees = UserAdmin.getBusinessEmployees(paramSecureUser, localBusinessEmployee, localHashMap);
      if (!localBusinessEmployees.isEmpty())
      {
        localBusinessEmployee = (BusinessEmployee)localBusinessEmployees.get(0);
        localBusinessEmployee.setPrimaryUser(paramString3);
        Alerts localAlerts = null;
        localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
        if (localAlerts == null) {
          localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
        }
        if (localHashMap == null) {
          localHashMap = new HashMap();
        }
        localHashMap.put("SERVICE", localAlerts);
        UserAdmin.modifyBusinessEmployee(paramSecureUser, localBusinessEmployee, localHashMap);
        return true;
      }
      return false;
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace();
    }
    return false;
  }
  
  private boolean jdMethod_case(HttpSession paramHttpSession)
  {
    com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)paramHttpSession.getAttribute("OldBusiness");
    if (localBusiness == null)
    {
      this.gd = this.taskErrorURL;
      this.error = 4123;
      return false;
    }
    int i = localBusiness.getStatusValue();
    ModifyBusiness localModifyBusiness = this;
    int j = localModifyBusiness.getStatusValue();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if ((j == 1) && (j != i))
    {
      localModifyBusiness.setApprovedBy(String.valueOf(localSecureUser.getProfileID()));
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      Date localDate = new Date();
      localModifyBusiness.setActivationDate(new DateTime(localDate, localLocale));
    }
    return true;
  }
  
  private int jdMethod_for(HttpSession paramHttpSession, com.ffusion.beans.business.Business paramBusiness, int paramInt)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    BusinessEmployee localBusinessEmployee1 = new BusinessEmployee(this.locale);
    localBusinessEmployee1.setBankId(paramBusiness.getBankId());
    localBusinessEmployee1.setBusinessId(paramBusiness.getId());
    BusinessEmployees localBusinessEmployees = jdMethod_for(localSecureUser, localBusinessEmployee1);
    if (localBusinessEmployees == null) {
      return this.error;
    }
    Iterator localIterator = localBusinessEmployees.iterator();
    Alerts localAlerts = null;
    localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
    if (localAlerts == null) {
      localAlerts = (Alerts)paramHttpSession.getAttribute("com.ffusion.services.Alerts");
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("SERVICE", localAlerts);
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee2 = (BusinessEmployee)localIterator.next();
      localBusinessEmployee2.setAccountStatus(String.valueOf(paramInt));
      this.error = jdMethod_for(localSecureUser, localBusinessEmployee2, localHashMap);
      if (this.error != 0) {
        return this.error;
      }
    }
    return this.error;
  }
  
  private BusinessEmployees jdMethod_for(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee)
  {
    try
    {
      return UserAdmin.getBusinessEmployees(paramSecureUser, paramBusinessEmployee, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.gd = this.serviceErrorURL;
    }
    return null;
  }
  
  private int jdMethod_for(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
  {
    try
    {
      UserAdmin.modifyBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.gd = this.serviceErrorURL;
    }
    return this.error;
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
  
  public void setVerifyFormat(String paramString)
  {
    this.verifyFormat = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.verifyFormat = paramString.toUpperCase();
    }
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setLimitName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.gi = paramString;
    }
  }
  
  public void setLimitData(String paramString)
  {
    this.gj.setProperty(this.gi, paramString);
    this.gi = "";
  }
  
  public void setPrimaryContact(String paramString)
  {
    this.gb = paramString;
  }
  
  public String getPrimaryContact()
  {
    return this.gb;
  }
  
  public void setSecondaryContact(String paramString)
  {
    this.gf = paramString;
  }
  
  public String getSecondaryContact()
  {
    return this.gf;
  }
  
  public void setOldPrimaryContact(String paramString)
  {
    this.go = paramString;
  }
  
  public String getOldPrimaryContact()
  {
    return this.go;
  }
  
  public void setOldSecondaryContact(String paramString)
  {
    this.ge = paramString;
  }
  
  public String getOldSecondaryContact()
  {
    return this.ge;
  }
  
  public void setAddBusinessToBPWOnActivate(String paramString)
  {
    this.addBusinessToBPWOnActivate = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setenforceUnqiueCIF(String paramString)
  {
    setEnforceUniqueCIF(paramString);
  }
  
  public void setEnforceUniqueCIF(String paramString)
  {
    this.enforceUnqiueCIF = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPropName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.gc = paramString;
    }
  }
  
  public void setPropData(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.f8.setProperty(this.gc, paramString);
    } else {
      this.f8.remove(this.gc);
    }
  }
  
  public String getPropData()
  {
    return this.f8.getProperty(this.gc, "");
  }
  
  protected void processEntitlements(SecureUser paramSecureUser)
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 2, getEntitlementGroupId());
    HashMap localHashMap = new HashMap();
    try
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupIdValue());
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      Enumeration localEnumeration = this.f8.keys();
      Object localObject1;
      Object localObject2;
      Object localObject3;
      while (localEnumeration.hasMoreElements())
      {
        localObject1 = (String)localEnumeration.nextElement();
        localObject2 = this.f8.getProperty((String)localObject1);
        if (localObject2 != null)
        {
          localObject3 = new Entitlement();
          ((Entitlement)localObject3).setOperationName((String)localObject1);
          if (((String)localObject2).equalsIgnoreCase("true"))
          {
            if (localEntitlements.contains(localObject3)) {
              localArrayList2.add(localObject3);
            }
            localEntitlements.remove(localObject3);
          }
          else if (!localEntitlements.contains(localObject3))
          {
            localEntitlements.add(localObject3);
            localArrayList1.add(localObject3);
          }
        }
      }
      if (localArrayList1.size() + localArrayList2.size() != 0)
      {
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupIdValue(), localEntitlements);
        Object localObject4;
        Object localObject5;
        Object localObject6;
        if ((localArrayList2.size() != 0) && (this._autoEntitleACHPermissions))
        {
          localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
          localObject2 = new HashMap();
          localObject3 = new ArrayList();
          ((ArrayList)localObject3).add("cross ACH company");
          ((ArrayList)localObject3).add("per ACH company");
          ((HashMap)localObject2).put("category", localObject3);
          localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties((HashMap)localObject2);
          localObject5 = localArrayList2.iterator();
          while (((Iterator)localObject5).hasNext())
          {
            localObject6 = (Entitlement)((Iterator)localObject5).next();
            if (((EntitlementTypePropertyLists)localObject4).getByOperationName(((Entitlement)localObject6).getOperationName()) != null) {
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).add(localObject6);
            }
          }
          AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(getEntitlementGroupIdValue()), (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, 2, this._autoEntitleFlag, localHashMap);
        }
        LocalizableString localLocalizableString;
        for (int i = 0; i < localArrayList1.size(); i++)
        {
          localObject2 = TrackingIDGenerator.GetNextID();
          localObject3 = ((Entitlement)localArrayList1.get(i)).getOperationName();
          localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
          localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
          localObject6 = new Object[2];
          localObject6[0] = localObject5;
          localObject6[1] = getBusinessName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyBusiness_1", (Object[])localObject6);
          Initialize.audit(paramSecureUser, localLocalizableString, getIdValue(), (String)localObject2, 3225);
          localHistoryTracker.logEntitlementAdd((Entitlement)localArrayList1.get(i), null);
        }
        for (i = 0; i < localArrayList2.size(); i++)
        {
          localObject2 = TrackingIDGenerator.GetNextID();
          localObject3 = ((Entitlement)localArrayList2.get(i)).getOperationName();
          localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
          localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
          localObject6 = new Object[2];
          localObject6[0] = localObject5;
          localObject6[1] = getBusinessName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyBusiness_2", (Object[])localObject6);
          Initialize.audit(paramSecureUser, localLocalizableString, getIdValue(), (String)localObject2, 3225);
          localHistoryTracker.logEntitlementDelete((Entitlement)localArrayList2.get(i), null);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for ModifyBusiness: " + localProfileException.toString());
    }
  }
  
  public void setMarketSegmentId(String paramString)
  {
    this.gm = paramString;
  }
  
  public String getMarketSegmentId()
  {
    return this.gm;
  }
  
  public String getMaxBusinessNameLength()
  {
    return Integer.toString(this.maxBusinessNameLength);
  }
  
  public void setMaxBusinessNameLength(String paramString)
  {
    try
    {
      this.maxBusinessNameLength = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getAutoEntitleFlag()
  {
    return String.valueOf(this._autoEntitleFlag);
  }
  
  public void setAutoEntitleFlag(String paramString)
  {
    this._autoEntitleFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitleACHPermissions()
  {
    return String.valueOf(this._autoEntitleACHPermissions);
  }
  
  public void setAutoEntitleACHPermissions(String paramString)
  {
    this._autoEntitleACHPermissions = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifyBusiness
 * JD-Core Version:    0.7.0.1
 */