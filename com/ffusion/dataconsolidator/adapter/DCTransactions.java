package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.dataconsolidator.ImportedFile;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

class DCTransactions
{
  private static final String jdField_long = "INSERT INTO DC_Transactions( DCAccountID, DCTransactionIndex, DataDate, DataSource, TransID, TransTypeID, TransCategoryID, TransTrackingID, Description, Memo, ReferenceNumber, Amount, RunningBalance, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, PostingDate, DueDate, FixedDepRate, PayeePayor, PayorNum, OrigUser, PONum, ExtendABeanXMLID, Extra, BAIFileIdentifier, InstNumber, InstBankName, TransSubTypeID, DataSourceFileName, DataSourceFileDate, TransDate, DataClassification, DataSourceLoadTime ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
  private static final String jdField_int = "UPDATE DC_Transactions SET DataDate=?, TransID=?, TransTypeID=?, TransCategoryID=?, TransTrackingID=?, Description=?, Memo=?, ReferenceNumber=?, Amount=?, RunningBalance=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, BankRefNum=?, CustRefNum=?, PostingDate=?, DueDate=?, FixedDepRate=?, PayeePayor=?, PayorNum=?, OrigUser=?, PONum=?, Extra=?, InstNumber=?, InstBankName=?, TransSubTypeID=?, TransDate=? WHERE DCAccountID=( SELECT DCAccountID FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=? ) AND DCTransactionIndex=? AND DataClassification=?";
  private static final String f = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.TransTypeID, b.TransCategoryID, b.TransTrackingID, b.Description, b.Memo, b.ReferenceNumber, b.Amount, b.RunningBalance, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.PostingDate, b.DueDate, b.FixedDepRate, b.PayeePayor, b.PayorNum, b.OrigUser, b.PONum, b.ExtendABeanXMLID, b.InstNumber, b.InstBankName, b.TransSubTypeID, TransDate, DataSourceLoadTime, b.DataClassification FROM DC_Transactions b, DC_Account a, DC_TransTypeDesc c WHERE a.DCAccountID = b.DCAccountID AND c.TRANS_TYPE_ID = b.TransTypeID AND c.LOCALE = ? ";
  private static final String jdField_char = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.TransTypeID, b.TransCategoryID, b.TransTrackingID, b.Description, b.Memo, b.ReferenceNumber, b.Amount, b.RunningBalance, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.PostingDate, b.DueDate, b.FixedDepRate, b.PayeePayor, b.PayorNum, b.OrigUser, b.PONum, b.ExtendABeanXMLID, b.InstNumber, b.InstBankName, b.TransSubTypeID, TransDate, DataSourceLoadTime, b.DataClassification  FROM DC_Transactions b, DC_Account a WHERE b.DCAccountID=a.DCAccountID  AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber=? AND b.DataClassification=? AND b.DataDate>=? AND b.DataDate<=?  ORDER BY b.DCTransactionIndex desc ";
  private static final String jdField_else = " ORDER BY b.DCTransactionIndex ";
  private static final String c = "SELECT COUNT(*) FROM DC_Transactions b, DC_Account a WHERE b.DCAccountID=a.DCAccountID ";
  private static final String a = "SELECT * FROM DC_Transactions WHERE AccountID=? AND BankID=? AND a.RoutingNumber=? AND DataClassification=? ";
  private static final String jdField_if = " a.RoutingNumber ";
  private static final String jdField_goto = "SELECT min( b.DCTransactionIndex ) FROM DC_Account a, DC_Transactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex > ? ";
  private static final String d = "SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_Transactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex < ? ";
  private static final String j = "SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_Transactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex >= ? AND b.DCTransactionIndex < ?";
  private static final String jdField_byte = "SELECT sum (ImmedAvailAmount), count(DCTransactionIndex) FROM DC_Transactions, DC_Account a WHERE DC_Transactions.DCAccountID = a.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND DataDate >= ? AND DataDate <= ? AND DC_Transactions.ImmedAvailAmount IS NOT NULL AND DC_Transactions.Amount >= 0 AND DC_Transactions.DataClassification = ? ";
  private static final String n = "SELECT sum (OneDayAvailAmount), count(DCTransactionIndex) FROM DC_Transactions, DC_Account a WHERE DC_Transactions.DCAccountID = a.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND DataDate >= ? AND DataDate <= ? AND DC_Transactions.OneDayAvailAmount IS NOT NULL AND DC_Transactions.Amount >= 0 AND DC_Transactions.DataClassification = ? ";
  private static final String l = "SELECT sum (MoreOneDayAvailAm), count(DCTransactionIndex) FROM DC_Transactions, DC_Account a WHERE DC_Transactions.DCAccountID = a.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND DataDate >= ? AND DataDate <= ? AND DC_Transactions.MoreOneDayAvailAm IS NOT NULL AND DC_Transactions.Amount >= 0 AND DC_Transactions.DataClassification = ? ";
  private static final String jdField_new = "UPDATE DC_Transactions SET DataSourceLoadTime=? WHERE DataSource=?";
  private static final String h = "SELECT DISTINCT ";
  private static final String g = " FROM DC_Transactions a, DC_Account b WHERE b.DCAccountID = a.DCAccountID AND b.BankID=? AND b.AccountID=? AND DataClassification=?";
  private static final String e = " ORDER BY ";
  private static final String jdField_case = " a.BankRefNum ";
  private static final String i = " a.CustRefNum ";
  private static final String p = " AND b.RoutingNumber=? ";
  private static final String jdField_void = " AND b.RoutingNumber IS NULL ";
  private static final String k = " AND a.DataDate>=? ";
  private static final String jdField_for = " AND a.DataDate<=? ";
  private static final DecimalFormat jdField_null = new DecimalFormat("00000000000");
  private static final String jdField_try = "00000000001";
  private static final String b = "99999999999";
  private static final String m = " ASC ";
  private static final String jdField_do = " DESC ";
  private static final int o = 1000;
  
  protected static void addTransactions(Account paramAccount, Transactions paramTransactions, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    if ((paramTransactions == null) || (paramTransactions.size() == 0)) {
      return;
    }
    try
    {
      if (!DCAccount.accountExists(paramAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(paramAccount, paramConnection, paramHashMap);
      }
      int i1 = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (i1 == -1) {
        throw new DCException("Invalid account.");
      }
      paramHashMap.put("DCACCOUNTID", new Integer(i1));
      HashMap localHashMap = new HashMap();
      Object localObject2;
      for (int i2 = 0; i2 < paramTransactions.size(); i2++)
      {
        localObject1 = (Transaction)paramTransactions.get(i2);
        localObject2 = ((Transaction)localObject1).getProcessingDate();
        String str = DateFormatUtil.getFormatter("yyyyMMdd").format(((DateTime)localObject2).getTime());
        Transactions localTransactions = (Transactions)localHashMap.get(str);
        if (localTransactions == null)
        {
          localTransactions = new Transactions(paramTransactions.getLocale());
          localHashMap.put(str, localTransactions);
        }
        localTransactions.add(localObject1);
      }
      ArrayList localArrayList = new ArrayList(localHashMap.keySet());
      Qsort.sortStrings(localArrayList, 1);
      Object localObject1 = localArrayList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Transactions)localHashMap.get(((Iterator)localObject1).next());
        a(paramConnection, i1, paramAccount, paramInt, (Transactions)localObject2, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
  }
  
  private static void a(Connection paramConnection, int paramInt1, Account paramAccount, int paramInt2, Transactions paramTransactions, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    Transaction localTransaction = null;
    long l1 = 0L;
    int i1 = DCAdapter.BATCHSIZE;
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
      l2 = a(paramConnection, paramAccount, paramTransactions, paramHashMap);
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_Transactions( DCAccountID, DCTransactionIndex, DataDate, DataSource, TransID, TransTypeID, TransCategoryID, TransTrackingID, Description, Memo, ReferenceNumber, Amount, RunningBalance, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, PostingDate, DueDate, FixedDepRate, PayeePayor, PayorNum, OrigUser, PONum, ExtendABeanXMLID, Extra, BAIFileIdentifier, InstNumber, InstBankName, TransSubTypeID, DataSourceFileName, DataSourceFileDate, TransDate, DataClassification, DataSourceLoadTime ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
      int i2 = 0;
      while (i2 < paramTransactions.size())
      {
        int i3 = 0;
        while ((i3 < i1) && (i2 < paramTransactions.size()))
        {
          localTransaction = (Transaction)paramTransactions.get(i2);
          long l3 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localTransaction.getExtendABeanXML(), paramHashMap);
          if (localTransaction.getTransactionIndex() != 0L) {
            throw new DCException("Transaction contains invalid index.");
          }
          if (DCAdapter.isPagingDateBased()) {
            l1 = l2++;
          } else {
            l1 = DCRecordCounter.getNextIndex(paramConnection, 1, paramInt1, "ACCT_TRANSACTION_INDEX", paramHashMap);
          }
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setLong(2, l1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, localTransaction.getProcessingDate());
          localPreparedStatement.setInt(4, paramInt2);
          localPreparedStatement.setString(5, localTransaction.getID());
          localPreparedStatement.setInt(6, localTransaction.getTypeValue());
          localPreparedStatement.setInt(7, localTransaction.getCategory());
          localPreparedStatement.setString(8, localTransaction.getTrackingID());
          localPreparedStatement.setString(9, localTransaction.getDescription());
          localPreparedStatement.setString(10, localTransaction.getMemo());
          localPreparedStatement.setString(11, localTransaction.getReferenceNumber());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 12, localTransaction.getAmountValue());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 13, localTransaction.getRunningBalanceValue());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localTransaction.getImmediateAvailAmount());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 15, localTransaction.getOneDayAvailAmount());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 16, localTransaction.getMoreThanOneDayAvailAmount());
          DCUtil.fillTimestampColumn(localPreparedStatement, 17, localTransaction.getValueDate());
          localPreparedStatement.setString(18, DCAdapter.padRefNum(localTransaction.getBankReferenceNumber(), DCAdapter.getBankReferenceNumberType()));
          localPreparedStatement.setString(19, DCAdapter.padRefNum(localTransaction.getCustomerReferenceNumber(), DCAdapter.getCustomerReferenceNumberType()));
          DCUtil.fillTimestampColumn(localPreparedStatement, 20, localTransaction.getPostingDate());
          DCUtil.fillTimestampColumn(localPreparedStatement, 21, localTransaction.getDueDate());
          localPreparedStatement.setFloat(22, localTransaction.getFixedDepositRate());
          localPreparedStatement.setString(23, localTransaction.getPayeePayor());
          localPreparedStatement.setString(24, localTransaction.getPayorNum());
          localPreparedStatement.setString(25, localTransaction.getOrigUser());
          localPreparedStatement.setString(26, localTransaction.getPONum());
          localPreparedStatement.setLong(27, l3);
          localPreparedStatement.setString(28, null);
          localPreparedStatement.setString(29, str1);
          localPreparedStatement.setString(30, localTransaction.getInstrumentNumber());
          localPreparedStatement.setString(31, localTransaction.getInstrumentBankName());
          localPreparedStatement.setInt(32, localTransaction.getSubTypeValue());
          localPreparedStatement.setString(33, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 34, localCalendar);
          DCUtil.fillTimestampColumn(localPreparedStatement, 35, localTransaction.getDateValue());
          localPreparedStatement.setString(36, str3);
          DCUtil.fillTimestampColumn(localPreparedStatement, 37, localTransaction.getDataSourceLoadTime());
          localPreparedStatement.addBatch();
          i3++;
          i2++;
        }
        localPreparedStatement.executeBatch();
      }
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  public static int updateTransactions(Account paramAccount, Transactions paramTransactions, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return 0;
    }
    PreparedStatement localPreparedStatement = null;
    Transaction localTransaction = null;
    long l1 = 0L;
    int i1 = DCAdapter.BATCHSIZE;
    int i2 = 0;
    int i3 = 0;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_Transactions SET DataDate=?, TransID=?, TransTypeID=?, TransCategoryID=?, TransTrackingID=?, Description=?, Memo=?, ReferenceNumber=?, Amount=?, RunningBalance=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, BankRefNum=?, CustRefNum=?, PostingDate=?, DueDate=?, FixedDepRate=?, PayeePayor=?, PayorNum=?, OrigUser=?, PONum=?, Extra=?, InstNumber=?, InstBankName=?, TransSubTypeID=?, TransDate=? WHERE DCAccountID=( SELECT DCAccountID FROM DC_Account WHERE AccountID=? AND BankID=? AND RoutingNumber=? ) AND DCTransactionIndex=? AND DataClassification=?");
      for (int i4 = 0; i4 < paramTransactions.size(); i4++)
      {
        localTransaction = (Transaction)paramTransactions.get(i4);
        localObject1 = (String)paramHashMap.get("DATA_CLASSIFICATION");
        if ((localObject1 == null) || (((String)localObject1).length() <= 0)) {
          localObject1 = "P";
        }
        DCUtil.fillTimestampColumn(localPreparedStatement, 1, localTransaction.getProcessingDate());
        localPreparedStatement.setString(2, localTransaction.getID());
        localPreparedStatement.setInt(3, localTransaction.getTypeValue());
        localPreparedStatement.setInt(4, localTransaction.getCategory());
        localPreparedStatement.setString(5, localTransaction.getTrackingID());
        localPreparedStatement.setString(6, localTransaction.getDescription());
        localPreparedStatement.setString(7, localTransaction.getMemo());
        localPreparedStatement.setString(8, localTransaction.getReferenceNumber());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localTransaction.getAmountValue());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 10, localTransaction.getRunningBalanceValue());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 11, localTransaction.getImmediateAvailAmount());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 12, localTransaction.getOneDayAvailAmount());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 13, localTransaction.getMoreThanOneDayAvailAmount());
        DCUtil.fillTimestampColumn(localPreparedStatement, 14, localTransaction.getValueDate());
        localPreparedStatement.setString(15, DCAdapter.padRefNum(localTransaction.getBankReferenceNumber(), DCAdapter.getBankReferenceNumberType()));
        localPreparedStatement.setString(16, DCAdapter.padRefNum(localTransaction.getCustomerReferenceNumber(), DCAdapter.getCustomerReferenceNumberType()));
        DCUtil.fillTimestampColumn(localPreparedStatement, 17, localTransaction.getPostingDate());
        DCUtil.fillTimestampColumn(localPreparedStatement, 18, localTransaction.getDueDate());
        localPreparedStatement.setFloat(19, localTransaction.getFixedDepositRate());
        localPreparedStatement.setString(20, localTransaction.getPayeePayor());
        localPreparedStatement.setString(21, localTransaction.getPayorNum());
        localPreparedStatement.setString(22, localTransaction.getOrigUser());
        localPreparedStatement.setString(23, localTransaction.getPONum());
        localPreparedStatement.setNull(24, 12);
        localPreparedStatement.setString(25, localTransaction.getInstrumentNumber());
        localPreparedStatement.setString(26, localTransaction.getInstrumentBankName());
        localPreparedStatement.setInt(27, localTransaction.getSubTypeValue());
        DCUtil.fillTimestampColumn(localPreparedStatement, 28, localTransaction.getDateValue());
        localPreparedStatement.setString(29, paramAccount.getID());
        localPreparedStatement.setString(30, paramAccount.getBankID());
        localPreparedStatement.setString(31, paramAccount.getRoutingNum());
        localPreparedStatement.setLong(32, localTransaction.getTransactionIndex());
        localPreparedStatement.setString(33, (String)localObject1);
        localPreparedStatement.addBatch();
        i3++;
        i2++;
        if (i2 >= i1)
        {
          localPreparedStatement.executeBatch();
          i2 = 0;
        }
      }
      if (i2 > 0) {
        localPreparedStatement.executeBatch();
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      if ((localException instanceof SQLException)) {
        ((DCException)localObject1).setCode(302);
      } else {
        ((DCException)localObject1).setCode(453);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      localPreparedStatement = null;
    }
    return i3;
  }
  
  protected static Transaction getLatestTransactionByAccountDates(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    Transaction localTransaction = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if ((paramString == null) || (paramString.length() <= 0)) {
      paramString = "P";
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.TransTypeID, b.TransCategoryID, b.TransTrackingID, b.Description, b.Memo, b.ReferenceNumber, b.Amount, b.RunningBalance, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.PostingDate, b.DueDate, b.FixedDepRate, b.PayeePayor, b.PayorNum, b.OrigUser, b.PONum, b.ExtendABeanXMLID, b.InstNumber, b.InstBankName, b.TransSubTypeID, TransDate, DataSourceLoadTime, b.DataClassification  FROM DC_Transactions b, DC_Account a WHERE b.DCAccountID=a.DCAccountID  AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber=? AND b.DataClassification=? AND b.DataDate>=? AND b.DataDate<=?  ORDER BY b.DCTransactionIndex desc ");
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      localPreparedStatement.setString(3, paramAccount.getRoutingNum());
      localPreparedStatement.setString(4, paramString);
      Timestamp localTimestamp = new Timestamp(paramCalendar1.getTime().getTime());
      localPreparedStatement.setTimestamp(5, localTimestamp);
      localTimestamp = new Timestamp(paramCalendar2.getTime().getTime());
      localPreparedStatement.setTimestamp(6, localTimestamp);
      localPreparedStatement.setMaxRows(1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.TransTypeID, b.TransCategoryID, b.TransTrackingID, b.Description, b.Memo, b.ReferenceNumber, b.Amount, b.RunningBalance, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.PostingDate, b.DueDate, b.FixedDepRate, b.PayeePayor, b.PayorNum, b.OrigUser, b.PONum, b.ExtendABeanXMLID, b.InstNumber, b.InstBankName, b.TransSubTypeID, TransDate, DataSourceLoadTime, b.DataClassification  FROM DC_Transactions b, DC_Account a WHERE b.DCAccountID=a.DCAccountID  AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber=? AND b.DataClassification=? AND b.DataDate>=? AND b.DataDate<=?  ORDER BY b.DCTransactionIndex desc ");
      if (!localResultSet.next())
      {
        localObject1 = null;
        return localObject1;
      }
      localObject1 = new Transactions(paramAccount.getLocale());
      localTransaction = ((Transactions)localObject1).createNoAdd();
      a(localTransaction, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
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
        ((DCException)localObject1).setCode(419);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localPreparedStatement = null;
    }
    return localTransaction;
  }
  
  private static void a(Transaction paramTransaction, ResultSet paramResultSet, String paramString, Locale paramLocale)
    throws Exception
  {
    paramTransaction.setTransactionIndex(paramResultSet.getLong(1));
    paramTransaction.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(2), paramLocale));
    paramTransaction.setID(paramResultSet.getString(3));
    paramTransaction.setType(paramResultSet.getInt(4));
    paramTransaction.setCategory(paramResultSet.getInt(5));
    paramTransaction.setTrackingID(paramResultSet.getString(6));
    paramTransaction.setDescription(paramResultSet.getString(7));
    paramTransaction.setMemo(paramResultSet.getString(8));
    paramTransaction.setReferenceNumber(paramResultSet.getString(9));
    paramTransaction.setAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramString, paramLocale));
    paramTransaction.setRunningBalance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramString, paramLocale));
    paramTransaction.setImmediateAvailAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramString, paramLocale));
    paramTransaction.setOneDayAvailAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramString, paramLocale));
    paramTransaction.setMoreThanOneDayAvailAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramString, paramLocale));
    paramTransaction.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(15), paramLocale));
    paramTransaction.setBankReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(16), DCAdapter.getBankReferenceNumberType()));
    paramTransaction.setCustomerReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(17), DCAdapter.getCustomerReferenceNumberType()));
    paramTransaction.setPostingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(18), paramLocale));
    paramTransaction.setDueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(19), paramLocale));
    paramTransaction.setFixedDepositRate(paramResultSet.getFloat(20));
    paramTransaction.setPayeePayor(paramResultSet.getString(21));
    paramTransaction.setPayorNum(paramResultSet.getString(22));
    paramTransaction.setOrigUser(paramResultSet.getString(23));
    paramTransaction.setPONum(paramResultSet.getString(24));
    DCUtil.fillExtendABean(paramTransaction, paramResultSet, 25);
    paramTransaction.setInstrumentNumber(paramResultSet.getString(26));
    paramTransaction.setInstrumentBankName(paramResultSet.getString(27));
    paramTransaction.setSubType(paramResultSet.getInt(28));
    paramTransaction.setDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(29), paramLocale));
    paramTransaction.setDataSourceLoadTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(30), paramLocale));
    paramTransaction.setDataClassification(paramResultSet.getString(31));
  }
  
  private static ResultSet a(Account paramAccount, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap, Connection paramConnection)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().put("Account", paramAccount.getID());
    if (paramAccount.getRoutingNum() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramAccount.getRoutingNum());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramAccount.getBankID());
    if (!localReportCriteria.getSearchCriteria().containsKey("DataClassification"))
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", str);
    }
    int i1 = MapUtil.getIntValue(paramHashMap, "PAGESIZE", DCAdapter.PAGESIZE);
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.TransTypeID, b.TransCategoryID, b.TransTrackingID, b.Description, b.Memo, b.ReferenceNumber, b.Amount, b.RunningBalance, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.PostingDate, b.DueDate, b.FixedDepRate, b.PayeePayor, b.PayorNum, b.OrigUser, b.PONum, b.ExtendABeanXMLID, b.InstNumber, b.InstBankName, b.TransSubTypeID, TransDate, DataSourceLoadTime, b.DataClassification FROM DC_Transactions b, DC_Account a, DC_TransTypeDesc c WHERE a.DCAccountID = b.DCAccountID AND c.TRANS_TYPE_ID = b.TransTypeID AND c.LOCALE = ? ");
      a(localStringBuffer, paramAccount, paramPagingContext, paramHashMap);
      ArrayList localArrayList = a(localStringBuffer, paramPagingContext, paramBoolean, paramHashMap);
      a(localStringBuffer, paramPagingContext, paramBoolean, null, paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramAccount.getLocale().toString());
      int i2 = 2;
      i2 = a(localPreparedStatement, i2, paramPagingContext, paramHashMap);
      a(localPreparedStatement, i2, localArrayList, paramHashMap);
      if (i1 != -1) {
        localPreparedStatement.setMaxRows(i1);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      localPreparedStatement = null;
    }
    return localResultSet;
  }
  
  protected static Transactions getTransactions(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    try
    {
      return getTransactions(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
  }
  
  protected static Transactions getTransactions(Connection paramConnection, Account paramAccount, Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      PagingContext localPagingContext = new PagingContext(null, null);
      localPagingContext.setMap(new HashMap());
      localObject1 = new ReportCriteria();
      ((ReportCriteria)localObject1).setSearchCriteria(paramProperties);
      localPagingContext.getMap().put("ReportCriteria", localObject1);
      Integer localInteger = (Integer)paramHashMap.get("PAGESIZE");
      paramHashMap.put("PAGESIZE", new Integer(-1));
      localResultSet = a(paramAccount, localPagingContext, false, paramHashMap, paramConnection);
      paramHashMap.put("PAGESIZE", localInteger);
      Transactions localTransactions = new Transactions();
      while (localResultSet.next())
      {
        localObject2 = localTransactions.create();
        a((Transaction)localObject2, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
      }
      a(localPagingContext, localTransactions);
      Object localObject2 = localTransactions;
      return localObject2;
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
        ((DCException)localObject1).setCode(419);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static Transactions getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
      localPagingContext.setMap(new HashMap());
      localObject1 = new ReportCriteria();
      localPagingContext.getMap().put("ReportCriteria", localObject1);
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      if (paramCalendar1 != null) {
        ((ReportCriteria)localObject1).getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
      }
      if (paramCalendar2 != null) {
        ((ReportCriteria)localObject1).getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
      }
      Integer localInteger = (Integer)paramHashMap.get("PAGESIZE");
      paramHashMap.put("PAGESIZE", new Integer(-1));
      localResultSet = a(paramAccount, localPagingContext, false, paramHashMap, paramConnection);
      paramHashMap.put("PAGESIZE", localInteger);
      Transactions localTransactions = new Transactions();
      while (localResultSet.next())
      {
        localObject2 = localTransactions.create();
        a((Transaction)localObject2, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
      }
      a(localPagingContext, localTransactions);
      Object localObject2 = localTransactions;
      return localObject2;
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
        ((DCException)localObject1).setCode(419);
      }
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected static Transactions getFDITransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      Account localAccount = new Account();
      localAccount.setID(paramFixedDepositInstrument.getAccountID());
      localAccount.setBankID(paramFixedDepositInstrument.getBankID());
      localAccount.setRoutingNum(paramFixedDepositInstrument.getRoutingNumber());
      localObject1 = new PagingContext(paramCalendar1, paramCalendar2);
      ((PagingContext)localObject1).setMap(new HashMap());
      ReportCriteria localReportCriteria = new ReportCriteria();
      ((PagingContext)localObject1).getMap().put("ReportCriteria", localReportCriteria);
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
      localReportCriteria.getSearchCriteria().setProperty("InstrumentNumber", paramFixedDepositInstrument.getInstrumentNumber());
      localReportCriteria.getSearchCriteria().setProperty("InstrumentBankName", paramFixedDepositInstrument.getInstrumentBankName());
      Integer localInteger = (Integer)paramHashMap.get("PAGESIZE");
      paramHashMap.put("PAGESIZE", new Integer(-1));
      localResultSet = a(localAccount, (PagingContext)localObject1, false, paramHashMap, localConnection);
      paramHashMap.put("PAGESIZE", localInteger);
      Transactions localTransactions = new Transactions();
      while (localResultSet.next())
      {
        localObject2 = localTransactions.create();
        a((Transaction)localObject2, localResultSet, paramFixedDepositInstrument.getCurrency(), paramFixedDepositInstrument.getLocale());
      }
      a((PagingContext)localObject1, localTransactions);
      Object localObject2 = localTransactions;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      ((DCException)localObject1).setCode(419);
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static Transactions getPagedTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap = new HashMap();
    localPagingContext.setMap(localHashMap);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      jdField_if(localConnection, paramAccount, localPagingContext, paramHashMap);
      a(localConnection, paramAccount, localPagingContext, paramHashMap);
      localResultSet = a(paramAccount, localPagingContext, false, paramHashMap, localConnection);
      Transactions localTransactions1 = new Transactions();
      while (localResultSet.next())
      {
        localObject1 = localTransactions1.create();
        a((Transaction)localObject1, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
      }
      a(localPagingContext, localTransactions1);
      localTransactions1.setSortedBy("TRANS_IDX");
      localObject1 = localPagingContext.getMap();
      paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", ((HashMap)localObject1).get("LOWER_BOUND_TransactionIndex"));
      paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", ((HashMap)localObject1).get("UPPER_BOUND_TransactionIndex"));
      Transactions localTransactions2 = localTransactions1;
      return localTransactions2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      ((DCException)localObject1).setCode(419);
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      Transactions localTransactions1 = new Transactions();
      localConnection = DCAdapter.getDBConnection();
      jdField_if(localConnection, paramAccount, paramPagingContext, paramHashMap);
      a(localConnection, paramAccount, paramPagingContext, paramHashMap);
      if (!paramPagingContext.getMap().containsKey("UPPER_BOUND_TransactionIndex"))
      {
        Transactions localTransactions2 = localTransactions1;
        return localTransactions2;
      }
      paramPagingContext.getMap().remove("CurrentPage");
      paramPagingContext.getMap().remove("PageSettings");
      int i1 = 1;
      paramPagingContext.getMap().put("CurrentPage", new Integer(i1));
      a(paramPagingContext.getMap());
      localResultSet = a(paramAccount, paramPagingContext, true, paramHashMap, localConnection);
      Transaction localTransaction = null;
      while (localResultSet.next())
      {
        localTransaction = localTransactions1.create();
        a(localTransaction, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
      }
      a(paramPagingContext, localTransactions1);
      Transactions localTransactions3 = localTransactions1;
      return localTransactions3;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      localDCException2.setCode(419);
      throw localDCException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  protected static Transactions getRecentTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      jdField_if(localConnection, paramAccount, paramPagingContext, paramHashMap);
      a(localConnection, paramAccount, paramPagingContext, paramHashMap);
      localResultSet = a(paramAccount, paramPagingContext, false, paramHashMap, localConnection);
      Transactions localTransactions = new Transactions();
      while (localResultSet.next())
      {
        localObject1 = localTransactions.create();
        a((Transaction)localObject1, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
      }
      a(paramPagingContext, localTransactions);
      localObject1 = localTransactions;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      ((DCException)localObject1).setCode(417);
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  protected static Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    ResultSet localResultSet = null;
    HashMap localHashMap = paramPagingContext.getMap();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      Transactions localTransactions1 = new Transactions();
      int i1 = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i1 != -1)
      {
        i1++;
        localHashMap.put("CurrentPage", new Integer(i1));
        a(localHashMap);
      }
      localResultSet = a(paramAccount, paramPagingContext, true, paramHashMap, localConnection);
      Transaction localTransaction = null;
      while (localResultSet.next())
      {
        localTransaction = localTransactions1.create();
        a(localTransaction, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
      }
      a(paramPagingContext, localTransactions1);
      Transactions localTransactions2 = localTransactions1;
      return localTransactions2;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      localDCException2.setCode(419);
      throw localDCException2;
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
    int i1 = MapUtil.getIntValue(paramHashMap, "CurrentPage", 1);
    if (localIntMap.get(i1) == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.putAll(paramHashMap);
      localIntMap.put(i1, localHashMap);
    }
  }
  
  protected static Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      Transactions localTransactions = new Transactions();
      localObject1 = paramPagingContext.getMap();
      ReportCriteria localReportCriteria = (ReportCriteria)((HashMap)localObject1).get("ReportCriteria");
      int i1 = MapUtil.getIntValue((HashMap)localObject1, "CurrentPage", -1);
      if (i1 != -1)
      {
        i1--;
        ((HashMap)localObject1).put("CurrentPage", new Integer(i1));
        localObject2 = (IntMap)((HashMap)localObject1).get("PageSettings");
        HashMap localHashMap = (HashMap)((IntMap)localObject2).get(i1);
        if (localReportCriteria != null) {
          for (int i2 = 0; i2 < localReportCriteria.getSortCriteria().size(); i2++)
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(i2);
            ((HashMap)localObject1).put("SORT_VALUE_MIN_" + localReportSortCriterion.getName(), localHashMap.get("SORT_VALUE_MIN_" + localReportSortCriterion.getName()));
            ((HashMap)localObject1).put("SORT_VALUE_MAX_" + localReportSortCriterion.getName(), localHashMap.get("SORT_VALUE_MAX_" + localReportSortCriterion.getName()));
          }
        }
        ((HashMap)localObject1).put("SORT_VALUE_MIN_TransactionIndex", localHashMap.get("SORT_VALUE_MIN_TransactionIndex"));
        ((HashMap)localObject1).put("SORT_VALUE_MAX_TransactionIndex", localHashMap.get("SORT_VALUE_MAX_TransactionIndex"));
      }
      if (i1 == -1) {
        localResultSet = a(paramAccount, paramPagingContext, false, paramHashMap, localConnection);
      } else {
        localResultSet = a(paramAccount, paramPagingContext, true, paramHashMap, localConnection);
      }
      while (localResultSet.next())
      {
        localObject2 = localTransactions.createNoAdd();
        a((Transaction)localObject2, localResultSet, paramAccount.getCurrencyCode(), paramAccount.getLocale());
        if (i1 == -1) {
          localTransactions.add(0, localObject2);
        } else {
          localTransactions.add(localObject2);
        }
      }
      a(paramPagingContext, localTransactions);
      Object localObject2 = localTransactions;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new DCException(localException);
      ((DCException)localObject1).setCode(419);
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  protected static int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  protected static int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    if (paramProperties == null) {
      paramProperties = new Properties();
    }
    if (!paramProperties.containsKey("DataClassification"))
    {
      localObject1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      paramProperties.put("DataClassification", localObject1);
    }
    paramProperties.put("Account", paramAccount.getID());
    if (paramAccount.getRoutingNum() != null) {
      paramProperties.put("RoutingNumber", paramAccount.getRoutingNum());
    }
    paramProperties.put("BankId", paramAccount.getBankID());
    Object localObject1 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      paramProperties.put("StartDate", ((DateFormat)localObject1).format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      paramProperties.put("EndDate", ((DateFormat)localObject1).format(paramCalendar2.getTime()));
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      StringBuffer localStringBuffer = new StringBuffer("SELECT COUNT(*) FROM DC_Transactions b, DC_Account a WHERE b.DCAccountID=a.DCAccountID ");
      a(localStringBuffer, paramAccount, paramProperties, paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
      int i1 = 1;
      i1 = a(localPreparedStatement, i1, paramProperties, paramHashMap);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        int i2 = localResultSet.getInt(1);
        int i3 = i2;
        return i3;
      }
      throw new DCException("Error occurred while retrieving the number of transactions.");
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      localDCException2.setCode(418);
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    for (int i1 = 0; i1 < localReportSortCriteria.size(); i1++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i1);
      paramStringBuffer.append(a(localReportSortCriterion.getName())).append(", ");
    }
    paramStringBuffer.append(" DCTransactionIndex ");
  }
  
  private static void jdField_if(Connection paramConnection, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramAccount.getID());
    if (paramAccount.getRoutingNum() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramAccount.getRoutingNum());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramAccount.getBankID());
    if (!localReportCriteria.getSearchCriteria().containsKey("DataClassification"))
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
      localStringBuffer.append(" FROM DC_Account a, DC_Transactions b, DC_TransTypeDesc c ");
      localStringBuffer.append(" WHERE a.DCAccountID = b.DCAccountID AND c.TRANS_TYPE_ID = b.TransTypeID AND c.LOCALE = ? ");
      a(localStringBuffer, paramAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(false), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramAccount.getLocale().toString());
      int i1 = 2;
      i1 = a(localPreparedStatement, i1, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int i2 = 0; i2 < localReportSortCriteria.size(); i2++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i2);
          paramPagingContext.getMap().put("UPPER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, i2 + 1, paramAccount.getCurrencyCode(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("UPPER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramAccount.getCurrencyCode(), localReportCriteria.getLocale()));
      }
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add sort-by columns for transaction retrieval.", localSQLException);
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
  
  private static void a(Connection paramConnection, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramAccount.getID());
    if (paramAccount.getRoutingNum() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramAccount.getRoutingNum());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramAccount.getBankID());
    if (!localReportCriteria.getSearchCriteria().containsKey("DataClassification"))
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
      localStringBuffer.append(" FROM DC_Account a, DC_Transactions b, DC_TransTypeDesc c ");
      localStringBuffer.append(" WHERE a.DCAccountID = b.DCAccountID AND c.TRANS_TYPE_ID = b.TransTypeID AND c.LOCALE = ? ");
      a(localStringBuffer, paramAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(true), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramAccount.getLocale().toString());
      int i1 = 2;
      i1 = a(localPreparedStatement, i1, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int i2 = 0; i2 < localReportSortCriteria.size(); i2++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i2);
          paramPagingContext.getMap().put("LOWER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, i2 + 1, paramAccount.getCurrencyCode(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("LOWER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramAccount.getCurrencyCode(), localReportCriteria.getLocale()));
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
  
  private static long a(Connection paramConnection, Account paramAccount, Transactions paramTransactions, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    Transaction localTransaction = (Transaction)paramTransactions.get(0);
    DateTime localDateTime = localTransaction.getProcessingDate();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str2 = localDateFormat.format(localDateTime.getTime());
    StringBuffer localStringBuffer1 = new StringBuffer(str2);
    localStringBuffer1.append("00000000001");
    StringBuffer localStringBuffer2 = new StringBuffer(str2);
    localStringBuffer2.append("99999999999");
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer3 = new StringBuffer();
    localStringBuffer3.append("SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_Transactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex >= ? AND b.DCTransactionIndex < ?");
    DCUtil.appendNullWhereClause(localStringBuffer3, " a.RoutingNumber ", paramAccount.getRoutingNum());
    PreparedStatement localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer3.toString());
    long l1 = -1L;
    try
    {
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      localPreparedStatement.setString(3, str1);
      localPreparedStatement.setLong(4, Long.parseLong(localStringBuffer1.toString()));
      localPreparedStatement.setLong(5, Long.parseLong(localStringBuffer2.toString()));
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(6, paramAccount.getRoutingNum());
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
          String str6 = jdField_null.format(l3);
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
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
    return l1;
  }
  
  protected static Currency getImmedAvailAmountSum(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    StringBuffer localStringBuffer = new StringBuffer("SELECT sum (ImmedAvailAmount), count(DCTransactionIndex) FROM DC_Transactions, DC_Account a WHERE DC_Transactions.DCAccountID = a.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND DataDate >= ? AND DataDate <= ? AND DC_Transactions.ImmedAvailAmount IS NOT NULL AND DC_Transactions.Amount >= 0 AND DC_Transactions.DataClassification = ? ");
    DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramAccount.getRoutingNum());
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
      DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
      localPreparedStatement.setString(5, str);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(6, paramAccount.getRoutingNum());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Currency localCurrency1 = null;
      if (localResultSet.next())
      {
        localCurrency1 = DCUtil.getCurrencyColumn(localResultSet.getBigDecimal(1), paramAccount.getCurrencyCode(), paramAccount.getLocale());
        int i1 = localResultSet.getInt(2);
        if (i1 == 0) {
          localCurrency1 = new Currency(new BigDecimal(0.0D), paramAccount.getLocale());
        }
      }
      else
      {
        localCurrency1 = new Currency(new BigDecimal(0.0D), paramAccount.getLocale());
      }
      Currency localCurrency2 = localCurrency1;
      return localCurrency2;
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
  
  protected static Currency getOneDayAvailAmountSum(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    StringBuffer localStringBuffer = new StringBuffer("SELECT sum (OneDayAvailAmount), count(DCTransactionIndex) FROM DC_Transactions, DC_Account a WHERE DC_Transactions.DCAccountID = a.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND DataDate >= ? AND DataDate <= ? AND DC_Transactions.OneDayAvailAmount IS NOT NULL AND DC_Transactions.Amount >= 0 AND DC_Transactions.DataClassification = ? ");
    DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramAccount.getRoutingNum());
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
      DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
      localPreparedStatement.setString(5, str);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(6, paramAccount.getRoutingNum());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Currency localCurrency1 = null;
      if (localResultSet.next())
      {
        localCurrency1 = DCUtil.getCurrencyColumn(localResultSet.getBigDecimal(1), paramAccount.getCurrencyCode(), paramAccount.getLocale());
        int i1 = localResultSet.getInt(2);
        if (i1 == 0) {
          localCurrency1 = new Currency(new BigDecimal(0.0D), paramAccount.getLocale());
        }
      }
      else
      {
        localCurrency1 = new Currency(new BigDecimal(0.0D), paramAccount.getLocale());
      }
      Currency localCurrency2 = localCurrency1;
      return localCurrency2;
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
  
  protected static Currency getMoreThanOneDayAvailAmountSum(Connection paramConnection, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    StringBuffer localStringBuffer = new StringBuffer("SELECT sum (MoreOneDayAvailAm), count(DCTransactionIndex) FROM DC_Transactions, DC_Account a WHERE DC_Transactions.DCAccountID = a.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND DataDate >= ? AND DataDate <= ? AND DC_Transactions.MoreOneDayAvailAm IS NOT NULL AND DC_Transactions.Amount >= 0 AND DC_Transactions.DataClassification = ? ");
    DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramAccount.getRoutingNum());
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
      DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
      localPreparedStatement.setString(5, str);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(6, paramAccount.getRoutingNum());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Currency localCurrency1 = null;
      if (localResultSet.next())
      {
        localCurrency1 = DCUtil.getCurrencyColumn(localResultSet.getBigDecimal(1), paramAccount.getCurrencyCode(), paramAccount.getLocale());
        int i1 = localResultSet.getInt(2);
        if (i1 == 0) {
          localCurrency1 = new Currency(new BigDecimal(0.0D), paramAccount.getLocale());
        }
      }
      else
      {
        localCurrency1 = new Currency(new BigDecimal(0.0D), paramAccount.getLocale());
      }
      Currency localCurrency2 = localCurrency1;
      return localCurrency2;
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
  
  private static void a(StringBuffer paramStringBuffer, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
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
    a(paramStringBuffer, paramAccount, localProperties, paramHashMap);
  }
  
  private static void a(StringBuffer paramStringBuffer, Account paramAccount, Properties paramProperties, HashMap paramHashMap)
  {
    if (paramProperties == null) {
      return;
    }
    String str1 = paramProperties.getProperty("Account");
    if ((str1 != null) && (str1.length() != 0)) {
      paramStringBuffer.append(" and a.AccountID = ? ");
    }
    str1 = paramProperties.getProperty("RoutingNumber");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and a.RoutingNumber = ? ");
    } else if (str1 == null) {
      paramStringBuffer.append(" and a.RoutingNumber is null ");
    }
    str1 = paramProperties.getProperty("BankId");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and a.BankID = ? ");
    }
    str1 = paramProperties.getProperty("InstrumentBankName");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and b.InstBankName = ? ");
    }
    str1 = paramProperties.getProperty("InstrumentNumber");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and b.InstNumber = ? ");
    }
    str1 = paramProperties.getProperty("DataClassification");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equals("A"))) {
      paramStringBuffer.append(" and b.DataClassification = ? ");
    }
    str1 = paramProperties.getProperty("StartDate");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and b.DataDate >= ? ");
    }
    str1 = paramProperties.getProperty("EndDate");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and b.DataDate <= ? ");
    }
    str1 = paramProperties.getProperty("Customer Reference Number (Min)");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and b.CustRefNum>=?");
    }
    str1 = paramProperties.getProperty("Customer Reference Number (Max)");
    if ((str1 != null) && (str1.length() > 0)) {
      paramStringBuffer.append(" and b.CustRefNum<=?");
    }
    str1 = paramProperties.getProperty("CustomerReferenceNumber");
    if (str1 != null) {
      paramStringBuffer.append(" and b.CustRefNum=?");
    }
    str1 = paramProperties.getProperty("CustomerReferenceNumberNull");
    if (str1 != null) {
      paramStringBuffer.append(" and b.CustRefNum is null");
    }
    str1 = paramProperties.getProperty("ZBAWithCustomerReferenceNumberNull");
    if (str1 != null) {
      paramStringBuffer.append(" and ((b.TransSubTypeID != 575) or (b.TransSubTypeID = 575) and (b.CustRefNum is null)) and ((b.TransSubTypeID != 275) or (b.TransSubTypeID = 275) and (b.CustRefNum is null)) ");
    }
    str1 = paramProperties.getProperty("BankReferenceNumber");
    if (str1 != null) {
      paramStringBuffer.append(" and b.BankRefNum=?");
    }
    str1 = paramProperties.getProperty("BankReferenceNumberNull");
    if (str1 != null) {
      paramStringBuffer.append(" and b.BankRefNum is null");
    }
    str1 = paramProperties.getProperty("ZBAWithBankReferenceNumberNull");
    if (str1 != null) {
      paramStringBuffer.append(" and ((b.TransSubTypeID != 575) or (b.TransSubTypeID = 575) and (b.BankRefNum is null)) and ((b.TransSubTypeID != 275) or (b.TransSubTypeID = 275) and (b.BankRefNum is null)) ");
    }
    String str2 = paramProperties.getProperty("MinimumAmount");
    String str3 = paramProperties.getProperty("MaximumAmount");
    if ((str2 != null) && (str2.length() > 0))
    {
      if ((str3 != null) && (str3.length() > 0)) {
        try
        {
          Currency localCurrency1 = new Currency(new BigDecimal(str2), paramAccount.getLocale());
          localObject = new Currency(new BigDecimal(str3), paramAccount.getLocale());
          paramStringBuffer.append(" and ( ( b.Amount>=? and b.Amount<=? ) or ( b.Amount>=? and b.Amount<=? ) )");
        }
        catch (Exception localException1) {}
      } else {
        try
        {
          Currency localCurrency2 = new Currency(new BigDecimal(str2), paramAccount.getLocale());
          paramStringBuffer.append(" and ( b.Amount>=? or b.Amount<=? )");
        }
        catch (Exception localException2) {}
      }
    }
    else if ((str3 != null) && (str3.length() > 0)) {
      try
      {
        Currency localCurrency3 = new Currency(new BigDecimal(str3), paramAccount.getLocale());
        paramStringBuffer.append(" and ( b.Amount<=? and b.Amount>=? )");
      }
      catch (Exception localException3) {}
    }
    String str4 = (String)paramHashMap.get("CustomTransGroupCodesForReport");
    if ((str4 != null) && (str4.length() > 0))
    {
      paramStringBuffer.append(" AND b.TransSubTypeID in ( ");
      paramStringBuffer.append(str4);
      paramStringBuffer.append(")");
    }
    Object localObject = null;
    str1 = paramProperties.getProperty("ZBA Display");
    if (str1 != null) {
      localObject = str1;
    } else {
      localObject = paramAccount.getZBAFlag();
    }
    if (localObject != null) {
      if (((String)localObject).equalsIgnoreCase("N")) {
        paramStringBuffer.append(" and b.TransSubTypeID not in (275, 575)");
      } else if (((String)localObject).equalsIgnoreCase("C")) {
        paramStringBuffer.append(" and b.TransSubTypeID <> 575");
      } else if (((String)localObject).equalsIgnoreCase("D")) {
        paramStringBuffer.append(" and b.TransSubTypeID <> 275");
      }
    }
    str1 = paramProperties.getProperty("Description");
    if (str1 != null) {
      paramStringBuffer.append(" and b.Description like '%" + str1 + "%'");
    }
    str1 = paramProperties.getProperty("TransactionReferenceNumber");
    if (str1 != null) {
      paramStringBuffer.append(" and b.ReferenceNumber=?");
    }
    str1 = paramProperties.getProperty("TransactionType");
    if ((str1 != null) && (!str1.equals("AllTransactionTypes"))) {
      paramStringBuffer.append(" and b.TransTypeID=?");
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
    return a(paramPreparedStatement, paramInt, localProperties, paramHashMap);
  }
  
  private static int a(PreparedStatement paramPreparedStatement, int paramInt, Properties paramProperties, HashMap paramHashMap)
    throws Exception
  {
    if (paramProperties == null) {
      return paramInt;
    }
    String str1 = paramProperties.getProperty("Account");
    if ((str1 != null) && (str1.length() != 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("RoutingNumber");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("BankId");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("InstrumentBankName");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("InstrumentNumber");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("DataClassification");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equals("A"))) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("StartDate");
    if ((str1 != null) && (str1.length() > 0)) {
      DCUtil.fillTimestampColumn(paramPreparedStatement, paramInt++, str1, "MM/dd/yyyy HH:mm:ss");
    }
    str1 = paramProperties.getProperty("EndDate");
    if ((str1 != null) && (str1.length() > 0)) {
      DCUtil.fillTimestampColumn(paramPreparedStatement, paramInt++, str1, "MM/dd/yyyy HH:mm:ss");
    }
    str1 = paramProperties.getProperty("Customer Reference Number (Min)");
    if ((str1 != null) && (str1.length() > 0))
    {
      str1 = DCAdapter.padRefNum(str1, DCAdapter.getCustomerReferenceNumberType());
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("Customer Reference Number (Max)");
    if ((str1 != null) && (str1.length() > 0))
    {
      str1 = DCAdapter.padRefNum(str1, DCAdapter.getCustomerReferenceNumberType());
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("CustomerReferenceNumber");
    if (str1 != null)
    {
      str1 = DCAdapter.padRefNum(str1, DCAdapter.getCustomerReferenceNumberType());
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("BankReferenceNumber");
    if (str1 != null)
    {
      str1 = DCAdapter.padRefNum(str1, DCAdapter.getBankReferenceNumberType());
      paramPreparedStatement.setString(paramInt++, str1);
    }
    String str2 = paramProperties.getProperty("MinimumAmount");
    String str3 = paramProperties.getProperty("MaximumAmount");
    if ((str2 != null) && (str2.length() > 0))
    {
      if ((str3 != null) && (str3.length() > 0)) {
        try
        {
          Currency localCurrency1 = new Currency(new BigDecimal(str2), null);
          Currency localCurrency4 = new Currency(new BigDecimal(str3), null);
          DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency1);
          DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency4);
          str2 = '-' + str2;
          str3 = '-' + str3;
          localCurrency1 = new Currency(new BigDecimal(str3), null);
          localCurrency4 = new Currency(new BigDecimal(str2), null);
          DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency1);
          DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency4);
        }
        catch (Exception localException1) {}
      } else {
        try
        {
          Currency localCurrency2 = new Currency(new BigDecimal(str2), null);
          DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency2);
          str2 = '-' + str2;
          localCurrency2 = new Currency(new BigDecimal(str2), null);
          DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency2);
        }
        catch (Exception localException2) {}
      }
    }
    else if ((str3 != null) && (str3.length() > 0)) {
      try
      {
        Currency localCurrency3 = new Currency(new BigDecimal(str3), null);
        DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency3);
        str3 = '-' + str3;
        localCurrency3 = new Currency(new BigDecimal(str3), null);
        DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency3);
      }
      catch (Exception localException3) {}
    }
    str1 = paramProperties.getProperty("TransactionReferenceNumber");
    if (str1 != null) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("TransactionType");
    if ((str1 != null) && (!str1.equals("AllTransactionTypes"))) {
      paramPreparedStatement.setInt(paramInt++, Integer.parseInt(str1));
    }
    return paramInt;
  }
  
  private static int a(PreparedStatement paramPreparedStatement, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws Exception
  {
    for (int i1 = 0; i1 < paramArrayList.size(); i1++) {
      DCUtil.fillValue(paramPreparedStatement, paramInt++, paramArrayList.get(i1));
    }
    return paramInt;
  }
  
  private static ArrayList a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    ArrayList localArrayList1 = new ArrayList();
    int i1 = MapUtil.getIntValue(paramPagingContext.getMap(), "CurrentPage", -1);
    if (i1 == 1) {
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
    for (int i2 = 0; i2 < localReportSortCriteria.size(); i2++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i2);
      int i3 = (DCAdapter.CONNECTIONTYPE != null) && (DCAdapter.CONNECTIONTYPE.indexOf("ASE") != -1) ? 1 : 0;
      int i4 = ((i3 != 0) && (localReportSortCriterion.getAsc())) || ((i3 == 0) && (!localReportSortCriterion.getAsc())) ? 1 : 0;
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
          if (i4 == 0)
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
          if (i4 == 0)
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
          if (i4 == 0)
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
      for (i2 = 1; i2 < localArrayList2.size(); i2++) {
        paramStringBuffer.append(" OR (").append((StringBuffer)localArrayList2.get(i2)).append(" ) ");
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
    for (int i1 = 0; i1 < localReportSortCriteria.size(); i1++)
    {
      if (i1 != 0) {
        paramStringBuffer.append(", ");
      }
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i1);
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
    if ((paramString.equals("ProcessingDate")) || (paramString.equals("Date"))) {
      return "TransDate";
    }
    if (paramString.equals("Amount")) {
      return "Amount";
    }
    if (paramString.equals("TransactionType")) {
      return "TRANS_TYPE_DESC";
    }
    if (paramString.equals("TransactionReferenceNumber")) {
      return "ReferenceNumber";
    }
    if (paramString.equals("BankReferenceNumber")) {
      return "BankRefNum";
    }
    if (paramString.equals("CustomerReferenceNumber")) {
      return "CustRefNum";
    }
    if (paramString.equals("RunningBalance")) {
      return "RunningBalance";
    }
    if (paramString.equals("Description")) {
      return "Description";
    }
    if (paramString.equals("Memo")) {
      return "Memo";
    }
    if (paramString.equals("DueDate")) {
      return "DueDate";
    }
    throw new DCException(1025, "Invalid paging sort criteria `" + paramString + "'.");
  }
  
  protected static void modifyDataSourceLoadTime(Connection paramConnection, DateTime paramDateTime, ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    HashSet localHashSet = (HashSet)paramHashMap.get("DCACCOUNTIDLIST");
    try
    {
      Object localObject1;
      if ((localHashSet != null) && (localHashSet.size() != 0))
      {
        localObject1 = localHashSet.iterator();
        StringBuffer localStringBuffer1 = new StringBuffer();
        int i2 = 0;
        int i3 = 0;
        while (((Iterator)localObject1).hasNext())
        {
          Integer localInteger = (Integer)((Iterator)localObject1).next();
          if (i2 != 0) {
            localStringBuffer1.append(",");
          }
          localStringBuffer1.append(localInteger.toString());
          i2++;
          i3++;
          if ((i3 >= localHashSet.size()) || (i2 >= 1000) || (localStringBuffer1.length() >= 50000))
          {
            StringBuffer localStringBuffer2 = new StringBuffer();
            localStringBuffer2.append("UPDATE DC_Transactions SET DataSourceLoadTime=? WHERE DataSource=?");
            localStringBuffer2.append(" and DCAccountID in( ").append(localStringBuffer1).append(")");
            DCUtil.appendNullWhereClause(localStringBuffer2, "DataSourceFileDate", paramImportedFile.getImportTimeValue());
            DCUtil.appendNullWhereClause(localStringBuffer2, "DataSourceFileName", paramImportedFile.getFileName());
            DCUtil.appendNullWhereClause(localStringBuffer2, "BAIFileIdentifier", paramImportedFile.getFileID());
            localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer2.toString());
            int i4 = 1;
            DCUtil.fillTimestampColumn(localPreparedStatement, i4++, paramDateTime);
            localPreparedStatement.setInt(i4++, paramImportedFile.getDataSource());
            if (paramImportedFile.getImportTimeValue() != null) {
              DCUtil.fillTimestampColumn(localPreparedStatement, i4++, paramImportedFile.getImportTimeValue());
            }
            if (paramImportedFile.getFileName() != null) {
              localPreparedStatement.setString(i4++, paramImportedFile.getFileName());
            }
            if (paramImportedFile.getFileID() != null) {
              localPreparedStatement.setString(i4++, paramImportedFile.getFileID());
            }
            DCUtil.executeUpdate(localPreparedStatement, localStringBuffer2.toString());
            localPreparedStatement = null;
            localStringBuffer1 = new StringBuffer();
            i2 = 0;
          }
        }
      }
      else
      {
        localObject1 = new StringBuffer("UPDATE DC_Transactions SET DataSourceLoadTime=? WHERE DataSource=?");
        DCUtil.appendNullWhereClause((StringBuffer)localObject1, "DataSourceFileDate", paramImportedFile.getImportTimeValue());
        DCUtil.appendNullWhereClause((StringBuffer)localObject1, "DataSourceFileName", paramImportedFile.getFileName());
        DCUtil.appendNullWhereClause((StringBuffer)localObject1, "BAIFileIdentifier", paramImportedFile.getFileID());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, ((StringBuffer)localObject1).toString());
        i1 = 1;
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramDateTime);
        localPreparedStatement.setInt(i1++, paramImportedFile.getDataSource());
        if (paramImportedFile.getImportTimeValue() != null) {
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramImportedFile.getImportTimeValue());
        }
        if (paramImportedFile.getFileName() != null) {
          localPreparedStatement.setString(i1++, paramImportedFile.getFileName());
        }
        if (paramImportedFile.getFileID() != null) {
          localPreparedStatement.setString(i1++, paramImportedFile.getFileID());
        }
        DCUtil.executeUpdate(localPreparedStatement, ((StringBuffer)localObject1).toString());
      }
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 457;
      throw new DCException(i1, "Unable to update transaction's data source load time.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(PagingContext paramPagingContext, Transactions paramTransactions)
  {
    if ((paramTransactions == null) || (paramTransactions.size() == 0)) {
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
    boolean bool = false;
    for (int i1 = 0; i1 < localReportCriteria.getSortCriteria().size(); i1++)
    {
      localObject2 = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(i1);
      Object localObject3 = a((ReportSortCriterion)localObject2, paramTransactions.get(0));
      Object localObject4 = a((ReportSortCriterion)localObject2, paramTransactions.get(paramTransactions.size() - 1));
      if (((ReportSortCriterion)localObject2).getAsc())
      {
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
      }
      else
      {
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
      }
      bool = ((ReportSortCriterion)localObject2).getAsc();
    }
    Object localObject1 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramTransactions.get(0));
    Object localObject2 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramTransactions.get(paramTransactions.size() - 1));
    if (bool)
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
    Transaction localTransaction = (Transaction)paramObject;
    if ((paramReportSortCriterion.getName().equals("ProcessingDate")) || (paramReportSortCriterion.getName().equals("Date"))) {
      return localTransaction.getDateValue();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localTransaction.getAmountValue();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localTransaction.getTransactionIndex());
    }
    if (paramReportSortCriterion.getName().equals("TransactionType")) {
      return localTransaction.getType();
    }
    if (paramReportSortCriterion.getName().equals("TransactionReferenceNumber")) {
      return localTransaction.getReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("BankReferenceNumber")) {
      return localTransaction.getBankReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("CustomerReferenceNumber")) {
      return localTransaction.getCustomerReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("RunningBalance")) {
      return localTransaction.getRunningBalanceValue();
    }
    if (paramReportSortCriterion.getName().equals("Description")) {
      return localTransaction.getDescription();
    }
    if (paramReportSortCriterion.getName().equals("Memo")) {
      return localTransaction.getMemo();
    }
    if (paramReportSortCriterion.getName().equals("DueDate")) {
      return localTransaction.getDueDate();
    }
    return null;
  }
  
  public static ArrayList getLocationIds(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt, HashMap paramHashMap)
    throws DCException
  {
    String str1 = (String)paramHashMap.get("DATA_CLASSIFICATION");
    if ((str1 == null) || (str1.length() <= 0)) {
      str1 = "P";
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      String str2 = null;
      String str3 = null;
      if (paramInt == 1)
      {
        str2 = " a.BankRefNum ";
        str3 = DCAdapter.getBankReferenceNumberType();
      }
      else if (paramInt == 2)
      {
        str2 = " a.CustRefNum ";
        str3 = DCAdapter.getCustomerReferenceNumberType();
      }
      else
      {
        if (paramInt == 0)
        {
          localObject1 = null;
          return localObject1;
        }
        throw new DCException(458, "The location ID placement value provided ( " + paramInt + " ) was " + " not valid.  The valid values for this argument are " + 1 + " for bank reference number and " + 2 + " for customer reference " + " number.");
      }
      Object localObject1 = new StringBuffer("SELECT DISTINCT ");
      ((StringBuffer)localObject1).append(str2);
      ((StringBuffer)localObject1).append(" FROM DC_Transactions a, DC_Account b WHERE b.DCAccountID = a.DCAccountID AND b.BankID=? AND b.AccountID=? AND DataClassification=?");
      if (paramAccount.getRoutingNum() != null) {
        ((StringBuffer)localObject1).append(" AND b.RoutingNumber=? ");
      } else {
        ((StringBuffer)localObject1).append(" AND b.RoutingNumber IS NULL ");
      }
      if (paramCalendar1 != null) {
        ((StringBuffer)localObject1).append(" AND a.DataDate>=? ");
      }
      if (paramCalendar2 != null) {
        ((StringBuffer)localObject1).append(" AND a.DataDate<=? ");
      }
      ((StringBuffer)localObject1).append(" ORDER BY ");
      ((StringBuffer)localObject1).append(str2);
      localPreparedStatement = DCAdapter.getStatement(localConnection, ((StringBuffer)localObject1).toString());
      int i1 = 1;
      localPreparedStatement.setString(i1++, paramAccount.getBankID());
      localPreparedStatement.setString(i1++, paramAccount.getID());
      localPreparedStatement.setString(i1++, str1);
      if (paramAccount.getRoutingNum() != null) {
        localPreparedStatement.setString(i1++, paramAccount.getRoutingNum());
      }
      if (paramCalendar1 != null) {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
      }
      if (paramCalendar2 != null) {
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, ((StringBuffer)localObject1).toString());
      ArrayList localArrayList = new ArrayList();
      while (localResultSet.next())
      {
        localObject2 = localResultSet.getString(1);
        localArrayList.add(DCAdapter.unPadRefNum((String)localObject2, str3));
      }
      Object localObject2 = localArrayList;
      return localObject2;
    }
    catch (Exception localException)
    {
      throw new DCException(302, "Unable to retrieve location ids from the transaction table.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCTransactions
 * JD-Core Version:    0.7.0.1
 */