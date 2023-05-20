package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.TransactionTypes;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.bcreport.BusinessServiceChargeEntries;
import com.ffusion.beans.bcreport.BusinessServiceChargeList;
import com.ffusion.beans.bcreport.BusinessServiceChargeLists;
import com.ffusion.beans.bcreport.BusinessServiceChargeRpt;
import com.ffusion.beans.bcreport.CSRActivityRpt;
import com.ffusion.beans.bcreport.CSRTeamActivityRpt;
import com.ffusion.beans.bcreport.ConsumerServiceChargeEntries;
import com.ffusion.beans.bcreport.ConsumerServiceChargeList;
import com.ffusion.beans.bcreport.ConsumerServiceChargeLists;
import com.ffusion.beans.bcreport.ConsumerServiceChargeRpt;
import com.ffusion.beans.bcreport.LoginActivityDetails;
import com.ffusion.beans.bcreport.LoginActivityRpt;
import com.ffusion.beans.bcreport.LoginActivitySummaries;
import com.ffusion.beans.bcreport.LoginSummaryRpt;
import com.ffusion.beans.bcreport.ReportLogRecord;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.bcreport.UserActivityRecords;
import com.ffusion.beans.bcreport.UserActivityRpt;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.handlers.AdminHandler;
import com.ffusion.efs.adapters.profile.servicecharge.BusinessDurationReportHandler;
import com.ffusion.efs.adapters.profile.servicecharge.BusinessProcessingHandler;
import com.ffusion.efs.adapters.profile.servicecharge.BusinessReportHandler;
import com.ffusion.efs.adapters.profile.servicecharge.ConsumerDurationReportHandler;
import com.ffusion.efs.adapters.profile.servicecharge.ConsumerProcessingHandler;
import com.ffusion.efs.adapters.profile.servicecharge.ConsumerReportHandler;
import com.ffusion.efs.adapters.profile.servicecharge.ReportProcessingHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.admin.AdminException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;

public class ReportAuditAdapter
{
  private static final String K = "AUDIT_LOG";
  public static final String USER_ID = "a.USER_ID";
  public static final String USER_ID_INT = "a.USER_ID_INT";
  public static final String PRIMARY_USER_ID = "a.PRIMARY_USER_ID";
  private static final String k = "a.AGENT_ID";
  private static final String jdField_if = "a.AGENT_TYPE";
  private static final String jdField_do = "a.DESCRIPTION";
  public static final String LOG_DATE = "a.LOG_DATE";
  private static final String jdField_case = "a.TRAN_ID";
  public static final String TRAN_TYPE = "a.TRAN_TYPE";
  public static final String BUSINESS_ID = "a.BUSINESS_ID";
  private static final String x = "a.AMOUNT";
  private static final String f = "a.SRVR_TID";
  private static final String u = "a.CURRENCY_CODE";
  private static final String v = "a.MODULE";
  private static final String B = "bus.business_name";
  private static final String P = "bus.affiliate_bank_id";
  private static final String jdField_void = "cust.affiliate_bank_id";
  private static final String b = "be.first_name";
  private static final String jdField_for = "be.last_name";
  private static final int I = 100;
  private static final String[] z = { "Group", "User", "StartDate", "EndDate", "StartTime", "EndTime", "Agent", "TranId", "TransactionType", "Module" };
  private static final String[] s = { "Group", "User", "StartDate", "EndDate", "StartTime", "EndTime", "TranId", "TransactionType", "Module" };
  private static final String[] F = { "Business", "User", "TranId", "TransactionType", "StartTime", "StartDate", "EndTime", "EndDate", "Affiliate Bank" };
  private static final String[] q = { "Business", "User", "TranId", "Agent", "TransactionType", "StartTime", "EndTime", "StartDate", "EndDate", "Affiliate Bank" };
  private static final String[] E = { "Business", "User", "TranId", "TransactionType", "StartTime", "EndTime", "StartDate", "EndDate", "Affiliate Bank" };
  private static final String[] jdField_else = { "Business", "User", "TranId", "StartDate", "StartTime", "EndTime", "EndDate", "Affiliate Bank" };
  private static final String[] C = { "StartDate", "StartTime", "EndTime", "EndDate", "Agent", "Business", "User", "Activity", "Module", "Affiliate Bank" };
  private static final String[] t = { "StartDate", "StartTime", "EndTime", "EndDate", "Agent", "Business", "User", "Activity", "Module", "Affiliate Bank" };
  private static final String[] g = { "StartDate", "EndDate", "StartTime", "EndTime", "TranId", "TransactionType", "Module" };
  private static final String y = "insert into AUDIT_LOG (TRAN_ID,USER_ID,AGENT_ID,AGENT_TYPE,DESCRIPTION,LOG_DATE,TRAN_TYPE,BUSINESS_ID,AMOUNT,CURRENCY_CODE,SRVR_TID,STATE,TO_ACCT_ID, TO_ACCT_RTGNUM, FROM_ACCT_ID, FROM_ACCT_RTGNUM, MODULE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String a = "select a.USER_ID, a.AGENT_ID, a.AGENT_TYPE, a.DESCRIPTION, a.TRAN_ID, a.TRAN_TYPE, a.BUSINESS_ID, a.AMOUNT, a.CURRENCY_CODE, a.LOG_DATE, a.SRVR_TID, a.STATE, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM, a.MODULE from AUDIT_LOG a";
  private static final String n = "SELECT a.LOG_DATE, be.first_name, be.last_name, be.user_name, be.bank_employee_id, bus.business_name,  pri.first_name, pri.last_name,  cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM,  a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN bank_employee be ON a.AGENT_ID_INT=be.employee_id  LEFT OUTER JOIN trans_type_desc t on a.TRAN_TYPE=t.id left outer join business bus on a.BUSINESS_ID=bus.business_id LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id=cr.PRIMARY_USER_ID";
  private static final String jdField_try = "SELECT a.LOG_DATE, bus.business_name,  pri.first_name, pri.last_name,  cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_ID,  a.FROM_ACCT_RTGNUM, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id LEFT OUTER JOIN business bus ON a.BUSINESS_ID=bus.business_id  LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id=cr.PRIMARY_USER_ID ";
  private static final String A = "SELECT a.LOG_DATE, cust.first_name, cust.last_name, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_RTGNUM, a.FROM_ACCT_ID, a.TO_ACCT_RTGNUM, a.TO_ACCT_ID, a.DESCRIPTION  FROM AUDIT_LOG a LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id";
  private static final String Q = "SELECT a.LOG_DATE, cust.first_name, cust.last_name, a.TRAN_TYPE, a.TRAN_ID, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN customer cust ON a.USER_ID_INT = cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id";
  private static final String jdField_byte = "SELECT a.LOG_DATE, cust.first_name, cust.last_name, be.first_name, be.last_name, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_RTGNUM, a.FROM_ACCT_ID, a.TO_ACCT_RTGNUM, a.TO_ACCT_ID, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN bank_employee be ON a.AGENT_ID_INT=be.employee_id LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id";
  private static final String e = "select bus.business_name, cust.user_name, a.TRAN_TYPE, a.LOG_DATE, a.DESCRIPTION, pri.first_name, pri.last_name from AUDIT_LOG a ";
  private static final String l = "select pass.Total, fail.Total from (select count(*) as Total from (select BUSINESS_ID as biz, USER_ID_INT as user_id from AUDIT_LOG a ";
  private static final String H = ")auditpass ";
  private static final String j = ") pass , (select count(*) as Total from (select BUSINESS_ID as biz, USER_ID_INT as user_id from AUDIT_LOG a ";
  private static final String r = ")auditfail ";
  private static final String i = ")fail ";
  private static final String L = "  LEFT OUTER JOIN business bus ON  a.BUSINESS_ID=bus.business_id ";
  private static final String d = "  LEFT OUTER JOIN customer cust ON  cust.directory_id=a.USER_ID_INT  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id = cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id = cr.PRIMARY_USER_ID ";
  private static final String O = "  LEFT OUTER JOIN business bus ON  auditpass.biz=bus.business_id ";
  private static final String p = "  LEFT OUTER JOIN customer cust ON  cust.directory_id=auditpass.user_id  ";
  private static final String N = "  LEFT OUTER JOIN business bus ON  auditfail.biz=bus.business_id ";
  private static final String m = "  LEFT OUTER JOIN customer cust ON  cust.directory_id=auditfail.user_id  ";
  private static final String h = "SELECT a.LOG_DATE, cust.first_name, cust.last_name, a.TRAN_TYPE, a.TRAN_ID, a.FROM_ACCT_RTGNUM, a.FROM_ACCT_ID, a.TO_ACCT_RTGNUM, a.TO_ACCT_ID, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id";
  private static final String w = " select business_name from business where business_id = ";
  private static final String jdField_null = " select c.first_name, c.last_name, c.user_name, cd.cust_id from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.directory_id=";
  private static final String jdField_new = " select c.first_name, c.last_name, c.user_name, cd.cust_id from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.directory_id IN (";
  private static final String D = " select first_name, last_name, user_name, bank_employee_id from bank_employee where employee_id = ";
  private static final String G = "SELECT a.LOG_DATE, bus.business_name, pri.first_name, pri.last_name, cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id LEFT OUTER JOIN business bus ON a.BUSINESS_ID = bus.business_id LEFT OUTER JOIN customer cust ON USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri on pri.directory_id=cr.PRIMARY_USER_ID ";
  private static final String jdField_int = "bus.business_name";
  private static final String o = "cust.last_name";
  private static final String M = "cust.first_name";
  private static final String c = "cust.user_name";
  private static final String jdField_char = "t.description";
  private static final String J = "SELECT directory_id FROM business WHERE business_name = ?";
  private static final String jdField_long = "SELECT directory_id FROM customer WHERE user_name = ? AND customer_type='2'";
  private static final String jdField_goto = "select a.USER_ID, a.AGENT_ID, a.AGENT_TYPE, a.DESCRIPTION, a.TRAN_ID, a.TRAN_TYPE, a.BUSINESS_ID, a.AMOUNT, a.CURRENCY_CODE, a.LOG_DATE, a.SRVR_TID, a.STATE, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM, a.MODULE FROM AUDIT_LOG a WHERE a.TRAN_ID=? ORDER BY a.LOG_DATE";
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportAuditAdapter.getReportData";
    try
    {
      Profile.isInitialized();
    }
    catch (ProfileException localProfileException)
    {
      throw new AdminException(str1, 22, "ReportAuditAdapter:Unable to create DB connection pool");
    }
    Locale localLocale = null;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject1 = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = ((UserLocale)localObject1).getLocale();
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
    }
    Object localObject1 = paramReportCriteria.getSearchCriteria();
    if (localObject1 == null)
    {
      localObject2 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str1, 10, (String)localObject2);
    }
    Object localObject2 = paramReportCriteria.getReportOptions();
    if (localObject2 == null)
    {
      str2 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new AdminException(str1, 12, str2);
    }
    String str2 = ((Properties)localObject2).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      localObject3 = "The report options contained within the Report Criteria used in a call to getAccountReportData does not contain a report type.";
      throw new AdminException(str1, 11, (String)localObject3);
    }
    Object localObject3 = null;
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    if (str2.equals("Session Receipt Report"))
    {
      localObject3 = (ReportResult)a(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    else
    {
      Object localObject4;
      Object localObject5;
      Object localObject6;
      Object localObject7;
      Object localObject8;
      Object localObject9;
      Object localObject10;
      Object localObject11;
      Object localObject12;
      Object localObject13;
      Object localObject14;
      Object localObject15;
      if ((str2.equals("On Behalf Of Report")) || (str2.equals("System Activity Report")) || (str2.equals("Session Activity Report")))
      {
        localObject3 = new ReportResult(localLocale);
        try
        {
          jdField_if(paramReportCriteria, localLocale, paramHashMap);
          ((ReportResult)localObject3).init(paramReportCriteria);
          ArrayList localArrayList1 = (ArrayList)paramHashMap.get("BusinessEmployeesForReport");
          if (localArrayList1.size() == 0)
          {
            localObject4 = localObject3;
            return localObject4;
          }
          ArrayList localArrayList2;
          ReportColumn localReportColumn;
          ArrayList localArrayList3;
          if (str2.equals("On Behalf Of Report"))
          {
            ((ReportResult)localObject3).setHeading(null);
            ((ReportResult)localObject3).setSectionHeading(null);
            localObject4 = new ReportDataDimensions(localLocale);
            ((ReportDataDimensions)localObject4).setNumRows(-1);
            ((ReportDataDimensions)localObject4).setNumColumns(9);
            ((ReportResult)localObject3).setDataDimensions((ReportDataDimensions)localObject4);
            localObject5 = new ReportColumn(localLocale);
            localObject6 = new ArrayList();
            ((ArrayList)localObject6).add(ReportConsts.getColumnName(1, localLocale));
            ((ReportColumn)localObject5).setLabels((ArrayList)localObject6);
            ((ReportColumn)localObject5).setDataType("com.ffusion.util.beans.DateTime");
            ((ReportColumn)localObject5).setWidthAsPercent(8);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject5);
            localObject7 = new ReportColumn(localLocale);
            localObject8 = new ArrayList();
            ((ArrayList)localObject8).add(ReportConsts.getColumnName(3, localLocale));
            ((ReportColumn)localObject7).setLabels((ArrayList)localObject8);
            ((ReportColumn)localObject7).setDataType("java.lang.String");
            ((ReportColumn)localObject7).setWidthAsPercent(10);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject7);
            localObject9 = new ReportColumn(localLocale);
            localObject10 = new ArrayList();
            ((ArrayList)localObject10).add(ReportConsts.getColumnName(2, localLocale));
            ((ReportColumn)localObject9).setLabels((ArrayList)localObject10);
            ((ReportColumn)localObject9).setDataType("java.lang.String");
            ((ReportColumn)localObject9).setWidthAsPercent(10);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject9);
            localObject11 = new ReportColumn(localLocale);
            localObject12 = new ArrayList();
            ((ArrayList)localObject12).add(ReportConsts.getColumnName(4, localLocale));
            ((ReportColumn)localObject11).setLabels((ArrayList)localObject12);
            ((ReportColumn)localObject11).setDataType("java.lang.String");
            ((ReportColumn)localObject11).setWidthAsPercent(15);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject11);
            localObject13 = new ReportColumn(localLocale);
            localObject14 = new ArrayList();
            ((ArrayList)localObject14).add(ReportConsts.getColumnName(6, localLocale));
            ((ReportColumn)localObject13).setLabels((ArrayList)localObject14);
            ((ReportColumn)localObject13).setDataType("java.lang.String");
            ((ReportColumn)localObject13).setWidthAsPercent(15);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject13);
            localObject15 = new ReportColumn(localLocale);
            localArrayList2 = new ArrayList();
            localArrayList2.add(ReportConsts.getColumnName(7, localLocale));
            ((ReportColumn)localObject15).setLabels(localArrayList2);
            ((ReportColumn)localObject15).setDataType("java.lang.String");
            ((ReportColumn)localObject15).setWidthAsPercent(8);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject15);
            localObject15 = new ReportColumn(localLocale);
            localArrayList2 = new ArrayList();
            localArrayList2.add(ReportConsts.getColumnName(8, localLocale));
            ((ReportColumn)localObject15).setLabels(localArrayList2);
            ((ReportColumn)localObject15).setDataType("java.lang.String");
            ((ReportColumn)localObject15).setWidthAsPercent(12);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject15);
            localObject15 = new ReportColumn(localLocale);
            localArrayList2 = new ArrayList();
            localArrayList2.add(ReportConsts.getColumnName(9, localLocale));
            ((ReportColumn)localObject15).setLabels(localArrayList2);
            ((ReportColumn)localObject15).setDataType("java.lang.String");
            ((ReportColumn)localObject15).setWidthAsPercent(12);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject15);
            localReportColumn = new ReportColumn(localLocale);
            localArrayList3 = new ArrayList();
            localArrayList3.add(ReportConsts.getColumnName(5, localLocale));
            localReportColumn.setLabels(localArrayList3);
            localReportColumn.setDataType("java.lang.String");
            localReportColumn.setWidthAsPercent(20);
            ((ReportResult)localObject3).addColumn(localReportColumn);
          }
          else if ((str2.equals("System Activity Report")) || (str2.equals("Session Activity Report")))
          {
            ((ReportResult)localObject3).setHeading(null);
            ((ReportResult)localObject3).setSectionHeading(null);
            localObject4 = new ReportDataDimensions(localLocale);
            ((ReportDataDimensions)localObject4).setNumRows(-1);
            ((ReportDataDimensions)localObject4).setNumColumns(9);
            ((ReportResult)localObject3).setDataDimensions((ReportDataDimensions)localObject4);
            localObject5 = new ReportColumn(localLocale);
            localObject6 = new ArrayList();
            ((ArrayList)localObject6).add(ReportConsts.getColumnName(10, localLocale));
            ((ReportColumn)localObject5).setLabels((ArrayList)localObject6);
            ((ReportColumn)localObject5).setDataType("java.lang.String");
            ((ReportColumn)localObject5).setWidthAsPercent(8);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject5);
            localObject7 = new ReportColumn(localLocale);
            localObject8 = new ArrayList();
            ((ArrayList)localObject8).add(ReportConsts.getColumnName(14, localLocale));
            ((ReportColumn)localObject7).setLabels((ArrayList)localObject8);
            ((ReportColumn)localObject7).setDataType("java.lang.String");
            ((ReportColumn)localObject7).setWidthAsPercent(5);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject7);
            localObject9 = new ReportColumn(localLocale);
            localObject10 = new ArrayList();
            ((ArrayList)localObject10).add(ReportConsts.getColumnName(11, localLocale));
            ((ReportColumn)localObject9).setLabels((ArrayList)localObject10);
            ((ReportColumn)localObject9).setDataType("java.lang.String");
            ((ReportColumn)localObject9).setWidthAsPercent(15);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject9);
            localObject11 = new ReportColumn(localLocale);
            localObject12 = new ArrayList();
            ((ArrayList)localObject12).add(ReportConsts.getColumnName(12, localLocale));
            ((ReportColumn)localObject11).setLabels((ArrayList)localObject12);
            ((ReportColumn)localObject11).setDataType("java.lang.String");
            ((ReportColumn)localObject11).setWidthAsPercent(15);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject11);
            localObject13 = new ReportColumn(localLocale);
            localObject14 = new ArrayList();
            ((ArrayList)localObject14).add(ReportConsts.getColumnName(15, localLocale));
            ((ReportColumn)localObject13).setLabels((ArrayList)localObject14);
            ((ReportColumn)localObject13).setDataType("java.lang.String");
            ((ReportColumn)localObject13).setWidthAsPercent(10);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject13);
            localObject15 = new ReportColumn(localLocale);
            localArrayList2 = new ArrayList();
            localArrayList2.add(ReportConsts.getColumnName(16, localLocale));
            ((ReportColumn)localObject15).setLabels(localArrayList2);
            ((ReportColumn)localObject15).setDataType("java.lang.String");
            ((ReportColumn)localObject15).setWidthAsPercent(8);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject15);
            localObject15 = new ReportColumn(localLocale);
            localArrayList2 = new ArrayList();
            localArrayList2.add(ReportConsts.getColumnName(17, localLocale));
            ((ReportColumn)localObject15).setLabels(localArrayList2);
            ((ReportColumn)localObject15).setDataType("java.lang.String");
            ((ReportColumn)localObject15).setWidthAsPercent(12);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject15);
            localObject15 = new ReportColumn(localLocale);
            localArrayList2 = new ArrayList();
            localArrayList2.add(ReportConsts.getColumnName(18, localLocale));
            ((ReportColumn)localObject15).setLabels(localArrayList2);
            ((ReportColumn)localObject15).setDataType("java.lang.String");
            ((ReportColumn)localObject15).setWidthAsPercent(12);
            ((ReportResult)localObject3).addColumn((ReportColumn)localObject15);
            localReportColumn = new ReportColumn(localLocale);
            localArrayList3 = new ArrayList();
            localArrayList3.add(ReportConsts.getColumnName(13, localLocale));
            localReportColumn.setLabels(localArrayList3);
            localReportColumn.setDataType("java.lang.String");
            localReportColumn.setWidthAsPercent(20);
            ((ReportResult)localObject3).addColumn(localReportColumn);
          }
          a(paramSecureUser, localLocale, paramReportCriteria, localArrayList1, (IReportResult)localObject3, paramHashMap);
        }
        catch (Exception localException1)
        {
          ((ReportResult)localObject3).setError(localException1);
          throw new AdminException(str1, 22, localException1);
        }
        finally
        {
          try
          {
            ((ReportResult)localObject3).fini();
          }
          catch (RptException localRptException1)
          {
            throw new AdminException(str1, 22, localRptException1);
          }
        }
      }
      else if (str2.equals("Positive Pay Activity"))
      {
        localObject3 = new ReportResult(localLocale);
        try
        {
          jdField_if(paramReportCriteria, localLocale, paramHashMap);
          ((ReportResult)localObject3).init(paramReportCriteria);
          ((ReportResult)localObject3).setHeading(null);
          ((ReportResult)localObject3).setSectionHeading(null);
          ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
          localReportDataDimensions.setNumRows(-1);
          localReportDataDimensions.setNumColumns(6);
          ((ReportResult)localObject3).setDataDimensions(localReportDataDimensions);
          localObject4 = new ReportColumn(localLocale);
          localObject5 = new ArrayList();
          ((ArrayList)localObject5).add(ReportConsts.getColumnName(10, localLocale));
          ((ReportColumn)localObject4).setLabels((ArrayList)localObject5);
          ((ReportColumn)localObject4).setDataType("java.lang.String");
          ((ReportColumn)localObject4).setWidthAsPercent(8);
          ((ReportResult)localObject3).addColumn((ReportColumn)localObject4);
          localObject6 = new ReportColumn(localLocale);
          localObject7 = new ArrayList();
          ((ArrayList)localObject7).add(ReportConsts.getColumnName(14, localLocale));
          ((ReportColumn)localObject6).setLabels((ArrayList)localObject7);
          ((ReportColumn)localObject6).setDataType("java.lang.String");
          ((ReportColumn)localObject6).setWidthAsPercent(5);
          ((ReportResult)localObject3).addColumn((ReportColumn)localObject6);
          localObject8 = new ReportColumn(localLocale);
          localObject9 = new ArrayList();
          ((ArrayList)localObject9).add(ReportConsts.getColumnName(11, localLocale));
          ((ReportColumn)localObject8).setLabels((ArrayList)localObject9);
          ((ReportColumn)localObject8).setDataType("java.lang.String");
          ((ReportColumn)localObject8).setWidthAsPercent(15);
          ((ReportResult)localObject3).addColumn((ReportColumn)localObject8);
          localObject10 = new ReportColumn(localLocale);
          localObject11 = new ArrayList();
          ((ArrayList)localObject11).add(ReportConsts.getColumnName(12, localLocale));
          ((ReportColumn)localObject10).setLabels((ArrayList)localObject11);
          ((ReportColumn)localObject10).setDataType("java.lang.String");
          ((ReportColumn)localObject10).setWidthAsPercent(26);
          ((ReportResult)localObject3).addColumn((ReportColumn)localObject10);
          localObject12 = new ReportColumn(localLocale);
          localObject13 = new ArrayList();
          ((ArrayList)localObject13).add(ReportConsts.getColumnName(15, localLocale));
          ((ReportColumn)localObject12).setLabels((ArrayList)localObject13);
          ((ReportColumn)localObject12).setDataType("java.lang.String");
          ((ReportColumn)localObject12).setWidthAsPercent(10);
          ((ReportResult)localObject3).addColumn((ReportColumn)localObject12);
          localObject14 = new ReportColumn(localLocale);
          localObject15 = new ArrayList();
          ((ArrayList)localObject15).add(ReportConsts.getColumnName(13, localLocale));
          ((ReportColumn)localObject14).setLabels((ArrayList)localObject15);
          ((ReportColumn)localObject14).setDataType("java.lang.String");
          ((ReportColumn)localObject14).setWidthAsPercent(30);
          ((ReportResult)localObject3).addColumn((ReportColumn)localObject14);
          a(paramSecureUser, localLocale, paramReportCriteria, null, (IReportResult)localObject3, paramHashMap);
        }
        catch (Exception localException2)
        {
          ((ReportResult)localObject3).setError(localException2);
          throw new AdminException(str1, 22, localException2);
        }
        finally
        {
          try
          {
            ((ReportResult)localObject3).fini();
          }
          catch (RptException localRptException2)
          {
            throw new AdminException(str1, 22, localRptException2);
          }
        }
      }
      else
      {
        throw new AdminException(str1, 11, str2 + " is not a valid report type");
      }
    }
    return localObject3;
  }
  
  public static IReportResult getBCReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria1, HashMap paramHashMap, ReportCriteria paramReportCriteria2)
    throws AdminException, BCReportException
  {
    String str1 = "ReportAuditAdapter.getBCReportData";
    try
    {
      Profile.isInitialized();
    }
    catch (ProfileException localProfileException)
    {
      throw new BCReportException(str1, 22, "ReportAuditAdapter:Unable to create DB connection pool");
    }
    Locale localLocale = null;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject1 = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = ((UserLocale)localObject1).getLocale();
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
    }
    Object localObject1 = paramReportCriteria1.getSearchCriteria();
    if (localObject1 == null)
    {
      localObject2 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str1, 10, (String)localObject2);
    }
    Object localObject2 = paramReportCriteria1.getReportOptions();
    if (localObject2 == null)
    {
      str2 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new AdminException(str1, 12, str2);
    }
    String str2 = ((Properties)localObject2).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      String str3 = "The report options contained within the Report Criteria used in a call to getAccountReportData does not contain a report type.";
      throw new AdminException(str1, 11, str3);
    }
    if (str2.equals("Business Service Charge Report")) {
      return a(paramSecureUser, localLocale, paramReportCriteria2, paramHashMap);
    }
    if (str2.equals("Consumer Service Charge Report")) {
      return jdField_if(paramSecureUser, localLocale, paramReportCriteria2, paramHashMap);
    }
    if (str2.equals("Bank Employee Activity Report"))
    {
      if (!a((Properties)localObject1, localLocale)) {
        throw new BCReportException(33);
      }
      try
      {
        a(localLocale, paramReportCriteria2, paramHashMap);
      }
      catch (Exception localException1)
      {
        DebugLog.log(Level.SEVERE, str1 + ": " + localException1.toString());
      }
      return retrieveBCReport(localLocale, paramReportCriteria2, paramHashMap);
    }
    if (str2.equals("CSR Activity Report"))
    {
      if (!a((Properties)localObject1, localLocale)) {
        throw new BCReportException(33);
      }
      try
      {
        a(localLocale, paramReportCriteria2, paramHashMap);
      }
      catch (Exception localException2)
      {
        DebugLog.log(Level.SEVERE, str1 + ": " + localException2.toString());
      }
      return retrieveBCReport(localLocale, paramReportCriteria2, paramHashMap);
    }
    ArrayList localArrayList;
    ReportResult localReportResult;
    if ((str2.equals("OBO CSR Activity Report")) || (str2.equals("OBO CSR Team Activity Report")) || (str2.equals("My OBO Activity Report")) || (str2.equals("User Activity Report")))
    {
      if (!a((Properties)localObject1, localLocale)) {
        throw new BCReportException(33);
      }
      localArrayList = (ArrayList)paramHashMap.get("UsersForReport");
      localReportResult = new ReportResult(localLocale);
      int i1 = 0;
      try
      {
        a(localLocale, paramReportCriteria2, paramHashMap);
        localReportResult.init(paramReportCriteria2);
        ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
        localReportDataDimensions.setNumRows(-1);
        boolean bool;
        int i2;
        if ((str2.equals("OBO CSR Activity Report")) || (str2.equals("My OBO Activity Report")) || (str2.equals("OBO CSR Team Activity Report")))
        {
          str5 = paramReportCriteria1.getSearchCriteria().getProperty("Business");
          bool = "AllConsumers".equals(str5);
          i2 = (!"AllConsumers".equals(str5)) && (!"AllBusinessesAndConsumers".equals(str5)) ? 1 : 0;
          if ((!bool) && (i2 == 0)) {
            localReportDataDimensions.setNumColumns(12);
          } else {
            localReportDataDimensions.setNumColumns(11);
          }
          localReportResult.setDataDimensions(localReportDataDimensions);
          jdField_if(localReportResult, localLocale, paramReportCriteria2);
        }
        else if (str2.equals("User Activity Report"))
        {
          str5 = ((Properties)localObject1).getProperty("Business");
          bool = "AllConsumers".equals(str5);
          i2 = (!"AllConsumers".equals(str5)) && (!"AllBusinessesAndConsumers".equals(str5)) ? 1 : 0;
          if ((!bool) && (i2 == 0)) {
            localReportDataDimensions.setNumColumns(11);
          } else {
            localReportDataDimensions.setNumColumns(10);
          }
          localReportResult.setDataDimensions(localReportDataDimensions);
          jdField_do(localReportResult, localLocale, paramReportCriteria1);
        }
        a(paramSecureUser, localLocale, paramReportCriteria1, localArrayList, localReportResult, paramHashMap);
      }
      catch (Exception localException4)
      {
        String str5 = "Unable to generate the report.";
        i1 = 1;
        localReportResult.setError(localException4);
        throw new BCReportException(str1, 19, str5);
      }
      finally
      {
        try
        {
          localReportResult.fini();
        }
        catch (RptException localRptException1)
        {
          String str6 = "Unable to generate the report.";
          if (i1 == 0) {
            throw new BCReportException(str1, 19, str6);
          }
        }
      }
      return localReportResult;
    }
    if ((str2.equals("Login Activity Report")) || (str2.equals("Login Summary Report")))
    {
      localArrayList = (ArrayList)paramHashMap.get("UsersForReport");
      localReportResult = new ReportResult(localLocale);
      try
      {
        a(localLocale, paramReportCriteria2, paramHashMap);
        localReportResult.init(paramReportCriteria2);
        if (str2.equals("Login Activity Report")) {
          a(localReportResult, localLocale, paramReportCriteria2);
        } else if (str2.equals("Login Summary Report")) {
          a(localReportResult, localLocale);
        }
        a(paramSecureUser, localLocale, paramReportCriteria1, localArrayList, localReportResult, paramHashMap);
      }
      catch (Exception localException3)
      {
        String str4 = "Unable to generate the report.";
        localReportResult.setError(localException3);
        throw new BCReportException(str1, 19, str4);
      }
      finally
      {
        try
        {
          localReportResult.fini();
        }
        catch (RptException localRptException2)
        {
          String str7 = "Unable to generate the report.";
          throw new BCReportException(str1, 19, str7);
        }
      }
      return localReportResult;
    }
    throw new AdminException(str1, 11, "Unknown report type '" + str2 + '\'');
  }
  
  public static ReportLogRecords getAuditEntries(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, DateTime paramDateTime1, DateTime paramDateTime2)
  {
    ReportLogRecords localReportLogRecords = new ReportLogRecords();
    String str = "AuditAdapter.getAuditEntries";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select a.USER_ID, a.AGENT_ID, a.AGENT_TYPE, a.DESCRIPTION, a.TRAN_ID, a.TRAN_TYPE, a.BUSINESS_ID, a.AMOUNT, a.CURRENCY_CODE, a.LOG_DATE, a.SRVR_TID, a.STATE, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM, a.MODULE from AUDIT_LOG a");
    boolean bool = false;
    try
    {
      bool = Profile.appendSQLSelectString(localStringBuffer, "a.USER_ID", paramString1, false, false, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "a.AGENT_ID", paramString2, false, false, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "a.AGENT_TYPE", paramString3, false, false, bool);
      java.sql.Date localDate1 = Profile.convertToDate(paramDateTime1);
      java.sql.Date localDate2 = Profile.getNextDay(Profile.convertToDate(paramDateTime2));
      bool = Profile.appendSQLSelectDate(localStringBuffer, "a.LOG_DATE", localDate1, ">=", bool);
      bool = Profile.appendSQLSelectDate(localStringBuffer, "a.LOG_DATE", localDate2, "<", bool);
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i1 = 1;
      i1 = Profile.setStatementString(localPreparedStatement, i1, paramString1, "a.USER_ID", true);
      i1 = Profile.setStatementString(localPreparedStatement, i1, paramString2, "a.AGENT_ID", true);
      i1 = Profile.setStatementString(localPreparedStatement, i1, paramString3, "a.AGENT_TYPE", true);
      i1 = Profile.setStatementDate(localPreparedStatement, i1, localDate1, true);
      i1 = Profile.setStatementDate(localPreparedStatement, i1, localDate2, true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        ReportLogRecord localReportLogRecord = new ReportLogRecord(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3), localResultSet.getString(4), localResultSet.getString(5), localResultSet.getInt(6), localResultSet.getInt(7), localResultSet.getBigDecimal(8), localResultSet.getString(9), localResultSet.getString(11), localResultSet.getString(12), localResultSet.getString(13), localResultSet.getString(14), localResultSet.getString(15), localResultSet.getString(16), localResultSet.getInt(17));
        Timestamp localTimestamp = localResultSet.getTimestamp(10);
        if (localTimestamp != null) {
          localReportLogRecord.setTranDate(new DateTime(localTimestamp, paramSecureUser.getLocale()));
        }
        localReportLogRecords.add(localReportLogRecord);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localException.toString());
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localReportLogRecords;
  }
  
  public static ReportLogRecords getAuditHistoryByTrackingId(SecureUser paramSecureUser, String paramString)
  {
    ReportLogRecords localReportLogRecords = new ReportLogRecords();
    String str1 = "AuditAdapter.getAuditEntries";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select a.USER_ID, a.AGENT_ID, a.AGENT_TYPE, a.DESCRIPTION, a.TRAN_ID, a.TRAN_TYPE, a.BUSINESS_ID, a.AMOUNT, a.CURRENCY_CODE, a.LOG_DATE, a.SRVR_TID, a.STATE, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM, a.MODULE FROM AUDIT_LOG a WHERE a.TRAN_ID=? ORDER BY a.LOG_DATE");
    int i1 = 0;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i2 = 1;
      i2 = Profile.setStatementString(localPreparedStatement, i2, paramString, "a.TRAN_ID", true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        ReportLogRecord localReportLogRecord = new ReportLogRecord(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3), localResultSet.getString(4), localResultSet.getString(5), localResultSet.getInt(6), localResultSet.getInt(7), localResultSet.getBigDecimal(8), localResultSet.getString(9), localResultSet.getString(11), localResultSet.getString(12), localResultSet.getString(13), localResultSet.getString(14), localResultSet.getString(15), localResultSet.getString(16), localResultSet.getInt(17));
        Timestamp localTimestamp = localResultSet.getTimestamp(10);
        if (localTimestamp != null)
        {
          localReportLogRecord.setTranDate(new DateTime(localTimestamp, paramSecureUser.getLocale()));
          localReportLogRecord.setTimestamp(localTimestamp.toString());
        }
        String str2 = getDisplayStatus(localReportLogRecord.getState(), paramSecureUser.getLocale(), "" + localReportLogRecord.getTranType());
        if ((str2 != null) && (!"-".equals(str2))) {
          localReportLogRecord.setState(str2);
        }
        String str3 = localResultSet.getString(1);
        if ((str3 != null) && (str3.trim().length() > 0))
        {
          int i3 = Integer.parseInt(str3.trim());
          try
          {
            User localUser = CustomerAdapter.getUserById(i3);
            localReportLogRecord.setProcessedBy(localUser.getUserName());
          }
          catch (Exception localException2)
          {
            localReportLogRecord.setProcessedBy("Unknown");
          }
        }
        else
        {
          localReportLogRecord.setProcessedBy("Bank Employee");
        }
        localReportLogRecords.add(localReportLogRecord);
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      DebugLog.log(Level.SEVERE, str1 + ": " + localException1.toString());
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localReportLogRecords;
  }
  
  private static void jdField_for(ReportCriteria paramReportCriteria, Locale paramLocale, HashMap paramHashMap)
    throws Exception
  {
    Connection localConnection = null;
    Iterator localIterator = null;
    Properties localProperties = null;
    Set localSet = null;
    String str1 = null;
    String str2 = null;
    String str3 = "ReportAuditAdapter.processEFSReportCriteria";
    String str4 = null;
    String str5 = null;
    localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      str2 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str3, 10, str2);
    }
    localSet = localProperties.keySet();
    localIterator = localSet.iterator();
    try
    {
      try
      {
        localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      }
      catch (Exception localException)
      {
        throw new Exception("ReportAuditAdapter:Unable to get DB Connection.");
      }
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str5 = localProperties.getProperty(str1);
        if ("User".equals(str1))
        {
          if ((str5.equals("")) || (str5.equals("AllUsers")))
          {
            paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, paramLocale));
          }
          else
          {
            str4 = jdField_do(localConnection, str5, paramLocale);
            paramReportCriteria.setDisplayValue("User", str4);
          }
        }
        else if ((str5 == null) || (str5.length() <= 0) || (str5.trim().length() <= 0)) {
          paramReportCriteria.setHiddenSearchCriterion(str1, true);
        }
      }
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws AdminException
  {
    ReportResult localReportResult = null;
    Locale localLocale = null;
    ReportColumn localReportColumn = null;
    ReportDataDimensions localReportDataDimensions = null;
    String str = "ReportAuditAdapter.getReportData";
    Object localObject1;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject1 = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = ((UserLocale)localObject1).getLocale();
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
    }
    localReportResult = new ReportResult(localLocale);
    try
    {
      jdField_for(paramReportCriteria, localLocale, paramHashMap);
      localReportResult.init(paramReportCriteria);
      localReportResult.setHeading(null);
      localReportResult.setSectionHeading(null);
      localReportDataDimensions = new ReportDataDimensions(localLocale);
      localReportDataDimensions.setNumRows(-1);
      localReportDataDimensions.setNumColumns(8);
      localReportResult.setDataDimensions(localReportDataDimensions);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(10, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(8);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(14, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(5);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(11, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(15);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(12, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(15);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(15, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(10);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(17, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(12);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(18, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(12);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(localLocale);
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(ReportConsts.getColumnName(13, localLocale));
      localReportColumn.setLabels((ArrayList)localObject1);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(20);
      localReportResult.addColumn(localReportColumn);
      a(paramSecureUser, localLocale, paramReportCriteria, localReportResult, paramHashMap);
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      throw new AdminException(str, 22, localException);
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        throw new AdminException(str, 22, localRptException);
      }
    }
    return localReportResult;
  }
  
  private static void a(SecureUser paramSecureUser, Locale paramLocale, ReportCriteria paramReportCriteria, IReportResult paramIReportResult, HashMap paramHashMap)
    throws AdminException
  {
    Connection localConnection = null;
    DateFormat localDateFormat1 = null;
    DateFormat localDateFormat2 = null;
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    int i1 = -1;
    int i2 = 0;
    Integer localInteger = null;
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    String str1 = localProperties2.getProperty("DATEFORMAT");
    String str2 = null;
    String str3 = null;
    String str4 = localProperties2.getProperty("MAXDISPLAYSIZE");
    String str5 = null;
    String str6 = null;
    String str7 = "ReportAuditAdapter.obtainSessionReceiptRecordsFromCriteria";
    String str8 = localProperties2.getProperty("TIMEFORMAT");
    String str9 = null;
    String str10 = null;
    StringTokenizer localStringTokenizer = null;
    Vector localVector = a(localReportSortCriteria, localProperties2.getProperty("REPORTTYPE"));
    str3 = localProperties1.getProperty("EndDate");
    str6 = localProperties1.getProperty("StartDate");
    str10 = localProperties1.getProperty("User");
    try
    {
      localDateTime2 = jdField_if(str6, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
      localDateTime1 = jdField_if(str3, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
    }
    catch (ParseException localParseException)
    {
      throw new AdminException(str7, 13, "The start date/end date is not in proper format.");
    }
    if (str1 == null)
    {
      if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
      {
        UserLocale localUserLocale = (UserLocale)paramHashMap.get("UserLocale");
        str2 = localUserLocale.getDateFormat();
      }
      else
      {
        str2 = "MM/dd/yyyy";
      }
      localDateFormat1 = DateFormatUtil.getFormatter(str2, paramLocale);
    }
    else
    {
      localDateFormat1 = DateFormatUtil.getFormatter(str1, paramLocale);
    }
    if (str8 == null) {
      localDateFormat2 = DateFormatUtil.getFormatter("HH:mm", paramLocale);
    } else {
      localDateFormat2 = DateFormatUtil.getFormatter(str8, paramLocale);
    }
    try
    {
      localInteger = Integer.valueOf(str4);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    str5 = String.valueOf(paramSecureUser.getPrimaryUserID());
    localStringTokenizer = new StringTokenizer(str10, ",", false);
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      while ((localStringTokenizer.hasMoreTokens()) && ((i1 == -1) || (i2 < i1)))
      {
        str9 = localStringTokenizer.nextToken();
        i2 = a(localConnection, paramSecureUser, paramLocale, str9, str5, localDateTime2, localDateTime1, paramIReportResult, localVector, i2, localDateFormat1, localDateFormat2, i1, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str7 + ": " + localException.toString());
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static int a(Connection paramConnection, SecureUser paramSecureUser, Locale paramLocale, String paramString1, String paramString2, DateTime paramDateTime1, DateTime paramDateTime2, IReportResult paramIReportResult, Vector paramVector, int paramInt1, DateFormat paramDateFormat1, DateFormat paramDateFormat2, int paramInt2, HashMap paramHashMap)
  {
    boolean bool = false;
    int i1 = 1;
    PreparedStatement localPreparedStatement = null;
    ReportDataItems localReportDataItems = null;
    ReportRow localReportRow = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = "ReportAuditAdapter.getAuditEntriesForSessionReceiptReport";
    String str5 = null;
    String str6 = null;
    StringBuffer localStringBuffer = new StringBuffer("SELECT a.LOG_DATE, cust.first_name, cust.last_name, a.TRAN_TYPE, a.TRAN_ID, a.FROM_ACCT_RTGNUM, a.FROM_ACCT_ID, a.TO_ACCT_RTGNUM, a.TO_ACCT_ID, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id");
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    Timestamp localTimestamp3 = null;
    try
    {
      if ((paramString1 != null) && (paramString1.length() > 0)) {
        bool = Profile.appendSQLSelectInt(localStringBuffer, "a.USER_ID_INT", paramString1, bool);
      }
      if ((paramString2 != null) && (paramString2.length() > 0)) {
        bool = Profile.appendSQLSelectInt(localStringBuffer, "a.PRIMARY_USER_ID", paramString2, bool);
      }
      localTimestamp1 = Profile.convertToTimestamp(paramDateTime1);
      bool = Profile.appendSQLSelectDate(localStringBuffer, "a.LOG_DATE", localTimestamp1, ">=", bool);
      localTimestamp3 = Profile.convertToTimestamp(paramDateTime2);
      bool = Profile.appendSQLSelectDate(localStringBuffer, "a.LOG_DATE", localTimestamp3, "<=", bool);
      if ((paramVector != null) && (paramVector.size() != 0))
      {
        localStringBuffer.append(" ORDER BY ");
        localStringBuffer.append((String)paramVector.get(0));
        for (int i2 = 1; i2 < paramVector.size(); i2++)
        {
          localStringBuffer.append(", ");
          localStringBuffer.append((String)paramVector.get(i2));
        }
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      if ((paramString1 != null) && (paramString1.length() != 0)) {
        i1 = Profile.setStatementInt(localPreparedStatement, i1, paramString1, false);
      }
      if ((paramString2 != null) && (paramString2.length() != 0)) {
        i1 = Profile.setStatementInt(localPreparedStatement, i1, paramString2, false);
      }
      i1 = Profile.setStatementDate(localPreparedStatement, i1, localTimestamp1, true);
      i1 = Profile.setStatementDate(localPreparedStatement, i1, localTimestamp3, true);
      if (paramInt2 != -1) {
        localPreparedStatement.setMaxRows(paramInt2);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while ((localResultSet.next()) && ((paramInt2 == -1) || (paramInt1 < paramInt2)))
      {
        localReportDataItems = new ReportDataItems(paramLocale);
        localTimestamp2 = localResultSet.getTimestamp(1);
        localReportDataItems.add().setData(paramDateFormat1.format(localTimestamp2));
        localReportDataItems.add().setData(paramDateFormat2.format(localTimestamp2));
        localReportDataItems.add().setData(a(localResultSet.getString(2), localResultSet.getString(3)));
        str1 = localResultSet.getString(4);
        if (str1 != null) {
          localReportDataItems.add().setData(AuditLogUtil.getActivityNameFromID(str1, paramLocale));
        } else {
          localReportDataItems.add().setData("");
        }
        localReportDataItems.add().setData(localResultSet.getString(5));
        str3 = localResultSet.getString(6);
        str2 = localResultSet.getString(7);
        str6 = localResultSet.getString(8);
        str5 = localResultSet.getString(9);
        localReportDataItems.add().setData(a(str3, str2, paramLocale));
        localReportDataItems.add().setData(a(str6, str5, paramLocale));
        localReportDataItems.add().setData(localResultSet.getString(10));
        localReportRow = new ReportRow(paramLocale);
        localReportRow.setDataItems(localReportDataItems);
        if (paramInt1 % 2 != 0) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        ((ReportResult)paramIReportResult).addRow(localReportRow);
        paramInt1++;
      }
      if ((paramInt2 != -1) && (paramInt1 >= paramInt2))
      {
        HashMap localHashMap = ((ReportResult)paramIReportResult).getProperties();
        if (localHashMap == null)
        {
          localHashMap = new HashMap();
          ((ReportResult)paramIReportResult).setProperties(localHashMap);
        }
        localHashMap.put("TRUNCATED", new Integer(paramInt2));
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str4 + ": " + localException.toString());
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return paramInt1;
  }
  
  private static String jdField_do(Connection paramConnection, String paramString, Locale paramLocale)
  {
    int i1 = 0;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = "ReportAuditAdapter.getUserNames";
    StringBuffer localStringBuffer = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      str1 = "";
    } else {
      try
      {
        str2 = " select c.first_name, c.last_name, c.user_name, cd.cust_id from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.directory_id IN (" + DBUtil.generateSQLNumericList(new StringTokenizer(paramString, ",", false), new StringBuffer()) + ") ORDER BY c.directory_id";
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
        localStringBuffer = new StringBuffer();
        while (localResultSet.next())
        {
          str3 = UserUtil.getFullNameWithLoginId(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3), localResultSet.getString(4), paramLocale);
          if (i1 != 0)
          {
            localStringBuffer.append(", " + str3);
          }
          else
          {
            i1 = 1;
            localStringBuffer.append(str3);
          }
        }
        DBUtil.commit(paramConnection);
        str1 = localStringBuffer.toString();
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, str4 + ": " + localException.toString());
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
    return str1;
  }
  
  private static void a(SecureUser paramSecureUser, Locale paramLocale, ReportCriteria paramReportCriteria, ArrayList paramArrayList, IReportResult paramIReportResult, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "obtainRecordsFromCriteria";
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str2 = localProperties2.getProperty("REPORTTYPE");
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    Object localObject7 = null;
    int i1 = 0;
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    Object localObject8 = null;
    int i2 = 0;
    String[] arrayOfString = null;
    if (str2.equals("On Behalf Of Report")) {
      arrayOfString = z;
    } else if ((str2.equals("System Activity Report")) || (str2.equals("Session Activity Report"))) {
      arrayOfString = s;
    } else if (str2.equals("User Activity Report")) {
      arrayOfString = F;
    } else if ((str2.equals("OBO CSR Activity Report")) || (str2.equals("OBO CSR Team Activity Report"))) {
      arrayOfString = q;
    } else if (str2.equals("My OBO Activity Report")) {
      arrayOfString = E;
    } else if ((str2.equals("Login Activity Report")) || (str2.equals("Login Summary Report"))) {
      arrayOfString = jdField_else;
    } else if (str2.equals("Positive Pay Activity")) {
      arrayOfString = g;
    }
    if ((str2.equals("System Activity Report")) || (str2.equals("Session Activity Report")) || (str2.equals("On Behalf Of Report")) || (str2.equals("Positive Pay Activity")))
    {
      localObject9 = Integer.toString(paramSecureUser.getBusinessID());
      localArrayList1.add(localObject9);
    }
    Object localObject9 = localProperties1.keys();
    for (int i3 = 1; ((Enumeration)localObject9).hasMoreElements(); i3 = 1)
    {
      localObject10 = (String)((Enumeration)localObject9).nextElement();
      for (int i4 = 0; i4 < arrayOfString.length; i4++) {
        if (((String)localObject10).equals(arrayOfString[i4]))
        {
          i3 = 0;
          break;
        }
      }
      if (i3 == 0) {}
    }
    Object localObject10 = localProperties1.keySet();
    Iterator localIterator1 = ((Set)localObject10).iterator();
    String str3 = null;
    String str4 = null;
    StringBuffer localStringBuffer3;
    StringBuffer localStringBuffer2;
    while (localIterator1.hasNext())
    {
      str3 = (String)localIterator1.next();
      str4 = localProperties1.getProperty(str3);
      Object localObject11;
      StringBuffer localStringBuffer1;
      if (str3.equals("Business"))
      {
        if ((str4.equals("")) || (str4.equals("AllBusinesses")) || (str4.equals("AllBusinessesAndConsumers")))
        {
          if (paramHashMap == null) {
            throw new AdminException(str1, 20, "Extra Hashmap is null.");
          }
          localObject11 = (ArrayList)paramHashMap.get("BusinessesForReport");
          if (localObject11 == null)
          {
            if (str4.equals("AllBusinesses")) {
              localArrayList1.add("!NULL");
            } else {
              localArrayList1.add(null);
            }
          }
          else
          {
            localStringBuffer1 = new StringBuffer();
            if (str4.equals("AllBusinessesAndConsumers")) {
              localStringBuffer1.append("NULL");
            }
            int i7 = 0;
            for (StringBuffer localStringBuffer4 = 0; localStringBuffer4 < ((ArrayList)localObject11).size(); localStringBuffer4++)
            {
              localStringBuffer3 = localStringBuffer4;
              if (localStringBuffer3 == 1000)
              {
                localStringBuffer3 = 0;
                localStringBuffer1.deleteCharAt(localStringBuffer1.length() - 1);
                localArrayList1.add(localStringBuffer1.toString());
                localStringBuffer1 = new StringBuffer();
                i7 = 0;
              }
              else if (((ArrayList)localObject11).get(localStringBuffer4) != null)
              {
                localStringBuffer1.append((String)((ArrayList)localObject11).get(localStringBuffer4));
                localStringBuffer1.append(",");
                i7 = 1;
              }
            }
            if (i7 != 0) {
              localStringBuffer1.deleteCharAt(localStringBuffer1.length() - 1);
            }
            localArrayList1.add(localStringBuffer1.toString());
          }
        }
        else if (str4.equals("AllConsumers"))
        {
          localArrayList1.add("NULL");
        }
        else
        {
          localArrayList1.add(str4);
        }
      }
      else if (str3.equals("User"))
      {
        if ((str4.equals("")) || (str4.equals("AllUsers")))
        {
          localObject11 = new StringBuffer();
          if (paramArrayList == null)
          {
            localArrayList2.add(null);
          }
          else
          {
            for (localStringBuffer3 = 0; localStringBuffer3 < paramArrayList.size(); localStringBuffer3++)
            {
              localStringBuffer1 = localStringBuffer3;
              if (localStringBuffer1 == 1000)
              {
                int i5 = 0;
                ((StringBuffer)localObject11).deleteCharAt(((StringBuffer)localObject11).length() - 1);
                localArrayList2.add(((StringBuffer)localObject11).toString());
                localObject11 = new StringBuffer();
              }
              else
              {
                ((StringBuffer)localObject11).append((String)paramArrayList.get(localStringBuffer3));
                ((StringBuffer)localObject11).append(",");
              }
            }
            if (paramArrayList.size() > 0) {
              ((StringBuffer)localObject11).deleteCharAt(((StringBuffer)localObject11).length() - 1);
            }
            localArrayList2.add(((StringBuffer)localObject11).toString());
          }
        }
        else
        {
          localArrayList2.add(str4);
        }
      }
      else if (str3.equals("StartDate")) {
        localObject4 = str4;
      } else if (str3.equals("EndDate")) {
        localObject7 = str4;
      } else if (str3.equals("TranId")) {
        localObject6 = str4;
      } else if (str3.equals("Agent"))
      {
        if ((str4.equals("")) || (str4.equals("AllAgents")))
        {
          if (paramHashMap == null) {
            throw new AdminException("ReportAuditAdapter.getReportData", 23, "Extra Hashmap is null.");
          }
          localObject11 = (BankEmployees)paramHashMap.get("BankEmployeesForReport");
          if (localObject11 == null) {
            throw new AdminException("ReportAuditAdapter.getReportData", 23, "No BankEmployees defined in extra HashMap.");
          }
          localStringBuffer2 = new StringBuffer();
          for (localStringBuffer3 = 0; localStringBuffer3 < ((BankEmployees)localObject11).size(); localStringBuffer3++)
          {
            localStringBuffer2.append(((BankEmployee)((BankEmployees)localObject11).get(localStringBuffer3)).getId());
            if (localStringBuffer3 != ((BankEmployees)localObject11).size() - 1) {
              localStringBuffer2.append(",");
            }
          }
          localObject1 = localStringBuffer2.toString();
        }
        else
        {
          localObject1 = str4;
        }
      }
      else if (str3.equals("TransactionType"))
      {
        if ((!str4.equals("")) && (!str4.equals("AllTransactions"))) {
          localObject2 = str4;
        }
      }
      else if (str3.equals("Module"))
      {
        if ((!str4.equals("")) && (!str4.equals("AllModules"))) {
          localObject8 = str4;
        }
      }
      else if ((str3.equals("Affiliate Bank")) && (!str4.equals("")) && (!str4.equals("AllAffiliateBanks"))) {
        try
        {
          i2 = Integer.parseInt(str4);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new AdminException("ReportAuditAdapter.getReportData", 10, "Specified affiliate bank is not valid.");
        }
      }
    }
    if (str2.equals("OBO CSR Team Activity Report"))
    {
      if (paramHashMap == null) {
        throw new AdminException("ReportAuditAdapter.getReportData", 23, "Extra Hashmap is null.");
      }
      BankEmployees localBankEmployees = (BankEmployees)paramHashMap.get("BankEmployeesForReport");
      if (localBankEmployees == null) {
        throw new AdminException("ReportAuditAdapter.getReportData", 23, "No BankEmployees defined in extra HashMap.");
      }
      localStringBuffer2 = new StringBuffer();
      for (localStringBuffer3 = 0; localStringBuffer3 < localBankEmployees.size(); localStringBuffer3++)
      {
        localStringBuffer2.append(((BankEmployee)localBankEmployees.get(localStringBuffer3)).getId());
        if (localStringBuffer3 != localBankEmployees.size() - 1) {
          localStringBuffer2.append(",");
        }
      }
      localObject1 = localStringBuffer2.toString();
    }
    try
    {
      localDateTime1 = jdField_if(localObject4, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
      localDateTime2 = jdField_if(localObject7, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
    }
    catch (ParseException localParseException)
    {
      throw new AdminException("ReportAuditAdapter.obtainRecordsFromCriteria", 13, "The start date/end date is not in proper format.");
    }
    Vector localVector = a(localReportSortCriteria, str2);
    int i6 = 0;
    Integer localInteger = null;
    String str5 = localProperties2.getProperty("MAXDISPLAYSIZE");
    try
    {
      localInteger = Integer.valueOf(str5);
    }
    catch (NumberFormatException localNumberFormatException2) {}
    int i8 = -1;
    if (localInteger != null) {
      i8 = localInteger.intValue();
    }
    if ((localArrayList1.size() == 0) && (localArrayList2.size() == 0))
    {
      a(paramSecureUser, paramLocale, str2, "", "", (String)localObject1, localObject2, localDateTime1, localDateTime2, localObject6, localObject8, i2, localVector, paramHashMap, paramIReportResult, localProperties2, i6, paramReportCriteria);
    }
    else
    {
      String str6;
      Object localObject12;
      if ((localArrayList1.size() == 0) && (localArrayList2.size() != 0))
      {
        str6 = null;
        localObject12 = localArrayList2.iterator();
        while (((Iterator)localObject12).hasNext())
        {
          str6 = (String)((Iterator)localObject12).next();
          a(paramSecureUser, paramLocale, str2, "", str6, (String)localObject1, localObject2, localDateTime1, localDateTime2, localObject6, localObject8, i2, localVector, paramHashMap, paramIReportResult, localProperties2, i6, paramReportCriteria);
          if ((i8 != -1) && (i6 >= i8)) {
            break;
          }
        }
      }
      else if ((localArrayList1.size() != 0) && (localArrayList2.size() == 0))
      {
        str6 = null;
        localObject12 = localArrayList1.iterator();
        while (((Iterator)localObject12).hasNext())
        {
          str6 = (String)((Iterator)localObject12).next();
          a(paramSecureUser, paramLocale, str2, str6, "", (String)localObject1, localObject2, localDateTime1, localDateTime2, localObject6, localObject8, i2, localVector, paramHashMap, paramIReportResult, localProperties2, i6, paramReportCriteria);
          if ((i8 != -1) && (i6 >= i8)) {
            break;
          }
        }
      }
      else
      {
        str6 = null;
        localObject12 = null;
        Iterator localIterator2 = localArrayList1.iterator();
        while (localIterator2.hasNext())
        {
          str6 = (String)localIterator2.next();
          Iterator localIterator3 = localArrayList2.iterator();
          while (localIterator3.hasNext())
          {
            localObject12 = (String)localIterator3.next();
            a(paramSecureUser, paramLocale, str2, str6, (String)localObject12, (String)localObject1, localObject2, localDateTime1, localDateTime2, localObject6, localObject8, i2, localVector, paramHashMap, paramIReportResult, localProperties2, i6, paramReportCriteria);
            if ((i8 != -1) && (i6 >= i8)) {
              break;
            }
          }
          if ((i8 != -1) && (i6 >= i8)) {
            break;
          }
        }
      }
    }
  }
  
  private static void a(SecureUser paramSecureUser, Locale paramLocale, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, DateTime paramDateTime1, DateTime paramDateTime2, String paramString6, String paramString7, int paramInt1, Vector paramVector, HashMap paramHashMap, IReportResult paramIReportResult, Properties paramProperties, int paramInt2, ReportCriteria paramReportCriteria)
  {
    String str1 = "";
    String str2 = "com.ffusion.beans.user.resources";
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject1 = (UserLocale)paramHashMap.get("UserLocale");
      str1 = ((UserLocale)localObject1).getDateFormat();
    }
    else
    {
      str1 = "MM/dd/yyyy";
    }
    Object localObject1 = "ReportAuditAdapter.getAuditEntriesForReport";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = null;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if ((paramString1.equals("System Activity Report")) || (paramString1.equals("Session Activity Report"))) {
      localStringBuffer = new StringBuffer("SELECT a.LOG_DATE, cust.first_name, cust.last_name, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_RTGNUM, a.FROM_ACCT_ID, a.TO_ACCT_RTGNUM, a.TO_ACCT_ID, a.DESCRIPTION  FROM AUDIT_LOG a LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id");
    } else if (paramString1.equals("On Behalf Of Report")) {
      localStringBuffer = new StringBuffer("SELECT a.LOG_DATE, cust.first_name, cust.last_name, be.first_name, be.last_name, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_RTGNUM, a.FROM_ACCT_ID, a.TO_ACCT_RTGNUM, a.TO_ACCT_ID, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN bank_employee be ON a.AGENT_ID_INT=be.employee_id LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id");
    } else if ((paramString1.equals("OBO CSR Activity Report")) || (paramString1.equals("OBO CSR Team Activity Report")) || (paramString1.equals("My OBO Activity Report"))) {
      localStringBuffer = new StringBuffer("SELECT a.LOG_DATE, be.first_name, be.last_name, be.user_name, be.bank_employee_id, bus.business_name,  pri.first_name, pri.last_name,  cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM,  a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN bank_employee be ON a.AGENT_ID_INT=be.employee_id  LEFT OUTER JOIN trans_type_desc t on a.TRAN_TYPE=t.id left outer join business bus on a.BUSINESS_ID=bus.business_id LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id=cr.PRIMARY_USER_ID");
    } else if (paramString1.equals("User Activity Report")) {
      localStringBuffer = new StringBuffer("SELECT a.LOG_DATE, bus.business_name,  pri.first_name, pri.last_name,  cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.FROM_ACCT_ID,  a.FROM_ACCT_RTGNUM, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id LEFT OUTER JOIN business bus ON a.BUSINESS_ID=bus.business_id  LEFT OUTER JOIN customer cust ON a.USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id=cr.PRIMARY_USER_ID ");
    } else if (paramString1.equals("Positive Pay Activity")) {
      localStringBuffer = new StringBuffer("SELECT a.LOG_DATE, cust.first_name, cust.last_name, a.TRAN_TYPE, a.TRAN_ID, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN customer cust ON a.USER_ID_INT = cust.directory_id LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id");
    } else {
      localStringBuffer = new StringBuffer("select a.USER_ID, a.AGENT_ID, a.AGENT_TYPE, a.DESCRIPTION, a.TRAN_ID, a.TRAN_TYPE, a.BUSINESS_ID, a.AMOUNT, a.CURRENCY_CODE, a.LOG_DATE, a.SRVR_TID, a.STATE, a.TO_ACCT_ID, a.TO_ACCT_RTGNUM, a.FROM_ACCT_ID, a.FROM_ACCT_RTGNUM, a.MODULE from AUDIT_LOG a");
    }
    String str3 = paramProperties.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    String str4 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", paramLocale);
    String str5 = paramProperties.getProperty("TIMEFORMAT");
    DateFormat localDateFormat1 = null;
    DateFormat localDateFormat2 = null;
    if (str4 == null) {
      localDateFormat1 = DateFormatUtil.getFormatter(str1, paramLocale);
    } else {
      localDateFormat1 = DateFormatUtil.getFormatter(str4, paramLocale);
    }
    if (str5 == null) {
      localDateFormat2 = DateFormatUtil.getFormatter("HH:mm", paramLocale);
    } else {
      localDateFormat2 = DateFormatUtil.getFormatter(str5, paramLocale);
    }
    try
    {
      localInteger = Integer.valueOf(str3);
    }
    catch (NumberFormatException localNumberFormatException) {}
    int i1 = -1;
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    boolean bool1 = false;
    try
    {
      Object localObject2;
      Object localObject3;
      if (paramString1.equals("Login Summary Report"))
      {
        if (paramDateTime2 == null) {
          paramDateTime2 = new DateTime();
        }
        if (paramDateTime1 == null) {
          throw new AdminException((String)localObject1, 13, " The start date for a login summary report has not been specified.");
        }
        localObject2 = Calendar.getInstance();
        Calendar localCalendar = Calendar.getInstance();
        localObject3 = Calendar.getInstance();
        ((Calendar)localObject2).setTime(paramDateTime1.getTime());
        localCalendar.setTime(paramDateTime1.getTime());
        a(localCalendar);
        if (localCalendar.getTime().getTime() < paramDateTime2.getTime().getTime())
        {
          paramInt2 = a(paramSecureUser, paramLocale, paramString1, paramString2, paramString3, new DateTime((Calendar)localObject2, paramLocale), new DateTime(localCalendar, paramLocale), paramString6, paramVector, paramHashMap, paramIReportResult, i1, paramInt2, paramInt1, paramReportCriteria);
          ((Calendar)localObject3).setTime(paramDateTime2.getTime());
          ((Calendar)localObject3).add(5, -7);
          a((Calendar)localObject3);
          localCalendar.add(5, 7);
          ((Calendar)localObject2).setTime(localCalendar.getTime());
          ((Calendar)localObject2).add(5, -6);
          jdField_if((Calendar)localObject2);
          while (localCalendar.getTime().getTime() <= ((Calendar)localObject3).getTime().getTime())
          {
            ((Calendar)localObject2).setTime(localCalendar.getTime());
            ((Calendar)localObject2).add(5, -6);
            jdField_if((Calendar)localObject2);
            paramInt2 = a(paramSecureUser, paramLocale, paramString1, paramString2, paramString3, new DateTime((Calendar)localObject2, paramLocale), new DateTime(localCalendar, paramLocale), paramString6, paramVector, paramHashMap, paramIReportResult, i1, paramInt2, paramInt1, paramReportCriteria);
            localCalendar.add(5, 7);
          }
          ((Calendar)localObject2).setTime(((Calendar)localObject3).getTime());
          ((Calendar)localObject2).add(5, 1);
          jdField_if((Calendar)localObject2);
          if (((Calendar)localObject2).getTime().getTime() < paramDateTime2.getTime().getTime()) {
            paramInt2 = a(paramSecureUser, paramLocale, paramString1, paramString2, paramString3, new DateTime((Calendar)localObject2, paramLocale), paramDateTime2, paramString6, paramVector, paramHashMap, paramIReportResult, i1, paramInt2, paramInt1, paramReportCriteria);
          }
        }
        else
        {
          paramInt2 = a(paramSecureUser, paramLocale, paramString1, paramString2, paramString3, paramDateTime1, paramDateTime2, paramString6, paramVector, paramHashMap, paramIReportResult, i1, paramInt2, paramInt1, paramReportCriteria);
        }
      }
      else if (paramString1.equals("Login Activity Report"))
      {
        paramInt2 = a(paramSecureUser, paramLocale, paramString1, paramString2, paramString3, paramDateTime1, paramDateTime2, paramString6, paramVector, paramHashMap, paramIReportResult, i1, paramInt2, paramInt1, paramReportCriteria);
      }
      else
      {
        localObject2 = paramReportCriteria.getSearchCriteria().getProperty("IncludeSecondaryUsers");
        if ((!paramString1.equals("Positive Pay Activity")) && (paramString3 != null))
        {
          if (paramString3.trim().length() == 0)
          {
            localStringBuffer.append(" WHERE a.USER_ID = ' ' ");
            bool1 = true;
          }
          else if ((localObject2 != null) && (((String)localObject2).equals("on")))
          {
            if (localStringBuffer != null)
            {
              i2 = Integer.parseInt(paramString3);
              if (i2 != 0)
              {
                if (bool1) {
                  localStringBuffer.append(" and ");
                } else if (localStringBuffer.toString().indexOf("where") == -1) {
                  localStringBuffer.append(" where ");
                }
                localStringBuffer.append("( ");
                localStringBuffer.append("a.PRIMARY_USER_ID");
                localStringBuffer.append(" = ? or ");
                localStringBuffer.append("a.USER_ID");
                localStringBuffer.append(" = ? ) ");
                bool1 = true;
              }
            }
          }
          else
          {
            bool1 = Profile.appendSQLSelectString(localStringBuffer, "", "a.USER_ID", paramString3, false, false, bool1, false);
          }
        }
        else if ((localObject2 != null) && ((((String)localObject2).equals("off")) || (((String)localObject2).equals(""))) && (localStringBuffer != null))
        {
          if (bool1)
          {
            localStringBuffer.append(" and ");
          }
          else if (localStringBuffer.toString().indexOf("where") == -1)
          {
            localStringBuffer.append(" where ");
            bool1 = true;
          }
          localStringBuffer.append("( a.PRIMARY_USER_ID = a.USER_ID_INT ) ");
        }
        if ((paramString1.equals("OBO CSR Activity Report")) || (paramString1.equals("OBO CSR Team Activity Report")) || (paramString1.equals("My OBO Activity Report")))
        {
          if (!bool1)
          {
            localStringBuffer.append(" where ");
            bool1 = true;
          }
          else
          {
            localStringBuffer.append(" and ");
          }
          localStringBuffer.append("a.USER_ID");
          localStringBuffer.append(" != ' ' ");
          bool1 = true;
        }
        int i2 = 0;
        if (paramString5 != null)
        {
          localObject3 = new StringTokenizer(paramString5, ",");
          i2 = 1;
          while ((((StringTokenizer)localObject3).hasMoreTokens()) && (i2 != 0))
          {
            localObject4 = ((StringTokenizer)localObject3).nextToken();
            i3 = Profile.convertToInt((String)localObject4);
            if (!Profile.isValidId(i3)) {
              i2 = 0;
            }
          }
        }
        if (i2 != 0) {
          bool1 = Profile.appendSQLSelectInt(localStringBuffer, "a.TRAN_TYPE", paramString5, bool1);
        }
        if ((paramString1.equals("System Activity Report")) || (paramString1.equals("Session Activity Report")))
        {
          bool1 = Profile.appendSQLSelectString(localStringBuffer, "", "a.AGENT_ID", paramString4, false, false, bool1, false);
        }
        else if (paramString1.equals("User Activity Report"))
        {
          if (bool1)
          {
            localStringBuffer.append(" and  ");
          }
          else
          {
            localStringBuffer.append(" where ");
            bool1 = true;
          }
          localStringBuffer.append("a.USER_ID_INT");
          localStringBuffer.append(" is not null ");
        }
        else if (!paramString1.equals("Positive Pay Activity"))
        {
          if ((paramString4 != null) && (paramString4.trim().length() > 0))
          {
            bool1 = Profile.appendSQLSelectString(localStringBuffer, "", "a.AGENT_ID", paramString4, false, false, bool1, false);
          }
          else
          {
            if (!bool1)
            {
              localStringBuffer.append(" where ");
              bool1 = true;
            }
            else
            {
              localStringBuffer.append(" and ");
            }
            localStringBuffer.append("a.AGENT_ID");
            localStringBuffer.append(" is not null ");
          }
        }
        if (paramString2 != null)
        {
          if (paramString2.trim().length() == 0) {
            return;
          }
          if (bool1)
          {
            localStringBuffer.append(" and ");
          }
          else
          {
            localStringBuffer.append(" where ");
            bool1 = true;
          }
          if (paramString2.startsWith("NULL"))
          {
            paramString2 = paramString2.substring(4);
            if (paramString2.trim().length() > 0) {
              localStringBuffer.append("(");
            }
            localStringBuffer.append("a.BUSINESS_ID");
            localStringBuffer.append(" IS NULL");
            if (paramString2.trim().length() > 0)
            {
              localStringBuffer.append(" OR ");
              localStringBuffer.append(DBUtil.generateSQLNumericInClause(paramString2, "a.BUSINESS_ID"));
              localStringBuffer.append(")");
            }
          }
          else if (paramString2.startsWith("!NULL"))
          {
            localStringBuffer.append("(a.BUSINESS_ID");
            localStringBuffer.append(" IS NOT NULL OR (a.USER_ID_INT=0 and a.BUSINESS_ID is null))");
          }
          else
          {
            localStringBuffer.append(DBUtil.generateSQLNumericInClause(paramString2, "a.BUSINESS_ID"));
          }
        }
        if (paramInt1 != 0)
        {
          if (bool1)
          {
            localStringBuffer.append(" and ");
          }
          else
          {
            localStringBuffer.append(" where ");
            bool1 = true;
          }
          localStringBuffer.append("(");
          localStringBuffer.append("bus.affiliate_bank_id");
          localStringBuffer.append("=");
          localStringBuffer.append(paramInt1);
          localStringBuffer.append(" or ");
          localStringBuffer.append("cust.affiliate_bank_id");
          localStringBuffer.append("=");
          localStringBuffer.append(paramInt1);
          localStringBuffer.append(") ");
        }
        localObject3 = null;
        Object localObject4 = null;
        localObject3 = Profile.convertToTimestamp(paramDateTime1);
        bool1 = Profile.appendSQLSelectDate(localStringBuffer, "a.LOG_DATE", (java.util.Date)localObject3, ">=", bool1);
        localObject4 = Profile.convertToTimestamp(paramDateTime2);
        bool1 = Profile.appendSQLSelectDate(localStringBuffer, "a.LOG_DATE", (java.util.Date)localObject4, "<=", bool1);
        bool1 = Profile.appendSQLSelectString(localStringBuffer, "a.TRAN_ID", paramString6, bool1);
        bool1 = Profile.appendSQLSelectInt(localStringBuffer, "a.MODULE", paramString7, bool1);
        if ((paramVector != null) && (paramVector.size() != 0))
        {
          localStringBuffer.append(" ORDER BY ");
          localStringBuffer.append((String)paramVector.get(0));
          for (i3 = 1; i3 < paramVector.size(); i3++)
          {
            localStringBuffer.append(", ");
            localStringBuffer.append((String)paramVector.get(i3));
          }
        }
        localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        int i3 = 1;
        if (paramString3 != null) {
          if ((localObject2 != null) && (((String)localObject2).equals("on")))
          {
            i3 = Profile.setStatementInt(localPreparedStatement, i3, paramString3, true);
            i3 = Profile.setStatementString(localPreparedStatement, i3, paramString3, "a.USER_ID", false, false, true, false);
          }
          else
          {
            i3 = Profile.setStatementString(localPreparedStatement, i3, paramString3, "a.USER_ID", false, false, true, false);
          }
        }
        if (i2 != 0) {
          i3 = Profile.setStatementInt(localPreparedStatement, i3, paramString5, true);
        }
        if ((paramString1.equals("System Activity Report")) || (paramString1.equals("Session Activity Report")) || (paramString1.equals("User Activity Report")) || ((paramString4 != null) && (paramString4.trim().length() > 0))) {
          i3 = Profile.setStatementString(localPreparedStatement, i3, paramString4, "a.AGENT_ID", false, false, true, false);
        }
        i3 = Profile.setStatementDate(localPreparedStatement, i3, localObject3, true);
        i3 = Profile.setStatementDate(localPreparedStatement, i3, localObject4, true);
        i3 = Profile.setStatementString(localPreparedStatement, i3, paramString6, "a.TRAN_ID", true);
        i3 = Profile.setStatementInt(localPreparedStatement, i3, paramString7, true);
        if (i1 != -1) {
          localPreparedStatement.setMaxRows(i1);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        String str7;
        boolean bool2;
        int i8;
        int i9;
        Object localObject9;
        Object localObject10;
        Object localObject11;
        Object localObject12;
        Object localObject13;
        String str8;
        String str6;
        Object localObject14;
        String str9;
        Object localObject5;
        Object localObject16;
        if ((paramString1.equals("OBO CSR Activity Report")) || (paramString1.equals("OBO CSR Team Activity Report")) || (paramString1.equals("My OBO Activity Report")))
        {
          str7 = paramReportCriteria.getSearchCriteria().getProperty("Business");
          bool2 = "AllConsumers".equals(str7);
          i8 = (!"AllConsumers".equals(str7)) && (!"AllBusinessesAndConsumers".equals(str7)) ? 1 : 0;
          for (i9 = 1; localResultSet.next(); i9++)
          {
            localObject9 = new ReportDataItems(paramLocale);
            localObject10 = localResultSet.getTimestamp(1);
            if (localObject10 != null)
            {
              ((ReportDataItems)localObject9).add().setData(localDateFormat1.format((java.util.Date)localObject10));
              ((ReportDataItems)localObject9).add().setData(localDateFormat2.format((java.util.Date)localObject10));
            }
            else
            {
              ((ReportDataItems)localObject9).add().setData("");
              ((ReportDataItems)localObject9).add().setData("");
            }
            localObject11 = localResultSet.getString(2);
            localObject12 = localResultSet.getString(3);
            localObject13 = localResultSet.getString(4);
            str8 = localResultSet.getString(5);
            if (localObject13 != null) {
              ((ReportDataItems)localObject9).add().setData(UserUtil.getFullNameWithLoginId((String)localObject11, (String)localObject12, (String)localObject13, str8, paramLocale));
            } else {
              ((ReportDataItems)localObject9).add().setData("");
            }
            str6 = localResultSet.getString(6);
            if (!bool2) {
              ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : str6);
            }
            localObject11 = localResultSet.getString(9);
            localObject12 = localResultSet.getString(10);
            localObject13 = localResultSet.getString(11);
            localObject14 = localResultSet.getString(12);
            if (localObject13 != null) {
              ((ReportDataItems)localObject9).add().setData(UserUtil.getFullNameWithLoginId((String)localObject11, (String)localObject12, (String)localObject13, (String)localObject14, paramLocale));
            } else {
              ((ReportDataItems)localObject9).add().setData("");
            }
            String str10;
            if (i8 == 0)
            {
              str9 = localResultSet.getString(7);
              str10 = localResultSet.getString(8);
              if ((str9 != null) && (str10 != null)) {
                ((ReportDataItems)localObject9).add().setData(UserUtil.getFullName(str9, str10, paramLocale));
              } else {
                ((ReportDataItems)localObject9).add().setData("");
              }
            }
            str9 = String.valueOf(localResultSet.getInt(13));
            try
            {
              str10 = ResourceUtil.getString("TransactionTypeText" + str9, "com.ffusion.beans.banking.resources", paramLocale);
              ((ReportDataItems)localObject9).add().setData(str10);
            }
            catch (Exception localException3)
            {
              ((ReportDataItems)localObject9).add().setData(str9);
            }
            str6 = localResultSet.getString(14);
            ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : str6);
            str6 = localResultSet.getString(15);
            ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : getDisplayStatus(str6, paramLocale, str9));
            str6 = localResultSet.getString(16);
            localObject5 = localResultSet.getString(17);
            ((ReportDataItems)localObject9).add().setData(a((String)localObject5, str6, paramLocale));
            str6 = localResultSet.getString(18);
            localObject5 = localResultSet.getString(19);
            ((ReportDataItems)localObject9).add().setData(a((String)localObject5, str6, paramLocale));
            str6 = localResultSet.getString(20);
            ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : str6);
            localObject16 = new ReportRow(paramLocale);
            if (i9 % 2 == 0) {
              ((ReportRow)localObject16).set("CELLBACKGROUND", "reportDataShaded");
            }
            ((ReportRow)localObject16).setDataItems((ReportDataItems)localObject9);
            ((ReportResult)paramIReportResult).addRow((ReportRow)localObject16);
            paramInt2++;
            if ((i1 != -1) && (paramInt2 >= i1))
            {
              HashMap localHashMap = ((ReportResult)paramIReportResult).getProperties();
              if (localHashMap == null)
              {
                localHashMap = new HashMap();
                ((ReportResult)paramIReportResult).setProperties(localHashMap);
              }
              localHashMap.put("TRUNCATED", new Integer(i1));
              return;
            }
          }
        }
        else
        {
          Object localObject15;
          if (paramString1.equals("User Activity Report"))
          {
            str7 = localProperties.getProperty("Business");
            bool2 = "AllConsumers".equals(str7);
            i8 = (!"AllConsumers".equals(str7)) && (!"AllBusinessesAndConsumers".equals(str7)) ? 1 : 0;
            for (i9 = 1; localResultSet.next(); i9++)
            {
              localObject9 = new ReportDataItems(paramLocale);
              localObject10 = localResultSet.getTimestamp(1);
              if (localObject10 != null)
              {
                ((ReportDataItems)localObject9).add().setData(localDateFormat1.format((java.util.Date)localObject10));
                ((ReportDataItems)localObject9).add().setData(localDateFormat2.format((java.util.Date)localObject10));
              }
              else
              {
                ((ReportDataItems)localObject9).add().setData("");
                ((ReportDataItems)localObject9).add().setData("");
              }
              str6 = localResultSet.getString(2);
              if (!bool2) {
                ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : str6);
              }
              localObject11 = localResultSet.getString(5);
              localObject12 = localResultSet.getString(6);
              localObject13 = localResultSet.getString(7);
              str8 = localResultSet.getString(8);
              if (localObject13 != null) {
                ((ReportDataItems)localObject9).add().setData(UserUtil.getFullNameWithLoginId((String)localObject11, (String)localObject12, (String)localObject13, str8, paramLocale));
              } else {
                ((ReportDataItems)localObject9).add().setData("");
              }
              if (i8 == 0)
              {
                localObject14 = localResultSet.getString(3);
                str9 = localResultSet.getString(4);
                if ((localObject14 != null) || (str9 != null)) {
                  ((ReportDataItems)localObject9).add().setData(UserUtil.getFullName((String)localObject14, str9, paramLocale));
                } else {
                  ((ReportDataItems)localObject9).add().setData("");
                }
              }
              localObject14 = String.valueOf(localResultSet.getInt(9));
              try
              {
                str9 = ResourceUtil.getString("TransactionTypeText" + (String)localObject14, "com.ffusion.beans.banking.resources", paramLocale);
                ((ReportDataItems)localObject9).add().setData(str9);
              }
              catch (Exception localException2)
              {
                ((ReportDataItems)localObject9).add().setData(localObject14);
              }
              str6 = localResultSet.getString(10);
              ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : str6);
              str6 = localResultSet.getString(11);
              ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : getDisplayStatus(str6, paramLocale, (String)localObject14));
              str6 = localResultSet.getString(12);
              localObject5 = localResultSet.getString(13);
              ((ReportDataItems)localObject9).add().setData(a((String)localObject5, str6, paramLocale));
              str6 = localResultSet.getString(14);
              localObject5 = localResultSet.getString(15);
              ((ReportDataItems)localObject9).add().setData(a((String)localObject5, str6, paramLocale));
              str6 = localResultSet.getString(16);
              ((ReportDataItems)localObject9).add().setData(str6 == null ? "" : str6);
              localObject15 = new ReportRow(paramLocale);
              if (i9 % 2 == 0) {
                ((ReportRow)localObject15).set("CELLBACKGROUND", "reportDataShaded");
              }
              ((ReportRow)localObject15).setDataItems((ReportDataItems)localObject9);
              ((ReportResult)paramIReportResult).addRow((ReportRow)localObject15);
              paramInt2++;
              if ((i1 != -1) && (paramInt2 >= i1))
              {
                localObject16 = ((ReportResult)paramIReportResult).getProperties();
                if (localObject16 == null)
                {
                  localObject16 = new HashMap();
                  ((ReportResult)paramIReportResult).setProperties((HashMap)localObject16);
                }
                ((HashMap)localObject16).put("TRUNCATED", new Integer(i1));
                return;
              }
            }
          }
          else
          {
            ReportDataItems localReportDataItems;
            Object localObject7;
            Object localObject8;
            if (paramString1.equals("System Activity Report"))
            {
              for (int i5 = 1; localResultSet.next(); i5++)
              {
                localReportDataItems = new ReportDataItems(paramLocale);
                localObject7 = localResultSet.getTimestamp(1);
                localReportDataItems.add().setData(localDateFormat1.format((java.util.Date)localObject7));
                localReportDataItems.add().setData(localDateFormat2.format((java.util.Date)localObject7));
                localObject8 = localResultSet.getString(2);
                localObject9 = localResultSet.getString(3);
                if ((localObject9 != null) || (localObject8 != null)) {
                  localReportDataItems.add().setData(UserUtil.getFullName((String)localObject8, (String)localObject9, paramLocale));
                } else {
                  localReportDataItems.add().setData("");
                }
                localObject10 = localResultSet.getString(4);
                if (localObject10 != null)
                {
                  localObject11 = AuditLogUtil.getActivityNameFromID((String)localObject10, paramLocale);
                  localReportDataItems.add().setData(localObject11);
                }
                else
                {
                  localReportDataItems.add().setData("");
                }
                localReportDataItems.add().setData(localResultSet.getString(5));
                str6 = localResultSet.getString(6);
                localReportDataItems.add().setData(str6 == null ? "" : getDisplayStatus(str6, paramLocale, (String)localObject10));
                str6 = localResultSet.getString(7);
                localObject5 = localResultSet.getString(8);
                localReportDataItems.add().setData(a(str6, (String)localObject5, paramLocale));
                str6 = localResultSet.getString(9);
                localObject5 = localResultSet.getString(10);
                localReportDataItems.add().setData(a(str6, (String)localObject5, paramLocale));
                localReportDataItems.add().setData(localResultSet.getString(11));
                localObject11 = new ReportRow(paramLocale);
                if (i5 % 2 == 0) {
                  ((ReportRow)localObject11).set("CELLBACKGROUND", "reportDataShaded");
                }
                ((ReportRow)localObject11).setDataItems(localReportDataItems);
                ((ReportResult)paramIReportResult).addRow((ReportRow)localObject11);
                paramInt2++;
                if ((i1 != -1) && (paramInt2 >= i1))
                {
                  localObject12 = ((ReportResult)paramIReportResult).getProperties();
                  if (localObject12 == null)
                  {
                    localObject12 = new HashMap();
                    ((ReportResult)paramIReportResult).setProperties((HashMap)localObject12);
                  }
                  ((HashMap)localObject12).put("TRUNCATED", new Integer(i1));
                  return;
                }
              }
            }
            else if (paramString1.equals("Session Activity Report"))
            {
              ArrayList localArrayList = new ArrayList();
              while (localResultSet.next())
              {
                localReportDataItems = new ReportDataItems(paramLocale);
                localObject7 = localResultSet.getTimestamp(1);
                localReportDataItems.add().setData(localDateFormat1.format((java.util.Date)localObject7));
                localReportDataItems.add().setData(localDateFormat2.format((java.util.Date)localObject7));
                localObject8 = localResultSet.getString(2);
                localObject9 = localResultSet.getString(3);
                if ((localObject9 != null) || (localObject8 != null)) {
                  localReportDataItems.add().setData(UserUtil.getFullName((String)localObject8, (String)localObject9, paramLocale));
                } else {
                  localReportDataItems.add().setData("");
                }
                localObject10 = localResultSet.getString(4);
                if (localObject10 != null)
                {
                  localObject11 = AuditLogUtil.getActivityNameFromID((String)localObject10, paramLocale);
                  localReportDataItems.add().setData(localObject11);
                }
                else
                {
                  localReportDataItems.add().setData("");
                }
                localReportDataItems.add().setData(localResultSet.getString(5));
                str6 = localResultSet.getString(6);
                localReportDataItems.add().setData(str6 == null ? "" : getDisplayStatus(str6, paramLocale, (String)localObject10));
                str6 = localResultSet.getString(7);
                localObject5 = localResultSet.getString(8);
                localReportDataItems.add().setData(a(str6, (String)localObject5, paramLocale));
                str6 = localResultSet.getString(9);
                localObject5 = localResultSet.getString(10);
                localReportDataItems.add().setData(a(str6, (String)localObject5, paramLocale));
                localReportDataItems.add().setData(localResultSet.getString(11));
                localObject11 = new ReportRow(paramLocale);
                ((ReportRow)localObject11).setDataItems(localReportDataItems);
                localArrayList.add(localObject11);
                if (((String)localObject10).equals(String.valueOf(3201))) {
                  break;
                }
              }
              for (int i7 = localArrayList.size() - 1; i7 >= 0; i7--)
              {
                localObject7 = (ReportRow)localArrayList.get(i7);
                if ((localArrayList.size() - i7) % 2 == 0) {
                  ((ReportRow)localObject7).set("CELLBACKGROUND", "reportDataShaded");
                }
                ((ReportResult)paramIReportResult).addRow((ReportRow)localObject7);
                paramInt2++;
                if ((i1 != -1) && (paramInt2 >= i1))
                {
                  localObject8 = ((ReportResult)paramIReportResult).getProperties();
                  if (localObject8 == null)
                  {
                    localObject8 = new HashMap();
                    ((ReportResult)paramIReportResult).setProperties((HashMap)localObject8);
                  }
                  ((HashMap)localObject8).put("TRUNCATED", new Integer(i1));
                  return;
                }
              }
            }
            else
            {
              Object localObject6;
              if (paramString1.equals("On Behalf Of Report")) {
                for (int i6 = 1; localResultSet.next(); i6++)
                {
                  localObject6 = new ReportDataItems(paramLocale);
                  localObject7 = localResultSet.getTimestamp(1);
                  localObject8 = new DateTime((java.util.Date)localObject7, paramLocale);
                  ((ReportDataItems)localObject6).add().setData(localObject8);
                  localObject9 = localResultSet.getString(2);
                  localObject10 = localResultSet.getString(3);
                  if ((localObject10 != null) || (localObject9 != null)) {
                    ((ReportDataItems)localObject6).add().setData(UserUtil.getFullName((String)localObject9, (String)localObject10, paramLocale));
                  } else {
                    ((ReportDataItems)localObject6).add().setData("");
                  }
                  localObject11 = localResultSet.getString(4);
                  localObject12 = localResultSet.getString(5);
                  localObject13 = new StringBuffer();
                  if ((localObject11 != null) || (localObject12 != null)) {
                    ((ReportDataItems)localObject6).add().setData(UserUtil.getFullName((String)localObject11, (String)localObject12, paramLocale));
                  } else {
                    ((ReportDataItems)localObject6).add().setData("");
                  }
                  str8 = localResultSet.getString(6);
                  if (str8 != null)
                  {
                    localObject14 = AuditLogUtil.getActivityNameFromID(str8, paramLocale);
                    ((ReportDataItems)localObject6).add().setData(localObject14);
                  }
                  else
                  {
                    ((ReportDataItems)localObject6).add().setData("");
                  }
                  ((ReportDataItems)localObject6).add().setData(localResultSet.getString(7));
                  str6 = localResultSet.getString(8);
                  ((ReportDataItems)localObject6).add().setData(str6 == null ? "" : getDisplayStatus(str6, paramLocale, str8));
                  str6 = localResultSet.getString(9);
                  localObject5 = localResultSet.getString(10);
                  ((ReportDataItems)localObject6).add().setData(a(str6, (String)localObject5, paramLocale));
                  str6 = localResultSet.getString(11);
                  localObject5 = localResultSet.getString(12);
                  ((ReportDataItems)localObject6).add().setData(a(str6, (String)localObject5, paramLocale));
                  ((ReportDataItems)localObject6).add().setData(localResultSet.getString(13));
                  localObject14 = new ReportRow(paramLocale);
                  if (i6 % 2 == 0) {
                    ((ReportRow)localObject14).set("CELLBACKGROUND", "reportDataShaded");
                  }
                  ((ReportRow)localObject14).setDataItems((ReportDataItems)localObject6);
                  ((ReportResult)paramIReportResult).addRow((ReportRow)localObject14);
                  paramInt2++;
                  if ((i1 != -1) && (paramInt2 >= i1))
                  {
                    localObject15 = ((ReportResult)paramIReportResult).getProperties();
                    if (localObject15 == null)
                    {
                      localObject15 = new HashMap();
                      ((ReportResult)paramIReportResult).setProperties((HashMap)localObject15);
                    }
                    ((HashMap)localObject15).put("TRUNCATED", new Integer(i1));
                    return;
                  }
                }
              } else if (paramString1.equals("Positive Pay Activity")) {
                for (int i4 = 1; localResultSet.next(); i4++)
                {
                  localObject5 = new ReportDataItems(paramLocale);
                  Timestamp localTimestamp = localResultSet.getTimestamp(1);
                  ((ReportDataItems)localObject5).add().setData(localDateFormat1.format(localTimestamp));
                  ((ReportDataItems)localObject5).add().setData(localDateFormat2.format(localTimestamp));
                  localObject6 = localResultSet.getString(2);
                  localObject7 = localResultSet.getString(3);
                  if ((localObject7 != null) || (localObject6 != null)) {
                    ((ReportDataItems)localObject5).add().setData(UserUtil.getFullName((String)localObject6, (String)localObject7, paramLocale));
                  } else {
                    ((ReportDataItems)localObject5).add().setData("");
                  }
                  localObject8 = localResultSet.getString(4);
                  if (localObject8 != null)
                  {
                    localObject9 = AuditLogUtil.getActivityNameFromID((String)localObject8, paramLocale);
                    ((ReportDataItems)localObject5).add().setData(localObject9);
                  }
                  else
                  {
                    ((ReportDataItems)localObject5).add().setData("");
                  }
                  ((ReportDataItems)localObject5).add().setData(localResultSet.getString(5));
                  ((ReportDataItems)localObject5).add().setData(localResultSet.getString(6));
                  localObject9 = new ReportRow(paramLocale);
                  if (i4 % 2 == 0) {
                    ((ReportRow)localObject9).set("CELLBACKGROUND", "reportDataShaded");
                  }
                  ((ReportRow)localObject9).setDataItems((ReportDataItems)localObject5);
                  ((ReportResult)paramIReportResult).addRow((ReportRow)localObject9);
                  paramInt2++;
                  if ((i1 != -1) && (paramInt2 >= i1))
                  {
                    localObject10 = ((ReportResult)paramIReportResult).getProperties();
                    if (localObject10 == null)
                    {
                      localObject10 = new HashMap();
                      ((ReportResult)paramIReportResult).setProperties((HashMap)localObject10);
                    }
                    ((HashMap)localObject10).put("TRUNCATED", new Integer(i1));
                    return;
                  }
                }
              }
            }
          }
        }
      }
    }
    catch (Exception localException1)
    {
      DebugLog.log(Level.SEVERE, (String)localObject1 + ": " + localException1.toString());
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static int a(SecureUser paramSecureUser, Locale paramLocale, String paramString1, String paramString2, String paramString3, DateTime paramDateTime1, DateTime paramDateTime2, String paramString4, Vector paramVector, HashMap paramHashMap, IReportResult paramIReportResult, int paramInt1, int paramInt2, int paramInt3, ReportCriteria paramReportCriteria)
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    int i1 = 0;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject1 = (UserLocale)paramHashMap.get("UserLocale");
      str1 = ((UserLocale)localObject1).getDateFormat();
      str2 = ((UserLocale)localObject1).getTimeFormat();
    }
    else
    {
      str1 = "MM/dd/yyyy";
      str2 = "HH:mm";
    }
    Object localObject1 = "ReportAuditAdapter.getLoginEntriesForReport";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool1 = true;
    StringBuffer localStringBuffer1 = null;
    StringBuffer localStringBuffer2 = new StringBuffer();
    StringBuffer localStringBuffer3 = new StringBuffer();
    StringBuffer localStringBuffer4 = new StringBuffer();
    StringBuffer localStringBuffer5 = new StringBuffer();
    String str4 = new Integer(3202).toString();
    String str5 = new Integer(3201).toString();
    ReportResult localReportResult = (ReportResult)paramIReportResult;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    localTimestamp1 = Profile.convertToTimestamp(paramDateTime1);
    localTimestamp2 = Profile.convertToTimestamp(paramDateTime2);
    try
    {
      if ((paramString2 != null) && (paramString2.trim().length() > 0)) {
        if (paramString2.startsWith("NULL"))
        {
          localStringBuffer2.append(" and (");
          localStringBuffer2.append("a.BUSINESS_ID");
          localStringBuffer2.append(" IS NULL");
          str6 = paramString2.substring(4);
          if (str6.trim().length() > 0)
          {
            localStringBuffer2.append(" OR ");
            a(localStringBuffer2, "a.BUSINESS_ID", str6, false);
          }
          localStringBuffer2.append(") ");
        }
        else if (paramString2.startsWith("!NULL"))
        {
          localStringBuffer2.append(" and (");
          localStringBuffer2.append("a.BUSINESS_ID");
          localStringBuffer2.append(" IS NOT NULL OR (a.USER_ID_INT=0 and a.BUSINESS_ID is null))");
        }
        else
        {
          a(localStringBuffer2, "a.BUSINESS_ID", paramString2, true);
        }
      }
      String str6 = paramReportCriteria.getSearchCriteria().getProperty("IncludeSecondaryUsers");
      if (paramString3 != null)
      {
        if ((str6 != null) && (str6.equals("on")))
        {
          if (localStringBuffer3 != null)
          {
            i2 = Integer.parseInt(paramString3);
            if (i2 != 0)
            {
              if (bool1) {
                localStringBuffer3.append(" and ");
              } else if (localStringBuffer3.toString().indexOf("where") == -1) {
                localStringBuffer3.append(" where ");
              }
              localStringBuffer3.append("( ");
              localStringBuffer3.append("a.PRIMARY_USER_ID");
              localStringBuffer3.append(" = ? or ");
              localStringBuffer3.append("a.USER_ID");
              localStringBuffer3.append(" = ? ) ");
            }
          }
        }
        else {
          Profile.appendSQLSelectString(localStringBuffer3, "", "a.USER_ID", paramString3, false, false, bool1, false);
        }
      }
      else if ((str6 != null) && ((str6.equals("off")) || (str6.equals(""))) && (localStringBuffer3 != null))
      {
        if (bool1)
        {
          localStringBuffer3.append(" and ");
        }
        else if (localStringBuffer3.toString().indexOf("where") == -1)
        {
          localStringBuffer3.append(" where ");
          bool1 = true;
        }
        localStringBuffer3.append("( a.PRIMARY_USER_ID = a.USER_ID_INT ) ");
      }
      if (paramString1.equals("Login Activity Report"))
      {
        localStringBuffer1 = new StringBuffer("select bus.business_name, cust.user_name, a.TRAN_TYPE, a.LOG_DATE, a.DESCRIPTION, pri.first_name, pri.last_name from AUDIT_LOG a ");
        localStringBuffer1.append("  LEFT OUTER JOIN business bus ON  a.BUSINESS_ID=bus.business_id ");
        localStringBuffer1.append("  LEFT OUTER JOIN customer cust ON  cust.directory_id=a.USER_ID_INT  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id = cr.DIRECTORY_ID LEFT OUTER JOIN customer pri ON pri.directory_id = cr.PRIMARY_USER_ID ");
        localStringBuffer1.append(" WHERE ");
        localStringBuffer1.append("a.TRAN_TYPE");
        localStringBuffer1.append(" IN (");
        localStringBuffer1.append(str5);
        localStringBuffer1.append(" , ");
        localStringBuffer1.append(str4);
        localStringBuffer1.append(") ");
      }
      else if (paramString1.equals("Login Summary Report"))
      {
        localStringBuffer1 = new StringBuffer("select pass.Total, fail.Total from (select count(*) as Total from (select BUSINESS_ID as biz, USER_ID_INT as user_id from AUDIT_LOG a ");
        localStringBuffer1.append(" WHERE ");
        a(localStringBuffer1, "a.TRAN_TYPE", "=", str5, false, false);
        Profile.appendSQLSelectDate2(localStringBuffer4, "a.LOG_DATE", localTimestamp1, ">=", true);
        Profile.appendSQLSelectDate2(localStringBuffer4, "a.LOG_DATE", localTimestamp2, "<=", true);
        localStringBuffer1.append(localStringBuffer4.toString());
        localStringBuffer1.append(localStringBuffer2.toString());
        localStringBuffer1.append(localStringBuffer3.toString());
        localStringBuffer1.append(")auditpass ");
        if (paramInt3 != 0)
        {
          localStringBuffer1.append("  LEFT OUTER JOIN business bus ON  auditpass.biz=bus.business_id ");
          localStringBuffer1.append("  LEFT OUTER JOIN customer cust ON  cust.directory_id=auditpass.user_id  ");
        }
      }
      if ((paramString2 != null) && (paramString2.trim().length() == 0))
      {
        i2 = paramInt2;
        return i2;
      }
      if (("Login Activity Report".equals(paramString1)) && (paramString2 != null) && (paramString2.trim().length() > 0)) {
        localStringBuffer1.append(localStringBuffer2.toString());
      }
      if (paramString1.equals("Login Activity Report"))
      {
        Profile.appendSQLSelectDate2(localStringBuffer4, "a.LOG_DATE", localTimestamp1, ">=", true);
        Profile.appendSQLSelectDate2(localStringBuffer4, "a.LOG_DATE", localTimestamp2, "<=", true);
        localStringBuffer1.append(localStringBuffer4.toString());
      }
      if (paramInt3 != 0)
      {
        if ("Login Activity Report".equals(paramString1)) {
          localStringBuffer5.append(" and (");
        } else {
          localStringBuffer5.append(" where (");
        }
        localStringBuffer5.append("cust.affiliate_bank_id");
        localStringBuffer5.append(" = ");
        localStringBuffer5.append(paramInt3);
        localStringBuffer5.append(" or ");
        localStringBuffer5.append("bus.affiliate_bank_id");
        localStringBuffer5.append(" = ");
        localStringBuffer5.append(paramInt3);
        localStringBuffer5.append(") ");
      }
      if (paramString4 != null) {
        a(localStringBuffer5, "a.TRAN_ID", "=", paramString4, true, true);
      }
      if (paramString1.equals("Login Activity Report"))
      {
        localStringBuffer1.append(localStringBuffer3.toString());
        localStringBuffer1.append(localStringBuffer5.toString());
      }
      else if (paramString1.equals("Login Summary Report"))
      {
        localStringBuffer1.append(localStringBuffer5.toString());
        localStringBuffer1.append(") pass , (select count(*) as Total from (select BUSINESS_ID as biz, USER_ID_INT as user_id from AUDIT_LOG a ");
        localStringBuffer1.append(" WHERE ");
        a(localStringBuffer1, "a.TRAN_TYPE", "=", str4, false, false);
        localStringBuffer1.append(localStringBuffer4.toString());
        localStringBuffer1.append(localStringBuffer2.toString());
        localStringBuffer1.append(localStringBuffer3.toString());
        localStringBuffer1.append(")auditfail ");
        if (paramInt3 != 0)
        {
          localStringBuffer1.append("  LEFT OUTER JOIN business bus ON  auditfail.biz=bus.business_id ");
          localStringBuffer1.append("  LEFT OUTER JOIN customer cust ON  cust.directory_id=auditfail.user_id  ");
        }
        localStringBuffer1.append(localStringBuffer5.toString());
        localStringBuffer1.append(")fail ");
      }
      if ((paramVector != null) && (paramVector.size() != 0))
      {
        localStringBuffer1.append(" ORDER BY ");
        for (i2 = 0; i2 < paramVector.size(); i2++)
        {
          localStringBuffer1.append(str3);
          localStringBuffer1.append((String)paramVector.get(i2));
          if (paramVector.get(i2).equals("a.LOG_DATE ASC")) {
            i1 = 1;
          }
          str3 = ", ";
        }
        if ((i1 == 0) && (paramString1.equals("Login Activity Report"))) {
          localStringBuffer1.append(", a.LOG_DATE ASC");
        }
      }
      else if (paramString1.equals("Login Activity Report"))
      {
        localStringBuffer1.append(" ORDER BY a.LOG_DATE ASC");
        i1 = 1;
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      if (("Login Activity Report".equals(paramString1)) && (paramInt1 > 0)) {
        localPreparedStatement.setMaxRows(paramInt1);
      }
      int i2 = 1;
      if (paramString3 != null)
      {
        if ((str6 != null) && (str6.equals("on")))
        {
          i2 = Profile.setStatementInt(localPreparedStatement, i2, paramString3, true);
          i2 = Profile.setStatementString(localPreparedStatement, i2, paramString3, "a.USER_ID", false, false, true, false);
        }
        else
        {
          i2 = Profile.setStatementString(localPreparedStatement, i2, paramString3, "a.USER_ID", false, false, true, false);
        }
        if (paramString1.equals("Login Summary Report")) {
          if ((str6 != null) && (str6.equals("on")))
          {
            i2 = Profile.setStatementInt(localPreparedStatement, i2, paramString3, true);
            i2 = Profile.setStatementString(localPreparedStatement, i2, paramString3, "a.USER_ID", false, false, true, false);
          }
          else
          {
            i2 = Profile.setStatementString(localPreparedStatement, i2, paramString3, "a.USER_ID", false, false, true, false);
          }
        }
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat(str1);
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(str2);
      DecimalFormat localDecimalFormat = (DecimalFormat)DecimalFormat.getInstance(paramLocale);
      localDecimalFormat.setMinimumFractionDigits(2);
      localDecimalFormat.setMaximumFractionDigits(2);
      int i5;
      Object localObject3;
      Object localObject4;
      Object localObject2;
      if (paramString1.equals("Login Summary Report"))
      {
        int i3 = 0;
        int i4 = 0;
        i5 = 0;
        float f1 = 0.0F;
        float f2 = 0.0F;
        while ((localResultSet.next()) && ((paramInt1 <= 0) || ((paramInt1 > 0) && (paramInt2 < paramInt1))))
        {
          i3 = localResultSet.getInt(1);
          i4 = localResultSet.getInt(2);
          i5 = i3 + i4;
          if (i5 != 0)
          {
            f1 = 100.0F * (i3 / i5);
            f2 = 100.0F * (i4 / i5);
          }
          localObject3 = new ReportDataItems(paramLocale);
          ((ReportDataItems)localObject3).add().setData(localSimpleDateFormat1.format(localTimestamp1));
          ((ReportDataItems)localObject3).add().setData(localSimpleDateFormat1.format(localTimestamp2));
          ((ReportDataItems)localObject3).add().setData(String.valueOf(i5));
          ((ReportDataItems)localObject3).add().setData(String.valueOf(i3));
          ((ReportDataItems)localObject3).add().setData(localDecimalFormat.format(f1).toString());
          ((ReportDataItems)localObject3).add().setData(String.valueOf(i4));
          ((ReportDataItems)localObject3).add().setData(localDecimalFormat.format(f2).toString());
          localObject4 = new ReportRow(paramLocale);
          if (paramInt2 % 2 == 1) {
            ((ReportRow)localObject4).set("CELLBACKGROUND", "reportDataShaded");
          }
          ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject3);
          localReportResult.addRow((ReportRow)localObject4);
          paramInt2++;
        }
      }
      else if (paramString1.equals("Login Activity Report"))
      {
        localObject2 = paramReportCriteria.getSearchCriteria().getProperty("Business");
        boolean bool2 = "AllConsumers".equals(localObject2);
        i5 = (!"AllConsumers".equals(localObject2)) && (!"AllBusinessesAndConsumers".equals(localObject2)) ? 1 : 0;
        for (int i6 = 1; (localResultSet.next()) && ((paramInt1 <= 0) || ((paramInt1 > 0) && (paramInt2 < paramInt1))); i6++)
        {
          ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
          localObject3 = localResultSet.getTimestamp(4);
          if (localObject3 != null)
          {
            localReportDataItems.add().setData(localSimpleDateFormat1.format((java.util.Date)localObject3));
            localReportDataItems.add().setData(localSimpleDateFormat2.format((java.util.Date)localObject3));
          }
          else
          {
            localReportDataItems.add().setData("");
            localReportDataItems.add().setData("");
          }
          localObject4 = localResultSet.getString(1);
          if (!bool2) {
            localReportDataItems.add().setData(localObject4);
          }
          String str7 = localResultSet.getString(2);
          localReportDataItems.add().setData(str7);
          String str8;
          if (i5 == 0)
          {
            str8 = localResultSet.getString(6);
            localObject5 = localResultSet.getString(7);
            if ((str8 != null) || (localObject5 != null)) {
              localReportDataItems.add().setData(UserUtil.getFullName(str8, (String)localObject5, paramLocale));
            } else {
              localReportDataItems.add().setData("");
            }
          }
          if (localResultSet.getInt(3) == 3201) {
            try
            {
              str8 = ResourceUtil.getString("LoginStatusSuccessful", "com.ffusion.beans.banking.resources", paramLocale);
              localReportDataItems.add().setData(str8);
            }
            catch (Exception localException2)
            {
              localReportDataItems.add().setData("Successful");
            }
          } else {
            try
            {
              String str9 = ResourceUtil.getString("LoginStatusFailed", "com.ffusion.beans.banking.resources", paramLocale);
              localReportDataItems.add().setData(str9);
            }
            catch (Exception localException3)
            {
              localReportDataItems.add().setData("Failed");
            }
          }
          String str10 = localResultSet.getString(5);
          if (str10 != null) {
            localReportDataItems.add().setData(str10);
          } else {
            localReportDataItems.add().setData("");
          }
          Object localObject5 = new ReportRow(paramLocale);
          if (i6 % 2 == 0) {
            ((ReportRow)localObject5).set("CELLBACKGROUND", "reportDataShaded");
          }
          ((ReportRow)localObject5).setDataItems(localReportDataItems);
          localReportResult.addRow((ReportRow)localObject5);
          paramInt2++;
        }
      }
      if ((paramInt1 != -1) && (paramInt2 >= paramInt1))
      {
        localObject2 = ((ReportResult)paramIReportResult).getProperties();
        if (localObject2 == null)
        {
          localObject2 = new HashMap();
          ((ReportResult)paramIReportResult).setProperties((HashMap)localObject2);
        }
        ((HashMap)localObject2).put("TRUNCATED", new Integer(paramInt1));
      }
    }
    catch (Exception localException1)
    {
      DebugLog.log(Level.SEVERE, (String)localObject1 + ": " + localException1.toString());
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return paramInt2;
  }
  
  private static ReportResult a(ReportCriteria paramReportCriteria, HashMap paramHashMap, Locale paramLocale)
    throws BCReportException, AdminException
  {
    String str = "ReportAuditAdapter.initializeMasterReport";
    ReportResult localReportResult = new ReportResult(paramLocale);
    try
    {
      jdField_do(paramReportCriteria, paramLocale, paramHashMap);
      localReportResult.init(paramReportCriteria);
      localReportResult.setHeading(null);
    }
    catch (RptException localRptException)
    {
      throw new BCReportException(localRptException, str, 19, "Unable to generate the report.");
    }
    return localReportResult;
  }
  
  private static ReportResult jdField_if(ReportCriteria paramReportCriteria, HashMap paramHashMap, Locale paramLocale)
    throws BCReportException, AdminException
  {
    String str1 = "ReportAuditAdapter.initializeMasterConsumerReport";
    ReportResult localReportResult = new ReportResult(paramLocale);
    try
    {
      a(paramReportCriteria, paramLocale, paramHashMap);
      localReportResult.init(paramReportCriteria);
      localReportResult.setHeading(null);
    }
    catch (RptException localRptException)
    {
      String str2 = "Unable to generate the report.";
      throw new BCReportException(localRptException, str1, 19, str2);
    }
    return localReportResult;
  }
  
  public static ArrayList getBizIds(HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i1 = 0;
    int i2 = 0;
    if ((paramHashMap.containsKey(new Integer(-1))) && (paramHashMap.size() == 1))
    {
      int i3 = Integer.parseInt((String)paramHashMap.get(new Integer(-1)));
      DBCookie localDBCookie = new DBCookie();
      for (EntitlementGroup localEntitlementGroup = Entitlements.getChildrenByGroupType(i3, "BusinessAdmin", localDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenByGroupType(i3, "BusinessAdmin", localDBCookie))
      {
        i1++;
        if (i1 % 1000 == 0)
        {
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer = new StringBuffer();
        }
        localStringBuffer.append(((EntitlementGroupMember)Entitlements.getMembers(localEntitlementGroup.getGroupId()).get(0)).getId());
        localStringBuffer.append(",");
        i2 = 1;
      }
      if (i2 != 0) {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      if (localStringBuffer.length() > 0) {
        localArrayList.add(localStringBuffer.toString());
      }
    }
    else
    {
      Object[] arrayOfObject = paramHashMap.keySet().toArray();
      for (int i4 = 0; i4 < arrayOfObject.length; i4++)
      {
        i1 = i4;
        if (i1 == 1000)
        {
          i1 = 0;
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer = new StringBuffer();
          i2 = 0;
        }
        else
        {
          localStringBuffer.append(((Integer)arrayOfObject[i4]).toString());
          localStringBuffer.append(",");
          i2 = 1;
        }
      }
      if (i2 != 0) {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      localArrayList.add(localStringBuffer.toString());
    }
    return localArrayList;
  }
  
  public static ArrayList getConsumerIds(HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i1 = 0;
    int i2 = 0;
    if ((paramHashMap.containsKey("ALL")) && (paramHashMap.size() == 1))
    {
      int i4 = Integer.parseInt(((String[])paramHashMap.get("ALL"))[0]);
      DBCookie localDBCookie = new DBCookie();
      for (EntitlementGroup localEntitlementGroup = Entitlements.getChildrenByGroupType(i4, "ConsumerAdmin", localDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenByGroupType(i4, "ConsumerAdmin", localDBCookie))
      {
        i1++;
        if (i1 % 1000 == 0)
        {
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer = new StringBuffer();
        }
        localStringBuffer.append("'");
        int i3 = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "USER").getGroup(0).getGroupId();
        localStringBuffer.append(((EntitlementGroupMember)Entitlements.getMembers(i3).get(0)).getId());
        localStringBuffer.append("'");
        localStringBuffer.append(",");
        i2 = 1;
      }
      if (i2 != 0) {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      if (localStringBuffer.length() > 0) {
        localArrayList.add(localStringBuffer.toString());
      }
    }
    else
    {
      Object[] arrayOfObject = paramHashMap.keySet().toArray();
      for (int i5 = 0; i5 < arrayOfObject.length; i5++)
      {
        i1 = i5;
        if (i1 == 1000)
        {
          i1 = 0;
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer = new StringBuffer();
          i2 = 0;
        }
        localStringBuffer.append("'");
        localStringBuffer.append((String)arrayOfObject[i5]);
        localStringBuffer.append("'");
        localStringBuffer.append(",");
        i2 = 1;
      }
      if (i2 != 0) {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      if (localStringBuffer.length() > 0) {
        localArrayList.add(localStringBuffer.toString());
      }
    }
    return localArrayList;
  }
  
  public static ArrayList getConsumerIntIds(HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i1 = 0;
    int i2 = 0;
    if ((paramHashMap.containsKey("ALL")) && (paramHashMap.size() == 1))
    {
      int i4 = Integer.parseInt(((String[])paramHashMap.get("ALL"))[0]);
      DBCookie localDBCookie = new DBCookie();
      for (EntitlementGroup localEntitlementGroup = Entitlements.getChildrenByGroupType(i4, "ConsumerAdmin", localDBCookie); localEntitlementGroup != null; localEntitlementGroup = Entitlements.getChildrenByGroupType(i4, "ConsumerAdmin", localDBCookie))
      {
        i1++;
        if (i1 % 1000 == 0)
        {
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer = new StringBuffer();
        }
        int i3 = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "USER").getGroup(0).getGroupId();
        EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(i3);
        for (int i6 = 0; i6 < localEntitlementGroupMembers.size(); i6++)
        {
          localStringBuffer.append(((EntitlementGroupMember)localEntitlementGroupMembers.get(i6)).getId());
          localStringBuffer.append(",");
        }
        i2 = 1;
      }
      if (i2 != 0) {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      if (localStringBuffer.length() > 0) {
        localArrayList.add(localStringBuffer.toString());
      }
    }
    else
    {
      Object[] arrayOfObject = paramHashMap.keySet().toArray();
      for (int i5 = 0; i5 < arrayOfObject.length; i5++)
      {
        i1 = i5;
        if (i1 == 1000)
        {
          i1 = 0;
          localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer = new StringBuffer();
          i2 = 0;
        }
        localStringBuffer.append((String)arrayOfObject[i5]);
        localStringBuffer.append(",");
        i2 = 1;
      }
      if (i2 != 0) {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      if (localStringBuffer.length() > 0) {
        localArrayList.add(localStringBuffer.toString());
      }
    }
    return localArrayList;
  }
  
  public static StringBuffer getTransTypesDelimited(Object[] paramArrayOfObject, ChargeableReportHandler paramChargeableReportHandler, HashMap paramHashMap, String paramString)
  {
    String str1 = "ReportAuditAdapter.getTransTypesDelimited";
    StringBuffer localStringBuffer = new StringBuffer();
    int i1 = 0;
    for (int i2 = 0; i2 < paramArrayOfObject.length; i2++)
    {
      String str2 = (String)paramArrayOfObject[i2];
      if ((str2 != null) || (!str2.equals("")))
      {
        ArrayList localArrayList = (ArrayList)paramChargeableReportHandler.getReportMap(paramHashMap).get(str2);
        if ((localArrayList != null) && (localArrayList.size() != 0)) {
          for (int i3 = 0; i3 < localArrayList.size(); i3++)
          {
            localStringBuffer.append(((Integer)localArrayList.get(i3)).toString());
            localStringBuffer.append(",");
            i1 = 1;
          }
        } else {
          DebugLog.log(Level.INFO, str1 + ": A chargeable operation '" + str2 + "' in service package '" + paramString + "' is not mapped to transaction type(s).");
        }
      }
    }
    if (i1 != 0) {
      localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
    }
    if (localStringBuffer.length() == 0) {
      DebugLog.log(Level.INFO, str1 + ": None of the chargeable operations in service package '" + paramString + "' were mapped to transaction type(s).");
    }
    return localStringBuffer;
  }
  
  private static int a(ReportCriteria paramReportCriteria)
  {
    int i1 = 3;
    paramReportCriteria.setHiddenSearchCriterion("TTYGracePeriod", true);
    try
    {
      i1 = Integer.parseInt((String)paramReportCriteria.get("TTYGracePeriod"));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DebugLog.log(Level.INFO, "WARNING: Either no grace period days or an invalid TTYGracePeriod was encountered.  In order to use a Grace Period of Days for Duration Service Charge reports, make sure to include TTYGracePeriod in the reporting.xml as part of the search criteria");
    }
    return i1;
  }
  
  public static void processBusinessSubReports(SecureUser paramSecureUser, Locale paramLocale, ReportProcessingHandler paramReportProcessingHandler, ReportCriteria paramReportCriteria, HashMap paramHashMap, ReportResult paramReportResult)
    throws CSILException, AdminException, Exception
  {
    String str1 = "ReportAuditAdapter.processBusinessSubReports";
    DateTime localDateTime2 = null;
    Connection localConnection = null;
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    int i1 = a(paramReportCriteria);
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str2 = (String)localProperties1.get("Affiliate Bank");
    int i2 = 0;
    if ((str2 != null) && (str2.length() > 0) && (!str2.equals("AllAffiliateBanks"))) {
      try
      {
        i2 = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new AdminException(str1, 25, "Affiliate bank ID must be a valid integer");
      }
    }
    String str3 = (String)localProperties1.get("StartDate");
    String str4 = (String)localProperties1.get("EndDate");
    DateTime localDateTime1;
    try
    {
      localDateTime1 = jdField_if(str3, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
      localDateTime2 = jdField_if(str4, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
    }
    catch (ParseException localParseException)
    {
      throw new AdminException(str1, 13, "Dates are not in MM/DD/YYYY or MM/DD/YYYY hh:mm format.");
    }
    MasterReportData localMasterReportData1 = null;
    MasterReportData localMasterReportData2 = null;
    BusinessServiceChargeLists localBusinessServiceChargeLists = (BusinessServiceChargeLists)paramHashMap.get("BusinessesForServiceChargeReport");
    Iterator localIterator = localBusinessServiceChargeLists.iterator();
    ChargeableReportHandler localChargeableReportHandler = paramReportProcessingHandler.getChargeableReportHandler();
    ServiceChargeList localServiceChargeList = new ServiceChargeList();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      ReportResult localReportResult = null;
      while (localIterator.hasNext())
      {
        localMasterReportData1 = new MasterReportData();
        BusinessServiceChargeList localBusinessServiceChargeList = (BusinessServiceChargeList)localIterator.next();
        localMasterReportData1.marketSegment = localBusinessServiceChargeList.getMarketSegment();
        localMasterReportData1.servicePackage = localBusinessServiceChargeList.getServicePackage();
        HashMap localHashMap1 = localBusinessServiceChargeList.getBusinesses();
        HashMap localHashMap2 = localBusinessServiceChargeList.getChargeableOperations();
        Object[] arrayOfObject = localHashMap2.keySet().toArray();
        localServiceChargeList.setValues(localHashMap1);
        if ((localHashMap1 != null) && (!localHashMap1.isEmpty()) && (arrayOfObject != null) && (arrayOfObject.length != 0))
        {
          ArrayList localArrayList = getBizIds(localHashMap1);
          ServiceChargeReportCriteria localServiceChargeReportCriteria = null;
          if ((localArrayList != null) && (!localArrayList.isEmpty()))
          {
            localServiceChargeReportCriteria = new ServiceChargeReportCriteria(paramReportCriteria);
            localServiceChargeReportCriteria.setIds(localArrayList);
            localServiceChargeReportCriteria.setStart(localDateTime1);
            localServiceChargeReportCriteria.setEnd(localDateTime2);
            localServiceChargeReportCriteria.setChargeableOperations(localHashMap2);
            localServiceChargeReportCriteria.setAffiliateBankId(i2);
            localServiceChargeReportCriteria.setGracePeriodInDays(i1);
            if (localChargeableReportHandler.anyRecords(localConnection, paramHashMap, localServiceChargeReportCriteria))
            {
              if (localMasterReportData1.hasMarketSegmentChanged(localMasterReportData2)) {
                localReportResult = paramReportProcessingHandler.createMainSubReport(paramReportResult, localMasterReportData1, paramLocale);
              }
              localChargeableReportHandler.initialize(localConnection, localServiceChargeReportCriteria);
              localChargeableReportHandler.executeSql(paramHashMap);
              localChargeableReportHandler.processResults(paramReportResult, localServiceChargeList, paramLocale, paramHashMap);
              localMasterReportData2 = MasterReportData.clone(localMasterReportData1);
            }
          }
        }
      }
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  public static void processConsumerSubReports(SecureUser paramSecureUser, Locale paramLocale, ReportProcessingHandler paramReportProcessingHandler, ReportCriteria paramReportCriteria, HashMap paramHashMap, ReportResult paramReportResult)
    throws CSILException, AdminException, Exception
  {
    String str1 = "ReportAuditAdapter.processConsumerSubReports";
    DateTime localDateTime2 = null;
    Connection localConnection = null;
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    int i1 = a(paramReportCriteria);
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str2 = (String)localProperties1.get("Affiliate Bank");
    int i2 = 0;
    if ((str2 != null) && (str2.length() > 0) && (!str2.equals("AllAffiliateBanks"))) {
      try
      {
        i2 = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new AdminException(str1, 25, "Affiliate bank ID must be a valid integer");
      }
    }
    String str3 = (String)localProperties1.get("StartDate");
    String str4 = (String)localProperties1.get("EndDate");
    DateTime localDateTime1;
    try
    {
      localDateTime1 = jdField_if(str3, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
      localDateTime2 = jdField_if(str4, "MM/dd/yyyy HH:mm:ss", paramReportCriteria.getLocale());
    }
    catch (ParseException localParseException)
    {
      throw new AdminException(str1, 13, "Dates are not in MM/DD/YYYY or MM/DD/YYYY hh:mm format.");
    }
    MasterReportData localMasterReportData1 = null;
    MasterReportData localMasterReportData2 = null;
    ConsumerServiceChargeLists localConsumerServiceChargeLists = (ConsumerServiceChargeLists)paramHashMap.get("ConsumersForServiceChargeReport");
    Iterator localIterator = localConsumerServiceChargeLists.iterator();
    ChargeableReportHandler localChargeableReportHandler = paramReportProcessingHandler.getChargeableReportHandler();
    ServiceChargeList localServiceChargeList = new ServiceChargeList();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      ReportResult localReportResult = null;
      while (localIterator.hasNext())
      {
        localMasterReportData1 = new MasterReportData();
        ConsumerServiceChargeList localConsumerServiceChargeList = (ConsumerServiceChargeList)localIterator.next();
        localMasterReportData1.marketSegment = localConsumerServiceChargeList.getMarketSegment();
        localMasterReportData1.servicePackage = localConsumerServiceChargeList.getServicePackage();
        HashMap localHashMap1 = localConsumerServiceChargeList.getConsumers();
        HashMap localHashMap2 = localConsumerServiceChargeList.getChargeableOperations();
        Object[] arrayOfObject = localHashMap2.keySet().toArray();
        localServiceChargeList.setValues(localHashMap1);
        if ((localHashMap1 != null) && (!localHashMap1.isEmpty()) && (arrayOfObject != null) && (arrayOfObject.length != 0))
        {
          ArrayList localArrayList = getConsumerIntIds(localHashMap1);
          ServiceChargeReportCriteria localServiceChargeReportCriteria = null;
          if ((localArrayList != null) && (!localArrayList.isEmpty()))
          {
            localServiceChargeReportCriteria = new ServiceChargeReportCriteria(paramReportCriteria);
            localServiceChargeReportCriteria.setIds(localArrayList);
            localServiceChargeReportCriteria.setStart(localDateTime1);
            localServiceChargeReportCriteria.setEnd(localDateTime2);
            localServiceChargeReportCriteria.setChargeableOperations(localHashMap2);
            localServiceChargeReportCriteria.setAffiliateBankId(i2);
            localServiceChargeReportCriteria.setGracePeriodInDays(i1);
            if (localChargeableReportHandler.anyRecords(localConnection, paramHashMap, localServiceChargeReportCriteria))
            {
              if (localMasterReportData1.hasMarketSegmentChanged(localMasterReportData2)) {
                localReportResult = paramReportProcessingHandler.createMainSubReport(paramReportResult, localMasterReportData1, paramLocale);
              }
              localChargeableReportHandler.initialize(localConnection, localServiceChargeReportCriteria);
              localChargeableReportHandler.executeSql(paramHashMap);
              localChargeableReportHandler.processResults(paramReportResult, localServiceChargeList, paramLocale, paramHashMap);
              localMasterReportData2 = MasterReportData.clone(localMasterReportData1);
            }
          }
        }
      }
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static ReportResult a(SecureUser paramSecureUser, Locale paramLocale, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException, AdminException
  {
    ReportResult localReportResult = null;
    String str1 = "ReportAuditAdapter.getBusinessServiceEntriesForReport";
    paramReportCriteria.setHiddenSearchCriterion("TTYGracePeriod", true);
    localReportResult = new ReportResult(paramLocale);
    Properties localProperties = paramReportCriteria.getReportOptions();
    if (localProperties == null)
    {
      localObject = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new AdminException(str1, 12, (String)localObject);
    }
    if (paramHashMap == null) {
      throw new AdminException(str1, 20, "Extra Hashmap is null.");
    }
    localReportResult = a(paramReportCriteria, paramHashMap, paramLocale);
    Object localObject = new ChargeableReportHandler[2];
    localObject[0] = new BusinessReportHandler(paramLocale);
    localObject[1] = new BusinessDurationReportHandler(paramLocale);
    try
    {
      processBusinessSubReports(paramSecureUser, paramLocale, new BusinessProcessingHandler((ChargeableReportHandler[])localObject, paramLocale), paramReportCriteria, paramHashMap, localReportResult);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str1 + ": " + localException.getMessage());
      localException.printStackTrace();
      localReportResult.setError(localException);
    }
    try
    {
      localReportResult.fini();
    }
    catch (RptException localRptException)
    {
      String str2 = "Unable to generate the report.";
      throw new BCReportException(localRptException, str1, 19, str2);
    }
    return localReportResult;
  }
  
  private static void jdField_do(ReportCriteria paramReportCriteria, Locale paramLocale, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportAuditAdapter.processBSCReportCriteria";
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str1, 10, (String)localObject1);
    }
    Object localObject1 = (BusinessServiceChargeLists)paramHashMap.get("BusinessesForServiceChargeReport");
    Set localSet = localProperties.keySet();
    Iterator localIterator = localSet.iterator();
    String str2 = null;
    String str3 = null;
    while (localIterator.hasNext())
    {
      str2 = (String)localIterator.next();
      str3 = localProperties.getProperty(str2);
      Object localObject2;
      Object localObject3;
      if (str2.equals("MarketSegments"))
      {
        if ((str3.equals("")) || (str3.equals("AllMarketSegments")))
        {
          paramReportCriteria.setDisplayValue("MarketSegments", ReportConsts.getText(10045, paramLocale));
        }
        else
        {
          localObject2 = ((BusinessServiceChargeLists)localObject1).iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localObject3 = (BusinessServiceChargeList)((Iterator)localObject2).next();
            paramReportCriteria.setDisplayValue("MarketSegments", ((BusinessServiceChargeList)localObject3).getMarketSegment());
          }
        }
      }
      else if (str2.equals("ServicePackages"))
      {
        if ((str3.equals("")) || (str3.equals("AllServicePackages")))
        {
          paramReportCriteria.setDisplayValue("ServicePackages", ReportConsts.getText(10046, paramLocale));
        }
        else
        {
          localObject2 = ((BusinessServiceChargeLists)localObject1).iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localObject3 = (BusinessServiceChargeList)((Iterator)localObject2).next();
            paramReportCriteria.setDisplayValue("ServicePackages", ((BusinessServiceChargeList)localObject3).getServicePackage());
          }
        }
      }
      else if (str2.equals("Business"))
      {
        if ((str3.equals("")) || (str3.equals("AllBusinesses"))) {
          paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramLocale));
        } else {
          try
          {
            localObject2 = BusinessAdapter.getBusiness(Integer.parseInt(str3));
            if (localObject2 != null)
            {
              localObject3 = new StringBuffer(((Business)localObject2).getBusinessName());
              String str5 = ((Business)localObject2).getCustId();
              if (str5 != null)
              {
                str5 = str5.trim();
                if (str5.length() > 0) {
                  ((StringBuffer)localObject3).append("(").append(str5).append(")");
                }
              }
              paramReportCriteria.setDisplayValue("Business", ((StringBuffer)localObject3).toString());
            }
          }
          catch (Exception localException) {}
        }
      }
      else if (str2.equals("Affiliate Bank"))
      {
        if (str3.equals("AllAffiliateBanks"))
        {
          paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, paramLocale));
        }
        else
        {
          String str4 = (String)paramHashMap.get("AffiliateBankNameForReport");
          paramReportCriteria.setDisplayValue("Affiliate Bank", str4);
        }
      }
      else if ((str3 == null) || (str3.length() <= 0) || (str3.trim().length() <= 0)) {
        paramReportCriteria.setHiddenSearchCriterion(str2, true);
      }
    }
  }
  
  private static ReportResult jdField_if(SecureUser paramSecureUser, Locale paramLocale, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException, AdminException
  {
    ReportResult localReportResult = null;
    String str1 = "ReportAuditAdapter.getConsumerServiceEntriesForReport";
    paramReportCriteria.setHiddenSearchCriterion("TTYGracePeriod", true);
    localReportResult = new ReportResult(paramLocale);
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null)
    {
      String str2 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new AdminException(str1, 12, str2);
    }
    if (paramHashMap == null) {
      throw new AdminException(str1, 20, "Extra Hashmap is null.");
    }
    int i1 = 0;
    localReportResult = jdField_if(paramReportCriteria, paramHashMap, paramLocale);
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    ChargeableReportHandler[] arrayOfChargeableReportHandler = new ChargeableReportHandler[2];
    arrayOfChargeableReportHandler[0] = new ConsumerReportHandler(paramLocale);
    arrayOfChargeableReportHandler[1] = new ConsumerDurationReportHandler(paramLocale);
    try
    {
      processConsumerSubReports(paramSecureUser, paramLocale, new ConsumerProcessingHandler(arrayOfChargeableReportHandler, paramLocale), paramReportCriteria, paramHashMap, localReportResult);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str1 + ": " + localException.getMessage());
      localException.printStackTrace();
      localReportResult.setError(localException);
    }
    try
    {
      localReportResult.fini();
    }
    catch (RptException localRptException)
    {
      String str3 = "Unable to generate the report.";
      throw new BCReportException(localRptException, str1, 19, str3);
    }
    return localReportResult;
  }
  
  private static void a(ReportCriteria paramReportCriteria, Locale paramLocale, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportAuditAdapter.processCSCReportCriteria";
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str1, 10, (String)localObject1);
    }
    Object localObject1 = (ConsumerServiceChargeLists)paramHashMap.get("ConsumersForServiceChargeReport");
    Set localSet = localProperties.keySet();
    Iterator localIterator = localSet.iterator();
    String str2 = null;
    String str3 = null;
    while (localIterator.hasNext())
    {
      str2 = (String)localIterator.next();
      str3 = localProperties.getProperty(str2);
      Object localObject2;
      ConsumerServiceChargeList localConsumerServiceChargeList;
      if (str2.equals("MarketSegments"))
      {
        if ((str3.equals("")) || (str3.equals("AllMarketSegments")))
        {
          paramReportCriteria.setDisplayValue("MarketSegments", ReportConsts.getText(10045, paramLocale));
        }
        else
        {
          localObject2 = ((ConsumerServiceChargeLists)localObject1).iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localConsumerServiceChargeList = (ConsumerServiceChargeList)((Iterator)localObject2).next();
            paramReportCriteria.setDisplayValue("MarketSegments", localConsumerServiceChargeList.getMarketSegment());
          }
        }
      }
      else if (str2.equals("ServicePackages"))
      {
        if ((str3.equals("")) || (str3.equals("AllServicePackages")))
        {
          paramReportCriteria.setDisplayValue("ServicePackages", ReportConsts.getText(10046, paramLocale));
        }
        else
        {
          localObject2 = ((ConsumerServiceChargeLists)localObject1).iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localConsumerServiceChargeList = (ConsumerServiceChargeList)((Iterator)localObject2).next();
            paramReportCriteria.setDisplayValue("ServicePackages", localConsumerServiceChargeList.getServicePackage());
          }
        }
      }
      else if (str2.equals("Consumer"))
      {
        if ((str3.equals("")) || (str3.equals("AllConsumers")))
        {
          paramReportCriteria.setDisplayValue("Consumer", ReportConsts.getText(10016, paramLocale));
        }
        else
        {
          localObject2 = ((ConsumerServiceChargeLists)localObject1).iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localConsumerServiceChargeList = (ConsumerServiceChargeList)((Iterator)localObject2).next();
            HashMap localHashMap = localConsumerServiceChargeList.getConsumers();
            String[] arrayOfString = (String[])localHashMap.get(str3);
            paramReportCriteria.setDisplayValue("Consumer", arrayOfString[0]);
          }
        }
      }
      else if (str2.equals("Affiliate Bank"))
      {
        if (str3.equals("AllAffiliateBanks"))
        {
          paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, paramLocale));
        }
        else
        {
          localObject2 = (String)paramHashMap.get("AffiliateBankNameForReport");
          paramReportCriteria.setDisplayValue("Affiliate Bank", (String)localObject2);
        }
      }
      else if ((str3 == null) || (str3.length() <= 0) || (str3.trim().length() <= 0)) {
        paramReportCriteria.setHiddenSearchCriterion(str2, true);
      }
    }
  }
  
  private static IReportResult a(SecureUser paramSecureUser, Locale paramLocale, String paramString, ArrayList paramArrayList, HashMap paramHashMap)
  {
    Object localObject = null;
    if ((paramString.equals("OBO CSR Activity Report")) || (paramString.equals("My OBO Activity Report"))) {
      localObject = new CSRActivityRpt((UserActivityRecords)paramArrayList);
    } else if (paramString.equals("OBO CSR Team Activity Report")) {
      localObject = new CSRTeamActivityRpt((UserActivityRecords)paramArrayList);
    } else if (paramString.equals("User Activity Report")) {
      localObject = new UserActivityRpt((UserActivityRecords)paramArrayList);
    } else if (paramString.equals("Login Activity Report")) {
      localObject = new LoginActivityRpt((LoginActivityDetails)paramArrayList);
    } else if (paramString.equals("Login Summary Report")) {
      localObject = new LoginSummaryRpt((LoginActivitySummaries)paramArrayList);
    } else if (paramString.equals("Business Service Charge Report")) {
      localObject = new BusinessServiceChargeRpt((BusinessServiceChargeEntries)paramArrayList);
    } else if (paramString.equals("Consumer Service Charge Report")) {
      localObject = new ConsumerServiceChargeRpt((ConsumerServiceChargeEntries)paramArrayList);
    }
    ((IReportResult)localObject).setLocale(paramLocale);
    return localObject;
  }
  
  private static Vector a(ReportSortCriteria paramReportSortCriteria, String paramString)
    throws AdminException
  {
    Vector localVector = new Vector();
    if (paramReportSortCriteria != null)
    {
      localVector = new Vector();
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      ReportSortCriterion localReportSortCriterion = null;
      String str1 = null;
      while (localIterator.hasNext())
      {
        localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        str1 = localReportSortCriterion.getName();
        ArrayList localArrayList = new ArrayList();
        String str2 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
        if (str1.equals("ProcessingDate"))
        {
          localArrayList.add("a.LOG_DATE");
        }
        else if (str1.equals("TransactionType"))
        {
          localArrayList.add("t.description");
        }
        else if (str1.equals("User"))
        {
          if (paramString.equals("Login Activity Report"))
          {
            localArrayList.add("cust.user_name");
          }
          else
          {
            localArrayList.add("cust.last_name");
            localArrayList.add("cust.first_name");
          }
        }
        else if (str1.equals("TranId"))
        {
          localArrayList.add("a.TRAN_ID");
        }
        else if ((str1.equals("Agent")) && (paramString.equals("On Behalf Of Report")))
        {
          localArrayList.add("be.last_name");
          localArrayList.add("be.first_name");
        }
        else
        {
          if (!str1.equals("Business")) {
            continue;
          }
          localArrayList.add("bus.business_name");
        }
        for (int i1 = 0; i1 < localArrayList.size(); i1++)
        {
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append((String)localArrayList.get(i1));
          localStringBuffer.append(" ");
          localStringBuffer.append(str2);
          localVector.add(localStringBuffer.toString());
        }
      }
    }
    return localVector;
  }
  
  private static DateTime jdField_if(String paramString1, String paramString2, Locale paramLocale)
    throws ParseException
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString2);
      java.util.Date localDate = localDateFormat.parse(paramString1);
      return new DateTime(localDate, paramLocale, paramString2);
    }
    return null;
  }
  
  private static void a(Calendar paramCalendar)
  {
    switch (paramCalendar.get(7))
    {
    case 1: 
      paramCalendar.add(5, 1);
    case 2: 
      paramCalendar.add(5, 1);
    case 3: 
      paramCalendar.add(5, 1);
    case 4: 
      paramCalendar.add(5, 1);
    case 5: 
      paramCalendar.add(5, 1);
    case 6: 
      paramCalendar.add(5, 1);
    case 7: 
      paramCalendar.set(11, 23);
      paramCalendar.set(9, 1);
      paramCalendar.set(10, 11);
      paramCalendar.set(12, 59);
      paramCalendar.set(13, 59);
      paramCalendar.set(14, 59);
    }
  }
  
  private static void jdField_if(Calendar paramCalendar)
  {
    paramCalendar.set(10, 0);
    paramCalendar.set(11, 0);
    paramCalendar.set(9, 0);
    paramCalendar.set(12, 0);
    paramCalendar.set(13, 0);
    paramCalendar.set(14, 0);
  }
  
  private static void jdField_if(ReportResult paramReportResult, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws RptException
  {
    String str = paramReportCriteria.getSearchCriteria().getProperty("Business");
    boolean bool = "AllConsumers".equals(str);
    int i1 = (!"AllConsumers".equals(str)) && (!"AllBusinessesAndConsumers".equals(str)) ? 1 : 0;
    int i2 = 14;
    int i3 = 8;
    if ((!bool) && (i1 == 0)) {
      i2 /= 2;
    }
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(350, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(356, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(6);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(351, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    if (!bool)
    {
      localReportColumn = new ReportColumn(paramLocale);
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(352, paramLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setWidthAsPercent(i2);
      paramReportResult.addColumn(localReportColumn);
    }
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(353, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    if (i1 == 0)
    {
      localReportColumn = new ReportColumn(paramLocale);
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(371, paramLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setWidthAsPercent(i2);
      paramReportResult.addColumn(localReportColumn);
    }
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(354, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(15);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(357, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(359, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(6);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(361, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(362, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(355, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(i3);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdField_do(ReportResult paramReportResult, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws RptException
  {
    int i1 = 14;
    int i2 = 9;
    String str = null;
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(350, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(356, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    str = paramReportCriteria.getSearchCriteria().getProperty("Business");
    boolean bool = "AllConsumers".equals(str);
    int i3 = (!"AllConsumers".equals(str)) && (!"AllBusinessesAndConsumers".equals(str)) ? 1 : 0;
    if ((!bool) && (i3 == 0)) {
      i1 /= 2;
    }
    if (!bool)
    {
      localReportColumn = new ReportColumn(paramLocale);
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(352, paramLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setWidthAsPercent(i1);
      paramReportResult.addColumn(localReportColumn);
    }
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(353, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
    if (i3 == 0)
    {
      localReportColumn = new ReportColumn(paramLocale);
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(371, paramLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setWidthAsPercent(i1);
      paramReportResult.addColumn(localReportColumn);
    }
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(354, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(16);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(357, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(359, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(361, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(362, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(355, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(i2);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws RptException
  {
    String str = paramReportCriteria.getSearchCriteria().getProperty("Business");
    boolean bool = "AllConsumers".equals(str);
    int i1 = (!"AllConsumers".equals(str)) && (!"AllBusinessesAndConsumers".equals(str)) ? 1 : 0;
    int i2 = 16;
    int i3 = 15;
    if ((!bool) && (i1 == 0))
    {
      a(paramReportResult, 7, paramLocale);
      i2 /= 2;
    }
    else
    {
      a(paramReportResult, 6, paramLocale);
    }
    a(paramReportResult, 350, paramLocale, 10);
    a(paramReportResult, 356, paramLocale, 10);
    if (!bool) {
      a(paramReportResult, 352, paramLocale, i2);
    }
    a(paramReportResult, 353, paramLocale, i3);
    if (i1 == 0) {
      a(paramReportResult, 371, paramLocale, i2);
    }
    a(paramReportResult, 359, paramLocale, 15);
    a(paramReportResult, 360, paramLocale, 25);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    a(paramReportResult, 7, paramLocale);
    a(paramReportResult, 363, paramLocale, 10);
    a(paramReportResult, 364, paramLocale, 10);
    a(paramReportResult, 365, paramLocale, 10, "RIGHT");
    a(paramReportResult, 366, paramLocale, 17, "RIGHT");
    a(paramReportResult, 367, paramLocale, 19, "RIGHT");
    a(paramReportResult, 368, paramLocale, 17, "RIGHT");
    a(paramReportResult, 369, paramLocale, 17, "RIGHT");
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, Locale paramLocale, int paramInt2)
    throws RptException
  {
    a(paramReportResult, paramInt1, paramLocale, paramInt2, null);
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, Locale paramLocale, int paramInt2, String paramString)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(paramInt1, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(paramInt2);
    if (paramString != null) {
      localReportColumn.setJustification(paramString);
    }
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws Exception
  {
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(paramString);
    paramReportResult.setHeading(localReportHeading);
  }
  
  private static void a(ReportResult paramReportResult, int paramInt, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(paramInt);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  public static ReportResult retrieveBCReport(Locale paramLocale, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws AdminException, BCReportException
  {
    String str1 = "ReportAuditAdapter.retrieveBCReport";
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null)
    {
      str2 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new AdminException(str1, 12, str2);
    }
    String str2 = localProperties1.getProperty("REPORTTYPE");
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    String str3 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    String str4 = localProperties1.getProperty("DATEFORMAT");
    String str5 = localProperties1.getProperty("TIMEFORMAT");
    DateFormat localDateFormat1 = null;
    DateFormat localDateFormat2 = null;
    if (str4 == null) {
      localDateFormat1 = DateFormatUtil.getFormatter(null, paramLocale);
    } else {
      localDateFormat1 = DateFormatUtil.getFormatter(str4, paramLocale);
    }
    if (str5 == null) {
      localDateFormat2 = DateFormatUtil.getFormatter(null, paramLocale);
    } else {
      localDateFormat2 = DateFormatUtil.getFormatter(str5, paramLocale);
    }
    try
    {
      localInteger = Integer.valueOf(str3);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localInteger = new Integer(-1);
    }
    int i1 = -1;
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    try
    {
      localReportResult1.init(paramReportCriteria);
      localReportResult1.setHeading(null);
    }
    catch (RptException localRptException1)
    {
      String str6 = "Unable to generate the report.";
      throw new BCReportException(localRptException1, str1, 19, str6);
    }
    if ((str2.equals("Bank Employee Activity Report")) || (str2.equals("CSR Activity Report")))
    {
      ArrayList localArrayList1 = buildWhereStringByAgent(paramLocale, str2, localProperties2, paramHashMap);
      if (localArrayList1 == null)
      {
        try
        {
          localReportResult1.fini();
        }
        catch (RptException localRptException2)
        {
          str7 = "Unable to generate the report.";
          throw new BCReportException(localRptException2, str1, 19, str7);
        }
        return localReportResult1;
      }
      ArrayList localArrayList2 = a("SELECT a.LOG_DATE, bus.business_name, pri.first_name, pri.last_name, cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id LEFT OUTER JOIN business bus ON a.BUSINESS_ID = bus.business_id LEFT OUTER JOIN customer cust ON USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri on pri.directory_id=cr.PRIMARY_USER_ID ", localArrayList1, " WHERE ");
      String str7 = buildSortString(localReportSortCriteria, paramHashMap);
      if (str7.length() != 0) {
        localArrayList2 = a(localArrayList2, str7, "ORDER BY");
      }
      String str8 = (String)localProperties2.get("Agent");
      int i2 = 0;
      int i3 = 0;
      Connection localConnection = null;
      ResultSet localResultSet = null;
      PreparedStatement localPreparedStatement = null;
      String str9 = localProperties2.getProperty("Business");
      boolean bool = "AllConsumers".equals(str9);
      int i4 = (!"AllConsumers".equals(str9)) && (!"AllBusinessesAndConsumers".equals(str9)) ? 1 : 0;
      int i5 = 16;
      int i6 = 22;
      if ((!bool) && (i4 == 0)) {
        i5 /= 2;
      }
      ReportColumn localReportColumn1 = new ReportColumn(paramLocale);
      ArrayList localArrayList3 = new ArrayList();
      localArrayList3.add(ReportConsts.getColumnName(350, paramLocale));
      localReportColumn1.setLabels(localArrayList3);
      localReportColumn1.setDataType("java.lang.String");
      localReportColumn1.setWidthAsPercent(8);
      ReportColumn localReportColumn2 = new ReportColumn(paramLocale);
      ArrayList localArrayList4 = new ArrayList();
      localArrayList4.add(ReportConsts.getColumnName(356, paramLocale));
      localReportColumn2.setLabels(localArrayList4);
      localReportColumn2.setDataType("java.lang.String");
      localReportColumn2.setWidthAsPercent(5);
      ReportColumn localReportColumn3 = new ReportColumn(paramLocale);
      ArrayList localArrayList5 = new ArrayList();
      localArrayList5.add(ReportConsts.getColumnName(352, paramLocale));
      localReportColumn3.setLabels(localArrayList5);
      localReportColumn3.setDataType("java.lang.String");
      localReportColumn3.setWidthAsPercent(i5);
      ReportColumn localReportColumn4 = new ReportColumn(paramLocale);
      ArrayList localArrayList6 = new ArrayList();
      localArrayList6.add(ReportConsts.getColumnName(353, paramLocale));
      localReportColumn4.setLabels(localArrayList6);
      localReportColumn4.setDataType("java.lang.String");
      localReportColumn4.setWidthAsPercent(14);
      ReportColumn localReportColumn5 = new ReportColumn(paramLocale);
      if (i4 == 0)
      {
        localObject1 = new ArrayList();
        ((ArrayList)localObject1).add(ReportConsts.getColumnName(371, paramLocale));
        localReportColumn5.setLabels((ArrayList)localObject1);
        localReportColumn5.setDataType("java.lang.String");
        localReportColumn5.setWidthAsPercent(i5);
      }
      Object localObject1 = new ReportColumn(paramLocale);
      ArrayList localArrayList7 = new ArrayList();
      localArrayList7.add(ReportConsts.getColumnName(358, paramLocale));
      ((ReportColumn)localObject1).setLabels(localArrayList7);
      ((ReportColumn)localObject1).setDataType("java.lang.String");
      ((ReportColumn)localObject1).setWidthAsPercent(14);
      ReportColumn localReportColumn6 = new ReportColumn(paramLocale);
      ArrayList localArrayList8 = new ArrayList();
      localArrayList8.add(ReportConsts.getColumnName(357, paramLocale));
      localReportColumn6.setLabels(localArrayList8);
      localReportColumn6.setDataType("java.lang.String");
      localReportColumn6.setWidthAsPercent(13);
      ReportColumn localReportColumn7 = new ReportColumn(paramLocale);
      ArrayList localArrayList9 = new ArrayList();
      localArrayList9.add(ReportConsts.getColumnName(359, paramLocale));
      localReportColumn7.setLabels(localArrayList9);
      localReportColumn7.setDataType("java.lang.String");
      localReportColumn7.setWidthAsPercent(10);
      ReportColumn localReportColumn8 = new ReportColumn(paramLocale);
      ArrayList localArrayList10 = new ArrayList();
      localArrayList10.add(ReportConsts.getColumnName(360, paramLocale));
      localReportColumn8.setLabels(localArrayList10);
      localReportColumn8.setDataType("java.lang.String");
      localReportColumn8.setWidthAsPercent(i6);
      try
      {
        localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
        Object localObject2;
        int i8;
        Object localObject4;
        Object localObject8;
        Object localObject9;
        Object localObject10;
        Object localObject11;
        Object localObject12;
        ReportDataItem localReportDataItem1;
        Object localObject13;
        Object localObject14;
        Object localObject15;
        Object localObject16;
        Object localObject17;
        Object localObject18;
        Object localObject19;
        Object localObject20;
        Object localObject21;
        Object localObject22;
        Object localObject23;
        Object localObject24;
        Object localObject25;
        Object localObject26;
        if ((str8 == null) || (!str8.equals("AllAgents")))
        {
          localObject2 = (BankEmployees)paramHashMap.get("BankEmployeesForReport");
          if ((localObject2 == null) || (((BankEmployees)localObject2).size() == 0)) {
            throw new AdminException("ReportAuditAdapter.getReportData", 15, "No BankEmployees defined in extra HashMap.");
          }
          Object localObject3 = null;
          for (i8 = 0; i8 < ((BankEmployees)localObject2).size(); i8++)
          {
            localObject4 = (BankEmployee)((BankEmployees)localObject2).get(i8);
            if (((BankEmployee)localObject4).getId().equals(str8)) {
              localObject3 = localObject4;
            }
          }
          i8 = 1;
          localObject4 = null;
          for (int i9 = 0; i9 < localArrayList2.size(); i9++)
          {
            StringBuffer localStringBuffer2 = (StringBuffer)localArrayList2.get(i9);
            localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
            if (i1 != -1) {
              localPreparedStatement.setMaxRows(i1);
            }
            int i11 = 1;
            localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
            while (localResultSet.next())
            {
              if (i8 != 0)
              {
                localObject4 = new ReportResult(paramLocale);
                localReportResult1.addSubReport((ReportResult)localObject4);
                localObject7 = new ReportHeading(paramLocale);
                if (localObject3 != null) {
                  localObject8 = UserUtil.getFullNameWithLoginId(localObject3.getFirstName(), localObject3.getLastName(), localObject3.getUserName(), localObject3.getEmployeeId(), paramLocale);
                } else {
                  localObject8 = str8;
                }
                localObject9 = new Object[] { localObject8 };
                ((ReportHeading)localObject7).setLabel(MessageFormat.format(ReportConsts.getText(10105, paramLocale), (Object[])localObject9));
                ((ReportResult)localObject4).setSectionHeading((ReportHeading)localObject7);
                localObject10 = new ReportDataDimensions(paramLocale);
                ((ReportDataDimensions)localObject10).setNumRows(-1);
                if ((!bool) && (i4 == 0)) {
                  ((ReportDataDimensions)localObject10).setNumColumns(9);
                } else {
                  ((ReportDataDimensions)localObject10).setNumColumns(8);
                }
                ((ReportResult)localObject4).setDataDimensions((ReportDataDimensions)localObject10);
                ((ReportResult)localObject4).addColumn(localReportColumn1);
                ((ReportResult)localObject4).addColumn(localReportColumn2);
                if (!bool) {
                  ((ReportResult)localObject4).addColumn(localReportColumn3);
                }
                ((ReportResult)localObject4).addColumn(localReportColumn4);
                if (i4 == 0) {
                  ((ReportResult)localObject4).addColumn(localReportColumn5);
                }
                ((ReportResult)localObject4).addColumn((ReportColumn)localObject1);
                ((ReportResult)localObject4).addColumn(localReportColumn6);
                ((ReportResult)localObject4).addColumn(localReportColumn7);
                ((ReportResult)localObject4).addColumn(localReportColumn8);
                i8 = 0;
              }
              if ((i1 != -1) && (i2 >= i1)) {
                break;
              }
              Object localObject7 = new ReportRow(paramLocale);
              if (i11 % 2 == 0) {
                ((ReportRow)localObject7).set("CELLBACKGROUND", "reportDataShaded");
              }
              localObject8 = new ReportDataItems(paramLocale);
              ((ReportRow)localObject7).setDataItems((ReportDataItems)localObject8);
              localObject9 = localResultSet.getTimestamp(1);
              localObject10 = localDateFormat1.format((java.util.Date)localObject9);
              localObject11 = localDateFormat2.format((java.util.Date)localObject9);
              localObject12 = ((ReportDataItems)localObject8).add();
              ((ReportDataItem)localObject12).setData(localObject10);
              localReportDataItem1 = ((ReportDataItems)localObject8).add();
              localReportDataItem1.setData(localObject11);
              localObject13 = localResultSet.getString(2);
              if (!bool)
              {
                localObject14 = ((ReportDataItems)localObject8).add();
                if (localObject13 != null) {
                  ((ReportDataItem)localObject14).setData(localObject13);
                } else {
                  ((ReportDataItem)localObject14).setData("");
                }
              }
              localObject14 = localResultSet.getString(5);
              localObject15 = localResultSet.getString(6);
              localObject16 = localResultSet.getString(7);
              localObject17 = localResultSet.getString(8);
              localObject18 = ((ReportDataItems)localObject8).add();
              if ((localObject14 == null) && (localObject15 == null))
              {
                ((ReportDataItem)localObject18).setData("");
              }
              else
              {
                localObject19 = UserUtil.getFullNameWithLoginId((String)localObject14, (String)localObject15, (String)localObject16, (String)localObject17, paramLocale);
                ((ReportDataItem)localObject18).setData(localObject19);
              }
              if (i4 == 0)
              {
                localObject19 = localResultSet.getString(3);
                localObject20 = localResultSet.getString(4);
                localObject21 = ((ReportDataItems)localObject8).add();
                if ((localObject19 == null) && (localObject20 == null))
                {
                  ((ReportDataItem)localObject21).setData("");
                }
                else
                {
                  localObject22 = UserUtil.getFullName((String)localObject19, (String)localObject20, paramLocale);
                  ((ReportDataItem)localObject21).setData(localObject22);
                }
              }
              localObject19 = localResultSet.getString(9);
              localObject20 = ((ReportDataItems)localObject8).add();
              if (localObject19 == null)
              {
                ((ReportDataItem)localObject20).setData("");
              }
              else
              {
                localObject21 = AuditLogUtil.getActivityNameFromID((String)localObject19, paramLocale);
                ((ReportDataItem)localObject20).setData(localObject21);
              }
              localObject21 = localResultSet.getString(10);
              localObject22 = ((ReportDataItems)localObject8).add();
              if (localObject21 != null) {
                ((ReportDataItem)localObject22).setData(localObject21);
              } else {
                ((ReportDataItem)localObject22).setData("");
              }
              localObject23 = localResultSet.getString(11);
              localObject24 = ((ReportDataItems)localObject8).add();
              ((ReportDataItem)localObject24).setData(localObject23 == null ? "" : getDisplayStatus((String)localObject23, paramLocale, (String)localObject19));
              localObject25 = localResultSet.getString(12);
              localObject26 = ((ReportDataItems)localObject8).add();
              if (localObject25 != null) {
                ((ReportDataItem)localObject26).setData(localObject25);
              } else {
                ((ReportDataItem)localObject26).setData("");
              }
              ((ReportResult)localObject4).addRow((ReportRow)localObject7);
              i2++;
              i11++;
            }
            if ((i1 != -1) && (i2 >= i1))
            {
              i3 = 1;
              break;
            }
            DBUtil.closeAll(localPreparedStatement, localResultSet);
            localPreparedStatement = null;
            localResultSet = null;
          }
        }
        else
        {
          if (paramHashMap == null) {
            throw new AdminException("ReportAuditAdapter.retrieveBCReport", 15, "Extra Hashmap is null.");
          }
          localObject2 = (BankEmployees)paramHashMap.get("BankEmployeesForReport");
          if ((localObject2 == null) || (((BankEmployees)localObject2).size() == 0)) {
            throw new AdminException("ReportAuditAdapter.getReportData", 15, "No BankEmployees defined in extra HashMap.");
          }
          localObject2 = (BankEmployees)((BankEmployees)localObject2).clone();
          if (str7.indexOf("a.AGENT_ID DESC") != -1) {
            ((BankEmployees)localObject2).setSortedBy("LAST REVERSE,FIRST REVERSE");
          } else {
            ((BankEmployees)localObject2).setSortedBy("LAST, FIRST");
          }
          Object localObject5;
          int i10;
          Object localObject6;
          for (int i7 = 0; i7 < ((BankEmployees)localObject2).size(); i7++)
          {
            i8 = 1;
            localObject4 = null;
            localObject5 = (BankEmployee)((BankEmployees)localObject2).get(i7);
            for (i10 = 0; i10 < localArrayList2.size(); i10++)
            {
              localObject6 = (StringBuffer)localArrayList2.get(i10);
              int i12 = 1;
              localPreparedStatement = DBUtil.prepareStatement(localConnection, ((StringBuffer)localObject6).toString());
              if (i1 != -1) {
                localPreparedStatement.setMaxRows(i1);
              }
              localPreparedStatement.setString(1, ((BankEmployee)localObject5).getId());
              localResultSet = DBUtil.executeQuery(localPreparedStatement, ((StringBuffer)localObject6).toString());
              while (localResultSet.next())
              {
                if (i8 != 0)
                {
                  localObject4 = new ReportResult(paramLocale);
                  localReportResult1.addSubReport((ReportResult)localObject4);
                  localObject8 = new ReportHeading(paramLocale);
                  localObject9 = new Object[] { UserUtil.getFullNameWithLoginId(((BankEmployee)localObject5).getFirstName(), ((BankEmployee)localObject5).getLastName(), ((BankEmployee)localObject5).getUserName(), ((BankEmployee)localObject5).getEmployeeId(), paramLocale) };
                  ((ReportHeading)localObject8).setLabel(MessageFormat.format(ReportConsts.getText(10105, paramLocale), (Object[])localObject9));
                  ((ReportResult)localObject4).setSectionHeading((ReportHeading)localObject8);
                  localObject10 = new ReportDataDimensions(paramLocale);
                  ((ReportDataDimensions)localObject10).setNumRows(-1);
                  if ((!bool) && (i4 == 0)) {
                    ((ReportDataDimensions)localObject10).setNumColumns(9);
                  } else {
                    ((ReportDataDimensions)localObject10).setNumColumns(8);
                  }
                  ((ReportResult)localObject4).setDataDimensions((ReportDataDimensions)localObject10);
                  ((ReportResult)localObject4).addColumn(localReportColumn1);
                  ((ReportResult)localObject4).addColumn(localReportColumn2);
                  if (!bool) {
                    ((ReportResult)localObject4).addColumn(localReportColumn3);
                  }
                  ((ReportResult)localObject4).addColumn(localReportColumn4);
                  if (i4 == 0) {
                    ((ReportResult)localObject4).addColumn(localReportColumn5);
                  }
                  ((ReportResult)localObject4).addColumn((ReportColumn)localObject1);
                  ((ReportResult)localObject4).addColumn(localReportColumn6);
                  ((ReportResult)localObject4).addColumn(localReportColumn7);
                  ((ReportResult)localObject4).addColumn(localReportColumn8);
                  i8 = 0;
                }
                if ((i1 != -1) && (i2 >= i1)) {
                  break;
                }
                localObject8 = new ReportRow(paramLocale);
                if (i12 % 2 == 0) {
                  ((ReportRow)localObject8).set("CELLBACKGROUND", "reportDataShaded");
                }
                localObject9 = new ReportDataItems(paramLocale);
                ((ReportRow)localObject8).setDataItems((ReportDataItems)localObject9);
                localObject10 = localResultSet.getTimestamp(1);
                localObject11 = localDateFormat1.format((java.util.Date)localObject10);
                localObject12 = localDateFormat2.format((java.util.Date)localObject10);
                localReportDataItem1 = ((ReportDataItems)localObject9).add();
                localReportDataItem1.setData(localObject11);
                localObject13 = ((ReportDataItems)localObject9).add();
                ((ReportDataItem)localObject13).setData(localObject12);
                localObject14 = localResultSet.getString(2);
                if (!bool)
                {
                  localObject15 = ((ReportDataItems)localObject9).add();
                  if (localObject14 != null) {
                    ((ReportDataItem)localObject15).setData(localObject14);
                  } else {
                    ((ReportDataItem)localObject15).setData("");
                  }
                }
                localObject15 = localResultSet.getString(5);
                localObject16 = localResultSet.getString(6);
                localObject17 = localResultSet.getString(7);
                localObject18 = localResultSet.getString(8);
                localObject19 = ((ReportDataItems)localObject9).add();
                if ((localObject15 == null) && (localObject16 == null))
                {
                  ((ReportDataItem)localObject19).setData("");
                }
                else
                {
                  localObject20 = UserUtil.getFullNameWithLoginId((String)localObject15, (String)localObject16, (String)localObject17, (String)localObject18, paramLocale);
                  ((ReportDataItem)localObject19).setData(localObject20);
                }
                if (i4 == 0)
                {
                  localObject20 = localResultSet.getString(3);
                  localObject21 = localResultSet.getString(4);
                  localObject22 = ((ReportDataItems)localObject9).add();
                  if ((localObject20 == null) && (localObject21 == null))
                  {
                    ((ReportDataItem)localObject22).setData("");
                  }
                  else
                  {
                    localObject23 = UserUtil.getFullName((String)localObject20, (String)localObject21, paramLocale);
                    ((ReportDataItem)localObject22).setData(localObject23);
                  }
                }
                localObject20 = localResultSet.getString(9);
                localObject21 = ((ReportDataItems)localObject9).add();
                if (localObject20 != null)
                {
                  localObject22 = AuditLogUtil.getActivityNameFromID((String)localObject20, paramLocale);
                  ((ReportDataItem)localObject21).setData(localObject22);
                }
                else
                {
                  ((ReportDataItem)localObject21).setData("");
                }
                localObject22 = localResultSet.getString(10);
                localObject23 = ((ReportDataItems)localObject9).add();
                if (localObject22 != null) {
                  ((ReportDataItem)localObject23).setData(localObject22);
                } else {
                  ((ReportDataItem)localObject23).setData("");
                }
                localObject24 = localResultSet.getString(11);
                localObject25 = ((ReportDataItems)localObject9).add();
                ((ReportDataItem)localObject25).setData(localObject24 == null ? "" : getDisplayStatus((String)localObject24, paramLocale, (String)localObject20));
                localObject26 = localResultSet.getString(12);
                ReportDataItem localReportDataItem2 = ((ReportDataItems)localObject9).add();
                if (localReportDataItem2 != null) {
                  localReportDataItem2.setData(localObject26);
                } else {
                  localReportDataItem2.setData("");
                }
                ((ReportResult)localObject4).addRow((ReportRow)localObject8);
                i12++;
                i2++;
              }
              if ((i1 != -1) && (i2 >= i1)) {
                break;
              }
              DBUtil.closeAll(localPreparedStatement, localResultSet);
              localPreparedStatement = null;
              localResultSet = null;
            }
            if ((i1 != -1) && (i2 >= i1))
            {
              i3 = 1;
              break;
            }
          }
          if (i3 == 0)
          {
            localArrayList1 = buildWhereStringByAgent(paramLocale, str2, localProperties2, paramHashMap);
            localArrayList2 = a("SELECT a.LOG_DATE, bus.business_name, pri.first_name, pri.last_name, cust.first_name, cust.last_name, cust.user_name, cd.cust_id, a.TRAN_TYPE, a.TRAN_ID, a.STATE, a.DESCRIPTION FROM AUDIT_LOG a LEFT OUTER JOIN trans_type_desc t ON a.TRAN_TYPE = t.id LEFT OUTER JOIN business bus ON a.BUSINESS_ID = bus.business_id LEFT OUTER JOIN customer cust ON USER_ID_INT=cust.directory_id LEFT OUTER JOIN customer_directory cd ON cust.directory_id=cd.directory_id  LEFT OUTER JOIN CUSTOMER_REL cr ON cust.directory_id=cr.DIRECTORY_ID LEFT OUTER JOIN customer pri on pri.directory_id=cr.PRIMARY_USER_ID ", localArrayList1, " WHERE ");
            str7 = buildSortString(localReportSortCriteria, paramHashMap);
            if (str7.length() != 0) {
              localArrayList2 = a(localArrayList2, str7, "ORDER BY");
            }
            StringBuffer localStringBuffer1 = (StringBuffer)localArrayList2.get(0);
            DBUtil.closeAll(localPreparedStatement, localResultSet);
            localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
            localPreparedStatement.setString(1, "0");
            localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer1.toString());
            if (localResultSet.next())
            {
              ReportResult localReportResult2 = new ReportResult(paramLocale);
              localReportResult1.addSubReport(localReportResult2);
              localObject4 = new ReportHeading(paramLocale);
              ((ReportHeading)localObject4).setLabel(ReportConsts.getText(10112, paramLocale));
              localReportResult2.setSectionHeading((ReportHeading)localObject4);
              localObject5 = new ReportDataDimensions(paramLocale);
              ((ReportDataDimensions)localObject5).setNumRows(-1);
              if ((!bool) && (i4 == 0)) {
                ((ReportDataDimensions)localObject5).setNumColumns(9);
              } else {
                ((ReportDataDimensions)localObject5).setNumColumns(8);
              }
              localReportResult2.setDataDimensions((ReportDataDimensions)localObject5);
              localReportResult2.addColumn(localReportColumn1);
              localReportResult2.addColumn(localReportColumn2);
              if (!bool) {
                localReportResult2.addColumn(localReportColumn3);
              }
              localReportResult2.addColumn(localReportColumn4);
              if (i4 == 0) {
                localReportResult2.addColumn(localReportColumn5);
              }
              localReportResult2.addColumn((ReportColumn)localObject1);
              localReportResult2.addColumn(localReportColumn6);
              localReportResult2.addColumn(localReportColumn7);
              localReportResult2.addColumn(localReportColumn8);
              i10 = 1;
              do
              {
                localObject6 = new ReportRow(paramLocale);
                if (i10 % 2 == 0) {
                  ((ReportRow)localObject6).set("CELLBACKGROUND", "reportDataShaded");
                }
                ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
                ((ReportRow)localObject6).setDataItems(localReportDataItems);
                localObject8 = localResultSet.getTimestamp(1);
                localObject9 = localDateFormat1.format((java.util.Date)localObject8);
                localObject10 = localDateFormat2.format((java.util.Date)localObject8);
                localObject11 = localReportDataItems.add();
                ((ReportDataItem)localObject11).setData(localObject9);
                localObject12 = localReportDataItems.add();
                ((ReportDataItem)localObject12).setData(localObject10);
                if (!bool)
                {
                  localReportDataItem1 = localReportDataItems.add();
                  localReportDataItem1.setData("");
                }
                localReportDataItem1 = localReportDataItems.add();
                localReportDataItem1.setData("");
                if (i4 == 0)
                {
                  localObject13 = localReportDataItems.add();
                  ((ReportDataItem)localObject13).setData("");
                }
                localObject13 = localResultSet.getString(9);
                localObject14 = localReportDataItems.add();
                if (localObject13 == null)
                {
                  ((ReportDataItem)localObject14).setData("");
                }
                else
                {
                  localObject15 = AuditLogUtil.getActivityNameFromID((String)localObject13, paramLocale);
                  ((ReportDataItem)localObject14).setData(localObject15);
                }
                localObject15 = localResultSet.getString(10);
                localObject16 = localReportDataItems.add();
                if (localObject15 != null) {
                  ((ReportDataItem)localObject16).setData(localObject15);
                } else {
                  ((ReportDataItem)localObject16).setData("");
                }
                localObject17 = localReportDataItems.add();
                ((ReportDataItem)localObject17).setData("");
                localObject18 = localResultSet.getString(12);
                localObject19 = localReportDataItems.add();
                if (localObject18 != null) {
                  ((ReportDataItem)localObject19).setData(localObject18);
                } else {
                  ((ReportDataItem)localObject19).setData("");
                }
                localReportResult2.addRow((ReportRow)localObject6);
                i10++;
                i2++;
                if ((i1 != -1) && (i2 >= i1))
                {
                  i3 = 1;
                  break;
                }
              } while (localResultSet.next());
            }
          }
        }
        if (i3 != 0)
        {
          localObject2 = localReportResult1.getProperties();
          if (localObject2 == null)
          {
            localObject2 = new HashMap();
            localReportResult1.setProperties((HashMap)localObject2);
          }
          ((HashMap)localObject2).put("TRUNCATED", new Integer(i1));
        }
      }
      catch (Exception localException)
      {
        localReportResult1.setError(localException);
        throw new AdminException("ReportAuditAdapter.retrieveBCReport", 22, localException);
      }
      finally
      {
        DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
        try
        {
          localReportResult1.fini();
        }
        catch (RptException localRptException3)
        {
          String str10 = "Unable to generate the report.";
          throw new BCReportException(localRptException3, str1, 19, str10);
        }
      }
    }
    return localReportResult1;
  }
  
  public static String buildSortString(ReportSortCriteria paramReportSortCriteria, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    HashSet localHashSet = new HashSet();
    if (paramReportSortCriteria != null)
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      ReportSortCriterion localReportSortCriterion = null;
      String str1 = null;
      int i1 = 1;
      while (localIterator.hasNext())
      {
        localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        str1 = localReportSortCriterion.getName();
        int i2 = 0;
        if (localHashSet.contains(str1))
        {
          i2 = 1;
        }
        else
        {
          localHashSet.add(str1);
          String[] arrayOfString = null;
          String str2 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
          if (str1.equals("ProcessingDate"))
          {
            arrayOfString = new String[] { "a.LOG_DATE" };
          }
          else if (str1.equals("TransactionType"))
          {
            arrayOfString = new String[] { "t.description" };
          }
          else if (str1.equals("User"))
          {
            arrayOfString = new String[] { "cust.last_name", "cust.first_name" };
          }
          else if (str1.equals("TranId"))
          {
            arrayOfString = new String[] { "a.TRAN_ID" };
          }
          else
          {
            if (!str1.equals("Business")) {
              continue;
            }
            arrayOfString = new String[] { "bus.business_name" };
          }
          for (int i3 = 0; i3 < arrayOfString.length; i3++)
          {
            if (i1 == 0) {
              localStringBuffer.append(", ");
            }
            i1 = 0;
            localStringBuffer.append(arrayOfString[i3]);
            localStringBuffer.append(" ");
            localStringBuffer.append(str2);
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public static ArrayList buildWhereStringByAgent(Locale paramLocale, String paramString, Properties paramProperties, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportAuditAdapter.buildWhereString";
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new StringBuffer());
    String[] arrayOfString = null;
    if (paramString.equals("Bank Employee Activity Report")) {
      arrayOfString = C;
    } else if (paramString.equals("CSR Activity Report")) {
      arrayOfString = t;
    }
    Enumeration localEnumeration = paramProperties.keys();
    for (int i1 = 1; localEnumeration.hasMoreElements(); i1 = 1)
    {
      localObject1 = (String)localEnumeration.nextElement();
      for (int i2 = 0; i2 < arrayOfString.length; i2++) {
        if (((String)localObject1).equals(arrayOfString[i2]))
        {
          i1 = 0;
          break;
        }
      }
      if (i1 == 0) {}
    }
    Object localObject1 = paramProperties.keySet();
    Iterator localIterator = ((Set)localObject1).iterator();
    String str2 = null;
    String str3 = null;
    while (localIterator.hasNext())
    {
      str2 = (String)localIterator.next();
      str3 = paramProperties.getProperty(str2);
      Object localObject2;
      if (str2.equals("Agent"))
      {
        if ((str3.equals("")) || (str3.equals("AllAgents")))
        {
          localObject2 = new StringBuffer();
          ((StringBuffer)localObject2).append("a.AGENT_ID");
          ((StringBuffer)localObject2).append("=?");
          localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
        }
        else
        {
          localObject2 = new StringBuffer();
          ((StringBuffer)localObject2).append("a.AGENT_ID");
          ((StringBuffer)localObject2).append("='");
          ((StringBuffer)localObject2).append(DBUtil.escapeSQLStringLiteral(str3));
          ((StringBuffer)localObject2).append("'");
          localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
        }
      }
      else if (str2.equals("StartDate"))
      {
        localObject2 = null;
        try
        {
          localObject2 = jdField_if(str3, "MM/dd/yyyy HH:mm:ss", paramLocale);
        }
        catch (ParseException localParseException1)
        {
          throw new AdminException("ReportAuditAdapter.obtainRecordsFromCriteria", 13, str2 + " is not in proper format.");
        }
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("a.LOG_DATE");
        localStringBuffer.append(" >= ");
        localStringBuffer.append(DBUtil.fixDate(Profile.dbType, ((DateTime)localObject2).getTime()));
        localArrayList = a(localArrayList, localStringBuffer.toString(), "AND");
      }
      else
      {
        Object localObject3;
        if (str2.equals("EndDate"))
        {
          localObject2 = null;
          try
          {
            localObject2 = jdField_if(str3, "MM/dd/yyyy HH:mm:ss", paramLocale);
          }
          catch (ParseException localParseException2)
          {
            throw new AdminException("ReportAuditAdapter.obtainRecordsFromCriteria", 13, str2 + " is not in proper format.");
          }
          localObject3 = new StringBuffer();
          ((StringBuffer)localObject3).append("a.LOG_DATE");
          ((StringBuffer)localObject3).append(" <= ");
          ((StringBuffer)localObject3).append(DBUtil.fixDate(Profile.dbType, ((DateTime)localObject2).getTime()));
          localArrayList = a(localArrayList, ((StringBuffer)localObject3).toString(), "AND");
        }
        else if (!str2.equals("EndTime"))
        {
          if (str2.equals("Business"))
          {
            if ((str3.equals("")) || (str3.equals("AllBusinesses")) || (str3.equals("AllBusinessesAndConsumers")))
            {
              localObject2 = (Businesses)paramHashMap.get("BusinessesForReport");
              if (str3.equals("AllBusinesses"))
              {
                localObject3 = new StringBuffer();
                ((StringBuffer)localObject3).append("(");
                ((StringBuffer)localObject3).append("a.BUSINESS_ID");
                ((StringBuffer)localObject3).append(" IS NOT NULL OR (");
                ((StringBuffer)localObject3).append("a.BUSINESS_ID");
                ((StringBuffer)localObject3).append(" IS NULL AND ");
                ((StringBuffer)localObject3).append("a.USER_ID");
                ((StringBuffer)localObject3).append(" =' '))");
                localArrayList = a(localArrayList, ((StringBuffer)localObject3).toString(), "AND");
              }
              else if (!str3.equals("AllBusinessesAndConsumers")) {}
            }
            else if (str3.equals("AllConsumers"))
            {
              localObject2 = new StringBuffer();
              ((StringBuffer)localObject2).append("a.BUSINESS_ID");
              ((StringBuffer)localObject2).append(" IS NULL ");
              localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
            }
            else
            {
              localObject2 = new StringBuffer();
              ((StringBuffer)localObject2).append("a.BUSINESS_ID");
              ((StringBuffer)localObject2).append(" = ");
              DBUtil.trimSQLNumericLiteral(str3, (StringBuffer)localObject2);
              localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
            }
          }
          else if (str2.equals("User"))
          {
            localObject2 = new StringBuffer();
            localObject3 = paramProperties.getProperty("IncludeSecondaryUsers");
            if ((str3.equals("")) || (str3.equals("AllUsers")))
            {
              if ((localObject3 != null) && ((((String)localObject3).equals("off")) || (((String)localObject3).equals(""))))
              {
                ((StringBuffer)localObject2).append("(");
                ((StringBuffer)localObject2).append("a.PRIMARY_USER_ID");
                ((StringBuffer)localObject2).append(" = ");
                ((StringBuffer)localObject2).append("a.USER_ID_INT");
                ((StringBuffer)localObject2).append(" or ");
                ((StringBuffer)localObject2).append("a.USER_ID_INT");
                ((StringBuffer)localObject2).append(" is null ");
                ((StringBuffer)localObject2).append(")");
                localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
              }
            }
            else
            {
              if ((localObject3 != null) && (((String)localObject3).equals("on")))
              {
                ((StringBuffer)localObject2).append("(");
                ((StringBuffer)localObject2).append("a.PRIMARY_USER_ID");
                ((StringBuffer)localObject2).append(" = ");
                DBUtil.trimSQLNumericLiteral(str3, (StringBuffer)localObject2);
                ((StringBuffer)localObject2).append(" or ");
                ((StringBuffer)localObject2).append("a.USER_ID");
                ((StringBuffer)localObject2).append(" = '");
                ((StringBuffer)localObject2).append(DBUtil.escapeSQLStringLiteral(str3));
                ((StringBuffer)localObject2).append("')");
              }
              else
              {
                ((StringBuffer)localObject2).append("a.USER_ID");
                ((StringBuffer)localObject2).append(" = '");
                ((StringBuffer)localObject2).append(DBUtil.escapeSQLStringLiteral(str3));
                ((StringBuffer)localObject2).append("'");
              }
              localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
            }
          }
          else if (str2.equals("Module"))
          {
            if ((!str3.equals("")) && (!str3.equals("AllModules")))
            {
              localObject2 = new StringBuffer();
              ((StringBuffer)localObject2).append("a.MODULE");
              ((StringBuffer)localObject2).append(" = ");
              DBUtil.trimSQLNumericLiteral(str3, (StringBuffer)localObject2);
              localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
            }
          }
          else if (str2.equals("Activity"))
          {
            if ((!str3.equals("")) && (!str3.equals("AllActivities")))
            {
              localObject2 = DBUtil.generateSQLNumericInClause(str3, "a.TRAN_TYPE");
              localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
            }
          }
          else if ((str2.equals("Affiliate Bank")) && (!str3.equals("")) && (!str3.equals("AllAffiliateBanks")))
          {
            localObject2 = new StringBuffer();
            ((StringBuffer)localObject2).append("(");
            ((StringBuffer)localObject2).append("bus.affiliate_bank_id");
            ((StringBuffer)localObject2).append(" = ");
            DBUtil.trimSQLNumericLiteral(str3, (StringBuffer)localObject2);
            ((StringBuffer)localObject2).append(" OR ");
            ((StringBuffer)localObject2).append("cust.affiliate_bank_id");
            ((StringBuffer)localObject2).append(" = ");
            DBUtil.trimSQLNumericLiteral(str3, (StringBuffer)localObject2);
            ((StringBuffer)localObject2).append(" OR (");
            ((StringBuffer)localObject2).append("bus.affiliate_bank_id");
            ((StringBuffer)localObject2).append(" IS NULL AND ");
            ((StringBuffer)localObject2).append("cust.affiliate_bank_id");
            ((StringBuffer)localObject2).append(" IS NULL))");
            localArrayList = a(localArrayList, ((StringBuffer)localObject2).toString(), "AND");
          }
        }
      }
    }
    return localArrayList;
  }
  
  private static ArrayList a(ArrayList paramArrayList, String paramString1, String paramString2)
  {
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      StringBuffer localStringBuffer = (StringBuffer)paramArrayList.get(i1);
      if ((localStringBuffer.length() != 0) && (paramString2 != null))
      {
        localStringBuffer.append(" ");
        localStringBuffer.append(paramString2);
        localStringBuffer.append(" ");
      }
      localStringBuffer.append(paramString1);
    }
    return paramArrayList;
  }
  
  private static ArrayList a(String paramString1, ArrayList paramArrayList, String paramString2)
  {
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      StringBuffer localStringBuffer = (StringBuffer)paramArrayList.get(i1);
      if ((localStringBuffer.length() != 0) && (paramString2 != null))
      {
        localStringBuffer.insert(0, " ");
        localStringBuffer.insert(0, paramString2);
        localStringBuffer.insert(0, " ");
      }
      localStringBuffer.insert(0, paramString1);
    }
    return paramArrayList;
  }
  
  private static ArrayList a(ArrayList paramArrayList1, ArrayList paramArrayList2, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i1 = 0; i1 < paramArrayList2.size(); i1++)
    {
      StringBuffer localStringBuffer1 = (StringBuffer)paramArrayList2.get(i1);
      int i2;
      StringBuffer localStringBuffer2;
      if (i1 == paramArrayList2.size() - 1)
      {
        for (i2 = 0; i2 < paramArrayList1.size(); i2++)
        {
          localStringBuffer2 = (StringBuffer)paramArrayList1.get(i2);
          if ((localStringBuffer2.length() != 0) && (paramString != null))
          {
            localStringBuffer2.append(" ");
            localStringBuffer2.append(paramString);
            localStringBuffer2.append(" ");
          }
          localStringBuffer2.append(localStringBuffer1.toString());
        }
        localArrayList.addAll(paramArrayList1);
      }
      else
      {
        for (i2 = 0; i2 < paramArrayList1.size(); i2++)
        {
          localStringBuffer2 = (StringBuffer)paramArrayList1.get(i2);
          StringBuffer localStringBuffer3 = new StringBuffer(localStringBuffer2.toString());
          if ((localStringBuffer3.length() != 0) && (paramString != null))
          {
            localStringBuffer3.append(" ");
            localStringBuffer3.append(paramString);
            localStringBuffer3.append(" ");
          }
          localStringBuffer3.append(localStringBuffer1.toString());
          localArrayList.add(localStringBuffer3);
        }
      }
    }
    return localArrayList;
  }
  
  private static void jdField_if(ReportCriteria paramReportCriteria, Locale paramLocale, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportAuditAdapter.processReportCriteria";
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str1, 10, (String)localObject1);
    }
    Object localObject1 = localProperties.keySet();
    Iterator localIterator = ((Set)localObject1).iterator();
    String str2 = null;
    String str3 = null;
    while (localIterator.hasNext())
    {
      str2 = (String)localIterator.next();
      str3 = localProperties.getProperty(str2);
      Object localObject2;
      if (str2.equals("User"))
      {
        if ((str3.equals("")) || (str3.equals("AllUsers")))
        {
          paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, paramLocale));
        }
        else
        {
          localObject2 = (BusinessEmployee)paramHashMap.get("UserForSearch");
          if (localObject2 != null) {
            paramReportCriteria.setDisplayValue("User", ((BusinessEmployee)localObject2).getName());
          }
        }
      }
      else if (str2.equals("Agent"))
      {
        if ((str3.equals("")) || (str3.equals("AllAgents")))
        {
          paramReportCriteria.setDisplayValue("Agent", ReportConsts.getText(10022, paramLocale));
        }
        else
        {
          localObject2 = (BankEmployee)paramHashMap.get("AgentForSearch");
          if (localObject2 != null) {
            paramReportCriteria.setDisplayValue("Agent", ((BankEmployee)localObject2).getName());
          }
        }
      }
      else if (str2.equals("Group"))
      {
        if ((str3.equals("")) || (str3.equals("AllGroups")))
        {
          paramReportCriteria.setDisplayValue("Group", ReportConsts.getText(10021, paramLocale));
        }
        else
        {
          localObject2 = (String)paramHashMap.get("GroupNameForSearch");
          paramReportCriteria.setDisplayValue("Group", (String)localObject2);
        }
      }
      else if (str2.equals("TransactionType"))
      {
        if ((str3.equals("")) || (a(str3))) {
          paramReportCriteria.setDisplayValue("TransactionType", ReportConsts.getText(10007, paramLocale));
        } else {
          try
          {
            localObject2 = new StringTokenizer(str3, ",");
            StringBuffer localStringBuffer = new StringBuffer();
            while (((StringTokenizer)localObject2).hasMoreTokens())
            {
              String str5 = AuditLogUtil.getActivityNameFromID(((StringTokenizer)localObject2).nextToken(), paramLocale);
              if (localStringBuffer.length() != 0) {
                localStringBuffer.append(", ");
              }
              localStringBuffer.append(str5);
            }
            if (localStringBuffer.length() != 0) {
              paramReportCriteria.setDisplayValue("TransactionType", localStringBuffer.toString());
            }
          }
          catch (Exception localException1)
          {
            paramReportCriteria.setDisplayValue("TransactionType", str3);
          }
        }
      }
      else if (str2.equals("Module"))
      {
        if ((str3.equals("")) || (str3.equals("AllModules"))) {
          paramReportCriteria.setDisplayValue("Module", ReportConsts.getText(10044, paramLocale));
        } else {
          try
          {
            String str4 = ResourceUtil.getString("TransactionModuleName" + str3, "com.ffusion.beans.banking.resources", paramLocale);
            paramReportCriteria.setDisplayValue("Module", str4);
          }
          catch (Exception localException2)
          {
            paramReportCriteria.setDisplayValue("Module", str3);
          }
        }
      }
      else if (str2.equals("IncludeSecondaryUsers"))
      {
        if (str3.equals("on")) {
          paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10081, paramLocale));
        } else if (str3.equals("off")) {
          paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10082, paramLocale));
        }
      }
      else if ((str3 == null) || (str3.length() <= 0) || (str3.trim().length() <= 0)) {
        paramReportCriteria.setHiddenSearchCriterion(str2, true);
      }
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramString3 != null)
    {
      if (paramBoolean1) {
        paramStringBuffer.append(" AND ");
      }
      paramStringBuffer.append(" ");
      paramStringBuffer.append(paramString1);
      paramStringBuffer.append(" ");
      paramStringBuffer.append(paramString2);
      if (paramBoolean2) {
        paramStringBuffer.append("'");
      }
      paramStringBuffer.append(paramString3);
      if (paramBoolean2) {
        paramStringBuffer.append("'");
      }
      paramStringBuffer.append(" ");
    }
  }
  
  private static void a(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramStringBuffer.append(" AND ");
    }
    paramStringBuffer.append(DBUtil.generateSQLNumericInClause(paramString2, paramString1));
  }
  
  private static void a(Locale paramLocale, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    Set localSet = localProperties.keySet();
    Iterator localIterator = localSet.iterator();
    String str1 = null;
    String str2 = null;
    Connection localConnection = null;
    try
    {
      try
      {
        localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      }
      catch (Exception localException1)
      {
        throw new Exception("ReportAuditAdapter:Unable to get DB Connection.");
      }
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str2 = localProperties.getProperty(str1);
        Object localObject1;
        if (str1.equals("Business"))
        {
          if (str2.equals("AllBusinesses"))
          {
            paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramLocale));
          }
          else if (str2.equals("AllConsumers"))
          {
            paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10016, paramLocale));
          }
          else if (str2.equals("AllBusinessesAndConsumers"))
          {
            paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10014, paramLocale));
          }
          else
          {
            localObject1 = Profile.getBusinessNameAndCustId(localConnection, str2, paramLocale);
            paramReportCriteria.setDisplayValue("Business", (String)localObject1);
          }
        }
        else if (str1.equals("User"))
        {
          if (str2.equals("AllUsers"))
          {
            paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, paramLocale));
          }
          else
          {
            localObject1 = jdField_if(localConnection, str2, paramLocale);
            paramReportCriteria.setDisplayValue("User", (String)localObject1);
          }
        }
        else if (str1.equals("Agent"))
        {
          if (str2.equals("AllAgents")) {
            paramReportCriteria.setDisplayValue("Agent", ReportConsts.getText(10022, paramLocale));
          } else {
            paramReportCriteria.setDisplayValue("Agent", a(localConnection, str2, paramLocale));
          }
        }
        else if (str1.equals("Affiliate Bank"))
        {
          if (str2.equals("AllAffiliateBanks"))
          {
            paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, paramLocale));
          }
          else
          {
            localObject1 = (String)paramHashMap.get("AffiliateBankNameForReport");
            paramReportCriteria.setDisplayValue("Affiliate Bank", (String)localObject1);
          }
        }
        else
        {
          StringBuffer localStringBuffer;
          String str4;
          if (str1.equals("TransactionType"))
          {
            if (a(str2)) {
              paramReportCriteria.setDisplayValue("TransactionType", ReportConsts.getText(10025, paramLocale));
            } else {
              try
              {
                localObject1 = new StringTokenizer(str2, ",");
                localStringBuffer = new StringBuffer();
                while (((StringTokenizer)localObject1).hasMoreTokens())
                {
                  str4 = ResourceUtil.getString("TransactionTypeText" + ((StringTokenizer)localObject1).nextToken(), "com.ffusion.beans.banking.resources", paramLocale);
                  if (localStringBuffer.length() != 0) {
                    localStringBuffer.append(", ");
                  }
                  localStringBuffer.append(str4);
                }
                if (localStringBuffer.length() != 0) {
                  paramReportCriteria.setDisplayValue("TransactionType", localStringBuffer.toString());
                }
              }
              catch (Exception localException2)
              {
                paramReportCriteria.setDisplayValue("TransactionType", str2);
              }
            }
          }
          else if (str1.equals("Module"))
          {
            if (str2.equals("AllModules")) {
              paramReportCriteria.setDisplayValue("Module", ReportConsts.getText(10044, paramLocale));
            } else {
              try
              {
                String str3 = ResourceUtil.getString("TransactionModuleName" + str2, "com.ffusion.beans.banking.resources", paramLocale);
                paramReportCriteria.setDisplayValue("Module", str3);
              }
              catch (Exception localException3)
              {
                paramReportCriteria.setDisplayValue("Module", str2);
              }
            }
          }
          else if (str1.equals("Activity"))
          {
            if (str2.equals("AllActivities")) {
              paramReportCriteria.setDisplayValue("Activity", ReportConsts.getText(10007, paramLocale));
            } else {
              try
              {
                StringTokenizer localStringTokenizer = new StringTokenizer(str2, ",");
                localStringBuffer = new StringBuffer();
                while (localStringTokenizer.hasMoreTokens())
                {
                  str4 = ResourceUtil.getString("TransactionTypeText" + localStringTokenizer.nextToken(), "com.ffusion.beans.banking.resources", paramLocale);
                  if (localStringBuffer.length() != 0) {
                    localStringBuffer.append(", ");
                  }
                  localStringBuffer.append(str4);
                }
                paramReportCriteria.setDisplayValue("Activity", localStringBuffer.toString());
              }
              catch (Exception localException4)
              {
                paramReportCriteria.setDisplayValue("Activity", str2);
              }
            }
          }
          else if (str1.equals("IncludeSecondaryUsers")) {
            if (str2.equals("on")) {
              paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10081, paramLocale));
            } else if (str2.equals("off")) {
              paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10082, paramLocale));
            }
          }
        }
      }
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static String a(Connection paramConnection, String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      return "";
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "ReportAuditAdapter.getBusinessName";
    try
    {
      String str3 = " select business_name from business where business_id = " + paramString;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      if (localResultSet.next()) {
        str1 = localResultSet.getString(1);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.toString());
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return str1;
  }
  
  private static String jdField_if(Connection paramConnection, String paramString, Locale paramLocale)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      return "";
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "ReportAuditAdapter.getUserName";
    try
    {
      String str3 = " select c.first_name, c.last_name, c.user_name, cd.cust_id from customer c, customer_directory cd where c.directory_id=cd.directory_id and c.directory_id=" + paramString;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      if (localResultSet.next()) {
        str1 = UserUtil.getFullNameWithLoginId(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3), localResultSet.getString(4), paramLocale);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.toString());
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return str1;
  }
  
  private static String a(Connection paramConnection, String paramString, Locale paramLocale)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      return "";
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    String str2 = "ReportAuditAdapter.getagentName";
    try
    {
      String str3 = " select first_name, last_name, user_name, bank_employee_id from bank_employee where employee_id = " + paramString;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str3);
      if (localResultSet.next()) {
        str1 = UserUtil.getFullNameWithLoginId(localResultSet.getString(1), localResultSet.getString(2), localResultSet.getString(3), localResultSet.getString(4), paramLocale);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str2 + ": " + localException.toString());
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return str1;
  }
  
  private static boolean a(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (("AllTransactions".equals(str)) || ("AllTransactions".equals(str))) {
        return true;
      }
    }
    return false;
  }
  
  public static String getDisplayStatus(String paramString1, Locale paramLocale, String paramString2)
  {
    String str1 = null;
    if ((paramString1 == null) || (paramString1.trim().length() == 0)) {
      return "-";
    }
    String str2 = a(paramString2, paramLocale);
    if (str2 == null) {
      return paramString1;
    }
    try
    {
      str1 = ResourceUtil.getString(paramString1, str2, paramLocale);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "-";
    }
    return str1;
  }
  
  private static String a(String paramString, Locale paramLocale)
  {
    String str1 = "TransactionTypeModule" + paramString;
    String str2 = ResourceUtil.getString(str1, "com.ffusion.beans.banking.resources", paramLocale);
    String str3 = null;
    if (("2".equals(str2)) || ("26".equals(str2)) || ("39".equals(str2))) {
      str3 = "com.ffusion.beans.reporting.ach_status";
    } else if ("9".equals(str2)) {
      str3 = "com.ffusion.beans.reporting.billpay_status";
    } else if (("23".equals(str2)) || ("32".equals(str2)) || ("33".equals(str2)) || ("34".equals(str2)) || ("35".equals(str2)) || ("36".equals(str2))) {
      str3 = "com.ffusion.beans.reporting.wire_status";
    } else if (("27".equals(str2)) || ("8".equals(str2)) || ("29".equals(str2))) {
      str3 = "com.ffusion.beans.reporting.transfer_status";
    } else if ("28".equals(str2)) {
      str3 = "com.ffusion.beans.reporting.cashcon_status";
    } else if ("21".equals(str2)) {
      str3 = "com.ffusion.beans.reporting.stoppayment_status";
    } else {
      str3 = "com.ffusion.beans.reporting.reports";
    }
    return str3;
  }
  
  private static boolean a(Properties paramProperties, Locale paramLocale)
  {
    String str1 = paramProperties.getProperty("Module");
    String str2 = paramProperties.getProperty("Activity");
    String str3 = paramProperties.getProperty("TransactionType");
    boolean bool = true;
    if ((str1 != null) && (!str1.equals("")))
    {
      TransactionTypes localTransactionTypes = null;
      if (!str1.equals("AllModules")) {
        localTransactionTypes = AdminHandler.getTransactionTypesForModule(paramLocale, str1);
      }
      if (localTransactionTypes != null) {
        if (localTransactionTypes.size() > 0)
        {
          StringTokenizer localStringTokenizer;
          int i1;
          if ((str2 != null) && (!str2.equals("")) && (!str2.equals("AllActivities")))
          {
            localStringTokenizer = new StringTokenizer(str2, ",");
            i1 = -1;
            while (localStringTokenizer.hasMoreTokens())
            {
              i1 = Integer.parseInt(localStringTokenizer.nextToken());
              if (localTransactionTypes.getById(i1) == null) {
                bool = false;
              }
            }
          }
          if ((str3 != null) && (!str3.equals("")) && (!str3.equals("AllTransactions")))
          {
            localStringTokenizer = new StringTokenizer(str3, ",");
            i1 = -1;
            while (localStringTokenizer.hasMoreTokens())
            {
              i1 = Integer.parseInt(localStringTokenizer.nextToken());
              if (localTransactionTypes.getById(i1) == null) {
                bool = false;
              }
            }
          }
        }
        else
        {
          if ((str2 != null) && (!str2.equals("")) && (!str2.equals("AllActivities"))) {
            bool = false;
          }
          if ((str3 != null) && (!str3.equals("")) && (!str3.equals("AllTransactions"))) {
            bool = false;
          }
        }
      }
    }
    return bool;
  }
  
  private static String a(String paramString1, String paramString2, Locale paramLocale)
  {
    String str2 = null;
    try
    {
      str2 = AccountUtil.buildAccountDisplayText(paramString2, paramLocale);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing("Error while constructing account display string.", localUtilException);
    }
    String str1;
    if (paramString1 == null)
    {
      if (str2 == null) {
        str1 = "";
      } else {
        str1 = str2;
      }
    }
    else if (str2 == null) {
      str1 = paramString1;
    } else {
      str1 = paramString1 + ":" + str2;
    }
    return str1;
  }
  
  private static String a(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString1 != null)
    {
      localStringBuffer.append(paramString1);
      if (paramString2 != null)
      {
        localStringBuffer.append(" ");
        localStringBuffer.append(paramString2);
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ReportAuditAdapter
 * JD-Core Version:    0.7.0.1
 */