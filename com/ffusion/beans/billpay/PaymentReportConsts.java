package com.ffusion.beans.billpay;

public abstract interface PaymentReportConsts
{
  public static final String RPT_PMT_CATEGORY = "BillPay";
  public static final String RPT_TYPE_PMT_HISTORY = "Payment History Report";
  public static final String RPT_TYPE_UNPROCESSED_PMT = "Unprocessed Payment Report";
  public static final String RPT_TYPE_EXPENSE_BY_PAYEE_DETAIL = "Expense by Payee - Detail";
  public static final String RPT_TYPE_EXPENSE_BY_PAYEE_SUMMARY = "Expense by Payee - Summary";
  public static final String RPT_TYPE_BILLPAY_BY_STATUS = "BillPay By Status";
  public static final String SORT_CRITERIA_DATE = "PAYDATE";
  public static final String SORT_CRITERIA_ACCOUNT = "ACCOUNTID";
  public static final String SORT_CRITERIA_AMOUNT = "AMOUNT";
  public static final String SORT_CRITERIA_PAYEE = "PAYEENAME";
  public static final String SORT_CRITERIA_USER = "User";
  public static final String SORT_CRITERIA_STATUS = "STATUS";
  public static final String SORT_CRITERIA_TYPE = "Type";
  public static final String SORT_CRITERIA_REF_NO = "REFERENCENUMBER";
  public static final String SEARCH_CRITERIA_ACCOUNT = "Account";
  public static final String SEARCH_CRITERIA_AMOUNT_FROM = "AmountFrom";
  public static final String SEARCH_CRITERIA_AMOUNT_TO = "AmountTo";
  public static final String SEARCH_CRITERIA_PAYEE = "Payee";
  public static final String SEARCH_CRITERIA_STATUS = "Status";
  public static final String RPT_STATUS_COMPLETED = "Completed";
  public static final String RPT_STATUS_NOFUNDSON = "No Funds Available";
  public static final String RPT_STATUS_FAILEDON = "Failed";
  public static final String RPT_STATUS_SCHEDULED = "Scheduled";
  public static final String RPT_STATUS_BATCH_INPROCESS = "Batch In Process";
  public static final String RPT_STATUS_LIMIT_CHECK_FAILED = "Limit Check Failed";
  public static final String RPT_STATUS_LIMIT_REVERT_FAILED = "Limit Revert Failed";
  public static final String RPT_STATUS_APPROVAL_FAILED = "Approval Failed";
  public static final String RPT_STATUS_FUNDS_ALLOCATED = "Funds Allocated";
  public static final String RPT_STATUS_CANCELLED = "Cancelled";
  public static final String RPT_STATUS_APPROVAL_PENDING = "Approval Pending";
  public static final String RPT_STATUS_APPROVAL_REJECTED = "Approval Rejected";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentReportConsts
 * JD-Core Version:    0.7.0.1
 */