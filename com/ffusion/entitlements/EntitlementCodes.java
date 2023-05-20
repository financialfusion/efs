package com.ffusion.entitlements;

import java.io.Serializable;

public abstract interface EntitlementCodes
  extends Serializable
{
  public static final int SUCCESS_CODE = 0;
  public static final int ERROR_NOT_ENTITLED = 14000;
  public static final int ERROR_LIMITS_EXCEEDED = 14001;
  public static final int ERROR_NO_ENTITLEMENT_SERVICE_IN_BEAN = 14002;
  public static final int ERROR_NO_LIMIT_TO_ADD = 14003;
  public static final int ERROR_NO_ENTITLEMENT_SERVICE = 14100;
  public static final int ERROR_NO_ENTITLEMENTS = 14101;
  public static final int ERROR_MALFORMED_ENTITLEMENTS = 14102;
  public static final int ERROR_NO_ENTITLEMENT = 14103;
  public static final int ERROR_NO_LIMIT = 14105;
  public static final int ERROR_NO_LIMITS = 14106;
  public static final int ERROR_NO_ENTITLEMENT_NAME = 14107;
  public static final int ERROR_NO_ENTITLEMENT_FOR_THIS_OPERATION = 14108;
  public static final int ERROR_NO_TARGET_OR_SOURCE_ENTITLEMENTS = 14109;
  public static final int ERROR_NO_LIMIT_NAME = 14110;
  public static final int ERROR_NO_ENTITLEMENT_SERVICE_NAME = 14111;
  public static final int ERROR_NO_LIMIT_VALUE = 14112;
  public static final int ERROR_NO_PERIOD_TYPE = 14113;
  public static final int ERROR_NO_PERIOD = 14114;
  public static final int ERROR_NO_PAYEE_ID = 14115;
  public static final int ERROR_NO_ACCOUNT_ID = 14116;
  public static final int ERROR_MALFORMED_PERIOD_LIMIT = 14117;
  public static final int ERROR_MALFORMED_TRANSACTION_LIMIT = 14118;
  public static final int ERROR_MALFORMED_TOID_LIMIT = 14119;
  public static final int ERROR_MALFORMED_FROMID_LIMIT = 14120;
  public static final int ERROR_NOT_A_VALID_DAY = 14121;
  public static final int ERROR_NOT_A_VALID_WEEK = 14122;
  public static final int ERROR_NOT_A_VALID_MONTH = 14123;
  public static final int ERROR_NOT_A_VALID_YEAR = 14124;
  public static final int ERROR_NOT_A_VALID_PERIOD_TYPE = 14125;
  public static final int ERROR_NO_ENTITLEMENT_FOUND = 14126;
  public static final int ERROR_NO_LIMIT_FOUND = 14127;
  public static final int ERROR_NO_ENTITLEMENTS_NAME = 14128;
  public static final int ERROR_NO_USER_ID = 14129;
  public static final int ERROR_NO_USER_TYPE = 14130;
  public static final int ERROR_MALFORMED_LIMIT = 14131;
  public static final int ERROR_INVALID_GROUPID = 14132;
  public static final int ERROR_INVALID_GROUP_TYPE = 14133;
  public static final int MIN_DAY_IN_YEAR = 1;
  public static final int MAX_DAY_IN_YEAR = 366;
  public static final int MIN_MONTH_IN_YEAR = 0;
  public static final int MAX_MONTH_IN_YEAR = 11;
  public static final int MIN_WEEK_IN_YEAR = 0;
  public static final int MAX_WEEK_IN_YEAR = 54;
  public static final int MIN_YEAR = 1;
  public static final int MAX_YEAR = 5000000;
  public static final int CUSTOMER_USER_TYPE = 1;
  public static final int EMPLOYEE_USER_TYPE = 2;
  public static final int GROUP_USER_TYPE = 4;
  public static final String LIMIT_ID = "LIMIT_ID";
  public static final String LIMIT_NAME = "LIMIT_NAME";
  public static final String LIMIT_VALUE = "LIMIT_VALUE";
  public static final String PERIOD = "PERIOD";
  public static final String PAYEEID = "PAYEEID";
  public static final String ACCOUNTID = "ACCOUNTID";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.EntitlementCodes
 * JD-Core Version:    0.7.0.1
 */