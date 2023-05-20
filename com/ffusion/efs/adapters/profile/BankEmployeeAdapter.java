package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class BankEmployeeAdapter
  implements ProfileDefines
{
  protected static MessageAdapter messageAdapter = new MessageAdapter();
  private static final String ajS = "select employee_id from bank_employee where bank_id=? and user_name=? and user_password=? and employee_status != 1";
  private static final String aj3 = "select * from bank_employee where bank_id=? and user_name=? and user_password=? and employee_status != 1";
  private static final String ajY = "select * from bank_employee where employee_id=?";
  private static final String aj4 = "select * from bank_employee be";
  private static final String ajR = "select count(*) from bank_employee";
  private static final String ajZ = "select * from bank_employee where employee_status != 1";
  private static final String ajM = "select * from bank_employee where supervisor=?";
  private static final String ajP = "insert into bank_employee (employee_id,bank_id,user_name,bank_employee_id,first_name,last_name,email_address,employee_admin_access,user_password,address1,address2,city,state,zip,home_phone,employee_status,supervisor,employee_notify,review_required,obo_enabled,approval_provider,msg_approval_provider,affiliate_bank_id,password_status,password_fail_count";
  private static final String aj7 = "delete from bank_employee where employee_id = ?";
  private static final String ajQ = "update bank_employee set bank_id=?,user_name=?,bank_employee_id=?,first_name=?,last_name=?,email_address=?,employee_admin_access=?,user_password=?,address1=?,address2=?,city=?,state=?,zip=?,home_phone=?,employee_status=?,supervisor=?,employee_notify=?,review_required=?,obo_enabled=?,approval_provider=?,msg_approval_provider=?,affiliate_bank_id=?";
  private static final String aj0 = "update bank_employee set employee_status=? where employee_id=?";
  private static final String aj5 = " where employee_id=?";
  private static final String ajN = "update bank_employee set supervisor=? where supervisor=?";
  private static final String aka = "update customer set personal_banker=? where personal_banker=?";
  private static final String akd = "select * from bank_employee where user_name=? and user_password=? and employee_status != 1";
  private static final String ajU = "select * from bank_employee where user_name=? and employee_status != 1";
  private static final String ajV = "select employee_id from bank_employee where supervisor=?";
  private static final String ajO = "select employee_id from bank_employee where ( supervisor=? and affiliate_bank_id=? ) OR employee_id in ( select EMPLOYEE_ID from AFFILIATE_ASSIGN where EMPLOYEE_ID in ( select employee_id from bank_employee where supervisor=? ) and AFFILIATE_BANK_ID=? )";
  private static final String ajX = "select AFFILIATE_BANK_ID from AFFILIATE_ASSIGN where EMPLOYEE_ID = ?";
  private static final String aj8 = "delete from AFFILIATE_ASSIGN where EMPLOYEE_ID = ?";
  private static final String ajW = "insert into AFFILIATE_ASSIGN (EMPLOYEE_ID, AFFILIATE_BANK_ID) values (?, ?)";
  private static final String aj2 = "select EMPLOYEE_ID from AFFILIATE_ASSIGN";
  private static final String ajL = "select password_status, password_fail_count, password_lockout_time from bank_employee where employee_id=?";
  private static final String akc = "update bank_employee set user_password=?, password_status=?, password_fail_count=?, password_lockout_time=? where employee_id=?";
  private static final String ajT = "update bank_employee set password_status=?,password_fail_count=?,password_lockout_time=? where employee_id=?";
  private static final String aj9 = "select PASSWORD_DATE , PASSWORD from PASSWORD_HISTORY where USER_TYPE = ?  and USER_ID = ? order by PASSWORD_DATE desc";
  private static final String aj6 = "update bank_employee set last_active_date=? where employee_id=?";
  private static final String akb = "update bank_employee set password_status=? where employee_id=?";
  private static final String ajK = "update bank_employee set user_password=? where employee_id=?";
  private static final String aj1 = "select employee_id from bank_employee where user_name=?";
  
  protected static ArrayList getBankEmployeeBanks(Connection paramConnection, BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str1 = "BankEmployeeAdapter.getBankEmployeeBanks";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select AFFILIATE_BANK_ID from AFFILIATE_ASSIGN where EMPLOYEE_ID = ?");
    ArrayList localArrayList = new ArrayList();
    Profile.isInitialized();
    int i = Profile.convertToInt(paramBankEmployee.getId());
    String str2 = paramBankEmployee.getDefaultAffiliateBankId();
    if ((str2 != null) && (str2.length() > 0) && (!str2.equals("0"))) {
      localArrayList.add(str2);
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, i, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next()) {
        localArrayList.add(String.valueOf(localResultSet.getInt(1)));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    paramBankEmployee.setAffiliateBankIds(localArrayList);
    return localArrayList;
  }
  
  protected static void deleteBankEmployeeBanks(Connection paramConnection, BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.deleteBankEmployeeBanks";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("delete from AFFILIATE_ASSIGN where EMPLOYEE_ID = ?");
    Profile.isInitialized();
    int i = Profile.convertToInt(paramBankEmployee.getId());
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, i, false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  protected static void updateBankEmployeeBanks(Connection paramConnection, BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str1 = "BankEmployeeAdapter.updateBankEmployeeBanks";
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    StringBuffer localStringBuffer = new StringBuffer("insert into AFFILIATE_ASSIGN (EMPLOYEE_ID, AFFILIATE_BANK_ID) values (?, ?)");
    Profile.isInitialized();
    try
    {
      deleteBankEmployeeBanks(paramConnection, paramBankEmployee);
      int i = Profile.convertToInt(paramBankEmployee.getId());
      int j = 0;
      String str2 = paramBankEmployee.getDefaultAffiliateBankId();
      if ((str2 != null) && (str2.length() > 0) && (!str2.equals("0"))) {
        j = Profile.convertToInt(str2);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      Iterator localIterator = paramBankEmployee.getAffiliateBankIds().iterator();
      while (localIterator.hasNext())
      {
        int k = Profile.convertToInt((String)localIterator.next());
        if (j != k)
        {
          int m = 1;
          m = Profile.setStatementInt(localPreparedStatement, m, i, false);
          m = Profile.setStatementInt(localPreparedStatement, m, k, false);
          DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static BankEmployee getBankEmployee(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.getBankEmployee";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from bank_employee where bank_id=? and user_name=? and user_password=? and employee_status != 1");
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getBankId(), "bank_id", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getUserName(), "user_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getPassword(), "user_password", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true)
      {
        jdMethod_for(localResultSet, paramBankEmployee);
        getBankEmployeeBanks(localConnection, paramBankEmployee);
      }
      else
      {
        throw new Exception("Bank employee not found: invalid bankId/username/password: 12");
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
    return paramBankEmployee;
  }
  
  public static BankEmployee getBankEmployeeById(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.getBankEmployeeById";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from bank_employee where employee_id=?");
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      int j = Profile.convertToInt(paramBankEmployee.getId());
      i = Profile.setStatementInt(localPreparedStatement, i, j, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next() == true)
      {
        jdMethod_for(localResultSet, paramBankEmployee);
        getBankEmployeeBanks(localConnection, paramBankEmployee);
      }
      else
      {
        throw new Exception("Bank employee not found: invalid employeeId: 12");
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
    return paramBankEmployee;
  }
  
  public static BankEmployees getFilteredBankEmployees(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str1 = "BankEmployeeAdapter.getFilteredBankEmployees";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BankEmployees localBankEmployees = new BankEmployees(Locale.getDefault());
    StringBuffer localStringBuffer1 = new StringBuffer("select * from bank_employee be");
    Profile.isInitialized();
    if (paramBankEmployee == null) {
      paramBankEmployee = new BankEmployee(Locale.getDefault());
    }
    try
    {
      String str2 = paramBankEmployee.getStatus();
      String str3 = paramBankEmployee.getSupervisor();
      String str4 = paramBankEmployee.getDefaultAffiliateBankId();
      boolean bool = false;
      bool = Profile.appendSQLSelectString(localStringBuffer1, "bank_id", paramBankEmployee.getBankId(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "user_name", paramBankEmployee.getUserName(), true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "first_name", paramBankEmployee.getFirstName(), true, true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "last_name", paramBankEmployee.getLastName(), true, true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "bank_employee_id", paramBankEmployee.getEmployeeId(), true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "employee_status", str2, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "supervisor", str3, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "affiliate_bank_id", str4, bool);
      ArrayList localArrayList = paramBankEmployee.getAffiliateBankIds();
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        if (bool)
        {
          localStringBuffer1.append(" and ( ( ");
        }
        else
        {
          localStringBuffer1.append(" where ( ( ");
          bool = true;
        }
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append("(");
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          localStringBuffer2.append(localIterator.next() + ",");
        }
        localStringBuffer2.setCharAt(localStringBuffer2.length() - 1, ')');
        localStringBuffer1.append("employee_id in (");
        localStringBuffer1.append("select EMPLOYEE_ID from AFFILIATE_ASSIGN where AFFILIATE_BANK_ID in ");
        localStringBuffer1.append(localStringBuffer2.toString());
        localStringBuffer1.append(" ) ");
        localStringBuffer1.append("or affiliate_bank_id in " + localStringBuffer2);
        localStringBuffer1.append(") ");
        localStringBuffer1.append("or ( be.affiliate_bank_id = 0 ");
        localStringBuffer1.append(" and not exists (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=be.employee_id)");
        localStringBuffer1.append(" ) ");
        localStringBuffer1.append(" ) ");
      }
      Profile.appendSQLSelectColumns(localStringBuffer1, "bank_employee", paramBankEmployee);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getBankId(), "bank_id", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getUserName(), "user_name", true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getFirstName(), "first_name", true, true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getLastName(), "last_name", true, true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getEmployeeId(), "bank_employee_id", true, true);
      if ((str2 != null) && (str2.length() != 0)) {
        i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(str2), false);
      }
      if ((str3 != null) && (str3.length() != 0)) {
        i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(str3), false);
      }
      if ((str4 != null) && (str4.length() != 0)) {
        i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(str4), false);
      }
      Profile.setStatementValues(localPreparedStatement, i, paramBankEmployee, "bank_employee", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      jdMethod_for(localConnection, localResultSet, localBankEmployees);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localBankEmployees;
  }
  
  public static String getFilteredBankEmployeesCount(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str1 = "BankEmployeeAdapter.getFilteredBankEmployeesCount";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BankEmployees localBankEmployees = new BankEmployees(Locale.getDefault());
    StringBuffer localStringBuffer1 = new StringBuffer("select count(*) from bank_employee");
    String str2 = null;
    Profile.isInitialized();
    if (paramBankEmployee == null) {
      paramBankEmployee = new BankEmployee(Locale.getDefault());
    }
    try
    {
      String str3 = paramBankEmployee.getStatus();
      String str4 = paramBankEmployee.getSupervisor();
      String str5 = paramBankEmployee.getDefaultAffiliateBankId();
      boolean bool = false;
      bool = Profile.appendSQLSelectString(localStringBuffer1, "bank_id", paramBankEmployee.getBankId(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "user_name", paramBankEmployee.getUserName(), true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "first_name", paramBankEmployee.getFirstName(), true, true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "last_name", paramBankEmployee.getLastName(), true, true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "bank_employee_id", paramBankEmployee.getEmployeeId(), true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "employee_status", str3, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "supervisor", str4, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer1, "affiliate_bank_id", str5, bool);
      ArrayList localArrayList = paramBankEmployee.getAffiliateBankIds();
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        if (bool)
        {
          localStringBuffer1.append(" and (");
        }
        else
        {
          localStringBuffer1.append(" where (");
          bool = true;
        }
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append("(");
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          localStringBuffer2.append(localIterator.next() + ",");
        }
        localStringBuffer2.setCharAt(localStringBuffer2.length() - 1, ')');
        localStringBuffer1.append("employee_id in (");
        localStringBuffer1.append("select EMPLOYEE_ID from AFFILIATE_ASSIGN where AFFILIATE_BANK_ID in ");
        localStringBuffer1.append(localStringBuffer2);
        localStringBuffer1.append(" ) ");
        localStringBuffer1.append("or affiliate_bank_id in " + localStringBuffer2);
        localStringBuffer1.append(") ");
      }
      Profile.appendSQLSelectColumns(localStringBuffer1, "bank_employee", paramBankEmployee);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getBankId(), "bank_id", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getUserName(), "user_name", true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getFirstName(), "first_name", true, true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getLastName(), "last_name", true, true, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getEmployeeId(), "bank_employee_id", true, true);
      if ((str3 != null) && (str3.length() != 0)) {
        i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(str3), false);
      }
      if ((str4 != null) && (str4.length() != 0)) {
        i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(str4), false);
      }
      if ((str5 != null) && (str5.length() != 0)) {
        i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(str5), false);
      }
      Profile.setStatementValues(localPreparedStatement, i, paramBankEmployee, "bank_employee", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next()) {
        str2 = String.valueOf(localResultSet.getInt(1));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return str2;
  }
  
  public static BankEmployees getBankEmployeeSupervisors()
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.getBankEmployeeSupervisors";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BankEmployees localBankEmployees = new BankEmployees(Locale.getDefault());
    StringBuffer localStringBuffer = new StringBuffer("select * from bank_employee where employee_status != 1");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next() == true) {
        if ((localResultSet.getInt("employee_admin_access") & 0x2) != 0)
        {
          BankEmployee localBankEmployee = localBankEmployees.add();
          jdMethod_for(localResultSet, localBankEmployee);
          getBankEmployeeBanks(localConnection, localBankEmployee);
        }
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
    return localBankEmployees;
  }
  
  public static BankEmployees getBankEmployeesForSupervisor(int paramInt)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.getBankEmployeesForSupervisor";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BankEmployees localBankEmployees = new BankEmployees(Locale.getDefault());
    StringBuffer localStringBuffer = new StringBuffer("select * from bank_employee where supervisor=?");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      jdMethod_for(localConnection, localResultSet, localBankEmployees);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localBankEmployees;
  }
  
  private static void jdMethod_for(Connection paramConnection, ResultSet paramResultSet, BankEmployees paramBankEmployees)
    throws Exception
  {
    while (paramResultSet.next() == true)
    {
      BankEmployee localBankEmployee = paramBankEmployees.add();
      jdMethod_for(paramResultSet, localBankEmployee);
      getBankEmployeeBanks(paramConnection, localBankEmployee);
    }
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, BankEmployee paramBankEmployee)
    throws Exception
  {
    paramBankEmployee.setId(String.valueOf(paramResultSet.getInt("employee_id")));
    paramBankEmployee.put("BANK_ID", Profile.getRSString(paramResultSet, "bank_id"));
    paramBankEmployee.setUserName(Profile.getRSString(paramResultSet, "user_name"));
    paramBankEmployee.setEmployeeId(Profile.getRSString(paramResultSet, "bank_employee_id"));
    paramBankEmployee.setFirstName(Profile.getRSString(paramResultSet, "first_name"));
    paramBankEmployee.setLastName(Profile.getRSString(paramResultSet, "last_name"));
    paramBankEmployee.setEmail(Profile.getRSString(paramResultSet, "email_address"));
    paramBankEmployee.setAdminAccess(d(paramResultSet.getInt("employee_admin_access")));
    paramBankEmployee.setPassword(Profile.getRSString(paramResultSet, "user_password"));
    paramBankEmployee.setStreet(Profile.getRSString(paramResultSet, "address1"));
    paramBankEmployee.setStreet2(Profile.getRSString(paramResultSet, "address2"));
    paramBankEmployee.setCity(Profile.getRSString(paramResultSet, "city"));
    paramBankEmployee.setState(Profile.getRSString(paramResultSet, "state"));
    paramBankEmployee.setZipCode(Profile.getRSString(paramResultSet, "zip"));
    paramBankEmployee.setPhone(Profile.getRSString(paramResultSet, "home_phone"));
    paramBankEmployee.setStatus(String.valueOf(paramResultSet.getInt("employee_status")));
    paramBankEmployee.setSupervisor(String.valueOf(paramResultSet.getInt("supervisor")));
    paramBankEmployee.setNotify(String.valueOf(paramResultSet.getInt("employee_notify")));
    paramBankEmployee.setReviewRequired(Profile.getRSString(paramResultSet, "review_required"));
    paramBankEmployee.setOboEnabled(Profile.getRSString(paramResultSet, "obo_enabled"));
    paramBankEmployee.setApprovalProvider(String.valueOf(paramResultSet.getInt("approval_provider")));
    paramBankEmployee.setMsgApprovalProvider(String.valueOf(paramResultSet.getInt("msg_approval_provider")));
    paramBankEmployee.setDefaultAffiliateBankId(String.valueOf(paramResultSet.getInt("affiliate_bank_id")));
    paramBankEmployee.put("PASSWORD_STATUS", new Integer(paramResultSet.getInt("password_status")));
    paramBankEmployee.put("PASSWORD_FAIL_COUNT", new Integer(paramResultSet.getInt("password_fail_count")));
    Timestamp localTimestamp = paramResultSet.getTimestamp("password_lockout_time");
    if (localTimestamp != null) {
      paramBankEmployee.put("PASSWORD_LOCKOUT_TIME", new DateTime(localTimestamp, Locale.getDefault()));
    }
    localTimestamp = paramResultSet.getTimestamp("last_active_date");
    if (localTimestamp != null) {
      paramBankEmployee.put("LAST_ACTIVE_DATE", new DateTime(localTimestamp, Locale.getDefault()));
    }
    Profile.setXBeanFields(paramResultSet, paramBankEmployee, "bank_employee");
  }
  
  public static BankEmployee addBankEmployee(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str1 = "BankEmployeeAdapter.addBankEmployee";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    StringBuffer localStringBuffer = new StringBuffer("insert into bank_employee (employee_id,bank_id,user_name,bank_employee_id,first_name,last_name,email_address,employee_admin_access,user_password,address1,address2,city,state,zip,home_phone,employee_status,supervisor,employee_notify,review_required,obo_enabled,approval_provider,msg_approval_provider,affiliate_bank_id,password_status,password_fail_count");
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLInsertColumns(localStringBuffer, "bank_employee", true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = DBUtil.getNextId(localConnection, Profile.dbType, "employee_id");
      String str2 = String.valueOf(i);
      paramBankEmployee.setId(str2);
      if ((paramBankEmployee.getEmployeeId() == null) || (paramBankEmployee.getEmployeeId().length() == 0)) {
        paramBankEmployee.setEmployeeId(str2);
      }
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, i, false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getBankId(), "bank_id", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getUserName(), "user_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getEmployeeId(), "bank_employee_id", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getFirstName(), "first_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getLastName(), "last_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getEmail(), "email_address", false);
      j = Profile.setStatementInt(localPreparedStatement, j, adminAccessToInt(paramBankEmployee.getAdminAccess()), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getPassword(), "user_password", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getStreet(), "address1", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getStreet2(), "address2", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getCity(), "city", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getState(), "state", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getZipCode(), "zip", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getPhone(), "home_phone", false);
      String str3 = paramBankEmployee.getStatus();
      if (str3 == null) {
        j = Profile.setStatementInt(localPreparedStatement, j, 1, false);
      } else {
        j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(str3), false);
      }
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramBankEmployee.getSupervisor()), false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramBankEmployee.getNotify()), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramBankEmployee.getReviewRequired(), "review_required", false);
      String str4 = paramBankEmployee.getOboEnabled();
      if (str4 == null) {
        str4 = "0";
      }
      j = Profile.setStatementString(localPreparedStatement, j, str4, "obo_enabled", false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramBankEmployee.getApprovalProvider()), false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramBankEmployee.getMsgApprovalProvider()), false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramBankEmployee.getDefaultAffiliateBankId()), false);
      j = Profile.setStatementInt(localPreparedStatement, j, 2, false);
      j = Profile.setStatementInt(localPreparedStatement, j, 0, false);
      Profile.setStatementValues(localPreparedStatement, j, paramBankEmployee, "bank_employee", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      updateBankEmployeeBanks(localConnection, paramBankEmployee);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramBankEmployee;
  }
  
  public static void modifyBankEmployee(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.modifyBankEmployees";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    StringBuffer localStringBuffer = new StringBuffer("update bank_employee set bank_id=?,user_name=?,bank_employee_id=?,first_name=?,last_name=?,email_address=?,employee_admin_access=?,user_password=?,address1=?,address2=?,city=?,state=?,zip=?,home_phone=?,employee_status=?,supervisor=?,employee_notify=?,review_required=?,obo_enabled=?,approval_provider=?,msg_approval_provider=?,affiliate_bank_id=?");
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLUpdateColumns(localStringBuffer, "bank_employee", paramBankEmployee);
      localStringBuffer.append(" where employee_id=?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getBankId(), "bank_id", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getUserName(), "user_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getEmployeeId(), "bank_employee_id", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getFirstName(), "first_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getLastName(), "last_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getEmail(), "email_address", false);
      i = Profile.setStatementInt(localPreparedStatement, i, adminAccessToInt(paramBankEmployee.getAdminAccess()), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getPassword(), "user_password", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getStreet(), "address1", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getStreet2(), "address2", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getCity(), "city", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getState(), "state", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getZipCode(), "zip", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getPhone(), "home_phone", false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getStatus()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getSupervisor()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getNotify()), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getReviewRequired(), "review_required", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getOboEnabled(), "obo_enabled", false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getApprovalProvider()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getMsgApprovalProvider()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getDefaultAffiliateBankId()), false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramBankEmployee, "bank_employee", true);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBankEmployee.getId()), false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      updateBankEmployeeBanks(localConnection, paramBankEmployee);
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
  
  public static void deleteBankEmployee(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.deleteBankEmployee";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    Profile.isInitialized();
    int i = Profile.convertToInt(paramBankEmployee.getId());
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid bank employee", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (jdMethod_try(localConnection, i, 0) == true) {
        Profile.handleError(str, "This bank employee has direct reports", 2);
      }
      ApplicationAdapter.deleteProductAccessTX(localConnection, paramBankEmployee.getBankId(), i, 0);
      MessageQueueAdapter.deleteQueueMemberByEmployeeIdTX(localConnection, i);
      if ((!messageAdapter.hasMailReferencesTX(localConnection, i, 0)) && (!messageAdapter.hasMessageReferencesEmployeeTX(localConnection, i)))
      {
        deleteBankEmployeeBanks(localConnection, paramBankEmployee);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from bank_employee where employee_id = ?");
        Profile.setStatementInt(localPreparedStatement, 1, i, false);
        DBUtil.executeUpdate(localPreparedStatement, "delete from bank_employee where employee_id = ?");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update bank_employee set employee_status=? where employee_id=?");
        int j = 1;
        j = Profile.setStatementInt(localPreparedStatement, j, 1, false);
        j = Profile.setStatementInt(localPreparedStatement, j, i, false);
        DBUtil.executeUpdate(localPreparedStatement, "update bank_employee set employee_status=? where employee_id=?");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void modifySupervisor(int paramInt1, int paramInt2)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.modifySupervisor";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    StringBuffer localStringBuffer = new StringBuffer("update bank_employee set supervisor=? where supervisor=?");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt2);
      localPreparedStatement.setInt(2, paramInt1);
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
  
  public static void modifyPersonalBanker(int paramInt1, int paramInt2)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.modifyPersonalBanker";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    StringBuffer localStringBuffer = new StringBuffer("update customer set personal_banker=? where personal_banker=?");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt2);
      localPreparedStatement.setInt(2, paramInt1);
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
  
  public static int adminAccessToInt(ArrayList paramArrayList)
  {
    int i = 0;
    if (paramArrayList != null) {
      for (int j = 0; j < paramArrayList.size(); j++) {
        if (((String)paramArrayList.get(j)).equalsIgnoreCase("ACCESS_ADMINISTRATOR")) {
          i |= 0x1;
        } else if (((String)paramArrayList.get(j)).equalsIgnoreCase("ACCESS_SUPERVISOR")) {
          i |= 0x2;
        } else if (((String)paramArrayList.get(j)).equalsIgnoreCase("ACCESS_CSR")) {
          i |= 0x4;
        } else if (((String)paramArrayList.get(j)).equalsIgnoreCase("ACCESS_PERSONALBANKER")) {
          i |= 0x8;
        }
      }
    }
    return i;
  }
  
  private static ArrayList d(int paramInt)
  {
    ArrayList localArrayList = new ArrayList(5);
    if ((paramInt & 0x1) != 0) {
      localArrayList.add("ACCESS_ADMINISTRATOR");
    }
    if ((paramInt & 0x2) != 0) {
      localArrayList.add("ACCESS_SUPERVISOR");
    }
    if ((paramInt & 0x4) != 0) {
      localArrayList.add("ACCESS_CSR");
    }
    if ((paramInt & 0x8) != 0) {
      localArrayList.add("ACCESS_PERSONALBANKER");
    }
    return localArrayList;
  }
  
  public static BankEmployee signonBankEmployee(BankEmployee paramBankEmployee)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.signonBankEmployee";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from bank_employee where user_name=? and user_password=? and employee_status != 1");
    Profile.isInitialized();
    ArrayList localArrayList = SignonSettings.getExemptJobTypeIds();
    Timestamp localTimestamp1 = DBUtil.getCurrentTimestamp();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getUserName(), "user_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBankEmployee.getPassword(), "user_password", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      int j;
      int k;
      int n;
      Object localObject1;
      if (localResultSet.next() == true)
      {
        jdMethod_for(localResultSet, paramBankEmployee);
        getBankEmployeeBanks(localConnection, paramBankEmployee);
        j = localResultSet.getInt("password_fail_count");
        k = localResultSet.getInt("password_status");
        Timestamp localTimestamp2 = localResultSet.getTimestamp("password_lockout_time");
        if (CustomerAdapter.isLockedPasswordStatus(k))
        {
          if (localTimestamp1.after(localTimestamp2))
          {
            k = CustomerAdapter.getUnlockedPasswordStatus(k);
            localTimestamp2 = null;
            j = 0;
          }
          else
          {
            throw new ProfileException(11);
          }
        }
        else
        {
          j = 0;
          localTimestamp2 = null;
        }
        n = Integer.parseInt(paramBankEmployee.getId());
        jdMethod_int(localConnection, n, k, j, localTimestamp2);
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select PASSWORD_DATE , PASSWORD from PASSWORD_HISTORY where USER_TYPE = ?  and USER_ID = ? order by PASSWORD_DATE desc");
        Profile.setStatementInt(localPreparedStatement, 1, 2, true);
        Profile.setStatementInt(localPreparedStatement, 2, n, true);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select PASSWORD_DATE , PASSWORD from PASSWORD_HISTORY where USER_TYPE = ?  and USER_ID = ? order by PASSWORD_DATE desc");
        int i1 = 0;
        if (localResultSet.next())
        {
          localObject1 = localResultSet.getTimestamp(1);
          long l = System.currentTimeMillis() - ((Timestamp)localObject1).getTime();
          if (l >= SignonSettings.getPwdExpireTime() * 86400000L) {
            i1 = 1;
          } else if (l >= SignonSettings.getPwdWarningTime() * 86400000L) {
            paramBankEmployee.put("AUTHENTICATE", new Integer(3018));
          }
        }
        if ((k == 2) || (i1 != 0)) {
          paramBankEmployee.put("AUTHENTICATE", new Integer(3007));
        } else if (k == 4) {
          paramBankEmployee.put("AUTHENTICATE", new Integer(3017));
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update bank_employee set last_active_date=? where employee_id=?");
        localPreparedStatement.setTimestamp(1, DBUtil.getCurrentTimestamp());
        localPreparedStatement.setInt(2, n);
        DBUtil.executeUpdate(localPreparedStatement, "update bank_employee set last_active_date=? where employee_id=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      else
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        if (getInfoForAuditing(paramBankEmployee, new HashMap()))
        {
          j = 0;
          k = Integer.parseInt(paramBankEmployee.getId());
          if (!localArrayList.contains(new Integer(paramBankEmployee.getJobTypeId())))
          {
            localPreparedStatement = DBUtil.prepareStatement(localConnection, "select password_status, password_fail_count, password_lockout_time from bank_employee where employee_id=?");
            Profile.setStatementInt(localPreparedStatement, 1, k, true);
            localResultSet = DBUtil.executeQuery(localPreparedStatement, "select password_status, password_fail_count, password_lockout_time from bank_employee where employee_id=?");
            if (localResultSet.next())
            {
              int m = localResultSet.getInt("password_fail_count");
              n = localResultSet.getInt("password_status");
              Timestamp localTimestamp3 = localResultSet.getTimestamp("password_lockout_time");
              if (CustomerAdapter.isLockedPasswordStatus(n))
              {
                if (localTimestamp1.after(localTimestamp3))
                {
                  n = CustomerAdapter.getUnlockedPasswordStatus(n);
                  localObject1 = Calendar.getInstance();
                  ((Calendar)localObject1).add(12, SignonSettings.getLockoutDuration());
                  localTimestamp3 = new Timestamp(((Calendar)localObject1).getTime().getTime());
                  m = 1;
                }
                else
                {
                  throw new ProfileException(11);
                }
              }
              else
              {
                m++;
                if (m >= SignonSettings.getNumLoginsBeforeLockout())
                {
                  n = CustomerAdapter.getLockedPasswordStatus(n);
                  localObject1 = Calendar.getInstance();
                  ((Calendar)localObject1).add(12, SignonSettings.getLockoutDuration());
                  localTimestamp3 = new Timestamp(((Calendar)localObject1).getTime().getTime());
                  j = 1;
                }
                else
                {
                  localTimestamp3 = null;
                }
              }
              jdMethod_int(localConnection, k, n, m, localTimestamp3);
            }
            else
            {
              throw new ProfileException(7);
            }
          }
          if (j != 0) {
            throw new ProfileException(11);
          }
          throw new ProfileException(str, 8, "Bank employee not found: invalid username/password");
        }
        throw new ProfileException(str, 7, "Bank employee not found: invalid username/password");
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
    return paramBankEmployee;
  }
  
  public static BankEmployees getBankEmployeesByIds(ArrayList paramArrayList)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.getBankEmployeeByIds";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BankEmployees localBankEmployees = new BankEmployees(Locale.getDefault());
    if ((paramArrayList == null) || (paramArrayList.size() == 0)) {
      return localBankEmployees;
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer1 = new StringBuffer("select * from bank_employee be");
      StringBuffer localStringBuffer2 = new StringBuffer();
      Iterator localIterator = paramArrayList.iterator();
      Object localObject1;
      while (localIterator.hasNext() == true)
      {
        localObject1 = (String)localIterator.next();
        localStringBuffer2.append((String)localObject1);
        localStringBuffer2.append(",");
      }
      Profile.appendSQLSelectInt(localStringBuffer1, "", "employee_id", localStringBuffer2.toString(), false);
      localStringBuffer1.append("and employee_status != 1");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      Profile.setStatementInt(localPreparedStatement, 1, localStringBuffer2.toString(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next() == true)
      {
        localObject1 = localBankEmployees.add();
        jdMethod_for(localResultSet, (BankEmployee)localObject1);
        getBankEmployeeBanks(localConnection, (BankEmployee)localObject1);
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
    return localBankEmployees;
  }
  
  public static boolean getInfoForAuditing(BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.getInfoForAuditing";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select employee_id from bank_employee where user_name=?");
      Profile.setStatementString(localPreparedStatement, 1, paramBankEmployee.getUserName(), "user_name", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select employee_id from bank_employee where user_name=?");
      if (localResultSet.next() == true)
      {
        paramBankEmployee.setId(Integer.toString(localResultSet.getInt("employee_id")));
        bool = true;
        return bool;
      }
      boolean bool = false;
      return bool;
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return false;
  }
  
  public static boolean hasDirectReports(int paramInt1, int paramInt2)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.hasDirectReports";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool1 = jdMethod_try(localConnection, paramInt1, paramInt2);
      return bool1;
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static boolean jdMethod_try(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    boolean bool = false;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramInt2 > 0) {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select employee_id from bank_employee where ( supervisor=? and affiliate_bank_id=? ) OR employee_id in ( select EMPLOYEE_ID from AFFILIATE_ASSIGN where EMPLOYEE_ID in ( select employee_id from bank_employee where supervisor=? ) and AFFILIATE_BANK_ID=? )");
      } else {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select employee_id from bank_employee where supervisor=?");
      }
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      if (paramInt2 > 0)
      {
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select employee_id from bank_employee where ( supervisor=? and affiliate_bank_id=? ) OR employee_id in ( select EMPLOYEE_ID from AFFILIATE_ASSIGN where EMPLOYEE_ID in ( select employee_id from bank_employee where supervisor=? ) and AFFILIATE_BANK_ID=? )");
      }
      else
      {
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select employee_id from bank_employee where supervisor=?");
      }
      if (localResultSet.next() == true) {
        bool = true;
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return bool;
  }
  
  public static void resetBankEmployeePassword(BankEmployee paramBankEmployee, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.resetBankEmployeePassword";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update bank_employee set user_password=?, password_status=?, password_fail_count=?, password_lockout_time=? where employee_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "user_password", false);
      localPreparedStatement.setInt(i++, 2);
      localPreparedStatement.setInt(i++, 0);
      localPreparedStatement.setTimestamp(i++, null);
      localPreparedStatement.setInt(i++, Integer.parseInt(paramBankEmployee.getId()));
      DBUtil.executeUpdate(localPreparedStatement, "update bank_employee set user_password=?, password_status=?, password_fail_count=?, password_lockout_time=? where employee_id=?");
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
  
  public static void modifyBankEmployeePasswordStatus(BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.modifyBankEmployeePasswordStatus";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      i = Integer.parseInt(paramBankEmployee.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProfileException(4);
    }
    if (!Profile.isValidId(i)) {
      throw new ProfileException(4);
    }
    Integer localInteger = (Integer)paramBankEmployee.get("PASSWORD_STATUS");
    if ((localInteger == null) || (localInteger.intValue() < 0)) {
      throw new ProfileException(3);
    }
    int j = localInteger.intValue();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (j == 0)
      {
        jdMethod_int(localConnection, i, j, 0, null);
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update bank_employee set password_status=? where employee_id=?");
        localPreparedStatement.setInt(1, j);
        localPreparedStatement.setInt(2, i);
        DBUtil.executeUpdate(localPreparedStatement, "update bank_employee set password_status=? where employee_id=?");
        DBUtil.commit(localConnection);
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
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
  
  public static void modifyBankEmployeePassword(BankEmployee paramBankEmployee, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.modifyBankEmployeePassword";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      i = Integer.parseInt(paramBankEmployee.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProfileException(4);
    }
    if (!Profile.isValidId(i)) {
      throw new ProfileException(4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update bank_employee set user_password=? where employee_id=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString, "user_password", false);
      localPreparedStatement.setInt(2, i);
      DBUtil.executeUpdate(localPreparedStatement, "update bank_employee set user_password=? where employee_id=?");
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
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
  
  public static void logFailedLogin(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "BankEmployeeAdapter.logFailedLogin";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select password_status, password_fail_count, password_lockout_time from bank_employee where employee_id=?");
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select password_status, password_fail_count, password_lockout_time from bank_employee where employee_id=?");
      Timestamp localTimestamp1 = DBUtil.getCurrentTimestamp();
      int i = 0;
      if (localResultSet.next())
      {
        int j = localResultSet.getInt("password_status");
        int k = localResultSet.getInt("password_fail_count");
        Timestamp localTimestamp2 = localResultSet.getTimestamp("password_lockout_time");
        Calendar localCalendar;
        if (CustomerAdapter.isLockedPasswordStatus(j))
        {
          if (localTimestamp1.after(localTimestamp2))
          {
            j = CustomerAdapter.getUnlockedPasswordStatus(j);
            localCalendar = Calendar.getInstance();
            localCalendar.add(12, SignonSettings.getLockoutDuration());
            localTimestamp2 = new Timestamp(localCalendar.getTime().getTime());
            k = 1;
          }
          else
          {
            throw new ProfileException(11);
          }
        }
        else
        {
          k++;
          if (k >= SignonSettings.getNumLoginsBeforeLockout())
          {
            j = CustomerAdapter.getLockedPasswordStatus(j);
            localCalendar = Calendar.getInstance();
            localCalendar.add(12, SignonSettings.getLockoutDuration());
            localTimestamp2 = new Timestamp(localCalendar.getTime().getTime());
            i = 1;
          }
          else
          {
            localTimestamp2 = null;
          }
        }
        jdMethod_int(localConnection, paramSecureUser.getProfileID(), j, k, localTimestamp2);
        if (i != 0) {
          throw new ProfileException(11);
        }
      }
      else
      {
        throw new ProfileException(7);
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
  }
  
  private static void jdMethod_int(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, Timestamp paramTimestamp)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update bank_employee set password_status=?,password_fail_count=?,password_lockout_time=? where employee_id=?");
    localPreparedStatement.setInt(1, paramInt2);
    localPreparedStatement.setInt(2, paramInt3);
    localPreparedStatement.setTimestamp(3, paramTimestamp);
    localPreparedStatement.setInt(4, paramInt1);
    DBUtil.executeUpdate(localPreparedStatement, "update bank_employee set password_status=?,password_fail_count=?,password_lockout_time=? where employee_id=?");
    DBUtil.closeStatement(localPreparedStatement);
    localPreparedStatement = null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.BankEmployeeAdapter
 * JD-Core Version:    0.7.0.1
 */