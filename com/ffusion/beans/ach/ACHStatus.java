package com.ffusion.beans.ach;

public abstract interface ACHStatus
{
  public static final int ACH_UNKNOWN = -1;
  public static final int ACH_CREATED = 1;
  public static final int ACH_SCHEDULED = 2;
  public static final int ACH_CANCELED = 3;
  public static final int ACH_CLEARED = 4;
  public static final int ACH_TRANSFERED = 5;
  public static final int ACH_FAILED_TO_TRANSFER = 6;
  public static final int ACH_NOT_EXPCEPTED = 7;
  public static final int ACH_APPROVAL_PENDING = 8;
  public static final int ACH_APPROVAL_HOLD = 9;
  public static final int ACH_APPROVAL_FAILED = 10;
  public static final int ACH_RELEASE_PENDING = 11;
  public static final int ACH_RELEASED = 12;
  public static final int ACH_RELEASE_REJECTED = 13;
  public static final int ACH_RELEASE_FAILED = 14;
  public static final int ACH_RELEASE_HELD = 15;
  public static final int ACH_EXCEEDED_LIMITS = 16;
  public static final int ACH_BPW_LIMITCHECK_FAILED = 17;
  public static final int ACH_BPW_LIMITREVERT_FAILED = 18;
  public static final int ACH_BPW_APPROVAL_FAILED = 19;
  public static final int ACH_BPW_APPROVAL_NOT_ALLOWED = 20;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHStatus
 * JD-Core Version:    0.7.0.1
 */