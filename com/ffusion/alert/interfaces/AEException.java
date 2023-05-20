package com.ffusion.alert.interfaces;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AEException
  extends Exception
{
  public static final int AEE_OKAY = 0;
  public static final int AEE_DB_EXCEPTION = 1;
  public static final int AEE_DB_UNKNOWN_CONN_TYPE = 2;
  public static final int AEE_DB_DRIVER_NOT_FOUND = 3;
  public static final int AEE_DB_COULD_NOT_CONNECT = 4;
  public static final int AEE_DB_NO_REPOSITORY = 5;
  public static final int AEE_DB_REPOSITORY_TOO_OLD = 6;
  public static final int AEE_DB_REPOSITORY_TOO_NEW = 7;
  public static final int AEE_DB_CREATING_REPOSITORY = 8;
  public static final int AEE_DB_DESTROYING_REPOSITORY = 9;
  public static final int AEE_DB_MAX_CONN_POOL_SIZE = 10;
  public static final int AEE_DB_CONN_NOT_IN_POOL = 11;
  public static final int AEE_DB_UNCOMMITTED_TRANS = 12;
  public static final int AEE_DB_INVALID_NAME_OR_PASS = 13;
  public static final int AEE_DB_NAME_EXISTS = 14;
  public static final int AEE_DB_BAD_SERVER_INFO = 15;
  public static final int AEE_ENGINE_CHANNEL_NOT_LOADED = 1001;
  public static final int AEE_ENGINE_CHANNEL_ALREADY_LOADED = 1002;
  public static final int AEE_ENGINE_CLASS_LOAD = 1003;
  public static final int AEE_ENGINE_CONSTRUCTOR_FAILED = 1004;
  public static final int AEE_ENGINE_INVALID_CHANNEL_CLASS = 1005;
  public static final int AEE_ENGINE_CHANNEL_NAME_EXISTS = 1006;
  public static final int AEE_ENGINE_CHANNEL_NAME_NOT_FOUND = 1007;
  public static final int AEE_ENGINE_CHANNEL_INIT_FAILED = 1008;
  public static final int AEE_ENGINE_INVALID_DELIVERY_INFO = 1009;
  public static final int AEE_ENGINE_REPOSITORY_EXISTS = 1010;
  public static final int AEE_ENGINE_SCHED_TZ_REQUIRED = 1011;
  public static final int AEE_ENGINE_PARAM_REQUIRED = 1012;
  public static final int AEE_ENGINE_BAD_START_END = 1013;
  public static final int AEE_ENGINE_SCHED_BAD_INTERVAL = 1014;
  public static final int AEE_ENGINE_SCHED_BAD_INTERVAL_TYPE = 1015;
  public static final int AEE_ENGINE_INVALID_ALERT_TYPE = 1016;
  public static final int AEE_ENGINE_INIT_FAILED = 1017;
  public static final int AEE_ENGINE_RESERVED_NAME = 1018;
  public static final int AEE_ENGINE_APPLICATION_IN_USE = 1019;
  public static final int AEE_ENGINE_APPLICATION_NOT_OWNER = 1020;
  public static final int AEE_ENGINE_INVALID_PROPERTY = 1021;
  public static final int AEE_ENGINE_NOT_STOPPED = 1022;
  public static final int AEE_ENGINE_NOT_RUNNING = 1023;
  public static final int AEE_ENGINE_NOT_SUSPENDED = 1024;
  public static final int AEE_ENGINE_STOPPED = 1025;
  public static final int AEE_ENGINE_INVALID_ALERT_ID = 1026;
  public static final int AEE_ENGINE_ALERT_NOT_ACTIVE = 1027;
  public static final int AEE_ENGINE_INTERNAL_ERROR = 1028;
  public static final int AEE_ENGINE_INVALID_CHANNEL_NAME = 1029;
  public static final int AEE_ENGINE_CLUSTER_NODE_NOT_FOUND = 1030;
  public static final int AEE_ENGINE_CLUSTER_NODE_ALREADY_EXISTS = 1031;
  public static final int AEE_MALFORMED_SERVER_URL = 1032;
  public static final int AEE_ENGINE_SCHED_BAD_SEMI_MONTHLY_DAYS = 1033;
  public static final int AEE_ENGINE_COULD_NOT_COMPLETE_OPERATION = 1034;
  public static final int AEE_ENGINE_INVALID_PAGE_SIZE = 1035;
  public static final int AEE_ADMIN_FOUND_ENGINE = 2000;
  public static final int AEE_ADMIN_NO_ENGINE = 2001;
  public static final int AEE_ADMIN_CANT_FIND_ENGINE = 2002;
  public static final int AEE_ADMIN_CLUSTER_NOT_STOPPED = 2003;
  public static final int AEE_CLIENT_ENGINE_NOT_RUNNING = 3000;
  public static final int AEE_CLIENT_CANT_FIND_ENGINE = 3001;
  private Throwable jdField_if;
  private int a;
  
  public AEException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public AEException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public AEException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public AEException(int paramInt, Throwable paramThrowable)
  {
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintStream);
    }
  }
  
  public Throwable getChained()
  {
    return this.jdField_if;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.AEException
 * JD-Core Version:    0.7.0.1
 */