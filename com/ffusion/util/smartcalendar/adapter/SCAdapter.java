package com.ffusion.util.smartcalendar.adapter;

import com.ffusion.util.LocaleUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.smartcalendar.SCBusinessDays;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.beans.smartcalendar.SCHoliday;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.smartcalendar.SCException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class SCAdapter
  implements ISCAdapter
{
  public static final int STARTING_DATA_VERSION = 1;
  public static final int MAX_DATA_VERSION = 2147483647;
  public static final int DEFAULT_CACHE_REFRESH_INTERVAL = 60;
  public static final String DB_PROPERTIES_TAG = "DB_PROPERTIES";
  public static final String CACHE_PROPERTIES_TAG = "CACHE_PROPERTIES";
  public static final String REFRESH_INTERVAL_TAG = "REFRESH_INTERVAL";
  private static final String jdField_null = "SC_CALENDAR_DEFN";
  public static final String COL_CALENDAR_ID = "calendar_id";
  public static final String COL_CALENDAR_NAME = "calendar_name";
  public static final String COL_BUSINESS_DAYS = "business_days";
  public static final String COL_ACTIONS = "actions";
  public static final String COL_HOLIDAY_DATE = "holiday_date";
  public static final String COL_HOLIDAY_NAME = "holiday_name";
  public static final String COL_ACTION = "action";
  public static final String COL_BANK_DIR_TYPE = "bank_dir_type";
  public static final String COL_BANK_DIR_ID = "bank_dir_id";
  public static final String COL_SERVICE_BUREAU_ID = "service_bureau_id";
  public static final String COL_IGNORE_FOR_TRANSFERS = "ignore_for_transfers";
  private static final String jdField_char = "select VERSION from SC_DATAVERSION";
  private static final String q = "insert into SC_DATAVERSION( VERSION ) values( 1 )";
  private static final String j = "update SC_DATAVERSION set VERSION = ?";
  private static final String jdField_byte = "update SC_DATAVERSION set VERSION = VERSION";
  private static final String b = "select BANK_DIR_TYPE, BANK_DIR_ID, SERVICE_BUREAU_ID, CALENDAR_ID from SC_CALENDARASSOC";
  private static final String jdField_int = "select CALENDAR_ID, SERVICE_BUREAU_ID, CALENDAR_NAME, BUSINESS_DAYS, ACTIONS, IGNORE_FOR_TRANSFERS from SC_CALENDARDEFN order by CALENDAR_ID";
  private static final String f = "select HOLIDAY_DATE, HOLIDAY_NAME, ACTION from SC_HOLIDAYDEFN where CALENDAR_ID = ?";
  private static final String jdField_new = "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ? AND HOLIDAY_DATE < ?";
  private static final String c = "select SERVICE_BUREAU_ID from SC_DEFAULT where CALENDAR_ID = ?";
  private static final String jdField_try = "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID where CALENDAR_ID = ?";
  private static final String k = "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID WHERE CALENDAR_ID = ? AND SERVICE_BUREAU_ID = ?";
  private static final String jdField_void = "insert into SC_CALENDARDEFN( CALENDAR_ID, SERVICE_BUREAU_ID, CALENDAR_NAME, BUSINESS_DAYS, ACTIONS, IGNORE_FOR_TRANSFERS ) values( ?, ?, ?, ?, ?, ? )";
  private static final String m = "delete from SC_CALENDARDEFN where CALENDAR_ID =?";
  private static final String i = "update SC_CALENDARDEFN set CALENDAR_NAME = ?, BUSINESS_DAYS = ?, ACTIONS = ?,  IGNORE_FOR_TRANSFERS = ? WHERE CALENDAR_ID = ?";
  private static final String s = "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ?";
  private static final String l = "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )";
  private static final String e = "update SC_HOLIDAYDEFN set HOLIDAY_DATE = ?, HOLIDAY_NAME = ?, ACTION = ?";
  private static final String jdField_if = "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?";
  private static final String n = "update SC_CALENDARDEFN set CALENDAR_NAME = ? WHERE CALENDAR_ID = ?";
  private static final String a = "update SC_CALENDARDEFN set BUSINESS_DAYS = ?, ACTIONS = ? WHERE CALENDAR_ID = ?";
  private static final String p = "select SERVICE_BUREAU_ID from SC_CALENDARDEFN WHERE CALENDAR_ID = ?";
  private static final String g = "delete from SC_DEFAULT where SERVICE_BUREAU_ID = ?";
  private static final String h = "delete from SC_DEFAULT where CALENDAR_ID = ?";
  private static final String jdField_long = "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )";
  private static final String jdField_goto = "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?";
  private static final String o = "insert into SC_CALENDARASSOC( BANK_DIR_TYPE, BANK_DIR_ID, SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ?, ?, ? )";
  private static final String r = "update SC_CALENDARASSOC set CALENDAR_ID = ? WHERE BANK_DIR_TYPE = ? AND BANK_DIR_ID = ? AND SERVICE_BUREAU_ID = ?";
  private static final String jdField_for = "delete from SC_CALENDARASSOC WHERE BANK_DIR_TYPE = ? AND BANK_DIR_ID = ? AND SERVICE_BUREAU_ID = ?";
  private SCCache jdField_case;
  private a jdField_do;
  private String jdField_else = null;
  private String d;
  
  public void initialize(HashMap paramHashMap)
    throws SCException
  {
    try
    {
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      if (localProperties == null)
      {
        String str1 = "<DB_PROPERTIES> tag not found in XML configuration file during initialization of the Smart Calendar adapter";
        DebugLog.log(Level.SEVERE, str1);
        throw new SCException(1, str1);
      }
      this.d = localProperties.getProperty("ConnectionType");
      if (!"true".equals(localProperties.getProperty("EXTERNAL_CONNECTION_POOL"))) {
        try
        {
          this.jdField_else = ConnectionPool.init(localProperties);
        }
        catch (PoolException localPoolException)
        {
          localObject = "Unable to create a DB Connection pool during initialization of the Smart Calendar adapter";
          DebugLog.throwing((String)localObject, localPoolException);
          throw new SCException(1, (String)localObject, localPoolException);
        }
      }
      int i1 = 60;
      localObject = (Properties)paramHashMap.get("CACHE_PROPERTIES");
      if (localObject == null)
      {
        DebugLog.log(Level.WARNING, "Smart Calendar adapter cache refresh interval not specified. Using a default value of " + i1 + " minutes");
      }
      else
      {
        int i2 = 1;
        try
        {
          i1 = Integer.parseInt((String)((Properties)localObject).get("REFRESH_INTERVAL"));
          if (i1 < 0)
          {
            i1 = 60;
            i2 = 0;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          i2 = 0;
        }
        if (i2 != 0) {
          DebugLog.log(Level.INFO, "Setting Smart Calendar adapter cache refresh interval to " + i1 + " minutes");
        } else {
          DebugLog.log(Level.WARNING, "An invalid value of \"" + (String)((Properties)localObject).get("REFRESH_INTERVAL") + "\" was " + "specified for the Smart Calendar adapter cache refresh " + "interval. Using a default " + "value of " + i1 + " minutes");
        }
      }
      this.jdField_case = new SCCache();
      jdField_if(false);
      DebugLog.log(Level.INFO, "Smart Calendar adapter cache has been initialized");
      if (i1 > 0) {
        this.jdField_do = new a(i1 * 60 * 1000);
      }
    }
    catch (Exception localException)
    {
      StringWriter localStringWriter = new StringWriter();
      Object localObject = new PrintWriter(localStringWriter);
      localException.printStackTrace((PrintWriter)localObject);
      String str2 = "An error occurred while initializing the Smart Calendar adapter: " + localStringWriter.toString();
      DebugLog.throwing(str2, localException);
      throw new SCException(1, str2, localException);
    }
  }
  
  public boolean isBankingDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    SCCalendar localSCCalendar = jdField_if(paramString, paramBankIdentifier);
    if (localSCCalendar == null)
    {
      localObject = "The operation could not be completed because there is no calendar associated with the bank and there is no default calendar specified for the service bureau";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new SCException(22, (String)localObject);
    }
    Object localObject = a(paramDate);
    return jdField_for(localSCCalendar, (Calendar)localObject);
  }
  
  public boolean isHoliday(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    SCCalendar localSCCalendar = jdField_if(paramString, paramBankIdentifier);
    if (localSCCalendar == null)
    {
      localObject = "The operation could not be completed because there is no calendar associated with the bank and there is no default calendar specified for the service bureau";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new SCException(22, (String)localObject);
    }
    Object localObject = a(paramDate);
    return jdField_if(localSCCalendar, (Calendar)localObject);
  }
  
  public Date getPreviousDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    SCCalendar localSCCalendar = jdField_if(paramString, paramBankIdentifier);
    if (localSCCalendar == null)
    {
      localObject = "The operation could not be completed because there is no calendar associated with the bank and there is no default calendar specified for the service bureau";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new SCException(22, (String)localObject);
    }
    Object localObject = a(paramDate);
    return a(localSCCalendar, (Calendar)localObject).getTime();
  }
  
  public Date getOffsetBankingDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, int paramInt, HashMap paramHashMap)
    throws SCException
  {
    SCCalendar localSCCalendar = jdField_if(paramString, paramBankIdentifier);
    if (localSCCalendar == null)
    {
      localObject = "The operation could not be completed because there is no calendar associated with the bank and there is no default calendar specified for the service bureau";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new SCException(22, (String)localObject);
    }
    Object localObject = a(paramDate);
    return a(localSCCalendar, (Calendar)localObject, paramInt).getTime();
  }
  
  public Date getTransactionDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    SCCalendar localSCCalendar = jdField_if(paramString, paramBankIdentifier);
    if (localSCCalendar == null)
    {
      localObject = "The operation could not be completed because there is no calendar associated with the bank and there is no default calendar specified for the service bureau";
      DebugLog.log(Level.SEVERE, (String)localObject);
      throw new SCException(22, (String)localObject);
    }
    Object localObject = a(paramDate);
    return jdField_do(localSCCalendar, (Calendar)localObject).getTime();
  }
  
  public void deleteCalendarHolidaysForDate(SCCalendar paramSCCalendar, Date paramDate, HashMap paramHashMap)
    throws SCException
  {
    String str = "SCAdapter.deleteCalendarHolidaysForDate";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      int i1 = paramSCCalendar.getCalendarId();
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID where CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, i1);
      int i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID where CALENDAR_ID = ?");
      if (i2 == 0) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ? AND HOLIDAY_DATE < ?");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setTimestamp(2, new Timestamp(a(paramDate).getTime().getTime()));
      DBUtil.executeUpdate(localPreparedStatement, "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ? AND HOLIDAY_DATE < ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select HOLIDAY_DATE, HOLIDAY_NAME, ACTION from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, i1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select HOLIDAY_DATE, HOLIDAY_NAME, ACTION from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
      HashMap localHashMap = a(localResultSet, LocaleUtil.getDefaultLocale());
      paramSCCalendar.setHolidays(localHashMap);
      DBUtil.closeResultSet(localResultSet);
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.updateCalendarHolidays(paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str, localThrowable2);
      jdField_if(false);
    }
  }
  
  public SCCalendar addCalendar(String paramString, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str1 = "SCAdapter.addCalendar";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str2 = paramSCCalendar.getCalendarName();
      String str3 = paramSCCalendar.getBusinessDays().getBusinessDays();
      String str4 = paramSCCalendar.getBusinessDays().getActions();
      String str5 = a(paramSCCalendar.getIgnoreForTransfersValue());
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      int i1 = DBUtil.getNextId(localConnection, this.d, "SC_CALENDAR_DEFN");
      paramSCCalendar.setCalendarId(i1);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_CALENDARDEFN( CALENDAR_ID, SERVICE_BUREAU_ID, CALENDAR_NAME, BUSINESS_DAYS, ACTIONS, IGNORE_FOR_TRANSFERS ) values( ?, ?, ?, ?, ?, ? )");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setString(2, paramString);
      localPreparedStatement.setString(3, str2);
      localPreparedStatement.setString(4, str3);
      localPreparedStatement.setString(5, str4);
      localPreparedStatement.setString(6, str5);
      int i2 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_CALENDARDEFN( CALENDAR_ID, SERVICE_BUREAU_ID, CALENDAR_NAME, BUSINESS_DAYS, ACTIONS, IGNORE_FOR_TRANSFERS ) values( ?, ?, ?, ?, ?, ? )");
      if (i2 == 0) {
        throw new SCException(10, "Unable to add the calendar to the database");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?");
      if ((localResultSet.next()) && (localResultSet.getInt(1) != 1)) {
        throw new SCException(23, "A calendar with the name '" + str2 + "' already exists");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      HashMap localHashMap = paramSCCalendar.getHolidays();
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator.next();
        SCHoliday localSCHoliday = (SCHoliday)localHashMap.get(localDateTime);
        localDateTime = localSCHoliday.getDate();
        Date localDate = a(localDateTime.getTime()).getTime();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setTimestamp(2, new Timestamp(localDate.getTime()));
        localPreparedStatement.setString(3, localSCHoliday.getName());
        char[] arrayOfChar = { localSCHoliday.getAction() };
        localPreparedStatement.setString(4, new String(arrayOfChar));
        i2 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )");
        if (i2 == 0) {
          throw new SCException(10, "Unable to add the holiday to the database");
        }
        localPreparedStatement.clearParameters();
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      if (paramSCCalendar.getIsDefault())
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setString(2, paramString);
        i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?");
        if (i2 == 0)
        {
          DBUtil.closeStatement(localPreparedStatement);
          localPreparedStatement = null;
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )");
          localPreparedStatement.setString(1, paramString);
          localPreparedStatement.setInt(2, i1);
          i2 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )");
          if (i2 != 1) {
            throw new SCException(10, "Unable to set the default calendar");
          }
        }
      }
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str1, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.addCalendar(paramString, a(paramSCCalendar));
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str1, localThrowable2);
      jdField_if(false);
    }
    return paramSCCalendar;
  }
  
  private String a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return "Y";
    }
    return "N";
  }
  
  private boolean a(String paramString)
  {
    return "Y".equals(paramString);
  }
  
  public void modifyCalendar(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str1 = "SCAdapter.modifyCalendar";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str2 = paramSCCalendar.getCalendarName();
      String str3 = null;
      int i1 = paramSCCalendar.getCalendarId();
      String str4 = a(paramSCCalendar.getIgnoreForTransfersValue());
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      SCBusinessDays localSCBusinessDays = paramSCCalendar.getBusinessDays();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set CALENDAR_NAME = ?, BUSINESS_DAYS = ?, ACTIONS = ?,  IGNORE_FOR_TRANSFERS = ? WHERE CALENDAR_ID = ?");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, localSCBusinessDays.getBusinessDays());
      localPreparedStatement.setString(3, localSCBusinessDays.getActions());
      localPreparedStatement.setString(4, str4);
      localPreparedStatement.setInt(5, i1);
      int i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set CALENDAR_NAME = ?, BUSINESS_DAYS = ?, ACTIONS = ?,  IGNORE_FOR_TRANSFERS = ? WHERE CALENDAR_ID = ?");
      if (i2 == 0) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select SERVICE_BUREAU_ID from SC_CALENDARDEFN WHERE CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, i1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select SERVICE_BUREAU_ID from SC_CALENDARDEFN WHERE CALENDAR_ID = ?");
      if (localResultSet.next()) {
        str3 = localResultSet.getString(1);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?");
      if ((localResultSet.next()) && (localResultSet.getInt(1) != 1)) {
        throw new SCException(23, "A calendar with the name '" + str2 + "' already exists");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, i1);
      DBUtil.executeUpdate(localPreparedStatement, "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      HashMap localHashMap = paramSCCalendar.getHolidays();
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator.next();
        SCHoliday localSCHoliday = (SCHoliday)localHashMap.get(localDateTime);
        localDateTime = localSCHoliday.getDate();
        Date localDate = a(localDateTime.getTime()).getTime();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setTimestamp(2, new Timestamp(localDate.getTime()));
        localPreparedStatement.setString(3, localSCHoliday.getName());
        char[] arrayOfChar = { localSCHoliday.getAction() };
        localPreparedStatement.setString(4, new String(arrayOfChar));
        i2 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )");
        if (i2 == 0) {
          throw new SCException(10, "Unable to add the holiday to the database");
        }
        localPreparedStatement.clearParameters();
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      if (paramSCCalendar.getIsDefault())
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setString(2, str3);
        i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?");
        if (i2 == 0)
        {
          DBUtil.closeStatement(localPreparedStatement);
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )");
          localPreparedStatement.setString(1, str3);
          localPreparedStatement.setInt(2, i1);
          i2 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )");
          if (i2 != 1) {
            throw new SCException(10, "Unable to set the default calendar");
          }
        }
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_DEFAULT where CALENDAR_ID = ?");
        localPreparedStatement.setInt(1, i1);
        DBUtil.executeUpdate(localPreparedStatement, "delete from SC_DEFAULT where CALENDAR_ID = ?");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str1, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      try
      {
        returnDBConnection(localConnection);
      }
      catch (Exception localException2) {}
    }
    try
    {
      this.jdField_case.updateCalendar(paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str1, localThrowable2);
      jdField_if(false);
    }
  }
  
  public void modifyCalendarName(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str1 = "SCAdapter.modifyCalendarName";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str2 = paramSCCalendar.getCalendarName();
      String str3 = null;
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set CALENDAR_NAME = ? WHERE CALENDAR_ID = ?");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setInt(2, paramSCCalendar.getCalendarId());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set CALENDAR_NAME = ? WHERE CALENDAR_ID = ?");
      if (i1 == 0) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select SERVICE_BUREAU_ID from SC_CALENDARDEFN WHERE CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, paramSCCalendar.getCalendarId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select SERVICE_BUREAU_ID from SC_CALENDARDEFN WHERE CALENDAR_ID = ?");
      if (localResultSet.next()) {
        str3 = localResultSet.getString(1);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from SC_CALENDARDEFN where CALENDAR_NAME = ? AND SERVICE_BUREAU_ID = ?");
      if ((localResultSet.next()) && (localResultSet.getInt(1) != 1)) {
        throw new SCException(23, "A calendar with the name '" + str2 + "' already exists");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str1, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.updateCalendarName(paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str1, localThrowable2);
      jdField_if(false);
    }
  }
  
  public void modifyCalendarBusinessDays(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SCAdapter.modifyCalendarBusinessDays";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      SCBusinessDays localSCBusinessDays = paramSCCalendar.getBusinessDays();
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set BUSINESS_DAYS = ?, ACTIONS = ? WHERE CALENDAR_ID = ?");
      localPreparedStatement.setString(1, localSCBusinessDays.getBusinessDays());
      localPreparedStatement.setString(2, localSCBusinessDays.getActions());
      localPreparedStatement.setInt(3, paramSCCalendar.getCalendarId());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set BUSINESS_DAYS = ?, ACTIONS = ? WHERE CALENDAR_ID = ?");
      if (i1 == 0) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.updateCalendarBusinessDays(paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str, localThrowable2);
      jdField_if(false);
    }
  }
  
  public void modifyCalendarHolidays(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SCAdapter.modifyCalendarHolidays";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      int i1 = paramSCCalendar.getCalendarId();
      HashMap localHashMap = paramSCCalendar.getHolidays();
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID where CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, i1);
      int i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID where CALENDAR_ID = ?");
      if (i2 == 0) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
      localPreparedStatement.setInt(1, i1);
      DBUtil.executeUpdate(localPreparedStatement, "delete from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      Iterator localIterator = localHashMap.keySet().iterator();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )");
      while (localIterator.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator.next();
        SCHoliday localSCHoliday = (SCHoliday)localHashMap.get(localDateTime);
        localDateTime = localSCHoliday.getDate();
        Date localDate = a(localDateTime.getTime()).getTime();
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setTimestamp(2, new Timestamp(localDate.getTime()));
        localPreparedStatement.setString(3, localSCHoliday.getName());
        char[] arrayOfChar = { localSCHoliday.getAction() };
        localPreparedStatement.setString(4, new String(arrayOfChar));
        int i3 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_HOLIDAYDEFN( CALENDAR_ID, HOLIDAY_DATE, HOLIDAY_NAME, ACTION ) values( ?, ?, ?, ? )");
        if (i3 == 0) {
          throw new SCException(10, "Unable to add the holiday to the database");
        }
        localPreparedStatement.clearParameters();
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.updateCalendarHolidays(paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str, localThrowable2);
      jdField_if(false);
    }
  }
  
  public void deleteCalendar(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SCAdapter.deleteCalendar";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    try
    {
      int i1 = paramSCCalendar.getCalendarId();
      HashMap localHashMap = paramSCCalendar.getHolidays();
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_CALENDARDEFN where CALENDAR_ID =?");
      localPreparedStatement.setInt(1, i1);
      int i2 = DBUtil.executeUpdate(localPreparedStatement, "delete from SC_CALENDARDEFN where CALENDAR_ID =?");
      if (i2 != 1) {
        throw new SCException(20, "The specified calendar id is invalid");
      }
      if (this.jdField_case.isCalendarInUse(paramSCCalendar)) {
        throw new SCException(24, "The specified calendar cannot be deleted because it is either associated with a bank or it is the default calendar for the service bureau");
      }
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.removeCalendar(paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str, localThrowable2);
      jdField_if(false);
    }
  }
  
  public boolean isCalendarInUse(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    return this.jdField_case.isCalendarInUse(paramSCCalendar);
  }
  
  public SCCalendar getCalendar(int paramInt, HashMap paramHashMap)
    throws SCException
  {
    return a(this.jdField_case.getCalendar(paramInt));
  }
  
  public SCCalendar getCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException
  {
    return a(a(paramString, paramBankIdentifier));
  }
  
  public SCCalendar resolveCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException
  {
    return a(jdField_if(paramString, paramBankIdentifier));
  }
  
  public SCCalendar getCalendarForName(String paramString1, String paramString2, HashMap paramHashMap)
    throws SCException
  {
    return a(this.jdField_case.getCalendarForName(paramString1, paramString2));
  }
  
  public SCCalendars getCalendars(String paramString, HashMap paramHashMap)
    throws SCException
  {
    SCCalendars localSCCalendars = a(this.jdField_case.getCalendars(paramString));
    if ((localSCCalendars == null) || (localSCCalendars.size() == 0)) {
      return null;
    }
    return localSCCalendars;
  }
  
  public void setCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SCAdapter.setCalendarForBank";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    try
    {
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      if (paramSCCalendar == null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_CALENDARASSOC WHERE BANK_DIR_TYPE = ? AND BANK_DIR_ID = ? AND SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setString(1, paramBankIdentifier.getBankDirectoryType());
        localPreparedStatement.setString(2, paramBankIdentifier.getBankDirectoryId());
        localPreparedStatement.setString(3, paramString);
        DBUtil.executeUpdate(localPreparedStatement, "delete from SC_CALENDARASSOC WHERE BANK_DIR_TYPE = ? AND BANK_DIR_ID = ? AND SERVICE_BUREAU_ID = ?");
      }
      else
      {
        int i1 = paramSCCalendar.getCalendarId();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID WHERE CALENDAR_ID = ? AND SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setString(2, paramString);
        int i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID WHERE CALENDAR_ID = ? AND SERVICE_BUREAU_ID = ?");
        if (i2 == 0) {
          throw new SCException(21, "The specified calendar id is invalid or does not belong to the specified service bureau");
        }
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARASSOC set CALENDAR_ID = ? WHERE BANK_DIR_TYPE = ? AND BANK_DIR_ID = ? AND SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setString(2, paramBankIdentifier.getBankDirectoryType());
        localPreparedStatement.setString(3, paramBankIdentifier.getBankDirectoryId());
        localPreparedStatement.setString(4, paramString);
        i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARASSOC set CALENDAR_ID = ? WHERE BANK_DIR_TYPE = ? AND BANK_DIR_ID = ? AND SERVICE_BUREAU_ID = ?");
        if (i2 == 0)
        {
          DBUtil.closeStatement(localPreparedStatement);
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_CALENDARASSOC( BANK_DIR_TYPE, BANK_DIR_ID, SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ?, ?, ? )");
          localPreparedStatement.setString(1, paramBankIdentifier.getBankDirectoryType());
          localPreparedStatement.setString(2, paramBankIdentifier.getBankDirectoryId());
          localPreparedStatement.setString(3, paramString);
          localPreparedStatement.setInt(4, i1);
          i2 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_CALENDARASSOC( BANK_DIR_TYPE, BANK_DIR_ID, SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ?, ?, ? )");
          if (i2 != 1) {
            throw new SCException(10, "Unable to add the calendar association to the database");
          }
        }
      }
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.setCalendarForBank(paramString, paramBankIdentifier, paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str, localThrowable2);
      jdField_if(false);
    }
  }
  
  public void setDefaultCalendar(String paramString, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException
  {
    String str = "SCAdapter.setDefaultCalendar";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    try
    {
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      if (paramSCCalendar == null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from SC_DEFAULT where SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setString(1, paramString);
        DBUtil.executeUpdate(localPreparedStatement, "delete from SC_DEFAULT where SERVICE_BUREAU_ID = ?");
      }
      else
      {
        int i1 = paramSCCalendar.getCalendarId();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID WHERE CALENDAR_ID = ? AND SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setString(2, paramString);
        int i2 = DBUtil.executeUpdate(localPreparedStatement, "update SC_CALENDARDEFN set CALENDAR_ID = CALENDAR_ID WHERE CALENDAR_ID = ? AND SERVICE_BUREAU_ID = ?");
        if (i2 == 0) {
          throw new SCException(21, "The specified calendar id is invalid or does not belong to the specified service bureau");
        }
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setString(2, paramString);
        int i3 = DBUtil.executeUpdate(localPreparedStatement, "update SC_DEFAULT set CALENDAR_ID = ? where SERVICE_BUREAU_ID = ?");
        if (i3 == 0)
        {
          DBUtil.closeStatement(localPreparedStatement);
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )");
          localPreparedStatement.setString(1, paramString);
          localPreparedStatement.setInt(2, i1);
          i3 = DBUtil.executeUpdate(localPreparedStatement, "insert into SC_DEFAULT( SERVICE_BUREAU_ID, CALENDAR_ID ) values( ?, ? )");
          if (i3 != 1) {
            throw new SCException(10, "Unable to set the default calendar");
          }
        }
      }
      a(localConnection);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable1)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable1);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    try
    {
      this.jdField_case.setDefaultCalendar(paramString, paramSCCalendar);
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing(str, localThrowable2);
      jdField_if(false);
    }
  }
  
  public SCCalendar getDefaultCalendar(String paramString, HashMap paramHashMap)
    throws SCException
  {
    return a(this.jdField_case.getDefaultCalendar(paramString));
  }
  
  private void jdField_if(boolean paramBoolean)
    throws SCException
  {
    String str1 = "SCAdapter.refreshCache";
    int i1 = a();
    if (paramBoolean)
    {
      int i2 = this.jdField_case.getVersion();
      if (i1 == i2) {
        return;
      }
      DebugLog.log(Level.INFO, "Refreshing Smart Calendar adapter cache");
    }
    Connection localConnection = null;
    Statement localStatement = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localConnection = getDBConnection();
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet1 = DBUtil.executeQuery(localStatement, "select CALENDAR_ID, SERVICE_BUREAU_ID, CALENDAR_NAME, BUSINESS_DAYS, ACTIONS, IGNORE_FOR_TRANSFERS from SC_CALENDARDEFN order by CALENDAR_ID");
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      HashMap localHashMap3 = new HashMap();
      HashMap localHashMap4 = new HashMap();
      HashMap localHashMap5 = new HashMap();
      Locale localLocale = LocaleUtil.getDefaultLocale();
      while (localResultSet1.next())
      {
        localObject1 = new SCCalendar(localLocale);
        int i3 = localResultSet1.getInt("calendar_id");
        ((SCCalendar)localObject1).setCalendarId(i3);
        String str2 = localResultSet1.getString("service_bureau_id");
        ((SCCalendar)localObject1).setCalendarName(localResultSet1.getString("calendar_name"));
        SCBusinessDays localSCBusinessDays = new SCBusinessDays(localLocale);
        localSCBusinessDays.setBusinessDays(localResultSet1.getString("business_days"));
        localSCBusinessDays.setActions(localResultSet1.getString("actions"));
        ((SCCalendar)localObject1).setIgnoreForTransfersValue(a(localResultSet1.getString("ignore_for_transfers")));
        ((SCCalendar)localObject1).setBusinessDays(localSCBusinessDays);
        if (localPreparedStatement1 == null) {
          localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select HOLIDAY_DATE, HOLIDAY_NAME, ACTION from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
        } else {
          localPreparedStatement1.clearParameters();
        }
        localPreparedStatement1.setInt(1, i3);
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement1, "select HOLIDAY_DATE, HOLIDAY_NAME, ACTION from SC_HOLIDAYDEFN where CALENDAR_ID = ?");
        HashMap localHashMap6 = a(localResultSet2, localLocale);
        DBUtil.closeResultSet(localResultSet2);
        ((SCCalendar)localObject1).setHolidays(localHashMap6);
        if (localPreparedStatement2 == null) {
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select SERVICE_BUREAU_ID from SC_DEFAULT where CALENDAR_ID = ?");
        } else {
          localPreparedStatement2.clearParameters();
        }
        localPreparedStatement2.setInt(1, i3);
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select SERVICE_BUREAU_ID from SC_DEFAULT where CALENDAR_ID = ?");
        boolean bool = false;
        while (localResultSet2.next())
        {
          bool = true;
          localHashMap2.put(localResultSet2.getString("service_bureau_id"), localObject1);
        }
        DBUtil.closeResultSet(localResultSet2);
        ((SCCalendar)localObject1).setIsDefault(bool);
        localHashMap1.put(new Integer(i3), localObject1);
        HashMap localHashMap7 = (HashMap)localHashMap3.get(str2);
        if (localHashMap7 == null)
        {
          localHashMap7 = new HashMap();
          localHashMap3.put(str2, localHashMap7);
        }
        localHashMap7.put(((SCCalendar)localObject1).getCalendarName(), localObject1);
        localHashMap4.put(localObject1, str2);
        HashSet localHashSet = (HashSet)localHashMap5.get(str2);
        if (localHashSet == null)
        {
          localHashSet = new HashSet();
          localHashMap5.put(str2, localHashSet);
        }
        localHashSet.add(localObject1);
      }
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeStatement(localStatement);
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet1 = DBUtil.executeQuery(localStatement, "select BANK_DIR_TYPE, BANK_DIR_ID, SERVICE_BUREAU_ID, CALENDAR_ID from SC_CALENDARASSOC");
      Object localObject1 = new HashMap();
      a(localResultSet1, localHashMap1, (HashMap)localObject1);
      DBUtil.closeResultSet(localResultSet1);
      this.jdField_case.loadData(localHashMap1, localHashMap3, localHashMap2, (HashMap)localObject1, localHashMap4, localHashMap5, i1);
    }
    catch (Throwable localThrowable)
    {
      a(str1, localThrowable);
    }
    finally
    {
      if (localStatement != null) {
        DBUtil.closeStatement(localStatement);
      }
      if (localPreparedStatement1 != null) {
        DBUtil.closeStatement(localPreparedStatement1);
      }
      if (localPreparedStatement2 != null) {
        DBUtil.closeStatement(localPreparedStatement2);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException) {}
      }
    }
  }
  
  private HashMap a(ResultSet paramResultSet, Locale paramLocale)
    throws SQLException
  {
    HashMap localHashMap = new HashMap();
    while (paramResultSet.next())
    {
      SCHoliday localSCHoliday = new SCHoliday(paramLocale);
      DateTime localDateTime = new DateTime(paramLocale);
      localDateTime.setTime(paramResultSet.getTimestamp("holiday_date"));
      localSCHoliday.setDate(localDateTime);
      localSCHoliday.setName(paramResultSet.getString("holiday_name"));
      localSCHoliday.setAction(paramResultSet.getString("action").charAt(0));
      localHashMap.put(localDateTime, localSCHoliday);
    }
    return localHashMap;
  }
  
  private void a(ResultSet paramResultSet, HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable
  {
    Locale localLocale = LocaleUtil.getDefaultLocale();
    while (paramResultSet.next())
    {
      BankIdentifier localBankIdentifier = new BankIdentifier(localLocale);
      localBankIdentifier.setBankDirectoryType(paramResultSet.getString("bank_dir_type"));
      localBankIdentifier.setBankDirectoryId(paramResultSet.getString("bank_dir_id"));
      String str = paramResultSet.getString("service_bureau_id");
      int i1 = paramResultSet.getInt("calendar_id");
      SCCalendar localSCCalendar = (SCCalendar)paramHashMap1.get(new Integer(i1));
      if (localSCCalendar == null)
      {
        DebugLog.log(Level.WARNING, "An internal error has occurred in SCAdapter.processCalendarAssociationsRS");
      }
      else
      {
        SCCalendarAssociationKey localSCCalendarAssociationKey = new SCCalendarAssociationKey(localBankIdentifier, str);
        paramHashMap2.put(localSCCalendarAssociationKey, localSCCalendar);
      }
    }
  }
  
  private int a()
    throws SCException
  {
    String str = "SCAdapter.getDBVersion";
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    int i1 = 0;
    try
    {
      localConnection = getDBConnection();
      DBUtil.beginTransaction(localConnection);
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, "select VERSION from SC_DATAVERSION");
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
      }
      else
      {
        int i2 = DBUtil.executeUpdate(localStatement, "insert into SC_DATAVERSION( VERSION ) values( 1 )");
        if (i2 != 1) {
          throw new SCException(10, "Unable to set the data version");
        }
        i1 = 1;
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      a(str, localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException1) {}
      if (localResultSet != null) {
        DBUtil.closeResultSet(localResultSet);
      }
      if (localStatement != null) {
        DBUtil.closeStatement(localStatement);
      }
      if (localConnection != null) {
        try
        {
          returnDBConnection(localConnection);
        }
        catch (Exception localException2) {}
      }
    }
    return i1;
  }
  
  private void a(Connection paramConnection)
    throws Throwable
  {
    Statement localStatement = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localStatement = DBUtil.createStatement(paramConnection);
      int i1 = DBUtil.executeUpdate(localStatement, "update SC_DATAVERSION set VERSION = VERSION");
      if (i1 == 0)
      {
        i1 = DBUtil.executeUpdate(localStatement, "insert into SC_DATAVERSION( VERSION ) values( 1 )");
        if (i1 != 1) {
          throw new SCException(10, "Unable to set the data version");
        }
      }
      else
      {
        localResultSet = DBUtil.executeQuery(localStatement, "select VERSION from SC_DATAVERSION");
        if (localResultSet.next())
        {
          int i2 = localResultSet.getInt(1);
          if (i2 == 2147483647) {
            i2 = 1;
          } else {
            i2++;
          }
          localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update SC_DATAVERSION set VERSION = ?");
          localPreparedStatement.setInt(1, i2);
          i1 = DBUtil.executeUpdate(localPreparedStatement, "update SC_DATAVERSION set VERSION = ?");
          if (i1 != 1) {
            throw new SCException(10, "Unable to set the data version");
          }
        }
        else
        {
          throw new SCException(10, "Unable to retrieve the data version");
        }
      }
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localStatement);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private SCCalendar a(String paramString, BankIdentifier paramBankIdentifier)
  {
    SCCalendarAssociationKey localSCCalendarAssociationKey = new SCCalendarAssociationKey(paramBankIdentifier, paramString);
    return this.jdField_case.getCalendar(localSCCalendarAssociationKey);
  }
  
  private SCCalendar jdField_if(String paramString, BankIdentifier paramBankIdentifier)
  {
    SCCalendarAssociationKey localSCCalendarAssociationKey = new SCCalendarAssociationKey(paramBankIdentifier, paramString);
    return this.jdField_case.resolveCalendar(localSCCalendarAssociationKey);
  }
  
  static Calendar a(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.clear(10);
    localCalendar.clear(11);
    localCalendar.clear(12);
    localCalendar.clear(13);
    localCalendar.clear(14);
    return localCalendar;
  }
  
  private boolean jdField_for(SCCalendar paramSCCalendar, Calendar paramCalendar)
    throws SCException
  {
    String str = paramSCCalendar.getBusinessDays().getBusinessDays();
    int i1 = paramCalendar.get(7);
    switch (i1)
    {
    case 1: 
      if (str.charAt(0) == 'N') {
        return false;
      }
      break;
    case 2: 
      if (str.charAt(1) == 'N') {
        return false;
      }
      break;
    case 3: 
      if (str.charAt(2) == 'N') {
        return false;
      }
      break;
    case 4: 
      if (str.charAt(3) == 'N') {
        return false;
      }
      break;
    case 5: 
      if (str.charAt(4) == 'N') {
        return false;
      }
      break;
    case 6: 
      if (str.charAt(5) == 'N') {
        return false;
      }
      break;
    case 7: 
      if (str.charAt(6) == 'N') {
        return false;
      }
      break;
    }
    return !jdField_if(paramSCCalendar, paramCalendar);
  }
  
  private boolean jdField_if(SCCalendar paramSCCalendar, Calendar paramCalendar)
    throws SCException
  {
    HashMap localHashMap = paramSCCalendar.getHolidays();
    DateTime localDateTime = new DateTime(paramCalendar, LocaleUtil.getDefaultLocale());
    return localHashMap.containsKey(localDateTime);
  }
  
  private Calendar a(SCCalendar paramSCCalendar, Calendar paramCalendar)
    throws SCException
  {
    paramCalendar = (Calendar)paramCalendar.clone();
    paramCalendar.add(5, -1);
    String str = paramSCCalendar.getBusinessDays().getBusinessDays();
    int i1 = paramCalendar.get(7);
    switch (i1)
    {
    case 1: 
      if (str.charAt(0) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    case 2: 
      if (str.charAt(1) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    case 3: 
      if (str.charAt(2) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    case 4: 
      if (str.charAt(3) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    case 5: 
      if (str.charAt(4) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    case 6: 
      if (str.charAt(5) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    case 7: 
      if (str.charAt(6) == 'N') {
        return a(paramSCCalendar, paramCalendar);
      }
      break;
    }
    HashMap localHashMap = paramSCCalendar.getHolidays();
    DateTime localDateTime = new DateTime(paramCalendar, LocaleUtil.getDefaultLocale());
    if (localHashMap.containsKey(localDateTime)) {
      return a(paramSCCalendar, paramCalendar);
    }
    return paramCalendar;
  }
  
  private Calendar a(SCCalendar paramSCCalendar, Calendar paramCalendar, int paramInt)
    throws SCException
  {
    int i1 = 1;
    int i2 = 1;
    if (paramInt < 0)
    {
      i2 = -1;
      paramInt *= -1;
    }
    paramCalendar = (Calendar)paramCalendar.clone();
    int i3 = 0;
    while (i3 < paramInt)
    {
      paramCalendar.add(5, i2);
      if (jdField_for(paramSCCalendar, paramCalendar)) {
        i3++;
      }
    }
    return paramCalendar;
  }
  
  private Calendar jdField_do(SCCalendar paramSCCalendar, Calendar paramCalendar)
    throws SCException
  {
    return a(paramSCCalendar, paramCalendar, null);
  }
  
  private Calendar a(SCCalendar paramSCCalendar, Calendar paramCalendar, HashSet paramHashSet)
    throws SCException
  {
    if (paramHashSet == null) {
      paramHashSet = new HashSet();
    }
    if (paramHashSet.contains(paramCalendar)) {
      throw new SCException(25, "The operation could not be completed because a loop has been detected in the calendar definition");
    }
    paramHashSet.add(paramCalendar.clone());
    HashMap localHashMap = paramSCCalendar.getHolidays();
    DateTime localDateTime = new DateTime(paramCalendar, LocaleUtil.getDefaultLocale());
    SCHoliday localSCHoliday = (SCHoliday)localHashMap.get(localDateTime);
    if (localSCHoliday != null) {
      return a(paramSCCalendar, a(paramCalendar, localSCHoliday.getAction()), paramHashSet);
    }
    String str1 = paramSCCalendar.getBusinessDays().getBusinessDays();
    String str2 = paramSCCalendar.getBusinessDays().getActions();
    int i1 = paramCalendar.get(7);
    switch (i1)
    {
    case 1: 
      if (str1.charAt(0) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(0)), paramHashSet);
      }
      break;
    case 2: 
      if (str1.charAt(1) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(1)), paramHashSet);
      }
      break;
    case 3: 
      if (str1.charAt(2) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(2)), paramHashSet);
      }
      break;
    case 4: 
      if (str1.charAt(3) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(3)), paramHashSet);
      }
      break;
    case 5: 
      if (str1.charAt(4) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(4)), paramHashSet);
      }
      break;
    case 6: 
      if (str1.charAt(5) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(5)), paramHashSet);
      }
      break;
    case 7: 
      if (str1.charAt(6) == 'N') {
        return a(paramSCCalendar, a(paramCalendar, str2.charAt(6)), paramHashSet);
      }
      break;
    }
    return paramCalendar;
  }
  
  public boolean getIgnoreForTransfers(String paramString, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException
  {
    SCCalendar localSCCalendar = null;
    localSCCalendar = getCalendarForBank(paramString, paramBankIdentifier, paramHashMap);
    if (localSCCalendar == null) {
      localSCCalendar = jdField_if(paramString, paramBankIdentifier);
    }
    if (localSCCalendar == null) {
      throw new SCException(22, "No calendar found");
    }
    return localSCCalendar.getIgnoreForTransfersValue();
  }
  
  private Calendar a(Calendar paramCalendar, char paramChar)
    throws SCException
  {
    if (paramChar == '+') {
      paramCalendar.add(5, 1);
    } else if (paramChar == '-') {
      paramCalendar.add(5, -1);
    } else {
      throw new SCException(26, "The action '" + paramChar + "' is not valid");
    }
    return paramCalendar;
  }
  
  private SCCalendar a(SCCalendar paramSCCalendar)
  {
    if (paramSCCalendar == null) {
      return null;
    }
    Locale localLocale = paramSCCalendar.getLocale();
    HashMap localHashMap1 = paramSCCalendar.getHolidays();
    HashMap localHashMap2 = new HashMap(localHashMap1.size());
    Iterator localIterator = localHashMap1.keySet().iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (DateTime)localIterator.next();
      localObject2 = (SCHoliday)localHashMap1.get(localObject1);
      localObject1 = ((SCHoliday)localObject2).getDate();
      localObject3 = a(((DateTime)localObject1).getTime());
      localObject1 = new DateTime((Calendar)localObject3, localLocale);
      SCHoliday localSCHoliday = new SCHoliday(localLocale);
      localSCHoliday.setDate((DateTime)localObject1);
      localSCHoliday.setName(((SCHoliday)localObject2).getName());
      localSCHoliday.setAction(((SCHoliday)localObject2).getAction());
      localHashMap2.put(localObject1, localSCHoliday);
    }
    Object localObject1 = paramSCCalendar.getBusinessDays();
    Object localObject2 = new SCBusinessDays(localLocale);
    ((SCBusinessDays)localObject2).setBusinessDays(((SCBusinessDays)localObject1).getBusinessDays());
    ((SCBusinessDays)localObject2).setActions(((SCBusinessDays)localObject1).getActions());
    Object localObject3 = new SCCalendar(localLocale);
    ((SCCalendar)localObject3).setCalendarId(paramSCCalendar.getCalendarId());
    ((SCCalendar)localObject3).setCalendarName(paramSCCalendar.getCalendarName());
    ((SCCalendar)localObject3).setBusinessDays((SCBusinessDays)localObject2);
    ((SCCalendar)localObject3).setHolidays(localHashMap2);
    ((SCCalendar)localObject3).setIsDefault(paramSCCalendar.getIsDefault());
    ((SCCalendar)localObject3).setIgnoreForTransfersValue(paramSCCalendar.getIgnoreForTransfersValue());
    return localObject3;
  }
  
  private SCCalendars a(SCCalendars paramSCCalendars)
  {
    if (paramSCCalendars == null) {
      return null;
    }
    Locale localLocale = paramSCCalendars.getLocale();
    SCCalendars localSCCalendars = new SCCalendars(localLocale);
    Iterator localIterator = paramSCCalendars.iterator();
    while (localIterator.hasNext())
    {
      SCCalendar localSCCalendar = (SCCalendar)localIterator.next();
      localSCCalendars.add(a(localSCCalendar));
    }
    return localSCCalendars;
  }
  
  protected Connection getDBConnection()
    throws SCException
  {
    try
    {
      return DBUtil.getConnection(this.jdField_else, true, 2);
    }
    catch (PoolException localPoolException)
    {
      String str = "An error occurred while obtaining a connection from the Smart Calendar database connection pool.";
      DebugLog.throwing(str, localPoolException);
      throw new SCException(11, str, localPoolException);
    }
  }
  
  protected void returnDBConnection(Connection paramConnection)
    throws SCException
  {
    try
    {
      DBUtil.returnConnectionWithException(this.jdField_else, paramConnection);
    }
    catch (PoolException localPoolException)
    {
      String str = "An error occurred while returning a connection to the Smart Calendar database connection pool.";
      DebugLog.throwing(str, localPoolException);
      throw new SCException(11, str, localPoolException);
    }
  }
  
  private void a(String paramString, Throwable paramThrowable)
    throws SCException
  {
    if ((paramThrowable instanceof SCException))
    {
      localObject = (SCException)paramThrowable;
      if ((((SCException)localObject).getErrorCode() == 10) || (((SCException)localObject).getErrorCode() == 3)) {
        DebugLog.throwing(paramString, (Throwable)localObject);
      }
      throw ((Throwable)localObject);
    }
    if ((paramThrowable instanceof SQLException))
    {
      localObject = "A database error has occurred";
      localSCException = new SCException(10, (String)localObject, paramThrowable);
      DebugLog.throwing(paramString, localSCException);
      throw localSCException;
    }
    Object localObject = "An unknown error has occurred";
    SCException localSCException = new SCException(0, (String)localObject, paramThrowable);
    DebugLog.throwing(paramString, localSCException);
    throw localSCException;
  }
  
  private class a
    extends Thread
  {
    private int a;
    
    public a(int paramInt)
    {
      this.a = paramInt;
      start();
    }
    
    public void run()
    {
      long l2;
      for (long l1 = this.a;; l1 = this.a - (System.currentTimeMillis() - l2))
      {
        try
        {
          sleep(l1);
        }
        catch (Exception localException1) {}
        l2 = System.currentTimeMillis();
        try
        {
          SCAdapter.this.jdMethod_if(true);
        }
        catch (Exception localException2) {}
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.smartcalendar.adapter.SCAdapter
 * JD-Core Version:    0.7.0.1
 */