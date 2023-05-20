package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.ExtendableTableUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class AffiliateBankAdapter
  implements ProfileDefines
{
  public static final String AFFILIATEBANKADAPTER_SETTINGS_TAG = "AFFILIATEBANKADAPTER_SETTINGS";
  public static final String DB_PROPERTIES_TAG = "DB_PROPERTIES";
  private static final String ahy = "SELECT * FROM affiliate_bank WHERE bank_id = ?";
  private static final String ahu = "SELECT * FROM affiliate_bank WHERE affiliate_bank_id = ?";
  private static final String ahC = "SELECT * FROM affiliate_bank WHERE bpw_fi_id = ?";
  private static final String ahx = "INSERT INTO affiliate_bank ( affiliate_bank_id, affiliate_name, bank_id, bpw_fi_id, bank_type, currency_code, BRAND_ID, corporate_url, consumer_url";
  private static final String ahJ = "DELETE FROM affiliate_bank WHERE affiliate_bank_id = ?";
  private static final String ahq = "UPDATE affiliate_bank SET affiliate_name = ?, bank_id = ?, bank_type = ?, currency_code = ?, BRAND_ID = ?, corporate_url = ?, consumer_url = ?";
  private static final String ahp = " WHERE affiliate_bank_id = ?";
  private static final String ahM = "Y";
  private static final String ahv = "N";
  private static final String ahP = "affiliate_bank_id";
  private static final String ahB = "cut_off_type";
  private static final String ahD = "create_empty_file";
  private static final String ahz = "process_on_holidays";
  private static final String ahw = "affiliate_bank_id";
  private static final String ahs = "cut_off_type";
  private static final String ahI = "day_of_week";
  private static final String ahL = "time_of_day";
  private static final String ahO = "one_time_extension";
  private static final String ahK = "active";
  private static final String ahA = "affiliate_bank_id, cut_off_type, create_empty_file, process_on_holidays";
  private static final String ahr = "affiliate_bank_id, cut_off_type, day_of_week, time_of_day, one_time_extension, active";
  private static final String ahN = "SELECT affiliate_bank_id, cut_off_type, create_empty_file, process_on_holidays";
  private static final String ahE = " FROM affil_cutoff_defn WHERE affiliate_bank_id=? AND cut_off_type=?";
  private static final String ahQ = "SELECT affiliate_bank_id, cut_off_type, day_of_week, time_of_day, one_time_extension, active";
  private static final String ahG = " FROM affil_cutoff_times WHERE affiliate_bank_id=? AND cut_off_type=?";
  private static final String ahF = "INSERT INTO affil_cutoff_defn ( affiliate_bank_id, cut_off_type, create_empty_file, process_on_holidays";
  private static final String aho = "DELETE FROM affil_cutoff_defn WHERE affiliate_bank_id=? AND cut_off_type=?";
  private static final String ahH = "INSERT INTO affil_cutoff_times ( affiliate_bank_id, cut_off_type, day_of_week, time_of_day, one_time_extension, active";
  private static final String aht = "DELETE FROM affil_cutoff_times WHERE affiliate_bank_id=? AND cut_off_type=?";
  
  public static AffiliateBanks getAffiliateBankNames(SecureUser paramSecureUser)
    throws ProfileException
  {
    return getAffiliateBankNames(paramSecureUser, null);
  }
  
  public static AffiliateBanks getAffiliateBankNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.getAffiliateBankNames";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    AffiliateBanks localAffiliateBanks = new AffiliateBanks(paramSecureUser.getLocale());
    Profile.isInitialized();
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT * FROM affiliate_bank WHERE bank_id = ?");
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramSecureUser.getBankID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      jdMethod_for(localResultSet, localAffiliateBanks);
      for (int i = 0; i < localAffiliateBanks.size(); i++)
      {
        AffiliateBank localAffiliateBank = (AffiliateBank)localAffiliateBanks.get(i);
        getBankSetting(localConnection, localAffiliateBank, paramHashMap);
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
    return localAffiliateBanks;
  }
  
  public static AffiliateBank getAffiliateBankByID(SecureUser paramSecureUser, int paramInt)
    throws ProfileException
  {
    return getAffiliateBankByID(paramSecureUser, paramInt, null);
  }
  
  public static AffiliateBank getAffiliateBankByID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.getAffiliateBankByID";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    AffiliateBank localAffiliateBank = new AffiliateBank(Locale.getDefault());
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM affiliate_bank WHERE affiliate_bank_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM affiliate_bank WHERE affiliate_bank_id = ?");
      if (localResultSet.next())
      {
        jdMethod_for(localResultSet, localAffiliateBank);
        getBankSetting(localConnection, localAffiliateBank, paramHashMap);
      }
      else
      {
        throw new ProfileException(str, 2, "");
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
    return localAffiliateBank;
  }
  
  public static AffiliateBank getAffiliateBankByBPWID(SecureUser paramSecureUser, String paramString)
    throws ProfileException
  {
    return getAffiliateBankByBPWID(paramSecureUser, paramString, null);
  }
  
  public static AffiliateBank getAffiliateBankByBPWID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.getAffiliateBankByBPWID";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    AffiliateBank localAffiliateBank = new AffiliateBank(Locale.getDefault());
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT * FROM affiliate_bank WHERE bpw_fi_id = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT * FROM affiliate_bank WHERE bpw_fi_id = ?");
      if (localResultSet.next())
      {
        jdMethod_for(localResultSet, localAffiliateBank);
        getBankSetting(localConnection, localAffiliateBank, paramHashMap);
      }
      else
      {
        throw new ProfileException(str, 2, "");
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
    return localAffiliateBank;
  }
  
  public static AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser)
    throws ProfileException
  {
    AffiliateBanks localAffiliateBanks = getAffiliateBankNames(paramSecureUser, null);
    return localAffiliateBanks;
  }
  
  public static AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    AffiliateBanks localAffiliateBanks = getAffiliateBankNames(paramSecureUser, paramHashMap);
    return localAffiliateBanks;
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, AffiliateBanks paramAffiliateBanks)
    throws Exception
  {
    while (paramResultSet.next())
    {
      AffiliateBank localAffiliateBank = paramAffiliateBanks.add();
      jdMethod_for(paramResultSet, localAffiliateBank);
    }
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, AffiliateBank paramAffiliateBank)
    throws Exception
  {
    paramAffiliateBank.setAffiliateBankID(paramResultSet.getInt("affiliate_bank_id"));
    paramAffiliateBank.setAffiliateBankName(Profile.getRSString(paramResultSet, "affiliate_name"));
    paramAffiliateBank.setBankID(Profile.getRSString(paramResultSet, "bank_id"));
    paramAffiliateBank.setFIBPWID(Profile.getRSString(paramResultSet, "bpw_fi_id"));
    paramAffiliateBank.setBankTypeValue(paramResultSet.getInt("bank_type"));
    paramAffiliateBank.setCurrencyCode(paramResultSet.getString("currency_code"));
    paramAffiliateBank.setBrandID(paramResultSet.getString("BRAND_ID"));
    paramAffiliateBank.setCorporateURL(paramResultSet.getString("corporate_url"));
    paramAffiliateBank.setConsumerURL(paramResultSet.getString("consumer_url"));
    Profile.setXBeanFields(paramResultSet, paramAffiliateBank, "affiliate_bank");
  }
  
  private static void jdMethod_for(Connection paramConnection, AffiliateBank paramAffiliateBank, String paramString)
    throws ProfileException
  {
    Object localObject;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      jdMethod_for(paramConnection, paramAffiliateBank, "en_US");
      localObject = paramAffiliateBank.getLanguages();
      while ((localObject != null) && (((Iterator)localObject).hasNext()))
      {
        paramString = (String)((Iterator)localObject).next();
        jdMethod_for(paramConnection, paramAffiliateBank, paramString);
      }
    }
    else if (paramAffiliateBank.getEmailSubject(paramString) != null)
    {
      localObject = Integer.toString(paramAffiliateBank.getAffiliateBankID());
      String str = "";
      if (!"en_US".equalsIgnoreCase(paramString.trim())) {
        str = "_" + paramString.trim();
      }
      ObjectPropertyAdapter.addObjectProperty(paramConnection, 1, (String)localObject, "EMAIL_SUBJECT" + str, paramAffiliateBank.getEmailSubject(paramString));
      ObjectPropertyAdapter.addObjectProperty(paramConnection, 1, (String)localObject, "EMAIL_MEMO" + str, paramAffiliateBank.getEmailMemo(paramString));
      ObjectPropertyAdapter.addObjectProperty(paramConnection, 1, (String)localObject, "EMAIL_FROM" + str, paramAffiliateBank.getEmailFrom(paramString));
    }
  }
  
  private static void jdMethod_int(AffiliateBank paramAffiliateBank, String paramString)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.addAffiliateBankEmailProperties";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_for(localConnection, paramAffiliateBank, paramString);
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
  
  private static void jdMethod_int(Connection paramConnection, AffiliateBank paramAffiliateBank, String paramString)
    throws ProfileException
  {
    String str1 = Integer.toString(paramAffiliateBank.getAffiliateBankID());
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      ObjectPropertyAdapter.deleteObjectPropertiesFromRoot(paramConnection, 1, str1, "EMAIL_SUBJECT");
      ObjectPropertyAdapter.deleteObjectPropertiesFromRoot(paramConnection, 1, str1, "EMAIL_MEMO");
      ObjectPropertyAdapter.deleteObjectPropertiesFromRoot(paramConnection, 1, str1, "EMAIL_FROM");
      jdMethod_for(paramConnection, paramAffiliateBank, null);
    }
    else
    {
      String str2 = "";
      if (!"en_US".equalsIgnoreCase(paramString.trim())) {
        str2 = "_" + paramString.trim();
      }
      ObjectPropertyAdapter.deleteObjectProperty(paramConnection, 1, str1, "EMAIL_SUBJECT" + str2);
      ObjectPropertyAdapter.deleteObjectProperty(paramConnection, 1, str1, "EMAIL_MEMO" + str2);
      ObjectPropertyAdapter.deleteObjectProperty(paramConnection, 1, str1, "EMAIL_FROM" + str2);
      jdMethod_for(paramConnection, paramAffiliateBank, paramString);
    }
  }
  
  private static void jdMethod_for(AffiliateBank paramAffiliateBank, String paramString)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.modifyAffiliateBankEmailProperties";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_int(localConnection, paramAffiliateBank, paramString);
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
  
  public static AffiliateBank addAffiliateBank(AffiliateBank paramAffiliateBank)
    throws ProfileException
  {
    String str1 = "AffiliateBankAdapter.addAffiliateBank";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("INSERT INTO affiliate_bank ( affiliate_bank_id, affiliate_name, bank_id, bpw_fi_id, bank_type, currency_code, BRAND_ID, corporate_url, consumer_url");
      Profile.appendSQLInsertColumns(localStringBuffer, "affiliate_bank", true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      int j = DBUtil.getNextId(localConnection, Profile.dbType, "affiliate_bank_id");
      paramAffiliateBank.setAffiliateBankID(j);
      i = Profile.setStatementInt(localPreparedStatement, i, j, false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getAffiliateBankName(), "affiliate_name", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getBankID(), "bank_id", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getFIBPWID(), "bpw_fi_id", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAffiliateBank.getBankTypeValue(), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getCurrencyCode(), "currency_code", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getBrandID(), "BRAND_ID", false);
      String str2 = paramAffiliateBank.getCorporateURL();
      String str3 = paramAffiliateBank.getConsumerURL();
      i = Profile.setStatementString(localPreparedStatement, i, str2 == null ? null : str2.trim(), "corporate_url", false);
      i = Profile.setStatementString(localPreparedStatement, i, str3 == null ? null : str3.trim(), "consumer_url", false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramAffiliateBank, "affiliate_bank", false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      jdMethod_int(paramAffiliateBank, null);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramAffiliateBank;
  }
  
  public static void deleteAffiliateBank(AffiliateBank paramAffiliateBank)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.deleteAffiliateBank";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE FROM affiliate_bank WHERE affiliate_bank_id = ?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramAffiliateBank.getAffiliateBankID(), false);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE FROM affiliate_bank WHERE affiliate_bank_id = ?");
      DBUtil.commit(localConnection);
      ObjectPropertyAdapter.deleteObjectPropertiesFromRoot(1, Integer.toString(paramAffiliateBank.getAffiliateBankID()), "EMAIL_SUBJECT");
      ObjectPropertyAdapter.deleteObjectPropertiesFromRoot(1, Integer.toString(paramAffiliateBank.getAffiliateBankID()), "EMAIL_MEMO");
      ObjectPropertyAdapter.deleteObjectPropertiesFromRoot(1, Integer.toString(paramAffiliateBank.getAffiliateBankID()), "EMAIL_FROM");
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
  
  public static void modifyAffiliateBank(AffiliateBank paramAffiliateBank)
    throws ProfileException
  {
    String str1 = "AffiliateBankAdapter.modifyAffiliateBank";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Profile.isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("UPDATE affiliate_bank SET affiliate_name = ?, bank_id = ?, bank_type = ?, currency_code = ?, BRAND_ID = ?, corporate_url = ?, consumer_url = ?");
      Profile.appendSQLUpdateColumns(localStringBuffer, "affiliate_bank", paramAffiliateBank);
      localStringBuffer.append(" WHERE affiliate_bank_id = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getAffiliateBankName(), "affiliate_name", false, false, false, true);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getBankID(), "bank_id", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAffiliateBank.getBankTypeValue(), false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getCurrencyCode(), "currency_code", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramAffiliateBank.getBrandID(), "BRAND_ID", false);
      String str2 = paramAffiliateBank.getCorporateURL();
      String str3 = paramAffiliateBank.getConsumerURL();
      i = Profile.setStatementString(localPreparedStatement, i, str2 == null ? null : str2.trim(), "corporate_url", false);
      i = Profile.setStatementString(localPreparedStatement, i, str3 == null ? null : str3.trim(), "consumer_url", false);
      i = Profile.setStatementValues(localPreparedStatement, i, paramAffiliateBank, "affiliate_bank", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramAffiliateBank.getAffiliateBankID(), false);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      jdMethod_for(paramAffiliateBank, null);
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
  
  public static CutOffDefinition getCutOffDefinition(int paramInt1, int paramInt2)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.getCutOffDefinition";
    CutOffDefinition localCutOffDefinition = new CutOffDefinition();
    Profile.isInitialized();
    Connection localConnection1 = null;
    Connection localConnection2 = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localConnection1 = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer1 = new StringBuffer("SELECT affiliate_bank_id, cut_off_type, create_empty_file, process_on_holidays");
      ExtendableTableUtil.appendSQLSelectColumns(localStringBuffer1, "affil_cutoff_defn");
      localStringBuffer1.append(" ");
      localStringBuffer1.append(" FROM affil_cutoff_defn WHERE affiliate_bank_id=? AND cut_off_type=?");
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection1, localStringBuffer1.toString());
      localConnection2 = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer2 = new StringBuffer("SELECT affiliate_bank_id, cut_off_type, day_of_week, time_of_day, one_time_extension, active");
      ExtendableTableUtil.appendSQLSelectColumns(localStringBuffer2, "affil_cutoff_times");
      localStringBuffer2.append(" ");
      localStringBuffer2.append(" FROM affil_cutoff_times WHERE affiliate_bank_id=? AND cut_off_type=?");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection2, localStringBuffer2.toString());
      localPreparedStatement1.setInt(1, paramInt1);
      localPreparedStatement1.setInt(2, paramInt2);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer1.toString());
      localPreparedStatement2.setInt(1, paramInt1);
      localPreparedStatement2.setInt(2, paramInt2);
      localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, localStringBuffer2.toString());
      localCutOffDefinition = jdMethod_for(localResultSet1, localResultSet2);
    }
    catch (Exception localException)
    {
      if ((localException instanceof ProfileException)) {
        throw ((ProfileException)localException);
      }
      DebugLog.throwing("An error has occurred while accessing the database in order to retrieve cut-off definitions.", localException);
      throw new ProfileException(localException, str, 2, "An error has occurred while accessing the database in order to retrieve cut-off definitions.");
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeResultSet(localResultSet2);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection1);
      DBUtil.returnConnection(Profile.poolName, localConnection2);
    }
    return localCutOffDefinition;
  }
  
  public static void setCutOffDefinition(int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.setCutOffDefinition";
    CutOffDefinition localCutOffDefinition = getCutOffDefinition(paramInt1, paramInt2);
    if (localCutOffDefinition == null) {
      jdMethod_for(paramInt1, paramInt2, paramCutOffDefinition);
    } else {
      jdMethod_int(paramInt1, paramInt2, paramCutOffDefinition);
    }
  }
  
  private static CutOffDefinition jdMethod_for(ResultSet paramResultSet1, ResultSet paramResultSet2)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.getCutOffDefinitionFromResultSet";
    CutOffDefinition localCutOffDefinition = null;
    try
    {
      if (paramResultSet1.next())
      {
        localCutOffDefinition = new CutOffDefinition();
        localCutOffDefinition.setFIId(String.valueOf(paramResultSet1.getInt("affiliate_bank_id")));
        if (paramResultSet1.getString("create_empty_file").compareTo("Y") == 0) {
          localCutOffDefinition.setCreateEmptyFileValue(true);
        } else {
          localCutOffDefinition.setCreateEmptyFileValue(false);
        }
        if (paramResultSet1.getString("process_on_holidays").compareTo("Y") == 0) {
          localCutOffDefinition.setProcessOnHolidaysValue(true);
        } else {
          localCutOffDefinition.setProcessOnHolidaysValue(false);
        }
        CutOffTimes localCutOffTimes = new CutOffTimes();
        while (paramResultSet2.next())
        {
          CutOffTime localCutOffTime = localCutOffTimes.add();
          localCutOffTime.setDayOfWeekValue(paramResultSet2.getInt("day_of_week"));
          localCutOffTime.setTimeOfDay(paramResultSet2.getString("time_of_day"));
          localCutOffTime.setExtension(paramResultSet2.getString("one_time_extension"));
          if (paramResultSet2.getString("active").compareTo("Y") == 0) {
            localCutOffTime.setActiveValue(true);
          } else {
            localCutOffTime.setActiveValue(false);
          }
        }
        localCutOffDefinition.setCutOffs(localCutOffTimes);
      }
    }
    catch (Exception localException)
    {
      throw new ProfileException(localException, str, 2, "An error has occurred while accessing the database in order to retrieve cut-off definitions.");
    }
    return localCutOffDefinition;
  }
  
  private static void jdMethod_for(int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.addCutOffDefinition";
    Profile.isInitialized();
    Connection localConnection1 = null;
    Connection localConnection2 = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localConnection1 = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer1 = new StringBuffer("INSERT INTO affil_cutoff_defn ( affiliate_bank_id, cut_off_type, create_empty_file, process_on_holidays");
      ExtendableTableUtil.appendSQLInsertColumns(localStringBuffer1, "affil_cutoff_defn", true);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection1, localStringBuffer1.toString());
      int i = 1;
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement1, i, paramInt1, false);
      i = ExtendableTableUtil.setStatementInt(localPreparedStatement1, i, paramInt2, false);
      if (paramCutOffDefinition.getCreateEmptyFileValue()) {
        i = ExtendableTableUtil.setStatementString(localPreparedStatement1, i, "Y", false);
      } else {
        i = ExtendableTableUtil.setStatementString(localPreparedStatement1, i, "N", false);
      }
      if (paramCutOffDefinition.getProcessOnHolidaysValue()) {
        i = ExtendableTableUtil.setStatementString(localPreparedStatement1, i, "Y", false);
      } else {
        i = ExtendableTableUtil.setStatementString(localPreparedStatement1, i, "N", false);
      }
      i = ExtendableTableUtil.setStatementValues(localPreparedStatement1, i, paramCutOffDefinition, "affil_cutoff_defn", false);
      DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer1.toString());
      CutOffTimes localCutOffTimes = paramCutOffDefinition.getCutOffs();
      Iterator localIterator = localCutOffTimes.iterator();
      while (localIterator.hasNext())
      {
        CutOffTime localCutOffTime = (CutOffTime)localIterator.next();
        localConnection2 = DBUtil.getConnection(Profile.poolName, true, 2);
        StringBuffer localStringBuffer2 = new StringBuffer("INSERT INTO affil_cutoff_times ( affiliate_bank_id, cut_off_type, day_of_week, time_of_day, one_time_extension, active");
        ExtendableTableUtil.appendSQLInsertColumns(localStringBuffer2, "affil_cutoff_times", true);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection2, localStringBuffer2.toString());
        int j = 1;
        j = ExtendableTableUtil.setStatementInt(localPreparedStatement2, j, paramInt1, false);
        j = ExtendableTableUtil.setStatementInt(localPreparedStatement2, j, paramInt2, false);
        j = ExtendableTableUtil.setStatementInt(localPreparedStatement2, j, localCutOffTime.getDayOfWeekValue(), false);
        j = ExtendableTableUtil.setStatementString(localPreparedStatement2, j, localCutOffTime.getTimeOfDay(), false);
        j = ExtendableTableUtil.setStatementString(localPreparedStatement2, j, localCutOffTime.getExtension(), false);
        if (localCutOffTime.getActiveValue()) {
          j = ExtendableTableUtil.setStatementString(localPreparedStatement2, j, "Y", false);
        } else {
          j = ExtendableTableUtil.setStatementString(localPreparedStatement2, j, "N", false);
        }
        j = ExtendableTableUtil.setStatementValues(localPreparedStatement2, j, localCutOffTime, "affil_cutoff_times", false);
        DBUtil.executeUpdate(localPreparedStatement2, localStringBuffer2.toString());
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("An error has occurred while accessing the database in order to add cut-off definitions.", localException);
      throw new ProfileException(localException, str, 2, "An error has occurred while accessing the database in order to add cut-off definitions.");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection1);
      DBUtil.returnConnection(Profile.poolName, localConnection2);
    }
  }
  
  private static void jdMethod_int(int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition)
    throws ProfileException
  {
    String str = "AffiliateBankAdapter.addCutOffDefinition";
    Profile.isInitialized();
    Connection localConnection1 = null;
    Connection localConnection2 = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localConnection2 = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection2, "DELETE FROM affil_cutoff_times WHERE affiliate_bank_id=? AND cut_off_type=?");
      localPreparedStatement2.setInt(1, paramInt1);
      localPreparedStatement2.setInt(2, paramInt2);
      DBUtil.executeUpdate(localPreparedStatement2, "DELETE FROM affil_cutoff_times WHERE affiliate_bank_id=? AND cut_off_type=?");
      localConnection1 = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection1, "DELETE FROM affil_cutoff_defn WHERE affiliate_bank_id=? AND cut_off_type=?");
      localPreparedStatement1.setInt(1, paramInt1);
      localPreparedStatement1.setInt(2, paramInt2);
      DBUtil.executeUpdate(localPreparedStatement1, "DELETE FROM affil_cutoff_defn WHERE affiliate_bank_id=? AND cut_off_type=?");
      jdMethod_for(paramInt1, paramInt2, paramCutOffDefinition);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("An error has occurred while accessing the database in order to add cut-off definitions.", localException);
      throw new ProfileException(localException, str, 2, "An error has occurred while accessing the database in order to add cut-off definitions.");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection1);
      DBUtil.returnConnection(Profile.poolName, localConnection2);
    }
  }
  
  protected static void getBankSetting(Connection paramConnection, AffiliateBank paramAffiliateBank)
    throws ProfileException
  {
    getBankSetting(paramConnection, paramAffiliateBank, null);
  }
  
  protected static void getBankSetting(Connection paramConnection, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "AffiliateBankAdapter.getBankSetting";
    Locale localLocale = Locale.getDefault();
    String str2 = "en_US";
    if (paramHashMap == null) {
      str2 = null;
    }
    if ((paramHashMap != null) && (paramHashMap.containsKey("LANGUAGE")))
    {
      str2 = (String)paramHashMap.get("LANGUAGE");
      if ((str2 != null) && (str2.trim().length() == 0)) {
        str2 = null;
      }
    }
    if (str2 != null) {
      paramAffiliateBank.setCurrentLanguage(str2);
    }
    HashMap localHashMap = ObjectPropertyAdapter.getObjectPropertyValues(paramConnection, 1, String.valueOf(paramAffiliateBank.getAffiliateBankID()));
    if (localHashMap != null)
    {
      if (str2 == null)
      {
        localObject1 = ObjectPropertyAdapter.getObjectPropertyValuesFromRoot(paramConnection, 1, String.valueOf(paramAffiliateBank.getAffiliateBankID()), "EMAIL_SUBJECT");
        if (localObject1 != null)
        {
          localObject2 = ((HashMap)localObject1).keySet().iterator();
          while ((localObject2 != null) && (((Iterator)localObject2).hasNext()))
          {
            String str3 = (String)((Iterator)localObject2).next();
            String str4 = null;
            String str5 = "";
            if (str3.equals("EMAIL_SUBJECT"))
            {
              str4 = "en_US";
            }
            else
            {
              str4 = str3.substring("EMAIL_SUBJECT".length() + 1);
              str5 = "_" + str4;
            }
            paramAffiliateBank.setEmailSubject(str4, (String)localHashMap.get("EMAIL_SUBJECT" + str5));
            paramAffiliateBank.setEmailMemo(str4, (String)localHashMap.get("EMAIL_MEMO" + str5));
            paramAffiliateBank.setEmailFrom(str4, (String)localHashMap.get("EMAIL_FROM" + str5));
          }
        }
      }
      else
      {
        paramAffiliateBank.setEmailSubject("en_US", (String)localHashMap.get("EMAIL_SUBJECT"));
        paramAffiliateBank.setEmailMemo("en_US", (String)localHashMap.get("EMAIL_MEMO"));
        paramAffiliateBank.setEmailFrom("en_US", (String)localHashMap.get("EMAIL_FROM"));
        if (!str2.trim().equalsIgnoreCase("en_US"))
        {
          paramAffiliateBank.setEmailSubject(str2.trim(), (String)localHashMap.get("EMAIL_SUBJECT_" + str2.trim()));
          paramAffiliateBank.setEmailMemo(str2.trim(), (String)localHashMap.get("EMAIL_MEMO_" + str2.trim()));
          paramAffiliateBank.setEmailFrom(str2.trim(), (String)localHashMap.get("EMAIL_FROM_" + str2.trim()));
        }
      }
      Object localObject1 = (String)localHashMap.get("TERMS_VERSION");
      Object localObject2 = (String)localHashMap.get("TERMS_DATE");
      if (localObject1 != null) {
        paramAffiliateBank.setTermsVersion((String)localObject1);
      } else {
        paramAffiliateBank.setTermsVersion(1);
      }
      try
      {
        if (localObject2 != null) {
          paramAffiliateBank.setTermsResetDate(new DateTime((String)localObject2, localLocale, "MM/dd/yyyy HH:mm:ss"));
        }
      }
      catch (Exception localException)
      {
        DebugLog.throwing("An error has occurred while setting terms reset date.", localException);
        throw new ProfileException(localException, str1, 2, "An error has occurred while setting terms reset date.");
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.AffiliateBankAdapter
 * JD-Core Version:    0.7.0.1
 */