package com.ffusion.tasks;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Password;
import com.ffusion.efs.adapters.profile.SignonSettings;
import java.util.HashMap;

public class PasswordUtil
{
  public static int validatePassword(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return 3;
    }
    if (paramString.length() < SignonSettings.getMinPasswordLength()) {
      return 6;
    }
    if (paramString.length() > SignonSettings.getMaxPasswordLength()) {
      return 80;
    }
    if (jdMethod_do(paramString) < paramInt2) {
      return 8;
    }
    if (jdMethod_if(paramString) < paramInt3) {
      return 9;
    }
    if ((!paramBoolean) && (a(paramString))) {
      return 7;
    }
    return 0;
  }
  
  public static int validatePassword(String paramString, int paramInt)
  {
    try
    {
      int i = Password.validatePassword(paramString, paramInt, new HashMap());
      return a(i);
    }
    catch (CSILException localCSILException) {}
    return 2;
  }
  
  private static int a(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return 0;
    case 7: 
      return 3;
    case 1: 
      return 6;
    case 2: 
      return 80;
    case 3: 
      return 147;
    case 4: 
      return 9;
    case 5: 
      return 8;
    case 6: 
      return 7;
    }
    return 2;
  }
  
  private static int jdMethod_if(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isDigit(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  private static int jdMethod_do(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isLetter(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  private static boolean a(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (!Character.isLetterOrDigit(paramString.charAt(j))) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.PasswordUtil
 * JD-Core Version:    0.7.0.1
 */