package com.ffusion.util.db;

import com.ffusion.util.HfnUtil;
import com.ffusion.util.PropertiesUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

public class ConnectionPool
  implements ConnectionDefines
{
  private static final int jdField_do = 30;
  private static String jdField_if = "ACCESS_COUNT";
  private static String a = "POOL_IDENTIFIER";
  protected static HashMap dbDataSources = new HashMap(3);
  protected static HashMap dbPoolProps = new HashMap(3);
  public static int validationLevel = 0;
  public static final int LEVEL_RETURNED_TWICE = 1;
  public static final int LEVEL_VALIDATE_ON_RETURN = 2;
  public static final int LEVEL_NEVER_RETURNED = 4;
  
  public static String init(String paramString)
    throws PoolException
  {
    Properties localProperties = PropertiesUtil.getPropertiesFromResource(new ConnectionPool(), paramString);
    if (localProperties == null) {
      throw new PoolException(1000, "Unable to load properties file:" + paramString);
    }
    return init(localProperties);
  }
  
  public static String init(Properties paramProperties)
    throws PoolException
  {
    Object localObject = paramProperties.get("PoolType");
    if (localObject == null) {
      throw new PoolException(1003, "Could not determine the type of pool to initialize.  Could not find the field \"POOL_TYPE\" in the properties.");
    }
    String str1 = (String)paramProperties.get("PoolName");
    if ((str1 == null) || (str1.length() == 0))
    {
      String str2 = (String)paramProperties.get("JDBCUrl");
      if ((str2 != null) && (str2.length() > 0)) {
        str1 = str2 + ":" + paramProperties.get("User");
      } else {
        str1 = DBUtil.getDBUrl((String)paramProperties.get("ConnectionType"), (String)paramProperties.get("DBName"), (String)paramProperties.get("Server"), (String)paramProperties.get("Port")) + ":" + paramProperties.get("User");
      }
    }
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = HfnUtil.generateAlpha(15);
    }
    jdField_do(str1, paramProperties);
    return str1;
  }
  
  public static Connection getConnection(String paramString, boolean paramBoolean, int paramInt)
    throws PoolException
  {
    Connection localConnection = null;
    Object localObject1 = dbDataSources.get(paramString);
    Properties localProperties = (Properties)dbPoolProps.get(paramString);
    if (localProperties == null) {
      throw new PoolException(1000, "Could not find init properties for " + paramString);
    }
    if (localObject1 == null) {
      synchronized (localProperties)
      {
        jdField_if(paramString, localProperties);
      }
    }
    if ((localObject1 instanceof DataSource))
    {
      ??? = (DataSource)localObject1;
      int i = 30;
      try
      {
        i = Integer.parseInt(localProperties.getProperty("ConnectionTimeout"));
      }
      catch (Exception localException1) {}
      long l = System.currentTimeMillis() + i * 1000;
      while (System.currentTimeMillis() < l) {
        try
        {
          localConnection = ((DataSource)???).getConnection();
          if (localConnection != null)
          {
            localConnection.setTransactionIsolation(paramInt);
            localConnection.setAutoCommit(paramBoolean);
            break;
          }
        }
        catch (Exception localException2)
        {
          DebugLog.log(Level.WARNING, "ConnectionPool.getConnection: Unable to retrieve a valid connection:" + localProperties.getProperty(a) + ": " + localException2.getMessage());
          if (localConnection != null) {
            a((DataSource)???, localConnection);
          }
          localConnection = null;
        }
      }
    }
    if (localConnection == null) {
      throw new PoolException(13, "ConnectionPool.getConnection: Unable to retrieve a valid connection:" + localProperties.getProperty(a));
    }
    return localConnection;
  }
  
  public static boolean returnConnection(String paramString, Connection paramConnection)
    throws PoolException
  {
    Object localObject = dbDataSources.get(paramString);
    boolean bool = true;
    if (paramConnection != null) {
      try
      {
        if ((localObject instanceof IConnectionPool))
        {
          ((IConnectionPool)localObject).returnConnection(paramConnection);
        }
        else
        {
          if (!paramConnection.getAutoCommit()) {
            paramConnection.commit();
          }
          paramConnection.close();
        }
      }
      catch (SQLException localSQLException)
      {
        bool = false;
        throw new PoolException(1, localSQLException);
      }
    }
    return bool;
  }
  
  public static boolean releasePool(String paramString)
    throws PoolException
  {
    boolean bool = true;
    Integer localInteger = null;
    int i = -1;
    Properties localProperties = (Properties)dbPoolProps.get(paramString);
    if (localProperties == null) {
      throw new PoolException(1000, "Named pool has not been initialized: Requested pool is " + paramString);
    }
    try
    {
      localInteger = (Integer)localProperties.get(jdField_if);
      localInteger = new Integer(localInteger.intValue() - 1);
      localProperties.put(jdField_if, localInteger);
      i = localInteger.intValue();
    }
    catch (Exception localException1)
    {
      throw new PoolException(1, localException1);
    }
    try
    {
      if (i == 0)
      {
        DataSource localDataSource = (DataSource)dbDataSources.remove(paramString);
        dbPoolProps.remove(paramString);
        if ((localDataSource instanceof IConnectionPool)) {
          bool = ((IConnectionPool)localDataSource).releasePool();
        }
        localDataSource = null;
      }
    }
    catch (Exception localException2)
    {
      bool = false;
      throw new PoolException(1, localException2);
    }
    return bool;
  }
  
  public static String getDBType(String paramString)
    throws PoolException
  {
    Properties localProperties = (Properties)dbPoolProps.get(paramString);
    if (localProperties == null) {
      throw new PoolException(1000, "Named pool has not been initialized: Requested pool is " + paramString);
    }
    return (String)localProperties.get("ConnectionType");
  }
  
  private static synchronized void jdField_do(String paramString, Properties paramProperties)
    throws PoolException
  {
    Properties localProperties1 = (Properties)dbPoolProps.get(paramString);
    Integer localInteger = null;
    if (localProperties1 == null)
    {
      Properties localProperties2 = new Properties();
      localProperties2.putAll(paramProperties);
      localInteger = new Integer(1);
      localProperties2.put(jdField_if, localInteger);
      localProperties2.put(a, paramString);
      String str = paramProperties.getProperty("ConnectionTimeout");
      int i = 0;
      try
      {
        i = Integer.parseInt(str);
      }
      catch (Exception localException) {}
      if (i == 0) {
        i = 30;
      }
      localProperties2.put("ConnectionTimeout", String.valueOf(i));
      jdField_if(paramString, localProperties2);
      dbPoolProps.put(paramString, localProperties2);
    }
    else
    {
      localInteger = (Integer)localProperties1.get(jdField_if);
      localInteger = new Integer(localInteger.intValue() + 1);
      localProperties1.put(jdField_if, localInteger);
      a(paramString, paramProperties);
    }
  }
  
  private static void jdField_if(String paramString, Properties paramProperties)
    throws PoolException
  {
    DataSource localDataSource = null;
    if (paramProperties != null) {
      try
      {
        String str = (String)paramProperties.get("PoolType");
        if ((str.equals("WebSphere")) || (str.equals("Weblogic")) || (str.equals("EAServer")))
        {
          localDataSource = a(paramProperties, str);
        }
        else
        {
          if (paramProperties.getProperty("ClassName") == null) {
            paramProperties.setProperty("ClassName", "com.ffusion.util.db.FFIConnectionPool");
          }
          localDataSource = a(paramProperties);
        }
        dbDataSources.put(paramString, localDataSource);
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, "Could not connect to a datasource. " + paramString + " " + localException.getMessage());
        throw new PoolException(1004, "Could not connect to a datasource. " + paramString, localException);
      }
    }
  }
  
  private static DataSource a(Properties paramProperties, String paramString)
    throws Exception
  {
    DataSource localDataSource = null;
    String str1 = (String)paramProperties.get("CTXFactory");
    String str2 = (String)paramProperties.get("ProviderURL");
    String str3 = (String)paramProperties.get("PoolName");
    if ((str1 == null) || (str1.length() == 0)) {
      if (paramString.equals("WebSphere")) {
        str1 = "com.ibm.websphere.naming.WsnInitialContextFactory";
      } else if (paramString.equals("Weblogic")) {
        str1 = "weblogic.jndi.WLInitialContextFactory";
      } else if (paramString.equals("EAServer")) {
        str1 = "com.sybase.ejb.InitialContextFactory";
      } else {
        str1 = "";
      }
    }
    Hashtable localHashtable = new Hashtable(2);
    localHashtable.put("java.naming.factory.initial", str1);
    if ((str2 != null) && (str2.length() > 0)) {
      localHashtable.put("java.naming.provider.url", str2);
    }
    InitialContext localInitialContext = new InitialContext(localHashtable);
    Object localObject = localInitialContext.lookup(str3);
    localDataSource = (DataSource)PortableRemoteObject.narrow(localObject, DataSource.class);
    return localDataSource;
  }
  
  private static DataSource a(Properties paramProperties)
    throws Exception
  {
    DataSource localDataSource = null;
    String str = (String)paramProperties.get("ClassName");
    Class localClass = Class.forName(str);
    IConnectionPool localIConnectionPool = (IConnectionPool)localClass.newInstance();
    localDataSource = localIConnectionPool.getDataSource(paramProperties);
    return localDataSource;
  }
  
  private static void a(DataSource paramDataSource, Connection paramConnection)
  {
    if ((paramDataSource instanceof IConnectionPool)) {
      ((IConnectionPool)paramDataSource).removeInvalidConnection(paramConnection);
    } else {
      try
      {
        paramConnection.close();
      }
      catch (Exception localException) {}
    }
  }
  
  private static void a(String paramString, Properties paramProperties)
  {
    Properties localProperties = (Properties)dbPoolProps.get(paramString);
    if ((localProperties != null) && (dbDataSources.get(paramString) != null) && ((dbDataSources.get(paramString) instanceof FFIConnectionPool)))
    {
      String str1 = (String)localProperties.get("MaxConnections");
      String str2 = (String)paramProperties.get("MaxConnections");
      if (str2 != null)
      {
        int i = Integer.parseInt(str1);
        int j = Integer.parseInt(str2);
        if (j > i)
        {
          localProperties.setProperty("MaxConnections", str2);
          FFIConnectionPool localFFIConnectionPool = (FFIConnectionPool)dbDataSources.get(paramString);
          localFFIConnectionPool.setMaxConnections(j);
        }
      }
    }
  }
  
  public static void setValidationLevel(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      Iterator localIterator;
      DataSource localDataSource;
      if ((i != validationLevel) && (i == 0))
      {
        validationLevel = i;
        localIterator = dbDataSources.values().iterator();
        while (localIterator.hasNext())
        {
          localDataSource = (DataSource)localIterator.next();
          if ((localDataSource instanceof FFIConnectionPool)) {
            ((FFIConnectionPool)localDataSource).stopConnectionValidation();
          }
        }
      }
      else if ((i != validationLevel) && (validationLevel == 0))
      {
        validationLevel = i;
        localIterator = dbDataSources.values().iterator();
        while (localIterator.hasNext())
        {
          localDataSource = (DataSource)localIterator.next();
          if ((localDataSource instanceof FFIConnectionPool)) {
            ((FFIConnectionPool)localDataSource).startConnectionValidation();
          }
        }
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public static String getDiagnostics()
  {
    Iterator localIterator = dbDataSources.keySet().iterator();
    StringBuffer localStringBuffer = new StringBuffer();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      Properties localProperties = (Properties)dbPoolProps.get(str1);
      Enumeration localEnumeration = localProperties.propertyNames();
      localStringBuffer.append("<br>-----------------------------------------------------------<br>");
      localStringBuffer.append("Pool Name: " + str1 + "<br>");
      while (localEnumeration.hasMoreElements())
      {
        localObject = (String)localEnumeration.nextElement();
        String str2;
        if (((String)localObject).equalsIgnoreCase("password")) {
          str2 = "****";
        } else {
          str2 = localProperties.getProperty((String)localObject);
        }
        localStringBuffer.append((String)localObject + ": " + str2 + "<br>");
      }
      localStringBuffer.append("<br>");
      Object localObject = (DataSource)dbDataSources.get(str1);
      if ((localObject instanceof FFIConnectionPool)) {
        ((FFIConnectionPool)localObject).getDiagnostics(localStringBuffer);
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.ConnectionPool
 * JD-Core Version:    0.7.0.1
 */