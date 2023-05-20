package com.ffusion.services;

public abstract interface SignOn2
  extends SignOn
{
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7;
  public static final int ERROR_INVALID_INIT_FILE = 8;
  
  public abstract int initialize(String paramString);
  
  public abstract int signOn(String paramString1, String paramString2);
  
  public abstract int changePIN(String paramString1, String paramString2);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.SignOn2
 * JD-Core Version:    0.7.0.1
 */