package com.ffusion.beans;

public abstract interface FundsTransactionTypes
{
  public static final int FUNDS_TYPE_UNKNOWN = 0;
  public static final int FUNDS_TYPE_TRANSFER = 1;
  public static final int FUNDS_TYPE_REC_TRANSFER = 2;
  public static final int FUNDS_TYPE_BILL_PAYMENT = 3;
  public static final int FUNDS_TYPE_REC_BILL_PAYMENT = 4;
  public static final int FUNDS_TYPE_WIRE_TRANSFER = 5;
  public static final int FUNDS_TYPE_REC_WIRE_TRANSFER = 6;
  public static final int FUNDS_TYPE_ACH_TRANSFER = 7;
  public static final int FUNDS_TYPE_REC_ACH_TRANSFER = 8;
  public static final int FUNDS_TYPE_ACH_BATCH = 9;
  public static final int FUNDS_TYPE_REC_ACH_BATCH = 10;
  public static final int FUNDS_TYPE_ACH_UPLOAD = 11;
  public static final int FUNDS_TYPE_TAX_PAYMENT = 12;
  public static final int FUNDS_TYPE_REC_TAX_PAYMENT = 13;
  public static final int FUNDS_TYPE_WIRE_BATCH = 14;
  public static final int FUNDS_TYPE_CASH_CONCENTRATION = 15;
  public static final int FUNDS_TYPE_CASH_DISBURSEMENT = 16;
  public static final int FUNDS_TYPE_CHILD_SUPPORT_PAYMENT = 17;
  public static final int FUNDS_TYPE_REC_CHILD_SUPPORT_PAYMENT = 18;
  public static final int FUNDS_TYPE_EXTERNAL_TRANSFER_FROM = 19;
  public static final int FUNDS_TYPE_EXTERNAL_TRANSFER_TO = 20;
  public static final int FUNDS_TYPE_PAYMENT_TEMPLATE = 21;
  public static final int FUNDS_TYPE_PAYMENT_BATCH_TEMPLATE = 22;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.FundsTransactionTypes
 * JD-Core Version:    0.7.0.1
 */