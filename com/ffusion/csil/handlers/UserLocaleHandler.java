package com.ffusion.csil.handlers;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.UserLocaleConsts;
import java.util.HashMap;
import java.util.Locale;

public class UserLocaleHandler
  implements UserLocaleConsts
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {}
  
  public static UserLocale getUserLocale(String paramString1, String paramString2)
    throws CSILException
  {
    UserLocale localUserLocale = new UserLocale(paramString1, paramString2);
    try
    {
      Locale localLocale = new Locale(paramString1, paramString2);
      localUserLocale.setLanguage(paramString1);
      localUserLocale.setCountry(paramString2);
      localUserLocale.setDateFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setDateFormatName(ResourceUtil.getString("DateFormatName", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setDateTimeFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", localLocale) + " " + ResourceUtil.getString("TimeFormat", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setDateTimeFormatName(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", localLocale) + " " + ResourceUtil.getString("TimeFormat", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setZoneFormat(ResourceUtil.getString("ZoneFormat", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setZoneFormatName(ResourceUtil.getString("ZoneFormatName", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setTimeFormat(ResourceUtil.getString("TimeFormat", "com.ffusion.beans.user.resources", localLocale));
      localUserLocale.setTimeFormatName(ResourceUtil.getString("TimeFormatName", "com.ffusion.beans.user.resources", localLocale));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new CSILException("UserLocaleCore.getUserLocale", 70000, localException);
    }
    return localUserLocale;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.UserLocaleHandler
 * JD-Core Version:    0.7.0.1
 */