package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.Contact;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Businesses;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AccountAdapter
  implements ProfileDefines
{
  private static final String aiq = "insert into accounts(directory_id,account_id,CONTACT_ID,nickname,hide,export_begin_date,export_end_date,bank_email_token,positive_pay,routing_num,bic_account,primary_account,core_account,personal_account,currency_type,bank_name,ZBAFLAG,SHOWPREVOPENLEDGER,ACCOUNT_TYPE,ACCOUNT_NUM,IS_MASTER,INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP,WITHHOLD_NZB_SUB,STRIPPED_ACCOUNT_NUM";
  private static final String ah9 = "select * from accounts ";
  private static final String aii = "select * from accounts where routing_num=? and account_id=?";
  private static final String aie = "select * from accounts where account_id = ? and routing_num is null";
  private static final String ah8 = "select * from accounts where routing_num is null and ACCOUNT_NUM = ?";
  private static final String aio = "select * from accounts where routing_num=? and ACCOUNT_NUM = ?";
  private static final String aij = "select * from accounts where ACCOUNT_NUM = ?";
  private static final String aip = "select * from accounts where directory_id=?";
  private static final String aik = "delete from accounts where directory_id=?";
  private static final String aig = "update accounts set CONTACT_ID=?,positive_pay=?,ZBAFLAG=?,primary_account=?,SHOWPREVOPENLEDGER=?,nickname=?,hide=?,export_begin_date=?,export_end_date=?,IS_MASTER=?,INC_ZBACR_INROLLUP=?,INC_ZBADB_INROLLUP=?,WITHHOLD_NZB_SUB=?";
  private static final String ah6 = "update accounts set CONTACT_ID=?,positive_pay=?,ZBAFLAG=?,primary_account=?,nickname=?,hide=?,export_begin_date=?,export_end_date=?,IS_MASTER=?,INC_ZBACR_INROLLUP=?,INC_ZBADB_INROLLUP=?,WITHHOLD_NZB_SUB=?";
  private static final String aib = " where directory_id=? and account_id=?";
  private static final String aia = "select a.* from accounts a, business_employee be where a.directory_id=be.business_id and be.directory_id=?";
  private static final String ais = "select bank_id from business where directory_id=?";
  private static final String aih = "select bank_id from customer where directory_id=?";
  private static final String ah5 = "select bank_id from bank";
  private static final String aic = "delete from reg_tran_category where reg_transaction_id=(select reg_transaction_id from reg_transaction where directory_id=? and account_id=?)";
  private static final String aif = "delete from reg_transaction where directory_id=?";
  private static final String aim = "delete from reg_payee where directory_id=?";
  private static final String air = "delete from reg_tran_category where directory_id=?";
  private static final String ah7 = "delete from reg_user_category where directory_id=?";
  private static final String ail = "*";
  private static final String aid = "SELECT b.*, ci.*, cd.*  FROM accounts a, business b, contact_info ci, customer_directory cd  WHERE b.business_id = a.directory_id AND b.directory_id=cd.directory_id and b.contact_id=ci.contact_id AND a.account_id = ? AND b.bank_id = ? AND a.routing_num = ?";
  private static final String ain = "SELECT b.*, ci.*, cd.*  FROM accounts a, business b, contact_info ci, customer_directory cd  WHERE b.business_id = a.directory_id AND b.directory_id=cd.directory_id and b.contact_id=ci.contact_id AND a.account_id = ? AND b.bank_id = ? AND a.routing_num IS NULL";
  
  public static Account getAccountAddress(Account paramAccount)
    throws ProfileException
  {
    String str = "AccountAdapter.getAccountAddress";
    Profile.isInitialized();
    int i = paramAccount.getContactId();
    if (i != -1)
    {
      Connection localConnection = null;
      try
      {
        localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
        Contact localContact = ContactAdapter.getContactTX(localConnection, i);
        paramAccount.setContact(localContact);
      }
      catch (Exception localException)
      {
        Profile.handleError(localException, str);
      }
      finally
      {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
    return paramAccount;
  }
  
  public static Accounts getAccounts(Account paramAccount, int paramInt)
    throws ProfileException
  {
    String str1 = "AccountAdapter.getAccounts";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from accounts ");
    Accounts localAccounts = null;
    try
    {
      String str2 = getBankIdByDirectoryId(paramInt);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool = false;
      if (paramInt > 0) {
        bool = Profile.appendSQLSelectInt(localStringBuffer, "directory_id", paramInt, bool);
      }
      bool = Profile.appendSQLSelectString(localStringBuffer, "account_id", paramAccount.getID(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "ACCOUNT_NUM", paramAccount.getNumber(), bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "ACCOUNT_TYPE", paramAccount.getTypeValue(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "nickname", paramAccount.getNickName(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "bank_name", paramAccount.getBankName(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "primary_account", paramAccount.getPrimaryAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "core_account", paramAccount.getCoreAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "personal_account", paramAccount.getPersonalAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "routing_num", paramAccount.getRoutingNum(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "bic_account", paramAccount.getBicAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "positive_pay", paramAccount.getPositivePay(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "currency_type", paramAccount.getCurrencyCode(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "ZBAFLAG", paramAccount.getZBAFlag(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "SHOWPREVOPENLEDGER", paramAccount.getShowPreviousDayOpeningLedger(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "STRIPPED_ACCOUNT_NUM", paramAccount.getStrippedAccountNumber(), bool);
      Profile.appendSQLSelectColumns(localStringBuffer, "accounts", paramAccount);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getID(), "account_id", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getNumber(), "ACCOUNT_NUM", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAccount.getTypeValue(), true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getNickName(), "nickname", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getBankName(), "bank_name", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPrimaryAccount(), "primary_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getCoreAccount(), "core_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPersonalAccount(), "personal_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getRoutingNum(), "routing_num", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getBicAccount(), "bic_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPositivePay(), "positive_pay", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getCurrencyCode(), "currency_type", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getZBAFlag(), "ZBAFLAG", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getShowPreviousDayOpeningLedger(), "SHOWPREVOPENLEDGER", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getStrippedAccountNumber(), "STRIPPED_ACCOUNT_NUM", true);
      Profile.setStatementValues(localPreparedStatement, i, paramAccount, "accounts", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localAccounts = ProfileUtil.processAccountsRS(localResultSet, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static Accounts searchAccounts(Account paramAccount, int paramInt)
    throws ProfileException
  {
    String str1 = "AccountAdapter.getAccountsBC";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from accounts ");
    Accounts localAccounts = null;
    try
    {
      if (paramAccount == null) {
        throw new ProfileException(3534);
      }
      String str2 = paramAccount.getBankID();
      if ((str2 == null) || (str2.equals(""))) {
        throw new ProfileException(3534);
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool = false;
      if (paramInt > 0) {
        bool = Profile.appendSQLSelectInt(localStringBuffer, "directory_id", paramInt, bool);
      }
      bool = Profile.appendSQLSelectString(localStringBuffer, "account_id", paramAccount.getID(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "ACCOUNT_NUM", paramAccount.getNumber(), true, true, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "ACCOUNT_TYPE", paramAccount.getTypeValue(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "nickname", paramAccount.getNickName(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "bank_name", paramAccount.getBankName(), true, true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "primary_account", paramAccount.getPrimaryAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "core_account", paramAccount.getCoreAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "personal_account", paramAccount.getPersonalAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "routing_num", paramAccount.getRoutingNum(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "bic_account", paramAccount.getBicAccount(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "positive_pay", paramAccount.getPositivePay(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "currency_type", paramAccount.getCurrencyCode(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "ZBAFLAG", paramAccount.getZBAFlag(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "SHOWPREVOPENLEDGER", paramAccount.getShowPreviousDayOpeningLedger(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "STRIPPED_ACCOUNT_NUM", paramAccount.getStrippedAccountNumber(), true, true, bool);
      Profile.appendSQLSelectColumns(localStringBuffer, "accounts", paramAccount);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getID(), "account_id", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getNumber(), "ACCOUNT_NUM", true, true, true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAccount.getTypeValue(), true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getNickName(), "nickname", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getBankName(), "bank_name", true, true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPrimaryAccount(), "primary_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getCoreAccount(), "core_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPersonalAccount(), "personal_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getRoutingNum(), "routing_num", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getBicAccount(), "bic_account", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPositivePay(), "positive_pay", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getCurrencyCode(), "currency_type", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getZBAFlag(), "ZBAFLAG", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getShowPreviousDayOpeningLedger(), "SHOWPREVOPENLEDGER", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getStrippedAccountNumber(), "STRIPPED_ACCOUNT_NUM", true, true, true);
      Profile.setStatementValues(localPreparedStatement, i, paramAccount, "accounts", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localAccounts = ProfileUtil.processAccountsRS(localResultSet, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static Account getAccount(String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "AccountAdapter.getAccount";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Account localAccount = null;
    StringBuffer localStringBuffer = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramString2 == null)
      {
        localStringBuffer = new StringBuffer("select * from accounts where account_id = ? and routing_num is null");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramString3);
      }
      else
      {
        localStringBuffer = new StringBuffer("select * from accounts where routing_num=? and account_id=?");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramString2);
        localPreparedStatement.setString(2, paramString3);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        localAccount = ProfileUtil.processAccountRS(localResultSet, paramString1);
        localAccount.setAccountGroup(ProfileUtil.b(localAccount.getTypeValue()));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccount;
  }
  
  public static Accounts getAccountsByAccountNumber(String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "AccountAdapter.getAccountsByAccountNumber";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramString2 == null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from accounts where routing_num is null and ACCOUNT_NUM = ?");
        localPreparedStatement.setString(1, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from accounts where routing_num is null and ACCOUNT_NUM = ?");
      }
      else if (paramString2.equals("*"))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from accounts where ACCOUNT_NUM = ?");
        localPreparedStatement.setString(1, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from accounts where ACCOUNT_NUM = ?");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from accounts where routing_num=? and ACCOUNT_NUM = ?");
        localPreparedStatement.setString(1, paramString2);
        localPreparedStatement.setString(2, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from accounts where routing_num=? and ACCOUNT_NUM = ?");
      }
      localAccounts = ProfileUtil.processAccountsRS(localResultSet, paramString1);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "AccountAdapter.getAccounts";
    return getAccountsById(paramSecureUser.getProfileID());
  }
  
  public static Accounts getAccountsX(SecureUser paramSecureUser, Account paramAccount)
    throws ProfileException
  {
    String str = "AccountAdapter.getAccountsX";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from accounts ");
    Profile.appendSQLSelectColumns(localStringBuffer, "accounts", paramAccount);
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      Profile.setStatementValues(localPreparedStatement, 2, paramAccount, "accounts", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localAccounts = ProfileUtil.processAccountsRS(localResultSet, paramSecureUser.getBankID());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  protected static Accounts processAccountsRS(ResultSet paramResultSet, String paramString)
    throws Exception
  {
    Accounts localAccounts = new Accounts();
    while (paramResultSet.next())
    {
      String str1 = Profile.getRSString(paramResultSet, "ACCOUNT_ID");
      String str2 = Profile.getRSString(paramResultSet, "ACCOUNT_NUM");
      int i = paramResultSet.getInt("ACCOUNT_TYPE");
      Account localAccount = localAccounts.create(paramString, str1, str2, i);
      Timestamp localTimestamp = paramResultSet.getTimestamp("EXPORT_BEGIN_DATE");
      DateTime localDateTime;
      if (localTimestamp != null)
      {
        localDateTime = new DateTime(localTimestamp, Locale.getDefault(), "MM-dd-yyyy");
        localAccount.put("EXPORT_BEGIN_DATE", localDateTime.toString());
      }
      localTimestamp = paramResultSet.getTimestamp("EXPORT_END_DATE");
      if (localTimestamp != null)
      {
        localDateTime = new DateTime(localTimestamp, Locale.getDefault(), "MM-dd-yyyy");
        localAccount.put("EXPORT_END_DATE", localDateTime.toString());
      }
      localAccount.setCurrencyCode(Profile.getRSString(paramResultSet, "CURRENCY_TYPE"));
      localAccount.setBankName(Profile.getRSString(paramResultSet, "BANK_NAME"));
      localAccount.setPrimaryAccount(Profile.getRSString(paramResultSet, "PRIMARY_ACCOUNT"));
      localAccount.setCoreAccount(Profile.getRSString(paramResultSet, "CORE_ACCOUNT"));
      localAccount.setPersonalAccount(Profile.getRSString(paramResultSet, "PERSONAL_ACCOUNT"));
      localAccount.setBicAccount(Profile.getRSString(paramResultSet, "BIC_ACCOUNT"));
      localAccount.setPositivePay(Profile.getRSString(paramResultSet, "POSITIVE_PAY"));
      localAccount.setRoutingNum(Profile.getRSString(paramResultSet, "ROUTING_NUM"));
      localAccount.setZBAFlag(Profile.getRSString(paramResultSet, "ZBAFLAG"));
      localAccount.setShowPreviousDayOpeningLedger(Profile.getRSString(paramResultSet, "SHOWPREVOPENLEDGER"));
      localAccount.setContactId(paramResultSet.getInt("CONTACT_ID"));
      jdMethod_int(localAccount, paramResultSet);
      localAccount.setAccountGroup(ProfileUtil.b(localAccount.getTypeValue()));
    }
    return localAccounts;
  }
  
  private static Account jdMethod_for(ResultSet paramResultSet, String paramString)
    throws Exception
  {
    String str1 = Profile.getRSString(paramResultSet, "ACCOUNT_ID");
    String str2 = Profile.getRSString(paramResultSet, "ACCOUNT_NUM");
    int i = paramResultSet.getInt("ACCOUNT_TYPE");
    Accounts localAccounts = new Accounts();
    Account localAccount = localAccounts.create(paramString, str1, str2, i);
    localAccount.setCurrencyCode(Profile.getRSString(paramResultSet, "CURRENCY_TYPE"));
    localAccount.setBankName(Profile.getRSString(paramResultSet, "BANK_NAME"));
    localAccount.setPrimaryAccount(Profile.getRSString(paramResultSet, "PRIMARY_ACCOUNT"));
    localAccount.setCoreAccount(Profile.getRSString(paramResultSet, "CORE_ACCOUNT"));
    localAccount.setPersonalAccount(Profile.getRSString(paramResultSet, "PERSONAL_ACCOUNT"));
    localAccount.setBicAccount(Profile.getRSString(paramResultSet, "BIC_ACCOUNT"));
    localAccount.setPositivePay(Profile.getRSString(paramResultSet, "POSITIVE_PAY"));
    localAccount.setRoutingNum(Profile.getRSString(paramResultSet, "ROUTING_NUM"));
    localAccount.setZBAFlag(Profile.getRSString(paramResultSet, "ZBAFLAG"));
    localAccount.setShowPreviousDayOpeningLedger(Profile.getRSString(paramResultSet, "SHOWPREVOPENLEDGER"));
    localAccount.setContactId(paramResultSet.getInt("CONTACT_ID"));
    jdMethod_int(localAccount, paramResultSet);
    localAccount.setAccountGroup(ProfileUtil.b(localAccount.getTypeValue()));
    return localAccount;
  }
  
  public static Account addAccount(Account paramAccount, int paramInt)
    throws ProfileException
  {
    String str = "AccountAdapter.addAccount";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      addAccountTX(localConnection, paramAccount, paramInt);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramAccount;
  }
  
  public static void addAccounts(Accounts paramAccounts, int paramInt)
    throws ProfileException
  {
    String str = "AccountAdapter.addAccounts";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        addAccountTX(localConnection, localAccount, paramInt);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void addAccountTX(Connection paramConnection, Account paramAccount, int paramInt)
    throws Exception
  {
    String str1 = "AccountAdapter.addAccountTX";
    if (paramAccount.getContactId() != -1) {
      throw new ProfileException(str1, 4100, "");
    }
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("insert into accounts(directory_id,account_id,CONTACT_ID,nickname,hide,export_begin_date,export_end_date,bank_email_token,positive_pay,routing_num,bic_account,primary_account,core_account,personal_account,currency_type,bank_name,ZBAFLAG,SHOWPREVOPENLEDGER,ACCOUNT_TYPE,ACCOUNT_NUM,IS_MASTER,INC_ZBACR_INROLLUP, INC_ZBADB_INROLLUP,WITHHOLD_NZB_SUB,STRIPPED_ACCOUNT_NUM");
    try
    {
      Contact localContact = paramAccount.getContact();
      if (localContact != null)
      {
        i = ContactAdapter.addContactTX(paramConnection, localContact);
        paramAccount.setContactId(i);
      }
      Profile.appendSQLInsertColumns(localStringBuffer, "accounts", true);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getID(), "account_id", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAccount.getContactId(), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getNickName(), "nickname", false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.getBeanInt(paramAccount, "hide"), false);
      i = Profile.setStatementDate(localPreparedStatement, i, paramAccount.get("EXPORT_BEGIN_DATE"), false);
      i = Profile.setStatementDate(localPreparedStatement, i, paramAccount.get("EXPORT_END_DATE"), false);
      i = Profile.setStatementString(localPreparedStatement, i, (String)paramAccount.get("BANK_EMAIL_TOKEN"), "bank_email_token", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPositivePay(), "positive_pay", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getRoutingNum(), "routing_num", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getBicAccount(), "bic_account", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPrimaryAccount(), "primary_account", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getCoreAccount(), "core_account", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getPersonalAccount(), "personal_account", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getCurrencyCode(), "currency_type", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getBankName(), "bank_name", false);
      String str2 = paramAccount.getZBAFlag();
      if ((str2 == null) || (str2.length() == 0))
      {
        str2 = "B";
        paramAccount.setZBAFlag(str2);
      }
      i = Profile.setStatementString(localPreparedStatement, i, str2, "ZBAFLAG", false);
      String str3 = paramAccount.getShowPreviousDayOpeningLedger();
      if ((str3 == null) || (str3.length() == 0))
      {
        str3 = "Y";
        paramAccount.setShowPreviousDayOpeningLedger(str3);
      }
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getShowPreviousDayOpeningLedger(), "SHOWPREVOPENLEDGER", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAccount.getTypeValue(), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getNumber(), "ACCOUNT_NUM", false);
      String str4 = null;
      if (paramAccount.isMaster()) {
        str4 = "Y";
      } else {
        str4 = "N";
      }
      i = Profile.setStatementString(localPreparedStatement, i, str4, "IS_MASTER", false);
      String str5 = null;
      if (paramAccount.getIncludeZBACreditInRollupValue()) {
        str5 = "Y";
      } else {
        str5 = "N";
      }
      i = Profile.setStatementString(localPreparedStatement, i, str5, "INC_ZBACR_INROLLUP", false);
      String str6 = null;
      if (paramAccount.getIncludeZBADebitInRollupValue()) {
        str6 = "Y";
      } else {
        str6 = "N";
      }
      i = Profile.setStatementString(localPreparedStatement, i, str6, "INC_ZBADB_INROLLUP", false);
      String str7 = null;
      if (paramAccount.getWithholdNonZeroBalanceSubAccountsValue()) {
        str7 = "Y";
      } else {
        str7 = "N";
      }
      i = Profile.setStatementString(localPreparedStatement, i, str7, "WITHHOLD_NZB_SUB", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAccount.getStrippedAccountNumber(), "STRIPPED_ACCOUNT_NUM", false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramAccount, "accounts", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static Accounts mergeAccounts(Accounts paramAccounts, int paramInt)
    throws ProfileException
  {
    String str1 = "AccountAdapter.mergeAccounts";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      Accounts localAccounts = getAccounts(new Account(), paramInt);
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount1 = (Account)localIterator.next();
        String str2 = localAccount1.getID();
        Account localAccount2 = localAccounts.getByID(str2);
        if (localAccount2 != null)
        {
          localAccount1.setNickName(localAccount2.getNickName());
          deleteAccountTX(localConnection, paramInt, localAccount2);
        }
        addAccountTX(localConnection, localAccount1, paramInt);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramAccounts;
  }
  
  public static void deleteAccount(Account paramAccount, int paramInt)
    throws ProfileException
  {
    String str = "AccountAdapter.deleteAccount";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      deleteAccountTX(localConnection, paramInt, paramAccount);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected static void deleteAccountTX(Connection paramConnection, int paramInt, Account paramAccount)
    throws Exception
  {
    String str1 = "AccountAdapter.addAccountTX";
    String str2 = null;
    if (paramAccount != null)
    {
      str2 = paramAccount.getID();
      int i = paramAccount.getContactId();
      if ((i == -1) && (paramAccount.getContact() != null)) {
        throw new ProfileException(str1, 4100, "");
      }
      if (i != -1) {
        ContactAdapter.deleteContactTX(paramConnection, i);
      }
    }
    else
    {
      ContactAdapter.deleteAllAccountContacts(paramConnection, paramInt);
    }
    jdMethod_new(paramConnection, paramInt, str2);
    jdMethod_int(paramConnection, new StringBuffer("delete from accounts where directory_id=?"), paramInt, str2);
  }
  
  public static boolean modifyAccount(Account paramAccount, int paramInt)
    throws ProfileException
  {
    String str1 = "AccountAdapter.modifyAccount";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = null;
    try
    {
      String str2 = paramAccount.getShowPreviousDayOpeningLedger();
      if ((str2 == null) || (str2.length() <= 0)) {
        localStringBuffer = new StringBuffer("update accounts set CONTACT_ID=?,positive_pay=?,ZBAFLAG=?,primary_account=?,nickname=?,hide=?,export_begin_date=?,export_end_date=?,IS_MASTER=?,INC_ZBACR_INROLLUP=?,INC_ZBADB_INROLLUP=?,WITHHOLD_NZB_SUB=?");
      } else {
        localStringBuffer = new StringBuffer("update accounts set CONTACT_ID=?,positive_pay=?,ZBAFLAG=?,primary_account=?,SHOWPREVOPENLEDGER=?,nickname=?,hide=?,export_begin_date=?,export_end_date=?,IS_MASTER=?,INC_ZBACR_INROLLUP=?,INC_ZBADB_INROLLUP=?,WITHHOLD_NZB_SUB=?");
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = paramAccount.getContactId();
      Contact localContact = paramAccount.getContact();
      if ((i == -1) && (localContact != null))
      {
        i = ContactAdapter.addContactTX(localConnection, localContact);
        paramAccount.setContactId(i);
      }
      else if ((i != -1) && (localContact != null))
      {
        ContactAdapter.modifyContactTX(localConnection, localContact);
      }
      else if ((i != -1) && (localContact == null))
      {
        ContactAdapter.deleteContactTX(localConnection, i);
        i = -1;
        paramAccount.setContactId(i);
      }
      Profile.appendSQLUpdateColumns(localStringBuffer, "accounts", paramAccount);
      localStringBuffer.append(" where directory_id=? and account_id=?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, paramAccount.getContactId(), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramAccount.getPositivePay(), "positive_pay", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramAccount.getZBAFlag(), "ZBAFLAG", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramAccount.getPrimaryAccount(), "primary_account", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramAccount.getShowPreviousDayOpeningLedger(), "SHOWPREVOPENLEDGER", false, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramAccount.getNickName(), "nickname", false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.getBeanInt(paramAccount, "hide"), false);
      j = Profile.setStatementDate(localPreparedStatement, j, Profile.convertToTimestamp(paramAccount.get("EXPORT_BEGIN_DATE")), false);
      j = Profile.setStatementDate(localPreparedStatement, j, Profile.convertToTimestamp(paramAccount.get("EXPORT_END_DATE")), false);
      String str3 = null;
      if (paramAccount.isMaster()) {
        str3 = "Y";
      } else {
        str3 = "N";
      }
      j = Profile.setStatementString(localPreparedStatement, j, str3, "IS_MASTER", false);
      String str4 = null;
      if (paramAccount.getIncludeZBACreditInRollupValue()) {
        str4 = "Y";
      } else {
        str4 = "N";
      }
      j = Profile.setStatementString(localPreparedStatement, j, str4, "INC_ZBACR_INROLLUP", false);
      String str5 = null;
      if (paramAccount.getIncludeZBADebitInRollupValue()) {
        str5 = "Y";
      } else {
        str5 = "N";
      }
      j = Profile.setStatementString(localPreparedStatement, j, str5, "INC_ZBADB_INROLLUP", false);
      String str6 = null;
      if (paramAccount.getWithholdNonZeroBalanceSubAccountsValue()) {
        str6 = "Y";
      } else {
        str6 = "N";
      }
      j = Profile.setStatementString(localPreparedStatement, j, str6, "WITHHOLD_NZB_SUB", false);
      j = Profile.setStatementValues(localPreparedStatement, j, paramAccount, "accounts", true);
      j = Profile.setStatementInt(localPreparedStatement, j, paramInt, false);
      j = Profile.setStatementString(localPreparedStatement, j, paramAccount.getID(), "account_id", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return true;
  }
  
  public static Accounts getAccountsById(int paramInt)
    throws ProfileException
  {
    String str1 = "AccountAdapter.getAccountsById";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts = null;
    try
    {
      String str2 = getBankIdByDirectoryId(paramInt);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from accounts where directory_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from accounts where directory_id=?");
      localAccounts = ProfileUtil.processAccountsRS(localResultSet, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "AccountAdapter.getAccountsByBusinessEmployee";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select a.* from accounts a, business_employee be where a.directory_id=be.business_id and be.directory_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramSecureUser.getProfileID(), false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select a.* from accounts a, business_employee be where a.directory_id=be.business_id and be.directory_id=?");
      localAccounts = ProfileUtil.processAccountsRS(localResultSet, paramSecureUser.getBankID(), paramSecureUser.getLocale());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  private static void jdMethod_int(Account paramAccount, ResultSet paramResultSet)
    throws Exception
  {
    paramAccount.setNickName(Profile.getRSString(paramResultSet, "nickname"));
    paramAccount.put("HIDE", Profile.getRSString(paramResultSet, "hide"));
    Timestamp localTimestamp = paramResultSet.getTimestamp("reg_retrieval_date");
    if (localTimestamp != null) {
      paramAccount.put("REG_RETRIEVAL_DATE", new DateTime(localTimestamp, Locale.getDefault()));
    }
    paramAccount.put("REG_DEFAULT", Profile.getRSString(paramResultSet, "reg_default"));
    paramAccount.put("REG_ENABLED", Profile.getRSString(paramResultSet, "reg_enabled"));
    Profile.setXBeanFields(paramResultSet, paramAccount, "accounts");
  }
  
  public static String getBankIdByDirectoryId(int paramInt)
    throws ProfileException
  {
    String str1 = "AccountAdapter.getBankIdByDirectoryId";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str2 = "";
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      str2 = jdMethod_for(localConnection, "select bank_id from business where directory_id=?", paramInt);
      String str3;
      if (str2.length() > 0)
      {
        str3 = str2;
        return str3;
      }
      str2 = jdMethod_for(localConnection, "select bank_id from customer where directory_id=?", paramInt);
      if (str2.length() > 0)
      {
        str3 = str2;
        return str3;
      }
      str2 = jdMethod_int(localConnection);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return str2;
  }
  
  private static String jdMethod_for(Connection paramConnection, String paramString, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString);
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, paramString);
      if (localResultSet.next())
      {
        String str = Profile.getRSString(localResultSet, "bank_id");
        return str;
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return "";
  }
  
  private static String jdMethod_int(Connection paramConnection)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select bank_id from bank");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select bank_id from bank");
      if (localResultSet.next())
      {
        String str = Profile.getRSString(localResultSet, "bank_id");
        return str;
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return "1000";
  }
  
  private static void jdMethod_new(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      jdMethod_for(paramConnection, new StringBuffer("delete from reg_tran_category where reg_transaction_id=(select reg_transaction_id from reg_transaction where directory_id=? and account_id=?)"), paramInt, paramString);
      jdMethod_int(paramConnection, new StringBuffer("delete from reg_transaction where directory_id=?"), paramInt, paramString);
    }
    else
    {
      jdMethod_int(paramConnection, new StringBuffer("delete from reg_payee where directory_id=?"), paramInt, paramString);
      jdMethod_int(paramConnection, new StringBuffer("delete from reg_tran_category where directory_id=?"), paramInt, paramString);
      jdMethod_int(paramConnection, new StringBuffer("delete from reg_transaction where directory_id=?"), paramInt, paramString);
      jdMethod_int(paramConnection, new StringBuffer("delete from reg_user_category where directory_id=?"), paramInt, paramString);
    }
  }
  
  private static void jdMethod_int(Connection paramConnection, StringBuffer paramStringBuffer, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = 1;
    try
    {
      Profile.appendSQLSelectString(paramStringBuffer, "account_id", paramString, true);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramStringBuffer.toString());
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "account_id", true);
      DBUtil.executeUpdate(localPreparedStatement, paramStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, StringBuffer paramStringBuffer, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = 1;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramStringBuffer.toString());
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "account_id", false);
      DBUtil.executeUpdate(localPreparedStatement, paramStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static Businesses getBusinessesForAccount(Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "AccountAdapter.getBusinessesForAccount";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Businesses localBusinesses = new Businesses();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT b.*, ci.*, cd.*  FROM accounts a, business b, contact_info ci, customer_directory cd  WHERE b.business_id = a.directory_id AND b.directory_id=cd.directory_id and b.contact_id=ci.contact_id AND a.account_id = ? AND b.bank_id = ? AND a.routing_num = ?");
      } else {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT b.*, ci.*, cd.*  FROM accounts a, business b, contact_info ci, customer_directory cd  WHERE b.business_id = a.directory_id AND b.directory_id=cd.directory_id and b.contact_id=ci.contact_id AND a.account_id = ? AND b.bank_id = ? AND a.routing_num IS NULL");
      }
      Profile.setStatementString(localPreparedStatement, 1, paramAccount.getID(), "ACCOUNT_ID", false);
      Profile.setStatementString(localPreparedStatement, 2, paramAccount.getBankID(), "BANK_ID", false);
      if (paramAccount.getRoutingNum() != null) {
        Profile.setStatementString(localPreparedStatement, 3, paramAccount.getRoutingNum(), "ROUTING_NUM", false);
      }
      if (paramAccount.getRoutingNum() != null) {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.*, ci.*, cd.*  FROM accounts a, business b, contact_info ci, customer_directory cd  WHERE b.business_id = a.directory_id AND b.directory_id=cd.directory_id and b.contact_id=ci.contact_id AND a.account_id = ? AND b.bank_id = ? AND a.routing_num = ?");
      } else {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.*, ci.*, cd.*  FROM accounts a, business b, contact_info ci, customer_directory cd  WHERE b.business_id = a.directory_id AND b.directory_id=cd.directory_id and b.contact_id=ci.contact_id AND a.account_id = ? AND b.bank_id = ? AND a.routing_num IS NULL");
      }
      while (localResultSet.next()) {
        localBusinesses.add(BusinessAdapter.processBusinessRS(localResultSet));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localBusinesses;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.AccountAdapter
 * JD-Core Version:    0.7.0.1
 */