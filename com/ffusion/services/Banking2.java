package com.ffusion.services;

public abstract interface Banking2
  extends Banking, SignOn2
{
  public static final int ERROR_INVALID_DUE_DATE = 1045;
  public static final int ERROR_NO_SCHEDULE_DEFINED = 1075;
  public static final int ERROR_DUE_DATE_EXPIRED = 1076;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking2
 * JD-Core Version:    0.7.0.1
 */