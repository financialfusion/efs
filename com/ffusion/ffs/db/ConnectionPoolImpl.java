package com.ffusion.ffs.db;

import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public class ConnectionPoolImpl
  implements ConnectionPool, FFSDBConst, FFSConst
{
  private boolean O = false;
  private final LinkedList S = new LinkedList();
  private ListIterator T = null;
  private final TreeMap U = new TreeMap();
  protected FFSDBProperties W;
  private boolean V = false;
  private int Q = 0;
  private boolean R = false;
  private int P = 0;
  
  public ConnectionPoolImpl(FFSDBProperties paramFFSDBProperties)
  {
    if (paramFFSDBProperties != null)
    {
      this.W = paramFFSDBProperties;
      this.R = true;
    }
    else
    {
      System.out.println("Failed to create connection Pool. Invalid database properties.");
      this.R = false;
    }
    this.Q = this.W._connInfo._maxPoolSize;
    this.P = 0;
    jdMethod_int();
  }
  
  private synchronized a jdMethod_do(FFSConnection paramFFSConnection)
    throws FFSException
  {
    if (paramFFSConnection == null) {
      return null;
    }
    Integer localInteger = paramFFSConnection.getId();
    a locala = (a)this.U.get(localInteger);
    if (locala != null)
    {
      if (locala.jdField_int != 0) {
        throw new FFSException("Connection Pool integrity constraint violation - connection(" + localInteger + ") already owned by pool.");
      }
    }
    else
    {
      locala = new a(paramFFSConnection);
      this.U.put(localInteger, locala);
    }
    return locala;
  }
  
  private synchronized void jdMethod_int(FFSConnection paramFFSConnection)
    throws FFSException
  {
    if (paramFFSConnection == null) {
      return;
    }
    Integer localInteger = paramFFSConnection.getId();
    a locala = (a)this.U.get(localInteger);
    a(locala);
  }
  
  private synchronized void a(a parama)
    throws FFSException
  {
    if (parama == null) {
      return;
    }
    if (parama.jdField_int == 1) {
      if (this.T != null) {
        this.T.remove();
      } else {
        this.S.remove(parama);
      }
    }
    this.U.remove(parama.jdField_for.getId());
  }
  
  private synchronized void jdMethod_do(a parama)
  {
    if (parama != null) {
      jdMethod_try(parama.jdField_for);
    }
  }
  
  private synchronized void jdMethod_try(FFSConnection paramFFSConnection)
  {
    if (paramFFSConnection == null) {
      return;
    }
    Integer localInteger = paramFFSConnection.getId();
    try
    {
      a locala = (a)this.U.get(localInteger);
      if (locala != null)
      {
        Thread localThread = locala.jdField_do;
        if (locala.jdField_int == 0)
        {
          locala.jdField_int = 1;
          if (this.T != null) {
            this.T.add(locala);
          } else {
            this.S.add(locala);
          }
        }
        else
        {
          if (locala.jdField_int == 1) {
            throw new FFSException("Connection Pool integrity constraint violation - connection(" + localInteger + ") being checked in multiple times.");
          }
          if (locala.jdField_int == 2)
          {
            locala.jdField_int = 1;
            locala.jdField_do = null;
            this.S.add(locala);
            if (localThread != Thread.currentThread()) {
              throw new FFSException("Connection Pool integrity constraint violation - connection(" + localInteger + ") being checked in by thread(" + Thread.currentThread().getName() + ") but was checked out by thread(" + localThread.getName() + ").");
            }
          }
          else
          {
            throw new FFSException("ConnectionPoolImpl.checkin(): unknown item status(" + locala.jdField_int + ")");
          }
        }
      }
      else
      {
        throw new FFSException("Connection Pool integrity constraint violation - connection(" + localInteger + ") not owned by pool.");
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("ConnectionPoolImpl.checkin(): caught exception: " + FFSConst.LINE_SEPARATOR + localFFSException.getMessage() + FFSConst.LINE_SEPARATOR + FFSDebug.stackTrace(localFFSException));
    }
  }
  
  private synchronized FFSConnection jdMethod_else()
  {
    a locala = jdMethod_for();
    if (locala == null) {
      return null;
    }
    return locala.jdField_for;
  }
  
  private synchronized a jdMethod_for()
  {
    a locala = null;
    if (this.S.size() > 0) {
      try
      {
        locala = (a)this.S.removeFirst();
      }
      catch (NoSuchElementException localNoSuchElementException) {}
    }
    if (locala == null) {
      return null;
    }
    return jdMethod_if(locala);
  }
  
  private synchronized FFSConnection jdMethod_if(FFSConnection paramFFSConnection)
  {
    a locala = a(paramFFSConnection);
    if (locala == null) {
      return null;
    }
    return locala.jdField_for;
  }
  
  private synchronized a a(FFSConnection paramFFSConnection)
  {
    if (paramFFSConnection == null) {
      return jdMethod_for();
    }
    Integer localInteger = paramFFSConnection.getId();
    a locala = (a)this.U.get(localInteger);
    return jdMethod_if(locala);
  }
  
  private synchronized a jdMethod_if(a parama)
  {
    if (parama == null) {
      return jdMethod_for();
    }
    parama.jdField_int = 2;
    parama.jdField_do = Thread.currentThread();
    return parama;
  }
  
  private void jdMethod_int()
  {
    if (!this.R)
    {
      System.out.println("Failed to initialize the connection pool. Error: in database properties.");
      return;
    }
    try
    {
      for (int i = 0; i < this.W._connInfo._optimalPoolSize; i++) {
        jdMethod_try(jdMethod_new());
      }
      System.out.println("+++ ConnectionPoolImpl.initPool: Connection Pool initialized successfully.");
      System.out.println("+++ ConnectionPoolImpl.initPool: Available connections in the pool: " + String.valueOf(this.S.size()));
    }
    catch (Throwable localThrowable)
    {
      String str1 = "ConnectionPoolImpl.initPool: Failed to initialize the connection pool. Error: " + localThrowable.toString();
      System.out.println(str1);
      localThrowable.printStackTrace();
      try
      {
        closePool();
      }
      catch (FFSException localFFSException)
      {
        String str2 = "ConnectionPoolImpl.initPool: Failed to close troubled connection pool. Error: ";
        System.out.println(str2);
        localFFSException.printStackTrace();
      }
      throw new RuntimeException(str1);
    }
  }
  
  public synchronized FFSConnection getConnection()
    throws FFSException
  {
    FFSConnection localFFSConnection = null;
    long l1 = System.currentTimeMillis();
    long l2 = this.W._connInfo._dbConnInfo._connectionTimeout * 1000L;
    long l3 = l2;
    checkRefreshPool();
    jdMethod_byte();
    try
    {
      for (int i = 0; (localFFSConnection = jdMethod_try()) == null; i++)
      {
        try
        {
          this.P += 1;
          this.P -= i;
          wait(l3);
          this.P -= 1;
          this.P += i;
        }
        catch (InterruptedException localInterruptedException) {}
        l3 = l2 - (System.currentTimeMillis() - l1);
        if (l3 <= 0L)
        {
          System.out.println("ConnectionPoolImpl: Timed-out while waiting for connection.");
          long l4 = System.currentTimeMillis();
          String str = FFSConst.LINE_SEPARATOR + "ConnectionPoolImpl: Timed-out in: " + (l4 - l1) + FFSConst.LINE_SEPARATOR + "ConnectionPoolImpl: Max pool size: " + this.U.size() + FFSConst.LINE_SEPARATOR + "ConnectionPoolImpl: available connections: " + this.S.size() + FFSConst.LINE_SEPARATOR + "Timed-out while waiting for connection." + FFSConst.LINE_SEPARATOR;
          System.out.println(str);
          throw new FFSException(str);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("Failed to get connection from connection pool.");
      localThrowable.printStackTrace();
      throw new FFSException(localThrowable, "Failed to get connection from connection pool." + FFSConst.LINE_SEPARATOR);
    }
    return localFFSConnection;
  }
  
  public synchronized FFSConnection getConnection(int paramInt)
    throws FFSException
  {
    FFSConnection localFFSConnection = getConnection();
    if (localFFSConnection != null)
    {
      localFFSConnection.setTransactionIsolation(paramInt);
      return localFFSConnection;
    }
    System.out.println("ConnectionPoolImpl.getConnection: Failed to get connection from the pool.");
    return localFFSConnection;
  }
  
  public synchronized FFSConnection[] getConnections(int paramInt)
    throws FFSException
  {
    checkRefreshPool();
    jdMethod_byte();
    if (paramInt <= 0) {
      return new FFSConnection[0];
    }
    return a(paramInt);
  }
  
  public synchronized void releaseConnection(FFSConnection paramFFSConnection)
  {
    checkRefreshPool();
    if (paramFFSConnection != null)
    {
      if (paramFFSConnection.transactionIsolationChanged()) {
        try
        {
          paramFFSConnection.setOriginalTransactionIsolation();
        }
        catch (Throwable localThrowable1)
        {
          System.out.println("ConnectionPoolImpl.releaseConnection: Failed to set isolation level back. Error : ");
          localThrowable1.printStackTrace();
          try
          {
            paramFFSConnection = renewConnectionNoRefresh(paramFFSConnection);
          }
          catch (Throwable localThrowable2)
          {
            System.out.println("ConnectionPoolImpl.releaseConnection: Failed to renew this connection. Error :" + FFSConst.LINE_SEPARATOR);
            localThrowable2.printStackTrace();
            System.out.println(FFSConst.LINE_SEPARATOR + FFSConst.LINE_SEPARATOR + "THIS CONNECTION WILL NOT BE" + " RETURNED TO THE CONNECTION POOL");
            return;
          }
        }
      }
      jdMethod_try(paramFFSConnection);
      notifyAll();
    }
  }
  
  public synchronized void disposeConnection(FFSConnection paramFFSConnection)
    throws FFSException
  {
    checkRefreshPool();
    try
    {
      if (paramFFSConnection != null) {
        paramFFSConnection.close();
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("disposeConnection: Failed to clearStmtCache.");
      localThrowable.printStackTrace();
    }
    jdMethod_int(paramFFSConnection);
    System.out.println("+++ ConnectionPoolImpl.disposeConnection: Removed connection from the pool...");
    try
    {
      paramFFSConnection = jdMethod_case();
    }
    catch (FFSException localFFSException)
    {
      System.out.println("Failed to replace a corrupted connection. Error: ");
      localFFSException.printStackTrace();
      paramFFSConnection = null;
      throw localFFSException;
    }
    if (paramFFSConnection == null)
    {
      String str = "Failed to replace a corrupted connection. Connection = null";
      System.out.println(str);
      throw new FFSException(str);
    }
    notifyAll();
  }
  
  public synchronized FFSConnection renewConnection(FFSConnection paramFFSConnection)
    throws FFSException
  {
    checkRefreshPool();
    if (paramFFSConnection == null) {
      throw new FFSException("Failed to renew a NULL connection");
    }
    if (jdMethod_new(paramFFSConnection)) {
      return paramFFSConnection;
    }
    jdMethod_int(paramFFSConnection);
    refreshPool();
    try
    {
      paramFFSConnection = jdMethod_case();
    }
    catch (FFSException localFFSException)
    {
      System.out.println("Failed to renew a corrupted connection.");
      localFFSException.printStackTrace();
      paramFFSConnection = null;
      throw localFFSException;
    }
    if (paramFFSConnection != null) {
      return paramFFSConnection;
    }
    String str = "Failed to renew connection. New Pooled Connection = null";
    System.out.println(str);
    throw new FFSException(str);
  }
  
  public synchronized FFSConnection renewConnectionNoRefresh(FFSConnection paramFFSConnection)
    throws FFSException
  {
    checkRefreshPool();
    if (paramFFSConnection != null) {
      try
      {
        paramFFSConnection.close();
      }
      catch (Throwable localThrowable)
      {
        System.out.println("+++ ConnectionPoolImpl.renewConnectionNoRefresh: Failed to close a corrupted connection. Error: ");
        localThrowable.printStackTrace();
      }
    }
    jdMethod_int(paramFFSConnection);
    paramFFSConnection = null;
    System.out.println("+++ ConnectionPoolImpl.renewConnectionNoRefresh: Removed corrupted connection from the pool...");
    try
    {
      paramFFSConnection = jdMethod_case();
    }
    catch (FFSException localFFSException)
    {
      System.out.println("Failed to renew a corrupted connection.");
      localFFSException.printStackTrace();
      paramFFSConnection = null;
      throw localFFSException;
    }
    if (paramFFSConnection != null) {
      return paramFFSConnection;
    }
    String str = "Failed to renew connection. Connection = null";
    System.out.println(str);
    throw new FFSException(str);
  }
  
  public synchronized void refreshPool()
    throws FFSException
  {
    System.out.println("+++ ConnectionPoolImpl.refreshPool: Calling refreshPool.");
    this.V = true;
    try
    {
      this.T = this.S.listIterator(0);
      while (this.T.hasNext())
      {
        a locala = (a)this.T.next();
        FFSConnection localFFSConnection = locala.jdField_for;
        if ((locala.jdField_for == null) || (!jdMethod_new(locala.jdField_for))) {
          try
          {
            a(locala);
            localFFSConnection = null;
            System.out.println("+++ ConnectionPoolImpl.refreshPool: Removed connection from the pool...");
            jdMethod_try(jdMethod_new());
          }
          catch (FFSException localFFSException)
          {
            System.out.println("Failed to substitute a closed connection with a new one: ");
            localFFSException.printStackTrace();
            localFFSConnection = null;
          }
          catch (Throwable localThrowable2)
          {
            System.out.println("Failed to substitute a closed connection with a new one :");
            localThrowable2.printStackTrace();
            localFFSConnection = null;
          }
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      System.out.println("Exception while refreshing connection pool : ");
      localThrowable1.printStackTrace();
    }
    finally
    {
      this.V = false;
      this.T = null;
    }
  }
  
  public synchronized void closePool()
    throws FFSException
  {
    this.O = true;
    int i = 0;
    while ((this.S.size() < this.U.size()) && (i++ < 6))
    {
      System.out.println("+++ ConnectionPoolImpl.closePool: Some connections are currently in use. Please Wait....");
      System.out.println("Connections currently in use: " + (this.U.size() - this.S.size()));
      try
      {
        wait(500L);
      }
      catch (InterruptedException localInterruptedException) {}
    }
    if (this.S.size() < this.U.size())
    {
      System.out.println("+++ WARNING: Closing the connection pool while some connections are still open!!!!!!!!");
      i = 0;
    }
    jdMethod_char();
    this.U.clear();
    this.S.clear();
    ConnectionPoolFactory.releaseConnectionPool(this);
    System.out.println("ConnectionPoolImpl: Done closePool");
  }
  
  protected void jdMethod_char()
  {
    Set localSet = this.U.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      a locala = (a)localEntry.getValue();
      FFSConnection localFFSConnection = locala.jdField_for;
      try
      {
        if (localFFSConnection != null)
        {
          localFFSConnection.close();
          localFFSConnection = null;
          System.out.println("+++ ConnectionPoolImpl.closeAllPooledObjects: Closing connection from the pool...");
        }
      }
      catch (Throwable localThrowable)
      {
        System.out.println("Failed to close a connection.");
        localThrowable.printStackTrace();
      }
    }
  }
  
  private void jdMethod_for(FFSConnection paramFFSConnection)
    throws FFSException
  {
    if (paramFFSConnection == null)
    {
      String str1 = "Invalid connection = null. This connection will not be configured.";
      System.out.println(str1);
      throw new FFSException(str1);
    }
    String str2;
    try
    {
      paramFFSConnection.setReadOnly(this.W._connInfo._dbConnInfo._connectionIsReadOnly);
    }
    catch (FFSException localFFSException1)
    {
      str2 = "Failed to set ReadOnlyFlag. This connection will not be configured: " + localFFSException1.toString();
      System.out.println(str2);
      throw localFFSException1;
    }
    try
    {
      paramFFSConnection.setAutoCommit(this.W._connInfo._dbConnInfo._connectionAutoCommit);
    }
    catch (FFSException localFFSException2)
    {
      str2 = "Failed to set connectionAutoCommit. This connection will not be configured: " + localFFSException2.toString();
      System.out.println(str2);
      throw localFFSException2;
    }
    if (this.W._connInfo._dbConnInfo._connectionCatalog != null) {
      try
      {
        paramFFSConnection.setCatalog(this.W._connInfo._dbConnInfo._connectionCatalog);
      }
      catch (FFSException localFFSException3)
      {
        str2 = "Failed to set connectionCatalog. This connection will not be configured: " + localFFSException3.toString();
        System.out.println(str2);
        throw localFFSException3;
      }
    }
    try
    {
      if (this.W._connInfo._dbConnInfo._connectionIsolationLevel != 0) {
        paramFFSConnection.setTransactionIsolation(this.W._connInfo._dbConnInfo._connectionIsolationLevel);
      }
    }
    catch (FFSException localFFSException4)
    {
      str2 = "Failed to set connectionIsolationLevel. This connection will not be configured: " + localFFSException4.toString();
      System.out.println(str2);
      throw localFFSException4;
    }
  }
  
  private FFSConnection jdMethod_new()
    throws FFSException
  {
    a locala = jdMethod_do();
    if (locala != null) {
      return locala.jdField_for;
    }
    return null;
  }
  
  private a jdMethod_do()
    throws FFSException
  {
    if (this.U.size() >= this.Q)
    {
      localObject = "+++ Failed to add connection to Connection Pool. Pool reached its maximum capacity: " + this.U.size();
      System.out.println((String)localObject);
      throw new FFSException((String)localObject);
    }
    Object localObject = ConnectionFactory.getNewConnection(this.W);
    if (localObject == null) {
      throw new FFSException("Failed to get new connection from the database");
    }
    if (this.W._connInfo._poolGuaranteeConnectionProperties) {
      jdMethod_for((FFSConnection)localObject);
    }
    if (!this.W._connInfo._dbConnInfo._connectionAutoCommit) {
      try
      {
        ((FFSConnection)localObject).setAutoCommit(this.W._connInfo._dbConnInfo._connectionAutoCommit);
      }
      catch (Throwable localThrowable)
      {
        String str = "Failed to set connectionAutoCommit. This connection will not be configured: ";
        System.out.println(str);
        localThrowable.printStackTrace();
        throw new FFSException(localThrowable, str);
      }
    }
    return jdMethod_do((FFSConnection)localObject);
  }
  
  private a jdMethod_goto()
    throws FFSException
  {
    if (this.U.size() < this.Q) {
      return jdMethod_if(jdMethod_do());
    }
    return null;
  }
  
  private FFSConnection jdMethod_case()
    throws FFSException
  {
    a locala = jdMethod_if(jdMethod_do());
    if (locala != null) {
      return locala.jdField_for;
    }
    return null;
  }
  
  public int getAvailabeConnNum()
    throws FFSException
  {
    return this.S.size();
  }
  
  public int getPoolCapacity()
    throws FFSException
  {
    return this.U.size();
  }
  
  public void checkRefreshPool()
  {
    while (this.V) {
      try
      {
        System.out.println("Refreshing connectionPool. Please wait for 200 ms.");
        wait(200L);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  private FFSConnection jdMethod_try()
    throws FFSException
  {
    a locala = jdMethod_for();
    if ((locala == null) && (this.U.size() < this.Q)) {
      locala = jdMethod_goto();
    }
    if (locala == null) {
      return null;
    }
    if (!jdMethod_new(locala.jdField_for)) {
      try
      {
        a(locala);
        locala = jdMethod_goto();
        if (locala == null) {
          return null;
        }
        if (!jdMethod_new(locala.jdField_for))
        {
          a(locala);
          String str1 = "Failed to get connection from connection pool.";
          System.out.println(str1);
          throw new FFSException(str1);
        }
      }
      catch (FFSException localFFSException)
      {
        String str2 = "Failed to replace a corrupted connection. This connection will not be added back to the connection pool";
        localFFSException.printStackTrace();
        throw new FFSException(str2);
      }
    }
    return locala.jdField_for;
  }
  
  private FFSConnection[] a(int paramInt)
    throws FFSException
  {
    FFSConnection[] arrayOfFFSConnection = new FFSConnection[paramInt];
    int i = this.S.size();
    int j;
    if (i >= paramInt) {
      for (j = 0; j < paramInt; j++) {
        arrayOfFFSConnection[j] = jdMethod_else();
      }
    }
    if (this.Q >= paramInt + i)
    {
      for (j = 0; j < i; j++) {
        arrayOfFFSConnection[j] = jdMethod_else();
      }
      for (j = i; j < paramInt; j++) {
        arrayOfFFSConnection[j] = jdMethod_case();
      }
    }
    String str1 = "Failed to get " + paramInt + " connections from connection pool: " + "pool maximum size reached.";
    System.out.println(str1);
    throw new FFSException(str1);
    for (int k = 0; k < arrayOfFFSConnection.length; k++) {
      if (!jdMethod_new(arrayOfFFSConnection[k])) {
        try
        {
          jdMethod_int(arrayOfFFSConnection[k]);
          arrayOfFFSConnection[k] = jdMethod_case();
          if (!jdMethod_new(arrayOfFFSConnection[k]))
          {
            jdMethod_int(arrayOfFFSConnection[k]);
            for (int m = 0; m < arrayOfFFSConnection.length; m++) {
              if (m != k) {
                jdMethod_try(arrayOfFFSConnection[m]);
              }
            }
            String str2 = "Failed to get connection from connection pool.";
            System.out.println(str2);
            throw new FFSException(str2);
          }
        }
        catch (FFSException localFFSException)
        {
          String str3 = "Failed to replace a corrupted connection. This connection will not be added back to the connection pool";
          localFFSException.printStackTrace();
          throw new FFSException(str3);
        }
      }
    }
    return arrayOfFFSConnection;
  }
  
  public int getWaitingThreads()
  {
    return this.P;
  }
  
  protected void jdMethod_byte()
    throws FFSException
  {
    if (this.O) {
      throw new FFSException("Pool is shuting down...");
    }
  }
  
  public int getTranxRetryNum()
  {
    return this.W == null ? 3 : this.W._connInfo._dbConnInfo._tranxRetry;
  }
  
  private boolean jdMethod_new(FFSConnection paramFFSConnection)
  {
    Object localObject1 = 0;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str = paramFFSConnection.getDatabaseType();
      if ((str == null) || (str.length() == 0))
      {
        localObject2 = paramFFSConnection.getConnection().getMetaData();
        str = ((DatabaseMetaData)localObject2).getDatabaseProductName().toUpperCase();
        if (str.indexOf("DB2") != -1) {
          str = "DB2:app";
        } else if (str.indexOf("ORACLE") != -1) {
          str = "ORACLE:thin";
        } else if (str.indexOf("MICROSOFT SQL") != -1) {
          str = "MSSQL:thin";
        } else if (str.indexOf("ASA") != -1) {
          str = "ASA";
        } else if (str.indexOf("ASE") != -1) {
          str = "ASE";
        }
      }
      localStatement = paramFFSConnection.getConnection().createStatement();
      if (localStatement == null) {
        throw new Exception("Couldn't create statement.");
      }
      if (str != null)
      {
        localObject2 = null;
        if (str.equalsIgnoreCase("MSSQL:thin"))
        {
          localObject2 = "select uid from sysusers";
        }
        else if ((str.equalsIgnoreCase("ORACLE:thin")) || (str.equalsIgnoreCase("ORACLE:oci8")))
        {
          localStatement.setQueryTimeout(1);
          localObject2 = "select * from dual";
        }
        else if (str.toUpperCase().startsWith("DB2"))
        {
          localStatement.setQueryTimeout(1);
          localObject2 = "select grantee from sysibm.sysdbauth";
        }
        else if ((str.equalsIgnoreCase("ASA")) || (str.equalsIgnoreCase("ASE")))
        {
          localObject2 = "select uid from sysusers";
        }
        else
        {
          boolean bool2 = true;
          return bool2;
        }
        localResultSet = localStatement.executeQuery((String)localObject2);
        if (localResultSet == null) {
          throw new Exception("Couldn't execute query.");
        }
        if (localResultSet.next()) {
          localObject1 = 1;
        }
        localResultSet.close();
        localStatement.close();
        localStatement = null;
      }
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (Throwable localThrowable)
    {
      System.out.println("ConnectionPool.isValidConnection: invalid connection found on:" + paramFFSConnection.getDatabaseType());
      localThrowable.printStackTrace();
      boolean bool1 = false;
      return bool1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localStatement != null) {
          localStatement.close();
        }
      }
      catch (SQLException localSQLException) {}
    }
  }
  
  private class a
  {
    static final int jdField_if = 0;
    static final int jdField_new = 1;
    static final int a = 2;
    FFSConnection jdField_for = null;
    Thread jdField_do = null;
    int jdField_int = 0;
    
    a(FFSConnection paramFFSConnection)
    {
      this.jdField_for = paramFFSConnection;
    }
    
    a(FFSConnection paramFFSConnection, Thread paramThread)
    {
      this.jdField_for = paramFFSConnection;
      this.jdField_do = paramThread;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.ConnectionPoolImpl
 * JD-Core Version:    0.7.0.1
 */