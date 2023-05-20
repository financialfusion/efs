package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.ConnectionFactory;
import com.ffusion.ffs.db.ConnectionPool;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class DBUtil
  implements BPWResource, FFSConst, DBConsts
{
  private static RecordLock[] kr = null;
  private static FFSConnectionHolder kp = null;
  private static final String ks = "00000000";
  private static HashMap ku = null;
  private static final int kt = 1;
  private static final int kq = 2;
  
  public static void init()
    throws Exception
  {
    if (kp == null)
    {
      FFSConnection localFFSConnection = getConnection();
      kp = new FFSConnectionHolder();
      kp.conn = localFFSConnection;
    }
    e(kp);
  }
  
  private static void e(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList(16);
    try
    {
      String str = null;
      localFFSResultSet = openResultSet(paramFFSConnectionHolder, "SELECT IndexName, IndexValue, IndexString FROM BPW_InternalIndices", null);
      while (localFFSResultSet.getNextRow())
      {
        str = localFFSResultSet.getColumnString(1);
        localArrayList.add(new RecordLock(str));
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    kr = (RecordLock[])localArrayList.toArray(new RecordLock[localArrayList.size()]);
  }
  
  public static synchronized void initIndexing()
  {
    if (ku != null) {
      return;
    }
    ku = new HashMap();
    String str1 = BPWRegistryUtil.getProperty("index.reserved.blocksize", "3000");
    int i = Integer.parseInt(str1);
    FFSDebug.log("DBUtil.initIndexing: Index reservation size = " + i, 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSConnectionHolder.conn = getConnection();
      localFFSResultSet = openResultSet(localFFSConnectionHolder, "SELECT IndexName, IndexValue, IndexString FROM BPW_InternalIndices", null);
      while (localFFSResultSet.getNextRow() == true)
      {
        String str2 = localFFSResultSet.getColumnString("IndexName");
        IndexEntry localIndexEntry = new IndexEntry(str2, i);
        ku.put(str2.toLowerCase(), localIndexEntry);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("DBUtil.initIndexing exception: " + localException1.getMessage());
      localException1.printStackTrace();
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2) {}
    }
  }
  
  public static void releaseResources()
  {
    FFSDebug.log("DBUtil.releaseResources started", 6);
    ae();
    kr = null;
    FFSDebug.log("DBUtil.releaseResources done", 6);
  }
  
  private static int ar(String paramString)
  {
    for (int i = 0; i < kr.length; i++) {
      if (paramString.equalsIgnoreCase(kr[i].a()))
      {
        kr[i].jdMethod_do();
        return i;
      }
    }
    return -1;
  }
  
  private static void at(String paramString)
  {
    for (int i = 0; i < kr.length; i++) {
      if (paramString.equalsIgnoreCase(kr[i].a()))
      {
        kr[i].jdMethod_do();
        break;
      }
    }
  }
  
  private static void jdMethod_new(int paramInt)
  {
    kr[paramInt].jdMethod_if();
  }
  
  private static final void ae()
  {
    FFSDebug.log("DBUtil.freeDBUtilConnection started", 6);
    if (kp != null) {
      try
      {
        kp.conn.close();
        kp = null;
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** DBUtil.freeDBUtilConnection failed:" + localException.toString(), 6);
      }
    }
    FFSDebug.log("DBUtil.freeDBUtilConnection done", 6);
  }
  
  public static FFSConnection getValidConnection()
    throws BPWException
  {
    FFSConnection localFFSConnection = null;
    int i = 0;
    while (i < 2) {
      try
      {
        ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
        localFFSConnection = localConnectionPool.getConnection();
        if (localFFSConnection.isClosed())
        {
          try
          {
            localFFSConnection = localConnectionPool.renewConnection(localFFSConnection);
          }
          catch (Exception localException2)
          {
            FFSDebug.log("*** DBUtil.getValidConnection renewConnection failed:" + FFSDebug.stackTrace(localException2));
            throw new BPWException("*** DBUtil.getValidConnection failed:" + FFSDebug.stackTrace(localException2));
          }
        }
        else
        {
          FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
          localFFSConnectionHolder.conn = localFFSConnection;
          String str = "SELECT Value FROM SCH_NamePlace";
          openResultSet(localFFSConnectionHolder, str, null);
        }
        return localFFSConnection;
      }
      catch (Exception localException1)
      {
        if (i != 0)
        {
          FFSDebug.log("*** DBUtil.getValidConnection failed:" + FFSDebug.stackTrace(localException1));
          throw new BPWException("*** DBUtil.getValidConnection failed:" + FFSDebug.stackTrace(localException1));
        }
        i++;
      }
    }
    return localFFSConnection;
  }
  
  public static FFSConnection getConnection()
    throws BPWException
  {
    FFSConnection localFFSConnection = null;
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      localFFSConnection = localConnectionPool.getConnection();
    }
    catch (Throwable localThrowable)
    {
      if (localFFSConnection != null) {
        freeConnection(localFFSConnection);
      }
      FFSDebug.log("*** DBUtil.getConnection failed:" + FFSDebug.stackTrace(localThrowable));
      throw new BPWException("DBUtil.getConnection failed:" + FFSDebug.stackTrace(localThrowable));
    }
    return localFFSConnection;
  }
  
  public static FFSConnection getDirtyReadConnection()
    throws BPWException
  {
    FFSConnection localFFSConnection = null;
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      int i = 1;
      localFFSConnection = localConnectionPool.getConnection(i);
    }
    catch (Throwable localThrowable)
    {
      if (localFFSConnection != null) {
        freeConnection(localFFSConnection);
      }
      FFSDebug.log("*** DBUtil.getConnection failed:" + FFSDebug.stackTrace(localThrowable));
      throw new BPWException("DBUtil.getConnection failed:" + FFSDebug.stackTrace(localThrowable));
    }
    return localFFSConnection;
  }
  
  public static FFSConnection getConnection(int paramInt)
    throws BPWException
  {
    FFSConnection localFFSConnection = null;
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      localFFSConnection = localConnectionPool.getConnection(paramInt);
    }
    catch (Throwable localThrowable)
    {
      if (localFFSConnection != null) {
        freeConnection(localFFSConnection);
      }
      FFSDebug.log("*** DBUtil.getConnection failed:" + FFSDebug.stackTrace(localThrowable));
      throw new BPWException("DBUtil.getConnection failed:" + FFSDebug.stackTrace(localThrowable));
    }
    return localFFSConnection;
  }
  
  public static FFSConnection[] getConnections(int paramInt)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getConnection started", 6);
    FFSConnection[] arrayOfFFSConnection = null;
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      arrayOfFFSConnection = localConnectionPool.getConnections(paramInt);
    }
    catch (Exception localException)
    {
      if (arrayOfFFSConnection != null) {
        for (int i = 0; i < paramInt; i++) {
          freeConnection(arrayOfFFSConnection[i]);
        }
      }
      FFSDebug.log("*** DBUtil.getConnection failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("DBUtil.getConnection done", 6);
    return arrayOfFFSConnection;
  }
  
  public static void freeConnection(FFSConnection paramFFSConnection)
  {
    try
    {
      paramFFSConnection.rollback();
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("There is open TX on this connection!", 6);
      localThrowable.printStackTrace();
    }
    paramFFSConnection.pruneCache();
    ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
    localConnectionPool.releaseConnection(paramFFSConnection);
  }
  
  public static final FFSConnection getMyOwnConnection(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    FFSConnection localFFSConnection = ConnectionFactory.getNewConnection(paramFFSDBProperties);
    localFFSConnection.setAutoCommit(false);
    return localFFSConnection;
  }
  
  public static FFSResultSet openResultSet(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject)
    throws Exception
  {
    FFSDebug.log("openResultSet: Query= " + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = paramFFSConnectionHolder.conn.prepareStmt(paramString);
      localFFSResultSet.open(paramArrayOfObject);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      FFSDebug.log(FFSDebug.stackTrace(localThrowable));
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException1)
      {
        localThrowable.printStackTrace();
        String str1 = "*** DBUtil.openResultSet: rollback() failed:";
        FFSDebug.log(str1 + FFSDebug.stackTrace(localException1));
        throw new BPWException(FFSDebug.stackTrace(localException1));
      }
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      try
      {
        paramFFSConnectionHolder.conn = localConnectionPool.renewConnection(paramFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        localThrowable.printStackTrace();
        String str3 = "*** DBUtil.openResultSet: renewConnection() failed:";
        FFSDebug.log(str3 + localException2.toString());
        throw new BPWException(localException2.toString());
      }
      String str2 = "*** DBUtil.openResultSet failed:";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable));
      throw new BPWException(str2 + FFSDebug.stackTrace(localThrowable));
    }
    return localFFSResultSet;
  }
  
  public static int executeStatement(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject)
    throws Exception
  {
    FFSDebug.log("executeStatement: Query= " + paramString, 6);
    int i = -1;
    try
    {
      i = paramFFSConnectionHolder.conn.executeCommand(paramString, paramArrayOfObject);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException1.printStackTrace();
        String str1 = "*** DBUtil.executeStatement: rollback() failed:";
        FFSDebug.log(str1 + localException2.toString());
        throw new BPWException(localException2.toString());
      }
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      try
      {
        paramFFSConnectionHolder.conn = localConnectionPool.renewConnection(paramFFSConnectionHolder.conn);
      }
      catch (Exception localException3)
      {
        localException1.printStackTrace();
        String str3 = "*** DBUtil.executeStatement renewConnection() failed:";
        FFSDebug.log(str3 + localException3.toString());
        throw new BPWException(localException3.toString());
      }
      String str2 = "*** DBUtil.executeStatement failed:";
      FFSDebug.log(str2 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    return i;
  }
  
  public static void populateBaseInfo(FFSResultSet paramFFSResultSet, BPWInfoBase paramBPWInfoBase)
    throws Exception
  {
    if (paramBPWInfoBase != null) {
      paramBPWInfoBase.setVersion(paramFFSResultSet.getColumnInt("VERSION"));
    }
  }
  
  public static ArrayList convertArrayToList(Object[] paramArrayOfObject)
  {
    return new ArrayList(Arrays.asList(paramArrayOfObject));
  }
  
  public static int executeStatement(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, Object[] paramArrayOfObject, BPWInfoBase paramBPWInfoBase, String paramString2, String paramString3)
    throws Exception
  {
    return executeStatement(paramFFSConnectionHolder, paramString1, convertArrayToList(paramArrayOfObject), paramBPWInfoBase, paramString2, paramString3);
  }
  
  public static int executeStatement(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, ArrayList paramArrayList, BPWInfoBase paramBPWInfoBase, String paramString2, String paramString3)
    throws Exception
  {
    FFSDebug.log("executeStatement: Query= " + paramString1, 6);
    int i = -1;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString1);
      String str1 = "";
      if (af())
      {
        str1 = as(paramString1);
        if (str1 == null)
        {
          str2 = "*** DBUtil.executeStatement: only single row UPDATE & DELETE statements supported";
          FFSDebug.log(str2);
          throw new BPWException(str2);
        }
        if (!aq(paramString1))
        {
          str2 = "*** DBUtil.executeStatement: only single row UPDATE & DELETE statements supported";
          FFSDebug.log(str2);
          throw new BPWException(str2);
        }
        jdMethod_if(localStringBuffer, paramArrayList, "VERSION", String.valueOf(paramBPWInfoBase.getVersion()), 2);
      }
      i = paramFFSConnectionHolder.conn.executeCommand(localStringBuffer.toString(), paramArrayList.toArray());
      if ((i == 0) && (af()))
      {
        str2 = "SELECT Version FROM " + str1 + " WHERE " + paramString2 + " = ? ";
        localObject = new Object[] { paramString3 };
        FFSResultSet localFFSResultSet = openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject);
        if (localFFSResultSet.getNextRow())
        {
          int j = localFFSResultSet.getColumnInt(1);
          if (j != paramBPWInfoBase.getVersion())
          {
            String str4 = "Optimistic locking error: table:" + str1 + " DB version:" + j + " Bean Version:" + paramBPWInfoBase.getVersion();
            FFSDebug.log(str4);
            paramBPWInfoBase.setStatusCode(26028);
            paramBPWInfoBase.setStatusMsg("Invalid Version");
          }
        }
      }
      else if (au(paramString1))
      {
        paramBPWInfoBase.setVersion(paramBPWInfoBase.getVersion() + 1);
      }
    }
    catch (Exception localException1)
    {
      String str2;
      Object localObject;
      localException1.printStackTrace();
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException1.printStackTrace();
        str2 = "*** DBUtil.executeStatement: rollback() failed:";
        FFSDebug.log(str2 + localException2.toString());
        throw new BPWException(localException2.toString());
      }
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      try
      {
        paramFFSConnectionHolder.conn = localConnectionPool.renewConnection(paramFFSConnectionHolder.conn);
      }
      catch (Exception localException3)
      {
        localException1.printStackTrace();
        localObject = "*** DBUtil.executeStatement renewConnection() failed:";
        FFSDebug.log((String)localObject + localException3.toString());
        throw new BPWException(localException3.toString());
      }
      String str3 = "*** DBUtil.executeStatement failed:";
      FFSDebug.log(str3 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    return i;
  }
  
  private static String as(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    String str = localStringTokenizer.nextToken();
    if ((str.equalsIgnoreCase("UPDATE")) || (str.equalsIgnoreCase("DELETE"))) {
      return localStringTokenizer.nextToken();
    }
    return null;
  }
  
  private static boolean aq(String paramString)
  {
    String str = paramString.toUpperCase();
    return str.indexOf("WHERE") != -1;
  }
  
  private static boolean au(String paramString)
  {
    String str = paramString.toUpperCase();
    return str.indexOf("UPDATE") != -1;
  }
  
  private static boolean af()
  {
    FFSProperties localFFSProperties = (FFSProperties)FFSRegistry.lookup("FFSPROPS");
    String str = localFFSProperties.getProperty("bpw.version.check", "N");
    boolean bool = !str.equalsIgnoreCase("N");
    return bool;
  }
  
  public static int executeStatementInTX(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject)
    throws Exception
  {
    FFSDebug.log("executeStatement: Query= " + paramString, 6);
    int i = -1;
    i = paramFFSConnectionHolder.conn.executeCommand(paramString, paramArrayOfObject);
    return i;
  }
  
  public static int executeStatement(FFSConnectionHolder paramFFSConnectionHolder, String paramString, Object[] paramArrayOfObject, int paramInt)
    throws Exception
  {
    FFSDebug.log("executeStatement: Query= " + paramString, 6);
    int i = -1;
    try
    {
      i = paramFFSConnectionHolder.conn.executeCommand(paramString, paramArrayOfObject, paramInt);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException1.printStackTrace();
        String str1 = "*** DBUtil.executeStatement: rollback() failed:";
        FFSDebug.log(str1 + localException2.toString());
        throw new BPWException(localException2.toString());
      }
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      try
      {
        paramFFSConnectionHolder.conn.rollback();
        paramFFSConnectionHolder.conn = localConnectionPool.renewConnection(paramFFSConnectionHolder.conn);
      }
      catch (Exception localException3)
      {
        localException1.printStackTrace();
        String str3 = "*** DBUtil.executeStatement renewConnection() failed:";
        FFSDebug.log(str3 + localException3.toString());
        throw new BPWException(localException3.toString());
      }
      String str2 = "*** DBUtil.executeStatement failed:";
      FFSDebug.log(str2 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    return i;
  }
  
  public static int[] executeStatementBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, ArrayList paramArrayList)
    throws Exception
  {
    FFSDebug.log("executeStatement: Query= " + paramString, 6);
    if (paramArrayList == null) {
      return null;
    }
    int[] arrayOfInt = null;
    try
    {
      PreparedStatement localPreparedStatement = paramFFSConnectionHolder.conn.prepareStatement(paramString);
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        a(localPreparedStatement, (ArrayList)paramArrayList.get(i));
        localPreparedStatement.addBatch();
      }
      arrayOfInt = localPreparedStatement.executeBatch();
      localPreparedStatement.clearBatch();
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException2)
      {
        localException1.printStackTrace();
        String str1 = "*** DBUtil.executeStatementBatch: rollback() failed:";
        FFSDebug.log(str1 + localException2.toString());
        throw new BPWException(localException2.toString());
      }
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      try
      {
        paramFFSConnectionHolder.conn = localConnectionPool.renewConnection(paramFFSConnectionHolder.conn);
      }
      catch (Exception localException3)
      {
        localException1.printStackTrace();
        String str3 = "*** DBUtil.executeBatch renewConnection() failed:";
        FFSDebug.log(str3 + localException3.toString());
        throw new BPWException(localException3.toString());
      }
      String str2 = "*** DBUtil.executeBatch failed:";
      FFSDebug.log(str2 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    return arrayOfInt;
  }
  
  private static void a(PreparedStatement paramPreparedStatement, ArrayList paramArrayList)
    throws Exception
  {
    for (int i = 0; i < paramArrayList.size(); i++) {
      paramPreparedStatement.setString(i + 1, (String)paramArrayList.get(i));
    }
  }
  
  public static void printAvailabeConnNum()
  {
    ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
    int i = -1;
    try
    {
      i = localConnectionPool.getAvailabeConnNum();
    }
    catch (Exception localException)
    {
      FFSDebug.log("printAvailabeConnNum exception " + localException.getMessage());
    }
    FFSDebug.log("printAvailabeConnNum = " + i);
  }
  
  public static String getCurrentDateTime()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    return localSimpleDateFormat.format(new Date());
  }
  
  public static String getCurrentLogDate()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    return localSimpleDateFormat.format(new Date());
  }
  
  public static int getCurrentStartDate()
  {
    int i = 0;
    try
    {
      DateFormat localDateFormat = DateFormat.getDateInstance(2);
      str = localDateFormat.format(new Date());
      Date localDate = localDateFormat.parse(str);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate);
      i = localCalendar.get(1) * 1000000 + (localCalendar.get(2) + 1) * 10000 + localCalendar.get(5) * 100;
    }
    catch (ParseException localParseException)
    {
      String str = "*** DBUtil.convertInstanceDateToNum parse failed:";
      FFSDebug.log(str + localParseException.getMessage());
    }
    return i;
  }
  
  public static String convertInstanceDateToString(String paramString)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
    Date localDate = localSimpleDateFormat1.parse(paramString, new ParsePosition(0));
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return localSimpleDateFormat2.format(localDate);
  }
  
  public static int getNextIndex(String paramString)
    throws BPWException
  {
    int i = 0;
    try
    {
      i = getNextIndex(null, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("DBUtil.getNextIndex exception: " + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString());
    }
    return i;
  }
  
  public static int getNextIndex(String paramString, int paramInt)
    throws BPWException
  {
    int i = 0;
    try
    {
      i = getNextIndex(null, paramString, paramInt);
    }
    catch (Exception localException)
    {
      FFSDebug.log("DBUtil.getNextIndex exception: " + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString());
    }
    return i;
  }
  
  private static final void ag()
    throws Exception
  {
    if ((kp == null) || (kp.conn == null))
    {
      FFSConnection localFFSConnection = getConnection();
      kp = new FFSConnectionHolder();
      kp.conn = localFFSConnection;
    }
    else if (kp.conn.isClosed())
    {
      kp.conn = getConnection();
    }
  }
  
  public static final int getNextIndex(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("DBUtil.getNextIndex: indexName=" + paramString, 6);
    int i = 0;
    try
    {
      if (ku == null) {
        initIndexing();
      }
      IndexEntry localIndexEntry = (IndexEntry)ku.get(paramString.toLowerCase());
      i = localIndexEntry.getNextIndexInt();
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new Exception("DBUtil.getNextIndex: No such index name: " + paramString);
    }
    catch (Exception localException)
    {
      throw localException;
    }
    return i;
  }
  
  public static final int getNextIndex(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "DBUtil.getNextIndex: ";
    String str2 = null;
    int i = 0;
    int j = 0;
    try
    {
      str2 = Integer.toString(getNextIndex(paramFFSConnectionHolder, paramString));
    }
    catch (Exception localException)
    {
      throw new FFSException(localException, str1 + "Unable to generate next index for " + paramString);
    }
    if (i < 0) {
      throw new FFSException(str1 + "Negative next index: " + i);
    }
    if ((paramInt <= 0) || (paramInt > str2.length()))
    {
      i = Integer.parseInt(str2);
    }
    else
    {
      j = str2.length() - paramInt;
      i = Integer.parseInt(str2.substring(j));
    }
    return i;
  }
  
  public static String getNextIndexString(String paramString)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getNextIndexString: begin... " + paramString, 6);
    String str = null;
    try
    {
      str = getNextIndexString(null, paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.log("DBUtil.getNextIndexString exception: " + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("DBUtil.getNextIndexString: " + paramString + "=" + str, 6);
    return str;
  }
  
  public static String getNextIndexString(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str = null;
    try
    {
      if (ku == null) {
        initIndexing();
      }
      IndexEntry localIndexEntry = (IndexEntry)ku.get(paramString.toLowerCase());
      str = localIndexEntry.getNextIndexString();
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new FFSException("DBUtil.getNextIndexString: No such index name: " + paramString);
    }
    catch (Exception localException)
    {
      throw new FFSException(FFSDebug.stackTrace(localException));
    }
    return str;
  }
  
  public static String getNextIndexStringNotFromCache(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = null;
    String str2 = "SELECT IndexString FROM BPW_InternalIndices WHERE IndexName = ? FOR UPDATE";
    String str3 = "UPDATE BPW_InternalIndices SET IndexString =? WHERE IndexName= ?";
    Object[] arrayOfObject1 = { paramString };
    String str4 = paramFFSConnectionHolder.conn.getDatabaseType();
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str5;
      Object localObject1;
      Object localObject2;
      if ((str4.startsWith("DB2")) || (str4.startsWith("ORA")))
      {
        str5 = null;
        localFFSResultSet = openResultSet(paramFFSConnectionHolder, str2, arrayOfObject1);
        if (localFFSResultSet.getNextRow()) {
          str5 = localFFSResultSet.getColumnString(1);
        } else {
          throw new FFSException("Invalid index name: " + paramString);
        }
        localObject1 = new BigInteger(str5);
        localObject1 = ((BigInteger)localObject1).add(new BigInteger("1"));
        if (((BigInteger)localObject1).bitLength() > 104) {
          localObject1 = new BigInteger("1");
        }
        str1 = ((BigInteger)localObject1).toString();
        localObject2 = new Object[] { str1, paramString };
        int i = executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject2);
      }
      else
      {
        Object localObject3;
        if (str4.startsWith("MSSQL"))
        {
          str5 = null;
          localObject1 = "SELECT IndexString FROM BPW_InternalIndices WITH (XLOCK) WHERE IndexName = ? ";
          localFFSResultSet = openResultSet(paramFFSConnectionHolder, (String)localObject1, arrayOfObject1);
          if (localFFSResultSet.getNextRow()) {
            str5 = localFFSResultSet.getColumnString(1);
          } else {
            throw new FFSException("Invalid index name: " + paramString);
          }
          localObject2 = new BigInteger(str5);
          localObject2 = ((BigInteger)localObject2).add(new BigInteger("1"));
          if (((BigInteger)localObject2).bitLength() > 104) {
            localObject2 = new BigInteger("1");
          }
          str1 = ((BigInteger)localObject2).toString();
          localObject3 = new Object[] { str1, paramString };
          int j = executeStatement(paramFFSConnectionHolder, str3, (Object[])localObject3);
        }
        else
        {
          str5 = "begin tran";
          executeStatement(paramFFSConnectionHolder, str5, null);
          localObject1 = "SELECT IndexString FROM BPW_InternalIndices HOLDLOCK WHERE IndexName = ? ";
          localObject2 = null;
          localFFSResultSet = openResultSet(paramFFSConnectionHolder, (String)localObject1, arrayOfObject1);
          if (localFFSResultSet.getNextRow()) {
            localObject2 = localFFSResultSet.getColumnString(1);
          } else {
            throw new FFSException("Invalid index name: " + paramString);
          }
          localObject3 = new BigInteger((String)localObject2);
          localObject3 = ((BigInteger)localObject3).add(new BigInteger("1"));
          if (((BigInteger)localObject3).bitLength() > 104) {
            localObject3 = new BigInteger("1");
          }
          str1 = ((BigInteger)localObject3).toString();
          Object[] arrayOfObject2 = { str1, paramString };
          int k = executeStatement(paramFFSConnectionHolder, str3, arrayOfObject2);
          executeStatement(paramFFSConnectionHolder, "commit transaction", null);
        }
      }
    }
    catch (Exception localException)
    {
      throw new FFSException(FFSDebug.stackTrace(localException));
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    return str1;
  }
  
  public static String findNextIndexString(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    String str = null;
    try
    {
      if (ku == null) {
        initIndexing();
      }
      IndexEntry localIndexEntry = (IndexEntry)ku.get(paramString.toLowerCase());
      str = localIndexEntry.getLastAssnStr();
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new Exception("DBUtil.findNextIndexString: No such index name: " + paramString);
    }
    catch (Exception localException)
    {
      throw localException;
    }
    return str;
  }
  
  public static int getIndex(String paramString)
    throws BPWException
  {
    try
    {
      return getIndex(null, paramString);
    }
    catch (Exception localException)
    {
      String str = "*** DBUtil.getIndex failed for index: " + paramString + ": ";
      FFSDebug.log(str + localException.toString());
      throw new BPWException(localException.toString());
    }
  }
  
  public static int getIndex(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("DBUtil.getIndex: indexName=" + paramString, 6);
    int i = 0;
    try
    {
      if (ku == null) {
        initIndexing();
      }
      IndexEntry localIndexEntry = (IndexEntry)ku.get(paramString.toLowerCase());
      i = localIndexEntry.getLastAssnInt();
    }
    catch (NullPointerException localNullPointerException)
    {
      i = -1;
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Failed to get index " + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** Failed to get index: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    return i;
  }
  
  public static FFSConnection getSingleConnection()
    throws Exception
  {
    FFSConnection localFFSConnection = null;
    try
    {
      FFSDBProperties localFFSDBProperties = BPWUtil.getBPWServerDBProperties();
      FFSDebug.log("Creating a new DB connection: " + localFFSDBProperties, 6);
      localFFSConnection = ConnectionFactory.getNewConnection(localFFSDBProperties);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to get single connection: " + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** Fail to get single connection: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    return localFFSConnection;
  }
  
  public static FFSConnection getSingleConnection(FFSDBProperties paramFFSDBProperties)
    throws Exception
  {
    FFSConnection localFFSConnection = null;
    try
    {
      FFSDebug.log("Creating a new DB connection: " + paramFFSDBProperties, 6);
      localFFSConnection = ConnectionFactory.getNewConnection(paramFFSDBProperties);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to get single connection: " + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** Fail to get single connection: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    return localFFSConnection;
  }
  
  public static void freeSingleConnection(FFSConnection paramFFSConnection)
    throws Exception
  {
    if (paramFFSConnection != null) {
      try
      {
        paramFFSConnection.close();
      }
      catch (Exception localException)
      {
        FFSDebug.console("Failed to free a single connection");
        FFSDebug.log("Failed to free single connection: ", FFSConst.LINE_SEPARATOR, FFSDebug.stackTrace(localException), 0);
      }
    }
  }
  
  public static final PropertyConfig getPropertyConfig(FFSProperties paramFFSProperties)
    throws Exception
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1 = null;
    PropertyConfig localPropertyConfig = null;
    FFSProperties localFFSProperties = new FFSProperties();
    localFFSConnectionHolder.conn = getSingleConnection(new FFSDBProperties(paramFFSProperties));
    try
    {
      localFFSProperties = DBPropertyConfig.readPropertyConfig(localFFSConnectionHolder);
      localPropertyConfig = DBPropertyConfig.createPropertyConfig(localFFSProperties);
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      if (localObject1 != null) {
        localObject1.close();
      }
      freeSingleConnection(localFFSConnectionHolder.conn);
    }
    return localPropertyConfig;
  }
  
  public static InstructionType[] getInstructionTypes(FFSProperties paramFFSProperties)
    throws Exception
  {
    Object localObject1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = getSingleConnection(new FFSDBProperties(paramFFSProperties));
    try
    {
      localArrayList = DBInstructionType.readInstructionTypes(localFFSConnectionHolder);
    }
    finally
    {
      freeSingleConnection(localFFSConnectionHolder.conn);
    }
    return (InstructionType[])localArrayList.toArray(new InstructionType[0]);
  }
  
  public static int computeForSrvrTID(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2 - paramInt3;
    int j = paramInt2 / (paramInt3 + 1) * (paramInt3 + 1);
    int k = j <= paramInt3 + 1 ? 0 : j - (paramInt3 + 1);
    int m = i % (paramInt3 + 1);
    int n;
    if (paramInt1 < m) {
      n = paramInt1 + j;
    } else {
      n = paramInt1 + k;
    }
    return n;
  }
  
  public static String getCursor(int paramInt1, int paramInt2)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(Integer.toString(paramInt1));
    String str = String.valueOf(paramInt2);
    int i = 9 - str.length();
    localStringBuffer.append("00000000".substring(0, i));
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
  
  public static String getNextIndexStringWithPadding(String paramString, int paramInt, char paramChar)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getNextIndexStringWithPadding: begin... " + paramString, 6);
    try
    {
      return FFSUtil.padWithChar(getNextIndexString(paramString), paramInt, true, paramChar);
    }
    catch (Exception localException)
    {
      FFSDebug.log(" DBUtil.getNextIndexStringWithPadding exception: " + FFSDebug.stackTrace(localException), 6);
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static String getNextIndexStringWithPadding(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, char paramChar)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getNextIndexStringWithPadding: begin... " + paramString, 6);
    try
    {
      return FFSUtil.padWithChar(getNextIndexString(paramFFSConnectionHolder, paramString), paramInt, true, paramChar);
    }
    catch (Exception localException)
    {
      FFSDebug.log(" DBUtil.getNextIndexStringWithPadding exception: " + FFSDebug.stackTrace(localException), 6);
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static String getNextIndexStringNotFromCacheWithPadding(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt, char paramChar)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getNextIndexStringNotFromCacheWithPadding: begin... " + paramString, 6);
    try
    {
      return FFSUtil.padWithChar(getNextIndexStringNotFromCache(paramFFSConnectionHolder, paramString), paramInt, true, paramChar);
    }
    catch (Exception localException)
    {
      FFSDebug.log(" DBUtil.getNextIndexStringNotFromCacheWithPadding exception: " + FFSDebug.stackTrace(localException), 6);
      throw new BPWException(localException.getMessage());
    }
  }
  
  public static String getNewTransId(String paramString, int paramInt)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getNewTransId: begin... ", paramString, " Length:" + paramInt, 6);
    String str1 = null;
    try
    {
      String str2 = getNextIndexString(paramString);
      FFSDebug.log("DBUtil.getNewTransId: dbIndex from DB:", str2, 6);
      if (str2.length() > paramInt)
      {
        str1 = str2.substring(str2.length() - paramInt);
        FFSDebug.log("DBUtil.getNewTransId: transId", str1, 6);
      }
      else
      {
        str1 = FFSUtil.padWithChar(str2, paramInt, true, '0');
      }
      FFSDebug.log("DBUtil.getNewTransId: transId:", str1, 6);
    }
    catch (Throwable localThrowable)
    {
      String str3 = " DBUtil.getNewTransId exception: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 6);
      throw new BPWException(str3);
    }
    FFSDebug.log("DBUtil.getNewTransId: Done:", 6);
    return str1;
  }
  
  public static String getNewTransId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws BPWException
  {
    FFSDebug.log("DBUtil.getNewTransId: begin... ", paramString, " Length:" + paramInt, 6);
    int i = 0;
    int j = 0;
    int k = 0;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if (paramInt <= 14)
    {
      String str4 = "Specified length must be greater than prefix length 14";
      FFSDebug.log(" DBUtil.getNewTransId: ", str4, 6);
      throw new BPWException(str4);
    }
    try
    {
      k = paramInt - 14;
      FFSDebug.log("DBUtil.getNewTransId: seqDigitsLen:" + k, 6);
      i = Integer.parseInt(getNextIndexString(paramString));
      FFSDebug.log("DBUtil.getNewTransId: indx from DB:" + i, 6);
      j = i % (int)Math.pow(10.0D, k);
      FFSDebug.log("DBUtil.getNewTransId: indxSignificantDigits:" + j, 6);
      str1 = FFSUtil.padWithChar(String.valueOf(j), k, true, '0');
      FFSDebug.log("DBUtil.getNewTransId: seqNum:", str1, 6);
      str2 = getCurrentDateTime();
      str3 = str2 + str1;
      FFSDebug.log("DBUtil.getNewTransId: nextTransId:", str3, 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log(" DBUtil.getNewTransId exception: " + FFSDebug.stackTrace(localException), 6);
      throw new BPWException(localException.getMessage());
    }
    FFSDebug.log("DBUtil.getNewTransId: Done:", 6);
    return str3;
  }
  
  public static String getEffectiveDateForDB(String paramString)
  {
    String str = FFSUtil.getCurrentYear();
    return str.substring(0, 2) + paramString + "00";
  }
  
  public static String getEffectiveDateForMBMsg(String paramString)
  {
    return paramString.substring(2, paramString.length() - 2);
  }
  
  public static String buildStatusQueryString(String paramString)
  {
    String str1 = " AND Status in ( ";
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    int i = 1;
    String str2 = "";
    while (localStringTokenizer.hasMoreTokens()) {
      if (i != 0)
      {
        str2 = str2 + "'" + localStringTokenizer.nextToken().trim() + "'";
        i = 0;
      }
      else
      {
        str2 = str2 + ",'" + localStringTokenizer.nextToken().trim() + "'";
      }
    }
    str1 = str1 + str2.toString() + " ) ";
    return str1;
  }
  
  public static boolean checkStatusQuery(String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",");
    boolean bool = false;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken().trim();
      if (str.equals(paramString2)) {
        bool = true;
      }
    }
    return bool;
  }
  
  public static void setTransactionIsolation(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws Exception
  {
    FFSDebug.log("DBUtil.setTransactionIsolation changing Connection isolation level: " + paramInt, 6);
    String str;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      str = localPropertyConfig.otherProperties.getProperty("BPW.DB.DirtyRead", "false");
      if ("false".equalsIgnoreCase(str)) {
        return;
      }
    }
    catch (Throwable localThrowable1)
    {
      return;
    }
    if ((paramFFSConnectionHolder == null) || (paramFFSConnectionHolder.conn == null) || (paramInt < 0))
    {
      FFSDebug.log("DBUtil.setTransactionIsolation failed. ", "Invalid connection or isolation level", 0);
      return;
    }
    try
    {
      paramFFSConnectionHolder.conn.setTransactionIsolation(paramInt);
    }
    catch (Throwable localThrowable2)
    {
      str = "DBUtil.setTransactionIsolation failed. Error: " + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      localThrowable2.printStackTrace();
      throw new Exception(str);
    }
  }
  
  public static int getTransactionIsolation(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if ((paramFFSConnectionHolder == null) || (paramFFSConnectionHolder.conn == null))
    {
      String str1 = "DBUtil.getTransactionIsolation failed. Invalid connection ";
      FFSDebug.log(str1, 0);
      throw new Exception(str1);
    }
    try
    {
      return paramFFSConnectionHolder.conn.getConnection().getTransactionIsolation();
    }
    catch (Throwable localThrowable)
    {
      String str2 = "DBUtil.getTransactionIsolation failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new Exception(str2);
    }
  }
  
  public static void renewConnectionNoRefresh(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      paramFFSConnectionHolder.conn = localConnectionPool.renewConnectionNoRefresh(paramFFSConnectionHolder.conn);
    }
    catch (Throwable localThrowable)
    {
      String str = "DBUtil.renewConnection failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
  }
  
  public static String getPayday(String paramString1, String paramString2)
    throws FFSException
  {
    String str = "System";
    return getPayday(paramString1, paramString2, str);
  }
  
  public static String getPayday(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "DBUtil.getPayday ";
    FFSDebug.log(str1, " starts, dueDateStr: ", paramString2, ", fiid: ", paramString1, 6);
    try
    {
      int i = BPWUtil.getDateDBFormat(paramString2);
      FFSDebug.log(str1, " dueDateInt: " + i, 6);
      i /= 100;
      i = SmartCalendar.getPayday(paramString1, i, paramString3);
      i *= 100;
      return BPWUtil.getDateBeanFormat(i);
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + ": failed:";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static String getACHPayday(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "DBUtil.getPayday ";
    FFSDebug.log(str1, " starts, dueDateStr: ", paramString2, ", fiid: ", paramString1, 6);
    try
    {
      int i = BPWUtil.getDateDBFormat(paramString2);
      FFSDebug.log(str1, " dueDateInt: " + i, 6);
      i /= 100;
      i = SmartCalendar.getACHPayday(paramString1, i);
      i *= 100;
      return BPWUtil.getDateBeanFormat(i);
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + ": failed:";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static String getNextPayday(String paramString)
    throws FFSException
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      String str1 = localSimpleDateFormat.format(new Date());
      String str2 = getPayday(paramString, str1);
      Date localDate1 = localSimpleDateFormat.parse(str2);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate1);
      localCalendar.add(5, 1);
      Date localDate2 = localCalendar.getTime();
      String str3 = localSimpleDateFormat.format(localDate2);
      return getPayday(paramString, str3);
    }
    catch (Exception localException)
    {
      throw new FFSException(localException, "DBUtil.getNextPayday failed");
    }
  }
  
  public static int getPayday(String paramString, int paramInt)
    throws FFSException
  {
    String str = "System";
    return getPayday(paramString, paramInt, str);
  }
  
  public static int getPayday(String paramString1, int paramInt, String paramString2)
    throws FFSException
  {
    String str1 = "DBUtil.getPayday ";
    try
    {
      paramInt /= 100;
      paramInt = SmartCalendar.getPayday(paramString1, paramInt, paramString2);
      paramInt *= 100;
      return paramInt;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + ": failed:";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static int getACHPayday(String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "DBUtil.getACHPayday ";
    try
    {
      paramInt /= 100;
      paramInt = SmartCalendar.getACHPayday(paramString, paramInt);
      paramInt *= 100;
      return paramInt;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + ": failed:";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static String buildWhereClauseStringColumn(String paramString1, String paramString2)
  {
    return jdMethod_int(paramString1, paramString2, "'");
  }
  
  public static String buildWhereClauseIntColumn(String paramString1, String paramString2)
  {
    return jdMethod_int(paramString1, paramString2, "");
  }
  
  private static String jdMethod_int(String paramString1, String paramString2, String paramString3)
  {
    String str1 = " AND " + paramString2 + "  in ( ";
    int i = 1;
    String str2 = "";
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",");
    while (localStringTokenizer.hasMoreTokens()) {
      if (i == 1)
      {
        str2 = str2 + paramString3 + localStringTokenizer.nextToken().trim() + paramString3;
        i = 0;
      }
      else
      {
        str2 = str2 + "," + paramString3 + localStringTokenizer.nextToken().trim() + paramString3;
      }
    }
    str1 = str1 + str2.toString() + " ) ";
    return str1;
  }
  
  public static void appendStringToCondition(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString1, String paramString2)
  {
    try
    {
      jdMethod_if(paramStringBuffer, paramArrayList, paramString1, paramString2, 1);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public static void appendIntToCondition(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString1, String paramString2)
    throws NumberFormatException
  {
    jdMethod_if(paramStringBuffer, paramArrayList, paramString1, paramString2, 2);
  }
  
  private static void jdMethod_if(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString1, String paramString2, int paramInt)
    throws NumberFormatException
  {
    if (paramString2 == null) {
      return;
    }
    if ((paramInt != 1) && (paramInt != 2)) {
      return;
    }
    if (paramInt == 2) {
      Integer localInteger = new Integer(paramString2);
    }
    paramStringBuffer.append(" AND " + paramString1 + " = ?");
    if (paramInt == 1) {
      paramArrayList.add(paramString2);
    } else if (paramInt == 2) {
      paramArrayList.add(new Integer(paramString2.trim()));
    }
  }
  
  public static void appendStringArrayToCondition(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString, String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
      if (paramArrayOfString.length == 1)
      {
        paramStringBuffer.append(" AND " + paramString + " = ? ");
        paramArrayList.add(paramArrayOfString[0]);
      }
      else
      {
        paramStringBuffer.append(" AND " + paramString + " IN (?");
        paramArrayList.add(paramArrayOfString[0]);
        for (int i = 1; i < paramArrayOfString.length; i++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(paramArrayOfString[i]);
        }
        paramStringBuffer.append(")");
      }
    }
  }
  
  public static void appendStringArrayToCondition(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString1, String paramString2)
  {
    try
    {
      a(paramStringBuffer, paramArrayList, paramString1, paramString2, 1);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public static void appendIntArrayToCondition(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString1, String paramString2)
    throws NumberFormatException
  {
    a(paramStringBuffer, paramArrayList, paramString1, paramString2, 2);
  }
  
  private static void a(StringBuffer paramStringBuffer, ArrayList paramArrayList, String paramString1, String paramString2, int paramInt)
    throws NumberFormatException
  {
    if ((paramString2 == null) || (paramString2.trim().length() == 0)) {
      return;
    }
    if ((paramInt != 1) && (paramInt != 2)) {
      return;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append(" AND " + paramString1 + " IN (");
    int i = 1;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString2.trim(), ",");
    while (localStringTokenizer.hasMoreTokens() == true)
    {
      if (i == 1)
      {
        localStringBuffer.append("?");
        i = 0;
      }
      else
      {
        localStringBuffer.append(",?");
      }
      String str = localStringTokenizer.nextToken().trim();
      if (paramInt == 1) {
        localArrayList.add(str);
      } else if (paramInt == 2) {
        localArrayList.add(new Integer(str));
      }
    }
    localStringBuffer.append(")");
    paramStringBuffer.append(localStringBuffer.toString());
    paramArrayList.addAll(localArrayList);
  }
  
  public static void startCalendars()
  {
    try
    {
      FFSDebug.log("SemiMonthly: init", 2);
      Semimonthly.init("SemiMonthly.txt");
    }
    catch (Throwable localThrowable1)
    {
      localThrowable1.printStackTrace();
      FFSDebug.log("Failed to initialize SemiMonthly. Error: ", FFSDebug.stackTrace(localThrowable1), 0);
      FFSDebug.console("Failed to initialize SemiMonthly. Error: " + FFSDebug.stackTrace(localThrowable1));
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      FFSDebug.log("SmartCalendar: init", 2);
      localFFSConnectionHolder.conn = getConnection();
      String[] arrayOfString = BPWFI.getAllBPWFIIds(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      for (int i = 0; i < arrayOfString.length; i++) {
        try
        {
          FFSDebug.log("BPWCalendar: init for FI: ", arrayOfString[i], 2);
          SmartCalendar.init(arrayOfString[i]);
          FFSDebug.log("Successfully initialized BPWCalendar for FI: ", arrayOfString[i], 2);
        }
        catch (Throwable localThrowable3)
        {
          FFSDebug.log("Failed to initialize BPWCalendarfor FI: ", arrayOfString[i], "'  Error: " + FFSDebug.stackTrace(localThrowable3), 0);
          FFSDebug.console("Failed to initialize BPWCalendar for FI: " + arrayOfString[i] + "'  Error: " + FFSDebug.stackTrace(localThrowable3));
        }
      }
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("Failed to initialize BPWCalendar. Error: ", FFSDebug.stackTrace(localThrowable2), 0);
      FFSDebug.console("Failed to initialize BPWCalendar. Error: " + FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public static String getFIId(TypeUserData paramTypeUserData)
    throws BPWException
  {
    String str = null;
    if (paramTypeUserData != null)
    {
      str = paramTypeUserData._fid;
      FFSDebug.log("User data fiid = " + paramTypeUserData._fid, 6);
    }
    if ((str == null) || (str.trim().length() == 0))
    {
      FFSDebug.log("DBUtil.getFIId failed: FIID in userData is null!");
      throw new BPWException("DBUtil.getFIId failed: FIID in userData is null!");
    }
    return str;
  }
  
  public static void appendToCondition(StringBuffer paramStringBuffer, Object paramObject, String paramString, ArrayList paramArrayList)
  {
    if (paramObject != null)
    {
      paramArrayList.add(paramObject);
      paramStringBuffer.append(paramString);
    }
  }
  
  public static void appendArrayToCondition(StringBuffer paramStringBuffer, Object[] paramArrayOfObject, String paramString, ArrayList paramArrayList)
  {
    if ((paramArrayOfObject != null) && (paramArrayOfObject.length != 0))
    {
      paramStringBuffer.append(paramString).append("?");
      paramArrayList.add(paramArrayOfObject[0]);
      for (int i = 1; i < paramArrayOfObject.length; i++)
      {
        paramStringBuffer.append(",?");
        paramArrayList.add(paramArrayOfObject[i]);
      }
      paramStringBuffer.append(")");
    }
  }
  
  public static String[] removeDupString(String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length != 0))
    {
      HashMap localHashMap = new HashMap();
      for (int i = 0; i < paramArrayOfString.length; i++) {
        localHashMap.put(paramArrayOfString[i], paramArrayOfString[i]);
      }
      return (String[])localHashMap.keySet().toArray(new String[0]);
    }
    return null;
  }
  
  public static int getNextRunDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "DBUtil.getNextRunDate: ";
    Object localObject = null;
    try
    {
      String str2 = BPWUtil.getLeadTimeForCutOff();
      String str3 = FFSUtil.convertDateFormat("yyyy/MM/dd HH:mm", "yyyyMMdd", str2);
      int i = Integer.parseInt(str3);
      int j = SmartCalendar.getACHPayday(paramString1, i);
      if (i != j) {
        str2 = FFSUtil.convertDateFormat("yyyyMMdd", "yyyy/MM/dd HH:mm", "" + j);
      }
      FFSDebug.log(str1 + "LeadTime = " + str2, 6);
      localObject = null;
      SchedulerInfo localSchedulerInfo = BPWUtil.getSchedulerInfo(paramString1, paramString2);
      CutOffInfo[] arrayOfCutOffInfo = null;
      boolean bool = false;
      if (localSchedulerInfo != null)
      {
        FFSDebug.log(str1 + "schInfo.NextRunTime =" + localSchedulerInfo.NextRunTime + ", interval = " + localSchedulerInfo.SchedulerInterval, 6);
        if ((localSchedulerInfo.NextRunTime != null) && (localSchedulerInfo.NextRunTime.compareTo("N/A") != 0))
        {
          String str4 = BPWUtil.getDateInNewFormat(localSchedulerInfo.NextRunTime, "EEE MMM dd HH:mm:ss z yyyy", "yyyy/MM/dd HH:mm");
          if ((str4.compareTo(str2) >= 0) && (SmartCalendar.isACHPayDay(paramString1, str4)))
          {
            localObject = str4;
          }
          else
          {
            InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
            bool = localInstructionType.useCutOffs;
            if (!bool)
            {
              int n = localSchedulerInfo.SchedulerInterval;
              str4 = BPWUtil.addIntervalToDateTime(n, str4, 12);
              FFSDebug.log(str1 + "New nextRunTime =" + str4, 6);
              while ((str4.compareTo(str2) < 0) || (!SmartCalendar.isACHPayDay(paramString1, str4)))
              {
                str4 = BPWUtil.addIntervalToDateTime(n, str4, 12);
                FFSDebug.log(str1 + "New nextRunTime =" + str4, 6);
              }
              if (str4.compareTo(str2) > 0) {
                localObject = str4;
              }
            }
          }
        }
        if ((bool) || (!localSchedulerInfo.SchedulerStatus.equals("DispatcherCompleted")))
        {
          arrayOfCutOffInfo = getCutOffsByFIIDAndInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
          if (arrayOfCutOffInfo != null)
          {
            int k = arrayOfCutOffInfo.length;
            for (m = 0; m < k; m++)
            {
              String str6 = arrayOfCutOffInfo[m].getNextProcessTime();
              FFSDebug.log(str1 + "nextProcessTime =" + str6, 6);
              while ((!SmartCalendar.isACHPayDay(paramString1, str6)) || (str6.compareTo(str2) < 0)) {
                str6 = BPWUtil.addIntervalToDateTime(1, str6, 4);
              }
              if (localObject == null) {
                localObject = str6;
              } else if ((str6.compareTo(localObject) < 0) && (str6.compareTo(str2) >= 0)) {
                localObject = str6;
              }
            }
          }
        }
      }
      if (localObject == null) {
        localObject = str2;
      }
      String str5 = BPWUtil.getDateInNewFormat(localObject, "yyyy/MM/dd HH:mm", "yyyyMMdd");
      int m = Integer.parseInt(str5);
      return m;
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.getMessage());
    }
  }
  
  public static Calendar longToCalendar(long paramLong)
  {
    Date localDate = new Date(paramLong);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return localCalendar;
  }
  
  public static void computeNextCalendar(Calendar paramCalendar, int paramInt, boolean paramBoolean)
    throws Exception
  {
    if (!paramBoolean)
    {
      paramCalendar.add(12, paramInt);
      return;
    }
    switch (paramInt)
    {
    case 10: 
      break;
    case 0: 
      paramCalendar.add(1, 1);
      break;
    case 1: 
      paramCalendar.add(2, 2);
      break;
    case 2: 
      paramCalendar.add(3, 2);
      break;
    case 3: 
      paramCalendar.add(3, 4);
      break;
    case 4: 
      paramCalendar.add(2, 1);
      break;
    case 5: 
      paramCalendar.add(2, 3);
      break;
    case 6: 
      paramCalendar.add(2, 6);
      break;
    case 7: 
      paramCalendar.add(2, 4);
      break;
    case 9: 
      paramCalendar.add(3, 1);
      break;
    case 11: 
      paramCalendar.add(6, 1);
      break;
    case 12: 
      paramCalendar.set(1, 1000);
      paramCalendar.set(2, 0);
      paramCalendar.set(5, 1);
      break;
    case 8: 
      if (Semimonthly.isInitialized() == true)
      {
        int i = paramCalendar.get(2) + 1;
        int j = paramCalendar.get(5);
        int k = i * 100 + j;
        int m = Semimonthly.getNext(k);
        if (m == 0)
        {
          paramCalendar.add(6, 15);
        }
        else
        {
          int n = m / 100;
          int i1 = m % 100;
          paramCalendar.set(2, n - 1);
          paramCalendar.set(5, i1);
          if ((i == 12) && (n == 1)) {
            paramCalendar.add(1, 1);
          }
        }
      }
      else
      {
        paramCalendar.add(6, 15);
      }
      break;
    }
    FFSDebug.log("Computed next calendar = " + calendarToString(paramCalendar), 6);
  }
  
  public static String calendarToString(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    return "" + paramCalendar.get(1) + "/" + (paramCalendar.get(2) + 1) + "/" + paramCalendar.get(5) + " " + paramCalendar.get(11) + ":" + paramCalendar.get(12) + ":" + paramCalendar.get(13);
  }
  
  public static Calendar[] getScheduleRunDates(FFSConnectionHolder paramFFSConnectionHolder, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramString == null) || (paramArrayOfString == null)) {
      return null;
    }
    String str = "DBUtil.getScheduleRunDate:";
    int i = paramArrayOfString.length;
    FFSDebug.log(str + "FIID = " + paramString + ", number of instructions = " + i, 6);
    Calendar[] arrayOfCalendar = new Calendar[i];
    int j = getTransferScheduleInterval();
    try
    {
      Calendar localCalendar1 = getPossibleCalendar(paramFFSConnectionHolder, paramCalendar1, paramCalendar2, paramString, paramArrayOfString[(i - 1)], true);
      Calendar localCalendar2 = null;
      Calendar localCalendar3 = null;
      if (i > 1)
      {
        localCalendar2 = Calendar.getInstance();
        localCalendar2.add(13, j);
        localCalendar3 = (Calendar)localCalendar1.clone();
        localCalendar3.add(13, -1 * j);
        FFSDebug.log(str + "New Upper Cal = " + calendarToString(localCalendar3), 6);
        for (int k = i - 2; k >= 0; k--)
        {
          Calendar localCalendar4 = getPossibleCalendar(paramFFSConnectionHolder, localCalendar2, localCalendar3, paramString, paramArrayOfString[k], false);
          if (localCalendar4.after(localCalendar3))
          {
            localCalendar3 = (Calendar)localCalendar4.clone();
            if (k > 0) {
              localCalendar3.add(13, -1 * j);
            }
          }
        }
        arrayOfCalendar[0] = ((Calendar)localCalendar3.clone());
        FFSDebug.log(str + "Schedule[" + paramArrayOfString[0] + "] = " + calendarToString(arrayOfCalendar[0]), 6);
        for (k = 1; k < i; k++)
        {
          localCalendar3.add(13, j);
          localCalendar3 = getPossibleCalendar(paramFFSConnectionHolder, localCalendar3, localCalendar3, paramString, paramArrayOfString[k], true);
          arrayOfCalendar[k] = ((Calendar)localCalendar3.clone());
          FFSDebug.log(str + "Schedule[" + paramArrayOfString[k] + "] = " + calendarToString(arrayOfCalendar[k]), 6);
        }
      }
      arrayOfCalendar[0] = ((Calendar)localCalendar1.clone());
      FFSDebug.log(str + "Schedule[" + paramArrayOfString[0] + "] = " + calendarToString(arrayOfCalendar[0]), 6);
      return arrayOfCalendar;
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new FFSException(localException.toString());
    }
  }
  
  public static int getTransferScheduleInterval()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = localPropertyConfig.otherProperties.getProperty("bpw.transfer.schedule.interval", "30");
    return Integer.parseInt(str);
  }
  
  public static Calendar getPossibleCalendar(FFSConnectionHolder paramFFSConnectionHolder, Calendar paramCalendar1, Calendar paramCalendar2, String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return paramCalendar1;
    }
    Calendar localCalendar = null;
    InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
    boolean bool = localInstructionType.useCutOffs;
    if (bool) {
      localCalendar = getPossibleCutOffCalendar(paramFFSConnectionHolder, paramCalendar1, paramCalendar2, paramString1, localInstructionType, paramBoolean);
    } else {
      localCalendar = getPossibleScheduleCalendar(paramCalendar1, paramCalendar2, paramString1, localInstructionType, paramBoolean);
    }
    return localCalendar;
  }
  
  public static Calendar getPossibleCutOffCalendar(FFSConnectionHolder paramFFSConnectionHolder, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, InstructionType paramInstructionType, boolean paramBoolean)
    throws Exception
  {
    Calendar localCalendar1 = null;
    CutOffInfo[] arrayOfCutOffInfo = getCutOffsByFIIDAndInstructionType(paramFFSConnectionHolder, paramString, paramInstructionType.InstructionType);
    int i;
    int j;
    if ((arrayOfCutOffInfo != null) && (arrayOfCutOffInfo.length > 0))
    {
      i = arrayOfCutOffInfo.length;
      j = 0;
    }
    while (j < i)
    {
      Calendar localCalendar2 = startTimeFormatToCalendar(arrayOfCutOffInfo[j].getNextProcessTime());
      if (localCalendar2 != null) {
        localCalendar1 = computePossibleCal(paramString, localCalendar1, localCalendar2, arrayOfCutOffInfo[j].getFrequency(), paramCalendar1, paramCalendar2, paramBoolean, true, paramInstructionType);
      }
      j++;
      continue;
      String str = "No CutOff defined for instruction type " + paramInstructionType + " of FIID " + paramString;
      FFSDebug.log(str, 6);
      throw new Exception(str);
    }
    return localCalendar1;
  }
  
  public static Calendar computePossibleCal(String paramString, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt, Calendar paramCalendar3, Calendar paramCalendar4, boolean paramBoolean1, boolean paramBoolean2, InstructionType paramInstructionType)
    throws Exception
  {
    String str = "DBUtil.computePossibleCal: ";
    boolean bool1 = paramInstructionType.processOnHolidays;
    FFSDebug.log(str + " FIID = " + paramString, 6);
    FFSDebug.log(str + "PossibleCal = " + calendarToString(paramCalendar1), 6);
    FFSDebug.log(str + "Schedule = " + calendarToString(paramCalendar2), 6);
    FFSDebug.log(str + "LowerCal = " + calendarToString(paramCalendar3), 6);
    FFSDebug.log(str + "UpperCal = " + calendarToString(paramCalendar4), 6);
    FFSDebug.log(str + "newLower = " + paramBoolean1, 6);
    FFSDebug.log(str + "processOnHolidays = " + bool1, 6);
    if (paramInstructionType.category == null) {
      throw new FFSException("*** DBUtil.computePossibleCal: Category for instruction type " + paramInstructionType.InstructionType + " is null");
    }
    boolean bool2 = false;
    if ((paramInstructionType.category.equals("ExternalTransfer")) && (getTransferIgnoreWeekendRule())) {
      bool2 = true;
    }
    Calendar localCalendar1 = (Calendar)paramCalendar4.clone();
    localCalendar1.add(1, 1);
    if (paramBoolean1) {
      while ((paramCalendar2.before(paramCalendar3)) || ((!bool1) && (!isBusinessDay(paramString, paramCalendar2, bool2))))
      {
        if (paramCalendar2.after(localCalendar1)) {
          throw new FFSException("*** Invalid schedule date for instruction type " + paramInstructionType.InstructionType);
        }
        computeNextCalendar(paramCalendar2, paramInt, paramBoolean2);
      }
    }
    Calendar localCalendar2 = (Calendar)paramCalendar2.clone();
    Calendar localCalendar3 = (Calendar)paramCalendar2.clone();
    while ((localCalendar2.before(paramCalendar4)) || ((!bool1) && (!isBusinessDay(paramString, localCalendar2, bool2))))
    {
      if (paramCalendar2.after(localCalendar1)) {
        throw new FFSException("*** Invalid schedule date for instruction type " + paramInstructionType.InstructionType);
      }
      if (isBusinessDay(paramString, localCalendar2, bool2)) {
        localCalendar3 = (Calendar)localCalendar2.clone();
      }
      computeNextCalendar(localCalendar2, paramInt, paramBoolean2);
    }
    if (isCalendarInRange(localCalendar3, paramCalendar3, paramCalendar4)) {
      paramCalendar2 = (Calendar)localCalendar3.clone();
    } else {
      paramCalendar2 = (Calendar)localCalendar2.clone();
    }
    FFSDebug.log(str + "Calculated scheduleCal = " + calendarToString(paramCalendar2), 6);
    if (paramCalendar1 == null) {
      paramCalendar1 = (Calendar)paramCalendar2.clone();
    }
    if (isCalendarInRange(paramCalendar2, paramCalendar3, paramCalendar4))
    {
      if (((paramBoolean1) && (paramCalendar2.before(paramCalendar1))) || ((!paramBoolean1) && (paramCalendar2.after(paramCalendar1))) || (!isCalendarInRange(paramCalendar1, paramCalendar3, paramCalendar4))) {
        paramCalendar1 = (Calendar)paramCalendar2.clone();
      }
    }
    else if (paramCalendar1.after(paramCalendar2)) {
      paramCalendar1 = (Calendar)paramCalendar2.clone();
    }
    FFSDebug.log(str + "Result PossibleCal = " + calendarToString(paramCalendar1), 6);
    return paramCalendar1;
  }
  
  public static Calendar getPossibleScheduleCalendar(Calendar paramCalendar1, Calendar paramCalendar2, String paramString, InstructionType paramInstructionType, boolean paramBoolean)
    throws Exception
  {
    String str1 = "DBUtil.getPossibleScheduleCalendar:";
    Calendar localCalendar1 = null;
    validateInstructionType(paramInstructionType);
    String str2 = paramInstructionType.SchedulerStartTime;
    String str3 = FFSUtil.getDateString("yyyy/MM/dd HH:mm");
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("hh:mm a", Locale.US);
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("HH:mm");
    Date localDate = localSimpleDateFormat1.parse(str2, new ParsePosition(0));
    str2 = localSimpleDateFormat2.format(localDate);
    str2 = str3.substring(0, 11) + str2;
    FFSDebug.log(str1 + "Scheduler Start Time(DB) = " + str2, 6);
    int i = paramInstructionType.SchedulerInterval;
    while (str2.compareTo(str3) < 0) {
      str2 = BPWUtil.addIntervalToDateTime(i, str2, 12);
    }
    FFSDebug.log(str1 + "Scheduler Start Time(real) = " + str2, 6);
    Calendar localCalendar2 = startTimeFormatToCalendar(str2);
    if (localCalendar2 == null) {
      return (Calendar)paramCalendar1.clone();
    }
    localCalendar1 = computePossibleCal(paramString, localCalendar1, localCalendar2, i, paramCalendar1, paramCalendar2, paramBoolean, false, paramInstructionType);
    return localCalendar1;
  }
  
  public static void validateInstructionType(InstructionType paramInstructionType)
    throws Exception
  {
    String str = null;
    if (paramInstructionType == null) {
      str = "Instruction Type is null";
    }
    if (paramInstructionType.ActiveFlag != 1) {
      str = "Inactive instruction type " + paramInstructionType.InstructionType + " of FIID " + paramInstructionType.FIId;
    }
    if (paramInstructionType.SchedulerStartTime == null) {
      str = "Invalid instruction type: SchedulerStartTime is null";
    }
    if (str != null)
    {
      FFSDebug.log(str, 6);
      throw new Exception(str);
    }
  }
  
  public static boolean isBusinessDay(String paramString, Calendar paramCalendar, boolean paramBoolean)
    throws Exception
  {
    if (paramBoolean)
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      return SmartCalendar.isBusinessDay(paramString, Integer.parseInt(localSimpleDateFormat.format(paramCalendar.getTime())));
    }
    int i = paramCalendar.get(1);
    int j = paramCalendar.get(2) + 1;
    int k = paramCalendar.get(5);
    String str = i + "/" + j + "/" + k + " 00:00";
    return SmartCalendar.isACHPayDay(paramString, str);
  }
  
  public static Calendar startTimeFormatToCalendar(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date localDate = localSimpleDateFormat.parse(paramString, new ParsePosition(0));
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return localCalendar;
  }
  
  public static boolean isCalendarInRange(Calendar paramCalendar1, Calendar paramCalendar2, Calendar paramCalendar3)
  {
    return ((paramCalendar1.equals(paramCalendar2)) || (paramCalendar1.after(paramCalendar2))) && ((paramCalendar1.equals(paramCalendar3)) || (paramCalendar1.before(paramCalendar3)));
  }
  
  public static CutOffInfo[] getCutOffsByFIIDAndInstructionType(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    CutOffInfoList localCutOffInfoList = new CutOffInfoList();
    localCutOffInfoList.setFIId(paramString1);
    localCutOffInfoList.setInstructionType(paramString2);
    String[] arrayOfString = { "ACTIVE" };
    localCutOffInfoList.setStatusList(arrayOfString);
    localCutOffInfoList = DBInstructionType.getCutOffListByFIIDInstStatus(paramFFSConnectionHolder, localCutOffInfoList);
    return localCutOffInfoList.getCutOffInfoList();
  }
  
  public static String getProcessDay(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str = new String(paramString4);
    InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
    if (!localInstructionType.processOnHolidays) {
      str = getPayday(paramString1, paramString4, paramString3);
    }
    return str;
  }
  
  public static int getProcessDay(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt)
    throws FFSException
  {
    int i = paramInt;
    InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
    if (!localInstructionType.processOnHolidays) {
      i = getPayday(paramString1, paramInt, paramString3);
    }
    return i;
  }
  
  public static String getProcessDay(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str = new String(paramString3);
    InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
    if (!localInstructionType.processOnHolidays) {
      str = getPayday(paramString1, paramString3);
    }
    return str;
  }
  
  public static int getProcessDay(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    int i = paramInt;
    InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramString1, paramString2);
    if (!localInstructionType.processOnHolidays) {
      i = getPayday(paramString1, paramInt);
    }
    return i;
  }
  
  public static int getNextScheduleRunDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.add(11, 23);
    localCalendar2.add(12, 59);
    String[] arrayOfString = { paramString2 };
    Calendar[] arrayOfCalendar = getScheduleRunDates(paramFFSConnectionHolder, localCalendar1, localCalendar2, paramString1, arrayOfString);
    Calendar localCalendar3 = arrayOfCalendar[0];
    return BPWUtil.calendarToDueDateFormatInt(localCalendar3);
  }
  
  public static boolean getTransferIgnoreWeekendRule()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = localPropertyConfig.otherProperties.getProperty("bpw.transfer.ignore.weekend.rule", "N").trim();
    return (str != null) && ((str.equalsIgnoreCase("Y")) || (str.equalsIgnoreCase("true")));
  }
  
  public static ArrayList arrayStringToArrayList(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      return null;
    }
    ArrayList localArrayList1 = new ArrayList();
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add(paramArrayOfString[i]);
      localArrayList1.add(localArrayList2);
    }
    return localArrayList1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.DBUtil
 * JD-Core Version:    0.7.0.1
 */