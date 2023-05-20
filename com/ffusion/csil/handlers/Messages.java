package com.ffusion.csil.handlers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.BankMessageInfo;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Messaging2;
import com.ffusion.services.Messaging8;
import com.ffusion.services.javax.JavaMail;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class Messages
{
  private static final String a = "Messaging";
  private static final String jdField_do = "Mailing";
  private static final String jdField_new = "INIT_URL";
  private static final int jdField_int = 0;
  private static Messaging8 jdField_try = null;
  public static JavaMail _mailService = null;
  private static String jdField_for;
  private static String jdField_if;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.initialize");
    String str1 = "Messages.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Messaging", str1, 20107);
    String str2 = (String)localHashMap.get("INIT_URL");
    jdField_try = (Messaging8)HandlerUtil.instantiateService(localHashMap, str1, 20107);
    localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Mailing", str1, 20107);
    String str3 = (String)localHashMap.get("INIT_URL");
    _mailService = (JavaMail)HandlerUtil.instantiateService(localHashMap, str1, 20107);
    jdField_for = HandlerUtil.getGlobalCaseDisplaySize(paramHashMap);
    jdField_if = HandlerUtil.getGlobalCaseMaxResultSize(paramHashMap);
    if ((jdField_try.initialize(str2) != 0) || (_mailService.initialize(str3) != 0)) {
      throw new CSILException(str1, 20107);
    }
  }
  
  public static Object getService(String paramString)
  {
    if (paramString.equals("Mailing")) {
      return _mailService;
    }
    return jdField_try;
  }
  
  public static String getDisplayCount()
  {
    return jdField_for;
  }
  
  public static String getMaxResultCount()
  {
    return jdField_if;
  }
  
  public static SecureUser signOn(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if ((localObject != null) && ((localObject instanceof Messaging2)))
      {
        Messaging2 localMessaging2 = (Messaging2)localObject;
        DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.signOn");
        int i = localMessaging2.signOn(paramString1, paramString2);
        if (i != 0) {
          throwing(-1009, i);
        }
        return paramSecureUser;
      }
    }
    return paramSecureUser;
  }
  
  public static SecureUser signOff(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if ((localObject != null) && ((localObject instanceof Messaging2)))
      {
        DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.signOff");
        int i = 0;
        if (i != 0) {
          throwing(-1009, i);
        }
        return paramSecureUser;
      }
    }
    return paramSecureUser;
  }
  
  public static SecureUser changePIN(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject = null;
    if (paramHashMap != null)
    {
      localObject = paramHashMap.get("SERVICE");
      if ((localObject != null) && ((localObject instanceof Messaging2)))
      {
        Messaging2 localMessaging2 = (Messaging2)localObject;
        DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.changePIN");
        int i = localMessaging2.changePIN(paramString1, paramString2);
        if (i != 0) {
          throwing(-1009, i);
        }
        return paramSecureUser;
      }
    }
    return paramSecureUser;
  }
  
  public static com.ffusion.beans.messages.Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessages");
    Object localObject1 = null;
    if (paramHashMap != null) {
      localObject1 = paramHashMap.get("OBJECT");
    }
    if (localObject1 == null) {
      DebugLog.log(Level.FINE, "Missing parameter (not required): extra.'OBJECT'");
    }
    Object localObject2 = null;
    Messaging2 localMessaging2 = null;
    if (paramHashMap != null) {
      localMessaging2 = (Messaging2)paramHashMap.get("SERVICE");
    }
    if (localMessaging2 == null)
    {
      try
      {
        if (localObject1 == null) {
          localObject2 = jdField_try.getMessages(paramSecureUser, paramHashMap);
        } else {
          localObject2 = jdField_try.getMessages(paramSecureUser, localObject1, paramHashMap);
        }
      }
      catch (ProfileException localProfileException)
      {
        throwing(-1009, localProfileException);
      }
    }
    else
    {
      com.ffusion.beans.messages.Messages localMessages = (com.ffusion.beans.messages.Messages)paramHashMap.get("MESSAGES");
      if (localMessages == null)
      {
        DebugLog.log(Level.FINE, "Missing required parameter: extra.'MESSAGES' - creating new Messages()");
        localMessages = new com.ffusion.beans.messages.Messages();
      }
      int i = 0;
      if (localObject1 == null) {
        i = localMessaging2.getMessages(localMessages);
      } else {
        i = localMessaging2.getMessages(localObject1, localMessages);
      }
      if (i != 0) {
        throwing(-1009, i);
      }
      localObject2 = localMessages;
    }
    return localObject2;
  }
  
  public static Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendMessage");
    Message localMessage = null;
    Messaging2 localMessaging2 = null;
    if (paramHashMap != null) {
      localMessaging2 = (Messaging2)paramHashMap.get("SERVICE");
    }
    if (localMessaging2 == null)
    {
      try
      {
        Object localObject = null;
        if (paramHashMap != null) {
          localObject = paramHashMap.get("OBJECT");
        }
        if (localObject == null)
        {
          localMessage = jdField_try.sendMessage(paramSecureUser, paramMessage, paramHashMap);
        }
        else
        {
          localMessage = null;
          jdField_try.sendMessage(paramSecureUser, paramMessage, localObject, paramHashMap);
        }
      }
      catch (ProfileException localProfileException)
      {
        throwing(-1009, localProfileException);
      }
    }
    else
    {
      int i = 0;
      i = localMessaging2.sendMessage(paramMessage);
      if (i != 0) {
        throwing(-1009, i);
      }
      localMessage = paramMessage;
    }
    return localMessage;
  }
  
  public static Message sendInternalMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendInternalMessage");
    Message localMessage = null;
    try
    {
      localMessage = jdField_try.sendInternalMessage(paramSecureUser, paramHashMap, paramMessage);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessage;
  }
  
  public static Message sendReplyMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, boolean paramBoolean)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendReplyMessage");
    Message localMessage = null;
    try
    {
      localMessage = jdField_try.sendReplyMessage(paramSecureUser, paramHashMap, paramMessage, paramBoolean);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessage;
  }
  
  public static MessageThread sendMessageThread(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendMessage");
    checkExtra(paramHashMap);
    Object localObject = paramHashMap.get("OBJECT");
    if (localObject == null) {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'OBJECT'");
    }
    MessageThread localMessageThread = null;
    if (localObject != null) {
      try
      {
        localMessageThread = jdField_try.sendMessage(paramSecureUser, paramMessage, localObject, paramHashMap);
      }
      catch (ProfileException localProfileException)
      {
        throwing(-1009, localProfileException);
      }
    }
    return localMessageThread;
  }
  
  public static Message deleteMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.deleteMessage");
    Message localMessage = null;
    try
    {
      localMessage = jdField_try.deleteMessage(paramSecureUser, paramMessage, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessage;
  }
  
  public static Message markMessageAsRead(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.markMessageAsRead");
    Message localMessage = null;
    try
    {
      localMessage = jdField_try.markMessageAsRead(paramSecureUser, paramMessage, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessage;
  }
  
  public static int getNumberOfUnreadMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getNumberOfUnreadMessages");
    checkExtra(paramHashMap);
    MessageThreads localMessageThreads = (MessageThreads)paramHashMap.get("MESSAGETHREADS");
    if (localMessageThreads == null)
    {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'MESSAGETHREADS'");
      throwing(-1009, 2);
    }
    int i = -1;
    try
    {
      i = jdField_try.getNumberOfUnreadMessages(paramSecureUser, localMessageThreads, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return i;
  }
  
  public static int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getNumberOfUnreadMessagesExcludingAlerts");
    checkExtra(paramHashMap);
    MessageThreads localMessageThreads = (MessageThreads)paramHashMap.get("MESSAGETHREADS");
    if (localMessageThreads == null)
    {
      DebugLog.log(Level.FINE, "Missing required parameter: extra.'MESSAGETHREADS'");
      throwing(-1009, 2);
    }
    int i = -1;
    try
    {
      i = jdField_try.getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, localMessageThreads, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return i;
  }
  
  public static MessageThreads findMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.findMessageThreads");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.findMessageThreads(paramSecureUser, paramHashMap, paramUser, paramBusiness, paramMessageThread, paramDateTime1, paramDateTime2);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static void createNewCase(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString, boolean paramBoolean)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.createNewCase");
    try
    {
      jdField_try.createNewCase(paramSecureUser, paramHashMap, paramMessage, paramString, paramBoolean);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static com.ffusion.beans.messages.Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessages");
    com.ffusion.beans.messages.Messages localMessages = null;
    try
    {
      localMessages = jdField_try.getMessages(paramSecureUser, paramHashMap, paramString);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessages;
  }
  
  public static void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.approveMessage");
    try
    {
      jdField_try.approveMessage(paramSecureUser, paramHashMap, paramMessage);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static void denyMessage(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.denyMessage");
    try
    {
      jdField_try.denyMessage(paramSecureUser, paramHashMap, paramMessageThread);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static int getAssignedMessageCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getAssignedMessageCount");
    int i = 0;
    try
    {
      i = jdField_try.getAssignedMessageCount(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return i;
  }
  
  public static MessageCounts getMessageCountsByHelpCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessageCountsByHelpCases");
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = jdField_try.getMessageCountsByHelpCases(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageCounts;
  }
  
  public static MessageCounts getMessageCountsByHelpCasesProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessageCountsByHelpCasesProvidedAndClosed");
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = jdField_try.getMessageCountsByHelpCasesProvidedAndClosed(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageCounts;
  }
  
  public static MessageCounts getMessageCountsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessageCountsByNewCases");
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = jdField_try.getMessageCountsByNewCases(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageCounts;
  }
  
  public static MessageCounts getMessageCountsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessageCountsByPersonalBanker");
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = jdField_try.getMessageCountsByPersonalBanker(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageCounts;
  }
  
  public static MessageCounts getMessageCountsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessageCountsByPending");
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = jdField_try.getMessageCountsByPending(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageCounts;
  }
  
  public static MessageThreads getThreadsByAssigned(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getThreadsByAssigned");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.getThreadsByAssigned(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static MessageThreads getThreadsByHelpProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getThreadsByHelpProvidedAndClosed");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.getThreadsByHelpProvidedAndClosed(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static MessageThreads getThreadsByHelpRequested(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getThreadsByHelpRequested");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.getThreadsByHelpRequested(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static MessageThreads getThreadsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getThreadsByNewCases");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.getThreadsByNewCases(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static MessageThreads getThreadsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getThreadsByPersonalBanker");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.getThreadsByPersonalBanker(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static MessageThreads getThreadsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getThreadsByPending");
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = jdField_try.getThreadsByPending(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessageThreads;
  }
  
  public static void reassignThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread, String paramString)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.reassignThread");
    try
    {
      jdField_try.reassignThread(paramSecureUser, paramHashMap, paramMessageThread, paramString);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.createGlobalMessage");
    try
    {
      jdField_try.createGlobalMessage(paramSecureUser, paramGlobalMessage, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code);
    }
  }
  
  public static void deleteGlobalMessage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.deleteGlobalMessage");
    try
    {
      jdField_try.deleteGlobalMessage(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static void modifyGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.ModifyGlobalMessage");
    try
    {
      jdField_try.modifyGlobalMessage(paramSecureUser, paramGlobalMessage, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException.code);
    }
  }
  
  public static GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getGlobalMessages(SecureUser, GlobalMessage, HashMap)");
    GlobalMessages localGlobalMessages = null;
    try
    {
      localGlobalMessages = jdField_try.getGlobalMessages(paramSecureUser, paramGlobalMessage, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localGlobalMessages;
  }
  
  public static GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getGlobalMessages(SecureUser, GlobalMessageSearchCriteria, HashMap)");
    GlobalMessages localGlobalMessages = null;
    try
    {
      localGlobalMessages = jdField_try.getGlobalMessages(paramSecureUser, paramGlobalMessageSearchCriteria, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localGlobalMessages;
  }
  
  public static GlobalMessages getGlobalLoginMessages(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getGlobalMessages");
    GlobalMessages localGlobalMessages = null;
    try
    {
      localGlobalMessages = jdField_try.getGlobalLoginMessages(paramString, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localGlobalMessages;
  }
  
  public static GlobalMessages getGlobalLoginMessages(String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getGlobalMessages");
    GlobalMessages localGlobalMessages = null;
    try
    {
      localGlobalMessages = jdField_try.getGlobalLoginMessages(paramString, paramInt1, paramInt2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localGlobalMessages;
  }
  
  public static void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendGlobalMessages");
    try
    {
      jdField_try.sendGlobalMessage(paramSecureUser, paramGlobalMessage, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static int approvalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return globalMsgApprovalRequired(paramSecureUser, paramHashMap);
  }
  
  public static int globalMsgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.globalMsgApprovalRequired");
    int i = 0;
    try
    {
      i = jdField_try.globalMsgApprovalRequired(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return i;
  }
  
  public static int msgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.msgApprovalRequired");
    int i = 0;
    try
    {
      i = jdField_try.msgApprovalRequired(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return i;
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
  
  public static void throwing(int paramInt, ProfileException paramProfileException)
    throws CSILException
  {
    CSILException localCSILException;
    if (paramInt == -1009) {
      localCSILException = new CSILException(paramInt, paramProfileException.code, paramProfileException);
    } else {
      localCSILException = new CSILException(paramInt, paramProfileException);
    }
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
  
  public static int sendEmail(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendEmail");
    return _mailService.sendMessage(paramString1, paramString2, paramString3, paramString4);
  }
  
  public static int sendBulkMessage(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.sendBulkMessage");
    try
    {
      return _mailService.sendBulkMessage(paramString1, paramInt1, paramInt2, paramString2, paramString3, paramString4);
    }
    catch (Exception localException)
    {
      throwing(-1009, localException);
    }
    return -1009;
  }
  
  public static String getNotificationSubject()
  {
    return jdField_try.getNotificationSubject();
  }
  
  public static String getNotificationMemo()
  {
    return jdField_try.getNotificationMemo();
  }
  
  public static String getNotificationFromEmail()
  {
    return jdField_try.getNotificationFromEmail();
  }
  
  public static com.ffusion.beans.messages.Messages getMessagesByThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getMessagesByThread");
    com.ffusion.beans.messages.Messages localMessages = null;
    try
    {
      localMessages = jdField_try.getMessagesByThread(paramSecureUser, paramHashMap, paramMessageThread);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localMessages;
  }
  
  public static void modifyMessageThreadStatus(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.ModifyMessageThreadStatus";
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.ModifyMessageThreadStatus");
    try
    {
      jdField_try.modifyMessageThreadStatus(paramSecureUser, paramInt1, paramInt2, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(-1009, localException);
    }
  }
  
  public static void processGlobalMessages()
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.processGlobalMessages");
    try
    {
      jdField_try.processGlobalMessages();
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
  }
  
  public static GlobalMessage getGlobalMessageById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.getGlobalMessagesById");
    GlobalMessage localGlobalMessage = null;
    try
    {
      localGlobalMessage = jdField_try.getGlobalMessageById(paramSecureUser, paramInt, paramHashMap);
    }
    catch (Exception localException)
    {
      throwing(-1009, localException);
    }
    return localGlobalMessage;
  }
  
  public static Object getService()
  {
    return jdField_try;
  }
  
  public static BankMessageInfo getEmailSettings(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    BankMessageInfo localBankMessageInfo = null;
    DebugLog.log(Level.FINE, "com.ffusion.csil.handlers.Messages.processGlobalMessages");
    try
    {
      localBankMessageInfo = jdField_try.getEmailSettings(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throwing(-1009, localProfileException);
    }
    return localBankMessageInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Messages
 * JD-Core Version:    0.7.0.1
 */