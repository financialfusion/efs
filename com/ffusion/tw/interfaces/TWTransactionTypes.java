package com.ffusion.tw.interfaces;

public abstract interface TWTransactionTypes
{
  public static final int TYPE_UNKNOWN = 0;
  public static final int TYPE_ACCOUNT_TRANSFER = 1;
  public static final int TYPE_RECURRING_ACCOUNT_TRANSFER = 2;
  public static final int TYPE_BILL_PAYMENT = 3;
  public static final int TYPE_RECURRING_BILL_PAYMENT = 4;
  public static final int TYPE_WIRE_TRANSFER = 5;
  public static final int TYPE_RECURRING_WIRE_TRANSFER = 6;
  public static final int TYPE_ACH_BATCH = 7;
  public static final int TYPE_RECURRING_ACH_BATCH = 8;
  public static final int TYPE_TAX_PAYMENT = 9;
  public static final int TYPE_WIRE_BATCH = 10;
  public static final int TYPE_CASHCON = 11;
  public static final int TYPE_REC_TAX_PAYMENT = 12;
  public static final int TYPE_CHILD_SUPPORT_PAYMENT = 13;
  public static final int TYPE_REC_CHILD_SUPPORT_PAYMENT = 14;
  public static final int TYPE_OTHER = 999;
  public static final int TYPE_TEMPLATE_UNKNOWN = 1000;
  public static final int TYPE_TEMPLATE_ACCOUNT_TRANSFER = 1001;
  public static final int TYPE_TEMPLATE_BILL_PAYMENT = 1003;
  public static final int TYPE_TEMPLATE_WIRE_TRANSFER = 1005;
  public static final int TYPE_TEMPLATE_ACH_BATCH = 1007;
  public static final int TYPE_TEMPLATE_OTHER = 1999;
  public static final String TYPE_UNKNOWN_STRING = "Unknown";
  public static final String TYPE_ACCOUNT_TRANSFER_STRING = "Account Transfer";
  public static final String TYPE_RECURRING_ACCOUNT_TRANSFER_STRING = "Recurring Account Transfer";
  public static final String TYPE_BILL_PAYMENT_STRING = "Payment";
  public static final String TYPE_RECURRING_BILL_PAYMENT_STRING = "Recurring Payment";
  public static final String TYPE_ACH_BATCH_STRING = "ACH Batch";
  public static final String TYPE_RECURRING_ACH_BATCH_STRING = "Recurring ACH Batch";
  public static final String TYPE_WIRE_TRANSFER_STRING = "Wire Transfer";
  public static final String TYPE_RECURRING_WIRE_TRANSFER_STRING = "Recurring Wire Transfer";
  public static final String TYPE_TAX_PAYMENT_STRING = "Tax Payment";
  public static final String TYPE_RECURRING_TAX_PAYMENT_STRING = "Recurring Tax Payment";
  public static final String TYPE_CHILD_SUPPORT_PAYMENT_STRING = "Child Support Payment";
  public static final String TYPE_RECURRING_CHILD_SUPPORT_PAYMENT_STRING = "Recurring Child Support Payment";
  public static final String TYPE_WIRE_BATCH_STRING = "Wire Batch";
  public static final String TYPE_CASHCON_DEPOSIT_STRING = "Cash Concentration Deposit Entry";
  public static final String TYPE_CASHCON_DISBURSEMENT_STRING = "Cash Concentration Disbursement Request";
  public static final String TYPE_WIRE_TEMPLATE_STRING = "Wire Template";
  public static final String TYPE_RECURRING_WIRE_TEMPLATE_STRING = "Recurring Wire Template";
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.tw.interfaces.TWTransactionTypes
 * JD-Core Version:    0.7.0.1
 */