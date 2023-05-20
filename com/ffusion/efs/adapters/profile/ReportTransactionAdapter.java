package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportRows;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.csil.handlers.FXHandler;
import com.ffusion.csil.handlers.PaymentsAdminHandler;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.admin.AdminException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

public class ReportTransactionAdapter
{
  private static String bd = null;
  private static final String aX = "AUDIT_LOG";
  private static final String e = " union ";
  private static final String bG = "=";
  private static final String Q = "'";
  private static final String aa = " and ";
  private static final String U = " or ";
  private static final String j = " is null ";
  private static final String aP = "AUDIT_LOG.BUSINESS_ID";
  private static final String N = "AUDIT_LOG.FROM_ACCT_ID";
  private static final String x = "AUDIT_LOG.USER_ID";
  private static final String t = "AUDIT_LOG.USER_ID_INT";
  private static final String ac = "AUDIT_LOG.PRIMARY_USER_ID";
  private static final String bB = "AUDIT_LOG.STATE";
  private static final String c = "AUDIT_LOG.TRAN_TYPE";
  private static final String V = "AUDIT_LOG.AMOUNT";
  private static final String L = "AUDIT_LOG.TO_AMOUNT";
  private static final String jdField_new = "AUDIT_LOG.LOG_DATE";
  private static final String aZ = "AUDIT_LOG.SRVR_TID";
  private static final String aK = "Wire.WireDest";
  private static final String bt = " in ";
  private static final String P = "(";
  private static final String jdField_else = ")";
  private static final String aT = ",";
  private static final String bH = " >= ";
  private static final String bn = " <= ";
  private static final String D = " ? ";
  private static final String[] aQ = { "Business", "Account", "User", "Status", "TransactionType", "FromAmount", "ToAmount", "StartDate", "EndDate" };
  private static String ax = "SELECT DISTINCT affiliate_bank.affiliate_name, SCH_ScheduleHistory.ProcessId, SCH_ScheduleHistory.LogDate, SCH_ScheduleHistory.FileName, SCH_ScheduleHistory.CutOffDay, SCH_ScheduleHistory.CutOffProcessTime, SCH_ScheduleHistory.CutOffId, ETF_ACHFile.FileId, ETF_ACHFile.CreateDate, ETF_ACHFile.TotalDebit, ETF_ACHFile.TotalCredit, ETF_ACHFile.NumberOfCredits, ETF_ACHFile.NumberOfDebits, ETF_ACHFile.FileStatus, SCH_ScheduleHistory.EventTrigger, ETF_ACHFile.ExportFileName FROM SCH_ScheduleHistory, affiliate_bank, ETF_ACHFile WHERE ";
  private static String aJ = "SCH_ScheduleHistory.FIId = affiliate_bank.bpw_fi_id and SCH_ScheduleHistory.ProcessId is not null and SCH_ScheduleHistory.ProcessId = ETF_ACHFile.ProcessId and SCH_ScheduleHistory.InstructionType = ";
  private static String I = "'ETFTRN'";
  private static String ay = "order by SCH_ScheduleHistory.LogDate, SCH_ScheduleHistory.CutOffDay, SCH_ScheduleHistory.CutOffProcessTime";
  private static final String bu = "SELECT AUDIT_LOG.LOG_DATE, AUDIT_LOG.TRAN_ID, AUDIT_LOG.STATE, AUDIT_LOG.AGENT_ID, AUDIT_LOG.USER_ID, AUDIT_LOG.BUSINESS_ID, AUDIT_LOG.TRAN_TYPE, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.AMOUNT, wire.OrigAmount, wire.WireDest, wire.RecSrvrTID, wire.SrvrTID, wire.ConfirmNum, wire.ConfirmNum2, wire.DateToPost, wire.OrigCurrency, customer.user_name, business.business_name, accounts.nickname, accounts.currency_type, accounts.routing_num, business.business_CIF, wire.DatePosted, wire.DateCreate FROM BPW_WireInfo wire, customer, AUDIT_LOG, business, accounts WHERE ";
  private static final String jdField_for = "SELECT AUDIT_LOG.LOG_DATE, AUDIT_LOG.TRAN_ID, AUDIT_LOG.STATE, AUDIT_LOG.AGENT_ID, AUDIT_LOG.USER_ID, AUDIT_LOG.BUSINESS_ID, AUDIT_LOG.TRAN_TYPE, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.AMOUNT, wire.OrigAmount, wire.WireDest, wire.RecSrvrTID, wire.SrvrTID, wire.ConfirmNum, wire.ConfirmNum2, wire.DateToPost, wire.OrigCurrency, customer.user_name, business.business_name,  business.business_CIF, wire.DatePosted, wire.DateCreate FROM BPW_WireInfo wire, customer, AUDIT_LOG, business WHERE ";
  private static final String jdField_int = "HOST";
  private static final String aL = "business.affiliate_bank_id=? and ";
  private static final String at = "AUDIT_LOG.BUSINESS_ID=? and ";
  private static final String aF = "AUDIT_LOG.USER_ID=? and ";
  private static final String ah = "AUDIT_LOG.LOG_DATE>=? and ";
  private static final String bM = "AUDIT_LOG.LOG_DATE<=? and ";
  private static final String aM = "AUDIT_LOG.FROM_ACCT_ID=? and ";
  private static final String aS = "wire.WireDest=? and ";
  private static final String bL = "AUDIT_LOG.AMOUNT>=? and ";
  private static final String M = "AUDIT_LOG.AMOUNT<=? and ";
  private static final String ak = "AUDIT_LOG.STATE=? and ";
  private static final String ar = "wire.WireDest='HOST' and ";
  private static final String aE = "AUDIT_LOG.BUSINESS_ID not in ( ";
  private static final String X = "AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ";
  private static final String Y = "wire.Status=? and ";
  private static final String am = "(AUDIT_LOG.BUSINESS_ID = business.business_id) AND (wire.ExtId = AUDIT_LOG.TRAN_ID)  AND (AUDIT_LOG.USER_ID_INT = customer.directory_id) AND (accounts.directory_id = AUDIT_LOG.BUSINESS_ID) AND (accounts.account_id = AUDIT_LOG.FROM_ACCT_ID OR accounts.account_id = AUDIT_LOG.TO_ACCT_ID)";
  private static final String bq = "(AUDIT_LOG.BUSINESS_ID = business.business_id) AND (wire.ExtId = AUDIT_LOG.TRAN_ID)  AND (AUDIT_LOG.USER_ID_INT = customer.directory_id)";
  private static String jdField_case = "SELECT DISTINCT affiliate_bank.affiliate_name, SCH_ScheduleHistory.ProcessId, SCH_ScheduleHistory.LogDate, SCH_ScheduleHistory.FileName, SCH_ScheduleHistory.CutOffDay, SCH_ScheduleHistory.CutOffProcessTime, SCH_ScheduleHistory.CutOffId, SCH_ScheduleHistory.EventTrigger FROM SCH_ScheduleHistory, affiliate_bank WHERE ";
  private static String ae = "SCH_ScheduleHistory.FIId = affiliate_bank.bpw_fi_id and SCH_ScheduleHistory.ProcessId is not null and SCH_ScheduleHistory.InstructionType = ";
  private static String af = "'ACHBATCHTRN' ";
  private static String bm = "'ACHFILETRN' ";
  private static String aR = "SCH_ScheduleHistory.LogDate >= ? and ";
  private static String a3 = "SCH_ScheduleHistory.LogDate <= ? and ";
  private static String u = "SCH_ScheduleHistory.FIId = (select affiliate_bank.bpw_fi_id from affiliate_bank where affiliate_bank.affiliate_bank_id = ?) and ";
  private static String O = "order by SCH_ScheduleHistory.LogDate, affiliate_bank.affiliate_name";
  private static String jdField_void = "SELECT DISTINCT affiliate_bank.affiliate_name, SCH_ScheduleHistory.ProcessId, SCH_ScheduleHistory.LogDate, SCH_ScheduleHistory.FileName, SCH_ScheduleHistory.CutOffDay, SCH_ScheduleHistory.CutOffProcessTime, affiliate_bank.affiliate_bank_id, SCH_ScheduleHistory.CutOffId FROM  SCH_ScheduleHistory, SCH_InstructionType, affiliate_bank WHERE SCH_ScheduleHistory.FileName is not null and ";
  private static String R = "SCH_ScheduleHistory.FIId = affiliate_bank.bpw_fi_id and SCH_ScheduleHistory.InstructionType = 'CASHCONTRN' ";
  private static String bE = "SCH_ScheduleHistory.LogDate >= ? and ";
  private static String k = "SCH_ScheduleHistory.LogDate <= ? and ";
  private static String T = "SCH_ScheduleHistory.FIId = (select affiliate_bank.bpw_fi_id from affiliate_bank where affiliate_bank.affiliate_bank_id = ?) and ";
  private static String F = "order by affiliate_bank.affiliate_name, SCH_ScheduleHistory.ProcessId, SCH_ScheduleHistory.LogDate";
  private static String by = "SELECT CC_Company.CompName, CC_Company.CCCompId, CC_Company.BatchType, CC_Company.ConcentrateAcctId, CC_Company.DisburseAcctId, CC_BatchHist.BatchNumber, CC_BatchHist.EffectEntryDate, CC_BatchHist.NumOfDeposit, CC_BatchHist.NumOfDisburse, CC_BatchHist.TotalCreditAmt, CC_BatchHist.TotalDebitAmt, CC_BatchHist.TransactionType FROM CC_Company, CC_BatchHist WHERE CC_BatchHist.ProcessId = ? and CC_BatchHist.CompId = CC_Company.CompId";
  private static String a9 = "select AcctId, AcctNumber from BPW_BankAcct";
  private static final String S = "select CC_Entry.CreatedDate from CC_Entry where CC_Entry.LocationId = ? and CC_Entry.CreatedDate <= ? order by CC_Entry.CreatedDate desc";
  private static final String a1 = "select CC_Entry.CreatedDate from CC_Entry where CC_Entry.LocationId = ? ";
  private static final String ai = " order by CC_Entry.CreatedDate desc";
  private static final String m = "SELECT DISTINCT BPW_Customer.FirstName, CC_Company.CompName, entitlement_group.name, CC_Location.LocationName, CC_Location.LocationId, CC_Location.LastRequestTime FROM CC_Location, location, entitlement_group,  CC_Company, BPW_Customer WHERE ";
  private static final String w = " CC_Company.CustomerId = BPW_Customer.CustomerID and CC_Location.CompId = CC_Company.CompId and location.LOCATION_BPW_ID = CC_Location.LocationId and location.DIVISION_ENT_GROUP_ID = entitlement_group.ent_group_id ";
  private static final String ba = "SELECT AUDIT_LOG.LOG_DATE FROM CC_Location, AUDIT_LOG WHERE   CC_Location.LocationId = ? and  AUDIT_LOG.TRAN_TYPE=5303 and CC_Location.LogId=AUDIT_LOG.TRAN_ID";
  private static final String bj = " ORDER BY BPW_Customer.FirstName, CC_Company.CompName, entitlement_group.name, CC_Location.LocationName";
  private static final String G = "BPW_Customer.CustomerID = ? and ";
  private static final String az = "BPW_Customer.FIID = ? and ";
  private static String E = "SELECT distinct BPW_Customer.FirstName, BPW_Customer.CustomerID, CC_Company.CompName, CC_Company.CompId, CC_Company.ConcentrateAcctId, CC_Company.DisburseAcctId,CC_Company.BatchType FROM  BPW_Customer, CC_Company WHERE ";
  private static String ad = " BPW_Customer.FIID = ? and ";
  private static String jdField_byte = " BPW_Customer.CustomerID = ? and ";
  private static String f = "CC_Company.CustomerId = BPW_Customer.CustomerID ";
  private static String bA = "order by BPW_Customer.FirstName, CC_Company.CompName";
  private static String be = "SELECT SCH_CutOffs.LastProcessTime FROM CC_CompanyCutOff, SCH_CutOffs WHERE CC_CompanyCutOff.CompId = ? and CC_CompanyCutOff.CutOffId = SCH_CutOffs.CutOffId ORDER BY SCH_CutOffs.NextProcessTime";
  private static String ag = "SELECT LocationId, LocationName, ConcentrateAcctId, DisburseAcctId FROM CC_Location where CompId = ?";
  private static String K = "SELECT CC_CompanyAcct.AcctId, BPW_BankAcct.AcctNumber, CC_CompanyAcct.TransactionType, BPW_BankAcct.AcctNickName, BPW_BankAcct.BankRTN, BPW_BankAcct.Currency, BPW_BankAcct.AcctType FROM  CC_CompanyAcct, BPW_BankAcct WHERE CC_CompanyAcct.CompId = ? and CC_CompanyAcct.AcctId = BPW_BankAcct.AcctId";
  private static String bl = "SELECT entitlement_group.name, CC_Location.LocationName, CC_Location.LocationId, CC_Location.ConcentrateAcctId, CC_Location.DisburseAcctId FROM  CC_Location, location, entitlement_group WHERE CC_Location.CompId = ? and location.LOCATION_BPW_ID = CC_Location.LocationId and location.DIVISION_ENT_GROUP_ID = entitlement_group.ent_group_id ORDER BY entitlement_group.name, CC_Location.LocationName";
  private static String n = "SELECT entitlement_group.name, CC_Location.LocationName, CC_Location.LocationId, CC_Location.ConcentrateAcctId, CC_Location.DisburseAcctId, CC_Location.AccountNum, CC_Location.AccountType, CC_Location.BankRtn FROM  CC_Location, location, entitlement_group WHERE ";
  private static String p = "CC_Location.CompId = ? and location.LOCATION_BPW_ID = CC_Location.LocationId and location.DIVISION_ENT_GROUP_ID = entitlement_group.ent_group_id ";
  private static String l = "SELECT CC_Entry.CreatedDate, CC_Entry.TransactionType, CC_Entry.SubmittedBy, CC_Entry.Amount,CC_Entry.LocationId, CC_Entry.ProcessedTime, CC_Entry.LogId, CC_Entry.Status, CC_Entry.EntryCategory FROM CC_Entry WHERE ";
  private static String bD = "CC_Entry.LocationId = ? ";
  private static String bx = "ORDER BY CC_Entry.DueDate, CC_Entry.Amount";
  private static String bw = "ORDER BY CC_Entry.Amount, CC_Entry.DueDate";
  private static String bf = "CC_Entry.Status = ? and ";
  private static String B = "CC_Entry.DueDate >= ? and ";
  private static String ap = "CC_Entry.DueDate <= ? and ";
  private static String bC = "CC_Entry.SubmittedBy = ? and ";
  private static String bF = "CC_Entry.TransactionType in ";
  private static String h = "CC_Entry.EntryCategory in ";
  private static String jdField_char = "entitlement_group.ent_group_id = ? and ";
  private static String a = "CC_Location.LocationId = ? and ";
  private static final String bb = "(AUDIT_LOG.STATE='BACKENDFAILED' OR AUDIT_LOG.STATE='RELEASEFAILED' OR AUDIT_LOG.STATE='FUNDSFAILEDON' OR AUDIT_LOG.STATE='FUNDSFAILEDON_NOTIF' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED_NOTIF') and ";
  private static final String bI = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, intratx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, biz.business_name as busname, intratx.ConfirmNum, biz.business_name as busname2, accts.nickname, accts.currency_type, accts.routing_num, biz.business_CIF as bizCif, intratx.DateCreate, intratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, business biz, affiliate_bank, BPW_XferInstruction intratx, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT  left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID  left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
  private static final String a0 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, intratx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, '' as busname, intratx.ConfirmNum, '' as busname2, accts.nickname, accts.currency_type, accts.routing_num, '' as bizCif, intratx.DateCreate, intratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, BPW_XferInstruction intratx, affiliate_bank, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT  left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID  left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
  private static final String jdField_long = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, extratx.AmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE  as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, biz.business_name as busname, extratx.LogId, biz.business_name as busname2, accts.nickname, AUDIT_LOG.CURRENCY_CODE, accts.routing_num, biz.business_CIF as bizCif, extratx.DateCreate, extratx.DateToPost, pri.first_name, pri.last_name ,AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, business biz, affiliate_bank, BPW_Transfer extratx, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
  private static final String ao = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, extratx.AmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE  as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, accts.nickname, AUDIT_LOG.CURRENCY_CODE, accts.routing_num, '' as bizCif, extratx.DateCreate, extratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, BPW_Transfer extratx, affiliate_bank, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
  private static final String bJ = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, ETF_ExternalAcct.CustomerId, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID as acctid, extratx.ToAmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, '' as acctNickName, AUDIT_LOG.CURRENCY_CODE, ETF_ExternalAcct.AcctBankRtn, '' as bizCif, extratx.DateCreate, extratx.DateToPost, affiliate_bank.affiliate_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from ETF_ExternalAcct, BPW_Transfer extratx, AUDIT_LOG, BPW_Customer, BPW_FIInfo, affiliate_bank ";
  private static final String ab = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, pmttx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, payee.PayeeName, pmttx.LogID, biz.business_name as busname, accts.nickname, accts.currency_type, accts.routing_num, biz.business_CIF as bizCif, pri.first_name, pri.last_name from accounts accts, business biz, affiliate_bank, BPW_PmtInstruction pmttx left outer join BPW_PmtHist hist on hist.SrvrTID = pmttx.SrvrTID, AUDIT_LOG left outer join customer cust on AUDIT_LOG.USER_ID_INT = cust.directory_id left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID, BPW_Payee payee ";
  private static final String br = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, pmttx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, payee.PayeeName, pmttx.LogID, '' as busname, accts.nickname, accts.currency_type, accts.routing_num, '' as bizCif, pri.first_name, pri.last_name from accounts accts, affiliate_bank, BPW_PmtInstruction pmttx left outer join BPW_PmtHist hist on hist.SrvrTID = pmttx.SrvrTID, AUDIT_LOG left outer join customer cust on AUDIT_LOG.USER_ID_INT = cust.directory_id left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID, BPW_Payee payee ";
  private static final String aI = "where AUDIT_LOG.SRVR_TID = intratx.SrvrTID and AUDIT_LOG.BUSINESS_ID = biz.business_id and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and accts.directory_id = AUDIT_LOG.BUSINESS_ID and biz.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
  private static final String bv = "where AUDIT_LOG.SRVR_TID = intratx.SrvrTID and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
  private static final String J = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and AUDIT_LOG.BUSINESS_ID = biz.business_id and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and accts.directory_id = AUDIT_LOG.BUSINESS_ID and biz.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
  private static final String aN = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
  private static final String aV = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and extratx.ExternalAcctId = ETF_ExternalAcct.AcctId and BPW_Customer.VirtualCustomer='Y' and BPW_Customer.CustomerID=extratx.CustomerId and BPW_FIInfo.FIId = BPW_Customer.FIID and BPW_FIInfo.FIId = affiliate_bank.bpw_fi_id and extratx.FIId = BPW_FIInfo.FIId ";
  private static final String jdField_if = "where AUDIT_LOG.SRVR_TID = pmttx.SrvrTID and AUDIT_LOG.BUSINESS_ID = biz.business_id and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and payee.PayeeID = pmttx.PayeeID and accts.directory_id = AUDIT_LOG.BUSINESS_ID and biz.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
  private static final String bk = "where AUDIT_LOG.SRVR_TID = pmttx.SrvrTID and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and payee.PayeeID = pmttx.PayeeID and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
  private static final String aC = " AUDIT_LOG.STATE ";
  private static final String bc = " AUDIT_LOG.STATE=intratx.Status ";
  private static final String v = " intratx.Status ";
  private static final String aq = ", auditState asc ";
  private static final String q = " AUDIT_LOG.STATE ";
  private static final String g = " AUDIT_LOG.STATE=pmttx.Status ";
  private static final String jdField_goto = " pmttx.Status ";
  private static final String a7 = " AUDIT_LOG.STATE ";
  private static final String bo = " AUDIT_LOG.STATE=extratx.Status and extratx.Status != 'TEMPLATE' and extratx.Status != 'RECTEMPLATE' ";
  private static final String jdField_try = " extratx.Status ";
  private static final String d = " order by acctid asc ";
  private static final String a6 = " order by busname asc, busid asc, acctid asc ";
  private static final String b = " order by acctid asc ";
  private static final String bN = " order by acctid asc ";
  private static final String bO = " order by busid asc, busname asc, acctid asc ";
  private static final String y = " order by acctid asc ";
  private static final String a8 = "FAILEDON";
  private static final String aY = "-1";
  private static final String[] al = { Integer.toString(4600), Integer.toString(4601), Integer.toString(4602), Integer.toString(4603), Integer.toString(4604), Integer.toString(4605), Integer.toString(4606), Integer.toString(4607), Integer.toString(4650), Integer.toString(4651), Integer.toString(3603), Integer.toString(1901), Integer.toString(1903), Integer.toString(1904), Integer.toString(1905), Integer.toString(1906) };
  private static final String[] as = { Integer.toString(4400), Integer.toString(4401), Integer.toString(4402), Integer.toString(4403), Integer.toString(4404), Integer.toString(4405), Integer.toString(4406), Integer.toString(4407), Integer.toString(4408), Integer.toString(4409), Integer.toString(4410), Integer.toString(4411), Integer.toString(4412), Integer.toString(4413), Integer.toString(4414), Integer.toString(4415), Integer.toString(4416), Integer.toString(4417), Integer.toString(4418), Integer.toString(4419), Integer.toString(4420), Integer.toString(4450), Integer.toString(4451), Integer.toString(4452), Integer.toString(4453), Integer.toString(4454), Integer.toString(4455), Integer.toString(4456), Integer.toString(4457), Integer.toString(4458), Integer.toString(4459), Integer.toString(4460), Integer.toString(4461), Integer.toString(4462), Integer.toString(4463), Integer.toString(4464), Integer.toString(4465), Integer.toString(4466), Integer.toString(4467), Integer.toString(4468), Integer.toString(4469), Integer.toString(3601) };
  private static final int aA = 1;
  private static final int aO = 2;
  private static final int a2 = 3;
  private static final int aj = 4;
  private static final int bz = 5;
  private static final int bs = 6;
  private static final int s = 7;
  private static final int A = 8;
  private static final int C = 9;
  private static final int r = 10;
  private static final int aw = 11;
  private static final int bp = 12;
  private static final int aD = 13;
  private static final int aW = 14;
  private static final int z = 15;
  private static final int bK = 16;
  private static final int jdField_null = 17;
  private static final String bh = "select BPW_BankAcct.AcctId,BPW_BankAcct.AcctNumber,BPW_BankAcct.AcctNickName from BPW_BankAcct";
  private static final String au = "select DISTINCT business.business_name,ACH_Company.CompName,ACH_Company.CompACHId,AUDIT_LOG.LOG_DATE,AUDIT_LOG.USER_ID,ACH_Batch.NonOffTotalDebit,ACH_Batch.NonOffTotalCredit,AUDIT_LOG.STATE,ACH_Batch.ExportFileName,ACH_Batch.Memo,ACH_Batch.BatchId,ACH_Batch.StdClassCode,ACH_Batch.BatchCategory,AUDIT_LOG.TRAN_ID,ACH_Batch.DueDate,ACH_Batch.OffsetAccountID,customer_directory.cust_id from AUDIT_LOG, business, customer,ACH_Company, ACH_Batch, customer_directory  where ACH_Batch.CompId=ACH_Company.CompId and business.directory_id=customer_directory.directory_id and business.business_id=AUDIT_LOG.BUSINESS_ID and AUDIT_LOG.TRAN_ID=ACH_Batch.LogId and AUDIT_LOG.SRVR_TID=ACH_Batch.BatchId  and customer.directory_id=AUDIT_LOG.USER_ID_INT ";
  private static final String Z = " and (ACH_Batch.BatchCategory = 'ACH_BATCH_PAYMENT' OR ACH_Batch.BatchCategory = 'ACH_BATCH_REVERSAL' OR ACH_Batch.BatchCategory = 'ACH_BATCH_PRENOTE') ";
  private static final String H = " and ACH_Batch.BatchCategory = 'ACH_BATCH_TAX' ";
  private static final String aH = " and ACH_Batch.BatchCategory = 'ACH_BATCH_CHILD_SUPPORT' ";
  private static final String W = " and business.affiliate_bank_id=?";
  private static final String an = " and AUDIT_LOG.BUSINESS_ID=?";
  private static final String a5 = " and customer.directory_id=?";
  private static final String a4 = " and ACH_Batch.CompId=?";
  private static final String bi = " and AUDIT_LOG.STATE=?";
  private static final String bg = " and AUDIT_LOG.LOG_DATE>=? ";
  private static final String i = " and AUDIT_LOG.LOG_DATE<=? ";
  private static final String aB = " Order by business.business_name, ACH_Company.CompName, ";
  private static final String aG = " Order by AUDIT_LOG.STATE, business.business_name, ACH_Company.CompName, ";
  private static final String o = "ACH_Batch.OffsetAccountID,ACH_Batch.BatchId";
  private static final String av = "select count (RecordId) from ACH_Record where TransCode in ('20','21','22','23','24','30','31','32','33','34','41','42','43','44','51','52','53','54') and BatchId=?";
  private static final String aU = "select count (RecordId) from ACH_Record where TransCode in ('25','26','27','28','29','35','36','37','38','39','46','47','48','49','55','56') and BatchId=?";
  private static final String jdField_do = "  and AUDIT_LOG.STATE=ACH_Batch.BatchStatus ";
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws AdminException, BCReportException
  {
    String str1 = "ReportTransactionAdapter.getReportData";
    if (Profile.poolName == null) {
      throw new AdminException(str1, 22, "ReportTransactionAdapter:Unable to create DB connection pool");
    }
    bd = Profile.poolName;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      throw new AdminException(str1, 10, (String)localObject1);
    }
    Object localObject1 = paramReportCriteria.getReportOptions();
    if (localObject1 == null)
    {
      str2 = "The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new AdminException(str1, 12, str2);
    }
    String str2 = ((Properties)localObject1).getProperty("REPORTTYPE");
    if (str2 == null)
    {
      localObject2 = "The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.";
      throw new AdminException(str1, 11, (String)localObject2);
    }
    Object localObject2 = null;
    Locale localLocale = paramSecureUser.getLocale();
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    if ((str2.equals("Wire Report")) || (str2.equals("Wire Exception Items Report"))) {
      localObject2 = a(paramSecureUser, paramReportCriteria, str2);
    } else if ((str2.equals("ACH Batch Report")) || (str2.equals("Tax Payment Report")) || (str2.equals("Child Support Payment Report"))) {
      localObject2 = jdField_do(paramSecureUser, str2, paramReportCriteria, paramHashMap);
    } else if ((str2.equals("ACH File Report")) || (str2.equals("ACH File Upload Report"))) {
      localObject2 = jdField_if(paramSecureUser, str2, paramReportCriteria, paramHashMap);
    } else if ((str2.equals("Bill Payment Report")) || (str2.equals("Bill Payment Exception Items Report"))) {
      localObject2 = a(paramSecureUser, paramReportCriteria, localProperties, localReportSortCriteria, str2, localLocale, paramHashMap);
    } else if ((str2.equals("Transfer Report")) || (str2.equals("Transfer Exception Items Report")) || (str2.equals("External Transfer Deposit Verification Report")) || (str2.equals("External Transfer Report")) || (str2.equals("External Transfer Exception Items Report"))) {
      localObject2 = a(paramSecureUser, paramReportCriteria, localProperties, localReportSortCriteria, str2, localLocale, paramHashMap);
    } else if ((str2.equals("Cash Concentration Cut-Off Status Report")) || (str2.equals("Cash Concentration Inactive Divisions and Locations Report")) || (str2.equals("Cash Concentration Company Activity Report")) || (str2.equals("Cash Concentration Activity Report")) || (str2.equals("Inactive or Non-Reporting Divisions and Locations Report"))) {
      localObject2 = a(paramSecureUser, paramReportCriteria, str2, paramHashMap);
    } else if (str2.equals("External Transfer ACH File Report")) {
      localObject2 = a(paramSecureUser, str2, paramReportCriteria, paramHashMap);
    } else if ((str2.equals("CB ACH File Upload")) || (str2.equals("ACH File Upload"))) {
      localObject2 = a(paramSecureUser, paramReportCriteria, paramHashMap);
    } else {
      throw new AdminException(str1, 11, str2 + " is not a valid report type");
    }
    return localObject2;
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, int paramInt1, int paramInt2)
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(paramInt1);
    localReportDataDimensions.setNumColumns(paramInt2);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  private static void a(ReportResult paramReportResult, boolean paramBoolean)
    throws RptException
  {
    a(paramReportResult, 1960, null, 15, null);
    a(paramReportResult, 1961, null, 8, null);
    if (paramBoolean) {
      a(paramReportResult, 1973, null, 8, null);
    }
    a(paramReportResult, 1962, null, 8, null);
    a(paramReportResult, 1963, null, 6, null);
    a(paramReportResult, 1964, null, 12, null);
    a(paramReportResult, 1966, null, 10, null);
    a(paramReportResult, 1967, null, 29, null);
  }
  
  private static void jdField_if(ReportResult paramReportResult, boolean paramBoolean)
    throws RptException
  {
    a(paramReportResult, paramBoolean, false);
  }
  
  private static void a(ReportResult paramReportResult, boolean paramBoolean1, boolean paramBoolean2)
    throws RptException
  {
    a(paramReportResult, 1940, null, 8, null);
    a(paramReportResult, 1953, null, 8, null);
    a(paramReportResult, 1954, null, 7, null);
    a(paramReportResult, 1941, null, 8, null);
    if (paramBoolean1) {
      a(paramReportResult, 1955, null, 8, null);
    }
    a(paramReportResult, 1942, null, 9, null);
    a(paramReportResult, 1943, null, 9, null);
    if (paramBoolean2)
    {
      a(paramReportResult, 1956, null, 9, null);
      a(paramReportResult, 1957, null, 9, null);
    }
    else
    {
      a(paramReportResult, 1944, null, 12, null);
    }
    a(paramReportResult, 1946, null, 12, null);
    a(paramReportResult, 1947, null, 28, null);
  }
  
  private static void jdField_do(ReportResult paramReportResult)
    throws RptException
  {
    a(paramReportResult, 1940, null, 8, null);
    a(paramReportResult, 1953, null, 8, null);
    a(paramReportResult, 1954, null, 8, null);
    a(paramReportResult, 1941, null, 8, null);
    a(paramReportResult, 1942, null, 15, null);
    a(paramReportResult, 1943, null, 15, null);
    a(paramReportResult, 1944, null, 10, "RIGHT");
    a(paramReportResult, 1947, null, 28, null);
  }
  
  private static String a(String paramString1, Locale paramLocale, String paramString2)
  {
    String str = null;
    if (jdField_int(paramString1)) {
      return "-";
    }
    try
    {
      str = ResourceUtil.getString(paramString1, paramString2, paramLocale);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "-";
    }
    return str;
  }
  
  private static void a(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Properties paramProperties, ReportSortCriteria paramReportSortCriteria, String paramString, Locale paramLocale, HashMap paramHashMap)
    throws BCReportException, Exception
  {
    String str1 = "obtainRecordsFromCriteria";
    int i1 = 0;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    Object localObject6 = null;
    String str2 = null;
    int i2 = -1;
    String str3 = null;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    int i3 = 0;
    boolean bool4 = false;
    Properties localProperties = paramReportCriteria.getReportOptions();
    str2 = localProperties.getProperty("MAXDISPLAYSIZE");
    if ((str2 != null) && (!str2.equals(""))) {
      i2 = Integer.parseInt(str2.trim());
    }
    String str4 = localProperties.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str4 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str4, paramLocale);
    }
    String str7 = "";
    String str8 = "";
    String str5;
    String str6;
    if ((paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report")))
    {
      str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, intratx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, biz.business_name as busname, intratx.ConfirmNum, biz.business_name as busname2, accts.nickname, accts.currency_type, accts.routing_num, biz.business_CIF as bizCif, intratx.DateCreate, intratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, business biz, affiliate_bank, BPW_XferInstruction intratx, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT  left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID  left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
      arrayOfString1 = al;
      str6 = "where AUDIT_LOG.SRVR_TID = intratx.SrvrTID and AUDIT_LOG.BUSINESS_ID = biz.business_id and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and accts.directory_id = AUDIT_LOG.BUSINESS_ID and biz.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
    }
    else if ((paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Exception Items Report")))
    {
      str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, extratx.AmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE  as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, biz.business_name as busname, extratx.LogId, biz.business_name as busname2, accts.nickname, AUDIT_LOG.CURRENCY_CODE, accts.routing_num, biz.business_CIF as bizCif, extratx.DateCreate, extratx.DateToPost, pri.first_name, pri.last_name ,AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, business biz, affiliate_bank, BPW_Transfer extratx, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
      str6 = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and AUDIT_LOG.BUSINESS_ID = biz.business_id and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and accts.directory_id = AUDIT_LOG.BUSINESS_ID and biz.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
    }
    else if (paramString.equals("External Transfer Deposit Verification Report"))
    {
      str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, ETF_ExternalAcct.CustomerId, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID as acctid, extratx.ToAmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, '' as acctNickName, AUDIT_LOG.CURRENCY_CODE, ETF_ExternalAcct.AcctBankRtn, '' as bizCif, extratx.DateCreate, extratx.DateToPost, affiliate_bank.affiliate_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from ETF_ExternalAcct, BPW_Transfer extratx, AUDIT_LOG, BPW_Customer, BPW_FIInfo, affiliate_bank ";
      str6 = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and extratx.ExternalAcctId = ETF_ExternalAcct.AcctId and BPW_Customer.VirtualCustomer='Y' and BPW_Customer.CustomerID=extratx.CustomerId and BPW_FIInfo.FIId = BPW_Customer.FIID and BPW_FIInfo.FIId = affiliate_bank.bpw_fi_id and extratx.FIId = BPW_FIInfo.FIId ";
    }
    else if ((paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report")))
    {
      str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, pmttx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, payee.PayeeName, pmttx.LogID, biz.business_name as busname, accts.nickname, accts.currency_type, accts.routing_num, biz.business_CIF as bizCif, pri.first_name, pri.last_name from accounts accts, business biz, affiliate_bank, BPW_PmtInstruction pmttx left outer join BPW_PmtHist hist on hist.SrvrTID = pmttx.SrvrTID, AUDIT_LOG left outer join customer cust on AUDIT_LOG.USER_ID_INT = cust.directory_id left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID, BPW_Payee payee ";
      arrayOfString1 = as;
      str6 = "where AUDIT_LOG.SRVR_TID = pmttx.SrvrTID and AUDIT_LOG.BUSINESS_ID = biz.business_id and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and payee.PayeeID = pmttx.PayeeID and accts.directory_id = AUDIT_LOG.BUSINESS_ID and biz.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
    }
    else
    {
      throw new BCReportException(str1, 11, "Invalid report type specified: " + paramString);
    }
    str3 = paramProperties.getProperty("ReportBy");
    if (str3 == null) {
      str3 = "ReportByStatus";
    }
    localObject3 = paramProperties.getProperty("Status");
    if (str3.equals("ReportByAction")) {
      paramReportCriteria.setDisplayValue("ReportBy", ReportConsts.getText(1058, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("ReportBy", ReportConsts.getText(1059, paramLocale));
    }
    if ((paramString.equals("Transfer Exception Items Report")) || (paramString.equals("Bill Payment Exception Items Report")) || (paramString.equals("External Transfer Exception Items Report")))
    {
      localObject3 = "FAILEDON";
      localObject7 = null;
      if (paramString.equals("Bill Payment Exception Items Report")) {
        localObject7 = "com.ffusion.beans.reporting.billpay_status";
      } else {
        localObject7 = "com.ffusion.beans.reporting.transfer_status";
      }
      paramReportCriteria.setDisplayValue("Status", a((String)localObject3, paramReportCriteria.getLocale(), (String)localObject7));
    }
    Object localObject7 = aQ;
    Enumeration localEnumeration = paramProperties.keys();
    for (int i4 = 1; localEnumeration.hasMoreElements(); i4 = 1)
    {
      localObject8 = (String)localEnumeration.nextElement();
      for (int i5 = 0; i5 < localObject7.length; i5++) {
        if (((String)localObject8).equals(localObject7[i5]))
        {
          i4 = 0;
          break;
        }
      }
      if (i4 == 0) {}
    }
    Object localObject8 = paramProperties.keySet();
    Iterator localIterator = ((Set)localObject8).iterator();
    String str9 = null;
    String str10 = null;
    int i6 = 0;
    try
    {
      i6 = Integer.parseInt(Profile.getSearchCriteria(paramProperties, "Affiliate Bank", "0"));
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      i6 = 0;
    }
    jdField_do(paramReportCriteria, paramProperties);
    String str14;
    while (localIterator.hasNext())
    {
      str9 = (String)localIterator.next();
      str10 = paramProperties.getProperty(str9);
      if (str9.equals("Business"))
      {
        if ((!str10.equals("AllBusinesses")) && (!str10.equals("AllConsumers")) && (!str10.equals("AllBusinessesAndConsumers")))
        {
          try
          {
            i1 = Integer.parseInt(str10);
            Business localBusiness = BusinessAdapter.getBusiness(i1);
            if (localBusiness != null)
            {
              localObject10 = new StringBuffer(localBusiness.getBusinessName());
              String str12 = localBusiness.getCustId();
              if (str12 != null)
              {
                str12 = str12.trim();
                if (str12.length() > 0) {
                  ((StringBuffer)localObject10).append(" (").append(str12).append(")");
                }
              }
              paramReportCriteria.setDisplayValue("Business", ((StringBuffer)localObject10).toString());
            }
          }
          catch (Exception localException1)
          {
            throw new BCReportException(str1, 10, "Business criterion cannot be represented as an integer value");
          }
          if (!bool2) {
            bool2 = true;
          } else {
            throw new AdminException(str1, 20, "Cannot specify more than 1 business criteria");
          }
        }
        else if (str10.equals("AllBusinesses"))
        {
          paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramLocale));
        }
        else if (str10.equals("AllConsumers"))
        {
          bool3 = true;
          if ((paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report")))
          {
            str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, intratx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, '' as busname, intratx.ConfirmNum, '' as busname2, accts.nickname, accts.currency_type, accts.routing_num, '' as bizCif, intratx.DateCreate, intratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, BPW_XferInstruction intratx, affiliate_bank, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT  left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID  left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
            str6 = "where AUDIT_LOG.SRVR_TID = intratx.SrvrTID and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
          }
          else if ((paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Exception Items Report")))
          {
            str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, extratx.AmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE  as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, accts.nickname, AUDIT_LOG.CURRENCY_CODE, accts.routing_num, '' as bizCif, extratx.DateCreate, extratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, BPW_Transfer extratx, affiliate_bank, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
            str6 = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
          }
          else if (paramString.equals("External Transfer Deposit Verification Report"))
          {
            str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, ETF_ExternalAcct.CustomerId, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID as acctid, extratx.ToAmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, '' as acctNickName, AUDIT_LOG.CURRENCY_CODE, ETF_ExternalAcct.AcctBankRtn, '' as bizCif, extratx.DateCreate, extratx.DateToPost, affiliate_bank.affiliate_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from ETF_ExternalAcct, BPW_Transfer extratx, AUDIT_LOG, BPW_Customer, BPW_FIInfo, affiliate_bank ";
            str6 = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and extratx.ExternalAcctId = ETF_ExternalAcct.AcctId and BPW_Customer.VirtualCustomer='Y' and BPW_Customer.CustomerID=extratx.CustomerId and BPW_FIInfo.FIId = BPW_Customer.FIID and BPW_FIInfo.FIId = affiliate_bank.bpw_fi_id and extratx.FIId = BPW_FIInfo.FIId ";
          }
          else if ((paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report")))
          {
            str5 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, pmttx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, payee.PayeeName, pmttx.LogID, '' as busname, accts.nickname, accts.currency_type, accts.routing_num, '' as bizCif, pri.first_name, pri.last_name from accounts accts, affiliate_bank, BPW_PmtInstruction pmttx left outer join BPW_PmtHist hist on hist.SrvrTID = pmttx.SrvrTID, AUDIT_LOG left outer join customer cust on AUDIT_LOG.USER_ID_INT = cust.directory_id left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID, BPW_Payee payee ";
            str6 = "where AUDIT_LOG.SRVR_TID = pmttx.SrvrTID and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and payee.PayeeID = pmttx.PayeeID and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
          }
          paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10016, paramLocale));
        }
        else if (str10.equals("AllBusinessesAndConsumers"))
        {
          i3 = 1;
          if ((paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report")))
          {
            str7 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, intratx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, '' as busname, intratx.ConfirmNum, '' as busname2, accts.nickname, accts.currency_type, accts.routing_num, '' as bizCif, intratx.DateCreate, intratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, BPW_XferInstruction intratx, affiliate_bank, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT  left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID  left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
            str8 = "where AUDIT_LOG.SRVR_TID = intratx.SrvrTID and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
          }
          else if ((paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Exception Items Report")))
          {
            str7 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID as acctid, AUDIT_LOG.TO_ACCT_ID, extratx.AmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE  as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, accts.nickname, AUDIT_LOG.CURRENCY_CODE, accts.routing_num, '' as bizCif, extratx.DateCreate, extratx.DateToPost, pri.first_name, pri.last_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.TO_AMOUNT_CURRENCY as auditToAmtCur, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from accounts accts, BPW_Transfer extratx, affiliate_bank, AUDIT_LOG left outer join customer cust on cust.directory_id = AUDIT_LOG.USER_ID_INT left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID ";
            str8 = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
          }
          else if (paramString.equals("External Transfer Deposit Verification Report"))
          {
            str7 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, ETF_ExternalAcct.CustomerId, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID as acctid, extratx.ToAmountCurrency, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, extratx.SourceRecSrvrTId, AUDIT_LOG.TRAN_TYPE, '' as busname, extratx.LogId, '' as busname2, '' as acctNickName, AUDIT_LOG.CURRENCY_CODE, ETF_ExternalAcct.AcctBankRtn, '' as bizCif, extratx.DateCreate, extratx.DateToPost, affiliate_bank.affiliate_name, AUDIT_LOG.TO_AMOUNT as auditToAmt, AUDIT_LOG.USER_ASSIGNED_AMOUNT as auditAssignedAmt from ETF_ExternalAcct, BPW_Transfer extratx, AUDIT_LOG, BPW_Customer, BPW_FIInfo, affiliate_bank ";
            str8 = "where AUDIT_LOG.SRVR_TID = extratx.SrvrTId and extratx.ExternalAcctId = ETF_ExternalAcct.AcctId and BPW_Customer.VirtualCustomer='Y' and BPW_Customer.CustomerID=extratx.CustomerId and BPW_FIInfo.FIId = BPW_Customer.FIID and BPW_FIInfo.FIId = affiliate_bank.bpw_fi_id and extratx.FIId = BPW_FIInfo.FIId ";
          }
          else if ((paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report")))
          {
            str7 = "select DISTINCT AUDIT_LOG.BUSINESS_ID as busid, AUDIT_LOG.LOG_DATE as auditLogDt, cust.user_name as user_name, AUDIT_LOG.FROM_ACCT_ID acctid, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.CURRENCY_CODE, AUDIT_LOG.AMOUNT, AUDIT_LOG.STATE as auditState, AUDIT_LOG.TRAN_ID, AUDIT_LOG.SRVR_TID, pmttx.RecSrvrTID, AUDIT_LOG.TRAN_TYPE, payee.PayeeName, pmttx.LogID, '' as busname, accts.nickname, accts.currency_type, accts.routing_num, '' as bizCif, pri.first_name, pri.last_name from accounts accts, affiliate_bank, BPW_PmtInstruction pmttx left outer join BPW_PmtHist hist on hist.SrvrTID = pmttx.SrvrTID, AUDIT_LOG left outer join customer cust on AUDIT_LOG.USER_ID_INT = cust.directory_id left outer join CUSTOMER_REL cr on cust.directory_id = cr.DIRECTORY_ID left outer join customer pri on pri.directory_id = cr.PRIMARY_USER_ID, BPW_Payee payee ";
            str8 = "where AUDIT_LOG.SRVR_TID = pmttx.SrvrTID and AUDIT_LOG.FROM_ACCT_ID = accts.account_id and payee.PayeeID = pmttx.PayeeID and (accts.directory_id = pri.directory_id or accts.directory_id = cust.directory_id) and cust.affiliate_bank_id = affiliate_bank.affiliate_bank_id ";
          }
          paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10014, paramLocale));
        }
        else
        {
          paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramLocale));
        }
      }
      else
      {
        Object localObject9;
        if (str9.equals("Account"))
        {
          if (!jdField_else(str10))
          {
            if ((str10 == null) || (str10.length() <= 0)) {
              throw new BCReportException(str1, 10, "Search criteria for accounts not complete: No account ID defined.");
            }
            localObject1 = str10;
            localObject9 = new StringBuffer();
            localObject10 = new StringTokenizer(localObject1, ",");
            i7 = ((StringTokenizer)localObject10).countTokens();
            for (int i8 = 1; ((StringTokenizer)localObject10).hasMoreTokens(); i8++)
            {
              localObject11 = new StringTokenizer(((StringTokenizer)localObject10).nextToken(), ":");
              int i9 = ((StringTokenizer)localObject11).countTokens();
              str14 = ((StringTokenizer)localObject11).nextToken();
              localObject12 = ((StringTokenizer)localObject11).nextToken();
              localObject13 = null;
              localObject14 = null;
              if (i9 > 2)
              {
                localObject13 = ((StringTokenizer)localObject11).nextToken();
                localObject14 = ((StringTokenizer)localObject11).nextToken();
                try
                {
                  ((StringBuffer)localObject9).append((String)localObject12 + " : " + AccountUtil.buildAccountDisplayText(str14, paramReportCriteria.getLocale()) + " - " + (String)localObject13 + " - " + (String)localObject14);
                }
                catch (UtilException localUtilException1)
                {
                  DebugLog.throwing("Error while constructing account display string for account id '" + str14 + "' and report type '" + paramString + "'.", localUtilException1);
                  ((StringBuffer)localObject9).append((String)localObject12 + " : " + str14 + " - " + (String)localObject13 + " - " + (String)localObject14);
                }
              }
              if (i8 != i7) {
                ((StringBuffer)localObject9).append("&&");
              }
            }
            paramReportCriteria.setDisplayValue("Account", ((StringBuffer)localObject9).toString());
          }
          else
          {
            paramReportCriteria.setDisplayValue("Account", ReportConsts.getText(10024, paramLocale));
          }
        }
        else if (str9.equals("User"))
        {
          if (!str10.equals("AllUsers"))
          {
            if ((str10 == null) || (str10.length() <= 0)) {
              throw new BCReportException(str1, 10, "Search criteria for users not complete: No user defined.");
            }
            localObject2 = str10;
            try
            {
              localObject9 = CustomerAdapter.getUserById(Integer.parseInt(localObject2));
              if (localObject9 != null) {
                paramReportCriteria.setDisplayValue("User", UserUtil.getFullNameWithLoginId(((User)localObject9).getFirstName(), ((User)localObject9).getLastName(), ((User)localObject9).getUserName(), ((User)localObject9).getCustId(), paramLocale));
              }
            }
            catch (Exception localException2) {}
          }
          else
          {
            paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, paramLocale));
          }
        }
        else if ((str9.equals("Status")) && ((paramString.equals("Transfer Report")) || (paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Deposit Verification Report")) || (paramString.equals("Bill Payment Report"))))
        {
          if ((!str10.equals("AllStatuses")) && (!str10.equals("AllStatus")))
          {
            if ((str10 == null) || (str10.length() <= 0)) {
              throw new BCReportException(str1, 10, "Search criteria for status not complete: No status defined.");
            }
            localObject3 = str10;
            String str11 = null;
            if (paramString.equals("Bill Payment Report")) {
              str11 = "com.ffusion.beans.reporting.billpay_status";
            } else {
              str11 = "com.ffusion.beans.reporting.transfer_status";
            }
            paramReportCriteria.setDisplayValue("Status", a((String)localObject3, paramReportCriteria.getLocale(), str11));
          }
          else
          {
            paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10018, paramLocale));
          }
        }
        else if (str9.equals("TransactionType"))
        {
          if (!str10.equals("AllTransactions"))
          {
            if ((str10 == null) || (str10.length() <= 0)) {
              throw new BCReportException(str1, 10, "Search criteria for status not complete: No transaction type defined.");
            }
            arrayOfString1 = new String[1];
            arrayOfString1[0] = str10;
            arrayOfString2 = new String[1];
            arrayOfString2[0] = str10;
          }
        }
        else if (str9.equals("StartDate")) {
          localObject4 = str10;
        } else if (str9.equals("EndDate")) {
          localObject5 = str10;
        } else if (str9.equals("TrackingId")) {
          localObject6 = str10;
        } else if (str9.equals("FromAmount")) {
          try
          {
            if ((str10 != null) && (str10.length() != 0)) {
              localBigDecimal1 = new BigDecimal(str10);
            }
          }
          catch (Exception localException3)
          {
            throw new BCReportException(str1, 10, "Search criteria error, fromAmount is not an amount: " + str10);
          }
        } else if (str9.equals("ToAmount")) {
          try
          {
            if ((str10 != null) && (str10.length() != 0)) {
              localBigDecimal2 = new BigDecimal(str10);
            }
          }
          catch (Exception localException4)
          {
            throw new BCReportException(str1, 10, "Search criteria error, toAmount is not an amount: " + str10);
          }
        } else if (str9.equals("IncludeSecondaryUsers")) {
          if (str10.equals("on")) {
            paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10081, paramLocale));
          } else if (str10.equals("off")) {
            paramReportCriteria.setDisplayValue("IncludeSecondaryUsers", ReportConsts.getText(10082, paramLocale));
          }
        }
      }
    }
    if ((localObject4 != null) && (localObject4.length() > 0)) {
      try
      {
        localTimestamp1 = jdField_byte(localObject4);
      }
      catch (ParseException localParseException1)
      {
        throw new BCReportException(str1, 13, "Invalid format for startDate: " + localObject4);
      }
    }
    if ((localObject5 != null) && (localObject5.length() > 0)) {
      try
      {
        localTimestamp2 = jdField_byte(localObject5);
      }
      catch (ParseException localParseException2)
      {
        throw new BCReportException(str1, 13, "Invalid format for endDate: " + localObject5);
      }
    }
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    paramReportResult.init(paramReportCriteria);
    paramReportResult.setDataDimensions(null);
    if ((paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report"))) {
      localReportHeading.setLabel(ReportConsts.getText(paramString.equals("Transfer Report") ? 1056 : 1061, paramReportCriteria.getLocale()));
    } else if ((paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report"))) {
      localReportHeading.setLabel(ReportConsts.getText(paramString.equals("Bill Payment Report") ? 1006 : 1010, paramReportCriteria.getLocale()));
    } else if ((paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Exception Items Report"))) {
      localReportHeading.setLabel(ReportConsts.getText(paramString.equals("External Transfer Report") ? 1057 : 1062, paramReportCriteria.getLocale()));
    } else if (paramString.equals("External Transfer Deposit Verification Report")) {
      localReportHeading.setLabel(ReportConsts.getText(1066, paramReportCriteria.getLocale()));
    }
    paramReportResult.setHeading(localReportHeading);
    Object localObject10 = paramProperties.getProperty("IncludeSecondaryUsers");
    int i7 = (localObject10 != null) && (((String)localObject10).equals("on")) ? 1 : 0;
    StringBuffer localStringBuffer = a(str6, i6, bool2, i1, bool3, bool4, localObject1, localObject2, paramString, str3, (String)localObject3, bool1, arrayOfString2, arrayOfString1, localBigDecimal1, localBigDecimal2, localTimestamp1, localTimestamp2, localObject6, i7);
    Object localObject11 = new StringBuffer();
    if (i3 != 0)
    {
      bool4 = true;
      localObject11 = a(str8, i6, bool2, i1, bool3, bool4, localObject1, localObject2, paramString, str3, (String)localObject3, bool1, arrayOfString2, arrayOfString1, localBigDecimal1, localBigDecimal2, localTimestamp1, localTimestamp2, localObject6, i7);
    }
    if (paramString.equals("External Transfer Deposit Verification Report"))
    {
      str13 = "order by affiliate_bank.affiliate_name,AUDIT_LOG.FROM_ACCT_ID asc ";
      str14 = "order by affiliate_bank.affiliate_name,AUDIT_LOG.FROM_ACCT_ID asc ";
    }
    else if (bool2)
    {
      str13 = " order by acctid asc ";
      str14 = " order by acctid asc ";
    }
    else if (bool3)
    {
      str13 = " order by acctid asc ";
      str14 = " order by acctid asc ";
    }
    else
    {
      str13 = " order by busname asc, busid asc, acctid asc ";
      str14 = " order by busid asc, busname asc, acctid asc ";
    }
    String str13 = str13 + ", auditState asc ";
    if (paramString.equals("External Transfer Deposit Verification Report"))
    {
      str13 = str13 + ", acctid asc, AUDIT_LOG.TRAN_ID asc ";
      str14 = str14 + ", auditState asc " + ", acctid asc, AUDIT_LOG.TRAN_ID asc ";
    }
    if (paramReportSortCriteria != null)
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      localObject12 = paramReportSortCriteria.iterator();
      localObject13 = new StringBuffer();
      while (((Iterator)localObject12).hasNext())
      {
        localObject14 = (ReportSortCriterion)((Iterator)localObject12).next();
        str15 = ((ReportSortCriterion)localObject14).getName();
        if ((str15 != null) && (str15.length() != 0))
        {
          if (str15.equalsIgnoreCase("Date"))
          {
            str15 = ", auditLogDt";
          }
          else
          {
            if (!str15.equalsIgnoreCase("User")) {
              continue;
            }
            str15 = ", user_name";
          }
          String str16 = ((ReportSortCriterion)localObject14).getAsc() ? "ASC" : "DESC";
          ((StringBuffer)localObject13).append(str15);
          ((StringBuffer)localObject13).append(" ");
          ((StringBuffer)localObject13).append(str16);
        }
      }
      if (((StringBuffer)localObject13).length() > 0)
      {
        str13 = str13 + ((StringBuffer)localObject13).toString();
        str14 = str14 + ((StringBuffer)localObject13).toString();
      }
    }
    Object localObject12 = null;
    Object localObject13 = null;
    Object localObject14 = null;
    String str15 = null;
    str15 = "(" + str5 + localStringBuffer.toString() + ")";
    if (i3 != 0) {
      str15 = str15 + " union " + "(" + str7 + ((StringBuffer)localObject11).toString() + ")";
    }
    if ((paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report")) || (paramString.equals("External Transfer Deposit Verification Report")) || (paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Exception Items Report"))) {
      str15 = str15 + str14;
    } else {
      str15 = str15 + str13;
    }
    try
    {
      localObject12 = DBUtil.getConnection(bd, true, 2);
      localObject13 = DBUtil.prepareStatement((Connection)localObject12, str15);
      if (i2 != -1) {
        ((PreparedStatement)localObject13).setMaxRows(i2 + 1);
      }
      int i10 = 1;
      if (localTimestamp1 != null) {
        ((PreparedStatement)localObject13).setTimestamp(i10++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        ((PreparedStatement)localObject13).setTimestamp(i10++, localTimestamp2);
      }
      if (i3 != 0)
      {
        if (localTimestamp1 != null) {
          ((PreparedStatement)localObject13).setTimestamp(i10++, localTimestamp1);
        }
        if (localTimestamp2 != null) {
          ((PreparedStatement)localObject13).setTimestamp(i10++, localTimestamp2);
        }
      }
      localObject14 = DBUtil.executeQuery((PreparedStatement)localObject13, str15);
      SummaryInfo localSummaryInfo = new SummaryInfo();
      localSummaryInfo.setLocale(paramLocale);
      localSummaryInfo.setUser(paramSecureUser);
      int i11 = 0;
      String str17 = null;
      String str18 = null;
      Account localAccount = null;
      ReportResult localReportResult1 = null;
      ReportResult localReportResult2 = null;
      ReportResult localReportResult3 = null;
      ReportResult localReportResult4 = null;
      String str19 = null;
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      String str20 = null;
      String str21 = null;
      String str22 = null;
      String str23 = null;
      String str24 = null;
      String str25 = null;
      String str26 = null;
      String str27 = null;
      String str28 = null;
      String str29 = null;
      String str30 = null;
      if ((paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report")))
      {
        str24 = ReportConsts.getText(1000, paramLocale);
        str25 = ReportConsts.getText(1001, paramLocale);
        str26 = ReportConsts.getText(1013, paramLocale);
        str27 = ReportConsts.getText(1002, paramLocale);
        str28 = ReportConsts.getText(1003, paramLocale);
        str23 = ReportConsts.getText(1004, paramLocale);
        str29 = ReportConsts.getText(1014, paramLocale);
        str30 = "com.ffusion.beans.reporting.billpay_status";
      }
      else
      {
        str24 = ReportConsts.getText(1050, paramLocale);
        str25 = ReportConsts.getText(1051, paramLocale);
        str26 = ReportConsts.getText(1064, paramLocale);
        str27 = ReportConsts.getText(1052, paramLocale);
        str28 = ReportConsts.getText(1053, paramLocale);
        str23 = ReportConsts.getText(1054, paramLocale);
        str29 = ReportConsts.getText(1065, paramLocale);
        str30 = "com.ffusion.beans.reporting.transfer_status";
      }
      if (paramString.equals("External Transfer Deposit Verification Report")) {
        str25 = ReportConsts.getText(1067, paramLocale);
      }
      localSummaryInfo.sameBusiness = false;
      localSummaryInfo.sameAccount = false;
      int i12 = 1;
      boolean bool5 = false;
      boolean bool6 = a(paramString);
      boolean bool7 = paramString.equals("External Transfer Deposit Verification Report");
      int i13 = (paramString.equals("Bill Payment Report")) || (paramString.equals("Bill Payment Exception Items Report")) ? 1 : 0;
      int i14 = 0;
      while (((ResultSet)localObject14).next())
      {
        if ((bool6) && (i14 == 0))
        {
          localObject15 = "" + ((ResultSet)localObject14).getString("auditAssignedAmt");
          if ((((String)localObject15).equals("1")) || (((String)localObject15).equals("2"))) {
            bool5 = true;
          }
          i14 = 1;
        }
        if ((i2 != -1) && (i2 <= localSummaryInfo.numRows))
        {
          localObject15 = new HashMap();
          ((HashMap)localObject15).put("TRUNCATED", new Integer(i2));
          paramReportResult.setProperties((HashMap)localObject15);
          break;
        }
        str17 = ((ResultSet)localObject14).getString(1);
        if (((bool3) || (i3 != 0)) && (str17 == null)) {
          str17 = "-1";
        }
        str18 = ((ResultSet)localObject14).getString(4);
        if (bool7) {
          str17 = ((ResultSet)localObject14).getString(22);
        }
        str19 = ((ResultSet)localObject14).getString(8);
        str22 = ((ResultSet)localObject14).getString(6);
        localObject15 = ((ResultSet)localObject14).getString(17);
        if ((localObject15 != null) && (((String)localObject15).trim().length() > 0)) {
          localSummaryInfo.setCurrentCurrencyCode((String)localObject15);
        }
        String str31;
        if (!str17.equals(localSummaryInfo.prevBusiness))
        {
          if (localSummaryInfo.prevBusiness != null)
          {
            if (localSummaryInfo.prevBusiness.equals("-1")) {
              localArrayList1.add(null);
            } else {
              localArrayList1.add(localSummaryInfo.prevBusiness);
            }
            if (localSummaryInfo.prevFromAcct != null)
            {
              localSummaryInfo.addAccountTotal(localSummaryInfo.getStatusTotalSummary());
              if (localSummaryInfo.prevStatus != null)
              {
                str31 = a(paramString, localSummaryInfo.prevStatus, paramLocale);
                a(localReportResult4, localSummaryInfo, localSummaryInfo.getStatusTotalSummary(), str31, str27, str28, str29, bool5);
              }
              a(localReportResult2, localSummaryInfo, localSummaryInfo.getAccountTotalSummary(), str24, str27, str28, str29, bool5);
              localArrayList2.add(localSummaryInfo.prevFromAcct);
            }
            localSummaryInfo.addBusinessTotal(localSummaryInfo.getAccountTotalSummary());
            localSummaryInfo.addGrandTotal(localSummaryInfo.getAccountTotalSummary());
            a(localReportResult1, localSummaryInfo, localSummaryInfo.getBusinessTotalSummary(), !"-1".equals(localSummaryInfo.prevBusiness) ? str25 : str26, str27, str28, str29, bool5);
          }
          localSummaryInfo.resetBusinessTotal();
          localSummaryInfo.prevBusiness = str17;
          localSummaryInfo.sameBusiness = false;
          str31 = null;
          if (localSummaryInfo.prevBusiness.equals("-1")) {
            str31 = "";
          } else if ((paramString.equals("Transfer Report")) || (paramString.equals("Bill Payment Report")) || (paramString.equals("External Transfer Report"))) {
            str31 = ((ResultSet)localObject14).getString(15) + " (" + ((ResultSet)localObject14).getString(19) + ")";
          } else if (bool7) {
            str31 = ((ResultSet)localObject14).getString(22);
          } else {
            str31 = ((ResultSet)localObject14).getString(15);
          }
          localReportResult1 = a(paramReportResult, paramReportCriteria, str31);
          localSummaryInfo.resetStatusTotal();
          localSummaryInfo.prevStatus = null;
          localSummaryInfo.resetAccountTotal();
          localSummaryInfo.prevFromAcct = null;
          localArrayList2 = new ArrayList();
          i12 = 1;
        }
        else
        {
          localSummaryInfo.sameBusiness = true;
        }
        if (!str18.equals(localSummaryInfo.prevFromAcct))
        {
          if ((str17.equals("-1")) || (paramString.equals("External Transfer Deposit Verification Report")))
          {
            localAccount = new Account();
            localAccount.setID(str18.substring(0, str18.lastIndexOf("-")), str18.substring(str18.lastIndexOf("-") + 1));
          }
          else
          {
            localAccount = jdField_if(str18, str17);
          }
          if (localSummaryInfo.prevFromAcct != null)
          {
            localArrayList2.add(localSummaryInfo.prevFromAcct);
            localSummaryInfo.addAccountTotal(localSummaryInfo.getStatusTotalSummary());
            if (localSummaryInfo.prevStatus != null)
            {
              str31 = a(paramString, localSummaryInfo.prevStatus, paramLocale);
              a(localReportResult4, localSummaryInfo, localSummaryInfo.getStatusTotalSummary(), str31, str27, str28, str29, bool5);
            }
            a(localReportResult2, localSummaryInfo, localSummaryInfo.getAccountTotalSummary(), str24, str27, str28, str29, bool5);
          }
          if (localSummaryInfo.sameBusiness == true)
          {
            localSummaryInfo.addBusinessTotal(localSummaryInfo.getAccountTotalSummary());
            localSummaryInfo.addGrandTotal(localSummaryInfo.getAccountTotalSummary());
          }
          localSummaryInfo.resetAccountTotal();
          localSummaryInfo.prevFromAcct = str18;
          localSummaryInfo.prevStatus = null;
          localSummaryInfo.sameAccount = false;
          str31 = localAccount.getDisplayText();
          localObject16 = ((ResultSet)localObject14).getString(16);
          if ((localObject16 != null) && (((String)localObject16).trim().length() > 0)) {
            str31 = str31 + " - " + (String)localObject16;
          }
          if ((localObject15 != null) && (((String)localObject15).trim().length() > 0)) {
            str31 = str31 + " - " + (String)localObject15;
          }
          localReportResult2 = jdField_do(localReportResult1, paramReportCriteria, str31);
          if (i13 == 0)
          {
            localReportResult3 = new ReportResult(paramLocale);
            localReportResult2.addSubReport(localReportResult3);
          }
          i12 = 1;
        }
        else
        {
          localSummaryInfo.sameAccount = true;
        }
        boolean bool8 = (bool6) || (i13 != 0);
        bool8 = (bool8) && ("-1".equals(str17));
        if (!str19.equals(localSummaryInfo.prevStatus))
        {
          if (localSummaryInfo.prevStatus != null)
          {
            localObject16 = a(paramString, localSummaryInfo.prevStatus, paramLocale);
            a(localReportResult4, localSummaryInfo, localSummaryInfo.getStatusTotalSummary(), (String)localObject16, str27, str28, str29, bool5);
          }
          if (localSummaryInfo.sameAccount == true) {
            localSummaryInfo.addAccountTotal(localSummaryInfo.getStatusTotalSummary());
          }
          localSummaryInfo.resetStatusTotal();
          localSummaryInfo.prevStatus = str19;
          localObject16 = null;
          if ((paramString.equals("Transfer Report")) || (paramString.equals("External Transfer Deposit Verification Report")) || (paramString.equals("External Transfer Report"))) {
            localObject16 = ReportConsts.getText(1063, paramReportCriteria.getLocale()) + " " + a(str19, paramReportCriteria.getLocale());
          } else if (paramString.equals("Bill Payment Report")) {
            localObject16 = ReportConsts.getText(1011, paramReportCriteria.getLocale()) + " " + jdField_do(str19, paramReportCriteria.getLocale());
          } else {
            localObject16 = ReportConsts.getText(1012, paramReportCriteria.getLocale()) + " " + str19;
          }
          localReportResult4 = jdField_if(localReportResult2, paramReportCriteria, (String)localObject16);
          localReportResult3 = new ReportResult(paramLocale);
          localReportResult4.addSubReport(localReportResult3);
          int i15 = 0;
          int i16 = 0;
          if (bool8) {
            i15 = 1;
          }
          if (bool5) {
            i16 = 1;
          }
          if (bool6)
          {
            localReportResult3 = a(localReportResult4, paramReportCriteria, ReportConsts.getText(1055, paramLocale), 9 + i15 + i16, paramLocale);
            a(localReportResult3, bool8, bool5);
          }
          else if (bool7)
          {
            localReportResult3 = a(localReportResult4, paramReportCriteria, ReportConsts.getText(1055, paramLocale), 8, paramLocale);
            jdField_do(localReportResult3);
          }
          else if (i13 != 0)
          {
            localReportResult3 = a(localReportResult4, paramReportCriteria, ReportConsts.getText(1005, paramLocale), 7 + i15, paramLocale);
            a(localReportResult3, bool8);
          }
          i12 = 1;
        }
        Object localObject16 = new ReportDataItems(paramLocale);
        str20 = ((ResultSet)localObject14).getString(2);
        Timestamp localTimestamp3 = ((ResultSet)localObject14).getTimestamp(2);
        String str32 = localDateFormat.format(localTimestamp3);
        if (jdField_int(str20)) {
          ((ReportDataItems)localObject16).add().setData("");
        } else {
          ((ReportDataItems)localObject16).add().setData(str32);
        }
        int i17 = 0;
        Object localObject17;
        Object localObject18;
        if ((bool6) || (bool7))
        {
          str20 = ((ResultSet)localObject14).getString(20);
          if (jdField_int(str20))
          {
            ((ReportDataItems)localObject16).add().setData("");
          }
          else
          {
            str20 = str20.trim();
            if (str20.length() < 19) {
              ((ReportDataItems)localObject16).add().setData(str20);
            } else {
              try
              {
                str20 = str20.substring(0, 19);
                SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                ((ReportDataItems)localObject16).add().setData(localDateFormat.format(localSimpleDateFormat1.parse(str20)));
              }
              catch (Exception localException6)
              {
                ((ReportDataItems)localObject16).add().setData(str20);
              }
            }
          }
          str20 = ((ResultSet)localObject14).getString(21);
          if (jdField_int(str20))
          {
            ((ReportDataItems)localObject16).add().setData("");
          }
          else
          {
            str20 = str20.trim();
            if (str20.length() < 8) {
              ((ReportDataItems)localObject16).add().setData(str20);
            } else {
              try
              {
                str20 = str20.substring(0, 8);
                SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
                localObject17 = localSimpleDateFormat2.parse(str20);
                localObject18 = localDateFormat.format((Date)localObject17);
                ((ReportDataItems)localObject16).add().setData(localObject18);
              }
              catch (Exception localException7)
              {
                ((ReportDataItems)localObject16).add().setData(str20);
              }
            }
          }
        }
        else
        {
          i17 = -2;
        }
        String str33 = ((ResultSet)localObject14).getString(3);
        if (bool7)
        {
          if (str33 == null) {
            str33 = "";
          }
          try
          {
            localObject17 = new Integer(str33);
            localObject18 = CustomerAdapter.getUserById(((Integer)localObject17).intValue());
            if (localObject18 != null) {
              str33 = ((User)localObject18).getName();
            }
          }
          catch (NumberFormatException localNumberFormatException2) {}
        }
        a(((ReportDataItems)localObject16).add(), str33);
        if (bool8)
        {
          str34 = ((ResultSet)localObject14).getString(22 + i17);
          localObject18 = ((ResultSet)localObject14).getString(23 + i17);
          if ((str34 != null) && (localObject18 != null)) {
            a(((ReportDataItems)localObject16).add(), UserUtil.getFullName(str34, (String)localObject18, paramReportCriteria.getLocale()));
          } else {
            a(((ReportDataItems)localObject16).add(), "");
          }
        }
        if (i13 != 0)
        {
          a(((ReportDataItems)localObject16).add(), ((ResultSet)localObject14).getString(13));
        }
        else
        {
          str34 = ((ResultSet)localObject14).getString(4);
          try
          {
            str34 = AccountUtil.buildAccountDisplayText(str34, paramReportCriteria.getLocale());
          }
          catch (UtilException localUtilException2)
          {
            DebugLog.throwing("Error while constructing account display string for account id '" + str34 + "' and report type '" + paramString + "'.", localUtilException2);
          }
          a(((ReportDataItems)localObject16).add(), str34);
        }
        String str34 = ((ResultSet)localObject14).getString(5);
        if (i13 == 0) {
          try
          {
            str34 = AccountUtil.buildAccountDisplayText(str34, paramReportCriteria.getLocale());
          }
          catch (UtilException localUtilException3)
          {
            DebugLog.throwing("Error while constructing account display string for account id '" + str34 + "' and report type '" + paramString + "'.", localUtilException3);
          }
        }
        a(((ReportDataItems)localObject16).add(), str34);
        str20 = ((ResultSet)localObject14).getString(7);
        if ((bool7) && (((ResultSet)localObject14).getInt("auditAssignedAmt") == 2)) {
          str20 = ((ResultSet)localObject14).getString("auditToAmt");
        }
        String str35 = "";
        String str36 = "";
        if (bool5)
        {
          str35 = ((ResultSet)localObject14).getString("auditToAmt");
          str36 = ((ResultSet)localObject14).getString("auditToAmtCur");
        }
        if ((jdField_int(str20)) || ("0.000".equals(str20)))
        {
          if ((bool5) && (!jdField_int(str35)) && (!str35.equals("0"))) {
            str21 = a(paramSecureUser, str35, str36, str22);
          } else {
            str21 = "0";
          }
        }
        else {
          str21 = str20;
        }
        Currency localCurrency;
        if (str21.equals("0"))
        {
          ((ReportDataItems)localObject16).add().setData("");
        }
        else
        {
          if ((str22 != null) && (str22.trim().length() > 0)) {
            localCurrency = new Currency(str21, str22, paramLocale);
          } else {
            localCurrency = new Currency(str21, paramLocale);
          }
          ((ReportDataItems)localObject16).add().setData(localCurrency.getCurrencyString() + " " + str22);
        }
        if (bool5)
        {
          if (jdField_int(str35)) {
            if (!str21.equals("0")) {
              str35 = a(paramSecureUser, str21, str22, str36);
            } else {
              str35 = "0";
            }
          }
          if (str35.equals("0"))
          {
            ((ReportDataItems)localObject16).add().setData("");
          }
          else
          {
            if ((str36 != null) && (str36.trim().length() > 0)) {
              localCurrency = new Currency(str35, str36, paramLocale);
            } else {
              localCurrency = new Currency(str35, paramLocale);
            }
            ((ReportDataItems)localObject16).add().setData(localCurrency.getCurrencyString() + " " + str36);
          }
        }
        if (!bool7) {
          a(((ReportDataItems)localObject16).add(), ((ResultSet)localObject14).getString(11));
        }
        String str37 = ((ResultSet)localObject14).getString(9);
        String str38 = ((ResultSet)localObject14).getString(10);
        String str39 = ((ResultSet)localObject14).getString(14);
        if (str37 == null) {
          str37 = " ";
        }
        if (str38 == null) {
          str38 = " ";
        }
        if ((str39 == null) || (str39.equals(str37)) || (str39.equals(str38))) {
          str39 = "";
        }
        if (paramString.equals("External Transfer Deposit Verification Report")) {
          str38 = "";
        }
        str20 = str37 + (str38.length() > 0 ? "," + str38 : "") + (str39.length() > 0 ? "," + str39 : "");
        a(((ReportDataItems)localObject16).add(), str20);
        ReportRow localReportRow = new ReportRow(paramLocale);
        if (i12 % 2 == 0) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportRow.setDataItems((ReportDataItems)localObject16);
        localReportResult3.addRow(localReportRow);
        i12++;
        localSummaryInfo.numRows += 1L;
        SummaryItem localSummaryItem1 = new SummaryItem(new BigDecimal(str21), 1L);
        if ((str22 != null) && (str22.trim().length() > 0)) {
          localSummaryItem1.setCurrencyCode(str22);
        }
        localSummaryInfo.addStatusTotal(localSummaryItem1);
        if (bool5)
        {
          SummaryItem localSummaryItem2 = new SummaryItem();
          SummaryItem localSummaryItem3 = new SummaryItem(new BigDecimal(str35), 1L);
          if ((str36 != null) && (str36.trim().length() > 0))
          {
            localSummaryItem3.setCurrencyCode(str36);
            localSummaryItem2.setCurrencyCode(str36);
          }
          localSummaryItem2.setToAmountItem(localSummaryItem3);
          localSummaryInfo.addStatusTotal(localSummaryItem2);
        }
      }
      Object localObject15 = a(paramString, localSummaryInfo.prevStatus, paramLocale);
      a(localReportResult4, localSummaryInfo, localSummaryInfo.getStatusTotalSummary(), (String)localObject15, str27, str28, str29, bool5);
      localSummaryInfo.addAccountTotal(localSummaryInfo.getStatusTotalSummary());
      if (!paramString.equals("External Transfer Deposit Verification Report")) {
        a(localReportResult2, localSummaryInfo, localSummaryInfo.getAccountTotalSummary(), str24, str27, str28, str29, bool5);
      }
      localSummaryInfo.addBusinessTotal(localSummaryInfo.getAccountTotalSummary());
      localSummaryInfo.addGrandTotal(localSummaryInfo.getAccountTotalSummary());
      localArrayList2.add(str18);
      if ((str17 == null) || (str17.equals("-1"))) {
        localArrayList1.add(null);
      } else {
        localArrayList1.add(str17);
      }
      a(localReportResult1, localSummaryInfo, localSummaryInfo.getBusinessTotalSummary(), !"-1".equals(localSummaryInfo.prevBusiness) ? str25 : str26, str27, str28, str29, bool5);
      a(paramReportResult, localSummaryInfo, localSummaryInfo.getGrandTotalSummary(), str23, str27, str28, str29, bool5);
    }
    catch (Exception localException5)
    {
      localException5.printStackTrace();
      DebugLog.log("Error: " + localException5.getMessage());
    }
    finally
    {
      DBUtil.closeAll(bd, (Connection)localObject12, (PreparedStatement)localObject13, (ResultSet)localObject14);
    }
  }
  
  private static StringBuffer a(String paramString1, int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, boolean paramBoolean3, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean4, String[] paramArrayOfString1, String[] paramArrayOfString2, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Timestamp paramTimestamp1, Timestamp paramTimestamp2, String paramString7, boolean paramBoolean5)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    if (paramInt1 != 0)
    {
      localStringBuffer.append(" and affiliate_bank.affiliate_bank_id");
      localStringBuffer.append("=" + Integer.toString(paramInt1));
    }
    if (paramBoolean1) {
      localStringBuffer.append(" and AUDIT_LOG.BUSINESS_ID=" + Integer.toString(paramInt2));
    } else if ((paramBoolean2) || (paramBoolean3)) {
      localStringBuffer.append(" and AUDIT_LOG.BUSINESS_ID is null ");
    }
    if (paramString2 != null)
    {
      localObject = new StringTokenizer(paramString2, ",", false);
      String str1 = "AUDIT_LOG.FROM_ACCT_ID";
      String str2 = "AUDIT_LOG.FROM_ACCT_RTGNUM";
      if (paramString4.equals("External Transfer Deposit Verification Report"))
      {
        str1 = "AUDIT_LOG.TO_ACCT_ID";
        str2 = "AUDIT_LOG.TO_ACCT_RTGNUM";
      }
      if (((StringTokenizer)localObject).countTokens() == 1)
      {
        StringTokenizer localStringTokenizer1 = new StringTokenizer(((StringTokenizer)localObject).nextToken(), ":");
        localStringBuffer.append(" and " + str1 + "=" + "'" + DBUtil.escapeSQLStringLiteral(localStringTokenizer1.nextToken()) + "'");
        localStringBuffer.append(" and " + str2 + "=" + "'" + DBUtil.escapeSQLStringLiteral(localStringTokenizer1.nextToken()) + "'");
      }
      else
      {
        int i2 = 1;
        localStringBuffer.append(" and (");
        while (((StringTokenizer)localObject).hasMoreTokens())
        {
          StringTokenizer localStringTokenizer2 = new StringTokenizer(((StringTokenizer)localObject).nextToken(), ":");
          if (i2 != 0)
          {
            localStringBuffer.append("(" + str1 + "=" + "'" + DBUtil.escapeSQLStringLiteral(localStringTokenizer2.nextToken()) + "'");
            localStringBuffer.append(" and " + str2 + "=" + "'" + DBUtil.escapeSQLStringLiteral(localStringTokenizer2.nextToken()) + "'" + ")");
            i2 = 0;
          }
          else
          {
            localStringBuffer.append(" or (" + str1 + "=" + "'" + DBUtil.escapeSQLStringLiteral(localStringTokenizer2.nextToken()) + "'");
            localStringBuffer.append(" and " + str2 + "=" + "'" + DBUtil.escapeSQLStringLiteral(localStringTokenizer2.nextToken()) + "'" + ")");
          }
        }
        localStringBuffer.append(")");
      }
    }
    if (!paramString4.equals("External Transfer Deposit Verification Report")) {
      if (paramString3 != null)
      {
        if (paramBoolean5) {
          localStringBuffer.append(" and (AUDIT_LOG.PRIMARY_USER_ID=" + paramString3 + " or " + "AUDIT_LOG.USER_ID" + "=" + "'" + DBUtil.escapeSQLStringLiteral(paramString3) + "'" + ")");
        } else {
          localStringBuffer.append(" and AUDIT_LOG.USER_ID='" + DBUtil.escapeSQLStringLiteral(paramString3) + "'");
        }
      }
      else if (!paramBoolean5) {
        localStringBuffer.append(" and (AUDIT_LOG.PRIMARY_USER_ID=AUDIT_LOG.USER_ID_INT)");
      }
    }
    if (paramString4.equals("Transfer Report"))
    {
      if (paramString5.equals("ReportByStatus"))
      {
        if ((paramString6 == null) || (paramString6.equals("AllStatuses")))
        {
          localStringBuffer.append(" and ");
          localStringBuffer.append(" AUDIT_LOG.STATE=intratx.Status ");
        }
        else
        {
          localStringBuffer.append(" and ");
          localStringBuffer.append(" AUDIT_LOG.STATE=intratx.Status ");
          localStringBuffer.append(" and ");
          localStringBuffer.append(" intratx.Status ");
          localStringBuffer.append("=").append("'").append(DBUtil.escapeSQLStringLiteral(paramString6)).append("'");
          paramBoolean4 = true;
        }
      }
      else if ((paramString6 != null) && (!paramString6.equalsIgnoreCase("AllStatuses")))
      {
        localStringBuffer.append(" and ");
        localStringBuffer.append(" AUDIT_LOG.STATE ");
        localStringBuffer.append("=").append("'").append(DBUtil.escapeSQLStringLiteral(paramString6)).append("'");
        paramBoolean4 = true;
      }
    }
    else if (paramString4.equals("Bill Payment Report"))
    {
      if (paramString5.equals("ReportByStatus"))
      {
        if ((paramString6 == null) || (paramString6.equals("AllStatuses")))
        {
          localStringBuffer.append(" and ");
          localStringBuffer.append(" AUDIT_LOG.STATE=pmttx.Status ");
        }
        else
        {
          localStringBuffer.append(" and ");
          localStringBuffer.append(" AUDIT_LOG.STATE=pmttx.Status ");
          localStringBuffer.append(" and ");
          localStringBuffer.append(" pmttx.Status ");
          localStringBuffer.append("=").append("'").append(DBUtil.escapeSQLStringLiteral(paramString6)).append("'");
          paramBoolean4 = true;
        }
      }
      else if ((paramString6 != null) && (!paramString6.equalsIgnoreCase("AllStatuses")))
      {
        localStringBuffer.append(" and ");
        localStringBuffer.append(" AUDIT_LOG.STATE ");
        localStringBuffer.append("=").append("'").append(DBUtil.escapeSQLStringLiteral(paramString6)).append("'");
        paramBoolean4 = true;
      }
    }
    else if ((paramString4.equals("External Transfer Report")) || (paramString4.equals("External Transfer Deposit Verification Report")))
    {
      if (paramString5.equals("ReportByStatus"))
      {
        if ((paramString6 == null) || (paramString6.equals("AllStatuses")))
        {
          localStringBuffer.append(" and ");
          localStringBuffer.append(" AUDIT_LOG.STATE=extratx.Status and extratx.Status != 'TEMPLATE' and extratx.Status != 'RECTEMPLATE' ");
        }
        else
        {
          localStringBuffer.append(" and ");
          localStringBuffer.append(" AUDIT_LOG.STATE=extratx.Status and extratx.Status != 'TEMPLATE' and extratx.Status != 'RECTEMPLATE' ");
          localStringBuffer.append(" and ");
          localStringBuffer.append(" extratx.Status ");
          localStringBuffer.append("=").append("'").append(DBUtil.escapeSQLStringLiteral(paramString6)).append("'");
          paramBoolean4 = true;
        }
      }
      else if ((paramString6 != null) && (!paramString6.equalsIgnoreCase("AllStatuses")))
      {
        localStringBuffer.append(" and ");
        localStringBuffer.append(" AUDIT_LOG.STATE ");
        localStringBuffer.append("=").append("'").append(DBUtil.escapeSQLStringLiteral(paramString6)).append("'");
        paramBoolean4 = true;
      }
    }
    else if (paramString6 != null) {
      localStringBuffer.append(" and AUDIT_LOG.STATE='" + DBUtil.escapeSQLStringLiteral(paramString6) + "'");
    }
    Object localObject = null;
    if ((paramString4.equals("External Transfer Report")) || (paramString4.equals("External Transfer Exception Items Report"))) {
      localObject = paramArrayOfString1;
    } else {
      localObject = paramArrayOfString2;
    }
    if (localObject != null)
    {
      localStringBuffer.append(" and AUDIT_LOG.TRAN_TYPE in (");
      for (int i1 = 0; i1 < localObject.length; i1++) {
        localStringBuffer.append(localObject[i1] + ",");
      }
      localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      localStringBuffer.append(")");
    }
    if (paramBigDecimal1 != null) {
      if (a(paramString4)) {
        localStringBuffer.append(" and (AUDIT_LOG.AMOUNT >= " + paramBigDecimal1.toString() + " or " + "AUDIT_LOG.TO_AMOUNT" + " >= " + paramBigDecimal1.toString() + ")");
      } else {
        localStringBuffer.append(" and AUDIT_LOG.AMOUNT >= " + paramBigDecimal1.toString());
      }
    }
    if (paramBigDecimal2 != null) {
      if (a(paramString4)) {
        localStringBuffer.append(" and (AUDIT_LOG.AMOUNT <= " + paramBigDecimal2.toString() + " or " + "AUDIT_LOG.TO_AMOUNT" + " <= " + paramBigDecimal2.toString() + ")");
      } else {
        localStringBuffer.append(" and AUDIT_LOG.AMOUNT <= " + paramBigDecimal2.toString());
      }
    }
    if (paramTimestamp1 != null) {
      localStringBuffer.append(" and AUDIT_LOG.LOG_DATE >=  ? ");
    }
    if (paramTimestamp2 != null) {
      localStringBuffer.append(" and AUDIT_LOG.LOG_DATE <=  ? ");
    }
    if (paramString7 != null) {
      localStringBuffer.append(" and AUDIT_LOG.SRVR_TID='" + DBUtil.escapeSQLStringLiteral(paramString7) + "'");
    }
    return localStringBuffer;
  }
  
  private static ReportResult a(ReportResult paramReportResult, ReportCriteria paramReportCriteria, String paramString, int paramInt, Locale paramLocale)
    throws Exception
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(paramString);
    localReportHeading.setJustification("LEFT");
    a(localReportResult, paramLocale, -1, paramInt);
    localReportResult.setHeading(localReportHeading);
    return localReportResult;
  }
  
  private static void a(ReportResult paramReportResult, SummaryInfo paramSummaryInfo, SummaryItem[] paramArrayOfSummaryItem, String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    a(paramReportResult, paramSummaryInfo, paramArrayOfSummaryItem, paramString1, paramString2, paramString3, paramString4, false);
  }
  
  private static void a(ReportResult paramReportResult, SummaryInfo paramSummaryInfo, SummaryItem[] paramArrayOfSummaryItem, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws Exception
  {
    ReportColumn localReportColumn = null;
    if (paramReportResult == null) {
      return;
    }
    ReportResult localReportResult = new ReportResult(paramSummaryInfo.getLocale());
    ReportHeading localReportHeading = new ReportHeading(paramSummaryInfo.getLocale());
    paramReportResult.addSubReport(localReportResult);
    localReportHeading.setLabel(paramString1);
    localReportResult.setHeading(localReportHeading);
    int i1 = 4 + (paramBoolean ? 2 : 0);
    int[] arrayOfInt = new int[6];
    if (paramBoolean)
    {
      i1 = 6;
      arrayOfInt[3] = 25;
      arrayOfInt[0] = 25;
      arrayOfInt[4] = 13;
      arrayOfInt[1] = 13;
      arrayOfInt[5] = 12;
      arrayOfInt[2] = 12;
    }
    else
    {
      i1 = 4;
      for (i2 = 0; i2 < i1; i2++) {
        arrayOfInt[i2] = 25;
      }
    }
    a(localReportResult, paramSummaryInfo.getLocale(), -1, i1);
    for (int i2 = 0; i2 < i1; i2++)
    {
      localReportColumn = new ReportColumn(paramSummaryInfo.getLocale());
      localReportColumn.setWidthAsPercent(arrayOfInt[i2]);
      localReportResult.addColumn(localReportColumn);
    }
    long l1 = 0L;
    int i3 = 0;
    ReportDataItems localReportDataItems = null;
    ReportRow localReportRow = null;
    SummaryItem localSummaryItem;
    for (int i4 = 0; i4 < paramArrayOfSummaryItem.length; i4++)
    {
      localSummaryItem = paramArrayOfSummaryItem[i4];
      if ((paramSummaryInfo.getUser() != null) && (paramSummaryInfo.getUser().getBaseCurrency() != null) && (!paramSummaryInfo.getUser().getBaseCurrency().equals(localSummaryItem.getCurrencyCode()))) {
        i3 = 1;
      }
      l1 += localSummaryItem.totalNumDebits;
    }
    if ((paramString4 == null) || (i3 != 0)) {
      for (i4 = 0; i4 < paramArrayOfSummaryItem.length; i4++)
      {
        localSummaryItem = paramArrayOfSummaryItem[i4];
        localReportDataItems = new ReportDataItems(paramSummaryInfo.getLocale());
        String str2 = localSummaryItem.getCurrencyCode();
        if ((str2 != null) && (str2.trim().length() > 0)) {
          localReportDataItems.add().setData(paramString3 + " (" + str2 + ")");
        } else {
          localReportDataItems.add().setData(paramString3);
        }
        Currency localCurrency1 = new Currency(localSummaryItem.totalDebitsAmount, str2, paramSummaryInfo.getLocale());
        localReportDataItems.add().setData(localCurrency1.toString());
        if (paramBoolean)
        {
          BigDecimal localBigDecimal;
          if (localSummaryItem.toAmountItem != null) {
            localBigDecimal = localSummaryItem.toAmountItem.totalDebitsAmount;
          } else {
            localBigDecimal = new BigDecimal("0");
          }
          Currency localCurrency2 = new Currency(localBigDecimal, str2, paramSummaryInfo.getLocale());
          localReportDataItems.add().setData(localCurrency2.toString());
        }
        localReportDataItems.add().setData(paramString2);
        localReportDataItems.add().setData(String.valueOf(localSummaryItem.totalNumDebits));
        if (paramBoolean) {
          if (localSummaryItem.toAmountItem == null) {
            localReportDataItems.add().setData("0");
          } else {
            localReportDataItems.add().setData(String.valueOf(localSummaryItem.toAmountItem.totalNumDebits));
          }
        }
        localReportRow = new ReportRow(paramSummaryInfo.getLocale());
        localReportRow.setDataItems(localReportDataItems);
        localReportResult.addRow(localReportRow);
      }
    }
    if (paramString4 != null)
    {
      String str1 = null;
      if ((paramSummaryInfo.getUser() != null) && (paramSummaryInfo.getUser().getBaseCurrency() != null) && (paramSummaryInfo.getUser().getBaseCurrency().trim().length() > 0)) {
        str1 = paramSummaryInfo.getUser().getBaseCurrency();
      } else if ((paramSummaryInfo.getCurrentCurrencyCode() != null) && (paramSummaryInfo.getCurrentCurrencyCode().trim().length() > 0)) {
        str1 = paramSummaryInfo.getCurrentCurrencyCode();
      }
      if (str1 != null) {
        paramString4 = paramString4 + " (" + str1 + ")";
      }
      localReportDataItems = new ReportDataItems(paramSummaryInfo.getLocale());
      localReportDataItems.add().setData(paramString4);
      localReportDataItems.add().setData(paramSummaryInfo.getBaseCurrencyTotalDebit(paramArrayOfSummaryItem).toString());
      if (paramBoolean) {
        localReportDataItems.add().setData(" ");
      }
      localReportDataItems.add().setData(paramString2);
      localReportDataItems.add().setData(String.valueOf(l1));
      if (paramBoolean) {
        localReportDataItems.add().setData(" ");
      }
      localReportRow = new ReportRow(paramSummaryInfo.getLocale());
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
    }
  }
  
  private static ReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Properties paramProperties, ReportSortCriteria paramReportSortCriteria, String paramString, Locale paramLocale, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportTransactionAdapter.getTxReportData";
    ReportResult localReportResult = null;
    localReportResult = new ReportResult(paramLocale);
    try
    {
      a(paramSecureUser, localReportResult, paramReportCriteria, paramProperties, paramReportSortCriteria, paramString, paramLocale, paramHashMap);
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      localException.printStackTrace();
      String str2 = "Unable to generate the report.";
      throw new AdminException(str1, 19, str2);
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        String str3 = "Unable to generate the report.";
        throw new AdminException(str1, 19, str3);
      }
    }
    return localReportResult;
  }
  
  private static com.ffusion.beans.DateTime a(String paramString, boolean paramBoolean)
    throws ParseException
  {
    if (paramString != null)
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Date localDate1 = localSimpleDateFormat.parse(paramString);
      if (!paramBoolean) {
        return new com.ffusion.beans.DateTime(localDate1, Locale.getDefault());
      }
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate1);
      localCalendar.set(10, 23);
      localCalendar.set(12, 59);
      localCalendar.set(13, 59);
      localCalendar.set(14, 59);
      Date localDate2 = new Date(localCalendar.getTime().getTime());
      return new com.ffusion.beans.DateTime(localDate2, Locale.getDefault());
    }
    return null;
  }
  
  private static IReportResult jdField_do(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    String str1 = "ReportTransactionAdapter.buildACHReport";
    String str2 = "ACH_BATCH_PAYMENT";
    if ((paramString != null) && (paramString.equals("Tax Payment Report"))) {
      str2 = "ACH_BATCH_TAX";
    } else if ((paramString != null) && (paramString.equals("Child Support Payment Report"))) {
      str2 = "ACH_BATCH_CHILD_SUPPORT";
    }
    Locale localLocale1 = paramReportCriteria.getLocale();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    ResultSet localResultSet1 = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    String str3 = null;
    StringBuffer localStringBuffer1 = null;
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str4 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger1 = null;
    try
    {
      localInteger1 = Integer.valueOf(str4);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      localInteger1 = new Integer(-1);
    }
    String str5 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat1 = null;
    if (str5 == null) {
      localDateFormat1 = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat1 = DateFormatUtil.getFormatter(str5, localLocale1);
    }
    int i1 = -1;
    if (localInteger1 != null) {
      i1 = localInteger1.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str6 = (String)localEnumeration.nextElement();
      String str7 = localProperties2.getProperty(str6);
      if ((str7 == null) || (str7.length() == 0)) {
        localProperties2.remove(str6);
      }
    }
    int i2 = 0;
    try
    {
      i2 = Integer.parseInt(Profile.getSearchCriteria(localProperties2, "Affiliate Bank", "0"));
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      i2 = 0;
    }
    jdField_do(paramReportCriteria, localProperties2);
    String str8 = a(paramReportCriteria, localProperties2);
    String str9 = jdField_if(paramReportCriteria, localProperties2);
    ACHCompanies localACHCompanies = null;
    String str10 = null;
    if (paramHashMap != null) {
      localACHCompanies = (ACHCompanies)paramHashMap.get("ACHCompaniesForReport");
    }
    String str11 = (String)localProperties2.get("ACHCompany");
    if (str11 == null) {
      str11 = "AllACHCompanies";
    }
    if (str11.equals("AllACHCompanies"))
    {
      paramReportCriteria.setDisplayValue("ACHCompany", ReportConsts.getText(10028, localLocale1));
    }
    else if (localACHCompanies != null)
    {
      localObject1 = null;
      localObject2 = localACHCompanies.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (ACHCompany)((Iterator)localObject2).next();
        if ((localObject1 != null) && (((ACHCompany)localObject1).getCompanyID().equals(str11)))
        {
          paramReportCriteria.setDisplayValue("ACHCompany", ((ACHCompany)localObject1).getCompanyName());
          str10 = ((ACHCompany)localObject1).getID();
        }
      }
    }
    Object localObject1 = (String)localProperties2.get("Status");
    if (localObject1 != null) {
      paramReportCriteria.setDisplayValue("Status", a((String)localObject1, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.ach_status"));
    }
    Object localObject2 = null;
    try
    {
      localConnection = DBUtil.getConnection(bd, true, 2);
      StringBuffer localStringBuffer2 = new StringBuffer("select BPW_BankAcct.AcctId,BPW_BankAcct.AcctNumber,BPW_BankAcct.AcctNickName from BPW_BankAcct");
      ACHOffsetAccounts localACHOffsetAccounts = new ACHOffsetAccounts(localLocale1);
      ACHOffsetAccount localACHOffsetAccount = null;
      try
      {
        localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer2.toString());
        while (localResultSet1.next())
        {
          String str12 = localResultSet1.getString(1);
          str14 = localResultSet1.getString(2);
          str15 = localResultSet1.getString(3);
          localACHOffsetAccount = localACHOffsetAccounts.create();
          localACHOffsetAccount.setID(str12);
          localACHOffsetAccount.setNumber(str14);
          localACHOffsetAccount.setNickName(str15);
        }
      }
      catch (Exception localException2) {}finally
      {
        DBUtil.closeAll(localPreparedStatement1, localResultSet1);
        localPreparedStatement1 = null;
        localResultSet1 = null;
      }
      str3 = localProperties2.getProperty("ReportBy");
      if (str3 == null) {
        str3 = "ReportByStatus";
      }
      if (str3.equalsIgnoreCase("ReportByAction")) {
        localStringBuffer1 = new StringBuffer("select DISTINCT business.business_name,ACH_Company.CompName,ACH_Company.CompACHId,AUDIT_LOG.LOG_DATE,AUDIT_LOG.USER_ID,ACH_Batch.NonOffTotalDebit,ACH_Batch.NonOffTotalCredit,AUDIT_LOG.STATE,ACH_Batch.ExportFileName,ACH_Batch.Memo,ACH_Batch.BatchId,ACH_Batch.StdClassCode,ACH_Batch.BatchCategory,AUDIT_LOG.TRAN_ID,ACH_Batch.DueDate,ACH_Batch.OffsetAccountID,customer_directory.cust_id from AUDIT_LOG, business, customer,ACH_Company, ACH_Batch, customer_directory  where ACH_Batch.CompId=ACH_Company.CompId and business.directory_id=customer_directory.directory_id and business.business_id=AUDIT_LOG.BUSINESS_ID and AUDIT_LOG.TRAN_ID=ACH_Batch.LogId and AUDIT_LOG.SRVR_TID=ACH_Batch.BatchId  and customer.directory_id=AUDIT_LOG.USER_ID_INT ");
      } else {
        localStringBuffer1 = new StringBuffer("select DISTINCT business.business_name,ACH_Company.CompName,ACH_Company.CompACHId,AUDIT_LOG.LOG_DATE,AUDIT_LOG.USER_ID,ACH_Batch.NonOffTotalDebit,ACH_Batch.NonOffTotalCredit,AUDIT_LOG.STATE,ACH_Batch.ExportFileName,ACH_Batch.Memo,ACH_Batch.BatchId,ACH_Batch.StdClassCode,ACH_Batch.BatchCategory,AUDIT_LOG.TRAN_ID,ACH_Batch.DueDate,ACH_Batch.OffsetAccountID,customer_directory.cust_id from AUDIT_LOG, business, customer,ACH_Company, ACH_Batch, customer_directory  where ACH_Batch.CompId=ACH_Company.CompId and business.directory_id=customer_directory.directory_id and business.business_id=AUDIT_LOG.BUSINESS_ID and AUDIT_LOG.TRAN_ID=ACH_Batch.LogId and AUDIT_LOG.SRVR_TID=ACH_Batch.BatchId  and customer.directory_id=AUDIT_LOG.USER_ID_INT   and AUDIT_LOG.STATE=ACH_Batch.BatchStatus ");
      }
      String str13 = (String)localProperties2.get("ACHBatchCategory");
      if (str13 != null) {
        if (!str13.equalsIgnoreCase("AllCategories"))
        {
          if (str13.equals("ACH"))
          {
            str2 = "ACH_BATCH_PAYMENT";
            paramReportCriteria.setDisplayValue("ACHBatchCategory", ReportConsts.getText(2491, localLocale1));
          }
          else if (str13.equals("Tax"))
          {
            if ((paramString != null) && (!paramString.equals("Tax Payment Report")))
            {
              str2 = "ACH_BATCH_TAX";
              paramReportCriteria.setDisplayValue("ACHBatchCategory", ReportConsts.getText(2492, localLocale1));
            }
            else
            {
              localProperties2.remove("ACHBatchCategory");
            }
          }
          else if (str13.equals("ChildSupportPayments"))
          {
            if ((paramString != null) && (!paramString.equals("Child Support Payment Report")))
            {
              str2 = "ACH_BATCH_CHILD_SUPPORT";
              paramReportCriteria.setDisplayValue("ACHBatchCategory", ReportConsts.getText(2493, localLocale1));
            }
            else
            {
              localProperties2.remove("ACHBatchCategory");
            }
          }
        }
        else
        {
          str2 = null;
          paramReportCriteria.setDisplayValue("ACHBatchCategory", ReportConsts.getText(2490, localLocale1));
        }
      }
      if (str2 != null)
      {
        if ("ACH_BATCH_PAYMENT".equals(str2)) {
          localStringBuffer1.append(" and (ACH_Batch.BatchCategory = 'ACH_BATCH_PAYMENT' OR ACH_Batch.BatchCategory = 'ACH_BATCH_REVERSAL' OR ACH_Batch.BatchCategory = 'ACH_BATCH_PRENOTE') ");
        }
        if ("ACH_BATCH_TAX".equals(str2)) {
          localStringBuffer1.append(" and ACH_Batch.BatchCategory = 'ACH_BATCH_TAX' ");
        }
        if ("ACH_BATCH_CHILD_SUPPORT".equals(str2)) {
          localStringBuffer1.append(" and ACH_Batch.BatchCategory = 'ACH_BATCH_CHILD_SUPPORT' ");
        }
      }
      if (i2 != 0) {
        localStringBuffer1.append(" and business.affiliate_bank_id=?");
      }
      if (str8 != null) {
        localStringBuffer1.append(" and AUDIT_LOG.BUSINESS_ID=?");
      }
      if (str9 != null) {
        localStringBuffer1.append(" and customer.directory_id=?");
      }
      if (str10 != null) {
        localStringBuffer1.append(" and ACH_Batch.CompId=?");
      }
      if ((localObject1 != null) && (!((String)localObject1).equals("ALLSTATUSES"))) {
        localStringBuffer1.append(" and AUDIT_LOG.STATE=?");
      }
      String str14 = localProperties2.getProperty("StartDate");
      String str15 = localProperties2.getProperty("EndDate");
      try
      {
        localTimestamp1 = jdField_byte(str14);
        localTimestamp2 = jdField_byte(str15);
      }
      catch (ParseException localParseException)
      {
        throw new Exception("Invalid format for date search criteria.");
      }
      if (localTimestamp1 != null) {
        localStringBuffer1.append(" and AUDIT_LOG.LOG_DATE>=? ");
      }
      if (localTimestamp2 != null) {
        localStringBuffer1.append(" and AUDIT_LOG.LOG_DATE<=? ");
      }
      localStringBuffer1.append(" Order by AUDIT_LOG.STATE, business.business_name, ACH_Company.CompName, ");
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      if ((localReportSortCriteria != null) && (localReportSortCriteria.size() > 0))
      {
        localReportSortCriteria.setSortedBy("ORDINAL");
        Iterator localIterator = localReportSortCriteria.iterator();
        while (localIterator.hasNext())
        {
          localObject5 = (ReportSortCriterion)localIterator.next();
          localObject6 = ((ReportSortCriterion)localObject5).getName();
          if ((localObject6 != null) && (((String)localObject6).length() != 0))
          {
            if (((String)localObject6).equalsIgnoreCase("Date"))
            {
              localObject6 = "AUDIT_LOG.LOG_DATE";
            }
            else if (((String)localObject6).equalsIgnoreCase("User"))
            {
              localObject6 = "customer.user_name";
            }
            else
            {
              if (!((String)localObject6).equalsIgnoreCase("ACHBatchType")) {
                continue;
              }
              localObject6 = "ACH_Batch.StdClassCode";
            }
            localObject7 = ((ReportSortCriterion)localObject5).getAsc() ? "ASC" : "DESC";
            localStringBuffer1.append((String)localObject6);
            localStringBuffer1.append(" ");
            localStringBuffer1.append((String)localObject7);
            localStringBuffer1.append(", ");
          }
        }
      }
      localStringBuffer1.append("ACH_Batch.OffsetAccountID,ACH_Batch.BatchId");
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      if (i1 != -1) {
        localPreparedStatement1.setMaxRows(i1 + 1);
      }
      int i3 = 1;
      if (i2 != 0) {
        localPreparedStatement1.setInt(i3++, i2);
      }
      if (str8 != null) {
        localPreparedStatement1.setInt(i3++, Integer.parseInt(str8));
      }
      if (str9 != null) {
        localPreparedStatement1.setInt(i3++, Integer.parseInt(str9));
      }
      if (str10 != null) {
        localPreparedStatement1.setString(i3++, str10);
      }
      if ((localObject1 != null) && (!((String)localObject1).equals("ALLSTATUSES"))) {
        localPreparedStatement1.setString(i3++, (String)localObject1);
      }
      if (localTimestamp1 != null) {
        localPreparedStatement1.setTimestamp(i3++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        localPreparedStatement1.setTimestamp(i3++, localTimestamp2);
      }
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer1.toString());
      Object localObject5 = { 2000, 2001, 2002, 2003, 2013, 2004, 2006, 2008, 2009, 2010, 2012 };
      Object localObject6 = { 8, 7, 7, 7, 7, 7, 9, 8, 12, 17, 11 };
      Object localObject7 = new ArrayList();
      for (int i4 = 0; i4 < localObject5.length; i4++)
      {
        int i5 = localObject6[i4];
        int i6 = localObject5[i4];
        localObject8 = new ReportColumn(localLocale1);
        localObject9 = new ArrayList();
        ((ArrayList)localObject9).add(ReportConsts.getColumnName(i6, localLocale1));
        if (i6 == 2010)
        {
          ((ArrayList)localObject9).add(ReportConsts.getColumnName(2007, localLocale1));
          ((ArrayList)localObject9).add(ReportConsts.getColumnName(2005, localLocale1));
        }
        ((ReportColumn)localObject8).setLabels((ArrayList)localObject9);
        ((ReportColumn)localObject8).setDataType("java.lang.String");
        ((ReportColumn)localObject8).setWidthAsPercent(i5);
        ((ArrayList)localObject7).add(localObject8);
      }
      SummaryInfo localSummaryInfo = new SummaryInfo();
      localSummaryInfo.setLocale(localLocale1);
      localSummaryInfo.setUser(paramSecureUser);
      String str16 = "";
      String str17 = "";
      Object localObject8 = "";
      Object localObject9 = "";
      Locale localLocale2 = paramSecureUser.getLocale();
      localObject2 = new ReportResult(localLocale2);
      ((ReportResult)localObject2).init(paramReportCriteria);
      ((ReportResult)localObject2).setHeading(null);
      a((ReportResult)localObject2, localLocale2, -1, 0);
      ReportResult localReportResult1 = null;
      ReportResult localReportResult2 = null;
      ReportResult localReportResult3 = null;
      ReportResult localReportResult4 = null;
      int i7 = 0;
      int i8 = 0;
      ArrayList localArrayList = new ArrayList();
      int i9 = 0;
      int i10 = 1;
      Object localObject13;
      while (localResultSet1.next())
      {
        i9 = 1;
        if ((i1 != -1) && (i1 <= localSummaryInfo.numRows))
        {
          localObject10 = new HashMap();
          ((HashMap)localObject10).put("TRUNCATED", new Integer(i1));
          ((ReportResult)localObject2).setProperties((HashMap)localObject10);
          break;
        }
        if (!a(localResultSet1.getString(6), localResultSet1.getString(7), localProperties2))
        {
          if (((str17.length() > 0) && (!str17.equalsIgnoreCase(localResultSet1.getString(2)))) || ((((String)localObject8).length() > 0) && (!((String)localObject8).equalsIgnoreCase(localResultSet1.getString(8)))))
          {
            jdField_if(localReportResult1, localLocale1, ReportConsts.getText(2017, localLocale1) + " " + str17, localSummaryInfo, localSummaryInfo.getACHTotalSummaryItem());
            localSummaryInfo.resetACHTotal();
            i10 = 1;
          }
          if (((str16.length() > 0) && (!str16.equalsIgnoreCase(localResultSet1.getString(1)))) || ((((String)localObject8).length() > 0) && (!((String)localObject8).equalsIgnoreCase(localResultSet1.getString(8)))))
          {
            localObject10 = new StringBuffer(ReportConsts.getText(2018, localLocale1));
            ((StringBuffer)localObject10).append(" ").append(str16);
            if (localObject9 != null)
            {
              localObject9 = ((String)localObject9).trim();
              if (((String)localObject9).length() > 0) {
                ((StringBuffer)localObject10).append(" (").append((String)localObject9).append(")");
              }
            }
            jdField_if(localReportResult2, localLocale1, ((StringBuffer)localObject10).toString(), localSummaryInfo, localSummaryInfo.getBusinessTotalSummaryItem());
            localSummaryInfo.resetBusinessTotal();
            i10 = 1;
          }
          Object localObject11;
          if ((((String)localObject8).length() > 0) && (!((String)localObject8).equalsIgnoreCase(localResultSet1.getString(8))))
          {
            localObject10 = new StringBuffer(ReportConsts.getText(2052, localLocale1));
            localObject11 = localObject8;
            if (localObject11 != null) {
              localObject11 = a((String)localObject11, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.ach_status");
            }
            ((StringBuffer)localObject10).append(" ").append((String)localObject11);
            jdField_if(localReportResult3, localLocale1, ((StringBuffer)localObject10).toString(), localSummaryInfo, localSummaryInfo.getStatusTotalSummaryItem());
            localSummaryInfo.resetStatusTotal();
            i10 = 1;
          }
          if (!((String)localObject8).equalsIgnoreCase(localResultSet1.getString(8)))
          {
            localObject8 = localResultSet1.getString(8);
            localObject10 = localObject8;
            localObject11 = null;
            if (((String)localObject1).equals("ALLSTATUSES"))
            {
              if (localObject10 != null) {
                localObject10 = a((String)localObject10, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.ach_status");
              }
              localObject11 = new StringBuffer(ReportConsts.getText(2060, localLocale1) + " ");
              ((StringBuffer)localObject11).append((String)localObject10);
            }
            else
            {
              localObject11 = new StringBuffer("");
            }
            localReportResult3 = new ReportResult(localLocale2);
            ((ReportResult)localObject2).addSubReport(localReportResult3);
            localObject13 = new ReportHeading(localLocale1);
            ((ReportHeading)localObject13).setLabel(((StringBuffer)localObject11).toString());
            localReportResult3.setHeading((ReportHeading)localObject13);
            a(localReportResult3, localLocale1, -1, 5);
            str16 = "";
            str17 = "";
            i10 = 1;
          }
          if (!str16.equalsIgnoreCase(localResultSet1.getString(1)))
          {
            str16 = localResultSet1.getString(1);
            localObject9 = localResultSet1.getString(17);
            localObject10 = new StringBuffer(str16);
            if (localObject9 != null)
            {
              localObject9 = ((String)localObject9).trim();
              if (((String)localObject9).length() > 0) {
                ((StringBuffer)localObject10).append(" (").append((String)localObject9).append(")");
              }
            }
            localReportResult2 = new ReportResult(localLocale2);
            localReportResult3.addSubReport(localReportResult2);
            localObject11 = new ReportHeading(localLocale1);
            ((ReportHeading)localObject11).setLabel(ReportConsts.getText(2056, localLocale1) + " " + ((StringBuffer)localObject10).toString());
            localReportResult2.setHeading((ReportHeading)localObject11);
            a(localReportResult2, localLocale1, -1, 5);
            i10 = 1;
          }
          if (!str17.equalsIgnoreCase(localResultSet1.getString(2)))
          {
            str17 = localResultSet1.getString(2);
            localReportResult1 = new ReportResult(localLocale2);
            localReportResult2.addSubReport(localReportResult1);
            localObject10 = new ReportHeading(localLocale1);
            ((ReportHeading)localObject10).setLabel(ReportConsts.getText(2059, localLocale1) + " " + str17 + " / " + localResultSet1.getString(3));
            localReportResult1.setHeading((ReportHeading)localObject10);
            a(localReportResult1, localLocale1, -1, 5);
            localReportResult4 = new ReportResult(localLocale2);
            localReportResult1.addSubReport(localReportResult4);
            localReportResult4.setHeading(null);
            a(localReportResult4, localLocale1, -1, ((ArrayList)localObject7).size());
            for (int i11 = 0; i11 < ((ArrayList)localObject7).size(); i11++) {
              localReportResult4.addColumn((ReportColumn)((ArrayList)localObject7).get(i11));
            }
            i10 = 1;
          }
          localObject10 = localResultSet1.getString(14);
          localObject12 = localResultSet1.getString(11);
          if (!localArrayList.contains((String)localObject10 + (String)localObject12))
          {
            localArrayList.add((String)localObject10 + (String)localObject12);
            localObject13 = new ReportRow(localLocale1);
            if (i10 % 2 == 0) {
              ((ReportRow)localObject13).set("CELLBACKGROUND", "reportDataShaded");
            }
            ReportDataItems localReportDataItems = new ReportDataItems(localLocale2);
            ((ReportRow)localObject13).setDataItems(localReportDataItems);
            Timestamp localTimestamp3 = localResultSet1.getTimestamp(4);
            String str18 = localDateFormat1.format(localTimestamp3);
            a(localReportDataItems.add(), str18);
            String str19 = localResultSet1.getString(5);
            if (str19 == null) {
              str19 = "";
            }
            try
            {
              Integer localInteger2 = new Integer(str19);
              localObject14 = CustomerAdapter.getUserById(localInteger2.intValue());
              if (localObject14 != null) {
                str19 = ((User)localObject14).getUserName();
              }
            }
            catch (NumberFormatException localNumberFormatException3) {}
            localReportDataItems.add().setData(str19);
            String str20 = localResultSet1.getString(6);
            Object localObject14 = new BigDecimal(str20);
            str20 = a((BigDecimal)localObject14, Locale.getDefault());
            str20 = jdField_if(str20, localLocale2, true);
            localObject14 = ((BigDecimal)localObject14).movePointLeft(2);
            localReportDataItems.add().setData(str20);
            String str21 = localResultSet1.getString(7);
            BigDecimal localBigDecimal = new BigDecimal(str21);
            str21 = a(localBigDecimal, Locale.getDefault());
            str21 = jdField_if(str21, localLocale2, true);
            localBigDecimal = localBigDecimal.movePointLeft(2);
            localReportDataItems.add().setData(str21);
            String str22 = null;
            String str23 = localResultSet1.getString(16);
            if ((str23 != null) && (str23.length() > 0))
            {
              localACHOffsetAccount = localACHOffsetAccounts.getByID(str23);
              if (localACHOffsetAccount != null) {
                str22 = localACHOffsetAccount.getNumber() + "-" + localACHOffsetAccount.getNickName();
              }
            }
            if (str22 == null) {
              str22 = "";
            }
            localReportDataItems.add().setData(str22);
            String str24 = localResultSet1.getString(8);
            if (str24 == null) {
              str24 = "";
            }
            str24 = a(str24, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.ach_status");
            localReportDataItems.add().setData(str24);
            String str25 = localResultSet1.getString(10);
            Hashtable localHashtable = FFSUtil.stringToHashtable(str25);
            String str26 = null;
            if ((localHashtable != null) && (localHashtable.get("NAME") != null)) {
              str26 = (String)localHashtable.get("NAME");
            }
            a(localReportDataItems.add(), str26);
            a(localReportDataItems.add(), localResultSet1.getString(12));
            String str27 = localResultSet1.getString(13);
            if (str27 == null) {
              str27 = "";
            }
            if (str27.equals("ACH_BATCH_PAYMENT")) {
              str27 = ReportConsts.getText(2020, localLocale1);
            } else if (str27.equals("ACH_BATCH_TAX")) {
              str27 = ReportConsts.getText(2021, localLocale1);
            } else if (str27.equals("ACH_BATCH_CHILD_SUPPORT")) {
              str27 = ReportConsts.getText(2022, localLocale1);
            } else if (str27.equals("ACH_BATCH_PRENOTE")) {
              str27 = ReportConsts.getText(2023, localLocale1);
            } else if (str27.equals("ACH_BATCH_REVERSAL")) {
              str27 = ReportConsts.getText(2024, localLocale1);
            }
            localReportDataItems.add().setData(str27);
            String str28 = localResultSet1.getString(9);
            if (str28 == null) {
              str28 = "";
            }
            localReportDataItems.add().setData((String)localObject10 + ", " + (String)localObject12 + ", " + str28);
            int i12 = localResultSet1.getInt(15);
            i12 /= 100;
            DateFormat localDateFormat2 = DateFormatUtil.getFormatter("yyyyMMdd");
            str18 = localDateFormat1.format(localDateFormat2.parse("" + i12));
            a(localReportDataItems.add(), str18);
            localReportResult4.addRow((ReportRow)localObject13);
            localSummaryInfo.numRows += 1L;
            i10++;
            PreparedStatement localPreparedStatement2 = null;
            ResultSet localResultSet2 = null;
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select count (RecordId) from ACH_Record where TransCode in ('20','21','22','23','24','30','31','32','33','34','41','42','43','44','51','52','53','54') and BatchId=?");
            localPreparedStatement2.setString(1, (String)localObject12);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select count (RecordId) from ACH_Record where TransCode in ('20','21','22','23','24','30','31','32','33','34','41','42','43','44','51','52','53','54') and BatchId=?");
            String str29 = "0";
            String str30 = "0";
            if (localResultSet2.next()) {
              str29 = localResultSet2.getString(1);
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select count (RecordId) from ACH_Record where TransCode in ('25','26','27','28','29','35','36','37','38','39','46','47','48','49','55','56') and BatchId=?");
            localPreparedStatement2.setString(1, (String)localObject12);
            localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select count (RecordId) from ACH_Record where TransCode in ('25','26','27','28','29','35','36','37','38','39','46','47','48','49','55','56') and BatchId=?");
            if (localResultSet2.next()) {
              str30 = localResultSet2.getString(1);
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet2);
            int i13 = 0;
            int i14 = 0;
            if ((str29 == null) || (str29.length() == 0)) {
              str29 = "0";
            }
            i13 = Integer.valueOf(str29).intValue();
            if ((str30 == null) || (str30.length() == 0)) {
              str30 = "0";
            }
            i14 = Integer.valueOf(str30).intValue();
            SummaryItem localSummaryItem = new SummaryItem((BigDecimal)localObject14, i14, localBigDecimal, i13, 1L);
            localSummaryInfo.addBusinessTotal(localSummaryItem);
            localSummaryInfo.addGrandTotal(localSummaryItem);
            localSummaryInfo.addACHTotal(localSummaryItem);
            localSummaryInfo.addStatusTotal(localSummaryItem);
          }
        }
      }
      jdField_if(localReportResult1, localLocale1, ReportConsts.getText(2017, localLocale1) + " " + str17, localSummaryInfo, localSummaryInfo.getACHTotalSummaryItem());
      Object localObject10 = new StringBuffer(ReportConsts.getText(2018, localLocale1));
      ((StringBuffer)localObject10).append(" ").append(str16);
      if (localObject9 != null)
      {
        localObject9 = ((String)localObject9).trim();
        if (((String)localObject9).length() > 0) {
          ((StringBuffer)localObject10).append(" (").append((String)localObject9).append(")");
        }
      }
      jdField_if(localReportResult2, localLocale1, ((StringBuffer)localObject10).toString(), localSummaryInfo, localSummaryInfo.getBusinessTotalSummaryItem());
      Object localObject12 = null;
      if (((String)localObject1).equals("ALLSTATUSES"))
      {
        localObject12 = new StringBuffer(ReportConsts.getText(2052, localLocale1));
        localObject13 = localObject8;
        if (localObject13 != null) {
          localObject13 = a((String)localObject13, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.ach_status");
        }
        ((StringBuffer)localObject12).append(" ").append((String)localObject13);
      }
      else
      {
        localObject13 = ReportConsts.getText(2019, localLocale1);
        localObject12 = new StringBuffer((String)localObject13);
      }
      jdField_if(localReportResult3, localLocale1, ((StringBuffer)localObject12).toString(), localSummaryInfo, localSummaryInfo.getStatusTotalSummaryItem());
      if ((((String)localObject1).equals("ALLSTATUSES")) && (i9 != 0))
      {
        localReportResult3 = new ReportResult(localLocale2);
        ((ReportResult)localObject2).addSubReport(localReportResult3);
        localObject13 = new ReportHeading(localLocale1);
        ((ReportHeading)localObject13).setLabel(ReportConsts.getText(2019, localLocale1));
        localReportResult3.setHeading((ReportHeading)localObject13);
        a(localReportResult3, localLocale1, -1, 5);
        jdField_if(localReportResult3, localLocale1, null, localSummaryInfo, localSummaryInfo.getGrandTotalSummaryItem());
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.out);
      if (localObject2 != null) {
        ((ReportResult)localObject2).setError(localException1);
      }
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement1, localResultSet1);
      try
      {
        if (localObject2 != null) {
          ((ReportResult)localObject2).fini();
        }
      }
      catch (RptException localRptException)
      {
        String str31 = "Unable to generate the report.";
        localRptException.printStackTrace(System.out);
      }
    }
    return localObject2;
  }
  
  private static boolean a(String paramString1, String paramString2, Properties paramProperties)
  {
    String str1 = paramProperties.getProperty("DebitFromAmount");
    String str2 = paramProperties.getProperty("DebitToAmount");
    String str3 = paramProperties.getProperty("CreditFromAmount");
    String str4 = paramProperties.getProperty("CreditToAmount");
    return (a(paramString2, str3, str4)) || (a(paramString1, str1, str2));
  }
  
  private static boolean a(String paramString1, String paramString2, String paramString3)
  {
    double d1 = new BigDecimal(paramString1).abs().doubleValue();
    double d2;
    if (paramString2 != null)
    {
      d2 = new BigDecimal(paramString2).abs().multiply(new BigDecimal(100.0D)).doubleValue();
      if (d1 < d2) {
        return true;
      }
    }
    if (paramString3 != null)
    {
      d2 = new BigDecimal(paramString3).abs().multiply(new BigDecimal(100.0D)).doubleValue();
      if (d1 > d2) {
        return true;
      }
    }
    return false;
  }
  
  private static String jdField_for(String paramString, Locale paramLocale)
  {
    return jdField_if(paramString, paramLocale, true);
  }
  
  private static String jdField_if(String paramString, Locale paramLocale, boolean paramBoolean)
  {
    if (paramString == null) {}
    for (paramString = ""; paramString.length() < 2; paramString = "0" + paramString) {}
    int i1 = paramString.length();
    if (i1 <= 2) {
      paramString = "0." + paramString;
    } else if (paramString.charAt(i1 - 2) == '.') {
      paramString = paramString + "0";
    } else if (paramString.indexOf('.') == -1) {
      paramString = paramString.substring(0, i1 - 2) + "." + paramString.substring(i1 - 2);
    }
    if (paramBoolean == true)
    {
      Currency localCurrency = new Currency(paramString, paramLocale);
      return localCurrency.getCurrencyStringNoSymbol();
    }
    return paramString;
  }
  
  private static String a(String paramString, Locale paramLocale, boolean paramBoolean)
  {
    if (paramString == null) {
      paramString = "0";
    }
    BigDecimal localBigDecimal = new BigDecimal(paramString);
    localBigDecimal = localBigDecimal.divide(new BigDecimal("100"), 2, 5);
    return jdField_if(localBigDecimal.toString(), paramLocale, paramBoolean);
  }
  
  private static IReportResult jdField_if(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    String str1 = "ReportTransactionAdapter.buildACHFileReport";
    Locale localLocale1 = paramReportCriteria.getLocale();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1 = 1;
    ArrayList localArrayList1 = new ArrayList();
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str2 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(str2);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      localInteger = new Integer(-1);
    }
    String str3 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str3 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str3, localLocale1);
    }
    int i2 = -1;
    if (localInteger != null) {
      i2 = localInteger.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str4 = (String)localEnumeration.nextElement();
      String str5 = localProperties2.getProperty(str4);
      if ((str5 == null) || (str5.length() == 0)) {
        localProperties2.remove(str4);
      }
    }
    int i3 = 0;
    try
    {
      i3 = Integer.parseInt(Profile.getSearchCriteria(localProperties2, "Affiliate Bank", "0"));
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      i3 = 0;
    }
    jdField_do(paramReportCriteria, localProperties2);
    String str6 = a(paramReportCriteria, localProperties2);
    String str7 = jdField_if(paramReportCriteria, localProperties2);
    String str8 = (String)localProperties2.get("ACHFileDetailLevel");
    if ((str8 == null) || (str8.trim().length() == 0)) {
      str8 = "File Summary";
    }
    String str9 = (String)localProperties2.get("CutOffTimes");
    Object localObject2;
    Object localObject3;
    Object localObject4;
    String str10;
    int i4;
    if (str9 != null) {
      if (str9.indexOf("AllCutOffTimes") >= 0)
      {
        paramReportCriteria.setDisplayValue("CutOffTimes", ReportConsts.getText(10050, paramReportCriteria.getLocale()));
      }
      else
      {
        i1 = 0;
        localObject1 = new StringTokenizer(str9, ",", false);
        while (((StringTokenizer)localObject1).hasMoreTokens())
        {
          localObject2 = ((StringTokenizer)localObject1).nextToken().trim();
          localArrayList1.add(localObject2);
        }
        localObject2 = null;
        localObject3 = new CutOffTime();
        localObject4 = (String[])localArrayList1.toArray(new String[0]);
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        paramHashMap.put("CutOffIDList", localObject4);
        try
        {
          localObject2 = PaymentsAdminHandler.getCutOffTimes(paramSecureUser, (CutOffTime)localObject3, paramHashMap);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
        if (localObject2 != null)
        {
          str10 = "";
          Iterator localIterator = ((CutOffTimes)localObject2).iterator();
          i4 = 1;
          while (localIterator.hasNext())
          {
            localObject3 = (CutOffTime)localIterator.next();
            if (localArrayList1.contains(((CutOffTime)localObject3).getCutOffId()))
            {
              if (i4 == 0) {
                str10 = str10 + ", ";
              }
              i4 = 0;
              str10 = str10 + ((CutOffTime)localObject3).getDayOfWeekString() + ((CutOffTime)localObject3).getTimeOfDay();
            }
          }
          paramReportCriteria.setDisplayValue("CutOffTimes", str10);
        }
      }
    }
    Object localObject1 = null;
    try
    {
      localConnection = DBUtil.getConnection(bd, true, 2);
      localObject2 = localProperties2.getProperty("StartDate");
      localObject3 = localProperties2.getProperty("EndDate");
      localObject4 = null;
      str10 = null;
      if ((localObject2 != null) && (((String)localObject2).length() > 0)) {
        try
        {
          localObject4 = jdField_case((String)localObject2).toString();
        }
        catch (ParseException localParseException1)
        {
          throw new BCReportException(str1, 13, "Invalid format for startDate: " + (String)localObject2);
        }
      }
      if ((localObject3 != null) && (((String)localObject3).length() > 0)) {
        try
        {
          str10 = jdField_case((String)localObject3).toString();
        }
        catch (ParseException localParseException2)
        {
          throw new BCReportException(str1, 13, "Invalid format for endDate: " + (String)localObject3);
        }
      }
      StringBuffer localStringBuffer = new StringBuffer(jdField_case);
      if (i3 != 0) {
        localStringBuffer.append(u);
      }
      if ((localObject4 != null) && (((String)localObject4).trim().length() != 0)) {
        localStringBuffer.append(aR);
      }
      if ((str10 != null) && (str10.trim().length() != 0)) {
        localStringBuffer.append(a3);
      }
      localStringBuffer.append(ae);
      if (paramString.equals("ACH File Report")) {
        localStringBuffer.append(af);
      } else if (paramString.equals("ACH File Upload Report")) {
        localStringBuffer.append(bm);
      }
      localStringBuffer.append(O);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i4 = 1;
      if (i2 != -1) {
        localPreparedStatement.setMaxRows(i2 + 1);
      }
      if (i3 != 0) {
        localPreparedStatement.setInt(i4++, i3);
      }
      if ((localObject4 != null) && (((String)localObject4).trim().length() != 0)) {
        localPreparedStatement.setString(i4++, (String)localObject4);
      }
      if ((str10 != null) && (str10.trim().length() != 0)) {
        localPreparedStatement.setString(i4++, str10);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      SummaryInfo localSummaryInfo = new SummaryInfo();
      localSummaryInfo.setLocale(localLocale1);
      localSummaryInfo.setUser(paramSecureUser);
      Object localObject5 = "";
      Locale localLocale2 = paramSecureUser.getLocale();
      localObject1 = new ReportResult(localLocale2);
      ((ReportResult)localObject1).init(paramReportCriteria);
      ((ReportResult)localObject1).setHeading(null);
      a((ReportResult)localObject1, localLocale2, -1, 0);
      ReportResult localReportResult = null;
      ArrayList localArrayList2 = new ArrayList();
      while (localResultSet.next())
      {
        if ((i2 != -1) && (i2 <= localSummaryInfo.numRows))
        {
          localObject6 = new HashMap();
          ((HashMap)localObject6).put("TRUNCATED", new Integer(i2));
          ((ReportResult)localObject1).setProperties((HashMap)localObject6);
          break;
        }
        Object localObject6 = localResultSet.getString(2);
        if (!localArrayList2.contains(localObject6))
        {
          localArrayList2.add(localObject6);
          Object localObject7 = localResultSet.getString(6);
          String str11 = localResultSet.getString(7);
          String str12 = localResultSet.getString(8);
          String str13 = localResultSet.getString(1);
          String str14 = localResultSet.getString(5);
          String str15 = localResultSet.getString(3);
          Object localObject8;
          if (localObject7 == null)
          {
            localObject7 = str15;
            if ((str12 == null) || ("Internal".equalsIgnoreCase(str12))) {
              localObject7 = (String)localObject7 + " " + ReportConsts.getText(2054, localLocale1);
            } else {
              localObject7 = (String)localObject7 + " " + ReportConsts.getText(2053, localLocale1);
            }
          }
          else
          {
            localObject8 = "";
            if ((str14 != null) && (!str14.equals("0"))) {
              localObject8 = ResourceUtil.getString("Day" + str14, "com.ffusion.beans.affiliatebank.resources", localLocale2);
            }
            localObject7 = str15 + " (" + (String)localObject8 + " " + (String)localObject7 + ") ";
          }
          if (i3 == 0) {
            localObject7 = str13 + " : " + (String)localObject7;
          }
          if ((i1 != 0) || ((str11 != null) && ((str11 == null) || (localArrayList1.contains(str11)))))
          {
            localObject5 = localObject7;
            localReportResult = new ReportResult(localLocale2);
            ((ReportResult)localObject1).addSubReport(localReportResult);
            localObject8 = new ReportHeading(localLocale1);
            ((ReportHeading)localObject8).setLabel(ReportConsts.getText(2058, localLocale1) + " " + (String)localObject5);
            localReportResult.setHeading((ReportHeading)localObject8);
            a(localReportResult, paramString, (String)localObject6, str15, str6, str7, localProperties2, localProperties1, localSummaryInfo, str8);
            if ((localSummaryInfo.getFileTotalSummaryItem().totalNumCredits == 0L) && (localSummaryInfo.getFileTotalSummaryItem().totalNumDebits == 0L))
            {
              localObject8 = new ReportHeading(localLocale1);
              localSummaryInfo.numRows += 1L;
              ((ReportHeading)localObject8).setLabel(ReportConsts.getText(2503, localLocale1));
              localReportResult.setSectionHeading((ReportHeading)localObject8);
            }
          }
        }
      }
      if (localReportResult != null)
      {
        localReportResult = new ReportResult(localLocale2);
        ((ReportResult)localObject1).addSubReport(localReportResult);
        a(localReportResult, localLocale1, -1, 4);
        a(localReportResult, localLocale1, ReportConsts.getText(2019, localLocale1) + " " + ReportConsts.getText(2055, localLocale1), localSummaryInfo, localSummaryInfo.getGrandTotalSummaryItem());
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
      if (localObject1 != null) {
        ((ReportResult)localObject1).setError(localException);
      }
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
      try
      {
        if (localObject1 != null) {
          ((ReportResult)localObject1).fini();
        }
      }
      catch (RptException localRptException)
      {
        String str16 = "Unable to generate the report.";
        localRptException.printStackTrace(System.out);
      }
    }
    return localObject1;
  }
  
  private static void jdField_if(ReportResult paramReportResult, Locale paramLocale, String paramString, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem)
    throws RptException
  {
    if (paramReportResult == null) {
      return;
    }
    if (paramString != null)
    {
      localObject = new ReportHeading(paramLocale);
      ((ReportHeading)localObject).setLabel(paramString);
      paramReportResult.setSectionHeading((ReportHeading)localObject);
    }
    a(paramReportResult, 2014, "java.lang.String", 20, null);
    a(paramReportResult, 2015, "java.lang.String", 20, null);
    Object localObject = new ReportColumn(paramLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(2002, paramLocale) + " (" + paramSummaryItem.getCurrencyCode() + ")");
    ((ReportColumn)localObject).setLabels(localArrayList1);
    ((ReportColumn)localObject).setDataType("java.lang.String");
    ((ReportColumn)localObject).setWidthAsPercent(20);
    paramReportResult.addColumn((ReportColumn)localObject);
    a(paramReportResult, 2016, "java.lang.String", 20, null);
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2003, paramLocale) + " (" + paramSummaryItem.getCurrencyCode() + ")");
    localReportColumn.setLabels(localArrayList2);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(20);
    paramReportResult.addColumn(localReportColumn);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumBatches);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumDebits);
    Currency localCurrency1 = new Currency(paramSummaryItem.totalDebitsAmount, paramSummaryItem.getCurrencyCode(), paramLocale);
    localReportDataItems.add().setData(localCurrency1.toString());
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumCredits);
    Currency localCurrency2 = new Currency(paramSummaryItem.totalCreditsAmount, paramSummaryItem.getCurrencyCode(), paramLocale);
    localReportDataItems.add().setData(localCurrency2.toString());
    paramReportResult.addRow(localReportRow);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, String paramString, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem)
    throws RptException
  {
    if (paramReportResult == null) {
      return;
    }
    if (paramString != null)
    {
      localObject = new ReportHeading(paramLocale);
      ((ReportHeading)localObject).setLabel(paramString);
      paramReportResult.setSectionHeading((ReportHeading)localObject);
    }
    a(paramReportResult, 2015, "java.lang.String", 25, null);
    Object localObject = new ReportColumn(paramLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(2002, paramLocale) + " (" + paramSummaryItem.getCurrencyCode() + ")");
    ((ReportColumn)localObject).setLabels(localArrayList1);
    ((ReportColumn)localObject).setDataType("java.lang.String");
    ((ReportColumn)localObject).setWidthAsPercent(25);
    paramReportResult.addColumn((ReportColumn)localObject);
    a(paramReportResult, 2016, "java.lang.String", 25, null);
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2003, paramLocale) + " (" + paramSummaryItem.getCurrencyCode() + ")");
    localReportColumn.setLabels(localArrayList2);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(25);
    paramReportResult.addColumn(localReportColumn);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumDebits);
    Currency localCurrency1 = new Currency(paramSummaryItem.totalDebitsAmount, paramSummaryItem.getCurrencyCode(), paramLocale);
    localReportDataItems.add().setData(localCurrency1.toString());
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumCredits);
    Currency localCurrency2 = new Currency(paramSummaryItem.totalCreditsAmount, paramSummaryItem.getCurrencyCode(), paramLocale);
    localReportDataItems.add().setData(localCurrency2.toString());
    paramReportResult.addRow(localReportRow);
  }
  
  private static void a(ReportResult paramReportResult, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Properties paramProperties1, Properties paramProperties2, SummaryInfo paramSummaryInfo, String paramString6)
  {
    paramSummaryInfo.resetFileTotal();
    Locale localLocale = paramReportResult.getLocale();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    String str1 = null;
    String str2 = "";
    Object localObject1 = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList1 = new ArrayList();
    String str3 = paramProperties2.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str3 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str3, localLocale);
    }
    String str5;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    Object localObject9;
    Object localObject10;
    Object localObject11;
    Object localObject13;
    Object localObject14;
    Object localObject16;
    Object localObject18;
    Object localObject19;
    Object localObject20;
    Object localObject21;
    Object localObject22;
    Object localObject24;
    Object localObject25;
    Object localObject23;
    Object localObject15;
    int i22;
    Object localObject27;
    int i27;
    if (paramString1.equals("ACH File Report"))
    {
      localObject2 = "select distinct ACH_Batch.NonOffTotalCredit, ACH_Batch.NonOffTotalDebit, ACH_Batch.StdClassCode, ACH_Batch.SubmittedBy, ACH_Batch.LogId, ACH_Batch.BatchStatus, ACH_Batch.BatchId, ACH_Batch.EffectiveDate, ACH_Batch.BatchNum, ACH_Batch.RecordCount, ACH_Company.CompName, ACH_Company.CompACHId, ACH_Batch.ExportFileName, ACH_Batch.ProcessId, business.business_name, customer_directory.cust_id from ACH_Batch, ACH_Company, business, customer_directory, AUDIT_LOG where business.directory_id=AUDIT_LOG.BUSINESS_ID and AUDIT_LOG.TRAN_ID=ACH_Batch.LogId and AUDIT_LOG.SRVR_TID=ACH_Batch.BatchId and business.directory_id=customer_directory.directory_id and ProcessId = ? and ACH_Company.CompId = ACH_Batch.CompId order by business.business_name, ACH_Batch.BatchId";
      localObject3 = "select RecordContent, TransCode from ACH_Record where BatchId=? order by RecordId";
      try
      {
        localConnection = DBUtil.getConnection(bd, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, (String)localObject2);
        localPreparedStatement.setString(1, paramString2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, (String)localObject2);
        while (localResultSet.next())
        {
          String str4 = localResultSet.getString(1);
          str5 = localResultSet.getString(2);
          if (!a(str5, str4, paramProperties1))
          {
            localObject4 = new HashMap();
            localArrayList1.add(localObject4);
            localObject5 = localResultSet.getString(7);
            localObject6 = jdField_for(str5, localLocale);
            str5 = jdField_if(str5, localLocale, false);
            localObject7 = jdField_for(str4, localLocale);
            str4 = jdField_if(str4, localLocale, false);
            localObject8 = new BigDecimal(str5);
            localObject9 = new BigDecimal(str4);
            localObject10 = localResultSet.getString(3);
            localObject11 = localResultSet.getString(5);
            localObject12 = localResultSet.getString(6);
            localObject13 = localResultSet.getString(8);
            localObject14 = DateFormatUtil.getFormatter("yyMMdd");
            localObject13 = localDateFormat.format(((DateFormat)localObject14).parse((String)localObject13));
            int i13 = localResultSet.getInt(9);
            int i15 = localResultSet.getInt(10);
            localObject16 = localResultSet.getString(11);
            String str7 = localResultSet.getString(12);
            String str9 = localResultSet.getString(13);
            localObject18 = localResultSet.getString(15);
            localObject19 = localResultSet.getString(16);
            ((HashMap)localObject4).put("BusinessCustID", localObject19);
            ((HashMap)localObject4).put("ExportFile", str9);
            ((HashMap)localObject4).put("BusinessName", localObject18);
            ((HashMap)localObject4).put("CompName", localObject16);
            ((HashMap)localObject4).put("CompID", str7);
            ((HashMap)localObject4).put("SECC", localObject10);
            ((HashMap)localObject4).put("EffectiveDate", localObject13);
            ((HashMap)localObject4).put("ID", localObject5);
            ((HashMap)localObject4).put("TotalDebits", str5);
            ((HashMap)localObject4).put("TotalCredits", str4);
            ((HashMap)localObject4).put("TotalDebitsDisplay", localObject6);
            ((HashMap)localObject4).put("TotalCreditsDisplay", localObject7);
            ((HashMap)localObject4).put("RecordCount", "" + i15);
            ((HashMap)localObject4).put("LogID", localObject11);
            ((HashMap)localObject4).put("BatchNum", "" + i13);
            ((HashMap)localObject4).put("BatchStatus", a((String)localObject12, localLocale, "com.ffusion.beans.reporting.ach_status"));
            localObject20 = new ArrayList();
            ((HashMap)localObject4).put("Entries", localObject20);
            localObject21 = null;
            localObject22 = null;
            localObject21 = DBUtil.prepareStatement(localConnection, (String)localObject3);
            ((PreparedStatement)localObject21).setString(1, (String)localObject5);
            int i21 = 0;
            int i23 = 0;
            localObject22 = DBUtil.executeQuery((PreparedStatement)localObject21, (String)localObject3);
            while (((ResultSet)localObject22).next())
            {
              localObject24 = ((ResultSet)localObject22).getString(1);
              localObject25 = new HashMap();
              localObject23 = ((ResultSet)localObject22).getString(2);
              ((HashMap)localObject25).put("TransCode", localObject23);
              ((HashMap)localObject25).put("RDFI_ID", ((String)localObject24).substring(3, 12));
              ((HashMap)localObject25).put("DFIAcctNum", ((String)localObject24).substring(12, 29));
              ((HashMap)localObject25).put("RecCoName", ((String)localObject24).substring(54, 76));
              ((HashMap)localObject25).put("IdentNum", ((String)localObject24).substring(39, 54));
              ((HashMap)localObject25).put("Amount", ((String)localObject24).substring(29, 39));
              ((HashMap)localObject25).put("TraceNum", ((String)localObject24).substring(79, 94));
              ((ArrayList)localObject20).add(localObject25);
              if (("20".equals(localObject23)) || ("21".equals(localObject23)) || ("22".equals(localObject23)) || ("23".equals(localObject23)) || ("24".equals(localObject23)) || ("30".equals(localObject23)) || ("31".equals(localObject23)) || ("32".equals(localObject23)) || ("33".equals(localObject23)) || ("34".equals(localObject23)) || ("41".equals(localObject23)) || ("42".equals(localObject23)) || ("43".equals(localObject23)) || ("44".equals(localObject23)) || ("51".equals(localObject23)) || ("52".equals(localObject23)) || ("53".equals(localObject23)) || ("54".equals(localObject23))) {
                i23++;
              } else {
                i21++;
              }
            }
            localObject24 = new SummaryItem((BigDecimal)localObject8, i21, (BigDecimal)localObject9, i23);
            paramSummaryInfo.addFileTotal(localObject24);
            paramSummaryInfo.addGrandTotal(localObject24);
            ((HashMap)localObject4).put("TotalNumberCredits", "" + i23);
            ((HashMap)localObject4).put("TotalNumberDebits", "" + i21);
            DBUtil.closeAll((PreparedStatement)localObject21, (ResultSet)localObject22);
          }
        }
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      finally
      {
        DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
      }
    }
    else if (paramString1.equals("ACH File Upload Report"))
    {
      localObject2 = new StringBuffer("select distinct ACH_FileBatch.TotalCredits, ACH_FileBatch.TotalDebits, ACH_FileBatch.StdEntryClassCode, ACH_File.LogId, ACH_File.FileStatus, ACH_FileBatch.BatchId, ACH_FileBatch.EffectiveEntryDate, ACH_FileBatch.BatchNumber, ACH_File.RecordCount, ACH_FileBatch.CompanyName, ACH_FileBatch.CompanyIdentification, ACH_File.ExportFileName, ACH_File.ProcessId, business.business_name, ACH_File.SubmittedBy, ACH_File.TotalCredit, ACH_File.TotalDebit, ACH_File.NumberOfCredits, ACH_File.NumberOfDebits,customer_directory.cust_id from ACH_FileBatch, ACH_File, business, customer_directory, AUDIT_LOG where business.directory_id=AUDIT_LOG.BUSINESS_ID and AUDIT_LOG.TRAN_ID=ACH_File.LogId and AUDIT_LOG.SRVR_TID=ACH_File.FileId and business.directory_id=customer_directory.directory_id and ACH_File.FileId = ACH_FileBatch.FileId and ProcessId = ? ");
      localObject3 = "select TransactionCode, RecvDFIIdentification, DFIAccountNumber, RcvCompIndvName, IdentificationNumber, Amount, TraceNumber from ACH_FileEntry where BatchId=? order by EntryId";
      try
      {
        localConnection = DBUtil.getConnection(bd, true, 2);
        if (paramString4 != null) {
          ((StringBuffer)localObject2).append(" and ACH_File.CustomerId=? ");
        }
        if (paramString5 != null) {
          ((StringBuffer)localObject2).append(" and ACH_File.SubmittedBy=?");
        }
        ((StringBuffer)localObject2).append(" order by business.business_name, ACH_File.ExportFileName");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, ((StringBuffer)localObject2).toString());
        int i1 = 1;
        localPreparedStatement.setString(i1++, paramString2);
        if (paramString4 != null) {
          localPreparedStatement.setString(i1++, paramString4);
        }
        if (paramString5 != null) {
          localPreparedStatement.setString(i1++, paramString5);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, ((StringBuffer)localObject2).toString());
        while (localResultSet.next())
        {
          str5 = localResultSet.getString(16);
          localObject4 = localResultSet.getString(17);
          if (!a((String)localObject4, str5, paramProperties1))
          {
            str5 = jdField_for(str5, localLocale);
            localObject4 = jdField_for((String)localObject4, localLocale);
            localObject5 = new HashMap();
            localArrayList1.add(localObject5);
            localObject6 = localResultSet.getString(6);
            localObject7 = "" + localResultSet.getInt(1);
            localObject8 = jdField_for((String)localObject7, localLocale);
            localObject7 = jdField_if((String)localObject7, localLocale, false);
            localObject9 = "" + localResultSet.getInt(2);
            localObject10 = jdField_for((String)localObject9, localLocale);
            localObject9 = jdField_if((String)localObject9, localLocale, false);
            localObject11 = new BigDecimal((String)localObject9);
            localObject12 = new BigDecimal((String)localObject7);
            localObject13 = localResultSet.getString(3);
            localObject14 = localResultSet.getString(4);
            String str6 = localResultSet.getString(5);
            localObject15 = localResultSet.getString(7);
            localObject16 = DateFormatUtil.getFormatter("yyMMdd");
            localObject15 = localDateFormat.format(((DateFormat)localObject16).parse((String)localObject15));
            int i16 = localResultSet.getInt(8);
            int i17 = localResultSet.getInt(9);
            localObject18 = localResultSet.getString(10);
            localObject19 = localResultSet.getString(11);
            localObject20 = localResultSet.getString(12);
            localObject21 = localResultSet.getString(14);
            ((HashMap)localObject5).put("ExportFile", localObject20);
            ((HashMap)localObject5).put("BusinessName", localObject21);
            localObject22 = localResultSet.getString(15);
            if (localObject22 == null) {
              localObject22 = "";
            }
            try
            {
              Integer localInteger = new Integer((String)localObject22);
              User localUser = CustomerAdapter.getUserById(localInteger.intValue());
              if (localUser != null) {
                localObject22 = localUser.getName();
              }
            }
            catch (NumberFormatException localNumberFormatException) {}
            ((HashMap)localObject5).put("UserName", localObject22);
            ((HashMap)localObject5).put("CompName", localObject18);
            ((HashMap)localObject5).put("CompID", localObject19);
            ((HashMap)localObject5).put("SECC", localObject13);
            ((HashMap)localObject5).put("EffectiveDate", localObject15);
            ((HashMap)localObject5).put("ID", localObject6);
            ((HashMap)localObject5).put("RecordCount", "" + i17);
            ((HashMap)localObject5).put("TotalDebits", localObject9);
            ((HashMap)localObject5).put("TotalCredits", localObject7);
            ((HashMap)localObject5).put("TotalDebitsDisplay", localObject10);
            ((HashMap)localObject5).put("TotalCreditsDisplay", localObject8);
            ((HashMap)localObject5).put("LogID", localObject14);
            ((HashMap)localObject5).put("BatchNum", "" + i16);
            ((HashMap)localObject5).put("BatchStatus", a(str6, localLocale, "com.ffusion.beans.reporting.ach_status"));
            i22 = localResultSet.getInt(18);
            int i24 = localResultSet.getInt(19);
            localObject23 = localResultSet.getString(20);
            ((HashMap)localObject5).put("BusinessCustID", localObject23);
            ((HashMap)localObject5).put("FileTotalDebitAmt", localObject4);
            ((HashMap)localObject5).put("FileTotalCreditAmt", str5);
            ((HashMap)localObject5).put("FileTotalNumberCredits", "" + i22);
            ((HashMap)localObject5).put("FileTotalNumberDebits", "" + i24);
            localObject24 = new ArrayList();
            ((HashMap)localObject5).put("Entries", localObject24);
            localObject25 = null;
            localObject27 = null;
            localObject25 = DBUtil.prepareStatement(localConnection, ((String)localObject3).toString());
            ((PreparedStatement)localObject25).setString(1, (String)localObject6);
            i27 = 0;
            int i28 = 0;
            localObject27 = DBUtil.executeQuery((PreparedStatement)localObject25, ((String)localObject3).toString());
            while (((ResultSet)localObject27).next())
            {
              localObject28 = new HashMap();
              String str11 = ((ResultSet)localObject27).getString(1);
              ((HashMap)localObject28).put("TransCode", str11);
              String str12 = ((ResultSet)localObject27).getString(2);
              if ((str12 != null) && (str12.length() == 8)) {
                str12 = str12 + RoutingNumberUtil.getRoutingNumber_CheckDigit(str12);
              }
              ((HashMap)localObject28).put("RDFI_ID", str12);
              ((HashMap)localObject28).put("DFIAcctNum", ((ResultSet)localObject27).getString(3));
              ((HashMap)localObject28).put("RecCoName", ((ResultSet)localObject27).getString(4));
              ((HashMap)localObject28).put("IdentNum", ((ResultSet)localObject27).getString(5));
              ((HashMap)localObject28).put("Amount", ((ResultSet)localObject27).getString(6));
              ((HashMap)localObject28).put("TraceNum", ((ResultSet)localObject27).getString(7));
              ((ArrayList)localObject24).add(localObject28);
              if (("20".equals(str11)) || ("21".equals(str11)) || ("22".equals(str11)) || ("23".equals(str11)) || ("24".equals(str11)) || ("30".equals(str11)) || ("31".equals(str11)) || ("32".equals(str11)) || ("33".equals(str11)) || ("34".equals(str11)) || ("41".equals(str11)) || ("42".equals(str11)) || ("43".equals(str11)) || ("44".equals(str11)) || ("51".equals(str11)) || ("52".equals(str11)) || ("53".equals(str11)) || ("54".equals(str11))) {
                i28++;
              } else {
                i27++;
              }
            }
            Object localObject28 = new SummaryItem((BigDecimal)localObject11, i27, (BigDecimal)localObject12, i28);
            paramSummaryInfo.addFileTotal(localObject28);
            paramSummaryInfo.addGrandTotal(localObject28);
            ((HashMap)localObject5).put("TotalNumberCredits", "" + i28);
            ((HashMap)localObject5).put("TotalNumberDebits", "" + i27);
            DBUtil.closeAll((PreparedStatement)localObject25, (ResultSet)localObject27);
          }
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      finally
      {
        DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
      }
    }
    if (localArrayList1.size() == 0) {
      return;
    }
    Object localObject2 = { 2051, 2029, 2030, 2031, 2032, 2033, 2034 };
    Object localObject3 = { 10, 10, 20, 10, 10, 10, 10 };
    ArrayList localArrayList2 = new ArrayList();
    for (int i2 = 0; i2 < localObject2.length; i2++)
    {
      int i3 = localObject3[i2];
      int i4 = localObject2[i2];
      localObject6 = new ReportColumn(localLocale);
      localObject7 = new ArrayList();
      localObject8 = ReportConsts.getColumnName(i4, localLocale);
      if ((i4 == 2032) || (i4 == 2034)) {
        localObject8 = (String)localObject8 + " (" + "USD" + ")";
      }
      ((ArrayList)localObject7).add(localObject8);
      if ((i4 == 2029) && (paramString1.equals("ACH File Report")))
      {
        ((ArrayList)localObject7).clear();
        i3 = 0;
      }
      ((ReportColumn)localObject6).setLabels((ArrayList)localObject7);
      ((ReportColumn)localObject6).setDataType("java.lang.String");
      ((ReportColumn)localObject6).setWidthAsPercent(i3);
      localArrayList2.add(localObject6);
    }
    int[] arrayOfInt1 = { 2038, 2040, 2041, 2042, 2032, 2034, 2043, 2035, 2036 };
    int[] arrayOfInt2 = { 15, 7, 10, 8, 12, 12, 7, 10, 19 };
    ArrayList localArrayList3 = new ArrayList();
    for (int i5 = 0; i5 < arrayOfInt1.length; i5++)
    {
      int i6 = arrayOfInt2[i5];
      int i7 = arrayOfInt1[i5];
      localObject9 = new ReportColumn(localLocale);
      localObject10 = new ArrayList();
      ((ArrayList)localObject10).add(ReportConsts.getColumnName(i7, localLocale));
      if (i7 == 2032) {
        ((ArrayList)localObject10).add(ReportConsts.getColumnName(2031, localLocale));
      }
      if (i7 == 2034) {
        ((ArrayList)localObject10).add(ReportConsts.getColumnName(2033, localLocale));
      }
      if (i7 == 2038) {
        ((ArrayList)localObject10).add(ReportConsts.getColumnName(2039, localLocale));
      }
      if (i7 == 2036) {
        ((ArrayList)localObject10).add(ReportConsts.getColumnName(2037, localLocale));
      }
      ((ReportColumn)localObject9).setLabels((ArrayList)localObject10);
      ((ReportColumn)localObject9).setDataType("java.lang.String");
      ((ReportColumn)localObject9).setWidthAsPercent(i6);
      localArrayList3.add(localObject9);
    }
    int[] arrayOfInt3 = { 2044, 2045, 2046, 2047, 2048, 2049, 2050 };
    int[] arrayOfInt4 = { 10, 14, 14, 20, 14, 10, 18 };
    ArrayList localArrayList4 = new ArrayList();
    for (int i8 = 0; i8 < arrayOfInt3.length; i8++)
    {
      int i9 = arrayOfInt4[i8];
      int i10 = arrayOfInt3[i8];
      localObject12 = new ReportColumn(localLocale);
      localObject13 = new ArrayList();
      ((ArrayList)localObject13).add(ReportConsts.getColumnName(i10, localLocale));
      ((ReportColumn)localObject12).setLabels((ArrayList)localObject13);
      ((ReportColumn)localObject12).setDataType("java.lang.String");
      ((ReportColumn)localObject12).setWidthAsPercent(i9);
      localArrayList4.add(localObject12);
    }
    ReportResult localReportResult1 = null;
    ReportResult localReportResult2 = null;
    ReportResult localReportResult3 = null;
    Object localObject12 = null;
    paramSummaryInfo.resetBusinessTotal();
    int i11 = 0;
    int i12 = 1;
    try
    {
      for (int i14 = 0; i14 < localArrayList1.size(); i14++)
      {
        localObject15 = (HashMap)localArrayList1.get(i14);
        localObject16 = (String)((HashMap)localObject15).get("ExportFile");
        str1 = (String)((HashMap)localObject15).get("BusinessName");
        String str8 = (String)((HashMap)localObject15).get("BusinessCustID");
        if (str8 != null)
        {
          str8 = str8.trim();
          if (str8.length() > 0)
          {
            localObject17 = new StringBuffer(str1);
            ((StringBuffer)localObject17).append(" (").append(str8).append(")");
            str1 = ((StringBuffer)localObject17).toString();
          }
        }
        Object localObject17 = (String)((HashMap)localObject15).get("UserName");
        if ((str2.length() > 0) && ((!str1.equalsIgnoreCase(str2)) || ((localObject16 != null) && (!((String)localObject16).equals(localObject1)))))
        {
          localObject18 = new StringBuffer(ReportConsts.getText(2018, localLocale));
          ((StringBuffer)localObject18).append(" ").append(str2);
          a(localReportResult2, localLocale, ((StringBuffer)localObject18).toString(), paramSummaryInfo, paramSummaryInfo.getBusinessTotalSummaryItem());
          paramSummaryInfo.resetBusinessTotal();
          str2 = null;
        }
        if (!str1.equalsIgnoreCase(str2))
        {
          str2 = str1;
          i11 = 1;
          localReportResult3 = null;
          i12 = 1;
        }
        if ((localObject16 != null) && (!((String)localObject16).equals(localObject1)))
        {
          localReportResult3 = null;
          localObject1 = localObject16;
          localReportResult1 = new ReportResult(localLocale);
          paramReportResult.addSubReport(localReportResult1);
          a(localReportResult1, localLocale, -1, localArrayList2.size());
          localObject18 = new ReportRow(localLocale);
          localObject19 = new ReportDataItems(localLocale);
          ((ReportRow)localObject18).setDataItems((ReportDataItems)localObject19);
          for (int i18 = 0; i18 < localArrayList2.size(); i18++) {
            localReportResult1.addColumn((ReportColumn)localArrayList2.get(i18));
          }
          for (i20 = 0; i20 < localArrayList2.size(); i20++)
          {
            localObject22 = "";
            Currency localCurrency;
            switch (i20)
            {
            case 0: 
              localObject22 = paramString3;
              break;
            case 1: 
              i22 = localObject2[i20];
              if ((i22 == 2029) && (paramString1.equals("ACH File Report"))) {
                localObject22 = "";
              } else {
                localObject22 = localObject17;
              }
              break;
            case 2: 
              localObject22 = localObject1;
              break;
            case 3: 
              if (paramString1.equals("ACH File Report")) {
                localObject22 = "" + paramSummaryInfo.getFileTotalSummaryItem().totalNumDebits;
              } else {
                localObject22 = (String)((HashMap)localObject15).get("FileTotalNumberDebits");
              }
              break;
            case 4: 
              if (paramString1.equals("ACH File Report")) {
                localObject22 = "" + paramSummaryInfo.getFileTotalSummaryItem().totalDebitsAmount;
              } else {
                localObject22 = (String)((HashMap)localObject15).get("FileTotalDebitAmt");
              }
              localCurrency = new Currency((String)localObject22, Locale.getDefault());
              localObject22 = localCurrency.toString();
              break;
            case 5: 
              if (paramString1.equals("ACH File Report")) {
                localObject22 = "" + paramSummaryInfo.getFileTotalSummaryItem().totalNumCredits;
              } else {
                localObject22 = (String)((HashMap)localObject15).get("FileTotalNumberCredits");
              }
              break;
            case 6: 
              if (paramString1.equals("ACH File Report")) {
                localObject22 = "" + paramSummaryInfo.getFileTotalSummaryItem().totalCreditsAmount;
              } else {
                localObject22 = (String)((HashMap)localObject15).get("FileTotalCreditAmt");
              }
              localCurrency = new Currency((String)localObject22, Locale.getDefault());
              localObject22 = localCurrency.toString();
              break;
            }
            a(((ReportDataItems)localObject19).add(), localObject22);
          }
          localReportResult1.addRow((ReportRow)localObject18);
          paramSummaryInfo.numRows += 1L;
          i12 = 1;
        }
        if ((str2 != null) && (i11 != 0))
        {
          i11 = 0;
          localReportResult2 = new ReportResult(localLocale);
          paramReportResult.addSubReport(localReportResult2);
          a(localReportResult2, localLocale, -1, 4);
          if ((paramString6.equals("Batch Summary")) || (paramString6.equals("Detail Entry")))
          {
            localObject18 = new ReportHeading(localLocale);
            ((ReportHeading)localObject18).setLabel(ReportConsts.getText(2056, localLocale) + " " + str2);
            localReportResult2.setHeading((ReportHeading)localObject18);
          }
        }
        localObject18 = new BigDecimal((String)((HashMap)localObject15).get("TotalDebits"));
        localObject19 = new BigDecimal((String)((HashMap)localObject15).get("TotalCredits"));
        int i19 = new Integer((String)((HashMap)localObject15).get("TotalNumberDebits")).intValue();
        int i20 = new Integer((String)((HashMap)localObject15).get("TotalNumberCredits")).intValue();
        localObject22 = new SummaryItem((BigDecimal)localObject18, i19, (BigDecimal)localObject19, i20);
        paramSummaryInfo.addBusinessTotal(localObject22);
        if ((paramString6.equals("Batch Summary")) || (paramString6.equals("Detail Entry")))
        {
          i22 = 0;
          if ((localReportResult3 == null) || (paramString6.equals("Detail Entry")) || ((str2 != null) && (i11 != 0)))
          {
            localReportResult3 = new ReportResult(localLocale);
            localReportResult2.addSubReport(localReportResult3);
            a(localReportResult3, localLocale, -1, localArrayList3.size());
            i22 = 1;
            i12 = 1;
          }
          ReportRow localReportRow = new ReportRow(localLocale);
          if (i12 % 2 == 0) {
            localReportRow.set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject23 = new ReportDataItems(localLocale);
          localReportRow.setDataItems((ReportDataItems)localObject23);
          for (int i25 = 0; i25 < localArrayList3.size(); i25++)
          {
            if (i22 != 0) {
              localReportResult3.addColumn((ReportColumn)localArrayList3.get(i25));
            }
            localObject25 = "" + i25;
            switch (i25)
            {
            case 0: 
              localObject25 = (String)((HashMap)localObject15).get("CompName") + ", " + (String)((HashMap)localObject15).get("CompID");
              break;
            case 1: 
              localObject25 = (String)((HashMap)localObject15).get("SECC");
              break;
            case 2: 
              localObject25 = (String)((HashMap)localObject15).get("EffectiveDate");
              break;
            case 3: 
              localObject25 = (String)((HashMap)localObject15).get("RecordCount");
              break;
            case 4: 
              localObject25 = (String)((HashMap)localObject15).get("TotalDebitsDisplay") + ",  " + (String)((HashMap)localObject15).get("TotalNumberDebits");
              break;
            case 5: 
              localObject25 = (String)((HashMap)localObject15).get("TotalCreditsDisplay") + ",  " + (String)((HashMap)localObject15).get("TotalNumberCredits");
              break;
            case 6: 
              localObject25 = (String)((HashMap)localObject15).get("BatchNum");
              break;
            case 7: 
              localObject25 = (String)((HashMap)localObject15).get("BatchStatus");
              break;
            case 8: 
              localObject25 = (String)((HashMap)localObject15).get("LogID") + ", " + (String)((HashMap)localObject15).get("ID");
            }
            a(((ReportDataItems)localObject23).add(), localObject25);
          }
          localReportResult3.addRow(localReportRow);
          i12++;
          paramSummaryInfo.numRows += 1L;
          if (paramString6.equals("Detail Entry"))
          {
            ArrayList localArrayList5 = (ArrayList)((HashMap)localObject15).get("Entries");
            localObject12 = new ReportResult(localLocale);
            localReportResult2.addSubReport((ReportResult)localObject12);
            ((ReportResult)localObject12).setHeading(null);
            a((ReportResult)localObject12, localLocale, -1, localArrayList4.size());
            for (int i26 = 0; i26 < localArrayList4.size(); i26++) {
              ((ReportResult)localObject12).addColumn((ReportColumn)localArrayList4.get(i26));
            }
            for (i26 = 0; i26 < localArrayList5.size(); i26++)
            {
              localReportRow = new ReportRow(localLocale);
              if (i26 % 2 == 1) {
                localReportRow.set("CELLBACKGROUND", "reportDataShaded");
              }
              localObject23 = new ReportDataItems(localLocale);
              localReportRow.setDataItems((ReportDataItems)localObject23);
              localObject27 = (HashMap)localArrayList5.get(i26);
              for (i27 = 0; i27 < localArrayList4.size(); i27++)
              {
                String str10 = "" + i27;
                switch (i27)
                {
                case 0: 
                  str10 = (String)((HashMap)localObject27).get("TransCode");
                  break;
                case 1: 
                  str10 = (String)((HashMap)localObject27).get("RDFI_ID");
                  break;
                case 2: 
                  str10 = (String)((HashMap)localObject27).get("DFIAcctNum");
                  break;
                case 3: 
                  str10 = (String)((HashMap)localObject27).get("RecCoName");
                  break;
                case 4: 
                  str10 = (String)((HashMap)localObject27).get("IdentNum");
                  break;
                case 5: 
                  str10 = (String)((HashMap)localObject27).get("Amount");
                  str10 = jdField_for(str10, localLocale);
                  break;
                case 6: 
                default: 
                  str10 = (String)((HashMap)localObject27).get("TraceNum");
                }
                a(((ReportDataItems)localObject23).add(), str10);
              }
              ((ReportResult)localObject12).addRow(localReportRow);
              paramSummaryInfo.numRows += 1L;
            }
          }
        }
      }
      StringBuffer localStringBuffer = new StringBuffer(ReportConsts.getText(2018, localLocale));
      localStringBuffer.append(" ").append(str2);
      a(localReportResult2, localLocale, localStringBuffer.toString(), paramSummaryInfo, paramSummaryInfo.getBusinessTotalSummaryItem());
    }
    catch (RptException localRptException)
    {
      localRptException.printStackTrace();
    }
  }
  
  private static Timestamp jdField_byte(String paramString)
    throws ParseException
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      Date localDate = localDateFormat.parse(paramString);
      return new Timestamp(localDate.getTime());
    }
    return null;
  }
  
  private static ReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString)
    throws AdminException
  {
    String str1 = "ReportTransactionAdapter.getWireReportData";
    ReportResult localReportResult = null;
    localReportResult = new ReportResult(Locale.getDefault());
    try
    {
      a(paramSecureUser, paramReportCriteria, localReportResult, paramString);
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      localException.printStackTrace();
      String str2 = "Unable to generate the report.";
      throw new AdminException(str1, 19, str2);
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        String str3 = "Unable to generate the report.";
        throw new AdminException(str1, 19, str3);
      }
    }
    return localReportResult;
  }
  
  private static void a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ReportResult paramReportResult, String paramString)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.obtainWireRecordsFromCriteria";
    ReportHeading localReportHeading = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    int i1 = 0;
    int i2 = -1;
    String str12 = null;
    Locale localLocale = paramReportCriteria.getLocale();
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    str12 = localProperties2.getProperty("MAXDISPLAYSIZE");
    if ((str12 != null) && (!str12.equals(""))) {
      i2 = Integer.parseInt(str12.trim());
    }
    int i3 = 0;
    try
    {
      i3 = Integer.parseInt(Profile.getSearchCriteria(localProperties1, "Affiliate Bank", "0"));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i3 = 0;
    }
    jdField_do(paramReportCriteria, localProperties1);
    str2 = a(paramReportCriteria, localProperties1);
    str3 = jdField_if(paramReportCriteria, localProperties1);
    str4 = a(paramReportCriteria, localProperties1, paramString);
    str6 = localProperties1.getProperty("StartDate");
    str7 = localProperties1.getProperty("EndDate");
    str5 = localProperties1.getProperty("WireServiceType");
    str9 = localProperties1.getProperty("Status");
    str10 = localProperties1.getProperty("FromAmount");
    str11 = localProperties1.getProperty("ToAmount");
    str8 = localProperties1.getProperty("ReportBy");
    if (str8 == null) {
      str8 = "ReportByStatus";
    }
    if (str8.equals("ReportByAction")) {
      paramReportCriteria.setDisplayValue("ReportBy", ReportConsts.getText(638, localLocale));
    } else {
      paramReportCriteria.setDisplayValue("ReportBy", ReportConsts.getText(639, localLocale));
    }
    SummaryInfo localSummaryInfo = new SummaryInfo();
    localSummaryInfo.setLocale(localLocale);
    localSummaryInfo.setUser(paramSecureUser);
    paramReportResult.init(paramReportCriteria);
    localReportHeading = new ReportHeading(Locale.getDefault());
    localReportHeading.setLabel(ReportConsts.getText(630, paramReportCriteria.getLocale()));
    paramReportResult.setHeading(localReportHeading);
    a(paramReportCriteria, paramString, i3, str2, str3, str4, str5, str6, str7, str10, str11, str8, str9, paramReportResult, i2, localSummaryInfo);
    String str13 = ReportConsts.getText(634, Locale.getDefault());
    a(paramReportResult, str13, localSummaryInfo.getGrandTotalSummaryItem());
  }
  
  private static void jdField_do(ReportCriteria paramReportCriteria, Properties paramProperties)
  {
    String str = paramProperties.getProperty("Affiliate Bank");
    if ((str == null) || (str.length() == 0)) {
      str = "AllAffiliateBanks";
    }
    if (str.equals("AllAffiliateBanks")) {
      paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, paramReportCriteria.getLocale()));
    } else {
      try
      {
        Integer localInteger = new Integer(str);
        AffiliateBank localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(null, localInteger.intValue());
        if (localAffiliateBank != null) {
          paramReportCriteria.setDisplayValue("Affiliate Bank", localAffiliateBank.getAffiliateBankName());
        }
      }
      catch (ProfileException localProfileException) {}
    }
  }
  
  private static String a(ReportCriteria paramReportCriteria, Properties paramProperties)
  {
    String str1 = paramProperties.getProperty("Business");
    String str2 = null;
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "AllBusinesses";
    }
    if (str1.equals("AllBusinessesAndConsumers")) {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10014, paramReportCriteria.getLocale()));
    } else if (str1.equals("AllBusinesses")) {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramReportCriteria.getLocale()));
    } else if (str1.equals("AllConsumers")) {
      paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10016, paramReportCriteria.getLocale()));
    } else {
      try
      {
        Integer localInteger = new Integer(str1);
        str2 = str1;
        Business localBusiness = BusinessAdapter.getBusiness(localInteger.intValue());
        if (localBusiness != null)
        {
          StringBuffer localStringBuffer = new StringBuffer(localBusiness.getBusinessName());
          String str3 = localBusiness.getCustId();
          if (str3 != null)
          {
            str3 = str3.trim();
            if (str3.length() > 0) {
              localStringBuffer.append(" (").append(str3).append(")");
            }
          }
          paramReportCriteria.setDisplayValue("Business", localStringBuffer.toString());
        }
      }
      catch (ProfileException localProfileException) {}
    }
    return str2;
  }
  
  private static String jdField_if(ReportCriteria paramReportCriteria, Properties paramProperties)
  {
    String str1 = (String)paramProperties.get("User");
    String str2 = null;
    if (str1 == null) {
      str1 = "AllUsers";
    }
    if (str1.equals("AllUsers"))
    {
      paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, paramReportCriteria.getLocale()));
    }
    else
    {
      str2 = str1;
      try
      {
        Integer localInteger = new Integer(str2);
        User localUser = CustomerAdapter.getUserById(localInteger.intValue());
        if (localUser != null) {
          paramReportCriteria.setDisplayValue("User", localUser.getName());
        }
      }
      catch (ProfileException localProfileException) {}
    }
    return str2;
  }
  
  private static String a(ReportCriteria paramReportCriteria, Properties paramProperties, String paramString)
  {
    String str1 = (String)paramProperties.get("Account");
    if ((str1 == null) || (str1.equals("")) || (jdField_else(str1)))
    {
      paramReportCriteria.setDisplayValue("Account", ReportConsts.getText(10024, paramReportCriteria.getLocale()));
      return "AllAccounts";
    }
    StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, ",");
    String str2;
    if (localStringTokenizer1.countTokens() == 1)
    {
      StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), ":");
      str2 = "AUDIT_LOG.FROM_ACCT_ID='" + localStringTokenizer2.nextToken() + "'" + " and ";
    }
    else
    {
      i1 = 1;
      str2 = "(";
      while (localStringTokenizer1.hasMoreTokens())
      {
        StringTokenizer localStringTokenizer3 = new StringTokenizer(localStringTokenizer1.nextToken(), ":");
        if (i1 != 0)
        {
          str2 = str2 + "AUDIT_LOG.FROM_ACCT_ID" + "=" + "'" + localStringTokenizer3.nextToken() + "'";
          i1 = 0;
        }
        else
        {
          str2 = str2 + " or " + "AUDIT_LOG.FROM_ACCT_ID" + "=" + "'" + localStringTokenizer3.nextToken() + "'";
        }
      }
      str2 = str2 + ")" + " and ";
    }
    localStringTokenizer1 = new StringTokenizer(str1, ",");
    int i1 = localStringTokenizer1.countTokens();
    int i2 = 1;
    StringBuffer localStringBuffer = new StringBuffer();
    while (localStringTokenizer1.hasMoreTokens())
    {
      StringTokenizer localStringTokenizer4 = new StringTokenizer(localStringTokenizer1.nextToken(), ":");
      int i3 = localStringTokenizer4.countTokens();
      String str3 = localStringTokenizer4.nextToken();
      String str4 = localStringTokenizer4.nextToken();
      String str5 = null;
      String str6 = null;
      if (i3 > 2)
      {
        str5 = localStringTokenizer4.nextToken();
        str6 = localStringTokenizer4.nextToken();
        try
        {
          localStringBuffer.append(str4 + " : " + AccountUtil.buildAccountDisplayText(str3, paramReportCriteria.getLocale()) + " - " + str5 + " - " + str6);
        }
        catch (UtilException localUtilException)
        {
          DebugLog.throwing("Error while constructing account display string for account id '" + str3 + "' and report type '" + paramString + "'.", localUtilException);
          localStringBuffer.append(str4 + " : " + str3 + " - " + str5 + " - " + str6);
        }
      }
      if (i2 != i1) {
        localStringBuffer.append("&&");
      }
      i2++;
    }
    paramReportCriteria.setDisplayValue("Account", localStringBuffer.toString());
    return str2;
  }
  
  private static void a(ReportCriteria paramReportCriteria, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, ReportResult paramReportResult, int paramInt2, SummaryInfo paramSummaryInfo)
    throws Exception
  {
    String str1 = "ReportAuditAdapter.getWireEntriesForReport";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("SELECT AUDIT_LOG.LOG_DATE, AUDIT_LOG.TRAN_ID, AUDIT_LOG.STATE, AUDIT_LOG.AGENT_ID, AUDIT_LOG.USER_ID, AUDIT_LOG.BUSINESS_ID, AUDIT_LOG.TRAN_TYPE, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.AMOUNT, wire.OrigAmount, wire.WireDest, wire.RecSrvrTID, wire.SrvrTID, wire.ConfirmNum, wire.ConfirmNum2, wire.DateToPost, wire.OrigCurrency, customer.user_name, business.business_name, accounts.nickname, accounts.currency_type, accounts.routing_num, business.business_CIF, wire.DatePosted, wire.DateCreate FROM BPW_WireInfo wire, customer, AUDIT_LOG, business, accounts WHERE ");
    Account localAccount = null;
    String str2 = null;
    ReportResult localReportResult1 = null;
    ReportResult localReportResult2 = null;
    ReportResult localReportResult3 = null;
    ReportResult localReportResult4 = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    String str3 = null;
    BigDecimal localBigDecimal = null;
    int i1 = 0;
    ArrayList localArrayList = new ArrayList();
    try
    {
      if (paramInt1 != 0) {
        localStringBuffer.append("business.affiliate_bank_id=? and ");
      }
      if ((paramString2 != null) && (!paramString2.equalsIgnoreCase("AllBusinesses"))) {
        localStringBuffer.append("AUDIT_LOG.BUSINESS_ID=? and ");
      }
      if ((paramString3 != null) && (!paramString3.equalsIgnoreCase("AllUsers"))) {
        localStringBuffer.append("AUDIT_LOG.USER_ID=? and ");
      }
      try
      {
        localTimestamp1 = jdField_byte(paramString6);
        localTimestamp2 = jdField_byte(paramString7);
      }
      catch (ParseException localParseException1)
      {
        localParseException1.printStackTrace();
        DebugLog.log("WARNING: Invalid format for date search criteria.");
      }
      if (localTimestamp1 != null) {
        localStringBuffer.append("AUDIT_LOG.LOG_DATE>=? and ");
      }
      if (localTimestamp2 != null) {
        localStringBuffer.append("AUDIT_LOG.LOG_DATE<=? and ");
      }
      if ((paramString4 != null) && (!paramString4.equalsIgnoreCase("AllAccounts"))) {
        localStringBuffer.append(paramString4);
      }
      if ((paramString5 != null) && (!paramString5.equalsIgnoreCase("AllWireServiceTypes"))) {
        localStringBuffer.append("wire.WireDest=? and ");
      }
      if ((paramString8 != null) && (paramString8.length() > 0)) {
        localStringBuffer.append("AUDIT_LOG.AMOUNT>=? and ");
      }
      if ((paramString9 != null) && (paramString9.length() > 0)) {
        localStringBuffer.append("AUDIT_LOG.AMOUNT<=? and ");
      }
      if (paramString1.equals("Wire Report"))
      {
        if (paramString10.equals("ReportByStatus"))
        {
          if ((paramString11 == null) || (paramString11.equals("AllStatuses")))
          {
            localStringBuffer.append("AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ");
          }
          else
          {
            localStringBuffer.append("AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ");
            localStringBuffer.append("wire.Status=? and ");
            i1 = 1;
          }
        }
        else if ((paramString11 != null) && (!paramString11.equalsIgnoreCase("AllStatuses")))
        {
          localStringBuffer.append("AUDIT_LOG.STATE=? and ");
          i1 = 1;
        }
      }
      else {
        localStringBuffer.append("(AUDIT_LOG.STATE='BACKENDFAILED' OR AUDIT_LOG.STATE='RELEASEFAILED' OR AUDIT_LOG.STATE='FUNDSFAILEDON' OR AUDIT_LOG.STATE='FUNDSFAILEDON_NOTIF' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED_NOTIF') and ");
      }
      localStringBuffer.append("(AUDIT_LOG.BUSINESS_ID = business.business_id) AND (wire.ExtId = AUDIT_LOG.TRAN_ID)  AND (AUDIT_LOG.USER_ID_INT = customer.directory_id) AND (accounts.directory_id = AUDIT_LOG.BUSINESS_ID) AND (accounts.account_id = AUDIT_LOG.FROM_ACCT_ID OR accounts.account_id = AUDIT_LOG.TO_ACCT_ID)");
      localStringBuffer.append(" Order By");
      localStringBuffer.append(" business.business_name ASC");
      localStringBuffer.append(", AUDIT_LOG.FROM_ACCT_ID ASC");
      localStringBuffer.append(", AUDIT_LOG.STATE ASC");
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      Object localObject1;
      Object localObject2;
      if ((localReportSortCriteria != null) && (localReportSortCriteria.size() > 0))
      {
        localReportSortCriteria.setSortedBy("ORDINAL");
        Iterator localIterator = localReportSortCriteria.iterator();
        while (localIterator.hasNext())
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
          localObject1 = localReportSortCriterion.getName();
          if ((localObject1 != null) && (((String)localObject1).length() != 0))
          {
            if (((String)localObject1).equalsIgnoreCase("Date"))
            {
              localObject1 = ", AUDIT_LOG.LOG_DATE";
            }
            else if (((String)localObject1).equalsIgnoreCase("User"))
            {
              localObject1 = ", AUDIT_LOG.USER_ID";
            }
            else
            {
              if (!((String)localObject1).equalsIgnoreCase("WireServiceType")) {
                continue;
              }
              localObject1 = ", wire.WireDest";
            }
            localObject2 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
            localStringBuffer.append((String)localObject1);
            localStringBuffer.append(" ");
            localStringBuffer.append((String)localObject2);
          }
        }
      }
      localConnection = DBUtil.getConnection(bd, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      if (paramInt2 != -1) {
        localPreparedStatement.setMaxRows(paramInt2 + 1);
      }
      int i2 = 1;
      if (paramInt1 != 0) {
        localPreparedStatement.setInt(i2++, paramInt1);
      }
      if ((paramString2 != null) && (!paramString2.equalsIgnoreCase("AllBusinesses"))) {
        localPreparedStatement.setInt(i2++, Integer.parseInt(paramString2));
      }
      if ((paramString3 != null) && (!paramString3.equalsIgnoreCase("AllUsers"))) {
        localPreparedStatement.setString(i2++, paramString3);
      }
      if (localTimestamp1 != null) {
        localPreparedStatement.setTimestamp(i2++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        localPreparedStatement.setTimestamp(i2++, localTimestamp2);
      }
      if ((paramString5 != null) && (!paramString5.equalsIgnoreCase("AllWireServiceTypes"))) {
        localPreparedStatement.setString(i2++, paramString5.toUpperCase());
      }
      if ((paramString8 != null) && (paramString8.length() > 0)) {
        localPreparedStatement.setBigDecimal(i2++, new BigDecimal(paramString8));
      }
      if ((paramString9 != null) && (paramString9.length() > 0)) {
        localPreparedStatement.setBigDecimal(i2++, new BigDecimal(paramString9));
      }
      if ((paramString1.equals("Wire Report")) && (i1 != 0)) {
        localPreparedStatement.setString(i2++, paramString11);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      paramSummaryInfo.sameBusiness = false;
      paramSummaryInfo.sameAccount = false;
      int i3 = 1;
      while (localResultSet.next())
      {
        if ((paramInt2 != -1) && (paramInt2 == paramSummaryInfo.numRows))
        {
          localObject1 = new HashMap();
          ((HashMap)localObject1).put("TRUNCATED", new Integer(paramInt2));
          paramReportResult.setProperties((HashMap)localObject1);
          break;
        }
        localObject1 = new SimpleDateFormat("yyyyMMdd");
        localObject2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat localSimpleDateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        paramSummaryInfo.currBusiness = localResultSet.getString(6);
        paramSummaryInfo.currFromAccount = localResultSet.getString(8);
        str2 = localResultSet.getString(12);
        paramSummaryInfo.currStatus = localResultSet.getString(3);
        if (!paramSummaryInfo.currBusiness.equals(paramSummaryInfo.prevBusiness))
        {
          a(localReportResult1, localReportResult2, localReportResult4, paramSummaryInfo, paramReportCriteria, paramString5, paramInt1, paramString6, paramString7, paramString3, paramString11);
          if (paramSummaryInfo.prevBusiness != null) {
            localArrayList.add(paramSummaryInfo.prevBusiness);
          }
          paramSummaryInfo.prevBusiness = paramSummaryInfo.currBusiness;
          paramSummaryInfo.sameBusiness = false;
          localObject3 = localResultSet.getString(20) + " (" + localResultSet.getString(24) + ")";
          localReportResult1 = a(paramReportResult, paramReportCriteria, (String)localObject3);
          i3 = 1;
        }
        else
        {
          paramSummaryInfo.sameBusiness = true;
        }
        if (!paramSummaryInfo.currFromAccount.equals(paramSummaryInfo.prevFromAcct))
        {
          localAccount = jdField_if(paramSummaryInfo.currFromAccount, paramSummaryInfo.currBusiness);
          a(localReportResult2, localReportResult4, paramSummaryInfo, paramReportCriteria);
          paramSummaryInfo.prevStatus = null;
          paramSummaryInfo.sameAccount = false;
          paramSummaryInfo.prevFromAcct = paramSummaryInfo.currFromAccount;
          localObject3 = localAccount.getDisplayText();
          localReportResult2 = jdField_do(localReportResult1, paramReportCriteria, (String)localObject3);
          localReportResult3 = new ReportResult(Locale.getDefault());
          localReportResult2.addSubReport(localReportResult3);
          i3 = 1;
        }
        else
        {
          paramSummaryInfo.sameAccount = true;
        }
        if (!paramSummaryInfo.currStatus.equals(paramSummaryInfo.prevStatus))
        {
          a(localReportResult4, paramSummaryInfo, paramReportCriteria);
          if (paramSummaryInfo.sameAccount == true) {
            a(paramSummaryInfo, true);
          }
          jdField_if(paramSummaryInfo, true);
          paramSummaryInfo.prevStatus = paramSummaryInfo.currStatus;
          localObject3 = "Wire Status: " + jdField_int(paramSummaryInfo.currStatus, paramReportCriteria.getLocale());
          localReportResult4 = jdField_if(localReportResult2, paramReportCriteria, (String)localObject3);
          localReportResult3 = new ReportResult(Locale.getDefault());
          localReportResult4.addSubReport(localReportResult3);
          a(localReportResult3, paramReportCriteria);
          i3 = 1;
        }
        Object localObject3 = new ReportDataItems(Locale.getDefault());
        str3 = localResultSet.getString(1);
        if (jdField_int(str3)) {
          ((ReportDataItems)localObject3).add().setData("");
        } else {
          ((ReportDataItems)localObject3).add().setData(str3.substring(0, 19));
        }
        str3 = localResultSet.getString(26);
        if (jdField_int(str3))
        {
          ((ReportDataItems)localObject3).add().setData("");
        }
        else
        {
          str3 = str3.trim();
          if (str3.length() < 19) {
            ((ReportDataItems)localObject3).add().setData("");
          } else {
            try
            {
              ((ReportDataItems)localObject3).add().setData(localSimpleDateFormat4.format(localSimpleDateFormat3.parse(str3)));
            }
            catch (Exception localException2)
            {
              ((ReportDataItems)localObject3).add().setData(str3.substring(0, 19));
            }
          }
        }
        str3 = localResultSet.getString(17);
        Date localDate;
        if (jdField_int(str3))
        {
          ((ReportDataItems)localObject3).add().setData("");
        }
        else
        {
          localDate = ((SimpleDateFormat)localObject1).parse(str3.substring(0, 8));
          localObject5 = ((SimpleDateFormat)localObject2).format(localDate);
          ((ReportDataItems)localObject3).add().setData(localObject5);
        }
        str3 = localResultSet.getString(25);
        if (jdField_int(str3))
        {
          ((ReportDataItems)localObject3).add().setData("");
        }
        else
        {
          localDate = localSimpleDateFormat1.parse(str3.substring(0, 14));
          localObject5 = localSimpleDateFormat2.format(localDate);
          ((ReportDataItems)localObject3).add().setData(localObject5);
        }
        a(((ReportDataItems)localObject3).add(), localResultSet.getString(19));
        str3 = localResultSet.getString(8);
        try
        {
          str3 = AccountUtil.buildAccountDisplayText(str3, paramReportCriteria.getLocale());
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string for account id '" + str3 + "' and report type '" + paramString1 + "'.", localUtilException1);
        }
        a(((ReportDataItems)localObject3).add(), str3);
        str3 = localResultSet.getString(9);
        try
        {
          str3 = AccountUtil.buildAccountDisplayText(str3, paramReportCriteria.getLocale());
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string for account id '" + str3 + "' and report type '" + paramString1 + "'.", localUtilException2);
        }
        a(((ReportDataItems)localObject3).add(), str3);
        localBigDecimal = localResultSet.getBigDecimal(10);
        if (localBigDecimal == null)
        {
          ((ReportDataItems)localObject3).add().setData("");
          localBigDecimal = new BigDecimal(0.0D);
        }
        else
        {
          localObject4 = new Currency(localBigDecimal, Locale.getDefault());
          ((ReportDataItems)localObject3).add().setData(((Currency)localObject4).getCurrencyStringNoSymbol());
        }
        a(((ReportDataItems)localObject3).add(), localResultSet.getString(12));
        Object localObject4 = str3;
        str3 = localResultSet.getString(2) + ", " + localResultSet.getString(14) + ", " + localResultSet.getString(15) + ", " + localResultSet.getString(16);
        a(((ReportDataItems)localObject3).add(), str3);
        a(((ReportDataItems)localObject3).add(), localResultSet.getString(18));
        str3 = localResultSet.getString(11);
        if (jdField_int(str3))
        {
          ((ReportDataItems)localObject3).add().setData("");
        }
        else if ("INTERNATIONAL".equalsIgnoreCase((String)localObject4))
        {
          localObject5 = new Currency(str3, Locale.getDefault());
          ((ReportDataItems)localObject3).add().setData(((Currency)localObject5).getCurrencyStringNoSymbol());
        }
        else
        {
          ((ReportDataItems)localObject3).add().setData("");
        }
        a(((ReportDataItems)localObject3).add(), localResultSet.getString(13));
        Object localObject5 = new ReportRow(Locale.getDefault());
        if (i3 % 2 == 0) {
          ((ReportRow)localObject5).set("CELLBACKGROUND", "reportDataShaded");
        }
        ((ReportRow)localObject5).setDataItems((ReportDataItems)localObject3);
        localReportResult3.addRow((ReportRow)localObject5);
        i3++;
        if ("DRAWDOWN".equals(str2)) {
          paramSummaryInfo.addStatusTotal(new SummaryItem(null, 0L, localBigDecimal, 1L));
        } else {
          paramSummaryInfo.addStatusTotal(new SummaryItem(localBigDecimal, 1L));
        }
      }
      paramSummaryInfo.bLastBusiness = true;
      localArrayList.add(paramSummaryInfo.currBusiness);
      a(localReportResult1, localReportResult2, localReportResult4, paramSummaryInfo, paramReportCriteria, paramString5, paramInt1, paramString6, paramString7, paramString3, paramString11);
      if ((paramString5 == null) || (paramString5.equals("AllWireServiceTypes")) || (paramString5.equals("HOST"))) {
        a(paramReportCriteria, paramInt1, localArrayList, paramString6, paramString7, paramString3, paramSummaryInfo, paramReportResult, paramString11, paramString2);
      }
    }
    catch (ParseException localParseException2)
    {
      localParseException2.printStackTrace();
      throw new Exception("Invalid format for date search criteria.");
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw localException1;
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void a(ReportResult paramReportResult1, ReportResult paramReportResult2, ReportResult paramReportResult3, SummaryInfo paramSummaryInfo, ReportCriteria paramReportCriteria, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5)
    throws Exception
  {
    if ((paramSummaryInfo.bLastBusiness) || (paramSummaryInfo.prevBusiness != null))
    {
      a(paramReportResult2, paramReportResult3, paramSummaryInfo, paramReportCriteria);
      paramSummaryInfo.addBusinessTotal(paramSummaryInfo.getAccountTotalSummary());
      if ((paramSummaryInfo.prevBusiness != null) && ((paramString1 == null) || (paramString1.equals("AllWireServiceTypes")) || (paramString1.equals("HOST")))) {
        a(paramReportCriteria, paramInt, paramSummaryInfo.prevBusiness, paramString2, paramString3, paramString4, paramSummaryInfo, paramReportResult1, paramString5, null, null);
      }
      String str = ReportConsts.getText(633, Locale.getDefault());
      a(paramReportResult1, str, paramSummaryInfo.getBusinessTotalSummaryItem());
      paramSummaryInfo.addGrandTotal(paramSummaryInfo.getBusinessTotalSummary());
    }
    if (!paramSummaryInfo.bLastBusiness)
    {
      paramSummaryInfo.resetBusinessTotal();
      paramSummaryInfo.resetAccountTotal();
      paramSummaryInfo.prevFromAcct = null;
      paramSummaryInfo.resetStatusTotal();
      paramSummaryInfo.prevStatus = null;
    }
  }
  
  private static void a(ReportResult paramReportResult1, ReportResult paramReportResult2, SummaryInfo paramSummaryInfo, ReportCriteria paramReportCriteria)
    throws RptException
  {
    if ((paramSummaryInfo.bLastBusiness) || (paramSummaryInfo.prevFromAcct != null))
    {
      paramSummaryInfo.addAccountTotal(paramSummaryInfo.getStatusTotalSummary());
      a(paramReportResult2, paramSummaryInfo, paramReportCriteria);
      jdField_if(paramSummaryInfo, true);
      String str = ReportConsts.getText(632, Locale.getDefault());
      a(paramReportResult1, str, paramSummaryInfo.getAccountTotalSummaryItem());
    }
    if ((paramSummaryInfo.sameBusiness) && (!paramSummaryInfo.bLastBusiness)) {
      paramSummaryInfo.addBusinessTotal(paramSummaryInfo.getAccountTotalSummary());
    }
    if (!paramSummaryInfo.bLastBusiness) {
      paramSummaryInfo.resetAccountTotal();
    }
  }
  
  private static void a(ReportResult paramReportResult, SummaryInfo paramSummaryInfo, ReportCriteria paramReportCriteria)
    throws RptException
  {
    if ((paramSummaryInfo.bLastBusiness) || (paramSummaryInfo.prevStatus != null))
    {
      String str = jdField_int(paramSummaryInfo.prevStatus, paramReportCriteria.getLocale()) + " " + ReportConsts.getText(640, Locale.getDefault());
      a(paramReportResult, str, paramSummaryInfo.getStatusTotalSummaryItem());
    }
  }
  
  private static void a(SummaryInfo paramSummaryInfo, boolean paramBoolean)
  {
    paramSummaryInfo.addAccountTotal(paramSummaryInfo.getStatusTotalSummary());
  }
  
  private static void jdField_if(SummaryInfo paramSummaryInfo, boolean paramBoolean)
  {
    paramSummaryInfo.resetStatusTotal();
  }
  
  private static void jdField_int(ReportResult paramReportResult)
    throws Exception
  {
    a(paramReportResult, 600, null, 8, null);
    a(paramReportResult, 622, null, 8, null);
    a(paramReportResult, 1888, null, 7, null);
    a(paramReportResult, 1886, null, 8, null);
    a(paramReportResult, 604, null, 6, null);
    a(paramReportResult, 605, null, 7, null);
    a(paramReportResult, 606, null, 7, null);
    a(paramReportResult, 602, null, 7, null);
    a(paramReportResult, 607, null, 7, null);
    ReportColumn localReportColumn = new ReportColumn(Locale.getDefault());
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(613, Locale.getDefault()));
    localArrayList.add(ReportConsts.getColumnName(614, Locale.getDefault()));
    localArrayList.add(ReportConsts.getColumnName(615, Locale.getDefault()));
    localArrayList.add(ReportConsts.getColumnName(616, Locale.getDefault()));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
    a(paramReportResult, 611, null, 5, null);
    a(paramReportResult, 612, null, 7, null);
    a(paramReportResult, 608, null, 7, null);
  }
  
  private static ReportResult a(ReportResult paramReportResult, ReportCriteria paramReportCriteria, String paramString)
    throws Exception
  {
    ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(paramReportCriteria.getLocale());
    localReportHeading.setLabel(paramString);
    localReportHeading.setJustification("LEFT");
    localReportResult.setHeading(localReportHeading);
    return localReportResult;
  }
  
  private static ReportResult jdField_do(ReportResult paramReportResult, ReportCriteria paramReportCriteria, String paramString)
    throws Exception
  {
    ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(paramReportCriteria.getLocale());
    localReportHeading.setLabel(paramString);
    localReportHeading.setJustification("LEFT");
    localReportResult.setHeading(localReportHeading);
    return localReportResult;
  }
  
  private static void a(ReportResult paramReportResult, ReportCriteria paramReportCriteria)
    throws Exception
  {
    ReportHeading localReportHeading = new ReportHeading(Locale.getDefault());
    localReportHeading.setLabel(ReportConsts.getText(631, Locale.getDefault()));
    localReportHeading.setJustification("LEFT");
    paramReportResult.setHeading(localReportHeading);
    a(paramReportResult, Locale.getDefault(), -1, 13);
    jdField_int(paramReportResult);
  }
  
  private static String jdField_int(String paramString, Locale paramLocale)
  {
    String str = null;
    if (jdField_int(paramString)) {
      return "-";
    }
    try
    {
      str = ResourceUtil.getString(paramString, "com.ffusion.beans.reporting.wire_status", paramLocale);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "-";
    }
    return str;
  }
  
  private static void a(ReportResult paramReportResult, String paramString, SummaryItem paramSummaryItem)
    throws RptException
  {
    if ((paramReportResult == null) || (paramSummaryItem == null)) {
      return;
    }
    int i1 = new Long(paramSummaryItem.totalNumDebits).intValue();
    int i2 = new Long(paramSummaryItem.totalNumCredits).intValue();
    int i3 = i1 + i2;
    Locale localLocale = paramReportResult.getLocale();
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, localLocale, -1, 5);
    if (paramString != null)
    {
      localObject = new ReportHeading(localLocale);
      ((ReportHeading)localObject).setLabel(paramString);
      localReportResult.setSectionHeading((ReportHeading)localObject);
    }
    a(localReportResult, 617, "java.lang.String", 20, null);
    a(localReportResult, 2015, "java.lang.String", 20, null);
    a(localReportResult, 2002, "java.lang.String", 20, null);
    a(localReportResult, 2016, "java.lang.String", 20, null);
    a(localReportResult, 2003, "java.lang.String", 20, null);
    Object localObject = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    ((ReportRow)localObject).setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + i3);
    localReportDataItems.add().setData("" + i1);
    Currency localCurrency = new Currency(paramSummaryItem.totalDebitsAmount, paramSummaryItem.getCurrencyCode(), localLocale);
    localReportDataItems.add().setData(localCurrency.toString());
    localReportDataItems.add().setData("" + i2);
    localCurrency = new Currency(paramSummaryItem.totalCreditsAmount, paramSummaryItem.getCurrencyCode(), localLocale);
    localReportDataItems.add().setData(localCurrency.toString());
    localReportResult.addRow((ReportRow)localObject);
  }
  
  private static Account jdField_if(String paramString1, String paramString2)
  {
    Account localAccount1 = null;
    Account localAccount2 = new Account();
    Accounts localAccounts = null;
    localAccount2.setID(paramString1);
    try
    {
      localAccounts = AccountAdapter.getAccounts(localAccount2, Integer.parseInt(paramString2));
      localAccount1 = localAccounts.getByID(paramString1);
    }
    catch (Exception localException)
    {
      localAccount1 = null;
    }
    if (localAccount1 == null)
    {
      localAccount1 = new Account();
      localAccount1.setID(paramString1);
      localAccount1.setType(0);
    }
    return localAccount1;
  }
  
  private static ReportResult jdField_if(ReportResult paramReportResult, ReportCriteria paramReportCriteria, String paramString)
    throws Exception
  {
    ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(paramReportCriteria.getLocale());
    localReportHeading.setLabel(paramString);
    localReportHeading.setJustification("LEFT");
    localReportResult.setHeading(localReportHeading);
    return localReportResult;
  }
  
  private static String a(String paramString, Locale paramLocale)
  {
    String str = null;
    if (jdField_int(paramString))
    {
      DebugLog.log("WARNING: Transfer Status is null or empty");
      return "-";
    }
    try
    {
      str = ResourceUtil.getString(paramString, "com.ffusion.beans.reporting.transfer_status", paramLocale);
    }
    catch (Exception localException)
    {
      DebugLog.log("WARNING: Unable to retrieve mapping for Transfer Status " + paramString);
      return paramString;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("WARNING: Unable to retrieve mapping for Transfer Status " + paramString);
      return paramString;
    }
    return str;
  }
  
  private static String a(String paramString1, String paramString2, Locale paramLocale)
    throws Exception
  {
    String str = null;
    if ((paramString1.equals("Transfer Report")) || (paramString1.equals("External Transfer Deposit Verification Report")) || (paramString1.equals("External Transfer Report"))) {
      str = a(paramString2, paramLocale) + " " + ReportConsts.getText(1060, paramLocale);
    } else if (paramString1.equals("Bill Payment Report")) {
      str = jdField_do(paramString2, paramLocale) + " " + ReportConsts.getText(1009, paramLocale);
    } else {
      str = paramString2 + " " + ReportConsts.getText(1009, paramLocale);
    }
    return str;
  }
  
  private static String jdField_do(String paramString, Locale paramLocale)
  {
    String str = null;
    if (jdField_int(paramString))
    {
      DebugLog.log("WARNING: Payment Status is null or empty");
      return "-";
    }
    try
    {
      str = ResourceUtil.getString(paramString, "com.ffusion.beans.reporting.billpay_status", paramLocale);
    }
    catch (Exception localException)
    {
      DebugLog.log("WARNING: Unable to retrieve mapping for Payment Status " + paramString);
      return paramString;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("WARNING: Unable to retrieve mapping for Payment Status " + paramString);
      return paramString;
    }
    return str;
  }
  
  private static boolean jdField_else(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if ("AllAccounts".equals(str)) {
        return true;
      }
    }
    return false;
  }
  
  private static ReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws AdminException
  {
    String str1 = "ReportTransactionAdapter.getCashConReportData";
    ReportResult localReportResult = null;
    boolean bool = true;
    localReportResult = new ReportResult(Locale.getDefault());
    String str2;
    try
    {
      if ((paramString.equals("Cash Concentration Inactive Divisions and Locations Report")) || (paramString.equals("Inactive or Non-Reporting Divisions and Locations Report"))) {
        jdField_do(paramSecureUser, paramReportCriteria, localReportResult, paramString, paramHashMap);
      } else if ((paramString.equals("Cash Concentration Company Activity Report")) || (paramString.equals("Cash Concentration Activity Report"))) {
        bool = jdField_if(paramSecureUser, paramReportCriteria, localReportResult, paramString, paramHashMap);
      } else if (paramString.equals("Cash Concentration Cut-Off Status Report")) {
        a(paramSecureUser, paramReportCriteria, localReportResult, paramString, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      localException.printStackTrace();
      str2 = "Unable to generate the report.";
      throw new AdminException(str1, 19, str2);
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException2)
      {
        String str3 = "Unable to generate the report.";
        throw new AdminException(str1, 19, str3);
      }
    }
    if (!bool)
    {
      localReportResult = new ReportResult(paramReportCriteria.getLocale());
      try
      {
        localReportResult.init(paramReportCriteria);
        localReportResult.setHeading(null);
      }
      catch (RptException localRptException1)
      {
        str2 = "Unable to generate the report.";
        throw new AdminException(str1, 19, str2);
      }
    }
    return localReportResult;
  }
  
  private static void a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ReportResult paramReportResult, String paramString, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.obtainCashConCutOffReportFromCriteria";
    Locale localLocale = paramReportCriteria.getLocale();
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str2 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(str2);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localInteger = new Integer(-1);
    }
    String str3 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str3 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str3, localLocale);
    }
    int i1 = -1;
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str4 = (String)localEnumeration.nextElement();
      str5 = localProperties2.getProperty(str4);
      if ((str5 == null) || (str5.length() == 0)) {
        localProperties2.remove(str4);
      }
    }
    int i2 = -1;
    String str5 = null;
    String str6 = null;
    i2 = a(localProperties2, paramSecureUser);
    jdField_do(paramReportCriteria, localProperties2);
    str5 = Profile.getSearchCriteria(localProperties2, "StartDate", null);
    str6 = Profile.getSearchCriteria(localProperties2, "EndDate", null);
    String str7 = null;
    String str8 = null;
    if ((str5 != null) && (str5.length() > 0)) {
      try
      {
        str7 = jdField_case(str5).toString();
      }
      catch (ParseException localParseException1)
      {
        throw new BCReportException(str1, 13, "Invalid format for startDate: " + str5);
      }
    }
    if ((str6 != null) && (str6.length() > 0)) {
      try
      {
        str8 = jdField_case(str6).toString();
      }
      catch (ParseException localParseException2)
      {
        throw new BCReportException(str1, 13, "Invalid format for endDate: " + str6);
      }
    }
    paramReportResult.init(paramReportCriteria);
    paramReportResult.setHeading(null);
    a(paramReportCriteria, paramString, i2, str7, str8, paramReportResult, i1, localLocale, paramSecureUser, localDateFormat);
  }
  
  private static void a(ReportCriteria paramReportCriteria, String paramString1, int paramInt1, String paramString2, String paramString3, ReportResult paramReportResult, int paramInt2, Locale paramLocale, SecureUser paramSecureUser, DateFormat paramDateFormat)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.getCutOffStatusForReport";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    try
    {
      localConnection = DBUtil.getConnection(bd, true, 2);
      HashMap localHashMap1 = a(localConnection);
      StringBuffer localStringBuffer = new StringBuffer(jdField_void);
      if (paramInt1 != -1) {
        localStringBuffer.append(T);
      }
      if ((paramString2 != null) && (paramString2.trim().length() != 0)) {
        localStringBuffer.append(bE);
      }
      if ((paramString3 != null) && (paramString3.trim().length() != 0)) {
        localStringBuffer.append(k);
      }
      localStringBuffer.append(R);
      localStringBuffer.append(F);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i1 = 1;
      if (paramInt2 != -1) {
        localPreparedStatement.setMaxRows(paramInt2 + 1);
      }
      if (paramInt1 != -1) {
        localPreparedStatement.setInt(i1++, paramInt1);
      }
      if ((paramString2 != null) && (paramString2.trim().length() != 0)) {
        localPreparedStatement.setString(i1++, paramString2);
      }
      if ((paramString3 != null) && (paramString3.trim().length() != 0)) {
        localPreparedStatement.setString(i1++, paramString3);
      }
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      String str2 = null;
      int i2 = 0;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = null;
      String str7 = null;
      String str8 = null;
      String str9 = null;
      Object localObject1 = null;
      String str10 = null;
      String str11 = null;
      Object localObject2 = null;
      ReportResult localReportResult1 = new ReportResult(paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult1);
      localReportResult1.addSubReport(localReportResult2);
      SummaryInfo localSummaryInfo = new SummaryInfo();
      localSummaryInfo.setLocale(paramLocale);
      localSummaryInfo.setUser(paramSecureUser);
      ArrayList localArrayList = new ArrayList();
      while ((localResultSet1.next()) && ((paramInt2 == -1) || (paramInt2 > localSummaryInfo.numRows)))
      {
        str2 = localResultSet1.getString(1);
        str4 = localResultSet1.getString(2);
        str5 = localResultSet1.getString(3);
        str6 = localResultSet1.getString(4);
        str7 = localResultSet1.getString(5);
        str8 = localResultSet1.getString(6);
        i2 = localResultSet1.getInt(7);
        AffiliateBank localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(paramSecureUser, i2);
        ACH.getAffiliateBank(paramSecureUser, localAffiliateBank, new HashMap());
        str3 = localAffiliateBank.getAffiliateRoutingNum();
        if (localObject1 == null)
        {
          localObject3 = str2 + " - " + str3;
          a(localReportResult1, paramLocale, (String)localObject3, null);
        }
        str9 = jdField_if(str5, str7, str8) + (str8 == null ? " (" + ReportConsts.getText(10055, paramReportCriteria.getLocale()) + ")" : "");
        if ((localObject2 != null) && (!str6.equals(localObject2)))
        {
          a(localReportResult1, (String)localObject2, localArrayList, paramLocale, localSummaryInfo, localSummaryInfo.getFileTotalSummaryItem(), str11, paramReportCriteria);
          localSummaryInfo.resetFileTotal();
          localArrayList = new ArrayList();
        }
        if ((localObject1 != null) && (!str2.equals(localObject1)))
        {
          a(localReportResult1, (String)localObject1, str10, paramLocale, localSummaryInfo, localSummaryInfo.getBankTotalSummaryItem(), paramReportCriteria);
          localSummaryInfo.resetBankTotal();
          localReportResult1 = new ReportResult(paramLocale);
          paramReportResult.addSubReport(localReportResult1);
          localObject3 = str2 + " - " + str3;
          a(localReportResult1, paramLocale, (String)localObject3, null);
        }
        Object localObject3 = null;
        ResultSet localResultSet2 = null;
        localObject3 = DBUtil.prepareStatement(localConnection, by);
        ((PreparedStatement)localObject3).setString(1, str4);
        localResultSet2 = DBUtil.executeQuery((PreparedStatement)localObject3, by);
        String str12 = null;
        String str13 = null;
        String str14 = null;
        String str15 = null;
        String str16 = null;
        String str17 = null;
        String str18 = null;
        String str19 = null;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        String str20 = null;
        String str21 = null;
        Object localObject4 = null;
        Object localObject5 = null;
        String str22 = null;
        while (localResultSet2.next())
        {
          if ((paramInt2 != -1) && (paramInt2 <= localSummaryInfo.getGrandTotalSummaryItem().totalNumTransactions))
          {
            HashMap localHashMap2 = new HashMap();
            localHashMap2.put("TRUNCATED", new Integer(paramInt2));
            paramReportResult.setProperties(localHashMap2);
            break;
          }
          str12 = localResultSet2.getString(1);
          str13 = localResultSet2.getString(2);
          str14 = localResultSet2.getString(3);
          str15 = localResultSet2.getString(4);
          str16 = localResultSet2.getString(5);
          str17 = localResultSet2.getString(6);
          str18 = localResultSet2.getString(7);
          int i6 = Integer.parseInt(str18);
          i6 /= 100;
          DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
          str18 = paramDateFormat.format(localDateFormat.parse("" + i6));
          i3 = localResultSet2.getInt(8);
          i4 = localResultSet2.getInt(9);
          str20 = localResultSet2.getString(10);
          str21 = localResultSet2.getString(11);
          str19 = localResultSet2.getString(12);
          i5 = i3 + i4;
          if (str20 != null) {
            str20 = a(str20, paramLocale, false);
          } else {
            str20 = "0.00";
          }
          if (str21 != null) {
            str21 = a(str21, paramLocale, false);
          } else {
            str21 = "0.00";
          }
          SummaryItem localSummaryItem = new SummaryItem(new BigDecimal(str21), i3, new BigDecimal(str20), i4, 1L);
          localSummaryItem.totalNumTransactions = i5;
          localSummaryInfo.addFileTotal(localSummaryItem);
          localSummaryInfo.addBankTotal(localSummaryItem);
          localSummaryInfo.addGrandTotal(localSummaryItem);
          HashMap localHashMap3 = new HashMap();
          localHashMap3.put("CashConCompanyName", str12);
          localHashMap3.put("CashConCompanyID", str13);
          localHashMap3.put("BatchType", str14);
          localHashMap3.put("BatchNumber", str17);
          localHashMap3.put("EffectiveEntryDate", str18);
          localHashMap3.put("NumDepositEntriesDisbursementRequests", new Integer(i5));
          localHashMap3.put("TotalCreditAmount", str20);
          localHashMap3.put("TotalDebitAmount", str21);
          localHashMap3.put("ProcessDate", jdField_if(str5));
          localHashMap3.put("ProcessTime", jdField_new(str5));
          if (str14.equals("BatchBalancedBatch")) {
            if ("DISBURSEMENT".equals(str19)) {
              str22 = str16;
            } else {
              str22 = str15;
            }
          }
          localHashMap3.put("ConcentrationDisbursementAccount", a(str22, localHashMap1));
          localArrayList.add(localHashMap3);
        }
        DBUtil.closeAll((PreparedStatement)localObject3, localResultSet2);
        localObject3 = null;
        localResultSet2 = null;
        localObject1 = str2;
        str10 = str3;
        str11 = str9;
        localObject2 = str6;
      }
      if (str6 != null) {
        a(localReportResult1, str6, localArrayList, paramLocale, localSummaryInfo, localSummaryInfo.getFileTotalSummaryItem(), str11, paramReportCriteria);
      }
      if (str2 != null) {
        a(localReportResult1, (String)localObject1, str10, paramLocale, localSummaryInfo, localSummaryInfo.getBankTotalSummaryItem(), paramReportCriteria);
      }
      if ((localSummaryInfo.getGrandTotalSummaryItem().totalNumTransactions > 0L) && (localSummaryInfo.getGrandTotalSummaryItem().totalNumBatches > 0L)) {
        a(paramReportResult, paramLocale, localSummaryInfo, localSummaryInfo.getGrandTotalSummaryItem(), paramReportCriteria);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet1);
    }
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem, ReportCriteria paramReportCriteria)
    throws Exception
  {
    String str = "ReportTransactionAdapter.outputCashConCutOffGrandTotal";
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, ReportConsts.getText(2552, paramLocale), null);
    a(localReportResult, paramLocale, 1, 4);
    a(localReportResult, paramLocale);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumBatches);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumTransactions);
    localReportDataItems.add().setData("" + paramSummaryItem.totalCreditsAmount.setScale(2, 4));
    localReportDataItems.add().setData("" + paramSummaryItem.totalDebitsAmount.setScale(2, 4));
    localReportResult.addRow(localReportRow);
    paramSummaryInfo.numRows += 1L;
  }
  
  private static void a(ReportResult paramReportResult, String paramString, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, paramString, null);
  }
  
  private static void a(ReportResult paramReportResult, String paramString1, ArrayList paramArrayList, Locale paramLocale, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem, String paramString2, ReportCriteria paramReportCriteria)
    throws RptException
  {
    String str = "ReportTransactionAdapter.outputCashConCutOffFileSummary";
    if ((paramString1 == null) || (paramArrayList == null)) {
      return;
    }
    if (paramArrayList.size() == 0) {
      return;
    }
    a(paramReportResult, paramString2, paramLocale, paramReportCriteria);
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, ReportConsts.getText(2549, paramLocale) + paramString1, null);
    a(localReportResult, paramLocale, -1, 10);
    jdField_if(localReportResult, paramLocale);
    int i1 = 1;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localObject = (HashMap)localIterator.next();
      if (localObject != null)
      {
        localReportRow = new ReportRow(paramLocale);
        if (i1 % 2 == 0) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportDataItems = new ReportDataItems(paramLocale);
        localReportRow.setDataItems(localReportDataItems);
        a(localReportDataItems.add(), (String)((HashMap)localObject).get("ProcessDate"));
        a(localReportDataItems.add(), (String)((HashMap)localObject).get("ProcessTime"));
        a(localReportDataItems.add(), (String)((HashMap)localObject).get("EffectiveEntryDate"));
        a(localReportDataItems.add(), (String)((HashMap)localObject).get("BatchNumber"));
        a(localReportDataItems.add(), (String)((HashMap)localObject).get("CashConCompanyName"));
        a(localReportDataItems.add(), (String)((HashMap)localObject).get("CashConCompanyID"));
        a(localReportDataItems.add(), ((HashMap)localObject).get("ConcentrationDisbursementAccount").toString());
        a(localReportDataItems.add(), ((HashMap)localObject).get("NumDepositEntriesDisbursementRequests").toString());
        a(localReportDataItems.add(), ((HashMap)localObject).get("TotalCreditAmount").toString());
        a(localReportDataItems.add(), ((HashMap)localObject).get("TotalDebitAmount").toString());
        localReportResult.addRow(localReportRow);
        i1++;
      }
    }
    Object localObject = new ReportResult(paramLocale);
    paramReportResult.addSubReport((ReportResult)localObject);
    a((ReportResult)localObject, paramLocale, ReportConsts.getText(2550, paramLocale) + " " + paramString1, null);
    a((ReportResult)localObject, paramLocale, 1, 4);
    a((ReportResult)localObject, paramLocale);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumBatches);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumTransactions);
    localReportDataItems.add().setData("" + paramSummaryItem.totalCreditsAmount.setScale(2, 4));
    localReportDataItems.add().setData("" + paramSummaryItem.totalDebitsAmount.setScale(2, 4));
    ((ReportResult)localObject).addRow(localReportRow);
    paramSummaryInfo.numRows += 1L;
  }
  
  private static void a(ReportResult paramReportResult, String paramString1, String paramString2, Locale paramLocale, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem, ReportCriteria paramReportCriteria)
    throws RptException
  {
    String str = "ReportTransactionAdapter.outputCashConCutOffBankSummary";
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, ReportConsts.getText(2551, paramLocale) + " " + paramString1, null);
    a(localReportResult, paramLocale, 1, 4);
    a(localReportResult, paramLocale);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumBatches);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumTransactions);
    localReportDataItems.add().setData("" + paramSummaryItem.totalCreditsAmount.setScale(2, 4));
    localReportDataItems.add().setData("" + paramSummaryItem.totalDebitsAmount.setScale(2, 4));
    localReportResult.addRow(localReportRow);
    paramSummaryInfo.numRows += 1L;
  }
  
  private static void jdField_if(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    String str = "ReportTransactionAdapter.addColumnsForCashConCutOffFileSummary";
    a(paramReportResult, 2535, null, 12, null);
    a(paramReportResult, 2536, null, 12, null);
    a(paramReportResult, 2537, null, 12, null);
    a(paramReportResult, 2538, null, 10, null);
    a(paramReportResult, 2539, null, 12, null);
    a(paramReportResult, 2540, null, 16, null);
    a(paramReportResult, 2541, null, 10, null);
    a(paramReportResult, 2542, null, 20, null);
    a(paramReportResult, 2543, null, 16, "RIGHT");
    a(paramReportResult, 2544, null, 16, "RIGHT");
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    String str = "ReportTransactionAdapter.addColumnsForCashConCutOffSubTotalReports";
    a(paramReportResult, 2545, null, 10, null);
    a(paramReportResult, 2546, null, 10, null);
    a(paramReportResult, 2547, null, 10, null);
    a(paramReportResult, 2548, null, 10, null);
  }
  
  private static String jdField_if(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    String str = "ReportTransactionAdapter.getCashConCutOffDate";
    if (paramString1 == null) {
      return "";
    }
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
    Date localDate = localDateFormat.parse(paramString1);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    if ((paramString2 != null) && (paramString3 != null))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString3, ":");
      int i1 = Integer.parseInt(localStringTokenizer.nextToken());
      int i2 = Integer.parseInt(localStringTokenizer.nextToken());
      localCalendar.set(7, Integer.parseInt(paramString2));
      localCalendar.set(11, i1);
      localCalendar.set(12, i2);
    }
    localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy E hh:mm a");
    return localDateFormat.format(localCalendar.getTime()).toString();
  }
  
  private static String jdField_if(String paramString)
    throws Exception
  {
    String str = "ReportTransactionAdapter.getCashConCutOffProcessDate";
    if (paramString == null) {
      return "";
    }
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
    Date localDate = localDateFormat.parse(paramString);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy");
    return localDateFormat.format(localCalendar.getTime());
  }
  
  private static String jdField_new(String paramString)
    throws Exception
  {
    String str = "ReportTransactionAdapter.getCashConCutOffProcessDate";
    if (paramString == null) {
      return "";
    }
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
    Date localDate = localDateFormat.parse(paramString);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    localDateFormat = DateFormatUtil.getFormatter("hh:mm a");
    return localDateFormat.format(localCalendar.getTime());
  }
  
  private static HashMap a(Connection paramConnection)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.getBPWBankAccounts";
    HashMap localHashMap = new HashMap();
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, a9);
    ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, a9);
    String str2 = null;
    String str3 = null;
    while (localResultSet.next())
    {
      str2 = localResultSet.getString(1);
      str3 = localResultSet.getString(2);
      localHashMap.put(str2, str3);
    }
    DBUtil.closeAll(localPreparedStatement, localResultSet);
    return localHashMap;
  }
  
  private static String a(String paramString, HashMap paramHashMap)
  {
    String str1 = "ReportTransactionAdapter.getCashConCutOffAccountNumber";
    if (paramString == null) {
      return "";
    }
    String str2 = (String)paramHashMap.get(paramString);
    if (str2 == null) {
      return "";
    }
    return str2;
  }
  
  private static void jdField_do(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ReportResult paramReportResult, String paramString, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.obtainCashConInactiveDivisionsAndLocationsFromCriteria";
    Locale localLocale = paramReportCriteria.getLocale();
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str2 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(str2);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      localInteger = new Integer(-1);
    }
    String str3 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str3 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str3, localLocale);
    }
    int i1 = -1;
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      localObject1 = (String)localEnumeration.nextElement();
      localObject2 = localProperties2.getProperty((String)localObject1);
      if ((localObject2 == null) || (((String)localObject2).length() == 0)) {
        localProperties2.remove(localObject1);
      }
    }
    Object localObject1 = localProperties2.keySet();
    Object localObject2 = ((Set)localObject1).iterator();
    String str4 = null;
    String str5 = null;
    while (((Iterator)localObject2).hasNext())
    {
      str4 = (String)((Iterator)localObject2).next();
      str5 = localProperties2.getProperty(str4);
      Object localObject3;
      if ((str4.equals("TransactionType")) && (str5 != null)) {
        if (str5.indexOf("AllTransactions") != -1)
        {
          paramReportCriteria.setDisplayValue("TransactionType", a("AllTransactions", paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status"));
        }
        else
        {
          localObject3 = new StringBuffer();
          int i2 = 1;
          localObject4 = new StringTokenizer(str5, ",", false);
          while (((StringTokenizer)localObject4).hasMoreTokens())
          {
            String str7 = ((StringTokenizer)localObject4).nextToken().trim();
            if (i2 == 0) {
              ((StringBuffer)localObject3).append(',');
            }
            ((StringBuffer)localObject3).append(a(str7, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status"));
            i2 = 0;
          }
          paramReportCriteria.setDisplayValue("TransactionType", ((StringBuffer)localObject3).toString());
        }
      }
      if ((str4.equals("Business")) && (str5 != null)) {
        if (str5.equals("AllBusinesses")) {
          paramReportCriteria.setDisplayValue("Business", ReportConsts.getText(10015, paramReportCriteria.getLocale()));
        } else {
          try
          {
            localObject3 = new Integer(str5);
            Business localBusiness = BusinessAdapter.getBusiness(((Integer)localObject3).intValue());
            if (localBusiness != null) {
              paramReportCriteria.setDisplayValue("Business", localBusiness.getBusinessName());
            }
          }
          catch (ProfileException localProfileException) {}
        }
      }
    }
    String str6 = Profile.getSearchCriteria(localProperties2, "Business", "AllBusinesses");
    int i3 = -1;
    i3 = a(localProperties2, paramSecureUser);
    jdField_do(paramReportCriteria, localProperties2);
    Object localObject4 = Profile.getSearchCriteria(localProperties2, "InactivePeriod", null);
    if ((localObject4 == null) || (((String)localObject4).trim().equals("0"))) {
      throw new BCReportException(str1, 10, "Invalid value for Inactive Period");
    }
    try
    {
      Integer.parseInt((String)localObject4);
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      throw new BCReportException(str1, 10, "Invalid value for Inactive Period");
    }
    paramReportCriteria.setDisplayValue("InactivePeriod", (String)localObject4 + " " + ReportConsts.getText(2558, localLocale));
    paramReportResult.init(paramReportCriteria);
    paramReportResult.setHeading(null);
    a(paramReportCriteria, paramString, str6, i3, (String)localObject4, paramReportResult, i1, localLocale, paramSecureUser);
  }
  
  private static void a(ReportCriteria paramReportCriteria, String paramString1, String paramString2, int paramInt1, String paramString3, ReportResult paramReportResult, int paramInt2, Locale paramLocale, SecureUser paramSecureUser)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.getInactiveDivisionsAndLocationsForReport";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    ResultSet localResultSet1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet2 = null;
    StringBuffer localStringBuffer = new StringBuffer("SELECT DISTINCT BPW_Customer.FirstName, CC_Company.CompName, entitlement_group.name, CC_Location.LocationName, CC_Location.LocationId, CC_Location.LastRequestTime FROM CC_Location, location, entitlement_group,  CC_Company, BPW_Customer WHERE ");
    try
    {
      if (paramInt1 != -1) {
        localStringBuffer.append("BPW_Customer.FIID = ? and ");
      }
      if (!paramString2.equals("AllBusinesses")) {
        localStringBuffer.append("BPW_Customer.CustomerID = ? and ");
      }
      localStringBuffer.append(" CC_Company.CustomerId = BPW_Customer.CustomerID and CC_Location.CompId = CC_Company.CompId and location.LOCATION_BPW_ID = CC_Location.LocationId and location.DIVISION_ENT_GROUP_ID = entitlement_group.ent_group_id ");
      localStringBuffer.append(" ORDER BY BPW_Customer.FirstName, CC_Company.CompName, entitlement_group.name, CC_Location.LocationName");
      DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd 00:00:00");
      String str2 = a(localDateFormat, paramString3);
      localConnection = DBUtil.getConnection(bd, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      if (paramInt2 != -1) {
        localPreparedStatement1.setMaxRows(paramInt2 + 1);
      }
      int i1 = 1;
      if (paramInt1 != -1)
      {
        localObject1 = a(paramSecureUser, paramInt1);
        localPreparedStatement1.setString(i1++, (String)localObject1);
      }
      if (!paramString2.equals("AllBusinesses")) {
        localPreparedStatement1.setString(i1++, paramString2);
      }
      Object localObject1 = new StringBuffer("select CC_Entry.CreatedDate from CC_Entry where CC_Entry.LocationId = ? ");
      ((StringBuffer)localObject1).append(" order by CC_Entry.CreatedDate desc");
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer.toString());
      ArrayList localArrayList = new ArrayList();
      while (localResultSet1.next())
      {
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        Object localObject2 = null;
        com.ffusion.beans.DateTime localDateTime = null;
        str3 = localResultSet1.getString(1);
        str4 = localResultSet1.getString(2);
        str5 = localResultSet1.getString(3);
        str6 = localResultSet1.getString(4);
        str7 = localResultSet1.getString(5);
        localObject2 = localResultSet1.getString(6);
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, ((StringBuffer)localObject1).toString());
        i1 = 1;
        localPreparedStatement2.setString(i1++, str7);
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, ((StringBuffer)localObject1).toString());
        if (localResultSet2.next())
        {
          str8 = localResultSet2.getString(1);
          if (str8 != null) {
            localObject2 = str8;
          }
        }
        else
        {
          DBUtil.closeAll(localPreparedStatement2, localResultSet2);
          localPreparedStatement2 = null;
          localResultSet2 = null;
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "SELECT AUDIT_LOG.LOG_DATE FROM CC_Location, AUDIT_LOG WHERE   CC_Location.LocationId = ? and  AUDIT_LOG.TRAN_TYPE=5303 and CC_Location.LogId=AUDIT_LOG.TRAN_ID");
          i1 = 1;
          localPreparedStatement2.setString(i1++, str7);
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, ((StringBuffer)localObject1).toString());
          if (localResultSet2.next())
          {
            localDateTime = new com.ffusion.beans.DateTime(localResultSet2.getTimestamp(1), Locale.getDefault());
            localObject2 = localDateFormat.format(localDateTime.getTime());
          }
        }
        DBUtil.closeAll(localPreparedStatement2, localResultSet2);
        localPreparedStatement2 = null;
        localResultSet2 = null;
        String str8 = jdField_char((String)localObject2);
        if (Integer.parseInt(str8) >= Integer.parseInt(paramString3))
        {
          if (str3 == null) {
            str3 = "";
          }
          if (str4 == null) {
            str4 = "";
          }
          HashMap localHashMap = new HashMap();
          localHashMap.put("BusinessName", str3);
          localHashMap.put("CompanyName", str4);
          localHashMap.put("DivisionName", str5);
          localHashMap.put("LocationName", str6);
          localHashMap.put("LocationID", str7);
          localHashMap.put("LastRequestTime", localObject2);
          localArrayList.add(localHashMap);
        }
      }
      if (paramString1.equals("Inactive or Non-Reporting Divisions and Locations Report"))
      {
        a(localArrayList, paramReportResult, paramLocale, paramInt2, paramReportCriteria, str2, localConnection);
        return;
      }
      if (paramString1.equals("Cash Concentration Inactive Divisions and Locations Report")) {
        jdField_if(localArrayList, paramReportResult, paramLocale, paramInt2, paramReportCriteria, str2, localConnection);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
    finally
    {
      if (localResultSet2 != null)
      {
        DBUtil.closeResultSet(localResultSet2);
        localResultSet2 = null;
      }
      if (localPreparedStatement2 != null) {
        DBUtil.closeStatement(localPreparedStatement2);
      }
      DBUtil.closeAll(bd, localConnection, localPreparedStatement1, localResultSet1);
    }
  }
  
  private static void a(ReportResult paramReportResult, String paramString1, ArrayList paramArrayList, String paramString2, String paramString3, String paramString4, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws Exception
  {
    String str = "ReportTransactionAdapter.outputInactiveDivisionAndLocationCompanySummary";
    if ((paramReportResult == null) || (paramString1 == null)) {
      return;
    }
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(paramString1);
    localReportResult.setHeading(localReportHeading);
    a(localReportResult, paramLocale, -1, 3);
    a(localReportResult, 2513, null, 15, null);
    a(localReportResult, 2514, null, 15, null);
    a(localReportResult, 2515, null, 15, null);
    int i1 = 1;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      ReportRow localReportRow = new ReportRow(paramLocale);
      if (i1 % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      HashMap localHashMap = (HashMap)localIterator.next();
      a(localReportDataItems.add(), (String)localHashMap.get(paramString2));
      a(localReportDataItems.add(), (String)localHashMap.get(paramString3));
      a(localReportDataItems.add(), (String)localHashMap.get(paramString4));
      localReportResult.addRow(localReportRow);
      i1++;
    }
  }
  
  private static String a(DateFormat paramDateFormat, String paramString)
    throws Exception
  {
    String str = "ReportTransactionAdapter.getReferenceDate";
    int i1 = Integer.parseInt(paramString);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -1 * i1);
    return paramDateFormat.format(localCalendar.getTime());
  }
  
  private static String jdField_char(String paramString)
    throws Exception
  {
    String str = "ReportTransactionAdapter.getInactiveFor";
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    localCalendar1.add(10, 0);
    localCalendar1.add(12, 0);
    localCalendar1.add(13, 0);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTime(localDateFormat.parse(paramString));
    long l1 = (localCalendar1.getTime().getTime() - localCalendar2.getTime().getTime()) / 86400000L;
    return String.valueOf(l1);
  }
  
  private static boolean jdField_if(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, ReportResult paramReportResult, String paramString, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.obtainCashConCompanyReportCriteria";
    Locale localLocale = paramReportCriteria.getLocale();
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str2 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(str2);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localInteger = new Integer(-1);
    }
    String str3 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str3 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str3, localLocale);
    }
    int i1 = -1;
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      localObject1 = (String)localEnumeration.nextElement();
      localObject2 = localProperties2.getProperty((String)localObject1);
      if ((localObject2 == null) || (((String)localObject2).length() == 0)) {
        localProperties2.remove(localObject1);
      }
    }
    Object localObject1 = localProperties2.keySet();
    Object localObject2 = ((Set)localObject1).iterator();
    String str4 = null;
    String str5 = null;
    while (((Iterator)localObject2).hasNext())
    {
      str4 = (String)((Iterator)localObject2).next();
      str5 = localProperties2.getProperty(str4);
      Object localObject3;
      if (str4.equals("Division")) {
        if ((str5.equals("")) || (str5.equals("AllDivisions")))
        {
          paramReportCriteria.setDisplayValue("Division", ReportConsts.getText(2553, localLocale));
        }
        else
        {
          localObject3 = (String)paramHashMap.get("ExtraDivisionName");
          if (localObject3 != null) {
            paramReportCriteria.setDisplayValue("Division", (String)localObject3);
          }
        }
      }
      if (str4.equals("Location")) {
        if ((str5.equals("")) || (str5.equals("AllLocations")))
        {
          paramReportCriteria.setDisplayValue("Location", ReportConsts.getText(2554, localLocale));
        }
        else
        {
          localObject3 = (String)paramHashMap.get("ExtraLocationName");
          if (localObject3 != null) {
            paramReportCriteria.setDisplayValue("Location", (String)localObject3);
          }
        }
      }
      if ((str4.equals("Status")) && (str5 != null)) {
        paramReportCriteria.setDisplayValue("Status", a(str5, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status"));
      }
      if ((str4.equals("TransactionType")) && (str5 != null)) {
        if (str5.indexOf("AllTransactions") != -1)
        {
          paramReportCriteria.setDisplayValue("TransactionType", a("AllTransactions", paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status"));
        }
        else
        {
          localObject3 = new StringBuffer();
          int i3 = 1;
          localObject4 = new StringTokenizer(str5, ",", false);
          while (((StringTokenizer)localObject4).hasMoreTokens())
          {
            str7 = ((StringTokenizer)localObject4).nextToken().trim();
            if (i3 == 0) {
              ((StringBuffer)localObject3).append(',');
            }
            ((StringBuffer)localObject3).append(a(str7, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status"));
            i3 = 0;
          }
          paramReportCriteria.setDisplayValue("TransactionType", ((StringBuffer)localObject3).toString());
        }
      }
    }
    int i2 = -1;
    i2 = a(localProperties2, paramSecureUser);
    jdField_do(paramReportCriteria, localProperties2);
    String str6 = a(paramReportCriteria, localProperties2);
    Object localObject4 = Profile.getSearchCriteria(localProperties2, "StartDate", null);
    String str7 = Profile.getSearchCriteria(localProperties2, "EndDate", null);
    String str8 = jdField_if(paramReportCriteria, localProperties2);
    String str9 = Profile.getSearchCriteria(localProperties2, "TransactionType", "AllTransactions");
    String str10 = Profile.getSearchCriteria(localProperties2, "Status", "AllStatuses");
    String str11 = Profile.getSearchCriteria(localProperties2, "DebitFromAmount", null);
    String str12 = Profile.getSearchCriteria(localProperties2, "DebitToAmount", null);
    String str13 = Profile.getSearchCriteria(localProperties2, "CreditFromAmount", null);
    String str14 = Profile.getSearchCriteria(localProperties2, "CreditToAmount", null);
    String str15 = Profile.getSearchCriteria(localProperties2, "Transaction", null);
    String str16 = Profile.getSearchCriteria(localProperties2, "Division", null);
    String str17 = Profile.getSearchCriteria(localProperties2, "Location", null);
    String str18 = null;
    String str19 = null;
    if ((localObject4 != null) && (((String)localObject4).length() > 0)) {
      try
      {
        str18 = jdField_case((String)localObject4).toString();
      }
      catch (ParseException localParseException1)
      {
        throw new BCReportException(str1, 13, "Invalid format for startDate: " + (String)localObject4);
      }
    }
    if ((str7 != null) && (str7.length() > 0)) {
      try
      {
        str19 = jdField_case(str7).toString();
      }
      catch (ParseException localParseException2)
      {
        throw new BCReportException(str1, 13, "Invalid format for endDate: " + str7);
      }
    }
    if ((str11 != null) && (!jdField_for(str11))) {
      throw new BCReportException(str1, 10, "Invalid Debit Amount: " + str11);
    }
    if ((str12 != null) && (!jdField_for(str12))) {
      throw new BCReportException(str1, 10, "Invalid Debit Amount: " + str12);
    }
    if ((str13 != null) && (!jdField_for(str13))) {
      throw new BCReportException(str1, 10, "Invalid Credit Amount: " + str13);
    }
    if ((str14 != null) && (!jdField_for(str14))) {
      throw new BCReportException(str1, 10, "Invalid Credit Amount: " + str14);
    }
    paramReportResult.init(paramReportCriteria);
    paramReportResult.setHeading(null);
    return a(paramReportCriteria, paramString, i2, str6, str18, str19, str10, str8, str9, str11, str12, str13, str14, str15, str16, str17, paramReportResult, i1, localLocale, paramSecureUser);
  }
  
  private static boolean a(ReportCriteria paramReportCriteria, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, ReportResult paramReportResult, int paramInt2, Locale paramLocale, SecureUser paramSecureUser)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.getCompanyActivityForReport";
    HashMap localHashMap1 = new HashMap();
    ArrayList localArrayList1 = new ArrayList();
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("DATEFORMAT");
    String str3 = localProperties.getProperty("TIMEFORMAT");
    DateFormat localDateFormat = null;
    if (str2 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str2 + (str3 != null ? " " + str3 : ""), paramLocale);
    }
    String str4 = bx;
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    if ((localReportSortCriteria != null) && (localReportSortCriteria.size() > 0))
    {
      localReportSortCriteria.setSortedBy("ORDINAL");
      localObject1 = localReportSortCriteria.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ReportSortCriterion)((Iterator)localObject1).next();
        localObject3 = ((ReportSortCriterion)localObject2).getName();
        if ((localObject3 != null) && (((String)localObject3).length() != 0)) {
          if (((String)localObject3).equals("ProcessingDate")) {
            str4 = bx;
          } else if (((String)localObject3).equals("Absolute Transaction Amount")) {
            str4 = bw;
          }
        }
      }
    }
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    StringBuffer localStringBuffer1 = new StringBuffer(E);
    int i1 = 0;
    if (paramInt1 != -1) {
      localStringBuffer1.append(ad);
    }
    if ((paramString2 != null) && (!paramString2.equals("AllBusinesses")))
    {
      try
      {
        i1 = Integer.parseInt(paramString2);
      }
      catch (Exception localException1) {}
      localStringBuffer1.append(jdField_byte);
    }
    localStringBuffer1.append(f);
    localStringBuffer1.append(bA);
    boolean bool = false;
    try
    {
      localObject1 = DBUtil.getConnection(bd, true, 2);
      localObject2 = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer1.toString());
      int i2 = 1;
      if (paramInt2 != -1) {
        ((PreparedStatement)localObject2).setMaxRows(paramInt2 + 1);
      }
      if (paramInt1 != -1)
      {
        str5 = a(paramSecureUser, paramInt1);
        ((PreparedStatement)localObject2).setString(i2++, str5);
      }
      if ((paramString2 != null) && (!paramString2.equals("AllBusinesses"))) {
        ((PreparedStatement)localObject2).setString(i2++, paramString2);
      }
      localObject3 = DBUtil.executeQuery((PreparedStatement)localObject2, localStringBuffer1.toString());
      String str5 = null;
      String str6 = null;
      String str7 = null;
      String str8 = null;
      String str9 = null;
      String str10 = null;
      String str11 = null;
      String str12 = null;
      HashMap localHashMap2 = new HashMap();
      HashMap localHashMap3 = new HashMap();
      SummaryInfo localSummaryInfo = new SummaryInfo();
      localSummaryInfo.setLocale(paramLocale);
      localSummaryInfo.setUser(paramSecureUser);
      Object localObject5;
      Object localObject6;
      Object localObject7;
      Object localObject8;
      Object localObject9;
      while ((((ResultSet)localObject3).next()) && ((paramInt2 == -1) || (paramInt2 > localSummaryInfo.getGrandTotalSummaryItem().totalNumTransactions)))
      {
        localHashMap2 = new HashMap();
        str5 = ((ResultSet)localObject3).getString(1);
        str6 = ((ResultSet)localObject3).getString(2);
        str7 = ((ResultSet)localObject3).getString(3);
        str8 = ((ResultSet)localObject3).getString(4);
        str9 = ((ResultSet)localObject3).getString(5);
        str10 = ((ResultSet)localObject3).getString(6);
        str11 = ((ResultSet)localObject3).getString(7);
        localObject4 = null;
        localObject5 = null;
        localObject4 = DBUtil.prepareStatement((Connection)localObject1, be);
        ((PreparedStatement)localObject4).setString(1, str8);
        localObject5 = DBUtil.executeQuery((PreparedStatement)localObject4, be);
        if (((ResultSet)localObject5).next()) {
          str12 = ((ResultSet)localObject5).getString(1);
        }
        DBUtil.closeAll((PreparedStatement)localObject4, (ResultSet)localObject5);
        localObject6 = null;
        localObject7 = null;
        localObject6 = DBUtil.prepareStatement((Connection)localObject1, ag);
        ((PreparedStatement)localObject6).setString(1, str8);
        localObject7 = DBUtil.executeQuery((PreparedStatement)localObject6, ag);
        localObject8 = null;
        localObject9 = null;
        while (((ResultSet)localObject7).next())
        {
          localObject8 = ((ResultSet)localObject7).getString(1);
          localObject9 = ((ResultSet)localObject7).getString(2);
          localHashMap3.put(localObject8, localObject9);
        }
        DBUtil.closeAll((PreparedStatement)localObject6, (ResultSet)localObject7);
        PreparedStatement localPreparedStatement1 = null;
        ResultSet localResultSet1 = null;
        localPreparedStatement1 = DBUtil.prepareStatement((Connection)localObject1, K);
        if (paramInt2 != -1) {
          localPreparedStatement1.setMaxRows(paramInt2 + 1);
        }
        localPreparedStatement1.setString(1, str8);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, K);
        String str13 = null;
        String str14 = null;
        String str15 = null;
        String str16 = null;
        String str17 = null;
        String str18 = null;
        String str19 = null;
        while (localResultSet1.next())
        {
          str13 = localResultSet1.getString(1);
          str14 = localResultSet1.getString(2);
          str15 = localResultSet1.getString(3);
          str17 = localResultSet1.getString(4);
          str16 = localResultSet1.getString(5);
          str19 = localResultSet1.getString(6);
          str18 = localResultSet1.getString(7);
          localObject10 = new Properties();
          ((Properties)localObject10).put("AccountNumber", str14);
          ((Properties)localObject10).put("TransactionType", str15);
          ((Properties)localObject10).put("Amount", "0");
          ((Properties)localObject10).put("AccountNickname", str17);
          ((Properties)localObject10).put("AccountCurrency", str19);
          ((Properties)localObject10).put("AccountRoutingNum", str16);
          ((Properties)localObject10).put("AccountType", str18);
          localHashMap2.put(str13, localObject10);
        }
        DBUtil.closeAll(localPreparedStatement1, localResultSet1);
        Object localObject10 = new StringBuffer(n);
        int i3 = 0;
        if ((paramString13 != null) && (!paramString13.equals("AllDivisions"))) {
          try
          {
            i3 = Integer.parseInt(paramString13);
            ((StringBuffer)localObject10).append(jdField_char);
          }
          catch (NumberFormatException localNumberFormatException) {}
        }
        if ((paramString14 != null) && (!paramString14.equals("AllLocations"))) {
          ((StringBuffer)localObject10).append(a);
        }
        ((StringBuffer)localObject10).append(p);
        PreparedStatement localPreparedStatement2 = null;
        ResultSet localResultSet2 = null;
        localPreparedStatement2 = DBUtil.prepareStatement((Connection)localObject1, ((StringBuffer)localObject10).toString());
        int i4 = 1;
        if ((paramString13 != null) && (!paramString13.equals("AllDivisions"))) {
          localPreparedStatement2.setInt(i4++, i3);
        }
        if ((paramString14 != null) && (!paramString14.equals("AllLocations"))) {
          localPreparedStatement2.setString(i4++, paramString14);
        }
        localPreparedStatement2.setString(i4++, str8);
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, ((StringBuffer)localObject10).toString());
        String str20 = null;
        String str21 = null;
        String str22 = null;
        String str23 = null;
        String str24 = null;
        String str25 = null;
        String str26 = null;
        String str27 = null;
        while ((localResultSet2.next()) && ((paramInt2 == -1) || (paramInt2 > localSummaryInfo.getGrandTotalSummaryItem().totalNumTransactions)))
        {
          str20 = localResultSet2.getString(1);
          str21 = localResultSet2.getString(2);
          str22 = localResultSet2.getString(3);
          str23 = localResultSet2.getString(4);
          str24 = localResultSet2.getString(5);
          str25 = localResultSet2.getString(6);
          str26 = localResultSet2.getString(7);
          str27 = localResultSet2.getString(8);
          StringBuffer localStringBuffer2 = new StringBuffer(l);
          if (!paramString5.equals("AllStatuses")) {
            localStringBuffer2.append(bf);
          }
          if (paramString3 != null) {
            localStringBuffer2.append(B);
          }
          if (paramString4 != null) {
            localStringBuffer2.append(ap);
          }
          if (paramString6 != null) {
            localStringBuffer2.append(bC);
          }
          int i5 = 0;
          ArrayList localArrayList2 = new ArrayList();
          ArrayList localArrayList3 = new ArrayList();
          if ((paramString7 == null) || (paramString7.indexOf("AllTransactions") != -1))
          {
            i5 = 1;
          }
          else
          {
            localObject11 = new StringTokenizer(paramString7, ",", false);
            while (((StringTokenizer)localObject11).hasMoreTokens())
            {
              String str28 = ((StringTokenizer)localObject11).nextToken().trim();
              if ((str28.equals("CONCENTRATION")) || (str28.equals("DISBURSEMENT"))) {
                localArrayList2.add(str28);
              } else {
                localArrayList3.add(str28);
              }
            }
            if ((localArrayList2.size() > 0) || (localArrayList3.size() > 0))
            {
              localStringBuffer2.append("(");
              int i6;
              if (localArrayList2.size() > 0)
              {
                localStringBuffer2.append(bF);
                localStringBuffer2.append("(");
                i6 = 1;
                for (i7 = 0; i7 < localArrayList2.size(); i7++)
                {
                  if (i6 == 0) {
                    localStringBuffer2.append(',');
                  }
                  localStringBuffer2.append('?');
                  i6 = 0;
                }
                localStringBuffer2.append(")");
                if (localArrayList3.size() > 0) {
                  localStringBuffer2.append(" or ");
                }
              }
              if (localArrayList3.size() > 0)
              {
                localStringBuffer2.append(h);
                localStringBuffer2.append("(");
                i6 = 1;
                for (i7 = 0; i7 < localArrayList3.size(); i7++)
                {
                  if (i6 == 0) {
                    localStringBuffer2.append(',');
                  }
                  localStringBuffer2.append('?');
                  i6 = 0;
                }
                localStringBuffer2.append(")");
              }
              localStringBuffer2.append(") and ");
            }
          }
          localStringBuffer2.append(bD);
          localStringBuffer2.append(str4);
          Object localObject11 = null;
          ResultSet localResultSet3 = null;
          localObject11 = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer2.toString());
          if (paramInt2 != -1) {
            ((PreparedStatement)localObject11).setMaxRows(paramInt2 + 1);
          }
          int i7 = 1;
          if (!paramString5.equals("AllStatuses")) {
            ((PreparedStatement)localObject11).setString(i7++, paramString5);
          }
          if (paramString3 != null) {
            ((PreparedStatement)localObject11).setString(i7++, jdField_do(paramString3));
          }
          if (paramString4 != null) {
            ((PreparedStatement)localObject11).setString(i7++, jdField_do(paramString4));
          }
          if (paramString6 != null) {
            ((PreparedStatement)localObject11).setString(i7++, paramString6);
          }
          if ((i5 == 0) && ((localArrayList2.size() > 0) || (localArrayList3.size() > 0)))
          {
            int i8;
            if (localArrayList2.size() > 0) {
              for (i8 = 0; i8 < localArrayList2.size(); i8++) {
                ((PreparedStatement)localObject11).setString(i7++, (String)localArrayList2.get(i8));
              }
            }
            if (localArrayList3.size() > 0) {
              for (i8 = 0; i8 < localArrayList3.size(); i8++) {
                ((PreparedStatement)localObject11).setString(i7++, (String)localArrayList3.get(i8));
              }
            }
          }
          ((PreparedStatement)localObject11).setString(i7++, str22);
          localResultSet3 = DBUtil.executeQuery((PreparedStatement)localObject11, localStringBuffer2.toString());
          String str29 = null;
          String str30 = null;
          String str31 = null;
          String str32 = null;
          String str33 = null;
          String str34 = null;
          String str35 = null;
          String str36 = null;
          String str37 = null;
          while (localResultSet3.next())
          {
            Object localObject12;
            if ((paramInt2 != -1) && (paramInt2 <= localSummaryInfo.getGrandTotalSummaryItem().totalNumTransactions))
            {
              localObject12 = new HashMap();
              ((HashMap)localObject12).put("TRUNCATED", new Integer(paramInt2));
              paramReportResult.setProperties((HashMap)localObject12);
              break;
            }
            str29 = localResultSet3.getString(1);
            str30 = localResultSet3.getString(2);
            str31 = localResultSet3.getString(3);
            str34 = localResultSet3.getString(4);
            if (str34 != null) {
              str34 = jdField_if(str34, paramLocale, false);
            }
            str35 = localResultSet3.getString(5);
            str36 = localResultSet3.getString(6);
            str37 = localResultSet3.getString(7);
            str32 = localResultSet3.getString(8);
            str33 = localResultSet3.getString(9);
            try
            {
              localObject12 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
              str29 = localDateFormat.format(((SimpleDateFormat)localObject12).parse(str29));
            }
            catch (Exception localException3) {}
            try
            {
              SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
              str36 = localDateFormat.format(localSimpleDateFormat.parse(str36));
            }
            catch (Exception localException4) {}
            if (a(str30, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, str34))
            {
              bool = true;
              HashMap localHashMap4 = new HashMap();
              localHashMap4.put("StartTime", str29);
              localHashMap4.put("TransType", str30);
              localHashMap4.put("SubmittedBy", str31);
              localHashMap4.put("Status", str32);
              localHashMap4.put("Category", str33);
              localHashMap4.put("Amount", str34);
              localHashMap4.put("EntryLocationId", str35);
              localHashMap4.put("ProcessTime", str36);
              localHashMap4.put("LogId", str37);
              localHashMap4.put("BusinessName", str5);
              localHashMap4.put("CompanyName", str7);
              localHashMap4.put("DivisionName", str20);
              localHashMap4.put("LocationName", str21);
              localHashMap4.put("BatchType", str11);
              localHashMap4.put("AccountAmtMap", localHashMap2);
              localHashMap4.put("ConcentrateAccId", str9);
              localHashMap4.put("DisburseAccId", str10);
              localHashMap4.put("DivLocConAccId", str23);
              localHashMap4.put("DivLocDisAcctId", str24);
              localHashMap4.put("LocAcctId", str27 + " : " + str25);
              if (localHashMap1 != null)
              {
                ArrayList localArrayList4 = null;
                localArrayList4 = (ArrayList)localHashMap1.get(str32);
                if (localArrayList4 != null)
                {
                  localArrayList4.add(localHashMap4);
                }
                else
                {
                  localArrayList1.add(str32);
                  localArrayList4 = new ArrayList();
                  localArrayList4.add(localHashMap4);
                  localHashMap1.put(str32, localArrayList4);
                }
              }
            }
          }
          DBUtil.closeAll((PreparedStatement)localObject11, localResultSet3);
          localObject11 = null;
          localResultSet3 = null;
        }
        DBUtil.closeAll(localPreparedStatement2, localResultSet2);
        localPreparedStatement2 = null;
        localResultSet2 = null;
      }
      if (localArrayList1 != null)
      {
        localObject4 = localArrayList1.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject5 = (String)((Iterator)localObject4).next();
          localObject6 = new String((String)localObject5);
          localObject7 = null;
          if (localObject6 != null) {
            localObject6 = a((String)localObject6, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status");
          }
          localObject7 = new StringBuffer(" : ");
          ((StringBuffer)localObject7).append((String)localObject6);
          localObject8 = (ArrayList)localHashMap1.get(localObject5);
          localObject9 = new ReportResult(paramLocale);
          paramReportResult.addSubReport((ReportResult)localObject9);
          a((ReportResult)localObject9, ((StringBuffer)localObject7).toString(), (ArrayList)localObject8, localSummaryInfo, paramLocale, localHashMap3, paramReportCriteria);
        }
      }
      Object localObject4 = ReportConsts.getText(2517, paramLocale);
      a(paramReportResult, (String)localObject4, localSummaryInfo, localSummaryInfo.getGrandTotalSummaryItem(), paramLocale, paramReportCriteria);
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      throw localException2;
    }
    finally
    {
      DBUtil.closeAll(bd, (Connection)localObject1, (PreparedStatement)localObject2, (ResultSet)localObject3);
    }
    return bool;
  }
  
  private static boolean a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    String str = "ReportTransactionAdapter.checkCashConCompActAmountRange";
    if (paramString8 == null) {
      return false;
    }
    if (paramString7 != null)
    {
      if ((paramString7.equals("Debit")) && (paramString1.equals("DISBURSEMENT"))) {
        return false;
      }
      if ((paramString7.equals("Credit")) && (paramString1.equals("CONCENTRATION"))) {
        return false;
      }
    }
    boolean bool;
    if (paramString1.equals("CONCENTRATION"))
    {
      bool = true;
      if (paramString3 != null) {
        bool = a(paramString8, paramString3);
      }
      if ((bool) && (paramString4 != null)) {
        bool = jdField_do(paramString8, paramString4);
      }
      return bool;
    }
    if (paramString1.equals("DISBURSEMENT"))
    {
      bool = true;
      if (paramString5 != null) {
        bool = a(paramString8, paramString5);
      }
      if ((bool) && (paramString6 != null)) {
        bool = jdField_do(paramString8, paramString6);
      }
      return bool;
    }
    return false;
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    BigDecimal localBigDecimal1 = new BigDecimal(paramString1);
    BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
    return localBigDecimal1.compareTo(localBigDecimal2) >= 0;
  }
  
  private static boolean jdField_do(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    BigDecimal localBigDecimal1 = new BigDecimal(paramString1);
    BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
    return localBigDecimal1.compareTo(localBigDecimal2) <= 0;
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, String paramString, ReportCriteria paramReportCriteria)
    throws RptException
  {
    String str = "ReportTransactionAdapter.createCashConCompActCutOffHeading";
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, paramString, null);
  }
  
  private static void a(ReportResult paramReportResult1, ReportResult paramReportResult2, ReportResult paramReportResult3, HashMap paramHashMap, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2)
    throws RptException
  {
    String str = "ReportTransactionAdapter.createConcentrationDisbursementReport";
    if ((!paramBoolean1) && (!paramBoolean2)) {
      return;
    }
    if (paramBoolean1)
    {
      a(paramReportResult2, paramLocale, ReportConsts.getText(2518, paramLocale), null);
      a(paramReportResult2, paramLocale, -1, 2);
      jdField_if(paramReportResult2);
      jdField_if(paramReportResult2, paramHashMap, paramBoolean1, paramLocale);
    }
    if (paramBoolean2)
    {
      a(paramReportResult3, paramLocale, ReportConsts.getText(2519, paramLocale), null);
      a(paramReportResult3, paramLocale, -1, 2);
      jdField_if(paramReportResult3);
      a(paramReportResult3, paramHashMap, paramBoolean2, paramLocale);
    }
  }
  
  private static void jdField_if(ReportResult paramReportResult, HashMap paramHashMap, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    String str1 = "ReportTransactionAdapter.addRowsToConcentrationReport";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    Set localSet = paramHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str5 = (String)localIterator.next();
      Properties localProperties = null;
      if (str5 != null) {
        localProperties = (Properties)paramHashMap.get(str5);
      }
      if (localProperties != null)
      {
        str2 = (String)localProperties.get("AccountRoutingNum") + " : " + (String)localProperties.get("AccountNumber");
        if (localProperties.get("AccountNickname") != null) {
          str2 = str2 + " (" + localProperties.get("AccountNickname") + ")";
        }
        str3 = (String)localProperties.get("TransactionType");
        str4 = (String)localProperties.get("Amount");
      }
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      a(localReportDataItems.add(), str2);
      a(localReportDataItems.add(), str4);
      if (("CONCENTRATION".equals(str3)) && (paramBoolean)) {
        paramReportResult.addRow(localReportRow);
      }
    }
  }
  
  private static void a(ReportResult paramReportResult, HashMap paramHashMap, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    String str1 = "ReportTransactionAdapter.addRowsToDisbursementReport";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    Set localSet = paramHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str5 = (String)localIterator.next();
      Properties localProperties = null;
      if (str5 != null) {
        localProperties = (Properties)paramHashMap.get(str5);
      }
      if (localProperties != null)
      {
        str2 = (String)localProperties.get("AccountRoutingNum") + " : " + (String)localProperties.get("AccountNumber");
        if (localProperties.get("AccountNickname") != null) {
          str2 = str2 + " (" + localProperties.get("AccountNickname") + ")";
        }
        str3 = (String)localProperties.get("TransactionType");
        str4 = (String)localProperties.get("Amount");
      }
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      a(localReportDataItems.add(), str2);
      a(localReportDataItems.add(), str4);
      if (("DISBURSEMENT".equals(str3)) && (paramBoolean)) {
        paramReportResult.addRow(localReportRow);
      }
    }
  }
  
  private static void a(ReportResult paramReportResult, String paramString, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws RptException
  {
    if ((paramSummaryItem.totalNumTransactions == 0L) && (paramSummaryItem.totalNumCredits == 0L) && (paramSummaryItem.totalNumDebits == 0L)) {
      return;
    }
    jdField_if(paramReportResult, paramString, paramSummaryInfo, paramSummaryItem, paramLocale, paramReportCriteria);
  }
  
  private static void jdField_if(ReportResult paramReportResult, String paramString, SummaryInfo paramSummaryInfo, SummaryItem paramSummaryItem, Locale paramLocale, ReportCriteria paramReportCriteria)
    throws RptException
  {
    if ((paramSummaryItem.totalNumTransactions == 0L) && (paramSummaryItem.totalNumCredits == 0L) && (paramSummaryItem.totalNumDebits == 0L)) {
      return;
    }
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, paramString, null);
    a(localReportResult, paramLocale, 1, 5);
    a(localReportResult);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumTransactions);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumCredits);
    localReportDataItems.add().setData("" + paramSummaryItem.totalCreditsAmount);
    localReportDataItems.add().setData("" + paramSummaryItem.totalNumDebits);
    localReportDataItems.add().setData("" + paramSummaryItem.totalDebitsAmount);
    localReportResult.addRow(localReportRow);
    paramSummaryInfo.numRows += 1L;
  }
  
  private static void a(ReportResult paramReportResult, String paramString, ArrayList paramArrayList, SummaryInfo paramSummaryInfo, Locale paramLocale, HashMap paramHashMap, ReportCriteria paramReportCriteria)
    throws Exception
  {
    if ((paramString == null) || (paramArrayList.size() == 0)) {
      return;
    }
    Object localObject1 = "";
    Object localObject2 = "";
    Object localObject3 = "";
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str1 = localProperties.getProperty("REPORTTYPE");
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    paramSummaryInfo.resetStatusTotal();
    paramSummaryInfo.resetBusinessTotal();
    paramSummaryInfo.resetCompanyTotal();
    paramSummaryInfo.resetDivisionTotal();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    ReportResult localReportResult2 = new ReportResult(paramLocale);
    ReportResult localReportResult3 = new ReportResult(paramLocale);
    ReportResult localReportResult4 = new ReportResult(paramLocale);
    StringBuffer localStringBuffer = new StringBuffer("Status");
    localStringBuffer.append(paramString);
    a(paramReportResult, paramLocale, localStringBuffer.toString(), null);
    int i1 = 1;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localObject4 = (HashMap)localIterator.next();
      String str2 = (String)((HashMap)localObject4).get("BusinessName");
      String str3 = (String)((HashMap)localObject4).get("CompanyName");
      String str4 = (String)((HashMap)localObject4).get("DivisionName");
      String str5 = (String)((HashMap)localObject4).get("StartTime");
      String str6 = (String)((HashMap)localObject4).get("SubmittedBy");
      String str7 = (String)((HashMap)localObject4).get("Status");
      String str8 = (String)((HashMap)localObject4).get("Category");
      String str9 = (String)((HashMap)localObject4).get("TransType");
      String str10 = (String)((HashMap)localObject4).get("BatchType");
      String str11 = (String)((HashMap)localObject4).get("Amount");
      String str12 = (String)((HashMap)localObject4).get("EntryLocationId");
      String str13 = (String)((HashMap)localObject4).get("ProcessTime");
      String str14 = (String)((HashMap)localObject4).get("LogId");
      String str15 = (String)((HashMap)localObject4).get("ConcentrateAccId");
      String str16 = (String)((HashMap)localObject4).get("DisburseAccId");
      String str17 = (String)((HashMap)localObject4).get("DivLocConAccId");
      String str18 = (String)((HashMap)localObject4).get("DivLocDisAcctId");
      String str19 = (String)((HashMap)localObject4).get("LocAcctId");
      localHashMap1 = (HashMap)((HashMap)localObject4).get("AccountAmtMap");
      if (((((String)localObject3).length() > 0) && (!str4.equals(localObject3))) || ((((String)localObject2).length() > 0) && (!str3.equals(localObject2))) || ((((String)localObject1).length() > 0) && (!str2.equals(localObject1))))
      {
        localObject5 = ReportConsts.getText(2516, paramLocale) + " : " + (String)localObject2;
        jdField_if(localReportResult2, (String)localObject5, paramSummaryInfo, paramSummaryInfo.getCompanyTotalSummaryItem(), paramLocale, paramReportCriteria);
        i1 = 1;
      }
      if (((((String)localObject3).length() > 0) && (!str4.equals(localObject3))) || ((((String)localObject1).length() > 0) && (!str2.equals(localObject1))))
      {
        localObject5 = ReportConsts.getText(2520, paramLocale) + " : " + (String)localObject3;
        jdField_if(localReportResult3, (String)localObject5, paramSummaryInfo, paramSummaryInfo.getDivisionTotalSummaryItem(), paramLocale, paramReportCriteria);
        i1 = 1;
      }
      if ((((String)localObject1).length() > 0) && (!str2.equals(localObject1)) && (!str1.equals("Cash Concentration Activity Report")))
      {
        localObject5 = ReportConsts.getText(2609, paramLocale) + " : " + (String)localObject1;
        jdField_if(localReportResult1, (String)localObject5, paramSummaryInfo, paramSummaryInfo.getBusinessTotalSummaryItem(), paramLocale, paramReportCriteria);
        i1 = 1;
      }
      if (!str2.equals(localObject1))
      {
        localReportResult1 = new ReportResult(paramLocale);
        paramReportResult.addSubReport(localReportResult1);
        localObject1 = str2;
        localObject2 = "";
        localObject3 = "";
        if (!str1.equals("Cash Concentration Activity Report"))
        {
          localObject5 = ReportConsts.getText(2611, paramLocale) + " : " + str2;
          a(localReportResult1, paramLocale, (String)localObject5, null);
        }
        paramSummaryInfo.resetBusinessTotal();
        i1 = 1;
      }
      if (!str4.equals(localObject3))
      {
        localReportResult3 = new ReportResult(paramLocale);
        localReportResult1.addSubReport(localReportResult3);
        localObject3 = str4;
        localHashMap2 = localHashMap1;
        localObject2 = "";
        localObject5 = ReportConsts.getText(2613, paramLocale) + " : " + str4;
        a(localReportResult3, paramLocale, (String)localObject5, null);
        paramSummaryInfo.resetDivisionTotal();
        i1 = 1;
      }
      if (!str3.equals(localObject2))
      {
        localReportResult2 = new ReportResult(paramLocale);
        localReportResult3.addSubReport(localReportResult2);
        localObject2 = str3;
        localObject5 = ReportConsts.getText(2612, paramLocale) + " : " + str3;
        a(localReportResult2, paramLocale, (String)localObject5, null);
        paramSummaryInfo.resetCompanyTotal();
        localReportResult4 = new ReportResult(paramLocale);
        localReportResult2.addSubReport(localReportResult4);
        a(localReportResult4, paramLocale, -1, 9);
        jdField_for(localReportResult4);
        i1 = 1;
      }
      Object localObject5 = new ReportRow(paramLocale);
      if (i1 % 2 == 0) {
        ((ReportRow)localObject5).set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      ((ReportRow)localObject5).setDataItems(localReportDataItems);
      a(localReportDataItems.add(), str5);
      if (str13 == null) {
        str13 = "";
      }
      a(localReportDataItems.add(), str13);
      try
      {
        Integer localInteger = new Integer(str6);
        localObject6 = CustomerAdapter.getUserById(localInteger.intValue());
        if (localObject6 != null) {
          str6 = ((User)localObject6).getUserName();
        }
      }
      catch (Exception localException) {}
      a(localReportDataItems.add(), str6);
      a(localReportDataItems.add(), a(paramHashMap, str12));
      String str20 = null;
      Object localObject6 = null;
      if (str9.equals("CONCENTRATION"))
      {
        str21 = null;
        if (str10.equals("BatchBalancedBatch"))
        {
          if (str15 != null) {
            str21 = str15;
          }
        }
        else if ((str10.equals("EntryBalancedBatch")) && (str17 != null)) {
          str21 = str17;
        }
        localObject7 = (Properties)localHashMap1.get(str21);
        if (localObject7 != null) {
          str20 = (String)((Properties)localObject7).get("AccountRoutingNum") + " : " + (String)((Properties)localObject7).get("AccountNumber");
        }
        localObject6 = str19;
      }
      else if (str9.equals("DISBURSEMENT"))
      {
        str21 = null;
        if (str10.equals("BatchBalancedBatch"))
        {
          if (str16 != null) {
            str21 = str16;
          }
        }
        else if ((str10.equals("EntryBalancedBatch")) && (str18 != null)) {
          str21 = str18;
        }
        localObject7 = (Properties)localHashMap1.get(str21);
        if (localObject7 != null) {
          localObject6 = (String)((Properties)localObject7).get("AccountRoutingNum") + " : " + (String)((Properties)localObject7).get("AccountNumber");
        }
        str20 = str19;
      }
      a(localReportDataItems.add(), str20);
      a(localReportDataItems.add(), localObject6);
      String str21 = a(str9, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status");
      if (!"USER_ENTRY".equals(str8)) {
        str21 = str21 + " (" + a(str8, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.cashcon_status") + ")";
      }
      a(localReportDataItems.add(), str21);
      a(localReportDataItems.add(), str11);
      a(localReportDataItems.add(), str14);
      localReportResult4.addRow((ReportRow)localObject5);
      paramSummaryInfo.numRows += 1L;
      Object localObject7 = new SummaryItem();
      String str22;
      if (str9.equals("CONCENTRATION"))
      {
        str22 = "";
        if (str10.equals("BatchBalancedBatch"))
        {
          if (str15 != null) {
            str22 = str15;
          }
        }
        else if ((str10.equals("EntryBalancedBatch")) && (str17 != null)) {
          str22 = str17;
        }
        ((SummaryItem)localObject7).totalNumDebits = 1L;
        ((SummaryItem)localObject7).totalNumTransactions = 1L;
        ((SummaryItem)localObject7).totalDebitsAmount = new BigDecimal(str11);
      }
      else if (str9.equals("DISBURSEMENT"))
      {
        str22 = "";
        if (str10.equals("BatchBalancedBatch"))
        {
          if (str16 != null) {
            str22 = str16;
          }
        }
        else if ((str10.equals("EntryBalancedBatch")) && (str18 != null)) {
          str22 = str18;
        }
        ((SummaryItem)localObject7).totalNumCredits = 1L;
        ((SummaryItem)localObject7).totalNumTransactions = 1L;
        ((SummaryItem)localObject7).totalCreditsAmount = new BigDecimal(str11);
      }
      paramSummaryInfo.addGrandTotal(localObject7);
      paramSummaryInfo.addStatusTotal(localObject7);
      paramSummaryInfo.addBusinessTotal(localObject7);
      paramSummaryInfo.addCompanyTotal(localObject7);
      paramSummaryInfo.addDivisionTotal(localObject7);
      i1++;
    }
    Object localObject4 = ReportConsts.getText(2516, paramLocale) + " : " + (String)localObject2;
    jdField_if(localReportResult2, (String)localObject4, paramSummaryInfo, paramSummaryInfo.getCompanyTotalSummaryItem(), paramLocale, paramReportCriteria);
    localObject4 = ReportConsts.getText(2520, paramLocale) + " : " + (String)localObject3;
    jdField_if(localReportResult3, (String)localObject4, paramSummaryInfo, paramSummaryInfo.getDivisionTotalSummaryItem(), paramLocale, paramReportCriteria);
    if (!str1.equals("Cash Concentration Activity Report"))
    {
      localObject4 = ReportConsts.getText(2609, paramLocale) + " : " + (String)localObject1;
      jdField_if(localReportResult1, (String)localObject4, paramSummaryInfo, paramSummaryInfo.getBusinessTotalSummaryItem(), paramLocale, paramReportCriteria);
    }
    localObject4 = ReportConsts.getText(2610, paramLocale) + " " + paramString;
    jdField_if(paramReportResult, (String)localObject4, paramSummaryInfo, paramSummaryInfo.getStatusTotalSummaryItem(), paramLocale, paramReportCriteria);
  }
  
  private static void a(ReportResult paramReportResult)
    throws RptException
  {
    a(paramReportResult, 2521, null, 10, null);
    a(paramReportResult, 2522, null, 10, null);
    a(paramReportResult, 2524, null, 10, null);
    a(paramReportResult, 2523, null, 10, null);
    a(paramReportResult, 2525, null, 10, null);
  }
  
  private static void jdField_for(ReportResult paramReportResult)
    throws RptException
  {
    a(paramReportResult, 2526, null, 10, null);
    a(paramReportResult, 2530, null, 10, null);
    a(paramReportResult, 2527, null, 10, null);
    a(paramReportResult, 2528, null, 10, null);
    a(paramReportResult, 2561, null, 11, null);
    a(paramReportResult, 2560, null, 11, null);
    a(paramReportResult, 2559, null, 7, null);
    a(paramReportResult, 2529, null, 9, "RIGHT");
    a(paramReportResult, 2532, null, 12, "RIGHT");
  }
  
  private static void jdField_if(ReportResult paramReportResult)
    throws RptException
  {
    a(paramReportResult, 2533, null, 10, null);
    a(paramReportResult, 2534, null, 10, null);
  }
  
  private static void a(HashMap paramHashMap, String paramString1, String paramString2)
    throws Exception
  {
    try
    {
      BigDecimal localBigDecimal1 = new BigDecimal("0");
      Properties localProperties = (Properties)paramHashMap.get(paramString1);
      String str = (String)localProperties.get("Amount");
      BigDecimal localBigDecimal2 = new BigDecimal("0");
      if (str != null) {
        localBigDecimal2 = new BigDecimal(str);
      }
      BigDecimal localBigDecimal3 = new BigDecimal(paramString2);
      localBigDecimal1 = localBigDecimal2.add(localBigDecimal3);
      localProperties.put("Amount", String.valueOf(localBigDecimal1));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw localException;
    }
  }
  
  private static String a(HashMap paramHashMap, String paramString)
  {
    String str = "";
    if (paramString != null) {
      str = (String)paramHashMap.get(paramString);
    }
    return str;
  }
  
  private static String jdField_case(String paramString)
    throws ParseException
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      Date localDate = localDateFormat.parse(paramString);
      localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
      return localDateFormat.format(localDate);
    }
    return null;
  }
  
  private static boolean jdField_for(String paramString)
  {
    try
    {
      BigDecimal localBigDecimal = new BigDecimal(paramString);
      return true;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return false;
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale, String paramString1, String paramString2)
    throws RptException
  {
    ReportHeading localReportHeading;
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel(paramString1);
      localReportHeading.setJustification("LEFT");
      paramReportResult.setHeading(localReportHeading);
    }
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel(paramString2);
      localReportHeading.setJustification("LEFT");
      paramReportResult.setSectionHeading(localReportHeading);
    }
  }
  
  private static int a(Properties paramProperties, SecureUser paramSecureUser)
  {
    int i1 = paramSecureUser.getAffiliateIDValue();
    try
    {
      i1 = Integer.parseInt(Profile.getSearchCriteria(paramProperties, "Affiliate Bank", paramSecureUser.getAffiliateID()));
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return -1;
    }
    return i1;
  }
  
  private static void a(ArrayList paramArrayList, ReportResult paramReportResult, Locale paramLocale, int paramInt, ReportCriteria paramReportCriteria, String paramString, Connection paramConnection)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramLocale, -1, 4);
    a(localReportResult, 2513, null, 15, null);
    a(localReportResult, 2514, null, 15, null);
    a(localReportResult, 2557, null, 15, null);
    a(localReportResult, 2515, null, 15, null);
    int i1 = 0;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      if (localReportResult.getRows() != null) {
        i1 = localReportResult.getRows().size();
      }
      if ((paramInt != -1) && (paramInt <= i1))
      {
        localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(paramInt));
        paramReportResult.setProperties(localHashMap);
        break;
      }
      HashMap localHashMap = (HashMap)localIterator.next();
      str1 = (String)localHashMap.get("LocationID");
      str2 = (String)localHashMap.get("LastRequestTime");
      str3 = a(paramConnection, str1, paramString);
      if ((str3 == null) || (str3.equals(""))) {
        str3 = str2;
      }
      String str4 = jdField_char(str3);
      ReportRow localReportRow = new ReportRow(paramLocale);
      if (i1 % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      i1++;
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      a(localReportDataItems.add(), (String)localHashMap.get("DivisionName"));
      a(localReportDataItems.add(), (String)localHashMap.get("LocationName"));
      a(localReportDataItems.add(), (String)localHashMap.get("CompanyName"));
      a(localReportDataItems.add(), str4);
      localReportResult.addRow(localReportRow);
    }
  }
  
  private static void jdField_if(ArrayList paramArrayList, ReportResult paramReportResult, Locale paramLocale, int paramInt, ReportCriteria paramReportCriteria, String paramString, Connection paramConnection)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    ArrayList localArrayList = new ArrayList();
    String str8 = "1";
    String str9 = "2";
    String str10 = "3";
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      if ((paramInt != -1) && (paramInt <= localArrayList.size()))
      {
        localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(paramInt));
        paramReportResult.setProperties(localHashMap);
        break;
      }
      HashMap localHashMap = (HashMap)localIterator.next();
      str1 = (String)localHashMap.get("BusinessName");
      str2 = (String)localHashMap.get("CompanyName");
      str3 = (String)localHashMap.get("DivisionName");
      str4 = (String)localHashMap.get("LocationName");
      str5 = (String)localHashMap.get("LocationID");
      str6 = (String)localHashMap.get("LastRequestTime");
      str7 = a(paramConnection, str5, paramString);
      if ((str7 == null) || (str7.equals(""))) {
        str7 = str6;
      }
      String str11 = jdField_char(str7);
      if (localObject1 == null)
      {
        localObject3 = new ReportHeading(paramLocale);
        ((ReportHeading)localObject3).setLabel(str1);
        localReportResult.setHeading((ReportHeading)localObject3);
      }
      if ((localObject2 != null) && (!str2.equals(localObject2)))
      {
        a(localReportResult, (String)localObject2, localArrayList, str8, str9, str10, paramLocale, paramReportCriteria);
        localArrayList = new ArrayList();
      }
      if ((localObject1 != null) && (!str1.equals(localObject1)))
      {
        localReportResult = new ReportResult(paramLocale);
        paramReportResult.addSubReport(localReportResult);
        localObject3 = new ReportHeading(paramLocale);
        ((ReportHeading)localObject3).setLabel(str1);
        localReportResult.setHeading((ReportHeading)localObject3);
      }
      Object localObject3 = new HashMap();
      ((HashMap)localObject3).put(str8, str3);
      ((HashMap)localObject3).put(str9, str4);
      ((HashMap)localObject3).put(str10, str11);
      localArrayList.add(localObject3);
      localObject1 = str1;
      localObject2 = str2;
    }
    if ((localArrayList != null) && (localArrayList.size() != 0)) {
      a(localReportResult, str2, localArrayList, str8, str9, str10, paramLocale, paramReportCriteria);
    }
  }
  
  private static String jdField_do(String paramString)
    throws ParseException
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
      Date localDate = localDateFormat.parse(paramString);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate);
      localCalendar.set(10, 0);
      localDateFormat = DateFormatUtil.getFormatter("yyyyMMddHH");
      String str = localDateFormat.format(localCalendar.getTime());
      return str;
    }
    return null;
  }
  
  private static String a(Connection paramConnection, String paramString1, String paramString2)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.getInactiveRefDateForLocation";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = "";
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select CC_Entry.CreatedDate from CC_Entry where CC_Entry.LocationId = ? and CC_Entry.CreatedDate <= ? order by CC_Entry.CreatedDate desc");
      localPreparedStatement.setMaxRows(1);
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select CC_Entry.CreatedDate from CC_Entry where CC_Entry.LocationId = ? and CC_Entry.CreatedDate <= ? order by CC_Entry.CreatedDate desc");
      if (localResultSet.next()) {
        str2 = localResultSet.getString(1);
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      if (localResultSet != null) {
        DBUtil.closeResultSet(localResultSet);
      }
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
    return str2;
  }
  
  private static String a(SecureUser paramSecureUser, int paramInt)
    throws Exception
  {
    String str = "ReportTransactionAdapter.getBPWFIIDFromAffiliateBankId";
    AffiliateBank localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(paramSecureUser, paramInt);
    if (localAffiliateBank != null) {
      return localAffiliateBank.getFIBPWID();
    }
    return null;
  }
  
  private static IReportResult a(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    String str1 = "ReportTransactionAdapter.buildETFACHFileReport(): ";
    Locale localLocale1 = paramReportCriteria.getLocale();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1 = 1;
    ArrayList localArrayList1 = new ArrayList();
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str2 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(str2);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      localInteger = new Integer(-1);
    }
    String str3 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat1 = null;
    if (str3 == null) {
      localDateFormat1 = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat1 = DateFormatUtil.getFormatter(str3, localLocale1);
    }
    int i2 = -1;
    if (localInteger != null) {
      i2 = localInteger.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str4 = (String)localEnumeration.nextElement();
      String str5 = localProperties2.getProperty(str4);
      if ((str5 == null) || (str5.length() == 0)) {
        localProperties2.remove(str4);
      }
    }
    int i3 = 0;
    try
    {
      i3 = Integer.parseInt(Profile.getSearchCriteria(localProperties2, "Affiliate Bank", "0"));
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      i3 = 0;
    }
    jdField_do(paramReportCriteria, localProperties2);
    String str6 = (String)localProperties2.get("ACHFileDetailLevel");
    if ((str6 == null) || (str6.trim().length() == 0)) {
      str6 = "File Summary";
    }
    String str7 = (String)localProperties2.get("CutOffTimes");
    Object localObject2;
    Object localObject3;
    Object localObject4;
    String str8;
    int i4;
    if (str7 != null) {
      if (str7.indexOf("AllCutOffTimes") >= 0)
      {
        paramReportCriteria.setDisplayValue("CutOffTimes", ReportConsts.getText(10050, paramReportCriteria.getLocale()));
      }
      else
      {
        i1 = 0;
        localObject1 = new StringTokenizer(str7, ",", false);
        while (((StringTokenizer)localObject1).hasMoreTokens())
        {
          localObject2 = ((StringTokenizer)localObject1).nextToken().trim();
          localArrayList1.add(localObject2);
        }
        localObject2 = null;
        localObject3 = new CutOffTime();
        localObject4 = (String[])localArrayList1.toArray(new String[0]);
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        paramHashMap.put("CutOffIDList", localObject4);
        try
        {
          localObject2 = PaymentsAdminHandler.getCutOffTimes(paramSecureUser, (CutOffTime)localObject3, paramHashMap);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
        if (localObject2 != null)
        {
          str8 = "";
          Iterator localIterator = ((CutOffTimes)localObject2).iterator();
          i4 = 1;
          while (localIterator.hasNext())
          {
            localObject3 = (CutOffTime)localIterator.next();
            if (localArrayList1.contains(((CutOffTime)localObject3).getCutOffId()))
            {
              if (i4 == 0) {
                str8 = str8 + ", ";
              }
              i4 = 0;
              str8 = str8 + ((CutOffTime)localObject3).getDayOfWeekString() + ((CutOffTime)localObject3).getTimeOfDay();
            }
          }
          paramReportCriteria.setDisplayValue("CutOffTimes", str8);
        }
      }
    }
    Object localObject1 = null;
    try
    {
      localConnection = DBUtil.getConnection(bd, true, 2);
      localObject2 = localProperties2.getProperty("StartDate");
      localObject3 = localProperties2.getProperty("EndDate");
      localObject4 = null;
      str8 = null;
      if ((localObject2 != null) && (((String)localObject2).length() > 0)) {
        try
        {
          localObject4 = jdField_case((String)localObject2).toString();
        }
        catch (ParseException localParseException1)
        {
          throw new BCReportException(str1, 13, "Invalid format for startDate: " + (String)localObject2);
        }
      }
      if ((localObject3 != null) && (((String)localObject3).length() > 0)) {
        try
        {
          str8 = jdField_case((String)localObject3).toString();
        }
        catch (ParseException localParseException2)
        {
          throw new BCReportException(str1, 13, "Invalid format for endDate: " + (String)localObject3);
        }
      }
      StringBuffer localStringBuffer = new StringBuffer(ax);
      if (i3 != 0) {
        localStringBuffer.append(u);
      }
      if ((localObject4 != null) && (((String)localObject4).trim().length() != 0)) {
        localStringBuffer.append(aR);
      }
      if ((str8 != null) && (str8.trim().length() != 0)) {
        localStringBuffer.append(a3);
      }
      localStringBuffer.append(aJ);
      localStringBuffer.append(I);
      localStringBuffer.append(ay);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      i4 = 1;
      if (i2 != -1) {
        localPreparedStatement.setMaxRows(i2 + 1);
      }
      if (i3 != 0) {
        localPreparedStatement.setInt(i4++, i3);
      }
      if ((localObject4 != null) && (((String)localObject4).trim().length() != 0)) {
        localPreparedStatement.setString(i4++, (String)localObject4);
      }
      if ((str8 != null) && (str8.trim().length() != 0)) {
        localPreparedStatement.setString(i4++, str8);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Locale localLocale2 = paramSecureUser.getLocale();
      localObject1 = new ReportResult(localLocale2);
      ((ReportResult)localObject1).init(paramReportCriteria);
      ((ReportResult)localObject1).setHeading(null);
      a((ReportResult)localObject1, localLocale2, -1, 0);
      ReportResult localReportResult = null;
      ArrayList localArrayList2 = new ArrayList();
      ArrayList localArrayList3 = new ArrayList();
      Hashtable localHashtable = new Hashtable();
      String str9 = null;
      String str10 = null;
      String str11 = null;
      String str12 = null;
      String str13 = null;
      String str14 = null;
      String str15 = null;
      String str16 = null;
      String str17 = null;
      String str18 = null;
      String str19 = null;
      String str20 = null;
      String str21 = null;
      Object localObject5 = null;
      String str22 = null;
      String str23 = null;
      String str24 = null;
      String str25 = null;
      SummaryInfo localSummaryInfo = new SummaryInfo();
      localSummaryInfo.setLocale(localLocale1);
      localSummaryInfo.setUser(paramSecureUser);
      while (localResultSet.next())
      {
        str23 = localResultSet.getString(1);
        str24 = localResultSet.getString(2);
        if (localArrayList2.contains(str24))
        {
          localObject6 = (HashMap)localHashtable.get(str24);
        }
        else
        {
          localObject6 = new HashMap();
          localArrayList2.add(str24);
          localHashtable.put(str24, localObject6);
        }
        str9 = localResultSet.getString(3);
        str10 = localResultSet.getString(4);
        str11 = localResultSet.getString(5);
        str12 = localResultSet.getString(6);
        str13 = localResultSet.getString(7);
        str14 = localResultSet.getString(8);
        str15 = localResultSet.getString(9);
        str16 = localResultSet.getString(10);
        str17 = localResultSet.getString(11);
        str18 = localResultSet.getString(12);
        str19 = localResultSet.getString(13);
        str20 = localResultSet.getString(14);
        str25 = localResultSet.getString(15);
        str22 = localResultSet.getString(16);
        if (((HashMap)localObject6).get("affiliateBankName") == null) {
          ((HashMap)localObject6).put("affiliateBankName", str23);
        }
        if (((HashMap)localObject6).get("logDate") == null) {
          ((HashMap)localObject6).put("logDate", str9);
        }
        if (((HashMap)localObject6).get("schHistFileName") == null) {
          ((HashMap)localObject6).put("schHistFileName", str10);
        }
        if (((HashMap)localObject6).get("cutoffDay") == null) {
          ((HashMap)localObject6).put("cutoffDay", str11);
        }
        if (((HashMap)localObject6).get("thisCutOffTime") == null) {
          ((HashMap)localObject6).put("thisCutOffTime", str12);
        }
        if (((HashMap)localObject6).get("cutoffID") == null) {
          ((HashMap)localObject6).put("cutoffID", str13);
        }
        if (((HashMap)localObject6).get("etfACHFileId") == null) {
          ((HashMap)localObject6).put("etfACHFileId", str14);
        }
        if (((HashMap)localObject6).get("etfACHFileCreateDate") == null) {
          ((HashMap)localObject6).put("etfACHFileCreateDate", str15);
        }
        if (((HashMap)localObject6).get("etfACHFileTotalDebit") == null) {
          ((HashMap)localObject6).put("etfACHFileTotalDebit", str16);
        }
        if (((HashMap)localObject6).get("etfACHFileTotalCredit") == null) {
          ((HashMap)localObject6).put("etfACHFileTotalCredit", str17);
        }
        if (((HashMap)localObject6).get("etfACHFileNumberOfCredits") == null) {
          ((HashMap)localObject6).put("etfACHFileNumberOfCredits", str18);
        }
        if (((HashMap)localObject6).get("etfACHFileNumberOfDebits") == null) {
          ((HashMap)localObject6).put("etfACHFileNumberOfDebits", str19);
        }
        if (((HashMap)localObject6).get("fileStatus") == null) {
          ((HashMap)localObject6).put("fileStatus", str20);
        }
        if (((HashMap)localObject6).get("eventTrigger") == null) {
          ((HashMap)localObject6).put("eventTrigger", str25);
        }
        if (((HashMap)localObject6).get("exportFileName") == null) {
          ((HashMap)localObject6).put("exportFileName", str22);
        }
      }
      Object localObject6 = localArrayList2.iterator();
      Object localObject7;
      Object localObject8;
      while (((Iterator)localObject6).hasNext())
      {
        str24 = (String)((Iterator)localObject6).next();
        localObject7 = (HashMap)localHashtable.get(str24);
        if (((HashMap)localObject7).get("affiliateBankName") != null) {
          str23 = (String)((HashMap)localObject7).get("affiliateBankName");
        } else {
          str23 = null;
        }
        if (((HashMap)localObject7).get("logDate") != null) {
          str9 = (String)((HashMap)localObject7).get("logDate");
        } else {
          str9 = null;
        }
        if (((HashMap)localObject7).get("schHistFileName") != null) {
          str10 = (String)((HashMap)localObject7).get("schHistFileName");
        } else {
          str10 = null;
        }
        if (((HashMap)localObject7).get("cutoffDay") != null) {
          str11 = (String)((HashMap)localObject7).get("cutoffDay");
        } else {
          str11 = null;
        }
        if (((HashMap)localObject7).get("thisCutOffTime") != null) {
          str12 = (String)((HashMap)localObject7).get("thisCutOffTime");
        } else {
          str12 = null;
        }
        if (((HashMap)localObject7).get("cutoffID") != null) {
          str13 = (String)((HashMap)localObject7).get("cutoffID");
        } else {
          str13 = null;
        }
        if (((HashMap)localObject7).get("etfACHFileId") != null) {
          str14 = (String)((HashMap)localObject7).get("etfACHFileId");
        } else {
          str14 = null;
        }
        if (((HashMap)localObject7).get("etfACHFileCreateDate") != null) {
          str15 = (String)((HashMap)localObject7).get("etfACHFileCreateDate");
        } else {
          str15 = null;
        }
        if (((HashMap)localObject7).get("etfACHFileTotalDebit") != null) {
          str16 = (String)((HashMap)localObject7).get("etfACHFileTotalDebit");
        } else {
          str16 = null;
        }
        if (((HashMap)localObject7).get("etfACHFileTotalCredit") != null) {
          str17 = (String)((HashMap)localObject7).get("etfACHFileTotalCredit");
        } else {
          str17 = null;
        }
        if (((HashMap)localObject7).get("etfACHFileNumberOfCredits") != null) {
          str18 = (String)((HashMap)localObject7).get("etfACHFileNumberOfCredits");
        } else {
          str18 = null;
        }
        if (((HashMap)localObject7).get("etfACHFileNumberOfDebits") != null) {
          str19 = (String)((HashMap)localObject7).get("etfACHFileNumberOfDebits");
        } else {
          str19 = null;
        }
        if (((HashMap)localObject7).get("fileStatus") != null) {
          str20 = (String)((HashMap)localObject7).get("fileStatus");
        } else {
          str20 = null;
        }
        if (((HashMap)localObject7).get("eventTrigger") != null) {
          str25 = (String)((HashMap)localObject7).get("eventTrigger");
        } else {
          str25 = null;
        }
        if (((HashMap)localObject7).get("exportFileName") != null) {
          str22 = (String)((HashMap)localObject7).get("exportFileName");
        } else {
          str22 = null;
        }
        if ((i2 != -1) && (localSummaryInfo.numRows >= i2))
        {
          localObject8 = new HashMap();
          ((HashMap)localObject8).put("TRUNCATED", new Integer(i2));
          ((ReportResult)localObject1).setProperties((HashMap)localObject8);
          break;
        }
        if (str16 != null) {
          str16 = a(str16, localLocale1, false);
        } else {
          str16 = "0.00";
        }
        if (str17 != null) {
          str17 = a(str17, localLocale1, false);
        } else {
          str17 = "0.00";
        }
        if (str10 == null) {
          str10 = "No File Name";
        }
        localObject8 = DateFormatUtil.getFormatter("MM/dd/yyyy");
        DateFormat localDateFormat2 = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
        String str26 = "";
        if ((str11 != null) && (!str11.equals("0"))) {
          str26 = " (" + ResourceUtil.getString(new StringBuffer().append("Day").append(str11).toString(), "com.ffusion.beans.affiliatebank.resources", localLocale2) + ") ";
        }
        if (str12 == null)
        {
          str12 = str9;
          if ((str25 == null) || ("Internal".equalsIgnoreCase(str25))) {
            str21 = str12 + " " + ReportConsts.getText(2054, localLocale1);
          } else {
            str21 = str12 + " " + ReportConsts.getText(2053, localLocale1);
          }
        }
        else
        {
          str21 = ((DateFormat)localObject8).format(localDateFormat2.parse(str9)) + " - " + str12 + str26;
        }
        if ((i1 != 0) || ((str13 != null) && ((str13 == null) || (localArrayList1.contains(str13)))))
        {
          if ((localObject5 != null) && (!str21.equals(localObject5)))
          {
            localObject9 = new ReportResult(localLocale1);
            localReportResult.addSubReport((ReportResult)localObject9);
            a((ReportResult)localObject9, localLocale1, -1, 4);
            a((ReportResult)localObject9, localLocale1, ReportConsts.getText(2019, localLocale1) + " " + (String)localObject5, localSummaryInfo, localSummaryInfo.getGrandTotalSummaryItem());
            localSummaryInfo.resetGrandTotal();
          }
          Object localObject9 = new BigDecimal(str16 == null ? "0" : str16);
          BigDecimal localBigDecimal = new BigDecimal(str17 == null ? "0" : str17);
          int i5 = str19 == null ? 0 : Integer.valueOf(str19).intValue();
          int i6 = str18 == null ? 0 : Integer.valueOf(str18).intValue();
          SummaryItem localSummaryItem = new SummaryItem((BigDecimal)localObject9, i5, localBigDecimal, i6);
          localSummaryInfo.addGrandTotal(localSummaryItem);
          localSummaryInfo.resetACHTotal();
          localSummaryInfo.addACHTotal(localSummaryItem);
          if (!localArrayList3.contains(str21))
          {
            localArrayList3.add(str21);
            localReportResult = new ReportResult(localLocale1);
            ((ReportResult)localObject1).addSubReport(localReportResult);
            localObject10 = new ReportHeading(localLocale1);
            ((ReportHeading)localObject10).setLabel(ReportConsts.getText(2600, localLocale1) + ": " + str21);
            localReportResult.setHeading((ReportHeading)localObject10);
          }
          Object localObject10 = new ReportResult(localLocale1);
          localReportResult.addSubReport((ReportResult)localObject10);
          ReportHeading localReportHeading = new ReportHeading(localLocale1);
          localReportHeading.setLabel(ReportConsts.getText(2601, localLocale1) + ": " + str10 + " (" + ReportConsts.getText(2603, localLocale1) + ": " + str14 + ")");
          ((ReportResult)localObject10).setHeading(localReportHeading);
          a(localReportResult, localProperties2, i3, str24, str6, str14, str15, localSummaryInfo, str20, str22, localDateFormat1);
          localObject5 = str21;
        }
      }
      if (localReportResult == null)
      {
        localObject7 = new ReportResult(localLocale2);
        ((ReportResult)localObject1).addSubReport((ReportResult)localObject7);
        localObject8 = new ReportHeading(localLocale1);
        ((ReportHeading)localObject8).setLabel(ReportConsts.getText(2503, localLocale1));
        ((ReportResult)localObject7).setHeading((ReportHeading)localObject8);
      }
      else
      {
        localObject7 = new ReportResult(localLocale1);
        localReportResult.addSubReport((ReportResult)localObject7);
        a((ReportResult)localObject7, localLocale1, -1, 4);
        a((ReportResult)localObject7, localLocale1, ReportConsts.getText(2019, localLocale1) + " " + str21, localSummaryInfo, localSummaryInfo.getGrandTotalSummaryItem());
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
      if (localObject1 != null) {
        ((ReportResult)localObject1).setError(localException);
      }
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
      try
      {
        if (localObject1 != null) {
          ((ReportResult)localObject1).fini();
        }
      }
      catch (RptException localRptException)
      {
        String str27 = "Unable to generate the report.";
        localRptException.printStackTrace(System.out);
      }
    }
    return localObject1;
  }
  
  private static void a(ReportResult paramReportResult, Properties paramProperties, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, SummaryInfo paramSummaryInfo, String paramString5, String paramString6, DateFormat paramDateFormat)
  {
    String str1 = "ReportTransactionAdapter.processETFFileReport";
    Locale localLocale = paramReportResult.getLocale();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    ResultSet localResultSet1 = null;
    ArrayList localArrayList1 = new ArrayList();
    int[] arrayOfInt1 = { 2051, 2030, 2031, 2032, 2033, 2034 };
    int[] arrayOfInt2 = { 10, 20, 10, 10, 10, 10 };
    ArrayList localArrayList2 = new ArrayList();
    for (int i1 = 0; i1 < arrayOfInt1.length; i1++)
    {
      int i2 = arrayOfInt2[i1];
      int i3 = arrayOfInt1[i1];
      ReportColumn localReportColumn1 = new ReportColumn(localLocale);
      ArrayList localArrayList4 = new ArrayList();
      localArrayList4.add(ReportConsts.getColumnName(i3, localLocale));
      localReportColumn1.setLabels(localArrayList4);
      localReportColumn1.setDataType("java.lang.String");
      localReportColumn1.setWidthAsPercent(i2);
      localArrayList2.add(localReportColumn1);
    }
    int[] arrayOfInt3 = { 2038, 2040, 2041, 2042, 2032, 2034, 2043, 2035 };
    int[] arrayOfInt4 = { 18, 8, 11, 9, 13, 13, 8, 12 };
    ArrayList localArrayList3 = new ArrayList();
    for (int i4 = 0; i4 < arrayOfInt3.length; i4++)
    {
      int i5 = arrayOfInt4[i4];
      int i6 = arrayOfInt3[i4];
      ReportColumn localReportColumn2 = new ReportColumn(localLocale);
      ArrayList localArrayList6 = new ArrayList();
      localArrayList6.add(ReportConsts.getColumnName(i6, localLocale));
      if (i6 == 2032) {
        localArrayList6.add(ReportConsts.getColumnName(2031, localLocale));
      }
      if (i6 == 2034) {
        localArrayList6.add(ReportConsts.getColumnName(2033, localLocale));
      }
      if (i6 == 2038) {
        localArrayList6.add(ReportConsts.getColumnName(2039, localLocale));
      }
      if (i6 == 2036) {
        localArrayList6.add(ReportConsts.getColumnName(2037, localLocale));
      }
      localReportColumn2.setLabels(localArrayList6);
      localReportColumn2.setDataType("java.lang.String");
      localReportColumn2.setWidthAsPercent(i5);
      localArrayList3.add(localReportColumn2);
    }
    int[] arrayOfInt5 = { 2044, 2045, 2046, 2047, 2048, 2049, 2050, 2036 };
    int[] arrayOfInt6 = { 8, 12, 12, 16, 12, 8, 14, 18 };
    ArrayList localArrayList5 = new ArrayList();
    for (int i7 = 0; i7 < arrayOfInt5.length; i7++)
    {
      int i8 = arrayOfInt6[i7];
      int i9 = arrayOfInt5[i7];
      localObject1 = new ReportColumn(localLocale);
      localObject2 = new ArrayList();
      ((ArrayList)localObject2).add(ReportConsts.getColumnName(i9, localLocale));
      if (i9 == 2036) {
        ((ArrayList)localObject2).add(ReportConsts.getColumnName(2037, localLocale));
      }
      ((ReportColumn)localObject1).setLabels((ArrayList)localObject2);
      ((ReportColumn)localObject1).setDataType("java.lang.String");
      ((ReportColumn)localObject1).setWidthAsPercent(i8);
      localArrayList5.add(localObject1);
    }
    ReportResult localReportResult1 = null;
    ReportResult localReportResult2 = null;
    ReportResult localReportResult3 = null;
    Object localObject1 = null;
    paramSummaryInfo.resetBusinessTotal();
    Object localObject2 = null;
    String str2 = null;
    StringBuffer localStringBuffer = new StringBuffer("select distinct ETF_ACHBatch.TotalCredits, ETF_ACHBatch.TotalDebits, ETF_ACHBatch.StdEntryClassCode, ETF_ACHBatch.BatchId, ETF_ACHBatch.EffectiveEntryDate, ETF_ACHBatch.BatchNumber, ETF_ACHBatch.CompanyName, ETF_ACHBatch.CompanyIdentification, BPW_Customer.FirstName, ETF_ACHBatch.CustomerIdInt, ETF_ACHBatch.NumberOfDebits, ETF_ACHBatch.NumberOfCredits, ETF_ACHBatch.EntryAddendaCount from ETF_ACHBatch, BPW_Customer, affiliate_bank where BPW_Customer.CustomerID=ETF_ACHBatch.CustomerId and ETF_ACHBatch.FileId = ? ");
    String str3 = "select TransactionCode, RecvDFIIdentification, DFIAccountNumber, RcvCompIndvName, IdentificationNumber, Amount, TraceNumber, LogId from ETF_ACHEntry where BatchId = ? order by EntryId";
    String str4 = "select cust_id from customer_directory where directory_id = ?";
    try
    {
      localReportResult3 = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult3);
      ReportDataDimensions localReportDataDimensions1 = new ReportDataDimensions(localLocale);
      localReportDataDimensions1.setNumRows(-1);
      localReportDataDimensions1.setNumColumns(localArrayList2.size());
      localReportResult3.setDataDimensions(localReportDataDimensions1);
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      for (int i10 = 0; i10 < localArrayList2.size(); i10++) {
        localReportResult3.addColumn((ReportColumn)localArrayList2.get(i10));
      }
      Object localObject3;
      Object localObject4;
      for (i10 = 0; i10 < localArrayList2.size(); i10++)
      {
        localObject3 = "";
        switch (i10)
        {
        case 0: 
          localObject3 = paramString4;
          break;
        case 1: 
          localObject3 = paramString6;
          break;
        case 2: 
          localObject3 = "" + paramSummaryInfo.getACHTotalSummaryItem().totalNumDebits;
          break;
        case 3: 
          localObject3 = "" + paramSummaryInfo.getACHTotalSummaryItem().totalDebitsAmount;
          break;
        case 4: 
          localObject3 = "" + paramSummaryInfo.getACHTotalSummaryItem().totalNumCredits;
          break;
        case 5: 
          localObject3 = "" + paramSummaryInfo.getACHTotalSummaryItem().totalCreditsAmount;
          break;
        }
        localObject4 = localReportDataItems.add();
        if (localObject3 == null) {
          localObject3 = "";
        }
        ((ReportDataItem)localObject4).setData(localObject3);
      }
      localReportResult3.addRow(localReportRow);
      paramSummaryInfo.numRows += 1L;
      if ((paramString2.equalsIgnoreCase("Batch Summary")) || (paramString2.equalsIgnoreCase("Detail Entry"))) {
        try
        {
          localConnection = DBUtil.getConnection(bd, true, 2);
          if (paramInt > 0) {
            localStringBuffer.append(" and BPW_Customer.FIID = affiliate_bank.bpw_fi_id and affiliate_bank.affiliate_bank_id = ? ");
          }
          localStringBuffer.append(" order by BPW_Customer.FirstName");
          localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
          i10 = 1;
          localPreparedStatement1.setString(i10++, paramString3);
          if (paramInt > 0) {
            localPreparedStatement1.setInt(i10++, paramInt);
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer.toString());
          Object localObject5;
          Object localObject6;
          Object localObject7;
          Object localObject8;
          Object localObject10;
          Object localObject11;
          Object localObject12;
          int i15;
          String str6;
          Object localObject13;
          Object localObject14;
          while (localResultSet1.next())
          {
            localObject3 = new HashMap();
            localArrayList1.add(localObject3);
            localObject4 = localResultSet1.getString(7);
            localObject5 = localResultSet1.getString(8);
            localObject6 = localResultSet1.getString(3);
            localObject7 = localResultSet1.getString(5);
            try
            {
              int i12 = Integer.parseInt((String)localObject7);
              i12 /= 100;
              localObject9 = DateFormatUtil.getFormatter("yyyyMMdd");
              localObject7 = paramDateFormat.format(((DateFormat)localObject9).parse("" + i12));
            }
            catch (Exception localException3) {}
            localObject8 = localResultSet1.getString(13);
            Object localObject9 = localResultSet1.getString(2);
            String str5 = localResultSet1.getString(11);
            localObject10 = localResultSet1.getString(1);
            localObject11 = localResultSet1.getString(12);
            localObject12 = localResultSet1.getString(6);
            i15 = localResultSet1.getInt(10);
            localObject2 = localResultSet1.getString(9);
            str6 = localResultSet1.getString(4);
            localObject13 = null;
            ResultSet localResultSet2 = null;
            localObject13 = DBUtil.prepareStatement(localConnection, str4.toString());
            ((PreparedStatement)localObject13).setInt(1, i15);
            str2 = "none";
            localResultSet2 = DBUtil.executeQuery((PreparedStatement)localObject13, str4.toString());
            if (localResultSet2.next()) {
              str2 = localResultSet2.getString(1);
            }
            DBUtil.closeAll((PreparedStatement)localObject13, localResultSet2);
            localObject13 = null;
            localResultSet2 = null;
            if (localObject9 != null) {
              localObject9 = jdField_if((String)localObject9, localLocale, false);
            } else {
              localObject9 = "0.00";
            }
            if (localObject10 != null) {
              localObject10 = jdField_if((String)localObject10, localLocale, false);
            } else {
              localObject10 = "0.00";
            }
            ((HashMap)localObject3).put("companyName", localObject4);
            ((HashMap)localObject3).put("companyId", localObject5);
            ((HashMap)localObject3).put("SECC", localObject6);
            ((HashMap)localObject3).put("effEntryDate", localObject7);
            ((HashMap)localObject3).put("entryAddCount", localObject8);
            ((HashMap)localObject3).put("totalDebits", localObject9);
            ((HashMap)localObject3).put("numOfDebits", str5);
            ((HashMap)localObject3).put("totalCredits", localObject10);
            ((HashMap)localObject3).put("numOfCredits", localObject11);
            ((HashMap)localObject3).put("batchNumber", localObject12);
            ((HashMap)localObject3).put("custId", str2);
            ((HashMap)localObject3).put("businessName", localObject2);
            ((HashMap)localObject3).put("batchId", str6);
            localObject14 = new ArrayList();
            ((HashMap)localObject3).put("Entries", localObject14);
            PreparedStatement localPreparedStatement2 = null;
            ResultSet localResultSet3 = null;
            if (paramString2.equals("Detail Entry"))
            {
              localPreparedStatement2 = DBUtil.prepareStatement(localConnection, str3.toString());
              localPreparedStatement2.setString(1, str6);
              localResultSet3 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
              while (localResultSet3.next())
              {
                HashMap localHashMap = new HashMap();
                String str7 = localResultSet3.getString(1);
                String str8 = localResultSet3.getString(6);
                if (str8 != null) {
                  str8 = jdField_if(str8, localLocale, false);
                } else {
                  str8 = "0.00";
                }
                localHashMap.put("TransCode", str7);
                String str9 = localResultSet3.getString(2);
                if ((str9 != null) && (str9.length() == 8)) {
                  str9 = str9 + RoutingNumberUtil.getRoutingNumber_CheckDigit(str9);
                }
                localHashMap.put("RDFI_ID", str9);
                localHashMap.put("DFIAcctNum", localResultSet3.getString(3));
                localHashMap.put("RecCoName", localResultSet3.getString(4));
                localHashMap.put("IdentNum", localResultSet3.getString(5));
                localHashMap.put("Amount", str8);
                localHashMap.put("TraceNum", localResultSet3.getString(7));
                localHashMap.put("LogId", localResultSet3.getString(8));
                localHashMap.put("BatchId", str6);
                ((ArrayList)localObject14).add(localHashMap);
              }
            }
            DBUtil.closeAll(localPreparedStatement2, localResultSet3);
            localPreparedStatement2 = null;
            localResultSet3 = null;
          }
          if (localArrayList1.size() > 0)
          {
            localObject1 = new ReportResult(localLocale);
            paramReportResult.addSubReport((ReportResult)localObject1);
            localObject3 = new ReportHeading(localLocale);
            ((ReportHeading)localObject3).setLabel(ReportConsts.getText(2056, localLocale) + " " + (String)localObject2 + " (" + str2 + ")");
            ((ReportResult)localObject1).setHeading((ReportHeading)localObject3);
            localReportResult1 = new ReportResult(localLocale);
            paramReportResult.addSubReport(localReportResult1);
            localReportResult1.setHeading(null);
            for (int i11 = 0; i11 < localArrayList1.size(); i11++)
            {
              localObject5 = new ReportResult(localLocale);
              localReportResult1.addSubReport((ReportResult)localObject5);
              ((ReportResult)localObject5).setHeading(null);
              a((ReportResult)localObject5, localLocale, -1, localArrayList3.size());
              localObject6 = (HashMap)localArrayList1.get(i11);
              localObject7 = new BigDecimal((String)((HashMap)localObject6).get("totalDebits") == null ? "0" : (String)((HashMap)localObject6).get("totalDebits"));
              localObject8 = new BigDecimal((String)((HashMap)localObject6).get("totalCredits") == null ? "0" : (String)((HashMap)localObject6).get("totalCredits"));
              int i13 = (String)((HashMap)localObject6).get("numOfDebits") == null ? 0 : Integer.valueOf((String)((HashMap)localObject6).get("numOfDebits")).intValue();
              int i14 = (String)((HashMap)localObject6).get("numOfCredits") == null ? 0 : Integer.valueOf((String)((HashMap)localObject6).get("numOfCredits")).intValue();
              localObject10 = new SummaryItem((BigDecimal)localObject7, i13, (BigDecimal)localObject8, i14);
              paramSummaryInfo.addBusinessTotal(localObject10);
              localObject11 = new ReportRow(localLocale);
              localObject12 = new ReportDataItems(localLocale);
              ((ReportRow)localObject11).setDataItems((ReportDataItems)localObject12);
              for (i15 = 0; i15 < localArrayList3.size(); i15++)
              {
                ((ReportResult)localObject5).addColumn((ReportColumn)localArrayList3.get(i15));
                str6 = "" + i11;
                switch (i15)
                {
                case 0: 
                  str6 = (String)((HashMap)localObject6).get("companyName") + ", " + (String)((HashMap)localObject6).get("companyId");
                  break;
                case 1: 
                  str6 = (String)((HashMap)localObject6).get("SECC");
                  break;
                case 2: 
                  str6 = (String)((HashMap)localObject6).get("effEntryDate");
                  break;
                case 3: 
                  str6 = (String)((HashMap)localObject6).get("entryAddCount");
                  break;
                case 4: 
                  str6 = (String)((HashMap)localObject6).get("totalDebits") + ",  " + (String)((HashMap)localObject6).get("numOfDebits");
                  break;
                case 5: 
                  str6 = (String)((HashMap)localObject6).get("totalCredits") + ",  " + (String)((HashMap)localObject6).get("numOfCredits");
                  break;
                case 6: 
                  str6 = (String)((HashMap)localObject6).get("batchNumber");
                  break;
                case 7: 
                  str6 = a(paramString5, localLocale, "com.ffusion.beans.reporting.transfer_status");
                  break;
                }
                a(((ReportDataItems)localObject12).add(), str6);
              }
              ((ReportResult)localObject5).addRow((ReportRow)localObject11);
              paramSummaryInfo.numRows += 1L;
              ArrayList localArrayList7 = (ArrayList)((HashMap)localObject6).get("Entries");
              if ((localArrayList7 != null) && (localArrayList7.size() > 0))
              {
                localReportResult2 = new ReportResult(localLocale);
                localReportResult1.addSubReport(localReportResult2);
                localReportResult2.setHeading(null);
                a(localReportResult2, localLocale, -1, localArrayList5.size());
                for (int i16 = 0; i16 < localArrayList5.size(); i16++) {
                  localReportResult2.addColumn((ReportColumn)localArrayList5.get(i16));
                }
                for (i16 = 0; i16 < localArrayList7.size(); i16++)
                {
                  localReportRow = new ReportRow(localLocale);
                  if (i16 % 2 == 1) {
                    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
                  }
                  localReportDataItems = new ReportDataItems(localLocale);
                  localReportRow.setDataItems(localReportDataItems);
                  localObject13 = (HashMap)localArrayList7.get(i16);
                  for (int i17 = 0; i17 < localArrayList5.size(); i17++)
                  {
                    localObject14 = "" + i17;
                    switch (i17)
                    {
                    case 0: 
                      localObject14 = (String)((HashMap)localObject13).get("TransCode");
                      break;
                    case 1: 
                      localObject14 = (String)((HashMap)localObject13).get("RDFI_ID");
                      break;
                    case 2: 
                      localObject14 = (String)((HashMap)localObject13).get("DFIAcctNum");
                      break;
                    case 3: 
                      localObject14 = (String)((HashMap)localObject13).get("RecCoName");
                      break;
                    case 4: 
                      localObject14 = (String)((HashMap)localObject13).get("IdentNum");
                      break;
                    case 5: 
                      localObject14 = (String)((HashMap)localObject13).get("Amount");
                      break;
                    case 6: 
                    default: 
                      localObject14 = (String)((HashMap)localObject13).get("TraceNum");
                      break;
                    case 7: 
                      localObject14 = (String)((HashMap)localObject13).get("LogId") + ", " + (String)((HashMap)localObject13).get("BatchId");
                    }
                    a(localReportDataItems.add(), localObject14);
                  }
                  localReportResult2.addRow(localReportRow);
                  paramSummaryInfo.numRows += 1L;
                }
              }
            }
          }
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        finally
        {
          DBUtil.closeAll(bd, localConnection, localPreparedStatement1, localResultSet1);
          localConnection = null;
          localPreparedStatement1 = null;
          localResultSet1 = null;
        }
      }
      if (localObject1 != null)
      {
        localObject1 = new ReportResult(localLocale);
        paramReportResult.addSubReport((ReportResult)localObject1);
        ReportDataDimensions localReportDataDimensions2 = new ReportDataDimensions(localLocale);
        localReportDataDimensions2.setNumRows(-1);
        localReportDataDimensions2.setNumColumns(4);
        ((ReportResult)localObject1).setDataDimensions(localReportDataDimensions2);
        a((ReportResult)localObject1, localLocale, ReportConsts.getText(2018, localLocale) + " " + (String)localObject2 + " (" + str2 + ")", paramSummaryInfo, paramSummaryInfo.getBusinessTotalSummaryItem());
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement1, localResultSet1);
    }
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    String str1 = "ReportTransactionAdapter.buildACHFileUploadReport";
    Locale localLocale1 = paramReportCriteria.getLocale();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str2 = localProperties1.getProperty("MAXDISPLAYSIZE");
    Integer localInteger = null;
    try
    {
      localInteger = Integer.valueOf(str2);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localInteger = new Integer(-1);
    }
    String str3 = localProperties1.getProperty("DATEFORMAT");
    DateFormat localDateFormat = null;
    if (str3 == null) {
      localDateFormat = DateFormatUtil.getFormatter(null);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str3, localLocale1);
    }
    int i1 = -1;
    if (localInteger != null) {
      i1 = localInteger.intValue();
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties2.keys();
    while (localEnumeration.hasMoreElements())
    {
      str4 = (String)localEnumeration.nextElement();
      str5 = localProperties2.getProperty(str4);
      if ((str5 == null) || (str5.length() == 0)) {
        localProperties2.remove(str4);
      }
    }
    String str4 = paramSecureUser.getBusinessName();
    paramReportCriteria.setDisplayValue("Business", str4);
    String str5 = jdField_if(paramReportCriteria, localProperties2);
    String str6 = (String)localProperties2.get("ACHFileDetailLevel");
    if ((str6 == null) || (str6.trim().length() == 0)) {
      str6 = "File Summary";
    }
    String str7 = (String)localProperties2.get("Status");
    if (str7 != null) {
      paramReportCriteria.setDisplayValue("Status", a(str7, paramReportCriteria.getLocale(), "com.ffusion.beans.reporting.ach_status"));
    }
    ReportResult localReportResult1 = null;
    try
    {
      localConnection = DBUtil.getConnection(bd, true, 2);
      String str8 = a(paramSecureUser.getBusinessID(), localConnection);
      String str9 = localProperties2.getProperty("StartDate");
      String str10 = localProperties2.getProperty("EndDate");
      String str11 = null;
      String str12 = null;
      if ((str9 != null) && (str9.length() > 0)) {
        try
        {
          str11 = jdField_try(str9).toString();
        }
        catch (ParseException localParseException1)
        {
          throw new BCReportException(str1, 13, "Invalid format for startDate: " + str9);
        }
      }
      if ((str10 != null) && (str10.length() > 0)) {
        try
        {
          str12 = jdField_try(str10).toString();
        }
        catch (ParseException localParseException2)
        {
          throw new BCReportException(str1, 13, "Invalid format for endDate: " + str10);
        }
      }
      String str13 = "SELECT a.FileId, a.CustomerId, a.ReferenceCODE, a.RDFIACHId, a.ODFIACHId,  a.OrgCreateDate, a.BPWCreateDate, a.DueDate, a.FileHeaderType, a.SubmitDate, a.FileStatus, a.BatchCount, a.BlockCount, a.RecordCount, a.TotalDebit, a.TotalCredit, a.LogId, a.SubmittedBy, a.Memo, a.DtProcessed, a.NumberOfCredits, a.NumberOfDebits, a.ExportFileName, a.UploadFileName, a.LogId, b.FileContent  FROM ACH_File a, ACH_FlatFile b WHERE a.FileId = b.FileId ";
      StringBuffer localStringBuffer = new StringBuffer(str13);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer.append(" AND a.CustomerId = ? ");
      localArrayList.add(Integer.toString(paramSecureUser.getBusinessID()));
      if (str5 != null)
      {
        localStringBuffer.append(" AND a.SubmittedBy = ? ");
        localArrayList.add(str5);
      }
      if (str7 != null)
      {
        localStringBuffer.append(" AND a.FileStatus = ? ");
        localArrayList.add(str7);
      }
      if ((str11 != null) && (str11.trim().length() != 0))
      {
        localStringBuffer.append(" AND a.SubmitDate >= ? ");
        localArrayList.add(str11);
      }
      if ((str12 != null) && (str12.trim().length() != 0))
      {
        localStringBuffer.append(" AND a.SubmitDate <= ? ");
        localArrayList.add(str12);
      }
      String str14 = localProperties2.getProperty("TrackingId");
      if (str14 != null)
      {
        localStringBuffer.append(" and a.LogId=?");
        localArrayList.add(str14);
        localProperties2.remove("TrackingId");
      }
      localStringBuffer.append(" ORDER BY a.SubmitDate ");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i2 = 1;
      if (i1 != -1) {
        localPreparedStatement.setMaxRows(i1 + 1);
      }
      int i3 = localArrayList.size();
      for (int i4 = 0; i4 < i3; i4++) {
        localPreparedStatement.setString(i4 + 1, (String)localArrayList.get(i4));
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Locale localLocale2 = paramSecureUser.getLocale();
      localReportResult1 = new ReportResult(localLocale2);
      localReportResult1.init(paramReportCriteria);
      int i5 = 0;
      int i6 = 0;
      int i7 = 0;
      double d1 = 0.0D;
      double d2 = 0.0D;
      while (localResultSet.next()) {
        if (!a(localResultSet.getString("TotalDebit"), localResultSet.getString("TotalCredit"), localProperties2))
        {
          String str15 = localResultSet.getString("FileId");
          ReportResult localReportResult2 = new ReportResult(localLocale2);
          localReportResult1.addSubReport(localReportResult2);
          a(localConnection, localReportResult2, localResultSet, str8);
          i5++;
          i6 += localResultSet.getInt("NumberOfCredits");
          i7 += localResultSet.getInt("NumberOfDebits");
          d1 += Long.parseLong(localResultSet.getString("TotalCredit")) / 100.0D;
          d2 += Long.parseLong(localResultSet.getString("TotalDebit")) / 100.0D;
          if (!str6.equals("File Summary")) {
            a(localConnection, localReportResult2, str15, str6, str8);
          }
        }
      }
      a(localReportResult1, i5, i6, i7, d1, d2, str8);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
      if (localReportResult1 != null) {
        localReportResult1.setError(localException);
      }
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
      try
      {
        if (localReportResult1 != null) {
          localReportResult1.fini();
        }
      }
      catch (RptException localRptException)
      {
        String str16 = "Unable to generate the report.";
        localRptException.printStackTrace(System.out);
      }
    }
    return localReportResult1;
  }
  
  private static void a(Connection paramConnection, ReportResult paramReportResult, ResultSet paramResultSet, String paramString)
    throws Exception
  {
    String str1 = "ReportTransactionAdapter.processACHFileUploadFileSummaryReport";
    String str2 = paramResultSet.getString("SubmitDate");
    String str3 = paramResultSet.getString("SubmittedBy");
    String str4 = paramResultSet.getString("FileStatus");
    int i1 = paramResultSet.getInt("BatchCount");
    int i2 = paramResultSet.getInt("RecordCount");
    String str5 = paramResultSet.getString("TotalCredit");
    double d1 = Long.parseLong(str5) / 100.0D;
    str5 = paramResultSet.getString("TotalDebit");
    double d2 = Long.parseLong(str5) / 100.0D;
    String str6 = paramResultSet.getString("ODFIACHId");
    String str7 = paramResultSet.getString("RDFIACHId");
    String str8 = paramResultSet.getString("FileContent");
    String str9 = paramResultSet.getString("UploadFileName");
    String str10 = paramResultSet.getString("LogId");
    String str11 = paramResultSet.getString("FileId");
    String str12 = str10 + "." + str11;
    String str13 = str3;
    String str14 = "SELECT user_name FROM customer WHERE directory_id = ?";
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, str14);
    localPreparedStatement.setInt(1, Integer.parseInt(str3));
    ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, str14);
    if (localResultSet.next()) {
      str13 = localResultSet.getString(1);
    }
    DBUtil.closeAll(localPreparedStatement, localResultSet);
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    a(localReportResult1, localLocale, 1, 5);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    localReportHeading.setLabel(ReportConsts.getColumnName(2471, localLocale));
    localReportResult1.setSectionHeading(localReportHeading);
    a(localReportResult1, 2474, "java.lang.String", 5, null);
    a(localReportResult1, 2475, "java.lang.String", 5, null);
    a(localReportResult1, 2485, "java.lang.String", 5, null);
    a(localReportResult1, 1856, "java.lang.String", 5, null);
    a(localReportResult1, 2488, "java.lang.String", 10, null);
    ReportRow localReportRow1 = new ReportRow(localLocale);
    ReportDataItems localReportDataItems1 = new ReportDataItems(localLocale);
    localReportRow1.setDataItems(localReportDataItems1);
    a(localReportDataItems1.add(), str2);
    a(localReportDataItems1.add(), str13);
    a(localReportDataItems1.add(), str9);
    a(localReportDataItems1.add(), jdField_if(str4, localLocale));
    a(localReportDataItems1.add(), str12);
    localReportResult1.addRow(localReportRow1);
    ReportResult localReportResult2 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult2);
    a(localReportResult2, localLocale, 1, 8);
    a(localReportResult2, 2476, "java.lang.String", 15, null);
    a(localReportResult2, 2477, "java.lang.String", 10, "CENTER");
    a(localReportResult2, 2482, "java.lang.Integer", 10, "CENTER");
    a(localReportResult2, 2483, "java.lang.Integer", 10, "CENTER");
    a(localReportResult2, 1855, "com.ffusion.beans.Currency", 10, null);
    a(localReportResult2, 1854, "com.ffusion.beans.Currency", 10, null);
    a(localReportResult2, 2478, "java.lang.String", 10, null);
    a(localReportResult2, 2479, "java.lang.String", 10, null);
    ReportRow localReportRow2 = new ReportRow(localLocale);
    ReportDataItems localReportDataItems2 = new ReportDataItems(localLocale);
    localReportRow2.setDataItems(localReportDataItems2);
    String str15 = str8.substring(23, 33);
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(DateFormatUtil.getFormatter("yyMMddHHmm").parse(str15), Locale.getDefault(), "MM/dd/yyyy HH:mm");
    a(localReportDataItems2.add(), localDateTime.toString());
    a(localReportDataItems2.add(), str8.substring(33, 34));
    a(localReportDataItems2.add(), new Integer(i1));
    a(localReportDataItems2.add(), new Integer(i2));
    a(localReportDataItems2.add(), a(d2, localLocale, paramString));
    a(localReportDataItems2.add(), a(d1, localLocale, paramString));
    a(localReportDataItems2.add(), str6);
    a(localReportDataItems2.add(), str7);
    localReportResult2.addRow(localReportRow2);
  }
  
  private static void a(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
  
  private static Currency a(double paramDouble, Locale paramLocale, String paramString)
  {
    BigDecimal localBigDecimal = new BigDecimal(paramDouble);
    Currency localCurrency = null;
    if (paramString != null) {
      localCurrency = new Currency(localBigDecimal, paramString, paramLocale);
    } else {
      localCurrency = new Currency(localBigDecimal, paramLocale);
    }
    return localCurrency;
  }
  
  private static void a(Connection paramConnection, ReportResult paramReportResult, String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = "SELECT BatchId, BatchNumber, CompanyName, CompanyIdentification, StdEntryClassCode, EffectiveEntryDate, EntryAddendaCount,  TotalDebits, NumberOfDebits, TotalCredits, NumberOfCredits FROM ACH_FileBatch WHERE FileId = ?  ORDER BY BatchNumber";
    localPreparedStatement = DBUtil.prepareStatement(paramConnection, str1.toString());
    localPreparedStatement.setString(1, paramString1);
    localResultSet = DBUtil.executeQuery(localPreparedStatement, str1.toString());
    ArrayList localArrayList1 = new ArrayList();
    int i1 = 0;
    while (localResultSet.next())
    {
      i1++;
      localObject = new ArrayList();
      ((ArrayList)localObject).add(Integer.toString(i1));
      ((ArrayList)localObject).add(localResultSet.getString("CompanyIdentification") + " / " + localResultSet.getString("CompanyName"));
      ((ArrayList)localObject).add(localResultSet.getString("StdEntryClassCode"));
      ((ArrayList)localObject).add(localResultSet.getString("EffectiveEntryDate"));
      ((ArrayList)localObject).add(localResultSet.getString("EntryAddendaCount"));
      ((ArrayList)localObject).add(localResultSet.getString("NumberOfDebits"));
      ((ArrayList)localObject).add(localResultSet.getString("TotalDebits"));
      ((ArrayList)localObject).add(localResultSet.getString("NumberOfCredits"));
      ((ArrayList)localObject).add(localResultSet.getString("TotalCredits"));
      ((ArrayList)localObject).add(localResultSet.getString("BatchNumber"));
      ((ArrayList)localObject).add(localResultSet.getString("BatchId"));
      localArrayList1.add(localObject);
    }
    DBUtil.closeAll(localPreparedStatement, localResultSet);
    a(localReportResult, localLocale, localArrayList1.size(), 10);
    Object localObject = new ReportHeading(localLocale);
    ((ReportHeading)localObject).setLabel(ReportConsts.getColumnName(2472, localLocale));
    localReportResult.setSectionHeading((ReportHeading)localObject);
    int[] arrayOfInt1 = { 2480, 2481, 2040, 1865, 2042, 2015, 1855, 2016, 1854, 1866 };
    int[] arrayOfInt2 = { 5, 20, 6, 9, 10, 12, 15, 12, 12, 5 };
    String[] arrayOfString = { "java.lang.Integer", "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.Integer", "java.lang.Integer", "com.ffusion.beans.Currency", "java.lang.Integer", "com.ffusion.beans.Currency", "java.lang.String" };
    for (int i2 = 0; i2 < arrayOfInt1.length; i2++) {
      a(localReportResult, arrayOfInt1[i2], arrayOfString[i2], arrayOfInt2[i2], null);
    }
    for (i2 = 0; i2 < localArrayList1.size(); i2++)
    {
      ArrayList localArrayList2 = (ArrayList)localArrayList1.get(i2);
      ReportRow localReportRow = new ReportRow(localLocale);
      if (i2 % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      for (int i3 = 0; i3 < localArrayList2.size() - 1; i3++)
      {
        ReportDataItem localReportDataItem = localReportDataItems.add();
        String str3 = arrayOfString[i3];
        String str4 = (String)localArrayList2.get(i3);
        if (str3.equals("java.lang.String"))
        {
          a(localReportDataItem, str4);
        }
        else if (str3.equals("java.lang.Integer"))
        {
          a(localReportDataItem, new Integer(str4));
        }
        else if (str3.equals("com.ffusion.beans.Currency"))
        {
          double d1 = Long.parseLong(str4) / 100.0D;
          a(localReportDataItem, a(d1, localLocale, paramString3));
        }
      }
      localReportResult.addRow(localReportRow);
      if (paramString2.equals("Detail Entry"))
      {
        String str2 = (String)localArrayList2.get(localArrayList2.size() - 1);
        a(paramConnection, paramReportResult, str2, paramString3);
      }
    }
  }
  
  private static void a(Connection paramConnection, ReportResult paramReportResult, String paramString1, String paramString2)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    String str1 = "SELECT TransactionCode, RecvDFIIdentification, DFIAccountNumber, RcvCompIndvName, IdentificationNumber, Amount, TraceNumber FROM ACH_FileEntry WHERE BatchId = ?";
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, str1);
    localPreparedStatement.setString(1, paramString1);
    ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, str1);
    ArrayList localArrayList = new ArrayList();
    while (localResultSet.next())
    {
      localObject1 = new ArrayList();
      ((ArrayList)localObject1).add(localResultSet.getString("TransactionCode"));
      localObject2 = localResultSet.getString("RecvDFIIdentification");
      if ((localObject2 != null) && (((String)localObject2).length() == 8)) {
        localObject2 = (String)localObject2 + RoutingNumberUtil.getRoutingNumber_CheckDigit((String)localObject2);
      }
      ((ArrayList)localObject1).add(localObject2);
      ((ArrayList)localObject1).add(localResultSet.getString("DFIAccountNumber"));
      ((ArrayList)localObject1).add(localResultSet.getString("RcvCompIndvName"));
      ((ArrayList)localObject1).add(localResultSet.getString("IdentificationNumber"));
      ((ArrayList)localObject1).add(localResultSet.getString("Amount"));
      ((ArrayList)localObject1).add(localResultSet.getString("TraceNumber"));
      localArrayList.add(localObject1);
    }
    DBUtil.closeAll(localPreparedStatement, localResultSet);
    a(localReportResult, localLocale, localArrayList.size(), 7);
    Object localObject1 = new ReportHeading(localLocale);
    ((ReportHeading)localObject1).setLabel(ReportConsts.getColumnName(2487, localLocale));
    localReportResult.setSectionHeading((ReportHeading)localObject1);
    Object localObject2 = { 2044, 2045, 2046, 2047, 2048, 2049, 2050 };
    int[] arrayOfInt = { 5, 5, 5, 5, 5, 5, 5 };
    String[] arrayOfString = { "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String", "com.ffusion.beans.Currency", "java.lang.String" };
    Object localObject3;
    for (int i1 = 0; i1 < localObject2.length; i1++)
    {
      localObject3 = null;
      if (localObject2[i1] == 2049) {
        localObject3 = "RIGHT";
      }
      a(localReportResult, localObject2[i1], arrayOfString[i1], arrayOfInt[i1], (String)localObject3);
    }
    for (i1 = 0; i1 < localArrayList.size(); i1++)
    {
      localObject3 = (ArrayList)localArrayList.get(i1);
      ReportRow localReportRow = new ReportRow(localLocale);
      if (i1 % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      for (int i2 = 0; i2 < ((ArrayList)localObject3).size(); i2++)
      {
        ReportDataItem localReportDataItem = localReportDataItems.add();
        String str2 = arrayOfString[i2];
        String str3 = (String)((ArrayList)localObject3).get(i2);
        if (str2.equals("java.lang.String"))
        {
          a(localReportDataItem, str3);
        }
        else if (str2.equals("java.lang.Integer"))
        {
          a(localReportDataItem, new Integer(str3));
        }
        else if (str2.equals("com.ffusion.beans.Currency"))
        {
          double d1 = Long.parseLong(str3) / 100.0D;
          a(localReportDataItem, a(d1, localLocale, paramString2));
        }
      }
      localReportResult.addRow(localReportRow);
    }
  }
  
  private static String jdField_try(String paramString)
    throws ParseException
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      Date localDate = localDateFormat.parse(paramString);
      localDateFormat = DateFormatUtil.getFormatter("yyyy/MM/dd HH:mm:ss");
      return localDateFormat.format(localDate);
    }
    return null;
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, String paramString)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    if (paramInt1 == 0)
    {
      localReportResult = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult);
      localReportHeading = new ReportHeading(localLocale);
      localReportHeading.setLabel(ReportConsts.getText(2503, localLocale));
      localReportResult.setHeading(localReportHeading);
      return;
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, localLocale, 1, 5);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    localReportHeading.setLabel(ReportConsts.getText(2552, localLocale));
    localReportResult.setSectionHeading(localReportHeading);
    int[] arrayOfInt1 = { 2489, 2015, 1855, 2016, 1854 };
    int[] arrayOfInt2 = { 10, 10, 10, 10, 10 };
    String[] arrayOfString = { "java.lang.Integer", "java.lang.Integer", "com.ffusion.beans.Currency", "java.lang.Integer", "com.ffusion.beans.Currency" };
    for (int i1 = 0; i1 < arrayOfInt1.length; i1++) {
      a(localReportResult, arrayOfInt1[i1], arrayOfString[i1], arrayOfInt2[i1], null);
    }
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    a(localReportDataItems.add(), new Integer(paramInt1));
    a(localReportDataItems.add(), new Integer(paramInt3));
    a(localReportDataItems.add(), a(paramDouble2, localLocale, paramString));
    a(localReportDataItems.add(), new Integer(paramInt2));
    a(localReportDataItems.add(), a(paramDouble1, localLocale, paramString));
    localReportResult.addRow(localReportRow);
  }
  
  private static String a(int paramInt, Connection paramConnection)
    throws Exception
  {
    String str1 = null;
    String str2 = "SELECT a.CurrencyCode from BPW_FIInfo a, BPW_Customer b WHERE  a.FIId = b.FIID AND b.CustomerID = ?";
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
    localPreparedStatement.setString(1, Integer.toString(paramInt));
    ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
    if (localResultSet.next()) {
      str1 = localResultSet.getString(1);
    }
    DBUtil.closeAll(localPreparedStatement, localResultSet);
    return str1;
  }
  
  private static String jdField_if(String paramString, Locale paramLocale)
  {
    String str = null;
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return "-";
    }
    try
    {
      str = ResourceUtil.getString(paramString, "com.ffusion.beans.reporting.ach_status", paramLocale);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "-";
    }
    return str;
  }
  
  private static boolean jdField_int(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
  
  private static void a(ReportCriteria paramReportCriteria, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, SummaryInfo paramSummaryInfo, ReportResult paramReportResult1, String paramString5, String paramString6, ReportResult paramReportResult2)
    throws Exception
  {
    String str1 = null;
    ReportResult localReportResult1 = null;
    ReportResult localReportResult2 = null;
    ReportResult localReportResult3 = null;
    ReportDataItems localReportDataItems = null;
    Object localObject1 = null;
    String str2 = null;
    String str3 = "HOST";
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    BigDecimal localBigDecimal = null;
    String str4 = null;
    String str5 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str6 = null;
    int i1 = -1;
    String str7 = null;
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    paramSummaryInfo.resetAccountTotal();
    paramSummaryInfo.prevFromAcct = null;
    paramSummaryInfo.resetStatusTotal();
    paramSummaryInfo.prevStatus = null;
    localObject1 = str3;
    str4 = paramReportCriteria.getSearchCriteria().getProperty("FromAmount");
    str5 = paramReportCriteria.getSearchCriteria().getProperty("ToAmount");
    str6 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    str7 = paramReportCriteria.getReportOptions().getProperty("MAXDISPLAYSIZE");
    if ((str7 != null) && (!str7.equals(""))) {
      i1 = Integer.parseInt(str7.trim());
    }
    StringBuffer localStringBuffer = new StringBuffer("SELECT AUDIT_LOG.LOG_DATE, AUDIT_LOG.TRAN_ID, AUDIT_LOG.STATE, AUDIT_LOG.AGENT_ID, AUDIT_LOG.USER_ID, AUDIT_LOG.BUSINESS_ID, AUDIT_LOG.TRAN_TYPE, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.AMOUNT, wire.OrigAmount, wire.WireDest, wire.RecSrvrTID, wire.SrvrTID, wire.ConfirmNum, wire.ConfirmNum2, wire.DateToPost, wire.OrigCurrency, customer.user_name, business.business_name,  business.business_CIF, wire.DatePosted, wire.DateCreate FROM BPW_WireInfo wire, customer, AUDIT_LOG, business WHERE ");
    if (paramInt != 0) {
      localStringBuffer.append("business.affiliate_bank_id=? and ");
    }
    if ((paramString1 != null) && (!paramString1.equalsIgnoreCase("AllBusinesses"))) {
      localStringBuffer.append("AUDIT_LOG.BUSINESS_ID=? and ");
    }
    if ((paramString4 != null) && (!paramString4.equalsIgnoreCase("AllUsers"))) {
      localStringBuffer.append("AUDIT_LOG.USER_ID=? and ");
    }
    try
    {
      localTimestamp1 = jdField_byte(paramString2);
      localTimestamp2 = jdField_byte(paramString3);
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
      DebugLog.log("WARNING: Invalid format for date search criteria.");
    }
    if (localTimestamp1 != null) {
      localStringBuffer.append("AUDIT_LOG.LOG_DATE>=? and ");
    }
    if (localTimestamp2 != null) {
      localStringBuffer.append("AUDIT_LOG.LOG_DATE<=? and ");
    }
    localStringBuffer.append("wire.WireDest='HOST' and ");
    if ((str4 != null) && (str4.length() > 0)) {
      localStringBuffer.append("AUDIT_LOG.AMOUNT>=? and ");
    }
    if ((str5 != null) && (str5.length() > 0)) {
      localStringBuffer.append("AUDIT_LOG.AMOUNT<=? and ");
    }
    String str8 = paramReportCriteria.getSearchCriteria().getProperty("ReportBy");
    if (str6.equals("Wire Report"))
    {
      if (str8 == null) {
        str8 = "ReportByStatus";
      }
      if (str8.equals("ReportByStatus"))
      {
        if ((paramString5 == null) || (paramString5.equals("AllStatuses")))
        {
          localStringBuffer.append("AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ");
        }
        else
        {
          localStringBuffer.append("AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ");
          localStringBuffer.append("wire.Status=? and ");
        }
      }
      else if ((paramString5 != null) && (!paramString5.equalsIgnoreCase("AllStatuses"))) {
        localStringBuffer.append("AUDIT_LOG.STATE=? and ");
      }
    }
    else
    {
      localStringBuffer.append("(AUDIT_LOG.STATE='BACKENDFAILED' OR AUDIT_LOG.STATE='RELEASEFAILED' OR AUDIT_LOG.STATE='FUNDSFAILEDON' OR AUDIT_LOG.STATE='FUNDSFAILEDON_NOTIF' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED_NOTIF') and ");
    }
    localStringBuffer.append("(AUDIT_LOG.BUSINESS_ID = business.business_id) AND (wire.ExtId = AUDIT_LOG.TRAN_ID)  AND (AUDIT_LOG.USER_ID_INT = customer.directory_id)");
    localStringBuffer.append(" Order By");
    localStringBuffer.append(" business.business_name ASC,");
    localStringBuffer.append(" AUDIT_LOG.STATE ASC ");
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if ((localReportSortCriteria != null) && (localReportSortCriteria.size() > 0))
    {
      localReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = localReportSortCriteria.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (ReportSortCriterion)localIterator.next();
        localObject3 = ((ReportSortCriterion)localObject2).getName();
        if ((localObject3 != null) && (((String)localObject3).length() != 0))
        {
          if (((String)localObject3).equalsIgnoreCase("Date"))
          {
            localObject3 = ", AUDIT_LOG.LOG_DATE";
          }
          else
          {
            if (!((String)localObject3).equalsIgnoreCase("User")) {
              continue;
            }
            localObject3 = ", AUDIT_LOG.USER_ID";
          }
          localObject4 = ((ReportSortCriterion)localObject2).getAsc() ? "ASC" : "DESC";
          localStringBuffer.append((String)localObject3);
          localStringBuffer.append(" ");
          localStringBuffer.append((String)localObject4);
        }
      }
    }
    try
    {
      localConnection = DBUtil.getConnection(bd, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      if (i1 != -1) {
        localPreparedStatement.setMaxRows(i1 + 1);
      }
      int i2 = 1;
      if (paramInt != 0) {
        localPreparedStatement.setInt(i2++, paramInt);
      }
      if ((paramString1 != null) && (!paramString1.equalsIgnoreCase("AllBusinesses"))) {
        localPreparedStatement.setInt(i2++, Integer.parseInt(paramString1));
      }
      if ((paramString4 != null) && (!paramString4.equalsIgnoreCase("AllUsers"))) {
        localPreparedStatement.setString(i2++, paramString4);
      }
      if (localTimestamp1 != null) {
        localPreparedStatement.setTimestamp(i2++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        localPreparedStatement.setTimestamp(i2++, localTimestamp2);
      }
      if ((str4 != null) && (str4.length() > 0)) {
        localPreparedStatement.setBigDecimal(i2++, new BigDecimal(str4));
      }
      if ((str5 != null) && (str5.length() > 0)) {
        localPreparedStatement.setBigDecimal(i2++, new BigDecimal(str5));
      }
      if ((str6.equals("Wire Report")) && (paramString5 != null) && (!paramString5.equalsIgnoreCase("AllStatuses"))) {
        localPreparedStatement.setString(i2++, paramString5);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localObject2 = new SimpleDateFormat("yyyyMMddhhmmss");
      localObject3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      localObject4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      paramSummaryInfo.resetStatusTotal();
      paramSummaryInfo.prevStatus = null;
      paramSummaryInfo.resetAccountTotal();
      paramSummaryInfo.prevFromAcct = null;
      int i3 = 1;
      while (localResultSet.next())
      {
        Object localObject5;
        if ((i1 != -1) && (i1 == paramSummaryInfo.numRows))
        {
          localObject5 = new HashMap();
          ((HashMap)localObject5).put("TRUNCATED", new Integer(i1));
          paramReportResult2.setProperties((HashMap)localObject5);
          break;
        }
        str2 = localResultSet.getString(3);
        if (localReportResult2 == null) {
          localReportResult2 = jdField_do(paramReportResult1, paramReportCriteria, localObject1);
        }
        if (!str2.equals(paramSummaryInfo.prevStatus))
        {
          a(localReportResult1, paramSummaryInfo, paramReportCriteria);
          a(paramSummaryInfo, false);
          jdField_if(paramSummaryInfo, true);
          paramSummaryInfo.prevStatus = str2;
          localObject5 = "Wire Status: " + jdField_int(str2, paramReportCriteria.getLocale());
          localReportResult1 = jdField_if(localReportResult2, paramReportCriteria, (String)localObject5);
          localReportResult3 = new ReportResult(Locale.getDefault());
          localReportResult1.addSubReport(localReportResult3);
          a(localReportResult3, paramReportCriteria);
          i3 = 1;
        }
        localReportDataItems = new ReportDataItems(Locale.getDefault());
        str1 = localResultSet.getString(1);
        if (jdField_int(str1)) {
          localReportDataItems.add().setData("");
        } else {
          localReportDataItems.add().setData(str1.substring(0, 19));
        }
        str1 = localResultSet.getString(23);
        if (jdField_int(str1))
        {
          localReportDataItems.add().setData("");
        }
        else
        {
          str1 = str1.trim();
          if (str1.length() < 19) {
            localReportDataItems.add().setData("");
          } else {
            try
            {
              localReportDataItems.add().setData(localSimpleDateFormat3.format(((SimpleDateFormat)localObject4).parse(str1)));
            }
            catch (Exception localException2)
            {
              localReportDataItems.add().setData(str1.substring(0, 19));
            }
          }
        }
        str1 = localResultSet.getString(17);
        String str10;
        if (jdField_int(str1))
        {
          localReportDataItems.add().setData("");
        }
        else
        {
          localObject6 = localSimpleDateFormat1.parse(str1.substring(0, 8));
          str10 = localSimpleDateFormat2.format((Date)localObject6);
          localReportDataItems.add().setData(str10);
        }
        str1 = localResultSet.getString(22);
        if (jdField_int(str1))
        {
          localReportDataItems.add().setData("");
        }
        else
        {
          localObject6 = ((SimpleDateFormat)localObject2).parse(str1.substring(0, 14));
          str10 = ((SimpleDateFormat)localObject3).format((Date)localObject6);
          localReportDataItems.add().setData(str10);
        }
        a(localReportDataItems.add(), localResultSet.getString(19));
        localReportDataItems.add().setData("HOST");
        localReportDataItems.add().setData("HOST");
        localBigDecimal = localResultSet.getBigDecimal(10);
        if (localBigDecimal == null)
        {
          localReportDataItems.add().setData("");
          localBigDecimal = new BigDecimal(0.0D);
        }
        else
        {
          localObject6 = new Currency(localBigDecimal, Locale.getDefault());
          localReportDataItems.add().setData(((Currency)localObject6).getCurrencyStringNoSymbol());
        }
        a(localReportDataItems.add(), localResultSet.getString(12));
        str1 = localResultSet.getString(2) + ", " + localResultSet.getString(14) + ", " + localResultSet.getString(15) + ", " + localResultSet.getString(16);
        localReportDataItems.add().setData(str1);
        a(localReportDataItems.add(), localResultSet.getString(18));
        str1 = localResultSet.getString(11);
        localReportDataItems.add().setData("");
        a(localReportDataItems.add(), localResultSet.getString(13));
        Object localObject6 = new ReportRow(Locale.getDefault());
        if (i3 % 2 == 0) {
          ((ReportRow)localObject6).set("CELLBACKGROUND", "reportDataShaded");
        }
        ((ReportRow)localObject6).setDataItems(localReportDataItems);
        localReportResult3.addRow((ReportRow)localObject6);
        i3++;
        paramSummaryInfo.numRows += 1L;
        paramSummaryInfo.addStatusTotal(new SummaryItem(localBigDecimal, 1L));
      }
    }
    catch (Exception localException1) {}finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
    }
    a(localReportResult1, paramSummaryInfo, paramReportCriteria);
    a(paramSummaryInfo, false);
    String str9 = ReportConsts.getText(632, Locale.getDefault());
    a(localReportResult2, str9, paramSummaryInfo.getAccountTotalSummaryItem());
    paramSummaryInfo.addBusinessTotal(paramSummaryInfo.getAccountTotalSummary());
    paramSummaryInfo.resetStatusTotal();
    paramSummaryInfo.prevStatus = null;
    paramSummaryInfo.resetAccountTotal();
    paramSummaryInfo.prevFromAcct = null;
  }
  
  private static void a(ReportCriteria paramReportCriteria, int paramInt, ArrayList paramArrayList, String paramString1, String paramString2, String paramString3, SummaryInfo paramSummaryInfo, ReportResult paramReportResult, String paramString4, String paramString5)
    throws Exception
  {
    String str1 = null;
    ReportResult localReportResult1 = null;
    ReportResult localReportResult2 = null;
    ReportResult localReportResult3 = null;
    ReportResult localReportResult4 = null;
    ReportDataItems localReportDataItems = null;
    String str2 = "HOST";
    String str3 = null;
    String str4 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    String str5 = null;
    BigDecimal localBigDecimal = null;
    String str6 = null;
    int i1 = -1;
    String str7 = null;
    String str8 = null;
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
      str3 = paramReportCriteria.getSearchCriteria().getProperty("FromAmount");
      str4 = paramReportCriteria.getSearchCriteria().getProperty("ToAmount");
      Object localObject2;
      if ((paramString4 == null) || (paramString4.equals("AllStatuses")) || (paramString4.equals("APPROVAL_PENDING")) || (paramString4.equals("APPROVAL_REJECTED")))
      {
        localStringBuffer = new StringBuffer("SELECT business_id, business_name, business_CIF FROM business");
        localStringBuffer.append(" WHERE business_id IN ");
        localStringBuffer.append(" (");
        localStringBuffer.append("SELECT DISTINCT(businessID) FROM Appr_Items WHERE ");
        localStringBuffer.append("itemType = ");
        localStringBuffer.append(1);
        localStringBuffer.append(" AND itemSubType in (");
        localStringBuffer.append(5);
        localStringBuffer.append(",");
        localStringBuffer.append(6);
        localStringBuffer.append(",");
        localStringBuffer.append(10);
        localStringBuffer.append(")");
        if ((paramString3 != null) && (!paramString3.equalsIgnoreCase("AllUsers"))) {
          localStringBuffer.append(" AND submittingUserID = " + DBUtil.trimSQLNumericLiteral(paramString3, new StringBuffer()));
        }
        String str9 = " processingState <> 'Complete' ";
        localObject1 = " decision = 'Rejected' ";
        if ((paramString4 == null) || (paramString4.equals("AllStatuses")))
        {
          localStringBuffer.append(" AND (");
          localStringBuffer.append(str9);
          localStringBuffer.append(" OR ");
          localStringBuffer.append((String)localObject1);
          localStringBuffer.append(") ");
        }
        else if (paramString4.equals("APPROVAL_PENDING"))
        {
          localStringBuffer.append(" AND ").append(str9);
        }
        else if (paramString4.equals("APPROVAL_REJECTED"))
        {
          localStringBuffer.append(" AND ").append((String)localObject1);
        }
        localObject2 = null;
        localObject3 = null;
        if (paramString1 != null)
        {
          localObject2 = a(paramString1, false);
          localStringBuffer.append(" AND dtSubmission >= ?");
        }
        if (paramString2 != null)
        {
          localObject3 = a(paramString2, true);
          localStringBuffer.append(" AND dtSubmission <= ?");
        }
        localStringBuffer.append(" )");
        localStringBuffer.append(" ORDER BY business_name ASC ");
        localConnection = DBUtil.getConnection(bd, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        if ((localObject2 != null) && (localObject3 != null))
        {
          localPreparedStatement.setTimestamp(1, new Timestamp(((com.ffusion.beans.DateTime)localObject2).getTime().getTime()));
          localPreparedStatement.setTimestamp(2, new Timestamp(((com.ffusion.beans.DateTime)localObject3).getTime().getTime()));
        }
        else if (localObject2 != null)
        {
          localPreparedStatement.setTimestamp(1, new Timestamp(((com.ffusion.beans.DateTime)localObject2).getTime().getTime()));
        }
        else if (localObject3 != null)
        {
          localPreparedStatement.setTimestamp(1, new Timestamp(((com.ffusion.beans.DateTime)localObject3).getTime().getTime()));
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        localObject4 = null;
        while (localResultSet.next())
        {
          paramSummaryInfo.resetBusinessTotal();
          localObject4 = localResultSet.getString(1);
          if ((paramString5 == null) || (paramString5.equals("AllBusinesses")) || (((String)localObject4).equals(paramString5)))
          {
            if (!paramArrayList.contains(localObject4))
            {
              localObject5 = localResultSet.getString(2) + " (" + localResultSet.getString(3) + ")";
              a(paramReportCriteria, paramInt, (String)localObject4, null, paramString1, paramString2, paramSummaryInfo, localReportResult4, paramString4, (String)localObject5, paramReportResult);
              localObject6 = ReportConsts.getText(633, Locale.getDefault());
              a(localReportResult4, (String)localObject6, paramSummaryInfo.getBusinessTotalSummaryItem());
              paramSummaryInfo.addGrandTotal(paramSummaryInfo.getBusinessTotalSummary());
              paramSummaryInfo.resetBusinessTotal();
            }
            paramArrayList.add(localObject4);
          }
        }
        DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
        localConnection = null;
        localPreparedStatement = null;
        localResultSet = null;
      }
      str3 = paramReportCriteria.getSearchCriteria().getProperty("FromAmount");
      str4 = paramReportCriteria.getSearchCriteria().getProperty("ToAmount");
      str6 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      str7 = paramReportCriteria.getReportOptions().getProperty("MAXDISPLAYSIZE");
      if ((str7 != null) && (!str7.equals(""))) {
        i1 = Integer.parseInt(str7.trim());
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT AUDIT_LOG.LOG_DATE, AUDIT_LOG.TRAN_ID, AUDIT_LOG.STATE, AUDIT_LOG.AGENT_ID, AUDIT_LOG.USER_ID, AUDIT_LOG.BUSINESS_ID, AUDIT_LOG.TRAN_TYPE, AUDIT_LOG.FROM_ACCT_ID, AUDIT_LOG.TO_ACCT_ID, AUDIT_LOG.AMOUNT, wire.OrigAmount, wire.WireDest, wire.RecSrvrTID, wire.SrvrTID, wire.ConfirmNum, wire.ConfirmNum2, wire.DateToPost, wire.OrigCurrency, customer.user_name, business.business_name,  business.business_CIF, wire.DatePosted, wire.DateCreate FROM BPW_WireInfo wire, customer, AUDIT_LOG, business WHERE ");
      if (paramInt != 0) {
        localStringBuffer.append("business.affiliate_bank_id=? and ");
      }
      if ((paramArrayList.isEmpty() != true) && (paramArrayList.get(0) != null))
      {
        localStringBuffer.append("AUDIT_LOG.BUSINESS_ID not in ( ");
        for (int i2 = 0; i2 < paramArrayList.size(); i2++) {
          DBUtil.trimSQLNumericLiteral((String)paramArrayList.get(i2), localStringBuffer).append(",");
        }
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        localStringBuffer.append(" ) and ");
      }
      if ((paramString3 != null) && (!paramString3.equalsIgnoreCase("AllUsers"))) {
        localStringBuffer.append("AUDIT_LOG.USER_ID=? and ");
      }
      try
      {
        localTimestamp1 = jdField_byte(paramString1);
        localTimestamp2 = jdField_byte(paramString2);
      }
      catch (ParseException localParseException)
      {
        localParseException.printStackTrace();
        DebugLog.log("WARNING: Invalid format for date search criteria.");
      }
      if (localTimestamp1 != null) {
        localStringBuffer.append("AUDIT_LOG.LOG_DATE>=? and ");
      }
      if (localTimestamp2 != null) {
        localStringBuffer.append("AUDIT_LOG.LOG_DATE<=? and ");
      }
      localStringBuffer.append("wire.WireDest='HOST' and ");
      if ((str3 != null) && (str3.length() > 0)) {
        localStringBuffer.append("AUDIT_LOG.AMOUNT>=? and ");
      }
      if ((str4 != null) && (str4.length() > 0)) {
        localStringBuffer.append("AUDIT_LOG.AMOUNT<=? and ");
      }
      String str10 = paramReportCriteria.getSearchCriteria().getProperty("ReportBy");
      if (str6.equals("Wire Report"))
      {
        if (str10 == null) {
          str10 = "ReportByStatus";
        }
        if (str10.equals("ReportByStatus"))
        {
          if ((paramString4 == null) || (paramString4.equals("AllStatuses")))
          {
            localStringBuffer.append("AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ");
          }
          else
          {
            localStringBuffer.append("AUDIT_LOG.STATE=wire.Status and wire.Status != 'TEMPLATE' and wire.Status != 'RECTEMPLATE' and ");
            localStringBuffer.append("wire.Status=? and ");
          }
        }
        else if ((paramString4 != null) && (!paramString4.equalsIgnoreCase("AllStatuses"))) {
          localStringBuffer.append("AUDIT_LOG.STATE=? and ");
        }
      }
      else
      {
        localStringBuffer.append("(AUDIT_LOG.STATE='BACKENDFAILED' OR AUDIT_LOG.STATE='RELEASEFAILED' OR AUDIT_LOG.STATE='FUNDSFAILEDON' OR AUDIT_LOG.STATE='FUNDSFAILEDON_NOTIF' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED' OR AUDIT_LOG.STATE='FUNDSREVERTFAILED_NOTIF') and ");
      }
      localStringBuffer.append("(AUDIT_LOG.BUSINESS_ID = business.business_id) AND (wire.ExtId = AUDIT_LOG.TRAN_ID)  AND (AUDIT_LOG.USER_ID_INT = customer.directory_id)");
      localStringBuffer.append(" Order By");
      localStringBuffer.append(" business.business_name ASC,");
      localStringBuffer.append(" AUDIT_LOG.STATE ASC ");
      Object localObject1 = paramReportCriteria.getSortCriteria();
      if ((localObject1 != null) && (((ReportSortCriteria)localObject1).size() > 0))
      {
        ((ReportSortCriteria)localObject1).setSortedBy("ORDINAL");
        localObject2 = ((ReportSortCriteria)localObject1).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (ReportSortCriterion)((Iterator)localObject2).next();
          localObject4 = ((ReportSortCriterion)localObject3).getName();
          if ((localObject4 != null) && (((String)localObject4).length() != 0))
          {
            if (((String)localObject4).equalsIgnoreCase("Date"))
            {
              localObject4 = ", AUDIT_LOG.LOG_DATE";
            }
            else
            {
              if (!((String)localObject4).equalsIgnoreCase("User")) {
                continue;
              }
              localObject4 = ", AUDIT_LOG.USER_ID";
            }
            localObject5 = ((ReportSortCriterion)localObject3).getAsc() ? "ASC" : "DESC";
            localStringBuffer.append((String)localObject4);
            localStringBuffer.append(" ");
            localStringBuffer.append((String)localObject5);
          }
        }
      }
      localConnection = DBUtil.getConnection(bd, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      if (i1 != -1) {
        localPreparedStatement.setMaxRows(i1 + 1);
      }
      int i3 = 1;
      if (paramInt != 0) {
        localPreparedStatement.setInt(i3++, paramInt);
      }
      if ((paramString3 != null) && (!paramString3.equalsIgnoreCase("AllUsers"))) {
        localPreparedStatement.setString(i3++, paramString3);
      }
      if (localTimestamp1 != null) {
        localPreparedStatement.setTimestamp(i3++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        localPreparedStatement.setTimestamp(i3++, localTimestamp2);
      }
      if ((str3 != null) && (str3.length() > 0)) {
        localPreparedStatement.setBigDecimal(i3++, new BigDecimal(str3));
      }
      if ((str4 != null) && (str4.length() > 0)) {
        localPreparedStatement.setBigDecimal(i3++, new BigDecimal(str4));
      }
      if ((str6.equals("Wire Report")) && (paramString4 != null) && (!paramString4.equalsIgnoreCase("AllStatuses"))) {
        localPreparedStatement.setString(i3++, paramString4);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      Object localObject3 = new SimpleDateFormat("yyyyMMddhhmmss");
      Object localObject4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      Object localObject5 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Object localObject6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      paramSummaryInfo.resetStatusTotal();
      paramSummaryInfo.prevStatus = null;
      paramSummaryInfo.resetAccountTotal();
      paramSummaryInfo.prevFromAcct = null;
      paramSummaryInfo.prevBusiness = null;
      paramSummaryInfo.resetBusinessTotal();
      int i4 = 1;
      while (localResultSet.next())
      {
        Object localObject7;
        if ((i1 != -1) && (i1 == paramSummaryInfo.numRows))
        {
          localObject7 = new HashMap();
          ((HashMap)localObject7).put("TRUNCATED", new Integer(i1));
          paramReportResult.setProperties((HashMap)localObject7);
          break;
        }
        str8 = localResultSet.getString(6);
        str5 = localResultSet.getString(3);
        if (!str8.equals(paramSummaryInfo.prevBusiness))
        {
          if (paramSummaryInfo.prevBusiness != null)
          {
            a(localReportResult1, paramSummaryInfo, paramReportCriteria);
            a(paramSummaryInfo, false);
            localObject7 = ReportConsts.getText(632, Locale.getDefault());
            a(localReportResult2, (String)localObject7, paramSummaryInfo.getAccountTotalSummaryItem());
            jdField_if(paramSummaryInfo, false);
            paramSummaryInfo.prevStatus = null;
            paramSummaryInfo.addBusinessTotal(paramSummaryInfo.getAccountTotalSummary());
            str11 = ReportConsts.getText(633, Locale.getDefault());
            a(localReportResult4, str11, paramSummaryInfo.getBusinessTotalSummaryItem());
            paramSummaryInfo.addGrandTotal(paramSummaryInfo.getBusinessTotalSummary());
          }
          paramSummaryInfo.resetBusinessTotal();
          paramSummaryInfo.prevBusiness = str8;
          localObject7 = localResultSet.getString(20) + " (" + localResultSet.getString(21) + ")";
          localReportResult4 = a(paramReportResult, paramReportCriteria, (String)localObject7);
          paramSummaryInfo.resetAccountTotal();
          localReportResult2 = jdField_do(localReportResult4, paramReportCriteria, str2);
          i4 = 1;
        }
        if (!str5.equals(paramSummaryInfo.prevStatus))
        {
          a(localReportResult1, paramSummaryInfo, paramReportCriteria);
          a(paramSummaryInfo, false);
          jdField_if(paramSummaryInfo, true);
          paramSummaryInfo.prevStatus = str5;
          localObject7 = "Wire Status: " + jdField_int(str5, paramReportCriteria.getLocale());
          localReportResult1 = jdField_if(localReportResult2, paramReportCriteria, (String)localObject7);
          localReportResult3 = new ReportResult(Locale.getDefault());
          localReportResult1.addSubReport(localReportResult3);
          a(localReportResult3, paramReportCriteria);
          i4 = 1;
        }
        localReportDataItems = new ReportDataItems(Locale.getDefault());
        str1 = localResultSet.getString(1);
        if (jdField_int(str1)) {
          localReportDataItems.add().setData("");
        } else {
          localReportDataItems.add().setData(str1.substring(0, 19));
        }
        str1 = localResultSet.getString(23);
        if (jdField_int(str1))
        {
          localReportDataItems.add().setData("");
        }
        else
        {
          str1 = str1.trim();
          if (str1.length() < 19) {
            localReportDataItems.add().setData("");
          } else {
            try
            {
              localReportDataItems.add().setData(((SimpleDateFormat)localObject6).format(((SimpleDateFormat)localObject5).parse(str1)));
            }
            catch (Exception localException2)
            {
              localReportDataItems.add().setData(str1.substring(0, 19));
            }
          }
        }
        str1 = localResultSet.getString(17);
        if (jdField_int(str1))
        {
          localReportDataItems.add().setData("");
        }
        else
        {
          localObject8 = localSimpleDateFormat1.parse(str1.substring(0, 8));
          str11 = localSimpleDateFormat2.format((Date)localObject8);
          localReportDataItems.add().setData(str11);
        }
        str1 = localResultSet.getString(22);
        if (jdField_int(str1))
        {
          localReportDataItems.add().setData("");
        }
        else
        {
          localObject8 = ((SimpleDateFormat)localObject3).parse(str1.substring(0, 14));
          str11 = ((SimpleDateFormat)localObject4).format((Date)localObject8);
          localReportDataItems.add().setData(str11);
        }
        a(localReportDataItems.add(), localResultSet.getString(19));
        localReportDataItems.add().setData("HOST");
        localReportDataItems.add().setData("HOST");
        localBigDecimal = localResultSet.getBigDecimal(10);
        if (localBigDecimal == null)
        {
          localReportDataItems.add().setData("");
          localBigDecimal = new BigDecimal(0.0D);
        }
        else
        {
          localObject8 = new Currency(localBigDecimal, Locale.getDefault());
          localReportDataItems.add().setData(((Currency)localObject8).getCurrencyStringNoSymbol());
        }
        a(localReportDataItems.add(), localResultSet.getString(12));
        str1 = localResultSet.getString(2) + ", " + localResultSet.getString(14) + ", " + localResultSet.getString(15) + ", " + localResultSet.getString(16);
        localReportDataItems.add().setData(str1);
        a(localReportDataItems.add(), localResultSet.getString(18));
        str1 = localResultSet.getString(11);
        localReportDataItems.add().setData("");
        a(localReportDataItems.add(), localResultSet.getString(13));
        localObject8 = new ReportRow(Locale.getDefault());
        if (i4 % 2 == 0) {
          ((ReportRow)localObject8).set("CELLBACKGROUND", "reportDataShaded");
        }
        ((ReportRow)localObject8).setDataItems(localReportDataItems);
        localReportResult3.addRow((ReportRow)localObject8);
        i4++;
        paramSummaryInfo.numRows += 1L;
        paramSummaryInfo.addStatusTotal(new SummaryItem(localBigDecimal, 1L));
      }
      a(localReportResult1, paramSummaryInfo, paramReportCriteria);
      a(paramSummaryInfo, false);
      Object localObject8 = ReportConsts.getText(632, Locale.getDefault());
      a(localReportResult2, (String)localObject8, paramSummaryInfo.getAccountTotalSummaryItem());
      paramSummaryInfo.addBusinessTotal(paramSummaryInfo.getAccountTotalSummary());
      String str11 = ReportConsts.getText(633, Locale.getDefault());
      a(localReportResult4, str11, paramSummaryInfo.getBusinessTotalSummaryItem());
      paramSummaryInfo.addGrandTotal(paramSummaryInfo.getBusinessTotalSummary());
      jdField_if(paramSummaryInfo, true);
      paramSummaryInfo.prevStatus = null;
      paramSummaryInfo.resetAccountTotal();
      paramSummaryInfo.prevFromAcct = null;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw localException1;
    }
    finally
    {
      DBUtil.closeAll(bd, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static String a(BigDecimal paramBigDecimal, Locale paramLocale)
  {
    String str;
    if (paramBigDecimal != null)
    {
      NumberFormat localNumberFormat = NumberFormat.getNumberInstance(paramLocale);
      str = localNumberFormat.format(paramBigDecimal.doubleValue());
    }
    else
    {
      str = "";
    }
    return str;
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, String paramString1, int paramInt2, String paramString2)
    throws RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(paramInt1, localLocale));
    localReportColumn.setLabels(localArrayList);
    if (paramString1 != null) {
      localReportColumn.setDataType(paramString1);
    }
    if (paramInt2 != 0) {
      localReportColumn.setWidthAsPercent(paramInt2);
    }
    if (paramString2 != null) {
      localReportColumn.setJustification(paramString2);
    }
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(HashMap paramHashMap)
  {
    String str1 = "ReportTransactionAdapter.setValueAccntAmtMap";
    BigDecimal localBigDecimal = new BigDecimal("0");
    Set localSet = paramHashMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      Properties localProperties = null;
      if (str2 != null) {
        localProperties = (Properties)paramHashMap.get(str2);
      }
      if (localProperties != null) {
        localProperties.put("Amount", String.valueOf(localBigDecimal));
      }
    }
  }
  
  private static boolean a(String paramString)
  {
    return (paramString.equals("Transfer Report")) || (paramString.equals("Transfer Exception Items Report")) || (paramString.equals("External Transfer Report")) || (paramString.equals("External Transfer Exception Items Report"));
  }
  
  private static String a(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3)
  {
    String str = "0";
    if ((jdField_int(paramString2)) || (jdField_int(paramString3)))
    {
      DebugLog.log("WARNING: currency code does not exist.");
      return str;
    }
    if (!paramString2.equalsIgnoreCase(paramString3))
    {
      FXRate localFXRate = null;
      try
      {
        localFXRate = FXHandler.getClosestFXRate(paramSecureUser, paramString2, paramString3, new com.ffusion.util.beans.DateTime(), 4, String.valueOf(paramSecureUser.getProfileID()), new HashMap());
      }
      catch (Exception localException)
      {
        localException.printStackTrace(System.out);
      }
      BigDecimal localBigDecimal1 = localFXRate.getBuyPrice().getAmountValue();
      BigDecimal localBigDecimal2 = new BigDecimal(paramString1);
      BigDecimal localBigDecimal3 = localBigDecimal2.multiply(localBigDecimal1);
      str = localBigDecimal3.toString();
    }
    else
    {
      str = paramString1;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ReportTransactionAdapter
 * JD-Core Version:    0.7.0.1
 */