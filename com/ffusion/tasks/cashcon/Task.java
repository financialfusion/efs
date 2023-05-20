package com.ffusion.tasks.cashcon;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String CONCENTRATION = "CONCENTRATION";
  public static final String DISBURSEMENT = "DISBURSEMENT";
  public static final String CASHCON = "CashCon";
  public static final String CASHCONS = "CashCons";
  public static final String TRANSTYPELIST = "TypeList";
  public static final String NUM_PENDING_CASH_CON_TRANSACTIONS = "NumPendingCashConTransactions";
  public static final int ERROR_NO_CASHCON = 24000;
  public static final int ERROR_NO_CASHCONS = 24001;
  public static final int ERROR_NO_CASHCONCOMPANY = 24002;
  public static final int ERROR_NO_CASHCONCOMPANIES = 24003;
  public static final int ERROR_NO_FIID = 24004;
  public static final int ERROR_NO_LOCATION = 24005;
  public static final int ERROR_NO_LOCATIONS = 24006;
  public static final int ERROR_AMOUNT = 24007;
  public static final int ERROR_AMOUNT_TOO_SMALL = 24008;
  public static final int ERROR_AMOUNT_TOO_LARGE = 24009;
  public static final int ERROR_NO_DIVISION = 24010;
  public static final int ERROR_NO_BANKS = 24011;
  public static final String REPORT_DATA = "ReportData";
  public static final int ERROR_INVALID_COMPANY_NAME = 24100;
  public static final int ERROR_INVALID_COMPANY_ID = 24101;
  public static final int ERROR_INVALID_FUNDS_CONCENTRATION_ACCOUNTS = 24102;
  public static final int ERROR_INVALID_FUNDS_CONCENTRATION_CUT_OFFS = 24103;
  public static final int ERROR_INVALID_FUNDS_DISBURSEMENT_ACCOUNTS = 24104;
  public static final int ERROR_INVALID_FUNDS_DISBURSEMENT_CUT_OFFS = 24105;
  public static final int ERROR_MISSING_DEFAULT_CONCENTRATION_ACCOUNT = 24106;
  public static final int ERROR_MISSING_DEFAULT_DISBURSEMENT_ACCOUNT = 24107;
  public static final int ERROR_INVALID_DEPOSIT_MINIMUM = 24108;
  public static final int ERROR_INVALID_DEPOSIT_MAXIMUM = 24109;
  public static final int ERROR_INVALID_ANTICIPATORY_AMOUNT = 24110;
  public static final int ERROR_INVALID_THRESHOLD_DEPOSIT_AMOUNT = 24111;
  public static final int ERROR_INVALID_AFFILIATE_BANK = 24112;
  public static final int ERROR_INVALID_ACCOUNT = 24113;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.Task
 * JD-Core Version:    0.7.0.1
 */