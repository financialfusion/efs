package com.ffusion.util;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtil
{
  private static HashMap a = new HashMap();
  public static final String SHORT = "SHORT";
  public static final String MEDIUM = "MEDIUM";
  public static final String LONG = "LONG";
  public static final String FULL = "FULL";
  public static final String START_DAY = "00:00:00";
  public static final String END_DAY = "23:59:59";
  public static final String DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy";
  public static final String DATE_TIME_FORMAT_MMDDYYYY_HHMMSS = "MM/dd/yyyy HH:mm:ss";
  
  public static DateFormat getFormatter(String paramString)
  {
    return getFormatter(paramString, LocaleUtil.getDefaultLocale());
  }
  
  public static DateFormat getFormatter(String paramString, Locale paramLocale)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "SHORT";
    }
    String str = paramString + paramLocale.toString();
    Object localObject = (DateFormat)a.get(str);
    if (localObject == null)
    {
      int i = -1;
      if (paramString.equalsIgnoreCase("FULL")) {
        i = 0;
      } else if (paramString.equalsIgnoreCase("MEDIUM")) {
        i = 2;
      } else if (paramString.equalsIgnoreCase("LONG")) {
        i = 1;
      } else if (paramString.equalsIgnoreCase("SHORT")) {
        i = 3;
      }
      if (i != -1) {
        localObject = new FFIDateFormat(-1, i, paramLocale);
      } else {
        localObject = new FFIDateFormat(paramString, paramLocale);
      }
      ((DateFormat)localObject).setLenient(false);
      a.put(str, localObject);
    }
    return localObject;
  }
  
  public static DateFormat getFormatter(String paramString, Locale paramLocale, TimeZone paramTimeZone)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "SHORT";
    }
    String str = paramString + paramLocale.toString() + paramTimeZone.getID();
    Object localObject = (DateFormat)a.get(str);
    if (localObject == null)
    {
      int i = -1;
      if (paramString.equalsIgnoreCase("FULL")) {
        i = 0;
      } else if (paramString.equalsIgnoreCase("MEDIUM")) {
        i = 2;
      } else if (paramString.equalsIgnoreCase("LONG")) {
        i = 1;
      } else if (paramString.equalsIgnoreCase("SHORT")) {
        i = 3;
      }
      if (i != -1) {
        localObject = new FFIDateFormat(-1, i, paramLocale, paramTimeZone);
      } else {
        localObject = new FFIDateFormat(paramString, paramLocale, paramTimeZone);
      }
      ((DateFormat)localObject).setLenient(false);
      a.put(str, localObject);
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.DateFormatUtil
 * JD-Core Version:    0.7.0.1
 */