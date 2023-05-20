package com.ffusion.beans.cashcon;

public abstract interface CashConDefines
{
  public static final int DEFAULT_PAGE_SIZE = 10;
  public static final String BPW_DATEFORMAT = "yyyyMMdd";
  public static final String CASHCON_STATUS = "CASHCON_STATUS";
  public static final String CASHCON_STATUS_PENDING = "CASHCON_STATUS_PENDING";
  public static final String CASHCON_STATUS_COMPLETED = "CASHCON_STATUS_COMPLETED";
  public static final String CASHCON_STATUS_APPROVAL = "CASHCON_STATUS_APPROVAL";
  public static final String CASHCON_VIEW = "ACH_VIEW";
  public static final String CASHCON_VIEW_ALL = "ALL";
  public static final String CASHCON_TRAN_TYPE = "CASHCON_TRAN_TYPE";
  public static final String CASHCON_TRAN_TYPE_DEPOSIT = "Deposit";
  public static final String CASHCON_TRAN_TYPE_DISBURSE = "Disbursement";
  public static final String CASHCON_LOCATION_RESULTS = "LocationResults";
  public static final String SORT_CRITERIA_DUEDATE = "DueDate";
  public static final String SORT_CRITERIA_DIVISION = "Division";
  public static final String SORT_CRITERIA_LOCATION = "Location";
  public static final String SORT_CRITERIA_TYPE = "Type";
  public static final String SORT_CRITERIA_STATUS = "Status";
  public static final String SORT_CRITERIA_AMOUNT = "Amount";
  public static final String SEARCH_CRITERIA_CUSTOMERID = "CustomerId";
  public static final String SEARCH_CRITERIA_SUBMITTEDBY = "SubmittedBys";
  public static final String SEARCH_CRITERIA_TRANSTYPE = "TransType";
  public static final String SEARCH_CRITERIA_STATUS_LIST = "StatusList";
  public static final String SEARCH_CRITERIA_LOCATION_LIST = "LocationIds";
  public static final String SEARCH_CRITERIA_SELECTION = "SelectionCriteria";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConDefines
 * JD-Core Version:    0.7.0.1
 */