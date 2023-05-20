package com.ffusion.util.settings;

import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SystemSettings
{
  public static final int DUP_ACCOUNTNUMS_OFF = 1;
  public static final int DUP_ACCOUNTNUMS_ON = 2;
  private static final String a = "SELECT DUP_ACCOUNT_NUMS FROM SYSTEM_SETTINGS";
  private static int jdField_if = -1;
  
  public static void initialize(Connection paramConnection)
    throws SystemException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT DUP_ACCOUNT_NUMS FROM SYSTEM_SETTINGS");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        jdField_if = localResultSet.getInt("DUP_ACCOUNT_NUMS");
      }
    }
    catch (Exception localException)
    {
      throw new SystemException("Error initializing SystemSettings");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static int getDuplicateAccountNumbers()
    throws SystemException
  {
    if (jdField_if != -1) {
      return jdField_if;
    }
    throw new SystemException("SystemSettings not initialized");
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.settings.SystemSettings
 * JD-Core Version:    0.7.0.1
 */