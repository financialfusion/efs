package com.ffusion.util.settings;

import com.ffusion.util.ResourceUtil;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;

public class AccountSettings
{
  public static String buildAccountDisplayText(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Locale paramLocale)
    throws SystemException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    try
    {
      str2 = ResourceUtil.getString(paramString3, paramString2, paramLocale);
    }
    catch (MissingResourceException localMissingResourceException1)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString3 + "' from resource bundle '" + paramString2 + "'.", localMissingResourceException1);
    }
    catch (ClassCastException localClassCastException1)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString3 + "' from resource bundle '" + paramString2 + "'.", localClassCastException1);
    }
    try
    {
      str3 = ResourceUtil.getString(paramString5, paramString4, paramLocale);
    }
    catch (MissingResourceException localMissingResourceException2)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString5 + "' from resource bundle '" + paramString4 + "'.", localMissingResourceException2);
    }
    catch (ClassCastException localClassCastException2)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString5 + "' from resource bundle '" + paramString4 + "'.", localClassCastException2);
    }
    if ((str2 == null) || (str2.length() == 0)) {
      throw new SystemException("The resource, '" + paramString3 + "', is missing from the resource bundle, '" + paramString2 + "'.");
    }
    if ((str3 == null) || (str3.length() == 0)) {
      throw new SystemException("The resource, '" + paramString5 + "', is missing from the resource bundle, '" + paramString4 + "'.");
    }
    str1 = MessageFormat.format(str2, new Object[] { paramString1, str3 });
    return str1;
  }
  
  public static String buildAccountDisplayTextForExport(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, Locale paramLocale)
    throws SystemException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    try
    {
      str4 = paramString4 + "_" + paramString1 + "_" + paramInt;
      str2 = ResourceUtil.getString(str4, paramString3, paramLocale);
    }
    catch (MissingResourceException localMissingResourceException1)
    {
      try
      {
        str4 = paramString4 + "_" + paramString1;
        str2 = ResourceUtil.getString(str4, paramString3, paramLocale);
      }
      catch (MissingResourceException localMissingResourceException3)
      {
        try
        {
          str4 = paramString4;
          str2 = ResourceUtil.getString(str4, paramString3, paramLocale);
        }
        catch (MissingResourceException localMissingResourceException4)
        {
          throw new SystemException("An error occurred while attempting to load resource '" + paramString4 + "' from resource bundle '" + paramString3 + "'.", localMissingResourceException4);
        }
      }
    }
    catch (ClassCastException localClassCastException1)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString4 + "' from resource bundle '" + paramString3 + "'.", localClassCastException1);
    }
    try
    {
      str3 = ResourceUtil.getString(paramString6, paramString5, paramLocale);
    }
    catch (MissingResourceException localMissingResourceException2)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString6 + "' from resource bundle '" + paramString5 + "'.", localMissingResourceException2);
    }
    catch (ClassCastException localClassCastException2)
    {
      throw new SystemException("An error occurred while attempting to load resource '" + paramString6 + "' from resource bundle '" + paramString5 + "'.", localClassCastException2);
    }
    if ((str2 == null) || (str2.length() == 0)) {
      throw new SystemException("The resource, '" + str4 + "', is missing from the resource bundle, '" + paramString3 + "'.");
    }
    if ((str3 == null) || (str3.length() == 0)) {
      throw new SystemException("The resource, '" + paramString6 + "', is missing from the resource bundle, '" + paramString5 + "'.");
    }
    str1 = MessageFormat.format(str2, new Object[] { paramString2, str3 });
    return str1;
  }
  
  public static String buildAccountId(String paramString1, String paramString2)
    throws SystemException
  {
    switch ()
    {
    case 1: 
      return paramString1;
    case 2: 
      return paramString1 + "-" + paramString2;
    }
    throw new SystemException("Invalid or unknown setting");
  }
  
  public static String getMaskedAccountNumber(String paramString, int paramInt, char paramChar)
    throws SystemException
  {
    String str = "";
    for (int i = 1; i <= paramString.length(); i++)
    {
      char c = paramString.charAt(paramString.length() - i);
      boolean bool = Character.isLetterOrDigit(c);
      if ((paramInt > 0) && (bool))
      {
        paramInt--;
        str = c + str;
      }
      else if (!bool)
      {
        str = c + str;
      }
      else
      {
        str = paramChar + str;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.settings.AccountSettings
 * JD-Core Version:    0.7.0.1
 */