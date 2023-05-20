package com.ffusion.ffs.db;

import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.interfaces.FFSStringHolder;
import com.ffusion.ffs.util.FFSDebug;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class FFSDBUtils
{
  public static void fillParameters(PreparedStatement paramPreparedStatement, Object[] paramArrayOfObject)
    throws SQLException
  {
    if (paramArrayOfObject != null)
    {
      Object localObject = null;
      for (int i = 0; i < paramArrayOfObject.length; i++)
      {
        localObject = paramArrayOfObject[i];
        if ((localObject == null) || ((localObject instanceof String)))
        {
          paramPreparedStatement.setString(i + 1, (String)localObject);
        }
        else if ((localObject instanceof byte[]))
        {
          paramPreparedStatement.setBytes(i + 1, (byte[])localObject);
        }
        else if ((localObject instanceof Long))
        {
          paramPreparedStatement.setLong(i + 1, ((Long)localObject).longValue());
        }
        else if ((localObject instanceof Integer))
        {
          paramPreparedStatement.setInt(i + 1, ((Integer)localObject).intValue());
        }
        else if ((localObject instanceof Short))
        {
          paramPreparedStatement.setShort(i + 1, ((Short)localObject).shortValue());
        }
        else if ((localObject instanceof Float))
        {
          paramPreparedStatement.setFloat(i + 1, ((Float)localObject).floatValue());
        }
        else if ((localObject instanceof Double))
        {
          paramPreparedStatement.setDouble(i + 1, ((Double)localObject).doubleValue());
        }
        else if ((localObject instanceof ByteArrayOutputStream))
        {
          paramPreparedStatement.setBytes(i + 1, ((ByteArrayOutputStream)localObject).toByteArray());
        }
        else if ((localObject instanceof Timestamp))
        {
          paramPreparedStatement.setTimestamp(i + 1, (Timestamp)localObject);
        }
        else if ((localObject instanceof BigDecimal))
        {
          paramPreparedStatement.setBigDecimal(i + 1, (BigDecimal)localObject);
        }
        else if ((localObject instanceof Date))
        {
          paramPreparedStatement.setDate(i + 1, (Date)localObject);
        }
        else if ((localObject instanceof Time))
        {
          paramPreparedStatement.setTime(i + 1, (Time)localObject);
        }
        else if ((localObject instanceof FFSStringHolder))
        {
          String str = ((FFSStringHolder)localObject).value();
          if (str != null)
          {
            StringReader localStringReader = new StringReader(str);
            paramPreparedStatement.setCharacterStream(i + 1, localStringReader, str.length());
          }
        }
        else
        {
          if (localObject != null) {
            throw new SQLException("Error: unsupported datatype: " + localObject.getClass().getName());
          }
          throw new SQLException("Error: unsupported datatype, object is null: " + localObject);
        }
      }
    }
  }
  
  public static void fillParameters(PreparedStatement paramPreparedStatement, Object[] paramArrayOfObject, int paramInt)
    throws SQLException
  {
    if (paramArrayOfObject != null) {
      for (int i = 0; i < paramArrayOfObject.length; i++)
      {
        Object localObject = paramArrayOfObject[i];
        if ((localObject == null) || ((localObject instanceof String))) {
          paramPreparedStatement.setString(i + 1, (String)localObject);
        } else if ((localObject instanceof Integer)) {
          paramPreparedStatement.setInt(i + 1, ((Integer)localObject).intValue());
        } else if ((localObject instanceof Long)) {
          paramPreparedStatement.setLong(i + 1, ((Long)localObject).longValue());
        } else if ((localObject instanceof BigDecimal)) {
          paramPreparedStatement.setBigDecimal(i + 1, (BigDecimal)localObject);
        } else if ((localObject instanceof ByteArrayOutputStream)) {
          paramPreparedStatement.setBytes(i + 1, ((ByteArrayOutputStream)localObject).toByteArray());
        } else if ((localObject instanceof ByteArrayInputStream)) {
          paramPreparedStatement.setAsciiStream(i + 1, (ByteArrayInputStream)localObject, paramInt);
        } else if ((localObject instanceof Timestamp)) {
          paramPreparedStatement.setTimestamp(i + 1, (Timestamp)localObject);
        } else if ((localObject instanceof Date)) {
          paramPreparedStatement.setDate(i + 1, (Date)localObject);
        } else if ((localObject instanceof Time)) {
          paramPreparedStatement.setTime(i + 1, (Time)localObject);
        } else {
          throw new SQLException("Error: unsupported datatype");
        }
      }
    }
  }
  
  public static String getDriver(String paramString)
    throws FFSException
  {
    if (paramString == null)
    {
      FFSDebug.log("The database type may not be null", 0);
      throw new FFSException("ERROR: The database type may not be null");
    }
    if (paramString.equalsIgnoreCase("ASA")) {
      return "com.sybase.jdbc2.jdbc.SybDriver";
    }
    if (paramString.equalsIgnoreCase("ORACLE:oci8")) {
      return "oracle.jdbc.driver.OracleDriver";
    }
    if (paramString.equalsIgnoreCase("ORACLE:thin")) {
      return "oracle.jdbc.driver.OracleDriver";
    }
    if (paramString.equalsIgnoreCase("DB2:app")) {
      return "COM.ibm.db2.jdbc.app.DB2Driver";
    }
    if (paramString.equalsIgnoreCase("DB2:net")) {
      return "COM.ibm.db2.jdbc.net.DB2Driver";
    }
    if (paramString.equalsIgnoreCase("DB2:390")) {
      return "COM.ibm.db2os390.sqlj.jdbc.DB2SQLJDriver";
    }
    if (paramString.equalsIgnoreCase("DB2:un2")) {
      return "com.ibm.db2.jcc.DB2Driver";
    }
    if (paramString.equalsIgnoreCase("ASE")) {
      return "com.sybase.jdbc3.jdbc.SybDriver";
    }
    if (paramString.equalsIgnoreCase("MSSQL:thin")) {
      return "com.jnetdirect.jsql.JSQLDriver";
    }
    FFSDebug.log("This database type is not supported: ", paramString, 0);
    throw new FFSException("ERROR: This database type is not supported: " + paramString);
  }
  
  public static String getDbUrl(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    String str1 = null;
    if (paramFFSDBProperties._connInfo._dbConnInfo._driver == null) {
      paramFFSDBProperties._connInfo._dbConnInfo._driver = getDriver(paramFFSDBProperties._connInfo._dbConnInfo._dbType);
    }
    if (paramFFSDBProperties._connInfo._dbConnInfo._driver.equals("sun.jdbc.odbc.JdbcOdbcDriver"))
    {
      str1 = "jdbc:odbc:" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
    }
    else if ((paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("DB2:app")) || (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("DB2:un2")))
    {
      str1 = "jdbc:db2:" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
    }
    else if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("DB2:net"))
    {
      if (paramFFSDBProperties._connInfo._dbConnInfo._port > 0) {
        str1 = "jdbc:db2://" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._port + "/" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      } else {
        str1 = "jdbc:db2://" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      }
    }
    else if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("DB2:390"))
    {
      if (paramFFSDBProperties._connInfo._dbConnInfo._port > 0) {
        str1 = "jdbc:db2os390://" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._port + "/" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      } else {
        str1 = "jdbc:db2os390://" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      }
    }
    else if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("ASA"))
    {
      str1 = "jdbc:sybase:Tds:" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._port;
    }
    else if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("ASE"))
    {
      str1 = "jdbc:sybase:Tds:" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._port;
    }
    else if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("ORACLE:oci8"))
    {
      if ((paramFFSDBProperties._connInfo._dbConnInfo._host.equals("")) || (paramFFSDBProperties._connInfo._dbConnInfo._host == null) || (paramFFSDBProperties._connInfo._dbConnInfo._port < 0)) {
        str1 = "jdbc:oracle:oci8:@" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      } else {
        str1 = "jdbc:oracle:oci8:@(description=(address=(host=" + paramFFSDBProperties._connInfo._dbConnInfo._host + ")(protocol=tcp)(port=" + paramFFSDBProperties._connInfo._dbConnInfo._port + "))(connect_data=(sid=" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName + ")))";
      }
    }
    else if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equalsIgnoreCase("ORACLE:thin"))
    {
      str1 = "jdbc:oracle:thin:@" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._port;
      if ((paramFFSDBProperties._connInfo._dbConnInfo._databaseName != null) && (paramFFSDBProperties._connInfo._dbConnInfo._databaseName.trim().length() > 0)) {
        str1 = str1 + ":" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      }
    }
    else if (!paramFFSDBProperties._connInfo._dbConnInfo._dbType.equals("SQLSVR"))
    {
      if (paramFFSDBProperties._connInfo._dbConnInfo._dbType.equals("MSSQL:thin"))
      {
        str1 = "jdbc:JSQLConnect://" + paramFFSDBProperties._connInfo._dbConnInfo._host + ":" + paramFFSDBProperties._connInfo._dbConnInfo._port + "/database=" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
      }
      else
      {
        FFSDebug.log("DriverConnection.getURL(): " + paramFFSDBProperties._pureProps, 6);
        String str2 = "Unsupported databaseType: [" + paramFFSDBProperties._connInfo._dbConnInfo._dbType + "]";
        FFSDebug.log(str2, 0);
        throw new FFSException(str2);
      }
    }
    if ((paramFFSDBProperties._connInfo._dbConnInfo._databaseName.length() > 0) && (paramFFSDBProperties._connInfo._dbConnInfo._dbType.startsWith("AS"))) {
      str1 = str1 + "/" + paramFFSDBProperties._connInfo._dbConnInfo._databaseName;
    }
    FFSDebug.log("DriverConnection.getURL(): " + str1, 2);
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSDBUtils
 * JD-Core Version:    0.7.0.1
 */