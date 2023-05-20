package com.ffusion.accountgroups.adapter;

import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.util.accountgroups.AccountGroupsUtil;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class AccountGroupAdapter
{
  private static String d = null;
  private static String jdField_do = null;
  private static final String jdField_try = "DB_PROPERTIES";
  private static final String g = "ACCOUNT_GROUP_ID";
  private static final String jdField_void = "id";
  private static final String jdField_char = "name";
  private static final String h = "acct_group_id";
  private static final String k = "bus_directory_id";
  private static final String jdField_new = "account_id";
  private static final String m = "routing_num";
  private static final String n = "COUNT";
  private static final String jdField_byte = "INSERT INTO ACCOUNTGROUP ( ID, NAME, ACCT_GROUP_ID, BUS_DIRECTORY_ID ) values ( ?, ?, ?, ? )";
  private static final String jdField_else = "SELECT * FROM ACCOUNTGROUP WHERE BUS_DIRECTORY_ID = ?";
  private static final String jdField_int = "SELECT * FROM ACCOUNTGROUP WHERE ID = ?";
  private static final String jdField_case = "SELECT * FROM ACCOUNTGROUP WHERE NAME = ? and BUS_DIRECTORY_ID = ?";
  private static final String f = "UPDATE ACCOUNTGROUP SET ID = ?, NAME = ?, ACCT_GROUP_ID = ?, BUS_DIRECTORY_ID = ? WHERE ID = ?";
  private static final String b = "DELETE FROM ACCOUNTGROUP WHERE ID = ?";
  private static final String jdField_goto = "SELECT acc.ID, count(acc.ID) \"COUNT\" from ACCOUNTGROUP_ACCT acc, ACCOUNTGROUP grp WHERE grp.ID = acc.ID AND grp.BUS_DIRECTORY_ID = ? AND acc.ID IN";
  private static final String a = " ( SELECT ID FROM ACCOUNTGROUP_ACCT WHERE ACCOUNT_ID = ? AND ROUTING_NUM = ? )";
  private static final String jdField_for = " GROUP BY acc.ID";
  private static final String jdField_null = " ( SELECT ID from ACCOUNTGROUP_ACCT where ID in";
  private static final String l = " AND ACCOUNT_ID = ? AND ROUTING_NUM = ? )";
  private static final String j = "INSERT INTO ACCOUNTGROUP_ACCT ( ID, ACCOUNT_ID, ROUTING_NUM ) values ( ?, ?, ? )";
  private static final String jdField_long = "SELECT * FROM ACCOUNTGROUP_ACCT WHERE ID = ?";
  private static final String i = "DELETE FROM ACCOUNTGROUP_ACCT";
  private static final String jdField_if = " WHERE ";
  private static final String c = " OR ";
  private static final String e = "( ID = ? AND ACCOUNT_ID = ? AND ROUTING_NUM = ? )";
  
  public static void initialize(HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.initialize";
    if ((d != null) && (d.length() > 0)) {
      return;
    }
    try
    {
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      if (localProperties == null)
      {
        DebugLog.log("<DB_PROPERTIES> tag not found  in XML configuration file during intialization");
        throw new BusinessAccountGroupException(str, 1, "<DB_PROPERTIES> tag not found  in XML configuration file");
      }
      a(localProperties);
    }
    catch (Exception localException)
    {
      a(localException, str, 1, "Unable to initialize account group adapter");
    }
  }
  
  private static void a(Properties paramProperties)
    throws Exception
  {
    jdField_do = paramProperties.getProperty("ConnectionType");
    if (jdField_do == null) {
      throw new Exception("DB type unknown");
    }
    d = ConnectionPool.init(paramProperties);
  }
  
  private static void a()
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.isInitialized";
    if ((d == null) || (d.length() == 0)) {
      throw new BusinessAccountGroupException(str, 1, "Account group adapter is not initialized");
    }
  }
  
  private static void a(Exception paramException, String paramString1, int paramInt, String paramString2)
    throws BusinessAccountGroupException
  {
    if ((paramException instanceof BusinessAccountGroupException)) {
      throw ((BusinessAccountGroupException)paramException);
    }
    DebugLog.log(Level.FINE, "AccountGroupAdapter.handleError: " + paramException.getMessage());
    throw new BusinessAccountGroupException(paramException, paramString1, paramInt, paramString2);
  }
  
  private static void jdField_if(Connection paramConnection, BusinessAccountGroup paramBusinessAccountGroup)
    throws BusinessAccountGroupException
  {
    String str1 = "AccountGroupAdapter.checkGroupNameExists";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1 = paramBusinessAccountGroup.getBusDirId();
    String str2 = paramBusinessAccountGroup.getName();
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer("SELECT * FROM ACCOUNTGROUP WHERE NAME = ? and BUS_DIRECTORY_ID = ?");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setInt(2, i1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      if (localResultSet.next())
      {
        StringBuffer localStringBuffer2 = new StringBuffer("The account group name ");
        localStringBuffer2.append(paramBusinessAccountGroup.getName()).append(" already exists for the business ( ");
        localStringBuffer2.append(paramBusinessAccountGroup.getBusDirId()).append(" )");
        DebugLog.log(str1 + ": " + localStringBuffer2.toString());
        throw new BusinessAccountGroupException(str1, 3, localStringBuffer2.toString());
      }
    }
    catch (Exception localException)
    {
      a(localException, str1, 2, "");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void a(Connection paramConnection, BusinessAccountGroup paramBusinessAccountGroup)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.checkAccountsListExists";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1 = paramBusinessAccountGroup.getBusDirId();
    int i2 = 0;
    Accounts localAccounts = paramBusinessAccountGroup.getAccounts();
    try
    {
      if ((localAccounts != null) && (localAccounts.size() > 0))
      {
        StringBuffer localStringBuffer1 = new StringBuffer("SELECT acc.ID, count(acc.ID) \"COUNT\" from ACCOUNTGROUP_ACCT acc, ACCOUNTGROUP grp WHERE grp.ID = acc.ID AND grp.BUS_DIRECTORY_ID = ? AND acc.ID IN");
        for (int i3 = 0; i3 < localAccounts.size(); i3++) {
          if (i3 == localAccounts.size() - 1)
          {
            localStringBuffer1.append(" ( SELECT ID FROM ACCOUNTGROUP_ACCT WHERE ACCOUNT_ID = ? AND ROUTING_NUM = ? )");
            for (int i4 = 0; i4 < localAccounts.size() - 1; i4++) {
              localStringBuffer1.append(" AND ACCOUNT_ID = ? AND ROUTING_NUM = ? )");
            }
            localStringBuffer1.append(" GROUP BY acc.ID");
          }
          else
          {
            localStringBuffer1.append(" ( SELECT ID from ACCOUNTGROUP_ACCT where ID in");
          }
        }
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
        i3 = 1;
        localPreparedStatement.setInt(i3, i1);
        i3++;
        Iterator localIterator = localAccounts.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          localPreparedStatement.setString(i3, localAccount.getID());
          i3++;
          localPreparedStatement.setString(i3, localAccount.getRoutingNum());
          i3++;
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
        while ((i2 == 0) && (localResultSet.next()))
        {
          int i5 = localResultSet.getInt("COUNT");
          if (i5 == localAccounts.size()) {
            i2 = 1;
          }
        }
        if (i2 != 0)
        {
          StringBuffer localStringBuffer2 = new StringBuffer("The same list of accounts already exists for the business ( ");
          localStringBuffer2.append(paramBusinessAccountGroup.getBusDirId()).append(" )");
          DebugLog.log(str + ": " + localStringBuffer2.toString());
          throw new BusinessAccountGroupException(str, 4, localStringBuffer2.toString());
        }
      }
    }
    catch (Exception localException)
    {
      a(localException, str, 2, "");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static BusinessAccountGroup addAccountGroup(BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str1 = "AccountGroupAdapter.addAccountGroup";
    a();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i1 = paramBusinessAccountGroup.getBusDirId();
    String str2 = paramBusinessAccountGroup.getName();
    String str3 = paramBusinessAccountGroup.getAcctGroupId();
    Accounts localAccounts = paramBusinessAccountGroup.getAccounts();
    if (i1 <= 0)
    {
      DebugLog.log(str1 + ": The business directory must be specified.");
      throw new BusinessAccountGroupException(str1, 5, "The business directory must be specified.");
    }
    if (str2 == null)
    {
      DebugLog.log(str1 + ": The account group name must be specified.");
      throw new BusinessAccountGroupException(str1, 5, "The account group name must be specified.");
    }
    if (str3 == null)
    {
      DebugLog.log(str1 + ": The account group id must be specified.");
      throw new BusinessAccountGroupException(str1, 5, "The account group id must be specified.");
    }
    if ((localAccounts == null) || (localAccounts.size() == 0))
    {
      DebugLog.log(str1 + ": At least one account must be specified.");
      throw new BusinessAccountGroupException(str1, 5, "At least one account must be specified.");
    }
    try
    {
      localConnection = DBUtil.getConnection(d, true, 2);
      DBUtil.beginTransaction(localConnection);
      jdField_if(localConnection, paramBusinessAccountGroup);
      a(localConnection, paramBusinessAccountGroup);
      StringBuffer localStringBuffer = new StringBuffer("INSERT INTO ACCOUNTGROUP ( ID, NAME, ACCT_GROUP_ID, BUS_DIRECTORY_ID ) values ( ?, ?, ?, ? )");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i2 = DBUtil.getNextId(localConnection, jdField_do, "ACCOUNT_GROUP_ID");
      paramBusinessAccountGroup.setId(i2);
      localPreparedStatement.setInt(1, i2);
      localPreparedStatement.setString(2, str2);
      localPreparedStatement.setString(3, str3);
      localPreparedStatement.setInt(4, i1);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      if (localAccounts != null)
      {
        Iterator localIterator = localAccounts.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          a(localConnection, i2, localAccount.getID(), localAccount.getRoutingNum());
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      a(localException1, str1, 5, "");
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(d, localConnection, localPreparedStatement);
    }
    return paramBusinessAccountGroup;
  }
  
  public static BusinessAccountGroups getAccountGroups(int paramInt, HashMap paramHashMap, Locale paramLocale)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.getAccountGroups";
    a();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BusinessAccountGroups localBusinessAccountGroups = new BusinessAccountGroups();
    HashMap localHashMap = new HashMap();
    try
    {
      localConnection = DBUtil.getConnection(d, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("SELECT * FROM ACCOUNTGROUP WHERE BUS_DIRECTORY_ID = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        BusinessAccountGroup localBusinessAccountGroup = new BusinessAccountGroup();
        localBusinessAccountGroup.setId(localResultSet.getInt("id"));
        localBusinessAccountGroup.setName(localResultSet.getString("name"));
        localBusinessAccountGroup.setAcctGroupId(localResultSet.getString("acct_group_id"));
        localBusinessAccountGroup.setBusDirId(paramInt);
        boolean bool = true;
        Accounts localAccounts = a(localConnection, localBusinessAccountGroup.getId(), paramInt, localHashMap, bool, paramLocale);
        localBusinessAccountGroup.setAccounts(localAccounts);
        localBusinessAccountGroups.add(localBusinessAccountGroup);
      }
    }
    catch (Exception localException)
    {
      a(localException, str, 6, "");
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
    return localBusinessAccountGroups;
  }
  
  private static Accounts a(Connection paramConnection, int paramInt1, int paramInt2, HashMap paramHashMap, boolean paramBoolean, Locale paramLocale)
    throws BusinessAccountGroupException
  {
    String str1 = "AccountGroupAdapter.getAccountsByGroupId";
    Accounts localAccounts = new Accounts(paramLocale);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer1 = new StringBuffer("SELECT * FROM ACCOUNTGROUP_ACCT WHERE ID = ?");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      localPreparedStatement.setInt(1, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      String str2 = null;
      int i1 = 1;
      while (localResultSet.next())
      {
        if (i1 != 0)
        {
          str2 = AccountAdapter.getBankIdByDirectoryId(paramInt2);
          i1 = 0;
        }
        Account localAccount = null;
        String str3 = localResultSet.getString("account_id");
        String str4 = localResultSet.getString("routing_num");
        StringBuffer localStringBuffer2 = new StringBuffer(str3).append(":");
        localStringBuffer2.append(str4);
        if (paramBoolean)
        {
          if (paramHashMap.containsKey(localStringBuffer2.toString()))
          {
            localAccount = (Account)paramHashMap.get(localStringBuffer2.toString());
          }
          else
          {
            localAccount = AccountAdapter.getAccount(str2, str4, str3);
            paramHashMap.put(localStringBuffer2.toString(), localAccount);
          }
        }
        else {
          localAccount = AccountAdapter.getAccount(str2, str4, str3);
        }
        localAccounts.add(localAccount);
      }
    }
    catch (Exception localException)
    {
      a(localException, str1, 6, "");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localAccounts;
  }
  
  public static ArrayList getAccountGroupIds(int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.getAccountGroupIds";
    a();
    Connection localConnection = null;
    ArrayList localArrayList = null;
    try
    {
      localConnection = DBUtil.getConnection(d, true, 2);
      localArrayList = AccountGroupsUtil.getAccountGroupsIds(localConnection, paramInt, paramArrayList);
    }
    catch (Exception localException)
    {
      a(localException, str, 6, "");
    }
    finally
    {
      DBUtil.returnConnection(d, localConnection);
    }
    return localArrayList;
  }
  
  public static BusinessAccountGroup getAccountGroup(int paramInt, HashMap paramHashMap, Locale paramLocale)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.getAccountGroup";
    a();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BusinessAccountGroup localBusinessAccountGroup = null;
    try
    {
      localConnection = DBUtil.getConnection(d, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("SELECT * FROM ACCOUNTGROUP WHERE ID = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        localBusinessAccountGroup = new BusinessAccountGroup();
        localBusinessAccountGroup.setId(paramInt);
        localBusinessAccountGroup.setName(localResultSet.getString("name"));
        localBusinessAccountGroup.setAcctGroupId(localResultSet.getString("acct_group_id"));
        localBusinessAccountGroup.setBusDirId(localResultSet.getInt("bus_directory_id"));
        boolean bool = false;
        Accounts localAccounts = a(localConnection, localBusinessAccountGroup.getId(), localBusinessAccountGroup.getBusDirId(), null, bool, paramLocale);
        localBusinessAccountGroup.setAccounts(localAccounts);
      }
    }
    catch (Exception localException)
    {
      a(localException, str, 6, "");
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement, localResultSet);
    }
    return localBusinessAccountGroup;
  }
  
  public static void modifyAccountGroup(BusinessAccountGroup paramBusinessAccountGroup1, BusinessAccountGroup paramBusinessAccountGroup2, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.modifyAccountGroup";
    a();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(d, true, 2);
      DBUtil.beginTransaction(localConnection);
      if (!paramBusinessAccountGroup2.getName().equals(paramBusinessAccountGroup1.getName())) {
        jdField_if(localConnection, paramBusinessAccountGroup1);
      }
      HashMap localHashMap = a(paramBusinessAccountGroup2.getAccounts());
      boolean bool = a(localConnection, paramBusinessAccountGroup2, paramBusinessAccountGroup1, localHashMap);
      if (localHashMap.size() > 0)
      {
        if (!bool) {
          a(localConnection, paramBusinessAccountGroup1);
        }
        a(localConnection, paramBusinessAccountGroup1.getId(), localHashMap);
      }
      StringBuffer localStringBuffer = new StringBuffer("UPDATE ACCOUNTGROUP SET ID = ?, NAME = ?, ACCT_GROUP_ID = ?, BUS_DIRECTORY_ID = ? WHERE ID = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramBusinessAccountGroup1.getId());
      localPreparedStatement.setString(2, paramBusinessAccountGroup1.getName());
      localPreparedStatement.setString(3, paramBusinessAccountGroup1.getAcctGroupId());
      localPreparedStatement.setInt(4, paramBusinessAccountGroup1.getBusDirId());
      localPreparedStatement.setInt(5, paramBusinessAccountGroup2.getId());
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      a(localException1, str, 7, "");
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(d, localConnection, localPreparedStatement);
    }
  }
  
  private static void a(Connection paramConnection, int paramInt, String paramString1, String paramString2)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.addAccount";
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("INSERT INTO ACCOUNTGROUP_ACCT ( ID, ACCOUNT_ID, ROUTING_NUM ) values ( ?, ?, ? )");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString1);
      localPreparedStatement.setString(3, paramString2);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      a(localException, str, 2, "");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static void deleteAccountGroup(BusinessAccountGroup paramBusinessAccountGroup, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.deleteAccountGroup";
    a();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(d, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("DELETE FROM ACCOUNTGROUP WHERE ID = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramBusinessAccountGroup.getId());
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      a(localException, str, 8, "");
    }
    finally
    {
      DBUtil.closeAll(d, localConnection, localPreparedStatement);
    }
  }
  
  private static void a(Connection paramConnection, int paramInt, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.deleteAccounts";
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("DELETE FROM ACCOUNTGROUP_ACCT");
      localStringBuffer.append(" WHERE ");
      for (int i1 = 0; i1 < paramHashMap.size(); i1++)
      {
        if (i1 > 0) {
          localStringBuffer.append(" OR ");
        }
        localStringBuffer.append("( ID = ? AND ACCOUNT_ID = ? AND ROUTING_NUM = ? )");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      Collection localCollection = paramHashMap.values();
      Iterator localIterator = localCollection.iterator();
      for (int i2 = 1; localIterator.hasNext(); i2++)
      {
        Account localAccount = (Account)localIterator.next();
        localPreparedStatement.setInt(i2, paramInt);
        i2++;
        localPreparedStatement.setString(i2, localAccount.getID());
        i2++;
        localPreparedStatement.setString(i2, localAccount.getRoutingNum());
      }
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      a(localException, str, 2, "");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static boolean a(Connection paramConnection, BusinessAccountGroup paramBusinessAccountGroup1, BusinessAccountGroup paramBusinessAccountGroup2, HashMap paramHashMap)
    throws BusinessAccountGroupException
  {
    String str = "AccountGroupAdapter.getAccountsToDeleteAndAddNewAccounts";
    boolean bool = false;
    try
    {
      Accounts localAccounts = paramBusinessAccountGroup2.getAccounts();
      if (localAccounts != null)
      {
        Iterator localIterator = localAccounts.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          StringBuffer localStringBuffer = new StringBuffer(localAccount.getRoutingNum());
          localStringBuffer.append(":");
          localStringBuffer.append(localAccount.getID());
          if (!paramHashMap.containsKey(localStringBuffer.toString()))
          {
            if (!bool)
            {
              a(paramConnection, paramBusinessAccountGroup2);
              bool = true;
            }
            a(paramConnection, paramBusinessAccountGroup2.getId(), localAccount.getID(), localAccount.getRoutingNum());
          }
          else
          {
            paramHashMap.remove(localStringBuffer.toString());
          }
        }
      }
    }
    catch (Exception localException)
    {
      a(localException, str, 2, "");
    }
    return bool;
  }
  
  private static HashMap a(Accounts paramAccounts)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    if (paramAccounts != null)
    {
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        StringBuffer localStringBuffer = new StringBuffer(localAccount.getRoutingNum());
        localStringBuffer.append(":");
        localStringBuffer.append(localAccount.getID());
        localHashMap.put(localStringBuffer.toString(), localAccount);
      }
    }
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.accountgroups.adapter.AccountGroupAdapter
 * JD-Core Version:    0.7.0.1
 */