package com.ffusion.istatements.adapters;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.istatements.BalanceSummary;
import com.ffusion.beans.istatements.DailyBalanceSummaries;
import com.ffusion.beans.istatements.DailyBalanceSummary;
import com.ffusion.beans.istatements.InterestBalanceSummary;
import com.ffusion.beans.istatements.InterestSummary;
import com.ffusion.beans.istatements.MonthlyAccountSummary;
import com.ffusion.beans.istatements.ReserveSummary;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.istatements.IStatementException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.PropertiesUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.LocalizableCurrency;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.logging.DebugLog;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class StatementDataAdapter
  implements IStatementDefines
{
  private static boolean jdField_char = false;
  private static String jdField_for = null;
  private static String p = null;
  private static final String c = "DB_PROPERTIES";
  private static final String k = "SETTING";
  private static final String jdField_null = "IStatements";
  public static final String TYPE_DEPOSITS = "Type=Credit";
  public static final String TYPE_TRANSACTIONS = "Type=Check,Type=Debit,Type=ATM";
  public static final String TYPE_CHECK = "Type=Check";
  private static String jdField_case = "Courier";
  private static CMYKColor jdField_goto = new CMYKColor(0, 0, 0, 0);
  private static int jdField_byte = 10;
  private static int jdField_new = 12;
  private static float o = 10.0F;
  private static float jdField_void = 8.0F;
  private static Font jdField_try = null;
  private static Font h = null;
  private static Font s = null;
  private static final String q = "INSERT INTO ISTMT_ACCTS (BANK_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, USER_ID) VALUES (?,?,?,?)";
  private static final String a = "DELETE FROM ISTMT_ACCTS WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ?";
  private static final String j = "SELECT * FROM ISTMT_ACCTS WHERE USER_ID = ?";
  private static final String i = "SELECT * FROM INTS_STATEMENT WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? ORDER BY STMT_DATE DESC";
  private static final String f = "SELECT * FROM MNTHLY_ACCT_SMRY WHERE STMT_ID = ?";
  private static final String l = "SELECT * FROM DAILY_BAL_SUMMARY WHERE STMT_ID = ?";
  private static final String m = "SELECT * FROM INT_BAL_SUMMARY WHERE STMT_ID = ?";
  private static final String r = "SELECT * FROM BALANCE_SUMMARY WHERE STMT_ID = ?";
  private static final String n = "SELECT * FROM TRANSACTIONS WHERE STMT_ID = ?";
  private static final String e = "SELECT MSG_TYPE, MSG_ID, CONTENT FROM INFORMATION WHERE STMT_ID = ? ORDER BY CAST(MSG_ID AS INTEGER)";
  private static final String b = "SELECT * FROM RESERVE_SUMMARY WHERE STMT_ID = ?";
  private static final String d = "SELECT * FROM RES_SUMMARY_TXT WHERE STMT_ID = ? ORDER BY MSG_ID";
  private static final String jdField_else = "SELECT * FROM INTEREST_SUMMARY WHERE STMT_ID = ?";
  private static final String g = "SELECT * FROM INTS_STATEMENT WHERE STMT_ID = ?";
  private static final String jdField_do = "SELECT * FROM STMT_AGREEMENT WHERE USER_ID = ?";
  private static final String jdField_long = "INSERT INTO STMT_AGREEMENT (USER_ID, ACCEPT) VALUES (?,?)";
  private static final String t = "SELECT STMT_ID FROM INTS_STATEMENT WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? AND STMT_DATE = ? AND ACCOUNT_NUMBER IN (SELECT ACCOUNT_NUMBER FROM ISTMT_ACCTS WHERE USER_ID = ?)";
  private static final String jdField_int = "SELECT DISTINCT(STMT_DATE) FROM INTS_STATEMENT WHERE ACCOUNT_NUMBER = ? AND BANK_ID = ? AND ACCOUNT_NUMBER IN (SELECT ACCOUNT_NUMBER FROM ISTMT_ACCTS WHERE USER_ID = ?) ORDER BY STMT_DATE DESC";
  private static final String jdField_if = "DELETE FROM ISTMT_ACCTS WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? AND USER_ID = ?";
  
  public static Statements getStatementList(Accounts paramAccounts)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getStatementList";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Iterator localIterator = null;
    Account localAccount = null;
    ResultSet localResultSet = null;
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    DateTime localDateTime3 = null;
    Statements localStatements = new Statements();
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM INTS_STATEMENT WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? ORDER BY STMT_DATE DESC");
      localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localPreparedStatement.setString(1, localAccount.getBankID());
        localPreparedStatement.setString(2, localAccount.getNumber());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM INTS_STATEMENT WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? ORDER BY STMT_DATE DESC");
        while (localResultSet.next())
        {
          Statement localStatement = (Statement)localStatements.create(localAccount.getNumber(), localResultSet.getString("STMT_ID"));
          localDateTime1 = a(localResultSet.getTimestamp("STMT_DATE"), localStatements.getLocale());
          localStatement.setStatementDate(localDateTime1);
          localStatement.setAccountName(localResultSet.getString("ACCOUNT_NAME"));
          localStatement.setAccountCurrency(localAccount.getCurrencyCode());
          if (localResultSet.getTimestamp("STMT_START_DATE") != null)
          {
            localDateTime2 = a(localResultSet.getTimestamp("STMT_START_DATE"), localStatements.getLocale());
            localStatement.setStatementStartDate(localDateTime2);
          }
          if (localResultSet.getTimestamp("STMT_END_DATE") != null)
          {
            localDateTime3 = a(localResultSet.getTimestamp("STMT_END_DATE"), localStatements.getLocale());
            localStatement.setStatementEndDate(localDateTime3);
          }
          localStatement.set("PROD_CODE", localResultSet.getString("PROD_CODE"));
          localStatement.set("SVC_CHARGE_CODE", String.valueOf(localResultSet.getInt("SVC_CHARGE_CODE")));
          localStatement.set("STMT_CYCLE", localResultSet.getString("STMT_CYCLE"));
          localStatement.set("BANK_ID", localResultSet.getString("BANK_ID"));
          localStatement.set("RET_ADDR1", localResultSet.getString("RET_ADDR1"));
          localStatement.set("RET_ADDR2", localResultSet.getString("RET_ADDR2"));
          localStatement.set("NAME_ADDR1", localResultSet.getString("NAME_ADDR1"));
          localStatement.set("NAME_ADDR2", localResultSet.getString("NAME_ADDR2"));
          localStatement.set("NAME_ADDR3", localResultSet.getString("NAME_ADDR3"));
          localStatement.set("NAME_ADDR4", localResultSet.getString("NAME_ADDR4"));
          localStatement.set("NAME_ADDR5", localResultSet.getString("NAME_ADDR5"));
          localStatement.set("NAME_ADDR6", localResultSet.getString("NAME_ADDR6"));
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
      }
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
    DebugLog.log("StatementDataAdapter.getStatementList : No. of Stmts : " + localStatements.size());
    return localStatements;
  }
  
  public static Statement getStatement(Statement paramStatement)
    throws IStatementException
  {
    jdField_if(paramStatement);
    jdField_new(paramStatement);
    jdField_byte(paramStatement);
    jdField_do(paramStatement);
    jdField_try(paramStatement);
    a(paramStatement);
    jdField_int(paramStatement);
    jdField_for(paramStatement);
    if (paramStatement.getStatementDateValue() == null)
    {
      Transactions localTransactions = paramStatement.getTransactions();
      if (localTransactions != null)
      {
        int i1 = localTransactions.size();
        localTransactions.setSortedBy("DATE,ID");
        if (i1 != 0)
        {
          paramStatement.setStatementStartDate(((Transaction)localTransactions.get(0)).getDateValue());
          paramStatement.setStatementEndDate(((Transaction)localTransactions.get(i1 - 1)).getDateValue());
        }
      }
    }
    return paramStatement;
  }
  
  public static Statement getFullStatement(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getFullStatement";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM INTS_STATEMENT WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM INTS_STATEMENT WHERE STMT_ID = ?");
      if (localResultSet.next())
      {
        paramStatement.setStatementDate(a(localResultSet.getTimestamp("STMT_DATE"), Locale.getDefault()));
        paramStatement.setAccountNumber(localResultSet.getString("ACCOUNT_NUMBER"));
        paramStatement.setAccountName(localResultSet.getString("ACCOUNT_NAME"));
        if (localResultSet.getTimestamp("STMT_START_DATE") != null) {
          paramStatement.setStatementStartDate(a(localResultSet.getTimestamp("STMT_START_DATE"), Locale.getDefault()));
        }
        if (localResultSet.getTimestamp("STMT_END_DATE") != null) {
          paramStatement.setStatementEndDate(a(localResultSet.getTimestamp("STMT_END_DATE"), Locale.getDefault()));
        }
        paramStatement.set("PROD_CODE", localResultSet.getString("PROD_CODE"));
        paramStatement.set("SVC_CHARGE_CODE", String.valueOf(localResultSet.getInt("SVC_CHARGE_CODE")));
        paramStatement.set("STMT_CYCLE", localResultSet.getString("STMT_CYCLE"));
        paramStatement.set("BANK_ID", localResultSet.getString("BANK_ID"));
        paramStatement.set("RET_ADDR1", localResultSet.getString("RET_ADDR1"));
        paramStatement.set("RET_ADDR2", localResultSet.getString("RET_ADDR2"));
        paramStatement.setNameAddress1(localResultSet.getString("NAME_ADDR1"));
        paramStatement.setNameAddress2(localResultSet.getString("NAME_ADDR2"));
        paramStatement.setNameAddress3(localResultSet.getString("NAME_ADDR3"));
        paramStatement.setNameAddress4(localResultSet.getString("NAME_ADDR4"));
        paramStatement.setNameAddress5(localResultSet.getString("NAME_ADDR5"));
        paramStatement.setNameAddress6(localResultSet.getString("NAME_ADDR6"));
        a(paramStatement, localResultSet);
      }
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
      getStatement(paramStatement);
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement);
    }
    return paramStatement;
  }
  
  public static void initialize(HashMap paramHashMap)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.initialize";
    try
    {
      if (!jdField_char)
      {
        jdField_for = (String)paramHashMap.get("URL");
        if ((jdField_for == null) || (jdField_for.equals(""))) {
          throw new MalformedURLException("Configuration file URL is null or blank");
        }
        InputStream localInputStream = ResourceUtil.getResourceAsStream(new StatementDataAdapter(), jdField_for);
        if (localInputStream != null)
        {
          String str2 = null;
          XMLTag localXMLTag = null;
          HashMap localHashMap = null;
          Object localObject1 = null;
          Object localObject2 = null;
          Properties localProperties = null;
          str2 = Strings.streamToString(localInputStream);
          localXMLTag = new XMLTag(true);
          localXMLTag.build(str2);
          localHashMap = XMLUtil.tagToHashMap(localXMLTag);
          localProperties = (Properties)localHashMap.get("DB_PROPERTIES");
          if (localProperties == null)
          {
            DebugLog.log("<DB_PROPERTIES> tag not found  in XML configuration file during intialization");
            throw new IStatementException(str1, 36117, "<DB_PROPERTIES> tag not found  in XML configuration file");
          }
          try
          {
            p = ConnectionPool.init(localProperties);
          }
          catch (PoolException localPoolException)
          {
            DebugLog.throwing("Unable to create a DB Connection pool during initialization.", localPoolException);
            throw new IStatementException(localPoolException, str1, 36117, "Unable to create a DB Connection pool");
          }
          jdField_char = true;
        }
      }
    }
    catch (Exception localException)
    {
      jdField_char = false;
      a(localException, str1, "Unable to initialize istatements adapter", 36117);
    }
  }
  
  public static void addAccountsForIStatement(SecureUser paramSecureUser, Accounts paramAccounts)
    throws IStatementException
  {
    String str = "StatementDataAdapter.addAccountsForIStatement";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Account localAccount = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO ISTMT_ACCTS (BANK_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, USER_ID) VALUES (?,?,?,?)");
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localPreparedStatement.setString(1, localAccount.getBankID());
        localPreparedStatement.setString(2, localAccount.getNumber());
        localPreparedStatement.setInt(3, localAccount.getTypeValue());
        localPreparedStatement.setString(4, paramSecureUser.getId());
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO ISTMT_ACCTS (BANK_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, USER_ID) VALUES (?,?,?,?)");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      if (localConnection != null) {
        DBUtil.rollback(localConnection);
      }
      a(localException1, str);
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          DBUtil.endTransaction(localConnection);
        }
        catch (Exception localException2)
        {
          DebugLog.log("StatementDataAdapter.addAccountsForIStatement : Failed to end transaction");
        }
      }
      DBUtil.closeAll(p, localConnection, localPreparedStatement);
    }
  }
  
  public static void removeAccountsForIStatement(Accounts paramAccounts)
    throws IStatementException
  {
    String str = "StatementDataAdapter.removeAccountsForIStatement";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Account localAccount = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM ISTMT_ACCTS WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ?");
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localPreparedStatement.setString(1, localAccount.getBankID());
        localPreparedStatement.setString(2, localAccount.getNumber());
        DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM ISTMT_ACCTS WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ?");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      if (localConnection != null) {
        DBUtil.rollback(localConnection);
      }
      a(localException1, str);
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          DBUtil.endTransaction(localConnection);
        }
        catch (Exception localException2)
        {
          DebugLog.log("StatementDataAdapter.removeAccountsForIStatement : Failed to end transaction");
        }
      }
      DBUtil.closeAll(p, localConnection, localPreparedStatement);
    }
  }
  
  public static Accounts getAccountsForIStatement(SecureUser paramSecureUser)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getAccountsForIStatements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts = new Accounts();
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM ISTMT_ACCTS WHERE USER_ID = ?");
      DebugLog.log("StatementDataAdapter.getAccountsForIStatement User ID : " + paramSecureUser.getId());
      localPreparedStatement.setString(1, paramSecureUser.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM ISTMT_ACCTS WHERE USER_ID = ?");
      while (localResultSet.next()) {
        localAccounts.create(localResultSet.getString("BANK_ID"), localResultSet.getString("ACCOUNT_NUMBER"), localResultSet.getInt("ACCOUNT_TYPE"));
      }
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
    DebugLog.log("StatementDataAdapter.getAccountsForIStatement No. of Accts : " + localAccounts.size());
    return localAccounts;
  }
  
  public static String getStatementAgreement(SecureUser paramSecureUser)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.getStatementAgreement";
    String str2 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts = new Accounts();
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM STMT_AGREEMENT WHERE USER_ID = ?");
      DebugLog.log("StatementDataAdapter.getStatementAgreement User ID : " + paramSecureUser.getId());
      localPreparedStatement.setString(1, paramSecureUser.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM STMT_AGREEMENT WHERE USER_ID = ?");
      if (localResultSet.next()) {
        str2 = localResultSet.getString("ACCEPT");
      } else {
        str2 = new String("N");
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
    DebugLog.log("StatementDataAdapter.getStatementAgreement Accept : " + str2);
    return str2;
  }
  
  public static void setStatementAgreement(SecureUser paramSecureUser, String paramString)
    throws IStatementException
  {
    String str = "StatementDataAdapter.setStatementAgreement";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Accounts localAccounts = new Accounts();
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO STMT_AGREEMENT (USER_ID, ACCEPT) VALUES (?,?)");
      DebugLog.log("StatementDataAdapter.setStatementAgreement User ID : " + paramSecureUser.getId());
      localPreparedStatement.setString(1, paramSecureUser.getId());
      localPreparedStatement.setString(2, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO STMT_AGREEMENT (USER_ID, ACCEPT) VALUES (?,?)");
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement);
    }
    DebugLog.log("StatementDataAdapter.getStatementAgreement");
  }
  
  public static void getPDF(Statement paramStatement, boolean paramBoolean, OutputStream paramOutputStream, Locale paramLocale)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getPDF";
    Document localDocument = new Document();
    try
    {
      PdfWriter localPdfWriter = PdfWriter.getInstance(localDocument, paramOutputStream);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36101, "PdfWriter creation failed");
    }
    a(localDocument, paramStatement, paramBoolean, paramLocale);
  }
  
  public static void getCSV(Statement paramStatement, PrintWriter paramPrintWriter, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.getCSV";
    String str2 = System.getProperty("line.separator");
    String[] arrayOfString = new String[3];
    Object[] arrayOfObject = new Object[1];
    try
    {
      Transactions localTransactions = paramStatement.getTransactions();
      localTransactions.setFilter("All");
      Iterator localIterator = localTransactions.iterator();
      for (int i1 = 0; i1 < arrayOfString.length; i1++)
      {
        arrayOfObject[0] = new Integer(i1 + 1);
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "TRANSACTION_DESCR_FIELD", arrayOfObject);
        arrayOfString[i1] = ((String)localLocalizableString.localize(paramLocale));
      }
      String str3 = "\"" + ResourceUtil.getString("TRANSACTION_DATE", "com.ffusion.istatements.resources", paramLocale) + "\",\"" + ResourceUtil.getString("TRANSACTION_AMOUNT", "com.ffusion.istatements.resources", paramLocale) + "\",\"" + arrayOfString[0] + "\",\"" + arrayOfString[1] + "\",\"" + arrayOfString[2] + "\"" + str2;
      paramPrintWriter.write(str3);
      while (localIterator.hasNext())
      {
        Transaction localTransaction = (Transaction)localIterator.next();
        StringBuffer localStringBuffer = new StringBuffer();
        a(localStringBuffer, a(localTransaction.getDateValue(), paramLocale, false), true);
        a(localStringBuffer, a(localTransaction.getAmountValue(), paramLocale, false, false), true);
        a(localStringBuffer, localTransaction.getDescription(), true);
        a(localStringBuffer, (String)localTransaction.get("DESCRIPTION2"), true);
        a(localStringBuffer, (String)localTransaction.get("DESCRIPTION3"), false);
        a(localStringBuffer, str2, false);
        paramPrintWriter.write(localStringBuffer.toString());
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36102, "Failed to create CSV");
    }
  }
  
  public static void addAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws IStatementException
  {
    String str = "StatementDataAdapter.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Account localAccount = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO ISTMT_ACCTS (BANK_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, USER_ID) VALUES (?,?,?,?)");
      localPreparedStatement.setString(4, paramString);
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localPreparedStatement.setString(1, localAccount.getBankID());
        localPreparedStatement.setString(2, localAccount.getNumber());
        localPreparedStatement.setInt(3, localAccount.getTypeValue());
        if (DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO ISTMT_ACCTS (BANK_ID, ACCOUNT_NUMBER, ACCOUNT_TYPE, USER_ID) VALUES (?,?,?,?)") != 1)
        {
          DBUtil.rollback(localConnection);
          localIStatementException2 = new IStatementException("StatementDataAdapter.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36121, "Failed to add an online statement account.");
          DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
          throw localIStatementException2;
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (PoolException localPoolException)
    {
      DBUtil.rollback(localConnection);
      localIStatementException2 = new IStatementException(localPoolException, "StatementDataAdapter.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36121, "Failed to retrieve a database connection.");
      DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
      throw localIStatementException2;
    }
    catch (SQLException localSQLException)
    {
      DBUtil.rollback(localConnection);
      localIStatementException2 = new IStatementException(localSQLException, "StatementDataAdapter.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36121, "A SQL-related error occurred.");
      DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
      throw localIStatementException2;
    }
    catch (IStatementException localIStatementException1)
    {
      throw localIStatementException1;
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      IStatementException localIStatementException2 = new IStatementException(localException1, "StatementDataAdapter.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36100, "An unknown error occurred.");
      DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
      throw localIStatementException2;
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          DBUtil.endTransaction(localConnection);
        }
        catch (Exception localException2)
        {
          DebugLog.log("StatementDataAdapter.addAccountsForIStatement(SecureUser, String, Accounts, HashMap): Failed to end transaction.");
        }
      }
      DBUtil.closeAll(p, localConnection, localPreparedStatement);
    }
  }
  
  public static void removeAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws IStatementException
  {
    String str = "StatementDataAdapter.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Account localAccount = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM ISTMT_ACCTS WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? AND USER_ID = ?");
      localPreparedStatement.setString(3, paramString);
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        localPreparedStatement.setString(1, localAccount.getBankID());
        localPreparedStatement.setString(2, localAccount.getNumber());
        if (DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM ISTMT_ACCTS WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? AND USER_ID = ?") != 1)
        {
          DBUtil.rollback(localConnection);
          localIStatementException2 = new IStatementException("StatementDataAdapter.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36121, "Failed to remove an online statement account.");
          DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
          throw localIStatementException2;
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (PoolException localPoolException)
    {
      DBUtil.rollback(localConnection);
      localIStatementException2 = new IStatementException(localPoolException, "StatementDataAdapter.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36121, "Failed to retrieve a database connection.");
      DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
      throw localIStatementException2;
    }
    catch (SQLException localSQLException)
    {
      DBUtil.rollback(localConnection);
      localIStatementException2 = new IStatementException(localSQLException, "StatementDataAdapter.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36121, "A SQL-related error occurred.");
      DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
      throw localIStatementException2;
    }
    catch (IStatementException localIStatementException1)
    {
      throw localIStatementException1;
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      IStatementException localIStatementException2 = new IStatementException(localException1, "StatementDataAdapter.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 36100, "An unknown error occurred.");
      DebugLog.throwing(localIStatementException2.getLocalizedMessage(), localIStatementException2);
      throw localIStatementException2;
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          DBUtil.endTransaction(localConnection);
        }
        catch (Exception localException2)
        {
          DebugLog.log("StatementDataAdapter.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap): Failed to end transaction.");
        }
      }
      DBUtil.closeAll(p, localConnection, localPreparedStatement);
    }
  }
  
  public static Accounts getAccountsForIStatement(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getAccountsForIStatement(SecureUser, String, HashMap)";
    Accounts localAccounts = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localAccounts = new Accounts(paramSecureUser.getLocale());
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM ISTMT_ACCTS WHERE USER_ID = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM ISTMT_ACCTS WHERE USER_ID = ?");
      while (localResultSet.next()) {
        localAccounts.create(localResultSet.getString("BANK_ID"), localResultSet.getString("ACCOUNT_NUMBER"), localResultSet.getInt("ACCOUNT_TYPE"));
      }
    }
    catch (PoolException localPoolException)
    {
      DBUtil.rollback(localConnection);
      localIStatementException = new IStatementException(localPoolException, "StatementDataAdapter.getAccountsForIStatement(SecureUser, String, HashMap)", 36121, "Failed to retrieve a database connection.");
      DebugLog.throwing(localIStatementException.getLocalizedMessage(), localIStatementException);
      throw localIStatementException;
    }
    catch (SQLException localSQLException)
    {
      DBUtil.rollback(localConnection);
      localIStatementException = new IStatementException(localSQLException, "StatementDataAdapter.getAccountsForIStatement(SecureUser, String, HashMap)", 36121, "A SQL-related error occurred.");
      DebugLog.throwing(localIStatementException.getLocalizedMessage(), localIStatementException);
      throw localIStatementException;
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      IStatementException localIStatementException = new IStatementException(localException, "StatementDataAdapter.getAccountsForIStatement(SecureUser, String, HashMap)", 36100, "An unknown error occurred.");
      DebugLog.throwing(localIStatementException.getLocalizedMessage(), localIStatementException);
      throw localIStatementException;
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static String getStatementID(SecureUser paramSecureUser, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws IStatementException
  {
    String str1 = null;
    String str2 = "StatementDataAdapter.getStatementID";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      int i1 = 1;
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT STMT_ID FROM INTS_STATEMENT WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? AND STMT_DATE = ? AND ACCOUNT_NUMBER IN (SELECT ACCOUNT_NUMBER FROM ISTMT_ACCTS WHERE USER_ID = ?)");
      localPreparedStatement.setString(i1++, paramSecureUser.getBankID());
      localPreparedStatement.setString(i1++, paramString);
      localPreparedStatement.setDate(i1++, new java.sql.Date(paramDateTime.getTimeInMillis()));
      localPreparedStatement.setString(i1++, paramSecureUser.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT STMT_ID FROM INTS_STATEMENT WHERE BANK_ID = ? AND ACCOUNT_NUMBER = ? AND STMT_DATE = ? AND ACCOUNT_NUMBER IN (SELECT ACCOUNT_NUMBER FROM ISTMT_ACCTS WHERE USER_ID = ?)");
      if (localResultSet.next()) {
        str1 = localResultSet.getString("STMT_ID");
      }
    }
    catch (PoolException localPoolException)
    {
      localIStatementException = new IStatementException(localPoolException, str2, 36121, "Failed to retrieve a database connection.");
      DebugLog.throwing(localIStatementException.getLocalizedMessage(), localIStatementException);
      throw localIStatementException;
    }
    catch (SQLException localSQLException)
    {
      IStatementException localIStatementException = new IStatementException(localSQLException, str2, 36121, "A SQL-related error occurred.");
      DebugLog.throwing(localIStatementException.getLocalizedMessage(), localIStatementException);
      throw localIStatementException;
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str2, 36100, "An unknown error occurred.");
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
    return str1;
  }
  
  public static FilteredList getStatementDates(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws IStatementException
  {
    FilteredList localFilteredList = new FilteredList();
    String str = "StatementDataAdapter.getStatementDates";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      int i1 = 1;
      localObject1 = null;
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT DISTINCT(STMT_DATE) FROM INTS_STATEMENT WHERE ACCOUNT_NUMBER = ? AND BANK_ID = ? AND ACCOUNT_NUMBER IN (SELECT ACCOUNT_NUMBER FROM ISTMT_ACCTS WHERE USER_ID = ?) ORDER BY STMT_DATE DESC");
      localPreparedStatement.setString(i1++, paramString);
      localPreparedStatement.setString(i1++, paramSecureUser.getBankID());
      localPreparedStatement.setString(i1++, paramSecureUser.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT DISTINCT(STMT_DATE) FROM INTS_STATEMENT WHERE ACCOUNT_NUMBER = ? AND BANK_ID = ? AND ACCOUNT_NUMBER IN (SELECT ACCOUNT_NUMBER FROM ISTMT_ACCTS WHERE USER_ID = ?) ORDER BY STMT_DATE DESC");
      while (localResultSet.next())
      {
        localObject1 = a(localResultSet.getTimestamp("STMT_DATE"), paramSecureUser.getLocale());
        localFilteredList.add(localObject1);
      }
    }
    catch (PoolException localPoolException)
    {
      localObject1 = new IStatementException(localPoolException, str, 36121, "Failed to retrieve a database connection.");
      DebugLog.throwing(((IStatementException)localObject1).getLocalizedMessage(), (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    catch (SQLException localSQLException)
    {
      Object localObject1 = new IStatementException(localSQLException, str, 36121, "A SQL-related error occurred.");
      DebugLog.throwing(((IStatementException)localObject1).getLocalizedMessage(), (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36100, "An unknown error occurred.");
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
    return localFilteredList;
  }
  
  private static String a(HashMap paramHashMap, String paramString1, String paramString2)
    throws Exception
  {
    Object localObject = paramHashMap.get(paramString1);
    if (localObject == null)
    {
      if (paramString2 == null) {
        throw new Exception(paramString1 + " tag not found");
      }
      return paramString2;
    }
    return localObject.toString();
  }
  
  private static void a(Exception paramException, String paramString)
    throws IStatementException
  {
    System.err.println("-----------------");
    paramException.printStackTrace();
    System.err.println("-----------------");
    if ((paramException instanceof IStatementException)) {
      throw ((IStatementException)paramException);
    }
    throw new IStatementException(paramException, paramString, 36118, "");
  }
  
  private static void a(Exception paramException, String paramString1, String paramString2, int paramInt)
    throws IStatementException
  {
    if ((paramException instanceof IStatementException)) {
      throw ((IStatementException)paramException);
    }
    throw new IStatementException(paramException, paramString1, paramInt, paramString2);
  }
  
  private static void a(String paramString1, String paramString2, int paramInt)
    throws IStatementException
  {
    throw new IStatementException(paramString1, paramInt, paramString2);
  }
  
  private static String a(Timestamp paramTimestamp)
  {
    if (paramTimestamp != null)
    {
      Calendar localCalendar = Calendar.getInstance();
      return DateFormatUtil.getFormatter("MM/dd/yyyy").format(paramTimestamp);
    }
    return "";
  }
  
  private static DateTime a(Timestamp paramTimestamp, Locale paramLocale)
  {
    if (paramTimestamp != null)
    {
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(new java.util.Date(paramTimestamp.getTime()));
      DateTime localDateTime = new DateTime(localCalendar, paramLocale);
      return localDateTime;
    }
    return null;
  }
  
  private static void jdField_if(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getMonthlyAccountSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MonthlyAccountSummary localMonthlyAccountSummary = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM MNTHLY_ACCT_SMRY WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM MNTHLY_ACCT_SMRY WHERE STMT_ID = ?");
      if (localResultSet.next())
      {
        localMonthlyAccountSummary = new MonthlyAccountSummary(paramStatement.getLocale());
        localMonthlyAccountSummary.set("HEADING", localResultSet.getString("HEADING"));
        localMonthlyAccountSummary.setAvgColBal(a(paramStatement, localResultSet.getString("AVG_COLL_BALANCE")));
        localMonthlyAccountSummary.setNumDeposits(localResultSet.getInt("DEPOSITS"));
        localMonthlyAccountSummary.setItemsDeposited(localResultSet.getInt("ITEMS_DEPOSITED"));
        localMonthlyAccountSummary.setNumWithdrawals(localResultSet.getInt("WITHDRAWALS"));
        localMonthlyAccountSummary.setCashItemsDeposited(localResultSet.getInt("CASH_DEPOSITED"));
        localMonthlyAccountSummary.setTransTowardLimit(localResultSet.getInt("TRANS_TO_LIMIT"));
        localMonthlyAccountSummary.setNumElectronicTrans(localResultSet.getInt("ELEC_TRANS"));
        localMonthlyAccountSummary.setNumExcessTrans(localResultSet.getInt("NUM_XS_TRANS"));
        localMonthlyAccountSummary.setExcessFeeAmount(a(paramStatement, localResultSet.getString("TRANS_FEE")));
        localMonthlyAccountSummary.setExcessLimit(localResultSet.getInt("XS_LIMIT"));
        localMonthlyAccountSummary.setExcessTransTotalFeeAmount(a(paramStatement, localResultSet.getString("XS_TRANS_TOT_FEE")));
      }
      paramStatement.setMonthlyAccountSummary(localMonthlyAccountSummary);
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdField_do(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getDailyBalanceSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    DailyBalanceSummary localDailyBalanceSummary = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM DAILY_BAL_SUMMARY WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM DAILY_BAL_SUMMARY WHERE STMT_ID = ?");
      DailyBalanceSummaries localDailyBalanceSummaries = new DailyBalanceSummaries(paramStatement.getLocale());
      while (localResultSet.next())
      {
        localDailyBalanceSummary = (DailyBalanceSummary)localDailyBalanceSummaries.create();
        localDailyBalanceSummary.setAmount(a(paramStatement, localResultSet.getString("BALANCE")));
        localDailyBalanceSummary.setReserveInUseAmount(a(paramStatement, localResultSet.getString("RES_IN_USE")));
        Locale localLocale = paramStatement.getLocale();
        DateTime localDateTime = null;
        localLocale = localLocale == null ? Locale.getDefault() : localLocale;
        localDateTime = a(localResultSet.getTimestamp("SUMMARY_DATE"), localLocale);
        localDailyBalanceSummary.setBalanceDate(localDateTime);
      }
      paramStatement.setDailyBalanceSummaries(localDailyBalanceSummaries);
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdField_new(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getInterestBalanceSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    InterestBalanceSummary localInterestBalanceSummary = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM INT_BAL_SUMMARY WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM INT_BAL_SUMMARY WHERE STMT_ID = ?");
      if (localResultSet.next())
      {
        localInterestBalanceSummary = new InterestBalanceSummary(paramStatement.getLocale());
        localInterestBalanceSummary.set("HEADING1", localResultSet.getString("HEADING1"));
        localInterestBalanceSummary.set("HEADING2", localResultSet.getString("HEADING2"));
        localInterestBalanceSummary.setReserveReqAmount(a(paramStatement, localResultSet.getString("FED_RES_REQMT")));
        localInterestBalanceSummary.setAvgLedgerBalanceAmount(a(paramStatement, localResultSet.getString("AVG_LEDGER_BAL")));
        localInterestBalanceSummary.setAvgFloatAmount(a(paramStatement, localResultSet.getString("AVG_FLOAT")));
        localInterestBalanceSummary.setAvgColAmount(a(paramStatement, localResultSet.getString("AVG_COLL_BALANCE")));
        localInterestBalanceSummary.setAvgReserveReqAmount(a(paramStatement, localResultSet.getString("AVG_RESERVE_REQMT")));
        localInterestBalanceSummary.setAvgBalQualIntAmount(a(paramStatement, localResultSet.getString("AVG_BAL_QLFY_INT")));
        localInterestBalanceSummary.setAvgIntRate(localResultSet.getDouble("AVG_INT_RATE"));
        localInterestBalanceSummary.setAvgDailyAccrAmount(a(paramStatement, localResultSet.getString("AVG_DAILY_ACCR")));
        localInterestBalanceSummary.setNumDaysQual(localResultSet.getInt("NO_OF_DAYS_QLFYING"));
        localInterestBalanceSummary.setTotalIntCreditAmount(a(paramStatement, localResultSet.getString("TOT_INT_CR_NXT_STM")));
        localInterestBalanceSummary.setNumDaysOver(localResultSet.getInt("NUM_DAYS_OVERDRAWN"));
        localInterestBalanceSummary.setAvgOverAmount(a(paramStatement, localResultSet.getString("AVG_OVERDRAFT")));
        localInterestBalanceSummary.setAvgOverRate(localResultSet.getDouble("AVG_OVERDRAFT_RATE"));
        localInterestBalanceSummary.setAvgDailyOverAmount(a(paramStatement, localResultSet.getString("AV_DLY_OVRDRFT_CHG")));
        localInterestBalanceSummary.setNumDaysCharged(localResultSet.getInt("NUM_DAYS_CHGD"));
        localInterestBalanceSummary.setTotalOverChargedAmount(a(paramStatement, localResultSet.getString("TOT_OD_CHGD_NXT")));
      }
      paramStatement.setInterestBalanceSummary(localInterestBalanceSummary);
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdField_byte(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getBalanceSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      BalanceSummary localBalanceSummary = new BalanceSummary(paramStatement.getLocale());
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM BALANCE_SUMMARY WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM BALANCE_SUMMARY WHERE STMT_ID = ?");
      if (localResultSet.next())
      {
        localBalanceSummary.set("HEADING_TEXT", localResultSet.getString("HEADING_TEXT"));
        localBalanceSummary.setPreviousBalanceDate(a(localResultSet.getTimestamp("PREV_BALANCE_DATE"), paramStatement.getLocale()));
        localBalanceSummary.setPreviousAmount(a(paramStatement, localResultSet.getString("PREV_BAL_AMT")));
        localBalanceSummary.setNewBalanceDate(a(localResultSet.getTimestamp("NEW_BAL_DATE"), paramStatement.getLocale()));
        localBalanceSummary.setNewAmount(a(paramStatement, localResultSet.getString("NEW_BAL_AMT")));
        localBalanceSummary.setNumDeposits(localResultSet.getInt("NO_OF_DEPOSITS"));
        localBalanceSummary.setTotalDeposits(a(paramStatement, localResultSet.getString("DEPOSITS_TOTAL")));
        localBalanceSummary.setNumWithdrawals(localResultSet.getInt("NO_OF_WITHDRAWALS"));
        localBalanceSummary.setTotalWithdrawals(a(paramStatement, localResultSet.getString("WITHDRAWALS_TOTAL")));
      }
      paramStatement.setBalanceSummary(localBalanceSummary);
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static Currency a(Statement paramStatement, String paramString)
  {
    return new Currency(paramString, paramStatement.getAccountCurrency(), paramStatement.getLocale());
  }
  
  private static void jdField_try(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getTransactions";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      Currency localCurrency = null;
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM TRANSACTIONS WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM TRANSACTIONS WHERE STMT_ID = ?");
      Object localObject1;
      if (paramStatement.getTransactions() == null)
      {
        localObject1 = new Transactions();
        paramStatement.setTransactions((Transactions)localObject1);
      }
      while (localResultSet.next())
      {
        localObject1 = null;
        DateTime localDateTime = null;
        Locale localLocale = null;
        int i1 = -1;
        localLocale = paramStatement.getLocale();
        localLocale = localLocale == null ? Locale.getDefault() : localLocale;
        localObject1 = paramStatement.getTransactions().create();
        ((Transaction)localObject1).setID(localResultSet.getString("TRANSACTION_ID"));
        i1 = localResultSet.getInt("TRANS_TYPE");
        ((Transaction)localObject1).setType(i1);
        localDateTime = a(localResultSet.getTimestamp("TRANS_DATE"), localLocale);
        ((Transaction)localObject1).setDate(localDateTime);
        localCurrency = a(paramStatement, localResultSet.getString("TRANS_AMOUNT"));
        if (localCurrency.doubleValue() < 0.0D)
        {
          ((Transaction)localObject1).setIsCredit(false);
          localCurrency.setAmount(localCurrency.getAmountValue().negate());
        }
        else
        {
          ((Transaction)localObject1).setIsCredit(true);
        }
        ((Transaction)localObject1).setAmount(localCurrency);
        if (localResultSet.getString("CARD_NUMBER") != null) {
          ((Transaction)localObject1).set("CARD_NUMBER", localResultSet.getString("CARD_NUMBER"));
        }
        if (localResultSet.getString("DESCRIPTION1") != null) {
          ((Transaction)localObject1).setDescription(localResultSet.getString("DESCRIPTION1"));
        }
        if (localResultSet.getString("DESCRIPTION2") != null) {
          ((Transaction)localObject1).set("DESCRIPTION2", localResultSet.getString("DESCRIPTION2"));
        }
        if (localResultSet.getString("DESCRIPTION3") != null) {
          ((Transaction)localObject1).set("DESCRIPTION3", localResultSet.getString("DESCRIPTION3"));
        }
      }
      paramStatement.getTransactions().setSortedBy("DATE,ID");
      DebugLog.log("StatementDataAdapter.getTransactions Number of Transactions : " + paramStatement.getTransactions().size());
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void a(Statement paramStatement)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.getInformation";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT MSG_TYPE, MSG_ID, CONTENT FROM INFORMATION WHERE STMT_ID = ? ORDER BY CAST(MSG_ID AS INTEGER)");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT MSG_TYPE, MSG_ID, CONTENT FROM INFORMATION WHERE STMT_ID = ? ORDER BY CAST(MSG_ID AS INTEGER)");
      Messages localMessages = paramStatement.getMessages();
      while (localResultSet.next())
      {
        Message localMessage = a(localMessages, localResultSet.getString("MSG_TYPE"));
        if (localMessage == null) {
          localMessage = localMessages.create();
        }
        localMessage.setID(localResultSet.getString("MSG_ID"));
        localMessage.set("MSG_TYPE", localResultSet.getString("MSG_TYPE"));
        String str2 = localMessage.getMemo();
        if (str2 == null) {
          localMessage.setMemo(localResultSet.getString("CONTENT"));
        } else {
          localMessage.setMemo(str2 + " " + localResultSet.getString("CONTENT"));
        }
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdField_int(Statement paramStatement)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.getReserveSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "SELECT * FROM RESERVE_SUMMARY WHERE STMT_ID = ?");
      localPreparedStatement1.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, "SELECT * FROM RESERVE_SUMMARY WHERE STMT_ID = ?");
      ReserveSummary localReserveSummary = new ReserveSummary(paramStatement.getLocale());
      if (localResultSet.next())
      {
        localReserveSummary.setPreviousReserveAmount(a(paramStatement, localResultSet.getString("PREV_RES_IN_USE")));
        localReserveSummary.setPreviousReserveDate(a(localResultSet.getTimestamp("PRV_RES_IN_USE_DTE"), paramStatement.getLocale()));
        localReserveSummary.setPmtsOnReserveAmount(a(paramStatement, localResultSet.getString("PMTS_ON_RESERVE")));
        localReserveSummary.setReserveTransAmount(a(paramStatement, localResultSet.getString("RES_TRANSACTIONS")));
        localReserveSummary.setFinanceChargeAmount(a(paramStatement, localResultSet.getString("FINANCE_CHARGE")));
        localReserveSummary.setServiceChargeAmount(a(paramStatement, localResultSet.getString("SVC_CHARGE")));
        localReserveSummary.setNewReserveAmount(a(paramStatement, localResultSet.getString("NEW_RES_IN_USE")));
        localReserveSummary.setNewReserveDate(a(localResultSet.getTimestamp("NEW_RES_IN_USE_DTE"), paramStatement.getLocale()));
        localReserveSummary.setPeriodIntRate(localResultSet.getDouble("PERIODIC_INT_RATE"));
        localReserveSummary.setAnnualIntRate(localResultSet.getDouble("ANNUAL_INT_RATE"));
        localReserveSummary.setApprovedReserveAmount(a(paramStatement, localResultSet.getString("APPROVED_RES")));
        localReserveSummary.setAvailableReserveAmount(a(paramStatement, localResultSet.getString("AVAILABLE_RES")));
        localReserveSummary.setResInUseFinanceChargeAmount(a(paramStatement, localResultSet.getString("AV_DY_RES_INUSE_FC")));
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "SELECT * FROM RES_SUMMARY_TXT WHERE STMT_ID = ? ORDER BY MSG_ID");
        localPreparedStatement2.setString(1, paramStatement.getID());
        StringBuffer localStringBuffer = new StringBuffer();
        localResultSet = DBUtil.executeQuery(localPreparedStatement2, "SELECT * FROM RES_SUMMARY_TXT WHERE STMT_ID = ? ORDER BY MSG_ID");
        while (localResultSet.next())
        {
          String str2 = localResultSet.getString("RES_SUMMARY_TXT");
          if ((str2 != null) && (str2.length() != 0)) {
            localStringBuffer.append(str2);
          }
        }
        localReserveSummary.set("RES_SUMMARY_TXT", localStringBuffer.toString());
        paramStatement.setReserveSummary(localReserveSummary);
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeAll(p, localConnection, localPreparedStatement1);
    }
  }
  
  private static void jdField_for(Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getInterestSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(p, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM INTEREST_SUMMARY WHERE STMT_ID = ?");
      localPreparedStatement.setString(1, paramStatement.getID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM INTEREST_SUMMARY WHERE STMT_ID = ?");
      InterestSummary localInterestSummary = new InterestSummary(paramStatement.getLocale());
      if (localResultSet.next())
      {
        localInterestSummary.setAPYIntRate(localResultSet.getDouble("APY"));
        localInterestSummary.setBasedAvgColBal(localResultSet.getString("BASED_AVG_COLL_BAL"));
        localInterestSummary.setInterestEarned(a(paramStatement, localResultSet.getString("INTEREST_EARNED")));
      }
      paramStatement.setInterestSummary(localInterestSummary);
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(p, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static DateTime a(String paramString, Statement paramStatement)
  {
    DebugLog.log("StatementDataAdapter.convertDate Date : " + paramString);
    DateTime localDateTime = null;
    if (paramStatement.getStatementDateValue() != null) {
      localDateTime = (DateTime)paramStatement.getStatementDateValue().clone();
    } else {
      localDateTime = new DateTime();
    }
    localDateTime.set(1, Integer.valueOf(paramString.substring(0, 4)).intValue());
    localDateTime.set(2, Integer.valueOf(paramString.substring(5, 7)).intValue());
    localDateTime.set(5, Integer.valueOf(paramString.substring(8, 10)).intValue());
    return localDateTime;
  }
  
  private static Message a(Messages paramMessages, String paramString)
  {
    Iterator localIterator = paramMessages.iterator();
    while (localIterator.hasNext())
    {
      Message localMessage = (Message)localIterator.next();
      String str = (String)localMessage.get("MSG_TYPE");
      if ((str != null) && (str.equals(paramString))) {
        return localMessage;
      }
    }
    return null;
  }
  
  private static void a(Document paramDocument, Statement paramStatement, boolean paramBoolean, Locale paramLocale)
    throws IStatementException
  {
    DebugLog.log("StatementDataAdapter : createPDF called");
    String str1 = "StatementDataAdapter.createPDF";
    try
    {
      paramDocument.open();
      String str2 = ResourceUtil.getString("HEADER_FONT_SIZE", "com.ffusion.istatements.resources", paramLocale);
      o = Float.parseFloat(str2);
      String str3 = ResourceUtil.getString("BODY_FONT_SIZE", "com.ffusion.istatements.resources", paramLocale);
      jdField_void = Float.parseFloat(str3);
      jdField_case = ResourceUtil.getString("DOCUMENT_FONT", "com.ffusion.istatements.resources", paramLocale);
      String str4 = ResourceUtil.getString("FONT_ENCODING", "com.ffusion.istatements.resources", paramLocale);
      String str5 = ResourceUtil.getString("FONT_EMBEDDED", "com.ffusion.istatements.resources", paramLocale);
      BaseFont localBaseFont1 = BaseFont.createFont(jdField_case, str4, Boolean.getBoolean(str5));
      jdField_try = new Font(localBaseFont1, o, 1);
      BaseFont localBaseFont2 = BaseFont.createFont(jdField_case, str4, Boolean.getBoolean(str5));
      h = new Font(localBaseFont2, jdField_void, 1);
      BaseFont localBaseFont3 = BaseFont.createFont(jdField_case, str4, Boolean.getBoolean(str5));
      s = new Font(localBaseFont3, jdField_void);
      paramStatement.setLocale(paramLocale);
      a(paramDocument, paramStatement, paramLocale);
      jdField_char(paramDocument, paramStatement, paramLocale);
      jdField_try(paramDocument, paramStatement, paramLocale);
      jdField_case(paramDocument, paramStatement, paramLocale);
      jdField_int(paramDocument, paramStatement, paramLocale);
      ReserveSummary localReserveSummary = paramStatement.getReserveSummary();
      if ((localReserveSummary != null) && (!localReserveSummary.isEmpty())) {
        jdField_byte(paramDocument, paramStatement, paramLocale);
      }
      InterestBalanceSummary localInterestBalanceSummary = paramStatement.getInterestBalanceSummary();
      if ((localInterestBalanceSummary != null) && (!localInterestBalanceSummary.isEmpty()))
      {
        jdField_new(paramDocument, paramStatement, paramLocale);
        jdField_for(paramDocument, paramStatement, paramLocale);
      }
      MonthlyAccountSummary localMonthlyAccountSummary = paramStatement.getMonthlyAccountSummary();
      if ((localMonthlyAccountSummary != null) && (!localMonthlyAccountSummary.isEmpty())) {
        jdField_do(paramDocument, paramStatement, paramLocale);
      }
      DailyBalanceSummaries localDailyBalanceSummaries = paramStatement.getDailyBalanceSummaries();
      if ((localDailyBalanceSummaries != null) && (!localDailyBalanceSummaries.isEmpty())) {
        jdField_else(paramDocument, paramStatement, paramLocale);
      }
      jdField_if(paramDocument, paramStatement);
      a(paramDocument, paramStatement);
      if (paramBoolean) {
        jdField_if(paramDocument, paramStatement, paramLocale);
      }
      paramDocument.close();
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
  }
  
  private static void a(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str = "StatementDataAdapter.paragraphAddHeader";
    try
    {
      Paragraph localParagraph1 = new Paragraph();
      localParagraph1.setLeading(jdField_new);
      localParagraph1.add(new Chunk(paramStatement.get("NAME_ADDR1") + "\r" + paramStatement.get("NAME_ADDR2"), jdField_try));
      paramDocument.add(localParagraph1);
      Paragraph localParagraph2 = new Paragraph();
      localParagraph2.setLeading(jdField_new);
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = paramStatement.getAccountName();
      arrayOfObject[1] = paramStatement.getAccountNumber();
      arrayOfObject[2] = new LocalizableDate(paramStatement.getStatementStartDateValue(), false);
      arrayOfObject[3] = new LocalizableDate(paramStatement.getStatementEndDateValue(), false);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "ACCOUNT_INFO", arrayOfObject);
      localParagraph2.add(new Chunk((String)localLocalizableString.localize(paramLocale), jdField_try));
      paramDocument.add(localParagraph2);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36103, "Failed to add header in PDF");
    }
  }
  
  private static void jdField_char(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddInterestSummary";
    Object[] arrayOfObject = new Object[1];
    try
    {
      Paragraph localParagraph1 = new Paragraph();
      localParagraph1.setLeading(-2.0F);
      Table localTable = new Table(5, 1);
      localTable.setAlignment(0);
      localTable.setBorderColor(jdField_goto);
      localTable.setCellpadding(0.0F);
      Cell localCell1 = new Cell(new Chunk(ResourceUtil.getString("BEGINING_BALANCE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell1.setLeading(jdField_byte);
      localCell1.setBorderColor(jdField_goto);
      localTable.addCell(localCell1);
      String str2 = a(paramStatement.getBalanceSummary().getPreviousAmountValue(), paramLocale, false, true);
      Cell localCell2 = new Cell(new Chunk(str2, s));
      localCell2.setLeading(jdField_byte);
      localCell2.setBorderColor(jdField_goto);
      localCell2.setHorizontalAlignment(2);
      localTable.addCell(localCell2);
      Cell localCell3 = new Cell();
      localCell3.setLeading(jdField_byte);
      localCell3.setBorderColor(jdField_goto);
      localTable.addCell(localCell3);
      Cell localCell4 = new Cell();
      localCell4.setLeading(jdField_byte);
      localCell4.setBorderColor(jdField_goto);
      localTable.addCell(localCell4);
      Cell localCell5 = new Cell();
      localCell5.setLeading(jdField_byte);
      localCell5.setBorderColor(jdField_goto);
      localTable.addCell(localCell5);
      Cell localCell6 = new Cell(new Chunk(ResourceUtil.getString("DEPOSITS", "com.ffusion.istatements.resources", paramLocale), s));
      localCell6.setLeading(jdField_byte);
      localCell6.setBorderColor(jdField_goto);
      localTable.addCell(localCell6);
      str2 = a(paramStatement.getBalanceSummary().getTotalDepositsValue(), paramLocale, false, true);
      Cell localCell7 = new Cell(new Chunk("+  " + str2, s));
      localCell7.setLeading(jdField_byte);
      localCell7.setBorderColor(jdField_goto);
      localCell7.setHorizontalAlignment(2);
      localTable.addCell(localCell7);
      Cell localCell8 = new Cell();
      localCell8.setLeading(jdField_byte);
      localCell8.setBorderColor(jdField_goto);
      localTable.addCell(localCell8);
      Cell localCell9 = new Cell();
      localCell9.setLeading(jdField_byte);
      localCell9.setBorderColor(jdField_goto);
      localTable.addCell(localCell9);
      Cell localCell10 = new Cell();
      localCell10.setLeading(jdField_byte);
      localCell10.setBorderColor(jdField_goto);
      localTable.addCell(localCell10);
      Cell localCell11 = new Cell(new Chunk(ResourceUtil.getString("WITHDRAWALS", "com.ffusion.istatements.resources", paramLocale), s));
      localCell11.setLeading(jdField_byte);
      localCell11.setBorderColor(jdField_goto);
      localTable.addCell(localCell11);
      str2 = a(paramStatement.getBalanceSummary().getTotalWithdrawalsValue(), paramLocale, false, true);
      Cell localCell12 = new Cell(new Chunk("-  " + str2, s));
      localCell12.setLeading(jdField_byte);
      localCell12.setBorderColor(jdField_goto);
      localCell12.setHorizontalAlignment(2);
      localTable.addCell(localCell12);
      Cell localCell13 = new Cell();
      localCell13.setLeading(jdField_byte);
      localCell13.setBorderColor(jdField_goto);
      localTable.addCell(localCell13);
      Cell localCell14 = new Cell();
      localCell14.setLeading(jdField_byte);
      localCell14.setBorderColor(jdField_goto);
      localTable.addCell(localCell14);
      Cell localCell15 = new Cell();
      localCell15.setLeading(jdField_byte);
      localCell15.setBorderColor(jdField_goto);
      localTable.addCell(localCell15);
      Cell localCell16 = new Cell(new Chunk(ResourceUtil.getString("ENDING_BALANCE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell16.setLeading(jdField_byte);
      localCell16.setBorderColor(jdField_goto);
      localTable.addCell(localCell16);
      str2 = a(paramStatement.getBalanceSummary().getNewAmountValue(), paramLocale, false, true);
      Cell localCell17 = new Cell(new Chunk("=  " + str2, s));
      localCell17.setLeading(jdField_byte);
      localCell17.setBorderColor(jdField_goto);
      localCell17.setHorizontalAlignment(2);
      localTable.addCell(localCell17);
      Cell localCell18 = new Cell();
      localCell18.setLeading(jdField_byte);
      localCell18.setBorderColor(jdField_goto);
      localTable.addCell(localCell18);
      Cell localCell19 = new Cell();
      localCell19.setLeading(jdField_byte);
      localCell19.setBorderColor(jdField_goto);
      localTable.addCell(localCell19);
      Cell localCell20 = new Cell();
      localCell20.setLeading(jdField_byte);
      localCell20.setBorderColor(jdField_goto);
      localTable.addCell(localCell20);
      Cell localCell21 = new Cell(new Chunk(ResourceUtil.getString("INTEREST_EARNED", "com.ffusion.istatements.resources", paramLocale), s));
      localCell21.setLeading(jdField_byte);
      localCell21.setBorderColor(jdField_goto);
      localTable.addCell(localCell21);
      Cell localCell22 = null;
      if (paramStatement.getInterestSummary().getInterestEarned().equals(""))
      {
        localCell22 = new Cell(new Chunk("", s));
      }
      else
      {
        str2 = a(paramStatement.getInterestSummary().getInterestEarnedValue(), paramLocale, false, true);
        localCell22 = new Cell(new Chunk("  " + str2, s));
      }
      localCell22.setLeading(jdField_byte);
      localCell22.setBorderColor(jdField_goto);
      localCell22.setHorizontalAlignment(2);
      localTable.addCell(localCell22);
      Cell localCell23 = new Cell();
      localCell23.setLeading(jdField_byte);
      localCell23.setBorderColor(jdField_goto);
      localTable.addCell(localCell23);
      Cell localCell24 = new Cell();
      localCell24.setLeading(jdField_byte);
      localCell24.setBorderColor(jdField_goto);
      localTable.addCell(localCell24);
      Cell localCell25 = new Cell();
      localCell25.setLeading(jdField_byte);
      localCell25.setBorderColor(jdField_goto);
      localTable.addCell(localCell25);
      localParagraph1.add(localTable);
      paramDocument.add(localParagraph1);
      String str3 = paramStatement.getInterestSummary().getAPYIntRate();
      if ((str3 != null) && (!str3.equals("")) && (!str3.equals("0")) && (!str3.equals("0.0")))
      {
        Paragraph localParagraph2 = new Paragraph();
        localParagraph2.setLeading(jdField_byte);
        localParagraph2.add(new Chunk("\n"));
        arrayOfObject[0] = paramStatement.getInterestSummary().getAPYIntRate();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "ANNUAL_PERCENTAGE_EARNED", arrayOfObject);
        localParagraph2.add(new Chunk((String)localLocalizableString.localize(paramLocale), s));
        paramDocument.add(localParagraph2);
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36104, "Failed to add Interest Summary to PDF");
    }
  }
  
  private static void jdField_try(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str = "StatementDataAdapter.paragraphAddSweepMessages";
    try
    {
      Messages localMessages = paramStatement.getMessages();
      if ((localMessages != null) && (!localMessages.isEmpty()))
      {
        Iterator localIterator = localMessages.iterator();
        while (localIterator.hasNext())
        {
          Message localMessage = (Message)localIterator.next();
          if (localMessage.get("MSG_TYPE").equals("5"))
          {
            Paragraph localParagraph = new Paragraph();
            localParagraph.setLeading(jdField_byte);
            localParagraph.add(new Chunk("\n"));
            localParagraph.add(new Chunk(localMessage.getMemo() + "\n", s));
            paramDocument.add(localParagraph);
          }
        }
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36105, "Failed to add sweep messages to PDF");
    }
  }
  
  private static void jdField_case(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddDepositsSummary";
    Object[] arrayOfObject = new Object[2];
    try
    {
      Transactions localTransactions = paramStatement.getTransactions();
      Transaction localTransaction = null;
      if ((localTransactions != null) && (!localTransactions.isEmpty()))
      {
        localTransactions.setFilter("Type=Credit");
        Paragraph localParagraph = new Paragraph();
        localParagraph.setLeading(-3.0F);
        Table localTable1 = new Table(6, 1);
        localTable1.setAlignment(0);
        localTable1.setBorderColor(jdField_goto);
        localTable1.setCellpadding(0.0F);
        arrayOfObject[0] = new Integer(paramStatement.getBalanceSummary().getNumDeposits());
        arrayOfObject[1] = new LocalizableCurrency(paramStatement.getBalanceSummary().getTotalDepositsValue());
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "DEPOSITS_TOTALING", arrayOfObject);
        Cell localCell1 = new Cell(new Chunk((String)localLocalizableString.localize(paramLocale), jdField_try));
        localCell1.setLeading(jdField_byte);
        localCell1.setBorderColor(jdField_goto);
        localCell1.setColspan(6);
        localTable1.addCell(localCell1);
        Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("DATE", "com.ffusion.istatements.resources", paramLocale), h));
        localCell2.setLeading(jdField_byte);
        localCell2.setBorderColor(jdField_goto);
        localTable1.addCell(localCell2);
        Cell localCell3 = new Cell(new Chunk(ResourceUtil.getString("AMOUNT", "com.ffusion.istatements.resources", paramLocale), h));
        localCell3.setLeading(jdField_byte);
        localCell3.setBorderColor(jdField_goto);
        localCell3.setHorizontalAlignment(2);
        localTable1.addCell(localCell3);
        Cell localCell4 = new Cell();
        localCell4.setLeading(jdField_byte);
        localCell4.setBorderColor(jdField_goto);
        localTable1.addCell(localCell4);
        Cell localCell5 = new Cell(new Chunk(ResourceUtil.getString("DESCRIPTION", "com.ffusion.istatements.resources", paramLocale), h));
        localCell5.setLeading(jdField_byte);
        localCell5.setBorderColor(jdField_goto);
        localTable1.addCell(localCell5);
        localParagraph.add(localTable1);
        Cell localCell6 = new Cell();
        localCell6.setLeading(jdField_byte);
        localCell6.setBorderColor(jdField_goto);
        localCell6.setColspan(2);
        localTable1.addCell(localCell6);
        Iterator localIterator = null;
        if (localTransactions != null)
        {
          localIterator = localTransactions.iterator();
          while (localIterator.hasNext())
          {
            Table localTable2 = new Table(6, 1);
            localTable2.setAlignment(0);
            localTable2.setBorderColor(jdField_goto);
            localTransaction = (Transaction)localIterator.next();
            String str2;
            if (localTransaction.getDate() == null) {
              str2 = "";
            } else {
              str2 = a(localTransaction.getDateValue(), paramLocale, false);
            }
            Cell localCell7 = new Cell(new Chunk(str2, s));
            localCell7.setLeading(jdField_byte);
            localCell7.setBorderColor(jdField_goto);
            localTable2.addCell(localCell7);
            String str3;
            if (localTransaction.getAmountValue() == null) {
              str3 = "";
            } else {
              str3 = a(localTransaction.getAmountValue(), paramLocale, false, true);
            }
            Cell localCell8 = new Cell(new Chunk(str3, s));
            localCell8.setLeading(jdField_byte);
            localCell8.setBorderColor(jdField_goto);
            localCell8.setHorizontalAlignment(2);
            localTable2.addCell(localCell8);
            Cell localCell9 = new Cell();
            localCell9.setLeading(jdField_byte);
            localCell9.setBorderColor(jdField_goto);
            localTable2.addCell(localCell9);
            String str4 = localTransaction.getDescription();
            if (str4 == null) {
              str4 = "";
            }
            Cell localCell10 = new Cell(new Chunk(str4, s));
            localCell10.setLeading(jdField_byte);
            localCell10.setBorderColor(jdField_goto);
            localCell10.setColspan(3);
            localTable2.addCell(localCell10);
            localParagraph.add(localTable2);
          }
        }
        paramDocument.add(localParagraph);
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36106, "Failed to add Deposits Summary to PDF");
    }
  }
  
  private static void jdField_int(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddAccountTransactionsSummary";
    Object[] arrayOfObject = new Object[2];
    String str4 = null;
    try
    {
      Transactions localTransactions = paramStatement.getTransactions();
      Transaction localTransaction = null;
      if ((localTransactions != null) && (!localTransactions.isEmpty()))
      {
        localTransactions.setFilter("Type=Check,Type=Debit,Type=ATM");
        Paragraph localParagraph = new Paragraph();
        localParagraph.setLeading(-3.0F);
        Table localTable = new Table(6, 1);
        localTable.setAlignment(0);
        localTable.setBorderColor(jdField_goto);
        localTable.setCellpadding(0.0F);
        arrayOfObject[0] = new Integer(paramStatement.getBalanceSummary().getNumWithdrawals());
        arrayOfObject[1] = new LocalizableCurrency(paramStatement.getBalanceSummary().getTotalWithdrawalsValue());
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "ACCOUNT_TRANSACTION", arrayOfObject);
        Cell localCell1 = new Cell(new Chunk((String)localLocalizableString.localize(paramLocale), jdField_try));
        localCell1.setLeading(jdField_byte);
        localCell1.setBorderColor(jdField_goto);
        localCell1.setColspan(6);
        localTable.addCell(localCell1);
        Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("CHECKS", "com.ffusion.istatements.resources", paramLocale), h));
        localCell2.setLeading(jdField_byte);
        localCell2.setBorderColor(jdField_goto);
        localCell2.setColspan(6);
        localTable.addCell(localCell2);
        Cell localCell3 = new Cell(new Chunk(ResourceUtil.getString("DATE", "com.ffusion.istatements.resources", paramLocale), h));
        localCell3.setLeading(jdField_byte);
        localCell3.setBorderColor(jdField_goto);
        localTable.addCell(localCell3);
        Cell localCell4 = new Cell(new Chunk(ResourceUtil.getString("AMOUNT", "com.ffusion.istatements.resources", paramLocale), h));
        localCell4.setLeading(jdField_byte);
        localCell4.setBorderColor(jdField_goto);
        localCell4.setHorizontalAlignment(2);
        localTable.addCell(localCell4);
        Cell localCell5 = new Cell();
        localCell5.setLeading(jdField_byte);
        localCell5.setBorderColor(jdField_goto);
        localTable.addCell(localCell5);
        Cell localCell6 = new Cell(new Chunk(ResourceUtil.getString("DESCRIPTION", "com.ffusion.istatements.resources", paramLocale), h));
        localCell6.setLeading(jdField_byte);
        localCell6.setBorderColor(jdField_goto);
        localTable.addCell(localCell6);
        Cell localCell7 = new Cell();
        localCell7.setLeading(jdField_byte);
        localCell7.setBorderColor(jdField_goto);
        localCell7.setColspan(2);
        localTable.addCell(localCell7);
        localParagraph.add(localTable);
        Iterator localIterator = null;
        String str3;
        Object localObject2;
        String str2;
        Cell localCell8;
        Cell localCell9;
        Object localObject3;
        Cell localCell10;
        if (localTransactions != null)
        {
          localTransactions.setFilter("Type=Check");
          localIterator = localTransactions.iterator();
          while (localIterator.hasNext())
          {
            localObject1 = new Table(6, 1);
            ((Table)localObject1).setAlignment(0);
            ((Table)localObject1).setBorderColor(jdField_goto);
            localTransaction = (Transaction)localIterator.next();
            if (localTransaction.getDate() == null) {
              str3 = "";
            } else {
              str3 = a(localTransaction.getDateValue(), paramLocale, false);
            }
            localObject2 = new Cell(new Chunk(str3, s));
            ((Cell)localObject2).setLeading(jdField_byte);
            ((Cell)localObject2).setBorderColor(jdField_goto);
            ((Table)localObject1).addCell((Cell)localObject2);
            if (localTransaction.getAmountValue().getCurrencyStringNoSymbol() == null) {
              str2 = "";
            } else {
              str2 = a(localTransaction.getAmountValue(), paramLocale, false, true);
            }
            localCell8 = new Cell(new Chunk(str2, s));
            localCell8.setLeading(jdField_byte);
            localCell8.setBorderColor(jdField_goto);
            localCell8.setHorizontalAlignment(2);
            ((Table)localObject1).addCell(localCell8);
            localCell9 = new Cell();
            localCell9.setLeading(jdField_byte);
            localCell9.setBorderColor(jdField_goto);
            ((Table)localObject1).addCell(localCell9);
            localObject3 = localTransaction.getDescription();
            if (localObject3 == null) {
              localObject3 = "";
            }
            localCell10 = new Cell(new Chunk((String)localObject3, s));
            localCell10.setLeading(jdField_byte);
            localCell10.setBorderColor(jdField_goto);
            localCell10.setColspan(3);
            ((Table)localObject1).addCell(localCell10);
            localParagraph.add(localObject1);
          }
        }
        localTable = new Table(6, 1);
        localTable.setAlignment(0);
        localTable.setBorderColor(jdField_goto);
        localTable.setCellpadding(0.0F);
        Object localObject1 = new Cell(new Chunk(ResourceUtil.getString("OTHER_WITHDRAWALS", "com.ffusion.istatements.resources", paramLocale), h));
        ((Cell)localObject1).setLeading(jdField_byte);
        ((Cell)localObject1).setBorderColor(jdField_goto);
        ((Cell)localObject1).setColspan(6);
        localTable.addCell((Cell)localObject1);
        localTable.addCell(localCell3);
        localTable.addCell(localCell4);
        localTable.addCell(localCell5);
        localTable.addCell(localCell6);
        localTable.addCell(localCell7);
        localParagraph.add(localTable);
        localIterator = null;
        if (localTransactions != null)
        {
          localTransactions.setFilter("Type=Debit,Type=ATM");
          localIterator = localTransactions.iterator();
          while (localIterator.hasNext())
          {
            localObject2 = new Table(6, 1);
            ((Table)localObject2).setAlignment(0);
            ((Table)localObject2).setBorderColor(jdField_goto);
            localTransaction = (Transaction)localIterator.next();
            if (localTransaction.getDate() == null) {
              str3 = "";
            } else {
              str3 = a(localTransaction.getDateValue(), paramLocale, false);
            }
            localCell8 = new Cell(new Chunk(str3, s));
            localCell8.setLeading(jdField_byte);
            localCell8.setBorderColor(jdField_goto);
            ((Table)localObject2).addCell(localCell8);
            str2 = a(localTransaction.getAmountValue(), paramLocale, false, true);
            localCell9 = new Cell(new Chunk(str2, s));
            localCell9.setLeading(jdField_byte);
            localCell9.setBorderColor(jdField_goto);
            localCell9.setHorizontalAlignment(2);
            ((Table)localObject2).addCell(localCell9);
            localObject3 = new Cell();
            ((Cell)localObject3).setLeading(jdField_byte);
            ((Cell)localObject3).setBorderColor(jdField_goto);
            ((Table)localObject2).addCell((Cell)localObject3);
            str4 = localTransaction.getDescription();
            localCell10 = new Cell(new Chunk(str4 == null ? "" : str4, s));
            localCell10.setLeading(jdField_byte);
            localCell10.setBorderColor(jdField_goto);
            localCell10.setColspan(3);
            ((Table)localObject2).addCell(localCell10);
            localParagraph.add(localObject2);
          }
        }
        paramDocument.add(localParagraph);
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36107, "Failed to Account Transaction Summary to PDF");
    }
  }
  
  private static void jdField_byte(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddReserveSummary";
    try
    {
      Paragraph localParagraph = new Paragraph();
      localParagraph.setLeading(-2.0F);
      Table localTable = new Table(5, 10);
      localTable.setAlignment(0);
      localTable.setBorderColor(jdField_goto);
      localTable.setCellpadding(0.0F);
      Cell localCell1 = new Cell(new Chunk(ResourceUtil.getString("RESERVE_SUMMARY", "com.ffusion.istatements.resources", paramLocale), jdField_try));
      localCell1.setLeading(jdField_byte);
      localCell1.setBorderColor(jdField_goto);
      localCell1.setColspan(5);
      localTable.addCell(localCell1);
      Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("PREVIOUS_RESERVE_IN_USE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell2.setLeading(jdField_byte);
      localCell2.setBorderColor(jdField_goto);
      localCell2.setColspan(3);
      localTable.addCell(localCell2);
      String str2 = a(paramStatement.getReserveSummary().getPreviousReserveAmountValue(), paramLocale, false, true);
      Cell localCell3 = new Cell(new Chunk(str2, s));
      localCell3.setLeading(jdField_byte);
      localCell3.setBorderColor(jdField_goto);
      localCell3.setHorizontalAlignment(2);
      localTable.addCell(localCell3);
      Cell localCell4 = new Cell();
      localCell4.setLeading(jdField_byte);
      localCell4.setBorderColor(jdField_goto);
      localTable.addCell(localCell4);
      Cell localCell5 = new Cell(new Chunk(ResourceUtil.getString("PERIODIC_INTEREST_RATE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell5.setLeading(jdField_byte);
      localCell5.setBorderColor(jdField_goto);
      localCell5.setColspan(3);
      localTable.addCell(localCell5);
      Cell localCell6 = new Cell(new Chunk(paramStatement.getReserveSummary().getPeriodIntRate() + " %", s));
      localCell6.setLeading(jdField_byte);
      localCell6.setBorderColor(jdField_goto);
      localCell6.setHorizontalAlignment(2);
      localTable.addCell(localCell6);
      Cell localCell7 = new Cell();
      localCell7.setLeading(jdField_byte);
      localCell7.setBorderColor(jdField_goto);
      localTable.addCell(localCell7);
      Cell localCell8 = new Cell(new Chunk(ResourceUtil.getString("PAYEMENT_ON_RESERVE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell8.setLeading(jdField_byte);
      localCell8.setBorderColor(jdField_goto);
      localCell8.setColspan(3);
      localTable.addCell(localCell8);
      str2 = a(paramStatement.getReserveSummary().getPmtsOnReserveAmountValue(), paramLocale, false, true);
      Cell localCell9 = new Cell(new Chunk(str2, s));
      localCell9.setLeading(jdField_byte);
      localCell9.setBorderColor(jdField_goto);
      localCell9.setHorizontalAlignment(2);
      localTable.addCell(localCell9);
      Cell localCell10 = new Cell();
      localCell10.setLeading(jdField_byte);
      localCell10.setBorderColor(jdField_goto);
      localTable.addCell(localCell10);
      Cell localCell11 = new Cell(new Chunk(ResourceUtil.getString("ANNUAL_PERCENTAGE_RATE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell11.setLeading(jdField_byte);
      localCell11.setBorderColor(jdField_goto);
      localCell11.setColspan(3);
      localTable.addCell(localCell11);
      Cell localCell12 = new Cell(new Chunk(paramStatement.getReserveSummary().getAnnualIntRate() + " %", s));
      localCell12.setLeading(jdField_byte);
      localCell12.setBorderColor(jdField_goto);
      localCell12.setHorizontalAlignment(2);
      localTable.addCell(localCell12);
      Cell localCell13 = new Cell();
      localCell13.setLeading(jdField_byte);
      localCell13.setBorderColor(jdField_goto);
      localTable.addCell(localCell13);
      Cell localCell14 = new Cell(new Chunk(ResourceUtil.getString("RES_TRANSACTIONS", "com.ffusion.istatements.resources", paramLocale), s));
      localCell14.setLeading(jdField_byte);
      localCell14.setBorderColor(jdField_goto);
      localCell14.setColspan(3);
      localTable.addCell(localCell14);
      str2 = a(paramStatement.getReserveSummary().getReserveTransAmountValue(), paramLocale, false, true);
      Cell localCell15 = new Cell(new Chunk(str2, s));
      localCell15.setLeading(jdField_byte);
      localCell15.setBorderColor(jdField_goto);
      localCell15.setHorizontalAlignment(2);
      localTable.addCell(localCell15);
      Cell localCell16 = new Cell();
      localCell16.setLeading(jdField_byte);
      localCell16.setBorderColor(jdField_goto);
      localTable.addCell(localCell16);
      Cell localCell17 = new Cell(new Chunk(ResourceUtil.getString("APPROVED_RES", "com.ffusion.istatements.resources", paramLocale), s));
      localCell17.setLeading(jdField_byte);
      localCell17.setBorderColor(jdField_goto);
      localCell17.setColspan(3);
      localTable.addCell(localCell17);
      str2 = a(paramStatement.getReserveSummary().getApprovedReserveAmountValue(), paramLocale, false, true);
      Cell localCell18 = new Cell(new Chunk(str2, s));
      localCell18.setLeading(jdField_byte);
      localCell18.setBorderColor(jdField_goto);
      localCell18.setHorizontalAlignment(2);
      localTable.addCell(localCell18);
      Cell localCell19 = new Cell();
      localCell19.setLeading(jdField_byte);
      localCell19.setBorderColor(jdField_goto);
      localTable.addCell(localCell19);
      Cell localCell20 = new Cell(new Chunk(ResourceUtil.getString("FINANCE_CHARGE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell20.setLeading(jdField_byte);
      localCell20.setBorderColor(jdField_goto);
      localCell20.setColspan(3);
      localTable.addCell(localCell20);
      str2 = a(paramStatement.getReserveSummary().getFinanceChargeAmountValue(), paramLocale, false, true);
      Cell localCell21 = new Cell(new Chunk(str2, s));
      localCell21.setLeading(jdField_byte);
      localCell21.setBorderColor(jdField_goto);
      localCell21.setHorizontalAlignment(2);
      localTable.addCell(localCell21);
      Cell localCell22 = new Cell();
      localCell22.setLeading(jdField_byte);
      localCell22.setBorderColor(jdField_goto);
      localTable.addCell(localCell22);
      Cell localCell23 = new Cell(new Chunk(ResourceUtil.getString("AVAILABLE_RES", "com.ffusion.istatements.resources", paramLocale), s));
      localCell23.setLeading(jdField_byte);
      localCell23.setBorderColor(jdField_goto);
      localCell23.setColspan(3);
      localTable.addCell(localCell23);
      str2 = a(paramStatement.getReserveSummary().getAvailableReserveAmountValue(), paramLocale, false, true);
      Cell localCell24 = new Cell(new Chunk(str2, s));
      localCell24.setLeading(jdField_byte);
      localCell24.setBorderColor(jdField_goto);
      localCell24.setHorizontalAlignment(2);
      localTable.addCell(localCell24);
      Cell localCell25 = new Cell();
      localCell25.setLeading(jdField_byte);
      localCell25.setBorderColor(jdField_goto);
      localTable.addCell(localCell25);
      Cell localCell26 = new Cell(new Chunk(ResourceUtil.getString("NEW_RESERVE_IN_USE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell26.setLeading(jdField_byte);
      localCell26.setBorderColor(jdField_goto);
      localCell26.setColspan(3);
      localTable.addCell(localCell26);
      str2 = a(paramStatement.getReserveSummary().getNewReserveAmountValue(), paramLocale, false, true);
      Cell localCell27 = new Cell(new Chunk(str2, s));
      localCell27.setLeading(jdField_byte);
      localCell27.setBorderColor(jdField_goto);
      localCell27.setHorizontalAlignment(2);
      localTable.addCell(localCell27);
      Cell localCell28 = new Cell();
      localCell28.setLeading(jdField_byte);
      localCell28.setBorderColor(jdField_goto);
      localTable.addCell(localCell28);
      Cell localCell29 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_DAILY_RESERVE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell29.setLeading(jdField_byte);
      localCell29.setBorderColor(jdField_goto);
      localCell29.setColspan(3);
      localTable.addCell(localCell29);
      str2 = a(paramStatement.getReserveSummary().getResInUseFinanceChargeAmountValue(), paramLocale, false, true);
      Cell localCell30 = new Cell(new Chunk(str2, s));
      localCell30.setLeading(jdField_byte);
      localCell30.setBorderColor(jdField_goto);
      localCell30.setHorizontalAlignment(2);
      localTable.addCell(localCell30);
      Cell localCell31 = new Cell();
      localCell31.setLeading(jdField_byte);
      localCell31.setBorderColor(jdField_goto);
      localTable.addCell(localCell31);
      localParagraph.add(localTable);
      paramDocument.add(localParagraph);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36108, "Failed to add Reserve Summary to PDF");
    }
  }
  
  private static void jdField_new(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str = "StatementDataAdapter.paragraphAddInterestBalanceSummary";
    try
    {
      Paragraph localParagraph = new Paragraph();
      localParagraph.setLeading(-2.0F);
      Table localTable = new Table(5, 9);
      localTable.setAlignment(0);
      localTable.setBorderColor(jdField_goto);
      localTable.setCellpadding(0.0F);
      Cell localCell1 = new Cell(new Chunk("\n" + ResourceUtil.getString("INTEREST_BALANCE_SUMMARY", "com.ffusion.istatements.resources", paramLocale) + ResourceUtil.getString("REQUIREMENT", "com.ffusion.istatements.resources", paramLocale) + paramStatement.getInterestBalanceSummary().getReserveReqAmount(), jdField_try));
      localCell1.setLeading(jdField_byte);
      localCell1.setBorderColor(jdField_goto);
      localCell1.setColspan(5);
      localTable.addCell(localCell1);
      Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("INTEREST_CALCULATION", "com.ffusion.istatements.resources", paramLocale), jdField_try));
      localCell2.setLeading(jdField_byte);
      localCell2.setBorderColor(jdField_goto);
      localCell2.setColspan(5);
      localTable.addCell(localCell2);
      Cell localCell3 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_LEDGER_BALANCE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell3.setLeading(jdField_byte);
      localCell3.setBorderColor(jdField_goto);
      localCell3.setColspan(3);
      localTable.addCell(localCell3);
      Cell localCell4 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgLedgerBalanceAmountValue().getCurrencyStringNoSymbol(), s));
      localCell4.setLeading(jdField_byte);
      localCell4.setBorderColor(jdField_goto);
      localCell4.setHorizontalAlignment(2);
      localTable.addCell(localCell4);
      Cell localCell5 = new Cell();
      localCell5.setLeading(jdField_byte);
      localCell5.setBorderColor(jdField_goto);
      localTable.addCell(localCell5);
      Cell localCell6 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_FLOAT", "com.ffusion.istatements.resources", paramLocale), s));
      localCell6.setLeading(jdField_byte);
      localCell6.setBorderColor(jdField_goto);
      localCell6.setColspan(3);
      localTable.addCell(localCell6);
      Cell localCell7 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgFloatAmountValue().getCurrencyStringNoSymbol(), s));
      localCell7.setLeading(jdField_byte);
      localCell7.setBorderColor(jdField_goto);
      localCell7.setHorizontalAlignment(2);
      localTable.addCell(localCell7);
      Cell localCell8 = new Cell();
      localCell8.setLeading(jdField_byte);
      localCell8.setBorderColor(jdField_goto);
      localTable.addCell(localCell8);
      if (!paramStatement.getInterestBalanceSummary().getAvgColAmount().equals(""))
      {
        localCell9 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_COLLECTED_BALANCE", "com.ffusion.istatements.resources", paramLocale), s));
        localCell9.setLeading(jdField_byte);
        localCell9.setBorderColor(jdField_goto);
        localCell9.setColspan(3);
        localTable.addCell(localCell9);
        localCell10 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgColAmountValue().getCurrencyStringNoSymbol(), s));
        localCell10.setLeading(jdField_byte);
        localCell10.setBorderColor(jdField_goto);
        localCell10.setHorizontalAlignment(2);
        localTable.addCell(localCell10);
        localCell11 = new Cell();
        localCell11.setLeading(jdField_byte);
        localCell11.setBorderColor(jdField_goto);
        localTable.addCell(localCell11);
      }
      Cell localCell9 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_RESERVE_REQUIREMENT", "com.ffusion.istatements.resources", paramLocale), s));
      localCell9.setLeading(jdField_byte);
      localCell9.setBorderColor(jdField_goto);
      localCell9.setColspan(3);
      localTable.addCell(localCell9);
      Cell localCell10 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgReserveReqAmountValue().getCurrencyStringNoSymbol(), s));
      localCell10.setLeading(jdField_byte);
      localCell10.setBorderColor(jdField_goto);
      localCell10.setHorizontalAlignment(2);
      localTable.addCell(localCell10);
      Cell localCell11 = new Cell();
      localCell11.setLeading(jdField_byte);
      localCell11.setBorderColor(jdField_goto);
      localTable.addCell(localCell11);
      Cell localCell12 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_BALANCE_QUALIFYING", "com.ffusion.istatements.resources", paramLocale), s));
      localCell12.setLeading(jdField_byte);
      localCell12.setBorderColor(jdField_goto);
      localCell12.setColspan(3);
      localTable.addCell(localCell12);
      Cell localCell13 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgBalQualIntAmountValue().getCurrencyStringNoSymbol(), s));
      localCell13.setLeading(jdField_byte);
      localCell13.setBorderColor(jdField_goto);
      localCell13.setHorizontalAlignment(2);
      localTable.addCell(localCell13);
      Cell localCell14 = new Cell();
      localCell14.setLeading(jdField_byte);
      localCell14.setBorderColor(jdField_goto);
      localTable.addCell(localCell14);
      Cell localCell15 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_INTEREST_RATE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell15.setLeading(jdField_byte);
      localCell15.setBorderColor(jdField_goto);
      localCell15.setColspan(3);
      localTable.addCell(localCell15);
      Cell localCell16 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgIntRate() + " %", s));
      localCell16.setLeading(jdField_byte);
      localCell16.setBorderColor(jdField_goto);
      localCell16.setHorizontalAlignment(2);
      localTable.addCell(localCell16);
      Cell localCell17 = new Cell();
      localCell17.setLeading(jdField_byte);
      localCell17.setBorderColor(jdField_goto);
      localTable.addCell(localCell17);
      Cell localCell18 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_DAILY_ACCRUAL", "com.ffusion.istatements.resources", paramLocale), s));
      localCell18.setLeading(jdField_byte);
      localCell18.setBorderColor(jdField_goto);
      localCell18.setColspan(3);
      localTable.addCell(localCell18);
      Cell localCell19 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgDailyAccrAmountValue().getCurrencyStringNoSymbol(), s));
      localCell19.setLeading(jdField_byte);
      localCell19.setBorderColor(jdField_goto);
      localCell19.setHorizontalAlignment(2);
      localTable.addCell(localCell19);
      Cell localCell20 = new Cell();
      localCell20.setLeading(jdField_byte);
      localCell20.setBorderColor(jdField_goto);
      localTable.addCell(localCell20);
      Cell localCell21 = new Cell(new Chunk(ResourceUtil.getString("NUMBER_OF_DAYS_QUALIFYING", "com.ffusion.istatements.resources", paramLocale), s));
      localCell21.setLeading(jdField_byte);
      localCell21.setBorderColor(jdField_goto);
      localCell21.setColspan(3);
      localTable.addCell(localCell21);
      Cell localCell22 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getNumDaysQual(), s));
      localCell22.setLeading(jdField_byte);
      localCell22.setBorderColor(jdField_goto);
      localCell22.setHorizontalAlignment(2);
      localTable.addCell(localCell22);
      Cell localCell23 = new Cell();
      localCell23.setLeading(jdField_byte);
      localCell23.setBorderColor(jdField_goto);
      localTable.addCell(localCell23);
      Cell localCell24 = new Cell(new Chunk(ResourceUtil.getString("TOTAL_INTEREST_TO_BE_CREDITED", "com.ffusion.istatements.resources", paramLocale), s));
      localCell24.setLeading(jdField_byte);
      localCell24.setBorderColor(jdField_goto);
      localCell24.setColspan(3);
      localTable.addCell(localCell24);
      Cell localCell25 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getTotalIntCreditAmountValue().getCurrencyStringNoSymbol(), s));
      localCell25.setLeading(jdField_byte);
      localCell25.setBorderColor(jdField_goto);
      localCell25.setHorizontalAlignment(2);
      localTable.addCell(localCell25);
      Cell localCell26 = new Cell();
      localCell26.setLeading(jdField_byte);
      localCell26.setBorderColor(jdField_goto);
      localTable.addCell(localCell26);
      localParagraph.add(localTable);
      paramDocument.add(localParagraph);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36119, "Failed to add Interest Balance Summary to PDF");
    }
  }
  
  private static void jdField_for(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddOverdraftChargeCalculation";
    try
    {
      Paragraph localParagraph = new Paragraph();
      localParagraph.setLeading(-2.0F);
      Table localTable = new Table(5, 6);
      localTable.setAlignment(0);
      localTable.setBorderColor(new CMYKColor(0, 0, 0, 0));
      localTable.setCellpadding(0.0F);
      Cell localCell1 = new Cell(new Chunk(ResourceUtil.getString("OVERDRAFT_CHARGE_CALCUTALTION", "com.ffusion.istatements.resources", paramLocale), jdField_try));
      localCell1.setLeading(jdField_byte);
      localCell1.setBorderColor(jdField_goto);
      localCell1.setColspan(5);
      localTable.addCell(localCell1);
      Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("NUMBER_OF_DAYS_OVERDRAWN", "com.ffusion.istatements.resources", paramLocale), s));
      localCell2.setLeading(jdField_byte);
      localCell2.setBorderColor(jdField_goto);
      localCell2.setColspan(3);
      localTable.addCell(localCell2);
      Cell localCell3 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getNumDaysOver(), s));
      localCell3.setLeading(jdField_byte);
      localCell3.setBorderColor(jdField_goto);
      localCell3.setHorizontalAlignment(2);
      localTable.addCell(localCell3);
      Cell localCell4 = new Cell();
      localCell4.setLeading(jdField_byte);
      localCell4.setBorderColor(jdField_goto);
      localTable.addCell(localCell4);
      Cell localCell5 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_OVERDRAFT", "com.ffusion.istatements.resources", paramLocale), s));
      localCell5.setLeading(jdField_byte);
      localCell5.setBorderColor(jdField_goto);
      localCell5.setColspan(3);
      localTable.addCell(localCell5);
      String str2 = a(paramStatement.getInterestBalanceSummary().getAvgDailyOverAmountValue(), paramLocale, false, true);
      Cell localCell6 = new Cell(new Chunk(str2, s));
      localCell6.setLeading(jdField_byte);
      localCell6.setBorderColor(jdField_goto);
      localCell6.setHorizontalAlignment(2);
      localTable.addCell(localCell6);
      Cell localCell7 = new Cell();
      localCell7.setLeading(jdField_byte);
      localCell7.setBorderColor(jdField_goto);
      localTable.addCell(localCell7);
      Cell localCell8 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_OVERDRAFT_RATE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell8.setLeading(jdField_byte);
      localCell8.setBorderColor(jdField_goto);
      localCell8.setColspan(3);
      localTable.addCell(localCell8);
      Cell localCell9 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getAvgOverRate() + " %", s));
      localCell9.setLeading(jdField_byte);
      localCell9.setBorderColor(jdField_goto);
      localCell9.setHorizontalAlignment(2);
      localTable.addCell(localCell9);
      Cell localCell10 = new Cell();
      localCell10.setLeading(jdField_byte);
      localCell10.setBorderColor(jdField_goto);
      localTable.addCell(localCell10);
      Cell localCell11 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_DAILY_OVERDRAFT_CHARGE", "com.ffusion.istatements.resources", paramLocale), s));
      localCell11.setLeading(jdField_byte);
      localCell11.setBorderColor(jdField_goto);
      localCell11.setColspan(3);
      localTable.addCell(localCell11);
      str2 = a(paramStatement.getInterestBalanceSummary().getAvgDailyOverAmountValue(), paramLocale, false, true);
      Cell localCell12 = new Cell(new Chunk(str2, s));
      localCell12.setLeading(jdField_byte);
      localCell12.setBorderColor(jdField_goto);
      localCell12.setHorizontalAlignment(2);
      localTable.addCell(localCell12);
      Cell localCell13 = new Cell();
      localCell13.setLeading(jdField_byte);
      localCell13.setBorderColor(jdField_goto);
      localTable.addCell(localCell13);
      Cell localCell14 = new Cell(new Chunk(ResourceUtil.getString("NUMBER_OF_DAYS_CHARGED", "com.ffusion.istatements.resources", paramLocale), s));
      localCell14.setLeading(jdField_byte);
      localCell14.setBorderColor(jdField_goto);
      localCell14.setColspan(3);
      localTable.addCell(localCell14);
      Cell localCell15 = new Cell(new Chunk(paramStatement.getInterestBalanceSummary().getNumDaysCharged(), s));
      localCell15.setLeading(jdField_byte);
      localCell15.setBorderColor(jdField_goto);
      localCell15.setHorizontalAlignment(2);
      localTable.addCell(localCell15);
      Cell localCell16 = new Cell();
      localCell16.setLeading(jdField_byte);
      localCell16.setBorderColor(jdField_goto);
      localTable.addCell(localCell16);
      Cell localCell17 = new Cell(new Chunk(ResourceUtil.getString("TOTAL_OVERDRAFT_TO_BE_CHARGED", "com.ffusion.istatements.resources", paramLocale), s));
      localCell17.setLeading(jdField_byte);
      localCell17.setBorderColor(jdField_goto);
      localCell17.setColspan(3);
      localTable.addCell(localCell17);
      str2 = a(paramStatement.getInterestBalanceSummary().getTotalOverChargedAmountValue(), paramLocale, false, true);
      Cell localCell18 = new Cell(new Chunk(str2, s));
      localCell18.setLeading(jdField_byte);
      localCell18.setBorderColor(jdField_goto);
      localCell18.setHorizontalAlignment(2);
      localTable.addCell(localCell18);
      Cell localCell19 = new Cell();
      localCell19.setLeading(jdField_byte);
      localCell19.setBorderColor(jdField_goto);
      localTable.addCell(localCell19);
      localParagraph.add(localTable);
      paramDocument.add(localParagraph);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36109, "Failed to add Overdraft Charge Calculation Information to PDF");
    }
  }
  
  private static void jdField_do(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddMonthlyAccountSummary";
    Object[] arrayOfObject = new Object[3];
    try
    {
      Paragraph localParagraph = new Paragraph();
      localParagraph.setLeading(-2.0F);
      Table localTable = new Table(5, 8);
      localTable.setAlignment(0);
      localTable.setBorderColor(jdField_goto);
      localTable.setCellpadding(0.0F);
      Cell localCell1 = new Cell(new Chunk(ResourceUtil.getString("CHECKING_ACCOUNT_SUMMARY", "com.ffusion.istatements.resources", paramLocale), jdField_try));
      localCell1.setLeading(jdField_byte);
      localCell1.setBorderColor(jdField_goto);
      localCell1.setColspan(5);
      localTable.addCell(localCell1);
      if (!paramStatement.getMonthlyAccountSummary().getAvgColBal().equals(""))
      {
        localCell2 = new Cell(new Chunk(ResourceUtil.getString("AVERAGE_COLLECTED_BALANCE", "com.ffusion.istatements.resources", paramLocale), s));
        localCell2.setLeading(jdField_byte);
        localCell2.setBorderColor(jdField_goto);
        localCell2.setColspan(3);
        localTable.addCell(localCell2);
        str2 = a(paramStatement.getMonthlyAccountSummary().getAvgColBalValue(), paramLocale, false, true);
        localCell3 = new Cell(new Chunk(str2, s));
        localCell3.setLeading(jdField_byte);
        localCell3.setBorderColor(jdField_goto);
        localCell3.setHorizontalAlignment(2);
        localTable.addCell(localCell3);
        localCell4 = new Cell();
        localCell4.setLeading(jdField_byte);
        localCell4.setBorderColor(jdField_goto);
        localTable.addCell(localCell4);
      }
      Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("DEPOSITS", "com.ffusion.istatements.resources", paramLocale), s));
      localCell2.setLeading(jdField_byte);
      localCell2.setBorderColor(jdField_goto);
      localCell2.setColspan(3);
      localTable.addCell(localCell2);
      Cell localCell3 = new Cell(new Chunk(paramStatement.getMonthlyAccountSummary().getNumDeposits(), s));
      localCell3.setLeading(jdField_byte);
      localCell3.setBorderColor(jdField_goto);
      localCell3.setHorizontalAlignment(2);
      localTable.addCell(localCell3);
      Cell localCell4 = new Cell();
      localCell4.setLeading(jdField_byte);
      localCell4.setBorderColor(jdField_goto);
      localTable.addCell(localCell4);
      Cell localCell5 = new Cell(new Chunk(ResourceUtil.getString("ITEMS_DEPOSITED", "com.ffusion.istatements.resources", paramLocale), s));
      localCell5.setLeading(10.0F);
      localCell5.setBorderColor(jdField_goto);
      localCell5.setColspan(3);
      localTable.addCell(localCell5);
      Cell localCell6 = new Cell(new Chunk(paramStatement.getMonthlyAccountSummary().getItemsDeposited(), s));
      localCell6.setLeading(jdField_byte);
      localCell6.setBorderColor(jdField_goto);
      localCell6.setHorizontalAlignment(2);
      localTable.addCell(localCell6);
      Cell localCell7 = new Cell();
      localCell7.setLeading(jdField_byte);
      localCell7.setBorderColor(jdField_goto);
      localTable.addCell(localCell7);
      Cell localCell8 = new Cell(new Chunk(ResourceUtil.getString("WITHDRAWALS", "com.ffusion.istatements.resources", paramLocale), s));
      localCell8.setLeading(jdField_byte);
      localCell8.setBorderColor(jdField_goto);
      localCell8.setColspan(3);
      localTable.addCell(localCell8);
      Cell localCell9 = new Cell(new Chunk(paramStatement.getMonthlyAccountSummary().getNumWithdrawals(), s));
      localCell9.setLeading(jdField_byte);
      localCell9.setBorderColor(jdField_goto);
      localCell9.setHorizontalAlignment(2);
      localTable.addCell(localCell9);
      Cell localCell10 = new Cell();
      localCell10.setLeading(jdField_byte);
      localCell10.setBorderColor(jdField_goto);
      localTable.addCell(localCell10);
      Cell localCell11 = new Cell(new Chunk(ResourceUtil.getString("CASH_DEPOSITED", "com.ffusion.istatements.resources", paramLocale), s));
      localCell11.setLeading(jdField_byte);
      localCell11.setBorderColor(jdField_goto);
      localCell11.setColspan(3);
      localTable.addCell(localCell11);
      Cell localCell12 = new Cell(new Chunk(paramStatement.getMonthlyAccountSummary().getCashItemsDeposited(), s));
      localCell12.setLeading(jdField_byte);
      localCell12.setBorderColor(jdField_goto);
      localCell12.setHorizontalAlignment(2);
      localTable.addCell(localCell12);
      Cell localCell13 = new Cell();
      localCell13.setLeading(jdField_byte);
      localCell13.setBorderColor(jdField_goto);
      localTable.addCell(localCell13);
      Cell localCell14 = new Cell(new Chunk(ResourceUtil.getString("TRANSACTIONS_COUNTED_TOWARD_LIMIT", "com.ffusion.istatements.resources", paramLocale), s));
      localCell14.setLeading(jdField_byte);
      localCell14.setBorderColor(jdField_goto);
      localCell14.setColspan(3);
      localTable.addCell(localCell14);
      Cell localCell15 = new Cell(new Chunk(paramStatement.getMonthlyAccountSummary().getTransTowardLimit(), s));
      localCell15.setLeading(jdField_byte);
      localCell15.setBorderColor(jdField_goto);
      localCell15.setHorizontalAlignment(2);
      localTable.addCell(localCell15);
      Cell localCell16 = new Cell();
      localCell16.setLeading(jdField_byte);
      localCell16.setBorderColor(jdField_goto);
      localTable.addCell(localCell16);
      Cell localCell17 = new Cell(new Chunk(ResourceUtil.getString("TRANSACTIONS_NOT_COUNTED_TOWARD_LIMIT", "com.ffusion.istatements.resources", paramLocale), s));
      localCell17.setLeading(jdField_byte);
      localCell17.setBorderColor(jdField_goto);
      localCell17.setColspan(3);
      localTable.addCell(localCell17);
      Cell localCell18 = new Cell(new Chunk(paramStatement.getMonthlyAccountSummary().getNumElectronicTrans(), s));
      localCell18.setLeading(jdField_byte);
      localCell18.setBorderColor(jdField_goto);
      localCell18.setHorizontalAlignment(2);
      localTable.addCell(localCell18);
      Cell localCell19 = new Cell();
      localCell19.setLeading(jdField_byte);
      localCell19.setBorderColor(jdField_goto);
      localTable.addCell(localCell19);
      arrayOfObject[0] = new Integer(paramStatement.getMonthlyAccountSummary().getExcessLimit());
      arrayOfObject[1] = new Integer(paramStatement.getMonthlyAccountSummary().getNumExcessTrans());
      arrayOfObject[2] = new LocalizableCurrency(paramStatement.getMonthlyAccountSummary().getExcessTransTotalFeeAmountValue());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "TRANSACTIONS_IN_EXCESS_OF", arrayOfObject);
      Cell localCell20 = new Cell(new Chunk((String)localLocalizableString.localize(paramLocale), s));
      localCell20.setLeading(jdField_byte);
      localCell20.setBorderColor(jdField_goto);
      localCell20.setColspan(3);
      localTable.addCell(localCell20);
      String str2 = a(paramStatement.getMonthlyAccountSummary().getExcessFeeAmountValue(), paramLocale, false, true);
      Cell localCell21 = new Cell(new Chunk(str2, s));
      localCell21.setLeading(jdField_byte);
      localCell21.setBorderColor(jdField_goto);
      localCell21.setHorizontalAlignment(2);
      localTable.addCell(localCell21);
      Cell localCell22 = new Cell();
      localCell22.setLeading(jdField_byte);
      localCell22.setBorderColor(jdField_goto);
      localTable.addCell(localCell22);
      localParagraph.add(localTable);
      paramDocument.add(localParagraph);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36110, "Failed to add Monthly Account Summary to PDF");
    }
  }
  
  private static void jdField_else(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddDailyBalanceSummary";
    try
    {
      DailyBalanceSummaries localDailyBalanceSummaries = paramStatement.getDailyBalanceSummaries();
      DailyBalanceSummary localDailyBalanceSummary = new DailyBalanceSummary();
      Paragraph localParagraph = new Paragraph();
      localParagraph.setLeading(-3.0F);
      Table localTable1 = new Table(6, 1);
      localTable1.setAlignment(0);
      localTable1.setBorderColor(jdField_goto);
      localTable1.setCellpadding(0.0F);
      Cell localCell1 = new Cell(new Chunk(ResourceUtil.getString("DAILY_BALANCE_ACCOUNT_SUMMARY", "com.ffusion.istatements.resources", paramLocale), jdField_try));
      localCell1.setLeading(jdField_byte);
      localCell1.setBorderColor(jdField_goto);
      localCell1.setColspan(6);
      localTable1.addCell(localCell1);
      Cell localCell2 = new Cell(new Chunk(ResourceUtil.getString("DATE", "com.ffusion.istatements.resources", paramLocale), h));
      localCell2.setLeading(jdField_byte);
      localCell2.setBorderColor(jdField_goto);
      localTable1.addCell(localCell2);
      Cell localCell3 = new Cell(new Chunk(ResourceUtil.getString("BALANCE", "com.ffusion.istatements.resources", paramLocale), h));
      localCell3.setLeading(jdField_byte);
      localCell3.setBorderColor(jdField_goto);
      localCell3.setHorizontalAlignment(2);
      localTable1.addCell(localCell3);
      Cell localCell4 = new Cell();
      localCell4.setLeading(jdField_byte);
      localCell4.setBorderColor(jdField_goto);
      localTable1.addCell(localCell4);
      Cell localCell5 = new Cell(new Chunk(ResourceUtil.getString("RES_IN_USE", "com.ffusion.istatements.resources", paramLocale), h));
      localCell5.setLeading(jdField_byte);
      localCell5.setBorderColor(jdField_goto);
      localCell5.setHorizontalAlignment(2);
      localTable1.addCell(localCell5);
      Cell localCell6 = new Cell();
      localCell6.setLeading(jdField_byte);
      localCell6.setBorderColor(jdField_goto);
      localCell6.setColspan(2);
      localTable1.addCell(localCell6);
      localParagraph.add(localTable1);
      Iterator localIterator = null;
      if (localDailyBalanceSummaries != null)
      {
        localIterator = localDailyBalanceSummaries.iterator();
        while (localIterator.hasNext())
        {
          Table localTable2 = new Table(6, 1);
          localTable2.setAlignment(0);
          localTable2.setBorderColor(jdField_goto);
          localDailyBalanceSummary = (DailyBalanceSummary)localIterator.next();
          String str3 = a(localDailyBalanceSummary.getBalanceDateValue(), paramLocale, false);
          Cell localCell7 = new Cell(new Chunk(str3, s));
          localCell7.setLeading(jdField_byte);
          localCell7.setBorderColor(jdField_goto);
          localTable2.addCell(localCell7);
          String str2 = a(localDailyBalanceSummary.getAmountValue(), paramLocale, false, true);
          Cell localCell8 = new Cell(new Chunk(str2, s));
          localCell8.setLeading(jdField_byte);
          localCell8.setBorderColor(jdField_goto);
          localCell8.setHorizontalAlignment(2);
          localTable2.addCell(localCell8);
          Cell localCell9 = new Cell();
          localCell9.setLeading(jdField_byte);
          localCell9.setBorderColor(jdField_goto);
          localTable2.addCell(localCell9);
          str2 = a(localDailyBalanceSummary.getReserveInUseAmountValue(), paramLocale, false, true);
          Cell localCell10 = new Cell(new Chunk(str2, s));
          localCell10.setLeading(jdField_byte);
          localCell10.setBorderColor(jdField_goto);
          localCell10.setHorizontalAlignment(2);
          localTable2.addCell(localCell10);
          Cell localCell11 = new Cell();
          localCell11.setLeading(jdField_byte);
          localCell11.setBorderColor(jdField_goto);
          localCell11.setColspan(2);
          localTable2.addCell(localCell11);
          localParagraph.add(localTable2);
        }
      }
      paramDocument.add(localParagraph);
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str1, 36111, "Failed to add Daily Balance Summary to PDF");
    }
  }
  
  private static void jdField_if(Document paramDocument, Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.paragraphAddImportantInformation";
    try
    {
      Messages localMessages = paramStatement.getMessages();
      if ((localMessages != null) && (!localMessages.isEmpty()))
      {
        Iterator localIterator = localMessages.iterator();
        while (localIterator.hasNext())
        {
          Message localMessage = (Message)localIterator.next();
          if ((localMessage.get("MSG_TYPE").equals("1")) || (localMessage.get("MSG_TYPE").equals("2")) || (localMessage.get("MSG_TYPE").equals("3")))
          {
            Paragraph localParagraph = new Paragraph();
            localParagraph.setLeading(jdField_byte);
            localParagraph.add(new Chunk("\n"));
            localParagraph.add(new Chunk(localMessage.getMemo() + "\n", s));
            paramDocument.add(localParagraph);
          }
        }
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36112, "Failed to add Important Information section to PDF");
    }
  }
  
  private static void a(Document paramDocument, Statement paramStatement)
    throws IStatementException
  {
    String str = "StatementDataAdapter.paragraphAddTaxMessage";
    try
    {
      Messages localMessages = paramStatement.getMessages();
      if ((localMessages != null) && (!localMessages.isEmpty()))
      {
        Iterator localIterator = localMessages.iterator();
        while (localIterator.hasNext())
        {
          Message localMessage = (Message)localIterator.next();
          if (localMessage.get("MSG_TYPE").equals("4"))
          {
            Paragraph localParagraph = new Paragraph();
            localParagraph.setLeading(jdField_byte);
            localParagraph.add(new Chunk("\n"));
            localParagraph.add(new Chunk(localMessage.getMemo() + "\n", s));
            paramDocument.add(localParagraph);
          }
        }
      }
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36113, "Failed to add Tax Messages to PDF");
    }
  }
  
  private static void jdField_if(Document paramDocument, Statement paramStatement, Locale paramLocale)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.paragraphAddImages";
    Object[] arrayOfObject = new Object[4];
    try
    {
      ImageResults localImageResults = paramStatement.getImageResults();
      ImageResult localImageResult = new ImageResult();
      Iterator localIterator = null;
      if (localImageResult != null)
      {
        paramDocument.newPage();
        Paragraph localParagraph = new Paragraph();
        localParagraph.setLeading(jdField_byte);
        Table localTable = new Table(2);
        localTable.setAlignment(0);
        localTable.setBorderColor(jdField_goto);
        Cell localCell1 = new Cell(new Chunk(ResourceUtil.getString("CHECK_IMAGES", "com.ffusion.istatements.resources", paramLocale), jdField_try));
        localCell1.setLeading(jdField_byte);
        localCell1.setBorderColor(jdField_goto);
        localCell1.setColspan(2);
        localTable.addCell(localCell1);
        localParagraph.add(localTable);
        paramDocument.add(localParagraph);
        int i1 = 0;
        localIterator = localImageResults.iterator();
        while (localIterator.hasNext())
        {
          i1++;
          if (i1 == 1)
          {
            localParagraph = new Paragraph();
            localParagraph.setLeading(jdField_byte);
            localTable = new Table(2);
            localTable.setAlignment(0);
            localTable.setBorderColor(jdField_goto);
          }
          else
          {
            i1 = 0;
          }
          int i2 = 0;
          localImageResult = (ImageResult)localIterator.next();
          try
          {
            String str2 = ResourceUtil.getString("DATE_FORMAT", "com.ffusion.istatements.resources", paramLocale);
            localObject1 = localImageResult.getErrorCode();
            if ((localObject1 != null) && (!((String)localObject1).equals("")) && (!((String)localObject1).equalsIgnoreCase("0"))) {
              i2 = 1;
            }
            HashMap localHashMap = localImageResult.getImage();
            if ((localHashMap == null) && (((String)localObject1).equals("0"))) {
              localObject1 = String.valueOf(600014);
            }
            arrayOfObject[0] = localImageResult.getCheckNumber();
            arrayOfObject[1] = localImageResult.getSequenceNumber();
            arrayOfObject[2] = new LocalizableDate((DateTime)localImageResult.getPostingDateValue(), false);
            arrayOfObject[3] = new LocalizableCurrency(localImageResult.getAmountValue());
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.istatements.resources", "CHECK_IMAGE_INFO", arrayOfObject);
            Chunk localChunk = new Chunk((String)localLocalizableString.localize(paramLocale), s);
            Object localObject2;
            if ((i2 == 0) && (localHashMap != null))
            {
              localObject2 = (byte[])localHashMap.get(localImageResult.getFrontViewHandle());
              Image localImage1 = Image.getInstance((byte[])localObject2);
              localImage1.scaleAbsolute(261.0F, 120.0F);
              Cell localCell2 = new Cell(localImage1);
              localCell2.setLeading(jdField_byte);
              localCell2.setBorderColor(jdField_goto);
              localCell2.add(localChunk);
              localTable.addCell(localCell2);
              byte[] arrayOfByte = (byte[])localHashMap.get(localImageResult.getBackViewHandle());
              Image localImage2 = Image.getInstance(arrayOfByte);
              localImage1.scaleAbsolute(261.0F, 120.0F);
              Cell localCell3 = new Cell(localImage2);
              localCell3.setLeading(jdField_byte);
              localCell3.setBorderColor(jdField_goto);
              localCell3.add(localChunk);
              localTable.addCell(localCell3);
            }
            else
            {
              localObject2 = new Cell(new Chunk("\n\n\n\n\n", jdField_try));
              ((Cell)localObject2).add(new Chunk("        " + mapImageError((String)localObject1) + "\n\n\n\n\n", jdField_try));
              ((Cell)localObject2).setLeading(jdField_byte);
              ((Cell)localObject2).setBorderColor(jdField_goto);
              ((Cell)localObject2).add(localChunk);
              localTable.addCell((Cell)localObject2);
            }
          }
          catch (Exception localException2)
          {
            Object localObject1 = new Cell(new Chunk("\n\n\n\n\n", jdField_try));
            ((Cell)localObject1).add(new Chunk(localException2 + "\n\n\n\n\n", jdField_try));
            ((Cell)localObject1).setLeading(jdField_byte);
            ((Cell)localObject1).setBorderColor(jdField_goto);
            localTable.addCell((Cell)localObject1);
          }
          if (i1 == 0)
          {
            localParagraph.add(localTable);
            paramDocument.add(localParagraph);
          }
        }
        if (i1 == 1)
        {
          localParagraph.add(localTable);
          paramDocument.add(localParagraph);
        }
      }
    }
    catch (Exception localException1)
    {
      throw new IStatementException(localException1, str1, 36120, "Failed to add images to PDF");
    }
  }
  
  private static void a(Statement paramStatement, ResultSet paramResultSet)
    throws IStatementException
  {
    String str1 = "StatementDataAdapter.loadExtraFields";
    ResultSetMetaData localResultSetMetaData = null;
    int i1 = 0;
    try
    {
      localResultSetMetaData = paramResultSet.getMetaData();
      i1 = localResultSetMetaData.getColumnCount();
    }
    catch (SQLException localSQLException1)
    {
      throw new IStatementException(localSQLException1, str1, 36114, "Failed to get ResultSet metadata");
    }
    if (i1 > 17) {
      for (int i2 = 1; i2 <= i1; i2++) {
        try
        {
          String str2 = localResultSetMetaData.getColumnName(i2);
          int i3 = localResultSetMetaData.getColumnType(i2);
          if ((!str2.equals("STMT_ID")) && (!str2.equals("STMT_DATE")) && (!str2.equals("ACCOUNT_NAME")) && (!str2.equals("STMT_START_DATE")) && (!str2.equals("STMT_END_DATE")) && (!str2.equals("PROD_CODE")) && (!str2.equals("SVC_CHARGE_CODE")) && (!str2.equals("STMT_CYCLE")) && (!str2.equals("BANK_ID")) && (!str2.equals("RET_ADDR1")) && (!str2.equals("RET_ADDR2")) && (!str2.equals("NAME_ADDR1")) && (!str2.equals("NAME_ADDR2")) && (!str2.equals("NAME_ADDR3")) && (!str2.equals("NAME_ADDR4")) && (!str2.equals("NAME_ADDR5")) && (!str2.equals("NAME_ADDR6"))) {
            switch (i3)
            {
            case -5: 
              BigDecimal localBigDecimal = paramResultSet.getBigDecimal(str2);
              paramStatement.set(str2, localBigDecimal.toString());
              break;
            case 91: 
              java.sql.Date localDate = paramResultSet.getDate(str2);
              paramStatement.set(str2, localDate.toString());
              break;
            case 3: 
            case 8: 
              double d1 = paramResultSet.getDouble(str2);
              paramStatement.set(str2, String.valueOf(d1));
              break;
            case 6: 
              float f1 = paramResultSet.getFloat(str2);
              paramStatement.set(str2, String.valueOf(f1));
              break;
            case 4: 
              int i4 = paramResultSet.getInt(str2);
              paramStatement.set(str2, String.valueOf(i4));
              break;
            case 92: 
              Time localTime = paramResultSet.getTime(str2);
              paramStatement.set(str2, localTime.toString());
              break;
            case 93: 
              Timestamp localTimestamp = paramResultSet.getTimestamp(str2);
              paramStatement.set(str2, localTimestamp.toString());
              break;
            default: 
              String str3 = paramResultSet.getString(str2);
              paramStatement.set(str2, str3);
            }
          }
        }
        catch (SQLException localSQLException2)
        {
          throw new IStatementException(localSQLException2, str1, 36115, "Failed to retrieve table column attributes");
        }
      }
    }
  }
  
  private static InputStream a(String paramString)
    throws IStatementException
  {
    String str = "StatementDataAdapter.getResourceAsStream";
    InputStream localInputStream = null;
    try
    {
      PropertiesUtil localPropertiesUtil = new PropertiesUtil();
      localInputStream = localPropertiesUtil.getClass().getClassLoader().getResourceAsStream(paramString);
      if (localInputStream == null) {
        localInputStream = ClassLoader.getSystemResourceAsStream(paramString);
      }
      if (localInputStream == null)
      {
        ClassLoader.getSystemClassLoader();
        localInputStream = ClassLoader.getSystemResourceAsStream(paramString);
      }
      return localInputStream;
    }
    catch (Exception localException)
    {
      throw new IStatementException(localException, str, 36116, "Failed to load properties");
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (paramString != null) {
      if ((paramString.indexOf(',') == -1) && (paramString.indexOf(' ') == -1)) {
        paramStringBuffer.append(paramString);
      } else {
        paramStringBuffer.append('"' + paramString + '"');
      }
    }
    if (paramBoolean == true) {
      paramStringBuffer.append(",");
    }
  }
  
  public static String mapImageError(String paramString)
  {
    String str = paramString;
    if (paramString.equalsIgnoreCase("0")) {
      str = "ERROR_NONE";
    } else if (paramString.equalsIgnoreCase("1010400")) {
      str = "ERROR_PARTIAL_SUCCESS";
    } else if (paramString.equalsIgnoreCase("1010401")) {
      str = "ERROR_MISSING_VIEW";
    } else if (paramString.equalsIgnoreCase("1010402")) {
      str = "ERROR_MAX_ITEMS_EXCEEDED";
    } else if (paramString.equalsIgnoreCase("1010403")) {
      str = "ERROR_MAX_ITEMS_MISSING_VIEWS";
    } else if (paramString.equalsIgnoreCase("1010600")) {
      str = "Your image cannot be displayed at this time.  Please allow 24 hours for your request to be processed, and log back in to see your statement.";
    } else if (paramString.equalsIgnoreCase("1010800")) {
      str = "ERROR_IN_REQUEST";
    } else if (paramString.equalsIgnoreCase("1010802")) {
      str = "ERROR_REQUEST_TOO_LARGE";
    } else if (paramString.equalsIgnoreCase("1010820")) {
      str = "ERROR_NO_CRITERIA";
    } else if (paramString.equalsIgnoreCase("1010821")) {
      str = "ERROR_CRITERIA_FIELD_INVALID_LENGTH";
    } else if (paramString.equalsIgnoreCase("1010822")) {
      str = "ERROR_CRITERIA_FIELD_UNSEARCHABLE";
    } else if (paramString.equalsIgnoreCase("1010823")) {
      str = "ERROR_CRITERIA_FIELD_UNDEFINED";
    } else if (paramString.equalsIgnoreCase("1010824")) {
      str = "ERROR_INVALID_REQUEST_TYPE";
    } else if (paramString.equalsIgnoreCase("1010825")) {
      str = "ERROR_REPOSITORY_LIST_UNDEFINED";
    } else if (paramString.equalsIgnoreCase("1010826")) {
      str = "ERROR_INVALID_OPERATORS";
    } else if (paramString.equalsIgnoreCase("1010827")) {
      str = "ERROR_INVALID_CONNECTORS";
    } else if (paramString.equalsIgnoreCase("1010828")) {
      str = "ERROR_INVALID_VIEW";
    } else if (paramString.equalsIgnoreCase("1010829")) {
      str = "ERROR_MAXIMUM_FIELDS";
    } else if (paramString.equalsIgnoreCase("1010830")) {
      str = "ERROR_DIRECTEDID_TOO_LONG";
    } else if (paramString.equalsIgnoreCase("1010831")) {
      str = "ERROR_ITEM_HANDLE_TOO_LONG";
    } else if (paramString.equalsIgnoreCase("1010832")) {
      str = "ERROR_CONFIG_DATA";
    } else if (paramString.equalsIgnoreCase("1010833")) {
      str = "ERROR_VALUE_FORMAT";
    } else if (paramString.equalsIgnoreCase("1010834")) {
      str = "ERROR_INVALID_HANDLE";
    } else if (paramString.equalsIgnoreCase("1010835")) {
      str = "ERROR_HANDLE_REJECTED";
    } else if (paramString.equalsIgnoreCase("1011200")) {
      str = "ERROR_SYSTEM_ERROR";
    } else if (paramString.equalsIgnoreCase("1011240")) {
      str = "ERROR_TIME_OUT";
    } else if (paramString.equalsIgnoreCase("1011241")) {
      str = "ERROR_FAILOVER_TOO_SLOW";
    } else if (paramString.equalsIgnoreCase("1011243")) {
      str = "ERROR_COMPUTE_STORAGE_TIER";
    } else if (paramString.equalsIgnoreCase("1011244")) {
      str = "ERROR_SERVER_NOT_FOUND";
    } else if (paramString.equalsIgnoreCase("1011245")) {
      str = "ERROR_SITE_NOT_FOUND";
    } else if (paramString.equalsIgnoreCase("1011246")) {
      str = "ERROR_COMMUNICATION_ERROR";
    } else if (paramString.equalsIgnoreCase("1011247")) {
      str = "ERROR_INVALID_DATA_FORMAT";
    } else if (paramString.equalsIgnoreCase("1011248")) {
      str = "ERROR_ALL_ITEMS_FAILED";
    } else if (paramString.equalsIgnoreCase("1011249")) {
      str = "ERROR_MAX_HITS";
    } else if (paramString.equalsIgnoreCase("1011250")) {
      str = "ERROR_UNABLE_TO_CONNECT";
    } else if (paramString.equalsIgnoreCase("1011251")) {
      str = "ERROR_SEARCH_FAILED";
    } else if (paramString.equalsIgnoreCase("1011252")) {
      str = "ERROR_LIBRARY_FAILED";
    } else if (paramString.equalsIgnoreCase("1011253")) {
      str = "ERROR_RETRIEVE_IMAGE";
    } else if (paramString.equalsIgnoreCase("1011254")) {
      str = "ERROR_KEY_SEARCH";
    } else if (paramString.equalsIgnoreCase("1011255")) {
      str = "ERROR_KEY_MATCH";
    } else if (paramString.equalsIgnoreCase("1011600")) {
      str = "ERROR_INTERNAL_IRB_ERROR";
    } else if (paramString.equalsIgnoreCase("1012000")) {
      str = "ERROR_SERVER_RESOURCE";
    } else if (paramString.equalsIgnoreCase("1012800")) {
      str = "ERROR_DATA_CREATION";
    } else if (paramString.equalsIgnoreCase("600013")) {
      str = "Exception building search criteria";
    } else if (paramString.equalsIgnoreCase("600014")) {
      str = "Image HashMap Empty";
    }
    return str;
  }
  
  private static String a(Currency paramCurrency, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2)
  {
    LocalizableCurrency localLocalizableCurrency = null;
    if (paramCurrency == null) {
      localLocalizableCurrency = new LocalizableCurrency(new Currency(new BigDecimal(0.0D), paramLocale));
    } else {
      localLocalizableCurrency = new LocalizableCurrency(paramCurrency);
    }
    localLocalizableCurrency.setShowCurrencySymbol(paramBoolean1);
    localLocalizableCurrency.setShowSeparatorAndSymbol(paramBoolean2);
    return (String)localLocalizableCurrency.localize(paramLocale);
  }
  
  private static String a(DateTime paramDateTime, Locale paramLocale, boolean paramBoolean)
  {
    LocalizableDate localLocalizableDate = new LocalizableDate(paramDateTime, paramBoolean);
    return (String)localLocalizableDate.localize(paramLocale);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.istatements.adapters.StatementDataAdapter
 * JD-Core Version:    0.7.0.1
 */