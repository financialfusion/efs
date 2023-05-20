package com.ffusion.tasks.banking;

import java.math.BigDecimal;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String BANKING = "com.ffusion.services.Banking";
  public static final String LIVEBANKING = "LiveBankingService";
  public static final String ACCOUNTS = "BankingAccounts";
  public static final String TRANSFER_TO_ACCOUNTS = "TransferToAccounts";
  public static final String TRANSFER_FROM_ACCOUNTS = "TransferFromAccounts";
  public static final String ACCOUNT = "Account";
  public static final String STATEMENT = "Statement";
  public static final String TRANSACTION = "Transaction";
  public static final String TRANSACTIONS = "Transactions";
  public static final String TRANSFER = "Transfer";
  public static final String TRANSFERS = "Transfers";
  public static final String RECTRANSFER = "RecTransfer";
  public static final String RECTRANSFERS = "RecTransfers";
  public static final String TRANSFER_BATCH = "TransferBatch";
  public static final String TRANSFER_BATCHES = "TransferBatches";
  public static final String HISTORIES = "AccountHistories";
  public static final String SUMMARIES = "AccountSummaries";
  public static final String SUMMARY = "AccountSummary";
  public static final String INSTRUMENTS = "FixedDepositInstruments";
  public static final String INSTRUMENT = "FixedDepositInstrument";
  public static final String EXTSUMMARIES = "ExtAccountSummaries";
  public static final String BALANCE_SUMMMARIES_BY_GROUP = "BalanceSummmariesByGroup";
  public static final String CASH_REPORT_DATA = "CashReport";
  public static final String GENERIC_REPORT_DATA = "GenericReport";
  public static final String TRANSFER_REPORT_DATA = "TransferReport";
  public static final BigDecimal MAX_AMOUNT_ALLOWED = new BigDecimal(1.0E+028D);
  public static final int ERROR_NO_BANKING_SERVICE = 1000;
  public static final int ERROR_NO_ACCOUNTS = 1001;
  public static final int ERROR_NO_ACCOUNT = 1002;
  public static final int ERROR_NO_TRANSFERS = 1003;
  public static final int ERROR_NO_TRANSFER = 1004;
  public static final int ERROR_NO_REC_TRANSFERS = 1005;
  public static final int ERROR_NO_REC_TRANSFER = 1006;
  public static final int ERROR_FROMACCOUNT = 1007;
  public static final int ERROR_TOACCOUNT = 1008;
  public static final int ERROR_AMOUNT = 1009;
  public static final int ERROR_DATE = 1010;
  public static final int ERROR_FREQUENCY = 1011;
  public static final int ERROR_NUMBER_OF_TRANSFERS = 1012;
  public static final int ERROR_MIN_AMOUNT = 1013;
  public static final int ERROR_MAX_AMOUNT = 1014;
  public static final int ERROR_DATE_TOO_EARLY = 1015;
  public static final int ERROR_TRANSFER_FROM_TO_SAME = 1016;
  public static final int ERROR_AMOUNT_TOO_SMALL = 1017;
  public static final int ERROR_AMOUNT_TOO_LARGE = 1018;
  public static final int ERROR_MISSING_DATE = 1019;
  public static final int ERROR_FORMAT = 1020;
  public static final int ERROR_INVALID_START_DATE = 1021;
  public static final int ERROR_INVALID_END_DATE = 1022;
  public static final int ERROR_END_BEFORE_START_DATE = 1023;
  public static final int ERROR_NO_TRANSACTIONS = 1024;
  public static final int ERROR_FROM_ACCOUNT_DOES_NOT_EXIST = 1025;
  public static final int ERROR_FROM_ACCOUNT_NOT_SPECIFIED = 1026;
  public static final int ERROR_TO_ACCOUNT_DOES_NOT_EXIST = 1027;
  public static final int ERROR_TO_ACCOUNT_NOT_SPECIFIED = 1028;
  public static final int ERROR_NO_ACCOUNT_ID = 1029;
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 1030;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 1031;
  public static final int ERROR_ALREADY_PROCESSING = 1032;
  public static final int ERROR_NO_FT_TEMPLATE = 1033;
  public static final int ERROR_NO_FT_TEMPLATES = 1034;
  public static final int ERROR_NO_FT_TEMPLATE_NAME = 1036;
  public static final int ERROR_NO_SECURE_USER = 1037;
  public static final int ERROR_DUPLICATE_FT_TEMPLATE_NAME = 1038;
  public static final int ERROR_NO_FT_TEMPLATE_ID = 1039;
  public static final int ERROR_NO_FDINSTRUMENT = 1040;
  public static final int ERROR_CANT_CHANGE_DESTINATION_TO_EXTERN = 1041;
  public static final int ERROR_CANT_CHANGE_DESTINATION_TO_BOOK = 1042;
  public static final int ERROR_INVALID_ACCOUNT_FORMAT = 1043;
  public static final int ERROR_MISSING_NEW_EXPORT_DATES = 1044;
  public static final int ERROR_NO_TRANSFER_TO_DESTINATION_ACCOUNTS = 1045;
  public static final int ERROR_NO_TRANSFER_FROM_DESTINATION_ACCOUNTS = 1046;
  public static final int ERROR_TRANSFER_BATCH_REQUIRES_TWO_TRANSFERS = 1047;
  public static final int ERROR_INVALID_TRANSFER_DESTINATION = 1048;
  public static final int ERROR_INVALID_AMOUNT = 1049;
  public static final int ERROR_NEGATIVE_AMOUNT = 1050;
  public static final int ERROR_INVALID_AMOUNT_RANGE = 1051;
  public static final int ERROR_NO_CURRENCY_SELECTED = 1052;
  public static final int ERROR_NONUSD_TO_ACH_TRANSFER = 1053;
  public static final int ERROR_ACH_TO_NONUSD_TRANSFER = 1054;
  public static final int ERROR_CANT_CHANGE_DESTINATION = 1055;
  public static final String ENTITLEMENT_ADD_TRANSFER = "AddTransfer";
  public static final String ENTITLEMENT_ADD_RECTRANSFER = "AddRecTransfer";
  public static final String ENTITLEMENT_GET_TRANSFERS = "GetTransfers";
  public static final String ENTITLEMENT_EDIT_TRANSFER = "EditTransfer";
  public static final String ENTITLEMENT_EDIT_RECTRANSFER = "EditRecTransfer";
  public static final String ENTITLEMENT_DEL_TRANSFER = "CancelTransfer";
  public static final String ENTITLEMENT_DEL_RECTRANSFER = "CancelRecTransfer";
  public static final String ENTITLEMENT_GET_ACCOUNTS = "GetAccounts";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.Task
 * JD-Core Version:    0.7.0.1
 */