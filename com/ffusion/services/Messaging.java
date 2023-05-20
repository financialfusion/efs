package com.ffusion.services;

import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;

public abstract interface Messaging
  extends SignOn
{
  public static final String EMPLOYEE = "EMPLOYEE";
  public static final String CUSTOMER = "CUSTOMER";
  public static final String QUEUE = "QUEUE";
  public static final int ERROR_UNABLE_TO_SEND_MESSAGE = 8500;
  public static final int ERROR_UNABLE_TO_MOD_MESSAGE = 8501;
  public static final int ERROR_UNABLE_TO_GET_MESSAGE = 8502;
  public static final int ERROR_NO_MESSAGE_FOUND = 8503;
  public static final int ERROR_UNABLE_TO_DEL_MESSAGE = 8504;
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract void setUserName(String paramString);
  
  public abstract void setPassword(String paramString);
  
  public abstract int getMessages(Messages paramMessages);
  
  public abstract int getMessages(Object paramObject, Messages paramMessages);
  
  public abstract int sendMessage(Message paramMessage);
  
  public abstract int sendMessage(Message paramMessage, Object paramObject);
  
  public abstract int deleteMessage(Object paramObject);
  
  public abstract int markMessageAsRead(Object paramObject);
  
  public abstract int getNumberOfUnreadMessages(Object paramObject);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging
 * JD-Core Version:    0.7.0.1
 */