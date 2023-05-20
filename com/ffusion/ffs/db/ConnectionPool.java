package com.ffusion.ffs.db;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;

public abstract interface ConnectionPool
  extends FFSConst
{
  public abstract FFSConnection getConnection()
    throws FFSException;
  
  public abstract FFSConnection getConnection(int paramInt)
    throws FFSException;
  
  public abstract FFSConnection[] getConnections(int paramInt)
    throws FFSException;
  
  public abstract void releaseConnection(FFSConnection paramFFSConnection);
  
  public abstract void disposeConnection(FFSConnection paramFFSConnection)
    throws FFSException;
  
  public abstract FFSConnection renewConnection(FFSConnection paramFFSConnection)
    throws FFSException;
  
  public abstract FFSConnection renewConnectionNoRefresh(FFSConnection paramFFSConnection)
    throws FFSException;
  
  public abstract void refreshPool()
    throws FFSException;
  
  public abstract void closePool()
    throws FFSException;
  
  public abstract int getAvailabeConnNum()
    throws FFSException;
  
  public abstract int getPoolCapacity()
    throws FFSException;
  
  public abstract void checkRefreshPool();
  
  public abstract int getWaitingThreads();
  
  public abstract int getTranxRetryNum();
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.ConnectionPool
 * JD-Core Version:    0.7.0.1
 */