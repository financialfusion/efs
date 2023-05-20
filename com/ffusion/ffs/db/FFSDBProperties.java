package com.ffusion.ffs.db;

import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.io.Serializable;

public class FFSDBProperties
  implements Serializable, FFSDBConst, FFSConst
{
  public ConnPoolInfo _connInfo = null;
  public FFSProperties _pureProps = null;
  public FFSProperties _connProps = null;
  
  public FFSDBProperties() {}
  
  public FFSDBProperties(FFSProperties paramFFSProperties)
  {
    this._connInfo = new ConnPoolInfo();
    if (paramFFSProperties == null)
    {
      FFSDebug.log("Invalid Database properties object", 0);
    }
    else
    {
      FFSDebug.log("Loading DB properties...........", 6);
      this._connProps = paramFFSProperties;
      this._pureProps = paramFFSProperties.getPureKeyProperties();
      FFSDebug.log("Loading this._pureProps: ", this._pureProps.toString(), 6);
      jdMethod_if();
    }
  }
  
  public FFSDBProperties(ConnPoolInfo paramConnPoolInfo)
  {
    FFSDebug.log("FFSDBProperties:connInfo: " + paramConnPoolInfo);
    this._connInfo = paramConnPoolInfo;
    this._connProps = this._connInfo.convertToProperties();
    FFSDebug.log("FFSDBProperties: after connInfo.convertToProperties()");
    this._pureProps = this._connProps.getPureKeyProperties();
    FFSDebug.log("FFSDBProperties: after connInfo.getPureKeyProperties()");
    jdMethod_if();
    FFSDebug.log("FFSDBProperties: after connInfo.loadProperties()");
  }
  
  private void jdMethod_if()
  {
    this._connInfo._disconnect = this._pureProps.getBoolean("disconnect");
    this._connInfo._dbConnInfo._connectionIsReadOnly = this._pureProps.getBoolean("connectionIsReadOnly");
    this._connInfo._dbConnInfo._connectionAutoCommit = this._pureProps.getBoolean("connectionAutoCommit");
    this._connInfo._dbConnInfo._haFlag = this._pureProps.getBoolean("haFlag");
    String str1 = this._pureProps.getProperty("haFlag");
    if ((str1 != null) && (str1.compareTo("1") == 0)) {
      this._connInfo._dbConnInfo._haFlag = true;
    }
    int i = this._pureProps.getInt("connectionTimeout");
    this._connInfo._dbConnInfo._connectionTimeout = (i > 0 ? i : 5L);
    i = this._pureProps.getInt("maxPoolSize");
    this._connInfo._maxPoolSize = (i > 0 ? i : 10);
    i = this._pureProps.getInt("optimalPoolSize");
    this._connInfo._optimalPoolSize = (i > 0 ? i : 10);
    i = this._pureProps.getInt("port");
    this._connInfo._dbConnInfo._port = (i > 0 ? i : 2639);
    int j = 3;
    int k = this._pureProps.getInt("txRetry");
    this._connInfo._dbConnInfo._tranxRetry = (k > 0 ? k : j);
    this._connInfo._dbConnInfo._connType = this._pureProps.getProperty("poolConnectionType");
    this._connInfo._dbConnInfo._databaseName = this._pureProps.getProperty("databaseSourceName");
    this._connInfo._dbConnInfo._password = this._pureProps.getProperty("password");
    this._connInfo._dbConnInfo._dbType = this._pureProps.getProperty("dbType");
    this._connInfo._dbConnInfo._user = this._pureProps.getProperty("user");
    this._connInfo._dbConnInfo._connectionCatalog = this._pureProps.getProperty("connectionCatalog");
    this._connInfo._dbConnInfo._driver = this._pureProps.getProperty("driver");
    this._connInfo._dbConnInfo._dsn = this._pureProps.getProperty("dsn");
    this._connInfo._dbConnInfo._host = this._pureProps.getProperty("host");
    this._connInfo._dbConnInfo._language = this._pureProps.getProperty("language");
    this._connInfo._dbConnInfo._url = this._pureProps.getProperty("databaseUrl");
    this._connInfo._dbConnInfo._ldapServer = this._pureProps.getProperty("ldapSvr");
    String str2 = this._pureProps.getProperty("ldapContextFactory");
    this._connInfo._dbConnInfo._ldapCtxFactory = ((str2 != null) && (str2.trim().length() > 0) ? str2 : "com.sun.jndi.ldap.LdapCtxFactory");
    FFSDebug.log("FFSDBProperties: Connection props loaded.", 6);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSDBProperties
 * JD-Core Version:    0.7.0.1
 */