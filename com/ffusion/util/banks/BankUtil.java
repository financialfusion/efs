package com.ffusion.util.banks;

import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

public class BankUtil
{
  private static final String a = "SELECT bank_id from affiliate_bank  WHERE bpw_fi_id=?";
  
  public static String getServiceBureauID(Connection paramConnection, String paramString)
  {
    String str1 = "BankUtil.getServiceBureauID";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "SELECT bank_id from affiliate_bank  WHERE bpw_fi_id=?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT bank_id from affiliate_bank  WHERE bpw_fi_id=?");
      if (localResultSet.next()) {
        str2 = localResultSet.getString(1);
      }
    }
    catch (Exception localException)
    {
      str2 = null;
      DebugLog.log(Level.FINE, str1 + localException.getMessage());
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return str2;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.banks.BankUtil
 * JD-Core Version:    0.7.0.1
 */