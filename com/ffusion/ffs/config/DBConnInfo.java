package com.ffusion.ffs.config;

import com.ffusion.ffs.util.FFSProperties;
import java.io.Serializable;
import java.sql.Time;

public class DBConnInfo
  implements Serializable
{
  private static final long a = 12345678910L;
  public boolean _isSet = false;
  public boolean _connectionIsReadOnly = false;
  public boolean _connectionAutoCommit = false;
  public boolean _haFlag = false;
  public long _connectionTimeout = 5L;
  public int _port = -1;
  public String _connType = "DRIVER_MANAGER";
  public String _databaseName = "OFXSrvCr";
  public String _password = "SQL";
  public String _dbType = "ASA";
  public String _user = "DBA";
  public String _connectionCatalog = null;
  public String _driver = "com.sybase.jdbc3.jdbc.SybDriver";
  public String _dsn = "OFXCR";
  public String _host = "localhost";
  public String _language = "us_english";
  public String _serverName = "OFXCR";
  public int _level = -1;
  public String _url = null;
  public String _ldapServer = null;
  public int _tranxRetry = 3;
  public int _retryDelay = 1;
  public int _ageTime = 90;
  public int _ageTimeLong = 360;
  public String _rule = null;
  public String _backupNeeded = null;
  public Time _cleanupTime = null;
  public int _connectionIsolationLevel = 0;
  public String _ldapCtxFactory = "com.sun.jndi.ldap.LdapCtxFactory";
  
  public void setHAFlag(boolean paramBoolean)
  {
    this._haFlag = paramBoolean;
  }
  
  public void setIsReadOnly(boolean paramBoolean)
  {
    this._connectionIsReadOnly = paramBoolean;
  }
  
  public void setAutoCommit(boolean paramBoolean)
  {
    this._connectionAutoCommit = paramBoolean;
  }
  
  public void setDBConnType(String paramString)
  {
    this._connType = paramString;
  }
  
  public void setCatalog(String paramString)
  {
    this._connectionCatalog = paramString;
  }
  
  public void setLanguage(String paramString)
  {
    this._language = paramString;
  }
  
  public void setServerName(String paramString)
  {
    this._serverName = paramString;
  }
  
  public void setLDAPServer(String paramString)
  {
    this._ldapServer = paramString;
  }
  
  public void setConnRule(String paramString)
  {
    this._rule = paramString;
  }
  
  public void setBackupNeeded(String paramString)
  {
    this._backupNeeded = paramString;
  }
  
  public void setLDAPCtxFactory(String paramString)
  {
    this._ldapCtxFactory = paramString;
  }
  
  public void setIsolationLevel(int paramInt)
  {
    this._connectionIsolationLevel = paramInt;
  }
  
  public boolean getHAFlag()
  {
    return this._haFlag;
  }
  
  public boolean getIsReadOnly()
  {
    return this._connectionIsReadOnly;
  }
  
  public boolean getAutoCommit()
  {
    return this._connectionAutoCommit;
  }
  
  public String getDBConnType()
  {
    return this._connType;
  }
  
  public String getCatalog()
  {
    return this._connectionCatalog;
  }
  
  public String getLanguage()
  {
    return this._language;
  }
  
  public String getServerName()
  {
    return this._serverName;
  }
  
  public String getLDAPServer()
  {
    return this._ldapServer;
  }
  
  public String getConnRule()
  {
    return this._rule;
  }
  
  public String getBackupNeeded()
  {
    return this._backupNeeded;
  }
  
  public String getLDAPCtxFactory()
  {
    return this._ldapCtxFactory;
  }
  
  public int getIsolationLevel()
  {
    return this._connectionIsolationLevel;
  }
  
  public void setConnectionTimeout(long paramLong)
  {
    this._connectionTimeout = paramLong;
  }
  
  public long getConnectionTimeout()
  {
    return this._connectionTimeout;
  }
  
  public void setPort(int paramInt)
  {
    this._port = paramInt;
  }
  
  public int getPort()
  {
    return this._port;
  }
  
  public void setDatabaseName(String paramString)
  {
    this._databaseName = paramString;
  }
  
  public String getDatabaseName()
  {
    return this._databaseName;
  }
  
  public void setPassword(String paramString)
  {
    this._password = paramString;
  }
  
  public String getPassword()
  {
    return this._password;
  }
  
  public void setDbType(String paramString)
  {
    this._dbType = paramString;
  }
  
  public String getDbType()
  {
    return this._dbType;
  }
  
  public void setUser(String paramString)
  {
    this._user = paramString;
  }
  
  public String getUser()
  {
    return this._user;
  }
  
  public void setDriver(String paramString)
  {
    this._driver = paramString;
  }
  
  public String getDriver()
  {
    return this._driver;
  }
  
  public void setDsn(String paramString)
  {
    this._dsn = paramString;
  }
  
  public String getDsn()
  {
    return this._dsn;
  }
  
  public void setHost(String paramString)
  {
    this._host = paramString;
  }
  
  public String getHost()
  {
    return this._host;
  }
  
  public void setLevel(int paramInt)
  {
    this._level = paramInt;
  }
  
  public int getLevel()
  {
    return this._level;
  }
  
  public void setUrl(String paramString)
  {
    this._url = paramString;
  }
  
  public String getUrl()
  {
    return this._url;
  }
  
  public void setTranxRetry(int paramInt)
  {
    this._tranxRetry = paramInt;
  }
  
  public int getTranxRetry()
  {
    return this._tranxRetry;
  }
  
  public void setRetryDelay(int paramInt)
  {
    this._retryDelay = paramInt;
  }
  
  public int getRetryDelay()
  {
    return this._retryDelay;
  }
  
  public void setAgeTime(int paramInt)
  {
    this._ageTime = paramInt;
  }
  
  public int getAgeTime()
  {
    return this._ageTime;
  }
  
  public void setAgeTimeLong(int paramInt)
  {
    this._ageTimeLong = paramInt;
  }
  
  public int getAgeTimeLong()
  {
    return this._ageTimeLong;
  }
  
  public void setCleanupTime(Time paramTime)
  {
    this._cleanupTime = paramTime;
  }
  
  public Time getCleanupTime()
  {
    return this._cleanupTime;
  }
  
  public void setConfigured(boolean paramBoolean)
  {
    this._isSet = paramBoolean;
  }
  
  public boolean isConfigured()
  {
    return this._isSet;
  }
  
  public FFSProperties convertToProperties()
  {
    FFSProperties localFFSProperties = new FFSProperties();
    localFFSProperties.setProperty("connectionIsReadOnly", String.valueOf(this._connectionIsReadOnly));
    localFFSProperties.setProperty("connectionAutoCommit", String.valueOf(this._connectionAutoCommit));
    localFFSProperties.setProperty("connectionTimeout", String.valueOf(this._connectionTimeout));
    localFFSProperties.setProperty("connectionIsolationLevel", String.valueOf(this._connectionIsolationLevel));
    localFFSProperties.setProperty("tranxRetry", String.valueOf(this._tranxRetry));
    localFFSProperties.setProperty("haFlag", String.valueOf(this._haFlag));
    localFFSProperties.setProperty("port", String.valueOf(this._port));
    localFFSProperties.setProperty("poolConnectionType", this._connType);
    localFFSProperties.setProperty("databaseSourceName", this._databaseName);
    localFFSProperties.setProperty("dbType", this._dbType);
    localFFSProperties.setProperty("user", this._user);
    localFFSProperties.setProperty("host", this._host);
    if ((this._password != null) && (this._password.length() > 0)) {
      localFFSProperties.setProperty("password", this._password);
    }
    if ((this._url != null) && (this._url.length() > 0)) {
      localFFSProperties.setProperty("url", String.valueOf(this._url));
    }
    if ((this._connectionCatalog != null) && (this._connectionCatalog.length() > 0)) {
      localFFSProperties.setProperty("connectionCatalog", this._connectionCatalog);
    }
    if ((this._driver != null) && (this._driver.length() > 0)) {
      localFFSProperties.setProperty("driver", this._driver);
    }
    if ((this._dsn != null) && (this._dsn.length() > 0)) {
      localFFSProperties.setProperty("dsn", this._dsn);
    }
    if ((this._language != null) && (this._language.length() > 0)) {
      localFFSProperties.setProperty("language", this._language);
    }
    if ((this._serverName != null) && (this._serverName.length() > 0)) {
      localFFSProperties.setProperty("serverName", this._serverName);
    }
    if ((this._ldapServer != null) && (this._ldapServer.length() > 0)) {
      localFFSProperties.setProperty("ldapSrv", this._ldapServer);
    }
    if ((this._ldapCtxFactory != null) && (this._ldapCtxFactory.length() > 0)) {
      localFFSProperties.setProperty("ldapContextFactory", this._ldapCtxFactory);
    }
    return localFFSProperties;
  }
  
  public FFSProperties convertToProperties(FFSProperties paramFFSProperties)
  {
    paramFFSProperties.setProperty("connectionIsReadOnly", String.valueOf(this._connectionIsReadOnly));
    paramFFSProperties.setProperty("connectionAutoCommit", String.valueOf(this._connectionAutoCommit));
    paramFFSProperties.setProperty("connectionTimeout", String.valueOf(this._connectionTimeout));
    paramFFSProperties.setProperty("connectionIsolationLevel", String.valueOf(this._connectionIsolationLevel));
    paramFFSProperties.setProperty("tranxRetry", String.valueOf(this._tranxRetry));
    paramFFSProperties.setProperty("haFlag", String.valueOf(this._haFlag));
    paramFFSProperties.setProperty("port", String.valueOf(this._port));
    paramFFSProperties.setProperty("poolConnectionType", this._connType);
    paramFFSProperties.setProperty("databaseSourceName", this._databaseName);
    paramFFSProperties.setProperty("dbType", this._dbType);
    paramFFSProperties.setProperty("user", this._user);
    paramFFSProperties.setProperty("host", this._host);
    if ((this._password != null) && (this._password.length() > 0)) {
      paramFFSProperties.setProperty("password", this._password);
    }
    if ((this._url != null) && (this._url.length() > 0))
    {
      paramFFSProperties.setProperty("url", String.valueOf(this._url));
      paramFFSProperties.setProperty("databaseUrl", String.valueOf(this._url));
    }
    if ((this._connectionCatalog != null) && (this._connectionCatalog.length() > 0)) {
      paramFFSProperties.setProperty("connectionCatalog", this._connectionCatalog);
    }
    if ((this._driver != null) && (this._driver.length() > 0)) {
      paramFFSProperties.setProperty("driver", this._driver);
    }
    if ((this._dsn != null) && (this._dsn.length() > 0)) {
      paramFFSProperties.setProperty("dsn", this._dsn);
    }
    if ((this._language != null) && (this._language.length() > 0)) {
      paramFFSProperties.setProperty("language", this._language);
    }
    if ((this._serverName != null) && (this._serverName.length() > 0)) {
      paramFFSProperties.setProperty("serverName", this._serverName);
    }
    if ((this._ldapServer != null) && (this._ldapServer.length() > 0)) {
      paramFFSProperties.setProperty("ldapSrv", this._ldapServer);
    }
    if ((this._ldapCtxFactory != null) && (this._ldapCtxFactory.length() > 0)) {
      paramFFSProperties.setProperty("ldapContextFactory", this._ldapCtxFactory);
    }
    return paramFFSProperties;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.config.DBConnInfo
 * JD-Core Version:    0.7.0.1
 */