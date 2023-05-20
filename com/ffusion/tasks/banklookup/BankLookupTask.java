package com.ffusion.tasks.banklookup;

import com.ffusion.tasks.Task;

public abstract interface BankLookupTask
  extends Task
{
  public static final String FINANCIAL_INSTITUTIONS = "FinancialInstitutions";
  public static final String FINANCIAL_INSTITUTION = "FinancialInstitution";
  public static final String FINANCIAL_INSTITUTION_TO_MODIFY = "FinancialInstitutiontoModify";
  public static final String COUNTRY_LIST = "CountryList";
  public static final String NONE = "None";
  public static final String UNKNOWN = "Unknown";
  public static final int TASK_ERROR_MISSING_BANK_ID = 32000;
  public static final int TASK_ERROR_FINANCIAL_INSTITUTION_TO_MODIFY_NOT_FOUND_IN_SESSION = 32001;
  public static final int TASK_ERROR_FAILED_TO_INITIALIZE_FINANCIAL_INSTITUTION = 32002;
  public static final int TASK_ERROR_NO_BANK_NAME = 32003;
  public static final int TASK_ERROR_INVALID_BANK_NAME = 32004;
  public static final int TASK_ERROR_NO_CITY = 32005;
  public static final int TASK_ERROR_INVALID_CITY = 32006;
  public static final int TASK_ERROR_ADDRESS1 = 32007;
  public static final int TASK_ERROR_ADDRESS2 = 32008;
  public static final int TASK_ERROR_ADDRESS3 = 32009;
  public static final int TASK_ERROR_NO_STATE = 32010;
  public static final int TASK_ERROR_NO_COUNTRY = 32011;
  public static final int TASK_ERROR_POSTALCODE = 32012;
  public static final int TASK_ERROR_PHONE = 32013;
  public static final int TASK_ERROR_INVALID_ROUTING_NUMBER = 32014;
  public static final int TASK_ERROR_INVALID_CHIPSUID = 32015;
  public static final int TASK_ERROR_INVALID_NATIONALID = 32016;
  public static final int TASK_ERROR_INVALID_SWIFTBIC = 32017;
  public static final int TASK_ERROR_FINANCIAL_INSTITUTIONS_NOT_FOUND_IN_SESSION = 32018;
  public static final int TASK_ERROR_FAILED_TO_SET_FINANCIAL_INSTITUTION = 32019;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banklookup.BankLookupTask
 * JD-Core Version:    0.7.0.1
 */