package com.ffusion.util.accountgroups;

import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

public class AccountGroupsUtil
{
  private static final String jdField_int = "SELECT distinct ag.ID from ACCOUNTGROUP ag, ACCOUNTGROUP_ACCT agt WHERE ag.ID = agt.ID AND ag.BUS_DIRECTORY_ID = ? AND (";
  private static final String jdField_for = "( agt.ACCOUNT_ID = ? AND agt.ROUTING_NUM = ? )";
  private static final String jdField_do = " OR ";
  private static final String jdField_if = " )";
  private static final String a = "ID";
  
  public static ArrayList getAccountGroupsIds(Connection paramConnection, int paramInt, ArrayList paramArrayList)
    throws BusinessAccountGroupException
  {
    String str1 = "AccountGroupsUtil.getAccountGroupsIds";
    ArrayList localArrayList = new ArrayList();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (paramArrayList.size() <= 0) {
      return localArrayList;
    }
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT distinct ag.ID from ACCOUNTGROUP ag, ACCOUNTGROUP_ACCT agt WHERE ag.ID = agt.ID AND ag.BUS_DIRECTORY_ID = ? AND (");
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        if (i > 0) {
          localStringBuffer.append(" OR ");
        }
        localStringBuffer.append("( agt.ACCOUNT_ID = ? AND agt.ROUTING_NUM = ? )");
      }
      localStringBuffer.append(" )");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      i = 1;
      localPreparedStatement.setInt(i, paramInt);
      i++;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        int j = str2.indexOf("-");
        String str3 = null;
        String str4 = null;
        if ((j > 0) && (j < str2.length() - 1))
        {
          str3 = str2.substring(0, j);
          str4 = str2.substring(j + 1);
        }
        else
        {
          throw new BusinessAccountGroupException(str1, 2, "Accounts must be specified of the form '<routing number>-<account id>'");
        }
        localPreparedStatement.setString(i, str4);
        i++;
        localPreparedStatement.setString(i, str3);
        i++;
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next()) {
        localArrayList.add(String.valueOf(localResultSet.getInt("ID")));
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  private static void a(Exception paramException, String paramString)
    throws BusinessAccountGroupException
  {
    if ((paramException instanceof BusinessAccountGroupException)) {
      throw ((BusinessAccountGroupException)paramException);
    }
    DebugLog.log(Level.FINE, "AccountGroupsUtil.handleError: " + paramException.getMessage());
    throw new BusinessAccountGroupException(paramException, paramString, 2, "");
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.accountgroups.AccountGroupsUtil
 * JD-Core Version:    0.7.0.1
 */