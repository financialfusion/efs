package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.IntMap;
import com.ffusion.util.MapUtil;
import com.ffusion.util.Qsort;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.db.DBUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

class DCLBTransactions
{
  private static final String c = "INSERT INTO DC_LBTransactions( DCAccountID, DCTransactionIndex, LockboxNumber, DataDate, DataSource, TransID, TransTypeID, Description, Amount, NumRejectedChecks, RejectedAmount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataSourceFileName, DataSourceFileDate, DataClassification  ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
  private static final String jdField_do = "SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=?  ";
  private static final String a = "SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static final String e = "SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static final String d = "SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static final String jdField_case = "SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID ";
  private static final String b = "SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static final String jdField_void = "SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static final String jdField_long = "SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static final String jdField_goto = "SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static String jdField_if = " a.RoutingNumber ";
  private static String jdField_null = " ORDER BY b.DCTransactionIndex";
  private static String jdField_char = " ORDER BY b.DCTransactionIndex DESC";
  private static final String jdField_int = "SELECT min( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex > ? ";
  private static final String jdField_byte = "SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex < ? ";
  private static final String jdField_else = "SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex >= ? AND b.DCTransactionIndex < ?";
  private static final DecimalFormat jdField_new = new DecimalFormat("00000000000");
  private static final String jdField_for = "00000000001";
  private static final String jdField_try = "99999999999";
  
  protected static void addTransactions(LockboxAccount paramLockboxAccount, LockboxTransactions paramLockboxTransactions, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCUtil.isAccountInfoSufficient(paramLockboxAccount);
    try
    {
      Account localAccount = DCUtil.getAccount(paramLockboxAccount);
      if (!DCAccount.accountExists(localAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(localAccount, paramConnection, paramHashMap);
      }
      i = DCAdapter.getDCAccountID(paramConnection, paramLockboxAccount.getAccountID(), paramLockboxAccount.getBankID(), paramLockboxAccount.getRoutingNumber());
      if (i == -1) {
        throw new DCException(414, "Account not found.");
      }
      HashMap localHashMap = new HashMap();
      Object localObject2;
      for (int j = 0; j < paramLockboxTransactions.size(); j++)
      {
        localObject1 = (LockboxTransaction)paramLockboxTransactions.get(j);
        localObject2 = ((LockboxTransaction)localObject1).getProcessingDate();
        String str = DateFormatUtil.getFormatter("yyyyMMdd").format(((DateTime)localObject2).getTime());
        LockboxTransactions localLockboxTransactions = (LockboxTransactions)localHashMap.get(str);
        if (localLockboxTransactions == null)
        {
          localLockboxTransactions = new LockboxTransactions(paramLockboxTransactions.getLocale());
          localHashMap.put(str, localLockboxTransactions);
        }
        localLockboxTransactions.add(localObject1);
      }
      ArrayList localArrayList = new ArrayList(localHashMap.keySet());
      Qsort.sortStrings(localArrayList, 1);
      Object localObject1 = localArrayList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (LockboxTransactions)localHashMap.get(((Iterator)localObject1).next());
        a(paramConnection, i, paramLockboxAccount, paramInt, (LockboxTransactions)localObject2, paramHashMap);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add lockbox transaction records: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39519;
      throw new DCException(i, "Failed to add lockbox transaction records.", localException);
    }
  }
  
  private static void a(Connection paramConnection, int paramInt1, LockboxAccount paramLockboxAccount, int paramInt2, LockboxTransactions paramLockboxTransactions, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    LockboxTransaction localLockboxTransaction = null;
    long l1 = 0L;
    int i = DCAdapter.getBatchSize(paramHashMap);
    String str1 = null;
    Calendar localCalendar = null;
    String str2 = null;
    if (paramHashMap != null)
    {
      str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
      localCalendar = (Calendar)paramHashMap.get("BAI_FILE_DATE");
      str2 = (String)paramHashMap.get("BAI_FILE_NAME");
    }
    String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    long l2 = -1L;
    if (DCAdapter.isPagingDateBased()) {
      l2 = a(paramConnection, paramLockboxAccount, paramLockboxTransactions, paramHashMap);
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_LBTransactions( DCAccountID, DCTransactionIndex, LockboxNumber, DataDate, DataSource, TransID, TransTypeID, Description, Amount, NumRejectedChecks, RejectedAmount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataSourceFileName, DataSourceFileDate, DataClassification  ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      int j = 0;
      while (j < paramLockboxTransactions.size())
      {
        int k = 0;
        while ((k < i) && (j < paramLockboxTransactions.size()))
        {
          localLockboxTransaction = (LockboxTransaction)paramLockboxTransactions.get(j);
          if (localLockboxTransaction.getTransactionIndex() != 0L) {
            throw new DCException(39520, "Lockbox transaction contains invalid index.");
          }
          if (DCAdapter.isPagingDateBased()) {
            l1 = l2++;
          } else {
            l1 = DCRecordCounter.getNextIndex(paramConnection, 2, paramInt1, "LOCKBOX_TRANSACTION_INDEX", paramHashMap);
          }
          long l3 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localLockboxTransaction.getExtendABeanXML(), paramHashMap);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setLong(2, l1);
          localPreparedStatement.setString(3, localLockboxTransaction.getLockboxNumber());
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, localLockboxTransaction.getProcessingDate());
          localPreparedStatement.setInt(5, paramInt2);
          localPreparedStatement.setInt(6, localLockboxTransaction.getTransactionID());
          localPreparedStatement.setInt(7, localLockboxTransaction.getTransactionType());
          localPreparedStatement.setString(8, localLockboxTransaction.getDescription());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localLockboxTransaction.getAmount());
          localPreparedStatement.setInt(10, localLockboxTransaction.getNumRejectedChecks());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 11, localLockboxTransaction.getRejectedAmount());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 12, localLockboxTransaction.getImmediateFloat());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 13, localLockboxTransaction.getOneDayFloat());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localLockboxTransaction.getTwoDayFloat());
          DCUtil.fillTimestampColumn(localPreparedStatement, 15, localLockboxTransaction.getValueDateTime());
          localPreparedStatement.setString(16, DCAdapter.padRefNum(localLockboxTransaction.getBankReferenceNumber(), DCAdapter.getBankReferenceNumberType()));
          localPreparedStatement.setString(17, DCAdapter.padRefNum(localLockboxTransaction.getCustomerReferenceNumber(), DCAdapter.getCustomerReferenceNumberType()));
          localPreparedStatement.setLong(18, l3);
          localPreparedStatement.setString(19, null);
          localPreparedStatement.setString(20, str1);
          localPreparedStatement.setString(21, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 22, localCalendar);
          localPreparedStatement.setString(23, str3);
          localPreparedStatement.addBatch();
          k++;
          j++;
        }
        localPreparedStatement.executeBatch();
      }
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(LockboxAccount paramLockboxAccount, LockboxTransaction paramLockboxTransaction, ResultSet paramResultSet)
    throws DCException
  {
    try
    {
      paramLockboxTransaction.setAccountID(paramLockboxAccount.getAccountID());
      paramLockboxTransaction.setAccountNumber(paramLockboxAccount.getAccountNumber());
      paramLockboxTransaction.setBankID(paramLockboxAccount.getBankID());
      paramLockboxTransaction.setTransactionIndex(paramResultSet.getLong(1));
      paramLockboxTransaction.setLockboxNumber(paramResultSet.getString(2));
      paramLockboxTransaction.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(3), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setTransactionID(paramResultSet.getInt(4));
      paramLockboxTransaction.setTransactionType(paramResultSet.getInt(5));
      paramLockboxTransaction.setDescription(paramResultSet.getString(6));
      paramLockboxTransaction.setAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setNumRejectedChecks(paramResultSet.getInt(8));
      paramLockboxTransaction.setRejectedAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setImmediateFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(13), paramLockboxAccount.getLocale()));
      paramLockboxTransaction.setBankReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(14), DCAdapter.getBankReferenceNumberType()));
      paramLockboxTransaction.setCustomerReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(15), DCAdapter.getCustomerReferenceNumberType()));
      DCUtil.fillExtendABean(paramLockboxTransaction, paramResultSet, 16);
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to read lockbox transaction records from database: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39518;
      throw new DCException(i, "Failed to read lockbox transaction records from database.", localException);
    }
  }
  
  private static ResultSet a(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap, Connection paramConnection)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().put("Account", paramLockboxAccount.getAccountID());
    if (paramLockboxAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramLockboxAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramLockboxAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", str);
    }
    int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", DCAdapter.PAGESIZE);
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID ");
      a(localStringBuffer, paramLockboxAccount, paramPagingContext, paramHashMap);
      ArrayList localArrayList = a(localStringBuffer, paramPagingContext, paramBoolean, paramHashMap);
      a(localStringBuffer, paramPagingContext, paramBoolean, null, paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int j = 1;
      j = a(localPreparedStatement, j, paramPagingContext, paramHashMap);
      a(localPreparedStatement, j, localArrayList, paramHashMap);
      localPreparedStatement.setMaxRows(i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      localPreparedStatement = null;
    }
    return localResultSet;
  }
  
  protected static LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    LockboxTransactions localLockboxTransactions = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      localLockboxTransactions = getTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, localConnection, paramHashMap);
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    finally
    {
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
    return localLockboxTransactions;
  }
  
  public static LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    LockboxTransactions localLockboxTransactions1 = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append("SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=?  ");
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
          localStringBuffer.append(jdField_null);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append("SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
          localStringBuffer.append(jdField_null);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append("SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(jdField_null);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append("SELECT b.DCTransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(jdField_null);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(4, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      localLockboxTransactions1 = new LockboxTransactions();
      LockboxTransaction localLockboxTransaction = null;
      while (localResultSet.next())
      {
        localLockboxTransaction = localLockboxTransactions1.create();
        a(paramLockboxAccount, localLockboxTransaction, localResultSet);
      }
      LockboxTransactions localLockboxTransactions2 = localLockboxTransactions1;
      return localLockboxTransactions2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to read lockbox transaction records from database: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39518;
      throw new DCException(i, "Failed to read lockbox transaction records from database.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  public static LockboxTransactions getRecentTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      a(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      jdField_if(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      localResultSet = a(paramLockboxAccount, paramPagingContext, false, paramHashMap, localConnection);
      LockboxTransactions localLockboxTransactions = new LockboxTransactions();
      while (localResultSet.next())
      {
        localObject1 = localLockboxTransactions.create();
        a(paramLockboxAccount, (LockboxTransaction)localObject1, localResultSet);
      }
      a(paramPagingContext, localLockboxTransactions);
      Object localObject1 = localLockboxTransactions;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39518, "Failed to get lockbox transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      LockboxTransactions localLockboxTransactions1 = new LockboxTransactions();
      localConnection = DCAdapter.getDBConnection();
      a(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      jdField_if(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      if (!paramPagingContext.getMap().containsKey("UPPER_BOUND_TransactionIndex"))
      {
        LockboxTransactions localLockboxTransactions2 = localLockboxTransactions1;
        return localLockboxTransactions2;
      }
      paramPagingContext.getMap().remove("CurrentPage");
      paramPagingContext.getMap().remove("PageSettings");
      int i = 1;
      paramPagingContext.getMap().put("CurrentPage", new Integer(i));
      a(paramPagingContext.getMap());
      localResultSet = a(paramLockboxAccount, paramPagingContext, true, paramHashMap, localConnection);
      while (localResultSet.next())
      {
        localObject1 = localLockboxTransactions1.create();
        a(paramLockboxAccount, (LockboxTransaction)localObject1, localResultSet);
      }
      a(paramPagingContext, localLockboxTransactions1);
      Object localObject1 = localLockboxTransactions1;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39518, "Failed to get lockbox transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i != -1)
      {
        i++;
        localHashMap.put("CurrentPage", new Integer(i));
        a(localHashMap);
      }
      localResultSet = a(paramLockboxAccount, paramPagingContext, true, paramHashMap, localConnection);
      LockboxTransactions localLockboxTransactions = new LockboxTransactions();
      while (localResultSet.next())
      {
        localObject1 = localLockboxTransactions.create();
        a(paramLockboxAccount, (LockboxTransaction)localObject1, localResultSet);
      }
      a(paramPagingContext, localLockboxTransactions);
      Object localObject1 = localLockboxTransactions;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39518, "Failed to get lockbox transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static void a(HashMap paramHashMap)
  {
    IntMap localIntMap = (IntMap)paramHashMap.get("PageSettings");
    if (localIntMap == null)
    {
      localIntMap = new IntMap();
      paramHashMap.put("PageSettings", localIntMap);
    }
    int i = MapUtil.getIntValue(paramHashMap, "CurrentPage", 1);
    if (localIntMap.get(i) == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.putAll(paramHashMap);
      localIntMap.put(i, localHashMap);
    }
  }
  
  public static LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i != -1)
      {
        i--;
        localHashMap.put("CurrentPage", new Integer(i));
        localObject1 = (IntMap)localHashMap.get("PageSettings");
        localObject2 = (HashMap)((IntMap)localObject1).get(i);
        if (localReportCriteria != null) {
          for (int j = 0; j < localReportCriteria.getSortCriteria().size(); j++)
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(j);
            localHashMap.put("SORT_VALUE_MIN_" + localReportSortCriterion.getName(), ((HashMap)localObject2).get("SORT_VALUE_MIN_" + localReportSortCriterion.getName()));
            localHashMap.put("SORT_VALUE_MAX_" + localReportSortCriterion.getName(), ((HashMap)localObject2).get("SORT_VALUE_MAX_" + localReportSortCriterion.getName()));
          }
        }
        localHashMap.put("SORT_VALUE_MIN_TransactionIndex", ((HashMap)localObject2).get("SORT_VALUE_MIN_TransactionIndex"));
        localHashMap.put("SORT_VALUE_MAX_TransactionIndex", ((HashMap)localObject2).get("SORT_VALUE_MAX_TransactionIndex"));
      }
      if (i == -1) {
        localResultSet = a(paramLockboxAccount, paramPagingContext, false, paramHashMap, localConnection);
      } else {
        localResultSet = a(paramLockboxAccount, paramPagingContext, true, paramHashMap, localConnection);
      }
      Object localObject1 = new LockboxTransactions();
      while (localResultSet.next())
      {
        localObject2 = ((LockboxTransactions)localObject1).createNoAdd();
        a(paramLockboxAccount, (LockboxTransaction)localObject2, localResultSet);
        if (i == -1) {
          ((LockboxTransactions)localObject1).add(0, localObject2);
        } else {
          ((LockboxTransactions)localObject1).add(localObject2);
        }
      }
      a(paramPagingContext, (LockboxTransactions)localObject1);
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39518, "Failed to get lockbox transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static long[] a(Connection paramConnection, LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append("SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append("SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append("SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append("SELECT min( b.DCTransactionIndex ), max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(4, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      long[] arrayOfLong1 = new long[2];
      if (!localResultSet.next()) {
        throw new DCException(39518, "Unable to retrieve the maximum transaction index for the given criteria");
      }
      arrayOfLong1[0] = localResultSet.getLong(1);
      arrayOfLong1[1] = localResultSet.getLong(2);
      long[] arrayOfLong2 = arrayOfLong1;
      return arrayOfLong2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get maximum lockbox transaction index: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39518;
      throw new DCException(i, "Failed to get maximum lockbox transaction index.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static long a(Connection paramConnection, LockboxAccount paramLockboxAccount, long paramLong, boolean paramBoolean, HashMap paramHashMap)
    throws DCException
  {
    if (!DCAdapter.isPagingDateBased()) {
      return -1L;
    }
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l1 = -1L;
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramBoolean)
    {
      localStringBuffer.append("SELECT min( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex > ? ");
      DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
    }
    else
    {
      localStringBuffer.append("SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex < ? ");
      DCUtil.appendNullWhereClause(localStringBuffer, jdField_if, paramLockboxAccount.getRoutingNumber());
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
    }
    try
    {
      localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
      localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
      localPreparedStatement.setString(3, str);
      localPreparedStatement.setLong(4, paramLong);
      if (paramLockboxAccount.getRoutingNumber() != null) {
        localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localResultSet.next();
      long l2 = localResultSet.getLong(1);
      if (!localResultSet.wasNull())
      {
        Long localLong;
        if (paramBoolean)
        {
          localLong = (Long)paramHashMap.get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE");
          if (localLong == null) {
            throw new DCException(39519, "The maximum transaction index for the specified range was not found in the extra hashmap.");
          }
          if (l2 >= localLong.longValue()) {
            return -1L;
          }
        }
        else
        {
          localLong = (Long)paramHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE");
          if (localLong == null) {
            throw new DCException(39519, "The minimum transaction index for the specified range was not found in the extra hashmap.");
          }
          if (l2 <= localLong.longValue()) {
            return -1L;
          }
        }
        l1 = l2;
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get next ID for lockbox transaction: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39519;
      throw new DCException(i, "Failed to get next ID for lockbox transaction.", localException);
    }
    return l1;
  }
  
  private static long a(Connection paramConnection, LockboxAccount paramLockboxAccount, LockboxTransactions paramLockboxTransactions, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramLockboxTransactions.get(0);
    DateTime localDateTime = localLockboxTransaction.getProcessingDate();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str2 = localDateFormat.format(localDateTime.getTime());
    StringBuffer localStringBuffer1 = new StringBuffer(str2);
    localStringBuffer1.append("00000000001");
    StringBuffer localStringBuffer2 = new StringBuffer(str2);
    localStringBuffer2.append("99999999999");
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer3 = new StringBuffer();
    localStringBuffer3.append("SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_LBTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex >= ? AND b.DCTransactionIndex < ?");
    DCUtil.appendNullWhereClause(localStringBuffer3, jdField_if, paramLockboxAccount.getRoutingNumber());
    PreparedStatement localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer3.toString());
    long l1 = -1L;
    try
    {
      localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
      localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
      localPreparedStatement.setString(3, str1);
      localPreparedStatement.setLong(4, Long.parseLong(localStringBuffer1.toString()));
      localPreparedStatement.setLong(5, Long.parseLong(localStringBuffer2.toString()));
      if (paramLockboxAccount.getRoutingNumber() != null) {
        localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer3.toString());
      localResultSet.next();
      long l2 = localResultSet.getLong(1);
      if (!localResultSet.wasNull())
      {
        String str3 = Long.toString(l2);
        String str4 = str3.substring(0, 8);
        String str5 = str3.substring(8);
        if (str4.equals(str2))
        {
          long l3 = Long.parseLong(str5);
          l3 += 1L;
          String str6 = jdField_new.format(l3);
          StringBuffer localStringBuffer4 = new StringBuffer(str2);
          localStringBuffer4.append(str6);
          l1 = Long.parseLong(localStringBuffer4.toString());
        }
        else
        {
          l1 = Long.parseLong(localStringBuffer1.toString());
        }
      }
      else
      {
        l1 = Long.parseLong(localStringBuffer1.toString());
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get next date based ID for lockbox transaction: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39519;
      throw new DCException(i, "Failed to get next date based ID for lockbox transaction.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
    return l1;
  }
  
  private static void a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    for (int i = 0; i < localReportSortCriteria.size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i);
      paramStringBuffer.append(a(localReportSortCriterion.getName())).append(", ");
    }
    paramStringBuffer.append(" DCTransactionIndex ");
  }
  
  private static void a(Connection paramConnection, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramLockboxAccount.getAccountID());
    if (paramLockboxAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramLockboxAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramLockboxAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      localObject1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", localObject1);
    }
    Object localObject1 = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT ");
      a(localStringBuffer, paramPagingContext, paramHashMap);
      localStringBuffer.append(" FROM DC_Account a, DC_LBTransactions b ");
      localStringBuffer.append(" WHERE a.DCAccountID = b.DCAccountID ");
      a(localStringBuffer, paramLockboxAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(false), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = a(localPreparedStatement, i, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int j = 0; j < localReportSortCriteria.size(); j++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
          paramPagingContext.getMap().put("UPPER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, j + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("UPPER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet((ResultSet)localObject1);
      localPreparedStatement = null;
    }
  }
  
  private static void jdField_if(Connection paramConnection, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramLockboxAccount.getAccountID());
    if (paramLockboxAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramLockboxAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramLockboxAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      localObject1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", localObject1);
    }
    Object localObject1 = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT ");
      a(localStringBuffer, paramPagingContext, paramHashMap);
      localStringBuffer.append(" FROM DC_Account a, DC_LBTransactions b ");
      localStringBuffer.append(" WHERE a.DCAccountID = b.DCAccountID ");
      a(localStringBuffer, paramLockboxAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(true), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = a(localPreparedStatement, i, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int j = 0; j < localReportSortCriteria.size(); j++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
          paramPagingContext.getMap().put("LOWER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, j + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("LOWER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet((ResultSet)localObject1);
      localPreparedStatement = null;
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
  {
    if (paramPagingContext == null) {
      return;
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      return;
    }
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
      return;
    }
    Properties localProperties = localReportCriteria.getSearchCriteria();
    String str = localProperties.getProperty("Account");
    if ((str != null) && (str.length() != 0)) {
      paramStringBuffer.append(" and a.AccountID = ? ");
    }
    str = localProperties.getProperty("RoutingNumber");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and a.RoutingNumber = ? ");
    } else if (str == null) {
      paramStringBuffer.append(" and a.RoutingNumber is NULL ");
    }
    str = localProperties.getProperty("BankId");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and a.BankID = ? ");
    }
    str = localProperties.getProperty("DataClassification");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.DataClassification = ? ");
    }
    str = localProperties.getProperty("StartDate");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.DataDate >= ? ");
    }
    str = localProperties.getProperty("EndDate");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.DataDate <= ? ");
    }
    str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency1 = new Currency(new BigDecimal(str), paramLockboxAccount.getLocale());
        paramStringBuffer.append(" and b.Amount>=?");
      }
      catch (Exception localException1) {}
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency2 = new Currency(new BigDecimal(str), paramLockboxAccount.getLocale());
        paramStringBuffer.append(" and b.Amount<=?");
      }
      catch (Exception localException2) {}
    }
  }
  
  private static int a(PreparedStatement paramPreparedStatement, int paramInt, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    if (paramPagingContext == null) {
      return paramInt;
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      return paramInt;
    }
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
      return paramInt;
    }
    Properties localProperties = localReportCriteria.getSearchCriteria();
    String str = localProperties.getProperty("Account");
    if ((str != null) && (str.length() != 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("RoutingNumber");
    if ((str != null) && (str.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("BankId");
    if ((str != null) && (str.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("DataClassification");
    if ((str != null) && (str.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("StartDate");
    if ((str != null) && (str.length() > 0)) {
      DCUtil.fillTimestampColumn(paramPreparedStatement, paramInt++, str, "MM/dd/yyyy HH:mm:ss");
    }
    str = localProperties.getProperty("EndDate");
    if ((str != null) && (str.length() > 0)) {
      DCUtil.fillTimestampColumn(paramPreparedStatement, paramInt++, str, "MM/dd/yyyy HH:mm:ss");
    }
    str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency1 = new Currency(new BigDecimal(str), null);
        DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency1);
      }
      catch (Exception localException1) {}
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency2 = new Currency(new BigDecimal(str), null);
        DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency2);
      }
      catch (Exception localException2) {}
    }
    return paramInt;
  }
  
  private static int a(PreparedStatement paramPreparedStatement, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws Exception
  {
    for (int i = 0; i < paramArrayList.size(); i++) {
      DCUtil.fillValue(paramPreparedStatement, paramInt++, paramArrayList.get(i));
    }
    return paramInt;
  }
  
  private static ArrayList a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    ArrayList localArrayList1 = new ArrayList();
    int i = MapUtil.getIntValue(paramPagingContext.getMap(), "CurrentPage", -1);
    if (i == 1) {
      return localArrayList1;
    }
    boolean bool = true;
    ArrayList localArrayList2 = new ArrayList();
    StringBuffer localStringBuffer1 = null;
    ArrayList localArrayList3 = new ArrayList();
    String str1 = null;
    if (!localReportSortCriteria.isEmpty()) {
      bool = ((ReportSortCriterion)localReportSortCriteria.get(localReportSortCriteria.size() - 1)).getAsc();
    } else {
      bool = true;
    }
    if (paramBoolean)
    {
      if (bool) {
        str1 = "SORT_VALUE_MAX_TransactionIndex";
      } else {
        str1 = "SORT_VALUE_MIN_TransactionIndex";
      }
    }
    else if (bool) {
      str1 = "SORT_VALUE_MIN_TransactionIndex";
    } else {
      str1 = "SORT_VALUE_MAX_TransactionIndex";
    }
    if (paramPagingContext.getMap().containsKey(str1)) {
      localReportSortCriteria.create(localReportSortCriteria.size() + 1, "TransactionIndex", bool);
    }
    for (int j = 0; j < localReportSortCriteria.size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
      int k = (DCAdapter.CONNECTIONTYPE != null) && (DCAdapter.CONNECTIONTYPE.indexOf("ASE") != -1) ? 1 : 0;
      int m = ((k != 0) && (localReportSortCriterion.getAsc())) || ((k == 0) && (!localReportSortCriterion.getAsc())) ? 1 : 0;
      String str2 = null;
      String str3 = null;
      bool = localReportSortCriterion.getAsc();
      if (paramBoolean)
      {
        if (bool)
        {
          str2 = "SORT_VALUE_MAX_" + localReportSortCriterion.getName();
          str3 = " > ";
        }
        else
        {
          str2 = "SORT_VALUE_MIN_" + localReportSortCriterion.getName();
          str3 = " < ";
        }
      }
      else if (bool)
      {
        str2 = "SORT_VALUE_MIN_" + localReportSortCriterion.getName();
        str3 = " < ";
      }
      else
      {
        str2 = "SORT_VALUE_MAX_" + localReportSortCriterion.getName();
        str3 = " > ";
      }
      if (paramPagingContext.getMap().containsKey(str2))
      {
        String str4;
        if ("TransactionIndex".equals(localReportSortCriterion.getName())) {
          str4 = "DCTransactionIndex";
        } else {
          str4 = a(localReportSortCriterion.getName());
        }
        Object localObject = paramPagingContext.getMap().get(str2);
        StringBuffer localStringBuffer2;
        if (localObject != null)
        {
          if (localStringBuffer1 == null) {
            localStringBuffer1 = new StringBuffer();
          }
          localStringBuffer2 = new StringBuffer(localStringBuffer1.toString());
          if (localStringBuffer2.length() != 0) {
            localStringBuffer2.append(" AND ");
          }
          if (m == 0)
          {
            localStringBuffer2.append("( ").append(str4).append(str3).append("? ");
            localStringBuffer2.append("OR ").append(str4).append(" is NULL ) ");
          }
          else
          {
            localStringBuffer2.append(str4).append(str3).append("? ");
          }
          localArrayList1.addAll(localArrayList3);
          localArrayList1.add(localObject);
          if (localStringBuffer1.length() != 0) {
            localStringBuffer1.append(" AND ");
          }
          if (m == 0)
          {
            localStringBuffer1.append("( ").append(str4).append(" = ? ");
            localStringBuffer1.append("OR ").append(str4).append(" is NULL ) ");
          }
          else
          {
            localStringBuffer1.append(str4).append(" = ? ");
          }
          localArrayList3.add(localObject);
          localArrayList2.add(localStringBuffer2);
        }
        else
        {
          if (localStringBuffer1 == null) {
            localStringBuffer1 = new StringBuffer();
          } else if (localStringBuffer1.length() != 0) {
            localStringBuffer1.append(" AND ");
          }
          if (m == 0)
          {
            localStringBuffer1.append(str4).append(" is NULL ");
          }
          else
          {
            localStringBuffer2 = new StringBuffer(localStringBuffer1.toString());
            localStringBuffer1.append(str4).append(" is NULL ");
            localStringBuffer2.append(str4).append(" is NOT NULL");
            localArrayList2.add(localStringBuffer2);
            localArrayList1.addAll(localArrayList3);
          }
        }
      }
    }
    if (paramPagingContext.getMap().containsKey(str1))
    {
      paramStringBuffer.append(" AND (");
      paramStringBuffer.append((StringBuffer)localArrayList2.get(0));
      for (j = 1; j < localArrayList2.size(); j++) {
        paramStringBuffer.append(" OR (").append((StringBuffer)localArrayList2.get(j)).append(" ) ");
      }
      paramStringBuffer.append(" ) ");
      localReportSortCriteria.remove(localReportSortCriteria.size() - 1);
    }
    return localArrayList1;
  }
  
  private static void a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, boolean paramBoolean, Boolean paramBoolean1, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    paramStringBuffer.append(" ORDER BY ");
    boolean bool = paramBoolean ? true : paramBoolean1 != null ? paramBoolean1.booleanValue() : false;
    for (int i = 0; i < localReportSortCriteria.size(); i++)
    {
      if (i != 0) {
        paramStringBuffer.append(", ");
      }
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i);
      paramStringBuffer.append(a(localReportSortCriterion.getName()));
      bool = paramBoolean1 == null ? false : !localReportSortCriterion.getAsc() ? true : paramBoolean ? localReportSortCriterion.getAsc() : paramBoolean1.booleanValue();
      paramStringBuffer.append(bool ? " ASC" : " DESC");
    }
    if (localReportSortCriteria.size() != 0) {
      paramStringBuffer.append(",");
    }
    paramStringBuffer.append(" DCTransactionIndex ").append(bool ? "ASC" : "DESC");
  }
  
  private static String a(String paramString)
    throws Exception
  {
    if (paramString.equals("LockBoxNumber")) {
      return "LockboxNumber";
    }
    if (paramString.equals("Amount")) {
      return "Amount";
    }
    if (paramString.equals("ImmediateFloat")) {
      return "ImmedAvailAmount";
    }
    if (paramString.equals("OneDayFloat")) {
      return "OneDayAvailAmount";
    }
    if (paramString.equals("TwoDayFloat")) {
      return "MoreOneDayAvailAm";
    }
    if (paramString.equals("NumRejectedChecks")) {
      return "NumRejectedChecks";
    }
    if (paramString.equals("AmountRejected")) {
      return "RejectedAmount";
    }
    throw new DCException(1025, "Invalid paging sort criteria `" + paramString + "'.");
  }
  
  private static void a(PagingContext paramPagingContext, LockboxTransactions paramLockboxTransactions)
  {
    if ((paramLockboxTransactions == null) || (paramLockboxTransactions.size() == 0)) {
      return;
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if (localReportCriteria == null) {
      return;
    }
    int i = 0;
    for (int j = 0; j < localReportCriteria.getSortCriteria().size(); j++)
    {
      localObject2 = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(j);
      Object localObject3 = a((ReportSortCriterion)localObject2, paramLockboxTransactions.get(0));
      Object localObject4 = a((ReportSortCriterion)localObject2, paramLockboxTransactions.get(paramLockboxTransactions.size() - 1));
      if (((ReportSortCriterion)localObject2).getAsc())
      {
        i = 1;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
      }
      else
      {
        i = 0;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
      }
    }
    Object localObject1 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramLockboxTransactions.get(0));
    Object localObject2 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramLockboxTransactions.get(paramLockboxTransactions.size() - 1));
    if (i != 0)
    {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", localObject1);
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", localObject2);
    }
    else
    {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", localObject2);
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", localObject1);
    }
  }
  
  private static Object a(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("LockBoxNumber")) {
      return localLockboxTransaction.getLockboxNumber();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localLockboxTransaction.getAmount();
    }
    if (paramReportSortCriterion.getName().equals("ImmediateFloat")) {
      return localLockboxTransaction.getImmediateFloat();
    }
    if (paramReportSortCriterion.getName().equals("OneDayFloat")) {
      return localLockboxTransaction.getOneDayFloat();
    }
    if (paramReportSortCriterion.getName().equals("TwoDayFloat")) {
      return localLockboxTransaction.getTwoDayFloat();
    }
    if (paramReportSortCriterion.getName().equals("NumRejectedChecks")) {
      return new Integer(localLockboxTransaction.getNumRejectedChecks());
    }
    if (paramReportSortCriterion.getName().equals("AmountRejected")) {
      return localLockboxTransaction.getRejectedAmount();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localLockboxTransaction.getTransactionIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCLBTransactions
 * JD-Core Version:    0.7.0.1
 */