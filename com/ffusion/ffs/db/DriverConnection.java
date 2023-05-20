package com.ffusion.ffs.db;

import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DriverConnection
  implements FFSDBConst, FFSConst
{
  public static FFSConnection getNewConnection(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    Connection localConnection = null;
    String str1 = paramFFSDBProperties._connInfo._dbConnInfo._dbType;
    FFSDebug.log("Database type: [" + str1 + "]", 2);
    if ((paramFFSDBProperties._connInfo._dbConnInfo._driver == null) || (paramFFSDBProperties._connInfo._dbConnInfo._driver.length() == 0)) {
      if ((str1 != null) && (str1.length() != 0))
      {
        paramFFSDBProperties._connInfo._dbConnInfo.setDriver(FFSDBUtils.getDriver(str1));
      }
      else
      {
        FFSDebug.log("Failed to load driver. Neither database name nor database type is provided.", 0);
        throw new FFSException("ERROR: Failed to load driver. Neither database name nor database type is provided.");
      }
    }
    try
    {
      try
      {
        Class.forName(paramFFSDBProperties._connInfo._dbConnInfo._driver);
      }
      catch (Exception localException1)
      {
        if (((localException1 instanceof InstantiationException)) || ((localException1 instanceof ClassNotFoundException)))
        {
          if ((str1.equalsIgnoreCase("ASE")) && (paramFFSDBProperties._connInfo._dbConnInfo._driver.equalsIgnoreCase("com.sybase.jdbc3.jdbc.SybDriver")))
          {
            paramFFSDBProperties._connInfo._dbConnInfo.setDriver("com.sybase.jdbc2.jdbc.SybDriver");
            try
            {
              Class.forName(paramFFSDBProperties._connInfo._dbConnInfo._driver);
            }
            catch (Exception localException3)
            {
              if (((localException3 instanceof InstantiationException)) || ((localException3 instanceof ClassNotFoundException)))
              {
                paramFFSDBProperties._connInfo._dbConnInfo.setDriver("com.sybase.jdbc.SybDriver");
                Class.forName(paramFFSDBProperties._connInfo._dbConnInfo._driver);
              }
              else
              {
                throw localException3;
              }
            }
          }
        }
        else {
          throw localException1;
        }
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      FFSDebug.log("Failed to load: ", paramFFSDBProperties._connInfo._dbConnInfo._driver, 0);
      throw new FFSException("Failed to load: " + paramFFSDBProperties._connInfo._dbConnInfo._driver + FFSConst.LINE_SEPARATOR + "Error: " + localClassNotFoundException.getMessage());
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      FFSDebug.log("Failed to load: ", paramFFSDBProperties._connInfo._dbConnInfo._driver, 0);
      throw new FFSException("Failed to load: " + paramFFSDBProperties._connInfo._dbConnInfo._driver + FFSConst.LINE_SEPARATOR + "Error: " + localException2.getMessage());
    }
    String str2 = a(paramFFSDBProperties);
    try
    {
      if (!jdMethod_int(paramFFSDBProperties._connInfo._dbConnInfo._databaseName)) {
        paramFFSDBProperties._connProps.setProperty("servicename", paramFFSDBProperties._connInfo._dbConnInfo._databaseName);
      }
      if (paramFFSDBProperties._pureProps.getProperty("user") == null)
      {
        paramFFSDBProperties._pureProps.setProperty("user", paramFFSDBProperties._connInfo._dbConnInfo._user);
        paramFFSDBProperties._pureProps.setProperty("password", paramFFSDBProperties._connInfo._dbConnInfo._password);
      }
      paramFFSDBProperties._pureProps.setProperty("servicename", paramFFSDBProperties._connInfo._dbConnInfo._databaseName);
      if ((str1.equalsIgnoreCase("ORACLE:oci8")) && ((paramFFSDBProperties._connInfo._dbConnInfo._host.equals("")) || (paramFFSDBProperties._connInfo._dbConnInfo._host == null) || (paramFFSDBProperties._connInfo._dbConnInfo._port < 0))) {
        localConnection = DriverManager.getConnection(str2, paramFFSDBProperties._connInfo._dbConnInfo._user, paramFFSDBProperties._connInfo._dbConnInfo._password);
      } else {
        localConnection = DriverManager.getConnection(str2, paramFFSDBProperties._pureProps);
      }
    }
    catch (SQLException localSQLException)
    {
      FFSDebug.log("SQLException: ", localSQLException.getMessage(), "\n", localSQLException.toString(), 0);
      localSQLException.printStackTrace();
      throw new FFSException(localSQLException.getMessage());
    }
    if (localConnection == null)
    {
      String str3 = "Failed to get connection form " + str2;
      FFSDebug.log(str3, 0);
      throw new FFSException(str3);
    }
    return new FFSConnection(localConnection, paramFFSDBProperties);
  }
  
  private static String a(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    if ((paramFFSDBProperties == null) || (paramFFSDBProperties._connInfo._dbConnInfo._driver == null) || (paramFFSDBProperties._connInfo._dbConnInfo._dbType == null))
    {
      String str = "Invalid property file: ";
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    paramFFSDBProperties._connInfo._dbConnInfo._driver = paramFFSDBProperties._connInfo._dbConnInfo._driver.trim();
    paramFFSDBProperties._connInfo._dbConnInfo._dbType = paramFFSDBProperties._connInfo._dbConnInfo._dbType.trim();
    if ((paramFFSDBProperties._connInfo._dbConnInfo._url != null) && (paramFFSDBProperties._connInfo._dbConnInfo._url.trim().length() > 0))
    {
      FFSDebug.log("Support HA: " + paramFFSDBProperties._connInfo._dbConnInfo._haFlag, 6);
      if (paramFFSDBProperties._connInfo._dbConnInfo._haFlag)
      {
        FFSDebug.log("databaseUrl for HA: " + paramFFSDBProperties._connInfo._dbConnInfo._url.trim(), 6);
        paramFFSDBProperties._pureProps.setProperty("java.naming.factory.initial", paramFFSDBProperties._connInfo._dbConnInfo._ldapCtxFactory.trim());
        paramFFSDBProperties._pureProps.setProperty("java.naming.provider.url", paramFFSDBProperties._connInfo._dbConnInfo._ldapServer.trim());
        paramFFSDBProperties._pureProps.setProperty("REQUEST_HA_SESSION", "true");
      }
      return paramFFSDBProperties._connInfo._dbConnInfo._url.trim();
    }
    return FFSDBUtils.getDbUrl(paramFFSDBProperties);
  }
  
  static boolean jdMethod_int(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.DriverConnection
 * JD-Core Version:    0.7.0.1
 */