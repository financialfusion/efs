package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.AuditAdapter;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;

public class HistoryAdapter
  implements ProfileDefines
{
  private static final String ahW = "select id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str from history where id=? and id_type=?";
  private static final String ahV = "select id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str from history where id=? and id_type=? and column_changed=?";
  private static final String ahU = "delete from history where id=? and id_type=?";
  private static final String ahR = "insert into history (id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str) values (?,?,?,?,?,?,?,?,?,?)";
  protected static MessageAdapter messageAdapter = new MessageAdapter();
  private static boolean ahT = false;
  private static int ahS;
  
  private static void N()
  {
    if (ahT) {
      return;
    }
    Connection localConnection = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      DatabaseMetaData localDatabaseMetaData = localConnection.getMetaData();
      String str1 = "history";
      String str2 = "history_comment";
      if (localDatabaseMetaData.storesUpperCaseIdentifiers())
      {
        str1 = "HISTORY";
        str2 = "HISTORY_COMMENT";
      }
      String[] arrayOfString = { "TABLE" };
      localResultSet1 = localDatabaseMetaData.getTables(null, null, str1, arrayOfString);
      while (localResultSet1.next())
      {
        String str3 = localResultSet1.getString(1);
        String str4 = localResultSet1.getString(2);
        localResultSet2 = localDatabaseMetaData.getColumns(str3, str4, str1, str2);
        while (localResultSet2.next()) {
          ahS = localResultSet2.getInt(7);
        }
      }
    }
    catch (Exception localException1)
    {
      ahS = 255;
    }
    finally
    {
      if (localResultSet2 != null) {
        try
        {
          localResultSet2.close();
        }
        catch (Exception localException2) {}
      }
      if (localResultSet1 != null) {
        try
        {
          localResultSet1.close();
        }
        catch (Exception localException3) {}
      }
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    ahT = true;
  }
  
  public static Histories getHistory(History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2)
    throws ProfileException
  {
    String str1 = "HistoryAdapter.getHistory";
    Profile.isInitialized();
    N();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool = true;
    int i = paramHistory.getDataChanged() != null ? 1 : 0;
    StringBuffer localStringBuffer = new StringBuffer(i != 0 ? "select id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str from history where id=? and id_type=? and column_changed=?" : "select id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str from history where id=? and id_type=?");
    Locale localLocale = paramHistory.getLocale();
    Histories localHistories = new Histories(localLocale);
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      java.sql.Date localDate1 = null;
      java.sql.Date localDate2 = null;
      if (paramCalendar1 != null) {
        localDate1 = new java.sql.Date(paramCalendar1.getTime().getTime());
      }
      if (paramCalendar2 != null)
      {
        paramCalendar2.add(5, 1);
        localDate2 = new java.sql.Date(paramCalendar2.getTime().getTime());
      }
      bool = Profile.appendSQLSelectDate(localStringBuffer, "date_changed", localDate1, ">=", bool);
      bool = Profile.appendSQLSelectDate(localStringBuffer, "date_changed", localDate2, "<=", bool);
      String str2 = localStringBuffer.toString();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, paramHistory.getId(), false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramHistory.getIdTypeValue(), false);
      if (i != 0) {
        j = Profile.setStatementString(localPreparedStatement, j, paramHistory.getDataChanged(), "column_changed", false);
      }
      j = Profile.setStatementDate(localPreparedStatement, j, localDate1, true);
      j = Profile.setStatementDate(localPreparedStatement, j, localDate2, true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      while (localResultSet.next())
      {
        History localHistory = localHistories.add();
        localHistory.setId(localResultSet.getString(1));
        localHistory.setIdType(localResultSet.getInt(2));
        int k = localResultSet.getInt(3);
        localHistory.setModifierId(String.valueOf(k));
        localHistory.setModifierType(localResultSet.getInt(4));
        localHistory.setDateChanged(new DateTime(localResultSet.getTimestamp(5), localLocale));
        localHistory.setDataChanged(localResultSet.getString(6));
        localHistory.setOldValue(localResultSet.getString(7));
        localHistory.setNewValue(localResultSet.getString(8));
        localHistory.setComment(localResultSet.getString(9));
        localHistory.setTrackingID(localResultSet.getString(10));
        localHistory.setModifierName(messageAdapter.getIdName(localConnection, k, localHistory.getModifierTypeValue(), localHistory.getLocale(), null));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localHistories;
  }
  
  public static void addHistory(Histories paramHistories)
    throws ProfileException
  {
    String str1 = "HistoryAdapter.addHistory";
    if (paramHistories.isEmpty()) {
      return;
    }
    Profile.isInitialized();
    N();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into history (id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str) values (?,?,?,?,?,?,?,?,?,?)");
      ArrayList localArrayList = AuditAdapter.getLogLanguages();
      Locale localLocale = (Locale)localArrayList.get(0);
      Iterator localIterator = paramHistories.iterator();
      while (localIterator.hasNext())
      {
        History localHistory = (History)localIterator.next();
        localHistory.setLocale(localLocale);
        int i = 1;
        localPreparedStatement.setInt(i++, Profile.convertToInt(localHistory.getId(), 0));
        localPreparedStatement.setInt(i++, localHistory.getIdTypeValue());
        localPreparedStatement.setInt(i++, Profile.convertToInt(localHistory.getModifierId(), 0));
        localPreparedStatement.setInt(i++, localHistory.getModifierTypeValue());
        i = Profile.setStatementDate(localPreparedStatement, i, DBUtil.getCurrentTimestamp(), false);
        i = Profile.setStatementString(localPreparedStatement, i, localHistory.getDataChanged(), "column_changed", false, false, false, true);
        i = Profile.setStatementString(localPreparedStatement, i, localHistory.getOldValue(), "old_value", false, false, false, true);
        i = Profile.setStatementString(localPreparedStatement, i, localHistory.getNewValue(), "new_value", false, false, false, true);
        if ((localHistory.getComment() == null) || (localHistory.getComment().length() <= ahS))
        {
          i = Profile.setStatementString(localPreparedStatement, i, localHistory.getComment(), "history_comment", false, false, false, true);
        }
        else
        {
          String str2 = localHistory.getComment().substring(0, ahS);
          i = Profile.setStatementString(localPreparedStatement, i, str2, "HISTORY_COMMENT", false, false, false, true);
          DebugLog.log(Level.WARNING, "A history comment was truncated to " + ahS + " characters.");
        }
        i = Profile.setStatementString(localPreparedStatement, i, localHistory.getTrackingID(), "tracking_id", false, false, false, true);
        DBUtil.executeUpdate(localPreparedStatement, "insert into history (id,id_type,modifier_id,modifier_type,date_changed,column_changed,old_value,new_value,history_comment,id_str) values (?,?,?,?,?,?,?,?,?,?)");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  protected static void deleteHistory(History paramHistory)
    throws ProfileException
  {
    String str = "HistoryAdapter.deleteHistory";
    Profile.isInitialized();
    N();
    Connection localConnection = null;
    Object localObject1 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      deleteHistoryTX(localConnection, Profile.convertToInt(paramHistory.getId()), paramHistory.getIdTypeValue());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected static void deleteHistoryTX(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from history where id=? and id_type=?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from history where id=? and id_type=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.HistoryAdapter
 * JD-Core Version:    0.7.0.1
 */