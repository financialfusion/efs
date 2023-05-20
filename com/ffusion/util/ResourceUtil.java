package com.ffusion.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceUtil
{
  private static Map a = Collections.synchronizedMap(new HashMap());
  
  public static String getString(String paramString1, String paramString2, Locale paramLocale)
  {
    String str1 = "";
    ResourceBundle localResourceBundle = null;
    String str2 = paramString2 + paramLocale.toString();
    localResourceBundle = (ResourceBundle)a.get(str2);
    if (localResourceBundle == null) {
      try
      {
        localResourceBundle = ResourceBundle.getBundle(paramString2, paramLocale);
        if (localResourceBundle != null) {
          a.put(str2, localResourceBundle);
        }
      }
      catch (Exception localException)
      {
        localResourceBundle = null;
      }
    }
    if (localResourceBundle != null) {
      str1 = localResourceBundle.getString(paramString1);
    }
    return str1;
  }
  
  public static ResourceBundle getBundle(String paramString, Locale paramLocale)
  {
    ResourceBundle localResourceBundle = null;
    String str = paramString + paramLocale.toString();
    localResourceBundle = (ResourceBundle)a.get(str);
    if (localResourceBundle == null) {
      try
      {
        localResourceBundle = ResourceBundle.getBundle(paramString, paramLocale);
        if (localResourceBundle != null) {
          a.put(str, localResourceBundle);
        }
      }
      catch (Exception localException)
      {
        localResourceBundle = null;
      }
    }
    return localResourceBundle;
  }
  
  public static URL findResourceURL(Object paramObject, String paramString)
  {
    URL localURL = null;
    try
    {
      localURL = paramObject.getClass().getClassLoader().getResource(paramString);
    }
    catch (Exception localException) {}
    if (localURL == null) {
      localURL = ClassLoader.getSystemResource(paramString);
    }
    if (localURL == null)
    {
      ClassLoader.getSystemClassLoader();
      localURL = ClassLoader.getSystemResource(paramString);
    }
    if (localURL == null) {
      localURL = Thread.currentThread().getContextClassLoader().getResource(paramString);
    }
    if (localURL == null)
    {
      ResourceUtil localResourceUtil = new ResourceUtil();
      localURL = localResourceUtil.getClass().getClassLoader().getResource(paramString);
    }
    return localURL;
  }
  
  public static InputStream getResourceAsStream(Object paramObject, String paramString)
  {
    InputStream localInputStream = null;
    try
    {
      URL localURL = findResourceURL(paramObject, paramString);
      if (localURL != null) {
        localInputStream = localURL.openStream();
      }
    }
    catch (Exception localException) {}
    return localInputStream;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ResourceUtil
 * JD-Core Version:    0.7.0.1
 */