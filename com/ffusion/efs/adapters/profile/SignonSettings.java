package com.ffusion.efs.adapters.profile;

import com.ffusion.csil.handlers.HandlerUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class SignonSettings
{
  private static int a;
  private static int jdField_long;
  private static int d;
  private static int f;
  private static int jdField_void;
  private static int jdField_char;
  private static int jdField_new;
  private static boolean jdField_for;
  private static int jdField_do;
  private static int c;
  private static int jdField_case;
  private static int jdField_null;
  private static int jdField_if;
  private static boolean jdField_else;
  private static ArrayList h = null;
  private static int jdField_goto;
  private static int b;
  private static int jdField_byte;
  private static int jdField_int;
  private static int jdField_try;
  private static boolean g;
  private static int e;
  
  public static void initialize(HashMap paramHashMap)
  {
    Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    int i = Integer.parseInt(Profile.properties.getProperty("LOGIN_RETRIES", "3"));
    int j = Integer.parseInt(Profile.properties.getProperty("LOCKOUT_TIME", "60"));
    a = HandlerUtil.getSignonNumLoginsBeforeLockout(paramHashMap, i);
    jdField_long = HandlerUtil.getSignonLockoutDuration(paramHashMap, j);
    d = HandlerUtil.getMinLengthPassword(paramHashMap);
    f = HandlerUtil.getMaxLengthPassword(paramHashMap);
    jdField_void = HandlerUtil.getPwdMinDigits(paramHashMap);
    jdField_char = HandlerUtil.getPwdMinLetters(paramHashMap);
    jdField_new = HandlerUtil.getPwdMinUpperCaseLetters(paramHashMap);
    jdField_for = HandlerUtil.getPwdAllowSpecialChars(paramHashMap);
    jdField_do = HandlerUtil.getBCMinLengthPassword(paramHashMap);
    c = HandlerUtil.getBCMaxLengthPassword(paramHashMap);
    jdField_case = HandlerUtil.getBCPwdMinDigits(paramHashMap);
    jdField_null = HandlerUtil.getBCPwdMinLetters(paramHashMap);
    jdField_if = HandlerUtil.getBCPwdMinUpperCaseLetters(paramHashMap);
    jdField_else = HandlerUtil.getBCPwdAllowSpecialChars(paramHashMap);
    h = HandlerUtil.getExemptJobTypeIds(paramHashMap);
    jdField_goto = HandlerUtil.getInactivityPeriod(paramHashMap);
    b = HandlerUtil.getPwdReuseOnTime(paramHashMap);
    jdField_byte = HandlerUtil.getPwdReuseHistory(paramHashMap);
    jdField_int = HandlerUtil.getPwdExpireTime(paramHashMap);
    jdField_try = HandlerUtil.getPwdWarningTime(paramHashMap);
    g = HandlerUtil.allowDuplicateUserNames(paramHashMap);
    e = HandlerUtil.getMaxInvalidCredentials(paramHashMap);
  }
  
  public static int getNumLoginsBeforeLockout()
  {
    return a;
  }
  
  public static int getLockoutDuration()
  {
    return jdField_long;
  }
  
  public static int getMinPasswordLength()
  {
    return d;
  }
  
  public static int getMaxPasswordLength()
  {
    return f;
  }
  
  public static int getMinPasswordDigits()
  {
    return jdField_void;
  }
  
  public static int getMinPasswordLetters()
  {
    return jdField_char;
  }
  
  public static int getMinPasswordUpperCaseLetters()
  {
    return jdField_new;
  }
  
  public static boolean getPasswordAllowSpecialChars()
  {
    return jdField_for;
  }
  
  public static int getBCMinPasswordLength()
  {
    return jdField_do;
  }
  
  public static int getBCMaxPasswordLength()
  {
    return c;
  }
  
  public static int getBCMinPasswordDigits()
  {
    return jdField_case;
  }
  
  public static int getBCMinPasswordLetters()
  {
    return jdField_null;
  }
  
  public static int getBCMinPasswordUpperCaseLetters()
  {
    return jdField_if;
  }
  
  public static boolean getBCPasswordAllowSpecialChars()
  {
    return jdField_else;
  }
  
  public static ArrayList getExemptJobTypeIds()
  {
    return h;
  }
  
  public static int getInactivityPeriod()
  {
    return jdField_goto;
  }
  
  public static int getPwdReuseOnTime()
  {
    return b;
  }
  
  public static int getPwdReuseHistory()
  {
    return jdField_byte;
  }
  
  public static int getPwdExpireTime()
  {
    return jdField_int;
  }
  
  public static int getPwdWarningTime()
  {
    return jdField_try;
  }
  
  public static boolean allowDuplicateUserNames()
  {
    return g;
  }
  
  public static int getMaxInvalidCredentialsBeforeLogout()
  {
    return e;
  }
  
  public static void setAllowDuplicateUserNames(boolean paramBoolean)
  {
    g = paramBoolean;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.SignonSettings
 * JD-Core Version:    0.7.0.1
 */