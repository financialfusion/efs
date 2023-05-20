package com.ffusion.services.profile;

import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.services.IPassword;
import java.util.HashMap;

public class PasswordService
  implements IPassword
{
  public void initialize(HashMap paramHashMap)
    throws ProfileException
  {
    SignonSettings.initialize(paramHashMap);
  }
  
  public int validatePassword(String paramString, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    if (paramInt == 2) {
      return jdMethod_if(paramString, paramHashMap);
    }
    return a(paramString, paramHashMap);
  }
  
  private int a(String paramString, HashMap paramHashMap)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return 7;
    }
    if (paramString.length() < SignonSettings.getMinPasswordLength()) {
      return 1;
    }
    if (paramString.length() > SignonSettings.getMaxPasswordLength()) {
      return 2;
    }
    if (jdMethod_for(paramString) < SignonSettings.getMinPasswordUpperCaseLetters()) {
      return 3;
    }
    if (jdMethod_if(paramString) < SignonSettings.getMinPasswordDigits()) {
      return 4;
    }
    if (jdMethod_do(paramString) < SignonSettings.getMinPasswordLetters()) {
      return 5;
    }
    if ((a(paramString)) && (!SignonSettings.getPasswordAllowSpecialChars())) {
      return 6;
    }
    return 0;
  }
  
  private int jdMethod_if(String paramString, HashMap paramHashMap)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return 7;
    }
    if (paramString.length() < SignonSettings.getBCMinPasswordLength()) {
      return 1;
    }
    if (paramString.length() > SignonSettings.getBCMaxPasswordLength()) {
      return 2;
    }
    if (jdMethod_for(paramString) < SignonSettings.getBCMinPasswordUpperCaseLetters()) {
      return 3;
    }
    if (jdMethod_if(paramString) < SignonSettings.getBCMinPasswordDigits()) {
      return 4;
    }
    if (jdMethod_do(paramString) < SignonSettings.getBCMinPasswordLetters()) {
      return 5;
    }
    if ((a(paramString)) && (!SignonSettings.getBCPasswordAllowSpecialChars())) {
      return 6;
    }
    return 0;
  }
  
  private int jdMethod_for(String paramString)
  {
    int i = 0;
    for (int j = 0; j < paramString.length(); j++) {
      if (Character.isUpperCase(paramString.charAt(j))) {
        i++;
      }
    }
    return i;
  }
  
  private int jdMethod_if(String paramString)
  {
    int i = 0;
    for (int j = 0; j < paramString.length(); j++) {
      if (Character.isDigit(paramString.charAt(j))) {
        i++;
      }
    }
    return i;
  }
  
  private int jdMethod_do(String paramString)
  {
    int i = 0;
    for (int j = 0; j < paramString.length(); j++) {
      if (Character.isLetter(paramString.charAt(j))) {
        i++;
      }
    }
    return i;
  }
  
  private boolean a(String paramString)
  {
    for (int i = 0; i < paramString.length(); i++) {
      if (!Character.isLetterOrDigit(paramString.charAt(i))) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.PasswordService
 * JD-Core Version:    0.7.0.1
 */