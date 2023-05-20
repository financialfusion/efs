package com.ffusion.services;

public abstract interface PortalData2
  extends PortalData
{
  public static final int ERROR_INIT_FILE_NOT_FOUND = 9000;
  public static final int ERROR_INVALID_INIT_FILE = 9001;
  
  public abstract int initialize(String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PortalData2
 * JD-Core Version:    0.7.0.1
 */