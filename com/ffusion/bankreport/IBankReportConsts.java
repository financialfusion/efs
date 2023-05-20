package com.ffusion.bankreport;

import com.ffusion.util.DateConsts;

public abstract interface IBankReportConsts
  extends DateConsts
{
  public static final String STRING_YES = "Y";
  public static final String STRING_NO = "N";
  public static final String ERR_MSG_UNKNOWN = "A bank report error has occurred.";
  public static final String ERR_MSG_DB_EXCEPTION = "A database related error has occurred.";
  public static final String ERR_MSG_CONNECTION_POOL_EXCEPTION = "A database connection pool related error has occurred.";
  public static final String ERR_MSG_PROFILE_EXCEPTION = "A profile database related error has occurred.";
  public static final String ERR_MSG_NULL_REPORT_TYPE = "The type of the report cannot be null.";
  public static final String ERR_MSG_NULL_ACCOUNT = "The account cannot be null.";
  public static final String ERR_MSG_NULL_ACCOUNT_ID = "The account ID of the specified account cannot be null.";
  public static final String ERR_MSG_NULL_BANK_ID = "The bank ID of the specified account cannot be null.";
  public static final String ERR_MSG_NULL_ROUTING_NUM = "The routing number of the specified account cannot be null.";
  public static final String ERR_MSG_REMOVE_ACCOUNT_DATA_BANK_ID_MISMATCH = "The business/consumer is not entitled to delete the report data belonging to the specified account.";
  public static final String ERR_MSG_NULL_GENERATED_TIME = "The generation time of the report cannot be null.";
  public static final String ERR_MSG_NEGATIVE_NUM_DAYS = "The value for number of days cannnot be negative.";
  public static final String ERR_MSG_INVALID_REPORT_ID = "The specified report ID is invalid.";
  public static final String ERR_MSG_NULL_BANK_REPORT = "The bank report cannot be null.";
  public static final String ERR_MSG_NULL_LINE_ITEM = "The bank report line data be null.";
  public static final String ERR_MSG_NULL_REPORT_CRITERIA = "No criteria has been specified for the bank report.";
  public static final String ERR_MSG_SEARCH_CRITERIA_MISSING_REPORT_ID = "The report ID has not been specified in the report search criteria.";
  public static final String ERR_MSG_MISSING_REPORT_DEFINITION_FOR_REPORT_TYPE = "No report definition exists for the given report type.";
  public static final String ERR_MSG_NULL_BANK_REPORT_DEFN = "No report definition exists for the given report key.";
  public static final String ERR_MSG_BR_ADAPTER_INIT = "Could not initialize the bank report adapter.";
  public static final String ERR_MSG_BR_ADAPTER_GET_REPORTS = "Could not obtain the bank reports from the database.";
  public static final String ERR_MSG_BR_ADAPTER_GET_ACCOUNTS = "Could not obtain the accounts for the given bank report.";
  public static final String ERR_MSG_BR_ADAPTER_CLEANUP_OLD_REPORTS = "Could not remove the old bank reports from the database.";
  public static final String ERR_MSG_BR_ADAPTER_REMOVE_REPORT_FILE = "Could not remove the report data from the database.";
  public static final String ERR_MSG_BR_ADAPTER_REMOVE_REPORT = "Could not remove the report data from the database.";
  public static final String ERR_MSG_BR_ADAPTER_REMOVE_ACCOUNT_DATA = "Could not remove the report data for the specified account.";
  public static final String ERR_MSG_BR_ADAPTER_REMOVE_DIRECTORY_DATA = "Could not remove the report data for the specified business/user.";
  public static final String ERR_MSG_BR_ADAPTER_REMOVE_EMPTY_REPORTS = "Could not remove the empty reports from the database.";
  public static final String ERR_MSG_BR_ADAPTER_GET_CONNECTION = "Could not obtain a connection from the connection pool.";
  public static final String ERR_MSG_BR_ADAPTER_RELEASE_CONNECTION = "Could not release a connection back to the connection pool.";
  public static final String ERR_MSG_BR_ADAPTER_INSERT_BANK_REPORT = "Could not add the bank report to the database.";
  public static final String ERR_MSG_BR_ADAPTER_UPDATE_BANK_REPORT = "Could not modify the bank report.";
  public static final String ERR_MSG_BR_ADAPTER_INSERT_LINE_ITEM = "Could not add the data to the bank report.";
  public static final String ERR_MSG_BR_ADAPTER_GET_BUSINESS_ID_FROM_ACCOUNT = "Could not obtain the business ID of the gievn account.";
  public static final String ERR_MSG_BR_ADAPTER_GET_USER_ID_FROM_ACCOUNT = "Could not obtain the user ID of the gievn account.";
  public static final String ERR_MSG_BR_SERVICE_GET_REPORT_DATA = "Could not obtain the bank report data.";
  public static final String ERR_MSG_BR_SERVICE_PROCESS_BANK_REPORT_FILE = "Could not process the bank report file.";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.IBankReportConsts
 * JD-Core Version:    0.7.0.1
 */