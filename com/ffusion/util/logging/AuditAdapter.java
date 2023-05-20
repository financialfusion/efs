package com.ffusion.util.logging;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.enums.UserAssignedAmount;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;

public class AuditAdapter
{
  private static final int a = 100;
  protected static String poolName = null;
  private static final String jdField_for = "insert into AUDIT_LOG (TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE,TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID,TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE,USER_ID_INT,AGENT_ID_INT, PRIMARY_USER_ID, TO_AMOUNT, TO_AMOUNT_CURRENCY, USER_ASSIGNED_AMOUNT ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String jdField_if = "LOG_LANGUAGE_LIST";
  private static final String jdField_do = "LOG_LANGUAGE";
  protected static String dbType = null;
  protected static String logDirectory = null;
  protected static ArrayList logLanguages = new ArrayList();
  protected static Object locker = new Object();
  
  public static synchronized void initialize(Properties paramProperties, String paramString)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new Locale("en", "US"));
    initialize(paramProperties, paramString, localArrayList);
  }
  
  public static synchronized void initialize(Properties paramProperties, String paramString, ArrayList paramArrayList)
    throws Exception
  {
    dbType = paramProperties.getProperty("ConnectionType");
    if (poolName == null)
    {
      poolName = ConnectionPool.init(paramProperties);
      if (poolName == null) {
        throw new Exception("AuditAdapter:Unable to create DB connection pool");
      }
    }
    logDirectory = paramString;
    logLanguages = paramArrayList;
  }
  
  public static void writeRecords(LinkedList paramLinkedList)
  {
    long l = System.currentTimeMillis();
    synchronized (locker)
    {
      String str = "AuditAdapter.writeRecords";
      Connection localConnection = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      AuditLogRecord localAuditLogRecord = null;
      LinkedList localLinkedList = new LinkedList();
      try
      {
        localConnection = DBUtil.getConnection(poolName, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into AUDIT_LOG (TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE,TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID,TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE,USER_ID_INT,AGENT_ID_INT, PRIMARY_USER_ID, TO_AMOUNT, TO_AMOUNT_CURRENCY, USER_ASSIGNED_AMOUNT ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        while (paramLinkedList.size() > 0)
        {
          for (int i = 0; (i < 100) && (paramLinkedList.size() > 0); i++)
          {
            try
            {
              localAuditLogRecord = (AuditLogRecord)paramLinkedList.removeLast();
            }
            catch (NoSuchElementException localNoSuchElementException2)
            {
              continue;
            }
            localLinkedList.add(localAuditLogRecord);
            a(localPreparedStatement, localAuditLogRecord);
            localPreparedStatement.addBatch();
          }
          int[] arrayOfInt = DBUtil.executeBatch(localPreparedStatement, "insert into AUDIT_LOG (TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE,TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID,TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE,USER_ID_INT,AGENT_ID_INT, PRIMARY_USER_ID, TO_AMOUNT, TO_AMOUNT_CURRENCY, USER_ASSIGNED_AMOUNT ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
          localPreparedStatement.clearBatch();
          for (int j = 0; j < arrayOfInt.length; j++)
          {
            Object localObject1 = localLinkedList.removeFirst();
            int k = arrayOfInt[j];
            if ((k < 1) && (k != -2))
            {
              DebugLog.log(Level.SEVERE, "AuditAdapter.writeRecords (ERROR=" + k + ")");
              localLinkedList.add(localObject1);
            }
          }
          if (localLinkedList.size() > 0) {
            writeRecordsToDisk(localLinkedList);
          }
        }
      }
      catch (NoSuchElementException localNoSuchElementException1)
      {
        writeRecordsToDisk(paramLinkedList);
        writeRecordsToDisk(localLinkedList);
        DebugLog.log(Level.SEVERE, str + ": " + localNoSuchElementException1.toString());
        DebugLog.throwing(str, localNoSuchElementException1);
      }
      catch (Throwable localThrowable)
      {
        writeRecordsToDisk(paramLinkedList);
        writeRecordsToDisk(localLinkedList);
        DebugLog.log(Level.SEVERE, str + ": " + localThrowable.toString());
        DebugLog.throwing(str, localThrowable);
      }
      finally
      {
        DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
        PerfLog.log(str, l, true);
      }
    }
  }
  
  public static void writeRecords(Connection paramConnection, LinkedList paramLinkedList)
    throws SQLException, NoSuchElementException
  {
    String str = "AuditAdapter.writeRecords";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    AuditLogRecord localAuditLogRecord = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into AUDIT_LOG (TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE,TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID,TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE,USER_ID_INT,AGENT_ID_INT, PRIMARY_USER_ID, TO_AMOUNT, TO_AMOUNT_CURRENCY, USER_ASSIGNED_AMOUNT ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      while (paramLinkedList.size() > 0) {
        for (int i = 0; (i < 100) && (paramLinkedList.size() > 0); i++)
        {
          localAuditLogRecord = (AuditLogRecord)paramLinkedList.removeFirst();
          a(localPreparedStatement, localAuditLogRecord);
          int j = DBUtil.executeUpdate(localPreparedStatement, "insert into AUDIT_LOG (TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE,TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID,TO_ACCT_RTGNUM,FROM_ACCT_ID,FROM_ACCT_RTGNUM,MODULE,USER_ID_INT,AGENT_ID_INT, PRIMARY_USER_ID, TO_AMOUNT, TO_AMOUNT_CURRENCY, USER_ASSIGNED_AMOUNT ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
          if (j < 1) {
            DebugLog.log(Level.SEVERE, "AuditAdapter.writeRecords Error.  rowCount=" + j + "Tracking ID=" + localAuditLogRecord.getTranID());
          }
        }
      }
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localNoSuchElementException.toString());
      throw localNoSuchElementException;
    }
    catch (SQLException localSQLException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localSQLException.toString());
      throw localSQLException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localException.toString());
      DebugLog.throwing(str, localException);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void a(PreparedStatement paramPreparedStatement, AuditLogRecord paramAuditLogRecord)
    throws SQLException
  {
    paramPreparedStatement.setString(1, paramAuditLogRecord.getTranID());
    if ((paramAuditLogRecord.getUserID() == null) || (paramAuditLogRecord.getUserID().equals(""))) {
      paramPreparedStatement.setString(2, " ");
    } else {
      paramPreparedStatement.setString(2, paramAuditLogRecord.getUserID());
    }
    paramPreparedStatement.setString(3, paramAuditLogRecord.getAgentID());
    paramPreparedStatement.setString(4, paramAuditLogRecord.getAgentType());
    String str = null;
    if (paramAuditLogRecord.getLocalizableMessage() == null) {
      str = paramAuditLogRecord.getMessage();
    } else {
      str = (String)paramAuditLogRecord.getLocalizableMessage().localize((Locale)logLanguages.get(0));
    }
    if (str.length() > 1000) {
      str = str.substring(0, 999);
    }
    paramPreparedStatement.setString(5, str);
    paramPreparedStatement.setTimestamp(6, new Timestamp(paramAuditLogRecord.getTimeInMillis()));
    paramPreparedStatement.setInt(7, paramAuditLogRecord.getTranType());
    if (paramAuditLogRecord.getBusinessID() == 0) {
      paramPreparedStatement.setNull(8, 4);
    } else {
      paramPreparedStatement.setInt(8, paramAuditLogRecord.getBusinessID());
    }
    if (paramAuditLogRecord.getAmount() == null)
    {
      paramPreparedStatement.setNull(9, 3);
    }
    else
    {
      localObject = paramAuditLogRecord.getAmountValue();
      localObject = ((BigDecimal)localObject).setScale(3, 0);
      paramPreparedStatement.setBigDecimal(9, (BigDecimal)localObject);
    }
    paramPreparedStatement.setString(10, paramAuditLogRecord.getCurrencyCode());
    paramPreparedStatement.setString(11, paramAuditLogRecord.getSrvrTid());
    paramPreparedStatement.setString(12, paramAuditLogRecord.getState());
    paramPreparedStatement.setString(13, paramAuditLogRecord.getToAcctID());
    paramPreparedStatement.setString(14, paramAuditLogRecord.getToAcctRtgNum());
    paramPreparedStatement.setString(15, paramAuditLogRecord.getFromAcctID());
    paramPreparedStatement.setString(16, paramAuditLogRecord.getFromAcctRtgNum());
    paramPreparedStatement.setInt(17, paramAuditLogRecord.getModule());
    Object localObject = null;
    try
    {
      localObject = Integer.valueOf(paramAuditLogRecord.getUserID());
    }
    catch (NumberFormatException localNumberFormatException1) {}
    if (localObject == null) {
      paramPreparedStatement.setNull(18, 4);
    } else {
      paramPreparedStatement.setInt(18, ((Integer)localObject).intValue());
    }
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(paramAuditLogRecord.getAgentID());
    }
    catch (NumberFormatException localNumberFormatException2) {}
    if (localInteger == null) {
      paramPreparedStatement.setNull(19, 4);
    } else {
      paramPreparedStatement.setInt(19, localInteger.intValue());
    }
    paramPreparedStatement.setInt(20, paramAuditLogRecord.getPrimaryUserID());
    if (paramAuditLogRecord.getToAmount() == null)
    {
      paramPreparedStatement.setNull(21, 3);
    }
    else
    {
      BigDecimal localBigDecimal = paramAuditLogRecord.getToAmountValue();
      paramPreparedStatement.setBigDecimal(21, localBigDecimal.setScale(3, 0));
    }
    paramPreparedStatement.setString(22, paramAuditLogRecord.getToAmountCurrency());
    if (paramAuditLogRecord.getUserAssignedAmount() == null) {
      paramPreparedStatement.setNull(23, 4);
    } else {
      paramPreparedStatement.setInt(23, paramAuditLogRecord.getUserAssignedAmount().getValue());
    }
  }
  
  public static void writeRecordsToDisk(LinkedList paramLinkedList)
  {
    String str = "AuditAdapter.writeRecordsToDisk";
    FileOutputStream localFileOutputStream = null;
    ObjectOutputStream localObjectOutputStream = null;
    try
    {
      File localFile = File.createTempFile("AUDIT", ".LOG", new File(logDirectory));
      if (localFile != null)
      {
        localFileOutputStream = new FileOutputStream(localFile);
        localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
        localObjectOutputStream.writeObject(paramLinkedList);
        localObjectOutputStream.flush();
        localFileOutputStream.close();
      }
      return;
    }
    catch (IOException localIOException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localIOException.toString());
    }
    finally
    {
      while (paramLinkedList.size() > 0) {
        paramLinkedList.removeFirst();
      }
    }
  }
  
  public static void readAndWriteRecords()
  {
    String str = "AuditAdapter.readAndWriteRecords";
    LinkedList localLinkedList = new LinkedList();
    try
    {
      File localFile1 = new File(logDirectory);
      File[] arrayOfFile = localFile1.listFiles(new FilenameFilter()
      {
        public boolean accept(File paramAnonymousFile, String paramAnonymousString)
        {
          if (!paramAnonymousString.trim().endsWith(".LOG")) {
            return false;
          }
          File localFile = new File(paramAnonymousFile, paramAnonymousString);
          if (!localFile.isFile()) {
            return false;
          }
          return localFile.canRead();
        }
      });
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        File localFile2 = arrayOfFile[i];
        DebugLog.log(Level.SEVERE, "Loading Audit Log file: " + localFile2.getName());
        FileInputStream localFileInputStream = new FileInputStream(localFile2);
        ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
        localLinkedList = (LinkedList)localObjectInputStream.readObject();
        for (int j = 0; j < localLinkedList.size(); j++)
        {
          AuditLogRecord localAuditLogRecord = (AuditLogRecord)localLinkedList.get(j);
          DebugLog.log(Level.SEVERE, "Loading Audit Log Record: " + localAuditLogRecord.toString());
        }
        writeRecords(localLinkedList);
        localObjectInputStream.close();
        localFileInputStream.close();
      }
    }
    catch (IOException localIOException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localIOException.toString());
      localLinkedList = null;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localException.toString());
      localLinkedList = null;
    }
  }
  
  private static void a(int paramInt1, int paramInt2) {}
  
  protected static String getDBType()
  {
    return dbType;
  }
  
  public static void setLogLanguages(HashMap paramHashMap)
  {
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap = (HashMap)paramHashMap.get("LOG_LANGUAGE_LIST");
    if (localHashMap != null)
    {
      ArrayList localArrayList2 = (ArrayList)localHashMap.get("LOG_LANGUAGE");
      if (localArrayList2 != null) {
        for (int i = 0; i < localArrayList2.size(); i++)
        {
          String str = (String)localArrayList2.get(i);
          int j = str.indexOf("_");
          if (j == -1) {
            DebugLog.log(Level.WARNING, "Unable to parse the language " + str);
          } else {
            localArrayList1.add(new Locale(str.substring(0, j), str.substring(j + 1, str.length())));
          }
        }
      }
    }
    logLanguages = localArrayList1;
  }
  
  public static ArrayList getLogLanguages()
  {
    return logLanguages;
  }
  
  static
  {
    logLanguages.add(new Locale("en", "US"));
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditAdapter
 * JD-Core Version:    0.7.0.1
 */