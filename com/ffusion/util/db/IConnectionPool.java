package com.ffusion.util.db;

import java.sql.Connection;
import java.util.Properties;
import javax.sql.DataSource;

public abstract interface IConnectionPool
{
  public abstract DataSource getDataSource(Properties paramProperties)
    throws Exception;
  
  public abstract boolean returnConnection(Connection paramConnection);
  
  public abstract void removeInvalidConnection(Connection paramConnection);
  
  public abstract boolean releasePool();
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.IConnectionPool
 * JD-Core Version:    0.7.0.1
 */