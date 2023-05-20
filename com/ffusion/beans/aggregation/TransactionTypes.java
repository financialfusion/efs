package com.ffusion.beans.aggregation;

abstract interface TransactionTypes
{
  public static final int TYPE_UNKNOWN = 0;
  public static final int TYPE_DEPOSIT = 1;
  public static final int TYPE_WITHDRAWAL = 2;
  public static final int TYPE_CHECK = 3;
  public static final int TYPE_CREDIT = 4;
  public static final int TYPE_DEBIT = 5;
  public static final int TYPE_ATM_CREDIT = 6;
  public static final int TYPE_ATM_DEBIT = 7;
  public static final int TYPE_POS_CREDIT = 8;
  public static final int TYPE_POSDEBIT = 9;
  public static final int TYPE_FEE = 10;
  public static final int TYPE_INTEREST = 11;
  public static final int TYPE_DIRECT_DEBIT = 12;
  public static final int TYPE_SO_RECURRINGPAYMENT = 13;
  public static final int TYPE_UNPAID_ITEM = 14;
  public static final int TYPE_ADVICE = 15;
  public static final int TYPE_TRANSFER = 16;
  public static final int TYPE_SUP_LIST = 17;
  public static final int TYPE_DIVIDEND = 18;
  public static final int TYPE_BANK_GIRO_CREDIT = 19;
  public static final int TYPE_REMITTANCE = 20;
  public static final int TYPE_REVERSAL = 21;
  public static final int TYPE_FINANCE_CHARGE = 22;
  public static final int TYPE_SERVICE_CHARGE = 23;
  public static final int TYPE_INTEREST_PAID = 24;
  public static final int TYPE_BILL_PAYMENT = 25;
  public static final int TYPE_BUY = 26;
  public static final int TYPE_SELL = 27;
  public static final int TYPE_CASH = 28;
  public static final int TYPE_DIRECT_DEPOSIT = 29;
  public static final int TYPE_OTHER = 30;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.TransactionTypes
 * JD-Core Version:    0.7.0.1
 */