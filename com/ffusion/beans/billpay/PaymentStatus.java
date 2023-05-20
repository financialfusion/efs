package com.ffusion.beans.billpay;

public abstract interface PaymentStatus
{
  public static final int PMS_UNKNOWN = 0;
  public static final int PMS_CREATED = 1;
  public static final int PMS_SCHEDULED = 2;
  public static final int PMS_CANCELED = 3;
  public static final int PMS_CLEARED = 4;
  public static final int PMS_PAID = 5;
  public static final int PMS_FAILED_TO_PAY = 6;
  public static final int PMS_NOT_EXCEPTED = 7;
  public static final int PMS_DATE_TOO_SOON = 8;
  public static final int PMS_PENDING_APPROVAL = 9;
  public static final int PMS_PENDING_HOLD = 10;
  public static final int PMS_INSUFFICIENT_FUNDS = 11;
  public static final int PMS_FUNDS_ALLOCATED = 12;
  public static final int PMS_BATCH_INPROCESS = 13;
  public static final int PMS_FUNDS_FAILEDON = 14;
  public static final int PMS_FAILED_APPROVAL = 15;
  public static final int PMS_BPW_LIMITCHECK_FAILED = 16;
  public static final int PMS_BPW_LIMITREVERT_FAILED = 17;
  public static final int PMS_BPW_APPROVAL_FAILED = 18;
  public static final int PMS_POSTED = 19;
  public static final int PMS_ACTIVE = 20;
  public static final int PMS_BPW_APPROVAL_NOT_ALLOWED = 21;
  public static final int PMS_BPW_FUNDSREVERTED = 22;
  public static final int PMS_BPW_FUNDSREVERTED_NOTIF = 23;
  public static final int STATUS_ERROR = 999;
  public static final String PMT_AUDIT_STATE_APPROVAL_PENDING = "APPROVAL_PENDING";
  public static final String PMT_AUDIT_STATE_APPROVAL_REJECTED = "APPROVAL_REJECTED";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentStatus
 * JD-Core Version:    0.7.0.1
 */