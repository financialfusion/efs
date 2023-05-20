package com.ffusion.tasks.systemadmin;

public abstract interface SystemAdminTask
{
  public static final int ERROR_INVALID_DATA_RETENTION_ID = 38100;
  public static final int ERROR_INVALID_DATA_RETENTION_TYPE = 38101;
  public static final int ERROR_MISSING_HISTORIC_DATA_RETENTION_SETTINGS = 38102;
  public static final int ERROR_INVALID_DATA_RETENTION_SETTING = 38103;
  public static final String SN_DRSETTINGS = "DRSettings";
  public static final String SN_DRSETTINGKEYS = "DRSettingKeys";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.systemadmin.SystemAdminTask
 * JD-Core Version:    0.7.0.1
 */