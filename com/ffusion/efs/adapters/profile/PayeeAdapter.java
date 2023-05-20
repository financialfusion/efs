package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class PayeeAdapter
  implements ProfileDefines
{
  private static final String agB = "select * from customer_payee where bank_id=? and directory_id=?";
  private static final String agD = "insert into customer_payee (directory_id,payee_id,employee_id,nick_name,bank_id";
  private static final String agA = "update customer_payee set nick_name=?";
  private static final String agC = " where directory_id=? and payee_id=?";
  private static final String agz = "delete from customer_payee where directory_id=?";
  
  public static void addCustomerPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "PayeeAdapter.addCustomerPayee";
    StringBuffer localStringBuffer = new StringBuffer("insert into customer_payee (directory_id,payee_id,employee_id,nick_name,bank_id");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str2 = paramPayee.getNickName();
    if ((str2 != null) && (str2.length() > 30)) {
      str2.substring(0, 30);
    }
    try
    {
      Profile.appendSQLInsertColumns(localStringBuffer, "customer_payee", true);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      if ((paramHashMap == null) || (paramSecureUser.getUserType() != 2))
      {
        localPreparedStatement.setInt(i++, paramSecureUser.getProfileID());
      }
      else
      {
        Integer localInteger = (Integer)paramHashMap.get("PROFILEID_KEY");
        localPreparedStatement.setInt(i++, localInteger.intValue());
      }
      i = Profile.setStatementString(localPreparedStatement, i, paramPayee.getHostID(), "payee_id", false);
      localPreparedStatement.setInt(i++, 0);
      i = Profile.setStatementString(localPreparedStatement, i, str2, "nick_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getBankID(), "bank_id", false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramPayee, "customer_payee", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void addCustomerPayee(SecureUser paramSecureUser, Payee paramPayee)
    throws ProfileException
  {
    addCustomerPayee(paramSecureUser, paramPayee, null);
  }
  
  public static Payees getCustomerPayees(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "PayeeAdapter.getCustomerPayees";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Payees localPayees = new Payees();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from customer_payee where bank_id=? and directory_id=?");
      int i = Profile.setStatementString(localPreparedStatement, 1, paramSecureUser.getBankID(), "bank_id", false);
      localPreparedStatement.setInt(i, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from customer_payee where bank_id=? and directory_id=?");
      while (localResultSet.next())
      {
        Payee localPayee = new Payee();
        localPayee.setHostID(String.valueOf(localResultSet.getInt("payee_id")));
        localPayee.setNickName(Profile.getRSString(localResultSet, "nick_name"));
        Profile.setXBeanFields(localResultSet, localPayee, "customer_payee");
        localPayees.add(localPayee);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localPayees;
  }
  
  public static Payees getCustomerPayeesX(SecureUser paramSecureUser, Payee paramPayee)
    throws ProfileException
  {
    String str = "PayeeAdapter.getCustomerPayeesX";
    StringBuffer localStringBuffer = new StringBuffer("select * from customer_payee where bank_id=? and directory_id=?");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Payees localPayees = new Payees();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLSelectColumns(localStringBuffer, "customer_payee", paramPayee);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = Profile.setStatementString(localPreparedStatement, 1, paramSecureUser.getBankID(), "bank_id", false);
      localPreparedStatement.setInt(i, paramSecureUser.getProfileID());
      i = Profile.setStatementValues(localPreparedStatement, i, paramPayee, "customer_payee", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Payee localPayee = new Payee();
        localPayee.setHostID(String.valueOf(localResultSet.getInt("payee_id")));
        localPayee.setNickName(Profile.getRSString(localResultSet, "nick_name"));
        Profile.setXBeanFields(localResultSet, localPayee, "customer_payee");
        localPayees.add(localPayee);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localPayees;
  }
  
  public static void modifyCustomerPayee(SecureUser paramSecureUser, Payee paramPayee)
    throws ProfileException
  {
    String str = "PayeeAdapter.modifyCustomerPayee";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("update customer_payee set nick_name=?");
    try
    {
      Profile.appendSQLUpdateColumns(localStringBuffer, "customer_payee", paramPayee);
      localStringBuffer.append(" where directory_id=? and payee_id=?");
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = Profile.setStatementString(localPreparedStatement, 1, paramPayee.getNickName(), "nick_name", false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramPayee, "customer_payee", true);
      localPreparedStatement.setInt(i++, paramSecureUser.getProfileID());
      i = Profile.setStatementString(localPreparedStatement, i, paramPayee.getHostID(), "payee_id", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteCustomerPayee(SecureUser paramSecureUser, Payee paramPayee)
    throws ProfileException
  {
    String str1 = "PayeeAdapter.deleteCustomerPayee";
    String str2 = null;
    if (paramPayee != null) {
      str2 = paramPayee.getHostID();
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      deleteCustomerPayeeTX(localConnection, paramSecureUser.getProfileID(), str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void deleteCustomerPayeeTX(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer("delete from customer_payee where directory_id=?");
    PreparedStatement localPreparedStatement = null;
    try
    {
      Profile.appendSQLSelectString(localStringBuffer, "payee_id", paramString, true);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      Profile.setStatementString(localPreparedStatement, 2, paramString, "payee_id", true);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.PayeeAdapter
 * JD-Core Version:    0.7.0.1
 */