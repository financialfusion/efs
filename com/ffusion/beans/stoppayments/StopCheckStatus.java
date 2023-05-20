package com.ffusion.beans.stoppayments;

abstract interface StopCheckStatus
{
  public static final int SCS_START_STATUS_VALUES = 13000;
  public static final int SCS_SUCCESS = 13000;
  public static final int SCS_ERROR = 13001;
  public static final int SCS_ACCT_ERROR = 13002;
  public static final int SCS_ACCT_NOT_FOUND = 13003;
  public static final int SCS_ACCT_CLOSED = 13004;
  public static final int SCS_ACCT_UNAUTHORIZED = 13005;
  public static final int SCS_DUP_REQUEST = 13006;
  public static final int SCS_OUT_OF_DATE_TOKEN = 13007;
  public static final int SCS_STATUS_IN_PROCESS = 13008;
  public static final int SCS_STATUS_TOO_MANY_CHECKS = 13009;
  public static final int SCS_CANCEL_IN_PROGRESS = 13010;
  public static final int SCS_END_STATUS_VALUES = 13010;
  public static final int SCS_PENDING = 0;
  public static final int SCS_CLOSED = 1;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.stoppayments.StopCheckStatus
 * JD-Core Version:    0.7.0.1
 */