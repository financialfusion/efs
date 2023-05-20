package com.ffusion.util.db;

public abstract interface ConnectionDefines
{
  public static final String DB_DEFAULT_CONNECTIONS = "DefaultConnections";
  public static final String DB_MAX_CONNECTIONS = "MaxConnections";
  public static final String DB_USER = "User";
  public static final String DB_PASSWORD = "Password";
  public static final String DB_SERVER = "Server";
  public static final String DB_CONNECTIONTIMEOUT = "ConnectionTimeout";
  public static final String DB_DIAGNOSTICSINTERVAL = "DiagnosticsInterval";
  public static final String DB_VALIDATIONINTERVAL = "ValidationInterval";
  public static final String DB_VALIDATIONTIMEOUT = "ValidationTimeout";
  public static final String DB_STALETIMEOUT = "StaleTimeout";
  public static final String DB_RESIZEPOOL = "ResizePool";
  public static final String DB_POOL = "Pool";
  public static final String DB_PORT = "Port";
  public static final String DB_NAME = "DBName";
  public static final String DB_CONNECT_TYPE = "ConnectionType";
  public static final String DB_JDBC_URL = "JDBCUrl";
  public static final String DB_DRIVER_CLASS = "DriverClass";
  public static final String DB_LICENSE = "License";
  public static final String DB_ORACLE_THIN = "ORACLE:THIN";
  public static final String DB_ORACLE_OCI8 = "ORACLE:OCI8";
  public static final String DB_MS_SQLSERVER = "MSSQL:THIN";
  public static final String DB_SYBASE_ASA = "ASA";
  public static final String DB_SYBASE_ASE = "ASE";
  public static final String DB_DB2_APP = "DB2:APP";
  public static final String DB_DB2_NET = "DB2:NET";
  public static final String DB_DB2_390 = "DB2:AS390";
  public static final String DB_DB2_UN2 = "DB2:UN2";
  public static final String POOL_WEBSPHERE = "WebSphere";
  public static final String POOL_WEBLOGIC = "Weblogic";
  public static final String POOL_EASERVER = "EAServer";
  public static final String POOL_FFI = "FFI";
  public static final String POOL_TYPE = "PoolType";
  public static final String CONTEXT_FACTORY = "CTXFactory";
  public static final String PROVIDER_URL = "ProviderURL";
  public static final String POOL_NAME = "PoolName";
  public static final String CLASS_NAME = "ClassName";
  public static final String WEBSPHERE_CTX_FACTORY = "com.ibm.websphere.naming.WsnInitialContextFactory";
  public static final String WEBLOGIC_CTX_FACTORY = "weblogic.jndi.WLInitialContextFactory";
  public static final String EASERVER_CTX_FACTORY = "com.sybase.ejb.InitialContextFactory";
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.ConnectionDefines
 * JD-Core Version:    0.7.0.1
 */