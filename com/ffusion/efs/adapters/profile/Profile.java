package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.HfnEncrypt;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.db.ConnectionDefines;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.SystemSettings;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Profile
  implements ProfileDefines, ConnectionDefines
{
  private static final String afb = "EncryptedFields";
  public static final String TRANS_TYPE_DESC_RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  public static final String TRANS_TYPE_DESC_PREFIX = "TransactionTypeText";
  public static final String TRANS_TYPE_MOD_PREFIX = "TransactionTypeModule";
  private static final long ae9 = 86400000L;
  private static final int afc = 1;
  protected static HashMap encryptedFields = null;
  protected static HashMap extTableData = null;
  protected static String poolName = null;
  protected static String dbCatalog = null;
  protected static String dbSchema = null;
  protected static String dbType = null;
  protected static Properties properties = null;
  private static Encrypt ae8 = null;
  public static final String LOCKOUT_TIME = "LOCKOUT_TIME";
  protected static int lockoutTime = 60;
  public static final String LOGIN_RETRIES = "LOGIN_RETRIES";
  protected static int loginRetries = 3;
  public static final String BATCH_MODE = "BATCH_MODE";
  protected static boolean batchMode = true;
  private static final String afd = " select business_name from business where business_id= ";
  private static final String afe = " select b.business_name, cd.cust_id from business b, customer_directory cd where b.directory_id=cd.directory_id and b.business_id= ";
  static final int[][] afa = { { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 365 }, { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 366 } };
  
  public static void isInitialized()
    throws ProfileException
  {
    if ((poolName == null) || (poolName.length() == 0)) {
      throw new ProfileException("Profile.isInitialized", 1, "Profile adapter is not initialized");
    }
  }
  
  public static synchronized void initialize(HashMap paramHashMap)
    throws ProfileException
  {
    Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    HashMap localHashMap = (HashMap)paramHashMap.get("GLOBAL_SETTINGS");
    int i = 1;
    try
    {
      i = Integer.parseInt((String)localHashMap.get("MaxTransactionTypeDescriptionAge"));
    }
    catch (Exception localException1)
    {
      i = 1;
    }
    String str1 = "Profile.initialize";
    if ((poolName != null) && (poolName.length() > 0)) {
      return;
    }
    Connection localConnection = null;
    try
    {
      properties = localProperties;
      poolName = ConnectionPool.init(properties);
      processInitArgs(properties);
      loadExtTableData();
      String str2 = properties.getProperty("Encrypt");
      if (str2 != null)
      {
        Class localClass = Class.forName(str2);
        ae8 = (Encrypt)localClass.newInstance();
      }
      jdMethod_long(i);
      localConnection = DBUtil.getConnection(poolName, false, 2);
      SystemSettings.initialize(localConnection);
    }
    catch (Exception localException2)
    {
      extTableData = null;
      handleError(localException2, str1, "Unable to initialize profile adapter", 1);
    }
    finally
    {
      DBUtil.returnConnection(poolName, localConnection);
    }
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
      for (int i = 0; i < ProfileDefines.EXTENDABLE_TABLES.length; i++) {
        loadExtTableData(localDatabaseMetaData, ProfileDefines.EXTENDABLE_TABLES[i], ProfileDefines.EXTENDABLE_TABLE_COLUMNS[i]);
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
        if (dbType.equalsIgnoreCase("ASE")) {
          localResultSet = paramDatabaseMetaData.getColumns(dbCatalog, dbSchema, paramString, "%");
        } else if (dbType.equalsIgnoreCase("MSSQL:THIN")) {
          localResultSet = paramDatabaseMetaData.getColumns(dbCatalog, dbSchema, paramString.toUpperCase(), "%");
        } else {
          localResultSet = paramDatabaseMetaData.getColumns(dbCatalog.toUpperCase(), dbSchema.toUpperCase(), paramString.toUpperCase(), "%");
        }
        TableInfo localTableInfo = new TableInfo();
        while (localResultSet.next())
        {
          String str = localResultSet.getString("COLUMN_NAME");
          if (!dbType.equalsIgnoreCase("ASE")) {
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
  
  public static String getDBType()
  {
    return dbType;
  }
  
  public static int getLockoutTime()
  {
    return lockoutTime;
  }
  
  public static int getLoginRetries()
  {
    return loginRetries;
  }
  
  public static boolean getBatchMode()
  {
    return batchMode;
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
  
  protected static void processInitArgs(Properties paramProperties)
    throws Exception
  {
    dbType = paramProperties.getProperty("ConnectionType");
    if (dbType == null) {
      throw new Exception("DB type unknown");
    }
    jdMethod_for(paramProperties);
    encryptedFields = new HashMap(5);
    String str = paramProperties.getProperty("EncryptedFields", "");
    if (str.length() > 0)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str, ",");
      while (localStringTokenizer.hasMoreTokens()) {
        encryptedFields.put(localStringTokenizer.nextToken().toLowerCase(), null);
      }
    }
    else
    {
      encryptedFields.put("PROCESSOR_PIN".toLowerCase(), null);
      encryptedFields.put("PASSWORD".toLowerCase(), null);
      encryptedFields.put("USER_PASSWORD".toLowerCase(), null);
      encryptedFields.put("PWD_REMINDER".toLowerCase(), null);
      encryptedFields.put("PWD_REMINDER2".toLowerCase(), null);
    }
    loginRetries = Integer.parseInt(properties.getProperty("LOGIN_RETRIES", "3"));
    lockoutTime = Integer.parseInt(properties.getProperty("LOCKOUT_TIME", "60"));
    batchMode = Boolean.valueOf(properties.getProperty("BATCH_MODE", "true")).booleanValue();
  }
  
  private static void jdMethod_for(Properties paramProperties)
    throws Exception
  {
    dbCatalog = paramProperties.getProperty("Catalog");
    dbSchema = paramProperties.getProperty("Schema");
    if (dbCatalog == null) {
      if ((dbType.equalsIgnoreCase("ORACLE:THIN")) || (dbType.equalsIgnoreCase("ORACLE:OCI8")) || (dbType.equalsIgnoreCase("DB2:APP")) || (dbType.equalsIgnoreCase("DB2:UN2")) || (dbType.equalsIgnoreCase("DB2:NET")) || (dbType.equalsIgnoreCase("DB2:AS390"))) {
        dbCatalog = "";
      } else if ((dbType.equalsIgnoreCase("ASE")) || (dbType.equalsIgnoreCase("MSSQL:THIN"))) {
        dbCatalog = null;
      } else {
        dbCatalog = paramProperties.getProperty("DBName");
      }
    }
    if (dbSchema == null) {
      if ((dbType.equalsIgnoreCase("ASE")) || (dbType.equalsIgnoreCase("MSSQL:THIN"))) {
        dbSchema = null;
      } else {
        dbSchema = paramProperties.getProperty("User");
      }
    }
  }
  
  public static String getRSString(ResultSet paramResultSet, String paramString)
    throws Exception
  {
    return checkDecrypt(paramResultSet.getString(paramString), paramString);
  }
  
  public static String checkEncrypt(String paramString1, String paramString2)
    throws Exception
  {
    if ((encryptedFields != null) && (encryptedFields.containsKey(paramString2.toLowerCase()))) {
      return dbEncrypt(paramString1);
    }
    return paramString1;
  }
  
  public static String checkDecrypt(String paramString1, String paramString2)
    throws Exception
  {
    if ((encryptedFields != null) && (encryptedFields.containsKey(paramString2.toLowerCase()))) {
      return dbDecrypt(paramString1);
    }
    return paramString1;
  }
  
  public static String dbEncrypt(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    if (ae8 != null) {
      return ae8.dbEncrypt(paramString);
    }
    byte[] arrayOfByte = paramString.getBytes();
    return new String(HfnEncrypt.encrypt(arrayOfByte, arrayOfByte.length), "8859_1");
  }
  
  public static String dbDecrypt(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return "";
    }
    if (ae8 != null) {
      return ae8.dbDecrypt(paramString);
    }
    byte[] arrayOfByte = paramString.getBytes("8859_1");
    return new String(HfnEncrypt.decrypt(arrayOfByte, arrayOfByte.length));
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
      jdMethod_int(paramStringBuffer);
    }
  }
  
  public static void appendSQLUpdateColumns(StringBuffer paramStringBuffer, String paramString, ExtendABean paramExtendABean)
  {
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      for (int i = 0; i < localTableInfo.size(); i++)
      {
        String str = localTableInfo.getName(i);
        if (paramExtendABean.get(K(str)) != null) {
          paramStringBuffer.append(", " + str + " = ?");
        }
      }
    }
  }
  
  public static void appendSQLSelectColumns(StringBuffer paramStringBuffer, String paramString, ExtendABean paramExtendABean)
  {
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      int i = 0;
      int j = 0;
      if (paramStringBuffer.toString().indexOf("where") == -1) {
        j = 1;
      }
      if (paramStringBuffer.toString().indexOf("?") != -1) {
        i = 1;
      }
      if (paramStringBuffer.charAt(paramStringBuffer.length() - 1) != ' ') {
        paramStringBuffer.append(" ");
      }
      for (int k = 0; k < localTableInfo.size(); k++)
      {
        String str = localTableInfo.getName(k);
        if (paramExtendABean.get(K(str)) != null)
        {
          if (j == 1) {
            paramStringBuffer.append(" where ");
          }
          if (i == 1) {
            paramStringBuffer.append(" and ");
          }
          paramStringBuffer.append(localTableInfo.getName(k) + " = ?");
          i = 1;
        }
      }
    }
  }
  
  public static boolean appendSQLSelectColumns2(StringBuffer paramStringBuffer, String paramString, ExtendABean paramExtendABean, boolean paramBoolean)
    throws Exception
  {
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      if (paramStringBuffer.charAt(paramStringBuffer.length() - 1) != ' ') {
        paramStringBuffer.append(" ");
      }
      for (int i = 0; i < localTableInfo.size(); i++)
      {
        String str = localTableInfo.getName(i);
        Object localObject = paramExtendABean.get(K(str));
        if (localObject != null)
        {
          if (paramBoolean)
          {
            paramStringBuffer.append(" and ");
          }
          else
          {
            paramStringBuffer.append(" where ");
            paramBoolean = true;
          }
          paramStringBuffer.append(localTableInfo.getName(i) + " = ");
          setSqlValue(paramStringBuffer, localObject, localTableInfo.getName(i), localTableInfo.getType(i));
        }
      }
    }
    return paramBoolean;
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
      paramStringBuffer.append("'" + checkEncrypt((String)paramObject, paramString) + "'");
      break;
    default: 
      throw new Exception("setSqlValue: unsupported data type");
    }
  }
  
  public static boolean appendSQLSelectString(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    return appendSQLSelectString(paramStringBuffer, "", paramString1, paramString2, false, false, paramBoolean);
  }
  
  public static boolean appendSQLSelectString(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return appendSQLSelectString(paramStringBuffer, "", paramString1, paramString2, paramBoolean1, false, paramBoolean2);
  }
  
  public static boolean appendSQLSelectString(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return appendSQLSelectString(paramStringBuffer, "", paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public static boolean appendSQLSelectString(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return appendSQLSelectString(paramStringBuffer, paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramBoolean3, true);
  }
  
  public static boolean appendSQLSelectString(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    return appendSQLSelectString(paramStringBuffer, paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, true);
  }
  
  public static boolean appendSQLSelectString(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
  {
    if ((paramString3 != null) && (paramString3.length() > 0))
    {
      if (!dbType.equalsIgnoreCase("ASE")) {
        paramString2 = paramString2.toLowerCase();
      }
      int i = 1;
      StringTokenizer localStringTokenizer = null;
      if (!paramBoolean4)
      {
        if ((encryptedFields != null) && (encryptedFields.get(paramString2.toLowerCase()) != null)) {
          localStringTokenizer = new StringTokenizer(paramString3, "");
        } else {
          localStringTokenizer = new StringTokenizer(paramString3, ",");
        }
      }
      else {
        localStringTokenizer = new StringTokenizer(paramString3, "");
      }
      int j = localStringTokenizer.countTokens();
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if ((str != null) && (str.length() > 0))
        {
          if (!paramBoolean3)
          {
            paramStringBuffer.append(" where ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            paramBoolean3 = true;
            i = 0;
          }
          else if (i == 1)
          {
            paramStringBuffer.append(" and ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            i = 0;
          }
          else
          {
            paramStringBuffer.append(" or ");
          }
          if (paramBoolean2) {
            paramStringBuffer.append(DBUtil.ignoreCase(paramString1 + paramString2, dbType));
          } else {
            paramStringBuffer.append(paramString1).append(paramString2);
          }
          if (paramBoolean1) {
            paramStringBuffer.append(" like ?");
          } else {
            paramStringBuffer.append(" = ?");
          }
        }
      }
      if (j > 1) {
        paramStringBuffer.append(") ");
      } else {
        paramStringBuffer.append(" ");
      }
    }
    else if ((paramString3 == null) && (!paramBoolean5))
    {
      if (!paramBoolean3)
      {
        paramBoolean3 = true;
        paramStringBuffer.append(" where ");
      }
      else
      {
        paramStringBuffer.append(" and ");
      }
      if (paramBoolean2) {
        paramStringBuffer.append(DBUtil.ignoreCase(paramString1 + paramString2, dbType));
      } else {
        paramStringBuffer.append(paramString1).append(paramString2);
      }
      paramStringBuffer.append(" is null ");
    }
    return paramBoolean3;
  }
  
  public static boolean appendSQLSelectInt(StringBuffer paramStringBuffer, String paramString, long paramLong, boolean paramBoolean)
  {
    return appendSQLSelectInt(paramStringBuffer, "", paramString, paramLong, paramBoolean);
  }
  
  public static boolean appendSQLSelectInt(StringBuffer paramStringBuffer, String paramString1, String paramString2, long paramLong, boolean paramBoolean)
  {
    if (paramLong != 0L)
    {
      if (paramBoolean) {
        paramStringBuffer.append(" and ");
      } else if (paramStringBuffer.toString().indexOf("where") == -1) {
        paramStringBuffer.append(" where ");
      }
      if (dbType.equalsIgnoreCase("ASE")) {
        paramStringBuffer.append(paramString1).append(paramString2 + " = ?");
      } else {
        paramStringBuffer.append(paramString1).append(paramString2.toLowerCase() + " = ?");
      }
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public static boolean appendSQLSelectInt(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    return appendSQLSelectInt(paramStringBuffer, "", paramString1, paramString2, paramBoolean);
  }
  
  public static boolean appendSQLSelectInt(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if ((paramString3 != null) && (paramString3.length() > 0))
    {
      if (!dbType.equalsIgnoreCase("ASE")) {
        paramString2 = paramString2.toLowerCase();
      }
      int i = 1;
      StringTokenizer localStringTokenizer = null;
      localStringTokenizer = new StringTokenizer(paramString3, ",");
      int j = localStringTokenizer.countTokens();
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if ((str != null) && (str.length() > 0))
        {
          if (!paramBoolean)
          {
            paramStringBuffer.append(" where ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            paramBoolean = true;
            i = 0;
          }
          else if (i == 1)
          {
            paramStringBuffer.append(" and ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            i = 0;
          }
          else
          {
            paramStringBuffer.append(" or ");
          }
          paramStringBuffer.append(paramString1).append(paramString2);
          paramStringBuffer.append(" = ?");
        }
      }
      if (j > 1) {
        paramStringBuffer.append(") ");
      } else {
        paramStringBuffer.append(" ");
      }
    }
    return paramBoolean;
  }
  
  public static boolean appendSQLSelectDate(StringBuffer paramStringBuffer, String paramString1, java.util.Date paramDate, String paramString2, boolean paramBoolean)
  {
    return appendSQLSelectDate(paramStringBuffer, "", paramString1, paramDate, paramString2, paramBoolean);
  }
  
  public static boolean appendSQLSelectDate(StringBuffer paramStringBuffer, String paramString1, String paramString2, java.util.Date paramDate, String paramString3, boolean paramBoolean)
  {
    if (paramDate != null)
    {
      if (paramBoolean) {
        paramStringBuffer.append(" and ");
      } else if (paramStringBuffer.toString().indexOf("where") == -1) {
        paramStringBuffer.append(" where ");
      }
      if (dbType.equalsIgnoreCase("ASE")) {
        paramStringBuffer.append(paramString1).append(paramString2 + " " + paramString3 + " ?");
      } else {
        paramStringBuffer.append(paramString1).append(paramString2.toLowerCase() + " " + paramString3 + " ?");
      }
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  private static void jdMethod_int(StringBuffer paramStringBuffer)
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
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    return setStatementString(paramPreparedStatement, paramInt, paramString1, paramString2, false, false, paramBoolean, true);
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    return setStatementString(paramPreparedStatement, paramInt, paramString1, paramString2, paramBoolean1, false, paramBoolean2, true);
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    return setStatementString(paramPreparedStatement, paramInt, paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, true);
  }
  
  public static int setStatementString(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    throws Exception
  {
    if ((!paramBoolean3) || ((paramString1 != null) && (paramString1.length() > 0))) {
      if ((paramString1 == null) || (paramString1.length() == 0))
      {
        if (dbType.equalsIgnoreCase("ASE")) {
          paramPreparedStatement.setNull(paramInt++, 12);
        } else {
          paramPreparedStatement.setString(paramInt++, paramString1);
        }
      }
      else
      {
        if (!dbType.equalsIgnoreCase("ASE")) {
          paramString2 = paramString2.toLowerCase();
        }
        StringTokenizer localStringTokenizer = null;
        if (!paramBoolean4)
        {
          if ((encryptedFields != null) && (encryptedFields.get(paramString2.toLowerCase()) != null)) {
            localStringTokenizer = new StringTokenizer(paramString1, "");
          } else {
            localStringTokenizer = new StringTokenizer(paramString1, ",");
          }
        }
        else {
          localStringTokenizer = new StringTokenizer(paramString1, "");
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
            paramPreparedStatement.setString(paramInt++, checkEncrypt(str, paramString2));
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
  
  public static int setStatementValues(PreparedStatement paramPreparedStatement, int paramInt, ExtendABean paramExtendABean, String paramString, boolean paramBoolean)
    throws Exception
  {
    int i = paramInt;
    if (extTableData.get(paramString) != null)
    {
      TableInfo localTableInfo = (TableInfo)extTableData.get(paramString);
      for (int j = 0; j < localTableInfo.size(); j++) {
        i = setStatementValue(paramPreparedStatement, i, paramExtendABean.get(K(localTableInfo.getName(j))), localTableInfo.getName(j), localTableInfo.getType(j), paramBoolean);
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
      case 2: 
      case 3: 
      case 4: 
        str = (String)paramObject;
        int i = 0;
        if (str != null) {
          i = Integer.parseInt(str);
        }
        paramPreparedStatement.setInt(paramInt1, i);
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
        paramPreparedStatement.setString(paramInt1, checkEncrypt((String)paramObject, paramString));
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
        jdMethod_for(paramResultSet, paramExtendABean, localTableInfo.getName(i), localTableInfo.getType(i));
      }
      catch (Exception localException) {}
    }
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, ExtendABean paramExtendABean, String paramString, int paramInt)
    throws Exception
  {
    switch (paramInt)
    {
    case 91: 
    case 93: 
      Timestamp localTimestamp = paramResultSet.getTimestamp(paramString);
      DateTime localDateTime = new DateTime(new java.util.Date(localTimestamp.getTime()), null);
      paramExtendABean.put(K(paramString), localDateTime);
      break;
    case 1: 
    case 2: 
    case 4: 
    case 6: 
    case 8: 
    case 12: 
    default: 
      String str = getRSString(paramResultSet, paramString);
      if ((str != null) && (str.trim().length() > 0)) {
        paramExtendABean.put(K(paramString), str);
      }
      break;
    }
  }
  
  protected static int getBeanInt(ExtendABean paramExtendABean, String paramString)
  {
    int i = 0;
    Object localObject = paramExtendABean.get(K(paramString));
    if (localObject != null) {
      try
      {
        i = Integer.parseInt((String)localObject);
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    return i;
  }
  
  public static java.util.Date convertDate(String paramString)
  {
    int[] arrayOfInt = new int[6];
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "/\\-,. :");
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 0;
    }
    for (i = 0; (localStringTokenizer.hasMoreTokens()) && (i < 6); i++)
    {
      String str = localStringTokenizer.nextToken();
      int j;
      try
      {
        j = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return null;
      }
      arrayOfInt[i] = j;
    }
    if ((arrayOfInt[0] <= 0) || (arrayOfInt[1] <= 0) || (arrayOfInt[2] <= 0) || (arrayOfInt[3] < 0) || (arrayOfInt[4] < 0) || (arrayOfInt[5] < 0)) {
      return null;
    }
    if ((arrayOfInt[0] < 1) || (arrayOfInt[0] > 12)) {
      return null;
    }
    i = isLeapYear(arrayOfInt[2]) ? 1 : 0;
    if (arrayOfInt[1] > afa[i][arrayOfInt[0]]) {
      return null;
    }
    if ((arrayOfInt[3] >= 24) || (arrayOfInt[4] >= 60) || (arrayOfInt[5] >= 60)) {
      return null;
    }
    return new GregorianCalendar(arrayOfInt[2], arrayOfInt[0] - 1, arrayOfInt[1], arrayOfInt[3], arrayOfInt[4], arrayOfInt[5]).getTime();
  }
  
  public static boolean isLeapYear(int paramInt)
  {
    return ((paramInt % 4 == 0) && (paramInt % 100 != 0)) || (paramInt % 400 == 0);
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
  
  public static boolean isValidId(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      return false;
    }
    try
    {
      int i = Integer.parseInt(paramString);
      if (i <= 0) {
        return false;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return false;
    }
    return true;
  }
  
  public static boolean isValidId(int paramInt)
  {
    return paramInt > 0;
  }
  
  public static boolean isValidId(long paramLong)
  {
    return paramLong > 0L;
  }
  
  public static boolean hasValue(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    return (!(paramObject instanceof String)) || (!paramObject.equals(""));
  }
  
  public static String convertSqlTimestamp(Timestamp paramTimestamp)
  {
    if (paramTimestamp != null)
    {
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(new java.util.Date(paramTimestamp.getTime()));
      String str;
      if ((localCalendar.get(11) == 0) && (localCalendar.get(12) == 0) && (localCalendar.get(13) == 0)) {
        str = "MM-dd-yyyy";
      } else {
        str = "MM-dd-yyyy HH:mm:ss";
      }
      return DateFormatUtil.getFormatter(str).format(paramTimestamp);
    }
    return "";
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
  
  public static float convertToFloat(String paramString)
  {
    float f = 0.0F;
    try
    {
      f = Float.parseFloat(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return f;
  }
  
  public static BigDecimal convertToBigDecimal(String paramString)
  {
    BigDecimal localBigDecimal = null;
    try
    {
      localBigDecimal = new BigDecimal(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localBigDecimal = new BigDecimal("0");
    }
    return localBigDecimal;
  }
  
  public static java.sql.Date convertToDate(Object paramObject)
  {
    java.sql.Date localDate = null;
    if (paramObject != null) {
      if ((paramObject instanceof String))
      {
        java.util.Date localDate1 = convertDate((String)paramObject);
        if (localDate1 != null) {
          localDate = new java.sql.Date(localDate1.getTime());
        }
      }
      else if ((paramObject instanceof DateTime))
      {
        localDate = new java.sql.Date(((DateTime)paramObject).getTime().getTime());
      }
      else if ((paramObject instanceof java.util.Date))
      {
        localDate = new java.sql.Date(((java.util.Date)paramObject).getTime());
      }
    }
    return localDate;
  }
  
  public static Timestamp convertToTimestamp(Object paramObject)
  {
    Timestamp localTimestamp = null;
    if (paramObject != null) {
      if ((paramObject instanceof String))
      {
        java.util.Date localDate = convertDate((String)paramObject);
        if (localDate != null) {
          localTimestamp = new Timestamp(localDate.getTime());
        }
      }
      else if ((paramObject instanceof DateTime))
      {
        localTimestamp = new Timestamp(((DateTime)paramObject).getTime().getTime());
      }
      else if ((paramObject instanceof java.util.Date))
      {
        localTimestamp = new Timestamp(((java.util.Date)paramObject).getTime());
      }
    }
    return localTimestamp;
  }
  
  public static java.sql.Date getNextDay(java.sql.Date paramDate)
  {
    if (paramDate != null)
    {
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(paramDate);
      if ((localCalendar.get(11) == 0) && (localCalendar.get(12) == 0) && (localCalendar.get(13) == 0))
      {
        localCalendar.add(5, 1);
        paramDate = new java.sql.Date(localCalendar.getTime().getTime());
      }
    }
    return paramDate;
  }
  
  public static void handleError(Exception paramException, String paramString)
    throws ProfileException
  {
    if ((paramException instanceof ProfileException)) {
      throw ((ProfileException)paramException);
    }
    DebugLog.log(Level.FINE, "Profile.handleError: " + paramException.getMessage());
    throw new ProfileException(paramException, paramString, 2, "");
  }
  
  public static void handleError(Exception paramException, String paramString1, String paramString2, int paramInt)
    throws ProfileException
  {
    if ((paramException instanceof ProfileException)) {
      throw ((ProfileException)paramException);
    }
    DebugLog.log(Level.FINE, "Profile.handleError: " + paramException.getMessage());
    throw new ProfileException(paramException, paramString1, paramInt, paramString2);
  }
  
  public static void handleError(String paramString1, String paramString2, int paramInt)
    throws ProfileException
  {
    DebugLog.log(Level.FINE, "Profile.handleError: where: " + paramString1 + " why: " + paramString2 + " code: " + paramInt);
    throw new ProfileException(paramString1, paramInt, paramString2);
  }
  
  public static ArrayList breakupMessage(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 255;
    int k = paramString.length();
    if (k < 255) {
      j = k;
    }
    while (i < k)
    {
      String str = paramString.substring(i, j);
      i = j;
      if (k > i + 255) {
        j = i + 255;
      } else {
        j = k;
      }
      localArrayList.add(str);
    }
    return localArrayList;
  }
  
  public static boolean appendSQLSelectString2(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    return appendSQLSelectString2(paramStringBuffer, "", paramString1, paramString2, false, false, paramBoolean);
  }
  
  public static boolean appendSQLSelectString2(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return appendSQLSelectString2(paramStringBuffer, "", paramString1, paramString2, paramBoolean1, false, paramBoolean2);
  }
  
  public static boolean appendSQLSelectString2(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return appendSQLSelectString2(paramStringBuffer, "", paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public static boolean appendSQLSelectString2(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return appendSQLSelectString2(paramStringBuffer, paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramBoolean3, false);
  }
  
  public static boolean appendSQLSelectString2(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if ((paramString3 != null) && (paramString3.length() > 0))
    {
      if (!dbType.equalsIgnoreCase("ASE")) {
        paramString2 = paramString2.toLowerCase();
      }
      int i = 1;
      StringTokenizer localStringTokenizer = null;
      if (!paramBoolean4)
      {
        if ((encryptedFields != null) && (encryptedFields.get(paramString2.toLowerCase()) != null)) {
          localStringTokenizer = new StringTokenizer(paramString3, "");
        } else {
          localStringTokenizer = new StringTokenizer(paramString3, ",");
        }
      }
      else {
        localStringTokenizer = new StringTokenizer(paramString3, "");
      }
      int j = localStringTokenizer.countTokens();
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if ((str != null) && (str.length() > 0))
        {
          str = Strings.replaceStr(str, "'", "''");
          if (!paramBoolean3)
          {
            paramStringBuffer.append(" where ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            paramBoolean3 = true;
            i = 0;
          }
          else if (i == 1)
          {
            paramStringBuffer.append(" and ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            i = 0;
          }
          else
          {
            paramStringBuffer.append(" or ");
          }
          if (paramBoolean2) {
            paramStringBuffer.append(DBUtil.ignoreCase(paramString1 + paramString2, dbType));
          } else {
            paramStringBuffer.append(paramString1).append(paramString2);
          }
          if (paramBoolean1) {
            paramStringBuffer.append(" like '" + DBUtil.escapeSQLStringLiteral(str) + "%'");
          } else {
            paramStringBuffer.append(" = '" + DBUtil.escapeSQLStringLiteral(str) + "'");
          }
        }
      }
      if (j > 1) {
        paramStringBuffer.append(") ");
      } else {
        paramStringBuffer.append(" ");
      }
    }
    return paramBoolean3;
  }
  
  public static boolean appendSQLSelectInt2(StringBuffer paramStringBuffer, String paramString, long paramLong, boolean paramBoolean)
  {
    return appendSQLSelectInt2(paramStringBuffer, "", paramString, paramLong, paramBoolean);
  }
  
  public static boolean appendSQLSelectInt2(StringBuffer paramStringBuffer, String paramString1, String paramString2, long paramLong, boolean paramBoolean)
  {
    if (paramLong != 0L)
    {
      if (paramBoolean) {
        paramStringBuffer.append(" and ");
      } else if (paramStringBuffer.toString().indexOf("where") == -1) {
        paramStringBuffer.append(" where ");
      }
      if (dbType.equalsIgnoreCase("ASE")) {
        paramStringBuffer.append(paramString1).append(paramString2 + " = " + paramLong);
      } else {
        paramStringBuffer.append(paramString1).append(paramString2.toLowerCase() + " = " + paramLong);
      }
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public static boolean appendSQLSelectInt2(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    return appendSQLSelectInt2(paramStringBuffer, "", paramString1, paramString2, paramBoolean);
  }
  
  public static boolean appendSQLSelectInt2(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if ((paramString3 != null) && (paramString3.length() > 0))
    {
      if (!dbType.equalsIgnoreCase("ASE")) {
        paramString2 = paramString2.toLowerCase();
      }
      int i = 1;
      StringTokenizer localStringTokenizer = null;
      localStringTokenizer = new StringTokenizer(paramString3, ",");
      int j = localStringTokenizer.countTokens();
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if ((str != null) && (str.length() > 0))
        {
          if (!paramBoolean)
          {
            paramStringBuffer.append(" where ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            paramBoolean = true;
            i = 0;
          }
          else if (i == 1)
          {
            paramStringBuffer.append(" and ");
            if (j > 1) {
              paramStringBuffer.append("(");
            }
            i = 0;
          }
          else
          {
            paramStringBuffer.append(" or ");
          }
          paramStringBuffer.append(paramString1).append(paramString2);
          paramStringBuffer.append(" = ");
          paramStringBuffer.append(str);
        }
      }
      if (j > 1) {
        paramStringBuffer.append(") ");
      } else {
        paramStringBuffer.append(" ");
      }
    }
    return paramBoolean;
  }
  
  public static boolean appendSQLSelectDate2(StringBuffer paramStringBuffer, String paramString1, java.util.Date paramDate, String paramString2, boolean paramBoolean)
  {
    return appendSQLSelectDate2(paramStringBuffer, "", paramString1, paramDate, paramString2, paramBoolean);
  }
  
  public static boolean appendSQLSelectDate2(StringBuffer paramStringBuffer, String paramString1, String paramString2, java.util.Date paramDate, String paramString3, boolean paramBoolean)
  {
    if (paramDate != null)
    {
      if (paramBoolean) {
        paramStringBuffer.append(" and ");
      } else {
        paramStringBuffer.append(" where ");
      }
      if (dbType.equalsIgnoreCase("ASE")) {
        paramStringBuffer.append(paramString1).append(paramString2 + " " + paramString3 + " " + fixDate(paramDate));
      } else {
        paramStringBuffer.append(paramString1).append(paramString2.toLowerCase() + " " + paramString3 + " " + fixDate(paramDate));
      }
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public static String fixDate(java.util.Date paramDate)
  {
    return DBUtil.fixDate(dbType, paramDate);
  }
  
  public static boolean useMaxRows(HashMap paramHashMap)
  {
    boolean bool = true;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("UseMaxRows");
      if ((str != null) && (str.equals("false"))) {
        bool = false;
      }
    }
    return bool;
  }
  
  public static int getMaxRowCount(HashMap paramHashMap)
  {
    int i = 250;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("MaxRowCount");
      if (str != null) {
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException)
        {
          DebugLog.log("getMaxRowCount: invalid max row count value");
          i = 250;
        }
      }
    }
    return i;
  }
  
  public static String getSearchCriteria(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  public static String getSortCriteria(ReportSortCriteria paramReportSortCriteria, int paramInt, String paramString)
  {
    for (int i = 0; i < paramReportSortCriteria.size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(i);
      if (localReportSortCriterion.getOrdinal() == paramInt)
      {
        String str = localReportSortCriterion.getName();
        if ((str == null) || (str.equals(""))) {
          return paramString;
        }
        return localReportSortCriterion.getName();
      }
    }
    return paramString;
  }
  
  public static String getBusinessName(Connection paramConnection, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "Profile.getBusinessName";
    try
    {
      String str3 = " select business_name from business where business_id= " + paramString;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      if (localResultSet.next()) {
        str1 = localResultSet.getString(1);
      }
    }
    catch (Exception localException)
    {
      throw ((ProfileException)localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return str1;
  }
  
  public static String getBusinessNameAndCustId(Connection paramConnection, String paramString, Locale paramLocale)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "Profile.getBusinessNameAndCustId";
    if ((paramString != null) && (paramString.length() > 0)) {
      try
      {
        String str3 = null;
        String str4 = " select b.business_name, cd.cust_id from business b, customer_directory cd where b.directory_id=cd.directory_id and b.business_id= " + paramString;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, str4);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str4);
        if (localResultSet.next())
        {
          str1 = localResultSet.getString(1);
          str3 = localResultSet.getString(2);
          if (str3 != null)
          {
            str3 = str3.trim();
            if (str3.length() > 0)
            {
              Object[] arrayOfObject = { str1, str3 };
              str1 = MessageFormat.format(ReportConsts.getText(10109, paramLocale), arrayOfObject);
            }
          }
        }
      }
      catch (Exception localException)
      {
        throw localException;
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
    return str1;
  }
  
  public static void addItem(String paramString, ReportDataItems paramReportDataItems, ReportDataItem paramReportDataItem)
  {
    if (paramString == null)
    {
      paramReportDataItems.add(null);
    }
    else
    {
      paramReportDataItem = paramReportDataItems.add();
      paramReportDataItem.setData(paramString);
    }
  }
  
  private static void jdMethod_long(int paramInt)
    throws Exception
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.banking.resources", Locale.getDefault());
    int i = 0;
    int j = 1;
    java.sql.Date localDate1 = new java.sql.Date(System.currentTimeMillis());
    java.sql.Date localDate2 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = "UPDATE trans_type_desc SET last_updated=last_updated";
    String str2 = "SELECT DISTINCT last_updated FROM trans_type_desc";
    String str3 = "DELETE FROM trans_type_desc";
    String str4 = "INSERT INTO trans_type_desc (id, description, last_updated) VALUES (?, ?, ?)";
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 8);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str1);
      DBUtil.execute(localPreparedStatement, str1);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      try
      {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      }
      catch (Exception localException1)
      {
        i = 1;
        DebugLog.log(Level.SEVERE, "Profile.loadTransactionTypeDescriptions: " + localException1.getMessage());
        throw localException1;
      }
      if (localResultSet.next())
      {
        long l = 0L;
        localDate2 = localResultSet.getDate("last_updated");
        l = localDate1.getTime() - localDate2.getTime();
        if (l < 86400000L * paramInt) {
          j = 0;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      if (j != 0)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
        DBUtil.execute(localPreparedStatement, str3);
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str4);
        Enumeration localEnumeration = localResourceBundle.getKeys();
        while (localEnumeration.hasMoreElements())
        {
          String str5 = (String)localEnumeration.nextElement();
          if (str5.startsWith("TransactionTypeText"))
          {
            String str6 = localResourceBundle.getString(str5).trim();
            int k = Integer.parseInt(str5.substring("TransactionTypeText".length(), str5.length()));
            int m = 1;
            localPreparedStatement.setInt(m++, k);
            localPreparedStatement.setString(m++, str6);
            localPreparedStatement.setDate(m++, localDate1);
            localPreparedStatement.addBatch();
          }
        }
        DBUtil.executeBatch(localPreparedStatement, str4);
      }
      localConnection.commit();
    }
    catch (Exception localException2)
    {
      localConnection.rollback();
      if (i == 0) {
        throw localException2;
      }
    }
    finally
    {
      try
      {
        DBUtil.closeResultSet(localResultSet);
      }
      catch (Exception localException3)
      {
        DebugLog.log(Level.FINE, "Profile.loadTransactionTypeDescriptions: " + localException3.getMessage());
      }
      try
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      catch (Exception localException4)
      {
        DebugLog.log(Level.FINE, "Profile.loadTransactionTypeDescriptions: " + localException4.getMessage());
      }
      try
      {
        localConnection.setTransactionIsolation(2);
        DBUtil.returnConnection(poolName, localConnection);
      }
      catch (Exception localException5)
      {
        DebugLog.log(Level.FINE, "Profile.loadTransactionTypeDescriptions: " + localException5.getMessage());
      }
    }
  }
  
  static String J(String paramString)
  {
    if (paramString.equals("customer")) {
      return "customer";
    }
    if (paramString.equals("customer_directory")) {
      return "customer_directory";
    }
    if (paramString.equals("accounts")) {
      return "accounts";
    }
    if (paramString.equals("directory_data")) {
      return "directory_data";
    }
    if (paramString.equals("custom_ims_tag")) {
      return "custom_ims_tag";
    }
    if (paramString.equals("bank_employee")) {
      return "bank_employee";
    }
    if (paramString.equals("history")) {
      return "history";
    }
    if (paramString.equals("message")) {
      return "message";
    }
    if (paramString.equals("message_item")) {
      return "message_item";
    }
    if (paramString.equals("message_body")) {
      return "message_body";
    }
    if (paramString.equals("response_body")) {
      return "response_body";
    }
    if (paramString.equals("queue")) {
      return "queue";
    }
    if (paramString.equals("queue_members")) {
      return "queue_members";
    }
    if (paramString.equals("queue_response")) {
      return "queue_response";
    }
    if (paramString.equals("customer_payee")) {
      return "customer_payee";
    }
    if (paramString.equals("bank")) {
      return "bank";
    }
    if (paramString.equals("application")) {
      return "application";
    }
    if (paramString.equals("app_history")) {
      return "app_history";
    }
    if (paramString.equals("product")) {
      return "product";
    }
    if (paramString.equals("product_access")) {
      return "product_access";
    }
    if (paramString.equals("product_status")) {
      return "product_status";
    }
    if (paramString.equals("status")) {
      return "status";
    }
    if (paramString.equals("category")) {
      return "category";
    }
    if (paramString.equals("app_form")) {
      return "app_form";
    }
    if (paramString.equals("form")) {
      return "form";
    }
    if (paramString.equals("form_columns")) {
      return "form_columns";
    }
    if (paramString.equals("contact_info")) {
      return "contact_info";
    }
    if (paramString.equals("business")) {
      return "business";
    }
    if (paramString.equals("business_employee")) {
      return "business_employee";
    }
    if (paramString.equals("assignment")) {
      return "assignment";
    }
    if (paramString.equals("reg_def_category")) {
      return "reg_def_category";
    }
    if (paramString.equals("reg_user_category")) {
      return "reg_user_category";
    }
    if (paramString.equals("reg_payee")) {
      return "reg_payee";
    }
    if (paramString.equals("reg_srvr_tran_cat")) {
      return "reg_srvr_tran_cat";
    }
    if (paramString.equals("reg_transaction")) {
      return "reg_transaction";
    }
    if (paramString.equals("reg_tran_category")) {
      return "reg_tran_category";
    }
    if (paramString.equals("affiliate_bank")) {
      return "affiliate_bank";
    }
    if (paramString.equals("DIRECTORY_ID")) {
      return "directory_id";
    }
    if (paramString.equals("CUST_ID")) {
      return "cust_id";
    }
    if (paramString.equals("DIRECTORY_DATE")) {
      return "directory_date";
    }
    if (paramString.equals("LAST_ACTIVE_DATE")) {
      return "last_active_date";
    }
    if (paramString.equals("CREATE_DATE")) {
      return "create_date";
    }
    if (paramString.equals("ACCOUNT_STATUS")) {
      return "account_status";
    }
    if (paramString.equals("MODIFIED_DATE")) {
      return "modified_date";
    }
    if (paramString.equals("ACCOUNT_ID")) {
      return "account_id";
    }
    if (paramString.equals("ACCOUNT_TYPE")) {
      return "ACCOUNT_TYPE";
    }
    if (paramString.equals("ACCOUNT_NUM")) {
      return "ACCOUNT_NUM";
    }
    if (paramString.equals("NICKNAME")) {
      return "nickname";
    }
    if (paramString.equals("NICK_NAME")) {
      return "nick_name";
    }
    if (paramString.equals("HIDE")) {
      return "hide";
    }
    if (paramString.equals("REG_RETRIEVAL_DATE")) {
      return "reg_retrieval_date";
    }
    if (paramString.equals("REG_DEFAULT")) {
      return "reg_default";
    }
    if (paramString.equals("REG_ENABLED")) {
      return "reg_enabled";
    }
    if (paramString.equals("EMPLOYEE_ID")) {
      return "employee_id";
    }
    if (paramString.equals("USER_NAME")) {
      return "user_name";
    }
    if (paramString.equals("BANK_EMPLOYEE_ID")) {
      return "bank_employee_id";
    }
    if (paramString.equals("PASSWORD")) {
      return "password";
    }
    if (paramString.equals("FIRST_NAME")) {
      return "first_name";
    }
    if (paramString.equals("LOW_FIRST_NAME")) {
      return "low_first_name";
    }
    if (paramString.equals("MIDDLE_NAME")) {
      return "middle_name";
    }
    if (paramString.equals("LOW_MIDDLE_NAME")) {
      return "low_middle_name";
    }
    if (paramString.equals("LAST_NAME")) {
      return "last_name";
    }
    if (paramString.equals("LOW_LAST_NAME")) {
      return "low_last_name";
    }
    if (paramString.equals("EMAIL_ADDRESS")) {
      return "email_address";
    }
    if (paramString.equals("EMPLOYEE_ADMIN_ACCESS")) {
      return "employee_admin_access";
    }
    if (paramString.equals("USER_PASSWORD")) {
      return "user_password";
    }
    if (paramString.equals("ADDRESS1")) {
      return "address1";
    }
    if (paramString.equals("ADDRESS2")) {
      return "address2";
    }
    if (paramString.equals("ADDRESS3")) {
      return "address3";
    }
    if (paramString.equals("CITY")) {
      return "city";
    }
    if (paramString.equals("STATE")) {
      return "state";
    }
    if (paramString.equals("ZIP")) {
      return "zip";
    }
    if (paramString.equals("HOME_PHONE")) {
      return "home_phone";
    }
    if (paramString.equals("WORK_PHONE")) {
      return "work_phone";
    }
    if (paramString.equals("SSN")) {
      return "ssn";
    }
    if (paramString.equals("MOTHERS_MAIDEN")) {
      return "mothers_maiden";
    }
    if (paramString.equals("BIRTH_DATE")) {
      return "birth_date";
    }
    if (paramString.equals("TIMEOUT")) {
      return "timeout";
    }
    if (paramString.equals("PASSWORD_STATUS")) {
      return "password_status";
    }
    if (paramString.equals("PASSWORD_FAIL_COUNT")) {
      return "password_fail_count";
    }
    if (paramString.equals("PASSWORD_LOCKOUT_TIME")) {
      return "password_lockout_time";
    }
    if (paramString.equals("PWD_REMINDER")) {
      return "pwd_reminder";
    }
    if (paramString.equals("REMINDER")) {
      return "reminder";
    }
    if (paramString.equals("PERSONAL_BANKER")) {
      return "personal_banker";
    }
    if (paramString.equals("GENDER")) {
      return "gender";
    }
    if (paramString.equals("GREETING")) {
      return "greeting";
    }
    if (paramString.equals("GREETING_TYPE")) {
      return "greeting_type";
    }
    if (paramString.equals("SERVICE_LEVEL")) {
      return "service_level";
    }
    if (paramString.equals("EMPLOYEE_STATUS")) {
      return "employee_status";
    }
    if (paramString.equals("SUPERVISOR")) {
      return "supervisor";
    }
    if (paramString.equals("EMPLOYEE_NOTIFY")) {
      return "employee_notify";
    }
    if (paramString.equals("ACCOUNT_REP")) {
      return "account_rep";
    }
    if (paramString.equals("APPROVED_BY")) {
      return "approved_by";
    }
    if (paramString.equals("APPROVAL_GROUP")) {
      return "approval_group";
    }
    if (paramString.equals("ACTIVATION_DATE")) {
      return "activation_date";
    }
    if (paramString.equals("PREFER_CONTACT")) {
      return "prefer_contact";
    }
    if (paramString.equals("DATA_PHONE")) {
      return "data_phone";
    }
    if (paramString.equals("FAX_PHONE")) {
      return "fax_phone";
    }
    if (paramString.equals("BATCH_TYPE")) {
      return "batch_type";
    }
    if (paramString.equals("DFLT_PPAY_DECISION")) {
      return "dflt_ppay_decision";
    }
    if (paramString.equals("PRIM_CONTACT_PERMS")) {
      return "PRIM_CONTACT_PERMS";
    }
    if (paramString.equals("SEC_CONTACT_PERMS")) {
      return "SEC_CONTACT_PERMS";
    }
    if (paramString.equals("PASSWORD_TIME")) {
      return "PASSWORD_TIME";
    }
    if (paramString.equals("LASTVIEWINTRATRANS")) {
      return "LASTVIEWINTRATRANS";
    }
    if (paramString.equals("PREFERRED_LANG")) {
      return "preferred_lang";
    }
    if (paramString.equals("reg_def_cat_id")) {
      return "reg_def_cat_id";
    }
    if (paramString.equals("type")) {
      return "type";
    }
    if (paramString.equals("parent_category_id")) {
      return "parent_category_id";
    }
    if (paramString.equals("tax_related")) {
      return "tax_related";
    }
    if (paramString.equals("reg_user_cat_id")) {
      return "reg_user_cat_id";
    }
    if (paramString.equals("reg_transaction_id")) {
      return "reg_transaction_id";
    }
    if (paramString.equals("fi_transaction_id")) {
      return "fi_transaction_id";
    }
    if (paramString.equals("server_tran_id")) {
      return "server_tran_id";
    }
    if (paramString.equals("reference_number")) {
      return "reference_number";
    }
    if (paramString.equals("payee_name")) {
      return "payee_name";
    }
    if (paramString.equals("memo")) {
      return "memo";
    }
    if (paramString.equals("date_issued")) {
      return "date_issued";
    }
    if (paramString.equals("date_cleared")) {
      return "date_cleared";
    }
    if (paramString.equals("reg_payee_id")) {
      return "reg_payee_id";
    }
    if (paramString.equals("rec_server_tran_id")) {
      return "rec_server_tran_id";
    }
    if (paramString.equals("REVIEW_REQUIRED")) {
      return "review_required";
    }
    if (paramString.equals("OBO_ENABLED")) {
      return "obo_enabled";
    }
    if (paramString.equals("APPROVAL_PROVIDER")) {
      return "approval_provider";
    }
    if (paramString.equals("MSG_APPROVAL_PROVIDER")) {
      return "msg_approval_provider";
    }
    if (paramString.equals("AFFILIATE_BANK_ID")) {
      return "affiliate_bank_id";
    }
    if (paramString.equals("CONTACT_ID")) {
      return "CONTACT_ID";
    }
    if (paramString.equals("STREET")) {
      return "street";
    }
    if (paramString.equals("STREET2")) {
      return "street2";
    }
    if (paramString.equals("STREET3")) {
      return "street3";
    }
    if (paramString.equals("POSTAL_CODE")) {
      return "postal_code";
    }
    if (paramString.equals("PHONE")) {
      return "phone";
    }
    if (paramString.equals("PHONE2")) {
      return "phone2";
    }
    if (paramString.equals("FAX_NUMBER")) {
      return "fax_number";
    }
    if (paramString.equals("BUSINESS_ID")) {
      return "business_id";
    }
    if (paramString.equals("BUSINESS_ID_STR")) {
      return "BUSINESS_ID_STR";
    }
    if (paramString.equals("BUSINESS_NAME")) {
      return "business_name";
    }
    if (paramString.equals("LOW_BUSINESS_NAME")) {
      return "low_business_name";
    }
    if (paramString.equals("PROCESSOR_PIN")) {
      return "processor_pin";
    }
    if (paramString.equals("TAX_ID")) {
      return "tax_id";
    }
    if (paramString.equals("BANK_ID")) {
      return "bank_id";
    }
    if (paramString.equals("BUSINESS_CIF")) {
      return "business_CIF";
    }
    if (paramString.equals("PERSONAL_CIF")) {
      return "personal_CIF";
    }
    if (paramString.equals("PAYEE_ID")) {
      return "payee_id";
    }
    if (paramString.equals("COUNTRY")) {
      return "country";
    }
    if (paramString.equals("NAME")) {
      return "name";
    }
    if (paramString.equals("CURRENCY_TYPE")) {
      return "currency_type";
    }
    if (paramString.equals("BANK_NAME")) {
      return "bank_name";
    }
    if (paramString.equals("POSITIVE_PAY")) {
      return "positive_pay";
    }
    if (paramString.equals("ROUTING_NUM")) {
      return "routing_num";
    }
    if (paramString.equals("BIC_ACCOUNT")) {
      return "bic_account";
    }
    if (paramString.equals("PRIMARY_ACCOUNT")) {
      return "primary_account";
    }
    if (paramString.equals("CORE_ACCOUNT")) {
      return "core_account";
    }
    if (paramString.equals("PERSONAL_ACCOUNT")) {
      return "personal_account";
    }
    if (paramString.equals("EXPORT_BEGIN_DATE")) {
      return "export_begin_date";
    }
    if (paramString.equals("EXPORT_END_DATE")) {
      return "export_end_date";
    }
    if (paramString.equals("BANK_EMAIL_TOKEN")) {
      return "bank_email_token";
    }
    if (paramString.equals("PRIMARY_USER")) {
      return "primary_user";
    }
    if (paramString.equals("ZBAFLAG")) {
      return "ZBAFLAG";
    }
    if (paramString.equals("SHOWPREVOPENLEDGER")) {
      return "SHOWPREVOPENLEDGER";
    }
    if (paramString.equals("MODULE")) {
      return "MODULE";
    }
    if (paramString.equals("log_date")) {
      return "log_date";
    }
    if (paramString.equals("USER_ID")) {
      return "user_id";
    }
    if (paramString.equals("USER_TYPE")) {
      return "user_type";
    }
    if (paramString.equals("AMOUNT")) {
      return "amount";
    }
    if (paramString.equals("START_DATE")) {
      return "start_date";
    }
    if (paramString.equals("CONFIG_ID")) {
      return "config_id";
    }
    if (paramString.equals("KEY_NAME")) {
      return "key_name";
    }
    if (paramString.equals("VALUE")) {
      return "value";
    }
    if (paramString.equals("CONTAINER")) {
      return "container";
    }
    if (paramString.equals("LANGUAGE")) {
      return "language";
    }
    if (paramString.equals("APP_ID")) {
      return "app_id";
    }
    if (paramString.equals("PRODUCT_ID")) {
      return "product_id";
    }
    if (paramString.equals("CATEGORY_ID")) {
      return "category_id";
    }
    if (paramString.equals("FORM_ID")) {
      return "form_id";
    }
    if (paramString.equals("STATUS_ID")) {
      return "status_id";
    }
    if (paramString.equals("DESCRIPTION")) {
      return "description";
    }
    if (paramString.equals("TITLE")) {
      return "title";
    }
    if (paramString.equals("TRACKING_NO")) {
      return "tracking_no";
    }
    if (paramString.equals("CUSTOMER_ID")) {
      return "customer_id";
    }
    if (paramString.equals("FORM_NAME")) {
      return "form_name";
    }
    if (paramString.equals("FIELD_ID")) {
      return "field_id";
    }
    if (paramString.equals("DISPLAY_NAME")) {
      return "display_name";
    }
    if (paramString.equals("FIELD_NAME")) {
      return "field_name";
    }
    if (paramString.equals("FIELD_TYPE")) {
      return "field_type";
    }
    if (paramString.equals("MIN_VALUE")) {
      return "min_value";
    }
    if (paramString.equals("MAX_VALUE")) {
      return "max_value";
    }
    if (paramString.equals("REQUIRED")) {
      return "required";
    }
    if (paramString.equals("FIELD_NUMBER")) {
      return "field_number";
    }
    if (paramString.equals("ERROR_STRING")) {
      return "error_string";
    }
    if (paramString.equals("DEPEND_FIELD")) {
      return "depend_field";
    }
    if (paramString.equals("DEPEND_VALUE")) {
      return "depend_value";
    }
    if (paramString.equals("EXACT_VALUE")) {
      return "exact_value";
    }
    if (paramString.equals("CONTROL_TYPE")) {
      return "control_type";
    }
    if (paramString.equals("AFFILIATE_BANK_ID")) {
      return "affiliate_bank_id";
    }
    if (paramString.equals("OWNER_ID")) {
      return "owner_id";
    }
    if (paramString.equals("MODIFIER_ID")) {
      return "modifier_id";
    }
    if (paramString.equals("HISTORY_COMMENT")) {
      return "history_comment";
    }
    if (paramString.equals("ID")) {
      return "id";
    }
    if (paramString.equals("ID_TYPE")) {
      return "id_type";
    }
    if (paramString.equals("MODIFIER_TYPE")) {
      return "modifier_type";
    }
    if (paramString.equals("DATE_CHANGED")) {
      return "date_changed";
    }
    if (paramString.equals("COLUMN_CHANGED")) {
      return "column_changed";
    }
    if (paramString.equals("OLD_VALUE")) {
      return "old_value";
    }
    if (paramString.equals("NEW_VALUE")) {
      return "new_value";
    }
    if (paramString.equals("TRACKING_ID")) {
      return "tracking_id";
    }
    if (paramString.equals("MESSAGE_ID")) {
      return "message_id";
    }
    if (paramString.equals("QUEUE_ID")) {
      return "queue_id";
    }
    if (paramString.equals("SUBJECT")) {
      return "subject";
    }
    if (paramString.equals("RECORD_TYPE")) {
      return "record_type";
    }
    if (paramString.equals("CASE_NUM")) {
      return "case_num";
    }
    if (paramString.equals("CLOSE_DATE")) {
      return "close_date";
    }
    if (paramString.equals("ITEM_ID")) {
      return "item_id";
    }
    if (paramString.equals("BODY_ID")) {
      return "body_id";
    }
    if (paramString.equals("COMMENT_ID")) {
      return "comment_id";
    }
    if (paramString.equals("TO_ID")) {
      return "to_id";
    }
    if (paramString.equals("TO_TYPE")) {
      return "to_type";
    }
    if (paramString.equals("FROM_ID")) {
      return "from_id";
    }
    if (paramString.equals("FROM_TYPE")) {
      return "from_type";
    }
    if (paramString.equals("READ_DATE")) {
      return "read_date";
    }
    if (paramString.equals("DELETE_DATE")) {
      return "delete_date";
    }
    if (paramString.equals("MESSAGE_TYPE")) {
      return "message_type";
    }
    if (paramString.equals("RESPONSE_ID")) {
      return "response_id";
    }
    if (paramString.equals("RESPONSE_NAME")) {
      return "response_name";
    }
    if (paramString.equals("UPDATED_BY")) {
      return "updated_by";
    }
    if (paramString.equals("TEXT")) {
      return "text";
    }
    if (paramString.equals("QUEUE_TYPE")) {
      return "queue_type";
    }
    if (paramString.equals("QUEUE_NAME")) {
      return "queue_name";
    }
    if (paramString.equals("AUTOREPLY_TEXT")) {
      return "autoreply_text";
    }
    if (paramString.equals("STATUS")) {
      return "status";
    }
    if (paramString.equals("FROM_DATE")) {
      return "from_date";
    }
    if (paramString.equals("TO_DATE")) {
      return "to_date";
    }
    if (paramString.equals("SEQ")) {
      return "seq";
    }
    if (paramString.equals("affiliate_bank_id")) {
      return "affiliate_bank_id";
    }
    if (paramString.equals("affiliate_name")) {
      return "affiliate_name";
    }
    if (paramString.equals("bank_id")) {
      return "bank_id";
    }
    if (paramString.equals("bpw_fi_id")) {
      return "bpw_fi_id";
    }
    if (paramString.equals("bank_type")) {
      return "bank_type";
    }
    if (paramString.equals("currency_code")) {
      return "currency_code";
    }
    if (paramString.equals("brand_id")) {
      return "BRAND_ID";
    }
    if (paramString.equals("corporate_url")) {
      return "corporate_url";
    }
    if (paramString.equals("consumer_url")) {
      return "consumer_url";
    }
    if (paramString.equals("ASSIGNMENT_TYPE")) {
      return "ASSIGNMENT_TYPE";
    }
    if (paramString.equals("ASSIGNMENT")) {
      return "ASSIGNMENT";
    }
    if (paramString.equals("TAX_TYPE")) {
      return "tax_type";
    }
    if (paramString.equals("TAX_CODE")) {
      return "tax_code";
    }
    if (paramString.equals("TAX_STATE")) {
      return "tax_state";
    }
    if (paramString.equals("TAX_NAME")) {
      return "tax_name";
    }
    if (paramString.equals("GLOBAL_MSG_ID")) {
      return "global_msg_id";
    }
    if (paramString.equals("TO_GROUP_ID")) {
      return "to_group_id";
    }
    if (paramString.equals("TO_GROUP_NAME")) {
      return "to_group_name";
    }
    if (paramString.equals("FROM_NAME")) {
      return "from_name";
    }
    if (paramString.equals("GLOBAL_BODY_ID")) {
      return "global_body_id";
    }
    if (paramString.equals("APPROVAL_ID")) {
      return "approval_id";
    }
    if (paramString.equals("APPROVED_DATE")) {
      return "approved_date";
    }
    if (paramString.equals("RECORD_TYPE")) {
      return "record_type";
    }
    if (paramString.equals("MSG_TYPE")) {
      return "msg_type";
    }
    if (paramString.equals("TO_GROUP_TYPE")) {
      return "to_group_type";
    }
    if (paramString.equals("COLOR")) {
      return "color";
    }
    if (paramString.equals("PRIORITY")) {
      return "priority";
    }
    if (paramString.equals("DISPLAY_FROM_DATE")) {
      return "display_from_date";
    }
    if (paramString.equals("DISPLAY_TO_DATE")) {
      return "display_to_date";
    }
    if (paramString.equals("TEMPLATE_NAME")) {
      return "template_name";
    }
    if (paramString.equals("SEND_NOW")) {
      return "send_now";
    }
    if (paramString.equals("TRAN_ID")) {
      return "TRAN_ID";
    }
    if (paramString.equals("SRVR_TID")) {
      return "SRVR_TID";
    }
    return paramString;
  }
  
  static String K(String paramString)
  {
    if (paramString.equals("customer")) {
      return "customer";
    }
    if (paramString.equals("customer_directory")) {
      return "customer_directory";
    }
    if (paramString.equals("accounts")) {
      return "accounts";
    }
    if (paramString.equals("directory_data")) {
      return "directory_data";
    }
    if (paramString.equals("custom_ims_tag")) {
      return "custom_ims_tag";
    }
    if (paramString.equals("bank_employee")) {
      return "bank_employee";
    }
    if (paramString.equals("history")) {
      return "history";
    }
    if (paramString.equals("message")) {
      return "message";
    }
    if (paramString.equals("message_item")) {
      return "message_item";
    }
    if (paramString.equals("message_body")) {
      return "message_body";
    }
    if (paramString.equals("response_body")) {
      return "response_body";
    }
    if (paramString.equals("queue")) {
      return "queue";
    }
    if (paramString.equals("queue_members")) {
      return "queue_members";
    }
    if (paramString.equals("queue_response")) {
      return "queue_response";
    }
    if (paramString.equals("customer_payee")) {
      return "customer_payee";
    }
    if (paramString.equals("bank")) {
      return "bank";
    }
    if (paramString.equals("application")) {
      return "application";
    }
    if (paramString.equals("app_history")) {
      return "app_history";
    }
    if (paramString.equals("product")) {
      return "product";
    }
    if (paramString.equals("product_access")) {
      return "product_access";
    }
    if (paramString.equals("product_status")) {
      return "product_status";
    }
    if (paramString.equals("status")) {
      return "status";
    }
    if (paramString.equals("category")) {
      return "category";
    }
    if (paramString.equals("app_form")) {
      return "app_form";
    }
    if (paramString.equals("form")) {
      return "form";
    }
    if (paramString.equals("form_columns")) {
      return "form_columns";
    }
    if (paramString.equals("contact_info")) {
      return "contact_info";
    }
    if (paramString.equals("business")) {
      return "business";
    }
    if (paramString.equals("business_employee")) {
      return "business_employee";
    }
    if (paramString.equals("assignment")) {
      return "assignment";
    }
    if (paramString.equals("reg_def_category")) {
      return "reg_def_category";
    }
    if (paramString.equals("reg_user_category")) {
      return "reg_user_category";
    }
    if (paramString.equals("reg_payee")) {
      return "reg_payee";
    }
    if (paramString.equals("reg_srvr_tran_cat")) {
      return "reg_srvr_tran_cat";
    }
    if (paramString.equals("reg_transaction")) {
      return "reg_transaction";
    }
    if (paramString.equals("reg_tran_category")) {
      return "reg_tran_category";
    }
    if (paramString.equals("affiliate_bank")) {
      return "affiliate_bank";
    }
    if (paramString.equals("directory_id")) {
      return "DIRECTORY_ID";
    }
    if (paramString.equals("cust_id")) {
      return "CUST_ID";
    }
    if (paramString.equals("directory_date")) {
      return "DIRECTORY_DATE";
    }
    if (paramString.equals("last_active_date")) {
      return "LAST_ACTIVE_DATE";
    }
    if (paramString.equals("create_date")) {
      return "CREATE_DATE";
    }
    if (paramString.equals("account_status")) {
      return "ACCOUNT_STATUS";
    }
    if (paramString.equals("modified_date")) {
      return "MODIFIED_DATE";
    }
    if (paramString.equals("account_id")) {
      return "ACCOUNT_ID";
    }
    if (paramString.equals("ACCOUNT_TYPE")) {
      return "ACCOUNT_TYPE";
    }
    if (paramString.equals("ACCOUNT_NUM")) {
      return "ACCOUNT_NUM";
    }
    if (paramString.equals("nickname")) {
      return "NICKNAME";
    }
    if (paramString.equals("nick_name")) {
      return "NICK_NAME";
    }
    if (paramString.equals("hide")) {
      return "HIDE";
    }
    if (paramString.equals("reg_retrieval_date")) {
      return "REG_RETRIEVAL_DATE";
    }
    if (paramString.equals("reg_default")) {
      return "REG_DEFAULT";
    }
    if (paramString.equals("reg_enabled")) {
      return "REG_ENABLED";
    }
    if (paramString.equals("employee_id")) {
      return "EMPLOYEE_ID";
    }
    if (paramString.equals("user_name")) {
      return "USER_NAME";
    }
    if (paramString.equals("bank_employee_id")) {
      return "BANK_EMPLOYEE_ID";
    }
    if (paramString.equals("password")) {
      return "PASSWORD";
    }
    if (paramString.equals("first_name")) {
      return "FIRST_NAME";
    }
    if (paramString.equals("low_first_name")) {
      return "LOW_FIRST_NAME";
    }
    if (paramString.equals("middle_name")) {
      return "MIDDLE_NAME";
    }
    if (paramString.equals("low_middle_name")) {
      return "LOW_MIDDLE_NAME";
    }
    if (paramString.equals("last_name")) {
      return "LAST_NAME";
    }
    if (paramString.equals("low_last_name")) {
      return "LOW_LAST_NAME";
    }
    if (paramString.equals("email_address")) {
      return "EMAIL_ADDRESS";
    }
    if (paramString.equals("employee_admin_access")) {
      return "EMPLOYEE_ADMIN_ACCESS";
    }
    if (paramString.equals("user_password")) {
      return "USER_PASSWORD";
    }
    if (paramString.equals("address1")) {
      return "ADDRESS1";
    }
    if (paramString.equals("address2")) {
      return "ADDRESS2";
    }
    if (paramString.equals("address3")) {
      return "ADDRESS3";
    }
    if (paramString.equals("city")) {
      return "CITY";
    }
    if (paramString.equals("state")) {
      return "STATE";
    }
    if (paramString.equals("zip")) {
      return "ZIP";
    }
    if (paramString.equals("home_phone")) {
      return "HOME_PHONE";
    }
    if (paramString.equals("work_phone")) {
      return "WORK_PHONE";
    }
    if (paramString.equals("ssn")) {
      return "SSN";
    }
    if (paramString.equals("mothers_maiden")) {
      return "MOTHERS_MAIDEN";
    }
    if (paramString.equals("birth_date")) {
      return "BIRTH_DATE";
    }
    if (paramString.equals("timeout")) {
      return "TIMEOUT";
    }
    if (paramString.equals("password_status")) {
      return "PASSWORD_STATUS";
    }
    if (paramString.equals("password_fail_count")) {
      return "PASSWORD_FAIL_COUNT";
    }
    if (paramString.equals("password_lockout_time")) {
      return "PASSWORD_LOCKOUT_TIME";
    }
    if (paramString.equals("pwd_reminder")) {
      return "PWD_REMINDER";
    }
    if (paramString.equals("reminder")) {
      return "REMINDER";
    }
    if (paramString.equals("personal_banker")) {
      return "PERSONAL_BANKER";
    }
    if (paramString.equals("gender")) {
      return "GENDER";
    }
    if (paramString.equals("greeting")) {
      return "GREETING";
    }
    if (paramString.equals("greeting_type")) {
      return "GREETING_TYPE";
    }
    if (paramString.equals("service_level")) {
      return "SERVICE_LEVEL";
    }
    if (paramString.equals("employee_status")) {
      return "EMPLOYEE_STATUS";
    }
    if (paramString.equals("supervisor")) {
      return "SUPERVISOR";
    }
    if (paramString.equals("employee_notify")) {
      return "EMPLOYEE_NOTIFY";
    }
    if (paramString.equals("account_rep")) {
      return "ACCOUNT_REP";
    }
    if (paramString.equals("approved_by")) {
      return "APPROVED_BY";
    }
    if (paramString.equals("approval_group")) {
      return "APPROVAL_GROUP";
    }
    if (paramString.equals("activation_date")) {
      return "ACTIVATION_DATE";
    }
    if (paramString.equals("prefer_contact")) {
      return "PREFER_CONTACT";
    }
    if (paramString.equals("data_phone")) {
      return "DATA_PHONE";
    }
    if (paramString.equals("fax_phone")) {
      return "FAX_PHONE";
    }
    if (paramString.equals("batch_type")) {
      return "BATCH_TYPE";
    }
    if (paramString.equals("dflt_ppay_decision")) {
      return "DFLT_PPAY_DECISION";
    }
    if (paramString.equals("PRIM_CONTACT_PERMS")) {
      return "PRIM_CONTACT_PERMS";
    }
    if (paramString.equals("SEC_CONTACT_PERMS")) {
      return "SEC_CONTACT_PERMS";
    }
    if (paramString.equals("PASSWORD_TIME")) {
      return "PASSWORD_TIME";
    }
    if (paramString.equals("LASTVIEWINTRATRANS")) {
      return "LASTVIEWINTRATRANS";
    }
    if (paramString.equals("preferred_lang")) {
      return "PREFERRED_LANG";
    }
    if (paramString.equals("reg_def_cat_id")) {
      return "reg_def_cat_id";
    }
    if (paramString.equals("type")) {
      return "type";
    }
    if (paramString.equals("parent_category_id")) {
      return "parent_category_id";
    }
    if (paramString.equals("tax_related")) {
      return "tax_related";
    }
    if (paramString.equals("reg_user_cat_id")) {
      return "reg_user_cat_id";
    }
    if (paramString.equals("reg_transaction_id")) {
      return "reg_transaction_id";
    }
    if (paramString.equals("fi_transaction_id")) {
      return "fi_transaction_id";
    }
    if (paramString.equals("server_tran_id")) {
      return "server_tran_id";
    }
    if (paramString.equals("reference_number")) {
      return "reference_number";
    }
    if (paramString.equals("payee_name")) {
      return "payee_name";
    }
    if (paramString.equals("memo")) {
      return "memo";
    }
    if (paramString.equals("date_issued")) {
      return "date_issued";
    }
    if (paramString.equals("date_cleared")) {
      return "date_cleared";
    }
    if (paramString.equals("reg_payee_id")) {
      return "reg_payee_id";
    }
    if (paramString.equals("rec_server_tran_id")) {
      return "rec_server_tran_id";
    }
    if (paramString.equals("review_required")) {
      return "REVIEW_REQUIRED";
    }
    if (paramString.equals("obo_enabled")) {
      return "OBO_ENABLED";
    }
    if (paramString.equals("approval_provider")) {
      return "APPROVAL_PROVIDER";
    }
    if (paramString.equals("msg_approval_provider")) {
      return "MSG_APPROVAL_PROVIDER";
    }
    if (paramString.equals("affiliate_bank_id")) {
      return "AFFILIATE_BANK_ID";
    }
    if (paramString.equals("CONTACT_ID")) {
      return "CONTACT_ID";
    }
    if (paramString.equals("street")) {
      return "STREET";
    }
    if (paramString.equals("street2")) {
      return "STREET2";
    }
    if (paramString.equals("street3")) {
      return "STREET3";
    }
    if (paramString.equals("postal_code")) {
      return "POSTAL_CODE";
    }
    if (paramString.equals("phone")) {
      return "PHONE";
    }
    if (paramString.equals("phone2")) {
      return "PHONE2";
    }
    if (paramString.equals("fax_number")) {
      return "FAX_NUMBER";
    }
    if (paramString.equals("business_id")) {
      return "BUSINESS_ID";
    }
    if (paramString.equals("BUSINESS_ID_STR")) {
      return "BUSINESS_ID_STR";
    }
    if (paramString.equals("business_name")) {
      return "BUSINESS_NAME";
    }
    if (paramString.equals("low_business_name")) {
      return "LOW_BUSINESS_NAME";
    }
    if (paramString.equals("processor_pin")) {
      return "PROCESSOR_PIN";
    }
    if (paramString.equals("tax_id")) {
      return "TAX_ID";
    }
    if (paramString.equals("bank_id")) {
      return "BANK_ID";
    }
    if (paramString.equals("business_CIF")) {
      return "BUSINESS_CIF";
    }
    if (paramString.equals("personal_CIF")) {
      return "PERSONAL_CIF";
    }
    if (paramString.equals("payee_id")) {
      return "PAYEE_ID";
    }
    if (paramString.equals("country")) {
      return "COUNTRY";
    }
    if (paramString.equals("name")) {
      return "NAME";
    }
    if (paramString.equals("currency_type")) {
      return "CURRENCY_TYPE";
    }
    if (paramString.equals("bank_name")) {
      return "BANK_NAME";
    }
    if (paramString.equals("positive_pay")) {
      return "POSITIVE_PAY";
    }
    if (paramString.equals("routing_num")) {
      return "ROUTING_NUM";
    }
    if (paramString.equals("bic_account")) {
      return "BIC_ACCOUNT";
    }
    if (paramString.equals("primary_account")) {
      return "PRIMARY_ACCOUNT";
    }
    if (paramString.equals("core_account")) {
      return "CORE_ACCOUNT";
    }
    if (paramString.equals("personal_account")) {
      return "PERSONAL_ACCOUNT";
    }
    if (paramString.equals("export_begin_date")) {
      return "EXPORT_BEGIN_DATE";
    }
    if (paramString.equals("export_end_date")) {
      return "EXPORT_END_DATE";
    }
    if (paramString.equals("bank_email_token")) {
      return "BANK_EMAIL_TOKEN";
    }
    if (paramString.equals("primary_user")) {
      return "PRIMARY_USER";
    }
    if (paramString.equals("ZBAFLAG")) {
      return "ZBAFLAG";
    }
    if (paramString.equals("SHOWPREVOPENLEDGER")) {
      return "SHOWPREVOPENLEDGER";
    }
    if (paramString.equals("MODULE")) {
      return "MODULE";
    }
    if (paramString.equals("log_date")) {
      return "log_date";
    }
    if (paramString.equals("user_id")) {
      return "USER_ID";
    }
    if (paramString.equals("user_type")) {
      return "USER_TYPE";
    }
    if (paramString.equals("amount")) {
      return "AMOUNT";
    }
    if (paramString.equals("start_date")) {
      return "START_DATE";
    }
    if (paramString.equals("config_id")) {
      return "CONFIG_ID";
    }
    if (paramString.equals("key_name")) {
      return "KEY_NAME";
    }
    if (paramString.equals("value")) {
      return "VALUE";
    }
    if (paramString.equals("container")) {
      return "CONTAINER";
    }
    if (paramString.equals("language")) {
      return "LANGUAGE";
    }
    if (paramString.equals("app_id")) {
      return "APP_ID";
    }
    if (paramString.equals("product_id")) {
      return "PRODUCT_ID";
    }
    if (paramString.equals("category_id")) {
      return "CATEGORY_ID";
    }
    if (paramString.equals("form_id")) {
      return "FORM_ID";
    }
    if (paramString.equals("status_id")) {
      return "STATUS_ID";
    }
    if (paramString.equals("description")) {
      return "DESCRIPTION";
    }
    if (paramString.equals("title")) {
      return "TITLE";
    }
    if (paramString.equals("tracking_no")) {
      return "TRACKING_NO";
    }
    if (paramString.equals("customer_id")) {
      return "CUSTOMER_ID";
    }
    if (paramString.equals("form_name")) {
      return "FORM_NAME";
    }
    if (paramString.equals("field_id")) {
      return "FIELD_ID";
    }
    if (paramString.equals("display_name")) {
      return "DISPLAY_NAME";
    }
    if (paramString.equals("field_name")) {
      return "FIELD_NAME";
    }
    if (paramString.equals("field_type")) {
      return "FIELD_TYPE";
    }
    if (paramString.equals("min_value")) {
      return "MIN_VALUE";
    }
    if (paramString.equals("max_value")) {
      return "MAX_VALUE";
    }
    if (paramString.equals("required")) {
      return "REQUIRED";
    }
    if (paramString.equals("field_number")) {
      return "FIELD_NUMBER";
    }
    if (paramString.equals("error_string")) {
      return "ERROR_STRING";
    }
    if (paramString.equals("depend_field")) {
      return "DEPEND_FIELD";
    }
    if (paramString.equals("depend_value")) {
      return "DEPEND_VALUE";
    }
    if (paramString.equals("exact_value")) {
      return "EXACT_VALUE";
    }
    if (paramString.equals("control_type")) {
      return "CONTROL_TYPE";
    }
    if (paramString.equals("affiliate_bank_id")) {
      return "AFFILIATE_BANK_ID";
    }
    if (paramString.equals("owner_id")) {
      return "OWNER_ID";
    }
    if (paramString.equals("modifier_id")) {
      return "MODIFIER_ID";
    }
    if (paramString.equals("history_comment")) {
      return "HISTORY_COMMENT";
    }
    if (paramString.equals("id")) {
      return "ID";
    }
    if (paramString.equals("id_type")) {
      return "ID_TYPE";
    }
    if (paramString.equals("modifier_type")) {
      return "MODIFIER_TYPE";
    }
    if (paramString.equals("date_changed")) {
      return "DATE_CHANGED";
    }
    if (paramString.equals("column_changed")) {
      return "COLUMN_CHANGED";
    }
    if (paramString.equals("old_value")) {
      return "OLD_VALUE";
    }
    if (paramString.equals("new_value")) {
      return "NEW_VALUE";
    }
    if (paramString.equals("tracking_id")) {
      return "TRACKING_ID";
    }
    if (paramString.equals("message_id")) {
      return "MESSAGE_ID";
    }
    if (paramString.equals("queue_id")) {
      return "QUEUE_ID";
    }
    if (paramString.equals("subject")) {
      return "SUBJECT";
    }
    if (paramString.equals("record_type")) {
      return "RECORD_TYPE";
    }
    if (paramString.equals("case_num")) {
      return "CASE_NUM";
    }
    if (paramString.equals("close_date")) {
      return "CLOSE_DATE";
    }
    if (paramString.equals("item_id")) {
      return "ITEM_ID";
    }
    if (paramString.equals("body_id")) {
      return "BODY_ID";
    }
    if (paramString.equals("comment_id")) {
      return "COMMENT_ID";
    }
    if (paramString.equals("to_id")) {
      return "TO_ID";
    }
    if (paramString.equals("to_type")) {
      return "TO_TYPE";
    }
    if (paramString.equals("from_id")) {
      return "FROM_ID";
    }
    if (paramString.equals("from_type")) {
      return "FROM_TYPE";
    }
    if (paramString.equals("read_date")) {
      return "READ_DATE";
    }
    if (paramString.equals("delete_date")) {
      return "DELETE_DATE";
    }
    if (paramString.equals("message_type")) {
      return "MESSAGE_TYPE";
    }
    if (paramString.equals("response_id")) {
      return "RESPONSE_ID";
    }
    if (paramString.equals("response_name")) {
      return "RESPONSE_NAME";
    }
    if (paramString.equals("updated_by")) {
      return "UPDATED_BY";
    }
    if (paramString.equals("text")) {
      return "TEXT";
    }
    if (paramString.equals("queue_type")) {
      return "QUEUE_TYPE";
    }
    if (paramString.equals("queue_name")) {
      return "QUEUE_NAME";
    }
    if (paramString.equals("autoreply_text")) {
      return "AUTOREPLY_TEXT";
    }
    if (paramString.equals("status")) {
      return "STATUS";
    }
    if (paramString.equals("from_date")) {
      return "FROM_DATE";
    }
    if (paramString.equals("to_date")) {
      return "TO_DATE";
    }
    if (paramString.equals("seq")) {
      return "SEQ";
    }
    if (paramString.equals("affiliate_bank_id")) {
      return "affiliate_bank_id";
    }
    if (paramString.equals("affiliate_name")) {
      return "affiliate_name";
    }
    if (paramString.equals("bank_id")) {
      return "bank_id";
    }
    if (paramString.equals("bpw_fi_id")) {
      return "bpw_fi_id";
    }
    if (paramString.equals("bank_type")) {
      return "bank_type";
    }
    if (paramString.equals("currency_code")) {
      return "currency_code";
    }
    if (paramString.equals("BRAND_ID")) {
      return "brand_id";
    }
    if (paramString.equals("corporate_url")) {
      return "corporate_url";
    }
    if (paramString.equals("consumer_url")) {
      return "consumer_url";
    }
    if (paramString.equals("ASSIGNMENT_TYPE")) {
      return "ASSIGNMENT_TYPE";
    }
    if (paramString.equals("ASSIGNMENT")) {
      return "ASSIGNMENT";
    }
    if (paramString.equals("tax_type")) {
      return "TAX_TYPE";
    }
    if (paramString.equals("tax_code")) {
      return "TAX_CODE";
    }
    if (paramString.equals("tax_state")) {
      return "TAX_STATE";
    }
    if (paramString.equals("tax_name")) {
      return "TAX_NAME";
    }
    if (paramString.equals("global_msg_id")) {
      return "GLOBAL_MSG_ID";
    }
    if (paramString.equals("to_group_id")) {
      return "TO_GROUP_ID";
    }
    if (paramString.equals("to_group_name")) {
      return "TO_GROUP_NAME";
    }
    if (paramString.equals("from_name")) {
      return "FROM_NAME";
    }
    if (paramString.equals("global_body_id")) {
      return "GLOBAL_BODY_ID";
    }
    if (paramString.equals("approval_id")) {
      return "APPROVAL_ID";
    }
    if (paramString.equals("approved_date")) {
      return "APPROVED_DATE";
    }
    if (paramString.equals("record_type")) {
      return "RECORD_TYPE";
    }
    if (paramString.equals("msg_type")) {
      return "MSG_TYPE";
    }
    if (paramString.equals("to_group_type")) {
      return "TO_GROUP_TYPE";
    }
    if (paramString.equals("color")) {
      return "COLOR";
    }
    if (paramString.equals("priority")) {
      return "PRIORITY";
    }
    if (paramString.equals("display_from_date")) {
      return "DISPLAY_FROM_DATE";
    }
    if (paramString.equals("display_to_date")) {
      return "DISPLAY_TO_DATE";
    }
    if (paramString.equals("template_name")) {
      return "TEMPLATE_NAME";
    }
    if (paramString.equals("send_now")) {
      return "SEND_NOW";
    }
    if (paramString.equals("TRAN_ID")) {
      return "TRAN_ID";
    }
    if (paramString.equals("SRVR_TID")) {
      return "SRVR_TID";
    }
    return paramString.toUpperCase();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.Profile
 * JD-Core Version:    0.7.0.1
 */