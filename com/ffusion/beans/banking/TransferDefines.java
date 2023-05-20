package com.ffusion.beans.banking;

public abstract interface TransferDefines
{
  public static final int DEFAULT_PAGE_SIZE = 10;
  public static final String BPW_DATEFORMAT = "yyyyMMdd";
  public static final String BPW_STATUS_UNKNOWN = "UNKNOWN";
  public static final String TRANSFER_TYPE_SINGLE = "Current";
  public static final String TRANSFER_TYPE_RECURRING = "Repetitive";
  public static final String TRANSFER_TYPE_TEMPLATE = "TEMPLATE";
  public static final String TRANSFER_TYPE_RECTEMPLATE = "RECTEMPLATE";
  public static final String TRANSFER_BATCH_TYPE_TEMPLATE = "TEMPLATE";
  public static final String TRANSFER_BATCH_TYPE_BATCH = "BATCH";
  public static final String TRANSFER_BOOK = "INTERNAL";
  public static final String TRANSFER_EXTERNAL = "ITOE";
  public static final String TRANSFER_BOTH = "BOTH";
  public static final String TRANSFER_ITOE = "ITOE";
  public static final String TRANSFER_ETOI = "ETOI";
  public static final String TRANSFER_ITOI = "ITOI";
  public static final String TRANSFER_TEXT_BOOK = "Internal to Internal";
  public static final String TRANSFER_TEXT_EXTERNAL = "Internal to External";
  public static final String TRANSFER_SOURCE_FREEFORM = "FREEFORM";
  public static final String TRANSFER_SOURCE_TEMPLATE = "TEMPLATE";
  public static final String TRANSFER_AUDIT_STATE_ADDED = "ADDED";
  public static final String TRANSFER_AUDIT_STATE_DELETED = "DELETED";
  public static final String TRANSFER_AUDIT_STATE_MODIFIED = "MODIFIED";
  public static final String TRANSFER_AUDIT_STATE_TEMP_ADDED = "TEMPLATE_ADDED";
  public static final String TRANSFER_AUDIT_STATE_TEMP_DELETED = "TEMPLATE_DELETED";
  public static final String TRANSFER_AUDIT_STATE_TEMP_MODIFIED = "TEMPLATE_MODIFIED";
  public static final String TRANSFER_AUDIT_STATE_APPROVAL_PENDING = "APPROVAL_PENDING";
  public static final String TRANSFER_AUDIT_STATE_APPROVAL_REJECTED = "APPROVAL_REJECTED";
  public static final String TRANSFER_AUDIT_STATE_APPROVAL_FAILED = "APPROVAL_FAILED";
  public static final String TRANSFER_CATEGORY_USER_ENTRY = "USER_ENTRY";
  public static final String TRANSFER_SCOPE_USER = "USER";
  public static final String TRANSFER_SCOPE_BUSINESS = "BUSINESS";
  public static final String ACCOUNTS_KEY = "ACCOUNTS";
  public static final String DESTINATION_KEY = "DESTINATION";
  public static final String EXTACCTID_KEY = "EXTACCTID";
  public static final String DATEPOSTED_KEY = "DATEPOSTED";
  public static final String FREQUENCY_KEY = "Frequency";
  public static final String FREQUENCY_VALUE_KEY = "FrequencyValue";
  public static final String NUMOFTRANSFERS_KEY = "NumberTransfers";
  public static final String FREQUENCY_VALUE_NONE = "NONE";
  public static final String MEMO_KEY = "Memo";
  public static final String ACCTBANKRTNTYPE_KEY = "ACCTBANKRTNTYPE";
  public static final String FAILED_LIMITS = "FAILED_LIMITS";
  public static final String LOG_DATE_KEY = "LogDate";
  public static final String USER_ID_KEY = "UserId";
  public static final String TRANSFER_ACCOUNTS = "TRANSFER_ACCOUNTS";
  public static final String TRANSFER_STATUS = "TRANSFER_STATUS";
  public static final String TRANS_TYPE_SINGLE = "TRANS_TYPE_SINGLE";
  public static final String TRANS_TYPE_MODEL = "TRANS_TYPE_MODEL";
  public static final String TRANS_TYPE_TEMPLATE = "TRANS_TYPE_TEMPLATE";
  public static final String SORT_CRITERIA_DATETOPOST = "DateToPost";
  public static final String SORT_CRITERIA_TRANSFERTYPE = "TransactionType";
  public static final String SORT_CRITERIA_FROMACCOUNT = "FromAcctId";
  public static final String SORT_CRITERIA_TOACCOUNT = "ToAcctId";
  public static final String SORT_CRITERIA_FREQUENCY = "Frequency";
  public static final String SORT_CRITERIA_STATUS = "Status";
  public static final String SORT_CRITERIA_AMOUNT = "Amount";
  public static final String SORT_CRITERIA_TOAMOUNT = "ToAmount";
  public static final String SORT_CRITERIA_DESTINATION = "Destination";
  public static final String SORT_CRITERIA_TEMPLATE_NICKNAME = "TemplateName";
  public static final String SORT_CRITERIA_SUBMITDATE = "SubmitDate";
  public static final String TRANSFER_STATUS_PENDING = "TRANSFER_STATUS_PENDING";
  public static final String TRANSFER_STATUS_COMPLETED = "TRANSFER_STATUS_COMPLETED";
  public static final String TRANSFER_STATUS_APPROVAL = "TRANSFER_STATUS_APPROVAL";
  public static final String TRANSFER_STATUS_TEMPLATE = "TRANSFER_STATUS_TEMPLATE";
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
  public static final String SEARCH_CRITERIA_CATEGORY_LIST = "CategoryList";
  public static final String SEARCH_CRITERIA_FROM_ACCOUNT = "FromAcct";
  public static final String SEARCH_CRITERIA_TO_ACCOUNT = "ToAcct";
  public static final String SEARCH_CRITERIA_AMOUNT = "Amount";
  public static final String SEARCH_CRITERIA_CURRENCY = "Currency";
  public static final String BPW_DATE_FORMAT_EXTENSION = "BPW_DATE_FORMAT_EXTENSION";
  public static final String SINGLE_TRANSFER_TEMPLATE_LIST = "SingleTransferTemplateList";
  public static final String MULTIPLE_TRANSFER_TEMPLATE_LIST = "MultipleTransferTemplateList";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransferDefines
 * JD-Core Version:    0.7.0.1
 */