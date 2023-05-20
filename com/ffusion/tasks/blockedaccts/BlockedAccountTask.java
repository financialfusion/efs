package com.ffusion.tasks.blockedaccts;

public abstract interface BlockedAccountTask
{
  public static final int BLOCK_ALL_USERS = 0;
  public static final int ERROR_NO_ROUTING_NUMBER = 100000;
  public static final int ERROR_NO_ACCOUNT_NUMBER = 100001;
  public static final int ERROR_NO_BANK_NAME = 100002;
  public static final int ERROR_USER_DOES_NOT_EXIST = 100003;
  public static final int ERROR_NO_OLD_BLOCKED_ACCOUNT_IN_SESSION = 100004;
  public static final int ERROR_NO_BLOCKED_ACCOUNT_IN_SESSION = 100005;
  public static final int ERROR_NO_BLOCKED_ACCOUNTS_IN_SESSION = 100006;
  public static final int ERROR_INVALID_BLOCKED_ACCOUNT = 100007;
  public static final int ERROR_INVALID_ROUTING_NUMBER = 100008;
  public static final int ERROR_INSUFFICIENT_SEARCH_INFORMATION = 100009;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.blockedaccts.BlockedAccountTask
 * JD-Core Version:    0.7.0.1
 */