package com.ffusion.fileimporter.fileadapters.baiparser;

public abstract interface BAIParserConsts
{
  public static final String CONFIG_ALLOW_UNKNOWN_ACCOUNTS = "ALLOWUNKNOWNACCOUNTS";
  public static final char RECORD_DELIMETER = '/';
  public static final char FIELD_DELIMETER = ',';
  public static final String FILE_BEGIN_ID = "01";
  public static final String FILE_END_ID = "99";
  public static final String GROUP_BEGIN_ID = "02";
  public static final String GROUP_END_ID = "98";
  public static final String ACCOUNT_BEGIN_ID = "03";
  public static final String ACCOUNT_END_ID = "49";
  public static final String TRANSACTION_DETAIL_ID = "16";
  public static final String CONTINUATION_ID = "88";
  public static final String FUNDS_TYPE_0 = "0";
  public static final String FUNDS_TYPE_1 = "1";
  public static final String FUNDS_TYPE_2 = "2";
  public static final String FUNDS_TYPE_S = "S";
  public static final String FUNDS_TYPE_V = "V";
  public static final String FUNDS_TYPE_D = "D";
  public static final String FUNDS_TYPE_DEFAULT = "Z";
  public static final String DEFAULT_CURRENCY = "USD";
  public static final int DEFAULT_NUMBER_OF_DECIMALS = 2;
  public static final int[] ACH_CREDIT_TRANS_TYPE_CODES = { 142, 143, 145, 147, 155, 156, 164, 165, 166, 168, 169 };
  public static final int[] ACH_DEBIT_TRANS_TYPE_CODES = { 421, 422, 423, 435, 445, 447, 451, 452, 455, 464, 466, 468, 469 };
  public static final int[] INCOMING_MONEY_TRANSFER_TRANS_TYPE_CODES = { 191, 195, 196 };
  public static final int[] OUTGOING_MONEY_TRANSFER_TRANS_TYPE_CODES = { 491, 495, 496 };
  public static final int[] LOCKBOX_DEPOSIT_TRANS_TYPE_CODES = { 115 };
  public static final int[] OTHER_CHECK_DEPOSIT_TRANS_TYPE_CODES = { 171, 172, 173, 187, 301 };
  public static final int[] ZBA_CREDITS_TRANS_TYPE_CODES = { 275 };
  public static final int[] ZBA_DEBITS_TRANS_TYPE_CODES = { 575 };
  public static final int[] CHECKS_PAID_TRANS_TYPE_CODES = { 475 };
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_CREDITS = "100";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_DEBITS = "400";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_ACH_CREDITS = "140";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_ACH_DEBITS = "450";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_INCOMMING_MONEY_TRANSFERS = "190";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_OUTGOING_MONEY_TRANSFERS = "490";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_LOCKBOX_DEPOSITS = "110";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_OTHER_CHECK_DEPOSITS = "170";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_ZBA_CREDITS = "270";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_ZBA_DEBITS = "570";
  public static final String IR_CALCULATIONS_TYPE_CODE_TOTAL_CHECKS_PAID = "470";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_IMMEDIATE_AVAILABLE = "070";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_ONE_DAY_FLOAT = "072";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_TWO_OR_MORE_DAYS_FLOAT = "074";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_OPENING_LEDGER = "010";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_OPENING_AVAILABLE = "040";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_CURRENT_LEDGER = "030";
  public static final String IR_CALCULATIONS_TYPE_CODE_INTERIM_CURRENT_AVAILABLE = "060";
  public static final String IR_CALCULATIONS_TYPE_CODE_CUSTOM_INTERIM_AVAILABLE = "1000";
  public static final String IR_CALCULATIONS_TYPE_CODE_CUSTOM_INTERIM_LEDGER = "1001";
  public static final String IR_CALCULATIONS_TYPE_CODE_CUSTOM_INTERIM_IMMEDIATE_AVAILABLE = "1002";
  public static final String IR_CALCULATIONS_TYPE_CODE_CUSTOM_INTERIM_ONE_DAY_FLOAT = "1003";
  public static final String IR_CALCULATIONS_TYPE_CODE_CUSTOM_INTERIM_TWO_DAY_FLOAT = "1004";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIParserConsts
 * JD-Core Version:    0.7.0.1
 */