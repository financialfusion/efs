package com.ffusion.tasks.wiretransfers;

import com.ffusion.tasks.Task;

public abstract interface WireTaskDefines
  extends Task
{
  public static final String REPORT_DATA = "ReportData";
  public static final int DEFAULT_BANK_PAGE_SIZE = 10;
  public static final int MAX_BATCH_NAME_LENGTH = 128;
  public static final int ERROR_NO_ACCOUNTS = 12002;
  public static final int ERROR_NO_ACCOUNT = 12003;
  public static final int ERROR_NO_WIRE_TRANSFERS = 12004;
  public static final int ERROR_NO_WIRE_TRANSFER = 12005;
  public static final int ERROR_NO_OLD_WIRE_TRANSFER = 12006;
  public static final int ERROR_FROM_ACCOUNT_ID = 12007;
  public static final int ERROR_AMOUNT = 12008;
  public static final int ERROR_FROM_ACCOUNT_ROUTING_NUM = 12009;
  public static final int ERROR_BANK_ID = 12010;
  public static final int ERROR_TO_ACCOUNT_ID = 12011;
  public static final int ERROR_TO_ACCOUNT_TYPE = 12012;
  public static final int ERROR_NO_SECURE_USER = 12014;
  public static final int ERROR_NO_WIRE_PAYEES = 12015;
  public static final int ERROR_NO_WIRE_PAYEE = 12016;
  public static final int ERROR_ALREADY_PROCESSING = 12017;
  public static final int ERROR_NO_BANKS = 12018;
  public static final int ERROR_NO_BANK = 12019;
  public static final int ERROR_BANK_NAME = 12020;
  public static final int ERROR_ROUTING_NUMBER_MISSING = 12021;
  public static final int ERROR_PAYEE_NAME_MISSING = 12022;
  public static final int ERROR_PAYEE_ACCOUNT_NUMBER = 12023;
  public static final int ERROR_PAYEE_ACCOUNT_TYPE = 12024;
  public static final int ERROR_INVALID_PAYEE_SCOPE_UPDATE = 12025;
  public static final int ERROR_PAYEE_CITY = 12026;
  public static final int ERROR_PAYEE_STATE = 12027;
  public static final int ERROR_PAYEE_ZIPCODE = 12028;
  public static final int ERROR_PAYEE_NAME_TOO_LONG = 12029;
  public static final int ERROR_MISSING_RECEIVING_BANK = 12030;
  public static final int ERROR_NO_WIRE_BATCHES = 12031;
  public static final int ERROR_NO_WIRE_BATCH = 12032;
  public static final int ERROR_NO_OLD_WIRE_BATCH = 12033;
  public static final int ERROR_NO_BUSINESS_ID = 12034;
  public static final int ERROR_INVALID_DATE = 12035;
  public static final int ERROR_NO_BATCH_NAME = 12036;
  public static final int ERROR_NO_HOST_ID = 12037;
  public static final int ERROR_NO_WIRE_TEMPLATES = 12038;
  public static final int ERROR_INVALID_PARAMETER = 12039;
  public static final int ERROR_PAYEE_ADDRESS = 12040;
  public static final int ERROR_PAYEE_COUNTRY = 12041;
  public static final int ERROR_DATE_TOO_EARLY = 12042;
  public static final int ERROR_AMOUNT_EXCEEDED = 12043;
  public static final int ERROR_MISSING_WIRE_TYPE = 12044;
  public static final int ERROR_NO_OLD_WIRE_TRANSFER_PAYEE = 12045;
  public static final int ERROR_DUPLICATE_INTERMEDIARY_BANK = 12046;
  public static final int ERROR_SAME_AS_DEST_BANK = 12047;
  public static final int ERROR_TEMPLATE_LIMIT = 12048;
  public static final int ERROR_NO_CREDIT_BIZ_INFO = 12049;
  public static final int ERROR_NO_CREDIT_BANK_INFO = 12050;
  public static final int ERROR_INVALID_PAYEE_BANK_INFO = 12051;
  public static final int ERROR_NO_WIRE_IN_BATCH = 12052;
  public static final int ERROR_NO_TEMPLATE_NAME = 12053;
  public static final int ERROR_PAYEE_ZIPCODE_FORMAT = 12054;
  public static final int ERROR_TOO_MANY_INTERMEDIARY_BANKS = 12055;
  public static final int ERROR_BATCH_NAME_TOO_LONG = 12056;
  public static final int ERROR_INVALID_PAYEE_ACCOUNT_NUMBER = 12058;
  public static final int ERROR_INVALID_BY_ORDER_OF_INFO = 12057;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.WireTaskDefines
 * JD-Core Version:    0.7.0.1
 */