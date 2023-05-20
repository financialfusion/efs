package com.ffusion.beans.billpay;

abstract interface PayeeStatus
{
  public static final int PS_CREATED = 1;
  public static final int PS_ACTIVE = 2;
  public static final int PS_DELETED = 3;
  public static final int STATUS_ERROR = 999;
  public static final int PS_MIN = 1;
  public static final int PS_MAX = 3;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PayeeStatus
 * JD-Core Version:    0.7.0.1
 */