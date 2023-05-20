package com.ffusion.ffs.db;

import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class FFSConnection
  implements FFSDBConst, FFSConst
{
  private static int H = 0;
  private static Object G = new Object();
  private Integer I = new Integer(0);
  private Connection M = null;
  private FFSDBProperties L = null;
  private int K = 100;
  public int _lockId = -1;
  public int _isolationLevel = -1;
  private a J = new a();
  
  public Integer getId()
  {
    return this.I;
  }
  
  public final synchronized void pruneCache()
  {
    while (this.J.jdField_do.size() > this.J.a)
    {
      FFSDebug.log("Pruneing the cache", 6);
      String str = (String)this.J.jdField_do.removeFirst();
      if (str != null)
      {
        PreparedStatement localPreparedStatement = (PreparedStatement)this.J.jdField_for.remove(str);
        try
        {
          localPreparedStatement.close();
        }
        catch (SQLException localSQLException)
        {
          FFSDebug.log("Failed to prune the cache", 0);
        }
      }
    }
  }
  
  public FFSConnection(Connection paramConnection, FFSDBProperties paramFFSDBProperties)
  {
    synchronized (G)
    {
      this.I = new Integer(++H);
    }
    this.M = paramConnection;
    this.L = paramFFSDBProperties;
    FFSDebug.log("FFSConnection.FFSConnection(): _id=" + this.I + " Creating FFSConnection, _conn=" + this.M, 6);
    if (paramConnection != null) {
      try
      {
        if (!paramConnection.isClosed()) {
          this.K = 100;
        }
      }
      catch (SQLException localSQLException)
      {
        FFSDebug.log("Failed to check connection status?", 0);
        this.K = 101;
      }
    }
    String str = this.L._connProps.getProperty("MaxPrepStatementCacheSize", String.valueOf(50));
    int i = Integer.parseInt(str);
    this.J.a(i);
  }
  
  public void clearStmtCache()
    throws FFSException
  {
    if (this.J != null) {
      this.J.a();
    }
  }
  
  public void close()
    throws FFSException
  {
    FFSDebug.log("FFSConnection.close(): _id=" + this.I + " Closing FFSConnection for FFSConnection, _conn=" + this.M, 6);
    if (this.J != null) {
      this.J.a();
    }
    if (this.K == 100) {
      try
      {
        this.M.close();
      }
      catch (SQLException localSQLException)
      {
        int i = 1;
        String str2 = localSQLException.getSQLState();
        if (str2.equals("JZ0C0")) {
          i = 0;
        } else if (str2.equals("JZ006")) {
          i = 0;
        }
        if (i != 0)
        {
          String str3 = "Failed to close connection: " + FFSDebug.stackTrace(localSQLException);
          FFSDebug.log(str3, 0);
          throw new FFSException(str3);
        }
      }
      catch (Throwable localThrowable)
      {
        String str1 = "Failed to close connection: " + FFSDebug.stackTrace(localThrowable);
        FFSDebug.log(str1, 0);
      }
    }
    this.K = 101;
  }
  
  public boolean getAutoCommit()
  {
    boolean bool = false;
    try
    {
      bool = this.M.getAutoCommit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return bool;
  }
  
  public boolean setAutoCommit(boolean paramBoolean)
    throws FFSException
  {
    try
    {
      if (this.K == 100)
      {
        this.M.setAutoCommit(paramBoolean);
        return true;
      }
      return false;
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to set autocommit: " + localSQLException.getMessage();
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
  }
  
  public void commit()
    throws FFSException
  {
    try
    {
      this.M.commit();
    }
    catch (SQLException localSQLException)
    {
      FFSDebug.log(" Commit failed in FFSConnection.", localSQLException.getMessage(), 0);
      throw new FFSException("Failed to set autocommit: " + localSQLException.getMessage());
    }
  }
  
  public void rollback()
  {
    try
    {
      this.M.rollback();
    }
    catch (SQLException localSQLException)
    {
      FFSDebug.log("FFSConnection rollback failed.", localSQLException.getMessage(), 0);
    }
  }
  
  public int executeCommand(String paramString)
    throws FFSException
  {
    return executeCommand(paramString, null);
  }
  
  public int executeCommand(String paramString, Object[] paramArrayOfObject)
    throws FFSException
  {
    int i = 0;
    try
    {
      PreparedStatement localPreparedStatement = this.J.a(paramString);
      if (localPreparedStatement == null)
      {
        localPreparedStatement = this.M.prepareStatement(paramString);
        this.J.a(paramString, localPreparedStatement);
      }
      FFSDBUtils.fillParameters(localPreparedStatement, paramArrayOfObject);
      i = localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to execute query: " + paramString + FFSConst.LINE_SEPARATOR + "Error: " + localSQLException.getMessage() + FFSConst.LINE_SEPARATOR + FFSDebug.stackTrace(localSQLException);
      FFSDebug.log(str, 0);
      if (localSQLException.getSQLState().equals("JZ006")) {
        throw new FFSException(2, str + localSQLException.getMessage());
      }
      throw new FFSException(0, str + localSQLException.getMessage());
    }
    return i;
  }
  
  public int executeCommand(String paramString, Object[] paramArrayOfObject, int paramInt)
    throws FFSException
  {
    int i = 0;
    try
    {
      PreparedStatement localPreparedStatement = this.J.a(paramString);
      if (localPreparedStatement == null)
      {
        localPreparedStatement = this.M.prepareStatement(paramString);
        this.J.a(paramString, localPreparedStatement);
      }
      FFSDBUtils.fillParameters(localPreparedStatement, paramArrayOfObject, paramInt);
      i = localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to execute query: " + paramString + FFSConst.LINE_SEPARATOR + "Error: " + localSQLException.getMessage() + FFSConst.LINE_SEPARATOR + FFSDebug.stackTrace(localSQLException);
      FFSDebug.log(str, 0);
      if (localSQLException.getSQLState().equals("JZ006")) {
        throw new FFSException(2, str + localSQLException.getMessage());
      }
      throw new FFSException(0, str + localSQLException.getMessage());
    }
    return i;
  }
  
  public FFSResultSet prepareStmt(String paramString)
    throws FFSException
  {
    PreparedStatement localPreparedStatement = this.J.a(paramString);
    try
    {
      if (localPreparedStatement == null)
      {
        localPreparedStatement = this.M.prepareStatement(paramString);
        this.J.a(paramString, localPreparedStatement);
      }
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to prepare statment for query: " + paramString + FFSConst.LINE_SEPARATOR + "Error: " + localSQLException.getMessage();
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    if (this.L._connInfo._dbConnInfo._driver.equals("sun.jdbc.odbc.JdbcOdbcDriver")) {
      return new FFSResultSet(true, localPreparedStatement);
    }
    return new FFSResultSet(false, localPreparedStatement);
  }
  
  public PreparedStatement prepareStatement(String paramString)
    throws FFSException
  {
    PreparedStatement localPreparedStatement = this.J.a(paramString);
    try
    {
      if (localPreparedStatement == null)
      {
        localPreparedStatement = this.M.prepareStatement(paramString);
        this.J.a(paramString, localPreparedStatement);
      }
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to prepare statment for query: " + paramString + FFSConst.LINE_SEPARATOR + "Error: " + localSQLException.getMessage();
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    return localPreparedStatement;
  }
  
  public int getConnectionStatus()
  {
    return this.K;
  }
  
  public boolean isClosed()
    throws FFSException
  {
    boolean bool = false;
    try
    {
      bool = this.M.isClosed();
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to check connection state..." + FFSConst.LINE_SEPARATOR + "Error: " + localSQLException.getMessage();
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    return bool;
  }
  
  public String getServerName()
  {
    return this.L._connInfo._dbConnInfo._serverName;
  }
  
  public String getDatabaseName()
  {
    return this.L._connInfo._dbConnInfo._databaseName;
  }
  
  public String getUserName()
  {
    return this.L._connInfo._dbConnInfo._user;
  }
  
  public String getPassword()
  {
    return this.L._connInfo._dbConnInfo._password;
  }
  
  public String getHostName()
  {
    return this.L._connInfo._dbConnInfo._host;
  }
  
  public int getPortNum()
  {
    return this.L._connInfo._dbConnInfo._port;
  }
  
  public String getDatabaseType()
  {
    return this.L._connInfo._dbConnInfo._dbType;
  }
  
  public Connection getConnection()
  {
    return this.M;
  }
  
  public void setReadOnly(boolean paramBoolean)
    throws FFSException
  {
    try
    {
      this.M.setReadOnly(paramBoolean);
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to setReadOnly, connection Error: " + localSQLException.getMessage();
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
  }
  
  public void setCatalog(String paramString)
    throws FFSException
  {
    try
    {
      this.M.setCatalog(paramString);
    }
    catch (SQLException localSQLException)
    {
      String str = "Failed to setCatalog, connection Error: " + localSQLException.getMessage();
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
  }
  
  public void setTransactionIsolation(int paramInt)
    throws FFSException
  {
    FFSDebug.log("FFSConnection.setTransactionIsolation changing Connection isolation level: " + paramInt, 6);
    if ((this.M == null) || (paramInt < 0))
    {
      FFSDebug.log("FFSConnection.setTransactionIsolation failed. ", "Invalid connection or isolation level", 0);
      return;
    }
    String str1 = getDatabaseType().toUpperCase();
    if ((str1.startsWith("ORACLE")) && (paramInt < 2))
    {
      FFSDebug.log("FFSConnection.setTransactionIsolation failed. ", "Invalid isolation level for oracle: " + paramInt, 1);
      return;
    }
    try
    {
      this._isolationLevel = getTransactionIsolation();
      this.M.setTransactionIsolation(paramInt);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "FFSconnection.setTransactionIsolation failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
  }
  
  public void setOriginalTransactionIsolation()
    throws FFSException
  {
    setTransactionIsolation(this._isolationLevel);
    this._isolationLevel = -1;
  }
  
  public boolean transactionIsolationChanged()
  {
    return this._isolationLevel != -1;
  }
  
  public int getTransactionIsolation()
    throws FFSException
  {
    if (this.M == null)
    {
      String str1 = "FFSconnection.getTransactionIsolation failed. Invalid connection ";
      FFSDebug.log(str1, 0);
      throw new FFSException(str1);
    }
    try
    {
      return this.M.getTransactionIsolation();
    }
    catch (Throwable localThrowable)
    {
      String str2 = "FFSconnection.getTransactionIsolation failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
  }
  
  public int getNextId(String paramString)
    throws FFSException
  {
    int i = -1;
    FFSDebug.log("++ FFSConnection.getNextId.  Getting id", 6);
    String str1 = getDatabaseType().toUpperCase();
    if (str1.startsWith("ORACLE"))
    {
      i = a(paramString);
    }
    else if (str1.startsWith("MSSQL"))
    {
      i = jdMethod_do(paramString);
    }
    else if (str1.startsWith("ASA"))
    {
      i = jdMethod_for(paramString);
    }
    else if (str1.startsWith("ASE"))
    {
      i = jdMethod_for(paramString);
    }
    else if (str1.startsWith("DB2"))
    {
      i = jdMethod_if(paramString);
    }
    else
    {
      String str2 = "FFSConnection.getNextId failed. Error: Invalid database type";
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
    return i;
  }
  
  private int a(String paramString)
    throws FFSException
  {
    int i = -1;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select ").append(paramString).append("_seq.NEXTVAL from dual");
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = prepareStmt(localStringBuffer.toString());
      localFFSResultSet.open(null);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      String str = "FFSConnection.getNextId from Oracle failed. Error: " + localException.getMessage() + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
  }
  
  private int jdMethod_do(String paramString)
    throws FFSException
  {
    int i = -1;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.M.prepareCall("{CALL nextval(?,?)}");
      localCallableStatement.setString(1, paramString + "_seq");
      localCallableStatement.registerOutParameter(2, 4);
      localCallableStatement.execute();
      i = localCallableStatement.getInt(2);
      int j = i;
      return j;
    }
    catch (Exception localException1)
    {
      String str1 = "FFSConnection.getNextId from Microsoft failed. Error: " + localException1.getMessage() + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str1, 0);
      throw new FFSException(str1);
    }
    finally
    {
      try
      {
        if (localCallableStatement != null) {
          localCallableStatement.close();
        }
      }
      catch (Exception localException2)
      {
        String str2 = "FFSConnection.getNextId from Microsoft failed. Error in closing callable statement. " + localException2.getMessage() + FFSDebug.stackTrace(localException2);
        FFSDebug.log(str2, 0);
      }
    }
  }
  
  private int jdMethod_for(String paramString)
    throws FFSException
  {
    int i = -1;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = this.M.prepareCall("{CALL nextval(?,?)}");
      localCallableStatement.setString(1, paramString + "_seq");
      localCallableStatement.registerOutParameter(2, 4);
      localCallableStatement.execute();
      i = localCallableStatement.getInt(2);
      int j = i;
      return j;
    }
    catch (Exception localException1)
    {
      String str1 = "FFSConnection.getNextId from Sybase failed. Error: " + localException1.getMessage() + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str1, 0);
      throw new FFSException(str1);
    }
    finally
    {
      try
      {
        if (localCallableStatement != null) {
          localCallableStatement.close();
        }
      }
      catch (Exception localException2)
      {
        String str2 = "FFSConnection.getNextId from Sybase failed. Error in closing callable statement. " + localException2.getMessage() + FFSDebug.stackTrace(localException2);
        FFSDebug.log(str2, 0);
      }
    }
  }
  
  private int jdMethod_if(String paramString)
    throws FFSException
  {
    FFSDebug.log("++ FFSConnection.getNextIBM.  Getting id for IBM", 6);
    int i = -1;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select NEXTVAL FOR ").append(paramString).append("_seq from sysibm.sysdummy1");
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = prepareStmt(localStringBuffer.toString());
      localFFSResultSet.open(null);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      String str = "FFSConnection.getNextId from IBM failed. Error: " + localException.getMessage() + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    finally
    {
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
    }
  }
  
  public String getLimitedSelect(String paramString, int paramInt)
  {
    if (getDatabaseType().toLowerCase().indexOf("db2") != -1)
    {
      paramString = paramString + " FETCH FIRST " + paramInt + " ROWS ONLY ";
    }
    else if (getDatabaseType().toLowerCase().indexOf("ora") != -1)
    {
      String str = " and rownum <= " + paramInt + " ";
      StringBuffer localStringBuffer = new StringBuffer(paramString);
      int i = paramString.toLowerCase().indexOf("order by");
      if (i != -1) {
        localStringBuffer.insert(i, str);
      } else {
        localStringBuffer.append(str);
      }
      paramString = localStringBuffer.toString();
    }
    else if (getDatabaseType().toLowerCase().indexOf("ase") != -1)
    {
      paramString = "set rowcount " + paramInt + " " + paramString + " set rowcount 0";
    }
    else if (getDatabaseType().toLowerCase().indexOf("mssql") == -1) {}
    return paramString;
  }
  
  private class a
  {
    private static final int jdField_int = 50;
    private static final int jdField_if = 89;
    private final HashMap jdField_for = new HashMap(89);
    private final LinkedList jdField_do = new LinkedList();
    private int a = 50;
    
    public a() {}
    
    public a(int paramInt)
    {
      this();
      a(paramInt);
    }
    
    public void a(int paramInt)
    {
      this.a = paramInt;
      FFSDebug.log("Max statement cache size set to " + this.a, 6);
    }
    
    public int jdField_if()
    {
      return this.a;
    }
    
    final synchronized PreparedStatement a(String paramString)
    {
      return (PreparedStatement)this.jdField_for.get(paramString);
    }
    
    final synchronized void a(String paramString, PreparedStatement paramPreparedStatement)
    {
      this.jdField_for.put(paramString, paramPreparedStatement);
      this.jdField_do.addLast(paramString);
      int i = this.jdField_do.size();
      if (i >= this.a)
      {
        if ((i - this.a) % 10 == 0) {
          FFSDebug.log("WARNNING: Reached or exceeded the maximum prepared statement cache size.  Maximum prepared statement cache size = " + this.a + "  Current prepared statement cache size = " + i, 0);
        }
        if ((i > this.a) && (i % this.a == 0))
        {
          HashSet localHashSet = new HashSet(this.jdField_for.keySet());
          int j = 0;
          FFSDebug.log("Statement cache size (" + localHashSet.size() + ") exceeds " + i / this.a + "x cache size, printing statements: ", 0);
          Iterator localIterator = localHashSet.iterator();
          while (localIterator.hasNext()) {
            FFSDebug.log(String.valueOf(j++) + ": " + (String)localIterator.next(), 0);
          }
        }
      }
    }
    
    final synchronized void a()
    {
      while (this.jdField_do.size() != 0)
      {
        String str = (String)this.jdField_do.removeLast();
        if (str != null) {
          try
          {
            PreparedStatement localPreparedStatement = (PreparedStatement)this.jdField_for.remove(str);
            localPreparedStatement.close();
          }
          catch (Throwable localThrowable)
          {
            FFSDebug.log("Failed to clear cache", 0);
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSConnection
 * JD-Core Version:    0.7.0.1
 */