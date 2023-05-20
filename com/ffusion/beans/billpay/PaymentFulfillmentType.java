package com.ffusion.beans.billpay;

public abstract interface PaymentFulfillmentType
{
  public static final int TYPE_UNKOWN = 0;
  public static final int TYPE_CHECK = 1;
  public static final int TYPE_ELECTRONIC = 2;
  public static final String TYPE_CHECK_TXT = "check";
  public static final String TYPE_ELECTRONIC_TXT = "electronic";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentFulfillmentType
 * JD-Core Version:    0.7.0.1
 */