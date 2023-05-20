package com.ffusion.efs.adapters.billpresentment;

import com.ffusion.beans.Contact;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.beans.billpresentment.Billers;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.ConsumerStatus;
import com.ffusion.beans.billpresentment.Consumers;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.beans.billpresentment.PaymentInfo;
import com.ffusion.beans.billpresentment.PaymentInfos;
import com.ffusion.beans.billpresentment.Publisher;
import com.ffusion.beans.billpresentment.Publishers;
import com.ffusion.beans.billpresentment.TCURL;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.efs.adapters.profile.ProfileUtil;
import com.ffusion.util.MultiException;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

public class OneViewAdapter
  implements OneViewDefines
{
  protected static String poolName = null;
  protected static Properties properties = null;
  protected static String dbType = null;
  private static final String jdField_new = "insert into t_publisher (PublisherId,OFXPublisherId,SPName,PublisherURL,StatusCode,ProxyUserId,ProxyPassword,CreateDate,StatusEffectiveDate) values(?,?,?,?,?,?,?,?,?)";
  private static final String jdField_long = "select * from T_Publisher";
  private static final String p = "select c.* from T_ContactInfo c, T_ContactRelationship cr where cr.ContactInfoId = c.ContactInfoId and cr.Id = ? and cr.IdType = ?";
  private static final String k = "update T_publisher set publisherurl=?,statuscode=?,spname=?,createdate=?,statuseffectiveDate=?,modifiedDate=? where publisherId=?";
  private static final String o = "update T_publisher set StatusCode='DELETE' where PublisherId=?";
  private static final String y = "insert into T_Consumer (ConsumerId,FirstName,MiddleName,LastName,SecurityName,TaxId,DateBirth,StatusCode,FeeAcctNumber,FeeAcctType,FeeAcctRTN,CreateDate) values (?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String jdField_null = "select StatusCode from T_Consumer where ConsumerId=?";
  private static final String m = "select ContactInfoId from T_ContactRelationship where ID=? and IDType=? and ContactType=?";
  private static final String jdField_char = "insert into T_ContactInfo (ContactInfoId,CreateDate,DayPhone,EvePhone,Fax,Email,Addr1,Addr2,Addr3,City,StateProv,PostalCode,Country,URL) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String r = "insert into T_ContactRelationship (Id,IdType,ContactType,ContactInfoId) values (?,?,?,?)";
  private static final String b = "select ConsumerId from FFI_ConsumerStatus where ConsumerId=?";
  private static final String l = "insert into FFI_ConsumerStatus (ConsumerId,TCId,TCAccepted,BillDueNotifyDesired,BillDueNotifyDays,NewBillNotifyDesired,AccountInfoNotifyDesired,NewBillerNotifyDesired) values (?,?,?,?,?,?,?,?)";
  private static final String q = "update FFI_ConsumerStatus set TCId=?,TCAccepted=?,BillDueNotifyDesired=?,BillDueNotifyDays=?,NewBillNotifyDesired=?,AccountInfoNotifyDesired=?,NewBillerNotifyDesired=?) where consumerId=?";
  private static final String w = "select PublisherId from T_Biller where BillerId=?";
  private static final String B = "select ConsumerId from T_ConsumerPubRelationship where ConsumerId=? and PublisherId=?";
  private static final String t = "Insert into T_ConsumerPubRelationship (ConsumerId,PublisherId,StatusCode,CreateDate,OFXUserId) values (?,?,?,?,?)";
  private static final String jdField_try = "select c.* from T_Consumer c where c.ConsumerId != 0";
  private static final String e = "select c.FirstName, c.LastName, c.ConsumerId from T_Consumer c where c.ConsumerId != 0";
  private static final String E = "select c.FirstName, c.LastName, c.ConsumerId from T_Consumer c, T_ConsumerPubRelationship cpr where c.ConsumerId = cpr.ConsumerId";
  private static final String jdField_goto = "select ContactInfoId from T_ContactRelationship where Id=? and IdType=?";
  private static final String j = "update T_ContactInfo set DayPhone=?,EvePhone=?,Fax=?,Email=?,Addr1=?,Addr2=?,Addr3=?,City=?,StateProv=?,PostalCode=?,Country=?,URL=?,ModifiedDate=? where contactInfoId=?";
  private static final String s = "update T_Consumer set FirstName=?,MiddleName=?,LastName=?,SecurityName=?,TaxId=?,DateBirth=?,StatusCode=?,FeeAcctNumber=?,FeeAcctType=?,FeeAcctRtn=?ModifiedDate=? where ConsumerId=?";
  private static final String jdField_case = "update T_Consumer set StatusCode=? where ConsumerId=?";
  private static final String v = "update T_ConsumerPubRelationship set StatusCode=?,ModifiedDate=? where ConsumerId=?";
  private static final String G = "update T_Consumer set StatusCode='STATUS_DELETE',ModifiedDate=? where ConsumerId=?";
  private static final String h = "select * from T_Biller";
  private static final String d = "update T_Biller set BillerName=?,PublisherId=?,StatusCode=?,RestrictMessage=?,BillerEnrollURL=?,BillerInfoURL=?,LogoURL=?,AccountFormat=?,AccountEditMask=?,HelpMessage=?,ValidateURL=? where BillerId=?";
  private static final String F = "select * from FFI_Biller where billerId=?";
  private static final String jdField_do = "update FFI_BILLER set PayeeIds=?,ShowMemo=?,Memo=? where BillerId=?";
  private static final String jdField_int = "select AccountId from T_Account where ConsumerId=? and BillerId=? and AccountNumber=? and (StatusCode='ACTIVE' or StatusCode='NEW' or StatusCode='PEND')";
  private static final String D = "insert into T_Account (AccountId,ConsumerId,AccountNumber,StatusCode,CreateDate,BillerId,NameAcctHeld,CurrAcctId,PreAuthToken,ReasonText) values (?,?,?,?,?,?,?,?,?,?)";
  private static final String jdField_byte = "insert into FFI_Account (AccountId,Nickname,PayeeId) values (?,?,?)";
  private static final String i = "select StatusCode from T_Account where AccountId=?";
  private static final String A = "update T_Account set StatusCode=?,ModifiedDate=? where AccountId=?";
  private static final String jdField_else = "update FFI_ACCOUNT set NickName=?,PayeeId=? where AccountId=?";
  public static final String SQL_UPDATE_ACCOUNT = "update T_Account set SATrnUID=?,SDTrnUID=?,statusCode=? where AccountId=?";
  public static final String SQL_UPDATE_ACCOUNT_NO_STATUS = "update T_Account set SATrnUID=?,SDTrnUID=? where AccountId=?";
  private static final String u = "select StatusCode from T_ConsumerPubRelationship where ConsumerId=? and PublisherId=?";
  private static final String c = "select a.*,f.* from T_Account a, FFI_Account f where a.AccountId = f.AccountId";
  private static final String C = "select * from T_BillSummary where ConsumerId=?";
  private static final String jdField_if = "select * from FFI_PaymentInfo where BillSummaryId=? order by PaymentDate";
  private static final String g = "update T_BillSummary set StatusCode=? where BillSummaryId=?";
  private static final String f = "update T_BillSummary set StatusCode=?,ViewDate=? where BillSummaryId=?";
  private static final String n = "insert into FFI_PaymentInfo (PaymentInfoId,BillSummaryId,PaymentRefId,PaymentAccount,PaymentDate,PaymentAmount) values(?,?,?,?,?,?)";
  private static final String a = "insert into FFI_TCUrl (TCId,TCURL,PrintableURL) values(?,?,?)";
  private static final String x = "select * from FFI_TCUrl where TCId=?";
  private static final String z = "update FFI_TCUrl set TCURL=?,PrintableURL=? where TCId=?";
  private static final String jdField_void = "update FFI_ConsumerStatus set TCId=0 where TCId=";
  private static final String jdField_for = "delete from FFI_TCUrl where TCId=";
  
  public static synchronized void initialize(Properties paramProperties)
    throws MultiException
  {
    String str = "Profile.initialize";
    if ((poolName != null) && (poolName.length() > 0)) {
      return;
    }
    try
    {
      properties = paramProperties;
      dbType = properties.getProperty("ConnectionType");
      if (dbType == null) {
        throw new Exception("DB type unknown");
      }
      poolName = ConnectionPool.init(properties);
    }
    catch (Exception localException)
    {
      handleError(localException, str, 0);
    }
  }
  
  public static Publisher addPublisher(Publisher paramPublisher)
    throws MultiException
  {
    String str = "OneViewAdapter.addPublisher";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      int i1 = DBUtil.getNextId(localConnection, dbType, "PUBLISHERID");
      if (paramPublisher.getSpName().length() == 0) {
        throw new MultiException(str, 6010, "Invalid Publisher Name");
      }
      if (paramPublisher.getPublisherURL().length() == 0) {
        throw new MultiException(str, 6011, "Invalid Publisher URL");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into t_publisher (PublisherId,OFXPublisherId,SPName,PublisherURL,StatusCode,ProxyUserId,ProxyPassword,CreateDate,StatusEffectiveDate) values(?,?,?,?,?,?,?,?,?)");
      int i2 = 1;
      Profile.setStatementInt(localPreparedStatement, i2, i1, false);
      Profile.setStatementString(localPreparedStatement, i2, (String)paramPublisher.get("OFXPUBLISHERID"), "OFXPUBLISHERID", false);
      Profile.setStatementString(localPreparedStatement, i2, paramPublisher.getSpName(), "SPNAME", false);
      Profile.setStatementString(localPreparedStatement, i2, paramPublisher.getPublisherURL(), "PUBLISHERURL", false);
      Profile.setStatementString(localPreparedStatement, i2, "ACTIVE", "STATUSCODE", false);
      Profile.setStatementString(localPreparedStatement, i2, (String)paramPublisher.get("PROXYUSERID"), "PROXYUSERID", false);
      Profile.setStatementString(localPreparedStatement, i2, (String)paramPublisher.get("PROXYPASSWORD"), "PROXYPASSWORD", false);
      Profile.setStatementDate(localPreparedStatement, i2, localTimestamp, false);
      Profile.setStatementDate(localPreparedStatement, i2, localTimestamp, false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into t_publisher (PublisherId,OFXPublisherId,SPName,PublisherURL,StatusCode,ProxyUserId,ProxyPassword,CreateDate,StatusEffectiveDate) values(?,?,?,?,?,?,?,?,?)");
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramPublisher;
  }
  
  public static Publishers getPublishers(Publisher paramPublisher)
    throws MultiException
  {
    String str = "OneViewAdapter.getPublishers";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Publishers localPublishers = new Publishers();
    StringBuffer localStringBuffer = new StringBuffer("select * from T_Publisher");
    if (paramPublisher != null) {
      Profile.appendSQLSelectInt(localStringBuffer, "PUBLISHERID", paramPublisher.getPublisherIDValue(), false);
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Publisher localPublisher = localPublishers.add();
        localPublisher.setPublisherID(localResultSet.getString("PUBLISHERID"));
        localPublisher.setPublisherURL(localResultSet.getString("PUBLISHERURL"));
        localPublisher.setStatusCode(localResultSet.getString("STATUSCODE"));
        localPublisher.setSpName(localResultSet.getString("SPNAME"));
        Timestamp localTimestamp = localResultSet.getTimestamp("CREATEDATE");
        if (localTimestamp != null) {
          localPublisher.setCreateDate(new DateTime(localTimestamp, Locale.getDefault()));
        }
        localTimestamp = localResultSet.getTimestamp("STATUSEFFECTIVEDATE");
        localPublisher.setStatusEffectiveDate(new DateTime(localTimestamp, Locale.getDefault()));
        localTimestamp = localResultSet.getTimestamp("MODIFIEDDATE");
        localPublisher.setModifiedDate(new DateTime(localTimestamp, Locale.getDefault()));
        a(localConnection, localResultSet.getInt("PUBLISHERID"), "PUBLISHER", localPublisher);
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str, 6002);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localPublishers;
  }
  
  public static Publisher modifyPublisher(Publisher paramPublisher)
    throws MultiException
  {
    String str = "OneViewAdapter.modifyPublisher";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramPublisher.getPublisherID())) {
      throw new MultiException(str, 6010, "Invalid Publisher Id");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update T_publisher set publisherurl=?,statuscode=?,spname=?,createdate=?,statuseffectiveDate=?,modifiedDate=? where publisherId=?");
      int i1 = 1;
      localPreparedStatement.setLong(i1++, paramPublisher.getPublisherIDValue());
      localPreparedStatement.setString(i1++, paramPublisher.getPublisherURL());
      localPreparedStatement.setString(i1++, paramPublisher.getStatusCode());
      localPreparedStatement.setString(i1++, paramPublisher.getSpName());
      localPreparedStatement.setTimestamp(i1++, Profile.convertToTimestamp(paramPublisher.getCreateDateValue()));
      localPreparedStatement.setTimestamp(i1++, Profile.convertToTimestamp(paramPublisher.getStatusEffectiveDateValue()));
      localPreparedStatement.setTimestamp(i1++, DBUtil.getCurrentTimestamp());
      DBUtil.executeUpdate(localPreparedStatement, "update T_publisher set publisherurl=?,statuscode=?,spname=?,createdate=?,statuseffectiveDate=?,modifiedDate=? where publisherId=?");
    }
    catch (Exception localException)
    {
      handleError(localException, str, 6002);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramPublisher;
  }
  
  public static void deletePublisher(Publisher paramPublisher)
    throws MultiException
  {
    String str = "OneViewAdapter.deletePublisher";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if ((paramPublisher == null) || (!Profile.isValidId(paramPublisher.getPublisherIDValue()))) {
      throw new MultiException(str, 6010, "Invalid Publisher Id");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update T_publisher set StatusCode='DELETE' where PublisherId=?");
      localPreparedStatement.setLong(1, paramPublisher.getPublisherIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update T_publisher set StatusCode='DELETE' where PublisherId=?");
    }
    catch (Exception localException)
    {
      handleError(localException, str, 6002);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static Consumer enrollConsumer(Publisher paramPublisher, Consumer paramConsumer)
    throws MultiException
  {
    String str1 = "OneViewAdapter.enrollConsumer";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1 = 0;
    String str2 = null;
    long l1 = paramConsumer.getConsumerIDValue();
    if (!Profile.isValidId(l1)) {
      throw new MultiException(str1, 6013, "Invalid Consumer Id");
    }
    String str3 = paramConsumer.getTaxID();
    if (!Profile.hasValue(str3)) {
      throw new MultiException(str1, 6016, "Invalid Tax Id");
    }
    if (!Profile.hasValue(paramConsumer.getSecurityName())) {
      paramConsumer.setSecurityName(str3);
    }
    Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select StatusCode from T_Consumer where ConsumerId=?");
      localPreparedStatement.setLong(1, l1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select StatusCode from T_Consumer where ConsumerId=?");
      if (localResultSet.next())
      {
        i1 = 1;
        str2 = localResultSet.getString("STATUSCODE");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      if (i1 == 0)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into T_Consumer (ConsumerId,FirstName,MiddleName,LastName,SecurityName,TaxId,DateBirth,StatusCode,FeeAcctNumber,FeeAcctType,FeeAcctRTN,CreateDate) values (?,?,?,?,?,?,?,?,?,?,?,?)");
        localPreparedStatement.setLong(1, l1);
        localPreparedStatement.setString(2, paramConsumer.getFirstName());
        String str4 = paramConsumer.getMiddleName();
        if (str4.length() == 0) {
          str4 = " ";
        }
        localPreparedStatement.setString(3, str4);
        localPreparedStatement.setString(4, paramConsumer.getLastName());
        localPreparedStatement.setString(5, paramConsumer.getSecurityName());
        localPreparedStatement.setString(6, str3);
        localPreparedStatement.setDate(7, Profile.convertToDate(paramConsumer.getBirthDateValue()));
        localPreparedStatement.setString(8, "ACTIVE");
        localPreparedStatement.setString(9, (String)paramConsumer.get("FEEACCTNUMBER"));
        localPreparedStatement.setString(10, (String)paramConsumer.get("FEEACCTTYPE"));
        localPreparedStatement.setString(11, (String)paramConsumer.get("FEEACCTRTN"));
        localPreparedStatement.setTimestamp(12, localTimestamp);
        DBUtil.executeUpdate(localPreparedStatement, "insert into T_Consumer (ConsumerId,FirstName,MiddleName,LastName,SecurityName,TaxId,DateBirth,StatusCode,FeeAcctNumber,FeeAcctType,FeeAcctRTN,CreateDate) values (?,?,?,?,?,?,?,?,?,?,?,?)");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        a(localConnection, l1, "CONSUMER", paramConsumer, "PRIMARY");
        jdField_if(localConnection, l1);
      }
      else if (!str2.equalsIgnoreCase("ACTIVE"))
      {
        paramConsumer.setStatusCode("ACTIVE");
        a(localConnection, paramConsumer, true);
      }
      if ((paramPublisher != null) && (Profile.isValidId(paramPublisher.getPublisherIDValue()))) {
        a(localConnection, l1, paramPublisher.getPublisherIDValue(), 0L);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramConsumer;
  }
  
  public static Consumer getConsumer(Consumer paramConsumer)
    throws MultiException
  {
    String str = "OneViewAdapter.getConsumer";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Consumer localConsumer = null;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select c.* from T_Consumer c where c.ConsumerId != 0");
    Profile.appendSQLSelectInt(localStringBuffer, "c.", "CONSUMERID", paramConsumer.getConsumerIDValue(), true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "FIRSTNAME", paramConsumer.getFirstName(), false, false, true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "LASTNAME", paramConsumer.getLastName(), false, false, true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "SECURITYNAME", paramConsumer.getSecurityName(), false, false, true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "TAXID", paramConsumer.getTaxID(), false, false, true);
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        localConsumer = new Consumer();
        localConsumer.setConsumerID(localResultSet.getLong("CONSUMERID"));
        localConsumer.setFirstName(localResultSet.getString("FIRSTNAME"));
        localConsumer.setMiddleName(localResultSet.getString("MIDDLENAME"));
        if (localConsumer.getMiddleName().trim().compareTo(".") == 0) {
          localConsumer.setMiddleName("");
        }
        localConsumer.setLastName(localResultSet.getString("LASTNAME"));
        localConsumer.setSecurityName(localResultSet.getString("SECURITYNAME"));
        localConsumer.setTaxID(localResultSet.getString("TAXID"));
        localConsumer.setStatusCode(localResultSet.getString("STATUSCODE"));
        Date localDate = localResultSet.getDate("DATEBIRTH");
        if (localDate != null) {
          localConsumer.setBirthDate(new DateTime(localDate, Locale.getDefault()));
        }
        a(localConnection, localResultSet.getLong("CONSUMERID"), "CONSUMER", localConsumer);
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localConsumer;
  }
  
  public static Consumers getConsumerList(Publisher paramPublisher, Consumer paramConsumer)
    throws MultiException
  {
    String str = "OneViewAdapter.getConsumerList";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Consumers localConsumers = new Consumers(Locale.getDefault());
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramPublisher != null) && (Profile.isValidId(paramPublisher.getPublisherIDValue()))) {
      localStringBuffer.append("select c.FirstName, c.LastName, c.ConsumerId from T_Consumer c, T_ConsumerPubRelationship cpr where c.ConsumerId = cpr.ConsumerId");
    } else {
      localStringBuffer.append("select c.FirstName, c.LastName, c.ConsumerId from T_Consumer c where c.ConsumerId != 0");
    }
    Profile.appendSQLSelectInt(localStringBuffer, "c.", "CONSUMERID", paramConsumer.getConsumerIDValue(), true);
    Profile.appendSQLSelectInt(localStringBuffer, "cpr.", "PUBLISHERID", paramPublisher.getPublisherIDValue(), true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "FIRSTNAME", paramConsumer.getFirstName(), false, false, true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "LASTNAME", paramConsumer.getLastName(), false, false, true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "SECURITYNAME", paramConsumer.getSecurityName(), false, false, true);
    Profile.appendSQLSelectString(localStringBuffer, "c.", "TAXID", paramConsumer.getTaxID(), false, false, true);
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Consumer localConsumer = localConsumers.add();
        localConsumer.setFirstName(localResultSet.getString("FIRSTNAME"));
        localConsumer.setLastName(localResultSet.getString("LASTNAME"));
        localConsumer.setConsumerID(localResultSet.getLong("CONSUMERID"));
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localConsumers;
  }
  
  public static Consumer modifyConsumer(Consumer paramConsumer)
    throws MultiException
  {
    String str = "OneViewAdapter.modifyConsumer";
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      a(localConnection, paramConsumer, false);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(poolName, localConnection);
    }
    return paramConsumer;
  }
  
  private static void a(Connection paramConnection, Consumer paramConsumer, boolean paramBoolean)
    throws Exception
  {
    String str1 = "OneViewAdapter.modifyConsumer";
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramConsumer.getConsumerIDValue())) {
      throw new MultiException(str1, 6013, "Invalid Consumer Id");
    }
    try
    {
      if (!paramBoolean)
      {
        a(paramConnection, paramConsumer.getConsumerIDValue(), "CONSUMER", 0L, paramConsumer);
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update T_Consumer set FirstName=?,MiddleName=?,LastName=?,SecurityName=?,TaxId=?,DateBirth=?,StatusCode=?,FeeAcctNumber=?,FeeAcctType=?,FeeAcctRtn=?ModifiedDate=? where ConsumerId=?");
        int i1 = 1;
        localPreparedStatement.setString(i1++, paramConsumer.getFirstName());
        String str2 = paramConsumer.getMiddleName();
        if (str2.length() == 0) {
          str2 = ".";
        }
        localPreparedStatement.setString(i1++, str2);
        localPreparedStatement.setString(i1++, paramConsumer.getLastName());
        localPreparedStatement.setString(i1++, paramConsumer.getSecurityName());
        localPreparedStatement.setString(i1++, paramConsumer.getTaxID());
        localPreparedStatement.setDate(i1++, Profile.convertToDate(paramConsumer.getBirthDateValue()));
        localPreparedStatement.setString(i1++, paramConsumer.getStatusCode());
        localPreparedStatement.setString(i1++, (String)paramConsumer.get("FEEACCTNUMBER"));
        localPreparedStatement.setString(i1++, (String)paramConsumer.get("FEEACCTTYPE"));
        localPreparedStatement.setString(i1++, (String)paramConsumer.get("FEEACCTRTN"));
        localPreparedStatement.setTimestamp(i1++, DBUtil.getCurrentTimestamp());
        localPreparedStatement.setLong(i1++, paramConsumer.getConsumerIDValue());
        DBUtil.executeUpdate(localPreparedStatement, "update T_Consumer set FirstName=?,MiddleName=?,LastName=?,SecurityName=?,TaxId=?,DateBirth=?,StatusCode=?,FeeAcctNumber=?,FeeAcctType=?,FeeAcctRtn=?ModifiedDate=? where ConsumerId=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update T_Consumer set StatusCode=? where ConsumerId=?");
        localPreparedStatement.setString(1, paramConsumer.getStatusCode());
        localPreparedStatement.setLong(2, paramConsumer.getConsumerIDValue());
        DBUtil.executeUpdate(localPreparedStatement, "update T_Consumer set StatusCode=? where ConsumerId=?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update T_ConsumerPubRelationship set StatusCode=?,ModifiedDate=? where ConsumerId=?");
      localPreparedStatement.setString(1, "CHANGE");
      localPreparedStatement.setTimestamp(2, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setLong(3, paramConsumer.getConsumerIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update T_ConsumerPubRelationship set StatusCode=?,ModifiedDate=? where ConsumerId=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static boolean deleteConsumer(Consumer paramConsumer)
    throws MultiException
  {
    String str = "OneViewAdapter.deleteConsumer";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramConsumer.getConsumerIDValue())) {
      throw new MultiException(str, 6013, "Invalid Consumer Id");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update T_Consumer set StatusCode='STATUS_DELETE',ModifiedDate=? where ConsumerId=?");
      localPreparedStatement.setTimestamp(1, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setLong(2, paramConsumer.getConsumerIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update T_Consumer set StatusCode='STATUS_DELETE',ModifiedDate=? where ConsumerId=?");
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return true;
  }
  
  public static ConsumerStatus getConsumerStatus(Consumer paramConsumer)
    throws MultiException
  {
    String str = "OneViewAdapter.getConsumerStatus";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ConsumerStatus localConsumerStatus = new ConsumerStatus();
    if ((paramConsumer == null) || (!Profile.isValidId(paramConsumer.getConsumerIDValue()))) {
      throw new MultiException(str, 6013, "Invalid consumer ID");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ConsumerId from FFI_ConsumerStatus where ConsumerId=?");
      localPreparedStatement.setLong(1, paramConsumer.getConsumerIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ConsumerId from FFI_ConsumerStatus where ConsumerId=?");
      if (localResultSet.next())
      {
        localConsumerStatus.setTCAccepted(localResultSet.getString("TCACCEPTED"));
        localConsumerStatus.setTCurl(localResultSet.getInt("TCID"));
        Timestamp localTimestamp = localResultSet.getTimestamp("NEWBILLERNOTIFIEDDATE");
        if (localTimestamp != null) {
          localConsumerStatus.setNewBillerNotifiedDate(new DateTime(localTimestamp, Locale.getDefault()));
        }
        localConsumerStatus.setBillDueNotifyDesired(localResultSet.getString("BILLDUENOTIFYDESIRED"));
        localConsumerStatus.setBillDueNotifyDays(localResultSet.getString("BILLDUENOTIFYDAYS"));
        localConsumerStatus.setNewBillNotifyDesired(localResultSet.getString("NEWBILLNOTIFYDESIRED"));
        localConsumerStatus.setAccountInfoNotifyDesired(localResultSet.getString("ACCOUNTINFONOTIFYDESIRED"));
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return localConsumerStatus;
  }
  
  public static ConsumerStatus modifyConsumerStatus(ConsumerStatus paramConsumerStatus)
    throws MultiException
  {
    String str = "OneViewAdapter.modifyConsumerStatus";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramConsumerStatus.getID())) {
      throw new MultiException(str, 6013, "Invalid consumer ID");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FFI_ConsumerStatus set TCId=?,TCAccepted=?,BillDueNotifyDesired=?,BillDueNotifyDays=?,NewBillNotifyDesired=?,AccountInfoNotifyDesired=?,NewBillerNotifyDesired=?) where consumerId=?");
      int i1 = 1;
      localPreparedStatement.setLong(i1++, paramConsumerStatus.getTCurlValue());
      localPreparedStatement.setString(i1++, paramConsumerStatus.getTCAccepted());
      localPreparedStatement.setString(i1++, paramConsumerStatus.getBillDueNotifyDesired());
      localPreparedStatement.setLong(i1++, paramConsumerStatus.getBillDueNotifyDaysValue());
      localPreparedStatement.setString(i1++, paramConsumerStatus.getNewBillNotifyDesired());
      localPreparedStatement.setString(i1++, paramConsumerStatus.getAccountInfoNotifyDesired());
      localPreparedStatement.setString(i1++, paramConsumerStatus.getNewBillerNotifyDesired());
      localPreparedStatement.setLong(i1++, paramConsumerStatus.getIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update FFI_ConsumerStatus set TCId=?,TCAccepted=?,BillDueNotifyDesired=?,BillDueNotifyDays=?,NewBillNotifyDesired=?,AccountInfoNotifyDesired=?,NewBillerNotifyDesired=?) where consumerId=?");
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramConsumerStatus;
  }
  
  public static Billers getBillers(Publisher paramPublisher)
    throws MultiException
  {
    String str = "OneViewAdapter.getBillers";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Billers localBillers = new Billers();
    long l1 = 0L;
    StringBuffer localStringBuffer = new StringBuffer("select * from T_Biller");
    if (paramPublisher != null)
    {
      l1 = paramPublisher.getPublisherIDValue();
      Profile.appendSQLSelectInt(localStringBuffer, "PUBLISHERID", l1, false);
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      if (l1 > 0L) {
        localPreparedStatement.setLong(1, l1);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Biller localBiller = localBillers.add();
        localBiller.setBillerID(localResultSet.getLong("BILLERID"));
        localBiller.setBillerName(localResultSet.getString("BILLERNAME"));
        localBiller.setPublisherID(localResultSet.getLong("PUBLISHERID"));
        localBiller.setStatusCode(localResultSet.getString("STATUSCODE"));
        localBiller.setRestrictMessage(localResultSet.getString("RESTRICTMESSAGE"));
        localBiller.setBillerEnrollURL(localResultSet.getString("BILLERENROLLURL"));
        localBiller.setBillerInfoURL(localResultSet.getString("BILLERINFOURL"));
        localBiller.setLogoURL(localResultSet.getString("LOGOURL"));
        localBiller.setAccountFormat(localResultSet.getString("ACCOUNTFORMAT"));
        localBiller.setAccountEditMask(localResultSet.getString("ACCOUNTEDITMASK"));
        localBiller.setHelpMessage(localResultSet.getString("HELPMESSAGE"));
        localBiller.setValidateURL(localResultSet.getString("VALIDATEURL"));
        a(localConnection, localBiller);
        a(localConnection, localResultSet.getLong("BILLERID"), "BILLER", localBiller);
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return localBillers;
  }
  
  private static void a(Connection paramConnection, Biller paramBiller)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from FFI_Biller where billerId=?");
      localPreparedStatement.setLong(1, paramBiller.getBillerIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from FFI_Biller where billerId=?");
      if (localResultSet.next()) {
        paramBiller.setPayeeID(localResultSet.getString("PAYEEIDS"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static Biller modifyBiller(Biller paramBiller)
    throws MultiException
  {
    String str = "OneViewAdapter.modifyBiller";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if (!Profile.isValidId(paramBiller.getBillerIDValue())) {
      throw new MultiException(str, 6012, "invalid Biller ID");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update T_Biller set BillerName=?,PublisherId=?,StatusCode=?,RestrictMessage=?,BillerEnrollURL=?,BillerInfoURL=?,LogoURL=?,AccountFormat=?,AccountEditMask=?,HelpMessage=?,ValidateURL=? where BillerId=?");
      int i1 = 1;
      localPreparedStatement.setString(i1++, paramBiller.getBillerName());
      localPreparedStatement.setLong(i1++, paramBiller.getPublisherIDValue());
      localPreparedStatement.setString(i1++, paramBiller.getStatusCode());
      localPreparedStatement.setString(i1++, paramBiller.getRestrictMessage());
      localPreparedStatement.setString(i1++, paramBiller.getBillerEnrollURL());
      localPreparedStatement.setString(i1++, paramBiller.getBillerInfoURL());
      localPreparedStatement.setString(i1++, paramBiller.getLogoURL());
      localPreparedStatement.setString(i1++, paramBiller.getAccountFormat());
      localPreparedStatement.setString(i1++, paramBiller.getAccountEditMask());
      localPreparedStatement.setString(i1++, paramBiller.getHelpMessage());
      localPreparedStatement.setString(i1++, paramBiller.getValidateURL());
      localPreparedStatement.setLong(i1++, paramBiller.getBillerIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update T_Biller set BillerName=?,PublisherId=?,StatusCode=?,RestrictMessage=?,BillerEnrollURL=?,BillerInfoURL=?,LogoURL=?,AccountFormat=?,AccountEditMask=?,HelpMessage=?,ValidateURL=? where BillerId=?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FFI_BILLER set PayeeIds=?,ShowMemo=?,Memo=? where BillerId=?");
      i1 = 1;
      localPreparedStatement.setString(i1++, paramBiller.getPayeeID());
      localPreparedStatement.setString(i1++, (String)paramBiller.get("SHOWMEMO"));
      localPreparedStatement.setString(i1++, (String)paramBiller.get("MEMO"));
      localPreparedStatement.setLong(i1++, paramBiller.getBillerIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update FFI_BILLER set PayeeIds=?,ShowMemo=?,Memo=? where BillerId=?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramBiller;
  }
  
  public static EBillAccount activateAccount(EBillAccount paramEBillAccount)
    throws MultiException
  {
    String str1 = "OneViewAdapter.activateAccount";
    Connection localConnection = null;
    long l1 = -1L;
    long l2 = -1L;
    l2 = paramEBillAccount.getConsumerIDValue();
    l1 = paramEBillAccount.getBillerIDValue();
    String str2 = paramEBillAccount.getAccountNumber();
    if (paramEBillAccount == null) {
      throw new MultiException(str1, 6014, "invalid account");
    }
    if (!Profile.isValidId(l1)) {
      throw new MultiException(str1, 6012, "invalid Biller ID");
    }
    if (!Profile.isValidId(l2)) {
      throw new MultiException(str1, 6013, "invalid Consumer ID");
    }
    if (!Profile.hasValue(str2)) {
      throw new MultiException(str1, 6014, "invalid account number");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      a(localConnection, l2, 0L, l1);
      int i1 = a(localConnection, l2, l1, str2);
      if (i1 == 0)
      {
        i1 = a(localConnection, paramEBillAccount);
        a(localConnection, i1, "ACCOUNT", paramEBillAccount, "PRIMARY");
      }
      paramEBillAccount.setAccountID(i1);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(poolName, localConnection);
    }
    return paramEBillAccount;
  }
  
  public static void deactivateAccount(EBillAccount paramEBillAccount)
    throws MultiException
  {
    String str = "OneViewAdapter.deactivateAccount";
    int i1 = -1;
    Connection localConnection = null;
    if ((paramEBillAccount == null) || (!Profile.isValidId(paramEBillAccount.getAccountIDValue()))) {
      throw new MultiException(str, 6014, "no account to deactivate");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      a(localConnection, i1, "DELPEN");
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(poolName, localConnection);
    }
  }
  
  public static EBillAccount modifyAccount(EBillAccount paramEBillAccount, boolean paramBoolean)
    throws MultiException
  {
    String str = "OneViewAdapter.modifyAccount";
    Connection localConnection = null;
    if ((paramEBillAccount == null) || (!Profile.isValidId(paramEBillAccount.getAccountIDValue()))) {
      throw new MultiException(str, 6014, "No account to modify");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      a(localConnection, paramEBillAccount, paramBoolean ? "CHANGE" : null);
      if (paramBoolean) {
        a(localConnection, paramEBillAccount.getAccountIDValue(), "ACCOUNT", paramEBillAccount, "NEW");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(poolName, localConnection);
    }
    return paramEBillAccount;
  }
  
  public static int getEnrollmentStatus(Publisher paramPublisher, Consumer paramConsumer)
    throws MultiException
  {
    String str1 = "OneViewAdapter.getEnrollmentStatus";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1 = 0;
    if ((paramConsumer == null) || (!Profile.isValidId(paramConsumer.getConsumerIDValue()))) {
      throw new MultiException(str1, 6013, "invalid consumer ID");
    }
    if ((paramPublisher == null) || (!Profile.isValidId(paramPublisher.getPublisherIDValue()))) {
      throw new MultiException(str1, 6010, "invalid publisher ID");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select StatusCode from T_ConsumerPubRelationship where ConsumerId=? and PublisherId=?");
      localPreparedStatement.setLong(1, paramConsumer.getConsumerIDValue());
      localPreparedStatement.setLong(2, paramPublisher.getPublisherIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select StatusCode from T_ConsumerPubRelationship where ConsumerId=? and PublisherId=?");
      if (localResultSet.next())
      {
        String str2 = localResultSet.getString("STATUSCODE");
        if (str2.equals("NEW")) {
          i1 = 1;
        } else if (str2.equals("PEND")) {
          i1 = 2;
        } else if (str2.equals("ACTIVE")) {
          i1 = 3;
        } else if (str2.equals("REJECT")) {
          i1 = 4;
        }
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return i1;
  }
  
  public static EBillAccounts getAccounts(Biller paramBiller, Consumer paramConsumer, EBillAccount paramEBillAccount)
    throws MultiException
  {
    String str1 = "OneViewAdapter.getAccounts";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    String str2 = null;
    StringBuffer localStringBuffer = new StringBuffer("select a.*,f.* from T_Account a, FFI_Account f where a.AccountId = f.AccountId");
    EBillAccounts localEBillAccounts = new EBillAccounts();
    if (paramEBillAccount != null) {
      l1 = paramEBillAccount.getAccountIDValue();
    }
    if (paramConsumer != null)
    {
      l2 = paramConsumer.getConsumerIDValue();
      str2 = paramConsumer.getStatusCode();
    }
    if (paramBiller != null) {
      l3 = paramBiller.getBillerIDValue();
    }
    if ((l1 <= 0L) && (l2 <= 0L) && (l3 <= 0L)) {
      throw new MultiException(str1, 6014, "Invalid account ID");
    }
    Profile.appendSQLSelectInt(localStringBuffer, "a.", "ACCOUNTID", l1, true);
    Profile.appendSQLSelectInt(localStringBuffer, "CONSUMERID", l2, true);
    Profile.appendSQLSelectInt(localStringBuffer, "BILLERID", l3, true);
    Profile.appendSQLSelectString(localStringBuffer, "STATUSCODE", str2, true);
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        EBillAccount localEBillAccount = localEBillAccounts.add();
        localEBillAccount.setAccountID(localResultSet.getLong("ACCOUNTID"));
        localEBillAccount.setConsumerID(localResultSet.getLong("CONSUMERID"));
        localEBillAccount.setAccountNumber(localResultSet.getString("ACCOUNTNUMBER"));
        localEBillAccount.setNickName(localResultSet.getString("NICKNAME"));
        localEBillAccount.setPayeeID(localResultSet.getString("PAYEEID"));
        localEBillAccount.setStatusCode(localResultSet.getString("STATUSCODE"));
        Timestamp localTimestamp = localResultSet.getTimestamp("CREATEDATE");
        if (localTimestamp != null) {
          localEBillAccount.setCreatedDate(new DateTime(localTimestamp, Locale.getDefault()));
        }
        localEBillAccount.setBillerID(localResultSet.getLong("BILLERID"));
        localEBillAccount.setAccountHoldersName(localResultSet.getString("NAMEACCTHELD"));
        localEBillAccount.setPreAuthToken(localResultSet.getString("PREAUTHTOKEN"));
        localEBillAccount.setReasonText(localResultSet.getString("REASONTEXT"));
        a(localConnection, localResultSet.getInt("ACCOUNTID"), "ACCOUNT", localEBillAccount);
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localEBillAccounts;
  }
  
  public static BillSummaries getBillSummaries(Consumer paramConsumer, BillSummary paramBillSummary)
    throws MultiException
  {
    String str = "OneViewAdapter.getBillSummaries";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    BillSummaries localBillSummaries = new BillSummaries(Locale.getDefault());
    StringBuffer localStringBuffer = new StringBuffer("select * from T_BillSummary where ConsumerId=?");
    if ((paramConsumer == null) || (!Profile.isValidId(paramConsumer.getConsumerIDValue()))) {
      throw new MultiException(str, 6013, "Invalid consumer ID");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from T_BillSummary where ConsumerId=?");
      localPreparedStatement.setLong(1, paramConsumer.getConsumerIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        BillSummary localBillSummary = localBillSummaries.add();
        paramBillSummary.setBillSummaryID(localResultSet.getString("BILLSUMMARYID"));
        localBillSummary.setAccountID(localResultSet.getLong("ACCOUNTID"));
        localBillSummary.setBillID(localResultSet.getString("BILLID"));
        localBillSummary.setConsumerID(localResultSet.getLong("CONSUMERID"));
        localBillSummary.setAmountDue(localResultSet.getString("AMOUNTDUE"));
        localBillSummary.setActivity(localResultSet.getString("ACTIVITY"));
        localBillSummary.setMinimumAmountDue(localResultSet.getString("MINIMUMAMOUNTDUE"));
        localBillSummary.setBalance(localResultSet.getString("BALANCE"));
        localBillSummary.setPreviousBalance(localResultSet.getString("PREVIOUSBALANCE"));
        localBillSummary.setStatusCode(localResultSet.getString("STATUSCODE"));
        localBillSummary.setBillRefInfo(localResultSet.getString("BILLREFINFO"));
        localBillSummary.setStatementURL(localResultSet.getString("STATEMENTURL"));
        localBillSummary.setCreatedDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("CREATEDATE")));
        localBillSummary.setBillDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("BILLDATE")));
        localBillSummary.setPaymentDueDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("PAYMENTDUEDATE")));
        localBillSummary.setViewDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("VIEWDATE")));
        localBillSummary.setStatementBeginDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("STATEMENTOPENDATE")));
        localBillSummary.setStatementEndDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("STATEMENTCLOSEDATE")));
        a(localConnection, localBillSummary);
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localBillSummaries;
  }
  
  private static void a(Connection paramConnection, BillSummary paramBillSummary)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from FFI_PaymentInfo where BillSummaryId=? order by PaymentDate");
      localPreparedStatement.setString(1, paramBillSummary.getBillSummaryID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from FFI_PaymentInfo where BillSummaryId=? order by PaymentDate");
      PaymentInfos localPaymentInfos = paramBillSummary.getPaymentInfos();
      while (localResultSet.next())
      {
        PaymentInfo localPaymentInfo = localPaymentInfos.add();
        localPaymentInfo.setPaymentInfoID(localResultSet.getString("PAYMENTINFOID"));
        localPaymentInfo.setBillSummaryID(localResultSet.getString("BILLSUMMARYID"));
        localPaymentInfo.setPaymentRefNum(localResultSet.getString("PAYMENTREFID"));
        localPaymentInfo.setPaymentAccountID(localResultSet.getString("PAYMENTACCOUNT"));
        localPaymentInfo.setPaymentDate(ProfileUtil.getDateTime(localResultSet.getTimestamp("PAYMENTDATE")));
        localPaymentInfo.setAmountPaid(localResultSet.getString("PAYMENTAMOUNT"));
        localPaymentInfo.setErrorCode(localResultSet.getString("PAYMENTERROR"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public static BillSummary modifyBillSummary(BillSummary paramBillSummary, PaymentInfo paramPaymentInfo)
    throws MultiException
  {
    String str1 = "OneViewAdapter.modifyBillSummary";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramBillSummary == null) || (!Profile.hasValue(paramBillSummary.getBillSummaryID()))) {
      throw new MultiException(str1, 6015, "Invalid bill summary ID");
    }
    String str2 = paramBillSummary.getStatusCode();
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      if (str2.length() > 0)
      {
        if (str2.equalsIgnoreCase("VIEWED")) {
          localStringBuffer.append("update T_BillSummary set StatusCode=?,ViewDate=? where BillSummaryId=?");
        } else {
          localStringBuffer.append("update T_BillSummary set StatusCode=? where BillSummaryId=?");
        }
        int i1 = 1;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(i1++, str2);
        if (str2.equalsIgnoreCase("VIEWED")) {
          localPreparedStatement.setTimestamp(i1++, DBUtil.getCurrentTimestamp());
        }
        localPreparedStatement.setString(i1++, paramBillSummary.getBillSummaryID());
        DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      }
      a(localConnection, paramBillSummary, paramPaymentInfo);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramBillSummary;
  }
  
  private static void a(Connection paramConnection, BillSummary paramBillSummary, PaymentInfo paramPaymentInfo)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    if ((paramPaymentInfo != null) && (paramPaymentInfo.getAmountPaidValue() != null)) {
      try
      {
        int i1 = DBUtil.getNextId(paramConnection, dbType, "PAYMENTINFOID");
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into FFI_PaymentInfo (PaymentInfoId,BillSummaryId,PaymentRefId,PaymentAccount,PaymentDate,PaymentAmount) values(?,?,?,?,?,?)");
        int i2 = 1;
        localPreparedStatement.setInt(i2++, i1);
        localPreparedStatement.setString(i2++, paramBillSummary.getBillSummaryID());
        localPreparedStatement.setString(i2++, paramPaymentInfo.getPaymentRefNum());
        localPreparedStatement.setString(i2++, paramPaymentInfo.getPaymentAccountID());
        localPreparedStatement.setDate(i2++, Profile.convertToDate(paramPaymentInfo.getPaymentDateValue()));
        localPreparedStatement.setString(i2++, paramPaymentInfo.getAmountPaid());
        DBUtil.executeUpdate(localPreparedStatement, "insert into FFI_PaymentInfo (PaymentInfoId,BillSummaryId,PaymentRefId,PaymentAccount,PaymentDate,PaymentAmount) values(?,?,?,?,?,?)");
      }
      finally
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
  }
  
  private static void a(Connection paramConnection, long paramLong1, long paramLong2, long paramLong3)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramLong3 != 0L)
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select PublisherId from T_Biller where BillerId=?");
        localPreparedStatement.setLong(1, paramLong3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select PublisherId from T_Biller where BillerId=?");
        if (!localResultSet.next()) {
          throw new Exception("Couldn't get publisher from T_biller");
        }
        paramLong2 = localResultSet.getInt(1);
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ConsumerId from T_ConsumerPubRelationship where ConsumerId=? and PublisherId=?");
      localPreparedStatement.setLong(1, paramLong1);
      localPreparedStatement.setLong(2, paramLong2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ConsumerId from T_ConsumerPubRelationship where ConsumerId=? and PublisherId=?");
      if (!localResultSet.next())
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "Insert into T_ConsumerPubRelationship (ConsumerId,PublisherId,StatusCode,CreateDate,OFXUserId) values (?,?,?,?,?)");
        localPreparedStatement.setLong(1, paramLong1);
        localPreparedStatement.setLong(2, paramLong2);
        localPreparedStatement.setString(3, "NEW");
        localPreparedStatement.setTimestamp(4, DBUtil.getCurrentTimestamp());
        Random localRandom = new Random();
        String str = String.valueOf(localRandom.nextInt()) + String.valueOf(paramLong1);
        localPreparedStatement.setString(5, str);
        DBUtil.executeUpdate(localPreparedStatement, "Insert into T_ConsumerPubRelationship (ConsumerId,PublisherId,StatusCode,CreateDate,OFXUserId) values (?,?,?,?,?)");
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void a(Connection paramConnection, long paramLong, String paramString, Contact paramContact)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select c.* from T_ContactInfo c, T_ContactRelationship cr where cr.ContactInfoId = c.ContactInfoId and cr.Id = ? and cr.IdType = ?");
      int i1 = 1;
      localPreparedStatement.setLong(i1++, paramLong);
      localPreparedStatement.setString(i1++, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.* from T_ContactInfo c, T_ContactRelationship cr where cr.ContactInfoId = c.ContactInfoId and cr.Id = ? and cr.IdType = ?");
      if (localResultSet.next())
      {
        paramContact.setPhone(localResultSet.getString("DAYPHONE"));
        paramContact.setPhone2(localResultSet.getString("EVEPHONE"));
        paramContact.setStreet(localResultSet.getString("ADDR1"));
        paramContact.setStreet2(localResultSet.getString("ADDR2"));
        paramContact.setEmail(localResultSet.getString("EMAIL"));
        paramContact.setCity(localResultSet.getString("CITY"));
        paramContact.setState("STATEPROV");
        paramContact.setZipCode("POSTALCODE");
        paramContact.setCountry("COUNTRY");
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void a(Connection paramConnection, long paramLong, String paramString1, Contact paramContact, String paramString2)
    throws Exception
  {
    if (!Profile.isValidId(paramLong)) {
      throw new Exception("Invalid contact ID in addContact");
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ContactInfoId from T_ContactRelationship where ID=? and IDType=? and ContactType=?");
      localPreparedStatement.setLong(1, paramLong);
      localPreparedStatement.setString(2, paramString1);
      localPreparedStatement.setString(3, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ContactInfoId from T_ContactRelationship where ID=? and IDType=? and ContactType=?");
      if (localResultSet.next())
      {
        a(paramConnection, paramLong, paramString1, localResultSet.getInt("CONTACTINFOID"), paramContact);
      }
      else
      {
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into T_ContactInfo (ContactInfoId,CreateDate,DayPhone,EvePhone,Fax,Email,Addr1,Addr2,Addr3,City,StateProv,PostalCode,Country,URL) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
        int i1 = DBUtil.getNextId(paramConnection, dbType, "s_ctactinfo");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setTimestamp(2, localTimestamp);
        localPreparedStatement.setString(3, paramContact.getPhone());
        localPreparedStatement.setString(4, paramContact.getPhone2());
        localPreparedStatement.setString(5, paramContact.getFaxPhone());
        localPreparedStatement.setString(6, paramContact.getEmail());
        localPreparedStatement.setString(7, paramContact.getStreet());
        localPreparedStatement.setString(8, paramContact.getStreet2());
        localPreparedStatement.setString(9, (String)paramContact.get("ADDR3"));
        localPreparedStatement.setString(10, paramContact.getCity());
        localPreparedStatement.setString(11, paramContact.getState());
        localPreparedStatement.setString(12, paramContact.getZipCode());
        localPreparedStatement.setString(13, paramContact.getCountry());
        localPreparedStatement.setString(14, (String)paramContact.get("URL"));
        DBUtil.executeUpdate(localPreparedStatement, "insert into T_ContactInfo (ContactInfoId,CreateDate,DayPhone,EvePhone,Fax,Email,Addr1,Addr2,Addr3,City,StateProv,PostalCode,Country,URL) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into T_ContactRelationship (Id,IdType,ContactType,ContactInfoId) values (?,?,?,?)");
        localPreparedStatement.setLong(1, paramLong);
        localPreparedStatement.setString(2, paramString1);
        localPreparedStatement.setString(3, paramString2);
        localPreparedStatement.setInt(4, i1);
        DBUtil.executeUpdate(localPreparedStatement, "insert into T_ContactRelationship (Id,IdType,ContactType,ContactInfoId) values (?,?,?,?)");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void a(Connection paramConnection, long paramLong1, String paramString, long paramLong2, Contact paramContact)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    if (paramLong2 == 0L) {
      paramLong2 = a(paramConnection, paramLong1, paramString);
    }
    if (paramLong2 == 0L) {
      throw new Exception("Couldn't find contact in modifyContact");
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update T_ContactInfo set DayPhone=?,EvePhone=?,Fax=?,Email=?,Addr1=?,Addr2=?,Addr3=?,City=?,StateProv=?,PostalCode=?,Country=?,URL=?,ModifiedDate=? where contactInfoId=?");
      int i1 = 1;
      localPreparedStatement.setString(i1++, paramContact.getPhone());
      localPreparedStatement.setString(i1++, paramContact.getPhone2());
      localPreparedStatement.setString(i1++, paramContact.getFaxPhone());
      localPreparedStatement.setString(i1++, paramContact.getEmail());
      localPreparedStatement.setString(i1++, paramContact.getStreet());
      localPreparedStatement.setString(i1++, paramContact.getStreet2());
      localPreparedStatement.setString(i1++, (String)paramContact.get("ADDR3"));
      localPreparedStatement.setString(i1++, paramContact.getState());
      localPreparedStatement.setString(i1++, paramContact.getZipCode());
      localPreparedStatement.setString(i1++, paramContact.getCountry());
      localPreparedStatement.setString(i1++, (String)paramContact.get("URL"));
      localPreparedStatement.setTimestamp(i1++, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setLong(i1++, paramLong2);
      DBUtil.executeUpdate(localPreparedStatement, "update T_ContactInfo set DayPhone=?,EvePhone=?,Fax=?,Email=?,Addr1=?,Addr2=?,Addr3=?,City=?,StateProv=?,PostalCode=?,Country=?,URL=?,ModifiedDate=? where contactInfoId=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static int a(Connection paramConnection, long paramLong, String paramString)
    throws Exception
  {
    int i1 = 0;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ContactInfoId from T_ContactRelationship where Id=? and IdType=?");
      localPreparedStatement.setLong(1, paramLong);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ContactInfoId from T_ContactRelationship where Id=? and IdType=?");
      if (localResultSet.next()) {
        i1 = localResultSet.getInt("CONTACTINFOID");
      }
      int i2 = i1;
      return i2;
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static int a(Connection paramConnection, long paramLong1, long paramLong2, String paramString)
    throws Exception
  {
    int i1 = 0;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select AccountId from T_Account where ConsumerId=? and BillerId=? and AccountNumber=? and (StatusCode='ACTIVE' or StatusCode='NEW' or StatusCode='PEND')");
      int i2 = 1;
      localPreparedStatement.setLong(i2++, paramLong1);
      localPreparedStatement.setLong(i2++, paramLong2);
      localPreparedStatement.setString(i2++, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select AccountId from T_Account where ConsumerId=? and BillerId=? and AccountNumber=? and (StatusCode='ACTIVE' or StatusCode='NEW' or StatusCode='PEND')");
      if (localResultSet.next()) {
        i1 = localResultSet.getInt("ACCOUNTID");
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return i1;
  }
  
  private static String a(Connection paramConnection, long paramLong)
    throws Exception
  {
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    String str = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select StatusCode from T_Account where AccountId=?");
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select StatusCode from T_Account where AccountId=?");
      if (localResultSet.next()) {
        str = localResultSet.getString("STATUSCODE");
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return str;
  }
  
  private static int a(Connection paramConnection, EBillAccount paramEBillAccount)
    throws Exception
  {
    int i1 = 0;
    PreparedStatement localPreparedStatement = null;
    Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
    try
    {
      DBUtil.getNextId(paramConnection, dbType, "s_acct");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into T_Account (AccountId,ConsumerId,AccountNumber,StatusCode,CreateDate,BillerId,NameAcctHeld,CurrAcctId,PreAuthToken,ReasonText) values (?,?,?,?,?,?,?,?,?,?)");
      int i2 = 1;
      localPreparedStatement.setInt(i2++, i1);
      localPreparedStatement.setLong(i2++, paramEBillAccount.getConsumerIDValue());
      localPreparedStatement.setString(i2++, paramEBillAccount.getAccountNumber());
      localPreparedStatement.setString(i2++, "NEW");
      localPreparedStatement.setTimestamp(i2++, localTimestamp);
      localPreparedStatement.setLong(i2++, paramEBillAccount.getBillerIDValue());
      localPreparedStatement.setString(i2++, paramEBillAccount.getAccountHoldersName());
      localPreparedStatement.setInt(i2++, i1);
      localPreparedStatement.setString(i2++, paramEBillAccount.getPreAuthToken());
      localPreparedStatement.setString(i2++, paramEBillAccount.getReasonText());
      DBUtil.executeUpdate(localPreparedStatement, "insert into T_Account (AccountId,ConsumerId,AccountNumber,StatusCode,CreateDate,BillerId,NameAcctHeld,CurrAcctId,PreAuthToken,ReasonText) values (?,?,?,?,?,?,?,?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into FFI_Account (AccountId,Nickname,PayeeId) values (?,?,?)");
      i2 = 1;
      localPreparedStatement.setInt(i2++, i1);
      localPreparedStatement.setString(i2++, paramEBillAccount.getNickName());
      localPreparedStatement.setString(i2++, paramEBillAccount.getPayeeID());
      DBUtil.executeUpdate(localPreparedStatement, "insert into FFI_Account (AccountId,Nickname,PayeeId) values (?,?,?)");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i1;
  }
  
  private static void a(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    if (paramInt <= 0) {
      throw new Exception("Invalid account ID in modifyAccountStatus");
    }
    try
    {
      paramString = jdField_if(paramConnection, paramInt, paramString);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update T_Account set StatusCode=?,ModifiedDate=? where AccountId=?");
      int i1 = 1;
      localPreparedStatement.setString(i1++, paramString);
      localPreparedStatement.setTimestamp(i1++, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setInt(i1++, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update T_Account set StatusCode=?,ModifiedDate=? where AccountId=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static String jdField_if(Connection paramConnection, long paramLong, String paramString)
  {
    String str = null;
    try
    {
      if (Profile.hasValue(paramString))
      {
        str = a(paramConnection, paramLong);
        if ((str != null) && (str.equals("NEW"))) {
          if (paramString.equals("DELPEN")) {
            paramString = "DELETE";
          } else {
            paramString = "";
          }
        }
      }
    }
    catch (Exception localException) {}
    return paramString;
  }
  
  private static void a(Connection paramConnection, EBillAccount paramEBillAccount, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (!Profile.isValidId(paramEBillAccount.getAccountIDValue())) {
        throw new Exception("Invalid account ID in modifyAccount");
      }
      boolean bool = Profile.hasValue(paramString);
      if (bool)
      {
        paramString = jdField_if(paramConnection, paramEBillAccount.getAccountIDValue(), paramString);
        localStringBuffer.append("update T_Account set SATrnUID=?,SDTrnUID=?,statusCode=? where AccountId=?");
      }
      else
      {
        localStringBuffer.append("update T_Account set SATrnUID=?,SDTrnUID=? where AccountId=?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i1 = 1;
      localPreparedStatement.setString(i1++, (String)paramEBillAccount.get("SATRNUID"));
      localPreparedStatement.setString(i1++, (String)paramEBillAccount.get("SDTRNUID"));
      if (bool) {
        localPreparedStatement.setString(i1++, paramString);
      }
      localPreparedStatement.setLong(i1++, paramEBillAccount.getAccountIDValue());
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update FFI_ACCOUNT set NickName=?,PayeeId=? where AccountId=?");
      i1 = 1;
      localPreparedStatement.setString(i1++, paramEBillAccount.getNickName());
      localPreparedStatement.setString(i1++, paramEBillAccount.getPayeeID());
      localPreparedStatement.setLong(i1++, paramEBillAccount.getAccountIDValue());
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdField_if(Connection paramConnection, long paramLong)
    throws Exception
  {
    if (paramLong == -1L) {
      throw new Exception("Invalid consumer ID in addConsumerStatus");
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ConsumerId from FFI_ConsumerStatus where ConsumerId=?");
      localPreparedStatement.setLong(1, paramLong);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ConsumerId from FFI_ConsumerStatus where ConsumerId=?");
      if (!localResultSet.next())
      {
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into FFI_ConsumerStatus (ConsumerId,TCId,TCAccepted,BillDueNotifyDesired,BillDueNotifyDays,NewBillNotifyDesired,AccountInfoNotifyDesired,NewBillerNotifyDesired) values (?,?,?,?,?,?,?,?)");
        localPreparedStatement.setLong(1, paramLong);
        localPreparedStatement.setInt(2, 0);
        localPreparedStatement.setString(3, "N");
        localPreparedStatement.setString(4, "N");
        localPreparedStatement.setInt(5, 0);
        localPreparedStatement.setString(6, "N");
        localPreparedStatement.setString(7, "N");
        localPreparedStatement.setString(8, "N");
        DBUtil.executeUpdate(localPreparedStatement, "insert into FFI_ConsumerStatus (ConsumerId,TCId,TCAccepted,BillDueNotifyDesired,BillDueNotifyDays,NewBillNotifyDesired,AccountInfoNotifyDesired,NewBillerNotifyDesired) values (?,?,?,?,?,?,?,?)");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static TCURL addTCURL(TCURL paramTCURL)
    throws MultiException
  {
    String str = "OneViewAdapter.addTCURL";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i1 = 0;
    if ((paramTCURL == null) || (!Profile.hasValue(paramTCURL.getURL()))) {
      throw new MultiException(str, 6017, "invalid TC URL");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      i1 = DBUtil.getNextId(localConnection, dbType, "TCID");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FFI_TCUrl (TCId,TCURL,PrintableURL) values(?,?,?)");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setString(2, paramTCURL.getURL());
      localPreparedStatement.setString(3, paramTCURL.getPrintableURL());
      DBUtil.executeUpdate(localPreparedStatement, "insert into FFI_TCUrl (TCId,TCURL,PrintableURL) values(?,?,?)");
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramTCURL;
  }
  
  public static TCURL getTCURL(TCURL paramTCURL)
    throws MultiException
  {
    String str = "OneViewAdapter.getTCURL";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    TCURL localTCURL = null;
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from FFI_TCUrl where TCId=?");
      localPreparedStatement.setLong(1, paramTCURL.getIDValue());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from FFI_TCUrl where TCId=?");
      if (localResultSet.next())
      {
        localTCURL = new TCURL();
        localTCURL.setID(localResultSet.getString("TCID"));
        localTCURL.setURL(localResultSet.getString("TCURL"));
        localTCURL.setPrintableURL(localResultSet.getString("PRINTABLEURL"));
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return localTCURL;
  }
  
  public static TCURL modifyTCURL(TCURL paramTCURL)
    throws MultiException
  {
    String str = "OneViewAdapter.modifyTCURL";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if ((paramTCURL == null) || (!Profile.isValidId(paramTCURL.getIDValue()))) {
      throw new MultiException(str, 6018, "Invalid TC Id");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FFI_TCUrl set TCURL=?,PrintableURL=? where TCId=?");
      localPreparedStatement.setString(1, paramTCURL.getURL());
      localPreparedStatement.setString(2, paramTCURL.getPrintableURL());
      localPreparedStatement.setLong(3, paramTCURL.getIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update FFI_TCUrl set TCURL=?,PrintableURL=? where TCId=?");
    }
    catch (Exception localException)
    {
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
    return paramTCURL;
  }
  
  public static void deleteTCURL(TCURL paramTCURL)
    throws MultiException
  {
    String str = "OneViewAdapter.deleteTCURL";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    if ((paramTCURL == null) || (!Profile.isValidId(paramTCURL.getIDValue()))) {
      throw new MultiException(str, 6018, "Invalid TC Id");
    }
    try
    {
      localConnection = DBUtil.getConnection(poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FFI_ConsumerStatus set TCId=0 where TCId=");
      localPreparedStatement.setLong(1, paramTCURL.getIDValue());
      DBUtil.executeUpdate(localPreparedStatement, "update FFI_ConsumerStatus set TCId=0 where TCId=");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FFI_TCUrl where TCId=");
      localPreparedStatement.setLong(1, paramTCURL.getIDValue());
      DBUtil.execute(localPreparedStatement, "delete from FFI_TCUrl where TCId=");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void handleError(Exception paramException, String paramString)
    throws MultiException
  {
    DebugLog.throwing("OneViewAdapter.handleError: ", paramException);
    if ((paramException instanceof MultiException)) {
      throw ((MultiException)paramException);
    }
    throw new MultiException(paramString, 6002, paramException);
  }
  
  public static void handleError(Exception paramException, String paramString, int paramInt)
    throws MultiException
  {
    DebugLog.throwing("OneViewAdapter.handleError: ", paramException);
    if ((paramException instanceof MultiException)) {
      throw ((MultiException)paramException);
    }
    throw new MultiException(paramString, paramInt, paramException);
  }
  
  public static void handleError(String paramString1, int paramInt, String paramString2)
    throws MultiException
  {
    DebugLog.log("OneViewAdapter.handleError: where: " + paramString1 + " why: " + paramString2 + " code: " + paramInt);
    throw new MultiException(paramString1, paramInt, paramString2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.billpresentment.OneViewAdapter
 * JD-Core Version:    0.7.0.1
 */