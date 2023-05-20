package com.ffusion.alert.interfaces;

public abstract interface IAEPropsConstants
{
  public static final String PC_LOG_LEVEL = "log.level";
  public static final String PC_LOG_FILE = "log.file";
  public static final String PC_LOG_CONSOLE = "log.console";
  public static final String PC_SCHED_INTERVAL = "scheduler.interval";
  public static final String PC_SCHED_BATCH_SIZE = "scheduler.batch.size";
  public static final String PC_CL_FAILURE_INTERVAL = "cluster.failureInterval";
  public static final String PC_AUDIT_HISTORY = "audit.history";
  public static final String PC_AUDIT_CLEANUP_TIME = "audit.cleanup";
  public static final String PC_AUDIT_POOL_SIZE = "audit.pool.size";
  public static final String PC_DB_CONN_MAX = "db.connection.max";
  public static final String LOG_NONE = "none";
  public static final String LOG_NORMAL = "normal";
  public static final String LOG_VERBOSE = "verbose";
  public static final String LOG_ALL = "all";
  public static final String DEFAULT_LOG_LEVEL = "normal";
  public static final String DEFAULT_LOG_FILE = "AlertEngine.log";
  public static final String DEFAULT_LOG_CONSOLE = "true";
  public static final String DEFAULT_FAILURE_INTERVAL = "90s";
  public static final String DEFAULT_SCHED_INTERVAL = "1m";
  public static final String DEFAULT_SCHED_BATCH_SIZE = "100";
  public static final String DEFAULT_AUDIT_HISTORY = "7d";
  public static final String DEFAULT_DB_CONN_MAX = "50";
  public static final String DEFAULT_AUDIT_CLEAN_UP_TIME = "03:00";
  public static final String DEFAULT_AUDIT_POOL_SIZE = "10000";
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEPropsConstants
 * JD-Core Version:    0.7.0.1
 */