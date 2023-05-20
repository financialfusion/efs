package com.ffusion.util;

import java.util.HashMap;

public class CommBankIdentifier
{
  protected static boolean initialized = false;
  private static String jdField_for;
  private static boolean a = false;
  private static IBankIdentifier jdField_int = null;
  private static String[] jdField_do = null;
  private static String[] jdField_if = null;
  public static final String BANKID_DISPLAY_KEY = "BankIdentifier";
  public static final String BANKID_TASKADD = "TaskErrorsAdd";
  public static final String BANKID_SERVICEADD = "ServiceErrorsAdd";
  public static final String BANKID_DISPLAY_PREFIX = "BankIdentifierName_";
  public static final String BANKID_DISPLAY_DEFAULT = "RoutingNumber";
  public static final String BANKID_VALIDCLASSES = "BankIdentifierJavaClass";
  public static final String BANKID_VALIDCLASSES_DEFAULT = "com.ffusion.util.RoutingNumberUtil";
  public static final String GLOBAL_SETTINGS = "GLOBAL_SETTINGS";
  
  public static synchronized void initialize(HashMap paramHashMap)
    throws Exception
  {
    if (initialized) {
      return;
    }
    jdField_for = "BankIdentifierName_RoutingNumber";
    String str1 = "com.ffusion.util.RoutingNumberUtil";
    if (paramHashMap != null)
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
      if (localHashMap != null)
      {
        String str2 = (String)localHashMap.get("BankIdentifier");
        jdField_for = "BankIdentifierName_" + str2;
        if (!"RoutingNumber".equals(str2)) {
          a = true;
        }
        str1 = (String)localHashMap.get("BankIdentifierJavaClass");
        String str3 = (String)localHashMap.get("TaskErrorsAdd");
        if ((str3 != null) && (!"".equals(str3))) {
          jdField_do = str3.split(",");
        }
        str3 = (String)localHashMap.get("ServiceErrorsAdd");
        if ((str3 != null) && (!"".equals(str3))) {
          jdField_if = str3.split(",");
        }
      }
    }
    jdField_int = (IBankIdentifier)bankIDValidService(str1);
    initialized = true;
  }
  
  public static boolean validateBankIdentifier(String paramString)
    throws Exception
  {
    return jdField_int.validateBankIdentifier(paramString);
  }
  
  public static boolean isValid(String paramString)
    throws Exception
  {
    return jdField_int.isValid(paramString);
  }
  
  public static boolean isValidCheckZeros(String paramString)
    throws Exception
  {
    return jdField_int.isValidCheckZeros(paramString);
  }
  
  public static String getBankIdentifierKey()
  {
    return jdField_for;
  }
  
  public static boolean getBankIdentifierFlag()
  {
    return a;
  }
  
  public static boolean isValidAdditionalTaskCode(String paramString)
  {
    if ((a) && (paramString != null) && (jdField_do != null))
    {
      int i = jdField_do.length;
      for (int j = 0; j < i; j++) {
        if (paramString.trim().equals(jdField_do[j].trim())) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static boolean isValidAdditionalServiceCode(String paramString)
  {
    if ((a) && (paramString != null) && (jdField_if != null))
    {
      int i = jdField_if.length;
      for (int j = 0; j < i; j++) {
        if (paramString.trim().equals(jdField_if[j].trim())) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static Object bankIDValidService(String paramString)
    throws Exception
  {
    Object localObject = null;
    if ((paramString == null) || ("".equals(paramString))) {
      paramString = "com.ffusion.util.RoutingNumberUtil";
    }
    try
    {
      Class localClass1 = Class.forName(paramString);
      localObject = localClass1.newInstance();
    }
    catch (Exception localException)
    {
      paramString = "com.ffusion.util.RoutingNumberUtil";
      Class localClass2 = Class.forName(paramString);
      localObject = localClass2.newInstance();
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.CommBankIdentifier
 * JD-Core Version:    0.7.0.1
 */