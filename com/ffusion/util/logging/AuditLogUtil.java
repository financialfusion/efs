package com.ffusion.util.logging;

import com.ffusion.util.ResourceUtil;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

public class AuditLogUtil
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  public static final String TRAN_MODULE = "TransactionTypeModule";
  public static final String TRAN_MODULE_NAME = "TransactionModuleName";
  public static final String TRAN_MODULE_ID = "TransactionModuleId";
  public static final String ACTIVITY_NAME = "TransactionTypeText";
  public static final String UNDER_SCORE = "_";
  public static final int UNKNOWN_MODULE = 0;
  
  public static int getModuleFromTranType(int paramInt)
  {
    int i;
    try
    {
      String str = ResourceUtil.getString("TransactionTypeModule" + paramInt, "com.ffusion.beans.banking.resources", Locale.US);
      i = Integer.parseInt(str);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      i = 0;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 0;
    }
    return i;
  }
  
  public static String getModuleNameFromModule(int paramInt)
  {
    return getModuleNameFromModule(paramInt, Locale.US);
  }
  
  public static String getModuleNameFromModule(int paramInt, Locale paramLocale)
  {
    String str = "";
    try
    {
      str = ResourceUtil.getString("TransactionModuleName" + paramInt, "com.ffusion.beans.banking.resources", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException) {}
    return str;
  }
  
  public static int getModuleIdFromModuleName(String paramString)
  {
    return getModuleIdFromModuleName(paramString, Locale.US);
  }
  
  public static int getModuleIdFromModuleName(String paramString, Locale paramLocale)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(ResourceUtil.getString(a(paramString), "com.ffusion.beans.banking.resources", paramLocale));
    }
    catch (Exception localException) {}
    return i;
  }
  
  public static String getActivityNameFromID(String paramString)
  {
    return getActivityNameFromID(paramString, Locale.US);
  }
  
  public static String getActivityNameFromID(String paramString, Locale paramLocale)
  {
    String str = "";
    try
    {
      str = ResourceUtil.getString("TransactionTypeText" + paramString, "com.ffusion.beans.banking.resources", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException) {}
    return str;
  }
  
  private static String a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("TransactionModuleId");
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, " ");
    for (int i = 0; localStringTokenizer.hasMoreTokens(); i++)
    {
      if (i > 0) {
        localStringBuffer.append("_");
      }
      localStringBuffer.append(localStringTokenizer.nextToken());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditLogUtil
 * JD-Core Version:    0.7.0.1
 */