package com.ffusion.tasks.affiliatebank;

import com.ffusion.tasks.Task;

public abstract interface AffiliateBankTask
  extends Task
{
  public static final String AFFILIATE_BANK_SERVICE = "AffiliateBankService";
  public static final String AFFILIATE_BANKS = "AffiliateBanks";
  public static final String AFFILIATE_BANK = "AffiliateBank";
  public static final String CUTOFF_DEFINITION = "AffiliateBank_CutOff_Definition";
  public static final String RESTRICTED_CALCULATIONS = "RestrictedCalculations";
  public static final String NEW_RESTRICTED_CALCULATIONS = "NewRestrictedCalculations";
  public static final int TASK_ERROR_MISSING_BANK_ID = 25500;
  public static final int TASK_ERROR_DUPLICATE_BANK_ID = 25501;
  public static final int TASK_ERROR_MISSING_BANK_NAME = 25502;
  public static final int TASK_ERROR_MISSING_ROUTING_NUM = 25503;
  public static final int TASK_ERROR_AFFILIATE_BANK_NOT_FOUND_IN_SESSION = 25504;
  public static final int TASK_ERROR_AFFILIATE_BANKS_NOT_FOUND_IN_SESSION = 25505;
  public static final int TASK_ERROR_MISSING_BANK_DESCRIPTION = 25506;
  public static final int TASK_ERROR_INVALID_ROUTING_NUMBER = 25507;
  public static final int TASK_ERROR_MISSING_ORIGIN_ID = 25508;
  public static final int TASK_ERROR_MISSING_ORIGIN_NAME = 25509;
  public static final int TASK_ERROR_MISSING_DESTINATION_ID = 25510;
  public static final int TASK_ERROR_MISSING_DESTINATION_NAME = 25511;
  public static final int TASK_ERROR_INVALID_ORIGIN_ROUTING_NUMBER = 25512;
  public static final int TASK_ERROR_INVALID_DESTINATION_ROUTING_NUMBER = 25513;
  public static final int TASK_ERROR_INVALID_ORIGIN_NAME = 25514;
  public static final int TASK_ERROR_INVALID_DESTINATION_NAME = 25515;
  public static final int TASK_ERROR_MISSING_BANK_ID_AND_ROUTING_NUMBER = 25516;
  public static final int TASK_ERROR_MISSING_WAREHOUSING_TYPE = 25517;
  public static final int TASK_ERROR_INVALID_WAREHOUSING_TYPE = 25518;
  public static final int TASK_ERROR_MISSING_ACH_MAX_FUTURE_DAYS = 25519;
  public static final int TASK_ERROR_INVALID_ACH_MAX_FUTURE_DAYS = 25520;
  public static final int TASK_ERROR_INVALID_EXT_XFER_COMP_NAME = 25521;
  public static final int TASK_ERROR_INVALID_EXT_XFER_COMP_ID = 25522;
  public static final int TASK_ERROR_INVALID_EXT_XFER_BATCH_DESC = 25523;
  public static final int TASK_ERROR_INVALID_EXT_XFER_REQUIRED = 25524;
  public static final int TASK_ERROR_INVALID_EXT_XFER_FUND_ACCT_LEN = 25525;
  public static final int TASK_ERROR_INVALID_EXT_XFER_FUND_ACCT = 25526;
  public static final int TASK_ERROR_INVALID_MIN_MAX_AMOUNTS = 25527;
  public static final int TASK_ERROR_INVALID_MAXIMUM_TOO_SMALL = 25528;
  public static final int TASK_ERROR_INVALID_ETF_DEPOSIT_AMOUNT = 25529;
  public static final int TASK_ERROR_INVALID_SWIFT_BIC = 25530;
  public static final int TASK_ERROR_CUTOFF_READING_SESSION = 25550;
  public static final int TASK_ERROR_CUTOFF_TIME_FORMAT = 25551;
  public static final int TASK_ERROR_CUTOFF_ALERT_ERROR = 25552;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.AffiliateBankTask
 * JD-Core Version:    0.7.0.1
 */