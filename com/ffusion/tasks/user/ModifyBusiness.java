package com.ffusion.tasks.user;

import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BusinessAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.business.BusinessTask;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBusiness
  extends Business
  implements BusinessTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected String verifyFormat;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int error = 0;
  private String f7 = null;
  private String f6 = null;
  private String f5 = null;
  private String f4 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.initFlag) && (!init(localHttpSession))) {
      return this.taskErrorURL;
    }
    try
    {
      if (!validateInput(localHttpSession)) {
        return this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException1)
    {
      MapError.mapError(localCSILException1);
      return this.serviceErrorURL;
    }
    if (!this.processFlag) {
      return this.successURL;
    }
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localObject1 = (Business)localHttpSession.getAttribute("OldBusiness");
      if (localObject1 == null)
      {
        this.error = 4104;
        String str = this.taskErrorURL;
        return str;
      }
      BusinessAdmin.modifyBusiness(localSecureUser, this, (Business)localObject1, null);
      localHttpSession.setAttribute("Business", this);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      Object localObject1 = this.serviceErrorURL;
      return localObject1;
    }
    finally
    {
      localHttpSession.removeAttribute("OldBusiness");
    }
    this.processFlag = false;
    return this.successURL;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    Business localBusiness1 = (Business)paramHttpSession.getAttribute("Business");
    set(localBusiness1);
    Business localBusiness2 = new Business((Locale)paramHttpSession.getAttribute("java.util.Locale"));
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
  {
    boolean bool = true;
    if (this.validate == null) {
      return bool;
    }
    if ((this.validate.indexOf(BUSINESSNAME) != -1) && ((this.businessName == null) || (this.businessName.trim().length() == 0))) {
      return setError(4100);
    }
    if ((this.validate.indexOf("STREET") != -1) && ((this.street == null) || (this.street.trim().length() == 0) || (this.street.trim().length() > 40))) {
      return setError(23);
    }
    if ((this.validate.indexOf("STREET2") != -1) && ((this.street2 == null) || (this.street2.trim().length() == 0) || (this.street2.trim().length() > 40))) {
      return setError(23);
    }
    if ((this.validate.indexOf("CITY") != -1) && ((this.city == null) || (this.city.trim().length() == 0) || (this.city.trim().length() > 20))) {
      return setError(24);
    }
    if ((this.validate.indexOf("ZIPCODE") != -1) && ((this.zipCode == null) || (this.zipCode.getString().length() == 0) || (this.zipCode.getString().length() > 11))) {
      return setError(26);
    }
    if ((this.validate.indexOf("STATE") != -1) && ((this.state == null) || (this.state.length() == 0))) {
      return setError(25);
    }
    if ((this.validate.indexOf("COUNTRY") != -1) && ((this.country == null) || (this.country.length() == 0))) {
      return setError(53);
    }
    if ((this.validate.indexOf("PHONE") != -1) && ((this.phone == null) || (this.phone.getString().length() == 0) || (this.phone.toString().length() > 14))) {
      return setError(27);
    }
    if ((this.validate.indexOf("PHONE2") != -1) && ((this.phone2 == null) || (this.phone2.getString().length() == 0) || (this.phone2.toString().length() > 14))) {
      return setError(27);
    }
    if ((this.validate.indexOf("FAXPHONE") != -1) && ((this.faxPhone == null) || (this.faxPhone.getString().length() == 0) || (this.faxPhone.toString().length() > 14))) {
      return setError(119);
    }
    if ((this.validate.indexOf("EMAIL") != -1) && ((this.email == null) || (this.email.getString().length() == 0) || (this.email.toString().length() > 40))) {
      return setError(31);
    }
    if ((this.validate.indexOf("EMAIL") != -1) && (this.email != null) && (!this.email.isValid())) {
      return setError(31);
    }
    if ((this.validate.indexOf(APPROVALGROUP) != -1) && ((this.approvalGroup == null) || (this.approvalGroup.trim().length() == 0) || (this.approvalGroup.equals("0") == true))) {
      return setError(4131);
    }
    if ((this.validate.indexOf(BUSINESSCIF) != -1) && ((this.businessCIF == null) || (this.businessCIF.trim().length() == 0))) {
      return setError(4132);
    }
    if ((this.validate.indexOf(PERSONALCIF) != -1) && ((this.personalCIF == null) || (this.personalCIF.trim().length() == 0))) {
      return setError(4133);
    }
    if ((this.validate.indexOf(SERVICESPACKAGEID) != -1) && (this.servicesPackageId == 0)) {
      return setError(4134);
    }
    this.validate = null;
    return bool;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    boolean bool = true;
    if (this.verifyFormat == null) {
      return bool;
    }
    if ((this.verifyFormat.indexOf("STREET") != -1) && (this.street != null) && (this.street.trim().length() > 40)) {
      return setError(23);
    }
    if ((this.verifyFormat.indexOf("STREET2") != -1) && (this.street2 != null) && (this.street2.trim().length() > 40)) {
      return setError(23);
    }
    if ((this.verifyFormat.indexOf("CITY") != -1) && (this.city != null) && (this.city.trim().length() > 20)) {
      return setError(24);
    }
    if ((this.verifyFormat.indexOf("ZIPCODE") != -1) && (this.zipCode != null) && (this.zipCode.getString().length() > 11)) {
      return setError(26);
    }
    if ((this.verifyFormat.indexOf("STATE") != -1) && (this.state != null) && (this.state.length() > 0) && (!validateState(paramHttpSession))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("COUNTRY") != -1) && (this.country != null) && (this.country.length() > 0) && (!validateCountry(paramHttpSession))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("PHONE") != -1) && (this.phone != null) && (this.phone.toString().length() > 14)) {
      return setError(27);
    }
    if ((this.verifyFormat.indexOf("PHONE2") != -1) && (this.phone2 != null) && (this.phone2.toString().length() > 14)) {
      return setError(27);
    }
    if ((this.verifyFormat.indexOf("FAXPHONE") != -1) && (this.faxPhone != null) && (this.faxPhone.toString().length() > 14)) {
      return setError(119);
    }
    if ((this.verifyFormat.indexOf("ZIPCODE") != -1) && (this.zipCode != null) && (this.zipCode.getString().length() > 0) && (!jdMethod_for(localSecureUser))) {
      return setError(26);
    }
    if ((this.verifyFormat.indexOf("PHONE") != -1) && (this.phone != null) && (this.phone.getString().length() > 0) && (!jdMethod_for(localSecureUser, this.phone))) {
      return setError(27);
    }
    if ((this.verifyFormat.indexOf("PHONE2") != -1) && (this.phone2 != null) && (this.phone2.getString().length() > 0) && (!jdMethod_for(localSecureUser, this.phone2))) {
      return setError(27);
    }
    if ((this.verifyFormat.indexOf("FAXPHONE") != -1) && (this.faxPhone != null) && (this.faxPhone.getString().length() > 0) && (!jdMethod_for(localSecureUser, this.faxPhone))) {
      return setError(119);
    }
    if ((this.verifyFormat.indexOf("EMAIL") != -1) && (this.email != null) && (this.email.getString().length() > 0) && ((this.email.toString().length() > 40) || (!this.email.isValid()))) {
      return setError(31);
    }
    if ((this.verifyFormat.indexOf(APPROVALGROUP) != -1) && (this.approvalGroup != null) && (this.approvalGroup.equals("0") == true)) {
      return setError(4131);
    }
    if ((this.verifyFormat.indexOf(SERVICESPACKAGEID) != -1) && (this.servicesPackageId == 0)) {
      return setError(4134);
    }
    this.verifyFormat = null;
    return bool;
  }
  
  private boolean jdMethod_for(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = Util.validateZipCodeFormat(paramSecureUser, this.country, this.zipCode.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    if (!str.equals(""))
    {
      this.zipCode.setFormat(str);
      return true;
    }
    return false;
  }
  
  private boolean jdMethod_for(SecureUser paramSecureUser, Phone paramPhone)
    throws CSILException
  {
    String str = Util.validatePhoneFormat(paramSecureUser, this.country, paramPhone.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    if (!str.equals(""))
    {
      paramPhone.setFormat(str);
      return true;
    }
    return false;
  }
  
  protected boolean validateState(HttpSession paramHttpSession)
  {
    if (this.state == null) {
      return true;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    StateProvinceDefns localStateProvinceDefns = null;
    try
    {
      localStateProvinceDefns = Util.getStatesForCountry(localSecureUser, getCountry(), null);
    }
    catch (CSILException localCSILException)
    {
      localStateProvinceDefns = null;
    }
    if (localStateProvinceDefns == null) {
      return jdMethod_try(paramHttpSession);
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
  
  private boolean jdMethod_try(HttpSession paramHttpSession)
  {
    if (this.state == null) {
      return setError(25);
    }
    Locale localLocale;
    ResourceBundle localResourceBundle;
    String str1;
    if ((this.f7 == null) && (this.f6 == null))
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
    else if ((this.f7 != null) && (this.f6 != null))
    {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localResourceBundle = ResourceUtil.getBundle(this.f7, localLocale);
      if (localResourceBundle == null) {
        return setError(43);
      }
      str1 = null;
      try
      {
        str1 = localResourceBundle.getString(this.f6).toUpperCase();
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
  
  protected boolean validateCountry(HttpSession paramHttpSession)
  {
    if (this.country == null) {
      return setError(53);
    }
    Locale localLocale;
    ResourceBundle localResourceBundle;
    String str1;
    if ((this.f5 == null) && (this.f4 == null))
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
    else if ((this.f5 != null) && (this.f4 != null))
    {
      localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      localResourceBundle = ResourceUtil.getBundle(this.f5, localLocale);
      if (localResourceBundle == null) {
        return setError(43);
      }
      str1 = null;
      try
      {
        str1 = localResourceBundle.getString(this.f4).toUpperCase();
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
  
  public void setStateValidationResourceFile(String paramString)
  {
    this.f7 = paramString;
  }
  
  public String getStateValidationResourceFile()
  {
    return this.f7;
  }
  
  public void setStateValidationResourceName(String paramString)
  {
    this.f6 = paramString;
  }
  
  public String getStateValidationResourceName()
  {
    return this.f6;
  }
  
  public void setCountryValidationResourceFile(String paramString)
  {
    this.f5 = paramString;
  }
  
  public String getCountryValidationResourceFile()
  {
    return this.f5;
  }
  
  public void setCountryValidationResourceName(String paramString)
  {
    this.f4 = paramString;
  }
  
  public String getCountryValidationResourceName()
  {
    return this.f4;
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
  
  public void setState(String paramString)
  {
    if ((paramString != null) && (paramString.equals(""))) {
      super.setState(null);
    } else {
      super.setState(paramString);
    }
  }
  
  public void setCountry(String paramString)
  {
    if ((paramString != null) && (paramString.equals(""))) {
      super.setCountry(null);
    } else {
      super.setCountry(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.ModifyBusiness
 * JD-Core Version:    0.7.0.1
 */