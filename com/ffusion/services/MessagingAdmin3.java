package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface MessagingAdmin3
{
  public static final int ERROR_INVALID_SIGNON = 8600;
  public static final int ERROR_NO_DATABASE_CONNECTION = 8601;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 8602;
  public static final int ERROR_INVALID_REQUEST = 8603;
  public static final int ERROR_UNABLE_TO_GET_QUEUES = 8604;
  public static final int ERROR_UNABLE_TO_ADD_QUEUE = 8605;
  public static final int ERROR_UNABLE_TO_DEL_QUEUE = 8606;
  public static final int ERROR_UNABLE_TO_MOD_QUEUE = 8607;
  public static final int ERROR_UNABLE_TO_ADD_RESPONSE = 8608;
  public static final int ERROR_UNABLE_TO_DEL_RESPONSE = 8609;
  public static final int ERROR_UNABLE_TO_MOD_RESPONSE = 8610;
  public static final int ERROR_UNABLE_TO_GET_MESSAGE_THREADS = 8611;
  public static final int ERROR_UNABLE_TO_MOD_MESSAGE_THREAD = 8612;
  public static final int ERROR_UNABLE_TO_MOD_QUEUE_MEMBERS = 8613;
  public static final int ERROR_UNABLE_TO_DEL_QUEUE_MEMBERS = 8614;
  public static final int ERROR_UNABLE_TO_GET_QUEUE = 8615;
  public static final int ERROR_UNABLE_TO_ADD_EMPLOYEE_TO_QUEUE = 8616;
  public static final int ERROR_UNABLE_TO_GET_EMPLOYEE_FROM_QUEUE = 8617;
  public static final int ERROR_UNABLE_TO_DEL_EMPLOYEE_FROM_QUEUE = 8618;
  
  public abstract Message sendReply(SecureUser paramSecureUser, Message paramMessage, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Message sendInternalQuery(SecureUser paramSecureUser, Message paramMessage, MessageThread paramMessageThread, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueues getMessageQueues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueues getMessageQueues(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap);
  
  public abstract MessageQueue addMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueue deleteMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueue modifyMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueueResponse addMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueueResponse deleteMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueueResponse modifyMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageQueue modifyMessageQueueMembersByQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyMessageQueueMembersByEmployee(SecureUser paramSecureUser, String paramString1, String paramString2, MessageQueues paramMessageQueues1, MessageQueues paramMessageQueues2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThread modifyMessageThread(SecureUser paramSecureUser, MessageThread paramMessageThread, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getMessageThreads(SecureUser paramSecureUser, Object paramObject, MessageThreads paramMessageThreads, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteMessageQueueMembers(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getMessageThreadsCount(SecureUser paramSecureUser, MessageThreads paramMessageThreads, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.MessagingAdmin3
 * JD-Core Version:    0.7.0.1
 */