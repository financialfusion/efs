package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class BusinessAdapter
  implements ProfileDefines
{
  private static final String aiH = "insert into customer_directory (directory_id,cust_id,directory_date,last_active_date,create_date,modified_date,account_status";
  private static final String aiE = "insert into business (business_id,bank_id,directory_id,contact_id,business_name,low_business_name,processor_pin,tax_id,business_CIF,personal_CIF,personal_banker,account_rep,approved_by,approval_group,affiliate_bank_id,batch_type,dflt_ppay_decision,PRIM_CONTACT_PERMS,SEC_CONTACT_PERMS, LOCATIONIDPLACE";
  private static final String aiM = "select b.*,c.*,cd.* from business b, contact_info c, customer_directory cd where b.directory_id = cd.directory_id and b.contact_id=c.contact_id and b.directory_id=?";
  private static final String aiF = "select b.directory_id from business b, customer_directory cd where cd.directory_id=b.directory_id and b.bank_id=? and  cd.cust_id=?";
  private static final String aiK = "select b.directory_id from business b where b.bank_id=? and b.business_CIF=?";
  private static final String aiI = "select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id";
  private static final String aiJ = "select count(*) from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id";
  private static final String aiv = "update business set business_name=?,low_business_name=?,processor_pin=?,tax_id=?,business_CIF=?,personal_CIF=?,personal_banker=?,account_rep=?,approved_by=?,approval_group=?,affiliate_bank_id=?,batch_type=?,dflt_ppay_decision=?,PRIM_CONTACT_PERMS=?,SEC_CONTACT_PERMS=?,LOCATIONIDPLACE=?";
  private static final String aiw = " where directory_id=?";
  private static final String aiu = "select * from business_employee where business_id=?";
  private static final String aiz = "select directory_id from business_employee where business_id=?";
  private static final String aix = "delete from business where business_id=?";
  private static final String aiB = "select contact_id from business where business_id=?";
  private static final String aiy = "select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd, business_employee be, customer c where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id and b.business_id=be.business_id and be.directory_id=c.directory_id";
  private static final String aiD = "select count(*) from business b, contact_info ci, customer_directory cd, business_employee be, customer c where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id and b.business_id=be.business_id and be.directory_id=c.directory_id";
  private static final String aiG = "select affiliate_bank_id from business where directory_id = ?";
  private static final String ait = "Select distinct trans_group_name from transaction_groups where directory_id = ? order by trans_group_name";
  private static final String aiL = "Select typecodes from transaction_groups where directory_id = ? and trans_group_name = ? order by seq_num";
  private static final String aiC = "Insert into transaction_groups (directory_id, trans_group_name, seq_num, typecodes) values (?, ?, ?, ?)";
  private static final String aiA = "Delete from transaction_groups where directory_id = ? and trans_group_name = ?";
  
  public static Business addBusiness(Business paramBusiness)
    throws ProfileException
  {
    return addBusiness(paramBusiness, null);
  }
  
  public static Business addBusiness(Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.addBusiness";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str2 = paramBusiness.getStatus();
      int i = 1;
      if (Profile.isValidInt(str2)) {
        i = Profile.convertToInt(str2);
      }
      String str3 = (String)paramBusiness.get("CUST_ID");
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = " ";
        paramBusiness.set("CUST_ID", str3);
      }
      int j = 0;
      if (paramHashMap != null)
      {
        String str4 = (String)paramHashMap.get("CheckCIFUnique");
        if ((str4 != null) && (str4.equals("true"))) {
          j = 1;
        }
      }
      if ((j != 0) && (!isUniqueCIF(localConnection, paramBusiness.getBusinessCIF(), paramBusiness.getBankId(), -1))) {
        Profile.handleError(str1, "Duplicate business CIF", 4025);
      }
      int k = CustomerAdapter.addCustomerDirectoryTX(localConnection, str3, i, paramBusiness);
      paramBusiness.setId(k);
      paramBusiness.put("BUSINESS_ID_STR", String.valueOf(k));
      int m = ContactAdapter.addContactTX(localConnection, paramBusiness);
      paramBusiness.set("CONTACT_ID", String.valueOf(m));
      StringBuffer localStringBuffer = new StringBuffer("insert into business (business_id,bank_id,directory_id,contact_id,business_name,low_business_name,processor_pin,tax_id,business_CIF,personal_CIF,personal_banker,account_rep,approved_by,approval_group,affiliate_bank_id,batch_type,dflt_ppay_decision,PRIM_CONTACT_PERMS,SEC_CONTACT_PERMS, LOCATIONIDPLACE");
      Profile.appendSQLInsertColumns(localStringBuffer, "business", true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int n = 1;
      n = Profile.setStatementInt(localPreparedStatement, n, k, false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getBankId(), "bank_id", false);
      n = Profile.setStatementInt(localPreparedStatement, n, k, false);
      n = Profile.setStatementInt(localPreparedStatement, n, m, false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getBusinessName(), "business_name", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getBusinessNameLowerCase(), "low_business_name", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getPassword(), "processor_pin", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getTaxId(), "tax_id", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getBusinessCIF(), "business_CIF", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getPersonalCIF(), "personal_CIF", false);
      n = Profile.setStatementInt(localPreparedStatement, n, Profile.convertToInt(paramBusiness.getPersonalBanker()), false);
      n = Profile.setStatementInt(localPreparedStatement, n, Profile.convertToInt(paramBusiness.getAccountRep()), false);
      n = Profile.setStatementInt(localPreparedStatement, n, Profile.convertToInt(paramBusiness.getApprovedBy()), false);
      n = Profile.setStatementInt(localPreparedStatement, n, Profile.convertToInt(paramBusiness.getApprovalGroup()), false);
      n = Profile.setStatementInt(localPreparedStatement, n, paramBusiness.getAffiliateBankID(), false);
      n = Profile.setStatementInt(localPreparedStatement, n, paramBusiness.getACHBatchTypeValue(), false);
      n = Profile.setStatementString(localPreparedStatement, n, paramBusiness.getPPayDefaultDecision(), "dflt_ppay_decision", false);
      n = Profile.setStatementInt(localPreparedStatement, n, paramBusiness.getPrimaryContactPermsValue(), false);
      n = Profile.setStatementInt(localPreparedStatement, n, paramBusiness.getSecondaryContactPermsValue(), false);
      n = Profile.setStatementInt(localPreparedStatement, n, paramBusiness.getLocationIdPlacementValue(), false);
      n = Profile.setStatementValues(localPreparedStatement, n, paramBusiness, "business", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramBusiness;
  }
  
  public static Business getBusiness(int paramInt)
    throws ProfileException
  {
    String str = "BusinessAdapter.getBusiness";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Business localBusiness = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select b.*,c.*,cd.* from business b, contact_info c, customer_directory cd where b.directory_id = cd.directory_id and b.contact_id=c.contact_id and b.directory_id=?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select b.*,c.*,cd.* from business b, contact_info c, customer_directory cd where b.directory_id = cd.directory_id and b.contact_id=c.contact_id and b.directory_id=?");
      if (localResultSet.next()) {
        localBusiness = processBusinessRS(localResultSet);
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
    return localBusiness;
  }
  
  public static Businesses getBusinesses()
    throws ProfileException
  {
    return getBusinesses(true, 250);
  }
  
  public static Businesses getBusinesses(boolean paramBoolean)
    throws ProfileException
  {
    return getBusinesses(paramBoolean, 250);
  }
  
  public static Businesses getBusinesses(boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str = "BusinessAdapter.getBusinesses";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Businesses localBusinesses = new Businesses();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
      while (localResultSet.next()) {
        localBusinesses.add(processBusinessRS(localResultSet));
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
    return localBusinesses;
  }
  
  public static Businesses getBusinesses(Business paramBusiness)
    throws ProfileException
  {
    return getBusinesses(paramBusiness, true, 250);
  }
  
  public static Businesses getBusinesses(Business paramBusiness, boolean paramBoolean)
    throws ProfileException
  {
    return getBusinesses(paramBusiness, paramBoolean, 250);
  }
  
  public static Businesses getBusinesses(Business paramBusiness, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.getBusinesses";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Businesses localBusinesses = new Businesses();
    String str2 = paramBusiness.getBankId();
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4022);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool = true;
      localStringBuffer = new StringBuffer("select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
      bool = createGetSQL(localStringBuffer, paramBusiness, bool, false, true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = fillGetStatement(localPreparedStatement, paramBusiness, i, true);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next()) {
        localBusinesses.add(processBusinessRS(localResultSet));
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
    return localBusinesses;
  }
  
  public static Business getBusinesses(Business paramBusiness, DBCookie paramDBCookie, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str = "BusinessAdapter.getBusinesses";
    Profile.isInitialized();
    Business localBusiness = null;
    Object localObject;
    if (!paramDBCookie.isInitialized())
    {
      localObject = paramBusiness.getBankId();
      if ((localObject == null) || (((String)localObject).length() == 0)) {
        Profile.handleError(str, "Invalid Bank Id", 4022);
      }
      try
      {
        Connection localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
        boolean bool = true;
        StringBuffer localStringBuffer = new StringBuffer("select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
        bool = createGetSQL(localStringBuffer, paramBusiness, bool, false, true);
        PreparedStatement localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        int i = 1;
        i = fillGetStatement(localPreparedStatement, paramBusiness, i, true);
        if (paramBoolean) {
          localPreparedStatement.setMaxRows(paramInt);
        }
        paramDBCookie.initialize(Profile.poolName, localConnection, localPreparedStatement, localStringBuffer.toString());
      }
      catch (Exception localException2)
      {
        Profile.handleError(localException2, str);
      }
    }
    if (!paramDBCookie.isOpened()) {
      Profile.handleError(str, "Cookie is closed", 3);
    }
    try
    {
      localObject = paramDBCookie.getResultSet();
      if (((ResultSet)localObject).next())
      {
        localBusiness = processBusinessRS((ResultSet)localObject);
      }
      else
      {
        paramDBCookie.close();
        localBusiness = null;
      }
    }
    catch (Exception localException1)
    {
      Profile.handleError(localException1, str);
    }
    return localBusiness;
  }
  
  public static int getBusinessesCount(Business paramBusiness)
    throws ProfileException
  {
    return getBusinessesCount(paramBusiness, null);
  }
  
  public static int getBusinessesCount(Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.getBusinessesCount";
    Profile.isInitialized();
    boolean bool1 = true;
    if (paramHashMap != null)
    {
      localObject1 = (String)paramHashMap.get("UseLikeComparison");
      if ((localObject1 != null) && (((String)localObject1).equals("false"))) {
        bool1 = false;
      }
    }
    Object localObject1 = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    String str2 = paramBusiness.getBankId();
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4022);
    }
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool2 = true;
      localStringBuffer = new StringBuffer("select count(*) from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
      bool2 = createGetSQL(localStringBuffer, paramBusiness, bool2, true, bool1);
      localPreparedStatement = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer.toString());
      int j = 1;
      j = fillGetStatement(localPreparedStatement, paramBusiness, j, bool1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, (Connection)localObject1, localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  public static Businesses getBusinessesX(Business paramBusiness)
    throws ProfileException
  {
    String str = "BusinessAdapter.getBusinessesX";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Businesses localBusinesses = null;
    StringBuffer localStringBuffer = new StringBuffer("select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLSelectColumns(localStringBuffer, "customer_directory", paramBusiness);
      Profile.appendSQLSelectColumns(localStringBuffer, "contact_info", paramBusiness);
      Profile.appendSQLSelectColumns(localStringBuffer, "business", paramBusiness);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementValues(localPreparedStatement, i, paramBusiness, "customer_directory", true);
      i = Profile.setStatementValues(localPreparedStatement, i, paramBusiness, "contact_info", true);
      i = Profile.setStatementValues(localPreparedStatement, i, paramBusiness, "business", true);
      localPreparedStatement.setMaxRows(250);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localBusinesses = new Businesses();
      while (localResultSet.next()) {
        localBusinesses.add(processBusinessRS(localResultSet));
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
    return localBusinesses;
  }
  
  public static void modifyBusiness(Business paramBusiness)
    throws ProfileException
  {
    modifyBusiness(paramBusiness, null);
  }
  
  public static void modifyBusiness(Business paramBusiness, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.modifyBusiness";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int i = 0;
      if (paramHashMap != null)
      {
        str2 = (String)paramHashMap.get("CheckCIFUnique");
        if ((str2 != null) && (str2.equals("true"))) {
          i = 1;
        }
      }
      if ((i != 0) && (!isUniqueCIF(localConnection, paramBusiness.getBusinessCIF(), paramBusiness.getBankId(), paramBusiness.getIdValue()))) {
        Profile.handleError(str1, "Duplicate business CIF", 4025);
      }
      String str2 = (String)paramBusiness.get("CUST_ID");
      if ((str2 == null) || (str2.trim().length() == 0))
      {
        str2 = " ";
        paramBusiness.set("CUST_ID", str2);
      }
      CustomerAdapter.modifyCustomerDirectoryTX(localConnection, paramBusiness.getIdValue(), (String)paramBusiness.get("CUST_ID"), Profile.convertToInt(paramBusiness.getStatus()), paramBusiness.getActivationDate(), paramBusiness);
      ContactAdapter.modifyContactTX(localConnection, paramBusiness);
      jdMethod_for(localConnection, paramBusiness);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, null);
    }
  }
  
  public static void deactivateBusiness(Business paramBusiness)
    throws ProfileException
  {
    String str = "BusinessAdapter.deactivateBusiness";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      int i = paramBusiness.getIdValue();
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      CustomerAdapter.modifyCustomerDirectoryStatusTX(localConnection, i, 2);
      jdMethod_new(localConnection, i, 2);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void reactivateBusiness(Business paramBusiness)
    throws ProfileException
  {
    String str = "BusinessAdapter.reactivateBusiness";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      int i = paramBusiness.getIdValue();
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      CustomerAdapter.modifyCustomerDirectoryStatusTX(localConnection, i, 1);
      jdMethod_new(localConnection, i, 1);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void deleteBusiness(Business paramBusiness)
    throws ProfileException
  {
    String str = "BusinessAdapter.deleteBusiness";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 1;
    try
    {
      int j = paramBusiness.getIdValue();
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select directory_id from business_employee where business_id=?");
      localPreparedStatement.setInt(1, j);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select directory_id from business_employee where business_id=?");
      while (localResultSet.next()) {
        if (!CustomerAdapter.deleteUserTX(localConnection, localResultSet.getInt("directory_id"))) {
          i = 0;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if ((i != 0) && (CustomerAdapter.isUserDeletable(localConnection, j)))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select contact_id from business where business_id=?");
        localPreparedStatement.setInt(1, j);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select contact_id from business where business_id=?");
        int k = 0;
        if (localResultSet.next()) {
          k = localResultSet.getInt("CONTACT_ID");
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from business where business_id=?");
        localPreparedStatement.setInt(1, j);
        DBUtil.executeUpdate(localPreparedStatement, "delete from business where business_id=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        ContactAdapter.deleteContactTX(localConnection, k);
        CustomerAdapter.deleteUserTX(localConnection, j);
      }
      else
      {
        CustomerAdapter.modifyCustomerDirectoryStatusTX(localConnection, j, 8);
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
  
  private static void jdMethod_new(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from business_employee where business_id=?");
      localPreparedStatement.setInt(1, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from business_employee where business_id=?");
      while (localResultSet.next()) {
        CustomerAdapter.modifyCustomerDirectoryStatusTX(paramConnection, localResultSet.getInt("directory_id"), paramInt2);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, Business paramBusiness)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("update business set business_name=?,low_business_name=?,processor_pin=?,tax_id=?,business_CIF=?,personal_CIF=?,personal_banker=?,account_rep=?,approved_by=?,approval_group=?,affiliate_bank_id=?,batch_type=?,dflt_ppay_decision=?,PRIM_CONTACT_PERMS=?,SEC_CONTACT_PERMS=?,LOCATIONIDPLACE=?");
    try
    {
      Profile.appendSQLUpdateColumns(localStringBuffer, "business", paramBusiness);
      localStringBuffer.append(" where directory_id=?");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getBusinessName(), "business_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getBusinessNameLowerCase(), "low_business_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getPassword(), "processor_pin", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getTaxId(), "tax_id", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getBusinessCIF(), "business_CIF", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getPersonalCIF(), "personal_CIF", false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBusiness.getPersonalBanker()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBusiness.getAccountRep()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBusiness.getApprovedBy()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, Profile.convertToInt(paramBusiness.getApprovalGroup()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusiness.getAffiliateBankID(), false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusiness.getACHBatchTypeValue(), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramBusiness.getPPayDefaultDecision(), "dflt_ppay_decision", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusiness.getPrimaryContactPermsValue(), false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusiness.getSecondaryContactPermsValue(), false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusiness.getLocationIdPlacementValue(), false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramBusiness, "business", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusiness.getIdValue(), false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static Business processBusinessRS(ResultSet paramResultSet)
    throws Exception
  {
    Business localBusiness = new Business(Locale.getDefault());
    localBusiness.setId(paramResultSet.getInt("directory_id"));
    localBusiness.setBankId(Profile.getRSString(paramResultSet, "bank_id"));
    localBusiness.setBusinessName(Profile.getRSString(paramResultSet, "business_name"));
    localBusiness.setPassword(Profile.getRSString(paramResultSet, "processor_pin"));
    localBusiness.setTaxId(Profile.getRSString(paramResultSet, "tax_id"));
    localBusiness.setBusinessCIF(Profile.getRSString(paramResultSet, "business_CIF"));
    localBusiness.setPersonalCIF(Profile.getRSString(paramResultSet, "personal_CIF"));
    localBusiness.setPersonalBanker(String.valueOf(paramResultSet.getInt("personal_banker")));
    localBusiness.setAccountRep(String.valueOf(paramResultSet.getInt("account_rep")));
    localBusiness.setApprovedBy(String.valueOf(paramResultSet.getInt("approved_by")));
    localBusiness.setApprovalGroup(String.valueOf(paramResultSet.getInt("approval_group")));
    localBusiness.setAffiliateBankID(paramResultSet.getInt("affiliate_bank_id"));
    localBusiness.setACHBatchType(paramResultSet.getInt("batch_type"));
    localBusiness.setPPayDefaultDecision(Profile.getRSString(paramResultSet, "dflt_ppay_decision"));
    localBusiness.setPrimaryContactPerms(paramResultSet.getInt("PRIM_CONTACT_PERMS"));
    localBusiness.setSecondaryContactPerms(paramResultSet.getInt("SEC_CONTACT_PERMS"));
    localBusiness.setLocationIdPlacement(paramResultSet.getInt("LOCATIONIDPLACE"));
    ContactAdapter.setContactTX(paramResultSet, localBusiness);
    localBusiness.setStatus(String.valueOf(paramResultSet.getInt("account_status")));
    localBusiness.put("CUST_ID", Profile.getRSString(paramResultSet, "cust_id"));
    localBusiness.put("CREATE_DATE", paramResultSet.getTimestamp("create_date"));
    localBusiness.put("MODIFIED_DATE", paramResultSet.getTimestamp("modified_date"));
    localBusiness.put("LAST_ACTIVE_DATE", paramResultSet.getTimestamp("last_active_date"));
    Locale localLocale = Locale.getDefault();
    Timestamp localTimestamp = paramResultSet.getTimestamp("activation_date");
    if (localTimestamp != null) {
      localBusiness.setActivationDate(new DateTime(localTimestamp, localLocale));
    }
    Profile.setXBeanFields(paramResultSet, localBusiness, "business");
    Profile.setXBeanFields(paramResultSet, localBusiness, "contact_info");
    Profile.setXBeanFields(paramResultSet, localBusiness, "customer_directory");
    return localBusiness;
  }
  
  public static Businesses getFilteredBusinesses(Business paramBusiness, BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    return getFilteredBusinesses(paramBusiness, paramBusinessEmployee, true, 250);
  }
  
  public static Businesses getFilteredBusinesses(Business paramBusiness, BusinessEmployee paramBusinessEmployee, boolean paramBoolean)
    throws ProfileException
  {
    return getFilteredBusinesses(paramBusiness, paramBusinessEmployee, paramBoolean, 250);
  }
  
  public static Businesses getFilteredBusinesses(Business paramBusiness, BusinessEmployee paramBusinessEmployee, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.getFilteredBusinesses";
    Profile.isInitialized();
    if (paramBusinessEmployee == null) {
      return getBusinesses(paramBusiness, paramBoolean, paramInt);
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = null;
    Businesses localBusinesses = new Businesses();
    String str2 = paramBusiness.getBankId();
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4022);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool = true;
      localStringBuffer = new StringBuffer("select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd, business_employee be, customer c where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id and b.business_id=be.business_id and be.directory_id=c.directory_id");
      bool = createGetSQLByEmployee(localStringBuffer, paramBusinessEmployee, bool);
      bool = createGetSQL(localStringBuffer, paramBusiness, bool, false, true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = fillGetStmtByEmployee(localPreparedStatement, paramBusinessEmployee, i);
      i = fillGetStatement(localPreparedStatement, paramBusiness, i, true);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next()) {
        localBusinesses.add(processBusinessRS(localResultSet));
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
    return localBusinesses;
  }
  
  public static int getFilteredBusinessesCount(Business paramBusiness, BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    return getFilteredBusinessesCount(paramBusiness, paramBusinessEmployee, null);
  }
  
  public static int getFilteredBusinessesCount(Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.getFilteredBusinesses";
    Profile.isInitialized();
    if (paramBusinessEmployee == null) {
      return getBusinessesCount(paramBusiness, paramHashMap);
    }
    boolean bool1 = true;
    if (paramHashMap != null)
    {
      localObject1 = (String)paramHashMap.get("UseLikeComparison");
      if ((localObject1 != null) && (((String)localObject1).equals("false"))) {
        bool1 = false;
      }
    }
    Object localObject1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = null;
    int i = 0;
    String str2 = paramBusiness.getBankId();
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4022);
    }
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool2 = true;
      localStringBuffer = new StringBuffer("select count(*) from business b, contact_info ci, customer_directory cd, business_employee be, customer c where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id and b.business_id=be.business_id and be.directory_id=c.directory_id");
      bool2 = createGetSQLByEmployee(localStringBuffer, paramBusinessEmployee, bool2);
      bool2 = createGetSQL(localStringBuffer, paramBusiness, bool2, true, bool1);
      localPreparedStatement = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer.toString());
      int j = 1;
      j = fillGetStmtByEmployee(localPreparedStatement, paramBusinessEmployee, j);
      j = fillGetStatement(localPreparedStatement, paramBusiness, j, bool1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, (Connection)localObject1, localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  public static Businesses getBusinessesByIds(ArrayList paramArrayList)
    throws ProfileException
  {
    return getBusinessesByIds(paramArrayList, true, 250);
  }
  
  public static Businesses getBusinessesByIds(ArrayList paramArrayList, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.getBusinessesByIds";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Businesses localBusinesses = new Businesses();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer1 = new StringBuffer("select b.*,ci.*,cd.* from business b, contact_info ci, customer_directory cd where b.directory_id=cd.directory_id and b.contact_id=ci.contact_id");
      StringBuffer localStringBuffer2 = new StringBuffer();
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localStringBuffer2.append(str2);
        localStringBuffer2.append(",");
      }
      Profile.appendSQLSelectInt(localStringBuffer1, "b.", "directory_id", localStringBuffer2.toString(), true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      Profile.setStatementInt(localPreparedStatement, 1, localStringBuffer2.toString(), true);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next())
      {
        int i = localResultSet.getInt("account_status");
        if (i != 8) {
          localBusinesses.add(processBusinessRS(localResultSet));
        }
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
    return localBusinesses;
  }
  
  protected static boolean createGetSQLByEmployee(StringBuffer paramStringBuffer, BusinessEmployee paramBusinessEmployee, boolean paramBoolean)
    throws Exception
  {
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "be.", "directory_id", Profile.convertToInt(paramBusinessEmployee.getId()), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "be.", "business_id", paramBusinessEmployee.getBusinessId(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "be.", "primary_user", paramBusinessEmployee.getPrimaryUser(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "bank_id", (String)paramBusinessEmployee.get("BANK_ID"), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "low_first_name", paramBusinessEmployee.getFirstNameLowerCase(), true, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "low_middle_name", paramBusinessEmployee.getMiddleNameLowerCase(), true, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "low_last_name", paramBusinessEmployee.getLastNameLowerCase(), true, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "user_name", paramBusinessEmployee.getUserName(), true, true, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "cd.", "cust_id", paramBusinessEmployee.getCustId(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "password", paramBusinessEmployee.getPassword(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "processor_pin", (String)paramBusinessEmployee.get("PROCESSOR_PIN"), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "ssn", paramBusinessEmployee.getSSN(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "address1", paramBusinessEmployee.getStreet(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "address2", paramBusinessEmployee.getStreet2(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "city", paramBusinessEmployee.getCity(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "state", paramBusinessEmployee.getState(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "zip", paramBusinessEmployee.getZipCode(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "country", paramBusinessEmployee.getCountry(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "home_phone", paramBusinessEmployee.getPhone(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "work_phone", paramBusinessEmployee.getPhone2(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString(paramStringBuffer, "c.", "email_address", paramBusinessEmployee.getEmail(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "c.", "birth_date", Profile.convertToDate(paramBusinessEmployee.get("BIRTH_DATE")), "=", paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "c.", "personal_banker", paramBusinessEmployee.getPersonalBanker(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt(paramStringBuffer, "c.", "password_status", (String)paramBusinessEmployee.get("PASSWORD_STATUS"), paramBoolean);
    Date localDate1 = Profile.convertToDate(paramBusinessEmployee.get("FROM_DATE"));
    Date localDate2 = Profile.convertToDate(paramBusinessEmployee.get("TO_DATE"));
    paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "cd.", "modified_date", localDate1, ">=", paramBoolean);
    paramBoolean = Profile.appendSQLSelectDate(paramStringBuffer, "cd.", "modified_date", localDate2, "<=", paramBoolean);
    Profile.appendSQLSelectColumns(paramStringBuffer, "customer", paramBusinessEmployee);
    return paramBoolean;
  }
  
  protected static boolean createGetSQL(StringBuffer paramStringBuffer, Business paramBusiness, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "b.", "low_business_name", paramBusiness.getBusinessNameLowerCase(), paramBoolean3, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "b.", "bank_id", paramBusiness.getBankId(), false, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "b.", "tax_id", paramBusiness.getTaxId(), true, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "cd.", "cust_id", paramBusiness.getCustId(), false, false, paramBoolean1);
    String str = paramBusiness.getStatus();
    int i = paramBusiness.getStatusValue();
    if ((paramBusiness == null) || (!isAccountStatusSpecified(paramBusiness)))
    {
      if (paramBoolean2)
      {
        if (paramBoolean1)
        {
          paramStringBuffer.append(" and ");
        }
        else
        {
          paramStringBuffer.append(" where ");
          paramBoolean1 = true;
        }
        paramStringBuffer.append("cd.account_status != 8");
      }
    }
    else if (i != -1) {
      paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "account_status", paramBusiness.getStatus(), paramBoolean1);
    }
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "b.", "business_CIF", paramBusiness.getBusinessCIF(), true, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "b.", "affiliate_bank_id", paramBusiness.getAffiliateBankID(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "b.", "personal_banker", paramBusiness.getPersonalBanker(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "b.", "account_rep", paramBusiness.getAccountRep(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "b.", "approved_by", paramBusiness.getApprovedBy(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "b.", "LOCATIONIDPLACE", paramBusiness.getLocationIdPlacementValue(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "b.", "approval_group", paramBusiness.getApprovalGroup(), paramBoolean1);
    Profile.appendSQLSelectColumns(paramStringBuffer, "customer_directory", paramBusiness);
    Profile.appendSQLSelectColumns(paramStringBuffer, "contact_info", paramBusiness);
    Profile.appendSQLSelectColumns(paramStringBuffer, "business", paramBusiness);
    return paramBoolean1;
  }
  
  protected static boolean createGetSQLByEmployee2(StringBuffer paramStringBuffer, BusinessEmployee paramBusinessEmployee, boolean paramBoolean)
    throws Exception
  {
    paramBoolean = Profile.appendSQLSelectInt2(paramStringBuffer, "be.", "directory_id", Profile.convertToInt(paramBusinessEmployee.getId()), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt2(paramStringBuffer, "be.", "business_id", paramBusinessEmployee.getBusinessId(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "be.", "primary_user", paramBusinessEmployee.getPrimaryUser(), false, false, paramBoolean);
    int i = paramBusinessEmployee.getAffiliateBankID();
    if (i > 0) {
      paramBoolean = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "affiliate_bank_id", paramBusinessEmployee.getAffiliateBankID(), paramBoolean);
    }
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "bank_id", (String)paramBusinessEmployee.get("BANK_ID"), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "low_first_name", paramBusinessEmployee.getFirstNameLowerCase(), true, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "low_middle_name", paramBusinessEmployee.getMiddleNameLowerCase(), true, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "low_last_name", paramBusinessEmployee.getLastNameLowerCase(), true, false, paramBoolean);
    if (paramBusinessEmployee.getUserName() != null) {
      paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "user_name", paramBusinessEmployee.getUserName().toLowerCase(), true, true, paramBoolean);
    }
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "cd.", "cust_id", paramBusinessEmployee.getCustId(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "password", paramBusinessEmployee.getPassword(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "processor_pin", (String)paramBusinessEmployee.get("PROCESSOR_PIN"), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "ssn", paramBusinessEmployee.getSSN(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "address1", paramBusinessEmployee.getStreet(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "address2", paramBusinessEmployee.getStreet2(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "city", paramBusinessEmployee.getCity(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "state", paramBusinessEmployee.getState(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "zip", paramBusinessEmployee.getZipCode(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "country", paramBusinessEmployee.getCountry(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "home_phone", paramBusinessEmployee.getPhone(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "work_phone", paramBusinessEmployee.getPhone2(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "email_address", paramBusinessEmployee.getEmail(), false, false, paramBoolean);
    paramBoolean = Profile.appendSQLSelectDate2(paramStringBuffer, "c.", "birth_date", Profile.convertToDate(paramBusinessEmployee.get("BIRTH_DATE")), "=", paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt2(paramStringBuffer, "c.", "personal_banker", paramBusinessEmployee.getPersonalBanker(), paramBoolean);
    paramBoolean = Profile.appendSQLSelectInt2(paramStringBuffer, "c.", "password_status", (String)paramBusinessEmployee.get("PASSWORD_STATUS"), paramBoolean);
    Date localDate1 = Profile.convertToDate(paramBusinessEmployee.get("FROM_DATE"));
    Date localDate2 = Profile.convertToDate(paramBusinessEmployee.get("TO_DATE"));
    paramBoolean = Profile.appendSQLSelectDate2(paramStringBuffer, "cd.", "modified_date", localDate1, ">=", paramBoolean);
    paramBoolean = Profile.appendSQLSelectDate2(paramStringBuffer, "cd.", "modified_date", localDate2, "<=", paramBoolean);
    paramBoolean = Profile.appendSQLSelectColumns2(paramStringBuffer, "customer", paramBusinessEmployee, paramBoolean);
    return paramBoolean;
  }
  
  protected static boolean createGetSQL2(StringBuffer paramStringBuffer, Business paramBusiness, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "b.", "low_business_name", paramBusiness.getBusinessNameLowerCase(), true, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "b.", "bank_id", paramBusiness.getBankId(), false, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "b.", "tax_id", paramBusiness.getTaxId(), true, false, paramBoolean1);
    String str = paramBusiness.getStatus();
    int i = paramBusiness.getStatusValue();
    if (!isAccountStatusSpecified(paramBusiness))
    {
      if (paramBoolean2)
      {
        if (paramBoolean1)
        {
          paramStringBuffer.append(" and ");
        }
        else
        {
          paramStringBuffer.append(" where ");
          paramBoolean1 = true;
        }
        paramStringBuffer.append("cd.account_status != 8");
      }
    }
    else if (i != -1) {
      paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "account_status", paramBusiness.getStatus(), paramBoolean1);
    }
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "b.", "business_CIF", paramBusiness.getBusinessCIF(), false, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "affiliate_bank_id", paramBusiness.getAffiliateBankID(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "personal_banker", paramBusiness.getPersonalBanker(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "account_rep", paramBusiness.getAccountRep(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "approved_by", paramBusiness.getApprovedBy(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "LOCATIONIDPLACE", paramBusiness.getLocationIdPlacementValue(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "b.", "approval_group", paramBusiness.getApprovalGroup(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectColumns2(paramStringBuffer, "customer_directory", paramBusiness, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectColumns2(paramStringBuffer, "contact_info", paramBusiness, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectColumns2(paramStringBuffer, "business", paramBusiness, paramBoolean1);
    return paramBoolean1;
  }
  
  protected static int fillGetStmtByEmployee(PreparedStatement paramPreparedStatement, BusinessEmployee paramBusinessEmployee, int paramInt)
    throws Exception
  {
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Profile.convertToInt(paramBusinessEmployee.getId()), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusinessEmployee.getBusinessId(), true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getPrimaryUser(), "primary_user", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, (String)paramBusinessEmployee.get("BANK_ID"), "bank_id", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getFirstName(), "first_name", true, true, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getMiddleName(), "middle_name", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getLastName(), "last_name", true, true, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getUserName(), "user_name", true, true, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getCustId(), "cust_id", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getPassword(), "password", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, (String)paramBusinessEmployee.get("PROCESSOR_PIN"), "processor_pin", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getSSN(), "ssn", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getStreet(), "address1", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getStreet2(), "address2", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getCity(), "city", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getState(), "state", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getZipCode(), "zip", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getCountry(), "country", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getPhone(), "home_phone", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getPhone2(), "work_phone", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusinessEmployee.getEmail(), "email_address", true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, Profile.convertToDate(paramBusinessEmployee.get("BIRTH_DATE")), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusinessEmployee.getPersonalBanker(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, (String)paramBusinessEmployee.get("PASSWORD_STATUS"), true);
    Date localDate1 = Profile.convertToDate(paramBusinessEmployee.get("FROM_DATE"));
    Date localDate2 = Profile.convertToDate(paramBusinessEmployee.get("TO_DATE"));
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, localDate1, true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, Profile.getNextDay(localDate2), true);
    paramInt = Profile.setStatementValues(paramPreparedStatement, paramInt, paramBusinessEmployee, "customer", true);
    return paramInt;
  }
  
  protected static int fillGetStatement(PreparedStatement paramPreparedStatement, Business paramBusiness, int paramInt, boolean paramBoolean)
    throws Exception
  {
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusiness.getBusinessNameLowerCase(), "low_business_name", paramBoolean, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusiness.getBankId(), "bank_id", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusiness.getTaxId(), "tax_id", true, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusiness.getCustId(), "cust_id", true);
    String str = paramBusiness.getStatus();
    int i = paramBusiness.getStatusValue();
    if (i != -1) {
      paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, str, true);
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramBusiness.getBusinessCIF(), "business_CIF", true, false, true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusiness.getAffiliateBankID(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusiness.getPersonalBanker(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusiness.getAccountRep(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusiness.getApprovedBy(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusiness.getLocationIdPlacementValue(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramBusiness.getApprovalGroup(), true);
    paramInt = Profile.setStatementValues(paramPreparedStatement, paramInt, paramBusiness, "customer_directory", true);
    paramInt = Profile.setStatementValues(paramPreparedStatement, paramInt, paramBusiness, "contact_info", true);
    paramInt = Profile.setStatementValues(paramPreparedStatement, paramInt, paramBusiness, "business", true);
    return paramInt;
  }
  
  protected static boolean isAccountStatusSpecified(Business paramBusiness)
  {
    if (paramBusiness == null) {
      return false;
    }
    String str = paramBusiness.getStatus();
    return (str != null) && (str.length() != 0);
  }
  
  protected static boolean isUniqueCIF(Connection paramConnection, String paramString1, String paramString2, int paramInt)
    throws ProfileException
  {
    String str = "BusinessAdapter.isUniqueCIF";
    boolean bool = true;
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select b.directory_id from business b where b.bank_id=? and b.business_CIF=?");
      if (paramInt != -1) {
        localStringBuffer.append(" and b.directory_id != ?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramString2);
      localPreparedStatement.setString(2, paramString1);
      if (paramInt != -1) {
        localPreparedStatement.setInt(3, paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        int i = localResultSet.getInt(1);
        if (i != paramInt) {
          bool = false;
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return bool;
  }
  
  public static boolean uniqueCustId(Business paramBusiness)
    throws ProfileException
  {
    String str1 = (String)paramBusiness.get("CUST_ID");
    String str2 = paramBusiness.getBankId();
    int i = paramBusiness.getIdValue();
    if (i <= 0) {
      i = -1;
    }
    String str3 = "BusinessAdapter.uniqueCustId";
    boolean bool = true;
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      StringBuffer localStringBuffer = new StringBuffer("select b.directory_id from business b, customer_directory cd where cd.directory_id=b.directory_id and b.bank_id=? and  cd.cust_id=?");
      if (i != -1) {
        localStringBuffer.append(" and b.directory_id != " + i);
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, str1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        int j = localResultSet.getInt(1);
        if (j != i) {
          bool = false;
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str3);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return bool;
  }
  
  public static ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "BusinessAdapter.getBankIdsByServicesPackage";
    DBCookie localDBCookie = null;
    EntitlementGroup localEntitlementGroup = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = 0;
    int j = 0;
    int k = -1;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBCookie = new DBCookie();
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select affiliate_bank_id from business where directory_id = ?");
      for (localEntitlementGroup = Entitlements.getChildrenByGroupType(paramInt, "BusinessAdmin", localDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenByGroupType(paramInt, "BusinessAdmin", localDBCookie))
      {
        String str2 = ((EntitlementGroupMember)Entitlements.getMembers(localEntitlementGroup.getGroupId()).get(0)).getId();
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (Exception localException1)
        {
          i = 0;
        }
        if (i > 0) {
          try
          {
            int m = 1;
            m = Profile.setStatementInt(localPreparedStatement, m, i, true);
            localResultSet = DBUtil.executeQuery(localPreparedStatement, "select affiliate_bank_id from business where directory_id = ?");
            if (localResultSet.next())
            {
              j = localResultSet.getInt(1);
              if (k != j)
              {
                String str3 = String.valueOf(j);
                if (!localArrayList.contains(str3)) {
                  localArrayList.add(str3);
                }
                k = j;
              }
            }
          }
          finally
          {
            DBUtil.closeResultSet(localResultSet);
          }
        }
      }
    }
    catch (Exception localException2)
    {
      Profile.handleError(localException2, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
      if (localDBCookie != null) {
        localDBCookie.close();
      }
    }
    return localArrayList;
  }
  
  public static ArrayList getTransactionGroups(int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "com.ffusion.efs.adapters.profile.BusinessAdapter.getTransactionGroups";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "Select distinct trans_group_name from transaction_groups where directory_id = ? order by trans_group_name");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "Select distinct trans_group_name from transaction_groups where directory_id = ? order by trans_group_name");
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        localArrayList.add(str2);
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
    return localArrayList;
  }
  
  public static String getTypeCodesForGroup(int paramInt, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "com.ffusion.efs.adapters.profile.BusinessAdapter.getTypeCodesForGroup";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "Select typecodes from transaction_groups where directory_id = ? and trans_group_name = ? order by seq_num");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "Select typecodes from transaction_groups where directory_id = ? and trans_group_name = ? order by seq_num");
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        localStringBuffer.append(str2);
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
    return localStringBuffer.toString();
  }
  
  public static void addTransactionGroup(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "com.ffusion.efs.adapters.profile.BusinessAdapter.addTransactionGroup";
    ArrayList localArrayList = getTransactionGroups(paramInt, paramHashMap);
    if (localArrayList.contains(paramString1))
    {
      localObject1 = new StringBuffer();
      ((StringBuffer)localObject1).append("Transaction Group '");
      ((StringBuffer)localObject1).append(paramString1);
      ((StringBuffer)localObject1).append("' already exists for business id ");
      ((StringBuffer)localObject1).append(paramInt);
      ((StringBuffer)localObject1).append(" so it cannot be added.");
      throw new ProfileException(str, 4024, ((StringBuffer)localObject1).toString());
    }
    Profile.isInitialized();
    Object localObject1 = null;
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_int((Connection)localObject1, paramInt, paramString1, paramString2);
      DBUtil.commit((Connection)localObject1);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, (Connection)localObject1);
    }
  }
  
  public static void deleteTransactionGroup(int paramInt, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "com.ffusion.efs.adapters.profile.BusinessAdapter.deleteTransactionGroup";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_try(localConnection, paramInt, paramString);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void modifyTransactionGroup(int paramInt, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "com.ffusion.efs.adapters.profile.BusinessAdapter.modifyTransactionGroup";
    ArrayList localArrayList = getTransactionGroups(paramInt, paramHashMap);
    if ((!paramString1.equals(paramString2)) && (localArrayList.contains(paramString2)))
    {
      localObject1 = new StringBuffer();
      ((StringBuffer)localObject1).append("A Transaction Group named '");
      ((StringBuffer)localObject1).append(paramString2);
      ((StringBuffer)localObject1).append("' already exists for business id ");
      ((StringBuffer)localObject1).append(paramInt);
      ((StringBuffer)localObject1).append(" so we cannot change the name of Transaction Group '");
      ((StringBuffer)localObject1).append(paramString1);
      ((StringBuffer)localObject1).append("' as specified.");
      throw new ProfileException(str, 4024, ((StringBuffer)localObject1).toString());
    }
    Profile.isInitialized();
    Object localObject1 = null;
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_try((Connection)localObject1, paramInt, paramString1);
      jdMethod_int((Connection)localObject1, paramInt, paramString2, paramString3);
      DBUtil.commit((Connection)localObject1);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, (Connection)localObject1);
    }
  }
  
  private static void jdMethod_int(Connection paramConnection, int paramInt, String paramString1, String paramString2)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramString2.length())
    {
      int j;
      if (paramString2.length() - i > 1024) {
        j = 1024;
      } else {
        j = paramString2.length() - i;
      }
      String str1 = paramString2.substring(i, i + j);
      localArrayList.add(str1);
      i += j;
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "Insert into transaction_groups (directory_id, trans_group_name, seq_num, typecodes) values (?, ?, ?, ?)");
      for (int k = 0; k < localArrayList.size(); k++)
      {
        localPreparedStatement.setInt(1, paramInt);
        localPreparedStatement.setString(2, paramString1);
        localPreparedStatement.setInt(3, k);
        String str2 = (String)localArrayList.get(k);
        localPreparedStatement.setString(4, str2);
        DBUtil.executeUpdate(localPreparedStatement, "Insert into transaction_groups (directory_id, trans_group_name, seq_num, typecodes) values (?, ?, ?, ?)");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_try(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    String str = "com.ffusion.efs.adapters.profile.BusinessAdapter.deleteTransactionGroup";
    Profile.isInitialized();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "Delete from transaction_groups where directory_id = ? and trans_group_name = ?");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "Delete from transaction_groups where directory_id = ? and trans_group_name = ?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.BusinessAdapter
 * JD-Core Version:    0.7.0.1
 */