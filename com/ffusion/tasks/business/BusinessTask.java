package com.ffusion.tasks.business;

import com.ffusion.tasks.Task;

public abstract interface BusinessTask
  extends Task
{
  public static final String BUSINESS = "Business";
  public static final String BUSINESSES = "Businesses";
  public static final String OLD_BUSINESS = "OldBusiness";
  public static final String BUSINESSES_SEARCH_LIST = "BusinessesSearchList";
  public static final String BUSINESSES_SEARCH_LIST_COUNT = "BusinessesSearchListCount";
  public static final String BUSINESSES_SEARCH_LIST_BY_PERSONAL_BANKER = "BusinessesSearchListByPersonalBanker";
  public static final String TEMP_ASSIGNED_PB_BUSINESSES = "TempAssignedPbBusinesses";
  public static final String BUSINESSES_SEARCH_LIST_BY_ACCOUNT_REP = "BusinessesSearchListByAccountRep";
  public static final String TEMP_ASSIGNED_AR_BUSINESSES = "TempAssignedArBusinesses";
  public static final String MARKET_SEGMENT = "MarketSegment";
  public static final String BUSINESS_MARKET_SEGMENT = "BusinessMarketSegment";
  public static final String OLD_MARKET_SEGMENT = "OldMarketSegment";
  public static final String MARKET_SEGMENTS = "MarketSegments";
  public static final String SERVICES_PACKAGE = "ServicesPackage";
  public static final String BUSINESS_SERVICES_PACKAGE = "BusinessServicesPackage";
  public static final String OLD_SERVICES_PACKAGE = "OldServicesPackage";
  public static final String SERVICES_PACKAGES = "ServicesPackages";
  public static final String BUSINESSES_BY_MARKET_SEGMENT = "BusinessesByMarketSegment";
  public static final String BUSINESSES_BY_SERVICES_PACKAGE = "BusinessesByServicesPackage";
  public static final String USERS_BY_MARKET_SEGMENT = "UsersByMarketSegment";
  public static final String USERS_BY_SERVICES_PACKAGE = "UsersByServicesPackage";
  public static final String MARKET_SEGMENT_FEATURES = "MarketSegmentFeatures";
  public static final String SERVICES_PACKAGE_FEATURES = "ServicesPackageFeatures";
  public static final String SERVICE_FEATURES = "ServiceFeatures";
  public static final String CHARGEABLE_FEATURES = "ChargeableFeatures";
  public static final String BUSINESS_ENROLL_COMMENTS = "BusinesEnrollComments";
  public static final String BUSINESS_TRANSACTION_LIMITS = "BusinessTransactionLimits";
  public static final String SERVICES_PACKAGE_TRANSACTION_LIMITS = "ServicesPackageTransactionLimits";
  public static final String BUSINESSES_SEARCH_LIST_BY_SERVICES_PACKAGE = "BusinessesSearchListByServicesPackage";
  public static final String BUSINESSES_SEARCH_LIST_BY_MARKET_SEGMENT = "BusinessesSearchListByMarketSegment";
  public static final String BUSINESS_BANK_IDS_LIST_BY_SERVICES_PACKAGE = "BusinessBankIdsListByServicesPackage";
  public static final String LASTDIRREQ = "LastDirectoryRequest";
  public static final String LASTDIRRES = "LastDirectoryResponse";
  public static final String ENROLLMENT_COMMENTS = "EnrollmentComments";
  public static final String MARKET_SEGMENT_HISTORIES = "MarketSegmentHistories";
  public static final String SERVICES_PACKAGE_HISTORIES = "ServicesPackageHistories";
  public static final String PENDING_TRANSACTION_BUSINESSES = "PendingTransactionBusinesses";
  public static final String PENDING_TRANSACTIONS = "PendingTransactions";
  public static final String PENDING_TRANSACTION = "PendingTransaction";
  public static final String ACH_COMPANY_ID = "ACHCompanyID";
  public static final String ACH_COMPANY_NAME = "ACHCompanyName";
  public static final String BPW_ID = "BillPayWarehouseID";
  public static final int TASKERROR = -1;
  public static final int SERVICEERROR = -2;
  public static final int ERROR_BUSINESSNAME = 4100;
  public static final int ERROR_NO_BUSINESS_FOUND = 4101;
  public static final int ERROR_BUSINESS_ALREADY_EXISTS = 4102;
  public static final int ERROR_NO_BUSINESS_SERVICE = 4103;
  public static final int ERROR_NO_BUSINESS_IN_SESSION = 4104;
  public static final int ERROR_NO_BUSINESSES_IN_SESSION = 4105;
  public static final int ERROR_INVALID_STATUS = 4106;
  public static final int ERROR_BANK_ID = 4107;
  public static final int ERROR_BUSINESS_ID = 4108;
  public static final int ERROR_INVALID_MARKET_SEGMENT_NAME = 4109;
  public static final int ERROR_NO_MARKET_SEGMENTS_IN_SESSION = 4110;
  public static final int ERROR_MARKET_SEGMENT_ALREADY_EXISTS = 4111;
  public static final int ERROR_NO_MARKET_SEGMENT_IN_SESSION = 4112;
  public static final int ERROR_UNABLE_TO_DELETE_MARKET_SEGMENT = 4114;
  public static final int ERROR_NO_SERVICES_PACKAGES_IN_SESSION = 4115;
  public static final int ERROR_NO_SERVICES_PACKAGE_IN_SESSION = 4116;
  public static final int ERROR_NO_MARKET_SEGMENT_FOUND = 4117;
  public static final int ERROR_NO_SERVICES_PACKAGE_FOUND = 4118;
  public static final int ERROR_NO_SERVICES_ADMIN_GROUP_ID = 4119;
  public static final int ERROR_NO_SERVICE_FEATURES = 4120;
  public static final int ERROR_INVALID_SERVICES_PACKAGE_NAME = 4121;
  public static final int ERROR_SERVICES_PACKAGE_ALREADY_EXISTS = 4122;
  public static final int ERROR_NO_OLD_BUSINESS_IN_SESSION = 4123;
  public static final int ERROR_UNABLE_TO_DELETE_SERVICES_PACKAGE = 4124;
  public static final int ERROR_NO_TRANSACTION_LIMITS_IN_SESSION = 4125;
  public static final int ERROR_NO_EMPLOYEE_FOUND = 4126;
  public static final int ERROR_NO_PENDING_TRANSACTIONS = 4127;
  public static final int ERROR_NO_PENDING_TRANSACTION = 4128;
  public static final int ERROR_NO_RESOLUTION = 4129;
  public static final int ERROR_NO_SEARCH_VALUE = 4130;
  public static final int ERROR_APPROVAL_GROUP = 4131;
  public static final int ERROR_BUSINESS_CIF = 4132;
  public static final int ERROR_PERSONAL_CIF = 4133;
  public static final int ERROR_SERVICES_PACKAGE_ID = 4134;
  public static final int ERROR_INVALID_TRANSACTION_LIMIT = 4135;
  public static final int TASK_ERROR_INVALID_CURRENCY = 4136;
  public static final int ERROR_INVALID_ACH_COMPANY_INFO = 4137;
  public static final int ERROR_NO_AFFILIATE_BANK_ID = 4138;
  public static final int ERROR_BUSINESS_CUST_ID = 4139;
  public static final int ERROR_INVALID_TRANSACTION_GROUP_NAME = 4140;
  public static final int ERROR_NO_TYPECODES_ASSIGNED_TO_TRANSACTION_GROUP = 4141;
  public static final int ERROR_TRANSACTION_GROUP_ALREADY_EXISTS = 4142;
  public static final int ERROR_DUPLICATE_CUSTOMER_ID = 4144;
  public static final int ERROR_INVALID_CREDIT_LEAD_DAY = 4147;
  public static final int ERROR_INVALID_DEBIT_LEAD_DAY = 4148;
  public static final int ERROR_GETTING_ENT_TYPE_PROP_LISTS = 4149;
  public static final int ERROR_BUSINESS_NAME_TOO_LONG = 4150;
  public static final int ERROR_MKT_SEG_GROUP_NOT_FOUND = 4160;
  public static final int ERROR_MKT_SEG_GROUP_NOT_SPECIFIED = 4161;
  public static final int ERROR_MKT_SEG_GROUP_NOT_VALID = 4162;
  public static final int ERROR_INVALID_LOCATIONID_PLACEMENT = 4163;
  public static final int ERROR_INVALID_PRIMARY_CONTACT = 4164;
  public static final int ERROR_INVALID_SECONDARY_CONTACT = 4165;
  public static final String ADDBUSINESS = "AddBusiness";
  public static final String DEACTIVATEBUSINESS = "DeactivateBusiness";
  public static final String GETBUSINESS = "GetBusiness";
  public static final String GETBUSINESSES = "GetBusinesses";
  public static final String MODIFYBUSINESS = "ModifyBusiness";
  public static final String PURGEDEACTIVATEDBUSINESS = "PurgeDeactivatedBusiness";
  public static final String PURGEALLDEACTIVATEDBUSINESSES = "PurgeAllDeactivatedBusinesses";
  public static final String REACTIVATEBUSINESS = "ReactivateBusiness";
  public static final int MAX_TRANSACTION_GROUP_NAME_LENGTH = 1010;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.BusinessTask
 * JD-Core Version:    0.7.0.1
 */