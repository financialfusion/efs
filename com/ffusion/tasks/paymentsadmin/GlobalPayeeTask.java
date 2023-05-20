package com.ffusion.tasks.paymentsadmin;

import com.ffusion.tasks.Task;

public abstract interface GlobalPayeeTask
  extends Task
{
  public static final String PAYEE = "Payee";
  public static final String PAYEES = "Payees";
  public static final String PAYEES_SEARCH_LIST = "PayeesSearchList";
  public static final String PAYEE_GROUPS = "PayeeGroups";
  public static final String PAYEE_GROUP = "IDScope";
  public static final int ERROR_PAYEENAME = 4100;
  public static final int ERROR_NO_PAYEE_FOUND = 4101;
  public static final int TASK_ERROR_MISSING_PAYEE_NAME = 100400;
  public static final int TASK_ERROR_MISSING_PAYEE_GROUP = 100401;
  public static final int TASK_ERROR_MISSING_PAYEE_ADDRESS = 100402;
  public static final int TASK_ERROR_MISSING_PAYEE_CITY = 100403;
  public static final int TASK_ERROR_MISSING_PAYEE_ZIPCODE = 100404;
  public static final int TASK_ERROR_MISSING_PAYEE_BANK_IDENTIFIER = 100405;
  public static final int TASK_ERROR_MISSING_PAYEE_ACCOUNT_NUMBER = 100406;
  public static final int TASK_ERROR_INVALID_ROUTING_NUMBER = 100407;
  public static final int TASK_ERROR_PAYEE_NOT_FOUND_IN_SESSION = 100408;
  public static final int TASK_ERROR_INVALID_BANK_IDENTIFIER = 100409;
  public static final int TASK_ERROR_MISSING_PAYEE_ROUTING_NUMBER = 100410;
  public static final String NONE = "NONE";
  public static final String PREFERRED_COUNTRY = "UNITED STATES";
  public static final String PREFERRED_COUNTRY_CODE = "USA";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.GlobalPayeeTask
 * JD-Core Version:    0.7.0.1
 */