package com.ffusion.tasks.bankemployee;

import com.ffusion.tasks.Task;

public abstract interface BankEmployeeTask
  extends Task
{
  public static final String BANK_EMPLOYEE = "BankEmployee";
  public static final String BANK_EMPLOYEES = "BankEmployees";
  public static final String BANK_EMPLOYEES_COUNT = "BankEmployeesCount";
  public static final String EMPLOYEE_SEARCH_LIST = "EmployeeSearchList";
  public static final String SUPERVISORS = "Supervisors";
  public static final String CSRS = "CSRs";
  public static final String PERSONAL_BANKERS = "PersonalBankers";
  public static final String APPROVAL_PROVIDERS = "ApprovalProvider";
  public static final String ACCOUNT_REPS = "AccountReps";
  public static final String BANK_EMPLOYEE_SERVICE = "BankEmployeeService";
  public static final String BANK_ID = "BankId";
  public static final String HAS_DIRECT_REPORTS = "HasDirectReports";
  public static final String CONSUMERS = "Consumers";
  public static final String ADMINISTRATOR_OF_GROUPS = "AdministratorOfGroups";
  public static final String JOB_TYPES = "JobTypes";
  public static final String EMPLOYEE_JOB_GROUPS = "EmployeeJobGroups";
  public static final String BUSINESS_APPROVAL_GROUPS = "BusinessApprovalGroups";
  public static final String SERVICES_ADMIN_GROUPS = "ServicesAdminGroups";
  public static final String MODIFIED_BANK_EMPLOYEE = "ModifiedBankEmployee";
  public static final String OLD_BANK_EMPLOYEE = "OldBankEmployee";
  public static final String EMPLOYEE_HISTORIES = "EmployeeHistories";
  public static final String EMPLOYEE_PROFILE_PROPERTIES = "EmployeeProfileProperties";
  public static final String ASSIGNED_EMPLOYEES = "AssignedEmployees";
  public static final String AFFILIATE_BANKS = "AffiliateBanks";
  public static final String ASSIGNED_BANKS = "AssignedBanks";
  public static final String QUEUES_UPDATED = "QueuesUpdated";
  public static final String PRODUCT_STATUS_UPDATED = "ProductStatusUpdated";
  public static final String ADDED_USER_NAMES = "AddedUserNames";
  public static final String REMOVED_USER_NAMES = "RemovedUserNames";
  public static final String ASSIGNED_USERS = "AssignedUsers";
  public static final String TEMP_ASSIGNED_USERS = "TempAssignedUsers";
  public static final String ASSIGNED_PRODUCTS = "AssignedProducts";
  public static final String EMPLOYEE_NOTIFY = "EmployeeNotify";
  public static final String USER = "User";
  public static final String USERS = "Users";
  public static final String OLD_USER = "OldUser";
  public static final String USER_HISTORIES = "UserHistories";
  public static final String HISTORY_ADD = "Add";
  public static final String HISTORY_MODIFY = "Modify";
  public static final String HISTORY_COMMENT = "Comment";
  public static final String BUSINESS = "Business";
  public static final String BUSINESSES = "Businesses";
  public static final String PERSONAL_BANKER_TYPE = "PersonalBankerType";
  public static final String ACCOUNT_REP_TYPE = "AccountRepType";
  public static final String ADDED_PB_BUSINESS_NAMES = "AddedPbBusinessNames";
  public static final String ADDED_AR_BUSINESS_NAMES = "AddedArBusinessNames";
  public static final String REMOVED_PB_BUSINESS_NAMES = "RemovedPbBusinessNames";
  public static final String REMOVED_AR_BUSINESS_NAMES = "RemovedArBusinessNames";
  public static final String ENTITLEMENT_ADD_BANKEMPLOYEE = "AddBankEmployee";
  public static final String ENTITLEMENT_ADD_BANKEMPLOYEE_HISTORY = "AddBankEmployeeHistory";
  public static final String ENTITLEMENT_DELETE_BANKEMPLOYEE = "DeleteBankEmployee";
  public static final String ENTITLEMENT_DELETE_USER = "DeleteUser";
  public static final String ENTITLEMENT_EDIT_BANKEMPLOYEE = "EditBankEmployee";
  public static final String ENTITLEMENT_GET_ASSIGNED_EMPLOYEES = "GetAssignedEmployees";
  public static final String ENTITLEMENT_GET_ASSIGNED_USERS = "GetAssignedUsers";
  public static final String ENTITLEMENT_GET_BANKEMPLOYEE_HISTORY = "GetBankEmployeeHistory";
  public static final String ENTITLEMENT_GET_BANKEMPLOYEE_LIST = "GetBankEmployeeList";
  public static final String ENTITLEMENT_GET_BANKEMPLOYEE_TO_MODIFY = "GetBankEmployeeToModify";
  public static final String ENTITLEMENT_GET_SUPERVISORS = "GetSupervisors";
  public static final String ENTITLEMENT_GET_USER = "GetUser";
  public static final String ENTITLEMENT_GET_USER_HISTORY = "GetUserHistory";
  public static final String ENTITLEMENT_MODIFY_PASSWORD_STATUS = "ModifyPasswordStatus";
  public static final String ENTITLEMENT_REMOVE_ASSIGNED_EMPLOYEES = "RemoveAssignedEmployees";
  public static final String ENTITLEMENT_SEARCH_BANKEMPLOYEE = "SearchBankEmployee";
  public static final String ENTITLEMENT_SEARCH_USER = "SearchUser";
  public static final String ENTITLEMENT_UPDATE_ASSIGNED_USERS = "UpdateAssignedUsers";
  public static final int TASK_ERROR_BANK_EMPLOYEE_SERVICE_NOT_FOUND_IN_SESSION = 5500;
  public static final int TASK_ERROR_INVALID_EMPLOYEEID = 5501;
  public static final int TASK_ERROR_BANK_EMPLOYEE_NOT_FOUND_IN_SESSION = 5502;
  public static final int TASK_ERROR_BANK_EMPLOYEES_NOT_FOUND_IN_SESSION = 5503;
  public static final int TASK_ERROR_OLD_BANK_EMPLOYEE_NOT_FOUND_IN_SESSION = 5504;
  public static final int TASK_ERROR_MODIFIED_BANK_EMPLOYEE_NOT_FOUND_IN_SESSION = 5505;
  public static final int TASK_ERROR_EMPLOYEE_HISTORIES_NOT_FOUND_IN_SESSION = 5507;
  public static final int TASK_ERROR_ASSIGNED_USERS_NOT_FOUND_IN_SESSION = 5508;
  public static final int TASK_ERROR_INSUFFICIENT_RIGHTS = 5509;
  public static final int TASK_ERROR_USER_NOT_FOUND_IN_SESSION = 5510;
  public static final int TASK_ERROR_USERS_NOT_FOUND_IN_SESSION = 5511;
  public static final int TASK_ERROR_EMPLOYEE_PROFILE_PROPERTIES_NOT_FOUND_IN_SESSION = 5512;
  public static final int TASK_ERROR_COLLECTION_NOT_FOUND = 5513;
  public static final int TASK_ERROR_DUPLICATE_USERNAME = 5514;
  public static final int TASK_ERROR_INVALID_USERID = 5515;
  public static final int TASK_ERROR_CLASSNAME_NOT_SPECIFIED = 5516;
  public static final int TASK_ERROR_INIT_URL_NOT_SPECIFIED = 5517;
  public static final int TASK_ERROR_UNABLE_TO_GET_EMPLOYEE_PROFILE_PROPERTIES_FILE = 5518;
  public static final int TASK_ERROR_UNABLE_TO_CREATE_BANK_EMPLOYEE_SERVICE = 5519;
  public static final int TASK_ERROR_COLLECTION_NAME_NOT_SPECIFIED = 5520;
  public static final int TASK_ERROR_UNABLE_TO_OPEN_TASK_SECURITY_FILE = 5521;
  public static final int TASK_ERROR_ASSIGNED_EMPLOYEES_NOT_FOUND_IN_SESSION = 5522;
  public static final int TASK_ERROR_OLD_USER_NOT_FOUND_IN_SESSION = 5523;
  public static final int TASK_ERROR_ASSIGNED_BUSINESSES_NOT_FOUND_IN_SESSION = 5524;
  public static final int TASK_ERROR_JOB_TYPES_NOT_FOUND_IN_SESSION = 5525;
  public static final int TASK_ERROR_INVALID_JOB_TYPE = 5526;
  public static final int TASK_ERROR_BANK_EMPLOYEE_ONLY_ASSIGNEE_OF_A_CASE_TYPE = 5527;
  public static final int TASK_ERROR_BANK_EMPLOYEE_HAS_CASES_ASSIGNED_TO = 5528;
  public static final int TASK_ERROR_ASSIGNED_BANKS_NOT_FOUND_IN_SESSION = 5529;
  public static final int TASK_ERROR_AFFILIATE_BANKS_NOT_FOUND_IN_SESSION = 5530;
  public static final int TASK_ERROR_AFFILIATE_BANK_NOT_SET = 5531;
  public static final int TASK_ERROR_INVALID_AFFILIATE_BANK_ID = 5532;
  public static final int TASK_ERROR_BUSINESSES_ASSIGNED_WITH_NO_AFFILIATE_BANKS = 5533;
  public static final int TASK_ERROR_INVALID_ENTITLEMENT_OPERATION_NAME = 5534;
  public static final int TASK_ERROR_NO_CONSUMER_OR_CORPORATE_MANAGEMENT_SPECIFIED = 5535;
  public static final int TASK_ERROR_INVALID_PASSWORD_STATUS = 5536;
  public static final int TASK_ERROR_INVALID_SWITCHBANK_AFFILIATE_BANK_ID = 5537;
  public static final int EMPLOYEE_ACTIVE = 0;
  public static final int EMPLOYEE_DELETED = 1;
  public static final int EMPLOYEE_TEMP_INACTIVE = 2;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.BankEmployeeTask
 * JD-Core Version:    0.7.0.1
 */