package com.ffusion.filemonitor.adapter;

import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportColumns;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FFIDateFormat;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class FileMonitorAdapter
{
  private static final String jdField_long = "MM/dd/yyyy HH:mm:ss";
  private static final String jdField_case = "SELECT LOGDATE, FILETYPE, HOSTNAME, FILENAME, ACTIVITYTYPE, FROMSYSTEM, TOSYSTEM, STATUS, COMMENTS FROM FM_LOG";
  private static final String jdField_do = "All";
  private static final String jdField_for = "File Type";
  private static final String jdField_char = "File Name";
  private static final String jdField_null = "Host Name";
  private static final String jdField_if = "From System";
  private static final String b = "To System";
  private static final String jdField_try = "Status";
  private static final String jdField_goto = "StartDate";
  private static final String jdField_byte = "EndDate";
  private static final String a = "Date";
  private static final String jdField_else = "File Type";
  private static final String jdField_new = "Host Name and File Name";
  private static final String jdField_void = "Status";
  private String jdField_int = null;
  
  public void initialize(HashMap paramHashMap)
    throws FMException
  {
    try
    {
      Properties localProperties = new Properties();
      localProperties.putAll(paramHashMap);
      this.jdField_int = ConnectionPool.init(localProperties);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("FileMonitorAdapter.initialize failed", localException);
      throw new FMException(localException, 2);
    }
  }
  
  protected void processReportCriteria(ReportCriteria paramReportCriteria)
  {
    Locale localLocale = paramReportCriteria.getLocale();
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties.propertyNames();
    String str1 = null;
    String str2 = null;
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();
      str2 = localProperties.getProperty(str1);
      str1 = str1.intern();
      if ((str1 == "Status") || (str1 == "File Type") || (str1 == "Date Range") || (str1 == "From System") || (str1 == "To System")) {
        paramReportCriteria.setDisplayValue(str1, ReportConsts.getCriteriaValue("File Activity Report", str1, str2, localLocale));
      }
    }
  }
  
  public IReportResult getReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException
  {
    try
    {
      
    }
    catch (FMException localFMException1)
    {
      DebugLog.log("Unable to flush FM log.");
      localObject1 = new RptException(100, "Unable to flush FM log.", localFMException1);
      throw ((Throwable)localObject1);
    }
    processReportCriteria(paramReportCriteria);
    String str1 = "FileMonitorAdapter.getReportData";
    Object localObject1 = paramReportCriteria.getLocale();
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    Object localObject2;
    if (localProperties1 == null)
    {
      DebugLog.log("The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.");
      FMException localFMException2 = new FMException(str1 + ": The report criteria used in a call to getReportData did " + "not contain a valid report options object.  This object " + "is required for report retrieval.", 3);
      localObject2 = new RptException(111, "No reportion options found with the report criteria", localFMException2);
      throw ((Throwable)localObject2);
    }
    int i = 0;
    try
    {
      localObject2 = (String)localProperties1.get("MAXDISPLAYSIZE");
      if (localObject2 != null) {
        i = Integer.parseInt((String)localObject2);
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    String str2 = localProperties1.getProperty("DATEFORMAT");
    String str3 = localProperties1.getProperty("TIMEFORMAT");
    DateFormat localDateFormat1 = null;
    DateFormat localDateFormat2 = null;
    if (str2 == null) {
      localDateFormat1 = DateFormatUtil.getFormatter(null, (Locale)localObject1);
    } else {
      localDateFormat1 = DateFormatUtil.getFormatter(str2, (Locale)localObject1);
    }
    if (str3 == null) {
      localDateFormat2 = DateFormatUtil.getFormatter(null, (Locale)localObject1);
    } else {
      localDateFormat2 = DateFormatUtil.getFormatter(str3, (Locale)localObject1);
    }
    ReportResult localReportResult = new ReportResult((Locale)localObject1);
    try
    {
      localReportResult.init(paramReportCriteria);
      localReportResult.setProperties(new HashMap());
      a(localReportResult);
      a(localReportResult, (Locale)localObject1);
    }
    catch (RptException localRptException1)
    {
      DebugLog.throwing(str1, localRptException1);
      throw localRptException1;
    }
    StringBuffer localStringBuffer = new StringBuffer("SELECT LOGDATE, FILETYPE, HOSTNAME, FILENAME, ACTIVITYTYPE, FROMSYSTEM, TOSYSTEM, STATUS, COMMENTS FROM FM_LOG");
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    ArrayList localArrayList = a(localStringBuffer, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getSearchCriteria());
    a(localStringBuffer, localReportSortCriteria);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    try
    {
      String str4 = localStringBuffer.toString();
      localStringBuffer = null;
      localConnection = DBUtil.getConnection(this.jdField_int, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str4);
      if (i >= 0) {
        localPreparedStatement.setMaxRows(i);
      }
      Object localObject3;
      Object localObject4;
      for (int j = 0; j < localArrayList.size(); j++)
      {
        localObject3 = (Date)localArrayList.get(j);
        localObject4 = new DateTime((Date)localObject3, (Locale)localObject1);
        localPreparedStatement.setTimestamp(j + 1, new Timestamp(((DateTime)localObject4).getTime().getTime()));
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str4);
      for (j = 0; localResultSet.next(); j++)
      {
        localObject3 = new FMLogRecord();
        localObject4 = localResultSet.getTimestamp(1);
        ((FMLogRecord)localObject3).setMillis(((Timestamp)localObject4).getTime());
        ((FMLogRecord)localObject3).setFileType(localResultSet.getString(2));
        ((FMLogRecord)localObject3).setHostName(localResultSet.getString(3));
        ((FMLogRecord)localObject3).setFileName(localResultSet.getString(4));
        ((FMLogRecord)localObject3).setActivityType(localResultSet.getString(5));
        ((FMLogRecord)localObject3).setFromSystem(localResultSet.getString(6));
        ((FMLogRecord)localObject3).setToSystem(localResultSet.getString(7));
        ((FMLogRecord)localObject3).setStatus(localResultSet.getString(8));
        ((FMLogRecord)localObject3).setMessage(localResultSet.getString(9));
        a(localReportResult, (FMLogRecord)localObject3, localDateFormat1, localDateFormat2, j + 1);
      }
      if ((i > 0) && (j >= i))
      {
        localObject3 = localReportResult.getProperties();
        ((HashMap)localObject3).put("TRUNCATED", new Integer(i));
      }
    }
    catch (PoolException localPoolException)
    {
      localReportResult.setError(localPoolException);
      DebugLog.throwing(str1, localPoolException);
      localRptException2 = new RptException(100, "Unable to retrieve report data from database", localPoolException);
      throw localRptException2;
    }
    catch (SQLException localSQLException)
    {
      localReportResult.setError(localSQLException);
      DebugLog.throwing(str1, localSQLException);
      localRptException2 = new RptException(5, "Unable to retrieve report data from database", localSQLException);
      throw localRptException2;
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      DebugLog.throwing(str1, localException);
      RptException localRptException2 = new RptException(100, "Unable to retrieve report data from database", localException);
      throw localRptException2;
    }
    finally
    {
      DBUtil.closeAll(this.jdField_int, localConnection, localPreparedStatement, localResultSet);
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException3)
      {
        DebugLog.throwing(str1, localRptException3);
        throw localRptException3;
      }
    }
    return localReportResult;
  }
  
  private static Locale a(HashMap paramHashMap)
  {
    Locale localLocale = (Locale)paramHashMap.get("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    return localLocale;
  }
  
  private static ArrayList a(StringBuffer paramStringBuffer, String paramString, Properties paramProperties)
    throws RptException
  {
    ArrayList localArrayList = new ArrayList(2);
    if ((paramProperties == null) || (paramProperties.isEmpty())) {
      return localArrayList;
    }
    if (paramString == null) {
      paramString = "MM/dd/yyyy HH:mm:ss";
    }
    FFIDateFormat localFFIDateFormat = (FFIDateFormat)DateFormatUtil.getFormatter(paramString);
    int i = 0;
    String str1 = null;
    str1 = paramProperties.getProperty("File Type");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equalsIgnoreCase("All")))
    {
      paramStringBuffer.append(" WHERE ");
      i = 1;
      paramStringBuffer.append("FILETYPE='");
      paramStringBuffer.append(DBUtil.escapeSQLStringLiteral(str1));
      paramStringBuffer.append("'");
    }
    str1 = paramProperties.getProperty("File Name");
    if ((str1 != null) && (str1.length() > 0))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      paramStringBuffer.append("FILENAME='");
      paramStringBuffer.append(DBUtil.escapeSQLStringLiteral(str1));
      paramStringBuffer.append("'");
    }
    str1 = paramProperties.getProperty("Host Name");
    if ((str1 != null) && (str1.length() > 0))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      paramStringBuffer.append("HOSTNAME='");
      paramStringBuffer.append(DBUtil.escapeSQLStringLiteral(str1));
      paramStringBuffer.append("'");
    }
    str1 = paramProperties.getProperty("From System");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equalsIgnoreCase("All")))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      paramStringBuffer.append("FROMSYSTEM='");
      paramStringBuffer.append(DBUtil.escapeSQLStringLiteral(str1));
      paramStringBuffer.append("'");
    }
    str1 = paramProperties.getProperty("To System");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equalsIgnoreCase("All")))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      paramStringBuffer.append("TOSYSTEM='");
      paramStringBuffer.append(DBUtil.escapeSQLStringLiteral(str1));
      paramStringBuffer.append("'");
    }
    str1 = paramProperties.getProperty("Status");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equalsIgnoreCase("ALLSTATUSES")))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      paramStringBuffer.append("STATUS='");
      paramStringBuffer.append(DBUtil.escapeSQLStringLiteral(str1));
      paramStringBuffer.append("'");
    }
    str1 = paramProperties.getProperty("StartDate");
    String str2;
    if ((str1 != null) && (str1.length() > 0) && (!str1.equalsIgnoreCase(localFFIDateFormat.toPattern())))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      try
      {
        Date localDate1 = localFFIDateFormat.parse(str1);
        paramStringBuffer.append("LOGDATE>=?");
        localArrayList.add(localDate1);
      }
      catch (ParseException localParseException1)
      {
        str2 = "Error when retrieving report data from database: The criterion value for 'StartDate is not in " + paramString + " format";
        DebugLog.throwing(str2, localParseException1);
        throw new RptException(113, str2, localParseException1);
      }
    }
    str1 = paramProperties.getProperty("EndDate");
    if ((str1 != null) && (str1.length() > 0) && (!str1.equalsIgnoreCase(localFFIDateFormat.toPattern())))
    {
      if (i != 0)
      {
        paramStringBuffer.append(" AND ");
      }
      else
      {
        paramStringBuffer.append(" WHERE ");
        i = 1;
      }
      try
      {
        Date localDate2 = localFFIDateFormat.parse(str1);
        paramStringBuffer.append("LOGDATE<=?");
        localArrayList.add(localDate2);
      }
      catch (ParseException localParseException2)
      {
        str2 = "Error when retrieving report data from database: The criterion value for 'EndDate is not in " + paramString + " format";
        DebugLog.throwing(str2, localParseException2);
        throw new RptException(114, str2, localParseException2);
      }
    }
    return localArrayList;
  }
  
  private static void a(StringBuffer paramStringBuffer, ReportSortCriteria paramReportSortCriteria)
  {
    if ((paramReportSortCriteria == null) || (paramReportSortCriteria.isEmpty()))
    {
      paramStringBuffer.append(" ORDER BY LOGDATE ASC");
      return;
    }
    int i = 0;
    Object localObject = null;
    paramReportSortCriteria.setSortedBy("ORDINAL");
    for (int j = 0; j < paramReportSortCriteria.size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(j);
      String str1 = localReportSortCriterion.getName();
      if (!str1.equals(localObject))
      {
        String str2 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
        if (str1.equals("Date"))
        {
          if (i != 0)
          {
            paramStringBuffer.append(", LOGDATE ");
          }
          else
          {
            paramStringBuffer.append(" ORDER BY  LOGDATE ");
            localObject = str1;
            i = 1;
          }
          paramStringBuffer.append(str2);
        }
        else if (str1.equals("File Type"))
        {
          if (i != 0)
          {
            paramStringBuffer.append(", FILETYPE ");
          }
          else
          {
            paramStringBuffer.append(" ORDER BY  FILETYPE ");
            localObject = str1;
            i = 1;
          }
          paramStringBuffer.append(str2);
        }
        else if (str1.equals("Host Name and File Name"))
        {
          if (i != 0)
          {
            paramStringBuffer.append(", HOSTNAME ");
          }
          else
          {
            paramStringBuffer.append(" ORDER BY  HOSTNAME ");
            localObject = str1;
            i = 1;
          }
          paramStringBuffer.append(str2);
          paramStringBuffer.append(", FILENAME ");
          paramStringBuffer.append(str2);
        }
        else if (str1.equals("Status"))
        {
          if (i != 0)
          {
            paramStringBuffer.append(", STATUS ");
          }
          else
          {
            paramStringBuffer.append(" ORDER BY  STATUS ");
            localObject = str1;
            i = 1;
          }
        }
      }
    }
  }
  
  private static void a(ReportResult paramReportResult)
    throws RptException
  {
    ReportHeading localReportHeading = new ReportHeading(paramReportResult.getLocale());
    localReportHeading.setLabel(ReportConsts.getText(800, paramReportResult.getLocale()));
    localReportHeading.setJustification("LEFT");
    paramReportResult.setHeading(localReportHeading);
  }
  
  private static void a(ReportResult paramReportResult, FMLogRecord paramFMLogRecord, DateFormat paramDateFormat1, DateFormat paramDateFormat2, int paramInt)
    throws RptException
  {
    ReportRow localReportRow = new ReportRow(paramReportResult.getLocale());
    if (paramInt % 2 == 0) {
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    }
    ReportDataItems localReportDataItems = new ReportDataItems(paramReportResult.getLocale());
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    Date localDate = new Date(paramFMLogRecord.getMillis());
    localReportDataItem.setData(paramDateFormat1.format(localDate));
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramDateFormat2.format(localDate));
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getFileType());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getFileName());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getHostName());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getActivityType());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getFromSystem());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getToSystem());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getStatus());
    localReportDataItems.add(localReportDataItem);
    localReportDataItem = new ReportDataItem(paramReportResult.getLocale());
    localReportDataItem.setData(paramFMLogRecord.getMessage());
    localReportDataItems.add(localReportDataItem);
    paramReportResult.addRow(localReportRow);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramReportResult.getLocale());
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(10);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumns localReportColumns = new ReportColumns(paramReportResult.getLocale());
    ReportColumn localReportColumn = new ReportColumn(paramReportResult.getLocale());
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(800, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(7);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(809, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(7);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(801, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(802, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(803, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(804, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(805, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(806, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(807, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(808, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.filemonitor.adapter.FileMonitorAdapter
 * JD-Core Version:    0.7.0.1
 */