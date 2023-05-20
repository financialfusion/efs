package com.ffusion.tasks.accounts;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String ACCOUNT = "Account";
  public static final String ACCOUNTS = "Accounts";
  public static final String ORIGINAL_ACCOUNTS = "OriginalAccounts";
  public static final String PRIMARY_ACCOUNTS = "PrimaryAccounts";
  public static final String SECONDARY_ACCOUNTS = "SecondaryAccounts";
  public static final String DELTA_ACCOUNTS = "DeltaAccounts";
  public static final String BACKEND_ACCOUNTS = "BackendAccounts";
  public static final String ACCOUNT_SERVICE = "com.ffusion.service.AccountService";
  public static final String FILTERED_ACCOUNTS = "FilteredAccounts";
  public static final String RESTRICTED_CALCULATIONS = "RestrictedCalculations";
  public static final String NEW_RESTRICTED_CALCULATIONS = "NewRestrictedCalculations";
  public static final int ERROR_NO_ACCOUNT_SERVICE = 18000;
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 18001;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 18002;
  public static final int ERROR_INVALID_ACCOUNT_ID = 18003;
  public static final int ERROR_NO_ACCOUNT_FOUND = 18004;
  public static final int ERROR_NO_ACCOUNTS_NAME = 18005;
  public static final int ERROR_NO_ACCOUNTS_IN_SESSION = 18006;
  public static final int ERROR_NO_ACCOUNT_IN_SESSION = 18007;
  public static final int ERROR_NO_BANKID_IN_SEARCHACCOUNT = 18008;
  public static final int ERROR_MISSING_ACCOUNT_ID = 18009;
  public static final int ERROR_MISSING_BANK_ID = 18010;
  public static final int ERROR_MISSING_ROUTING_NUMBER = 18011;
  public static final int ERROR_MISSING_DIRECTORY_ID = 18012;
  public static final int ERROR_INVALID_ROUTING_NUMB = 18013;
  public static final int ERROR_INVALID_ACCOUNT_NUMBER = 18014;
  public static final int ERROR_NO_ACCOUNT_SELECTED = 18015;
  public static final int ERROR_DUPLICATE_ACCOUNTS = 18016;
  public static final int ERROR_CONFIRM_ACCOUNT_DOESNT_MATCH = 18017;
  public static final int ERROR_PLEASE_ENTER_CONFIRM_ACCOUNT = 18018;
  public static final int ERROR_PLEASE_ENTER_ACCOUNT_NUMBER = 18019;
  public static final int ERROR_ACCOUNT_NUMBER_BAD_LENGTH = 18020;
  public static final int ERROR_MISSING_BANK_NAME = 18021;
  public static final int TASK_ERROR_INVALID_SWIFT_BIC = 18022;
  public static final int ERROR_ACCOUNT_NUMBER_BAD_LENGTH_17 = 18023;
  public static final String ENTITLEMENT_GETACCOUNTS = "GetAccounts";
  public static final String ENTITLEMENT_MODIFY_ACCOUNT = "ModifyAccount";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.Task
 * JD-Core Version:    0.7.0.1
 */