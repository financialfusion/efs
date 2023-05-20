package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class CustomerAdapter
  implements ProfileDefines
{
  protected static MessageAdapter messageAdapter = new MessageAdapter();
  static final int af5 = 1;
  static final int agh = 2;
  public static final int MAX_PASSWORD_SIZE = 50;
  public static final int NO_PWD_CHANGE = 0;
  public static final int PWD_CHANGE_OK = 1;
  public static final int PWD_CHANGE_BAD = 2;
  public static final int PWD_CHANGE_TOO_SOON = 3;
  public static final int PWD_CHANGE_IN_HISTORY = 4;
  private static final String af9 = "select c.directory_id,affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2,cd.last_active_date, cd.create_date from customer c, customer_directory cd where c.directory_id=cd.directory_id and bank_id=? and user_name=? and account_status=1";
  private static final String afH = "select c.directory_id,b.affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,c.processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2 from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.bank_id=? and c.user_name=? and cd.account_status=1 and cd.cust_id = ?";
  private static final String afF = "select cust_id, last_active_date, account_status, create_date from customer_directory where directory_id = ?";
  private static final String afR = "SELECT PRIMARY_USER_ID FROM CUSTOMER_REL where DIRECTORY_ID = ?";
  private static final String afy = "SELECT cd.cust_id, c.processor_pin FROM customer_directory cd, customer c where c.directory_id = cd.directory_id and cd.directory_id = ?";
  private static final String agq = "INSERT INTO CUSTOMER_REL (DIRECTORY_ID, PRIMARY_USER_ID ) VALUES (?, ?)";
  private static final String afW = "select bpw_fi_id, currency_code from affiliate_bank where affiliate_bank_id=?";
  private static final String agu = "update customer set password_status=?,password_fail_count=?,password_lockout_time=? where directory_id=?";
  private static final String agl = "select directory_id from customer where user_name=?";
  private static final String afT = "select c.directory_id from customer c where c.customer_type=? and c.user_name=?";
  private static final String afS = "select c.directory_id from customer c, customer_directory cd, business b, business_employee be where c.directory_id = be.directory_id and be.business_id = b.business_id and b.directory_id=cd.directory_id and c.user_name=? and cd.cust_id=?";
  private static final String agj = "insert into customer_directory (directory_id,cust_id,account_status,last_active_date,create_date,modified_date,directory_date";
  private static final String af3 = "insert into customer (directory_id,bank_id,first_name,middle_name,last_name,low_first_name,low_middle_name,low_last_name,user_name,password,processor_pin,ssn,address1,address2,city,state,zip,country,home_phone,work_phone,email_address,mothers_maiden,birth_date,timeout,pwd_reminder,PWD_REMINDER2,password_status,personal_banker,gender,title,greeting,greeting_type,service_level,reminder,REMINDER2,prefer_contact,data_phone,fax_phone,affiliate_bank_id,customer_type,preferred_lang,TERMS_ACCEPTED,TERMS_ACCEPTED_DATE,USER_TYPE";
  private static final String afr = "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.DIRECTORY_ID and cr.PRIMARY_USER_ID=?";
  private static final String agf = "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.PRIMARY_USER_ID and cr.DIRECTORY_ID = ?";
  private static final String af4 = "select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id";
  private static final String agg = "select cd.* from customer_directory cd where directory_id=?";
  private static final String agk = "select c.*,cd.* from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id";
  private static final String afZ = "select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id";
  private static final String afO = "select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id and user_name = ?";
  private static final String af7 = "select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.customer_type='2'";
  private static final String afL = "select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.customer_type='2'";
  private static final String afA = "select c.*,cd.*,be.* from customer c, customer_directory cd, business_employee be, business b where c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id";
  private static final String agt = "select be.directory_id, cd.account_status from customer c, customer_directory cd, business_employee be, business b where c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id";
  private static final String af1 = "select be.directory_id, cd.account_status from business_employee be, business b, customer_directory cd where be.business_id=b.business_id and be.directory_id=cd.directory_id and b.business_name = ?";
  private static final String agm = "select c.*,cd.* from customer c, customer_directory cd where c.customer_type='2' and c.directory_id=cd.directory_id";
  private static final String afP = "select count(*) from customer c, customer_directory cd where c.directory_id=cd.directory_id";
  private static final String agw = "select count(*) from customer c, customer_directory cdwhere c.customer_type='1' and c.directory_id=cd.directory_id";
  private static final String afo = "select count(*) from customer c, customer_directory cd where c.customer_type='2' and c.directory_id=cd.directory_id";
  private static final String ags = "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?";
  private static final String agn = "select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id";
  private static final String afq = "select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status,be.business_id from customer c, customer_directory cd, business_employee be where c.customer_type='1' and c.directory_id=cd.directory_id";
  private static final String af0 = "select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status,home_phone,work_phone from customer c, customer_directory cd";
  private static final String afp = " and c.directory_id=cd.directory_id order by low_last_name";
  private static final String age = "select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status,home_phone,work_phone,a.account_id from customer c, customer_directory cd, accounts a";
  private static final String afY = " and c.directory_id=cd.directory_id and a.directory_id=c.directory_id order by low_last_name";
  private static final String afQ = "delete from business_employee where directory_id=?";
  private static final String afz = "delete from customer where directory_id=?";
  private static final String af2 = "delete from customer_directory where directory_id=?";
  private static final String afv = "update customer set first_name=?,middle_name=?,last_name=?,low_first_name=?,low_middle_name=?,low_last_name=?,user_name=?,processor_pin=?,ssn=?,address1=?,address2=?,city=?,state=?,zip=?,country=?,home_phone=?,work_phone=?,email_address=?,mothers_maiden=?,birth_date=?,timeout=?,pwd_reminder=?,PWD_REMINDER2=?,password_status=?,password_fail_count=?,password_lockout_time=?,personal_banker=?,gender=?,title=?,greeting=?,greeting_type=?,service_level=?,reminder=?,REMINDER2=?,password=?,prefer_contact=?,data_phone=?,fax_phone=?,affiliate_bank_id=?,LASTVIEWINTRATRANS=?,preferred_lang=?,TERMS_ACCEPTED=?,TERMS_ACCEPTED_DATE=?";
  private static final String agy = " where directory_id = ?";
  private static final String afN = "update customer_directory set modified_date=?,cust_id=?,account_status=?,activation_date=? where directory_id=?";
  private static final String ago = "select c.directory_id,cust_id from customer c, customer_directory cd where c.directory_id = cd.directory_id and user_name=? and processor_pin=?";
  private static final String afB = "update customer set processor_pin=?, password_status=? where directory_id=?";
  private static final String agv = "select c.directory_id,cust_id from customer c, customer_directory cd where c.directory_id = cd.directory_id and user_name=? and password=?";
  private static final String afs = "update customer set password=?,password_status=?,reminder=?,pwd_reminder=? where directory_id=?";
  private static final String afm = "update customer set password_status=? where directory_id=?";
  private static final String af8 = "update customer_directory set last_active_date=? where directory_id=?";
  private static final String afD = "update customer_directory set modified_date=?,account_status=? where directory_id=?";
  private static final String afE = "insert into business_employee (directory_id,business_id) values (?,?)";
  private static final String afM = "select business_id from business where business_id=?";
  private static final String aft = "update business_employee set primary_user=? where directory_id=? and business_id=?";
  private static final String agx = "select c.*, cd.*, be.* from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id";
  private static final String afw = "select be.directory_id, cd.account_status from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id";
  private static final String agp = "select count(*) from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id";
  private static final String af6 = "select c.*, cd.*, be.* from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id";
  private static final String agc = "update customer set password=?,password_status=?,password_fail_count=?,password_lockout_time=? where directory_id=?";
  private static final String agr = "insert into PASSWORD_HISTORY ( USER_TYPE, USER_ID, PASSWORD_DATE, PASSWORD ) values( ?, ?, ?, ?)";
  private static final String afn = "select PASSWORD_DATE, PASSWORD from PASSWORD_HISTORY where USER_TYPE = ? and USER_ID = ? order by PASSWORD_DATE desc";
  private static final String agb = "delete from PASSWORD_HISTORY where USER_TYPE = ? and USER_ID = ? and PASSWORD_DATE = ?";
  private static final String afX = "select affiliate_bank_id from customer where directory_id = ?";
  private static final String agd = "select password_status, password_fail_count, password_lockout_time from customer where directory_id = ?";
  private static final String afK = "update customer set password_status = ?, password_fail_count=?, password_lockout_time=? where directory_id = ?";
  private static final String afV = "select directory_id from customer where bank_id=? and user_name=?";
  private static final String afU = "select c.directory_id from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.bank_id=? and c.user_name=? and cd.cust_id = ?";
  private static final String afu = "INSERT INTO contact_points( contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure ) VALUES ( ?, ?, ?, ?, ?, ? )";
  private static final String afJ = "update contact_points set contact_point_name = ?, contact_point_type = ?, address = ? where contact_point_id = ? and directory_id = ?";
  private static final String afI = "delete from contact_points where contact_point_id = ? and directory_id = ?";
  private static final String agi = "select contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure from contact_points where directory_id = ?";
  private static final String afx = "select contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure from contact_points where contact_point_id = ?";
  private static final String afC = "select c.*,cd.*, b.* from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.directory_id=?";
  private static final String afG = "select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.directory_id=?";
  private static final String aga = "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.PRIMARY_USER_ID and cr.DIRECTORY_ID = ?";
  
  public static SecureUser authenticateUser(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.authenticateUser";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Timestamp localTimestamp1 = DBUtil.getCurrentTimestamp();
    int i = 0;
    int j = 0;
    int k = 0;
    Timestamp localTimestamp2 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    int m = -1;
    int n = 0;
    int i1 = (paramHashMap == null) || (paramHashMap.get("NO_PASSWORD_CHECK") == null) ? 1 : 0;
    Locale localLocale = Locale.getDefault();
    if (paramSecureUser.getUserName() != null) {
      paramSecureUser.updateUserName(paramSecureUser.getUserName().toLowerCase());
    }
    paramSecureUser.setUserType(1);
    String str9 = paramSecureUser.getUserName();
    String str10 = paramSecureUser.getPassword();
    String str11 = paramSecureUser.getBusinessCustId();
    String str12 = (String)paramSecureUser.get("PROCESSOR_PIN");
    String str13 = paramSecureUser.getBankID();
    if ((str9 == null) || (str9.length() == 0)) {
      Profile.handleError(str1, "Invalid userName", 6);
    }
    if ((str13 == null) || (str13.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank ID", 4);
    }
    String str14 = " (username:" + str9 + ", bankID:" + str13 + ")";
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Timestamp localTimestamp3;
      Object localObject1;
      if ((SignonSettings.allowDuplicateUserNames()) && (str11 != null))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id,b.affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,c.processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2 from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.bank_id=? and c.user_name=? and cd.account_status=1 and cd.cust_id = ?");
        Profile.setStatementString(localPreparedStatement, 1, str13, "bank_id", false);
        Profile.setStatementString(localPreparedStatement, 2, str9, "user_name", false);
        Profile.setStatementString(localPreparedStatement, 3, str11, "cust_id", false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id,b.affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,c.processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2 from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.bank_id=? and c.user_name=? and cd.account_status=1 and cd.cust_id = ?");
        if (!localResultSet.next())
        {
          Profile.handleError(str1, "User not found" + str14, 7);
        }
        else
        {
          i = localResultSet.getInt("directory_id");
          m = localResultSet.getInt("affiliate_bank_id");
          str4 = localResultSet.getString("password");
          j = localResultSet.getInt("password_status");
          k = localResultSet.getInt("password_fail_count");
          localTimestamp2 = localResultSet.getTimestamp("password_lockout_time");
          str2 = Profile.getRSString(localResultSet, "cust_id");
          str3 = localResultSet.getString("processor_pin");
          str5 = localResultSet.getString("REMINDER");
          str6 = localResultSet.getString("REMINDER2");
          str7 = localResultSet.getString("PWD_REMINDER");
          str8 = localResultSet.getString("PWD_REMINDER2");
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
        }
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select cust_id, last_active_date, account_status, create_date from customer_directory where directory_id = ?");
        Profile.setStatementInt(localPreparedStatement, 1, i, false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select cust_id, last_active_date, account_status, create_date from customer_directory where directory_id = ?");
        if (!localResultSet.next())
        {
          Profile.handleError(str1, "User not found" + str14, 7);
        }
        else
        {
          localTimestamp3 = localResultSet.getTimestamp("last_active_date");
          int i3 = localResultSet.getInt("account_status");
          if (i3 != 1) {
            Profile.handleError(str1, "User not found" + str14, 7);
          }
          localObject1 = localResultSet.getTimestamp("CREATE_DATE");
          jdMethod_for(paramSecureUser, localTimestamp3, (Timestamp)localObject1, localLocale);
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
        }
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id,affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2,cd.last_active_date, cd.create_date from customer c, customer_directory cd where c.directory_id=cd.directory_id and bank_id=? and user_name=? and account_status=1");
        Profile.setStatementString(localPreparedStatement, 1, str13, "bank_id", false);
        Profile.setStatementString(localPreparedStatement, 2, str9, "user_name", false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id,affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2,cd.last_active_date, cd.create_date from customer c, customer_directory cd where c.directory_id=cd.directory_id and bank_id=? and user_name=? and account_status=1");
        if (!localResultSet.next())
        {
          Profile.handleError(str1, "User not found" + str14, 7);
        }
        else
        {
          i = localResultSet.getInt("directory_id");
          m = localResultSet.getInt("affiliate_bank_id");
          str4 = localResultSet.getString("password");
          j = localResultSet.getInt("password_status");
          k = localResultSet.getInt("password_fail_count");
          localTimestamp2 = localResultSet.getTimestamp("password_lockout_time");
          str2 = Profile.getRSString(localResultSet, "cust_id");
          str3 = localResultSet.getString("processor_pin");
          str5 = localResultSet.getString("reminder");
          str6 = localResultSet.getString("REMINDER2");
          str7 = localResultSet.getString("pwd_reminder");
          str8 = localResultSet.getString("PWD_REMINDER2");
          localTimestamp3 = localResultSet.getTimestamp("last_active_date");
          Timestamp localTimestamp4 = localResultSet.getTimestamp("create_date");
          jdMethod_for(paramSecureUser, localTimestamp3, localTimestamp4, localLocale);
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
        }
      }
      paramSecureUser.updateProfileID(i);
      paramSecureUser.updateId(str2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT PRIMARY_USER_ID FROM CUSTOMER_REL where DIRECTORY_ID = ?");
      Profile.setStatementInt(localPreparedStatement, 1, i, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT PRIMARY_USER_ID FROM CUSTOMER_REL where DIRECTORY_ID = ?");
      if (localResultSet.next()) {
        paramSecureUser.setPrimaryUserID(localResultSet.getInt(1));
      } else {
        paramSecureUser.setPrimaryUserID(paramSecureUser.getProfileID());
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT cd.cust_id, c.processor_pin FROM customer_directory cd, customer c where c.directory_id = cd.directory_id and cd.directory_id = ?");
      Profile.setStatementInt(localPreparedStatement, 1, paramSecureUser.getPrimaryUserID(), false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT cd.cust_id, c.processor_pin FROM customer_directory cd, customer c where c.directory_id = cd.directory_id and cd.directory_id = ?");
      if (localResultSet.next())
      {
        paramSecureUser.setPrimaryUserCustID(Profile.getRSString(localResultSet, "CUST_ID"));
        paramSecureUser.setPrimaryUserProcessorPin(localResultSet.getString("PROCESSOR_PIN"));
      }
      else
      {
        paramSecureUser.setPrimaryUserCustID("");
        paramSecureUser.setPrimaryUserProcessorPin("");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      int i2 = paramSecureUser.getAffiliateIDValue();
      getBusinessInfo(localConnection, paramSecureUser);
      if ((paramSecureUser.getBusinessID() != 0) && (!String.valueOf(paramSecureUser.getBusinessStatus()).equals("1"))) {
        Profile.handleError(str1, "Login to business that is not active" + str14, 23);
      }
      if (i2 != 0)
      {
        if (i2 != paramSecureUser.getAffiliateIDValue())
        {
          getBusinessInfo(localConnection, paramSecureUser);
          Profile.handleError(str1, "Login using wrong affiliate bank " + str14, 18);
        }
      }
      else if (m != 0) {
        paramSecureUser.setAffiliateID(m);
      }
      getAffiliateBank(localConnection, paramSecureUser);
      int i4;
      if (i1 != 0)
      {
        if (isLockedPasswordStatus(j)) {
          if (localTimestamp1.after(localTimestamp2))
          {
            j = getUnlockedPasswordStatus(j);
            k = 0;
          }
          else
          {
            Profile.handleError(str1, "User locked out" + str14, 11);
          }
        }
        i4 = 0;
        if ((str10 == null) || ((str10.length() == 0) && ((str12 == null) || (str12.length() == 0)))) {
          n = 1;
        }
        if ((str10 != null) && (str10.length() > 0) && (!str4.equals(Profile.checkEncrypt(str10, "password")))) {
          n = 1;
        }
        if ((str12 != null) && (str12.length() > 0) && (!str3.equals(Profile.checkEncrypt(str12, "processor_pin")))) {
          n = 1;
        }
        if (n != 0)
        {
          k++;
          if (k >= SignonSettings.getNumLoginsBeforeLockout())
          {
            j = getLockedPasswordStatus(j);
            localObject1 = Calendar.getInstance();
            ((Calendar)localObject1).add(12, SignonSettings.getLockoutDuration());
            localTimestamp2 = new Timestamp(((Calendar)localObject1).getTime().getTime());
            i4 = 1;
          }
        }
        else
        {
          k = 0;
        }
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update customer set password_status=?,password_fail_count=?,password_lockout_time=? where directory_id=?");
        localPreparedStatement.setInt(1, j);
        localPreparedStatement.setInt(2, k);
        localPreparedStatement.setTimestamp(3, localTimestamp2);
        localPreparedStatement.setInt(4, i);
        DBUtil.executeUpdate(localPreparedStatement, "update customer set password_status=?,password_fail_count=?,password_lockout_time=? where directory_id=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        if (i4 != 0) {
          Profile.handleError(str1, "User locked out" + str14, 11);
        }
      }
      if (i1 != 0) {
        if (n != 0)
        {
          Profile.handleError(str1, "Invalid password/pin" + str14, 8);
        }
        else
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "select PASSWORD_DATE, PASSWORD from PASSWORD_HISTORY where USER_TYPE = ? and USER_ID = ? order by PASSWORD_DATE desc");
          Profile.setStatementInt(localPreparedStatement, 1, paramSecureUser.getUserType(), true);
          Profile.setStatementInt(localPreparedStatement, 2, paramSecureUser.getProfileID(), true);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, "select PASSWORD_DATE, PASSWORD from PASSWORD_HISTORY where USER_TYPE = ? and USER_ID = ? order by PASSWORD_DATE desc");
          i4 = 0;
          if (localResultSet.next())
          {
            localObject1 = localResultSet.getTimestamp(1);
            long l = System.currentTimeMillis() - ((Timestamp)localObject1).getTime();
            if (l >= SignonSettings.getPwdExpireTime() * 86400000L)
            {
              i4 = 1;
            }
            else if (l >= SignonSettings.getPwdWarningTime() * 86400000L)
            {
              DebugLog.log(Level.FINE, str1 + ": Password about to expire " + str14);
              paramSecureUser.put("AUTHENTICATE", new Integer(3018));
            }
          }
          if ((j == 2) || (i4 != 0))
          {
            DebugLog.log(Level.FINE, str1 + ": Password Change required " + str14);
            paramSecureUser.put("AUTHENTICATE", new Integer(3007));
          }
          else if (j == 4)
          {
            DebugLog.log(Level.FINE, str1 + ": Account Expired " + str14);
            paramSecureUser.put("AUTHENTICATE", new Integer(3017));
          }
          else if (j == 3)
          {
            DebugLog.log(Level.FINE, str1 + ": Password Authenticate required " + str14);
            paramSecureUser.put("AUTHENTICATE", new Integer(3006));
          }
          else if ((str5 == null) || (str5.length() == 0) || (str6 == null) || (str6.length() == 0) || (str7 == null) || (str7.length() == 0) || (str8 == null) || (str8.length() == 0))
          {
            DebugLog.log(Level.FINE, str1 + ": User is missing one or more password questions and/or answers " + str14);
            paramSecureUser.put("AUTHENTICATE", new Integer(3023));
          }
        }
      }
      setLastActiveDateTX(localConnection, i);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return paramSecureUser;
  }
  
  public static boolean isLockedPasswordStatus(int paramInt)
  {
    return (paramInt == 1) || (paramInt == 5);
  }
  
  public static int getLockedPasswordStatus(int paramInt)
  {
    if (paramInt == 2) {
      return 5;
    }
    return 1;
  }
  
  public static int getUnlockedPasswordStatus(int paramInt)
  {
    if (paramInt == 5) {
      return 2;
    }
    return 0;
  }
  
  public static boolean userExists(String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "CustomerAdapter.userExists";
    boolean bool = false;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select directory_id from customer where user_name=?");
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      Profile.handleError(str, "Invalid user name", 6);
    }
    if (!Profile.properties.getProperty("UNIQUE_USER", "").equalsIgnoreCase("true")) {
      Profile.appendSQLSelectString(localStringBuffer, "bank_id", paramString2, true);
    } else {
      paramString2 = null;
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString1.toLowerCase(), "user_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramString2, "bank_id", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        bool = true;
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
    return bool;
  }
  
  public static int getVirtualUserID()
    throws ProfileException
  {
    String str = "CustomerAdapter.getVirtualUserID";
    Connection localConnection = null;
    int i = 0;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      i = DBUtil.getNextId(localConnection, Profile.dbType, "DIRECTORY_ID");
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return i;
  }
  
  public static boolean userExists(String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str = "CustomerAdapter.userExists";
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      Profile.handleError(str, "Invalid user name", 6);
    }
    StringBuffer localStringBuffer = null;
    if (paramString2 == null) {
      localStringBuffer = new StringBuffer("select c.directory_id from customer c where c.customer_type=? and c.user_name=?");
    } else {
      localStringBuffer = new StringBuffer("select c.directory_id from customer c, customer_directory cd, business b, business_employee be where c.directory_id = be.directory_id and be.business_id = b.business_id and b.directory_id=cd.directory_id and c.user_name=? and cd.cust_id=?");
    }
    if (!Profile.properties.getProperty("UNIQUE_USER", "").equalsIgnoreCase("true")) {
      Profile.appendSQLSelectString(localStringBuffer, "c.bank_id", paramString3, true);
    } else {
      paramString3 = null;
    }
    boolean bool = false;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = 1;
      if (paramString2 == null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        i = Profile.setStatementInt(localPreparedStatement, i, 2, false);
        i = Profile.setStatementString(localPreparedStatement, i, paramString1.toLowerCase(), "user_name", false);
        i = Profile.setStatementString(localPreparedStatement, i, paramString3, "bank_id", true);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        i = Profile.setStatementString(localPreparedStatement, i, paramString1.toLowerCase(), "user_name", false);
        i = Profile.setStatementString(localPreparedStatement, i, paramString2, "cust_id", false);
        i = Profile.setStatementString(localPreparedStatement, i, paramString3, "bank_id", true);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      if (localResultSet.next()) {
        bool = true;
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
    return bool;
  }
  
  public static User addUser(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.addUser";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (paramUser.getPreferredLanguage() == null) {
        Profile.handleError(str, "The user has no preferred language", 19);
      }
      jdMethod_for(localConnection, paramUser);
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
    return paramUser;
  }
  
  public static void addSecondaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "CustomerAdapter.addSecondaryUser";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (paramUser.getPreferredLanguage() == null) {
      Profile.handleError(str, "The user has no preferred language", 19);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      paramUser.setPrimarySecondary(User.USER_TYPE_SECONDARY);
      jdMethod_for(localConnection, paramUser);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT INTO CUSTOMER_REL (DIRECTORY_ID, PRIMARY_USER_ID ) VALUES (?, ?)");
      localPreparedStatement.setInt(1, paramUser.getIdValue());
      localPreparedStatement.setInt(2, paramSecureUser.getProfileID());
      DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO CUSTOMER_REL (DIRECTORY_ID, PRIMARY_USER_ID ) VALUES (?, ?)");
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
  
  private static User jdMethod_for(Connection paramConnection, User paramUser)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      String str1 = (String)paramUser.get("BANK_ID");
      String str2 = paramUser.getCustId();
      String str3 = (String)paramUser.get("BUSINESS_ID");
      if ((!Profile.isValidId(str3)) && (paramUser.getAffiliateBankID() <= 0)) {
        Profile.handleError("CustomerAdapter.addUser", "Invalid Affiliate Bank ID", 4);
      }
      int j = 1;
      if (Profile.isValidInt(paramUser.getAccountStatus())) {
        j = Profile.convertToInt(paramUser.getAccountStatus());
      } else {
        paramUser.setAccountStatus(String.valueOf(1));
      }
      i = addCustomerDirectoryTX(paramConnection, str2, j, paramUser);
      if ((str2 == null) || (str2.length() == 0)) {
        paramUser.setCustId(String.valueOf(i));
      }
      StringBuffer localStringBuffer = new StringBuffer("insert into customer (directory_id,bank_id,first_name,middle_name,last_name,low_first_name,low_middle_name,low_last_name,user_name,password,processor_pin,ssn,address1,address2,city,state,zip,country,home_phone,work_phone,email_address,mothers_maiden,birth_date,timeout,pwd_reminder,PWD_REMINDER2,password_status,personal_banker,gender,title,greeting,greeting_type,service_level,reminder,REMINDER2,prefer_contact,data_phone,fax_phone,affiliate_bank_id,customer_type,preferred_lang,TERMS_ACCEPTED,TERMS_ACCEPTED_DATE,USER_TYPE");
      Profile.appendSQLInsertColumns(localStringBuffer, "customer", true);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int k = 1;
      k = Profile.setStatementInt(localPreparedStatement, k, i, false);
      paramUser.setId(String.valueOf(i));
      if (paramUser.getUserName() != null) {
        paramUser.setUserName(paramUser.getUserName().toLowerCase());
      } else {
        paramUser.setUserName(null);
      }
      if (paramUser.getPasswordReminder() != null) {
        paramUser.setPasswordReminder(paramUser.getPasswordReminder().toLowerCase());
      } else {
        paramUser.setPasswordReminder(null);
      }
      if (paramUser.getPasswordReminder2() != null) {
        paramUser.setPasswordReminder2(paramUser.getPasswordReminder2().toLowerCase());
      } else {
        paramUser.setPasswordReminder2(null);
      }
      k = Profile.setStatementString(localPreparedStatement, k, str1, "bank_id", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getFirstName(), "first_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getMiddleName(), "middle_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getLastName(), "last_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getFirstNameLowerCase(), "low_first_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getMiddleNameLowerCase(), "low_middle_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getLastNameLowerCase(), "low_last_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getUserName(), "user_name", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPassword(), "password", false);
      String str4 = null;
      if ((paramUser.get("PROCESSOR_PIN") != null) && (((String)paramUser.get("PROCESSOR_PIN")).length() > 0)) {
        str4 = (String)paramUser.get("PROCESSOR_PIN");
      }
      k = Profile.setStatementString(localPreparedStatement, k, str4, "processor_pin", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getSSN(), "ssn", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getStreet(), "address1", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getStreet2(), "address2", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getCity(), "city", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getState(), "state", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getZipCode(), "zip", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getCountry(), "country", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPhone(), "home_phone", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPhone2(), "work_phone", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getEmail(), "email_address", false);
      k = Profile.setStatementString(localPreparedStatement, k, (String)paramUser.get("MOTHERS_MAIDEN"), "mothers_maiden", false);
      java.sql.Date localDate = Profile.convertToDate(paramUser.get("BIRTH_DATE"));
      k = Profile.setStatementDate(localPreparedStatement, k, localDate, false);
      int m = Profile.convertToInt(paramUser.getTimeout());
      if (m <= 0)
      {
        m = 900;
        paramUser.setTimeout("900");
      }
      k = Profile.setStatementInt(localPreparedStatement, k, m, false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPasswordReminder(), "pwd_reminder", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPasswordReminder2(), "PWD_REMINDER2", false);
      k = Profile.setStatementInt(localPreparedStatement, k, Profile.convertToInt((String)paramUser.get("PASSWORD_STATUS")), false);
      k = Profile.setStatementInt(localPreparedStatement, k, paramUser.getPersonalBanker(), false);
      k = Profile.setStatementInt(localPreparedStatement, k, paramUser.getGender(), false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getTitle(), "title", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getGreeting(), "greeting", false);
      k = Profile.setStatementInt(localPreparedStatement, k, paramUser.getGreetingType(), false);
      k = Profile.setStatementInt(localPreparedStatement, k, paramUser.getServiceLevel(), false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPasswordClue(), "reminder", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPasswordClue2(), "REMINDER2", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPreferredContactMethod(), "prefer_contact", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getDataPhone(), "data_phone", false);
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getFaxPhone(), "fax_phone", false);
      if (paramUser.getAffiliateBankID() == -1) {
        localPreparedStatement.setNull(k++, 4);
      } else {
        k = Profile.setStatementInt(localPreparedStatement, k, paramUser.getAffiliateBankID(), false);
      }
      if (Profile.isValidId(str3)) {
        paramUser.set("CUSTOMER_TYPE", "1");
      } else {
        paramUser.set("CUSTOMER_TYPE", "2");
      }
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getCustomerType(), "customer_type", false);
      if (paramUser.getPrimarySecondary() == null) {
        paramUser.setPrimarySecondary(User.USER_TYPE_PRIMARY);
      }
      k = Profile.setStatementString(localPreparedStatement, k, paramUser.getPreferredLanguage(), "preferred_lang", false);
      k = Profile.setStatementInt(localPreparedStatement, k, paramUser.getTermsAccepted(), false);
      com.ffusion.util.beans.DateTime localDateTime = paramUser.getTermsAcceptedDate();
      Timestamp localTimestamp = null;
      if (localDateTime != null)
      {
        String str5 = localDateTime.getFormat();
        try
        {
          localDateTime.setFormat("MM/dd/yyyy");
          localTimestamp = Profile.convertToTimestamp(localDateTime.toString());
        }
        finally
        {
          localDateTime.setFormat(str5);
        }
      }
      k = Profile.setStatementDate(localPreparedStatement, k, localTimestamp, false);
      try
      {
        k = Profile.setStatementInt(localPreparedStatement, k, Integer.parseInt(paramUser.getPrimarySecondary()), false);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Profile.handleError(localNumberFormatException, "CustomerAdapter.addUser", "Invalid user type", 3741);
      }
      k = Profile.setStatementValues(localPreparedStatement, k, paramUser, "customer", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      if (Profile.isValidId(str3)) {
        jdMethod_for(paramConnection, i, Profile.convertToInt(str3));
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return paramUser;
  }
  
  protected static int addCustomerDirectoryTX(Connection paramConnection, String paramString, int paramInt, ExtendABean paramExtendABean)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("insert into customer_directory (directory_id,cust_id,account_status,last_active_date,create_date,modified_date,directory_date");
      Profile.appendSQLInsertColumns(localStringBuffer, "customer_directory", true);
      i = DBUtil.getNextId(paramConnection, Profile.dbType, "directory_id");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      int j = 1;
      localPreparedStatement.setInt(j++, i);
      if ((paramString == null) || (paramString.length() == 0)) {
        paramString = String.valueOf(i);
      }
      j = Profile.setStatementString(localPreparedStatement, j, paramString, "cust_id", false);
      localPreparedStatement.setInt(j++, paramInt);
      localPreparedStatement.setTimestamp(j++, localTimestamp);
      localPreparedStatement.setTimestamp(j++, localTimestamp);
      localPreparedStatement.setTimestamp(j++, localTimestamp);
      localPreparedStatement.setTimestamp(j++, localTimestamp);
      j = Profile.setStatementValues(localPreparedStatement, j, paramExtendABean, "customer_directory", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i;
  }
  
  private static void jdMethod_for(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into business_employee (directory_id,business_id) values (?,?)");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into business_employee (directory_id,business_id) values (?,?)");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static User getUserById(int paramInt)
    throws ProfileException
  {
    String str = "CustomerAdapter.getUserById";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    User localUser = null;
    StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLSelectInt(localStringBuffer, "c.", "directory_id", paramInt, true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        localUser = jdMethod_for(localResultSet, false);
      } else {
        throw new ProfileException(str, 7, "The user could not be found in the database.");
      }
      getBusinessInfo(localConnection, localUser);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localUser;
  }
  
  public static User getUser(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.getUser";
    Profile.isInitialized();
    User localUser = null;
    if (paramSecureUser.getBusinessCustId() != null) {
      localUser = jdMethod_case(paramSecureUser);
    } else {
      localUser = jdMethod_byte(paramSecureUser);
    }
    if (localUser == null) {
      throw new ProfileException(str, 7, "The user could not be found.");
    }
    return localUser;
  }
  
  private static User jdMethod_case(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.getBusinessUser";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id");
    User localUser = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLSelectString(localStringBuffer, "user_name", paramSecureUser.getUserName(), false, true);
      Profile.appendSQLSelectString(localStringBuffer, "password", paramSecureUser.getPassword(), false, true);
      Profile.appendSQLSelectString(localStringBuffer, "c.bank_id", paramSecureUser.getBankID(), false, true);
      Profile.appendSQLSelectString(localStringBuffer, "cust_id", paramSecureUser.getBusinessCustId(), false, true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getUserName(), "user_name", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getPassword(), "password", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getBankID(), "bank_id", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getBusinessCustId(), "cust_id", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Object localObject1;
      if (localResultSet.next())
      {
        localUser = jdMethod_for(localResultSet, false);
      }
      else
      {
        localObject1 = null;
        return localObject1;
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      i = 1;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select cd.* from customer_directory cd where directory_id=?");
      i = Profile.setStatementInt(localPreparedStatement, i, localUser.getId(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        localObject1 = Locale.getDefault();
        localUser.setCustId(Profile.getRSString(localResultSet, "cust_id"));
        localUser.setAccountStatus(String.valueOf(localResultSet.getInt("account_status")));
        Timestamp localTimestamp1 = localResultSet.getTimestamp("last_active_date");
        Timestamp localTimestamp2 = localResultSet.getTimestamp("create_date");
        jdMethod_for(localUser, localTimestamp1, localTimestamp2, (Locale)localObject1);
        Timestamp localTimestamp3 = localResultSet.getTimestamp("modified_date");
        if (localTimestamp3 != null) {
          localUser.put("modified_date", new com.ffusion.beans.DateTime(localTimestamp3, (Locale)localObject1));
        }
        localTimestamp3 = localResultSet.getTimestamp("directory_date");
        if (localTimestamp3 != null) {
          localUser.put("directory_date", new com.ffusion.beans.DateTime(localTimestamp3, (Locale)localObject1));
        }
      }
      else
      {
        localObject1 = null;
        return localObject1;
      }
      getBusinessInfo(localConnection, localUser);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localUser;
  }
  
  private static User jdMethod_byte(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.getConsumerUser";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id");
    User localUser1 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Profile.appendSQLSelectString(localStringBuffer, "user_name", paramSecureUser.getUserName(), false, true);
      Profile.appendSQLSelectString(localStringBuffer, "password", paramSecureUser.getPassword(), false, true);
      Profile.appendSQLSelectString(localStringBuffer, "c.bank_id", paramSecureUser.getBankID(), false, true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getUserName(), "user_name", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getPassword(), "password", true);
      i = Profile.setStatementString(localPreparedStatement, i, paramSecureUser.getBankID(), "bank_id", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        localUser1 = jdMethod_for(localResultSet, false);
        getBusinessInfo(localConnection, localUser1);
      }
      else
      {
        User localUser2 = null;
        return localUser2;
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
    return localUser1;
  }
  
  public static void logFailedLogin(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.logFailedLogin";
    Users localUsers = getUsers(paramUser);
    if (localUsers.size() != 1) {
      throw new ProfileException(2);
    }
    User localUser = (User)localUsers.get(0);
    int i = Integer.parseInt((String)localUser.get("PASSWORD_FAIL_COUNT"));
    int j = Integer.parseInt((String)localUser.get("PASSWORD_STATUS"));
    i++;
    if (i >= SignonSettings.getNumLoginsBeforeLockout())
    {
      j = getLockedPasswordStatus(j);
      localObject1 = Calendar.getInstance();
      ((Calendar)localObject1).add(12, SignonSettings.getLockoutDuration());
      Timestamp localTimestamp = new Timestamp(((Calendar)localObject1).getTime().getTime());
      localUser.put("PASSWORD_LOCKOUT_TIME", localTimestamp);
    }
    localUser.put("PASSWORD_FAIL_COUNT", Integer.toString(i));
    localUser.put("PASSWORD_STATUS", Integer.toString(j));
    Object localObject1 = null;
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, false, 2);
      modifyUserTX((Connection)localObject1, localUser);
      DBUtil.commit((Connection)localObject1);
    }
    catch (Exception localException)
    {
      DBUtil.rollback((Connection)localObject1);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, (Connection)localObject1);
    }
  }
  
  public static void logFailedLogin(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "CustomerAdapter.logFailedLogin( SecureUser )";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select password_status, password_fail_count, password_lockout_time from customer where directory_id = ?");
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select password_status, password_fail_count, password_lockout_time from customer where directory_id = ?");
      Timestamp localTimestamp1 = DBUtil.getCurrentTimestamp();
      int i = 0;
      if (localResultSet.next())
      {
        int j = localResultSet.getInt("PASSWORD_STATUS");
        int k = localResultSet.getInt("PASSWORD_FAIL_COUNT");
        Timestamp localTimestamp2 = localResultSet.getTimestamp("PASSWORD_LOCKOUT_TIME");
        Calendar localCalendar;
        if (isLockedPasswordStatus(j))
        {
          if (localTimestamp1.after(localTimestamp2))
          {
            j = getUnlockedPasswordStatus(j);
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
            j = getLockedPasswordStatus(j);
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
        jdMethod_for(localConnection, paramSecureUser.getProfileID(), j, k, localTimestamp2);
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
  
  private static void jdMethod_for(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, Timestamp paramTimestamp)
    throws ProfileException
  {
    String str = "CustomerAdapter.updatePasswordStatus";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update customer set password_status = ?, password_fail_count=?, password_lockout_time=? where directory_id = ?");
      localPreparedStatement.setInt(1, paramInt2);
      localPreparedStatement.setInt(2, paramInt3);
      localPreparedStatement.setTimestamp(3, paramTimestamp);
      localPreparedStatement.setInt(4, paramInt1);
      DBUtil.executeUpdate(localPreparedStatement, "update customer set password_status = ?, password_fail_count=?, password_lockout_time=? where directory_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static boolean isUserLockedOut(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.isUserLockedOut";
    boolean bool = paramUser.containsKey("PASSWORD_STATUS");
    int i = 0;
    if (bool) {
      i = Integer.parseInt((String)paramUser.get("PASSWORD_STATUS"));
    }
    paramUser.remove("PASSWORD_STATUS");
    Users localUsers = getUsers(paramUser);
    if ((localUsers.size() != 1) && (paramUser.getCountry() != null) && (paramUser.getCountry().equals("USA")))
    {
      paramUser.setCountry(null);
      localUsers = getUsers(paramUser);
    }
    if (bool) {
      paramUser.put("PASSWORD_STATUS", Integer.toString(i));
    }
    if (localUsers.size() != 1) {
      throw new ProfileException(2);
    }
    User localUser = (User)localUsers.get(0);
    i = Integer.parseInt((String)localUser.get("PASSWORD_STATUS"));
    if ((i == 5) || (i == 1))
    {
      Timestamp localTimestamp1 = DBUtil.getCurrentTimestamp();
      Timestamp localTimestamp2 = Profile.convertToTimestamp(localUser.get("PASSWORD_LOCKOUT_TIME"));
      if (localTimestamp1.before(localTimestamp2)) {
        return true;
      }
      localUser.put("PASSWORD_FAIL_COUNT", Integer.toString(0));
      localUser.put("PASSWORD_STATUS", Integer.toString(getUnlockedPasswordStatus(i)));
      Connection localConnection = null;
      try
      {
        localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
        modifyUserTX(localConnection, localUser);
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
      return false;
    }
    return false;
  }
  
  public static boolean isUserExpired(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.isUserExpired";
    boolean bool = paramUser.containsKey("PASSWORD_STATUS");
    int i = 0;
    if (bool) {
      i = Integer.parseInt((String)paramUser.get("PASSWORD_STATUS"));
    }
    paramUser.remove("PASSWORD_STATUS");
    Users localUsers = getUsers(paramUser);
    if ((localUsers.size() != 1) && (paramUser.getCountry() != null) && (paramUser.getCountry().equals("USA")))
    {
      paramUser.setCountry(null);
      localUsers = getUsers(paramUser);
    }
    if (bool) {
      paramUser.put("PASSWORD_STATUS", Integer.toString(i));
    }
    if (localUsers.size() != 1) {
      throw new ProfileException(2);
    }
    User localUser = (User)localUsers.get(0);
    i = Integer.parseInt((String)localUser.get("PASSWORD_STATUS"));
    if (i != 4)
    {
      com.ffusion.beans.DateTime localDateTime = (com.ffusion.beans.DateTime)localUser.get("LAST_ACTIVE_DATE");
      long l = -1L;
      if (localDateTime != null) {
        l = System.currentTimeMillis() - localDateTime.getTime().getTime();
      }
      if (l >= SignonSettings.getInactivityPeriod() * 86400000L) {
        i = 4;
      }
    }
    return i == 4;
  }
  
  public static Users getSecondaryUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "CustomerAdapter.getSecondaryUsers";
    Users localUsers = new Users(paramSecureUser.getLocale());
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.DIRECTORY_ID and cr.PRIMARY_USER_ID=?");
      localPreparedStatement.setInt(1, paramUser.getIdValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.DIRECTORY_ID and cr.PRIMARY_USER_ID=?");
      while (localResultSet.next()) {
        localUsers.add(jdMethod_for(localResultSet, false));
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
    return localUsers;
  }
  
  public static User getPrimaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "CustomerAdapter.getPrimaryUser";
    User localUser = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.PRIMARY_USER_ID and cr.DIRECTORY_ID = ?");
      localPreparedStatement.setInt(1, paramUser.getIdValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.PRIMARY_USER_ID and cr.DIRECTORY_ID = ?");
      if (localResultSet.next()) {
        localUser = jdMethod_for(localResultSet, false);
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
    return localUser;
  }
  
  public static Users getUsers(User paramUser)
    throws ProfileException
  {
    return getUsers(paramUser, true, 250, new HashMap());
  }
  
  public static Users getUsers(User paramUser, boolean paramBoolean)
    throws ProfileException
  {
    return getUsers(paramUser, paramBoolean, 250, new HashMap());
  }
  
  public static Users getUsers(User paramUser, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    return getUsers(paramUser, paramBoolean, paramInt, new HashMap());
  }
  
  public static Users getUsers(User paramUser, boolean paramBoolean, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getUsers";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    Users localUsers = null;
    String str2 = (String)paramUser.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    int i = Profile.convertToInt((String)paramUser.get("BUSINESS_ID"));
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (Profile.isValidId(i)) {
        localStringBuffer.append("select c.*,cd.*,be.* from customer c, customer_directory cd, business_employee be, business b where c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      } else if (paramUser.get("NO_BUSINESS") != null) {
        localStringBuffer.append("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.customer_type='2'");
      } else {
        localStringBuffer.append("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id");
      }
      localPreparedStatement = jdMethod_for(localConnection, localStringBuffer, paramUser, false, paramHashMap);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localUsers = new Users(Locale.getDefault());
      boolean bool = isAccountStatusSpecified(paramUser);
      while (localResultSet.next())
      {
        int j = localResultSet.getInt("account_status");
        if ((bool) || ((!bool) && (j != 8))) {
          localUsers.add(jdMethod_for(localResultSet, Profile.isValidId(i)));
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
    return localUsers;
  }
  
  public static ArrayList getUsersIDs(User paramUser)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getUsers";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    String str2 = (String)paramUser.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    int i = Profile.convertToInt((String)paramUser.get("BUSINESS_ID"));
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (Profile.isValidId(i)) {
        localStringBuffer.append("select be.directory_id, cd.account_status from customer c, customer_directory cd, business_employee be, business b where c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      } else if (paramUser.get("NO_BUSINESS") != null) {
        localStringBuffer.append("select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.customer_type='2'");
      } else {
        localStringBuffer.append("select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id");
      }
      localPreparedStatement = jdMethod_for(localConnection, localStringBuffer, paramUser, false, null);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      boolean bool = isAccountStatusSpecified(paramUser);
      while (localResultSet.next())
      {
        int j = localResultSet.getInt("account_status");
        if ((bool) || ((!bool) && (j != 8))) {
          localArrayList.add(String.valueOf(localResultSet.getInt("directory_id")));
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
    return localArrayList;
  }
  
  public static ArrayList getUserIDsByUserNames(ArrayList paramArrayList)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getBusinessEmployeesIDs";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id and user_name = ?");
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        Profile.setStatementString(localPreparedStatement, 1, str2, "user_name", true);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id, cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id and user_name = ?");
        while (localResultSet.next())
        {
          int i = localResultSet.getInt("account_status");
          if (i != 8) {
            localArrayList.add(String.valueOf(localResultSet.getInt("directory_id")));
          }
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public static String getUsersCount(User paramUser)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getUsersCount";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    String str2 = "";
    String str3 = (String)paramUser.get("BANK_ID");
    if ((str3 == null) || (str3.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    int i = Profile.convertToInt((String)paramUser.get("BUSINESS_ID"));
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (Profile.isValidId(i)) {
        localStringBuffer.append("select count(*) from customer c, customer_directory cdwhere c.customer_type='1' and c.directory_id=cd.directory_id");
      } else if (paramUser.get("NO_BUSINESS") != null) {
        localStringBuffer.append("select count(*) from customer c, customer_directory cd where c.customer_type='2' and c.directory_id=cd.directory_id");
      } else {
        localStringBuffer.append("select count(*) from customer c, customer_directory cd where c.directory_id=cd.directory_id");
      }
      localPreparedStatement = jdMethod_for(localConnection, localStringBuffer, paramUser, true, null);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
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
  
  public static Users getUserList(User paramUser)
    throws ProfileException
  {
    return getUserList(paramUser, true, 250);
  }
  
  public static Users getUserList(User paramUser, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getUserList";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    Users localUsers = null;
    String str2 = (String)paramUser.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    int i = Profile.convertToInt((String)paramUser.get("BUSINESS_ID"));
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if (Profile.isValidId(i)) {
        localStringBuffer.append("select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status,be.business_id from customer c, customer_directory cd, business_employee be where c.customer_type='1' and c.directory_id=cd.directory_id");
      } else {
        localStringBuffer.append("select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status from customer c, customer_directory cd where c.directory_id=cd.directory_id");
      }
      localPreparedStatement = jdMethod_for(localConnection, localStringBuffer, paramUser, false, null);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localUsers = new Users(Locale.getDefault());
      boolean bool = isAccountStatusSpecified(paramUser);
      while (localResultSet.next())
      {
        int j = localResultSet.getInt("account_status");
        if ((bool) || ((!bool) && (j != 8)))
        {
          User localUser = localUsers.add();
          localUser.setId(String.valueOf(localResultSet.getInt("directory_id")));
          localUser.setCustId(Profile.getRSString(localResultSet, "cust_id"));
          localUser.setAccountStatus(String.valueOf(localResultSet.getInt("account_status")));
          localUser.setFirstName(Profile.getRSString(localResultSet, "first_name"));
          localUser.setMiddleName(Profile.getRSString(localResultSet, "middle_name"));
          localUser.setLastName(Profile.getRSString(localResultSet, "last_name"));
          localUser.setUserName(Profile.getRSString(localResultSet, "user_name"));
          localUser.setSSN(Profile.getRSString(localResultSet, "ssn"));
          localUser.setEmail(Profile.getRSString(localResultSet, "email_address"));
          if (Profile.isValidId(i)) {
            localUser.set("BUSINESS_ID", localResultSet.getString("business_id"));
          }
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
    return localUsers;
  }
  
  public static Users getFilteredUsers(User paramUser, String paramString)
    throws ProfileException
  {
    return getFilteredUsers(paramUser, paramString, true, 250);
  }
  
  public static Users getFilteredUsers(User paramUser, String paramString, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getFilteredUsers";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Users localUsers = null;
    boolean bool = false;
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString == null) {
      paramString = "";
    }
    String str2 = (String)paramUser.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    java.sql.Date localDate = Profile.convertToDate(paramUser.get("BIRTH_DATE"));
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    if (paramString.length() > 0) {
      localStringBuffer.append("select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status,home_phone,work_phone,a.account_id from customer c, customer_directory cd, accounts a");
    } else {
      localStringBuffer.append("select first_name,middle_name,last_name,user_name,ssn,email_address,cd.cust_id,cd.directory_id,cd.account_status,home_phone,work_phone from customer c, customer_directory cd");
    }
    if (paramUser.getUserName() != null) {
      paramUser.setUserName(paramUser.getUserName().toLowerCase());
    }
    bool = Profile.appendSQLSelectDate(localStringBuffer, "birth_date", localDate, "=", bool);
    bool = Profile.appendSQLSelectInt(localStringBuffer, "personal_banker", paramUser.getPersonalBanker(), bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "account_id", paramString, true, L(paramString), bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "low_first_name", paramUser.getFirstNameLowerCase(), true, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "low_last_name", paramUser.getLastNameLowerCase(), true, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "low_middle_name", paramUser.getMiddleNameLowerCase(), true, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "user_name", paramUser.getUserName(), true, false, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "cust_id", paramUser.getCustId(), true, L(paramUser.getCustId()), bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "ssn", paramUser.getSSN(), true, false, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "home_phone", paramUser.getPhone(), true, false, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "work_phone", paramUser.getPhone2(), true, false, bool);
    bool = Profile.appendSQLSelectString(localStringBuffer, "address1", paramUser.getStreet(), true, true, bool);
    if (!bool) {
      Profile.handleError(str1, "No filter specified", 2);
    }
    int i = 0;
    bool = Profile.appendSQLSelectString(localStringBuffer, "bank_id", str2, bool);
    if (Profile.convertToInt(paramUser.getAccountStatus()) != 0)
    {
      bool = Profile.appendSQLSelectInt(localStringBuffer, "account_status", paramUser.getAccountStatus(), bool);
      i = 1;
    }
    if (paramString.length() > 0) {
      localStringBuffer.append(" and c.directory_id=cd.directory_id and a.directory_id=c.directory_id order by low_last_name");
    } else {
      localStringBuffer.append(" and c.directory_id=cd.directory_id order by low_last_name");
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementDate(localPreparedStatement, j, localDate, true);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getPersonalBanker(), true);
      j = Profile.setStatementString(localPreparedStatement, j, paramString, "account_id", true, true, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getFirstNameLowerCase(), "low_first_name", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getLastNameLowerCase(), "low_last_name", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getMiddleNameLowerCase(), "low_middle_name", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getUserName(), "user_name", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getCustId(), "cust_id", true, true, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getSSN(), "ssn", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPhone(), "home_phone", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPhone2(), "work_phone", true, false, true);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getStreet(), "address1", true, true, true);
      j = Profile.setStatementString(localPreparedStatement, j, str2, "bank_id", true);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getAccountStatus(), true);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localUsers = new Users(Locale.getDefault());
      while (localResultSet.next())
      {
        int k = localResultSet.getInt("account_status");
        if ((i != 0) || ((i == 0) && (k != 8)))
        {
          User localUser = localUsers.add();
          localUser.setId(String.valueOf(localResultSet.getInt("directory_id")));
          localUser.setFirstName(localResultSet.getString("first_name"));
          localUser.setMiddleName(localResultSet.getString("middle_name"));
          localUser.setLastName(localResultSet.getString("last_name"));
          localUser.setUserName(localResultSet.getString("user_name"));
          localUser.setId(String.valueOf(localResultSet.getInt("directory_id")));
          localUser.setSSN(localResultSet.getString("ssn"));
          localUser.setEmail(localResultSet.getString("email_address"));
          localUser.setCustId(Profile.getRSString(localResultSet, "cust_id"));
          localUser.setPhone(localResultSet.getString("home_phone"));
          localUser.setPhone2(localResultSet.getString("work_phone"));
          localUser.setAccountStatus(String.valueOf(k));
          if (paramString.length() > 0) {
            localUser.set("ACCOUNT_ID", localResultSet.getString("account_id"));
          }
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
    return localUsers;
  }
  
  public static Users getUsersX(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.getUsersX";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Users localUsers = null;
    StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id");
    Profile.appendSQLSelectColumns(localStringBuffer, "customer", paramUser);
    Profile.appendSQLSelectColumns(localStringBuffer, "customer_directory", paramUser);
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = Profile.setStatementValues(localPreparedStatement, 1, paramUser, "customer", true);
      i = Profile.setStatementValues(localPreparedStatement, i, paramUser, "customer_directory", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localUsers = new Users(Locale.getDefault());
      while (localResultSet.next()) {
        localUsers.add(jdMethod_for(localResultSet, false));
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
    return localUsers;
  }
  
  public static void deleteUser(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.deleteUser";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = Integer.parseInt(paramUser.getId());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      deleteUserTX(localConnection, i);
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
  
  protected static boolean deleteUserTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    boolean bool = true;
    try
    {
      if (!isUserDeletable(paramConnection, paramInt))
      {
        modifyCustomerDirectoryStatusTX(paramConnection, paramInt, 8);
        bool = false;
      }
      else
      {
        AccountAdapter.deleteAccountTX(paramConnection, paramInt, null);
        DirectoryDataAdapter.deleteAdditionalData(paramConnection, paramInt, null);
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from business_employee where directory_id=?");
        localPreparedStatement.setInt(1, paramInt);
        DBUtil.executeUpdate(localPreparedStatement, "delete from business_employee where directory_id=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from customer where directory_id=?");
        localPreparedStatement.setInt(1, paramInt);
        DBUtil.executeUpdate(localPreparedStatement, "delete from customer where directory_id=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from customer_directory where directory_id=?");
        localPreparedStatement.setInt(1, paramInt);
        DBUtil.executeUpdate(localPreparedStatement, "delete from customer_directory where directory_id=?");
        PayeeAdapter.deleteCustomerPayeeTX(paramConnection, paramInt, null);
        HistoryAdapter.deleteHistoryTX(paramConnection, paramInt, 1);
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return bool;
  }
  
  public static boolean modifyUser(User paramUser)
    throws ProfileException
  {
    String str = "CustomerAdapter.modifyUser";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (paramUser.getPreferredLanguage() == null) {
        Profile.handleError(str, "The user has no preferred language", 19);
      }
      modifyUserTX(localConnection, paramUser);
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
    return true;
  }
  
  protected static void modifyUserTX(Connection paramConnection, User paramUser)
    throws Exception
  {
    String str1 = "CustomerAdapter.modifyUserTX";
    PreparedStatement localPreparedStatement = null;
    int i = Profile.convertToInt(paramUser.getId());
    if (i == 0) {
      Profile.handleError(str1, "Invalid User ID", 4);
    }
    StringBuffer localStringBuffer = new StringBuffer("update customer set first_name=?,middle_name=?,last_name=?,low_first_name=?,low_middle_name=?,low_last_name=?,user_name=?,processor_pin=?,ssn=?,address1=?,address2=?,city=?,state=?,zip=?,country=?,home_phone=?,work_phone=?,email_address=?,mothers_maiden=?,birth_date=?,timeout=?,pwd_reminder=?,PWD_REMINDER2=?,password_status=?,password_fail_count=?,password_lockout_time=?,personal_banker=?,gender=?,title=?,greeting=?,greeting_type=?,service_level=?,reminder=?,REMINDER2=?,password=?,prefer_contact=?,data_phone=?,fax_phone=?,affiliate_bank_id=?,LASTVIEWINTRATRANS=?,preferred_lang=?,TERMS_ACCEPTED=?,TERMS_ACCEPTED_DATE=?");
    Profile.appendSQLUpdateColumns(localStringBuffer, "customer", paramUser);
    localStringBuffer.append(" where directory_id = ?");
    if (paramUser.getUserName() != null) {
      paramUser.setUserName(paramUser.getUserName().toLowerCase());
    } else {
      paramUser.setUserName(null);
    }
    if (paramUser.getPasswordReminder() != null) {
      paramUser.setPasswordReminder(paramUser.getPasswordReminder().toLowerCase());
    } else {
      paramUser.setPasswordReminder(null);
    }
    if (paramUser.getPasswordReminder2() != null) {
      paramUser.setPasswordReminder2(paramUser.getPasswordReminder2().toLowerCase());
    } else {
      paramUser.setPasswordReminder2(null);
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getFirstName(), "first_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getMiddleName(), "middle_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getLastName(), "last_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getFirstNameLowerCase(), "low_first_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getMiddleNameLowerCase(), "low_middle_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getLastNameLowerCase(), "low_last_name", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getUserName(), "user_name", false);
      String str2 = null;
      if ((paramUser.get("PROCESSOR_PIN") != null) && (((String)paramUser.get("PROCESSOR_PIN")).length() > 0)) {
        str2 = (String)paramUser.get("PROCESSOR_PIN");
      }
      j = Profile.setStatementString(localPreparedStatement, j, str2, "processor_pin", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getSSN(), "ssn", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getStreet(), "address1", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getStreet2(), "address2", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getCity(), "city", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getState(), "state", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getZipCode(), "zip", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getCountry(), "country", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPhone(), "home_phone", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPhone2(), "work_phone", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getEmail(), "email_address", false);
      j = Profile.setStatementString(localPreparedStatement, j, (String)paramUser.get("MOTHERS_MAIDEN"), "mothers_maiden", false);
      java.sql.Date localDate = Profile.convertToDate(paramUser.get("BIRTH_DATE"));
      j = Profile.setStatementDate(localPreparedStatement, j, localDate, false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getTimeout(), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPasswordReminder(), "pwd_reminder", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPasswordReminder2(), "PWD_REMINDER2", false);
      j = Profile.setStatementInt(localPreparedStatement, j, (String)paramUser.get("PASSWORD_STATUS"), false);
      j = Profile.setStatementInt(localPreparedStatement, j, (String)paramUser.get("PASSWORD_FAIL_COUNT"), false);
      Timestamp localTimestamp1 = Profile.convertToTimestamp(paramUser.get("PASSWORD_LOCKOUT_TIME"));
      j = Profile.setStatementDate(localPreparedStatement, j, localTimestamp1, false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getPersonalBanker(), false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getGender(), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getTitle(), "title", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getGreeting(), "greeting", false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getGreetingType(), false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getServiceLevel(), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPasswordClue(), "reminder", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPasswordClue2(), "REMINDER2", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPassword(), "password", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPreferredContactMethod(), "prefer_contact", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getDataPhone(), "data_phone", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getFaxPhone(), "fax_phone", false);
      if ((paramUser.getAffiliateBankID() == -1) || (paramUser.getCustomerType().equals(User.CUSTOMER_TYPE_EMPLOYEE))) {
        localPreparedStatement.setNull(j++, 4);
      } else {
        j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getAffiliateBankID(), false);
      }
      Timestamp localTimestamp2 = null;
      if ((paramUser instanceof BusinessEmployee)) {
        localTimestamp2 = Profile.convertToTimestamp(((BusinessEmployee)paramUser).getLastViewedIntradayTransDate());
      }
      j = Profile.setStatementDate(localPreparedStatement, j, localTimestamp2, false);
      j = Profile.setStatementString(localPreparedStatement, j, paramUser.getPreferredLanguage(), "preferred_lang", false);
      j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getTermsAccepted(), false);
      com.ffusion.util.beans.DateTime localDateTime = paramUser.getTermsAcceptedDate();
      Timestamp localTimestamp3 = null;
      if (localDateTime != null)
      {
        String str3 = localDateTime.getFormat();
        try
        {
          localDateTime.setFormat("MM/dd/yyyy");
          localTimestamp3 = Profile.convertToTimestamp(localDateTime.toString());
        }
        finally
        {
          localDateTime.setFormat(str3);
        }
      }
      j = Profile.setStatementDate(localPreparedStatement, j, localTimestamp3, false);
      j = Profile.setStatementValues(localPreparedStatement, j, paramUser, "customer", true);
      localPreparedStatement.setInt(j++, i);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      modifyCustomerDirectoryTX(paramConnection, i, paramUser.getCustId(), Integer.parseInt(paramUser.getAccountStatus()), null, paramUser);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static String modifyUserPin(String paramString1, String paramString2, String paramString3)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.modifyUserPin";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    String str2 = null;
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      Profile.handleError(str1, "Invalid userName", 6);
    }
    if (paramString3.length() < 4) {
      Profile.handleError(str1, "Invalid PIN", 8);
    }
    if (paramString2.equals(paramString3)) {
      Profile.handleError(str1, "Old and new PINs are the same", 8);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id,cust_id from customer c, customer_directory cd where c.directory_id = cd.directory_id and user_name=? and processor_pin=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString1.toLowerCase(), "user_name", false);
      Profile.setStatementString(localPreparedStatement, 2, paramString2, "processor_pin", false);
      ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id,cust_id from customer c, customer_directory cd where c.directory_id = cd.directory_id and user_name=? and processor_pin=?");
      if (localResultSet.next())
      {
        i = localResultSet.getInt("directory_id");
        str2 = localResultSet.getString("cust_id");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if (i == 0) {
        Profile.handleError(str1, "Invalid user name or password", 6);
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update customer set processor_pin=?, password_status=? where directory_id=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString3, "processor_pin", false);
      localPreparedStatement.setInt(2, 0);
      localPreparedStatement.setInt(3, i);
      DBUtil.executeUpdate(localPreparedStatement, "update customer set processor_pin=?, password_status=? where directory_id=?");
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
    return str2;
  }
  
  public static String modifyUserPassword(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.modifyUserPassword";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    String str2 = null;
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      Profile.handleError(str1, "Invalid userName", 6);
    }
    if (paramString2.equals(paramString3)) {
      Profile.handleError(str1, "Old and new passwords are the same", 8);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id,cust_id from customer c, customer_directory cd where c.directory_id = cd.directory_id and user_name=? and password=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString1.toLowerCase(), "user_name", false);
      Profile.setStatementString(localPreparedStatement, 2, paramString2, "password", false);
      ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id,cust_id from customer c, customer_directory cd where c.directory_id = cd.directory_id and user_name=? and password=?");
      if (localResultSet.next())
      {
        i = localResultSet.getInt("directory_id");
        str2 = localResultSet.getString("cust_id");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if (i == 0) {
        Profile.handleError(str1, "Invalid user name or password", 6);
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update customer set password=?,password_status=?,reminder=?,pwd_reminder=? where directory_id=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString3, "password", false);
      localPreparedStatement.setInt(2, 0);
      Profile.setStatementString(localPreparedStatement, 3, paramString4, "reminder", false);
      Profile.setStatementString(localPreparedStatement, 4, paramString5, "pwd_reminder", false);
      localPreparedStatement.setInt(5, i);
      DBUtil.executeUpdate(localPreparedStatement, "update customer set password=?,password_status=?,reminder=?,pwd_reminder=? where directory_id=?");
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
    return str2;
  }
  
  public static void modifyUserPassword(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
    throws ProfileException
  {
    String str = "CustomerAdapter.modifyUserPassword";
    Profile.isInitialized();
    if (paramString1.equals(paramString2)) {
      Profile.handleError(str, "Old and new passwords are the same", 8);
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update customer set password=?,password_status=?,reminder=?,pwd_reminder=? where directory_id=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString2, "password", false);
      localPreparedStatement.setInt(2, 0);
      Profile.setStatementString(localPreparedStatement, 3, paramString3, "reminder", false);
      Profile.setStatementString(localPreparedStatement, 4, paramString4, "pwd_reminder", false);
      localPreparedStatement.setInt(5, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update customer set password=?,password_status=?,reminder=?,pwd_reminder=? where directory_id=?");
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
  
  public static void modifyPasswordStatus(int paramInt1, int paramInt2)
    throws ProfileException
  {
    String str = "CustomerAdapter.modifyPasswordStatus";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramInt1)) {
      Profile.handleError(str, "Invalid user Id", 4);
    }
    if (paramInt2 < 0) {
      Profile.handleError(str, "Invalid password status", 3);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update customer set password_status=? where directory_id=?");
      localPreparedStatement.setInt(1, paramInt2);
      localPreparedStatement.setInt(2, paramInt1);
      DBUtil.executeUpdate(localPreparedStatement, "update customer set password_status=? where directory_id=?");
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
  
  protected static void modifyCustomerDirectoryTX(Connection paramConnection, int paramInt1, String paramString, int paramInt2, com.ffusion.beans.DateTime paramDateTime, ExtendABean paramExtendABean)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("update customer_directory set modified_date=?,cust_id=?,account_status=?,activation_date=? where directory_id=?");
    try
    {
      Profile.appendSQLUpdateColumns(localStringBuffer, "customer_directory", paramExtendABean);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      localPreparedStatement.setTimestamp(i++, DBUtil.getCurrentTimestamp());
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "cust_id", false);
      localPreparedStatement.setInt(i++, paramInt2);
      Timestamp localTimestamp = Profile.convertToTimestamp(paramDateTime);
      localPreparedStatement.setTimestamp(i++, localTimestamp);
      i = Profile.setStatementValues(localPreparedStatement, i, paramExtendABean, "customer_directory", true);
      localPreparedStatement.setInt(i++, paramInt1);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static void modifyCustomerDirectoryStatusTX(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update customer_directory set modified_date=?,account_status=? where directory_id=?");
      localPreparedStatement.setTimestamp(1, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setInt(3, paramInt1);
      DBUtil.executeUpdate(localPreparedStatement, "update customer_directory set modified_date=?,account_status=? where directory_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static void setLastActiveDateTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
    StringBuffer localStringBuffer = new StringBuffer("update customer_directory set last_active_date=? where directory_id=?");
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      localPreparedStatement.setTimestamp(i++, localTimestamp);
      localPreparedStatement.setInt(i++, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static boolean isUserDeletable(Connection paramConnection, int paramInt)
  {
    boolean bool = true;
    try
    {
      if (messageAdapter.hasMailReferencesTX(paramConnection, paramInt, 1) == true) {
        bool = false;
      }
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  protected static void getBusinessInfo(Connection paramConnection, SecureUser paramSecureUser)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?");
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?");
      if (localResultSet.next())
      {
        paramSecureUser.updateBusinessID(localResultSet.getInt("business_id"));
        paramSecureUser.updateBusinessCustId(localResultSet.getString("cust_id"));
        paramSecureUser.updateBusinessName(localResultSet.getString("business_name"));
        paramSecureUser.updateBusinessCIF(localResultSet.getString("business_CIF"));
        paramSecureUser.updateAffiliateID(localResultSet.getInt("affiliate_bank_id"));
        paramSecureUser.updateBusinessStatus(localResultSet.getInt("ACCOUNT_STATUS"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  protected static void getBusinessInfo(Connection paramConnection, User paramUser)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?");
      localPreparedStatement.setInt(1, paramUser.getIdValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?");
      if (localResultSet.next())
      {
        paramUser.set("BUSINESS_ID", localResultSet.getString("business_id"));
        paramUser.set("BUSINESS_CUST_ID", localResultSet.getString("cust_id"));
        paramUser.setAffiliateBankID(localResultSet.getInt("affiliate_bank_id"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  protected static void getBusinessInfo(Connection paramConnection, BusinessEmployee paramBusinessEmployee)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?");
      localPreparedStatement.setInt(1, paramBusinessEmployee.getIdValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select be.business_id, b.business_name, b.business_CIF, b.affiliate_bank_id, cd.cust_id, cd.account_status from business_employee be, business b, customer_directory cd where b.business_id=be.business_id and b.business_id=cd.directory_id and be.directory_id=?");
      if (localResultSet.next())
      {
        paramBusinessEmployee.setBusinessId(localResultSet.getInt("business_id"));
        paramBusinessEmployee.setBusinessCustId(localResultSet.getString("cust_id"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static PreparedStatement jdMethod_for(Connection paramConnection, StringBuffer paramStringBuffer, User paramUser, boolean paramBoolean, HashMap paramHashMap)
    throws Exception
  {
    boolean bool = true;
    PreparedStatement localPreparedStatement = null;
    int i = Profile.convertToInt((String)paramUser.get("BUSINESS_ID"));
    bool = Profile.appendSQLSelectInt(paramStringBuffer, "be.", "business_id", i, bool);
    bool = Profile.appendSQLSelectString(paramStringBuffer, "c.", "directory_id", paramUser.getId(), false, false, bool);
    bool = jdMethod_for(paramStringBuffer, paramUser, bool, paramBoolean, paramHashMap);
    localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramStringBuffer.toString());
    int j = 1;
    j = Profile.setStatementInt(localPreparedStatement, j, i, true);
    j = Profile.setStatementInt(localPreparedStatement, j, paramUser.getId(), true);
    j = jdMethod_for(localPreparedStatement, j, paramUser, paramHashMap);
    return localPreparedStatement;
  }
  
  private static User jdMethod_for(ResultSet paramResultSet, boolean paramBoolean)
    throws Exception
  {
    Locale localLocale = Locale.getDefault();
    User localUser = new User(localLocale);
    jdMethod_for(paramResultSet, localUser, paramBoolean);
    return localUser;
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, User paramUser, boolean paramBoolean)
    throws Exception
  {
    Locale localLocale = Locale.getDefault();
    paramUser.setId(String.valueOf(paramResultSet.getInt("directory_id")));
    paramUser.set("BANK_ID", Profile.getRSString(paramResultSet, "bank_id"));
    paramUser.setCustomerType(Profile.getRSString(paramResultSet, "customer_type"));
    paramUser.setFirstName(Profile.getRSString(paramResultSet, "first_name"));
    paramUser.setMiddleName(Profile.getRSString(paramResultSet, "middle_name"));
    paramUser.setLastName(Profile.getRSString(paramResultSet, "last_name"));
    paramUser.setUserName(Profile.getRSString(paramResultSet, "user_name"));
    paramUser.setPassword(Profile.getRSString(paramResultSet, "password"));
    paramUser.set("PROCESSOR_PIN", Profile.getRSString(paramResultSet, "processor_pin"));
    paramUser.setSSN(Profile.getRSString(paramResultSet, "ssn"));
    paramUser.setStreet(Profile.getRSString(paramResultSet, "address1"));
    paramUser.setStreet2(Profile.getRSString(paramResultSet, "address2"));
    paramUser.setCity(Profile.getRSString(paramResultSet, "city"));
    paramUser.setState(Profile.getRSString(paramResultSet, "state"));
    paramUser.setZipCode(Profile.getRSString(paramResultSet, "zip"));
    String str = Profile.getRSString(paramResultSet, "country");
    if (str == null) {
      paramUser.setCountry("USA");
    } else {
      paramUser.setCountry(str);
    }
    paramUser.setPhone(Profile.getRSString(paramResultSet, "home_phone"));
    paramUser.setPhone2(Profile.getRSString(paramResultSet, "work_phone"));
    paramUser.setEmail(Profile.getRSString(paramResultSet, "email_address"));
    paramUser.set("MOTHERS_MAIDEN", Profile.getRSString(paramResultSet, "mothers_maiden"));
    java.sql.Date localDate = paramResultSet.getDate("birth_date");
    if (localDate != null)
    {
      localObject = new SimpleDateFormat("MM/dd/yyyy");
      paramUser.put("BIRTH_DATE", ((SimpleDateFormat)localObject).format(localDate));
    }
    paramUser.setTimeout(String.valueOf(paramResultSet.getInt("timeout")));
    paramUser.setPasswordReminder(Profile.getRSString(paramResultSet, "pwd_reminder"));
    paramUser.setPasswordReminder2(Profile.getRSString(paramResultSet, "PWD_REMINDER2"));
    paramUser.set("PASSWORD_STATUS", String.valueOf(paramResultSet.getInt("password_status")));
    paramUser.set("PASSWORD_FAIL_COUNT", String.valueOf(paramResultSet.getInt("password_fail_count")));
    Object localObject = paramResultSet.getTimestamp("password_lockout_time");
    if (localObject != null) {
      paramUser.put("PASSWORD_LOCKOUT_TIME", new com.ffusion.beans.DateTime((java.util.Date)localObject, localLocale));
    }
    paramUser.setPersonalBanker(String.valueOf(paramResultSet.getInt("personal_banker")));
    paramUser.setGender(String.valueOf(paramResultSet.getInt("gender")));
    paramUser.setTitle(Profile.getRSString(paramResultSet, "title"));
    paramUser.setGreeting(Profile.getRSString(paramResultSet, "greeting"));
    paramUser.setGreetingType(String.valueOf(paramResultSet.getInt("GREETING_TYPE")));
    paramUser.setServiceLevel(String.valueOf(paramResultSet.getInt("SERVICE_LEVEL")));
    paramUser.setPasswordClue(Profile.getRSString(paramResultSet, "reminder"));
    paramUser.setPasswordClue2(Profile.getRSString(paramResultSet, "REMINDER2"));
    paramUser.setPreferredContactMethod(Profile.getRSString(paramResultSet, "prefer_contact"));
    paramUser.setDataPhone(Profile.getRSString(paramResultSet, "data_phone"));
    paramUser.setFaxPhone(Profile.getRSString(paramResultSet, "fax_phone"));
    paramUser.setPreferredLanguage(Profile.getRSString(paramResultSet, "preferred_lang"));
    paramUser.setTermsAccepted(String.valueOf(paramResultSet.getInt("TERMS_ACCEPTED")));
    Timestamp localTimestamp1 = paramResultSet.getTimestamp("TERMS_ACCEPTED_DATE");
    if (localTimestamp1 != null) {
      paramUser.setTermsAcceptedDate(new com.ffusion.util.beans.DateTime(localTimestamp1, localLocale, "MM/dd/yyyy HH:mm:ss"));
    }
    paramUser.setPrimarySecondary(Profile.getRSString(paramResultSet, "USER_TYPE"));
    paramUser.setCustId(Profile.getRSString(paramResultSet, "cust_id"));
    paramUser.setAccountStatus(String.valueOf(paramResultSet.getInt("account_status")));
    paramUser.setAffiliateBankID(paramResultSet.getInt("affiliate_bank_id"));
    if (paramResultSet.wasNull()) {
      paramUser.setAffiliateBankID(-1);
    }
    Timestamp localTimestamp2 = paramResultSet.getTimestamp("last_active_date");
    Timestamp localTimestamp3 = paramResultSet.getTimestamp("create_date");
    jdMethod_for(paramUser, localTimestamp2, localTimestamp3, localLocale);
    localObject = paramResultSet.getTimestamp("modified_date");
    if (localObject != null) {
      paramUser.put("MODIFIED_DATE", new com.ffusion.beans.DateTime((java.util.Date)localObject, localLocale));
    }
    localObject = paramResultSet.getTimestamp("directory_date");
    if (localObject != null) {
      paramUser.put("DIRECTORY_DATE", new com.ffusion.beans.DateTime((java.util.Date)localObject, localLocale));
    }
    if (paramBoolean) {
      paramUser.set("BUSINESS_ID", String.valueOf(paramResultSet.getInt("business_id")));
    }
    Profile.setXBeanFields(paramResultSet, paramUser, "customer");
    Profile.setXBeanFields(paramResultSet, paramUser, "customer_directory");
  }
  
  private static boolean L(String paramString)
  {
    boolean bool = false;
    if (paramString != null)
    {
      int i = paramString.length();
      for (int j = 0; j < i; j++) {
        if (Character.isLetter(paramString.charAt(j)))
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public static BusinessEmployee addBusinessEmployee(BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str = "CustomerAdapter.addBusinessEmployee";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = paramBusinessEmployee.getBusinessId();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (jdMethod_for(localConnection, i) == true)
      {
        paramBusinessEmployee.set("BUSINESS_ID", Integer.toString(paramBusinessEmployee.getBusinessId()));
        paramBusinessEmployee.set("CUSTOMER_TYPE", "1");
        jdMethod_for(localConnection, paramBusinessEmployee);
        jdMethod_for(localConnection, paramBusinessEmployee);
        DBUtil.commit(localConnection);
      }
      else
      {
        Profile.handleError(str, "Invalid Business Id", 4);
      }
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
    return paramBusinessEmployee;
  }
  
  public static boolean modifyBusinessEmployee(BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str = "CustomerAdapter.modifyBusinessEmployee";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = paramBusinessEmployee.getBusinessId();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (jdMethod_for(localConnection, i) == true)
      {
        modifyUserTX(localConnection, paramBusinessEmployee);
        jdMethod_for(localConnection, paramBusinessEmployee);
        DBUtil.commit(localConnection);
      }
      else
      {
        Profile.handleError(str, "Invalid Business Id", 4);
      }
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
    return true;
  }
  
  public static void deleteBusinessEmployee(BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str = "CustomerAdapter.deleteBusinessEmployee";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = paramBusinessEmployee.getBusinessId();
    int j = Integer.parseInt(paramBusinessEmployee.getId());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (jdMethod_for(localConnection, i) == true)
      {
        deleteUserTX(localConnection, j);
        DBUtil.commit(localConnection);
      }
      else
      {
        Profile.handleError(str, "Invalid Business Id", 4);
      }
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
  
  public static BusinessEmployees getBusinessEmployees(BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    return getBusinessEmployees(paramBusinessEmployee, true);
  }
  
  public static BusinessEmployees getBusinessEmployees(BusinessEmployee paramBusinessEmployee, boolean paramBoolean)
    throws ProfileException
  {
    return getBusinessEmployees(paramBusinessEmployee, paramBoolean, 250);
  }
  
  public static BusinessEmployees getBusinessEmployees(BusinessEmployee paramBusinessEmployee, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getBusinessEmployees";
    Profile.isInitialized();
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.*,be.* from customer c, customer_directory cd, business_employee be, business b where c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
    BusinessEmployees localBusinessEmployees = null;
    String str2 = (String)paramBusinessEmployee.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool1 = true;
      bool1 = Profile.appendSQLSelectInt2(localStringBuffer, "be.", "business_id", paramBusinessEmployee.getBusinessId(), bool1);
      bool1 = Profile.appendSQLSelectString2(localStringBuffer, "be.", "primary_user", paramBusinessEmployee.getPrimaryUser(), false, false, bool1);
      bool1 = Profile.appendSQLSelectInt2(localStringBuffer, "be.", "directory_id", paramBusinessEmployee.getIdValue(), bool1);
      bool1 = jdMethod_for(localStringBuffer, paramBusinessEmployee, bool1, false);
      localStatement = DBUtil.createStatement(localConnection);
      if (paramBoolean) {
        localStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      localBusinessEmployees = new BusinessEmployees(Locale.getDefault());
      boolean bool2 = isAccountStatusSpecified(paramBusinessEmployee);
      while (localResultSet.next())
      {
        int i = localResultSet.getInt("account_status");
        if ((bool2) || ((!bool2) && (i != 8)))
        {
          BusinessEmployee localBusinessEmployee = jdMethod_new(localResultSet);
          getBusinessInfo(localConnection, localBusinessEmployee);
          localBusinessEmployees.add(localBusinessEmployee);
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localBusinessEmployees;
  }
  
  public static ArrayList getBusinessEmployeesIDs(BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getBusinessEmployeesIDs";
    Profile.isInitialized();
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select be.directory_id, cd.account_status from customer c, customer_directory cd, business_employee be, business b where c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
    ArrayList localArrayList = null;
    String str2 = (String)paramBusinessEmployee.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool1 = true;
      bool1 = Profile.appendSQLSelectInt2(localStringBuffer, "be.", "business_id", paramBusinessEmployee.getBusinessId(), bool1);
      bool1 = Profile.appendSQLSelectString2(localStringBuffer, "be.", "primary_user", paramBusinessEmployee.getPrimaryUser(), false, false, bool1);
      bool1 = Profile.appendSQLSelectInt2(localStringBuffer, "be.", "directory_id", paramBusinessEmployee.getIdValue(), bool1);
      bool1 = jdMethod_for(localStringBuffer, paramBusinessEmployee, bool1, false);
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      localArrayList = new ArrayList();
      boolean bool2 = isAccountStatusSpecified(paramBusinessEmployee);
      while (localResultSet.next())
      {
        int i = localResultSet.getInt("account_status");
        if ((bool2) || ((!bool2) && (i != 8))) {
          localArrayList.add(String.valueOf(localResultSet.getInt("directory_id")));
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public static ArrayList getBusinessEmployeesIDs(ArrayList paramArrayList)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getBusinessEmployeesIDs";
    Profile.isInitialized();
    if ((paramArrayList == null) || (paramArrayList.size() == 0)) {
      return new ArrayList();
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select be.directory_id, cd.account_status from business_employee be, business b, customer_directory cd where be.business_id=b.business_id and be.directory_id=cd.directory_id and b.business_name = ?");
      localArrayList = new ArrayList();
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        Profile.setStatementString(localPreparedStatement, 1, str2, "business_name", true);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select be.directory_id, cd.account_status from business_employee be, business b, customer_directory cd where be.business_id=b.business_id and be.directory_id=cd.directory_id and b.business_name = ?");
        while (localResultSet.next())
        {
          int i = localResultSet.getInt("account_status");
          if (i != 8) {
            localArrayList.add(String.valueOf(localResultSet.getInt("directory_id")));
          }
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public static Users getConsumers(User paramUser)
    throws ProfileException
  {
    return getConsumers(paramUser, true, 250);
  }
  
  public static Users getConsumers(User paramUser, boolean paramBoolean)
    throws ProfileException
  {
    return getConsumers(paramUser, paramBoolean, 250);
  }
  
  public static Users getConsumers(User paramUser, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getConsumers";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd where c.customer_type='2' and c.directory_id=cd.directory_id");
    Users localUsers = null;
    String str2 = (String)paramUser.get("BANK_ID");
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool1 = true;
      bool1 = jdMethod_for(localStringBuffer, paramUser, bool1, false, null);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      jdMethod_for(localPreparedStatement, 1, paramUser, null);
      if (paramBoolean) {
        localPreparedStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localUsers = new Users(Locale.getDefault());
      boolean bool2 = isAccountStatusSpecified(paramUser);
      while (localResultSet.next())
      {
        int i = localResultSet.getInt("account_status");
        if ((bool2) || ((!bool2) && (i != 8))) {
          localUsers.add(jdMethod_for(localResultSet, false));
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
    return localUsers;
  }
  
  public static String getConsumersCount(User paramUser)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getConsumersCount";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select count(*) from customer c, customer_directory cd where c.customer_type='2' and c.directory_id=cd.directory_id");
    String str2 = "";
    String str3 = (String)paramUser.get("BANK_ID");
    if ((str3 == null) || (str3.length() == 0)) {
      Profile.handleError(str1, "Invalid Bank Id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool = true;
      bool = jdMethod_for(localStringBuffer, paramUser, bool, true, null);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      jdMethod_for(localPreparedStatement, 1, paramUser, null);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
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
  
  public static BusinessEmployees getFilteredBusinessEmployees(Business paramBusiness, BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    return getFilteredBusinessEmployees(paramBusiness, paramBusinessEmployee, true, 250);
  }
  
  public static BusinessEmployees getFilteredBusinessEmployees(Business paramBusiness, BusinessEmployee paramBusinessEmployee, boolean paramBoolean, int paramInt)
    throws ProfileException
  {
    String str = "CustomerAdapter.getFilteredBusinessEmployees";
    Profile.isInitialized();
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    BusinessEmployees localBusinessEmployees = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool1 = true;
      StringBuffer localStringBuffer = new StringBuffer("select c.*, cd.*, be.* from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      if (paramBusinessEmployee != null) {
        bool1 = BusinessAdapter.createGetSQLByEmployee2(localStringBuffer, paramBusinessEmployee, bool1);
      }
      if (paramBusiness != null) {
        bool1 = BusinessAdapter.createGetSQL2(localStringBuffer, paramBusiness, bool1, false);
      }
      localStatement = DBUtil.createStatement(localConnection);
      if (paramBoolean) {
        localStatement.setMaxRows(paramInt);
      }
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      localBusinessEmployees = new BusinessEmployees(Locale.getDefault());
      boolean bool2 = isAccountStatusSpecified(paramBusinessEmployee);
      while (localResultSet.next())
      {
        int i = localResultSet.getInt("account_status");
        if ((bool2) || ((!bool2) && (i != 8)))
        {
          BusinessEmployee localBusinessEmployee = jdMethod_new(localResultSet);
          getBusinessInfo(localConnection, localBusinessEmployee);
          localBusinessEmployees.add(localBusinessEmployee);
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localBusinessEmployees;
  }
  
  public static ArrayList getFilteredBusinessEmployeesIDs(Business paramBusiness, BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str = "CustomerAdapter.getFilteredBusinessEmployeesIDs";
    Profile.isInitialized();
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool1 = true;
      StringBuffer localStringBuffer = new StringBuffer("select be.directory_id, cd.account_status from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      if (paramBusinessEmployee != null) {
        bool1 = BusinessAdapter.createGetSQLByEmployee2(localStringBuffer, paramBusinessEmployee, bool1);
      }
      if (paramBusiness != null) {
        bool1 = BusinessAdapter.createGetSQL2(localStringBuffer, paramBusiness, bool1, false);
      }
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      localArrayList = new ArrayList();
      boolean bool2 = isAccountStatusSpecified(paramBusinessEmployee);
      while (localResultSet.next())
      {
        int i = localResultSet.getInt("account_status");
        if ((bool2) || ((!bool2) && (i != 8))) {
          localArrayList.add(String.valueOf(localResultSet.getInt("directory_id")));
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public static String getFilteredBusinessEmployeesCount(Business paramBusiness, BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getFilteredBusinessEmployeesCount";
    Profile.isInitialized();
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    String str2 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      boolean bool = true;
      StringBuffer localStringBuffer = new StringBuffer("select count(*) from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      if (paramBusinessEmployee != null) {
        bool = BusinessAdapter.createGetSQLByEmployee2(localStringBuffer, paramBusinessEmployee, bool);
      }
      if (paramBusiness != null) {
        bool = BusinessAdapter.createGetSQL2(localStringBuffer, paramBusiness, bool, true);
      }
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
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
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return str2;
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, User paramUser, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws Exception
  {
    boolean bool = true;
    if (paramHashMap != null)
    {
      str = (String)paramHashMap.get("UseLikeComparison");
      if ((str != null) && (str.equals("false"))) {
        bool = false;
      }
    }
    String str = (String)paramUser.get("BANK_ID");
    if ((str != null) && (!str.equals("*"))) {
      paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "c.", "bank_id", str, false, false, paramBoolean1, false);
    }
    int i = paramUser.getAffiliateBankID();
    if (i > 0) {
      paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "affiliate_bank_id", i, paramBoolean1);
    }
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "low_first_name", paramUser.getFirstNameLowerCase(), bool, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "low_middle_name", paramUser.getMiddleNameLowerCase(), bool, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "low_last_name", paramUser.getLastNameLowerCase(), bool, paramBoolean1);
    if (paramUser.getUserName() != null) {
      paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "user_name", paramUser.getUserName().toLowerCase(), bool, paramBoolean1);
    }
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "cust_id", paramUser.getCustId(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "password", paramUser.getPassword(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "processor_pin", (String)paramUser.get("PROCESSOR_PIN"), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "ssn", paramUser.getSSN(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "address1", paramUser.getStreet(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "address2", paramUser.getStreet2(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "city", paramUser.getCity(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "state", paramUser.getState(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "zip", paramUser.getZipCode(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "country", paramUser.getCountry(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "home_phone", paramUser.getPhone(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "work_phone", paramUser.getPhone2(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "email_address", paramUser.getEmail(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectDate(paramStringBuffer, "birth_date", Profile.convertToDate(paramUser.get("BIRTH_DATE")), "=", paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "personal_banker", paramUser.getPersonalBanker(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "password_status", (String)paramUser.get("PASSWORD_STATUS"), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString(paramStringBuffer, "customer_type", paramUser.getCustomerType(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "USER_TYPE", paramUser.getPrimarySecondary(), paramBoolean1);
    if (paramUser.getAffiliateBankID() > 0) {
      paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "affiliate_bank_id", paramUser.getAffiliateBankID(), paramBoolean1);
    }
    java.sql.Date localDate1 = Profile.convertToDate(paramUser.get("FROM_DATE"));
    java.sql.Date localDate2 = Profile.convertToDate(paramUser.get("TO_DATE"));
    paramBoolean1 = Profile.appendSQLSelectDate(paramStringBuffer, "modified_date", localDate1, ">=", paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectDate(paramStringBuffer, "modified_date", localDate2, "<=", paramBoolean1);
    if (!isAccountStatusSpecified(paramUser))
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
    else {
      paramBoolean1 = Profile.appendSQLSelectInt(paramStringBuffer, "account_status", paramUser.getAccountStatus(), paramBoolean1);
    }
    Profile.appendSQLSelectColumns(paramStringBuffer, "customer", paramUser);
    Profile.appendSQLSelectColumns(paramStringBuffer, "customer_directory", paramUser);
    return paramBoolean1;
  }
  
  private static boolean jdMethod_for(StringBuffer paramStringBuffer, User paramUser, boolean paramBoolean1, boolean paramBoolean2)
    throws Exception
  {
    int i = paramUser.getAffiliateBankID();
    if (i > 0)
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
      paramStringBuffer.append("(");
      paramStringBuffer.append("b.");
      paramStringBuffer.append("affiliate_bank_id");
      paramStringBuffer.append(" = ");
      paramStringBuffer.append(i);
      paramStringBuffer.append(" or c.");
      paramStringBuffer.append("affiliate_bank_id");
      paramStringBuffer.append(" = ");
      paramStringBuffer.append(i);
      paramStringBuffer.append(")");
    }
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "c.", "bank_id", (String)paramUser.get("BANK_ID"), false, false, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "low_first_name", paramUser.getFirstNameLowerCase(), true, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "low_middle_name", paramUser.getMiddleNameLowerCase(), true, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "low_last_name", paramUser.getLastNameLowerCase(), true, paramBoolean1);
    if (paramUser.getUserName() != null) {
      paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "user_name", paramUser.getUserName().toLowerCase(), true, paramBoolean1);
    }
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "cust_id", paramUser.getCustId(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "password", paramUser.getPassword(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "processor_pin", (String)paramUser.get("PROCESSOR_PIN"), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "ssn", paramUser.getSSN(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "address1", paramUser.getStreet(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "address2", paramUser.getStreet2(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "city", paramUser.getCity(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "state", paramUser.getState(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "zip", paramUser.getZipCode(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "country", paramUser.getCountry(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "home_phone", paramUser.getPhone(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "work_phone", paramUser.getPhone2(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "email_address", paramUser.getEmail(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectDate2(paramStringBuffer, "birth_date", Profile.convertToDate(paramUser.get("BIRTH_DATE")), "=", paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "personal_banker", paramUser.getPersonalBanker(), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "password_status", (String)paramUser.get("PASSWORD_STATUS"), paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectString2(paramStringBuffer, "customer_type", paramUser.getCustomerType(), paramBoolean1);
    java.sql.Date localDate1 = Profile.convertToDate(paramUser.get("FROM_DATE"));
    java.sql.Date localDate2 = Profile.convertToDate(paramUser.get("TO_DATE"));
    paramBoolean1 = Profile.appendSQLSelectDate2(paramStringBuffer, "modified_date", localDate1, ">=", paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectDate2(paramStringBuffer, "modified_date", localDate2, "<=", paramBoolean1);
    if (!isAccountStatusSpecified(paramUser))
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
    else {
      paramBoolean1 = Profile.appendSQLSelectInt2(paramStringBuffer, "account_status", paramUser.getAccountStatus(), paramBoolean1);
    }
    paramBoolean1 = Profile.appendSQLSelectColumns2(paramStringBuffer, "customer", paramUser, paramBoolean1);
    paramBoolean1 = Profile.appendSQLSelectColumns2(paramStringBuffer, "customer_directory", paramUser, paramBoolean1);
    return paramBoolean1;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, User paramUser, HashMap paramHashMap)
    throws Exception
  {
    boolean bool = true;
    if (paramHashMap != null)
    {
      str = (String)paramHashMap.get("UseLikeComparison");
      if ((str != null) && (str.equals("false"))) {
        bool = false;
      }
    }
    String str = (String)paramUser.get("BANK_ID");
    if ((str != null) && (!str.equals("*"))) {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, str, "bank_id", false, false, true, false);
    }
    int i = paramUser.getAffiliateBankID();
    if (i > 0) {
      paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, i, true);
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getFirstNameLowerCase(), "low_first_name", bool, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getMiddleNameLowerCase(), "low_middle_name", bool, false, true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getLastNameLowerCase(), "low_last_name", bool, false, true);
    if (paramUser.getUserName() != null) {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getUserName().toLowerCase(), "user_name", bool, true, true);
    }
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getCustId(), "cust_id", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getPassword(), "password", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, (String)paramUser.get("PROCESSOR_PIN"), "processor_pin", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getSSN(), "ssn", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getStreet(), "address1", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getStreet2(), "address2", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getCity(), "city", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getState(), "state", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getZipCode(), "zip", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getCountry(), "country", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getPhone(), "home_phone", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getPhone2(), "work_phone", true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getEmail(), "email_address", true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, Profile.convertToDate(paramUser.get("BIRTH_DATE")), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramUser.getPersonalBanker(), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, (String)paramUser.get("PASSWORD_STATUS"), true);
    paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getCustomerType(), "customer_type", true);
    if (paramUser.getPrimarySecondary() == null) {
      paramInt = Profile.setStatementString(paramPreparedStatement, paramInt, paramUser.getPrimarySecondary(), "USER_TYPE", true);
    } else {
      try
      {
        paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, Integer.parseInt(paramUser.getPrimarySecondary()), true);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Profile.handleError(localNumberFormatException, "", "Invalid user type", 3741);
      }
    }
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramUser.getAffiliateBankID(), true);
    java.sql.Date localDate1 = Profile.convertToDate(paramUser.get("FROM_DATE"));
    java.sql.Date localDate2 = Profile.convertToDate(paramUser.get("TO_DATE"));
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, localDate1, true);
    paramInt = Profile.setStatementDate(paramPreparedStatement, paramInt, Profile.getNextDay(localDate2), true);
    paramInt = Profile.setStatementInt(paramPreparedStatement, paramInt, paramUser.getAccountStatus(), true);
    paramInt = Profile.setStatementValues(paramPreparedStatement, paramInt, paramUser, "customer", true);
    paramInt = Profile.setStatementValues(paramPreparedStatement, paramInt, paramUser, "customer_directory", true);
    return paramInt;
  }
  
  private static BusinessEmployee jdMethod_new(ResultSet paramResultSet)
    throws Exception
  {
    BusinessEmployee localBusinessEmployee = new BusinessEmployee(Locale.getDefault());
    localBusinessEmployee.setBusinessId(paramResultSet.getInt("business_id"));
    localBusinessEmployee.setPrimaryUser(Profile.getRSString(paramResultSet, "primary_user"));
    Timestamp localTimestamp = paramResultSet.getTimestamp("LASTVIEWINTRATRANS");
    if (localTimestamp != null) {
      localBusinessEmployee.setLastViewedIntradayTransDate(new com.ffusion.beans.DateTime(localTimestamp, localBusinessEmployee.getLocale()));
    }
    jdMethod_for(paramResultSet, localBusinessEmployee, false);
    return localBusinessEmployee;
  }
  
  private static void jdMethod_for(Connection paramConnection, BusinessEmployee paramBusinessEmployee)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update business_employee set primary_user=? where directory_id=? and business_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramBusinessEmployee.getPrimaryUser(), "primary_user", false);
      i = Profile.setStatementInt(localPreparedStatement, i, Integer.parseInt(paramBusinessEmployee.getId()), false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramBusinessEmployee.getBusinessId(), false);
      DBUtil.executeUpdate(localPreparedStatement, "update business_employee set primary_user=? where directory_id=? and business_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static boolean isValidBusinessId(int paramInt)
    throws Exception
  {
    String str = "CustomerAdapter.isValidBusinessId";
    Profile.isInitialized();
    Connection localConnection = null;
    boolean bool = false;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      bool = jdMethod_for(localConnection, paramInt);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return bool;
  }
  
  private static boolean jdMethod_for(Connection paramConnection, int paramInt)
    throws Exception
  {
    boolean bool = false;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select business_id from business where business_id=?");
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select business_id from business where business_id=?");
      bool = localResultSet.next();
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return bool;
  }
  
  public static BusinessEmployees getBusinessEmployeesByIds(ArrayList paramArrayList)
    throws ProfileException
  {
    String str = "CustomerAdapter.getBusinessEmployeesByIds";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer1 = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    BusinessEmployees localBusinessEmployees = new BusinessEmployees(Locale.getDefault());
    if (paramArrayList.size() == 0) {
      return localBusinessEmployees;
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer1 = new StringBuffer("select c.*, cd.*, be.* from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      StringBuffer localStringBuffer2 = new StringBuffer();
      Iterator localIterator = paramArrayList.iterator();
      Object localObject1;
      while (localIterator.hasNext())
      {
        localObject1 = (String)localIterator.next();
        localStringBuffer2.append((String)localObject1);
        localStringBuffer2.append(",");
      }
      Profile.appendSQLSelectInt2(localStringBuffer1, "c.", "directory_id", localStringBuffer2.toString(), true);
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer1.toString());
      while (localResultSet.next())
      {
        localObject1 = jdMethod_new(localResultSet);
        getBusinessInfo(localConnection, (BusinessEmployee)localObject1);
        localBusinessEmployees.add(localObject1);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localBusinessEmployees;
  }
  
  public static Users getUsersByIds(ArrayList paramArrayList)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getUsersByIds";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Users localUsers = new Users(Locale.getDefault());
    if (paramArrayList.size() == 0) {
      return localUsers;
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer1 = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id");
      StringBuffer localStringBuffer2 = new StringBuffer();
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localStringBuffer2.append(str2);
        localStringBuffer2.append(",");
      }
      Profile.appendSQLSelectInt(localStringBuffer1, "c.", "directory_id", localStringBuffer2.toString(), true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      Profile.setStatementInt(localPreparedStatement, 1, localStringBuffer2.toString(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      while (localResultSet.next()) {
        localUsers.add(jdMethod_for(localResultSet, false));
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
    return localUsers;
  }
  
  public static void resetUserPassword(User paramUser, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "CustomerAdapter.resetUserPassword";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update customer set password=?,password_status=?,password_fail_count=?,password_lockout_time=? where directory_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString2, "password", false);
      localPreparedStatement.setInt(i++, 0);
      localPreparedStatement.setInt(i++, 0);
      localPreparedStatement.setTimestamp(i++, null);
      localPreparedStatement.setInt(i++, paramUser.getIdValue());
      DBUtil.executeUpdate(localPreparedStatement, "update customer set password=?,password_status=?,password_fail_count=?,password_lockout_time=? where directory_id=?");
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
  
  public static void addToPasswordHistory(int paramInt1, int paramInt2, String paramString)
    throws ProfileException
  {
    String str = "CustomerAdapter.addToPasswordHistory";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into PASSWORD_HISTORY ( USER_TYPE, USER_ID, PASSWORD_DATE, PASSWORD ) values( ?, ?, ?, ?)");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
      i = Profile.setStatementDate(localPreparedStatement, i, DBUtil.getCurrentTimestamp(), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "password", false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into PASSWORD_HISTORY ( USER_TYPE, USER_ID, PASSWORD_DATE, PASSWORD ) values( ?, ?, ?, ?)");
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
  
  public static int checkPasswordHistory(int paramInt1, int paramInt2, String paramString, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.checkPasswordHistory";
    int i = 1;
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str2 = Profile.checkEncrypt(paramString, "password");
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer = new StringBuffer("select PASSWORD_DATE, PASSWORD from PASSWORD_HISTORY where USER_TYPE = ? and USER_ID = ? order by PASSWORD_DATE desc");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Profile.setStatementInt(localPreparedStatement, 1, paramInt1, true);
      Profile.setStatementInt(localPreparedStatement, 2, paramInt2, true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      int j = 0;
      int k = SignonSettings.getPwdReuseHistory();
      while (localResultSet.next())
      {
        Timestamp localTimestamp = localResultSet.getTimestamp(1);
        String str3 = localResultSet.getString(2);
        if (i != 0)
        {
          if (str3.equals(str2))
          {
            if (paramBoolean)
            {
              m = 0;
              return m;
            }
            int m = 4;
            return m;
          }
          long l1 = SignonSettings.getPwdReuseOnTime() * 3600000L;
          if (l1 > 0L)
          {
            long l2 = System.currentTimeMillis() - localTimestamp.getTime();
            if ((paramBoolean) && (l2 < l1))
            {
              int i1 = 3;
              return i1;
            }
          }
          i = 0;
        }
        int n;
        if (k == 0)
        {
          n = 1;
          return n;
        }
        if (str3.equals(str2))
        {
          n = 4;
          return n;
        }
        j++;
        if (j == k)
        {
          n = 1;
          return n;
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
    return 1;
  }
  
  protected static boolean isAccountStatusSpecified(User paramUser)
  {
    if (paramUser == null) {
      return false;
    }
    String str = paramUser.getAccountStatus();
    return (str != null) && (str.length() != 0);
  }
  
  protected static void getAffiliateBank(Connection paramConnection, SecureUser paramSecureUser)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (paramSecureUser.getAffiliateIDValue() <= 0) {
      Profile.handleError("CustomerAdapter.authenticateUser", "Invalid Affiliate Bank ID", 4);
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select bpw_fi_id, currency_code from affiliate_bank where affiliate_bank_id=?");
      localPreparedStatement.setInt(1, paramSecureUser.getAffiliateIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select bpw_fi_id, currency_code from affiliate_bank where affiliate_bank_id=?");
      if (localResultSet.next())
      {
        paramSecureUser.setBPWFIID(localResultSet.getString("bpw_fi_id"));
        paramSecureUser.setBaseCurrency(localResultSet.getString("currency_code"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getBankIdsByServicesPackage";
    DBCookie localDBCookie = null;
    EntitlementGroup localEntitlementGroup = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = null;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = -1;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBCookie = new DBCookie();
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select affiliate_bank_id from customer where directory_id = ?");
      for (localEntitlementGroup = Entitlements.getChildrenByGroupType(paramInt, "ConsumerAdmin", localDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenByGroupType(paramInt, "ConsumerAdmin", localDBCookie))
      {
        j = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "USER").getGroup(0).getGroupId();
        if (Entitlements.getMembers(j).size() == 0)
        {
          i = 0;
        }
        else
        {
          str2 = ((EntitlementGroupMember)Entitlements.getMembers(j).get(0)).getId();
          try
          {
            i = Integer.parseInt(str2);
          }
          catch (Exception localException1)
          {
            i = 0;
          }
        }
        if (i > 0) {
          try
          {
            int n = 1;
            n = Profile.setStatementInt(localPreparedStatement, n, i, true);
            localResultSet = DBUtil.executeQuery(localPreparedStatement, "select affiliate_bank_id from customer where directory_id = ?");
            if (localResultSet.next())
            {
              k = localResultSet.getInt(1);
              if (m != k)
              {
                String str3 = String.valueOf(k);
                if (!localArrayList.contains(str3)) {
                  localArrayList.add(str3);
                }
                m = k;
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
  
  public static BusinessEmployees getFilteredBusinessEmployeesByIds(ArrayList paramArrayList, BusinessEmployee paramBusinessEmployee)
    throws ProfileException
  {
    String str1 = "CustomerAdapter.getBusinessEmployeesByIds";
    Profile.isInitialized();
    Connection localConnection = null;
    StringBuffer localStringBuffer1 = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    BusinessEmployees localBusinessEmployees = new BusinessEmployees(Locale.getDefault());
    if (paramArrayList.size() == 0) {
      return localBusinessEmployees;
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStringBuffer1 = new StringBuffer("select c.*, cd.*, be.* from customer c, customer_directory cd, business_employee be, business b where c.customer_type='1' and c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id");
      StringBuffer localStringBuffer2 = new StringBuffer();
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localStringBuffer2.append(str2);
        localStringBuffer2.append(",");
      }
      Profile.appendSQLSelectInt2(localStringBuffer1, "c.", "directory_id", localStringBuffer2.toString(), true);
      boolean bool = true;
      if (paramBusinessEmployee != null)
      {
        bool = Profile.appendSQLSelectString2(localStringBuffer1, "c.", "low_first_name", paramBusinessEmployee.getFirstNameLowerCase(), true, false, bool);
        bool = Profile.appendSQLSelectString2(localStringBuffer1, "c.", "low_middle_name", paramBusinessEmployee.getMiddleNameLowerCase(), true, false, bool);
        bool = Profile.appendSQLSelectString2(localStringBuffer1, "c.", "low_last_name", paramBusinessEmployee.getLastNameLowerCase(), true, false, bool);
      }
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer1.toString());
      while (localResultSet.next()) {
        localBusinessEmployees.add(jdMethod_new(localResultSet));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localStatement, localResultSet);
    }
    return localBusinessEmployees;
  }
  
  public static boolean getInfoForAuditing(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "getInfoForAuditing";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = paramSecureUser.getUserName();
    if (str2 == null) {
      return false;
    }
    str2 = str2.toLowerCase();
    String str3 = paramSecureUser.getBusinessCustId();
    String str4 = paramSecureUser.getBankID();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      if ((SignonSettings.allowDuplicateUserNames()) && (str3 != null))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.directory_id from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.bank_id=? and c.user_name=? and cd.cust_id = ?");
        Profile.setStatementString(localPreparedStatement, 1, str4, "BANK_ID", false);
        Profile.setStatementString(localPreparedStatement, 2, str2, "USER_NAME", false);
        Profile.setStatementString(localPreparedStatement, 3, str3, "CUST_ID", false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.directory_id,b.affiliate_bank_id,password,password_status,password_fail_count,password_lockout_time,cust_id,c.processor_pin, reminder,REMINDER2,pwd_reminder,PWD_REMINDER2 from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.bank_id=? and c.user_name=? and cd.account_status=1 and cd.cust_id = ?");
        if (!localResultSet.next())
        {
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localPreparedStatement = null;
          localResultSet = null;
          bool = false;
          return bool;
        }
        i = localResultSet.getInt("DIRECTORY_ID");
        paramSecureUser.setProfileID(i);
        getBusinessInfo(localConnection, paramSecureUser);
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        paramSecureUser.updateUserName(str2);
        bool = true;
        return bool;
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select directory_id from customer where bank_id=? and user_name=?");
      Profile.setStatementString(localPreparedStatement, 1, str4, "BANK_ID", false);
      Profile.setStatementString(localPreparedStatement, 2, str2, "USER_NAME", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select directory_id from customer where bank_id=? and user_name=?");
      if (!localResultSet.next())
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        bool = false;
        return bool;
      }
      int i = localResultSet.getInt("DIRECTORY_ID");
      paramSecureUser.setProfileID(i);
      getBusinessInfo(localConnection, paramSecureUser);
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT PRIMARY_USER_ID FROM CUSTOMER_REL where DIRECTORY_ID = ?");
      Profile.setStatementInt(localPreparedStatement, 1, i, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT PRIMARY_USER_ID FROM CUSTOMER_REL where DIRECTORY_ID = ?");
      if (localResultSet.next()) {
        paramSecureUser.setPrimaryUserID(localResultSet.getInt(1));
      } else {
        paramSecureUser.setPrimaryUserID(paramSecureUser.getProfileID());
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      paramSecureUser.updateUserName(str2);
      boolean bool = true;
      return bool;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return false;
  }
  
  public static void addContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    Object localObject1 = null;
    String str = "com.ffusion.efs.adapters.CustomerAdapter.addContactPoint(SecureUser, ContactPoint, HashMap)";
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_for(localConnection, paramSecureUser, paramContactPoint, paramHashMap);
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
  
  public static void addContactPoints(SecureUser paramSecureUser, ContactPoints paramContactPoints, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    String str = "com.ffusion.efs.adapters.CustomerAdapter.addContactPoints(SecureUser, ContactPoints, HashMap)";
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      for (int i = 0; i < paramContactPoints.size(); i++)
      {
        ContactPoint localContactPoint = (ContactPoint)paramContactPoints.get(i);
        jdMethod_for(localConnection, paramSecureUser, localContactPoint, paramHashMap);
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
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "INSERT INTO contact_points( contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure ) VALUES ( ?, ?, ?, ?, ?, ? )");
      int i = DBUtil.getNextId(paramConnection, Profile.dbType, "CONTACT_POINT_ID");
      localPreparedStatement.setInt(1, i);
      int j = paramContactPoint.getDirectoryID();
      localPreparedStatement.setInt(2, j);
      localPreparedStatement.setString(3, paramContactPoint.getName());
      localPreparedStatement.setInt(4, paramContactPoint.getContactPointType());
      localPreparedStatement.setString(5, paramContactPoint.getAddress());
      localPreparedStatement.setString(6, paramContactPoint.getSecure() ? "T" : "F");
      DBUtil.execute(localPreparedStatement, "INSERT INTO contact_points( contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure ) VALUES ( ?, ?, ?, ?, ?, ? )");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static void modifyContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "com.ffusion.efs.adapters.CustomerAdapter.modifyContactPoint(SecureUser, ContactPoint, HashMap)";
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update contact_points set contact_point_name = ?, contact_point_type = ?, address = ? where contact_point_id = ? and directory_id = ?");
      localPreparedStatement.setString(1, paramContactPoint.getName());
      localPreparedStatement.setInt(2, paramContactPoint.getContactPointType());
      localPreparedStatement.setString(3, paramContactPoint.getAddress());
      localPreparedStatement.setInt(4, paramContactPoint.getContactPointID());
      int i = paramContactPoint.getDirectoryID();
      localPreparedStatement.setInt(5, i);
      int j = DBUtil.executeUpdate(localPreparedStatement, "update contact_points set contact_point_name = ?, contact_point_type = ?, address = ? where contact_point_id = ? and directory_id = ?");
      if (j == 0) {
        throw new ProfileException(str, 5050, "The contact point for to be modified does not exist in the repository.");
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void deleteContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str = "com.ffusion.efs.adapters.CustomerAdapter.modifyContactPoint(SecureUser, ContactPoint, HashMap)";
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from contact_points where contact_point_id = ? and directory_id = ?");
      localPreparedStatement.setInt(1, paramContactPoint.getContactPointID());
      int i = paramContactPoint.getDirectoryID();
      localPreparedStatement.setInt(2, i);
      int j = DBUtil.executeUpdate(localPreparedStatement, "delete from contact_points where contact_point_id = ? and directory_id = ?");
      if (j == 0) {
        throw new ProfileException(str, 5050, "The contact point to be deleted does not exist in the repository.");
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static ContactPoints getContactPoints(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ContactPoints localContactPoints = null;
    String str = "com.ffusion.efs.adapters.CustomerAdapter.modifyContactPoint(SecureUser, ContactPoint, HashMap)";
    Locale localLocale = null;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      UserLocale localUserLocale = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = localUserLocale.getLocale();
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure from contact_points where directory_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure from contact_points where directory_id = ?");
      localContactPoints = jdMethod_for(localResultSet, localLocale);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localContactPoints;
  }
  
  public static ContactPoint getContactPoint(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = "com.ffusion.efs.adapters.CustomerAdapter.modifyContactPoint(SecureUser, ContactPoint, HashMap)";
    ContactPoint localContactPoint = null;
    Locale localLocale = null;
    Object localObject1;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject1 = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = ((UserLocale)localObject1).getLocale();
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure from contact_points where contact_point_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select contact_point_id, directory_id, contact_point_name, contact_point_type, address, secure from contact_points where contact_point_id = ?");
      localObject1 = jdMethod_for(localResultSet, localLocale);
      if (((ContactPoints)localObject1).size() > 0) {
        localContactPoint = (ContactPoint)((ContactPoints)localObject1).get(0);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    return localContactPoint;
  }
  
  private static ContactPoints jdMethod_for(ResultSet paramResultSet, Locale paramLocale)
    throws SQLException
  {
    ContactPoints localContactPoints = new ContactPoints(paramLocale);
    while (paramResultSet.next())
    {
      ContactPoint localContactPoint = new ContactPoint(paramLocale);
      localContactPoints.add(localContactPoint);
      localContactPoint.setContactPointID(paramResultSet.getInt(1));
      localContactPoint.setDirectoryID(paramResultSet.getInt(2));
      localContactPoint.setName(paramResultSet.getString(3));
      localContactPoint.setContactPointType(paramResultSet.getInt(4));
      localContactPoint.setAddress(paramResultSet.getString(5));
      String str = paramResultSet.getString(6);
      boolean bool = false;
      if (str.equals("T")) {
        bool = true;
      }
      localContactPoint.setSecure(bool);
    }
    return localContactPoints;
  }
  
  private static void jdMethod_for(ExtendABean paramExtendABean, Timestamp paramTimestamp1, Timestamp paramTimestamp2, Locale paramLocale)
  {
    if (paramTimestamp2 != null) {
      paramExtendABean.put("CREATE_DATE", new com.ffusion.beans.DateTime(paramTimestamp2, paramLocale));
    }
    if ((paramTimestamp1 != null) && (!paramTimestamp1.equals(paramTimestamp2))) {
      paramExtendABean.put("LAST_ACTIVE_DATE", new com.ffusion.beans.DateTime(paramTimestamp1, paramLocale));
    }
  }
  
  public static SecureUser getSecureUserByProfileID(int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "CustomerAdapter.getSecureUserByProfileID";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    SecureUser localSecureUser = new SecureUser();
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select c.*,cd.*, b.* from customer c, customer_directory cd, business b, business_employee be where c.directory_id=be.directory_id and be.business_id=b.business_id and b.directory_id=cd.directory_id and c.directory_id=?");
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        localSecureUser.setProfileID(String.valueOf(localResultSet.getInt("directory_id")));
        localSecureUser.setId(Profile.getRSString(localResultSet, "cust_id"));
        localSecureUser.setUserType(1);
        localSecureUser.setAffiliateID(localResultSet.getInt("affiliate_bank_id"));
        localSecureUser.setBankID(Profile.getRSString(localResultSet, "bank_id"));
        localSecureUser.setBusinessID(String.valueOf(localResultSet.getInt("business_id")));
        localSecureUser.setBusinessName(Profile.getRSString(localResultSet, "business_name"));
        localSecureUser.setBusinessCIF(Profile.getRSString(localResultSet, "business_CIF"));
        localSecureUser.setBusinessCustId(localResultSet.getString("cust_id"));
      }
      else
      {
        localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.directory_id=?");
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        if (localResultSet.next())
        {
          localSecureUser.setProfileID(String.valueOf(localResultSet.getInt("directory_id")));
          localSecureUser.setId(Profile.getRSString(localResultSet, "cust_id"));
          localSecureUser.setUserType(1);
          localSecureUser.setAffiliateID(localResultSet.getInt("affiliate_bank_id"));
          localSecureUser.setBankID(Profile.getRSString(localResultSet, "bank_id"));
          int i = localResultSet.getInt("USER_TYPE");
          if (i == 2)
          {
            localStringBuffer = new StringBuffer("select c.*,cd.* from customer c, customer_directory cd, CUSTOMER_REL cr where c.directory_id=cd.directory_id and c.directory_id=cr.PRIMARY_USER_ID and cr.DIRECTORY_ID = ?");
            DBUtil.closeAll(localPreparedStatement, localResultSet);
            localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
            Profile.setStatementInt(localPreparedStatement, 1, paramInt, false);
            localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
            if (localResultSet.next()) {
              localSecureUser.setPrimaryUserID(localResultSet.getInt("directory_id"));
            }
          }
          else
          {
            localSecureUser.setPrimaryUserID(localSecureUser.getProfileID());
          }
        }
        else
        {
          throw new ProfileException(str, 7, "The user could not be found in the database.");
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select bpw_fi_id, currency_code from affiliate_bank where affiliate_bank_id=?");
      localPreparedStatement.setInt(1, localSecureUser.getAffiliateIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select bpw_fi_id, currency_code from affiliate_bank where affiliate_bank_id=?");
      if (localResultSet.next())
      {
        localSecureUser.setBPWFIID(localResultSet.getString("bpw_fi_id"));
        localSecureUser.setBaseCurrency(localResultSet.getString("currency_code"));
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
    return localSecureUser;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.CustomerAdapter
 * JD-Core Version:    0.7.0.1
 */