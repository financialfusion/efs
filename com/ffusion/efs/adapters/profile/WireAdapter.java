package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.bcreport.ReportLogRecord;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.user.User;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

public class WireAdapter
  implements ProfileDefines
{
  private static final String akt = "SELECT TRAN_ID, USER_ID, AGENT_ID, AGENT_TYPE, DESCRIPTION, LOG_DATE, BUSINESS_ID, AMOUNT, CURRENCY_CODE, SRVR_TID, STATE, FROM_ACCT_ID, FROM_ACCT_RTGNUM, USER_ID_INT FROM AUDIT_LOG WHERE TRAN_ID=? AND (SRVR_TID=? OR SRVR_TID IS NULL OR SRVR_TID='') ORDER BY LOG_DATE";
  private static final String aku = "SELECT USER_ID, AGENT_ID, AGENT_TYPE, DESCRIPTION, TRAN_ID, TRAN_TYPE, BUSINESS_ID, AMOUNT, CURRENCY_CODE, LOG_DATE, SRVR_TID, STATE, TO_ACCT_ID, TO_ACCT_RTGNUM, FROM_ACCT_ID, FROM_ACCT_RTGNUM, MODULE, USER_ID_INT FROM AUDIT_LOG WHERE TRAN_ID=? AND (SRVR_TID=? OR SRVR_TID IS NULL OR SRVR_TID='') ORDER BY LOG_DATE";
  
  public static WireTransfers getAuditHistoryById(String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "WireAdapter.getAuditHistoryById";
    Profile.isInitialized();
    if ((paramString1 == null) || (paramString1.length() == 0))
    {
      DebugLog.log(str + ": Missing TranId in search criteria.");
      throw new ProfileException(str, 2, "Missing TranId in search criteria.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    WireTransfers localWireTransfers = new WireTransfers();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT TRAN_ID, USER_ID, AGENT_ID, AGENT_TYPE, DESCRIPTION, LOG_DATE, BUSINESS_ID, AMOUNT, CURRENCY_CODE, SRVR_TID, STATE, FROM_ACCT_ID, FROM_ACCT_RTGNUM, USER_ID_INT FROM AUDIT_LOG WHERE TRAN_ID=? AND (SRVR_TID=? OR SRVR_TID IS NULL OR SRVR_TID='') ORDER BY LOG_DATE");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString1, "TRAN_ID", false, false);
      i = Profile.setStatementString(localPreparedStatement, i, paramString2, "SRVR_TID", false, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT TRAN_ID, USER_ID, AGENT_ID, AGENT_TYPE, DESCRIPTION, LOG_DATE, BUSINESS_ID, AMOUNT, CURRENCY_CODE, SRVR_TID, STATE, FROM_ACCT_ID, FROM_ACCT_RTGNUM, USER_ID_INT FROM AUDIT_LOG WHERE TRAN_ID=? AND (SRVR_TID=? OR SRVR_TID IS NULL OR SRVR_TID='') ORDER BY LOG_DATE");
      jdMethod_for(localResultSet, localWireTransfers);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localWireTransfers;
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, WireTransfers paramWireTransfers)
    throws Exception
  {
    while (paramResultSet.next())
    {
      WireTransfer localWireTransfer = (WireTransfer)paramWireTransfers.create();
      try
      {
        localWireTransfer.setTrackingID(paramResultSet.getString(1));
        String str1 = paramResultSet.getString(2);
        String str2 = paramResultSet.getString(3);
        String str3 = paramResultSet.getString(4);
        localWireTransfer.setComment(paramResultSet.getString(5));
        Timestamp localTimestamp = paramResultSet.getTimestamp(6);
        localWireTransfer.setLogDate(localTimestamp.toString());
        localWireTransfer.setCustomerID(paramResultSet.getInt(7));
        localWireTransfer.setAmount(paramResultSet.getBigDecimal(8));
        localWireTransfer.setAmtCurrencyType(paramResultSet.getString(9));
        localWireTransfer.setID(paramResultSet.getString(10));
        localWireTransfer.setTranState(paramResultSet.getString(11));
        localWireTransfer.setFromAccountID(paramResultSet.getString(12));
        localWireTransfer.setFromAccountRoutingNum(paramResultSet.getString(13));
        if ((str2 != null) && (str2.trim().length() > 0))
        {
          localWireTransfer.setProcessedBy("Bank Employee");
        }
        else if ((str1 != null) && (str1.trim().length() > 0))
        {
          int i = paramResultSet.getInt(14);
          User localUser = CustomerAdapter.getUserById(i);
          localWireTransfer.setProcessedBy(localUser.getUserName());
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException) {}catch (Exception localException)
      {
        DebugLog.log("WARNING: Processing AuditLogHistory results - " + localException.getMessage());
      }
    }
  }
  
  public static ReportLogRecords getAuditHistoryRecordsById(String paramString1, String paramString2, Locale paramLocale)
  {
    String str = "WireAdapter.getAuditHistoryRecordsById";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ReportLogRecords localReportLogRecords = new ReportLogRecords();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT USER_ID, AGENT_ID, AGENT_TYPE, DESCRIPTION, TRAN_ID, TRAN_TYPE, BUSINESS_ID, AMOUNT, CURRENCY_CODE, LOG_DATE, SRVR_TID, STATE, TO_ACCT_ID, TO_ACCT_RTGNUM, FROM_ACCT_ID, FROM_ACCT_RTGNUM, MODULE, USER_ID_INT FROM AUDIT_LOG WHERE TRAN_ID=? AND (SRVR_TID=? OR SRVR_TID IS NULL OR SRVR_TID='') ORDER BY LOG_DATE");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString1, "TRAN_ID", false, false);
      i = Profile.setStatementString(localPreparedStatement, i, paramString2, "SRVR_TID", false, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT USER_ID, AGENT_ID, AGENT_TYPE, DESCRIPTION, TRAN_ID, TRAN_TYPE, BUSINESS_ID, AMOUNT, CURRENCY_CODE, LOG_DATE, SRVR_TID, STATE, TO_ACCT_ID, TO_ACCT_RTGNUM, FROM_ACCT_ID, FROM_ACCT_RTGNUM, MODULE, USER_ID_INT FROM AUDIT_LOG WHERE TRAN_ID=? AND (SRVR_TID=? OR SRVR_TID IS NULL OR SRVR_TID='') ORDER BY LOG_DATE");
      jdMethod_for(localResultSet, localReportLogRecords, paramLocale);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      DebugLog.log(str + ": " + localException.toString());
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localReportLogRecords;
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, ReportLogRecords paramReportLogRecords, Locale paramLocale)
    throws Exception
  {
    while (paramResultSet.next()) {
      try
      {
        String str1 = paramResultSet.getString(1);
        String str2 = paramResultSet.getString(2);
        ReportLogRecord localReportLogRecord = new ReportLogRecord(str1, str2, paramResultSet.getString(3), paramResultSet.getString(4), paramResultSet.getString(5), paramResultSet.getInt(6), paramResultSet.getInt(7), paramResultSet.getBigDecimal(8), paramResultSet.getString(9), paramResultSet.getString(11), paramResultSet.getString(12), paramResultSet.getString(13), paramResultSet.getString(14), paramResultSet.getString(15), paramResultSet.getString(16), paramResultSet.getInt(17));
        Timestamp localTimestamp = paramResultSet.getTimestamp(10);
        if (localTimestamp != null)
        {
          localReportLogRecord.setTranDate(new DateTime(localTimestamp, Locale.getDefault()));
          localReportLogRecord.setTimestamp(localTimestamp.toString());
        }
        String str3 = ReportAuditAdapter.getDisplayStatus(localReportLogRecord.getState(), paramLocale, "" + localReportLogRecord.getTranType());
        if ((str3 != null) && (!"-".equals(str3))) {
          localReportLogRecord.setState(str3);
        }
        if ((str2 != null) && (str2.trim().length() > 0))
        {
          localReportLogRecord.setProcessedBy("Bank Employee");
        }
        else if ((str1 != null) && (str1.length() > 0))
        {
          int i = paramResultSet.getInt(18);
          User localUser = CustomerAdapter.getUserById(i);
          localReportLogRecord.setProcessedBy(localUser.getUserName());
        }
        paramReportLogRecords.add(localReportLogRecord);
      }
      catch (IllegalArgumentException localIllegalArgumentException) {}catch (Exception localException)
      {
        DebugLog.log("WARNING: Processing AuditLogHistory results - " + localException.getMessage());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.WireAdapter
 * JD-Core Version:    0.7.0.1
 */