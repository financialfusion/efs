package com.ffusion.bankreport.adapter;

import com.ffusion.bankreport.BRException;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.beans.bankreport.ReportLineItem;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.util.GrowableIntArray;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.ExtendableTableUtil;
import com.ffusion.util.db.PoolException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class BankReportAdapter
  implements IBankReportAdapter
{
  private static final String jdField_long = "BANKREPORTS";
  private static final String jdField_if = "select REPORTID, GENERATEDTIME, IMPORTTIME from BR_BANKREPORTS where DIRECTORYID = ? and REPORTTYPE = ?";
  private static final String jdField_goto = "select distinct ACCOUNTID, RTGNUM from BR_BANKREPORTDATA where REPORTID = ?";
  private static final String jdField_char = "select DIRECTORYID from BR_BANKREPORTS where REPORTID = ?";
  private static final String f = "delete from BR_BANKREPORTS where REPORTTYPE = ? and IMPORTTIME <= ?";
  private static final String jdField_void = "delete from BR_BANKREPORTS where REPORTTYPE = ? and GENERATEDTIME >= ? and GENERATEDTIME <= ?";
  private static final String jdField_null = "delete from BR_BANKREPORTS where REPORTID = ?";
  private static final String d = "delete from BR_BANKREPORTDATA where ACCOUNTID = ? and RTGNUM = ? and REPORTID in ( select REPORTID from BR_BANKREPORTS where DIRECTORYID = ? )";
  private static final String jdField_try = "delete from BR_BANKREPORTS where DIRECTORYID = ?";
  private static final String e = "delete from BR_BANKREPORTS b1 where b1.DIRECTORYID = ? and not exists (  select b2.REPORTID from BR_BANKREPORTDATA b2 where b1.REPORTID = b2.REPORTID )";
  private static final String a = "insert into BR_BANKREPORTS( REPORTID, GENERATEDTIME, IMPORTTIME, REPORTTYPE,  DIRECTORYID";
  private static final String jdField_else = "update BR_BANKREPORTS set GENERATEDTIME = ?, IMPORTTIME = ?";
  private static final String jdField_byte = "where REPORTID = ?";
  private static final String jdField_for = "insert into BR_BANKREPORTDATA( REPORTID, LINENUM, LINEDATA, FORCEPAGEBREAK";
  private static final String jdField_new = ", RTGNUM";
  private static final String jdField_case = ", ACCOUNTID";
  private static final String b = "select b.business_id from business b inner join accounts a on b.business_id=a.directory_id where a.account_id = ? and a.routing_num = ? and b.bank_id = ?";
  private static final String jdField_do = "select c.directory_id from customer c inner join accounts a on c.directory_id=a.directory_id where a.account_id = ? and a.routing_num = ? and c.BANK_ID = ?";
  private String c = null;
  private String jdField_int = null;
  
  public void initialize(Properties paramProperties)
    throws BRException
  {
    try
    {
      this.c = ConnectionPool.init(paramProperties);
      this.jdField_int = paramProperties.getProperty("ConnectionType");
      ExtendableTableUtil.initialize(paramProperties);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof PoolException)) {
        throw new BRException(60011, "Could not initialize the bank report adapter.", localThrowable);
      }
      throw new BRException("Could not initialize the bank report adapter.", localThrowable);
    }
  }
  
  public BankReports getReports(int paramInt, String paramString, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BankReports localBankReports = new BankReports(Locale.getDefault());
    try
    {
      if (paramString == null) {
        throw new BRException(60100, "Could not obtain the bank reports from the database.");
      }
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select REPORTID, GENERATEDTIME, IMPORTTIME from BR_BANKREPORTS where DIRECTORYID = ? and REPORTTYPE = ?");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select REPORTID, GENERATEDTIME, IMPORTTIME from BR_BANKREPORTS where DIRECTORYID = ? and REPORTTYPE = ?");
      while (localResultSet.next())
      {
        BankReport localBankReport = localBankReports.add();
        localBankReport.setDirectoryID(paramInt);
        localBankReport.setType(paramString);
        localBankReport.setReportID(localResultSet.getInt(1));
        localBankReport.setGeneratedTime(new DateTime(localResultSet.getTimestamp(2), Locale.getDefault()));
        localBankReport.setImportTime(new DateTime(localResultSet.getTimestamp(3), Locale.getDefault()));
      }
    }
    catch (Throwable localThrowable1)
    {
      if ((localThrowable1 instanceof PoolException)) {
        throw new BRException(60011, "Could not obtain the bank reports from the database.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain the bank reports from the database.", localThrowable1);
      }
      throw new BRException("Could not obtain the bank reports from the database.", localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
    return localBankReports;
  }
  
  public Accounts getAccountsForReport(int paramInt, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Accounts localAccounts1 = new Accounts(Locale.getDefault());
    try
    {
      localConnection = DBUtil.getConnection(this.c, true, 2);
      int i = -1;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select DIRECTORYID from BR_BANKREPORTS where REPORTID = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select DIRECTORYID from BR_BANKREPORTS where REPORTID = ?");
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      if (i != -1)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct ACCOUNTID, RTGNUM from BR_BANKREPORTDATA where REPORTID = ?");
        localPreparedStatement.setInt(1, paramInt);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct ACCOUNTID, RTGNUM from BR_BANKREPORTDATA where REPORTID = ?");
        ArrayList localArrayList = new ArrayList();
        while (localResultSet.next())
        {
          a locala = new a();
          locala.jdField_if(localResultSet.getString(1));
          locala.a(localResultSet.getString(2));
          localArrayList.add(locala);
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        int j = localArrayList.size();
        if (j > 0)
        {
          Accounts localAccounts2 = AccountAdapter.getAccounts(new Account(), i);
          a[] arrayOfa = new a[j];
          arrayOfa = (a[])localArrayList.toArray(arrayOfa);
          Iterator localIterator = localAccounts2.iterator();
          while (localIterator.hasNext())
          {
            Account localAccount = (Account)localIterator.next();
            String str1 = localAccount.getID();
            String str2 = localAccount.getRoutingNum();
            for (int k = 0; k < j; k++) {
              if ((arrayOfa[k].jdField_if() != null) && (arrayOfa[k].a() != null) && (arrayOfa[k].jdField_if().equals(str2)) && (arrayOfa[k].a().equals(str1)))
              {
                localAccounts1.add(localAccount);
                break;
              }
            }
          }
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      if ((localThrowable1 instanceof PoolException)) {
        throw new BRException(60011, "Could not obtain the accounts for the given bank report.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain the accounts for the given bank report.", localThrowable1);
      }
      throw new BRException("Could not obtain the accounts for the given bank report.", localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
    return localAccounts1;
  }
  
  public void cleanupOldReports(int paramInt, String paramString, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramString == null) {
        throw new BRException(60100, "Could not remove the old bank reports from the database.");
      }
      if (paramInt < 0) {
        throw new BRException(60107, "Could not remove the old bank reports from the database.");
      }
      long l1 = System.currentTimeMillis();
      long l2 = 86400000L;
      long l3 = paramInt * l2;
      long l4 = l1 - l3;
      Timestamp localTimestamp = new Timestamp(l4);
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from BR_BANKREPORTS where REPORTTYPE = ? and IMPORTTIME <= ?");
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setTimestamp(2, localTimestamp);
      DBUtil.executeUpdate(localPreparedStatement, "delete from BR_BANKREPORTS where REPORTTYPE = ? and IMPORTTIME <= ?");
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not remove the old bank reports from the database.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not remove the old bank reports from the database.", localThrowable2);
      }
      throw new BRException("Could not remove the old bank reports from the database.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public void removeReportFile(Connection paramConnection, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws BRException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramString == null) {
        throw new BRException(60100, "Could not remove the report data from the database.");
      }
      if (paramDateTime == null) {
        throw new BRException(60106, "Could not remove the report data from the database.");
      }
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      localGregorianCalendar.setTime(paramDateTime.getTime());
      localGregorianCalendar.set(14, localGregorianCalendar.getMinimum(14));
      Timestamp localTimestamp1 = new Timestamp(localGregorianCalendar.getTime().getTime());
      localGregorianCalendar.set(14, localGregorianCalendar.getMaximum(14));
      Timestamp localTimestamp2 = new Timestamp(localGregorianCalendar.getTime().getTime());
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from BR_BANKREPORTS where REPORTTYPE = ? and GENERATEDTIME >= ? and GENERATEDTIME <= ?");
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setTimestamp(2, localTimestamp1);
      localPreparedStatement.setTimestamp(3, localTimestamp2);
      DBUtil.executeUpdate(localPreparedStatement, "delete from BR_BANKREPORTS where REPORTTYPE = ? and GENERATEDTIME >= ? and GENERATEDTIME <= ?");
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not remove the report data from the database.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not remove the report data from the database.", localThrowable2);
      }
      throw new BRException("Could not remove the report data from the database.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public void removeReport(int paramInt, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from BR_BANKREPORTS where REPORTID = ?");
      localPreparedStatement.setInt(1, paramInt);
      int i = DBUtil.executeUpdate(localPreparedStatement, "delete from BR_BANKREPORTS where REPORTID = ?");
      if (i == 0) {
        throw new BRException(60108, "Could not remove the report data from the database.");
      }
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not remove the report data from the database.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not remove the report data from the database.", localThrowable2);
      }
      throw new BRException("Could not remove the report data from the database.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public void removeAccountData(int paramInt, Account paramAccount, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramAccount == null) {
        throw new BRException(60101, "Could not remove the report data for the specified account.");
      }
      if (paramAccount.getBankID() == null) {
        throw new BRException(60103, "Could not remove the report data for the specified account.");
      }
      if (paramAccount.getRoutingNum() == null) {
        throw new BRException(60104, "Could not remove the report data for the specified account.");
      }
      if (paramAccount.getID() == null) {
        throw new BRException(60102, "Could not remove the report data for the specified account.");
      }
      if (!AccountAdapter.getBankIdByDirectoryId(paramInt).equals(paramAccount.getBankID())) {
        throw new BRException(60105, "Could not remove the report data for the specified account.");
      }
      localConnection = DBUtil.getConnection(this.c, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from BR_BANKREPORTDATA where ACCOUNTID = ? and RTGNUM = ? and REPORTID in ( select REPORTID from BR_BANKREPORTS where DIRECTORYID = ? )");
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getRoutingNum());
      localPreparedStatement.setInt(3, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from BR_BANKREPORTDATA where ACCOUNTID = ? and RTGNUM = ? and REPORTID in ( select REPORTID from BR_BANKREPORTS where DIRECTORYID = ? )");
      removeEmptyReports(localConnection, paramInt, paramHashMap);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        DBUtil.rollback(localConnection);
      }
      catch (Exception localException) {}
      if ((localThrowable1 instanceof PoolException)) {
        throw new BRException(60011, "Could not remove the report data for the specified account.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not remove the report data for the specified account.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw ((BRException)localThrowable1);
      }
      throw new BRException("Could not remove the report data for the specified account.", localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.endTransaction(localConnection);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
  }
  
  public void removeDirectoryData(int paramInt, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from BR_BANKREPORTS where DIRECTORYID = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from BR_BANKREPORTS where DIRECTORYID = ?");
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not remove the report data for the specified business/user.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not remove the report data for the specified business/user.", localThrowable2);
      }
      throw new BRException("Could not remove the report data for the specified business/user.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public void removeEmptyReports(Connection paramConnection, int paramInt, HashMap paramHashMap)
    throws BRException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from BR_BANKREPORTS b1 where b1.DIRECTORYID = ? and not exists (  select b2.REPORTID from BR_BANKREPORTDATA b2 where b1.REPORTID = b2.REPORTID )");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from BR_BANKREPORTS b1 where b1.DIRECTORYID = ? and not exists (  select b2.REPORTID from BR_BANKREPORTDATA b2 where b1.REPORTID = b2.REPORTID )");
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not remove the empty reports from the database.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not remove the empty reports from the database.", localThrowable2);
      }
      throw new BRException("Could not remove the empty reports from the database.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public Connection getConnection()
    throws BRException
  {
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.c, true, 2);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof PoolException)) {
        throw new BRException(60011, "Could not obtain a connection from the connection pool.", localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain a connection from the connection pool.", localThrowable);
      }
      throw new BRException("Could not obtain a connection from the connection pool.", localThrowable);
    }
    return localConnection;
  }
  
  public void releaseConnection(Connection paramConnection)
    throws BRException
  {
    try
    {
      DBUtil.returnConnectionWithException(this.c, paramConnection);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof PoolException)) {
        throw new BRException(60011, "Could not release a connection back to the connection pool.", localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new BRException(60010, "Could not release a connection back to the connection pool.", localThrowable);
      }
      throw new BRException("Could not release a connection back to the connection pool.", localThrowable);
    }
  }
  
  public void insertBankReport(Connection paramConnection, BankReport paramBankReport, HashMap paramHashMap)
    throws BRException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramBankReport == null) {
        throw new BRException(60109, "Could not add the bank report to the database.");
      }
      if (paramBankReport.getType() == null) {
        throw new BRException(60100, "Could not add the bank report to the database.");
      }
      DateTime localDateTime1 = paramBankReport.getGeneratedTime();
      DateTime localDateTime2 = paramBankReport.getImportTime();
      long l1 = System.currentTimeMillis();
      long l2 = l1;
      if (localDateTime2 != null) {
        l1 = localDateTime2.getTime().getTime();
      }
      if (localDateTime1 != null) {
        l2 = localDateTime1.getTime().getTime();
      }
      StringBuffer localStringBuffer = new StringBuffer("insert into BR_BANKREPORTS( REPORTID, GENERATEDTIME, IMPORTTIME, REPORTTYPE,  DIRECTORYID");
      ExtendableTableUtil.appendSQLInsertColumns(localStringBuffer, "BR_BANKREPORTS", true);
      String str = localStringBuffer.toString();
      int i = 1;
      int j = DBUtil.getNextId(paramConnection, this.jdField_int, "BANKREPORTS");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement, i, j, false);
      i = ExtendableTableUtil.setStatementDate(localPreparedStatement, i, new Timestamp(l2), false);
      i = ExtendableTableUtil.setStatementDate(localPreparedStatement, i, new Timestamp(l1), false);
      i = ExtendableTableUtil.setStatementString(localPreparedStatement, i, paramBankReport.getType(), false);
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement, i, paramBankReport.getDirectoryID(), false);
      i = ExtendableTableUtil.setStatementValues(localPreparedStatement, i, paramBankReport, "BR_BANKREPORTS", true);
      DBUtil.executeUpdate(localPreparedStatement, str);
      paramBankReport.setReportID(j);
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not add the bank report to the database.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not add the bank report to the database.", localThrowable2);
      }
      throw new BRException("Could not add the bank report to the database.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public void updateBankReport(Connection paramConnection, BankReport paramBankReport, HashMap paramHashMap)
    throws BRException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      long l1 = System.currentTimeMillis();
      long l2 = l1;
      long l3 = l1;
      if (paramBankReport == null) {
        throw new BRException(60109, "Could not modify the bank report.");
      }
      if (paramBankReport.getType() == null) {
        throw new BRException(60100, "Could not modify the bank report.");
      }
      if (paramBankReport.getGeneratedTime() != null) {
        l3 = paramBankReport.getGeneratedTime().getTime().getTime();
      }
      StringBuffer localStringBuffer = new StringBuffer("update BR_BANKREPORTS set GENERATEDTIME = ?, IMPORTTIME = ?");
      ExtendableTableUtil.appendSQLUpdateColumns(localStringBuffer, "BR_BANKREPORTS");
      localStringBuffer.append("where REPORTID = ?");
      String str = localStringBuffer.toString();
      int i = 1;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
      i = ExtendableTableUtil.setStatementDate(localPreparedStatement, i, new Timestamp(l3), false);
      i = ExtendableTableUtil.setStatementDate(localPreparedStatement, i, new Timestamp(l2), false);
      i = ExtendableTableUtil.setStatementValues(localPreparedStatement, i, paramBankReport, "BR_BANKREPORTS", true);
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement, i, paramBankReport.getReportID(), false);
      DBUtil.executeUpdate(localPreparedStatement, str);
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not modify the bank report.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not modify the bank report.", localThrowable2);
      }
      throw new BRException("Could not modify the bank report.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public void insertLineItem(Connection paramConnection, ReportLineItem paramReportLineItem, HashMap paramHashMap)
    throws BRException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramReportLineItem == null) {
        throw new BRException(60110, "Could not add the data to the bank report.");
      }
      StringBuffer localStringBuffer = new StringBuffer("insert into BR_BANKREPORTDATA( REPORTID, LINENUM, LINEDATA, FORCEPAGEBREAK");
      if (paramReportLineItem.getRoutingNum() != null) {
        localStringBuffer.append(", RTGNUM");
      }
      if (paramReportLineItem.getAccountID() != null) {
        localStringBuffer.append(", ACCOUNTID");
      }
      ExtendableTableUtil.appendSQLInsertColumns(localStringBuffer, "BR_BANKREPORTDATA", true);
      String str1 = localStringBuffer.toString();
      int i = 1;
      String str2 = paramReportLineItem.getForcePageBreak() ? "Y" : "N";
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str1);
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement, i, paramReportLineItem.getReportID(), false);
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement, i, paramReportLineItem.getLineNumber(), false);
      i = ExtendableTableUtil.setStatementString(localPreparedStatement, i, paramReportLineItem.getData(), false);
      i = ExtendableTableUtil.setStatementString(localPreparedStatement, i, str2, false);
      i = ExtendableTableUtil.setStatementString(localPreparedStatement, i, paramReportLineItem.getRoutingNum(), true);
      i = ExtendableTableUtil.setStatementString(localPreparedStatement, i, paramReportLineItem.getAccountID(), true);
      i = ExtendableTableUtil.setStatementValues(localPreparedStatement, i, paramReportLineItem, "BR_BANKREPORTDATA", true);
      DBUtil.executeUpdate(localPreparedStatement, str1);
      return;
    }
    catch (Throwable localThrowable2)
    {
      if ((localThrowable2 instanceof PoolException)) {
        throw new BRException(60011, "Could not add the data to the bank report.", localThrowable2);
      }
      if ((localThrowable2 instanceof SQLException)) {
        throw new BRException(60010, "Could not add the data to the bank report.", localThrowable2);
      }
      throw new BRException("Could not add the data to the bank report.", localThrowable2);
    }
    finally
    {
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      catch (Throwable localThrowable3) {}
    }
  }
  
  public int getBusinessIdFromAccount(String paramString1, String paramString2, String paramString3)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1;
    try
    {
      if (paramString1 == null) {
        throw new BRException(60103, "Could not obtain the business ID of the gievn account.");
      }
      if (paramString2 == null) {
        throw new BRException(60104, "Could not obtain the business ID of the gievn account.");
      }
      if (paramString3 == null) {
        throw new BRException(60102, "Could not obtain the business ID of the gievn account.");
      }
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select b.business_id from business b inner join accounts a on b.business_id=a.directory_id where a.account_id = ? and a.routing_num = ? and b.bank_id = ?");
      localPreparedStatement.setString(1, paramString3);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setString(3, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select b.business_id from business b inner join accounts a on b.business_id=a.directory_id where a.account_id = ? and a.routing_num = ? and b.bank_id = ?");
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (Throwable localThrowable1)
    {
      if ((localThrowable1 instanceof PoolException)) {
        throw new BRException(60011, "Could not obtain the business ID of the gievn account.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain the business ID of the gievn account.", localThrowable1);
      }
      throw new BRException("Could not obtain the business ID of the gievn account.", localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
    return i;
  }
  
  public int[] getBusinessIdsFromAccount(String paramString1, String paramString2, String paramString3)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1;
    GrowableIntArray localGrowableIntArray = null;
    try
    {
      if (paramString1 == null) {
        throw new BRException(60103, "Could not obtain the business ID of the gievn account.");
      }
      if (paramString2 == null) {
        throw new BRException(60104, "Could not obtain the business ID of the gievn account.");
      }
      if (paramString3 == null) {
        throw new BRException(60102, "Could not obtain the business ID of the gievn account.");
      }
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select b.business_id from business b inner join accounts a on b.business_id=a.directory_id where a.account_id = ? and a.routing_num = ? and b.bank_id = ?");
      localPreparedStatement.setString(1, paramString3);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setString(3, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select b.business_id from business b inner join accounts a on b.business_id=a.directory_id where a.account_id = ? and a.routing_num = ? and b.bank_id = ?");
      localGrowableIntArray = new GrowableIntArray();
      while (localResultSet.next()) {
        localGrowableIntArray.add(localResultSet.getInt(1));
      }
    }
    catch (Throwable localThrowable1)
    {
      if ((localThrowable1 instanceof PoolException)) {
        throw new BRException(60011, "Could not obtain the business ID of the gievn account.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain the business ID of the gievn account.", localThrowable1);
      }
      throw new BRException("Could not obtain the business ID of the gievn account.", localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
    return localGrowableIntArray.getElements();
  }
  
  public int getUserIdFromAccount(String paramString1, String paramString2, String paramString3)
    throws BRException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = -1;
    try
    {
      if (paramString1 == null) {
        throw new BRException(60103, "Could not obtain the user ID of the gievn account.");
      }
      if (paramString2 == null) {
        throw new BRException(60104, "Could not obtain the user ID of the gievn account.");
      }
      if (paramString3 == null) {
        throw new BRException(60102, "Could not obtain the user ID of the gievn account.");
      }
      localConnection = DBUtil.getConnection(this.c, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id from customer c inner join accounts a on c.directory_id=a.directory_id where a.account_id = ? and a.routing_num = ? and c.BANK_ID = ?");
      localPreparedStatement.setString(1, paramString3);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setString(3, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id from customer c inner join accounts a on c.directory_id=a.directory_id where a.account_id = ? and a.routing_num = ? and c.BANK_ID = ?");
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (Throwable localThrowable1)
    {
      if ((localThrowable1 instanceof PoolException)) {
        throw new BRException(60011, "Could not obtain the user ID of the gievn account.", localThrowable1);
      }
      if ((localThrowable1 instanceof SQLException)) {
        throw new BRException(60010, "Could not obtain the user ID of the gievn account.", localThrowable1);
      }
      throw new BRException("Could not obtain the user ID of the gievn account.", localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        DBUtil.returnConnection(this.c, localConnection);
      }
      catch (Throwable localThrowable2) {}
    }
    return i;
  }
  
  class a
  {
    private String jdField_if;
    private String a;
    
    public a() {}
    
    public void jdField_if(String paramString)
    {
      this.jdField_if = paramString;
    }
    
    public String a()
    {
      return this.jdField_if;
    }
    
    public void a(String paramString)
    {
      this.a = paramString;
    }
    
    public String jdField_if()
    {
      return this.a;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.adapter.BankReportAdapter
 * JD-Core Version:    0.7.0.1
 */