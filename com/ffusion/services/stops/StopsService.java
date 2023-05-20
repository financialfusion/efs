package com.ffusion.services.stops;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.messages.Messages;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.MessageAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Stops4;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

public class StopsService
  implements Stops4
{
  private String aT;
  private String aS;
  private static final String aU = "MM/dd/yyyy";
  private MessageAdapter aR = new MessageAdapter();
  
  public int initialize(String paramString)
  {
    return 0;
  }
  
  public void setSettings(String paramString) {}
  
  protected void processOFXRequest() {}
  
  public String getSettings()
  {
    return null;
  }
  
  public void setPassword(String paramString)
  {
    this.aT = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.aS = paramString;
  }
  
  public int addStopPayment(StopCheck paramStopCheck)
  {
    int i = 0;
    try
    {
      addStopPayment(null, paramStopCheck, null);
    }
    catch (CSILException localCSILException)
    {
      i = localCSILException.code;
    }
    return i;
  }
  
  public int deleteStopPayment(StopCheck paramStopCheck)
  {
    int i = 0;
    try
    {
      deleteStopPayment(null, paramStopCheck, null);
    }
    catch (CSILException localCSILException)
    {
      i = localCSILException.code;
    }
    return i;
  }
  
  public int getStopPayments(StopChecks paramStopChecks)
  {
    return getStopPayments(paramStopChecks, null, null, null);
  }
  
  public int getStopPayments(StopChecks paramStopChecks, com.ffusion.beans.accounts.Accounts paramAccounts)
  {
    return getStopPayments(paramStopChecks, paramAccounts, null, null);
  }
  
  public int getStopPayments(StopChecks paramStopChecks, com.ffusion.beans.accounts.Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    int i = 0;
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("StartDate", paramCalendar1);
    localHashMap2.put("EndDate", paramCalendar2);
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject;
    if (paramAccounts != null) {
      for (int j = 0; j < paramAccounts.size(); j++)
      {
        localObject = (Account)paramAccounts.get(j);
        localStringBuffer.append(((Account)localObject).getConsumerDisplayText());
        localStringBuffer.append("\n");
      }
    }
    localHashMap2.put("AccountId", localStringBuffer.toString());
    localHashMap1.put("SEARCH_CRITERIA", localHashMap2);
    localPagingContext.setMap(localHashMap1);
    try
    {
      SecureUser localSecureUser = a((StopCheck)paramStopChecks.get(0));
      localObject = jdMethod_if((StopCheck)paramStopChecks.get(0));
      paramStopChecks = getStopPayments(localSecureUser, localPagingContext, (HashMap)localObject);
    }
    catch (CSILException localCSILException)
    {
      localCSILException.printStackTrace();
      return localCSILException.code;
    }
    return i;
  }
  
  public StopCheck modStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StopService.modStopPayment";
    try
    {
      int i = Integer.parseInt(paramStopCheck.getID());
      MessageThread localMessageThread = this.aR.getMessageThread(paramSecureUser, i);
      int j = Integer.parseInt(localMessageThread.getThreadStatus());
      if (j == 5) {
        throw new CSILException(str, 80504);
      }
      Message localMessage = new Message();
      localMessage.setMsgThreadID(paramStopCheck.getTrackingID());
      localMessage.put("MESSAGE_ID", paramStopCheck.getTrackingID());
      localMessage.setType(1);
      localMessage.setTo(localMessageThread.getEmployeeID());
      localMessage.setToType(String.valueOf(0));
      localMessage.setFrom(paramSecureUser.getProfileID());
      localMessage.setFromType(String.valueOf(1));
      localMessage.setCaseNum(localMessageThread.getCaseNum());
      localMessage.setMsgThreadStatus(localMessageThread.getThreadStatus());
      localMessage.setSubject(ResourceUtil.getString("StopPaymentModify", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()));
      paramStopCheck.setLocale(paramSecureUser.getLocale());
      localMessage.setComment(paramStopCheck.getInquireComment(localMessage.getSubject()));
      localMessage.setMemo(ResourceUtil.getString("StopPaymentModifyComment", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()));
      this.aR.sendMessage(paramSecureUser, localMessage);
    }
    catch (ProfileException localProfileException)
    {
      throw new CSILException(str, localProfileException.code, localProfileException);
    }
    return paramStopCheck;
  }
  
  public StopCheck addStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StopsService.addStopPayment";
    if (paramSecureUser == null) {
      paramSecureUser = a(paramStopCheck);
    }
    if (paramHashMap == null) {
      paramHashMap = jdMethod_if(paramStopCheck);
    }
    Message localMessage = a(paramSecureUser, paramStopCheck, ResourceUtil.getString("StopPaymentRequest", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()));
    try
    {
      this.aR.sendMessage(paramSecureUser, localMessage);
      paramStopCheck.setTrackingID(localMessage.getMsgThreadID());
    }
    catch (ProfileException localProfileException)
    {
      throw new CSILException(str, localProfileException.code, localProfileException);
    }
    return paramStopCheck;
  }
  
  public StopCheck deleteStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StopsService.deleteStopPayment";
    if (paramSecureUser == null) {
      paramSecureUser = a(paramStopCheck);
    }
    if (paramHashMap == null) {
      paramHashMap = jdMethod_if(paramStopCheck);
    }
    try
    {
      int i = Integer.parseInt(paramStopCheck.getTrackingID());
      MessageThread localMessageThread = this.aR.getMessageThread(paramSecureUser, i);
      int j = Integer.parseInt(localMessageThread.getThreadStatus());
      if (j != 1)
      {
        localObject = this.aR.getMessagesByThread(paramSecureUser, paramHashMap, localMessageThread);
        if (localObject != null) {
          for (int k = 0; k < ((Messages)localObject).size(); k++)
          {
            Message localMessage = (Message)((Messages)localObject).get(k);
            if (localMessage.getFromTypeValue() == 0) {
              throw new CSILException(str, 80502);
            }
          }
        }
      }
      Object localObject = new Message();
      ((Message)localObject).setMsgThreadID(paramStopCheck.getTrackingID());
      ((Message)localObject).put("MESSAGE_ID", paramStopCheck.getTrackingID());
      ((Message)localObject).setType(2);
      ((Message)localObject).setTo(localMessageThread.getEmployeeID());
      ((Message)localObject).setToType(String.valueOf(0));
      ((Message)localObject).setFrom(paramSecureUser.getProfileID());
      ((Message)localObject).setFromType(String.valueOf(1));
      ((Message)localObject).setCaseNum(localMessageThread.getCaseNum());
      ((Message)localObject).setMsgThreadStatus(localMessageThread.getThreadStatus());
      ((Message)localObject).setSubject(ResourceUtil.getString("StopPaymentCancel", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()));
      paramStopCheck.setLocale(paramSecureUser.getLocale());
      ((Message)localObject).setComment(paramStopCheck.getInquireComment(((Message)localObject).getSubject()));
      ((Message)localObject).setMemo(ResourceUtil.getString("StopPaymentCancelComment", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()));
      this.aR.sendMessageThread(paramSecureUser, (Message)localObject);
      try
      {
        MessageAdapter.modifyMessageThreadStatus(Integer.parseInt(paramStopCheck.getTrackingID()), 5, new HashMap());
      }
      catch (Exception localException)
      {
        throw new CSILException(str, 80503, localException);
      }
    }
    catch (ProfileException localProfileException)
    {
      throw new CSILException(-1009, localProfileException.code, localProfileException);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    return paramStopCheck;
  }
  
  public StopChecks getStopPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "StopService.getStopPayments";
    if (paramPagingContext == null) {
      throw new CSILException(str1, 80500);
    }
    StopChecks localStopChecks = new StopChecks(paramSecureUser.getLocale());
    HashMap localHashMap1 = paramPagingContext.getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = (String)localHashMap2.get("AccountId");
    String str3 = (String)localHashMap2.get("PayeeName");
    if (str3 != null) {
      str3 = str3.trim().toUpperCase();
    }
    String str4 = (String)localHashMap2.get("Amount");
    String str5 = (String)localHashMap2.get("StopPaymentStatus");
    ArrayList localArrayList = new ArrayList();
    if (str2 != null)
    {
      localObject1 = new StringTokenizer(str2, "\n");
      while (((StringTokenizer)localObject1).hasMoreElements()) {
        localArrayList.add(((StringTokenizer)localObject1).nextToken());
      }
    }
    Object localObject1 = a(paramSecureUser, localHashMap2, paramHashMap);
    if (localObject1 != null) {
      for (int i = 0; i < ((MessageThreads)localObject1).size(); i++)
      {
        MessageThread localMessageThread = (MessageThread)((MessageThreads)localObject1).get(i);
        Messages localMessages = null;
        try
        {
          localMessages = this.aR.getMessagesByThread(paramSecureUser, paramHashMap, localMessageThread);
        }
        catch (ProfileException localProfileException)
        {
          throw new CSILException(str1, localProfileException.code, localProfileException);
        }
        if (localMessages.size() != 0)
        {
          Object localObject2 = (Message)localMessages.get(localMessages.size() - 1);
          if (localMessageThread.getThreadStatus().compareTo("5") == 0)
          {
            Message localMessage1 = (Message)localMessages.get(0);
            if ((localMessage1 != null) && (localMessage1.getComment() != null) && (localMessage1.getSubject().equals(ResourceUtil.getString("StopPaymentCancel", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()))) && (localMessage1.getFromTypeValue() == 1)) {}
          }
          else
          {
            for (int j = localMessages.size() - 2; j >= 0; j--)
            {
              Message localMessage2 = (Message)localMessages.get(j);
              if ((localMessage2.getComment() != null) && (localMessage2.getComment().indexOf(ResourceUtil.getString("StopPaymentModify", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale())) == 0)) {
                localObject2 = localMessage2;
              }
            }
            StopCheck localStopCheck = a(paramSecureUser, (Message)localObject2, localArrayList, str3, str4, localMessageThread);
            if (localStopCheck != null)
            {
              localStopCheck.setTrackingID(localMessageThread.getThreadID());
              localStopChecks.add(localStopCheck);
            }
          }
        }
      }
    }
    return localStopChecks;
  }
  
  private StopCheck a(SecureUser paramSecureUser, Message paramMessage, ArrayList paramArrayList, String paramString1, String paramString2, MessageThread paramMessageThread)
    throws CSILException
  {
    String str1 = "StopsService.parseMessageComment";
    try
    {
      StopCheck localStopCheck = new StopCheck(paramSecureUser.getLocale());
      localStopCheck.setStatus(paramMessageThread.getThreadStatus());
      String str2 = paramMessage.getComment();
      if (str2 == null) {
        return null;
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(str2, "\n");
      String str3 = localStringTokenizer.nextToken();
      String str4 = localStringTokenizer.nextToken();
      str4 = str4.substring(str4.indexOf("=") + 1);
      if ((paramArrayList != null) && (paramArrayList.size() > 0) && (!paramArrayList.contains(str4))) {
        return null;
      }
      String str5 = localStringTokenizer.nextToken();
      str5 = str5.substring(str5.indexOf("=") + 1);
      String str6 = localStringTokenizer.nextToken();
      str6 = str6.substring(str6.indexOf("=") + 1);
      String str7 = localStringTokenizer.nextToken();
      str7 = str7.substring(str7.indexOf("=") + 1);
      DateTime localDateTime1 = null;
      if (str7 != null)
      {
        localObject1 = DateFormatUtil.getFormatter(paramSecureUser.getDateFormat(), paramSecureUser.getLocale());
        try
        {
          DateTime localDateTime2 = new DateTime(str7, paramSecureUser.getLocale(), "MM/dd/yyyy");
          str7 = ((DateFormat)localObject1).format(localDateTime2.getTime());
        }
        catch (Exception localException2) {}
        localDateTime1 = new DateTime(str7, paramSecureUser.getLocale(), paramSecureUser.getDateFormat());
      }
      Object localObject1 = localStringTokenizer.nextToken();
      localObject1 = ((String)localObject1).substring(((String)localObject1).indexOf("=") + 1);
      if ((paramString2 != null) && (paramString2.trim().length() > 0))
      {
        localObject2 = new Currency(paramString2.trim(), paramSecureUser.getLocale());
        localObject3 = new Currency((String)localObject1, paramSecureUser.getLocale());
        if (((Currency)localObject2).compareTo((Currency)localObject3) != 0) {
          return null;
        }
      }
      Object localObject2 = localStringTokenizer.nextToken();
      localObject2 = ((String)localObject2).substring(((String)localObject2).indexOf("=") + 1);
      if ((paramString1 != null) && (((String)localObject2).trim().length() > 0))
      {
        localObject3 = ((String)localObject2).trim().toUpperCase();
        if (((String)localObject3).indexOf(paramString1) < 0) {
          return null;
        }
      }
      Object localObject3 = localStringTokenizer.nextToken();
      localObject3 = ((String)localObject3).substring(((String)localObject3).indexOf("=") + 1);
      localStopCheck.setAccountID(str4);
      localStopCheck.setAccountDisplayText(str5);
      localStopCheck.setCheckNumbers(str6);
      localStopCheck.setCheckDate(localDateTime1);
      localStopCheck.setCreateDate(paramMessageThread.getCreateDateValue());
      com.ffusion.beans.accounts.Accounts localAccounts;
      if (paramSecureUser.getBusinessID() == 0) {
        localAccounts = com.ffusion.csil.core.Accounts.getAccountsById(paramSecureUser, paramSecureUser.getProfileID(), new HashMap());
      } else {
        localAccounts = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(paramSecureUser, new HashMap());
      }
      localStopCheck.setCurrencyCode(localAccounts.getByID(str4).getCurrencyCode());
      localStopCheck.setAmount((String)localObject1);
      localStopCheck.setPayeeName((String)localObject2);
      localStopCheck.setReason((String)localObject3);
      localStopCheck.setIDStr(paramMessageThread.getCaseNum());
      localStopCheck.setTrackingID(paramMessageThread.getThreadID());
      return localStopCheck;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw new CSILException(str1, 80501, localException1);
    }
  }
  
  private Message a(SecureUser paramSecureUser, StopCheck paramStopCheck, String paramString)
  {
    Locale localLocale = paramSecureUser.getLocale();
    Message localMessage = new Message();
    localMessage.setType(1);
    localMessage.setTo("22");
    localMessage.setToType("QUEUE");
    localMessage.setFrom(paramSecureUser.getProfileID());
    localMessage.setFromType(String.valueOf(1));
    localMessage.setSubject(paramString);
    paramStopCheck.setLocale(paramSecureUser.getLocale());
    localMessage.setComment(paramStopCheck.getInquireComment(paramString));
    localMessage.setMsgThreadID(paramStopCheck.getTrackingID());
    localMessage.setMemo(ResourceUtil.getString("StopPaymentSendComment", "com.ffusion.beans.stoppayments.resources", paramSecureUser.getLocale()));
    return localMessage;
  }
  
  private MessageThreads a(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str1 = "StopsService.findMessageThreads";
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    Calendar localCalendar1 = (Calendar)paramHashMap1.get("StartDate");
    if (localCalendar1 != null) {
      localDateTime1 = new DateTime(localCalendar1, paramSecureUser.getLocale());
    }
    Calendar localCalendar2 = (Calendar)paramHashMap1.get("EndDate");
    if (localCalendar2 != null) {
      localDateTime2 = new DateTime(localCalendar2, paramSecureUser.getLocale());
    }
    MessageThread localMessageThread = new MessageThread();
    localMessageThread.setQueueID("22");
    String str2 = (String)paramHashMap1.get("StopPaymentStatus");
    if (str2 != null)
    {
      int i = Integer.parseInt(str2);
      if (i == 1) {
        localMessageThread.setThreadStatus(String.valueOf(5));
      }
    }
    User localUser = null;
    if ((paramSecureUser.getUserName() != null) && (paramSecureUser.getUserName().trim().length() > 0))
    {
      localUser = new User();
      localUser.setUserName(paramSecureUser.getUserName().trim());
    }
    Business localBusiness = null;
    if ((paramSecureUser.getBusinessName() != null) && (paramSecureUser.getBusinessName().trim().length() > 0))
    {
      localBusiness = new Business();
      localBusiness.setBusinessName(paramSecureUser.getBusinessName().trim());
    }
    MessageThreads localMessageThreads = null;
    try
    {
      localMessageThreads = this.aR.findMessageThreads(paramSecureUser, paramHashMap2, localUser, localBusiness, localMessageThread, localDateTime1, localDateTime2);
    }
    catch (ProfileException localProfileException)
    {
      localProfileException.printStackTrace();
      throw new CSILException(str1, localProfileException.code, localProfileException);
    }
    return localMessageThreads;
  }
  
  private SecureUser a(StopCheck paramStopCheck)
    throws CSILException
  {
    String str = "StopsService.getSecureUserFromStopCheck";
    HashMap localHashMap = paramStopCheck.getHash();
    SecureUser localSecureUser = (SecureUser)localHashMap.get("SecureUser");
    if (localSecureUser == null) {
      throw new CSILException(str, 34101);
    }
    return localSecureUser;
  }
  
  private HashMap jdMethod_if(StopCheck paramStopCheck)
  {
    HashMap localHashMap1 = paramStopCheck.getHash();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("ExtraFields");
    if (localHashMap2 == null) {
      localHashMap2 = new HashMap();
    }
    return localHashMap2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.stops.StopsService
 * JD-Core Version:    0.7.0.1
 */