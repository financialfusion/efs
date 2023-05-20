package com.ffusion.beans.banking;

public abstract interface BankingReportConsts
{
  public static final String RPT_TRANSFER_CATEGORY = "Transfer";
  public static final String RPT_TYPE_TRANSFER_HISTORY = "Transfer History Report";
  public static final String RPT_TYPE_TRANSFER_DETAIL = "Transfer Detail Report";
  public static final String RPT_TYPE_PENDING_TRANSFER = "Pending Transfer Report";
  public static final String RPT_TYPE_TRANSFER_BY_STATUS = "Transfer By Status";
  public static final String VALUE_ALL = "All";
  public static final String SORT_CRITERIA_DATE = "DATE";
  public static final String SORT_CRITERIA_FROM_ACCOUNT = "FROMACCOUNTID";
  public static final String SORT_CRITERIA_TO_ACCOUNT = "TOACCOUNTID";
  public static final String SORT_CRITERIA_AMOUNT = "AMOUNT";
  public static final String SORT_CRITERIA_STATUS = "STATUS";
  public static final String SORT_CRITERIA_TRACE_NO = "REFERENCENUMBER";
  public static final String SEARCH_CRITERIA_FROM_ACCOUNT = "FromAccount";
  public static final String SEARCH_CRITERIA_TO_ACCOUNT = "ToAccount";
  public static final String SEARCH_CRITERIA_AMOUNT_FROM = "AmountFrom";
  public static final String SEARCH_CRITERIA_AMOUNT_TO = "AmountTo";
  public static final String SEARCH_CRITERIA_STATUS = "Status";
  public static final String EXTRA_TRANSFERS = "TransfersToDisplay";
  public static final String RPT_STATUS_COMPLETED = "Completed";
  public static final String RPT_STATUS_NOFUNDSON = "No Funds Available";
  public static final String RPT_STATUS_FAILEDON = "Failed";
  public static final String RPT_STATUS_SCHEDULED = "Scheduled";
  public static final String RPT_STATUS_BATCH_INPROCESS = "Batch In Process";
  public static final String RPT_STATUS_LIMIT_CHECK_FAILED = "Limit Check Failed";
  public static final String RPT_STATUS_LIMIT_REVERT_FAILED = "Limit Revert Failed";
  public static final String RPT_STATUS_APPROVAL_FAILED = "Approval Failed";
  public static final String RPT_STATUS_APPROVAL_PENDING = "Approval Pending";
  public static final String RPT_STATUS_APPROVAL_REJECTED = "Approval Rejected";
  public static final String RPT_STATUS_CANCELLED = "Cancelled";
  public static final String RPT_STATUS_IMMED_INPROCESS = "Immediate In Process";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.BankingReportConsts
 * JD-Core Version:    0.7.0.1
 */