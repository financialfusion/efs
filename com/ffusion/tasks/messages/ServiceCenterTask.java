package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.LocalizableString;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ServiceCenterTask
{
  public static final String SERVICE_CENTER_RESOURCE_BUNDLE = "com.ffusion.tasks.service_center";
  public static final String DELIVERY_METHOD_PREFIX = "DeliveryMethod";
  public static final String SUBJECT = "ServiceCenterSubject";
  public static final String NO_REFERENCE_NUM = "NoReferenceNum";
  public static final int ERROR_NO_ACCOUNT_NUMBER = 100001;
  public static final int ERROR_NO_ROUTING_NUMBER = 100000;
  public static final int ERROR_INVALID_ROUTING_NUMBER = 100008;
  public static final int ERROR_NO_AMOUNT = 100100;
  public static final int ERROR_INVALID_AMOUNT = 100101;
  public static final int ERROR_NO_DATE = 100102;
  public static final int ERROR_INVALID_DATE = 100103;
  public static final int ERROR_NO_ACCOUNT_SPECIFIED = 100104;
  public static final int ERROR_NO_COMMENT_QUESTION = 100105;
  public static final int ERROR_NO_BEGIN_DATE = 100106;
  public static final int ERROR_NO_END_DATE = 100107;
  public static final int ERROR_INVALID_BEGIN_DATE = 100108;
  public static final int ERROR_INVALID_END_DATE = 100109;
  public static final int ERROR_NO_FAX_NUMBER = 100110;
  public static final int ERROR_INVALID_FAX_NUMBER = 100111;
  public static final int ERROR_NO_DELIVERY_METHOD = 100112;
  public static final int ERROR_NO_CONTACT_NUMBER = 100113;
  public static final int ERROR_INVALID_CONTACT_NUMBER = 100114;
  public static final int ERROR_NO_TRANSACTION_TYPE = 100115;
  public static final int ERROR_NO_TRANS_DESCRIPTION = 100116;
  public static final int ERROR_NO_CHECK_NUM_SPECIFIED = 100117;
  public static final int ERROR_NO_ACCOUNT = 100118;
  public static final int ERROR_END_DATE_BEFORE_BEGIN_DATE = 100119;
  public static final int ERROR_PARTIAL_ACCOUNT_SPECIFIED = 100120;
  public static final int ERROR_NO_PAYEE = 100121;
  public static final int ERROR_NO_TO_ACCOUNT_SPECIFIED = 100122;
  public static final int ERROR_NO_FROM_ACCOUNT_SPECIFIED = 100123;
  public static final int ERROR_DATE_OCCURS_IN_FUTURE = 100124;
  public static final int ERROR_BEGIN_DATE_OCCURS_IN_FUTURE = 100125;
  public static final int ERROR_END_DATE_OCCURS_IN_FUTURE = 100126;
  public static final int ERROR_COMMENT_FIELD_TOO_LONG = 100127;
  public static final int ERROR_ACCOUNT_EXISTS = 100128;
  public static final String QUESTION = "QUESTION";
  public static final String PHONE = "PHONE";
  public static final String TRANS_DESCRIPTION = "TRANS_DESCRIPTION";
  public static final int NO_DELIVERY = 0;
  public static final int ADDRESS_OF_RECORD = 1;
  public static final int FAX = 2;
  
  private static MessageQueue a(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    MessageQueue localMessageQueue1 = null;
    MessageQueues localMessageQueues = null;
    MessageQueue localMessageQueue2 = new MessageQueue();
    localMessageQueue2.setQueueID(paramString);
    localMessageQueue2.setQueueType("0");
    localMessageQueue2.setSearchLanguage(paramSecureUser.getLocaleLanguage());
    localMessageQueues = MessageAdmin.getMessageQueues(paramSecureUser, localMessageQueue2, paramHashMap);
    if ((localMessageQueues != null) && (localMessageQueues.size() == 1)) {
      localMessageQueue1 = (MessageQueue)localMessageQueues.get(0);
    }
    return localMessageQueue1;
  }
  
  public static void setupServiceCenterMessage(SecureUser paramSecureUser, Message paramMessage, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    MessageQueue localMessageQueue = a(paramSecureUser, paramString, paramHashMap);
    Object[] arrayOfObject = null;
    paramMessage.setFrom(paramSecureUser.getProfileID());
    paramMessage.setFromType("CUSTOMER");
    paramMessage.setTo(paramString);
    paramMessage.setToType("QUEUE");
    if (localMessageQueue != null)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = localMessageQueue.getQueueName();
      localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "ServiceCenterSubject", arrayOfObject);
      paramMessage.setSubject((String)localLocalizableString.localize(paramSecureUser.getLocale()));
    }
  }
  
  public static boolean checkSearchDate(DateTime paramDateTime, Locale paramLocale)
  {
    DateTime localDateTime = new DateTime(Calendar.getInstance(), paramLocale);
    return !localDateTime.before(paramDateTime);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ServiceCenterTask
 * JD-Core Version:    0.7.0.1
 */