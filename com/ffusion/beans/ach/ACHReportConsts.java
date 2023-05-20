package com.ffusion.beans.ach;

public abstract interface ACHReportConsts
{
  public static final String RPT_ACH_CATEGORY = "ACH";
  public static final String RPT_TYPE_TOTAL_TAX_PMTS = "Total Tax Payments";
  public static final String RPT_TYPE_TOTAL_CHILD_SUPPORT_PMTS = "Total Child Support Payments";
  public static final String RPT_TYPE_COMPLETED_ACH_PMTS = "Completed ACH Payments";
  public static final String RPT_TYPE_FILE_UPLOAD = "ACH File Upload";
  public static final String RPT_TYPE_FILE_IMPORT = "ACH Import Entries Report";
  public static final String RPT_TYPE_CB_ACH_FILE_UPLOAD = "CB ACH File Upload";
  public static final String RPT_TYPE_PARTICIPANT_PRENOTE = "ACH Participant Prenote Report";
  public static final String SORT_CRITERIA_BATCH_SUBMISSION_DATE = "SUBMITDATE";
  public static final String SORT_CRITERIA_BATCH_PROCESS_DATE = "PROCESSEDONDATE";
  public static final String SORT_CRITERIA_BATCH_EFFECTIVE_DATE = "DATE";
  public static final String SORT_CRITERIA_ACH_CLASS = "STANDARDENTRYCLASSCODE";
  public static final String SORT_CRITERIA_PAYMENT_STATUS = "Payment Status";
  public static final String SORT_CRITERIA_AMOUNT = "AMOUNT";
  public static final String SEARCH_CRITERIA_AMOUNT_FROM = "AmountFrom";
  public static final String SEARCH_CRITERIA_AMOUNT_TO = "AmountTo";
  public static final String SEARCH_CRITERIA_BATCH_TYPE = "BatchType";
  public static final String SEARCH_CRITERIA_COMPANY_ID = "CompanyID";
  public static final String SEARCH_CRITERIA_BATCH_STATUS = "BatchStatus";
  public static final String SEARCH_CRITERIA_VALUE_CREDIT_BATCH = "Credit Batch";
  public static final String SEARCH_CRITERIA_VALUE_DEBIT_BATCH = "Debit Batch";
  public static final String SEARCH_CRITERIA_PENDING_COMPLETED = "PendingCompleted";
  public static final String SEARCH_CRITERIA_VALUE_ALL = "All";
  public static final String SEARCH_CRITERIA_VALUE_PENDING = "Pending";
  public static final String SEARCH_CRITERIA_VALUE_COMPLETED = "Completed";
  public static final String SEARCH_CRITERIA_PRENOTE_STATUS = "PrenoteStatus";
  public static final String CC_LOCATION_PRENOTE_SUCCESS = "Success";
  public static final String CC_LOCATION_PRENOTE_PENDING = "Pending";
  public static final String CC_LOCATION_PRENOTE_FAILED = "Failed";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHReportConsts
 * JD-Core Version:    0.7.0.1
 */