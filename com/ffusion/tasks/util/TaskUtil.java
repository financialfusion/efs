package com.ffusion.tasks.util;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

public class TaskUtil
{
  public static final String TASK_ERROR_RESOURCE_BUNDLE = "com.ffusion.tasks.errors";
  public static final String SERVICE_ERROR_RESOURCE_BUNDLE = "com.ffusion.services.errors";
  public static final String RESOURCE_NAME_PREFIX = "Error";
  public static final String RESOURCE_NAME_ALERT_SUFFIX = "_alert";
  public static final String RESOURCE_NAME_DESC_SUFFIX = "_descr";
  public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
  public static final String OS_TYPE_WINDOWS = "Windows";
  public static final String OS_TYPE_UNIX = "Unix";
  
  public static int validateReportPeriod(String paramString1, String paramString2, String paramString3)
  {
    if (((paramString2 == null) || (paramString2.length() <= 0)) && ((paramString3 == null) || (paramString3.length() <= 0))) {
      return 0;
    }
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      paramString1 = "yyyy/MM/dd";
    }
    DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString1);
    Date localDate1 = null;
    Date localDate2 = null;
    try
    {
      if ((paramString2 != null) && (paramString2.length() > 0)) {
        localDate1 = localDateFormat.parse(paramString2);
      }
    }
    catch (ParseException localParseException1)
    {
      return 77;
    }
    try
    {
      if ((paramString3 != null) && (paramString3.length() > 0)) {
        localDate2 = localDateFormat.parse(paramString3);
      }
    }
    catch (ParseException localParseException2)
    {
      return 78;
    }
    if ((localDate1 == null) || (localDate2 == null)) {
      return 0;
    }
    if (localDate1.compareTo(localDate2) > 0) {
      return 79;
    }
    return 0;
  }
  
  public static String getOSType(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = "Unix";
    String str2 = paramHttpServletRequest.getHeader("user-agent");
    if (str2.indexOf("Windows") != -1) {
      str1 = "Windows";
    }
    return str1;
  }
  
  public static int validateLimitAmount(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0;
    }
    if (paramString.length() > 30) {
      return 86;
    }
    try
    {
      Float.parseFloat(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return 4543;
    }
    return 0;
  }
  
  public static String getErrorAlertResourceName(int paramInt)
  {
    return "Error" + paramInt + "_alert";
  }
  
  public static String getErrorDescriptionResourceName(int paramInt)
  {
    return "Error" + paramInt + "_descr";
  }
  
  public static String getTaskErrorDescription(int paramInt, Locale paramLocale)
  {
    String str = getErrorDescriptionResourceName(paramInt);
    return ResourceUtil.getString(str, "com.ffusion.tasks.errors", paramLocale);
  }
  
  public static String getServiceErrorDescription(int paramInt, Locale paramLocale)
  {
    String str = getErrorDescriptionResourceName(paramInt);
    return ResourceUtil.getString(str, "com.ffusion.services.errors", paramLocale);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.TaskUtil
 * JD-Core Version:    0.7.0.1
 */