package com.ffusion.util;

public class HfnUtil
{
  public static String wordCap(String paramString)
  {
    paramString = paramString.trim();
    String str = paramString.substring(0, 1).toUpperCase();
    paramString = str + paramString.substring(1);
    int i = -1;
    while ((i = paramString.indexOf(" ", i + 1)) != -1)
    {
      str = paramString.substring(i + 1, i + 2).toUpperCase();
      paramString = paramString.substring(0, i + 1) + str + paramString.substring(i + 2);
    }
    i = -1;
    while ((i = paramString.indexOf("-", i + 1)) != -1)
    {
      str = paramString.substring(i + 1, i + 2).toUpperCase();
      paramString = paramString.substring(0, i + 1) + str + paramString.substring(i + 2);
    }
    return paramString;
  }
  
  public static String ensureSlash(String paramString)
  {
    int i = paramString.length();
    if ((i > 0) && (paramString.charAt(i - 1) != '/')) {
      paramString = paramString + "/";
    }
    return paramString;
  }
  
  public static String genNumber(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    String str = "";
    char c1 = '#';
    int k = 0;
    while (j < i)
    {
      char c2 = paramString.charAt(j);
      if (c2 == c1)
      {
        k++;
      }
      else if (k != 0)
      {
        if (c1 == '#') {
          str = str + generateNewNumber(k);
        } else if (c1 == '@') {
          str = str + generateAlpha(k);
        } else {
          str = str + String.valueOf(c1);
        }
        k = 1;
        c1 = c2;
      }
      else
      {
        c1 = c2;
        k = 1;
      }
      j++;
    }
    if (k > 0) {
      if (c1 == '#') {
        str = str + generateNewNumber(k);
      } else if (c1 == '@') {
        str = str + generateAlpha(k);
      } else {
        str = str + String.valueOf(c1);
      }
    }
    return str;
  }
  
  public static String generateNewNumber(int paramInt)
  {
    double d = 0.0D;
    String str = "";
    for (int j = 0; j < paramInt; j++)
    {
      d = Math.random();
      d *= 1000.0D;
      int i = (int)d % 9;
      str = str + String.valueOf(i);
    }
    return str;
  }
  
  public static String generateAlpha(int paramInt)
  {
    String[] arrayOfString = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
    double d = 0.0D;
    String str = "";
    for (int j = 0; j < paramInt; j++)
    {
      d = Math.random();
      d *= 1000.0D;
      int i = (int)d % 35;
      str = str + arrayOfString[i];
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.HfnUtil
 * JD-Core Version:    0.7.0.1
 */