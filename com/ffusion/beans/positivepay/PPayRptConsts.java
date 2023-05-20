package com.ffusion.beans.positivepay;

public abstract interface PPayRptConsts
{
  public static final String RPT_TYPE_DECISION_HISTORY = "Decision History";
  public static final String RPT_TYPE_ISSUE_SUMMARY = "Exceptions Summary";
  public static final String RPT_TYPE_ACTIVITY = "Positive Pay Activity";
  public static final String RPT_TYPE_POSITIVE_PAY_ISSUES = "Positive Pay Exceptions Report";
  public static final String RPT_TYPE_POSITIVE_PAY_DECISIONS = "Positive Pay Decisions Report";
  public static final String SEARCH_CRITERIA_ACCOUNT = "Account";
  public static final String SEARCH_CRITERIA_ACCOUNT_NUMBER = "Account number";
  public static final String SEARCH_CRITERIA_START_CHECK_NUMBER = "Start Check Number";
  public static final String SEARCH_CRITERIA_END_CHECK_NUMBER = "End Check Number";
  public static final String SEARCH_CRITERIA_MAXIMUM_AMOUNT = "Maximum Amount";
  public static final String SEARCH_CRITERIA_MINIMUM_AMOUNT = "Minimum Amount";
  public static final String SEARCH_CRITERIA_CHECK_VOID_STATUS = "Check Void";
  public static final String SEARCH_CRITERIA_REJECT_REASON = "Reject Reason";
  public static final String SEARCH_CRITERIA_DECISION = "Decision";
  public static final String SEARCH_CRITERIA_DECISION_NOT = "Decision Is Not";
  public static final String SEARCH_CRITERIA_DECISION_ALL = "All";
  public static final String SEARCH_CRITERIA_DECISION_PAY = "Pay";
  public static final String SEARCH_CRITERIA_DECISION_RETURN = "Return";
  public static final String SORT_CRITERIA_CHECK_NUMBER = "Check Number";
  public static final String SORT_CRITERIA_CHECK_DATE = "Check Date";
  public static final String SORT_CRITERIA_AMOUNT = "Amount";
  public static final String SORT_CRITERIA_REJECT_REASON = "Reject Reason";
  public static final String SORT_CRITERIA_DECISION = "Decision";
  public static final String SEARCH_CRITERIA_VALUE_ALL_ACCOUNTS = "All";
  public static final String SEARCH_CRITERIA_VALUE_ACCOUNT_SEPERATOR = ",";
  public static final String SEARCH_CRITERIA_VALUE_ACCOUNT_BANKID_SEPERATOR = ":";
  public static final String SEARCH_CRITERIA_VALUE_VOID_CHECKS = "true";
  public static final String SEARCH_CRITERIA_VALUE_NO_VOID_CHECKS = "false";
  public static final String SEARCH_CRITERIA_VALUE_ALL_CHECKS = " ";
  public static final int CHECK_NUMBER_MAXLENGTH = 20;
  public static final int DECISION_MAXLENGTH = 20;
  public static final int REJECT_REASON_MAXLENGTH = 32;
  public static final String ERRORS_ENCOUNTERED = "ERRORS_ENCOUNTERED";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayRptConsts
 * JD-Core Version:    0.7.0.1
 */