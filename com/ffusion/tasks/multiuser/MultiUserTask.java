package com.ffusion.tasks.multiuser;

public class MultiUserTask
{
  public static final String SECONDARY_USERS_NAME = "SecondaryUsers";
  public static final String MODIFIED_SECONDARY_USERS_NAME = "ModifiedSecondaryUsers";
  public static final String SECONDARY_USER_NAME = "SecondaryUser";
  public static final String PRIMARY_USER_NAME = "PrimaryUser";
  public static final String CANDIDATE_STATEMENT_ACCOUNTS = "CandidateStatementAccounts";
  public static final String SECONDARY_USER_ENTITLED_CORE_ACCOUNTS = "SecondaryUserEntitledCoreAccounts";
  public static final String SECONDARY_USER_ENTITLED_EXTERNAL_ACCOUNTS = "SecondaryUserEntitledExternalAccounts";
  public static final String SECONDARY_USER_ENTITLED_REGISTER_ACCOUNTS = "SecondaryUserEntitledRegisterAccounts";
  public static final String SECONDARY_USER_ENTITLED_STATEMENT_ACCOUNTS = "SecondaryUserEntitledStatementAccounts";
  public static final String SECONDARY_USER_RESTRICTED_CORE_ACCOUNTS = "SecondaryUserRestrictedCoreAccounts";
  public static final String SECONDARY_USER_RESTRICTED_EXTERNAL_ACCOUNTS = "SecondaryUserRestrictedExternalAccounts";
  public static final String SECONDARY_USER_RESTRICTED_REGISTER_ACCOUNTS = "SecondaryUserRestrictedRegisterAccounts";
  public static final String SECONDARY_USER_RESTRICTED_STATEMENT_ACCOUNTS = "SecondaryUserRestrictedStatementAccounts";
  public static final String EXTERNAL_ACCOUNTS = "ExternalAccounts";
  public static final String ENABLE_INTERNAL_TRANSFERS = "EnableInternalTransfers";
  public static final String ENABLE_EXTERNAL_TRANSFERS = "EnableExternalTransfers";
  public static final String ENABLE_EXTERNAL_TRANSFERS_ADD_ACCOUNT = "EnableExternalTransfersAddAccount";
  public static final String ENABLE_BILL_PAYMENTS = "EnableBillPayments";
  public static final String ENABLE_MANAGE_PAYEES = "EnableManagePayees";
  public static final String MODIFY_PAYMENT_TX = "ModifyPaymentTx";
  public static final String MODIFY_REC_PAYMENT_TX = "ModifyRecPaymentTx";
  public static final String MODIFY_TRANSFER_TX = "ModifyTransferTx";
  public static final String MODIFY_REC_TRANSFER_TX = "ModifyRecTransferTx";
  public static final String MODIFY_TRANSACTIONS_TRANSFER_OR_CANCEL = "TransferOrCancelTransactions";
  public static final String MODIFY_ACTION_TRANSFER = "Transfer";
  public static final String MODIFY_ACTION_CANCEL = "Cancel";
  public static final String ENABLE_CORE_ACCOUNT = "EnableCoreAccount_";
  public static final String ENABLE_EXTERNAL_ACCOUNT = "EnableExternalAccount_";
  public static final String ENABLE_REGISTER_ACCOUNT = "EnableRegisterAccount_";
  public static final String ENABLE_EXTERNAL_REGISTER_ACCOUNT = "EnableExternalRegisterAccount_";
  public static final String ENABLE_STATEMENT_ACCOUNT = "EnableStatementAccount_";
  public static final String ACTIVATE_USER = "ActivateUser_";
  public static final int ERROR_TERMS_NOT_ACCEPTED = 28000;
  public static final int ERROR_MISSING_SECONDARY_USER_SESSION_NAME = 28001;
  public static final int ERROR_MISSING_SECONDARY_USER = 28002;
  public static final int ERROR_MISSING_SECONDARY_USER_ENTITLED_CORE_ACCOUNTS_SESSION_NAME = 28003;
  public static final int ERROR_MISSING_CANDIDATE_CORE_ACCOUNTS_SESSION_NAME = 28004;
  public static final int ERROR_MISSING_CANDIDATE_CORE_ACCOUNTS = 28005;
  public static final int ERROR_MISSING_CANDIDATE_STATEMENT_ACCOUNTS_SESSION_NAME = 28006;
  public static final int ERROR_MISSING_CANDIDATE_STATEMENT_ACCOUNTS = 28007;
  public static final int ERROR_MISSING_SECONDARY_USER_ID = 28008;
  public static final int ERROR_INVALID_LIMIT_AMOUNT = 28009;
  public static final int ERROR_MISSING_ENABLE_INTERNAL_TRANSFERS_SESSION_NAME = 28010;
  public static final int ERROR_MISSING_ENABLE_EXTERNAL_TRANSFERS_SESSION_NAME = 28011;
  public static final int ERROR_MISSING_ENABLE_BILL_PAYMENTS_SESSION_NAME = 28012;
  public static final int ERROR_MISSING_SECONDARY_USERS_SESSION_NAME = 28013;
  public static final int ERROR_MISSING_SECONDARY_USERS = 28014;
  public static final int ERROR_MISSING_SECONDARY_USER_FIRST_NAME = 28015;
  public static final int ERROR_MISSING_SECONDARY_USER_LAST_NAME = 28016;
  public static final int ERROR_MISSING_SECONDARY_USER_USER_NAME = 28017;
  public static final int ERROR_MISSING_SECONDARY_USER_PASSWORD = 28018;
  public static final int ERROR_MISMATCHED_SECONDARY_USER_PASSWORD = 28019;
  public static final int ERROR_INVALID_SECONDARY_USER_PHONE = 28020;
  public static final int ERROR_INVALID_SECONDARY_USER_EMAIL = 28021;
  public static final int ERROR_INVALID_SECONDARY_USER_ZIP_CODE = 28022;
  public static final int ERROR_MISSING_ORIGINAL_SECONDARY_USERS_SESSION_NAME = 28023;
  public static final int ERROR_MISSING_MODIFIED_SECONDARY_USERS_SESSION_NAME = 28024;
  public static final int ERROR_MISSING_FIRST_SECONDARY_USERS_SESSION_NAME = 28025;
  public static final int ERROR_MISSING_SECOND_SECONDARY_USERS_SESSION_NAME = 28026;
  public static final int ERROR_MISSING_FIRST_SECONDARY_USERS = 28027;
  public static final int ERROR_MISSING_SECOND_SECONDARY_USERS = 28028;
  public static final int ERROR_MISSING_BANKING_SERVICE = 28029;
  public static final int ERROR_MISSING_PRIMARY_USER = 28030;
  public static final int ERROR_MISSING_PRIMARY_USER_SESSION_NAME = 28031;
  public static final int ERROR_MISSING_BANKING_SERVICE_SESSION_NAME = 28032;
  public static final int ERROR_MISSING_PRIMARY_USER_ACCOUNTS_SESSION_NAME = 28033;
  public static final int ERROR_MISSING_PRIMARY_USER_ACCOUNTS = 28034;
  public static final int ERROR_MISSING_SECONDARY_USER_ENTITLED_EXTERNAL_ACCOUNTS_SESSION_NAME = 28035;
  public static final int ERROR_MISSING_SECONDARY_USER_ENTITLED_REGISTER_ACCOUNTS_SESSION_NAME = 28036;
  public static final int ERROR_MISSING_SECONDARY_USER_ENTITLED_STATEMENT_ACCOUNTS_SESSION_NAME = 28037;
  public static final int ERROR_MISSING_SECONDARY_USER_RESTRICTED_CORE_ACCOUNTS_SESSION_NAME = 28038;
  public static final int ERROR_MISSING_SECONDARY_USER_RESTRICTED_EXTERNAL_ACCOUNTS_SESSION_NAME = 28039;
  public static final int ERROR_MISSING_SECONDARY_USER_RESTRICTED_REGISTER_ACCOUNTS_SESSION_NAME = 28040;
  public static final int ERROR_MISSING_SECONDARY_USER_RESTRICTED_STATEMENT_ACCOUNTS_SESSION_NAME = 28041;
  public static final int ERROR_MISSING_CANDIDATE_EXTERNAL_ACCOUNTS = 28042;
  public static final int ERROR_MISSING_CANDIDATE_EXTERNAL_ACCOUNTS_SESSION_NAME = 28043;
  public static final int ERROR_MISSING_ENABLE_MANAGE_PAYEES_SESSION_NAME = 28044;
  public static final int ERROR_MISSING_ENABLE_EXTERNAL_TRANSFERS_ADD_ACCOUNT_SESSION_NAME = 28045;
  public static final int ERROR_MALFORMED_ACCESS_OR_LIMIT_INPUT_NAME = 28046;
  public static final int ERROR_INVALID_MAX_LIMIT_AMOUNT = 28047;
  public static final int ERROR_DUPLICATE_USER_NAME = 28048;
  public static final int ERROR_MISSING_SECONDARY_USER_CUSTOMER_ID = 28049;
  public static final int ERROR_INVALID_MODIFY_ACTION = 28050;
  public static final int ERROR_MISSING_ENTITLEMENT_GROUP_ID = 28051;
  public static final int ERROR_MISSING_SOURCE_TX_COLLECTION_SESSION_NAME = 28052;
  public static final int ERROR_MISSING_SOURCE_TX_COLLECTION = 28053;
  public static final int ERROR_MISSING_DESTINATION_TX_COLLECTION_SESSION_NAME = 28054;
  public static final int ERROR_NOT_SUPPORTED_TX_COLLECTION_TYPE = 28055;
  public static final int ERROR_NOT_SUPPORTED_TX_TYPE = 28056;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.MultiUserTask
 * JD-Core Version:    0.7.0.1
 */