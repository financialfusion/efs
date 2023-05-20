package com.ffusion.positivepay.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayAccounts;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayDecision;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.positivepay.PPaySummary;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.positivepay.PPayException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.ExtendableTableUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;

public class PPayAdapter
  implements IPPayAdapter
{
  private static final String jdField_if = "com.ffusion.beans.positivepay.resources";
  private static final String i = "DEFAULT_DECISION_USERNAME";
  private static final String jdField_char = "                                        ";
  private static final int A = 20;
  private static final int w = 32;
  private static final int F = 200;
  private static final String jdField_new = "PPAYADAPTER_SETTINGS";
  private static final String u = "DB_PROPERTIES";
  public static final String ACCOUNTS_FOR_REPORTS = "Accounts";
  public static final String FM_LOG_RECORD_DATA = "FM_LOG_RECORD_DATA";
  private static final String q = "accountID";
  private static final String g = "bankID";
  private static final String s = "bankName";
  private static final String jdField_else = "routingNumber";
  private static final String b = "checkNumber";
  private static final String E = "checkDate";
  private static final String jdField_try = "amount";
  private static final String v = "voidCheck";
  private static final String d = "additionalData";
  private static final String P = "rejectReason";
  private static final String D = "issueDate";
  private static final String jdField_for = "decision";
  private static final String z = "submittingUserName";
  private static final String jdField_byte = "New";
  private static final long O = 86400000L;
  public static final String DECISION_PAY = "Pay";
  public static final String DECISION_RETURN = "Return";
  private static final String r = "accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName";
  private static final String jdField_null = "p.accountID,p.bankID,p.bankName,p.routingNumber,p.checkNumber,p.checkDate,p.amount,p.voidCheck,p.additionalData,p.rejectReason,p.issueDate,p.decision,ac.nickname, b.business_name, ac.currency_type, ac.routing_num, p.submittingUserName,cd.cust_id";
  private static final String x = "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber=?  AND decision='New'";
  private static final String C = "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber IS NULL  AND decision='New'";
  private static final String I = "SELECT accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName";
  private static final String jdField_case = " FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND decision='New' ORDER BY checkNumber ASC";
  private static final String y = "INSERT INTO PPay_IssueDecisions ( accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName";
  private static final String jdField_void = "UPDATE PPay_IssueDecisions SET decision = ?, submittingUserName = ? ";
  private static final String J = " WHERE accountID=? AND bankID=? AND checkNumber=?";
  private static final String M = "UPDATE PPay_IssueDecisions SET decision = ?, submittingUserName = ? WHERE accountID=? AND bankID=? ";
  private static final String Q = "DELETE FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? ";
  private static final String jdField_long = "DELETE FROM PPay_IssueDecisions WHERE issueDate<?";
  private static final String B = "DELETE FROM PPay_IssueDecisions WHERE issueDate < ? and accountID = ? and bankID = ?";
  private static final String jdField_int = "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND checkNumber=?";
  private static final String a = "SELECT accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName FROM PPay_IssueDecisions";
  private static final String L = "SELECT p.accountID,p.bankID,p.bankName,p.routingNumber,p.checkNumber,p.checkDate,p.amount,p.voidCheck,p.additionalData,p.rejectReason,p.issueDate,p.decision,ac.nickname, b.business_name, ac.currency_type, ac.routing_num, p.submittingUserName,cd.cust_id FROM PPay_IssueDecisions p, accounts ac, business b, customer_directory cd ";
  private static final String jdField_goto = " p.accountID = ac.account_id  and p.bankID = b.bank_id  and ac.directory_id=b.directory_id  and b.directory_id=cd.directory_id ";
  private static final String jdField_do = " and b.business_id =  ";
  private static final String G = " p.accountid in ";
  private static final String H = " b.business_name, p.accountID ";
  private static final String N = " select business_name from business where business_id= ";
  private static final String o = " and affiliate_bank_id = ";
  private static String K = null;
  private static String p = null;
  private static final String t = "com.ffusion.util.logging.audit.ppay";
  private static final String n = "LogMessage_0";
  private static final String m = "LogMessage_1";
  private static final String l = "LogMessage_2";
  private static final String k = "LogMessage_3";
  private static final String j = "LogMessage_4";
  private static final String h = "LogMessage_5";
  private static final String f = "LogMessage_6";
  private static final String e = "LogMessage_7";
  private static final String c = "LogMessage_8";
  
  public void initialize(HashMap paramHashMap)
    throws PPayException
  {
    String str = "IPPayAdapter.initialize";
    HashMap localHashMap = (HashMap)paramHashMap.get("DB_PROPERTIES");
    if (localHashMap == null)
    {
      DebugLog.log("<DB_PROPERTIES> tag not found  in XML configuration file during intialization");
      throw new PPayException(str, 1, "<DB_PROPERTIES> tag not found  in XML configuration file");
    }
    Properties localProperties = jdField_if(localHashMap);
    try
    {
      ExtendableTableUtil.initialize(localProperties);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Unable to initialize the extendable table utilitiy.", localException);
      throw new PPayException(localException, str, 1, "Unable to initialize the extendable table utility");
    }
    try
    {
      p = localProperties.getProperty("ConnectionType");
      K = ConnectionPool.init(localProperties);
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to create a DB Connection pool during initialization.", localPoolException);
      throw new PPayException(localPoolException, str, 1, "Unable to create a DB Connection pool");
    }
  }
  
  public Connection getConnection(boolean paramBoolean, int paramInt)
    throws PoolException
  {
    return DBUtil.getConnection(K, paramBoolean, paramInt);
  }
  
  public void releaseConnection(Connection paramConnection)
  {
    DBUtil.returnConnection(K, paramConnection);
  }
  
  public PPaySummaries getSummaries(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "IPPayAdapter.getSummaries";
    Locale localLocale = a(paramHashMap);
    PPaySummaries localPPaySummaries = new PPaySummaries(localLocale);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber=?  AND decision='New'");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber IS NULL  AND decision='New'");
      for (int i1 = 0; i1 < paramPPayAccounts.size(); i1++)
      {
        localPreparedStatement1.clearParameters();
        localPreparedStatement2.clearParameters();
        PPayAccount localPPayAccount = (PPayAccount)paramPPayAccounts.get(i1);
        String str2 = localPPayAccount.getAccountID();
        String str3 = localPPayAccount.getBankID();
        String str4 = localPPayAccount.getRoutingNumber();
        if ((str2 == null) || (str3 == null))
        {
          DebugLog.log("No account ID or bank ID was found for an account used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.\nAccount ID: " + str2 + "\n" + "Bank ID: " + str3 + "\nRouting Number: " + str4);
          throw new PPayException(str1, 5, "Account ID and Bank ID are required for each account used in a getSummaries call.  One or both of these parameters did not exist for a given account.");
        }
        if ((str4 == null) || (str4.trim().length() == 0))
        {
          localPreparedStatement2.setString(1, str2);
          localPreparedStatement2.setString(2, str3);
          localResultSet = DBUtil.executeQuery(localPreparedStatement2, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber IS NULL  AND decision='New'");
        }
        else
        {
          localPreparedStatement1.setString(1, str2);
          localPreparedStatement1.setString(2, str3);
          localPreparedStatement1.setString(3, str4);
          localResultSet = DBUtil.executeQuery(localPreparedStatement1, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber=?  AND decision='New'");
        }
        PPaySummary localPPaySummary = localPPaySummaries.add();
        if (localResultSet.next())
        {
          localPPaySummary.setPPayAccount(localPPayAccount);
          localPPaySummary.setNumIssues(localResultSet.getInt(1));
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
      }
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve account summaries.", localException);
      throw new PPayException(localException, str1, 7, "An error has occurred while accessing the database in order to retrieve account summaries.");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(K, localConnection);
    }
    localPPaySummaries.setSortedBy("ACCOUNTID");
    return localPPaySummaries;
  }
  
  public int getNumIssues(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "IPPayAdapter.getNumIssues";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    int i1 = 0;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber=?  AND decision='New'");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber IS NULL  AND decision='New'");
      for (int i2 = 0; i2 < paramPPayAccounts.size(); i2++)
      {
        localPreparedStatement1.clearParameters();
        localPreparedStatement2.clearParameters();
        PPayAccount localPPayAccount = (PPayAccount)paramPPayAccounts.get(i2);
        String str2 = localPPayAccount.getAccountID();
        String str3 = localPPayAccount.getBankID();
        String str4 = localPPayAccount.getRoutingNumber();
        if ((str2 == null) || (str3 == null))
        {
          DebugLog.log("No account ID or  bank ID were found for an account used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.\naccount ID: " + str2 + "\n" + "Bank ID: " + str3 + "\nRouting Number: " + str4);
          throw new PPayException(str1, 5, "Account ID and Bank ID are required for each account used in a getNumIssues call.  One or both of these parameters did not exist for a given account.");
        }
        if ((str4 == null) || (str4.trim().length() == 0))
        {
          localPreparedStatement2.setString(1, str2);
          localPreparedStatement2.setString(2, str3);
          localResultSet = DBUtil.executeQuery(localPreparedStatement2, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber IS NULL  AND decision='New'");
        }
        else
        {
          localPreparedStatement1.setString(1, str2);
          localPreparedStatement1.setString(2, str3);
          localPreparedStatement1.setString(3, str4);
          localResultSet = DBUtil.executeQuery(localPreparedStatement1, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND routingNumber=?  AND decision='New'");
        }
        if (localResultSet.next()) {
          i1 += localResultSet.getInt(1);
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
      }
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve account summaries.", localException);
      throw new PPayException(localException, str1, 7, "An error has occurred while accessing the database in order to retrieve account summaries.");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(K, localConnection);
    }
    return i1;
  }
  
  public PPayIssues getIssuesForAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PPayAdapter.getIssuesForAccount";
    Locale localLocale = a(paramHashMap);
    PPayIssues localPPayIssues = new PPayIssues(localLocale);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("SELECT accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName");
      ExtendableTableUtil.appendSQLSelectColumns(localStringBuffer, "PPay_IssueDecisions");
      localStringBuffer.append(" ");
      localStringBuffer.append(" FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND decision='New' ORDER BY checkNumber ASC");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      String str2 = paramPPayAccount.getAccountID();
      String str3 = paramPPayAccount.getBankID();
      String str4 = paramPPayAccount.getRoutingNumber();
      if ((str2 == null) || (str3 == null))
      {
        DebugLog.log("No account ID or bank ID was found for an account used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.\nAccount ID: " + str2 + "\n" + "Bank ID: " + str3 + "\nRouting Number: " + str4);
        throw new PPayException(str1, 5, "Account ID and Bank ID are required for each account used in a " + str1 + " call.  One or " + "both of these parameters did not exist for a " + "given account.");
      }
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      a(localPPayIssues, localResultSet, localLocale, paramPPayAccount.getCurrencyType());
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve account issues.", localException);
      throw new PPayException(localException, str1, 7, "An error has occurred while accessing the database in order to retrieve account issues.");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(K, localConnection);
    }
    return localPPayIssues;
  }
  
  public void insertIssues(PPayIssues paramPPayIssues, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PPayAdapter.insertIssues";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    PPayIssue localPPayIssue = null;
    try
    {
      localConnection = DBUtil.getConnection(K, false, 2);
      StringBuffer localStringBuffer = new StringBuffer("INSERT INTO PPay_IssueDecisions ( accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName");
      ExtendableTableUtil.appendSQLInsertColumns(localStringBuffer, "PPay_IssueDecisions", true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      while (paramPPayIssues.size() > 0)
      {
        for (int i1 = 0; (i1 < 200) && (paramPPayIssues.size() > 0); i1++)
        {
          localPPayIssue = (PPayIssue)paramPPayIssues.remove(0);
          a(localConnection, localPreparedStatement, localPPayIssue, paramHashMap);
          localPreparedStatement.addBatch();
        }
        int[] arrayOfInt = localPreparedStatement.executeBatch();
        localPreparedStatement.clearBatch();
        for (int i2 = 0; i2 < arrayOfInt.length; i2++)
        {
          int i3 = arrayOfInt[i2];
          if ((i3 < 1) && (i3 != -2))
          {
            DebugLog.log(Level.SEVERE, str + " : " + "(ERROR=" + i3 + ")");
            throw new Exception("An error occured while adding a Positive Pay Issue to the database");
          }
        }
      }
      localConnection.commit();
    }
    catch (Throwable localThrowable)
    {
      try
      {
        localConnection.rollback();
      }
      catch (SQLException localSQLException) {}
      if ((localThrowable instanceof PPayException)) {
        throw ((PPayException)localThrowable);
      }
      if ((localThrowable instanceof PoolException))
      {
        DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localThrowable);
        throw new PPayException((PoolException)localThrowable, str, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
      }
      DebugLog.throwing("An error has occured while adding a Positive Pay Issue to the database.", localThrowable);
      throw new PPayException(new Exception(localThrowable.getMessage()), str, 7, "An error has occured while adding a Positive Pay Issue to the database.");
    }
    finally
    {
      DBUtil.closeAll(K, localConnection, localPreparedStatement);
    }
  }
  
  public void insertIssue(PPayIssue paramPPayIssue, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PPayAdapter.insertIssue";
    Locale localLocale = Locale.getDefault();
    PPayIssues localPPayIssues = new PPayIssues(localLocale);
    localPPayIssues.add(paramPPayIssue);
    insertIssues(localPPayIssues, paramHashMap);
  }
  
  private static void a(Connection paramConnection, PreparedStatement paramPreparedStatement, PPayIssue paramPPayIssue, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "PPayAdapter.fillStmtFromIssue";
    PPayCheckRecord localPPayCheckRecord = paramPPayIssue.getCheckRecord();
    if (localPPayCheckRecord == null) {
      throw new PPayException(str1, 5, "An attempt was made to insert an issue with no check record into the database.");
    }
    String str2 = localPPayCheckRecord.getAccountID();
    String str3 = localPPayCheckRecord.getBankID();
    String str4 = localPPayCheckRecord.getBankName();
    String str5 = localPPayCheckRecord.getRoutingNumber();
    String str6 = localPPayCheckRecord.getCheckNumber();
    try
    {
      if (a(paramConnection, str2, str3, str5, str6))
      {
        FMLogRecord localFMLogRecord = (FMLogRecord)paramHashMap.get("FM_LOG_RECORD_DATA");
        if (localFMLogRecord != null)
        {
          localObject1 = new Object[4];
          localObject1[0] = str2;
          localObject1[1] = str3;
          localObject1[2] = str5;
          localObject1[3] = str6;
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ppay", "LogMessage_0", (Object[])localObject1);
          localFMLogRecord.setLocalizableMessage(localLocalizableString);
          localFMLogRecord.setStatus("Error");
          try
          {
            FMLog.log(localFMLogRecord);
          }
          catch (FMException localFMException) {}finally
          {
            localObject1 = null;
            localLocalizableString = null;
          }
        }
        DebugLog.log("While attempting to insert an issue into the database, it was determined that an issue with the same account ID ( " + str2 + " ), bank ID ( " + str3 + " ), " + "routing number ( " + str5 + " ) and check number ( " + str6 + " ) already exists.");
        throw new PPayException(str1, 2, "An issue with the same account ID ( " + str2 + " ) bank ID ( " + str3 + " ) and " + "routing number ( " + str5 + " ) and check number ( " + str6 + " ) already exists in the database.");
      }
    }
    catch (InvalidParameterException localInvalidParameterException)
    {
      DebugLog.log("No account ID, bank ID or check number were found for an account used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.\nAccount ID: " + str2 + "\nBank ID: " + str3 + "\nCheck Number: " + str6);
      throw new PPayException(str1, 5, "Account ID, bank ID, routing number and check number are required for each account used in a " + str1 + " call.  One or " + "all of these parameters did not exist for the " + "given account.");
    }
    java.sql.Date localDate1 = new java.sql.Date(localPPayCheckRecord.getCheckDate().getTime().getTime());
    Object localObject1 = localPPayCheckRecord.getAmount().getAmountValue();
    boolean bool = localPPayCheckRecord.getVoidCheck();
    String str7 = localPPayCheckRecord.getAdditionalData();
    String str8 = paramPPayIssue.getRejectReason();
    java.sql.Date localDate2 = new java.sql.Date(paramPPayIssue.getIssueDate().getTime().getTime());
    int i1 = 1;
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, str2, false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, str3, false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, str4, false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, str5, false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, a(str6, 20), false);
    i1 = ExtendableTableUtil.setStatementDate(paramPreparedStatement, i1, localDate1, false);
    i1 = ExtendableTableUtil.setStatementBigDecimal(paramPreparedStatement, i1, (BigDecimal)localObject1, false);
    if (bool) {
      i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, "Y", false);
    } else {
      i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, "N", false);
    }
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, str7, false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, str8, false);
    i1 = ExtendableTableUtil.setStatementDate(paramPreparedStatement, i1, localDate2, false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, "New", false);
    i1 = ExtendableTableUtil.setStatementString(paramPreparedStatement, i1, null, false);
    i1 = ExtendableTableUtil.setStatementValues(paramPreparedStatement, i1, paramPPayIssue, "PPay_IssueDecisions", false);
  }
  
  private ResultSet a(Connection paramConnection, PreparedStatement[] paramArrayOfPreparedStatement, String paramString1, String paramString2, String paramString3, Vector paramVector, int paramInt)
    throws Exception
  {
    String str = null;
    ResultSet localResultSet = null;
    str = a(paramString1, paramString2, paramString3);
    paramArrayOfPreparedStatement[0] = DBUtil.prepareStatement(paramConnection, str);
    if (paramInt > 0) {
      paramArrayOfPreparedStatement[0].setMaxRows(paramInt);
    }
    int i1 = 0;
    while (!paramVector.isEmpty())
    {
      DateTime localDateTime = (DateTime)paramVector.remove(0);
      i1++;
      paramArrayOfPreparedStatement[0].setDate(i1, new java.sql.Date(localDateTime.getTime().getTime()));
    }
    localResultSet = DBUtil.executeQuery(paramArrayOfPreparedStatement[0], str);
    return localResultSet;
  }
  
  private ResultSet a(Connection paramConnection, PreparedStatement[] paramArrayOfPreparedStatement, String paramString1, String paramString2, String paramString3, int paramInt)
    throws Exception
  {
    String str = null;
    ResultSet localResultSet = null;
    str = a(paramString1, paramString2, paramString3);
    paramArrayOfPreparedStatement[0] = DBUtil.prepareStatement(paramConnection, str);
    if (paramInt > 0) {
      paramArrayOfPreparedStatement[0].setMaxRows(paramInt);
    }
    int i1 = 0;
    localResultSet = DBUtil.executeQuery(paramArrayOfPreparedStatement[0], str);
    return localResultSet;
  }
  
  public IReportResult getReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PPayAdapter.getReportData";
    Locale localLocale = a(paramHashMap);
    PPayAccounts localPPayAccounts = (PPayAccounts)paramHashMap.get("Accounts");
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null)
    {
      DebugLog.log("The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.");
      throw new PPayException(str1, 5, "The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.");
    }
    String str2 = localProperties1.getProperty("REPORTTYPE");
    if (str2 == null)
    {
      DebugLog.log("The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.");
      throw new PPayException(str1, 5, "The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.");
    }
    String str3 = localProperties1.getProperty("MAXDISPLAYSIZE");
    int i1 = -1;
    try
    {
      Integer localInteger = Integer.valueOf(str3);
      i1 = localInteger.intValue();
    }
    catch (Exception localException1) {}
    ReportResult localReportResult = new ReportResult(localLocale);
    a(paramReportCriteria, localPPayAccounts);
    try
    {
      localReportResult.init(paramReportCriteria);
    }
    catch (Exception localException2)
    {
      DebugLog.log("Unable to initialize the report.");
      throw new PPayException(str1, 24, "Unable to initialize the report.");
    }
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Vector localVector = new Vector();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      Properties localProperties2 = paramReportCriteria.getSearchCriteria();
      Properties localProperties3 = new Properties();
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      String str4 = a(localReportSortCriteria);
      String str5;
      if (str2.equals("Decision History"))
      {
        localProperties3.setProperty("Decision Is Not", "New");
        str5 = a(localProperties2, localProperties3, localPPayAccounts, localVector, localLocale, paramHashMap);
        localResultSet = a(localConnection, arrayOfPreparedStatement, "SELECT accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName FROM PPay_IssueDecisions", str5, str4, localVector, i1);
        a(localReportResult, localResultSet, str2, localLocale, i1, localPPayAccounts);
      }
      else if (str2.equals("Exceptions Summary"))
      {
        localProperties3.setProperty("Decision", "New");
        str5 = a(localProperties2, localProperties3, localPPayAccounts, localVector, localLocale, paramHashMap);
        localResultSet = a(localConnection, arrayOfPreparedStatement, "SELECT accountID, bankID, bankName, routingNumber, checkNumber, checkDate, amount, voidCheck, additionalData, rejectReason, issueDate, decision,submittingUserName FROM PPay_IssueDecisions", str5, str4, localVector, i1);
        jdField_if(localReportResult, localResultSet, str2, localLocale, i1, localPPayAccounts);
      }
      else
      {
        DebugLog.log("The report type requested in not a valid Positive Pay report type.  Currently the valid reporting types are Decision History and Exceptions Summary.");
        throw new PPayException(str1, 19, "The report type requested in not a valid Positive Pay report type.  Currently the valid reporting types are Decision History and Exceptions Summary.");
      }
    }
    catch (PoolException localPoolException)
    {
      localReportResult.setError(localPoolException);
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException3)
    {
      localReportResult.setError(localException3);
      if ((localException3 instanceof PPayException)) {
        throw ((PPayException)localException3);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve a report.", localException3);
      throw new PPayException(localException3, str1, 7, "An error has occurred while accessing the database in order to retrieve a report.");
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (Exception localException4) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DBUtil.returnConnection(K, localConnection);
      }
    }
    return localReportResult;
  }
  
  public IReportResult getBCReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PPayAdapter.getReportData";
    Locale localLocale = a(paramHashMap);
    boolean bool1 = true;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Connection localConnection = null;
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null)
    {
      DebugLog.log("The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.");
      throw new PPayException(str1, 5, "The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.");
    }
    String str2 = localProperties1.getProperty("REPORTTYPE");
    if (str2 == null)
    {
      DebugLog.log("The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.");
      throw new PPayException(str1, 5, "The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.");
    }
    String str3 = localProperties1.getProperty("MAXDISPLAYSIZE");
    int i1 = -1;
    try
    {
      Integer localInteger = Integer.valueOf(str3);
      i1 = localInteger.intValue();
    }
    catch (Exception localException1) {}
    ReportResult localReportResult = new ReportResult(localLocale);
    String str4 = null;
    int i2 = 0;
    Object localObject1;
    String str6;
    String str7;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      String str5 = a(paramReportCriteria.getSearchCriteria(), "Affiliate Bank", "AllAffiliateBanks");
      localObject1 = null;
      if (!str5.equals("AllAffiliateBanks"))
      {
        i2 = Integer.parseInt(str5);
        localObject1 = (String)paramHashMap.get("AffiliateBankNameForReport");
      }
      else
      {
        localObject1 = ReportConsts.getText(10048, localLocale);
      }
      paramReportCriteria.setDisplayValue("Affiliate Bank", (String)localObject1);
      str6 = a(paramReportCriteria.getSearchCriteria(), "Check Void", " ");
      if ((str6 != null) && (str6.trim().length() != 0))
      {
        boolean bool2 = Boolean.valueOf(str6).booleanValue();
        if (bool2) {
          paramReportCriteria.setDisplayValue("Check Void", ReportConsts.getText(10052, paramReportCriteria.getLocale()));
        } else {
          paramReportCriteria.setDisplayValue("Check Void", ReportConsts.getText(10053, paramReportCriteria.getLocale()));
        }
      }
      else
      {
        paramReportCriteria.setDisplayValue("Check Void", ReportConsts.getText(10051, paramReportCriteria.getLocale()));
      }
      str7 = a(paramReportCriteria.getSearchCriteria(), "Business", "AllBusinesses");
      if (str7.equals("AllBusinesses"))
      {
        str4 = ReportConsts.getText(10015, localLocale);
      }
      else
      {
        bool1 = false;
        str4 = Profile.getBusinessNameAndCustId(localConnection, str7, localLocale);
        if (str4 == null) {
          str4 = "";
        }
      }
      paramReportCriteria.setDisplayValue("Business", str4);
      String str8 = a(paramReportCriteria.getSearchCriteria(), "Account", "All");
      if (a(str8))
      {
        paramReportCriteria.setDisplayValue("Account", ReportConsts.getText(10024, paramReportCriteria.getLocale()));
      }
      else
      {
        StringBuffer localStringBuffer = new StringBuffer();
        StringTokenizer localStringTokenizer1 = new StringTokenizer(str8, ",");
        int i3 = localStringTokenizer1.countTokens();
        for (int i4 = 1; localStringTokenizer1.hasMoreTokens(); i4++)
        {
          StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), ":");
          int i5 = localStringTokenizer2.countTokens();
          if (i5 < 2)
          {
            str9 = "The account id and bank ID were not correctly passed in as a report criteria. The proper format is the account number folled by the delimiter ' :' and then the bank ID.";
            DebugLog.log(str9);
            throw new PPayException(str1, 5, str9);
          }
          String str9 = localStringTokenizer2.nextToken();
          String str10 = localStringTokenizer2.nextToken();
          String str11 = null;
          String str12 = null;
          String str13 = null;
          if (i5 > 2)
          {
            str11 = localStringTokenizer2.nextToken();
            str12 = localStringTokenizer2.nextToken();
            str13 = localStringTokenizer2.nextToken();
            try
            {
              localStringBuffer.append(str11 + " : " + AccountUtil.buildAccountDisplayText(str9, paramReportCriteria.getLocale()) + " - " + str12 + " - " + str13);
            }
            catch (UtilException localUtilException)
            {
              DebugLog.throwing("Error while constructing account display string for report type, '" + str2 + "'.", localUtilException);
              localStringBuffer.append(str11 + " : " + str9 + " - " + str12 + " - " + str13);
            }
          }
          if (i4 != i3) {
            localStringBuffer.append("&&");
          }
        }
        paramReportCriteria.setDisplayValue("Account", localStringBuffer.toString());
      }
      localReportResult.init(paramReportCriteria);
    }
    catch (Exception localException2)
    {
      DBUtil.returnConnection(K, localConnection);
      DebugLog.log("Unable to initialize the report.");
      throw new PPayException(str1, 24, "Unable to initialize the report.");
    }
    try
    {
      Properties localProperties2 = new Properties();
      localObject1 = paramReportCriteria.getSortCriteria();
      str6 = a((ReportSortCriteria)localObject1);
      if ((str6 != null) && (str6.trim().length() > 0)) {
        str6 = " b.business_name, p.accountID ," + str6;
      } else {
        str6 = " b.business_name, p.accountID ";
      }
      if (str2.equals("Positive Pay Decisions Report"))
      {
        str7 = a(paramReportCriteria, str2, bool1, i2, localLocale, paramHashMap);
        if ((str7 != null) && (str7.trim().length() > 0)) {
          str7 = str7 + " and  p.accountID = ac.account_id  and p.bankID = b.bank_id  and ac.directory_id=b.directory_id  and b.directory_id=cd.directory_id ";
        } else {
          str7 = " p.accountID = ac.account_id  and p.bankID = b.bank_id  and ac.directory_id=b.directory_id  and b.directory_id=cd.directory_id ";
        }
        localResultSet = a(localConnection, arrayOfPreparedStatement, "SELECT p.accountID,p.bankID,p.bankName,p.routingNumber,p.checkNumber,p.checkDate,p.amount,p.voidCheck,p.additionalData,p.rejectReason,p.issueDate,p.decision,ac.nickname, b.business_name, ac.currency_type, ac.routing_num, p.submittingUserName,cd.cust_id FROM PPay_IssueDecisions p, accounts ac, business b, customer_directory cd ", str7, str6, i1);
        jdField_if(localConnection, localReportResult, localResultSet, localLocale, bool1, str4, i1, str2);
      }
      else if (str2.equals("Positive Pay Exceptions Report"))
      {
        str7 = a(paramReportCriteria, str2, bool1, i2, localLocale, paramHashMap);
        if ((str7 != null) && (str7.trim().length() > 0)) {
          str7 = str7 + " and  p.accountID = ac.account_id  and p.bankID = b.bank_id  and ac.directory_id=b.directory_id  and b.directory_id=cd.directory_id ";
        } else {
          str7 = " p.accountID = ac.account_id  and p.bankID = b.bank_id  and ac.directory_id=b.directory_id  and b.directory_id=cd.directory_id ";
        }
        localResultSet = a(localConnection, arrayOfPreparedStatement, "SELECT p.accountID,p.bankID,p.bankName,p.routingNumber,p.checkNumber,p.checkDate,p.amount,p.voidCheck,p.additionalData,p.rejectReason,p.issueDate,p.decision,ac.nickname, b.business_name, ac.currency_type, ac.routing_num, p.submittingUserName,cd.cust_id FROM PPay_IssueDecisions p, accounts ac, business b, customer_directory cd ", str7, str6, i1);
        a(localConnection, localReportResult, localResultSet, localLocale, bool1, str4, i1, str2);
      }
      else
      {
        DebugLog.log("The report type requested in not a valid Positive Pay report type.  Currently the valid reporting types are Decision History and Exceptions Summary.");
        throw new PPayException(str1, 19, "The report type requested in not a valid Positive Pay report type.  Currently the valid reporting types are Decision History and Exceptions Summary.");
      }
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      localReportResult.setError(localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException3)
    {
      if ((localException3 instanceof PPayException)) {
        throw ((PPayException)localException3);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve a report.", localException3);
      localReportResult.setError(localException3);
      throw new PPayException(localException3, str1, 7, "An error has occurred while accessing the database in order to retrieve a report.");
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (Exception localException4) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DBUtil.returnConnection(K, localConnection);
      }
    }
    return localReportResult;
  }
  
  public void submitDecisions(PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PPayAdapter.submitDecisions";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(K, false, 2);
      StringBuffer localStringBuffer = new StringBuffer("UPDATE PPay_IssueDecisions SET decision = ?, submittingUserName = ? ");
      ExtendableTableUtil.appendSQLUpdateColumns(localStringBuffer, "PPay_IssueDecisions");
      localStringBuffer.append(" ");
      localStringBuffer.append(" WHERE accountID=? AND bankID=? AND checkNumber=?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      for (int i1 = 0; i1 < paramPPayDecisions.size(); i1++)
      {
        localPreparedStatement.clearParameters();
        PPayDecision localPPayDecision = (PPayDecision)paramPPayDecisions.get(i1);
        PPayCheckRecord localPPayCheckRecord = localPPayDecision.getCheckRecord();
        if (localPPayCheckRecord == null)
        {
          DebugLog.log("No PPayCheckRecord was found for a decision used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.");
          throw new PPayException(str1, 5, "No PPayCheckRecord was found for a decision used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.");
        }
        String str2 = localPPayCheckRecord.getAccountID();
        String str3 = localPPayCheckRecord.getBankID();
        String str4 = localPPayCheckRecord.getRoutingNumber();
        String str5 = localPPayCheckRecord.getCheckNumber();
        if ((str2 == null) || (str3 == null) || (str5 == null))
        {
          DebugLog.log("No account ID, bank ID  or check number were found for a decision used in a call to " + str1 + ".  This information " + "is mandatory for this call to succeed.\naccount ID: " + str2 + "\nBank ID: " + str3 + "\nRouting Number: " + str4 + "\nCheck Number: " + str5);
          throw new PPayException(str1, 5, "Account ID, bank ID  and check number are required for each decision used in a " + str1 + " call.  One or " + "all of these parameters did not exist for a " + "given decision.");
        }
        String str6 = localPPayDecision.getDecision();
        if (str6 == null)
        {
          DebugLog.log("The decision submitted for this issue is invalid.");
          throw new PPayException(str1, 25, "The decision submitted for this issue is invalid.");
        }
        String str7 = localPPayDecision.getSubmittingUserName();
        int i2 = 1;
        i2 = ExtendableTableUtil.setStatementString(localPreparedStatement, i2, str6, false);
        i2 = ExtendableTableUtil.setStatementString(localPreparedStatement, i2, str7, false);
        i2 = ExtendableTableUtil.setStatementValues(localPreparedStatement, i2, localPPayDecision, "PPay_IssueDecisions", false);
        i2 = ExtendableTableUtil.setStatementString(localPreparedStatement, i2, str2, false);
        i2 = ExtendableTableUtil.setStatementString(localPreparedStatement, i2, str3, false);
        i2 = ExtendableTableUtil.setStatementString(localPreparedStatement, i2, a(str5, 20), false);
        int i3 = DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
        if (i3 == 0)
        {
          DebugLog.log("There was no issue with the corresponding account ID ( " + str2 + " ), bankID ( " + str3 + " ) and check number ( " + str5 + " ) found in the database.  The PPayDecision object:\n" + localPPayDecision.toXML());
          throw new PPayException(str1, 4, "There was no issue with the corresponding account ID ( " + str2 + " ), bankID ( " + str3 + " ) and check number ( " + str5 + " ) found in the database.");
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to submit a decision.", localException);
      throw new PPayException(localException, str1, 7, "An error has occurred while accessing the database in order to submit a decision.");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(K, localConnection);
    }
  }
  
  public void deleteAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PPayAdapter.deleteAccount";
    String str2 = paramPPayAccount.getAccountID();
    String str3 = paramPPayAccount.getBankID();
    String str4 = paramPPayAccount.getRoutingNumber();
    if ((str2 == null) || (str3 == null) || (str4 == null))
    {
      DebugLog.log("No account ID, bank ID, or routing number was found in a call to " + str1 + ". This information is mandatory for this call to succeed.\nAccount ID: " + str2 + "\nBank ID: " + str3 + "\nRouting Number: " + str4 + ".");
      throw new PPayException(str1, 5, "Account ID, bank ID, and routing number are required in order to make a " + str1 + " call.  One or all of these parameters did not exist.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? ");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, str3);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? ");
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str1, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to delete an account.", localException);
      throw new PPayException(localException, str1, 7, "An error has occurred while accessing the database in order to delete an account.");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(K, localConnection);
    }
  }
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PPayAdapter.cleanup";
    if (paramInt < 0)
    {
      DebugLog.log("The age specified in the cleanup method must be a positive number.  The age specified was : " + paramInt + ".");
      throw new PPayException(str, 8, "The age specified in the cleanup method must be a positive number.  The age specified was : " + paramInt + ".");
    }
    long l1 = DBUtil.getCurrentDate().getTime();
    java.sql.Date localDate = new java.sql.Date(l1 - paramInt * 86400000L);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(K, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM PPay_IssueDecisions WHERE issueDate<?");
      localPreparedStatement.setDate(1, localDate);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM PPay_IssueDecisions WHERE issueDate<?");
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to clean up.", localException);
      throw new PPayException(localException, str, 7, "An error has occurred while accessing the database in order to clean up.");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(K, localConnection);
    }
  }
  
  public void cleanup(PPayAccounts paramPPayAccounts, int paramInt, HashMap paramHashMap)
    throws PPayException
  {
    String str = "PPayAdapter.cleanup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    java.sql.Date localDate = null;
    long l1 = 0L;
    if (paramInt < 0)
    {
      DebugLog.log("The age specified in the cleanup method must be a non-negative number. The age specified was: " + paramInt + ".");
      throw new PPayException(str, 8, "The age specified in the cleanup method must be a non-negative number. The age specified was: " + paramInt + ".");
    }
    try
    {
      l1 = DBUtil.getCurrentDate().getTime();
      localDate = new java.sql.Date(l1 - paramInt * 86400000L);
      localConnection = DBUtil.getConnection(K, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM PPay_IssueDecisions WHERE issueDate < ? and accountID = ? and bankID = ?");
      localPreparedStatement.setDate(1, localDate);
      Iterator localIterator = paramPPayAccounts.iterator();
      while (localIterator.hasNext())
      {
        PPayAccount localPPayAccount = (PPayAccount)localIterator.next();
        localPreparedStatement.setString(2, localPPayAccount.getAccountID());
        localPreparedStatement.setString(3, localPPayAccount.getBankID());
        DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM PPay_IssueDecisions WHERE issueDate < ? and accountID = ? and bankID = ?");
      }
    }
    catch (PoolException localPoolException)
    {
      DebugLog.throwing("Unable to retrieve a connection from the database pool named: " + K + ".", localPoolException);
      throw new PPayException(localPoolException, str, 6, "Unable to retrieve a connection from the database pool named: " + K + ".");
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to clean up.", localException);
      throw new PPayException(localException, str, 7, "An error has occurred while accessing the database in order to clean up.");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(K, localConnection);
    }
  }
  
  public void submitDefaultDecisions(PPayAccounts paramPPayAccounts, String paramString, HashMap paramHashMap)
    throws PPayException
  {
    String str1 = "PPayAdapter.submitDefaultDecisions";
    if ((paramString == null) || (paramString.length() <= 0)) {
      throw new PPayException(str1, 25, "Default decision cannot not be null or empty");
    }
    if ((paramPPayAccounts == null) || (paramPPayAccounts.isEmpty())) {
      return;
    }
    Locale localLocale = paramPPayAccounts.getLocale();
    String str2 = ResourceUtil.getString("DEFAULT_DECISION_USERNAME", "com.ffusion.beans.positivepay.resources", localLocale);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(K, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "UPDATE PPay_IssueDecisions SET decision = ?, submittingUserName = ? WHERE accountID=? AND bankID=? ");
      for (int i1 = 0; i1 < paramPPayAccounts.size(); i1++)
      {
        PPayAccount localPPayAccount = (PPayAccount)paramPPayAccounts.get(i1);
        localPreparedStatement.setString(1, paramString);
        localPreparedStatement.setString(2, str2);
        localPreparedStatement.setString(3, localPPayAccount.getAccountID());
        localPreparedStatement.setString(4, localPPayAccount.getBankID());
        localPreparedStatement.executeUpdate();
        localConnection.commit();
      }
    }
    catch (Exception localException)
    {
      if (localConnection != null) {
        try
        {
          localConnection.rollback();
        }
        catch (SQLException localSQLException) {}
      }
      throw new PPayException(7, localException);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(K, localConnection);
    }
  }
  
  private static boolean a(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    String str = "PPayAdapter.doesIssueExistInDB";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND checkNumber=?");
      if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null) || (paramString4 == null)) {
        throw new InvalidParameterException("The accountID, bankID, routing number and check number passed in must not be null.");
      }
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setString(3, a(paramString4, 20));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT COUNT(*) FROM PPay_IssueDecisions WHERE accountID=? AND bankID=? AND checkNumber=?");
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 != 0)
        {
          boolean bool = true;
          return bool;
        }
      }
      else
      {
        DebugLog.log("In the doesIssueExist method an unexpected event has occured. A count request has produced a result set which has no rows to return.");
        throw new PPayException(str, 7, "A count request has produced a result set which has no rows to return.");
      }
      int i1 = 0;
      return i1;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private boolean a(PPayIssue paramPPayIssue)
  {
    PPayCheckRecord localPPayCheckRecord = paramPPayIssue.getCheckRecord();
    if (localPPayCheckRecord == null) {
      return false;
    }
    return (localPPayCheckRecord.getAccountID() != null) && (localPPayCheckRecord.getBankID() != null) && (localPPayCheckRecord.getCheckNumber() != null) && (localPPayCheckRecord.getCheckDate() != null) && (localPPayCheckRecord.getAmount() != null) && (paramPPayIssue.getRejectReason() != null) && (paramPPayIssue.getIssueDate() != null);
  }
  
  private Properties jdField_if(HashMap paramHashMap)
  {
    Properties localProperties = null;
    if (paramHashMap != null)
    {
      localProperties = new Properties();
      Set localSet = paramHashMap.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str1 = localIterator.next().toString();
        String str2 = paramHashMap.get(str1).toString();
        localProperties.setProperty(str1, str2);
      }
    }
    return localProperties;
  }
  
  private String a(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      localStringBuffer.append(" WHERE ");
      localStringBuffer.append(paramString2);
    }
    if ((paramString3 != null) && (paramString3.length() != 0))
    {
      localStringBuffer.append(" ORDER BY ");
      localStringBuffer.append(paramString3);
    }
    return localStringBuffer.toString();
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramReportSortCriteria != null)
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      while (localIterator.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        String str1 = localReportSortCriterion.getName();
        String str2 = null;
        if (str1.equals("Check Number")) {
          str2 = "checkNumber";
        } else if (str1.equals("Check Date")) {
          str2 = "checkDate";
        } else if (str1.equals("Amount")) {
          str2 = "amount";
        } else if (str1.equals("Reject Reason")) {
          str2 = "rejectReason";
        } else if (str1.equals("Decision")) {
          str2 = "decision";
        }
        String str3 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
        localStringBuffer.append(str2);
        localStringBuffer.append(" ");
        localStringBuffer.append(str3);
        if (localIterator.hasNext()) {
          localStringBuffer.append(", ");
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  private static String a(Properties paramProperties1, Properties paramProperties2, PPayAccounts paramPPayAccounts, Vector paramVector, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    String str = "PPayAdapter.getWhereString";
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramProperties1 != null) {
      a(paramProperties1, localStringBuffer, paramVector, paramLocale);
    }
    if (paramProperties2 != null) {
      a(paramProperties2, localStringBuffer, paramVector, paramLocale);
    }
    HashMap localHashMap = (HashMap)paramHashMap.get("StartDates");
    if (localHashMap == null)
    {
      localObject1 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log((String)localObject1);
      throw new PPayException(str, 5, (String)localObject1);
    }
    Object localObject1 = (HashMap)paramHashMap.get("EndDates");
    if (localObject1 == null)
    {
      localObject2 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log((String)localObject2);
      throw new PPayException(str, 5, (String)localObject2);
    }
    Object localObject2 = paramPPayAccounts.iterator();
    if (((Iterator)localObject2).hasNext())
    {
      localObject3 = new String(localStringBuffer).trim();
      if (((String)localObject3).length() != 0) {
        localStringBuffer.append(" AND ( ");
      } else {
        localStringBuffer.append(" ( ");
      }
    }
    Object localObject3 = new HashMap();
    Object localObject5;
    while (((Iterator)localObject2).hasNext())
    {
      localObject4 = (PPayAccount)((Iterator)localObject2).next();
      if (((HashMap)localObject3).containsKey(((PPayAccount)localObject4).getRoutingNumber()))
      {
        localObject5 = (Vector)((HashMap)localObject3).get(((PPayAccount)localObject4).getRoutingNumber());
      }
      else
      {
        localObject5 = new Vector();
        ((HashMap)localObject3).put(((PPayAccount)localObject4).getRoutingNumber(), localObject5);
      }
      ((Vector)localObject5).add(localObject4);
    }
    Object localObject4 = ((HashMap)localObject3).keySet().iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject5 = (String)((Iterator)localObject4).next();
      Vector localVector = (Vector)((HashMap)localObject3).get(localObject5);
      localStringBuffer.append("( ");
      Iterator localIterator = localVector.iterator();
      localStringBuffer.append("( ");
      while (localIterator.hasNext())
      {
        localObject6 = (PPayAccount)localIterator.next();
        localStringBuffer.append("( ");
        localStringBuffer.append("accountID");
        localStringBuffer.append(" = '");
        localStringBuffer.append(DBUtil.escapeSQLStringLiteral(((PPayAccount)localObject6).getAccountID()));
        localStringBuffer.append("' AND ");
        localStringBuffer.append("routingNumber");
        localStringBuffer.append(" = '");
        localStringBuffer.append(DBUtil.escapeSQLStringLiteral(((PPayAccount)localObject6).getRoutingNumber()));
        localStringBuffer.append("' AND ");
        localStringBuffer.append("bankID");
        localStringBuffer.append(" = '");
        localStringBuffer.append(DBUtil.escapeSQLStringLiteral(((PPayAccount)localObject6).getBankID()));
        localStringBuffer.append("'");
        localStringBuffer.append(" )");
        if (localIterator.hasNext()) {
          localStringBuffer.append(" OR ");
        }
      }
      localStringBuffer.append(" )");
      Object localObject6 = (Calendar)localHashMap.get(localObject5);
      if (localObject6 != null)
      {
        localStringBuffer.append(" AND ");
        localStringBuffer.append("checkDate");
        localStringBuffer.append(" >= ?");
        paramVector.add(localObject6);
      }
      Calendar localCalendar = (Calendar)((HashMap)localObject1).get(localObject5);
      if (localCalendar != null)
      {
        localStringBuffer.append(" AND ");
        localStringBuffer.append("checkDate");
        localStringBuffer.append(" <= ?");
        paramVector.add(localCalendar);
      }
      localStringBuffer.append(" ) ");
      if (((Iterator)localObject4).hasNext()) {
        localStringBuffer.append(" OR ");
      }
    }
    if (paramPPayAccounts.size() > 0) {
      localStringBuffer.append(" ) ");
    }
    return localStringBuffer.toString();
  }
  
  private static String a(ReportCriteria paramReportCriteria, String paramString, boolean paramBoolean, int paramInt, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "PPayAdapter.getBCWhereString";
    StringBuffer localStringBuffer1 = new StringBuffer();
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("StartDate");
    String str3 = localProperties.getProperty("EndDate");
    try
    {
      localTimestamp1 = jdField_do(str2);
      localTimestamp2 = jdField_do(str3);
    }
    catch (ParseException localParseException)
    {
      throw new Exception("Invalid format for date and time search criteria.");
    }
    if (localTimestamp1 != null) {
      a(localStringBuffer1, " p.checkDate>= " + fixDate(localTimestamp1) + " ");
    }
    if (localTimestamp2 != null) {
      a(localStringBuffer1, " p.checkDate<=  " + fixDate(localTimestamp2) + " ");
    }
    String str4 = a(localProperties, "Start Check Number", null);
    if ((str4 != null) && (str4.trim().length() > 0)) {
      a(localStringBuffer1, " p.checkNumber >='" + DBUtil.escapeSQLStringLiteral(a(str4, 20)) + "' ");
    }
    String str5 = a(localProperties, "End Check Number", null);
    if ((str5 != null) && (str5.trim().length() > 0)) {
      a(localStringBuffer1, " p.checkNumber<='" + DBUtil.escapeSQLStringLiteral(a(str5, 20)) + "' ");
    }
    String str6 = a(localProperties, "FromAmount", null);
    if ((str6 != null) && (str6.trim().length() > 0)) {
      a(localStringBuffer1, " p.amount >= " + DBUtil.trimSQLNumericLiteral(str6, new StringBuffer()) + " ");
    }
    String str7 = a(localProperties, "ToAmount", null);
    if ((str7 != null) && (str7.trim().length() > 0)) {
      a(localStringBuffer1, " p.amount<= " + DBUtil.trimSQLNumericLiteral(str7, new StringBuffer()) + " ");
    }
    String str8 = a(localProperties, "Check Void", null);
    String str9;
    if ((str8 != null) && (str8.trim().length() > 0) && (str8.equalsIgnoreCase("TRUE"))) {
      str9 = "'Y'";
    } else {
      str9 = "'N'";
    }
    if ((str8 != null) && (str8.trim().length() > 0)) {
      a(localStringBuffer1, " p.voidCheck=" + str9 + " ");
    }
    String str10 = a(localProperties, "Reject Reason", null);
    if ((str10 != null) && (str10.trim().length() > 0)) {
      a(localStringBuffer1, " LOWER( p.rejectReason)  like '%" + DBUtil.escapeSQLStringLiteral(str10.toLowerCase()) + "%' ");
    }
    String str11 = a(localProperties, "Decision", null);
    if ((str11 != null) && (str11.trim().length() > 0) && (!str11.equals("All"))) {
      a(localStringBuffer1, " p.decision='" + DBUtil.escapeSQLStringLiteral(str11) + "' ");
    }
    if (!paramBoolean)
    {
      str12 = a(localProperties, "Business", "AllBusinesses");
      a(localStringBuffer1, " b.business_id=" + DBUtil.trimSQLNumericLiteral(str12, new StringBuffer()) + " ");
    }
    if (paramInt != 0) {
      a(localStringBuffer1, " b.affiliate_bank_id=" + paramInt + " ");
    }
    String str12 = a(localProperties, "Account", "All");
    boolean bool = a(str12);
    if (!bool)
    {
      StringTokenizer localStringTokenizer1 = new StringTokenizer(str12, ",");
      int i1 = 0;
      StringBuffer localStringBuffer2 = new StringBuffer();
      if (localStringTokenizer1.countTokens() == 1) {
        i1 = 1;
      } else {
        localStringBuffer2.append("(");
      }
      for (int i2 = 1; localStringTokenizer1.hasMoreTokens(); i2 = 0)
      {
        String str13 = localStringTokenizer1.nextToken();
        StringTokenizer localStringTokenizer2 = new StringTokenizer(str13, ":");
        int i3 = localStringTokenizer2.countTokens();
        if (i3 < 2)
        {
          str14 = "The account id and bank ID were not correctly passed in as a report criteria. The proper format is the account number folled by the delimiter ' :' and then the bank ID.";
          DebugLog.log(str14);
          throw new PPayException(str1, 5, str14);
        }
        String str14 = localStringTokenizer2.nextToken();
        String str15 = localStringTokenizer2.nextToken();
        String str16 = null;
        if ((i1 == 0) && (i2 != 0)) {
          localStringBuffer2.append("(");
        } else if ((i1 == 0) && (i2 == 0)) {
          localStringBuffer2.append(" OR (");
        }
        localStringBuffer2.append("p.accountID='" + DBUtil.escapeSQLStringLiteral(str14) + "'");
        localStringBuffer2.append(" AND p.bankID='" + DBUtil.escapeSQLStringLiteral(str15) + "'");
        if (i3 > 2)
        {
          str16 = localStringTokenizer2.nextToken();
          localStringBuffer2.append(" AND p.routingNumber='" + DBUtil.escapeSQLStringLiteral(str16) + "'");
        }
        if (i1 == 0) {
          localStringBuffer2.append(")");
        }
      }
      if (i1 == 0) {
        localStringBuffer2.append(")");
      }
      a(localStringBuffer1, localStringBuffer2.toString());
    }
    if (paramString.equals("Positive Pay Decisions Report")) {
      a(localStringBuffer1, " p.decision<>'New' ");
    } else if (paramString.equals("Positive Pay Exceptions Report")) {
      a(localStringBuffer1, " p.decision='New' ");
    }
    return localStringBuffer1.toString();
  }
  
  private static void a(Properties paramProperties, StringBuffer paramStringBuffer, Vector paramVector, Locale paramLocale)
    throws PPayException
  {
    String str1 = "PPayAdapter.getReportData";
    if (paramStringBuffer == null) {
      paramStringBuffer = new StringBuffer();
    }
    if (paramProperties != null)
    {
      Set localSet = paramProperties.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        String str3 = paramProperties.getProperty(str2);
        String str4 = null;
        String str5 = null;
        if (str2.equals("Account number"))
        {
          str4 = "accountID";
          str5 = "='" + DBUtil.escapeSQLStringLiteral(str3) + "'";
        }
        else
        {
          if (str2.equals("Account")) {
            continue;
          }
          if (str2.equals("Start Check Number"))
          {
            if ((str3 != null) && (str3.trim().length() != 0))
            {
              str4 = "checkNumber";
              str5 = ">='" + DBUtil.escapeSQLStringLiteral(a(str3, 20)) + "'";
            }
          }
          else if (str2.equals("End Check Number"))
          {
            if ((str3 != null) && (str3.trim().length() != 0))
            {
              str4 = "checkNumber";
              str5 = "<='" + DBUtil.escapeSQLStringLiteral(a(str3, 20)) + "'";
            }
          }
          else
          {
            if ((str2.equals("StartDate")) || (str2.equals("EndDate"))) {
              continue;
            }
            if (str2.equals("Maximum Amount"))
            {
              if ((str3 != null) && (str3.trim().length() != 0))
              {
                str4 = "amount";
                str5 = "<=" + DBUtil.trimSQLNumericLiteral(str3, new StringBuffer());
              }
            }
            else if (str2.equals("Minimum Amount"))
            {
              if ((str3 != null) && (str3.trim().length() != 0))
              {
                str4 = "amount";
                str5 = ">=" + DBUtil.trimSQLNumericLiteral(str3, new StringBuffer());
              }
            }
            else if (str2.equals("Check Void"))
            {
              if ((str3 != null) && (str3.trim().length() != 0))
              {
                str4 = "voidCheck";
                boolean bool = Boolean.valueOf(str3).booleanValue();
                if (bool) {
                  str5 = "='Y'";
                } else {
                  str5 = "='N'";
                }
              }
            }
            else if (str2.equals("Reject Reason"))
            {
              if ((str3 != null) && (str3.trim().length() != 0))
              {
                str4 = "rejectReason";
                str5 = "='" + DBUtil.escapeSQLStringLiteral(str3) + "'";
              }
            }
            else if (str2.equals("Decision"))
            {
              if ((str3 != null) && (str3.trim().length() != 0))
              {
                str4 = "decision";
                str5 = "='" + DBUtil.escapeSQLStringLiteral(str3) + "'";
              }
            }
            else if ((str2.equals("Decision Is Not")) && (str3 != null) && (str3.trim().length() != 0))
            {
              str4 = "decision";
              str5 = "<>'" + DBUtil.escapeSQLStringLiteral(str3) + "'";
            }
          }
        }
        if (str4 != null)
        {
          String str6 = new String(paramStringBuffer).trim();
          if (str6.length() != 0) {
            paramStringBuffer.append(" AND ");
          }
          paramStringBuffer.append(str4);
          paramStringBuffer.append(str5);
        }
      }
      paramStringBuffer.append(" ");
    }
  }
  
  private static void jdField_if(ReportResult paramReportResult, ResultSet paramResultSet, String paramString, Locale paramLocale, int paramInt, PPayAccounts paramPPayAccounts)
    throws Exception
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(7);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(222, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(223, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(205, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(206, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(30);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(208, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(209, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(211, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(212, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(214, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(215, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(217, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(218, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(220, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(20);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    Object localObject1;
    for (int i1 = 0; (paramResultSet.next()) && ((paramInt < 0) || (i1 < paramInt)); i1++)
    {
      localObject1 = new ReportDataItems(paramLocale);
      ((ReportDataItems)localObject1).add().setData(new DateTime(paramResultSet.getDate(11), paramLocale));
      String str = paramResultSet.getString(1);
      PPayAccount localPPayAccount = null;
      for (int i2 = 0; i2 < paramPPayAccounts.size(); i2++)
      {
        localPPayAccount = (PPayAccount)paramPPayAccounts.get(i2);
        if (localPPayAccount.getAccountID().equals(str)) {
          break;
        }
      }
      if (localPPayAccount == null)
      {
        localObject2 = str;
        try
        {
          localObject2 = AccountUtil.buildAccountDisplayText((String)localObject2, paramLocale);
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException1);
          localObject2 = str;
        }
        ((ReportDataItems)localObject1).add().setData(localObject2);
      }
      else
      {
        localObject2 = new StringBuffer();
        ((StringBuffer)localObject2).append(localPPayAccount.getRoutingNumber());
        ((StringBuffer)localObject2).append(" : ");
        try
        {
          ((StringBuffer)localObject2).append(AccountUtil.buildAccountDisplayText(localPPayAccount.getAccountID(), paramLocale));
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException2);
          ((StringBuffer)localObject2).append(localPPayAccount.getAccountID());
        }
        ((StringBuffer)localObject2).append(" - ");
        ((StringBuffer)localObject2).append(localPPayAccount.getNickName());
        ((StringBuffer)localObject2).append(" - ");
        ((StringBuffer)localObject2).append(localPPayAccount.getCurrencyType());
        ((ReportDataItems)localObject1).add().setData(((StringBuffer)localObject2).toString());
      }
      ((ReportDataItems)localObject1).add().setData(jdField_if(paramResultSet.getString(5)));
      ((ReportDataItems)localObject1).add().setData(new DateTime(paramResultSet.getDate(6), paramLocale));
      if (localPPayAccount == null) {
        ((ReportDataItems)localObject1).add().setData(new Currency(paramResultSet.getBigDecimal(7), paramLocale));
      } else {
        ((ReportDataItems)localObject1).add().setData(new Currency(paramResultSet.getBigDecimal(7), localPPayAccount.getCurrencyType(), paramLocale));
      }
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(8).equalsIgnoreCase("Y") ? "VOID" : "");
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(10));
      Object localObject2 = new ReportRow(Locale.getDefault());
      if (i1 % 2 == 1) {
        ((ReportRow)localObject2).set("CELLBACKGROUND", "reportDataShaded");
      }
      ((ReportRow)localObject2).setDataItems((ReportDataItems)localObject1);
      paramReportResult.addRow((ReportRow)localObject2);
    }
    if (i1 == paramInt)
    {
      localObject1 = new HashMap();
      ((HashMap)localObject1).put("TRUNCATED", new Integer(paramInt));
      paramReportResult.setProperties((HashMap)localObject1);
    }
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, String paramString, Locale paramLocale, int paramInt, PPayAccounts paramPPayAccounts)
    throws Exception
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(9);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(222, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(223, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(205, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(206, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(25);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(208, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(209, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(211, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(212, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(214, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(215, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(217, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(218, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(9);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(220, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(20);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(224, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(299, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    Object localObject1;
    for (int i1 = 0; (paramResultSet.next()) && ((paramInt < 0) || (i1 < paramInt)); i1++)
    {
      localObject1 = new ReportDataItems(paramLocale);
      ((ReportDataItems)localObject1).add().setData(new DateTime(paramResultSet.getDate(11), paramLocale));
      String str = paramResultSet.getString(1);
      PPayAccount localPPayAccount = null;
      for (int i2 = 0; i2 < paramPPayAccounts.size(); i2++)
      {
        localPPayAccount = (PPayAccount)paramPPayAccounts.get(i2);
        if (localPPayAccount.getAccountID().equals(str)) {
          break;
        }
      }
      if (localPPayAccount == null)
      {
        localObject2 = str;
        try
        {
          localObject2 = AccountUtil.buildAccountDisplayText((String)localObject2, paramLocale);
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException1);
          localObject2 = str;
        }
        ((ReportDataItems)localObject1).add().setData(localObject2);
      }
      else
      {
        localObject2 = new StringBuffer();
        ((StringBuffer)localObject2).append(localPPayAccount.getRoutingNumber());
        ((StringBuffer)localObject2).append(" : ");
        try
        {
          ((StringBuffer)localObject2).append(AccountUtil.buildAccountDisplayText(localPPayAccount.getAccountID(), paramLocale));
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException2);
          ((StringBuffer)localObject2).append(localPPayAccount.getAccountID());
        }
        ((StringBuffer)localObject2).append(" - ");
        ((StringBuffer)localObject2).append(localPPayAccount.getNickName());
        ((StringBuffer)localObject2).append(" - ");
        ((StringBuffer)localObject2).append(localPPayAccount.getCurrencyType());
        ((ReportDataItems)localObject1).add().setData(((StringBuffer)localObject2).toString());
      }
      ((ReportDataItems)localObject1).add().setData(jdField_if(paramResultSet.getString(5)));
      ((ReportDataItems)localObject1).add().setData(new DateTime(paramResultSet.getDate(6), paramLocale));
      if (localPPayAccount == null) {
        ((ReportDataItems)localObject1).add().setData(new Currency(paramResultSet.getBigDecimal(7), paramLocale));
      } else {
        ((ReportDataItems)localObject1).add().setData(new Currency(paramResultSet.getBigDecimal(7), localPPayAccount.getCurrencyType(), paramLocale));
      }
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(8).equalsIgnoreCase("Y") ? "VOID" : "");
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(10));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(12));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(13));
      Object localObject2 = new ReportRow(Locale.getDefault());
      ((ReportRow)localObject2).setDataItems((ReportDataItems)localObject1);
      if (i1 % 2 == 1) {
        ((ReportRow)localObject2).set("CELLBACKGROUND", "reportDataShaded");
      }
      paramReportResult.addRow((ReportRow)localObject2);
    }
    if (i1 == paramInt)
    {
      localObject1 = new HashMap();
      ((HashMap)localObject1).put("TRUNCATED", new Integer(paramInt));
      paramReportResult.setProperties((HashMap)localObject1);
    }
  }
  
  private static void a(Connection paramConnection, ReportResult paramReportResult, ResultSet paramResultSet, Locale paramLocale, boolean paramBoolean, String paramString1, int paramInt, String paramString2)
    throws Exception
  {
    ReportResult localReportResult1 = null;
    int i1 = 1;
    int i2 = 1;
    Object localObject1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    Object localObject2 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    int i3 = 0;
    int i4 = 0;
    ReportResult localReportResult2 = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult2);
    a(localReportResult2, paramString1, paramLocale);
    for (int i5 = 0; (paramResultSet.next()) && ((paramInt < 0) || (i5 < paramInt)); i5++)
    {
      str4 = paramResultSet.getString(1);
      str1 = paramResultSet.getString(13);
      str2 = paramResultSet.getString(15);
      str3 = paramResultSet.getString(16);
      str5 = paramResultSet.getString(4);
      localObject2 = str5;
      if (localObject1 == null)
      {
        localObject1 = str4;
        i1 = 1;
        i3 = 1;
      }
      else if (localObject1.equals(str4))
      {
        i1 = 0;
        i3++;
      }
      else if (!localObject1.equals(str4))
      {
        i1 = 1;
        try
        {
          a(localReportResult2, " account " + AccountUtil.buildAccountDisplayText(localObject1, paramLocale), i3, "issues", paramLocale);
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString2 + "'.", localUtilException1);
          a(localReportResult2, " account " + localObject1, i3, "issues", paramLocale);
        }
        localObject1 = str4;
        i4 += i3;
        i3 = 1;
      }
      if (i1 != 0)
      {
        if (paramBoolean)
        {
          str6 = paramResultSet.getString(14);
          str7 = paramResultSet.getString(18);
          if ((str7 != null) && (str7.length() > 0)) {
            str6 = str6 + " ( " + str7 + " )";
          }
          if ((paramString1 != null) && (paramString1.equals(str6)))
          {
            i2 = 0;
          }
          else if ((!paramString1.equals(ReportConsts.getText(10015, paramLocale))) && (!paramString1.equals(str6)))
          {
            a(paramReportResult, " business " + paramString1, i4, "issues", paramLocale);
            i2 = 1;
          }
        }
        if (i2 != 0)
        {
          if (paramBoolean) {
            paramString1 = str6;
          }
          localReportResult2 = new ReportResult(paramLocale);
          paramReportResult.addSubReport(localReportResult2);
          a(localReportResult2, paramString1, paramLocale);
          i4 = 0;
          i2 = 0;
        }
        localReportResult1 = new ReportResult(paramLocale);
        localReportResult2.addSubReport(localReportResult1);
        try
        {
          jdField_do(localReportResult1, str3 + " : " + AccountUtil.buildAccountDisplayText(localObject1, paramLocale) + " " + str1 + " - " + str2, paramLocale);
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString2 + "'.", localUtilException2);
          jdField_do(localReportResult1, str3 + " : " + localObject1 + " " + str1 + " - " + str2, paramLocale);
        }
      }
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItems.add().setData(new DateTime(paramResultSet.getDate(11), paramLocale));
      localReportDataItems.add().setData(jdField_if(paramResultSet.getString(5)));
      localReportDataItems.add().setData(new DateTime(paramResultSet.getDate(6), paramLocale));
      localReportDataItems.add().setData(new Currency(paramResultSet.getBigDecimal(7), str2, paramLocale));
      localReportDataItems.add().setData(paramResultSet.getString(8).equalsIgnoreCase("Y") ? "VOID" : "");
      localReportDataItems.add().setData(paramResultSet.getString(10));
      ReportRow localReportRow = new ReportRow(Locale.getDefault());
      if (i3 % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      localReportRow.setDataItems(localReportDataItems);
      localReportResult1.addRow(localReportRow);
    }
    if (localReportResult1 != null)
    {
      try
      {
        a(localReportResult2, " account " + AccountUtil.buildAccountDisplayText(str4, paramLocale), i3, "issues", paramLocale);
      }
      catch (UtilException localUtilException3)
      {
        DebugLog.throwing("Error while constructing account display string for report type, '" + paramString2 + "'.", localUtilException3);
        a(localReportResult2, " account " + str4, i3, "issues", paramLocale);
      }
      i4 += i3;
      a(paramReportResult, " business " + paramString1, i4, "issues", paramLocale);
    }
    if (i5 == paramInt)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("TRUNCATED", new Integer(paramInt));
      paramReportResult.setProperties(localHashMap);
    }
  }
  
  private static void jdField_do(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws Exception
  {
    a(paramReportResult, paramString, paramLocale);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(6);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(222, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(223, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(208, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(209, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(211, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(212, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(214, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(215, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(217, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(218, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(12);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(220, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(40);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdField_if(Connection paramConnection, ReportResult paramReportResult, ResultSet paramResultSet, Locale paramLocale, boolean paramBoolean, String paramString1, int paramInt, String paramString2)
    throws Exception
  {
    ReportResult localReportResult1 = null;
    int i1 = 1;
    int i2 = 1;
    Object localObject1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    Object localObject2 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    int i3 = 0;
    int i4 = 0;
    ReportResult localReportResult2 = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult2);
    a(localReportResult2, paramString1, paramLocale);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    for (int i5 = 0; (paramResultSet.next()) && ((paramInt < 0) || (i5 < paramInt)); i5++)
    {
      str4 = paramResultSet.getString(1);
      str1 = paramResultSet.getString(13);
      str2 = paramResultSet.getString(15);
      str3 = paramResultSet.getString(16);
      str5 = paramResultSet.getString(4);
      localObject2 = str5;
      if (localObject1 == null)
      {
        localObject1 = str4;
        i1 = 1;
        i3 = 1;
      }
      else if (localObject1.equals(str4))
      {
        i1 = 0;
        i3++;
      }
      else if (!localObject1.equals(str4))
      {
        i1 = 1;
        try
        {
          a(localReportResult2, " account " + AccountUtil.buildAccountDisplayText(localObject1, paramLocale), i3, "issues", paramLocale);
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString2 + "'.", localUtilException1);
          a(localReportResult2, " account " + localObject1, i3, "issues", paramLocale);
        }
        localObject1 = str4;
        i4 += i3;
        i3 = 1;
      }
      if (i1 != 0)
      {
        if (paramBoolean)
        {
          str6 = paramResultSet.getString(14);
          str7 = paramResultSet.getString(18);
          if ((str7 != null) && (str7.length() > 0)) {
            str6 = str6 + " ( " + str7 + " )";
          }
          if ((paramString1 != null) && (paramString1.equals(str6)))
          {
            i2 = 0;
          }
          else if ((!paramString1.equals(ReportConsts.getText(10015, paramLocale))) && (!paramString1.equals(str6)))
          {
            a(paramReportResult, " business " + paramString1, i4, "issues", paramLocale);
            i2 = 1;
          }
        }
        if (i2 != 0)
        {
          if (paramBoolean) {
            paramString1 = str6;
          }
          localReportResult2 = new ReportResult(paramLocale);
          paramReportResult.addSubReport(localReportResult2);
          a(localReportResult2, paramString1, paramLocale);
          i4 = 0;
          i2 = 0;
        }
        localReportResult1 = new ReportResult(paramLocale);
        localReportResult2.addSubReport(localReportResult1);
        try
        {
          jdField_if(localReportResult1, str3 + " : " + AccountUtil.buildAccountDisplayText(localObject1, paramLocale) + " " + str1 + " - " + str2, paramLocale);
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString2 + "'.", localUtilException2);
          jdField_if(localReportResult1, str3 + " : " + localObject1 + " " + str1 + " - " + str2, paramLocale);
        }
      }
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItems.add().setData(new DateTime(paramResultSet.getDate(11), paramLocale));
      localReportDataItems.add().setData(jdField_if(paramResultSet.getString(5)));
      localReportDataItems.add().setData(new DateTime(paramResultSet.getDate(6), paramLocale));
      localReportDataItems.add().setData(new Currency(paramResultSet.getBigDecimal(7), str2, paramLocale));
      localReportDataItems.add().setData(paramResultSet.getString(8).equalsIgnoreCase("Y") ? "VOID" : "");
      localReportDataItems.add().setData(paramResultSet.getString(10));
      localReportDataItems.add().setData(paramResultSet.getString(12));
      localReportDataItems.add().setData(paramResultSet.getString(17));
      ReportRow localReportRow = new ReportRow(Locale.getDefault());
      if (i3 % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      localReportRow.setDataItems(localReportDataItems);
      localReportResult1.addRow(localReportRow);
    }
    if (localReportResult1 != null)
    {
      if (!paramBoolean) {
        a(localReportResult2, paramString1, paramLocale);
      }
      try
      {
        a(localReportResult2, " account " + AccountUtil.buildAccountDisplayText(str4, paramLocale), i3, "decisions", paramLocale);
      }
      catch (UtilException localUtilException3)
      {
        DebugLog.throwing("Error while constructing account display string for report type, '" + paramString2 + "'.", localUtilException3);
        a(localReportResult2, " account " + str4, i3, "decisions", paramLocale);
      }
      i4 += i3;
      a(paramReportResult, " business " + paramString1, i4, "decisions", paramLocale);
    }
    if (i5 == paramInt)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("TRUNCATED", new Integer(paramInt));
      paramReportResult.setProperties(localHashMap);
    }
  }
  
  private static void jdField_if(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws Exception
  {
    a(paramReportResult, paramString, paramLocale);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(8);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(222, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(223, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(208, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(209, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(211, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(212, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(214, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(215, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(217, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(218, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(220, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(30);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(224, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(299, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setDataType("java.lang.String");
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(PPayIssues paramPPayIssues, ResultSet paramResultSet, Locale paramLocale, String paramString)
    throws Exception
  {
    while (paramResultSet.next())
    {
      PPayCheckRecord localPPayCheckRecord = new PPayCheckRecord(paramLocale);
      localPPayCheckRecord.setAccountID(paramResultSet.getString(1));
      localPPayCheckRecord.setBankID(paramResultSet.getString(2));
      localPPayCheckRecord.setBankName(paramResultSet.getString(3));
      localPPayCheckRecord.setRoutingNumber(paramResultSet.getString(4));
      localPPayCheckRecord.setCheckNumber(jdField_if(paramResultSet.getString(5)));
      DateTime localDateTime1 = new DateTime(paramResultSet.getDate(6), paramLocale);
      localPPayCheckRecord.setCheckDate(localDateTime1);
      BigDecimal localBigDecimal = paramResultSet.getBigDecimal(7);
      Currency localCurrency = new Currency(localBigDecimal, paramString, paramLocale);
      localPPayCheckRecord.setAmount(localCurrency);
      String str = paramResultSet.getString(8);
      localPPayCheckRecord.setVoidCheck(str.equalsIgnoreCase("Y"));
      localPPayCheckRecord.setAdditionalData(paramResultSet.getString(9));
      PPayIssue localPPayIssue = paramPPayIssues.add();
      localPPayIssue.setCheckRecord(localPPayCheckRecord);
      localPPayIssue.setRejectReason(paramResultSet.getString(10));
      DateTime localDateTime2 = new DateTime(paramResultSet.getDate(11), paramLocale);
      localPPayIssue.setIssueDate(localDateTime2);
      ExtendableTableUtil.setXBeanFields(paramResultSet, localPPayIssue, "PPay_IssueDecisions");
    }
  }
  
  private static String a(String paramString, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    paramString = paramString.trim();
    int i1 = paramInt - paramString.length();
    localStringBuffer.append("                                        ".substring(0, i1));
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
  
  private static String jdField_if(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    return paramString;
  }
  
  private static Locale a(HashMap paramHashMap)
  {
    Locale localLocale;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      UserLocale localUserLocale = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = localUserLocale.getLocale();
    }
    else if ((paramHashMap != null) && (paramHashMap.containsKey("Locale")))
    {
      localLocale = (Locale)paramHashMap.get("Locale");
    }
    else
    {
      localLocale = Locale.getDefault();
    }
    return localLocale;
  }
  
  private static String a(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria, int paramInt, String paramString)
  {
    for (int i1 = 0; i1 < paramReportSortCriteria.size(); i1++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(i1);
      if (localReportSortCriterion.getOrdinal() == paramInt)
      {
        String str = localReportSortCriterion.getName();
        if ((str == null) || (str.equals(""))) {
          return paramString;
        }
        return localReportSortCriterion.getName();
      }
    }
    return paramString;
  }
  
  private static void a(ReportResult paramReportResult, String paramString1, int paramInt, String paramString2, Locale paramLocale)
    throws Exception
  {
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel("Number of " + paramString2 + " for " + paramString1 + ": " + paramInt);
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportResult.setSectionHeading(localReportHeading);
  }
  
  private static void a(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws Exception
  {
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(paramString);
    paramReportResult.setHeading(localReportHeading);
  }
  
  private static Timestamp jdField_do(String paramString)
    throws ParseException
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      java.util.Date localDate = localDateFormat.parse(paramString);
      return new Timestamp(localDate.getTime());
    }
    return null;
  }
  
  private static String a(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "PPayAdapter.getBusinessName";
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(" select business_name from business where business_id= ");
      localStringBuffer.append(paramString);
      if (paramInt != 0)
      {
        localStringBuffer.append(" and affiliate_bank_id = ");
        localStringBuffer.append(paramInt);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        str1 = localResultSet.getString(1);
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof PPayException)) {
        throw ((PPayException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve business name.", localException);
      throw new PPayException(localException, str2, 7, "An error has occurred while accessing the database in order to retrieve business name.");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return str1;
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString)
  {
    String str = new String(paramStringBuffer).trim();
    if (str.length() != 0) {
      paramStringBuffer.append(" AND ");
    }
    paramStringBuffer.append(" " + paramString + " ");
  }
  
  protected static String fixDate(java.util.Date paramDate)
  {
    return DBUtil.fixDate(p, paramDate);
  }
  
  private static void a(ReportCriteria paramReportCriteria, PPayAccounts paramPPayAccounts)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties.keys();
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();
      localObject = localProperties.getProperty(str1);
      if ((((String)localObject).length() <= 0) || (((String)localObject).trim().length() <= 0)) {
        paramReportCriteria.setHiddenSearchCriterion(str1, true);
      }
    }
    String str1 = localProperties.getProperty("Account");
    if ((str1 == null) || (str1.length() <= 0) || (a(str1)))
    {
      paramReportCriteria.setDisplayValue("Account", ReportConsts.getText(10024, paramReportCriteria.getLocale()));
    }
    else
    {
      localObject = new StringBuffer();
      for (int i1 = 0; i1 < paramPPayAccounts.size(); i1++)
      {
        PPayAccount localPPayAccount = (PPayAccount)paramPPayAccounts.get(i1);
        if (i1 > 0) {
          ((StringBuffer)localObject).append(",");
        }
        ((StringBuffer)localObject).append(localPPayAccount.getRoutingNumber());
        ((StringBuffer)localObject).append(" ");
        ((StringBuffer)localObject).append(":");
        ((StringBuffer)localObject).append(" ");
        try
        {
          String str2 = AccountUtil.buildAccountDisplayText(localPPayAccount.getAccountID(), localPPayAccount.getLocale());
          ((StringBuffer)localObject).append(str2);
        }
        catch (UtilException localUtilException)
        {
          DebugLog.throwing("An error occurred while generating an account display string.", localUtilException);
          ((StringBuffer)localObject).append(localPPayAccount.getAccountNumber());
        }
        if ((localPPayAccount.getNickName() != null) && (localPPayAccount.getNickName().length() > 0))
        {
          ((StringBuffer)localObject).append(" - ");
          ((StringBuffer)localObject).append(localPPayAccount.getNickName());
        }
        ((StringBuffer)localObject).append(" - ");
        ((StringBuffer)localObject).append(localPPayAccount.getCurrencyType());
      }
      paramReportCriteria.setDisplayValue("Account", ((StringBuffer)localObject).toString());
    }
    Object localObject = localProperties.getProperty("Check Void");
    if ((localObject != null) && (((String)localObject).trim().length() != 0))
    {
      boolean bool = Boolean.valueOf((String)localObject).booleanValue();
      if (bool) {
        paramReportCriteria.setDisplayValue("Check Void", ReportConsts.getText(10052, paramReportCriteria.getLocale()));
      } else {
        paramReportCriteria.setDisplayValue("Check Void", ReportConsts.getText(10053, paramReportCriteria.getLocale()));
      }
    }
    else
    {
      paramReportCriteria.setDisplayValue("Check Void", ReportConsts.getText(10051, paramReportCriteria.getLocale()));
    }
  }
  
  private static boolean a(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if ("All".equals(str)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.positivepay.adapter.PPayAdapter
 * JD-Core Version:    0.7.0.1
 */