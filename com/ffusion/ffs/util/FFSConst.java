package com.ffusion.ffs.util;

import java.io.Serializable;

public abstract interface FFSConst
  extends Serializable
{
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final int SCH_FREQ_ANNUALLY = 0;
  public static final int SCH_FREQ_BIMONTHLY = 1;
  public static final int SCH_FREQ_BIWEEKLY = 2;
  public static final int SCH_FREQ_FOURWEEKS = 3;
  public static final int SCH_FREQ_MONTHLY = 4;
  public static final int SCH_FREQ_QUARTERLY = 5;
  public static final int SCH_FREQ_SEMIANNUALLY = 6;
  public static final int SCH_FREQ_TRIANNUALLY = 7;
  public static final int SCH_FREQ_SEMIMONTHLY = 8;
  public static final int SCH_FREQ_WEEKLY = 9;
  public static final int SCH_FREQ_ONCE = 10;
  public static final int SCH_FREQ_DAILY = 11;
  public static final int SCH_FREQ_IMMEDIATELY = 12;
  public static final String SCH_FREQ_ANNUALLY_STR = "ANNUALLY";
  public static final String SCH_FREQ_ANUALLY_STR = "ANUALLY";
  public static final String SCH_FREQ_BIMONTHLY_STR = "BIMONTHLY";
  public static final String SCH_FREQ_BIWEEKLY_STR = "BIWEEKLY";
  public static final String SCH_FREQ_FOURWEEKS_STR = "FOURWEEKS";
  public static final String SCH_FREQ_MONTHLY_STR = "MONTHLY";
  public static final String SCH_FREQ_QUARTERLY_STR = "QUARTERLY";
  public static final String SCH_FREQ_SEMIANNUALLY_STR = "SEMIANNUALLY";
  public static final String SCH_FREQ_TRIANNUALLY_STR = "TRIANNUALLY";
  public static final String SCH_FREQ_SEMIMONTHLY_STR = "SEMIMONTHLY";
  public static final String SCH_FREQ_TWICEMONTHLY_STR = "TWICEMONTHLY";
  public static final String SCH_FREQ_WEEKLY_STR = "WEEKLY";
  public static final String SCH_FREQ_ONCE_STR = "ONCE";
  public static final String SCH_FREQ_DAILY_STR = "DAILY";
  public static final String SCH_FREQ_IMMEDIATELY_STR = "IMMEDIATELY";
  public static final String SCH_FREQ_NONE_STR = "NONE";
  public static final String FFS_CASMF = "FFS_CASMF";
  public static final String FFS_MQSERIES = "FFS_MQSERIES";
  public static final String FFS_FIX = "FFS_FIX";
  public static final String FFS_OFX = "FFS_OFX";
  public static final String FFS_SWIFT = "FFS_SWIFT";
  public static final int PRINT_NONE = -1;
  public static final int PRINT_ERR = 0;
  public static final int PRINT_WRN = 1;
  public static final int PRINT_CFG = 2;
  public static final int PRINT_INF = 3;
  public static final int PRINT_MED = 4;
  public static final int PRINT_MAX = 5;
  public static final int PRINT_DEV = 6;
  public static final int PRINT_ALL = 1000000;
  public static final String OFXADAPTER_LOG_FILE = "OFXLogFile.log";
  public static final String FIXADAPTER_LOG_FILE = "FIXLogFile.log";
  public static final String SWIFTADAPTER_LOG_FILE = "SWIFTLogFile.log";
  public static final String MQADAPTER_LOG_FILE = "MQLogFile.log";
  public static final String SRVRID = "com.ffusion.ffs.serverName";
  public static final String OFX_ADAPTER = "OFXAdapter";
  public static final String OFX_DBHANDLE = "OFXDBHandle";
  public static final String OFX_IMBINSTANCE = "OFXIMBInstance";
  public static final String BPW_Server = "BPWServer";
  public static final int UNKNOWN = -1;
  public static final int NOT_INITIALIZED = 0;
  public static final int SHUTDOWN = 1;
  public static final int INITIALIZED = 2;
  public static final int SUSPENDED = 3;
  public static final int STARTED = 4;
  public static final int NOT_REGISTERED = 0;
  public static final int REGISTERED = 1;
  public static final int IOEXCEPTION = 2;
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSConst
 * JD-Core Version:    0.7.0.1
 */