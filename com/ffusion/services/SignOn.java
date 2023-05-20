package com.ffusion.services;

import java.io.Serializable;

public abstract interface SignOn
  extends Serializable
{
  public static final int ERROR_NONE = 0;
  public static final int ERROR_UNKNOWN = 1;
  public static final int ERROR_NOT_SUPPORTED = 2;
  public static final int ERROR_INVALID_MESSAGE = 3;
  public static final int ERROR_NO_CONNECTION = 4;
  public static final int ERROR_ACCOUNT_IN_USE = 5;
  public static final int ERROR_ACCOUNT_LOCKED = 6;
  public static final int ERROR_INVALID_CUSTOMER_ID = 100;
  public static final int ERROR_INVALID_PASSWORD = 101;
  public static final int ERROR_MUST_CHANGE_PASSWORD = 102;
  
  public abstract void setInitURL(String paramString);
  
  public abstract int signOn(String paramString1, String paramString2);
  
  public abstract int changePIN(String paramString1, String paramString2);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.SignOn
 * JD-Core Version:    0.7.0.1
 */