package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.CountryDefns;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.services.IUtilService2;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class UtilHandler
  extends Initialize
{
  private static final String a7v = "Util";
  private static IUtilService2 a7w = null;
  private static boolean a7u = false;
  private static String a7t = "USD";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "UtilHandler.initialize";
    DebugLog.log(Level.FINE, str1);
    try
    {
      HashMap localHashMap1 = HandlerUtil.getServiceSettings(paramHashMap, "Util", str1, 20107);
      a7w = (IUtilService2)HandlerUtil.instantiateService(localHashMap1, str1, 20107);
      a7w.initialize(null);
      HashMap localHashMap2 = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap2 != null)
      {
        localObject = (String)localHashMap2.get("UseDemoClasses");
        if (localObject != null)
        {
          DebugLog.log(Level.FINEST, "CSIL flag for using demo classes set to " + (String)localObject);
          a7u = Boolean.valueOf((String)localObject).booleanValue();
        }
        else
        {
          DebugLog.log(Level.INFO, "CSIL flag for using demo classes was not set");
        }
      }
      Object localObject = (Properties)paramHashMap.get("DB_PROPERTIES");
      if (localObject != null)
      {
        String str2 = ((Properties)localObject).getProperty("LimitBaseCurrency");
        if (str2 != null)
        {
          a7t = str2;
          DebugLog.log(Level.FINEST, "Default base currency for limits set to " + a7t);
        }
        else
        {
          DebugLog.log(Level.FINEST, "Default base currency for limits is not set. The default value of " + a7t + " will be used.");
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1 + ": " + localCSILException.why, localCSILException);
      throw new CSILException(-1007, localCSILException);
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1 + ": " + localException.getMessage(), localException);
      throw new CSILException(-1007, localException);
    }
  }
  
  public static boolean useDemoMode()
  {
    return a7u;
  }
  
  public static ArrayList getValidPhoneFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getValidPhoneFormats";
    DebugLog.log(Level.FINE, str);
    ArrayList localArrayList = null;
    try
    {
      localArrayList = a7w.getValidPhoneFormats(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localArrayList;
  }
  
  public static ArrayList getValidZipCodeFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getValidZipCodeFormats";
    DebugLog.log(Level.FINE, str);
    ArrayList localArrayList = null;
    try
    {
      localArrayList = a7w.getValidZipCodeFormats(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localArrayList;
  }
  
  public static ArrayList getValidSSNFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getValidSSNFormats";
    DebugLog.log(Level.FINE, str);
    ArrayList localArrayList = null;
    try
    {
      localArrayList = a7w.getValidSSNFormats(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localArrayList;
  }
  
  public static String validatePhoneFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.handlers.UtilHandler.validatePhoneFormat";
    DebugLog.log(Level.FINE, str1);
    String str2 = null;
    try
    {
      str2 = a7w.validatePhoneFormat(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str1, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return str2;
  }
  
  public static String validateZipCodeFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.handlers.UtilHandler.validateZipCodeFormat";
    DebugLog.log(Level.FINE, str1);
    String str2 = null;
    try
    {
      str2 = a7w.validateZipCodeFormat(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str1, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return str2;
  }
  
  public static String validateSSNFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.handlers.UtilHandler.validateSSNFormat";
    DebugLog.log(Level.FINE, str1);
    String str2 = null;
    try
    {
      str2 = a7w.validateSSNFormat(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str1, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return str2;
  }
  
  public static boolean isRegisteredCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.isRegisteredCountry";
    DebugLog.log(Level.FINE, str);
    boolean bool;
    try
    {
      bool = a7w.isRegisteredCountry(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return bool;
  }
  
  public static StateProvinceDefns getStatesForCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getStatesForCountry";
    DebugLog.log(Level.FINE, str);
    StateProvinceDefns localStateProvinceDefns;
    try
    {
      localStateProvinceDefns = a7w.getStatesForCountry(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localStateProvinceDefns;
  }
  
  public static StateProvinceDefn getStateProvinceDefnForState(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getStateProvinceDefnForState";
    DebugLog.log(Level.FINE, str);
    StateProvinceDefn localStateProvinceDefn = null;
    try
    {
      localStateProvinceDefn = a7w.getStateProvinceDefnForState(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localStateProvinceDefn;
  }
  
  public static LanguageDefns getLanguageList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getLangaugeList";
    DebugLog.log(Level.FINE, str);
    LanguageDefns localLanguageDefns;
    try
    {
      localLanguageDefns = a7w.getLanguageList(paramSecureUser, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localLanguageDefns;
  }
  
  public static LanguageDefn getLanguage(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getLangauge";
    DebugLog.log(Level.FINE, str);
    LanguageDefn localLanguageDefn;
    try
    {
      localLanguageDefn = a7w.getLanguage(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localLanguageDefn;
  }
  
  public static Locale getDefaultLanguage(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getDefaultLangauge";
    DebugLog.log(Level.FINE, str);
    Locale localLocale;
    try
    {
      localLocale = a7w.getDefaultLanguage(paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localLocale;
  }
  
  public static String getCodeForBankLookupCountryName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.handlers.UtilHandler.getCodeForBankLookupCountryName";
    DebugLog.log(Level.FINE, str1);
    String str2 = null;
    try
    {
      str2 = a7w.getCodeForBankLookupCountryName(paramSecureUser, paramString, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str1, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return str2;
  }
  
  public static CountryDefns getBankLookupStandardCountryNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getBankLookupStandardCountryNames";
    DebugLog.log(Level.FINE, str);
    CountryDefns localCountryDefns = null;
    try
    {
      localCountryDefns = a7w.getBankLookupStandardCountryNames(paramSecureUser, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localCountryDefns;
  }
  
  public static CountryDefns getCountryList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.UtilHandler.getCountryList";
    DebugLog.log(Level.FINE, str);
    CountryDefns localCountryDefns = null;
    try
    {
      localCountryDefns = a7w.getCountryList(paramSecureUser, paramHashMap);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing(str, localUtilException);
      throw new CSILException(jdMethod_try(localUtilException), localUtilException);
    }
    return localCountryDefns;
  }
  
  private static int jdMethod_try(Throwable paramThrowable)
  {
    int i = 40500;
    if ((paramThrowable instanceof UtilException))
    {
      int j = ((UtilException)paramThrowable).getErrorCode();
      switch (j)
      {
      case 1: 
        i = 40501;
        break;
      case 2: 
        i = 40502;
        break;
      case 3: 
        i = 40503;
        break;
      case 4: 
        i = 40504;
        break;
      case 5: 
        i = 40505;
        break;
      case 6: 
        i = 40506;
        break;
      case 7: 
        i = 40507;
        break;
      case 8: 
        i = 40508;
        break;
      case 9: 
        i = 40509;
        break;
      default: 
        i = 40500;
      }
    }
    return i;
  }
  
  public static String getLimitBaseCurrency()
  {
    return a7t;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.UtilHandler
 * JD-Core Version:    0.7.0.1
 */