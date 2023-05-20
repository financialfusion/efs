package com.ffusion.dataconsolidator.adapter;

import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

class DCExtendABeanXML
{
  private static final String a = "INSERT INTO DC_ExtendABeanXML( DCExtendABeanID, XMLSegmentNumber, XMLSegment ) VALUES( ?, ?, ?)";
  private static final String jdField_do = "DELETE FROM DC_ExtendABeanXML WHERE DCExtendABeanID=?";
  private static final String jdField_if = "SELECT XMLSegment FROM DC_ExtendABeanXML WHERE DCExtendABeanID=? ORDER BY XMLSegmentNumber ASC";
  
  protected static long addExtendABeanXML(Connection paramConnection, String paramString, HashMap paramHashMap)
    throws DCException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return 0L;
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      long l1 = DCRecordCounter.getNextIndex(paramConnection, 3, 0, "DCExtendABeanIndex", paramHashMap);
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_ExtendABeanXML( DCExtendABeanID, XMLSegmentNumber, XMLSegment ) VALUES( ?, ?, ?)");
      int j = 1;
      String str = null;
      int k = 1;
      while (k != 0)
      {
        if (paramString.length() > 2000)
        {
          str = paramString.substring(0, 2000);
          paramString = paramString.substring(2000, paramString.length());
        }
        else
        {
          str = paramString;
          k = 0;
        }
        localPreparedStatement.setLong(1, l1);
        localPreparedStatement.setInt(2, j);
        localPreparedStatement.setString(3, str);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_ExtendABeanXML( DCExtendABeanID, XMLSegmentNumber, XMLSegment ) VALUES( ?, ?, ?)");
        j++;
      }
      long l2 = l1;
      return l2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add ExtendABeanXML", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 307;
      throw new DCException(i, "Failed to add ExtendABean XML.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void deleteExtendABeanXML(Connection paramConnection, long paramLong)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "DELETE FROM DC_ExtendABeanXML WHERE DCExtendABeanID=?");
      localPreparedStatement.setLong(1, paramLong);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM DC_ExtendABeanXML WHERE DCExtendABeanID=?");
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to delete ExtendABeanXML", localSQLException);
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 308;
      throw new DCException(i, "Failed to delete ExtendABean XML.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static String getExtendABeanXML(long paramLong)
    throws DCException
  {
    if (paramLong == 0L) {
      return null;
    }
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      String str = getExtendABeanXML(localConnection, paramLong);
      return str;
    }
    finally
    {
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  protected static String getExtendABeanXML(Connection paramConnection, long paramLong)
    throws DCException
  {
    if (paramLong == 0L) {
      return null;
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT XMLSegment FROM DC_ExtendABeanXML WHERE DCExtendABeanID=? ORDER BY XMLSegmentNumber ASC");
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT XMLSegment FROM DC_ExtendABeanXML WHERE DCExtendABeanID=? ORDER BY XMLSegmentNumber ASC");
      StringBuffer localStringBuffer = new StringBuffer();
      while (localResultSet.next()) {
        localStringBuffer.append(localResultSet.getString(1));
      }
      String str = localStringBuffer.toString();
      return str;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get ExtendABeanXML", localSQLException);
    }
    catch (Exception localException1)
    {
      int i = (localException1 instanceof SQLException) ? 302 : 309;
      throw new DCException(i, "Failed to get ExtendABean XML.", localException1);
    }
    finally
    {
      localPreparedStatement = null;
      if (localResultSet != null) {
        try
        {
          DBUtil.closeResultSet(localResultSet);
        }
        catch (Exception localException2) {}
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCExtendABeanXML
 * JD-Core Version:    0.7.0.1
 */