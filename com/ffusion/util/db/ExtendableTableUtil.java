package com.ffusion.util.db;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

public class ExtendableTableUtil
{
  protected static HashMap extTableData = null;
  protected static String poolName = null;
  protected static String dbCatalog = null;
  protected static String dbSchema = null;
  protected static String dbType = null;
  protected static Properties properties = null;
  
  public static void isInitialized()
    throws Exception
  {
    if ((poolName == null) || (poolName.length() == 0)) {
      throw new Exception("The ExtendableTableUtil must be initialized prior to use.");
    }
  }
  
  public static synchronized void initialize(Properties paramProperties)
    throws Exception
  {
    String str = "ExtenableTableUtil.initialize";
    if ((poolName != null) && (poolName.length() > 0)) {
      return;
    }
    try
    {
      properties = paramProperties;
      poolName = ConnectionPool.init(properties);
      processInitArgs(properties);
      loadExtTableData();
    }
    catch (Exception localException)
    {
      extTableData = null;
      throw localException;
    }
  }
  
  protected static void processInitArgs(Properties paramProperties)
    throws Exception
  {
    dbType = paramProperties.getProperty("ConnectionType");
    if (dbType == null) {
      throw new Exception("DB type unknown");
    }
    a(paramProperties);
  }
  
  public static void shutDown(Properties paramProperties)
    throws Exception
  {
    try
    {
      ConnectionPool.releasePool(poolName);
      poolName = null;
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      poolName = null;
    }
  }
  
  protected static void loadExtTableData()
    throws Exception
  {
    Connection localConnection = null;
    if (extTableData == null) {
      extTableData = new HashMap(10);
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      DatabaseMetaData localDatabaseMetaData = localConnection.getMetaData();
      for (int i = 0; i < ExtendableTableDefines.EXTENDABLE_TABLES.length; i++) {
        loadExtTableData(localDatabaseMetaData, ExtendableTableDefines.EXTENDABLE_TABLES[i], ExtendableTableDefines.EXTENDABLE_TABLE_COLUMNS[i]);
      }
    }
    finally
    {
      if (localConnection != null) {
        DBUtil.returnConnection(poolName, localConnection);
      }
    }
  }
  
  protected static void loadExtTableData(DatabaseMetaData paramDatabaseMetaData, String paramString, String[] paramArrayOfString)
    throws Exception
  {
    ResultSet localResultSet = null;
    try
    {
      if (extTableData.get(paramString) == null)
      {
        if ("ASE".equalsIgnoreCase(dbType)) {
          localResultSet = paramDatabaseMetaData.getColumns(dbCatalog, dbSchema, paramString, "%");
        } else {
          localResultSet = paramDatabaseMetaData.getColumns(dbCatalog.toUpperCase(), dbSchema.toUpperCase(), paramString.toUpperCase(), "%");
        }
        TableInfo localTableInfo = new TableInfo();
        while (localResultSet.next())
        {
          String str = localResultSet.getString("COLUMN_NAME");
          if (!"ASE".equalsIgnoreCase(dbType)) {
            str = str.toUpperCase();
          }
          if (!isBaseColumn(str, paramArrayOfString)) {
            localTableInfo.add(str, localResultSet.getInt("DATA_TYPE"));
          }
        }
        if (localTableInfo.size() > 0) {
          extTableData.put(paramString, localTableInfo);
        }
      }
    }
    finally
    {
      if (localResultSet != null) {
        localResultSet.close();
      }
    }
  }
  
  public static String getPoolName()
  {
    return poolName;
  }
  
  protected static boolean isBaseColumn(String paramString, String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i])) {
        return true;
      }
    }
    return false;
  }
  
  private static void a(Properties paramProperties)
    throws Exception
  {
    dbCatalog = paramProperties.getProperty("Catalog");
    dbSchema = paramProperties.getProperty("Schema");
    if (dbCatalog == null) {
      if ((dbType.equalsIgnoreCase("ORACLE:THIN")) || (dbType.equalsIgnoreCase("ORACLE:OCI8")) || (dbType.equalsIgnoreCase("DB2:APP")) || (dbType.equalsIgnoreCase("DB2:UN2")) || (dbType.equalsIgnoreCase("DB2:NET")) || (dbType.equalsIgnoreCase("DB2:AS390")) || (dbType.equalsIgnoreCase("MSSQL:THIN"))) {
        dbCatalog = "";
      } else {
        dbCatalog = paramProperties.getProperty("DBName");
      }
    }
    if (dbSchema == null) {
      if (("ASE".equalsIgnoreCase(dbType)) || ("MSSQL:THIN".equalsIgnoreCase(dbType))) {
        dbSchema = null;
      } else {
        dbSchema = paramProperties.getProperty("User");
      }
    }
  }
  
  public static void appendSQLInsertColumns(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
  {
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      for (int i = 0; i < localTableInfo.size(); i++) {
        paramStringBuffer.append("," + localTableInfo.getName(i));
      }
    }
    if (paramBoolean)
    {
      paramStringBuffer.append(") values (");
      a(paramStringBuffer);
    }
  }
  
  public static void appendSQLUpdateColumns(StringBuffer paramStringBuffer, String paramString)
  {
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      for (int i = 0; i < localTableInfo.size(); i++)
      {
        String str = localTableInfo.getName(i);
        paramStringBuffer.append(", " + str + " = ?");
      }
    }
  }
  
  public static void appendSQLSelectColumns(StringBuffer paramStringBuffer, String paramString)
  {
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      for (int i = 0; i < localTableInfo.size(); i++) {
        paramStringBuffer.append("," + localTableInfo.getName(i));
      }
    }
  }
  
  public static void setSqlValue(StringBuffer paramStringBuffer, Object paramObject, String paramString, int paramInt)
    throws Exception
  {
    switch (paramInt)
    {
    case 91: 
    case 93: 
      java.util.Date localDate = null;
      if ((paramObject instanceof String)) {
        localDate = convertDate((String)paramObject);
      } else if ((paramObject instanceof DateTime)) {
        localDate = ((DateTime)paramObject).getTime();
      } else if ((paramObject instanceof java.util.Date)) {
        localDate = (java.util.Date)paramObject;
      } else {
        throw new Exception("setSqlValue: invalid date/time");
      }
      paramStringBuffer.append(fixDate(localDate));
      break;
    case 2: 
    case 3: 
    case 4: 
    case 6: 
    case 8: 
      String str = (String)paramObject;
      paramStringBuffer.append(str);
      break;
    case 1: 
    case 12: 
      paramStringBuffer.append("'" + (String)paramObject + "'");
      break;
    default: 
      throw new Exception("setSqlValue: unsupported data type");
    }
  }
  
  private static void a(StringBuffer paramStringBuffer)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramStringBuffer.toString(), ",");
    int i = localStringTokenizer.countTokens();
    for (int j = 0; j < i; j++)
    {
      paramStringBuffer.append("?");
      if (j < i - 1) {
        paramStringBuffer.append(",");
      }
    }
    paramStringBuffer.append(")");
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean)
    throws Exception
  {
    return setStatementString(paramPreparedStatement, paramInt, paramString, false, false, paramBoolean, true);
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    return setStatementString(paramPreparedStatement, paramInt, paramString, paramBoolean1, false, paramBoolean2, true);
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    return setStatementString(paramPreparedStatement, paramInt, paramString, paramBoolean1, paramBoolean2, paramBoolean3, true);
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    throws Exception
  {
    if ((!paramBoolean3) || ((paramString != null) && (paramString.length() > 0))) {
      if (paramString == null)
      {
        paramPreparedStatement.setNull(paramInt++, 12);
      }
      else if (paramString.length() == 0)
      {
        paramPreparedStatement.setString(paramInt++, paramString);
      }
      else
      {
        StringTokenizer localStringTokenizer = null;
        if (!paramBoolean4) {
          localStringTokenizer = new StringTokenizer(paramString, ",");
        } else {
          localStringTokenizer = new StringTokenizer(paramString, "");
        }
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken();
          if ((str != null) && (str.length() > 0))
          {
            if (paramBoolean2) {
              str = str.toLowerCase();
            }
            if (paramBoolean1) {
              str = str + "%";
            }
            paramPreparedStatement.setString(paramInt++, str);
          }
        }
      }
    }
    return paramInt;
  }
  
  public static int setStatementInt(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, boolean paramBoolean)
    throws Exception
  {
    if ((!paramBoolean) || (paramInt2 > 0)) {
      paramPreparedStatement.setInt(paramInt1++, paramInt2);
    }
    return paramInt1;
  }
  
  public static int setStatementInt(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean)
    throws Exception
  {
    if ((!paramBoolean) || ((paramString != null) && (paramString.length() > 0))) {
      if (paramString == null)
      {
        paramPreparedStatement.setInt(paramInt++, 0);
      }
      else
      {
        StringTokenizer localStringTokenizer = null;
        localStringTokenizer = new StringTokenizer(paramString, ",");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken();
          if (isValidInt(str)) {
            paramPreparedStatement.setInt(paramInt++, convertToInt(str));
          }
        }
      }
    }
    return paramInt;
  }
  
  public static int setStatementDate(PreparedStatement paramPreparedStatement, int paramInt, Object paramObject, boolean paramBoolean)
    throws Exception
  {
    if ((!paramBoolean) || (paramObject != null)) {
      if ((paramObject instanceof java.sql.Date)) {
        paramPreparedStatement.setDate(paramInt++, (java.sql.Date)paramObject);
      } else {
        paramPreparedStatement.setTimestamp(paramInt++, (Timestamp)paramObject);
      }
    }
    return paramInt;
  }
  
  public static int setStatementBigDecimal(PreparedStatement paramPreparedStatement, int paramInt, BigDecimal paramBigDecimal, boolean paramBoolean)
    throws Exception
  {
    if ((!paramBoolean) || (paramBigDecimal != null)) {
      paramPreparedStatement.setBigDecimal(paramInt++, paramBigDecimal);
    }
    return paramInt;
  }
  
  public static int setStatementValues(PreparedStatement paramPreparedStatement, int paramInt, ExtendABean paramExtendABean, String paramString, boolean paramBoolean)
    throws Exception
  {
    int i = paramInt;
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      for (int j = 0; j < localTableInfo.size(); j++) {
        i = setStatementValue(paramPreparedStatement, i, paramExtendABean.get(localTableInfo.getName(j)), localTableInfo.getName(j), localTableInfo.getType(j), paramBoolean);
      }
    }
    return i;
  }
  
  public static int setStatementValue(PreparedStatement paramPreparedStatement, int paramInt1, Object paramObject, String paramString, int paramInt2, boolean paramBoolean)
    throws Exception
  {
    if ((paramBoolean == true) && (paramObject == null)) {
      return paramInt1;
    }
    if ((!paramBoolean) && (paramObject == null))
    {
      paramPreparedStatement.setNull(paramInt1, paramInt2);
    }
    else
    {
      String str;
      switch (paramInt2)
      {
      case 91: 
      case 93: 
        java.util.Date localDate = null;
        if ((paramObject instanceof String)) {
          localDate = convertDate((String)paramObject);
        } else if ((paramObject instanceof DateTime)) {
          localDate = ((DateTime)paramObject).getTime();
        } else if ((paramObject instanceof java.util.Date)) {
          localDate = (java.util.Date)paramObject;
        } else {
          throw new Exception("setStatementValue: invalid date/time");
        }
        if (paramInt2 == 93) {
          paramPreparedStatement.setTimestamp(paramInt1, new Timestamp(localDate.getTime()));
        } else {
          paramPreparedStatement.setDate(paramInt1, new java.sql.Date(localDate.getTime()));
        }
        break;
      case 4: 
        str = (String)paramObject;
        int i = 0;
        if (str != null) {
          i = Integer.parseInt(str);
        }
        paramPreparedStatement.setInt(paramInt1, i);
        break;
      case 2: 
      case 3: 
        BigDecimal localBigDecimal = null;
        str = (String)paramObject;
        if ((paramObject instanceof String))
        {
          if (str != null) {
            localBigDecimal = new BigDecimal(str);
          }
        }
        else if ((paramObject instanceof BigDecimal))
        {
          if (paramObject != null) {
            localBigDecimal = (BigDecimal)paramObject;
          }
        }
        else if (((paramObject instanceof Double)) && (paramObject != null)) {
          localBigDecimal = new BigDecimal(((Double)paramObject).doubleValue());
        }
        break;
      case 6: 
        str = (String)paramObject;
        Float localFloat = new Float(0.0D);
        if (str != null) {
          localFloat = new Float(str);
        }
        paramPreparedStatement.setFloat(paramInt1, localFloat.floatValue());
        break;
      case 8: 
        str = (String)paramObject;
        Double localDouble = new Double(0.0D);
        if (str != null) {
          localDouble = new Double(str);
        }
        paramPreparedStatement.setDouble(paramInt1, localDouble.doubleValue());
        break;
      case 1: 
      case 12: 
        paramPreparedStatement.setString(paramInt1, (String)paramObject);
        break;
      default: 
        throw new Exception("setStatementValue: unsupported data type");
      }
    }
    paramInt1++;
    return paramInt1;
  }
  
  public static void setXBeanFields(ResultSet paramResultSet, ExtendABean paramExtendABean, String paramString)
  {
    if (extTableData.get(paramString) == null) {
      return;
    }
    TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
    for (int i = 0; i < localTableInfo.size(); i++) {
      try
      {
        a(paramResultSet, paramExtendABean, localTableInfo.getName(i), localTableInfo.getType(i));
      }
      catch (Exception localException) {}
    }
  }
  
  private static void a(ResultSet paramResultSet, ExtendABean paramExtendABean, String paramString, int paramInt)
    throws Exception
  {
    switch (paramInt)
    {
    case 91: 
    case 93: 
      Timestamp localTimestamp = paramResultSet.getTimestamp(paramString);
      DateTime localDateTime = new DateTime(new java.util.Date(localTimestamp.getTime()), null);
      paramExtendABean.put(paramString, localDateTime);
      break;
    case 4: 
      int i = paramResultSet.getInt(paramString);
      paramExtendABean.put(paramString, new Integer(i));
      break;
    case 2: 
    case 3: 
      BigDecimal localBigDecimal = paramResultSet.getBigDecimal(paramString);
      paramExtendABean.put(paramString, localBigDecimal);
      break;
    case 6: 
      float f = paramResultSet.getFloat(paramString);
      paramExtendABean.put(paramString, new Float(f));
      break;
    case 8: 
      double d = paramResultSet.getDouble(paramString);
      paramExtendABean.put(paramString, new Double(d));
      break;
    case 1: 
    case 12: 
    default: 
      String str = paramResultSet.getString(paramString);
      if ((str != null) && (str.trim().length() > 0)) {
        paramExtendABean.put(paramString, str);
      }
      break;
    }
  }
  
  public static java.util.Date convertDate(String paramString)
    throws Exception
  {
    java.util.Date localDate = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    return localDate;
  }
  
  public static boolean isValidInt(String paramString)
  {
    if (paramString != null) {
      try
      {
        Long localLong = new Long(paramString);
        if (localLong.longValue() != -1L) {
          return true;
        }
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    return false;
  }
  
  public static boolean hasValue(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    return (!(paramObject instanceof String)) || (!paramObject.equals(""));
  }
  
  public static int convertToInt(String paramString)
  {
    return convertToInt(paramString, 0);
  }
  
  public static int convertToInt(String paramString, int paramInt)
  {
    int i = paramInt;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  protected static String fixDate(java.util.Date paramDate)
  {
    return DBUtil.fixDate(dbType, paramDate);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.ExtendableTableUtil
 * JD-Core Version:    0.7.0.1
 */