package com.ffusion.ffs.db;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FFSResultSet
{
  private ResultSet jdField_byte;
  private int jdField_new;
  private PreparedStatement jdField_for;
  private int jdField_if;
  private boolean jdField_do;
  private boolean jdField_case;
  private boolean jdField_int;
  private boolean jdField_try;
  private boolean jdField_char;
  private ResultSetMetaData a;
  
  public FFSResultSet(boolean paramBoolean, PreparedStatement paramPreparedStatement)
  {
    this.jdField_case = paramBoolean;
    this.jdField_do = true;
    this.jdField_new = 0;
    this.jdField_byte = null;
    this.a = null;
    this.jdField_for = paramPreparedStatement;
  }
  
  protected void finalize()
    throws FFSException
  {
    if (!this.jdField_do) {
      close();
    }
  }
  
  public void close()
    throws FFSException
  {
    try
    {
      if (this.jdField_byte != null)
      {
        this.jdField_byte.close();
        this.jdField_byte = null;
        this.a = null;
      }
      this.jdField_for = null;
      this.jdField_do = true;
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public void open(Object[] paramArrayOfObject)
    throws FFSException
  {
    try
    {
      if (this.jdField_byte != null)
      {
        this.jdField_byte.close();
        this.jdField_byte = null;
        this.a = null;
      }
      FFSDBUtils.fillParameters(this.jdField_for, paramArrayOfObject);
      this.jdField_byte = this.jdField_for.executeQuery();
      this.a = this.jdField_byte.getMetaData();
      this.jdField_if = this.a.getColumnCount();
      this.jdField_char = false;
      this.jdField_do = false;
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public boolean getNextRow()
    throws FFSException
  {
    try
    {
      if (this.jdField_char) {
        a(true);
      }
      if (this.jdField_byte != null) {
        return this.jdField_byte.next();
      }
      FFSDebug.log("ERROR:  NULL result set encountered!", 0);
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
    return false;
  }
  
  public ResultSetMetaData getResultSetMetaData()
    throws FFSException
  {
    if (this.jdField_char) {
      a(true);
    }
    return this.a;
  }
  
  private boolean a(boolean paramBoolean)
    throws FFSException
  {
    try
    {
      if (this.jdField_do) {
        return false;
      }
      if (this.jdField_char) {
        this.jdField_char = false;
      } else {
        this.jdField_try = this.jdField_for.getMoreResults();
      }
      if ((!a()) && (paramBoolean))
      {
        this.jdField_try = this.jdField_for.getMoreResults();
        a();
      }
      return this.jdField_int;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public String[] getColumnsAsArray()
    throws FFSException
  {
    String[] arrayOfString = new String[this.jdField_if];
    for (int i = 0; i < this.jdField_if; i++) {
      arrayOfString[i] = getColumnString(i + 1);
    }
    return arrayOfString;
  }
  
  public String getColumnString(int paramInt)
    throws FFSException
  {
    String str = null;
    try
    {
      str = this.jdField_byte.getString(paramInt);
      if ((!this.jdField_byte.wasNull()) && (this.a.getColumnType(paramInt) == 1)) {
        str = str.trim();
      }
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
    return str;
  }
  
  public String getColumnString(String paramString)
    throws FFSException
  {
    String str = null;
    try
    {
      str = this.jdField_byte.getString(paramString);
      if (this.jdField_byte.wasNull()) {
        str = null;
      }
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
    return str;
  }
  
  public String getColumnStringNoTrim(int paramInt)
    throws FFSException
  {
    String str = null;
    try
    {
      str = this.jdField_byte.getString(paramInt);
      if (this.jdField_byte.wasNull()) {
        str = null;
      }
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
    return str;
  }
  
  public int getColumnInt(int paramInt)
    throws FFSException
  {
    try
    {
      if (this.jdField_case)
      {
        String str = getColumnString(paramInt);
        try
        {
          return Integer.valueOf(str).intValue();
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return 0;
        }
      }
      return this.jdField_byte.getInt(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public int getColumnInt(String paramString)
    throws FFSException
  {
    try
    {
      if (this.jdField_case)
      {
        String str = getColumnString(paramString);
        try
        {
          return Integer.valueOf(str).intValue();
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return 0;
        }
      }
      return this.jdField_byte.getInt(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public long getColumnLong(int paramInt)
    throws FFSException
  {
    try
    {
      if (this.jdField_case)
      {
        String str = getColumnString(paramInt);
        try
        {
          return Long.valueOf(str).longValue();
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return 0L;
        }
      }
      return this.jdField_byte.getLong(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public long getColumnLong(String paramString)
    throws FFSException
  {
    try
    {
      if (this.jdField_case)
      {
        String str = getColumnString(paramString);
        try
        {
          return Long.valueOf(str).longValue();
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return 0L;
        }
      }
      return this.jdField_byte.getLong(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public boolean getColumnBool(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getBoolean(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public boolean getColumnBool(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getBoolean(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public byte[] getColumnBytes(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getBytes(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public byte[] getColumnBytes(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getBytes(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public Timestamp getColumnTimestamp(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getTimestamp(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public Timestamp getColumnTimestamp(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getTimestamp(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public float getColumnFloat(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getFloat(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public float getColumnFloat(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getFloat(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public double getColumnDouble(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getDouble(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public double getColumnDouble(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getDouble(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public long getColumnBigIntAsLong(int paramInt)
    throws FFSException
  {
    BigDecimal localBigDecimal = null;
    try
    {
      localBigDecimal = this.jdField_byte.getBigDecimal(paramInt);
      if (this.jdField_byte.wasNull()) {
        return 0L;
      }
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
    return localBigDecimal.longValue();
  }
  
  public long getColumnBigIntAsLong(String paramString)
    throws FFSException
  {
    BigDecimal localBigDecimal = null;
    try
    {
      localBigDecimal = this.jdField_byte.getBigDecimal(paramString);
      if (this.jdField_byte.wasNull()) {
        return 0L;
      }
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
    return localBigDecimal.longValue();
  }
  
  public BigDecimal getColumnBigDecimal(int paramInt)
    throws FFSException
  {
    Object localObject = null;
    try
    {
      return this.jdField_byte.getBigDecimal(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public BigDecimal getColumnBigDecimal(String paramString)
    throws FFSException
  {
    Object localObject = null;
    try
    {
      return this.jdField_byte.getBigDecimal(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), localSQLException.getMessage(), localSQLException.getSQLState());
    }
  }
  
  public boolean wasNull()
    throws SQLException
  {
    return this.jdField_byte.wasNull();
  }
  
  private boolean a()
    throws SQLException
  {
    this.a = null;
    this.jdField_int = false;
    if (this.jdField_try)
    {
      this.jdField_byte = this.jdField_for.getResultSet();
      if (this.jdField_byte != null)
      {
        this.a = this.jdField_byte.getMetaData();
        this.jdField_if = this.a.getColumnCount();
        this.jdField_int = true;
      }
    }
    else
    {
      int i = this.jdField_for.getUpdateCount();
      if (i != -1)
      {
        this.jdField_int = true;
        return false;
      }
    }
    return true;
  }
  
  public InputStream getColumnBinaryStream(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getBinaryStream(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), FFSDebug.stackTrace(localSQLException), localSQLException.getSQLState());
    }
  }
  
  public InputStream getColumnBinaryStream(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getBinaryStream(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), FFSDebug.stackTrace(localSQLException), localSQLException.getSQLState());
    }
  }
  
  public Clob getColumnClob(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getClob(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), FFSDebug.stackTrace(localSQLException), localSQLException.getSQLState());
    }
  }
  
  public Clob getColumnClob(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getClob(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), FFSDebug.stackTrace(localSQLException), localSQLException.getSQLState());
    }
  }
  
  public Object getColumnObject(int paramInt)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getObject(paramInt);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), FFSDebug.stackTrace(localSQLException), localSQLException.getSQLState());
    }
  }
  
  public Object getColumnObject(String paramString)
    throws FFSException
  {
    try
    {
      return this.jdField_byte.getObject(paramString);
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getErrorCode(), FFSDebug.stackTrace(localSQLException), localSQLException.getSQLState());
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSResultSet
 * JD-Core Version:    0.7.0.1
 */