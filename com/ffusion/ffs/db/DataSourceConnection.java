package com.ffusion.ffs.db;

import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

class DataSourceConnection
  implements FFSConst
{
  public static FFSConnection getNewConnection(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    DataSource localDataSource = jdMethod_new(paramFFSDBProperties._connInfo._dbConnInfo._dsn);
    Connection localConnection = null;
    Object localObject = null;
    if (paramFFSDBProperties != null)
    {
      str1 = paramFFSDBProperties._connInfo._dbConnInfo._user;
      str2 = paramFFSDBProperties._connInfo._dbConnInfo._password;
    }
    else
    {
      str1 = null;
      str2 = null;
    }
    try
    {
      if (str1 == null) {
        localConnection = localDataSource.getConnection();
      } else {
        localConnection = localDataSource.getConnection(str1, str2);
      }
    }
    catch (SQLException localSQLException)
    {
      throw new FFSException(localSQLException.getMessage());
    }
    return new FFSConnection(localConnection, paramFFSDBProperties);
  }
  
  private static final DataSource jdMethod_new(String paramString)
    throws FFSException
  {
    DataSource localDataSource = null;
    try
    {
      InitialContext localInitialContext = new InitialContext();
      localDataSource = (DataSource)localInitialContext.lookup(paramString);
      if (localDataSource == null) {
        throw new NullPointerException();
      }
      localInitialContext.close();
    }
    catch (NamingException localNamingException)
    {
      throw new FFSException("Failed to get Datasource name from JNDI" + localNamingException.getMessage());
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new FFSException("Failed to get Datasource name from JNDI" + localNullPointerException.getMessage());
    }
    return localDataSource;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.DataSourceConnection
 * JD-Core Version:    0.7.0.1
 */