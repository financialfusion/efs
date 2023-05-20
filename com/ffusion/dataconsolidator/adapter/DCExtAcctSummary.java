package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.util.MapUtil;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

class DCExtAcctSummary
{
  private static final String jdField_long = "INSERT INTO DC_ExtAcctSummary( DCAccountID, DataDate, DataSource, SummaryType, Amount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, ItemsCount, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String jdField_char = "UPDATE DC_ExtAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, SummaryType=?, Amount=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, ItemsCount = ?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND SummaryType = ? AND DataClassification=? ";
  private static final String jdField_void = "UPDATE DC_ExtAcctSummary SET DCAccountID=?, DataDate=?, SummaryType=?, Amount=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, ItemsCount=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND SummaryType=? AND DataClassification=? ";
  private static String jdField_else = "SELECT b.DataDate, b.SummaryType, b.Amount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.ItemsCount, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND DataClassification=? ";
  private static String jdField_case = "SELECT b.DataDate, b.SummaryType, b.Amount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.ItemsCount, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND DataClassification=? ";
  private static String jdField_byte = "SELECT b.DataDate, b.SummaryType, b.Amount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.ItemsCount, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND DataClassification=? ";
  private static String jdField_int = "SELECT b.DataDate, b.SummaryType, b.Amount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.ItemsCount, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND DataClassification=? ";
  private static String jdField_for = "SELECT b.DataDate, b.SummaryType, b.Amount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.ItemsCount, b.ExtendABeanXMLID FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate=? AND b.SummaryType=? AND a.RoutingNumber=? AND b.DataClassification=? ";
  private static String c = "SELECT b.DataDate, b.SummaryType, b.Amount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.ItemsCount, b.ExtendABeanXMLID FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate=? AND b.SummaryType=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ";
  private static String jdField_try = "SELECT ExtendABeanXMLID FROM DC_ExtAcctSummary WHERE DCAccountID=? AND DataDate=? AND SummaryType=? AND DataClassification=?";
  private static String jdField_do = "a.RoutingNumber ";
  private static String jdField_goto = " ORDER BY b.DataDate, b.SummaryType";
  private static String jdField_new = "DELETE FROM DC_ExtAcctSummary WHERE DCAccountID = ( SELECT DCAccountID FROM DC_Account where AccountID = ? AND BankID = ? AND RoutingNumber = ? ) AND SummaryType = ? AND DataDate >= ? AND DataDate <= ? AND DataClassification = ?";
  private static String jdField_if = " SELECT max(b.DataDate) FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String a = " SELECT max(b.DataDate) FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static String b = " SELECT max(b.DataDate)  FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_null = " SELECT max(b.DataDate) FROM DC_Account a, DC_ExtAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  
  protected static void addExtendedSummary(Account paramAccount, ExtendedAccountSummary paramExtendedAccountSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      String str1 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_IDENTIFIER", null);
      Calendar localCalendar = null;
      if (paramHashMap != null) {
        localCalendar = (Calendar)paramHashMap.get("BAI_FILE_DATE");
      }
      String str2 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_NAME", null);
      String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      if (!DCAccount.accountExists(paramAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(paramAccount, paramConnection, paramHashMap);
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_ExtAcctSummary( DCAccountID, DataDate, DataSource, SummaryType, Amount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, ItemsCount, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      int j = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (j == -1) {
        throw new DCException("Invalid account.");
      }
      ExtendedAccountSummary localExtendedAccountSummary1 = a(paramAccount, paramExtendedAccountSummary.getSummaryDate(), paramExtendedAccountSummary.getSummaryType(), paramConnection, str3);
      ExtendedAccountSummary localExtendedAccountSummary2 = null;
      if (localExtendedAccountSummary1 == null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_ExtAcctSummary( DCAccountID, DataDate, DataSource, SummaryType, Amount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, ItemsCount, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        localExtendedAccountSummary2 = paramExtendedAccountSummary;
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_ExtAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, SummaryType=?, Amount=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, ItemsCount = ?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND SummaryType = ? AND DataClassification=? ");
        localExtendedAccountSummary2 = localExtendedAccountSummary1;
        Currency localCurrency = null;
        com.ffusion.beans.DateTime localDateTime = null;
        localCurrency = paramExtendedAccountSummary.getAmt();
        if (localCurrency != null) {
          localExtendedAccountSummary2.setAmt(localCurrency);
        }
        localCurrency = paramExtendedAccountSummary.getImmedAvailAmt();
        if (localCurrency != null) {
          localExtendedAccountSummary2.setImmedAvailAmt(localCurrency);
        }
        localCurrency = paramExtendedAccountSummary.getOneDayAvailAmt();
        if (localCurrency != null) {
          localExtendedAccountSummary2.setOneDayAvailAmt(localCurrency);
        }
        localCurrency = paramExtendedAccountSummary.getMoreThanOneDayAvailAmt();
        if (localCurrency != null) {
          localExtendedAccountSummary2.setMoreThanOneDayAvailAmt(localCurrency);
        }
        localDateTime = paramExtendedAccountSummary.getValueDateTime();
        if (localDateTime != null) {
          localExtendedAccountSummary2.setValueDateTime(localDateTime);
        }
        int k = paramExtendedAccountSummary.getItemsCount();
        if (k != -1) {
          localExtendedAccountSummary2.setItemsCount(k);
        }
      }
      localPreparedStatement.setInt(1, j);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, localExtendedAccountSummary2.getSummaryDate());
      localPreparedStatement.setInt(3, paramInt);
      localPreparedStatement.setInt(4, localExtendedAccountSummary2.getSummaryType());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, localExtendedAccountSummary2.getAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, localExtendedAccountSummary2.getImmedAvailAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, localExtendedAccountSummary2.getOneDayAvailAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localExtendedAccountSummary2.getMoreThanOneDayAvailAmt());
      DCUtil.fillTimestampColumn(localPreparedStatement, 9, localExtendedAccountSummary2.getValueDateTime());
      localPreparedStatement.setInt(10, localExtendedAccountSummary2.getItemsCount());
      if (localExtendedAccountSummary1 == null)
      {
        localPreparedStatement.setLong(11, DCExtendABeanXML.addExtendABeanXML(paramConnection, localExtendedAccountSummary2.getExtendABeanXML(), paramHashMap));
        localPreparedStatement.setString(12, null);
        localPreparedStatement.setString(13, str1);
        localPreparedStatement.setString(14, str3);
        localPreparedStatement.setString(15, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 16, localCalendar);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_ExtAcctSummary( DCAccountID, DataDate, DataSource, SummaryType, Amount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, ItemsCount, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      }
      else
      {
        long l1 = a(paramConnection, j, localExtendedAccountSummary2, str3);
        DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
        a(localExtendedAccountSummary2, paramExtendedAccountSummary);
        long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localExtendedAccountSummary2.getExtendABeanXML(), paramHashMap);
        localPreparedStatement.setString(11, str1);
        localPreparedStatement.setLong(12, l2);
        localPreparedStatement.setString(13, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 14, localCalendar);
        localPreparedStatement.setInt(15, j);
        DCUtil.fillTimestampColumn(localPreparedStatement, 16, localExtendedAccountSummary2.getSummaryDate());
        localPreparedStatement.setInt(17, localExtendedAccountSummary2.getSummaryType());
        localPreparedStatement.setString(18, str3);
        DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_ExtAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, SummaryType=?, Amount=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, ItemsCount = ?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND SummaryType = ? AND DataClassification=? ");
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add extended account summary record: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 451;
      throw new DCException(i, "Failed to add extended account summary record: SQLException.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void updateExtendedSummary(Connection paramConnection, Account paramAccount, ExtendedAccountSummary paramExtendedAccountSummary, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      i = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (i == -1) {
        throw new DCException("Invalid account.");
      }
      ExtendedAccountSummary localExtendedAccountSummary = a(paramAccount, paramExtendedAccountSummary.getSummaryDate(), paramExtendedAccountSummary.getSummaryType(), paramConnection, str);
      if (localExtendedAccountSummary == null) {
        throw new DCException(614, "No extended account summary was found to update for the date " + paramExtendedAccountSummary.getSummaryDate().getTime().toString() + " , and account with accoutid=" + paramAccount.getID() + ", bankid= " + paramAccount.getBankID() + ", routing number=" + paramAccount.getRoutingNum() + ", and data classification '" + str + "'.");
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_ExtAcctSummary SET DCAccountID=?, DataDate=?, SummaryType=?, Amount=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, ItemsCount=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND SummaryType=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, i);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramExtendedAccountSummary.getSummaryDate());
      localPreparedStatement.setInt(3, paramExtendedAccountSummary.getSummaryType());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramExtendedAccountSummary.getAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, paramExtendedAccountSummary.getImmedAvailAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, paramExtendedAccountSummary.getOneDayAvailAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, paramExtendedAccountSummary.getMoreThanOneDayAvailAmt());
      DCUtil.fillTimestampColumn(localPreparedStatement, 8, paramExtendedAccountSummary.getValueDateTime());
      localPreparedStatement.setInt(9, paramExtendedAccountSummary.getItemsCount());
      long l1 = a(paramConnection, i, paramExtendedAccountSummary, str);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, paramExtendedAccountSummary.getExtendABeanXML(), paramHashMap);
      localPreparedStatement.setLong(10, l2);
      localPreparedStatement.setInt(11, i);
      DCUtil.fillTimestampColumn(localPreparedStatement, 12, paramExtendedAccountSummary.getSummaryDate());
      localPreparedStatement.setInt(13, paramExtendedAccountSummary.getSummaryType());
      localPreparedStatement.setString(14, str);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_ExtAcctSummary SET DCAccountID=?, DataDate=?, SummaryType=?, Amount=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, ItemsCount=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND SummaryType=? AND DataClassification=? ");
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to update extended account summary record: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 614;
      throw new DCException(i, "Failed to update extended account summary record: SQLException.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static long a(Connection paramConnection, int paramInt, ExtendedAccountSummary paramExtendedAccountSummary, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    com.ffusion.beans.DateTime localDateTime = null;
    int i = -1;
    long l;
    try
    {
      localDateTime = paramExtendedAccountSummary.getSummaryDate();
      i = paramExtendedAccountSummary.getSummaryType();
      localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_try);
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, localDateTime);
      localPreparedStatement.setInt(3, i);
      localPreparedStatement.setString(4, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_try);
      if (localResultSet.next()) {
        l = localResultSet.getLong(1);
      } else {
        throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_EXTACCTSUMMARY table with DCACCOUNTID " + paramInt + ", DATADATE " + localDateTime + ", and SUMMARYTYPE " + i);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get ExtendABean XML ID: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int j = (localException instanceof SQLException) ? 302 : 434;
      throw new DCException(j, "Failed to get ExtendABean XML ID.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
    return l;
  }
  
  private static void a(ExtendedAccountSummary paramExtendedAccountSummary1, ExtendedAccountSummary paramExtendedAccountSummary2)
  {
    if ((paramExtendedAccountSummary1 != null) && (paramExtendedAccountSummary2 != null)) {
      paramExtendedAccountSummary1.putAll(paramExtendedAccountSummary2);
    }
  }
  
  protected static ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ExtendedAccountSummaries localExtendedAccountSummaries1 = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localResultSet = jdField_if(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      localExtendedAccountSummaries1 = new ExtendedAccountSummaries();
      ExtendedAccountSummary localExtendedAccountSummary = null;
      while (localResultSet.next())
      {
        localExtendedAccountSummary = a(paramAccount, localResultSet, paramConnection);
        localExtendedAccountSummaries1.add(localExtendedAccountSummary);
      }
      ExtendedAccountSummaries localExtendedAccountSummaries2 = localExtendedAccountSummaries1;
      return localExtendedAccountSummaries2;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 422;
      DCException localDCException2 = new DCException(i, "Failed to get extended account summary.", localException);
      throw localDCException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ExtendedAccountSummary a(Account paramAccount, Calendar paramCalendar, int paramInt, Connection paramConnection, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    ResultSet localResultSet = null;
    try
    {
      String str = paramAccount.getRoutingNum();
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_for);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setInt(4, paramInt);
        localPreparedStatement.setString(5, str);
        localPreparedStatement.setString(6, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_for);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, c);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setInt(4, paramInt);
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, c);
      }
      ExtendedAccountSummary localExtendedAccountSummary = null;
      if (localResultSet.next()) {
        localExtendedAccountSummary = a(paramAccount, localResultSet, paramConnection);
      }
      localObject2 = localExtendedAccountSummary;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get extended account summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 422;
      Object localObject2 = new DCException(i, "Failed to get extended account summary.", localException);
      throw ((Throwable)localObject2);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ExtendedAccountSummary a(Account paramAccount, ResultSet paramResultSet, Connection paramConnection)
    throws DCException
  {
    try
    {
      ExtendedAccountSummary localExtendedAccountSummary = new ExtendedAccountSummary();
      localExtendedAccountSummary.setAccountID(paramAccount.getID());
      localExtendedAccountSummary.setAccountNumber(paramAccount.getNumber());
      localExtendedAccountSummary.setBankID(paramAccount.getBankID());
      localExtendedAccountSummary.setRoutingNumber(paramAccount.getRoutingNum());
      localExtendedAccountSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localExtendedAccountSummary.setSummaryType(paramResultSet.getInt(2));
      localExtendedAccountSummary.setAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localExtendedAccountSummary.setImmedAvailAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localExtendedAccountSummary.setOneDayAvailAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localExtendedAccountSummary.setMoreThanOneDayAvailAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localExtendedAccountSummary.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(7), paramAccount.getLocale()));
      localExtendedAccountSummary.setItemsCount(paramResultSet.getInt(8));
      DCUtil.fillExtendABean(paramConnection, localExtendedAccountSummary, paramResultSet, 9);
      return localExtendedAccountSummary;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get extended account summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 422;
      DCException localDCException2 = new DCException(i, "Failed to get extended account summary.", localException);
      throw localDCException2;
    }
  }
  
  private static ResultSet jdField_if(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(jdField_else);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_goto);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(6, paramAccount.getRoutingNum());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(jdField_case);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_goto);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(5, paramAccount.getRoutingNum());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_byte);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(5, paramAccount.getRoutingNum());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(jdField_int);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get extended account summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 422;
      DCException localDCException2 = new DCException(i, "Failed to get extended account summary.", localException);
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static int deleteExtendedAccountSummary(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt, String paramString, HashMap paramHashMap)
    throws DCException
  {
    DCUtil.isAccountInfoSufficient(paramAccount);
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_new);
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      localPreparedStatement.setString(3, paramAccount.getRoutingNum());
      localPreparedStatement.setInt(4, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar1);
      DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar2);
      localPreparedStatement.setString(7, paramString);
      int i = DBUtil.executeUpdate(localPreparedStatement, jdField_new);
      return i;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get delete extended account summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int j = (localException instanceof SQLException) ? 302 : 454;
      DCException localDCException2 = new DCException(j, "Failed to delete extended account summary.", localException);
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static ArrayList getExtendedSummaryMapList(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCUtil.isAccountInfoSufficient(paramAccount);
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_if(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = a(localResultSet, paramAccount, paramConnection);
      return localArrayList;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList a(ResultSet paramResultSet, Account paramAccount, Connection paramConnection)
    throws DCException
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      HashMap localHashMap1 = null;
      localObject = null;
      while (paramResultSet.next())
      {
        com.ffusion.beans.DateTime localDateTime = DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale());
        if (localObject == null)
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        else if (!localDateTime.isSameDayInYearAs((com.ffusion.util.beans.DateTime)localObject))
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        a(localHashMap1, "DATADATE", localDateTime);
        Integer localInteger = new Integer(paramResultSet.getInt(2));
        Currency localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramResultSet.getString(10), paramAccount.getLocale());
        a(localHashMap1, localInteger, SummaryValue.getInstance(localCurrency, paramResultSet.getInt(8)));
        ExtendedAccountSummary localExtendedAccountSummary = new ExtendedAccountSummary();
        DCUtil.fillExtendABean(paramConnection, localExtendedAccountSummary, paramResultSet, 9);
        HashMap localHashMap2 = localExtendedAccountSummary.getHash();
        localHashMap1.putAll(localHashMap2);
      }
      return localArrayList;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get extended account summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 422;
      Object localObject = new DCException(i, "Failed to get extended account summary.", localException);
      throw ((Throwable)localObject);
    }
  }
  
  private static void a(HashMap paramHashMap, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 != null) {
      paramHashMap.put(paramObject1, paramObject2);
    }
  }
  
  protected static com.ffusion.beans.DateTime getExtendedSummaryMaxDate(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    Object localObject1 = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localResultSet = jdField_if(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      if (localResultSet.next())
      {
        localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(1), paramAccount.getLocale());
        return localDateTime;
      }
      com.ffusion.beans.DateTime localDateTime = null;
      return localDateTime;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 464;
      DCException localDCException2 = new DCException(i, "Failed to get extended account summary.", localException);
      throw localDCException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ResultSet a(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(jdField_if);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_goto);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(6, paramAccount.getRoutingNum());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(a);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_goto);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(5, paramAccount.getRoutingNum());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(b);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(5, paramAccount.getRoutingNum());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(jdField_null);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_do, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get extended account summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 464;
      DCException localDCException2 = new DCException(i, "Failed to get extended account summary.", localException);
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCExtAcctSummary
 * JD-Core Version:    0.7.0.1
 */