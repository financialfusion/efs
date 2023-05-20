package com.ffusion.util.db;

import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.sql.DataSource;

public class FFIConnectionPool
  implements DataSource, ConnectionDefines, IConnectionPool
{
  private static final String v = "com.sybase.jdbc2.jdbc.SybDriver";
  private static final String z = "com.sybase.jdbc3.jdbc.SybDriver";
  private static final String r = "oracle.jdbc.driver.OracleDriver";
  private static final String m = "COM.ibm.db2.jdbc.app.DB2Driver";
  private static final String k = "com.ibm.db2.jcc.DB2Driver";
  private static final String x = "COM.ibm.db2.jdbc.net.DB2Driver";
  private static final String u = "COM.ibm.db2.jdbc.app.DB2Driver";
  private static final String e = "com.jnetdirect.jsql.JSQLDriver";
  int y = 0;
  int n = 0;
  int h = 0;
  protected LinkedList mConnections = null;
  protected HashMap mAllConnections = null;
  String j;
  String i;
  String f;
  String b;
  String d;
  String o;
  String jdField_null;
  String jdField_void;
  String w;
  int jdField_long = 60;
  int l = 7200;
  int q = 600;
  int p = 0;
  int t = 1;
  boolean s = false;
  Thread g = null;
  boolean c = false;
  protected int notReturnedCount = 0;
  protected int returnedTwiceCount = 0;
  protected int invalidConnectionCount = 0;
  protected int waitConnectionCount = 0;
  protected int staleConnectionCount = 0;
  protected int mNewConnectionsCreated = 0;
  
  public DataSource getDataSource(Properties paramProperties)
    throws Exception
  {
    this.b = a(paramProperties, "ConnectionType", true);
    this.jdField_void = a(paramProperties, "DriverClass", false);
    this.jdField_null = a(paramProperties, "JDBCUrl", false);
    if ((this.jdField_null == null) || (this.jdField_null.length() == 0))
    {
      this.d = a(paramProperties, "DBName", true);
      boolean bool = true;
      i1 = 1;
      if ((this.b.equalsIgnoreCase("DB2:APP")) || (this.b.equalsIgnoreCase("DB2:UN2")))
      {
        bool = false;
        i1 = 0;
      }
      this.o = a(paramProperties, "Port", bool);
      this.f = a(paramProperties, "Server", i1);
    }
    this.j = a(paramProperties, "User", true);
    this.i = a(paramProperties, "Password", true);
    this.w = a(paramProperties, "License", false);
    this.n = a(paramProperties, "DefaultConnections", 1, 1);
    this.y = a(paramProperties, "MaxConnections", 1, this.n);
    this.jdField_long = a(paramProperties, "ConnectionTimeout", 60, 1);
    this.q = a(paramProperties, "DiagnosticsInterval", 600, 60);
    this.p = a(paramProperties, "ValidationInterval", 0, 0);
    this.t = a(paramProperties, "ValidationTimeout", 1, 1);
    this.l = a(paramProperties, "StaleTimeout", 7200, 60);
    String str = a(paramProperties, "ResizePool", false);
    if ((str != null) && (str.length() > 0)) {
      this.s = Boolean.valueOf(str).booleanValue();
    }
    if (this.mConnections != null)
    {
      releasePool();
      this.mConnections = null;
    }
    this.mConnections = new LinkedList();
    this.mAllConnections = new HashMap();
    for (int i1 = 0; i1 < this.n; i1++) {
      jdMethod_for(jdMethod_if());
    }
    this.g = new DiagnosticsThread();
    this.g.start();
    if (ConnectionPool.validationLevel > 0) {
      startConnectionValidation();
    }
    return this;
  }
  
  private String a(Properties paramProperties, String paramString, boolean paramBoolean)
    throws Exception
  {
    String str = paramProperties.getProperty(paramString);
    if (((str == null) || (str.length() == 0)) && (paramBoolean)) {
      throw new Exception("paramater " + paramString + " missing from database properties");
    }
    return str;
  }
  
  private int a(Properties paramProperties, String paramString, int paramInt1, int paramInt2)
  {
    int i1 = paramInt1;
    String str = null;
    try
    {
      str = a(paramProperties, paramString, false);
    }
    catch (Exception localException) {}
    if ((str != null) && (str.length() > 0)) {
      try
      {
        i1 = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    if (i1 < paramInt2) {
      i1 = paramInt2;
    }
    return i1;
  }
  
  protected void finalize()
    throws Throwable
  {
    releasePool();
  }
  
  private Connection jdMethod_if()
    throws Exception
  {
    Connection localConnection = null;
    String str1 = this.jdField_void;
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = jdMethod_do();
    }
    String str2 = this.jdField_null;
    if ((str2 == null) || (str2.length() == 0)) {
      str2 = DBUtil.getDBUrl(this.b, this.d, this.f, this.o);
    }
    try
    {
      Class.forName(str1).newInstance();
    }
    catch (Exception localException)
    {
      if (((localException instanceof InstantiationException)) || ((localException instanceof ClassNotFoundException)))
      {
        if (str1.equals("com.sybase.jdbc3.jdbc.SybDriver"))
        {
          str1 = "com.sybase.jdbc2.jdbc.SybDriver";
          Class.forName(str1).newInstance();
        }
      }
      else {
        throw localException;
      }
    }
    Object localObject;
    if (this.b.equalsIgnoreCase("MSSQL:THIN"))
    {
      localObject = new Properties();
      ((Properties)localObject).put("user", this.j);
      ((Properties)localObject).put("password", this.i);
      if ((this.w != null) && (this.w.length() > 0)) {
        ((Properties)localObject).put("license", this.w);
      }
      localConnection = DriverManager.getConnection(str2, (Properties)localObject);
    }
    else if ((this.b.equalsIgnoreCase("ASA")) || (this.b.equalsIgnoreCase("ASE")))
    {
      localObject = new Properties();
      ((Properties)localObject).put("user", this.j);
      ((Properties)localObject).put("password", this.i);
      localConnection = DriverManager.getConnection(str2, (Properties)localObject);
    }
    else
    {
      localConnection = DriverManager.getConnection(str2, this.j, this.i);
    }
    if (localConnection == null)
    {
      localObject = "FFIConnectionPool.createConnection: couldn't create a connection: " + str2;
      DebugLog.log(Level.WARNING, (String)localObject);
      throw new Exception((String)localObject);
    }
    DebugLog.log(Level.INFO, "New DB connection created for:" + str2);
    this.h += 1;
    this.mNewConnectionsCreated += 1;
    jdMethod_try(localConnection);
    setConnectionInUse(localConnection, true);
    return localConnection;
  }
  
  public Connection getConnection()
    throws SQLException
  {
    String str = "FFIConnectionPool.getConnection";
    Connection localConnection = null;
    long l1 = System.currentTimeMillis() + this.jdField_long * 1000;
    while (System.currentTimeMillis() < l1)
    {
      localConnection = a(true);
      if (localConnection == null)
      {
        int i1 = 0;
        synchronized (this.mConnections)
        {
          if (this.h < this.y)
          {
            i1 = 1;
            this.h += 1;
          }
        }
        if (i1 != 0) {
          try
          {
            localConnection = jdMethod_if();
          }
          catch (Exception localException)
          {
            throw new SQLException(localException.getMessage());
          }
          finally
          {
            this.h -= 1;
          }
        }
        if (localConnection == null)
        {
          this.waitConnectionCount += 1;
          DebugLog.log(Level.INFO, str + ": waiting for a connection. MaxConnections is:" + this.y);
          while (System.currentTimeMillis() < l1)
          {
            try
            {
              Thread.currentThread();
              Thread.sleep(1000L);
            }
            catch (InterruptedException localInterruptedException)
            {
              throw new SQLException(localInterruptedException.getMessage());
            }
            localConnection = a(true);
            if (localConnection != null) {
              break;
            }
          }
        }
      }
      localConnection = jdMethod_new(localConnection);
      if (localConnection != null) {
        break;
      }
    }
    if (localConnection == null)
    {
      DebugLog.log(Level.WARNING, str + ":No Database connections available");
      throw new SQLException("No Database Connections available - try again later");
    }
    return localConnection;
  }
  
  public boolean returnConnection(Connection paramConnection)
  {
    if ((ConnectionPool.validationLevel & 0x2) != 0) {
      paramConnection = jdMethod_int(paramConnection);
    }
    if (paramConnection != null)
    {
      jdMethod_for(paramConnection);
      return true;
    }
    return false;
  }
  
  public void removeInvalidConnection(Connection paramConnection)
  {
    if (paramConnection != null)
    {
      jdMethod_do(paramConnection);
      this.invalidConnectionCount += 1;
      DebugLog.log(Level.WARNING, "FFIConnectionPool: Invalid connection found and removed");
    }
  }
  
  private void jdMethod_byte(Connection paramConnection)
  {
    if (paramConnection != null)
    {
      jdMethod_do(paramConnection);
      this.staleConnectionCount += 1;
      DebugLog.log(Level.WARNING, "FFIConnectionPool: Stale connection found and removed, unused for more than " + this.l + " seconds");
    }
  }
  
  private void jdMethod_do(Connection paramConnection)
  {
    if (paramConnection != null)
    {
      try
      {
        paramConnection.close();
      }
      catch (SQLException localSQLException) {}
      this.h -= 1;
      synchronized (this.mAllConnections)
      {
        this.mAllConnections.remove(String.valueOf(paramConnection.hashCode()));
      }
    }
  }
  
  public boolean releasePool()
  {
    if (this.mConnections != null)
    {
      while (!this.mConnections.isEmpty()) {
        try
        {
          Connection localConnection = a(false);
          if (localConnection != null) {
            localConnection.close();
          }
        }
        catch (SQLException localSQLException) {}
      }
      this.mConnections = null;
      synchronized (this.mAllConnections)
      {
        this.mAllConnections.clear();
      }
      this.h = 0;
    }
    if (this.g != null)
    {
      this.c = true;
      this.g.interrupt();
    }
    return true;
  }
  
  private void jdMethod_for(Connection paramConnection)
  {
    if (this.mConnections == null) {
      return;
    }
    if (paramConnection != null) {
      synchronized (this.mConnections)
      {
        if (clearConnectionInUse(paramConnection) == true) {
          this.mConnections.addLast(paramConnection);
        }
      }
    }
  }
  
  private Connection a(boolean paramBoolean)
  {
    Connection localConnection = null;
    if (this.mConnections != null) {
      synchronized (this.mConnections)
      {
        if (!this.mConnections.isEmpty())
        {
          localConnection = (Connection)this.mConnections.removeFirst();
          if (localConnection != null)
          {
            FFIConnection localFFIConnection = (FFIConnection)this.mAllConnections.get(String.valueOf(localConnection.hashCode()));
            if (localFFIConnection == null)
            {
              DebugLog.log(Level.WARNING, "FFIConnectionPool.dequeue: Connection missing from master connection list");
              jdMethod_try(localConnection);
            }
            setConnectionInUse(localConnection, paramBoolean);
          }
        }
      }
    }
    return localConnection;
  }
  
  public int getLoginTimeout()
    throws SQLException
  {
    return this.jdField_long;
  }
  
  public void setLoginTimeout(int paramInt)
    throws SQLException
  {
    this.jdField_long = paramInt;
  }
  
  public Connection getConnection(String paramString1, String paramString2)
    throws SQLException
  {
    return null;
  }
  
  public PrintWriter getLogWriter()
    throws SQLException
  {
    return null;
  }
  
  public void setLogWriter(PrintWriter paramPrintWriter)
    throws SQLException
  {}
  
  public void setMaxConnections(int paramInt)
  {
    this.y = paramInt;
  }
  
  private String jdMethod_do()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.b.equalsIgnoreCase("DB2:APP")) || (this.b.equalsIgnoreCase("DB2:AS390"))) {
      localStringBuffer.append("COM.ibm.db2.jdbc.app.DB2Driver");
    } else if (this.b.equalsIgnoreCase("DB2:UN2")) {
      localStringBuffer.append("com.ibm.db2.jcc.DB2Driver");
    } else if (this.b.equalsIgnoreCase("DB2:NET")) {
      localStringBuffer.append("COM.ibm.db2.jdbc.net.DB2Driver");
    } else if (this.b.equalsIgnoreCase("ORACLE:THIN")) {
      localStringBuffer.append("oracle.jdbc.driver.OracleDriver");
    } else if (this.b.equalsIgnoreCase("ORACLE:OCI8")) {
      localStringBuffer.append("oracle.jdbc.driver.OracleDriver");
    } else if (this.b.equalsIgnoreCase("ASA")) {
      localStringBuffer.append("com.sybase.jdbc2.jdbc.SybDriver");
    } else if (this.b.equalsIgnoreCase("MSSQL:THIN")) {
      localStringBuffer.append("com.jnetdirect.jsql.JSQLDriver");
    } else if (this.b.equalsIgnoreCase("ASE")) {
      localStringBuffer.append("com.sybase.jdbc3.jdbc.SybDriver");
    } else {
      localStringBuffer.append("com.sybase.jdbc2.jdbc.SybDriver");
    }
    return localStringBuffer.toString();
  }
  
  public void startConnectionValidation()
  {
    if (ConnectionPool.validationLevel > 0)
    {
      this.notReturnedCount = 0;
      this.returnedTwiceCount = 0;
      this.invalidConnectionCount = 0;
      this.staleConnectionCount = 0;
      this.waitConnectionCount = 0;
    }
  }
  
  public void stopConnectionValidation() {}
  
  private void jdMethod_try(Connection paramConnection)
  {
    FFIConnection localFFIConnection = new FFIConnection(paramConnection);
    synchronized (this.mAllConnections)
    {
      this.mAllConnections.put(String.valueOf(paramConnection.hashCode()), localFFIConnection);
    }
  }
  
  protected void setConnectionInUse(Connection paramConnection, boolean paramBoolean)
  {
    FFIConnection localFFIConnection = (FFIConnection)this.mAllConnections.get(String.valueOf(paramConnection.hashCode()));
    if (localFFIConnection == null)
    {
      DebugLog.log(Level.WARNING, "FFIConnectionPool.setConnectionInUse: Connection missing from master connection list");
      jdMethod_try(paramConnection);
    }
    else
    {
      localFFIConnection.jdField_int = true;
      if (paramBoolean) {
        localFFIConnection.jdField_new = System.currentTimeMillis();
      }
      if ((ConnectionPool.validationLevel & 0x4) != 0)
      {
        Throwable localThrowable = new Throwable();
        StringWriter localStringWriter = new StringWriter();
        localThrowable.printStackTrace(new PrintWriter(localStringWriter));
        localFFIConnection.a = localStringWriter.toString();
      }
    }
  }
  
  protected boolean clearConnectionInUse(Connection paramConnection)
  {
    FFIConnection localFFIConnection = (FFIConnection)this.mAllConnections.get(String.valueOf(paramConnection.hashCode()));
    if (localFFIConnection == null)
    {
      DebugLog.log(Level.WARNING, "FFIConnectionPool.clearConnectionInUse: Connection missing from master connection list");
      jdMethod_try(paramConnection);
    }
    else
    {
      if (((ConnectionPool.validationLevel & 0x1) != 0) && (!localFFIConnection.jdField_int))
      {
        DebugLog.log(Level.WARNING, "FFIConnectionPool.clearConnectionInUse: DEBUG: An attempt was made to return a connection to the pool twice");
        this.returnedTwiceCount += 1;
        try
        {
          throw new Exception("FFIConnectionPool.clearConnectionInUse");
        }
        catch (Exception localException)
        {
          localException.printStackTrace(System.err);
          return false;
        }
      }
      localFFIConnection.jdField_int = false;
      localFFIConnection.jdField_for = false;
      localFFIConnection.jdField_new = System.currentTimeMillis();
    }
    return true;
  }
  
  public void getDiagnostics(StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("Max connections setting: " + this.y + "<br>");
    paramStringBuffer.append("Total connections allocated: " + this.h + "<br>");
    paramStringBuffer.append("Currently available connections: " + this.mConnections.size() + "<br>");
    paramStringBuffer.append("<b>New connections created: " + this.mNewConnectionsCreated + "<br></b>");
    paramStringBuffer.append("<b>Had to wait for a connection: " + this.waitConnectionCount + " times<br></b>");
    paramStringBuffer.append("<b>Invalid connections found: " + this.invalidConnectionCount + "<br></b>");
    paramStringBuffer.append("<b>Stale connections found: " + this.staleConnectionCount + "<br></b>");
    paramStringBuffer.append("<b>Connections returned twice: " + this.returnedTwiceCount + "<br></b>");
    paramStringBuffer.append("<b>Connections requested, not returned, stack trace thrown: " + this.notReturnedCount + "<br></b>");
  }
  
  public void connectionCheck()
  {
    if ((this.s == true) && (this.mNewConnectionsCreated == 0))
    {
      if (this.h > this.n)
      {
        a();
        if (this.h > this.n) {
          a();
        }
      }
    }
    else {
      this.mNewConnectionsCreated = 0;
    }
    long l1 = System.currentTimeMillis();
    Object localObject1;
    for (;;)
    {
      Connection localConnection = a(false);
      if (localConnection == null) {
        break;
      }
      localObject1 = (FFIConnection)this.mAllConnections.get(String.valueOf(localConnection.hashCode()));
      if (l1 - ((FFIConnection)localObject1).jdField_new > this.l * 1000)
      {
        jdMethod_byte(localConnection);
      }
      else
      {
        jdMethod_for(localConnection);
        break;
      }
    }
    if ((ConnectionPool.validationLevel & 0x4) != 0) {
      synchronized (this.mAllConnections)
      {
        localObject1 = this.mAllConnections.keySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          String str = (String)((Iterator)localObject1).next();
          FFIConnection localFFIConnection = (FFIConnection)this.mAllConnections.get(str);
          if ((localFFIConnection.jdField_int == true) && (!localFFIConnection.jdField_for) && (System.currentTimeMillis() - localFFIConnection.jdField_new > 60000L))
          {
            DebugLog.log(Level.WARNING, "FFIConnectionPool.noReturnCheck: DEBUG: A connection was requested and never returned");
            DebugLog.log(Level.WARNING, localFFIConnection.a);
            this.notReturnedCount += 1;
            localFFIConnection.jdField_for = true;
          }
        }
      }
    }
  }
  
  private boolean a()
  {
    try
    {
      Connection localConnection = a(false);
      if (localConnection != null)
      {
        localConnection.close();
        this.h -= 1;
        synchronized (this.mAllConnections)
        {
          this.mAllConnections.remove(String.valueOf(localConnection.hashCode()));
        }
      }
    }
    catch (SQLException localSQLException) {}
    return true;
  }
  
  private Connection jdMethod_new(Connection paramConnection)
  {
    if (paramConnection != null)
    {
      FFIConnection localFFIConnection = (FFIConnection)this.mAllConnections.get(String.valueOf(paramConnection.hashCode()));
      if (localFFIConnection == null)
      {
        DebugLog.log(Level.WARNING, "FFIConnectionPool.validateConnection: Connection missing from master connection list");
        jdMethod_try(paramConnection);
      }
      if (System.currentTimeMillis() - localFFIConnection.jdField_do > this.p * 1000) {
        if (!jdMethod_case(paramConnection))
        {
          removeInvalidConnection(paramConnection);
          paramConnection = null;
        }
        else
        {
          localFFIConnection.jdField_do = System.currentTimeMillis();
        }
      }
    }
    return paramConnection;
  }
  
  private boolean jdMethod_case(Connection paramConnection)
  {
    Object localObject1 = 0;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if ((this.b == null) || (this.b.length() == 0))
      {
        localObject2 = paramConnection.getMetaData();
        this.b = ((DatabaseMetaData)localObject2).getDatabaseProductName().toUpperCase();
        if (this.b.indexOf("DB2") != -1) {
          this.b = "DB2:UN2";
        } else if (this.b.indexOf("ORACLE") != -1) {
          this.b = "ORACLE:THIN";
        } else if (this.b.indexOf("MICROSOFT SQL") != -1) {
          this.b = "MSSQL:THIN";
        } else if (this.b.indexOf("SYBASE") != -1) {
          this.b = "ASE";
        }
      }
      if (this.b != null)
      {
        localObject2 = null;
        if (this.b.equalsIgnoreCase("MSSQL:THIN"))
        {
          localObject2 = "select uid from sysusers";
        }
        else if ((this.b.equalsIgnoreCase("ORACLE:THIN")) || (this.b.equalsIgnoreCase("ORACLE:OCI8")))
        {
          localObject2 = "select * from dual";
        }
        else if ((this.b.equalsIgnoreCase("DB2:APP")) || (this.b.equalsIgnoreCase("DB2:NET")) || (this.b.equalsIgnoreCase("DB2:AS390")) || (this.b.equalsIgnoreCase("DB2:UN2")))
        {
          localObject2 = "select grantee from sysibm.sysdbauth";
        }
        else if ((this.b.equalsIgnoreCase("ASA")) || (this.b.equalsIgnoreCase("ASE")))
        {
          localObject2 = "select count(*) from systypes";
        }
        else
        {
          boolean bool1 = true;
          return bool1;
        }
        localStatement = paramConnection.createStatement();
        localStatement.setQueryTimeout(this.t);
        localResultSet = localStatement.executeQuery((String)localObject2);
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
    catch (Exception localException)
    {
      String str = this.jdField_null;
      if ((str == null) || (str.length() == 0)) {
        str = DBUtil.getDBUrl(this.b, this.d, this.f, this.o);
      }
      DebugLog.log(Level.WARNING, "FFIConnectionPool.isValidConnection: invalid connection found on:" + str + ": " + localException.getMessage());
      boolean bool2 = false;
      return bool2;
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
  
  private Connection jdMethod_int(Connection paramConnection)
  {
    try
    {
      if (!jdMethod_case(paramConnection)) {
        throw new Exception("Invalid connection found");
      }
      if ((this.b.equalsIgnoreCase("ORACLE:THIN")) || (this.b.equalsIgnoreCase("ORACLE:OCI8")))
      {
        Statement localStatement = null;
        localObject1 = null;
        try
        {
          String str = "select a.value, b.name from v$mystat a, v$statname b where a.statistic# = b.statistic# and b.name = 'opened cursors current'";
          localStatement = paramConnection.createStatement();
          localObject1 = localStatement.executeQuery(str);
          if ((((ResultSet)localObject1).next()) && (((ResultSet)localObject1).getLong(1) > 1L)) {
            throw new Exception("A connection with an open result set was returned");
          }
        }
        finally
        {
          if (localObject1 != null) {
            ((ResultSet)localObject1).close();
          }
          if (localStatement != null) {
            localStatement.close();
          }
        }
      }
      else
      {
        paramConnection.setTransactionIsolation(2);
        paramConnection.setAutoCommit(true);
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = this.jdField_null;
      if ((localObject1 == null) || (((String)localObject1).length() == 0)) {
        localObject1 = DBUtil.getDBUrl(this.b, this.d, this.f, this.o);
      }
      DebugLog.log(Level.WARNING, "FFIConnectionPool.validateReturnedConnection: DEBUG: An invalid connection was returned:" + (String)localObject1 + ": " + localException.getMessage());
      localException.printStackTrace(System.err);
      if (paramConnection != null)
      {
        removeInvalidConnection(paramConnection);
        paramConnection = null;
      }
    }
    return paramConnection;
  }
  
  public class DiagnosticsThread
    extends Thread
  {
    public DiagnosticsThread() {}
    
    public void run()
    {
      long l = FFIConnectionPool.this.q * 1000;
      for (;;)
      {
        try
        {
          sleep(l);
        }
        catch (InterruptedException localInterruptedException) {}catch (Throwable localThrowable) {}
        if (FFIConnectionPool.this.c == true)
        {
          FFIConnectionPool.this.g = null;
          break;
        }
        FFIConnectionPool.this.connectionCheck();
        if (ConnectionPool.validationLevel > 0)
        {
          StringBuffer localStringBuffer = new StringBuffer();
          String str = FFIConnectionPool.this.jdField_null;
          if ((str == null) || (str.length() == 0)) {
            str = DBUtil.getDBUrl(FFIConnectionPool.this.b, FFIConnectionPool.this.d, FFIConnectionPool.this.f, FFIConnectionPool.this.o);
          }
          localStringBuffer.append("Diagnostics for " + str + "\n");
          FFIConnectionPool.this.getDiagnostics(localStringBuffer);
          DebugLog.log(Level.SEVERE, localStringBuffer.toString().replaceAll("<br>", "\n"));
        }
      }
    }
  }
  
  public class FFIConnection
  {
    Connection jdField_if;
    long jdField_try = 0L;
    long jdField_new = 0L;
    long jdField_do = 0L;
    String a;
    boolean jdField_int = false;
    boolean jdField_for = false;
    
    public FFIConnection(Connection paramConnection)
    {
      this.jdField_if = paramConnection;
      this.jdField_try = System.currentTimeMillis();
      this.jdField_new = this.jdField_try;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.FFIConnectionPool
 * JD-Core Version:    0.7.0.1
 */