package com.ffusion.tasks.util;

public class ValidateString
{
  public static boolean validateName(String paramString1, String paramString2)
  {
    String str = " ._" + paramString2;
    int i = 0;
    for (int j = 0; j < paramString1.length(); j++)
    {
      char c = paramString1.charAt(j);
      if (!Character.isLetterOrDigit(c))
      {
        i = 0;
        for (int k = 0; k < str.length(); k++) {
          if (c == str.charAt(k))
          {
            i = 1;
            break;
          }
        }
        if (i == 0) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean validateName(String paramString)
  {
    return validateName(paramString, "");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.ValidateString
 * JD-Core Version:    0.7.0.1
 */