package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEDBParams;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBResultSet
{
  private ResultSet jdField_case = null;
  private PreparedStatement jdField_for;
  private int jdField_if;
  private boolean jdField_do = true;
  private ResultSetMetaData a = null;
  private int jdField_int;
  private int jdField_try;
  private AEDBParams jdField_byte;
  private int jdField_new;
  
  DBResultSet(PreparedStatement paramPreparedStatement, AEDBParams paramAEDBParams)
  {
    this.jdField_for = paramPreparedStatement;
    this.jdField_byte = paramAEDBParams;
    this.jdField_new = 0;
  }
  
  public final void jdField_for()
    throws SQLException
  {
    try
    {
      if (this.jdField_case != null)
      {
        this.jdField_case.close();
        this.jdField_case = null;
        this.a = null;
      }
    }
    catch (SQLException localSQLException)
    {
      if ((!this.jdField_byte.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
        throw localSQLException;
      }
    }
    this.jdField_do = true;
    this.jdField_int = 0;
    this.jdField_try = 0;
  }
  
  public final void a(Object[] paramArrayOfObject, int paramInt)
    throws SQLException
  {
    this.jdField_new = (paramInt < 0 ? 0 : paramInt);
    int i = 0;
    for (;;)
    {
      try
      {
        if (this.jdField_case != null)
        {
          this.jdField_case.close();
          this.jdField_case = null;
          this.a = null;
        }
        DBSqlUtils.a(this.jdField_for, paramArrayOfObject, this.jdField_byte);
        jdField_if();
        return;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final void jdField_try()
    throws SQLException
  {
    a(null, 0);
  }
  
  public final void jdField_case(int paramInt)
    throws SQLException
  {
    a(null, paramInt);
  }
  
  public final void a(Object[] paramArrayOfObject)
    throws SQLException
  {
    a(paramArrayOfObject, 0);
  }
  
  private void jdField_if()
    throws SQLException
  {
    this.jdField_for.setMaxRows(this.jdField_new);
    this.jdField_case = this.jdField_for.executeQuery();
    this.a = this.jdField_case.getMetaData();
    this.jdField_if = this.a.getColumnCount();
    this.jdField_do = false;
  }
  
  public final boolean a()
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = 0;
        if (this.jdField_case != null)
        {
          boolean bool = this.jdField_case.next();
          this.jdField_int += 1;
          return bool;
        }
        return false;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final ResultSetMetaData jdField_do()
  {
    return this.a;
  }
  
  public final String[] jdField_byte()
    throws SQLException
  {
    String[] arrayOfString = new String[this.jdField_if];
    int i = 0;
    for (;;)
    {
      try
      {
        int j = 0;
        if (j < this.jdField_if)
        {
          arrayOfString[j] = jdField_try(j + 1);
          j++;
        }
        else
        {
          return arrayOfString;
        }
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final String jdField_try(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        String str = this.jdField_case.getString(paramInt);
        if ((!this.jdField_case.wasNull()) && (this.a.getColumnType(paramInt) == 1)) {
          str = str.trim();
        }
        return str;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final String jdField_new(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        String str = this.jdField_case.getString(paramInt);
        return str;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final String jdField_do(int paramInt)
    throws SQLException
  {
    if (this.jdField_byte.getConnectionType() != 4) {
      return jdField_try(paramInt);
    }
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        Clob localClob = this.jdField_case.getClob(paramInt);
        if (this.jdField_case.wasNull()) {
          return null;
        }
        String str = localClob.getSubString(1L, (int)localClob.length());
        return str;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final byte[] jdField_if(int paramInt)
    throws SQLException
  {
    if (this.jdField_byte.getConnectionType() != 4) {
      return jdField_for(paramInt);
    }
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        Blob localBlob = this.jdField_case.getBlob(paramInt);
        if (this.jdField_case.wasNull()) {
          return null;
        }
        byte[] arrayOfByte = localBlob.getBytes(1L, (int)localBlob.length());
        return arrayOfByte;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public int jdMethod_else(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        BigDecimal localBigDecimal = this.jdField_case.getBigDecimal(paramInt);
        if (this.jdField_case.wasNull()) {
          return 0;
        }
        return localBigDecimal.intValue();
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final int jdField_int(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        return this.jdField_case.getInt(paramInt);
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final long jdMethod_char(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        return this.jdField_case.getLong(paramInt);
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final boolean jdField_byte(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        return this.jdField_case.getBoolean(paramInt);
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final byte[] jdField_for(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        return this.jdField_case.getBytes(paramInt);
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final long a(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        BigDecimal localBigDecimal = this.jdField_case.getBigDecimal(paramInt);
        if (this.jdField_case.wasNull()) {
          return 0L;
        }
        return localBigDecimal.longValue();
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final Clob jdMethod_goto(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        return this.jdField_case.getClob(paramInt);
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final Blob jdMethod_null(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        return this.jdField_case.getBlob(paramInt);
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final Timestamp jdMethod_long(int paramInt)
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        this.jdField_try = paramInt;
        Timestamp localTimestamp = this.jdField_case.getTimestamp(paramInt);
        return localTimestamp;
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, false, i);
        i++;
      }
    }
  }
  
  public final boolean jdField_new()
    throws SQLException
  {
    int i = 0;
    for (;;)
    {
      try
      {
        return this.jdField_case.wasNull();
      }
      catch (SQLException localSQLException)
      {
        a(localSQLException, true, i);
        i++;
      }
    }
  }
  
  public final AEDBParams jdField_int()
  {
    return this.jdField_byte;
  }
  
  private void a(SQLException paramSQLException, boolean paramBoolean, int paramInt)
    throws SQLException
  {
    if ((!this.jdField_byte.isHA()) || ((!paramSQLException.getSQLState().equals("JZ0F2")) && (!paramSQLException.getSQLState().equals("JZ006")))) {
      throw paramSQLException;
    }
    if (paramInt >= 3) {
      throw paramSQLException;
    }
    int i = this.jdField_int;
    int j = this.jdField_try;
    jdField_if();
    while (i != this.jdField_int) {
      if (!a()) {
        throw paramSQLException;
      }
    }
    if ((paramBoolean) && (j > 0)) {
      this.jdField_case.getString(j);
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DBResultSet
 * JD-Core Version:    0.7.0.1
 */