package com.ffusion.util.db;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class DBUtil
  implements ConnectionDefines
{
  public static final String GLOBAL_SETTINGS = "GLOBAL_SETTINGS";
  private static final boolean jdField_int = false;
  public static final String DATABASE_UTIL_DEBUG_LOGGING = "DatabaseUtilDebugLogging";
  public static final String MONITOR_OPEN_CONNECTIONS = "MonitorOpenConnections";
  private static final boolean jdField_byte = false;
  public static final String MONITOR_OPEN_CONNECTIONS_INTERVAL = "MonitorOpenConnectionsInterval";
  private static final int jdField_else = 900000;
  public static final int MAX_NUMBER_OF_ITEMS_IN_LIST = 1000;
  public static long THRESHOLD_MS = 9999L;
  private static a jdField_try;
  private static boolean jdField_new = false;
  private static boolean jdField_case = false;
  private static ArrayList jdField_for = null;
  private static ArrayList jdField_goto = null;
  private static ArrayList jdField_char = null;
  
  public static void initialize(HashMap paramHashMap)
    throws Exception
  {
    if (!jdField_new) {
      try
      {
        HashMap localHashMap1 = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
        if (localHashMap1 == null)
        {
          setDebugMode(false);
        }
        else
        {
          String str1 = (String)localHashMap1.get("DatabaseUtilDebugLogging");
          if ((str1 == null) || (str1.equals(""))) {
            setDebugMode(false);
          } else {
            setDebugMode(Boolean.valueOf(str1).booleanValue());
          }
        }
      }
      catch (ClassCastException localClassCastException1) {}
    }
    if (jdField_new)
    {
      boolean bool = false;
      int i = 900000;
      try
      {
        HashMap localHashMap2 = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
        if (localHashMap2 != null)
        {
          String str2 = (String)localHashMap2.get("MonitorOpenConnections");
          if ((str2 != null) && (!str2.equals(""))) {
            bool = Boolean.valueOf(str2).booleanValue();
          }
          String str3 = (String)localHashMap2.get("MonitorOpenConnectionsInterval");
          if ((str3 != null) && (!str3.equals(""))) {
            i = Integer.parseInt(str3);
          }
        }
      }
      catch (ClassCastException localClassCastException2) {}
      if (bool) {
        jdField_try = new a(i);
      }
    }
  }
  
  public static void setDebugMode(boolean paramBoolean)
  {
    if (jdField_new != paramBoolean)
    {
      jdField_new = paramBoolean;
      if (jdField_new == true)
      {
        jdField_for = new ArrayList();
        jdField_goto = new ArrayList();
        jdField_char = new ArrayList();
        DebugLog.log(Level.SEVERE, "Database debug logging has been enabled by the system. All open connections detected by the Database Util will be reported.");
      }
      else
      {
        jdField_for = null;
        jdField_goto = null;
        jdField_char = null;
      }
    }
  }
  
  public static boolean getDebugMode()
  {
    return jdField_new;
  }
  
  public static Statement createStatement(Connection paramConnection)
    throws Exception
  {
    return createStatement(paramConnection, false);
  }
  
  public static PreparedStatement prepareStatement(Connection paramConnection, String paramString)
    throws SQLException, Exception
  {
    return prepareStatement(paramConnection, paramString, false);
  }
  
  public static PreparedStatement prepareStatement(Connection paramConnection, String paramString, int paramInt1, int paramInt2)
    throws SQLException, Exception
  {
    return prepareStatement(paramConnection, paramString, paramInt1, paramInt2, false);
  }
  
  public static Statement createStatement(Connection paramConnection, boolean paramBoolean)
    throws Exception
  {
    Statement localStatement = paramConnection.createStatement();
    if (localStatement == null) {
      throw new Exception("Couldn't create statement.");
    }
    if (jdField_new) {
      a(paramConnection, localStatement, paramBoolean);
    }
    return localStatement;
  }
  
  public static PreparedStatement prepareStatement(Connection paramConnection, String paramString, boolean paramBoolean)
    throws SQLException, Exception
  {
    PreparedStatement localPreparedStatement = paramConnection.prepareStatement(paramString);
    if (localPreparedStatement == null) {
      throw new Exception("Couldn't prepare statement");
    }
    if (jdField_new) {
      a(paramConnection, localPreparedStatement, paramBoolean);
    }
    return localPreparedStatement;
  }
  
  public static PreparedStatement prepareStatement(Connection paramConnection, String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
    throws SQLException, Exception
  {
    PreparedStatement localPreparedStatement = paramConnection.prepareStatement(paramString, paramInt1, paramInt2);
    if (localPreparedStatement == null) {
      throw new Exception("Couldn't prepare statement");
    }
    if (jdField_new) {
      a(paramConnection, localPreparedStatement, paramBoolean);
    }
    return localPreparedStatement;
  }
  
  public static int executeUpdate(Statement paramStatement, String paramString)
    throws Exception
  {
    int i = 0;
    long l = System.currentTimeMillis();
    try
    {
      i = paramStatement.executeUpdate(paramString);
    }
    finally
    {
      debugTime(l, paramString);
    }
    return i;
  }
  
  public static boolean execute(Statement paramStatement, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    boolean bool = false;
    try
    {
      bool = paramStatement.execute(paramString);
    }
    finally
    {
      debugTime(l, paramString);
    }
    return bool;
  }
  
  public static ResultSet executeQuery(Statement paramStatement, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    ResultSet localResultSet = null;
    try
    {
      localResultSet = paramStatement.executeQuery(paramString);
      if (jdField_new) {
        a(paramStatement, localResultSet);
      }
    }
    finally
    {
      debugTime(l, paramString);
    }
    return localResultSet;
  }
  
  public static boolean execute(PreparedStatement paramPreparedStatement, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    boolean bool = false;
    try
    {
      bool = paramPreparedStatement.execute();
    }
    finally
    {
      debugTime(l, paramString);
    }
    return bool;
  }
  
  public static int executeUpdate(PreparedStatement paramPreparedStatement, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    int i = 0;
    try
    {
      i = paramPreparedStatement.executeUpdate();
    }
    finally
    {
      debugTime(l, paramString);
    }
    return i;
  }
  
  public static ResultSet executeQuery(PreparedStatement paramPreparedStatement, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    ResultSet localResultSet = null;
    try
    {
      localResultSet = paramPreparedStatement.executeQuery();
      if (jdField_new) {
        a(paramPreparedStatement, localResultSet);
      }
    }
    finally
    {
      debugTime(l, paramString);
    }
    return localResultSet;
  }
  
  public static int[] executeBatch(PreparedStatement paramPreparedStatement, String paramString)
    throws Exception
  {
    long l = System.currentTimeMillis();
    try
    {
      int[] arrayOfInt = paramPreparedStatement.executeBatch();
      return arrayOfInt;
    }
    finally
    {
      debugTime(l, paramString);
    }
  }
  
  protected static void debugTime(long paramLong, String paramString)
  {
    if ((THRESHOLD_MS != 9999L) && ((THRESHOLD_MS == 0L) || (System.currentTimeMillis() - paramLong > THRESHOLD_MS))) {
      DebugLog.log(Level.SEVERE, "DBUtil: " + (System.currentTimeMillis() - paramLong) + " ms - " + paramString);
    }
  }
  
  public static Connection getConnection(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
    throws PoolException
  {
    Connection localConnection = ConnectionPool.getConnection(paramString, paramBoolean1, paramInt);
    if (jdField_new) {
      jdMethod_if(localConnection, paramBoolean2);
    }
    return localConnection;
  }
  
  public static Connection getConnection(String paramString, boolean paramBoolean, int paramInt)
    throws PoolException
  {
    return getConnection(paramString, paramBoolean, paramInt, false);
  }
  
  public static void returnConnection(String paramString, Connection paramConnection)
  {
    try
    {
      if (paramConnection != null)
      {
        ConnectionPool.returnConnection(paramString, paramConnection);
        if (jdField_new) {
          a(paramConnection);
        }
      }
    }
    catch (Exception localException) {}
  }
  
  public static void returnConnectionWithException(String paramString, Connection paramConnection)
    throws PoolException
  {
    if (paramConnection != null)
    {
      ConnectionPool.returnConnection(paramString, paramConnection);
      if (jdField_new) {
        a(paramConnection);
      }
    }
  }
  
  public static void closeResultSet(ResultSet paramResultSet)
  {
    try
    {
      if (paramResultSet != null)
      {
        paramResultSet.close();
        if (jdField_new) {
          a(paramResultSet);
        }
      }
    }
    catch (Exception localException) {}
  }
  
  public static void closeStatement(Statement paramStatement)
  {
    try
    {
      if (paramStatement != null)
      {
        paramStatement.close();
        if (jdField_new) {
          a(paramStatement, true);
        }
      }
    }
    catch (Exception localException) {}
  }
  
  public static void closeStatement(PreparedStatement paramPreparedStatement)
  {
    try
    {
      if (paramPreparedStatement != null)
      {
        paramPreparedStatement.close();
        if (jdField_new) {
          a(paramPreparedStatement, true);
        }
      }
    }
    catch (Exception localException) {}
  }
  
  public static void closeAll(Statement paramStatement, ResultSet paramResultSet)
  {
    closeResultSet(paramResultSet);
    closeStatement(paramStatement);
  }
  
  public static void closeAll(PreparedStatement paramPreparedStatement, ResultSet paramResultSet)
  {
    closeResultSet(paramResultSet);
    closeStatement(paramPreparedStatement);
  }
  
  public static void closeAll(String paramString, Connection paramConnection, PreparedStatement paramPreparedStatement)
  {
    closeStatement(paramPreparedStatement);
    returnConnection(paramString, paramConnection);
  }
  
  public static void closeAll(String paramString, Connection paramConnection, PreparedStatement paramPreparedStatement, ResultSet paramResultSet)
  {
    closeResultSet(paramResultSet);
    closeStatement(paramPreparedStatement);
    returnConnection(paramString, paramConnection);
  }
  
  public static void closeAll(String paramString, Connection paramConnection, Statement paramStatement, ResultSet paramResultSet)
  {
    closeResultSet(paramResultSet);
    closeStatement(paramStatement);
    returnConnection(paramString, paramConnection);
  }
  
  public static void beginTransaction(Connection paramConnection)
    throws Exception
  {
    try
    {
      paramConnection.setAutoCommit(false);
    }
    catch (SQLException localSQLException)
    {
      throw new Exception("Unable to begin the transaction: " + localSQLException);
    }
  }
  
  public static void endTransaction(Connection paramConnection)
    throws Exception
  {
    try
    {
      paramConnection.setAutoCommit(true);
    }
    catch (SQLException localSQLException)
    {
      throw new Exception("Unable to end the transaction: " + localSQLException);
    }
  }
  
  public static void setTransactionIsolation(Connection paramConnection, int paramInt)
    throws Exception
  {
    try
    {
      paramConnection.setTransactionIsolation(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new Exception("Unable to set the transaction isolation level: " + localSQLException);
    }
  }
  
  public static void commit(Connection paramConnection)
    throws Exception
  {
    try
    {
      paramConnection.commit();
    }
    catch (SQLException localSQLException)
    {
      throw new Exception("Unable to commit the transaction: " + localSQLException);
    }
  }
  
  public static void rollback(Connection paramConnection)
  {
    try
    {
      if (paramConnection != null) {
        paramConnection.rollback();
      }
    }
    catch (SQLException localSQLException) {}
  }
  
  public static java.sql.Date getCurrentDate()
  {
    return new java.sql.Date(System.currentTimeMillis());
  }
  
  public static Timestamp getCurrentTimestamp()
  {
    return new Timestamp(System.currentTimeMillis());
  }
  
  public static int getNextId(Connection paramConnection, String paramString1, String paramString2)
    throws Exception
  {
    int i = -1;
    if ((paramString1.equalsIgnoreCase("ORACLE:THIN")) || (paramString1.equalsIgnoreCase("ORACLE:OCI8"))) {
      i = jdMethod_do(paramConnection, paramString2);
    } else if (paramString1.equalsIgnoreCase("MSSQL:THIN")) {
      i = jdField_for(paramConnection, paramString2);
    } else if ((paramString1.equalsIgnoreCase("ASA")) || (paramString1.equalsIgnoreCase("ASE"))) {
      i = jdMethod_if(paramConnection, paramString2);
    } else if ((paramString1.equalsIgnoreCase("DB2:APP")) || (paramString1.equalsIgnoreCase("DB2:UN2")) || (paramString1.equalsIgnoreCase("DB2:NET")) || (paramString1.equalsIgnoreCase("DB2:AS390"))) {
      i = a(paramConnection, paramString2);
    } else {
      throw new Exception(paramString1 + " database not supported.");
    }
    if (i == -1) {
      throw new Exception("Unable to get current ID.");
    }
    return i;
  }
  
  private static int jdMethod_do(Connection paramConnection, String paramString)
    throws Exception
  {
    int i = -1;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select ").append(paramString).append("_seq.NEXTVAL from dual");
    Statement localStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localStatement = paramConnection.createStatement();
      localResultSet = localStatement.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      localResultSet.close();
      localStatement.close();
    }
  }
  
  private static int jdField_for(Connection paramConnection, String paramString)
    throws Exception
  {
    int i = -1;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = paramConnection.prepareCall("{CALL nextval(?,?)}");
      localCallableStatement.setString(1, paramString + "_seq");
      localCallableStatement.registerOutParameter(2, 4);
      localCallableStatement.execute();
      i = localCallableStatement.getInt(2);
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      System.out.println("Error getting next ID from Microsoft SqlServer: " + localException);
      throw localException;
    }
    finally
    {
      if (localCallableStatement != null) {
        localCallableStatement.close();
      }
    }
  }
  
  private static int jdMethod_if(Connection paramConnection, String paramString)
    throws Exception
  {
    int i = -1;
    CallableStatement localCallableStatement = null;
    try
    {
      localCallableStatement = paramConnection.prepareCall("{CALL nextval(?,?)}");
      localCallableStatement.setString(1, paramString.toUpperCase() + "_SEQ");
      localCallableStatement.registerOutParameter(2, 4);
      localCallableStatement.execute();
      i = localCallableStatement.getInt(2);
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      System.out.println("Error getting next ID from Sybase: " + localException);
      throw localException;
    }
    finally
    {
      if (localCallableStatement != null) {
        localCallableStatement.close();
      }
    }
  }
  
  private static int a(Connection paramConnection, String paramString)
    throws Exception
  {
    int i = -1;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select NEXTVAL FOR ").append(paramString).append("_seq from sysibm.sysdummy1");
    Statement localStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localStatement = paramConnection.createStatement();
      localResultSet = localStatement.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
      int j = i;
      return j;
    }
    catch (Exception localException)
    {
      String str = localException.getMessage();
      throw localException;
    }
    finally
    {
      localResultSet.close();
      localStatement.close();
    }
  }
  
  public static String ignoreCase(String paramString1, String paramString2)
  {
    return "lower(" + paramString1 + ")";
  }
  
  public static String getDBUrl(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString1.equalsIgnoreCase("DB2:APP")) || (paramString1.equalsIgnoreCase("DB2:UN2")))
    {
      localStringBuffer.append("jdbc:db2:");
      localStringBuffer.append(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("DB2:NET"))
    {
      localStringBuffer.append("jdbc:db2://");
      localStringBuffer.append(paramString3);
      if ((paramString4 != null) && (paramString4.length() > 0)) {
        localStringBuffer.append(":").append(paramString4);
      }
      localStringBuffer.append("/");
      localStringBuffer.append(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("DB2:AS390"))
    {
      localStringBuffer.append("jdbc:db2os390://");
      if ((paramString4 != null) && (paramString4.length() > 0))
      {
        localStringBuffer.append(paramString3).append(":");
        localStringBuffer.append(paramString4).append("/");
      }
      localStringBuffer.append(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("ORACLE:THIN"))
    {
      localStringBuffer.append("jdbc:oracle:thin:@");
      localStringBuffer.append(paramString3).append(":");
      localStringBuffer.append(paramString4).append(":");
      localStringBuffer.append(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("ORACLE:OCI8"))
    {
      localStringBuffer.append("jdbc:oracle:oci:@");
      localStringBuffer.append(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("MSSQL:THIN"))
    {
      localStringBuffer.append("jdbc:JSQLConnect://");
      localStringBuffer.append(paramString3).append(":");
      localStringBuffer.append(paramString4).append("/database=");
      localStringBuffer.append(paramString2);
    }
    else
    {
      localStringBuffer.append("jdbc:sybase:Tds:");
      localStringBuffer.append(paramString3).append(":");
      localStringBuffer.append(paramString4);
      if ((paramString2 != null) && (paramString2.length() > 0)) {
        localStringBuffer.append("/").append(paramString2);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String fixDate(String paramString, java.util.Date paramDate)
  {
    if (paramDate != null)
    {
      String str2;
      String str1;
      if ((paramString.equalsIgnoreCase("ORACLE:THIN")) || (paramString.equalsIgnoreCase("ORACLE:OCI8")))
      {
        str2 = DateFormatUtil.getFormatter("MM-dd-yyyy HH:mm:ss").format(paramDate);
        str1 = "TO_DATE('" + str2 + "','MM-DD-YYYY HH24:MI:SS')";
      }
      else if ((paramString.equalsIgnoreCase("MSSQL:THIN")) || (paramString.equalsIgnoreCase("ASE")))
      {
        str2 = DateFormatUtil.getFormatter("yyyy-MM-dd HH:mm:ss").format(paramDate);
        str1 = "'" + str2 + "'";
      }
      else
      {
        str2 = DateFormatUtil.getFormatter("yyyy-MM-dd-HH.mm.ss").format(paramDate);
        str1 = "'" + str2 + "'";
      }
      return str1;
    }
    return "''";
  }
  
  public static boolean getAnyOpenAccess()
  {
    if (jdField_new)
    {
      Iterator localIterator;
      synchronized (jdField_for)
      {
        localIterator = jdField_for.iterator();
        while (localIterator.hasNext()) {
          if (!((b)localIterator.next()).jdField_if) {
            return true;
          }
        }
      }
      synchronized (jdField_goto)
      {
        localIterator = jdField_goto.iterator();
        while (localIterator.hasNext()) {
          if (!((e)localIterator.next()).jdField_if) {
            return true;
          }
        }
      }
      synchronized (jdField_char)
      {
        localIterator = jdField_char.iterator();
        while (localIterator.hasNext()) {
          if (!((c)localIterator.next()).jdField_if) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public static void resetErrorStatus()
  {
    if (jdField_new) {
      jdField_case = false;
    }
  }
  
  public static boolean getErrorStatus()
  {
    return jdField_case;
  }
  
  public static void clearAllDebugInformation()
  {
    if (jdField_new)
    {
      Iterator localIterator;
      synchronized (jdField_for)
      {
        localIterator = jdField_for.iterator();
        while (localIterator.hasNext()) {
          if (!((b)localIterator.next()).jdField_if) {
            localIterator.remove();
          }
        }
      }
      synchronized (jdField_goto)
      {
        localIterator = jdField_goto.iterator();
        while (localIterator.hasNext()) {
          if (!((e)localIterator.next()).jdField_if) {
            localIterator.remove();
          }
        }
      }
      synchronized (jdField_char)
      {
        localIterator = jdField_char.iterator();
        while (localIterator.hasNext()) {
          if (!((c)localIterator.next()).jdField_if) {
            localIterator.remove();
          }
        }
      }
    }
  }
  
  public static String getDiagnosticInformation()
  {
    if (jdField_new)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(" Database Diagnostic Information \n");
      int i;
      synchronized (jdField_for)
      {
        localStringBuffer.append("Connections Open: " + jdField_for.size() + "\n");
        for (i = 0; i < jdField_for.size(); i++) {
          localStringBuffer.append("Connection " + (i + 1) + ": " + ((b)jdField_for.get(i)).a + "\n");
        }
      }
      synchronized (jdField_goto)
      {
        localStringBuffer.append("Statements Open: " + jdField_goto.size() + "\n");
        for (i = 0; i < jdField_goto.size(); i++) {
          localStringBuffer.append("Statement " + (i + 1) + ": " + ((e)jdField_goto.get(i)).a + "\n");
        }
      }
      synchronized (jdField_char)
      {
        localStringBuffer.append("Result Sets Open: " + jdField_char.size() + "\n");
        for (i = 0; i < jdField_char.size(); i++) {
          localStringBuffer.append("Result Set " + (i + 1) + ": " + ((c)jdField_char.get(i)).a + "\n");
        }
      }
      return localStringBuffer.toString();
    }
    return "Debug mode not enabled.";
  }
  
  private static void jdMethod_if(Connection paramConnection, boolean paramBoolean)
  {
    b localb = a(paramConnection, true);
    if (localb != null)
    {
      jdField_case = true;
      DebugLog.log(Level.SEVERE, "Connection not released through DBUtil: " + localb.a);
    }
    synchronized (jdField_for)
    {
      jdField_for.add(new b(paramConnection, paramBoolean));
    }
  }
  
  private static void a(Connection paramConnection, Statement paramStatement, boolean paramBoolean)
  {
    e locale = new e(paramConnection, paramStatement, paramBoolean);
    b localb = a(paramConnection, false);
    if (localb == null)
    {
      jdField_case = true;
      DebugLog.log(Level.SEVERE, "Connection not added to Util for statement: " + locale.a);
    }
    else
    {
      synchronized (jdField_goto)
      {
        jdField_goto.add(locale);
      }
    }
  }
  
  private static void a(Statement paramStatement, ResultSet paramResultSet)
  {
    e locale = jdMethod_if(paramStatement, false);
    c localc;
    if (locale == null)
    {
      localc = new c(paramStatement, paramResultSet, false);
      jdField_case = true;
      DebugLog.log(Level.SEVERE, "Statement not added to Util for Result Set: " + localc.a);
    }
    else
    {
      localc = new c(paramStatement, paramResultSet, locale.jdField_if);
      synchronized (jdField_char)
      {
        jdField_char.add(localc);
      }
    }
  }
  
  private static void a(Connection paramConnection)
  {
    b localb = a(paramConnection, true);
    if (localb != null)
    {
      ArrayList localArrayList = jdMethod_if(paramConnection);
      if (localArrayList.size() != 0)
      {
        jdField_case = true;
        DebugLog.log(Level.SEVERE, "Error when releasing connection, " + localArrayList.size() + " Statements have been left open.");
        DebugLog.log(Level.SEVERE, "Connection: " + localb.a);
        for (int i = 0; i < localArrayList.size(); i++)
        {
          e locale = (e)localArrayList.get(i);
          DebugLog.log(Level.SEVERE, "Statement " + (i + 1) + ": " + locale.a);
          a(locale.jdField_try, false);
        }
      }
    }
    else
    {
      jdField_case = true;
      localb = new b(paramConnection, false);
      DebugLog.log(Level.SEVERE, "Util releasing a connection that has not been registered: " + localb.a);
    }
  }
  
  private static void a(Statement paramStatement, boolean paramBoolean)
  {
    e locale = jdMethod_if(paramStatement, true);
    if (locale != null)
    {
      ArrayList localArrayList = a(paramStatement);
      if (localArrayList.size() != 0)
      {
        jdField_case = true;
        if (paramBoolean)
        {
          DebugLog.log(Level.SEVERE, "Error when closing statement, " + localArrayList.size() + " ResultSets have been left open.");
          DebugLog.log(Level.SEVERE, "Statement: " + locale.a);
        }
        for (int i = 0; i < localArrayList.size(); i++) {
          DebugLog.log(Level.SEVERE, "Result Set " + (i + 1) + ": " + ((c)localArrayList.get(i)).a);
        }
      }
    }
    else
    {
      jdField_case = true;
      locale = new e(null, paramStatement, false);
      DebugLog.log(Level.SEVERE, "Util releasing a statement that has not been registered: " + locale.a);
    }
  }
  
  private static void a(ResultSet paramResultSet)
  {
    c localc = a(paramResultSet, true);
    if (localc == null)
    {
      jdField_case = true;
      localc = new c(null, paramResultSet, false);
      DebugLog.log(Level.SEVERE, "Util releasing a ResultSet that has not been registered: " + localc.a);
    }
  }
  
  private static b a(Connection paramConnection, boolean paramBoolean)
  {
    synchronized (jdField_for)
    {
      Iterator localIterator = jdField_for.iterator();
      while (localIterator.hasNext())
      {
        b localb = (b)localIterator.next();
        if (localb.jdField_byte == paramConnection)
        {
          if (paramBoolean) {
            localIterator.remove();
          }
          return localb;
        }
      }
    }
    return null;
  }
  
  private static e jdMethod_if(Statement paramStatement, boolean paramBoolean)
  {
    synchronized (jdField_goto)
    {
      Iterator localIterator = jdField_goto.iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        if (locale.jdField_try == paramStatement)
        {
          if (paramBoolean) {
            localIterator.remove();
          }
          return locale;
        }
      }
    }
    return null;
  }
  
  private static c a(ResultSet paramResultSet, boolean paramBoolean)
  {
    synchronized (jdField_char)
    {
      Iterator localIterator = jdField_char.iterator();
      while (localIterator.hasNext())
      {
        c localc = (c)localIterator.next();
        if (localc.jdField_for == paramResultSet)
        {
          if (paramBoolean) {
            localIterator.remove();
          }
          return localc;
        }
      }
    }
    return null;
  }
  
  private static ArrayList jdMethod_if(Connection paramConnection)
  {
    ArrayList localArrayList = new ArrayList();
    synchronized (jdField_goto)
    {
      Iterator localIterator = jdField_goto.iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        if (locale.jdField_new == paramConnection) {
          localArrayList.add(locale);
        }
      }
    }
    return localArrayList;
  }
  
  private static ArrayList a(Statement paramStatement)
  {
    ArrayList localArrayList = new ArrayList();
    synchronized (jdField_char)
    {
      Iterator localIterator = jdField_char.iterator();
      while (localIterator.hasNext())
      {
        c localc = (c)localIterator.next();
        if (localc.jdField_int == paramStatement)
        {
          localArrayList.add(localc);
          localIterator.remove();
        }
      }
    }
    return localArrayList;
  }
  
  public static StringBuffer escapeSQLStringLiteral(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString.length() + 5);
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      localStringBuffer.append(c);
      if (c == '\'') {
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer;
  }
  
  public static StringBuffer trimSQLNumericLiteral(String paramString, StringBuffer paramStringBuffer)
    throws NumberFormatException
  {
    if (paramString == null) {
      throw new NumberFormatException("Null SQL Numeric Literal");
    }
    int i = paramStringBuffer.length();
    int j = 0;
    int k = 1;
    for (int m = 0; m < paramString.length(); m++)
    {
      char c = paramString.charAt(m);
      if (Character.isDigit(c))
      {
        paramStringBuffer.append(c);
        k = 1;
      }
      else if ((c == '+') || (c == '-'))
      {
        if (m != 0) {
          break;
        }
        paramStringBuffer.append(c);
      }
      else if ((c == '.') && (j == 0))
      {
        j = 1;
        paramStringBuffer.append(c);
      }
      else
      {
        if (paramStringBuffer.length() != i) {
          break;
        }
      }
    }
    m = paramStringBuffer.length() - i;
    if (m == 0) {
      throw new NumberFormatException("Invalid Empty SQL Numeric Literal: '" + paramString + "'");
    }
    if (k == 0) {
      throw new NumberFormatException("Invalid SQL Numeric Literal: '" + paramStringBuffer.substring(i) + "' from '" + paramString + "'");
    }
    return paramStringBuffer;
  }
  
  public static StringBuffer generateSQLNumericInClause(String paramString1, String paramString2)
    throws NumberFormatException
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1.length());
    if (paramString1 == null) {
      return localStringBuffer;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",", false);
    if (localStringTokenizer.hasMoreTokens())
    {
      localStringBuffer.append(" ( ");
      while (localStringTokenizer.hasMoreTokens())
      {
        localStringBuffer.append(paramString2).append(" IN( ");
        generateSQLNumericList(localStringTokenizer, localStringBuffer);
        localStringBuffer.append(" ) ");
        if (localStringTokenizer.hasMoreTokens()) {
          localStringBuffer.append(" OR ");
        }
      }
      localStringBuffer.append(" ) ");
    }
    return localStringBuffer;
  }
  
  public static StringBuffer generateSQLNumericList(StringTokenizer paramStringTokenizer, StringBuffer paramStringBuffer)
    throws NumberFormatException
  {
    for (int i = 0; (i < 1000) && (paramStringTokenizer.hasMoreTokens()); i++)
    {
      String str = paramStringTokenizer.nextToken();
      if (i != 0) {
        paramStringBuffer.append(",");
      }
      trimSQLNumericLiteral(str, paramStringBuffer);
    }
    return paramStringBuffer;
  }
  
  static class a
    extends Thread
  {
    private boolean jdField_if;
    private int a;
    
    public a(int paramInt)
    {
      this.a = paramInt;
      this.jdField_if = true;
      start();
    }
    
    public void run()
    {
      long l1 = this.a;
      while (this.jdField_if)
      {
        try
        {
          if (l1 > 0L) {
            sleep(l1);
          }
        }
        catch (Exception localException) {}
        long l2 = System.currentTimeMillis();
        int i;
        Iterator localIterator;
        DBUtil.d locald;
        synchronized (DBUtil.for)
        {
          i = 1;
          localIterator = DBUtil.for.iterator();
          while (localIterator.hasNext())
          {
            locald = (DBUtil.d)localIterator.next();
            if (!locald.jdField_if) {
              if (locald.jdField_do)
              {
                if (i == 1) {
                  DebugLog.log(Level.SEVERE, "The DBUtil has identified connection(s) open for over " + this.a / 60000 + " minutes.");
                }
                DebugLog.log(Level.SEVERE, "Connection #" + i++ + " is currently open:" + locald.a);
              }
              else
              {
                locald.jdField_do = true;
              }
            }
          }
        }
        synchronized (DBUtil.goto)
        {
          i = 1;
          localIterator = DBUtil.goto.iterator();
          while (localIterator.hasNext())
          {
            locald = (DBUtil.d)localIterator.next();
            if (!locald.jdField_if) {
              if (locald.jdField_do)
              {
                if (i == 1) {
                  DebugLog.log(Level.SEVERE, "The DBUtil has identified statement(s) open for over " + this.a / 60000 + " minutes.");
                }
                DebugLog.log(Level.SEVERE, "Statement #" + i++ + " is currently open:" + locald.a);
              }
              else
              {
                locald.jdField_do = true;
              }
            }
          }
        }
        synchronized (DBUtil.char)
        {
          i = 1;
          localIterator = DBUtil.char.iterator();
          while (localIterator.hasNext())
          {
            locald = (DBUtil.d)localIterator.next();
            if (!locald.jdField_if) {
              if (locald.jdField_do)
              {
                if (i == 1) {
                  DebugLog.log(Level.SEVERE, "The DBUtil has identified Result Sets open for over " + this.a / 60000 + " minutes.");
                }
                DebugLog.log(Level.SEVERE, "Result Set #" + i++ + " is currently open:" + locald.a);
              }
              else
              {
                locald.jdField_do = true;
              }
            }
          }
        }
        l1 = this.a - (System.currentTimeMillis() - l2);
        if (l1 <= 0L) {
          l1 = 0L;
        }
      }
    }
    
    public void a()
    {
      this.jdField_if = false;
      interrupt();
    }
  }
  
  private static class c
    extends DBUtil.d
  {
    ResultSet jdField_for;
    Statement jdField_int;
    
    public c(Statement paramStatement, ResultSet paramResultSet, boolean paramBoolean)
    {
      super();
      this.jdField_int = paramStatement;
      this.jdField_for = paramResultSet;
    }
  }
  
  private static class e
    extends DBUtil.d
  {
    Statement jdField_try;
    Connection jdField_new;
    
    public e(Connection paramConnection, Statement paramStatement, boolean paramBoolean)
    {
      super();
      this.jdField_new = paramConnection;
      this.jdField_try = paramStatement;
    }
  }
  
  private static class b
    extends DBUtil.d
  {
    Connection jdField_byte;
    
    public b(Connection paramConnection, boolean paramBoolean)
    {
      super();
      this.jdField_byte = paramConnection;
    }
  }
  
  private static class d
  {
    String a = "";
    boolean jdField_if;
    boolean jdField_do = false;
    
    public d(boolean paramBoolean)
    {
      Throwable localThrowable = new Throwable();
      StringWriter localStringWriter = new StringWriter();
      localThrowable.printStackTrace(new PrintWriter(localStringWriter));
      this.a = localStringWriter.toString();
      this.jdField_if = paramBoolean;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.DBUtil
 * JD-Core Version:    0.7.0.1
 */