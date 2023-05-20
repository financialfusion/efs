package com.ffusion.services.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.BankMessageInfo;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.messages.Messages;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.csil.handlers.AffiliateBankAdmin;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.MessageAdapter;
import com.ffusion.efs.adapters.profile.MessageQueueAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Messaging8;
import com.ffusion.services.MessagingAdmin3;
import com.ffusion.services.MessagingFilters;
import com.ffusion.util.CollatorUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class MessagesService
  extends ProfileServices
  implements Messaging8, MessagingAdmin3
{
  public static final String SERVICE_INIT_XML = "messaging.xml";
  protected static final String NOTIFY_SUBJECT = "NOTIFY_SUBJECT";
  protected static final String NOTIFY_MEMO = "NOTIFY_MEMO";
  protected static final String NOTIFY_FROM = "NOTIFY_FROM";
  protected static final String FILTER = "FILTER";
  public static final String FILTER_IMPL_KEY = "messaginFilterKey";
  public static final String BANK_MESSAGE = "BANK_MESSAGE";
  public static final String BANK_NAME = "BANK_NAME";
  protected static final String BANK_NOTIFY_SUBJECT = "BANK_NOTIFY_SUBJECT";
  protected static final String BANK_NOTIFY_MEMO = "BANK_NOTIFY_MEMO";
  protected static final String BANK_NOTIFY_FROM = "BANK_NOTIFY_FROM";
  private HashMap dp = null;
  private ArrayList dj = null;
  protected String notifySubject = null;
  protected String notifyMemo = null;
  protected String notifyFromEmail = null;
  private int dq = -1;
  protected MessageAdapter messageAdapter = null;
  private MessagingFilters dm = null;
  private String dl = null;
  private boolean dk = true;
  private Collator dn = null;
  
  public int initialize(String paramString)
  {
    int i = 0;
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "messaging.xml";
    }
    try
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
      String str = Strings.streamToString(localInputStream);
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), str);
      XMLTag localXMLTag1 = new XMLTag(true);
      localXMLTag1.build(str);
      XMLTag localXMLTag2 = localXMLTag1.getContainedTag("BANK_MESSAGE");
      this.dj = new ArrayList();
      while (localXMLTag2 != null)
      {
        HashMap localHashMap = localXMLTag2.getTagHashMap();
        BankMessageInfo localBankMessageInfo = new BankMessageInfo();
        localBankMessageInfo.setBankName((String)localHashMap.get("BANK_NAME"));
        localBankMessageInfo.setNotifyMemo((String)localHashMap.get("BANK_NOTIFY_MEMO"));
        localBankMessageInfo.setNotifySubject((String)localHashMap.get("BANK_NOTIFY_SUBJECT"));
        localBankMessageInfo.setNotifyFromEmail((String)localHashMap.get("BANK_NOTIFY_FROM"));
        this.dj.add(localBankMessageInfo);
        localXMLTag1.removeContainedTag("BANK_MESSAGE");
        localXMLTag2 = localXMLTag1.getContainedTag("BANK_MESSAGE");
      }
      if ((this.dl != null) && (this.dl.length() > 0)) {
        this.dm = ((MessagingFilters)Class.forName(this.dl).newInstance());
      }
    }
    catch (Throwable localThrowable)
    {
      i = this.dq;
    }
    return i;
  }
  
  private void jdMethod_do(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("NOTIFY_SUBJECT")) {
      this.notifySubject = paramString2;
    } else if (paramString1.equalsIgnoreCase("NOTIFY_MEMO")) {
      this.notifyMemo = paramString2;
    } else if (paramString1.equalsIgnoreCase("NOTIFY_FROM")) {
      this.notifyFromEmail = paramString2;
    } else if (paramString1.equalsIgnoreCase("FILTER")) {
      this.dl = paramString2;
    }
  }
  
  public Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      boolean bool1 = false;
      boolean bool2 = false;
      if (paramHashMap.get("SENTMESSAGES") != null) {
        bool1 = true;
      }
      if (paramHashMap.get("RECEIVEDMESSAGES") != null) {
        bool2 = true;
      }
      if ((!bool1) && (!bool2))
      {
        bool1 = true;
        bool2 = true;
      }
      return this.messageAdapter.getMessageList(paramSecureUser, bool2, bool1);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws ProfileException
  {
    if (paramMessage == null) {
      throw new ProfileException(3812);
    }
    try
    {
      Message localMessage = this.messageAdapter.sendMessage(paramSecureUser, paramMessage);
      return localMessage;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThread sendMessage(SecureUser paramSecureUser, Message paramMessage, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    if (!(paramObject instanceof MessageThread)) {
      throw new ProfileException(3);
    }
    try
    {
      paramObject = this.messageAdapter.sendMessageThread(paramSecureUser, paramMessage);
      return (MessageThread)paramObject;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Message sendInternalMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException
  {
    try
    {
      Message localMessage = this.messageAdapter.sendInternalMessage(paramSecureUser, paramHashMap, paramMessage);
      return localMessage;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Message sendReplyMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, boolean paramBoolean)
    throws ProfileException
  {
    try
    {
      int i = paramMessage.getToValue();
      BankMessageInfo localBankMessageInfo = getEmailSettings(paramSecureUser, i, paramHashMap);
      Message localMessage = this.messageAdapter.sendReplyMessage(paramSecureUser, paramHashMap, paramMessage, localBankMessageInfo.getNotifyFromEmail(), localBankMessageInfo.getNotifySubject(), localBankMessageInfo.getNotifyMemo(), paramBoolean);
      return localMessage;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Message deleteMessage(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    Message localMessage = null;
    if ((paramObject instanceof Message)) {
      localMessage = (Message)paramObject;
    } else {
      throw new ProfileException(3);
    }
    try
    {
      this.messageAdapter.deleteMessage(Integer.parseInt(localMessage.getID()));
      return localMessage;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Message markMessageAsRead(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    Message localMessage = null;
    if ((paramObject instanceof Message)) {
      localMessage = (Message)paramObject;
    } else {
      throw new ProfileException(3);
    }
    try
    {
      this.messageAdapter.markMessageRead(Integer.parseInt(localMessage.getID()));
      return localMessage;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public int getNumberOfUnreadMessages(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    MessageThreads localMessageThreads = null;
    if ((paramObject instanceof MessageThreads)) {
      localMessageThreads = (MessageThreads)paramObject;
    } else {
      throw new ProfileException(3);
    }
    try
    {
      int i = this.messageAdapter.getUnreadMessageCount(paramSecureUser);
      ((MessageThreads)paramObject).setNewCount(i);
      return i;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    MessageThreads localMessageThreads = null;
    if ((paramObject instanceof MessageThreads)) {
      localMessageThreads = (MessageThreads)paramObject;
    } else {
      throw new ProfileException(3);
    }
    try
    {
      int i = this.messageAdapter.getUnreadMessageCountExcludingAlerts(paramSecureUser);
      ((MessageThreads)paramObject).setNewCount(i);
      return i;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Messages getMessages(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getMessageList(paramSecureUser);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Message sendReply(SecureUser paramSecureUser, Message paramMessage, Object paramObject, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      this.messageAdapter.sendReply(paramSecureUser, paramMessage);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessage;
  }
  
  public Message sendInternalQuery(SecureUser paramSecureUser, Message paramMessage, MessageThread paramMessageThread, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      this.messageAdapter.sendInternalQuery(paramSecureUser, paramMessage, paramMessageThread);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessage;
  }
  
  public MessageQueues getMessageQueues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    MessageQueue localMessageQueue = new MessageQueue();
    localMessageQueue.setLocale(paramSecureUser.getLocale());
    localMessageQueue.setBankId(paramSecureUser.getBankID());
    localMessageQueue.setQueueType("0");
    try
    {
      MessageQueues localMessageQueues = MessageQueueAdapter.getQueues(localMessageQueue, paramHashMap);
      return localMessageQueues;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageQueues getMessageQueues(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
  {
    String str = null;
    if (paramMessageQueue != null) {
      str = paramMessageQueue.getBankId();
    }
    if ((str == null) || (str.length() == 0)) {
      paramMessageQueue.setBankId(paramSecureUser.getBankID());
    }
    try
    {
      return MessageQueueAdapter.getQueues(paramMessageQueue, paramHashMap);
    }
    catch (Exception localException) {}
    return null;
  }
  
  public MessageQueue addMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException
  {
    MessageQueue localMessageQueue = null;
    try
    {
      localMessageQueue = MessageQueueAdapter.addQueue(paramMessageQueue);
      paramMessageQueue.set(localMessageQueue);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localMessageQueue;
  }
  
  public MessageQueue deleteMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.deleteQueue(paramMessageQueue);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageQueue;
  }
  
  public MessageQueue modifyMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.modifyQueue(paramMessageQueue);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageQueue;
  }
  
  public MessageQueueResponse addMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueResponse localMessageQueueResponse = MessageQueueAdapter.addQueueResponse(paramMessageQueueResponse);
      paramMessageQueueResponse.set(localMessageQueueResponse);
      return localMessageQueueResponse;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageQueueResponse deleteMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.deleteQueueResponse(paramMessageQueueResponse.getResponseID());
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageQueueResponse;
  }
  
  public MessageQueueResponse modifyMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.modifyQueueResponse(paramMessageQueueResponse);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageQueueResponse;
  }
  
  public MessageQueue modifyMessageQueueMembersByQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.modifyQueueMembersByQueue(paramMessageQueue.getId(), paramMessageQueue.getActiveQueueMembers(), paramMessageQueue.getInactiveQueueMembers());
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageQueue;
  }
  
  public void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, BusinessEmployees paramBusinessEmployees, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramBusinessEmployees.size(); i++)
      {
        BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramBusinessEmployees.get(i);
        localArrayList.add(localBusinessEmployee.getId());
      }
      createGlobalMessage(paramSecureUser, paramGlobalMessage, localArrayList, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      if ((paramGlobalMessage.getTemplateName() != null) && (paramGlobalMessage.getTemplateName().trim().length() > 0))
      {
        GlobalMessage localGlobalMessage = new GlobalMessage();
        localGlobalMessage.setRecordType(2);
        localGlobalMessage.setBankId(paramSecureUser.getBankID());
        localGlobalMessage.setTemplateName(paramGlobalMessage.getTemplateName());
        localGlobalMessage.setStatus(2);
        GlobalMessages localGlobalMessages = getGlobalMessages(paramSecureUser, localGlobalMessage, paramHashMap);
        if ((localGlobalMessages != null) && (localGlobalMessages.size() != 0)) {
          throw new ProfileException(3813);
        }
      }
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("messaginFilterKey", this.dm);
      this.messageAdapter.createGlobalMessage(paramSecureUser, paramGlobalMessage, paramArrayList, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void deleteGlobalMessage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      this.messageAdapter.deleteGlobalMessage(paramSecureUser, paramInt, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void modifyGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      if (paramGlobalMessage.getTemplateName() != null)
      {
        GlobalMessage localGlobalMessage1 = new GlobalMessage();
        localGlobalMessage1.setRecordType(2);
        localGlobalMessage1.setBankId(paramSecureUser.getBankID());
        localGlobalMessage1.setTemplateName(paramGlobalMessage.getTemplateName());
        localGlobalMessage1.setStatus(2);
        GlobalMessages localGlobalMessages = getGlobalMessages(paramSecureUser, localGlobalMessage1, paramHashMap);
        if ((localGlobalMessages != null) && (localGlobalMessages.size() > 0))
        {
          if (localGlobalMessages.size() > 1) {
            throw new ProfileException(3813);
          }
          GlobalMessage localGlobalMessage2 = (GlobalMessage)localGlobalMessages.get(0);
          if (!localGlobalMessage2.getGlobalMsgID().equals(paramGlobalMessage.getGlobalMsgID())) {
            throw new ProfileException(3813);
          }
        }
      }
      this.messageAdapter.modifyGlobalMessage(paramSecureUser, paramGlobalMessage, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException
  {
    GlobalMessages localGlobalMessages = null;
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("messaginFilterKey", this.dm);
      localGlobalMessages = this.messageAdapter.getGlobalMessages(paramSecureUser, paramGlobalMessage, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, HashMap paramHashMap)
    throws ProfileException
  {
    GlobalMessages localGlobalMessages = null;
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("messaginFilterKey", this.dm);
      localGlobalMessages = this.messageAdapter.getGlobalMessages(paramSecureUser, paramGlobalMessageSearchCriteria, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessages getGlobalLoginMessages(String paramString, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    GlobalMessages localGlobalMessages = null;
    try
    {
      localGlobalMessages = this.messageAdapter.getGlobalLoginMessages(paramString, paramInt, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localGlobalMessages;
  }
  
  public GlobalMessages getGlobalLoginMessages(String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    GlobalMessages localGlobalMessages = null;
    try
    {
      localGlobalMessages = this.messageAdapter.getGlobalLoginMessages(paramString, paramInt1, paramInt2, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localGlobalMessages;
  }
  
  public void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, BusinessEmployees paramBusinessEmployees, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramBusinessEmployees.size(); i++)
      {
        BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramBusinessEmployees.get(i);
        localArrayList.add(localBusinessEmployee.getId());
      }
      sendGlobalMessage(paramSecureUser, paramGlobalMessage, localArrayList, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("messaginFilterKey", this.dm);
      this.messageAdapter.sendGlobalMessage(paramSecureUser, paramGlobalMessage, paramArrayList, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public int approvalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 0;
    try
    {
      i = this.messageAdapter.approvalRequired(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return i;
  }
  
  public int globalMsgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 0;
    try
    {
      i = this.messageAdapter.globalMsgApprovalRequired(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return i;
  }
  
  public int msgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 0;
    try
    {
      i = this.messageAdapter.msgApprovalRequired(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return i;
  }
  
  public void modifyMessageQueueMembersByEmployee(SecureUser paramSecureUser, String paramString1, String paramString2, MessageQueues paramMessageQueues1, MessageQueues paramMessageQueues2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.modifyQueueMembersByEmployee(paramString1, paramString2, paramMessageQueues1, paramMessageQueues2);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThread modifyMessageThread(SecureUser paramSecureUser, MessageThread paramMessageThread, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      this.messageAdapter.modifyMessageThread(paramMessageThread);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageThread;
  }
  
  public MessageThreads getMessageThreads(SecureUser paramSecureUser, Object paramObject, MessageThreads paramMessageThreads, HashMap paramHashMap)
    throws ProfileException
  {
    Boolean localBoolean = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    if ((paramObject instanceof Boolean)) {
      localBoolean = (Boolean)paramObject;
    }
    Object localObject;
    if ((paramObject instanceof String))
    {
      localObject = null;
      StringTokenizer localStringTokenizer = null;
      localStringTokenizer = new StringTokenizer((String)paramObject, ",");
      if (localStringTokenizer.hasMoreTokens()) {
        str1 = localStringTokenizer.nextToken();
      }
      if (localStringTokenizer.hasMoreTokens()) {
        str2 = localStringTokenizer.nextToken();
      }
      if (localStringTokenizer.hasMoreTokens()) {
        localObject = localStringTokenizer.nextToken();
      }
      if (localStringTokenizer.hasMoreTokens()) {
        str3 = localStringTokenizer.nextToken();
      }
      if ((localObject != null) && ((((String)localObject).equalsIgnoreCase("false")) || (((String)localObject).equalsIgnoreCase("true")))) {
        localBoolean = new Boolean((String)localObject);
      }
    }
    if (paramMessageThreads.size() > 0)
    {
      localObject = (MessageThread)paramMessageThreads.get(0);
      str4 = ((MessageThread)localObject).getThreadStatus();
      paramMessageThreads.clear();
    }
    try
    {
      localObject = this.messageAdapter.getMessageThreads(paramSecureUser, paramHashMap);
      paramMessageThreads.set((MessageThreads)localObject);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return paramMessageThreads;
  }
  
  public String getMessageThreadsCount(SecureUser paramSecureUser, MessageThreads paramMessageThreads, HashMap paramHashMap)
    throws ProfileException
  {
    String str = null;
    try
    {
      str = this.messageAdapter.getMessageThreadsCount(paramSecureUser, paramMessageThreads, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return str;
  }
  
  public void deleteMessageQueueMembers(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageQueueAdapter.deleteQueueMemberByEmployeeIdAndQueueType(paramString1, paramString3);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads findMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2)
    throws ProfileException
  {
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = this.messageAdapter.findMessageThreads(paramSecureUser, paramHashMap, paramUser, paramBusiness, paramMessageThread, paramDateTime1, paramDateTime2);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localMessageThreads;
  }
  
  public void createNewCase(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString, boolean paramBoolean)
    throws ProfileException
  {
    try
    {
      int i = paramMessage.getToValue();
      BankMessageInfo localBankMessageInfo = getEmailSettings(paramSecureUser, i, paramHashMap);
      this.messageAdapter.createNewCase(paramSecureUser, paramHashMap, paramMessage, paramString, localBankMessageInfo.getNotifyFromEmail(), localBankMessageInfo.getNotifySubject(), localBankMessageInfo.getNotifyMemo(), paramBoolean);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws ProfileException
  {
    Messages localMessages = null;
    try
    {
      localMessages = this.messageAdapter.getMessages(paramSecureUser, paramHashMap, paramString);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localMessages;
  }
  
  public void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException
  {
    try
    {
      boolean bool = false;
      try
      {
        bool = Integer.parseInt(paramMessage.getMsgThreadStatus()) == 12;
      }
      catch (Exception localException2)
      {
        bool = false;
      }
      int i = this.messageAdapter.getMessageDirectoryID(Integer.parseInt(paramMessage.getMsgThreadID()));
      BankMessageInfo localBankMessageInfo = getEmailSettings(paramSecureUser, i, paramHashMap);
      this.messageAdapter.approveMessage(paramSecureUser, paramHashMap, paramMessage, localBankMessageInfo.getNotifyFromEmail(), localBankMessageInfo.getNotifySubject(), localBankMessageInfo.getNotifyMemo(), bool);
    }
    catch (Exception localException1)
    {
      throw new ProfileException(mapError(localException1), localException1);
    }
  }
  
  public void denyMessage(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {
    try
    {
      this.messageAdapter.denyMessage(paramSecureUser, paramHashMap, paramMessageThread);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public int getAssignedMessageCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      String str1 = null;
      String str2 = null;
      String str3 = null;
      if (paramHashMap != null)
      {
        str1 = (String)paramHashMap.get("EMPLOYEE_ID");
        str2 = (String)paramHashMap.get("QUEUE_ID");
        str3 = (String)paramHashMap.get("affiliate_bank");
      }
      if (str2 != null)
      {
        MessageCounts localMessageCounts1 = this.messageAdapter.getAssignedMessageCountsByQueue(paramSecureUser, str2, paramHashMap);
        paramHashMap.put("QueueAssignedCounts", localMessageCounts1);
        return 0;
      }
      if (str1 != null)
      {
        int i = 0;
        try
        {
          i = Integer.parseInt(str3);
        }
        catch (NumberFormatException localNumberFormatException) {}
        MessageCounts localMessageCounts2 = this.messageAdapter.getAssignedMessageCountsByEmployee(paramSecureUser, str1, paramHashMap, i);
        paramHashMap.put("EmployeeAssignedCounts", localMessageCounts2);
        return 0;
      }
      return this.messageAdapter.getAssignedMessageCount(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageCounts getMessageCountsByHelpCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getMessageCountsByHelpCases(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageCounts getMessageCountsByHelpCasesProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getMessageCountsByHelpCasesProvidedAndClosed(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageCounts getMessageCountsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getMessageCountsByNewCases(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageCounts getMessageCountsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getMessageCountsByPersonalBanker(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageCounts getMessageCountsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getMessageCountsByPending(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads getThreadsByAssigned(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getThreadsByAssigned(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads getThreadsByHelpProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getThreadsByHelpProvidedAndClosed(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads getThreadsByHelpRequested(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getThreadsByHelpRequested(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads getThreadsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getThreadsByNewCases(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads getThreadsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getThreadsByPersonalBanker(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public MessageThreads getThreadsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return this.messageAdapter.getThreadsByPending(paramSecureUser, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void reassignThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread, String paramString)
    throws ProfileException
  {
    try
    {
      this.messageAdapter.reassignThread(paramSecureUser, paramHashMap, paramMessageThread, paramString);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  protected int mapError(Exception paramException)
  {
    int i = 16002;
    paramException.printStackTrace(System.out);
    if ((paramException instanceof ProfileException))
    {
      ProfileException localProfileException = (ProfileException)paramException;
      DebugLog.log("mapProfileException: " + localProfileException.where + " : " + localProfileException.why + " : " + localProfileException.code);
      switch (localProfileException.code)
      {
      case 0: 
        i = 0;
        break;
      case 1: 
        i = 16001;
        break;
      case 12: 
        i = 0;
        break;
      case 9: 
        i = 19002;
        break;
      case 4022: 
        i = 4022;
        break;
      case 8620: 
        i = 8620;
        break;
      case 8621: 
        i = 8621;
        break;
      case 8626: 
        i = 8626;
        break;
      case 8619: 
        i = 8619;
        break;
      case 8622: 
        i = 8622;
        break;
      case 8623: 
        i = 8623;
        break;
      case 3814: 
        i = 8507;
        break;
      case 3815: 
        i = 8508;
        break;
      case 3817: 
        i = 8510;
        break;
      case 3816: 
        i = 8509;
        break;
      case 3818: 
        i = 8511;
        break;
      case 8625: 
        i = 8625;
        break;
      case 8624: 
        i = 8624;
        break;
      case 3503: 
      default: 
        i = 16002;
      }
    }
    return i;
  }
  
  public String getNotificationSubject()
  {
    return this.notifySubject;
  }
  
  public String getNotificationMemo()
  {
    return this.notifyMemo;
  }
  
  public String getNotificationFromEmail()
  {
    return this.notifyFromEmail;
  }
  
  public Messages getMessagesByThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException
  {
    Messages localMessages = null;
    try
    {
      localMessages = this.messageAdapter.getMessagesByThread(paramSecureUser, paramHashMap, paramMessageThread);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localMessages;
  }
  
  public void processGlobalMessages()
    throws ProfileException
  {
    GlobalMessages localGlobalMessages = null;
    GlobalMessage localGlobalMessage = null;
    GlobalMessageSearchCriteria localGlobalMessageSearchCriteria = new GlobalMessageSearchCriteria();
    HashMap localHashMap = new HashMap();
    localGlobalMessageSearchCriteria.addStatus(2);
    localGlobalMessageSearchCriteria.addMsgTypes(1);
    localGlobalMessageSearchCriteria.addMsgTypes(2);
    localGlobalMessageSearchCriteria.setRecordType(1);
    localGlobalMessageSearchCriteria.setSearchLanguage(null);
    localHashMap.put("messaginFilterKey", this.dm);
    try
    {
      localGlobalMessages = this.messageAdapter.getGlobalMessages(localGlobalMessageSearchCriteria, localHashMap);
      Iterator localIterator = localGlobalMessages.iterator();
      while (localIterator.hasNext())
      {
        localGlobalMessage = (GlobalMessage)localIterator.next();
        if (localHashMap == null) {
          localHashMap = new HashMap();
        }
        localHashMap.put("messaginFilterKey", this.dm);
        this.messageAdapter.sendGlobalMessage(localGlobalMessage, localHashMap);
      }
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public GlobalMessage getGlobalMessageById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    GlobalMessage localGlobalMessage = null;
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("messaginFilterKey", this.dm);
      localGlobalMessage = this.messageAdapter.getGlobalMessageById(paramSecureUser, paramInt, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localGlobalMessage;
  }
  
  public void modifyMessageThreadStatus(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      MessageAdapter.modifyMessageThreadStatus(paramInt1, paramInt2, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public BankMessageInfo getEmailSettings(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    BankMessageInfo localBankMessageInfo1 = new BankMessageInfo();
    this.dn = CollatorUtil.getCollator(paramSecureUser.getLocale());
    try
    {
      AffiliateBanks localAffiliateBanks = AffiliateBankAdmin.getAffiliateBanks(paramSecureUser, null);
      this.dp = new HashMap();
      for (int i = 0; i < localAffiliateBanks.size(); i++)
      {
        AffiliateBank localAffiliateBank = (AffiliateBank)localAffiliateBanks.get(i);
        for (int k = 0; k < this.dj.size(); k++)
        {
          localBankMessageInfo2 = (BankMessageInfo)this.dj.get(k);
          if ((localBankMessageInfo2.getBankName() != null) && (localAffiliateBank.getAffiliateBankName() != null) && (this.dn.compare(localBankMessageInfo2.getBankName(), localAffiliateBank.getAffiliateBankName()) == 0))
          {
            this.dp.put(Integer.toString(localAffiliateBank.getAffiliateBankID()), localBankMessageInfo2);
            a(localAffiliateBank, localBankMessageInfo2.getBankName(), null);
          }
        }
      }
      User localUser = CustomerAdapter.getUserById(paramInt);
      int j = localUser.getAffiliateBankID();
      String str = localUser.getPreferredLanguage();
      BankMessageInfo localBankMessageInfo2 = null;
      if (str != null) {
        localBankMessageInfo2 = (BankMessageInfo)this.dp.get(Integer.toString(j) + "_" + str);
      }
      if (localBankMessageInfo2 == null) {
        localBankMessageInfo2 = (BankMessageInfo)this.dp.get(Integer.toString(j));
      }
      if (localBankMessageInfo2 == null)
      {
        localBankMessageInfo1.setNotifyFromEmail(this.notifyFromEmail);
        localBankMessageInfo1.setNotifyMemo(this.notifyMemo);
        localBankMessageInfo1.setNotifySubject(this.notifySubject);
      }
      else
      {
        localBankMessageInfo1 = (BankMessageInfo)localBankMessageInfo2.clone();
      }
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
    return localBankMessageInfo1;
  }
  
  private void a(AffiliateBank paramAffiliateBank, String paramString1, String paramString2)
  {
    Object localObject;
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      a(paramAffiliateBank, paramString1, "en_US");
      localObject = paramAffiliateBank.getLanguages();
      while ((localObject != null) && (((Iterator)localObject).hasNext()))
      {
        paramString2 = (String)((Iterator)localObject).next();
        a(paramAffiliateBank, paramString1, paramString2);
      }
    }
    else if (paramAffiliateBank.getEmailSubject(paramString2) != null)
    {
      localObject = new BankMessageInfo();
      ((BankMessageInfo)localObject).setBankName(paramString1);
      ((BankMessageInfo)localObject).setNotifyFromEmail(paramAffiliateBank.getEmailFrom(paramString2));
      ((BankMessageInfo)localObject).setNotifyMemo(paramAffiliateBank.getEmailMemo(paramString2));
      ((BankMessageInfo)localObject).setNotifySubject(paramAffiliateBank.getEmailSubject(paramString2));
      this.dp.put(Integer.toString(paramAffiliateBank.getAffiliateBankID()) + "_" + paramString2, localObject);
    }
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      MessagesService.this.jdMethod_do(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.MessagesService
 * JD-Core Version:    0.7.0.1
 */