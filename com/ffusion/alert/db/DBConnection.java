package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

public class DBConnection
  implements IAEErrConstants
{
  public static final String fi = "com.sybase.jdbc.SybDriver";
  public static final String fw = "com.sybase.jdbc2.jdbc.SybDriver";
  public static final String fg = "com.sybase.jdbc3.jdbc.SybDriver";
  public static final String fj = "oracle.jdbc.driver.OracleDriver";
  public static final String fs = "COM.ibm.db2.jdbc.app.DB2Driver";
  public static final String fu = "COM.ibm.db2.jdbc.net.DB2Driver";
  public static final String ft = "com.jnetdirect.jsql.JSQLDriver";
  public static final String fr = "com.ibm.db2.jcc.DB2Driver";
  static final int fm = 1073741824;
  static final String fl = "JZ006";
  static final String fD = "JZ0F2";
  static final int fh = 3;
  private static final String fx = "set chained off";
  private static final String fB = "set chained on";
  private static OracleClassWrapperHack fn = null;
  private Connection fA;
  private DatabaseMetaData fC;
  private String fq;
  private String fz;
  private boolean fv;
  private boolean fp = true;
  private AEDBParams fo;
  private Properties fy;
  private a fk = new a(null);
  
  private DBConnection(AEDBParams paramAEDBParams)
  {
    this.fo = paramAEDBParams;
    this.fy = new Properties();
    this.fy.put("user", this.fo.getUser());
    this.fy.put("password", this.fo.getPassword());
  }
  
  public static DBConnection jdMethod_new(AEDBParams paramAEDBParams)
    throws AEException
  {
    DBConnection localDBConnection = null;
    switch (paramAEDBParams.getConnectionType())
    {
    case 1: 
      localDBConnection = jdMethod_try(paramAEDBParams);
      break;
    case 2: 
      if (paramAEDBParams.isHA()) {
        localDBConnection = jdMethod_do(paramAEDBParams);
      } else {
        localDBConnection = jdMethod_try(paramAEDBParams);
      }
      break;
    case 5: 
      localDBConnection = jdMethod_byte(paramAEDBParams);
      break;
    case 3: 
    case 6: 
      localDBConnection = jdMethod_int(paramAEDBParams);
      break;
    case 4: 
      localDBConnection = jdMethod_for(paramAEDBParams);
      break;
    default: 
      jdMethod_if(2, "ERR_UNKNOWN_CONNECTION_TYPE");
    }
    return localDBConnection;
  }
  
  private static DBConnection jdMethod_do(AEDBParams paramAEDBParams)
    throws AEException
  {
    DBConnection localDBConnection = new DBConnection(paramAEDBParams);
    localDBConnection.fq = ("jdbc:sybase:jndi:" + paramAEDBParams.getJNDIURL() + paramAEDBParams.getJNDICtx());
    localDBConnection.fy.put("REQUEST_HA_SESSION", "true");
    localDBConnection.fy.put("java.naming.factory.initial", paramAEDBParams.getCtxFactory());
    localDBConnection.fy.put("java.naming.provider.url", paramAEDBParams.getJNDIURL());
    localDBConnection.fz = "com.sybase.jdbc2.jdbc.SybDriver";
    return localDBConnection;
  }
  
  private static DBConnection jdMethod_byte(AEDBParams paramAEDBParams)
    throws AEException
  {
    DBConnection localDBConnection = new DBConnection(paramAEDBParams);
    if (paramAEDBParams.getJDBCURL() != null)
    {
      localDBConnection.fq = paramAEDBParams.getJDBCURL();
    }
    else
    {
      String str = paramAEDBParams.getDBName();
      if ((str != null) && (str.length() > 0))
      {
        localDBConnection.fq = ("jdbc:JSQLConnect://" + paramAEDBParams.getMachine() + ":" + paramAEDBParams.getPort() + "/database=" + str);
        localDBConnection.fy.put("SERVICENAME", str);
      }
    }
    localDBConnection.fz = "com.jnetdirect.jsql.JSQLDriver";
    return localDBConnection;
  }
  
  private static DBConnection jdMethod_try(AEDBParams paramAEDBParams)
    throws AEException
  {
    DBConnection localDBConnection = new DBConnection(paramAEDBParams);
    if (paramAEDBParams.getJDBCURL() != null)
    {
      localDBConnection.fq = paramAEDBParams.getJDBCURL();
    }
    else
    {
      String str = paramAEDBParams.getDBName();
      if ((str != null) && (str.length() > 0))
      {
        localDBConnection.fq = ("jdbc:sybase:Tds:" + paramAEDBParams.getMachine() + ":" + paramAEDBParams.getPort() + "/" + str);
        localDBConnection.fy.put("SERVICENAME", str);
      }
      else
      {
        localDBConnection.fq = ("jdbc:sybase:Tds:" + paramAEDBParams.getMachine() + ":" + paramAEDBParams.getPort());
      }
    }
    localDBConnection.fz = "com.sybase.jdbc3.jdbc.SybDriver";
    return localDBConnection;
  }
  
  private static DBConnection jdMethod_int(AEDBParams paramAEDBParams)
    throws AEException
  {
    DBConnection localDBConnection = new DBConnection(paramAEDBParams);
    int i = paramAEDBParams.getConnectionType() == 6 ? 1 : 0;
    if (paramAEDBParams.getJDBCURL() != null)
    {
      localDBConnection.fq = paramAEDBParams.getJDBCURL();
      if (i != 0) {
        localDBConnection.fz = "com.ibm.db2.jcc.DB2Driver";
      } else if (paramAEDBParams.isNativeDriver()) {
        localDBConnection.fz = "COM.ibm.db2.jdbc.app.DB2Driver";
      } else {
        localDBConnection.fz = "COM.ibm.db2.jdbc.net.DB2Driver";
      }
    }
    else if ((i != 0) || (paramAEDBParams.isNativeDriver()))
    {
      localDBConnection.fq = ("jdbc:db2:" + paramAEDBParams.getDBName());
      localDBConnection.fz = (i != 0 ? "com.ibm.db2.jcc.DB2Driver" : "COM.ibm.db2.jdbc.app.DB2Driver");
    }
    else
    {
      localDBConnection.fq = ("jdbc:db2://" + paramAEDBParams.getMachine() + ":" + paramAEDBParams.getPort() + "/" + paramAEDBParams.getDBName());
      localDBConnection.fz = "COM.ibm.db2.jdbc.net.DB2Driver";
    }
    return localDBConnection;
  }
  
  private static DBConnection jdMethod_for(AEDBParams paramAEDBParams)
    throws AEException
  {
    DBConnection localDBConnection = new DBConnection(paramAEDBParams);
    if (paramAEDBParams.getJDBCURL() != null) {
      localDBConnection.fq = paramAEDBParams.getJDBCURL();
    } else if (paramAEDBParams.isNativeDriver()) {
      localDBConnection.fq = ("jdbc:oracle:oci8:@(description=(address=(host=" + paramAEDBParams.getMachine() + ")(protocol=tcp)(port=" + paramAEDBParams.getPort() + "))(connect_data=(sid=" + paramAEDBParams.getDBName() + ")))");
    } else {
      localDBConnection.fq = ("jdbc:oracle:thin:@" + paramAEDBParams.getMachine() + ":" + paramAEDBParams.getPort() + ":" + paramAEDBParams.getDBName());
    }
    localDBConnection.fz = "oracle.jdbc.driver.OracleDriver";
    return localDBConnection;
  }
  
  public synchronized void aZ()
    throws AEException
  {
    aV();
    try
    {
      try
      {
        Class.forName(this.fz);
      }
      catch (Exception localException1)
      {
        if (this.fz == "com.sybase.jdbc3.jdbc.SybDriver")
        {
          this.fz = "com.sybase.jdbc2.jdbc.SybDriver";
          try
          {
            Class.forName(this.fz);
          }
          catch (Exception localException3)
          {
            if (this.fz == "com.sybase.jdbc2.jdbc.SybDriver")
            {
              this.fz = "com.sybase.jdbc.SybDriver";
              Class.forName(this.fz);
            }
            else
            {
              throw localException1;
            }
          }
        }
      }
    }
    catch (Exception localException2)
    {
      jdMethod_if(3, "ERR_JDBC_DRIVER_NOT_FOUND", this.fz, localException2);
    }
    try
    {
      this.fA = DriverManager.getConnection(this.fq, this.fy);
      this.fv = true;
      this.fC = this.fA.getMetaData();
      if (this.fC.supportsTransactionIsolationLevel(2)) {
        this.fA.setTransactionIsolation(2);
      }
      if ((this.fo.getConnectionType() == 1) || (this.fo.getConnectionType() == 2))
      {
        Statement localStatement = this.fA.createStatement();
        try
        {
          localStatement.executeUpdate("set textsize 1073741824");
        }
        finally
        {
          localStatement.close();
        }
      }
    }
    catch (SQLException localSQLException)
    {
      aV();
      jdMethod_if(4, "ERR_COULD_NOT_CONNECT_TO_DB", this.fz, this.fq, localSQLException);
    }
  }
  
  public synchronized void aV()
  {
    if (this.fv) {
      try
      {
        this.fv = false;
        this.fp = true;
        this.fk.a();
        this.fA.close();
      }
      catch (SQLException localSQLException) {}
    }
  }
  
  public synchronized boolean jdMethod_try(boolean paramBoolean)
    throws SQLException
  {
    if (this.fv)
    {
      if (this.fo.getConnectionType() == 2) {
        K(paramBoolean ? "set chained off" : "set chained on");
      } else {
        this.fA.setAutoCommit(paramBoolean);
      }
      this.fp = paramBoolean;
      return true;
    }
    return false;
  }
  
  public synchronized boolean aT()
  {
    try
    {
      Object localObject1;
      if (this.fo.getConnectionType() == 4)
      {
        localObject1 = I("select 1 from dual");
        ((DBResultSet)localObject1).jdMethod_try();
        ((DBResultSet)localObject1).jdMethod_for();
        return true;
      }
      if ((this.fo.getConnectionType() == 3) || (this.fo.getConnectionType() == 6))
      {
        localObject1 = null;
        ResultSet localResultSet = null;
        try
        {
          localObject1 = this.fA.createStatement();
          ((Statement)localObject1).setQueryTimeout(1);
          localResultSet = ((Statement)localObject1).executeQuery("select grantee from sysibm.sysdbauth");
          localResultSet.next();
          localResultSet.close();
          boolean bool2 = true;
          return bool2;
        }
        finally
        {
          if (localResultSet != null) {
            localResultSet.close();
          }
          if (localObject1 != null) {
            ((Statement)localObject1).close();
          }
        }
      }
      boolean bool1 = this.fp;
      return (jdMethod_try(!bool1)) && (jdMethod_try(bool1));
    }
    catch (Exception localException) {}
    return false;
  }
  
  public synchronized boolean aU()
  {
    return this.fp;
  }
  
  public synchronized void a0()
    throws SQLException
  {
    if (this.fv) {
      this.fA.commit();
    }
  }
  
  public synchronized void a1()
    throws SQLException
  {
    if (this.fv) {
      this.fA.rollback();
    }
  }
  
  public synchronized int K(String paramString)
    throws SQLException
  {
    return jdMethod_if(paramString, null);
  }
  
  public synchronized int jdMethod_if(String paramString, Object[] paramArrayOfObject)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = this.fk.a(paramString);
    int i = 0;
    for (;;)
    {
      try
      {
        if (localPreparedStatement == null)
        {
          String str = DBSqlUtils.a(paramString, this.fo.getConnectionType());
          if (str.trim().length() > 0)
          {
            localPreparedStatement = this.fA.prepareStatement(str);
            this.fk.a(paramString, localPreparedStatement);
          }
        }
        if (localPreparedStatement == null) {
          return 0;
        }
        DBSqlUtils.a(localPreparedStatement, paramArrayOfObject, this.fo);
        return localPreparedStatement.executeUpdate();
      }
      catch (SQLException localSQLException)
      {
        if ((!this.fo.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
          throw localSQLException;
        }
        if (i >= 3) {
          throw localSQLException;
        }
        i++;
      }
    }
  }
  
  public synchronized int J(String paramString)
    throws SQLException
  {
    Statement localStatement = null;
    for (int i = 0;; i++) {
      try
      {
        String str = DBSqlUtils.a(paramString, this.fo.getConnectionType());
        if (str.trim().length() > 0) {
          localStatement = this.fA.createStatement();
        }
        if (localStatement == null)
        {
          j = 0;
          return j;
        }
        int j = localStatement.executeUpdate(str);
        return j;
      }
      catch (SQLException localSQLException1)
      {
        if ((!this.fo.isHA()) || ((!localSQLException1.getSQLState().equals("JZ0F2")) && (!localSQLException1.getSQLState().equals("JZ006")))) {
          throw localSQLException1;
        }
        if (i >= 3) {
          throw localSQLException1;
        }
      }
      finally
      {
        if (localStatement != null)
        {
          try
          {
            localStatement.close();
          }
          catch (SQLException localSQLException2)
          {
            localSQLException2.printStackTrace();
          }
          localStatement = null;
        }
      }
    }
  }
  
  public synchronized DBResultSet I(String paramString)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = this.fk.a(paramString);
    int i = 0;
    for (;;)
    {
      try
      {
        if (localPreparedStatement == null)
        {
          String str = DBSqlUtils.a(paramString, this.fo.getConnectionType());
          localPreparedStatement = this.fA.prepareStatement(str);
          this.fk.a(paramString, localPreparedStatement);
        }
        return new DBResultSet(localPreparedStatement, this.fo);
      }
      catch (SQLException localSQLException)
      {
        if ((!this.fo.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
          throw localSQLException;
        }
        if (i >= 3) {
          throw localSQLException;
        }
        i++;
      }
    }
  }
  
  public synchronized int a(String paramString, Object[] paramArrayOfObject)
    throws SQLException
  {
    CallableStatement localCallableStatement = (CallableStatement)this.fk.a(paramString);
    int i = 0;
    for (;;)
    {
      try
      {
        if (localCallableStatement == null)
        {
          String str = DBSqlUtils.a(paramString, this.fo.getConnectionType());
          localCallableStatement = this.fA.prepareCall(str);
          this.fk.a(paramString, localCallableStatement);
        }
        DBSqlUtils.a(localCallableStatement, paramArrayOfObject, this.fo);
        localCallableStatement.registerOutParameter(paramArrayOfObject.length + 1, 4);
        localCallableStatement.execute();
        return localCallableStatement.getInt(paramArrayOfObject.length + 1);
      }
      catch (SQLException localSQLException)
      {
        if ((!this.fo.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
          throw localSQLException;
        }
        if (i >= 3) {
          throw localSQLException;
        }
        i++;
      }
    }
  }
  
  public synchronized void a(String paramString, int paramInt, Object[] paramArrayOfObject)
    throws SQLException, IOException
  {
    if (fn == null) {
      fn = new OracleClassWrapperHack();
    }
    fn.a(this, paramString, paramInt, paramArrayOfObject);
  }
  
  public final String aX()
  {
    return this.fz;
  }
  
  public final boolean aY()
  {
    return this.fv;
  }
  
  public final AEDBParams aS()
  {
    return this.fo;
  }
  
  public final DatabaseMetaData aW()
  {
    return this.fC;
  }
  
  private static void jdMethod_if(int paramInt, String paramString)
    throws AEException
  {
    throw new AEException(paramInt, AERepository.H(paramString));
  }
  
  private static void jdMethod_if(int paramInt, String paramString, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, AERepository.H(paramString), paramThrowable);
  }
  
  private static void jdMethod_if(int paramInt, String paramString1, String paramString2)
    throws AEException
  {
    throw new AEException(paramInt, AERepository.jdMethod_try(paramString1, paramString2));
  }
  
  private static void jdMethod_if(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, AERepository.jdMethod_try(paramString1, paramString2), paramThrowable);
  }
  
  private static void a(int paramInt, String paramString1, String paramString2, String paramString3)
    throws AEException
  {
    throw new AEException(paramInt, AERepository.a(paramString1, paramString2, paramString3));
  }
  
  private static void jdMethod_if(int paramInt, String paramString1, String paramString2, String paramString3, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, AERepository.a(paramString1, paramString2, paramString3), paramThrowable);
  }
  
  private static class a
  {
    private static final int jdField_int = 50;
    private static final int a = 89;
    private final HashMap jdField_for = new HashMap(89);
    private final String[] jdField_do = new String[50];
    private int jdField_if = 0;
    
    private a() {}
    
    final PreparedStatement a(String paramString)
    {
      return (PreparedStatement)this.jdField_for.get(paramString);
    }
    
    final void a(String paramString, PreparedStatement paramPreparedStatement)
    {
      if (this.jdField_for.size() == 50) {
        try
        {
          PreparedStatement localPreparedStatement = (PreparedStatement)this.jdField_for.remove(this.jdField_do[this.jdField_if]);
          localPreparedStatement.close();
        }
        catch (SQLException localSQLException) {}
      }
      this.jdField_for.put(paramString, paramPreparedStatement);
      this.jdField_do[this.jdField_if] = paramString;
      this.jdField_if = ((this.jdField_if + 1) % 50);
    }
    
    final void a()
    {
      this.jdField_if = 0;
      for (int i = 0; i < 50; i++) {
        if (this.jdField_do[i] != null)
        {
          try
          {
            PreparedStatement localPreparedStatement = (PreparedStatement)this.jdField_for.remove(this.jdField_do[i]);
            if (localPreparedStatement != null) {
              localPreparedStatement.close();
            }
          }
          catch (SQLException localSQLException) {}
          this.jdField_do[i] = null;
        }
      }
    }
    
    a(DBConnection.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DBConnection
 * JD-Core Version:    0.7.0.1
 */