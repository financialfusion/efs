package com.ffusion.beans.cashcon;

public abstract interface CashConStatus
{
  public static final int CASH_CON_STATUS_UNKNOWN = 0;
  public static final int CASH_CON_STATUS_PENDING_APPROVAL = 1;
  public static final int CASH_CON_STATUS_APPROVED = 2;
  public static final int CASH_CON_STATUS_APPROVAL_HOLD = 3;
  public static final int CASH_CON_STATUS_APPROVAL_FAILED = 4;
  public static final int CASH_CON_STATUS_PENDING = 5;
  public static final int CASH_CON_STATUS_PROCESSED = 6;
  public static final int CASH_CON_STATUS_DELETED = 7;
  public static final int CASH_CON_STATUS_LIMITCHECK_FAILED = 8;
  public static final int CASH_CON_STATUS_LIMITREVERT_FAILED = 9;
  public static final int CASH_CON_STATUS_FAILED = 10;
  public static final int CASH_CON_STATUS_APPROVAL_REJECTED = 11;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConStatus
 * JD-Core Version:    0.7.0.1
 */