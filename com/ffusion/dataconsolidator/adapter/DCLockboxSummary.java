package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
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
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

class DCLockboxSummary
{
  private static final String jdField_void = "INSERT INTO DC_LockboxSummary( DCAccountID, DataDate, DataSource, TotalCredits, TotalDebits, TotalNumCredits, TotalNumDebits, ImmediateFloat, OneDayFloat, TwoDayFloat, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
  private static final String jdField_if = "UPDATE DC_LockboxSummary SET DCAccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ";
  private static final String b = "UPDATE DC_LockboxSummary SET DCAccountID=?, DataDate=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ";
  private static String jdField_char = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_LockboxSummary b WHERE b.DCAccountID= a.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_byte = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_LockboxSummary b WHERE b.DCAccountID= a.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static String jdField_new = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_LockboxSummary b WHERE b.DCAccountID= a.DCAccountID AND a.AccountID=? AND a.BankID=? AND  b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_for = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_LockboxSummary b WHERE b.DCAccountID= a.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static String jdField_do = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM DC_Account a, DC_LockboxSummary b WHERE b.DCAccountID= a.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ";
  private static String jdField_null = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM DC_Account a, DC_LockboxSummary b WHERE b.DCAccountID= a.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ";
  private static String jdField_int = "SELECT ExtendABeanXMLID FROM DC_LockboxSummary WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ";
  private static String a = " a.RoutingNumber ";
  private static String jdField_goto = " ORDER BY b.DataDate ";
  private static String jdField_long = " SELECT max(b.DataDate) FROM DC_Account a, DC_LockboxSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_else = " SELECT max(b.DataDate) FROM DC_Account a, DC_LockboxSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static String jdField_case = " SELECT max(b.DataDate)  FROM DC_Account a, DC_LockboxSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_try = " SELECT max(b.DataDate) FROM DC_Account a, DC_LockboxSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  
  public static void add(LockboxSummary paramLockboxSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    LockboxAccount localLockboxAccount = paramLockboxSummary.getLockboxAccount();
    DCUtil.isAccountInfoSufficient(localLockboxAccount);
    try
    {
      String str1 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_IDENTIFIER", null);
      Calendar localCalendar = null;
      if (paramHashMap != null) {
        localCalendar = (Calendar)paramHashMap.get("BAI_FILE_DATE");
      }
      String str2 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_NAME", null);
      String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      Account localAccount = DCUtil.getAccount(localLockboxAccount);
      if (!DCAccount.accountExists(localAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(localAccount, paramConnection, paramHashMap);
      }
      int j = DCAdapter.getDCAccountID(paramConnection, localLockboxAccount.getAccountID(), localLockboxAccount.getBankID(), localLockboxAccount.getRoutingNumber());
      if (j == -1) {
        throw new DCException(414, "Account not found.");
      }
      LockboxSummary localLockboxSummary1 = a(localLockboxAccount, paramLockboxSummary.getSummaryDate(), paramConnection, str3);
      LockboxSummary localLockboxSummary2 = null;
      if (localLockboxSummary1 == null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_LockboxSummary( DCAccountID, DataDate, DataSource, TotalCredits, TotalDebits, TotalNumCredits, TotalNumDebits, ImmediateFloat, OneDayFloat, TwoDayFloat, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
        localLockboxSummary2 = paramLockboxSummary;
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_LockboxSummary SET DCAccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
        localLockboxSummary2 = localLockboxSummary1;
        Currency localCurrency = null;
        int k = -1;
        localCurrency = paramLockboxSummary.getTotalLockboxCredits();
        if (localCurrency != null) {
          localLockboxSummary2.setTotalLockboxCredits(localCurrency);
        }
        localCurrency = paramLockboxSummary.getTotalLockboxDebits();
        if (localCurrency != null) {
          localLockboxSummary2.setTotalLockboxDebits(localCurrency);
        }
        k = paramLockboxSummary.getTotalNumLockboxCredits();
        if (k != -1) {
          localLockboxSummary2.setTotalNumLockboxCredits(k);
        }
        k = paramLockboxSummary.getTotalNumLockboxDebits();
        if (k != -1) {
          localLockboxSummary2.setTotalNumLockboxDebits(k);
        }
        localCurrency = paramLockboxSummary.getImmediateFloat();
        if (localCurrency != null) {
          localLockboxSummary2.setImmediateFloat(localCurrency);
        }
        localCurrency = paramLockboxSummary.getOneDayFloat();
        if (localCurrency != null) {
          localLockboxSummary2.setOneDayFloat(localCurrency);
        }
        localCurrency = paramLockboxSummary.getTwoDayFloat();
        if (localCurrency != null) {
          localLockboxSummary2.setTwoDayFloat(localCurrency);
        }
      }
      localPreparedStatement.setInt(1, j);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, localLockboxSummary2.getSummaryDate());
      localPreparedStatement.setInt(3, paramInt);
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, localLockboxSummary2.getTotalLockboxCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, localLockboxSummary2.getTotalLockboxDebits());
      localPreparedStatement.setInt(6, localLockboxSummary2.getTotalNumLockboxCredits());
      localPreparedStatement.setInt(7, localLockboxSummary2.getTotalNumLockboxDebits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localLockboxSummary2.getImmediateFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localLockboxSummary2.getOneDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 10, localLockboxSummary2.getTwoDayFloat());
      if (localLockboxSummary1 == null)
      {
        localPreparedStatement.setLong(11, DCExtendABeanXML.addExtendABeanXML(paramConnection, localLockboxSummary2.getExtendABeanXML(), paramHashMap));
        localPreparedStatement.setString(12, null);
        localPreparedStatement.setString(13, str1);
        localPreparedStatement.setString(14, str3);
        localPreparedStatement.setString(15, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 16, localCalendar);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_LockboxSummary( DCAccountID, DataDate, DataSource, TotalCredits, TotalDebits, TotalNumCredits, TotalNumDebits, ImmediateFloat, OneDayFloat, TwoDayFloat, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
      }
      else
      {
        long l1 = a(paramConnection, j, localLockboxSummary2.getSummaryDate(), str3);
        DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
        a(localLockboxSummary2, paramLockboxSummary);
        long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localLockboxSummary2.getExtendABeanXML(), paramHashMap);
        localPreparedStatement.setString(11, str1);
        localPreparedStatement.setLong(12, l2);
        localPreparedStatement.setString(13, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 14, localCalendar);
        localPreparedStatement.setInt(15, j);
        DCUtil.fillTimestampColumn(localPreparedStatement, 16, localLockboxSummary2.getSummaryDate());
        localPreparedStatement.setString(17, str3);
        DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_LockboxSummary SET DCAccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add lockbox summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39514;
      throw new DCException(i, "Failed to add lockbox summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void updateLockboxSummary(Connection paramConnection, LockboxSummary paramLockboxSummary, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    LockboxAccount localLockboxAccount = paramLockboxSummary.getLockboxAccount();
    DCUtil.isAccountInfoSufficient(localLockboxAccount);
    try
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      i = DCAdapter.getDCAccountID(paramConnection, localLockboxAccount.getAccountID(), localLockboxAccount.getBankID(), localLockboxAccount.getRoutingNumber());
      if (i == -1) {
        throw new DCException(414, "Account not found.");
      }
      LockboxSummary localLockboxSummary = a(localLockboxAccount, paramLockboxSummary.getSummaryDate(), paramConnection, str);
      if (localLockboxSummary == null) {
        throw new DCException(39522, "No lockbox summary was found to update for the date " + paramLockboxSummary.getSummaryDate().getTime().toString() + " , and account with accoutid=" + localLockboxAccount.getAccountID() + ", bankid= " + localLockboxAccount.getBankID() + ", routing number=" + localLockboxAccount.getRoutingNumber() + ", and data classification '" + str + "'.");
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_LockboxSummary SET DCAccountID=?, DataDate=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, i);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramLockboxSummary.getSummaryDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 3, paramLockboxSummary.getTotalLockboxCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramLockboxSummary.getTotalLockboxDebits());
      localPreparedStatement.setInt(5, paramLockboxSummary.getTotalNumLockboxCredits());
      localPreparedStatement.setInt(6, paramLockboxSummary.getTotalNumLockboxDebits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, paramLockboxSummary.getImmediateFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, paramLockboxSummary.getOneDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, paramLockboxSummary.getTwoDayFloat());
      long l1 = a(paramConnection, i, paramLockboxSummary.getSummaryDate(), str);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, paramLockboxSummary.getExtendABeanXML(), paramHashMap);
      localPreparedStatement.setLong(10, l2);
      localPreparedStatement.setInt(11, i);
      DCUtil.fillTimestampColumn(localPreparedStatement, 12, paramLockboxSummary.getSummaryDate());
      localPreparedStatement.setString(13, str);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_LockboxSummary SET DCAccountID=?, DataDate=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add lockbox summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39514;
      throw new DCException(i, "Failed to add lockbox summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static long a(Connection paramConnection, int paramInt, DateTime paramDateTime, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_int);
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramDateTime);
      localPreparedStatement.setString(3, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_int);
      if (localResultSet.next()) {
        l = localResultSet.getLong(1);
      } else {
        throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_LOCKBOXSUMMARY table with DCACCOUNTID " + paramInt + " and DATADATE " + paramDateTime);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get EXTENDABEANXMLID for lockbox summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 434;
      throw new DCException(i, "Failed to get EXTENDABEANXMLID for lockbox summary:", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
    return l;
  }
  
  private static void a(LockboxSummary paramLockboxSummary1, LockboxSummary paramLockboxSummary2)
  {
    if ((paramLockboxSummary1 != null) && (paramLockboxSummary2 != null)) {
      paramLockboxSummary1.putAll(paramLockboxSummary2);
    }
  }
  
  public static LockboxSummaries getLockboxSummaries(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    LockboxSummaries localLockboxSummaries1 = null;
    ResultSet localResultSet = null;
    DCUtil.isAccountInfoSufficient(paramLockboxAccount);
    try
    {
      localResultSet = a(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      localLockboxSummaries1 = new LockboxSummaries();
      LockboxSummary localLockboxSummary = null;
      while (localResultSet.next())
      {
        localLockboxSummary = a(paramLockboxAccount, localResultSet, paramConnection);
        localLockboxSummaries1.add(localLockboxSummary);
      }
      LockboxSummaries localLockboxSummaries2 = localLockboxSummaries1;
      return localLockboxSummaries2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox summaries: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39513;
      throw new DCException(i, "Failed to get lockbox summaries:", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static LockboxSummary a(LockboxAccount paramLockboxAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str = paramLockboxAccount.getRoutingNumber();
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_do);
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramLockboxAccount.getRoutingNumber());
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_do);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_null);
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_null);
      }
      LockboxSummary localLockboxSummary1 = null;
      if (localResultSet.next()) {
        localLockboxSummary1 = a(paramLockboxAccount, localResultSet, paramConnection);
      }
      LockboxSummary localLockboxSummary2 = localLockboxSummary1;
      return localLockboxSummary2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox summary: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39513;
      throw new DCException(i, "Failed to get lockbox summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static LockboxSummary a(LockboxAccount paramLockboxAccount, ResultSet paramResultSet, Connection paramConnection)
    throws Exception
  {
    try
    {
      LockboxSummary localLockboxSummary = new LockboxSummary();
      localLockboxSummary.setLockboxAccount(paramLockboxAccount);
      localLockboxSummary.setSummaryDate(new DateTime(paramResultSet.getTimestamp(1), paramLockboxAccount.getLocale()));
      localLockboxSummary.setTotalLockboxCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      localLockboxSummary.setTotalLockboxDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      i = paramResultSet.getInt(4);
      if (!paramResultSet.wasNull()) {
        localLockboxSummary.setTotalNumLockboxCredits(i);
      }
      int j = paramResultSet.getInt(5);
      if (!paramResultSet.wasNull()) {
        localLockboxSummary.setTotalNumLockboxDebits(j);
      }
      localLockboxSummary.setImmediateFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      localLockboxSummary.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      localLockboxSummary.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      DCUtil.fillExtendABean(paramConnection, localLockboxSummary, paramResultSet, 9);
      return localLockboxSummary;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to read lockbox summary record from database: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39513;
      throw new DCException(i, "Failed to read lockbox summary record from database.", localException);
    }
  }
  
  private static ResultSet a(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
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
          localStringBuffer.append(jdField_char);
          DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
          localStringBuffer.append(jdField_goto);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(jdField_byte);
          DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
          localStringBuffer.append(jdField_goto);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_new);
        DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(jdField_for);
        DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(4, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox summary result set: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39513;
      throw new DCException(i, "Failed to get lockbox summary result set.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static ArrayList getLockboxSummaryMapList(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = a(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = a(localResultSet, paramLockboxAccount, paramConnection);
      return localArrayList;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList a(ResultSet paramResultSet, LockboxAccount paramLockboxAccount, Connection paramConnection)
    throws DCException
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap1 = null;
    Object localObject = null;
    try
    {
      while (paramResultSet.next())
      {
        DateTime localDateTime = DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramLockboxAccount.getLocale());
        if (localObject == null)
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        else if (!localDateTime.isSameDayInYearAs(localObject))
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        int j = paramResultSet.getInt(4);
        Integer localInteger1 = null;
        if (!paramResultSet.wasNull()) {
          localInteger1 = new Integer(j);
        } else {
          j = -1;
        }
        int k = paramResultSet.getInt(5);
        Integer localInteger2 = null;
        if (!paramResultSet.wasNull()) {
          localInteger2 = new Integer(k);
        } else {
          k = -1;
        }
        String str = paramResultSet.getString(10);
        a(localHashMap1, "DATADATE", localDateTime);
        a(localHashMap1, "TOTALCREDITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), str, paramLockboxAccount.getLocale()), j));
        a(localHashMap1, "TOTALDEBITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramLockboxAccount.getLocale()), k));
        a(localHashMap1, "TOTALNUMCREDITS", localInteger1);
        a(localHashMap1, "TOTALNUMDEBITS", localInteger2);
        a(localHashMap1, "IMMEDIATEFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), str, paramLockboxAccount.getLocale())));
        a(localHashMap1, "ONEDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), str, paramLockboxAccount.getLocale())));
        a(localHashMap1, "TWODAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), str, paramLockboxAccount.getLocale())));
        LockboxSummary localLockboxSummary = new LockboxSummary();
        DCUtil.fillExtendABean(paramConnection, localLockboxSummary, paramResultSet, 9);
        HashMap localHashMap2 = localLockboxSummary.getHash();
        if (localHashMap2.size() != 0)
        {
          Iterator localIterator = localHashMap2.entrySet().iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            a(localHashMap1, localEntry.getKey(), localEntry.getValue());
          }
        }
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox summary map list: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39513;
      throw new DCException(i, "Failed to get lockbox summary map list.", localException);
    }
    return localArrayList;
  }
  
  private static void a(HashMap paramHashMap, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 != null) {
      paramHashMap.put(paramObject1, paramObject2);
    }
  }
  
  public static DateTime getLockboxSummariesMaxDate(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    Object localObject1 = null;
    ResultSet localResultSet = null;
    DCUtil.isAccountInfoSufficient(paramLockboxAccount);
    try
    {
      localResultSet = jdField_if(paramLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      if (localResultSet.next())
      {
        localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(1), paramLockboxAccount.getLocale());
        return localDateTime;
      }
      DateTime localDateTime = null;
      return localDateTime;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox summaries: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39523;
      throw new DCException(i, "Failed to get lockbox summaries:", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ResultSet jdField_if(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
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
          localStringBuffer.append(jdField_long);
          DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(jdField_else);
          DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_case);
        DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(jdField_try);
        DCUtil.appendNullWhereClause(localStringBuffer, a, paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(jdField_goto);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(4, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox summary result set: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39523;
      throw new DCException(i, "Failed to get lockbox summary result set.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCLockboxSummary
 * JD-Core Version:    0.7.0.1
 */