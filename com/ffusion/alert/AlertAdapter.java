package com.ffusion.alert;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.AccountAlert;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.services.alerts.AlertsException;
import com.ffusion.util.IntMap;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

public class AlertAdapter
{
  public static final String XML_DB_PROPERTIES_TAG = "DB_PROPERTIES";
  public static final String XML_CONTACT_POINTS_TAG = "CONTACT_POINT_LIST";
  public static final String XML_CONTACT_POINT_PROPERTIES_TAG = "CONTACT_POINT_PROPERTIES";
  public static final String XML_TYPE_TAG = "Type";
  public static final String XML_CHANNEL_NAME_TAG = "ChannelName";
  public static final String KEY_PRIMARY_CONTACTS = "PrimaryContacts";
  public static final String KEY_SECONDARY_CONTACTS = "SecondaryContacts";
  public static final String VAL_ONE_TIME_TRUE = "T";
  public static final String VAL_ONE_TIME_FALSE = "F";
  private static final String jdField_new = "ALERT_ID";
  private static final String d = "insert into USER_ALERTS( ALERT_ID, DIRECTORY_ID, ALERT_TYPE, STATUS, ONE_TIME ) values( ?, ?, ?, ?, ? )";
  private static final String f = "insert into ACCOUNT_ALERTS( ALERT_ID, ROUTING_NUMBER, ACCOUNT_ID, ACCOUNT_TYPE ) values( ?, ?, ?, ? )";
  private static final String jdField_null = "insert into ALERT_PROPERTIES( ALERT_ID, PROP_NAME, PROP_VALUE ) values( ?, ?, ? )";
  private static final String jdField_char = "update USER_ALERTS set STATUS=?, ONE_TIME=? where ALERT_ID=?";
  private static final String h = "update ACCOUNT_ALERTS set ROUTING_NUMBER=?, ACCOUNT_ID=?, ACCOUNT_TYPE=? where ALERT_ID=?";
  private static final String jdField_try = "delete from USER_ALERTS where ALERT_ID=?";
  private static final String jdField_long = "delete from ALERT_PROPERTIES where ALERT_ID=?";
  private static final String jdField_do = "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE ";
  private static final int jdField_goto = 10;
  private static final String a = "from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID ";
  private static final String jdField_void = "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID where DIRECTORY_ID=? and STATUS=1 order by a.ALERT_ID";
  private static final String c = "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID , customer c where a.DIRECTORY_ID=c.directory_id and a.ALERT_ID=? and STATUS=1";
  private static final String b = "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) ";
  private static final String jdField_if = "select ALERT_ID_1, DIRECTORY_ID, ALERT_TYPE, STATUS, ONE_TIME, ALERT_ID_2, ROUTING_NUMBER, ACCOUNT_ID, ACCOUNT_TYPE, preferred_lang from (select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) and a.DIRECTORY_ID=? union select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) and a.DIRECTORY_ID in ( select DIRECTORY_ID from CUSTOMER_REL where PRIMARY_USER_ID=? )) t order by ALERT_ID_1";
  private static final String jdField_else = "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID where a.ALERT_TYPE={0} and a.ALERT_ID>{1} and STATUS=1 order by a.ALERT_ID";
  private static final String e = "select PROP_NAME, PROP_VALUE from ALERT_PROPERTIES where ALERT_ID=?";
  private static final String jdField_case = "select c.directory_id, a.nickname from accounts a, customer c where c.directory_id=a.directory_id and (a.hide is null or NOT a.hide=1) and a.account_id=? and a.routing_num=? AND c.bank_id=? ";
  private static final String jdField_int = "select c.preferred_lang from customer c where c.directory_id = ?";
  private static IntMap jdField_for;
  private static String g;
  private static String jdField_byte;
  
  public static void initialize(HashMap paramHashMap)
    throws AlertsException
  {
    String str1 = "AlertAdapter.initialize";
    if ((g != null) && (g.length() > 0)) {
      return;
    }
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("CONTACT_POINT_LIST");
      if (localHashMap == null) {
        throw new AlertsException(str1, 2, "The CONTACT_POINT_LIST tag is missing in the adapter settings.");
      }
      ArrayList localArrayList = (ArrayList)localHashMap.get("CONTACT_POINT_PROPERTIES");
      IntMap localIntMap = new IntMap();
      Object localObject;
      if (localArrayList != null) {
        for (int i = 0; i < localArrayList.size(); i++)
        {
          localObject = (Properties)localArrayList.get(i);
          String str2 = ((Properties)localObject).getProperty("Type");
          String str3 = ((Properties)localObject).getProperty("ChannelName");
          if (str2 == null) {
            throw new AlertsException(str1, 3, "The Type tag is missing from a CONTACT_POINT_PROPERTIES tag in the adapter settings.");
          }
          if (str3 == null) {
            throw new AlertsException(str1, 5, "The ChannelName tag is missing from a CONTACT_POINT_PROPERTIES tag with type '" + str2 + "' in the adapter settings.");
          }
          int j = 0;
          try
          {
            j = Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            throw new AlertsException(str1, 5, "The Type tag is invalid in a CONTACT_POINT_PROPERTIES tag with a type of '" + str2 + "' in the adapter settings.");
          }
          localIntMap.put(j, str3);
        }
      }
      jdField_for = localIntMap;
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      if (localProperties == null)
      {
        localObject = new AlertsException(str1, 19005, "DB_PROPERTIES tag not found in XML configuration file during intialization.");
        DebugLog.throwing(str1, (Throwable)localObject);
        throw ((Throwable)localObject);
      }
      jdField_byte = localProperties.getProperty("ConnectionType");
      if (jdField_byte == null)
      {
        localObject = new AlertsException(str1, 19005, "The ConnectionType tag is missing in the XML configuration file during intialization.");
        DebugLog.throwing(str1, (Throwable)localObject);
        throw ((Throwable)localObject);
      }
      g = ConnectionPool.init(localProperties);
    }
    catch (AlertsException localAlertsException)
    {
      throw localAlertsException;
    }
    catch (Exception localException)
    {
      throw new AlertsException(str1, 1, localException);
    }
  }
  
  public static String getChannelName(int paramInt)
    throws AlertsException
  {
    String str1 = "AlertsAdapter.getChannelName";
    String str2 = (String)jdField_for.get(paramInt);
    if (str2 == null)
    {
      AlertsException localAlertsException = new AlertsException(str1, 6, "The contact type of '" + paramInt + "' does not have an entry in the contact types list.");
      DebugLog.throwing(str1, localAlertsException);
      throw localAlertsException;
    }
    return str2;
  }
  
  public static void addUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "AlertAdapter.addUserAlert";
    try
    {
      localConnection = DBUtil.getConnection(g, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into USER_ALERTS( ALERT_ID, DIRECTORY_ID, ALERT_TYPE, STATUS, ONE_TIME ) values( ?, ?, ?, ?, ? )");
      int i = DBUtil.getNextId(localConnection, jdField_byte, "ALERT_ID");
      localPreparedStatement.setInt(1, i);
      localPreparedStatement.setInt(2, paramUserAlert.getDirectoryIdValue());
      localPreparedStatement.setInt(3, paramUserAlert.getAlertTypeValue());
      localPreparedStatement.setInt(4, paramUserAlert.getStatusValue());
      localPreparedStatement.setString(5, paramUserAlert.getOneTimeValue() ? "T" : "F");
      DBUtil.execute(localPreparedStatement, "insert into USER_ALERTS( ALERT_ID, DIRECTORY_ID, ALERT_TYPE, STATUS, ONE_TIME ) values( ?, ?, ?, ?, ? )");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if ((paramUserAlert instanceof AccountAlert))
      {
        AccountAlert localAccountAlert = (AccountAlert)paramUserAlert;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into ACCOUNT_ALERTS( ALERT_ID, ROUTING_NUMBER, ACCOUNT_ID, ACCOUNT_TYPE ) values( ?, ?, ?, ? )");
        localPreparedStatement.setInt(1, i);
        localPreparedStatement.setString(2, localAccountAlert.getRoutingNumber());
        localPreparedStatement.setString(3, localAccountAlert.getAccountId());
        if (localAccountAlert.getAccountTypeValue() == -2147483648) {
          localPreparedStatement.setNull(4, 4);
        } else {
          localPreparedStatement.setInt(4, localAccountAlert.getAccountTypeValue());
        }
        DBUtil.execute(localPreparedStatement, "insert into ACCOUNT_ALERTS( ALERT_ID, ROUTING_NUMBER, ACCOUNT_ID, ACCOUNT_TYPE ) values( ?, ?, ?, ? )");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      a(localConnection, i, paramUserAlert);
      DBUtil.commit(localConnection);
      paramUserAlert.setUserAlertId(i);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(g, localConnection, localPreparedStatement);
    }
  }
  
  public static void modifyUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str1 = "AlertAdapter.modifyUserAlert";
    try
    {
      localConnection = DBUtil.getConnection(g, false, 2);
      String str2 = "F";
      if (paramUserAlert.getOneTime() == "true") {
        str2 = "T";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update USER_ALERTS set STATUS=?, ONE_TIME=? where ALERT_ID=?");
      int i = paramUserAlert.getUserAlertIdValue();
      localPreparedStatement.setInt(1, paramUserAlert.getStatusValue());
      localPreparedStatement.setString(2, str2);
      localPreparedStatement.setInt(3, i);
      int j = DBUtil.executeUpdate(localPreparedStatement, "update USER_ALERTS set STATUS=?, ONE_TIME=? where ALERT_ID=?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (j == 0) {
        return;
      }
      if ((paramUserAlert instanceof AccountAlert))
      {
        AccountAlert localAccountAlert = (AccountAlert)paramUserAlert;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update ACCOUNT_ALERTS set ROUTING_NUMBER=?, ACCOUNT_ID=?, ACCOUNT_TYPE=? where ALERT_ID=?");
        localPreparedStatement.setString(1, localAccountAlert.getRoutingNumber());
        localPreparedStatement.setString(2, localAccountAlert.getAccountId());
        if (localAccountAlert.getAccountTypeValue() == -2147483648) {
          localPreparedStatement.setNull(3, 4);
        } else {
          localPreparedStatement.setInt(3, localAccountAlert.getAccountTypeValue());
        }
        localPreparedStatement.setInt(4, i);
        j = DBUtil.executeUpdate(localPreparedStatement, "update ACCOUNT_ALERTS set ROUTING_NUMBER=?, ACCOUNT_ID=?, ACCOUNT_TYPE=? where ALERT_ID=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from ALERT_PROPERTIES where ALERT_ID=?");
      localPreparedStatement.setInt(1, i);
      j = DBUtil.executeUpdate(localPreparedStatement, "delete from ALERT_PROPERTIES where ALERT_ID=?");
      a(localConnection, i, paramUserAlert);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(g, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteUserAlert(SecureUser paramSecureUser, UserAlert paramUserAlert, HashMap paramHashMap)
    throws AlertsException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "AlertAdapter.deleteUserAlert";
    try
    {
      localConnection = DBUtil.getConnection(g, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from USER_ALERTS where ALERT_ID=?");
      int i = paramUserAlert.getUserAlertIdValue();
      localPreparedStatement.setInt(1, i);
      DBUtil.executeUpdate(localPreparedStatement, "delete from USER_ALERTS where ALERT_ID=?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeAll(g, localConnection, localPreparedStatement);
    }
  }
  
  public static UserAlerts getUserAlerts(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException
  {
    String str = "AlertAdapter.getUserAlerts";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    Locale localLocale = paramSecureUser.getLocale();
    UserAlerts localUserAlerts = new UserAlerts();
    localUserAlerts.setLocale(localLocale);
    try
    {
      localConnection = DBUtil.getConnection(g, true, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select PROP_NAME, PROP_VALUE from ALERT_PROPERTIES where ALERT_ID=?");
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID where DIRECTORY_ID=? and STATUS=1 order by a.ALERT_ID");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID where DIRECTORY_ID=? and STATUS=1 order by a.ALERT_ID");
      while (localResultSet.next())
      {
        UserAlert localUserAlert = a(localResultSet, localPreparedStatement2);
        localUserAlert.setLocale(localLocale);
        localUserAlerts.add(localUserAlert);
      }
    }
    catch (Exception localException)
    {
      a(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeAll(g, localConnection, localPreparedStatement1, localResultSet);
    }
    return localUserAlerts;
  }
  
  public static UserAlert getUserAlert(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws AlertsException
  {
    String str1 = "AlertAdapter.getUserAlert";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    UserAlert localUserAlert = null;
    try
    {
      localConnection = DBUtil.getConnection(g, true, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select PROP_NAME, PROP_VALUE from ALERT_PROPERTIES where ALERT_ID=?");
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID , customer c where a.DIRECTORY_ID=c.directory_id and a.ALERT_ID=? and STATUS=1");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, "select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID , customer c where a.DIRECTORY_ID=c.directory_id and a.ALERT_ID=? and STATUS=1");
      if (localResultSet.next())
      {
        localUserAlert = a(localResultSet, localPreparedStatement2);
        String str2 = localResultSet.getString(10);
        localUserAlert.setLocale(str2);
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeAll(g, localConnection, localPreparedStatement1, localResultSet);
    }
    return localUserAlert;
  }
  
  public static ArrayList getAlertsForAccounts(Accounts paramAccounts, HashMap paramHashMap)
    throws AlertsException
  {
    String str1 = "AlertAdapter.getAlertsForAccounts";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localConnection = DBUtil.getConnection(g, true, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ALERT_ID_1, DIRECTORY_ID, ALERT_TYPE, STATUS, ONE_TIME, ALERT_ID_2, ROUTING_NUMBER, ACCOUNT_ID, ACCOUNT_TYPE, preferred_lang from (select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) and a.DIRECTORY_ID=? union select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) and a.DIRECTORY_ID in ( select DIRECTORY_ID from CUSTOMER_REL where PRIMARY_USER_ID=? )) t order by ALERT_ID_1");
      localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "select PROP_NAME, PROP_VALUE from ALERT_PROPERTIES where ALERT_ID=?");
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select c.directory_id, a.nickname from accounts a, customer c where c.directory_id=a.directory_id and (a.hide is null or NOT a.hide=1) and a.account_id=? and a.routing_num=? AND c.bank_id=? ");
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        UserAlerts localUserAlerts = new UserAlerts();
        localPreparedStatement1.setString(1, localAccount.getID());
        localPreparedStatement1.setString(2, localAccount.getRoutingNum());
        localPreparedStatement1.setString(3, localAccount.getBankID());
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select c.directory_id, a.nickname from accounts a, customer c where c.directory_id=a.directory_id and (a.hide is null or NOT a.hide=1) and a.account_id=? and a.routing_num=? AND c.bank_id=? ");
        while (localResultSet1.next())
        {
          int i = localResultSet1.getInt(1);
          String str2 = localResultSet1.getString(2);
          localPreparedStatement2.setString(1, localAccount.getRoutingNum());
          localPreparedStatement2.setString(2, localAccount.getID());
          localPreparedStatement2.setString(3, localAccount.getBankID());
          localPreparedStatement2.setInt(4, i);
          localPreparedStatement2.setString(5, localAccount.getRoutingNum());
          localPreparedStatement2.setString(6, localAccount.getID());
          localPreparedStatement2.setString(7, localAccount.getBankID());
          localPreparedStatement2.setInt(8, i);
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ALERT_ID_1, DIRECTORY_ID, ALERT_TYPE, STATUS, ONE_TIME, ALERT_ID_2, ROUTING_NUMBER, ACCOUNT_ID, ACCOUNT_TYPE, preferred_lang from (select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) and a.DIRECTORY_ID=? union select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE , c.preferred_lang from USER_ALERTS a, ACCOUNT_ALERTS b, customer c where a.ALERT_ID = b.ALERT_ID and a.DIRECTORY_ID=c.directory_id and STATUS=1 and ( ( b.ROUTING_NUMBER is null and b.ACCOUNT_ID is null and b.ACCOUNT_TYPE is null ) or ( b.ROUTING_NUMBER=? and b.ACCOUNT_ID=? and c.bank_id=? ) ) and a.DIRECTORY_ID in ( select DIRECTORY_ID from CUSTOMER_REL where PRIMARY_USER_ID=? )) t order by ALERT_ID_1");
          while (localResultSet2.next())
          {
            AccountAlert localAccountAlert = (AccountAlert)a(localResultSet2, localPreparedStatement3);
            localAccountAlert.setNickname(str2);
            String str3 = localResultSet2.getString(10);
            localAccountAlert.setLocale(str3);
            localUserAlerts.add(localAccountAlert);
          }
          DBUtil.closeResultSet(localResultSet2);
          localResultSet2 = null;
        }
        if (localUserAlerts.size() == 0) {
          localUserAlerts = null;
        }
        localArrayList.add(localUserAlerts);
        DBUtil.closeResultSet(localResultSet1);
        localResultSet1 = null;
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.closeAll(localPreparedStatement2, localResultSet2);
      DBUtil.closeAll(g, localConnection, localPreparedStatement1, localResultSet1);
    }
    return localArrayList;
  }
  
  public static UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws AlertsException
  {
    String str1 = "AlertAdapter.getPagedUserAlerts";
    Connection localConnection = null;
    Statement localStatement = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = null;
    UserAlerts localUserAlerts = new UserAlerts();
    HashMap localHashMap = new HashMap();
    try
    {
      localConnection = DBUtil.getConnection(g, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select PROP_NAME, PROP_VALUE from ALERT_PROPERTIES where ALERT_ID=?");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select c.preferred_lang from customer c where c.directory_id = ?");
      String str3 = MessageFormat.format("select a.ALERT_ID as ALERT_ID_1, a.DIRECTORY_ID, a.ALERT_TYPE, a.STATUS, a.ONE_TIME, b.ALERT_ID as ALERT_ID_2, b.ROUTING_NUMBER, b.ACCOUNT_ID, b.ACCOUNT_TYPE from USER_ALERTS a left outer join ACCOUNT_ALERTS b on a.ALERT_ID = b.ALERT_ID where a.ALERT_TYPE={0} and a.ALERT_ID>{1} and STATUS=1 order by a.ALERT_ID", new Object[] { Integer.toString(paramInt1), Integer.toString(paramInt2) });
      localStatement = DBUtil.createStatement(localConnection);
      localStatement.setMaxRows(paramInt3);
      localResultSet1 = DBUtil.executeQuery(localStatement, str3);
      while (localResultSet1.next())
      {
        UserAlert localUserAlert = a(localResultSet1, localPreparedStatement1);
        localUserAlerts.add(localUserAlert);
        str2 = (String)localHashMap.get(new Integer(localUserAlert.getDirectoryIdValue()));
        if (str2 == null)
        {
          localPreparedStatement2.setInt(1, localUserAlert.getDirectoryIdValue());
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select c.preferred_lang from customer c where c.directory_id = ?");
          if (localResultSet2.next())
          {
            str2 = localResultSet2.getString(1);
            localHashMap.put(new Integer(localUserAlert.getDirectoryIdValue()), str2);
          }
          DBUtil.closeResultSet(localResultSet2);
          localResultSet2 = null;
        }
        localUserAlert.setLocale(str2);
      }
    }
    catch (Exception localException)
    {
      a(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeAll(g, localConnection, localStatement, localResultSet1);
    }
    return localUserAlerts;
  }
  
  private static void a(Connection paramConnection, int paramInt, UserAlert paramUserAlert)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into ALERT_PROPERTIES( ALERT_ID, PROP_NAME, PROP_VALUE ) values( ?, ?, ? )");
      Properties localProperties = paramUserAlert.getAdditionalProperties();
      Iterator localIterator = localProperties.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        if (!str1.equals("SECUREUSER"))
        {
          String str2 = localProperties.getProperty(str1);
          localPreparedStatement.setInt(1, paramInt);
          localPreparedStatement.setString(2, str1);
          localPreparedStatement.setString(3, str2);
          DBUtil.execute(localPreparedStatement, "insert into ALERT_PROPERTIES( ALERT_ID, PROP_NAME, PROP_VALUE ) values( ?, ?, ? )");
        }
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static UserAlert a(ResultSet paramResultSet, PreparedStatement paramPreparedStatement)
    throws Exception
  {
    ResultSet localResultSet = null;
    try
    {
      int i = paramResultSet.getInt(1);
      int j = paramResultSet.getInt(2);
      int k = paramResultSet.getInt(3);
      int m = paramResultSet.getInt(4);
      String str1 = paramResultSet.getString(5);
      paramResultSet.getInt(6);
      int n = !paramResultSet.wasNull() ? 1 : 0;
      String str2 = paramResultSet.getString(7);
      String str3 = paramResultSet.getString(8);
      int i1 = paramResultSet.getInt(9);
      Object localObject1;
      if (n != 0)
      {
        localObject2 = new AccountAlert();
        ((AccountAlert)localObject2).setRoutingNumber(str2);
        ((AccountAlert)localObject2).setAccountId(str3);
        ((AccountAlert)localObject2).setAccountType(i1);
        localObject1 = localObject2;
      }
      else
      {
        localObject1 = new UserAlert();
      }
      ((UserAlert)localObject1).setUserAlertId(i);
      ((UserAlert)localObject1).setDirectoryId(j);
      ((UserAlert)localObject1).setAlertType(k);
      ((UserAlert)localObject1).setStatus(m);
      ((UserAlert)localObject1).setOneTime(str1.equals("T"));
      paramPreparedStatement.setInt(1, i);
      localResultSet = DBUtil.executeQuery(paramPreparedStatement, "select PROP_NAME, PROP_VALUE from ALERT_PROPERTIES where ALERT_ID=?");
      Object localObject2 = new Properties();
      while (localResultSet.next())
      {
        localObject3 = localResultSet.getString(1);
        String str4 = localResultSet.getString(2);
        if (str4 == null) {
          str4 = "";
        }
        ((Properties)localObject2).setProperty((String)localObject3, str4);
      }
      ((UserAlert)localObject1).setAdditionalProperites((Properties)localObject2);
      Object localObject3 = localObject1;
      return localObject3;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static void a(Exception paramException, String paramString)
    throws AlertsException
  {
    if ((paramException instanceof AlertsException)) {
      throw ((AlertsException)paramException);
    }
    DebugLog.log(Level.FINE, "AlertsAdapter.handleError: " + paramException.getMessage());
    throw new AlertsException(paramString, 7, paramException);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.AlertAdapter
 * JD-Core Version:    0.7.0.1
 */