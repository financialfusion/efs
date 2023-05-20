package com.ffusion.approvals.adapter;

import com.ffusion.approvals.ApprovalsException;
import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsDecision;
import com.ffusion.beans.approvals.ApprovalsDecisions;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroupMember;
import com.ffusion.beans.approvals.ApprovalsGroupMembers;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.beans.approvals.ApprovalsHistoryRecord;
import com.ffusion.beans.approvals.ApprovalsHistoryRecords;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsItemCount;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.tw.TWTransactions;
import com.ffusion.csil.core.CurrencyUtil;
import com.ffusion.csil.core.Util;
import com.ffusion.csil.handlers.FXHandler;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.tw.TransactionWarehouse;
import com.ffusion.tw.interfaces.TWException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.GrowableIntArray;
import com.ffusion.util.IntMap;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

public class ApprovalsAdapter
  implements IApprovalsAdapter
{
  private TransactionWarehouse d;
  private String bi;
  private String R;
  private static final BigDecimal r = new BigDecimal("1000000000000");
  private static final BigDecimal jdField_char = new BigDecimal("0");
  private static final long aE = 86400000L;
  private static final int ag = 100;
  private static final int f = 1000000;
  private static final int M = 254;
  private static final String bh = "Y";
  private static final String bf = "N";
  private static final String aQ = "A";
  private static final String C = "APPROVAL";
  private static final String w = "APPROVAL_GROUP";
  protected static final String SETTINGS_KEY = "APPROVALS_SETTINGS";
  private static final String a4 = "DB_PROPERTIES";
  private static final String N = "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL AND ( ( minAmount <= ? AND maxAmount >= ? ) OR ( minAmount <= ? AND maxAmount >= ?  ) OR       ( minAmount >= ? AND maxAmount <= ? ) )";
  private static final String B = "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? AND ( ( minAmount <= ? AND maxAmount >= ? ) OR ( minAmount <= ? AND maxAmount >= ?  ) OR       ( minAmount >= ? AND maxAmount <= ? ) )";
  private static final String bd = "select levelID from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL AND minAmount <= ? AND maxAmount >= ?";
  private static final String m = "select levelID from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? AND minAmount <= ? AND maxAmount >= ?";
  private static final String k = "select businessID, objectType from Appr_FlowLevels where levelID = ?";
  private static final String L = "select levelID, levelType, operationType, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? order by minAmount";
  private static final String t = "select levelID, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL order by minAmount";
  private static final String x = "select levelID, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? order by minAmount";
  private static final String y = "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL ";
  private static final String I = "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? ";
  private static final String z = "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? ";
  private static final String bg = "update Appr_FlowLevels set businessID = businessID where levelID = ?";
  private static final String au = "insert into Appr_FlowLevels( levelID, businessID, objectType, minAmount, maxAmount, levelType, operationType ) values ( ?, ?, ?, ?, ?, ?, ? )";
  private static final String ai = "update Appr_FlowLevels set minAmount = ?, maxAmount = ? where levelID = ?";
  private static final String ay = "delete from Appr_FlowLevels where levelID = ? AND businessID = ? AND objectType = ?";
  private static final String Q = "select ordinal, groupOrUserID, userType, isUser from Appr_FlowChains where levelID = ? order by ordinal";
  private static final String aM = "delete from Appr_FlowChains where levelID = ?";
  private static final String aW = "insert into Appr_FlowChains( levelID, ordinal, groupOrUserID, isUser, userType ) values( ?, ?, ?, ?, ? )";
  private static final String s = "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?";
  private static final String D = "select decision, approvingUserID, approvingUserType, dtDecision from Appr_Items where itemID = ? and itemType = ? and ordinal = ( (select ordinal from Appr_Items where itemID = ? and itemType = ? and processingState = 'ToApprove' and decision is null) -1)";
  private static final String ao = "select decision, approvingUserID, approvingUserType, dtDecision from Appr_Items where itemID = ? and itemType = ? and ( decision = 'Hold' or  decision = 'Rejected')";
  private static final String v = "select groupOrUserID, userType, isUser from Appr_Items where itemID = ? and itemType = ? and ( processingState = 'ToApprove' OR decision = 'Rejected' )";
  private static final String a5 = "select itemType, itemSubType, count(*) from Appr_Items A1 where A1.processingState = 'ToApprove' AND (( A1.isUser = 'Y' AND A1.groupOrUserID = ? AND A1.userType = " + String.valueOf(2) + ") " + "OR   ( A1.isUser = 'N' AND A1.groupOrUserID = ? )) " + "AND NOT EXISTS ( select A2.itemID from Appr_Items A2 " + "where A2.itemID = A1.itemID AND A2.itemType = A1.itemType " + "AND A2.ordinal != A1.ordinal " + "AND A2.approvingUserID = ? AND A2.approvingUserType = " + String.valueOf(2) + ") " + "GROUP BY itemType, itemSubType";
  private static final String a2 = "select itemType, itemSubType, count(*) from Appr_Items A1 where A1.processingState = 'ToApprove' AND (( A1.isUser = 'Y' AND A1.groupOrUserID = ? AND A1.userType = " + String.valueOf(1) + ") " + "OR   ( A1.isUser = 'N' AND A1.groupOrUserID = ? ) " + "OR ( A1.isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM " + "Appr_GroupMembers WHERE userID = ? ) ) ) " + "AND NOT ( A1.submittingUserID = ? AND A1.isUser <> 'Y') " + "AND NOT EXISTS ( select A2.itemID from Appr_Items A2 " + "where A2.itemID = A1.itemID AND A2.itemType = A1.itemType " + "AND A2.ordinal != A1.ordinal " + "AND A2.approvingUserID = ? AND A2.approvingUserType = " + String.valueOf(1) + ")" + "GROUP BY itemType, itemSubType";
  private static final String aP = "select itemType, itemID, processingState from Appr_Items where  ( ( processingState = 'ToApprove' AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + " ) OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + "OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) order by itemType, itemID, ordinal";
  private static final String a6 = "select itemType, itemID, processingState from Appr_Items where  ( ( processingState = 'ToApprove' AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(1) + ") OR ( isUser = 'N' AND groupOrUserID = ? )" + " OR ( isUser='A' AND groupOrUserID IN ( select apprGroupID FROM " + "Appr_GroupMembers WHERE userID = ?) ) ) )" + "OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(1) + " ) ) AND NOT ( submittingUserID = ? AND isUser <> 'Y') order by itemType, itemID, ordinal";
  private static final String ab = "select itemType, itemID, processingState from Appr_Items where  ( ( processingState = 'ToApprove' AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + " ) OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + "OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) ";
  private static final String a0 = "select itemType, itemID, processingState from Appr_Items where  ( ( processingState = 'ToApprove' AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(1) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) " + "OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM " + "Appr_GroupMembers WHERE userID = ? ) ) ) ) " + "OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(1) + " ) ) AND NOT ( submittingUserID = ? AND isUser <> 'Y') ";
  private static final String Z = " order by itemType, itemID, ordinal";
  private static final String E = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items where businessID <> 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) order by itemType, itemID, ordinal";
  private static final String o = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items where businessID = 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) order by itemType, itemID, ordinal";
  private static final String bl = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items, business where Appr_Items.businessID <> 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) AND business.affiliate_bank_id = ? AND business.directory_id = Appr_Items.businessID " + "order by itemType, itemID, ordinal";
  private static final String aL = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items, customer where Appr_Items.businessID = 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) AND customer.affiliate_bank_id = ? AND customer.directory_id = Appr_Items.submittingUserID " + "order by itemType, itemID, ordinal";
  private static final String b = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission,  processingState from Appr_Items where ( ( processingState = 'ToApprove' AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(1) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) " + "OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM " + "Appr_GroupMembers WHERE userID = ? ) ) ) ) " + "OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(1) + " ) ) AND NOT ( submittingUserID = ? AND isUser <> 'Y') order by itemType, itemID, ordinal";
  private static final String i = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items where businessID <> 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) ";
  private static final String av = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items where businessID = 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) ";
  private static final String a = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items, business where Appr_Items.businessID <> 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) AND business.affiliate_bank_id = ? AND business.directory_id = Appr_Items.businessID ";
  private static final String jdField_do = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission, processingState from Appr_Items, customer where Appr_Items.businessID = 0 AND ( ( processingState = 'ToApprove'  AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(2) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) ) )" + " OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(2) + " ) ) AND customer.affiliate_bank_id = ? AND customer.directory_id = Appr_Items.submittingUserID ";
  private static final String bk = "select itemID, itemType, submittingUserID, submittingUserName, dtSubmission,  processingState from Appr_Items where ( ( processingState = 'ToApprove' AND ( ( isUser = 'Y' AND groupOrUserID = ? AND userType = " + String.valueOf(1) + ") OR ( isUser = 'N' AND groupOrUserID = ? ) " + "OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM " + "Appr_GroupMembers WHERE userID = ? ) ) ) ) " + "OR ( processingState = 'Approved' AND approvingUserID = ? AND approvingUserType = " + String.valueOf(1) + " ) ) AND NOT ( submittingUserID = ? AND isUser <> 'Y') ";
  private static final String ak = " order by itemType, itemID, ordinal";
  private static final String X = "insert into Appr_Items ( itemID, itemType, itemSubType, ordinal, businessID, groupOrUserID, userType,  isUser, submittingUserID, processingState, executionState, submittingUserName, dtSubmission, dtDue )  values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Passed', ?, ?, ? )";
  private static final String J = "select count(*) from Appr_Items where itemID = ? AND itemType = ? AND processingState = 'Complete'";
  private static final String aZ = "select itemID from Appr_Items where itemID = ? AND itemType = ? AND ( decision = 'Rejected' OR processingState = 'ToApprove' )";
  private static final String be = "update Appr_Items set processingState = 'Waiting', approvingUserID = NULL, approvingUserType = NULL, decision = NULL, dtDecision = NULL where itemID = ? AND itemType = ? AND ordinal <> 1";
  private static final String T = "update Appr_Items set processingState = 'ToApprove', approvingUserID = NULL, approvingUserType = NULL, decision = NULL, dtDecision = NULL where itemID = ? AND itemType = ? AND ordinal = 1";
  private static final String a9 = "update Appr_Items set submittingUserID = ?where itemID = ? AND itemType = ?";
  private static final String bj = "select processingState from Appr_Items where itemID = ? AND itemType = ?";
  private static final String aC = "delete from Appr_Items where itemID = ? AND itemType = ?";
  private static final String A = "select ordinal, groupOrUserID, isUser, userType, decision, submittingUserID from Appr_Items where itemID = ? AND itemType = ? AND processingState = 'ToApprove'";
  private static final String aB = "update Appr_Items set approvingUserID = ?, approvingUserType = ?, decision = ?, dtDecision = ?, processingState = ? where itemID = ? AND itemType = ? AND ordinal = ? AND processingState = 'ToApprove'";
  private static final String G = "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?";
  private static final String jdField_try = "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?";
  private static final String jdField_int = "delete from Appr_FlowLevels where businessID = ? and objectType=1";
  private static final String aV = "delete from Appr_FlowLevels where businessID = ? and objectType = ?";
  private static final String al = "select count(*) from Appr_FlowChains where groupOrUserID = ? AND userType = ? AND isUser = 'Y'";
  private static final String g = "select count(*) from Appr_Items where groupOrUserID = ? AND userType = ? AND isUser = 'Y' AND processingState <>'Complete'";
  private static final String ba = "select count(*) from Appr_GroupMembers where userID = ?";
  private static final String aj = "select levelID, ordinal from Appr_FlowChains where groupOrUserID = ? AND userType = ? AND isUser = 'Y' order by levelID";
  private static final String aS = "select levelID, ordinal from Appr_FlowChains where groupOrUserID = ? AND isUser = 'N' order by levelID";
  private static final String aK = "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND userType = ? AND isUser = 'Y' AND processingState <> 'Complete' order by itemType, itemID";
  private static final String jdField_void = "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND isUser = 'N' AND processingState <> 'Complete' order by itemType, itemID";
  private static final String jdField_for = "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND userType = ? AND isUser = 'Y'";
  private static final String n = "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'N'";
  private static final String jdField_else = "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND userType = ? AND isUser = 'Y'";
  private static final String aI = "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'N'";
  private static final String bm = "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal";
  private static final String aw = "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal";
  private static final String jdField_long = "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?";
  private static final String P = "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?";
  private static final String jdField_goto = "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?";
  private static final String a3 = "select count(*) from Appr_FlowChains where groupOrUserID = ? AND isUser = 'N'";
  private static final String q = "select count(*) from Appr_Items where groupOrUserID = ? AND isUser = 'N' AND processingState <> 'Complete'";
  private static final String aN = "select count(*) from Appr_FlowLevels where businessID = ? AND objectType=1";
  private static final String an = "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? ";
  private static final String ac = "select count(*) from Appr_Items where businessID = ? AND processingState <> 'Complete'";
  private static final String U = "select count(*) from Appr_FlowChains where ( groupOrUserID = ? AND userType = ? AND isUser = 'Y') OR ( groupOrUserID = ? AND isUser = 'N' )OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM Appr_GroupMembers WHERE userID = ?) )";
  private static final String a8 = "select count(*) from Appr_Items where ( ( groupOrUserID = ? AND userType = ? AND isUser = 'Y' ) OR ( groupOrUserID = ? AND isUser = 'N' ) OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM Appr_GroupMembers WHERE userID = ?) ) ) AND  ( processingState <> 'Complete' )";
  private static final String bb = "select distinct itemID from Appr_Items where processingState = 'Complete' AND itemType = " + Integer.toString(1) + " AND dtDecision <= ? ORDER BY itemID";
  private static final String aF = "select distinct itemID from Appr_Items where processingState != 'Complete' AND itemType = " + Integer.toString(1) + " AND businessID = ? ORDER BY itemID";
  private static final String jdField_new = "delete from Appr_Items where itemType = " + Integer.toString(1) + " and itemID = ? ";
  private static final String aR = "update Appr_Items set executionState = 'Failed', processingState = 'Approved' where itemID = ? AND itemType = ?";
  private static final String l = "update Appr_Items set processingState = 'ToApprove', decision = 'Hold' where itemID = ? AND itemType = ? AND ordinal = (select max(ordinal) from Appr_Items where itemID = ? and itemType = ?)";
  private static final String a1 = "update Appr_Items set executionState = 'Failed', processingState = 'Waiting' where itemID = ? AND itemType = ?";
  private static final String aT = "update Appr_Items set processingState = 'Approved' where itemID = ? AND itemType = ? AND decision <> 'Rejected' AND decision is not null";
  private static final String jdField_byte = "update Appr_Items set processingState = 'ToApprove', decision = 'Hold' where itemID = ? AND itemType = ? AND decision = 'Rejected'";
  private static final String aH = "SELECT approvingUserID, approvingUserType, decision, dtDecision, processingState FROM Appr_Items WHERE itemID = ? AND itemType = ? ORDER BY ordinal";
  private static final String aU = "SELECT ordinal FROM Appr_Items WHERE itemID = ? AND itemType = ? AND approvingUserID = ? AND approvingUserType = ? AND processingState != 'ToApprove'";
  private static final String ae = "INSERT into Appr_ItemProps ( itemID, itemType, propName, propValue ) VALUES( ?, ?, ?, ?)";
  private static final String K = "SELECT propValue from Appr_ItemProps where itemID = ? and itemType = ? and propName = ?";
  private static final String az = "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?";
  private static final String ap = "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ? AND propName = 'RejectReason'";
  private static final String aD = "SELECT DISTINCT itemID, itemSubType FROM Appr_Items WHERE ";
  private static final String V = " processingState <> 'Complete' ";
  private static final String e = " decision = 'Rejected' ";
  private static final String bo = "insert into Appr_Groups( apprGroupID, businessID, groupName ) values ( ?, ?, ? )";
  private static final String af = "select apprGroupID, groupName from Appr_Groups where businessID = ?";
  private static final String at = "select groupName from Appr_Groups where apprGroupID = ? ";
  private static final String h = "update Appr_Groups set groupName = ? where apprGroupID = ?";
  private static final String bc = "select userID from Appr_GroupMembers where apprGroupID = ? ";
  private static final String a7 = "select userID from Appr_GroupMembers where apprGroupID = ? AND userID = ? ";
  private static final String jdField_case = "select count(*) from Appr_FlowChains where groupOrUserID = ? AND isUser = 'A'";
  private static final String ah = "select count(*) from Appr_Items where groupOrUserID = ? AND isUser = 'A' AND processingState <> 'Complete'";
  private static final String Y = "delete from Appr_Groups where businessID = ?";
  private static final String H = "select apprGroupID from Appr_GroupMembers where userID = ? order by apprGroupID";
  private static final String j = "delete from Appr_GroupMembers where userID = ? ";
  private static final String aJ = "update Appr_GroupMembers set apprGroupID = apprGroupID where userID = ?";
  private static final String aG = "delete from Appr_GroupMembers where userID = ?";
  private static final String jdField_null = "INSERT into Appr_GroupMembers ( apprGroupID, userID ) VALUES( ?, ?)";
  private static final String aX = "select count(*) from Appr_GroupMembers where apprGroupID = ?";
  private static final String F = "select levelID, ordinal from Appr_FlowChains where isUser = 'A' AND groupOrUserID = ? ";
  private static final String aO = "delete from Appr_Groups where apprGroupID = ? ";
  private static final String ax = "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND isUser = 'A' AND processingState <> 'Complete' order by itemType, itemID";
  private static final String bn = "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'A'";
  private static final String p = "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'A'";
  private static final String S = "select g.apprGroupID, g.businessID, g.groupName from Appr_Groups g, Appr_GroupMembers gm WHERE g.apprGroupID = gm.apprGroupID AND gm.userID = ? ";
  private static final String[] W = { "Pending" };
  private static final String aq = " AND ";
  private static final String jdField_if = " OR ";
  private static final String as = " ( ";
  private static final String ar = " ) ";
  private static final String aa = " businessID = ? ";
  private static final String c = " submittingUserID = ? ";
  private static final String u = " itemSubType = ? ";
  private static final String ad = " dtSubmission >= ? ";
  private static final String am = " dtSubmission <= ? ";
  private static final String aA = " dtDue >= ? ";
  private static final String O = " dtDue <= ? ";
  private static final String aY = "MMM d, yyyy";
  
  public void initialize(HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.initialize";
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("APPROVALS_SETTINGS");
      Properties localProperties = null;
      if (localHashMap != null) {
        localProperties = (Properties)localHashMap.get("DB_PROPERTIES");
      }
      if (localProperties == null) {
        localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      }
      if (localProperties == null) {
        throw new ApprovalsException(30104, "Initializing the approvals adapter failed.");
      }
      this.bi = localProperties.getProperty("ConnectionType");
      try
      {
        this.R = ConnectionPool.init(localProperties);
      }
      catch (PoolException localPoolException)
      {
        throw new ApprovalsException(30012, "Initializing the approvals adapter failed.", localPoolException);
      }
      this.d = new TransactionWarehouse(paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Initializing the approvals adapter failed.", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Initializing the approvals adapter failed.", localThrowable);
      }
      throw new ApprovalsException("Initializing the approvals adapter failed.", localThrowable);
    }
  }
  
  public ApprovalsLevels getWorkflowLevels(int paramInt, String paramString, HashMap paramHashMap)
    throws ApprovalsException
  {
    return getWorkflowLevels(paramInt, paramString, null, paramHashMap);
  }
  
  public ApprovalsLevels getWorkflowLevels(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException
  {
    return getWorkflowLevels(paramInt, 1, paramString1, paramString2, paramHashMap);
  }
  
  public ApprovalsLevels getWorkflowLevels(int paramInt1, int paramInt2, String paramString1, String paramString2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.getWorkflowLevels";
    ApprovalsLevels localApprovalsLevels = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str2 = null;
      if (paramInt2 == 2) {
        str2 = (String)paramHashMap.get("ApprovalServicesPackageAffiliateBankCurrencyCode");
      } else {
        str2 = CurrencyUtil.getCurrencyCodeByBusinessId(paramInt1);
      }
      if (str2 == null) {
        str2 = Util.getLimitBaseCurrency();
      }
      localConnection = DBUtil.getConnection(this.R, true, 2);
      if (paramString2 == null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select levelID, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL order by minAmount");
        localPreparedStatement.setInt(1, paramInt1);
        localPreparedStatement.setInt(2, paramInt2);
        localPreparedStatement.setString(3, paramString1);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select levelID, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL order by minAmount");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select levelID, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? order by minAmount");
        localPreparedStatement.setInt(1, paramInt1);
        localPreparedStatement.setInt(2, paramInt2);
        localPreparedStatement.setString(3, paramString1);
        localPreparedStatement.setString(4, paramString2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select levelID, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? order by minAmount");
      }
      while (localResultSet.next())
      {
        if (localApprovalsLevels == null) {
          localApprovalsLevels = new ApprovalsLevels(Locale.getDefault());
        }
        ApprovalsLevel localApprovalsLevel = localApprovalsLevels.add();
        localApprovalsLevel.setLevelID(localResultSet.getInt(1));
        localApprovalsLevel.setMinAmount(new Currency(localResultSet.getBigDecimal(2), str2, Locale.getDefault()));
        localApprovalsLevel.setMaxAmount(new Currency(localResultSet.getBigDecimal(3), str2, Locale.getDefault()));
        localApprovalsLevel.setBusinessID(paramInt1);
        localApprovalsLevel.setObjectType(paramInt2);
        localApprovalsLevel.setLevelType(paramString1);
        localApprovalsLevel.setOperationType(paramString2);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not obtain the approval flow levels.", localThrowable);
      }
      throw new ApprovalsException("Could not obtain the approval flow levels.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsLevels;
  }
  
  public void addWorkflowLevel(ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.addWorkflowLevel";
    if (paramApprovalsLevel == null) {
      throw new ApprovalsException(30066, "Could not add a new approvals flow level.");
    }
    String str2 = paramApprovalsLevel.getLevelType();
    String str3 = paramApprovalsLevel.getOperationType();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      BigDecimal localBigDecimal1 = null;
      if (paramApprovalsLevel.getMinAmount() != null) {
        localBigDecimal1 = paramApprovalsLevel.getMinAmount().getAmountValue();
      } else {
        localBigDecimal1 = jdField_char;
      }
      BigDecimal localBigDecimal2 = null;
      if (paramApprovalsLevel.getMaxAmount() != null) {
        localBigDecimal2 = paramApprovalsLevel.getMaxAmount().getAmountValue();
      } else {
        localBigDecimal2 = r;
      }
      if ((localBigDecimal1.signum() == -1) || (localBigDecimal2.signum() == -1)) {
        throw new ApprovalsException(30053, "Could not add a new approvals flow level.");
      }
      if (localBigDecimal2.compareTo(localBigDecimal1) == -1) {
        throw new ApprovalsException(30054, "Could not add a new approvals flow level.");
      }
      int i1 = paramApprovalsLevel.getBusinessID();
      int i2 = paramApprovalsLevel.getObjectType();
      if ((i2 != 1) && (i2 != 2)) {
        if (i2 == 0)
        {
          i2 = 1;
          paramApprovalsLevel.setObjectType(1);
        }
        else
        {
          throw new ApprovalsException(30150, "Could not add a new approvals flow level.");
        }
      }
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      if (str3 == null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL ");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setInt(2, i2);
        localPreparedStatement.setString(3, str2);
        DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL ");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL AND ( ( minAmount <= ? AND maxAmount >= ? ) OR ( minAmount <= ? AND maxAmount >= ?  ) OR       ( minAmount >= ? AND maxAmount <= ? ) )");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setInt(2, i2);
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setBigDecimal(4, localBigDecimal1);
        localPreparedStatement.setBigDecimal(5, localBigDecimal1);
        localPreparedStatement.setBigDecimal(6, localBigDecimal2);
        localPreparedStatement.setBigDecimal(7, localBigDecimal2);
        localPreparedStatement.setBigDecimal(8, localBigDecimal1);
        localPreparedStatement.setBigDecimal(9, localBigDecimal2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL AND ( ( minAmount <= ? AND maxAmount >= ? ) OR ( minAmount <= ? AND maxAmount >= ?  ) OR       ( minAmount >= ? AND maxAmount <= ? ) )");
      }
      else
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? ");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setInt(2, i2);
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str3);
        DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? ");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? AND ( ( minAmount <= ? AND maxAmount >= ? ) OR ( minAmount <= ? AND maxAmount >= ?  ) OR       ( minAmount >= ? AND maxAmount <= ? ) )");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setInt(2, i2);
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str3);
        localPreparedStatement.setBigDecimal(5, localBigDecimal1);
        localPreparedStatement.setBigDecimal(6, localBigDecimal1);
        localPreparedStatement.setBigDecimal(7, localBigDecimal2);
        localPreparedStatement.setBigDecimal(8, localBigDecimal2);
        localPreparedStatement.setBigDecimal(9, localBigDecimal1);
        localPreparedStatement.setBigDecimal(10, localBigDecimal2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? AND ( ( minAmount <= ? AND maxAmount >= ? ) OR ( minAmount <= ? AND maxAmount >= ?  ) OR       ( minAmount >= ? AND maxAmount <= ? ) )");
      }
      if ((localResultSet.next()) && (localResultSet.getInt(1) > 0)) {
        throw new ApprovalsException(30055, "Could not add a new approvals flow level.");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into Appr_FlowLevels( levelID, businessID, objectType, minAmount, maxAmount, levelType, operationType ) values ( ?, ?, ?, ?, ?, ?, ? )");
      int i3 = DBUtil.getNextId(localConnection, this.bi, "APPROVAL");
      localPreparedStatement.setInt(1, i3);
      localPreparedStatement.setInt(2, i1);
      localPreparedStatement.setInt(3, i2);
      localPreparedStatement.setBigDecimal(4, localBigDecimal1);
      localPreparedStatement.setBigDecimal(5, localBigDecimal2);
      localPreparedStatement.setString(6, str2);
      localPreparedStatement.setString(7, str3);
      int i4 = DBUtil.executeUpdate(localPreparedStatement, "insert into Appr_FlowLevels( levelID, businessID, objectType, minAmount, maxAmount, levelType, operationType ) values ( ?, ?, ?, ?, ?, ?, ? )");
      if (i4 == 0) {
        throw new ApprovalsException(30001, "Could not add a new approvals flow level.");
      }
      DBUtil.commit(localConnection);
      paramApprovalsLevel.setLevelID(i3);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not add a new approvals flow level.", localThrowable);
      }
      throw new ApprovalsException("Could not add a new approvals flow level.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public void updateWorkflowLevels(ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.updateWorkflowLevels";
    if (paramApprovalsLevels == null) {
      throw new ApprovalsException(30067, "Could not modify the approval flow levels");
    }
    if (paramApprovalsLevels.isEmpty()) {
      return;
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      int i1 = ((ApprovalsLevel)paramApprovalsLevels.get(0)).getLevelID();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select businessID, objectType from Appr_FlowLevels where levelID = ?");
      localPreparedStatement.setInt(1, i1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select businessID, objectType from Appr_FlowLevels where levelID = ?");
      int i2 = -1;
      int i3 = -1;
      if (localResultSet.next())
      {
        i2 = localResultSet.getInt(1);
        i3 = localResultSet.getInt(2);
      }
      else
      {
        throw new ApprovalsException(30052, "Could not modify the approval flow levels");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? ");
      localPreparedStatement.setInt(1, i2);
      localPreparedStatement.setInt(2, i3);
      DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? ");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select levelID, levelType, operationType, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? order by minAmount");
      localPreparedStatement.setInt(1, i2);
      localPreparedStatement.setInt(2, i3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select levelID, levelType, operationType, minAmount, maxAmount from Appr_FlowLevels where businessID = ? AND objectType = ? order by minAmount");
      HashMap localHashMap = new HashMap();
      String str2;
      Object localObject3;
      Object localObject4;
      while (localResultSet.next())
      {
        localObject1 = new b();
        ((b)localObject1).a(localResultSet.getInt(1));
        localObject2 = localResultSet.getString(2);
        ((b)localObject1).a((String)localObject2);
        str2 = localResultSet.getString(3);
        ((b)localObject1).jdField_if(str2);
        ((b)localObject1).a(localResultSet.getBigDecimal(4));
        ((b)localObject1).jdField_if(localResultSet.getBigDecimal(5));
        if (localHashMap.containsKey(localObject2))
        {
          localObject3 = (HashMap)localHashMap.get(localObject2);
          if (((HashMap)localObject3).containsKey(str2))
          {
            localObject4 = (Vector)((HashMap)localObject3).get(str2);
            ((Vector)localObject4).add(localObject1);
          }
          else
          {
            localObject4 = new Vector();
            ((Vector)localObject4).add(localObject1);
            ((HashMap)localObject3).put(str2, localObject4);
          }
        }
        else
        {
          localObject3 = new Vector();
          ((Vector)localObject3).add(localObject1);
          localObject4 = new HashMap();
          ((HashMap)localObject4).put(str2, localObject3);
          localHashMap.put(localObject2, localObject4);
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      Object localObject1 = paramApprovalsLevels;
      Object localObject2 = localHashMap.keySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        str2 = (String)((Iterator)localObject2).next();
        localObject3 = (HashMap)localHashMap.get(str2);
        localObject4 = ((HashMap)localObject3).keySet().iterator();
        while (((Iterator)localObject4).hasNext())
        {
          String str3 = (String)((Iterator)localObject4).next();
          Vector localVector = (Vector)((HashMap)localObject3).get(str3);
          localObject1 = a((ApprovalsLevels)localObject1, localVector, localConnection);
        }
      }
      if (!((ApprovalsLevels)localObject1).isEmpty()) {
        throw new ApprovalsException(30001, "Could not modify the approval flow levels");
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not modify the approval flow levels", localThrowable);
      }
      throw new ApprovalsException("Could not modify the approval flow levels", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  private ApprovalsLevels a(ApprovalsLevels paramApprovalsLevels, Vector paramVector, Connection paramConnection)
    throws ApprovalsException, Exception
  {
    if ((paramApprovalsLevels.isEmpty()) || (paramVector.isEmpty())) {
      return paramApprovalsLevels;
    }
    int i1 = paramVector.size();
    b[] arrayOfb = new b[i1];
    arrayOfb = (b[])paramVector.toArray(arrayOfb);
    ApprovalsLevels localApprovalsLevels1 = (ApprovalsLevels)paramApprovalsLevels.clone();
    Iterator localIterator = paramApprovalsLevels.iterator();
    int i2;
    int i3;
    while (localIterator.hasNext())
    {
      localObject1 = (ApprovalsLevel)localIterator.next();
      i2 = ((ApprovalsLevel)localObject1).getLevelID();
      for (i3 = 0; i3 < i1; i3++) {
        if (arrayOfb[i3].jdField_if() == i2)
        {
          if (!((ApprovalsLevel)localObject1).getLevelType().equals(arrayOfb[i3].jdField_int())) {
            throw new ApprovalsException(30110, "Could not modify the approval flow levels");
          }
          if ((((ApprovalsLevel)localObject1).getOperationType() != null) && (arrayOfb[i3].jdField_do() != null) && (!((ApprovalsLevel)localObject1).getOperationType().equals(arrayOfb[i3].jdField_do()))) {
            throw new ApprovalsException(30121, "Could not modify the approval flow levels");
          }
          BigDecimal localBigDecimal1 = null;
          if (((ApprovalsLevel)localObject1).getMinAmount() != null) {
            localBigDecimal1 = ((ApprovalsLevel)localObject1).getMinAmount().getAmountValue();
          } else {
            localBigDecimal1 = jdField_char;
          }
          BigDecimal localBigDecimal2 = null;
          if (((ApprovalsLevel)localObject1).getMaxAmount() != null) {
            localBigDecimal2 = ((ApprovalsLevel)localObject1).getMaxAmount().getAmountValue();
          } else {
            localBigDecimal2 = r;
          }
          if (localBigDecimal1.signum() == -1) {
            throw new ApprovalsException(30053, "Could not modify the approval flow levels");
          }
          if (localBigDecimal2.compareTo(localBigDecimal1) == -1) {
            throw new ApprovalsException(30054, "Could not modify the approval flow levels");
          }
          arrayOfb[i3].a(true);
          arrayOfb[i3].a(localBigDecimal1);
          arrayOfb[i3].jdField_if(localBigDecimal2);
          localApprovalsLevels1.remove(localObject1);
          break;
        }
      }
    }
    Arrays.sort(arrayOfb);
    Object localObject1 = null;
    try
    {
      localObject1 = DBUtil.prepareStatement(paramConnection, "update Appr_FlowLevels set minAmount = ?, maxAmount = ? where levelID = ?");
      for (i2 = 0; i2 < i1; i2++) {
        if (arrayOfb[i2].a())
        {
          if ((i2 > 0) && (arrayOfb[i2].jdField_for().compareTo(arrayOfb[(i2 - 1)].jdField_new()) < 1)) {
            throw new ApprovalsException(30055, "Could not modify the approval flow levels");
          }
          if ((i2 < i1 - 1) && (arrayOfb[i2].jdField_new().compareTo(arrayOfb[(i2 + 1)].jdField_for()) > -1)) {
            throw new ApprovalsException(30055, "Could not modify the approval flow levels");
          }
          ((PreparedStatement)localObject1).clearParameters();
          ((PreparedStatement)localObject1).setBigDecimal(1, arrayOfb[i2].jdField_for());
          ((PreparedStatement)localObject1).setBigDecimal(2, arrayOfb[i2].jdField_new());
          ((PreparedStatement)localObject1).setInt(3, arrayOfb[i2].jdField_if());
          i3 = DBUtil.executeUpdate((PreparedStatement)localObject1, "update Appr_FlowLevels set minAmount = ?, maxAmount = ? where levelID = ?");
          if (i3 == 0) {
            throw new ApprovalsException(30001, "Could not modify the approval flow levels");
          }
        }
      }
      ApprovalsLevels localApprovalsLevels2 = localApprovalsLevels1;
      return localApprovalsLevels2;
    }
    finally
    {
      DBUtil.closeStatement((PreparedStatement)localObject1);
      localObject1 = null;
    }
  }
  
  public void removeWorkflowLevels(ApprovalsLevels paramApprovalsLevels, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.removeWorkflowLevels";
    if (paramApprovalsLevels == null) {
      throw new ApprovalsException(30067, "Could not delete the approval flow levels");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      int i1 = ((ApprovalsLevel)paramApprovalsLevels.get(0)).getLevelID();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select businessID, objectType from Appr_FlowLevels where levelID = ?");
      localPreparedStatement.setInt(1, i1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select businessID, objectType from Appr_FlowLevels where levelID = ?");
      int i2 = -1;
      int i3 = -1;
      if (localResultSet.next())
      {
        i2 = localResultSet.getInt(1);
        i3 = localResultSet.getInt(2);
      }
      else
      {
        throw new ApprovalsException(30052, "Could not delete the approval flow levels");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? ");
      localPreparedStatement.setInt(1, i2);
      localPreparedStatement.setInt(2, i3);
      DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set levelID = levelID where businessID = ? AND objectType = ? ");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      Iterator localIterator = paramApprovalsLevels.iterator();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowLevels where levelID = ? AND businessID = ? AND objectType = ?");
      while (localIterator.hasNext())
      {
        ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)localIterator.next();
        localPreparedStatement.clearParameters();
        localPreparedStatement.setInt(1, localApprovalsLevel.getLevelID());
        localPreparedStatement.setInt(2, i2);
        localPreparedStatement.setInt(3, i3);
        int i4 = DBUtil.executeUpdate(localPreparedStatement, "delete from Appr_FlowLevels where levelID = ? AND businessID = ? AND objectType = ?");
        if (i4 == 0) {
          throw new ApprovalsException(30052, "Could not delete the approval flow levels");
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not delete the approval flow levels", localThrowable);
      }
      throw new ApprovalsException("Could not delete the approval flow levels", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public ApprovalsChainItems getWorkflowChainItems(ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.getWorkflowChainItems";
    if (paramApprovalsLevel == null) {
      throw new ApprovalsException(30066, "Could not obtain the approval flow chain for the specified approval level");
    }
    ApprovalsChainItems localApprovalsChainItems = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
      localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
      if (i1 == 0) {
        throw new ApprovalsException(30052, "Could not obtain the approval flow chain for the specified approval level");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ordinal, groupOrUserID, userType, isUser from Appr_FlowChains where levelID = ? order by ordinal");
      localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ordinal, groupOrUserID, userType, isUser from Appr_FlowChains where levelID = ? order by ordinal");
      while (localResultSet.next())
      {
        if (localApprovalsChainItems == null) {
          localApprovalsChainItems = new ApprovalsChainItems(Locale.getDefault());
        }
        ApprovalsChainItem localApprovalsChainItem = localApprovalsChainItems.add();
        localApprovalsChainItem.setGroupOrUserID(localResultSet.getInt(2));
        localApprovalsChainItem.setUserType(localResultSet.getInt(3));
        localApprovalsChainItem.setUsingUser(localResultSet.getString(4).equals("Y"));
        localApprovalsChainItem.setIsApprovalsGroup(localResultSet.getString(4).equals("A"));
      }
      DBUtil.commit(localConnection);
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not obtain the approval flow chain for the specified approval level", localThrowable);
      }
      throw new ApprovalsException("Could not obtain the approval flow chain for the specified approval level", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsChainItems;
  }
  
  public void setWorkflowChainItems(ApprovalsLevel paramApprovalsLevel, ApprovalsChainItems paramApprovalsChainItems, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.setWorkflowChainItems";
    if (paramApprovalsLevel == null) {
      throw new ApprovalsException(30066, "Could not modify the approval flow chain for the specified approval level");
    }
    if (paramApprovalsChainItems == null) {
      throw new ApprovalsException(30069, "Could not modify the approval flow chain for the specified approval level");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
      localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
      if (i1 == 0) {
        throw new ApprovalsException(30052, "Could not modify the approval flow chain for the specified approval level");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowChains where levelID = ?");
      localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from Appr_FlowChains where levelID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      int i2 = 1;
      Iterator localIterator = paramApprovalsChainItems.iterator();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into Appr_FlowChains( levelID, ordinal, groupOrUserID, isUser, userType ) values( ?, ?, ?, ?, ? )");
      while (localIterator.hasNext())
      {
        ApprovalsChainItem localApprovalsChainItem = (ApprovalsChainItem)localIterator.next();
        boolean bool1 = localApprovalsChainItem.isUsingUser();
        boolean bool2 = localApprovalsChainItem.getIsApprovalsGroup();
        localPreparedStatement.clearParameters();
        localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
        localPreparedStatement.setInt(2, i2++);
        localPreparedStatement.setInt(3, localApprovalsChainItem.getGroupOrUserID());
        if (bool1) {
          localPreparedStatement.setString(4, "Y");
        } else if (bool2) {
          localPreparedStatement.setString(4, "A");
        } else {
          localPreparedStatement.setString(4, "N");
        }
        localPreparedStatement.setInt(5, bool1 ? localApprovalsChainItem.getUserType() : 0);
        i1 = DBUtil.executeUpdate(localPreparedStatement, "insert into Appr_FlowChains( levelID, ordinal, groupOrUserID, isUser, userType ) values( ?, ?, ?, ?, ? )");
        if (i1 == 0) {
          throw new ApprovalsException(30001, "Could not modify the approval flow chain for the specified approval level");
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not modify the approval flow chain for the specified approval level", localThrowable);
      }
      throw new ApprovalsException("Could not modify the approval flow chain for the specified approval level", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public void clearWorkflowChainItems(ApprovalsLevel paramApprovalsLevel, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.clearWorkflowChainItems";
    if (paramApprovalsLevel == null) {
      throw new ApprovalsException(30066, "Could not delete the approval flow chain for the specified approval level");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
      localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
      if (i1 == 0) {
        throw new ApprovalsException(30052, "Could not delete the approval flow chain for the specified approval level");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowChains where levelID = ?");
      localPreparedStatement.setInt(1, paramApprovalsLevel.getLevelID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from Appr_FlowChains where levelID = ?");
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not delete the approval flow chain for the specified approval level", localThrowable);
      }
      throw new ApprovalsException("Could not delete the approval flow chain for the specified approval level", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public ApprovalsStatuses getPendingItems(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.getPendingItems";
    return getSubmittedItems(paramInt, W, paramHashMap1, paramHashMap2);
  }
  
  public ApprovalsStatuses getSubmittedItems(int paramInt, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.getSubmittedItems";
    if (paramHashMap1 == null) {
      throw new ApprovalsException(30070, "Could not obtain the submitted approval items");
    }
    if (paramArrayOfString == null) {
      throw new ApprovalsException(30122, "Could not obtain the submitted approval items");
    }
    if (paramArrayOfString.length > 2) {
      throw new ApprovalsException(30123, "Could not obtain the submitted approval items");
    }
    ApprovalsStatuses localApprovalsStatuses = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    PreparedStatement localPreparedStatement4 = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramInt == 1)
      {
        String str2 = (String)paramHashMap1.get("TrackingID");
        Integer localInteger1 = (Integer)paramHashMap1.get("ItemSubType");
        TWTransactions localTWTransactions = null;
        localConnection = DBUtil.getConnection(this.R, true, 2);
        Object localObject1;
        Object localObject2;
        Object localObject3;
        String str3;
        Object localObject4;
        int i6;
        if (str2 != null)
        {
          localObject1 = null;
          if (localInteger1 == null) {
            localObject1 = this.d.getTransaction(str2);
          } else {
            localObject1 = this.d.getTransaction(localInteger1.intValue(), str2);
          }
          if (localObject1 != null)
          {
            localTWTransactions = new TWTransactions(Locale.getDefault());
            localTWTransactions.add(localObject1);
          }
        }
        else
        {
          localObject1 = (Integer)paramHashMap1.get("SubmittingUser");
          Integer localInteger2 = (Integer)paramHashMap1.get("BusinessID");
          localObject2 = (String)paramHashMap1.get("StartDate");
          localObject3 = (String)paramHashMap1.get("EndDate");
          str3 = (String)paramHashMap1.get("StartDueDate");
          String str4 = (String)paramHashMap1.get("EndDueDate");
          if ((localObject1 == null) && (localInteger2 == null)) {
            throw new ApprovalsException(30101, "Could not obtain the submitted approval items");
          }
          if ((localObject1 != null) && (localInteger2 != null)) {
            throw new ApprovalsException(30119, "Could not obtain the submitted approval items");
          }
          StringBuffer localStringBuffer = new StringBuffer("SELECT DISTINCT itemID, itemSubType FROM Appr_Items WHERE ");
          for (int i5 = 0; i5 < paramArrayOfString.length; i5++)
          {
            if (i5 == 0) {
              localStringBuffer.append(" ( ");
            } else {
              localStringBuffer.append(" OR ");
            }
            if (paramArrayOfString[i5].equalsIgnoreCase("Pending")) {
              localStringBuffer.append(" processingState <> 'Complete' ");
            } else if (paramArrayOfString[i5].equalsIgnoreCase("Rejected")) {
              localStringBuffer.append(" decision = 'Rejected' ");
            } else {
              throw new ApprovalsException(30124, "Could not obtain the submitted approval items");
            }
            if (i5 == paramArrayOfString.length - 1) {
              localStringBuffer.append(" ) ");
            }
          }
          if (localObject1 != null) {
            localStringBuffer.append(" AND ").append(" submittingUserID = ? ");
          } else if (localInteger2 != null) {
            localStringBuffer.append(" AND ").append(" businessID = ? ");
          }
          if (localInteger1 != null) {
            localStringBuffer.append(" AND ").append(" itemSubType = ? ");
          }
          if ((localObject2 != null) && (localObject3 != null))
          {
            localStringBuffer.append(" AND ");
            localStringBuffer.append(" ( ");
            localStringBuffer.append(" dtSubmission >= ? ");
            localStringBuffer.append(" AND ");
            localStringBuffer.append(" dtSubmission <= ? ");
            localStringBuffer.append(" ) ");
          }
          else if (localObject2 != null)
          {
            localStringBuffer.append(" AND ").append(" dtSubmission >= ? ");
          }
          else if (localObject3 != null)
          {
            localStringBuffer.append(" AND ").append(" dtSubmission <= ? ");
          }
          if ((str3 != null) && (str4 != null))
          {
            localStringBuffer.append(" AND ");
            localStringBuffer.append(" ( ");
            localStringBuffer.append(" dtDue >= ? ");
            localStringBuffer.append(" AND ");
            localStringBuffer.append(" dtDue <= ? ");
            localStringBuffer.append(" ) ");
          }
          else if (str3 != null)
          {
            localStringBuffer.append(" AND ").append(" dtDue >= ? ");
          }
          else if (str4 != null)
          {
            localStringBuffer.append(" AND ").append(" dtDue <= ? ");
          }
          localObject4 = localStringBuffer.toString();
          localPreparedStatement1 = DBUtil.prepareStatement(localConnection, (String)localObject4);
          i6 = 1;
          if (localObject1 != null)
          {
            localPreparedStatement1.setInt(i6, ((Integer)localObject1).intValue());
            i6++;
          }
          else if (localInteger2 != null)
          {
            localPreparedStatement1.setInt(i6, localInteger2.intValue());
            i6++;
          }
          if (localInteger1 != null)
          {
            localPreparedStatement1.setInt(i6, localInteger1.intValue());
            i6++;
          }
          if (localObject2 != null)
          {
            localObject5 = new Timestamp(a((String)localObject2).getTime());
            localPreparedStatement1.setTimestamp(i6, (Timestamp)localObject5);
            i6++;
          }
          if (localObject3 != null)
          {
            localObject5 = new Timestamp(a((String)localObject3).getTime());
            localPreparedStatement1.setTimestamp(i6, (Timestamp)localObject5);
            i6++;
          }
          if (str3 != null)
          {
            localObject5 = new Timestamp(a(str3).getTime());
            localPreparedStatement1.setTimestamp(i6, (Timestamp)localObject5);
            i6++;
          }
          if (str4 != null)
          {
            localObject5 = new Timestamp(a(str4).getTime());
            localPreparedStatement1.setTimestamp(i6, (Timestamp)localObject5);
            i6++;
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement1, (String)localObject4);
          Object localObject5 = new GrowableIntArray();
          while (localResultSet.next()) {
            ((GrowableIntArray)localObject5).add(localResultSet.getInt(1));
          }
          DBUtil.closeAll(localPreparedStatement1, localResultSet);
          localPreparedStatement1 = null;
          localResultSet = null;
          localTWTransactions = this.d.getTransactions(((GrowableIntArray)localObject5).getElements());
        }
        if (localTWTransactions != null)
        {
          int i1 = localTWTransactions.size();
          localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select decision, approvingUserID, approvingUserType, dtDecision from Appr_Items where itemID = ? and itemType = ? and ordinal = ( (select ordinal from Appr_Items where itemID = ? and itemType = ? and processingState = 'ToApprove' and decision is null) -1)");
          for (int i2 = 0; i2 < i1; i2++)
          {
            localObject2 = (TWTransaction)localTWTransactions.get(i2);
            if (localApprovalsStatuses == null) {
              localApprovalsStatuses = new ApprovalsStatuses(Locale.getDefault());
            }
            localObject3 = new ApprovalsItem(Locale.getDefault());
            ((ApprovalsItem)localObject3).setItemID(((TWTransaction)localObject2).getID());
            ((ApprovalsItem)localObject3).setItemType(1);
            ((ApprovalsItem)localObject3).setItem(localObject2);
            ((ApprovalsItem)localObject3).setSubmittingUserID(((TWTransaction)localObject2).getUserID());
            ((ApprovalsItem)localObject3).setSubmissionDate(((TWTransaction)localObject2).getSubmissionDateTime());
            ((ApprovalsItem)localObject3).setItemSubType(((TWTransaction)localObject2).getTransactionType());
            ((ApprovalsItem)localObject3).setDueDate(((TWTransaction)localObject2).getTransaction().getApprovalDueDate());
            str3 = null;
            int i3 = -1;
            int i4 = -1;
            localObject4 = null;
            i6 = ((ApprovalsItem)localObject3).getItemID();
            int i7 = 0;
            localPreparedStatement1.clearParameters();
            localPreparedStatement1.setInt(1, i6);
            localPreparedStatement1.setInt(2, paramInt);
            localPreparedStatement1.setInt(3, i6);
            localPreparedStatement1.setInt(4, paramInt);
            localResultSet = DBUtil.executeQuery(localPreparedStatement1, "select decision, approvingUserID, approvingUserType, dtDecision from Appr_Items where itemID = ? and itemType = ? and ordinal = ( (select ordinal from Appr_Items where itemID = ? and itemType = ? and processingState = 'ToApprove' and decision is null) -1)");
            if (localResultSet.next())
            {
              str3 = localResultSet.getString(1);
              i3 = localResultSet.getInt(2);
              i4 = localResultSet.getInt(3);
              localObject4 = a(localResultSet.getTimestamp(4));
            }
            else
            {
              if (localPreparedStatement2 == null) {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select decision, approvingUserID, approvingUserType, dtDecision from Appr_Items where itemID = ? and itemType = ? and ( decision = 'Hold' or  decision = 'Rejected')");
              }
              localPreparedStatement2.clearParameters();
              localPreparedStatement2.setInt(1, i6);
              localPreparedStatement2.setInt(2, paramInt);
              DBUtil.closeResultSet(localResultSet);
              localResultSet = null;
              localResultSet = DBUtil.executeQuery(localPreparedStatement2, "select decision, approvingUserID, approvingUserType, dtDecision from Appr_Items where itemID = ? and itemType = ? and ( decision = 'Hold' or  decision = 'Rejected')");
              if (localResultSet.next())
              {
                str3 = localResultSet.getString(1).trim();
                i3 = localResultSet.getInt(2);
                i4 = localResultSet.getInt(3);
                localObject4 = a(localResultSet.getTimestamp(4));
              }
            }
            DBUtil.closeResultSet(localResultSet);
            localResultSet = null;
            if (localPreparedStatement3 == null) {
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "select groupOrUserID, userType, isUser from Appr_Items where itemID = ? and itemType = ? and ( processingState = 'ToApprove' OR decision = 'Rejected' )");
            }
            localPreparedStatement3.clearParameters();
            localPreparedStatement3.setInt(1, i6);
            localPreparedStatement3.setInt(2, paramInt);
            ApprovalsChainItem localApprovalsChainItem = null;
            localResultSet = DBUtil.executeQuery(localPreparedStatement3, "select groupOrUserID, userType, isUser from Appr_Items where itemID = ? and itemType = ? and ( processingState = 'ToApprove' OR decision = 'Rejected' )");
            if (localResultSet.next())
            {
              localApprovalsChainItem = new ApprovalsChainItem(Locale.getDefault());
              localApprovalsChainItem.setGroupOrUserID(localResultSet.getInt(1));
              localApprovalsChainItem.setUserType(localResultSet.getInt(2));
              localApprovalsChainItem.setUsingUser(localResultSet.getString(3).equalsIgnoreCase("Y"));
              localApprovalsChainItem.setIsApprovalsGroup(localResultSet.getString(3).equals("A"));
            }
            else
            {
              DBUtil.closeResultSet(localResultSet);
              localResultSet = null;
              continue;
            }
            if ((str3 != null) && (str3.equalsIgnoreCase("Rejected")))
            {
              localObject6 = null;
              if (localPreparedStatement4 == null) {
                localPreparedStatement4 = DBUtil.prepareStatement(localConnection, "SELECT propValue from Appr_ItemProps where itemID = ? and itemType = ? and propName = ?");
              }
              localPreparedStatement4.clearParameters();
              localPreparedStatement4.setInt(1, i6);
              localPreparedStatement4.setInt(2, paramInt);
              localPreparedStatement4.setString(3, "RejectReason");
              DBUtil.closeResultSet(localResultSet);
              localResultSet = null;
              localResultSet = DBUtil.executeQuery(localPreparedStatement4, "SELECT propValue from Appr_ItemProps where itemID = ? and itemType = ? and propName = ?");
              if (localResultSet.next()) {
                localObject6 = localResultSet.getString(1);
              }
              if (localObject6 != null) {
                ((ApprovalsItem)localObject3).setApprovalItemProperty("RejectReason", (String)localObject6);
              }
            }
            Object localObject6 = localApprovalsStatuses.add();
            ((ApprovalsStatus)localObject6).setApprovalItem((ApprovalsItem)localObject3);
            if ((str3 != null) && (str3.equalsIgnoreCase("Rejected"))) {
              ((ApprovalsStatus)localObject6).setProcessingState("Complete");
            } else {
              ((ApprovalsStatus)localObject6).setProcessingState("ToApprove");
            }
            ((ApprovalsStatus)localObject6).setDecision(str3);
            ((ApprovalsStatus)localObject6).setApprovingUserID(i3);
            ((ApprovalsStatus)localObject6).setApprovingUserType(i4);
            ((ApprovalsStatus)localObject6).setDecisionDate((com.ffusion.beans.DateTime)localObject4);
            ((ApprovalsStatus)localObject6).setCurrentChainItem(localApprovalsChainItem);
            DBUtil.closeResultSet(localResultSet);
            localResultSet = null;
          }
          if ((localApprovalsStatuses != null) && (localApprovalsStatuses.isEmpty())) {
            localApprovalsStatuses = null;
          }
        }
      }
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.closeStatement(localPreparedStatement4);
      localResultSet = null;
      localPreparedStatement1 = null;
      localPreparedStatement2 = null;
      localPreparedStatement3 = null;
      localPreparedStatement4 = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not obtain the submitted approval items", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Could not obtain the submitted approval items", localThrowable);
      }
      throw new ApprovalsException("Could not obtain the submitted approval items", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      if (localPreparedStatement1 != null) {
        DBUtil.closeStatement(localPreparedStatement1);
      }
      if (localPreparedStatement2 != null) {
        DBUtil.closeStatement(localPreparedStatement2);
      }
      if (localPreparedStatement3 != null) {
        DBUtil.closeStatement(localPreparedStatement3);
      }
      if (localPreparedStatement4 != null) {
        DBUtil.closeStatement(localPreparedStatement3);
      }
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsStatuses;
  }
  
  public int getNumberOfPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException
  {
    return getNumberOfPendingApprovals(paramInt1, paramInt2, paramInt3, null, paramHashMap);
  }
  
  public int getNumberOfPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.getNumberOfPendingApprovals";
    int i1 = 0;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      if ((paramHashMap1 == null) || ((paramHashMap1 != null) && (paramHashMap1.isEmpty())))
      {
        if (paramInt2 == 1)
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, a6);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localPreparedStatement.setInt(4, paramInt1);
          localPreparedStatement.setInt(5, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, a6);
        }
        else
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, aP);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, aP);
        }
      }
      else
      {
        String str2 = (String)paramHashMap1.get("StartDueDate");
        String str3 = (String)paramHashMap1.get("EndDueDate");
        StringBuffer localStringBuffer = new StringBuffer();
        if (paramInt2 == 1) {
          localStringBuffer.append(a0);
        } else {
          localStringBuffer.append(ab);
        }
        if ((str2 != null) && (str3 != null))
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" ( ");
          localStringBuffer.append(" dtDue >= ? ");
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" dtDue <= ? ");
          localStringBuffer.append(" ) ");
        }
        else if (str2 != null)
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" dtDue >= ? ");
        }
        else if (str3 != null)
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" dtDue <= ? ");
        }
        localStringBuffer.append(" order by itemType, itemID, ordinal");
        String str4 = localStringBuffer.toString();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str4);
        int i6 = 1;
        localPreparedStatement.setInt(i6++, paramInt1);
        localPreparedStatement.setInt(i6++, paramInt3);
        localPreparedStatement.setInt(i6++, paramInt1);
        if (paramInt2 == 1)
        {
          localPreparedStatement.setInt(i6++, paramInt1);
          localPreparedStatement.setInt(i6++, paramInt1);
        }
        Timestamp localTimestamp;
        if (str2 != null)
        {
          localTimestamp = new Timestamp(a(str2).getTime());
          localPreparedStatement.setTimestamp(i6++, localTimestamp);
        }
        if (str3 != null)
        {
          localTimestamp = new Timestamp(a(str3).getTime());
          localPreparedStatement.setTimestamp(i6++, localTimestamp);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str4);
      }
      int i2 = -1;
      int i3 = -1;
      while (localResultSet.next())
      {
        int i4 = localResultSet.getInt(1);
        int i5 = localResultSet.getInt(2);
        String str5 = localResultSet.getString(3);
        if (str5.trim().equalsIgnoreCase("Approved"))
        {
          i2 = i4;
          i3 = i5;
        }
        else if ((i3 != i5) || (i2 != i4))
        {
          i1++;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not obtain the number of pending approvals for this user", localThrowable);
      }
      throw new ApprovalsException("Could not obtain the number of pending approvals for this user", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return i1;
  }
  
  public ApprovalsItemCounts getNumberOfPendingApprovalsDetail(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.getNumberOfPendingApprovalsDetail";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      String str2;
      if (paramInt2 == 1) {
        str2 = a2;
      } else {
        str2 = a5;
      }
      int i1 = 1;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      localPreparedStatement.setInt(i1++, paramInt1);
      localPreparedStatement.setInt(i1++, paramInt3);
      if (paramInt2 == 1)
      {
        localPreparedStatement.setInt(i1++, paramInt1);
        localPreparedStatement.setInt(i1++, paramInt1);
      }
      localPreparedStatement.setInt(i1, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      ApprovalsItemCounts localApprovalsItemCounts1 = new ApprovalsItemCounts(Locale.getDefault());
      while (localResultSet.next())
      {
        int i2 = localResultSet.getInt(1);
        int i3 = localResultSet.getInt(2);
        int i4 = localResultSet.getInt(3);
        ApprovalsItemCount localApprovalsItemCount = localApprovalsItemCounts1.add();
        localApprovalsItemCount.setItemType(i2);
        localApprovalsItemCount.setItemSubType(i3);
        localApprovalsItemCount.setNumItems(i4);
      }
      if (localApprovalsItemCounts1.size() == 0)
      {
        localApprovalsItemCounts2 = null;
        return localApprovalsItemCounts2;
      }
      ApprovalsItemCounts localApprovalsItemCounts2 = localApprovalsItemCounts1;
      return localApprovalsItemCounts2;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not obtain the detailed number of pending approvals for this user", localThrowable);
      }
      throw new ApprovalsException("Could not obtain the detailed number of pending approvals for this user", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public ApprovalsItems getPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException
  {
    return a(paramInt1, paramInt2, paramInt3, 0, true, null, paramHashMap);
  }
  
  public ApprovalsItems getPendingApprovals(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    return a(paramInt1, paramInt2, paramInt3, 0, true, paramHashMap1, paramHashMap2);
  }
  
  public ApprovalsItems getPendingApprovalsForBank(int paramInt1, int paramInt2, int paramInt3, int paramInt4, HashMap paramHashMap)
    throws ApprovalsException
  {
    return a(paramInt1, paramInt2, paramInt3, paramInt4, true, null, paramHashMap);
  }
  
  public ApprovalsItems getConsumerPendingApprovalsForBank(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ApprovalsException
  {
    int i1 = paramSecureUser.getUserType();
    int i2 = paramSecureUser.getProfileID();
    int i3 = paramSecureUser.getEntitlementID();
    int i4 = paramSecureUser.getAffiliateIDValue();
    return a(i2, i1, i3, i4, false, null, paramHashMap);
  }
  
  public ApprovalsItem createApprovalItem(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, IApprovable paramIApprovable, String[] paramArrayOfString, HashMap paramHashMap)
    throws ApprovalsException
  {
    return createApprovalItem(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramInt3, paramIApprovable, paramArrayOfString, null, paramHashMap);
  }
  
  private Integer a(Connection paramConnection, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    Integer localInteger = null;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    String str = "select levelID from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType = ? AND minAmount <= ? AND maxAmount >= ?";
    if (paramString2 == null) {
      str = "select levelID from Appr_FlowLevels where businessID = ? AND objectType = ? AND levelType = ? AND operationType IS NULL AND minAmount <= ? AND maxAmount >= ?";
    }
    try
    {
      int i1 = 1;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
      localPreparedStatement.setInt(i1++, paramInt1);
      localPreparedStatement.setInt(i1++, paramInt2);
      localPreparedStatement.setString(i1++, paramString1);
      if (paramString2 != null) {
        localPreparedStatement.setString(i1++, paramString2);
      }
      localPreparedStatement.setBigDecimal(i1++, Profile.convertToBigDecimal(paramString3));
      localPreparedStatement.setBigDecimal(i1++, Profile.convertToBigDecimal(paramString3));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str);
      if (localResultSet.next()) {
        localInteger = new Integer(localResultSet.getInt(1));
      }
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return localInteger;
  }
  
  public ApprovalsItem createApprovalItem(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, IApprovable paramIApprovable, String[] paramArrayOfString1, String[] paramArrayOfString2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.createApprovalItem";
    ApprovalsItem localApprovalsItem = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    if ((paramArrayOfString1 == null) || (paramArrayOfString1.length == 0)) {
      throw new ApprovalsException(30103, "Could not submit the item for approval");
    }
    if ((paramHashMap == null) || ((paramHashMap != null) && (!paramHashMap.containsKey("ApprovalServicesPackageId")))) {
      throw new ApprovalsException(30151, "Could not submit the item for approval");
    }
    int i1 = ((Integer)paramHashMap.get("ApprovalServicesPackageId")).intValue();
    TWTransaction localTWTransaction1 = null;
    int i2 = -1;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      TWTransaction localTWTransaction2 = null;
      int i3 = -1;
      String str2 = null;
      Timestamp localTimestamp1 = null;
      Timestamp localTimestamp2 = null;
      String str3;
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      if (paramInt3 == 1)
      {
        localTWTransaction1 = this.d.addTransaction(paramInt2, paramString1, paramString2, paramIApprovable);
        i2 = localTWTransaction1.getID();
        Currency localCurrency = localTWTransaction1.getAmount();
        str3 = localCurrency.getCurrencyCode();
        String str4 = null;
        if (paramInt1 == 0) {
          str4 = (String)paramHashMap.get("ApprovalServicesPackageAffiliateBankCurrencyCode");
        } else {
          str4 = CurrencyUtil.getCurrencyCodeByBusinessId(paramInt1);
        }
        if (str4 == null) {
          str4 = Util.getLimitBaseCurrency();
        }
        if (!str3.equalsIgnoreCase(str4))
        {
          localObject1 = new SecureUser();
          ((SecureUser)localObject1).setProfileID(paramInt2);
          localObject2 = FXHandler.getClosestFXRate((SecureUser)localObject1, str3, str4, new com.ffusion.util.beans.DateTime(), 4, String.valueOf(paramInt2), new HashMap());
          if (localObject2 == null) {
            throw new ApprovalsException(30154, " The required foreign exchange rate does not exist ");
          }
          localObject3 = ((FXRate)localObject2).getBuyPrice().getAmountValue();
          localObject4 = localCurrency.getAmountValue().multiply((BigDecimal)localObject3);
          str2 = ((BigDecimal)localObject4).toString();
        }
        else
        {
          str2 = localCurrency.getAmountValue().toString();
        }
        i3 = localTWTransaction1.getTransactionType();
        localTWTransaction2 = localTWTransaction1;
        localTimestamp1 = new Timestamp(localTWTransaction1.getSubmissionDateTime().getTime().getTime());
        localTimestamp2 = new Timestamp(paramIApprovable.getApprovalDueDate().getTime().getTime());
      }
      else
      {
        throw new ApprovalsException(30075, "Could not submit the item for approval");
      }
      int i4 = 1;
      for (int i5 = 0; i5 < paramArrayOfString1.length; i5++)
      {
        if (paramArrayOfString2 == null) {
          str3 = null;
        } else {
          str3 = paramArrayOfString2[i5];
        }
        localObject1 = null;
        Object localObject5;
        if (paramArrayOfString1[i5].equals("Business_defined"))
        {
          if (paramInt1 == 0) {
            throw new ApprovalsException(30152, "Could not submit the item for approval");
          }
          localObject1 = a(localConnection, paramInt1, 1, paramArrayOfString1[i5], str3, str2);
          if ((localObject1 == null) && (str3 != null)) {
            localObject1 = a(localConnection, paramInt1, 1, paramArrayOfString1[i5], null, str2);
          }
        }
        else if (paramArrayOfString1[i5].equals("Bank_defined"))
        {
          if (paramInt1 == 0)
          {
            localObject1 = a(localConnection, i1, 2, paramArrayOfString1[i5], str3, str2);
            if ((localObject1 == null) && (str3 != null)) {
              localObject1 = a(localConnection, i1, 2, paramArrayOfString1[i5], null, str2);
            }
          }
          else
          {
            localObject1 = a(localConnection, paramInt1, 1, paramArrayOfString1[i5], str3, str2);
            if ((localObject1 == null) && (str3 != null)) {
              localObject1 = a(localConnection, paramInt1, 1, paramArrayOfString1[i5], null, str2);
            }
            if (localObject1 == null)
            {
              localObject2 = localTWTransaction1.getAmount();
              localObject3 = ((Currency)localObject2).getCurrencyCode();
              localObject4 = (String)paramHashMap.get("ApprovalServicesPackageAffiliateBankCurrencyCode");
              if (!((String)localObject3).equalsIgnoreCase((String)localObject4))
              {
                localObject5 = new SecureUser();
                ((SecureUser)localObject5).setProfileID(paramInt2);
                FXRate localFXRate = FXHandler.getClosestFXRate((SecureUser)localObject5, (String)localObject3, (String)localObject4, new com.ffusion.util.beans.DateTime(), 4, String.valueOf(paramInt2), new HashMap());
                BigDecimal localBigDecimal1 = localFXRate.getBuyPrice().getAmountValue();
                BigDecimal localBigDecimal2 = ((Currency)localObject2).getAmountValue().multiply(localBigDecimal1);
                str2 = localBigDecimal2.toString();
              }
              localObject1 = a(localConnection, i1, 2, paramArrayOfString1[i5], str3, str2);
              if ((localObject1 == null) && (str3 != null)) {
                localObject1 = a(localConnection, i1, 2, paramArrayOfString1[i5], null, str2);
              }
            }
          }
        }
        if (localObject1 != null)
        {
          if (localPreparedStatement1 == null) {
            localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select ordinal, groupOrUserID, userType, isUser from Appr_FlowChains where levelID = ? order by ordinal");
          }
          localPreparedStatement1.clearParameters();
          localPreparedStatement1.setInt(1, ((Integer)localObject1).intValue());
          localResultSet = DBUtil.executeQuery(localPreparedStatement1, "select ordinal, groupOrUserID, userType, isUser from Appr_FlowChains where levelID = ? order by ordinal");
          int i6 = 0;
          int i7 = -1;
          int i8 = -1;
          localObject5 = null;
          while (localResultSet.next())
          {
            i7 = localResultSet.getInt(2);
            i8 = localResultSet.getInt(3);
            localObject5 = localResultSet.getString(4);
            if ((!((String)localObject5).equals("Y")) || (i8 != 1) || (paramInt2 != i7))
            {
              if (localApprovalsItem == null)
              {
                localApprovalsItem = new ApprovalsItem(Locale.getDefault());
                localApprovalsItem.setItemID(i2);
                localApprovalsItem.setItemType(paramInt3);
                localApprovalsItem.setItemSubType(i3);
                localApprovalsItem.setSubmittingUserID(paramInt2);
                localApprovalsItem.setItem(localTWTransaction2);
                localApprovalsItem.setSubmissionDate(localTWTransaction1.getSubmissionDateTime());
                localApprovalsItem.setDueDate(localTWTransaction1.getTransaction().getApprovalDueDate());
              }
              if (localPreparedStatement2 == null) {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "insert into Appr_Items ( itemID, itemType, itemSubType, ordinal, businessID, groupOrUserID, userType,  isUser, submittingUserID, processingState, executionState, submittingUserName, dtSubmission, dtDue )  values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Passed', ?, ?, ? )");
              }
              localPreparedStatement2.clearParameters();
              localPreparedStatement2.setInt(1, i2);
              localPreparedStatement2.setInt(2, paramInt3);
              localPreparedStatement2.setInt(3, i3);
              localPreparedStatement2.setInt(4, i4);
              localPreparedStatement2.setInt(5, paramInt1);
              localPreparedStatement2.setInt(6, i7);
              localPreparedStatement2.setInt(7, i8);
              localPreparedStatement2.setString(8, (String)localObject5);
              localPreparedStatement2.setInt(9, paramInt2);
              localPreparedStatement2.setString(10, i4 == 1 ? "ToApprove" : "Waiting");
              localPreparedStatement2.setString(11, paramString3);
              localPreparedStatement2.setTimestamp(12, localTimestamp1);
              localPreparedStatement2.setTimestamp(13, localTimestamp2);
              int i9 = DBUtil.executeUpdate(localPreparedStatement2, "insert into Appr_Items ( itemID, itemType, itemSubType, ordinal, businessID, groupOrUserID, userType,  isUser, submittingUserID, processingState, executionState, submittingUserName, dtSubmission, dtDue )  values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Passed', ?, ?, ? )");
              if (i9 == 0) {
                throw new ApprovalsException(30001, "Could not submit the item for approval");
              }
              i6++;
              i4++;
            }
          }
          DBUtil.closeResultSet(localResultSet);
          DBUtil.closeStatement(localPreparedStatement1);
          DBUtil.closeStatement(localPreparedStatement2);
          localResultSet = null;
          localPreparedStatement1 = null;
          localPreparedStatement2 = null;
          if (i6 == 0)
          {
            if ((localObject5 != null) && (((String)localObject5).equals("Y")) && (i8 == 1) && (paramInt2 == i7)) {
              throw new ApprovalsException(30113, "Could not submit the item for approval");
            }
            throw new ApprovalsException(30057, "Could not submit the item for approval");
          }
        }
        else
        {
          throw new ApprovalsException(30076, "Could not submit the item for approval");
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException))
      {
        if ((paramInt3 == 1) && (localTWTransaction1 != null)) {
          try
          {
            this.d.deleteTransaction(i2);
          }
          catch (TWException localTWException1) {}
        }
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException))
      {
        if ((paramInt3 == 1) && (localTWTransaction1 != null)) {
          try
          {
            this.d.deleteTransaction(i2);
          }
          catch (TWException localTWException2) {}
        }
        throw new ApprovalsException(30011, "Could not submit the item for approval", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Could not submit the item for approval", localThrowable);
      }
      if ((paramInt3 == 1) && (localTWTransaction1 != null)) {
        try
        {
          this.d.deleteTransaction(i2);
        }
        catch (TWException localTWException3) {}
      }
      throw new ApprovalsException("Could not submit the item for approval", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsItem;
  }
  
  public ApprovalsItem modifyApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.modifyApprovalItem";
    if (paramApprovalsItem == null) {
      throw new ApprovalsException(30071, "Could not modify the approval item");
    }
    SecureUser localSecureUser = null;
    if (paramHashMap != null) {
      localSecureUser = (SecureUser)paramHashMap.get("ApprovalsUser");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      int i1 = paramApprovalsItem.getItemID();
      int i2 = paramApprovalsItem.getItemType();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      int i3 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
      if (i3 == 0) {
        throw new ApprovalsException(30060, "Could not modify the approval item");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_Items where itemID = ? AND itemType = ? AND processingState = 'Complete'");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_Items where itemID = ? AND itemType = ? AND processingState = 'Complete'");
      if ((localResultSet.next()) && (localResultSet.getInt(1) > 0)) {
        throw new ApprovalsException(30058, "Could not modify the approval item");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if (localSecureUser != null)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_Items set submittingUserID = ?where itemID = ? AND itemType = ?");
        localPreparedStatement.setInt(1, localSecureUser.getProfileID());
        localPreparedStatement.setInt(2, i1);
        localPreparedStatement.setInt(3, i2);
        i4 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_Items set submittingUserID = ?where itemID = ? AND itemType = ?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove', approvingUserID = NULL, approvingUserType = NULL, decision = NULL, dtDecision = NULL where itemID = ? AND itemType = ? AND ordinal = 1");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      int i4 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_Items set processingState = 'ToApprove', approvingUserID = NULL, approvingUserType = NULL, decision = NULL, dtDecision = NULL where itemID = ? AND itemType = ? AND ordinal = 1");
      if (i4 == 0) {
        throw new ApprovalsException(30063, "Could not modify the approval item");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if (i3 > 1)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Waiting', approvingUserID = NULL, approvingUserType = NULL, decision = NULL, dtDecision = NULL where itemID = ? AND itemType = ? AND ordinal <> 1");
        localPreparedStatement.setInt(1, i1);
        localPreparedStatement.setInt(2, i2);
        i4 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_Items set processingState = 'Waiting', approvingUserID = NULL, approvingUserType = NULL, decision = NULL, dtDecision = NULL where itemID = ? AND itemType = ? AND ordinal <> 1");
        if (i4 == 0) {
          throw new ApprovalsException(30063, "Could not modify the approval item");
        }
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
      }
      if (i2 == 1)
      {
        TWTransaction localTWTransaction = null;
        if (localSecureUser == null) {
          localTWTransaction = this.d.modifyTransaction(i1, ((TWTransaction)paramApprovalsItem.getItem()).getTransaction());
        } else {
          localTWTransaction = this.d.modifyTransaction(localSecureUser.getProfileID(), i1, ((TWTransaction)paramApprovalsItem.getItem()).getTransaction());
        }
        paramApprovalsItem.setItem(localTWTransaction);
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not modify the approval item", localThrowable);
      }
      throw new ApprovalsException("Could not modify the approval item", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return paramApprovalsItem;
  }
  
  public void removeApprovalItem(ApprovalsItem paramApprovalsItem, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.removeApprovalItem";
    if (paramApprovalsItem == null) {
      throw new ApprovalsException(30071, "Could not delete the approval item");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      int i1 = paramApprovalsItem.getItemID();
      int i2 = paramApprovalsItem.getItemType();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      int i3 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
      if (i3 == 0) {
        throw new ApprovalsException(30060, "Could not delete the approval item");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select itemID from Appr_Items where itemID = ? AND itemType = ? AND ( decision = 'Rejected' OR processingState = 'ToApprove' )");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select itemID from Appr_Items where itemID = ? AND itemType = ? AND ( decision = 'Rejected' OR processingState = 'ToApprove' )");
      if (!localResultSet.next()) {
        throw new ApprovalsException(30079, "Could not delete the approval item");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from Appr_Items where itemID = ? AND itemType = ?");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      int i4 = DBUtil.executeUpdate(localPreparedStatement, "delete from Appr_Items where itemID = ? AND itemType = ?");
      if (i4 == 0) {
        throw new ApprovalsException(30001, "Could not delete the approval item");
      }
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
      localPreparedStatement.setInt(1, i1);
      localPreparedStatement.setInt(2, i2);
      DBUtil.executeUpdate(localPreparedStatement, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      this.d.deleteTransaction(i1);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not delete the approval item", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Could not delete the approval item", localThrowable);
      }
      throw new ApprovalsException("Could not delete the approval item", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public ApprovalsItems submitDecisions(int paramInt1, int paramInt2, int paramInt3, ApprovalsDecisions paramApprovalsDecisions, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.submitDecisions";
    if (paramApprovalsDecisions == null) {
      throw new ApprovalsException(30073, "Submitting the approval decisions failed");
    }
    ApprovalsItems localApprovalsItems = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    PreparedStatement localPreparedStatement4 = null;
    PreparedStatement localPreparedStatement5 = null;
    PreparedStatement localPreparedStatement6 = null;
    PreparedStatement localPreparedStatement7 = null;
    PreparedStatement localPreparedStatement8 = null;
    PreparedStatement localPreparedStatement9 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      paramApprovalsDecisions.setSortedBy("ITEMID");
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      Iterator localIterator = paramApprovalsDecisions.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsDecision localApprovalsDecision = (ApprovalsDecision)localIterator.next();
        ApprovalsItem localApprovalsItem = localApprovalsDecision.getApprovalItem();
        int i1 = localApprovalsItem.getItemID();
        int i2 = localApprovalsItem.getItemType();
        if (localPreparedStatement1 == null) {
          localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        }
        localPreparedStatement1.clearParameters();
        localPreparedStatement1.setInt(1, i1);
        localPreparedStatement1.setInt(2, i2);
        int i3 = DBUtil.executeUpdate(localPreparedStatement1, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        if (i3 == 0)
        {
          paramHashMap.put(localApprovalsItem, new Integer(30060));
          throw new ApprovalsException(30060, "Submitting the approval decisions failed");
        }
        if (localPreparedStatement7 == null) {
          localPreparedStatement7 = DBUtil.prepareStatement(localConnection, "SELECT ordinal FROM Appr_Items WHERE itemID = ? AND itemType = ? AND approvingUserID = ? AND approvingUserType = ? AND processingState != 'ToApprove'");
        }
        localPreparedStatement7.clearParameters();
        localPreparedStatement7.setInt(1, i1);
        localPreparedStatement7.setInt(2, i2);
        localPreparedStatement7.setInt(3, paramInt1);
        localPreparedStatement7.setInt(4, paramInt2);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement7, "SELECT ordinal FROM Appr_Items WHERE itemID = ? AND itemType = ? AND approvingUserID = ? AND approvingUserType = ? AND processingState != 'ToApprove'");
        if (localResultSet1.next())
        {
          paramHashMap.put(localApprovalsItem, new Integer(30120));
          throw new ApprovalsException(30120, "Submitting the approval decisions failed");
        }
        DBUtil.closeResultSet(localResultSet1);
        localResultSet1 = null;
        if (localPreparedStatement2 == null) {
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal, groupOrUserID, isUser, userType, decision, submittingUserID from Appr_Items where itemID = ? AND itemType = ? AND processingState = 'ToApprove'");
        }
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setInt(1, i1);
        localPreparedStatement2.setInt(2, i2);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal, groupOrUserID, isUser, userType, decision, submittingUserID from Appr_Items where itemID = ? AND itemType = ? AND processingState = 'ToApprove'");
        int i4 = 0;
        while (localResultSet1.next())
        {
          i4++;
          if (i4 > 1)
          {
            paramHashMap.put(localApprovalsItem, new Integer(30108));
            throw new ApprovalsException(30108, "Submitting the approval decisions failed");
          }
          int i5 = localResultSet1.getInt(1);
          int i6 = localResultSet1.getInt(2);
          String str2 = localResultSet1.getString(3);
          int i7 = localResultSet1.getInt(4);
          String str3 = localResultSet1.getString(5);
          int i8 = localResultSet1.getInt(6);
          if (str2.equals("Y"))
          {
            if ((i6 != paramInt1) || (i7 != paramInt2))
            {
              paramHashMap.put(localApprovalsItem, new Integer(30061));
              throw new ApprovalsException(30061, "Submitting the approval decisions failed");
            }
          }
          else if (str2.equals("N"))
          {
            if ((paramInt1 == i8) && (paramInt2 == 1))
            {
              paramHashMap.put(localApprovalsItem, new Integer(30114));
              throw new ApprovalsException(30114, "Submitting the approval decisions failed");
            }
            if (i6 != paramInt3)
            {
              paramHashMap.put(localApprovalsItem, new Integer(30062));
              throw new ApprovalsException(30062, "Submitting the approval decisions failed");
            }
          }
          else if (str2.equals("A"))
          {
            if ((paramInt1 == i8) && (paramInt2 == 1)) {
              throw new ApprovalsException(30114, "Submitting the approval decisions failed");
            }
            localPreparedStatement9 = DBUtil.prepareStatement(localConnection, "select userID from Appr_GroupMembers where apprGroupID = ? AND userID = ? ");
            localPreparedStatement9.setInt(1, i6);
            localPreparedStatement9.setInt(2, paramInt1);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement9, "select userID from Appr_GroupMembers where apprGroupID = ? AND userID = ? ");
            if (!localResultSet2.next())
            {
              paramHashMap.put(localApprovalsItem, new Integer(30133));
              throw new ApprovalsException(30133, "Submitting the approval decisions failed");
            }
          }
          DBUtil.closeResultSet(localResultSet2);
          localResultSet2 = null;
          if (i2 == 1)
          {
            String str4 = localApprovalsDecision.getDecision();
            String str5 = null;
            if ((str3 == null) || (str3.equals("Hold")))
            {
              if ((str4.equals("Approved")) || (str4.equals("Release")) || (str4.equals("Rejected")))
              {
                str5 = "Approved";
              }
              else if (str4.equals("Hold"))
              {
                str5 = "ToApprove";
              }
              else
              {
                paramHashMap.put(localApprovalsItem, new Integer(30107));
                throw new ApprovalsException(30107, "Submitting the approval decisions failed");
              }
            }
            else if ((str3.equals("Approved")) || (str3.equals("Rejected")))
            {
              paramHashMap.put(localApprovalsItem, new Integer(30106));
              throw new ApprovalsException(30106, "Submitting the approval decisions failed");
            }
            if (localPreparedStatement3 == null) {
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_Items set approvingUserID = ?, approvingUserType = ?, decision = ?, dtDecision = ?, processingState = ? where itemID = ? AND itemType = ? AND ordinal = ? AND processingState = 'ToApprove'");
            }
            localPreparedStatement3.clearParameters();
            localPreparedStatement3.setInt(1, paramInt1);
            localPreparedStatement3.setInt(2, paramInt2);
            localPreparedStatement3.setString(3, str4);
            localPreparedStatement3.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            localPreparedStatement3.setString(5, str5);
            localPreparedStatement3.setInt(6, i1);
            localPreparedStatement3.setInt(7, i2);
            localPreparedStatement3.setInt(8, i5);
            int i9 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_Items set approvingUserID = ?, approvingUserType = ?, decision = ?, dtDecision = ?, processingState = ? where itemID = ? AND itemType = ? AND ordinal = ? AND processingState = 'ToApprove'");
            if (i9 == 0)
            {
              paramHashMap.put(localApprovalsItem, new Integer(30063));
              throw new ApprovalsException(30063, "Submitting the approval decisions failed");
            }
            if (str4.equals("Rejected"))
            {
              if (localPreparedStatement4 == null) {
                localPreparedStatement4 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
              }
              localPreparedStatement4.clearParameters();
              localPreparedStatement4.setInt(1, i1);
              localPreparedStatement4.setInt(2, i2);
              i9 = DBUtil.executeUpdate(localPreparedStatement4, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
              if (i9 == 0)
              {
                paramHashMap.put(localApprovalsItem, new Integer(30063));
                throw new ApprovalsException(30063, "Submitting the approval decisions failed");
              }
              String str6 = localApprovalsItem.getApprovalItemProperty("RejectReason");
              if ((str6 != null) && (str6.trim().length() > 0))
              {
                if (localPreparedStatement8 == null) {
                  localPreparedStatement8 = DBUtil.prepareStatement(localConnection, "INSERT into Appr_ItemProps ( itemID, itemType, propName, propValue ) VALUES( ?, ?, ?, ?)");
                }
                localPreparedStatement8.clearParameters();
                localPreparedStatement8.setInt(1, i1);
                localPreparedStatement8.setInt(2, i2);
                localPreparedStatement8.setString(3, "RejectReason");
                localPreparedStatement8.setString(4, str6);
                DBUtil.executeUpdate(localPreparedStatement8, "INSERT into Appr_ItemProps ( itemID, itemType, propName, propValue ) VALUES( ?, ?, ?, ?)");
              }
            }
            else if ((str4.equals("Approved")) || (str4.equals("Release")))
            {
              localPreparedStatement5 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
              localPreparedStatement5.clearParameters();
              localPreparedStatement5.setInt(1, i1);
              localPreparedStatement5.setInt(2, i2);
              localPreparedStatement5.setInt(3, i5 + 1);
              int i10 = DBUtil.executeUpdate(localPreparedStatement5, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
              if (i10 == 0)
              {
                if (localPreparedStatement6 == null) {
                  localPreparedStatement6 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                }
                localPreparedStatement6.clearParameters();
                localPreparedStatement6.setInt(1, i1);
                localPreparedStatement6.setInt(2, i2);
                i10 = DBUtil.executeUpdate(localPreparedStatement6, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                if (i10 == 0)
                {
                  paramHashMap.put(localApprovalsItem, new Integer(30063));
                  throw new ApprovalsException(30063, "Submitting the approval decisions failed");
                }
                if (localApprovalsItems == null) {
                  localApprovalsItems = new ApprovalsItems(Locale.getDefault());
                }
                localApprovalsItems.add(localApprovalsItem);
              }
            }
          }
        }
        DBUtil.closeResultSet(localResultSet1);
        localResultSet1 = null;
        if (i4 == 0)
        {
          paramHashMap.put(localApprovalsItem, new Integer(30058));
          throw new ApprovalsException(30058, "Submitting the approval decisions failed");
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.closeStatement(localPreparedStatement4);
      DBUtil.closeStatement(localPreparedStatement5);
      DBUtil.closeStatement(localPreparedStatement6);
      DBUtil.closeStatement(localPreparedStatement7);
      DBUtil.closeStatement(localPreparedStatement8);
      DBUtil.closeStatement(localPreparedStatement9);
      localPreparedStatement1 = null;
      localPreparedStatement2 = null;
      localPreparedStatement3 = null;
      localPreparedStatement4 = null;
      localPreparedStatement5 = null;
      localPreparedStatement6 = null;
      localPreparedStatement7 = null;
      localPreparedStatement8 = null;
      localPreparedStatement9 = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Submitting the approval decisions failed", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Submitting the approval decisions failed", localThrowable);
      }
      throw new ApprovalsException("Submitting the approval decisions failed", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeResultSet(localResultSet2);
      if (localPreparedStatement1 != null) {
        DBUtil.closeStatement(localPreparedStatement1);
      }
      if (localPreparedStatement2 != null) {
        DBUtil.closeStatement(localPreparedStatement2);
      }
      if (localPreparedStatement3 != null) {
        DBUtil.closeStatement(localPreparedStatement3);
      }
      if (localPreparedStatement4 != null) {
        DBUtil.closeStatement(localPreparedStatement4);
      }
      if (localPreparedStatement5 != null) {
        DBUtil.closeStatement(localPreparedStatement5);
      }
      if (localPreparedStatement6 != null) {
        DBUtil.closeStatement(localPreparedStatement6);
      }
      if (localPreparedStatement7 != null) {
        DBUtil.closeStatement(localPreparedStatement7);
      }
      if (localPreparedStatement8 != null) {
        DBUtil.closeStatement(localPreparedStatement8);
      }
      if (localPreparedStatement9 != null) {
        DBUtil.closeStatement(localPreparedStatement9);
      }
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsItems;
  }
  
  public void removeBusiness(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.removeBusiness";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowLevels where businessID = ? and objectType=1");
      localPreparedStatement1.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement1, "delete from Appr_FlowLevels where businessID = ? and objectType=1");
      DBUtil.closeStatement(localPreparedStatement1);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "delete from Appr_Groups where businessID = ?");
      localPreparedStatement1.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement1, "delete from Appr_Groups where businessID = ?");
      DBUtil.closeStatement(localPreparedStatement1);
      localPreparedStatement1 = null;
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, aF);
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, aF);
      int i1 = 100;
      int i2 = 0;
      Object localObject1 = new int[i1];
      while (localResultSet.next())
      {
        if (i2 == i1)
        {
          i1 *= 2;
          int[] arrayOfInt1 = new int[i1];
          System.arraycopy(localObject1, 0, arrayOfInt1, 0, i2);
          localObject1 = arrayOfInt1;
        }
        int i3 = localResultSet.getInt(1);
        localObject1[i2] = i3;
        i2++;
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet);
      localResultSet = null;
      localPreparedStatement1 = null;
      if (i2 == 0) {
        return;
      }
      int[] arrayOfInt2 = new int[i2];
      System.arraycopy(localObject1, 0, arrayOfInt2, 0, i2);
      int i4 = arrayOfInt2.length;
      Arrays.sort(arrayOfInt2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, jdField_new);
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
      for (int i5 = 0; i5 < i4; i5++)
      {
        localPreparedStatement1.clearParameters();
        localPreparedStatement1.setInt(1, arrayOfInt2[i5]);
        DBUtil.executeUpdate(localPreparedStatement1, jdField_new);
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setInt(1, arrayOfInt2[i5]);
        localPreparedStatement2.setInt(2, 1);
        DBUtil.executeUpdate(localPreparedStatement2, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
      }
      this.d.deleteTransactions(arrayOfInt2);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not remove the business from the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not remove the business from the approval system", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public void removeObject(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException
  {
    if (paramInt2 == 1)
    {
      removeBusiness(paramInt1, paramHashMap);
      return;
    }
    String str = "ApprovalsAdapter.removeObject";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramInt2 == 2)
      {
        localConnection = DBUtil.getConnection(this.R, true, 2);
        DBUtil.beginTransaction(localConnection);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowLevels where businessID = ? and objectType = ?");
        localPreparedStatement.setInt(1, paramInt1);
        localPreparedStatement.setInt(2, paramInt2);
        DBUtil.executeUpdate(localPreparedStatement, "delete from Appr_FlowLevels where businessID = ? and objectType = ?");
        DBUtil.commit(localConnection);
      }
      else
      {
        throw new ApprovalsException(30150, "Could not remove the object from the approval system");
      }
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not remove the object from the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not remove the object from the approval system", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public boolean isUserInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.isUserInUse";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowChains where groupOrUserID = ? AND userType = ? AND isUser = 'Y'");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowChains where groupOrUserID = ? AND userType = ? AND isUser = 'Y'");
      int i1 = 0;
      boolean bool;
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_Items where groupOrUserID = ? AND userType = ? AND isUser = 'Y' AND processingState <>'Complete'");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_Items where groupOrUserID = ? AND userType = ? AND isUser = 'Y' AND processingState <>'Complete'");
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if (paramInt2 == 1)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_GroupMembers where userID = ?");
        localPreparedStatement.setInt(1, paramInt1);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_GroupMembers where userID = ?");
        if (localResultSet.next())
        {
          i1 = localResultSet.getInt(1);
          if (i1 > 0)
          {
            bool = true;
            return bool;
          }
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not determine if the apecified user is being used by the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not determine if the apecified user is being used by the approval system", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return false;
  }
  
  public ApprovalsItems removeUser(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.removeUser";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ApprovalsItems localApprovalsItems = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select levelID, ordinal from Appr_FlowChains where groupOrUserID = ? AND userType = ? AND isUser = 'Y' order by levelID");
      localPreparedStatement1.setInt(1, paramInt1);
      localPreparedStatement1.setInt(2, paramInt2);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select levelID, ordinal from Appr_FlowChains where groupOrUserID = ? AND userType = ? AND isUser = 'Y' order by levelID");
      int i1;
      int i2;
      int i3;
      int i4;
      while (localResultSet1.next())
      {
        i1 = localResultSet1.getInt(1);
        i2 = localResultSet1.getInt(2);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
        localPreparedStatement2.setInt(1, i1);
        i3 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
        if (i3 != 0)
        {
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND userType = ? AND isUser = 'Y'");
          localPreparedStatement2.setInt(1, i1);
          localPreparedStatement2.setInt(2, i2);
          localPreparedStatement2.setInt(3, paramInt1);
          localPreparedStatement2.setInt(4, paramInt2);
          i3 = DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND userType = ? AND isUser = 'Y'");
          if (i3 != 0)
          {
            DBUtil.closeStatement(localPreparedStatement2);
            localPreparedStatement2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal");
            localPreparedStatement2.setInt(1, i1);
            localPreparedStatement2.setInt(2, i2);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal");
            while (localResultSet2.next())
            {
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?");
              i4 = localResultSet2.getInt(1);
              localPreparedStatement3.setInt(1, i4 - 1);
              localPreparedStatement3.setInt(2, i1);
              localPreparedStatement3.setInt(3, i4);
              i3 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?");
              if (i3 == 0) {
                throw new ApprovalsException(30065, "Could not remove the user from the approval system");
              }
              DBUtil.closeStatement(localPreparedStatement3);
              localPreparedStatement3 = null;
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localResultSet2 = null;
            localPreparedStatement2 = null;
          }
        }
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_GroupMembers where userID = ? ");
      localPreparedStatement2.setInt(1, paramInt1);
      DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_GroupMembers where userID = ? ");
      DBUtil.closeStatement(localPreparedStatement2);
      localPreparedStatement2 = null;
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND userType = ? AND isUser = 'Y' AND processingState <> 'Complete' order by itemType, itemID");
      localPreparedStatement1.setInt(1, paramInt1);
      localPreparedStatement1.setInt(2, paramInt2);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND userType = ? AND isUser = 'Y' AND processingState <> 'Complete' order by itemType, itemID");
      while (localResultSet1.next())
      {
        i1 = localResultSet1.getInt(1);
        i2 = localResultSet1.getInt(2);
        i3 = localResultSet1.getInt(3);
        i4 = localResultSet1.getInt(4);
        int i5 = localResultSet1.getInt(5);
        String str2 = localResultSet1.getString(6).trim();
        Timestamp localTimestamp1 = localResultSet1.getTimestamp(7);
        Timestamp localTimestamp2 = localResultSet1.getTimestamp(8);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        localPreparedStatement2.setInt(1, i1);
        localPreparedStatement2.setInt(2, i2);
        int i6 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        if (i6 != 0)
        {
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND userType = ? AND isUser = 'Y'");
          localPreparedStatement2.setInt(1, i1);
          localPreparedStatement2.setInt(2, i2);
          localPreparedStatement2.setInt(3, i3);
          localPreparedStatement2.setInt(4, paramInt1);
          localPreparedStatement2.setInt(5, paramInt2);
          int i7 = DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND userType = ? AND isUser = 'Y'");
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          if (i7 != 0)
          {
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
            localPreparedStatement2.setInt(1, i1);
            localPreparedStatement2.setInt(2, i2);
            DBUtil.executeUpdate(localPreparedStatement2, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
            DBUtil.closeStatement(localPreparedStatement2);
            localPreparedStatement2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal");
            localPreparedStatement2.setInt(1, i1);
            localPreparedStatement2.setInt(2, i2);
            localPreparedStatement2.setInt(3, i3);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal");
            int i8 = 0;
            while (localResultSet2.next())
            {
              i8++;
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?");
              int i9 = localResultSet2.getInt(1);
              localPreparedStatement3.setInt(1, i9 - 1);
              localPreparedStatement3.setInt(2, i1);
              localPreparedStatement3.setInt(3, i2);
              localPreparedStatement3.setInt(4, i9);
              i7 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?");
              if (i7 == 0) {
                throw new ApprovalsException(30065, "Could not remove the user from the approval system");
              }
              DBUtil.closeStatement(localPreparedStatement3);
              localPreparedStatement3 = null;
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localResultSet2 = null;
            localPreparedStatement2 = null;
            if (str2.equals("ToApprove")) {
              if (i8 == 0)
              {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                localPreparedStatement2.setInt(1, i1);
                localPreparedStatement2.setInt(2, i2);
                DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                DBUtil.closeStatement(localPreparedStatement2);
                localPreparedStatement2 = null;
                if (localApprovalsItems == null) {
                  localApprovalsItems = new ApprovalsItems(Locale.getDefault());
                }
                ApprovalsItem localApprovalsItem = localApprovalsItems.add();
                localApprovalsItem.setItemID(i1);
                localApprovalsItem.setItemType(i2);
                localApprovalsItem.setItemSubType(i4);
                localApprovalsItem.setSubmittingUserID(i5);
                if (localTimestamp1 != null) {
                  localApprovalsItem.setSubmissionDate(a(localTimestamp1));
                }
                if (localTimestamp2 != null) {
                  localApprovalsItem.setDueDate(a(localTimestamp2));
                }
                if (i2 == 1)
                {
                  TWTransaction localTWTransaction = this.d.getTransaction(i1);
                  localApprovalsItem.setItem(localTWTransaction);
                }
              }
              else
              {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
                localPreparedStatement2.setInt(1, i1);
                localPreparedStatement2.setInt(2, i2);
                localPreparedStatement2.setInt(3, i3);
                i7 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
                if (i7 == 0) {
                  throw new ApprovalsException(30063, "Could not remove the user from the approval system");
                }
                DBUtil.closeStatement(localPreparedStatement2);
                localPreparedStatement2 = null;
              }
            }
          }
        }
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not remove the user from the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not remove the user from the approval system", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeResultSet(localResultSet2);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsItems;
  }
  
  public boolean isGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.IsGroupInUse";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowChains where groupOrUserID = ? AND isUser = 'N'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowChains where groupOrUserID = ? AND isUser = 'N'");
      int i1 = 0;
      boolean bool;
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_Items where groupOrUserID = ? AND isUser = 'N' AND processingState <> 'Complete'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_Items where groupOrUserID = ? AND isUser = 'N' AND processingState <> 'Complete'");
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not determine if the specified group is being used by the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not determine if the specified group is being used by the approval system", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return false;
  }
  
  public boolean isBusinessInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.IsGroupInUse";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType=1");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType=1");
      int i1 = 0;
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool1 = true;
          return bool1;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_Items where businessID = ? AND processingState <> 'Complete'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_Items where businessID = ? AND processingState <> 'Complete'");
      boolean bool1 = false;
      if (localResultSet.next())
      {
        int i2 = localResultSet.getInt(1);
        if (i2 > 0)
        {
          boolean bool2 = true;
          return bool2;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not determine if the specified business is being used by the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not determine if the specified business is being used by the approval system", localThrowable);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return false;
  }
  
  public boolean isObjectInUse(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ApprovalsException
  {
    if (paramInt2 == 1) {
      return isBusinessInUse(paramInt1, paramHashMap);
    }
    String str = "ApprovalsAdapter.IsObjectInUse";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (paramInt2 == 2)
      {
        localConnection = DBUtil.getConnection(this.R, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? ");
        localPreparedStatement.setInt(1, paramInt1);
        localPreparedStatement.setInt(2, paramInt2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowLevels where businessID = ? AND objectType = ? ");
        int i1 = 0;
        if (localResultSet.next())
        {
          i1 = localResultSet.getInt(1);
          if (i1 > 0)
          {
            boolean bool = true;
            return bool;
          }
        }
      }
      else
      {
        throw new ApprovalsException(30150, "Could not remove the object from the approval system");
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not determine if the specified object is being used by the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not determine if the specified object is being used by the approval system", localThrowable);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return false;
  }
  
  public ApprovalsItems removeGroup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.removeGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ApprovalsItems localApprovalsItems = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select levelID, ordinal from Appr_FlowChains where groupOrUserID = ? AND isUser = 'N' order by levelID");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select levelID, ordinal from Appr_FlowChains where groupOrUserID = ? AND isUser = 'N' order by levelID");
      int i1;
      int i2;
      int i3;
      int i4;
      while (localResultSet1.next())
      {
        i1 = localResultSet1.getInt(1);
        i2 = localResultSet1.getInt(2);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
        localPreparedStatement2.setInt(1, i1);
        i3 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
        if (i3 != 0)
        {
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'N'");
          localPreparedStatement2.setInt(1, i1);
          localPreparedStatement2.setInt(2, i2);
          localPreparedStatement2.setInt(3, paramInt);
          i3 = DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'N'");
          if (i3 != 0)
          {
            DBUtil.closeStatement(localPreparedStatement2);
            localPreparedStatement2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal");
            localPreparedStatement2.setInt(1, i1);
            localPreparedStatement2.setInt(2, i2);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal");
            while (localResultSet2.next())
            {
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?");
              i4 = localResultSet2.getInt(1);
              localPreparedStatement3.setInt(1, i4 - 1);
              localPreparedStatement3.setInt(2, i1);
              localPreparedStatement3.setInt(3, i4);
              i3 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?");
              if (i3 == 0) {
                throw new ApprovalsException(30065, "Could not remove the group from the approval system");
              }
              DBUtil.closeStatement(localPreparedStatement3);
              localPreparedStatement3 = null;
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localResultSet2 = null;
            localPreparedStatement2 = null;
          }
        }
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND isUser = 'N' AND processingState <> 'Complete' order by itemType, itemID");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND isUser = 'N' AND processingState <> 'Complete' order by itemType, itemID");
      while (localResultSet1.next())
      {
        i1 = localResultSet1.getInt(1);
        i2 = localResultSet1.getInt(2);
        i3 = localResultSet1.getInt(3);
        i4 = localResultSet1.getInt(4);
        int i5 = localResultSet1.getInt(5);
        String str2 = localResultSet1.getString(6).trim();
        Timestamp localTimestamp1 = localResultSet1.getTimestamp(7);
        Timestamp localTimestamp2 = localResultSet1.getTimestamp(8);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        localPreparedStatement2.setInt(1, i1);
        localPreparedStatement2.setInt(2, i2);
        int i6 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        if (i6 != 0)
        {
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'N'");
          localPreparedStatement2.setInt(1, i1);
          localPreparedStatement2.setInt(2, i2);
          localPreparedStatement2.setInt(3, i3);
          localPreparedStatement2.setInt(4, paramInt);
          int i7 = DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'N'");
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          if (i7 != 0)
          {
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
            localPreparedStatement2.setInt(1, i1);
            localPreparedStatement2.setInt(2, i2);
            DBUtil.executeUpdate(localPreparedStatement2, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
            DBUtil.closeStatement(localPreparedStatement2);
            localPreparedStatement2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal");
            localPreparedStatement2.setInt(1, i1);
            localPreparedStatement2.setInt(2, i2);
            localPreparedStatement2.setInt(3, i3);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal");
            int i8 = 0;
            while (localResultSet2.next())
            {
              i8++;
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?");
              int i9 = localResultSet2.getInt(1);
              localPreparedStatement3.setInt(1, i9 - 1);
              localPreparedStatement3.setInt(2, i1);
              localPreparedStatement3.setInt(3, i2);
              localPreparedStatement3.setInt(4, i9);
              i7 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?");
              if (i7 == 0) {
                throw new ApprovalsException(30065, "Could not remove the group from the approval system");
              }
              DBUtil.closeStatement(localPreparedStatement3);
              localPreparedStatement3 = null;
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localResultSet2 = null;
            localPreparedStatement2 = null;
            if (str2.equals("ToApprove")) {
              if (i8 == 0)
              {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                localPreparedStatement2.setInt(1, i1);
                localPreparedStatement2.setInt(2, i2);
                DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                DBUtil.closeStatement(localPreparedStatement2);
                localPreparedStatement2 = null;
                if (localApprovalsItems == null) {
                  localApprovalsItems = new ApprovalsItems(Locale.getDefault());
                }
                ApprovalsItem localApprovalsItem = localApprovalsItems.add();
                localApprovalsItem.setItemID(i1);
                localApprovalsItem.setItemType(i2);
                localApprovalsItem.setItemSubType(i4);
                localApprovalsItem.setSubmittingUserID(i5);
                if (localTimestamp1 != null) {
                  localApprovalsItem.setSubmissionDate(a(localTimestamp1));
                }
                if (localTimestamp2 != null) {
                  localApprovalsItem.setDueDate(a(localTimestamp2));
                }
                if (i2 == 1)
                {
                  TWTransaction localTWTransaction = this.d.getTransaction(i1);
                  localApprovalsItem.setItem(localTWTransaction);
                }
              }
              else
              {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
                localPreparedStatement2.setInt(1, i1);
                localPreparedStatement2.setInt(2, i2);
                localPreparedStatement2.setInt(3, i3);
                i7 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
                if (i7 == 0) {
                  throw new ApprovalsException(30063, "Could not remove the group from the approval system");
                }
                DBUtil.closeStatement(localPreparedStatement2);
                localPreparedStatement2 = null;
              }
            }
            DBUtil.closeAll(localPreparedStatement1, localResultSet1);
            localResultSet1 = null;
            localPreparedStatement1 = null;
          }
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not remove the group from the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not remove the group from the approval system", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeResultSet(localResultSet2);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsItems;
  }
  
  public ApprovalsHistory getItemHistory(int paramInt, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.getItemHistory";
    Locale localLocale = (Locale)paramHashMap2.get("CURRENT_LOCALE");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if (paramInt == 1)
    {
      String str2 = (String)paramHashMap1.get("ObjectID");
      if (str2 == null) {
        throw new ApprovalsException(30116, "The object ID must be provided in the search criteria hashmap in a call to GetItemHistory()");
      }
      Integer localInteger = (Integer)paramHashMap1.get("ItemSubType");
      TWTransaction localTWTransaction = null;
      try
      {
        if (localInteger == null) {
          localTWTransaction = this.d.getTransaction(str2);
        } else {
          localTWTransaction = this.d.getTransaction(localInteger.intValue(), str2);
        }
      }
      catch (TWException localTWException)
      {
        throw new ApprovalsException(30013, "A transaction warehouse related error has occurred", localTWException);
      }
      if (localTWTransaction == null) {
        return null;
      }
      int i1 = localTWTransaction.getTransactionType();
      ApprovalsItem localApprovalsItem = new ApprovalsItem(localLocale);
      localApprovalsItem.setItem(localTWTransaction);
      localApprovalsItem.setItemID(localTWTransaction.getID());
      localApprovalsItem.setItemType(1);
      localApprovalsItem.setItemSubType(i1);
      localApprovalsItem.setSubmittingUserID(localTWTransaction.getUserID());
      localApprovalsItem.setSubmittingUserName(localTWTransaction.getUserName());
      localApprovalsItem.setSubmissionDate(localTWTransaction.getSubmissionDateTime());
      localApprovalsItem.setDueDate(localTWTransaction.getTransaction().getApprovalDueDate());
      Connection localConnection = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      ApprovalsHistoryRecords localApprovalsHistoryRecords = new ApprovalsHistoryRecords(localLocale);
      try
      {
        localConnection = DBUtil.getConnection(this.R, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT approvingUserID, approvingUserType, decision, dtDecision, processingState FROM Appr_Items WHERE itemID = ? AND itemType = ? ORDER BY ordinal");
        localPreparedStatement.setInt(1, localApprovalsItem.getItemID());
        localPreparedStatement.setInt(2, localApprovalsItem.getItemType());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT approvingUserID, approvingUserType, decision, dtDecision, processingState FROM Appr_Items WHERE itemID = ? AND itemType = ? ORDER BY ordinal");
        int i2 = 0;
        ApprovalsHistoryRecord localApprovalsHistoryRecord;
        while (localResultSet.next())
        {
          i2++;
          localApprovalsHistoryRecord = localApprovalsHistoryRecords.add();
          localApprovalsHistoryRecord.setApprovingUserID(localResultSet.getInt(1));
          localApprovalsHistoryRecord.setApprovingUserType(localResultSet.getInt(2));
          localApprovalsHistoryRecord.setDecision(localResultSet.getString(3));
          Timestamp localTimestamp = localResultSet.getTimestamp(4);
          if (localTimestamp == null) {
            localApprovalsHistoryRecord.setDecisionDate(null);
          } else {
            localApprovalsHistoryRecord.setDecisionDate(a(localTimestamp));
          }
          localApprovalsHistoryRecord.setProcessingState(localResultSet.getString(5).trim());
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
        if (i2 == 0)
        {
          localApprovalsHistoryRecord = null;
          return localApprovalsHistoryRecord;
        }
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing(str1, localThrowable);
        if ((localThrowable instanceof ApprovalsException)) {
          throw ((ApprovalsException)localThrowable);
        }
        if ((localThrowable instanceof SQLException)) {
          throw new ApprovalsException(30011, "Unable to retrieve history for the given item.", localThrowable);
        }
        throw new ApprovalsException("Unable to retrieve history for the given item.", localThrowable);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.returnConnection(this.R, localConnection);
      }
      ApprovalsHistory localApprovalsHistory = new ApprovalsHistory(localLocale);
      localApprovalsHistory.setApprovalItem(localApprovalsItem);
      localApprovalsHistory.setHistoryRecords(localApprovalsHistoryRecords);
      return localApprovalsHistory;
    }
    throw new ApprovalsException(30118, "The item type used in a call to GetItemHistory() is not currently supported for history retrieval.");
  }
  
  public boolean isUserApprover(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.isUserApprover";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowChains where ( groupOrUserID = ? AND userType = ? AND isUser = 'Y') OR ( groupOrUserID = ? AND isUser = 'N' )OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM Appr_GroupMembers WHERE userID = ?) )");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setInt(3, paramInt3);
      localPreparedStatement.setInt(4, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowChains where ( groupOrUserID = ? AND userType = ? AND isUser = 'Y') OR ( groupOrUserID = ? AND isUser = 'N' )OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM Appr_GroupMembers WHERE userID = ?) )");
      int i1 = 0;
      boolean bool;
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_Items where ( ( groupOrUserID = ? AND userType = ? AND isUser = 'Y' ) OR ( groupOrUserID = ? AND isUser = 'N' ) OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM Appr_GroupMembers WHERE userID = ?) ) ) AND  ( processingState <> 'Complete' )");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setInt(3, paramInt3);
      localPreparedStatement.setInt(4, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_Items where ( ( groupOrUserID = ? AND userType = ? AND isUser = 'Y' ) OR ( groupOrUserID = ? AND isUser = 'N' ) OR ( isUser = 'A' AND groupOrUserID IN (select apprGroupID FROM Appr_GroupMembers WHERE userID = ?) ) ) AND  ( processingState <> 'Complete' )");
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not determine if the apecified user is required to approve pending or future approval items", localThrowable);
      }
      throw new ApprovalsException("Could not determine if the apecified user is required to approve pending or future approval items", localThrowable);
    }
    finally
    {
      DBUtil.closeAll(this.R, localConnection, localPreparedStatement, localResultSet);
    }
    return false;
  }
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.cleanup";
    if (paramInt < 0) {
      throw new ApprovalsException(30105, "Could not peform the cleanup activities on the approval system");
    }
    long l1 = paramInt * 86400000L;
    long l2 = System.currentTimeMillis();
    long l3 = l2 - l1;
    Timestamp localTimestamp = new Timestamp(l3);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, bb);
      localPreparedStatement.setTimestamp(1, localTimestamp);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, bb);
      int i1 = 100;
      int i2 = 0;
      Object localObject1 = new int[i1];
      while (localResultSet.next())
      {
        if (i2 == i1)
        {
          i1 *= 2;
          int[] arrayOfInt1 = new int[i1];
          System.arraycopy(localObject1, 0, arrayOfInt1, 0, i2);
          localObject1 = arrayOfInt1;
        }
        int i3 = localResultSet.getInt(1);
        localObject1[i2] = i3;
        i2++;
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if (i2 == 0) {
        return;
      }
      int[] arrayOfInt2 = new int[i2];
      System.arraycopy(localObject1, 0, arrayOfInt2, 0, i2);
      int i4 = arrayOfInt2.length;
      Arrays.sort(arrayOfInt2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, jdField_new);
      for (int i5 = 0; i5 < i4; i5++)
      {
        localPreparedStatement.clearParameters();
        localPreparedStatement.setInt(1, arrayOfInt2[i5]);
        DBUtil.executeUpdate(localPreparedStatement, jdField_new);
      }
      this.d.deleteTransactions(arrayOfInt2);
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not peform the cleanup activities on the approval system", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Could not peform the cleanup activities on the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not peform the cleanup activities on the approval system", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public void setItemsFailedRejection(ApprovalsItems paramApprovalsItems, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.setItemsFailedRejection";
    if ((paramApprovalsItems == null) || (paramApprovalsItems.isEmpty())) {
      return;
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    PreparedStatement localPreparedStatement4 = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "update Appr_Items set executionState = 'Failed', processingState = 'Waiting' where itemID = ? AND itemType = ?");
      localPreparedStatement4 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Approved' where itemID = ? AND itemType = ? AND decision <> 'Rejected' AND decision is not null");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove', decision = 'Hold' where itemID = ? AND itemType = ? AND decision = 'Rejected'");
      localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ? AND propName = 'RejectReason'");
      Iterator localIterator = paramApprovalsItems.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
        int i1 = localApprovalsItem.getItemID();
        int i2 = localApprovalsItem.getItemType();
        localPreparedStatement1.clearParameters();
        localPreparedStatement1.setInt(1, i1);
        localPreparedStatement1.setInt(2, i2);
        int i3 = DBUtil.executeUpdate(localPreparedStatement1, "update Appr_Items set executionState = 'Failed', processingState = 'Waiting' where itemID = ? AND itemType = ?");
        if (i3 == 0) {
          DebugLog.log("Unable to flag the item (item type: " + i2 + ", item id: " + i1 + ") as failing rejection.");
        }
        localPreparedStatement4.clearParameters();
        localPreparedStatement4.setInt(1, i1);
        localPreparedStatement4.setInt(2, i2);
        i3 = DBUtil.executeUpdate(localPreparedStatement4, "update Appr_Items set processingState = 'Approved' where itemID = ? AND itemType = ? AND decision <> 'Rejected' AND decision is not null");
        if (i3 == 0) {
          DebugLog.log("Unable to flag the item (item type: " + i2 + ", item id: " + i1 + ") as failing rejection.");
        }
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setInt(1, i1);
        localPreparedStatement2.setInt(2, i2);
        i3 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'ToApprove', decision = 'Hold' where itemID = ? AND itemType = ? AND decision = 'Rejected'");
        if (i3 == 0) {
          DebugLog.log("Unable to flag the item (item type: " + i2 + ", item id: " + i1 + ") after failing rejection.");
        }
        localPreparedStatement3.clearParameters();
        localPreparedStatement3.setInt(1, i1);
        localPreparedStatement3.setInt(2, i2);
        i3 = DBUtil.executeUpdate(localPreparedStatement3, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ? AND propName = 'RejectReason'");
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.closeStatement(localPreparedStatement4);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not flag approval items that failed to reject succesfully", localThrowable);
      }
      throw new ApprovalsException("Could not flag approval items that failed to reject succesfully", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.closeStatement(localPreparedStatement4);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public void setItemsFailedExecution(ApprovalsItems paramApprovalsItems, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.setItemsFailedExecution";
    if ((paramApprovalsItems == null) || (paramApprovalsItems.isEmpty())) {
      return;
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "update Appr_Items set executionState = 'Failed', processingState = 'Approved' where itemID = ? AND itemType = ?");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove', decision = 'Hold' where itemID = ? AND itemType = ? AND ordinal = (select max(ordinal) from Appr_Items where itemID = ? and itemType = ?)");
      Iterator localIterator = paramApprovalsItems.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsItem localApprovalsItem = (ApprovalsItem)localIterator.next();
        int i1 = localApprovalsItem.getItemID();
        int i2 = localApprovalsItem.getItemType();
        localPreparedStatement1.clearParameters();
        localPreparedStatement1.setInt(1, i1);
        localPreparedStatement1.setInt(2, i2);
        int i3 = DBUtil.executeUpdate(localPreparedStatement1, "update Appr_Items set executionState = 'Failed', processingState = 'Approved' where itemID = ? AND itemType = ?");
        if (i3 == 0) {
          DebugLog.log("Unable to flag the item (item type: " + i2 + ", item id: " + i1 + ") as failing execution.");
        }
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setInt(1, i1);
        localPreparedStatement2.setInt(2, i2);
        localPreparedStatement2.setInt(3, i1);
        localPreparedStatement2.setInt(4, i2);
        i3 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'ToApprove', decision = 'Hold' where itemID = ? AND itemType = ? AND ordinal = (select max(ordinal) from Appr_Items where itemID = ? and itemType = ?)");
        if (i3 == 0) {
          DebugLog.log("Unable to flag the item (item type: " + i2 + ", item id: " + i1 + ") after failing execution.");
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      localPreparedStatement1 = null;
      localPreparedStatement2 = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not flag approval items that failed to execute succesfully", localThrowable);
      }
      throw new ApprovalsException("Could not flag approval items that failed to execute succesfully", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  private ApprovalsItems a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, HashMap paramHashMap1, HashMap paramHashMap2)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.getPendingApprovals";
    ApprovalsItems localApprovalsItems = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      int i3;
      if ((paramHashMap1 == null) || ((paramHashMap1 != null) && (paramHashMap1.isEmpty())))
      {
        if (paramInt2 == 1)
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, b);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localPreparedStatement.setInt(4, paramInt1);
          localPreparedStatement.setInt(5, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, b);
        }
        else if ((paramInt4 == 0) && (paramBoolean))
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, E);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, E);
        }
        else if ((paramInt4 == 0) && (!paramBoolean))
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, o);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, o);
        }
        else if ((paramInt4 != 0) && (paramBoolean))
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, bl);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localPreparedStatement.setInt(4, paramInt4);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, bl);
        }
        else
        {
          localPreparedStatement = DBUtil.prepareStatement(localConnection, aL);
          localPreparedStatement.setInt(1, paramInt1);
          localPreparedStatement.setInt(2, paramInt3);
          localPreparedStatement.setInt(3, paramInt1);
          localPreparedStatement.setInt(4, paramInt4);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, aL);
        }
      }
      else
      {
        localObject1 = (String)paramHashMap1.get("StartDueDate");
        localObject2 = (String)paramHashMap1.get("EndDueDate");
        StringBuffer localStringBuffer = new StringBuffer();
        if (paramInt2 == 1) {
          localStringBuffer.append(bk);
        } else if ((paramInt4 == 0) && (paramBoolean)) {
          localStringBuffer.append(i);
        } else if ((paramInt4 == 0) && (!paramBoolean)) {
          localStringBuffer.append(av);
        } else if ((paramInt4 != 0) && (paramBoolean)) {
          localStringBuffer.append(a);
        } else {
          localStringBuffer.append(jdField_do);
        }
        if ((localObject1 != null) && (localObject2 != null))
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" ( ");
          localStringBuffer.append(" dtDue >= ? ");
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" dtDue <= ? ");
          localStringBuffer.append(" ) ");
        }
        else if (localObject1 != null)
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" dtDue >= ? ");
        }
        else if (localObject2 != null)
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append(" dtDue <= ? ");
        }
        localStringBuffer.append(" order by itemType, itemID, ordinal");
        String str2 = localStringBuffer.toString();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
        i3 = 1;
        localPreparedStatement.setInt(i3++, paramInt1);
        localPreparedStatement.setInt(i3++, paramInt3);
        localPreparedStatement.setInt(i3++, paramInt1);
        if (paramInt4 != 0) {
          localPreparedStatement.setInt(i3++, paramInt4);
        }
        if (paramInt2 == 1)
        {
          localPreparedStatement.setInt(i3++, paramInt1);
          localPreparedStatement.setInt(i3++, paramInt1);
        }
        Timestamp localTimestamp;
        if (localObject1 != null)
        {
          localTimestamp = new Timestamp(a((String)localObject1).getTime());
          localPreparedStatement.setTimestamp(i3++, localTimestamp);
        }
        if (localObject2 != null)
        {
          localTimestamp = new Timestamp(a((String)localObject2).getTime());
          localPreparedStatement.setTimestamp(i3++, localTimestamp);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      }
      Object localObject1 = new IntMap();
      Object localObject2 = new GrowableIntArray();
      int i1 = -1;
      int i2 = -1;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      while (localResultSet.next())
      {
        i3 = localResultSet.getInt(1);
        i4 = localResultSet.getInt(2);
        i5 = localResultSet.getInt(3);
        String str3 = localResultSet.getString(4);
        localObject3 = localResultSet.getTimestamp(5);
        localObject4 = localResultSet.getString(6);
        if (((String)localObject4).trim().equalsIgnoreCase("Approved"))
        {
          i1 = i4;
          i2 = i3;
        }
        else if ((i4 != i1) || (i3 != i2))
        {
          localObject5 = null;
          a locala = new a(i3, i5, str3, (Timestamp)localObject3);
          if (((IntMap)localObject1).containsKey(i4))
          {
            localObject5 = (Vector)((IntMap)localObject1).get(i4);
            ((Vector)localObject5).add(locala);
          }
          else
          {
            localObject5 = new Vector();
            ((Vector)localObject5).add(locala);
            ((GrowableIntArray)localObject2).add(i4);
            ((IntMap)localObject1).put(i4, localObject5);
          }
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      int[] arrayOfInt = ((GrowableIntArray)localObject2).getElements();
      int i4 = arrayOfInt.length;
      for (int i5 = 0; i5 < i4; i5++) {
        if (arrayOfInt[i5] == 1)
        {
          int i6 = arrayOfInt[i5];
          localObject3 = (Vector)((IntMap)localObject1).get(i6);
          localObject4 = new a[((Vector)localObject3).size()];
          localObject4 = (a[])((Vector)localObject3).toArray((Object[])localObject4);
          Arrays.sort((Object[])localObject4);
          localObject5 = new int[localObject4.length];
          for (int i7 = 0; i7 < localObject4.length; i7++) {
            localObject5[i7] = localObject4[i7].jdField_if();
          }
          TWTransactions localTWTransactions = this.d.getTransactions((int[])localObject5);
          if (localTWTransactions != null)
          {
            int i8 = localTWTransactions.size();
            if (i8 != localObject4.length) {
              throw new ApprovalsException(30111, "Could not obtain the pending approvals for this user");
            }
            for (int i9 = 0; i9 < i8; i9++)
            {
              TWTransaction localTWTransaction = (TWTransaction)localTWTransactions.get(i9);
              if (localApprovalsItems == null) {
                localApprovalsItems = new ApprovalsItems(Locale.getDefault());
              }
              ApprovalsItem localApprovalsItem = localApprovalsItems.add();
              localApprovalsItem.setItemID(localTWTransaction.getID());
              localApprovalsItem.setItemType(1);
              localApprovalsItem.setItem(localTWTransaction);
              localApprovalsItem.setSubmittingUserID(localObject4[i9].a());
              localApprovalsItem.setSubmittingUserName(localObject4[i9].jdField_do());
              localApprovalsItem.setItemSubType(localTWTransaction.getTransactionType());
              if (localObject4[i9].jdField_for() != null) {
                localApprovalsItem.setSubmissionDate(a(localObject4[i9].jdField_for()));
              }
              localApprovalsItem.setDueDate(localTWTransaction.getTransaction().getApprovalDueDate());
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not obtain the pending approvals for this user", localThrowable);
      }
      if ((localThrowable instanceof TWException)) {
        throw new ApprovalsException(30013, "Could not obtain the pending approvals for this user", localThrowable);
      }
      throw new ApprovalsException("Could not obtain the pending approvals for this user", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsItems;
  }
  
  private Properties a(HashMap paramHashMap)
  {
    Properties localProperties = null;
    if (paramHashMap != null)
    {
      localProperties = new Properties();
      Set localSet = paramHashMap.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str1 = localIterator.next().toString();
        String str2 = paramHashMap.get(str1).toString();
        localProperties.setProperty(str1, str2);
      }
    }
    return localProperties;
  }
  
  private com.ffusion.beans.DateTime a(Timestamp paramTimestamp)
  {
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime();
    long l1 = paramTimestamp.getTime() + paramTimestamp.getNanos() / 1000000;
    localDateTime.setTime(new Date(l1));
    return localDateTime;
  }
  
  private Date a(String paramString)
    throws ApprovalsException
  {
    Date localDate = null;
    try
    {
      localDate = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(paramString);
    }
    catch (ParseException localParseException1)
    {
      try
      {
        localDate = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
      }
      catch (ParseException localParseException2)
      {
        try
        {
          localDate = DateFormatUtil.getFormatter("MMM d, yyyy").parse(paramString);
        }
        catch (ParseException localParseException3)
        {
          throw new ApprovalsException(30080, "The date specified in the search criteria is not of the format MM/dd/yyyy or MM/dd/yyyy HH:mm:ss", localParseException1);
        }
      }
    }
    return localDate;
  }
  
  public void addApprovalGroup(ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.addApprovalGroup";
    if (paramApprovalsGroup == null) {
      throw new ApprovalsException(30125, "Could not add a new approvals group.");
    }
    int i1 = paramApprovalsGroup.getBusinessID();
    String str2 = paramApprovalsGroup.getGroupName();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      if (str2 == null) {
        throw new ApprovalsException(30126, "Could not add a new approvals group.");
      }
      if (i1 < 0) {
        throw new ApprovalsException(30127, "Could not add a new approvals group.");
      }
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into Appr_Groups( apprGroupID, businessID, groupName ) values ( ?, ?, ? )");
      int i2 = DBUtil.getNextId(localConnection, this.bi, "APPROVAL_GROUP");
      localPreparedStatement.setInt(1, i2);
      localPreparedStatement.setInt(2, i1);
      localPreparedStatement.setString(3, str2);
      int i3 = DBUtil.executeUpdate(localPreparedStatement, "insert into Appr_Groups( apprGroupID, businessID, groupName ) values ( ?, ?, ? )");
      if (i3 == 0) {
        throw new ApprovalsException(30001, "Could not add a new approvals group.");
      }
      DBUtil.commit(localConnection);
      paramApprovalsGroup.setApprovalsGroupID(i2);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not add a new approvals group.", localThrowable);
      }
      throw new ApprovalsException("Could not add a new approvals group.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public ApprovalsGroups getApprovalGroups(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.getApprovalGroups";
    ApprovalsGroups localApprovalsGroups = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select apprGroupID, groupName from Appr_Groups where businessID = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select apprGroupID, groupName from Appr_Groups where businessID = ?");
      while (localResultSet.next())
      {
        if (localApprovalsGroups == null) {
          localApprovalsGroups = new ApprovalsGroups(Locale.getDefault());
        }
        ApprovalsGroup localApprovalsGroup = localApprovalsGroups.add();
        localApprovalsGroup.setApprovalsGroupID(localResultSet.getInt(1));
        localApprovalsGroup.setGroupName(localResultSet.getString(2));
        localApprovalsGroup.setBusinessID(paramInt);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not get the approval groups.", localThrowable);
      }
      throw new ApprovalsException("Could not get the approval groups.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsGroups;
  }
  
  public ApprovalsGroups getApprovalGroupsForUser(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.getApprovalGroupsForUser";
    ApprovalsGroups localApprovalsGroups = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select g.apprGroupID, g.businessID, g.groupName from Appr_Groups g, Appr_GroupMembers gm WHERE g.apprGroupID = gm.apprGroupID AND gm.userID = ? ");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select g.apprGroupID, g.businessID, g.groupName from Appr_Groups g, Appr_GroupMembers gm WHERE g.apprGroupID = gm.apprGroupID AND gm.userID = ? ");
      while (localResultSet.next())
      {
        if (localApprovalsGroups == null) {
          localApprovalsGroups = new ApprovalsGroups(Locale.getDefault());
        }
        ApprovalsGroup localApprovalsGroup = localApprovalsGroups.add();
        localApprovalsGroup.setApprovalsGroupID(localResultSet.getInt(1));
        localApprovalsGroup.setBusinessID(localResultSet.getInt(2));
        localApprovalsGroup.setGroupName(localResultSet.getString(3));
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not get the approval groups for the specified user.", localThrowable);
      }
      throw new ApprovalsException("Could not get the approval groups.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsGroups;
  }
  
  public void updateApprovalGroup(ApprovalsGroup paramApprovalsGroup, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.updateApprovalsGroup";
    if (paramApprovalsGroup == null) {
      throw new ApprovalsException(30125, "Could not update the approval group.");
    }
    if (paramApprovalsGroup.getGroupName() == null) {
      throw new ApprovalsException(30126, "Could not update the approval group.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      int i1 = paramApprovalsGroup.getApprovalsGroupID();
      String str2 = paramApprovalsGroup.getGroupName();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update Appr_Groups set groupName = ? where apprGroupID = ?");
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setInt(2, i1);
      int i2 = DBUtil.executeUpdate(localPreparedStatement, "update Appr_Groups set groupName = ? where apprGroupID = ?");
      if (i2 == 0) {
        throw new ApprovalsException(30129, "Could not update the approval group.");
      }
      DBUtil.closeStatement(localPreparedStatement);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not update the approval group.", localThrowable);
      }
      throw new ApprovalsException("Could not update the approval group.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public boolean isApprovalGroupInUse(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.IsApprovalGroupIn";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_FlowChains where groupOrUserID = ? AND isUser = 'A'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_FlowChains where groupOrUserID = ? AND isUser = 'A'");
      int i1 = 0;
      boolean bool;
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_Items where groupOrUserID = ? AND isUser = 'A' AND processingState <> 'Complete'");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from Appr_Items where groupOrUserID = ? AND isUser = 'A' AND processingState <> 'Complete'");
      if (localResultSet.next())
      {
        i1 = localResultSet.getInt(1);
        if (i1 > 0)
        {
          bool = true;
          return bool;
        }
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not determine if the specified approval group is being used by the approval system", localThrowable);
      }
      throw new ApprovalsException("Could not determine if the specified approval group is being used by the approval system", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return false;
  }
  
  public ApprovalsGroupMembers getApprovalGroupMembers(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.getApprovalGroupMembers";
    ApprovalsGroupMembers localApprovalsGroupMembers = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select userID from Appr_GroupMembers where apprGroupID = ? ");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select userID from Appr_GroupMembers where apprGroupID = ? ");
      while (localResultSet.next())
      {
        if (localApprovalsGroupMembers == null) {
          localApprovalsGroupMembers = new ApprovalsGroupMembers(Locale.getDefault());
        }
        ApprovalsGroupMember localApprovalsGroupMember = localApprovalsGroupMembers.add();
        localApprovalsGroupMember.setApprovalsGroupID(paramInt);
        localApprovalsGroupMember.setUserID(localResultSet.getInt(1));
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not get the approval group members.", localThrowable);
      }
      throw new ApprovalsException("Could not get the approval group members.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsGroupMembers;
  }
  
  public void assignApprovalGroups(int paramInt, ApprovalsGroups paramApprovalsGroups, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str = "ApprovalsAdapter.assignApprovalGroups";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from Appr_GroupMembers where userID = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from Appr_GroupMembers where userID = ?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      if ((paramApprovalsGroups != null) && (paramApprovalsGroups.size() > 0))
      {
        Iterator localIterator = paramApprovalsGroups.iterator();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "INSERT into Appr_GroupMembers ( apprGroupID, userID ) VALUES( ?, ?)");
        while (localIterator.hasNext())
        {
          ApprovalsGroup localApprovalsGroup = (ApprovalsGroup)localIterator.next();
          int i1 = localApprovalsGroup.getApprovalsGroupID();
          localPreparedStatement.clearParameters();
          localPreparedStatement.setInt(1, i1);
          localPreparedStatement.setInt(2, paramInt);
          int i2 = DBUtil.executeUpdate(localPreparedStatement, "INSERT into Appr_GroupMembers ( apprGroupID, userID ) VALUES( ?, ?)");
          if (i2 == 0) {
            throw new ApprovalsException(30001, "Could not assign the approval groups.");
          }
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not assign the approval groups.", localThrowable);
      }
      throw new ApprovalsException("Could not assign the approval groups.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.R, localConnection);
    }
  }
  
  public ApprovalsItems removeApprovalGroup(int paramInt, HashMap paramHashMap)
    throws ApprovalsException
  {
    String str1 = "ApprovalsAdapter.removeApprovalGroup";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    PreparedStatement localPreparedStatement3 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ApprovalsItems localApprovalsItems = null;
    try
    {
      localConnection = DBUtil.getConnection(this.R, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select count(*) from Appr_GroupMembers where apprGroupID = ?");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select count(*) from Appr_GroupMembers where apprGroupID = ?");
      int i1 = 0;
      if (localResultSet1.next())
      {
        i1 = localResultSet1.getInt(1);
        if (i1 != 0) {
          throw new ApprovalsException(30131, "Could not remove the approval group.");
        }
      }
      else
      {
        throw new ApprovalsException(30132, "Could not remove the approval group.");
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localPreparedStatement1 = null;
      localResultSet1 = null;
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select levelID, ordinal from Appr_FlowChains where isUser = 'A' AND groupOrUserID = ? ");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select levelID, ordinal from Appr_FlowChains where isUser = 'A' AND groupOrUserID = ? ");
      int i2;
      int i3;
      int i4;
      while (localResultSet1.next())
      {
        i2 = localResultSet1.getInt(1);
        i3 = localResultSet1.getInt(2);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
        localPreparedStatement2.setInt(1, i2);
        i1 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_FlowLevels set businessID = businessID where levelID = ?");
        if (i1 != 0)
        {
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'A'");
          localPreparedStatement2.setInt(1, i2);
          localPreparedStatement2.setInt(2, i3);
          localPreparedStatement2.setInt(3, paramInt);
          DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_FlowChains where levelID = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'A'");
          if (i1 != 0)
          {
            DBUtil.closeStatement(localPreparedStatement2);
            localPreparedStatement2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal");
            localPreparedStatement2.setInt(1, i2);
            localPreparedStatement2.setInt(2, i3);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal from Appr_FlowChains where levelID = ? AND ordinal > ? order by ordinal");
            while (localResultSet2.next())
            {
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?");
              i4 = localResultSet2.getInt(1);
              localPreparedStatement3.setInt(1, i4 - 1);
              localPreparedStatement3.setInt(2, i2);
              localPreparedStatement3.setInt(3, i4);
              i1 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_FlowChains set ordinal = ? where levelID = ? AND ordinal = ?");
              if (i1 == 0) {
                throw new ApprovalsException(30065, "Could not remove the approval group.");
              }
              DBUtil.closeStatement(localPreparedStatement3);
              localPreparedStatement3 = null;
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localResultSet2 = null;
            localPreparedStatement2 = null;
          }
        }
      }
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND isUser = 'A' AND processingState <> 'Complete' order by itemType, itemID");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select itemID, itemType, ordinal, itemSubType, submittingUserID, processingState, dtSubmission, dtDue from Appr_Items where groupOrUserID = ? AND isUser = 'A' AND processingState <> 'Complete' order by itemType, itemID");
      while (localResultSet1.next())
      {
        i2 = localResultSet1.getInt(1);
        i3 = localResultSet1.getInt(2);
        i4 = localResultSet1.getInt(3);
        int i5 = localResultSet1.getInt(4);
        int i6 = localResultSet1.getInt(5);
        String str2 = localResultSet1.getString(6).trim();
        Timestamp localTimestamp1 = localResultSet1.getTimestamp(7);
        Timestamp localTimestamp2 = localResultSet1.getTimestamp(8);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        localPreparedStatement2.setInt(1, i2);
        localPreparedStatement2.setInt(2, i3);
        int i7 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set itemType = itemType where itemID = ? AND itemType = ?");
        if (i7 != 0)
        {
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'A'");
          localPreparedStatement2.setInt(1, i2);
          localPreparedStatement2.setInt(2, i3);
          localPreparedStatement2.setInt(3, i4);
          localPreparedStatement2.setInt(4, paramInt);
          i1 = DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_Items where itemID = ? AND itemType = ? AND ordinal = ? AND groupOrUserID = ? AND isUser = 'A'");
          DBUtil.closeStatement(localPreparedStatement2);
          localPreparedStatement2 = null;
          if (i1 != 0)
          {
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
            localPreparedStatement2.setInt(1, i2);
            localPreparedStatement2.setInt(2, i3);
            DBUtil.executeUpdate(localPreparedStatement2, "DELETE from Appr_ItemProps WHERE itemID = ? AND itemType = ?");
            DBUtil.closeStatement(localPreparedStatement2);
            localPreparedStatement2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal");
            localPreparedStatement2.setInt(1, i2);
            localPreparedStatement2.setInt(2, i3);
            localPreparedStatement2.setInt(3, i4);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select ordinal from Appr_Items where itemID = ? AND itemType = ? AND ordinal > ? order by ordinal");
            int i8 = 0;
            while (localResultSet2.next())
            {
              i8++;
              localPreparedStatement3 = DBUtil.prepareStatement(localConnection, "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?");
              int i9 = localResultSet2.getInt(1);
              localPreparedStatement3.setInt(1, i9 - 1);
              localPreparedStatement3.setInt(2, i2);
              localPreparedStatement3.setInt(3, i3);
              localPreparedStatement3.setInt(4, i9);
              i1 = DBUtil.executeUpdate(localPreparedStatement3, "update Appr_Items set ordinal = ? where itemID = ? AND itemType = ? AND ordinal = ?");
              if (i1 == 0) {
                throw new ApprovalsException(30065, "Could not remove the approval group.");
              }
              DBUtil.closeStatement(localPreparedStatement3);
              localPreparedStatement3 = null;
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localResultSet2 = null;
            localPreparedStatement2 = null;
            if (str2.equals("ToApprove")) {
              if (i8 == 0)
              {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                localPreparedStatement2.setInt(1, i2);
                localPreparedStatement2.setInt(2, i3);
                DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'Complete', executionState = 'Passed' where itemID = ? AND itemType = ?");
                DBUtil.closeStatement(localPreparedStatement2);
                localPreparedStatement2 = null;
                if (localApprovalsItems == null) {
                  localApprovalsItems = new ApprovalsItems(Locale.getDefault());
                }
                ApprovalsItem localApprovalsItem = localApprovalsItems.add();
                localApprovalsItem.setItemID(i2);
                localApprovalsItem.setItemType(i3);
                localApprovalsItem.setItemSubType(i5);
                localApprovalsItem.setSubmittingUserID(i6);
                if (localTimestamp1 != null) {
                  localApprovalsItem.setSubmissionDate(a(localTimestamp1));
                }
                if (localTimestamp2 != null) {
                  localApprovalsItem.setDueDate(a(localTimestamp2));
                }
                if (i3 == 1)
                {
                  TWTransaction localTWTransaction = this.d.getTransaction(i2);
                  localApprovalsItem.setItem(localTWTransaction);
                }
              }
              else
              {
                localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
                localPreparedStatement2.setInt(1, i2);
                localPreparedStatement2.setInt(2, i3);
                localPreparedStatement2.setInt(3, i4);
                i1 = DBUtil.executeUpdate(localPreparedStatement2, "update Appr_Items set processingState = 'ToApprove' where itemID = ? AND itemType = ? AND ordinal = ?");
                if (i1 == 0) {
                  throw new ApprovalsException(30063, "Could not remove the approval group.");
                }
                DBUtil.closeStatement(localPreparedStatement2);
                localPreparedStatement2 = null;
              }
            }
          }
        }
      }
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from Appr_Groups where apprGroupID = ? ");
      localPreparedStatement2.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement2, "delete from Appr_Groups where apprGroupID = ? ");
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
      localResultSet1 = null;
      localPreparedStatement1 = null;
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      DebugLog.throwing(str1, localThrowable);
      if ((localThrowable instanceof ApprovalsException)) {
        throw ((ApprovalsException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new ApprovalsException(30011, "Could not remove the approval group.", localThrowable);
      }
      throw new ApprovalsException("Could not remove the approval group.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeResultSet(localResultSet2);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeStatement(localPreparedStatement3);
      DBUtil.returnConnection(this.R, localConnection);
    }
    return localApprovalsItems;
  }
  
  class a
    implements Comparable
  {
    private int jdField_do;
    private int a;
    private String jdField_for;
    private Timestamp jdField_if;
    
    public a(int paramInt1, int paramInt2, String paramString, Timestamp paramTimestamp)
    {
      this.jdField_do = paramInt1;
      this.a = paramInt2;
      this.jdField_for = paramString;
      this.jdField_if = paramTimestamp;
    }
    
    public int jdField_if()
    {
      return this.jdField_do;
    }
    
    public void a(int paramInt)
    {
      this.jdField_do = paramInt;
    }
    
    public int a()
    {
      return this.a;
    }
    
    public void jdField_if(int paramInt)
    {
      this.a = paramInt;
    }
    
    public String jdField_do()
    {
      return this.jdField_for;
    }
    
    public void a(String paramString)
    {
      this.jdField_for = paramString;
    }
    
    public Timestamp jdField_for()
    {
      return this.jdField_if;
    }
    
    public void a(Timestamp paramTimestamp)
    {
      this.jdField_if = paramTimestamp;
    }
    
    public int compareTo(Object paramObject)
    {
      if ((paramObject == null) || (!(paramObject instanceof a))) {
        throw new ClassCastException("The object being compared is either null or not an ItemData object");
      }
      a locala = (a)paramObject;
      int i = locala.jdField_if();
      if (this.jdField_do < i) {
        return -1;
      }
      if (this.jdField_do > i) {
        return 1;
      }
      return 0;
    }
  }
  
  class b
    implements Comparable
  {
    private int jdField_if;
    private String jdField_for;
    private String a;
    private BigDecimal jdField_do;
    private BigDecimal jdField_new;
    private boolean jdField_int;
    
    public b() {}
    
    public b(int paramInt, String paramString1, String paramString2, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
    {
      this.jdField_if = paramInt;
      this.jdField_for = paramString1;
      this.jdField_do = paramBigDecimal1;
      this.jdField_new = paramBigDecimal2;
      this.a = paramString2;
    }
    
    public int jdField_if()
    {
      return this.jdField_if;
    }
    
    public void a(int paramInt)
    {
      this.jdField_if = paramInt;
    }
    
    public String jdField_int()
    {
      return this.jdField_for;
    }
    
    public void a(String paramString)
    {
      this.jdField_for = paramString;
    }
    
    public String jdField_do()
    {
      return this.a;
    }
    
    public void jdField_if(String paramString)
    {
      this.a = paramString;
    }
    
    public BigDecimal jdField_for()
    {
      return this.jdField_do;
    }
    
    public void a(BigDecimal paramBigDecimal)
    {
      this.jdField_do = paramBigDecimal;
    }
    
    public BigDecimal jdField_new()
    {
      return this.jdField_new;
    }
    
    public void jdField_if(BigDecimal paramBigDecimal)
    {
      this.jdField_new = paramBigDecimal;
    }
    
    public boolean a()
    {
      return this.jdField_int;
    }
    
    public void a(boolean paramBoolean)
    {
      this.jdField_int = paramBoolean;
    }
    
    public int compareTo(Object paramObject)
    {
      if ((paramObject == null) || (!(paramObject instanceof b))) {
        throw new ClassCastException("The object being compared is either null or not a RowData object");
      }
      b localb = (b)paramObject;
      BigDecimal localBigDecimal1 = localb.jdField_for();
      BigDecimal localBigDecimal2 = localb.jdField_new();
      String str = localb.jdField_int();
      int i = this.jdField_do.compareTo(localBigDecimal1);
      if (i < 0) {
        return -1;
      }
      if (i > 0) {
        return 1;
      }
      int j = this.jdField_new.compareTo(localBigDecimal2);
      if (j < 0) {
        return -1;
      }
      if (j > 0) {
        return 1;
      }
      if (this.jdField_if < localb.jdField_if()) {
        return -1;
      }
      if (this.jdField_if > localb.jdField_if()) {
        return 1;
      }
      return 0;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.adapter.ApprovalsAdapter
 * JD-Core Version:    0.7.0.1
 */