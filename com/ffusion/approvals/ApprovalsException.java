package com.ffusion.approvals;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ApprovalsException
  extends Exception
{
  public static final int UNKNOWN_ERROR = 0;
  public static final int APPROVALS_ERROR = 30001;
  public static final int NOT_INITIALIZED = 30002;
  public static final int SERVICE_INIT_FAILED = 30003;
  public static final int UNABLE_TO_LOAD_PLUGIN = 30004;
  public static final int ERROR_PROCESSING_APPROVED_ITEM = 30005;
  public static final int ERROR_PROCESSING_REJECTED_ITEM = 30006;
  public static final int ERROR_PROCESSING_COMPLETED_ITEM = 30007;
  public static final int DB_EXCEPTION = 30011;
  public static final int CONNECTION_POOL_EXCEPTION = 30012;
  public static final int TRANSACTION_WAREHOUSE_EXCEPTION = 30013;
  public static final int INVALID_LEVEL_TYPE = 30051;
  public static final int INVALID_LEVELID = 30052;
  public static final int LEVEL_NEGATIVE_AMOUNT = 30053;
  public static final int LEVEL_MAX_LESS_THAN_MIN = 30054;
  public static final int LEVEL_RANGE_CONFLICT = 30055;
  public static final int UNKNOWN_FLOW_TYPE = 30056;
  public static final int NO_CHAIN_ITEMS_PRESENT = 30057;
  public static final int ITEM_ALREADY_COMPLETED_APPROVAL = 30058;
  public static final int ITEM_DOES_NOT_EXIST = 30060;
  public static final int INVALID_USER = 30061;
  public static final int INVALID_GROUP = 30062;
  public static final int ITEM_PROCESSING_STATE_CHANGE_FAILED = 30063;
  public static final int DELETE_FLOW_CHAIN_FAILED = 30064;
  public static final int UPDATE_FLOW_ORDINAL_FAILED = 30065;
  public static final int NULL_LEVEL = 30066;
  public static final int NULL_LEVELS = 30067;
  public static final int NULL_CHAIN_ITEMS = 30069;
  public static final int NULL_SEARCH_CRITERIA = 30070;
  public static final int NULL_ITEM = 30071;
  public static final int NULL_ITEMS = 30072;
  public static final int NULL_DECISIONS = 30073;
  public static final int LEVELS_BUSINESSID_MISMATCH = 30074;
  public static final int INVALID_ITEM_TYPE = 30075;
  public static final int NO_LEVELS_PRESENT = 30076;
  public static final int DELETE_APPROVAL_ITEM_FAILED = 30077;
  public static final int NO_CHAIN_ITEM_PRESENT = 30078;
  public static final int ITEM_ALREADY_PASSED_APPROVAL = 30079;
  public static final int SEARCH_CRITERIA_INVALID_DATE_FORMAT = 30080;
  public static final int GETSUBMITTEDITEMS_MUST_PROVIDE_USER_OR_BUSINESS_ID = 30101;
  public static final int GETSUBMITTEDITEMS_TYPE_MISMATCH = 30102;
  public static final int CREATEAPPROVALITEM_MISSING_FLOW_TYPE = 30103;
  public static final int INITIALIZE_NULL_DB_SETTINGS = 30104;
  public static final int CLEANUP_INVALID_AGE_IN_DAYS_VALUE = 30105;
  public static final int SUBMITDECISIONS_COMPLETED = 30106;
  public static final int SUBMITDECISIONS_INVALID_DECISION = 30107;
  public static final int SUBMITDECISIONS_MULTIPLE_ROWS_FOR_CURRENT_APPR_ITEM = 30108;
  public static final int UPDATEWORKFLOWLEVELS_ATTEMPT_MODIFYING_BUSINESSID = 30109;
  public static final int UPDATEWORKFLOWLEVELS_ATTEMPT_MODIFYING_LEVEL_TYPE = 30110;
  public static final int GETPENDINGAPPROVALS_TRANSACTIONS_LENGTH_MISMATCH = 30111;
  public static final int CREATEAPPROVALITEM_MISSING_USERNAME = 30112;
  public static final int CREATE_ITEM_RESTRICTED = 30113;
  public static final int SUBMIT_ITEM_RESTRICTED = 30114;
  public static final int INCORRECT_CURRENCY_FOR_LOCALE = 30115;
  public static final int GETITEMHISTORY_MISSING_OBJECT_ID = 30116;
  public static final int GETITEMHISTORY_UNSUPPORTED_ITEM_TYPE = 30118;
  public static final int GETSUBMITTEDITEMS_BOTH_USER_AND_BUSINESS_ID_PROVIDED = 30119;
  public static final int SUBMITDECISIONS_ITEM_ALREADY_APPROVED_BY_USER = 30120;
  public static final int UPDATEWORKFLOWLEVELS_ATTEMPT_MODIFYING_OPERATION_TYPE = 30121;
  public static final int GETSUBMITTEDITEMS_NULL_APPROVALSTAGES = 30122;
  public static final int GETSUBMITTEDITEMS_INVALID_APPROVALSTAGES_LENGTH = 30123;
  public static final int GETSUBMITTEDITEMS_INVALID_APPROVALSTAGE_VALUE = 30124;
  public static final int NULL_APPR_GROUP = 30125;
  public static final int NULL_GROUP_NAME = 30126;
  public static final int NULL_BUSINESS_ID = 30127;
  public static final int APPROVAL_GROUP_DOES_NOT_EXIST = 30128;
  public static final int UPDATE_APPROVAL_GROUP_FAILED = 30129;
  public static final int NULL_APPR_GROUPS = 30130;
  public static final int NON_EMPTY_APPROVAL_GROUP = 30131;
  public static final int INVALID_APPR_GROUPID = 30132;
  public static final int INVALID_APPR_GROUP = 30133;
  public static final int STALL_CONDITION_DETECT_ERROR = 30134;
  public static final int SETWORKFLOWCHAINITEMS_STALL_CONDITION_DETECTED = 30135;
  public static final int SUBMITDECISIONS_UNABLE_TO_LOAD_PLUGIN = 30140;
  public static final int SUBMITDECISIONS_ERROR_CHECKING_ENTITLEMENTS = 30141;
  public static final int SUBMITDECISIONS_NOT_ENTITLED = 30142;
  public static final int SUBMITDECISIONS_ERROR_CHECKING_APPROVAL_LIMITS = 30143;
  public static final int SUBMITDECISIONS_EXCEEDS_APPROVAL_LIMITS = 30144;
  public static final int SUBMITDECISIONS_ERROR_UNKNOWN = 30145;
  public static final int GETENTITLEDBUSINESSEMPLOYEES_ERROR_RETRIEVING_ENTITLEMENTS = 30146;
  public static final int GETENTITLEDBUSINESSEMPLOYEES_ERROR_RETRIEVING_EMPLOYEES = 30147;
  public static final int GETENTITLEDGROUPS_ERROR_RETRIEVING_ENTITLEMENTS = 30148;
  public static final int GETENTITLEDGROUPS_ERROR_RETRIEVING_GROUPS = 30149;
  public static final int INVALID_OBJECT_TYPE = 30150;
  public static final int MISSING_SERVICES_PACKAGE_ID = 30151;
  public static final int BUSINESS_DEFINED_FLOW_TYPE_FOR_CONSUMER = 30152;
  public static final int CREATEAPROVALITEM_MISSING_SERVICE_PACKAGEID = 30153;
  public static final int CREATEAPROVALITEM_MISSING_FX_RATE = 30154;
  private int jdField_do = -1;
  private Throwable jdField_if = null;
  private String a = null;
  
  public ApprovalsException(int paramInt, String paramString, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.a = paramString;
    this.jdField_if = paramThrowable;
  }
  
  public ApprovalsException(String paramString, Throwable paramThrowable)
  {
    this.a = paramString;
    this.jdField_if = paramThrowable;
  }
  
  public ApprovalsException(int paramInt, String paramString)
  {
    this.jdField_do = paramInt;
    this.a = paramString;
  }
  
  public ApprovalsException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public int getErrorCode()
  {
    return this.jdField_do;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.a != null) {
      localStringBuffer.append(this.a).append(" ");
    }
    if (this.jdField_do != -1) {
      localStringBuffer.append(a()).append(" ");
    }
    if (this.jdField_if != null) {
      localStringBuffer.append(this.jdField_if.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintStream);
    }
  }
  
  private String a()
  {
    String str = "";
    switch (this.jdField_do)
    {
    case 30001: 
      str = "An error has occurred in the approvals adapter";
      break;
    case 30003: 
      str = "The Approvals Service could not be initialized. ";
      break;
    case 30011: 
      str = "A database error has occurred";
      break;
    case 30012: 
      str = "A connection pool error has occurred";
      break;
    case 30013: 
      str = "A transaction warehouse related error has occurred";
      break;
    case 30051: 
      str = "An invalid level type has been specified";
      break;
    case 30052: 
      str = "The given levelID is invalid";
      break;
    case 30053: 
      str = "An Approval level cannot have a negative amount value";
      break;
    case 30054: 
      str = "An Approval level cannot have the minAmount greater than the maxAmount";
      break;
    case 30055: 
      str = "The specified approval level's range conflicts with another level";
      break;
    case 30056: 
      str = "The flow type specified is unknown";
      break;
    case 30057: 
      str = "There are no chain items present";
      break;
    case 30058: 
      str = "The item has already been approved or rejected";
      break;
    case 30060: 
      str = "One or more specified approval items could not be found";
      break;
    case 30061: 
      str = "Could not submit an approval decision: the item is waiting for approval on another user";
      break;
    case 30062: 
      str = "Could not submit an approval decision: the item is waiting for approval on another group";
      break;
    case 30063: 
      str = "The processing state of this approval item could not be modified";
      break;
    case 30064: 
      str = "The approval flow chain could not be deleted";
      break;
    case 30065: 
      str = "The approval flow chain ordinal could not be updated";
      break;
    case 30066: 
      str = "The specified ApprovalLevel is null";
      break;
    case 30067: 
      str = "The specified ApprovalLevels are null";
      break;
    case 30069: 
      str = "The specified ApprovalChainItems are null";
      break;
    case 30070: 
      str = "The specified search criteria is null";
      break;
    case 30071: 
      str = "The specified ApprovalItem is null";
      break;
    case 30072: 
      str = "The specified ApprovalItems are null";
      break;
    case 30073: 
      str = "The ApprovalsDecision are null";
      break;
    case 30074: 
      str = "The given approval levels do not belong to the same business";
      break;
    case 30075: 
      str = "The approval item type is unknown";
      break;
    case 30076: 
      str = "No approval level has been defined to handle the amount of the item being submitted for approval";
      break;
    case 30077: 
      str = "The approval item could not be deleted";
      break;
    case 30078: 
      str = "There is no chain item present for the approval item";
      break;
    case 30101: 
      str = "A user ID or business ID must be provided in the search criteria hashmap.";
      break;
    case 30102: 
      str = "Could not get the submitted items as the item sub type did not match the transaction type";
      break;
    case 30119: 
      str = "Only one of the user ID or business ID can be provided in the search criteria hashmap.";
      break;
    case 30103: 
      str = "The flow type has not been specified for this approval item";
      break;
    case 30104: 
      str = "Could not initialize the approvals adapter as the <DATABASE_SETTINGS> tag is not present in the XML configuration file";
      break;
    case 30105: 
      str = "Cannot invoke cleanup with a negative value for ageInDays";
      break;
    case 30106: 
      str = "Cannot submit a decision for a completed approval";
      break;
    case 30107: 
      str = "An improper decision value was given";
      break;
    case 30108: 
      str = "Multiple approval items exist with their processingStates set to 'ToApprove'";
      break;
    case 30120: 
      str = "Item has already been approved by the user";
      break;
    case 30109: 
      str = "The businessID of the approval workflow level cannot be modified";
      break;
    case 30121: 
      str = "The operation type of the approval workflow level cannot be modified";
      break;
    case 30110: 
      str = "The level type of the approval workflow level cannot be modified";
      break;
    case 30111: 
      str = "The number of transactions returned differs from the number requested";
      break;
    case 30112: 
      str = "Unable to obtain information about the submitting user";
      break;
    case 30113: 
      str = "Could not create an approval item: the user creating the approval item is the only user in the approval chain";
      break;
    case 30114: 
      str = "Could not submit an approval decision: the decision must be submitted by a different user in the group";
      break;
    case 30115: 
      str = "The currency amounts provided are not appropriate for your locale";
      break;
    case 30116: 
      str = "The object ID must be provided in the search criteria hashmap in a call to GetItemHistory()";
      break;
    case 30118: 
      str = "The item type used in a call to GetItemHistory() is not currently supported for history retrieval.";
      break;
    case 30122: 
      str = "The specified approvalStages have a null value.";
      break;
    case 30123: 
      str = "The specified size of the specified approvalStages is invalid.";
      break;
    case 30124: 
      str = "An invalid approvalStage has been specified.";
      break;
    case 30079: 
      str = "The item has already passed approval";
      break;
    case 30080: 
      str = "The date specified in the search criteria is not of the format MM/dd/yyyy or MM/dd/yyyy HH:mm:ss";
      break;
    case 30126: 
      str = "No group name was specified.";
      break;
    case 30125: 
      str = "Null group was specified.";
      break;
    case 30133: 
      str = "The specified user does not belong to the approval group.";
      break;
    case 30131: 
      str = "The specified group cannot be removed.  It still has group members assigned to it.";
      break;
    case 30134: 
      str = "An error occurred while evaluating the specified approval chain for stall conditions.";
      break;
    case 30150: 
      str = " An invalid object type has been specified for the approvals workflow level ";
      break;
    case 30151: 
      str = " The services package ID is missing ";
      break;
    case 30152: 
      str = " An invalid flow type has been specified for the consumer submitted transaction ";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.ApprovalsException
 * JD-Core Version:    0.7.0.1
 */