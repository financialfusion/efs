package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueResponse;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.BankEmployeeAdmin;
import com.ffusion.services.Messaging2;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MessageAdmin
  extends Initialize
{
  private static Entitlement aA9 = new Entitlement("Messages", null, null);
  private static Entitlement aBe = new Entitlement("CaseTypeCrud", null, null);
  private static Entitlement aA8 = new Entitlement("CaseTypeView", null, null);
  private static Entitlement aBk = new Entitlement("CaseCrud", null, null);
  private static Entitlement aBd = new Entitlement("CaseView", null, null);
  private static final String aBo = "com.ffusion.util.logging.audit.messageadmin";
  private static final String aA5 = "AuditMessage_1";
  private static final String aAZ = "AuditMessage_2";
  private static final String aBi = "AuditMessage_3";
  private static final String aAP = "AuditMessage_4";
  private static final String aBc = "AuditMessage_5";
  private static final String aAS = "AuditMessage_6";
  private static final String aAQ = "AuditMessage_7";
  private static final String aAY = "AuditMessage_8";
  private static final String aA2 = "AuditMessage_9";
  private static final String aBa = "AuditMessage_10";
  private static final String aAU = "AuditMessage_11";
  private static final String aBg = "AuditMessage_12";
  private static final String aA0 = "AuditMessage_13";
  private static final String aAR = "AuditMessage_14";
  private static final String aAT = "AuditMessage_15";
  private static final String aBp = "AuditMessage_16";
  private static final String aAV = "AuditMessage_17";
  private static final String aBb = "AuditMessage_18";
  private static final String aAW = "AuditMessage_19";
  private static final String aAO = "AuditMessage_20";
  private static final String aBf = "AuditMessage_21";
  private static final String aA7 = "AuditMessage_22";
  private static final String aAX = "AuditMessage_23";
  private static final String aBj = "AuditMessage_24";
  private static final String aBm = "AuditMessage_25";
  private static final String aBl = "AuditMessage_26";
  private static final String aA1 = "AuditMessage_27";
  private static final String aA4 = "AuditMessage_28";
  private static final String aA3 = "AuditMessage_29";
  private static final String aBh = "AuditMessage_30";
  private static final String aA6 = "AuditMessage_31";
  private static final String aBn = "Queue_Member_Status.";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.MessageAdmin.initialize");
    com.ffusion.csil.handlers.MessageAdmin.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.MessageAdmin.getService();
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
        String str1 = "MessageAdmin.SignOn";
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aA9))
        {
          long l = System.currentTimeMillis();
          String str2 = TrackingIDGenerator.GetNextID();
          SecureUser localSecureUser = com.ffusion.csil.handlers.MessageAdmin.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
          PerfLog.log(str1, l, true);
          debug(paramSecureUser, str1);
          return localSecureUser;
        }
        Object[] arrayOfObject = new Object[0];
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_1", arrayOfObject);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    return paramSecureUser;
  }
  
  public static Message sendReply(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.SendReply";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBk))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      if ((paramMessage.getFromValue() != paramSecureUser.getProfileID()) || ((paramSecureUser.getUserType() == 1) && (paramMessage.getFromTypeValue() != 1)) || ((paramSecureUser.getUserType() == 2) && (paramMessage.getFromTypeValue() != 0)))
      {
        DebugLog.log(Level.SEVERE, "MessageAdmin.SendReply.  Attempted to send a message where message sender information did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      Message localMessage = com.ffusion.csil.handlers.MessageAdmin.sendReply(paramSecureUser, paramMessage, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = paramMessage.getSubject();
      LocalizableString localLocalizableString2;
      if ((paramMessage.getCaseNum() != null) && (!paramMessage.getCaseNum().equals("")))
      {
        arrayOfObject2[0] = paramMessage.getCaseNum();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_3", arrayOfObject2);
        audit(paramSecureUser, localLocalizableString2, str2, 2600);
      }
      else
      {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_30", arrayOfObject2);
        audit(paramSecureUser, localLocalizableString2, str2, 2600);
      }
      debug(paramSecureUser, str1);
      return localMessage;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_2", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Message sendInternalQuery(SecureUser paramSecureUser, Message paramMessage, MessageThread paramMessageThread, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.SendInternalQuery";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBk))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Message localMessage = com.ffusion.csil.handlers.MessageAdmin.sendInternalQuery(paramSecureUser, paramMessage, paramMessageThread, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "";
      if (paramMessage.getCaseNum() != null) {
        arrayOfObject2[0] = paramMessage.getCaseNum();
      }
      arrayOfObject2[1] = paramMessage.getSubject();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_5", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2601);
      debug(paramSecureUser, str1);
      return localMessage;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_4", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueues getMessageQueues(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    String str = "MessageAdmin.GetMessageQueues";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aA8)))
    {
      long l = System.currentTimeMillis();
      MessageQueues localMessageQueues = com.ffusion.csil.handlers.MessageAdmin.getMessageQueues(paramSecureUser, paramMessageQueue, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageQueues;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_6", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageQueue addMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.AddMessageQueue";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueue localMessageQueue = com.ffusion.csil.handlers.MessageAdmin.addMessageQueue(paramSecureUser, paramMessageQueue, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueue.getQueueName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_8", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2602);
      debug(paramSecureUser, str1);
      return localMessageQueue;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_7", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueue deleteMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.DeleteMessageQueue";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueue localMessageQueue = com.ffusion.csil.handlers.MessageAdmin.deleteMessageQueue(paramSecureUser, paramMessageQueue, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueue.getQueueName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_10", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2603);
      debug(paramSecureUser, str1);
      return localMessageQueue;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_9", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueue modifyMessageQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.ModifyMessageQueue";
    if ((com.ffusion.util.entitlements.EntitlementsUtil.isEntitlementBypassAllowed(paramHashMap)) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe)))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueue localMessageQueue = com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueue(paramSecureUser, paramMessageQueue, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueue.getQueueName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_12", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2604);
      debug(paramSecureUser, str1);
      return localMessageQueue;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_11", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueueResponse addMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.AddMessageQueueResponse";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueueResponse localMessageQueueResponse = com.ffusion.csil.handlers.MessageAdmin.addMessageQueueResponse(paramSecureUser, paramMessageQueueResponse, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueueResponse.getResponseName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_14", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2605);
      debug(paramSecureUser, str1);
      return localMessageQueueResponse;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_13", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueueResponse deleteMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.DeleteMessageQueueResponse";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueueResponse localMessageQueueResponse = com.ffusion.csil.handlers.MessageAdmin.deleteMessageQueueResponse(paramSecureUser, paramMessageQueueResponse, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueueResponse.getResponseName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_16", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2606);
      debug(paramSecureUser, str1);
      return localMessageQueueResponse;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_15", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueueResponse modifyMessageQueueResponse(SecureUser paramSecureUser, MessageQueueResponse paramMessageQueueResponse, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.ModifyMessageQueueResponse";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueueResponse localMessageQueueResponse = com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueueResponse(paramSecureUser, paramMessageQueueResponse, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueueResponse.getResponseName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_18", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2605);
      debug(paramSecureUser, str1);
      return localMessageQueueResponse;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_17", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageQueue modifyMessageQueueMembersByQueue(SecureUser paramSecureUser, MessageQueue paramMessageQueue, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.ModifyMessageQueueMembersByQueue";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageQueue localMessageQueue = com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueueMembersByQueue(paramSecureUser, paramMessageQueue, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramMessageQueue.getQueueName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_20", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2607);
      debug(paramSecureUser, str1);
      return localMessageQueue;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_19", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void modifyMessageQueueMembersByEmployee(SecureUser paramSecureUser, String paramString1, String paramString2, MessageQueues paramMessageQueues1, MessageQueues paramMessageQueues2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.ModifyMessageQueueMembersByEmployee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.MessageAdmin.modifyMessageQueueMembersByEmployee(paramSecureUser, paramString1, paramString2, paramMessageQueues1, paramMessageQueues2, paramHashMap);
      PerfLog.log(str1, l, true);
      String str3 = null;
      try
      {
        BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee.setId(paramString1);
        localObject = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
        if (localObject != null) {
          str3 = ((BankEmployee)localObject).getUserName();
        }
      }
      catch (Exception localException)
      {
        str3 = null;
      }
      Object[] arrayOfObject2 = new Object[1];
      Object localObject = null;
      if (str3 != null)
      {
        arrayOfObject2[0] = str3;
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_22", arrayOfObject2);
      }
      else
      {
        arrayOfObject2[0] = "";
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_31", arrayOfObject2);
      }
      audit(paramSecureUser, (ILocalizable)localObject, str2, 2608);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_21", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageThread modifyMessageThread(SecureUser paramSecureUser, MessageThread paramMessageThread, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.ModifyMessageThread";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBk))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      MessageThread localMessageThread = com.ffusion.csil.handlers.MessageAdmin.modifyMessageThread(paramSecureUser, paramMessageThread, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "";
      if ((paramMessageThread.getCaseNum() != null) && (!paramMessageThread.getCaseNum().equals(""))) {
        arrayOfObject2[0] = paramMessageThread.getCaseNum();
      }
      arrayOfObject2[1] = paramMessageThread.getQueueName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_24", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2609);
      debug(paramSecureUser, str1);
      return localMessageThread;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_23", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageThreads getMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "MessageAdmin.GetMessageThreads";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBd))
    {
      long l = System.currentTimeMillis();
      MessageThreads localMessageThreads = com.ffusion.csil.handlers.MessageAdmin.getMessageThreads(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_25", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getMessageThreadsCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.getMessageThreadsCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBd))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.MessageAdmin.getMessageThreadsCount(paramSecureUser, paramHashMap);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_26", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteMessageQueueMembers(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "MessageAdmin.DeleteMessageQueueMembers";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBe))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      String str3 = null;
      try
      {
        BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee.setId(paramString1);
        localObject1 = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
        if (localObject1 != null) {
          str3 = ((BankEmployee)localObject1).getUserName();
        }
      }
      catch (Exception localException)
      {
        str3 = null;
      }
      com.ffusion.csil.handlers.MessageAdmin.deleteMessageQueueMembers(paramSecureUser, paramString1, paramString2, paramString3, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[3];
      Object localObject1 = new Object[0];
      ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.logging.audit.messageadmin", paramSecureUser.getLocale());
      arrayOfObject2[0] = paramString2;
      if (localResourceBundle != null)
      {
        localObject2 = localResourceBundle.getString("Queue_Member_Status." + paramString2);
        if (localObject2 != null) {
          arrayOfObject2[0] = localObject2;
        }
      }
      arrayOfObject2[1] = paramString3;
      arrayOfObject2[2] = "";
      Object localObject2 = null;
      if (str3 != null)
      {
        arrayOfObject2[2] = str3;
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_28", arrayOfObject2);
      }
      else
      {
        localObject2 = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_29", arrayOfObject2);
      }
      audit(paramSecureUser, (ILocalizable)localObject2, str2, 2610);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messageadmin", "AuditMessage_27", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.MessageAdmin
 * JD-Core Version:    0.7.0.1
 */