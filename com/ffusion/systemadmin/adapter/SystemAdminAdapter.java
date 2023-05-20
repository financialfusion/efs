package com.ffusion.systemadmin.adapter;

import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSetting;
import com.ffusion.systemadmin.SAConstants;
import com.ffusion.systemadmin.SAException;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class SystemAdminAdapter
  implements SAConstants, SAAdapterConstants
{
  private static final String a7B = "DB_PROPERTIES";
  private static String a7E = null;
  private static final String a7D = "select d.data_type, d.data_class, d.num_days from sa_dataretention d where d.data_ret_type = ? and d.data_ret_id = ?";
  private static final String a7z = "insert into sa_dataretention (data_ret_type, data_ret_id, data_type, data_class, num_days) values (?, ?, ?, ?, ?)";
  private static final String a7A = "update sa_dataretention set num_days = ? where data_ret_type = ? and data_ret_id = ? and data_type = ? and data_class = ?";
  private static final String a7C = "delete from sa_dataretention where data_ret_type = ? and data_ret_id = ? and data_type = ? and data_class = ?";
  
  public static void initialize(HashMap paramHashMap)
    throws SAException
  {
    String str = "SystemAdminAdapter.initialize";
    if ((a7E == null) || (a7E.length() == 0))
    {
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      if (localProperties == null)
      {
        DebugLog.log(Level.SEVERE, str + ": Missing database connection pool configuration.");
        throw new SAException(38202, "Missing database connection pool configuration.");
      }
      try
      {
        a7E = ConnectionPool.init(localProperties);
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, str + ": " + localException.getMessage());
        throw new SAException(38203, localException);
      }
    }
  }
  
  public static HashMap getDataRetentionSettings(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws SAException
  {
    String str1 = "SystemAdminAdapter.getDataRetentionSettings";
    HashMap localHashMap = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 1;
    String str2 = "select d.data_type, d.data_class, d.num_days from sa_dataretention d where d.data_ret_type = ? and d.data_ret_id = ?";
    try
    {
      localConnection = DBUtil.getConnection(a7E, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      i = 1;
      localPreparedStatement.setInt(i++, paramInt1);
      localPreparedStatement.setInt(i++, paramInt2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localHashMap = jdMethod_try(localResultSet);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
      throw new SAException(38201, localException);
    }
    finally
    {
      DBUtil.closeAll(a7E, localConnection, localPreparedStatement, localResultSet);
    }
    return localHashMap;
  }
  
  public static void setDataRetentionSettings(int paramInt1, int paramInt2, HashMap paramHashMap1, HashMap paramHashMap2)
    throws SAException
  {
    String str1 = "SystemAdminAdapter.setDataRetentionSettings";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str2 = null;
      int i = 1;
      HashMap localHashMap = getDataRetentionSettings(paramInt1, paramInt2, paramHashMap2);
      localConnection = DBUtil.getConnection(a7E, false, 2);
      Iterator localIterator;
      DRKey localDRKey;
      if (paramHashMap1.size() > 0)
      {
        localIterator = paramHashMap1.keySet().iterator();
        while (localIterator.hasNext())
        {
          localDRKey = (DRKey)localIterator.next();
          DRSetting localDRSetting = (DRSetting)paramHashMap1.get(localDRKey);
          if (localDRSetting.getRetentionDays() >= 0)
          {
            if (localHashMap.get(localDRKey) == null)
            {
              str2 = "insert into sa_dataretention (data_ret_type, data_ret_id, data_type, data_class, num_days) values (?, ?, ?, ?, ?)";
              localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
              i = 1;
              localPreparedStatement.setInt(i++, paramInt1);
              localPreparedStatement.setInt(i++, paramInt2);
              localPreparedStatement.setString(i++, localDRKey.getDataType());
              localPreparedStatement.setString(i++, localDRKey.getDataClass());
              localPreparedStatement.setInt(i++, localDRSetting.getRetentionDays());
              DBUtil.execute(localPreparedStatement, str2);
            }
            else
            {
              str2 = "update sa_dataretention set num_days = ? where data_ret_type = ? and data_ret_id = ? and data_type = ? and data_class = ?";
              localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
              i = 1;
              localPreparedStatement.setInt(i++, localDRSetting.getRetentionDays());
              localPreparedStatement.setInt(i++, paramInt1);
              localPreparedStatement.setInt(i++, paramInt2);
              localPreparedStatement.setString(i++, localDRKey.getDataType());
              localPreparedStatement.setString(i++, localDRKey.getDataClass());
              DBUtil.executeUpdate(localPreparedStatement, str2);
              localHashMap.remove(localDRKey);
            }
          }
          else {
            throw new SAException(38207, "A negative number of days was specified.");
          }
          DBUtil.closeStatement(localPreparedStatement);
          localPreparedStatement = null;
        }
      }
      if (localHashMap.size() > 0)
      {
        str2 = "delete from sa_dataretention where data_ret_type = ? and data_ret_id = ? and data_type = ? and data_class = ?";
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
        localIterator = localHashMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          localDRKey = (DRKey)localIterator.next();
          i = 1;
          localPreparedStatement.setInt(i++, paramInt1);
          localPreparedStatement.setInt(i++, paramInt2);
          localPreparedStatement.setString(i++, localDRKey.getDataType());
          localPreparedStatement.setString(i++, localDRKey.getDataClass());
          DBUtil.execute(localPreparedStatement, str2);
        }
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      DebugLog.log(Level.FINE, str1 + ": " + localException.getMessage());
      throw new SAException(38201, localException);
    }
    finally
    {
      DBUtil.closeAll(a7E, localConnection, localPreparedStatement);
    }
  }
  
  private static HashMap jdMethod_try(ResultSet paramResultSet)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    DRKey localDRKey = null;
    DRSetting localDRSetting = null;
    while (paramResultSet.next())
    {
      localDRKey = new DRKey();
      localDRKey.setDataType(paramResultSet.getString("data_type"));
      localDRKey.setDataClass(paramResultSet.getString("data_class"));
      localDRSetting = new DRSetting();
      localDRSetting.setDataKey(localDRKey);
      localDRSetting.setRetentionDays(paramResultSet.getInt("num_days"));
      localHashMap.put(localDRKey, localDRSetting);
    }
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.systemadmin.adapter.SystemAdminAdapter
 * JD-Core Version:    0.7.0.1
 */