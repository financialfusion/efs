package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEDBParams;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.SQLException;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

class OracleClassWrapperHack
{
  void a(DBConnection paramDBConnection, String paramString, int paramInt, Object[] paramArrayOfObject)
    throws SQLException, IOException
  {
    if ((paramDBConnection.aS().getConnectionType() != 4) || (paramDBConnection.aS().isNativeDriver())) {
      throw new SQLException("Error: can't execute thin LOB stream on non-Oracle or non-thin driver");
    }
    Object[] arrayOfObject = { new Integer(paramInt) };
    DBResultSet localDBResultSet = paramDBConnection.I(paramString);
    localDBResultSet.a(arrayOfObject);
    try
    {
      if (localDBResultSet.a()) {
        for (int i = 0; i < paramArrayOfObject.length; i++)
        {
          Object localObject1;
          Object localObject2;
          if ((paramArrayOfObject[i] instanceof String))
          {
            localObject1 = (CLOB)localDBResultSet.jdMethod_goto(i + 1);
            localObject2 = ((CLOB)localObject1).getCharacterOutputStream();
            ((Writer)localObject2).write((String)paramArrayOfObject[i]);
            ((Writer)localObject2).flush();
            ((Writer)localObject2).close();
          }
          else if ((paramArrayOfObject[i] instanceof byte[]))
          {
            localObject1 = (BLOB)localDBResultSet.jdMethod_null(i + 1);
            localObject2 = ((BLOB)localObject1).getBinaryOutputStream();
            ((OutputStream)localObject2).write((byte[])paramArrayOfObject[i]);
            ((OutputStream)localObject2).flush();
            ((OutputStream)localObject2).close();
          }
          else
          {
            throw new SQLException("Error: Unsupported Oracle LOB type");
          }
        }
      }
    }
    finally
    {
      localDBResultSet.jdMethod_for();
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.OracleClassWrapperHack
 * JD-Core Version:    0.7.0.1
 */