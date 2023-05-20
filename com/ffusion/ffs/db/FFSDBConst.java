package com.ffusion.ffs.db;

public abstract interface FFSDBConst
{
  public static final String OFXSrvCf_PROPS_FILE = "OFXSrvCf.props";
  public static final int DEFAULT_POOL_SIZE = 10;
  public static final long DEFAULT_CONN_TIMEOUT = 5L;
  public static final String PREFIX = "";
  public static final String CONN_TIMEOUT = "connectionTimeout";
  public static final String MAX_POOL_SIZE = "maxPoolSize";
  public static final String MAXPREPSTATEMENTCACHESIZE = "MaxPrepStatementCacheSize";
  public static final String OPT_POOL_SIZE = "optimalPoolSize";
  public static final String POOL_JNDI_PROPERTIES = "poolJNDIProperties";
  public static final String DATABASE_SOURCE_NAME = "databaseSourceName";
  public static final String POOL_CONNECTION_TYPE = "poolConnectionType";
  public static final String POOL_GUARANTEE_CONNECTION_PROPERTIES = "poolGuaranteeConnectionProperties";
  public static final String CONNECTION_IS_READ_ONLY = "connectionIsReadOnly";
  public static final String CONNECTION_CATALOG = "connectionCatalog";
  public static final String CONNECTION_ISOLATION_LEVEL = "connectionIsolationLevel";
  public static final String CONNECTION_AUTO_COMMIT = "connectionAutoCommit";
  public static final String CONNECTION_PROPERTIES = "connectionProperties";
  public static final String DATABASE_TYPE = "dbType";
  public static final String CLUSTER = "cluster";
  public static final String DRIVER = "driver";
  public static final String DSN = "dsn";
  public static final String PASSWORD = "password";
  public static final String USER = "user";
  public static final String HOST_NAME = "host";
  public static final String PORT_NUMBER = "port";
  public static final String DATABASE_URL = "databaseUrl";
  public static final String LDAP_URL = "ldapSvr";
  public static final String LDAP_CTX_FACTORY = "ldapContextFactory";
  public static final String TX_RETRY = "txRetry";
  public static final String DATABASE_EXISTS = "databaseExists";
  public static final String HA_FLAG = "haFlag";
  public static final String DISCONNECT = "disconnect";
  public static final String LANGUAGE = "language";
  public static final String URL_PREFIX = "urlPrefix";
  public static final int DEFAULT_PORT_NUMBER = 2639;
  public static final int UNKNOWN = 0;
  public static final int ASE = 1;
  public static final int ASA = 2;
  public static final int DB2 = 3;
  public static final int ORL = 4;
  public static final int MSF = 5;
  public static final String DB_ORACLE = "ORACLE";
  public static final String DB_DB2 = "DB2";
  public static final String DB_ASE = "ASE";
  public static final String DB_ASA = "ASA";
  public static final String DB_MSSQL = "MSSQL";
  public static final int CONNECTION_OPEN = 100;
  public static final int CONNECTION_CLOSED = 101;
  public static final int TRANSACTION_NONE = 0;
  public static final int TRANSACTION_READ_UNCOMMITTED = 1;
  public static final int TRANSACTION_READ_COMMITTED = 2;
  public static final int TRANSACTION_REPEATABLE_READ = 3;
  public static final int TRANSACTION_SERIALIZABLE = 4;
  public static final int ERROR_FATAL = 0;
  public static final int ERROR_WARNING = 1;
  public static final int ERROR_SQL_CONNECTION_RESET = 2;
  public static final int ERROR_SQL_CONNECTION_ROBBED = 3;
  public static final int ERROR_SQL_TABLE_NOT_EXIST = 4;
  public static final int ERROR_SQL_TABLE_LOCKED = 5;
  public static final String SUN_LDAP_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSDBConst
 * JD-Core Version:    0.7.0.1
 */