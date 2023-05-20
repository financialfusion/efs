package com.ffusion.util;

import com.ffusion.util.logging.DebugLog;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;

public class LocaleUtil
{
  public static final char LOCALE_INFO_SEPARATOR = '_';
  private static final String a = ".xml";
  private static final String jdField_int = "windows";
  private static final HashSet jdField_for;
  private static final HashSet jdField_do;
  private static final Locale jdField_if = ;
  
  public static ArrayList getXMLFilesWithBase(String paramString)
  {
    return getXMLFilesWithBase(paramString, true);
  }
  
  public static ArrayList getXMLFilesWithBase(String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = null;
    URL localURL = null;
    File localFile = null;
    String[] arrayOfString = null;
    String str = null;
    localStringBuffer = new StringBuffer(paramString);
    localStringBuffer.append(".xml");
    localURL = ResourceUtil.findResourceURL(new Object(), localStringBuffer.toString());
    if (localURL == null)
    {
      System.out.println("LocaleUtil:Resource file not found : " + localStringBuffer.toString());
    }
    else
    {
      str = localURL.getPath();
      str = str.substring(0, str.lastIndexOf(localStringBuffer.toString()));
      localFile = new File(str);
      arrayOfString = localFile.list();
      if (arrayOfString != null) {
        for (int i = 0; i < arrayOfString.length; i++) {
          if (isLocaleSpecificXMLFileWithBase(paramString, arrayOfString[i], paramBoolean)) {
            localArrayList.add(arrayOfString[i]);
          }
        }
      }
    }
    return localArrayList;
  }
  
  public static boolean isLocaleSpecificXMLFileWithBase(String paramString1, String paramString2)
  {
    return isLocaleSpecificXMLFileWithBase(paramString1, paramString2, true);
  }
  
  public static boolean isLocaleSpecificXMLFileWithBase(String paramString1, String paramString2, boolean paramBoolean)
  {
    boolean bool = true;
    int i = 1;
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    localStringBuffer.append(".xml");
    if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      if (localStringBuffer.toString().equals(paramString2)) {
        bool = false;
      } else if (!paramString2.startsWith(paramString1)) {
        bool = false;
      } else if (!paramString2.endsWith(".xml")) {
        bool = false;
      }
    }
    else if (localStringBuffer.toString().equalsIgnoreCase(paramString2)) {
      bool = false;
    } else if (!paramString2.toLowerCase().startsWith(paramString1.toLowerCase())) {
      bool = false;
    } else if (!paramString2.toLowerCase().endsWith(".xml".toLowerCase())) {
      bool = false;
    }
    if (bool)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString2.substring(paramString1.length() + 1), "_");
      int j = localStringTokenizer.countTokens();
      if ((j == 1) || (j == 2))
      {
        String str1 = null;
        String str2 = null;
        while (localStringTokenizer.hasMoreTokens()) {
          if (str1 == null)
          {
            str1 = localStringTokenizer.nextToken();
          }
          else
          {
            str2 = localStringTokenizer.nextToken();
            str2 = str2.substring(0, str2.length() - ".xml".length());
          }
        }
        if (str2 == null) {
          str1 = str1.substring(0, str1.length() - ".xml".length());
        }
        if (paramBoolean)
        {
          if (!isValidLanguageCode(str1))
          {
            bool = false;
            DebugLog.log("The file '" + paramString2 + "' contains an invalid language code.");
          }
          if (!isValidCountryCode(str2))
          {
            bool = false;
            DebugLog.log("The file '" + paramString2 + "' contains an invalid country code.");
          }
        }
      }
      else
      {
        bool = false;
      }
    }
    return bool;
  }
  
  public static String getLocaleIDFromXMLFileName(String paramString1, String paramString2)
  {
    String str1 = "";
    String str2 = paramString1;
    str2 = str2.substring(paramString2.length());
    int i = str2.indexOf('_');
    int j = str2.toLowerCase().lastIndexOf(".xml".toLowerCase());
    if ((i != -1) && (j != -1) && (i < j))
    {
      StringTokenizer localStringTokenizer = null;
      StringBuffer localStringBuffer = new StringBuffer();
      String str3 = null;
      String str4 = null;
      str1 = str2.substring(i + 1, j);
      localStringTokenizer = new StringTokenizer(str1, "_");
      while (localStringTokenizer.hasMoreTokens()) {
        if (str3 == null)
        {
          str3 = localStringTokenizer.nextToken().toLowerCase();
          localStringBuffer.append(str3);
        }
        else
        {
          str4 = localStringTokenizer.nextToken().toUpperCase();
          localStringBuffer.append('_');
          localStringBuffer.append(str4);
        }
      }
      str1 = localStringBuffer.toString();
    }
    return str1;
  }
  
  public static Locale getLocaleFromLocaleID(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return getDefaultLocale();
    }
    int i = paramString.indexOf('_');
    String str1 = (i < 0) || (i >= paramString.length() - 1) ? paramString : paramString.substring(0, i);
    String str2 = (i < 0) || (i >= paramString.length() - 1) ? null : paramString.substring(i + 1);
    Locale localLocale = (str2 == null) || (str2.length() <= 0) ? new Locale(str1) : new Locale(str1, str2);
    return localLocale;
  }
  
  public static boolean isValidLanguageCode(String paramString)
  {
    boolean bool = true;
    if ((paramString != null) && (paramString.length() > 0) && (!jdField_do.contains(paramString.toLowerCase()))) {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isValidCountryCode(String paramString)
  {
    boolean bool = true;
    if ((paramString != null) && (paramString.length() > 0) && (!jdField_for.contains(paramString.toUpperCase()))) {
      bool = false;
    }
    return bool;
  }
  
  public static Locale getDefaultLocale()
  {
    return jdField_if;
  }
  
  static
  {
    String[] arrayOfString1 = Locale.getISOCountries();
    String[] arrayOfString2 = Locale.getISOLanguages();
    jdField_for = new HashSet(arrayOfString1.length);
    jdField_do = new HashSet(arrayOfString2.length);
    for (int i = 0; i < arrayOfString1.length; i++) {
      jdField_for.add(arrayOfString1[i]);
    }
    for (i = 0; i < arrayOfString2.length; i++) {
      jdField_do.add(arrayOfString2[i]);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.LocaleUtil
 * JD-Core Version:    0.7.0.1
 */