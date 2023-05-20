package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Balance;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

class DCAccount
{
  private static final String jdField_if = "INSERT INTO DC_Account( DCAccountID, BankID, AccountID, CurrencyCode, RoutingNumber, Extra ) VALUES ( ?, ?, ?, ?, ?, ? )";
  private static final String jdField_for = "DELETE FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?";
  private static final String jdField_goto = "DELETE FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL";
  private static final String jdField_try = "UPDATE DC_Account SET CurrencyCode=?, Extra=? WHERE DCAccountID=?";
  private static final String jdField_else = "SELECT DCAccountID, CurrencyCode, RoutingNumber FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?";
  private static final String jdField_char = "SELECT DCAccountID, CurrencyCode, RoutingNumber FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL";
  private static final String jdField_null = "SELECT * FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?";
  private static final String jdField_case = "SELECT * FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL";
  private static final String jdField_long = "INSERT INTO DC_AccountHistory( DCAccountID, DataDate, DataSource, CurrentLedger, CurrentAvail, ExtendABeanXMLID ) VALUES( ?, ?, ?, ?, ?, ? )";
  private static final String jdField_do = "SELECT CurrentLedger from DC_AccountHistory WHERE CurrentLedger IS NOT null AND DCAccountID = ? ORDER BY DataDate DESC";
  private static final String jdField_int = "SELECT CurrentAvail from DC_AccountHistory WHERE CurrentAvail IS NOT null AND DCAccountID = ? ORDER BY DataDate DESC";
  private static final String a = "SELECT a.DCAccountID, BankID, RoutingNumber, AccountID, CurrencyCode, CurrentLedger, CurrentAvail FROM DC_Account a LEFT OUTER JOIN DC_AccountHistory b ON a.DCAccountID = b.DCAccountID AND DataClassification='P' WHERE BankID = ? AND RoutingNumber = ? AND AccountID = ? ORDER BY DataDate DESC";
  private static final String jdField_byte = "SELECT a.DCAccountID, BankID, RoutingNumber, AccountID, CurrencyCode, CurrentLedger, CurrentAvail from DC_Account a LEFT OUTER JOIN DC_AccountHistory b ON a.DCAccountID = b.DCAccountID AND DataClassification='P' WHERE BankID = ? AND RoutingNumber IS NULL AND AccountID = ? ORDER BY DataDate DESC";
  private static final String jdField_void = "SELECT CurrentLedger, CurrentAvail FROM DC_Account account, DC_AccountHistory history WHERE account.DCAccountID = history.DCAccountID AND BankID=? AND RoutingNumber=? AND AccountID=? AND DataClassification='I' ORDER BY DataDate DESC";
  private static final String jdField_new = "SELECT CurrentLedger, CurrentAvail FROM DC_Account account, DC_AccountHistory history WHERE account.DCAccountID = history.DCAccountID AND BankID=? AND RoutingNumber IS NULL AND AccountID=? AND DataClassification='I' ORDER BY DataDate DESC";
  
  protected static void addAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    if (accountExists(paramAccount, paramConnection, paramHashMap)) {
      throw new DCException(423, "Account " + paramAccount.getID() + " already exists.");
    }
    String str1 = paramAccount.getBankID();
    String str2 = paramAccount.getID();
    String str3 = paramAccount.getCurrencyCode();
    String str4 = paramAccount.getRoutingNum();
    int i = -1;
    if ((str1 == null) || (str1.length() <= 0)) {
      throw new DCException(424, "Cannot add account: bank ID does not exist");
    }
    if ((str2 == null) || (str2.length() <= 0)) {
      throw new DCException(425, "Cannot add account: account ID does not exist");
    }
    if ((str3 == null) || (str3.length() <= 0)) {
      throw new DCException(426, "Cannot add account: currency code does not exist");
    }
    try
    {
      i = DBUtil.getNextId(paramConnection, DCAdapter.CONNECTIONTYPE, "DCAccountID");
    }
    catch (Exception localException1)
    {
      throw new DCException(303, "Failed to generate account ID");
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_Account( DCAccountID, BankID, AccountID, CurrencyCode, RoutingNumber, Extra ) VALUES ( ?, ?, ?, ?, ?, ? )");
      localPreparedStatement.setInt(1, i);
      localPreparedStatement.setString(2, str1);
      localPreparedStatement.setString(3, str2);
      localPreparedStatement.setString(4, str3);
      localPreparedStatement.setString(5, str4);
      localPreparedStatement.setString(6, null);
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_Account( DCAccountID, BankID, AccountID, CurrencyCode, RoutingNumber, Extra ) VALUES ( ?, ?, ?, ?, ?, ? )");
      if (!DCAdapter.isPagingDateBased())
      {
        String str5 = (String)paramHashMap.get("DATA_CLASSIFICATION");
        paramHashMap.put("DATA_CLASSIFICATION", "P");
        DCRecordCounter.addNewCounter(paramConnection, 1, i, "ACCT_TRANSACTION_INDEX", paramHashMap);
        DCRecordCounter.addNewCounter(paramConnection, 1, i, "DISBURSEMENT_TRANSACTION_INDEX", paramHashMap);
        DCRecordCounter.addNewCounter(paramConnection, 2, i, "LOCKBOX_TRANSACTION_INDEX", paramHashMap);
        paramHashMap.put("DATA_CLASSIFICATION", "I");
        DCRecordCounter.addNewCounter(paramConnection, 1, i, "ACCT_TRANSACTION_INDEX", paramHashMap);
        DCRecordCounter.addNewCounter(paramConnection, 1, i, "DISBURSEMENT_TRANSACTION_INDEX", paramHashMap);
        DCRecordCounter.addNewCounter(paramConnection, 2, i, "LOCKBOX_TRANSACTION_INDEX", paramHashMap);
        paramHashMap.put("DATA_CLASSIFICATION", str5);
      }
    }
    catch (Exception localException2)
    {
      throw new DCException(302, "Failed to add account", localException2);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static boolean accountExists(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      if (paramAccount.getRoutingNum() == null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT * FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT * FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, paramAccount.getRoutingNum());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?");
      }
      if (localResultSet.next())
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
      return bool;
    }
    catch (Exception localException)
    {
      throw new DCException(302, "Failed to check account.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static Account getAccount(Account paramAccount, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    Account localAccount = null;
    try
    {
      localAccount = getAccount(paramAccount, localConnection, paramHashMap);
    }
    catch (DCException localDCException2)
    {
      throw localDCException2;
    }
    finally
    {
      DCAdapter.releaseDBConnection(localConnection);
    }
    return localAccount;
  }
  
  protected static Account getAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str1 = paramAccount.getBankID();
    String str2 = paramAccount.getID();
    if ((str1 == null) || (str1.length() <= 0)) {
      throw new DCException(424, "Cannot retrieve account: bank ID does not exist");
    }
    if ((str2 == null) || (str2.length() <= 0)) {
      throw new DCException(425, "Cannot retrieve account: account ID does not exist");
    }
    try
    {
      String str3 = paramAccount.getRoutingNum();
      if (str3 != null)
      {
        localPreparedStatement1 = DCAdapter.getStatement(paramConnection, "SELECT a.DCAccountID, BankID, RoutingNumber, AccountID, CurrencyCode, CurrentLedger, CurrentAvail FROM DC_Account a LEFT OUTER JOIN DC_AccountHistory b ON a.DCAccountID = b.DCAccountID AND DataClassification='P' WHERE BankID = ? AND RoutingNumber = ? AND AccountID = ? ORDER BY DataDate DESC");
        localPreparedStatement1.setString(1, str1);
        localPreparedStatement1.setString(2, str3);
        localPreparedStatement1.setString(3, str2);
        localPreparedStatement1.setMaxRows(1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "SELECT a.DCAccountID, BankID, RoutingNumber, AccountID, CurrencyCode, CurrentLedger, CurrentAvail FROM DC_Account a LEFT OUTER JOIN DC_AccountHistory b ON a.DCAccountID = b.DCAccountID AND DataClassification='P' WHERE BankID = ? AND RoutingNumber = ? AND AccountID = ? ORDER BY DataDate DESC");
      }
      else
      {
        localPreparedStatement1 = DCAdapter.getStatement(paramConnection, "SELECT a.DCAccountID, BankID, RoutingNumber, AccountID, CurrencyCode, CurrentLedger, CurrentAvail from DC_Account a LEFT OUTER JOIN DC_AccountHistory b ON a.DCAccountID = b.DCAccountID AND DataClassification='P' WHERE BankID = ? AND RoutingNumber IS NULL AND AccountID = ? ORDER BY DataDate DESC");
        localPreparedStatement1.setString(1, paramAccount.getBankID());
        localPreparedStatement1.setString(2, paramAccount.getID());
        localPreparedStatement1.setMaxRows(1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "SELECT a.DCAccountID, BankID, RoutingNumber, AccountID, CurrencyCode, CurrentLedger, CurrentAvail from DC_Account a LEFT OUTER JOIN DC_AccountHistory b ON a.DCAccountID = b.DCAccountID AND DataClassification='P' WHERE BankID = ? AND RoutingNumber IS NULL AND AccountID = ? ORDER BY DataDate DESC");
      }
      localObject1 = new Accounts();
      if (localResultSet1.next())
      {
        localAccount = ((Accounts)localObject1).create(paramAccount.getBankID(), paramAccount.getID(), paramAccount.getNumber(), paramAccount.getTypeValue());
        String str4 = localResultSet1.getString(1);
        localAccount.setBankID(localResultSet1.getString(2));
        localAccount.setRoutingNum(localResultSet1.getString(3));
        localAccount.setCurrencyCode(localResultSet1.getString(5));
        Balance localBalance = new Balance();
        localBalance.setAmount(DCUtil.getCurrencyColumn(localResultSet1.getBigDecimal(6), localAccount.getCurrencyCode(), paramAccount.getLocale()));
        localAccount.setCurrentBalance(localBalance);
        localBalance = new Balance();
        localBalance.setAmount(DCUtil.getCurrencyColumn(localResultSet1.getBigDecimal(7), localAccount.getCurrencyCode(), paramAccount.getLocale()));
        localAccount.setAvailableBalance(localBalance);
        if (str3 != null)
        {
          localPreparedStatement2 = DCAdapter.getStatement(paramConnection, "SELECT CurrentLedger, CurrentAvail FROM DC_Account account, DC_AccountHistory history WHERE account.DCAccountID = history.DCAccountID AND BankID=? AND RoutingNumber=? AND AccountID=? AND DataClassification='I' ORDER BY DataDate DESC");
          localPreparedStatement2.setString(1, str1);
          localPreparedStatement2.setString(2, str3);
          localPreparedStatement2.setString(3, str2);
          localPreparedStatement2.setMaxRows(1);
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT CurrentLedger, CurrentAvail FROM DC_Account account, DC_AccountHistory history WHERE account.DCAccountID = history.DCAccountID AND BankID=? AND RoutingNumber=? AND AccountID=? AND DataClassification='I' ORDER BY DataDate DESC");
        }
        else
        {
          localPreparedStatement2 = DCAdapter.getStatement(paramConnection, "SELECT CurrentLedger, CurrentAvail FROM DC_Account account, DC_AccountHistory history WHERE account.DCAccountID = history.DCAccountID AND BankID=? AND RoutingNumber IS NULL AND AccountID=? AND DataClassification='I' ORDER BY DataDate DESC");
          localPreparedStatement2.setString(1, paramAccount.getBankID());
          localPreparedStatement2.setString(2, paramAccount.getID());
          localPreparedStatement2.setMaxRows(1);
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT CurrentLedger, CurrentAvail FROM DC_Account account, DC_AccountHistory history WHERE account.DCAccountID = history.DCAccountID AND BankID=? AND RoutingNumber IS NULL AND AccountID=? AND DataClassification='I' ORDER BY DataDate DESC");
        }
        if (localResultSet2.next())
        {
          localObject2 = new Balance();
          ((Balance)localObject2).setAmount(DCUtil.getCurrencyColumn(localResultSet2.getBigDecimal(1), localAccount.getCurrencyCode(), paramAccount.getLocale()));
          localAccount.setIntradayCurrentBalance((Balance)localObject2);
          localObject2 = new Balance();
          ((Balance)localObject2).setAmount(DCUtil.getCurrencyColumn(localResultSet2.getBigDecimal(2), localAccount.getCurrencyCode(), paramAccount.getLocale()));
          localAccount.setIntradayAvailableBalance((Balance)localObject2);
        }
        Object localObject2 = localAccount;
        return localObject2;
      }
      Account localAccount = null;
      return localAccount;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      if ((localException instanceof SQLException)) {
        ((DCException)localObject1).setCode(302);
      } else {
        ((DCException)localObject1).setCode(414);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      localPreparedStatement1 = null;
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeResultSet(localResultSet2);
    }
  }
  
  protected static void updateAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (accountExists(paramAccount, paramConnection, paramHashMap))
      {
        int i = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_Account SET CurrencyCode=?, Extra=? WHERE DCAccountID=?");
        localPreparedStatement.setString(1, paramAccount.getCurrencyCode());
        localPreparedStatement.setString(2, null);
        localPreparedStatement.setInt(3, i);
        DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_Account SET CurrencyCode=?, Extra=? WHERE DCAccountID=?");
      }
      else
      {
        addAccount(paramAccount, paramConnection, paramHashMap);
      }
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      if ((localException instanceof SQLException)) {
        localDCException2.setCode(302);
      } else {
        localDCException2.setCode(427);
      }
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void deleteAccount(Account paramAccount, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      int i = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      localObject1 = paramAccount.getRoutingNum();
      if (localObject1 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "DELETE FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, (String)localObject1);
        DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=?");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "DELETE FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber IS NULL");
      }
      String str = (String)paramHashMap.get("DATA_CLASSIFICATION");
      paramHashMap.put("DATA_CLASSIFICATION", "P");
      DCRecordCounter.deleteCounter(paramConnection, 1, i, "ACCT_TRANSACTION_INDEX", paramHashMap);
      DCRecordCounter.deleteCounter(paramConnection, 1, i, "DISBURSEMENT_TRANSACTION_INDEX", paramHashMap);
      DCRecordCounter.deleteCounter(paramConnection, 2, i, "LOCKBOX_TRANSACTION_INDEX", paramHashMap);
      paramHashMap.put("DATA_CLASSIFICATION", "I");
      DCRecordCounter.deleteCounter(paramConnection, 1, i, "ACCT_TRANSACTION_INDEX", paramHashMap);
      DCRecordCounter.deleteCounter(paramConnection, 1, i, "DISBURSEMENT_TRANSACTION_INDEX", paramHashMap);
      DCRecordCounter.deleteCounter(paramConnection, 2, i, "LOCKBOX_TRANSACTION_INDEX", paramHashMap);
      paramHashMap.put("DATA_CLASSIFICATION", str);
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      if ((localException instanceof SQLException)) {
        ((DCException)localObject1).setCode(302);
      } else {
        ((DCException)localObject1).setCode(428);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCAccount
 * JD-Core Version:    0.7.0.1
 */