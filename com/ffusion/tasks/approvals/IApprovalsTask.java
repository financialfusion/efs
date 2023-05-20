package com.ffusion.tasks.approvals;

import com.ffusion.tasks.Task;

public abstract interface IApprovalsTask
  extends Task
{
  public static final String APPROVALS_PENDING_COUNT = "PendingApprovalsCount";
  public static final String APPROVALS_PENDING_COUNT_DETAIL = "PendingApprovalsCountDetail";
  public static final String APPROVALS_LEVEL = "ApprovalsLevel";
  public static final String APPROVALS_LEVELS = "ApprovalsLevels";
  public static final String APPROVALS_CHAIN_ITEMS = "ApprovalsChainItems";
  public static final String APPROVALS_ITEM_TYPE = "ApprovalsItemType";
  public static final String APPROVALS_STATUSES = "ApprovalsStatuses";
  public static final String APPROVALS_ITEMS = "ApprovalsItems";
  public static final String APPROVALS_ITEM = "ApprovalsItem";
  public static final String APPROVALS_SEARCH_CRITERIA = "SearchCriteria";
  public static final String APPROVALS_DECISIONS = "ApprovalsDecisions";
  public static final String APPROVALS_HISTORY = "ApprovalsHistory";
  public static final String APPROVALS_IS_APPROVER = "IsApprover";
  public static final String APPROVALS_IS_BUSINESS_IN_USE = "IsBusinessInUse";
  public static final String APPROVALS_IS_OBJECT_IN_USE = "IsObjectInUse";
  public static final String APPROVALS_ITEM_ERRORS = "ApprovalsItemErrors";
  public static final String APPROVALS_STAGES = "ApprovalsStages";
  public static final String APPROVALS_STALL_CONDITIONS = "ApprovalsStallConditions";
  public static final String APPROVALS_STALL_CONDITIONS_MEMBERLIST = "ApprovalsStallConditionsMemberList";
  public static final String APPROVALS_STALL_CONDITIONS_STALLNUMBER = "ApprovalsStallConditionsStallNumber";
  public static final String APPROVALS_STALL_CONDITIONS_GROUPID = "ApprovalsStallConditionsGroupID";
  public static final String APPROVALS_STALL_CONDITIONS_APPROVALGROUPIDS = "ApprovalsStallConditionsApprovalGroupIDs";
  public static final String APPROVALS_ENTITLED_BANK_EMPLOYEES = "ApprovalsEntitledBankEmployees";
  public static final String APPROVALS_ENTITLED_BANK_GROUPS = "ApprovalsEntitledBankGroups";
  public static final String APPROVALS_GROUP = "ApprovalsGroup";
  public static final String APPROVALS_GROUPS = "ApprovalsGroups";
  public static final String APPROVALS_USER_APPROVALS_GROUPS = "UserApprovalsGroups";
  public static final String APPROVALS_ISAPPROVALGROUPINUSE = "IsApprovalsGroupInUse";
  public static final String APPROVALS_GROUPMEMBERS = "ApprovalsGroupMembers";
  public static final String APPROVALS_USER_ID = "ApprovalsUserID";
  public static final String APPROVALS_ENTITLED_BUSINESS_EMPLOYEES = "ApprovalsEntitledBusinessEmployees";
  public static final String APPROVALS_ENTITLED_BUSINESS_GROUPS = "ApprovalsEntitledBusinessGroups";
  public static final String APPROVALS_EXCEEDED_LIMITS_MAP = "ApprovalsExceededLimitsMap";
  public static final String APPROVALS_USER_COLLECTION_NAME = "ApprovalsUsersCollection";
  public static final String APPROVALS_GROUP_COLLECTION_NAME = "ApprovalsGroupsCollection";
  public static final String APPROVALS_APPROVALS_GROUP_COLLECTION_NAME = "ApprovalsApprovalsGroupsCollection";
  public static final int BASE_APPROVALS_TASK_ERROR = 31000;
  public static final int MISSING_OBJECT_ID = 31000;
  public static final int MISSING_ITEM_TYPE = 31001;
  public static final int MISSING_ITEM_SUB_TYPE = 31002;
  public static final int MISSING_BUSINESS_EMPLOYEES = 31003;
  public static final int MALFORMED_APPROVER_VALUE = 31004;
  public static final int MISSING_ENTITLEMENTS_COLLECTION_NAME = 31005;
  public static final int MISSING_LIMITS_COLLECTION_NAME = 31006;
  public static final int MISSING_ENTITLEMENTS_COLLECTION = 31007;
  public static final int MISSING_LIMITS_COLLECTION = 31008;
  public static final int MISSING_APPROVALS_GROUP_MEMBERS_COLLECTION = 31009;
  public static final int ERROR_INVALID_MIN_AMOUNT = 31010;
  public static final int ERROR_INVALID_MAX_AMOUNT = 31011;
  public static final int ERROR_NEGATIF_MIN_AMOUNT = 31012;
  public static final int ERROR_NEGATIF_MAX_AMOUNT = 31013;
  public static final int ERROR_MAX_LESSER_MIN = 31014;
  public static final int ERROR_INVALID_LEVELID = 31015;
  public static final int ERROR_INVALID_BUSID = 31016;
  public static final int ERROR_NO_SEARCH_VALUE = 4130;
  public static final int INCORRECT_CURRENCY_FOR_LOCALE = 30115;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.IApprovalsTask
 * JD-Core Version:    0.7.0.1
 */