package com.ffusion.util.filemonitor;

import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.logging.DebugLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;

public class FMLogAdapter
{
  public static final String STR_BATCH_SIZE = "BATCH_SIZE";
  private static final int jdField_int = 255;
  private static final String jdField_for = "INSERT INTO FM_LOG (LOGDATE, FILETYPE, HOSTNAME, FILENAME, ACTIVITYTYPE, FROMSYSTEM, TOSYSTEM, STATUS, COMMENTS)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? )";
  private static final String a = "DELETE FROM FM_LOG WHERE LOGDATE <= ?";
  protected static String _poolName = null;
  protected static String _logDirectory = null;
  protected static int _batchSize = 255;
  private static final String jdField_if = "LOG_LANGUAGE_LIST";
  private static final String jdField_do = "LOG_LANGUAGE";
  protected static ArrayList _logLanguages = new ArrayList();
  
  public static synchronized void initialize(Properties paramProperties, String paramString)
    throws FMException
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new Locale("en", "US"));
    initialize(paramProperties, paramString, localArrayList);
  }
  
  public static synchronized void initialize(Properties paramProperties, String paramString, ArrayList paramArrayList)
    throws FMException
  {
    String str = "FMLogAdapter.initialize";
    FMException localFMException;
    if (_poolName == null)
    {
      try
      {
        _poolName = ConnectionPool.init(paramProperties);
      }
      catch (PoolException localPoolException)
      {
        localFMException = new FMException("Failed to initialize connection pool", localPoolException, 8);
        DebugLog.throwing(str, localFMException);
        throw localFMException;
      }
      if (_poolName == null)
      {
        localObject = new FMException("FMLogAdapter:Unable to create DB connection pool", 8);
        DebugLog.throwing(str, (Throwable)localObject);
        throw ((Throwable)localObject);
      }
    }
    Object localObject = paramProperties.getProperty("BATCH_SIZE");
    if ((localObject != null) || (((String)localObject).length() > 0)) {
      try
      {
        _batchSize = Integer.parseInt((String)localObject);
        if (_batchSize <= 0)
        {
          localFMException = new FMException("Error from file monitor log database properties: Batch size must be positive. Please make sure the value for BATCH_SIZE in the configuration is set properly.");
          localFMException.setCode(8);
          DebugLog.throwing(str, localFMException);
          throw localFMException;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.INFO, "FMLogAdapter.initialize: failed to parse batch size value. Default value adopted.");
        _batchSize = 255;
      }
    }
    _logDirectory = paramString;
    _logLanguages = paramArrayList;
  }
  
  public static void writeRecords(LinkedList paramLinkedList)
  {
    if ((paramLinkedList == null) || (paramLinkedList.isEmpty())) {
      return;
    }
    String str = "FMLogAdapter.writeRecords";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    FMLogRecord localFMLogRecord = null;
    LinkedList localLinkedList = new LinkedList();
    try
    {
      localConnection = DBUtil.getConnection(_poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO FM_LOG (LOGDATE, FILETYPE, HOSTNAME, FILENAME, ACTIVITYTYPE, FROMSYSTEM, TOSYSTEM, STATUS, COMMENTS)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? )");
      while (paramLinkedList.size() > 0)
      {
        for (int i = 0; (i < _batchSize) && (paramLinkedList.size() > 0); i++)
        {
          localFMLogRecord = (FMLogRecord)paramLinkedList.removeFirst();
          localLinkedList.add(localFMLogRecord);
          a(localPreparedStatement, localFMLogRecord);
          localPreparedStatement.addBatch();
        }
        int[] arrayOfInt = localPreparedStatement.executeBatch();
        localPreparedStatement.clearBatch();
        for (int j = 0; j < arrayOfInt.length; j++)
        {
          Object localObject1 = localLinkedList.removeFirst();
          int k = arrayOfInt[j];
          if ((k < 1) && (k != -2))
          {
            DebugLog.log(Level.SEVERE, "FMLogAdapter.writeRecords (ERROR=" + k + ")");
            localLinkedList.add(localObject1);
          }
        }
        if (!localLinkedList.isEmpty()) {
          writeRecordsToDisk(localLinkedList);
        }
      }
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
      DebugLog.throwing(str, localNoSuchElementException);
      writeRecordsToDisk(paramLinkedList);
      writeRecordsToDisk(localLinkedList);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      writeRecordsToDisk(paramLinkedList);
      writeRecordsToDisk(localLinkedList);
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          DBUtil.returnConnectionWithException(_poolName, localConnection);
        }
        catch (PoolException localPoolException)
        {
          DebugLog.throwing(str + ": Unable to return connection", localPoolException);
        }
      }
    }
  }
  
  public static void writeRecords(Connection paramConnection, LinkedList paramLinkedList)
    throws SQLException, NoSuchElementException, Throwable
  {
    if ((paramLinkedList == null) || (paramLinkedList.isEmpty())) {
      return;
    }
    String str = "FMLogAdapter.writeRecords";
    PreparedStatement localPreparedStatement = null;
    FMLogRecord localFMLogRecord = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT INTO FM_LOG (LOGDATE, FILETYPE, HOSTNAME, FILENAME, ACTIVITYTYPE, FROMSYSTEM, TOSYSTEM, STATUS, COMMENTS)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? )");
      while (paramLinkedList.size() > 0)
      {
        localFMLogRecord = (FMLogRecord)paramLinkedList.removeFirst();
        a(localPreparedStatement, localFMLogRecord);
        int i = localPreparedStatement.executeUpdate();
        if (i < 1) {
          DebugLog.log(Level.SEVERE, "FMLogAdapter.writeRecords Error.  rowCount=" + i + "File Type=" + localFMLogRecord.getFileType());
        }
      }
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localNoSuchElementException.toString());
      throw localNoSuchElementException;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localThrowable.toString());
      throw localThrowable;
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
  }
  
  private static void a(PreparedStatement paramPreparedStatement, FMLogRecord paramFMLogRecord)
    throws SQLException
  {
    Timestamp localTimestamp = paramFMLogRecord.getMillis() > 0L ? new Timestamp(paramFMLogRecord.getMillis()) : new Timestamp(System.currentTimeMillis());
    paramPreparedStatement.setTimestamp(1, localTimestamp);
    paramPreparedStatement.setString(2, paramFMLogRecord.getFileType());
    paramPreparedStatement.setString(3, paramFMLogRecord.getHostName());
    paramPreparedStatement.setString(4, paramFMLogRecord.getFileName());
    paramPreparedStatement.setString(5, paramFMLogRecord.getActivityType());
    paramPreparedStatement.setString(6, paramFMLogRecord.getFromSystem());
    paramPreparedStatement.setString(7, paramFMLogRecord.getToSystem());
    paramPreparedStatement.setString(8, paramFMLogRecord.getStatus());
    String str = null;
    if (paramFMLogRecord.getLocalizableMessage() == null) {
      str = paramFMLogRecord.getMessage();
    } else {
      str = paramFMLogRecord.getMessage((Locale)_logLanguages.get(0));
    }
    if ((str != null) && (str.length() > 1000)) {
      str = str.substring(0, 999);
    }
    paramPreparedStatement.setString(9, str);
  }
  
  public static void writeRecordsToDisk(LinkedList paramLinkedList)
  {
    String str = "FMLogAdapter.writeRecordsToDisk";
    FileOutputStream localFileOutputStream = null;
    ObjectOutputStream localObjectOutputStream = null;
    try
    {
      File localFile = File.createTempFile("FMLOG", ".LOG", new File(_logDirectory));
      if (localFile != null)
      {
        localFileOutputStream = new FileOutputStream(localFile);
        localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
        localObjectOutputStream.writeObject(paramLinkedList);
        localObjectOutputStream.flush();
      }
    }
    catch (IOException localIOException1)
    {
      DebugLog.throwing(str + ": ", localIOException1);
      DebugLog.log(Level.INFO, str + ": " + _logDirectory);
    }
    finally
    {
      if (localFileOutputStream != null) {
        try
        {
          localFileOutputStream.close();
        }
        catch (IOException localIOException2) {}
      }
    }
  }
  
  public static void readAndWriteRecords()
  {
    String str = "FMLogAdapter.readAndWriteRecords";
    LinkedList localLinkedList = new LinkedList();
    try
    {
      File localFile1 = new File(_logDirectory);
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
        FileInputStream localFileInputStream = new FileInputStream(localFile2);
        ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
        localLinkedList = (LinkedList)localObjectInputStream.readObject();
        localLinkedList.removeFirst();
        writeRecords(localLinkedList);
        localObjectInputStream.close();
        localFileInputStream.close();
      }
    }
    catch (IOException localIOException)
    {
      DebugLog.throwing(str, localIOException);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      DebugLog.throwing(str, localClassNotFoundException);
    }
    finally
    {
      localLinkedList.clear();
    }
  }
  
  public static void cleanup(String paramString, int paramInt)
    throws FMException
  {
    String str1 = "FMLogAdapter.cleanup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str2 = paramString == null ? "DELETE FROM FM_LOG WHERE LOGDATE <= ?" : "DELETE FROM FM_LOG WHERE LOGDATE <= ? AND FILETYPE=?";
    try
    {
      localConnection = DBUtil.getConnection(_poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      Timestamp localTimestamp = new Timestamp(System.currentTimeMillis() - paramInt * 24 * 3600 * 1000);
      localPreparedStatement.setTimestamp(1, localTimestamp);
      if (paramString != null) {
        localPreparedStatement.setString(2, paramString);
      }
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException1)
    {
      try
      {
        if (localConnection != null) {
          localConnection.rollback();
        }
      }
      catch (SQLException localSQLException2) {}
      DebugLog.throwing(str1, localSQLException1);
      throw new FMException(localSQLException1, 4);
    }
    catch (PoolException localPoolException1)
    {
      try
      {
        if (localConnection != null) {
          localConnection.rollback();
        }
      }
      catch (SQLException localSQLException3) {}
      DebugLog.throwing(str1, localPoolException1);
      throw new FMException(localPoolException1, 4);
    }
    catch (Exception localException)
    {
      throw new FMException(localException, 4);
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          DBUtil.returnConnectionWithException(_poolName, localConnection);
        }
        catch (PoolException localPoolException2)
        {
          DebugLog.throwing(str1 + ": Unable to return connection", localPoolException2);
        }
      }
    }
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
    _logLanguages = localArrayList1;
  }
  
  public static ArrayList getLogLanguages()
  {
    return _logLanguages;
  }
  
  static
  {
    _logLanguages.add(new Locale("en", "US"));
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.filemonitor.FMLogAdapter
 * JD-Core Version:    0.7.0.1
 */