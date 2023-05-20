package com.ffusion.treasurydirect;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportColumns;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.csil.core.Reporting;
import com.ffusion.dataconsolidator.adapter.DCAdapter;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.ProfileUtil;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

public class TreasuryDirectAdapter
{
  private static boolean jdField_try;
  private static final String t = "location_id_2";
  private static final String m = "inc_zbacr_inrollup_2";
  private static final String q = "inc_zbadb_inrollup_2";
  private static final String jdField_new = "sub_routing_num";
  private static final String C = "sub_account_id";
  private static final String jdField_else = "business_id";
  private static final String jdField_void = "business_name";
  private static final String y = "cust_id";
  private static final String jdField_goto = "bank_id";
  private static final String k = "DataDate";
  private static final String jdField_int = "BankID";
  private static final String i = "RoutingNum";
  private static final String s = "AccountID";
  private static final String n = "ClosingLedger";
  private static final String F = "dcAccount.BankID";
  private static final String a = "dcAccount.AccountID";
  private static final String v = "dcAccount.RoutingNumber";
  private static final String o = "dcAcctHistory.DataDate";
  private static final String D = "insert into TD_ACCOUNTS_REL (BANK_ID, BUS_DIRECTORY_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, SUB_ROUTING_NUM, SUB_ACCOUNT_ID, LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String jdField_do = "delete from TD_ACCOUNTS_REL where BANK_ID = ? and BUS_DIRECTORY_ID = ? and MASTER_ACCOUNT_ID = ? ";
  private static final String r = "update accounts set IS_MASTER = 'N' where directory_id = ? and account_id = ? ";
  private static final String jdField_for = "delete from TD_ACCOUNTS_REL where BANK_ID = ? and BUS_DIRECTORY_ID = ? and MASTER_ACCOUNT_ID = ? ";
  private static final String A = "select a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2 from accounts a, TD_ACCOUNTS_REL b where a.routing_num = b.SUB_ROUTING_NUM and a.account_id = b.SUB_ACCOUNT_ID and a.directory_id = b.BUS_DIRECTORY_ID and b.BANK_ID = ? and b.BUS_DIRECTORY_ID = ? and b.MASTER_ACCOUNT_ID = ? ";
  private static final String c = "select a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2 from accounts a, TD_ACCOUNTS_REL b where a.account_id = b.SUB_ACCOUNT_ID and a.directory_id = b.BUS_DIRECTORY_ID and a.routing_num is null and b.SUB_ROUTING_NUM is null and b.BANK_ID = ? and b.BUS_DIRECTORY_ID = ? and b.MASTER_ACCOUNT_ID = ? ";
  private static final String jdField_if = "update TD_ACCOUNTS_REL set LOCATION_ID = ?, INC_ZBACR_INROLLUP = ?, INC_ZBADB_INROLLUP = ? where BANK_ID = ? and BUS_DIRECTORY_ID = ? and SUB_ACCOUNT_ID = ? and SUB_ROUTING_NUM = ? and MASTER_ACCOUNT_ID = ? ";
  private static final String jdField_case = "update TD_ACCOUNTS_REL set LOCATION_ID = ?, INC_ZBACR_INROLLUP = ?, INC_ZBADB_INROLLUP = ? where BANK_ID = ? and BUS_DIRECTORY_ID = ? and SUB_ACCOUNT_ID = ? and SUB_ROUTING_NUM is null and MASTER_ACCOUNT_ID = ? ";
  private static final String K = "select SUB_ROUTING_NUM, SUB_ACCOUNT_ID from TD_ACCOUNTS_REL where BANK_ID = ? and BUS_DIRECTORY_ID = ? and MASTER_ACCOUNT_ID = ? ";
  private static final String z = " AND SUB_ROUTING_NUM = ? ";
  private static final String u = " AND SUB_ROUTING_NUM is null ";
  private static final String p = " AND MASTER_ROUTING_NUM = ? ";
  private static final String jdField_null = " AND MASTER_ROUTING_NUM is null ";
  private static final String b = "SELECT count(*) FROM TD_ACCOUNTS_REL WHERE BANK_ID = ? AND BUS_DIRECTORY_ID = ? AND SUB_ACCOUNT_ID = ?";
  private static final String j = "SELECT count(*) FROM TD_ACCOUNTS_REL WHERE BANK_ID = ? AND BUS_DIRECTORY_ID = ? AND MASTER_ACCOUNT_ID = ?";
  private static final String g = "SELECT DISTINCT SUB_ROUTING_NUM, SUB_ACCOUNT_ID FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?";
  private static final String h = "SELECT SUB_ROUTING_NUM, SUB_ACCOUNT_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?";
  private static final String w = "select business_id, business_name, cust_id, bank_id from business b, customer_directory c where b.business_id = c.directory_id and affiliate_bank_id = ? order by business_name";
  private static final String l = "select dcAcctHistory.DataDate, dcAcctHistory.DataSourceFileDate, dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.AccountID, dcAcctHistory.ClosingLedger from DC_AccountHistory dcAcctHistory, DC_Account dcAccount where ( ";
  private static final String jdField_long = " ) AND ( dcAcctHistory.DCAccountID = dcAccount.DCAccountID)  AND (dcAcctHistory.ClosingLedger is null OR dcAcctHistory.ClosingLedger != 0) ) order by dcAcctHistory.DataDate, dcAccount.RoutingNumber, dcAccount.AccountID";
  private static final String e = "DB_PROPERTIES";
  private static final String jdField_char = "SELECT a.*  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.MASTER_ROUTING_NUM = a.routing_num AND b.MASTER_ACCOUNT_ID = a.account_id AND  b.BANK_ID = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.SUB_ROUTING_NUM = ? ";
  private static final String B = "SELECT a.*  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.MASTER_ROUTING_NUM = a.routing_num AND b.MASTER_ACCOUNT_ID = a.account_id AND  b.BANK_ID = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.SUB_ROUTING_NUM IS NULL ";
  private static final String L = "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM = ? AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ";
  private static final String J = "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM = ? AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM IS NULL AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ";
  private static final String I = "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM IS NULL AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ";
  private static final String G = "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM IS NULL AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM IS NULL AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ";
  private static final int f = 12;
  private static final int jdField_byte = 100;
  private static final int x = 250;
  private static String d = null;
  private static final String E = "com.ffusion.beans.accounts.resources";
  private static final String H = "com.ffusion.beans.reporting.reports";
  
  public static void initialize(HashMap paramHashMap)
    throws TDException
  {
    String str = "SystemAdminAdapter.initialize";
    if ((d == null) || (d.length() == 0))
    {
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      if (localProperties == null)
      {
        DebugLog.log(Level.SEVERE, str + ": Missing database connection pool configuration.");
        throw new TDException(80001, "Missing database connection pool configuration.");
      }
      try
      {
        d = ConnectionPool.init(localProperties);
        jdField_try = true;
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, str + ": " + localException.getMessage());
        throw new TDException(80002, localException);
      }
    }
  }
  
  public static void verifyInitialized()
    throws TDException
  {
    if (!jdField_try)
    {
      String str = "The Treasury Direct adapter must be initialized before any calls are made to the service.";
      DebugLog.log(Level.SEVERE, str);
      throw new TDException(80003, str);
    }
  }
  
  public static void addSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.addSubAccounts";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1;
    if ((paramSecureUser == null) || (paramBusiness == null) || (paramAccount == null) || (paramAccounts == null))
    {
      localObject1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80008, (String)localObject1);
    }
    verifyInitialized();
    if (paramBusiness.getBankId() == null)
    {
      localObject1 = "An invalid bank ID specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80005, (String)localObject1);
    }
    a(paramAccount);
    try
    {
      localObject1 = null;
      int i1 = 1;
      localConnection = DBUtil.getConnection(d, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into TD_ACCOUNTS_REL (BANK_ID, BUS_DIRECTORY_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, SUB_ROUTING_NUM, SUB_ACCOUNT_ID, LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
      localPreparedStatement.setString(i1++, paramBusiness.getBankId());
      localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
      localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
      localPreparedStatement.setString(i1++, paramAccount.getID());
      int i2 = 0;
      int i3 = paramAccounts.size();
      while (i2 < i3)
      {
        int i4 = i1;
        localObject1 = (Account)paramAccounts.get(i2);
        a((Account)localObject1);
        localPreparedStatement.setString(i4++, ((Account)localObject1).getRoutingNum());
        localPreparedStatement.setString(i4++, ((Account)localObject1).getID());
        localPreparedStatement.setString(i4++, ((Account)localObject1).getLocationID());
        localPreparedStatement.setString(i4++, ((Account)localObject1).getIncludeZBACreditInRollupValue() ? "Y" : "N");
        localPreparedStatement.setString(i4++, ((Account)localObject1).getIncludeZBADebitInRollupValue() ? "Y" : "N");
        if (DBUtil.executeUpdate(localPreparedStatement, "insert into TD_ACCOUNTS_REL (BANK_ID, BUS_DIRECTORY_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, SUB_ROUTING_NUM, SUB_ACCOUNT_ID, LOCATION_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP) values (?, ?, ?, ?, ?, ?, ?, ?, ?)") != 1)
        {
          String str2 = "Failed to add an account as a sub-account to a master account.";
          DebugLog.log(Level.FINE, str2);
          throw new TDException(80004, str2);
        }
        i2++;
      }
      DBUtil.commit(localConnection);
    }
    catch (TDException localTDException)
    {
      DBUtil.rollback(localConnection);
      throw localTDException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.deleteSubAccounts";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1;
    if ((paramSecureUser == null) || (paramBusiness == null) || (paramAccount == null))
    {
      localObject1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80008, (String)localObject1);
    }
    verifyInitialized();
    if (paramBusiness.getBankId() == null)
    {
      localObject1 = "An invalid bank ID specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80005, (String)localObject1);
    }
    a(paramAccount);
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      localObject1 = null;
      String str2 = "";
      Account localAccount = null;
      localObject1 = new StringBuffer("delete from TD_ACCOUNTS_REL where BANK_ID = ? and BUS_DIRECTORY_ID = ? and MASTER_ACCOUNT_ID = ? ");
      if (paramAccount.getRoutingNum() == null) {
        ((StringBuffer)localObject1).append("and MASTER_ROUTING_NUM is null ");
      } else {
        ((StringBuffer)localObject1).append("and MASTER_ROUTING_NUM = ? ");
      }
      ((StringBuffer)localObject1).append("and (");
      int i1 = 0;
      int i2 = paramAccounts.size();
      while (i1 < i2)
      {
        localAccount = (Account)paramAccounts.get(i1);
        ((StringBuffer)localObject1).append(str2);
        ((StringBuffer)localObject1).append("(SUB_ROUTING_NUM");
        if (localAccount.getRoutingNum() == null) {
          ((StringBuffer)localObject1).append(" is null ");
        } else {
          ((StringBuffer)localObject1).append(" = ? ");
        }
        ((StringBuffer)localObject1).append("and SUB_ACCOUNT_ID = ?)");
        str2 = " or ";
        i1++;
      }
      ((StringBuffer)localObject1).append(')');
      try
      {
        i1 = 1;
        localConnection = DBUtil.getConnection(d, false, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, ((StringBuffer)localObject1).toString());
        localPreparedStatement.setString(i1++, paramBusiness.getBankId());
        localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
        localPreparedStatement.setString(i1++, paramAccount.getID());
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
        }
        i2 = 0;
        int i3 = paramAccounts.size();
        while (i2 < i3)
        {
          localAccount = (Account)paramAccounts.get(i2);
          a(localAccount);
          if (localAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(i1++, localAccount.getRoutingNum());
          }
          localPreparedStatement.setString(i1++, localAccount.getID());
          i2++;
        }
        if (DBUtil.executeUpdate(localPreparedStatement, ((StringBuffer)localObject1).toString()) != paramAccounts.size())
        {
          String str3 = "Failed to remove one or more accounts as a sub-account to a master account.";
          DebugLog.log(Level.FINE, str3);
          DBUtil.rollback(localConnection);
          throw new TDException(80009, str3);
        }
        DBUtil.commit(localConnection);
      }
      catch (TDException localTDException)
      {
        DBUtil.rollback(localConnection);
        throw localTDException;
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
        DBUtil.rollback(localConnection);
        throw new TDException(80000, localException);
      }
      finally
      {
        DBUtil.closeAll(d, localConnection, localPreparedStatement);
      }
    }
  }
  
  public static void deleteMasterAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.deleteMasterAccount";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str2;
    if ((paramSecureUser == null) || (paramBusiness == null) || (paramAccount == null))
    {
      str2 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str2);
      throw new TDException(80008, str2);
    }
    verifyInitialized();
    if (paramBusiness.getBankId() == null)
    {
      str2 = "An invalid bank ID specified.";
      DebugLog.log(Level.FINE, str2);
      throw new TDException(80005, str2);
    }
    a(paramAccount);
    try
    {
      int i1 = 1;
      StringBuffer localStringBuffer = null;
      localConnection = DBUtil.getConnection(d, false, 2);
      localStringBuffer = new StringBuffer("update accounts set IS_MASTER = 'N' where directory_id = ? and account_id = ? ");
      if (paramAccount.getRoutingNum() == null) {
        localStringBuffer.append("and routing_num is null");
      } else {
        localStringBuffer.append("and routing_num = ?");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i1 = 1;
      localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
      localPreparedStatement.setString(i1++, paramAccount.getID());
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
      }
      if (DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString()) != 1)
      {
        String str3 = "Failed to delete the specified master account.";
        DebugLog.log(Level.FINE, str3);
        DBUtil.rollback(localConnection);
        throw new TDException(80010, str3);
      }
      localStringBuffer.setLength(0);
      localStringBuffer.append("delete from TD_ACCOUNTS_REL where BANK_ID = ? and BUS_DIRECTORY_ID = ? and MASTER_ACCOUNT_ID = ? ");
      if (paramAccount.getRoutingNum() == null) {
        localStringBuffer.append("and MASTER_ROUTING_NUM is null");
      } else {
        localStringBuffer.append("and MASTER_ROUTING_NUM = ?");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i1 = 1;
      localPreparedStatement.setString(i1++, paramBusiness.getBankId());
      localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
      localPreparedStatement.setString(i1++, paramAccount.getID());
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
      }
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      DBUtil.commit(localConnection);
    }
    catch (TDException localTDException)
    {
      DBUtil.rollback(localConnection);
      throw localTDException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement);
    }
  }
  
  public static Accounts getSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    String str = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getSubAccounts";
    Accounts localAccounts = new Accounts();
    if ((paramSecureUser != null) && (paramSecureUser.getLocale() != null)) {
      localAccounts.setLocale(paramSecureUser.getLocale());
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object localObject1;
    if ((paramSecureUser == null) || (paramBusiness == null) || (paramAccount == null))
    {
      localObject1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80008, (String)localObject1);
    }
    verifyInitialized();
    if (paramBusiness.getBankId() == null)
    {
      localObject1 = "An invalid bank ID specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80005, (String)localObject1);
    }
    a(paramAccount);
    try
    {
      localObject1 = null;
      StringBuffer localStringBuffer = null;
      int i1 = 1;
      localStringBuffer = new StringBuffer("select a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2 from accounts a, TD_ACCOUNTS_REL b where a.routing_num = b.SUB_ROUTING_NUM and a.account_id = b.SUB_ACCOUNT_ID and a.directory_id = b.BUS_DIRECTORY_ID and b.BANK_ID = ? and b.BUS_DIRECTORY_ID = ? and b.MASTER_ACCOUNT_ID = ? ");
      if (paramAccount.getRoutingNum() == null) {
        localStringBuffer.append("and b.MASTER_ROUTING_NUM is null");
      } else {
        localStringBuffer.append("and b.MASTER_ROUTING_NUM = ?");
      }
      localStringBuffer.append(" union ");
      localStringBuffer.append("select a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2 from accounts a, TD_ACCOUNTS_REL b where a.account_id = b.SUB_ACCOUNT_ID and a.directory_id = b.BUS_DIRECTORY_ID and a.routing_num is null and b.SUB_ROUTING_NUM is null and b.BANK_ID = ? and b.BUS_DIRECTORY_ID = ? and b.MASTER_ACCOUNT_ID = ? ");
      if (paramAccount.getRoutingNum() == null) {
        localStringBuffer.append("and b.MASTER_ROUTING_NUM is null");
      } else {
        localStringBuffer.append("and b.MASTER_ROUTING_NUM = ?");
      }
      localConnection = DBUtil.getConnection(d, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i1 = 1;
      for (int i2 = 0; i2 < 2; i2++)
      {
        localPreparedStatement.setString(i1++, paramBusiness.getBankId());
        localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
        localPreparedStatement.setString(i1++, paramAccount.getID());
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
        }
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = ProfileUtil.processAccountRS(localResultSet, paramBusiness.getBankId());
        ((Account)localObject1).setLocationID(localResultSet.getString("location_id_2"));
        ((Account)localObject1).setIncludeZBACreditInRollup(localResultSet.getString("inc_zbacr_inrollup_2").equalsIgnoreCase("y"));
        ((Account)localObject1).setIncludeZBADebitInRollup(localResultSet.getString("inc_zbadb_inrollup_2").equalsIgnoreCase("y"));
        localAccounts.add(localObject1);
      }
      DBUtil.commit(localConnection);
    }
    catch (TDException localTDException)
    {
      DBUtil.rollback(localConnection);
      throw localTDException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static void modifySubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.modifySubAccounts";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    Object localObject1;
    if ((paramSecureUser == null) || (paramBusiness == null) || (paramAccount == null))
    {
      localObject1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80008, (String)localObject1);
    }
    verifyInitialized();
    if (paramBusiness.getBankId() == null)
    {
      localObject1 = "An invalid bank ID specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80005, (String)localObject1);
    }
    a(paramAccount);
    if ((paramAccounts != null) && (paramAccounts.size() > 0)) {
      try
      {
        localObject1 = null;
        StringBuffer localStringBuffer1 = null;
        StringBuffer localStringBuffer2 = null;
        localStringBuffer1 = new StringBuffer("update TD_ACCOUNTS_REL set LOCATION_ID = ?, INC_ZBACR_INROLLUP = ?, INC_ZBADB_INROLLUP = ? where BANK_ID = ? and BUS_DIRECTORY_ID = ? and SUB_ACCOUNT_ID = ? and SUB_ROUTING_NUM = ? and MASTER_ACCOUNT_ID = ? ");
        localStringBuffer2 = new StringBuffer("update TD_ACCOUNTS_REL set LOCATION_ID = ?, INC_ZBACR_INROLLUP = ?, INC_ZBADB_INROLLUP = ? where BANK_ID = ? and BUS_DIRECTORY_ID = ? and SUB_ACCOUNT_ID = ? and SUB_ROUTING_NUM is null and MASTER_ACCOUNT_ID = ? ");
        if (paramAccount.getRoutingNum() == null)
        {
          localStringBuffer1.append("and MASTER_ROUTING_NUM is null");
          localStringBuffer2.append("and MASTER_ROUTING_NUM is null");
        }
        else
        {
          localStringBuffer1.append("and MASTER_ROUTING_NUM = ?");
          localStringBuffer2.append("and MASTER_ROUTING_NUM = ?");
        }
        localConnection = DBUtil.getConnection(d, false, 2);
        localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
        localPreparedStatement1.setString(4, paramBusiness.getBankId());
        localPreparedStatement1.setInt(5, paramBusiness.getIdValue());
        localPreparedStatement1.setString(8, paramAccount.getID());
        localPreparedStatement2.setString(4, paramBusiness.getBankId());
        localPreparedStatement2.setInt(5, paramBusiness.getIdValue());
        localPreparedStatement2.setString(7, paramAccount.getID());
        if (paramAccount.getRoutingNum() != null)
        {
          localPreparedStatement1.setString(9, paramAccount.getRoutingNum());
          localPreparedStatement2.setString(8, paramAccount.getRoutingNum());
        }
        int i1 = 0;
        int i2 = paramAccounts.size();
        while (i1 < i2)
        {
          localObject1 = (Account)paramAccounts.get(i1);
          a((Account)localObject1);
          String str2;
          if (((Account)localObject1).getRoutingNum() == null)
          {
            localPreparedStatement2.setString(1, ((Account)localObject1).getLocationID());
            localPreparedStatement2.setString(2, ((Account)localObject1).getIncludeZBACreditInRollupValue() ? "Y" : "N");
            localPreparedStatement2.setString(3, ((Account)localObject1).getIncludeZBADebitInRollupValue() ? "Y" : "N");
            localPreparedStatement2.setString(6, ((Account)localObject1).getID());
            if (DBUtil.executeUpdate(localPreparedStatement2, localStringBuffer2.toString()) != 1)
            {
              str2 = "Failed to update a sub-account.";
              DebugLog.log(Level.FINE, str2);
              throw new TDException(80011, str2);
            }
          }
          else
          {
            localPreparedStatement1.setString(1, ((Account)localObject1).getLocationID());
            localPreparedStatement1.setString(2, ((Account)localObject1).getIncludeZBACreditInRollupValue() ? "Y" : "N");
            localPreparedStatement1.setString(3, ((Account)localObject1).getIncludeZBADebitInRollupValue() ? "Y" : "N");
            localPreparedStatement1.setString(6, ((Account)localObject1).getID());
            localPreparedStatement1.setString(7, ((Account)localObject1).getRoutingNum());
            if (DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer1.toString()) != 1)
            {
              str2 = "Failed to update a sub-account.";
              DebugLog.log(Level.FINE, str2);
              throw new TDException(80011, str2);
            }
          }
          i1++;
        }
        DBUtil.commit(localConnection);
      }
      catch (TDException localTDException)
      {
        DBUtil.rollback(localConnection);
        throw localTDException;
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
        DBUtil.rollback(localConnection);
        throw new TDException(80000, localException);
      }
      finally
      {
        DBUtil.closeStatement(localPreparedStatement1);
        DBUtil.closeStatement(localPreparedStatement2);
        DBUtil.returnConnection(d, localConnection);
      }
    }
  }
  
  public static Accounts filterAvailableSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    String str = "com.ffusion.treasurydirect.TreasuryDirectAdapter.filterAvailableSubAccounts";
    Accounts localAccounts = new Accounts();
    if ((paramSecureUser != null) && (paramSecureUser.getLocale() != null)) {
      localAccounts.setLocale(paramSecureUser.getLocale());
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object localObject1;
    if ((paramSecureUser == null) || (paramBusiness == null) || (paramAccount == null))
    {
      localObject1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80008, (String)localObject1);
    }
    verifyInitialized();
    if (paramBusiness.getBankId() == null)
    {
      localObject1 = "An invalid bank ID specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80005, (String)localObject1);
    }
    a(paramAccount);
    if ((paramAccounts != null) && (paramAccounts.size() > 0)) {
      try
      {
        localObject1 = null;
        HashSet localHashSet = null;
        StringBuffer localStringBuffer1 = null;
        StringBuffer localStringBuffer2 = null;
        int i1 = 1;
        localConnection = DBUtil.getConnection(d, false, 2);
        localStringBuffer1 = new StringBuffer("select SUB_ROUTING_NUM, SUB_ACCOUNT_ID from TD_ACCOUNTS_REL where BANK_ID = ? and BUS_DIRECTORY_ID = ? and MASTER_ACCOUNT_ID = ? ");
        if (paramAccount.getRoutingNum() == null) {
          localStringBuffer1.append(" and MASTER_ROUTING_NUM is null");
        } else {
          localStringBuffer1.append(" and MASTER_ROUTING_NUM = ?");
        }
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
        i1 = 1;
        localPreparedStatement.setString(i1++, paramBusiness.getBankId());
        localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
        localPreparedStatement.setString(i1++, paramAccount.getID());
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
        localHashSet = new HashSet();
        localStringBuffer2 = new StringBuffer();
        while (localResultSet.next())
        {
          localStringBuffer2.setLength(0);
          localStringBuffer2.append(localResultSet.getString("sub_routing_num"));
          localStringBuffer2.append(localResultSet.getString("sub_account_id"));
          localHashSet.add(localStringBuffer2.toString());
        }
        localStringBuffer2.setLength(0);
        localStringBuffer2.append(paramAccount.getRoutingNum());
        localStringBuffer2.append(paramAccount.getID());
        localHashSet.add(localStringBuffer2.toString());
        DBUtil.commit(localConnection);
        Iterator localIterator = paramAccounts.iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Account)localIterator.next();
          localStringBuffer2.setLength(0);
          localStringBuffer2.append(((Account)localObject1).getRoutingNum());
          localStringBuffer2.append(((Account)localObject1).getID());
          if (!localHashSet.contains(localStringBuffer2.toString())) {
            localAccounts.add(localObject1);
          }
        }
      }
      catch (TDException localTDException)
      {
        DBUtil.rollback(localConnection);
        throw localTDException;
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
        DBUtil.rollback(localConnection);
        throw new TDException(80000, localException);
      }
      finally
      {
        DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
      }
    }
    return localAccounts;
  }
  
  private static void a(Account paramAccount)
    throws TDException
  {
    if (paramAccount == null) {
      throw new IllegalArgumentException();
    }
    if (paramAccount.getID() == null)
    {
      String str = "The specified account does not have an ID.";
      DebugLog.log(Level.FINE, str);
      throw new TDException(80007, str);
    }
  }
  
  private static void a(Business paramBusiness)
    throws TDException
  {
    String str;
    if (paramBusiness == null)
    {
      str = "The specified business is null.";
      DebugLog.log(Level.FINE, str);
      throw new TDException(-1, str);
    }
    if (paramBusiness.getIdValue() <= 0)
    {
      str = "The business id in the specified business is invalid.";
      DebugLog.log(Level.FINE, str);
      throw new TDException(80012, str);
    }
    if (paramBusiness.getBankIdValue() <= 0)
    {
      str = "The bank id in the specified business is invalid.";
      DebugLog.log(Level.FINE, str);
      throw new TDException(80005, str);
    }
  }
  
  public static int getNumMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    String str = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getNumMasterAccounts";
    a(paramAccount);
    a(paramBusiness);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    verifyInitialized();
    try
    {
      int i1 = 1;
      localConnection = DBUtil.getConnection(d, false, 2);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("SELECT count(*) FROM TD_ACCOUNTS_REL WHERE BANK_ID = ? AND BUS_DIRECTORY_ID = ? AND SUB_ACCOUNT_ID = ?");
      if (paramAccount.getRoutingNum() == null) {
        localStringBuffer.append(" AND SUB_ROUTING_NUM is null ");
      } else {
        localStringBuffer.append(" AND SUB_ROUTING_NUM = ? ");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i1 = 1;
      localPreparedStatement.setString(i1++, paramBusiness.getBankId());
      localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
      localPreparedStatement.setString(i1++, paramAccount.getID());
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      int i2 = 0;
      if (localResultSet.next()) {
        i2 = localResultSet.getInt(1);
      }
      int i3 = i2;
      return i3;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static int getNumSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    String str = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getNumSubAccounts";
    a(paramAccount);
    a(paramBusiness);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    verifyInitialized();
    try
    {
      int i1 = 1;
      localConnection = DBUtil.getConnection(d, false, 2);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("SELECT count(*) FROM TD_ACCOUNTS_REL WHERE BANK_ID = ? AND BUS_DIRECTORY_ID = ? AND MASTER_ACCOUNT_ID = ?");
      if (paramAccount.getRoutingNum() == null) {
        localStringBuffer.append(" AND MASTER_ROUTING_NUM is null ");
      } else {
        localStringBuffer.append(" AND MASTER_ROUTING_NUM = ? ");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i1 = 1;
      localPreparedStatement.setString(i1++, paramBusiness.getBankId());
      localPreparedStatement.setInt(i1++, paramBusiness.getIdValue());
      localPreparedStatement.setString(i1++, paramAccount.getID());
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      int i2 = 0;
      if (localResultSet.next()) {
        i2 = localResultSet.getInt(1);
      }
      int i3 = i2;
      return i3;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static Accounts filterNonSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.filterNonSubAccounts";
    a(paramBusiness);
    for (int i1 = 0; i1 < paramAccounts.size(); i1++)
    {
      localObject1 = (Account)paramAccounts.get(i1);
      a((Account)localObject1);
    }
    Connection localConnection = null;
    Object localObject1 = null;
    ResultSet localResultSet = null;
    verifyInitialized();
    try
    {
      int i2 = 1;
      localConnection = DBUtil.getConnection(d, false, 2);
      localObject1 = DBUtil.prepareStatement(localConnection, "SELECT DISTINCT SUB_ROUTING_NUM, SUB_ACCOUNT_ID FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?");
      i2 = 1;
      ((PreparedStatement)localObject1).setString(i2++, paramBusiness.getBankId());
      ((PreparedStatement)localObject1).setInt(i2++, paramBusiness.getIdValue());
      localResultSet = DBUtil.executeQuery((PreparedStatement)localObject1, "SELECT DISTINCT SUB_ROUTING_NUM, SUB_ACCOUNT_ID FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?");
      Accounts localAccounts = new Accounts(paramAccounts.getLocale());
      while (localResultSet.next())
      {
        localObject2 = localResultSet.getString(1);
        String str2 = localResultSet.getString(2);
        for (int i3 = 0; i3 < paramAccounts.size(); i3++)
        {
          Account localAccount = (Account)paramAccounts.get(i3);
          if ((localAccount.getID().equals(str2)) && (a(localAccount.getRoutingNum(), (String)localObject2)))
          {
            localAccounts.add(localAccount);
            break;
          }
        }
      }
      Object localObject2 = localAccounts;
      return localObject2;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, (PreparedStatement)localObject1, localResultSet);
    }
  }
  
  public static HashMap getMasterSubStatistics(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getMasterSubStatistics";
    verifyInitialized();
    a(paramBusiness);
    for (int i1 = 0; i1 < paramAccounts.size(); i1++)
    {
      Account localAccount1 = (Account)paramAccounts.get(i1);
      a(localAccount1);
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("NumberOfAccounts", new Integer(paramAccounts.size()));
    int i2 = 0;
    for (int i3 = 0; i3 < paramAccounts.size(); i3++)
    {
      localObject1 = (Account)paramAccounts.get(i3);
      if (((Account)localObject1).isMaster()) {
        i2++;
      }
    }
    localHashMap.put("NumberOfMasterAccounts", new Integer(i2));
    Connection localConnection = null;
    Object localObject1 = null;
    ResultSet localResultSet = null;
    try
    {
      int i4 = 1;
      localConnection = DBUtil.getConnection(d, false, 2);
      localObject1 = DBUtil.prepareStatement(localConnection, "SELECT DISTINCT SUB_ROUTING_NUM, SUB_ACCOUNT_ID FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?");
      i4 = 1;
      ((PreparedStatement)localObject1).setString(i4++, paramBusiness.getBankId());
      ((PreparedStatement)localObject1).setInt(i4++, paramBusiness.getIdValue());
      localResultSet = DBUtil.executeQuery((PreparedStatement)localObject1, "SELECT DISTINCT SUB_ROUTING_NUM, SUB_ACCOUNT_ID FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?");
      int i5 = 0;
      Object localObject2;
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        String str3 = localResultSet.getString(2);
        for (int i6 = 0; i6 < paramAccounts.size(); i6++)
        {
          localObject2 = (Account)paramAccounts.get(i6);
          if ((((Account)localObject2).getID().equals(str3)) && (a(((Account)localObject2).getRoutingNum(), str2)))
          {
            i5++;
            break;
          }
        }
      }
      DBUtil.closeAll((PreparedStatement)localObject1, localResultSet);
      localResultSet = null;
      localObject1 = null;
      localHashMap.put("NumberOfSubAccounts", new Integer(i5));
      boolean bool1 = false;
      if ((i2 > 0) || (i5 > 0)) {
        bool1 = true;
      }
      localHashMap.put("AnyAccountMasterOrSub", new Boolean(bool1));
      localObject1 = DBUtil.prepareStatement(localConnection, "SELECT SUB_ROUTING_NUM, SUB_ACCOUNT_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?");
      i4 = 1;
      ((PreparedStatement)localObject1).setString(i4++, paramBusiness.getBankId());
      ((PreparedStatement)localObject1).setInt(i4++, paramBusiness.getIdValue());
      localResultSet = DBUtil.executeQuery((PreparedStatement)localObject1, "SELECT SUB_ROUTING_NUM, SUB_ACCOUNT_ID, MASTER_ROUTING_NUM, MASTER_ACCOUNT_ID, INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP FROM TD_ACCOUNTS_REL WHERE BANK_ID=? AND BUS_DIRECTORY_ID=?");
      boolean bool2 = false;
      while ((localResultSet.next()) && (!bool2))
      {
        String str4 = localResultSet.getString(1);
        localObject2 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        String str6 = localResultSet.getString(4);
        String str7 = localResultSet.getString(5);
        String str8 = localResultSet.getString(6);
        for (int i7 = 0; i7 < paramAccounts.size(); i7++)
        {
          Account localAccount2 = (Account)paramAccounts.get(i7);
          if (((str7.equals("Y")) || (str8.equals("Y"))) && (((localAccount2.getID().equals(localObject2)) && (a(localAccount2.getRoutingNum(), str4))) || ((localAccount2.getID().equals(str6)) && (a(localAccount2.getRoutingNum(), str5)))))
          {
            bool2 = true;
            break;
          }
        }
      }
      localHashMap.put("AnyIncludeZBAInRollup", new Boolean(bool2));
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, (PreparedStatement)localObject1, localResultSet);
    }
    return localHashMap;
  }
  
  public static Accounts getMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws TDException
  {
    String str = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getMasterAccounts";
    if (paramSecureUser == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid secure user has been specified.");
      throw new TDException(80016, str + ": Invalid secure user has been specified.");
    }
    if (paramAccount == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid sub-account has been specified.");
      throw new TDException(80014, str + ": Invalid sub-account has been specified.");
    }
    if (paramBusiness == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid business has been specified.");
      throw new TDException(80013, str + ": Invalid business has been specified.");
    }
    verifyInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(d, false, 2);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.*  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.MASTER_ROUTING_NUM = a.routing_num AND b.MASTER_ACCOUNT_ID = a.account_id AND  b.BANK_ID = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.SUB_ROUTING_NUM = ? ");
      } else {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.*  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.MASTER_ROUTING_NUM = a.routing_num AND b.MASTER_ACCOUNT_ID = a.account_id AND  b.BANK_ID = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.SUB_ROUTING_NUM IS NULL ");
      }
      localPreparedStatement.setString(1, paramAccount.getBankID());
      localPreparedStatement.setString(2, paramAccount.getID());
      localPreparedStatement.setInt(3, paramBusiness.getIdValue());
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(4, paramAccount.getRoutingNum());
      }
      if (paramAccount.getRoutingNum() != null) {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.*  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.MASTER_ROUTING_NUM = a.routing_num AND b.MASTER_ACCOUNT_ID = a.account_id AND  b.BANK_ID = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.SUB_ROUTING_NUM = ? ");
      } else {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.*  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.MASTER_ROUTING_NUM = a.routing_num AND b.MASTER_ACCOUNT_ID = a.account_id AND  b.BANK_ID = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.SUB_ROUTING_NUM IS NULL ");
      }
      Accounts localAccounts = new Accounts();
      if ((paramSecureUser != null) && (paramSecureUser.getLocale() != null)) {
        localAccounts.setLocale(paramSecureUser.getLocale());
      }
      while (localResultSet.next())
      {
        localObject1 = ProfileUtil.processAccountRS(localResultSet, paramAccount.getBankID());
        localAccounts.add(localObject1);
      }
      Object localObject1 = localAccounts;
      return localObject1;
    }
    catch (TDException localTDException)
    {
      DBUtil.rollback(localConnection);
      throw localTDException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static Account getSubAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount1, Account paramAccount2, HashMap paramHashMap)
    throws TDException
  {
    String str = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getMasterAccounts";
    if (paramSecureUser == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid secure user has been specified.");
      throw new TDException(80016, str + ": Invalid secure user has been specified.");
    }
    if (paramAccount1 == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid master account has been specified.");
      throw new TDException(80015, str + ": Invalid master account has been specified.");
    }
    if (paramAccount2 == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid sub-account has been specified.");
      throw new TDException(80014, str + ": Invalid sub-account has been specified.");
    }
    if (paramBusiness == null)
    {
      DebugLog.log(Level.FINE, str + ": Invalid business has been specified.");
      throw new TDException(80013, str + ": Invalid business has been specified.");
    }
    verifyInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(d, false, 2);
      if ((paramAccount1.getRoutingNum() != null) && (paramAccount2.getRoutingNum() != null))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM = ? AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
        localPreparedStatement.setString(1, paramAccount1.getRoutingNum());
        localPreparedStatement.setString(2, paramAccount1.getID());
        localPreparedStatement.setString(3, paramAccount2.getRoutingNum());
        localPreparedStatement.setString(4, paramAccount2.getID());
        localPreparedStatement.setInt(5, paramBusiness.getIdValue());
        localPreparedStatement.setString(6, paramBusiness.getBankId());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM = ? AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
      }
      else if (paramAccount1.getRoutingNum() != null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM = ? AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM IS NULL AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
        localPreparedStatement.setString(1, paramAccount1.getRoutingNum());
        localPreparedStatement.setString(2, paramAccount1.getID());
        localPreparedStatement.setString(3, paramAccount2.getID());
        localPreparedStatement.setInt(4, paramBusiness.getIdValue());
        localPreparedStatement.setString(5, paramBusiness.getBankId());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM = ? AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM IS NULL AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
      }
      else if (paramAccount2.getRoutingNum() != null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM IS NULL AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
        localPreparedStatement.setString(1, paramAccount1.getID());
        localPreparedStatement.setString(2, paramAccount2.getRoutingNum());
        localPreparedStatement.setString(3, paramAccount2.getID());
        localPreparedStatement.setInt(4, paramBusiness.getIdValue());
        localPreparedStatement.setString(5, paramBusiness.getBankId());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM IS NULL AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM = ? AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM IS NULL AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM IS NULL AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
        localPreparedStatement.setString(1, paramAccount1.getID());
        localPreparedStatement.setString(2, paramAccount2.getID());
        localPreparedStatement.setInt(3, paramBusiness.getIdValue());
        localPreparedStatement.setString(4, paramBusiness.getBankId());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.*, b.LOCATION_ID as location_id_2, b.INC_ZBACR_INROLLUP as inc_zbacr_inrollup_2, b.INC_ZBADB_INROLLUP as inc_zbadb_inrollup_2  FROM accounts a, TD_ACCOUNTS_REL b  WHERE b.SUB_ROUTING_NUM = a.routing_num AND b.SUB_ACCOUNT_ID = a.account_id AND  b.MASTER_ROUTING_NUM IS NULL AND b.MASTER_ACCOUNT_ID = ? AND b.SUB_ROUTING_NUM IS NULL AND b.SUB_ACCOUNT_ID = ? AND b.BUS_DIRECTORY_ID = ? AND b.BANK_ID = ? ");
      }
      if (localResultSet.next())
      {
        localAccount1 = ProfileUtil.processAccountRS(localResultSet, paramBusiness.getBankId());
        localAccount1.setLocationID(localResultSet.getString("location_id_2"));
        localAccount1.setIncludeZBACreditInRollup(localResultSet.getString("inc_zbacr_inrollup_2").equalsIgnoreCase("y"));
        localAccount1.setIncludeZBADebitInRollup(localResultSet.getString("inc_zbadb_inrollup_2").equalsIgnoreCase("y"));
        Account localAccount2 = localAccount1;
        return localAccount2;
      }
      Account localAccount1 = null;
      return localAccount1;
    }
    catch (TDException localTDException)
    {
      DBUtil.rollback(localConnection);
      throw localTDException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localException);
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getReportData";
    String str2 = null;
    Locale localLocale = paramSecureUser.getLocale();
    ReportResult localReportResult = null;
    if ((paramSecureUser == null) || (paramReportCriteria == null) || (paramHashMap == null))
    {
      localObject1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, (String)localObject1);
      throw new TDException(80008, (String)localObject1);
    }
    Object localObject1 = paramReportCriteria.getSearchCriteria();
    if (localObject1 == null)
    {
      localObject2 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new TDException(80008, (String)localObject2);
    }
    Object localObject2 = paramReportCriteria.getSortCriteria();
    if (localObject2 == null)
    {
      localObject3 = "A sort criteria object was not found within the given report criteria.  Without a sort criteria object, a report cannot be run.";
      throw new TDException(80008, (String)localObject3);
    }
    Object localObject3 = paramReportCriteria.getReportOptions();
    if (localObject3 == null)
    {
      str3 = "The report criteria used in a call to getReportData did not contain a valid report options object. This object is required for report retrieval.";
      throw new TDException(80008, str3);
    }
    str2 = ((Properties)localObject3).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      str3 = "The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.";
      throw new TDException(80008, str3);
    }
    if ((str2.equals("BC Non-zero Balance Report")) || (str2.equals("Information Reporting - Non-zero Balance Report")))
    {
      localReportResult = (ReportResult)a(paramSecureUser, paramReportCriteria, paramHashMap);
      return localReportResult;
    }
    String str3 = "The report has an invalide type";
    throw new TDException(80008, str3);
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws TDException
  {
    String str1 = "com.ffusion.treasurydirect.TreasuryDirectAdapter.getNonZeroReportData";
    String str2 = null;
    String str3 = null;
    int i1 = 0;
    int i2 = -1;
    boolean bool = false;
    Locale localLocale = paramSecureUser.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    ResultSet localResultSet = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    str2 = localProperties1.getProperty("REPORTTYPE");
    if (str2.equals("BC Non-zero Balance Report")) {
      bool = true;
    } else if (str2.equals("Information Reporting - Non-zero Balance Report")) {
      bool = false;
    }
    str3 = localProperties1.getProperty("MAXDISPLAYSIZE");
    if (str3 != null) {
      i2 = Integer.parseInt(str3);
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      localConnection = DBUtil.getConnection(d, false, 2);
      Object localObject1;
      Object localObject2;
      Object localObject3;
      ArrayList localArrayList;
      if (bool)
      {
        localObject1 = (AffiliateBanks)paramHashMap.get("AffiliateBankCollection");
        if (localObject1 == null)
        {
          localObject2 = "The hashmap used in a call to getReportData did not contain an AffiliateBanks collection. This object is needed for BC report retrieval.";
          DebugLog.log((String)localObject2);
          throw new TDException(80008, (String)localObject2);
        }
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select business_id, business_name, cust_id, bank_id from business b, customer_directory c where b.business_id = c.directory_id and affiliate_bank_id = ? order by business_name");
        localObject2 = ((AffiliateBanks)localObject1).iterator();
        localObject3 = null;
        while (((Iterator)localObject2).hasNext())
        {
          Object localObject4 = null;
          localArrayList = new ArrayList();
          localObject3 = (AffiliateBank)((Iterator)localObject2).next();
          String str4 = ((AffiliateBank)localObject3).getAffiliateBankName();
          String str5 = ((AffiliateBank)localObject3).getAffiliateRoutingNum();
          Object[] arrayOfObject = { str4, str5 };
          String str6 = MessageFormat.format(ReportConsts.getText(10600, localLocale), arrayOfObject);
          Businesses localBusinesses = new Businesses(localLocale);
          String str7 = localProperties2.getProperty("Business");
          if (!str7.equals("AllBusinesses"))
          {
            localBusinesses = (Businesses)paramHashMap.get("BusinessCollection");
          }
          else
          {
            localPreparedStatement.setInt(1, ((AffiliateBank)localObject3).getAffiliateBankID());
            localResultSet = DBUtil.executeQuery(localPreparedStatement, "select business_id, business_name, cust_id, bank_id from business b, customer_directory c where b.business_id = c.directory_id and affiliate_bank_id = ? order by business_name");
            localBusinesses = a(localResultSet, ((AffiliateBank)localObject3).getAffiliateBankID(), localLocale);
          }
          if (localBusinesses == null)
          {
            String str8 = "Businesses collection is null when trying to retrieve the data for BC Non-zero Balance Report";
            DebugLog.log(str8);
            throw new TDException(80008, str8);
          }
          int i4 = 0;
          Business localBusiness = null;
          Iterator localIterator = localBusinesses.iterator();
          while (localIterator.hasNext())
          {
            localBusiness = (Business)localIterator.next();
            if (localBusiness.getAffiliateBankID() == ((AffiliateBank)localObject3).getAffiliateBankID())
            {
              localArrayList.add(0, localObject4);
              i4 = a(paramSecureUser, paramReportCriteria, paramHashMap, localBusiness, str6, localArrayList, localConnection, i2, localReportResult, i1, bool);
              localObject4 = (ReportResult)localArrayList.get(0);
              i1 += i4;
              if (a(i2, i1, null)) {
                break;
              }
            }
          }
          if (a(i2, i1, null)) {
            break;
          }
          DBUtil.closeResultSet(localResultSet);
          localResultSet = null;
        }
      }
      else
      {
        localObject1 = (Businesses)paramHashMap.get("BusinessCollection");
        if (localObject1 == null)
        {
          localObject2 = "The hashmap used in a call to getReportData did not contain an Businesses collection. This object is needed for CB report retrieval.";
          DebugLog.log((String)localObject2);
          throw new TDException(80008, (String)localObject2);
        }
        localObject2 = ((Businesses)localObject1).iterator();
        localObject3 = null;
        localObject3 = (Business)((Iterator)localObject2).next();
        localArrayList = new ArrayList();
        int i3 = a(paramSecureUser, paramReportCriteria, paramHashMap, (Business)localObject3, null, localArrayList, localConnection, i2, localReportResult, i1, bool);
        i1 += i3;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localNumberFormatException);
    }
    catch (Exception localException1)
    {
      localReportResult.setError(localException1);
      DebugLog.log(Level.FINE, str1 + ": " + localException1.getMessage());
      DBUtil.rollback(localConnection);
      throw new TDException(80000, localException1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(d, localConnection);
      try
      {
        localReportResult.fini();
      }
      catch (Exception localException2) {}
    }
    return localReportResult;
  }
  
  private static int a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap, Business paramBusiness, String paramString, ArrayList paramArrayList, Connection paramConnection, int paramInt1, ReportResult paramReportResult, int paramInt2, boolean paramBoolean)
    throws TDException
  {
    String str1 = "TreasuryDirectAdapter.retrieveReportData";
    Locale localLocale = paramSecureUser.getLocale();
    ResultSet localResultSet = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    int i1 = 0;
    Connection localConnection = null;
    try
    {
      Accounts localAccounts1 = AccountAdapter.getAccountsById(Integer.parseInt(paramBusiness.getId()));
      HashMap localHashMap = new HashMap();
      localObject1 = new HashMap();
      Vector localVector = new Vector();
      if ((localAccounts1 == null) || (localAccounts1.isEmpty()))
      {
        int i2 = i1;
        return i2;
      }
      Reporting.calculateDateRange(paramSecureUser, localAccounts1, paramReportCriteria, localHashMap, (HashMap)localObject1, paramHashMap);
      localResultSet = a(arrayOfPreparedStatement, localHashMap, (HashMap)localObject1, localVector, paramConnection, localAccounts1, paramInt1);
      ReportResult localReportResult1 = null;
      localConnection = DCAdapter.getDBConnection();
      int i3 = 0;
      int i4 = 0;
      int i5 = 0;
      ReportResult localReportResult2 = null;
      String str2 = paramReportCriteria.getSearchCriteria().getProperty("Display");
      while (localResultSet.next())
      {
        Timestamp localTimestamp = localResultSet.getTimestamp("DataDate");
        DateTime localDateTime = new DateTime(localTimestamp, localLocale);
        String str3 = localResultSet.getString(5);
        String str4 = localResultSet.getString(3);
        String str5 = localResultSet.getString(4);
        Account localAccount = localAccounts1.getByIDAndBankIDAndRoutingNum(str3, str4, str5);
        Accounts localAccounts2 = DCAdapter.getMasterAccounts(localConnection, localDateTime, Integer.parseInt(paramBusiness.getId()), localAccount, paramHashMap);
        if ((localAccounts2 != null) && (localAccounts2.size() != 0))
        {
          Object localObject3;
          Object localObject4;
          if (paramBoolean)
          {
            ReportResult localReportResult3 = (ReportResult)paramArrayList.get(0);
            Object localObject2;
            if (localReportResult3 == null)
            {
              localReportResult3 = new ReportResult(localLocale);
              paramReportResult.addSubReport(localReportResult3);
              localObject2 = new ReportHeading(localLocale);
              ((ReportHeading)localObject2).setLabel(paramString);
              localReportResult3.setHeading((ReportHeading)localObject2);
              paramArrayList.add(0, localReportResult3);
            }
            if (i3 == 0)
            {
              localReportResult1 = new ReportResult(localLocale);
              localReportResult3.addSubReport(localReportResult1);
              paramArrayList.add(localReportResult1);
              localObject2 = paramBusiness.getCustId();
              if (!((String)localObject2).equals(" "))
              {
                localObject3 = new Object[] { paramBusiness.getBusinessName(), paramBusiness.getCustId() };
                localObject4 = new ReportHeading(localLocale);
                ((ReportHeading)localObject4).setLabel(MessageFormat.format(ReportConsts.getText(10601, localLocale), (Object[])localObject3));
                localReportResult1.setHeading((ReportHeading)localObject4);
              }
              else
              {
                localObject3 = new Object[] { paramBusiness.getBusinessName() };
                localObject4 = new ReportHeading(localLocale);
                ((ReportHeading)localObject4).setLabel(MessageFormat.format(ReportConsts.getText(10605, localLocale), (Object[])localObject3));
                localReportResult1.setHeading((ReportHeading)localObject4);
              }
              i3 = 1;
            }
          }
          else if (i4 == 0)
          {
            localReportResult1 = new ReportResult(localLocale);
            paramReportResult.addSubReport(localReportResult1);
            i4 = 1;
          }
          int i6;
          if (i5 == 0)
          {
            if (str2.equals("DisplaySummaryOnly"))
            {
              localReportResult2 = new ReportResult(localLocale);
              localReportResult1.addSubReport(localReportResult2);
              i6 = a(localReportResult2, localLocale);
              a(localReportResult2, i6, -1);
            }
            i5 = 1;
          }
          if (str2.equals("DisplaySummaryAndDetail"))
          {
            localReportResult2 = new ReportResult(localLocale);
            localReportResult1.addSubReport(localReportResult2);
            i6 = a(localReportResult2, localLocale);
            a(localReportResult2, i6, -1);
          }
          Accounts localAccounts3 = new Accounts(localLocale);
          for (int i7 = 0; i7 < localAccounts2.size(); i7++)
          {
            localObject3 = (Account)localAccounts2.get(i7);
            localObject4 = localAccounts1.getByIDAndBankIDAndRoutingNum(((Account)localObject3).getID(), ((Account)localObject3).getBankID(), ((Account)localObject3).getRoutingNum());
            if (localObject4 != null) {
              localAccounts3.add(localObject4);
            }
          }
          if ((localAccounts3 == null) || (localAccounts3.size() == 0))
          {
            String str6 = "Master Accounts collection incorrect from retrieving Non zero balance report";
            throw new TDException(80000, str6);
          }
          localAccounts3.setSortedBy("ROUTINGNUM,ID");
          int i8 = a(localReportResult2, localResultSet, localAccounts3, localAccount, paramReportCriteria, paramInt1, paramReportResult, paramInt2);
          i1 += i8;
          paramInt2 += i8;
          if (a(paramInt1, paramInt2, null)) {
            break;
          }
          if (str2.equals("DisplaySummaryAndDetail"))
          {
            localObject3 = (Calendar)localHashMap.get(localAccount.getRoutingNum());
            localObject4 = (Calendar)((HashMap)localObject1).get(localAccount.getRoutingNum());
            String str7 = paramReportCriteria.getSearchCriteria().getProperty("DataClassification");
            paramHashMap.put("DATA_CLASSIFICATION", str7);
            Transactions localTransactions = DCAdapter.getTransactions(localAccount, localDateTime, localDateTime, localConnection, paramHashMap);
            if (localTransactions != null)
            {
              int i9 = a(localReportResult1, paramInt1, localTransactions, paramReportResult, paramInt2);
              i1 += i9;
              paramInt2 += i9;
              if (a(paramInt1, paramInt2, null)) {
                break;
              }
            }
          }
        }
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Object localObject1 = "The search critiera specifying the id of account to query is missing or its format is not value for Non-zero Balance Report.";
      throw new NumberFormatException((String)localObject1);
    }
    catch (ProfileException localProfileException) {}catch (Exception localException1)
    {
      DebugLog.log(Level.FINE, str1 + ": " + localException1.getMessage());
      throw new TDException(80000, localException1);
    }
    finally
    {
      DBUtil.closeAll(arrayOfPreparedStatement[0], localResultSet);
      try
      {
        DCAdapter.releaseDBConnection(localConnection);
      }
      catch (Exception localException2) {}
      localResultSet = null;
    }
    return i1;
  }
  
  private static int a(ReportResult paramReportResult1, int paramInt1, Transactions paramTransactions, ReportResult paramReportResult2, int paramInt2)
    throws TDException
  {
    int i1 = 0;
    try
    {
      Locale localLocale = paramReportResult1.getLocale();
      localObject1 = null;
      int i2 = 0;
      int i3 = 100;
      int i4 = 0;
      String str1 = ReportConsts.getText(731, localLocale);
      String str2 = ReportConsts.getText(732, localLocale);
      String str3 = ReportConsts.getText(733, localLocale);
      Iterator localIterator = paramTransactions.iterator();
      boolean bool = true;
      for (int i5 = 1; localIterator.hasNext(); i5++)
      {
        Transaction localTransaction = (Transaction)localIterator.next();
        i1++;
        paramInt2++;
        if (a(paramInt1, paramInt2, paramReportResult2)) {
          break;
        }
        if (i2 == 0)
        {
          i2 = 1;
          localObject1 = new ReportResult(localLocale);
          paramReportResult1.addSubReport((ReportResult)localObject1);
          a((ReportResult)localObject1, 0, 0);
        }
        ReportResult localReportResult = new ReportResult(localLocale);
        ((ReportResult)localObject1).addSubReport(localReportResult);
        ReportHeading localReportHeading = new ReportHeading(localLocale);
        localReportResult.setHeading(null);
        int i6 = a(localReportResult, localLocale, bool);
        bool = false;
        a(localReportResult, i6, -1);
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        ReportDataItem localReportDataItem = null;
        int i7 = localTransaction.getSubTypeValue();
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i7);
        }
        catch (CSILException localCSILException) {}
        if (localBAITypeCodeInfo != null) {
          a(localBAITypeCodeInfo.getDescription(localLocale), localReportDataItems, localReportDataItem);
        } else {
          a(" ", localReportDataItems, localReportDataItem);
        }
        a(localTransaction.getCustomerReferenceNumber(), localReportDataItems, localReportDataItem);
        a(localTransaction.getBankReferenceNumber(), localReportDataItems, localReportDataItem);
        Currency localCurrency1 = localTransaction.getCreditValue();
        if (localCurrency1 != null) {
          a(a(localCurrency1), localReportDataItems, localReportDataItem);
        } else {
          a(" ", localReportDataItems, localReportDataItem);
        }
        Currency localCurrency2 = localTransaction.getDebitValue();
        if (localCurrency2 != null) {
          a(a(localCurrency2), localReportDataItems, localReportDataItem);
        } else {
          a(" ", localReportDataItems, localReportDataItem);
        }
        a(localReportResult, localReportDataItems, i5);
        localReportDataItems = null;
        Object localObject2 = localTransaction.getHash().entrySet().iterator();
        Object localObject3;
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (Map.Entry)((Iterator)localObject2).next();
          if (localReportDataItems == null)
          {
            localReportDataItems = new ReportDataItems(localLocale);
            for (int i8 = i6; i8 > 1; i8--) {
              localReportDataItems.add(null);
            }
          }
          localReportDataItems.add().setData(((Map.Entry)localObject3).getKey() + " : " + ((Map.Entry)localObject3).getValue());
          a(localReportResult, localReportDataItems, i5);
          localReportDataItems = null;
        }
        if (localReportDataItems != null)
        {
          localReportDataItems.add(null);
          a(localReportResult, localReportDataItems, i5);
        }
        Object localObject4;
        if ((localTransaction.getImmediateAvailAmount() != null) || (localTransaction.getOneDayAvailAmount() != null) || (localTransaction.getMoreThanOneDayAvailAmount() != null))
        {
          localReportResult = new ReportResult(localLocale);
          ((ReportResult)localObject1).addSubReport(localReportResult);
          localReportHeading = new ReportHeading(localLocale);
          localReportResult.setHeading(null);
          a(localReportResult, 1, -1);
          localObject2 = new ReportColumn(localLocale);
          localObject2 = new ReportColumn(localLocale);
          ((ReportColumn)localObject2).setLabels(null);
          ((ReportColumn)localObject2).setDataType("java.lang.String");
          ((ReportColumn)localObject2).setWidthAsPercent(i3);
          localReportResult.addColumn((ReportColumn)localObject2);
          localObject3 = new StringBuffer(40);
          localObject4 = null;
          if (localTransaction.getImmediateAvailAmount() != null)
          {
            localObject4 = localTransaction.getImmediateAvailAmount();
            ((StringBuffer)localObject3).append(str1);
          }
          else if (localTransaction.getOneDayAvailAmount() != null)
          {
            localObject4 = localTransaction.getOneDayAvailAmount();
            ((StringBuffer)localObject3).append(str2);
          }
          else if (localTransaction.getMoreThanOneDayAvailAmount() != null)
          {
            localObject4 = localTransaction.getMoreThanOneDayAvailAmount();
            ((StringBuffer)localObject3).append(str3);
          }
          localReportDataItems = new ReportDataItems(localLocale);
          ((StringBuffer)localObject3).append(": " + ((Currency)localObject4).getCurrencyStringNoSymbol());
          a(((StringBuffer)localObject3).toString(), localReportDataItems, localReportDataItem);
          a(localReportResult, localReportDataItems, i5);
        }
        localObject2 = localTransaction.getDescription();
        if ((localObject2 != null) && (((String)localObject2).trim().length() > 0))
        {
          localReportResult = new ReportResult(localLocale);
          ((ReportResult)localObject1).addSubReport(localReportResult);
          localReportHeading = new ReportHeading(localLocale);
          localReportResult.setHeading(null);
          a(localReportResult, 1, -1);
          localObject3 = new ReportColumn(localLocale);
          localObject3 = new ReportColumn(localLocale);
          ((ReportColumn)localObject3).setLabels(null);
          ((ReportColumn)localObject3).setDataType("java.lang.String");
          ((ReportColumn)localObject3).setWidthAsPercent(i3);
          localReportResult.addColumn((ReportColumn)localObject3);
          localObject4 = new StringBuffer(ReportConsts.getText(1102, localLocale));
          ((StringBuffer)localObject4).append(": ");
          ((StringBuffer)localObject4).append((String)localObject2);
          localReportDataItems = new ReportDataItems(localLocale);
          a(((StringBuffer)localObject4).toString(), localReportDataItems, localReportDataItem);
          a(localReportResult, localReportDataItems, i5);
          localReportDataItems = null;
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = "Rows could not be added to Non-zero Balance Report (Detail)";
      DebugLog.throwing((String)localObject1, localException);
      throw new TDException(80008, (String)localObject1, localException);
    }
    return i1;
  }
  
  private static int a(ReportResult paramReportResult1, ResultSet paramResultSet, Accounts paramAccounts, Account paramAccount, ReportCriteria paramReportCriteria, int paramInt1, ReportResult paramReportResult2, int paramInt2)
    throws TDException
  {
    Locale localLocale = paramReportResult1.getLocale();
    int i1 = 0;
    Timestamp localTimestamp = null;
    DateTime localDateTime = new DateTime(localLocale);
    String str1 = paramReportCriteria.getSearchCriteria().getProperty("Display");
    int i2;
    if (str1.equals("DisplaySummaryAndDetail")) {
      i2 = 0;
    } else {
      i2 = paramInt2;
    }
    try
    {
      ReportRow localReportRow1 = new ReportRow(localLocale);
      localObject1 = new ReportDataItems(localLocale);
      ReportDataItem localReportDataItem1 = null;
      localTimestamp = paramResultSet.getTimestamp(1);
      localDateTime = new DateTime(localTimestamp, localLocale);
      localReportDataItem1 = ((ReportDataItems)localObject1).add();
      localReportDataItem1.setData(localDateTime);
      localTimestamp = paramResultSet.getTimestamp(2);
      localDateTime = new DateTime(localTimestamp, localLocale);
      localReportDataItem1 = ((ReportDataItems)localObject1).add();
      localReportDataItem1.setData(localDateTime);
      int i3 = 0;
      String str2 = paramReportCriteria.getSearchCriteria().getProperty("Rolled-up NZB Accounts");
      if (str2.equals("Include")) {
        i3 = 1;
      } else if (str2.equals("Exclude")) {
        i3 = 0;
      }
      int i4 = 0;
      Iterator localIterator = paramAccounts.iterator();
      String str4 = ResourceUtil.getString("WithholdNonZeroBalanceSubAccounts_True", "com.ffusion.beans.accounts.resources", localLocale);
      Account localAccount;
      while (localIterator.hasNext())
      {
        localAccount = (Account)localIterator.next();
        str3 = a(localAccount.getRoutingNum(), localAccount.getID(), localAccount.getNickName(), localAccount.getCurrencyCode(), localLocale);
        if (i3 == 0)
        {
          if (localAccount.getWithholdNonZeroBalanceSubAccounts().equals(str4))
          {
            a(str3, (ReportDataItems)localObject1, localReportDataItem1);
            i4 = 1;
          }
        }
        else
        {
          a(str3, (ReportDataItems)localObject1, localReportDataItem1);
          i4 = 1;
        }
      }
      if (i4 == 0) {
        a(" ", (ReportDataItems)localObject1, localReportDataItem1);
      }
      String str5 = paramResultSet.getString(4);
      String str6 = paramResultSet.getString(5);
      String str3 = a(str5, str6, paramAccount.getNickName(), paramAccount.getCurrencyCode(), localLocale);
      a(str3, (ReportDataItems)localObject1, localReportDataItem1);
      BigDecimal localBigDecimal = paramResultSet.getBigDecimal(6);
      Object localObject2;
      String str7;
      if (localBigDecimal != null)
      {
        localObject2 = new Currency(localBigDecimal, paramAccount.getCurrencyCode(), localLocale);
        str7 = ((Currency)localObject2).toString();
      }
      else
      {
        localObject2 = new Object[0];
        str7 = MessageFormat.format(ReportConsts.getText(10602, localLocale), (Object[])localObject2);
      }
      a(str7, (ReportDataItems)localObject1, localReportDataItem1);
      i1++;
      localReportRow1.setDataItems((ReportDataItems)localObject1);
      if (i2 % 2 == 1) {
        localReportRow1.set("CELLBACKGROUND", "reportDataShaded");
      }
      i2++;
      paramReportResult1.addRow(localReportRow1);
      if (i4 != 0) {
        while (localIterator.hasNext())
        {
          int i5 = 0;
          ReportRow localReportRow2 = new ReportRow(localLocale);
          ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
          ReportDataItem localReportDataItem2 = null;
          a(null, localReportDataItems, localReportDataItem2);
          a(null, localReportDataItems, localReportDataItem2);
          localAccount = (Account)localIterator.next();
          str3 = a(localAccount.getRoutingNum(), localAccount.getID(), localAccount.getNickName(), localAccount.getCurrencyCode(), localLocale);
          if (i3 == 0)
          {
            if (localAccount.getWithholdNonZeroBalanceSubAccounts().equals(str4))
            {
              a(str3, localReportDataItems, localReportDataItem2);
              i5 = 1;
            }
          }
          else
          {
            a(str3, localReportDataItems, localReportDataItem2);
            i5 = 1;
          }
          a(" ", localReportDataItems, localReportDataItem2);
          a(" ", localReportDataItems, localReportDataItem2);
          if (i5 != 0)
          {
            i1++;
            localReportRow2.setDataItems(localReportDataItems);
            if (i2 % 2 == 1) {
              localReportRow2.set("CELLBACKGROUND", "reportDataShaded");
            }
            i2++;
            paramReportResult1.addRow(localReportRow2);
          }
        }
      }
      paramInt2++;
      if (a(paramInt1, paramInt2, paramReportResult2)) {
        return i1;
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = "Rows could not be added to Non-zero Balance Report (Summary)";
      DebugLog.throwing((String)localObject1, localException);
      throw new TDException(80008, (String)localObject1, localException);
    }
    return i1;
  }
  
  private static ResultSet a(PreparedStatement[] paramArrayOfPreparedStatement, HashMap paramHashMap1, HashMap paramHashMap2, Vector paramVector, Connection paramConnection, Accounts paramAccounts, int paramInt)
    throws Exception
  {
    ResultSet localResultSet = null;
    try
    {
      String str1 = a(paramVector, paramHashMap1, paramHashMap2, paramAccounts);
      localObject = new StringBuffer();
      ((StringBuffer)localObject).append("select dcAcctHistory.DataDate, dcAcctHistory.DataSourceFileDate, dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.AccountID, dcAcctHistory.ClosingLedger from DC_AccountHistory dcAcctHistory, DC_Account dcAccount where ( ");
      ((StringBuffer)localObject).append(str1);
      ((StringBuffer)localObject).append(" ) AND ( dcAcctHistory.DCAccountID = dcAccount.DCAccountID)  AND (dcAcctHistory.ClosingLedger is null OR dcAcctHistory.ClosingLedger != 0) ) order by dcAcctHistory.DataDate, dcAccount.RoutingNumber, dcAccount.AccountID");
      String str2 = ((StringBuffer)localObject).toString();
      paramArrayOfPreparedStatement[0] = DBUtil.prepareStatement(paramConnection, str2, 1004, 1007);
      try
      {
        if (paramInt != -1) {
          paramArrayOfPreparedStatement[0].setMaxRows(paramInt);
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new Exception("The report option: MaxDisplaySize, specified for the report is not a valid number ");
      }
      int i1 = 0;
      while (!paramVector.isEmpty())
      {
        Long localLong = (Long)paramVector.remove(0);
        long l1 = localLong.longValue();
        i1++;
        if (l1 > 0L) {
          paramArrayOfPreparedStatement[0].setTimestamp(i1, new Timestamp(l1));
        } else {
          paramArrayOfPreparedStatement[0].setNull(i1, 92);
        }
      }
      localResultSet = DBUtil.executeQuery(paramArrayOfPreparedStatement[0], str2);
    }
    catch (Exception localException)
    {
      Object localObject = "Query could not be run to get all the non-zero balance accounts for the Non-zero Balance Report.";
      throw new TDException(80000, (String)localObject, localException);
    }
    return localResultSet;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, String paramString4, Locale paramLocale)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      str2 = AccountUtil.buildAccountDisplayText(paramString2, paramLocale);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing("Error while constructing account display string.", localUtilException);
      str2 = paramString2;
    }
    int i1 = (paramString3 != null) && (paramString3.length() > 0) ? 1 : 0;
    int i2 = paramString4 != null ? 1 : 0;
    int i3 = (paramString1 != null) || (paramString1.length() >= 0) ? 1 : 0;
    if (i3 != 0)
    {
      localArrayList.add(paramString1);
      localArrayList.add(str2);
      if ((i1 != 0) && (i2 != 0)) {
        str1 = "Non_Zero_Balance_Report_Account_With_Routing_Number_Nickname_And_Currency_Code_Format";
      } else if (i1 != 0) {
        str1 = "Non_Zero_Balance_Report_Account_With_Routing_Number_Nickname_Format";
      } else if (i2 != 0) {
        str1 = "Non_Zero_Balance_Report_Account_With_Routing_Number_Currency_Code_Format";
      } else {
        str1 = "Non_Zero_Balance_Report_Account_With_Routing_Number_No_Other_Information_Format";
      }
    }
    else
    {
      localArrayList.add(str2);
      if ((i1 != 0) && (i2 != 0)) {
        str1 = "Non_Zero_Balance_Report_Account_With_Nickname_And_Currency_Code_Format";
      } else if (i1 != 0) {
        str1 = "Non_Zero_Balance_Report_Account_With_Nickname_Format";
      } else if (i2 != 0) {
        str1 = "Non_Zero_Balance_Report_Account_With_Currency_Code_Format";
      } else {
        str1 = "Non_Zero_Balance_Report_Account_With_No_Other_Information_Format";
      }
    }
    if (i1 != 0) {
      localArrayList.add(paramString3);
    }
    if (i2 != 0) {
      localArrayList.add(paramString4);
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", str1, localArrayList.toArray());
    return (String)localLocalizableString.localize(paramLocale);
  }
  
  private static void a(Object paramObject, ReportDataItems paramReportDataItems, ReportDataItem paramReportDataItem)
  {
    if (paramObject == null)
    {
      paramReportDataItems.add(null);
    }
    else
    {
      paramReportDataItem = paramReportDataItems.add();
      paramReportDataItem.setData(paramObject);
    }
  }
  
  private static String a(Vector paramVector, HashMap paramHashMap1, HashMap paramHashMap2, Accounts paramAccounts)
    throws TDException
  {
    if (paramHashMap1 == null)
    {
      localObject = "startDates in helper method arguments has not been specified.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new TDException(80008, (String)localObject);
    }
    if (paramHashMap2 == null)
    {
      localObject = "endDates in helper method arguments has not been specified.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new TDException(80008, (String)localObject);
    }
    if ((paramAccounts == null) || (paramAccounts.isEmpty()))
    {
      localObject = "accountsToQuery in helper method arguments has not been specified.";
      DebugLog.log(Level.FINE, (String)localObject);
      throw new TDException(80008, (String)localObject);
    }
    Object localObject = new StringBuffer("");
    ((StringBuffer)localObject).append("( ");
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      ((StringBuffer)localObject).append(a(localAccount, paramVector, (Calendar)paramHashMap1.get(localAccount.getRoutingNum()), (Calendar)paramHashMap2.get(localAccount.getRoutingNum())));
      if (localIterator.hasNext()) {
        ((StringBuffer)localObject).append(" OR ");
      }
    }
    return ((StringBuffer)localObject).toString();
  }
  
  private static String a(Account paramAccount, Vector paramVector, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("( ");
    localStringBuffer.append("dcAccount.AccountID");
    localStringBuffer.append(" = '");
    localStringBuffer.append(paramAccount.getID());
    localStringBuffer.append("' AND ");
    localStringBuffer.append("dcAccount.BankID");
    localStringBuffer.append(" = '");
    localStringBuffer.append(paramAccount.getBankID());
    if (paramAccount.getRoutingNum() != null)
    {
      localStringBuffer.append("' AND ");
      localStringBuffer.append("dcAccount.RoutingNumber");
      localStringBuffer.append(" = '");
      localStringBuffer.append(paramAccount.getRoutingNum());
      localStringBuffer.append("'");
    }
    else
    {
      localStringBuffer.append("' AND ");
      localStringBuffer.append("dcAccount.RoutingNumber");
      localStringBuffer.append(" IS NULL ");
    }
    if (paramCalendar1 != null)
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append("dcAcctHistory.DataDate");
      localStringBuffer.append(" >= ?");
      paramVector.add(new Long(paramCalendar1.getTime().getTime()));
    }
    if (paramCalendar2 != null)
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append("dcAcctHistory.DataDate");
      localStringBuffer.append(" <= ?");
      paramVector.add(new Long(paramCalendar2.getTime().getTime()));
    }
    localStringBuffer.append(" ) ");
    return localStringBuffer.toString();
  }
  
  private static int a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportColumn localReportColumn1 = new ReportColumn(paramLocale);
    localReportColumn1.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn1.setWidthAsPercent(13);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(2710, paramLocale));
    localReportColumn1.setLabels(localArrayList1);
    paramReportResult.addColumn((ReportColumn)localReportColumn1.clone());
    ReportColumn localReportColumn2 = new ReportColumn(paramLocale);
    localReportColumn2.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn2.setWidthAsPercent(13);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2711, paramLocale));
    localReportColumn2.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn2.clone());
    ReportColumn localReportColumn3 = new ReportColumn(paramLocale);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(29);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(2712, paramLocale));
    localReportColumn3.setLabels(localArrayList3);
    paramReportResult.addColumn((ReportColumn)localReportColumn3.clone());
    ReportColumn localReportColumn4 = new ReportColumn(paramLocale);
    localReportColumn4.setDataType("java.lang.String");
    localReportColumn4.setWidthAsPercent(29);
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(2713, paramLocale));
    localReportColumn4.setLabels(localArrayList4);
    paramReportResult.addColumn((ReportColumn)localReportColumn4.clone());
    ReportColumn localReportColumn5 = new ReportColumn(paramLocale);
    localReportColumn5.setJustification("RIGHT");
    localReportColumn5.setDataType("java.lang.String");
    localReportColumn5.setWidthAsPercent(16);
    ArrayList localArrayList5 = new ArrayList();
    localArrayList5.add(ReportConsts.getColumnName(2714, paramLocale));
    localReportColumn5.setLabels(localArrayList5);
    paramReportResult.addColumn((ReportColumn)localReportColumn5.clone());
    return paramReportResult.getColumns().size();
  }
  
  private static int a(ReportResult paramReportResult, Locale paramLocale, boolean paramBoolean)
    throws RptException
  {
    ReportColumn localReportColumn1 = new ReportColumn(paramLocale);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(20);
    ArrayList localArrayList1 = new ArrayList(1);
    if (paramBoolean) {
      localArrayList1.add(ReportConsts.getColumnName(2715, paramLocale));
    } else {
      localArrayList1 = null;
    }
    localReportColumn1.setLabels(localArrayList1);
    paramReportResult.addColumn((ReportColumn)localReportColumn1.clone());
    ReportColumn localReportColumn2 = new ReportColumn(paramLocale);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(20);
    ArrayList localArrayList2 = new ArrayList();
    if (paramBoolean) {
      localArrayList2.add(ReportConsts.getColumnName(2716, paramLocale));
    } else {
      localArrayList2 = null;
    }
    localReportColumn2.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn2.clone());
    ReportColumn localReportColumn3 = new ReportColumn(paramLocale);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(20);
    ArrayList localArrayList3 = new ArrayList();
    if (paramBoolean) {
      localArrayList3.add(ReportConsts.getColumnName(2717, paramLocale));
    } else {
      localArrayList3 = null;
    }
    localReportColumn3.setLabels(localArrayList3);
    paramReportResult.addColumn((ReportColumn)localReportColumn3.clone());
    ReportColumn localReportColumn4 = new ReportColumn(paramLocale);
    localReportColumn4.setJustification("RIGHT");
    localReportColumn4.setDataType("java.lang.String");
    localReportColumn4.setWidthAsPercent(20);
    ArrayList localArrayList4 = new ArrayList();
    if (paramBoolean) {
      localArrayList4.add(ReportConsts.getColumnName(2718, paramLocale));
    } else {
      localArrayList4 = null;
    }
    localReportColumn4.setLabels(localArrayList4);
    paramReportResult.addColumn((ReportColumn)localReportColumn4.clone());
    ReportColumn localReportColumn5 = new ReportColumn(paramLocale);
    localReportColumn5.setJustification("RIGHT");
    localReportColumn5.setDataType("java.lang.String");
    localReportColumn5.setWidthAsPercent(20);
    ArrayList localArrayList5 = new ArrayList();
    if (paramBoolean) {
      localArrayList5.add(ReportConsts.getColumnName(2719, paramLocale));
    } else {
      localArrayList5 = null;
    }
    localReportColumn5.setLabels(localArrayList5);
    paramReportResult.addColumn((ReportColumn)localReportColumn5.clone());
    return paramReportResult.getColumns().size();
  }
  
  private static boolean a(int paramInt1, int paramInt2, ReportResult paramReportResult)
  {
    if ((paramInt1 > 0) && (paramInt2 > paramInt1))
    {
      if (paramReportResult != null)
      {
        HashMap localHashMap = paramReportResult.getProperties();
        if (localHashMap == null) {
          localHashMap = new HashMap();
        }
        localHashMap.put("TRUNCATED", Integer.toString(250));
        paramReportResult.setProperties(localHashMap);
      }
      return true;
    }
    return false;
  }
  
  private static void a(ReportResult paramReportResult, ReportDataItems paramReportDataItems, int paramInt)
    throws RptException
  {
    ReportRow localReportRow = new ReportRow(paramReportResult.getLocale());
    if ((paramInt != -1) && (paramInt % 2 == 0)) {
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    }
    localReportRow.setDataItems(paramReportDataItems);
    paramReportResult.addRow(localReportRow);
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, int paramInt2)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramReportResult.getLocale());
    localReportDataDimensions.setNumColumns(paramInt1);
    localReportDataDimensions.setNumRows(paramInt2);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  private static Businesses a(ResultSet paramResultSet, int paramInt, Locale paramLocale)
    throws Exception
  {
    Businesses localBusinesses = new Businesses(paramLocale);
    while (paramResultSet.next())
    {
      int i1 = paramResultSet.getInt("business_id");
      String str2 = paramResultSet.getString("business_name");
      String str3 = paramResultSet.getString("cust_id");
      String str1 = paramResultSet.getString("bank_id");
      Business localBusiness = new Business(paramLocale);
      localBusiness.setId(i1);
      localBusiness.setBusinessName(str2);
      localBusiness.setCustId(str3);
      localBusiness.setBankId(str1);
      localBusiness.setAffiliateBankID(paramInt);
      localBusinesses.add(localBusiness);
    }
    return localBusinesses;
  }
  
  private static String a(Currency paramCurrency)
  {
    String str;
    if (paramCurrency.isNegative())
    {
      BigDecimal localBigDecimal = paramCurrency.getAmountValue();
      localBigDecimal = localBigDecimal.negate();
      Currency localCurrency = new Currency(localBigDecimal, paramCurrency.getCurrencyCode(), paramCurrency.getLocale());
      str = localCurrency.getCurrencyStringNoSymbol_1();
    }
    else
    {
      str = paramCurrency.getCurrencyStringNoSymbol_1();
    }
    return str;
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    if (paramString1 == paramString2) {
      return true;
    }
    if (paramString1 == null) {
      return false;
    }
    return paramString1.equals(paramString2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.treasurydirect.TreasuryDirectAdapter
 * JD-Core Version:    0.7.0.1
 */