package com.ffusion.services;

import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface IPassword
{
  public static final int PASSWORD_OK = 0;
  public static final int PASSWORD_TOO_SHORT = 1;
  public static final int PASSWORD_TOO_LONG = 2;
  public static final int PASSWORD_TOO_FEW_UPPERCASE_LETTERS = 3;
  public static final int PASSWORD_TOO_FEW_DIGITS = 4;
  public static final int PASSWORD_TOO_FEW_LETTERS = 5;
  public static final int PASSWORD_HAS_SPECIAL_CHARACTERS = 6;
  public static final int PASSWORD_MISSING = 7;
  public static final int PASSWORD_CUSTOM_CODE_BASE = 1000;
  
  public abstract void initialize(HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int validatePassword(String paramString, int paramInt, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IPassword
 * JD-Core Version:    0.7.0.1
 */