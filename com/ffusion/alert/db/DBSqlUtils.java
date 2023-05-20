package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEDBParams;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class DBSqlUtils
  implements DBSQLConstants
{
  public static boolean a(AEDBParams paramAEDBParams, String paramString)
  {
    if (paramAEDBParams.getConnectionType() != 4) {
      return false;
    }
    return (paramString != null) && (paramString.length() > 4000);
  }
  
  public static boolean jdMethod_if(AEDBParams paramAEDBParams, byte[] paramArrayOfByte)
  {
    if (paramAEDBParams.getConnectionType() != 4) {
      return false;
    }
    return (paramArrayOfByte != null) && (paramArrayOfByte.length > 4000);
  }
  
  public static boolean jdMethod_if(AEDBParams paramAEDBParams, String paramString)
  {
    if (paramAEDBParams.isNativeDriver()) {
      return false;
    }
    return a(paramAEDBParams, paramString);
  }
  
  public static boolean a(AEDBParams paramAEDBParams, byte[] paramArrayOfByte)
  {
    if (paramAEDBParams.isNativeDriver()) {
      return false;
    }
    return jdMethod_if(paramAEDBParams, paramArrayOfByte);
  }
  
  static void a(PreparedStatement paramPreparedStatement, Object[] paramArrayOfObject, AEDBParams paramAEDBParams)
    throws SQLException
  {
    paramPreparedStatement.clearParameters();
    if (paramArrayOfObject != null) {
      for (int i = 0; i < paramArrayOfObject.length; i++)
      {
        Object localObject1 = paramArrayOfObject[i];
        if (localObject1 == null)
        {
          paramPreparedStatement.setString(i + 1, null);
        }
        else
        {
          Object localObject2;
          if ((localObject1 instanceof String))
          {
            localObject2 = (String)localObject1;
            if (a(paramAEDBParams, (String)localObject2)) {
              paramPreparedStatement.setCharacterStream(i + 1, new StringReader((String)localObject2), ((String)localObject2).length());
            } else if ((paramAEDBParams.getConnectionType() == 4) && (((String)localObject2).length() == 0)) {
              paramPreparedStatement.setString(i + 1, " ");
            } else {
              paramPreparedStatement.setString(i + 1, (String)localObject2);
            }
          }
          else if ((localObject1 instanceof Integer))
          {
            paramPreparedStatement.setInt(i + 1, ((Integer)localObject1).intValue());
          }
          else if ((localObject1 instanceof Long))
          {
            paramPreparedStatement.setLong(i + 1, ((Long)localObject1).longValue());
          }
          else if ((localObject1 instanceof ByteArrayOutputStream))
          {
            paramPreparedStatement.setBytes(i + 1, ((ByteArrayOutputStream)localObject1).toByteArray());
          }
          else if ((localObject1 instanceof Float))
          {
            paramPreparedStatement.setFloat(i + 1, ((Float)localObject1).floatValue());
          }
          else if ((localObject1 instanceof Double))
          {
            paramPreparedStatement.setDouble(i + 1, ((Double)localObject1).doubleValue());
          }
          else if ((localObject1 instanceof Timestamp))
          {
            paramPreparedStatement.setTimestamp(i + 1, (Timestamp)localObject1);
          }
          else if ((localObject1 instanceof Date))
          {
            paramPreparedStatement.setDate(i + 1, (Date)localObject1);
          }
          else if ((localObject1 instanceof Time))
          {
            paramPreparedStatement.setTime(i + 1, (Time)localObject1);
          }
          else if ((localObject1 instanceof byte[]))
          {
            localObject2 = (byte[])localObject1;
            if (jdMethod_if(paramAEDBParams, (byte[])localObject2)) {
              paramPreparedStatement.setBinaryStream(i + 1, new ByteArrayInputStream((byte[])localObject2), localObject2.length);
            } else {
              paramPreparedStatement.setBytes(i + 1, (byte[])localObject2);
            }
          }
          else
          {
            throw new SQLException("Error: unsupported datatype");
          }
        }
      }
    }
  }
  
  static String a(String paramString, int paramInt)
  {
    int i = w(paramInt);
    StringBuffer localStringBuffer = new StringBuffer(paramString.length());
    int m = 0;
    for (;;)
    {
      int j = paramString.indexOf("{", m);
      if (j == -1) {
        break;
      }
      int n = paramString.indexOf("}", j);
      localStringBuffer = localStringBuffer.append(paramString.substring(m, j));
      int k = j;
      int i3 = 1;
      for (int i1 = 1; i1 < i; i1++)
      {
        k = paramString.indexOf("|", k + 1);
        if ((k == -1) || (k > n))
        {
          i3 = 0;
          break;
        }
        if (paramString.charAt(k - 1) == '!') {
          i1--;
        }
      }
      if (i3 == 0)
      {
        m = n + 1;
      }
      else
      {
        k++;
        for (;;)
        {
          int i2 = paramString.indexOf("|", k);
          if ((i2 == -1) || (i2 > n))
          {
            localStringBuffer = localStringBuffer.append(paramString.substring(k, n));
            break;
          }
          if (paramString.charAt(i2 - 1) == '!')
          {
            localStringBuffer = localStringBuffer.append(paramString.substring(k, i2 - 1));
            localStringBuffer = localStringBuffer.append('|');
          }
          else
          {
            localStringBuffer = localStringBuffer.append(paramString.substring(k, i2));
            break;
          }
          k = i2 + 1;
        }
        m = n + 1;
      }
    }
    String str;
    if (m > 0)
    {
      localStringBuffer = localStringBuffer.append(paramString.substring(m, paramString.length()));
      str = localStringBuffer.toString();
    }
    else
    {
      str = paramString;
    }
    return str;
  }
  
  private static int w(int paramInt)
  {
    switch (paramInt)
    {
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
      return paramInt;
    case 6: 
      return 3;
    }
    throw new IllegalArgumentException("Unknown database connection type: " + paramInt);
  }
  
  public static final SQLException a(SQLException paramSQLException)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramSQLException.printStackTrace(localPrintWriter);
    localPrintWriter.flush();
    localPrintWriter.close();
    return new SQLException(localStringWriter.toString(), paramSQLException.getSQLState(), paramSQLException.getErrorCode());
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DBSqlUtils
 * JD-Core Version:    0.7.0.1
 */