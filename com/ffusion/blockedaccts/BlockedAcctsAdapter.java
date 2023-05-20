package com.ffusion.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.services.blockedaccts.BLKException;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResult;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class BlockedAcctsAdapter
{
  private static final String jdField_byte = "insert into BLK_ACCOUNT (USER_DIR_ID,ROUTING_NUM,ACCOUNT_NUM,STRIPPED_ACCOUNT_NUM,BANK_NAME,LOWER_BANK_NAME) VALUES (?,?,?,?,?,?)";
  private static final String jdField_int = "select USER_DIR_ID,ROUTING_NUM,STRIPPED_ACCOUNT_NUM from BLK_ACCOUNT where ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ? and USER_DIR_ID IN (0";
  private static final String jdField_case = "select USER_DIR_ID,ROUTING_NUM,STRIPPED_ACCOUNT_NUM from BLK_ACCOUNT where ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ? and USER_DIR_ID = ?";
  private static final String jdField_if = "delete from BLK_ACCOUNT where USER_DIR_ID = ? and ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ?";
  private static final String a = "select count(*) from BLK_ACCOUNT";
  private static final String jdField_for = "select USER_DIR_ID,ROUTING_NUM,ACCOUNT_NUM,BANK_NAME,user_name,first_name,last_name,STRIPPED_ACCOUNT_NUM from BLK_ACCOUNT left outer join customer on USER_DIR_ID=directory_id";
  private static final String jdField_try = "update BLK_ACCOUNT set ROUTING_NUM=?,ACCOUNT_NUM=?,STRIPPED_ACCOUNT_NUM=?,BANK_NAME=?,LOWER_BANK_NAME=?,USER_DIR_ID=? where ROUTING_NUM=? and STRIPPED_ACCOUNT_NUM=? and USER_DIR_ID=?";
  private static boolean jdField_new = false;
  private static String jdField_do = null;
  
  public static void initialize(HashMap paramHashMap)
    throws BLKException
  {
    String str = "com.ffusion.blockedaccts.BlockedAcctsAdapter.initialize";
    try
    {
      if (jdField_do == null)
      {
        Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
        if (localProperties == null)
        {
          DebugLog.log(Level.SEVERE, str + ": Missing database connection pool configuration.");
          throw new BLKException(str, 3, "Missing database connection pool configuration.");
        }
        jdField_do = ConnectionPool.init(localProperties);
        jdField_new = true;
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localException.getMessage());
      throw new BLKException(localException, str, 1, localException.getMessage());
    }
  }
  
  public static void addBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str1 = null;
    String str2 = "com.ffusion.blockedaccts.BlockedAcctsAdapter.addBlockedAccount";
    if (paramSecureUser == null)
    {
      str1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str2 + ": " + str1);
      throw new BLKException(str2, 5, str1);
    }
    a(str2);
    a(paramBlockedAccount, true, str2);
    try
    {
      localConnection = DBUtil.getConnection(jdField_do, false, 2);
      if (a(paramBlockedAccount, localConnection, str2, true))
      {
        str1 = "The new account is already a blocked account.";
        throw new BLKException(str2, 11, str1);
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into BLK_ACCOUNT (USER_DIR_ID,ROUTING_NUM,ACCOUNT_NUM,STRIPPED_ACCOUNT_NUM,BANK_NAME,LOWER_BANK_NAME) VALUES (?,?,?,?,?,?)");
      localPreparedStatement.setInt(1, paramBlockedAccount.getUserDirectoryID());
      localPreparedStatement.setString(2, paramBlockedAccount.getRoutingNumber().trim());
      localPreparedStatement.setString(3, paramBlockedAccount.getAccountNumber().trim());
      localPreparedStatement.setString(4, paramBlockedAccount.getStrippedAccountNumber().trim());
      localPreparedStatement.setString(5, paramBlockedAccount.getBankName().trim());
      localPreparedStatement.setString(6, paramBlockedAccount.getBankName().trim().toLowerCase());
      DBUtil.executeUpdate(localPreparedStatement, "insert into BLK_ACCOUNT (USER_DIR_ID,ROUTING_NUM,ACCOUNT_NUM,STRIPPED_ACCOUNT_NUM,BANK_NAME,LOWER_BANK_NAME) VALUES (?,?,?,?,?,?)");
      DBUtil.commit(localConnection);
    }
    catch (BLKException localBLKException)
    {
      DBUtil.rollback(localConnection);
      throw localBLKException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new BLKException(localException, str2, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(jdField_do, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException
  {
    Connection localConnection = null;
    int i = 0;
    PreparedStatement localPreparedStatement = null;
    String str1 = null;
    String str2 = "com.ffusion.blockedaccts.BlockedAcctsAdapter.deleteBlockedAccount";
    if (paramSecureUser == null)
    {
      str1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str2 + ": " + str1);
      throw new BLKException(str2, 5, str1);
    }
    a(str2);
    a(paramBlockedAccount, false, str2);
    try
    {
      localConnection = DBUtil.getConnection(jdField_do, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from BLK_ACCOUNT where USER_DIR_ID = ? and ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ?");
      localPreparedStatement.setInt(1, paramBlockedAccount.getUserDirectoryID());
      localPreparedStatement.setString(2, paramBlockedAccount.getRoutingNumber().trim());
      localPreparedStatement.setString(3, paramBlockedAccount.getStrippedAccountNumber().trim());
      i = DBUtil.executeUpdate(localPreparedStatement, "delete from BLK_ACCOUNT where USER_DIR_ID = ? and ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ?");
      DBUtil.commit(localConnection);
      if (i == 0)
      {
        str1 = "The specified blocked account could not be found or deleted from the database.";
        DebugLog.log(Level.FINE, str2 + ": " + str1);
        throw new BLKException(str2, 9, str1);
      }
    }
    catch (BLKException localBLKException)
    {
      DBUtil.rollback(localConnection);
      throw localBLKException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new BLKException(localException, str2, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(jdField_do, localConnection, localPreparedStatement);
    }
  }
  
  public static void modifyBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount1, BlockedAccount paramBlockedAccount2, HashMap paramHashMap)
    throws BLKException
  {
    Connection localConnection = null;
    int i = 0;
    PreparedStatement localPreparedStatement = null;
    String str1 = null;
    String str2 = "com.ffusion.blockedaccts.BlockedAcctsAdapter.modifyBlockedAccount";
    if (paramSecureUser == null)
    {
      str1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str2 + ": " + str1);
      throw new BLKException(str2, 5, str1);
    }
    a(str2);
    a(paramBlockedAccount1, false, str2);
    a(paramBlockedAccount2, true, str2);
    try
    {
      localConnection = DBUtil.getConnection(jdField_do, false, 2);
      if ((a(paramBlockedAccount2, localConnection, str2, true)) && (!paramBlockedAccount2.isEquivalentExcludingBankName(paramBlockedAccount1)))
      {
        str1 = "The new account is already a blocked account.";
        throw new BLKException(str2, 11, str1);
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update BLK_ACCOUNT set ROUTING_NUM=?,ACCOUNT_NUM=?,STRIPPED_ACCOUNT_NUM=?,BANK_NAME=?,LOWER_BANK_NAME=?,USER_DIR_ID=? where ROUTING_NUM=? and STRIPPED_ACCOUNT_NUM=? and USER_DIR_ID=?");
      localPreparedStatement.setString(1, paramBlockedAccount2.getRoutingNumber().trim());
      localPreparedStatement.setString(2, paramBlockedAccount2.getAccountNumber().trim());
      localPreparedStatement.setString(3, paramBlockedAccount2.getStrippedAccountNumber().trim());
      localPreparedStatement.setString(4, paramBlockedAccount2.getBankName().trim());
      localPreparedStatement.setString(5, paramBlockedAccount2.getBankName().trim().toLowerCase());
      localPreparedStatement.setInt(6, paramBlockedAccount2.getUserDirectoryID());
      localPreparedStatement.setString(7, paramBlockedAccount1.getRoutingNumber().trim());
      localPreparedStatement.setString(8, paramBlockedAccount1.getStrippedAccountNumber().trim());
      localPreparedStatement.setInt(9, paramBlockedAccount1.getUserDirectoryID());
      i = DBUtil.executeUpdate(localPreparedStatement, "update BLK_ACCOUNT set ROUTING_NUM=?,ACCOUNT_NUM=?,STRIPPED_ACCOUNT_NUM=?,BANK_NAME=?,LOWER_BANK_NAME=?,USER_DIR_ID=? where ROUTING_NUM=? and STRIPPED_ACCOUNT_NUM=? and USER_DIR_ID=?");
      DBUtil.commit(localConnection);
      if (i == 0)
      {
        str1 = "The specified blocked account could not be found or updated in the database.";
        DebugLog.log(Level.FINE, str2 + ": " + str1);
        throw new BLKException(str2, 9, str1);
      }
    }
    catch (BLKException localBLKException)
    {
      DBUtil.rollback(localConnection);
      throw localBLKException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new BLKException(localException, str2, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(jdField_do, localConnection, localPreparedStatement);
    }
  }
  
  public static int getNumBlockedAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BLKException
  {
    Connection localConnection = null;
    int i = 0;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "com.ffusion.blockedaccts.BlockedAcctsAdapter.getNumBlockedAccounts";
    if (paramSecureUser == null)
    {
      str1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str2 + ": " + str1);
      throw new BLKException(str2, 5, str1);
    }
    a(str2);
    try
    {
      localConnection = DBUtil.getConnection(jdField_do, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from BLK_ACCOUNT");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from BLK_ACCOUNT");
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      } else {
        i = 0;
      }
      DBUtil.commit(localConnection);
    }
    catch (BLKException localBLKException)
    {
      DBUtil.rollback(localConnection);
      throw localBLKException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new BLKException(localException, str2, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(jdField_do, localConnection, localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  public static BlockedAccountSearchResults getBlockedAccounts(SecureUser paramSecureUser, BlockedAccountSearchCriteria paramBlockedAccountSearchCriteria, HashMap paramHashMap)
    throws BLKException
  {
    BlockedAccountSearchResult localBlockedAccountSearchResult = null;
    BlockedAccountSearchResults localBlockedAccountSearchResults = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = null;
    String str3 = "com.ffusion.blockedaccts.BlockedAcctsAdapter.getBlockedAccounts";
    if (paramSecureUser == null)
    {
      str1 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str3 + ": " + str1);
      throw new BLKException(str3, 5, str1);
    }
    a(str3);
    try
    {
      localConnection = DBUtil.getConnection(jdField_do, false, 2);
      str2 = a(paramBlockedAccountSearchCriteria);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localBlockedAccountSearchResults = new BlockedAccountSearchResults();
      localBlockedAccountSearchResults.setLocale(paramSecureUser.getLocale());
      while (localResultSet.next())
      {
        localBlockedAccountSearchResult = new BlockedAccountSearchResult();
        localBlockedAccountSearchResult.setUserDirectoryID(localResultSet.getInt(1));
        if (localBlockedAccountSearchResult.getUserDirectoryID() != 0)
        {
          localBlockedAccountSearchResult.setUserName(localResultSet.getString(5));
          localBlockedAccountSearchResult.setFirstName(localResultSet.getString(6));
          localBlockedAccountSearchResult.setLastName(localResultSet.getString(7));
        }
        localBlockedAccountSearchResult.setRoutingNumber(localResultSet.getString(2));
        localBlockedAccountSearchResult.setAccountNumber(localResultSet.getString(3));
        localBlockedAccountSearchResult.setBankName(localResultSet.getString(4));
        localBlockedAccountSearchResult.setStrippedAccountNumber(localResultSet.getString(8));
        localBlockedAccountSearchResults.add(localBlockedAccountSearchResult);
      }
      DBUtil.commit(localConnection);
    }
    catch (BLKException localBLKException)
    {
      DBUtil.rollback(localConnection);
      throw localBLKException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str3 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new BLKException(localException, str3, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(jdField_do, localConnection, localPreparedStatement, localResultSet);
    }
    return localBlockedAccountSearchResults;
  }
  
  public static boolean isBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException
  {
    Connection localConnection = null;
    String str1 = "com.ffusion.blockedaccts.BlockedAcctsAdapter.isBlockedAccount";
    if (paramSecureUser == null)
    {
      String str2 = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, str1 + ": " + str2);
      throw new BLKException(str1, 5, str2);
    }
    a(str1);
    try
    {
      a(paramBlockedAccount, true, str1);
    }
    catch (Exception localException1)
    {
      String str3 = "An invalid field was specified in the given blocked account.";
      DebugLog.log(Level.FINE, str1 + ": " + str3);
      return false;
    }
    try
    {
      localConnection = DBUtil.getConnection(jdField_do, false, 2);
      boolean bool = a(paramBlockedAccount, localConnection, str1, false);
      return bool;
    }
    catch (BLKException localBLKException)
    {
      DBUtil.rollback(localConnection);
      throw localBLKException;
    }
    catch (Exception localException2)
    {
      DebugLog.log(Level.SEVERE, str1 + ": " + localException2.getMessage());
      DBUtil.rollback(localConnection);
      throw new BLKException(localException2, str1, 2, localException2.getMessage());
    }
    finally
    {
      DBUtil.returnConnection(jdField_do, localConnection);
    }
  }
  
  public static String getStrippedAccountNumber(String paramString, HashMap paramHashMap)
    throws BLKException
  {
    char c = '0';
    int i = 0;
    String str = null;
    StringBuffer localStringBuffer = null;
    if (paramString != null)
    {
      localStringBuffer = new StringBuffer(paramString.length());
      paramString = paramString.toLowerCase();
      i = paramString.length();
      for (int j = 0; j < i; j++)
      {
        c = paramString.charAt(j);
        if (Character.isLetterOrDigit(c)) {
          localStringBuffer.append(c);
        }
      }
      str = localStringBuffer.toString();
    }
    return str;
  }
  
  private static void a(String paramString)
    throws BLKException
  {
    if (!jdField_new)
    {
      String str = "The Blocked Account adapter must be initialized before any calls are made to the service.";
      DebugLog.log(Level.SEVERE, paramString + ": " + str);
      throw new BLKException(paramString, 4, str);
    }
  }
  
  public static void verifyInitialization()
    throws BLKException
  {
    a("External call.");
  }
  
  private static void a(BlockedAccount paramBlockedAccount, boolean paramBoolean, String paramString)
    throws BLKException
  {
    String str = "";
    int i = 0;
    if (paramBlockedAccount != null)
    {
      if ((paramBlockedAccount.getRoutingNumber() == null) || (paramBlockedAccount.getRoutingNumber().trim().length() == 0))
      {
        str = "An invalid routing number has been specified.";
        i = 6;
      }
      else if ((paramBoolean) && ((paramBlockedAccount.getBankName() == null) || (paramBlockedAccount.getBankName().trim().length() == 0)))
      {
        str = "An invalid bank name has been specified.";
        i = 8;
      }
      else if ((paramBlockedAccount.getAccountNumber() == null) || (paramBlockedAccount.getAccountNumber().trim().length() == 0))
      {
        str = "An invalid account number has been specified.";
        i = 7;
      }
      else if ((paramBlockedAccount.getStrippedAccountNumber() == null) || (paramBlockedAccount.getStrippedAccountNumber().trim().length() == 0))
      {
        str = "An invalid stripped account number has been specified.";
        i = 12;
      }
      else if (paramBlockedAccount.getUserDirectoryID() < 0)
      {
        str = "An invalid user directory ID has been specified.";
        i = 10;
      }
    }
    else
    {
      str = "One or more illegal arguments have been specified.";
      i = 5;
    }
    if (i != 0)
    {
      DebugLog.log(Level.FINE, paramString + ": " + str);
      throw new BLKException(paramString, i, str);
    }
  }
  
  private static boolean a(BlockedAccount paramBlockedAccount, Connection paramConnection, String paramString, boolean paramBoolean)
    throws Exception
  {
    boolean bool = false;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    StringBuffer localStringBuffer = null;
    if (paramBlockedAccount == null)
    {
      str = "One or more illegal arguments have been specified.";
      DebugLog.log(Level.FINE, paramString + ": " + str);
      throw new BLKException(paramString, 5, str);
    }
    if (paramConnection == null) {
      throw new Exception("No connection to the database");
    }
    try
    {
      if (paramBoolean)
      {
        localStringBuffer = new StringBuffer("select USER_DIR_ID,ROUTING_NUM,STRIPPED_ACCOUNT_NUM from BLK_ACCOUNT where ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ? and USER_DIR_ID = ?");
      }
      else
      {
        localStringBuffer = new StringBuffer("select USER_DIR_ID,ROUTING_NUM,STRIPPED_ACCOUNT_NUM from BLK_ACCOUNT where ROUTING_NUM = ? and STRIPPED_ACCOUNT_NUM = ? and USER_DIR_ID IN (0");
        if (paramBlockedAccount.getUserDirectoryID() != 0) {
          localStringBuffer.append(", " + paramBlockedAccount.getUserDirectoryID() + ")");
        } else {
          localStringBuffer.append(")");
        }
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramBlockedAccount.getRoutingNumber().trim());
      localPreparedStatement.setString(2, paramBlockedAccount.getStrippedAccountNumber().trim());
      if (paramBoolean) {
        localPreparedStatement.setInt(3, paramBlockedAccount.getUserDirectoryID());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        bool = true;
      } else {
        bool = false;
      }
      DBUtil.commit(paramConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(paramConnection);
      throw localException;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return bool;
  }
  
  private static String a(BlockedAccountSearchCriteria paramBlockedAccountSearchCriteria)
    throws Exception
  {
    boolean bool = false;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    StringBuffer localStringBuffer = new StringBuffer("select USER_DIR_ID,ROUTING_NUM,ACCOUNT_NUM,BANK_NAME,user_name,first_name,last_name,STRIPPED_ACCOUNT_NUM from BLK_ACCOUNT left outer join customer on USER_DIR_ID=directory_id");
    if (paramBlockedAccountSearchCriteria != null)
    {
      str5 = paramBlockedAccountSearchCriteria.getRoutingNumber();
      str1 = paramBlockedAccountSearchCriteria.getStrippedAccountNumber();
      str2 = paramBlockedAccountSearchCriteria.getBankName();
      str6 = paramBlockedAccountSearchCriteria.getUserName();
      str3 = paramBlockedAccountSearchCriteria.getFirstName();
      str4 = paramBlockedAccountSearchCriteria.getLastName();
      if ((str5 != null) && (str5.trim().length() != 0))
      {
        bool = true;
        localStringBuffer.append(" where ROUTING_NUM='" + DBUtil.escapeSQLStringLiteral(str5.trim()) + "'");
      }
      if ((str1 != null) && (str1.trim().length() != 0))
      {
        bool = a(localStringBuffer, bool);
        localStringBuffer.append(" STRIPPED_ACCOUNT_NUM='" + DBUtil.escapeSQLStringLiteral(str1.trim()) + "'");
      }
      if ((str2 != null) && (str2.trim().length() != 0))
      {
        bool = a(localStringBuffer, bool);
        localStringBuffer.append(" LOWER_BANK_NAME like '" + DBUtil.escapeSQLStringLiteral(str2.trim().toLowerCase()) + "%'");
      }
      if ((str6 != null) && (str6.trim().length() != 0))
      {
        bool = a(localStringBuffer, bool);
        localStringBuffer.append(" user_name like '" + DBUtil.escapeSQLStringLiteral(str6.trim().toLowerCase()) + "%'");
      }
      if ((str3 != null) && (str3.trim().length() != 0))
      {
        bool = a(localStringBuffer, bool);
        localStringBuffer.append(" low_first_name like '" + DBUtil.escapeSQLStringLiteral(str3.trim().toLowerCase()) + "%'");
      }
      if ((str4 != null) && (str4.trim().length() != 0))
      {
        bool = a(localStringBuffer, bool);
        localStringBuffer.append(" low_last_name like '" + DBUtil.escapeSQLStringLiteral(str4.trim().toLowerCase()) + "%'");
      }
    }
    return localStringBuffer.toString();
  }
  
  private static boolean a(StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    if (!paramBoolean) {
      paramStringBuffer.append(" where");
    } else {
      paramStringBuffer.append(" and");
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.blockedaccts.BlockedAcctsAdapter
 * JD-Core Version:    0.7.0.1
 */