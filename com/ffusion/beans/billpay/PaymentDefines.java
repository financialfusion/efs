package com.ffusion.beans.billpay;

public abstract interface PaymentDefines
{
  public static final String BPW_DATEFORMAT = "yyyyMMdd";
  public static final int DEFAULT_PAGE_SIZE = 10;
  public static final String FAILED_LIMITS = "FAILED_LIMITS";
  public static final String PAYMENT_ACCOUNTS = "PAYMENT_ACCOUNTS";
  public static final String PAYMENT_PAYEES = "PAYMENT_PAYEES";
  public static final String PAYMENT_RECPAYMENTS = "PAYMENT_RECPAYMENTS";
  public static final String PAYMENT_STATUS = "PAYMENT_STATUS";
  public static final String PAYMENT_STATUS_PENDING = "PAYMENT_STATUS_PENDING";
  public static final String PAYMENT_STATUS_COMPLETED = "PAYMENT_STATUS_COMPLETED";
  public static final String PAYMENT_STATUS_APPROVAL = "PAYMENT_STATUS_APPROVAL";
  public static final String PAYMENT_AUDIT_STATE_ADDED = "ADDED";
  public static final String PAYMENT_AUDIT_STATE_DELETED = "DELETED";
  public static final String PAYMENT_AUDIT_STATE_MODIFIED = "MODIFIED";
  public static final String PAYMENT_AUDIT_STATE_FAILED = "FAILED";
  public static final String SORT_CRITERIA_PAYDATE = "DateToPost";
  public static final String SORT_CRITERIA_PAYEE = "PayeeName";
  public static final String SORT_CRITERIA_ACCOUNT = "AcctDebitId";
  public static final String SORT_CRITERIA_FREQUENCY = "Frequency";
  public static final String SORT_CRITERIA_STATUS = "Status";
  public static final String SORT_CRITERIA_AMOUNT = "Amount";
  public static final String SORT_CRITERIA_TEMPLATENAME = "TemplateName";
  public static final String SORT_CRITERIA_TEMPLATETYPE = "PaymentType";
  public static final String SEARCH_CRITERIA_CUSTOMERID = "CustomerId";
  public static final String SEARCH_CRITERIA_FIID = "FIID";
  public static final String SEARCH_CRITERIA_SUBMITTEDBY = "SubmittedBys";
  public static final String SEARCH_CRITERIA_USERID = "UserIds";
  public static final String SEARCH_CRITERIA_DEST = "Dests";
  public static final String SEARCH_CRITERIA_TRANSTYPE = "TransType";
  public static final String SEARCH_CRITERIA_SCOPE = "TransScope";
  public static final String SEARCH_CRITERIA_SOURCE = "TransSource";
  public static final String SEARCH_CRITERIA_TEMPLATEID = "TemplateId";
  public static final String SEARCH_CRITERIA_MIN_AMOUNT = "MinAmount";
  public static final String SEARCH_CRITERIA_MAX_AMOUNT = "MaxAmount";
  public static final String SEARCH_CRITERIA_SELECTION = "SelectionCriteria";
  public static final String SEARCH_CRITERIA_STATUS_LIST = "StatusList";
  public static final String SEARCH_CRITERIA_STARTDATE = "StartDate";
  public static final String SEARCH_CRITERIA_ENDDATE = "EndDate";
  public static final String SEARCH_CRITERIA_PMTTYPE = "PmtType";
  public static final String SEARCH_CRITERIA_AMOUNT = "Amount";
  public static final String SEARCH_CRITERIA_ACCOUNT_ID = "AcctDebitNumber";
  public static final String SEARCH_CRITERIA_PAYEE_ID = "PayeeIdList";
  public static final String PAYMENT_TYPE_PAYMENT = "PAYMENT";
  public static final String PAYMENT_TYPE_SINGLE = "Current";
  public static final String PAYMENT_TYPE_RECURRING = "Recurring";
  public static final String PAYMENT_TYPE_TEMPLATE = "TEMPLATE";
  public static final String PAYMENT_TYPE_RECTEMPLATE = "RECTEMPLATE";
  public static final String PAYMENT_TYPE_BATCH_TEMPLATE = "BATCHTEMPLATE";
  public static final String PAYMENT_TYPE_REPETITIVE = "Repetitive";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentDefines
 * JD-Core Version:    0.7.0.1
 */