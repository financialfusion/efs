package com.ffusion.ffs.db;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.util.Hashtable;

public class ConnectionPoolFactory
  implements FFSDBConst, FFSConst
{
  private static final Hashtable N = new Hashtable();
  
  public static ConnectionPool createConnectionPool(FFSDBProperties paramFFSDBProperties, String paramString)
    throws FFSException
  {
    ConnectionPoolImpl localConnectionPoolImpl = null;
    if (N.contains(paramString))
    {
      FFSDebug.log("Connection Pool: ", paramString, " is already", "active. Please release the active connection pool before ", "creating a new one", 0);
      throw new FFSException("Connection Pool: " + paramString + " is already active");
    }
    try
    {
      localConnectionPoolImpl = new ConnectionPoolImpl(paramFFSDBProperties);
      if (localConnectionPoolImpl != null)
      {
        N.put(localConnectionPoolImpl, paramString);
        FFSDebug.log("Connection Pool: ", paramString, " created successfully", 6);
      }
    }
    catch (Throwable localThrowable)
    {
      String str = "ConnectionPoolImpl.initPool: Failed to initialize the connection pool. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, "Failed to create Connection Pool. Error: " + str);
    }
    return localConnectionPoolImpl;
  }
  
  public static ConnectionPool createConnectionPool(FFSProperties paramFFSProperties, String paramString)
    throws FFSException
  {
    if (N.contains(paramString))
    {
      FFSDebug.log("Connection Pool: ", paramString, " is already", "active. Please relase the active connection pool before ", "creating a new one", 0);
      throw new FFSException("Connection Pool: " + paramString + " is already active");
    }
    FFSDBProperties localFFSDBProperties = new FFSDBProperties(paramFFSProperties);
    return createConnectionPool(localFFSDBProperties, paramString);
  }
  
  public static ConnectionPool createConnectionPool(String paramString1, String paramString2)
    throws FFSException
  {
    if (N.contains(paramString2))
    {
      FFSDebug.log("Connection Pool: ", paramString2, " is already", "active. Please relase the active connection pool before ", "creating a new one", 0);
      throw new FFSException("Connection Pool: " + paramString2 + " is already active");
    }
    FFSProperties localFFSProperties = new FFSProperties(paramString1);
    FFSDBProperties localFFSDBProperties = new FFSDBProperties(localFFSProperties);
    return createConnectionPool(localFFSDBProperties, paramString2);
  }
  
  public static void releaseConnectionPool(ConnectionPool paramConnectionPool)
    throws FFSException
  {
    if (N.containsKey(paramConnectionPool))
    {
      String str = (String)N.get(paramConnectionPool);
      N.remove(paramConnectionPool);
      paramConnectionPool = null;
      FFSDebug.log("Connection Pool: ", str, " released", 4);
    }
    else
    {
      FFSDebug.log("Connection Pool does not exists!", 0);
      throw new FFSException("Failed to release Connection Pool,It does not exists");
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.ConnectionPoolFactory
 * JD-Core Version:    0.7.0.1
 */