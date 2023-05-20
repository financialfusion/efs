package com.ffusion.beans.banking;

public abstract interface TransferStatus
{
  public static final int TRS_UNKNOWN = 0;
  public static final int TRS_CREATED = 1;
  public static final int TRS_SCHEDULED = 2;
  public static final int TRS_CANCELED = 3;
  public static final int TRS_CLEARED = 4;
  public static final int TRS_TRANSFERED = 5;
  public static final int TRS_FAILED_TO_TRANSFER = 6;
  public static final int TRS_NOT_EXCEPTED = 7;
  public static final int TRS_PENDING_APPROVAL = 8;
  public static final int TRS_PENDING_HOLD = 9;
  public static final int TRS_INSUFFICIENT_FUNDS = 10;
  public static final int TRS_BATCH_INPROCESS = 11;
  public static final int TRS_FAILED_APPROVAL = 12;
  public static final int TRS_BPW_LIMITCHECK_FAILED = 13;
  public static final int TRS_BPW_LIMITREVERT_FAILED = 14;
  public static final int TRS_BPW_APPROVAL_FAILED = 15;
  public static final int TRS_TEMPLATE = 16;
  public static final int TRS_RECTEMPLATE = 17;
  public static final int TRS_PROCESSED = 18;
  public static final int TRS_IMMED_INPROCESS = 19;
  public static final int TRS_INPROCESS = 20;
  public static final int TRS_FUNDSPROCESSED = 21;
  public static final int STATUS_ERROR = 999;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransferStatus
 * JD-Core Version:    0.7.0.1
 */