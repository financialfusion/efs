package com.ffusion.beans.accounts;

public abstract interface GenericBankingRptConsts
{
  public static final String RPT_TYPE_DEPOSIT_DETAIL = "DepositDetail";
  public static final String RPT_TYPE_TRANSACTION_DETAIL = "TransactionDetail";
  public static final String RPT_TYPE_CREDIT_RPT = "CreditReport";
  public static final String RPT_TYPE_DEBIT_RPT = "DebitReport";
  public static final String RPT_TYPE_ACCOUNT_HISTORY = "AccountHistoryReport";
  public static final String RPT_TYPE_DISPLAY_FILE = "DisplayFile";
  public static final String RPT_TYPE_BALANCE_SUMMARY = "BalanceSummaryReport";
  public static final String RPT_TYPE_BALANCE_SUMMARY_AND_DETAIL = "BalanceDetailReport";
  public static final String RPT_TYPE_BALANCE_DETAIL = "BalanceDetailOnlyReport";
  public static final String RPT_TYPE_CUSTOM_SUMMARY = "CustomSummaryReport";
  public static final String RPT_TYPE_ACCT_HISTORY = "AccountHistory";
  public static final String RPT_TYPE_ALERT_TRANSACTION = "AlertTransaction";
  public static final String RPT_TYPE_CONSUMER_ACCT_HISTORY = "ConsumerAccountHistory";
  public static final String RPT_TYPE_CONSUMER_ACCT_REGISTER = "ConsumerAccountRegister";
  public static final String ACCOUNTS_FOR_REPORT = "AccountsForReport";
  public static final String CUSTOM_TRANS_GROUP_CODES_FOR_REPORT = "CustomTransGroupCodesForReport";
  public static final String BUSINESS_FOR_REPORT = "BusinessForReport";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION = "DataClassification";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION_PREVIOUSDAY = "P";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION_INTRADAY = "I";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION_ALL = "A";
  public static final String SEARCH_CRITERIA_ACCOUNTS = "Accounts";
  public static final String SEARCH_CRITERIA_VALUE_ALL_ACCOUNTS = "AllAccounts";
  public static final String SEARCH_CRITERIA_VALUE_ACCOUNT_SEPERATOR = ",";
  public static final String SEARCH_CRITERIA_VALUE_ACCOUNT_BANKID_SEPERATOR = ":";
  public static final String SEARCH_CRITERIA_DIVIDER = "------------------------------";
  public static final String SEARCH_CRITERIA_TRANS_TYPE = "TransactionType";
  public static final String SEARCH_CRITERIA_VALUE_ALL_TRANS_TYPE = "AllTransactionTypes";
  public static final String SEARCH_CRITERIA_VALUE_ALL_CREDIT_TRANSACTIONS = "AllCreditTransactions";
  public static final String SEARCH_CRITERIA_VALUE_ALL_DEBIT_TRANSACTIONS = "AllDebitTransactions";
  public static final String SEARCH_CRITERIA_CUSTOM_TRANS_GROUP_PREFIX = "CustTransGrp_";
  public static final String SEARCH_CRITERIA_AMOUNT_MIN = "MinimumAmount";
  public static final String SEARCH_CRITERIA_AMOUNT_MAX = "MaximumAmount";
  public static final String SEARCH_CRITERIA_TRANS_STATUS = "TransactionStatus";
  public static final String SEARCH_CRITERIA_VALUE_TRANS_STATUS_COMPLETED = "Completed";
  public static final String SEARCH_CRITERIA_TRANS_SUBTYPE = "TransactionSubType";
  public static final String SEARCH_CRITERIA_VALUE_ALL_TRANS_SUBTYPE = "AllTransactionSubtypes";
  public static final String SEARCH_CRITERIA_PAYEE_PAYOR = "PayeePayor";
  public static final String SEARCH_CRITERIA_PAYOR_NUM = "PayorNumber";
  public static final String SEARCH_CRITERIA_ORIG_USER = "OriginatingUser";
  public static final String SEARCH_CRITERIA_PO_NUM = "PONumber";
  public static final String SEARCH_CRITERIA_TRANS_REF_NUM = "TransactionReferenceNumber";
  public static final String SEARCH_CRITERIA_BANK_REF_NUM = "BankReferenceNumber";
  public static final String SEARCH_CRITERIA_BANK_REF_NUM_NULL = "BankReferenceNumberNull";
  public static final String SEARCH_CRITERIA_ZBA_WITH_BANK_REF_NUM_NULL = "ZBAWithBankReferenceNumberNull";
  public static final String SEARCH_CRITERIA_CUSTOMER_REF_NUM = "CustomerReferenceNumber";
  public static final String SEARCH_CRITERIA_CUSTOMER_REF_NUM_NULL = "CustomerReferenceNumberNull";
  public static final String SEARCH_CRITERIA_ZBA_WITH_CUSTOMER_REF_NUM_NULL = "ZBAWithCustomerReferenceNumberNull";
  public static final String SEARCH_CRITERIA_INST_NUMBER = "FixedDepositInstrumentNumber";
  public static final String SEARCH_CRITERIA_INST_BANK_NAME = "FixedDepositInstrumentBankName";
  public static final String SEARCH_CRITERIA_SUMMARY_CODE = "BAISummaryCode";
  public static final String SEARCH_CRITERIA_VALUE_ALL_SUMMARY_CODE = "AllSummaryCodes";
  public static final String SEARCH_CRITERIA_ZBA_DISPLAY = "ZBA Display";
  public static final String SEARCH_CRITERIA_SHOW_PREV_DAY_OPENING_LEDGER = "Show Prev-day Opening Ledger";
  public static final String SEARCH_CRITERIA_TRANS_GROUPS = "Transaction Groups";
  public static final String SEARCH_CRITERIA_CUST_REF_MIN = "Customer Reference Number (Min)";
  public static final String SEARCH_CRITERIA_CUST_REF_MAX = "Customer Reference Number (Max)";
  public static final String SEARCH_CRITERIA_BANK_REF_MIN = "Bank Reference Number (Min)";
  public static final String SEARCH_CRITERIA_BANK_REF_MAX = "Bank Reference Number (Max)";
  public static final String SEARCH_CRITERIA_DISPLAY_ZBA_TRANSACTIONS = "DisplayZBATransactions";
  public static final String SEARCH_CRITERIA_SUPPRESS_SUB_ACCOUNTS = "SupressSubAccounts";
  public static final String SEARCH_CRITERIA_SORT_BY_LOCATION = "SortByLocation";
  public static final String SEARCH_CRITERIA_DESCRIPTION = "Description";
  public static final String SEARCH_CRITERIA_SHOW_MEMO = "ShowMemo";
  public static final String SEARCH_CRITERIA_SHOW_BALANCE = "ShowBalance";
  public static final String SEARCH_CRITERIA_BANK_REF_NUM_IS_NULL = "SearchForNullBankRefNum";
  public static final String SEARCH_CRITERIA_CUST_REF_NUM_IS_NULL = "SearchForNullCustRefNum";
  public static final String SEARCH_CRITERIA_DATASOURCE_LOAD_START_TIME = "Datasource Load Start Time";
  public static final String SEARCH_CRITERIA_DATASOURCE_LOAD_END_TIME = "Datasource Load End Time";
  public static final String SEARCH_CRITERIA_TIME_FORMAT = "TimeFormat";
  public static final String SEARCH_CRITERIA_DATE_FORMAT = "DateFormat";
  public static final String SEARCH_CRITERIA_VALUE_DISPLAY_ZBA_TRANS_ALL = "MasterAndSubAccounts";
  public static final String SEARCH_CRITERIA_VALUE_DISPLAY_ZBA_TRANS_MASTER_ONLY = "MasterAccounts";
  public static final String SEARCH_CRITERIA_VALUE_DISPLAY_ZBA_TRANS_SUB_ONLY = "SubAccounts";
  public static final String SEARCH_CRITERIA_VALUE_DISPLAY_ZBA_TRANS_NONE = "None";
  public static final String SORT_CRITERIA_ACCOUNT_NUMBER = "AccountNumber";
  public static final String SORT_CRITERIA_CREDIT = "Amount+";
  public static final String SORT_CRITERIA_DEBIT = "Amount-";
  public static final String SORT_CRITERIA_CREDIT_DEBIT = "CreditDebit";
  public static final String SORT_CRITERIA_DEBIT_CREDIT = "DebitCredit";
  public static final String SORT_CRITERIA_PROCESS_DATE = "ProcessingDate";
  public static final String SORT_CRITERIA_TRANS_TYPE = "TransactionType";
  public static final String SORT_CRITERIA_TRANS_SUBTYPE = "TransactionSubType";
  public static final String SORT_CRITERIA_AMOUNT = "Amount";
  public static final String SORT_CRITERIA_PAYEE_PAYOR = "PayeePayor";
  public static final String SORT_CRITERIA_PAYOR_NUM = "PayorNumber";
  public static final String SORT_CRITERIA_ORIG_USER = "OriginatingUser";
  public static final String SORT_CRITERIA_PO_NUM = "PONumber";
  public static final String SORT_CRITERIA_TRANS_REF_NUM = "TransactionReferenceNumber";
  public static final String SORT_CRITERIA_BANK_REF_NUM = "BankReferenceNumber";
  public static final String SORT_CRITERIA_CUSTOMER_REF_NUM = "CustomerReferenceNumber";
  public static final String SORT_CRITERIA_ROUTING_NUM = "RoutingNum";
  public static final String SORT_CRITERIA_TRANS_INDEX = "TransactionIndex";
  public static final String SORT_CRITERIA_DESCRIPTION = "Description";
  public static final String SORT_CRITERIA_MEMO = "Memo";
  public static final String SORT_CRITERIA_RUNNING_BALANCE = "RunningBalance";
  public static final String SORT_CRITERIA_DUE_DATE = "DueDate";
  public static final String SORT_CRITERIA_LOCATION = "Location";
  public static final String SORT_CRITERIA_SUB_TYPE_DESC = "SubTypeDesc";
  public static final String SORT_CRITERIA_DATE = "Date";
  public static final String LARGE_PAGE_SIZE = "10000000";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.GenericBankingRptConsts
 * JD-Core Version:    0.7.0.1
 */