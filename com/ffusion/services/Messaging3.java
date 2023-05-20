package com.ffusion.services;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.messages.Messages;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface Messaging3
{
  public static final String EMPLOYEE = "EMPLOYEE";
  public static final String CUSTOMER = "CUSTOMER";
  public static final String QUEUE = "QUEUE";
  public static final int ERROR_UNABLE_TO_SEND_MESSAGE = 8500;
  public static final int ERROR_UNABLE_TO_MOD_MESSAGE = 8501;
  public static final int ERROR_UNABLE_TO_GET_MESSAGE = 8502;
  public static final int ERROR_NO_MESSAGE_FOUND = 8503;
  public static final int ERROR_UNABLE_TO_DEL_MESSAGE = 8504;
  public static final int ERROR_UNABLE_TO_SEND_INTERNET_MESSAGE = 8505;
  
  public abstract int initialize(String paramString);
  
  public abstract Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Messages getMessages(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThread sendMessage(SecureUser paramSecureUser, Message paramMessage, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Message deleteMessage(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Message markMessageAsRead(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int getNumberOfUnreadMessages(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, Object paramObject, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads findMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2)
    throws ProfileException;
  
  public abstract void createNewCase(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString, boolean paramBoolean)
    throws ProfileException;
  
  public abstract Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws ProfileException;
  
  public abstract void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException;
  
  public abstract void denyMessage(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException;
  
  public abstract int getAssignedMessageCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageCounts getMessageCountsByHelpCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageCounts getMessageCountsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageCounts getMessageCountsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageCounts getMessageCountsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getThreadsByAssigned(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getThreadsByHelpProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getThreadsByHelpRequested(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getThreadsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getThreadsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageThreads getThreadsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void reassignThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread, String paramString)
    throws ProfileException;
  
  public abstract Message sendInternalMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws ProfileException;
  
  public abstract Message sendReplyMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, boolean paramBoolean)
    throws ProfileException;
  
  public abstract void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, BusinessEmployees paramBusinessEmployees, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteGlobalMessage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, BusinessEmployees paramBusinessEmployees, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int approvalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getNotificationSubject();
  
  public abstract String getNotificationMemo();
  
  public abstract String getNotificationFromEmail();
  
  public abstract Messages getMessagesByThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging3
 * JD-Core Version:    0.7.0.1
 */