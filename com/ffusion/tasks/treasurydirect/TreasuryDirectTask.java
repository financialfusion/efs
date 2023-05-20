package com.ffusion.tasks.treasurydirect;

public abstract interface TreasuryDirectTask
{
  public static final int ERROR_NO_ROUTING_NUMB = 80100;
  public static final int ERROR_NO_ACCOUNT_ID = 80101;
  public static final int ERROR_NO_SUB_ACCOUNTS_IN_SESSION = 80102;
  public static final int ERROR_INVALID_LOCATION_ID = 80103;
  public static final int ERROR_NO_DIRECTORY_ID = 80104;
  public static final int ERROR_NO_ACCOUNTS_TO_DELETE = 80105;
  public static final int ERROR_NO_AVAIALABLE_ACCOUNTS = 80106;
  public static final int ERROR_NO_SELECTED_ACCOUNTS_CRITERION = 80107;
  public static final int ERROR_INVALID_SELECTED_ACCOUNTS_CRITERION = 80108;
  public static final int ERROR_NO_ACCOUNTS_SELECTED_AS_MASTERS = 80109;
  public static final int ERROR_MISSING_LOCATION_ID = 80110;
  public static final int ERROR_DUPLICATE_LOCATION_ID_FOUND = 80111;
  public static final int ERROR_NO_ACCOUNTS_SELECTED_AS_SUBACCOUNTS = 80112;
  public static final String SUBACCOUNT_COLLECTION_NAME = "SubAccountAddCollection";
  public static final String ACCOUNT_COLLECTION_NAME = "ListAccountsForMasterAccountAdd";
  public static final String ADD_MASTER_COLLECTION_NAME = "MasterAccountAddCollection";
  public static final String EXISTING_SUBACCOUNT_COLLECTION_NAME = "ListSubAccountsForMasterAccountEdit";
  public static final String LIST_SUBACCOUNT_COLLECTION_NAME = "ListAccountsForSubAccountAdd";
  public static final String SEARCH_ACCOUNT_COLLECTION_NAME = "SearchAccountsCollection";
  public static final String BUSINESS_SESSION_NAME = "Business";
  public static final String INCLUDE_ZBA_CREDIT = "INCLUDE_ZBA_CREDIT";
  public static final String INCLUDE_ZBA_DEBIT = "INCLUDE_ZBA_DEBIT";
  public static final String WITHHOLD_NON_ZBA = "WITHHOLD_NON_ZBA_BAL_SUBS";
  public static final String ADD_MASTER = "ADD_MASTER";
  public static final String SUB_ACCOUNT_ADD = "SUB_ACCOUNT_ADD";
  public static final String LOCATION_ID = "LOCATION_ID";
  public static final String SELECTED_ACCOUNTS_CRITERIA_NAME = "currentAccounts";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.TreasuryDirectTask
 * JD-Core Version:    0.7.0.1
 */