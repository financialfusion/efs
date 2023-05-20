package com.ffusion.efs.tasks.entitlements;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String ENTITLEMENT_GROUP = "Entitlement_EntitlementGroup";
  public static final String ENTITLEMENT_GROUPS = "Entitlement_EntitlementGroups";
  public static final String ENTITLEMENT_HASHMAP = "Entitlement_HashMap";
  public static final String ENTITLEMENT_ARRAYLIST = "Entitlement_ArrayList";
  public static final String ENTITLEMENT_LIMITS = "Entitlement_Limits";
  public static final String ENTITLEMENT_LIMIT = "Entitlement_Limit";
  public static final String ENTITLEMENT_CAN_ADMIN = "Entitlement_CanAdminister";
  public static final String ENTITLEMENT_CAN_ADMIN_ANY_GROUP = "Entitlement_CanAdministerAnyGroup";
  public static final String ENTITLEMENT_RESTRICTED_LIST = "Entitlement_Restricted_list";
  public static final String ENTITLEMENT_GROUP_MEMBER = "Entitlement_Group_Member";
  public static final String ENTITLEMENT_GROUP_MEMBERS = "Entitlement_Group_Members";
  public static final String ENTITLEMENT_GROUP_NUM_MEMBERS = "Entitlement_Group_Num_Members";
  public static final String ENTITLEMENT_TYPE = "Entitlement_Type";
  public static final String ENTITLEMENT_TYPES = "Entitlement_Types";
  public static final String LIMIT_TYPES = "Limit_Types";
  public static final String MODULE_TYPES = "Module_Types";
  public static final String ENT_REPORT_NAME = "Entitlement_Report";
  public static final String ENT_REPORT_RESULTS = "Entitlement_Report_Results";
  public static final String ENTITLEMENTS = "Entitlement_Entitlements";
  public static final String ENT_ENTITLEMENTS = "Entitlements";
  public static final int ERROR_NO_ENTITLEMENT_GROUP = 35001;
  public static final int ERROR_NO_ENTITLEMENT_LIMIT = 35002;
  public static final int ERROR_NO_GROUPID = 35003;
  public static final int ERROR_NO_ENTITLEMENT_TYPE = 35004;
  public static final int ERROR_NO_NOTENTITLEDURL = 35005;
  public static final int ERROR_NO_GROUPNAME = 35006;
  public static final int ERROR_NO_AMOUNT = 35008;
  public static final int ERROR_NO_DATE = 35009;
  public static final int ERROR_NO_OLDAMOUNT = 35010;
  public static final int ERROR_NO_NEWAMOUNT = 35011;
  public static final int ERROR_NO_OLDDATE = 35012;
  public static final int ERROR_NO_NEWDATE = 35013;
  public static final int ERROR_NO_TYPENAME = 35014;
  public static final int ERROR_NO_NOTEXISTSURL = 35015;
  public static final int ERROR_NO_CANADMINGROUPID = 35017;
  public static final int ERROR_NO_LIMITNAME = 35018;
  public static final int ERROR_NO_DATA = 35019;
  public static final int ERROR_NO_PERIOD = 35020;
  public static final int ERROR_NO_PARENTID = 35021;
  public static final int ERROR_NO_ENTITLEMENT_RESTRICTED_LIST = 35023;
  public static final int ERROR_NO_MEMBER_TYPE = 35024;
  public static final int ERROR_NO_MEMBER_SUBTYPE = 35025;
  public static final int ERROR_NO_MEMBER_ID = 35026;
  public static final int ERROR_NO_SVC_BUREAU_ID = 35027;
  public static final int ERROR_NO_OPERATION_NAME = 35028;
  public static final int ERROR_NO_OBJECT_TYPE = 35029;
  public static final int ERROR_NO_OBJECT_ID = 35030;
  public static final int ERROR_NO_ALLOW_APPROVAL = 35031;
  public static final int ERROR_NO_TARGETID = 35032;
  public static final int ERROR_NO_NEWGROUPID = 35033;
  public static final int ERROR_NO_NEWTARGETID = 35034;
  public static final int ERROR_INVALID_GROUPID = 35035;
  public static final int ERROR_NO_EXTEND_ADMIN = 35036;
  public static final int ERROR_NO_GROUP_TYPE = 35037;
  public static final int ERROR_NO_OBJECTINUSEURL = 35038;
  public static final int ERROR_NO_MAX_LIMIT_MAP_NAME = 35039;
  public static final int ERROR_NO_MAX_LIMIT_MAP = 35040;
  public static final int ERROR_NO_MERGED_ENT_TYPE_PROP_LIST_LIST = 35041;
  public static final int ERROR_NO_WIRE_TEMPLATE_IDS_LIST = 35042;
  public static final int ERROR_NO_ENT_TYPE_PROPERTY_LIST_NAME = 35043;
  public static final int ERROR_NO_ENT_TYPE_PROPERTY_LIST = 35044;
  public static final int ERROR_NO_PROPERTY_NAME = 35045;
  public static final int ERROR_NO_CROSS_CURRENCY = 35046;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.Task
 * JD-Core Version:    0.7.0.1
 */