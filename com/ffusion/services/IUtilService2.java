package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.CountryDefns;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.beans.util.UtilException;
import java.util.HashMap;
import java.util.Locale;

public abstract interface IUtilService2
  extends IUtilService
{
  public static final String LANGUAGE_INIT_XML = "languages.xml";
  public static final String STATE_KEY_LIST = "StateKeyList";
  public static final String SERVICE_BLINIT_XML = "bankLookupCountries.xml";
  public static final String DEFAULT_LANGUAGE = "DEFAULT_LANGUAGE";
  public static final String LANGUAGE_LIST = "LANGUAGE_LIST";
  public static final String LANGUAGE = "LANGUAGE";
  public static final String LOCALE = "LOCALE";
  public static final String COUNTRY_NAME = "CountryName";
  
  public abstract StateProvinceDefns getStatesForCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
  
  public abstract StateProvinceDefn getStateProvinceDefnForState(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws UtilException;
  
  public abstract LanguageDefns getLanguageList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws UtilException;
  
  public abstract LanguageDefn getLanguage(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
  
  public abstract Locale getDefaultLanguage(HashMap paramHashMap)
    throws UtilException;
  
  public abstract String getCodeForBankLookupCountryName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws UtilException;
  
  public abstract CountryDefns getBankLookupStandardCountryNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws UtilException;
  
  public abstract CountryDefns getCountryList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws UtilException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IUtilService2
 * JD-Core Version:    0.7.0.1
 */