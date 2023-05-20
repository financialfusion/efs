package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.bcreport.ServiceChargeEntries;
import com.ffusion.beans.bcreport.ServiceChargeEntry;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.efs.adapters.profile.ChargeableReportHandler;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public abstract class BaseReportServiceCharge
  implements ChargeableReportHandler
{
  private Locale jdField_do = null;
  private ServiceChargeReportCriteria jdField_new = null;
  private Connection a = null;
  private PreparedStatement jdField_int = null;
  private long jdField_if = 0L;
  private ResultSet jdField_for = null;
  
  protected BaseReportServiceCharge(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  protected void formatLogDatesForSql(StringBuffer paramStringBuffer)
  {
    boolean bool = true;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    localTimestamp1 = Profile.convertToTimestamp(getReportCriteria().getStart());
    bool = Profile.appendSQLSelectDate2(paramStringBuffer, "a.LOG_DATE", localTimestamp1, ">=", bool);
    localTimestamp2 = Profile.convertToTimestamp(getReportCriteria().getEnd());
    int i = getReportCriteria().getGracePeriodInDays();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localTimestamp2);
    localCalendar.add(5, i);
    Profile.appendSQLSelectDate2(paramStringBuffer, "a.LOG_DATE", localCalendar.getTime(), "<=", bool);
  }
  
  protected boolean isValidValue(String paramString, long paramLong)
  {
    boolean bool = true;
    if ((paramString == null) || (paramString.trim().length() == 0) || (hasValueChanged(getId(), paramLong))) {
      bool = false;
    }
    return bool;
  }
  
  public boolean anyRecords(Connection paramConnection, HashMap paramHashMap, ServiceChargeReportCriteria paramServiceChargeReportCriteria)
    throws SQLException, Exception
  {
    boolean bool = false;
    initialize(paramConnection, paramServiceChargeReportCriteria);
    executeSql(paramHashMap);
    if (getResultSet().next()) {
      bool = true;
    }
    close();
    return bool;
  }
  
  public void initialize(Connection paramConnection, ServiceChargeReportCriteria paramServiceChargeReportCriteria)
  {
    setConnection(paramConnection);
    setReportCriteria(paramServiceChargeReportCriteria);
  }
  
  public void close()
  {
    DBUtil.closeAll(getStatement(), getResultSet());
  }
  
  protected void runSql(String paramString)
    throws SQLException, Exception
  {
    setStatement(DBUtil.prepareStatement(getConnection(), paramString));
    DebugLog.log(Level.INFO, getClass().getName() + "->Executing sql: " + paramString.toString());
    setResultSet(DBUtil.executeQuery(getStatement(), paramString));
  }
  
  public static boolean hasValueChanged(long paramLong1, long paramLong2)
  {
    boolean bool = false;
    if ((paramLong1 == 0L) || (paramLong1 != paramLong2)) {
      bool = true;
    }
    return bool;
  }
  
  protected void setDimension(ReportResult paramReportResult, int paramInt, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(paramInt);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  public static void addBlankColumns(ReportResult paramReportResult, Locale paramLocale, int paramInt)
    throws RptException
  {
    addBlankColumns(paramReportResult, paramLocale, paramInt, 18);
  }
  
  public static void addBlankColumns(ReportResult paramReportResult, Locale paramLocale, int paramInt1, int paramInt2)
    throws RptException
  {
    for (int i = 0; i < paramInt1; i++) {
      addBlankColumn(paramReportResult, paramLocale, paramInt2);
    }
  }
  
  public static void addBlankColumn(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setLabels(null);
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
  }
  
  public static void addBlankColumn(ReportResult paramReportResult, Locale paramLocale, int paramInt)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setLabels(null);
    localReportColumn.setWidthAsPercent(paramInt);
    paramReportResult.addColumn(localReportColumn);
  }
  
  protected boolean maxRowLimitReached(long paramLong)
  {
    boolean bool = false;
    if ((getMaxDisplay() > 0) && (paramLong >= getMaxDisplay())) {
      bool = true;
    }
    return bool;
  }
  
  protected String getServiceCodeFromTransactionType(int paramInt, HashMap paramHashMap)
  {
    Set localSet = paramHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (((ArrayList)paramHashMap.get(str)).contains(new Integer(paramInt))) {
        return str;
      }
    }
    return "";
  }
  
  protected static ArrayList getServiceCodesFromTransactionType(int paramInt, HashMap paramHashMap)
  {
    ArrayList localArrayList = new ArrayList();
    Set localSet = paramHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (((ArrayList)paramHashMap.get(str)).contains(new Integer(paramInt))) {
        localArrayList.add(str);
      }
    }
    return localArrayList;
  }
  
  protected ServiceChargeEntry getEntryByServiceCode(ServiceChargeEntries paramServiceChargeEntries, String paramString)
  {
    for (int i = 0; i < paramServiceChargeEntries.size(); i++)
    {
      ServiceChargeEntry localServiceChargeEntry = (ServiceChargeEntry)paramServiceChargeEntries.get(i);
      if (localServiceChargeEntry.getServiceCode().equals(paramString)) {
        return localServiceChargeEntry;
      }
    }
    return null;
  }
  
  protected Locale getLocale()
  {
    return this.jdField_do;
  }
  
  protected void setLocale(Locale paramLocale)
  {
    this.jdField_do = paramLocale;
  }
  
  protected String getTransTypesDelimited(Object[] paramArrayOfObject, ChargeableReportHandler paramChargeableReportHandler, HashMap paramHashMap, String paramString)
  {
    String str1 = "ReportAuditAdapter.getTransTypesDelimited";
    String str2 = "";
    int i = 0;
    for (int j = 0; j < paramArrayOfObject.length; j++)
    {
      String str3 = (String)paramArrayOfObject[j];
      if ((str3 != null) || (!str3.equals("")))
      {
        ArrayList localArrayList = (ArrayList)paramChargeableReportHandler.getReportMap(paramHashMap).get(str3);
        if ((localArrayList != null) && (localArrayList.size() != 0)) {
          for (int k = 0; k < localArrayList.size(); k++)
          {
            str2 = str2 + ((Integer)localArrayList.get(k)).toString();
            str2 = str2 + ",";
            i = 1;
          }
        } else {
          DebugLog.log(Level.INFO, str1 + ": A chargeable operation '" + str3 + "' in service package '" + paramString + "' is not mapped to transaction type(s).");
        }
      }
    }
    if (i != 0) {
      str2 = str2.substring(0, str2.length() - 1);
    }
    if (str2.length() == 0) {
      DebugLog.log(Level.INFO, str1 + ": None of the chargeable operations in service package '" + paramString + "' were mapped to transaction type(s).");
    }
    return str2;
  }
  
  protected int getMaxDisplay()
  {
    Integer localInteger = null;
    String str = getReportCriteria().getReportCriteria().getReportOptions().getProperty("MAXDISPLAYSIZE");
    try
    {
      localInteger = Integer.valueOf(str);
    }
    catch (NumberFormatException localNumberFormatException) {}
    int i = -1;
    if (localInteger != null) {
      i = localInteger.intValue();
    }
    return i;
  }
  
  protected ServiceChargeReportCriteria getReportCriteria()
  {
    return this.jdField_new;
  }
  
  protected void setReportCriteria(ServiceChargeReportCriteria paramServiceChargeReportCriteria)
  {
    this.jdField_new = paramServiceChargeReportCriteria;
  }
  
  public Connection getConnection()
    throws SQLException
  {
    if (this.a == null) {
      throw new SQLException("Could not connect to database.  Make sure initialize is called prior to executing any SQL methods on ServiceChargeReports");
    }
    return this.a;
  }
  
  protected void setConnection(Connection paramConnection)
  {
    this.a = paramConnection;
  }
  
  protected ResultSet getResultSet()
  {
    return this.jdField_for;
  }
  
  protected void setResultSet(ResultSet paramResultSet)
  {
    this.jdField_for = paramResultSet;
  }
  
  protected PreparedStatement getStatement()
  {
    return this.jdField_int;
  }
  
  protected void setStatement(PreparedStatement paramPreparedStatement)
  {
    this.jdField_int = paramPreparedStatement;
  }
  
  public long getId()
  {
    return this.jdField_if;
  }
  
  public void setId(long paramLong)
  {
    this.jdField_if = paramLong;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.BaseReportServiceCharge
 * JD-Core Version:    0.7.0.1
 */