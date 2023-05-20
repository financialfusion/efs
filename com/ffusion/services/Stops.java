package com.ffusion.services;

import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;

public abstract interface Stops
{
  public static final int ERROR_NONE = 0;
  public static final int ERROR_INVALID_SIGNON = 13100;
  public static final int ERROR_NO_DATABASE_CONNECTION = 13101;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 13102;
  public static final int ERROR_INVALID_REQUEST = 13103;
  public static final int ERROR_REMOTE_EXCEPTION = 13104;
  public static final int ERROR_EJB_NOT_FOUND = 13105;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 13106;
  public static final int ERROR_INVALID_INIT_FILE = 13107;
  
  public abstract int initialize(String paramString);
  
  public abstract void setSettings(String paramString);
  
  public abstract String getSettings();
  
  public abstract void setUserName(String paramString);
  
  public abstract void setPassword(String paramString);
  
  public abstract int addStopPayment(StopCheck paramStopCheck);
  
  public abstract int deleteStopPayment(StopCheck paramStopCheck);
  
  public abstract int getStopPayments(StopChecks paramStopChecks);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Stops
 * JD-Core Version:    0.7.0.1
 */