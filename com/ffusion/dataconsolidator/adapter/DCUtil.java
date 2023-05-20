package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountKey;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.DCModule;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.csil.handlers.AffiliateBankAdmin;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.fileimporter.fileadapters.BAITypeCodes;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIAvailabilityDistributions;
import com.ffusion.fileimporter.fileadapters.baiparser.BAIParserConsts;
import com.ffusion.fileimporter.fileadapters.baiparser.BAISummaryStatus;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

public class DCUtil
  extends DBUtil
{
  private static final String a = "1000";
  
  public static void appendNullWhereClause(StringBuffer paramStringBuffer, String paramString, Object paramObject)
  {
    paramStringBuffer.append(" and ");
    paramStringBuffer.append(paramString);
    if (paramObject == null) {
      paramStringBuffer.append(" is null");
    } else {
      paramStringBuffer.append(" = ?");
    }
  }
  
  public static void fillDate(PreparedStatement paramPreparedStatement, int paramInt, DateTime paramDateTime)
    throws Exception
  {
    if (paramDateTime != null) {
      paramPreparedStatement.setDate(paramInt, new java.sql.Date(paramDateTime.getTime().getTime()));
    } else {
      paramPreparedStatement.setDate(paramInt, null);
    }
  }
  
  public static Object getValue(ResultSet paramResultSet, int paramInt, String paramString, Locale paramLocale)
    throws Exception
  {
    ResultSetMetaData localResultSetMetaData = paramResultSet.getMetaData();
    switch (localResultSetMetaData.getColumnType(paramInt))
    {
    case -6: 
    case -5: 
    case 2: 
    case 5: 
      return new Long(paramResultSet.getLong(paramInt));
    case 4: 
      return new Integer(paramResultSet.getInt(paramInt));
    case 93: 
      return getTimestampColumn(paramResultSet.getTimestamp(paramInt), paramLocale);
    case 3: 
      return getCurrencyColumn(paramResultSet.getBigDecimal(paramInt), paramString, paramLocale);
    case 12: 
      return paramResultSet.getString(paramInt);
    }
    return null;
  }
  
  public static void fillValue(PreparedStatement paramPreparedStatement, int paramInt, Object paramObject)
    throws Exception
  {
    if ((paramObject instanceof Integer)) {
      paramPreparedStatement.setInt(paramInt, ((Integer)paramObject).intValue());
    } else if ((paramObject instanceof Long)) {
      paramPreparedStatement.setLong(paramInt, ((Long)paramObject).longValue());
    } else if ((paramObject instanceof String)) {
      paramPreparedStatement.setString(paramInt, (String)paramObject);
    } else if ((paramObject instanceof DateTime)) {
      fillTimestampColumn(paramPreparedStatement, paramInt, (DateTime)paramObject);
    } else if ((paramObject instanceof Calendar)) {
      fillTimestampColumn(paramPreparedStatement, paramInt, (Calendar)paramObject);
    } else if ((paramObject instanceof Currency)) {
      fillCurrencyColumn(paramPreparedStatement, paramInt, (Currency)paramObject);
    }
  }
  
  public static void fillBalanceColumn(PreparedStatement paramPreparedStatement, int paramInt, Balance paramBalance)
    throws Exception
  {
    if (paramBalance != null)
    {
      Currency localCurrency = paramBalance.getAmountValue();
      if (localCurrency != null)
      {
        BigDecimal localBigDecimal = localCurrency.getAmountValue();
        try
        {
          localBigDecimal = localBigDecimal.setScale(3, 7);
        }
        catch (ArithmeticException localArithmeticException)
        {
          throw new DCException("Invalid currency. Scale portion must contain no more than three digits.", localArithmeticException);
        }
        paramPreparedStatement.setBigDecimal(paramInt, localBigDecimal);
      }
      else
      {
        paramPreparedStatement.setBigDecimal(paramInt, null);
      }
    }
    else
    {
      paramPreparedStatement.setBigDecimal(paramInt, null);
    }
  }
  
  public static void fillCurrencyColumn(PreparedStatement paramPreparedStatement, int paramInt, Currency paramCurrency)
    throws Exception
  {
    if (paramCurrency != null)
    {
      BigDecimal localBigDecimal = paramCurrency.getAmountValue();
      try
      {
        localBigDecimal = localBigDecimal.setScale(3, 7);
      }
      catch (ArithmeticException localArithmeticException)
      {
        throw new DCException("Invalid currency amount. Scale value must contain no more than three digits.", localArithmeticException);
      }
      paramPreparedStatement.setBigDecimal(paramInt, localBigDecimal);
    }
    else
    {
      paramPreparedStatement.setNull(paramInt, 2);
    }
  }
  
  public static Currency getCurrencyColumn(BigDecimal paramBigDecimal)
  {
    return getCurrencyColumn(paramBigDecimal, Locale.getDefault());
  }
  
  public static Currency getCurrencyColumn(BigDecimal paramBigDecimal, String paramString)
  {
    return getCurrencyColumn(paramBigDecimal, paramString, Locale.getDefault());
  }
  
  public static Currency getCurrencyColumn(BigDecimal paramBigDecimal, Locale paramLocale)
  {
    if (paramBigDecimal == null) {
      return null;
    }
    return new Currency(paramBigDecimal, paramLocale);
  }
  
  public static Currency getCurrencyColumn(BigDecimal paramBigDecimal, String paramString, Locale paramLocale)
  {
    if (paramBigDecimal == null) {
      return null;
    }
    return new Currency(paramBigDecimal, paramString, paramLocale);
  }
  
  public static DateTime getTimestampColumn(Timestamp paramTimestamp)
  {
    return getTimestampColumn(paramTimestamp, Locale.getDefault());
  }
  
  public static DateTime getTimestampColumn(Timestamp paramTimestamp, Locale paramLocale)
  {
    if (paramTimestamp == null) {
      return null;
    }
    return new DateTime(paramTimestamp, paramLocale);
  }
  
  public static DateTime getTimestampColumn(java.sql.Date paramDate, Locale paramLocale)
  {
    if (paramDate == null) {
      return null;
    }
    return new DateTime(paramDate, paramLocale);
  }
  
  public static DateTime getDateColumn(java.sql.Date paramDate)
  {
    return getDateColumn(paramDate, Locale.getDefault());
  }
  
  public static DateTime getDateColumn(java.sql.Date paramDate, Locale paramLocale)
  {
    if (paramDate == null) {
      return null;
    }
    return new DateTime(paramDate, paramLocale);
  }
  
  public static void fillTimestampColumn(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2)
    throws Exception
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString2);
      java.util.Date localDate = localDateFormat.parse(paramString1);
      fillTimestampColumn(paramPreparedStatement, paramInt, new DateTime(localDate, Locale.getDefault(), paramString2));
    }
  }
  
  public static void fillTimestampColumn(PreparedStatement paramPreparedStatement, int paramInt, long paramLong)
    throws Exception
  {
    if (paramLong > 0L) {
      paramPreparedStatement.setTimestamp(paramInt, new Timestamp(paramLong));
    } else {
      paramPreparedStatement.setTimestamp(paramInt, null);
    }
  }
  
  public static void fillTimestampColumn(PreparedStatement paramPreparedStatement, int paramInt, Calendar paramCalendar)
    throws Exception
  {
    if (paramCalendar != null) {
      paramPreparedStatement.setTimestamp(paramInt, new Timestamp(paramCalendar.getTime().getTime()));
    } else {
      paramPreparedStatement.setTimestamp(paramInt, null);
    }
  }
  
  public static void cleanupTable(Connection paramConnection, StringBuffer paramStringBuffer, Timestamp paramTimestamp)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, paramStringBuffer.toString());
      localPreparedStatement.setTimestamp(1, paramTimestamp);
      DBUtil.executeUpdate(localPreparedStatement, paramStringBuffer.toString());
    }
    catch (SQLException localSQLException)
    {
      String str = localSQLException.getSQLState();
      if (!str.equalsIgnoreCase("SQL0100W")) {
        throw localSQLException;
      }
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  public static void cleanupTable(Connection paramConnection, StringBuffer paramStringBuffer, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, paramStringBuffer.toString());
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, paramStringBuffer.toString());
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  public static void cleanupTable(Connection paramConnection, StringBuffer paramStringBuffer, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, paramStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, paramStringBuffer.toString());
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  public static void fillExtendABean(ExtendABean paramExtendABean, ResultSet paramResultSet, int paramInt)
    throws DCException
  {
    try
    {
      String str = DCExtendABeanXML.getExtendABeanXML(paramResultSet.getLong(paramInt));
      if (str != null) {
        paramExtendABean.setExtendABeanXML(str);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to fill ExtendABean with database row.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 310;
      throw new DCException(i, "Unable to fill ExtendABean.", localException);
    }
  }
  
  public static void fillExtendABean(Connection paramConnection, ExtendABean paramExtendABean, ResultSet paramResultSet, int paramInt)
    throws DCException
  {
    try
    {
      String str = DCExtendABeanXML.getExtendABeanXML(paramConnection, paramResultSet.getLong(paramInt));
      if (str != null) {
        paramExtendABean.setExtendABeanXML(str);
      }
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to fill ExtendABean object: SQLException.", localSQLException);
    }
    catch (Exception localException)
    {
      int i = 310;
      throw new DCException(i, "Unable to fill ExtendABean.", localException);
    }
  }
  
  public static PreparedStatement prepareStatement(Connection paramConnection, String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString, paramInt1, paramInt2);
    if (localPreparedStatement == null) {
      throw new Exception("Couldn't prepare statement");
    }
    return localPreparedStatement;
  }
  
  public static Account getAccount(DisbursementAccount paramDisbursementAccount)
  {
    Account localAccount = new Account();
    localAccount.setNumber(paramDisbursementAccount.getAccountNumber());
    localAccount.setID(paramDisbursementAccount.getAccountID());
    localAccount.setNickName(paramDisbursementAccount.getAccountName());
    localAccount.setBankID(paramDisbursementAccount.getBankID());
    localAccount.setCurrencyCode(paramDisbursementAccount.getCurrencyType());
    localAccount.setRoutingNum(paramDisbursementAccount.getRoutingNumber());
    localAccount.setBankName(paramDisbursementAccount.getBankName());
    localAccount.setLocale(paramDisbursementAccount.getLocale());
    return localAccount;
  }
  
  public static DisbursementAccount getDisbursementAccount(Account paramAccount)
  {
    DisbursementAccount localDisbursementAccount = new DisbursementAccount();
    localDisbursementAccount.setAccountNumber(paramAccount.getNumber());
    localDisbursementAccount.setAccountID(paramAccount.getID());
    localDisbursementAccount.setAccountName(paramAccount.getNickName());
    localDisbursementAccount.setBankID(paramAccount.getBankID());
    localDisbursementAccount.setCurrencyType(paramAccount.getCurrencyCode());
    localDisbursementAccount.setRoutingNumber(paramAccount.getRoutingNum());
    localDisbursementAccount.setBankName(paramAccount.getBankName());
    localDisbursementAccount.setLocale(paramAccount.getLocale());
    return localDisbursementAccount;
  }
  
  public static Account getAccount(LockboxAccount paramLockboxAccount)
  {
    Account localAccount = new Account();
    localAccount.setNumber(paramLockboxAccount.getAccountNumber());
    localAccount.setID(paramLockboxAccount.getAccountID());
    localAccount.setNickName(paramLockboxAccount.getNickname());
    localAccount.setBankID(paramLockboxAccount.getBankID());
    localAccount.setCurrencyCode(paramLockboxAccount.getCurrencyType());
    localAccount.setRoutingNum(paramLockboxAccount.getRoutingNumber());
    localAccount.setLocale(paramLockboxAccount.getLocale());
    return localAccount;
  }
  
  public static LockboxAccount getLockboxAccount(Account paramAccount)
  {
    LockboxAccount localLockboxAccount = new LockboxAccount();
    localLockboxAccount.setAccountNumber(paramAccount.getNumber());
    localLockboxAccount.setAccountID(paramAccount.getID());
    localLockboxAccount.setNickname(paramAccount.getNickName());
    localLockboxAccount.setBankID(paramAccount.getBankID());
    localLockboxAccount.setCurrencyType(paramAccount.getCurrencyCode());
    localLockboxAccount.setRoutingNumber(paramAccount.getRoutingNum());
    localLockboxAccount.setLocale(paramAccount.getLocale());
    return localLockboxAccount;
  }
  
  public static int mapAccountType(int paramInt)
  {
    return Account.getAccountGroupFromType(paramInt);
  }
  
  public static void isAccountInfoSufficient(Account paramAccount)
    throws DCException
  {
    String str1 = paramAccount.getBankID();
    String str2 = paramAccount.getID();
    if ((str1 == null) || (str1.length() <= 0)) {
      throw new DCException(424, "Cannot retrieve account: bank ID does not exist");
    }
    if ((str2 == null) || (str2.length() <= 0)) {
      throw new DCException(425, "Cannot retrieve account: account ID does not exist");
    }
  }
  
  public static void isAccountInfoSufficient(LockboxAccount paramLockboxAccount)
    throws DCException
  {
    String str1 = paramLockboxAccount.getBankID();
    String str2 = paramLockboxAccount.getAccountID();
    if ((str1 == null) || (str1.length() <= 0)) {
      throw new DCException(39511, "Cannot retrieve account by lockbox account: bank ID does not exist");
    }
    if ((str2 == null) || (str2.length() <= 0)) {
      throw new DCException(39512, "Cannot retrieve account by lockbox account: account ID does not exist");
    }
  }
  
  public static Transactions updateTransactionRunningBalances(Transactions paramTransactions, Currency paramCurrency, boolean paramBoolean)
  {
    if ((paramTransactions == null) || (paramTransactions.isEmpty())) {
      return null;
    }
    Transactions localTransactions = new Transactions();
    if (paramCurrency == null)
    {
      for (int i = 0; i < paramTransactions.size(); i++)
      {
        Transaction localTransaction1 = (Transaction)paramTransactions.get(i);
        if (localTransaction1.getRunningBalanceValue() != null)
        {
          localTransaction1.setRunningBalance(null);
          localTransactions.add(localTransaction1);
        }
      }
    }
    else
    {
      BigDecimal localBigDecimal = paramCurrency.getAmountValue();
      for (int j = 0; j < paramTransactions.size(); j++)
      {
        Transaction localTransaction2 = (Transaction)paramTransactions.get(j);
        Currency localCurrency1 = localTransaction2.getAmountValue();
        if (localCurrency1 != null) {
          localBigDecimal = localBigDecimal.add(localCurrency1.getAmountValue());
        }
        if ((paramBoolean) || (localTransaction2.getRunningBalanceValue() == null))
        {
          Currency localCurrency2 = new Currency(new BigDecimal(localBigDecimal.toString()), paramCurrency.getCurrencyCode(), paramCurrency.getLocale());
          localTransaction2.setRunningBalance(localCurrency2);
          localTransactions.add(localTransaction2);
        }
      }
    }
    return localTransactions;
  }
  
  public static AccountHistories combineAccountHistoriesByDate(AccountHistories paramAccountHistories)
  {
    if (paramAccountHistories == null) {
      return null;
    }
    AccountHistories localAccountHistories = new AccountHistories();
    localAccountHistories.setLocale(paramAccountHistories.getLocale());
    AccountHistory localAccountHistory1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramAccountHistories.size(); i++)
    {
      AccountHistory localAccountHistory2 = (AccountHistory)paramAccountHistories.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localAccountHistory2.getHistoryDate())))
      {
        localAccountHistory1 = new AccountHistory();
        localAccountHistories.add(localAccountHistory1);
        localDateTime = localAccountHistory2.getHistoryDate();
      }
      if (localAccountHistory2.getAccountID() != null) {
        localAccountHistory1.setAccountID(localAccountHistory2.getAccountID());
      }
      if (localAccountHistory2.getAccountNumber() != null) {
        localAccountHistory1.setAccountNumber(localAccountHistory2.getAccountNumber());
      }
      if (localAccountHistory2.getRoutingNumber() != null) {
        localAccountHistory1.setRoutingNumber(localAccountHistory2.getRoutingNumber());
      }
      if (localAccountHistory2.getBankID() != null) {
        localAccountHistory1.setBankID(localAccountHistory2.getBankID());
      }
      if (localAccountHistory2.getHistoryDate() != null) {
        localAccountHistory1.setHistoryDate(localAccountHistory2.getHistoryDate());
      }
      if (localAccountHistory2.getOpeningLedger() != null) {
        localAccountHistory1.setOpeningLedger(localAccountHistory2.getOpeningLedger());
      }
      if (localAccountHistory2.getAvgOpeningLedgerMTD() != null) {
        localAccountHistory1.setAvgOpeningLedgerMTD(localAccountHistory2.getAvgOpeningLedgerMTD());
      }
      if (localAccountHistory2.getAvgOpeningLedgerYTD() != null) {
        localAccountHistory1.setAvgOpeningLedgerYTD(localAccountHistory2.getAvgOpeningLedgerYTD());
      }
      if (localAccountHistory2.getClosingLedger() != null) {
        localAccountHistory1.setClosingLedger(localAccountHistory2.getClosingLedger());
      }
      if (localAccountHistory2.getAvgClosingLedgerMTD() != null) {
        localAccountHistory1.setAvgClosingLedgerMTD(localAccountHistory2.getAvgClosingLedgerMTD());
      }
      if (localAccountHistory2.getAvgMonth() != null) {
        localAccountHistory1.setAvgMonth(localAccountHistory2.getAvgMonth());
      }
      if (localAccountHistory2.getAggregateBalAdjustment() != null) {
        localAccountHistory1.setAggregateBalAdjustment(localAccountHistory2.getAggregateBalAdjustment());
      }
      if (localAccountHistory2.getAvgClosingLedgerYTDPrevMonth() != null) {
        localAccountHistory1.setAvgClosingLedgerYTDPrevMonth(localAccountHistory2.getAvgClosingLedgerYTDPrevMonth());
      }
      if (localAccountHistory2.getAvgClosingLedgerYTD() != null) {
        localAccountHistory1.setAvgClosingLedgerYTD(localAccountHistory2.getAvgClosingLedgerYTD());
      }
      if (localAccountHistory2.getCurrentLedger() != null) {
        localAccountHistory1.setCurrentLedger(localAccountHistory2.getCurrentLedger());
      }
      if (localAccountHistory2.getNetPositionACH() != null) {
        localAccountHistory1.setNetPositionACH(localAccountHistory2.getNetPositionACH());
      }
      if (localAccountHistory2.getOpenAvailSameDayACHDTC() != null) {
        localAccountHistory1.setOpenAvailSameDayACHDTC(localAccountHistory2.getOpenAvailSameDayACHDTC());
      }
      if (localAccountHistory2.getOpeningAvail() != null) {
        localAccountHistory1.setOpeningAvail(localAccountHistory2.getOpeningAvail());
      }
      if (localAccountHistory2.getAvgOpenAvailMTD() != null) {
        localAccountHistory1.setAvgOpenAvailMTD(localAccountHistory2.getAvgOpenAvailMTD());
      }
      if (localAccountHistory2.getAvgOpenAvailYTD() != null) {
        localAccountHistory1.setAvgOpenAvailYTD(localAccountHistory2.getAvgOpenAvailYTD());
      }
      if (localAccountHistory2.getAvgAvailPrevMonth() != null) {
        localAccountHistory1.setAvgAvailPrevMonth(localAccountHistory2.getAvgAvailPrevMonth());
      }
      if (localAccountHistory2.getDisbursingOpeningAvailBal() != null) {
        localAccountHistory1.setDisbursingOpeningAvailBal(localAccountHistory2.getDisbursingOpeningAvailBal());
      }
      if (localAccountHistory2.getClosingAvail() != null) {
        localAccountHistory1.setClosingAvail(localAccountHistory2.getClosingAvail());
      }
      if (localAccountHistory2.getAvgClosingAvailMTD() != null) {
        localAccountHistory1.setAvgClosingAvailMTD(localAccountHistory2.getAvgClosingAvailMTD());
      }
      if (localAccountHistory2.getAvgClosingAvailPrevMonth() != null) {
        localAccountHistory1.setAvgClosingAvailPrevMonth(localAccountHistory2.getAvgClosingAvailPrevMonth());
      }
      if (localAccountHistory2.getAvgClosingAvailYTDPrevMonth() != null) {
        localAccountHistory1.setAvgClosingAvailYTDPrevMonth(localAccountHistory2.getAvgClosingAvailYTDPrevMonth());
      }
      if (localAccountHistory2.getAvgClosingAvailYTD() != null) {
        localAccountHistory1.setAvgClosingAvailYTD(localAccountHistory2.getAvgClosingAvailYTD());
      }
      if (localAccountHistory2.getLoanBal() != null) {
        localAccountHistory1.setLoanBal(localAccountHistory2.getLoanBal());
      }
      if (localAccountHistory2.getTotalInvestmentPosition() != null) {
        localAccountHistory1.setTotalInvestmentPosition(localAccountHistory2.getTotalInvestmentPosition());
      }
      if (localAccountHistory2.getCurrentAvailCRSSurpressed() != null) {
        localAccountHistory1.setCurrentAvailCRSSurpressed(localAccountHistory2.getCurrentAvailCRSSurpressed());
      }
      if (localAccountHistory2.getCurrentAvail() != null) {
        localAccountHistory1.setCurrentAvail(localAccountHistory2.getCurrentAvail());
      }
      if (localAccountHistory2.getAvgCurrentAvailMTD() != null) {
        localAccountHistory1.setAvgCurrentAvailMTD(localAccountHistory2.getAvgCurrentAvailMTD());
      }
      if (localAccountHistory2.getAvgCurrentAvailYTD() != null) {
        localAccountHistory1.setAvgCurrentAvailYTD(localAccountHistory2.getAvgCurrentAvailYTD());
      }
      if (localAccountHistory2.getTotalFloat() != null) {
        localAccountHistory1.setTotalFloat(localAccountHistory2.getTotalFloat());
      }
      if (localAccountHistory2.getTargetBal() != null) {
        localAccountHistory1.setTargetBal(localAccountHistory2.getTargetBal());
      }
      if (localAccountHistory2.getAdjustedBal() != null) {
        localAccountHistory1.setAdjustedBal(localAccountHistory2.getAdjustedBal());
      }
      if (localAccountHistory2.getAdjustedBalMTD() != null) {
        localAccountHistory1.setAdjustedBalMTD(localAccountHistory2.getAdjustedBalMTD());
      }
      if (localAccountHistory2.getAdjustedBalYTD() != null) {
        localAccountHistory1.setAdjustedBalYTD(localAccountHistory2.getAdjustedBalYTD());
      }
      if (localAccountHistory2.getZeroDayFloat() != null) {
        localAccountHistory1.setZeroDayFloat(localAccountHistory2.getZeroDayFloat());
      }
      if (localAccountHistory2.getOneDayFloat() != null) {
        localAccountHistory1.setOneDayFloat(localAccountHistory2.getOneDayFloat());
      }
      if (localAccountHistory2.getFloatAdjusted() != null) {
        localAccountHistory1.setFloatAdjusted(localAccountHistory2.getFloatAdjusted());
      }
      if (localAccountHistory2.getTwoOrMoreDayFloat() != null) {
        localAccountHistory1.setTwoOrMoreDayFloat(localAccountHistory2.getTwoOrMoreDayFloat());
      }
      if (localAccountHistory2.getThreeOrMoreDayFloat() != null) {
        localAccountHistory1.setThreeOrMoreDayFloat(localAccountHistory2.getThreeOrMoreDayFloat());
      }
      if (localAccountHistory2.getAdjustmentToBal() != null) {
        localAccountHistory1.setAdjustmentToBal(localAccountHistory2.getAdjustmentToBal());
      }
      if (localAccountHistory2.getAvgAdjustmentToBalMTD() != null) {
        localAccountHistory1.setAvgAdjustmentToBalMTD(localAccountHistory2.getAvgAdjustmentToBalMTD());
      }
      if (localAccountHistory2.getAvgAdjustmentToBalYTD() != null) {
        localAccountHistory1.setAvgAdjustmentToBalYTD(localAccountHistory2.getAvgAdjustmentToBalYTD());
      }
      if (localAccountHistory2.getFourDayFloat() != null) {
        localAccountHistory1.setFourDayFloat(localAccountHistory2.getFourDayFloat());
      }
      if (localAccountHistory2.getFiveDayFloat() != null) {
        localAccountHistory1.setFiveDayFloat(localAccountHistory2.getFiveDayFloat());
      }
      if (localAccountHistory2.getSixDayFloat() != null) {
        localAccountHistory1.setSixDayFloat(localAccountHistory2.getSixDayFloat());
      }
      if (localAccountHistory2.getAvgOneDayFloatMTD() != null) {
        localAccountHistory1.setAvgOneDayFloatMTD(localAccountHistory2.getAvgOneDayFloatMTD());
      }
      if (localAccountHistory2.getAvgOneDayFloatYTD() != null) {
        localAccountHistory1.setAvgOneDayFloatYTD(localAccountHistory2.getAvgOneDayFloatYTD());
      }
      if (localAccountHistory2.getAvgTwoDayFloatMTD() != null) {
        localAccountHistory1.setAvgTwoDayFloatMTD(localAccountHistory2.getAvgTwoDayFloatMTD());
      }
      if (localAccountHistory2.getAvgTwoDayFloatYTD() != null) {
        localAccountHistory1.setAvgTwoDayFloatYTD(localAccountHistory2.getAvgTwoDayFloatYTD());
      }
      if (localAccountHistory2.getTransferCalculation() != null) {
        localAccountHistory1.setTransferCalculation(localAccountHistory2.getTransferCalculation());
      }
      if (localAccountHistory2.getTargetBalDeficiency() != null) {
        localAccountHistory1.setTargetBalDeficiency(localAccountHistory2.getTargetBalDeficiency());
      }
      if (localAccountHistory2.getTotalFundingRequirement() != null) {
        localAccountHistory1.setTotalFundingRequirement(localAccountHistory2.getTotalFundingRequirement());
      }
      if (localAccountHistory2.getTotalChecksPaid() != null) {
        localAccountHistory1.setTotalChecksPaid(localAccountHistory2.getTotalChecksPaid());
      }
      if (localAccountHistory2.getGrandTotalCreditMinusDebit() != null) {
        localAccountHistory1.setGrandTotalCreditMinusDebit(localAccountHistory2.getGrandTotalCreditMinusDebit());
      }
      HashMap localHashMap = localAccountHistory1.getHash();
      localHashMap.putAll(localAccountHistory2.getHash());
    }
    return localAccountHistories;
  }
  
  public static AccountSummaries combineAccountSummariesByDate(AccountSummaries paramAccountSummaries)
  {
    if ((paramAccountSummaries == null) || (paramAccountSummaries.size() == 0)) {
      return paramAccountSummaries;
    }
    AccountSummary localAccountSummary = (AccountSummary)paramAccountSummaries.get(0);
    if ((localAccountSummary instanceof AssetAcctSummary)) {
      return jdMethod_int(paramAccountSummaries);
    }
    if ((localAccountSummary instanceof DepositAcctSummary)) {
      return jdMethod_byte(paramAccountSummaries);
    }
    if ((localAccountSummary instanceof CreditCardAcctSummary)) {
      return jdMethod_try(paramAccountSummaries);
    }
    if ((localAccountSummary instanceof LoanAcctSummary)) {
      return jdMethod_new(paramAccountSummaries);
    }
    return paramAccountSummaries;
  }
  
  private static AccountSummaries jdMethod_int(AccountSummaries paramAccountSummaries)
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    localAccountSummaries.setLocale(paramAccountSummaries.getLocale());
    AssetAcctSummary localAssetAcctSummary1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramAccountSummaries.size(); i++)
    {
      AssetAcctSummary localAssetAcctSummary2 = (AssetAcctSummary)paramAccountSummaries.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localAssetAcctSummary2.getSummaryDate())))
      {
        localAssetAcctSummary1 = new AssetAcctSummary();
        localAccountSummaries.add(localAssetAcctSummary1);
        localDateTime = localAssetAcctSummary2.getSummaryDate();
      }
      if (localAssetAcctSummary2.getAccountID() != null) {
        localAssetAcctSummary1.setAccountID(localAssetAcctSummary2.getAccountID());
      }
      if (localAssetAcctSummary2.getAccountNumber() != null) {
        localAssetAcctSummary1.setAccountNumber(localAssetAcctSummary2.getAccountNumber());
      }
      if (localAssetAcctSummary2.getBankID() != null) {
        localAssetAcctSummary1.setBankID(localAssetAcctSummary2.getBankID());
      }
      if (localAssetAcctSummary2.getRoutingNumber() != null) {
        localAssetAcctSummary1.setRoutingNumber(localAssetAcctSummary2.getRoutingNumber());
      }
      if (localAssetAcctSummary2.getSummaryDate() != null) {
        localAssetAcctSummary1.setSummaryDate(localAssetAcctSummary2.getSummaryDate());
      }
      if (localAssetAcctSummary2.getBookValue() != null) {
        localAssetAcctSummary1.setBookValue(localAssetAcctSummary2.getBookValue());
      }
      if (localAssetAcctSummary2.getMarketValue() != null) {
        localAssetAcctSummary1.setMarketValue(localAssetAcctSummary2.getMarketValue());
      }
      if (localAssetAcctSummary2.getValueDate() != null) {
        localAssetAcctSummary1.setValueDate(localAssetAcctSummary2.getValueDate());
      }
      HashMap localHashMap = localAssetAcctSummary1.getHash();
      localHashMap.putAll(localAssetAcctSummary2.getHash());
    }
    return localAccountSummaries;
  }
  
  private static AccountSummaries jdMethod_byte(AccountSummaries paramAccountSummaries)
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    localAccountSummaries.setLocale(paramAccountSummaries.getLocale());
    DepositAcctSummary localDepositAcctSummary1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramAccountSummaries.size(); i++)
    {
      DepositAcctSummary localDepositAcctSummary2 = (DepositAcctSummary)paramAccountSummaries.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localDepositAcctSummary2.getSummaryDate())))
      {
        localDepositAcctSummary1 = new DepositAcctSummary();
        localAccountSummaries.add(localDepositAcctSummary1);
        localDateTime = localDepositAcctSummary2.getSummaryDate();
      }
      if (localDepositAcctSummary2.getCurrentLedger() != null) {
        localDepositAcctSummary1.setCurrentLedger(localDepositAcctSummary2.getCurrentLedger());
      }
      if (localDepositAcctSummary2.getAccountID() != null) {
        localDepositAcctSummary1.setAccountID(localDepositAcctSummary2.getAccountID());
      }
      if (localDepositAcctSummary2.getAccountNumber() != null) {
        localDepositAcctSummary1.setAccountNumber(localDepositAcctSummary2.getAccountNumber());
      }
      if (localDepositAcctSummary2.getBankID() != null) {
        localDepositAcctSummary1.setBankID(localDepositAcctSummary2.getBankID());
      }
      if (localDepositAcctSummary2.getRoutingNumber() != null) {
        localDepositAcctSummary1.setRoutingNumber(localDepositAcctSummary2.getRoutingNumber());
      }
      if (localDepositAcctSummary2.getSummaryDate() != null) {
        localDepositAcctSummary1.setSummaryDate(localDepositAcctSummary2.getSummaryDate());
      }
      if (localDepositAcctSummary2.getTotalCredits() != null) {
        localDepositAcctSummary1.setTotalCredits(localDepositAcctSummary2.getTotalCredits());
      }
      if (localDepositAcctSummary2.getTotalCreditAmtMTD() != null) {
        localDepositAcctSummary1.setTotalCreditAmtMTD(localDepositAcctSummary2.getTotalCreditAmtMTD());
      }
      if (localDepositAcctSummary2.getCreditsNotDetailed() != null) {
        localDepositAcctSummary1.setCreditsNotDetailed(localDepositAcctSummary2.getCreditsNotDetailed());
      }
      if (localDepositAcctSummary2.getDepositsSubjectToFloat() != null) {
        localDepositAcctSummary1.setDepositsSubjectToFloat(localDepositAcctSummary2.getDepositsSubjectToFloat());
      }
      if (localDepositAcctSummary2.getTotalAdjCreditsYTD() != null) {
        localDepositAcctSummary1.setTotalAdjCreditsYTD(localDepositAcctSummary2.getTotalAdjCreditsYTD());
      }
      if (localDepositAcctSummary2.getTotalLockboxDeposits() != null) {
        localDepositAcctSummary1.setTotalLockboxDeposits(localDepositAcctSummary2.getTotalLockboxDeposits());
      }
      if (localDepositAcctSummary2.getTotalDebits() != null) {
        localDepositAcctSummary1.setTotalDebits(localDepositAcctSummary2.getTotalDebits());
      }
      if (localDepositAcctSummary2.getTotalDebitAmtMTD() != null) {
        localDepositAcctSummary1.setTotalDebitAmtMTD(localDepositAcctSummary2.getTotalDebitAmtMTD());
      }
      if (localDepositAcctSummary2.getTodaysTotalDebits() != null) {
        localDepositAcctSummary1.setTodaysTotalDebits(localDepositAcctSummary2.getTodaysTotalDebits());
      }
      if (localDepositAcctSummary2.getTotalDebitsLessWireAndCharge() != null) {
        localDepositAcctSummary1.setTotalDebitsLessWireAndCharge(localDepositAcctSummary2.getTotalDebitsLessWireAndCharge());
      }
      if (localDepositAcctSummary2.getTotalAdjDebitsYTD() != null) {
        localDepositAcctSummary1.setTotalAdjDebitsYTD(localDepositAcctSummary2.getTotalAdjDebitsYTD());
      }
      if (localDepositAcctSummary2.getTotalDebitsExcludeReturns() != null) {
        localDepositAcctSummary1.setTotalDebitsExcludeReturns(localDepositAcctSummary2.getTotalDebitsExcludeReturns());
      }
      if (localDepositAcctSummary2.getImmedAvailAmt() != null) {
        localDepositAcctSummary1.setImmedAvailAmt(localDepositAcctSummary2.getImmedAvailAmt());
      }
      if (localDepositAcctSummary2.getOneDayAvailAmt() != null) {
        localDepositAcctSummary1.setOneDayAvailAmt(localDepositAcctSummary2.getOneDayAvailAmt());
      }
      if (localDepositAcctSummary2.getMoreThanOneDayAvailAmt() != null) {
        localDepositAcctSummary1.setMoreThanOneDayAvailAmt(localDepositAcctSummary2.getMoreThanOneDayAvailAmt());
      }
      if (localDepositAcctSummary2.getValueDate() != null) {
        localDepositAcctSummary1.setValueDate(localDepositAcctSummary2.getValueDate());
      }
      if (localDepositAcctSummary2.getAvailOverdraft() != null) {
        localDepositAcctSummary1.setAvailOverdraft(localDepositAcctSummary2.getAvailOverdraft());
      }
      if (localDepositAcctSummary2.getRestrictedCash() != null) {
        localDepositAcctSummary1.setRestrictedCash(localDepositAcctSummary2.getRestrictedCash());
      }
      if (localDepositAcctSummary2.getAccruedInterest() != null) {
        localDepositAcctSummary1.setAccruedInterest(localDepositAcctSummary2.getAccruedInterest());
      }
      if (localDepositAcctSummary2.getAccruedDividend() != null) {
        localDepositAcctSummary1.setAccruedDividend(localDepositAcctSummary2.getAccruedDividend());
      }
      if (localDepositAcctSummary2.getTotalOverdraftAmt() != null) {
        localDepositAcctSummary1.setTotalOverdraftAmt(localDepositAcctSummary2.getTotalOverdraftAmt());
      }
      if (localDepositAcctSummary2.getNextOverdraftPmtDate() != null) {
        localDepositAcctSummary1.setNextOverdraftPmtDate(localDepositAcctSummary2.getNextOverdraftPmtDate());
      }
      if (localDepositAcctSummary2.getInterestRate() != -1.0F) {
        localDepositAcctSummary1.setInterestRate(localDepositAcctSummary2.getInterestRate());
      }
      if (localDepositAcctSummary2.getOpeningLedger() != null) {
        localDepositAcctSummary1.setOpeningLedger(localDepositAcctSummary2.getOpeningLedger());
      }
      if (localDepositAcctSummary2.getClosingLedger() != null) {
        localDepositAcctSummary1.setClosingLedger(localDepositAcctSummary2.getClosingLedger());
      }
      if (localDepositAcctSummary2.getCurrentAvailBal() != null) {
        localDepositAcctSummary1.setCurrentAvailBal(localDepositAcctSummary2.getCurrentAvailBal());
      }
      if (localDepositAcctSummary2.getLedgerBal() != null) {
        localDepositAcctSummary1.setLedgerBal(localDepositAcctSummary2.getLedgerBal());
      }
      if (localDepositAcctSummary2.getOneDayFloat() != null) {
        localDepositAcctSummary1.setOneDayFloat(localDepositAcctSummary2.getOneDayFloat());
      }
      if (localDepositAcctSummary2.getTwoDayFloat() != null) {
        localDepositAcctSummary1.setTwoDayFloat(localDepositAcctSummary2.getTwoDayFloat());
      }
      if (localDepositAcctSummary2.getTotalFloat() != null) {
        localDepositAcctSummary1.setTotalFloat(localDepositAcctSummary2.getTotalFloat());
      }
      if (localDepositAcctSummary2.getTotalNumberOfDebits() > 0) {
        localDepositAcctSummary1.setTotalNumberOfDebits(localDepositAcctSummary2.getTotalNumberOfDebits());
      }
      if (localDepositAcctSummary2.getTotalNumberOfCredits() > 0) {
        localDepositAcctSummary1.setTotalNumberOfCredits(localDepositAcctSummary2.getTotalNumberOfCredits());
      }
      HashMap localHashMap = localDepositAcctSummary1.getHash();
      localHashMap.putAll(localDepositAcctSummary2.getHash());
    }
    return localAccountSummaries;
  }
  
  private static AccountSummaries jdMethod_try(AccountSummaries paramAccountSummaries)
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    localAccountSummaries.setLocale(paramAccountSummaries.getLocale());
    CreditCardAcctSummary localCreditCardAcctSummary1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramAccountSummaries.size(); i++)
    {
      CreditCardAcctSummary localCreditCardAcctSummary2 = (CreditCardAcctSummary)paramAccountSummaries.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localCreditCardAcctSummary2.getSummaryDate())))
      {
        localCreditCardAcctSummary1 = new CreditCardAcctSummary();
        localAccountSummaries.add(localCreditCardAcctSummary1);
        localDateTime = localCreditCardAcctSummary2.getSummaryDate();
      }
      if (localCreditCardAcctSummary2.getCurrentBalance() != null) {
        localCreditCardAcctSummary1.setCurrentBalance(localCreditCardAcctSummary2.getCurrentBalance());
      }
      if (localCreditCardAcctSummary2.getAccountID() != null) {
        localCreditCardAcctSummary1.setAccountID(localCreditCardAcctSummary2.getAccountID());
      }
      if (localCreditCardAcctSummary2.getAccountNumber() != null) {
        localCreditCardAcctSummary1.setAccountNumber(localCreditCardAcctSummary2.getAccountNumber());
      }
      if (localCreditCardAcctSummary2.getBankID() != null) {
        localCreditCardAcctSummary1.setBankID(localCreditCardAcctSummary2.getBankID());
      }
      if (localCreditCardAcctSummary2.getRoutingNumber() != null) {
        localCreditCardAcctSummary1.setRoutingNumber(localCreditCardAcctSummary2.getRoutingNumber());
      }
      if (localCreditCardAcctSummary2.getSummaryDate() != null) {
        localCreditCardAcctSummary1.setSummaryDate(localCreditCardAcctSummary2.getSummaryDate());
      }
      if (localCreditCardAcctSummary2.getAvailCredit() != null) {
        localCreditCardAcctSummary1.setAvailCredit(localCreditCardAcctSummary2.getAvailCredit());
      }
      if (localCreditCardAcctSummary2.getAmtDue() != null) {
        localCreditCardAcctSummary1.setAmtDue(localCreditCardAcctSummary2.getAmtDue());
      }
      if (localCreditCardAcctSummary2.getInterestRate() != -1.0F) {
        localCreditCardAcctSummary1.setInterestRate(localCreditCardAcctSummary2.getInterestRate());
      }
      if (localCreditCardAcctSummary2.getDueDate() != null) {
        localCreditCardAcctSummary1.setDueDate(localCreditCardAcctSummary2.getDueDate());
      }
      if (localCreditCardAcctSummary2.getCardHolderName() != null) {
        localCreditCardAcctSummary1.setCardHolderName(localCreditCardAcctSummary2.getCardHolderName());
      }
      if (localCreditCardAcctSummary2.getCardExpDate() != null) {
        localCreditCardAcctSummary1.setCardExpDate(localCreditCardAcctSummary2.getCardExpDate());
      }
      if (localCreditCardAcctSummary2.getCreditLimit() != null) {
        localCreditCardAcctSummary1.setCreditLimit(localCreditCardAcctSummary2.getCreditLimit());
      }
      if (localCreditCardAcctSummary2.getLastPaymentAmt() != null) {
        localCreditCardAcctSummary1.setLastPaymentAmt(localCreditCardAcctSummary2.getLastPaymentAmt());
      }
      if (localCreditCardAcctSummary2.getNextPaymentMinAmt() != null) {
        localCreditCardAcctSummary1.setNextPaymentMinAmt(localCreditCardAcctSummary2.getNextPaymentMinAmt());
      }
      if (localCreditCardAcctSummary2.getNextPaymentDue() != null) {
        localCreditCardAcctSummary1.setNextPaymentDue(localCreditCardAcctSummary2.getNextPaymentDue());
      }
      if (localCreditCardAcctSummary2.getLastPaymentDate() != null) {
        localCreditCardAcctSummary1.setLastPaymentDate(localCreditCardAcctSummary2.getLastPaymentDate());
      }
      if (localCreditCardAcctSummary2.getValueDate() != null) {
        localCreditCardAcctSummary1.setValueDate(localCreditCardAcctSummary2.getValueDate());
      }
      HashMap localHashMap = localCreditCardAcctSummary1.getHash();
      localHashMap.putAll(localCreditCardAcctSummary2.getHash());
    }
    return localAccountSummaries;
  }
  
  private static AccountSummaries jdMethod_new(AccountSummaries paramAccountSummaries)
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    localAccountSummaries.setLocale(paramAccountSummaries.getLocale());
    LoanAcctSummary localLoanAcctSummary1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramAccountSummaries.size(); i++)
    {
      LoanAcctSummary localLoanAcctSummary2 = (LoanAcctSummary)paramAccountSummaries.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localLoanAcctSummary2.getSummaryDate())))
      {
        localLoanAcctSummary1 = new LoanAcctSummary();
        localAccountSummaries.add(localLoanAcctSummary1);
        localDateTime = localLoanAcctSummary2.getSummaryDate();
      }
      if (localLoanAcctSummary2.getCurrentBalance() != null) {
        localLoanAcctSummary1.setCurrentBalance(localLoanAcctSummary2.getCurrentBalance());
      }
      if (localLoanAcctSummary2.getAccountID() != null) {
        localLoanAcctSummary1.setAccountID(localLoanAcctSummary2.getAccountID());
      }
      if (localLoanAcctSummary2.getAccountNumber() != null) {
        localLoanAcctSummary1.setAccountNumber(localLoanAcctSummary2.getAccountNumber());
      }
      if (localLoanAcctSummary2.getBankID() != null) {
        localLoanAcctSummary1.setBankID(localLoanAcctSummary2.getBankID());
      }
      if (localLoanAcctSummary2.getRoutingNumber() != null) {
        localLoanAcctSummary1.setRoutingNumber(localLoanAcctSummary2.getRoutingNumber());
      }
      if (localLoanAcctSummary2.getSummaryDate() != null) {
        localLoanAcctSummary1.setSummaryDate(localLoanAcctSummary2.getSummaryDate());
      }
      if (localLoanAcctSummary2.getAvailCredit() != null) {
        localLoanAcctSummary1.setAvailCredit(localLoanAcctSummary2.getAvailCredit());
      }
      if (localLoanAcctSummary2.getAmtDue() != null) {
        localLoanAcctSummary1.setAmtDue(localLoanAcctSummary2.getAmtDue());
      }
      if (localLoanAcctSummary2.getInterestRate() != -1.0F) {
        localLoanAcctSummary1.setInterestRate(localLoanAcctSummary2.getInterestRate());
      }
      if (localLoanAcctSummary2.getDueDate() != null) {
        localLoanAcctSummary1.setDueDate(localLoanAcctSummary2.getDueDate());
      }
      if (localLoanAcctSummary2.getMaturityDate() != null) {
        localLoanAcctSummary1.setMaturityDate(localLoanAcctSummary2.getMaturityDate());
      }
      if (localLoanAcctSummary2.getAccruedInterest() != null) {
        localLoanAcctSummary1.setAccruedInterest(localLoanAcctSummary2.getAccruedInterest());
      }
      if (localLoanAcctSummary2.getOpeningBal() != null) {
        localLoanAcctSummary1.setOpeningBal(localLoanAcctSummary2.getOpeningBal());
      }
      if (localLoanAcctSummary2.getCollateralDescription() != null) {
        localLoanAcctSummary1.setCollateralDescription(localLoanAcctSummary2.getCollateralDescription());
      }
      if (localLoanAcctSummary2.getPrincipalPastDue() != null) {
        localLoanAcctSummary1.setPrincipalPastDue(localLoanAcctSummary2.getPrincipalPastDue());
      }
      if (localLoanAcctSummary2.getInterestPastDue() != null) {
        localLoanAcctSummary1.setInterestPastDue(localLoanAcctSummary2.getInterestPastDue());
      }
      if (localLoanAcctSummary2.getLateFees() != null) {
        localLoanAcctSummary1.setLateFees(localLoanAcctSummary2.getLateFees());
      }
      if (localLoanAcctSummary2.getNextPrincipalAmt() != null) {
        localLoanAcctSummary1.setNextPrincipalAmt(localLoanAcctSummary2.getNextPrincipalAmt());
      }
      if (localLoanAcctSummary2.getNextInterestAmt() != null) {
        localLoanAcctSummary1.setNextInterestAmt(localLoanAcctSummary2.getNextInterestAmt());
      }
      if (localLoanAcctSummary2.getValueDate() != null) {
        localLoanAcctSummary1.setValueDate(localLoanAcctSummary2.getValueDate());
      }
      HashMap localHashMap = localLoanAcctSummary1.getHash();
      localHashMap.putAll(localLoanAcctSummary2.getHash());
    }
    return localAccountSummaries;
  }
  
  public static ExtendedAccountSummaries combineExtendedAccountSummariesByDate(ExtendedAccountSummaries paramExtendedAccountSummaries)
  {
    ExtendedAccountSummaries localExtendedAccountSummaries = new ExtendedAccountSummaries();
    localExtendedAccountSummaries.setLocale(paramExtendedAccountSummaries.getLocale());
    ExtendedAccountSummary localExtendedAccountSummary1 = null;
    DateTime localDateTime = null;
    int i = -1;
    for (int j = 0; j < paramExtendedAccountSummaries.size(); j++)
    {
      ExtendedAccountSummary localExtendedAccountSummary2 = (ExtendedAccountSummary)paramExtendedAccountSummaries.get(j);
      if ((localDateTime == null) || (i == -1) || (!localDateTime.isSameDayInYearAs(localExtendedAccountSummary2.getSummaryDate())) || (i != localExtendedAccountSummary2.getSummaryType()))
      {
        localExtendedAccountSummary1 = new ExtendedAccountSummary();
        localExtendedAccountSummaries.add(localExtendedAccountSummary1);
        localDateTime = localExtendedAccountSummary2.getSummaryDate();
        i = localExtendedAccountSummary2.getSummaryType();
      }
      if (localExtendedAccountSummary2.getAccountID() != null) {
        localExtendedAccountSummary1.setAccountID(localExtendedAccountSummary2.getAccountID());
      }
      if (localExtendedAccountSummary2.getAccountNumber() != null) {
        localExtendedAccountSummary1.setAccountNumber(localExtendedAccountSummary2.getAccountNumber());
      }
      if (localExtendedAccountSummary2.getBankID() != null) {
        localExtendedAccountSummary1.setBankID(localExtendedAccountSummary2.getBankID());
      }
      if (localExtendedAccountSummary2.getRoutingNumber() != null) {
        localExtendedAccountSummary1.setRoutingNumber(localExtendedAccountSummary2.getRoutingNumber());
      }
      if (localExtendedAccountSummary2.getSummaryDate() != null) {
        localExtendedAccountSummary1.setSummaryDate(localExtendedAccountSummary2.getSummaryDate());
      }
      if (localExtendedAccountSummary2.getSummaryType() != -1) {
        localExtendedAccountSummary1.setSummaryType(localExtendedAccountSummary2.getSummaryType());
      }
      if (localExtendedAccountSummary2.getAmt() != null) {
        localExtendedAccountSummary1.setAmt(localExtendedAccountSummary2.getAmt());
      }
      if (localExtendedAccountSummary2.getImmedAvailAmt() != null) {
        localExtendedAccountSummary1.setImmedAvailAmt(localExtendedAccountSummary2.getImmedAvailAmt());
      }
      if (localExtendedAccountSummary2.getOneDayAvailAmt() != null) {
        localExtendedAccountSummary1.setOneDayAvailAmt(localExtendedAccountSummary2.getOneDayAvailAmt());
      }
      if (localExtendedAccountSummary2.getMoreThanOneDayAvailAmt() != null) {
        localExtendedAccountSummary1.setMoreThanOneDayAvailAmt(localExtendedAccountSummary2.getMoreThanOneDayAvailAmt());
      }
      if (localExtendedAccountSummary2.getValueDateTime() != null) {
        localExtendedAccountSummary1.setValueDateTime(localExtendedAccountSummary2.getValueDateTime());
      }
      if (localExtendedAccountSummary2.getItemsCount() != -1) {
        localExtendedAccountSummary1.setItemsCount(localExtendedAccountSummary2.getItemsCount());
      }
      HashMap localHashMap = localExtendedAccountSummary1.getHash();
      localHashMap.putAll(localExtendedAccountSummary2.getHash());
    }
    return localExtendedAccountSummaries;
  }
  
  public static LockboxSummaries combineLockboxSummariesByDate(LockboxSummaries paramLockboxSummaries)
  {
    if (paramLockboxSummaries == null) {
      return null;
    }
    LockboxSummaries localLockboxSummaries = new LockboxSummaries(paramLockboxSummaries.getLocale());
    LockboxSummary localLockboxSummary1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramLockboxSummaries.size(); i++)
    {
      LockboxSummary localLockboxSummary2 = (LockboxSummary)paramLockboxSummaries.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localLockboxSummary2.getSummaryDate())))
      {
        localLockboxSummary1 = new LockboxSummary(paramLockboxSummaries.getLocale());
        localLockboxSummaries.add(localLockboxSummary1);
        localDateTime = localLockboxSummary2.getSummaryDate();
      }
      if (localLockboxSummary2.getLockboxAccount() != null) {
        localLockboxSummary1.setLockboxAccount(localLockboxSummary2.getLockboxAccount());
      }
      if (localLockboxSummary2.getSummaryDate() != null) {
        localLockboxSummary1.setSummaryDate(localLockboxSummary2.getSummaryDate());
      }
      if (localLockboxSummary2.getTotalLockboxCredits() != null) {
        localLockboxSummary1.setTotalLockboxCredits(localLockboxSummary2.getTotalLockboxCredits());
      }
      if (localLockboxSummary2.getTotalLockboxDebits() != null) {
        localLockboxSummary1.setTotalLockboxDebits(localLockboxSummary2.getTotalLockboxDebits());
      }
      if (localLockboxSummary2.getTotalNumLockboxCredits() != -1) {
        localLockboxSummary1.setTotalNumLockboxCredits(localLockboxSummary2.getTotalNumLockboxCredits());
      }
      if (localLockboxSummary2.getTotalNumLockboxDebits() != -1) {
        localLockboxSummary1.setTotalNumLockboxDebits(localLockboxSummary2.getTotalNumLockboxDebits());
      }
      if (localLockboxSummary2.getImmediateFloat() != null) {
        localLockboxSummary1.setImmediateFloat(localLockboxSummary2.getImmediateFloat());
      }
      if (localLockboxSummary2.getOneDayFloat() != null) {
        localLockboxSummary1.setOneDayFloat(localLockboxSummary2.getOneDayFloat());
      }
      if (localLockboxSummary2.getTwoDayFloat() != null) {
        localLockboxSummary1.setTwoDayFloat(localLockboxSummary2.getTwoDayFloat());
      }
      HashMap localHashMap = localLockboxSummary1.getHash();
      localHashMap.putAll(localLockboxSummary2.getHash());
      localLockboxSummary1.setHash(localHashMap);
    }
    return localLockboxSummaries;
  }
  
  public static DisbursementSummaries combineDisbursementSummariesByDate(DisbursementSummaries paramDisbursementSummaries)
  {
    if (paramDisbursementSummaries == null) {
      return null;
    }
    DisbursementSummaries localDisbursementSummaries = new DisbursementSummaries(paramDisbursementSummaries.getLocale());
    DisbursementSummary localDisbursementSummary1 = null;
    DateTime localDateTime = null;
    for (int i = 0; i < paramDisbursementSummaries.size(); i++)
    {
      DisbursementSummary localDisbursementSummary2 = (DisbursementSummary)paramDisbursementSummaries.get(i);
      if ((localDateTime == null) || (!localDateTime.isSameDayInYearAs(localDisbursementSummary2.getSummaryDate())))
      {
        localDisbursementSummary1 = new DisbursementSummary(paramDisbursementSummaries.getLocale());
        localDisbursementSummaries.add(localDisbursementSummary1);
        localDateTime = localDisbursementSummary2.getSummaryDate();
      }
      if (localDisbursementSummary1.getAccount() == null) {
        localDisbursementSummary1.setAccount(localDisbursementSummary2.getAccount());
      }
      if (localDisbursementSummary1.getSummaryDate() == null) {
        localDisbursementSummary1.setSummaryDate(localDisbursementSummary2.getSummaryDate());
      }
      if (localDisbursementSummary1.getNumItemsPending() == -1) {
        localDisbursementSummary1.setNumItemsPending(localDisbursementSummary2.getNumItemsPending());
      }
      if (localDisbursementSummary1.getTotalDebits() == null) {
        localDisbursementSummary1.setTotalDebits(localDisbursementSummary2.getTotalDebits());
      }
      if (localDisbursementSummary1.getTotalCredits() == null) {
        localDisbursementSummary1.setTotalCredits(localDisbursementSummary2.getTotalCredits());
      }
      if (localDisbursementSummary1.getTotalDTCCredits() == null) {
        localDisbursementSummary1.setTotalDTCCredits(localDisbursementSummary2.getTotalDTCCredits());
      }
      if (localDisbursementSummary1.getImmediateFundsNeeded() == null) {
        localDisbursementSummary1.setImmediateFundsNeeded(localDisbursementSummary2.getImmediateFundsNeeded());
      }
      if (localDisbursementSummary1.getOneDayFundsNeeded() == null) {
        localDisbursementSummary1.setOneDayFundsNeeded(localDisbursementSummary2.getOneDayFundsNeeded());
      }
      if (localDisbursementSummary1.getTwoDayFundsNeeded() == null) {
        localDisbursementSummary1.setTwoDayFundsNeeded(localDisbursementSummary2.getTwoDayFundsNeeded());
      }
      if (localDisbursementSummary1.getValueDateTime() == null) {
        localDisbursementSummary1.setValueDateTime(localDisbursementSummary2.getValueDateTime());
      }
      if (localDisbursementSummary1.getTotalChecksPaidEarlyAmount() == null) {
        localDisbursementSummary1.setTotalChecksPaidEarlyAmount(localDisbursementSummary2.getTotalChecksPaidEarlyAmount());
      }
      if (localDisbursementSummary1.getTotalChecksPaidLateAmount() == null) {
        localDisbursementSummary1.setTotalChecksPaidLateAmount(localDisbursementSummary2.getTotalChecksPaidLateAmount());
      }
      if (localDisbursementSummary1.getTotalChecksPaidLastAmount() == null) {
        localDisbursementSummary1.setTotalChecksPaidLastAmount(localDisbursementSummary2.getTotalChecksPaidLastAmount());
      }
      if (localDisbursementSummary1.getFedPresentmentEstimate() == null) {
        localDisbursementSummary1.setFedPresentmentEstimate(localDisbursementSummary2.getFedPresentmentEstimate());
      }
      if (localDisbursementSummary1.getLateDebits() == null) {
        localDisbursementSummary1.setLateDebits(localDisbursementSummary2.getLateDebits());
      }
      HashMap localHashMap = localDisbursementSummary2.getHash();
      localHashMap.putAll(localDisbursementSummary1.getHash());
      localDisbursementSummary1.setHash(localHashMap);
    }
    return localDisbursementSummaries;
  }
  
  private static boolean jdMethod_int(AccountHistory paramAccountHistory, String paramString, Object paramObject, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      Currency localCurrency = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("OPENINGLEDGER"))
      {
        paramAccountHistory.setOpeningLedger(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGOPENLEDGERMTD"))
      {
        paramAccountHistory.setAvgOpeningLedgerMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGOPENLEDGERYTD"))
      {
        paramAccountHistory.setAvgOpeningLedgerYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("CLOSINGLEDGER"))
      {
        paramAccountHistory.setClosingLedger(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGCLOSELEDGERMTD"))
      {
        paramAccountHistory.setAvgClosingLedgerMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGMONTH"))
      {
        paramAccountHistory.setAvgMonth(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AGGBALANCEADJUST"))
      {
        paramAccountHistory.setAggregateBalAdjustment(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVCLOSELEDGYTDPRVM"))
      {
        paramAccountHistory.setAvgClosingLedgerYTDPrevMonth(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGCLOSELEDGERYTD"))
      {
        paramAccountHistory.setAvgClosingLedgerYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("CURRENTLEDGER"))
      {
        paramAccountHistory.setCurrentLedger(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ACHNETPOSITION"))
      {
        paramAccountHistory.setNetPositionACH(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("OPAVAISAMDAYACHDTC"))
      {
        paramAccountHistory.setOpenAvailSameDayACHDTC(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("OPENINGAVAILABLE"))
      {
        paramAccountHistory.setOpeningAvail(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGOPENAVAILMTD"))
      {
        paramAccountHistory.setAvgOpenAvailMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGOPENAVAILYTD"))
      {
        paramAccountHistory.setAvgOpenAvailYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGAVAILPREVMONTH"))
      {
        paramAccountHistory.setAvgAvailPrevMonth(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("DISBOPENAVAILBAL"))
      {
        paramAccountHistory.setDisbursingOpeningAvailBal(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("CLOSINGAVAIL"))
      {
        paramAccountHistory.setClosingAvail(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGCLOSEAVAILMTD"))
      {
        paramAccountHistory.setAvgClosingAvailMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVCLOSEAVAILPREM"))
      {
        paramAccountHistory.setAvgClosingAvailPrevMonth(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVCLOSEAVAILYTDPRM"))
      {
        paramAccountHistory.setAvgClosingAvailYTDPrevMonth(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVCLOSEAVAILYTD"))
      {
        paramAccountHistory.setAvgClosingAvailYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("LOANBALANCE"))
      {
        paramAccountHistory.setLoanBal(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TOTALINVESTPOSN"))
      {
        paramAccountHistory.setTotalInvestmentPosition(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("CURRAVAILCRSSUPR"))
      {
        paramAccountHistory.setCurrentAvailCRSSurpressed(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("CURRENTAVAIL"))
      {
        paramAccountHistory.setCurrentAvail(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGCURRAVAILMTD"))
      {
        paramAccountHistory.setAvgCurrentAvailMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGCURRAVAILYTD"))
      {
        paramAccountHistory.setAvgCurrentAvailYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TOTALFLOAT"))
      {
        paramAccountHistory.setTotalFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TARGETBALANCE"))
      {
        paramAccountHistory.setTargetBal(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ADJBALANCE"))
      {
        paramAccountHistory.setAdjustedBal(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ADJBALANCEMTD"))
      {
        paramAccountHistory.setAdjustedBalMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ADJBALANCEYTD"))
      {
        paramAccountHistory.setAdjustedBalYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ZERODAYFLOAT"))
      {
        paramAccountHistory.setZeroDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ONEDAYFLOAT"))
      {
        paramAccountHistory.setOneDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("FLOATADJ"))
      {
        paramAccountHistory.setFloatAdjusted(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TWOMOREDAYFLOAT"))
      {
        paramAccountHistory.setTwoOrMoreDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("THREEMOREDAYFLOAT"))
      {
        paramAccountHistory.setThreeOrMoreDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("ADJTOBALANCES"))
      {
        paramAccountHistory.setAdjustmentToBal(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGADJTOBALMTD"))
      {
        paramAccountHistory.setAvgAdjustmentToBalMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGADJTOBALYTD"))
      {
        paramAccountHistory.setAvgAdjustmentToBalYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("FOURDAYFLOAT"))
      {
        paramAccountHistory.setFourDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("FIVEDAYFLOAT"))
      {
        paramAccountHistory.setFiveDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("SIXDAYFLOAT"))
      {
        paramAccountHistory.setSixDayFloat(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGONEDAYFLOATMTD"))
      {
        paramAccountHistory.setAvgOneDayFloatMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGONEDAYFLOATYTD"))
      {
        paramAccountHistory.setAvgOneDayFloatYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGTWODAYFLOATMTD"))
      {
        paramAccountHistory.setAvgTwoDayFloatMTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("AVGTWODAYFLOATYTD"))
      {
        paramAccountHistory.setAvgTwoDayFloatYTD(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TRANSFERCALC"))
      {
        paramAccountHistory.setTransferCalculation(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TARGBALDEFICIENCY"))
      {
        paramAccountHistory.setTargetBalDeficiency(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TOTALFUNDINGREQ"))
      {
        paramAccountHistory.setTotalFundingRequirement(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("GRANDTOTCREDMINDEB"))
      {
        paramAccountHistory.setGrandTotalCreditMinusDebit(localCurrency);
        paramAccountHistory.setGrandNumCreditMinusDebit(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALCHECKSPAID"))
      {
        paramAccountHistory.setTotalChecksPaid(localCurrency);
        paramAccountHistory.setNumChecksPaid(paramInt1);
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static boolean removeTypeCodesFromAccountHistory(ArrayList paramArrayList, AccountHistory paramAccountHistory)
  {
    boolean bool1 = false;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      if (BAITypeCodes.isAccountHistoryCode(j))
      {
        bool1 = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type codes are being mapped to the account history table instead of the extended account summary table.");
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int k = getIndexOfModule(localArrayList, "Account History");
        String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
        int m = localBAITypeCodeInfo.getDataType();
        boolean bool2 = jdMethod_int(paramAccountHistory, str2, null, -1, m);
        if ((!bool2) && (str2 != null)) {
          paramAccountHistory.put(str2, null);
        }
      }
    }
    return bool1;
  }
  
  public static boolean convertSummaryStatusItemsToAccountHistory(Vector paramVector, AccountHistory paramAccountHistory)
  {
    boolean bool1 = false;
    for (int i = 0; i < paramVector.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(i);
      if (BAITypeCodes.isAccountHistoryCode(localBAISummaryStatus._typeCode))
      {
        bool1 = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(localBAISummaryStatus._typeCode);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type code (" + localBAISummaryStatus._typeCode + ") is being mapped to the account history table instead of the extended account summary table.");
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int j = getIndexOfModule(localArrayList, "Account History");
        String str = ((DCModule)localArrayList.get(j)).getColumnName();
        Currency localCurrency = null;
        if (localBAITypeCodeInfo.getDataType() == 0)
        {
          localCurrency = localBAISummaryStatus._data == null ? null : new Currency((BigDecimal)localBAISummaryStatus._data, paramAccountHistory.getLocale());
          boolean bool2 = jdMethod_int(paramAccountHistory, str, localCurrency, localBAISummaryStatus._itemCount, localBAITypeCodeInfo.getDataType());
          if ((!bool2) && (str != null)) {
            paramAccountHistory.put(str, localCurrency);
          }
        }
        else if (str != null)
        {
          paramAccountHistory.put(str, localBAISummaryStatus._data);
        }
      }
    }
    return bool1;
  }
  
  private static boolean jdMethod_int(DepositAcctSummary paramDepositAcctSummary, String paramString, Object paramObject, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      Currency localCurrency = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("OPENINGLEDGER"))
      {
        paramDepositAcctSummary.setOpeningLedger(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("CLOSINGLEDGER"))
      {
        paramDepositAcctSummary.setClosingLedger(localCurrency);
      }
      else if (paramString.equalsIgnoreCase("TOTALCREDITS"))
      {
        paramDepositAcctSummary.setTotalCredits(localCurrency);
        paramDepositAcctSummary.setTotalNumberOfCredits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALCREDITSMTD"))
      {
        paramDepositAcctSummary.setTotalCreditAmtMTD(localCurrency);
        paramDepositAcctSummary.setNumCreditMTD(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("CREDITSNOTDETAILED"))
      {
        paramDepositAcctSummary.setCreditsNotDetailed(localCurrency);
        paramDepositAcctSummary.setNumCreditsNotDetailed(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("DEPOSITSSUBJFLOAT"))
      {
        paramDepositAcctSummary.setDepositsSubjectToFloat(localCurrency);
        paramDepositAcctSummary.setNumDepositsSubjectToFloat(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALADJCREDITSYTD"))
      {
        paramDepositAcctSummary.setTotalAdjCreditsYTD(localCurrency);
        paramDepositAcctSummary.setNumAdjCreditsYTD(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALLOCKBOXDEPOSITS"))
      {
        paramDepositAcctSummary.setTotalLockboxDeposits(localCurrency);
        paramDepositAcctSummary.setNumLockboxDeposits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALDEBITS"))
      {
        paramDepositAcctSummary.setTotalDebits(localCurrency);
        paramDepositAcctSummary.setTotalNumberOfDebits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALDEBITSMTD"))
      {
        paramDepositAcctSummary.setTotalDebitAmtMTD(localCurrency);
        paramDepositAcctSummary.setNumDebitMTD(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TODAYTOTALDEBITS"))
      {
        paramDepositAcctSummary.setTodaysTotalDebits(localCurrency);
        paramDepositAcctSummary.setNumTodaysTotalDebits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALDEBITLESSWIRE"))
      {
        paramDepositAcctSummary.setTotalDebitsLessWireAndCharge(localCurrency);
        paramDepositAcctSummary.setNumDebitsLessWireAndCharge(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALADJDEBITSYTD"))
      {
        paramDepositAcctSummary.setTotalAdjDebitsYTD(localCurrency);
        paramDepositAcctSummary.setNumAdjDebitsYTD(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALDEBITSEXRETN"))
      {
        paramDepositAcctSummary.setTotalDebitsExcludeReturns(localCurrency);
        paramDepositAcctSummary.setNumDebitsExcludeReturns(paramInt1);
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static void removeTypeCodesFromDepositAccountSummary(ArrayList paramArrayList, DepositAcctSummary paramDepositAcctSummary)
  {
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log(Level.FINE, "Unknown BAI type codes are being mapped to the account summary tables instead of the extended account summary table.");
      }
      ArrayList localArrayList = localBAITypeCodeInfo.getModules();
      int k = getIndexOfModule(localArrayList, "Account Summary");
      String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
      int m = localBAITypeCodeInfo.getDataType();
      boolean bool = jdMethod_int(paramDepositAcctSummary, str2, (Currency)null, -1, m);
      if ((!bool) && (str2 != null)) {
        paramDepositAcctSummary.put(str2, null);
      }
    }
  }
  
  private static void jdMethod_int(BAISummaryStatus paramBAISummaryStatus, DepositAcctSummary paramDepositAcctSummary, String paramString)
  {
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    try
    {
      localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramBAISummaryStatus._typeCode);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.FINE, "Unknown BAI type code (" + paramBAISummaryStatus._typeCode + ") is being mapped to the account summary tables instead of the extended account summary table.");
    }
    ArrayList localArrayList = localBAITypeCodeInfo.getModules();
    int i = getIndexOfModule(localArrayList, "Account Summary");
    String str = ((DCModule)localArrayList.get(i)).getColumnName();
    Object localObject;
    if (localBAITypeCodeInfo.getDataType() == 0)
    {
      localObject = paramBAISummaryStatus._data == null ? null : new Currency((BigDecimal)paramBAISummaryStatus._data, paramString, paramDepositAcctSummary.getLocale());
      if ((paramBAISummaryStatus._fundsType.equals("Z")) || (paramBAISummaryStatus._fundsType.equals("V")))
      {
        boolean bool = jdMethod_int(paramDepositAcctSummary, str, localObject, paramBAISummaryStatus._itemCount, localBAITypeCodeInfo.getDataType());
        if ((!bool) && (str != null)) {
          paramDepositAcctSummary.put(str, localObject);
        }
      }
      else if (paramBAISummaryStatus._fundsType.equals("0"))
      {
        paramDepositAcctSummary.setImmedAvailAmt((Currency)localObject);
      }
      else if (paramBAISummaryStatus._fundsType.equals("1"))
      {
        paramDepositAcctSummary.setOneDayAvailAmt((Currency)localObject);
      }
      else if (paramBAISummaryStatus._fundsType.equals("2"))
      {
        paramDepositAcctSummary.setMoreThanOneDayAvailAmt((Currency)localObject);
      }
    }
    else if (str != null)
    {
      paramDepositAcctSummary.put(str, paramBAISummaryStatus._data);
    }
    if (paramBAISummaryStatus._fundsType.equals("S"))
    {
      localObject = paramBAISummaryStatus._immediateAvailabilityAmount == null ? null : new Currency(paramBAISummaryStatus._immediateAvailabilityAmount, paramDepositAcctSummary.getLocale());
      paramDepositAcctSummary.setImmedAvailAmt((Currency)localObject);
      localObject = paramBAISummaryStatus._oneDayAvailabilityAmount == null ? null : new Currency(paramBAISummaryStatus._oneDayAvailabilityAmount, paramDepositAcctSummary.getLocale());
      paramDepositAcctSummary.setOneDayAvailAmt((Currency)localObject);
      localObject = paramBAISummaryStatus._moreThanOneDayAvailabilityAmount == null ? null : new Currency(paramBAISummaryStatus._moreThanOneDayAvailabilityAmount, paramDepositAcctSummary.getLocale());
      paramDepositAcctSummary.setMoreThanOneDayAvailAmt((Currency)localObject);
    }
    else if (paramBAISummaryStatus._fundsType.equals("D"))
    {
      localObject = paramBAISummaryStatus._distributions;
      BAIAvailabilityDistributions localBAIAvailabilityDistributions = null;
      if (localObject != null)
      {
        BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
        BigDecimal localBigDecimal2 = new BigDecimal(0.0D);
        BigDecimal localBigDecimal3 = new BigDecimal(0.0D);
        for (int j = 0; j < ((Vector)localObject).size(); j++)
        {
          localBAIAvailabilityDistributions = (BAIAvailabilityDistributions)((Vector)localObject).get(j);
          switch (localBAIAvailabilityDistributions._availabilityInDays)
          {
          case 0: 
            localBigDecimal1 = localBigDecimal1.add(localBAIAvailabilityDistributions._availableAmount);
            break;
          case 1: 
            localBigDecimal2 = localBigDecimal2.add(localBAIAvailabilityDistributions._availableAmount);
            break;
          default: 
            localBigDecimal3 = localBigDecimal3.add(localBAIAvailabilityDistributions._availableAmount);
          }
        }
        paramDepositAcctSummary.setImmedAvailAmt(new Currency(localBigDecimal1, paramDepositAcctSummary.getLocale()));
        paramDepositAcctSummary.setOneDayAvailAmt(new Currency(localBigDecimal2, paramDepositAcctSummary.getLocale()));
        paramDepositAcctSummary.setMoreThanOneDayAvailAmt(new Currency(localBigDecimal3, paramDepositAcctSummary.getLocale()));
      }
    }
  }
  
  private static boolean jdMethod_int(AssetAcctSummary paramAssetAcctSummary, String paramString, Object paramObject, int paramInt)
  {
    if (paramInt == 0)
    {
      Currency localCurrency = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("BOOKVALUE")) {
        paramAssetAcctSummary.setBookValue(localCurrency);
      } else if (paramString.equalsIgnoreCase("MARKETVALUE")) {
        paramAssetAcctSummary.setMarketValue(localCurrency);
      } else {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static void removeTypeCodesFromAssetAccountSummary(ArrayList paramArrayList, AssetAcctSummary paramAssetAcctSummary)
  {
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log(Level.FINE, "Unknown BAI type codes are being mapped to the account summary tables instead of the extended account summary table.");
      }
      ArrayList localArrayList = localBAITypeCodeInfo.getModules();
      int k = getIndexOfModule(localArrayList, "Account Summary");
      String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
      int m = localBAITypeCodeInfo.getDataType();
      boolean bool = jdMethod_int(paramAssetAcctSummary, str2, null, m);
      if ((!bool) && (str2 != null)) {
        paramAssetAcctSummary.put(str2, null);
      }
    }
  }
  
  private static void jdMethod_int(BAISummaryStatus paramBAISummaryStatus, AssetAcctSummary paramAssetAcctSummary, String paramString)
  {
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    try
    {
      localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramBAISummaryStatus._typeCode);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.FINE, "Unknown BAI type code (" + paramBAISummaryStatus._typeCode + ") is being mapped to the account summary tables instead of the extended account summary table.");
    }
    ArrayList localArrayList = localBAITypeCodeInfo.getModules();
    int i = getIndexOfModule(localArrayList, "Account Summary");
    String str = ((DCModule)localArrayList.get(i)).getColumnName();
    if (localBAITypeCodeInfo.getDataType() == 0)
    {
      Currency localCurrency = paramBAISummaryStatus._data == null ? null : new Currency((BigDecimal)paramBAISummaryStatus._data, paramString, paramAssetAcctSummary.getLocale());
      boolean bool = jdMethod_int(paramAssetAcctSummary, str, localCurrency, localBAITypeCodeInfo.getDataType());
      if ((!bool) && (str != null)) {
        paramAssetAcctSummary.put(str, localCurrency);
      }
    }
    else if (str != null)
    {
      paramAssetAcctSummary.put(str, paramBAISummaryStatus._data);
    }
  }
  
  private static boolean jdMethod_int(CreditCardAcctSummary paramCreditCardAcctSummary, String paramString, Object paramObject, int paramInt)
  {
    Object localObject;
    if (paramInt == 0)
    {
      localObject = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("AVAILABLECREDIT")) {
        paramCreditCardAcctSummary.setAvailCredit((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("AMOUNTDUE")) {
        paramCreditCardAcctSummary.setAmtDue((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("CREDITLIMIT")) {
        paramCreditCardAcctSummary.setCreditLimit((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("LASTPAYMENTAMT")) {
        paramCreditCardAcctSummary.setLastPaymentAmt((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("NEXTPAYMENTMINAMT")) {
        paramCreditCardAcctSummary.setNextPaymentMinAmt((Currency)localObject);
      } else {
        return false;
      }
    }
    else if (paramInt == 2)
    {
      localObject = (DateTime)paramObject;
      if (paramString.equalsIgnoreCase("DUEDATE")) {
        paramCreditCardAcctSummary.setDueDate((DateTime)localObject);
      } else if (paramString.equalsIgnoreCase("CARDEXPDATE")) {
        paramCreditCardAcctSummary.setCardExpDate((DateTime)localObject);
      } else if (paramString.equalsIgnoreCase("NEXTPAYMENTDUE")) {
        paramCreditCardAcctSummary.setNextPaymentDue((DateTime)localObject);
      } else if (paramString.equalsIgnoreCase("LASTPAYMENTDATE")) {
        paramCreditCardAcctSummary.setLastPaymentDate((DateTime)localObject);
      } else if (paramString.equalsIgnoreCase("VALUEDATETIME")) {
        paramCreditCardAcctSummary.setValueDate((DateTime)localObject);
      } else {
        return false;
      }
    }
    else if (paramInt == 1)
    {
      localObject = (String)paramObject;
      if (paramString.equalsIgnoreCase("CARDHOLDERNAME")) {
        paramCreditCardAcctSummary.setCardHolderName((String)localObject);
      } else {
        return false;
      }
    }
    else if (paramInt == 4)
    {
      float f = -1.0F;
      if (paramObject != null) {
        f = ((Float)paramObject).floatValue();
      }
      if (paramString.equalsIgnoreCase("INTERESTRATE")) {
        paramCreditCardAcctSummary.setInterestRate(f);
      } else {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static void removeTypeCodesFromCreditCardAccountSummary(ArrayList paramArrayList, CreditCardAcctSummary paramCreditCardAcctSummary)
  {
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log(Level.FINE, "Unknown BAI type codes are being mapped to the account summary tables instead of the extended account summary table.");
      }
      ArrayList localArrayList = localBAITypeCodeInfo.getModules();
      int k = getIndexOfModule(localArrayList, "Account Summary");
      String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
      boolean bool = false;
      bool = jdMethod_int(paramCreditCardAcctSummary, str2, null, localBAITypeCodeInfo.getDataType());
      if ((!bool) && (str2 != null)) {
        paramCreditCardAcctSummary.put(str2, null);
      }
    }
  }
  
  private static void jdMethod_int(BAISummaryStatus paramBAISummaryStatus, CreditCardAcctSummary paramCreditCardAcctSummary, String paramString)
  {
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    try
    {
      localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramBAISummaryStatus._typeCode);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.FINE, "Unknown BAI type code (" + paramBAISummaryStatus._typeCode + ") is being mapped to the account summary tables instead of the extended account summary table.");
    }
    ArrayList localArrayList = localBAITypeCodeInfo.getModules();
    int i = getIndexOfModule(localArrayList, "Account Summary");
    String str = ((DCModule)localArrayList.get(i)).getColumnName();
    Object localObject = null;
    if (localBAITypeCodeInfo.getDataType() == 0) {
      localObject = paramBAISummaryStatus._data == null ? null : new Currency((BigDecimal)paramBAISummaryStatus._data, paramString, paramCreditCardAcctSummary.getLocale());
    } else if (localBAITypeCodeInfo.getDataType() == 2) {
      localObject = (DateTime)paramBAISummaryStatus._data;
    } else if (localBAITypeCodeInfo.getDataType() == 1) {
      localObject = (String)paramBAISummaryStatus._data;
    } else if (localBAITypeCodeInfo.getDataType() == 4) {
      localObject = new Float(paramBAISummaryStatus._data.toString());
    } else {
      localObject = paramBAISummaryStatus._data;
    }
    boolean bool = false;
    bool = jdMethod_int(paramCreditCardAcctSummary, str, localObject, localBAITypeCodeInfo.getDataType());
    if ((!bool) && (str != null)) {
      paramCreditCardAcctSummary.put(str, localObject);
    }
  }
  
  private static boolean jdMethod_int(LoanAcctSummary paramLoanAcctSummary, String paramString, Object paramObject, int paramInt)
  {
    Object localObject;
    if (paramInt == 0)
    {
      localObject = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("AVAILABLECREDIT")) {
        paramLoanAcctSummary.setAvailCredit((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("AMOUNTDUE")) {
        paramLoanAcctSummary.setAmtDue((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("ACCRUEDINTEREST")) {
        paramLoanAcctSummary.setAccruedInterest((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("OPENINGBALANCE")) {
        paramLoanAcctSummary.setOpeningBal((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("PRINCIPLEPASTDUE")) {
        paramLoanAcctSummary.setPrincipalPastDue((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("INTERESTPASTDUE")) {
        paramLoanAcctSummary.setInterestPastDue((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("LATEFEES")) {
        paramLoanAcctSummary.setLateFees((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("NEXTPRINCIPLEAMT")) {
        paramLoanAcctSummary.setNextPrincipalAmt((Currency)localObject);
      } else if (paramString.equalsIgnoreCase("NEXTINTERESTAMT")) {
        paramLoanAcctSummary.setLateFees((Currency)localObject);
      } else {
        return false;
      }
    }
    else if (paramInt == 2)
    {
      localObject = (DateTime)paramObject;
      if (paramString.equalsIgnoreCase("DUEDATE")) {
        paramLoanAcctSummary.setDueDate((DateTime)localObject);
      } else if (paramString.equalsIgnoreCase("VALUEDATETIME")) {
        paramLoanAcctSummary.setValueDate((DateTime)localObject);
      } else if (paramString.equalsIgnoreCase("MATURITYDATE")) {
        paramLoanAcctSummary.setMaturityDate((DateTime)localObject);
      } else {
        return false;
      }
    }
    else if (paramInt == 1)
    {
      localObject = (String)paramObject;
      if (paramString.equalsIgnoreCase("COLLATERALDESC")) {
        paramLoanAcctSummary.setCollateralDescription((String)localObject);
      } else {
        return false;
      }
    }
    else if (paramInt == 4)
    {
      float f = -1.0F;
      if (paramObject != null) {
        f = ((Float)paramObject).floatValue();
      }
      if (paramString.equalsIgnoreCase("INTERESTRATE")) {
        paramLoanAcctSummary.setInterestRate(f);
      } else {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  private static void jdMethod_int(ArrayList paramArrayList, LoanAcctSummary paramLoanAcctSummary)
  {
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log(Level.FINE, "Unknown BAI type code (" + j + ") is being mapped to the account summary tables instead of the extended account summary table.");
      }
      ArrayList localArrayList = localBAITypeCodeInfo.getModules();
      int k = getIndexOfModule(localArrayList, "Account Summary");
      String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
      int m = localBAITypeCodeInfo.getDataType();
      boolean bool = false;
      bool = jdMethod_int(paramLoanAcctSummary, str2, null, m);
      if ((!bool) && (str2 != null)) {
        paramLoanAcctSummary.put(str2, null);
      }
    }
  }
  
  private static void jdMethod_int(BAISummaryStatus paramBAISummaryStatus, LoanAcctSummary paramLoanAcctSummary, String paramString)
  {
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    try
    {
      localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramBAISummaryStatus._typeCode);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.FINE, "Unknown BAI type code (" + paramBAISummaryStatus._typeCode + ") is being mapped to the account summary tables instead of the extended account summary table.");
    }
    ArrayList localArrayList = localBAITypeCodeInfo.getModules();
    int i = getIndexOfModule(localArrayList, "Account Summary");
    String str = ((DCModule)localArrayList.get(i)).getColumnName();
    Object localObject = null;
    if (localBAITypeCodeInfo.getDataType() == 0) {
      localObject = paramBAISummaryStatus._data == null ? null : new Currency((BigDecimal)paramBAISummaryStatus._data, paramString, paramLoanAcctSummary.getLocale());
    } else if (localBAITypeCodeInfo.getDataType() == 2) {
      localObject = (DateTime)paramBAISummaryStatus._data;
    } else if (localBAITypeCodeInfo.getDataType() == 1) {
      localObject = (String)paramBAISummaryStatus._data;
    } else if (localBAITypeCodeInfo.getDataType() == 4) {
      localObject = new Float(((BigDecimal)paramBAISummaryStatus._data).floatValue());
    } else {
      localObject = paramBAISummaryStatus._data;
    }
    boolean bool = false;
    bool = jdMethod_int(paramLoanAcctSummary, str, localObject, localBAITypeCodeInfo.getDataType());
    if ((!bool) && (str != null)) {
      paramLoanAcctSummary.put(str, localObject);
    }
  }
  
  public static boolean removeTypeCodesFromAccountSummary(ArrayList paramArrayList, AccountSummary paramAccountSummary, int paramInt)
  {
    boolean bool = false;
    int i = Account.getAccountSystemTypeFromGroup(paramInt);
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < paramArrayList.size(); j++)
    {
      String str = (String)paramArrayList.get(j);
      int k = -1;
      try
      {
        k = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + k + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      if (BAITypeCodes.isAccountSummaryCode(k)) {
        localArrayList.add(str);
      }
    }
    if (localArrayList.size() > 0) {
      bool = true;
    }
    if (i == 1) {
      removeTypeCodesFromDepositAccountSummary(localArrayList, (DepositAcctSummary)paramAccountSummary);
    } else if (i == 2) {
      removeTypeCodesFromAssetAccountSummary(localArrayList, (AssetAcctSummary)paramAccountSummary);
    } else if (i == 4) {
      removeTypeCodesFromCreditCardAccountSummary(localArrayList, (CreditCardAcctSummary)paramAccountSummary);
    } else if (i == 3) {
      jdMethod_int(localArrayList, (LoanAcctSummary)paramAccountSummary);
    } else {
      removeTypeCodesFromDepositAccountSummary(localArrayList, (DepositAcctSummary)paramAccountSummary);
    }
    return bool;
  }
  
  public static boolean convertSummaryStatusItemsToAccountSummary(Vector paramVector, int paramInt, AccountSummary paramAccountSummary, String paramString)
  {
    boolean bool = false;
    int i = Account.getAccountSystemTypeFromGroup(paramInt);
    for (int j = 0; j < paramVector.size(); j++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(j);
      if (BAITypeCodes.isAccountSummaryCode(localBAISummaryStatus._typeCode))
      {
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(localBAISummaryStatus._typeCode);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type code (" + localBAISummaryStatus._typeCode + ") is being mapped to the account summary tables instead of the extended account summary table.");
        }
        bool = true;
        if (i == 1) {
          jdMethod_int(localBAISummaryStatus, (DepositAcctSummary)paramAccountSummary, paramString);
        } else if (i == 2) {
          jdMethod_int(localBAISummaryStatus, (AssetAcctSummary)paramAccountSummary, paramString);
        } else if (i == 4) {
          jdMethod_int(localBAISummaryStatus, (CreditCardAcctSummary)paramAccountSummary, paramString);
        } else if (i == 3) {
          jdMethod_int(localBAISummaryStatus, (LoanAcctSummary)paramAccountSummary, paramString);
        } else {
          jdMethod_int(localBAISummaryStatus, (DepositAcctSummary)paramAccountSummary, paramString);
        }
        if (localBAISummaryStatus._fundsType.equals("V"))
        {
          DateTime localDateTime = new DateTime(localBAISummaryStatus._valueDate, paramAccountSummary.getLocale());
          paramAccountSummary.setValueDate(localDateTime);
        }
      }
    }
    return bool;
  }
  
  public static ArrayList getCodesMappedToExtendedAccountSummary(ArrayList paramArrayList)
    throws DCException
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      if ((!BAITypeCodes.isAccountHistoryCode(j)) && (!BAITypeCodes.isAccountSummaryCode(j)) && (!BAITypeCodes.isLockboxSummaryCode(j)) && (!BAITypeCodes.isDspSummaryCode(j))) {
        localArrayList.add(new Integer(j));
      }
    }
    return localArrayList;
  }
  
  public static boolean convertSummaryStatusItemsToExtendedAccountSummaries(Vector paramVector, ExtendedAccountSummaries paramExtendedAccountSummaries, DateTime paramDateTime)
  {
    boolean bool = false;
    for (int i = 0; i < paramVector.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(i);
      if ((!BAITypeCodes.isAccountHistoryCode(localBAISummaryStatus._typeCode)) && (!BAITypeCodes.isAccountSummaryCode(localBAISummaryStatus._typeCode)) && (!BAITypeCodes.isLockboxSummaryCode(localBAISummaryStatus._typeCode)) && (!BAITypeCodes.isDspSummaryCode(localBAISummaryStatus._typeCode)))
      {
        ExtendedAccountSummary localExtendedAccountSummary = paramExtendedAccountSummaries.create();
        localExtendedAccountSummary.setSummaryDate(paramDateTime);
        bool = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(localBAISummaryStatus._typeCode);
        }
        catch (CSILException localCSILException)
        {
          localBAITypeCodeInfo = genericObjectForUnknownBAITypeCodes(localBAISummaryStatus._typeCode, "TypeCode " + localBAISummaryStatus._typeCode + " not found from " + "baiTypeCodes.xml", 1, 0, -1);
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int j = getIndexOfModule(localArrayList, "Extended Account Summary");
        String str = j >= 0 ? ((DCModule)localArrayList.get(j)).getColumnName() : "AMOUNT";
        Currency localCurrency = null;
        if (localBAITypeCodeInfo.getDataType() == 0) {
          localCurrency = localBAISummaryStatus._data == null ? null : new Currency((BigDecimal)localBAISummaryStatus._data, paramExtendedAccountSummaries.getLocale());
        } else if (str != null) {
          localExtendedAccountSummary.put(str, localBAISummaryStatus._data);
        }
        localExtendedAccountSummary.setSummaryType(localBAISummaryStatus._typeCode);
        if (localBAISummaryStatus._itemCount != -1) {
          localExtendedAccountSummary.setItemsCount(localBAISummaryStatus._itemCount);
        }
        Object localObject;
        if ((localBAISummaryStatus._fundsType.equals("Z")) || (localBAISummaryStatus._fundsType.equals("V")))
        {
          if ("AMOUNT".equalsIgnoreCase(str)) {
            localExtendedAccountSummary.setAmt(localCurrency);
          } else if (str != null) {
            localExtendedAccountSummary.put(str, localBAISummaryStatus._data);
          }
        }
        else if (localBAISummaryStatus._fundsType.equals("0"))
        {
          localExtendedAccountSummary.setImmedAvailAmt(localCurrency);
        }
        else if (localBAISummaryStatus._fundsType.equals("1"))
        {
          localExtendedAccountSummary.setOneDayAvailAmt(localCurrency);
        }
        else if (localBAISummaryStatus._fundsType.equals("2"))
        {
          localExtendedAccountSummary.setMoreThanOneDayAvailAmt(localCurrency);
        }
        else if (localBAISummaryStatus._fundsType.equals("S"))
        {
          localObject = localBAISummaryStatus._immediateAvailabilityAmount == null ? null : new Currency(localBAISummaryStatus._immediateAvailabilityAmount, paramExtendedAccountSummaries.getLocale());
          localExtendedAccountSummary.setImmedAvailAmt((Currency)localObject);
          localObject = localBAISummaryStatus._oneDayAvailabilityAmount == null ? null : new Currency(localBAISummaryStatus._oneDayAvailabilityAmount, paramExtendedAccountSummaries.getLocale());
          localExtendedAccountSummary.setOneDayAvailAmt((Currency)localObject);
          localObject = localBAISummaryStatus._moreThanOneDayAvailabilityAmount == null ? null : new Currency(localBAISummaryStatus._moreThanOneDayAvailabilityAmount, paramExtendedAccountSummaries.getLocale());
          localExtendedAccountSummary.setMoreThanOneDayAvailAmt((Currency)localObject);
        }
        else if (localBAISummaryStatus._fundsType.equals("D"))
        {
          localObject = localBAISummaryStatus._distributions;
          BAIAvailabilityDistributions localBAIAvailabilityDistributions = null;
          if (localObject != null)
          {
            BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
            BigDecimal localBigDecimal2 = new BigDecimal(0.0D);
            BigDecimal localBigDecimal3 = new BigDecimal(0.0D);
            for (int k = 0; k < ((Vector)localObject).size(); k++)
            {
              localBAIAvailabilityDistributions = (BAIAvailabilityDistributions)((Vector)localObject).get(k);
              switch (localBAIAvailabilityDistributions._availabilityInDays)
              {
              case 0: 
                localBigDecimal1 = localBigDecimal1.add(localBAIAvailabilityDistributions._availableAmount);
                break;
              case 1: 
                localBigDecimal2 = localBigDecimal2.add(localBAIAvailabilityDistributions._availableAmount);
                break;
              default: 
                localBigDecimal3 = localBigDecimal3.add(localBAIAvailabilityDistributions._availableAmount);
              }
            }
            localExtendedAccountSummary.setImmedAvailAmt(new Currency(localBigDecimal1, paramExtendedAccountSummaries.getLocale()));
            localExtendedAccountSummary.setOneDayAvailAmt(new Currency(localBigDecimal2, paramExtendedAccountSummaries.getLocale()));
            localExtendedAccountSummary.setMoreThanOneDayAvailAmt(new Currency(localBigDecimal3, paramExtendedAccountSummaries.getLocale()));
          }
        }
        if (localBAISummaryStatus._fundsType.equals("V"))
        {
          localObject = new DateTime(localBAISummaryStatus._valueDate, paramExtendedAccountSummaries.getLocale());
          localExtendedAccountSummary.setValueDateTime((DateTime)localObject);
        }
      }
    }
    return bool;
  }
  
  private static boolean jdMethod_int(LockboxSummary paramLockboxSummary, String paramString, Object paramObject, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      Currency localCurrency = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("TOTALCREDITS"))
      {
        paramLockboxSummary.setTotalLockboxCredits(localCurrency);
        paramLockboxSummary.setTotalNumLockboxCredits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALDEBITS"))
      {
        paramLockboxSummary.setTotalLockboxDebits(localCurrency);
        paramLockboxSummary.setTotalNumLockboxDebits(paramInt1);
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static boolean removeTypeCodesFromLockboxSummary(ArrayList paramArrayList, LockboxSummary paramLockboxSummary)
  {
    boolean bool1 = false;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      if (BAITypeCodes.isLockboxSummaryCode(j))
      {
        bool1 = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type code (" + j + ") is being mapped to the account summary tables instead of the extended account summary table.");
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int k = getIndexOfModule(localArrayList, "Lockbox Summary");
        String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
        boolean bool2 = jdMethod_int(paramLockboxSummary, str2, null, -1, localBAITypeCodeInfo.getDataType());
        if ((!bool2) && (str2 != null)) {
          paramLockboxSummary.put(str2, null);
        }
      }
    }
    return bool1;
  }
  
  public static boolean convertSummaryStatusItemsToLockboxSummary(Vector paramVector, LockboxSummary paramLockboxSummary)
  {
    boolean bool1 = false;
    for (int i = 0; i < paramVector.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(i);
      if (BAITypeCodes.isLockboxSummaryCode(localBAISummaryStatus._typeCode))
      {
        bool1 = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(localBAISummaryStatus._typeCode);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type code (" + localBAISummaryStatus._typeCode + ") is being mapped to the lockbox summary tables instead of the extended account summary table.");
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int j = getIndexOfModule(localArrayList, "Lockbox Summary");
        String str = ((DCModule)localArrayList.get(j)).getColumnName();
        Currency localCurrency = null;
        if (localBAITypeCodeInfo.getDataType() == 0)
        {
          localCurrency = localBAISummaryStatus._data == null ? null : new Currency((BigDecimal)localBAISummaryStatus._data, paramLockboxSummary.getLocale());
          boolean bool2 = jdMethod_int(paramLockboxSummary, str, localCurrency, localBAISummaryStatus._itemCount, localBAITypeCodeInfo.getDataType());
          if ((!bool2) && (str != null)) {
            paramLockboxSummary.put(str, localBAISummaryStatus._data);
          }
        }
        else if (str != null)
        {
          paramLockboxSummary.put(str, localBAISummaryStatus._data);
        }
      }
    }
    return bool1;
  }
  
  private static boolean jdMethod_int(DisbursementSummary paramDisbursementSummary, String paramString, Object paramObject, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      Currency localCurrency = (Currency)paramObject;
      if (paramString.equalsIgnoreCase("TOTALDEBITS"))
      {
        paramDisbursementSummary.setTotalDebits(localCurrency);
        paramDisbursementSummary.setNumItemsPending(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALCREDITS"))
      {
        paramDisbursementSummary.setTotalCredits(localCurrency);
        paramDisbursementSummary.setNumCredits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("TOTALDTCCREDITS"))
      {
        paramDisbursementSummary.setTotalDTCCredits(localCurrency);
        paramDisbursementSummary.setNumDTCCredits(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("IMMEDFUNDSNEEDED"))
      {
        paramDisbursementSummary.setImmediateFundsNeeded(localCurrency);
        paramDisbursementSummary.setNumImmediateFundsNeeded(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("CHECKSPAIDEARLY"))
      {
        paramDisbursementSummary.setTotalChecksPaidEarlyAmount(localCurrency);
        paramDisbursementSummary.setNumChecksPaidEarly(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("CHECKSPAIDLATE"))
      {
        paramDisbursementSummary.setTotalChecksPaidLateAmount(localCurrency);
        paramDisbursementSummary.setNumChecksPaidLate(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("CHECKSPAIDLAST"))
      {
        paramDisbursementSummary.setTotalChecksPaidLastAmount(localCurrency);
        paramDisbursementSummary.setNumChecksPaidLast(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("FEDESTIMATE"))
      {
        paramDisbursementSummary.setFedPresentmentEstimate(localCurrency);
        paramDisbursementSummary.setNumFedPresentmentEstimate(paramInt1);
      }
      else if (paramString.equalsIgnoreCase("LATEDEBITS"))
      {
        paramDisbursementSummary.setLateDebits(localCurrency);
        paramDisbursementSummary.setNumLateDebits(paramInt1);
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public static boolean removeTypeCodesFromDisbursementSummary(ArrayList paramArrayList, DisbursementSummary paramDisbursementSummary)
  {
    boolean bool1 = false;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      String str1 = (String)paramArrayList.get(i);
      int j = -1;
      try
      {
        j = Integer.parseInt(str1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + j + ").  BAI Type codes must be integers.  " + "The calculated value will not be stored.");
      }
      if (BAITypeCodes.isDspSummaryCode(j))
      {
        bool1 = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(j);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type code (" + j + ") is being mapped to the account summary tables instead of the extended account summary table.");
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int k = getIndexOfModule(localArrayList, "Disbursement Summary");
        String str2 = ((DCModule)localArrayList.get(k)).getColumnName();
        boolean bool2 = jdMethod_int(paramDisbursementSummary, str2, null, -1, localBAITypeCodeInfo.getDataType());
        if ((!bool2) && (str2 != null)) {
          paramDisbursementSummary.put(str2, null);
        }
      }
    }
    return bool1;
  }
  
  public static boolean convertSummaryStatusItemsToDisbursementSummary(Vector paramVector, DisbursementSummary paramDisbursementSummary)
  {
    boolean bool1 = false;
    for (int i = 0; i < paramVector.size(); i++)
    {
      BAISummaryStatus localBAISummaryStatus = (BAISummaryStatus)paramVector.get(i);
      if (BAITypeCodes.isDspSummaryCode(localBAISummaryStatus._typeCode))
      {
        bool1 = true;
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(localBAISummaryStatus._typeCode);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log(Level.FINE, "Unknown BAI type code (" + localBAISummaryStatus._typeCode + ") is being mapped to the lockbox summary tables instead of the extended account summary table.");
        }
        ArrayList localArrayList = localBAITypeCodeInfo.getModules();
        int j = getIndexOfModule(localArrayList, "Disbursement Summary");
        String str = ((DCModule)localArrayList.get(j)).getColumnName();
        Currency localCurrency = null;
        if (localBAITypeCodeInfo.getDataType() == 0)
        {
          localCurrency = localBAISummaryStatus._data == null ? null : new Currency((BigDecimal)localBAISummaryStatus._data, paramDisbursementSummary.getLocale());
        }
        else
        {
          if (str == null) {
            continue;
          }
          paramDisbursementSummary.put(str, localBAISummaryStatus._data);
          continue;
        }
        Object localObject;
        if ((localBAISummaryStatus._fundsType.equals("Z")) || (localBAISummaryStatus._fundsType.equals("V")))
        {
          if (localBAITypeCodeInfo.getDataType() == 0)
          {
            boolean bool2 = jdMethod_int(paramDisbursementSummary, str, localCurrency, localBAISummaryStatus._itemCount, localBAITypeCodeInfo.getDataType());
            if ((!bool2) && (str != null)) {
              paramDisbursementSummary.put(str, localCurrency);
            }
          }
        }
        else if (localBAISummaryStatus._fundsType.equals("0"))
        {
          paramDisbursementSummary.setImmediateFundsNeeded(localCurrency);
        }
        else if (localBAISummaryStatus._fundsType.equals("1"))
        {
          paramDisbursementSummary.setOneDayFundsNeeded(localCurrency);
        }
        else if (localBAISummaryStatus._fundsType.equals("2"))
        {
          paramDisbursementSummary.setTwoDayFundsNeeded(localCurrency);
        }
        else if (localBAISummaryStatus._fundsType.equals("S"))
        {
          localObject = localBAISummaryStatus._immediateAvailabilityAmount == null ? null : new Currency(localBAISummaryStatus._immediateAvailabilityAmount, paramDisbursementSummary.getLocale());
          paramDisbursementSummary.setImmediateFundsNeeded((Currency)localObject);
          localObject = localBAISummaryStatus._oneDayAvailabilityAmount == null ? null : new Currency(localBAISummaryStatus._oneDayAvailabilityAmount, paramDisbursementSummary.getLocale());
          paramDisbursementSummary.setOneDayFundsNeeded((Currency)localObject);
          localObject = localBAISummaryStatus._moreThanOneDayAvailabilityAmount == null ? null : new Currency(localBAISummaryStatus._moreThanOneDayAvailabilityAmount, paramDisbursementSummary.getLocale());
          paramDisbursementSummary.setTwoDayFundsNeeded((Currency)localObject);
        }
        else if (localBAISummaryStatus._fundsType.equals("D"))
        {
          localObject = localBAISummaryStatus._distributions;
          BAIAvailabilityDistributions localBAIAvailabilityDistributions = null;
          if (localObject != null)
          {
            BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
            BigDecimal localBigDecimal2 = new BigDecimal(0.0D);
            BigDecimal localBigDecimal3 = new BigDecimal(0.0D);
            for (int k = 0; k < ((Vector)localObject).size(); k++)
            {
              localBAIAvailabilityDistributions = (BAIAvailabilityDistributions)((Vector)localObject).get(k);
              switch (localBAIAvailabilityDistributions._availabilityInDays)
              {
              case 0: 
                localBigDecimal1 = localBigDecimal1.add(localBAIAvailabilityDistributions._availableAmount);
                break;
              case 1: 
                localBigDecimal2 = localBigDecimal2.add(localBAIAvailabilityDistributions._availableAmount);
                break;
              default: 
                localBigDecimal3 = localBigDecimal3.add(localBAIAvailabilityDistributions._availableAmount);
              }
            }
            paramDisbursementSummary.setImmediateFundsNeeded(new Currency(localBigDecimal1, paramDisbursementSummary.getLocale()));
            paramDisbursementSummary.setOneDayFundsNeeded(new Currency(localBigDecimal2, paramDisbursementSummary.getLocale()));
            paramDisbursementSummary.setTwoDayFundsNeeded(new Currency(localBigDecimal3, paramDisbursementSummary.getLocale()));
          }
        }
        if (localBAISummaryStatus._fundsType.equals("V"))
        {
          localObject = new DateTime(localBAISummaryStatus._valueDate, paramDisbursementSummary.getLocale());
          paramDisbursementSummary.setValueDateTime((DateTime)localObject);
        }
      }
    }
    return bool1;
  }
  
  public static BAITypeCodeInfo genericObjectForUnknownBAITypeCodes(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = 0;
    int j = 0;
    if (paramInt2 == 2) {
      if (paramInt1 >= 400)
      {
        i = 2;
        j = 5;
      }
      else
      {
        i = 1;
        j = 4;
      }
    }
    ArrayList localArrayList = new ArrayList();
    if (i == 0) {
      localArrayList.add(new DCModule("Extended Account Summary", "Amount"));
    } else {
      localArrayList.add(new DCModule("Account Transactions", ""));
    }
    return new BAITypeCodeInfo(paramInt1, paramString, i, paramInt2, paramInt3, paramInt4, j, localArrayList);
  }
  
  public static int getIndexOfModule(ArrayList paramArrayList, String paramString)
  {
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (((DCModule)paramArrayList.get(i)).getModuleName().equals(paramString)) {
        return i;
      }
    }
    return -1;
  }
  
  public static Vector getIRCalculationsSummaryStatusItems(Transactions paramTransactions, LockboxTransactions paramLockboxTransactions, AccountHistory paramAccountHistory, ArrayList paramArrayList1, String paramString, ArrayList paramArrayList2, HashMap paramHashMap)
  {
    Locale localLocale = null;
    if (paramTransactions != null) {
      localLocale = paramTransactions.getLocale();
    } else if (paramLockboxTransactions != null) {
      localLocale = paramLockboxTransactions.getLocale();
    } else if (paramAccountHistory != null) {
      localLocale = paramAccountHistory.getLocale();
    } else {
      localLocale = Locale.getDefault();
    }
    Vector localVector = new Vector();
    if (paramArrayList2 == null) {
      paramArrayList2 = new ArrayList();
    }
    Currency localCurrency1 = new Currency("0", localLocale);
    int i = 0;
    Currency localCurrency2 = new Currency("0", localLocale);
    int j = 0;
    Currency localCurrency3 = new Currency("0", localLocale);
    int k = 0;
    Currency localCurrency4 = new Currency("0", localLocale);
    int m = 0;
    Currency localCurrency5 = new Currency("0", localLocale);
    int n = 0;
    Currency localCurrency6 = new Currency("0", localLocale);
    int i1 = 0;
    Currency localCurrency7 = new Currency("0", localLocale);
    int i2 = 0;
    Currency localCurrency8 = new Currency("0", localLocale);
    int i3 = 0;
    Currency localCurrency9 = new Currency("0", localLocale);
    int i4 = 0;
    Currency localCurrency10 = new Currency("0", localLocale);
    int i5 = 0;
    Currency localCurrency11 = new Currency("0", localLocale);
    int i6 = 0;
    Currency localCurrency12 = new Currency("0", localLocale);
    Currency localCurrency13 = new Currency("0", localLocale);
    Currency localCurrency14 = new Currency("0", localLocale);
    int i7;
    Object localObject;
    if ((paramTransactions != null) && (paramTransactions.size() > 0)) {
      for (i7 = 0; i7 < paramTransactions.size(); i7++)
      {
        localObject = (Transaction)paramTransactions.get(i7);
        if (((Transaction)localObject).getAmountValue().getAmountValue().doubleValue() > 0.0D)
        {
          i++;
          jdMethod_int(localCurrency1, ((Transaction)localObject).getAmountValue());
        }
        else if (((Transaction)localObject).getAmountValue().getAmountValue().doubleValue() < 0.0D)
        {
          j++;
          jdMethod_int(localCurrency2, ((Transaction)localObject).getAmountValue().getAmountValue().negate());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.ACH_CREDIT_TRANS_TYPE_CODES))
        {
          k++;
          jdMethod_int(localCurrency3, ((Transaction)localObject).getAmountValue());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.ACH_DEBIT_TRANS_TYPE_CODES))
        {
          m++;
          jdMethod_int(localCurrency4, ((Transaction)localObject).getAmountValue().getAmountValue().negate());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.INCOMING_MONEY_TRANSFER_TRANS_TYPE_CODES))
        {
          n++;
          jdMethod_int(localCurrency5, ((Transaction)localObject).getAmountValue());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.OUTGOING_MONEY_TRANSFER_TRANS_TYPE_CODES))
        {
          i1++;
          jdMethod_int(localCurrency6, ((Transaction)localObject).getAmountValue().getAmountValue().negate());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.LOCKBOX_DEPOSIT_TRANS_TYPE_CODES))
        {
          i2++;
          jdMethod_int(localCurrency7, ((Transaction)localObject).getAmountValue());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.OTHER_CHECK_DEPOSIT_TRANS_TYPE_CODES))
        {
          i3++;
          jdMethod_int(localCurrency8, ((Transaction)localObject).getAmountValue());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.ZBA_CREDITS_TRANS_TYPE_CODES))
        {
          i4++;
          jdMethod_int(localCurrency9, ((Transaction)localObject).getAmountValue());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.ZBA_DEBITS_TRANS_TYPE_CODES))
        {
          i5++;
          jdMethod_int(localCurrency10, ((Transaction)localObject).getAmountValue().getAmountValue().negate());
        }
        if (isIntInList(((Transaction)localObject).getSubTypeValue(), BAIParserConsts.CHECKS_PAID_TRANS_TYPE_CODES))
        {
          i6++;
          jdMethod_int(localCurrency11, ((Transaction)localObject).getAmountValue().getAmountValue().negate());
        }
        jdMethod_int(localCurrency12, ((Transaction)localObject).getImmediateAvailAmount());
        jdMethod_int(localCurrency13, ((Transaction)localObject).getOneDayAvailAmount());
        jdMethod_int(localCurrency14, ((Transaction)localObject).getMoreThanOneDayAvailAmount());
      }
    }
    if ((paramLockboxTransactions != null) && (paramLockboxTransactions.size() > 0)) {
      for (i7 = 0; i7 < paramLockboxTransactions.size(); i7++)
      {
        localObject = (LockboxTransaction)paramLockboxTransactions.get(i7);
        if (((LockboxTransaction)localObject).getAmount().getAmountValue().doubleValue() > 0.0D)
        {
          i++;
          jdMethod_int(localCurrency1, ((LockboxTransaction)localObject).getAmount());
        }
        else if (((LockboxTransaction)localObject).getAmount().getAmountValue().doubleValue() < 0.0D)
        {
          j++;
          jdMethod_int(localCurrency2, ((LockboxTransaction)localObject).getAmount().getAmountValue().negate());
        }
        if (isIntInList(((LockboxTransaction)localObject).getTransactionType(), BAIParserConsts.LOCKBOX_DEPOSIT_TRANS_TYPE_CODES))
        {
          i2++;
          jdMethod_int(localCurrency7, ((LockboxTransaction)localObject).getAmount());
        }
        jdMethod_int(localCurrency12, ((LockboxTransaction)localObject).getImmediateFloat());
        jdMethod_int(localCurrency13, ((LockboxTransaction)localObject).getOneDayFloat());
        jdMethod_int(localCurrency14, ((LockboxTransaction)localObject).getTwoDayFloat());
      }
    }
    jdMethod_int(localVector, paramArrayList1, "100", localCurrency1.getAmountValue(), i, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "400", localCurrency2.getAmountValue(), j, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "140", localCurrency3.getAmountValue(), k, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "450", localCurrency4.getAmountValue(), m, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "190", localCurrency5.getAmountValue(), n, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "490", localCurrency6.getAmountValue(), i1, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "110", localCurrency7.getAmountValue(), i2, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "170", localCurrency8.getAmountValue(), i3, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "270", localCurrency9.getAmountValue(), i4, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "570", localCurrency10.getAmountValue(), i5, paramString, paramArrayList2);
    jdMethod_int(localVector, paramArrayList1, "470", localCurrency11.getAmountValue(), i6, paramString, paramArrayList2);
    if ("I".equals(paramString))
    {
      Currency localCurrency15 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getOneDayFloat() != null))
      {
        localCurrency15 = new Currency(paramAccountHistory.getOneDayFloat().getAmountValue(), paramAccountHistory.getOneDayFloat().getCurrencyCode(), paramAccountHistory.getOneDayFloat().getLocale());
        localCurrency15.addAmount(localCurrency12);
        jdMethod_int(localVector, paramArrayList1, "070", localCurrency15.getAmountValue(), paramString);
        jdMethod_int(localVector, paramArrayList1, "1002", localCurrency15.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("070"));
          paramArrayList2.add("070");
        }
        catch (CSILException localCSILException1) {}
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("1002"));
          paramArrayList2.add("1002");
        }
        catch (CSILException localCSILException2) {}
      }
      if (paramAccountHistory != null)
      {
        jdMethod_int(localVector, paramArrayList1, "072", localCurrency13.getAmountValue(), paramString);
        jdMethod_int(localVector, paramArrayList1, "1003", localCurrency13.getAmountValue(), paramString);
        jdMethod_int(localVector, paramArrayList1, "074", localCurrency14.getAmountValue(), paramString);
        jdMethod_int(localVector, paramArrayList1, "1004", localCurrency14.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("072"));
          paramArrayList2.add("072");
        }
        catch (CSILException localCSILException3) {}
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("1003"));
          paramArrayList2.add("1003");
        }
        catch (CSILException localCSILException4) {}
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("074"));
          paramArrayList2.add("074");
        }
        catch (CSILException localCSILException5) {}
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("1004"));
          paramArrayList2.add("1004");
        }
        catch (CSILException localCSILException6) {}
      }
      Currency localCurrency16 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localCurrency16 = new Currency(paramAccountHistory.getClosingLedger().getAmountValue(), paramAccountHistory.getClosingLedger().getCurrencyCode(), paramAccountHistory.getClosingLedger().getLocale());
        jdMethod_int(localVector, paramArrayList1, "010", localCurrency16.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("010"));
          paramArrayList2.add("010");
        }
        catch (CSILException localCSILException7) {}
      }
      Currency localCurrency17 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingAvail() != null) && (paramAccountHistory.getOneDayFloat() != null))
      {
        localCurrency17 = new Currency(paramAccountHistory.getClosingAvail().getAmountValue(), paramAccountHistory.getClosingAvail().getCurrencyCode(), paramAccountHistory.getClosingAvail().getLocale());
        localCurrency17.addAmount(paramAccountHistory.getOneDayFloat().getAmountValue());
        jdMethod_int(localVector, paramArrayList1, "040", localCurrency17.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("040"));
          paramArrayList2.add("040");
        }
        catch (CSILException localCSILException8) {}
      }
      Currency localCurrency18 = null;
      if (localCurrency16 != null)
      {
        localCurrency18 = new Currency(localCurrency16.getAmountValue(), localCurrency16.getCurrencyCode(), localCurrency16.getLocale());
        localCurrency18.addAmount(localCurrency1);
        localCurrency18.addAmount(localCurrency2.getAmountValue().negate());
        jdMethod_int(localVector, paramArrayList1, "030", localCurrency18.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("030"));
          paramArrayList2.add("030");
        }
        catch (CSILException localCSILException9) {}
      }
      Currency localCurrency19 = null;
      if (localCurrency17 != null)
      {
        localCurrency19 = new Currency(localCurrency17.getAmountValue(), localCurrency17.getCurrencyCode(), localCurrency17.getLocale());
        localCurrency19.addAmount(localCurrency1);
        localCurrency19.addAmount(localCurrency2.getAmountValue().negate());
        localCurrency19.addAmount(localCurrency13.getAmountValue().negate());
        localCurrency19.addAmount(localCurrency14.getAmountValue().negate());
        jdMethod_int(localVector, paramArrayList1, "060", localCurrency19.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("060"));
          paramArrayList2.add("060");
        }
        catch (CSILException localCSILException10) {}
      }
      Currency localCurrency20 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingAvail() != null))
      {
        localCurrency20 = new Currency(paramAccountHistory.getClosingAvail().getAmountValue(), paramAccountHistory.getClosingAvail().getCurrencyCode(), paramAccountHistory.getClosingAvail().getLocale());
        localCurrency20.addAmount(localCurrency12);
        jdMethod_int(localVector, paramArrayList1, "1000", localCurrency20.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("1000"));
          paramArrayList2.add("1000");
        }
        catch (CSILException localCSILException11) {}
      }
      Currency localCurrency21 = null;
      if ((paramAccountHistory != null) && (paramAccountHistory.getClosingLedger() != null))
      {
        localCurrency21 = new Currency(paramAccountHistory.getClosingLedger().getAmountValue(), paramAccountHistory.getClosingLedger().getCurrencyCode(), paramAccountHistory.getClosingLedger().getLocale());
        localCurrency21.addAmount(localCurrency12);
        jdMethod_int(localVector, paramArrayList1, "1001", localCurrency21.getAmountValue(), paramString);
      }
      else
      {
        try
        {
          DataConsolidator.getBAITypeCodeInfo(Integer.parseInt("1001"));
          paramArrayList2.add("1001");
        }
        catch (CSILException localCSILException12) {}
      }
    }
    return localVector;
  }
  
  private static void jdMethod_int(Currency paramCurrency1, Currency paramCurrency2)
  {
    if (paramCurrency2 != null) {
      paramCurrency1.addAmount(paramCurrency2);
    }
  }
  
  private static void jdMethod_int(Currency paramCurrency, BigDecimal paramBigDecimal)
  {
    if (paramBigDecimal != null) {
      paramCurrency.addAmount(paramBigDecimal);
    }
  }
  
  private static void jdMethod_int(Vector paramVector, ArrayList paramArrayList, String paramString1, BigDecimal paramBigDecimal, String paramString2)
  {
    String str = jdMethod_int(paramString1, paramString2, true);
    boolean bool = paramArrayList.contains(str);
    if (bool) {
      return;
    }
    int i = 0;
    try
    {
      DataConsolidator.getBAITypeCodeInfo(Integer.parseInt(paramString1));
      i = 1;
    }
    catch (CSILException localCSILException) {}
    if (i == 0) {
      return;
    }
    try
    {
      BAISummaryStatus localBAISummaryStatus = new BAISummaryStatus();
      paramVector.add(localBAISummaryStatus);
      localBAISummaryStatus._typeCode = Integer.parseInt(paramString1);
      localBAISummaryStatus._fundsType = "Z";
      localBAISummaryStatus._data = paramBigDecimal;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + paramString1 + ").  BAI Type codes must be integers.  The calculated value will not be stored.");
    }
  }
  
  private static void jdMethod_int(Vector paramVector, ArrayList paramArrayList1, String paramString1, BigDecimal paramBigDecimal, int paramInt, String paramString2, ArrayList paramArrayList2)
  {
    String str1 = jdMethod_int(paramString1, paramString2, true);
    String str2 = jdMethod_int(paramString1, paramString2, false);
    boolean bool1 = paramArrayList1.contains(str1);
    boolean bool2 = paramArrayList1.contains(str2);
    if ((bool1) && (bool2)) {
      return;
    }
    if (paramInt <= 0)
    {
      try
      {
        DataConsolidator.getBAITypeCodeInfo(Integer.parseInt(paramString1));
        paramArrayList2.add(paramString1);
      }
      catch (CSILException localCSILException1) {}
      return;
    }
    int i = 0;
    try
    {
      DataConsolidator.getBAITypeCodeInfo(Integer.parseInt(paramString1));
      i = 1;
    }
    catch (CSILException localCSILException2) {}
    if (i == 0) {
      return;
    }
    try
    {
      BAISummaryStatus localBAISummaryStatus = new BAISummaryStatus();
      localBAISummaryStatus._typeCode = Integer.parseInt(paramString1);
      localBAISummaryStatus._fundsType = "Z";
      if (!bool1) {
        localBAISummaryStatus._data = paramBigDecimal;
      }
      if (!bool2) {
        localBAISummaryStatus._itemCount = paramInt;
      }
      paramVector.add(localBAISummaryStatus);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DebugLog.log(Level.FINE, "A non-numeric bai type code to store an IR calculation value was specified ( " + paramString1 + ").  BAI Type codes must be integers.  The calculated value will not be stored.");
    }
  }
  
  private static String jdMethod_int(String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return "";
    }
    if ((paramString1.equals("100")) || (paramString1.equals("400"))) {
      paramString1 = "100/400";
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    if ("I".equals(paramString2)) {
      localStringBuffer.append("-I-");
    } else {
      localStringBuffer.append("-P-");
    }
    localStringBuffer.append(paramBoolean ? "A" : "C");
    return localStringBuffer.toString();
  }
  
  public static boolean isIntInList(int paramInt, int[] paramArrayOfInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      if (paramInt == paramArrayOfInt[i]) {
        return true;
      }
    }
    return false;
  }
  
  public static ArrayList mergeBankAndAccountRestrictions(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramArrayList1 != null) {
      localArrayList.addAll(paramArrayList1);
    }
    if (paramArrayList2 != null) {
      for (int i = 0; i < paramArrayList2.size(); i++)
      {
        String str = (String)paramArrayList2.get(i);
        if (!localArrayList.contains(str)) {
          localArrayList.add(str);
        }
      }
    }
    return localArrayList;
  }
  
  public static void recalculateIRCalculations(Connection paramConnection, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator1 = localSet.iterator();
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = new ArrayList();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator1.next();
      AccountKey localAccountKey = (AccountKey)localEntry.getKey();
      Account localAccount = null;
      try
      {
        localAccount = AccountAdapter.getAccount(localAccountKey.getBankID(), localAccountKey.getRoutingNumber(), localAccountKey.getAccountID());
      }
      catch (Exception localException) {}
      continue;
      if (localAccount != null)
      {
        if (paramHashMap2.containsKey("LOCALE")) {
          localAccount.setLocale((Locale)paramHashMap2.get("LOCALE"));
        }
        int i = Account.getAccountSystemTypeFromGroup(Account.getAccountGroupFromType(localAccount.getTypeValue()));
        if (i == 1)
        {
          ArrayList localArrayList2 = jdMethod_int(localAccount.getRoutingNum(), localHashMap, localArrayList1);
          if (localArrayList2 != null)
          {
            ArrayList localArrayList3 = (ArrayList)localEntry.getValue();
            Iterator localIterator2 = localArrayList3.iterator();
            while (localIterator2.hasNext())
            {
              DateTime localDateTime1 = (DateTime)localIterator2.next();
              ArrayList localArrayList4 = null;
              try
              {
                localArrayList4 = AccountHandler.getRestrictedCalculations(new SecureUser(), localAccount, new HashMap());
              }
              catch (CSILException localCSILException)
              {
                throw new DCException(362, "Unable to retrieve the restricted calculations for the account with account id = " + localAccount.getID() + ", routing number= " + localAccount.getRoutingNum() + ", bank id = " + localAccount.getBankID() + ".", localCSILException);
              }
              ArrayList localArrayList5 = mergeBankAndAccountRestrictions(localArrayList2, localArrayList4);
              AccountHistory localAccountHistory = jdMethod_int(paramConnection, localAccount, localDateTime1, paramHashMap2);
              DateTime localDateTime2 = new DateTime(localDateTime1.getTime(), localDateTime1.getLocale());
              localDateTime2.set(11, 0);
              localDateTime2.set(12, 0);
              localDateTime2.set(13, 0);
              localDateTime2.set(14, 0);
              DateTime localDateTime3 = new DateTime(localDateTime2.getTime(), localDateTime1.getLocale());
              localDateTime3.add(6, 1);
              localDateTime3.add(14, -1);
              Transactions localTransactions = getTransactions(localAccount, localDateTime2, localDateTime3, "P", null, paramConnection);
              LockboxTransactions localLockboxTransactions = getLockboxTransactions(localAccount, localDateTime2, localDateTime3, "P", paramConnection);
              ArrayList localArrayList6 = new ArrayList();
              Vector localVector1 = getIRCalculationsSummaryStatusItems(localTransactions, localLockboxTransactions, localAccountHistory, localArrayList5, "P", localArrayList6, paramHashMap2);
              jdMethod_int(paramConnection, localAccount, localDateTime1, localVector1, "P", localArrayList6, paramHashMap2);
              localTransactions = getTransactions(localAccount, localDateTime2, localDateTime3, "I", null, paramConnection);
              localLockboxTransactions = getLockboxTransactions(localAccount, localDateTime2, localDateTime3, "I", paramConnection);
              localArrayList6 = new ArrayList();
              Vector localVector2 = getIRCalculationsSummaryStatusItems(localTransactions, localLockboxTransactions, localAccountHistory, localArrayList5, "I", localArrayList6, paramHashMap2);
              jdMethod_int(paramConnection, localAccount, localDateTime1, localVector2, "I", localArrayList6, paramHashMap2);
            }
          }
        }
      }
    }
  }
  
  private static void jdMethod_int(Connection paramConnection, Account paramAccount, DateTime paramDateTime, Vector paramVector, String paramString, ArrayList paramArrayList, HashMap paramHashMap)
    throws DCException
  {
    DateTime localDateTime1 = new DateTime(paramDateTime.getTime(), paramDateTime.getLocale());
    localDateTime1.set(11, 0);
    localDateTime1.set(12, 0);
    localDateTime1.set(13, 0);
    localDateTime1.set(14, 0);
    DateTime localDateTime2 = new DateTime(localDateTime1.getTime(), paramDateTime.getLocale());
    localDateTime2.add(6, 1);
    localDateTime2.add(14, -1);
    HashMap localHashMap = new HashMap();
    localHashMap.putAll(paramHashMap);
    localHashMap.put("DATA_CLASSIFICATION", paramString);
    AccountHistories localAccountHistories = DCAdapter.getHistory(paramAccount, localDateTime1, localDateTime2, paramConnection, localHashMap);
    boolean bool2;
    if ((localAccountHistories != null) && (localAccountHistories.size() > 0))
    {
      localObject1 = (AccountHistory)localAccountHistories.get(localAccountHistories.size() - 1);
      boolean bool1 = convertSummaryStatusItemsToAccountHistory(paramVector, (AccountHistory)localObject1);
      bool2 = removeTypeCodesFromAccountHistory(paramArrayList, (AccountHistory)localObject1);
      if ((bool1) || (bool2)) {
        DCAdapter.updateHistory(paramConnection, paramAccount, (AccountHistory)localObject1, localHashMap);
      }
      for (int i = 0; i < localAccountHistories.size() - 1; i++)
      {
        AccountHistory localAccountHistory = (AccountHistory)localAccountHistories.get(i);
        bool2 = removeTypeCodesFromAccountHistory(paramArrayList, localAccountHistory);
        if (bool2) {
          DCAdapter.updateHistory(paramConnection, paramAccount, localAccountHistory, localHashMap);
        }
      }
    }
    Object localObject1 = DCAdapter.getSummary(paramAccount, localDateTime1, localDateTime2, paramConnection, localHashMap);
    boolean bool3;
    if ((localObject1 != null) && (((AccountSummaries)localObject1).size() > 0))
    {
      localObject2 = (AccountSummary)((AccountSummaries)localObject1).get(((AccountSummaries)localObject1).size() - 1);
      bool2 = convertSummaryStatusItemsToAccountSummary(paramVector, paramAccount.getAccountGroup(), (AccountSummary)localObject2, paramAccount.getCurrencyCode());
      bool3 = removeTypeCodesFromAccountSummary(paramArrayList, (AccountSummary)localObject2, paramAccount.getAccountGroup());
      if ((bool2) || (bool3)) {
        DCAdapter.updateSummary(paramConnection, paramAccount, (AccountSummary)localObject2, localHashMap);
      }
      for (int j = 0; j < ((AccountSummaries)localObject1).size() - 1; j++)
      {
        AccountSummary localAccountSummary = (AccountSummary)((AccountSummaries)localObject1).get(j);
        bool3 = removeTypeCodesFromAccountSummary(paramArrayList, localAccountSummary, paramAccount.getAccountGroup());
        if (bool3) {
          DCAdapter.updateSummary(paramConnection, paramAccount, localAccountSummary, localHashMap);
        }
      }
    }
    Object localObject2 = DCAdapter.getLockboxSummaries(getLockboxAccount(paramAccount), localDateTime1, localDateTime2, paramConnection, localHashMap);
    boolean bool4;
    if ((localObject2 != null) && (((LockboxSummaries)localObject2).size() > 0))
    {
      localObject3 = (LockboxSummary)((LockboxSummaries)localObject2).get(((LockboxSummaries)localObject2).size() - 1);
      bool3 = convertSummaryStatusItemsToLockboxSummary(paramVector, (LockboxSummary)localObject3);
      bool4 = removeTypeCodesFromLockboxSummary(paramArrayList, (LockboxSummary)localObject3);
      if ((bool3) || (bool4)) {
        DCAdapter.updateLockboxSummary(paramConnection, (LockboxSummary)localObject3, localHashMap);
      }
      for (int k = 0; k < ((LockboxSummaries)localObject2).size() - 1; k++)
      {
        LockboxSummary localLockboxSummary = (LockboxSummary)((LockboxSummaries)localObject2).get(k);
        bool4 = removeTypeCodesFromLockboxSummary(paramArrayList, localLockboxSummary);
        if (bool4) {
          DCAdapter.updateLockboxSummary(paramConnection, localLockboxSummary, localHashMap);
        }
      }
    }
    Object localObject3 = DCAdapter.getDisbursementSummaries(getDisbursementAccount(paramAccount), localDateTime1, localDateTime2, paramConnection, localHashMap);
    Object localObject5;
    if ((localObject3 != null) && (((DisbursementSummaries)localObject3).size() > 0))
    {
      localObject4 = (DisbursementSummary)((DisbursementSummaries)localObject3).get(((DisbursementSummaries)localObject3).size() - 1);
      bool4 = convertSummaryStatusItemsToDisbursementSummary(paramVector, (DisbursementSummary)localObject4);
      boolean bool5 = removeTypeCodesFromDisbursementSummary(paramArrayList, (DisbursementSummary)localObject4);
      if ((bool4) || (bool5)) {
        DCAdapter.updateDisbursementSummary(paramConnection, (DisbursementSummary)localObject4, localHashMap);
      }
      for (m = 0; m < ((DisbursementSummaries)localObject3).size() - 1; m++)
      {
        localObject5 = (DisbursementSummary)((DisbursementSummaries)localObject3).get(m);
        bool5 = removeTypeCodesFromDisbursementSummary(paramArrayList, (DisbursementSummary)localObject5);
        if (bool5) {
          DCAdapter.updateDisbursementSummary(paramConnection, (DisbursementSummary)localObject5, localHashMap);
        }
      }
    }
    Object localObject4 = DCAdapter.getExtendedSummary(paramAccount, localDateTime1, localDateTime2, paramConnection, localHashMap);
    ExtendedAccountSummaries localExtendedAccountSummaries1 = new ExtendedAccountSummaries();
    localExtendedAccountSummaries1.setLocale(((ExtendedAccountSummaries)localObject4).getLocale());
    convertSummaryStatusItemsToExtendedAccountSummaries(paramVector, localExtendedAccountSummaries1, paramDateTime);
    ExtendedAccountSummaries localExtendedAccountSummaries2 = jdMethod_int((ExtendedAccountSummaries)localObject4, localExtendedAccountSummaries1);
    for (int m = 0; m < localExtendedAccountSummaries2.size(); m++)
    {
      localObject5 = (ExtendedAccountSummary)localExtendedAccountSummaries2.get(m);
      DCAdapter.updateExtendedSummary(paramConnection, paramAccount, (ExtendedAccountSummary)localObject5, localHashMap);
    }
    ArrayList localArrayList = getCodesMappedToExtendedAccountSummary(paramArrayList);
    if (localArrayList.size() > 0) {
      for (int n = 0; n < localArrayList.size(); n++)
      {
        Integer localInteger = (Integer)localArrayList.get(n);
        DCAdapter.deleteExtendedAccountSummary(paramConnection, paramAccount, localDateTime1, localDateTime2, localInteger.intValue(), paramString, localHashMap);
      }
    }
    ExtendedAccountSummaries localExtendedAccountSummaries3 = jdMethod_new((ExtendedAccountSummaries)localObject4, localExtendedAccountSummaries1);
    if (localExtendedAccountSummaries3.size() > 0) {
      for (int i1 = 0; i1 < localExtendedAccountSummaries3.size(); i1++)
      {
        ExtendedAccountSummary localExtendedAccountSummary = (ExtendedAccountSummary)localExtendedAccountSummaries3.get(i1);
        DCAdapter.addExtendedSummary(paramAccount, localExtendedAccountSummary, 2, paramConnection, localHashMap);
      }
    }
  }
  
  private static ExtendedAccountSummaries jdMethod_new(ExtendedAccountSummaries paramExtendedAccountSummaries1, ExtendedAccountSummaries paramExtendedAccountSummaries2)
  {
    ExtendedAccountSummaries localExtendedAccountSummaries1 = new ExtendedAccountSummaries();
    ExtendedAccountSummaries localExtendedAccountSummaries2 = combineExtendedAccountSummariesByDate(paramExtendedAccountSummaries1);
    for (int i = 0; i < paramExtendedAccountSummaries2.size(); i++)
    {
      ExtendedAccountSummary localExtendedAccountSummary1 = (ExtendedAccountSummary)paramExtendedAccountSummaries2.get(i);
      int j = 0;
      for (int k = 0; k < paramExtendedAccountSummaries1.size(); k++)
      {
        ExtendedAccountSummary localExtendedAccountSummary2 = (ExtendedAccountSummary)paramExtendedAccountSummaries1.get(k);
        if (localExtendedAccountSummary1.getSummaryType() == localExtendedAccountSummary2.getSummaryType())
        {
          j = 1;
          break;
        }
      }
      if (j == 0) {
        localExtendedAccountSummaries1.add(localExtendedAccountSummary1);
      }
    }
    return localExtendedAccountSummaries1;
  }
  
  private static ExtendedAccountSummaries jdMethod_int(ExtendedAccountSummaries paramExtendedAccountSummaries1, ExtendedAccountSummaries paramExtendedAccountSummaries2)
  {
    ExtendedAccountSummaries localExtendedAccountSummaries1 = new ExtendedAccountSummaries();
    ExtendedAccountSummaries localExtendedAccountSummaries2 = combineExtendedAccountSummariesByDate(paramExtendedAccountSummaries1);
    for (int i = 0; i < paramExtendedAccountSummaries2.size(); i++)
    {
      ExtendedAccountSummary localExtendedAccountSummary1 = (ExtendedAccountSummary)paramExtendedAccountSummaries2.get(i);
      int j = localExtendedAccountSummary1.getSummaryType();
      for (int k = 0; k < paramExtendedAccountSummaries1.size(); k++)
      {
        ExtendedAccountSummary localExtendedAccountSummary2 = (ExtendedAccountSummary)paramExtendedAccountSummaries1.get(k);
        if (j == localExtendedAccountSummary2.getSummaryType())
        {
          localExtendedAccountSummary1.setSummaryDate(localExtendedAccountSummary2.getSummaryDate());
          localExtendedAccountSummaries1.add(localExtendedAccountSummary1);
        }
      }
    }
    return localExtendedAccountSummaries1;
  }
  
  public static void recalculateTransactionRunningBalances(Connection paramConnection, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator1 = localSet.iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator1.next();
      AccountKey localAccountKey = (AccountKey)localEntry.getKey();
      Account localAccount = new Account();
      localAccount.setID(localAccountKey.getAccountID());
      localAccount.setRoutingNum(localAccountKey.getRoutingNumber());
      localAccount.setBankID(localAccountKey.getBankID());
      if (paramHashMap2.containsKey("LOCALE")) {
        localAccount.setLocale((Locale)paramHashMap2.get("LOCALE"));
      }
      ArrayList localArrayList = (ArrayList)localEntry.getValue();
      Iterator localIterator2 = localArrayList.iterator();
      while (localIterator2.hasNext())
      {
        DateTime localDateTime1 = (DateTime)localIterator2.next();
        AccountHistory localAccountHistory = jdMethod_int(paramConnection, localAccount, localDateTime1, paramHashMap2);
        Currency localCurrency = null;
        if (localAccountHistory != null) {
          localCurrency = localAccountHistory.getClosingLedger();
        }
        DateTime localDateTime2 = new DateTime(localDateTime1.getTime(), localDateTime1.getLocale());
        localDateTime2.set(11, 0);
        localDateTime2.set(12, 0);
        localDateTime2.set(13, 0);
        localDateTime2.set(14, 0);
        DateTime localDateTime3 = new DateTime(localDateTime2.getTime(), localDateTime1.getLocale());
        localDateTime3.add(6, 1);
        localDateTime3.add(14, -1);
        HashMap localHashMap1 = new HashMap();
        localHashMap1.putAll(paramHashMap2);
        localHashMap1.put("DATA_CLASSIFICATION", "P");
        Transactions localTransactions = DCAdapter.getTransactions(paramConnection, localAccount, localDateTime2, localDateTime3, localHashMap1);
        if (localTransactions.size() != 0)
        {
          localTransactions = updateTransactionRunningBalances(localTransactions, localCurrency, true);
          DCAdapter.updateTransactions(localAccount, localTransactions, paramConnection, localHashMap1);
        }
        HashMap localHashMap2 = new HashMap();
        localHashMap2.putAll(paramHashMap2);
        localHashMap2.put("DATA_CLASSIFICATION", "I");
        localTransactions = DCAdapter.getTransactions(paramConnection, localAccount, localDateTime2, localDateTime3, localHashMap2);
        if (localTransactions.size() != 0)
        {
          localTransactions = updateTransactionRunningBalances(localTransactions, localCurrency, true);
          DCAdapter.updateTransactions(localAccount, localTransactions, paramConnection, localHashMap2);
        }
      }
    }
  }
  
  private static ArrayList jdMethod_int(String paramString, HashMap paramHashMap, ArrayList paramArrayList)
    throws DCException
  {
    if (paramArrayList.contains(paramString)) {
      return null;
    }
    ArrayList localArrayList = (ArrayList)paramHashMap.get(paramString);
    if (localArrayList == null)
    {
      AffiliateBank localAffiliateBank = null;
      try
      {
        localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByRoutingNumber(new SecureUser(), paramString, new HashMap());
      }
      catch (CSILException localCSILException1)
      {
        DebugLog.throwing("Unable to retrieve the restricted calculations for the affiliate bank corresponding  to the routing number " + paramString + ".  No IR recalculations will be performed " + "for accounts belonging to this bank.", localCSILException1);
        paramArrayList.add(paramString);
        return null;
      }
      int i = localAffiliateBank.getAffiliateBankID();
      try
      {
        localArrayList = AffiliateBankAdmin.getRestrictedCalculations(new SecureUser(), i, new HashMap());
      }
      catch (CSILException localCSILException2)
      {
        throw new DCException(361, "Unable to retrieve the restricted calculations for the affiliate bank corresponding to the routing number " + paramString + ".", localCSILException2);
      }
      if (localArrayList != null) {
        paramHashMap.put(paramString, localArrayList);
      }
    }
    return localArrayList;
  }
  
  private static AccountHistory jdMethod_int(Connection paramConnection, Account paramAccount, DateTime paramDateTime, HashMap paramHashMap)
    throws DCException
  {
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setBankID(paramAccount.getBankID());
    BankIdentifier localBankIdentifier = new BankIdentifier(paramDateTime.getLocale());
    localBankIdentifier.setBankDirectoryId(paramAccount.getRoutingNum());
    java.util.Date localDate = null;
    try
    {
      localDate = SmartCalendar.getPreviousDay(localSecureUser, localBankIdentifier, paramDateTime.getTime(), paramHashMap);
    }
    catch (Exception localException)
    {
      throw new DCException(360, "Unable to retrieve the previous business day from the smart calendar for the following bank: routingnumber=" + paramAccount.getRoutingNum() + ", bankid=" + paramAccount.getBankID() + ".", localException);
    }
    DateTime localDateTime1 = new DateTime(localDate, paramDateTime.getLocale());
    localDateTime1.set(11, 0);
    localDateTime1.set(12, 0);
    localDateTime1.set(13, 0);
    localDateTime1.set(14, 0);
    DateTime localDateTime2 = new DateTime(localDateTime1.getTime(), paramDateTime.getLocale());
    localDateTime2.add(6, 1);
    localDateTime2.add(14, -1);
    HashMap localHashMap = new HashMap();
    localHashMap.putAll(paramHashMap);
    localHashMap.put("DATA_CLASSIFICATION", "P");
    AccountHistories localAccountHistories = combineAccountHistoriesByDate(DCAdapter.getHistory(paramAccount, localDateTime1, localDateTime2, paramConnection, localHashMap));
    AccountHistory localAccountHistory = null;
    if (localAccountHistories.size() != 0) {
      localAccountHistory = (AccountHistory)localAccountHistories.get(0);
    }
    return localAccountHistory;
  }
  
  public static Transactions getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, Business paramBusiness, Connection paramConnection)
    throws DCException
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("DATA_CLASSIFICATION", paramString);
    Properties localProperties = new Properties();
    DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar1 != null) {
      localProperties.setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
    }
    if (paramCalendar2 != null) {
      localProperties.setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
    }
    if (paramAccount.isMaster())
    {
      if (paramBusiness == null) {
        try
        {
          SecureUser localSecureUser = new SecureUser();
          localSecureUser.setBankID("1000");
          localObject = AccountHandler.getBusinessesForAccount(localSecureUser, paramAccount, localHashMap);
          if ((localObject != null) && (((Businesses)localObject).size() != 0)) {
            paramBusiness = (Business)((Businesses)localObject).get(0);
          }
        }
        catch (CSILException localCSILException)
        {
          Object localObject = new DCException(419, "Unexpected exception when retrieving the business for the account.", localCSILException);
          DebugLog.throwing("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", localCSILException);
          throw ((Throwable)localObject);
        }
      }
      if (paramBusiness != null) {
        if (paramBusiness.getLocationIdPlacementValue() == 2) {
          localProperties.setProperty("ZBAWithCustomerReferenceNumberNull", "true");
        } else if (paramBusiness.getLocationIdPlacementValue() == 1) {
          localProperties.setProperty("ZBAWithBankReferenceNumberNull", "true");
        }
      }
    }
    return DCAdapter.getTransactions(paramConnection, paramAccount, localProperties, localHashMap);
  }
  
  public static LockboxTransactions getLockboxTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, Connection paramConnection)
    throws DCException
  {
    LockboxAccount localLockboxAccount = new LockboxAccount(paramAccount.getLocale());
    localLockboxAccount.setAccountID(paramAccount.getID());
    localLockboxAccount.setAccountNumber(paramAccount.getNumber());
    localLockboxAccount.setRoutingNumber(paramAccount.getRoutingNum());
    localLockboxAccount.setBankID(paramAccount.getBankID());
    localLockboxAccount.setNickname(paramAccount.getNickName());
    localLockboxAccount.setCurrencyType(paramAccount.getCurrencyCode());
    HashMap localHashMap = new HashMap();
    localHashMap.put("DATA_CLASSIFICATION", paramString);
    LockboxTransactions localLockboxTransactions = DCAdapter.getLockboxTransactions(localLockboxAccount, paramCalendar1, paramCalendar2, paramConnection, localHashMap);
    return localLockboxTransactions;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCUtil
 * JD-Core Version:    0.7.0.1
 */