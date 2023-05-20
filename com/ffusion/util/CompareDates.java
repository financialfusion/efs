package com.ffusion.util;

import com.ffusion.util.beans.DateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class CompareDates
{
  public static int DATE_BEFORE = -1;
  public static int DATE_AFTER = 1;
  public static int DATE_EQUALS = 0;
  
  public static boolean isDateinRange(Calendar paramCalendar, String paramString1, String paramString2)
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
    if ((adjustDateFromPattern(localGregorianCalendar1, paramString1) == 0) && (adjustDateFromPattern(localGregorianCalendar2, paramString2) == 0)) {
      return (compareDates(paramCalendar, localGregorianCalendar1) != DATE_BEFORE) && (compareDates(paramCalendar, localGregorianCalendar2) != DATE_AFTER);
    }
    return false;
  }
  
  public static int adjustDateFromPattern(Calendar paramCalendar, String paramString)
  {
    if ((paramCalendar != null) && (paramString != null))
    {
      paramString = paramString.trim();
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      for (int i = 0; localStringTokenizer.hasMoreTokens(); i++)
      {
        String str1 = localStringTokenizer.nextToken().trim();
        int j = str1.indexOf(' ');
        if (j != -1) {
          try
          {
            String str2 = str1.substring(0, j);
            int k = 0;
            if (str2.charAt(0) == '+') {
              k = Integer.parseInt(str2.substring(1));
            } else {
              k = Integer.parseInt(str2);
            }
            String str3 = str1.substring(j + 1);
            if ((str3.equalsIgnoreCase("MONTHS")) || (str3.equalsIgnoreCase("MONTH"))) {
              paramCalendar.add(2, k);
            } else if ((str3.equalsIgnoreCase("DAYS")) || (str3.equalsIgnoreCase("DAY"))) {
              paramCalendar.add(5, k);
            } else if ((str3.equalsIgnoreCase("YEARS")) || (str3.equalsIgnoreCase("YEAR"))) {
              paramCalendar.add(1, k);
            } else {
              return i + 1;
            }
          }
          catch (Throwable localThrowable)
          {
            return i + 1;
          }
        } else {
          return i + 1;
        }
      }
    }
    return 0;
  }
  
  public static int compareDates(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    if (paramCalendar1.get(1) > paramCalendar2.get(1)) {
      return DATE_AFTER;
    }
    if (paramCalendar1.get(1) < paramCalendar2.get(1)) {
      return DATE_BEFORE;
    }
    if (paramCalendar1.get(2) > paramCalendar2.get(2)) {
      return DATE_AFTER;
    }
    if (paramCalendar1.get(2) < paramCalendar2.get(2)) {
      return DATE_BEFORE;
    }
    if (paramCalendar1.get(5) > paramCalendar2.get(5)) {
      return DATE_AFTER;
    }
    if (paramCalendar1.get(5) < paramCalendar2.get(5)) {
      return DATE_BEFORE;
    }
    return DATE_EQUALS;
  }
  
  public static void updateDateToEndOfDay(Calendar paramCalendar)
  {
    if (paramCalendar != null)
    {
      paramCalendar.set(11, 23);
      paramCalendar.set(12, 59);
      paramCalendar.set(13, 59);
    }
  }
  
  public static void updateDateToStartOfDay(Calendar paramCalendar)
  {
    if (paramCalendar != null)
    {
      paramCalendar.set(11, 0);
      paramCalendar.set(12, 0);
      paramCalendar.set(13, 0);
    }
  }
  
  public static Calendar convertDateStrToCal(String paramString1, Locale paramLocale, String paramString2)
  {
    if ((paramString1 != null) && (paramLocale != null) && (paramString2 != null)) {
      try
      {
        DateTime localDateTime = new DateTime(paramString1, paramLocale, paramString2);
        localDateTime.fromString(paramString1);
        return localDateTime;
      }
      catch (Throwable localThrowable) {}
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.CompareDates
 * JD-Core Version:    0.7.0.1
 */