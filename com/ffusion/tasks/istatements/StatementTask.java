package com.ffusion.tasks.istatements;

import com.ffusion.tasks.Task;

public abstract interface StatementTask
  extends Task
{
  public static final String ISTMT_ACCTS = "StatementAccounts";
  public static final String ISTMT_ACCT = "StatementAccount";
  public static final String AVAIL_ACCTS = "AvailableAccounts";
  public static final String STATEMENTS = "Statements";
  public static final String STATEMENT = "Statement";
  public static final String STATEMENT_TRANSACTIONS = "StatementTransactions";
  public static final String STATEMENT_TRANSACTION = "StatementTransaction";
  public static final String STATEMENT_DAILY_BALANCES = "StatementDailyBalances";
  public static final String STATEMENT_MESSAGES = "StatementMessages";
  public static final String STATEMENT_DATES = "StatementDates";
  public static final String REPORT_DATA = "ReportData";
  public static final String STATEMENT_ACCEPTED = "StmtAccepted";
  public static final int ERROR_GETTING_STATEMENT = 36009;
  public static final int ERROR_WRONG_ACCT_IDENTIFIER = 36200;
  public static final int ERROR_ACCT_ENAB_MISSING_BANK_ID = 36201;
  public static final int ERROR_ACCT_ENAB_MISSING_ACCT_NO = 36202;
  public static final int ERROR_ACCT_DISAB_MISSING_BANK_ID = 36203;
  public static final int ERROR_ACCT_DISAB_MISSING_ACCT_NO = 36204;
  public static final int ERROR_NO_STATEMENT_IN_SESSION = 36205;
  public static final int ERROR_STMT_FLAG_NOT_AVAILABLE = 36206;
  public static final int ERROR_NO_TRANSACTIONS = 36207;
  public static final int ERROR_NO_TRANSACTION = 36208;
  public static final int ERROR_UNKNOWN = 36209;
  public static final int ERROR_GETTING_STMT_ACCT = 36210;
  public static final int ERROR_ACCOUNT_SERVICE_NOT_IN_SESSION = 36211;
  public static final int ERROR_STMT_NO_ACCOUNTS_FOUND = 36212;
  public static final int ERROR_MISSING_ACCOUNT_INFO = 36213;
  public static final int ERROR_MISSING_STMT_DATE_INFO = 36214;
  public static final int ERROR_MISSING_DATE_FORMAT_INFO = 36215;
  public static final int ERROR_DATE_DATE_FORMAT_MISMATCH = 36216;
  public static final int ERROR_MISSING_REPORT_BEAN = 36217;
  public static final int ERROR_MISSING_DATES_COLLECTION_NAME = 36218;
  public static final int ERROR_MISSING_REPORT_SESSION_NAME = 36219;
  public static final int ERROR_MISSING_STATEMENT_ID = 36220;
  public static final int ERROR_MISSING_STATEMENT_SESSION_NAME = 36221;
  public static final int ERROR_MISSING_TRANSACTIONS_SESSION_NAME = 36222;
  public static final int ERROR_MISSING_MESSAGES_SESSION_NAME = 36223;
  public static final int ERROR_MISSING_DAILY_BALANCE_SUMMARIES_SESSION_NAME = 36224;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.StatementTask
 * JD-Core Version:    0.7.0.1
 */