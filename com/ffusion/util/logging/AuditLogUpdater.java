package com.ffusion.util.logging;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class AuditLogUpdater
{
  private static final String a = "select distinct TRAN_TYPE from AUDIT_LOG";
  private static final String jdField_new = "update AUDIT_LOG set MODULE = ? where TRAN_TYPE = ?";
  private static final String jdField_int = "SELECT DISTINCT USER_ID FROM AUDIT_LOG";
  private static final String jdField_do = "UPDATE AUDIT_LOG set USER_ID_INT=? WHERE USER_ID=?";
  private static final String jdField_for = "SELECT AGENT_ID FROM AUDIT_LOG";
  private static final String jdField_if = "UPDATE AUDIT_LOG set AGENT_ID_INT=? WHERE AGENT_ID=?";
  
  public static void main(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length != 1))
    {
      System.out.println("AuditLogUpdater usage: AuditLogUpdater dbPropsFile");
      return;
    }
    Properties localProperties = null;
    Object localObject2;
    Object localObject4;
    try
    {
      InputStream localInputStream = null;
      localInputStream = ResourceUtil.getResourceAsStream(null, paramArrayOfString[0]);
      int i = 0;
      int j = 0;
      localObject1 = new ArrayList(3);
      while (j != -1)
      {
        localObject2 = new byte[1024];
        j = localInputStream.read((byte[])localObject2, 0, 1024);
        if (j == -1) {
          break;
        }
        ((ArrayList)localObject1).add(localObject2);
        i += j;
      }
      localInputStream.close();
      localObject2 = new byte[i];
      int k = 0;
      localObject4 = null;
      localObject4 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (byte[])((Iterator)localObject4).next();
        if (i - k > 1024) {
          j = 1024;
        } else {
          j = i - k;
        }
        System.arraycopy(localObject5, 0, localObject2, k, j);
        k += j;
      }
      Object localObject5 = new XMLTag(true);
      ((XMLTag)localObject5).build(new String((byte[])localObject2, 0, i));
      localProperties = XMLUtil.tagToProperties((XMLTag)localObject5);
    }
    catch (Exception localException1)
    {
      System.out.println("Error reading db properties: ");
      localException1.printStackTrace();
    }
    String str = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    try
    {
      str = ConnectionPool.init(localProperties);
      localConnection = DBUtil.getConnection(str, false, 2);
      localObject2 = new ArrayList();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct TRAN_TYPE from AUDIT_LOG");
      localObject1 = localPreparedStatement.executeQuery();
      while (((ResultSet)localObject1).next())
      {
        localObject3 = new Integer(((ResultSet)localObject1).getInt(1));
        ((ArrayList)localObject2).add(localObject3);
      }
      DBUtil.closeAll(localPreparedStatement, (ResultSet)localObject1);
      localObject1 = null;
      localPreparedStatement = null;
      Object localObject3 = ((ArrayList)localObject2).iterator();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update AUDIT_LOG set MODULE = ? where TRAN_TYPE = ?");
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (Integer)((Iterator)localObject3).next();
        localPreparedStatement.setInt(1, AuditLogUtil.getModuleFromTranType(((Integer)localObject4).intValue()));
        localPreparedStatement.setInt(2, ((Integer)localObject4).intValue());
        localPreparedStatement.executeUpdate();
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      populateAgentIDInt(localConnection);
      populateUserIDInt(localConnection);
      localConnection.commit();
    }
    catch (Exception localException2)
    {
      System.out.println("Unable to update audit log table: ");
      localException2.printStackTrace();
      try
      {
        localConnection.rollback();
      }
      catch (Exception localException3)
      {
        jsr 14;
      }
    }
    finally
    {
      DBUtil.closeAll(str, localConnection, localPreparedStatement, (ResultSet)localObject1);
      try
      {
        ConnectionPool.releasePool(str);
      }
      catch (Exception localException4) {}
    }
  }
  
  public static void populateAgentIDInt(Connection paramConnection)
  {
    ArrayList localArrayList = new ArrayList();
    Statement localStatement = null;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      int i = 0;
      localStatement = DBUtil.createStatement(paramConnection);
      localResultSet = DBUtil.executeQuery(localStatement, "SELECT AGENT_ID FROM AUDIT_LOG");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE AUDIT_LOG set AGENT_ID_INT=? WHERE AGENT_ID=?");
      while (localResultSet.next())
      {
        Integer localInteger = null;
        String str = localResultSet.getString(1);
        if (str != null)
        {
          try
          {
            localInteger = new Integer(str);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            i = 1;
          }
          continue;
          localPreparedStatement.setInt(1, localInteger.intValue());
          localPreparedStatement.setString(2, localInteger.toString());
          DBUtil.executeUpdate(localPreparedStatement, "UPDATE AUDIT_LOG set AGENT_ID_INT=? WHERE AGENT_ID=?");
        }
      }
      if (i != 0) {
        System.err.println("The conversion of the audit log tables is incomplete, due to the fact that some of the agent IDs in the audit log table could not be parsed as integers (the do not contain purely numeric data).");
      }
    }
    catch (Exception localException)
    {
      System.err.println("Unable to update the agent id columns in the audit log: ");
      localException.printStackTrace();
    }
    finally
    {
      DBUtil.closeAll(localStatement, localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static void populateUserIDInt(Connection paramConnection)
  {
    ArrayList localArrayList = new ArrayList();
    Statement localStatement = null;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      int i = 0;
      localStatement = DBUtil.createStatement(paramConnection);
      localResultSet = DBUtil.executeQuery(localStatement, "SELECT DISTINCT USER_ID FROM AUDIT_LOG");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE AUDIT_LOG set USER_ID_INT=? WHERE USER_ID=?");
      while (localResultSet.next())
      {
        Integer localInteger = null;
        String str = localResultSet.getString(1);
        if (str != null)
        {
          try
          {
            localInteger = new Integer(str);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            i = 1;
          }
          continue;
          localPreparedStatement.setInt(1, localInteger.intValue());
          localPreparedStatement.setString(2, localInteger.toString());
          DBUtil.executeUpdate(localPreparedStatement, "UPDATE AUDIT_LOG set USER_ID_INT=? WHERE USER_ID=?");
        }
      }
      if (i != 0) {
        System.err.println("The conversion of the audit log tables is incomplete, due to the fact that some of the user IDs in the audit log table could not be parsed as integers (the do not contain purely numeric data).");
      }
    }
    catch (Exception localException)
    {
      System.err.println("Unable to update the agent id columns in the audit log: ");
      localException.printStackTrace();
    }
    finally
    {
      DBUtil.closeAll(localStatement, localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditLogUpdater
 * JD-Core Version:    0.7.0.1
 */