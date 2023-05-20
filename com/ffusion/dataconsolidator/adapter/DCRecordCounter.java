package com.ffusion.dataconsolidator.adapter;

import com.ffusion.util.MapUtil;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class DCRecordCounter
{
  protected static final int TYPE_ACCOUNT = 1;
  protected static final int TYPE_LOCKBOX = 2;
  protected static final int TYPE_EXTENDABEAN = 3;
  protected static final int EXTENDABEAN_ID = 0;
  protected static final String ACCT_TRANSACTION_INDEX = "ACCT_TRANSACTION_INDEX";
  protected static final String LOCKBOX_CREDITITEM_INDEX = "LOCKBOX_CREDITITEM_INDEX";
  protected static final String LOCKBOX_TRANSACTION_INDEX = "LOCKBOX_TRANSACTION_INDEX";
  protected static final String DISBURSEMENT_TRANSACTION_INDEX = "DISBURSEMENT_TRANSACTION_INDEX";
  protected static final String EXTENDABEAN_INDEX = "DCExtendABeanIndex";
  private static final String a = "INSERT INTO DC_RecordCounter( DCObjectType, DCObjectID, CounterName, NextIndex, DataClassification ) VALUES( ?, ?, ?, ?, ? )";
  private static final String jdField_if = "UPDATE DC_RecordCounter SET NextIndex=NextIndex+1 WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_int = "UPDATE DC_RecordCounter SET NextIndex=? WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_for = "DELETE FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_char = "SELECT NextIndex FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_do = "SELECT * FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_try = "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber=? AND acc.DCAccountID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_new = "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber IS NULL AND acc.DCAccountID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND DataClassification=? ";
  private static final String jdField_case = "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc, DC_Lockbox lockbox WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber=? AND acc.DCAccountID = lockbox.DCAccountID AND lockbox.DCLockboxID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND lockbox.LockboxNumber=? AND DataClassification = ?";
  private static final String jdField_byte = "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc, DC_Lockbox lockbox WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber IS NULL AND acc.DCAccountID = lockbox.DCAccountID AND lockbox.DCLockboxID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND lockbox.LockboxNumber=? AND DataClassification=? ";
  
  protected static long getIndex(Connection paramConnection, int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str = null;
      if ((paramInt1 == 3) && (paramInt2 == 0) && (paramString.equals("DCExtendABeanIndex"))) {
        str = "P";
      } else {
        str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT NextIndex FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString);
      localPreparedStatement.setString(4, str);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT NextIndex FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      if (localResultSet.next())
      {
        long l1 = localResultSet.getLong(1);
        long l2 = l1;
        return l2;
      }
      throw new DCException("Error occurred while retrieving index.");
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static long getIndex(Connection paramConnection, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramString3 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber=? AND acc.DCAccountID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND DataClassification=? ");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setString(3, paramString3);
        localPreparedStatement.setInt(4, paramInt);
        localPreparedStatement.setString(5, paramString4);
        localPreparedStatement.setString(6, MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P"));
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber=? AND acc.DCAccountID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND DataClassification=? ");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber IS NULL AND acc.DCAccountID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND DataClassification=? ");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setInt(3, paramInt);
        localPreparedStatement.setString(4, paramString4);
        localPreparedStatement.setString(5, MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P"));
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber IS NULL AND acc.DCAccountID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND DataClassification=? ");
      }
      if (localResultSet.next())
      {
        l1 = localResultSet.getLong(1);
        long l2 = l1;
        return l2;
      }
      long l1 = -1L;
      return l1;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static long getIndex(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramString3 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc, DC_Lockbox lockbox WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber=? AND acc.DCAccountID = lockbox.DCAccountID AND lockbox.DCLockboxID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND lockbox.LockboxNumber=? AND DataClassification = ?");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setString(3, paramString3);
        localPreparedStatement.setInt(4, paramInt);
        localPreparedStatement.setString(5, paramString5);
        localPreparedStatement.setString(6, paramString4);
        localPreparedStatement.setString(7, MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P"));
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc, DC_Lockbox lockbox WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber=? AND acc.DCAccountID = lockbox.DCAccountID AND lockbox.DCLockboxID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND lockbox.LockboxNumber=? AND DataClassification = ?");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc, DC_Lockbox lockbox WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber IS NULL AND acc.DCAccountID = lockbox.DCAccountID AND lockbox.DCLockboxID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND lockbox.LockboxNumber=? AND DataClassification=? ");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setInt(3, paramInt);
        localPreparedStatement.setString(4, paramString5);
        localPreparedStatement.setString(5, paramString4);
        localPreparedStatement.setString(6, MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P"));
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT NextIndex FROM DC_RecordCounter counter, DC_Account acc, DC_Lockbox lockbox WHERE acc.AccountID=? AND acc.BankID=? AND acc.RoutingNumber IS NULL AND acc.DCAccountID = lockbox.DCAccountID AND lockbox.DCLockboxID = counter.DCObjectID AND DCObjectType=? AND CounterName=? AND lockbox.LockboxNumber=? AND DataClassification=? ");
      }
      if (localResultSet.next())
      {
        l1 = localResultSet.getLong(1);
        long l2 = l1;
        return l2;
      }
      long l1 = -1L;
      return l1;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static void addNewCounter(Connection paramConnection, int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_RecordCounter( DCObjectType, DCObjectID, CounterName, NextIndex, DataClassification ) VALUES( ?, ?, ?, ?, ? )");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString);
      localPreparedStatement.setLong(4, 1L);
      localPreparedStatement.setString(5, MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P"));
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_RecordCounter( DCObjectType, DCObjectID, CounterName, NextIndex, DataClassification ) VALUES( ?, ?, ?, ?, ? )");
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void deleteCounter(Connection paramConnection, int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    if ((paramInt1 == 3) && (paramInt2 == 0) && (paramString.equals("DCExtendABeanIndex"))) {
      str = "P";
    } else {
      str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "DELETE FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString);
      localPreparedStatement.setString(4, str);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void incrementIndex(Connection paramConnection, int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    if ((paramInt1 == 3) && (paramInt2 == 0) && (paramString.equals("DCExtendABeanIndex"))) {
      str = "P";
    } else {
      str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_RecordCounter SET NextIndex=NextIndex+1 WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString);
      localPreparedStatement.setString(4, str);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_RecordCounter SET NextIndex=NextIndex+1 WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void updateIndex(Connection paramConnection, HashMap paramHashMap1, int paramInt, String paramString, HashMap paramHashMap2)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    if ((paramInt == 3) && (paramString.equals("DCExtendABeanIndex"))) {
      str = "P";
    } else {
      str = MapUtil.getStringValue(paramHashMap2, "DATA_CLASSIFICATION", "P");
    }
    int i = DCAdapter.getBatchSize(paramHashMap2);
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_RecordCounter SET NextIndex=? WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      Iterator localIterator = paramHashMap1.keySet().iterator();
      while (localIterator.hasNext())
      {
        for (int j = 0; (j < i) && (localIterator.hasNext()); j++)
        {
          Integer localInteger = (Integer)localIterator.next();
          Long localLong = (Long)paramHashMap1.get(localInteger);
          localPreparedStatement.setLong(1, localLong.longValue());
          localPreparedStatement.setInt(2, paramInt);
          localPreparedStatement.setInt(3, localInteger.intValue());
          localPreparedStatement.setString(4, paramString);
          localPreparedStatement.setString(5, str);
          localPreparedStatement.addBatch();
        }
        localPreparedStatement.executeBatch();
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static long getNextIndex(Connection paramConnection, int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    long l1 = 0L;
    String str = null;
    if ((paramInt1 == 3) && (paramString.equals("DCExtendABeanIndex"))) {
      str = "P";
    } else {
      str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    }
    try
    {
      localPreparedStatement1 = DCAdapter.getStatement(paramConnection, "UPDATE DC_RecordCounter SET NextIndex=NextIndex+1 WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement2 = DCAdapter.getStatement(paramConnection, "SELECT NextIndex FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement1.setInt(1, paramInt1);
      localPreparedStatement1.setInt(2, paramInt2);
      localPreparedStatement1.setString(3, paramString);
      localPreparedStatement1.setString(4, str);
      DBUtil.executeUpdate(localPreparedStatement1, "UPDATE DC_RecordCounter SET NextIndex=NextIndex+1 WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement2.setInt(1, paramInt1);
      localPreparedStatement2.setInt(2, paramInt2);
      localPreparedStatement2.setString(3, paramString);
      localPreparedStatement2.setString(4, str);
      localResultSet = DBUtil.executeQuery(localPreparedStatement2, "SELECT NextIndex FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      if (localResultSet.next()) {
        l1 = localResultSet.getLong(1);
      } else {
        throw new DCException("Error occurred while retrieving index.");
      }
      long l2 = l1;
      return l2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get next index value: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 303;
      throw new DCException(i, "Failed to get next index value.", localException);
    }
    finally
    {
      localPreparedStatement1 = null;
      localPreparedStatement2 = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static boolean counterExists(Connection paramConnection, int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    if ((paramInt1 == 3) && (paramString.equals("DCExtendABeanIndex"))) {
      str = "P";
    } else {
      str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT * FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString);
      localPreparedStatement.setString(4, str);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM DC_RecordCounter WHERE DCObjectType=? AND DCObjectID=? AND CounterName=? AND DataClassification=? ");
      if (localResultSet.next())
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
      return bool;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to check the existance of a record counter: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 311;
      throw new DCException(i, "Failed to check the existance of a record counter.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCRecordCounter
 * JD-Core Version:    0.7.0.1
 */