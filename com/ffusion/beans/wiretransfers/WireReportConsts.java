package com.ffusion.beans.wiretransfers;

public abstract interface WireReportConsts
{
  public static final String RPT_WIRE_CATEGORY = "Wires";
  public static final String RPT_TYPE_COMPLETED_WIRES = "Completed Wires";
  public static final String RPT_TYPE_FAILED_WIRES = "Failed Wires";
  public static final String RPT_TYPE_INPROCESS_WIRES = "InProcess Wires";
  public static final String RPT_TYPE_BY_STATUS = "Wire By Status";
  public static final String RPT_TYPE_BY_SOURCE = "Wire By Source";
  public static final String RPT_TYPE_BY_TEMPLATE = "Wire By Template";
  public static final String RPT_TYPE_TEMPLATE_WIRES = "Wire Templates";
  public static final String SORT_CRITERIA_DATE = "DATE_TO_POST";
  public static final String SORT_CRITERIA_ACCOUNT = "FROM_ACCOUNT_ID";
  public static final String SORT_CRITERIA_AMOUNT = "AMOUNT";
  public static final String SORT_CRITERIA_PAYEE = "WIRE_PAYEE";
  public static final String SORT_CRITERIA_USER = "User";
  public static final String SORT_CRITERIA_STATUS = "STATUS";
  public static final String SORT_CRITERIA_TYPE = "Type";
  public static final String SORT_CRITERIA_REF_NO = "REFERENCENUMBER";
  public static final String SEARCH_CRITERIA_ACCOUNT = "Account";
  public static final String SEARCH_CRITERIA_AMOUNT_FROM = "AmountFrom";
  public static final String SEARCH_CRITERIA_AMOUNT_TO = "AmountTo";
  public static final String SEARCH_CRITERIA_PAYEE = "Payee";
  public static final String SEARCH_CRITERIA_WIRESOURCE = "WireSource";
  public static final String SEARCH_CRITERIA_WIRESCOPE = "WireScope";
  public static final String SEARCH_CRITERIA_STATUS = "Status";
  public static final String SEARCH_CRITERIA_TEMPLATE = "Template";
  public static final String SEARCH_CRITERIA_LEVEL = "LEVEL";
  public static final String SEARCH_CRITERIA_LEVEL_SUMMARY = "Summary";
  public static final String SEARCH_CRITERIA_LEVEL_DETAILED = "Detailed";
  public static final String RPT_STATUS_CREATED = "Created";
  public static final String RPT_STATUS_COMPLETED = "Completed";
  public static final String RPT_STATUS_SCHEDULED = "Scheduled";
  public static final String RPT_STATUS_RELEASED = "Released";
  public static final String RPT_STATUS_RELEASEPENDING = "Release Pending";
  public static final String RPT_STATUS_CANCELLED = "Cancelled";
  public static final String RPT_STATUS_IN_FUNDS_APPROVAL = "In Funds Approval";
  public static final String RPT_STATUS_REJECTED = "Rejected";
  public static final String RPT_STATUS_BACKENDREJECTED = "Rejected by Backend";
  public static final String RPT_STATUS_RELEASEREJECTED = "Release Rejected";
  public static final String RPT_STATUS_RECEIVED = "Received by Backend";
  public static final String RPT_STATUS_CONFIRMED = "Confirmed by Backend";
  public static final String RPT_STATUS_HELD = "Backend Pending";
  public static final String RPT_STATUS_FUNDS_PENDING = "Funds Pending";
  public static final String RPT_STATUS_NO_FUNDS = "No Funds";
  public static final String RPT_STATUS_PENDING_APPROVAL = "Approval Pending";
  public static final String RPT_STATUS_APPROVAL_REJECTED = "Approval Rejected";
  public static final String RPT_STATUS_APPROVAL_FAILED = "Approval Failed";
  public static final String RPT_STATUS_ACKNOWLEDGED = "Acknowledged";
  public static final String RPT_STATUS_IMMED_INPROCESS = "Immediate In Process";
  public static final String RPT_STATUS_INPROCESS = "In Process by Backend";
  public static final String RPT_STATUS_BACKENDFAILED = "Failed at Backend";
  public static final String RPT_STATUS_FUNDS_REVERTED = "Funds Reverted";
  public static final String RPT_STATUS_FUNDS_REVERT_FAILED = "Funds Revert Failed";
  public static final String RPT_STATUS_FUNDS_FAILED_ON = "Funds Approval Failed";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireReportConsts
 * JD-Core Version:    0.7.0.1
 */