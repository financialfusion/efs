package com.ffusion.beans.util;

import com.ffusion.util.ResourceUtil;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;

public class UserUtil
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.user.resources";
  public static final String KEY_FULL_NAME_FORMAT = "FULL_NAME_FORMAT";
  public static final String KEY_FULL_NAME_WITH_ID_FORMAT = "FULL_NAME_WITH_ID_FORMAT";
  public static final String KEY_FULL_NAME_SORTABLE_FORMAT = "FULL_NAME_SORTABLE_FORMAT";
  public static final String KEY_FULL_NAME_WITH_ID_SORTABLE_FORMAT = "FULL_NAME_WITH_ID_SORTABLE_FORMAT";
  
  public static String getFullName(String paramString1, String paramString2, Locale paramLocale)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("FULL_NAME_FORMAT", "com.ffusion.beans.user.resources", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = "{0} {1}";
    }
    return MessageFormat.format(str, new Object[] { paramString1, paramString2 });
  }
  
  public static String getFullNameWithLoginId(String paramString1, String paramString2, String paramString3, String paramString4, Locale paramLocale)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("FULL_NAME_WITH_ID_FORMAT", "com.ffusion.beans.user.resources", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = "{0} {1} ({2})";
    }
    return MessageFormat.format(str, new Object[] { paramString1, paramString2, paramString3, paramString4 });
  }
  
  public static String getSortableFullName(String paramString1, String paramString2, Locale paramLocale)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("FULL_NAME_SORTABLE_FORMAT", "com.ffusion.beans.user.resources", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = "{1}, {0}";
    }
    return MessageFormat.format(str, new Object[] { paramString1, paramString2 });
  }
  
  public static String getSortableFullNameWithLoginId(String paramString1, String paramString2, String paramString3, String paramString4, Locale paramLocale)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("FULL_NAME_WITH_ID_SORTABLE_FORMAT", "com.ffusion.beans.user.resources", paramLocale);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = "{1}, {0} ({2})";
    }
    return MessageFormat.format(str, new Object[] { paramString1, paramString2, paramString3, paramString4 });
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.UserUtil
 * JD-Core Version:    0.7.0.1
 */