package com.ffusion.services;

public abstract interface Applications2
  extends Applications
{
  public static final int SERVICE_ERROR_INIT_FILE_NOT_FOUND = 7011;
  public static final int SERVICE_ERROR_INVALID_INIT_FILE = 7012;
  
  public abstract int initialize(String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Applications2
 * JD-Core Version:    0.7.0.1
 */