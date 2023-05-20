package com.ffusion.tasks.aggregation;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String CONTEXT_AGG_INSTITUTIONS_BANKING = "Agg_Institutions_Banking";
  public static final String CONTEXT_AGG_INSTITUTIONS_CREDITCARDS = "Agg_Institutions_CreditCards";
  public static final String CONTEXT_AGG_INSTITUTIONS_INVESTMENTS = "Agg_Institutions_Investements";
  public static final String CONTEXT_AGG_INSTITUTIONS_REWARDS = "Agg_Institutions_Rewards";
  public static final String AGGREGATION = "com.ffusion.services.AccountAggregation";
  public static final String AGGREGATEDACCOUNTS = "AccountAggregationCollection";
  public static final String AGGREGATEDACCOUNT = "AggregatedAccount";
  public static final String INSTITUTIONS = "InstitutionsCollection";
  public static final String INSTITUTION = "Institution";
  public static final String AGGACCTTRANSACTION = "AggTransaction";
  public static final String AGGACCTTRANSACTIONS = "AggTransactions";
  public static final String INSTITUTIONACCOUNTTYPES = "InstitutionAccountTypes";
  public static final String INSTITUTIONACCOUNTTYPE = "InstitutionAccountType";
  public static final String ACCOUNT_REQUIRED_FIELDS = "AccountRequiredFields";
  public static final String AGGREGATIONSUMMARY = "AggregationSummary";
  public static final int ERROR_NO_ACCOUNT_AGGREGATION_SERVICE = 11000;
  public static final int ERROR_NO_USER_AGGREGATION_SERVICE = 11001;
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 11002;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 11003;
  public static final int ERROR_INVALID_INSTITUTION_ID = 11004;
  public static final int ERROR_NO_INSTITUTIONS_FOUND = 11005;
  public static final int ERROR_INVALID_START_DATE = 11006;
  public static final int ERROR_INVALID_END_DATE = 11007;
  public static final int ERROR_NO_ACCOUNT_ID = 11008;
  public static final int ERROR_NO_ACCOUNT = 11009;
  public static final int ERROR_NO_TRANSACTIONS = 11010;
  public static final int ERROR_ALREADY_PROCESSING = 11011;
  public static final int ERROR_INVALID_INSTITUTION_ACCOUNT_TYPE_ID = 11012;
  public static final int ERROR_NO_INSTITUTION_ACCOUNT_TYPES_FOUND = 11013;
  public static final int ERROR_NO_REQUIRED_ACCOUNT_FIELDS = 11014;
  public static final int ERROR_INVALID_ACCOUNT = 11015;
  public static final int ERROR_INVAILD_NICKNAME = 11016;
  public static final int ERROR_ACCOUNT_EXISTS = 11017;
  public static final String ENTITLEMENT_ADD_ACCOUNT = "AddAccount";
  public static final String ENTITLEMENT_DELETE_ACCOUNT = "DeleteAccount";
  public static final String ENTITLEMENT_FIND_TRANSACTION = "FindTransaction";
  public static final String ENTITLEMENT_GET_ACCOUNTS = "GetAccounts";
  public static final String ENTITLEMENT_GET_INSTITUTIONS = "GetInstitutions";
  public static final String ENTITLEMENT_GET_TRANSACTIONS = "GetTransactions";
  public static final String ENTITLEMENT_MODIFY_ACCOUNT = "ModifyAccount";
  public static final String ENTITLEMENT_REFRESH_ACCOUNT = "RefreshAccount";
  public static final String ENTITLEMENT_SET_ACCOUNT = "SetAccount";
  public static final String ENTITLEMENT_SET_INSTITUTION = "SetInstitution";
  public static final String ENTITLEMENT_SET_INSTITUTION_ACCOUNT_TYPE = "SetInstitutionAccountType";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.Task
 * JD-Core Version:    0.7.0.1
 */