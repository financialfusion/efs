package com.ffusion.util;

import java.util.StringTokenizer;

public class FieldValidation
{
  protected static final int[][] m_nDayTable = { { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 365 }, { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 366 } };
  
  public static boolean time(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ":");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (!num(str, false)) {
        return false;
      }
      if ((str.length() > 0) && (str.length() < 2)) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean string(String paramString, boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      for (i = 0; i < paramString.length(); i++) {
        if ((!Character.isLetter(paramString.charAt(i))) && (!Character.isWhitespace(paramString.charAt(i))) && (!a(paramString.charAt(i)))) {
          return false;
        }
      }
    } else {
      for (i = 0; i < paramString.length(); i++) {
        if ((!Character.isLetter(paramString.charAt(i))) && (!a(paramString.charAt(i)))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean num(String paramString, boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      for (i = 0; i < paramString.length(); i++) {
        if ((!Character.isDigit(paramString.charAt(i))) && (!Character.isWhitespace(paramString.charAt(i)))) {
          return false;
        }
      }
    } else {
      for (i = 0; i < paramString.length(); i++) {
        if (!Character.isDigit(paramString.charAt(i))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean strnum(String paramString1, String paramString2)
  {
    for (int i = 0; i < paramString1.length(); i++) {
      if ((!Character.isLetterOrDigit(paramString1.charAt(i))) && (!a(paramString1.charAt(i))))
      {
        int j = 0;
        for (int k = 0; k < paramString2.length(); k++) {
          if (paramString2.charAt(k) == paramString1.charAt(i))
          {
            j = 1;
            break;
          }
        }
        if (j == 0) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean currency(String paramString)
  {
    for (int i = 0; i < paramString.length(); i++) {
      if ((!Character.isDigit(paramString.charAt(i))) && ((paramString.charAt(i) != '$') || (i != 0)) && (paramString.charAt(i) != ',') && (paramString.charAt(i) != '.')) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean zipcode(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "-");
    String str = localStringTokenizer.nextToken();
    if (!num(str, false)) {
      return false;
    }
    if (str.length() != 5) {
      return false;
    }
    if (localStringTokenizer.hasMoreTokens())
    {
      str = localStringTokenizer.nextToken();
      if (!num(str, false)) {
        return false;
      }
      if (str.length() != 4) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean state(String paramString)
  {
    return true;
  }
  
  public static boolean email(String paramString)
  {
    int i = paramString.indexOf('@');
    if (i == -1) {
      return false;
    }
    if (!strnum(paramString.substring(0, i), ".")) {
      return false;
    }
    int j = paramString.indexOf('.', i);
    if (j == -1) {
      return false;
    }
    if (!strnum(paramString.substring(i + 1, j), ".")) {
      return false;
    }
    return strnum(paramString.substring(j + 1), "");
  }
  
  public static boolean ssn(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "-");
    int i = 0;
    if ((paramString.length() == 9) && (num(paramString, false))) {
      return true;
    }
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (num(str, false)) {
        i += str.length();
      } else {
        return false;
      }
    }
    return i == 9;
  }
  
  public static boolean phone(String paramString)
  {
    int i = paramString.length();
    if (((i == 7) || (i == 8) || (i == 10) || (i == 11) || (i == 12)) && (num(paramString, false))) {
      return true;
    }
    if (paramString.indexOf("(") != -1)
    {
      paramString = paramString.replace('(', '1');
      paramString = paramString.replace(')', '1');
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ".- ", false);
    int j = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (num(str, false)) {
        j += str.length();
      } else {
        return false;
      }
    }
    return (j == 7) || (j == 8) || (j == 10) || (j == 11) || (j == 12);
  }
  
  private static boolean a(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (!Character.isDigit(paramString.charAt(j))) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean date(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "/\\-,.");
    int[] arrayOfInt = new int[3];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 0;
    }
    for (i = 0; (localStringTokenizer.hasMoreTokens()) && (i < 3); i++)
    {
      String str = localStringTokenizer.nextToken();
      int j;
      try
      {
        j = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return false;
      }
      arrayOfInt[i] = j;
    }
    if (arrayOfInt[2] < 100) {
      if (arrayOfInt[2] > 70) {
        arrayOfInt[2] += 1900;
      } else {
        arrayOfInt[2] += 2000;
      }
    }
    return validateDate(arrayOfInt[2], arrayOfInt[0], arrayOfInt[1]);
  }
  
  public static boolean validateDate(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0) || (paramInt3 <= 0)) {
      return false;
    }
    if ((paramInt2 < 1) || (paramInt2 > 12)) {
      return false;
    }
    int i = isLeapYear(paramInt1) ? 1 : 0;
    return paramInt3 <= m_nDayTable[i][paramInt2];
  }
  
  public static boolean isLeapYear(int paramInt)
  {
    return ((paramInt % 4 == 0) && (paramInt % 100 != 0)) || (paramInt % 400 == 0);
  }
  
  private static boolean a(char paramChar)
  {
    boolean bool = false;
    switch (Character.getType(paramChar))
    {
    case 20: 
    case 23: 
    case 24: 
      bool = true;
      break;
    case 0: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    case 21: 
    case 22: 
    case 25: 
    case 26: 
    case 27: 
    case 28: 
    default: 
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.FieldValidation
 * JD-Core Version:    0.7.0.1
 */