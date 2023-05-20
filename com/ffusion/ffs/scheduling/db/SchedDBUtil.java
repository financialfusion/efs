package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.db.ConnectionPool;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;

public class SchedDBUtil
{
  public static FFSConnection getConnection()
  {
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      return localConnectionPool.getConnection();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "Error in getConnection.");
    }
    return null;
  }
  
  public static void freeConnection(FFSConnection paramFFSConnection)
  {
    ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
    localConnectionPool.releaseConnection(paramFFSConnection);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.SchedDBUtil
 * JD-Core Version:    0.7.0.1
 */