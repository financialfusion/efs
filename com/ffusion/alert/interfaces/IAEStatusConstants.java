package com.ffusion.alert.interfaces;

public abstract interface IAEStatusConstants
{
  public static final int DI_STATUS_FAILED = -1;
  public static final int DI_STATUS_SUCCEEDED = 0;
  public static final int DI_STATUS_FAILED_CHANNEL_NOT_LOADED = 1000;
  public static final int DI_STATUS_FAILED_BEYOND_LATE_TOLERANCE = 1001;
  public static final int DI_STATUS_FAILED_PROCESSING_EXCEPTION = 1002;
  public static final int DI_STATUS_FAILED_SUSPENDED = 1003;
  public static final int DI_STATUS_INTERNAL_RANGE_SHIFT = 2000;
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEStatusConstants
 * JD-Core Version:    0.7.0.1
 */