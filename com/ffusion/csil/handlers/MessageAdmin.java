package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Messaging2;
import com.ffusion.services.MessagingAdmin3;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class MessageAdmin
{
  private static final String a = "Messaging";
  private static MessagingAdmin3 jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.initialize");
    String str = "MessageAdmin.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Messaging", str, 20107);
    jdField_if = (MessagingAdmin3)HandlerUtil.instantiateService(localHashMap, str, 20107);
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Messaging2 localMessaging2 = null;
    if (paramHashMap != null)
    {
      localMessaging2 = (Messaging2)paramHashMap.get("SERVICE");
      if (localMessaging2 != null)
      {
        DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.signOn");
        checkExtra(paramHashMap);
        if (paramSecureUser == null) {
          paramSecureUser = new SecureUser();
        }
        int i = localMessaging2.signOn(paramString1, paramString2);
        if (i != 0) {
          throwing(-1009, i);
        }
        return paramSecureUser;
      }
    }
    return paramSecureUser;
  }
  
  public static Message sendReply(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.sendReply");
    checkExtra(paramHashMap);
    Object localObject = paramHashMap.get("OBJECT");
    if (localObject == null)
    {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'OBJECT' - either Message or MessageThread");
      throwing(-1009, 8603);
    }
    Message localMessage = null;
    try
    {
      localMessage = jdField_if.sendReply(paramSecureUser, paramMessage, localObject, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessage;
  }
  
  public static Message sendInternalQuery(SecureUser paramSecureUser, Message paramMessage, MessageThread paramMessageThread, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.sendInternalQuery");
    Message localMessage = null;
    try
    {
      localMessage = jdField_if.sendInternalQuery(paramSecureUser, paramMessage, paramMessageThread, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessage;
  }
  
  public static MessageQueues getMessageQueues(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.getMessageQueues");
    checkExtra(paramHashMap);
    return jdField_if.getMessageQueues(paramSecureUser, paramMessageQueue, paramHashMap);
  }
  
  public static MessageQueue addMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.addMessageQueue");
    MessageQueue localMessageQueue = null;
    try
    {
      localMessageQueue = jdField_if.addMessageQueue(paramSecureUser, paramMessageQueue, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueue;
  }
  
  public static MessageQueue deleteMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.deleteMessageQueue");
    MessageQueue localMessageQueue = null;
    try
    {
      localMessageQueue = jdField_if.deleteMessageQueue(paramSecureUser, paramMessageQueue, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueue;
  }
  
  public static MessageQueue modifyMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueue");
    MessageQueue localMessageQueue = null;
    try
    {
      localMessageQueue = jdField_if.modifyMessageQueue(paramSecureUser, paramMessageQueue, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueue;
  }
  
  public static MessageQueueResponse addMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.addMessageQueueResponse");
    MessageQueueResponse localMessageQueueResponse = null;
    try
    {
      localMessageQueueResponse = jdField_if.addMessageQueueResponse(paramSecureUser, paramMessageQueueResponse, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueueResponse;
  }
  
  public static MessageQueueResponse deleteMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.deleteMessageQueueResponse");
    MessageQueueResponse localMessageQueueResponse = null;
    try
    {
      localMessageQueueResponse = jdField_if.deleteMessageQueueResponse(paramSecureUser, paramMessageQueueResponse, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueueResponse;
  }
  
  public static MessageQueueResponse modifyMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueueResponse");
    MessageQueueResponse localMessageQueueResponse = null;
    try
    {
      localMessageQueueResponse = jdField_if.modifyMessageQueueResponse(paramSecureUser, paramMessageQueueResponse, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueueResponse;
  }
  
  public static MessageQueue modifyMessageQueueMembersByQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueueMembers");
    MessageQueue localMessageQueue = null;
    try
    {
      localMessageQueue = jdField_if.modifyMessageQueueMembersByQueue(paramSecureUser, paramMessageQueue, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageQueue;
  }
  
  public static void modifyMessageQueueMembersByEmployee(SecureUser paramSecureUser, String paramString1, String paramString2, MessageQueues paramMessageQueues1, MessageQueues paramMessageQueues2, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueueMembersByEmployee");
    checkExtra(paramHashMap);
    try
    {
      jdField_if.modifyMessageQueueMembersByEmployee(paramSecureUser, paramString1, paramString2, paramMessageQueues1, paramMessageQueues2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
  }
  
  public static MessageThread modifyMessageThread(SecureUser paramSecureUser, MessageThread paramMessageThread, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.modifyMessageThread");
    MessageThread localMessageThread = null;
    try
    {
      localMessageThread = jdField_if.modifyMessageThread(paramSecureUser, paramMessageThread, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageThread;
  }
  
  public static MessageThreads getMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.getMessageThreads");
    checkExtra(paramHashMap);
    Object localObject = paramHashMap.get("OBJECT");
    if (localObject == null)
    {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'OBJECT'");
      throwing(-1009, 3);
    }
    MessageThreads localMessageThreads1 = (MessageThreads)paramHashMap.get("MESSAGETHREADS");
    if (localMessageThreads1 == null)
    {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'MESSAGETHREADS'");
      throwing(-1009, 3);
    }
    MessageThreads localMessageThreads2 = null;
    try
    {
      localMessageThreads2 = jdField_if.getMessageThreads(paramSecureUser, localObject, localMessageThreads1, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return localMessageThreads2;
  }
  
  public static String getMessageThreadsCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.getMessageThreads");
    checkExtra(paramHashMap);
    MessageThreads localMessageThreads = (MessageThreads)paramHashMap.get("MESSAGETHREADS");
    if (localMessageThreads == null)
    {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'MESSAGETHREADS'");
      throwing(-1009, 3);
    }
    String str = null;
    try
    {
      str = jdField_if.getMessageThreadsCount(paramSecureUser, localMessageThreads, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
    return str;
  }
  
  public static void deleteMessageQueueMembers(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.MessageAdmin.deleteMessageQueueMembers");
    try
    {
      jdField_if.deleteMessageQueueMembers(paramSecureUser, paramString1, paramString2, paramString3, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code, localProfileException);
    }
  }
  
  public static void checkExtra(HashMap paramHashMap)
    throws CSILException
  {
    if (paramHashMap == null)
    {
      DebugLog.log(Level.FINER, "'extra' is null - Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      Object localObject = paramHashMap.get(str1);
      String str2 = localObject.getClass().getName();
      String str3 = localObject.toString();
      DebugLog.log(Level.FINER, "(" + str2 + ")" + str1 + "=" + str3);
    }
  }
  
  public static void throwing(int paramInt1, int paramInt2)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt1, paramInt2);
    DebugLog.throwing("COMPATIBILITY EXCEPTION", localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(String paramString, int paramInt)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramString, paramInt);
    DebugLog.throwing("COMPATIBILITY EXCEPTION", localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(int paramInt, Exception paramException)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt, paramException);
    DebugLog.throwing("COMPATIBILITY EXCEPTION", localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(int paramInt1, int paramInt2, Exception paramException)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt1, paramInt2, paramException);
    DebugLog.throwing("COMPATIBILITY EXCEPTION", localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(int paramInt)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt);
    DebugLog.throwing("COMPATIBILITY EXCEPTION", localCSILException);
    throw localCSILException;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.MessageAdmin
 * JD-Core Version:    0.7.0.1
 */