package com.ffusion.beans.ach;

public abstract interface ACHDefines
{
  public static final int DEFAULT_PAGE_SIZE = 10;
  public static final String BPW_DATEFORMAT = "yyyyMMdd";
  public static final String ACH_BATCH_MEMO_NAME = "NAME";
  public static final String ACH_BATCH_MEMO_TAXID = "TAXID";
  public static final String ACH_BATCH_MEMO_TAXSTATE = "TAXSTATE";
  public static final String ACH_BATCH_MEMO_CREDIT = "CREDIT";
  public static final String ACH_BATCH_MEMO_TEMPLATEID = "TEMPLATEID";
  public static final String ACH_BATCH_MEMO_ORIGINALID = "ORIGINALID";
  public static final String ACH_BATCH_MEMO_SCOPE = "SCOPE";
  public static final String ACH_BATCH_MEMO_COMPANYID = "COMPANYID";
  public static final String ACH_BATCH_MEMO_TRACKINGID = "TRACKINGID";
  public static final String ACH_BATCH_MEMO_TOTALENTRIES = "TOTALENTRIES";
  public static final String ACH_BATCH_MEMO_BB_ACCOUNTID = "BB_ACCOUNTID";
  public static final String ACH_BATCH_MEMO_BB_ACCOUNTNUMBER = "BB_ACCOUNTNUMBER";
  public static final String ACH_BATCH_MEMO_BB_ACCOUNTBANKID = "BB_ACCOUNTBANKID";
  public static final String ACH_BATCH_MEMO_BB_ACCOUNTTYPE = "BB_ACCOUNTTYPE";
  public static final String ACH_BATCH_MEMO_BB_ACCOUNTNAME = "BB_ACCOUNTNAME";
  public static final String ACH_STATUS = "ACH_STATUS";
  public static final String ACH_STATUS_PENDING = "ACH_STATUS_PENDING";
  public static final String ACH_STATUS_COMPLETED = "ACH_STATUS_COMPLETED";
  public static final String ACH_STATUS_APPROVAL = "ACH_STATUS_APPROVAL";
  public static final String ACH_STATUS_REVERSAL = "ACH_STATUS_REVERSAL";
  public static final String ACH_STATUS_APPROVAL_PENDING = "ACH_STATUS_APPROVAL_PENDING";
  public static final String ACH_STATUS_APPROVAL_REJECTED = "ACH_STATUS_APPROVAL_REJECTED";
  public static final String ACH_TYPE = "ACH_TYPE";
  public static final String ACH_TYPE_ACH = "ACHBatch";
  public static final String ACH_TYPE_TAX = "TaxPayment";
  public static final String ACH_TYPE_CHILD = "ChildSupportPayment";
  public static final String ACH_TYPE_REVERSAL = "REVERSAL";
  public static final String ACH_COMPANYIDS = "ACH_CompanyIDs";
  public static final String ACH_VIEW = "ACH_VIEW";
  public static final String ACH_VIEW_ALL = "ALL";
  public static final String ACH_INCLUDE_PRENOTE = "IncludePrenote";
  public static final String ACH_TRACKINGID = "TrackingID";
  public static final String ACH_COMPANYID = "CompanyID";
  public static final String ACH_PAYEES = "ACHPayees";
  public static final String SORT_CRITERIA_DUEDATE = "DueDate";
  public static final String SORT_CRITERIA_TOTALCREDIT = "TotalCredit";
  public static final String SORT_CRITERIA_TOTALDEBIT = "TotalDebit";
  public static final String SORT_CRITERIA_STATUS = "Status";
  public static final String SORT_CRITERIA_FREQUENCY = "Frequency";
  public static final String SORT_CRITERIA_NUMBEROFENTRIES = "NumberOfEntries";
  public static final String SORT_CRITERIA_COMPACHID = "CompACHId";
  public static final String SORT_CRITERIA_BATCHNAME = "BatchName";
  public static final String SEARCH_CRITERIA_CUSTOMERID = "CustomerId";
  public static final String SEARCH_CRITERIA_FIID = "FIID";
  public static final String SEARCH_CRITERIA_SUBMITTEDBY = "SubmittedBys";
  public static final String SEARCH_CRITERIA_TRANSTYPE = "TransType";
  public static final String SEARCH_CRITERIA_STATUS_LIST = "StatusList";
  public static final String SEARCH_CRITERIA_CATEGORY_LIST = "CategoryList";
  public static final String SEARCH_CRITERIA_COMPANYSECS = "CompanyIdSECs";
  public static final String SEARCH_CRITERIA_TRACKINGID = "LogId";
  public static final String SEARCH_CRITERIA_COMPANY_ID = "CompId";
  public static final String SEARCH_CRITERIA_SELECTION = "SelectionCriteria";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHDefines
 * JD-Core Version:    0.7.0.1
 */