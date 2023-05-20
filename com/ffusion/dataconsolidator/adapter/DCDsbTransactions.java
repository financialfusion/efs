package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementTransaction;
import com.ffusion.beans.disbursement.DisbursementTransactions;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

class DCDsbTransactions
{
  private static final String jdField_case = "INSERT INTO DC_DsbTransactions( DCAccountID, DCTransactionIndex, DataDate, DataSource, TransID, CheckDate, Payee, Amount, CheckNumber, CheckRefNum, Memo, IssuedBy, ApprovedBy, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, Presentment, BAIFileIdentifier, DataSourceFileName, DataSourceFileDate, DataClassification) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String q = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.DataDate <= ? AND b.DataClassification=? ";
  private static final String p = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate >= ? AND b.DataClassification=? ";
  private static final String n = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate <= ? AND b.DataClassification=? ";
  private static final String l = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static final String jdField_goto = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID ";
  private static final String f = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.DataDate <= ?  AND b.DataClassification=? ";
  private static final String d = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ?  AND b.DataClassification=? ";
  private static final String b = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate <= ?  AND b.DataClassification=? ";
  private static final String jdField_null = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static final String i = " ORDER BY b.DCTransactionIndex ";
  private static final String h = " ORDER BY b.DCTransactionIndex DESC ";
  private static final String a = " a.RoutingNumber ";
  private static final String jdField_new = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.DataDate <= ? AND b.Presentment = ?  AND b.DataClassification=? ";
  private static final String jdField_int = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.Presentment = ?   AND b.DataClassification=? ";
  private static final String jdField_for = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate <= ? AND b.Presentment = ?  AND b.DataClassification=? ";
  private static final String jdField_do = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.Presentment = ?  AND b.DataClassification=? ";
  private static final String e = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.DataDate <= ? AND b.Presentment = ? AND b.DCTransactionIndex>=? AND b.DCTransactionIndex<=?  AND b.DataClassification=? ";
  private static final String c = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.Presentment = ? AND b.DCTransactionIndex>=? AND b.DCTransactionIndex<=?  AND b.DataClassification=? ";
  private static final String jdField_void = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate <= ? AND b.Presentment = ? AND b.DCTransactionIndex>=? AND b.DCTransactionIndex<=?  AND b.DataClassification=? ";
  private static final String jdField_long = "SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.Presentment = ? AND b.DCTransactionIndex>=? AND b.DCTransactionIndex<=?  AND b.DataClassification=? ";
  private static final String o = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.Presentment = ? AND b.DataDate >= ? AND b.DataDate <= ?  AND b.DataClassification=? ";
  private static final String m = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.Presentment = ? AND b.DataDate >= ?  AND b.DataClassification=? ";
  private static final String k = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.Presentment = ? AND b.DataDate <= ?  AND b.DataClassification=? ";
  private static final String j = "SELECT min(b.DCTransactionIndex), max(b.DCTransactionIndex) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.Presentment = ? AND b.DataClassification=? ";
  private static final String jdField_try = "SELECT min( b.DCTransactionIndex ) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex > ? ";
  private static final String jdField_else = "SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex < ? ";
  private static final String g = "SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex >= ? AND b.DCTransactionIndex < ?";
  private static final DecimalFormat jdField_byte = new DecimalFormat("00000000000");
  private static final String jdField_if = "00000000001";
  private static final String jdField_char = "99999999999";
  
  protected static void addTransactions(DisbursementAccount paramDisbursementAccount, DisbursementTransactions paramDisbursementTransactions, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    try
    {
      Account localAccount = DCUtil.getAccount(paramDisbursementAccount);
      if (!DCAccount.accountExists(localAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(localAccount, paramConnection, paramHashMap);
      }
      i1 = DCAdapter.getDCAccountID(paramConnection, paramDisbursementAccount.getAccountID(), paramDisbursementAccount.getBankID(), paramDisbursementAccount.getRoutingNumber());
      if (i1 == -1) {
        throw new DCException(414, "Invalid account.");
      }
      HashMap localHashMap = new HashMap();
      Object localObject2;
      for (int i2 = 0; i2 < paramDisbursementTransactions.size(); i2++)
      {
        localObject1 = (DisbursementTransaction)paramDisbursementTransactions.get(i2);
        localObject2 = ((DisbursementTransaction)localObject1).getProcessingDate();
        String str = DateFormatUtil.getFormatter("yyyyMMdd").format(((DateTime)localObject2).getTime());
        DisbursementTransactions localDisbursementTransactions = (DisbursementTransactions)localHashMap.get(str);
        if (localDisbursementTransactions == null)
        {
          localDisbursementTransactions = new DisbursementTransactions(paramDisbursementTransactions.getLocale());
          localHashMap.put(str, localDisbursementTransactions);
        }
        localDisbursementTransactions.add(localObject1);
      }
      ArrayList localArrayList = new ArrayList(localHashMap.keySet());
      Qsort.sortStrings(localArrayList, 1);
      Object localObject1 = localArrayList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (DisbursementTransactions)localHashMap.get(((Iterator)localObject1).next());
        a(paramConnection, i1, paramDisbursementAccount, paramInt, (DisbursementTransactions)localObject2, paramHashMap);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add disbursement transactions.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 606;
      throw new DCException(i1, "Failed to add disbursement transaction records.", localException);
    }
  }
  
  private static void a(Connection paramConnection, int paramInt1, DisbursementAccount paramDisbursementAccount, int paramInt2, DisbursementTransactions paramDisbursementTransactions, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    DisbursementTransaction localDisbursementTransaction = null;
    long l1 = 0L;
    int i1 = DCAdapter.getBatchSize(paramHashMap);
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
      l2 = a(paramConnection, paramDisbursementAccount, paramDisbursementTransactions, paramHashMap);
    }
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_DsbTransactions( DCAccountID, DCTransactionIndex, DataDate, DataSource, TransID, CheckDate, Payee, Amount, CheckNumber, CheckRefNum, Memo, IssuedBy, ApprovedBy, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, Presentment, BAIFileIdentifier, DataSourceFileName, DataSourceFileDate, DataClassification) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      int i2 = 0;
      while (i2 < paramDisbursementTransactions.size())
      {
        int i3 = 0;
        while ((i3 < i1) && (i2 < paramDisbursementTransactions.size()))
        {
          localDisbursementTransaction = (DisbursementTransaction)paramDisbursementTransactions.get(i2);
          if (localDisbursementTransaction.getTransactionIndex() != 0L) {
            throw new DCException(605, "The incoming disbursement record contains invalid item index.");
          }
          if (DCAdapter.isPagingDateBased()) {
            l1 = l2++;
          } else {
            l1 = DCRecordCounter.getNextIndex(paramConnection, 1, paramInt1, "DISBURSEMENT_TRANSACTION_INDEX", paramHashMap);
          }
          long l3 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localDisbursementTransaction.getExtendABeanXML(), paramHashMap);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setLong(2, l1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, localDisbursementTransaction.getProcessingDate());
          localPreparedStatement.setInt(4, paramInt2);
          localPreparedStatement.setInt(5, localDisbursementTransaction.getTransID());
          DCUtil.fillDate(localPreparedStatement, 6, localDisbursementTransaction.getCheckDate());
          localPreparedStatement.setString(7, localDisbursementTransaction.getPayee());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localDisbursementTransaction.getCheckAmount());
          localPreparedStatement.setString(9, localDisbursementTransaction.getCheckNumber());
          localPreparedStatement.setString(10, localDisbursementTransaction.getCheckReferenceNumber());
          localPreparedStatement.setString(11, localDisbursementTransaction.getMemo());
          localPreparedStatement.setString(12, localDisbursementTransaction.getIssuedBy());
          localPreparedStatement.setString(13, localDisbursementTransaction.getApprovedBy());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localDisbursementTransaction.getImmediateFundsNeeded());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 15, localDisbursementTransaction.getOneDayFundsNeeded());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 16, localDisbursementTransaction.getTwoDayFundsNeeded());
          DCUtil.fillTimestampColumn(localPreparedStatement, 17, localDisbursementTransaction.getValueDateTime());
          localPreparedStatement.setString(18, DCAdapter.padRefNum(localDisbursementTransaction.getBankReferenceNumber(), DCAdapter.getBankReferenceNumberType()));
          localPreparedStatement.setString(19, DCAdapter.padRefNum(localDisbursementTransaction.getCustomerReferenceNumber(), DCAdapter.getCustomerReferenceNumberType()));
          localPreparedStatement.setLong(20, l3);
          localPreparedStatement.setString(21, null);
          localPreparedStatement.setString(22, localDisbursementTransaction.getPresentment());
          localPreparedStatement.setString(23, str1);
          localPreparedStatement.setString(24, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 25, localCalendar);
          localPreparedStatement.setString(26, str3);
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
  
  private static void a(DisbursementAccount paramDisbursementAccount, DisbursementTransaction paramDisbursementTransaction, ResultSet paramResultSet)
    throws DCException
  {
    try
    {
      paramDisbursementTransaction.setAccountID(paramDisbursementAccount.getAccountID());
      paramDisbursementTransaction.setAccountNumber(paramDisbursementAccount.getAccountNumber());
      paramDisbursementTransaction.setBankID(paramDisbursementAccount.getBankID());
      paramDisbursementTransaction.setRoutingNumber(paramDisbursementAccount.getRoutingNumber());
      paramDisbursementTransaction.setTransactionIndex(paramResultSet.getLong(1));
      paramDisbursementTransaction.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(2), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setTransID(paramResultSet.getInt(3));
      paramDisbursementTransaction.setCheckDate(new DateTime(paramResultSet.getDate(4), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setPayee(paramResultSet.getString(5));
      paramDisbursementTransaction.setCheckAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramDisbursementAccount.getCurrencyType(), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setCheckNumber(paramResultSet.getString(7));
      paramDisbursementTransaction.setCheckReferenceNumber(paramResultSet.getString(8));
      paramDisbursementTransaction.setMemo(paramResultSet.getString(9));
      paramDisbursementTransaction.setIssuedBy(paramResultSet.getString(10));
      paramDisbursementTransaction.setApprovedBy(paramResultSet.getString(11));
      paramDisbursementTransaction.setImmediateFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramDisbursementAccount.getCurrencyType(), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setOneDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramDisbursementAccount.getCurrencyType(), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setTwoDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramDisbursementAccount.getCurrencyType(), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(15), paramDisbursementAccount.getLocale()));
      paramDisbursementTransaction.setBankReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(16), DCAdapter.getBankReferenceNumberType()));
      paramDisbursementTransaction.setCustomerReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(17), DCAdapter.getCustomerReferenceNumberType()));
      DCUtil.fillExtendABean(paramDisbursementTransaction, paramResultSet, 18);
      paramDisbursementTransaction.setPresentment(paramResultSet.getString(19));
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to populate disbursement transaction record with database record.", localSQLException);
    }
  }
  
  protected static DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  protected static DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    DisbursementTransactions localDisbursementTransactions1 = null;
    Connection localConnection = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      if (paramString == null)
      {
        if (paramCalendar1 != null)
        {
          if (paramCalendar2 != null)
          {
            localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.DataDate <= ? AND b.DataClassification=? ");
            DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
            localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
            localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
            localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
            localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str);
            if (paramDisbursementAccount.getRoutingNumber() != null) {
              localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
            }
            localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
          }
          else
          {
            localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate >= ? AND b.DataClassification=? ");
            DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
            localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
            localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
            localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
            localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            localPreparedStatement.setString(4, str);
            if (paramDisbursementAccount.getRoutingNumber() != null) {
              localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
            }
            localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
          }
        }
        else if (paramCalendar2 != null)
        {
          localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate <= ? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(3, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.DataDate <= ? AND b.Presentment = ?  AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, paramString);
          localPreparedStatement.setString(6, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(7, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate >= ? AND b.Presentment = ?   AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, paramString);
          localPreparedStatement.setString(5, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate <= ? AND b.Presentment = ?  AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, paramString);
        localPreparedStatement.setString(5, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.Presentment = ?  AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(" ORDER BY b.DCTransactionIndex ");
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(3, paramString);
        localPreparedStatement.setString(4, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      localDisbursementTransactions1 = new DisbursementTransactions();
      DisbursementTransaction localDisbursementTransaction = null;
      while (localResultSet.next())
      {
        localDisbursementTransaction = localDisbursementTransactions1.create();
        a(paramDisbursementAccount, localDisbursementTransaction, localResultSet);
      }
      DisbursementTransactions localDisbursementTransactions2 = localDisbursementTransactions1;
      return localDisbursementTransactions2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbusement transactions from database: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 607;
      throw new DCException(i1, "Failed to get disbursement transactions from database.", localException);
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
  
  private static ResultSet a(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap, Connection paramConnection)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().put("Account", paramDisbursementAccount.getAccountID());
    if (paramDisbursementAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramDisbursementAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramDisbursementAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", str);
    }
    int i1 = MapUtil.getIntValue(paramHashMap, "PAGESIZE", DCAdapter.PAGESIZE);
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT b.DCTransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID ");
      a(localStringBuffer, paramDisbursementAccount, paramPagingContext, paramHashMap);
      ArrayList localArrayList = a(localStringBuffer, paramPagingContext, paramBoolean, paramHashMap);
      a(localStringBuffer, paramPagingContext, paramBoolean, null, paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i2 = 1;
      i2 = a(localPreparedStatement, i2, paramPagingContext, paramHashMap);
      a(localPreparedStatement, i2, localArrayList, paramHashMap);
      localPreparedStatement.setMaxRows(i1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      localPreparedStatement = null;
    }
    return localResultSet;
  }
  
  public static DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    DisbursementTransactions localDisbursementTransactions1 = new DisbursementTransactions();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      jdField_if(localConnection, paramDisbursementAccount, paramPagingContext, paramHashMap);
      a(localConnection, paramDisbursementAccount, paramPagingContext, paramHashMap);
      if (!paramPagingContext.getMap().containsKey("UPPER_BOUND_TransactionIndex"))
      {
        DisbursementTransactions localDisbursementTransactions2 = localDisbursementTransactions1;
        return localDisbursementTransactions2;
      }
      paramPagingContext.getMap().remove("CurrentPage");
      paramPagingContext.getMap().remove("PageSettings");
      int i1 = 1;
      paramPagingContext.getMap().put("CurrentPage", new Integer(i1));
      a(paramPagingContext.getMap());
      localResultSet = a(paramDisbursementAccount, paramPagingContext, true, paramHashMap, localConnection);
      while (localResultSet.next())
      {
        localObject1 = localDisbursementTransactions1.create();
        a(paramDisbursementAccount, (DisbursementTransaction)localObject1, localResultSet);
      }
      a(paramPagingContext, localDisbursementTransactions1);
      Object localObject1 = localDisbursementTransactions1;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbursement transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(607, "Failed to get disbursement transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static DisbursementTransactions getRecentTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      jdField_if(localConnection, paramDisbursementAccount, paramPagingContext, paramHashMap);
      a(localConnection, paramDisbursementAccount, paramPagingContext, paramHashMap);
      localResultSet = a(paramDisbursementAccount, paramPagingContext, false, paramHashMap, localConnection);
      DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions();
      while (localResultSet.next())
      {
        localObject1 = localDisbursementTransactions.create();
        a(paramDisbursementAccount, (DisbursementTransaction)localObject1, localResultSet);
      }
      a(paramPagingContext, localDisbursementTransactions);
      Object localObject1 = localDisbursementTransactions;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbursement transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(607, "Failed to get disbursement transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i1 = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i1 != -1)
      {
        i1++;
        localHashMap.put("CurrentPage", new Integer(i1));
        a(localHashMap);
      }
      localResultSet = a(paramDisbursementAccount, paramPagingContext, true, paramHashMap, localConnection);
      DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions();
      while (localResultSet.next())
      {
        localObject1 = localDisbursementTransactions.create();
        a(paramDisbursementAccount, (DisbursementTransaction)localObject1, localResultSet);
      }
      a(paramPagingContext, localDisbursementTransactions);
      Object localObject1 = localDisbursementTransactions;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbursement transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(607, "Failed to get disbursement transactions from database.", localException);
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
  
  public static DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i1 = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i1 != -1)
      {
        i1--;
        localHashMap.put("CurrentPage", new Integer(i1));
        localObject1 = (IntMap)localHashMap.get("PageSettings");
        localObject2 = (HashMap)((IntMap)localObject1).get(i1);
        if (localReportCriteria != null) {
          for (int i2 = 0; i2 < localReportCriteria.getSortCriteria().size(); i2++)
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(i2);
            localHashMap.put("SORT_VALUE_MIN_" + localReportSortCriterion.getName(), ((HashMap)localObject2).get("SORT_VALUE_MIN_" + localReportSortCriterion.getName()));
            localHashMap.put("SORT_VALUE_MAX_" + localReportSortCriterion.getName(), ((HashMap)localObject2).get("SORT_VALUE_MAX_" + localReportSortCriterion.getName()));
          }
        }
        localHashMap.put("SORT_VALUE_MIN_TransactionIndex", ((HashMap)localObject2).get("SORT_VALUE_MIN_TransactionIndex"));
        localHashMap.put("SORT_VALUE_MAX_TransactionIndex", ((HashMap)localObject2).get("SORT_VALUE_MAX_TransactionIndex"));
      }
      if (i1 == -1) {
        localResultSet = a(paramDisbursementAccount, paramPagingContext, false, paramHashMap, localConnection);
      } else {
        localResultSet = a(paramDisbursementAccount, paramPagingContext, true, paramHashMap, localConnection);
      }
      Object localObject1 = new DisbursementTransactions();
      while (localResultSet.next())
      {
        localObject2 = ((DisbursementTransactions)localObject1).createNoAdd();
        a(paramDisbursementAccount, (DisbursementTransaction)localObject2, localResultSet);
        if (i1 == -1) {
          ((DisbursementTransactions)localObject1).add(0, localObject2);
        } else {
          ((DisbursementTransactions)localObject1).add(localObject2);
        }
      }
      a(paramPagingContext, (DisbursementTransactions)localObject1);
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbursement transactions: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(607, "Failed to get disbursement transactions from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static long a(Connection paramConnection, DisbursementAccount paramDisbursementAccount, DisbursementTransactions paramDisbursementTransactions, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)paramDisbursementTransactions.get(0);
    DateTime localDateTime = localDisbursementTransaction.getProcessingDate();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str2 = localDateFormat.format(localDateTime.getTime());
    StringBuffer localStringBuffer1 = new StringBuffer(str2);
    localStringBuffer1.append("00000000001");
    StringBuffer localStringBuffer2 = new StringBuffer(str2);
    localStringBuffer2.append("99999999999");
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer3 = new StringBuffer();
    localStringBuffer3.append("SELECT max( b.DCTransactionIndex ) FROM DC_Account a, DC_DsbTransactions b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? AND b.DCTransactionIndex >= ? AND b.DCTransactionIndex < ?");
    DCUtil.appendNullWhereClause(localStringBuffer3, " a.RoutingNumber ", paramDisbursementAccount.getRoutingNumber());
    PreparedStatement localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer3.toString());
    long l1 = -1L;
    try
    {
      localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
      localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
      localPreparedStatement.setString(3, str1);
      localPreparedStatement.setLong(4, Long.parseLong(localStringBuffer1.toString()));
      localPreparedStatement.setLong(5, Long.parseLong(localStringBuffer2.toString()));
      if (paramDisbursementAccount.getRoutingNumber() != null) {
        localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
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
          String str6 = jdField_byte.format(l3);
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
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to generate disbursement transaction ID.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 608;
      throw new DCException(i1, "Failed to generate disbursement transaction ID.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
    return l1;
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
  
  private static void jdField_if(Connection paramConnection, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramDisbursementAccount.getAccountID());
    if (paramDisbursementAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramDisbursementAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramDisbursementAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
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
      localStringBuffer.append(" FROM DC_Account a, DC_DsbTransactions b ");
      localStringBuffer.append(" WHERE a.DCAccountID = b.DCAccountID ");
      a(localStringBuffer, paramDisbursementAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(false), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i1 = 1;
      i1 = a(localPreparedStatement, i1, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int i2 = 0; i2 < localReportSortCriteria.size(); i2++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i2);
          paramPagingContext.getMap().put("UPPER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, i2 + 1, paramDisbursementAccount.getCurrencyType(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("UPPER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramDisbursementAccount.getCurrencyType(), localReportCriteria.getLocale()));
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
  
  private static void a(Connection paramConnection, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramDisbursementAccount.getAccountID());
    if (paramDisbursementAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramDisbursementAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramDisbursementAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
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
      localStringBuffer.append(" FROM DC_Account a, DC_DsbTransactions b ");
      localStringBuffer.append(" WHERE a.DCAccountID = b.DCAccountID ");
      a(localStringBuffer, paramDisbursementAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(true), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i1 = 1;
      i1 = a(localPreparedStatement, i1, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int i2 = 0; i2 < localReportSortCriteria.size(); i2++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i2);
          paramPagingContext.getMap().put("LOWER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, i2 + 1, paramDisbursementAccount.getCurrencyType(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("LOWER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramDisbursementAccount.getCurrencyType(), localReportCriteria.getLocale()));
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
  
  private static void a(StringBuffer paramStringBuffer, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
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
    String str = localProperties.getProperty("Account");
    if ((str != null) && (str.length() != 0)) {
      paramStringBuffer.append(" and a.AccountID = ? ");
    }
    str = localProperties.getProperty("RoutingNumber");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and a.RoutingNumber = ? ");
    } else if (str == null) {
      paramStringBuffer.append(" and a.RoutingNumber is NULL ");
    }
    str = localProperties.getProperty("BankId");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and a.BankID = ? ");
    }
    str = localProperties.getProperty("Presentment");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.Presentment = ? ");
    }
    str = localProperties.getProperty("DataClassification");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.DataClassification = ? ");
    }
    str = localProperties.getProperty("StartDate");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.DataDate >= ? ");
    }
    str = localProperties.getProperty("EndDate");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.DataDate <= ? ");
    }
    str = localProperties.getProperty("Customer Reference Number (Min)");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.CustRefNum>=?");
    }
    str = localProperties.getProperty("Customer Reference Number (Max)");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and b.CustRefNum<=?");
    }
    str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency1 = new Currency(new BigDecimal(str), paramDisbursementAccount.getLocale());
        paramStringBuffer.append(" and b.Amount>=?");
      }
      catch (Exception localException1) {}
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency2 = new Currency(new BigDecimal(str), paramDisbursementAccount.getLocale());
        paramStringBuffer.append(" and b.Amount<=?");
      }
      catch (Exception localException2) {}
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
    String str = localProperties.getProperty("Account");
    if ((str != null) && (str.length() != 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("RoutingNumber");
    if ((str != null) && (str.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("BankId");
    if ((str != null) && (str.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("Presentment");
    if ((str != null) && (str.length() != 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("DataClassification");
    if ((str != null) && (str.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("StartDate");
    if ((str != null) && (str.length() > 0)) {
      DCUtil.fillTimestampColumn(paramPreparedStatement, paramInt++, str, "MM/dd/yyyy HH:mm:ss");
    }
    str = localProperties.getProperty("EndDate");
    if ((str != null) && (str.length() > 0)) {
      DCUtil.fillTimestampColumn(paramPreparedStatement, paramInt++, str, "MM/dd/yyyy HH:mm:ss");
    }
    str = localProperties.getProperty("Customer Reference Number (Min)");
    if ((str != null) && (str.length() > 0))
    {
      str = DCAdapter.padRefNum(str, DCAdapter.getCustomerReferenceNumberType());
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("Customer Reference Number (Max)");
    if ((str != null) && (str.length() > 0))
    {
      str = DCAdapter.padRefNum(str, DCAdapter.getCustomerReferenceNumberType());
      paramPreparedStatement.setString(paramInt++, str);
    }
    str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency1 = new Currency(new BigDecimal(str), null);
        DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency1);
      }
      catch (Exception localException1) {}
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency2 = new Currency(new BigDecimal(str), null);
        DCUtil.fillCurrencyColumn(paramPreparedStatement, paramInt++, localCurrency2);
      }
      catch (Exception localException2) {}
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
    if (paramString.equals("CheckDate")) {
      return "CheckDate";
    }
    if (paramString.equals("CheckAmount")) {
      return "Amount";
    }
    if (paramString.equals("CheckNumber")) {
      return "CheckNumber";
    }
    if (paramString.equals("CheckReferenceNumber")) {
      return "CheckRefNuM";
    }
    if (paramString.equals("BankReferenceNumber")) {
      return "BankRefNum";
    }
    if (paramString.equals("CustomerReferenceNumber")) {
      return "CustRefNum";
    }
    if (paramString.equals("Memo")) {
      return "Memo";
    }
    if (paramString.equals("Payee")) {
      return "Payee";
    }
    throw new DCException(1025, "Invalid paging sort criteria `" + paramString + "'.");
  }
  
  private static void a(PagingContext paramPagingContext, DisbursementTransactions paramDisbursementTransactions)
  {
    if ((paramDisbursementTransactions == null) || (paramDisbursementTransactions.size() == 0)) {
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
    int i1 = 0;
    for (int i2 = 0; i2 < localReportCriteria.getSortCriteria().size(); i2++)
    {
      localObject2 = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(i2);
      Object localObject3 = a((ReportSortCriterion)localObject2, paramDisbursementTransactions.get(0));
      Object localObject4 = a((ReportSortCriterion)localObject2, paramDisbursementTransactions.get(paramDisbursementTransactions.size() - 1));
      if (((ReportSortCriterion)localObject2).getAsc())
      {
        i1 = 1;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
      }
      else
      {
        i1 = 0;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
      }
    }
    Object localObject1 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramDisbursementTransactions.get(0));
    Object localObject2 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramDisbursementTransactions.get(paramDisbursementTransactions.size() - 1));
    if (i1 != 0)
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
    DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("CheckDate")) {
      return localDisbursementTransaction.getCheckDate();
    }
    if (paramReportSortCriterion.getName().equals("CheckAmount")) {
      return localDisbursementTransaction.getCheckAmount();
    }
    if (paramReportSortCriterion.getName().equals("CheckNumber")) {
      return localDisbursementTransaction.getCheckNumber();
    }
    if (paramReportSortCriterion.getName().equals("CheckReferenceNumber")) {
      return localDisbursementTransaction.getCheckReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localDisbursementTransaction.getTransactionIndex());
    }
    if (paramReportSortCriterion.getName().equals("BankReferenceNumber")) {
      return localDisbursementTransaction.getBankReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("CustomerReferenceNumber")) {
      return localDisbursementTransaction.getCustomerReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("Memo")) {
      return localDisbursementTransaction.getMemo();
    }
    if (paramReportSortCriterion.getName().equals("Payee")) {
      return localDisbursementTransaction.getPayee();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCDsbTransactions
 * JD-Core Version:    0.7.0.1
 */