package com.ffusion.util;

public class HTMLUtil
{
  public static String encode(String paramString)
  {
    if (paramString != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      char[] arrayOfChar = paramString.toCharArray();
      for (int i = 0; i < arrayOfChar.length; i++) {
        localStringBuffer.append(encode(arrayOfChar[i]));
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  public static String encode(char paramChar)
  {
    String str;
    switch (paramChar)
    {
    case '<': 
      str = "&lt;";
      break;
    case '>': 
      str = "&gt;";
      break;
    case '&': 
      str = "&amp;";
      break;
    case '"': 
      str = "&#34;";
      break;
    case '\'': 
      str = "&#39;";
      break;
    default: 
      str = String.valueOf(paramChar);
    }
    return str;
  }
  
  public static String encodeLeadingSpaces(String paramString)
  {
    int i = 0;
    StringBuffer localStringBuffer = null;
    if ((paramString != null) && (paramString.length() > 0))
    {
      localStringBuffer = new StringBuffer(2 * paramString.length());
      i = 0;
      while ((i < paramString.length()) && (paramString.charAt(i) == ' '))
      {
        i++;
        localStringBuffer.append("&nbsp;");
      }
      localStringBuffer.append(paramString.substring(i));
      return localStringBuffer.toString();
    }
    return paramString;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.HTMLUtil
 * JD-Core Version:    0.7.0.1
 */