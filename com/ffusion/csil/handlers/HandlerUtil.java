package com.ffusion.csil.handlers;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

public class HandlerUtil
{
  public static final String SERVICE_SETTINGS_TAG = "SERVICE_SETTINGS_LIST";
  public static final String SERVICE_TAG = "SERVICE";
  public static final String SERVICE_NAME_TAG = "SERVICE_NAME";
  public static final String JAVA_CLASS_TAG = "JAVA_CLASS";
  public static final String DEMO_JAVA_CLASS_TAG = "DEMO_JAVA_CLASS";
  public static final String SIGNON_SETTINGS = "SIGNON_SETTINGS";
  public static final String NUM_LOGINS_BEFORE_LOCKOUT = "NUM_LOGINS_BEFORE_LOCKOUT";
  public static final String LOCKOUT_DURATION = "LOCKOUT_DURATION";
  public static final String PASSWORD_MAX_LENGTH = "PASSWORD_MAX_LENGTH";
  public static final String PASSWORD_MIN_LENGTH = "PASSWORD_MIN_LENGTH";
  public static final String PASSWORD_MIN_DIGITS = "PASSWORD_MIN_DIGITS";
  public static final String PASSWORD_MIN_LETTERS = "PASSWORD_MIN_LETTERS";
  public static final String PASSWORD_MIN_UPPERCASE_LETTERS = "PASSWORD_MIN_UPPERCASE_LETTERS";
  public static final String PASSWORD_ALLOW_SPECIAL_CHARS = "PASSWORD_ALLOW_SPECIAL_CHARS";
  public static final String BC_SIGNON_SETTINGS = "BC_SIGNON_SETTINGS";
  public static final String BC_PASSWORD_MAX_LENGTH = "BC_PASSWORD_MAX_LENGTH";
  public static final String BC_PASSWORD_MIN_LENGTH = "BC_PASSWORD_MIN_LENGTH";
  public static final String BC_PASSWORD_MIN_DIGITS = "BC_PASSWORD_MIN_DIGITS";
  public static final String BC_PASSWORD_MIN_LETTERS = "BC_PASSWORD_MIN_LETTERS";
  public static final String BC_PASSWORD_MIN_UPPERCASE_LETTERS = "BC_PASSWORD_MIN_UPPERCASE_LETTERS";
  public static final String BC_PASSWORD_ALLOW_SPECIAL_CHARS = "BC_PASSWORD_ALLOW_SPECIAL_CHARS";
  public static final String INACTIVITY_PERIOD = "INACTIVITY_PERIOD";
  public static final String PWD_REUSE_ON_TIME = "PWD_REUSE_ON_TIME";
  public static final String PWD_REUSE_HISTORY = "PWD_REUSE_HISTORY";
  public static final String PWD_EXPIRE_TIME = "PWD_EXPIRE_TIME";
  public static final String PWD_EXPIRE_WARNING = "PWD_EXPIRE_WARNING";
  public static final String DUPLICATE_USER_NAMES = "DUPLICATE_USER_NAMES";
  public static final String MAX_INVALID_CREDENTIALS = "MAX_NUM_INVALID_CREDENTIALS";
  public static final String EXEMPT_JOB_TYPES_LIST = "EXEMPT_JOB_TYPES_LIST";
  public static final String JOB = "JOB";
  public static final String NAME = "NAME";
  public static final String SVC_BUREAU_ID = "SVC_BUREAU_ID";
  public static final String GLOBAL_SETTINGS = "GLOBAL_SETTINGS";
  public static final String DB_PROPERTIES = "DB_PROPERTIES";
  public static final String USE_COMPATIBILITY = "UseCompatibilityMode";
  public static final String PAGESIZE = "PageSize";
  public static final String BUSINESS_DISPLAY_SIZE = "BusinessDisplaySize";
  public static final String USER_DISPLAY_SIZE = "UserDisplaySize";
  public static final String CONSUMER_DISPLAY_SIZE = "ConsumerDisplaySize";
  public static final String CASE_DISPLAY_SIZE = "CaseDisplaySize";
  public static final String APPLICATION_DISPLAY_SIZE = "ApplicationDisplaySize";
  public static final String EMPLOYEE_DISPLAY_SIZE = "EmployeeDisplaySize";
  public static final String ACCOUNT_DISPLAY_SIZE = "AccountDisplaySize";
  public static final String ACH_COMPANY_DISPLAY_SIZE = "ACHCompanyDisplaySize";
  public static final String ACH_PAYEE_DISPLAY_SIZE = "ACHPayeeDisplaySize";
  public static final String LOCATION_DISPLAY_SIZE = "LocationDisplaySize";
  public static final String TRANSACTION_SEARCH_MAX_DISPLAY = "TransactionSearchMaxDisplay";
  public static final String BUSINESS_MAXRESULT_SIZE = "BusinessMaxResultSize";
  public static final String USER_MAXRESULT_SIZE = "UserMaxResultSize";
  public static final String CONSUMER_MAXRESULT_SIZE = "ConsumerMaxResultSize";
  public static final String CASE_MAXRESULT_SIZE = "CaseMaxResultSize";
  public static final String APPLICATION_MAXRESULT_SIZE = "ApplicationMaxResultSize";
  public static final String EMPLOYEE_MAXRESULT_SIZE = "EmployeeMaxResultSize";
  public static final String ACCOUNT_MAXRESULT_SIZE = "AccountMaxResultSize";
  public static final String ACH_COMPANY_MAXRESULT_SIZE = "ACHCompanyMaxResultSize";
  public static final String RPT_MAX_DISPLAY = "ReportMaxDisplay";
  public static final String USE_DEMOCLASSES = "UseDemoClasses";
  public static final String LIMIT_BASE_CURRENCY = "LimitBaseCurrency";
  public static final String WIRE_TEMPLATE_RESULT_SIZE = "WireTemplateResultSize";
  public static final String DEFAULT_WIRE_TEMPLATE_RESULT_SIZE = "15";
  public static final String NAME_CONVENTION = "NameConvention";
  public static final String DEFAULT_NAME_CONVENTION = "dual";
  public static final String GLOBAL_PAYEE_DISPLAY_SIZE = "GlobalPayeeDisplaySize";
  private static final String n = "15";
  private static final String jdField_byte = "15";
  private static final String jdField_try = "15";
  private static final String i = "15";
  private static final String h = "15";
  private static final String z = "15";
  private static final String k = "15";
  private static final String jdField_new = "15";
  private static final String jdField_goto = "15";
  private static final String c = "15";
  private static final String m = "15";
  public static final String DEFAULT_TRANSACTION_SEARCH_MAX_DISPLAY = "250";
  private static final String A = "250";
  private static final String j = "250";
  private static final String jdField_case = "250";
  private static final String jdField_null = "250";
  private static final String jdField_for = "250";
  private static final String jdField_else = "250";
  private static final String jdField_void = "250";
  private static final String l = "250";
  private static final String d = "250";
  private static final int o = 3;
  private static final int b = 60;
  private static final int a = 8;
  private static final int y = 15;
  private static final int r = 1;
  private static final int v = 2;
  private static final int p = 1;
  private static final boolean x = true;
  private static final int f = 9;
  private static final int jdField_long = 15;
  private static final int jdField_if = 1;
  private static final int s = 2;
  private static final int B = 1;
  private static final boolean q = true;
  private static final int w = 90;
  private static final int t = 24;
  private static final int g = 5;
  private static final int e = 150;
  private static final int jdField_do = 14;
  private static final boolean u = false;
  private static final int jdField_char = 3;
  private static boolean jdField_int = false;
  
  public static HashMap getServiceSettings(HashMap paramHashMap, String paramString1, String paramString2, int paramInt)
    throws CSILException
  {
    try
    {
      HashMap localHashMap1 = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap1 != null)
      {
        String str = (String)localHashMap1.get("UseDemoClasses");
        if (str != null)
        {
          DebugLog.log(Level.FINEST, "CSIL flag for using demo classes set to " + str);
          jdField_int = Boolean.valueOf(str).booleanValue();
        }
        else
        {
          DebugLog.log(Level.INFO, "CSIL flag for using demo classes was not set");
        }
      }
    }
    catch (ClassCastException localClassCastException1) {}
    HashMap localHashMap2;
    CSILException localCSILException;
    try
    {
      localHashMap2 = (HashMap)paramHashMap.get("SERVICE_SETTINGS_LIST");
    }
    catch (ClassCastException localClassCastException2)
    {
      localCSILException = new CSILException(paramString2, paramInt, "Invalid service settings entries.");
      DebugLog.throwing(paramString2, localCSILException);
      throw localCSILException;
    }
    if (localHashMap2 == null)
    {
      localObject = new CSILException(paramString2, paramInt, "Missing service settings entries.");
      DebugLog.throwing(paramString2, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    Object localObject = XMLUtil.getMultiSetEntry(localHashMap2, "SERVICE", "SERVICE_NAME", paramString1);
    if ((localObject == null) || (!(localObject instanceof HashMap)))
    {
      localCSILException = new CSILException(paramString2, paramInt, "Missing service settings entry for service.");
      DebugLog.throwing(paramString2, localCSILException);
      throw localCSILException;
    }
    return (HashMap)localObject;
  }
  
  public static Object instantiateService(HashMap paramHashMap, String paramString, int paramInt)
    throws CSILException
  {
    String str = null;
    if (jdField_int)
    {
      str = (String)paramHashMap.get("DEMO_JAVA_CLASS");
      DebugLog.log(Level.FINEST, "CSIL Instantiating service using DEMO_JAVA_CLASS");
    }
    else
    {
      str = (String)paramHashMap.get("JAVA_CLASS");
      DebugLog.log(Level.FINEST, "CSIL Instantiating service using JAVA_CLASS");
    }
    if (str == null)
    {
      localObject = new CSILException(paramString, paramInt, "Missing service class for service.");
      DebugLog.throwing(paramString, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    DebugLog.log(Level.FINE, "CSIL Instantiating service using class " + str);
    Object localObject = null;
    try
    {
      Class localClass = Class.forName(str);
      localObject = localClass.newInstance();
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(paramInt, localException);
      DebugLog.throwing(paramString, localCSILException);
      throw localCSILException;
    }
    return localObject;
  }
  
  public static boolean useCompatibilityMode(HashMap paramHashMap)
  {
    boolean bool = false;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap != null)
      {
        String str = (String)localHashMap.get("UseCompatibilityMode");
        if (str != null) {
          bool = Boolean.valueOf(str).booleanValue();
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return bool;
  }
  
  public static String getGlobalPageSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap != null) {
        str = (String)localHashMap.get("PageSize");
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalBusinessDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("BusinessDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalUserDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("UserDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalConsumerDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("ConsumerDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalCaseDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("CaseDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalApplicationDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("ApplicationDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalEmployeeDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("EmployeeDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalAccountDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("AccountDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalACHCompanyDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("ACHCompanyDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalACHPayeeDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("ACHPayeeDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalLocationDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("LocationDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalBusinessMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("BusinessMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalUserMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("UserMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalWireTemplateResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("WireTemplateResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalConsumerMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("ConsumerMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalCaseMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("CaseMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalApplicationMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("ApplicationMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalEmployeeMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("EmployeeMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalAccountMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("AccountMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getGlobalACHCompanyMaxResultSize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("ACHCompanyMaxResultSize");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getReportMaxDisplay(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "250";
      }
      else
      {
        str = (String)localHashMap.get("ReportMaxDisplay");
        if ((str == null) || (str.equals(""))) {
          str = "250";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static String getNameConvention(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "dual";
      }
      else
      {
        str = (String)localHashMap.get("NameConvention");
        if ((str == null) || (str.equals(""))) {
          str = "dual";
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return str;
  }
  
  public static int getSignonNumLoginsBeforeLockout(HashMap paramHashMap, int paramInt)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = paramInt;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("NUM_LOGINS_BEFORE_LOCKOUT"));
        if (i1 < 0) {
          i1 = paramInt;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = paramInt;
    }
    return i1;
  }
  
  public static int getSignonLockoutDuration(HashMap paramHashMap, int paramInt)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = paramInt;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("LOCKOUT_DURATION"));
        if (i1 < 0) {
          i1 = paramInt;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = paramInt;
    }
    return i1;
  }
  
  public static int getMinLengthPassword(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 8;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("PASSWORD_MIN_LENGTH"));
        if (i1 < 0) {
          i1 = 8;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 8;
    }
    return i1;
  }
  
  public static int getMaxLengthPassword(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 15;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("PASSWORD_MAX_LENGTH"));
        if (i1 < 0) {
          i1 = 15;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 15;
    }
    return i1;
  }
  
  public static int getPwdMinDigits(HashMap paramHashMap)
  {
    int i1 = 1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap != null)
      {
        i1 = Integer.parseInt((String)localHashMap.get("PASSWORD_MIN_DIGITS"));
        if (i1 < 0) {
          i1 = 1;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 1;
    }
    return i1;
  }
  
  public static int getPwdMinLetters(HashMap paramHashMap)
  {
    int i1 = 2;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap != null)
      {
        i1 = Integer.parseInt((String)localHashMap.get("PASSWORD_MIN_LETTERS"));
        if (i1 < 0) {
          i1 = 2;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 2;
    }
    return i1;
  }
  
  public static int getPwdMinUpperCaseLetters(HashMap paramHashMap)
  {
    int i1 = 1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap != null)
      {
        i1 = Integer.parseInt((String)localHashMap.get("PASSWORD_MIN_UPPERCASE_LETTERS"));
        if (i1 < 0) {
          i1 = 1;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 1;
    }
    return i1;
  }
  
  public static boolean getPwdAllowSpecialChars(HashMap paramHashMap)
  {
    boolean bool = true;
    HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
    if (localHashMap != null) {
      bool = new Boolean((String)localHashMap.get("PASSWORD_ALLOW_SPECIAL_CHARS")).booleanValue();
    }
    return bool;
  }
  
  public static int getBCMinLengthPassword(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 9;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("BC_PASSWORD_MIN_LENGTH"));
        if (i1 < 0) {
          i1 = 9;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 9;
    }
    return i1;
  }
  
  public static int getBCMaxLengthPassword(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 15;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("BC_PASSWORD_MAX_LENGTH"));
        if (i1 < 0) {
          i1 = 15;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 15;
    }
    return i1;
  }
  
  public static int getBCPwdMinDigits(HashMap paramHashMap)
  {
    int i1 = 1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
      if (localHashMap != null)
      {
        i1 = Integer.parseInt((String)localHashMap.get("BC_PASSWORD_MIN_DIGITS"));
        if (i1 < 0) {
          i1 = 1;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 1;
    }
    return i1;
  }
  
  public static int getBCPwdMinLetters(HashMap paramHashMap)
  {
    int i1 = 2;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
      if (localHashMap != null)
      {
        i1 = Integer.parseInt((String)localHashMap.get("BC_PASSWORD_MIN_LETTERS"));
        if (i1 < 0) {
          i1 = 2;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 2;
    }
    return i1;
  }
  
  public static int getBCPwdMinUpperCaseLetters(HashMap paramHashMap)
  {
    int i1 = 1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
      if (localHashMap != null)
      {
        i1 = Integer.parseInt((String)localHashMap.get("BC_PASSWORD_MIN_UPPERCASE_LETTERS"));
        if (i1 < 0) {
          i1 = 1;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 1;
    }
    return i1;
  }
  
  public static boolean getBCPwdAllowSpecialChars(HashMap paramHashMap)
  {
    boolean bool = true;
    HashMap localHashMap = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
    if (localHashMap != null) {
      bool = new Boolean((String)localHashMap.get("BC_PASSWORD_ALLOW_SPECIAL_CHARS")).booleanValue();
    }
    return bool;
  }
  
  public static ArrayList getExemptJobTypeIds(HashMap paramHashMap)
  {
    String str1 = "HandlerUtil.getExemptJobTypeIds";
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap1 = (HashMap)paramHashMap.get("BC_SIGNON_SETTINGS");
    HashMap localHashMap2 = (HashMap)localHashMap1.get("EXEMPT_JOB_TYPES_LIST");
    ArrayList localArrayList2 = (ArrayList)localHashMap2.get("JOB");
    Iterator localIterator = localArrayList2.iterator();
    while (localIterator.hasNext())
    {
      HashMap localHashMap3 = (HashMap)localIterator.next();
      String str2 = (String)localHashMap3.get("NAME");
      int i1 = -1;
      try
      {
        i1 = Integer.parseInt((String)localHashMap3.get("SVC_BUREAU_ID"));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.throwing(str1, localNumberFormatException);
      }
      continue;
      try
      {
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroupByNameAndSvcBureau(str2, "EmployeeType", i1);
        Integer localInteger = new Integer(localEntitlementGroup.getGroupId());
        localArrayList1.add(localInteger);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.throwing(str1, localCSILException);
      }
    }
    return localArrayList1;
  }
  
  public static int getInactivityPeriod(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 90;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("INACTIVITY_PERIOD"));
        if (i1 < 0) {
          i1 = 90;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 90;
    }
    return i1;
  }
  
  public static int getPwdReuseOnTime(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 24;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("PWD_REUSE_ON_TIME"));
        if (i1 < 0) {
          i1 = 24;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 24;
    }
    return i1;
  }
  
  public static int getPwdReuseHistory(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 5;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("PWD_REUSE_HISTORY"));
        if (i1 < 0) {
          i1 = 5;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 5;
    }
    return i1;
  }
  
  public static int getPwdExpireTime(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 150;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("PWD_EXPIRE_TIME"));
        if (i1 < 0) {
          i1 = 150;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 150;
    }
    return i1;
  }
  
  public static int getPwdWarningTime(HashMap paramHashMap)
  {
    int i1 = -1;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null)
      {
        i1 = 14;
      }
      else
      {
        i1 = Integer.parseInt((String)localHashMap.get("PWD_EXPIRE_WARNING"));
        if (i1 < 0) {
          i1 = 14;
        }
      }
    }
    catch (Exception localException)
    {
      i1 = 14;
    }
    return i1;
  }
  
  public static boolean allowDuplicateUserNames(HashMap paramHashMap)
  {
    boolean bool = false;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null) {
        bool = false;
      } else {
        bool = Boolean.valueOf((String)localHashMap.get("DUPLICATE_USER_NAMES")).booleanValue();
      }
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  public static int getMaxInvalidCredentials(HashMap paramHashMap)
  {
    int i1 = 3;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("SIGNON_SETTINGS");
      if (localHashMap == null) {
        i1 = 3;
      } else {
        i1 = Integer.parseInt((String)localHashMap.get("MAX_NUM_INVALID_CREDENTIALS"));
      }
    }
    catch (Exception localException)
    {
      i1 = 3;
    }
    return i1;
  }
  
  public static String getGlobalPayeeDisplaySize(HashMap paramHashMap)
  {
    String str = null;
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap == null)
      {
        str = "15";
      }
      else
      {
        str = (String)localHashMap.get("GlobalPayeeDisplaySize");
        if ((str == null) || (str.equals(""))) {
          str = "15";
        }
      }
    }
    catch (ClassCastException localClassCastException)
    {
      str = "15";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.HandlerUtil
 * JD-Core Version:    0.7.0.1
 */