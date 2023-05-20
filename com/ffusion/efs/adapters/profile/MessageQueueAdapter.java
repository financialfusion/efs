package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMember;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueueResponses;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MessageQueueAdapter
  implements ProfileDefines
{
  protected static MessageAdapter messageAdapter = new MessageAdapter();
  private static final String agL = "select * from queue";
  private static final String agQ = "insert into queue (queue_id,bank_id,queue_type,queue_name,product_id,status_id,status,autoreply_text,subject,from_id,email_address,description,modified_date,IS_CONSUMER,IS_CORPORATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String ag6 = "update queue set bank_id=?,queue_type=?,queue_name=?,product_id=?,status_id=?,status=?,autoreply_text=?,subject=?,from_id=?,email_address=?,description=?,modified_date=?,IS_CONSUMER=?,IS_CORPORATE=? where queue_id = ?";
  private static final String agO = "select p.title, s.name from product p, status s where p.bank_id=s.bank_id and p.bank_id=? and p.product_id=? and s.status_id=?";
  private static final String ag5 = "select queue_id from message where queue_id=?";
  private static final String agW = "update queue set status=? where queue_id=?";
  private static final String agN = "delete from queue where queue_id=?";
  private static final String agU = "select queue_id from queue where ";
  private static final String agR = "select * from queue_i18n where queue_id=?";
  private static final String ag9 = "insert into queue_i18n (queue_id,language,queue_name,autoreply_text,subject,description) values(?,?,?,?,?,?)";
  private static final String ag1 = "update queue_i18n set queue_name=?,autoreply_text=?,subject=?,description=? where queue_id=? and language=?";
  private static final String ag0 = "delete from queue_i18n where queue_id=?";
  private static final String agT = "select * from product_i18n where bank_id = ? and product_id=?";
  private static final String agS = " and language=?";
  private static final String aha = "select * from status_i18n where bank_id = ? and status_id=?";
  private static final String ag8 = " and language=?";
  private static final String ahb = "select qm.employee_id,qm.status,e.first_name,e.last_name,e.user_name,e.email_address from queue q, queue_members qm, bank_employee e where q.queue_id=qm.queue_id and qm.employee_id=e.employee_id and qm.queue_id=? and q.status != 1";
  private static final String ag7 = "insert into queue_members (queue_id,employee_id,status) values (?,?,?)";
  private static final String ag4 = "delete from queue_members where queue_id=?";
  private static final String ahd = "delete from queue_members where employee_id=?";
  private static final String agP = "insert into queue_response (response_id,queue_id,response_name,modified_date,updated_by) values(?,?,?,?,?)";
  private static final String agZ = "select * from queue_response where queue_id=?";
  private static final String ahc = "select text from response_body where response_id=? order by seq";
  private static final String agV = "insert into response_body (response_id,seq,text) values(?,?,?)";
  private static final String agY = "update queue_response set response_name=?,modified_date=?,updated_by=? where response_id=?";
  private static final String ag3 = "delete from queue_response where queue_id=?";
  private static final String agM = "delete from response_body where response_id in (select response_id from queue_response where queue_id=?)";
  private static final String ag2 = "delete from queue_response where response_id=?";
  private static final String agX = "delete from response_body where response_id=?";
  public static final String SQL_CHECK_UNIQUE_QUEUE_NAME = "select * from queue where queue_name = ? and product_id = ? and status_id = ?";
  public static final String SQL_CHECK_EXISTING_QUEUE_NAME = "select queue_name from queue where queue_id = ? ";
  
  public static MessageQueues getQueues(MessageQueue paramMessageQueue)
    throws ProfileException
  {
    return getQueues(paramMessageQueue, null);
  }
  
  public static MessageQueues getQueues(MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "MessageQueueAdapter.getQueues";
    Profile.isInitialized();
    StringBuffer localStringBuffer = new StringBuffer("select * from queue");
    MessageQueues localMessageQueues = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = paramMessageQueue.getBankId();
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid bank id", 4);
    }
    String str3 = paramMessageQueue.getSearchLanguage();
    try
    {
      boolean bool1 = false;
      if (!str2.equals("*")) {
        bool1 = Profile.appendSQLSelectString(localStringBuffer, "", "bank_id", str2, false, false, bool1, false);
      }
      String str4 = paramMessageQueue.getQueueID();
      Profile.appendSQLSelectInt(localStringBuffer, "queue_id", str4, bool1);
      Profile.appendSQLSelectInt(localStringBuffer, "queue_type", paramMessageQueue.getQueueType(), bool1);
      boolean bool2 = paramMessageQueue.getIsCorporate();
      boolean bool3 = paramMessageQueue.getIsConsumer();
      if (!bool2) {
        bool1 = Profile.appendSQLSelectString(localStringBuffer, "IS_CONSUMER", "Y", bool1);
      }
      if (!bool3) {
        bool1 = Profile.appendSQLSelectString(localStringBuffer, "IS_CORPORATE", "Y", bool1);
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i = 1;
      if (!str2.equals("*")) {
        i = Profile.setStatementString(localPreparedStatement, i, str2, "bank_id", false, false, true, false);
      }
      i = Profile.setStatementInt(localPreparedStatement, i, str4, true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramMessageQueue.getQueueType(), true);
      if (!bool2) {
        i = Profile.setStatementString(localPreparedStatement, i, "Y", "IS_CONSUMER", true);
      }
      if (!bool3) {
        i = Profile.setStatementString(localPreparedStatement, i, "Y", "IS_CORPORATE", true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localMessageQueues = new MessageQueues(paramMessageQueue.getLocale());
      int j = 1;
      while (localResultSet.next())
      {
        MessageQueue localMessageQueue = localMessageQueues.create();
        jdMethod_for(localConnection, localResultSet, str3, localMessageQueue);
        jdMethod_for(localConnection, localResultSet.getInt("queue_id"), localMessageQueue);
        if (paramHashMap != null)
        {
          String str5 = (String)paramHashMap.get("GetBankEmployees");
          if ((str5 != null) && (str5.equals("no"))) {
            j = 0;
          }
        }
        if (j != 0) {
          jdMethod_for(localConnection, localMessageQueue);
        }
        if (localResultSet.getInt("queue_type") == 1) {
          jdMethod_for(localConnection, localResultSet.getInt("product_id"), localResultSet.getInt("status_id"), str3, localMessageQueue);
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
    return localMessageQueues;
  }
  
  private static void jdMethod_for(Connection paramConnection, ResultSet paramResultSet, String paramString, MessageQueue paramMessageQueue)
    throws Exception
  {
    paramMessageQueue.setQueueID(String.valueOf(paramResultSet.getInt("queue_id")));
    paramMessageQueue.setBankId(Profile.getRSString(paramResultSet, "bank_id"));
    paramMessageQueue.setQueueType(String.valueOf(paramResultSet.getInt("queue_type")));
    paramMessageQueue.setQueueProductID(String.valueOf(paramResultSet.getInt("product_id")));
    paramMessageQueue.setQueueStatusID(String.valueOf(paramResultSet.getInt("status_id")));
    paramMessageQueue.setQueueStatus(String.valueOf(paramResultSet.getInt("status")));
    paramMessageQueue.setQueueAutoReplyEmployeeID(String.valueOf(paramResultSet.getInt("from_id")));
    paramMessageQueue.setQueueAutoReplyEmailAddress(Profile.getRSString(paramResultSet, "email_address"));
    paramMessageQueue.setSearchLanguage(paramString);
    paramMessageQueue.setCurrentLanguage(paramString);
    if (Profile.getRSString(paramResultSet, "IS_CORPORATE").equals("N")) {
      paramMessageQueue.setIsCorporate(false);
    }
    if (Profile.getRSString(paramResultSet, "IS_CONSUMER").equals("N")) {
      paramMessageQueue.setIsConsumer(false);
    }
    Timestamp localTimestamp = paramResultSet.getTimestamp("modified_date");
    if (localTimestamp != null) {
      paramMessageQueue.setModifiedDate(localTimestamp);
    }
    if (paramString == null)
    {
      paramMessageQueue.setQueueName(Profile.getRSString(paramResultSet, "queue_name"));
      paramMessageQueue.setQueueAutoReplyText(Profile.getRSString(paramResultSet, "autoreply_text"));
      paramMessageQueue.setQueueAutoReplySubject(Profile.getRSString(paramResultSet, "subject"));
      paramMessageQueue.setDescription(Profile.getRSString(paramResultSet, "description"));
      jdMethod_for(paramConnection, null, paramMessageQueue);
    }
    else if (paramString.equals("en_US"))
    {
      paramMessageQueue.setQueueName(Profile.getRSString(paramResultSet, "queue_name"));
      paramMessageQueue.setQueueAutoReplyText(Profile.getRSString(paramResultSet, "autoreply_text"));
      paramMessageQueue.setQueueAutoReplySubject(Profile.getRSString(paramResultSet, "subject"));
      paramMessageQueue.setDescription(Profile.getRSString(paramResultSet, "description"));
    }
    else
    {
      jdMethod_for(paramConnection, paramString, paramMessageQueue);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, String paramString, MessageQueue paramMessageQueue)
    throws Exception
  {
    String str1 = "MessageQueueAdapter.getMessageQueueI18N";
    StringBuffer localStringBuffer = new StringBuffer("select * from queue_i18n where queue_id=?");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramMessageQueue.getQueueID(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        if ((paramString == null) || (str2.equalsIgnoreCase(paramString)))
        {
          paramMessageQueue.setQueueName(str2, Profile.getRSString(localResultSet, "queue_name"));
          paramMessageQueue.setQueueAutoReplyText(str2, Profile.getRSString(localResultSet, "autoreply_text"));
          paramMessageQueue.setQueueAutoReplySubject(str2, Profile.getRSString(localResultSet, "subject"));
          paramMessageQueue.setDescription(str2, Profile.getRSString(localResultSet, "description"));
        }
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
  }
  
  public static MessageQueue addQueue(MessageQueue paramMessageQueue)
    throws ProfileException
  {
    String str1 = "MessageQueueAdapter.addQueue";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = paramMessageQueue.getBankId();
    int i = 0;
    int j = 0;
    int k = 0;
    String str3 = null;
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid bank id", 4022);
    }
    String str4 = paramMessageQueue.getQueueType();
    if (Profile.isValidInt(str4)) {
      i = Profile.convertToInt(str4);
    } else {
      Profile.handleError(str1, "Invalid Queue Type", 8621);
    }
    if (i == 0)
    {
      str3 = paramMessageQueue.getQueueName("en_US");
      if ((str3 == null) || (str3.length() == 0))
      {
        Iterator localIterator1 = paramMessageQueue.getLanguages();
        while (localIterator1.hasNext())
        {
          String str5 = (String)localIterator1.next();
          str3 = paramMessageQueue.getQueueName(str5);
          if (str3 != null) {
            break;
          }
        }
        if ((str3 == null) || (str3.length() == 0)) {
          Profile.handleError(str1, "Invalid queue name", 8620);
        }
      }
    }
    else if (i == 1)
    {
      j = Profile.convertToInt(paramMessageQueue.getQueueProductID());
      k = Profile.convertToInt(paramMessageQueue.getQueueStatusID());
      if (!Profile.isValidId(j)) {
        Profile.handleError(str1, "Invalid product id", 8622);
      }
      if (!Profile.isValidId(k)) {
        Profile.handleError(str1, "Invalid status id", 8623);
      }
    }
    else
    {
      Profile.handleError(str1, "Invalid queue type", 8621);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      str3 = paramMessageQueue.getQueueName("en_US");
      if (str3.length() == 0) {
        str3 = null;
      }
      if (str3 != null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from queue where queue_name = ? and product_id = ? and status_id = ?");
        localPreparedStatement.setString(1, str3);
        localPreparedStatement.setInt(2, j);
        localPreparedStatement.setInt(3, k);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from queue where queue_name = ? and product_id = ? and status_id = ?");
        if (localResultSet.next()) {
          Profile.handleError(str1, "Non-unique queue name", 8619);
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
      }
      int m = DBUtil.getNextId(localConnection, Profile.dbType, "queue_id");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into queue (queue_id,bank_id,queue_type,queue_name,product_id,status_id,status,autoreply_text,subject,from_id,email_address,description,modified_date,IS_CONSUMER,IS_CORPORATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      int n = 1;
      n = Profile.setStatementInt(localPreparedStatement, n, m, true);
      n = Profile.setStatementString(localPreparedStatement, n, str2, "bank_id", false);
      n = Profile.setStatementInt(localPreparedStatement, n, i, false);
      n = Profile.setStatementString(localPreparedStatement, n, str3, "queue_name", false);
      n = Profile.setStatementInt(localPreparedStatement, n, j, false);
      n = Profile.setStatementInt(localPreparedStatement, n, k, false);
      n = Profile.setStatementInt(localPreparedStatement, n, 0, false);
      n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getQueueAutoReplyText("en_US"), "autoreply_text", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getQueueAutoReplySubject("en_US"), "subject", false);
      n = Profile.setStatementInt(localPreparedStatement, n, paramMessageQueue.getQueueAutoReplyEmployeeID(), false);
      n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getQueueAutoReplyEmailAddress(), "email_address", false);
      n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getDescription("en_US"), "description", false);
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      n = Profile.setStatementDate(localPreparedStatement, n, localTimestamp, true);
      if (paramMessageQueue.getIsConsumer() == true) {
        n = Profile.setStatementString(localPreparedStatement, n, "Y", "IS_CONSUMER", false);
      } else {
        n = Profile.setStatementString(localPreparedStatement, n, "N", "IS_CONSUMER", false);
      }
      if (paramMessageQueue.getIsCorporate() == true) {
        n = Profile.setStatementString(localPreparedStatement, n, "Y", "IS_CORPORATE", false);
      } else {
        n = Profile.setStatementString(localPreparedStatement, n, "N", "IS_CONSUMER", false);
      }
      DBUtil.executeUpdate(localPreparedStatement, "insert into queue (queue_id,bank_id,queue_type,queue_name,product_id,status_id,status,autoreply_text,subject,from_id,email_address,description,modified_date,IS_CONSUMER,IS_CORPORATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      Iterator localIterator2 = paramMessageQueue.getLanguages();
      if (localIterator2 != null) {
        while (localIterator2.hasNext())
        {
          String str6 = (String)localIterator2.next();
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into queue_i18n (queue_id,language,queue_name,autoreply_text,subject,description) values(?,?,?,?,?,?)");
          n = 1;
          n = Profile.setStatementInt(localPreparedStatement, n, m, true);
          n = Profile.setStatementString(localPreparedStatement, n, str6, "language", false);
          str3 = paramMessageQueue.getQueueName(str6);
          if ((str3 != null) && (str3.length() == 0)) {
            str3 = null;
          }
          n = Profile.setStatementString(localPreparedStatement, n, str3, "queue_name", false);
          n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getQueueAutoReplyText(str6), "autoreply_text", false);
          n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getQueueAutoReplySubject(str6), "subject", false);
          n = Profile.setStatementString(localPreparedStatement, n, paramMessageQueue.getDescription(str6), "description", false);
          DBUtil.executeUpdate(localPreparedStatement, "insert into queue_i18n (queue_id,language,queue_name,autoreply_text,subject,description) values(?,?,?,?,?,?)");
          DBUtil.closeStatement(localPreparedStatement);
          localPreparedStatement = null;
        }
      }
      paramMessageQueue.setQueueID(String.valueOf(m));
      paramMessageQueue.setModifiedDate(localTimestamp);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return paramMessageQueue;
  }
  
  public static void modifyQueue(MessageQueue paramMessageQueue)
    throws ProfileException
  {
    String str1 = "MessageQueueAdapter.modifyQueue";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i = Profile.convertToInt(paramMessageQueue.getQueueID());
    if (!Profile.isValidId(i)) {
      Profile.handleError(str1, "Invalid queue ID", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = paramMessageQueue.getQueueName("en_US");
      if ((str2 != null) && (str2.length() == 0)) {
        str2 = null;
      }
      if (str2 != null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select queue_name from queue where queue_id = ? ");
        localPreparedStatement.setInt(1, i);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select queue_name from queue where queue_id = ? ");
        String str3 = null;
        if (localResultSet.next()) {
          str3 = localResultSet.getString(1);
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
        if ((str3 == null) || ((str3 != null) && (!str3.equals(str2))))
        {
          int k = 0;
          int m = 0;
          int n = 0;
          String str5 = paramMessageQueue.getQueueType();
          if (Profile.isValidInt(str5)) {
            n = Profile.convertToInt(str5);
          } else {
            Profile.handleError(str1, "Invalid Queue Type", 3);
          }
          if (n == 1)
          {
            k = Profile.convertToInt(paramMessageQueue.getQueueProductID());
            m = Profile.convertToInt(paramMessageQueue.getQueueStatusID());
          }
          localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from queue where queue_name = ? and product_id = ? and status_id = ?");
          localPreparedStatement.setString(1, str2);
          localPreparedStatement.setInt(2, k);
          localPreparedStatement.setInt(3, m);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from queue where queue_name = ? and product_id = ? and status_id = ?");
          if (localResultSet.next()) {
            Profile.handleError(str1, "Non-unique queue name", 3);
          }
          DBUtil.closeAll(localPreparedStatement, localResultSet);
          localResultSet = null;
          localPreparedStatement = null;
        }
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update queue set bank_id=?,queue_type=?,queue_name=?,product_id=?,status_id=?,status=?,autoreply_text=?,subject=?,from_id=?,email_address=?,description=?,modified_date=?,IS_CONSUMER=?,IS_CORPORATE=? where queue_id = ?");
      int j = 1;
      j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getBankId(), "bank_id", false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramMessageQueue.getQueueType()), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueName("en_US"), "queue_name", false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramMessageQueue.getQueueProductID()), false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramMessageQueue.getQueueStatusID()), false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramMessageQueue.getQueueStatus()), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueAutoReplyText("en_US"), "autoreply_text", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueAutoReplySubject("en_US"), "subject", false);
      j = Profile.setStatementInt(localPreparedStatement, j, Profile.convertToInt(paramMessageQueue.getQueueAutoReplyEmployeeID()), false);
      j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueAutoReplyEmailAddress(), "email_address", false);
      j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getDescription("en_US"), "description", false);
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      j = Profile.setStatementDate(localPreparedStatement, j, localTimestamp, true);
      if (paramMessageQueue.getIsConsumer() == true) {
        j = Profile.setStatementString(localPreparedStatement, j, "Y", "IS_CONSUMER", false);
      } else {
        j = Profile.setStatementString(localPreparedStatement, j, "N", "IS_CONSUMER", false);
      }
      if (paramMessageQueue.getIsCorporate() == true) {
        j = Profile.setStatementString(localPreparedStatement, j, "Y", "IS_CORPORATE", false);
      } else {
        j = Profile.setStatementString(localPreparedStatement, j, "N", "IS_CORPORATE", false);
      }
      j = Profile.setStatementInt(localPreparedStatement, j, i, true);
      DBUtil.executeUpdate(localPreparedStatement, "update queue set bank_id=?,queue_type=?,queue_name=?,product_id=?,status_id=?,status=?,autoreply_text=?,subject=?,from_id=?,email_address=?,description=?,modified_date=?,IS_CONSUMER=?,IS_CORPORATE=? where queue_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      jdMethod_new(localConnection, i);
      Iterator localIterator = paramMessageQueue.getLanguages();
      if (localIterator != null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into queue_i18n (queue_id,language,queue_name,autoreply_text,subject,description) values(?,?,?,?,?,?)");
        while (localIterator.hasNext())
        {
          String str4 = (String)localIterator.next();
          j = 1;
          j = Profile.setStatementInt(localPreparedStatement, j, i, true);
          j = Profile.setStatementString(localPreparedStatement, j, str4, "language", false);
          j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueName(str4), "queue_name", false);
          j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueAutoReplyText(str4), "autoreply_text", false);
          j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getQueueAutoReplySubject(str4), "subject", false);
          j = Profile.setStatementString(localPreparedStatement, j, paramMessageQueue.getDescription(str4), "description", false);
          DBUtil.executeUpdate(localPreparedStatement, "insert into queue_i18n (queue_id,language,queue_name,autoreply_text,subject,description) values(?,?,?,?,?,?)");
        }
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      paramMessageQueue.setModifiedDate(localTimestamp);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static void deleteQueue(MessageQueue paramMessageQueue)
    throws ProfileException
  {
    String str = "MessageQueueAdapter.deleteQueue";
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_char(localConnection, Profile.convertToInt(paramMessageQueue.getQueueID()));
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
  
  private static void jdMethod_char(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select queue_id from message where queue_id=?");
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, paramInt, false);
      ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, "select queue_id from message where queue_id=?");
      if (localResultSet.next()) {
        i = 1;
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      if (i != 0)
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update queue set status=? where queue_id=?");
        j = 1;
        j = Profile.setStatementInt(localPreparedStatement, j, 1, false);
        j = Profile.setStatementInt(localPreparedStatement, j, paramInt, false);
        DBUtil.executeUpdate(localPreparedStatement, "update queue set status=? where queue_id=?");
      }
      else
      {
        jdMethod_try(paramConnection, paramInt);
        jdMethod_case(paramConnection, paramInt);
        jdMethod_new(paramConnection, paramInt);
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from queue where queue_id=?");
        j = 1;
        j = Profile.setStatementInt(localPreparedStatement, j, paramInt, false);
        DBUtil.executeUpdate(localPreparedStatement, "delete from queue where queue_id=?");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static void deleteQueueTX(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select queue_id from queue where ");
    boolean bool = false;
    bool = Profile.appendSQLSelectInt(localStringBuffer, "product_id", paramInt1, bool);
    bool = Profile.appendSQLSelectInt(localStringBuffer, "status_id", paramInt2, bool);
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      try
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
        int i = 1;
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, true);
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, true);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        while (localResultSet.next()) {
          jdMethod_char(paramConnection, localResultSet.getInt("queue_id"));
        }
      }
      finally
      {
        DBUtil.closeAll(localPreparedStatement, localResultSet);
      }
    }
  }
  
  public static void modifyQueueMembersByQueue(String paramString, MessageQueueMembers paramMessageQueueMembers1, MessageQueueMembers paramMessageQueueMembers2)
    throws ProfileException
  {
    String str = "MessageQueueAdapter.modifyQueueMembersByQueue";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = Integer.parseInt(paramString);
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid queue Id", 4);
    }
    if ((paramMessageQueueMembers1 == null) || (paramMessageQueueMembers2 == null)) {
      Profile.handleError(str, "No queue members", 3);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_try(localConnection, i);
      MessageQueueMember localMessageQueueMember = null;
      Iterator localIterator = paramMessageQueueMembers1.iterator();
      int j;
      while (localIterator.hasNext())
      {
        localMessageQueueMember = (MessageQueueMember)localIterator.next();
        j = Profile.convertToInt(localMessageQueueMember.getId());
        jdMethod_for(localConnection, i, j, 0);
      }
      localIterator = paramMessageQueueMembers2.iterator();
      while (localIterator.hasNext())
      {
        localMessageQueueMember = (MessageQueueMember)localIterator.next();
        j = Profile.convertToInt(localMessageQueueMember.getId());
        jdMethod_for(localConnection, i, j, 1);
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
      DBUtil.closeAll(Profile.poolName, localConnection, null);
    }
  }
  
  public static void modifyQueueMembersByEmployee(String paramString1, String paramString2, MessageQueues paramMessageQueues1, MessageQueues paramMessageQueues2)
    throws ProfileException
  {
    String str = "MessageQueueAdapter.modifyQueueMembersByEmployee";
    Profile.isInitialized();
    Connection localConnection = null;
    int i = Profile.convertToInt(paramString1);
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid employee Id", 4);
    }
    int j = -1;
    try
    {
      j = Integer.parseInt(paramString2);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (j < 0) {
      Profile.handleError(str, "Invalid queue type ", 3606);
    }
    if ((paramMessageQueues1 == null) || (paramMessageQueues2 == null)) {
      Profile.handleError(str, "No queues", 3);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_int(localConnection, i, j);
      MessageQueue localMessageQueue = null;
      Iterator localIterator = paramMessageQueues1.iterator();
      int k;
      while (localIterator.hasNext())
      {
        localMessageQueue = (MessageQueue)localIterator.next();
        k = Profile.convertToInt(localMessageQueue.getId());
        jdMethod_for(localConnection, k, i, 0);
      }
      localIterator = paramMessageQueues2.iterator();
      while (localIterator.hasNext())
      {
        localMessageQueue = (MessageQueue)localIterator.next();
        k = Profile.convertToInt(localMessageQueue.getId());
        jdMethod_for(localConnection, k, i, 1);
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
      DBUtil.closeAll(Profile.poolName, localConnection, null);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into queue_members (queue_id,employee_id,status) values (?,?,?)");
    int i = 1;
    i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
    i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
    i = Profile.setStatementInt(localPreparedStatement, i, paramInt3, false);
    DBUtil.executeUpdate(localPreparedStatement, "insert into queue_members (queue_id,employee_id,status) values (?,?,?)");
    DBUtil.closeStatement(localPreparedStatement);
  }
  
  public static void deleteQueueMemberByEmployeeIdAndQueueType(String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "MessageQueueAdapter.deleteQueueMember";
    Connection localConnection = null;
    int i = Profile.convertToInt(paramString1);
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid employee Id", 4);
    }
    int j = -1;
    try
    {
      j = Integer.parseInt(paramString2);
    }
    catch (NumberFormatException localNumberFormatException) {}
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_int(localConnection, i, j);
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
  
  private static void jdMethod_for(Connection paramConnection, MessageQueue paramMessageQueue)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select qm.employee_id,qm.status,e.first_name,e.last_name,e.user_name,e.email_address from queue q, queue_members qm, bank_employee e where q.queue_id=qm.queue_id and qm.employee_id=e.employee_id and qm.queue_id=? and q.status != 1");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramMessageQueue.getQueueID(), false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select qm.employee_id,qm.status,e.first_name,e.last_name,e.user_name,e.email_address from queue q, queue_members qm, bank_employee e where q.queue_id=qm.queue_id and qm.employee_id=e.employee_id and qm.queue_id=? and q.status != 1");
      MessageQueueMembers localMessageQueueMembers1 = new MessageQueueMembers();
      MessageQueueMembers localMessageQueueMembers2 = new MessageQueueMembers();
      while (localResultSet.next())
      {
        MessageQueueMember localMessageQueueMember;
        if (localResultSet.getInt("status") == 0)
        {
          localMessageQueueMember = (MessageQueueMember)localMessageQueueMembers1.create();
          localMessageQueueMember.setId(String.valueOf(localResultSet.getInt("employee_id")));
          localMessageQueueMember.setFirstName(Profile.getRSString(localResultSet, "first_name"));
          localMessageQueueMember.setLastName(Profile.getRSString(localResultSet, "last_name"));
          localMessageQueueMember.setUserName(Profile.getRSString(localResultSet, "user_name"));
          localMessageQueueMember.setEmail(Profile.getRSString(localResultSet, "email_address"));
          localMessageQueueMember.setQueueEmpStatus(String.valueOf(0));
        }
        else
        {
          localMessageQueueMember = (MessageQueueMember)localMessageQueueMembers2.create();
          localMessageQueueMember.setId(String.valueOf(localResultSet.getInt("employee_id")));
          localMessageQueueMember.setFirstName(Profile.getRSString(localResultSet, "first_name"));
          localMessageQueueMember.setLastName(Profile.getRSString(localResultSet, "last_name"));
          localMessageQueueMember.setUserName(Profile.getRSString(localResultSet, "user_name"));
          localMessageQueueMember.setEmail(Profile.getRSString(localResultSet, "email_address"));
          localMessageQueueMember.setQueueEmpStatus(String.valueOf(1));
        }
      }
      paramMessageQueue.setActiveQueueMembers(localMessageQueueMembers1);
      paramMessageQueue.setInactiveQueueMembers(localMessageQueueMembers2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_try(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from queue_members where queue_id=?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from queue_members where queue_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static void deleteQueueMemberByEmployeeIdTX(Connection paramConnection, int paramInt)
    throws Exception
  {
    String str = "MessageQueueAdapter.deleteQueueMemberByEmployeeIdTX";
    Profile.isInitialized();
    if (!Profile.isValidId(paramInt)) {
      Profile.handleError(str, "Invalid employee Id", 4);
    }
    jdMethod_int(paramConnection, paramInt, -1);
  }
  
  private static void jdMethod_int(Connection paramConnection, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("delete from queue_members where employee_id=?");
    try
    {
      if (paramInt2 >= 0) {
        localStringBuffer.append(" and queue_id in (select queue_id from queue where queue_type=?)");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      if (paramInt2 >= 0) {
        i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
      }
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public static MessageQueueResponse addQueueResponse(MessageQueueResponse paramMessageQueueResponse)
    throws ProfileException
  {
    String str1 = "MessageQueueAdapter.addQueueResponse";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = -1;
    int j = Profile.convertToInt(paramMessageQueueResponse.getQueueID());
    if (!Profile.isValidId(j)) {
      Profile.handleError(str1, "Invalid Queue Id", 8626);
    }
    int k = Profile.convertToInt(paramMessageQueueResponse.getModifierId());
    if (!Profile.isValidId(k)) {
      Profile.handleError(str1, "Invalid employee Id", 8624);
    }
    String str2 = paramMessageQueueResponse.getResponseName();
    if ((str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "Invalid response name", 8625);
    }
    String str3 = paramMessageQueueResponse.getResponseText();
    if ((str3 == null) || (str3.length() == 0)) {
      Profile.handleError(str1, "Invalid response text", 3818);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      i = DBUtil.getNextId(localConnection, Profile.dbType, "response_id");
      paramMessageQueueResponse.setResponseID(String.valueOf(i));
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into queue_response (response_id,queue_id,response_name,modified_date,updated_by) values(?,?,?,?,?)");
      int m = 1;
      m = Profile.setStatementInt(localPreparedStatement, m, i, false);
      m = Profile.setStatementInt(localPreparedStatement, m, j, false);
      m = Profile.setStatementString(localPreparedStatement, m, str2, "response_name", false);
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      m = Profile.setStatementDate(localPreparedStatement, m, localTimestamp, true);
      m = Profile.setStatementInt(localPreparedStatement, m, k, false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into queue_response (response_id,queue_id,response_name,modified_date,updated_by) values(?,?,?,?,?)");
      jdMethod_for(localConnection, i, str3);
      DBUtil.commit(localConnection);
      paramMessageQueueResponse.setModifiedDate(localTimestamp);
      paramMessageQueueResponse.setModifierName(messageAdapter.getIdName(localConnection, k, 0, paramMessageQueueResponse.getLocale(), null));
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
    return paramMessageQueueResponse;
  }
  
  public static void deleteQueueResponse(String paramString)
    throws ProfileException
  {
    String str = "MessageQueueAdapter.deleteQueueResponse";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = Integer.parseInt(paramString);
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid response id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_byte(localConnection, i);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from queue_response where response_id=?");
      int j = 1;
      j = Profile.setStatementInt(localPreparedStatement, j, i, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from queue_response where response_id=?");
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
  
  public static void modifyQueueResponse(MessageQueueResponse paramMessageQueueResponse)
    throws ProfileException
  {
    String str = "MessageQueueAdapter.modifyQueueResponse";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = Profile.convertToInt(paramMessageQueueResponse.getResponseID());
    if (!Profile.isValidId(i)) {
      Profile.handleError(str, "Invalid response id", 4);
    }
    int j = Profile.convertToInt(paramMessageQueueResponse.getModifierId());
    if (!Profile.isValidId(j)) {
      Profile.handleError(str, "Invalid employeeId id", 4);
    }
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update queue_response set response_name=?,modified_date=?,updated_by=? where response_id=?");
      int k = 1;
      k = Profile.setStatementString(localPreparedStatement, k, paramMessageQueueResponse.getResponseName(), "response_name", false);
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      k = Profile.setStatementDate(localPreparedStatement, k, localTimestamp, true);
      k = Profile.setStatementInt(localPreparedStatement, k, j, false);
      k = Profile.setStatementInt(localPreparedStatement, k, i, false);
      DBUtil.executeUpdate(localPreparedStatement, "update queue_response set response_name=?,modified_date=?,updated_by=? where response_id=?");
      jdMethod_byte(localConnection, i);
      jdMethod_for(localConnection, i, paramMessageQueueResponse.getResponseText());
      DBUtil.commit(localConnection);
      paramMessageQueueResponse.setModifiedDate(localTimestamp);
      paramMessageQueueResponse.setModifierName(messageAdapter.getIdName(localConnection, j, 0, paramMessageQueueResponse.getLocale(), null));
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
  
  private static void jdMethod_for(Connection paramConnection, int paramInt, MessageQueue paramMessageQueue)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    MessageQueueResponses localMessageQueueResponses = new MessageQueueResponses();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from queue_response where queue_id=?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from queue_response where queue_id=?");
      while (localResultSet.next())
      {
        MessageQueueResponse localMessageQueueResponse = localMessageQueueResponses.create();
        localMessageQueueResponse.setQueueID(String.valueOf(localResultSet.getInt("queue_id")));
        localMessageQueueResponse.setResponseID(String.valueOf(localResultSet.getInt("response_id")));
        localMessageQueueResponse.setResponseName(Profile.getRSString(localResultSet, "response_name"));
        localMessageQueueResponse.setResponseText(jdMethod_int(paramConnection, localResultSet.getInt("response_id")));
        Timestamp localTimestamp = localResultSet.getTimestamp("modified_date");
        if (localTimestamp != null) {
          localMessageQueueResponse.setModifiedDate(localTimestamp);
        }
        int j = localResultSet.getInt("updated_by");
        localMessageQueueResponse.setModifierId(String.valueOf(j));
        String str = messageAdapter.getIdName(paramConnection, j, 0, localMessageQueueResponse.getLocale(), null);
        localMessageQueueResponse.setModifierName(str);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    paramMessageQueue.setQueueResponses(localMessageQueueResponses);
  }
  
  private static void jdMethod_case(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from response_body where response_id in (select response_id from queue_response where queue_id=?)");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from response_body where response_id in (select response_id from queue_response where queue_id=?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from queue_response where queue_id=?");
      i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from queue_response where queue_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_new(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      int i = 1;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from queue_i18n where queue_id=?");
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from queue_i18n where queue_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_byte(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from response_body where response_id=?");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      DBUtil.executeUpdate(localPreparedStatement, "delete from response_body where response_id=?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static String jdMethod_int(Connection paramConnection, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select text from response_body where response_id=? order by seq");
      int i = 1;
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select text from response_body where response_id=? order by seq");
      while (localResultSet.next()) {
        localStringBuffer.append(localResultSet.getString("text"));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localStringBuffer.toString();
  }
  
  private static void jdMethod_for(Connection paramConnection, int paramInt, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ArrayList localArrayList = Profile.breakupMessage(paramString);
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into response_body (response_id,seq,text) values(?,?,?)");
      for (int i = 0; i < localArrayList.size(); i++)
      {
        int j = 1;
        j = Profile.setStatementInt(localPreparedStatement, j, paramInt, false);
        j = Profile.setStatementInt(localPreparedStatement, j, i, false);
        j = Profile.setStatementString(localPreparedStatement, j, (String)localArrayList.get(i), "text", false);
        DBUtil.executeUpdate(localPreparedStatement, "insert into response_body (response_id,seq,text) values(?,?,?)");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, int paramInt1, int paramInt2, String paramString, MessageQueue paramMessageQueue)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if ((!Profile.isValidId(paramInt1)) || (!Profile.isValidId(paramInt2))) {
      return;
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select p.title, s.name from product p, status s where p.bank_id=s.bank_id and p.bank_id=? and p.product_id=? and s.status_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramMessageQueue.getBankId(), "bank_id", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select p.title, s.name from product p, status s where p.bank_id=s.bank_id and p.bank_id=? and p.product_id=? and s.status_id=?");
      if (localResultSet.next())
      {
        paramMessageQueue.setQueueProductDesc(Profile.getRSString(localResultSet, "title"));
        paramMessageQueue.setQueueStatusName(Profile.getRSString(localResultSet, "name"));
      }
      jdMethod_for(paramConnection, paramString, paramInt1, paramMessageQueue);
      jdMethod_int(paramConnection, paramString, paramInt2, paramMessageQueue);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, String paramString, int paramInt, MessageQueue paramMessageQueue)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getProductI18N";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select * from product_i18n where bank_id = ? and product_id=?");
      if (paramString != null) {
        localStringBuffer.append(" and language=?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramMessageQueue.getBankId(), "bank_id", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, true);
      if (paramString != null) {
        i = Profile.setStatementString(localPreparedStatement, i, paramString, "language", true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        paramMessageQueue.setQueueProductDesc(str2, Profile.getRSString(localResultSet, "title"));
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
  }
  
  private static void jdMethod_int(Connection paramConnection, String paramString, int paramInt, MessageQueue paramMessageQueue)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getStatusI18N";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select * from status_i18n where bank_id = ? and status_id=?");
      if (paramString != null) {
        localStringBuffer.append(" and language=?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramMessageQueue.getBankId(), "bank_id", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt, true);
      if (paramString != null) {
        i = Profile.setStatementString(localPreparedStatement, i, paramString, "language", true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        paramMessageQueue.setQueueStatusName(str2, Profile.getRSString(localResultSet, "name"));
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
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.MessageQueueAdapter
 * JD-Core Version:    0.7.0.1
 */