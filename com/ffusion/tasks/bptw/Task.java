package com.ffusion.tasks.bptw;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String BPTW_FLAG_SESSION = "BptwFlag";
  public static final String BPTW_BANKING_SESSION = "BptwBanking";
  public static final String BPTW_BILLPAY_SESSION = "BptwBillPay";
  public static final String BPTW_SERVICE_SESSION = "BptwService";
  public static final String BPTW_LOG_SESSION = "BptwLog";
  public static final String BPTW_LOGS = "BptwLogs";
  public static final String USER_SESSION = "User";
  public static final String TRANSFER_FROM_FILTER = "TransferFrom";
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 17000;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 17001;
  public static final int ERROR_LOG_SERVICE_NOT_IN_SESSION = 17002;
  public static final int ERROR_LOG_REQUEST_FAILED = 17003;
  public static final int ERROR_BILLPAY_SERVICE_NOT_IN_SESSION = 17004;
  public static final int ERROR_BANKING_SERVICE_NOT_IN_SESSION = 17005;
  public static final int ERROR_USER_NOT_IN_SESSION = 17006;
  public static final int ERROR_BPTW_SERVICE_NOT_IN_SESSION = 17007;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.Task
 * JD-Core Version:    0.7.0.1
 */