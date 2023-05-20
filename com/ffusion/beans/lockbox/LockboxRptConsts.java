package com.ffusion.beans.lockbox;

public abstract interface LockboxRptConsts
{
  public static final String RPT_TYPE_DEPOSIT_ITEM_SEARCH = "DepositItemSearch";
  public static final String RPT_TYPE_LOCKBOX_DEPOSIT_REPORT = "LockboxDepositReport";
  public static final String RPT_TYPE_LOCKBOX_SUMMARY = "LockboxSummary";
  public static final String SEARCH_CRITERIA_LOCKBOX_NUM = "LockboxNumber";
  public static final String SEARCH_CRITERIA_VALUE_ALL_LOCKBOXES = "ALL";
  public static final String SEARCH_CRITERIA_VALUE_ACCOUNT_BANKID_SEPERATOR = ":";
  public static final String SEARCH_CRITERIA_PAYOR = "Payor";
  public static final String SEARCH_CRITERIA_START_CHECK_NUMBER = "StartCheckNumber";
  public static final String SEARCH_CRITERIA_END_CHECK_NUMBER = "EndCheckNumber";
  public static final String SEARCH_CRITERIA_AMOUNT_MIN = "MinimumAmount";
  public static final String SEARCH_CRITERIA_AMOUNT_MAX = "MaximumAmount";
  public static final String SEARCH_CRITERIA_DOC_TYPE = "documentType";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION = "DataClassification";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION_PREVIOUSDAY = "P";
  public static final String SEARCH_CRITERIA_DATA_CLASSIFICATION_INTRADAY = "I";
  public static final String SEARCH_CRITERIA_COUPON_ACC_NUM_START = "StartCouponAccountNumber";
  public static final String SEARCH_CRITERIA_COUPON_ACC_NUM_END = "EndCouponAccountNumber";
  public static final String SEARCH_CRITERIA_COUPON_AMOUNT1_MIN = "MinimumCouponAmount1";
  public static final String SEARCH_CRITERIA_COUPON_AMOUNT1_MAX = "MaximumCouponAmount1";
  public static final String SEARCH_CRITERIA_COUPON_AMOUNT2_MIN = "MinimumCouponAmount2";
  public static final String SEARCH_CRITERIA_COUPON_AMOUNT2_MAX = "MaximumCouponAmount2";
  public static final String SEARCH_CRITERIA_COUPON_DATE1_START = "StartCouponDate1";
  public static final String SEARCH_CRITERIA_COUPON_DATE1_END = "EndCouponDate1";
  public static final String SEARCH_CRITERIA_COUPON_DATE2_START = "StartCouponDate2";
  public static final String SEARCH_CRITERIA_COUPON_DATE2_END = "EndCouponDate2";
  public static final String SEARCH_CRITERIA_COUPON_REF_NUM_START = "StartCouponReferenceNumber";
  public static final String SEARCH_CRITERIA_COUPON_REF_NUM_END = "EndCouponReferenceNumber";
  public static final String SEARCH_CRITERIA_CHECK_ROUTING_NUM_START = "StartCheckRoutingNumber";
  public static final String SEARCH_CRITERIA_CHECK_ROUTING_NUM_END = "EndCheckRoutingNumber";
  public static final String SEARCH_CRITERIA_CHECK_ACC_NUM_START = "StartCheckAccountNumber";
  public static final String SEARCH_CRITERIA_CHECK_ACC_NUM_END = "EndCheckAccountNumber";
  public static final String SEARCH_CRITERIA_LOCKBOX_WORK_TYPE = "lockboxWorkType";
  public static final String SEARCH_CRITERIA_LOCKBOX_BATCH_NUM_START = "StartLockboxBatchNumber";
  public static final String SEARCH_CRITERIA_LOCKBOX_BATCH_NUM_END = "EndLockboxBatchNumber";
  public static final String SEARCH_CRITERIA_LOCKBOX_SEQ_NUM_START = "StartLockboxSequenceNumber";
  public static final String SEARCH_CRITERIA_LOCKBOX_SEQ_NUM_END = "EndLockboxSequenceNumber";
  public static final String SORT_CRITERIA_ACCOUNT_NUMBER = "AccountNumber";
  public static final String SORT_CRITERIA_PROCESS_DATE = "ProcessingDate";
  public static final String SORT_CRITERIA_PAYOR = "Payor";
  public static final String SORT_CRITERIA_CHECK_NUM = "CheckNumber";
  public static final String SORT_CRITERIA_DOC_TYPE = "documentType";
  public static final String SORT_CRITERIA_COUPON_ACC_NUM = "couponAccountNumber";
  public static final String SORT_CRITERIA_COUPON_AMOUNT1 = "couponAmount1";
  public static final String SORT_CRITERIA_COUPON_AMOUNT2 = "couponAmount2";
  public static final String SORT_CRITERIA_COUPON_DATE1 = "couponDate1";
  public static final String SORT_CRITERIA_COUPON_DATE2 = "couponDate2";
  public static final String SORT_CRITERIA_COUPON_REF_NUM = "couponReferenceNumber";
  public static final String SORT_CRITERIA_CHECK_ROUTING_NUM = "checkRoutingNumber";
  public static final String SORT_CRITERIA_CHECK_ACC_NUM = "checkAccountNumber";
  public static final String SORT_CRITERIA_LOCKBOX_WORK_TYPE = "lockboxWorkType";
  public static final String SORT_CRITERIA_LOCKBOX_BATCH_NUM = "lockboxBatchNumber";
  public static final String SORT_CRITERIA_LOCKBOX_SEQ_NUM = "lockboxSequenceNumber";
  public static final String SORT_CRITERIA_CHECK_AMOUNT = "checkAmount";
  public static final String SORT_CRITERIA_LOCKBOX_NUMBER = "LockboxNumber";
  public static final int PAYOR_MAXLENGTH = 80;
  public static final int CHECK_NUMBER_MAXLENGTH = 40;
  public static final int DOC_TYPE_MAXLENGTH = 40;
  public static final int COUPON_ACC_NUM_MAXLENGTH = 40;
  public static final int COUPON_REF_NUM_MAXLENGTH = 40;
  public static final int CHECK_ROUTING_NUM_MAXLENGTH = 40;
  public static final int CHECK_ACC_NUM_MAXLENGTH = 40;
  public static final int LOCKBOX_WORK_TYPE_MAXLENGTH = 40;
  public static final int LOCKBOX_BATCH_NUM_MAXLENGTH = 40;
  public static final int LOCKBOX_SEQ_NUM_MAXLENGTH = 40;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxRptConsts
 * JD-Core Version:    0.7.0.1
 */