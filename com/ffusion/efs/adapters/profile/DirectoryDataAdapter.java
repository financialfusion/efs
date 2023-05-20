package com.ffusion.efs.adapters.profile;

import com.ffusion.util.Strings;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class DirectoryDataAdapter
  implements ProfileDefines
{
  static final int ahe = 255;
  public static final String NAME_SPACE = "name_space";
  public static final String DATA = "data";
  private static final String ahj = "delete from directory_data where directory_id=?";
  private static final String ahi = "insert into directory_data (directory_id,employee_id,name_space,seq,data) values(?,0,?,?,?)";
  private static final String ahh = "select name_space from directory_data where directory_id=? and seq=0";
  private static final String ahf = "select data from directory_data where directory_id=? and name_space=? order by seq";
  private static int ahg = 0;
  
  public static void addAdditionalData(int paramInt, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "DirectoryDataAdapter.addAdditionalData";
    Profile.isInitialized();
    Connection localConnection = null;
    if (!Profile.isValidId(paramInt)) {
      throw new ProfileException(str, 3505, "Directory Id required");
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_for(localConnection);
      jdMethod_for(localConnection, paramInt, paramString1, paramString2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static String getAdditionalData(int paramInt, String paramString)
    throws ProfileException
  {
    String str1 = "DirectoryDataAdapter.getAdditionalData";
    Profile.isInitialized();
    StringBuffer localStringBuffer = new StringBuffer();
    Connection localConnection = null;
    if (!Profile.isValidId(paramInt)) {
      throw new ProfileException(str1, 3505, "Directory Id required");
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_for(localConnection);
      Object localObject1;
      if ((paramString == null) || (paramString.length() == 0))
      {
        localObject1 = jdMethod_else(localConnection, paramInt);
        for (int i = 0; i < ((ArrayList)localObject1).size(); i++) {
          localStringBuffer.append(jdMethod_int(localConnection, paramInt, (String)((ArrayList)localObject1).get(i)));
        }
      }
      else
      {
        localObject1 = new StringTokenizer(paramString, ",");
        while (((StringTokenizer)localObject1).hasMoreTokens())
        {
          String str2 = ((StringTokenizer)localObject1).nextToken();
          if (str2 != null) {
            localStringBuffer.append(jdMethod_int(localConnection, paramInt, str2));
          }
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localStringBuffer.toString();
  }
  
  protected static boolean deleteAdditionalData(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer("delete from directory_data where directory_id=?");
    Profile.appendSQLSelectString(localStringBuffer, "name_space", paramString, true);
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      Profile.setStatementString(localPreparedStatement, 2, paramString, "name_space", true);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
    return true;
  }
  
  private static void jdMethod_for(Connection paramConnection, int paramInt, String paramString1, String paramString2)
    throws Exception
  {
    int i = 0;
    int j = 0;
    int k = 0;
    deleteAdditionalData(paramConnection, paramInt, paramString1);
    int m = paramString2.length();
    while ((k != m) && (paramString2 != null))
    {
      k += 255;
      if (k > m) {
        k = m;
      }
      k = Strings.getLastNonSpaceCharIndex(paramString2, j, k);
      jdMethod_for(paramConnection, paramInt, paramString1, paramString2.substring(j, k), i);
      i++;
      j = k;
    }
  }
  
  private static boolean jdMethod_for(Connection paramConnection, int paramInt1, String paramString1, String paramString2, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into directory_data (directory_id,employee_id,name_space,seq,data) values(?,0,?,?,?)");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setString(2, paramString1);
      localPreparedStatement.setInt(3, paramInt2);
      if (ahg != 12) {
        localPreparedStatement.setBytes(4, paramString2.getBytes());
      } else {
        localPreparedStatement.setString(4, paramString2);
      }
      DBUtil.executeUpdate(localPreparedStatement, "insert into directory_data (directory_id,employee_id,name_space,seq,data) values(?,0,?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
    return true;
  }
  
  private static String jdMethod_int(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select data from directory_data where directory_id=? and name_space=? order by seq");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select data from directory_data where directory_id=? and name_space=? order by seq");
      while (localResultSet.next()) {
        if (ahg != 12) {
          localStringBuffer.append(new String(localResultSet.getBytes(1)));
        } else {
          localStringBuffer.append(localResultSet.getString(1));
        }
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer.toString();
  }
  
  private static ArrayList jdMethod_else(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select name_space from directory_data where directory_id=? and seq=0");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select name_space from directory_data where directory_id=? and seq=0");
      while (localResultSet.next()) {
        localArrayList.add(localResultSet.getString(1));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  private static int jdMethod_for(Connection paramConnection)
  {
    if (ahg == 0)
    {
      ResultSet localResultSet = null;
      try
      {
        DatabaseMetaData localDatabaseMetaData = paramConnection.getMetaData();
        if ((Profile.getDBType().equalsIgnoreCase("ASE")) || (Profile.getDBType().equalsIgnoreCase("MSSQL:THIN"))) {
          localResultSet = localDatabaseMetaData.getColumns(Profile.dbCatalog, Profile.dbSchema, "directory_data", "%");
        } else {
          localResultSet = localDatabaseMetaData.getColumns(Profile.dbCatalog.toUpperCase(), Profile.dbSchema.toUpperCase(), "directory_data".toUpperCase(), "%");
        }
        while (localResultSet.next()) {
          if (localResultSet.getString("COLUMN_NAME").equalsIgnoreCase("data")) {
            ahg = localResultSet.getInt("DATA_TYPE");
          }
        }
        try
        {
          localResultSet.close();
        }
        catch (Exception localException1) {}
        if (ahg != 0) {
          break label182;
        }
      }
      catch (Exception localException2)
      {
        DebugLog.log(Level.WARNING, "Couldn't determine directory_data.data type");
      }
      finally
      {
        try
        {
          localResultSet.close();
        }
        catch (Exception localException4) {}
      }
    }
    ahg = 12;
    label182:
    return ahg;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.DirectoryDataAdapter
 * JD-Core Version:    0.7.0.1
 */