package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.util.smartcalendar.SCException;
import com.ffusion.util.smartcalendar.adapter.SCAdapter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

public class BPWSCAdapter
  extends SCAdapter
{
  private Hashtable t;
  private String u = "EXTERNAL_CONNECTION_POOL";
  
  public void initialize(HashMap paramHashMap)
    throws SCException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Properties localProperties = new Properties();
      localProperties.setProperty("ConnectionType", jdMethod_int());
      localProperties.setProperty(this.u, "true");
      paramHashMap.put("DB_PROPERTIES", localProperties);
      localObject = new Properties();
      ((Properties)localObject).put("REFRESH_INTERVAL", "0");
      paramHashMap.put("CACHE_PROPERTIES", localObject);
      super.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      Object localObject = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter((Writer)localObject);
      localException.printStackTrace(localPrintWriter);
      String str = "An error occurred while initializing the BPW Smart Calendar adapter: " + ((StringWriter)localObject).toString();
      FFSDebug.throwing(str, localException);
      throw new SCException(1, str, localException);
    }
  }
  
  private String jdMethod_int()
    throws SCException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        str1 = "Failed to obtain a connection from from the BPTW connection pool.";
        FFSDebug.log(str1);
        throw new SCException(11, str1);
      }
      String str1 = localFFSConnectionHolder.conn.getDatabaseType();
      return str1;
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "An error occurred while obtaining a connection from the BPTW database connection pool.";
      FFSDebug.throwing(str2, localBPWException);
      throw new SCException(11, str2, localBPWException);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  protected Connection getDBConnection()
    throws SCException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localFFSConnectionHolder.conn.setAutoCommit(true);
      if (localFFSConnectionHolder.conn == null)
      {
        localObject = "Failed to obtain a connection from from the BPTW connection pool.";
        FFSDebug.log((String)localObject);
        throw new SCException(11, (String)localObject);
      }
      if (this.t == null) {
        this.t = new Hashtable();
      }
      Object localObject = localFFSConnectionHolder.conn.getConnection();
      this.t.put(localObject, localFFSConnectionHolder.conn);
      return localObject;
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "An error occurred while obtaining a connection from the BPTW database connection pool.";
      FFSDebug.throwing(str, localBPWException);
      throw new SCException(11, str, localBPWException);
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "An error occurred while obtaining a connection from the BPTW database connection pool.";
      FFSDebug.throwing(str, localFFSException);
      throw new SCException(11, str, localFFSException);
    }
  }
  
  protected void returnDBConnection(Connection paramConnection)
    throws SCException
  {
    try
    {
      if ((this.t != null) && (!this.t.isEmpty()))
      {
        FFSConnection localFFSConnection = (FFSConnection)this.t.remove(paramConnection);
        if (localFFSConnection != null)
        {
          localFFSConnection.setAutoCommit(false);
          DBUtil.freeConnection(localFFSConnection);
        }
        else
        {
          FFSDebug.log("BPWSCAdapter.returnDBConnection: Failed to return a connection to the BPW database connection pool.  The connection was not used for the smart calendar.", 6);
        }
      }
      else
      {
        this.t = null;
        FFSDebug.log("BPWSCAdapter.returnDBConnection: Failed to return a connection to the BPW database connection pool.  The connection was not used for the smart calendar.", 6);
      }
    }
    catch (Exception localException)
    {
      String str = "An error occurred while returning a connection to the BPW database connection pool.";
      FFSDebug.throwing(str, localException);
      throw new SCException(11, str, localException);
    }
  }
  
  public void reset()
  {
    if ((this.t != null) && (!this.t.isEmpty()))
    {
      Enumeration localEnumeration = this.t.keys();
      try
      {
        while (!this.t.isEmpty()) {
          DBUtil.freeConnection((FFSConnection)this.t.remove((Connection)localEnumeration.nextElement()));
        }
      }
      catch (Exception localException)
      {
        String str = "An error occurred while returning a connection to the BPW database connection pool.";
        FFSDebug.log(str, localException);
      }
    }
    this.t = null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.BPWSCAdapter
 * JD-Core Version:    0.7.0.1
 */