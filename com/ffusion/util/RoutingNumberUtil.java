package com.ffusion.util;

import java.util.HashMap;

public class RoutingNumberUtil
  implements IBankIdentifier
{
  private static final int[] jdField_if = { 3, 7, 1 };
  
  public void initialize(HashMap paramHashMap)
    throws Exception
  {}
  
  public boolean validateBankIdentifier(String paramString)
    throws Exception
  {
    return isValidRoutingNumber(paramString);
  }
  
  public boolean isValid(String paramString)
    throws Exception
  {
    return isValidRoutingNumber(paramString);
  }
  
  public boolean isValidCheckZeros(String paramString)
    throws Exception
  {
    return isValidRoutingNumber(paramString, true);
  }
  
  public static boolean isValidRoutingNumber(String paramString)
  {
    return isValidRoutingNumber(paramString, false);
  }
  
  public static boolean isValidRoutingNumber(String paramString, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.length() != 9)) {
      return false;
    }
    if ((paramBoolean) && (paramString.equals("000000000"))) {
      return false;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    int i = 0;
    for (int k = 0; k < 9; k++)
    {
      char c = localStringBuffer.charAt(k);
      int j = a(c);
      if (j < 0) {
        return false;
      }
      i += j * jdField_if[(k % 3)];
    }
    return i % 10 == 0;
  }
  
  private static int a(char paramChar)
  {
    switch (paramChar)
    {
    case '0': 
      return 0;
    case '1': 
      return 1;
    case '2': 
      return 2;
    case '3': 
      return 3;
    case '4': 
      return 4;
    case '5': 
      return 5;
    case '6': 
      return 6;
    case '7': 
      return 7;
    case '8': 
      return 8;
    case '9': 
      return 9;
    }
    return -1;
  }
  
  public static String getRoutingNumber_EightDigits(String paramString)
  {
    String str = paramString;
    if (str == null) {
      str = "";
    }
    if (str.length() > 8) {}
    for (str = str.substring(0, 8); str.length() < 8; str = "0" + str) {}
    return str;
  }
  
  public static String getRoutingNumber_CheckDigit(String paramString)
  {
    if (paramString == null) {
      return "0";
    }
    if (paramString.length() > 8) {
      return paramString.substring(8, 9);
    }
    while (paramString.length() < 8) {
      paramString = "0" + paramString;
    }
    int i = 0;
    for (int k = 0; k < 8; k++)
    {
      char c = paramString.charAt(k);
      int j = a(c);
      if (j > 0) {
        i += j * jdField_if[(k % 3)];
      }
    }
    k = (short)(10 - i % 10);
    if (k == 10) {
      k = 0;
    }
    return "" + k;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.RoutingNumberUtil
 * JD-Core Version:    0.7.0.1
 */