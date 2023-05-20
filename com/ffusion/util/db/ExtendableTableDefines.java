package com.ffusion.util.db;

public abstract interface ExtendableTableDefines
{
  public static final String DB_CATALOG = "Catalog";
  public static final String DB_SCHEMA = "Schema";
  public static final String COLUMN_NAME = "COLUMN_NAME";
  public static final String DATA_TYPE = "DATA_TYPE";
  public static final String CUSTOMER_TYPE = "CUSTOMER_TYPE";
  public static final String PPAY = "PPay_IssueDecisions";
  public static final String BANK_REPORTS = "BR_BANKREPORTS";
  public static final String BANK_REPORT_DATA = "BR_BANKREPORTDATA";
  public static final String AFFIL_CUTOFF_DEFN = "affil_cutoff_defn";
  public static final String AFFIL_CUTOFF_TIMES = "affil_cutoff_times";
  public static final String PPAY_ACCOUNT_ID = "accountID";
  public static final String PPAY_BANK_ID = "bankID";
  public static final String PPAY_BANK_NAME = "bankName";
  public static final String PPAY_ROUTING_NUMBER = "routingNumber";
  public static final String PPAY_CHECK_NUMBER = "checkNumber";
  public static final String PPAY_CHECK_DATE = "checkDate";
  public static final String PPAY_AMOUNT = "amount";
  public static final String PPAY_VOID_CHECK = "voidCheck";
  public static final String PPAY_ADDITIONAL_DATE = "additionalData";
  public static final String PPAY_REJECT_REASON = "rejectReason";
  public static final String PPAY_ISSUE_DATE = "issueDate";
  public static final String PPAY_DECISION = "decision";
  public static final String PPAY_SUBMITTING_USER = "submittingUserName";
  public static final String BANK_REPORTS_REPORT_ID = "REPORTID";
  public static final String BANK_REPORTS_GENERATED_TIME = "GENERATEDTIME";
  public static final String BANK_REPORTS_IMPORT_TIME = "IMPORTTIME";
  public static final String BANK_REPORTS_REPORT_TYPE = "REPORTTYPE";
  public static final String BANK_REPORTS_DIRECTORY_ID = "DIRECTORYID";
  public static final String BANK_REPORT_DATA_REPORT_ID = "REPORTID";
  public static final String BANK_REPORT_DATA_LINE_NUM = "LINENUM";
  public static final String BANK_REPORT_DATA_LINE_DATA = "LINEDATA";
  public static final String BANK_REPORT_DATA_RTG_NUM = "RTGNUM";
  public static final String BANK_REPORT_DATA_ACCOUNT_ID = "ACCOUNTID";
  public static final String BANK_REPORT_DATA_FORCE_PAGE_BREAK = "FORCEPAGEBREAK";
  public static final String AFFIL_CUTOFF_DEFN_BANK_ID = "affiliate_bank_id";
  public static final String AFFIL_CUTOFF_DEFN_CUT_OFF_TYPE = "cut_off_type";
  public static final String AFFIL_CUTOFF_DEFN_CREATE_EMPTY_FILE = "create_empty_file";
  public static final String AFFIL_CUTOFF_DEFN_PROCESS_ON_HOLIDAYS = "process_on_holidays";
  public static final String AFFIL_CUTOFF_TIMES_BANK_ID = "affiliate_bank_id";
  public static final String AFFIL_CUTOFF_TIMES_CUT_OFF_TYPE = "cut_off_type";
  public static final String AFFIL_CUTOFF_TIMES_DAY_OF_WEEK = "day_of_week";
  public static final String AFFIL_CUTOFF_TIMES_TIME_OF_DAY = "time_of_day";
  public static final String AFFIL_CUTOFF_TIMES_ONE_TIME_EXTENSION = "one_time_extension";
  public static final String AFFIL_CUTOFF_TIMES_ACTIVE = "active";
  public static final String[] EXTENDABLE_TABLES = { "PPay_IssueDecisions", "BR_BANKREPORTS", "BR_BANKREPORTDATA", "affil_cutoff_defn", "affil_cutoff_times" };
  public static final String[] PPAY_COLUMNS = { "accountID", "bankID", "bankName", "routingNumber", "checkNumber", "checkDate", "amount", "voidCheck", "additionalData", "rejectReason", "issueDate", "decision", "submittingUserName" };
  public static final String[] BANK_REPORTS_COLUMNS = { "REPORTID", "GENERATEDTIME", "IMPORTTIME", "REPORTTYPE", "DIRECTORYID" };
  public static final String[] BANK_REPORT_DATA_COLUMNS = { "REPORTID", "LINENUM", "LINEDATA", "RTGNUM", "ACCOUNTID", "FORCEPAGEBREAK" };
  public static final String[] AFFIL_CUTOFF_DEFN_COLUMNS = { "affiliate_bank_id", "cut_off_type", "create_empty_file", "process_on_holidays" };
  public static final String[] AFFIL_CUTOFF_TIMES_COLUMNS = { "affiliate_bank_id", "cut_off_type", "day_of_week", "time_of_day", "one_time_extension", "active" };
  public static final String[][] EXTENDABLE_TABLE_COLUMNS = { PPAY_COLUMNS, BANK_REPORTS_COLUMNS, BANK_REPORT_DATA_COLUMNS, AFFIL_CUTOFF_DEFN_COLUMNS, AFFIL_CUTOFF_TIMES_COLUMNS };
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.ExtendableTableDefines
 * JD-Core Version:    0.7.0.1
 */