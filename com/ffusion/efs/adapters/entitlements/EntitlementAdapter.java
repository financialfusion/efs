package com.ffusion.efs.adapters.entitlements;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import com.ffusion.beans.fx.FXRate;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyLists;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.ConnectionHolder;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.db.ReopenableDBCookie;

public class EntitlementAdapter
  implements EntitlementDefines
{
  public static final String DB_PROPERTIES = "DB_PROPERTIES";
  public static final int USERTYPE_USER = 1;
  public static final int USERTYPE_BUSINESS = 2;
  public static final int USERTYPE_AGENT = 3;
  private static final String b = "A";
  private static final String d = "E";
  String jdField_void;
  private boolean e;
  private boolean c;
  protected HashMap entMap;
  protected HashMap limitMap;
  protected HashMap objTypeMap;
  private EntitlementCacheManager jdField_null;
  protected Properties properties;
  protected String dbType;
  private String f = "USD";
  private EntitlementTypePropertyLists i;
  private LimitTypePropertyLists g;
  private ObjectTypePropertyLists jdField_long;
  private long jdField_goto = 0L;
  private IForeignExchangeService h = null;
  public static final String SQL_ENTITLEMENT_TYPE_EXISTS = "select count(*) from entitlement_list where operation_name = ?";
  public static final String SQL_UPDATE_ENTITLEMENT_TYPE = "update entitlement_list set operation_name = ? where operation_name = ?";
  public static final String SQL_GET_GROUP_LIST = "select ent_group_id, name from entitlement_group order by ent_group_id";
  public static final String SQL_GET_ENTITLEMENT_GROUPS = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ?";
  public static final String SQL_GET_ENTITLEMENT_GROUPS_BY_SVC_BUREAU = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and svc_bureau_id = ?";
  public static final String SQL_GET_ENTITLEMENT_GROUPS_BY_TYPE = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_type = ?";
  public static final String SQL_GET_ENTITLEMENT_GROUPS_BY_TYPE_AND_SVC_BUREAU = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_type = ";
  public static final String SQL_GET_ENTITLEMENT_GROUPS_BY_PARENT_AND_TYPE = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and ent_group_type = ?";
  public static final String SQL_GET_ENTITLEMENT_GROUP_BY_NAME_TYPE_AND_SVC_BUREAU = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where name = ? and svc_bureau_id = ? and ent_group_type = ?";
  public static final String SQL_GET_ENTITLEMENT_GROUP_PROPS = "select prop_name, prop_value from entitlement_gprops where ent_group_id = ?";
  public static final String SQL_REMOVE_ENTITLEMENT_GROUP_PROPS = "delete from entitlement_gprops where ent_group_id = ?";
  public static final String SQL_ADD_ENTITLEMENT_GROUP_PROP = "insert into entitlement_gprops ( ent_group_id, prop_name, prop_value ) values (?,?,?)";
  public static final String SQL_ENTITLEMENT_EXISTS = "select count(*) from entitlement_group where name = ? and ent_group_type=? and svc_bureau_id=?";
  public static final String SQL_ENTITLEMENT_EXISTS_GUI = "select count(*) from entitlement_group where name = ? and parent_id=?";
  public static final String SQL_ENTITLEMENT_ID_EXISTS = "select count(*) from entitlement_group where ent_group_id = ?";
  public static final String SQL_GET_ENTITLEMENT_GROUP = "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_id = ?";
  public static final String SQL_GET_GROUPS_I_ADMIN = "select group_to_admin from entitlement_admin where ent_group_id = ? and member_id is null and permission_type like '%A%'";
  public static final String SQL_GET_GROUPS_I_ADMIN_OF_TYPE = "select a.group_to_admin from entitlement_admin a, entitlement_group g where a.ent_group_id = ? and a.group_to_admin = g.ent_group_id and g.ent_group_type = ? and a.member_id is null and permission_type like '%A%'";
  public static final String SQL_GET_GROUPS_I_ADMIN_MEMBER = "select distinct group_to_admin from entitlement_admin where ent_group_id = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) )  and permission_type like '%A%'";
  public static final String SQL_GET_ADMINS_I_ADMIN = "select group_to_admin, permission_type from entitlement_admin where ent_group_id = ? and member_id is null";
  public static final String SQL_GET_ADMINS_A_MEMBER_ADMINS = "select group_to_admin, permission_type from entitlement_admin  where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_GET_GROUPS_THAT_ADMIN_ME = "select ent_group_id from entitlement_admin where group_to_admin = ? and member_id is null and permission_type like '%A%'";
  public static final String SQL_GET_ADMINS_THAT_ADMIN_ME = "select ent_group_id, member_type, member_subtype, member_id, permission_type from entitlement_admin where group_to_admin = ?";
  public static final String SQL_DEL_ENTITLEMENT_GROUP = "delete from entitlement_group where ent_group_id = ?";
  public static final String SQL_GET_ENTITLEMENT_DEL = "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type is null and member_subtype is null and member_id is null";
  public static final String SQL_GET_ENTITLEMENT_DEL_FOR_MEMBER = "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_GET_ENTITLEMENT_MAP = "select operation_name from entitlement_list";
  public static final String SQL_GET_LIMIT_TYPE_MAP = "select operation_name from limit_list";
  public static final String SQL_ADD_ENTITLEMENT = "insert into entitlement_list (operation_name) values (?)";
  public static final String SQL_DEL_ENTITLEMENT = "delete from entitlement_list where operation_name = ?";
  public static final String SQL_GET_OBJECT_TYPE_MAP = "select object_type from object_type";
  public static final String SQL_ADD_OBJECT_TYPE = "insert into object_type (object_type) values (?)";
  public static final String SQL_DEL_OBJECT_TYPE = "delete from object_type where object_type = ?";
  public static final String SQL_UPDATE_OBJECT_TYPE = "update object_type set object_type = ? where object_type = ?";
  public static final String SQL_ADD_ENTITLEMENT_GROUP = "insert into entitlement_group (ent_group_id,name,ent_group_type,parent_id,svc_bureau_id,modified_date) values (?,?,?,?,?,?)";
  public static final String SQL_UPDATE_ENTITLEMENT_GROUP = "update entitlement_group set name = ?, ent_group_type = ?, parent_id = ?, svc_bureau_id = ?, modified_date = ? where ent_group_id = ?";
  public static final String SQL_REMOVE_DELETED_ENTITLEMENTS = "delete from entitlement_del where ent_group_id = ? and member_id is null";
  public static final String SQL_REMOVE_DELETED_ENTITLEMENTS_FOR_MEMBER = "delete from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_RESTORE_GROUP_ENTITLEMENT = "delete from entitlement_del where ent_group_id = ? and member_id is null AND ";
  public static final String SQL_RESTORE_GROUP_ENTITLEMENT_FOR_MEMBER = "delete from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ? AND ";
  public static final String SQL_RESTRICTED_ENTITLEMENT_EXISTS = "select count(*) from entitlement_del where ent_group_id = ? and member_id is null AND ";
  public static final String SQL_RESTRICTED_ENTITLEMENT_EXISTS_FOR_MEMBER = "select count(*) from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ? AND ";
  public static final String SQL_MODIFY_GROUP_ENTITLEMENT = "update entitlement_del set operation_name = ?, object_type = ?, object_id = ? where ent_group_id = ? and member_id is null AND ";
  public static final String SQL_MODIFY_GROUP_ENTITLEMENT_FOR_MEMBER = "update entitlement_del set operation_name = ?, object_type = ?, object_id = ? where ent_group_id = ? AND member_type = ? AND member_subtype = ? AND member_id = ? AND ";
  public static final String SQL_ADD_DELETED_ENTITLEMENTS = "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)";
  public static final String SQL_ADD_DELETED_ENTITLEMENTS_FOR_MEMBER = "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)";
  public static final String SQL_MOVE_ENTITLEMENTS = "update entitlement_del set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?";
  public static final String SQL_CHECK_GROUP_ENT = "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type is null";
  public static final String SQL_CHECK_GROUP_MEMBER_ENT = "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type = ? and r.member_subtype = ? and r.member_id = ?";
  public static final String SQL_REMOVE_ADMIN_GROUPS = "delete from entitlement_admin where ent_group_id = ? and member_type is null";
  public static final String SQL_REMOVE_ADMIN_GROUPS_FOR_MEMBER = "delete from entitlement_admin where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_REMOVE_GROUP_FROM_ADMIN = "delete from entitlement_admin where group_to_admin = ?";
  public static final String SQL_REMOVE_ADMIN_GROUP = "delete from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null";
  public static final String SQL_REMOVE_ADMIN_GROUP_WITH_MEMBER = "delete from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_ADMIN_GROUP_EXISTS = "select count(*) from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null";
  public static final String SQL_ADMIN_GROUP_EXISTS_WITH_MEMBER = "select count(*) from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_ADD_ADMIN_GROUP = "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)";
  public static final String SQL_IS_GROUP_ADMIN = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null and permission_type like '%A%'";
  public static final String SQL_IS_GROUP_ADMIN_WITH_MEMBER = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) ) and permission_type like '%A%'";
  public static final String SQL_IS_GROUP_EXTEND = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null and permission_type like '%E%'";
  public static final String SQL_IS_GROUP_EXTEND_WITH_MEMBER = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) ) and permission_type like '%E%'";
  public static final String SQL_UPDATE_ADMIN_GROUP = "update entitlement_admin set permission_type = ? where ent_group_id = ? and group_to_admin = ? and member_id is null";
  public static final String SQL_UPDATE_ADMIN_GROUP_WITH_MEMBER = "update entitlement_admin set permission_type = ? where ent_group_id = ? and group_to_admin = ? and member_type = ? and member_subtype = ? and member_id = ?";
  public static final String SQL_GET_LIMIT = "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and limit_id = ?";
  public static final String SQL_LIMIT_EXISTS = "select count(*) from limits where ent_group_id = ? AND period = ? AND isCrossCurrency = ? AND currencyCode = ? ";
  public static final String SQL_LIMIT_ID_EXISTS = "select count(*) from limits where limit_id = ?";
  public static final String SQL_ADD_LIMIT = "insert into limit_list (operation_name) values (?)";
  public static final String SQL_LIMIT_TYPE_EXISTS = "select count(*) from limit_list where operation_name = ?";
  public static final String SQL_DEL_LIMIT = "delete from limit_list where operation_name = ?";
  public static final String SQL_GET_GROUP_LIMITS = "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ? and l.member_type is null and l.member_subtype is null and l.member_id is null";
  public static final String SQL_GET_GROUP_LIMITS_FOR_MEMBER = "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ? and l.member_type = ? and l.member_subtype = ? and member_id = ?";
  public static final String SQL_SEARCH_GROUP_LIMITS = "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id";
  public static final String SQL_ADD_GROUP_LIMIT = "insert into limits (limit_id, operation_name, ent_group_id, member_type, member_subtype, member_id, period, data, modified_date, object_type, object_id, allowApproval, running_total_type, isCrossCurrency, currencyCode ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String SQL_DEL_GROUP_LIMIT = "delete from limits where limit_id = ?";
  public static final String SQL_CHECK_GROUP_LIMIT = "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ?";
  public static final String SQL_UPDATE_GROUP_LIMIT = "update limits set operation_name = ?, ent_group_id = ?, member_type = ?, member_subtype = ?, member_id = ?, period = ?, data = ?, modified_date = ?, object_type = ?, object_id = ?, allowApproval = ?, running_total_type = ?, isCrossCurrency = ?, currencyCode = ? where limit_id = ?";
  public static final String SQL_MOVE_GROUP_LIMITS = "update limits set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?";
  public static final String SQL_MOVE_ADMIN_PRIV = "update entitlement_admin set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?";
  public static final String SQL_GET_RUNNING_TOTALS = "select limit_id, amount, run_total_id from running_total ";
  public static final String SQL_GET_RUNNING_TOTAL_PART_FOR_USER = "(limit_id = ? and member_id = ? and member_type = ? and member_subtype = ? and start_date = ?)";
  public static final String SQL_GET_RUNNING_TOTAL_PART_FOR_GROUP = "(limit_id = ? and ent_group_id = ? and start_date = ?)";
  public static final String SQL_UPDATE_RUNNING_TOTAL = "update running_total set amount = ? where run_total_id = ?";
  public static final String SQL_ADD_RUNNING_TOTAL = "insert into running_total (run_total_id, limit_id, member_id, member_type, member_subtype, ent_group_id, amount, start_date) values (?,?,?,?,?,?,?,?)";
  public static final String SQL_MOVE_RUNNING_TOTALS = "update running_total set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?";
  public static final String SQL_ADD_GROUP_MEMBER = "insert into entitlement_gmemb( member_type, member_subtype, member_id, ent_group_id ) values ( ?,?,?,? )";
  public static final String SQL_UPDATE_GROUP_MEMBER = "update entitlement_gmemb set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ?";
  public static final String SQL_DEL_GROUP_MEMBER = "delete from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?";
  public static final String SQL_GET_GROUP_MEMBER = "select ent_group_id from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?";
  public static final String SQL_GET_GROUP_MEMBERS = "select member_id, member_type, member_subtype from entitlement_gmemb where ent_group_id = ?";
  public static final String SQL_GET_NUM_GROUP_MEMBERS = "select count(*) from entitlement_gmemb where ent_group_id = ?";
  public static final String SQL_WIPE_A_LIMITS_RUNNING_TOTAL = "delete from running_total where limit_id = ?";
  public static final String SQL_CLEANUP_RUNNING_TOTAL = "delete from running_total where run_total_id in (select run_total_id from limits l, running_total r  where l.limit_id = r.limit_id and l.period = ? and r.start_date < ? )";
  public static final String SQL_IS_ENTITLEMENT_OBJECT_IN_USE = "select distinct e.object_type, e.object_id from entitlement_del e where ( e.object_type=? and e.object_id=? ) ";
  public static final String SQL_IS_LIMIT_OBJECT_IN_USE = "select distinct l.object_type, l.object_id from limits l where ( l.object_type=? and l.object_id=? )";
  public static final String SQL_LOCK_LIMITS = "update limits set limit_id = limit_id where limit_id in (";
  public static final String SQL_GET_ENTITLEMENT_TYPE_PROPS = "SELECT a.operation_name, b.prop_name, b.prop_value FROM entitlement_list a LEFT JOIN ent_type_props b ON a.operation_name = b.operation_name ORDER BY a.operation_name";
  public static final String SQL_GET_ENTITLEMENT_TYPE_PROPS_ORACLE = "SELECT a.operation_name, b.prop_name, b.prop_value FROM entitlement_list a, ent_type_props b WHERE a.operation_name = b.operation_name(+) ORDER BY a.operation_name";
  public static final String SQL_DEL_ENTITLEMENT_TYPE_PROPS = "delete from ent_type_props where operation_name = ?";
  public static final String SQL_ADD_ENTITLEMENT_TYPE_PROP = "insert into ent_type_props (operation_name,prop_name,prop_value) values (?,?,?)";
  public static final String SQL_GET_LIMIT_TYPE_PROPS = "SELECT a.operation_name, b.prop_name, b.prop_value FROM limit_list a LEFT JOIN limit_type_props b ON a.operation_name = b.operation_name ORDER BY a.operation_name";
  public static final String SQL_GET_LIMIT_TYPE_PROPS_ORACLE = "SELECT a.operation_name, b.prop_name, b.prop_value FROM limit_list a, limit_type_props b WHERE a.operation_name = b.operation_name(+) ORDER BY a.operation_name";
  public static final String SQL_DEL_LIMIT_TYPE_PROPS = "delete from limit_type_props where operation_name = ?";
  public static final String SQL_ADD_LIMIT_TYPE_PROP = "insert into limit_type_props (operation_name,prop_name,prop_value) values (?,?,?)";
  public static final String SQL_GET_OBJECT_TYPE_PROPS = "SELECT a.object_type, b.prop_name, b.prop_value FROM object_type a LEFT JOIN object_type_props b ON a.object_type = b.object_type ORDER BY a.object_type";
  public static final String SQL_GET_OBJECT_TYPE_PROPS_ORACLE = "SELECT a.object_type, b.prop_name, b.prop_value FROM object_type a, object_type_props b WHERE a.object_type = b.object_type(+) ORDER BY a.object_type";
  public static final String SQL_DEL_OBJECT_TYPE_PROPS = "delete from object_type_props where object_type = ?";
  public static final String SQL_ADD_OBJECT_TYPE_PROP = "insert into object_type_props (object_type,prop_name,prop_value) values (?,?,?)";
  public static final String SQL_CLEANUP_ENTITLEMENT_CACHE_CHANGES = "delete from ent_cache_changes where change_time < ?";
  public static final String SQL_GROUP_MODIFIED_THIS_DB2 = "INSERT INTO ent_cache_changes( group_id, change_maker, change_time ) VALUES ( ?, ?, current timestamp )";
  public static final String SQL_MEMBER_MODIFIED_THIS_DB2 = "INSERT INTO ent_cache_changes ( group_id, member_type, member_subtype, member_id, change_maker, change_time ) VALUES ( ?, ?, ?, ?, ?, current timestamp )";
  public static final String SQL_GROUP_MODIFIED_THIS_ORACLE = "INSERT INTO ent_cache_changes( group_id, change_maker, change_time ) VALUES ( ?, ?, systimestamp )";
  public static final String SQL_MEMBER_MODIFIED_THIS_ORACLE = "INSERT INTO ent_cache_changes ( group_id, member_type, member_subtype, member_id, change_maker, change_time ) VALUES ( ?, ?, ?, ?, ?, systimestamp )";
  public static final String SQL_GROUP_MODIFIED_THIS_ASE = "INSERT INTO ent_cache_changes( group_id, change_maker, change_time ) VALUES ( ?, ?, getdate() )";
  public static final String SQL_MEMBER_MODIFIED_THIS_ASE = "INSERT INTO ent_cache_changes ( group_id, member_type, member_subtype, member_id, change_maker, change_time ) VALUES ( ?, ?, ?, ?, ?, getdate() )";
  public static final String SQL_GET_MODIFIED_ITEMS = "SELECT group_id, change_time, member_type, member_subtype, member_id FROM ent_cache_changes WHERE change_maker <> ? AND change_time > ? AND change_time <= ?";
  public static final String SQL_GET_DB_TIME_DB2 = "select current timestamp from sysibm.sysdummy1";
  public static final String SQL_GET_DB_TIME_ORACLE = "select systimestamp from dual";
  public static final String SQL_GET_DB_TIME_ASE = "select getdate()";
  
  public EntitlementAdapter(HashMap paramHashMap, boolean paramBoolean)
    throws EntitlementException
  {
    this((Properties)paramHashMap.get("DB_PROPERTIES"), paramBoolean);
  }
  
  public EntitlementAdapter(Properties paramProperties, boolean paramBoolean)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.initialize";
    if ((this.jdField_void != null) && (this.jdField_void.length() > 0)) {
      return;
    }
    try
    {
      this.properties = paramProperties;
      this.f = this.properties.getProperty("LimitBaseCurrency");
      this.jdField_void = ConnectionPool.init(this.properties);
      if ((this.jdField_void == null) || (this.jdField_void.length() == 0)) {
        this.e = false;
      } else {
        this.e = true;
      }
      this.dbType = this.properties.getProperty("ConnectionType");
      this.c = paramBoolean;
      getEntitlementMap(true);
      getObjectTypeMap(true);
      getLimitTypeMap(true);
      this.i = jdMethod_do();
      this.g = a();
      this.jdField_long = jdMethod_if();
      String str2 = paramProperties.getProperty("CacheMaxSize");
      int j;
      try
      {
        j = str2 == null ? 2500 : Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        j = 0;
      }
      if (j <= 0) {
        j = 0;
      }
      InetAddress localInetAddress = InetAddress.getLocalHost();
      byte[] arrayOfByte = localInetAddress.getAddress();
      int k = arrayOfByte[0];
      if (k < 0) {
        k += 256;
      }
      this.jdField_goto = k;
      this.jdField_goto <<= 8;
      k = arrayOfByte[1];
      if (k < 0) {
        k += 256;
      }
      this.jdField_goto += k;
      this.jdField_goto <<= 8;
      k = arrayOfByte[2];
      if (k < 0) {
        k += 256;
      }
      this.jdField_goto += k;
      this.jdField_goto <<= 8;
      k = arrayOfByte[3];
      if (k < 0) {
        k += 256;
      }
      this.jdField_goto += k;
      this.jdField_goto <<= 32;
      Connection localConnection = null;
      try
      {
        localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
        this.jdField_goto += DBUtil.getNextId(localConnection, this.dbType, "ent_cache_inst");
        DBUtil.commit(localConnection);
      }
      catch (Exception localException2)
      {
        if (localConnection != null) {
          DBUtil.rollback(localConnection);
        }
        throw new EntitlementException(localException2, str1, 2);
      }
      finally
      {
        if (localConnection != null) {
          DBUtil.returnConnection(this.jdField_void, localConnection);
        }
      }
      if (this.c) {
        this.jdField_null = new EntitlementCacheManager(this.entMap, this.objTypeMap, j, true);
      }
    }
    catch (Exception localException1)
    {
      throw new EntitlementException(localException1, str1, 1, "Unable to initialize entitlement adapter");
    }
  }
  
  private void isInitialized()
    throws EntitlementException
  {
    String str = "EntitlementAdapter.isInitialized";
    if (!this.e) {
      throw new EntitlementException(str, 1, "Entitlement adapter is not initialized");
    }
  }
  
  public Entitlements getCumulativeEntitlements(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeEntitlements";
    EntitlementGroup localEntitlementGroup = getEntitlementGroup(paramInt);
    Entitlements localEntitlements1 = new Entitlements();
    for (;;)
    {
      Entitlements localEntitlements2 = getRestrictedEntitlements(localEntitlementGroup.getGroupId());
      localEntitlements1.addAll(localEntitlements2);
      if (localEntitlementGroup.getParentId() == 0) {
        break;
      }
      localEntitlementGroup = getEntitlementGroup(localEntitlementGroup.getParentId());
    }
    return a(localEntitlements1);
  }
  
  Entitlements jdMethod_char(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeEntitlements";
    EntitlementGroup localEntitlementGroup = jdMethod_byte(paramConnection, paramInt);
    Entitlements localEntitlements1 = new Entitlements();
    for (;;)
    {
      Entitlements localEntitlements2 = jdMethod_else(paramConnection, localEntitlementGroup.getGroupId());
      localEntitlements1.addAll(localEntitlements2);
      if (localEntitlementGroup.getParentId() == 0) {
        break;
      }
      localEntitlementGroup = jdMethod_byte(paramConnection, localEntitlementGroup.getParentId());
    }
    return a(localEntitlements1);
  }
  
  public Entitlements getCumulativeEntitlements(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeEntitlements";
    Entitlements localEntitlements1 = getRestrictedEntitlements(paramEntitlementGroupMember);
    for (EntitlementGroup localEntitlementGroup = getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());; localEntitlementGroup = getEntitlementGroup(localEntitlementGroup.getParentId()))
    {
      Entitlements localEntitlements2 = getRestrictedEntitlements(localEntitlementGroup.getGroupId());
      localEntitlements1.addAll(localEntitlements2);
      if (localEntitlementGroup.getParentId() == 0) {
        break;
      }
    }
    return a(localEntitlements1);
  }
  
  private Entitlements a(Entitlements paramEntitlements)
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      if (localHashSet.contains(localEntitlement)) {
        localIterator.remove();
      } else {
        localHashSet.add(localEntitlement);
      }
    }
    return paramEntitlements;
  }
  
  Entitlements jdMethod_if(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeEntitlements";
    Entitlements localEntitlements1 = jdMethod_do(paramConnection, paramEntitlementGroupMember);
    for (EntitlementGroup localEntitlementGroup = jdMethod_byte(paramConnection, paramEntitlementGroupMember.getEntitlementGroupId());; localEntitlementGroup = jdMethod_byte(paramConnection, localEntitlementGroup.getParentId()))
    {
      Entitlements localEntitlements2 = jdMethod_else(paramConnection, localEntitlementGroup.getGroupId());
      localEntitlements1.addAll(localEntitlements2);
      if (localEntitlementGroup.getParentId() == 0) {
        break;
      }
    }
    return a(localEntitlements1);
  }
  
  private EntitlementGroup jdMethod_do(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCachedEntitlementGroup";
    EntitlementGroup localEntitlementGroup = null;
    if (this.c)
    {
      localEntitlementGroup = this.jdField_null.getEntitlementGroup(paramInt);
      if (localEntitlementGroup != null) {
        return localEntitlementGroup;
      }
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int j = 0;
    try
    {
      if (paramConnection == null)
      {
        paramConnection = DBUtil.getConnection(this.jdField_void, true, 2);
        j = 1;
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_id = ?");
      if (localResultSet.next()) {
        localEntitlementGroup = a(paramConnection, localResultSet, true);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      if (j != 0) {
        DBUtil.returnConnection(this.jdField_void, paramConnection);
      }
    }
    if (localEntitlementGroup == null) {
      throw new EntitlementException(str, 3, "Entitlement group not found");
    }
    return localEntitlementGroup;
  }
  
  EntitlementGroup a(Connection paramConnection, ResultSet paramResultSet, boolean paramBoolean)
    throws Exception
  {
    EntitlementGroup localEntitlementGroup1 = new EntitlementGroup();
    localEntitlementGroup1.setGroupId(paramResultSet.getInt(1));
    localEntitlementGroup1.setGroupName(paramResultSet.getString(2));
    localEntitlementGroup1.setEntGroupType(paramResultSet.getString(3));
    localEntitlementGroup1.setParentId(paramResultSet.getInt(4));
    localEntitlementGroup1.setSvcBureauId(Integer.toString(paramResultSet.getInt(5)));
    localEntitlementGroup1.setModifiedDate(paramResultSet.getTimestamp(6));
    localEntitlementGroup1.setRetrievalDate(new Date(System.currentTimeMillis()));
    a(paramConnection, localEntitlementGroup1);
    if ((paramBoolean) && (this.c))
    {
      EntitlementGroup localEntitlementGroup2 = this.jdField_null.getEntitlementGroup(localEntitlementGroup1.getGroupId());
      if (localEntitlementGroup2 == null) {
        this.jdField_null.addEntitlementGroup(localEntitlementGroup1);
      }
    }
    return localEntitlementGroup1;
  }
  
  private Limit a(ResultSet paramResultSet)
    throws Exception
  {
    Limit localLimit = new Limit();
    localLimit.setLimitId(paramResultSet.getInt(1));
    localLimit.setEntitlement(new Entitlement(paramResultSet.getString(2), paramResultSet.getString(3), paramResultSet.getString(4)));
    localLimit.setGroupId(paramResultSet.getInt(5));
    localLimit.setMemberType(paramResultSet.getString(6));
    localLimit.setMemberSubType(paramResultSet.getString(7));
    localLimit.setMemberId(paramResultSet.getString(8));
    localLimit.setParentId(paramResultSet.getInt(9));
    localLimit.setPeriod(paramResultSet.getInt(10));
    localLimit.setData(paramResultSet.getString(11));
    localLimit.setModifiedDate(paramResultSet.getTimestamp(12));
    localLimit.setAllowApproval(paramResultSet.getString(13).equalsIgnoreCase("Y"));
    localLimit.setRunningTotalType(paramResultSet.getString(14).charAt(0));
    localLimit.setRetrievalDate(new Date(System.currentTimeMillis()));
    localLimit.setCrossCurrency(paramResultSet.getString(15).equalsIgnoreCase("Y"));
    localLimit.setCurrencyCode(paramResultSet.getString(16));
    return localLimit;
  }
  
  private void a(Connection paramConnection, EntitlementGroup paramEntitlementGroup)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select prop_name, prop_value from entitlement_gprops where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select prop_name, prop_value from entitlement_gprops where ent_group_id = ?");
      while (localResultSet.next())
      {
        paramEntitlementGroup.getProperties().setCurrentProperty(localResultSet.getString(1));
        paramEntitlementGroup.getProperties().setValueOfCurrentProperty(localResultSet.getString(2));
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public boolean entitlementGroupExists(String paramString1, String paramString2, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.entitlementGroupExists";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from entitlement_group where name = ? and ent_group_type=? and svc_bureau_id=?");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setInt(3, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_group where name = ? and ent_group_type=? and svc_bureau_id=?");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public boolean entitlementGroupExists(String paramString, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.entitlementGroupExists";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from entitlement_group where name = ? and parent_id=?");
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setInt(2, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_group where name = ? and parent_id=?");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public boolean entitlementTypeExists(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.entitlementTypeExists";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from entitlement_list where operation_name = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_list where operation_name = ?");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public boolean limitExists(Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.limitExists";
    if ((this.c) && (this.jdField_null.limitExists(paramLimit))) {
      return true;
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("select count(*) from limits where ent_group_id = ? AND period = ? AND isCrossCurrency = ? AND currencyCode = ? ");
      if (paramLimit.getMemberType() == null) {
        localStringBuffer.append("AND member_type is null ");
      } else {
        localStringBuffer.append("AND member_type = ? ");
      }
      if (paramLimit.getMemberSubType() == null) {
        localStringBuffer.append("AND member_subtype is null ");
      } else {
        localStringBuffer.append("AND member_subtype = ? ");
      }
      if (paramLimit.getMemberId() == null) {
        localStringBuffer.append("AND member_id is null ");
      } else {
        localStringBuffer.append("AND member_id = ? ");
      }
      if (paramLimit.getLimitName() == null) {
        localStringBuffer.append("AND operation_name is null ");
      } else {
        localStringBuffer.append("AND operation_name = ? ");
      }
      if (paramLimit.getObjectType() == null) {
        localStringBuffer.append("AND object_type is null ");
      } else {
        localStringBuffer.append("AND object_type = ? ");
      }
      if (paramLimit.getObjectId() == null) {
        localStringBuffer.append("AND object_id is null ");
      } else {
        localStringBuffer.append("AND object_id = ? ");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramLimit.getGroupId());
      localPreparedStatement.setInt(2, paramLimit.getPeriod());
      if (paramLimit.isCrossCurrency()) {
        localPreparedStatement.setString(3, "Y");
      } else {
        localPreparedStatement.setString(3, "N");
      }
      localPreparedStatement.setString(4, paramLimit.getCurrencyCode());
      int j = 5;
      if (paramLimit.getMemberType() != null)
      {
        localPreparedStatement.setString(j, paramLimit.getMemberType());
        j++;
      }
      if (paramLimit.getMemberSubType() != null)
      {
        localPreparedStatement.setString(j, paramLimit.getMemberSubType());
        j++;
      }
      if (paramLimit.getMemberId() != null)
      {
        localPreparedStatement.setString(j, paramLimit.getMemberId());
        j++;
      }
      if (paramLimit.getLimitName() != null)
      {
        localPreparedStatement.setString(j, paramLimit.getLimitName());
        j++;
      }
      if (paramLimit.getObjectType() != null)
      {
        localPreparedStatement.setString(j, paramLimit.getObjectType());
        j++;
      }
      if (paramLimit.getObjectId() != null) {
        localPreparedStatement.setString(j, paramLimit.getObjectId());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localResultSet.next();
      int k = localResultSet.getInt(1);
      boolean bool = k > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private boolean jdMethod_int(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.limitExists";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select count(*) from limits where limit_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from limits where limit_id = ?");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public EntitlementGroup getEntitlementGroup(int paramInt)
    throws EntitlementException
  {
    isInitialized();
    return jdMethod_do(null, paramInt);
  }
  
  EntitlementGroup jdMethod_byte(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    isInitialized();
    return jdMethod_do(paramConnection, paramInt);
  }
  
  public synchronized HashMap getEntitlementMap(boolean paramBoolean)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementMap";
    if (!paramBoolean) {
      return this.entMap;
    }
    if (this.entMap == null) {
      this.entMap = new HashMap();
    }
    this.entMap.clear();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select operation_name from entitlement_list");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select operation_name from entitlement_list");
      int j = 0;
      while (localResultSet.next()) {
        this.entMap.put(localResultSet.getString(1), new Integer(j++));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return this.entMap;
  }
  
  public synchronized HashMap getLimitTypeMap(boolean paramBoolean)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getLimitTypeMap";
    if (!paramBoolean) {
      return this.limitMap;
    }
    if (this.limitMap == null) {
      this.limitMap = new HashMap();
    }
    this.limitMap.clear();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select operation_name from limit_list");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select operation_name from limit_list");
      int j = 0;
      while (localResultSet.next()) {
        this.limitMap.put(localResultSet.getString(1), new Integer(j++));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return this.limitMap;
  }
  
  public synchronized HashMap getObjectTypeMap(boolean paramBoolean)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getObjectTypeMap";
    if (!paramBoolean) {
      return this.objTypeMap;
    }
    if (this.objTypeMap == null) {
      this.objTypeMap = new HashMap();
    }
    this.objTypeMap.clear();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select object_type from object_type");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select object_type from object_type");
      int j = 0;
      while (localResultSet.next()) {
        this.objTypeMap.put(localResultSet.getString(1), new Integer(j++));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return this.objTypeMap;
  }
  
  public EntitlementGroups getTopEntitlementGroups()
    throws EntitlementException
  {
    return getEntitlementGroupsByParentId(0);
  }
  
  public EntitlementGroups getTopEntitlementGroupsBySvcBureau(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementGroupsByParentIdAndSvcBureau";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroup localEntitlementGroup = null;
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and svc_bureau_id = ?");
      localPreparedStatement.setInt(1, 0);
      localPreparedStatement.setInt(2, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and svc_bureau_id = ?");
      while (localResultSet.next())
      {
        localEntitlementGroup = a(localConnection, localResultSet, true);
        localEntitlementGroups.add(localEntitlementGroup);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroups getEntitlementGroupsByParentId(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementGroupsByParentId";
    Connection localConnection = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      EntitlementGroups localEntitlementGroups = jdField_long(localConnection, paramInt);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
  }
  
  public EntitlementGroup getEntitlementGroupsByParentId(int paramInt, ReopenableDBCookie paramReopenableDBCookie)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementGroupsByParentId";
    isInitialized();
    try
    {
      if (!paramReopenableDBCookie.isInitialized())
      {
        localObject1 = DBUtil.getConnection(this.jdField_void, true, 2);
        localObject2 = DBUtil.prepareStatement((Connection)localObject1, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ?");
        paramReopenableDBCookie.initialize(this.jdField_void, (Connection)localObject1, (PreparedStatement)localObject2, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ?");
      }
      if (paramReopenableDBCookie.isReset())
      {
        localObject1 = paramReopenableDBCookie.getPreparedStatement();
        ((PreparedStatement)localObject1).setInt(1, paramInt);
        paramReopenableDBCookie.open();
      }
      if (!paramReopenableDBCookie.isOpened()) {
        return null;
      }
      Object localObject1 = paramReopenableDBCookie.getConnection();
      Object localObject2 = paramReopenableDBCookie.getResultSet();
      if (!((ResultSet)localObject2).next())
      {
        paramReopenableDBCookie.close();
        return null;
      }
      return a((Connection)localObject1, (ResultSet)localObject2, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      paramReopenableDBCookie.close();
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      paramReopenableDBCookie.close();
      throw new EntitlementException(localException, str, 2);
    }
  }
  
  private EntitlementGroups jdField_long(Connection paramConnection, int paramInt)
    throws Exception
  {
    EntitlementGroup localEntitlementGroup = null;
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ?");
      while (localResultSet.next())
      {
        localEntitlementGroup = a(paramConnection, localResultSet, true);
        localEntitlementGroups.add(localEntitlementGroup);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroups getEntitlementGroupsByType(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementGroupsByType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroup localEntitlementGroup = null;
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_type = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_type = ?");
      while (localResultSet.next())
      {
        localEntitlementGroup = a(localConnection, localResultSet, true);
        localEntitlementGroups.add(localEntitlementGroup);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroups getEntitlementGroupsByTypeAndSvcBureau(String paramString, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementGroupsByTypeAndSvcBureau";
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroup localEntitlementGroup = null;
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localStatement = DBUtil.createStatement(localConnection);
      StringBuffer localStringBuffer = new StringBuffer("select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where ent_group_type = ");
      localStringBuffer.append("'" + paramString + "'");
      localStringBuffer.append(" and svc_bureau_id = ");
      localStringBuffer.append(String.valueOf(paramInt));
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        localEntitlementGroup = a(localConnection, localResultSet, true);
        localEntitlementGroups.add(localEntitlementGroup);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroups getChildrenByGroupType(int paramInt, String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getChildrenByGroupType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroup localEntitlementGroup = null;
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and ent_group_type = ?");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and ent_group_type = ?");
      while (localResultSet.next())
      {
        localEntitlementGroup = a(localConnection, localResultSet, true);
        localEntitlementGroups.add(localEntitlementGroup);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroup getChildrenByGroupType(int paramInt, String paramString, DBCookie paramDBCookie)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getChildrenByGroupType";
    try
    {
      isInitialized();
      if (!paramDBCookie.isInitialized())
      {
        localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
        localObject = DBUtil.prepareStatement(localConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and ent_group_type = ?");
        ((PreparedStatement)localObject).setInt(1, paramInt);
        ((PreparedStatement)localObject).setString(2, paramString);
        paramDBCookie.initialize(this.jdField_void, localConnection, (PreparedStatement)localObject, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where parent_id = ? and ent_group_type = ?");
      }
      if (!paramDBCookie.isOpened()) {
        return null;
      }
      Connection localConnection = paramDBCookie.getConnection();
      Object localObject = paramDBCookie.getResultSet();
      if (!((ResultSet)localObject).next())
      {
        paramDBCookie.close();
        return null;
      }
      return a(localConnection, (ResultSet)localObject, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      paramDBCookie.close();
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      paramDBCookie.close();
      throw new EntitlementException(localException, str, 2);
    }
  }
  
  public EntitlementGroup getEntitlementGroupByNameAndSvcBureau(String paramString1, String paramString2, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getEntitlementGroupByNameAndSvcBureau";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroup localEntitlementGroup = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where name = ? and svc_bureau_id = ? and ent_group_type = ?");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setInt(2, paramInt);
      localPreparedStatement.setString(3, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name, ent_group_type, parent_id, svc_bureau_id, modified_date from entitlement_group where name = ? and svc_bureau_id = ? and ent_group_type = ?");
      if (localResultSet.next()) {
        localEntitlementGroup = a(localConnection, localResultSet, true);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    if (localEntitlementGroup == null) {
      throw new EntitlementException(str, 3, "Entitlement group not found");
    }
    return localEntitlementGroup;
  }
  
  public EntitlementGroups getGroupsAdministeredBy(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getGroupsAdministeredBy";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select group_to_admin from entitlement_admin where ent_group_id = ? and member_id is null and permission_type like '%A%'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select group_to_admin from entitlement_admin where ent_group_id = ? and member_id is null and permission_type like '%A%'");
      while (localResultSet.next()) {
        localEntitlementGroups.add(jdMethod_do(localConnection, localResultSet.getInt(1)));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroups getGroupsAdministeredBy(int paramInt, String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getGroupsAdministeredBy";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select a.group_to_admin from entitlement_admin a, entitlement_group g where a.ent_group_id = ? and a.group_to_admin = g.ent_group_id and g.ent_group_type = ? and a.member_id is null and permission_type like '%A%'");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select a.group_to_admin from entitlement_admin a, entitlement_group g where a.ent_group_id = ? and a.group_to_admin = g.ent_group_id and g.ent_group_type = ? and a.member_id is null and permission_type like '%A%'");
      while (localResultSet.next()) {
        localEntitlementGroups.add(jdMethod_do(localConnection, localResultSet.getInt(1)));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementGroups getGroupsAdministeredBy(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getGroupsAdministeredBy";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct group_to_admin from entitlement_admin where ent_group_id = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) )  and permission_type like '%A%'");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct group_to_admin from entitlement_admin where ent_group_id = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) )  and permission_type like '%A%'");
      while (localResultSet.next()) {
        localEntitlementGroups.add(jdMethod_do(localConnection, localResultSet.getInt(1)));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public EntitlementAdmins getAdminInfoFor(int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getAdminInfoFor";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementAdmins localEntitlementAdmins = new EntitlementAdmins();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select group_to_admin, permission_type from entitlement_admin where ent_group_id = ? and member_id is null");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select group_to_admin, permission_type from entitlement_admin where ent_group_id = ? and member_id is null");
      while (localResultSet.next())
      {
        EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin();
        localEntitlementAdmin.setGranteeGroupId(paramInt);
        localEntitlementAdmin.setTargetGroupId(localResultSet.getInt(1));
        localEntitlementAdmin.setGranteeMemberType(null);
        localEntitlementAdmin.setGranteeMemberSubType(null);
        localEntitlementAdmin.setGranteeMemberId(null);
        String str2 = localResultSet.getString(2);
        if (str2.indexOf("A") != -1) {
          localEntitlementAdmin.setAdminister(true);
        }
        if (str2.indexOf("E") != -1) {
          localEntitlementAdmin.setExtend(true);
        }
        localEntitlementAdmins.add(localEntitlementAdmin);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementAdmins;
  }
  
  public EntitlementAdmins getAdminInfoFor(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getAdminInfoFor";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementAdmins localEntitlementAdmins = new EntitlementAdmins();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select group_to_admin, permission_type from entitlement_admin  where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select group_to_admin, permission_type from entitlement_admin  where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      while (localResultSet.next())
      {
        EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin();
        localEntitlementAdmin.setGranteeGroupId(paramEntitlementGroupMember.getEntitlementGroupId());
        localEntitlementAdmin.setTargetGroupId(localResultSet.getInt(1));
        localEntitlementAdmin.setGranteeMemberType(paramEntitlementGroupMember.getMemberType());
        localEntitlementAdmin.setGranteeMemberSubType(paramEntitlementGroupMember.getMemberSubType());
        localEntitlementAdmin.setGranteeMemberId(paramEntitlementGroupMember.getId());
        String str2 = localResultSet.getString(2);
        if (str2.indexOf("A") != -1) {
          localEntitlementAdmin.setAdminister(true);
        }
        if (str2.indexOf("E") != -1) {
          localEntitlementAdmin.setExtend(true);
        }
        localEntitlementAdmins.add(localEntitlementAdmin);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementAdmins;
  }
  
  public EntitlementAdmins getAdminInfoForTarget(int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getAdminInfoForTarget";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementAdmins localEntitlementAdmins = new EntitlementAdmins();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id, member_type, member_subtype, member_id, permission_type from entitlement_admin where group_to_admin = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, member_type, member_subtype, member_id, permission_type from entitlement_admin where group_to_admin = ?");
      while (localResultSet.next())
      {
        EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin();
        localEntitlementAdmin.setTargetGroupId(paramInt);
        localEntitlementAdmin.setGranteeGroupId(localResultSet.getInt(1));
        localEntitlementAdmin.setGranteeMemberType(localResultSet.getString(2));
        localEntitlementAdmin.setGranteeMemberSubType(localResultSet.getString(3));
        localEntitlementAdmin.setGranteeMemberId(localResultSet.getString(4));
        String str2 = localResultSet.getString(5);
        if (str2.indexOf("A") != -1) {
          localEntitlementAdmin.setAdminister(true);
        }
        if (str2.indexOf("E") != -1) {
          localEntitlementAdmin.setExtend(true);
        }
        localEntitlementAdmins.add(localEntitlementAdmin);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementAdmins;
  }
  
  public boolean isAdministratorOf(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.isAdministratorOf";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    int j = paramEntitlementAdmin.getGranteeMemberId() != null ? 1 : 0;
    String str2;
    if (j != 0) {
      str2 = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) ) and permission_type like '%A%'";
    } else {
      str2 = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null and permission_type like '%A%'";
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, paramEntitlementAdmin.getGranteeGroupId());
      localPreparedStatement.setInt(2, paramEntitlementAdmin.getTargetGroupId());
      if (j != 0)
      {
        localPreparedStatement.setString(3, paramEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(4, paramEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(5, paramEntitlementAdmin.getGranteeMemberId());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      boolean bool = localResultSet.next();
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public boolean canExtend(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.canExtend";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    int j = paramEntitlementAdmin.getGranteeMemberId() != null ? 1 : 0;
    String str2;
    if (j != 0) {
      str2 = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and ( member_id is null or ( member_type = ? and member_subtype = ? and member_id = ? ) ) and permission_type like '%E%'";
    } else {
      str2 = "select ent_group_id from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null and permission_type like '%E%'";
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, paramEntitlementAdmin.getGranteeGroupId());
      localPreparedStatement.setInt(2, paramEntitlementAdmin.getTargetGroupId());
      if (j != 0)
      {
        localPreparedStatement.setString(3, paramEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(4, paramEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(5, paramEntitlementAdmin.getGranteeMemberId());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      boolean bool = localResultSet.next();
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public EntitlementGroups getAdministratorsFor(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getAdministratorsFor";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id from entitlement_admin where group_to_admin = ? and member_id is null and permission_type like '%A%'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id from entitlement_admin where group_to_admin = ? and member_id is null and permission_type like '%A%'");
      while (localResultSet.next()) {
        localEntitlementGroups.add(jdMethod_do(localConnection, localResultSet.getInt(1)));
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlementGroups;
  }
  
  public void addMember(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addMember";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_gmemb( member_type, member_subtype, member_id, ent_group_id ) values ( ?,?,?,? )");
      localPreparedStatement.setString(1, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getId());
      localPreparedStatement.setInt(4, paramEntitlementGroupMember.getEntitlementGroupId());
      DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_gmemb( member_type, member_subtype, member_id, ent_group_id ) values ( ?,?,?,? )");
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyMember(EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyMember";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update entitlement_gmemb set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_gmemb set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update entitlement_del set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localPreparedStatement.setInt(5, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_del set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update limits set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localPreparedStatement.setInt(5, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update limits set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update entitlement_admin set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localPreparedStatement.setInt(5, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_admin set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update running_total set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localPreparedStatement.setInt(5, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update running_total set ent_group_id = ? where member_type = ? and member_subtype = ? AND member_id = ? and ent_group_id = ?");
      int j = paramEntitlementGroupMember.getEntitlementGroupId();
      paramEntitlementGroupMember.setEntitlementGroupId(paramInt);
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      paramEntitlementGroupMember.setEntitlementGroupId(j);
      DBUtil.commit(localConnection);
      if (this.c)
      {
        paramEntitlementGroupMember.setEntitlementGroupId(paramInt);
        this.jdField_null.deleteEntitlementGroupMember(paramEntitlementGroupMember);
        paramEntitlementGroupMember.setEntitlementGroupId(j);
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public EntitlementGroupMember getMember(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getMember";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?");
      localPreparedStatement.setString(1, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?");
      if (localResultSet.next())
      {
        paramEntitlementGroupMember.setEntitlementGroupId(localResultSet.getInt(1));
        EntitlementGroupMember localEntitlementGroupMember = paramEntitlementGroupMember;
        return localEntitlementGroupMember;
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    throw new EntitlementException(str, 5, "No Entitlement Group Member matching the specified criteria was found.");
  }
  
  EntitlementGroupMember jdMethod_for(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getMember";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select ent_group_id from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?");
      localPreparedStatement.setString(1, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?");
      if (localResultSet.next())
      {
        paramEntitlementGroupMember.setEntitlementGroupId(localResultSet.getInt(1));
        EntitlementGroupMember localEntitlementGroupMember = paramEntitlementGroupMember;
        return localEntitlementGroupMember;
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    throw new EntitlementException(str, 5, "No Entitlement Group Member matching the specified criteria was found.");
  }
  
  public EntitlementGroupMembers getMembers(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getMembers";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select member_id, member_type, member_subtype from entitlement_gmemb where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select member_id, member_type, member_subtype from entitlement_gmemb where ent_group_id = ?");
      EntitlementGroupMembers localEntitlementGroupMembers = new EntitlementGroupMembers();
      while (localResultSet.next())
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setId(localResultSet.getString(1));
        ((EntitlementGroupMember)localObject1).setMemberType(localResultSet.getString(2));
        ((EntitlementGroupMember)localObject1).setMemberSubType(localResultSet.getString(3));
        ((EntitlementGroupMember)localObject1).setEntitlementGroupId(paramInt);
        localEntitlementGroupMembers.add(localObject1);
      }
      Object localObject1 = localEntitlementGroupMembers;
      return localObject1;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public EntitlementGroupMembers getMembers(int paramInt, ConnectionHolder paramConnectionHolder)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getMembers";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      if (paramConnectionHolder.isInitialized())
      {
        localConnection = paramConnectionHolder.getConnection();
        localPreparedStatement = paramConnectionHolder.getPreparedStatement();
      }
      else
      {
        localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select member_id, member_type, member_subtype from entitlement_gmemb where ent_group_id = ?");
        paramConnectionHolder.initialize(this.jdField_void, localConnection, localPreparedStatement);
      }
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select member_id, member_type, member_subtype from entitlement_gmemb where ent_group_id = ?");
      EntitlementGroupMembers localEntitlementGroupMembers = new EntitlementGroupMembers();
      while (localResultSet.next())
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setId(localResultSet.getString(1));
        ((EntitlementGroupMember)localObject1).setMemberType(localResultSet.getString(2));
        ((EntitlementGroupMember)localObject1).setMemberSubType(localResultSet.getString(3));
        ((EntitlementGroupMember)localObject1).setEntitlementGroupId(paramInt);
        localEntitlementGroupMembers.add(localObject1);
      }
      Object localObject1 = localEntitlementGroupMembers;
      return localObject1;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  public int getNumMembers(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getNumMembers";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from entitlement_gmemb where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_gmemb where ent_group_id = ?");
      if (localResultSet.next())
      {
        j = localResultSet.getInt(1);
        return j;
      }
      int j = 0;
      return j;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public void removeMember(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.removeMember";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?");
      localPreparedStatement.setString(1, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_gmemb where member_type = ? and member_subtype = ? AND member_id = ?");
      if (this.c) {
        this.jdField_null.deleteEntitlementGroupMember(paramEntitlementGroupMember);
      }
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void addEntitlementType(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addEntitlementType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_list (operation_name) values (?)");
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_list (operation_name) values (?)");
      this.i.add(new EntitlementTypePropertyList(paramString));
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void deleteEntitlementType(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.deleteEntitlementType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_list where operation_name = ?");
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_list where operation_name = ?");
      Iterator localIterator = this.i.iterator();
      while (localIterator.hasNext()) {
        if (((EntitlementTypePropertyList)localIterator.next()).getOperationName().equals(paramString)) {
          localIterator.remove();
        }
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyEntitlementType(String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyEntitlementType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update entitlement_list set operation_name = ? where operation_name = ?");
      localPreparedStatement.setString(1, paramString2);
      localPreparedStatement.setString(2, paramString1);
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_list set operation_name = ? where operation_name = ?");
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void setEntitlementTypePropertyList(EntitlementTypePropertyList paramEntitlementTypePropertyList)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.setEntitlementTypePropertyList";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from ent_type_props where operation_name = ?");
      localPreparedStatement.setString(1, paramEntitlementTypePropertyList.getOperationName());
      DBUtil.executeUpdate(localPreparedStatement, "delete from ent_type_props where operation_name = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into ent_type_props (operation_name,prop_name,prop_value) values (?,?,?)");
      String str2 = paramEntitlementTypePropertyList.getOperationName();
      localPreparedStatement.setString(1, str2);
      EntitlementTypePropertyList.EntIterator localEntIterator = paramEntitlementTypePropertyList.iterator();
      Object localObject1;
      while (localEntIterator.hasNextName())
      {
        String str3 = localEntIterator.nextName();
        localPreparedStatement.setString(2, str3);
        while (localEntIterator.hasNextValue())
        {
          localObject1 = localEntIterator.nextValue();
          localPreparedStatement.setString(3, (String)localObject1);
          DBUtil.executeUpdate(localPreparedStatement, "insert into ent_type_props (operation_name,prop_name,prop_value) values (?,?,?)");
        }
      }
      paramEntitlementTypePropertyList = (EntitlementTypePropertyList)paramEntitlementTypePropertyList.clone();
      int j = 0;
      for (int k = this.i.size() - 1; k >= 0; k--)
      {
        localObject1 = (EntitlementTypePropertyList)this.i.get(k);
        if (((EntitlementTypePropertyList)localObject1).getOperationName().equals(str2))
        {
          this.i.set(k, paramEntitlementTypePropertyList);
          j = 1;
          break;
        }
      }
      if (j == 0) {
        this.i.add(paramEntitlementTypePropertyList);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void setLimitTypePropertyList(LimitTypePropertyList paramLimitTypePropertyList)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.setLimitTypePropertyList";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from limit_type_props where operation_name = ?");
      localPreparedStatement.setString(1, paramLimitTypePropertyList.getOperationName());
      DBUtil.executeUpdate(localPreparedStatement, "delete from limit_type_props where operation_name = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into limit_type_props (operation_name,prop_name,prop_value) values (?,?,?)");
      String str2 = paramLimitTypePropertyList.getOperationName();
      localPreparedStatement.setString(1, str2);
      LimitTypePropertyList.LimitIterator localLimitIterator = paramLimitTypePropertyList.iterator();
      Object localObject1;
      while (localLimitIterator.hasNextName())
      {
        String str3 = localLimitIterator.nextName();
        localPreparedStatement.setString(2, str3);
        while (localLimitIterator.hasNextValue())
        {
          localObject1 = localLimitIterator.nextValue();
          localPreparedStatement.setString(3, (String)localObject1);
          DBUtil.executeUpdate(localPreparedStatement, "insert into limit_type_props (operation_name,prop_name,prop_value) values (?,?,?)");
        }
      }
      paramLimitTypePropertyList = (LimitTypePropertyList)paramLimitTypePropertyList.clone();
      int j = 0;
      for (int k = this.g.size() - 1; k >= 0; k--)
      {
        localObject1 = (LimitTypePropertyList)this.g.get(k);
        if (((LimitTypePropertyList)localObject1).getOperationName().equals(str2))
        {
          this.g.set(k, paramLimitTypePropertyList);
          j = 1;
          break;
        }
      }
      if (j == 0) {
        this.g.add(paramLimitTypePropertyList);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void setObjectTypePropertyList(ObjectTypePropertyList paramObjectTypePropertyList)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.setObjectTypePropertyList";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from object_type_props where object_type = ?");
      localPreparedStatement.setString(1, paramObjectTypePropertyList.getOperationName());
      DBUtil.executeUpdate(localPreparedStatement, "delete from object_type_props where object_type = ?");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into object_type_props (object_type,prop_name,prop_value) values (?,?,?)");
      String str2 = paramObjectTypePropertyList.getOperationName();
      localPreparedStatement.setString(1, str2);
      ObjectTypePropertyList.ObjectIterator localObjectIterator = paramObjectTypePropertyList.iterator();
      Object localObject1;
      while (localObjectIterator.hasNextName())
      {
        String str3 = localObjectIterator.nextName();
        localPreparedStatement.setString(2, str3);
        while (localObjectIterator.hasNextValue())
        {
          localObject1 = localObjectIterator.nextValue();
          localPreparedStatement.setString(3, (String)localObject1);
          DBUtil.executeUpdate(localPreparedStatement, "insert into object_type_props (object_type,prop_name,prop_value) values (?,?,?)");
        }
      }
      paramObjectTypePropertyList = (ObjectTypePropertyList)paramObjectTypePropertyList.clone();
      int j = 0;
      for (int k = this.jdField_long.size() - 1; k >= 0; k--)
      {
        localObject1 = (ObjectTypePropertyList)this.jdField_long.get(k);
        if (((ObjectTypePropertyList)localObject1).getOperationName().equals(str2))
        {
          this.jdField_long.set(k, paramObjectTypePropertyList);
          j = 1;
          break;
        }
      }
      if (j == 0) {
        this.jdField_long.add(paramObjectTypePropertyList);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void addObjectType(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addObjectType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into object_type (object_type) values (?)");
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "insert into object_type (object_type) values (?)");
      this.jdField_long.add(new ObjectTypePropertyList(paramString));
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void deleteObjectType(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.deleteObjectType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from object_type where object_type = ?");
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "delete from object_type where object_type = ?");
      Iterator localIterator = this.jdField_long.iterator();
      while (localIterator.hasNext()) {
        if (((ObjectTypePropertyList)localIterator.next()).getObjectType().equals(paramString)) {
          localIterator.remove();
        }
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyObjectType(String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyObjectType";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update object_type set object_type = ? where object_type = ?");
      localPreparedStatement.setString(1, paramString2);
      localPreparedStatement.setString(2, paramString1);
      DBUtil.executeUpdate(localPreparedStatement, "update object_type set object_type = ? where object_type = ?");
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public EntitlementGroup[] getGroups()
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getGroups";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    ArrayList localArrayList = new ArrayList();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ent_group_id, name from entitlement_group order by ent_group_id");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ent_group_id, name from entitlement_group order by ent_group_id");
      while (localResultSet.next())
      {
        EntitlementGroup localEntitlementGroup = new EntitlementGroup();
        localEntitlementGroup.setGroupId(localResultSet.getInt(1));
        localEntitlementGroup.setGroupName(localResultSet.getString(2));
        localArrayList.add(localEntitlementGroup);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
    EntitlementGroup[] arrayOfEntitlementGroup = new EntitlementGroup[localArrayList.size()];
    return (EntitlementGroup[])localArrayList.toArray(arrayOfEntitlementGroup);
  }
  
  public EntitlementGroup addEntitlementGroup(EntitlementGroup paramEntitlementGroup, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addEntitlementGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_group (ent_group_id,name,ent_group_type,parent_id,svc_bureau_id,modified_date) values (?,?,?,?,?,?)");
      paramEntitlementGroup.setGroupId(DBUtil.getNextId(localConnection, this.dbType, "ent_group_id"));
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroup.getGroupName());
      localPreparedStatement.setString(3, paramEntitlementGroup.getEntGroupType());
      localPreparedStatement.setInt(4, paramEntitlementGroup.getParentId());
      localPreparedStatement.setInt(5, Integer.parseInt(paramEntitlementGroup.getSvcBureauId()));
      localPreparedStatement.setTimestamp(6, DBUtil.getCurrentTimestamp());
      DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_group (ent_group_id,name,ent_group_type,parent_id,svc_bureau_id,modified_date) values (?,?,?,?,?,?)");
      jdMethod_do(localConnection, paramEntitlementGroup);
      if (paramInt != 0)
      {
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
        localPreparedStatement.setInt(1, paramInt);
        localPreparedStatement.setString(2, null);
        localPreparedStatement.setString(3, null);
        localPreparedStatement.setString(4, null);
        localPreparedStatement.setInt(5, paramEntitlementGroup.getGroupId());
        localPreparedStatement.setString(6, "A");
        DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      }
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.addEntitlementGroup(paramEntitlementGroup);
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
    return paramEntitlementGroup;
  }
  
  public void deleteEntitlementGroup(EntitlementGroup paramEntitlementGroup)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.deleteEntitlementGroup";
    Connection localConnection = null;
    isInitialized();
    if (!jdMethod_new(paramEntitlementGroup.getGroupId())) {
      throw new EntitlementException(str, 3, "Entitlement group to delete does not exist");
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      jdMethod_if(localConnection, paramEntitlementGroup);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException2)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException2, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException3) {}
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
  }
  
  private void jdMethod_if(Connection paramConnection, EntitlementGroup paramEntitlementGroup)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from entitlement_group where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_group where ent_group_id = ?");
      jdMethod_new(paramConnection, paramEntitlementGroup.getGroupId());
      if (this.c) {
        this.jdField_null.deleteEntitlementGroup(paramEntitlementGroup.getGroupId());
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from entitlement_admin where group_to_admin = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_admin where group_to_admin = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from entitlement_admin where ent_group_id = ? and member_type is null");
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_admin where ent_group_id = ? and member_type is null");
      EntitlementGroups localEntitlementGroups = jdField_long(paramConnection, paramEntitlementGroup.getGroupId());
      for (int j = 0; j < localEntitlementGroups.size(); j++)
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localEntitlementGroups.get(j);
        jdMethod_if(paramConnection, localEntitlementGroup);
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public void modifyEntitlementGroup(EntitlementGroup paramEntitlementGroup)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyEntitlementGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    if (!jdMethod_new(paramEntitlementGroup.getGroupId())) {
      throw new EntitlementException(str, 3, "Entitlement group to modify does not exist");
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update entitlement_group set name = ?, ent_group_type = ?, parent_id = ?, svc_bureau_id = ?, modified_date = ? where ent_group_id = ?");
      localPreparedStatement.setString(1, paramEntitlementGroup.getGroupName());
      localPreparedStatement.setString(2, paramEntitlementGroup.getEntGroupType());
      localPreparedStatement.setInt(3, paramEntitlementGroup.getParentId());
      localPreparedStatement.setInt(4, Integer.parseInt(paramEntitlementGroup.getSvcBureauId()));
      localPreparedStatement.setTimestamp(5, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setInt(6, paramEntitlementGroup.getGroupId());
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_group set name = ?, ent_group_type = ?, parent_id = ?, svc_bureau_id = ?, modified_date = ? where ent_group_id = ?");
      jdMethod_do(localConnection, paramEntitlementGroup);
      jdMethod_new(localConnection, paramEntitlementGroup.getGroupId());
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
    if (this.c) {
      this.jdField_null.modifyEntitlementGroup(paramEntitlementGroup);
    }
  }
  
  private boolean jdMethod_new(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.entitlementGroupExists";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from entitlement_group where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_group where ent_group_id = ?");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private void jdMethod_do(Connection paramConnection, EntitlementGroup paramEntitlementGroup)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from entitlement_gprops where ent_group_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_gprops where ent_group_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into entitlement_gprops ( ent_group_id, prop_name, prop_value ) values (?,?,?)");
      localPreparedStatement.setInt(1, paramEntitlementGroup.getGroupId());
      Enumeration localEnumeration = paramEntitlementGroup.getProperties().getPropertyNames();
      while (localEnumeration.hasMoreElements())
      {
        paramEntitlementGroup.getProperties().setCurrentProperty((String)localEnumeration.nextElement());
        localPreparedStatement.setString(2, paramEntitlementGroup.getProperties().getCurrentProperty());
        localPreparedStatement.setString(3, paramEntitlementGroup.getProperties().getValueOfCurrentProperty());
        DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_gprops ( ent_group_id, prop_name, prop_value ) values (?,?,?)");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public Entitlements getRestrictedEntitlements(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getRestrictedEntitlements";
    Entitlements localEntitlements = null;
    if (this.c)
    {
      localEntitlements = this.jdField_null.getRestrictedEntitlements(paramInt);
      if (localEntitlements != null) {
        return localEntitlements;
      }
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    localEntitlements = new Entitlements();
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type is null and member_subtype is null and member_id is null");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type is null and member_subtype is null and member_id is null");
      while (localResultSet.next())
      {
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setOperationName(localResultSet.getString(1));
        localEntitlement.setObjectType(localResultSet.getString(2));
        localEntitlement.setObjectId(localResultSet.getString(3));
        localEntitlements.add(localEntitlement);
      }
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.setRestrictedEntitlements(paramInt, localEntitlements);
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlements;
  }
  
  Entitlements jdMethod_else(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getRestrictedEntitlements";
    Entitlements localEntitlements = null;
    if (this.c)
    {
      localEntitlements = this.jdField_null.getRestrictedEntitlements(paramInt);
      if (localEntitlements != null) {
        return localEntitlements;
      }
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    localEntitlements = new Entitlements();
    isInitialized();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type is null and member_subtype is null and member_id is null");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type is null and member_subtype is null and member_id is null");
      while (localResultSet.next())
      {
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setOperationName(localResultSet.getString(1));
        localEntitlement.setObjectType(localResultSet.getString(2));
        localEntitlement.setObjectId(localResultSet.getString(3));
        localEntitlements.add(localEntitlement);
      }
      if (this.c) {
        this.jdField_null.setRestrictedEntitlements(paramInt, localEntitlements);
      }
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localEntitlements;
  }
  
  public Entitlements getRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getRestrictedEntitlements";
    Entitlements localEntitlements = null;
    if (this.c)
    {
      localEntitlements = this.jdField_null.getRestrictedEntitlements(paramEntitlementGroupMember);
      if (localEntitlements != null) {
        return localEntitlements;
      }
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    localEntitlements = new Entitlements();
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      while (localResultSet.next())
      {
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setOperationName(localResultSet.getString(1));
        localEntitlement.setObjectType(localResultSet.getString(2));
        localEntitlement.setObjectId(localResultSet.getString(3));
        localEntitlements.add(localEntitlement);
      }
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.setRestrictedEntitlements(paramEntitlementGroupMember, localEntitlements);
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localEntitlements;
  }
  
  Entitlements jdMethod_do(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getRestrictedEntitlements";
    Entitlements localEntitlements = null;
    if (this.c)
    {
      localEntitlements = this.jdField_null.getRestrictedEntitlements(paramEntitlementGroupMember);
      if (localEntitlements != null) {
        return localEntitlements;
      }
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    localEntitlements = new Entitlements();
    isInitialized();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select operation_name, object_type, object_id from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      while (localResultSet.next())
      {
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setOperationName(localResultSet.getString(1));
        localEntitlement.setObjectType(localResultSet.getString(2));
        localEntitlement.setObjectId(localResultSet.getString(3));
        localEntitlements.add(localEntitlement);
      }
      if (this.c) {
        this.jdField_null.setRestrictedEntitlements(paramEntitlementGroupMember, localEntitlements);
      }
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localEntitlements;
  }
  
  public void setRestrictedEntitlements(int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.setRestrictedEntitlements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_del where ent_group_id = ? and member_id is null");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_del where ent_group_id = ? and member_id is null");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if ((paramEntitlements != null) && (paramEntitlements.size() > 0))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)");
        for (int j = 0; j < paramEntitlements.size(); j++)
        {
          localPreparedStatement.setInt(1, paramInt);
          localPreparedStatement.setString(2, ((Entitlement)paramEntitlements.get(j)).getOperationName());
          localPreparedStatement.setString(3, ((Entitlement)paramEntitlements.get(j)).getObjectType());
          localPreparedStatement.setString(4, ((Entitlement)paramEntitlements.get(j)).getObjectId());
          DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)");
        }
      }
      jdMethod_new(localConnection, paramInt);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.setRestrictedEntitlements(paramInt, paramEntitlements);
      }
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void setRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.setRestrictedEntitlements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if ((paramEntitlements != null) && (paramEntitlements.size() > 0))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)");
        for (int j = 0; j < paramEntitlements.size(); j++)
        {
          localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
          localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
          localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
          localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
          localPreparedStatement.setString(5, ((Entitlement)paramEntitlements.get(j)).getOperationName());
          localPreparedStatement.setString(6, ((Entitlement)paramEntitlements.get(j)).getObjectType());
          localPreparedStatement.setString(7, ((Entitlement)paramEntitlements.get(j)).getObjectId());
          DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)");
        }
      }
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.setRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlements);
      }
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void addRestrictedEntitlements(int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addRestrictedEntitlements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      Entitlement localEntitlement1 = null;
      Entitlement localEntitlement2 = null;
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type is null");
      localPreparedStatement2.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement2, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type is null");
      while (localResultSet.next())
      {
        localEntitlement1 = new Entitlement(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3));
        for (j = 0; j < paramEntitlements.size(); j++)
        {
          localEntitlement2 = (Entitlement)paramEntitlements.get(j);
          if (localEntitlement2.equals(localEntitlement1)) {
            throw new EntitlementException(str, 15, "Entitlement adapter adding duplicate Entitlement");
          }
        }
      }
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)");
      localPreparedStatement1.setInt(1, paramInt);
      for (int j = 0; j < paramEntitlements.size(); j++)
      {
        localPreparedStatement1.setString(2, ((Entitlement)paramEntitlements.get(j)).getOperationName());
        localPreparedStatement1.setString(3, ((Entitlement)paramEntitlements.get(j)).getObjectType());
        localPreparedStatement1.setString(4, ((Entitlement)paramEntitlements.get(j)).getObjectId());
        DBUtil.executeUpdate(localPreparedStatement1, "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)");
      }
      jdMethod_new(localConnection, paramInt);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.addRestrictedEntitlements(paramInt, paramEntitlements);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(localPreparedStatement2, localResultSet);
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement1);
    }
  }
  
  public void addRestrictedEntitlementsUnsafeNoDupCheck(int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addRestrictedEntitlementsUnsafeNoDupCheck";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      Entitlements localEntitlements = new Entitlements();
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type is null");
      localPreparedStatement2.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement2, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type is null");
      while (localResultSet.next())
      {
        Entitlement localEntitlement1 = new Entitlement(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3));
        for (int k = 0; k < paramEntitlements.size(); k++)
        {
          Entitlement localEntitlement2 = (Entitlement)paramEntitlements.get(k);
          if (localEntitlement2.equals(localEntitlement1)) {
            localEntitlements.add(localEntitlement2);
          }
        }
      }
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)");
      localPreparedStatement1.setInt(1, paramInt);
      for (int j = 0; j < paramEntitlements.size(); j++) {
        if (!localEntitlements.contains(paramEntitlements.getEntitlement(j)))
        {
          localPreparedStatement1.setString(2, ((Entitlement)paramEntitlements.get(j)).getOperationName());
          localPreparedStatement1.setString(3, ((Entitlement)paramEntitlements.get(j)).getObjectType());
          localPreparedStatement1.setString(4, ((Entitlement)paramEntitlements.get(j)).getObjectId());
          DBUtil.executeUpdate(localPreparedStatement1, "insert into entitlement_del (ent_group_id, operation_name, object_type, object_id) values (?,?,?,?)");
        }
      }
      jdMethod_new(localConnection, paramInt);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.addRestrictedEntitlements(paramInt, paramEntitlements);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(localPreparedStatement2, localResultSet);
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement1);
    }
  }
  
  public void addRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addRestrictedEntitlements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      Entitlement localEntitlement1 = null;
      Entitlement localEntitlement2 = null;
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type = ? and r.member_subtype = ? and r.member_id = ?");
      localPreparedStatement2.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement2.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement2.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement2.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement2, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type = ? and r.member_subtype = ? and r.member_id = ?");
      while (localResultSet.next())
      {
        localEntitlement1 = new Entitlement(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3));
        for (j = 0; j < paramEntitlements.size(); j++)
        {
          localEntitlement2 = (Entitlement)paramEntitlements.get(j);
          if (localEntitlement2.equals(localEntitlement1)) {
            throw new EntitlementException(str, 15, "Entitlement adapter adding duplicate Entitlement");
          }
        }
      }
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)");
      localPreparedStatement1.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement1.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement1.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement1.setString(4, paramEntitlementGroupMember.getId());
      for (int j = 0; j < paramEntitlements.size(); j++)
      {
        localPreparedStatement1.setString(5, ((Entitlement)paramEntitlements.get(j)).getOperationName());
        localPreparedStatement1.setString(6, ((Entitlement)paramEntitlements.get(j)).getObjectType());
        localPreparedStatement1.setString(7, ((Entitlement)paramEntitlements.get(j)).getObjectId());
        DBUtil.executeUpdate(localPreparedStatement1, "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)");
      }
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.addRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlements);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(localPreparedStatement2, localResultSet);
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement1);
    }
  }
  
  public void addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addRestrictedEntitlementsUnsafeNoDupCheck";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      Entitlements localEntitlements = new Entitlements();
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type = ? and r.member_subtype = ? and r.member_id = ?");
      localPreparedStatement2.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement2.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement2.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement2.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement2, "select r.operation_name, r.object_type, r.object_id from entitlement_del r, entitlement_group e where r.ent_group_id = e.ent_group_id and r.ent_group_id = ? and r.member_type = ? and r.member_subtype = ? and r.member_id = ?");
      while (localResultSet.next())
      {
        Entitlement localEntitlement1 = new Entitlement(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3));
        for (int k = 0; k < paramEntitlements.size(); k++)
        {
          Entitlement localEntitlement2 = (Entitlement)paramEntitlements.get(k);
          if (localEntitlement2.equals(localEntitlement1)) {
            localEntitlements.add(localEntitlement2);
          }
        }
      }
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)");
      localPreparedStatement1.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement1.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement1.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement1.setString(4, paramEntitlementGroupMember.getId());
      for (int j = 0; j < paramEntitlements.size(); j++) {
        if (!localEntitlements.contains(paramEntitlements.getEntitlement(j)))
        {
          localPreparedStatement1.setString(5, ((Entitlement)paramEntitlements.get(j)).getOperationName());
          localPreparedStatement1.setString(6, ((Entitlement)paramEntitlements.get(j)).getObjectType());
          localPreparedStatement1.setString(7, ((Entitlement)paramEntitlements.get(j)).getObjectId());
          DBUtil.executeUpdate(localPreparedStatement1, "insert into entitlement_del (ent_group_id, member_type, member_subtype, member_id, operation_name, object_type, object_id) values (?,?,?,?,?,?,?)");
        }
      }
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.addRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlements);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(localPreparedStatement2, localResultSet);
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement1);
    }
  }
  
  public void removeRestrictedEntitlements(int paramInt, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.removeRestrictedEntitlements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      for (int j = 0; j < paramEntitlements.size(); j++)
      {
        String str2 = "delete from entitlement_del where ent_group_id = ? and member_id is null AND ";
        Entitlement localEntitlement = paramEntitlements.getEntitlement(j);
        if (!a(localConnection, paramInt, localEntitlement.getOperationName(), localEntitlement.getObjectType(), localEntitlement.getObjectId())) {
          throw new EntitlementException(str1, 13, "Restricted entitlement to remove does not exist");
        }
        if (localEntitlement.getOperationName() == null) {
          str2 = str2.concat("operation_name is null AND ");
        } else {
          str2 = str2.concat("operation_name = '" + DBUtil.escapeSQLStringLiteral(localEntitlement.getOperationName()) + "' AND ");
        }
        if (localEntitlement.getObjectType() == null) {
          str2 = str2.concat("object_type is null AND ");
        } else {
          str2 = str2.concat("object_type = '" + DBUtil.escapeSQLStringLiteral(localEntitlement.getObjectType()) + "' AND ");
        }
        if (localEntitlement.getObjectId() == null) {
          str2 = str2.concat("object_id is null");
        } else {
          str2 = str2.concat("object_id = '" + DBUtil.escapeSQLStringLiteral(localEntitlement.getObjectId()) + "'");
        }
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
        localPreparedStatement.setInt(1, paramInt);
        DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_del where ent_group_id = ? and member_id is null AND ");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      jdMethod_new(localConnection, paramInt);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.removeRestrictedEntitlements(paramInt, paramEntitlements);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str1, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void removeRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.removeRestrictedEntitlements";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      for (int j = 0; j < paramEntitlements.size(); j++)
      {
        String str2 = "delete from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ? AND ";
        Entitlement localEntitlement = paramEntitlements.getEntitlement(j);
        if (!a(localConnection, paramEntitlementGroupMember, localEntitlement.getOperationName(), localEntitlement.getObjectType(), localEntitlement.getObjectId())) {
          throw new EntitlementException(str1, 13, "Restricted entitlement to remove does not exist");
        }
        if (localEntitlement.getOperationName() == null) {
          str2 = str2.concat("operation_name is null AND ");
        } else {
          str2 = str2.concat("operation_name = '" + DBUtil.escapeSQLStringLiteral(localEntitlement.getOperationName()) + "' AND ");
        }
        if (localEntitlement.getObjectType() == null) {
          str2 = str2.concat("object_type is null AND ");
        } else {
          str2 = str2.concat("object_type = '" + DBUtil.escapeSQLStringLiteral(localEntitlement.getObjectType()) + "' AND ");
        }
        if (localEntitlement.getObjectId() == null) {
          str2 = str2.concat("object_id is null");
        } else {
          str2 = str2.concat("object_id = '" + DBUtil.escapeSQLStringLiteral(localEntitlement.getObjectId()) + "'");
        }
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
        localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
        localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
        localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
        localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
        DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ? AND ");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.removeRestrictedEntitlements(paramEntitlementGroupMember, paramEntitlements);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str1, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyRestrictedEntitlement(int paramInt, Entitlement paramEntitlement1, Entitlement paramEntitlement2)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.modifyRestrictedEntitlement";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      String str2 = "update entitlement_del set operation_name = ?, object_type = ?, object_id = ? where ent_group_id = ? and member_id is null AND ";
      if (paramEntitlement1.getOperationName() == null) {
        str2 = str2.concat("operation_name is null AND ");
      } else {
        str2 = str2.concat("operation_name = '" + DBUtil.escapeSQLStringLiteral(paramEntitlement1.getOperationName()) + "' AND ");
      }
      if (paramEntitlement1.getObjectType() == null) {
        str2 = str2.concat("object_type is null AND ");
      } else {
        str2 = str2.concat("object_type = '" + DBUtil.escapeSQLStringLiteral(paramEntitlement1.getObjectType()) + "' AND ");
      }
      if (paramEntitlement1.getObjectId() == null) {
        str2 = str2.concat("object_id is null");
      } else {
        str2 = str2.concat("object_id = '" + DBUtil.escapeSQLStringLiteral(paramEntitlement1.getObjectId()) + "'");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setString(1, paramEntitlement2.getOperationName());
      localPreparedStatement.setString(2, paramEntitlement2.getObjectType());
      localPreparedStatement.setString(3, paramEntitlement2.getObjectId());
      localPreparedStatement.setInt(4, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_del set operation_name = ?, object_type = ?, object_id = ? where ent_group_id = ? and member_id is null AND ");
      jdMethod_new(localConnection, paramInt);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.modifyRestrictedEntitlement(paramInt, paramEntitlement1, paramEntitlement2);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str1, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyRestrictedEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement1, Entitlement paramEntitlement2)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.modifyRestrictedEntitlement";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      String str2 = "update entitlement_del set operation_name = ?, object_type = ?, object_id = ? where ent_group_id = ? AND member_type = ? AND member_subtype = ? AND member_id = ? AND ";
      if (paramEntitlement1.getOperationName() == null) {
        str2 = str2.concat("operation_name is null AND ");
      } else {
        str2 = str2.concat("operation_name = '" + DBUtil.escapeSQLStringLiteral(paramEntitlement1.getOperationName()) + "' AND ");
      }
      if (paramEntitlement1.getObjectType() == null) {
        str2 = str2.concat("object_type is null AND ");
      } else {
        str2 = str2.concat("object_type = '" + DBUtil.escapeSQLStringLiteral(paramEntitlement1.getObjectType()) + "' AND ");
      }
      if (paramEntitlement1.getObjectId() == null) {
        str2 = str2.concat("object_id is null");
      } else {
        str2 = str2.concat("object_id = '" + DBUtil.escapeSQLStringLiteral(paramEntitlement1.getObjectId()) + "'");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setString(1, paramEntitlement2.getOperationName());
      localPreparedStatement.setString(2, paramEntitlement2.getObjectType());
      localPreparedStatement.setString(3, paramEntitlement2.getObjectId());
      localPreparedStatement.setInt(4, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(5, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(6, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(7, paramEntitlementGroupMember.getId());
      DBUtil.executeUpdate(localPreparedStatement, "update entitlement_del set operation_name = ?, object_type = ?, object_id = ? where ent_group_id = ? AND member_type = ? AND member_subtype = ? AND member_id = ? AND ");
      jdMethod_int(localConnection, paramEntitlementGroupMember);
      DBUtil.commit(localConnection);
      if (this.c) {
        this.jdField_null.modifyRestrictedEntitlement(paramEntitlementGroupMember, paramEntitlement1, paramEntitlement2);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str1, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public boolean restrictedEntitlementExists(int paramInt, String paramString1, String paramString2, String paramString3)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.restrictedEntitlementExists";
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      boolean bool = a(localConnection, paramInt, paramString1, paramString2, paramString3);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
  }
  
  public boolean restrictedEntitlementExists(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2, String paramString3)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.restrictedEntitlementExists";
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      boolean bool = a(localConnection, paramEntitlementGroupMember, paramString1, paramString2, paramString3);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
  }
  
  private boolean a(Connection paramConnection, int paramInt, String paramString1, String paramString2, String paramString3)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.restrictedEntitlementExists";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      String str2 = "select count(*) from entitlement_del where ent_group_id = ? and member_id is null AND ";
      if (paramString1 == null) {
        str2 = str2.concat("operation_name is null AND ");
      } else {
        str2 = str2.concat("operation_name = '" + DBUtil.escapeSQLStringLiteral(paramString1) + "' AND ");
      }
      if (paramString2 == null) {
        str2 = str2.concat("object_type is null AND ");
      } else {
        str2 = str2.concat("object_type = '" + DBUtil.escapeSQLStringLiteral(paramString2) + "' AND ");
      }
      if (paramString3 == null) {
        str2 = str2.concat("object_id is null");
      } else {
        str2 = str2.concat("object_id = '" + DBUtil.escapeSQLStringLiteral(paramString3) + "'");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_del where ent_group_id = ? and member_id is null AND ");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private boolean a(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2, String paramString3)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.restrictedEntitlementExists";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      String str2 = "select count(*) from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ? AND ";
      if (paramString1 == null) {
        str2 = str2.concat("operation_name is null AND ");
      } else {
        str2 = str2.concat("operation_name = '" + DBUtil.escapeSQLStringLiteral(paramString1) + "' AND ");
      }
      if (paramString2 == null) {
        str2 = str2.concat("object_type is null AND ");
      } else {
        str2 = str2.concat("object_type = '" + DBUtil.escapeSQLStringLiteral(paramString2) + "' AND ");
      }
      if (paramString3 == null) {
        str2 = str2.concat("object_id is null");
      } else {
        str2 = str2.concat("object_id = '" + DBUtil.escapeSQLStringLiteral(paramString3) + "'");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from entitlement_del where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ? AND ");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  public void modifyAdminEntitlementGroups(int paramInt, EntitlementAdmins paramEntitlementAdmins)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyAdminEntitlementGroups";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int j = 0;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_admin where ent_group_id = ? and member_type is null");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_admin where ent_group_id = ? and member_type is null");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      for (int k = 0; k < paramEntitlementAdmins.size(); k++)
      {
        EntitlementAdmin localEntitlementAdmin = (EntitlementAdmin)paramEntitlementAdmins.get(k);
        localPreparedStatement.setInt(1, localEntitlementAdmin.getGranteeGroupId());
        localPreparedStatement.setString(2, localEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(3, localEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(4, localEntitlementAdmin.getGranteeMemberId());
        localPreparedStatement.setInt(5, localEntitlementAdmin.getTargetGroupId());
        StringBuffer localStringBuffer = new StringBuffer();
        if (localEntitlementAdmin.canAdminister()) {
          localStringBuffer.append("A");
        }
        if (localEntitlementAdmin.canExtend()) {
          localStringBuffer.append("E");
        }
        localPreparedStatement.setString(6, localStringBuffer.toString());
        DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyAdminEntitlementGroups(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmins paramEntitlementAdmins)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyAdminEntitlementGroups";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int j = 0;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_admin where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_admin where ent_group_id = ? and member_type = ? and member_subtype = ? and member_id = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      for (int k = 0; k < paramEntitlementAdmins.size(); k++)
      {
        EntitlementAdmin localEntitlementAdmin = (EntitlementAdmin)paramEntitlementAdmins.get(k);
        localPreparedStatement.setInt(1, localEntitlementAdmin.getGranteeGroupId());
        localPreparedStatement.setString(2, localEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(3, localEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(4, localEntitlementAdmin.getGranteeMemberId());
        localPreparedStatement.setInt(5, localEntitlementAdmin.getTargetGroupId());
        StringBuffer localStringBuffer = new StringBuffer();
        if (localEntitlementAdmin.canAdminister()) {
          localStringBuffer.append("A");
        }
        if (localEntitlementAdmin.canExtend()) {
          localStringBuffer.append("E");
        }
        localPreparedStatement.setString(6, localStringBuffer.toString());
        DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void addAdministratorGroup(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addAdministratorGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    int j = paramEntitlementAdmin.getGranteeGroupId();
    int k = paramEntitlementAdmin.getTargetGroupId();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      localPreparedStatement.setInt(1, j);
      localPreparedStatement.setString(2, paramEntitlementAdmin.getGranteeMemberType());
      localPreparedStatement.setString(3, paramEntitlementAdmin.getGranteeMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementAdmin.getGranteeMemberId());
      localPreparedStatement.setInt(5, k);
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramEntitlementAdmin.canAdminister()) {
        localStringBuffer.append("A");
      }
      if (paramEntitlementAdmin.canExtend()) {
        localStringBuffer.append("E");
      }
      localPreparedStatement.setString(6, localStringBuffer.toString());
      DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void setAdministratorGroup(int paramInt, EntitlementAdmins paramEntitlementAdmins)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.setAdministratorGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from entitlement_admin where group_to_admin = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from entitlement_admin where group_to_admin = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      localPreparedStatement.setInt(5, paramInt);
      for (int j = 0; j < paramEntitlementAdmins.size(); j++)
      {
        EntitlementAdmin localEntitlementAdmin = (EntitlementAdmin)paramEntitlementAdmins.get(j);
        localPreparedStatement.setInt(1, localEntitlementAdmin.getGranteeGroupId());
        localPreparedStatement.setString(2, localEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(3, localEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(4, localEntitlementAdmin.getGranteeMemberId());
        StringBuffer localStringBuffer = new StringBuffer();
        if (localEntitlementAdmin.canAdminister()) {
          localStringBuffer.append("A");
        }
        if (localEntitlementAdmin.canExtend()) {
          localStringBuffer.append("E");
        }
        localPreparedStatement.setString(6, localStringBuffer.toString());
        DBUtil.executeUpdate(localPreparedStatement, "insert into entitlement_admin (ent_group_id, member_type, member_subtype, member_id, group_to_admin, permission_type ) values (?,?,?,?,?,?)");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void deleteAdministratorGroup(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.deleteAdministratorGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    int j = paramEntitlementAdmin.getGranteeGroupId();
    int k = paramEntitlementAdmin.getTargetGroupId();
    int m = paramEntitlementAdmin.getGranteeMemberId() != null ? 1 : 0;
    String str2;
    if (m != 0) {
      str2 = "delete from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_type = ? and member_subtype = ? and member_id = ?";
    } else {
      str2 = "delete from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null";
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, j);
      localPreparedStatement.setInt(2, k);
      if (m != 0)
      {
        localPreparedStatement.setString(3, paramEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(4, paramEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(5, paramEntitlementAdmin.getGranteeMemberId());
      }
      DBUtil.executeUpdate(localPreparedStatement, str2);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str1, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyAdministratorGroup(EntitlementAdmin paramEntitlementAdmin1, EntitlementAdmin paramEntitlementAdmin2)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.modifyAdministratorGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    if (!jdMethod_if(paramEntitlementAdmin1)) {
      throw new EntitlementException(str1, 12, "Administrator group to modify does not exist");
    }
    if (paramEntitlementAdmin1.getTargetGroupId() != paramEntitlementAdmin2.getTargetGroupId()) {
      throw new EntitlementException(str1, 10, "Target groups given are not the same");
    }
    int j = paramEntitlementAdmin1.getGranteeMemberId() != null ? 1 : 0;
    String str2;
    if (j != 0) {
      str2 = "update entitlement_admin set permission_type = ? where ent_group_id = ? and group_to_admin = ? and member_type = ? and member_subtype = ? and member_id = ?";
    } else {
      str2 = "update entitlement_admin set permission_type = ? where ent_group_id = ? and group_to_admin = ? and member_id is null";
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramEntitlementAdmin2.canAdminister()) {
        localStringBuffer.append("A");
      }
      if (paramEntitlementAdmin2.canExtend()) {
        localStringBuffer.append("E");
      }
      localPreparedStatement.setString(1, localStringBuffer.toString());
      localPreparedStatement.setInt(2, paramEntitlementAdmin1.getGranteeGroupId());
      localPreparedStatement.setInt(3, paramEntitlementAdmin1.getTargetGroupId());
      if (j != 0)
      {
        localPreparedStatement.setString(4, paramEntitlementAdmin1.getGranteeMemberType());
        localPreparedStatement.setString(5, paramEntitlementAdmin1.getGranteeMemberSubType());
        localPreparedStatement.setString(6, paramEntitlementAdmin1.getGranteeMemberId());
      }
      DBUtil.executeUpdate(localPreparedStatement, str2);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException1, str1, 2);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException2) {}
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  private boolean jdMethod_if(EntitlementAdmin paramEntitlementAdmin)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.adminExists";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    int j = paramEntitlementAdmin.getGranteeMemberId() != null ? 1 : 0;
    String str2;
    if (j != 0) {
      str2 = "select count(*) from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_type = ? and member_subtype = ? and member_id = ?";
    } else {
      str2 = "select count(*) from entitlement_admin where ent_group_id = ? and group_to_admin = ? and member_id is null";
    }
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(1, paramEntitlementAdmin.getGranteeGroupId());
      localPreparedStatement.setInt(2, paramEntitlementAdmin.getTargetGroupId());
      if (j != 0)
      {
        localPreparedStatement.setString(3, paramEntitlementAdmin.getGranteeMemberType());
        localPreparedStatement.setString(4, paramEntitlementAdmin.getGranteeMemberSubType());
        localPreparedStatement.setString(5, paramEntitlementAdmin.getGranteeMemberId());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      localResultSet.next();
      int k = localResultSet.getInt(1);
      boolean bool = k > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public void addLimit(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addLimit";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into limit_list (operation_name) values (?)");
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "insert into limit_list (operation_name) values (?)");
      this.g.add(new LimitTypePropertyList(paramString));
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void deleteLimit(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.deleteLimit";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from limit_list where operation_name = ?");
      localPreparedStatement.setString(1, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "delete from limit_list where operation_name = ?");
      Iterator localIterator = this.g.iterator();
      while (localIterator.hasNext()) {
        if (((LimitTypePropertyList)localIterator.next()).getOperationName().equals(paramString)) {
          localIterator.remove();
        }
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public boolean limitTypeExists(String paramString)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.limitTypeExists";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from limit_list where operation_name = ?");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from limit_list where operation_name = ?");
      localResultSet.next();
      int j = localResultSet.getInt(1);
      boolean bool = j > 0;
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public Limits getCumulativeLimits(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeLimits";
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      Limits localLimits = jdField_goto(localConnection, paramInt);
      return localLimits;
    }
    catch (PoolException localPoolException)
    {
      throw new EntitlementException(localPoolException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
  }
  
  Limits jdField_goto(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeLimits";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    Limits localLimits = new Limits();
    try
    {
      EntitlementGroup localEntitlementGroup;
      for (int j = paramInt; j != 0; j = localEntitlementGroup.getParentId())
      {
        localLimits.addAll(jdMethod_case(paramConnection, j));
        localEntitlementGroup = jdMethod_byte(paramConnection, j);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeLimits";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    Limits localLimits = null;
    try
    {
      int j = paramEntitlementGroupMember.getEntitlementGroupId();
      localLimits = getGroupLimits(paramEntitlementGroupMember);
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      while (j != 0)
      {
        localLimits.addAll(jdMethod_case(localConnection, j));
        EntitlementGroup localEntitlementGroup = jdMethod_byte(localConnection, j);
        j = localEntitlementGroup.getParentId();
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(int paramInt, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeLimits";
    Connection localConnection = null;
    isInitialized();
    Limits localLimits = new Limits();
    try
    {
      int j = paramInt;
      if (j != 0) {
        localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      }
      while (j != 0)
      {
        localLimits.addAll(getGroupLimits(j, paramLimit));
        EntitlementGroup localEntitlementGroup = jdMethod_byte(localConnection, j);
        j = localEntitlementGroup.getParentId();
        paramLimit.setGroupId(j);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCumulativeLimits";
    Connection localConnection = null;
    isInitialized();
    Limits localLimits = new Limits();
    try
    {
      int j = paramEntitlementGroupMember.getEntitlementGroupId();
      localLimits = getGroupLimits(paramEntitlementGroupMember, paramLimit);
      if (j != 0) {
        localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      }
      while (j != 0)
      {
        localLimits.addAll(getGroupLimits(j, paramLimit));
        EntitlementGroup localEntitlementGroup = jdMethod_byte(localConnection, j);
        j = localEntitlementGroup.getParentId();
        paramLimit.setGroupId(j);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  private Limits jdMethod_case(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCachedLimitGroup";
    Limits localLimits = null;
    if (this.c)
    {
      localLimits = this.jdField_null.getGroupLimits(paramInt);
      if (localLimits != null) {
        return localLimits;
      }
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int j = 0;
    localLimits = new Limits();
    try
    {
      if (paramConnection == null)
      {
        paramConnection = DBUtil.getConnection(this.jdField_void, true, 2);
        j = 1;
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ? and l.member_type is null and l.member_subtype is null and l.member_id is null");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ? and l.member_type is null and l.member_subtype is null and l.member_id is null");
      while (localResultSet.next())
      {
        Limit localLimit = a(localResultSet);
        localLimits.add(localLimit);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      if (j != 0) {
        DBUtil.returnConnection(this.jdField_void, paramConnection);
      }
    }
    if (this.c) {
      this.jdField_null.addGroupLimits(paramInt, localLimits);
    }
    return localLimits;
  }
  
  private Limits jdMethod_new(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getCachedLimitGroup";
    Limits localLimits = null;
    if (this.c)
    {
      localLimits = this.jdField_null.getGroupLimits(paramEntitlementGroupMember);
      if (localLimits != null) {
        return localLimits;
      }
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int j = 0;
    localLimits = new Limits();
    try
    {
      if (paramConnection == null)
      {
        paramConnection = DBUtil.getConnection(this.jdField_void, true, 2);
        j = 1;
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ? and l.member_type = ? and l.member_subtype = ? and member_id = ?");
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ? and l.member_type = ? and l.member_subtype = ? and member_id = ?");
      while (localResultSet.next())
      {
        Limit localLimit = a(localResultSet);
        localLimits.add(localLimit);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      if (j != 0) {
        DBUtil.returnConnection(this.jdField_void, paramConnection);
      }
    }
    if (this.c) {
      this.jdField_null.addGroupLimits(paramEntitlementGroupMember, localLimits);
    }
    return localLimits;
  }
  
  public Limit getGroupLimit(int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getLimitGroup";
    Limit localLimit = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localLimit = jdMethod_try(localConnection, paramInt);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    if (localLimit == null) {
      throw new EntitlementException(str, 14, "Limit not found");
    }
    return localLimit;
  }
  
  private Limit jdMethod_try(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getLimitGroup";
    Limit localLimit = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int j = 0;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and limit_id = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and limit_id = ?");
      if (localResultSet.next()) {
        localLimit = a(localResultSet);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return localLimit;
  }
  
  private Limits a(int paramInt, Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getGroupLimits";
    Limits localLimits = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int j = 0;
    localLimits = new Limits();
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    StringBuffer localStringBuffer = new StringBuffer("select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id");
    localStringBuffer.append(" and e.ent_group_id=? ");
    if (paramLimit.getMemberType() != null)
    {
      localStringBuffer.append(" and l.member_type=? ");
      k = 1;
    }
    else
    {
      localStringBuffer.append(" and l.member_type is null ");
    }
    if (paramLimit.getMemberSubType() != null)
    {
      localStringBuffer.append(" and l.member_subtype=? ");
      m = 1;
    }
    else
    {
      localStringBuffer.append(" and l.member_subtype is null ");
    }
    if (paramLimit.getMemberId() != null)
    {
      localStringBuffer.append(" and l.member_id=? ");
      n = 1;
    }
    else
    {
      localStringBuffer.append(" and l.member_id is null ");
    }
    if (paramLimit.getLimitId() != 0)
    {
      localStringBuffer.append(" and l.limit_id=? ");
      i1 = 1;
    }
    if (paramLimit.getLimitName() != null) {
      if (paramLimit.getLimitName().length() == 0)
      {
        localStringBuffer.append(" and l.operation_name is null ");
      }
      else
      {
        localStringBuffer.append(" and l.operation_name=? ");
        i2 = 1;
      }
    }
    if (paramLimit.getObjectType() != null) {
      if (paramLimit.getObjectType().length() == 0)
      {
        localStringBuffer.append(" and l.object_type is null ");
      }
      else
      {
        localStringBuffer.append(" and l.object_type=?");
        i3 = 1;
      }
    }
    if (paramLimit.getObjectId() != null) {
      if (paramLimit.getObjectId().length() == 0)
      {
        localStringBuffer.append(" and l.object_id is null ");
      }
      else
      {
        localStringBuffer.append(" and l.object_id=? ");
        i4 = 1;
      }
    }
    if (paramLimit.getPeriod() != 0) {
      localStringBuffer.append(" and l.period=?");
    }
    if (paramLimit.getData() != null)
    {
      localStringBuffer.append(" and l.data=?");
      i6 = 1;
    }
    if (paramLimit.getModifiedDate() != null)
    {
      localStringBuffer.append(" and l.modified_date=?");
      i7 = 1;
    }
    if (paramLimit.isAllowApprovalSet()) {
      localStringBuffer.append(" and l.allowApproval=?");
    }
    if (paramLimit.isRunningTotalTypeSet()) {
      localStringBuffer.append(" and l.running_total_type=?");
    }
    if (paramLimit.isParentSet()) {
      localStringBuffer.append(" and e.parent_id=?");
    }
    if (paramLimit.isCrossCurrencySet()) {
      localStringBuffer.append(" and l.isCrossCurrency=?");
    }
    if (paramLimit.isCurrencyCodeSet()) {
      localStringBuffer.append(" and l.currencyCode=?");
    }
    try
    {
      if (localConnection == null)
      {
        localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
        j = 1;
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i8 = 1;
      localPreparedStatement.setInt(i8, paramInt);
      i8++;
      if (k != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getMemberType());
        i8++;
      }
      if (m != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getMemberSubType());
        i8++;
      }
      if (n != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getMemberId());
        i8++;
      }
      if (i1 != 0)
      {
        localPreparedStatement.setInt(i8, paramLimit.getLimitId());
        i8++;
      }
      if (i2 != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getLimitName());
        i8++;
      }
      if (i3 != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getObjectType());
        i8++;
      }
      if (i4 != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getObjectId());
        i8++;
      }
      if (paramLimit.getPeriod() != 0)
      {
        localPreparedStatement.setInt(i8, paramLimit.getPeriod());
        i8++;
      }
      if (i6 != 0)
      {
        localPreparedStatement.setString(i8, paramLimit.getData());
        i8++;
      }
      if (i7 != 0)
      {
        localPreparedStatement.setTimestamp(i8, new Timestamp(paramLimit.getModifiedDate().getTime()));
        i8++;
      }
      if (paramLimit.isAllowApprovalSet())
      {
        if (paramLimit.isAllowedApproval()) {
          localPreparedStatement.setString(i8, "Y");
        } else {
          localPreparedStatement.setString(i8, "N");
        }
        i8++;
      }
      if (paramLimit.isRunningTotalTypeSet())
      {
        localPreparedStatement.setString(i8, String.valueOf(paramLimit.getRunningTotalType()));
        i8++;
      }
      if (paramLimit.isParentSet())
      {
        localPreparedStatement.setInt(i8, paramLimit.getParentId());
        i8++;
      }
      if (paramLimit.isCrossCurrencySet())
      {
        if (paramLimit.isCrossCurrency()) {
          localPreparedStatement.setString(i8, "Y");
        } else {
          localPreparedStatement.setString(i8, "N");
        }
        i8++;
      }
      if (paramLimit.isCurrencyCodeSet()) {
        localPreparedStatement.setString(i8, paramLimit.getCurrencyCode());
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Limit localLimit = a(localResultSet);
        localLimits.add(localLimit);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      if (j != 0) {
        DBUtil.returnConnection(this.jdField_void, localConnection);
      }
    }
    return localLimits;
  }
  
  public Limits getGroupLimits(int paramInt, Limit paramLimit)
    throws EntitlementException
  {
    Limit localLimit = (Limit)paramLimit.clone();
    localLimit.setMemberId(null);
    localLimit.setMemberType(null);
    localLimit.setMemberSubType(null);
    return a(paramInt, localLimit);
  }
  
  public Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws EntitlementException
  {
    Limit localLimit = (Limit)paramLimit.clone();
    localLimit.setMemberId(paramEntitlementGroupMember.getId());
    localLimit.setMemberType(paramEntitlementGroupMember.getMemberType());
    localLimit.setMemberSubType(paramEntitlementGroupMember.getMemberSubType());
    return a(paramEntitlementGroupMember.getEntitlementGroupId(), localLimit);
  }
  
  public Limits getGroupLimits(int paramInt)
    throws EntitlementException
  {
    isInitialized();
    Limits localLimits = jdMethod_case(null, paramInt);
    return localLimits;
  }
  
  Limits jdMethod_for(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    isInitialized();
    Limits localLimits = jdMethod_case(paramConnection, paramInt);
    return localLimits;
  }
  
  public Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    isInitialized();
    Limits localLimits = jdMethod_new(null, paramEntitlementGroupMember);
    return localLimits;
  }
  
  Limits jdMethod_try(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    isInitialized();
    Limits localLimits = jdMethod_new(paramConnection, paramEntitlementGroupMember);
    return localLimits;
  }
  
  public Limit addGroupLimit(Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.addGroupLimit";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ?");
      localPreparedStatement2.setInt(1, paramLimit.getGroupId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement2, "select l.limit_id, l.operation_name, l.object_type, l.object_id, l.ent_group_id, l.member_type, l.member_subtype, l.member_id, e.parent_id, l.period, l.data, l.modified_date, l.allowApproval, l.running_total_type, l.isCrossCurrency, l.currencyCode from limits l, entitlement_group e where l.ent_group_id = e.ent_group_id and l.ent_group_id = ?");
      while (localResultSet.next())
      {
        Limit localLimit = a(localResultSet);
        if (paramLimit.isLimitInfoIdentical(localLimit)) {
          throw new EntitlementException(str, 6, "Entitlement adapter adding duplicate limit");
        }
      }
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "insert into limits (limit_id, operation_name, ent_group_id, member_type, member_subtype, member_id, period, data, modified_date, object_type, object_id, allowApproval, running_total_type, isCrossCurrency, currencyCode ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      paramLimit.setLimitId(DBUtil.getNextId(localConnection, this.dbType, "limit_id"));
      localPreparedStatement1.setInt(1, paramLimit.getLimitId());
      localPreparedStatement1.setString(2, paramLimit.getLimitName());
      localPreparedStatement1.setInt(3, paramLimit.getGroupId());
      localPreparedStatement1.setString(4, paramLimit.getMemberType());
      localPreparedStatement1.setString(5, paramLimit.getMemberSubType());
      localPreparedStatement1.setString(6, paramLimit.getMemberId());
      localPreparedStatement1.setInt(7, paramLimit.getPeriod());
      localPreparedStatement1.setString(8, paramLimit.getData());
      localPreparedStatement1.setTimestamp(9, DBUtil.getCurrentTimestamp());
      localPreparedStatement1.setString(10, paramLimit.getObjectType());
      localPreparedStatement1.setString(11, paramLimit.getObjectId());
      if (paramLimit.isAllowedApproval()) {
        localPreparedStatement1.setString(12, "Y");
      } else {
        localPreparedStatement1.setString(12, "N");
      }
      localPreparedStatement1.setString(13, String.valueOf(paramLimit.getRunningTotalType()));
      if (paramLimit.isCrossCurrency()) {
        localPreparedStatement1.setString(14, "Y");
      } else {
        localPreparedStatement1.setString(14, "N");
      }
      localPreparedStatement1.setString(15, paramLimit.getCurrencyCode());
      DBUtil.executeUpdate(localPreparedStatement1, "insert into limits (limit_id, operation_name, ent_group_id, member_type, member_subtype, member_id, period, data, modified_date, object_type, object_id, allowApproval, running_total_type, isCrossCurrency, currencyCode ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      if (this.c) {
        this.jdField_null.addGroupLimit(paramLimit);
      }
      if (paramLimit.getMemberType() == null) {
        jdMethod_new(localConnection, paramLimit.getGroupId());
      } else {
        jdMethod_int(localConnection, paramLimit.getMember());
      }
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement2, localResultSet);
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement1);
    }
    return paramLimit;
  }
  
  public void deleteGroupLimit(Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.deleteGroupLimit";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      if (!jdMethod_int(localConnection, paramLimit.getLimitId())) {
        throw new EntitlementException(str, 14, "Limit to delete does not exist");
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from limits where limit_id = ?");
      localPreparedStatement.setInt(1, paramLimit.getLimitId());
      DBUtil.executeUpdate(localPreparedStatement, "delete from limits where limit_id = ?");
      if (this.c) {
        this.jdField_null.deleteGroupLimit(paramLimit);
      }
      if (paramLimit.getMemberType() == null) {
        jdMethod_new(localConnection, paramLimit.getGroupId());
      } else {
        jdMethod_int(localConnection, paramLimit.getMember());
      }
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public Limit modifyGroupLimit(Limit paramLimit)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.modifyGroupLimit";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update limits set operation_name = ?, ent_group_id = ?, member_type = ?, member_subtype = ?, member_id = ?, period = ?, data = ?, modified_date = ?, object_type = ?, object_id = ?, allowApproval = ?, running_total_type = ?, isCrossCurrency = ?, currencyCode = ? where limit_id = ?");
      Limit localLimit1 = jdMethod_try(localConnection, paramLimit.getLimitId());
      if (localLimit1 == null) {
        throw new EntitlementException(str, 14, "Limit to modify does not exist");
      }
      localPreparedStatement.setString(1, paramLimit.getLimitName());
      localPreparedStatement.setInt(2, paramLimit.getGroupId());
      localPreparedStatement.setString(3, paramLimit.getMemberType());
      localPreparedStatement.setString(4, paramLimit.getMemberSubType());
      localPreparedStatement.setString(5, paramLimit.getMemberId());
      localPreparedStatement.setInt(6, paramLimit.getPeriod());
      localPreparedStatement.setString(7, paramLimit.getData());
      Timestamp localTimestamp = DBUtil.getCurrentTimestamp();
      paramLimit.setModifiedDate(localTimestamp);
      localPreparedStatement.setTimestamp(8, localTimestamp);
      localPreparedStatement.setString(9, paramLimit.getObjectType());
      localPreparedStatement.setString(10, paramLimit.getObjectId());
      if (paramLimit.isAllowedApproval()) {
        localPreparedStatement.setString(11, "Y");
      } else {
        localPreparedStatement.setString(11, "N");
      }
      localPreparedStatement.setString(12, String.valueOf(paramLimit.getRunningTotalType()));
      if (paramLimit.isCrossCurrency()) {
        localPreparedStatement.setString(13, "Y");
      } else {
        localPreparedStatement.setString(13, "N");
      }
      localPreparedStatement.setString(14, paramLimit.getCurrencyCode());
      localPreparedStatement.setInt(15, paramLimit.getLimitId());
      DBUtil.executeUpdate(localPreparedStatement, "update limits set operation_name = ?, ent_group_id = ?, member_type = ?, member_subtype = ?, member_id = ?, period = ?, data = ?, modified_date = ?, object_type = ?, object_id = ?, allowApproval = ?, running_total_type = ?, isCrossCurrency = ?, currencyCode = ? where limit_id = ?");
      if (this.c) {
        this.jdField_null.modifyGroupLimit(paramLimit);
      }
      if ((!localLimit1.getCurrencyCode().equals(paramLimit.getCurrencyCode())) || (localLimit1.isCrossCurrency() != paramLimit.isCrossCurrency()))
      {
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from running_total where limit_id = ?");
        localPreparedStatement.setInt(1, paramLimit.getLimitId());
        DBUtil.executeUpdate(localPreparedStatement, "update limits set operation_name = ?, ent_group_id = ?, member_type = ?, member_subtype = ?, member_id = ?, period = ?, data = ?, modified_date = ?, object_type = ?, object_id = ?, allowApproval = ?, running_total_type = ?, isCrossCurrency = ?, currencyCode = ? where limit_id = ?");
      }
      if (paramLimit.getMemberType() == null) {
        jdMethod_new(localConnection, paramLimit.getGroupId());
      } else {
        jdMethod_int(localConnection, paramLimit.getMember());
      }
      DBUtil.commit(localConnection);
      Limit localLimit2 = paramLimit;
      return localLimit2;
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement);
    }
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal.negate(), paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal.negate(), paramString, paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal.negate(), paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal.negate(), paramString, paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal.negate(), paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal.negate(), paramString, paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    a(paramConnection, paramEntitlementGroupMember, EntitlementUtil.getEntitlements(paramMultiEntitlement), paramBigDecimal.negate(), this.f, paramDate, paramLimits);
  }
  
  public void checkLimitsDelete(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    a(paramConnection, paramEntitlementGroupMember, EntitlementUtil.getEntitlements(paramMultiEntitlement), paramBigDecimal.negate(), paramString, paramDate, paramLimits);
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.f, paramDate, paramLimits);
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.confirmLimtisBeforeAdd";
    Limits localLimits = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localLimits = checkLimitsAdd(localConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate, paramLimits);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.rollback(localConnection);
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, this.f, paramDate, paramLimits);
  }
  
  public Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.confirmLimtisBeforeAdd";
    Limits localLimits = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localLimits = a(localConnection, paramEntitlementGroupMember, EntitlementUtil.getEntitlements(paramMultiEntitlement), paramBigDecimal, paramString, paramDate, paramLimits);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.rollback(localConnection);
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    return checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, this.f, paramDate, paramLimits);
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsAdd";
    Limits localLimits = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localLimits = checkLimitsAdd(localConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate, paramLimits);
      for (int j = 0; j < localLimits.size(); j++)
      {
        Limit localLimit = (Limit)localLimits.get(j);
        if (!localLimit.isAllowedApproval())
        {
          DBUtil.rollback(localConnection);
          break;
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    return checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, this.f, paramDate, paramLimits);
  }
  
  public Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsAdd";
    Limits localLimits = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      localLimits = a(localConnection, paramEntitlementGroupMember, EntitlementUtil.getEntitlements(paramMultiEntitlement), paramBigDecimal, paramString, paramDate, paramLimits);
      for (int j = 0; j < localLimits.size(); j++)
      {
        Limit localLimit = (Limit)localLimits.get(j);
        if (!localLimit.isAllowedApproval())
        {
          DBUtil.rollback(localConnection);
          break;
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    Entitlements localEntitlements = new Entitlements();
    localEntitlements.add(paramEntitlement);
    return a(paramConnection, paramEntitlementGroupMember, localEntitlements, paramBigDecimal, this.f, paramDate, paramLimits);
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    Entitlements localEntitlements = new Entitlements();
    localEntitlements.add(paramEntitlement);
    return a(paramConnection, paramEntitlementGroupMember, localEntitlements, paramBigDecimal, paramString, paramDate, paramLimits);
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    return a(paramConnection, paramEntitlementGroupMember, EntitlementUtil.getEntitlements(paramMultiEntitlement), paramBigDecimal, this.f, paramDate, paramLimits);
  }
  
  public Limits checkLimitsAdd(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    return a(paramConnection, paramEntitlementGroupMember, EntitlementUtil.getEntitlements(paramMultiEntitlement), paramBigDecimal, paramString, paramDate, paramLimits);
  }
  
  private Limits a(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements, BigDecimal paramBigDecimal, String paramString, Date paramDate, Limits paramLimits)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.checkLimitsAdd";
    Limits newLimits = new Limits();
    StringBuffer localStringBuffer = new StringBuffer();
    Limits existingLimits = null;
    isInitialized();
    checkAvaliabilityOfForeignExchangeService();
    try
    {
      if (paramLimits != null) {
        existingLimits = paramLimits;
      } else {
        existingLimits = getCumulativeLimits(paramEntitlementGroupMember);
      }
      Limits cloneOfExistingLimits = (Limits)existingLimits.clone();
      Limit localLimit1 = null;
      int j = 0;
      String idOfEntitlementGroupMember = "0";
      EntitlementGroup localEntitlementGroup = getEntitlementGroup(paramEntitlementGroupMember.getEntitlementGroupId());
      String str3 = localEntitlementGroup.getEntGroupType();
      if (("Business".equals(str3)) || ("Division".equals(str3)) || ("Group".equals(str3)) || ("USER".equals(str3)))
      {
        j = 4;
        idOfEntitlementGroupMember = paramEntitlementGroupMember.getId();
      }
      for (int k = 0; k < existingLimits.size(); k++)
      {
        localLimit1 = (Limit)existingLimits.get(k);
        boolean isLimitMatchEntit = false;
        for (int n = 0; n < paramEntitlements.size(); n++)
        {
        	Entitlement paramEntitlemen = paramEntitlements.getEntitlement(n);
          if ((a(localLimit1, paramString, (Entitlement)paramEntitlemen)) && (localLimit1.getPeriod() != 0))
          {
        	  isLimitMatchEntit = true;
            if (localStringBuffer.length() > 0) {
              localStringBuffer.append(",");
            }
            localStringBuffer.append(String.valueOf(localLimit1.getLimitId()));
            localObject4 = exchangeRate(paramConnection, paramBigDecimal, paramString, localLimit1, paramDate, j, idOfEntitlementGroupMember);
            if ((((BigDecimal)localObject4).compareTo(new BigDecimal(0.0D)) > 0) && (((BigDecimal)localObject4).compareTo(localLimit1.getAmount()) > 0) && (!newLimits.contains(localLimit1))) {
              newLimits.add(localLimit1);
            }
            if (localLimit1.getPeriod() == 1) {
              cloneOfExistingLimits.remove(localLimit1);
            }
          }
        }
        if (isLimitMatchEntit) {
          cloneOfExistingLimits.remove(localLimit1);
        }
      }
      if (cloneOfExistingLimits.size() == 0)
      {
        localObject1 = newLimits;
        return localObject1;
      }
      Object localObject1 = new StringBuffer("update limits set limit_id = limit_id where limit_id in (");
      for (int m = 0; m < cloneOfExistingLimits.size(); m++)
      {
        if (m > 0) {
          ((StringBuffer)localObject1).append(", ");
        }
        localObject2 = (Limit)cloneOfExistingLimits.get(m);
        ((StringBuffer)localObject1).append(Integer.toString(((Limit)localObject2).getLimitId()));
      }
      ((StringBuffer)localObject1).append(" )");
      PreparedStatement localPreparedStatement = null;
      try
      {
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, ((StringBuffer)localObject1).toString());
        DBUtil.executeUpdate(localPreparedStatement, ((StringBuffer)localObject1).toString());
      }
      finally
      {
        DBUtil.closeStatement(localPreparedStatement);
      }
      Object localObject2 = new StringBuffer("select limit_id, amount, run_total_id from running_total ");
      Object localObject3 = null;
      Object localObject4 = null;
      ResultSet localResultSet = null;
      try
      {
        if (this.dbType.equals("ASE")) {
          ((StringBuffer)localObject2).append(" HOLDLOCK ");
        }
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(paramDate);
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        Calendar localCalendar2 = (Calendar)localCalendar1.clone();
        Calendar localCalendar3 = (Calendar)localCalendar1.clone();
        localCalendar2.set(7, 1);
        localCalendar3.set(5, 1);
        int i1 = 0;
        for (int i2 = 0; i2 < cloneOfExistingLimits.size(); i2++)
        {
          if (i1 == 0)
          {
            i1 = 1;
            ((StringBuffer)localObject2).append("where ");
          }
          if (i2 > 0) {
            ((StringBuffer)localObject2).append(" or ");
          }
          Limit localLimit2 = (Limit)cloneOfExistingLimits.get(i2);
          if (localLimit2.getRunningTotalType() == 'U') {
            ((StringBuffer)localObject2).append("(limit_id = ? and member_id = ? and member_type = ? and member_subtype = ? and start_date = ?)");
          } else {
            ((StringBuffer)localObject2).append("(limit_id = ? and ent_group_id = ? and start_date = ?)");
          }
        }
        if (!this.dbType.equals("ASE")) {
          ((StringBuffer)localObject2).append(" for update ");
        }
        localObject3 = DBUtil.prepareStatement(paramConnection, ((StringBuffer)localObject2).toString());
        i2 = 1;
        for (int i3 = 0; i3 < cloneOfExistingLimits.size(); i3++) {
          i2 = exchangeRate((PreparedStatement)localObject3, i2, (Limit)cloneOfExistingLimits.get(i3), paramEntitlementGroupMember, localCalendar1, localCalendar2, localCalendar3);
        }
        localResultSet = DBUtil.executeQuery((PreparedStatement)localObject3, "select limit_id, amount, run_total_id from running_total ");
        HashMap localHashMap = new HashMap();
        Object localObject6;
        while (localResultSet.next())
        {
          int limit_id = localResultSet.getInt(1);
          localLimit1 = cloneOfExistingLimits.getLimitById(limit_id);
          int limitPeriod = localLimit1.getPeriod();
          BigDecimal limitAmount = a(localResultSet.getString(2));
          localObject6 = exchangeRate(paramConnection, paramBigDecimal, paramString, localLimit1, paramDate, j, idOfEntitlementGroupMember);
          if ((limitAmount.add((BigDecimal)localObject6).compareTo(localLimit1.getAmount()) > 0) && (!newLimits.contains(localLimit1))) {
            newLimits.add(localLimit1);
          }
          if (localObject4 == null) {
            localObject4 = DBUtil.prepareStatement(paramConnection, "update running_total set amount = ? where run_total_id = ?");
          }
          BigDecimal localBigDecimal2 = limitAmount.add((BigDecimal)localObject6);
          if (localBigDecimal2.compareTo(new BigDecimal(0.0D)) < 0) {
            localBigDecimal2 = new BigDecimal(0.0D);
          }
          ((PreparedStatement)localObject4).setString(1, localBigDecimal2.toString());
          ((PreparedStatement)localObject4).setInt(2, localResultSet.getInt(3));
          DBUtil.executeUpdate((PreparedStatement)localObject4, "update running_total set amount = ? where run_total_id = ?");
        }
        if (paramBigDecimal.compareTo(new BigDecimal(0.0D)) > 0) {
          for (int i5 = 0; i5 < cloneOfExistingLimits.size(); i5++)
          {
            localLimit1 = (Limit)cloneOfExistingLimits.get(i5);
            BigDecimal localBigDecimal3 = exchangeRate(paramConnection, paramBigDecimal, paramString, localLimit1, paramDate, j, idOfEntitlementGroupMember);
            if ((!newLimits.contains(localLimit1)) || (localLimit1.isAllowedApproval()))
            {
              String str4 = String.valueOf(localLimit1.getPeriod());
              if (localLimit1.getPeriod() == 2) {
                localObject6 = localCalendar1;
              } else if (localLimit1.getPeriod() == 3) {
                localObject6 = localCalendar2;
              } else {
                localObject6 = localCalendar3;
              }
              if (localBigDecimal3.compareTo(new BigDecimal(0.0D)) > 0) {
                a(paramConnection, localLimit1, paramEntitlementGroupMember, localBigDecimal3, (Calendar)localObject6);
              }
            }
          }
        }
      }
      finally
      {
        DBUtil.closeStatement((PreparedStatement)localObject4);
        DBUtil.closeAll((PreparedStatement)localObject3, localResultSet);
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      localEntitlementException = localEntitlementException;
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      localException = localException;
      throw new EntitlementException(localException, str1, 2);
    }
    finally {}
    return newLimits;
  }
  
  private BigDecimal exchangeRate(Connection paramConnection, BigDecimal paramBigDecimal, String paramString1, Limit paramLimit, Date paramDate, int paramInt, String paramString2)
    throws EntitlementException
  {
    if (paramLimit.getCurrencyCode().equalsIgnoreCase(paramString1)) {
      return paramBigDecimal;
    }
    try
    {
      FXRate localFXRate = this.h.getClosestFXRate(Integer.parseInt(paramString2), paramConnection, paramString1, paramLimit.getCurrencyCode(), new DateTime(paramDate, Locale.getDefault()), paramInt, paramString2, new HashMap());
      BigDecimal localBigDecimal = localFXRate.getBuyPrice().getAmountValue();
      return paramBigDecimal.multiply(localBigDecimal);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(35, localException);
    }
  }
  
  private boolean a(Limit paramLimit, String paramString, Entitlement paramEntitlement)
    throws EntitlementException
  {
    boolean bool = true;
    if ((paramLimit.getLimitName() != null) && (!paramLimit.getLimitName().equalsIgnoreCase(paramEntitlement.getOperationName()))) {
      return false;
    }
    if ((paramLimit.getObjectType() != null) && (!paramLimit.getObjectType().equalsIgnoreCase(paramEntitlement.getObjectType()))) {
      return false;
    }
    if ((paramLimit.getObjectId() != null) && (!paramLimit.getObjectId().equalsIgnoreCase(paramEntitlement.getObjectId()))) {
      return false;
    }
    if ((!paramLimit.isCrossCurrency()) && (!paramLimit.getCurrencyCode().equalsIgnoreCase(paramString))) {
      return false;
    }
    return bool;
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    return checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, this.f, paramDate1, paramDate2, paramLimits);
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsEdit";
    isInitialized();
    Limits localLimits = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      checkLimitsAdd(localConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1.negate(), paramString, paramDate1, paramLimits);
      localLimits = checkLimitsAdd(localConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal2, paramString, paramDate2, paramLimits);
      for (int j = 0; j < localLimits.size(); j++)
      {
        Limit localLimit = (Limit)localLimits.get(j);
        if (!localLimit.isAllowedApproval())
        {
          DBUtil.rollback(localConnection);
          break;
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    return checkLimitsEdit(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, this.f, paramDate1, paramDate2, paramLimits);
  }
  
  public Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsEdit";
    isInitialized();
    Limits localLimits = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, false, 2);
      checkLimitsAdd(localConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1.negate(), paramString, paramDate1, paramLimits);
      localLimits = checkLimitsAdd(localConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal2, paramString, paramDate2, paramLimits);
      for (int j = 0; j < localLimits.size(); j++)
      {
        Limit localLimit = (Limit)localLimits.get(j);
        if (!localLimit.isAllowedApproval())
        {
          DBUtil.rollback(localConnection);
          break;
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (EntitlementException localEntitlementException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return localLimits;
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsEdit";
    Limits localLimits = null;
    try
    {
      checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1.negate(), paramDate1, paramLimits);
      localLimits = checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal2, paramDate2, paramLimits);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    return localLimits;
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsEdit";
    Limits localLimits = null;
    try
    {
      checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1.negate(), paramString, paramDate1, paramLimits);
      localLimits = checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramEntitlement, paramBigDecimal2, paramString, paramDate2, paramLimits);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    return localLimits;
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsEdit";
    Limits localLimits = null;
    try
    {
      checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1.negate(), paramDate1, paramLimits);
      localLimits = checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal2, paramDate2, paramLimits);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    return localLimits;
  }
  
  public Limits checkLimitsEdit(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2, Limits paramLimits)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.checkLimitsEdit";
    Limits localLimits = null;
    try
    {
      checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1.negate(), paramString, paramDate1, paramLimits);
      localLimits = checkLimitsAdd(paramConnection, paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal2, paramString, paramDate2, paramLimits);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    return localLimits;
  }
  
  private static int a(PreparedStatement paramPreparedStatement, int paramInt, Limit paramLimit, EntitlementGroupMember paramEntitlementGroupMember, Calendar paramCalendar1, Calendar paramCalendar2, Calendar paramCalendar3)
    throws Exception
  {
    paramPreparedStatement.setInt(paramInt++, paramLimit.getLimitId());
    if (paramLimit.getRunningTotalType() == 'U')
    {
      paramPreparedStatement.setString(paramInt++, paramEntitlementGroupMember.getId());
      paramPreparedStatement.setString(paramInt++, paramEntitlementGroupMember.getMemberType());
      paramPreparedStatement.setString(paramInt++, paramEntitlementGroupMember.getMemberSubType());
    }
    else
    {
      paramPreparedStatement.setInt(paramInt++, paramLimit.getGroupId());
    }
    if (paramLimit.getPeriod() == 2) {
      paramPreparedStatement.setTimestamp(paramInt++, new Timestamp(paramCalendar1.getTime().getTime()));
    }
    if (paramLimit.getPeriod() == 3) {
      paramPreparedStatement.setTimestamp(paramInt++, new Timestamp(paramCalendar2.getTime().getTime()));
    }
    if (paramLimit.getPeriod() == 4) {
      paramPreparedStatement.setTimestamp(paramInt++, new Timestamp(paramCalendar3.getTime().getTime()));
    }
    return paramInt;
  }
  
  protected static String getRSString(ResultSet paramResultSet, String paramString)
    throws Exception
  {
    return paramResultSet.getString(paramString);
  }
  
  public static int convertToInt(String paramString)
  {
    int j = 0;
    try
    {
      j = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return j;
  }
  
  public static float convertToFloat(String paramString)
  {
    float f1 = 0.0F;
    try
    {
      f1 = Float.parseFloat(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return f1;
  }
  
  private static BigDecimal a(String paramString)
  {
    BigDecimal localBigDecimal;
    try
    {
      localBigDecimal = new BigDecimal(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localBigDecimal = new BigDecimal(0.0D);
    }
    return localBigDecimal;
  }
  
  private void a(Connection paramConnection, Limit paramLimit, EntitlementGroupMember paramEntitlementGroupMember, BigDecimal paramBigDecimal, Calendar paramCalendar)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into running_total (run_total_id, limit_id, member_id, member_type, member_subtype, ent_group_id, amount, start_date) values (?,?,?,?,?,?,?,?)");
      localPreparedStatement.setInt(1, DBUtil.getNextId(paramConnection, this.dbType, "run_total_id"));
      localPreparedStatement.setInt(2, paramLimit.getLimitId());
      if (paramLimit.getRunningTotalType() == 'U')
      {
        localPreparedStatement.setString(3, paramEntitlementGroupMember.getId());
        localPreparedStatement.setString(4, paramEntitlementGroupMember.getMemberType());
        localPreparedStatement.setString(5, paramEntitlementGroupMember.getMemberSubType());
        localPreparedStatement.setNull(6, 4);
      }
      else
      {
        localPreparedStatement.setString(3, null);
        localPreparedStatement.setString(4, null);
        localPreparedStatement.setString(5, null);
        localPreparedStatement.setInt(6, paramLimit.getGroupId());
      }
      localPreparedStatement.setString(7, paramBigDecimal.toString());
      localPreparedStatement.setTimestamp(8, new Timestamp(paramCalendar.getTime().getTime()));
      DBUtil.executeUpdate(localPreparedStatement, "insert into running_total (run_total_id, limit_id, member_id, member_type, member_subtype, ent_group_id, amount, start_date) values (?,?,?,?,?,?,?,?)");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  public boolean isObjectInUse(String paramString1, String paramString2)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.isObjectInUse";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct e.object_type, e.object_id from entitlement_del e where ( e.object_type=? and e.object_id=? ) ");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct e.object_type, e.object_id from entitlement_del e where ( e.object_type=? and e.object_id=? ) ");
      if (localResultSet.next())
      {
        bool = true;
        return bool;
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct l.object_type, l.object_id from limits l where ( l.object_type=? and l.object_id=? )");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct l.object_type, l.object_id from limits l where ( l.object_type=? and l.object_id=? )");
      boolean bool = localResultSet.next();
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private EntitlementTypePropertyLists jdMethod_do()
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getEntTypeProps";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      String str2 = ConnectionPool.getDBType(this.jdField_void);
      if ((str2.equalsIgnoreCase("ORACLE:THIN")) || (str2.equalsIgnoreCase("ORACLE:OCI8")))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.operation_name, b.prop_name, b.prop_value FROM entitlement_list a, ent_type_props b WHERE a.operation_name = b.operation_name(+) ORDER BY a.operation_name");
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.operation_name, b.prop_name, b.prop_value FROM entitlement_list a, ent_type_props b WHERE a.operation_name = b.operation_name(+) ORDER BY a.operation_name");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.operation_name, b.prop_name, b.prop_value FROM entitlement_list a LEFT JOIN ent_type_props b ON a.operation_name = b.operation_name ORDER BY a.operation_name");
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.operation_name, b.prop_name, b.prop_value FROM entitlement_list a LEFT JOIN ent_type_props b ON a.operation_name = b.operation_name ORDER BY a.operation_name");
      }
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
      EntitlementTypePropertyList localEntitlementTypePropertyList = null;
      while (localResultSet.next())
      {
        localObject1 = localResultSet.getString(1);
        String str3 = localResultSet.getString(2);
        String str4 = localResultSet.getString(3);
        if ((localEntitlementTypePropertyList == null) || (!localEntitlementTypePropertyList.getOperationName().equals(localObject1)))
        {
          localEntitlementTypePropertyList = new EntitlementTypePropertyList((String)localObject1);
          localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList);
        }
        if (str3 != null) {
          localEntitlementTypePropertyList.addProperty(str3, str4);
        }
      }
      Object localObject1 = localEntitlementTypePropertyLists;
      return localObject1;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public ArrayList getEntitlementTypes()
    throws EntitlementException
  {
    ArrayList localArrayList = new ArrayList(this.i.size());
    Iterator localIterator = this.i.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      localArrayList.add(localEntitlementTypePropertyList.getOperationName());
    }
    return localArrayList;
  }
  
  public ArrayList getEntitlementTypes(String paramString1, String paramString2)
    throws EntitlementException
  {
    ArrayList localArrayList = new ArrayList(this.i.size());
    Iterator localIterator = this.i.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.isPropertySet(paramString1, paramString2)) {
        localArrayList.add(localEntitlementTypePropertyList.getOperationName());
      }
    }
    return localArrayList;
  }
  
  public ArrayList getEntitlementTypes(HashMap paramHashMap)
    throws EntitlementException
  {
    ArrayList localArrayList = new ArrayList(this.i.size());
    Iterator localIterator = this.i.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.areAllPropertiesSet(paramHashMap)) {
        localArrayList.add(localEntitlementTypePropertyList.getOperationName());
      }
    }
    return localArrayList;
  }
  
  public EntitlementTypePropertyList getEntitlementTypePropertyList(String paramString)
    throws EntitlementException
  {
    EntitlementTypePropertyList localEntitlementTypePropertyList = this.i.getByOperationName(paramString);
    if (localEntitlementTypePropertyList != null) {
      localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyList.clone();
    }
    return localEntitlementTypePropertyList;
  }
  
  public EntitlementTypePropertyList getEntitlementTypeWithProperties(String paramString)
    throws EntitlementException
  {
    EntitlementTypePropertyList localEntitlementTypePropertyList = this.i.getByOperationName(paramString);
    if (localEntitlementTypePropertyList != null) {
      localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyList.clone();
    }
    return localEntitlementTypePropertyList;
  }
  
  public EntitlementTypePropertyLists getEntitlementTypesWithProperties(String paramString1, String paramString2)
    throws EntitlementException
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    Iterator localIterator = this.i.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.isPropertySet(paramString1, paramString2)) {
        localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList.clone());
      }
    }
    return localEntitlementTypePropertyLists;
  }
  
  public EntitlementTypePropertyLists getEntitlementTypesWithProperties(HashMap paramHashMap)
    throws EntitlementException
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    Iterator localIterator = this.i.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.areAllPropertiesSet(paramHashMap)) {
        localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList.clone());
      }
    }
    return localEntitlementTypePropertyLists;
  }
  
  public ArrayList getRestrictedEntitlementTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getRestrictedEntitlements(paramInt);
    ArrayList localArrayList = new ArrayList();
    a(localEntitlements, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public EntitlementTypePropertyLists getRestrictedEntitlementTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getRestrictedEntitlements(paramInt);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    a(localEntitlements, localEntitlementTypePropertyLists, paramString1, paramString2, true);
    return localEntitlementTypePropertyLists;
  }
  
  public ArrayList getRestrictedEntitlementTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getRestrictedEntitlements(paramEntitlementGroupMember);
    ArrayList localArrayList = new ArrayList();
    a(localEntitlements, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public EntitlementTypePropertyLists getRestrictedEntitlementTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getRestrictedEntitlements(paramEntitlementGroupMember);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    a(localEntitlements, localEntitlementTypePropertyLists, paramString1, paramString2, true);
    return localEntitlementTypePropertyLists;
  }
  
  public ArrayList getCumulativeEntitlementTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getCumulativeEntitlements(paramInt);
    ArrayList localArrayList = new ArrayList();
    a(localEntitlements, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public EntitlementTypePropertyLists getCumulativeEntitlementTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getCumulativeEntitlements(paramInt);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    a(localEntitlements, localEntitlementTypePropertyLists, paramString1, paramString2, true);
    return localEntitlementTypePropertyLists;
  }
  
  public ArrayList getCumulativeEntitlementTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getCumulativeEntitlements(paramEntitlementGroupMember);
    ArrayList localArrayList = new ArrayList();
    a(localEntitlements, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public EntitlementTypePropertyLists getCumulativeEntitlementTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Entitlements localEntitlements = getCumulativeEntitlements(paramEntitlementGroupMember);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    a(localEntitlements, localEntitlementTypePropertyLists, paramString1, paramString2, true);
    return localEntitlementTypePropertyLists;
  }
  
  private void a(Entitlements paramEntitlements, ArrayList paramArrayList, String paramString1, String paramString2, boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet((int)Math.ceil(Math.min(paramEntitlements.size(), this.i.size()) * 1.334D));
    Iterator localIterator = paramEntitlements.iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      String str = localEntitlement.getOperationName();
      if ((str != null) && (!localHashSet.contains(str)))
      {
        localHashSet.add(str);
        EntitlementTypePropertyList localEntitlementTypePropertyList = this.i.getByOperationName(str);
        if (localEntitlementTypePropertyList.isPropertySet(paramString1, paramString2)) {
          if (paramBoolean) {
            paramArrayList.add(localEntitlementTypePropertyList.clone());
          } else {
            paramArrayList.add(str);
          }
        }
      }
    }
  }
  
  private LimitTypePropertyLists a()
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getLimitTypeProps";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      String str2 = ConnectionPool.getDBType(this.jdField_void);
      if ((str2.equalsIgnoreCase("ORACLE:THIN")) || (str2.equalsIgnoreCase("ORACLE:OCI8")))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.operation_name, b.prop_name, b.prop_value FROM limit_list a, limit_type_props b WHERE a.operation_name = b.operation_name(+) ORDER BY a.operation_name");
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.operation_name, b.prop_name, b.prop_value FROM limit_list a, limit_type_props b WHERE a.operation_name = b.operation_name(+) ORDER BY a.operation_name");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.operation_name, b.prop_name, b.prop_value FROM limit_list a LEFT JOIN limit_type_props b ON a.operation_name = b.operation_name ORDER BY a.operation_name");
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.operation_name, b.prop_name, b.prop_value FROM limit_list a LEFT JOIN limit_type_props b ON a.operation_name = b.operation_name ORDER BY a.operation_name");
      }
      LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
      LimitTypePropertyList localLimitTypePropertyList = null;
      while (localResultSet.next())
      {
        localObject1 = localResultSet.getString(1);
        String str3 = localResultSet.getString(2);
        String str4 = localResultSet.getString(3);
        if ((localLimitTypePropertyList == null) || (!localLimitTypePropertyList.getOperationName().equals(localObject1)))
        {
          localLimitTypePropertyList = new LimitTypePropertyList((String)localObject1);
          localLimitTypePropertyLists.add(localLimitTypePropertyList);
        }
        if (str3 != null) {
          localLimitTypePropertyList.addProperty(str3, str4);
        }
      }
      Object localObject1 = localLimitTypePropertyLists;
      return localObject1;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private ObjectTypePropertyLists jdMethod_if()
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getObjectTypeProps";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      String str2 = ConnectionPool.getDBType(this.jdField_void);
      if ((str2.equalsIgnoreCase("ORACLE:THIN")) || (str2.equalsIgnoreCase("ORACLE:OCI8")))
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.object_type, b.prop_name, b.prop_value FROM object_type a, object_type_props b WHERE a.object_type = b.object_type(+) ORDER BY a.object_type");
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.object_type, b.prop_name, b.prop_value FROM object_type a, object_type_props b WHERE a.object_type = b.object_type(+) ORDER BY a.object_type");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT a.object_type, b.prop_name, b.prop_value FROM object_type a LEFT JOIN object_type_props b ON a.object_type = b.object_type ORDER BY a.object_type");
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT a.object_type, b.prop_name, b.prop_value FROM object_type a LEFT JOIN object_type_props b ON a.object_type = b.object_type ORDER BY a.object_type");
      }
      ObjectTypePropertyLists localObjectTypePropertyLists = new ObjectTypePropertyLists();
      ObjectTypePropertyList localObjectTypePropertyList = null;
      while (localResultSet.next())
      {
        localObject1 = localResultSet.getString(1);
        String str3 = localResultSet.getString(2);
        String str4 = localResultSet.getString(3);
        if ((localObjectTypePropertyList == null) || (!localObjectTypePropertyList.getObjectType().equals(localObject1)))
        {
          localObjectTypePropertyList = new ObjectTypePropertyList((String)localObject1);
          localObjectTypePropertyLists.add(localObjectTypePropertyList);
        }
        if (str3 != null) {
          localObjectTypePropertyList.addProperty(str3, str4);
        }
      }
      Object localObject1 = localObjectTypePropertyLists;
      return localObject1;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_void, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public ArrayList getLimitTypes()
    throws EntitlementException
  {
    ArrayList localArrayList = new ArrayList(this.g.size());
    Iterator localIterator = this.g.iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      localArrayList.add(localLimitTypePropertyList.getOperationName());
    }
    return localArrayList;
  }
  
  public ArrayList getLimitTypes(String paramString1, String paramString2)
    throws EntitlementException
  {
    ArrayList localArrayList = new ArrayList(this.g.size());
    Iterator localIterator = this.g.iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      if (localLimitTypePropertyList.isPropertySet(paramString1, paramString2)) {
        localArrayList.add(localLimitTypePropertyList.getOperationName());
      }
    }
    return localArrayList;
  }
  
  public ArrayList getLimitTypes(HashMap paramHashMap)
    throws EntitlementException
  {
    ArrayList localArrayList = new ArrayList(this.g.size());
    Iterator localIterator = this.g.iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      if (localLimitTypePropertyList.areAllPropertiesSet(paramHashMap)) {
        localArrayList.add(localLimitTypePropertyList.getOperationName());
      }
    }
    return localArrayList;
  }
  
  public LimitTypePropertyList getLimitTypePropertyList(String paramString)
    throws EntitlementException
  {
    LimitTypePropertyList localLimitTypePropertyList = this.g.getByOperationName(paramString);
    if (localLimitTypePropertyList != null) {
      localLimitTypePropertyList = (LimitTypePropertyList)localLimitTypePropertyList.clone();
    }
    return localLimitTypePropertyList;
  }
  
  public LimitTypePropertyLists getLimitTypesWithProperties(String paramString1, String paramString2)
    throws EntitlementException
  {
    LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
    Iterator localIterator = this.g.iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      if (localLimitTypePropertyList.isPropertySet(paramString1, paramString2)) {
        localLimitTypePropertyLists.add(localLimitTypePropertyList.clone());
      }
    }
    return localLimitTypePropertyLists;
  }
  
  public LimitTypePropertyLists getLimitTypesWithProperties(HashMap paramHashMap)
    throws EntitlementException
  {
    LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
    Iterator localIterator = this.g.iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      if (localLimitTypePropertyList.areAllPropertiesSet(paramHashMap)) {
        localLimitTypePropertyLists.add(localLimitTypePropertyList.clone());
      }
    }
    return localLimitTypePropertyLists;
  }
  
  public ObjectTypePropertyList getObjectTypePropertyList(String paramString)
    throws EntitlementException
  {
    ObjectTypePropertyList localObjectTypePropertyList = this.jdField_long.getByObjectType(paramString);
    if (localObjectTypePropertyList != null) {
      localObjectTypePropertyList = (ObjectTypePropertyList)localObjectTypePropertyList.clone();
    }
    return localObjectTypePropertyList;
  }
  
  public ArrayList getGroupLimitTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getGroupLimits(paramInt);
    ArrayList localArrayList = new ArrayList();
    a(localLimits, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public LimitTypePropertyLists getGroupLimitTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getGroupLimits(paramInt);
    LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
    a(localLimits, localLimitTypePropertyLists, paramString1, paramString2, true);
    return localLimitTypePropertyLists;
  }
  
  public ArrayList getGroupLimitTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getGroupLimits(paramEntitlementGroupMember);
    ArrayList localArrayList = new ArrayList();
    a(localLimits, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public LimitTypePropertyLists getGroupLimitTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getGroupLimits(paramEntitlementGroupMember);
    LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
    a(localLimits, localLimitTypePropertyLists, paramString1, paramString2, true);
    return localLimitTypePropertyLists;
  }
  
  public ArrayList getCumulativeLimitTypes(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getCumulativeLimits(paramInt);
    ArrayList localArrayList = new ArrayList();
    a(localLimits, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public LimitTypePropertyLists getCumulativeLimitTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getCumulativeLimits(paramInt);
    LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
    a(localLimits, localLimitTypePropertyLists, paramString1, paramString2, true);
    return localLimitTypePropertyLists;
  }
  
  public ArrayList getCumulativeLimitTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getCumulativeLimits(paramEntitlementGroupMember);
    ArrayList localArrayList = new ArrayList();
    a(localLimits, localArrayList, paramString1, paramString2, false);
    return localArrayList;
  }
  
  public LimitTypePropertyLists getCumulativeLimitTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws EntitlementException
  {
    Limits localLimits = getCumulativeLimits(paramEntitlementGroupMember);
    LimitTypePropertyLists localLimitTypePropertyLists = new LimitTypePropertyLists();
    a(localLimits, localLimitTypePropertyLists, paramString1, paramString2, true);
    return localLimitTypePropertyLists;
  }
  
  private void a(Limits paramLimits, ArrayList paramArrayList, String paramString1, String paramString2, boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet((int)Math.ceil(Math.min(paramLimits.size(), this.g.size()) * 1.334D));
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      String str = localLimit.getLimitName();
      if ((str != null) && (!localHashSet.contains(str)))
      {
        localHashSet.add(str);
        LimitTypePropertyList localLimitTypePropertyList = this.g.getByOperationName(str);
        if (localLimitTypePropertyList.isPropertySet(paramString1, paramString2)) {
          if (paramBoolean) {
            paramArrayList.add(localLimitTypePropertyList.clone());
          } else {
            paramArrayList.add(str);
          }
        }
      }
    }
  }
  
  public void cleanup(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.cleanup";
    PreparedStatement localPreparedStatement = null;
    Connection localConnection = null;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -paramInt2);
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from running_total where run_total_id in (select run_total_id from limits l, running_total r  where l.limit_id = r.limit_id and l.period = ? and r.start_date < ? )");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setTimestamp(2, new Timestamp(localCalendar.getTime().getTime()));
      DBUtil.executeUpdate(localPreparedStatement, "delete from running_total where run_total_id in (select run_total_id from limits l, running_total r  where l.limit_id = r.limit_id and l.period = ? and r.start_date < ? )");
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
  }
  
  public void cleanup(long paramLong)
  {
    if (this.c) {
      this.jdField_null.cleanup(paramLong);
    }
  }
  
  public int cleanupCacheChanges(long paramLong)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.cleanupCacheChanges";
    PreparedStatement localPreparedStatement = null;
    Connection localConnection = null;
    int j = -1;
    try
    {
      localConnection = DBUtil.getConnection(this.jdField_void, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from ent_cache_changes where change_time < ?");
      localPreparedStatement.setTimestamp(1, new Timestamp(a(localConnection).getTime() - paramLong));
      j = DBUtil.executeUpdate(localPreparedStatement, "delete from ent_cache_changes where change_time < ?");
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str, 2);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.jdField_void, localConnection);
    }
    return j;
  }
  
  public void shutDown()
    throws Exception
  {
    synchronized (this.entMap)
    {
      this.entMap.clear();
    }
    synchronized (this.objTypeMap)
    {
      this.objTypeMap.clear();
    }
    this.entMap = null;
    this.objTypeMap = null;
    this.e = false;
    ConnectionPool.releasePool(this.jdField_void);
    this.jdField_void = null;
  }
  
  private void jdMethod_new(Connection paramConnection, int paramInt)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.iModifiedThis";
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str2;
      if ((this.dbType.equalsIgnoreCase("ORACLE:THIN")) || (this.dbType.equalsIgnoreCase("ORACLE:OCI8"))) {
        str2 = "INSERT INTO ent_cache_changes( group_id, change_maker, change_time ) VALUES ( ?, ?, systimestamp )";
      } else if ((this.dbType.equalsIgnoreCase("DB2:APP")) || (this.dbType.equalsIgnoreCase("DB2:UN2")) || (this.dbType.equalsIgnoreCase("DB2:NET")) || (this.dbType.equalsIgnoreCase("DB2:AS390"))) {
        str2 = "INSERT INTO ent_cache_changes( group_id, change_maker, change_time ) VALUES ( ?, ?, current timestamp )";
      } else if (this.dbType.equalsIgnoreCase("ASE")) {
        str2 = "INSERT INTO ent_cache_changes( group_id, change_maker, change_time ) VALUES ( ?, ?, getdate() )";
      } else {
        throw new EntitlementException(33);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setLong(2, this.jdField_goto);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
  }
  
  private void jdMethod_int(Connection paramConnection, EntitlementGroupMember paramEntitlementGroupMember)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.iModifiedThis";
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str2;
      if ((this.dbType.equalsIgnoreCase("ORACLE:THIN")) || (this.dbType.equalsIgnoreCase("ORACLE:OCI8"))) {
        str2 = "INSERT INTO ent_cache_changes ( group_id, member_type, member_subtype, member_id, change_maker, change_time ) VALUES ( ?, ?, ?, ?, ?, systimestamp )";
      } else if ((this.dbType.equalsIgnoreCase("DB2:APP")) || (this.dbType.equalsIgnoreCase("DB2:UN2")) || (this.dbType.equalsIgnoreCase("DB2:NET")) || (this.dbType.equalsIgnoreCase("DB2:AS390"))) {
        str2 = "INSERT INTO ent_cache_changes ( group_id, member_type, member_subtype, member_id, change_maker, change_time ) VALUES ( ?, ?, ?, ?, ?, current timestamp )";
      } else if (this.dbType.equalsIgnoreCase("ASE")) {
        str2 = "INSERT INTO ent_cache_changes ( group_id, member_type, member_subtype, member_id, change_maker, change_time ) VALUES ( ?, ?, ?, ?, ?, getdate() )";
      } else {
        throw new EntitlementException(33);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      localPreparedStatement.setInt(1, paramEntitlementGroupMember.getEntitlementGroupId());
      localPreparedStatement.setString(2, paramEntitlementGroupMember.getMemberType());
      localPreparedStatement.setString(3, paramEntitlementGroupMember.getMemberSubType());
      localPreparedStatement.setString(4, paramEntitlementGroupMember.getId());
      localPreparedStatement.setLong(5, this.jdField_goto);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new EntitlementException(localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
  }
  
  public long getDatabaseTime(Connection paramConnection)
    throws EntitlementException
  {
    String str = "EntitlementAdapter.getDatabaseTime";
    Object localObject = null;
    isInitialized();
    try
    {
      return a(paramConnection).getTime();
    }
    catch (Exception localException)
    {
      DBUtil.rollback(paramConnection);
      throw new EntitlementException(localException, str, 2);
    }
  }
  
  private Timestamp a(Connection paramConnection)
    throws EntitlementException, SQLException, Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, jdMethod_for());
      Timestamp localTimestamp = a(paramConnection, localPreparedStatement);
      return localTimestamp;
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  String jdMethod_for()
    throws EntitlementException
  {
    String str;
    if ((this.dbType.equalsIgnoreCase("ORACLE:THIN")) || (this.dbType.equalsIgnoreCase("ORACLE:OCI8"))) {
      str = "select systimestamp from dual";
    } else if ((this.dbType.equalsIgnoreCase("DB2:APP")) || (this.dbType.equalsIgnoreCase("DB2:UN2")) || (this.dbType.equalsIgnoreCase("DB2:NET")) || (this.dbType.equalsIgnoreCase("DB2:AS390"))) {
      str = "select current timestamp from sysibm.sysdummy1";
    } else if (this.dbType.equalsIgnoreCase("ASE")) {
      str = "select getdate()";
    } else {
      throw new EntitlementException(33);
    }
    return str;
  }
  
  Timestamp a(Connection paramConnection, PreparedStatement paramPreparedStatement)
    throws EntitlementException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = DBUtil.executeQuery(paramPreparedStatement, "");
      if (localResultSet.next())
      {
        Timestamp localTimestamp = localResultSet.getTimestamp(1);
        return localTimestamp;
      }
    }
    catch (Exception localException)
    {
      throw new EntitlementException(2, localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
    throw new EntitlementException(33);
  }
  
  public HashSet getItemsModifiedSince(Connection paramConnection, PreparedStatement paramPreparedStatement, long paramLong1, long paramLong2)
    throws EntitlementException
  {
    String str1 = "EntitlementAdapter.getItemsModifiedSince";
    ResultSet localResultSet = null;
    isInitialized();
    HashSet localHashSet1 = new HashSet();
    try
    {
      paramPreparedStatement.setLong(1, this.jdField_goto);
      paramPreparedStatement.setTimestamp(2, new Timestamp(paramLong1));
      paramPreparedStatement.setTimestamp(3, new Timestamp(paramLong2));
      localResultSet = DBUtil.executeQuery(paramPreparedStatement, "SELECT group_id, change_time, member_type, member_subtype, member_id FROM ent_cache_changes WHERE change_maker <> ? AND change_time > ? AND change_time <= ?");
      while (localResultSet.next())
      {
        int j = localResultSet.getInt(1);
        long l = localResultSet.getTimestamp(2).getTime();
        String str2 = localResultSet.getString(3);
        if (str2 == null)
        {
          localHashSet1.add(new ItemChange(new Integer(j), l));
        }
        else
        {
          EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
          localEntitlementGroupMember.setEntitlementGroupId(j);
          localEntitlementGroupMember.setMemberType(str2);
          localEntitlementGroupMember.setMemberSubType(localResultSet.getString(4));
          localEntitlementGroupMember.setId(localResultSet.getString(5));
          localHashSet1.add(new ItemChange(localEntitlementGroupMember, l));
        }
      }
      HashSet localHashSet2 = localHashSet1;
      return localHashSet2;
    }
    catch (Exception localException)
    {
      throw new EntitlementException(localException, str1, 2);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  public void cleanupCache(int paramInt)
  {
    if (this.c) {
      this.jdField_null.deleteEntitlementGroup(paramInt);
    }
  }
  
  public void cleanupCache(EntitlementGroupMember paramEntitlementGroupMember)
  {
    if (this.c) {
      this.jdField_null.deleteEntitlementGroupMember(paramEntitlementGroupMember);
    }
  }
  
  private void checkAvaliabilityOfForeignExchangeService()
    throws EntitlementException
  {
    if (this.h == null) {
      throw new EntitlementException(36);
    }
  }
  
  public void setFXService(IForeignExchangeService paramIForeignExchangeService)
  {
    this.h = paramIForeignExchangeService;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.EntitlementAdapter
 * JD-Core Version:    0.7.0.1
 */