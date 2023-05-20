package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSDebug;

public class NamePlace
{
  public static String getValue(String paramString)
    throws Exception
  {
    String str1 = null;
    String str2 = "SELECT Value from SCH_NamePlace WHERE Name = ?";
    Object[] arrayOfObject = { paramString };
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str1 = localFFSResultSet.getColumnString(1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** NamePlace.getValue failed" + localException1.toString());
      throw new Exception(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2) {}
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return str1;
  }
  
  public static void setValue(String paramString1, String paramString2)
    throws Exception
  {
    String str = "UPDATE SCH_NamePlace SET Value = ? WHERE Name = ?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      int i = DBUtil.executeStatement(localFFSConnectionHolder, str, arrayOfObject);
      if (i == 0)
      {
        str = "INSERT INTO SCH_NamePlace( Name, Value ) VALUES(?, ?)";
        arrayOfObject[0] = paramString1;
        arrayOfObject[1] = paramString2;
        i = DBUtil.executeStatement(localFFSConnectionHolder, str, arrayOfObject);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** NamePlace.setValue failed: " + localException.toString());
      throw new Exception(localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.NamePlace
 * JD-Core Version:    0.7.0.1
 */