package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.beans.dataconsolidator.MasterSubRelation;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.DirectedGraph;
import com.ffusion.util.GraphVertex;
import com.ffusion.util.MapUtil;
import com.ffusion.util.Pair;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class DCAdapter
{
  public static String PAGING_MECHANISM_CONSECUTIVE = "consecutive";
  public static String PAGING_MECHANISM_DATE_BASED = "date-based";
  public static final String TYPE_STRING = "String";
  public static final String TYPE_INTEGER = "Integer";
  public static String CONNECTIONTYPE = null;
  private static String ae = "String";
  private static String Y = "String";
  public static final int LIVE_DATA = 1;
  public static final int BAI_DATA = 2;
  private static String jdField_null = null;
  protected static int PAGESIZE = 10;
  protected static int BATCHSIZE = 200;
  protected static String PAGING_MECHANISM = PAGING_MECHANISM_CONSECUTIVE;
  private static final String h = "UTF-8";
  private static final int X = 1;
  private static final int aI = 2;
  private static final int aA = 3;
  private static final int jdField_void = 4;
  private static String c = "DELETE FROM DC_TransTypeDesc";
  private static String jdField_try = "INSERT INTO DC_TransTypeDesc(TRANS_TYPE_ID, TRANS_TYPE_DESC, LOCALE) VALUES( ?, ?, ? )";
  private static final String jdField_char = "SELECT DCAccountID, max( DCTransactionIndex ) FROM DC_Transactions WHERE DataClassification=? GROUP BY DCAccountID";
  private static final String g = "SELECT DCAccountID, max( DCTransactionIndex ) FROM DC_LBTransactions WHERE DataClassification=? GROUP BY DCAccountID";
  private static final String jdField_byte = "SELECT DCLockboxID, max( DCCreditItemIndex ) FROM DC_LBCreditItems WHERE DataClassification=? GROUP BY DCLockboxID";
  private static final String n = "SELECT DCAccountID, max( DCTransactionIndex ) FROM DC_DsbTransactions WHERE DataClassification=? GROUP BY DCAccountID";
  private static final String aC = "DELETE FROM DC_MASTERSUB_REL WHERE DATADATE < ? AND ( (MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ?) OR (SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ?) )";
  private static final String aL = "DELETE FROM DC_MASTERSUB_REL WHERE DATADATE < ? AND ( (MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ?) OR (SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ?) )";
  protected static HashMap connHashMap = null;
  private static final String l = "SELECT DCAccountID FROM DC_Account WHERE AccountID=?  AND BankID=? AND RoutingNumber=?";
  private static final String am = "SELECT DCAccountID FROM DC_Account WHERE AccountID=?  AND BankID=? AND RoutingNumber IS NULL";
  private static final String aJ = "SELECT DCLockboxID FROM DC_Lockbox WHERE DCAccountID=? AND LockboxNumber=?";
  private static final String aw = "INSERT INTO DC_MASTERSUB_REL (DATADATE, BANK_ID, BUS_DIRECTORY_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, SUB_ROUTING_NUM, SUB_ACCOUNT_ID, LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, DATASOURCEFILENAME, DATASOURCEFILEDATE, BAIFILEIDENTIFIER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String ag = "SELECT MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, WITHHOLD_NZB_SUB FROM DC_MASTERSUB_REL WHERE SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ? AND SUB_ROUTING_NUM = ?";
  private static final String aa = "SELECT MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, WITHHOLD_NZB_SUB FROM DC_MASTERSUB_REL WHERE SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ? AND SUB_ROUTING_NUM IS NULL";
  private static final String jdField_new = "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?";
  private static final String jdField_int = "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?";
  private static final String jdField_for = "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?";
  private static final String jdField_do = "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?";
  private static final String V = "SELECT max(DataDate) as MAXDATE FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND b.DataDate < ? AND b.ClosingLedger = 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ?";
  private static final String T = "SELECT max(DataDate) as MAXDATE FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND b.DataDate < ? AND b.ClosingLedger = 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL";
  private static final String v = "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND DataDate > ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ? ORDER BY DataDate ";
  private static final String y = "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND DataDate > ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL ORDER BY DataDate ";
  private static final String t = "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ? ORDER BY DataDate ";
  private static final String S = "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL ORDER BY DataDate ";
  private static final int i = 100;
  private static final int aD = 200;
  private static final String W = "DataSource";
  private static final String D = "DCTransactionIndex";
  private static final String I = "DCCreditItemIndex";
  private static final String p = "BAIFileIdentifier";
  private static final String J = "DataSourceFileName";
  private static final String jdField_else = "DataSourceFileDate";
  private static final String al = "BAIFILEIDENTIFIER";
  private static final String f = "DATASOURCEFILENAME";
  private static final String ax = "DATASOURCEFILEDATE";
  private static final String u = "DCAccountID";
  private static final String x = "DCLockboxID";
  private static final String aF = "DataClassification";
  public static final String DC_DSBTRANSACTIONS = " DC_DsbTransactions ";
  public static final String DC_LBCREDITITEMS = " DC_LBCreditItems ";
  public static final String DC_LBTRANSACTIONS = " DC_LBTransactions ";
  public static final String DC_TRANSACTIONS = " DC_Transactions ";
  public static final String DC_MASTERSUB_REL = " DC_MASTERSUB_REL ";
  public static final String DC_ACCOUNTHISTORY = " DC_AccountHistory ";
  public static final String DC_ACCOUNTSUMMARY = " DC_AccountSummary ";
  public static final String DC_LOANACCTSUMMARY = " DC_LoanAcctSummary ";
  public static final String DC_CCACCTSUMMARY = " DC_CCAcctSummary ";
  public static final String DC_EXTACCTSUMMARY = " DC_ExtAcctSummary ";
  public static final String DC_EXTENDABEANXML = "DC_ExtendABeanXML";
  private static final String K = " SELECT ";
  private static final String an = " DCAccountID ";
  private static final String w = " DCLockboxID ";
  private static final String R = " max( ";
  private static final String ac = " min( ";
  private static final String z = " DCTransactionIndex ";
  private static final String aH = " DCCreditItemIndex ";
  private static final String ar = " DataClassification ";
  private static final String av = " FROM ";
  private static final String o = " DC_DsbTransactions ";
  private static final String Q = " DC_LBCreditItems ";
  private static final String U = " DC_LBTransactions ";
  private static final String s = " DC_Transactions ";
  private static final String j = " DC_MASTERSUB_REL ";
  private static final String G = " DC_AccountHistory ";
  private static final String ay = " DC_AccountSummary ";
  private static final String B = " DC_LoanAcctSummary ";
  private static final String aK = " DC_CCAcctSummary ";
  private static final String F = " DC_DsbSummary";
  private static final String H = " DC_LockboxSummary ";
  private static final String ad = " DC_ExtAcctSummary ";
  private static final String A = " WHERE DataSource=? ";
  private static final String aB = " GROUP BY ";
  private static final String jdField_if = "DELETE FROM DC_ExtendABeanXML WHERE DCExtendABeanID IN ( SELECT ExtendABeanXMLID FROM ";
  private static final String a = " )";
  private static final int aG = 1;
  private static final int b = 2;
  private static final int at = 1;
  private static final int aj = 2;
  private static final int aE = 3;
  private static final int az = 4;
  private static final int M = 5;
  private static final int as = 6;
  private static final int m = 7;
  private static final int ab = 8;
  private static final int r = 9;
  private static final int N = 10;
  private static final int ai = 11;
  private static final int ap = 12;
  private static final String aq = "DELETE FROM ";
  private static final String P = " DataSourceFileName is null ";
  private static final String jdField_long = " DataSourceFileName = ? ";
  private static final String L = " DataSourceFileDate is null ";
  private static final String ao = " DataSourceFileDate = ? ";
  private static final String ah = " BAIFileIdentifier is null ";
  private static final String e = " BAIFileIdentifier = ? ";
  private static final String Z = " DATASOURCEFILENAME is null ";
  private static final String O = " DATASOURCEFILENAME = ? ";
  private static final String d = " DATASOURCEFILEDATE is null ";
  private static final String jdField_goto = " DATASOURCEFILEDATE = ? ";
  private static final String ak = " BAIFILEIDENTIFIER is null ";
  private static final String af = " BAIFILEIDENTIFIER = ? ";
  private static final String q = "UPDATE DC_RecordCounter SET NextIndex = ? WHERE DCObjectType = ? AND DCObjectID = ? AND CounterName = ? AND DataClassification = ? ";
  private static final String au = "99999999999";
  private static final String k = "SELECT (LAST_UPDATED) FROM DC_SUBTYPE_DESC";
  private static final String E = "DELETE FROM DC_SUBTYPE_DESC";
  private static final String C = "INSERT INTO DC_SUBTYPE_DESC VALUES(?,?,?,?)";
  private static final String jdField_case = "UPDATE DC_SUBTYPE_DESC SET LAST_UPDATED=LAST_UPDATED";
  
  protected static int getBatchSize(HashMap paramHashMap)
  {
    int i1 = MapUtil.getIntValue(paramHashMap, "BATCHSIZE", BATCHSIZE);
    if (i1 < 1)
    {
      i1 = BATCHSIZE;
      DebugLog.log(Level.INFO, "Invalid batch size. Batch size must be a non-negative integer. Using default batch size.");
    }
    return i1;
  }
  
  protected static int getDCAccountID(Connection paramConnection, String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramString3 != null)
      {
        localPreparedStatement = getStatement(paramConnection, "SELECT DCAccountID FROM DC_Account WHERE AccountID=?  AND BankID=? AND RoutingNumber=?");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setString(3, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT DCAccountID FROM DC_Account WHERE AccountID=?  AND BankID=? AND RoutingNumber=?");
      }
      else
      {
        localPreparedStatement = getStatement(paramConnection, "SELECT DCAccountID FROM DC_Account WHERE AccountID=?  AND BankID=? AND RoutingNumber IS NULL");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT DCAccountID FROM DC_Account WHERE AccountID=?  AND BankID=? AND RoutingNumber IS NULL");
      }
      int i1 = -1;
      if (localResultSet.next()) {
        i1 = localResultSet.getInt("DCAccountID");
      }
      int i2 = i1;
      return i2;
    }
    catch (Exception localException)
    {
      DCException localDCException = new DCException("Error occurred while tring to get DCAccountID.", localException);
      if ((localException instanceof SQLException)) {
        localDCException.setCode(302);
      } else {
        localDCException.setCode(414);
      }
      throw localDCException;
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static int getDCLockboxID(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, "SELECT DCLockboxID FROM DC_Lockbox WHERE DCAccountID=? AND LockboxNumber=?");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT DCLockboxID FROM DC_Lockbox WHERE DCAccountID=? AND LockboxNumber=?");
      int i1 = -1;
      if (localResultSet.next()) {
        i1 = localResultSet.getInt("DCLockboxID");
      }
      int i2 = i1;
      return i2;
    }
    catch (Exception localException)
    {
      throw new DCException("Error occurred while trying to get DCLockboxID.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  public static PreparedStatement getStatement(Connection paramConnection, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    HashMap localHashMap = getMapUsingConnection(paramConnection);
    try
    {
      if (!localHashMap.containsKey(paramString))
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString);
        localHashMap.put(paramString, localPreparedStatement);
      }
      else
      {
        localPreparedStatement = (PreparedStatement)localHashMap.get(paramString);
      }
      return localPreparedStatement;
    }
    catch (Exception localException)
    {
      throw new DCException(304, "Unable to prepare statement for DB access.", localException);
    }
  }
  
  protected static synchronized HashMap getMapUsingConnection(Connection paramConnection)
  {
    return (HashMap)connHashMap.get(paramConnection);
  }
  
  public static void initialize(HashMap paramHashMap)
    throws DCException
  {
    if (connHashMap != null) {
      return;
    }
    try
    {
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      jdField_null = ConnectionPool.init(localProperties);
      if (connHashMap == null) {
        connHashMap = new HashMap();
      }
      PAGESIZE = MapUtil.getIntValue(paramHashMap, "PAGESIZE", PAGESIZE);
      BATCHSIZE = MapUtil.getIntValue(paramHashMap, "BATCHSIZE", BATCHSIZE);
      ae = MapUtil.getStringValue(paramHashMap, "BANKREFNUMTYPE", ae);
      Y = MapUtil.getStringValue(paramHashMap, "CUSTREFNUMTYPE", Y);
      CONNECTIONTYPE = localProperties.getProperty("ConnectionType");
      PAGING_MECHANISM = MapUtil.getStringValue(paramHashMap, "PAGING_MECHANISM", PAGING_MECHANISM_CONSECUTIVE);
      if ((!PAGING_MECHANISM.equals(PAGING_MECHANISM_CONSECUTIVE)) && (!PAGING_MECHANISM.equals(PAGING_MECHANISM_DATE_BASED)))
      {
        DebugLog.log(Level.INFO, "The paging mechanism '" + PAGING_MECHANISM + "' specified in the XML " + "is not recognized.  Valid choices are 'date-based' and 'consecutive'.  The paging " + "mechanism has defaulted to consecutive paging.");
        PAGING_MECHANISM = PAGING_MECHANISM_CONSECUTIVE;
      }
    }
    catch (Exception localException)
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localException.printStackTrace(localPrintWriter);
      throw new DCException(localStringWriter.toString(), localException);
    }
  }
  
  public static Connection getDBConnection()
    throws DCException
  {
    if (jdField_null == null) {
      throw new DCException("Pool was not initialized.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(jdField_null, false, 2);
      synchronized (connHashMap)
      {
        connHashMap.put(localConnection, new HashMap());
      }
    }
    catch (Exception localException)
    {
      DCException localDCException = new DCException(301, "Unable to get connection from connection pool", localException);
      throw localDCException;
    }
    return localConnection;
  }
  
  public static void releaseDBConnection(Connection paramConnection)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      HashMap localHashMap = getMapUsingConnection(paramConnection);
      Iterator localIterator = localHashMap.values().iterator();
      while (localIterator.hasNext())
      {
        localPreparedStatement = (PreparedStatement)localIterator.next();
        DBUtil.closeStatement(localPreparedStatement);
      }
      rollback(paramConnection);
      synchronized (connHashMap)
      {
        connHashMap.remove(paramConnection);
      }
      DBUtil.returnConnectionWithException(jdField_null, paramConnection);
    }
    catch (Exception localException)
    {
      throw new DCException(305, "Failed to release connection back to connection pool.", localException);
    }
  }
  
  public static void commit(Connection paramConnection)
    throws DCException
  {
    try
    {
      DBUtil.commit(paramConnection);
    }
    catch (Exception localException)
    {
      throw new DCException(306, "Failed to commit database changes.", localException);
    }
  }
  
  public static void rollback(Connection paramConnection)
    throws DCException
  {
    if (paramConnection == null) {
      return;
    }
    try
    {
      DBUtil.rollback(paramConnection);
    }
    catch (Exception localException)
    {
      throw new DCException(302, "Failed to rollback connection.", localException);
    }
  }
  
  private static String a(String paramString1, String paramString2)
  {
    if (paramString1.equals("A")) {
      return paramString2;
    }
    return paramString2 + " AND DataClassification = '" + DBUtil.escapeSQLStringLiteral(paramString1) + "' ";
  }
  
  public static void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    String str1 = "DELETE FROM ";
    String str2 = " WHERE DataDate<?";
    String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    str2 = a(str3, str2);
    StringBuffer localStringBuffer = new StringBuffer(str1);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    Timestamp localTimestamp = new Timestamp(localCalendar.getTime().getTime());
    try
    {
      localConnection = getDBConnection();
      if (paramString.equals("All"))
      {
        for (int i1 = 0; i1 < DCAdapterConstants.CLEANUP_TABLES.length; i1++)
        {
          localStringBuffer.replace(0, localStringBuffer.length(), str1);
          localStringBuffer.append(DCAdapterConstants.CLEANUP_TABLES[i1]).append(str2);
          DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
        }
        localStringBuffer = new StringBuffer(str1);
        localStringBuffer.append("DC_MASTERSUB_REL");
        localStringBuffer.append(" WHERE DATADATE<?");
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Account histories"))
      {
        localStringBuffer.append("DC_AccountHistory").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Account summaries"))
      {
        localStringBuffer.append("DC_AccountSummary").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Account transactions"))
      {
        localStringBuffer.append("DC_Transactions").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
        localStringBuffer = new StringBuffer(str1);
        localStringBuffer.append("DC_MASTERSUB_REL");
        localStringBuffer.append(" WHERE DATADATE<?");
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Lockbox summaries"))
      {
        localStringBuffer.append("DC_LockboxSummary").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Lockbox transactions"))
      {
        localStringBuffer.append("DC_LBTransactions").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Lockbox credit items"))
      {
        localStringBuffer.append("DC_LBCreditItems").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Disbursement summaries"))
      {
        localStringBuffer.append("DC_DsbSummary").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else if (paramString.equals("Disbursement transactions"))
      {
        localStringBuffer.append("DC_DsbTransactions").append(str2);
        DCUtil.cleanupTable(localConnection, localStringBuffer, localTimestamp);
      }
      else
      {
        throw new DCException("Invalid type of information to clean up.");
      }
      DebugLog.log(Level.INFO, "DC Cleanup completed successfully.");
      localConnection.commit();
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static void cleanup(Accounts paramAccounts, String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    String str1 = "DELETE FROM ";
    String str2 = " WHERE DataDate < ?";
    String str3 = "DCAccountID = ?";
    String str4 = "DCLockboxID IN (SELECT DCLockboxID FROM DC_Lockbox WHERE DCAccountID = ?)";
    String[] arrayOfString = { "DC_AccountHistory", "DC_AccountSummary", "DC_ExtAcctSummary", "DC_CCAcctSummary", "DC_LoanAcctSummary", "DC_FixDepInstrment" };
    Calendar localCalendar = null;
    Timestamp localTimestamp = null;
    int[] arrayOfInt = null;
    int i1 = 0;
    str2 = a(paramString2, str2);
    localStringBuffer = new StringBuffer(str1);
    localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    localTimestamp = new Timestamp(localCalendar.getTime().getTime());
    try
    {
      localConnection = getDBConnection();
      arrayOfInt = new int[paramAccounts.size()];
      i1 = 0;
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount1 = (Account)localIterator.next();
        arrayOfInt[(i1++)] = getDCAccountID(localConnection, localAccount1.getID(), localAccount1.getBankID(), localAccount1.getRoutingNum());
      }
      if (!paramString1.equals("Account summaries")) {
        if (paramString1.equals("Account transactions"))
        {
          localStringBuffer.append("DC_Transactions").append(str2);
          localStringBuffer.append(" and ").append(str3);
        }
        else if (paramString1.equals("Lockbox summaries"))
        {
          localStringBuffer.append("DC_LockboxSummary").append(str2);
          localStringBuffer.append(" and ").append(str3);
        }
        else if (paramString1.equals("Lockbox transactions"))
        {
          localStringBuffer.append("DC_LBTransactions").append(str2);
          localStringBuffer.append(" and ").append(str3);
        }
        else if (paramString1.equals("Lockbox credit items"))
        {
          localStringBuffer.append("DC_LBCreditItems").append(str2);
          localStringBuffer.append(" and ").append(str4);
        }
        else if (paramString1.equals("Disbursement summaries"))
        {
          localStringBuffer.append("DC_DsbSummary").append(str2);
          localStringBuffer.append(" and ").append(str3);
        }
        else if (paramString1.equals("Disbursement transactions"))
        {
          localStringBuffer.append("DC_DsbTransactions").append(str2);
          localStringBuffer.append(" and ").append(str3);
        }
        else
        {
          throw new DCException("Invalid type of information to clean up.");
        }
      }
      int i2;
      if (paramString1.equals("Account summaries"))
      {
        for (i2 = 0; i2 < arrayOfString.length; i2++)
        {
          localStringBuffer = new StringBuffer(str1);
          localStringBuffer.append(arrayOfString[i2]).append(str2);
          localStringBuffer.append(" and ").append(str3);
          localPreparedStatement1 = getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement1.setTimestamp(1, localTimestamp);
          for (int i3 = 0; i3 < arrayOfInt.length; i3++)
          {
            localPreparedStatement1.setInt(2, arrayOfInt[i3]);
            DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer.toString());
          }
          commit(localConnection);
        }
      }
      else
      {
        localPreparedStatement1 = getStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement1.setTimestamp(1, localTimestamp);
        if (paramString1.equals("DC_Transactions"))
        {
          localPreparedStatement2 = getStatement(localConnection, "DELETE FROM DC_MASTERSUB_REL WHERE DATADATE < ? AND ( (MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ?) OR (SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ?) )");
          localPreparedStatement2.setTimestamp(1, localTimestamp);
          localPreparedStatement3 = getStatement(localConnection, "DELETE FROM DC_MASTERSUB_REL WHERE DATADATE < ? AND ( (MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ?) OR (SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ?) )");
          localPreparedStatement3.setTimestamp(1, localTimestamp);
        }
        for (i2 = 0; i2 < arrayOfInt.length; i2++)
        {
          localPreparedStatement1.setInt(2, arrayOfInt[i2]);
          DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer.toString());
          if (paramString1.equals("DC_Transactions"))
          {
            Account localAccount2 = (Account)paramAccounts.get(i2);
            if (localAccount2.getRoutingNum() != null)
            {
              localPreparedStatement2.setString(2, localAccount2.getRoutingNum());
              localPreparedStatement2.setString(3, localAccount2.getID());
              localPreparedStatement2.setString(4, localAccount2.getRoutingNum());
              localPreparedStatement2.setString(5, localAccount2.getID());
              DBUtil.executeUpdate(localPreparedStatement2, "DELETE FROM DC_MASTERSUB_REL WHERE DATADATE < ? AND ( (MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ?) OR (SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ?) )");
            }
            else
            {
              localPreparedStatement2.setString(2, localAccount2.getID());
              localPreparedStatement2.setString(3, localAccount2.getID());
              DBUtil.executeUpdate(localPreparedStatement2, "DELETE FROM DC_MASTERSUB_REL WHERE DATADATE < ? AND ( (MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ?) OR (SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ?) )");
            }
          }
        }
        commit(localConnection);
      }
      DebugLog.log(Level.INFO, "DC Cleanup (" + paramString1 + ") completed successfully.");
    }
    catch (Exception localException1)
    {
      if (localConnection != null) {
        try
        {
          rollback(localConnection);
        }
        catch (Exception localException2) {}
      }
      throw new DCException(localException1);
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          releaseDBConnection(localConnection);
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  private static void a(Connection paramConnection, int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    int i1 = -1;
    String str = null;
    if (paramInt == 1)
    {
      i1 = 1;
      str = "ACCT_TRANSACTION_INDEX";
    }
    else if (paramInt == 2)
    {
      i1 = 2;
      str = "LOCKBOX_TRANSACTION_INDEX";
    }
    else if (paramInt == 3)
    {
      i1 = 2;
      str = "LOCKBOX_CREDITITEM_INDEX";
    }
    else if (paramInt == 4)
    {
      i1 = 1;
      str = "DISBURSEMENT_TRANSACTION_INDEX";
    }
    DCRecordCounter.updateIndex(paramConnection, paramHashMap1, i1, str, paramHashMap2);
  }
  
  private static HashMap a(int paramInt1, int paramInt2, Connection paramConnection, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    String str = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramInt1 == 1)
      {
        str = "SELECT DCAccountID, max( DCTransactionIndex ) FROM DC_Transactions WHERE DataClassification=? GROUP BY DCAccountID";
      }
      else if (paramInt1 == 2)
      {
        str = "SELECT DCAccountID, max( DCTransactionIndex ) FROM DC_LBTransactions WHERE DataClassification=? GROUP BY DCAccountID";
      }
      else if (paramInt1 == 3)
      {
        str = "SELECT DCLockboxID, max( DCCreditItemIndex ) FROM DC_LBCreditItems WHERE DataClassification=? GROUP BY DCLockboxID";
      }
      else if (paramInt1 == 4)
      {
        str = "SELECT DCAccountID, max( DCTransactionIndex ) FROM DC_DsbTransactions WHERE DataClassification=? GROUP BY DCAccountID";
      }
      else
      {
        localHashMap = new HashMap();
        return localHashMap;
      }
      localPreparedStatement = getStatement(paramConnection, str);
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str);
      HashMap localHashMap = new HashMap();
      while (localResultSet.next())
      {
        localObject1 = new Integer(localResultSet.getInt(1));
        Long localLong = new Long(localResultSet.getLong(2));
        localHashMap.put(localObject1, localLong);
      }
      Object localObject1 = localHashMap;
      return localObject1;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static void cleanupDataSource(int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DELETE FROM ";
    String str2 = " WHERE dataSource=? ";
    String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    str2 = a(str3, str2);
    StringBuffer localStringBuffer = new StringBuffer(str1);
    try
    {
      for (int i1 = 0; i1 < DCAdapterConstants.CLEANUP_TABLES.length; i1++)
      {
        localStringBuffer.replace(0, localStringBuffer.length(), str1);
        localStringBuffer.append(DCAdapterConstants.CLEANUP_TABLES[i1]).append(str2);
        DCUtil.cleanupTable(paramConnection, localStringBuffer, paramInt);
      }
      HashMap localHashMap = a(1, paramInt, paramConnection, str3);
      a(paramConnection, 1, localHashMap, paramHashMap);
      localHashMap = a(2, paramInt, paramConnection, str3);
      a(paramConnection, 2, localHashMap, paramHashMap);
      localHashMap = a(3, paramInt, paramConnection, str3);
      a(paramConnection, 3, localHashMap, paramHashMap);
      localHashMap = a(4, paramInt, paramConnection, str3);
      a(paramConnection, 4, localHashMap, paramHashMap);
      DebugLog.log(Level.INFO, "DC Cleanup completed successfully.");
    }
    catch (Exception localException)
    {
      localException = localException;
      throw new DCException(localException);
    }
    finally {}
  }
  
  public static void addAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCAccount.addAccount(paramAccount, paramConnection, paramHashMap);
  }
  
  public static boolean accountExists(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccount.accountExists(paramAccount, paramConnection, paramHashMap);
  }
  
  public static Account getAccount(Account paramAccount, HashMap paramHashMap)
    throws DCException
  {
    return DCAccount.getAccount(paramAccount, paramHashMap);
  }
  
  public static Account getAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccount.getAccount(paramAccount, paramConnection, paramHashMap);
  }
  
  public static void updateAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCAccount.updateAccount(paramAccount, paramConnection, paramHashMap);
  }
  
  public static void deleteAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCAccount.deleteAccount(paramAccount, paramConnection, paramHashMap);
  }
  
  public static void addTransactions(Account paramAccount, Transactions paramTransactions, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCTransactions.addTransactions(paramAccount, paramTransactions, paramInt, paramConnection, paramHashMap);
  }
  
  public static Transactions getTransactions(Connection paramConnection, Account paramAccount, Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    Transactions localTransactions = DCTransactions.getTransactions(paramConnection, paramAccount, paramProperties, paramHashMap);
    localTransactions.setSortedBy("TRANS_IDX");
    return localTransactions;
  }
  
  public static Transactions getTransactions(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Transactions localTransactions = DCTransactions.getTransactions(paramConnection, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    localTransactions.setSortedBy("TRANS_IDX");
    return localTransactions;
  }
  
  public static Transactions getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      Transactions localTransactions = getTransactions(localConnection, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
      return localTransactions;
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static Transactions getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return getTransactions(paramConnection, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static int updateTransactions(Account paramAccount, Transactions paramTransactions, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.updateTransactions(paramAccount, paramTransactions, paramConnection, paramHashMap);
  }
  
  public static Transactions getPagedTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Transactions localTransactions = DCTransactions.getPagedTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    return localTransactions;
  }
  
  public static Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public static Transactions getRecentTransactions(Account paramAccount, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    Transactions localTransactions = getRecentTransactions(paramAccount, localPagingContext, paramHashMap);
    localTransactions.setSortedBy("TRANS_IDX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localTransactions;
  }
  
  public static Transactions getRecentTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getRecentTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public static Transactions getNextTransactions(Account paramAccount, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MAX_TransactionIndex", new Long(paramLong - 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    Transactions localTransactions = getNextTransactions(paramAccount, localPagingContext, paramHashMap);
    localTransactions.setSortedBy("TRANS_IDX");
    return localTransactions;
  }
  
  public static Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getNextTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public static Transactions getPreviousTransactions(Account paramAccount, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MIN_TransactionIndex", new Long(paramLong + 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    Transactions localTransactions = getPreviousTransactions(paramAccount, localPagingContext, paramHashMap);
    localTransactions.setSortedBy("TRANS_IDX");
    return localTransactions;
  }
  
  public static Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getPreviousTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public static Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getFDITransactions(paramFixedDepositInstrument, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramProperties, paramHashMap);
  }
  
  public static Currency getImmedAvailAmountSum(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getImmedAvailAmountSum(paramConnection, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static Currency getOneDayAvailAmountSum(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getOneDayAvailAmountSum(paramConnection, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static Currency getMoreThanOneDayAvailAmountSum(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getMoreThanOneDayAvailAmountSum(paramConnection, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static void addFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCFixDepInstrment.addFixedDepositInstrument(paramFixedDepositInstrument, paramInt, paramConnection, paramHashMap);
  }
  
  public static FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCFixDepInstrment.getFixedDepositInstruments(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static void addSummary(Account paramAccount, AccountSummary paramAccountSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCAccountSummary.addSummary(paramAccount, paramAccountSummary, paramInt, paramConnection, paramHashMap);
  }
  
  public static AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountSummary.getSummary(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      AccountSummaries localAccountSummaries = getSummary(paramAccount, paramCalendar1, paramCalendar2, localConnection, paramHashMap);
      return localAccountSummaries;
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static void updateSummary(Connection paramConnection, Account paramAccount, AccountSummary paramAccountSummary, HashMap paramHashMap)
    throws DCException
  {
    DCAccountSummary.updateSummary(paramConnection, paramAccount, paramAccountSummary, paramHashMap);
  }
  
  public static void addExtendedSummary(Account paramAccount, ExtendedAccountSummary paramExtendedAccountSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCExtAcctSummary.addExtendedSummary(paramAccount, paramExtendedAccountSummary, paramInt, paramConnection, paramHashMap);
  }
  
  public static ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCExtAcctSummary.getExtendedSummary(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      ExtendedAccountSummaries localExtendedAccountSummaries = getExtendedSummary(paramAccount, paramCalendar1, paramCalendar2, localConnection, paramHashMap);
      return localExtendedAccountSummaries;
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static int deleteExtendedAccountSummary(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt, String paramString, HashMap paramHashMap)
    throws DCException
  {
    return DCExtAcctSummary.deleteExtendedAccountSummary(paramConnection, paramAccount, paramCalendar1, paramCalendar2, paramInt, paramString, paramHashMap);
  }
  
  public static void updateExtendedSummary(Connection paramConnection, Account paramAccount, ExtendedAccountSummary paramExtendedAccountSummary, HashMap paramHashMap)
    throws DCException
  {
    DCExtAcctSummary.updateExtendedSummary(paramConnection, paramAccount, paramExtendedAccountSummary, paramHashMap);
  }
  
  public static void addHistory(Account paramAccount, AccountHistory paramAccountHistory, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCAccountHistory.addHistory(paramAccount, paramAccountHistory, paramInt, paramConnection, paramHashMap);
  }
  
  public static AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountHistory.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountHistory.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static void updateHistory(Connection paramConnection, Account paramAccount, AccountHistory paramAccountHistory, HashMap paramHashMap)
    throws DCException
  {
    DCAccountHistory.updateHistory(paramConnection, paramAccount, paramAccountHistory, paramHashMap);
  }
  
  public static void addLockboxSummary(LockboxSummary paramLockboxSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCLockboxSummary.add(paramLockboxSummary, paramInt, paramConnection, paramHashMap);
  }
  
  public static LockboxSummaries getLockboxSummaries(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCLockboxSummary.getLockboxSummaries(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static LockboxSummaries getLockboxSummaries(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      LockboxSummaries localLockboxSummaries = getLockboxSummaries(paramLockboxAccount, paramCalendar1, paramCalendar2, localConnection, paramHashMap);
      return localLockboxSummaries;
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static void updateLockboxSummary(Connection paramConnection, LockboxSummary paramLockboxSummary, HashMap paramHashMap)
    throws DCException
  {
    DCLockboxSummary.updateLockboxSummary(paramConnection, paramLockboxSummary, paramHashMap);
  }
  
  public static void addDisbursementSummary(DisbursementSummary paramDisbursementSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCDsbSummary.addDisbursementSummary(paramDisbursementSummary, paramInt, paramConnection, paramHashMap);
  }
  
  public static DisbursementSummaries getDisbursementSummaries(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbSummary.getDisbursementSummaries(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static DisbursementSummaries getDisbursementSummaries(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      DisbursementSummaries localDisbursementSummaries = getDisbursementSummaries(paramDisbursementAccount, paramCalendar1, paramCalendar2, localConnection, paramHashMap);
      return localDisbursementSummaries;
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static void updateDisbursementSummary(Connection paramConnection, DisbursementSummary paramDisbursementSummary, HashMap paramHashMap)
    throws DCException
  {
    DCDsbSummary.updateDisbursementSummary(paramConnection, paramDisbursementSummary, paramHashMap);
  }
  
  public static DisbursementSummaries getDisbursementSummariesForPresentment(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbSummary.getDisbursementSummariesForPresentment(paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static DisbursementPresentmentSummaries getDisbursementPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbSummary.getDisbursementPresentmentSummaries(paramDisbursementAccounts, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static void addDisbursementTransactions(DisbursementAccount paramDisbursementAccount, DisbursementTransactions paramDisbursementTransactions, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCDsbTransactions.addTransactions(paramDisbursementAccount, paramDisbursementTransactions, paramInt, paramConnection, paramHashMap);
  }
  
  public static DisbursementTransactions getDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbTransactions.getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static DisbursementTransactions getDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbTransactions.getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, paramHashMap);
  }
  
  public static DisbursementTransactions getPagedDisbursementTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbTransactions.getPagedTransactions(paramDisbursementAccount, paramPagingContext, paramHashMap);
  }
  
  public static DisbursementTransactions getPagedDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    DisbursementTransactions localDisbursementTransactions = getPagedDisbursementTransactions(paramDisbursementAccount, localPagingContext, paramHashMap);
    localDisbursementTransactions.setSortedBy("TRANSACTIONINDEX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getPagedDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    if (paramString != null) {
      localReportCriteria.getSearchCriteria().setProperty("Presentment", paramString);
    }
    DisbursementTransactions localDisbursementTransactions = getPagedDisbursementTransactions(paramDisbursementAccount, localPagingContext, paramHashMap);
    localDisbursementTransactions.setSortedBy("TRANSACTIONINDEX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getRecentDisbursementTransactions(DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DisbursementTransactions localDisbursementTransactions = DCDsbTransactions.getRecentTransactions(paramDisbursementAccount, localPagingContext, paramHashMap);
    localDisbursementTransactions.setSortedBy("TRANSACTIONINDEX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getNextDisbursementTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbTransactions.getNextTransactions(paramDisbursementAccount, paramPagingContext, paramHashMap);
  }
  
  public static DisbursementTransactions getNextDisbursementTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MAX_TransactionIndex", new Long(paramLong - 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DisbursementTransactions localDisbursementTransactions = getNextDisbursementTransactions(paramDisbursementAccount, localPagingContext, paramHashMap);
    localDisbursementTransactions.setSortedBy("TRANSACTIONINDEX");
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getPreviousDisbursementTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbTransactions.getPreviousTransactions(paramDisbursementAccount, paramPagingContext, paramHashMap);
  }
  
  public static DisbursementTransactions getPreviousDisbursementTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MIN_TransactionIndex", new Long(paramLong + 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DisbursementTransactions localDisbursementTransactions = getPreviousDisbursementTransactions(paramDisbursementAccount, localPagingContext, paramHashMap);
    localDisbursementTransactions.setSortedBy("TRANSACTIONINDEX");
    return localDisbursementTransactions;
  }
  
  public static void addLockboxTransactions(LockboxAccount paramLockboxAccount, LockboxTransactions paramLockboxTransactions, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCLBTransactions.addTransactions(paramLockboxAccount, paramLockboxTransactions, paramInt, paramConnection, paramHashMap);
  }
  
  public static LockboxTransactions getLockboxTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCLBTransactions.getTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static LockboxTransactions getLockboxTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCLBTransactions.getTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static LockboxTransactions getPagedLockboxTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCLBTransactions.getPagedTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
  }
  
  public static LockboxTransactions getPagedLockboxTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    localPagingContext.setMap(new HashMap());
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    LockboxTransactions localLockboxTransactions = getPagedLockboxTransactions(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxTransactions.setSortedBy("TRANS_INDEX");
    HashMap localHashMap = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap.get("UPPER_BOUND_TransactionIndex"));
    return localLockboxTransactions;
  }
  
  public static LockboxTransactions getRecentLockboxTransactions(LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    LockboxTransactions localLockboxTransactions = DCLBTransactions.getRecentTransactions(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxTransactions.setSortedBy("TRANS_INDEX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localLockboxTransactions;
  }
  
  public static LockboxTransactions getNextLockboxTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCLBTransactions.getNextTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
  }
  
  public static LockboxTransactions getNextLockboxTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MAX_TransactionIndex", new Long(paramLong - 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    LockboxTransactions localLockboxTransactions = getNextLockboxTransactions(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxTransactions.setSortedBy("TRANS_INDEX");
    return localLockboxTransactions;
  }
  
  public static LockboxTransactions getPreviousLockboxTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCLBTransactions.getPreviousTransactions(paramLockboxAccount, paramPagingContext, paramHashMap);
  }
  
  public static LockboxTransactions getPreviousLockboxTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MIN_TransactionIndex", new Long(paramLong + 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    LockboxTransactions localLockboxTransactions = getPreviousLockboxTransactions(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxTransactions.setSortedBy("TRANS_INDEX");
    return localLockboxTransactions;
  }
  
  public static void addLockboxCreditItems(LockboxAccount paramLockboxAccount, LockboxCreditItems paramLockboxCreditItems, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCLBCreditItems.addItems(paramLockboxAccount, paramLockboxCreditItems, paramInt, paramConnection, paramHashMap);
  }
  
  public static LockboxCreditItems getLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCLBCreditItems.getItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public static LockboxCreditItems getPagedLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    LockboxCreditItems localLockboxCreditItems = getPagedLockboxCreditItems(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxCreditItems.setSortedBy("ITEM_INDEX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localLockboxCreditItems;
  }
  
  public static LockboxCreditItems getPagedLockboxCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCLBCreditItems.getPagedItems(paramLockboxAccount, paramPagingContext, paramHashMap);
  }
  
  public static LockboxCreditItems getRecentLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    LockboxCreditItems localLockboxCreditItems = DCLBCreditItems.getRecentItems(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxCreditItems.setSortedBy("ITEM_INDEX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    return localLockboxCreditItems;
  }
  
  public static LockboxCreditItems getNextLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MAX_TransactionIndex", new Long(paramLong - 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    LockboxCreditItems localLockboxCreditItems = getNextLockboxCreditItems(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxCreditItems.setSortedBy("ITEM_INDEX");
    return localLockboxCreditItems;
  }
  
  public static LockboxCreditItems getNextLockboxCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCLBCreditItems.getNextItems(paramLockboxAccount, paramPagingContext, paramHashMap);
  }
  
  public static LockboxCreditItems getPreviousLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap = new HashMap();
    localHashMap.put("SORT_VALUE_MIN_TransactionIndex", new Long(paramLong + 1L));
    localPagingContext.setMap(localHashMap);
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", paramString);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    LockboxCreditItems localLockboxCreditItems = getPreviousLockboxCreditItems(paramLockboxAccount, localPagingContext, paramHashMap);
    localLockboxCreditItems.setSortedBy("ITEM_INDEX");
    return localLockboxCreditItems;
  }
  
  public static LockboxCreditItems getPreviousLockboxCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    return DCLBCreditItems.getPreviousItems(paramLockboxAccount, paramPagingContext, paramHashMap);
  }
  
  public static void undoFile(ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      undoFile(localConnection, paramImportedFile, paramImportedFiles, paramHashMap);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      localException.printStackTrace(new PrintWriter(System.err));
      throw new DCException(localException);
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  public static void undoFile(Connection paramConnection, ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException
  {
    if (isPagingDateBased()) {
      a(paramConnection, paramImportedFile, paramHashMap);
    } else {
      a(paramConnection, paramImportedFile, paramImportedFiles, paramHashMap);
    }
  }
  
  private static void a(Connection paramConnection, ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCAdapter.undoFileWithDateBasedPaging";
    try
    {
      a(paramConnection, paramImportedFile);
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
      throw new DCException(localException);
    }
  }
  
  private static void a(Connection paramConnection, ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCAdapter.undoFileWithConsecutivePaging";
    try
    {
      if (paramImportedFiles == null) {
        paramImportedFiles = getDependentFiles(paramImportedFile, paramHashMap);
      }
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      HashMap localHashMap3 = new HashMap();
      HashMap localHashMap4 = new HashMap();
      a(localHashMap1, localHashMap2, localHashMap3, localHashMap4, paramImportedFile, paramConnection);
      for (int i1 = 0; i1 < paramImportedFiles.size(); i1++)
      {
        ImportedFile localImportedFile = (ImportedFile)paramImportedFiles.get(i1);
        HashMap localHashMap5 = new HashMap();
        HashMap localHashMap6 = new HashMap();
        HashMap localHashMap7 = new HashMap();
        HashMap localHashMap8 = new HashMap();
        a(localHashMap5, localHashMap6, localHashMap7, localHashMap8, localImportedFile, paramConnection);
        a(localHashMap1, localHashMap5);
        a(localHashMap2, localHashMap6);
        a(localHashMap3, localHashMap7);
        a(localHashMap4, localHashMap8);
        a(paramConnection, localImportedFile);
        a(paramConnection, localHashMap5, localHashMap6, localHashMap7, localHashMap8);
      }
      a(paramConnection, paramImportedFile);
      a(paramConnection, localHashMap1, localHashMap2, localHashMap3, localHashMap4);
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
      throw new DCException(localException);
    }
  }
  
  public static ImportedFiles getImportedFileList(int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCAdapter.getImportedFileList";
    DebugLog.log(Level.FINE, str);
    Connection localConnection = null;
    HashSet localHashSet = new HashSet();
    Locale localLocale = paramHashMap.containsKey("LOCALE") ? (Locale)paramHashMap.get("LOCALE") : Locale.getDefault();
    try
    {
      localConnection = getDBConnection();
      getImportedFiles(" DC_DsbTransactions ", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles(" DC_LBCreditItems ", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles(" DC_LBTransactions ", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles(" DC_Transactions ", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles(" DC_MASTERSUB_REL ", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_AccountHistory", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_AccountSummary", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_CCAcctSummary", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_LoanAcctSummary", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_DsbSummary", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_LockboxSummary", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
      getImportedFiles("DC_ExtAcctSummary", localHashSet, localConnection, paramInt, paramCalendar1, paramCalendar2, localLocale);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(new PrintWriter(System.err));
      throw new DCException(localException);
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
    ImportedFiles localImportedFiles = createList(localHashSet);
    localImportedFiles.setSortedBy("IMPORTTIMEVALUE,REVERSE");
    return localImportedFiles;
  }
  
  public static ImportedFiles getDependentFiles(ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCAdapter.getDependentFiles";
    DebugLog.log(Level.FINE, str);
    Connection localConnection = null;
    try
    {
      localConnection = getDBConnection();
      ImportedFiles localImportedFiles = jdField_if(localConnection, paramImportedFile, paramHashMap);
      return localImportedFiles;
    }
    catch (Exception localException)
    {
      localException.printStackTrace(new PrintWriter(System.err));
      throw new DCException(localException);
    }
    finally
    {
      if (localConnection != null) {
        releaseDBConnection(localConnection);
      }
    }
  }
  
  private static ImportedFiles jdField_if(Connection paramConnection, ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCAdapter.getDependentFiles";
    DebugLog.log(Level.FINE, str);
    ImportedFiles localImportedFiles = null;
    if (isPagingDateBased()) {
      return new ImportedFiles();
    }
    try
    {
      GraphVertex localGraphVertex = new GraphVertex(paramImportedFile);
      DirectedGraph localDirectedGraph = new DirectedGraph(localGraphVertex);
      a(localGraphVertex, paramConnection, localDirectedGraph, paramImportedFile.getLocale());
      localImportedFiles = createList(localGraphVertex, localDirectedGraph);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(new PrintWriter(System.err));
      throw new DCException(localException);
    }
    return localImportedFiles;
  }
  
  private static void a(Connection paramConnection, int paramInt, HashMap paramHashMap)
    throws DCException
  {
    int i1 = -1;
    String str1 = null;
    switch (paramInt)
    {
    case 1: 
      i1 = 1;
      str1 = "DISBURSEMENT_TRANSACTION_INDEX";
      break;
    case 2: 
      i1 = 2;
      str1 = "LOCKBOX_CREDITITEM_INDEX";
      break;
    case 3: 
      i1 = 2;
      str1 = "LOCKBOX_TRANSACTION_INDEX";
      break;
    case 4: 
      i1 = 1;
      str1 = "ACCT_TRANSACTION_INDEX";
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, "UPDATE DC_RecordCounter SET NextIndex = ? WHERE DCObjectType = ? AND DCObjectID = ? AND CounterName = ? AND DataClassification = ? ");
      Set localSet = paramHashMap.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        Pair localPair = (Pair)localIterator.next();
        Integer localInteger = (Integer)localPair.getKey();
        String str2 = (String)localPair.getValue();
        Long localLong = (Long)paramHashMap.get(localPair);
        localPreparedStatement.setLong(1, localLong.longValue() - 1L);
        localPreparedStatement.setInt(2, i1);
        localPreparedStatement.setInt(3, localInteger.intValue());
        localPreparedStatement.setString(4, str1);
        localPreparedStatement.setString(5, str2);
        DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_RecordCounter SET NextIndex = ? WHERE DCObjectType = ? AND DCObjectID = ? AND CounterName = ? AND DataClassification = ? ");
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
  
  private static void a(Connection paramConnection, int paramInt, ImportedFile paramImportedFile)
    throws DCException
  {
    String str1 = null;
    switch (paramInt)
    {
    case 1: 
      str1 = " DC_DsbTransactions ";
      break;
    case 2: 
      str1 = " DC_LBCreditItems ";
      break;
    case 3: 
      str1 = " DC_LBTransactions ";
      break;
    case 4: 
      str1 = " DC_Transactions ";
      break;
    case 12: 
      str1 = " DC_MASTERSUB_REL ";
      break;
    case 5: 
      str1 = " DC_AccountHistory ";
      break;
    case 6: 
      str1 = " DC_AccountSummary ";
      break;
    case 7: 
      str1 = " DC_CCAcctSummary ";
      break;
    case 8: 
      str1 = " DC_LoanAcctSummary ";
      break;
    case 9: 
      str1 = " DC_DsbSummary";
      break;
    case 10: 
      str1 = " DC_LockboxSummary ";
      break;
    case 11: 
      str1 = " DC_ExtAcctSummary ";
    }
    a(paramConnection, str1, paramImportedFile);
    StringBuffer localStringBuffer = new StringBuffer("DELETE FROM ");
    localStringBuffer.append(str1);
    String str2 = a(str1, paramImportedFile);
    if ((str2 != null) && (str2.length() != 0)) {
      localStringBuffer.append(" WHERE ").append(str2);
    }
    String str3 = localStringBuffer.toString();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, str3);
      int i1 = 1;
      if (!str1.equals(" DC_MASTERSUB_REL ")) {
        localPreparedStatement.setInt(i1++, paramImportedFile.getDataSource());
      }
      if (paramImportedFile.getFileName() != null) {
        localPreparedStatement.setString(i1++, paramImportedFile.getFileName());
      }
      if (paramImportedFile.getImportTimeValue() != null) {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramImportedFile.getImportTimeValue());
      }
      if (paramImportedFile.getFileID() != null) {
        localPreparedStatement.setString(i1++, paramImportedFile.getFileID());
      }
      DBUtil.executeUpdate(localPreparedStatement, str3);
    }
    catch (Exception localException)
    {
      localException = localException;
      throw new DCException(localException);
    }
    finally {}
  }
  
  private static void a(HashMap paramHashMap1, HashMap paramHashMap2)
  {
    Iterator localIterator = paramHashMap2.keySet().iterator();
    while (localIterator.hasNext())
    {
      Pair localPair = (Pair)localIterator.next();
      Long localLong1 = (Long)paramHashMap2.get(localPair);
      Long localLong2 = (Long)paramHashMap1.get(localPair);
      if ((localLong2 == null) || (localLong1.longValue() < localLong2.longValue())) {
        paramHashMap1.put(localPair, localLong1);
      }
    }
  }
  
  private static void a(HashMap paramHashMap, Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, String paramString1, DateTime paramDateTime, String paramString2)
    throws DCException
  {
    String str1 = "DCAdapter.getMinOrMaxIndexValuesForIDs";
    DebugLog.log(Level.FINE, str1);
    StringBuffer localStringBuffer = new StringBuffer(" SELECT ");
    if (paramInt2 == 2) {
      localStringBuffer.append(" DCLockboxID ");
    } else {
      localStringBuffer.append(" DCAccountID ");
    }
    localStringBuffer.append(", ");
    if (paramInt1 == 1) {
      localStringBuffer.append(" min( ");
    } else {
      localStringBuffer.append(" max( ");
    }
    if (paramInt2 == 2) {
      localStringBuffer.append(" DCCreditItemIndex ");
    } else {
      localStringBuffer.append(" DCTransactionIndex ");
    }
    localStringBuffer.append(") , ");
    localStringBuffer.append(" DataClassification ");
    localStringBuffer.append(" FROM ");
    switch (paramInt2)
    {
    case 1: 
      localStringBuffer.append(" DC_DsbTransactions ");
      break;
    case 2: 
      localStringBuffer.append(" DC_LBCreditItems ");
      break;
    case 3: 
      localStringBuffer.append(" DC_LBTransactions ");
      break;
    case 4: 
      localStringBuffer.append(" DC_Transactions ");
    }
    localStringBuffer.append(" WHERE DataSource=? ");
    localStringBuffer.append(" AND ");
    if (paramString1 == null)
    {
      if (paramInt2 == 12) {
        localStringBuffer.append(" DATASOURCEFILENAME is null ");
      } else {
        localStringBuffer.append(" DataSourceFileName is null ");
      }
    }
    else if (paramInt2 == 12) {
      localStringBuffer.append(" DATASOURCEFILENAME = ? ");
    } else {
      localStringBuffer.append(" DataSourceFileName = ? ");
    }
    localStringBuffer.append(" AND ");
    if (paramDateTime == null)
    {
      if (paramInt2 == 12) {
        localStringBuffer.append(" DATASOURCEFILEDATE is null ");
      } else {
        localStringBuffer.append(" DataSourceFileDate is null ");
      }
    }
    else if (paramInt2 == 12) {
      localStringBuffer.append(" DATASOURCEFILEDATE = ? ");
    } else {
      localStringBuffer.append(" DataSourceFileDate = ? ");
    }
    localStringBuffer.append(" AND ");
    if (paramString2 == null)
    {
      if (paramInt2 == 12) {
        localStringBuffer.append(" BAIFILEIDENTIFIER is null ");
      } else {
        localStringBuffer.append(" BAIFileIdentifier is null ");
      }
    }
    else if (paramInt2 == 12) {
      localStringBuffer.append(" BAIFILEIDENTIFIER = ? ");
    } else {
      localStringBuffer.append(" BAIFileIdentifier = ? ");
    }
    localStringBuffer.append(" GROUP BY ");
    if (paramInt2 == 2) {
      localStringBuffer.append(" DCLockboxID ");
    } else {
      localStringBuffer.append(" DCAccountID ");
    }
    localStringBuffer.append(", ");
    localStringBuffer.append(" DataClassification ");
    PreparedStatement localPreparedStatement = null;
    String str2 = localStringBuffer.toString();
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, str2);
      int i1 = 1;
      localPreparedStatement.setInt(i1++, paramInt3);
      if (paramString1 != null) {
        localPreparedStatement.setString(i1++, paramString1);
      }
      if (paramDateTime != null) {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramDateTime);
      }
      if (paramString2 != null) {
        localPreparedStatement.setString(i1++, paramString2);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      paramHashMap.clear();
      while (localResultSet.next())
      {
        int i2 = localResultSet.getInt(1);
        long l1 = localResultSet.getLong(2);
        String str3 = localResultSet.getString(3);
        paramHashMap.put(new Pair(new Integer(i2), str3), new Long(l1));
      }
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
  
  private static String a(String paramString, int paramInt)
  {
    String str = "DCAdapter.buildDependentFileListQuery";
    DebugLog.log(Level.INFO, str);
    StringBuffer localStringBuffer = new StringBuffer("SELECT distinct ");
    localStringBuffer.append("DataSource");
    localStringBuffer.append(", ");
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("BAIFILEIDENTIFIER");
    } else {
      localStringBuffer.append("BAIFileIdentifier");
    }
    localStringBuffer.append(", ");
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("DATASOURCEFILENAME");
    } else {
      localStringBuffer.append("DataSourceFileName");
    }
    localStringBuffer.append(", ");
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("DATASOURCEFILEDATE");
    } else {
      localStringBuffer.append("DataSourceFileDate");
    }
    localStringBuffer.append(", ");
    localStringBuffer.append("DataClassification");
    localStringBuffer.append(" FROM ");
    localStringBuffer.append(paramString);
    localStringBuffer.append(" WHERE ");
    if (paramInt == 100)
    {
      localStringBuffer.append("DCAccountID");
      localStringBuffer.append(" = ? AND ");
      localStringBuffer.append("DCTransactionIndex");
      localStringBuffer.append(" > ? ");
    }
    else if (paramInt == 200)
    {
      localStringBuffer.append("DCLockboxID");
      localStringBuffer.append(" = ? AND ");
      localStringBuffer.append("DCCreditItemIndex");
      localStringBuffer.append(" > ? ");
    }
    localStringBuffer.append(" AND ");
    localStringBuffer.append("DataClassification");
    localStringBuffer.append(" = ? ");
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    String str = "DCAdapter.buildImportedFileListQuery";
    DebugLog.log(Level.FINE, str);
    StringBuffer localStringBuffer = new StringBuffer("SELECT distinct ");
    if (!paramString.equals(" DC_MASTERSUB_REL "))
    {
      localStringBuffer.append("DataSource");
      localStringBuffer.append(", ");
    }
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("BAIFILEIDENTIFIER");
    } else {
      localStringBuffer.append("BAIFileIdentifier");
    }
    localStringBuffer.append(", ");
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("DATASOURCEFILENAME");
    } else {
      localStringBuffer.append("DataSourceFileName");
    }
    localStringBuffer.append(", ");
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("DATASOURCEFILEDATE");
    } else {
      localStringBuffer.append("DataSourceFileDate");
    }
    if (!paramString.equals(" DC_MASTERSUB_REL "))
    {
      localStringBuffer.append(", ");
      localStringBuffer.append("DataClassification");
    }
    localStringBuffer.append(" FROM ");
    localStringBuffer.append(paramString);
    int i1 = 0;
    if (!paramString.equals(" DC_MASTERSUB_REL "))
    {
      localStringBuffer.append(" WHERE ");
      localStringBuffer.append("DataSource");
      localStringBuffer.append(" = ? ");
      i1 = 1;
    }
    if ((paramCalendar1 != null) || (paramCalendar2 != null))
    {
      if (i1 != 0) {
        localStringBuffer.append(" AND ");
      } else {
        localStringBuffer.append(" WHERE ");
      }
      localStringBuffer.append(" ( ( ");
      if (paramString.equals(" DC_MASTERSUB_REL ")) {
        localStringBuffer.append("DATASOURCEFILEDATE");
      } else {
        localStringBuffer.append("DataSourceFileDate");
      }
    }
    if ((paramCalendar1 != null) && (paramCalendar2 != null))
    {
      localStringBuffer.append(" >= ? AND ");
      if (paramString.equals(" DC_MASTERSUB_REL ")) {
        localStringBuffer.append("DATASOURCEFILEDATE");
      } else {
        localStringBuffer.append("DataSourceFileDate");
      }
      localStringBuffer.append(" <= ? ");
    }
    else if (paramCalendar1 != null)
    {
      localStringBuffer.append(" >= ? ");
    }
    else if (paramCalendar2 != null)
    {
      localStringBuffer.append(" <= ? ");
    }
    if ((paramCalendar1 != null) || (paramCalendar2 != null))
    {
      localStringBuffer.append(" ) OR ");
      if (paramString.equals(" DC_MASTERSUB_REL ")) {
        localStringBuffer.append("DATASOURCEFILEDATE");
      } else {
        localStringBuffer.append("DataSourceFileDate");
      }
      localStringBuffer.append(" is NULL ) ");
    }
    localStringBuffer.append(" ORDER BY ");
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append("DATASOURCEFILEDATE");
    } else {
      localStringBuffer.append("DataSourceFileDate");
    }
    localStringBuffer.append(" DESC ");
    return localStringBuffer.toString();
  }
  
  public static void getImportedFiles(String paramString, HashSet paramHashSet, Connection paramConnection, int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, Locale paramLocale)
    throws Exception
  {
    String str1 = "DCAdapter.getImportedFiles";
    DebugLog.log(Level.FINE, str1);
    String str2 = a(paramString, paramCalendar1, paramCalendar2);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, str2);
      int i1 = 1;
      if (!paramString.equals(" DC_MASTERSUB_REL ")) {
        localPreparedStatement.setInt(i1++, paramInt);
      }
      if ((paramCalendar1 != null) && (paramCalendar2 != null))
      {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
      }
      else if ((paramCalendar1 != null) && (paramCalendar2 == null))
      {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
      }
      else if ((paramCalendar1 == null) && (paramCalendar2 != null))
      {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      String str3 = null;
      String str4 = null;
      DateTime localDateTime = null;
      String str5 = null;
      while (localResultSet.next())
      {
        int i3 = 1;
        int i2;
        if (!paramString.equals(" DC_MASTERSUB_REL ")) {
          i2 = localResultSet.getInt(i3++);
        } else {
          i2 = 2;
        }
        str4 = localResultSet.getString(i3++);
        str3 = localResultSet.getString(i3++);
        localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(i3++), paramLocale);
        if (!paramString.equals(" DC_MASTERSUB_REL ")) {
          str5 = localResultSet.getString(i3++);
        } else {
          str5 = "P";
        }
        ImportedFile localImportedFile = new ImportedFile(i2, str4, str3, localDateTime, str5);
        localImportedFile.setLocale(paramLocale);
        paramHashSet.add(localImportedFile);
      }
      return;
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
  
  public static final ImportedFiles createList(HashSet paramHashSet)
  {
    String str = "DCAdapter.createList(HashSet)";
    DebugLog.log(Level.FINE, str);
    ImportedFiles localImportedFiles = new ImportedFiles();
    Iterator localIterator = paramHashSet.iterator();
    while (localIterator.hasNext()) {
      localImportedFiles.add((ImportedFile)localIterator.next());
    }
    return localImportedFiles;
  }
  
  public static final ImportedFiles createList(GraphVertex paramGraphVertex, DirectedGraph paramDirectedGraph)
  {
    String str = "DCAdapter.createList(DirectedGraph)";
    DebugLog.log(Level.FINE, str);
    ImportedFiles localImportedFiles = new ImportedFiles();
    while (paramDirectedGraph.getSize() > 0)
    {
      for (GraphVertex localGraphVertex = paramGraphVertex; !localGraphVertex.isLeaf(); localGraphVertex = localGraphVertex.getNextOutgoingVertex()) {}
      paramDirectedGraph.removeVertexUpdateTree(localGraphVertex);
      if (!localGraphVertex.equals(paramGraphVertex)) {
        localImportedFiles.add((ImportedFile)localGraphVertex.getKey());
      }
    }
    return localImportedFiles;
  }
  
  private static void a(int paramInt, HashMap paramHashMap, Connection paramConnection, ImportedFile paramImportedFile)
    throws DCException
  {
    String str = "DCAdapter.getIdsAndIndexes";
    DebugLog.log(Level.FINE, str);
    try
    {
      HashMap localHashMap = new HashMap();
      a(localHashMap, paramConnection, 2, paramInt, paramImportedFile.getDataSource(), paramImportedFile.getFileName(), paramImportedFile.getImportTimeValue(), paramImportedFile.getFileID());
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        Pair localPair = (Pair)localIterator.next();
        Long localLong = (Long)localHashMap.get(localPair);
        boolean bool = paramHashMap.containsKey(localPair);
        if ((!bool) || ((bool) && (((Integer)paramHashMap.get(localPair)).compareTo(localLong) < 0))) {
          paramHashMap.put(localPair, localLong);
        }
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
  }
  
  private static void a(String paramString, Connection paramConnection, HashMap paramHashMap, HashSet paramHashSet, int paramInt, Locale paramLocale)
    throws DCException
  {
    String str1 = "DCAdapter.getDependentFileList";
    DebugLog.log(Level.FINE, str1);
    String str2 = a(paramString, paramInt);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Pair localPair = (Pair)localIterator.next();
      Integer localInteger = (Integer)localPair.getKey();
      String str3 = (String)localPair.getValue();
      Long localLong = (Long)paramHashMap.get(localPair);
      try
      {
        localPreparedStatement = getStatement(paramConnection, str2);
        localPreparedStatement.setInt(1, localInteger.intValue());
        localPreparedStatement.setLong(2, localLong.longValue());
        localPreparedStatement.setString(3, str3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
        String str4 = null;
        String str5 = null;
        DateTime localDateTime = null;
        String str6 = null;
        while (localResultSet.next())
        {
          int i1 = localResultSet.getInt(1);
          str5 = localResultSet.getString(2);
          str4 = localResultSet.getString(3);
          localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(4), paramLocale);
          str6 = localResultSet.getString(5);
          ImportedFile localImportedFile = new ImportedFile(i1, str5, str4, localDateTime, str6);
          localImportedFile.setLocale(paramLocale);
          paramHashSet.add(localImportedFile);
        }
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
  }
  
  private static void a(GraphVertex paramGraphVertex, Connection paramConnection, DirectedGraph paramDirectedGraph, Locale paramLocale)
    throws DCException
  {
    String str = "DCAdapter.getDependentFilesRecursive";
    DebugLog.log(Level.FINE, str);
    ImportedFile localImportedFile = (ImportedFile)paramGraphVertex.getKey();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    HashMap localHashMap4 = new HashMap();
    HashSet localHashSet = new HashSet();
    a(1, localHashMap1, paramConnection, localImportedFile);
    a(3, localHashMap2, paramConnection, localImportedFile);
    a(4, localHashMap3, paramConnection, localImportedFile);
    a(2, localHashMap4, paramConnection, localImportedFile);
    a(" DC_DsbTransactions ", paramConnection, localHashMap1, localHashSet, 100, paramLocale);
    a(" DC_LBTransactions ", paramConnection, localHashMap2, localHashSet, 100, paramLocale);
    a(" DC_Transactions ", paramConnection, localHashMap3, localHashSet, 100, paramLocale);
    a(" DC_LBCreditItems ", paramConnection, localHashMap4, localHashSet, 200, paramLocale);
    Iterator localIterator = localHashSet.iterator();
    while (localIterator.hasNext())
    {
      GraphVertex localGraphVertex = new GraphVertex((ImportedFile)localIterator.next());
      if (paramDirectedGraph.containsVertex(localGraphVertex)) {
        localGraphVertex = paramDirectedGraph.getVertex(localGraphVertex);
      } else {
        paramDirectedGraph.addVertex(localGraphVertex);
      }
      paramGraphVertex.addOutgoingVertex(localGraphVertex);
      localGraphVertex.addIncomingVertex(paramGraphVertex);
      a(localGraphVertex, paramConnection, paramDirectedGraph, paramLocale);
    }
  }
  
  private static void a(HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3, HashMap paramHashMap4, ImportedFile paramImportedFile, Connection paramConnection)
    throws DCException
  {
    a(paramHashMap1, paramConnection, 1, 1, paramImportedFile.getDataSource(), paramImportedFile.getFileName(), paramImportedFile.getImportTimeValue(), paramImportedFile.getFileID());
    a(paramHashMap2, paramConnection, 1, 3, paramImportedFile.getDataSource(), paramImportedFile.getFileName(), paramImportedFile.getImportTimeValue(), paramImportedFile.getFileID());
    a(paramHashMap3, paramConnection, 1, 4, paramImportedFile.getDataSource(), paramImportedFile.getFileName(), paramImportedFile.getImportTimeValue(), paramImportedFile.getFileID());
    a(paramHashMap4, paramConnection, 1, 2, paramImportedFile.getDataSource(), paramImportedFile.getFileName(), paramImportedFile.getImportTimeValue(), paramImportedFile.getFileID());
  }
  
  private static void a(Connection paramConnection, ImportedFile paramImportedFile)
    throws DCException
  {
    a(paramConnection, 1, paramImportedFile);
    a(paramConnection, 3, paramImportedFile);
    a(paramConnection, 4, paramImportedFile);
    a(paramConnection, 12, paramImportedFile);
    a(paramConnection, 2, paramImportedFile);
    a(paramConnection, 5, paramImportedFile);
    a(paramConnection, 5, paramImportedFile);
    a(paramConnection, 11, paramImportedFile);
    a(paramConnection, 6, paramImportedFile);
    a(paramConnection, 7, paramImportedFile);
    a(paramConnection, 8, paramImportedFile);
    a(paramConnection, 9, paramImportedFile);
    a(paramConnection, 10, paramImportedFile);
  }
  
  private static void a(Connection paramConnection, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3, HashMap paramHashMap4)
    throws DCException
  {
    if (paramHashMap1.size() != 0) {
      a(paramConnection, 1, paramHashMap1);
    }
    if (paramHashMap2.size() != 0) {
      a(paramConnection, 3, paramHashMap2);
    }
    if (paramHashMap3.size() != 0) {
      a(paramConnection, 4, paramHashMap3);
    }
    if (paramHashMap4.size() != 0) {
      a(paramConnection, 2, paramHashMap4);
    }
  }
  
  public static IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    throw new DCException("This method is no longer supported");
  }
  
  public static IReportResult getDisbursementReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    return null;
  }
  
  public static IReportResult getLockboxReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    return DCLockboxReport.getReportData(paramReportCriteria, paramHashMap);
  }
  
  public static ArrayList getHistoryMapList(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountHistory.getHistoryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static ArrayList getSummaryMapList(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountSummary.getSummaryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static ArrayList getDisbursementSummaryMapList(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbSummary.getDisbursementSummaryMapList(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static ArrayList getExtendedSummaryMapList(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCExtAcctSummary.getExtendedSummaryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static ArrayList getLockboxSummaryMapList(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCLockboxSummary.getLockboxSummaryMapList(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static Transaction getLatestTransactionByAccountDates(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getLatestTransactionByAccountDates(paramAccount, paramCalendar1, paramCalendar2, paramString, paramConnection, paramHashMap);
  }
  
  public static void modifyDataSourceLoadTime(Connection paramConnection, DateTime paramDateTime, ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    DCTransactions.modifyDataSourceLoadTime(paramConnection, paramDateTime, paramImportedFile, paramHashMap);
  }
  
  protected static boolean isPagingDateBased()
  {
    return PAGING_MECHANISM.equals(PAGING_MECHANISM_DATE_BASED);
  }
  
  public static String padRefNum(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return null;
    }
    if (paramString2.equals("Integer"))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      int i1 = paramString1.length();
      try
      {
        byte[] arrayOfByte = paramString1.getBytes("UTF-8");
        i1 = arrayOfByte.length;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
      for (int i2 = 40 - i1; i2 > 0; i2--) {
        localStringBuffer.append(" ");
      }
      paramString1 = localStringBuffer.toString() + paramString1;
    }
    return paramString1;
  }
  
  public static String unPadRefNum(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return null;
    }
    if (paramString2.equals("Integer")) {
      return paramString1.trim();
    }
    return paramString1;
  }
  
  public static void clearTransactionTypes(Connection paramConnection)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, c);
      DBUtil.executeUpdate(localPreparedStatement, c);
    }
    catch (Exception localException)
    {
      DCException localDCException = new DCException("Error occurred while trying to clear transaction types.", localException);
      if ((localException instanceof SQLException)) {
        localDCException.setCode(302);
      } else {
        localDCException.setCode(1023);
      }
      throw localDCException;
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  public static void loadTransactionTypes(Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = getStatement(paramConnection, jdField_try);
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (Integer)localIterator.next();
        TransactionTypeInfo localTransactionTypeInfo = (TransactionTypeInfo)paramHashMap.get(localObject1);
        ArrayList localArrayList = localTransactionTypeInfo.getLanguages();
        for (int i2 = 0; i2 < localArrayList.size(); i2++)
        {
          Locale localLocale = (Locale)localArrayList.get(i2);
          localPreparedStatement.setInt(1, ((Integer)localObject1).intValue());
          localPreparedStatement.setString(2, localTransactionTypeInfo.getDescription(localLocale));
          localPreparedStatement.setString(3, localLocale.toString());
          localPreparedStatement.addBatch();
        }
      }
      localObject1 = localPreparedStatement.executeBatch();
      localPreparedStatement.clearBatch();
      for (int i1 = 0; i1 < localObject1.length; i1++) {
        if ((localObject1[i1] < 1) && (localObject1[i1] != -2))
        {
          DebugLog.log(Level.SEVERE, "Error loading transaction type ERROR=" + localObject1[i1]);
          throw new DCException(1024, "Error occurred while trying to load transaction types.");
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException("Error occurred while trying to load transaction types.", localException);
      if ((localException instanceof SQLException)) {
        ((DCException)localObject1).setCode(302);
      } else {
        ((DCException)localObject1).setCode(1024);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(Connection paramConnection, String paramString, ImportedFile paramImportedFile)
    throws DCException
  {
    if (paramString.equals(" DC_MASTERSUB_REL ")) {
      return;
    }
    String str1 = "DCAdapter.deleteExtendABeanXMLByImportedFile";
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("DELETE FROM DC_ExtendABeanXML WHERE DCExtendABeanID IN ( SELECT ExtendABeanXMLID FROM ");
      localStringBuffer.append(paramString);
      String str2 = a(paramString, paramImportedFile);
      if ((str2 != null) && (str2.length() != 0)) {
        localStringBuffer.append(" WHERE ").append(str2);
      }
      localStringBuffer.append(" )");
      String str3 = localStringBuffer.toString();
      localPreparedStatement = getStatement(paramConnection, str3);
      int i1 = 1;
      if (!paramString.equals(" DC_MASTERSUB_REL ")) {
        localPreparedStatement.setInt(i1++, paramImportedFile.getDataSource());
      }
      if (paramImportedFile.getFileName() != null) {
        localPreparedStatement.setString(i1++, paramImportedFile.getFileName());
      }
      if (paramImportedFile.getImportTimeValue() != null) {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramImportedFile.getImportTimeValue());
      }
      if (paramImportedFile.getFileID() != null) {
        localPreparedStatement.setString(i1++, paramImportedFile.getFileID());
      }
      DBUtil.executeUpdate(localPreparedStatement, str3);
    }
    catch (DCException localDCException)
    {
      localDCException = localDCException;
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
    catch (Exception localException)
    {
      localException = localException;
      DebugLog.throwing(str1, localException);
      throw new DCException(localException);
    }
    finally {}
  }
  
  private static String a(String paramString, ImportedFile paramImportedFile)
  {
    int i1 = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    if (!paramString.equals(" DC_MASTERSUB_REL "))
    {
      localStringBuffer.append(" DataSource = ? ");
      i1 = 1;
    }
    if (i1 != 0) {
      localStringBuffer.append(" AND ");
    }
    i1 = 1;
    if (paramImportedFile.getFileName() == null)
    {
      if (paramString.equals(" DC_MASTERSUB_REL ")) {
        localStringBuffer.append(" DATASOURCEFILENAME is null ");
      } else {
        localStringBuffer.append(" DataSourceFileName is null ");
      }
    }
    else if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append(" DATASOURCEFILENAME = ? ");
    } else {
      localStringBuffer.append(" DataSourceFileName = ? ");
    }
    localStringBuffer.append(" AND ");
    if (paramImportedFile.getImportTimeValue() == null)
    {
      if (paramString.equals(" DC_MASTERSUB_REL ")) {
        localStringBuffer.append(" DATASOURCEFILEDATE is null ");
      } else {
        localStringBuffer.append(" DataSourceFileDate is null ");
      }
    }
    else if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append(" DATASOURCEFILEDATE = ? ");
    } else {
      localStringBuffer.append(" DataSourceFileDate = ? ");
    }
    localStringBuffer.append(" AND ");
    if (paramImportedFile.getFileID() == null)
    {
      if (paramString.equals(" DC_MASTERSUB_REL ")) {
        localStringBuffer.append(" BAIFILEIDENTIFIER is null ");
      } else {
        localStringBuffer.append(" BAIFileIdentifier is null ");
      }
    }
    else if (paramString.equals(" DC_MASTERSUB_REL ")) {
      localStringBuffer.append(" BAIFILEIDENTIFIER = ? ");
    } else {
      localStringBuffer.append(" BAIFileIdentifier = ? ");
    }
    return localStringBuffer.toString();
  }
  
  public static void setPagingMechanism(String paramString)
  {
    PAGING_MECHANISM = (paramString == null) || (!paramString.equalsIgnoreCase(PAGING_MECHANISM_DATE_BASED)) ? PAGING_MECHANISM_CONSECUTIVE : PAGING_MECHANISM_DATE_BASED;
  }
  
  public static String getPagingMechanism()
  {
    return PAGING_MECHANISM;
  }
  
  public static void addMasterSubRelations(Connection paramConnection, DateTime paramDateTime, MasterSubRelation paramMasterSubRelation, HashMap paramHashMap)
    throws DCException
  {
    if (paramConnection == null) {
      throw new DCException(21200, "Null database connection supplied.");
    }
    if (paramDateTime == null) {
      throw new DCException(21201, "Null data date supplied.");
    }
    if (paramMasterSubRelation == null) {
      throw new DCException(21202, "Null master-sub relation supplied.");
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str1 = null;
      Calendar localCalendar = null;
      String str2 = null;
      if (paramHashMap != null)
      {
        str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
        localCalendar = (Calendar)paramHashMap.get("BAI_FILE_DATE");
        str2 = (String)paramHashMap.get("BAI_FILE_NAME");
      }
      localPreparedStatement = getStatement(paramConnection, "INSERT INTO DC_MASTERSUB_REL (DATADATE, BANK_ID, BUS_DIRECTORY_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, SUB_ROUTING_NUM, SUB_ACCOUNT_ID, LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, DATASOURCEFILENAME, DATASOURCEFILEDATE, BAIFILEIDENTIFIER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramDateTime);
      localPreparedStatement.setString(2, paramMasterSubRelation.getBankId());
      localPreparedStatement.setInt(3, paramMasterSubRelation.getBusinessId());
      localPreparedStatement.setString(4, paramMasterSubRelation.getMasterRoutingNumber());
      localPreparedStatement.setString(5, paramMasterSubRelation.getMasterAccountId());
      localPreparedStatement.setString(6, paramMasterSubRelation.getSubRoutingNumber());
      localPreparedStatement.setString(7, paramMasterSubRelation.getSubAccountId());
      localPreparedStatement.setString(8, paramMasterSubRelation.getLocationId());
      if (paramMasterSubRelation.getIncludeZBACreditInRollup()) {
        localPreparedStatement.setString(9, "Y");
      } else {
        localPreparedStatement.setString(9, "N");
      }
      if (paramMasterSubRelation.getIncludeZBADebitInRollup()) {
        localPreparedStatement.setString(10, "Y");
      } else {
        localPreparedStatement.setString(10, "N");
      }
      if (paramMasterSubRelation.getWithholdNZBSubAccounts()) {
        localPreparedStatement.setString(11, "Y");
      } else {
        localPreparedStatement.setString(11, "N");
      }
      localPreparedStatement.setInt(12, paramMasterSubRelation.getLocationIdPlacement());
      localPreparedStatement.setString(13, str2);
      DCUtil.fillTimestampColumn(localPreparedStatement, 14, localCalendar);
      localPreparedStatement.setString(15, str1);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_MASTERSUB_REL (DATADATE, BANK_ID, BUS_DIRECTORY_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, SUB_ROUTING_NUM, SUB_ACCOUNT_ID, LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, DATASOURCEFILENAME, DATASOURCEFILEDATE, BAIFILEIDENTIFIER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
  
  public static Accounts getMasterAccounts(Connection paramConnection, DateTime paramDateTime, int paramInt, Account paramAccount, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement = getStatement(paramConnection, "SELECT MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, WITHHOLD_NZB_SUB FROM DC_MASTERSUB_REL WHERE SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ? AND SUB_ROUTING_NUM = ?");
      } else {
        localPreparedStatement = getStatement(paramConnection, "SELECT MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, WITHHOLD_NZB_SUB FROM DC_MASTERSUB_REL WHERE SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ? AND SUB_ROUTING_NUM IS NULL");
      }
      localPreparedStatement.setString(1, paramAccount.getID());
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramDateTime);
      localPreparedStatement.setInt(3, paramInt);
      localPreparedStatement.setString(4, paramAccount.getBankID());
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(5, paramAccount.getRoutingNum());
      }
      if (paramAccount.getRoutingNum() != null) {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, WITHHOLD_NZB_SUB FROM DC_MASTERSUB_REL WHERE SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ? AND SUB_ROUTING_NUM = ?");
      } else {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, WITHHOLD_NZB_SUB FROM DC_MASTERSUB_REL WHERE SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ? AND SUB_ROUTING_NUM IS NULL");
      }
      Accounts localAccounts = new Accounts();
      while (localResultSet.next())
      {
        localObject1 = localAccounts.create(paramAccount.getBankID(), localResultSet.getString(2), localResultSet.getString(2), 0);
        ((Account)localObject1).setRoutingNum(localResultSet.getString(1));
        ((Account)localObject1).setWithholdNonZeroBalanceSubAccounts(localResultSet.getString(3));
      }
      Object localObject1 = localAccounts;
      return localObject1;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localPreparedStatement = null;
    }
  }
  
  public static MasterSubRelation getMasterSubRelation(Connection paramConnection, int paramInt, DateTime paramDateTime, Account paramAccount1, Account paramAccount2, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if ((paramAccount1.getRoutingNum() != null) && (paramAccount2.getRoutingNum() != null))
      {
        localPreparedStatement = getStatement(paramConnection, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
        localPreparedStatement.setString(1, paramAccount1.getRoutingNum());
        localPreparedStatement.setString(2, paramAccount1.getID());
        localPreparedStatement.setString(3, paramAccount2.getRoutingNum());
        localPreparedStatement.setString(4, paramAccount2.getID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramDateTime);
        localPreparedStatement.setInt(6, paramInt);
        localPreparedStatement.setString(7, paramAccount1.getBankID());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
      }
      else if (paramAccount1.getRoutingNum() != null)
      {
        localPreparedStatement = getStatement(paramConnection, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
        localPreparedStatement.setString(1, paramAccount1.getRoutingNum());
        localPreparedStatement.setString(2, paramAccount1.getID());
        localPreparedStatement.setString(3, paramAccount2.getID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramDateTime);
        localPreparedStatement.setInt(5, paramInt);
        localPreparedStatement.setString(6, paramAccount1.getBankID());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM = ? AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
      }
      else if (paramAccount2.getRoutingNum() != null)
      {
        localPreparedStatement = getStatement(paramConnection, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
        localPreparedStatement.setString(1, paramAccount1.getID());
        localPreparedStatement.setString(2, paramAccount2.getRoutingNum());
        localPreparedStatement.setString(3, paramAccount2.getID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramDateTime);
        localPreparedStatement.setInt(5, paramInt);
        localPreparedStatement.setString(6, paramAccount1.getBankID());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM = ? AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
      }
      else
      {
        localPreparedStatement = getStatement(paramConnection, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
        localPreparedStatement.setString(1, paramAccount1.getID());
        localPreparedStatement.setString(2, paramAccount2.getID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramDateTime);
        localPreparedStatement.setInt(4, paramInt);
        localPreparedStatement.setString(5, paramAccount1.getBankID());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP, WITHHOLD_NZB_SUB, LOCATIONIDPLACE, BAIFILEIDENTIFIER, DATASOURCEFILENAME, DATASOURCEFILEDATE FROM DC_MASTERSUB_REL WHERE MASTER_ROUTING_NUM IS NULL AND MASTER_ACCOUNT_ID = ? AND SUB_ROUTING_NUM IS NULL AND SUB_ACCOUNT_ID = ? AND DATADATE = ? AND BUS_DIRECTORY_ID = ? AND BANK_ID = ?");
      }
      if (localResultSet.next())
      {
        localMasterSubRelation1 = new MasterSubRelation();
        localMasterSubRelation1.setBankId(paramAccount1.getBankID());
        localMasterSubRelation1.setBusinessId(paramInt);
        localMasterSubRelation1.setDataDate(paramDateTime);
        localMasterSubRelation1.setMasterRoutingNumber(paramAccount1.getRoutingNum());
        localMasterSubRelation1.setMasterAccountId(paramAccount1.getID());
        localMasterSubRelation1.setSubRoutingNumber(paramAccount2.getRoutingNum());
        localMasterSubRelation1.setSubAccountId(paramAccount2.getID());
        localMasterSubRelation1.setLocationId(localResultSet.getString(1));
        if (localResultSet.getString(2).equalsIgnoreCase("y")) {
          localMasterSubRelation1.setIncludeZBACreditInRollup(true);
        } else {
          localMasterSubRelation1.setIncludeZBACreditInRollup(false);
        }
        if (localResultSet.getString(3).equalsIgnoreCase("y")) {
          localMasterSubRelation1.setIncludeZBADebitInRollup(true);
        } else {
          localMasterSubRelation1.setIncludeZBADebitInRollup(false);
        }
        if (localResultSet.getString(4).equalsIgnoreCase("y")) {
          localMasterSubRelation1.setWithholdNZBSubAccounts(true);
        } else {
          localMasterSubRelation1.setWithholdNZBSubAccounts(false);
        }
        localMasterSubRelation1.setLocationIdPlacement(localResultSet.getInt(5));
        localMasterSubRelation1.setBAIFileIdentifier(localResultSet.getString(6));
        localMasterSubRelation1.setBAIFileName(localResultSet.getString(7));
        localMasterSubRelation1.setBAIFileDate(DCUtil.getTimestampColumn(localResultSet.getTimestamp(8), paramAccount1.getLocale()));
        MasterSubRelation localMasterSubRelation2 = localMasterSubRelation1;
        return localMasterSubRelation2;
      }
      MasterSubRelation localMasterSubRelation1 = null;
      return localMasterSubRelation1;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localPreparedStatement = null;
    }
  }
  
  public static ArrayList getDatesWithNonZeroClosingLedger(Connection paramConnection, Account paramAccount, DateTime paramDateTime, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement1 = null;
    ResultSet localResultSet1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet2 = null;
    try
    {
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement1 = getStatement(paramConnection, "SELECT max(DataDate) as MAXDATE FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND b.DataDate < ? AND b.ClosingLedger = 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ?");
      } else {
        localPreparedStatement1 = getStatement(paramConnection, "SELECT max(DataDate) as MAXDATE FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND b.DataDate < ? AND b.ClosingLedger = 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL");
      }
      localPreparedStatement1.setString(1, paramAccount.getBankID());
      localPreparedStatement1.setString(2, paramAccount.getID());
      DCUtil.fillTimestampColumn(localPreparedStatement1, 3, paramDateTime);
      localPreparedStatement1.setString(4, str);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement1.setString(5, paramAccount.getRoutingNum());
      }
      if (paramAccount.getRoutingNum() != null) {
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "SELECT max(DataDate) as MAXDATE FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND b.DataDate < ? AND b.ClosingLedger = 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ?");
      } else {
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "SELECT max(DataDate) as MAXDATE FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND b.DataDate < ? AND b.ClosingLedger = 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL");
      }
      DateTime localDateTime = null;
      if (localResultSet1.next()) {
        localDateTime = DCUtil.getTimestampColumn(localResultSet1.getTimestamp(1), paramAccount.getLocale());
      }
      if (localDateTime != null)
      {
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement2 = getStatement(paramConnection, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND DataDate > ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ? ORDER BY DataDate ");
        } else {
          localPreparedStatement2 = getStatement(paramConnection, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND DataDate > ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL ORDER BY DataDate ");
        }
        localPreparedStatement2.setString(1, paramAccount.getBankID());
        localPreparedStatement2.setString(2, paramAccount.getID());
        DCUtil.fillTimestampColumn(localPreparedStatement2, 3, paramDateTime);
        DCUtil.fillTimestampColumn(localPreparedStatement2, 4, localDateTime);
        localPreparedStatement2.setString(5, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement2.setString(6, paramAccount.getRoutingNum());
        }
        if (paramAccount.getRoutingNum() != null) {
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND DataDate > ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ? ORDER BY DataDate ");
        } else {
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND DataDate > ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL ORDER BY DataDate ");
        }
      }
      else
      {
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement2 = getStatement(paramConnection, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ? ORDER BY DataDate ");
        } else {
          localPreparedStatement2 = getStatement(paramConnection, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL ORDER BY DataDate ");
        }
        localPreparedStatement2.setString(1, paramAccount.getBankID());
        localPreparedStatement2.setString(2, paramAccount.getID());
        DCUtil.fillTimestampColumn(localPreparedStatement2, 3, paramDateTime);
        localPreparedStatement2.setString(4, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement2.setString(5, paramAccount.getRoutingNum());
        }
        if (paramAccount.getRoutingNum() != null) {
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber = ? ORDER BY DataDate ");
        } else {
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT DataDate FROM DC_AccountHistory b, DC_Account a WHERE a.DCAccountID = b.DCAccountID AND a.BankID = ? AND a.AccountID = ? AND DataDate <= ? AND b.ClosingLedger != 0.0 AND b.DataClassification = ? AND a.RoutingNumber IS NULL ORDER BY DataDate ");
        }
      }
      ArrayList localArrayList1 = new ArrayList();
      while (localResultSet2.next()) {
        localArrayList1.add(DCUtil.getTimestampColumn(localResultSet2.getTimestamp(1), paramAccount.getLocale()));
      }
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement1 = null;
      DBUtil.closeResultSet(localResultSet1);
      localPreparedStatement2 = null;
      DBUtil.closeResultSet(localResultSet2);
    }
  }
  
  public static String getCustomerReferenceNumberType()
  {
    return Y;
  }
  
  public static void setCustomerReferenceNumberType(String paramString)
  {
    Y = paramString;
  }
  
  public static String getBankReferenceNumberType()
  {
    return ae;
  }
  
  public static void setBankReferenceNumberType(String paramString)
  {
    ae = paramString;
  }
  
  public static ArrayList getLocationIds(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt, HashMap paramHashMap)
    throws DCException
  {
    return DCTransactions.getLocationIds(paramAccount, paramCalendar1, paramCalendar2, paramInt, paramHashMap);
  }
  
  public static DateTime getHistoryMaxDate(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountHistory.getHistoryMaxDate(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static DateTime getLockboxSummariesMaxDate(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCLockboxSummary.getLockboxSummariesMaxDate(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static DateTime getExtendedSummaryMaxDate(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCExtAcctSummary.getExtendedSummaryMaxDate(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static DateTime getDisbursementSummariesMaxDate(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCDsbSummary.getDisbursementSummariesMaxDate(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static DateTime getSummaryMaxDate(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    return DCAccountSummary.getSummaryMaxDate(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  public static void loadBAISubTypeDescriptions(Connection paramConnection, int paramInt, HashMap paramHashMap1, Locale paramLocale, HashMap paramHashMap2)
    throws DCException
  {
    long l1 = 86400000L;
    PreparedStatement localPreparedStatement1 = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement1 = getStatement(paramConnection, "UPDATE DC_SUBTYPE_DESC SET LAST_UPDATED=LAST_UPDATED");
      DBUtil.executeUpdate(localPreparedStatement1, "UPDATE DC_SUBTYPE_DESC SET LAST_UPDATED=LAST_UPDATED");
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("Unable to lock the DC_SUBTYPE_DESC table in order to determine whether an update is required.", localException1);
      throw new DCException("Unable to lock the DC_SUBTYPE_DESC table in order to determine whether an update is required.", localException1);
    }
    finally
    {
      localPreparedStatement1 = null;
    }
    DateTime localDateTime1 = null;
    try
    {
      localPreparedStatement1 = getStatement(paramConnection, "SELECT (LAST_UPDATED) FROM DC_SUBTYPE_DESC");
      localPreparedStatement1.setMaxRows(1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, "SELECT (LAST_UPDATED) FROM DC_SUBTYPE_DESC");
      if (localResultSet.next()) {
        localDateTime1 = DCUtil.getTimestampColumn(localResultSet.getTimestamp(1), paramLocale);
      }
    }
    catch (Exception localException2)
    {
      rollback(paramConnection);
      DebugLog.throwing("Unable to retrieve the last updated time from the DC_SUBTYPE_DESC table.", localException2);
      throw new DCException("Unable to retrieve the last updated time from the DC_SUBTYPE_DESC table.", localException2);
    }
    finally
    {
      localPreparedStatement1 = null;
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
    }
    DateTime localDateTime2 = new DateTime(paramLocale);
    Object localObject4;
    if (localDateTime1 != null)
    {
      long l2 = localDateTime2.getTime().getTime() - localDateTime1.getTime().getTime();
      if (l2 < l1 * paramInt)
      {
        rollback(paramConnection);
        return;
      }
      localObject4 = null;
      try
      {
        localObject4 = getStatement(paramConnection, "DELETE FROM DC_SUBTYPE_DESC");
        DBUtil.executeUpdate((PreparedStatement)localObject4, "DELETE FROM DC_SUBTYPE_DESC");
      }
      catch (Exception localException4)
      {
        rollback(paramConnection);
        DebugLog.throwing("Unable to clean the DC_SUBTYPE_DESC table in order to load refreshed data.", localException4);
        throw new DCException("Unable to clean the DC_SUBTYPE_DESC table in order to load refreshed data.", localException4);
      }
      finally
      {
        localObject4 = null;
      }
    }
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      Iterator localIterator1 = null;
      localObject4 = null;
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      Object localObject6 = null;
      String str = null;
      localPreparedStatement2 = getStatement(paramConnection, "INSERT INTO DC_SUBTYPE_DESC VALUES(?,?,?,?)");
      Set localSet = paramHashMap1.entrySet();
      Iterator localIterator2 = localSet.iterator();
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator2.next();
        localObject4 = (Integer)localEntry.getKey();
        localBAITypeCodeInfo = (BAITypeCodeInfo)localEntry.getValue();
        str = localBAITypeCodeInfo.getLanguage();
        localPreparedStatement2.setInt(1, ((Integer)localObject4).intValue());
        localPreparedStatement2.setString(2, localBAITypeCodeInfo.getDescription());
        DCUtil.fillTimestampColumn(localPreparedStatement2, 3, localDateTime2.getTime().getTime());
        localPreparedStatement2.setString(4, str);
        localPreparedStatement2.addBatch();
        localIterator1 = localBAITypeCodeInfo.getLanguages();
        while (localIterator1.hasNext())
        {
          str = (String)localIterator1.next();
          localPreparedStatement2.setInt(1, ((Integer)localObject4).intValue());
          localPreparedStatement2.setString(2, localBAITypeCodeInfo.getDescription(str));
          DCUtil.fillTimestampColumn(localPreparedStatement2, 3, localDateTime2.getTime().getTime());
          localPreparedStatement2.setString(4, str);
          localPreparedStatement2.addBatch();
        }
      }
      DBUtil.executeBatch(localPreparedStatement2, "INSERT INTO DC_SUBTYPE_DESC VALUES(?,?,?,?)");
    }
    catch (Exception localException3)
    {
      rollback(paramConnection);
      DebugLog.throwing("Unable to add new sub type descriptions to the DC_SUBTYPE_DESC table.", localException3);
      throw new DCException("Unable to add new sub type descriptions to the DC_SUBTYPE_DESC table.", localException3);
    }
    finally
    {
      localPreparedStatement2 = null;
    }
    commit(paramConnection);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCAdapter
 * JD-Core Version:    0.7.0.1
 */