package com.ffusion.services;

import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;

public abstract interface MessagingAdmin
  extends SignOn
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
  
  public abstract int sendReply(Message paramMessage, Object paramObject);
  
  public abstract int sendInternalQuery(Message paramMessage, MessageThread paramMessageThread);
  
  public abstract int getMessageQueues(MessageQueues paramMessageQueues);
  
  public abstract int addMessageQueue(MessageQueue paramMessageQueue);
  
  public abstract int deleteMessageQueue(MessageQueue paramMessageQueue);
  
  public abstract int modifyMessageQueue(MessageQueue paramMessageQueue);
  
  public abstract int addMessageQueueResponse(MessageQueueResponse paramMessageQueueResponse);
  
  public abstract int deleteMessageQueueResponse(MessageQueueResponse paramMessageQueueResponse);
  
  public abstract int modifyMessageQueueResponse(MessageQueueResponse paramMessageQueueResponse);
  
  public abstract int modifyMessageQueueMembers(MessageQueue paramMessageQueue);
  
  public abstract int modifyMessageThread(MessageThread paramMessageThread);
  
  public abstract int getMessageThreads(Object paramObject, MessageThreads paramMessageThreads);
  
  public abstract int deleteMessageQueueMembers(String paramString1, String paramString2, String paramString3);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.MessagingAdmin
 * JD-Core Version:    0.7.0.1
 */