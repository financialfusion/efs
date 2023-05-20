package com.ffusion.tasks.admin;

import com.ffusion.tasks.Task;

public abstract interface AdminTask
  extends Task
{
  public static final int MAX_ENT_GROUP_NAME_LENGTH = 255;
  public static final String ACCOUNT = "Account";
  public static final String ADMIN = "Admin";
  public static final String ADMINISTRATORS = "Administrators";
  public static final String CATEGORY_NAME = "CorpAdmin";
  public static final String ENTITLEMENT_GROUP = "EntitlementGroup";
  public static final String ENTITLEMENTGROUPMEMBER = "EntitlementGroupMember";
  public static final String ENTITLEMENTS = "Entitlements";
  public static final String GROUPACCOUNTS = "GroupAccounts";
  public static final String GROUPACCOUNTGROUPS = "GroupAccountGroups";
  public static final String CHECKGROUPACCOUNTS = "ACCOUNTS";
  public static final String CHECKACCOUNTID = "AccountID";
  public static final String LAST_REQUEST = "LastRequest";
  public static final String GROUPACHCOMPANIES = "GroupACHCompanies";
  public static final String ENTITLEMENT_TYPES = "EntitlementTypes";
  public static final String LIMITS = "Limits";
  public static final String LIMITABLE_OPERATION_NAMES = "LimitableOperationNames";
  public static final String NONLIMITABLE_OPERATION_NAMES = "NonlimitableOperationNames";
  public static final String NONACCOUNT_OPERATION_NAMES = "NonAccountOperationNames";
  public static final String ENTITLEMENTS_DISPLAYED_NAMES = "EntitlementsDisplayedNames";
  public static final String REPORT_ACCOUNTS = "RptAccounts";
  public static final String REPORT_AUDIT_LOG_RECORDS = "RptAuditLogRecords";
  public static final String REPORT_BUSINESS_AGENTS = "RptBusinessAgents";
  public static final String REPORT_BUSINESS_EMPLOYEES = "RptBusinessEmployees";
  public static final String REPORT_CRITERIA = "ReportCriteria";
  public static final String REPORT_DATA = "ReportData";
  public static final String REPORT_ENTITLEMENT_PERMISSIONS = "RptPermissions";
  public static final String REPORT_LIMIT_PERIOD_MAP = "RptLimitPeriodMap";
  public static final String TRANSACTION_TYPES = "TransactionTypes";
  public static final String DISPLAY_NAMES = "DisplayNames";
  public static final String HIDDEN = "Hidden";
  public static final String SECTION = "Section";
  public static final String CONTEXT = "Context";
  public static final String SECTION_BUSINESS = "Business";
  public static final String SECTION_COMPANY = "Company";
  public static final String SECTION_DIVISION = "Divisions";
  public static final String SECTION_GROUP = "Groups";
  public static final String SECTION_USER = "Users";
  public static final String COMPONENT_NAMES_SESSION = "ComponentNames";
  public static final String LOCALIZED_COMPONENT_NAMES_SESSION = "LocalizedComponentNames";
  public static final String ACH_COMPANY_ID = "ACHCompanyID";
  public static final String ACH_COMPANY_NAME = "ACHCompanyName";
  public static final String BPW_ID = "BillPayWarehouseID";
  public static final int TASK_ERROR_GETTING_ENTITLEMENTGROUPMEMBER = 4500;
  public static final int TASK_ERROR_GETTING_BUSINESS_EMPLOYEE = 4501;
  public static final int TASK_ERROR_GETTING_BUSINESS = 4502;
  public static final int TASK_ERROR_INVALID_SOURCE_PAGE = 4503;
  public static final int TASK_ERROR_GETTING_ENTITLEMENTS = 4504;
  public static final int TASK_ERROR_GETTING_LIMITS = 4505;
  public static final int TASK_ERROR_GETTING_ACCOUNT = 4506;
  public static final int TASK_ERROR_GETTING_ENTITLEMENT_TYPES = 4507;
  public static final int TASK_ERROR_GETTING_ENTITLEMENT_GROUP = 4508;
  public static final int TASK_ERROR_GETTING_OPERATION_NAMES = 4509;
  public static final int TASK_ERROR_GETTING_REPORT_CRITERIA = 4510;
  public static final int TASK_ERROR_GETTING_ACCOUNT_IDS = 4511;
  public static final int TASK_ERROR_INVALID_BANKID = 4512;
  public static final int TASK_ERROR_INVALID_GROUPID = 4513;
  public static final int TASK_ERROR_GETTING_NEW_BUSINESS_EMPLOYEE = 4514;
  public static final int TASK_ERROR_GETTING_OLD_BUSINESS_EMPLOYEE = 4515;
  public static final int TASK_ERROR_GETTING_ENTITLEMENT_OBJECTID = 4516;
  public static final int TASK_ERROR_LIMITS_LESS_RESTRICTIVE = 4517;
  public static final int TASK_ERROR_LIMITS_INVALID_NUMBER_FORMAT = 4518;
  public static final int TASK_ERROR_INVALID_ACH_COMPANY_INFO = 4519;
  public static final int TASK_ERROR_INVALID_BUSINESS_DIRECTORY_ID = 4523;
  public static final int TASK_ERROR_NO_ENTITLEMENT_GROUP = 4524;
  public static final int TASK_ERROR_ENTITLEMENT_GROUP_IN_USE = 4525;
  public static final int TASK_ERROR_USER_IN_USE = 4526;
  public static final int TASK_ERROR_ENTITLEMENT_GROUP_HAS_MEMBERS = 4527;
  public static final int TASK_ERROR_ENTITLEMENT_GROUP_HAS_CHILDREN = 4528;
  public static final int TASK_ERROR_INVALID_CURRENCY = 4529;
  public static final int TASK_ERROR_INVALID_TRANSACTION_CURRENCY = 4530;
  public static final int TASK_ERROR_INVALID_DAY_CURRENCY = 4531;
  public static final int TASK_ERROR_INVALID_WEEK_CURRENCY = 4532;
  public static final int TASK_ERROR_INVALID_MONTH_CURRENCY = 4533;
  public static final int TASK_ERROR_DAY_LESS_TRANS = 4534;
  public static final int TASK_ERROR_WEEK_LESS_TRANS = 4535;
  public static final int TASK_ERROR_WEEK_LESS_DAY = 4536;
  public static final int TASK_ERROR_MONTH_LESS_TRANS = 4537;
  public static final int TASK_ERROR_MONTH_LESS_DAY = 4538;
  public static final int TASK_ERROR_MONTH_LESS_WEEK = 4539;
  public static final int TASK_ERROR_INVALID_BLANK_NAME = 4540;
  public static final int TASK_ERROR_CANNOT_DELETE_CURRENT_USER = 4541;
  public static final int TASK_ERROR_DUPLICATE_GROUP_NAME = 4542;
  public static final int ERROR_LIMIT_DATA_NOT_NUMERIC = 4543;
  public static final int TASK_ERROR_INVALID_NAME = 4544;
  public static final int TASK_ERROR_ENT_GROUP_NAME_TOO_LONG = 4545;
  public static final int TASK_ERROR_DIVISION_HAS_LOCATIONS = 4546;
  public static final int TASK_ERROR_INVALID_ADMIN_LIST = 4547;
  public static final int TASK_ERROR_PARENT_CHILD_CHECK_FAILED = 4548;
  public static final int TASK_ERROR_GETTING_ENT_TYPE_PROP_LISTS = 4549;
  public static final int TASK_ERROR_TRANSACTION_MAP_NOT_SET = 4550;
  public static final int TASK_ERROR_DAY_MAP_NOT_SET = 4551;
  public static final int TASK_ERROR_WEEK_MAP_NOT_SET = 4552;
  public static final int TASK_ERROR_MONTH_MAP_NOT_SET = 4553;
  public static final int TASK_ERROR_NO_ACCOUNT_ID = 4554;
  public static final int TASK_ERROR_NO_LOCATION_BPW_ID = 4555;
  public static final int TASK_ERROR_WIRE_TEMPLATES_LIST_NOT_SET = 4556;
  public static final int TASK_ERROR_NO_WIRE_TEMPLATES_LIST = 4557;
  public static final int TASK_ERROR_NO_LIMIT_INFO = 4558;
  public static final int TASK_ERROR_NO_ACCOUNT_GROUP_ID = 4559;
  public static final int TASK_ERROR_MISSING_SECTION = 4560;
  public static final int TASK_ERROR_MISSING_CONTEXT = 4561;
  public static final int TASK_ERROR_DEL_USER_IS_A_LONE_ADMIN = 4562;
  public static final int TASK_ERROR_MOVE_USER_IS_A_LONE_ADMIN = 4563;
  public static final int TASK_ERROR_INACTIVE_USER_IS_A_LONE_ADMIN = 4564;
  public static final String PERMISSIONS_PAGE = "Permissions";
  public static final String ACH_PERMISSIONS_PAGE = "ACHPermissions";
  public static final String LIMITS_PAGE = "Limits";
  public static final String USERINFO_PAGE = "UserInfo";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.AdminTask
 * JD-Core Version:    0.7.0.1
 */