package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.UtilException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface IUtilService
{
  public static final String SERVICE_INIT_XML = "fieldValidation.xml";
  public static final String COUNTRY_LIST = "CountryList";
  public static final String COUNTRY = "Country";
  public static final String COUNTRY_CODE = "CountryCode";
  public static final String PHONE_FORMAT_LIST = "PhoneFormatList";
  public static final String PHONE_FORMAT = "PhoneFormat";
  public static final String ZIP_CODE_FORMAT_LIST = "ZipCodeFormatList";
  public static final String ZIP_CODE_FORMAT = "ZipCodeFormat";
  public static final String SSN_FORMAT_LIST = "SSNFormatList";
  public static final String SSN_FORMAT = "SSNFormat";
  public static final String MASK = "Mask";
  public static final String NAME = "Name";
  public static final String BANK_EMPLOYEE_COUNTRY_CODE = "BankEmployeeCountryCode";
  
  public abstract void initialize(HashMap paramHashMap)
    throws UtilException;
  
  public abstract ArrayList getValidPhoneFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
  
  public abstract ArrayList getValidZipCodeFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
  
  public abstract ArrayList getValidSSNFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
  
  public abstract String validatePhoneFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException;
  
  public abstract String validateZipCodeFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException;
  
  public abstract String validateSSNFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException;
  
  public abstract boolean isRegisteredCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IUtilService
 * JD-Core Version:    0.7.0.1
 */