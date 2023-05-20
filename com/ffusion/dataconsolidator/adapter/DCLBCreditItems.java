package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
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

class DCLBCreditItems
{
  private static final String d = "INSERT INTO DC_LBCreditItems( DCLockboxID, DCCreditItemIndex, DataDate, DataSource, ItemID, DocumentType, Payor, Amount, CheckNumber, CheckDate, CouponAccountNum, CouponAmount1, CouponAmount2, CouponDate1, CouponDate2, CouponRefNum, CheckRoutingNum, CheckAccountNum, LockboxWorkType, LockboxBatchNum, LockboxSeqNum, Memo, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataSourceFileName, DataSourceFileDate, DataClassification ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
  private static final String jdField_int = "SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String jdField_for = "SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String jdField_do = "SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String a = "SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String e = "SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID ";
  private static final String jdField_case = "INSERT INTO DC_Lockbox( DCLockboxID, DCAccountID, LockboxNumber, Extra) VALUES(?, ?, ?, ?)";
  private static final String f = " ORDER BY b.DCCreditItemIndex ";
  private static final String b = " ORDER BY b.DCCreditItemIndex DESC ";
  private static final String jdField_if = " a.RoutingNumber ";
  private static final String jdField_void = "SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String jdField_null = "SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String jdField_long = "SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String jdField_goto = "SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND c.LockboxNumber = ? AND b.DataClassification=? ";
  private static final String jdField_try = "SELECT min( b.DCCreditItemIndex ) FROM DC_Account a, DC_Lockbox l, DC_LBCreditItems b WHERE a.DCAccountID=l.DCAccountID AND a.AccountID=? AND a.BankID=? AND l.LockboxNumber=? AND l.DCLockboxID = b.DCLockboxID AND b.DataClassification=? AND b.DCCreditItemIndex > ? ";
  private static final String jdField_else = "SELECT max( b.DCCreditItemIndex ) FROM DC_Account a, DC_Lockbox l, DC_LBCreditItems b WHERE a.DCAccountID=l.DCAccountID AND a.AccountID=? AND a.BankID=? AND l.LockboxNumber=? AND l.DCLockboxID = b.DCLockboxID AND b.DataClassification=? AND b.DCCreditItemIndex < ? ";
  private static final String c = "SELECT max( b.DCCreditItemIndex ) FROM DC_Account a, DC_Lockbox l, DC_LBCreditItems b WHERE a.DCAccountID=l.DCAccountID AND a.AccountID=? AND a.BankID=? AND l.LockboxNumber=? AND l.DCLockboxID = b.DCLockboxID AND b.DataClassification=? AND b.DCCreditItemIndex >= ? AND b.DCCreditItemIndex < ?";
  private static final DecimalFormat jdField_byte = new DecimalFormat("00000000000");
  private static final String jdField_new = "00000000001";
  private static final String jdField_char = "99999999999";
  
  protected static void addItems(LockboxAccount paramLockboxAccount, LockboxCreditItems paramLockboxCreditItems, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DCUtil.isAccountInfoSufficient(paramLockboxAccount);
    try
    {
      Account localAccount = DCUtil.getAccount(paramLockboxAccount);
      if (!DCAccount.accountExists(localAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(localAccount, paramConnection, paramHashMap);
      }
      i = DCAdapter.getDCAccountID(paramConnection, paramLockboxAccount.getAccountID(), paramLockboxAccount.getBankID(), paramLockboxAccount.getRoutingNumber());
      if (i == -1) {
        throw new DCException(414, "Account not found.");
      }
      HashMap localHashMap = new HashMap();
      Object localObject2;
      for (int j = 0; j < paramLockboxCreditItems.size(); j++)
      {
        localObject1 = (LockboxCreditItem)paramLockboxCreditItems.get(j);
        localObject2 = ((LockboxCreditItem)localObject1).getProcessingDate();
        String str = DateFormatUtil.getFormatter("yyyyMMdd").format(((DateTime)localObject2).getTime());
        LockboxCreditItems localLockboxCreditItems = (LockboxCreditItems)localHashMap.get(str);
        if (localLockboxCreditItems == null)
        {
          localLockboxCreditItems = new LockboxCreditItems(paramLockboxCreditItems.getLocale());
          localHashMap.put(str, localLockboxCreditItems);
        }
        localLockboxCreditItems.add(localObject1);
      }
      ArrayList localArrayList = new ArrayList(localHashMap.keySet());
      Qsort.sortStrings(localArrayList, 1);
      Object localObject1 = localArrayList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (LockboxCreditItems)localHashMap.get(((Iterator)localObject1).next());
        a(paramConnection, i, paramLockboxAccount, paramInt, (LockboxCreditItems)localObject2, paramHashMap);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add lockbox credit item records: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39516;
      throw new DCException(i, "Failed to add lockbox credit item records.", localException);
    }
  }
  
  private static void a(Connection paramConnection, int paramInt1, LockboxAccount paramLockboxAccount, int paramInt2, LockboxCreditItems paramLockboxCreditItems, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    LockboxCreditItem localLockboxCreditItem = null;
    long l1 = 0L;
    int i = DCAdapter.getBatchSize(paramHashMap);
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
    HashMap localHashMap = null;
    if (DCAdapter.isPagingDateBased()) {
      localHashMap = new HashMap();
    }
    try
    {
      localPreparedStatement1 = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_LBCreditItems( DCLockboxID, DCCreditItemIndex, DataDate, DataSource, ItemID, DocumentType, Payor, Amount, CheckNumber, CheckDate, CouponAccountNum, CouponAmount1, CouponAmount2, CouponDate1, CouponDate2, CouponRefNum, CheckRoutingNum, CheckAccountNum, LockboxWorkType, LockboxBatchNum, LockboxSeqNum, Memo, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataSourceFileName, DataSourceFileDate, DataClassification ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
      localPreparedStatement2 = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_Lockbox( DCLockboxID, DCAccountID, LockboxNumber, Extra) VALUES(?, ?, ?, ?)");
      int j = 0;
      while (j < paramLockboxCreditItems.size())
      {
        int k = 0;
        while ((k < i) && (j < paramLockboxCreditItems.size()))
        {
          localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(j);
          int m = DCAdapter.getDCLockboxID(paramConnection, paramInt1, localLockboxCreditItem.getLockboxNumber());
          if (m == -1)
          {
            m = DBUtil.getNextId(paramConnection, DCAdapter.CONNECTIONTYPE, "DCLockboxID");
            localPreparedStatement2.setInt(1, m);
            localPreparedStatement2.setInt(2, paramInt1);
            localPreparedStatement2.setString(3, localLockboxCreditItem.getLockboxNumber());
            localPreparedStatement2.setString(4, null);
            DBUtil.executeUpdate(localPreparedStatement2, "INSERT INTO DC_Lockbox( DCLockboxID, DCAccountID, LockboxNumber, Extra) VALUES(?, ?, ?, ?)");
            if (!DCAdapter.isPagingDateBased())
            {
              String str4 = (String)paramHashMap.get("DATA_CLASSIFICATION");
              paramHashMap.put("DATA_CLASSIFICATION", "P");
              DCRecordCounter.addNewCounter(paramConnection, 2, m, "LOCKBOX_CREDITITEM_INDEX", paramHashMap);
              paramHashMap.put("DATA_CLASSIFICATION", "I");
              DCRecordCounter.addNewCounter(paramConnection, 2, m, "LOCKBOX_CREDITITEM_INDEX", paramHashMap);
              paramHashMap.put("DATA_CLASSIFICATION", str4);
            }
          }
          if (localLockboxCreditItem.getItemIndex() != 0L) {
            throw new DCException(39517, "Credit Item contains invalid item index.");
          }
          if (DCAdapter.isPagingDateBased()) {
            l1 = a(paramConnection, paramLockboxAccount, localLockboxCreditItem, localHashMap, paramHashMap);
          } else {
            l1 = DCRecordCounter.getNextIndex(paramConnection, 2, m, "LOCKBOX_CREDITITEM_INDEX", paramHashMap);
          }
          long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localLockboxCreditItem.getExtendABeanXML(), paramHashMap);
          localPreparedStatement1.setInt(1, m);
          localPreparedStatement1.setLong(2, l1);
          DCUtil.fillTimestampColumn(localPreparedStatement1, 3, localLockboxCreditItem.getProcessingDate());
          localPreparedStatement1.setInt(4, paramInt2);
          localPreparedStatement1.setInt(5, localLockboxCreditItem.getItemID());
          localPreparedStatement1.setString(6, localLockboxCreditItem.getDocumentType());
          localPreparedStatement1.setString(7, localLockboxCreditItem.getPayer());
          DCUtil.fillCurrencyColumn(localPreparedStatement1, 8, localLockboxCreditItem.getCheckAmount());
          localPreparedStatement1.setString(9, localLockboxCreditItem.getCheckNumber());
          DCUtil.fillDate(localPreparedStatement1, 10, localLockboxCreditItem.getCheckDate());
          localPreparedStatement1.setString(11, localLockboxCreditItem.getCouponAccountNumber());
          DCUtil.fillCurrencyColumn(localPreparedStatement1, 12, localLockboxCreditItem.getCouponAmount1());
          DCUtil.fillCurrencyColumn(localPreparedStatement1, 13, localLockboxCreditItem.getCouponAmount2());
          DCUtil.fillDate(localPreparedStatement1, 14, localLockboxCreditItem.getCouponDate1());
          DCUtil.fillDate(localPreparedStatement1, 15, localLockboxCreditItem.getCouponDate2());
          localPreparedStatement1.setString(16, localLockboxCreditItem.getCouponReferenceNumber());
          localPreparedStatement1.setString(17, localLockboxCreditItem.getCheckRoutingNumber());
          localPreparedStatement1.setString(18, localLockboxCreditItem.getCheckAccountNumber());
          localPreparedStatement1.setString(19, localLockboxCreditItem.getLockboxWorkType());
          localPreparedStatement1.setString(20, localLockboxCreditItem.getLockboxBatchNumber());
          localPreparedStatement1.setString(21, localLockboxCreditItem.getLockboxSequenceNumber());
          localPreparedStatement1.setString(22, localLockboxCreditItem.getMemo());
          DCUtil.fillCurrencyColumn(localPreparedStatement1, 23, localLockboxCreditItem.getImmediateFloat());
          DCUtil.fillCurrencyColumn(localPreparedStatement1, 24, localLockboxCreditItem.getOneDayFloat());
          DCUtil.fillCurrencyColumn(localPreparedStatement1, 25, localLockboxCreditItem.getTwoDayFloat());
          DCUtil.fillTimestampColumn(localPreparedStatement1, 26, localLockboxCreditItem.getValueDateTime());
          localPreparedStatement1.setString(27, DCAdapter.padRefNum(localLockboxCreditItem.getBankReferenceNumber(), DCAdapter.getBankReferenceNumberType()));
          localPreparedStatement1.setString(28, DCAdapter.padRefNum(localLockboxCreditItem.getCustomerReferenceNumber(), DCAdapter.getCustomerReferenceNumberType()));
          localPreparedStatement1.setLong(29, l2);
          localPreparedStatement1.setString(30, null);
          localPreparedStatement1.setString(31, str1);
          localPreparedStatement1.setString(32, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement1, 33, localCalendar);
          localPreparedStatement1.setString(34, str3);
          localPreparedStatement1.addBatch();
          k++;
          j++;
        }
        localPreparedStatement1.executeBatch();
      }
    }
    finally
    {
      localPreparedStatement1 = null;
      localPreparedStatement2 = null;
    }
  }
  
  private static void a(LockboxAccount paramLockboxAccount, LockboxCreditItem paramLockboxCreditItem, ResultSet paramResultSet)
    throws DCException
  {
    try
    {
      paramLockboxCreditItem.setAccountID(paramLockboxAccount.getAccountID());
      paramLockboxCreditItem.setAccountNumber(paramLockboxAccount.getAccountNumber());
      paramLockboxCreditItem.setBankID(paramLockboxAccount.getBankID());
      paramLockboxCreditItem.setLockboxNumber(paramResultSet.getString(1));
      paramLockboxCreditItem.setItemIndex(paramResultSet.getLong(2));
      paramLockboxCreditItem.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(3), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setItemID(paramResultSet.getInt(4));
      paramLockboxCreditItem.setDocumentType(paramResultSet.getString(5));
      paramLockboxCreditItem.setPayer(paramResultSet.getString(6));
      paramLockboxCreditItem.setCheckAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setCheckNumber(paramResultSet.getString(8));
      paramLockboxCreditItem.setCheckDate(DCUtil.getDateColumn(paramResultSet.getDate(9), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setCouponAccountNumber(paramResultSet.getString(10));
      paramLockboxCreditItem.setCouponAmount1(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setCouponAmount2(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setCouponDate1(DCUtil.getDateColumn(paramResultSet.getDate(13), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setCouponDate2(DCUtil.getDateColumn(paramResultSet.getDate(14), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setCouponReferenceNumber(paramResultSet.getString(15));
      paramLockboxCreditItem.setCheckRoutingNumber(paramResultSet.getString(16));
      paramLockboxCreditItem.setCheckAccountNumber(paramResultSet.getString(17));
      paramLockboxCreditItem.setLockboxWorkType(paramResultSet.getString(18));
      paramLockboxCreditItem.setLockboxBatchNumber(paramResultSet.getString(19));
      paramLockboxCreditItem.setLockboxSequenceNumber(paramResultSet.getString(20));
      paramLockboxCreditItem.setMemo(paramResultSet.getString(21));
      paramLockboxCreditItem.setImmediateFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(22), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(23), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(24), paramLockboxAccount.getCurrencyType(), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(25), paramLockboxAccount.getLocale()));
      paramLockboxCreditItem.setBankReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(26), DCAdapter.getBankReferenceNumberType()));
      paramLockboxCreditItem.setCustomerReferenceNumber(DCAdapter.unPadRefNum(paramResultSet.getString(27), DCAdapter.getCustomerReferenceNumberType()));
      DCUtil.fillExtendABean(paramLockboxCreditItem, paramResultSet, 28);
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to read lockbox credit item database records: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39515;
      throw new DCException(i, "Failed to read lockbox credit item database records.", localException);
    }
  }
  
  protected static LockboxCreditItems getItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    LockboxCreditItems localLockboxCreditItems1 = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append("SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
          localStringBuffer.append(" ORDER BY b.DCCreditItemIndex ");
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, paramString);
          localPreparedStatement.setString(6, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(7, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append("SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
          localStringBuffer.append(" ORDER BY b.DCCreditItemIndex ");
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, paramString);
          localPreparedStatement.setString(5, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append("SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(" ORDER BY b.DCCreditItemIndex ");
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, paramString);
        localPreparedStatement.setString(5, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append("SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
        localStringBuffer.append(" ORDER BY b.DCCreditItemIndex ");
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        localPreparedStatement.setString(3, paramString);
        localPreparedStatement.setString(4, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      localLockboxCreditItems1 = new LockboxCreditItems();
      LockboxCreditItem localLockboxCreditItem = null;
      while (localResultSet.next())
      {
        localLockboxCreditItem = localLockboxCreditItems1.create();
        a(paramLockboxAccount, localLockboxCreditItem, localResultSet);
      }
      LockboxCreditItems localLockboxCreditItems2 = localLockboxCreditItems1;
      return localLockboxCreditItems2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox credit item records: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39515;
      throw new DCException(i, "Failed to get lockbox credit item records.", localException);
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
  
  private static ResultSet a(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap, Connection paramConnection)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().put("Account", paramLockboxAccount.getAccountID());
    if (paramLockboxAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramLockboxAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramLockboxAccount.getBankID());
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", str);
    }
    int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", DCAdapter.PAGESIZE);
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT c.LockboxNumber, b.DCCreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID ");
      a(localStringBuffer, paramLockboxAccount, paramPagingContext, paramHashMap);
      ArrayList localArrayList = a(localStringBuffer, paramPagingContext, paramBoolean, paramHashMap);
      a(localStringBuffer, paramPagingContext, paramBoolean, null, paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int j = 1;
      j = a(localPreparedStatement, j, paramPagingContext, paramHashMap);
      a(localPreparedStatement, j, localArrayList, paramHashMap);
      localPreparedStatement.setMaxRows(i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      localPreparedStatement = null;
    }
    return localResultSet;
  }
  
  public static LockboxCreditItems getPagedItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      LockboxCreditItems localLockboxCreditItems1 = new LockboxCreditItems();
      localConnection = DCAdapter.getDBConnection();
      a(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      jdField_if(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      if (!paramPagingContext.getMap().containsKey("UPPER_BOUND_TransactionIndex"))
      {
        LockboxCreditItems localLockboxCreditItems2 = localLockboxCreditItems1;
        return localLockboxCreditItems2;
      }
      paramPagingContext.getMap().remove("CurrentPage");
      paramPagingContext.getMap().remove("PageSettings");
      int i = 1;
      paramPagingContext.getMap().put("CurrentPage", new Integer(i));
      a(paramPagingContext.getMap());
      localResultSet = a(paramLockboxAccount, paramPagingContext, true, paramHashMap, localConnection);
      while (localResultSet.next())
      {
        localObject1 = localLockboxCreditItems1.create();
        a(paramLockboxAccount, (LockboxCreditItem)localObject1, localResultSet);
      }
      a(paramPagingContext, localLockboxCreditItems1);
      Object localObject1 = localLockboxCreditItems1;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox credit items: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39515, "Failed to get lockbox credit items from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static LockboxCreditItems getRecentItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      a(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      jdField_if(localConnection, paramLockboxAccount, paramPagingContext, paramHashMap);
      localResultSet = a(paramLockboxAccount, paramPagingContext, false, paramHashMap, localConnection);
      LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems();
      while (localResultSet.next())
      {
        localObject1 = localLockboxCreditItems.create();
        a(paramLockboxAccount, (LockboxCreditItem)localObject1, localResultSet);
      }
      a(paramPagingContext, localLockboxCreditItems);
      Object localObject1 = localLockboxCreditItems;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox credit items: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39515, "Failed to get lockbox credit items from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static LockboxCreditItems getNextItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i != -1)
      {
        i++;
        localHashMap.put("CurrentPage", new Integer(i));
        a(localHashMap);
      }
      localResultSet = a(paramLockboxAccount, paramPagingContext, true, paramHashMap, localConnection);
      LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems();
      while (localResultSet.next())
      {
        localObject1 = localLockboxCreditItems.create();
        a(paramLockboxAccount, (LockboxCreditItem)localObject1, localResultSet);
      }
      a(paramPagingContext, localLockboxCreditItems);
      Object localObject1 = localLockboxCreditItems;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox credit items: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39515, "Failed to get lockbox credit items from database.", localException);
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
    int i = MapUtil.getIntValue(paramHashMap, "CurrentPage", 1);
    if (localIntMap.get(i) == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.putAll(paramHashMap);
      localIntMap.put(i, localHashMap);
    }
  }
  
  public static LockboxCreditItems getPreviousItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (i != -1)
      {
        i--;
        localHashMap.put("CurrentPage", new Integer(i));
        localObject1 = (IntMap)localHashMap.get("PageSettings");
        localObject2 = (HashMap)((IntMap)localObject1).get(i);
        if (localReportCriteria != null) {
          for (int j = 0; j < localReportCriteria.getSortCriteria().size(); j++)
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(j);
            localHashMap.put("SORT_VALUE_MIN_" + localReportSortCriterion.getName(), ((HashMap)localObject2).get("SORT_VALUE_MIN_" + localReportSortCriterion.getName()));
            localHashMap.put("SORT_VALUE_MAX_" + localReportSortCriterion.getName(), ((HashMap)localObject2).get("SORT_VALUE_MAX_" + localReportSortCriterion.getName()));
          }
        }
        localHashMap.put("SORT_VALUE_MIN_TransactionIndex", ((HashMap)localObject2).get("SORT_VALUE_MIN_TransactionIndex"));
        localHashMap.put("SORT_VALUE_MAX_TransactionIndex", ((HashMap)localObject2).get("SORT_VALUE_MAX_TransactionIndex"));
      }
      if (i == -1) {
        localResultSet = a(paramLockboxAccount, paramPagingContext, false, paramHashMap, localConnection);
      } else {
        localResultSet = a(paramLockboxAccount, paramPagingContext, true, paramHashMap, localConnection);
      }
      Object localObject1 = new LockboxCreditItems();
      while (localResultSet.next())
      {
        localObject2 = ((LockboxCreditItems)localObject1).createNoAdd();
        a(paramLockboxAccount, (LockboxCreditItem)localObject2, localResultSet);
        if (i == -1) {
          ((LockboxCreditItems)localObject1).add(0, localObject2);
        } else {
          ((LockboxCreditItems)localObject1).add(localObject2);
        }
      }
      a(paramPagingContext, (LockboxCreditItems)localObject1);
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox credit items: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      throw new DCException(39515, "Failed to get lockbox credit items from database.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static long[] a(Connection paramConnection, LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append("SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, paramString);
          localPreparedStatement.setString(6, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(7, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append("SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
          DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
          localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, paramString);
          localPreparedStatement.setString(5, str);
          if (paramLockboxAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append("SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, paramString);
        localPreparedStatement.setString(5, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append("SELECT min(b.DCCreditItemIndex), max(b.DCCreditItemIndex) FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c WHERE a.DCAccountID=c.DCAccountID AND b.DCLockboxID=c.DCLockboxID AND a.AccountID=? AND a.BankID=? AND c.LockboxNumber = ? AND b.DataClassification=? ");
        DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
        localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
        localPreparedStatement.setString(3, paramString);
        localPreparedStatement.setString(4, str);
        if (paramLockboxAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramLockboxAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      long[] arrayOfLong1 = new long[2];
      if (!localResultSet.next()) {
        throw new DCException(39515, "Unable to retrieve the maximum transaction index for the given criteria");
      }
      arrayOfLong1[0] = localResultSet.getLong(1);
      arrayOfLong1[1] = localResultSet.getLong(2);
      long[] arrayOfLong2 = arrayOfLong1;
      return arrayOfLong2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get lockbox credit item transaction indices: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39515;
      throw new DCException(i, "Failed to get lockbox credit item transaction indices.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static long a(Connection paramConnection, LockboxAccount paramLockboxAccount, String paramString, long paramLong, boolean paramBoolean, HashMap paramHashMap)
    throws DCException
  {
    if (!DCAdapter.isPagingDateBased()) {
      return -1L;
    }
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l1 = -1L;
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramBoolean)
    {
      localStringBuffer.append("SELECT min( b.DCCreditItemIndex ) FROM DC_Account a, DC_Lockbox l, DC_LBCreditItems b WHERE a.DCAccountID=l.DCAccountID AND a.AccountID=? AND a.BankID=? AND l.LockboxNumber=? AND l.DCLockboxID = b.DCLockboxID AND b.DataClassification=? AND b.DCCreditItemIndex > ? ");
      DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
    }
    else
    {
      localStringBuffer.append("SELECT max( b.DCCreditItemIndex ) FROM DC_Account a, DC_Lockbox l, DC_LBCreditItems b WHERE a.DCAccountID=l.DCAccountID AND a.AccountID=? AND a.BankID=? AND l.LockboxNumber=? AND l.DCLockboxID = b.DCLockboxID AND b.DataClassification=? AND b.DCCreditItemIndex < ? ");
      DCUtil.appendNullWhereClause(localStringBuffer, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
    }
    try
    {
      localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
      localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
      localPreparedStatement.setString(3, paramString);
      localPreparedStatement.setString(4, str);
      localPreparedStatement.setLong(5, paramLong);
      if (paramLockboxAccount.getRoutingNumber() != null) {
        localPreparedStatement.setString(6, paramLockboxAccount.getRoutingNumber());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localResultSet.next();
      long l2 = localResultSet.getLong(1);
      if (!localResultSet.wasNull())
      {
        Long localLong;
        if (paramBoolean)
        {
          localLong = (Long)paramHashMap.get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE");
          if (localLong == null) {
            throw new DCException("The maximum transaction index for the specified range was not found in the extra hashmap.");
          }
          if (l2 >= localLong.longValue()) {
            return -1L;
          }
        }
        else
        {
          localLong = (Long)paramHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE");
          if (localLong == null) {
            throw new DCException(39516, "The minimum transaction index for the specified range was not found in the extra hashmap.");
          }
          if (l2 <= localLong.longValue()) {
            return -1L;
          }
        }
        l1 = l2;
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get a new lockbox credit item ID: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39516;
      throw new DCException(i, "Failed to get a new lockbox credit item ID.", localException);
    }
    return l1;
  }
  
  private static long a(Connection paramConnection, LockboxAccount paramLockboxAccount, LockboxCreditItem paramLockboxCreditItem, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap2, "DATA_CLASSIFICATION", "P");
    HashMap localHashMap = (HashMap)paramHashMap1.get(str1);
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramHashMap1.put(str1, localHashMap);
    }
    Long localLong = (Long)localHashMap.get(paramLockboxCreditItem.getLockboxNumber());
    if (localLong != null)
    {
      localHashMap.put(paramLockboxCreditItem.getLockboxNumber(), new Long(localLong.longValue() + 1L));
      return localLong.longValue();
    }
    String str2 = paramLockboxCreditItem.getLockboxNumber();
    DateTime localDateTime = paramLockboxCreditItem.getProcessingDate();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str3 = localDateFormat.format(localDateTime.getTime());
    StringBuffer localStringBuffer1 = new StringBuffer(str3);
    localStringBuffer1.append("00000000001");
    StringBuffer localStringBuffer2 = new StringBuffer(str3);
    localStringBuffer2.append("99999999999");
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer3 = new StringBuffer();
    localStringBuffer3.append("SELECT max( b.DCCreditItemIndex ) FROM DC_Account a, DC_Lockbox l, DC_LBCreditItems b WHERE a.DCAccountID=l.DCAccountID AND a.AccountID=? AND a.BankID=? AND l.LockboxNumber=? AND l.DCLockboxID = b.DCLockboxID AND b.DataClassification=? AND b.DCCreditItemIndex >= ? AND b.DCCreditItemIndex < ?");
    DCUtil.appendNullWhereClause(localStringBuffer3, " a.RoutingNumber ", paramLockboxAccount.getRoutingNumber());
    PreparedStatement localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer3.toString());
    long l1 = -1L;
    try
    {
      localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
      localPreparedStatement.setString(2, paramLockboxAccount.getBankID());
      localPreparedStatement.setString(3, str2);
      localPreparedStatement.setString(4, str1);
      localPreparedStatement.setLong(5, Long.parseLong(localStringBuffer1.toString()));
      localPreparedStatement.setLong(6, Long.parseLong(localStringBuffer2.toString()));
      if (paramLockboxAccount.getRoutingNumber() != null) {
        localPreparedStatement.setString(7, paramLockboxAccount.getRoutingNumber());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer3.toString());
      localResultSet.next();
      long l2 = localResultSet.getLong(1);
      if (!localResultSet.wasNull())
      {
        String str4 = Long.toString(l2);
        String str5 = str4.substring(0, 8);
        String str6 = str4.substring(8);
        if (str5.equals(str3))
        {
          long l3 = Long.parseLong(str6);
          l3 += 1L;
          String str7 = jdField_byte.format(l3);
          StringBuffer localStringBuffer4 = new StringBuffer(str3);
          localStringBuffer4.append(str7);
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
      localHashMap.put(paramLockboxCreditItem.getLockboxNumber(), new Long(l1 + 1L));
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get a new date based lockbox credit item ID: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 39516;
      throw new DCException(i, "Failed to get a new date based lockbox credit item ID.", localException);
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
    for (int i = 0; i < localReportSortCriteria.size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i);
      paramStringBuffer.append(a(localReportSortCriterion.getName())).append(", ");
    }
    paramStringBuffer.append(" DCCreditItemIndex ");
  }
  
  private static void a(Connection paramConnection, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramLockboxAccount.getAccountID());
    if (paramLockboxAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramLockboxAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramLockboxAccount.getBankID());
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
      localStringBuffer.append(" FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c ");
      localStringBuffer.append(" WHERE a.DCAccountID = c.DCAccountID and c.DCLockboxID=b.DCLockboxID ");
      a(localStringBuffer, paramLockboxAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(false), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = a(localPreparedStatement, i, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int j = 0; j < localReportSortCriteria.size(); j++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
          paramPagingContext.getMap().put("UPPER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, j + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("UPPER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
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
  
  private static void jdField_if(Connection paramConnection, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    localReportCriteria.getSearchCriteria().put("Account", paramLockboxAccount.getAccountID());
    if (paramLockboxAccount.getRoutingNumber() != null) {
      localReportCriteria.getSearchCriteria().put("RoutingNumber", paramLockboxAccount.getRoutingNumber());
    }
    localReportCriteria.getSearchCriteria().put("BankId", paramLockboxAccount.getBankID());
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
      localStringBuffer.append(" FROM DC_Account a, DC_LBCreditItems b, DC_Lockbox c ");
      localStringBuffer.append(" WHERE a.DCAccountID = c.DCAccountID and c.DCLockboxID=b.DCLockboxID ");
      a(localStringBuffer, paramLockboxAccount, paramPagingContext, paramHashMap);
      a(localStringBuffer, paramPagingContext, true, new Boolean(true), paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = a(localPreparedStatement, i, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int j = 0; j < localReportSortCriteria.size(); j++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
          paramPagingContext.getMap().put("LOWER_BOUND_" + localReportSortCriterion.getName(), DCUtil.getValue((ResultSet)localObject1, j + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
        }
        paramPagingContext.getMap().put("LOWER_BOUND_TransactionIndex", DCUtil.getValue((ResultSet)localObject1, localReportSortCriteria.size() + 1, paramLockboxAccount.getCurrencyType(), localReportCriteria.getLocale()));
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
  
  private static void a(StringBuffer paramStringBuffer, LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
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
    }
    str = localProperties.getProperty("BankId");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and a.BankID = ? ");
    }
    str = localProperties.getProperty("LockboxNumber");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and c.LockboxNumber = ? ");
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
    str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency1 = new Currency(new BigDecimal(str), paramLockboxAccount.getLocale());
        paramStringBuffer.append(" and b.Amount>=?");
      }
      catch (Exception localException1) {}
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0)) {
      try
      {
        Currency localCurrency2 = new Currency(new BigDecimal(str), paramLockboxAccount.getLocale());
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
    str = localProperties.getProperty("LockboxNumber");
    if ((str != null) && (str.length() > 0)) {
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
    for (int i = 0; i < paramArrayList.size(); i++) {
      DCUtil.fillValue(paramPreparedStatement, paramInt++, paramArrayList.get(i));
    }
    return paramInt;
  }
  
  private static ArrayList a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    ArrayList localArrayList1 = new ArrayList();
    int i = MapUtil.getIntValue(paramPagingContext.getMap(), "CurrentPage", -1);
    if (i == 1) {
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
    for (int j = 0; j < localReportSortCriteria.size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
      int k = (DCAdapter.CONNECTIONTYPE != null) && (DCAdapter.CONNECTIONTYPE.indexOf("ASE") != -1) ? 1 : 0;
      int m = ((k != 0) && (localReportSortCriterion.getAsc())) || ((k == 0) && (!localReportSortCriterion.getAsc())) ? 1 : 0;
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
          str4 = "DCCreditItemIndex";
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
          if (m == 0)
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
          if (m == 0)
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
          if (m == 0)
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
      for (j = 1; j < localArrayList2.size(); j++) {
        paramStringBuffer.append(" OR (").append((StringBuffer)localArrayList2.get(j)).append(" ) ");
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
    for (int i = 0; i < localReportSortCriteria.size(); i++)
    {
      if (i != 0) {
        paramStringBuffer.append(", ");
      }
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i);
      paramStringBuffer.append(a(localReportSortCriterion.getName()));
      bool = paramBoolean1 == null ? false : !localReportSortCriterion.getAsc() ? true : paramBoolean ? localReportSortCriterion.getAsc() : paramBoolean1.booleanValue();
      paramStringBuffer.append(bool ? " ASC" : " DESC");
    }
    if (localReportSortCriteria.size() != 0) {
      paramStringBuffer.append(",");
    }
    paramStringBuffer.append(" DCCreditItemIndex ").append(bool ? "ASC" : "DESC");
  }
  
  private static String a(String paramString)
    throws Exception
  {
    if (paramString.equals("Payer")) {
      return "Payor";
    }
    if (paramString.equals("Amount")) {
      return "Amount";
    }
    if (paramString.equals("CouponAccountNumber")) {
      return "CouponAccountNum";
    }
    if (paramString.equals("CouponAmount1")) {
      return "CouponAmount1";
    }
    if (paramString.equals("CouponAmount2")) {
      return "CouponAmount2";
    }
    if (paramString.equals("CouponDate1")) {
      return "CouponDate1";
    }
    if (paramString.equals("CouponDate2")) {
      return "CouponDate2";
    }
    if (paramString.equals("CouponRefNum")) {
      return "CouponRefNum";
    }
    if (paramString.equals("CheckNumber")) {
      return "CheckNumber";
    }
    throw new DCException(1025, "Invalid paging sort criteria `" + paramString + "'.");
  }
  
  private static void a(PagingContext paramPagingContext, LockboxCreditItems paramLockboxCreditItems)
  {
    if ((paramLockboxCreditItems == null) || (paramLockboxCreditItems.size() == 0)) {
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
    int i = 0;
    for (int j = 0; j < localReportCriteria.getSortCriteria().size(); j++)
    {
      localObject2 = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(j);
      Object localObject3 = a((ReportSortCriterion)localObject2, paramLockboxCreditItems.get(0));
      Object localObject4 = a((ReportSortCriterion)localObject2, paramLockboxCreditItems.get(paramLockboxCreditItems.size() - 1));
      if (((ReportSortCriterion)localObject2).getAsc())
      {
        i = 1;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
      }
      else
      {
        i = 0;
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
      }
    }
    Object localObject1 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramLockboxCreditItems.get(0));
    Object localObject2 = a(new ReportSortCriterion(1, "TransactionIndex", true), paramLockboxCreditItems.get(paramLockboxCreditItems.size() - 1));
    if (i != 0)
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
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramObject;
    if (paramReportSortCriterion.getName().equals("Payer")) {
      return localLockboxCreditItem.getPayer();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localLockboxCreditItem.getCheckAmount();
    }
    if (paramReportSortCriterion.getName().equals("CheckNumber")) {
      return localLockboxCreditItem.getCheckNumber();
    }
    if (paramReportSortCriterion.getName().equals("CouponAccountNumber")) {
      return localLockboxCreditItem.getCouponAccountNumber();
    }
    if (paramReportSortCriterion.getName().equals("CouponAmount1")) {
      return localLockboxCreditItem.getCouponAmount1();
    }
    if (paramReportSortCriterion.getName().equals("CouponAmount2")) {
      return localLockboxCreditItem.getCouponAmount2();
    }
    if (paramReportSortCriterion.getName().equals("CouponDate1")) {
      return localLockboxCreditItem.getCouponDate1();
    }
    if (paramReportSortCriterion.getName().equals("CouponDate2")) {
      return localLockboxCreditItem.getCouponDate2();
    }
    if (paramReportSortCriterion.getName().equals("CouponRefNum")) {
      return localLockboxCreditItem.getCouponReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localLockboxCreditItem.getItemIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCLBCreditItems
 * JD-Core Version:    0.7.0.1
 */