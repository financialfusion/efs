package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueueResponses;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.messages.Messages;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Messaging3;
import com.ffusion.util.XMLTag;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class MessageCenterDemo
  implements Messaging3
{
  private Messages dg;
  private Messages di;
  private MessageThreads dh;
  private MessageQueues db;
  private String dd;
  private String df;
  private int de = 10000;
  private int dc = 10000;
  public static final String MESSAGE_THREADS = "MESSAGE_THREADS";
  public static final String DEMO_INIT_URL = "demo.xml";
  
  public MessageCenterDemo()
  {
    initialize("demo.xml");
  }
  
  public void setSettings(String paramString)
  {
    this.dd = paramString;
  }
  
  public String getSettings()
  {
    return this.dd;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if (paramString1 == null) {
          paramString1 = str;
        } else {
          this.df = str;
        }
      }
    }
    catch (Exception localException)
    {
      i = 1;
    }
    return i;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public void setPassword(String paramString) {}
  
  public void setUserName(String paramString) {}
  
  public int getMessages(SecureUser paramSecureUser, Messages paramMessages)
  {
    if (this.dg != null)
    {
      Iterator localIterator = this.dg.iterator();
      while (localIterator.hasNext()) {
        paramMessages.add(localIterator.next());
      }
    }
    return 0;
  }
  
  public int sendMessage(SecureUser paramSecureUser, Message paramMessage)
  {
    if (this.dg != null) {
      this.dg.add(paramMessage);
    }
    return 0;
  }
  
  public int sendMessage(SecureUser paramSecureUser, Message paramMessage, Object paramObject)
  {
    return 0;
  }
  
  public int deleteMessage(SecureUser paramSecureUser, Object paramObject)
  {
    return 0;
  }
  
  public int getMessages(SecureUser paramSecureUser, Object paramObject, Messages paramMessages)
  {
    if (this.di != null)
    {
      Iterator localIterator = this.di.iterator();
      while (localIterator.hasNext()) {
        paramMessages.add(localIterator.next());
      }
    }
    return 0;
  }
  
  public int sendReply(SecureUser paramSecureUser, Message paramMessage, Object paramObject)
  {
    return 0;
  }
  
  public int sendInternalQuery(SecureUser paramSecureUser, Message paramMessage, MessageThread paramMessageThread)
  {
    return 0;
  }
  
  public int addMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue)
  {
    paramMessageQueue.setQueueID(String.valueOf(this.dc));
    this.dc += 1;
    return 0;
  }
  
  public int deleteMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue)
  {
    return 0;
  }
  
  public int addMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse)
  {
    paramMessageQueueResponse.setResponseID(String.valueOf(this.de));
    this.de += 1;
    return 0;
  }
  
  public int deleteMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse)
  {
    return 0;
  }
  
  public int modifyMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue)
  {
    return 0;
  }
  
  public int modifyMessageQueueMembersByQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue)
  {
    return 0;
  }
  
  public int modifyMessageQueueMembersByEmployee(SecureUser paramSecureUser, String paramString, MessageQueues paramMessageQueues1, MessageQueues paramMessageQueues2)
  {
    return 0;
  }
  
  public int deleteMessageQueueMembers(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3)
  {
    return 0;
  }
  
  public int modifyMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse)
  {
    return 0;
  }
  
  public int modifyMessageThread(SecureUser paramSecureUser, MessageThread paramMessageThread)
  {
    return 0;
  }
  
  public int getMessageThreads(SecureUser paramSecureUser, Object paramObject, MessageThreads paramMessageThreads, HashMap paramHashMap)
  {
    if (this.dh != null)
    {
      Iterator localIterator = this.dh.iterator();
      while (localIterator.hasNext()) {
        paramMessageThreads.add(localIterator.next());
      }
    }
    return 0;
  }
  
  public int getNumberOfUnreadMessages(SecureUser paramSecureUser, Object paramObject)
  {
    return 0;
  }
  
  public int markMessageAsRead(SecureUser paramSecureUser, Object paramObject)
  {
    return 0;
  }
  
  public int getMessageQueues(SecureUser paramSecureUser, MessageQueues paramMessageQueues)
  {
    if (this.db != null)
    {
      Iterator localIterator = this.db.iterator();
      while (localIterator.hasNext()) {
        paramMessageQueues.add(localIterator.next());
      }
    }
    return 0;
  }
  
  public MessageQueues getMessageQueues(SecureUser paramSecureUser, MessageQueue paramMessageQueue)
  {
    return null;
  }
  
  public int initialize(String paramString)
  {
    int i = 0;
    try
    {
      Locale localLocale = new Locale("en", "US");
      String str = new String(Service.getXMLString(this, paramString));
      if (str.length() == 0)
      {
        i = 7;
      }
      else
      {
        XMLTag localXMLTag1 = new XMLTag();
        localXMLTag1.build(str);
        if (localXMLTag1 != null)
        {
          this.dg = new Messages(localLocale);
          XMLTag localXMLTag2;
          Object localObject1;
          Object localObject2;
          Object localObject3;
          Object localObject4;
          if ((localXMLTag2 = localXMLTag1.getContainedTag("MESSAGES")) != null)
          {
            localObject1 = localXMLTag2.getContainedTagList();
            if (localObject1 != null)
            {
              localObject2 = ((ArrayList)localObject1).iterator();
              while (((Iterator)localObject2).hasNext())
              {
                localObject3 = (XMLTag)((Iterator)localObject2).next();
                if (((XMLTag)localObject3).getTagName().equalsIgnoreCase("MESSAGE"))
                {
                  localObject4 = this.dg.createNoAdd();
                  if (updateMessageFromXML((XMLTag)localObject3, (Message)localObject4)) {
                    this.dg.add(localObject4);
                  }
                }
              }
            }
          }
          if ((localXMLTag2 = localXMLTag1.getContainedTag("MESSAGE_ITEMS")) != null)
          {
            this.di = new Messages(localLocale);
            localObject1 = localXMLTag2.getContainedTagList();
            if (localObject1 != null)
            {
              localObject2 = ((ArrayList)localObject1).iterator();
              while (((Iterator)localObject2).hasNext())
              {
                localObject3 = (XMLTag)((Iterator)localObject2).next();
                if (((XMLTag)localObject3).getTagName().equalsIgnoreCase("MESSAGE_ITEM"))
                {
                  localObject4 = this.di.createNoAdd();
                  if (updateMessageFromXML((XMLTag)localObject3, (Message)localObject4)) {
                    this.di.add(localObject4);
                  }
                }
              }
            }
          }
          Object localObject5;
          if ((localXMLTag2 = localXMLTag1.getContainedTag("QUEUES")) != null)
          {
            this.db = new MessageQueues();
            localObject1 = localXMLTag2.getContainedTagList();
            if (localObject1 != null)
            {
              localObject2 = ((ArrayList)localObject1).iterator();
              while (((Iterator)localObject2).hasNext())
              {
                localObject3 = (XMLTag)((Iterator)localObject2).next();
                localObject4 = ((XMLTag)localObject3).getTagName();
                if (((XMLTag)localObject3).getTagName().equalsIgnoreCase("QUEUE"))
                {
                  localObject5 = this.db.createNoAdd();
                  if (updateQueueFromXML((XMLTag)localObject3, (MessageQueue)localObject5)) {
                    this.db.add(localObject5);
                  }
                }
              }
            }
          }
          if ((localXMLTag2 = localXMLTag1.getContainedTag("MESSAGE_THREADS")) != null)
          {
            localObject1 = null;
            this.dh = new MessageThreads(localLocale);
            if ((localObject1 = localXMLTag2.getContainedTag("UNREAD_MESSAGES")) != null) {
              this.dh.setNewCount(((XMLTag)localObject1).getTagContent());
            }
            if ((localXMLTag2 = localXMLTag2.getContainedTag("MESSAGES")) != null)
            {
              localObject2 = localXMLTag2.getContainedTagList();
              if (localObject2 != null)
              {
                localObject3 = ((ArrayList)localObject2).iterator();
                while (((Iterator)localObject3).hasNext())
                {
                  localObject4 = (XMLTag)((Iterator)localObject3).next();
                  if (((XMLTag)localObject4).getTagName().equalsIgnoreCase("MESSAGE"))
                  {
                    localObject5 = this.dh.createNoAdd();
                    if (updateThreadFromXML((XMLTag)localObject4, (MessageThread)localObject5)) {
                      this.dh.add(localObject5);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("Error initializing file" + localThrowable);
      i = 8;
    }
    return i;
  }
  
  protected boolean updateQueueFromXML(XMLTag paramXMLTag, MessageQueue paramMessageQueue)
  {
    ArrayList localArrayList1 = null;
    boolean bool = false;
    localArrayList1 = paramXMLTag.getContainedTagList();
    Iterator localIterator1 = localArrayList1.iterator();
    bool = true;
    while (localIterator1.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator1.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("QUEUE_ID"))
      {
        paramMessageQueue.setQueueID(str2);
      }
      else if (str1.equals("QUEUE_NAME"))
      {
        paramMessageQueue.setQueueName(str2);
      }
      else if (str1.equals("QUEUE_TYPE"))
      {
        paramMessageQueue.setQueueType(str2);
      }
      else if (str1.equals("STATUS"))
      {
        paramMessageQueue.setQueueStatus(str2);
      }
      else if (str1.equals("STATUS_ID"))
      {
        paramMessageQueue.setQueueStatusID(str2);
      }
      else if (str1.equals("STATUS_NAME"))
      {
        paramMessageQueue.setQueueStatusName(str2);
      }
      else if (str1.equals("PRODUCT_ID"))
      {
        paramMessageQueue.setQueueProductID(str2);
      }
      else if (str1.equals("PRODUCT_NAME"))
      {
        paramMessageQueue.setQueueProductDesc(str2);
      }
      else if (str1.equals("AUTOREPLY_TEXT"))
      {
        paramMessageQueue.setQueueAutoReplyText(str2);
      }
      else if (str1.equals("SUBJECT"))
      {
        paramMessageQueue.setQueueAutoReplySubject(str2);
      }
      else if (str1.equals("FROM_ID"))
      {
        paramMessageQueue.setQueueAutoReplyEmployeeID(str2);
      }
      else if (str1.equals("EMAIL_ADDRESS"))
      {
        paramMessageQueue.setQueueAutoReplyEmailAddress(str2);
      }
      else
      {
        ArrayList localArrayList2;
        Iterator localIterator2;
        Object localObject1;
        Object localObject2;
        Object localObject3;
        if (str1.equals("QUEUE_MEMBERS"))
        {
          localArrayList2 = paramXMLTag.getContainedTagList();
          if (localArrayList2 != null)
          {
            localIterator2 = localArrayList2.iterator();
            localObject1 = paramMessageQueue.getActiveQueueMembers();
            localObject2 = paramMessageQueue.getInactiveQueueMembers();
            while (localIterator2.hasNext())
            {
              localObject3 = (XMLTag)localIterator2.next();
              if (((XMLTag)localObject3).getTagName().equalsIgnoreCase("QUEUE_MEMBER"))
              {
                BankEmployee localBankEmployee = ((MessageQueueMembers)localObject1).createNoAdd();
                updateQueueMemberFromXML((XMLTag)localObject3, localBankEmployee, (MessageQueueMembers)localObject1, (MessageQueueMembers)localObject2);
              }
            }
          }
        }
        else if (str1.equals("RESPONSES"))
        {
          localArrayList2 = paramXMLTag.getContainedTagList();
          if (localArrayList2 != null)
          {
            localIterator2 = localArrayList2.iterator();
            localObject1 = paramMessageQueue.getQueueResponses();
            while (localIterator2.hasNext())
            {
              localObject2 = (XMLTag)localIterator2.next();
              if (((XMLTag)localObject2).getTagName().equalsIgnoreCase("RESPONSE"))
              {
                localObject3 = ((MessageQueueResponses)localObject1).createNoAdd();
                if (updateQueueResponseFromXML((XMLTag)localObject2, (MessageQueueResponse)localObject3)) {
                  ((MessageQueueResponses)localObject1).add(localObject3);
                }
              }
            }
          }
        }
      }
    }
    return bool;
  }
  
  protected boolean updateQueueResponseFromXML(XMLTag paramXMLTag, MessageQueueResponse paramMessageQueueResponse)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("RESPONSE_ID")) {
        paramMessageQueueResponse.setResponseID(str2);
      } else if (str1.equals("RESPONSE_NAME")) {
        paramMessageQueueResponse.setResponseName(str2);
      } else if (str1.equals("RESPONSE_TEXT")) {
        paramMessageQueueResponse.setResponseText(str2);
      }
    }
    return bool;
  }
  
  protected boolean updateQueueMemberFromXML(XMLTag paramXMLTag, BankEmployee paramBankEmployee, MessageQueueMembers paramMessageQueueMembers1, MessageQueueMembers paramMessageQueueMembers2)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    int i = 0;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("EMPLOYEE_ID"))
      {
        paramBankEmployee.setId(str2);
      }
      else if (str1.equals("FIRST_NAME"))
      {
        paramBankEmployee.setFirstName(str2);
      }
      else if (str1.equals("LAST_NAME"))
      {
        paramBankEmployee.setLastName(str2);
      }
      else if (str1.equals("STATUS"))
      {
        Integer localInteger = new Integer(str2);
        if ((localInteger != null) && (localInteger.intValue() == 0))
        {
          paramMessageQueueMembers1.add(paramBankEmployee);
          i = 1;
        }
        else
        {
          paramMessageQueueMembers2.add(paramBankEmployee);
          i = 1;
        }
      }
    }
    if (i == 0) {
      paramMessageQueueMembers2.add(paramBankEmployee);
    }
    return bool;
  }
  
  protected boolean updateThreadFromXML(XMLTag paramXMLTag, MessageThread paramMessageThread)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("MESSAGE_ID")) {
        paramMessageThread.setThreadID(str2);
      } else if (str1.equals("QUEUE_ID")) {
        paramMessageThread.setQueueID(str2);
      } else if (str1.equals("QUEUE_NAME")) {
        paramMessageThread.setQueueName(str2);
      } else if (str1.equals("EMPLOYEE_ID")) {
        paramMessageThread.setEmployeeID(str2);
      } else if (str1.equals("EMPLOYEE_NAME")) {
        paramMessageThread.setEmployeeName(str2);
      } else if (str1.equals("DIRECTORY_ID")) {
        paramMessageThread.setDirectoryID(str2);
      } else if (str1.equals("CUSTOMER_NAME")) {
        paramMessageThread.setDirectoryName(str2);
      } else if (str1.equals("EMAIL_ADDRESS")) {
        paramMessageThread.setDirectoryEmail(str2);
      } else if (str1.equals("SUBJECT")) {
        paramMessageThread.setSubject(str2);
      } else if (str1.equals("STATUS")) {
        paramMessageThread.setThreadStatus(str2);
      } else if (str1.equals("CREATE_DATE")) {
        paramMessageThread.setCreateDate(XMLUtil.parseNewDateTime(str2));
      } else if (str1.equals("CLOSE_DATE")) {
        paramMessageThread.setClosedDate(XMLUtil.parseNewDateTime(str2));
      } else if (str1.equals("UNREAD_ITEMS")) {
        paramMessageThread.setNewThreadStatus(str2);
      }
    }
    return bool;
  }
  
  protected boolean updateMessageFromXML(XMLTag paramXMLTag, Message paramMessage)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("ITEM_ID")) {
        paramMessage.setID(str2);
      }
      if (str1.equals("MESSAGE_ID")) {
        paramMessage.set("MESSAGE_ID", str2);
      } else if (str1.equals("SUBJECT")) {
        paramMessage.setSubject(str2);
      } else if (str1.equals("CREATE_DATE")) {
        paramMessage.setDate(XMLUtil.parseNewDateTime(str2));
      } else if (str1.equals("DELETE_DATE")) {
        paramMessage.setArchivedDate(XMLUtil.parseNewDateTime(str2));
      } else if (str1.equals("READ_DATE")) {
        paramMessage.setReadDate(XMLUtil.parseNewDateTime(str2));
      } else if (str1.equals("FROM_ID")) {
        paramMessage.set("FROM_ID", str2);
      } else if (str1.equals("TO_ID")) {
        paramMessage.set("TO_ID", str2);
      } else if (str1.equals("FROM_TYPE")) {
        paramMessage.setFromType(str2);
      } else if (str1.equals("TO_TYPE")) {
        paramMessage.setToType(str2);
      } else if (str1.equals("FROM_NAME")) {
        paramMessage.setFrom(str2);
      } else if (str1.equals("TO_NAME")) {
        paramMessage.setTo(str2);
      } else if (str1.equals("MESSAGE_TEXT")) {
        paramMessage.setMemo(str2);
      } else if (str1.equals("MESSAGE_TYPE")) {
        paramMessage.setType(str2);
      } else {
        paramMessage.set(str1, str2);
      }
    }
    return bool;
  }
  
  public Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Messages getMessages(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThread sendMessage(SecureUser paramSecureUser, Message paramMessage, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Message deleteMessage(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Message markMessageAsRead(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public int getNumberOfUnreadMessages(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    return 0;
  }
  
  public int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    return 0;
  }
  
  public MessageThreads findMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2)
    throws ProfileException
  {
    return null;
  }
  
  public void createNewCase(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString, boolean paramBoolean)
    throws ProfileException
  {}
  
  public Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws ProfileException
  {
    return null;
  }
  
  public void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException
  {}
  
  public void denyMessage(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {}
  
  public int getAssignedMessageCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return 0;
  }
  
  public MessageCounts getMessageCountsByHelpCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageCounts getMessageCountsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageCounts getMessageCountsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageCounts getMessageCountsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThreads getThreadsByAssigned(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThreads getThreadsByHelpProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThreads getThreadsByHelpRequested(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThreads getThreadsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThreads getThreadsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public MessageThreads getThreadsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public void reassignThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread, String paramString)
    throws ProfileException
  {}
  
  public Message sendInternalMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException
  {
    return null;
  }
  
  public Message sendReplyMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, boolean paramBoolean)
    throws ProfileException
  {
    return null;
  }
  
  public void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, BusinessEmployees paramBusinessEmployees, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public void deleteGlobalMessage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public void modifyGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, BusinessEmployees paramBusinessEmployees, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public int approvalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return 0;
  }
  
  public String getNotificationSubject()
  {
    return "";
  }
  
  public String getNotificationMemo()
  {
    return "";
  }
  
  public String getNotificationFromEmail()
  {
    return "";
  }
  
  public Messages getMessagesByThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.MessageCenterDemo
 * JD-Core Version:    0.7.0.1
 */