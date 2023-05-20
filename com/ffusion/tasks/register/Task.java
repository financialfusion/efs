package com.ffusion.tasks.register;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String ACCOUNT = "Account";
  public static final String ACCOUNTS = "BankingAccounts";
  public static final String REGISTER_TRANSACTION = "RegisterTransaction";
  public static final String REGISTER_TRANSACTIONS = "RegisterTransactions";
  public static final String FUTURE_REGISTER_TRANSACTIONS = "FutureRegisterTransactions";
  public static final String REGISTER_CATEGORY = "RegisterCategory";
  public static final String REGISTER_CATEGORIES = "RegisterCategories";
  public static final String REGISTER_PAYEE = "RegisterPayee";
  public static final String REGISTER_PAYEES = "RegisterPayees";
  public static final String REGISTER_TRANSACTION_TYPES = "RegisterTransactionTypes";
  public static final String REGISTER_DISCREPANCIES = "RegisterDiscrepancies";
  public static final String SECURE_USER = "SecureUser";
  public static final String REGISTER_DEFAULT = "REG_DEFAULT";
  public static final String REGISTER_ENABLED = "REG_ENABLED";
  public static final String REGISTER_RETRIEVAL_DATE = "REG_RETRIEVAL_DATE";
  public static final String REGISTER_REPORT = "RegisterReport";
  public static final int ERROR_NO_ACCOUNT = 20001;
  public static final int ERROR_NO_ACCOUNTS = 20002;
  public static final int ERROR_NO_REGISTER_TRANSACTION = 20003;
  public static final int ERROR_NO_REGISTER_TRANSACTIONS = 20004;
  public static final int ERROR_NO_REGISTER_CATEGORY = 20005;
  public static final int ERROR_NO_REGISTER_CATEGORIES = 20006;
  public static final int ERROR_NO_REGISTER_PAYEE = 20007;
  public static final int ERROR_NO_REGISTER_PAYEES = 20008;
  public static final int ERROR_NO_SECURE_USER = 20009;
  public static final int ERROR_NO_DEFAULT_ACCOUNT = 20010;
  public static final int ERROR_NO_BEAN_IN_SESSION = 20011;
  public static final int ERROR_BEAN_NAME_NOT_SPECIFIED = 20012;
  public static final int ERROR_ACCOUNT_NOT_REGISTER_ENABLED = 20013;
  public static final int ERROR_INVALID_CATEGORY_DESCRIPTION = 20100;
  public static final int ERROR_INVALID_CATEGORY_NAME = 20101;
  public static final int ERROR_INVALID_AMOUNT = 20102;
  public static final int ERROR_INVALID_REFERENCE_NUMBER = 20103;
  public static final int ERROR_INVALID_TRANSACTION_TYPE = 20104;
  public static final int ERROR_INVALID_REGISTER_ID = 20106;
  public static final int ERROR_INVALID_CATEGORY_ID = 20107;
  public static final int ERROR_INVALID_PAYEE_NAME = 20108;
  public static final int ERROR_INVALID_USER_ID = 20109;
  public static final int ERROR_INVALID_ACCOUNT_ID = 20110;
  public static final int ERROR_INVALID_PAYEE_ID = 20111;
  public static final int ERROR_NOT_SUPPORTED = 20112;
  public static final int ERROR_INVALID_EXPORT_OPTION = 20113;
  public static final int ERROR_NO_TRANSACTION_CATEGORIES = 20114;
  public static final int ERROR_INVALID_ISSUED_DATE = 20116;
  public static final int ERROR_INVALID_REGISTER_DEFAULT = 20117;
  public static final int ERROR_INVALID_REGISTER_ENABLED = 20118;
  public static final int ERROR_INVALID_REGISTER_RETRIEVAL_DATE = 20119;
  public static final int ERROR_AMOUNTS_DO_NOT_MATCH = 20120;
  public static final int ERROR_INVALID_CATEGORY_TYPE = 20121;
  public static final int ERROR_INAVLID_RECONCILE_PERIOD = 20122;
  public static final int ERROR_TRANSACTION_DOUBLE_MATCHED = 20123;
  public static final int ERROR_DUPLICATE_CATEGORIES = 20124;
  public static final int ERROR_INVALID_RESOURCE_FILE = 20125;
  public static final int ERROR_DUPLICATE_CATEGORY_NAME = 20126;
  public static final int ERROR_DUPLICATE_PAYEE_NAME = 20127;
  public static final int ERROR_MEMO_TOO_LONG = 20128;
  public static final int ERROR_AMOUNT_TYPE_MISMATCH = 20129;
  public static final int ERROR_INVALID_AMOUNT_FOR_REGISTER_TRANSACTION = 20130;
  public static final int ERROR_INVALID_REGISTER_TRANSACTION_TYPE = 20131;
  public static final int ERROR_NO_ACCOUNT_SELECTED = 20132;
  public static final int ERROR_NEGATIVE_AMOUNT = 1050;
  public static final int ERROR_INVALID_AMOUNT_RANGE = 1051;
  public static final int ERROR_NEW_AMOUNT_DOES_NOT_MATCH_ORIGINAL_AMOUNT = 20133;
  public static final int ERROR_NEW_REGISTER_TYPE_DOES_NOT_MATCH_ORIGINAL_REGISTER_TYPE = 20134;
  public static final int ERROR_NEW_REF_NUM_DOES_NOT_MATCH_ORIGINAL_REF_NUM = 20135;
  public static final int ERROR_NEW_DATE_ISSUED_DOES_NOT_MATCH_ORIGINAL_DATE_ISSUED = 20136;
  public static final int ERROR_FINDING_ACCOUNT = 20200;
  public static final int ERROR_FINDING_REGISTER_TRANSACTION = 20201;
  public static final int ERROR_FINDING_BANK_TRANSACTION = 20202;
  public static final int ERROR_FINDING_REGISTER_CATEGORY = 20203;
  public static final int ERROR_FINDING_REGISTER_PAYEE = 20204;
  public static final int ERROR_INVALID_BEAN_IN_SESSION = 20205;
  public static final String ENTITLEMENT_ADD_BANK_TRANSACTIONS = "AddBankTransactions";
  public static final String ENTITLEMENT_ADD_REC_TRANSFER_TRANSACTIONS = "AddRecTransferTransactions";
  public static final String ENTITLEMENT_ADD_REC_PAYMENT_TRANSACTIONS = "AddRecPaymentTransactions";
  public static final String ENTITLEMENT_ADD_REGISTER_CATEGORY = "AddRegisterCategory";
  public static final String ENTITLEMENT_ADD_REGISTER_PAYEE = "AddRegisterPayee";
  public static final String ENTITLEMENT_ADD_REGISTER_TRANSACTION = "AddRegisterTransaction";
  public static final String ENTITLEMENT_AUTORECONCILE_BANK_TRANSACTIONS = "AutoReconcileBankTransactions";
  public static final String ENTITLEMENT_DELETE_REGISTER_CATEGORY = "DeleteRegisterCategory";
  public static final String ENTITLEMENT_DELETE_REGISTER_PAYEE = "DeleteRegisterPayee";
  public static final String ENTITLEMENT_DELETE_REGISTER_TRANSACTIONS_BY_TRANID = "DeleteRegisterTransactionsByTranId";
  public static final String ENTITLEMENT_DELETE_REGISTER_TRANSACTION = "DeleteRegisterTransaction";
  public static final String ENTITLEMENT_EDIT_REGISTER_ACCOUNT_DATA = "EditRegisterAccountData";
  public static final String ENTITLEMENT_EDIT_REGISTER_CATEGORY = "EditRegisterCategory";
  public static final String ENTITLEMENT_EDIT_REGISTER_PAYEE = "EditRegisterPayee";
  public static final String ENTITLEMENT_EDIT_REGISTER_TRANSACTION = "EditRegisterTransaction";
  public static final String ENTITLEMENT_GET_REGISTER_CATEGORIES = "GetRegisterCategories";
  public static final String ENTITLEMENT_GET_REGISTER_PAYEES = "GetRegisterPayees";
  public static final String ENTITLEMENT_GET_REGISTER_TRANSACTIONS = "GetRegisterTransactions";
  public static final String ENTITLEMENT_GET_REGISTER_ACCOUNTS_DATA = "GetRegisterAccountsData";
  public static final String ENTITLEMENT_REASSIGN_TRANSACTIONS_CATEGORY = "ReassignTransactionsCategory";
  public static final String ENTITLEMENT_RECONCILE_TRANSACTIONS = "ReconcileTransactions";
  public static final String ENTITLEMENT_TRANSFER_DEFAULT_CATEGORIES = "TransferDefaultCategories";
  public static final String ENTITLEMENT_UPDATE_RETRIEVAL_DATE = "UpdateRetrievalDate";
  public static final String INCOME = "INCOME";
  public static final String EXPENSE = "EXPENSE";
  public static final String BANK = "BANK";
  public static final String DEBIT = "DEBIT";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.Task
 * JD-Core Version:    0.7.0.1
 */