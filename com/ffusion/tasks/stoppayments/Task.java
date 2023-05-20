package com.ffusion.tasks.stoppayments;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String STOPS = "com.ffusion.services.Stops";
  public static final String STOPCHECK = "StopCheck";
  public static final String STOPCHECKS = "StopChecks";
  public static final int SUCCESS = 0;
  public static final int ERROR_GENERAL = 13001;
  public static final int ERROR_ACCT_ERROR = 13002;
  public static final int ERROR_ACCT_NOT_FOUND = 13003;
  public static final int ERROR_ACCT_CLOSED = 13004;
  public static final int ERROR_ACCT_UNAUTHORIZED = 13005;
  public static final int ERROR_DUP_REQUEST = 13006;
  public static final int ERROR_OUT_OF_DATE_TOKEN = 13007;
  public static final int ERROR_IN_PROCESS = 13008;
  public static final int ERROR_TOO_MANY_CHECKS = 13009;
  public static final int ERROR_NO_SERVICE = 13010;
  public static final int ERROR_NO_ACCOUNTS = 13011;
  public static final int ERROR_INVALID_ACCOUNT = 13012;
  public static final int ERROR_AMOUNT = 13013;
  public static final int ERROR_CHECKDATE = 13014;
  public static final int ERROR_CHECKNUMBERS = 13015;
  public static final int ERROR_PAYEENAME = 13016;
  public static final int ERROR_CHECKRANGESTARTDATE = 13017;
  public static final int ERROR_CHECKRANGEENDDATE = 13018;
  public static final int ERROR_ALREADY_PROCESSING = 13019;
  public static final int ERROR_NO_STOPCHECKS = 13020;
  public static final int ERROR_NO_STOPCHECK = 13021;
  public static final int ERROR_NO_STOPCHECKID = 13022;
  public static final int ERROR_INVALID_START_DATE = 13023;
  public static final int ERROR_NO_REQUIRED_DATE = 13024;
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 13400;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 13401;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.Task
 * JD-Core Version:    0.7.0.1
 */