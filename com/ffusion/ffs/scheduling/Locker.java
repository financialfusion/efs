package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.db.ConnectionPool;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Locker
  implements FFSConst
{
  public static boolean releaseSchedulerLock(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Locker.releaseSchedulerLock begin", 6);
    boolean bool = false;
    try
    {
      paramFFSConnectionHolder.conn.rollback();
      bool = true;
    }
    catch (Throwable localThrowable)
    {
      String str = "Locker.releaseSchedulerLock:Failed to releaselock for scheduler. Error= " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
    }
    return bool;
  }
  
  public static boolean checkSchedulerLock(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("Locker.checkSchedulerLock begins", 6);
    Statement localStatement = null;
    ResultSet localResultSet = null;
    boolean bool = false;
    String str1 = paramFFSConnectionHolder.conn.getDatabaseType();
    String str2 = null;
    if ((str1.startsWith("DB2")) || (str1.indexOf("ASE") != -1)) {
      str2 = "SELECT Value FROM SCH_Locker WHERE IndexName='EngineLock_1'";
    } else {
      str2 = "SELECT Value FROM SCH_Locker WHERE IndexName='EngineLock'";
    }
    try
    {
      localStatement = paramFFSConnectionHolder.conn.getConnection().createStatement();
      localResultSet = localStatement.executeQuery(str2);
      localResultSet.next();
      if (localResultSet.getInt(1) == Integer.parseInt(paramString)) {
        bool = true;
      } else {
        bool = false;
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = FFSDebug.stackTrace(localThrowable);
      String str4 = null;
      if (str3.indexOf("ORA-00054") > -1)
      {
        str4 = "Locker.checkSchedulerLock: the scheduler lock is currently not available.";
        FFSDebug.log(str4, 3);
      }
      else
      {
        str4 = "Locker.checkSchedulerLock:Failed to check lock for scheduler. Error: " + str3;
        FFSDebug.log(str4, 0);
      }
      bool = false;
    }
    finally
    {
      a(localStatement);
      a(localResultSet);
    }
    FFSDebug.log("Locker.checkSchedulerLock ends: holdingSchLock = " + bool, 6);
    return bool;
  }
  
  public static boolean getSchedulerLock(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("Locker.getSchedulerLock begins", 6);
    Statement localStatement1 = null;
    PreparedStatement localPreparedStatement = null;
    Statement localStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    boolean bool = false;
    String str1 = paramFFSConnectionHolder.conn.getDatabaseType();
    Object localObject1;
    Object localObject2;
    String str4;
    String str5;
    if ((str1.startsWith("ORA")) || (str1.startsWith("Ora")) || (str1.startsWith("ora")))
    {
      localObject1 = "SELECT Value FROM SCH_Locker WHERE  IndexName='EngineLock' FOR UPDATE NOWAIT";
      localObject2 = "UPDATE SCH_Locker SET Value= ?  WHERE IndexName='EngineLock'";
      try
      {
        localStatement1 = paramFFSConnectionHolder.conn.getConnection().createStatement();
        localResultSet1 = localStatement1.executeQuery((String)localObject1);
        bool = localResultSet1.next();
        localPreparedStatement = paramFFSConnectionHolder.conn.getConnection().prepareStatement((String)localObject2);
        localPreparedStatement.setInt(1, Integer.parseInt(paramString));
        int k = localPreparedStatement.executeUpdate();
        if (k == 1) {
          bool = true;
        } else {
          bool = false;
        }
      }
      catch (Throwable localThrowable3)
      {
        str4 = FFSDebug.stackTrace(localThrowable3);
        str5 = null;
        if (str4.indexOf("ORA-00054") > -1)
        {
          str5 = "Locker.getSchedulerLock: the scheduler lock is currently not available.  The scheduler will not start on this server.";
          FFSDebug.log(str5, 3);
        }
        else
        {
          str5 = "Locker.getSchedulerLock:Failed to get lock for scheduler. Error: " + str4;
          FFSDebug.log(str5, 0);
        }
        bool = false;
      }
      finally
      {
        a(localStatement1);
        a(localResultSet1);
        a(localPreparedStatement);
      }
    }
    else
    {
      String str3;
      if ((str1.startsWith("DB2")) || (str1.indexOf("ASE") != -1))
      {
        localObject1 = new FFSConnectionHolder();
        localObject2 = new FFSConnectionHolder();
        str3 = "SELECT SCH_Locker.Value, SCH_Locker2.Value FROM SCH_Locker, SCH_Locker2 WHERE SCH_Locker.IndexName='EngineLock_1' AND SCH_Locker.Value=0 AND SCH_Locker2.IndexName='EngineLock_2' AND SCH_Locker2.Value=0";
        str4 = "UPDATE SCH_Locker set Value = ?  WHERE Value = 0 AND IndexName='EngineLock_1'";
        str5 = "SELECT SCH_Locker.Value FROM SCH_Locker WHERE SCH_Locker.IndexName='EngineLock_1' AND SCH_Locker.Value=0";
        try
        {
          ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
          localStatement2 = ((FFSConnectionHolder)localObject1).conn.getConnection().createStatement();
          localResultSet2 = localStatement2.executeQuery(str3);
          if (!localResultSet2.next())
          {
            bool = false;
            ((FFSConnectionHolder)localObject1).conn.rollback();
          }
          else
          {
            ((FFSConnectionHolder)localObject1).conn.rollback();
            ((FFSConnectionHolder)localObject2).conn = DBUtil.getConnection();
            if (a((FFSConnectionHolder)localObject2, paramString))
            {
              localStatement1 = ((FFSConnectionHolder)localObject1).conn.getConnection().createStatement();
              localResultSet1 = localStatement1.executeQuery(str5);
              if (localResultSet1.next())
              {
                Integer[] arrayOfInteger = { new Integer(paramString) };
                DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfInteger);
                bool = true;
              }
            }
          }
        }
        catch (Throwable localThrowable4)
        {
          ((FFSConnectionHolder)localObject1).conn.rollback();
          ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
          ((FFSConnectionHolder)localObject1).conn = localConnectionPool.renewConnection(((FFSConnectionHolder)localObject1).conn);
          String str6 = "Locker.getSchedulerLock:Failed to get lock for scheduler Error: " + FFSDebug.stackTrace(localThrowable4);
          FFSDebug.log(str6, 0);
          bool = false;
        }
        finally
        {
          a(localStatement1);
          a(localResultSet1);
          a(localResultSet2);
          a(localStatement2);
          ((FFSConnectionHolder)localObject1).conn.rollback();
          a((FFSConnectionHolder)localObject2);
          if (((FFSConnectionHolder)localObject1).conn != null) {
            DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
          }
          if (((FFSConnectionHolder)localObject2).conn != null) {
            DBUtil.freeConnection(((FFSConnectionHolder)localObject2).conn);
          }
        }
      }
      else if (str1.startsWith("MSSQL"))
      {
        localObject1 = "SELECT Value FROM SCH_Locker WHERE IndexName='EngineLock'";
        try
        {
          localStatement1 = paramFFSConnectionHolder.conn.getConnection().createStatement();
          localResultSet1 = localStatement1.executeQuery((String)localObject1);
          localResultSet1.next();
          int i = localResultSet1.getInt(1);
          localStatement1.close();
          localResultSet1.close();
          if (i != 0)
          {
            bool = false;
          }
          else
          {
            str3 = "begin tran";
            localStatement1 = paramFFSConnectionHolder.conn.getConnection().createStatement();
            localStatement1.execute(str3);
            localStatement1.close();
            localObject1 = "SELECT Value FROM SCH_Locker WITH (XLOCK) WHERE IndexName='EngineLock'";
            localStatement1 = paramFFSConnectionHolder.conn.getConnection().createStatement();
            localResultSet1 = localStatement1.executeQuery((String)localObject1);
            localResultSet1.next();
            i = localResultSet1.getInt(1);
            localStatement1.close();
            localResultSet1.close();
            str4 = "commit";
            if (i != 0)
            {
              bool = false;
            }
            else
            {
              str5 = "UPDATE SCH_Locker SET Value= ?  WHERE IndexName= 'EngineLock'";
              localPreparedStatement = paramFFSConnectionHolder.conn.getConnection().prepareStatement(str5);
              localPreparedStatement.setInt(1, Integer.parseInt(paramString));
              int m = localPreparedStatement.executeUpdate();
              if (m == 1)
              {
                bool = true;
                str4 = "commit";
              }
              else
              {
                bool = false;
                str4 = "rollback";
              }
            }
            localStatement1 = paramFFSConnectionHolder.conn.getConnection().createStatement();
            localStatement1.execute(str4);
          }
        }
        catch (Throwable localThrowable2)
        {
          localStatement1.execute("rollback");
          str3 = "Locker.getSchedulerLock:Failed to get lock for scheduler=";
          FFSDebug.log(str3, 0);
          bool = false;
        }
        finally
        {
          a(localPreparedStatement);
          a(localStatement1);
          a(localResultSet1);
        }
      }
      else
      {
        try
        {
          localObject1 = "UPDATE SCH_Locker SET Value= ?  WHERE IndexName= 'EngineLock'";
          localPreparedStatement = paramFFSConnectionHolder.conn.getConnection().prepareStatement((String)localObject1);
          localPreparedStatement.setInt(1, Integer.parseInt(paramString));
          int j = localPreparedStatement.executeUpdate();
          if (j == 1) {
            bool = true;
          } else {
            bool = false;
          }
        }
        catch (Throwable localThrowable1)
        {
          String str2 = "Locker.getSchedulerLock:Failed to get lock for scheduler=";
          FFSDebug.log(str2, 0);
          bool = false;
        }
        finally
        {
          a(localStatement1);
          a(localResultSet1);
        }
      }
    }
    FFSDebug.log("Locker.getSchedulerLock: holdingSchLock = " + bool, 6);
    return bool;
  }
  
  public static void flipLockTrans(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("Locker.flipLockTrans begin.....", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str1 = "UPDATE SCH_Locker set  Value= ?  WHERE IndexName='EngineLock_1'";
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (a(localFFSConnectionHolder, paramString))
      {
        paramFFSConnectionHolder.conn.rollback();
        Integer[] arrayOfInteger = { new Integer(paramString) };
        DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfInteger);
      }
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup("BPWDBPOOL");
      localFFSConnectionHolder.conn = localConnectionPool.renewConnection(localFFSConnectionHolder.conn);
      String str2 = "Locker:flipLockTrans: Failed to flip locks for scheduler= Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
    }
    finally
    {
      a(localFFSConnectionHolder);
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
  {
    boolean bool = false;
    if ((paramFFSConnectionHolder == null) || (paramFFSConnectionHolder.conn == null)) {
      return bool;
    }
    String str = "UPDATE SCH_Locker2 set Value= ?  WHERE IndexName='EngineLock_2'";
    try
    {
      Integer[] arrayOfInteger = { new Integer(paramString) };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfInteger);
      if (i > 0) {
        bool = true;
      }
    }
    catch (Throwable localThrowable)
    {
      bool = false;
    }
    return bool;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder)
  {
    boolean bool = false;
    if ((paramFFSConnectionHolder == null) || (paramFFSConnectionHolder.conn == null)) {
      return bool;
    }
    try
    {
      paramFFSConnectionHolder.conn.rollback();
      bool = true;
    }
    catch (Throwable localThrowable)
    {
      String str = "Locker:endSync: Failed to release sync token Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      bool = false;
    }
    return bool;
  }
  
  private static void a(ResultSet paramResultSet)
  {
    if (paramResultSet != null) {
      try
      {
        paramResultSet.close();
      }
      catch (Throwable localThrowable)
      {
        String str = "Locker:closeRset: Failed to close ResultSet: " + FFSDebug.stackTrace(localThrowable);
        FFSDebug.log(str, 0);
      }
    }
  }
  
  private static void a(Statement paramStatement)
  {
    if (paramStatement != null) {
      try
      {
        paramStatement.close();
      }
      catch (Throwable localThrowable)
      {
        String str = "Locker:closeStmt: Failed to close Statement: " + FFSDebug.stackTrace(localThrowable);
        FFSDebug.log(str, 0);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.Locker
 * JD-Core Version:    0.7.0.1
 */