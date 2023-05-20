package com.ffusion.csil.core;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.BankMessageInfo;
import com.ffusion.beans.messages.EntitledMsgType;
import com.ffusion.beans.messages.EntitledMsgTypes;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageConsts;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.handlers.BankEmployeeAdmin;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.csil.handlers.UtilHandler;
import com.ffusion.services.Messaging2;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;

public class Messages
  extends Initialize
  implements GlobalMessageConsts
{
  private static Entitlement aIc = new Entitlement("Messages", null, null);
  private static Entitlement aIS = new Entitlement("CaseCrud", null, null);
  private static Entitlement aJs = new Entitlement("CaseView", null, null);
  private static Entitlement aJb = new Entitlement("GlobalMessageCrud", null, null);
  private static Entitlement aKa = new Entitlement("GlobalMessageView", null, null);
  private static Entitlement aIO = new Entitlement("Secure Message - Create/Edit/Delete", null, null);
  private static Entitlement aIF = new Entitlement("Secure Message - View", null, null);
  private static Entitlement aID = new Entitlement("Unsecure Message - Create/Edit/Delete", null, null);
  private static Entitlement aIh = new Entitlement("Unsecure Message - View", null, null);
  private static Entitlement aKf = new Entitlement("Login Message - Create/Edit/Delete", null, null);
  private static Entitlement aIz = new Entitlement("Login Message - View", null, null);
  private static Entitlement aIU = new Entitlement("Home Page Message - Create/Edit/Delete", null, null);
  private static Entitlement aIR = new Entitlement("Home Page Message - View", null, null);
  private static Entitlement aJO = new Entitlement("ACH Message - Create/Edit/Delete", null, null);
  private static Entitlement aIH = new Entitlement("ACH Message - View", null, null);
  private static Entitlement aI2 = new Entitlement("Wire Message - Create/Edit/Delete", null, null);
  private static Entitlement aIr = new Entitlement("Wire Message - View", null, null);
  private static Entitlement aIu = new Entitlement("Business Admin Message - Create/Edit/Delete", null, null);
  private static Entitlement aJS = new Entitlement("Business Admin Message - View", null, null);
  private static Entitlement aIb = new Entitlement("Payments Message - Create/Edit/Delete", null, null);
  private static Entitlement aJ6 = new Entitlement("Payments Message - View", null, null);
  private static Entitlement aJq = new Entitlement("Transfers Message - Create/Edit/Delete", null, null);
  private static Entitlement aIj = new Entitlement("Transfers Message - View", null, null);
  private static Entitlement aKm = new Entitlement("Manage Corporate Banking", null, null);
  private static Entitlement aJr = new Entitlement("Manage Consumer Banking", null, null);
  private static Entitlement[] aI8 = { null, aIF, aIh, aIH, aIr, aIR, aIz, aJS, aJ6, aIj };
  private static Entitlement[] aIs = { null, aIO, aID, aJO, aI2, aIU, aKf, aIu, aIb, aJq };
  private static boolean[] aI5 = { false, true, true, true, true, true, true, true, true, true };
  private static boolean[] aJj = { false, true, true, false, false, true, true, false, true, true };
  private static boolean[] aJ5 = { false, true, true, true, true, true, true, true, true, true };
  private static boolean[] aJ8 = { false, true, true, false, false, true, true, false, true, true };
  private static final String aJo = "com.ffusion.util.logging.audit.messages";
  private static final String aI3 = "AuditMessage_1";
  private static final String aJc = "AuditMessage_2";
  private static final String aI4 = "AuditMessage_3";
  private static final String aIG = "AuditMessage_4";
  private static final String aJT = "AuditMessage_5";
  private static final String aKg = "AuditMessage_6";
  private static final String aJn = "AuditMessage_7";
  private static final String aIq = "AuditMessage_8";
  private static final String aJN = "AuditMessage_9";
  private static final String aJL = "AuditMessage_10";
  private static final String aJJ = "AuditMessage_11";
  private static final String aJI = "AuditMessage_12";
  private static final String aJG = "AuditMessage_13";
  private static final String aJE = "AuditMessage_14";
  private static final String aJB = "AuditMessage_15";
  private static final String aJz = "AuditMessage_16";
  private static final String aJx = "AuditMessage_17";
  private static final String aJg = "AuditMessage_18";
  private static final String aJf = "AuditMessage_19";
  private static final String aJd = "AuditMessage_20";
  private static final String aI9 = "AuditMessage_21";
  private static final String aIe = "AuditMessage_22";
  private static final String aJH = "AuditMessage_23";
  private static final String aJF = "AuditMessage_24";
  private static final String aIT = "AuditMessage_25";
  private static final String aJ9 = "AuditMessage_26";
  private static final String aJ7 = "AuditMessage_27";
  private static final String aJ4 = "AuditMessage_28";
  private static final String aJ3 = "AuditMessage_29";
  private static final String aJ2 = "AuditMessage_30";
  private static final String aJ0 = "AuditMessage_31";
  private static final String aJY = "AuditMessage_32";
  private static final String aJW = "AuditMessage_33";
  private static final String aJV = "AuditMessage_34";
  private static final String aIi = "AuditMessage_35";
  private static final String aKh = "AuditMessage_36";
  private static final String aJ1 = "AuditMessage_37";
  private static final String aJZ = "AuditMessage_38";
  private static final String aJX = "AuditMessage_39";
  private static final String aJU = "AuditMessage_40";
  private static final String aJR = "AuditMessage_41";
  private static final String aIx = "AuditMessage_42";
  private static final String aJD = "AuditMessage_43";
  private static final String aJA = "AuditMessage_44";
  private static final String aJe = "AuditMessage_45";
  private static final String aJy = "AuditMessage_46";
  private static final String aJw = "AuditMessage_47";
  private static final String aI6 = "AuditMessage_48";
  private static final String aIv = "AuditMessage_49";
  private static final String aIt = "AuditMessage_50";
  private static final String aIw = "AuditMessage_51";
  private static final String aIa = "AuditMessage_52";
  private static final String aIg = "AuditMessage_53";
  private static final String aKe = "AuditMessage_54";
  private static final String aIo = "AuditMessage_55";
  private static final String aJM = "AuditMessage_56";
  private static final String aJK = "AuditMessage_57";
  private static final String aIX = "AuditMessage_58";
  private static final String aKb = "AuditMessage_59";
  private static final String aIN = "AuditMessage_60";
  private static final String aIL = "AuditMessage_61";
  private static final String aKj = "AuditMessage_62";
  private static final String aJv = "AuditMessage_63";
  private static final String aJu = "AuditMessage_64";
  private static final String aIp = "AuditMessage_65";
  private static final String aKk = "AuditMessage_66";
  private static final String aIK = "AuditMessage_67";
  private static final String aJa = "AuditMessage_68";
  private static final String aJC = "AuditMessage_69";
  private static final String aIf = "AuditMessage_70";
  private static final String aJQ = "AuditMessage_71";
  private static final String aIW = "AuditMessage_72";
  private static final String aIm = "AuditMessage_73";
  private static final String aJh = "AuditMessage_74";
  private static final String aIQ = "AuditMessage_75";
  private static final String aI7 = "AuditMessage_76";
  private static final String aIP = "AuditMessage_77";
  private static final String aJP = "AuditMessage_78";
  private static final String aKd = "AuditMessage_79";
  private static final String aI1 = "AuditMessage_80";
  private static final String aI0 = "AuditMessage_81";
  private static final String aIZ = "AuditMessage_82";
  private static final String aIY = "AuditMessage_83";
  private static final String aIV = "AuditMessage_84";
  private static final String aIB = "AuditMessage_85";
  private static final String aIk = "AuditMessage_86";
  private static final String aJm = "AuditMessage_87";
  private static final String aKc = "AuditMessage_88";
  private static final String aJl = "AuditMessage_89";
  private static final String aIC = "AuditMessage_90";
  private static final String aIE = "AuditMessage_91";
  private static final String aJi = "AuditMessage_92";
  private static final String aIl = "AuditMessage_93";
  private static final String aIM = "AuditMessage_94";
  private static final String aIn = "AuditMessage_95";
  private static final String aIy = "AuditMessage_96";
  private static final String aId = "AuditMessage_97";
  private static final String aKl = "AuditMessage_98";
  private static final String aKi = "AuditMessage_99";
  private static final String aIJ = "AuditMessage_100";
  private static final String aII = "AuditMessage_101";
  private static final String aJt = "AuditMessage_102";
  private static final String aJp = "AuditMessage_103";
  private static final String aIA = "AuditMessage_104";
  private static final String aJk = "AuditMessage_105";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Messages.initialize");
    com.ffusion.csil.handlers.Messages.initialize(paramHashMap);
  }
  
  public static Object getService(String paramString)
  {
    return com.ffusion.csil.handlers.Messages.getService(paramString);
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
        String str1 = "Messages.SignOn";
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIc))
        {
          long l = System.currentTimeMillis();
          String str2 = TrackingIDGenerator.GetNextID();
          SecureUser localSecureUser = com.ffusion.csil.handlers.Messages.signOn(paramSecureUser, paramString1, paramString2, paramHashMap);
          PerfLog.log(str1, l, true);
          debug(paramSecureUser, str1);
          return localSecureUser;
        }
        Object[] arrayOfObject = new Object[0];
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_1", arrayOfObject);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
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
        String str1 = "Messages.SignOff";
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIc))
        {
          long l = System.currentTimeMillis();
          String str2 = TrackingIDGenerator.GetNextID();
          SecureUser localSecureUser = com.ffusion.csil.handlers.Messages.signOff(paramSecureUser, paramHashMap);
          PerfLog.log(str1, l, true);
          debug(paramSecureUser, str1);
          return localSecureUser;
        }
        Object[] arrayOfObject = new Object[0];
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_2", arrayOfObject);
        logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
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
        String str1 = "Messages.ChangePIN";
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIc))
        {
          EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
          long l = System.currentTimeMillis();
          String str2 = TrackingIDGenerator.GetNextID();
          SecureUser localSecureUser = com.ffusion.csil.handlers.Messages.changePIN(paramSecureUser, paramString1, paramString2, paramHashMap);
          PerfLog.log(str1, l, true);
          Object[] arrayOfObject2 = new Object[0];
          LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_4", arrayOfObject2);
          audit(paramSecureUser, localLocalizableString2, str2, 2700);
          debug(paramSecureUser, str1);
          return localSecureUser;
        }
        Object[] arrayOfObject1 = new Object[0];
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_3", arrayOfObject1);
        logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
    }
    return paramSecureUser;
  }
  
  public static com.ffusion.beans.messages.Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetMessages";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      com.ffusion.beans.messages.Messages localMessages = com.ffusion.csil.handlers.Messages.getMessages(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessages;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_5", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static String getDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "Messages.getDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Messages.getDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_6", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getMaxResultCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "Messages.getMaxResultCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      String str2 = com.ffusion.csil.handlers.Messages.getMaxResultCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_7", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.SendMessage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      int i = (paramMessage.getFromValue() == paramSecureUser.getProfileID()) && (((paramSecureUser.getUserType() == 1) && (paramMessage.getFromTypeValue() == 1)) || ((paramSecureUser.getUserType() == 2) && (paramMessage.getFromTypeValue() == 0))) ? 1 : 0;
      int j = (paramMessage.getToValue() == paramSecureUser.getProfileID()) && (((paramSecureUser.getUserType() == 1) && (paramMessage.getToTypeValue() == 1)) || ((paramSecureUser.getUserType() == 2) && (paramMessage.getToTypeValue() == 0))) ? 1 : 0;
      if ((i == 0) && (j == 0))
      {
        DebugLog.log(Level.SEVERE, "Message.sendMessage.  Attempted to send a message where the sender or receiver information in the message did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      Message localMessage = com.ffusion.csil.handlers.Messages.sendMessage(paramSecureUser, paramMessage, paramHashMap);
      PerfLog.log(str1, l, true);
      int k = 0;
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      Object[] arrayOfObject2 = new Object[6];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = "";
      arrayOfObject2[2] = "";
      arrayOfObject2[3] = "";
      arrayOfObject2[4] = "";
      arrayOfObject2[5] = "";
      if (paramMessage.getToTypeValue() == 1)
      {
        localObject = new User();
        try
        {
          ((User)localObject).setId(paramMessage.getTo());
          localObject = UserAdminHandler.getUserById(paramSecureUser, (User)localObject, paramHashMap);
        }
        catch (CSILException localCSILException1) {}
        if ((((User)localObject).getName() != null) && (((User)localObject).equals("")))
        {
          k = 1;
          arrayOfObject2[0] = ((User)localObject).getName();
          if (((User)localObject).get("BUSINESS_NAME") != null)
          {
            m = 1;
            arrayOfObject2[1] = ((User)localObject).get("BUSINESS_NAME");
          }
          if (((User)localObject).get("CUST_ID") != null)
          {
            n = 1;
            arrayOfObject2[2] = ((User)localObject).get("CUST_ID");
          }
        }
      }
      else if (paramMessage.getToTypeValue() == 0)
      {
        localObject = new BankEmployee(paramSecureUser.getLocale());
        try
        {
          ((BankEmployee)localObject).setId(paramMessage.getTo());
          localObject = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject, paramHashMap);
        }
        catch (CSILException localCSILException2) {}
        if ((((BankEmployee)localObject).getName() != null) && (((BankEmployee)localObject).equals("")))
        {
          i1 = 1;
          arrayOfObject2[3] = ((BankEmployee)localObject).getName();
        }
      }
      if (paramMessage.getCaseNum() != null)
      {
        i2 = 1;
        arrayOfObject2[4] = paramMessage.getCaseNum();
      }
      else if (paramMessage.getMsgThreadID() != null)
      {
        i2 = 1;
        arrayOfObject2[4] = paramMessage.getMsgThreadID();
      }
      arrayOfObject2[5] = paramMessage.getSubject();
      Object localObject = null;
      if ((k == 0) && (m == 0) && (n == 0) && (i1 == 0) && (i2 == 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_9", arrayOfObject2);
      } else if ((k == 0) && (m == 0) && (n == 0) && (i1 == 0) && (i2 != 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_10", arrayOfObject2);
      } else if ((k == 0) && (m == 0) && (n == 0) && (i1 != 0) && (i2 == 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_11", arrayOfObject2);
      } else if ((k == 0) && (m == 0) && (n == 0) && (i1 != 0) && (i2 != 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_12", arrayOfObject2);
      } else if ((k != 0) && (m == 0) && (n != 0) && (i1 == 0) && (i2 == 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_13", arrayOfObject2);
      } else if ((k != 0) && (m == 0) && (n != 0) && (i1 == 0) && (i2 != 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_14", arrayOfObject2);
      } else if ((k != 0) && (m == 0) && (n == 0) && (i1 == 0) && (i2 == 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_15", arrayOfObject2);
      } else if ((k != 0) && (m == 0) && (n == 0) && (i1 == 0) && (i2 != 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_16", arrayOfObject2);
      } else if ((k != 0) && (m != 0) && (n != 0) && (i1 == 0) && (i2 == 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_17", arrayOfObject2);
      } else if ((k != 0) && (m != 0) && (n != 0) && (i1 == 0) && (i2 != 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_18", arrayOfObject2);
      } else if ((k != 0) && (m != 0) && (n == 0) && (i1 == 0) && (i2 == 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_19", arrayOfObject2);
      } else if ((k != 0) && (m != 0) && (n == 0) && (i1 == 0) && (i2 != 0)) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_20", arrayOfObject2);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_21", arrayOfObject2);
      }
      audit(paramSecureUser, (ILocalizable)localObject, str2, 2701);
      debug(paramSecureUser, str1);
      return localMessage;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_8", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Message sendInternalMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws CSILException
  {
    String str1 = "Messages.SendInternalMessage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Message localMessage = com.ffusion.csil.handlers.Messages.sendInternalMessage(paramSecureUser, paramHashMap, paramMessage);
      PerfLog.log(str1, l, true);
      BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
      try
      {
        localBankEmployee.setId(paramMessage.getTo());
        localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
      }
      catch (CSILException localCSILException) {}
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = "";
      if (paramMessage.getCaseNum() != null) {
        arrayOfObject2[1] = paramMessage.getCaseNum();
      }
      arrayOfObject2[2] = paramMessage.getSubject();
      LocalizableString localLocalizableString2 = null;
      if ((localBankEmployee.getName() != null) && (!localBankEmployee.getName().equals("")))
      {
        arrayOfObject2[0] = localBankEmployee.getName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_23", arrayOfObject2);
      }
      else
      {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_24", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2701);
      debug(paramSecureUser, str1);
      return localMessage;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_22", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Message sendReplyMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, boolean paramBoolean)
    throws CSILException
  {
    String str1 = "Messages.SendReplyMessage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      if ((paramMessage.getFromValue() != paramSecureUser.getProfileID()) || ((paramSecureUser.getUserType() == 1) && (paramMessage.getFromTypeValue() != 1)) || ((paramSecureUser.getUserType() == 2) && (paramMessage.getFromTypeValue() != 0)))
      {
        DebugLog.log(Level.SEVERE, "Message.sendMessage.  Attempted to send a message where message sender information did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      String str3 = paramMessage.getTo();
      int i = paramMessage.getToTypeValue();
      Message localMessage = com.ffusion.csil.handlers.Messages.sendReplyMessage(paramSecureUser, paramHashMap, paramMessage, paramBoolean);
      PerfLog.log(str1, l, true);
      Object localObject;
      int k;
      int m;
      if ((i == 1) && (paramMessage.getToTypeValue() == 0))
      {
        localObject = new User();
        BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
        try
        {
          ((User)localObject).setId(str3);
          localObject = UserAdminHandler.getUserById(paramSecureUser, (User)localObject, paramHashMap);
          localBankEmployee.setId(paramMessage.getTo());
          localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
        }
        catch (CSILException localCSILException3) {}
        Object[] arrayOfObject4 = new Object[6];
        arrayOfObject4[0] = "";
        arrayOfObject4[1] = "";
        arrayOfObject4[2] = "";
        arrayOfObject4[3] = "";
        arrayOfObject4[4] = "";
        if (paramMessage.getCaseNum() != null) {
          arrayOfObject4[4] = paramMessage.getCaseNum();
        }
        arrayOfObject4[5] = paramMessage.getSubject();
        k = 0;
        m = 0;
        int n = 0;
        int i1 = 0;
        if ((localBankEmployee != null) && (localBankEmployee.getName() != null) && (!localBankEmployee.getName().equals("")))
        {
          k = 1;
          arrayOfObject4[0] = localBankEmployee.getName();
        }
        if ((((User)localObject).getName() != null) && (!((User)localObject).getName().equals("")))
        {
          m = 1;
          arrayOfObject4[1] = ((User)localObject).getName();
          if (((User)localObject).get("BUSINESS_NAME") != null)
          {
            n = 1;
            arrayOfObject4[2] = ((User)localObject).get("BUSINESS_NAME");
          }
          if (((User)localObject).get("CUST_ID") != null)
          {
            i1 = 1;
            arrayOfObject4[3] = ((User)localObject).get("CUST_ID");
          }
        }
        LocalizableString localLocalizableString4 = null;
        if ((k == 0) && (m == 0) && (n == 0) && (i1 == 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_26", arrayOfObject4);
        } else if ((k == 0) && (m != 0) && (n == 0) && (i1 == 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_27", arrayOfObject4);
        } else if ((k == 0) && (m != 0) && (n == 0) && (i1 != 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_28", arrayOfObject4);
        } else if ((k == 0) && (m != 0) && (n != 0) && (i1 == 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_29", arrayOfObject4);
        } else if ((k == 0) && (m != 0) && (n != 0) && (i1 != 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_30", arrayOfObject4);
        } else if ((k != 0) && (m == 0) && (n == 0) && (i1 == 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_31", arrayOfObject4);
        } else if ((k != 0) && (m != 0) && (n == 0) && (i1 == 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_32", arrayOfObject4);
        } else if ((k != 0) && (m != 0) && (n == 0) && (i1 != 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_33", arrayOfObject4);
        } else if ((k != 0) && (m != 0) && (n != 0) && (i1 == 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_34", arrayOfObject4);
        } else if ((k != 0) && (m != 0) && (n != 0) && (i1 != 0)) {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_35", arrayOfObject4);
        } else {
          localLocalizableString4 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_36", arrayOfObject4);
        }
        audit(paramSecureUser, localLocalizableString4, str2, 2701);
      }
      else if (paramMessage.getToTypeValue() == 1)
      {
        localObject = new User();
        try
        {
          ((User)localObject).setId(paramMessage.getTo());
          localObject = UserAdminHandler.getUserById(paramSecureUser, (User)localObject, paramHashMap);
        }
        catch (CSILException localCSILException1) {}
        Object[] arrayOfObject2 = new Object[6];
        arrayOfObject2[0] = "";
        arrayOfObject2[1] = "";
        arrayOfObject2[2] = "";
        arrayOfObject2[3] = "";
        arrayOfObject2[4] = "";
        if (paramMessage.getCaseNum() != null) {
          arrayOfObject2[4] = paramMessage.getCaseNum();
        }
        arrayOfObject2[5] = paramMessage.getSubject();
        int j = 0;
        k = 0;
        m = 0;
        if ((((User)localObject).getName() != null) && (!((User)localObject).getName().equals("")))
        {
          j = 1;
          arrayOfObject2[1] = ((User)localObject).getName();
          if (((User)localObject).get("BUSINESS_NAME") != null)
          {
            k = 1;
            arrayOfObject2[2] = ((User)localObject).get("BUSINESS_NAME");
          }
          if (((User)localObject).get("CUST_ID") != null)
          {
            m = 1;
            arrayOfObject2[3] = ((User)localObject).get("CUST_ID");
          }
        }
        LocalizableString localLocalizableString3 = null;
        if ((j == 0) && (k == 0) && (m == 0)) {
          localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_37", arrayOfObject2);
        } else if ((j != 0) && (k == 0) && (m == 0)) {
          localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_38", arrayOfObject2);
        } else if ((j != 0) && (k == 0) && (m != 0)) {
          localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_39", arrayOfObject2);
        } else if ((j != 0) && (k != 0) && (m == 0)) {
          localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_40", arrayOfObject2);
        } else if ((j != 0) && (k != 0) && (m != 0)) {
          localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_41", arrayOfObject2);
        } else {
          localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_42", arrayOfObject2);
        }
        audit(paramSecureUser, localLocalizableString3, str2, 2701);
      }
      else if (paramMessage.getToTypeValue() == 0)
      {
        localObject = null;
        try
        {
          localObject = new BankEmployee(paramSecureUser.getLocale());
          ((BankEmployee)localObject).setId(paramMessage.getTo());
          localObject = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject, paramHashMap);
        }
        catch (CSILException localCSILException2) {}
        Object[] arrayOfObject3 = new Object[6];
        arrayOfObject3[0] = "";
        arrayOfObject3[1] = "";
        arrayOfObject3[2] = "";
        arrayOfObject3[3] = "";
        arrayOfObject3[4] = "";
        if (paramMessage.getCaseNum() != null) {
          arrayOfObject3[4] = paramMessage.getCaseNum();
        }
        arrayOfObject3[5] = paramMessage.getSubject();
        LocalizableString localLocalizableString2 = null;
        if ((((BankEmployee)localObject).getName() != null) && (!((BankEmployee)localObject).getName().equals("")))
        {
          arrayOfObject3[0] = ((BankEmployee)localObject).getName();
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_43", arrayOfObject3);
        }
        else
        {
          localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_44", arrayOfObject3);
        }
        audit(paramSecureUser, localLocalizableString2, str2, 2701);
      }
      debug(paramSecureUser, str1);
      return localMessage;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_25", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageThread sendMessageThread(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.sendMessageThread";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      if ((paramMessage.getFromValue() != paramSecureUser.getProfileID()) || ((paramSecureUser.getUserType() == 1) && (paramMessage.getFromTypeValue() != 1)) || ((paramSecureUser.getUserType() == 2) && (paramMessage.getFromTypeValue() != 0)))
      {
        DebugLog.log(Level.SEVERE, "Message.sendMessageThread.  Attempted to send a message where message sender information did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      String str2 = TrackingIDGenerator.GetNextID();
      MessageThread localMessageThread = com.ffusion.csil.handlers.Messages.sendMessageThread(paramSecureUser, paramMessage, paramHashMap);
      PerfLog.log(str1, l, true);
      BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
      try
      {
        localBankEmployee.setId(paramMessage.getTo());
        localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
      }
      catch (CSILException localCSILException) {}
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = "";
      if (paramMessage.getCaseNum() != null) {
        arrayOfObject2[1] = paramMessage.getCaseNum();
      }
      arrayOfObject2[2] = paramMessage.getSubject();
      LocalizableString localLocalizableString2 = null;
      if ((localBankEmployee.getName() != null) && (!localBankEmployee.getName().equals("")))
      {
        arrayOfObject2[0] = localBankEmployee.getName();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_46", arrayOfObject2);
      }
      else
      {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_47", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2701);
      debug(paramSecureUser, str1);
      return localMessageThread;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_45", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Message deleteMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.DeleteMessage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      Message localMessage = com.ffusion.csil.handlers.Messages.deleteMessage(paramSecureUser, paramMessage, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramMessage.getSubject();
      arrayOfObject2[1] = "";
      LocalizableString localLocalizableString2 = null;
      if (paramMessage.getCaseNum() != null)
      {
        arrayOfObject2[1] = paramMessage.getCaseNum();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_49", arrayOfObject2);
      }
      else
      {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_50", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2702);
      debug(paramSecureUser, str1);
      return localMessage;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_48", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static Message markMessageAsRead(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.MarkMessageAsRead";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      Message localMessage = com.ffusion.csil.handlers.Messages.markMessageAsRead(paramSecureUser, paramMessage, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessage;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_51", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int getNumberOfUnreadMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetNumberOfUnreadMessages";
    if ((paramSecureUser.getUserType() == 1) || (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs)))
    {
      long l = System.currentTimeMillis();
      int i = com.ffusion.csil.handlers.Messages.getNumberOfUnreadMessages(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return i;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_52", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetNumberOfUnreadMessagesExcludingAlerts";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      int i = com.ffusion.csil.handlers.Messages.getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return i;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_53", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads findMessageThreads(SecureUser paramSecureUser, HashMap paramHashMap, User paramUser, Business paramBusiness, MessageThread paramMessageThread, DateTime paramDateTime1, DateTime paramDateTime2)
    throws CSILException
  {
    String str = "Messages.findMessageThreads";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      MessageThreads localMessageThreads = com.ffusion.csil.handlers.Messages.findMessageThreads(paramSecureUser, paramHashMap, paramUser, paramBusiness, paramMessageThread, paramDateTime1, paramDateTime2);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_54", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void createNewCase(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage, String paramString, boolean paramBoolean)
    throws CSILException
  {
    String str1 = "Messages.createNewCase";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      if ((paramMessage.getFromValue() != paramSecureUser.getProfileID()) || ((paramSecureUser.getUserType() == 1) && (paramMessage.getFromTypeValue() != 1)) || ((paramSecureUser.getUserType() == 2) && (paramMessage.getFromTypeValue() != 0)))
      {
        DebugLog.log(Level.SEVERE, "Message.createNewCase.  Attempted to send a message where message sender information did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      com.ffusion.csil.handlers.Messages.createNewCase(paramSecureUser, paramHashMap, paramMessage, paramString, paramBoolean);
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramMessage.getSubject();
      arrayOfObject2[1] = "";
      LocalizableString localLocalizableString2 = null;
      if ((paramMessage.getCaseNum() != null) && (!paramMessage.getCaseNum().equals("")))
      {
        arrayOfObject2[1] = paramMessage.getCaseNum();
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_56", arrayOfObject2);
      }
      else
      {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_57", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2703);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_55", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static com.ffusion.beans.messages.Messages getMessages(SecureUser paramSecureUser, HashMap paramHashMap, String paramString)
    throws CSILException
  {
    String str = "Messages.getMessages";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      com.ffusion.beans.messages.Messages localMessages = com.ffusion.csil.handlers.Messages.getMessages(paramSecureUser, paramHashMap, paramString);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessages;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_58", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static GlobalMessage getGlobalMessageById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetGlobalMessageById";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aKa))
    {
      long l = System.currentTimeMillis();
      GlobalMessage localGlobalMessage = com.ffusion.csil.handlers.Messages.getGlobalMessageById(paramSecureUser, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localGlobalMessage;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_90", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void approveMessage(SecureUser paramSecureUser, HashMap paramHashMap, Message paramMessage)
    throws CSILException
  {
    String str1 = "Messages.approveMessage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.Messages.approveMessage(paramSecureUser, paramHashMap, paramMessage);
      String str2 = TrackingIDGenerator.GetNextID();
      User localUser = new User();
      try
      {
        localUser.setId(paramMessage.getTo());
        localUser = UserAdminHandler.getUserById(paramSecureUser, localUser, paramHashMap);
      }
      catch (CSILException localCSILException1) {}
      BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
      try
      {
        localBankEmployee.setId(paramMessage.getFrom());
        localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
      }
      catch (CSILException localCSILException2) {}
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = "";
      int i = 0;
      int j = 0;
      if ((paramMessage.getCaseNum() != null) && (!paramMessage.getCaseNum().equals("")))
      {
        i = 1;
        arrayOfObject2[0] = paramMessage.getCaseNum();
      }
      else if ((paramMessage.getMsgThreadID() != null) && (!paramMessage.getMsgThreadID().equals("")))
      {
        i = 1;
        arrayOfObject2[0] = paramMessage.getMsgThreadID();
      }
      if (paramMessage.getSubject() != null)
      {
        j = 1;
        arrayOfObject2[1] = paramMessage.getSubject();
      }
      LocalizableString localLocalizableString2 = null;
      if ((i == 0) && (j == 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_100", arrayOfObject2);
      } else if ((i == 0) && (j != 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_61", arrayOfObject2);
      } else if ((i != 0) && (j == 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_101", arrayOfObject2);
      } else {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_60", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2704);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_59", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void denyMessage(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread)
    throws CSILException
  {
    String str1 = "Messages.denyMessage";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.Messages.denyMessage(paramSecureUser, paramHashMap, paramMessageThread);
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = "";
      int i = 0;
      int j = 0;
      if ((paramMessageThread.getCaseNum() != null) && (!paramMessageThread.getCaseNum().equals("")))
      {
        i = 1;
        arrayOfObject2[0] = paramMessageThread.getCaseNum();
      }
      if (paramMessageThread.getSubject() != null)
      {
        j = 1;
        arrayOfObject2[1] = paramMessageThread.getSubject();
      }
      LocalizableString localLocalizableString2 = null;
      if ((i == 0) && (j == 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_102", arrayOfObject2);
      } else if ((i == 0) && (j != 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_64", arrayOfObject2);
      } else if ((i != 0) && (j == 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_103", arrayOfObject2);
      } else {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_63", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2705);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_62", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static MessageCounts getAssignedMessageCountsByEmployee(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return getAssignedMessageCountsByEmployee(paramSecureUser, paramString, paramHashMap, null);
  }
  
  public static MessageCounts getAssignedMessageCountsByEmployee(SecureUser paramSecureUser, String paramString1, HashMap paramHashMap, String paramString2)
    throws CSILException
  {
    String str = "Messages.getAssignedMessageCountsByEmployee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("EMPLOYEE_ID", paramString1);
      if ((paramString2 != null) && (!paramString2.trim().equals(""))) {
        paramHashMap.put("affiliate_bank", paramString2);
      }
      int i = com.ffusion.csil.handlers.Messages.getAssignedMessageCount(paramSecureUser, paramHashMap);
      MessageCounts localMessageCounts = (MessageCounts)paramHashMap.get("EmployeeAssignedCounts");
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_65", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageCounts getAssignedMessageCountsByQueue(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getAssignedMessageCountsByQueue";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("QUEUE_ID", paramString);
      int i = com.ffusion.csil.handlers.Messages.getAssignedMessageCount(paramSecureUser, paramHashMap);
      MessageCounts localMessageCounts = (MessageCounts)paramHashMap.get("QueueAssignedCounts");
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_66", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int getAssignedMessageCount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getAssignedMessageCount";
    int i = 0;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      i = com.ffusion.csil.handlers.Messages.getAssignedMessageCount(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return i;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_67", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int getAssignedMessageCount(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getAssignedMessageCount";
    int i = 0;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      SecureUser localSecureUser = new SecureUser();
      localSecureUser.setBankID(paramSecureUser.getBankID());
      localSecureUser.setId(paramString);
      i = com.ffusion.csil.handlers.Messages.getAssignedMessageCount(localSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return i;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_68", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageCounts getMessageCountsByHelpCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getMessageCountsByHelpCases";
    MessageCounts localMessageCounts = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageCounts = com.ffusion.csil.handlers.Messages.getMessageCountsByHelpCases(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_69", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageCounts getMessageCountsByHelpCasesProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getMessageCountsByHelpCasesProvidedAndClosed";
    MessageCounts localMessageCounts = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageCounts = com.ffusion.csil.handlers.Messages.getMessageCountsByHelpCasesProvidedAndClosed(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_69", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageCounts getMessageCountsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getMessageCountsByHelpCases";
    MessageCounts localMessageCounts = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageCounts = com.ffusion.csil.handlers.Messages.getMessageCountsByNewCases(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_70", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageCounts getMessageCountsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getMessageCountsByPersonalBanker";
    MessageCounts localMessageCounts = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageCounts = com.ffusion.csil.handlers.Messages.getMessageCountsByPersonalBanker(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_71", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageCounts getMessageCountsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getMessageCountsByPending";
    MessageCounts localMessageCounts = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageCounts = com.ffusion.csil.handlers.Messages.getMessageCountsByPending(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageCounts;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_72", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads getThreadsByAssigned(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getThreadsByAssigned";
    MessageThreads localMessageThreads = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageThreads = com.ffusion.csil.handlers.Messages.getThreadsByAssigned(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_73", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads getThreadsByHelpProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getThreadsByHelpProvidedAndClosed";
    MessageThreads localMessageThreads = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageThreads = com.ffusion.csil.handlers.Messages.getThreadsByHelpProvidedAndClosed(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_74", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads getThreadsByHelpRequested(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getThreadsByHelpRequested";
    MessageThreads localMessageThreads = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageThreads = com.ffusion.csil.handlers.Messages.getThreadsByHelpRequested(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_75", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads getThreadsByNewCases(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getThreadsByNewCases";
    MessageThreads localMessageThreads = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageThreads = com.ffusion.csil.handlers.Messages.getThreadsByNewCases(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_76", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads getThreadsByPersonalBanker(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getThreadsByPersonalBanker";
    MessageThreads localMessageThreads = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageThreads = com.ffusion.csil.handlers.Messages.getThreadsByPersonalBanker(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_77", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static MessageThreads getThreadsByPending(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.getThreadsByPending";
    MessageThreads localMessageThreads = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs))
    {
      long l = System.currentTimeMillis();
      localMessageThreads = com.ffusion.csil.handlers.Messages.getThreadsByPending(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localMessageThreads;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_78", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static void reassignThread(SecureUser paramSecureUser, HashMap paramHashMap, MessageThread paramMessageThread, String paramString)
    throws CSILException
  {
    String str1 = "Messages.reassignThread";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.Messages.reassignThread(paramSecureUser, paramHashMap, paramMessageThread, paramString);
      String str2 = TrackingIDGenerator.GetNextID();
      BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
      try
      {
        localBankEmployee.setId(paramString);
        localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, localBankEmployee, paramHashMap);
      }
      catch (CSILException localCSILException) {}
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "";
      arrayOfObject2[1] = "";
      int i = 0;
      int j = 0;
      LocalizableString localLocalizableString2 = null;
      if (paramMessageThread.getCaseNum() != null)
      {
        i = 1;
        arrayOfObject2[0] = paramMessageThread.getCaseNum();
      }
      if ((localBankEmployee.getName() != null) && (!localBankEmployee.getName().equals("")))
      {
        j = 1;
        arrayOfObject2[1] = localBankEmployee.getName();
      }
      if ((i == 0) && (j == 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_80", arrayOfObject2);
      } else if ((i == 0) && (j != 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_81", arrayOfObject2);
      } else if ((i != 0) && (j == 0)) {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_82", arrayOfObject2);
      } else {
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_83", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str2, 2706);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_79", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.CreateGlobalMessage";
    String str2 = UtilHandler.getDefaultLanguage(paramHashMap).toString();
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJb)) && (jdMethod_int(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramGlobalMessage.getMsgTypeValue())))
    {
      if (paramGlobalMessage.getFromIDValue() != paramSecureUser.getProfileID())
      {
        DebugLog.log(Level.SEVERE, "Message.CreateGlobalMessage.  Attempted to send a global message where message sender information did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      long l = System.currentTimeMillis();
      ArrayList localArrayList = null;
      String str3 = jdMethod_for(paramGlobalMessage);
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
      Iterator localIterator = localLanguageDefns.iterator();
      while (localIterator.hasNext())
      {
        LanguageDefn localLanguageDefn1 = (LanguageDefn)localIterator.next();
        paramGlobalMessage.setToGroupName(localLanguageDefn1.getLanguage(), str3);
      }
      int i = 1;
      if (paramGlobalMessage.getFromName(str2) == null)
      {
        localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
        localIterator = localLanguageDefns.iterator();
        while (localIterator.hasNext())
        {
          LanguageDefn localLanguageDefn2 = (LanguageDefn)localIterator.next();
          paramGlobalMessage.setFromName(localLanguageDefn2.getLanguage(), " ");
        }
        i = 0;
      }
      int j = globalMsgApprovalRequired(paramSecureUser, paramHashMap);
      if (j == -1) {
        throw new CSILException(str1, 5100);
      }
      if ((j == 0) || (paramGlobalMessage.getRecordTypeValue() == 2))
      {
        paramGlobalMessage.setStatus(2);
        paramGlobalMessage.setApprovalEmployeeID(paramSecureUser.getProfileID());
      }
      else
      {
        paramGlobalMessage.setStatus(1);
        paramGlobalMessage.setApprovalEmployeeID(j);
      }
      com.ffusion.csil.handlers.Messages.createGlobalMessage(paramSecureUser, paramGlobalMessage, localArrayList, paramHashMap);
      String str4 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = (paramGlobalMessage.getSubject(str2) == null ? "" : paramGlobalMessage.getSubject(str2));
      LocalizableString localLocalizableString2 = null;
      if (i != 0)
      {
        arrayOfObject2[1] = (paramGlobalMessage.getFromName(str2) == null ? "" : paramGlobalMessage.getFromName(str2));
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_85", arrayOfObject2);
      }
      else
      {
        arrayOfObject2[1] = "";
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_104", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str4, 2707);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_84", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void deleteGlobalMessage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.DeleteGlobalMessage";
    String str2 = UtilHandler.getDefaultLanguage(paramHashMap).toString();
    Object localObject;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJb))
    {
      localObject = com.ffusion.csil.handlers.Messages.getGlobalMessageById(paramSecureUser, paramInt, paramHashMap);
      if (jdMethod_int(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ((GlobalMessage)localObject).getMsgTypeValue()))
      {
        long l = System.currentTimeMillis();
        String str3 = "";
        String str4 = "";
        try
        {
          GlobalMessage localGlobalMessage = com.ffusion.csil.handlers.Messages.getGlobalMessageById(paramSecureUser, paramInt, paramHashMap);
          str3 = localGlobalMessage.getSubject(str2);
          str4 = localGlobalMessage.getFromIDName();
        }
        catch (Exception localException) {}
        com.ffusion.csil.handlers.Messages.deleteGlobalMessage(paramSecureUser, paramInt, paramHashMap);
        String str5 = TrackingIDGenerator.GetNextID();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = str3;
        arrayOfObject[1] = str4;
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_87", arrayOfObject);
        audit(paramSecureUser, localLocalizableString2, str5, 2708);
        PerfLog.log(str1, l, true);
        debug(paramSecureUser, str1);
        return;
      }
    }
    else
    {
      localObject = new Object[0];
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_86", (Object[])localObject);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void modifyGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.ModifyGlobalMessage";
    String str2 = UtilHandler.getDefaultLanguage(paramHashMap).toString();
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJb)) && (jdMethod_int(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramGlobalMessage.getMsgTypeValue())))
    {
      long l = System.currentTimeMillis();
      String str3 = jdMethod_for(paramGlobalMessage);
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
      Iterator localIterator = localLanguageDefns.iterator();
      while (localIterator.hasNext())
      {
        LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
        paramGlobalMessage.setToGroupName(localLanguageDefn.getLanguage(), str3);
      }
      int i = 1;
      if (paramGlobalMessage.getFromName(str2) == null)
      {
        paramGlobalMessage.setFromName(str2, " ");
        i = 0;
      }
      com.ffusion.csil.handlers.Messages.modifyGlobalMessage(paramSecureUser, paramGlobalMessage, paramHashMap);
      String str4 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = (paramGlobalMessage.getSubject(str2) == null ? "" : paramGlobalMessage.getSubject(str2));
      LocalizableString localLocalizableString2 = null;
      if (i != 0)
      {
        arrayOfObject2[1] = paramGlobalMessage.getFromName(str2);
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_89", arrayOfObject2);
      }
      else
      {
        arrayOfObject2[1] = "";
        localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_105", arrayOfObject2);
      }
      audit(paramSecureUser, localLocalizableString2, str4, 2709);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_88", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetGlobalMessages";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aKa))
    {
      long l = System.currentTimeMillis();
      StringBuffer localStringBuffer = new StringBuffer();
      int i;
      if (paramGlobalMessage != null)
      {
        i = paramGlobalMessage.getMsgTypeValue();
        if (jdMethod_for(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), i))
        {
          localStringBuffer.append(i);
          paramHashMap.put("GlobalMsgEntitledMsgTypes", localStringBuffer.toString());
        }
        else
        {
          throw new CSILException(str, 20001);
        }
      }
      else
      {
        i = 0;
        int j = 1;
        for (int k = 1; k < aI8.length; k++) {
          if (jdMethod_for(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), k))
          {
            localStringBuffer.append(k + ",");
            j = 0;
          }
          else
          {
            i = 1;
          }
        }
        if (j != 0) {
          throw new CSILException(str, 20001);
        }
        if (i != 0)
        {
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          paramHashMap.put("GlobalMsgEntitledMsgTypes", localStringBuffer.toString());
        }
      }
      GlobalMessages localGlobalMessages = com.ffusion.csil.handlers.Messages.getGlobalMessages(paramSecureUser, paramGlobalMessage, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localGlobalMessages;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_90", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetGlobalMessages";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aKa))
    {
      long l = System.currentTimeMillis();
      ArrayList localArrayList = paramGlobalMessageSearchCriteria.getMsgTypes();
      int i;
      if ((localArrayList != null) && (localArrayList.size() != 0))
      {
        for (i = 0; i < localArrayList.size(); i++) {
          if (!jdMethod_for(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), ((Integer)localArrayList.get(i)).intValue())) {
            localArrayList.remove(i);
          }
        }
      }
      else
      {
        localArrayList = new ArrayList();
        i = 0;
        int j = 1;
        for (int k = 1; k < aI8.length; k++) {
          if (jdMethod_for(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), k))
          {
            localArrayList.add(new Integer(k));
            j = 0;
          }
          else
          {
            i = 1;
          }
        }
        if (j != 0) {
          throw new CSILException(str, 20001);
        }
        if (i != 0) {
          paramGlobalMessageSearchCriteria.setMsgTypes(localArrayList);
        }
      }
      GlobalMessages localGlobalMessages = com.ffusion.csil.handlers.Messages.getGlobalMessages(paramSecureUser, paramGlobalMessageSearchCriteria, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localGlobalMessages;
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_91", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static GlobalMessages getGlobalLoginMessages(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetGlobalMessages";
    long l = System.currentTimeMillis();
    GlobalMessages localGlobalMessages = com.ffusion.csil.handlers.Messages.getGlobalLoginMessages(paramString, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
    return localGlobalMessages;
  }
  
  public static GlobalMessages getGlobalLoginMessages(String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.GetGlobalMessages";
    long l = System.currentTimeMillis();
    GlobalMessages localGlobalMessages = com.ffusion.csil.handlers.Messages.getGlobalLoginMessages(paramString, paramInt1, paramInt2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
    return localGlobalMessages;
  }
  
  public static int sendBulkMessage(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
    throws CSILException
  {
    String str = "Messages.GetGlobalMessages";
    long l = System.currentTimeMillis();
    int i = com.ffusion.csil.handlers.Messages.sendBulkMessage(paramString1, paramInt1, paramInt2, paramString2, paramString3, paramString4);
    PerfLog.log(str, l, true);
    debug(str);
    return i;
  }
  
  public static void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.sendGlobalMessage";
    String str2 = UtilHandler.getDefaultLanguage(paramHashMap).toString();
    if ((Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJb)) && (jdMethod_int(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), paramGlobalMessage.getMsgTypeValue())))
    {
      if (paramGlobalMessage.getApprovalEmployeeIDValue() != paramSecureUser.getProfileID())
      {
        DebugLog.log(Level.SEVERE, "Message.CreateGlobalMessage.  Attempted to send a global message where message sender information did not match the authenticated user.");
        throw new CSILException(str1, 104);
      }
      long l = System.currentTimeMillis();
      String str3 = jdMethod_for(paramGlobalMessage);
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
      Iterator localIterator = localLanguageDefns.iterator();
      while (localIterator.hasNext())
      {
        localObject = (LanguageDefn)localIterator.next();
        paramGlobalMessage.setToGroupName(((LanguageDefn)localObject).getLanguage(), str3);
      }
      com.ffusion.csil.handlers.Messages.sendGlobalMessage(paramSecureUser, paramGlobalMessage, null, paramHashMap);
      Object localObject = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramGlobalMessage.getSubject(str2);
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_93", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, (String)localObject, 2710);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_92", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static int approvalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return globalMsgApprovalRequired(paramSecureUser, paramHashMap);
  }
  
  public static int globalMsgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.approvalRequired";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aKa)) {
      return com.ffusion.csil.handlers.Messages.globalMsgApprovalRequired(paramSecureUser, paramHashMap);
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_94", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int msgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Messages.approvalRequired";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aJs)) {
      return com.ffusion.csil.handlers.Messages.msgApprovalRequired(paramSecureUser, paramHashMap);
    }
    Object[] arrayOfObject = new Object[0];
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_95", arrayOfObject);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static int sendEmail(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4)
    throws CSILException
  {
    String str1 = "Messages.sendEmail";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      int i = com.ffusion.csil.handlers.Messages.sendEmail(paramSecureUser, paramString1, paramString2, paramString3, paramString4);
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = paramString1;
      arrayOfObject2[1] = paramString2;
      arrayOfObject2[2] = paramString3;
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_97", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2711);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return i;
    }
    Object[] arrayOfObject1 = new Object[0];
    LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_96", arrayOfObject1);
    logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static String getNotificationSubject()
  {
    return com.ffusion.csil.handlers.Messages.getNotificationSubject();
  }
  
  public static String getNotificationMemo()
  {
    return com.ffusion.csil.handlers.Messages.getNotificationMemo();
  }
  
  public static String getNotificationFromEmail()
  {
    return com.ffusion.csil.handlers.Messages.getNotificationFromEmail();
  }
  
  public static void processGlobalMessages()
    throws CSILException
  {
    String str = "Messages. processGlobalMessages";
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Messages.processGlobalMessages();
    PerfLog.log(str, l, true);
  }
  
  public static EntitledMsgTypes getEntitledGlobalMsgTypesToView(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    EntitledMsgTypes localEntitledMsgTypes = new EntitledMsgTypes();
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int j = 0; j < GlobalMessageConsts.MessageTypes.length; j++)
    {
      int i = GlobalMessageConsts.MessageTypes[j];
      if (jdMethod_for(localEntitlementGroupMember, i)) {
        localEntitledMsgTypes.add(new EntitledMsgType(i, GlobalMessage.getMsgTypeString(i, paramSecureUser.getLocale())));
      }
    }
    return localEntitledMsgTypes;
  }
  
  public static EntitledMsgTypes getEntitledGlobalMsgTypesToCED(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    EntitledMsgTypes localEntitledMsgTypes = new EntitledMsgTypes();
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int j = 0; j < GlobalMessageConsts.MessageTypes.length; j++)
    {
      int i = GlobalMessageConsts.MessageTypes[j];
      if (jdMethod_int(localEntitlementGroupMember, i)) {
        localEntitledMsgTypes.add(new EntitledMsgType(i, GlobalMessage.getMsgTypeString(i, paramSecureUser.getLocale())));
      }
    }
    return localEntitledMsgTypes;
  }
  
  private static boolean jdMethod_int(EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(paramEntitlementGroupMember, aIs[paramInt])) {
      return false;
    }
    if ((aI5[paramInt] == 0) && (aJj[paramInt] != 0)) {
      return Entitlements.checkEntitlement(paramEntitlementGroupMember, aJr);
    }
    if ((aI5[paramInt] != 0) && (aJj[paramInt] == 0)) {
      return Entitlements.checkEntitlement(paramEntitlementGroupMember, aKm);
    }
    return true;
  }
  
  private static boolean jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(paramEntitlementGroupMember, aI8[paramInt])) {
      return false;
    }
    if ((aJ5[paramInt] == 0) && (aJ8[paramInt] != 0)) {
      return Entitlements.checkEntitlement(paramEntitlementGroupMember, aJr);
    }
    if ((aJ5[paramInt] != 0) && (aJ8[paramInt] == 0)) {
      return Entitlements.checkEntitlement(paramEntitlementGroupMember, aKm);
    }
    return true;
  }
  
  public static void modifyMessageThreadStatus(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Messages.ModifyMessageThreadStatus";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aIS))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      com.ffusion.csil.handlers.Messages.modifyMessageThreadStatus(paramSecureUser, paramInt1, paramInt2, paramHashMap);
      PerfLog.log(str1, l, true);
      MessageThread localMessageThread = new MessageThread();
      localMessageThread.setThreadStatus(Integer.toString(paramInt2));
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.toString(paramInt1);
      arrayOfObject2[1] = localMessageThread.getThreadStatusName();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_99", arrayOfObject2);
      audit(paramSecureUser, localLocalizableString2, str2, 2712);
      debug(paramSecureUser, str1);
    }
    else
    {
      Object[] arrayOfObject1 = new Object[0];
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.messages", "AuditMessage_98", arrayOfObject1);
      logEntitlementFault(paramSecureUser, localLocalizableString1, null);
      throw new CSILException(str1, 20001);
    }
  }
  
  private static String jdMethod_for(GlobalMessage paramGlobalMessage)
    throws CSILException
  {
    String str1 = "getGlobalMessageToGroupName";
    Object localObject = null;
    int i = paramGlobalMessage.getToGroupIDValue();
    int j = paramGlobalMessage.getMsgTypeValue();
    int k = paramGlobalMessage.getToGroupTypeValue();
    if (i == 0) {
      switch (k)
      {
      case 2: 
        if (j == 7) {
          localObject = "All Business Administrators";
        } else if (j == 8) {
          localObject = "All Corporate Bill Payments Customers";
        } else if (j == 9) {
          localObject = "All Corporate Transfers Customers";
        } else {
          localObject = "All Business Customers";
        }
        break;
      case 3: 
        if (j == 8) {
          localObject = "All Consumer Bill Payments Customers";
        } else if (j == 9) {
          localObject = "All Consumer Transfers Customers";
        } else {
          localObject = "All Consumer Customers";
        }
        break;
      case 1: 
        localObject = "All Service Packages";
        break;
      case 0: 
        if (j == 3) {
          localObject = "All ACH Customers";
        } else if (j == 4) {
          localObject = "All Wire Customers";
        } else if (j == 8) {
          localObject = "All Bill Payments Customers";
        } else if (j == 9) {
          localObject = "All Transfers Customers";
        } else {
          localObject = "All Online Banking Customers";
        }
        break;
      case 4: 
        localObject = "Specific Customers";
        break;
      }
    } else if (k != 4) {
      try
      {
        EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(i);
        String str2 = localEntitlementGroup.getGroupName();
        if (j == 7) {
          localObject = "Administrators in " + str2;
        } else {
          localObject = str2;
        }
      }
      catch (CSILException localCSILException)
      {
        throw new CSILException(str1, 5101);
      }
    }
    return localObject;
  }
  
  public static BankMessageInfo getEmailSettings(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    BankMessageInfo localBankMessageInfo = null;
    String str = "Messages.getEmailSettings";
    long l = System.currentTimeMillis();
    boolean bool = true;
    try
    {
      localBankMessageInfo = com.ffusion.csil.handlers.Messages.getEmailSettings(paramSecureUser, paramInt, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      bool = false;
      throw localCSILException;
    }
    finally
    {
      PerfLog.log(str, l, bool);
    }
    return localBankMessageInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Messages
 * JD-Core Version:    0.7.0.1
 */