package com.ffusion.services.dataconsolidator;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountKey;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.lockbox.LockboxAccount;
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
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.CurrencyEstimate;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.csil.core.FX;
import com.ffusion.csil.core.Reporting;
import com.ffusion.csil.handlers.TreasuryDirect;
import com.ffusion.dataconsolidator.adapter.DCAdapter;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.dataconsolidator.adapter.SummaryValue;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.BankingException;
import com.ffusion.util.ArrayListUtil;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FFIStringTokenizer;
import com.ffusion.util.FFIUtilException;
import com.ffusion.util.Qsort;
import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;

public class DCServiceUtil
{
  public static final int NUMBER_OF_DAYS_IN_ALL_DATES_SEARCH = 31;
  private static final int jdField_int = 100;
  private static final int jdField_new = 400;
  private static final String t = "dcAccount.DCAccountID";
  private static final String aN = "dcAccount.BankID";
  private static final String z = "dcAccount.AccountID";
  private static final String b = "dcAccount.CurrencyCode";
  private static final String jdField_do = "dcAccount.RoutingNumber";
  private static final String G = "dcTransactions.DCAccountID";
  private static final String u = "dcTransactions.DCTransactionIndex";
  private static final String aE = "dcTransactions.DataDate";
  private static final String L = "dcTransactions.TransID";
  private static final String a9 = "dcTransactions.TransTypeID";
  private static final String n = "dcTransactions.TransSubTypeID";
  private static final String jdField_byte = "dcTransactions.Amount";
  private static final String aF = "dcTransactions.ImmedAvailAmount";
  private static final String bb = "dcTransactions.OneDayAvailAmount";
  private static final String ay = "dcTransactions.MoreOneDayAvailAm";
  private static final String aU = "dcTransactions.BankRefNum";
  private static final String jdField_goto = "dcTransactions.CustRefNum";
  private static final String aG = "dcTransactions.PostingDate";
  private static final String br = "dcTransactions.DueDate";
  private static final String ac = "dcTransactions.FixedDepRate";
  private static final String N = "dcTransactions.PayeePayor";
  private static final String aw = "dcTransactions.PayorNum";
  private static final String aO = "dcTransactions.OrigUser";
  private static final String bv = "dcTransactions.PONum";
  private static final String aR = "dcTransactions.ReferenceNumber";
  private static final String Q = "dcTransactions.TransCategoryID";
  private static final String aA = "dcTransactions.Description";
  private static final String aI = "dcTransactions.Memo";
  private static final String jdField_else = "dcTransactions.RunningBalance";
  private static final String l = "dcTransactions.TransTrackingID";
  private static final String bL = "dcTransactions.ValueDateTime";
  private static final String ad = "dcTransactions.InstNumber";
  private static final String bI = "dcTransactions.InstBankName";
  private static final String h = "dcTransactions.ExtendABeanXMLID";
  private static final String bB = "dcTransactions.TransDate";
  private static final String aK = "dcTransactions.DataClassification";
  private static final String aH = "dcTransactions.DataSourceLoadTime";
  private static final String jdField_long = "transTypeDesc.TRANS_TYPE_DESC";
  private static final String Z = "subTypeDesc.DESCRIPTION";
  private static final String by = "dcAcctHistory.DataDate";
  private static final String au = "dcAcctHistory.DataClassification";
  private static final String jdField_void = "dcAcctHistory.DCAccountID";
  private static final String j = "dcAcctHistory.OpeningLedger";
  private static final String aP = "dcAcctHistory.AvgOpenLedgerMTD";
  private static final String aq = "dcAcctHistory.AvgOpenLedgerYTD";
  private static final String bA = "dcAcctHistory.ClosingLedger";
  private static final String bj = "dcAcctHistory.AvgCloseLedgerMTD";
  private static final String aQ = "dcAcctHistory.AvgMonth";
  private static final String M = "dcAcctHistory.AggBalanceAdjust";
  private static final String s = "dcAcctHistory.AvCloseLedgYTDPrvM";
  private static final String aJ = "dcAcctHistory.AvgCloseLedgerYTD";
  private static final String w = "dcAcctHistory.CurrentLedger";
  private static final String a0 = "dcAcctHistory.ACHNetPosition";
  private static final String aD = "dcAcctHistory.OpAvaiSamDayACHDTC";
  private static final String bz = "dcAcctHistory.OpeningAvailable";
  private static final String bG = "dcAcctHistory.AvgOpenAvailMTD";
  private static final String bl = "dcAcctHistory.AvgOpenAvailYTD";
  private static final String x = "dcAcctHistory.AvgAvailPrevMonth";
  private static final String B = "dcAcctHistory.DisbOpenAvailBal";
  private static final String jdField_if = "dcAcctHistory.ClosingAvail";
  private static final String ah = "dcAcctHistory.AvgCloseAvailMTD";
  private static final String V = "dcAcctHistory.AvCloseAvailPreM";
  private static final String F = "dcAcctHistory.AvCloseAvailYTDPrM";
  private static final String O = "dcAcctHistory.AvCloseAvailYTD";
  private static final String a1 = "dcAcctHistory.LoanBalance";
  private static final String jdField_null = "dcAcctHistory.TotalInvestPosn";
  private static final String D = "dcAcctHistory.CurrAvailCRSSupr";
  private static final String o = "dcAcctHistory.CurrentAvail";
  private static final String aZ = "dcAcctHistory.AvgCurrAvailMTD";
  private static final String aB = "dcAcctHistory.AvgCurrAvailYTD";
  private static final String ai = "dcAcctHistory.TotalFloat";
  private static final String bd = "dcAcctHistory.TargetBalance";
  private static final String aa = "dcAcctHistory.AdjBalance";
  private static final String K = "dcAcctHistory.AdjBalanceMTD";
  private static final String r = "dcAcctHistory.AdjBalanceYTD";
  private static final String ab = "dcAcctHistory.ZeroDayFloat";
  private static final String bE = "dcAcctHistory.OneDayFloat";
  private static final String a5 = "dcAcctHistory.FloatAdj";
  private static final String bf = "dcAcctHistory.TwoMoreDayFloat";
  private static final String S = "dcAcctHistory.ThreeMoreDayFloat";
  private static final String bq = "dcAcctHistory.AdjToBalances";
  private static final String aM = "dcAcctHistory.AvgAdjToBalMTD";
  private static final String ap = "dcAcctHistory.AvgAdjToBalYTD";
  private static final String C = "dcAcctHistory.FourDayFloat";
  private static final String bn = "dcAcctHistory.FiveDayFloat";
  private static final String as = "dcAcctHistory.SixDayFloat";
  private static final String X = "dcAcctHistory.AvgOneDayFloatMTD";
  private static final String E = "dcAcctHistory.AvgOneDayFloatYTD";
  private static final String bF = "dcAcctHistory.AvgTwoDayFloatMTD";
  private static final String bk = "dcAcctHistory.AvgTwoDayFloatYTD";
  private static final String a8 = "dcAcctHistory.TransferCalc";
  private static final String jdField_for = "dcAcctHistory.TargBalDeficiency";
  private static final String bC = "dcAcctHistory.TotalFundingReq";
  private static final String bc = "dcAcctHistory.TotalChecksPaid";
  private static final String aS = "dcAcctHistory.GrandTotCredMinDeb";
  private static final String bK = "dcextaccsummary.DataDate";
  private static final String bH = "dcextaccsummary.DataClassification";
  private static final String bt = "dcextaccsummary.SummaryType";
  private static final String aj = "dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode";
  private static final String a = "SELECT CurrentAvail from DC_AccountHistory dcAcctHistory, DC_Account dcAccount WHERE CurrentAvail IS NOT null AND dcAccount.AccountID = ? AND dcAccount.BankID = ? AND dcAccount.RoutingNumber = ? AND dcAcctHistory.DCAccountID = dcAccount.DCAccountID ORDER BY dcAcctHistory.DataDate DESC";
  private static final String aY = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcAcctHistory.ClosingLedger FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount, ( SELECT dcAcctHistory.DCAccountID, max(dcAcctHistory.DataDate) AS MAX_DATE, dcAcctHistory.DataClassification FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount WHERE dcAcctHistory.ClosingLedger IS NOT null AND dcAcctHistory.DCAccountID=dcAccount.DCAccountID AND ";
  private static final String Y = "GROUP BY dcAcctHistory.DCAccountID, dcAcctHistory.DataClassification ) recentDate ";
  private static final String p = " ";
  private static final String g = "dcAcctHistory.DCAccountID=recentDate.DCAccountID AND dcAcctHistory.DataClassification=recentDate.DataClassification AND dcAcctHistory.DataDate=recentDate.MAX_DATE AND dcAcctHistory.DCAccountID=dcAccount.DCAccountID ";
  private static String ao = "SELECT dcAccount.AccountID, dcAccount.BankID, dcAccount.RoutingNumber, dcextaccsummary.DataDate, dcextaccsummary.SummaryType, dcextaccsummary.Amount, dcextaccsummary.ImmedAvailAmount, dcextaccsummary.OneDayAvailAmount, dcextaccsummary.MoreOneDayAvailAm, dcextaccsummary.ValueDateTime, dcextaccsummary.ExtendABeanXMLID, dcAccount.CurrencyCode FROM DC_Account dcAccount, DC_ExtAcctSummary dcextaccsummary WHERE dcAccount.DCAccountID = dcextaccsummary.DCAccountID ";
  private static final String f = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.PayeePayor, dcTransactions.Description, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.ReferenceNumber, dcTransactions.Amount FROM DC_Account dcAccount, DC_Transactions dcTransactions ";
  private static final String ba = "SELECT dcAccount.AccountID, dcTransactions.PayeePayor, dcTransactions.Description, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.Amount, dcTransactions.DataDate, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount,dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION FROM DC_Transactions dcTransactions, DC_Account dcAccount, DC_SUBTYPE_DESC subTypeDesc WHERE dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ";
  private static final String P = " AND (dcTransactions.Amount < 0 OR dcTransactions.Amount IS NULL) ";
  private static final String aW = " AND (dcTransactions.Amount >= 0 OR dcTransactions.Amount IS NULL) ";
  private static final String q = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.TransID, dcTransactions.TransTypeID, dcTransactions.TransCategoryID, dcTransactions.Description, dcTransactions.ReferenceNumber, dcTransactions.Memo, dcTransactions.Amount, dcTransactions.RunningBalance, dcTransactions.TransTrackingID, dcTransactions.DCTransactionIndex, dcTransactions.PostingDate, dcTransactions.DueDate, dcTransactions.FixedDepRate, dcTransactions.PayeePayor, dcTransactions.PayorNum, dcTransactions.OrigUser, dcTransactions.PONum, dcTransactions.ImmedAvailAmount, dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.DataDate, dcTransactions.InstNumber, dcTransactions.InstBankName, dcTransactions.ValueDateTime, dcTransactions.TransSubTypeID, dcTransactions.ExtendABeanXMLID, dcTransactions.TransDate, subTypeDesc.DESCRIPTION  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_SUBTYPE_DESC subTypeDesc WHERE  dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ";
  private static final String ax = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.Description, dcTransactions.ReferenceNumber, dcTransactions.Amount, dcTransactions.RunningBalance, dcTransactions.DueDate, dcTransactions.TransSubTypeID, dcTransactions.BankRefNum, dcTransactions.CustRefNum  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_TransTypeDesc transTypeDesc WHERE  transTypeDesc.TRANS_TYPE_ID = dcTransactions.TransTypeID AND transTypeDesc.LOCALE = ? ";
  private static final String A = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.OrigUser, dcTransactions.Description, dcTransactions.Amount, dcTransactions.PayorNum, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount, dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_SUBTYPE_DESC subTypeDesc WHERE  dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ";
  private static final String H = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcAcctHistory.DataDate, dcAcctHistory.CurrentLedger FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount ";
  private static final String jdField_char = "SELECT dcAccount.AccountID, dcAccount.BankID, dcAccount.RoutingNumber, dcAcctHistory.DataDate, dcAcctHistory.OpeningLedger, dcAcctHistory.AvgOpenLedgerMTD, dcAcctHistory.AvgOpenLedgerYTD , dcAcctHistory.ClosingLedger, dcAcctHistory.AvgCloseLedgerMTD, dcAcctHistory.AvgMonth, dcAcctHistory.AggBalanceAdjust, dcAcctHistory.AvCloseLedgYTDPrvM, dcAcctHistory.AvgCloseLedgerYTD, dcAcctHistory.CurrentLedger, dcAcctHistory.ACHNetPosition, dcAcctHistory.OpAvaiSamDayACHDTC, dcAcctHistory.OpeningAvailable, dcAcctHistory.AvgOpenAvailMTD, dcAcctHistory.AvgOpenAvailYTD, dcAcctHistory.AvgAvailPrevMonth, dcAcctHistory.DisbOpenAvailBal, dcAcctHistory.ClosingAvail, dcAcctHistory.AvgCloseAvailMTD, dcAcctHistory.AvCloseAvailPreM, dcAcctHistory.AvCloseAvailYTDPrM, dcAcctHistory.AvCloseAvailYTD, dcAcctHistory.LoanBalance, dcAcctHistory.TotalInvestPosn, dcAcctHistory.CurrAvailCRSSupr, dcAcctHistory.CurrentAvail, dcAcctHistory.AvgCurrAvailMTD, dcAcctHistory.AvgCurrAvailYTD, dcAcctHistory.TotalFloat, dcAcctHistory.TargetBalance, dcAcctHistory.AdjBalance, dcAcctHistory.AdjBalanceMTD, dcAcctHistory.AdjBalanceYTD, dcAcctHistory.ZeroDayFloat, dcAcctHistory.OneDayFloat, dcAcctHistory.FloatAdj, dcAcctHistory.TwoMoreDayFloat, dcAcctHistory.ThreeMoreDayFloat, dcAcctHistory.AdjToBalances, dcAcctHistory.AvgAdjToBalMTD, dcAcctHistory.AvgAdjToBalYTD, dcAcctHistory.FourDayFloat, dcAcctHistory.FiveDayFloat, dcAcctHistory.SixDayFloat, dcAcctHistory.AvgOneDayFloatMTD, dcAcctHistory.AvgOneDayFloatYTD, dcAcctHistory.AvgTwoDayFloatMTD, dcAcctHistory.AvgTwoDayFloatYTD, dcAcctHistory.TransferCalc, dcAcctHistory.TargBalDeficiency, dcAcctHistory.TotalFundingReq, dcAcctHistory.TotalChecksPaid, dcAcctHistory.GrandTotCredMinDeb FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount ";
  private static final String bp = "SELECT SUM(dcTransactions.Amount)  FROM DC_Account dcAccount, DC_Transactions dcTransactions ";
  private static final String ae = "SELECT dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.Amount, dcTransactions.Description, dcAccount.AccountID, dcAccount.RoutingNumber, dcAccount.CurrencyCode FROM DC_Account dcAccount, DC_Transactions dcTransactions WHERE dcAccount.DCAccountID = dcTransactions.DCAccountID";
  private static final a[] ag = { new a(701, "com.ffusion.util.beans.DateTime", 12), new a(1101, "java.lang.String", 18), new a(748, "java.lang.String", 20), new a(747, "java.lang.String", 20), new a(749, "java.lang.String", 15, "RIGHT"), new a(729, "java.lang.String", 15, "RIGHT") };
  private static final a[] ak = ag;
  private static final a[] bi = ag;
  private static final a[] y = { new a(null, "java.lang.String", 40), new a(null, "java.lang.String", 20), new a(755, "java.lang.String", 15), new a(756, "java.lang.String", 15), new a(null, "java.lang.String", 10) };
  private static final a[] e = { new a(null, "java.lang.String", 40), new a(null, "java.lang.String", 20), new a(755, "java.lang.String", 15), new a(null, "java.lang.String", 25) };
  private static final a[] k = { new a(null, "java.lang.String", 40), new a(null, "java.lang.String", 20), new a(756, "java.lang.String", 15), new a(null, "java.lang.String", 25) };
  private static final int bJ = 12;
  private static final int bo = 88;
  private static final int a2 = 12;
  private static final int aT = 88;
  private static final int v = 12;
  private static final int d = 88;
  private static final a[] a4 = { new a(null, "com.ffusion.util.beans.DateTime", 12), new a(null, "java.lang.String", 18), new a(null, "java.lang.String", 20), new a(null, "java.lang.String", 20), new a(null, "java.lang.String", 15, "RIGHT"), new a(null, "java.lang.String", 15, "RIGHT") };
  private static final a[] at = a4;
  private static final a[] aX = a4;
  private static final a[] R = { new a("Date", "com.ffusion.util.beans.DateTime"), new a("AccountId", "java.lang.String"), new a("BankId", "java.lang.String"), new a("RoutingNum", "java.lang.String"), new a("CurrencyCode", "java.lang.String"), new a("DataClassification", "java.lang.String") };
  private static final a[] jdField_case = { new a(-1, "java.lang.String", 100, "LEFT", "reportColumnHeading") };
  private static final a[] I = { new a(248, "com.ffusion.util.beans.DateTime", 12), new a(245, "java.lang.String", 14), new a(249, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(261, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(264, "java.lang.String", 14), new a(265, "java.lang.String", 14) };
  private static final a[] a7 = { new a(248, "com.ffusion.util.beans.DateTime", 10), new a(240, "java.lang.String", 15), new a(245, "java.lang.String", 14), new a(249, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(261, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(264, "java.lang.String", 14), new a(265, "java.lang.String", 14) };
  private static final a[] jdField_try = { new a("Account ID", "java.lang.String", 1), new a("Bank ID", "java.lang.String", 1), new a("Routing Number", "java.lang.String", 1), new a("Currency Code", "java.lang.String", 1), new a("ID", "java.lang.String", 1), new a("Type", "java.lang.Integer", 1), new a("Category", "java.lang.Integer", 1), new a("Description", "java.lang.String", 1), new a("Reference Number", "java.lang.String", 1), new a("Memo", "java.lang.String", 1), new a("Amount", "com.ffusion.beans.Currency", 1), new a("Running Balance", "com.ffusion.beans.Currency", 1), new a("Tracking ID", "java.lang.String", 1), new a("Transaction Index", "java.lang.Long", 1), new a("Posting Date", "com.ffusion.util.beans.DateTime", 1), new a("Due Date", "com.ffusion.util.beans.DateTime", 1), new a("Fixed Deposit Rate", "java.lang.Float", 1), new a("Payee/Payor", "java.lang.String", 1), new a("Payor Num", "java.lang.String", 1), new a("Orig User", "java.lang.String", 1), new a("PO Num", "java.lang.String", 1), new a("Immediate Avalaible Amount", "com.ffusion.beans.Currency", 1), new a("One Day Available Amount", "com.ffusion.beans.Currency", 1), new a("More Than One Day Available Amount", "com.ffusion.beans.Currency", 1), new a("Bank Reference Number", "java.lang.String", 1), new a("Customer Reference Number", "java.lang.String", 1), new a("Processing Date", "com.ffusion.util.beans.DateTime", 1), new a("Instrument Number", "java.lang.String", 1), new a("Instrument Bank Name", "java.lang.String", 1), new a("Value Date", "com.ffusion.util.beans.DateTime", 1), new a("Sub Type", "java.lang.Integer", 1), new a("Date", "com.ffusion.util.beans.DateTime", 1), new a("Data Classification", "java.lang.String", 1) };
  private static final a[] c = { new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 20, "RIGHT"), new a(-1, "java.lang.String", 20, "RIGHT"), new a(-1, "java.lang.String", 30, "LEFT") };
  private static final a[] af = { new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "RIGHT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 20, "LEFT"), new a(-1, "java.lang.String", 20, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "RIGHT"), new a(-1, "java.lang.String", 30, "RIGHT"), new a(-1, "java.lang.String", 30, "RIGHT") };
  private static final a[] aL = { new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 30, "RIGHT"), new a(-1, "java.lang.String", 30, "LEFT"), new a(-1, "java.lang.String", 20, "LEFT"), new a(-1, "java.lang.String", 20, "LEFT"), new a(-1, "java.lang.String", 30, "LEFT") };
  private static final a[] U = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(243, "java.lang.String", 10), new a(245, "java.lang.String", 30), new a(246, "java.lang.String", 10), new a(271, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(270, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(272, "com.ffusion.beans.Currency", 10, "RIGHT") };
  private static final a[] an = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(245, "java.lang.String", 30), new a(276, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(253, "com.ffusion.util.beans.DateTime", 10), new a(277, "com.ffusion.beans.Currency", 10, "RIGHT") };
  private static final a[] ar = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(243, "java.lang.String", 10), new a(245, "java.lang.String", 30), new a(270, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(271, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(273, "com.ffusion.beans.Currency", 10, "RIGHT") };
  private static final a[] bs = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(245, "java.lang.String", 10), new a(274, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(275, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(272, "com.ffusion.beans.Currency", 10, "RIGHT") };
  private static final a[] W = { new a("Account ID", "java.lang.String", 1), new a("Bank ID", "java.lang.String", 1), new a("Routing Number", "java.lang.String", 1), new a("Currency Code", "java.lang.String", 1), new a("Summary Date", "com.ffusion.util.beans.DateTime", 1), new a("Summary Type", "java.lang.Integer", 1), new a("Amount", "com.ffusion.beans.Currency", 1), new a("Immediate Availability Amount", "com.ffusion.beans.Currency", 1), new a("One Day Availability Amount", "com.ffusion.beans.Currency", 1), new a("More Than One Day Availability Amount", "com.ffusion.beans.Currency", 1), new a("Value Date/Time", "com.ffusion.util.beans.DateTime", 1), new a("Data Classification", "java.lang.String", 1) };
  private static final a[] T = { new a(452, "java.lang.Integer", 50), new a(453, "java.lang.String", 50) };
  private static final a[] a6 = { new a(228, "com.ffusion.util.beans.DateTime", 9), new a(229, "java.lang.String", 11), new a(231, "java.lang.String", 28), new a(237, "java.lang.String", 16), new a(238, "java.lang.String", 16), new a(234, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(235, "com.ffusion.beans.Currency", 10, "RIGHT") };
  private static final a[] bm = { new a(225, "java.lang.String", 15), new a(229, "java.lang.String", 10), new a(231, "java.lang.String", 25), new a(237, "java.lang.String", 15), new a(238, "java.lang.String", 15), new a(234, "com.ffusion.beans.Currency", 10, "RIGHT"), new a(235, "com.ffusion.beans.Currency", 10, "RIGHT") };
  private static final a[] bD = { new a(-1, "java.lang.String", 52, "RIGHT", "reportDataSubtotal"), new a(-1, "com.ffusion.beans.Currency", 17, "RIGHT", "reportDataSubtotal"), new a("", "java.lang.String", 16), new a("", "java.lang.String", 16) };
  private static final a[] m = { new a(700, "java.lang.String", 37), new a(667, "java.lang.String", 15), new a(668, "com.ffusion.beans.Currency", 17, "RIGHT"), new a("", "com.ffusion.beans.Currency", 16), new a("", "com.ffusion.beans.Currency", 16) };
  private static final a[] bx = { new a(667, "java.lang.String", 52), new a(668, "com.ffusion.beans.Currency", 17, "RIGHT"), new a("", "com.ffusion.beans.Currency", 16), new a("", "com.ffusion.beans.Currency", 16) };
  private static final a[] am = { new a(150, "java.lang.String", 55), new a(153, "com.ffusion.beans.Currency", 15), new a(156, "com.ffusion.beans.Currency", 15), new a(155, "com.ffusion.beans.Currency", 15) };
  private static final a[] al = { new a(-1, "java.lang.String", 55), new a(-1, "java.lang.String", 15), new a(-1, "java.lang.String", 15), new a(-1, "java.lang.String", 15) };
  private static final a[] a3 = { new a(160, "java.lang.String", 55), new a(163, "com.ffusion.beans.Currency", 15), new a(164, "com.ffusion.beans.Currency", 15), new a(165, "com.ffusion.beans.Currency", 15) };
  private static int[] av = { 657, 658, 659, 660, 661 };
  private static String[] i = { "TYPESTRING==Savings", "TYPESTRING==Checking", "ACCOUNTGROUP=1,and,TYPESTRING!!Savings,and,TYPESTRING!!Checking,and,TYPESTRING!!Fixed Deposit", "TYPESTRING=Fixed Deposit", "ACCOUNTGROUP=2,and,TYPESTRING!!Savings,and,TYPESTRING!!Checking,and,TYPESTRING!!Fixed Deposit" };
  private static int[] aC = { 664, 665 };
  private static String[] bw = { "ACCOUNTGROUP=4", "ACCOUNTGROUP=3" };
  private static final a[] az = { new a(290, "com.ffusion.util.beans.DateTime", 12), new a(291, "java.lang.String", 30), new a(292, "java.lang.String", 13), new a(293, "java.lang.String", 8), new a(294, "com.ffusion.beans.Currency", 14), new a(295, "com.ffusion.beans.Currency", 14), new a(296, "com.ffusion.beans.Currency", 14) };
  private static final a[] bg = { new a(null, "com.ffusion.util.beans.DateTime", 12), new a(null, "java.lang.String", 30), new a(null, "java.lang.String", 13), new a(null, "java.lang.String", 8), new a(null, "com.ffusion.beans.Currency", 14), new a(null, "com.ffusion.beans.Currency", 14), new a(null, "com.ffusion.beans.Currency", 14) };
  private static final a[] be = { new a(290, "com.ffusion.util.beans.DateTime", 12), new a(291, "java.lang.String", 30), new a(292, "java.lang.String", 13), new a(293, "java.lang.String", 8), new a(294, "com.ffusion.beans.Currency", 14), new a(295, "com.ffusion.beans.Currency", 14) };
  private static final a[] bu = { new a(null, "com.ffusion.util.beans.DateTime", 12), new a(null, "java.lang.String", 30), new a(null, "java.lang.String", 13), new a(null, "java.lang.String", 8), new a(null, "com.ffusion.beans.Currency", 14), new a(null, "com.ffusion.beans.Currency", 14) };
  private static final a[] bh = { new a(null, "java.lang.String", 12), new a(null, "java.lang.String", 88) };
  private static final a[] J = { new a(-1, "java.lang.String", 100) };
  private static final a[] aV = { new a("Account ID", "java.lang.String", 1), new a("Bank ID", "java.lang.String", 1), new a("Routing Number", "java.lang.String", 1), new a("Currency Code", "java.lang.String", 1), new a("ID", "java.lang.String", 1), new a("Type", "java.lang.Integer", 1), new a("Category", "java.lang.Integer", 1), new a("Description", "java.lang.String", 1), new a("Reference Number", "java.lang.String", 1), new a("Memo", "java.lang.String", 1), new a("Amount", "com.ffusion.beans.Currency", 1), new a("Running Balance", "com.ffusion.beans.Currency", 1), new a("Tracking ID", "java.lang.String", 1), new a("Transaction Index", "java.lang.Integer", 1), new a("Posting Date", "com.ffusion.util.beans.DateTime", 1), new a("Due Date", "com.ffusion.util.beans.DateTime", 1), new a("Fixed Deposit Rate", "java.lang.Float", 1), new a("Payee/Payor", "java.lang.String", 1), new a("Payor Num", "java.lang.String", 1), new a("Orig User", "java.lang.String", 1), new a("PO Num", "java.lang.String", 1), new a("Immediate Avalaible Amount", "com.ffusion.beans.Currency", 1), new a("One Day Available Amount", "com.ffusion.beans.Currency", 1), new a("More Than One Day Available Amount", "com.ffusion.beans.Currency", 1), new a("Bank Reference Number", "java.lang.String", 1), new a("Customer Reference Number", "java.lang.String", 1), new a("Processing Date", "com.ffusion.util.beans.DateTime", 1), new a("Instrument Number", "java.lang.String", 1), new a("Instrument Bank Name", "java.lang.String", 1), new a("Value Date", "com.ffusion.util.beans.DateTime", 1), new a("Sub Type", "java.lang.Integer", 1), new a("Date", "com.ffusion.util.beans.DateTime", 1), new a("Data Classification", "java.lang.String", 1) };
  
  public static IReportResult getAlertTransactionReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    ReportResult localReportResult = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    Vector localVector = new Vector();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      Locale localLocale = paramReportCriteria.getLocale();
      localReportResult = new ReportResult(localLocale);
      localObject1 = paramReportCriteria.getSearchCriteria();
      try
      {
        StringBuffer localStringBuffer = new StringBuffer("SELECT dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.Amount, dcTransactions.Description, dcAccount.AccountID, dcAccount.RoutingNumber, dcAccount.CurrencyCode FROM DC_Account dcAccount, DC_Transactions dcTransactions WHERE dcAccount.DCAccountID = dcTransactions.DCAccountID");
        String str1 = ((Properties)localObject1).getProperty("DataClassification");
        if ((str1 == null) || (!str1.equals("I"))) {
          str1 = "P";
        }
        localStringBuffer.append(" AND ").append("dcTransactions.DataClassification").append("='").append(DBUtil.escapeSQLStringLiteral(str1)).append("'");
        String str2 = ((Properties)localObject1).getProperty("TransactionType");
        if ((str2 != null) && (!str2.equals("AllTransactionTypes")))
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append("dcTransactions.TransTypeID").append(" = ");
          DBUtil.trimSQLNumericLiteral(str2, localStringBuffer);
        }
        Accounts localAccounts = (Accounts)paramHashMap.get("Accounts");
        Iterator localIterator = localAccounts.iterator();
        localStringBuffer.append(" AND ( ");
        Account localAccount = null;
        while (localIterator.hasNext())
        {
          localAccount = (Account)localIterator.next();
          localStringBuffer.append(" ( ");
          localStringBuffer.append("dcAccount.AccountID");
          localStringBuffer.append(" = '");
          localStringBuffer.append(DBUtil.escapeSQLStringLiteral(localAccount.getID()));
          localStringBuffer.append("' AND ");
          localStringBuffer.append("dcAccount.BankID");
          localStringBuffer.append(" = '");
          localStringBuffer.append(DBUtil.escapeSQLStringLiteral(localAccount.getBankID()));
          if (localAccount.getRoutingNum() != null)
          {
            localStringBuffer.append("' AND ");
            localStringBuffer.append("dcAccount.RoutingNumber");
            localStringBuffer.append(" = '");
            localStringBuffer.append(DBUtil.escapeSQLStringLiteral(localAccount.getRoutingNum()));
          }
          localStringBuffer.append("' ) ");
          if (localIterator.hasNext()) {
            localStringBuffer.append(" OR ");
          }
        }
        localStringBuffer.append(" ) ");
        HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
        HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
        Timestamp[] arrayOfTimestamp = null;
        if ((localHashMap1 != null) && (localHashMap2 != null))
        {
          localObject2 = (Calendar)localHashMap1.get(localAccount.getRoutingNum());
          localObject3 = (Calendar)localHashMap2.get(localAccount.getRoutingNum());
          if ((localObject2 != null) && (localObject3 != null))
          {
            arrayOfTimestamp = new Timestamp[2];
            arrayOfTimestamp[0] = new Timestamp(((Calendar)localObject2).getTime().getTime());
            arrayOfTimestamp[1] = new Timestamp(((Calendar)localObject3).getTime().getTime());
            localStringBuffer.append(" AND ");
            localStringBuffer.append("dcTransactions.DataDate");
            localStringBuffer.append(" >= ? ");
            localStringBuffer.append(" AND ");
            localStringBuffer.append("dcTransactions.DataDate");
            localStringBuffer.append(" <= ? ");
          }
        }
        Object localObject2 = localStringBuffer.toString();
        localPreparedStatement = DCUtil.prepareStatement(localConnection, (String)localObject2, 1004, 1007);
        if (arrayOfTimestamp != null)
        {
          localPreparedStatement.setTimestamp(1, arrayOfTimestamp[0]);
          localPreparedStatement.setTimestamp(2, arrayOfTimestamp[1]);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, (String)localObject2);
        Object localObject3 = TransactionTypeCache.getTransactionTypeCache(paramReportCriteria.getLocale());
        ReportRow localReportRow = null;
        ReportDataItems localReportDataItems = null;
        while (localResultSet.next())
        {
          localReportRow = new ReportRow(localLocale);
          localReportDataItems = new ReportDataItems(localLocale);
          localReportDataItems.add().setData(a(localResultSet, 1, localLocale));
          Integer localInteger = new Integer(localResultSet.getInt(2));
          String str3 = (String)((HashMap)localObject3).get(localInteger);
          localReportDataItems.add().setData(str3);
          localReportDataItems.add().setData(jdField_if(localResultSet, 3, localLocale));
          localReportDataItems.add().setData(localResultSet.getString(4));
          localReportDataItems.add().setData(localResultSet.getString(5));
          localReportRow.setDataItems(localReportDataItems);
          localReportResult.addRow(localReportRow);
        }
      }
      catch (Exception localException2)
      {
        localReportResult.setError(localException2);
        throw localException2;
      }
    }
    catch (DCException localDCException)
    {
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      Object localObject1 = "Exception when attempting to get report data.";
      throw new DCException(313, (String)localObject1, localException1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
    return localReportResult;
  }
  
  private static void a(ResultSet paramResultSet1, ResultSet paramResultSet2, ReportResult paramReportResult, int paramInt, Currency paramCurrency1, Currency paramCurrency2, Account paramAccount, SecureUser paramSecureUser)
    throws DCException, RptException, SQLException, CSILException
  {
    Locale localLocale = paramReportResult.getLocale();
    Currency localCurrency1 = null;
    Currency localCurrency2 = null;
    Currency localCurrency3 = null;
    if (paramResultSet1.next()) {
      localCurrency1 = a(paramResultSet1, 8, localLocale, paramAccount.getCurrencyCode());
    }
    if (paramResultSet2.next()) {
      localCurrency2 = a(paramResultSet2, 1, localLocale, paramAccount.getCurrencyCode());
    }
    if (localCurrency1 != null)
    {
      localCurrency3 = new Currency(localCurrency1.getAmountValue(), localCurrency1.getLocale());
      if (localCurrency2 == null) {
        localCurrency2 = new Currency(new BigDecimal("0.0"), paramAccount.getCurrencyCode(), localLocale);
      }
      localCurrency3.addAmount(localCurrency2);
    }
    HashMap localHashMap = new HashMap();
    if (localCurrency1 != null) {
      if (paramCurrency1.getCurrencyCode().equals(localCurrency1.getCurrencyCode())) {
        paramCurrency1.addAmount(localCurrency1);
      } else {
        paramCurrency1.addAmount(FX.convertToBaseCurrency(paramSecureUser, localCurrency1, localHashMap));
      }
    }
    if (localCurrency3 != null) {
      if (paramCurrency2.getCurrencyCode().equals(localCurrency3.getCurrencyCode())) {
        paramCurrency2.addAmount(localCurrency3);
      } else {
        paramCurrency2.addAmount(FX.convertToBaseCurrency(paramSecureUser, localCurrency3, localHashMap));
      }
    }
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    String str1 = null;
    String str2 = null;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramAccount.getRoutingNum());
    try
    {
      str2 = AccountUtil.buildAccountDisplayText(paramAccount.getID(), localLocale);
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing("Error while constructing account display string.", localUtilException);
      str2 = paramAccount.getID();
    }
    localArrayList.add(str2);
    int i1 = (paramAccount.getNickName() != null) && (paramAccount.getNickName().length() > 0) ? 1 : 0;
    int i2 = paramAccount.getCurrencyCode() != null ? 1 : 0;
    if ((i1 != 0) && (i2 != 0)) {
      str1 = "Cash_Flow_Report_Account_With_Nickname_And_Currency_Code_Format";
    } else if (i1 != 0) {
      str1 = "Cash_Flow_Report_Account_With_Nickname_Format";
    } else if (i2 != 0) {
      str1 = "Cash_Flow_Report_Account_With_Currency_Code_Format";
    } else {
      str1 = "Cash_Flow_Report_Account_With_No_Other_Information_Format";
    }
    if (i1 != 0) {
      localArrayList.add(paramAccount.getNickName());
    }
    if (i2 != 0) {
      localArrayList.add(paramAccount.getCurrencyCode());
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", str1, localArrayList.toArray());
    jdField_if(localReportDataItems, localLocalizableString.localize(localLocale));
    if (localCurrency1 == null) {
      localReportDataItems.add(null);
    } else {
      localReportDataItems.add().setData(localCurrency1);
    }
    if (localCurrency2 == null) {
      localReportDataItems.add(null);
    } else {
      localReportDataItems.add().setData(localCurrency2);
    }
    if (localCurrency3 == null) {
      localReportDataItems.add(null);
    } else {
      localReportDataItems.add().setData(localCurrency3);
    }
    a(paramReportResult, localReportDataItems, paramInt);
  }
  
  private static Account jdField_if(String paramString1, String paramString2)
    throws DCException
  {
    Account localAccount = new Account();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",");
    int i1 = localStringTokenizer.countTokens();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i2 = 0; i2 < i1; i2++)
    {
      FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer(localStringTokenizer.nextToken(), ":");
      int i3 = localFFIStringTokenizer.countTokens();
      if (i3 < 2) {
        throw new DCException("The account id and bank ID were not correctly passed in as a report criteria. The proper format is AccountID:BankID:RoutingNumber , where RoutingNumber is optional");
      }
      String str = localFFIStringTokenizer.nextToken();
      if (str.equals(paramString2))
      {
        localAccount.setID(str);
        localAccount.setBankID(localFFIStringTokenizer.nextToken());
        if (i3 >= 3) {
          localAccount.setRoutingNum(localFFIStringTokenizer.nextToken());
        }
        if (i3 >= 4) {
          localAccount.setNickName(localFFIStringTokenizer.nextToken());
        }
        if (i3 >= 5) {
          localAccount.setCurrencyCode(localFFIStringTokenizer.nextToken());
        }
        return localAccount;
      }
    }
    return localAccount;
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, com.ffusion.beans.DateTime paramDateTime, String paramString, HashMap paramHashMap)
    throws RptException, SQLException, CSILException, DCException
  {
    Locale localLocale = paramReportResult.getLocale();
    Accounts localAccounts = (Accounts)paramHashMap.get("Accounts");
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), localSecureUser.getBaseCurrency(), localLocale);
    Currency localCurrency2 = new Currency(new BigDecimal(0.0D), localSecureUser.getBaseCurrency(), localLocale);
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, 4, -1);
    a.a(localReportResult, localLocale, am);
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    CurrencyEstimate localCurrencyEstimate = new CurrencyEstimate();
    Object localObject6 = null;
    Object localObject7 = null;
    int i1 = 1;
    String str5;
    ArrayList localArrayList;
    int i4;
    LocalizableString localLocalizableString;
    Currency localCurrency4;
    double d2;
    Currency localCurrency5;
    while (paramResultSet.next())
    {
      String str1 = paramResultSet.getString(1);
      String str2 = paramResultSet.getString(2);
      String str3 = paramResultSet.getString(3);
      String str4 = paramResultSet.getString(4);
      com.ffusion.beans.DateTime localDateTime = a(paramResultSet, 5, localLocale);
      Currency localCurrency3 = a(paramResultSet, 6, localLocale, str4);
      if (localObject2 == null)
      {
        localObject2 = str1;
        localObject3 = str2;
        localObject4 = str3;
        localObject5 = str4;
      }
      else if ((!str1.equals(localObject2)) || (!str2.equals(localObject3)) || ((str3 != localObject4) && ((str3 == null) || (!str3.equals(localObject4)))))
      {
        if ((localObject6 != null) && (localObject7 != null))
        {
          localCurrencyEstimate.addPoint((com.ffusion.beans.DateTime)localObject6, (Currency)localObject7);
          if (localObject1 == null) {
            localObject1 = localObject7;
          }
          localObject7 = null;
          localObject6 = null;
        }
        localObject8 = new ReportDataItems(localLocale);
        localObject9 = null;
        if (localAccounts == null) {
          localObject9 = jdField_if(paramString, localObject2);
        } else {
          localObject9 = localAccounts.getByID(localObject2);
        }
        localObject10 = null;
        str5 = null;
        localArrayList = new ArrayList();
        localArrayList.add(((Account)localObject9).getRoutingNum());
        try
        {
          str5 = AccountUtil.buildAccountDisplayText(((Account)localObject9).getID(), paramReportResult.getLocale());
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string.", localUtilException1);
          str5 = ((Account)localObject9).getID();
        }
        localArrayList.add(str5);
        int i2 = (((Account)localObject9).getNickName() != null) && (((Account)localObject9).getNickName().length() > 0) ? 1 : 0;
        i4 = ((Account)localObject9).getCurrencyCode() != null ? 1 : 0;
        if ((i2 != 0) && (i4 != 0)) {
          localObject10 = "Cash_Flow_Forecast_Report_Account_With_Nickname_And_Currency_Code_Format";
        } else if (i2 != 0) {
          localObject10 = "Cash_Flow_Forecast_Report_Account_With_Nickname_Format";
        } else if (i4 != 0) {
          localObject10 = "Cash_Flow_Forecast_Report_Account_With_Currency_Code_Format";
        } else {
          localObject10 = "Cash_Flow_Forecast_Report_Account_With_No_Other_Information_Format";
        }
        if (i2 != 0) {
          localArrayList.add(((Account)localObject9).getNickName());
        }
        if (i4 != 0) {
          localArrayList.add(((Account)localObject9).getCurrencyCode());
        }
        localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject10, localArrayList.toArray());
        ((ReportDataItems)localObject8).add().setData(localLocalizableString.localize(paramReportResult.getLocale()));
        if (localObject1 != null)
        {
          ((ReportDataItems)localObject8).add().setData(localObject1);
          localCurrencyEstimate.calculateRegression();
          localCurrency4 = localCurrencyEstimate.getEstimated(paramDateTime);
          d2 = localCurrency4.doubleValue() - localObject1.doubleValue();
          localCurrency5 = new Currency(new BigDecimal(d2), localCurrency4.getCurrencyCode(), localLocale);
          ((ReportDataItems)localObject8).add().setData(localCurrency5);
          ((ReportDataItems)localObject8).add().setData(localCurrency4);
          if (localCurrency1.getCurrencyCode().equals(localObject1.getCurrencyCode()))
          {
            localCurrency1.addAmount(localObject1);
            localCurrency2.addAmount(localCurrency4);
          }
          else
          {
            localCurrency1.addAmount(FX.convertToBaseCurrency(localSecureUser, localObject1, paramHashMap));
            localCurrency2.addAmount(FX.convertToBaseCurrency(localSecureUser, localCurrency4, paramHashMap));
          }
        }
        else
        {
          ((ReportDataItems)localObject8).add(null);
          ((ReportDataItems)localObject8).add(null);
          ((ReportDataItems)localObject8).add(null);
        }
        a(localReportResult, (ReportDataItems)localObject8, i1);
        i1++;
        localCurrencyEstimate = new CurrencyEstimate();
        localObject1 = null;
        localObject2 = str1;
        localObject3 = str2;
        localObject4 = str3;
        localObject5 = str4;
      }
      if ((localObject6 != null) && (!localDateTime.isSameDayInYearAs((com.ffusion.util.beans.DateTime)localObject6)) && (localObject6 != null) && (localObject7 != null))
      {
        localCurrencyEstimate.addPoint((com.ffusion.beans.DateTime)localObject6, (Currency)localObject7);
        if (localObject1 == null) {
          localObject1 = localObject7;
        }
        localObject7 = null;
        localObject6 = null;
      }
      if (localCurrency3 != null)
      {
        localObject7 = localCurrency3;
        localObject6 = localDateTime;
      }
    }
    if (localObject2 != null)
    {
      if ((localObject6 != null) && (localObject7 != null))
      {
        localCurrencyEstimate.addPoint((com.ffusion.beans.DateTime)localObject6, (Currency)localObject7);
        if (localObject1 == null) {
          localObject1 = localObject7;
        }
        localObject7 = null;
        localObject6 = null;
      }
      localObject8 = new ReportDataItems(localLocale);
      localObject9 = null;
      if (localAccounts == null) {
        localObject9 = jdField_if(paramString, localObject2);
      } else {
        localObject9 = localAccounts.getByID(localObject2);
      }
      localObject10 = null;
      str5 = null;
      localArrayList = new ArrayList();
      localArrayList.add(((Account)localObject9).getRoutingNum());
      try
      {
        str5 = AccountUtil.buildAccountDisplayText(((Account)localObject9).getID(), paramReportResult.getLocale());
      }
      catch (UtilException localUtilException2)
      {
        DebugLog.throwing("Error while constructing account display string.", localUtilException2);
        str5 = ((Account)localObject9).getID();
      }
      localArrayList.add(str5);
      int i3 = (((Account)localObject9).getNickName() != null) && (((Account)localObject9).getNickName().length() > 0) ? 1 : 0;
      i4 = ((Account)localObject9).getCurrencyCode() != null ? 1 : 0;
      if ((i3 != 0) && (i4 != 0)) {
        localObject10 = "Cash_Flow_Forecast_Report_Account_With_Nickname_And_Currency_Code_Format";
      } else if (i3 != 0) {
        localObject10 = "Cash_Flow_Forecast_Report_Account_With_Nickname_Format";
      } else if (i4 != 0) {
        localObject10 = "Cash_Flow_Forecast_Report_Account_With_Currency_Code_Format";
      } else {
        localObject10 = "Cash_Flow_Forecast_Report_Account_With_No_Other_Information_Format";
      }
      if (i3 != 0) {
        localArrayList.add(((Account)localObject9).getNickName());
      }
      if (i4 != 0) {
        localArrayList.add(((Account)localObject9).getCurrencyCode());
      }
      localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject10, localArrayList.toArray());
      ((ReportDataItems)localObject8).add().setData(localLocalizableString.localize(paramReportResult.getLocale()));
      if (localObject1 != null)
      {
        ((ReportDataItems)localObject8).add().setData(localObject1);
        localCurrencyEstimate.calculateRegression();
        localCurrency4 = localCurrencyEstimate.getEstimated(paramDateTime);
        localCurrency4.setCurrencyCode(localObject5);
        d2 = localCurrency4.doubleValue() - localObject1.doubleValue();
        localCurrency5 = new Currency(new BigDecimal(d2), localObject5, localLocale);
        ((ReportDataItems)localObject8).add().setData(localCurrency5);
        ((ReportDataItems)localObject8).add().setData(localCurrency4);
        if (localCurrency1.getCurrencyCode().equals(localObject1.getCurrencyCode()))
        {
          localCurrency1.addAmount(localObject1);
          localCurrency2.addAmount(localCurrency4);
        }
        else
        {
          localCurrency1.addAmount(FX.convertToBaseCurrency(localSecureUser, localObject1, paramHashMap));
          localCurrency2.addAmount(FX.convertToBaseCurrency(localSecureUser, localCurrency4, paramHashMap));
        }
      }
      else
      {
        ((ReportDataItems)localObject8).add(null);
        ((ReportDataItems)localObject8).add(null);
        ((ReportDataItems)localObject8).add(null);
      }
      a(localReportResult, (ReportDataItems)localObject8, i1);
      i1++;
    }
    Object localObject8 = new Object[1];
    localObject8[0] = localCurrency1.getCurrencyCode();
    Object localObject9 = new LocalizableString("com.ffusion.beans.reporting.reports", "Cash_Flow_Forecast_Report_Totals_Format", (Object[])localObject8);
    a(paramReportResult, (String)((LocalizableString)localObject9).localize(paramReportResult.getLocale()));
    a(paramReportResult, 4, 1);
    a.a(paramReportResult, localLocale, al);
    Object localObject10 = new ReportDataItems(localLocale);
    ((ReportDataItems)localObject10).add(null);
    ((ReportDataItems)localObject10).add().setData(localCurrency1.toString());
    double d1 = localCurrency2.doubleValue() - localCurrency1.doubleValue();
    ((ReportDataItems)localObject10).add().setData(new Currency(new BigDecimal(d1), localLocale).toString());
    ((ReportDataItems)localObject10).add().setData(localCurrency2.toString());
    a(paramReportResult, (ReportDataItems)localObject10, -1);
  }
  
  private static void a(ReportResult paramReportResult, SecureUser paramSecureUser, Locale paramLocale, Accounts paramAccounts, a[] paramArrayOfa, String paramString, int[] paramArrayOfInt, String[] paramArrayOfString, boolean paramBoolean)
    throws RptException, CSILException
  {
    a(paramReportResult, 0, 0);
    String str = paramAccounts.getFilter();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    for (int i1 = 0; i1 < paramAccounts.size(); i1++)
    {
      localObject1 = (Account)paramAccounts.get(i1);
      if (!localArrayList1.contains(((Account)localObject1).getCurrencyCode())) {
        localArrayList1.add(((Account)localObject1).getCurrencyCode());
      }
    }
    Currency localCurrency1 = new Currency("0", paramSecureUser.getBaseCurrency(), paramLocale);
    Object localObject1 = new HashMap();
    Object localObject3;
    int i7;
    Object localObject5;
    Object localObject6;
    ReportDataItems localReportDataItems;
    Object localObject4;
    for (int i2 = 0; i2 < localArrayList1.size(); i2++)
    {
      localObject2 = (String)localArrayList1.get(i2);
      int i3 = 0;
      i4 = 0;
      for (int i5 = 0; i5 < paramArrayOfInt.length; i5++)
      {
        paramAccounts.setFilter("CURRENCY_CODE=" + (String)localObject2 + ",AND," + paramArrayOfString[i5]);
        if (paramAccounts.size() != 0)
        {
          i3++;
          i4 += paramAccounts.size();
        }
      }
      if (i3 != 0)
      {
        ReportResult localReportResult2 = new ReportResult(paramLocale);
        paramReportResult.addSubReport(localReportResult2);
        if (paramBoolean)
        {
          a(localReportResult2, bx.length, i3);
          a.a(localReportResult2, paramLocale, bx);
        }
        else
        {
          a(localReportResult2, m.length, i4);
          a.a(localReportResult2, paramLocale, m);
        }
        localObject3 = new Currency("0", (String)localObject2, paramLocale);
        i7 = 1;
        for (int i8 = 0; i8 < paramArrayOfInt.length; i8++)
        {
          paramAccounts.setFilter("CURRENCY_CODE=" + (String)localObject2 + ",AND," + paramArrayOfString[i8]);
          if (paramAccounts.size() != 0) {
            if (paramBoolean)
            {
              localObject5 = new ReportDataItems(paramLocale);
              ((ReportDataItems)localObject5).add().setData(ReportConsts.getColumnName(paramArrayOfInt[i8], paramLocale));
              localObject6 = paramAccounts.getAccountsClosingBalanceTotalCurrency();
              ((Currency)localObject6).setCurrencyCode((String)localObject2);
              ((ReportDataItems)localObject5).add().setData(localObject6);
              ((ReportDataItems)localObject5).add().setData(null);
              ((ReportDataItems)localObject5).add().setData(null);
              a(localReportResult2, (ReportDataItems)localObject5, i7);
              i7++;
              ((Currency)localObject3).addAmount((Currency)localObject6);
            }
            else
            {
              localObject5 = paramAccounts.iterator();
              while (((Iterator)localObject5).hasNext())
              {
                localObject6 = (Account)((Iterator)localObject5).next();
                ((Account)localObject6).setLocale(paramLocale);
                localReportDataItems = new ReportDataItems(paramLocale);
                Currency localCurrency2 = ((Account)localObject6).getClosingBalance().getAmountValue();
                localCurrency2.setCurrencyCode((String)localObject2);
                localReportDataItems.add().setData(a((Account)localObject6));
                localReportDataItems.add().setData(Account.getType(((Account)localObject6).getTypeValue(), paramLocale));
                localReportDataItems.add().setData(localCurrency2);
                localReportDataItems.add().setData(null);
                localReportDataItems.add().setData(null);
                a(localReportResult2, localReportDataItems, i7);
                i7++;
                ((Currency)localObject3).addAmount(localCurrency2);
              }
            }
          }
        }
        localArrayList2.add(localObject2);
        localObject4 = new ReportResult(paramLocale);
        paramReportResult.addSubReport((ReportResult)localObject4);
        a((ReportResult)localObject4, 4, 1);
        a.a((ReportResult)localObject4, paramLocale, paramArrayOfa);
        paramArrayOfa[0].a("");
        localObject5 = new ReportDataItems(paramLocale);
        ((ReportDataItems)localObject5).add().setData(paramString + " (" + ((Currency)localObject3).getCurrencyCode() + ")");
        ((ReportDataItems)localObject5).add().setData(localObject3);
        ((ReportDataItems)localObject5).add().setData(null);
        ((ReportDataItems)localObject5).add().setData(null);
        localObject6 = new ReportRow(paramLocale);
        ((ReportRow)localObject6).setDataItems((ReportDataItems)localObject5);
        ((ReportResult)localObject4).addRow((ReportRow)localObject6);
        if (localCurrency1.getCurrencyCode().equals(((Currency)localObject3).getCurrencyCode())) {
          localCurrency1.addAmount((Currency)localObject3);
        } else {
          localCurrency1.addAmount(FX.convertToBaseCurrency(paramSecureUser, (Currency)localObject3, (HashMap)localObject1));
        }
      }
    }
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult1);
    a(localReportResult1, 4, 1);
    a.a(localReportResult1, paramLocale, paramArrayOfa);
    Object localObject2 = new ReportDataItems(paramLocale);
    ((ReportDataItems)localObject2).add().setData(paramString);
    ((ReportDataItems)localObject2).add().setData(localCurrency1);
    ((ReportDataItems)localObject2).add().setData(null);
    ((ReportDataItems)localObject2).add().setData(null);
    ReportRow localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems((ReportDataItems)localObject2);
    localReportResult1.addRow(localReportRow);
    int i4 = 0;
    for (int i6 = 0; i6 < localArrayList2.size(); i6++)
    {
      localObject3 = (String)localArrayList1.get(i6);
      if (!((String)localObject3).equals(paramSecureUser.getBaseCurrency())) {
        i4++;
      }
    }
    if (i4 > 0)
    {
      ReportResult localReportResult3 = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult3);
      a(localReportResult3, 2, i4);
      localObject3 = new a[] { new a("", "java.lang.String", 52 - paramString.length(), "LEFT"), new a("", "java.lang.String", 100 - (52 - paramString.length()), "LEFT") };
      a.a(localReportResult3, paramLocale, (a[])localObject3);
      for (i7 = 0; i7 < localArrayList2.size(); i7++)
      {
        localObject4 = (String)localArrayList1.get(i7);
        if (!((String)localObject4).equals(paramSecureUser.getBaseCurrency()))
        {
          localObject5 = new StringBuffer("1 ");
          ((StringBuffer)localObject5).append((String)localObject4);
          ((StringBuffer)localObject5).append(" = ");
          localObject6 = FX.getFXRate(paramSecureUser, (String)localObject4, paramSecureUser.getBaseCurrency(), (HashMap)localObject1);
          ((StringBuffer)localObject5).append(((FXRate)localObject6).getBuyPrice().getAmountValue().toString());
          ((StringBuffer)localObject5).append(" ");
          ((StringBuffer)localObject5).append(paramSecureUser.getBaseCurrency());
          localReportDataItems = new ReportDataItems(paramLocale);
          localReportDataItems.add().setData(null);
          localReportDataItems.add().setData(((StringBuffer)localObject5).toString());
          a(localReportResult3, localReportDataItems, -1);
        }
      }
    }
    paramAccounts.setFilter(str);
  }
  
  private static void a(ReportResult paramReportResult, SecureUser paramSecureUser, Accounts paramAccounts, boolean paramBoolean)
    throws RptException, CSILException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    jdField_if(localReportResult1, ReportConsts.getColumnName(656, localLocale));
    a(localReportResult1, paramSecureUser, localLocale, paramAccounts, bD, ReportConsts.getColumnName(662, localLocale), av, i, paramBoolean);
    ReportResult localReportResult2 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult2);
    jdField_if(localReportResult2, ReportConsts.getColumnName(663, localLocale));
    a(localReportResult2, paramSecureUser, localLocale, paramAccounts, bD, ReportConsts.getColumnName(666, localLocale), aC, bw, paramBoolean);
    a(paramReportResult, 0, 0);
  }
  
  private static void a(Accounts paramAccounts1, Accounts paramAccounts2)
  {
    for (int i1 = 0; i1 < paramAccounts1.size(); i1++)
    {
      Account localAccount1 = (Account)paramAccounts1.get(i1);
      String str1 = localAccount1.getID();
      String str2 = localAccount1.getBankID();
      String str3 = localAccount1.getRoutingNum();
      if ((str1 != null) && (str2 != null) && (str3 != null))
      {
        Account localAccount2 = paramAccounts2.getByIDAndBankIDAndRoutingNum(str1, str2, str3);
        if (localAccount2 != null)
        {
          String str4 = localAccount2.getNickName();
          if (str4 != null) {
            localAccount1.setNickName(str4);
          }
        }
      }
    }
  }
  
  private static Balance a(Account paramAccount, Connection paramConnection)
    throws DCException
  {
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    Balance localBalance = new Balance();
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT CurrentAvail from DC_AccountHistory dcAcctHistory, DC_Account dcAccount WHERE CurrentAvail IS NOT null AND dcAccount.AccountID = ? AND dcAccount.BankID = ? AND dcAccount.RoutingNumber = ? AND dcAcctHistory.DCAccountID = dcAccount.DCAccountID ORDER BY dcAcctHistory.DataDate DESC");
      localPreparedStatement.setString(1, paramAccount.getID());
      localPreparedStatement.setString(2, paramAccount.getBankID());
      localPreparedStatement.setString(3, paramAccount.getRoutingNum());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT CurrentAvail from DC_AccountHistory dcAcctHistory, DC_Account dcAccount WHERE CurrentAvail IS NOT null AND dcAccount.AccountID = ? AND dcAccount.BankID = ? AND dcAccount.RoutingNumber = ? AND dcAcctHistory.DCAccountID = dcAccount.DCAccountID ORDER BY dcAcctHistory.DataDate DESC");
      if (localResultSet.next()) {
        localBalance.setAmount(DCUtil.getCurrencyColumn(localResultSet.getBigDecimal(1), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      } else {
        localBalance = null;
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
    return localBalance;
  }
  
  private static Accounts a(ResultSet paramResultSet, Locale paramLocale, Connection paramConnection)
    throws Exception
  {
    Accounts localAccounts = new Accounts();
    Account localAccount1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    while (paramResultSet.next())
    {
      str1 = paramResultSet.getString(1);
      str2 = paramResultSet.getString(2);
      localAccount1 = localAccounts.getByID(str1);
      str3 = paramResultSet.getString(3);
      if (localAccount1 == null)
      {
        localObject = null;
        int i1 = 0;
        Account localAccount2 = AccountAdapter.getAccount(str2, str3, str1);
        localObject = localAccount2.getNumber();
        i1 = localAccount2.getTypeValue();
        localAccount1 = localAccounts.create(str2, str1, (String)localObject, i1);
        localAccount1.setRoutingNum(paramResultSet.getString(3));
        localAccount1.setCurrencyCode(paramResultSet.getString(4));
        localAccount1.setAvailableBalance(a(localAccount1, paramConnection));
        localAccount1.setAccountGroup(DCUtil.mapAccountType(i1));
      }
      Object localObject = new Balance(paramLocale);
      ((Balance)localObject).setAmount(jdField_if(paramResultSet, 5, paramLocale));
      localAccount1.setClosingBalance((Balance)localObject);
    }
    return localAccounts;
  }
  
  private static void a(ResultSet paramResultSet, ReportResult paramReportResult, SecureUser paramSecureUser, Accounts paramAccounts, Connection paramConnection, boolean paramBoolean, String paramString, int paramInt)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), paramSecureUser.getBaseCurrency(), localLocale);
    Currency localCurrency2 = new Currency(new BigDecimal(0.0D), paramSecureUser.getBaseCurrency(), localLocale);
    a[] arrayOfa;
    if (paramBoolean) {
      arrayOfa = a6;
    } else {
      arrayOfa = bm;
    }
    int i1 = 0;
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(0);
    localReportDataDimensions.setNumRows(0);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportResult localReportResult = null;
    Object localObject5;
    Object localObject6;
    for (int i2 = 1; paramResultSet.next(); i2++)
    {
      i1++;
      if ((paramInt != -1) && (i1 > paramInt))
      {
        localObject5 = new HashMap();
        ((HashMap)localObject5).put("TRUNCATED", Integer.toString(i1 - 1));
        paramReportResult.setProperties((HashMap)localObject5);
        break;
      }
      localObject5 = paramResultSet.getString(1);
      String str1 = paramResultSet.getString(2);
      localObject6 = paramResultSet.getString(3);
      Account localAccount = paramAccounts.getByIDAndBankIDAndRoutingNum((String)localObject5, str1, (String)localObject6);
      localAccount.setLocale(localLocale);
      com.ffusion.beans.DateTime localDateTime = a(paramResultSet, 5, localLocale);
      int i4 = 0;
      String str2 = null;
      if (paramBoolean)
      {
        if ((!((String)localObject5).equals(localObject1)) || (!str1.equals(localObject2)) || (!((String)localObject6).equals(localObject3)))
        {
          i4 = 1;
          i2 = 1;
          localObject1 = localObject5;
          localObject2 = str1;
          localObject3 = localObject6;
          str2 = a(localAccount);
        }
      }
      else if (!localDateTime.equals(localObject4))
      {
        i4 = 1;
        i2 = 1;
        localDateTime.setFormat(paramString);
        localObject4 = localDateTime;
        str2 = localDateTime.toString();
      }
      if (i4 != 0)
      {
        if (localReportResult != null)
        {
          localObject7 = new ReportDataItems(localLocale);
          for (int i5 = arrayOfa.length - 3; i5 > 0; i5--) {
            ((ReportDataItems)localObject7).add(null);
          }
          localObject8 = new Object[1];
          localObject8[0] = localCurrency1.getCurrencyCode();
          localObject9 = new LocalizableString("com.ffusion.beans.reporting.reports", "General_Ledger_Report_Totals_Format", (Object[])localObject8);
          ((ReportDataItems)localObject7).add().setData(((LocalizableString)localObject9).localize(localLocale));
          ((ReportDataItems)localObject7).add().setData(localCurrency2);
          ((ReportDataItems)localObject7).add().setData(localCurrency1);
          a(localReportResult, (ReportDataItems)localObject7, -1);
          localCurrency1 = new Currency(new BigDecimal(0.0D), paramSecureUser.getBaseCurrency(), localLocale);
          localCurrency2 = new Currency(new BigDecimal(0.0D), paramSecureUser.getBaseCurrency(), localLocale);
        }
        localReportResult = new ReportResult(localLocale);
        paramReportResult.addSubReport(localReportResult);
        localObject7 = new ReportHeading(localLocale);
        ((ReportHeading)localObject7).setLabel(str2);
        localReportResult.setHeading((ReportHeading)localObject7);
        localReportDataDimensions = new ReportDataDimensions(localLocale);
        localReportDataDimensions.setNumColumns(arrayOfa.length);
        localReportDataDimensions.setNumRows(-1);
        localReportResult.setDataDimensions(localReportDataDimensions);
        a.a(localReportResult, localLocale, arrayOfa);
      }
      Object localObject7 = new ReportDataItems(localLocale);
      if (paramBoolean) {
        jdField_if((ReportDataItems)localObject7, localDateTime);
      } else {
        ((ReportDataItems)localObject7).add().setData(a(localAccount));
      }
      Object localObject8 = Banking.getTransactionTypeInfo(paramResultSet.getInt(6), new HashMap());
      jdField_if((ReportDataItems)localObject7, ((TransactionTypeInfo)localObject8).getDescription(localLocale));
      Object localObject9 = paramResultSet.getString(8);
      if ((localObject9 != null) && (((String)localObject9).trim().length() == 0)) {
        localObject9 = null;
      }
      jdField_if((ReportDataItems)localObject7, localObject9);
      jdField_if((ReportDataItems)localObject7, DCAdapter.unPadRefNum(paramResultSet.getString(9), DCAdapter.getBankReferenceNumberType()));
      jdField_if((ReportDataItems)localObject7, DCAdapter.unPadRefNum(paramResultSet.getString(10), DCAdapter.getCustomerReferenceNumberType()));
      Currency localCurrency3 = a(paramResultSet, 12, localLocale, localAccount.getCurrencyCode());
      HashMap localHashMap = new HashMap();
      if (localCurrency3.doubleValue() >= 0.0D)
      {
        if (localCurrency1.getCurrencyCode().equals(localCurrency3.getCurrencyCode())) {
          localCurrency1.addAmount(localCurrency3);
        } else {
          localCurrency1.addAmount(FX.convertToBaseCurrency(paramSecureUser, localCurrency3, localHashMap));
        }
        ((ReportDataItems)localObject7).add(null);
        ((ReportDataItems)localObject7).add().setData(localCurrency3);
      }
      else
      {
        localCurrency3.setAmount(localCurrency3.getAmountValue().negate());
        if (localCurrency2.getCurrencyCode().equals(localCurrency3.getCurrencyCode())) {
          localCurrency2.addAmount(localCurrency3);
        } else {
          localCurrency2.addAmount(FX.convertToBaseCurrency(paramSecureUser, localCurrency3, localHashMap));
        }
        ((ReportDataItems)localObject7).add().setData(localCurrency3);
        ((ReportDataItems)localObject7).add(null);
      }
      a(localReportResult, (ReportDataItems)localObject7, i2);
    }
    if (localReportResult != null)
    {
      localObject5 = new ReportDataItems(localLocale);
      for (int i3 = arrayOfa.length - 3; i3 > 0; i3--) {
        ((ReportDataItems)localObject5).add(null);
      }
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localCurrency1.getCurrencyCode();
      localObject6 = new LocalizableString("com.ffusion.beans.reporting.reports", "General_Ledger_Report_Totals_Format", arrayOfObject);
      ((ReportDataItems)localObject5).add().setData(((LocalizableString)localObject6).localize(localLocale));
      ((ReportDataItems)localObject5).add().setData(localCurrency2);
      ((ReportDataItems)localObject5).add().setData(localCurrency1);
      a(localReportResult, (ReportDataItems)localObject5, -1);
    }
  }
  
  private static String jdField_if(ReportCriteria paramReportCriteria, Vector paramVector, HashMap paramHashMap, String paramString)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer = a(paramReportCriteria, paramString, paramHashMap);
    String str1 = new String(localStringBuffer).trim();
    if (str1.length() != 0) {
      localStringBuffer.append(" AND ");
    }
    String str2 = (String)localProperties.get("Accounts");
    if (str2 == null) {
      str2 = "AllAccounts";
    } else if (accountCriterionHasAllAccounts(str2)) {
      str2 = "AllAccounts";
    }
    String str3 = a(str2, paramString, paramVector, null, false, paramReportCriteria, paramHashMap);
    if (str3 == null) {
      return null;
    }
    if (str3.length() != 0) {
      localStringBuffer.append(str3);
    }
    if (paramString.equals("GeneralLedgerReport"))
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append("dcAccount.DCAccountID = dcTransactions.DCAccountID");
    }
    else if ((!paramString.equals("BalanceSheetSummary")) && (!paramString.equals("BalanceSheetReport")) && ((paramString.equals("CashFlowForecastReport")) || (paramString.equals("CashFlowReport"))))
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append("dcAccount.DCAccountID = dcAcctHistory.DCAccountID");
    }
    return localStringBuffer.toString();
  }
  
  private static String a(ReportCriteria paramReportCriteria, Vector paramVector, HashMap paramHashMap)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer = a(paramReportCriteria, "CashFlowNetIncrease", paramHashMap);
    String str1 = new String(localStringBuffer).trim();
    if (str1.length() != 0) {
      localStringBuffer.append(" AND ");
    }
    String str2 = (String)localProperties.get("Accounts");
    if (str2 == null) {
      str2 = "AllAccounts";
    } else if (accountCriterionHasAllAccounts(str2)) {
      str2 = "AllAccounts";
    }
    String str3 = a(str2, "CashFlowNetIncrease", paramVector, null, false, paramReportCriteria, paramHashMap);
    if (str3 == null) {
      return null;
    }
    if (str3.length() != 0) {
      localStringBuffer.append(str3);
    }
    localStringBuffer.append(" AND ");
    localStringBuffer.append("dcAccount.DCAccountID = dcTransactions.DCAccountID");
    return localStringBuffer.toString();
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria, String paramString)
    throws DCException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i1 = 0;
    if (paramString.equals("CashFlowReport"))
    {
      localStringBuffer.append("dcAcctHistory.DataDate");
      localStringBuffer.append(" ASC");
      i1 = 1;
    }
    if (paramReportSortCriteria != null)
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      ReportSortCriterion localReportSortCriterion = null;
      String str1 = null;
      String str2 = null;
      String str3 = null;
      while (localIterator.hasNext())
      {
        localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        str1 = localReportSortCriterion.getName();
        str2 = null;
        if (paramString.equals("GeneralLedgerReport"))
        {
          if (str1.equals("ProcessingDate")) {
            str2 = "dcTransactions.DataDate";
          } else if (str1.equals("AccountNumber")) {
            str2 = "dcAccount.AccountID";
          }
        }
        else if ((paramString.equals("BalanceSheetReport")) || (paramString.equals("BalanceSheetSummary")) || (paramString.equals("CashFlowForecastReport")))
        {
          if (str1.equals("ProcessingDate")) {
            str2 = "dcAcctHistory.DataDate";
          } else if (str1.equals("AccountNumber")) {
            str2 = "dcAccount.AccountID";
          }
        }
        else if (paramString.equals("CashFlowReport"))
        {
          if (str1.equals("AccountNumber")) {
            str2 = "dcAccount.AccountID";
          }
        }
        else if ((paramString.equals("BalanceSummaryReport")) || (paramString.equals("BalanceDetailOnlyReport"))) {
          if (str1.equals("ProcessingDate")) {
            str2 = "dcAcctHistory.DataDate";
          } else if (str1.equals("AccountNumber")) {
            str2 = "dcAccount.AccountID";
          }
        }
        if (str2 != null)
        {
          if (i1 == 0) {
            i1 = 1;
          } else {
            localStringBuffer.append(", ");
          }
          str3 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
          localStringBuffer.append(str2);
          localStringBuffer.append(" ");
          localStringBuffer.append(str3);
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public static int addAccountBasedCustomSummaries(ReportResult paramReportResult, ArrayList paramArrayList, Accounts paramAccounts, int paramInt1, int paramInt2)
    throws RptException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramArrayList.size() == 0) {
      return paramInt1;
    }
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(paramReportResult.getLocale());
    localReportHeading.setLabel(((com.ffusion.beans.DateTime)((Object[])paramArrayList.get(0))[3]).toString());
    localReportResult.setHeading(localReportHeading);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramReportResult.getLocale());
    localReportDataDimensions.setNumColumns(T.length);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    a.a(localReportResult, paramReportResult.getLocale(), T);
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      Object[] arrayOfObject1 = (Object[])paramArrayList.get(i1);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = arrayOfObject1[4];
      arrayOfObject2[1] = arrayOfObject1[6];
      localArrayList.add(arrayOfObject2);
    }
    paramInt1 = a(localReportResult, localArrayList, paramInt1, paramInt2);
    return paramInt1;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, Accounts paramAccounts)
  {
    Account localAccount = paramAccounts.getByIDAndBankIDAndRoutingNum(paramString1, paramString2, paramString3);
    if (localAccount == null) {
      return "";
    }
    String str1 = null;
    String str2 = null;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString3);
    try
    {
      str2 = AccountUtil.buildAccountDisplayText(paramString1, paramAccounts.getLocale());
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing("Error while constructing account display string.", localUtilException);
      str2 = paramString1;
    }
    localArrayList.add(str2);
    int i1 = (localAccount.getNickName() != null) && (localAccount.getNickName().length() > 0) ? 1 : 0;
    int i2 = localAccount.getCurrencyCode() != null ? 1 : 0;
    if ((i1 != 0) && (i2 != 0)) {
      str1 = "Custom_Summary_Report_Account_With_Nickname_And_Currency_Code_Format";
    } else if (i1 != 0) {
      str1 = "Custom_Summary_Report_Account_With_Nickname_Format";
    } else if (i2 != 0) {
      str1 = "Custom_Summary_Report_Account_With_Currency_Code_Format";
    } else {
      str1 = "Custom_Summary_Report_Account_With_No_Other_Information_Format";
    }
    if (i1 != 0) {
      localArrayList.add(localAccount.getNickName());
    }
    if (i2 != 0) {
      localArrayList.add(localAccount.getCurrencyCode());
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", str1, localArrayList.toArray());
    return (String)localLocalizableString.localize(paramAccounts.getLocale());
  }
  
  public static int addDateBasedCustomSummaries(ReportResult paramReportResult, ArrayList paramArrayList, Accounts paramAccounts, int paramInt1, int paramInt2)
    throws RptException
  {
    Object localObject1 = null;
    ArrayList localArrayList = null;
    Object localObject2;
    Object localObject3;
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      localObject2 = (Object[])paramArrayList.get(i1);
      localObject3 = new AccountKey((String)localObject2[0], (String)localObject2[1], (String)localObject2[2]);
      if (localObject1 == null)
      {
        localObject1 = localObject3;
        localArrayList = new ArrayList();
      }
      else if (!localObject1.equals(localObject3))
      {
        localObject4 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport((ReportResult)localObject4);
        ReportHeading localReportHeading = new ReportHeading(paramReportResult.getLocale());
        localReportHeading.setLabel(a(localObject1.getAccountID(), localObject1.getBankID(), localObject1.getRoutingNumber(), paramAccounts));
        ((ReportResult)localObject4).setHeading(localReportHeading);
        ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramReportResult.getLocale());
        localReportDataDimensions.setNumColumns(T.length);
        localReportDataDimensions.setNumRows(-1);
        ((ReportResult)localObject4).setDataDimensions(localReportDataDimensions);
        a.a((ReportResult)localObject4, paramReportResult.getLocale(), T);
        paramInt1 = a((ReportResult)localObject4, localArrayList, paramInt1, paramInt2);
        localArrayList = new ArrayList();
        localObject1 = localObject3;
      }
      Object localObject4 = new Object[2];
      localObject4[0] = localObject2[4];
      localObject4[1] = localObject2[6];
      localArrayList.add(localObject4);
    }
    if (localArrayList.size() != 0)
    {
      ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
      paramReportResult.addSubReport(localReportResult);
      localObject2 = new ReportHeading(paramReportResult.getLocale());
      ((ReportHeading)localObject2).setLabel(a(localObject1.getAccountID(), localObject1.getBankID(), localObject1.getRoutingNumber(), paramAccounts));
      localReportResult.setHeading((ReportHeading)localObject2);
      localObject3 = new ReportDataDimensions(paramReportResult.getLocale());
      ((ReportDataDimensions)localObject3).setNumColumns(T.length);
      ((ReportDataDimensions)localObject3).setNumRows(-1);
      localReportResult.setDataDimensions((ReportDataDimensions)localObject3);
      a.a(localReportResult, paramReportResult.getLocale(), T);
      paramInt1 = a(localReportResult, localArrayList, paramInt1, paramInt2);
    }
    return paramInt1;
  }
  
  private static Object[] a(ResultSet paramResultSet, Locale paramLocale, String paramString)
    throws Exception
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = paramResultSet.getString(1);
    arrayOfObject[1] = paramResultSet.getString(2);
    arrayOfObject[2] = paramResultSet.getString(3);
    com.ffusion.beans.DateTime localDateTime = DCUtil.getTimestampColumn(paramResultSet.getTimestamp(4), paramLocale);
    localDateTime.setFormat(paramString);
    arrayOfObject[3] = localDateTime;
    arrayOfObject[4] = new Integer(paramResultSet.getInt(5));
    arrayOfObject[5] = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramResultSet.getString(12), paramLocale);
    ExtendedAccountSummary localExtendedAccountSummary = new ExtendedAccountSummary();
    DCUtil.fillExtendABean(localExtendedAccountSummary, paramResultSet, 11);
    HashMap localHashMap = localExtendedAccountSummary.getHash();
    if (arrayOfObject[5] != null)
    {
      String str = ReportConsts.getColumnName(455, paramLocale);
      localHashMap.put(str, ((Currency)arrayOfObject[5]).getCurrencyStringNoSymbol());
    }
    arrayOfObject[6] = localHashMap;
    return arrayOfObject;
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt, Accounts paramAccounts, String paramString)
    throws Exception
  {
    HashMap localHashMap1 = null;
    ArrayList localArrayList = null;
    Object localObject1 = null;
    int i1 = 0;
    Object localObject2;
    Object localObject3;
    while (paramResultSet.next())
    {
      localObject2 = null;
      localObject2 = a(paramResultSet, paramReportResult.getLocale(), paramString);
      localObject3 = (String)localObject2[0];
      String str1 = (String)localObject2[1];
      String str2 = (String)localObject2[2];
      com.ffusion.beans.DateTime localDateTime = (com.ffusion.beans.DateTime)localObject2[3];
      Integer localInteger = (Integer)localObject2[4];
      if ((localHashMap1 == null) || (localObject1 == null))
      {
        localHashMap1 = new HashMap();
        localArrayList = new ArrayList();
        localObject1 = localDateTime;
      }
      else if (!localDateTime.isSameDayInYearAs(localObject1))
      {
        localObject4 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport((ReportResult)localObject4);
        localObject5 = new ReportHeading(paramReportResult.getLocale());
        ((ReportHeading)localObject5).setLabel(localObject1.toString());
        ((ReportResult)localObject4).setHeading((ReportHeading)localObject5);
        i1 = addDateBasedCustomSummaries((ReportResult)localObject4, localArrayList, paramAccounts, i1, paramInt);
        localHashMap1 = new HashMap();
        localArrayList = new ArrayList();
        localObject1 = localDateTime;
        if ((paramInt > 0) && (i1 >= paramInt))
        {
          HashMap localHashMap2 = paramReportResult.getProperties();
          if (localHashMap2 == null)
          {
            localHashMap2 = new HashMap();
            paramReportResult.setProperties(localHashMap2);
          }
          localHashMap2.put("TRUNCATED", new Integer(paramInt));
          break;
        }
      }
      Object localObject4 = new ExtendedSummaryKey((String)localObject3, str1, str2, localInteger);
      Object localObject5 = (Object[])localHashMap1.get(localObject4);
      if (localObject5 == null)
      {
        localObject5 = new Object[7];
        localHashMap1.put(localObject4, localObject5);
        localArrayList.add(localObject5);
      }
      for (int i2 = 0; i2 < localObject2.length; i2++) {
        if (localObject2[i2] != null) {
          if ((localObject2[i2] instanceof HashMap))
          {
            if (localObject5[i2] == null) {
              localObject5[i2] = localObject2[i2];
            } else {
              ((HashMap)localObject5[i2]).putAll((HashMap)localObject2[i2]);
            }
          }
          else {
            localObject5[i2] = localObject2[i2];
          }
        }
      }
    }
    if (localArrayList.size() != 0)
    {
      localObject2 = new ReportResult(paramReportResult.getLocale());
      paramReportResult.addSubReport((ReportResult)localObject2);
      localObject3 = new ReportHeading(paramReportResult.getLocale());
      ((ReportHeading)localObject3).setLabel(localObject1.toString());
      ((ReportResult)localObject2).setHeading((ReportHeading)localObject3);
      i1 = addDateBasedCustomSummaries((ReportResult)localObject2, localArrayList, paramAccounts, i1, paramInt);
    }
    if ((paramInt > 0) && (i1 >= paramInt))
    {
      localObject2 = paramReportResult.getProperties();
      if (localObject2 == null)
      {
        localObject2 = new HashMap();
        paramReportResult.setProperties((HashMap)localObject2);
      }
      ((HashMap)localObject2).put("TRUNCATED", new Integer(paramInt));
    }
  }
  
  private static void jdField_if(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt, Accounts paramAccounts, String paramString)
    throws Exception
  {
    HashMap localHashMap = null;
    ArrayList localArrayList = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    int i1 = 0;
    ReportResult localReportResult = null;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    while (paramResultSet.next())
    {
      localObject4 = null;
      localObject4 = a(paramResultSet, paramReportResult.getLocale(), paramString);
      localObject5 = (String)localObject4[0];
      localObject6 = (String)localObject4[1];
      String str = (String)localObject4[2];
      com.ffusion.beans.DateTime localDateTime = (com.ffusion.beans.DateTime)localObject4[3];
      Integer localInteger = (Integer)localObject4[4];
      AccountKey localAccountKey = new AccountKey((String)localObject5, (String)localObject6, str);
      if ((localHashMap == null) || (localObject3 == null) || (localObject1 == null))
      {
        localHashMap = new HashMap();
        localArrayList = new ArrayList();
        localObject1 = localDateTime;
        localObject3 = localAccountKey;
      }
      else if ((!localDateTime.isSameDayInYearAs(localObject1)) || (!localObject3.equals(localAccountKey)))
      {
        localObject7 = (Object[])localArrayList.get(0);
        localObject8 = new AccountKey((String)localObject7[0], (String)localObject7[1], (String)localObject7[2]);
        Object localObject9;
        if ((localObject2 == null) || (!((AccountKey)localObject8).equals(localObject2)))
        {
          localReportResult = new ReportResult(paramReportResult.getLocale());
          paramReportResult.addSubReport(localReportResult);
          localObject9 = new ReportHeading(paramReportResult.getLocale());
          ((ReportHeading)localObject9).setLabel(a(((AccountKey)localObject8).getAccountID(), ((AccountKey)localObject8).getBankID(), ((AccountKey)localObject8).getRoutingNumber(), paramAccounts));
          localReportResult.setHeading((ReportHeading)localObject9);
          localObject2 = localObject3;
        }
        i1 = addAccountBasedCustomSummaries(localReportResult, localArrayList, paramAccounts, i1, paramInt);
        localHashMap = new HashMap();
        localArrayList = new ArrayList();
        localObject3 = localAccountKey;
        localObject1 = localDateTime;
        if ((paramInt > 0) && (i1 >= paramInt))
        {
          localObject9 = paramReportResult.getProperties();
          if (localObject9 == null)
          {
            localObject9 = new HashMap();
            paramReportResult.setProperties((HashMap)localObject9);
          }
          ((HashMap)localObject9).put("TRUNCATED", new Integer(paramInt));
          break;
        }
      }
      Object localObject7 = new ExtendedSummaryKey((String)localObject5, (String)localObject6, str, localInteger);
      Object localObject8 = (Object[])localHashMap.get(localObject7);
      if (localObject8 == null)
      {
        localObject8 = new Object[7];
        localHashMap.put(localObject7, localObject8);
        localArrayList.add(localObject8);
      }
      for (int i2 = 0; i2 < localObject4.length; i2++) {
        if (localObject4[i2] != null) {
          if ((localObject4[i2] instanceof HashMap))
          {
            if (localObject8[i2] == null) {
              localObject8[i2] = localObject4[i2];
            } else {
              ((HashMap)localObject8[i2]).putAll((HashMap)localObject4[i2]);
            }
          }
          else {
            localObject8[i2] = localObject4[i2];
          }
        }
      }
    }
    if (localArrayList.size() != 0)
    {
      localObject4 = (Object[])localArrayList.get(0);
      localObject5 = new AccountKey((String)localObject4[0], (String)localObject4[1], (String)localObject4[2]);
      if ((localObject2 == null) || (!((AccountKey)localObject5).equals(localObject2)))
      {
        localReportResult = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult);
        localObject6 = new ReportHeading(paramReportResult.getLocale());
        ((ReportHeading)localObject6).setLabel(a(((AccountKey)localObject5).getAccountID(), ((AccountKey)localObject5).getBankID(), ((AccountKey)localObject5).getRoutingNumber(), paramAccounts));
        localReportResult.setHeading((ReportHeading)localObject6);
        localObject2 = localObject5;
      }
      i1 = addAccountBasedCustomSummaries(localReportResult, localArrayList, paramAccounts, i1, paramInt);
    }
    if ((paramInt > 0) && (i1 >= paramInt))
    {
      localObject4 = paramReportResult.getProperties();
      if (localObject4 == null)
      {
        localObject4 = new HashMap();
        paramReportResult.setProperties((HashMap)localObject4);
      }
      ((HashMap)localObject4).put("TRUNCATED", new Integer(paramInt));
    }
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, Accounts paramAccounts, ReportSortCriteria paramReportSortCriteria, String paramString1, Locale paramLocale, String paramString2)
    throws SQLException, DCException, RptException, Exception
  {
    int i1 = -1;
    try
    {
      if (paramString2 != null) {
        i1 = Integer.parseInt(paramString2);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new DCException("The report option: MaxDisplaySize, specified for the custom summary report is not a valid number ");
    }
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(0);
    localReportDataDimensions.setNumRows(0);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    int i2 = 1;
    paramReportSortCriteria.setSortedBy("ORDINAL");
    ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(0);
    if ("ProcessingDate".equals(localReportSortCriterion.getName())) {
      i2 = 0;
    }
    if (i2 != 0) {
      jdField_if(paramReportResult, paramResultSet, i1, paramAccounts, paramString1);
    } else {
      a(paramReportResult, paramResultSet, i1, paramAccounts, paramString1);
    }
  }
  
  private static ReportSortCriteria jdField_if(ReportSortCriteria paramReportSortCriteria)
  {
    ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
    if (paramReportSortCriteria == null)
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "AccountNumber", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "ProcessingDate", true));
    }
    paramReportSortCriteria.setSortedBy("ORDINAL");
    Object localObject = null;
    for (int i1 = 0; i1 < paramReportSortCriteria.size(); i1++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(i1);
      String str = localReportSortCriterion.getName();
      if ((str.equals("AccountNumber")) || (str.equals("ProcessingDate")))
      {
        localObject = localReportSortCriterion;
        break;
      }
    }
    if (localObject == null)
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "AccountNumber", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "ProcessingDate", true));
    }
    else if (localObject.getName().equals("AccountNumber"))
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "AccountNumber", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "ProcessingDate", true));
    }
    else if (localObject.getName().equals("ProcessingDate"))
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "ProcessingDate", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "AccountNumber", true));
    }
    return localReportSortCriteria;
  }
  
  private static IReportResult jdField_do(ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap, Locale paramLocale, String paramString)
    throws DCException
  {
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    String str1 = localProperties2.getProperty("DATEFORMAT");
    localReportSortCriteria = jdField_if(localReportSortCriteria);
    Vector localVector = new Vector();
    Accounts localAccounts = new Accounts(paramLocale);
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      String str2 = a(localProperties1, localVector, localAccounts, paramReportCriteria, paramHashMap);
      if (str2 == null)
      {
        a(paramReportResult, 2456);
        localObject2 = paramReportResult;
        return localObject2;
      }
      Object localObject2 = a(localReportSortCriteria);
      localConnection = DCAdapter.getDBConnection();
      localResultSet = a(arrayOfPreparedStatement, ao, str2, (String)localObject2, localVector, localConnection);
      if (localResultSet.first())
      {
        localResultSet.beforeFirst();
        a(paramReportResult, localResultSet, localAccounts, localReportSortCriteria, str1, paramLocale, paramString);
      }
      else
      {
        a(paramReportResult, 2456);
      }
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
    return paramReportResult;
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria)
    throws DCException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    paramReportSortCriteria.setSortedBy("ORDINAL");
    Iterator localIterator = paramReportSortCriteria.iterator();
    ReportSortCriterion localReportSortCriterion = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    while (localIterator.hasNext())
    {
      localReportSortCriterion = (ReportSortCriterion)localIterator.next();
      str1 = localReportSortCriterion.getName();
      if (str1.equals("AccountNumber"))
      {
        str2 = "dcAccount.AccountID";
      }
      else if (str1.equals("ProcessingDate"))
      {
        str2 = "dcextaccsummary.DataDate";
      }
      else
      {
        if (!str1.equals("RoutingNum")) {
          continue;
        }
        str2 = "dcAccount.RoutingNumber";
      }
      str3 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
      localStringBuffer.append(str2);
      localStringBuffer.append(" ");
      localStringBuffer.append(str3);
      if (localIterator.hasNext()) {
        localStringBuffer.append(", ");
      }
    }
    return localStringBuffer.toString();
  }
  
  private static String a(Properties paramProperties, Vector paramVector, Accounts paramAccounts, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramAccounts == null) {
      paramAccounts = new Accounts();
    }
    String str1;
    if (paramProperties != null)
    {
      str1 = (String)paramProperties.get("Accounts");
      if (str1 == null) {
        str1 = "AllAccounts";
      } else if (accountCriterionHasAllAccounts(str1)) {
        str1 = "AllAccounts";
      }
      String str2 = a(str1, "CustomSummaryReport", paramVector, null, false, paramReportCriteria, paramHashMap);
      if (str2 == null) {
        return null;
      }
      localStringBuffer.append(str2);
      Set localSet = paramProperties.keySet();
      Iterator localIterator = localSet.iterator();
      String str3 = null;
      String str4 = null;
      while (localIterator.hasNext())
      {
        str3 = (String)localIterator.next();
        str4 = paramProperties.getProperty(str3);
        if (str4.trim().length() != 0) {
          if (str3.equals("Accounts"))
          {
            Object localObject;
            if (accountCriterionHasAllAccounts(str4))
            {
              if (paramHashMap == null) {
                throw new DCException("Extra hashmap is null.");
              }
              localObject = (Accounts)paramHashMap.get("Accounts");
              if (localObject != null) {
                paramAccounts.addAll((Collection)localObject);
              }
            }
            else
            {
              localObject = new StringTokenizer(str4, ",");
              int i1 = ((StringTokenizer)localObject).countTokens();
              if (i1 < 1) {
                throw new DCException("Value for SearchCriteria key Accounts has incorrect format.");
              }
              for (int i2 = 0; i2 < i1; i2++)
              {
                String str5 = ((StringTokenizer)localObject).nextToken();
                Account localAccount = getAccountFromAccountCriterion(str5);
                paramAccounts.add(localAccount);
              }
            }
          }
          else if ((!str3.equals("StartDate")) && (!str3.equals("EndDate")))
          {
            if (str3.equals("DataClassification"))
            {
              localStringBuffer.append(" AND ");
              localStringBuffer.append("dcextaccsummary.DataClassification");
              localStringBuffer.append("='");
              if (str4.equals("I")) {
                localStringBuffer.append("I");
              } else {
                localStringBuffer.append("P");
              }
              localStringBuffer.append("' ");
            }
            else if (str3.equals("BAISummaryCode"))
            {
              try
              {
                if (!"AllSummaryCodes".equals(str4.trim())) {
                  localStringBuffer.append(" AND ").append(DBUtil.generateSQLNumericInClause(str4, "dcextaccsummary.SummaryType"));
                }
              }
              catch (NumberFormatException localNumberFormatException)
              {
                throw new DCException(str4 + " does not contain appropriate integer summary code values.", localNumberFormatException);
              }
            }
          }
        }
      }
    }
    else
    {
      str1 = a("AllAccounts", "CustomSummaryReport", paramVector, null, false, paramReportCriteria, paramHashMap);
      if (str1 == null) {
        return null;
      }
      localStringBuffer.append(str1);
      if (paramHashMap == null) {
        throw new DCException("Extra hashmap is null.");
      }
      paramAccounts = (Accounts)paramHashMap.get("Accounts");
    }
    return localStringBuffer.toString();
  }
  
  private static int a(ReportResult paramReportResult, ArrayList paramArrayList, int paramInt1, int paramInt2)
    throws RptException
  {
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      Object[] arrayOfObject1 = (Object[])paramArrayList.get(i1);
      Locale localLocale = paramReportResult.getLocale();
      ReportDataItem localReportDataItem = null;
      ReportRow localReportRow = new ReportRow(localLocale);
      if (i1 % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      for (int i2 = 0; i2 < arrayOfObject1.length; i2++)
      {
        localReportDataItem = new ReportDataItem(localLocale);
        if ((arrayOfObject1[i2] instanceof HashMap))
        {
          HashMap localHashMap = (HashMap)arrayOfObject1[i2];
          ArrayList localArrayList = new ArrayList();
          localArrayList.addAll(localHashMap.keySet());
          Qsort.sortStrings(localArrayList, 1);
          Iterator localIterator = localArrayList.iterator();
          LocalizableList localLocalizableList = new LocalizableList("com.ffusion.beans.reporting.reports", "Custom_Summary_Report_List_Item_Separator");
          while (localIterator.hasNext())
          {
            Object localObject = localIterator.next();
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = localObject;
            arrayOfObject2[1] = localHashMap.get(localObject);
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Custom_Summary_Report_Key_Value_Format", arrayOfObject2);
            localLocalizableList.add(localLocalizableString);
          }
          localReportDataItem.setData(localLocalizableList.localize(localLocale));
        }
        else
        {
          localReportDataItem.setData(arrayOfObject1[i2]);
        }
        localReportDataItems.add(localReportDataItem);
      }
      paramReportResult.addRow(localReportRow);
      paramInt1++;
      if ((paramInt2 > 0) && (paramInt1 >= paramInt2)) {
        break;
      }
    }
    return paramInt1;
  }
  
  public static int addDateBasedBAICustomSummaries(ReportResult paramReportResult, ArrayList paramArrayList, int paramInt)
    throws RptException
  {
    Object localObject1 = null;
    ArrayList localArrayList1 = null;
    Object[] arrayOfObject1;
    Object localObject2;
    Object localObject3;
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      arrayOfObject1 = (Object[])paramArrayList.get(i1);
      AccountKey localAccountKey = new AccountKey((String)arrayOfObject1[0], (String)arrayOfObject1[1], (String)arrayOfObject1[2]);
      if (localObject1 == null)
      {
        localObject1 = localAccountKey;
        localArrayList1 = new ArrayList();
      }
      else if (!localObject1.equals(localAccountKey))
      {
        localObject2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport((ReportResult)localObject2);
        Object[] arrayOfObject2 = (Object[])localArrayList1.get(0);
        int i4 = arrayOfObject2.length - 12;
        localObject3 = new ReportDataDimensions(paramReportResult.getLocale());
        ((ReportDataDimensions)localObject3).setNumColumns(W.length + i4);
        ((ReportDataDimensions)localObject3).setNumRows(0);
        ((ReportResult)localObject2).setDataDimensions((ReportDataDimensions)localObject3);
        a.a((ReportResult)localObject2, paramReportResult.getLocale(), W);
        for (int i5 = 0; i5 < i4; i5++)
        {
          ReportColumn localReportColumn2 = new ReportColumn(paramReportResult.getLocale());
          ArrayList localArrayList2 = new ArrayList();
          localArrayList2.add("ExtendABean" + (i5 + 1));
          localReportColumn2.setLabels(localArrayList2);
          localReportColumn2.setDataType("java.lang.String");
          localReportColumn2.setWidthAsPercent(1);
          ((ReportResult)localObject2).addColumn(localReportColumn2);
        }
        paramInt = a((ReportResult)localObject2, localArrayList1, paramInt, -1);
        localArrayList1 = new ArrayList();
        localObject1 = localAccountKey;
      }
      localArrayList1.add(arrayOfObject1);
    }
    if (localArrayList1.size() != 0)
    {
      ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
      paramReportResult.addSubReport(localReportResult);
      arrayOfObject1 = (Object[])localArrayList1.get(0);
      int i2 = arrayOfObject1.length - 12;
      localObject2 = new ReportDataDimensions(paramReportResult.getLocale());
      ((ReportDataDimensions)localObject2).setNumColumns(W.length + i2);
      ((ReportDataDimensions)localObject2).setNumRows(0);
      localReportResult.setDataDimensions((ReportDataDimensions)localObject2);
      a.a(localReportResult, paramReportResult.getLocale(), W);
      for (int i3 = 0; i3 < i2; i3++)
      {
        ReportColumn localReportColumn1 = new ReportColumn(paramReportResult.getLocale());
        localObject3 = new ArrayList();
        ((ArrayList)localObject3).add("ExtendABean" + (i3 + 1));
        localReportColumn1.setLabels((ArrayList)localObject3);
        localReportColumn1.setDataType("java.lang.String");
        localReportColumn1.setWidthAsPercent(1);
        localReportResult.addColumn(localReportColumn1);
      }
      paramInt = a(localReportResult, localArrayList1, paramInt, -1);
    }
    return paramInt;
  }
  
  private static Object[] a(ResultSet paramResultSet, String paramString, Locale paramLocale)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    String str = null;
    try
    {
      str = AccountUtil.buildAccountDisplayTextForExport("BAI2", paramResultSet.getString(1), paramLocale);
    }
    catch (UtilException localUtilException)
    {
      str = paramResultSet.getString(1);
    }
    localArrayList.add(str);
    localArrayList.add(paramResultSet.getString(2));
    localArrayList.add(paramResultSet.getString(3));
    localArrayList.add(paramResultSet.getString(12));
    localArrayList.add(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(4), paramLocale));
    localArrayList.add(new Integer(paramResultSet.getInt(5)));
    localArrayList.add(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramLocale));
    localArrayList.add(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramLocale));
    localArrayList.add(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramLocale));
    localArrayList.add(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), paramLocale));
    localArrayList.add(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(10), paramLocale));
    localArrayList.add(paramString);
    ExtendedAccountSummary localExtendedAccountSummary = new ExtendedAccountSummary();
    DCUtil.fillExtendABean(localExtendedAccountSummary, paramResultSet, 11);
    HashMap localHashMap = localExtendedAccountSummary.getHash();
    Collection localCollection = localHashMap.values();
    Iterator localIterator = localCollection.iterator();
    int i1 = 12;
    while (localIterator.hasNext()) {
      localArrayList.add(localIterator.next());
    }
    return localArrayList.toArray();
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, String paramString)
    throws Exception
  {
    HashMap localHashMap = null;
    ArrayList localArrayList = null;
    Object localObject = null;
    int i1 = 0;
    while (paramResultSet.next())
    {
      Object[] arrayOfObject1 = null;
      arrayOfObject1 = a(paramResultSet, paramString, paramReportResult.getLocale());
      String str1 = (String)arrayOfObject1[0];
      String str2 = (String)arrayOfObject1[1];
      String str3 = (String)arrayOfObject1[2];
      com.ffusion.beans.DateTime localDateTime = (com.ffusion.beans.DateTime)arrayOfObject1[4];
      Integer localInteger = (Integer)arrayOfObject1[5];
      if ((localHashMap == null) || (localObject == null))
      {
        localHashMap = new HashMap();
        localArrayList = new ArrayList();
        localObject = localDateTime;
      }
      else if (!localDateTime.isSameDayInYearAs(localObject))
      {
        i1 = addDateBasedBAICustomSummaries(paramReportResult, localArrayList, i1);
        localHashMap = new HashMap();
        localArrayList = new ArrayList();
        localObject = localDateTime;
      }
      ExtendedSummaryKey localExtendedSummaryKey = new ExtendedSummaryKey(str1, str2, str3, localInteger);
      Object[] arrayOfObject2 = (Object[])localHashMap.get(localExtendedSummaryKey);
      if (arrayOfObject2 == null)
      {
        arrayOfObject2 = new Object[arrayOfObject1.length];
        localHashMap.put(localExtendedSummaryKey, arrayOfObject2);
        localArrayList.add(arrayOfObject2);
      }
      for (int i2 = 0; i2 < arrayOfObject1.length; i2++) {
        if (arrayOfObject1[i2] != null) {
          arrayOfObject2[i2] = arrayOfObject1[i2];
        }
      }
    }
    i1 = addDateBasedBAICustomSummaries(paramReportResult, localArrayList, i1);
  }
  
  private static IReportResult jdField_if(ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap, Locale paramLocale, String paramString)
    throws DCException
  {
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
    int i1 = 1;
    String str1 = (String)paramHashMap.get("ORIGINATOR_ID_TYPE");
    if (str1 == null) {
      str1 = "1";
    }
    if (str1.equals("1")) {
      localReportSortCriteria.create(i1++, "RoutingNum", true);
    }
    localReportSortCriteria.create(i1++, "ProcessingDate", true);
    localReportSortCriteria.create(i1++, "AccountNumber", true);
    int i2 = 1;
    String str2 = (String)paramHashMap.get("CUSTOMER_ACCOUNT_OPTION");
    if (str2 == null) {
      str2 = "1";
    }
    if (str2.equals("1")) {
      i2 = 1;
    } else if (str2.equals("2")) {
      i2 = 0;
    }
    if (i2 == 0) {
      localReportSortCriteria.create(i1++, "RoutingNum", true);
    }
    localProperties2 = (Properties)localProperties2.clone();
    localProperties2.setProperty("SHOWHEADER", "TRUE");
    localProperties2.setProperty("SHOWFOOTER", "TRUE");
    Vector localVector = new Vector();
    Accounts localAccounts = new Accounts(paramLocale);
    String str3 = a(localProperties1, localVector, localAccounts, paramReportCriteria, paramHashMap);
    if (str3 == null) {
      return paramReportResult;
    }
    String str4 = a(localReportSortCriteria);
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      localResultSet = a(arrayOfPreparedStatement, ao, str3, str4, localVector, localConnection);
      String str5 = (String)localProperties1.get("DataClassification");
      if (str5 == null) {
        str5 = "P";
      }
      a(paramReportResult, localResultSet, str5);
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
    return paramReportResult;
  }
  
  private static IReportResult a(ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap, Locale paramLocale, String paramString)
    throws DCException, RptException
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "BAI2".equals(localProperties.getProperty("FORMAT"));
    IReportResult localIReportResult = null;
    if (bool) {
      localIReportResult = jdField_if(paramReportResult, paramReportCriteria, paramHashMap, paramLocale, paramString);
    } else {
      localIReportResult = jdField_do(paramReportResult, paramReportCriteria, paramHashMap, paramLocale, paramString);
    }
    return localIReportResult;
  }
  
  private static boolean a(BAITypeCodeInfo paramBAITypeCodeInfo, Currency paramCurrency)
  {
    int i1;
    if (paramBAITypeCodeInfo != null) {
      i1 = paramBAITypeCodeInfo.getTransaction();
    } else {
      i1 = 0;
    }
    if (2 == i1) {
      return true;
    }
    if (1 == i1) {
      return false;
    }
    return (paramCurrency != null) && (paramCurrency.isNegative());
  }
  
  private static String a(Account paramAccount)
  {
    String str1 = null;
    String str2 = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      str2 = AccountUtil.buildAccountDisplayText(paramAccount.getID(), paramAccount.getLocale());
    }
    catch (UtilException localUtilException)
    {
      DebugLog.throwing("Error while constructing account display string.", localUtilException);
      str2 = paramAccount.getID();
    }
    int i1 = (paramAccount.getNickName() != null) && (paramAccount.getNickName().length() > 0) ? 1 : 0;
    int i2 = paramAccount.getCurrencyCode() != null ? 1 : 0;
    int i3 = (paramAccount.getRoutingNum() != null) || (paramAccount.getRoutingNum().length() >= 0) ? 1 : 0;
    if (i3 != 0)
    {
      localArrayList.add(paramAccount.getRoutingNum());
      localArrayList.add(str2);
      if ((i1 != 0) && (i2 != 0)) {
        str1 = "Generic_Detail_Report_Account_With_Routing_Number_Nickname_And_Currency_Code_Format";
      } else if (i1 != 0) {
        str1 = "Generic_Detail_Report_Account_With_Routing_Number_Nickname_Format";
      } else if (i2 != 0) {
        str1 = "Generic_Detail_Report_Account_With_Routing_Number_Currency_Code_Format";
      } else {
        str1 = "Generic_Detail_Report_Account_With_Routing_Number_No_Other_Information_Format";
      }
    }
    else
    {
      localArrayList.add(str2);
      if ((i1 != 0) && (i2 != 0)) {
        str1 = "Generic_Detail_Report_Account_With_Nickname_And_Currency_Code_Format";
      } else if (i1 != 0) {
        str1 = "Generic_Detail_Report_Account_With_Nickname_Format";
      } else if (i2 != 0) {
        str1 = "Generic_Detail_Report_Account_With_Currency_Code_Format";
      } else {
        str1 = "Generic_Detail_Report_Account_With_No_Other_Information_Format";
      }
    }
    if (i1 != 0) {
      localArrayList.add(paramAccount.getNickName());
    }
    if (i2 != 0) {
      localArrayList.add(paramAccount.getCurrencyCode());
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", str1, localArrayList.toArray());
    return (String)localLocalizableString.localize(paramAccount.getLocale());
  }
  
  private static void a(ReportResult paramReportResult, int paramInt)
    throws RptException
  {
    a(paramReportResult, 1, 1);
    ReportColumn localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(100);
    paramReportResult.addColumn(localReportColumn);
    ReportDataItems localReportDataItems = new ReportDataItems(paramReportResult.getLocale());
    localReportDataItems.add().setData(ReportConsts.getText(paramInt, paramReportResult.getLocale()));
    a(paramReportResult, localReportDataItems, -1);
  }
  
  private static int jdField_if(ResultSet paramResultSet, ReportResult paramReportResult, Accounts paramAccounts, Connection paramConnection, ReportCriteria paramReportCriteria)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str1 = localProperties.getProperty("DATEFORMAT");
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(aL.length);
    localReportDataDimensions.setNumRows(0);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    a.a(paramReportResult, localLocale, aL);
    int i1 = 0;
    String str2 = new String();
    String str3 = new String();
    String str4 = new String();
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    while (paramResultSet.next())
    {
      String str5 = paramResultSet.getString(1);
      String str6 = paramResultSet.getString(2);
      String str7 = paramResultSet.getString(3);
      String str8 = paramResultSet.getString(4);
      Account localAccount = paramAccounts.getByIDAndBankIDAndRoutingNum(str5, str6, str7);
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      jdField_if(localReportDataItems, str7);
      jdField_if(localReportDataItems, localAccount.getDisplayText("CSV"));
      com.ffusion.beans.DateTime localDateTime = a(paramResultSet, 5, localLocale);
      if ((str1 != null) && (localDateTime != null)) {
        localDateTime.setFormat(str1);
      }
      jdField_if(localReportDataItems, localDateTime.toString());
      int i2 = paramResultSet.getInt(12);
      jdField_if(localReportDataItems, Integer.toString(i2));
      localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i2);
      }
      catch (CSILException localCSILException) {}
      if (localBAITypeCodeInfo != null) {
        jdField_if(localReportDataItems, localBAITypeCodeInfo.getDescription(paramReportCriteria.getLocale()));
      } else {
        jdField_if(localReportDataItems, null);
      }
      Currency localCurrency = a(paramResultSet, 9, localLocale, str8);
      jdField_if(localReportDataItems, localCurrency == null ? null : localCurrency.getCurrencyStringNoSymbol());
      jdField_if(localReportDataItems, DCAdapter.unPadRefNum(paramResultSet.getString(13), DCAdapter.getBankReferenceNumberType()));
      jdField_if(localReportDataItems, DCAdapter.unPadRefNum(paramResultSet.getString(14), DCAdapter.getCustomerReferenceNumberType()));
      String str9 = paramResultSet.getString(7);
      if ((str9 != null) && (str9.trim().length() == 0)) {
        str9 = null;
      }
      jdField_if(localReportDataItems, str9);
      jdField_if(localReportDataItems, localAccount.getCurrencyCode());
      paramReportResult.addRow(localReportRow);
      i1++;
    }
    return i1;
  }
  
  private static int a(ResultSet paramResultSet, ReportResult paramReportResult, Accounts paramAccounts, Connection paramConnection, ReportCriteria paramReportCriteria)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    if (bool) {
      return jdField_if(paramResultSet, paramReportResult, paramAccounts, paramConnection, paramReportCriteria);
    }
    ReportDataDimensions localReportDataDimensions1 = new ReportDataDimensions(localLocale);
    localReportDataDimensions1.setNumColumns(0);
    localReportDataDimensions1.setNumRows(0);
    paramReportResult.setDataDimensions(localReportDataDimensions1);
    ReportResult localReportResult = null;
    int i1 = 0;
    int i2 = 0;
    Object localObject1 = new String();
    Object localObject2 = new String();
    Object localObject3 = new String();
    while (paramResultSet.next())
    {
      String str1 = paramResultSet.getString(1);
      String str2 = paramResultSet.getString(2);
      String str3 = paramResultSet.getString(3);
      String str4 = paramResultSet.getString(4);
      Account localAccount = paramAccounts.getByIDAndBankIDAndRoutingNum(str1, str2, str3);
      TransactionTypeInfo localTransactionTypeInfo = Banking.getTransactionTypeInfo(paramResultSet.getInt(6), new HashMap());
      String str5 = localTransactionTypeInfo.getDescription(localLocale);
      if ((!((String)localObject1).equals(str1)) || (!((String)localObject2).equals(str2)) || (!((String)localObject3).equals(str3)))
      {
        localObject1 = str1;
        localObject2 = str2;
        localObject3 = str3;
        localObject4 = U;
        int i3 = Account.getAccountSystemTypeFromGroup(localAccount.getAccountGroup());
        switch (i3)
        {
        case 2: 
          localObject4 = ar;
          break;
        case 4: 
          localObject4 = bs;
          break;
        case 3: 
          localObject4 = an;
          break;
        case 1: 
          localObject4 = U;
          break;
        }
        localReportResult = new ReportResult(localLocale);
        paramReportResult.addSubReport(localReportResult);
        ReportDataDimensions localReportDataDimensions2 = new ReportDataDimensions(localLocale);
        localReportDataDimensions2.setNumColumns(localObject4.length);
        localReportDataDimensions2.setNumRows(-1);
        localReportResult.setDataDimensions(localReportDataDimensions2);
        a.a(localReportResult, localLocale, (a[])localObject4);
        i2 = 0;
      }
      Object localObject4 = new ReportRow(localLocale);
      if (i2 % 2 == 1) {
        ((ReportRow)localObject4).set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      ((ReportRow)localObject4).setDataItems(localReportDataItems);
      int i4 = Account.getAccountSystemTypeFromGroup(localAccount.getAccountGroup());
      String str6;
      Currency localCurrency;
      if (i4 == 1)
      {
        jdField_if(localReportDataItems, a(paramResultSet, 5, localLocale));
        jdField_if(localReportDataItems, str5);
        str6 = paramResultSet.getString(7);
        if ((str6 != null) && (str6.trim().length() == 0)) {
          str6 = null;
        }
        jdField_if(localReportDataItems, str6);
        jdField_if(localReportDataItems, paramResultSet.getString(8));
        localCurrency = a(paramResultSet, 9, localLocale, str4);
        if (localCurrency != null)
        {
          if (localCurrency.doubleValue() < 0.0D) {
            localReportDataItems.add().setData(new Currency(localCurrency.getAmountValue().abs(), str4, localLocale));
          } else {
            localReportDataItems.add().setData(null);
          }
          if (localCurrency.doubleValue() >= 0.0D) {
            localReportDataItems.add().setData(localCurrency);
          } else {
            localReportDataItems.add().setData(null);
          }
        }
        else
        {
          localReportDataItems.add().setData(null);
          localReportDataItems.add().setData(null);
        }
        jdField_if(localReportDataItems, a(paramResultSet, 10, localLocale, str4));
      }
      else if (i4 == 3)
      {
        jdField_if(localReportDataItems, a(paramResultSet, 5, localLocale));
        str6 = paramResultSet.getString(7);
        if ((str6 != null) && (str6.trim().length() == 0)) {
          str6 = null;
        }
        jdField_if(localReportDataItems, str6);
        jdField_if(localReportDataItems, a(paramResultSet, 9, localLocale, str4));
        jdField_if(localReportDataItems, a(paramResultSet, 11, localLocale));
        jdField_if(localReportDataItems, a(paramResultSet, 10, localLocale, str4));
      }
      else if (i4 == 2)
      {
        jdField_if(localReportDataItems, a(paramResultSet, 5, localLocale));
        jdField_if(localReportDataItems, str5);
        str6 = paramResultSet.getString(7);
        if ((str6 != null) && (str6.trim().length() == 0)) {
          str6 = null;
        }
        jdField_if(localReportDataItems, str6);
        localCurrency = a(paramResultSet, 9, localLocale, str4);
        if (localCurrency != null)
        {
          if (localCurrency.doubleValue() >= 0.0D) {
            localReportDataItems.add().setData(localCurrency);
          } else {
            localReportDataItems.add().setData(null);
          }
          if (localCurrency.doubleValue() < 0.0D) {
            localReportDataItems.add().setData(new Currency(localCurrency.getAmountValue().abs(), str4, localLocale));
          } else {
            localReportDataItems.add().setData(null);
          }
        }
        else
        {
          localReportDataItems.add().setData(null);
          localReportDataItems.add().setData(null);
        }
        jdField_if(localReportDataItems, a(paramResultSet, 10, localLocale, str4));
      }
      else if (i4 == 4)
      {
        jdField_if(localReportDataItems, a(paramResultSet, 5, localLocale));
        str6 = paramResultSet.getString(7);
        if ((str6 != null) && (str6.trim().length() == 0)) {
          str6 = null;
        }
        jdField_if(localReportDataItems, str6);
        localCurrency = a(paramResultSet, 9, localLocale, str4);
        if (localCurrency != null)
        {
          if (localCurrency.doubleValue() < 0.0D) {
            localReportDataItems.add().setData(new Currency(localCurrency.getAmountValue().abs(), str4, localLocale));
          } else {
            localReportDataItems.add().setData(null);
          }
          if (localCurrency.doubleValue() >= 0.0D) {
            localReportDataItems.add().setData(localCurrency);
          } else {
            localReportDataItems.add().setData(null);
          }
        }
        else
        {
          localReportDataItems.add().setData(null);
          localReportDataItems.add().setData(null);
        }
        jdField_if(localReportDataItems, a(paramResultSet, 10, localLocale, str4));
      }
      localReportResult.addRow((ReportRow)localObject4);
      i1++;
      i2++;
    }
    return i1;
  }
  
  private static int a(ResultSet paramResultSet, ReportResult paramReportResult, Accounts paramAccounts, Connection paramConnection, boolean paramBoolean1, a[] paramArrayOfa, boolean paramBoolean2, ReportCriteria paramReportCriteria)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    if (bool) {
      return a(paramResultSet, paramReportResult, paramAccounts, paramConnection, paramBoolean1, paramReportCriteria);
    }
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    int i1 = 0;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    Transactions localTransactions = new Transactions(localLocale);
    int i2 = paramArrayOfa.length;
    localReportDataDimensions.setNumColumns(0);
    localReportDataDimensions.setNumRows(0);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportResult localReportResult1 = paramReportResult;
    int i3 = 0;
    int i4 = 0;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    Object localObject10;
    while (paramResultSet.next())
    {
      localObject5 = paramResultSet.getString(1);
      localObject6 = paramResultSet.getString(2);
      localObject7 = paramResultSet.getString(3);
      localObject8 = paramAccounts.getByIDAndBankIDAndRoutingNum((String)localObject5, (String)localObject6, (String)localObject7);
      Object localObject12;
      if ((paramBoolean1) && ((!((String)localObject5).equals(localObject1)) || (!((String)localObject6).equals(localObject2)) || (!((String)localObject7).equals(localObject3))))
      {
        i3 = 0;
        if (((localObject1 != null) || (localObject2 != null) || (localObject3 != null)) && (localReportResult1 != null))
        {
          localObject9 = null;
          localObject10 = null;
          ReportRow localReportRow = null;
          localObject11 = null;
          localObject9 = new ReportResult(localLocale);
          ((ReportResult)localObject9).setHeading(null);
          localReportResult1.addSubReport((ReportResult)localObject9);
          localObject10 = new ReportDataDimensions(localLocale);
          ((ReportDataDimensions)localObject10).setNumColumns(paramArrayOfa.length - 1);
          ((ReportDataDimensions)localObject10).setNumRows(1);
          ((ReportResult)localObject9).setDataDimensions((ReportDataDimensions)localObject10);
          for (int i7 = 0; i7 < paramArrayOfa.length; i7++)
          {
            localObject12 = new ReportColumn(localLocale);
            ((ReportColumn)localObject12).setLabels(null);
            if (i7 == 0)
            {
              ((ReportColumn)localObject12).setDataType("java.lang.String");
              ((ReportColumn)localObject12).setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
              ((ReportColumn)localObject12).setWidthAsPercent(paramArrayOfa[i7].jdField_do() + paramArrayOfa[(i7 + 1)].jdField_do());
              i7++;
            }
            else if (i7 == 3)
            {
              ((ReportColumn)localObject12).setDataType(paramArrayOfa[i7].a());
              ((ReportColumn)localObject12).setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
              ((ReportColumn)localObject12).setWidthAsPercent(paramArrayOfa[i7].jdField_do());
            }
            else
            {
              ((ReportColumn)localObject12).setDataType(paramArrayOfa[i7].a());
              ((ReportColumn)localObject12).setWidthAsPercent(paramArrayOfa[i7].jdField_do());
            }
            ((ReportResult)localObject9).addColumn((ReportColumn)localObject12);
          }
          localReportRow = new ReportRow(localLocale);
          localObject11 = new ReportDataItems(localLocale);
          localReportRow.setDataItems((ReportDataItems)localObject11);
          for (i7 = 0; i7 < paramArrayOfa.length; i7++) {
            if (i7 == 0)
            {
              localObject12 = new Object[1];
              localObject12[0] = ((Currency)localObject4).getCurrencyCode();
              LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Transaction_Detail_Report_Account_SubTotal_Text", (Object[])localObject12);
              jdField_if((ReportDataItems)localObject11, localLocalizableString2.localize(paramReportResult.getLocale()));
              i7++;
            }
            else if (i7 == 3)
            {
              jdField_if((ReportDataItems)localObject11, localObject4);
            }
            else
            {
              jdField_if((ReportDataItems)localObject11, null);
            }
          }
          ((ReportResult)localObject9).addRow(localReportRow);
        }
        localObject1 = localObject5;
        localObject2 = localObject6;
        localObject3 = localObject7;
        localObject4 = new Currency("0", ((Account)localObject8).getCurrencyCode(), localLocale);
        localReportResult1 = new ReportResult(localLocale);
        paramReportResult.addSubReport(localReportResult1);
        localObject9 = new ReportHeading(localLocale);
        ((ReportHeading)localObject9).setLabel(a((Account)localObject8));
        localReportResult1.setHeading((ReportHeading)localObject9);
        localReportDataDimensions = new ReportDataDimensions(localLocale);
        localReportDataDimensions.setNumColumns(0);
        localReportDataDimensions.setNumRows(-1);
        localReportResult1.setDataDimensions(localReportDataDimensions);
        i4 = 0;
      }
      ReportResult localReportResult2 = new ReportResult(localLocale);
      localReportResult1.addSubReport(localReportResult2);
      Object localObject9 = new ReportHeading(localLocale);
      localReportResult2.setHeading(null);
      localReportDataDimensions = new ReportDataDimensions(localLocale);
      localReportDataDimensions.setNumColumns(paramArrayOfa.length);
      localReportDataDimensions.setNumRows(-1);
      localReportResult2.setDataDimensions(localReportDataDimensions);
      localObject9 = new ReportRow(localLocale);
      if (i4 % 2 == 1) {
        ((ReportRow)localObject9).set("CELLBACKGROUND", "reportDataShaded");
      }
      localObject10 = new ReportDataItems(localLocale);
      ((ReportRow)localObject9).setDataItems((ReportDataItems)localObject10);
      jdField_if((ReportDataItems)localObject10, a(paramResultSet, 5, localLocale));
      if (!paramBoolean1) {
        ((ReportDataItems)localObject10).add().setData(a((Account)localObject8));
      }
      int i6 = paramResultSet.getInt(7);
      Object localObject11 = paramResultSet.getString(19);
      localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i6);
      }
      catch (CSILException localCSILException) {}
      jdField_if((ReportDataItems)localObject10, localObject11);
      Currency localCurrency = a(paramResultSet, 13, localLocale, ((Account)localObject8).getCurrencyCode());
      if (localCurrency != null) {
        ((ReportDataItems)localObject10).add().setData(localCurrency);
      } else {
        ((ReportDataItems)localObject10).add(null);
      }
      if ((paramBoolean1) && (localCurrency != null)) {
        ((Currency)localObject4).addAmount(localCurrency);
      }
      int i8 = (paramBoolean2) || (i3 == 0) ? 1 : 0;
      if (a(localBAITypeCodeInfo, localCurrency))
      {
        ArrayList localArrayList = new ArrayList();
        for (int i10 = 0; i10 < paramArrayOfa.length; i10++) {
          if (paramArrayOfa[i10].jdField_for() != 261)
          {
            if (i8 != 0) {
              localArrayList.add(paramArrayOfa[i10]);
            } else {
              localArrayList.add(new a(-1, paramArrayOfa[i10].a(), paramArrayOfa[i10].jdField_do(), paramArrayOfa[i10].jdField_if()));
            }
          }
          else if (i8 != 0) {
            localArrayList.add(new a(0, "java.lang.String", 10));
          } else {
            localArrayList.add(new a(-1, "java.lang.String", 10));
          }
        }
        localObject12 = (a[])localArrayList.toArray(new a[localArrayList.size()]);
        ((ReportDataItems)localObject10).add(null);
      }
      else
      {
        if (i8 == 0)
        {
          localObject12 = new a[paramArrayOfa.length];
          for (int i9 = 0; i9 < localObject12.length; i9++) {
            localObject12[i9] = new a(-1, paramArrayOfa[i9].a(), paramArrayOfa[i9].jdField_do(), paramArrayOfa[i9].jdField_if());
          }
        }
        else
        {
          localObject12 = paramArrayOfa;
        }
        localObject13 = a(paramResultSet, 16, localLocale, ((Account)localObject8).getCurrencyCode());
        if (localObject13 != null) {
          ((ReportDataItems)localObject10).add().setData(localObject13);
        } else {
          ((ReportDataItems)localObject10).add(null);
        }
      }
      a.a(localReportResult2, localLocale, (a[])localObject12);
      i3 = 1;
      jdField_if((ReportDataItems)localObject10, DCAdapter.unPadRefNum(paramResultSet.getString(9), DCAdapter.getBankReferenceNumberType()));
      jdField_if((ReportDataItems)localObject10, DCAdapter.unPadRefNum(paramResultSet.getString(10), DCAdapter.getCustomerReferenceNumberType()));
      localReportResult2.addRow((ReportRow)localObject9);
      localObject9 = null;
      Object localObject13 = localTransactions.create();
      DCUtil.fillExtendABean((ExtendABean)localObject13, paramResultSet, 15);
      Object localObject14 = ((Transaction)localObject13).getHash().entrySet().iterator();
      Object localObject15;
      Object localObject16;
      while (((Iterator)localObject14).hasNext())
      {
        localObject15 = (Map.Entry)((Iterator)localObject14).next();
        if (localObject9 == null)
        {
          localObject9 = new ReportRow(localLocale);
          if (i4 % 2 == 1) {
            ((ReportRow)localObject9).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject10 = new ReportDataItems(localLocale);
          ((ReportRow)localObject9).setDataItems((ReportDataItems)localObject10);
          for (int i11 = i2; i11 > 1; i11--) {
            ((ReportDataItems)localObject10).add(null);
          }
        }
        localObject16 = new Object[2];
        localObject16[0] = ((Map.Entry)localObject15).getKey();
        localObject16[1] = ((Map.Entry)localObject15).getValue();
        LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.beans.reporting.reports", "Generic_Detail_Report_ExtendABean_Field_Display_Format", (Object[])localObject16);
        ((ReportDataItems)localObject10).add().setData(localLocalizableString3.localize(localLocale));
        localReportResult2.addRow((ReportRow)localObject9);
        localObject9 = null;
      }
      if (localObject9 != null)
      {
        ((ReportDataItems)localObject10).add(null);
        localReportResult2.addRow((ReportRow)localObject9);
      }
      localObject14 = paramResultSet.getString(12);
      if ((localObject14 != null) && (((String)localObject14).trim().length() != 0))
      {
        localReportResult2 = new ReportResult(localLocale);
        localReportResult1.addSubReport(localReportResult2);
        localObject15 = new ReportHeading(localLocale);
        localReportResult2.setHeading(null);
        localReportDataDimensions = new ReportDataDimensions(localLocale);
        localReportDataDimensions.setNumColumns(1);
        localReportDataDimensions.setNumRows(-1);
        localReportResult2.setDataDimensions(localReportDataDimensions);
        localObject16 = new ReportColumn(localLocale);
        ((ReportColumn)localObject16).setLabels(null);
        ((ReportColumn)localObject16).setDataType("java.lang.String");
        ((ReportColumn)localObject16).setWidthAsPercent(100);
        localReportResult2.addColumn((ReportColumn)localObject16);
        localObject9 = new ReportRow(localLocale);
        if (i4 % 2 == 1) {
          ((ReportRow)localObject9).set("CELLBACKGROUND", "reportDataShaded");
        }
        localObject10 = new ReportDataItems(localLocale);
        ((ReportRow)localObject9).setDataItems((ReportDataItems)localObject10);
        jdField_if((ReportDataItems)localObject10, localObject14);
        localReportResult2.addRow((ReportRow)localObject9);
      }
      i4++;
      i1++;
    }
    if ((paramBoolean1) && ((localObject1 != null) || (localObject2 != null) || (localObject3 != null)) && (localReportResult1 != null))
    {
      localObject5 = null;
      localObject6 = null;
      localObject7 = null;
      localObject8 = null;
      localObject5 = new ReportResult(localLocale);
      ((ReportResult)localObject5).setHeading(null);
      localReportResult1.addSubReport((ReportResult)localObject5);
      localObject6 = new ReportDataDimensions(localLocale);
      ((ReportDataDimensions)localObject6).setNumColumns(paramArrayOfa.length - 1);
      ((ReportDataDimensions)localObject6).setNumRows(1);
      ((ReportResult)localObject5).setDataDimensions((ReportDataDimensions)localObject6);
      for (int i5 = 0; i5 < paramArrayOfa.length; i5++)
      {
        localObject10 = new ReportColumn(localLocale);
        ((ReportColumn)localObject10).setLabels(null);
        if (i5 == 0)
        {
          ((ReportColumn)localObject10).setDataType("java.lang.String");
          ((ReportColumn)localObject10).setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
          ((ReportColumn)localObject10).setWidthAsPercent(paramArrayOfa[i5].jdField_do() + paramArrayOfa[(i5 + 1)].jdField_do());
          i5++;
        }
        else if (i5 == 3)
        {
          ((ReportColumn)localObject10).setDataType(paramArrayOfa[i5].a());
          ((ReportColumn)localObject10).setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
          ((ReportColumn)localObject10).setWidthAsPercent(paramArrayOfa[i5].jdField_do());
        }
        else
        {
          ((ReportColumn)localObject10).setDataType(paramArrayOfa[i5].a());
          ((ReportColumn)localObject10).setWidthAsPercent(paramArrayOfa[i5].jdField_do());
        }
        ((ReportResult)localObject5).addColumn((ReportColumn)localObject10);
      }
      localObject7 = new ReportRow(localLocale);
      localObject8 = new ReportDataItems(localLocale);
      ((ReportRow)localObject7).setDataItems((ReportDataItems)localObject8);
      for (i5 = 0; i5 < paramArrayOfa.length; i5++) {
        if (i5 == 0)
        {
          localObject10 = new Object[1];
          localObject10[0] = ((Currency)localObject4).getCurrencyCode();
          LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.beans.reporting.reports", "Transaction_Detail_Report_Account_SubTotal_Text", (Object[])localObject10);
          jdField_if((ReportDataItems)localObject8, localLocalizableString1.localize(paramReportResult.getLocale()));
          i5++;
        }
        else if (i5 == 3)
        {
          jdField_if((ReportDataItems)localObject8, localObject4);
        }
        else
        {
          jdField_if((ReportDataItems)localObject8, null);
        }
      }
      ((ReportResult)localObject5).addRow((ReportRow)localObject7);
    }
    return i1;
  }
  
  private static int a(ResultSet paramResultSet, ReportResult paramReportResult, Accounts paramAccounts, Connection paramConnection, boolean paramBoolean, ReportCriteria paramReportCriteria)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    String str1 = ReportConsts.getColumnName(735, localLocale);
    String str2 = ReportConsts.getColumnName(738, localLocale);
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str3 = localProperties.getProperty("DATEFORMAT");
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    int i1 = 0;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Currency localCurrency1 = null;
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    Transactions localTransactions = new Transactions(localLocale);
    localReportDataDimensions.setNumColumns(af.length);
    localReportDataDimensions.setNumRows(0);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    a.a(paramReportResult, localLocale, af);
    Account localAccount = null;
    Object localObject5;
    Object localObject6;
    String str4;
    Object localObject7;
    while (paramResultSet.next())
    {
      localObject5 = paramResultSet.getString(1);
      localObject6 = paramResultSet.getString(2);
      str4 = paramResultSet.getString(3);
      localAccount = paramAccounts.getByIDAndBankIDAndRoutingNum((String)localObject5, (String)localObject6, str4);
      if ((paramBoolean) && ((!((String)localObject5).equals(localObject1)) || (!((String)localObject6).equals(localObject2)) || (!str4.equals(localObject3))))
      {
        if ((localObject1 != null) || (localObject2 != null) || (localObject3 != null))
        {
          localObject7 = new ReportRow(localLocale);
          localObject8 = new ReportDataItems(localLocale);
          ((ReportRow)localObject7).setDataItems((ReportDataItems)localObject8);
          localObject9 = null;
          String str5 = (String)paramReportCriteria.getSearchCriteria().get("Date Range Type");
          if ("Relative".equals(str5)) {
            localObject9 = paramReportCriteria.getDisplayValue("Date Range");
          } else {
            localObject9 = a(paramReportCriteria, str3, localLocale);
          }
          jdField_if((ReportDataItems)localObject8, localObject3);
          jdField_if((ReportDataItems)localObject8, localObject4.getDisplayText("CSV"));
          jdField_if((ReportDataItems)localObject8, localObject9);
          jdField_if((ReportDataItems)localObject8, str1);
          jdField_if((ReportDataItems)localObject8, str2);
          jdField_if((ReportDataItems)localObject8, localCurrency1 == null ? null : localCurrency1.getCurrencyStringNoSymbol());
          jdField_if((ReportDataItems)localObject8, null);
          jdField_if((ReportDataItems)localObject8, null);
          jdField_if((ReportDataItems)localObject8, null);
          jdField_if((ReportDataItems)localObject8, null);
          jdField_if((ReportDataItems)localObject8, localObject4.getCurrencyCode());
          jdField_if((ReportDataItems)localObject8, null);
          jdField_if((ReportDataItems)localObject8, null);
          jdField_if((ReportDataItems)localObject8, null);
          paramReportResult.addRow((ReportRow)localObject7);
        }
        localObject1 = localObject5;
        localObject2 = localObject6;
        localObject3 = str4;
        localObject4 = localAccount;
        localCurrency1 = new Currency("0", localAccount.getCurrencyCode(), localLocale);
      }
      localObject7 = new ReportDataItems(localLocale);
      Object localObject8 = new ReportRow(localLocale);
      ((ReportRow)localObject8).setDataItems((ReportDataItems)localObject7);
      jdField_if((ReportDataItems)localObject7, str4);
      jdField_if((ReportDataItems)localObject7, localAccount.getDisplayText("CSV"));
      Object localObject9 = a(paramResultSet, 5, localLocale);
      if ((str3 != null) && (localObject9 != null)) {
        ((com.ffusion.beans.DateTime)localObject9).setFormat(str3);
      }
      jdField_if((ReportDataItems)localObject7, ((com.ffusion.beans.DateTime)localObject9).toString());
      int i2 = paramResultSet.getInt(7);
      jdField_if((ReportDataItems)localObject7, Integer.toString(i2));
      localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i2);
      }
      catch (CSILException localCSILException) {}
      String str6 = paramResultSet.getString(19);
      jdField_if((ReportDataItems)localObject7, str6);
      Currency localCurrency2 = a(paramResultSet, 13, localLocale, localAccount.getCurrencyCode());
      if ((paramBoolean) && (localCurrency2 != null)) {
        localCurrency1.addAmount(localCurrency2);
      }
      jdField_if((ReportDataItems)localObject7, localCurrency2 == null ? null : localCurrency2.getCurrencyStringNoSymbol());
      jdField_if((ReportDataItems)localObject7, null);
      jdField_if((ReportDataItems)localObject7, DCAdapter.unPadRefNum(paramResultSet.getString(9), DCAdapter.getBankReferenceNumberType()));
      jdField_if((ReportDataItems)localObject7, DCAdapter.unPadRefNum(paramResultSet.getString(10), DCAdapter.getCustomerReferenceNumberType()));
      String str7 = paramResultSet.getString(12);
      if ((str7 != null) && (str7.trim().length() == 0)) {
        str7 = null;
      }
      jdField_if((ReportDataItems)localObject7, str7);
      jdField_if((ReportDataItems)localObject7, localAccount.getCurrencyCode());
      Currency localCurrency3 = a(paramResultSet, 16, localLocale, localAccount.getCurrencyCode());
      jdField_if((ReportDataItems)localObject7, localCurrency3 == null ? null : localCurrency3.getCurrencyStringNoSymbol());
      Currency localCurrency4 = a(paramResultSet, 17, localLocale, localAccount.getCurrencyCode());
      jdField_if((ReportDataItems)localObject7, localCurrency4 == null ? null : localCurrency4.getCurrencyStringNoSymbol());
      Currency localCurrency5 = a(paramResultSet, 18, localLocale, localAccount.getCurrencyCode());
      jdField_if((ReportDataItems)localObject7, localCurrency5 == null ? null : localCurrency5.getCurrencyStringNoSymbol());
      paramReportResult.addRow((ReportRow)localObject8);
      localObject8 = null;
      i1++;
    }
    if ((paramBoolean) && ((localObject1 != null) || (localObject2 != null) || (localObject3 != null)))
    {
      localObject5 = new ReportRow(localLocale);
      localObject6 = new ReportDataItems(localLocale);
      ((ReportRow)localObject5).setDataItems((ReportDataItems)localObject6);
      str4 = null;
      localObject7 = (String)paramReportCriteria.getSearchCriteria().get("Date Range Type");
      if ("Relative".equals(localObject7)) {
        str4 = paramReportCriteria.getDisplayValue("Date Range");
      } else {
        str4 = a(paramReportCriteria, str3, localLocale);
      }
      jdField_if((ReportDataItems)localObject6, localObject3);
      jdField_if((ReportDataItems)localObject6, localAccount.getDisplayText("CSV"));
      jdField_if((ReportDataItems)localObject6, str4);
      jdField_if((ReportDataItems)localObject6, str1);
      jdField_if((ReportDataItems)localObject6, str2);
      jdField_if((ReportDataItems)localObject6, localCurrency1 == null ? null : localCurrency1.getCurrencyStringNoSymbol());
      jdField_if((ReportDataItems)localObject6, null);
      jdField_if((ReportDataItems)localObject6, null);
      jdField_if((ReportDataItems)localObject6, null);
      jdField_if((ReportDataItems)localObject6, null);
      jdField_if((ReportDataItems)localObject6, localAccount.getCurrencyCode());
      jdField_if((ReportDataItems)localObject6, null);
      jdField_if((ReportDataItems)localObject6, null);
      jdField_if((ReportDataItems)localObject6, null);
      paramReportResult.addRow((ReportRow)localObject5);
    }
    return i1;
  }
  
  private static void jdField_if(Properties paramProperties, HashMap paramHashMap, boolean paramBoolean)
    throws DCException
  {
    String str1;
    if (paramBoolean) {
      str1 = paramProperties.getProperty("Accounts");
    } else {
      str1 = paramProperties.getProperty("Accounts");
    }
    Object localObject1;
    Object localObject2;
    if ((str1 == null) || (str1.equals("")))
    {
      localObject1 = "No accounts specified in the search criteria";
      localObject2 = new DCException(326, (String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject3;
    if (accountCriterionHasAllAccounts(str1))
    {
      a(paramHashMap, "attempt to retrieve accounts");
      if (paramBoolean) {
        localObject1 = (Accounts)paramHashMap.get("Accounts");
      } else {
        localObject1 = (Accounts)paramHashMap.get("Accounts");
      }
      if ((localObject1 == null) || (((Accounts)localObject1).isEmpty()))
      {
        localObject2 = "All accounts are specified in the search criteria but no accounts are specified in the extra hashmap.";
        localObject3 = new DCException(327, (String)localObject2);
        DebugLog.throwing((String)localObject2, (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
    }
    else
    {
      localObject1 = new StringTokenizer(str1, ",");
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        localObject2 = ((StringTokenizer)localObject1).nextToken();
        if (paramBoolean) {
          localObject3 = new FFIStringTokenizer((String)localObject2, ":");
        } else {
          localObject3 = new FFIStringTokenizer((String)localObject2, ":");
        }
        if (((FFIStringTokenizer)localObject3).countTokens() < 3)
        {
          String str2 = "The account (value '" + (String)localObject2 + "') was not passed in properly as search criteria.";
          DCException localDCException = new DCException(329, str2);
          DebugLog.throwing(str2, localDCException);
          throw localDCException;
        }
      }
    }
  }
  
  private static void a(Properties paramProperties, HashMap paramHashMap, boolean paramBoolean, ReportCriteria paramReportCriteria)
    throws DCException
  {
    String str1 = "DCAccountReport.validateDateRange";
    a(paramHashMap, "attempt to retrieve start dates, end dates, accounts for report");
    HashMap localHashMap = (HashMap)paramHashMap.get("StartDates");
    Object localObject2;
    if (localHashMap == null)
    {
      localObject1 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hashmap.";
      localObject2 = new DCException(322, (String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = (HashMap)paramHashMap.get("EndDates");
    if (localObject1 == null)
    {
      localObject2 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hashmap.";
      localObject3 = new DCException(323, (String)localObject2);
      DebugLog.throwing(str1, (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    if (paramBoolean) {
      localObject2 = paramProperties.getProperty("Accounts");
    } else {
      localObject2 = paramProperties.getProperty("Accounts");
    }
    Object localObject3 = new HashSet();
    Object localObject5;
    Object localObject6;
    String str3;
    DCException localDCException;
    String str2;
    if (accountCriterionHasAllAccounts((String)localObject2))
    {
      if (paramBoolean) {
        localObject4 = (Accounts)paramHashMap.get("Accounts");
      } else {
        localObject4 = (Accounts)paramHashMap.get("Accounts");
      }
      for (int i1 = 0; i1 < ((Accounts)localObject4).size(); i1++)
      {
        localObject5 = (Account)((Accounts)localObject4).get(i1);
        localObject6 = ((Account)localObject5).getRoutingNum();
        if (localObject6 == null)
        {
          str3 = "Account (id '" + ((Account)localObject5).getID() + "') missing routing number.";
          localDCException = new DCException(328, str3);
          DebugLog.throwing(str1, localDCException);
          throw localDCException;
        }
        ((HashSet)localObject3).add(localObject6);
      }
    }
    else
    {
      localObject4 = new StringTokenizer((String)localObject2, ",");
      while (((StringTokenizer)localObject4).hasMoreTokens())
      {
        str2 = ((StringTokenizer)localObject4).nextToken();
        if (paramBoolean) {
          localObject5 = new FFIStringTokenizer(str2, ":");
        } else {
          localObject5 = new FFIStringTokenizer(str2, ":");
        }
        ((FFIStringTokenizer)localObject5).nextToken();
        ((FFIStringTokenizer)localObject5).nextToken();
        localObject6 = ((FFIStringTokenizer)localObject5).nextToken();
        ((HashSet)localObject3).add(localObject6);
      }
    }
    Object localObject4 = ((HashSet)localObject3).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      str2 = (String)((Iterator)localObject4).next();
      localObject5 = (com.ffusion.util.beans.DateTime)localHashMap.get(str2);
      localObject6 = (com.ffusion.util.beans.DateTime)localHashMap.get(str2);
      if (localObject5 == null)
      {
        str3 = "No start date found for routing number " + str2 + ".";
        localDCException = new DCException(320, str3);
        DebugLog.throwing(str1, localDCException);
        throw localDCException;
      }
      if (localObject6 == null)
      {
        str3 = "No end date found for routing number " + str2 + ".";
        localDCException = new DCException(321, str3);
        DebugLog.throwing(str1, localDCException);
        throw localDCException;
      }
      a((com.ffusion.util.beans.DateTime)localObject5, (com.ffusion.util.beans.DateTime)localObject6, str2, paramReportCriteria);
    }
  }
  
  static void a(com.ffusion.util.beans.DateTime paramDateTime1, com.ffusion.util.beans.DateTime paramDateTime2, String paramString, ReportCriteria paramReportCriteria)
    throws DCException
  {
    Object localObject2;
    if (paramDateTime2.before(paramDateTime1))
    {
      localObject1 = "The end date " + paramDateTime2 + " is invalid - it is before the start date (" + paramDateTime1 + ")";
      localObject2 = new DCException(359, (String)localObject1);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = new com.ffusion.util.beans.DateTime(paramDateTime2, Locale.getDefault());
    ((com.ffusion.util.beans.DateTime)localObject1).add(5, -1 * a(paramReportCriteria));
    if (paramDateTime1.before(localObject1))
    {
      localObject2 = "The date range ";
      if (paramString != null) {
        localObject2 = (String)localObject2 + " for routing number " + paramString;
      }
      localObject2 = (String)localObject2 + " is too long - " + "the maximum range covered is " + a(paramReportCriteria) + " days.";
      DCException localDCException = new DCException(357, (String)localObject2);
      DebugLog.throwing((String)localObject2, localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_do(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCAccountReport.validateAcctHistoryDateRange";
    a(paramHashMap, "attempt to retrieve start dates, end dates, accounts for report");
    HashMap localHashMap = (HashMap)paramHashMap.get("StartDates");
    Object localObject2;
    if (localHashMap == null)
    {
      localObject1 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hashmap.";
      localObject2 = new DCException(322, (String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = (HashMap)paramHashMap.get("EndDates");
    if (localObject1 == null)
    {
      localObject2 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hashmap.";
      DCException localDCException = new DCException(323, (String)localObject2);
      DebugLog.throwing(str, localDCException);
      throw localDCException;
    }
  }
  
  private static void a(Properties paramProperties, HashMap paramHashMap, boolean paramBoolean)
    throws DCException
  {
    ArrayList localArrayList = new ArrayList();
    String str;
    if (paramBoolean)
    {
      str = paramProperties.getProperty("DataClassification");
      localArrayList.add("P");
      localArrayList.add("I");
    }
    else
    {
      str = paramProperties.getProperty("DataClassification");
      localArrayList.add("P");
      localArrayList.add("I");
    }
    a(str, localArrayList);
  }
  
  static void a(String paramString, ArrayList paramArrayList)
    throws DCException
  {
    Object localObject;
    if (paramString == null)
    {
      String str = "The data classification is missing.";
      localObject = new DCException(330, str);
      DebugLog.throwing(str, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    boolean bool = paramArrayList.contains(paramString);
    if (!bool)
    {
      localObject = "The data classification '" + paramString + "' is invalid.";
      DCException localDCException = new DCException(331, (String)localObject);
      DebugLog.throwing((String)localObject, localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_try(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = paramProperties.getProperty("MinimumAmount");
    String str2 = paramProperties.getProperty("MaximumAmount");
    a(str1, str2);
  }
  
  static void a(String paramString1, String paramString2)
    throws DCException
  {
    String str1 = "validateAmountRange";
    String str2 = "0123456789 .,";
    Double localDouble1 = null;
    Object localObject3;
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      Object localObject1;
      for (int i1 = 0; i1 < paramString1.length(); i1++)
      {
        char c1 = paramString1.charAt(i1);
        if (str2.indexOf(c1) == -1)
        {
          localObject1 = "The minimum amount contains an invalid character '" + c1 + "'";
          localObject3 = new DCException(332, (String)localObject1);
          DebugLog.throwing(str1, (Throwable)localObject3);
          throw ((Throwable)localObject3);
        }
      }
      try
      {
        localDouble1 = new Double(paramString1);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        String str3 = "The minimum amount '" + paramString1 + "' is not a valid amount.";
        localObject1 = new DCException(332, str3);
        DebugLog.throwing(str1, (Throwable)localObject1);
        throw ((Throwable)localObject1);
      }
    }
    Double localDouble2 = null;
    Object localObject2;
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      for (int i2 = 0; i2 < paramString2.length(); i2++)
      {
        char c2 = paramString2.charAt(i2);
        if (str2.indexOf(c2) == -1)
        {
          localObject3 = "The maximum amount contains an invalid character '" + c2 + "'";
          DCException localDCException = new DCException(333, (String)localObject3);
          DebugLog.throwing(str1, localDCException);
          throw localDCException;
        }
      }
      try
      {
        localDouble2 = new Double(paramString2);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        localObject2 = "The maximum amount '" + paramString2 + "' is not a valid amount.";
        localObject3 = new DCException(333, (String)localObject2);
        DebugLog.throwing(str1, (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
    }
    if ((localDouble1 != null) && (localDouble2 != null) && (localDouble2.compareTo(localDouble1) < 0))
    {
      String str4 = "The amount range provided is invalid because the maximum amount " + paramString2 + " is less than the minimum amount " + paramString1 + ".";
      localObject2 = new DCException(366, str4);
      DebugLog.throwing(str1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
  }
  
  private static void a(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = paramProperties.getProperty("TransactionType");
    if ((str1 == null) || (str1.length() == 0)) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    int i1 = 0;
    int i2 = 0;
    String str2;
    while (localStringTokenizer.hasMoreTokens())
    {
      str2 = localStringTokenizer.nextToken();
      if ("AllTransactionTypes".equals(str2))
      {
        i2 = 1;
        break;
      }
      if (str2.startsWith("CustTransGrp_")) {
        i1 = 1;
      }
    }
    if ((i2 == 0) && (i1 != 0))
    {
      a(paramHashMap, "attempt to retrieve data with key GenericBankingRptConsts.CUSTOM_TRANS_GROUP_CODES_FOR_REPORT");
      str2 = (String)paramHashMap.get("CustomTransGroupCodesForReport");
      Object localObject2;
      if (str2 == null)
      {
        localObject1 = "extra hashmap is missing the BAI codes specified by the custom transaction groups";
        localObject2 = new DCException(340, (String)localObject1);
        DebugLog.throwing((String)localObject1, (Throwable)localObject2);
        throw ((Throwable)localObject2);
      }
      Object localObject1 = new StringTokenizer(str2, ",");
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        localObject2 = ((StringTokenizer)localObject1).nextToken();
        try
        {
          Integer.parseInt((String)localObject2);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          String str3 = "Non-integer value '" + (String)localObject2 + "' specified as BAI code for custom transaction group";
          DCException localDCException = new DCException(339, str3);
          DebugLog.throwing(str3, localDCException);
          throw localDCException;
        }
      }
    }
  }
  
  private static void jdField_if(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str = paramProperties.getProperty("TransactionSubType");
    jdField_int(str);
  }
  
  static void jdField_int(String paramString)
    throws DCException
  {
    if (paramString != null)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str1 = localStringTokenizer.nextToken();
        if (!str1.equals("AllTransactionSubtypes")) {
          try
          {
            Integer.parseInt(str1);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            String str2 = "Non-integer value '" + str1 + "' specified as transaction code";
            DCException localDCException = new DCException(338, str2);
            DebugLog.throwing(str2, localDCException);
            throw localDCException;
          }
        }
      }
    }
  }
  
  private static void jdField_int(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = paramProperties.getProperty("BAISummaryCode");
    if ((str1 == null) || (str1.length() == 0)) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = localStringTokenizer.nextToken();
      if (!str2.equals("AllSummaryCodes")) {
        try
        {
          Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          String str3 = "Non-integer value '" + str2 + "' specified as summary code";
          DCException localDCException = new DCException(336, str3);
          DebugLog.throwing(str3, localDCException);
          throw localDCException;
        }
      }
    }
  }
  
  private static void jdField_for(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str = paramProperties.getProperty("CustomerReferenceNumber");
    a(str);
  }
  
  static void a(String paramString)
    throws DCException
  {
    if ((paramString != null) && (paramString.length() > 40))
    {
      String str = "Customer Reference Number is too long (" + paramString.length() + " characters)" + " - maximum is " + 40 + " characters.";
      DCException localDCException = new DCException(334, str);
      DebugLog.throwing(str, localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_new(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str = paramProperties.getProperty("BankReferenceNumber");
    jdField_if(str);
  }
  
  private static void jdField_if(String paramString)
    throws DCException
  {
    if ((paramString != null) && (paramString.length() > 40))
    {
      String str = "Bank Reference Number is too long (" + paramString.length() + " characters)" + " - maximum is " + 40 + " characters.";
      DCException localDCException = new DCException(335, str);
      DebugLog.throwing(str, localDCException);
      throw localDCException;
    }
  }
  
  private static void a(Properties paramProperties1, Locale paramLocale, HashMap paramHashMap, Properties paramProperties2)
    throws DCException
  {
    String str1 = paramProperties1.getProperty("ForecastDate");
    String str2 = "MM/dd/yyyy";
    Object localObject;
    if ((str1 == null) || (str1.length() == 0))
    {
      String str3 = "ForecastDate cannot be empty.";
      localObject = new DCException(324, str3);
      DebugLog.throwing(str3, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    try
    {
      a(str1, str2, paramLocale);
    }
    catch (ParseException localParseException)
    {
      localObject = "Forecast date is not in " + str2 + " format.";
      DCException localDCException = new DCException(325, (String)localObject);
      DebugLog.throwing((String)localObject, localDCException);
      throw localDCException;
    }
  }
  
  private static com.ffusion.beans.DateTime a(String paramString, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private static com.ffusion.beans.DateTime a(String paramString1, String paramString2, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter(paramString2).parse(paramString1);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private static void a(HashMap paramHashMap, String paramString)
    throws DCException
  {
    if (paramHashMap == null)
    {
      String str = "extra is null when it is required ";
      if (paramString != null) {
        str = str + "(" + paramString + ")";
      }
      DCException localDCException = new DCException(317, str);
      DebugLog.throwing(str, localDCException);
      throw localDCException;
    }
  }
  
  private static void a(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      localObject2 = new DCException(314, (String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = paramReportCriteria.getReportOptions();
    if (localObject1 == null)
    {
      localObject2 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      localObject3 = new DCException(315, (String)localObject2);
      DebugLog.throwing((String)localObject2, (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    Object localObject2 = paramReportCriteria.getSortCriteria();
    Object localObject4;
    if (localObject2 == null)
    {
      localObject3 = "A sort criteria object was not found within the given report criteria.";
      localObject4 = new DCException(316, (String)localObject3);
      DebugLog.throwing((String)localObject3, (Throwable)localObject4);
      throw ((Throwable)localObject4);
    }
    Object localObject3 = ((Properties)localObject1).getProperty("REPORTTYPE");
    if (localObject3 == null)
    {
      localObject4 = "The report options contained within the Report Criteria used in a call to getAccountReportData does not contain a report type.";
      DCException localDCException = new DCException(318, (String)localObject4);
      DebugLog.throwing((String)localObject4, localDCException);
      throw localDCException;
    }
    if (((String)localObject3).equals("DepositDetail"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      jdField_try(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_if(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("TransactionDetail"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      jdField_try(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_if(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("CreditReport"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      jdField_try(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_if(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("DebitReport"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      jdField_try(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_if(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("CustomSummaryReport"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      jdField_int(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("AccountHistory"))
    {
      jdField_if(localProperties, paramHashMap, false);
      jdField_do(localProperties, paramHashMap);
      a(localProperties, paramHashMap, false);
      jdField_try(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("CashFlowReport"))
    {
      jdField_if(localProperties, paramHashMap, true);
      a(localProperties, paramHashMap, true, paramReportCriteria);
      a(localProperties, paramHashMap, true);
    }
    else if (((String)localObject3).equals("CashFlowForecastReport"))
    {
      jdField_if(localProperties, paramHashMap, true);
      a(localProperties, paramHashMap, true, paramReportCriteria);
      a(localProperties, paramHashMap, true);
      a(localProperties, paramReportCriteria.getLocale(), paramHashMap, paramReportCriteria.getReportOptions());
    }
    else if (((String)localObject3).equals("BalanceSheetReport"))
    {
      jdField_if(localProperties, paramHashMap, true);
      a(localProperties, paramHashMap, true, paramReportCriteria);
      a(localProperties, paramHashMap, true);
    }
    else if (((String)localObject3).equals("BalanceSheetSummary"))
    {
      jdField_if(localProperties, paramHashMap, true);
      a(localProperties, paramHashMap, true, paramReportCriteria);
      a(localProperties, paramHashMap, true);
    }
    else if (((String)localObject3).equals("GeneralLedgerReport"))
    {
      jdField_if(localProperties, paramHashMap, true);
      a(localProperties, paramHashMap, true, paramReportCriteria);
      a(localProperties, paramHashMap, true);
    }
    else if (((String)localObject3).equals("BalanceDetailReport"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("BalanceSummaryReport"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
    }
    else if (((String)localObject3).equals("BalanceDetailOnlyReport"))
    {
      jdField_if(localProperties, paramHashMap, false);
      a(localProperties, paramHashMap, false, paramReportCriteria);
      a(localProperties, paramHashMap, false);
      jdField_try(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_if(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
    }
  }
  
  public static IReportResult createAlertTransactionReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    IReportResult localIReportResult1 = null;
    Vector localVector = new Vector();
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool1 = "BAI2".equals(str2);
    boolean bool2 = "QIF".equals(str2);
    boolean bool3 = "QFX".equals(str2);
    boolean bool4 = "QBO".equals(str2);
    boolean bool5 = "IIF".equals(str2);
    int i1 = (bool1) || (bool2) || (bool3) || (bool4) || (bool5) ? 1 : 0;
    try
    {
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      str3 = a(localReportSortCriteria, str1);
      String str4 = jdField_if(paramReportCriteria, localVector, paramHashMap, str1);
      if (str4 == null)
      {
        ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
        return localReportResult;
      }
      int i2 = a(localProperties2);
      localIReportResult1 = getAlertTransactionReportData(paramReportCriteria, paramHashMap);
      IReportResult localIReportResult2 = localIReportResult1;
      return localIReportResult2;
    }
    catch (DCException localDCException)
    {
      localDCException = localDCException;
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException)
    {
      localException = localException;
      localException.printStackTrace(System.err);
      String str3 = "Exception when attempting to get report data.";
      throw new DCException(313, str3, localException);
    }
    finally {}
  }
  
  public static IReportResult createCashFlowReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement1 = new PreparedStatement[1];
    PreparedStatement[] arrayOfPreparedStatement2 = new PreparedStatement[1];
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    Vector localVector = new Vector();
    Connection localConnection = null;
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool1 = "BAI2".equals(str2);
    boolean bool2 = "QIF".equals(str2);
    boolean bool3 = "QFX".equals(str2);
    boolean bool4 = "QBO".equals(str2);
    boolean bool5 = "IIF".equals(str2);
    int i1 = (bool1) || (bool2) || (bool3) || (bool4) || (bool5) ? 1 : 0;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      str3 = a(localReportSortCriteria, str1);
      String str4 = null;
      int i2 = a(localProperties2);
      String str5 = (String)localProperties1.get("Accounts");
      Locale localLocale = paramReportCriteria.getLocale();
      ReportResult localReportResult1 = new ReportResult(localLocale);
      try
      {
        localReportResult1.init(paramReportCriteria);
        Currency localCurrency1 = new Currency(new BigDecimal(0.0D), localLocale);
        Currency localCurrency2 = new Currency(new BigDecimal(0.0D), localLocale);
        ReportResult localReportResult2 = new ReportResult(localLocale);
        localReportResult1.addSubReport(localReportResult2);
        a(localReportResult2, 4, -1);
        a.a(localReportResult2, localLocale, a3);
        a(paramHashMap, "attempt to get secure user for report");
        SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
        int i4;
        Object localObject5;
        Object localObject6;
        Object localObject7;
        if ((str5 == null) || (accountCriterionHasAllAccounts(str5)))
        {
          a(paramHashMap, "attempt to get accounts");
          localObject3 = (Accounts)paramHashMap.get("Accounts");
          if (!localReportSortCriteria.isEmpty())
          {
            localObject4 = ((ReportSortCriterion)localReportSortCriteria.get(0)).getName();
            if ((localObject4 != null) && (((String)localObject4).equals("AccountNumber"))) {
              ((Accounts)localObject3).setSortedBy("ID");
            }
          }
          Object localObject4 = ((Accounts)localObject3).iterator();
          if (!((Iterator)localObject4).hasNext()) {
            throw new DCException("No Accounts defined in extra.");
          }
          for (i4 = 1; ((Iterator)localObject4).hasNext(); i4++)
          {
            HashMap localHashMap = new HashMap();
            try
            {
              a(paramHashMap, "attempt to create a copy of extra hashmap using some data that should currently be in extra hashmap");
              localObject5 = (Account)((Iterator)localObject4).next();
              localObject6 = new Accounts();
              ((Accounts)localObject6).add(localObject5);
              localHashMap.put("Accounts", localObject6);
              localHashMap.put("StartDates", paramHashMap.get("StartDates"));
              localHashMap.put("EndDates", paramHashMap.get("EndDates"));
              localHashMap.put("BusinessForReport", paramHashMap.get("BusinessForReport"));
              str4 = jdField_if(paramReportCriteria, localVector, localHashMap, str1);
              if (str4 == null)
              {
                localObject7 = localReportResult1;
                jsr 140;
                localReportResult1.fini();
                localObject1 = localReportResult1;
                return localObject7;
              }
              localResultSet1 = a(arrayOfPreparedStatement1, "SELECT dcAccount.AccountID, dcAccount.BankID, dcAccount.RoutingNumber, dcAcctHistory.DataDate, dcAcctHistory.OpeningLedger, dcAcctHistory.AvgOpenLedgerMTD, dcAcctHistory.AvgOpenLedgerYTD , dcAcctHistory.ClosingLedger, dcAcctHistory.AvgCloseLedgerMTD, dcAcctHistory.AvgMonth, dcAcctHistory.AggBalanceAdjust, dcAcctHistory.AvCloseLedgYTDPrvM, dcAcctHistory.AvgCloseLedgerYTD, dcAcctHistory.CurrentLedger, dcAcctHistory.ACHNetPosition, dcAcctHistory.OpAvaiSamDayACHDTC, dcAcctHistory.OpeningAvailable, dcAcctHistory.AvgOpenAvailMTD, dcAcctHistory.AvgOpenAvailYTD, dcAcctHistory.AvgAvailPrevMonth, dcAcctHistory.DisbOpenAvailBal, dcAcctHistory.ClosingAvail, dcAcctHistory.AvgCloseAvailMTD, dcAcctHistory.AvCloseAvailPreM, dcAcctHistory.AvCloseAvailYTDPrM, dcAcctHistory.AvCloseAvailYTD, dcAcctHistory.LoanBalance, dcAcctHistory.TotalInvestPosn, dcAcctHistory.CurrAvailCRSSupr, dcAcctHistory.CurrentAvail, dcAcctHistory.AvgCurrAvailMTD, dcAcctHistory.AvgCurrAvailYTD, dcAcctHistory.TotalFloat, dcAcctHistory.TargetBalance, dcAcctHistory.AdjBalance, dcAcctHistory.AdjBalanceMTD, dcAcctHistory.AdjBalanceYTD, dcAcctHistory.ZeroDayFloat, dcAcctHistory.OneDayFloat, dcAcctHistory.FloatAdj, dcAcctHistory.TwoMoreDayFloat, dcAcctHistory.ThreeMoreDayFloat, dcAcctHistory.AdjToBalances, dcAcctHistory.AvgAdjToBalMTD, dcAcctHistory.AvgAdjToBalYTD, dcAcctHistory.FourDayFloat, dcAcctHistory.FiveDayFloat, dcAcctHistory.SixDayFloat, dcAcctHistory.AvgOneDayFloatMTD, dcAcctHistory.AvgOneDayFloatYTD, dcAcctHistory.AvgTwoDayFloatMTD, dcAcctHistory.AvgTwoDayFloatYTD, dcAcctHistory.TransferCalc, dcAcctHistory.TargBalDeficiency, dcAcctHistory.TotalFundingReq, dcAcctHistory.TotalChecksPaid, dcAcctHistory.GrandTotCredMinDeb FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount ", str4, str3, localVector, localConnection, "1");
              str4 = a(paramReportCriteria, localVector, localHashMap);
              if (str4 == null)
              {
                localObject7 = localReportResult1;
                jsr 75;
                localReportResult1.fini();
                localObject1 = localReportResult1;
                return localObject7;
              }
              localResultSet2 = a(arrayOfPreparedStatement2, "SELECT SUM(dcTransactions.Amount)  FROM DC_Account dcAccount, DC_Transactions dcTransactions ", str4, null, localVector, localConnection);
              a(localResultSet1, localResultSet2, localReportResult2, i4, localCurrency1, localCurrency2, (Account)localObject5, localSecureUser);
            }
            finally
            {
              jsr 6;
            }
            localObject9 = returnAddress;
            DBUtil.closeResultSet(localResultSet1);
            DBUtil.closeStatement(arrayOfPreparedStatement1[0]);
            DBUtil.closeResultSet(localResultSet2);
            DBUtil.closeStatement(arrayOfPreparedStatement2[0]);
            ret;
          }
        }
        else
        {
          localObject3 = new StringTokenizer(str5, ",");
          int i3 = ((StringTokenizer)localObject3).countTokens();
          if (i3 < 1) {
            throw new DCException("Value (" + str5 + ") for SearchCriteria key " + "Accounts" + " has incorrect format.");
          }
          i4 = 1;
          for (int i5 = 0; i5 < i3; i5++)
          {
            localObject5 = new HashMap();
            localVector = new Vector();
            try
            {
              localObject6 = ((StringTokenizer)localObject3).nextToken();
              localObject7 = getAccountFromAccountCriterion((String)localObject6);
              Accounts localAccounts = new Accounts();
              localAccounts.add(localObject7);
              a(paramHashMap, "attempt to create a copy of extra hashmap using some data that should currently be in extra hashmap");
              ((HashMap)localObject5).put("AccountsForReport", paramHashMap.get("AccountsForReport"));
              ((HashMap)localObject5).put("Accounts", localAccounts);
              ((HashMap)localObject5).put("StartDates", paramHashMap.get("StartDates"));
              ((HashMap)localObject5).put("EndDates", paramHashMap.get("EndDates"));
              ((HashMap)localObject5).put("BusinessForReport", paramHashMap.get("BusinessForReport"));
              Properties localProperties3 = new Properties(localProperties1);
              localProperties3.setProperty("Accounts", (String)localObject6);
              ReportCriteria localReportCriteria = (ReportCriteria)paramReportCriteria.clone();
              localReportCriteria.setSearchCriteria(localProperties3);
              localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
              str4 = jdField_if(localReportCriteria, localVector, (HashMap)localObject5, str1);
              paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
              ReportResult localReportResult3;
              if (str4 == null)
              {
                localReportResult3 = localReportResult1;
                jsr 143;
                localReportResult1.fini();
                localObject1 = localReportResult1;
                return localReportResult3;
              }
              localResultSet1 = a(arrayOfPreparedStatement1, "SELECT dcAccount.AccountID, dcAccount.BankID, dcAccount.RoutingNumber, dcAcctHistory.DataDate, dcAcctHistory.OpeningLedger, dcAcctHistory.AvgOpenLedgerMTD, dcAcctHistory.AvgOpenLedgerYTD , dcAcctHistory.ClosingLedger, dcAcctHistory.AvgCloseLedgerMTD, dcAcctHistory.AvgMonth, dcAcctHistory.AggBalanceAdjust, dcAcctHistory.AvCloseLedgYTDPrvM, dcAcctHistory.AvgCloseLedgerYTD, dcAcctHistory.CurrentLedger, dcAcctHistory.ACHNetPosition, dcAcctHistory.OpAvaiSamDayACHDTC, dcAcctHistory.OpeningAvailable, dcAcctHistory.AvgOpenAvailMTD, dcAcctHistory.AvgOpenAvailYTD, dcAcctHistory.AvgAvailPrevMonth, dcAcctHistory.DisbOpenAvailBal, dcAcctHistory.ClosingAvail, dcAcctHistory.AvgCloseAvailMTD, dcAcctHistory.AvCloseAvailPreM, dcAcctHistory.AvCloseAvailYTDPrM, dcAcctHistory.AvCloseAvailYTD, dcAcctHistory.LoanBalance, dcAcctHistory.TotalInvestPosn, dcAcctHistory.CurrAvailCRSSupr, dcAcctHistory.CurrentAvail, dcAcctHistory.AvgCurrAvailMTD, dcAcctHistory.AvgCurrAvailYTD, dcAcctHistory.TotalFloat, dcAcctHistory.TargetBalance, dcAcctHistory.AdjBalance, dcAcctHistory.AdjBalanceMTD, dcAcctHistory.AdjBalanceYTD, dcAcctHistory.ZeroDayFloat, dcAcctHistory.OneDayFloat, dcAcctHistory.FloatAdj, dcAcctHistory.TwoMoreDayFloat, dcAcctHistory.ThreeMoreDayFloat, dcAcctHistory.AdjToBalances, dcAcctHistory.AvgAdjToBalMTD, dcAcctHistory.AvgAdjToBalYTD, dcAcctHistory.FourDayFloat, dcAcctHistory.FiveDayFloat, dcAcctHistory.SixDayFloat, dcAcctHistory.AvgOneDayFloatMTD, dcAcctHistory.AvgOneDayFloatYTD, dcAcctHistory.AvgTwoDayFloatMTD, dcAcctHistory.AvgTwoDayFloatYTD, dcAcctHistory.TransferCalc, dcAcctHistory.TargBalDeficiency, dcAcctHistory.TotalFundingReq, dcAcctHistory.TotalChecksPaid, dcAcctHistory.GrandTotCredMinDeb FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount ", str4, str3, localVector, localConnection, "1");
              str4 = a(paramReportCriteria, localVector, (HashMap)localObject5);
              if (str4 == null)
              {
                localReportResult3 = localReportResult1;
                jsr 78;
                localReportResult1.fini();
                localObject1 = localReportResult1;
                return localReportResult3;
              }
              localResultSet2 = a(arrayOfPreparedStatement2, "SELECT SUM(dcTransactions.Amount)  FROM DC_Account dcAccount, DC_Transactions dcTransactions ", str4, null, localVector, localConnection);
              a(localResultSet1, localResultSet2, localReportResult2, i4, localCurrency1, localCurrency2, (Account)localObject7, localSecureUser);
              i4++;
            }
            finally
            {
              jsr 6;
            }
            localObject11 = returnAddress;
            DBUtil.closeResultSet(localResultSet1);
            DBUtil.closeStatement(arrayOfPreparedStatement1[0]);
            DBUtil.closeResultSet(localResultSet2);
            DBUtil.closeStatement(arrayOfPreparedStatement2[0]);
            ret;
          }
        }
        Object localObject3 = new Object[1];
        localObject3[0] = localCurrency1.getCurrencyCode();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Cash_Flow_Report_Totals_Format", (Object[])localObject3);
        a(localReportResult1, (String)localLocalizableString.localize(localLocale));
        a(localReportResult1, 4, 1);
        a.a(localReportResult1, localLocale, al);
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        localReportDataItems.add(null);
        localReportDataItems.add().setData(localCurrency1.toString());
        double d1 = localCurrency2.doubleValue() - localCurrency1.doubleValue();
        localReportDataItems.add().setData(new Currency(new BigDecimal(d1), localLocale).toString());
        localReportDataItems.add().setData(localCurrency2.toString());
        a(localReportResult1, localReportDataItems, -1);
      }
      catch (Exception localException2)
      {
        localReportResult1.setError(localException2);
        throw localException2;
      }
      finally
      {
        localReportResult1.fini();
        localObject1 = localReportResult1;
      }
      Object localObject2 = localObject1;
      return localObject2;
    }
    catch (DCException localDCException)
    {
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      String str3 = "Exception when attempting to get report data.";
      throw new DCException(313, str3, localException1);
    }
    finally
    {
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static IReportResult createCashFlowForeCastReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    Object localObject1 = null;
    arrayOfPreparedStatement = new PreparedStatement[1];
    localResultSet = null;
    Vector localVector = new Vector();
    localConnection = null;
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool1 = "BAI2".equals(str2);
    boolean bool2 = "QIF".equals(str2);
    boolean bool3 = "QFX".equals(str2);
    boolean bool4 = "QBO".equals(str2);
    boolean bool5 = "IIF".equals(str2);
    int i1 = (bool1) || (bool2) || (bool3) || (bool4) || (bool5) ? 1 : 0;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      localReportResult1 = new ReportResult(paramReportCriteria.getLocale());
      localObject2 = paramReportCriteria.getSortCriteria();
      String str3 = a((ReportSortCriteria)localObject2, str1);
      str4 = jdField_if(paramReportCriteria, localVector, paramHashMap, str1);
      if (str4 == null)
      {
        ReportResult localReportResult2 = localReportResult1;
        return localReportResult2;
      }
      int i2 = a(localProperties2);
      String str5 = localProperties1.getProperty("ForecastDate");
      String str6 = "MM/dd/yyyy";
      localDateTime = null;
      if ((str5 != null) && (str5.length() != 0)) {
        try
        {
          localDateTime = a(str5, str6, paramReportCriteria.getLocale());
        }
        catch (ParseException localParseException)
        {
          throw new DCException("ForecastDate is not in + " + str6, localParseException);
        }
      } else {
        throw new DCException("ForecastDate cannot be empty.");
      }
    }
    catch (DCException localDCException)
    {
      ReportResult localReportResult1;
      String str4;
      com.ffusion.beans.DateTime localDateTime;
      StringBuffer localStringBuffer;
      Object localObject3;
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      Object localObject2 = "Exception when attempting to get report data.";
      throw new DCException(313, (String)localObject2, localException1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection == null) {
        break label498;
      }
      DCAdapter.releaseDBConnection(localConnection);
    }
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("dcAccount.AccountID").append(", ").append("dcAcctHistory.DataDate");
    localResultSet = a(arrayOfPreparedStatement, "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcAcctHistory.DataDate, dcAcctHistory.CurrentLedger FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount ", str4, localStringBuffer.toString(), localVector, localConnection);
    try
    {
      localReportResult1.init(paramReportCriteria);
      String str7 = (String)paramReportCriteria.getSearchCriteria().get("Accounts");
      a(localReportResult1, localResultSet, localDateTime, str7, paramHashMap);
    }
    catch (Exception localException2)
    {
      localReportResult1.setError(localException2);
      throw localException2;
    }
    finally
    {
      localReportResult1.fini();
      localObject1 = localReportResult1;
    }
    localObject3 = localObject1;
    jsr 56;
    return localObject3;
  }
  
  public static IReportResult createBalanceSheetReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Vector localVector = new Vector();
    Connection localConnection = null;
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool1 = "BAI2".equals(str2);
    boolean bool2 = "QIF".equals(str2);
    boolean bool3 = "QFX".equals(str2);
    boolean bool4 = "QBO".equals(str2);
    boolean bool5 = "IIF".equals(str2);
    int i1 = (bool1) || (bool2) || (bool3) || (bool4) || (bool5) ? 1 : 0;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ReportResult localReportResult1 = new ReportResult(paramReportCriteria.getLocale());
      localObject2 = paramReportCriteria.getSortCriteria();
      String str3 = a((ReportSortCriteria)localObject2, str1);
      String str4 = jdField_if(paramReportCriteria, localVector, paramHashMap, str1);
      if (str4 == null)
      {
        ReportResult localReportResult2 = localReportResult1;
        return localReportResult2;
      }
      int i2 = a(localProperties2);
      localResultSet = a(arrayOfPreparedStatement, " ", "dcAcctHistory.DCAccountID=recentDate.DCAccountID AND dcAcctHistory.DataClassification=recentDate.DataClassification AND dcAcctHistory.DataDate=recentDate.MAX_DATE AND dcAcctHistory.DCAccountID=dcAccount.DCAccountID ", str3, localVector, localConnection, null, "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcAcctHistory.ClosingLedger FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount, ( SELECT dcAcctHistory.DCAccountID, max(dcAcctHistory.DataDate) AS MAX_DATE, dcAcctHistory.DataClassification FROM DC_AccountHistory dcAcctHistory, DC_Account dcAccount WHERE dcAcctHistory.ClosingLedger IS NOT null AND dcAcctHistory.DCAccountID=dcAccount.DCAccountID AND " + str4 + "GROUP BY dcAcctHistory.DCAccountID, dcAcctHistory.DataClassification ) recentDate ");
      Accounts localAccounts1 = a(localResultSet, paramReportCriteria.getLocale(), localConnection);
      a(paramHashMap, "get accounts to fill in nicknames");
      Accounts localAccounts2 = (Accounts)paramHashMap.get("Accounts");
      if ((localAccounts2 != null) && (localAccounts2.size() != 0)) {
        a(localAccounts1, localAccounts2);
      }
      a(paramHashMap, "attempt to get secure user for report");
      SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
      try
      {
        localReportResult1.init(paramReportCriteria);
        a(localReportResult1, localSecureUser, localAccounts1, str1.equals("BalanceSheetSummary"));
      }
      catch (Exception localException2)
      {
        localReportResult1.setError(localException2);
        throw localException2;
      }
      finally
      {
        localReportResult1.fini();
        localObject1 = localReportResult1;
      }
      Object localObject3 = localObject1;
      return localObject3;
    }
    catch (DCException localDCException)
    {
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      Object localObject2 = "Exception when attempting to get report data.";
      throw new DCException(313, (String)localObject2, localException1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static IReportResult createGeneralLedgerReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Vector localVector = new Vector();
    Connection localConnection = null;
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool1 = "BAI2".equals(str2);
    boolean bool2 = "QIF".equals(str2);
    boolean bool3 = "QFX".equals(str2);
    boolean bool4 = "QBO".equals(str2);
    boolean bool5 = "IIF".equals(str2);
    int i1 = (bool1) || (bool2) || (bool3) || (bool4) || (bool5) ? 1 : 0;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ReportResult localReportResult1 = new ReportResult(paramReportCriteria.getLocale());
      localObject2 = paramReportCriteria.getSortCriteria();
      String str3 = a((ReportSortCriteria)localObject2, str1);
      String str4 = jdField_if(paramReportCriteria, localVector, paramHashMap, str1);
      if (str4 == null)
      {
        a(localReportResult1, 2458);
        ReportResult localReportResult2 = localReportResult1;
        return localReportResult2;
      }
      int i2 = a(localProperties2);
      localResultSet = a(arrayOfPreparedStatement, "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.PayeePayor, dcTransactions.Description, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.ReferenceNumber, dcTransactions.Amount FROM DC_Account dcAccount, DC_Transactions dcTransactions ", str4, str3, localVector, localConnection);
      try
      {
        localReportResult1.init(paramReportCriteria);
        if (localResultSet.first())
        {
          localResultSet.beforeFirst();
          String str5 = (String)localProperties1.get("Accounts");
          Accounts localAccounts = null;
          if (accountCriterionHasAllAccounts(str5))
          {
            a(paramHashMap, "attempt to get accounts");
            localAccounts = (Accounts)paramHashMap.get("Accounts");
          }
          else
          {
            StringTokenizer localStringTokenizer = new StringTokenizer(str5, ",");
            int i3 = localStringTokenizer.countTokens();
            if (i3 < 1) {
              throw new DCException("Value for SearchCriteria key Accounts has incorrect format.");
            }
            localAccounts = new Accounts();
            for (int i4 = 0; i4 < i3; i4++)
            {
              String str7 = localStringTokenizer.nextToken();
              Account localAccount1 = getAccountFromAccountCriterion(str7);
              Account localAccount2 = localAccounts.create(localAccount1.getBankID(), localAccount1.getID(), localAccount1.getNumber(), localAccount1.getTypeValue());
              localAccount2.setRoutingNum(localAccount1.getRoutingNum());
              localAccount2.setNickName(localAccount1.getNickName());
              localAccount2.setCurrencyCode(localAccount1.getCurrencyCode());
              localAccount2.setAccountGroup(localAccount1.getAccountGroup());
            }
          }
          boolean bool6 = false;
          if (!((ReportSortCriteria)localObject2).isEmpty())
          {
            ((ReportSortCriteria)localObject2).setSortedBy("ORDINAL");
            str6 = ((ReportSortCriterion)((ReportSortCriteria)localObject2).get(0)).getName();
            bool6 = "AccountNumber".equals(str6);
          }
          String str6 = localProperties2.getProperty("DATEFORMAT");
          a(paramHashMap, "attempt to get secure user for report");
          SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
          a(localResultSet, localReportResult1, localSecureUser, localAccounts, localConnection, bool6, str6, i2);
        }
        else
        {
          a(localReportResult1, 2458);
        }
      }
      catch (Exception localException2)
      {
        localReportResult1.setError(localException2);
        throw localException2;
      }
      finally
      {
        localReportResult1.fini();
        localObject1 = localReportResult1;
      }
      Object localObject3 = localObject1;
      return localObject3;
    }
    catch (DCException localDCException)
    {
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      Object localObject2 = "Exception when attempting to get report data.";
      throw new DCException(313, (String)localObject2, localException1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static IReportResult createCustomSummaryReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    ResultSet localResultSet = null;
    Vector localVector = new Vector();
    Connection localConnection = null;
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool1 = "BAI2".equals(str2);
    boolean bool2 = "QIF".equals(str2);
    boolean bool3 = "QFX".equals(str2);
    boolean bool4 = "QBO".equals(str2);
    boolean bool5 = "IIF".equals(str2);
    int i1 = (bool1) || (bool2) || (bool3) || (bool4) || (bool5) ? 1 : 0;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      localObject2 = new ReportResult(paramReportCriteria.getLocale());
      try
      {
        ((ReportResult)localObject2).init(paramReportCriteria);
        String str3 = localProperties2.getProperty("MAXDISPLAYSIZE");
        a((ReportResult)localObject2, paramReportCriteria, paramHashMap, paramReportCriteria.getLocale(), str3);
      }
      catch (Exception localException2)
      {
        ((ReportResult)localObject2).setError(localException2);
        throw localException2;
      }
      finally
      {
        ((ReportResult)localObject2).fini();
        localObject1 = localObject2;
      }
      Object localObject3 = localObject1;
      return localObject3;
    }
    catch (DCException localDCException)
    {
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      Object localObject2 = "Exception when attempting to get report data.";
      throw new DCException(313, (String)localObject2, localException1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static IReportResult createTransactionDetailReport(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    boolean bool1 = new Boolean(localProperties2.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    Object localObject1 = null;
    PreparedStatement[] arrayOfPreparedStatement1 = new PreparedStatement[1];
    ResultSet localResultSet1 = null;
    PreparedStatement[] arrayOfPreparedStatement2 = new PreparedStatement[1];
    ResultSet localResultSet2 = null;
    Vector localVector = new Vector();
    Connection localConnection = null;
    String str2 = localProperties2.getProperty("FORMAT");
    boolean bool2 = "BAI2".equals(str2);
    boolean bool3 = "QIF".equals(str2);
    boolean bool4 = "QFX".equals(str2);
    boolean bool5 = "QBO".equals(str2);
    boolean bool6 = "IIF".equals(str2);
    int i1 = (bool2) || (bool3) || (bool4) || (bool5) || (bool6) ? 1 : 0;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ReportSortCriteria localReportSortCriteria1 = paramReportCriteria.getSortCriteria();
      localObject2 = paramReportCriteria.getLocale();
      ReportResult localReportResult1 = new ReportResult((Locale)localObject2);
      try
      {
        localProperties2 = (Properties)localProperties2.clone();
        localProperties2.setProperty("SHOWHEADER", "TRUE");
        localProperties2.setProperty("SHOWFOOTER", "TRUE");
        String str3 = null;
        int i2 = 0;
        boolean bool7 = true;
        if (i1 != 0)
        {
          str3 = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.TransID, dcTransactions.TransTypeID, dcTransactions.TransCategoryID, dcTransactions.Description, dcTransactions.ReferenceNumber, dcTransactions.Memo, dcTransactions.Amount, dcTransactions.RunningBalance, dcTransactions.TransTrackingID, dcTransactions.DCTransactionIndex, dcTransactions.PostingDate, dcTransactions.DueDate, dcTransactions.FixedDepRate, dcTransactions.PayeePayor, dcTransactions.PayorNum, dcTransactions.OrigUser, dcTransactions.PONum, dcTransactions.ImmedAvailAmount, dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.DataDate, dcTransactions.InstNumber, dcTransactions.InstBankName, dcTransactions.ValueDateTime, dcTransactions.TransSubTypeID, dcTransactions.ExtendABeanXMLID, dcTransactions.TransDate, subTypeDesc.DESCRIPTION  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_SUBTYPE_DESC subTypeDesc WHERE  dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ";
          localVector.add(paramReportCriteria.getLocale().toString());
        }
        else if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")))
        {
          str3 = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.OrigUser, dcTransactions.Description, dcTransactions.Amount, dcTransactions.PayorNum, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount, dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_SUBTYPE_DESC subTypeDesc WHERE  dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ";
          localVector.add(paramReportCriteria.getLocale().toString());
          i2 = 2451;
          bool7 = true;
        }
        else if (str1.equals("DebitReport"))
        {
          str3 = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.OrigUser, dcTransactions.Description, dcTransactions.Amount, dcTransactions.PayorNum, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount, dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_SUBTYPE_DESC subTypeDesc WHERE  dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ";
          localVector.add(paramReportCriteria.getLocale().toString());
          i2 = 2451;
          bool7 = false;
        }
        else if (str1.equals("AccountHistory"))
        {
          str3 = "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.DataDate, dcTransactions.TransTypeID, dcTransactions.Description, dcTransactions.ReferenceNumber, dcTransactions.Amount, dcTransactions.RunningBalance, dcTransactions.DueDate, dcTransactions.TransSubTypeID, dcTransactions.BankRefNum, dcTransactions.CustRefNum  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_TransTypeDesc transTypeDesc WHERE  transTypeDesc.TRANS_TYPE_ID = dcTransactions.TransTypeID AND transTypeDesc.LOCALE = ? ";
          localVector.add(paramReportCriteria.getLocale().toString());
          i2 = 2457;
        }
        Object localObject8;
        Object localObject9;
        if (i1 != 0)
        {
          localObject4 = localReportSortCriteria1;
          localReportSortCriteria1 = new ReportSortCriteria();
          int i3 = 1;
          a(paramHashMap, "attempt to get BAI export originator ID type");
          localObject5 = (String)paramHashMap.get("ORIGINATOR_ID_TYPE");
          if (localObject5 == null) {
            localObject5 = "1";
          }
          if (((String)localObject5).equals("1")) {
            localReportSortCriteria1.create(i3++, "RoutingNum", true);
          }
          localReportSortCriteria1.create(i3++, "ProcessingDate", true);
          localReportSortCriteria1.create(i3++, "AccountNumber", true);
          if (localObject4 != null)
          {
            ReportSortCriteria localReportSortCriteria2 = new ReportSortCriteria();
            localReportSortCriteria2.ensureCapacity(((ReportSortCriteria)localObject4).size());
            for (int i5 = 0; i5 < ((ReportSortCriteria)localObject4).size(); i5++)
            {
              localObject8 = (ReportSortCriterion)((ReportSortCriteria)localObject4).get(i5);
              for (int i7 = 0; (i7 < localReportSortCriteria2.size()) && (((ReportSortCriterion)localReportSortCriteria2.get(i7)).getOrdinal() < ((ReportSortCriterion)localObject8).getOrdinal()); i7++) {}
              localReportSortCriteria2.add(i7, localObject8);
            }
            for (i5 = 0; i5 < localReportSortCriteria2.size(); i5++)
            {
              localObject8 = (ReportSortCriterion)localReportSortCriteria2.get(i5);
              localObject9 = ((ReportSortCriterion)localObject8).getName();
              if ((!((String)localObject9).equals("ProcessingDate")) && (!((String)localObject9).equals("AccountNumber")) && ((!((String)localObject5).equals("1")) || (!((String)localObject9).equals("RoutingNum")))) {
                localReportSortCriteria1.create(i3++, ((ReportSortCriterion)localObject8).getName(), ((ReportSortCriterion)localObject8).getAsc());
              }
            }
          }
          int i4 = 1;
          a(paramHashMap, "attempt to get BAI export customer account option");
          localObject7 = (String)paramHashMap.get("CUSTOMER_ACCOUNT_OPTION");
          if (localObject7 == null) {
            localObject7 = "1";
          }
          if (((String)localObject7).equals("1")) {
            i4 = 1;
          } else if (((String)localObject7).equals("2")) {
            i4 = 0;
          }
          if (i4 == 0) {
            localReportSortCriteria1.create(i3++, "RoutingNum", true);
          }
        }
        Object localObject4 = a(localReportSortCriteria1, str1, bool7);
        String str4 = a(paramReportCriteria, localVector, paramHashMap, str1);
        if (str4 == null)
        {
          a(localReportResult1, i2);
          localObject5 = localReportResult1;
          localReportResult1.fini();
          localObject1 = localReportResult1;
          jsr 1956;
          return localObject5;
        }
        Object localObject5 = localProperties2.getProperty("MAXDISPLAYSIZE");
        localResultSet1 = a(arrayOfPreparedStatement1, str3, str4, (String)localObject4, localVector, localConnection, (String)localObject5);
        if ((bool4) || (bool5))
        {
          localObject7 = null;
          localObject8 = null;
          localObject9 = new com.ffusion.beans.DateTime((Locale)localObject2);
          if (localProperties1.getProperty("StartDate") == null)
          {
            localObject6 = "dcTransactions.DataDate ASC";
            try
            {
              localResultSet2 = a(arrayOfPreparedStatement2, str3, str4, (String)localObject6, localVector, localConnection, "1");
              if (localResultSet2.next()) {
                localObject7 = a(localResultSet2, 27, (Locale)localObject2);
              }
            }
            finally
            {
              DBUtil.closeResultSet(localResultSet2);
              DBUtil.closeStatement(arrayOfPreparedStatement2[0]);
            }
            if ((localObject7 == null) || (((com.ffusion.beans.DateTime)localObject7).after(localObject9))) {
              localObject7 = localObject9;
            }
            ((com.ffusion.beans.DateTime)localObject7).setFormat("MM/dd/yyyy HH:mm:ss");
            paramReportCriteria.setDisplayValue("StartDate", ((com.ffusion.beans.DateTime)localObject7).toString());
          }
          else
          {
            paramReportCriteria.setDisplayValue("StartDate", localProperties1.getProperty("StartDate"));
          }
          if (localProperties1.getProperty("EndDate") == null)
          {
            localObject6 = "dcTransactions.DataDate DESC";
            try
            {
              localResultSet2 = a(arrayOfPreparedStatement2, str3, str4, (String)localObject6, localVector, localConnection, "1");
              if (localResultSet2.next()) {
                localObject8 = a(localResultSet2, 27, (Locale)localObject2);
              }
            }
            finally
            {
              DBUtil.closeResultSet(localResultSet2);
              DBUtil.closeStatement(arrayOfPreparedStatement2[0]);
            }
            if ((localObject8 == null) || (((com.ffusion.beans.DateTime)localObject8).before(localObject9))) {
              localObject8 = localObject9;
            }
            ((com.ffusion.beans.DateTime)localObject8).setFormat("MM/dd/yyyy HH:mm:ss");
            paramReportCriteria.setDisplayValue("EndDate", ((com.ffusion.beans.DateTime)localObject8).toString());
          }
          else
          {
            paramReportCriteria.setDisplayValue("EndDate", localProperties1.getProperty("EndDate"));
          }
        }
        localReportResult1.init(paramReportCriteria);
        Object localObject6 = new ReportDataDimensions((Locale)localObject2);
        ((ReportDataDimensions)localObject6).setNumColumns(0);
        ((ReportDataDimensions)localObject6).setNumRows(0);
        localReportResult1.setDataDimensions((ReportDataDimensions)localObject6);
        a(paramHashMap, "attempt to get account beans needed to determine account nicknames");
        Object localObject7 = (Accounts)paramHashMap.get("AccountsForReport");
        if (localObject7 == null) {
          localObject7 = new Accounts();
        }
        int i8;
        Object localObject13;
        Object localObject14;
        if (i1 != 0)
        {
          int i6 = 0;
          i8 = 0;
          Object localObject11 = null;
          localObject13 = null;
          localObject14 = null;
          ReportResult localReportResult2 = new ReportResult((Locale)localObject2);
          localReportResult1.addSubReport(localReportResult2);
          while (localResultSet1.next())
          {
            ReportRow localReportRow = new ReportRow((Locale)localObject2);
            ReportDataItems localReportDataItems = new ReportDataItems((Locale)localObject2);
            String str6 = localResultSet1.getString(1);
            String str7 = localResultSet1.getString(2);
            String str8 = localResultSet1.getString(3);
            if ((localObject11 == null) && (localObject13 == null) && (localObject14 == null))
            {
              localObject11 = str6;
              localObject13 = str7;
              localObject14 = str8;
            }
            else if ((!str6.equals(localObject11)) || (!str7.equals(localObject13)) || (!str8.equals(localObject14)))
            {
              localObject11 = str6;
              localObject13 = str7;
              localObject14 = str8;
              localReportResult2 = new ReportResult((Locale)localObject2);
              localReportResult1.addSubReport(localReportResult2);
              i6 = 0;
            }
            String str9 = null;
            try
            {
              str9 = AccountUtil.buildAccountDisplayTextForExport(str2, str6, (Locale)localObject2);
            }
            catch (UtilException localUtilException)
            {
              str9 = str6;
            }
            localReportDataItems.add().setData(str9);
            localReportDataItems.add().setData(str7);
            localReportDataItems.add().setData(str8);
            localReportDataItems.add().setData(localResultSet1.getString(4));
            localReportDataItems.add().setData(localResultSet1.getString(5));
            localReportDataItems.add().setData(new Integer(localResultSet1.getInt(6)));
            localReportDataItems.add().setData(new Integer(localResultSet1.getInt(7)));
            String str10 = localResultSet1.getString(8);
            if ((str10 != null) && (str10.trim().length() == 0)) {
              str10 = null;
            }
            localReportDataItems.add().setData(str10);
            localReportDataItems.add().setData(localResultSet1.getString(9));
            localReportDataItems.add().setData(localResultSet1.getString(10));
            localReportDataItems.add().setData(jdField_if(localResultSet1, 11, (Locale)localObject2));
            localReportDataItems.add().setData(jdField_if(localResultSet1, 12, (Locale)localObject2));
            localReportDataItems.add().setData(localResultSet1.getString(13));
            localReportDataItems.add().setData(new Long(localResultSet1.getLong(14)));
            localReportDataItems.add().setData(a(localResultSet1, 15, (Locale)localObject2));
            localReportDataItems.add().setData(a(localResultSet1, 16, (Locale)localObject2));
            localReportDataItems.add().setData(new Float(localResultSet1.getFloat(17)));
            localReportDataItems.add().setData(localResultSet1.getString(18));
            localReportDataItems.add().setData(localResultSet1.getString(19));
            localReportDataItems.add().setData(localResultSet1.getString(20));
            localReportDataItems.add().setData(localResultSet1.getString(21));
            localReportDataItems.add().setData(jdField_if(localResultSet1, 22, (Locale)localObject2));
            localReportDataItems.add().setData(jdField_if(localResultSet1, 23, (Locale)localObject2));
            localReportDataItems.add().setData(jdField_if(localResultSet1, 24, (Locale)localObject2));
            jdField_if(localReportDataItems, DCAdapter.unPadRefNum(localResultSet1.getString(25), DCAdapter.getBankReferenceNumberType()));
            jdField_if(localReportDataItems, DCAdapter.unPadRefNum(localResultSet1.getString(26), DCAdapter.getCustomerReferenceNumberType()));
            localReportDataItems.add().setData(a(localResultSet1, 27, (Locale)localObject2));
            localReportDataItems.add().setData(localResultSet1.getString(28));
            localReportDataItems.add().setData(localResultSet1.getString(29));
            localReportDataItems.add().setData(a(localResultSet1, 30, (Locale)localObject2));
            localReportDataItems.add().setData(new Integer(localResultSet1.getInt(31)));
            localReportDataItems.add().setData(a(localResultSet1, 33, (Locale)localObject2));
            String str11 = (String)localProperties1.get("DataClassification");
            if (str11 == null) {
              str11 = "P";
            }
            localReportDataItems.add().setData(str11);
            Transactions localTransactions = new Transactions((Locale)localObject2);
            Transaction localTransaction = localTransactions.create();
            DCUtil.fillExtendABean(localTransaction, localResultSet1, 32);
            if (i6 == 0)
            {
              i6 = 1;
              localHashMap = localTransaction.getHash();
              localObject15 = localHashMap.keySet();
              localIterator = ((Set)localObject15).iterator();
              for (i8 = 0; localIterator.hasNext(); i8++) {
                localIterator.next();
              }
              ReportDataDimensions localReportDataDimensions = new ReportDataDimensions((Locale)localObject2);
              localReportDataDimensions.setNumColumns(jdField_try.length + i8);
              localReportDataDimensions.setNumRows(0);
              localReportResult2.setDataDimensions(localReportDataDimensions);
              a.a(localReportResult2, (Locale)localObject2, jdField_try);
              for (int i11 = 0; i11 < i8; i11++)
              {
                ReportColumn localReportColumn = new ReportColumn((Locale)localObject2);
                ArrayList localArrayList = new ArrayList();
                localArrayList.add("ExtendABean" + (i11 + 1));
                localReportColumn.setLabels(localArrayList);
                localReportColumn.setDataType("java.lang.String");
                localReportColumn.setWidthAsPercent(1);
                localReportResult2.addColumn(localReportColumn);
              }
            }
            HashMap localHashMap = localTransaction.getHash();
            Object localObject15 = new ArrayList();
            ((ArrayList)localObject15).addAll(localHashMap.keySet());
            Qsort.sortStrings((ArrayList)localObject15, 1);
            Iterator localIterator = ((ArrayList)localObject15).iterator();
            for (int i10 = 0; i10 < i8; i10++) {
              if (localIterator.hasNext()) {
                localReportDataItems.add().setData(localHashMap.get(localIterator.next()));
              } else {
                localReportDataItems.add(null);
              }
            }
            localReportRow.setDataItems(localReportDataItems);
            localReportResult2.addRow(localReportRow);
          }
        }
        else
        {
          String str5 = localProperties1.getProperty("TransactionStatus");
          if (str5 != null) {
            str5 = str5.trim();
          }
          i8 = !"Completed".equals(str5) ? 1 : 0;
          if (!localResultSet1.first()) {
            i8 = 1;
          } else {
            localResultSet1.beforeFirst();
          }
          if (i8 != 0)
          {
            a(localReportResult1, i2);
          }
          else
          {
            boolean bool8 = false;
            if (!localReportSortCriteria1.isEmpty())
            {
              localReportSortCriteria1.setSortedBy("ORDINAL");
              localObject13 = ((ReportSortCriterion)localReportSortCriteria1.get(0)).getName();
              bool8 = "AccountNumber".equals(localObject13);
            }
            int i9 = 0;
            if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport"))) {
              i9 = a(localResultSet1, localReportResult1, (Accounts)localObject7, localConnection, bool8, bool8 ? I : a7, bool1, paramReportCriteria);
            } else if (str1.equals("AccountHistory")) {
              i9 = a(localResultSet1, localReportResult1, (Accounts)localObject7, localConnection, paramReportCriteria);
            }
            if ((localObject5 != null) && (i9 >= a(localProperties2)))
            {
              localObject14 = new HashMap();
              ((HashMap)localObject14).put("TRUNCATED", Integer.toString(i9));
              localReportResult1.setProperties((HashMap)localObject14);
            }
          }
        }
      }
      catch (Exception localException2)
      {
        localReportResult1.setError(localException2);
        throw localException2;
      }
      finally
      {
        localReportResult1.fini();
        localObject1 = localReportResult1;
      }
      Object localObject3 = localObject1;
      return localObject3;
    }
    catch (DCException localDCException)
    {
      localDCException.printStackTrace(System.err);
      throw localDCException;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace(System.err);
      Object localObject2 = "Exception when attempting to get report data.";
      throw new DCException(313, (String)localObject2, localException1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet1);
      DBUtil.closeStatement(arrayOfPreparedStatement1[0]);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  public static IReportResult createBalanceReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    paramReportCriteria.setHiddenSearchCriterion("DisplayZBATransactions", true);
    paramReportCriteria.setHiddenSearchCriterion("SupressSubAccounts", true);
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    String str1 = (String)localProperties2.get("Accounts");
    Accounts localAccounts1 = null;
    Object localObject3;
    if ((str1 == null) || (accountCriterionHasAllAccounts(str1)))
    {
      str1 = "AllAccounts";
      localAccounts1 = (Accounts)paramHashMap.get("Accounts");
    }
    else
    {
      localAccounts1 = new Accounts(paramSecureUser.getLocale());
      localAccounts2 = jdField_new(str1);
      localObject1 = (Accounts)paramHashMap.get("AccountsForReport");
      for (int i1 = 0; i1 < localAccounts2.size(); i1++)
      {
        Account localAccount = (Account)localAccounts2.get(i1);
        String str2 = localAccount.getID();
        localObject3 = localAccount.getBankID();
        String str3 = localAccount.getRoutingNum();
        localObject4 = ((Accounts)localObject1).getByIDAndBankIDAndRoutingNum(str2, (String)localObject3, str3);
        if (localObject4 != null) {
          localAccounts1.add(localObject4);
        }
      }
    }
    Accounts localAccounts2 = new Accounts(localAccounts1.getLocale());
    Object localObject1 = (Business)paramHashMap.get("BusinessForReport");
    if (localObject1 == null) {
      throw new DCException("The Business object was not found in extra.");
    }
    Accounts localAccounts3 = null;
    try
    {
      localAccounts3 = TreasuryDirect.filterNonSubAccounts(null, (Business)localObject1, localAccounts1, paramHashMap);
      if ((localAccounts3 != null) && (localAccounts3.size() != 0))
      {
        paramReportCriteria.setHiddenSearchCriterion("DisplayZBATransactions", false);
        paramReportCriteria.setHiddenSearchCriterion("SupressSubAccounts", false);
      }
    }
    catch (CSILException localCSILException)
    {
      throw new DCException("Unable to retrieve a list of sub-accounts for the given accounts.", localCSILException);
    }
    boolean bool1 = new Boolean(paramReportCriteria.getSearchCriteria().getProperty("SupressSubAccounts")).booleanValue();
    if (bool1) {
      for (int i2 = 0; i2 < localAccounts1.size(); i2++)
      {
        localObject3 = (Account)localAccounts1.get(i2);
        if ((((Account)localObject3).isMaster()) || (!localAccounts3.contains(localObject3))) {
          localAccounts2.add(localObject3);
        }
      }
    } else {
      localAccounts2 = localAccounts1;
    }
    if (localAccounts2.size() == 0)
    {
      localObject2 = new ReportResult(localAccounts1.getLocale());
      ((ReportResult)localObject2).init(paramReportCriteria);
      localObject3 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      int i3 = -1;
      if (((String)localObject3).equals("BalanceDetailOnlyReport")) {
        i3 = 2455;
      } else if (((String)localObject3).equals("BalanceSummaryReport")) {
        i3 = 2454;
      } else if (((String)localObject3).equals("BalanceDetailReport")) {
        i3 = 2455;
      }
      a((ReportResult)localObject2, i3);
      ((ReportResult)localObject2).fini();
      return localObject2;
    }
    Object localObject2 = a(localAccounts2);
    boolean bool2 = "BAI2".equals(localProperties1.getProperty("FORMAT"));
    if (bool2) {
      return a(paramSecureUser, (ArrayList)localObject2, paramReportCriteria, paramBoolean1, paramBoolean2, paramHashMap);
    }
    HashMap localHashMap = (HashMap)paramHashMap.get("StartDates");
    if (localHashMap == null)
    {
      localObject4 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log((String)localObject4);
      throw new DCException(320, (String)localObject4);
    }
    Object localObject4 = (HashMap)paramHashMap.get("EndDates");
    if (localObject4 == null)
    {
      localObject5 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log((String)localObject5);
      throw new DCException(321, (String)localObject5);
    }
    Object localObject5 = paramReportCriteria.getSortCriteria();
    if (localObject5 == null) {
      localObject5 = new ReportSortCriteria();
    } else {
      ((ReportSortCriteria)localObject5).setSortedBy("ORDINAL");
    }
    Object localObject6;
    if (((ReportSortCriteria)localObject5).size() > 0)
    {
      i4 = 0;
      localObject6 = ((ReportSortCriterion)((ReportSortCriteria)localObject5).get(i4++)).getName();
      if ((((String)localObject6).equals("AccountNumber")) && (((ReportSortCriteria)localObject5).size() > i4))
      {
        localObject6 = ((ReportSortCriterion)((ReportSortCriteria)localObject5).get(i4++)).getName();
        if (((String)localObject6).equals("Location")) {
          localObject6 = ((ReportSortCriterion)((ReportSortCriteria)localObject5).get(i4++)).getName();
        }
        boolean bool3;
        if ((((String)localObject6).equals("CreditDebit")) || (((String)localObject6).equals("DebitCredit")))
        {
          bool3 = ((String)localObject6).equals("CreditDebit");
          return a(paramSecureUser, (ArrayList)localObject2, paramReportCriteria, paramBoolean1, paramBoolean2, true, bool3, paramHashMap);
        }
        if ((((String)localObject6).equals("ProcessingDate")) && (((ReportSortCriteria)localObject5).size() > i4))
        {
          localObject6 = ((ReportSortCriterion)((ReportSortCriteria)localObject5).get(i4++)).getName();
          if ((((String)localObject6).equals("CreditDebit")) || (((String)localObject6).equals("DebitCredit")))
          {
            bool3 = ((String)localObject6).equals("CreditDebit");
            return jdField_if(paramSecureUser, (ArrayList)localObject2, paramReportCriteria, paramBoolean1, paramBoolean2, true, bool3, paramHashMap);
          }
          return jdField_if(paramSecureUser, (ArrayList)localObject2, paramReportCriteria, paramBoolean1, paramBoolean2, false, true, paramHashMap);
        }
      }
    }
    int i4 = 1;
    if ((localObject5 != null) || (((ReportSortCriteria)localObject5).size() != 0))
    {
      ((ReportSortCriteria)localObject5).setSortedBy("ORDINAL");
      localObject6 = ((ReportSortCriteria)localObject5).iterator();
      while (((Iterator)localObject6).hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)((Iterator)localObject6).next();
        String str4 = localReportSortCriterion.getName();
        if (str4.equals("AccountNumber"))
        {
          i4 = 1;
          break;
        }
        if (str4.equals("ProcessingDate"))
        {
          i4 = 0;
          break;
        }
      }
    }
    if (i4 != 0) {
      return jdField_if(paramSecureUser, (ArrayList)localObject2, paramReportCriteria, paramBoolean1, paramBoolean2, true, true, paramHashMap);
    }
    return jdField_if(paramSecureUser, (ArrayList)localObject2, paramReportCriteria, paramBoolean1, paramBoolean2, paramHashMap);
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ArrayList paramArrayList, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    Locale localLocale = paramSecureUser.getLocale();
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str1 = (String)localProperties.get("DataClassification");
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
    ReportResult localReportResult1 = new ReportResult(localLocale);
    localReportResult1.init(paramReportCriteria);
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ArrayList localArrayList1 = a(paramArrayList, localHashMap1, localHashMap2);
      Calendar localCalendar1 = (Calendar)localArrayList1.get(0);
      Calendar localCalendar2 = (Calendar)localArrayList1.get(1);
      if (localCalendar2 == null)
      {
        localCalendar2 = Calendar.getInstance();
        localCalendar2.set(11, 0);
        localCalendar2.set(12, 0);
        localCalendar2.set(13, 0);
        localCalendar2.set(14, 0);
        localCalendar2.add(6, 1);
        localCalendar2.add(14, -1);
      }
      if (localCalendar1 == null)
      {
        localCalendar1 = (Calendar)localCalendar2.clone();
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        localCalendar1.add(6, -a(paramReportCriteria));
      }
      com.ffusion.beans.DateTime localDateTime1 = new com.ffusion.beans.DateTime(localCalendar1, localLocale);
      com.ffusion.beans.DateTime localDateTime2 = new com.ffusion.beans.DateTime(localCalendar2, localLocale);
      com.ffusion.beans.DateTime localDateTime3 = new com.ffusion.beans.DateTime((Calendar)localDateTime1.clone(), localLocale);
      while ((localDateTime3.before(localDateTime2)) || (localDateTime3.isSameDayInYearAs(localDateTime2)))
      {
        for (int i1 = 0; i1 < paramArrayList.size(); i1++)
        {
          Accounts localAccounts = (Accounts)paramArrayList.get(i1);
          String str2 = ((Account)localAccounts.get(0)).getRoutingNum();
          Calendar localCalendar3 = (Calendar)localHashMap1.get(str2);
          Calendar localCalendar4 = (Calendar)localHashMap2.get(str2);
          com.ffusion.beans.DateTime localDateTime4 = null;
          com.ffusion.beans.DateTime localDateTime5 = null;
          if (localCalendar4 != null) {
            localDateTime5 = new com.ffusion.beans.DateTime(localCalendar4, localLocale);
          }
          if (localCalendar3 != null) {
            localDateTime4 = new com.ffusion.beans.DateTime(localCalendar3, localLocale);
          }
          if (((localDateTime4 == null) || (localDateTime4.isSameDayInYearAs(localDateTime3)) || (!localDateTime3.before(localDateTime4))) && ((localDateTime5 == null) || (localDateTime5.isSameDayInYearAs(localDateTime3)) || (!localDateTime3.after(localDateTime5))))
          {
            Object localObject1 = null;
            Object localObject2 = null;
            if ((localDateTime4 == null) || (!localDateTime3.isSameDayInYearAs(localDateTime4)))
            {
              localObject1 = (Calendar)localDateTime3.clone();
              ((Calendar)localObject1).set(11, 0);
              ((Calendar)localObject1).set(12, 0);
              ((Calendar)localObject1).set(13, 0);
              ((Calendar)localObject1).set(14, 0);
            }
            else
            {
              localObject1 = localDateTime4;
            }
            if ((localDateTime5 == null) || (!localDateTime3.isSameDayInYearAs(localDateTime5)))
            {
              localObject2 = (Calendar)localDateTime3.clone();
              ((Calendar)localObject2).add(6, 1);
              ((Calendar)localObject2).add(14, -1);
            }
            else
            {
              localObject2 = localDateTime5;
            }
            for (int i2 = 0; i2 < localAccounts.size(); i2++)
            {
              Account localAccount = (Account)localAccounts.get(i2);
              ReportResult localReportResult2 = null;
              boolean bool = true;
              if (paramBoolean1)
              {
                HashMap localHashMap3 = jdField_if(localAccount, localProperties, (Calendar)localObject1, (Calendar)localObject2, localConnection, paramHashMap);
                if ((!paramBoolean2) && (localHashMap3.size() == 0)) {
                  continue;
                }
                if (localHashMap3.size() != 0)
                {
                  bool = false;
                  localReportResult2 = new ReportResult(localLocale);
                  localReportResult1.addSubReport(localReportResult2);
                  jdField_if(localReportResult2, "AccountSummary");
                  a(localReportResult2, R.length + localHashMap3.size(), -1);
                  a.a(localReportResult2, localReportResult2.getLocale(), R);
                  Set localSet = localHashMap3.keySet();
                  ArrayList localArrayList2 = new ArrayList(localSet);
                  Qsort.sortStrings(localArrayList2, 1);
                  Iterator localIterator1 = localArrayList2.iterator();
                  Object localObject5;
                  while (localIterator1.hasNext())
                  {
                    localObject3 = (String)localIterator1.next();
                    localObject4 = localHashMap3.get(localObject3);
                    String str3 = null;
                    if ((localObject4 instanceof SummaryValue)) {
                      str3 = "com.ffusion.beans.Currency";
                    } else if ((localObject4 instanceof String)) {
                      str3 = "java.lang.String";
                    } else if ((localObject4 instanceof Float)) {
                      str3 = "java.lang.Float";
                    } else if ((localObject4 instanceof Integer)) {
                      str3 = "java.lang.Integer";
                    } else if ((localObject4 instanceof BigDecimal)) {
                      str3 = "java.math.BigDecimal";
                    } else if ((localObject4 instanceof Currency)) {
                      str3 = "com.ffusion.beans.Currency";
                    } else if ((localObject4 instanceof com.ffusion.beans.DateTime)) {
                      str3 = "com.ffusion.util.beans.DateTime";
                    }
                    if (((localObject4 instanceof SummaryValue)) && (((SummaryValue)localObject4)._itemCount != -1))
                    {
                      localObject5 = new a[] { new a("BAI_" + (String)localObject3, str3, 25), new a("ITEMCOUNT_" + (String)localObject3, "java.lang.Integer", 25) };
                      a.a(localReportResult2, localReportResult2.getLocale(), (a[])localObject5);
                    }
                    else
                    {
                      localObject5 = new a[] { new a("BAI_" + (String)localObject3, str3, 25) };
                      a.a(localReportResult2, localReportResult2.getLocale(), (a[])localObject5);
                    }
                  }
                  Object localObject3 = new ReportDataItems(localLocale);
                  ((ReportDataItems)localObject3).add().setData(new com.ffusion.beans.DateTime((Calendar)localObject1, localLocale));
                  Object localObject4 = null;
                  try
                  {
                    localObject4 = AccountUtil.buildAccountDisplayTextForExport("BAI2", localAccount.getID(), localAccount.getLocale());
                  }
                  catch (UtilException localUtilException)
                  {
                    localObject4 = localAccount.getID();
                  }
                  ((ReportDataItems)localObject3).add().setData(localObject4);
                  ((ReportDataItems)localObject3).add().setData(localAccount.getBankID());
                  ((ReportDataItems)localObject3).add().setData(localAccount.getRoutingNum());
                  ((ReportDataItems)localObject3).add().setData(localAccount.getCurrencyCode());
                  ((ReportDataItems)localObject3).add().setData(str1);
                  Iterator localIterator2 = localArrayList2.iterator();
                  while (localIterator2.hasNext())
                  {
                    localObject5 = (String)localIterator2.next();
                    Object localObject6 = localHashMap3.get(localObject5);
                    if ((localObject6 instanceof SummaryValue))
                    {
                      ((ReportDataItems)localObject3).add().setData(((SummaryValue)localObject6)._amount);
                      if (((SummaryValue)localObject6)._itemCount != -1) {
                        ((ReportDataItems)localObject3).add().setData(new Integer(((SummaryValue)localObject6)._itemCount));
                      }
                    }
                    else
                    {
                      ((ReportDataItems)localObject3).add().setData(localObject6);
                    }
                  }
                  a(localReportResult2, (ReportDataItems)localObject3, -1);
                }
              }
              if (paramBoolean2) {
                fillBAIBalanceDetailReport(localReportResult1, localAccount, bool, new com.ffusion.beans.DateTime((Calendar)localObject1, localLocale), new com.ffusion.beans.DateTime((Calendar)localObject2, localLocale), paramReportCriteria, localConnection, paramHashMap);
              }
            }
          }
        }
        localDateTime3.add(6, 1);
      }
    }
    catch (DCException localDCException)
    {
      DebugLog.throwing("Unable to generate report.", localDCException);
      throw localDCException;
    }
    finally
    {
      localReportResult1.fini();
      DCAdapter.releaseDBConnection(localConnection);
    }
    return localReportResult1;
  }
  
  private static ResultSet a(int paramInt, PreparedStatement[] paramArrayOfPreparedStatement, Account paramAccount, com.ffusion.beans.DateTime paramDateTime1, com.ffusion.beans.DateTime paramDateTime2, ReportCriteria paramReportCriteria, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = paramAccount.getID();
    String str2 = paramAccount.getBankID();
    String str3 = paramAccount.getRoutingNum();
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str4 = localProperties2.getProperty("REPORTTYPE");
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    Properties localProperties3 = (Properties)localProperties1.clone();
    localProperties3.put("Accounts", a(str1, str2, str3));
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap(paramHashMap);
    localHashMap1.put(str3, paramDateTime1);
    localHashMap2.put("StartDates", localHashMap1);
    localHashMap1 = new HashMap();
    localHashMap1.put(str3, paramDateTime2);
    localHashMap2.put("EndDates", localHashMap1);
    localHashMap2.put("BusinessForReport", paramHashMap.get("BusinessForReport"));
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.setSearchCriteria(localProperties3);
    Vector localVector = new Vector();
    localVector.add(paramReportCriteria.getLocale().toString());
    localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
    String str5 = a(localReportCriteria, localVector, localHashMap2, "TransactionDetail");
    paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
    if (str5 == null) {
      return null;
    }
    String str6 = a(localReportSortCriteria, str4, paramInt != 2);
    if (paramInt == 1) {
      str5 = str5 + " AND (dcTransactions.Amount >= 0 OR dcTransactions.Amount IS NULL) ";
    } else if (paramInt == 2) {
      str5 = str5 + " AND (dcTransactions.Amount < 0 OR dcTransactions.Amount IS NULL) ";
    } else if (paramInt == 3) {
      str5 = str5;
    }
    ResultSet localResultSet = a(paramArrayOfPreparedStatement, "SELECT dcAccount.AccountID, dcTransactions.PayeePayor, dcTransactions.Description, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.Amount, dcTransactions.DataDate, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount,dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION FROM DC_Transactions dcTransactions, DC_Account dcAccount, DC_SUBTYPE_DESC subTypeDesc WHERE dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ", str5, str6, localVector, paramConnection);
    return localResultSet;
  }
  
  private static void a(ArrayList paramArrayList1, ArrayList paramArrayList2, ReportResult paramReportResult, ReportCriteria paramReportCriteria, com.ffusion.beans.DateTime paramDateTime, Account paramAccount)
    throws RptException
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    Locale localLocale = paramReportResult.getLocale();
    String str1 = paramReportCriteria.getReportOptions() == null ? "" : paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    for (int i1 = 0; i1 < paramArrayList2.size(); i1++)
    {
      ReportResult localReportResult = (ReportResult)paramArrayList2.get(i1);
      if (localReportResult == null)
      {
        int i2 = -1;
        if (paramArrayList1.size() > i1) {
          i2 = ((Integer)paramArrayList1.get(i1)).intValue();
        }
        localReportResult = new ReportResult(localLocale);
        paramArrayList2.set(i1, localReportResult);
        if (i1 == 0) {
          paramReportResult.addSubReport(localReportResult);
        } else {
          ((ReportResult)paramArrayList2.get(i1 - 1)).addSubReport(localReportResult);
        }
        a(localReportResult, 0, -1);
        if (!bool)
        {
          String str2 = null;
          if (i2 == -1)
          {
            str2 = null;
          }
          else
          {
            String str3;
            Object localObject1;
            Object localObject2;
            if (i2 == 700)
            {
              str3 = null;
              localObject1 = null;
              localObject2 = new ArrayList();
              try
              {
                localObject1 = AccountUtil.buildAccountDisplayText(paramAccount.getID(), paramAccount.getLocale());
              }
              catch (UtilException localUtilException)
              {
                DebugLog.throwing("Error while constructing account display string.", localUtilException);
                localObject1 = paramAccount.getID();
              }
              ((ArrayList)localObject2).add(localObject1);
              int i3 = (paramAccount.getNickName() != null) && (paramAccount.getNickName().length() > 0) ? 1 : 0;
              int i4 = paramAccount.getCurrencyCode() != null ? 1 : 0;
              if ((i3 != 0) && (i4 != 0)) {
                str3 = "Balance_Reports_Account_Header_With_Nickname_And_Currency_Code_Format";
              } else if (i3 != 0) {
                str3 = "Balance_Reports_Account_Header_With_Nickname_Format";
              } else if (i4 != 0) {
                str3 = "Balance_Reports_Account_Header_With_Currency_Code_Format";
              } else {
                str3 = "Balance_Reports_Account_Header_With_No_Other_Information_Format";
              }
              if (i3 != 0) {
                ((ArrayList)localObject2).add(paramAccount.getNickName());
              }
              if (i4 != 0) {
                ((ArrayList)localObject2).add(paramAccount.getCurrencyCode());
              }
              str2 = (String)new LocalizableString("com.ffusion.beans.reporting.reports", str3, ((ArrayList)localObject2).toArray()).localize(localLocale);
            }
            else if ((i2 == 701) && (!str1.equals("BalanceDetailOnlyReport")))
            {
              str3 = localProperties.getProperty("DATEFORMAT");
              localObject1 = new Object[1];
              if (paramDateTime != null)
              {
                localObject2 = new com.ffusion.beans.DateTime(paramDateTime, localLocale);
                String str4 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
                if (str4 != null) {
                  ((com.ffusion.beans.DateTime)localObject2).setFormat(str4);
                }
                localObject1[0] = ((com.ffusion.beans.DateTime)localObject2).toString();
              }
              else
              {
                localObject2 = (String)paramReportCriteria.getSearchCriteria().get("Date Range Type");
                if ("Relative".equals(localObject2)) {
                  localObject1[0] = paramReportCriteria.getDisplayValue("Date Range");
                } else {
                  localObject1[0] = a(paramReportCriteria, str3, localLocale);
                }
              }
              str2 = (String)new LocalizableString("com.ffusion.beans.reporting.reports", "Balance_Reports_Date_Header_Format", (Object[])localObject1).localize(localLocale);
            }
            else if (i2 == 697)
            {
              str3 = null;
              localObject1 = new ArrayList();
              if (paramAccount.getBankName() != null)
              {
                str3 = "Balance_Reports_Bank_Header_With_Name_Format";
                ((ArrayList)localObject1).add(paramAccount.getBankName());
              }
              else
              {
                str3 = "Balance_Reports_Bank_Header_Routing_Number_Only_Format";
              }
              ((ArrayList)localObject1).add(paramAccount.getRoutingNum());
              str2 = (String)new LocalizableString("com.ffusion.beans.reporting.reports", str3, ((ArrayList)localObject1).toArray()).localize(localLocale);
            }
            else if (i2 == 740)
            {
              str2 = ReportConsts.getColumnName(280, localLocale);
            }
            else if (i2 == 720)
            {
              str2 = ReportConsts.getColumnName(281, localLocale);
            }
          }
          if (str2 != null) {
            jdField_if(localReportResult, str2);
          }
        }
      }
    }
  }
  
  private static ArrayList a(Account paramAccount, HashMap paramHashMap, ReportCriteria paramReportCriteria)
  {
    Locale localLocale = paramAccount.getLocale();
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
    Calendar localCalendar1 = (Calendar)localHashMap1.get(paramAccount.getRoutingNum());
    Calendar localCalendar2 = (Calendar)localHashMap2.get(paramAccount.getRoutingNum());
    if (localCalendar2 == null)
    {
      localCalendar2 = Calendar.getInstance();
      localCalendar2.set(11, 0);
      localCalendar2.set(12, 0);
      localCalendar2.set(13, 0);
      localCalendar2.set(14, 0);
      localCalendar2.add(6, 1);
      localCalendar2.add(14, -1);
    }
    if (localCalendar1 == null)
    {
      localCalendar1 = (Calendar)localCalendar2.clone();
      localCalendar1.set(11, 0);
      localCalendar1.set(12, 0);
      localCalendar1.set(13, 0);
      localCalendar1.set(14, 0);
      localCalendar1.add(6, -a(paramReportCriteria));
    }
    com.ffusion.beans.DateTime localDateTime1 = new com.ffusion.beans.DateTime(localCalendar1, localLocale);
    com.ffusion.beans.DateTime localDateTime2 = new com.ffusion.beans.DateTime(localCalendar2, localLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localDateTime1);
    localArrayList.add(localDateTime2);
    return localArrayList;
  }
  
  private static ArrayList a(ArrayList paramArrayList, Locale paramLocale, HashMap paramHashMap, ReportCriteria paramReportCriteria)
  {
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
    ArrayList localArrayList1 = a(paramArrayList, localHashMap1, localHashMap2);
    Calendar localCalendar1 = (Calendar)localArrayList1.get(0);
    Calendar localCalendar2 = (Calendar)localArrayList1.get(1);
    if (localCalendar2 == null)
    {
      localCalendar2 = Calendar.getInstance();
      localCalendar2.set(11, 0);
      localCalendar2.set(12, 0);
      localCalendar2.set(13, 0);
      localCalendar2.set(14, 0);
      localCalendar2.add(6, 1);
      localCalendar2.add(14, -1);
    }
    if (localCalendar1 == null)
    {
      localCalendar1 = (Calendar)localCalendar2.clone();
      localCalendar1.set(11, 0);
      localCalendar1.set(12, 0);
      localCalendar1.set(13, 0);
      localCalendar1.set(14, 0);
      localCalendar1.add(6, -a(paramReportCriteria));
    }
    com.ffusion.beans.DateTime localDateTime1 = new com.ffusion.beans.DateTime(localCalendar1, paramLocale);
    com.ffusion.beans.DateTime localDateTime2 = new com.ffusion.beans.DateTime(localCalendar2, paramLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(localDateTime1);
    localArrayList2.add(localDateTime2);
    return localArrayList2;
  }
  
  private static boolean a(com.ffusion.beans.DateTime paramDateTime, String paramString, HashMap paramHashMap)
  {
    Locale localLocale = paramDateTime.getLocale();
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
    Calendar localCalendar1 = (Calendar)localHashMap1.get(paramString);
    Calendar localCalendar2 = (Calendar)localHashMap2.get(paramString);
    com.ffusion.beans.DateTime localDateTime1 = null;
    com.ffusion.beans.DateTime localDateTime2 = null;
    if (localCalendar2 != null) {
      localDateTime2 = new com.ffusion.beans.DateTime(localCalendar2, localLocale);
    }
    if (localCalendar1 != null) {
      localDateTime1 = new com.ffusion.beans.DateTime(localCalendar1, localLocale);
    }
    boolean bool = true;
    if ((localDateTime1 != null) && (!localDateTime1.isSameDayInYearAs(paramDateTime)) && (paramDateTime.before(localDateTime1))) {
      bool = false;
    }
    if ((localDateTime2 != null) && (!localDateTime2.isSameDayInYearAs(paramDateTime)) && (paramDateTime.after(localDateTime2))) {
      bool = false;
    }
    return bool;
  }
  
  private static ArrayList jdField_if(com.ffusion.beans.DateTime paramDateTime, String paramString, HashMap paramHashMap)
  {
    Locale localLocale = paramDateTime.getLocale();
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
    Calendar localCalendar1 = (Calendar)localHashMap1.get(paramString);
    Calendar localCalendar2 = (Calendar)localHashMap2.get(paramString);
    com.ffusion.beans.DateTime localDateTime1 = null;
    com.ffusion.beans.DateTime localDateTime2 = null;
    if (localCalendar2 != null) {
      localDateTime2 = new com.ffusion.beans.DateTime(localCalendar2, localLocale);
    }
    if (localCalendar1 != null) {
      localDateTime1 = new com.ffusion.beans.DateTime(localCalendar1, localLocale);
    }
    Object localObject1 = null;
    Object localObject2 = null;
    if ((localDateTime1 == null) || (!paramDateTime.isSameDayInYearAs(localDateTime1)))
    {
      localObject1 = (Calendar)paramDateTime.clone();
      ((Calendar)localObject1).set(11, 0);
      ((Calendar)localObject1).set(12, 0);
      ((Calendar)localObject1).set(13, 0);
      ((Calendar)localObject1).set(14, 0);
    }
    else
    {
      localObject1 = localDateTime1;
    }
    if ((localDateTime2 == null) || (!paramDateTime.isSameDayInYearAs(localDateTime2)))
    {
      localObject2 = (Calendar)paramDateTime.clone();
      ((Calendar)localObject2).add(6, 1);
      ((Calendar)localObject2).add(14, -1);
    }
    else
    {
      localObject2 = localDateTime2;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new com.ffusion.beans.DateTime((Calendar)localObject1, localLocale));
    localArrayList.add(new com.ffusion.beans.DateTime((Calendar)localObject2, localLocale));
    return localArrayList;
  }
  
  private static ReportSortCriteria a(ReportSortCriteria paramReportSortCriteria, ArrayList paramArrayList)
  {
    ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
    if (paramReportSortCriteria != null) {
      for (int i1 = 0; i1 < paramReportSortCriteria.size(); i1++)
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(i1);
        String str = localReportSortCriterion.getName();
        if (!paramArrayList.contains(str)) {
          localReportSortCriteria.create(localReportSortCriteria.size() + 1, str, localReportSortCriterion.getAsc());
        }
      }
    }
    return localReportSortCriteria;
  }
  
  private static IReportResult jdField_if(SecureUser paramSecureUser, ArrayList paramArrayList, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws RptException, DCException, CSILException
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    Locale localLocale = paramSecureUser.getLocale();
    int i1 = a(paramReportCriteria.getReportOptions());
    int i2 = 0;
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    HashMap localHashMap2 = (HashMap)paramHashMap.get("EndDates");
    String str1 = ReportConsts.getColumnName(700, localLocale);
    String str2 = ReportConsts.getColumnName(701, localLocale);
    String str3 = ReportConsts.getColumnName(697, localLocale);
    String str4 = ReportConsts.getColumnName(699, localLocale);
    String str5 = ReportConsts.getColumnName(698, localLocale);
    String str6 = ReportConsts.getColumnName(734, localLocale);
    ReportResult localReportResult1 = new ReportResult(localLocale);
    localReportResult1.init(paramReportCriteria);
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      ArrayList localArrayList = a(paramArrayList, localHashMap1, localHashMap2);
      Calendar localCalendar1 = (Calendar)localArrayList.get(0);
      Calendar localCalendar2 = (Calendar)localArrayList.get(1);
      if (localCalendar2 == null)
      {
        localCalendar2 = Calendar.getInstance();
        localCalendar2.set(11, 0);
        localCalendar2.set(12, 0);
        localCalendar2.set(13, 0);
        localCalendar2.set(14, 0);
        localCalendar2.add(6, 1);
        localCalendar2.add(14, -1);
      }
      if (localCalendar1 == null)
      {
        localCalendar1 = (Calendar)localCalendar2.clone();
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        localCalendar1.add(6, -a(paramReportCriteria));
      }
      com.ffusion.beans.DateTime localDateTime1 = new com.ffusion.beans.DateTime(localCalendar1, localLocale);
      com.ffusion.beans.DateTime localDateTime2 = new com.ffusion.beans.DateTime(localCalendar2, localLocale);
      HashMap localHashMap3 = new HashMap();
      int i3 = 0;
      HashMap localHashMap4 = new HashMap();
      com.ffusion.beans.DateTime localDateTime3 = new com.ffusion.beans.DateTime((Calendar)localDateTime1.clone(), localLocale);
      Object localObject1;
      Object localObject2;
      Object localObject3;
      while ((localDateTime3.before(localDateTime2)) || (localDateTime3.isSameDayInYearAs(localDateTime2)))
      {
        localObject1 = null;
        localObject2 = new HashMap();
        localObject3 = new HashMap();
        int i4 = 0;
        Object localObject5;
        Object localObject6;
        Object localObject7;
        for (int i5 = 0; i5 < paramArrayList.size(); i5++)
        {
          localObject5 = null;
          localObject6 = (Accounts)paramArrayList.get(i5);
          localObject7 = ((Account)((Accounts)localObject6).get(0)).getRoutingNum();
          Calendar localCalendar3 = (Calendar)localHashMap1.get(localObject7);
          Calendar localCalendar4 = (Calendar)localHashMap2.get(localObject7);
          com.ffusion.beans.DateTime localDateTime4 = null;
          com.ffusion.beans.DateTime localDateTime5 = null;
          if (localCalendar4 != null) {
            localDateTime5 = new com.ffusion.beans.DateTime(localCalendar4, localLocale);
          }
          if (localCalendar3 != null) {
            localDateTime4 = new com.ffusion.beans.DateTime(localCalendar3, localLocale);
          }
          if (((localDateTime4 == null) || (localDateTime4.isSameDayInYearAs(localDateTime3)) || (!localDateTime3.before(localDateTime4))) && ((localDateTime5 == null) || (localDateTime5.isSameDayInYearAs(localDateTime3)) || (!localDateTime3.after(localDateTime5))))
          {
            Object localObject8 = null;
            Object localObject9 = null;
            if ((localDateTime4 == null) || (!localDateTime3.isSameDayInYearAs(localDateTime4)))
            {
              localObject8 = (Calendar)localDateTime3.clone();
              ((Calendar)localObject8).set(11, 0);
              ((Calendar)localObject8).set(12, 0);
              ((Calendar)localObject8).set(13, 0);
              ((Calendar)localObject8).set(14, 0);
            }
            else
            {
              localObject8 = localDateTime4;
            }
            if ((localDateTime5 == null) || (!localDateTime3.isSameDayInYearAs(localDateTime5)))
            {
              localObject9 = (Calendar)localDateTime3.clone();
              ((Calendar)localObject9).add(6, 1);
              ((Calendar)localObject9).add(14, -1);
            }
            else
            {
              localObject9 = localDateTime5;
            }
            HashMap localHashMap5 = new HashMap();
            HashMap localHashMap6 = new HashMap();
            int i6 = 0;
            Account localAccount = null;
            Object localObject11;
            Object localObject12;
            Object localObject13;
            for (int i7 = 0; i7 < ((Accounts)localObject6).size(); i7++)
            {
              localAccount = (Account)((Accounts)localObject6).get(i7);
              localObject11 = null;
              if (paramBoolean1)
              {
                localObject12 = jdField_if(localAccount, paramReportCriteria.getSearchCriteria(), (Calendar)localObject8, (Calendar)localObject9, localConnection, paramHashMap);
                if (!a((HashMap)localObject12))
                {
                  if (!paramBoolean2) {
                    continue;
                  }
                }
                else
                {
                  i6++;
                  if (localObject1 == null)
                  {
                    localObject1 = new ReportResult(localLocale);
                    localReportResult1.addSubReport((ReportResult)localObject1);
                    localObject13 = new com.ffusion.beans.DateTime(localDateTime3, localLocale);
                    String str7 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
                    if (str7 != null) {
                      ((com.ffusion.beans.DateTime)localObject13).setFormat(str7);
                    }
                    if (!bool) {
                      jdField_if((ReportResult)localObject1, ((com.ffusion.beans.DateTime)localObject13).toString());
                    }
                    a((ReportResult)localObject1, 0, -1);
                  }
                  if (localObject5 == null)
                  {
                    localObject5 = new ReportResult(localLocale);
                    ((ReportResult)localObject1).addSubReport((ReportResult)localObject5);
                    localObject13 = new StringBuffer(str3);
                    ((StringBuffer)localObject13).append(" : ");
                    if (localAccount.getBankName() != null)
                    {
                      ((StringBuffer)localObject13).append(localAccount.getBankName());
                      ((StringBuffer)localObject13).append(" - ");
                    }
                    ((StringBuffer)localObject13).append(localAccount.getRoutingNum());
                    if (!bool) {
                      jdField_if((ReportResult)localObject5, ((StringBuffer)localObject13).toString());
                    }
                    a((ReportResult)localObject5, 0, -1);
                  }
                  localObject11 = new ReportResult(localLocale);
                  ((ReportResult)localObject5).addSubReport((ReportResult)localObject11);
                  localObject13 = new StringBuffer(str1);
                  ((StringBuffer)localObject13).append(" : ");
                  try
                  {
                    ((StringBuffer)localObject13).append(AccountUtil.buildAccountDisplayText(localAccount.getID(), localLocale));
                  }
                  catch (UtilException localUtilException)
                  {
                    DebugLog.throwing("Error while constructing account display string.", localUtilException);
                    ((StringBuffer)localObject13).append(localAccount.getID());
                  }
                  if ((localAccount.getNickName() != null) && (localAccount.getNickName().length() > 0))
                  {
                    ((StringBuffer)localObject13).append(" - ");
                    ((StringBuffer)localObject13).append(localAccount.getNickName());
                  }
                  if (localAccount.getCurrencyCode() != null)
                  {
                    ((StringBuffer)localObject13).append(" - ");
                    ((StringBuffer)localObject13).append(localAccount.getCurrencyCode());
                  }
                  if (!bool) {
                    jdField_if((ReportResult)localObject11, ((StringBuffer)localObject13).toString());
                  }
                  a((ReportResult)localObject11, 0, -1);
                  i2 = a((ReportResult)localObject11, localAccount, localDateTime3, paramReportCriteria, (HashMap)localObject12, i2, i1, paramHashMap, localAccount.getCurrencyCode(), null, -1);
                  if (a(i1, i2, localReportResult1))
                  {
                    ReportResult localReportResult2 = localReportResult1;
                    return localReportResult2;
                  }
                  a(localAccount.getCurrencyCode(), localHashMap5, (HashMap)localObject12, localLocale);
                  a(paramSecureUser, localAccount.getCurrencyCode(), (HashMap)localObject12, paramSecureUser.getBaseCurrency(), localDateTime3, localHashMap6, paramHashMap);
                }
              }
              if (paramBoolean2)
              {
                localObject12 = new ArrayList(3);
                ((ArrayList)localObject12).add(localObject5);
                ((ArrayList)localObject12).add(localObject11);
                ((ArrayList)localObject12).add(localObject1);
                i2 = fillByDateBalanceDetailReport(localReportResult1, 0, localAccount, new com.ffusion.beans.DateTime((Calendar)localObject8, localLocale), new com.ffusion.beans.DateTime((Calendar)localObject9, localLocale), paramReportCriteria, (ArrayList)localObject12, false, i2, i1, localConnection, paramHashMap);
                if (a(i1, i2, localReportResult1))
                {
                  localObject13 = localReportResult1;
                  return localObject13;
                }
                localObject5 = (ReportResult)((ArrayList)localObject12).get(0);
                localObject11 = (ReportResult)((ArrayList)localObject12).get(1);
                localObject1 = (ReportResult)((ArrayList)localObject12).get(2);
              }
            }
            if (paramBoolean1)
            {
              Object localObject10;
              if (a(i1, i2, localReportResult1))
              {
                localObject10 = localReportResult1;
                return localObject10;
              }
              if (i6 > 0) {
                i4++;
              }
              if (i6 > 1)
              {
                localObject10 = null;
                if (bool) {
                  localObject10 = "Balance_Summary_Reports_Bank_Total_CSV_Format";
                } else {
                  localObject10 = "Balance_Summary_Reports_Bank_Total_With_Currency_Code";
                }
                i2 = a(paramSecureUser, (String)localObject10, localHashMap5, localReportResult1, i2, i1, paramReportCriteria, paramHashMap, localAccount, localDateTime3, 1);
                localObject11 = null;
                if (!isCurrencyAloneInMap(localHashMap5, paramSecureUser.getBaseCurrency())) {
                  localObject11 = "Balance_Summary_Reports_Bank_Total_Base_Currency_With_Currency_Conversion";
                } else {
                  localObject11 = "Balance_Summary_Reports_Bank_Total_Base_Currency_No_Currency_Conversion";
                }
                localObject12 = new Object[1];
                localObject12[0] = paramSecureUser.getBaseCurrency();
                localObject13 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject11, (Object[])localObject12);
                i2 = a((LocalizableString)localObject13, localHashMap6, localReportResult1, i2, i1, paramReportCriteria, paramHashMap, localAccount, localDateTime3, paramSecureUser.getBaseCurrency(), 1);
              }
              a(paramSecureUser, localHashMap5, (HashMap)localObject2, false, localDateTime3, paramHashMap);
              a(paramSecureUser, paramSecureUser.getBaseCurrency(), localHashMap6, paramSecureUser.getBaseCurrency(), localDateTime3, (HashMap)localObject3, paramHashMap);
            }
          }
        }
        Object localObject4;
        if (a(i1, i2, localReportResult1))
        {
          localObject4 = localReportResult1;
          return localObject4;
        }
        if (i4 > 0) {
          i3++;
        }
        if (i4 > 1)
        {
          localObject4 = null;
          if (bool) {
            localObject4 = "Balance_Summary_Reports_Grand_Total_CSV_Format";
          } else {
            localObject4 = "Balance_Summary_Reports_Grand_Total_With_Currency_Code";
          }
          i2 = a(paramSecureUser, (String)localObject4, (HashMap)localObject2, localReportResult1, i2, i1, paramReportCriteria, paramHashMap, null, localDateTime3, 2);
          localObject5 = null;
          if (!isCurrencyAloneInMap((HashMap)localObject2, paramSecureUser.getBaseCurrency())) {
            localObject5 = "Balance_Summary_Reports_Grand_Total_Base_Currency_With_Currency_Conversion";
          } else {
            localObject5 = "Balance_Summary_Reports_Grand_Total_Base_Currency_No_Currency_Conversion";
          }
          localObject6 = new Object[1];
          localObject6[0] = paramSecureUser.getBaseCurrency();
          localObject7 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject5, (Object[])localObject6);
          i2 = a((LocalizableString)localObject7, (HashMap)localObject3, localReportResult1, i2, i1, paramReportCriteria, paramHashMap, null, localDateTime3, paramSecureUser.getBaseCurrency(), 2);
        }
        a(paramSecureUser, (HashMap)localObject2, localHashMap3, true, localDateTime3, paramHashMap);
        a(paramSecureUser, paramSecureUser.getBaseCurrency(), localHashMap4, localDateTime3, (HashMap)localObject3, localLocale, paramHashMap);
        if (!bool)
        {
          localObject4 = getFXRatesForCurrencyTotals((HashMap)localObject2, paramSecureUser, localDateTime3, paramHashMap);
          a(paramSecureUser, (HashMap)localObject4, localReportResult1, paramHashMap);
        }
        localDateTime3.add(6, 1);
      }
      if (a(i1, i2, localReportResult1))
      {
        localObject1 = localReportResult1;
        return localObject1;
      }
      if (i3 > 1)
      {
        localObject1 = null;
        if (bool) {
          localObject1 = "Balance_Summary_Reports_Date_Total_CSV_Format";
        } else {
          localObject1 = "Balance_Summary_Reports_Date_Total_With_Currency_Code";
        }
        i2 = a(paramSecureUser, (String)localObject1, localHashMap3, localReportResult1, i2, i1, paramReportCriteria, paramHashMap, null, null, 3);
        localObject2 = null;
        if (!isCurrencyAloneInMap(localHashMap3, paramSecureUser.getBaseCurrency())) {
          localObject2 = "Balance_Summary_Reports_Date_Total_Base_Currency_With_Currency_Conversion";
        } else {
          localObject2 = "Balance_Summary_Reports_Date_Total_Base_Currency_No_Currency_Conversion";
        }
        localObject3 = new Object[1];
        localObject3[0] = paramSecureUser.getBaseCurrency();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject2, (Object[])localObject3);
        i2 = a(localLocalizableString, localHashMap4, localReportResult1, i2, i1, paramReportCriteria, paramHashMap, null, null, paramSecureUser.getBaseCurrency(), 3);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing("Unable to generate report.", localCSILException);
      throw localCSILException;
    }
    catch (DCException localDCException)
    {
      DebugLog.throwing("Unable to generate report.", localDCException);
      throw localDCException;
    }
    finally
    {
      localReportResult1.fini();
      DCAdapter.releaseDBConnection(localConnection);
    }
    return localReportResult1;
  }
  
  private static void a(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2, boolean paramBoolean, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap3)
    throws CSILException, DCException, RptException
  {
    Locale localLocale = paramSecureUser.getLocale();
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      HashMap localHashMap = (HashMap)localEntry.getValue();
      if (paramHashMap2 != null) {
        if (paramBoolean) {
          a(str, paramHashMap2, paramDateTime, localHashMap, localLocale, paramHashMap3);
        } else {
          a(str, paramHashMap2, localHashMap, localLocale);
        }
      }
    }
  }
  
  private static int a(SecureUser paramSecureUser, String paramString, HashMap paramHashMap1, ReportResult paramReportResult, int paramInt1, int paramInt2, ReportCriteria paramReportCriteria, HashMap paramHashMap2, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, int paramInt3)
    throws CSILException, DCException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    if (!isCurrencyAloneInMap(paramHashMap1, paramSecureUser.getBaseCurrency()))
    {
      Set localSet = paramHashMap1.entrySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str = (String)localEntry.getKey();
        HashMap localHashMap = (HashMap)localEntry.getValue();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = str;
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", paramString, arrayOfObject);
        a(localLocalizableString, localHashMap, paramReportResult, paramInt1, paramInt2, paramReportCriteria, paramHashMap2, paramAccount, paramDateTime, str, paramInt3);
      }
    }
    return paramInt1;
  }
  
  private static int a(LocalizableString paramLocalizableString, HashMap paramHashMap1, ReportResult paramReportResult, int paramInt1, int paramInt2, ReportCriteria paramReportCriteria, HashMap paramHashMap2, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, String paramString, int paramInt3)
    throws CSILException, DCException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    if (bool)
    {
      a(localReportResult, -1, -1);
    }
    else
    {
      jdField_if(localReportResult, (String)paramLocalizableString.localize(paramReportResult.getLocale()));
      a(localReportResult, c.length, -1);
      a.a(localReportResult, localLocale, c);
    }
    paramInt1 = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap1, paramInt1, paramInt2, paramHashMap2, paramString, paramLocalizableString, paramInt3);
    return paramInt1;
  }
  
  private static void a(SecureUser paramSecureUser, HashMap paramHashMap1, ReportResult paramReportResult, HashMap paramHashMap2)
    throws CSILException, DCException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, 1, -1);
    a.a(localReportResult, localLocale, jdField_case);
    int i1 = 1;
    Iterator localIterator = paramHashMap1.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      FXRate localFXRate = (FXRate)localEntry.getValue();
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("1 ");
      localStringBuffer.append(localEntry.getKey());
      localStringBuffer.append(" = ");
      localStringBuffer.append(localFXRate.getBuyPrice().getAmountValue().toString());
      localStringBuffer.append(" ");
      localStringBuffer.append(paramSecureUser.getBaseCurrency());
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportDataItems.add().setData(localStringBuffer.toString());
      a(localReportResult, localReportDataItems, i1);
      i1++;
    }
  }
  
  private static void a(String paramString, HashMap paramHashMap1, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap2, Locale paramLocale, HashMap paramHashMap3)
    throws DCException, CSILException
  {
    HashMap localHashMap = (HashMap)paramHashMap1.get(paramString);
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramHashMap1.put(paramString, localHashMap);
    }
    a(null, paramString, localHashMap, paramDateTime, paramHashMap2, paramLocale, paramHashMap3);
  }
  
  private static Object a(SecureUser paramSecureUser, String paramString, Object paramObject, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramSecureUser != null) && (paramString != null) && (!paramSecureUser.getBaseCurrency().equals(paramString)))
    {
      if ((paramObject instanceof SummaryValue)) {
        return SummaryValue.getInstance(FX.convertToBaseCurrency(paramSecureUser, ((SummaryValue)paramObject)._amount, paramDateTime, paramHashMap), ((SummaryValue)paramObject)._itemCount);
      }
      if ((paramObject instanceof BigDecimal))
      {
        Currency localCurrency = FX.convertToBaseCurrency(paramSecureUser, new Currency((BigDecimal)paramObject, paramString, paramSecureUser.getLocale()), paramDateTime, paramHashMap);
        return localCurrency.getAmountValue();
      }
      if ((paramObject instanceof Currency)) {
        return FX.convertToBaseCurrency(paramSecureUser, (Currency)paramObject, paramDateTime, paramHashMap);
      }
    }
    return paramObject;
  }
  
  private static void a(SecureUser paramSecureUser, String paramString, HashMap paramHashMap1, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap2, Locale paramLocale, HashMap paramHashMap3)
    throws DCException, CSILException
  {
    Set localSet = paramHashMap2.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      Object localObject = paramHashMap2.get(str1);
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(Integer.parseInt(str1));
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log(Level.WARNING, "The BAI Type code " + str1 + " does not have a type info associated with " + "it in the BAI Type Info cache.");
      }
      continue;
      String str2 = null;
      if (paramSecureUser != null) {
        str2 = paramSecureUser.getBaseCurrency();
      } else {
        str2 = paramString;
      }
      if (localBAITypeCodeInfo.getLevel() == 1) {
        a(str2, str1, paramHashMap1, a(paramSecureUser, paramString, localObject, paramDateTime, paramHashMap3), paramLocale);
      } else if (localBAITypeCodeInfo.getLevel() == 0) {
        switch (Integer.parseInt(str1))
        {
        case 10: 
        case 39: 
        case 40: 
        case 44: 
          if (!paramHashMap1.containsKey(str1)) {
            paramHashMap1.put(str1, a(paramSecureUser, paramString, localObject, paramDateTime, paramHashMap3));
          }
          break;
        case 11: 
        case 12: 
        case 15: 
        case 20: 
        case 21: 
        case 24: 
        case 25: 
        case 30: 
        case 37: 
        case 41: 
        case 42: 
        case 43: 
        case 45: 
        case 50: 
        case 51: 
        case 54: 
        case 55: 
        case 56: 
        case 57: 
        case 59: 
        case 60: 
        case 61: 
        case 62: 
        case 65: 
        case 66: 
        case 67: 
        case 68: 
        case 70: 
        case 72: 
        case 74: 
        case 75: 
        case 77: 
        case 78: 
        case 80: 
        case 81: 
        case 82: 
        case 83: 
        case 84: 
        case 85: 
        case 86: 
        case 701: 
        case 703: 
        case 705: 
        case 707: 
        case 709: 
        default: 
          paramHashMap1.put(str1, a(paramSecureUser, paramString, localObject, paramDateTime, paramHashMap3));
          break;
        case 22: 
        case 63: 
        case 73: 
        case 76: 
        case 87: 
        case 88: 
          a(str2, str1, paramHashMap1, a(paramSecureUser, paramString, localObject, paramDateTime, paramHashMap3), paramLocale);
        }
      }
    }
  }
  
  private static void a(String paramString1, String paramString2, HashMap paramHashMap, Object paramObject, Locale paramLocale)
    throws DCException
  {
    Object localObject;
    if ((paramObject instanceof SummaryValue))
    {
      localObject = (SummaryValue)paramHashMap.get(paramString2);
      if (localObject == null)
      {
        localObject = SummaryValue.getInstance(new Currency(new BigDecimal(0.0D), ((SummaryValue)paramObject)._amount.getCurrencyCode(), ((SummaryValue)paramObject)._amount.getLocale()), ((SummaryValue)paramObject)._itemCount);
        paramHashMap.put(paramString2, localObject);
      }
      else if (((SummaryValue)paramObject)._itemCount != -1)
      {
        if (((SummaryValue)localObject)._itemCount == -1) {
          ((SummaryValue)localObject)._itemCount = ((SummaryValue)paramObject)._itemCount;
        } else {
          localObject._itemCount += ((SummaryValue)paramObject)._itemCount;
        }
      }
      ((SummaryValue)localObject)._amount.addAmount(((SummaryValue)paramObject)._amount);
    }
    else if ((paramObject instanceof BigDecimal))
    {
      localObject = (SummaryValue)paramHashMap.get(paramString2);
      if (localObject == null)
      {
        localObject = SummaryValue.getInstance(new Currency(new BigDecimal(0.0D), paramString1, paramLocale));
        paramHashMap.put(paramString2, localObject);
      }
      ((SummaryValue)localObject)._amount.addAmount((BigDecimal)paramObject);
    }
    else if ((paramObject instanceof Currency))
    {
      localObject = (SummaryValue)paramHashMap.get(paramString2);
      if (localObject == null)
      {
        localObject = SummaryValue.getInstance(new Currency(new BigDecimal(0.0D), paramString1, paramLocale));
        paramHashMap.put(paramString2, localObject);
      }
      ((SummaryValue)localObject)._amount.addAmount((Currency)paramObject);
    }
    else if ((paramObject instanceof Integer))
    {
      localObject = (Integer)paramHashMap.get(paramString2);
      Integer localInteger = null;
      if (localObject == null) {
        localInteger = (Integer)paramObject;
      } else {
        localInteger = new Integer(((Integer)localObject).intValue() + ((Integer)paramObject).intValue());
      }
      paramHashMap.put(paramString2, localInteger);
    }
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, ReportCriteria paramReportCriteria1, ReportCriteria paramReportCriteria2, ArrayList paramArrayList1, ArrayList paramArrayList2, Vector paramVector, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Connection paramConnection, HashMap paramHashMap)
    throws SQLException, RptException, DCException
  {
    ResultSet localResultSet = null;
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    Properties localProperties = paramReportCriteria2.getReportOptions();
    boolean bool = new Boolean(localProperties.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    try
    {
      Locale localLocale = paramReportResult.getLocale();
      String str = null;
      localResultSet = a(paramInt1, arrayOfPreparedStatement, paramAccount, new com.ffusion.beans.DateTime(paramCalendar1, localLocale), new com.ffusion.beans.DateTime(paramCalendar2, localLocale), paramReportCriteria1, paramConnection, paramHashMap);
      if (localResultSet == null)
      {
        int i1 = paramInt2;
        return i1;
      }
      if (a(localResultSet))
      {
        a(paramArrayList2, paramArrayList1, paramReportResult, paramReportCriteria2, paramDateTime, paramAccount);
        ReportResult localReportResult = (ReportResult)paramArrayList1.get(paramArrayList1.size() - 1);
        paramInt2 = a(localReportResult, paramAccount, localResultSet, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, bool, paramVector, str, paramReportCriteria2);
      }
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
    }
    return paramInt2;
  }
  
  private static IReportResult jdField_if(SecureUser paramSecureUser, ArrayList paramArrayList, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, HashMap paramHashMap)
    throws CSILException, RptException, DCException
  {
    Locale localLocale = paramSecureUser.getLocale();
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(new Integer(697));
    localArrayList1.add(new Integer(700));
    localArrayList1.add(new Integer(701));
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    boolean bool1 = new Boolean(localProperties1.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    int i1 = a(paramReportCriteria.getReportOptions());
    ArrayList localArrayList2 = 0;
    String str1 = ReportConsts.getColumnName(699, localLocale);
    String str2 = ReportConsts.getColumnName(698, localLocale);
    String str3 = ReportConsts.getColumnName(734, localLocale);
    ReportResult localReportResult1 = new ReportResult(localLocale);
    localReportResult1.init(paramReportCriteria);
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      HashMap localHashMap1 = new HashMap();
      int i2 = 0;
      HashMap localHashMap2 = new HashMap();
      Business localBusiness = (Business)paramHashMap.get("BusinessForReport");
      if (localBusiness == null) {
        throw new DCException("The Business object was not found in extra.");
      }
      int i3 = localBusiness.getLocationIdPlacementValue();
      String str4 = null;
      String str5 = null;
      if (i3 == 1)
      {
        str4 = "BankReferenceNumber";
        str5 = "SearchForNullBankRefNum";
      }
      else if (i3 == 2)
      {
        str4 = "CustomerReferenceNumber";
        str5 = "SearchForNullCustRefNum";
      }
      else
      {
        str4 = null;
      }
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      for (int i4 = 0; i4 < paramArrayList.size(); i4++)
      {
        localObject1 = null;
        localObject2 = new HashMap();
        localObject3 = (Accounts)paramArrayList.get(i4);
        localObject4 = new HashMap();
        int i5 = 0;
        Account localAccount = null;
        Object localObject5;
        Object localObject6;
        Object localObject7;
        for (int i6 = 0; i6 < ((Accounts)localObject3).size(); i6++)
        {
          if (a(i1, localArrayList2, localReportResult1))
          {
            localReportResult3 = localReportResult1;
            return localReportResult3;
          }
          localAccount = (Account)((Accounts)localObject3).get(i6);
          ReportResult localReportResult3 = null;
          localObject5 = new HashMap();
          localObject6 = a(localAccount, paramHashMap, paramReportCriteria);
          localObject7 = (com.ffusion.beans.DateTime)((ArrayList)localObject6).get(0);
          com.ffusion.beans.DateTime localDateTime = (com.ffusion.beans.DateTime)((ArrayList)localObject6).get(1);
          HashMap localHashMap3 = new HashMap();
          int i7 = 0;
          ReportResult localReportResult4 = null;
          ArrayList localArrayList3;
          ArrayList localArrayList4;
          Object localObject8;
          Object localObject9;
          if (paramBoolean1)
          {
            localArrayList3 = new ArrayList();
            localArrayList3.add(localObject1);
            localArrayList3.add(localReportResult3);
            localArrayList3.add(null);
            localArrayList4 = new ArrayList();
            localArrayList2 = a(paramSecureUser, (com.ffusion.beans.DateTime)localObject7, localDateTime, localAccount, localReportResult1, paramReportCriteria, localArrayList1, localArrayList3, localHashMap3, (HashMap)localObject5, localArrayList4, localArrayList2, i1, localConnection, paramHashMap);
            if (a(i1, localArrayList2, localReportResult1))
            {
              localObject8 = localReportResult1;
              return localObject8;
            }
            localObject1 = (ReportResult)localArrayList3.get(0);
            localReportResult3 = (ReportResult)localArrayList3.get(1);
            localReportResult4 = (ReportResult)localArrayList3.get(2);
            localObject8 = (Integer)localArrayList4.get(0);
            i7 = ((Integer)localObject8).intValue();
            if (i7 >= 1)
            {
              localObject9 = (HashMap)localHashMap3.get(localAccount.getCurrencyCode());
              localArrayList2 = a(localReportResult4, localAccount, null, paramReportCriteria, (HashMap)localObject9, localArrayList2, i1, paramHashMap, localAccount.getCurrencyCode(), null, -1);
              if (a(i1, localArrayList2, localReportResult1))
              {
                ReportResult localReportResult5 = localReportResult1;
                return localReportResult5;
              }
            }
          }
          if (paramBoolean2)
          {
            localArrayList3 = localArrayList2;
            localArrayList4 = new ArrayList();
            localArrayList4.add(localObject1);
            localArrayList4.add(localReportResult3);
            localArrayList4.add(localReportResult4);
            localObject8 = new Currency("0", localAccount.getCurrencyCode(), localLocale, "CURRENCY");
            localObject9 = new Currency("0", localAccount.getCurrencyCode(), localLocale, "CURRENCY");
            int i8 = 0;
            int i9 = 0;
            int i10 = paramReportCriteria.getSortCriteria().getOrdinalOfCriteriaByName("Location") == 2 ? 1 : 0;
            Object localObject10;
            if ((i10 != 0) && (str4 != null) && (localAccount.isMaster()))
            {
              localObject10 = paramReportCriteria.getSearchCriteria();
              Properties localProperties2 = new Properties();
              localProperties2.putAll((Map)localObject10);
              ArrayList localArrayList5 = DCAdapter.getLocationIds(localAccount, (Calendar)localObject7, localDateTime, localBusiness.getLocationIdPlacementValue(), paramHashMap);
              for (int i11 = 0; i11 < localArrayList5.size(); i11++)
              {
                localProperties2 = new Properties();
                localProperties2.putAll((Map)localObject10);
                String str7 = (String)localArrayList5.get(i11);
                if (str7 == null)
                {
                  localProperties2.remove(str4);
                  localProperties2.put(str5, "IgnoreThisValue");
                  paramReportCriteria.setHiddenSearchCriterion(str5, true);
                }
                else
                {
                  localProperties2.put(str4, str7);
                }
                paramReportCriteria.setSearchCriteria(localProperties2);
                Vector localVector = new Vector();
                localVector.add(localObject8);
                localVector.add(localObject9);
                localVector.add(new Integer(i8));
                localVector.add(new Integer(i9));
                localArrayList2 = a(localAccount, paramReportCriteria, paramBoolean3, paramBoolean4, localArrayList4, localArrayList2, localArrayList3, localReportResult1, localConnection, localVector, paramSecureUser, paramHashMap);
                localObject8 = (Currency)localVector.get(0);
                localObject9 = (Currency)localVector.get(1);
                i8 = ((Integer)localVector.get(2)).intValue();
                i9 = ((Integer)localVector.get(3)).intValue();
              }
              paramReportCriteria.setSearchCriteria((Properties)localObject10);
            }
            else
            {
              localObject10 = new Vector();
              ((Vector)localObject10).add(localObject8);
              ((Vector)localObject10).add(localObject9);
              ((Vector)localObject10).add(new Integer(i8));
              ((Vector)localObject10).add(new Integer(i9));
              localArrayList2 = a(localAccount, paramReportCriteria, paramBoolean3, paramBoolean4, localArrayList4, localArrayList2, localArrayList3, localReportResult1, localConnection, (Vector)localObject10, paramSecureUser, paramHashMap);
              localObject8 = (Currency)((Vector)localObject10).get(0);
              localObject9 = (Currency)((Vector)localObject10).get(1);
              i8 = ((Integer)((Vector)localObject10).get(2)).intValue();
              i9 = ((Integer)((Vector)localObject10).get(3)).intValue();
            }
            if (a(i1, localArrayList2, localReportResult1))
            {
              localObject10 = localReportResult1;
              return localObject10;
            }
            localObject1 = (ReportResult)localArrayList4.get(0);
            localReportResult3 = (ReportResult)localArrayList4.get(1);
            localReportResult4 = (ReportResult)localArrayList4.get(2);
            if ((!((Currency)localObject8).equals(0.0D)) || (!((Currency)localObject9).equals(0.0D)) || (i8 > 0) || (i9 > 0)) {
              localArrayList2 = a(localReportResult3, localAccount, (Currency)localObject8, (Currency)localObject9, new Integer(i8), new Integer(i9), localArrayList2, i1, paramReportCriteria, null);
            }
            if (a(i1, localArrayList2, localReportResult1))
            {
              localObject10 = localReportResult1;
              return localObject10;
            }
          }
          if (i7 > 0) {
            i5++;
          }
          a(paramSecureUser, localHashMap3, (HashMap)localObject2, false, null, paramHashMap);
          a(paramSecureUser, paramSecureUser.getBaseCurrency(), (HashMap)localObject5, paramSecureUser.getBaseCurrency(), null, (HashMap)localObject4, paramHashMap);
        }
        if (i5 > 0) {
          i2++;
        }
        if (i5 > 1)
        {
          String str6 = null;
          boolean bool3 = "CSV".equals(localProperties1.getProperty("FORMAT"));
          if (bool3) {
            str6 = "Balance_Summary_Reports_Bank_Total_CSV_Format";
          } else {
            str6 = "Balance_Summary_Reports_Bank_Total_With_Currency_Code";
          }
          localArrayList2 = a(paramSecureUser, str6, (HashMap)localObject2, (ReportResult)localObject1, localArrayList2, i1, paramReportCriteria, paramHashMap, localAccount, null, 1);
          localObject5 = null;
          if (!isCurrencyAloneInMap((HashMap)localObject2, paramSecureUser.getBaseCurrency())) {
            localObject5 = "Balance_Summary_Reports_Bank_Total_Base_Currency_With_Currency_Conversion";
          } else {
            localObject5 = "Balance_Summary_Reports_Bank_Total_Base_Currency_No_Currency_Conversion";
          }
          localObject6 = new Object[1];
          localObject6[0] = paramSecureUser.getBaseCurrency();
          localObject7 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject5, (Object[])localObject6);
          localArrayList2 = a((LocalizableString)localObject7, (HashMap)localObject4, (ReportResult)localObject1, localArrayList2, i1, paramReportCriteria, paramHashMap, localAccount, null, paramSecureUser.getBaseCurrency(), 1);
        }
        a(paramSecureUser, (HashMap)localObject2, localHashMap1, false, null, paramHashMap);
        a(paramSecureUser, paramSecureUser.getBaseCurrency(), (HashMap)localObject4, paramSecureUser.getBaseCurrency(), null, localHashMap2, paramHashMap);
      }
      if (a(i1, localArrayList2, localReportResult1))
      {
        ReportResult localReportResult2 = localReportResult1;
        return localReportResult2;
      }
      if (i2 > 1)
      {
        boolean bool2 = "CSV".equals(localProperties1.getProperty("FORMAT"));
        localObject1 = null;
        if (bool2) {
          localObject1 = "Balance_Summary_Reports_Grand_Total_CSV_Format";
        } else {
          localObject1 = "Balance_Summary_Reports_Grand_Total_With_Currency_Code";
        }
        localArrayList2 = a(paramSecureUser, (String)localObject1, localHashMap1, localReportResult1, localArrayList2, i1, paramReportCriteria, paramHashMap, null, null, 2);
        localObject2 = null;
        if (!isCurrencyAloneInMap(localHashMap1, paramSecureUser.getBaseCurrency())) {
          localObject2 = "Balance_Summary_Reports_Grand_Total_Base_Currency_With_Currency_Conversion";
        } else {
          localObject2 = "Balance_Summary_Reports_Grand_Total_Base_Currency_No_Currency_Conversion";
        }
        localObject3 = new Object[1];
        localObject3[0] = paramSecureUser.getBaseCurrency();
        localObject4 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject2, (Object[])localObject3);
        localArrayList2 = a((LocalizableString)localObject4, localHashMap2, localReportResult1, localArrayList2, i1, paramReportCriteria, paramHashMap, null, null, paramSecureUser.getBaseCurrency(), 2);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing("Unable to generate report.", localCSILException);
      throw localCSILException;
    }
    catch (DCException localDCException)
    {
      DebugLog.throwing("Unable to generate report.", localDCException);
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to generate Balance Report from database.", localSQLException);
    }
    finally
    {
      localReportResult1.fini();
      DCAdapter.releaseDBConnection(localConnection);
    }
    return localReportResult1;
  }
  
  private static int a(Account paramAccount, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, ArrayList paramArrayList, int paramInt1, int paramInt2, ReportResult paramReportResult, Connection paramConnection, Vector paramVector, SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException, SQLException, RptException, DCException
  {
    int i1 = a(paramReportCriteria.getReportOptions());
    Locale localLocale = paramSecureUser.getLocale();
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(new Integer(697));
    localArrayList1.add(new Integer(700));
    localArrayList1.add(new Integer(701));
    ReportResult localReportResult1 = (ReportResult)paramArrayList.get(0);
    ReportResult localReportResult2 = (ReportResult)paramArrayList.get(1);
    ReportResult localReportResult3 = (ReportResult)paramArrayList.get(2);
    ArrayList localArrayList2 = a(paramAccount, paramHashMap, paramReportCriteria);
    com.ffusion.beans.DateTime localDateTime1 = (com.ffusion.beans.DateTime)localArrayList2.get(0);
    com.ffusion.beans.DateTime localDateTime2 = (com.ffusion.beans.DateTime)localArrayList2.get(1);
    HashMap localHashMap = new HashMap();
    com.ffusion.beans.DateTime localDateTime3 = new com.ffusion.beans.DateTime((Calendar)localDateTime1.clone(), localLocale);
    Currency localCurrency1 = (Currency)paramVector.get(0);
    Currency localCurrency2 = (Currency)paramVector.get(1);
    int i2 = ((Integer)paramVector.get(2)).intValue();
    int i3 = ((Integer)paramVector.get(3)).intValue();
    ArrayList localArrayList3 = new ArrayList(3);
    localArrayList3.add(localReportResult1);
    localArrayList3.add(localReportResult2);
    localArrayList3.add(localReportResult3);
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    Object localObject1 = null;
    int i4 = 0;
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.setLocale(paramReportCriteria.getLocale());
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add("AccountNumber");
    localArrayList4.add("ProcessingDate");
    ReportSortCriteria localReportSortCriteria = a(paramReportCriteria.getSortCriteria(), localArrayList4);
    localReportCriteria.setSortCriteria(localReportSortCriteria);
    localReportCriteria.setSearchCriteria(paramReportCriteria.getSearchCriteria());
    localReportCriteria.setReportOptions(paramReportCriteria.getReportOptions());
    int i5 = -1;
    Object localObject2 = null;
    Vector localVector = new Vector();
    int i6 = 0;
    while ((localDateTime3.before(localDateTime2)) || (localDateTime3.isSameDayInYearAs(localDateTime2)))
    {
      localVector.clear();
      Object localObject3 = null;
      Object localObject4 = null;
      if (!localDateTime3.isSameDayInYearAs(localDateTime1))
      {
        localObject3 = (Calendar)localDateTime3.clone();
        ((Calendar)localObject3).set(11, 0);
        ((Calendar)localObject3).set(12, 0);
        ((Calendar)localObject3).set(13, 0);
        ((Calendar)localObject3).set(14, 0);
      }
      else
      {
        localObject3 = localDateTime1;
      }
      if (!localDateTime3.isSameDayInYearAs(localDateTime2))
      {
        localObject4 = (Calendar)localDateTime3.clone();
        ((Calendar)localObject4).add(6, 1);
        ((Calendar)localObject4).add(14, -1);
      }
      else
      {
        localObject4 = localDateTime2;
      }
      int i7;
      Currency localCurrency3;
      Integer localInteger1;
      if (paramBoolean1)
      {
        if (paramBoolean2)
        {
          localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
          i7 = a(paramReportResult, paramAccount, null, (Calendar)localObject3, (Calendar)localObject4, 1, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList1, localVector, paramInt1, i1, i6, paramInt1 == paramInt2, paramConnection, paramHashMap);
          paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
          i6 += i7 - paramInt1;
          paramInt1 = i7;
          if (localVector.size() >= 2)
          {
            localCurrency3 = (Currency)localVector.get(0);
            localInteger1 = (Integer)localVector.get(1);
            localCurrency1.addAmount(localCurrency3);
            i2 += localInteger1.intValue();
          }
          if (a(i1, paramInt1, paramReportResult)) {
            return paramInt1;
          }
          localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
          i7 = a(paramReportResult, paramAccount, null, (Calendar)localObject3, (Calendar)localObject4, 2, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList1, localVector, paramInt1, i1, i6, paramInt1 == paramInt2, paramConnection, paramHashMap);
          paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
          i6 += i7 - paramInt1;
          paramInt1 = i7;
          if (localVector.size() >= 4)
          {
            localCurrency3 = (Currency)localVector.get(2);
            localInteger1 = (Integer)localVector.get(3);
            localCurrency2.addAmount(localCurrency3);
            i3 += localInteger1.intValue();
          }
        }
        else
        {
          localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
          i7 = a(paramReportResult, paramAccount, null, (Calendar)localObject3, (Calendar)localObject4, 2, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList1, localVector, paramInt1, i1, i6, paramInt1 == paramInt2, paramConnection, paramHashMap);
          i6 += i7 - paramInt1;
          paramInt1 = i7;
          paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
          if (localVector.size() >= 4)
          {
            localCurrency3 = (Currency)localVector.get(2);
            localInteger1 = (Integer)localVector.get(3);
            localCurrency2.addAmount(localCurrency3);
            i3 += localInteger1.intValue();
          }
          if (a(i1, paramInt1, paramReportResult)) {
            return paramInt1;
          }
          localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
          i7 = a(paramReportResult, paramAccount, null, (Calendar)localObject3, (Calendar)localObject4, 1, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList1, localVector, paramInt1, i1, i6, paramInt1 == paramInt2, paramConnection, paramHashMap);
          paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
          i6 += i7 - paramInt1;
          paramInt1 = i7;
          if (localVector.size() >= 2)
          {
            localCurrency3 = (Currency)localVector.get(0);
            localInteger1 = (Integer)localVector.get(1);
            localCurrency1.addAmount(localCurrency3);
            i2 += localInteger1.intValue();
          }
        }
      }
      else
      {
        localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
        i7 = a(paramReportResult, paramAccount, null, (Calendar)localObject3, (Calendar)localObject4, 3, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList1, localVector, paramInt1, i1, i6, paramInt1 == paramInt2, paramConnection, paramHashMap);
        paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
        i6 += i7 - paramInt1;
        paramInt1 = i7;
        if (localVector.size() >= 4)
        {
          localCurrency3 = (Currency)localVector.get(0);
          localInteger1 = (Integer)localVector.get(1);
          localCurrency1.addAmount(localCurrency3);
          i2 += localInteger1.intValue();
          Currency localCurrency4 = (Currency)localVector.get(2);
          Integer localInteger2 = (Integer)localVector.get(3);
          localCurrency2.addAmount(localCurrency4);
          i3 += localInteger2.intValue();
        }
      }
      paramVector.clear();
      paramVector.add(localCurrency1);
      paramVector.add(localCurrency2);
      paramVector.add(new Integer(i2));
      paramVector.add(new Integer(i3));
      localReportResult1 = (ReportResult)localArrayList3.get(0);
      localReportResult2 = (ReportResult)localArrayList3.get(1);
      localReportResult3 = (ReportResult)localArrayList3.get(2);
      paramArrayList.remove(0);
      paramArrayList.add(0, localReportResult1);
      paramArrayList.remove(1);
      paramArrayList.add(1, localReportResult2);
      paramArrayList.remove(2);
      paramArrayList.add(2, localReportResult3);
      if (a(i1, paramInt1, paramReportResult)) {
        return paramInt1;
      }
      localDateTime3.add(6, 1);
    }
    return paramInt1;
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ArrayList paramArrayList, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, HashMap paramHashMap)
    throws CSILException, RptException, DCException
  {
    Locale localLocale = paramSecureUser.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool1 = new Boolean(localProperties.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    int i1 = a(paramReportCriteria.getReportOptions());
    Integer localInteger1 = 0;
    String str1 = ReportConsts.getColumnName(699, localLocale);
    String str2 = ReportConsts.getColumnName(698, localLocale);
    String str3 = ReportConsts.getColumnName(734, localLocale);
    ReportResult localReportResult = new ReportResult(localLocale);
    localReportResult.init(paramReportCriteria);
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      HashMap localHashMap1 = new HashMap();
      int i2 = 0;
      HashMap localHashMap2 = new HashMap();
      Object localObject3;
      Object localObject4;
      Object localObject5;
      for (int i3 = 0; i3 < paramArrayList.size(); i3++)
      {
        Object localObject2 = null;
        localObject3 = new HashMap();
        localObject4 = (Accounts)paramArrayList.get(i3);
        localObject5 = new HashMap();
        int i4 = 0;
        Account localAccount = null;
        Object localObject8;
        Object localObject9;
        Object localObject10;
        for (int i5 = 0; i5 < ((Accounts)localObject4).size(); i5++)
        {
          Object localObject7 = null;
          localAccount = (Account)((Accounts)localObject4).get(i5);
          localObject8 = a(localAccount, paramHashMap, paramReportCriteria);
          localObject9 = (com.ffusion.beans.DateTime)((ArrayList)localObject8).get(0);
          localObject10 = (com.ffusion.beans.DateTime)((ArrayList)localObject8).get(1);
          HashMap localHashMap3 = new HashMap();
          HashMap localHashMap4 = new HashMap();
          int i6 = 0;
          Object localObject11;
          ArrayList localArrayList1;
          ArrayList localArrayList2;
          Integer localInteger2;
          Object localObject12;
          if (paramBoolean1)
          {
            localObject11 = new ArrayList();
            ((ArrayList)localObject11).add(new Integer(697));
            ((ArrayList)localObject11).add(new Integer(700));
            ((ArrayList)localObject11).add(new Integer(701));
            localArrayList1 = new ArrayList();
            localArrayList1.add(localObject2);
            localArrayList1.add(localObject7);
            localArrayList1.add(null);
            localArrayList2 = new ArrayList();
            localInteger1 = a(paramSecureUser, (com.ffusion.beans.DateTime)localObject9, (com.ffusion.beans.DateTime)localObject10, localAccount, localReportResult, paramReportCriteria, (ArrayList)localObject11, localArrayList1, localHashMap3, localHashMap4, localArrayList2, localInteger1, i1, localConnection, paramHashMap);
            localObject2 = (ReportResult)localArrayList1.get(0);
            localObject7 = (ReportResult)localArrayList1.get(1);
            localInteger2 = (Integer)localArrayList2.get(0);
            i6 = localInteger2.intValue();
            if (i6 > 1)
            {
              localObject12 = null;
              boolean bool4 = "CSV".equals(localProperties.getProperty("FORMAT"));
              if (bool4) {
                localObject12 = "Balance_Summary_Reports_Date_Total_CSV_Format";
              } else {
                localObject12 = "Balance_Summary_Reports_Date_Total_With_Currency_Code";
              }
              localInteger1 = a(paramSecureUser, (String)localObject12, localHashMap3, (ReportResult)localObject7, localInteger1, i1, paramReportCriteria, paramHashMap, localAccount, null, 3);
              String str4 = null;
              if (!isCurrencyAloneInMap(localHashMap3, paramSecureUser.getBaseCurrency())) {
                str4 = "Balance_Summary_Reports_Grand_Total_Base_Currency_With_Currency_Conversion";
              } else {
                str4 = "Balance_Summary_Reports_Grand_Total_Base_Currency_No_Currency_Conversion";
              }
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = paramSecureUser.getBaseCurrency();
              LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", str4, arrayOfObject);
              localInteger1 = a(localLocalizableString, localHashMap4, (ReportResult)localObject7, localInteger1, i1, paramReportCriteria, paramHashMap, localAccount, null, paramSecureUser.getBaseCurrency(), 3);
            }
          }
          if (a(i1, localInteger1, localReportResult))
          {
            localObject11 = localReportResult;
            return localObject11;
          }
          if (paramBoolean2)
          {
            localObject11 = new ArrayList();
            ((ArrayList)localObject11).add(new Integer(697));
            ((ArrayList)localObject11).add(new Integer(700));
            localArrayList1 = new ArrayList();
            localArrayList1.add(new Integer(697));
            localArrayList1.add(new Integer(700));
            if (paramBoolean3)
            {
              localArrayList2 = new ArrayList(3);
              localArrayList2.add(localObject2);
              localArrayList2.add(localObject7);
              localArrayList2.add(null);
              localInteger2 = localInteger1;
              localInteger1 = a((com.ffusion.beans.DateTime)localObject9, (com.ffusion.beans.DateTime)localObject10, localAccount, localReportResult, 1, (ArrayList)localObject11, localArrayList2, localInteger1, i1, paramReportCriteria, true, localConnection, paramHashMap);
              localObject2 = (ReportResult)localArrayList2.get(0);
              localObject7 = (ReportResult)localArrayList2.get(1);
              localObject12 = new ArrayList(3);
              ((ArrayList)localObject12).add(localObject2);
              ((ArrayList)localObject12).add(localObject7);
              ((ArrayList)localObject12).add(null);
              localInteger1 = a((com.ffusion.beans.DateTime)localObject9, (com.ffusion.beans.DateTime)localObject10, localAccount, localReportResult, 2, localArrayList1, (ArrayList)localObject12, localInteger1, i1, paramReportCriteria, localInteger1 == localInteger2, localConnection, paramHashMap);
            }
            else
            {
              localArrayList2 = new ArrayList(3);
              localArrayList2.add(localObject2);
              localArrayList2.add(localObject7);
              localArrayList2.add(null);
              Integer localInteger3 = localInteger1;
              localInteger1 = a((com.ffusion.beans.DateTime)localObject9, (com.ffusion.beans.DateTime)localObject10, localAccount, localReportResult, 2, localArrayList1, localArrayList2, localInteger1, i1, paramReportCriteria, true, localConnection, paramHashMap);
              localObject2 = (ReportResult)localArrayList2.get(0);
              localObject7 = (ReportResult)localArrayList2.get(1);
              localObject12 = new ArrayList(3);
              ((ArrayList)localObject12).add(localObject2);
              ((ArrayList)localObject12).add(localObject7);
              ((ArrayList)localObject12).add(null);
              localInteger1 = a((com.ffusion.beans.DateTime)localObject9, (com.ffusion.beans.DateTime)localObject10, localAccount, localReportResult, 1, (ArrayList)localObject11, (ArrayList)localObject12, localInteger1, i1, paramReportCriteria, localInteger1 == localInteger3, localConnection, paramHashMap);
            }
          }
          if (a(i1, localInteger1, localReportResult))
          {
            localObject11 = localReportResult;
            return localObject11;
          }
          if (i6 > 0) {
            i4++;
          }
          a(paramSecureUser, localHashMap3, (HashMap)localObject3, false, null, paramHashMap);
          a(paramSecureUser, paramSecureUser.getBaseCurrency(), localHashMap4, paramSecureUser.getBaseCurrency(), null, (HashMap)localObject5, paramHashMap);
        }
        Object localObject6;
        if (a(i1, localInteger1, localReportResult))
        {
          localObject6 = localReportResult;
          return localObject6;
        }
        if (i4 > 0) {
          i2++;
        }
        if (i4 > 1)
        {
          localObject6 = null;
          boolean bool3 = "CSV".equals(localProperties.getProperty("FORMAT"));
          if (bool3) {
            localObject6 = "Balance_Summary_Reports_Bank_Total_CSV_Format";
          } else {
            localObject6 = "Balance_Summary_Reports_Bank_Total_With_Currency_Code";
          }
          localInteger1 = a(paramSecureUser, (String)localObject6, (HashMap)localObject3, (ReportResult)localObject2, localInteger1, i1, paramReportCriteria, paramHashMap, localAccount, null, 1);
          localObject8 = null;
          if (!isCurrencyAloneInMap((HashMap)localObject3, paramSecureUser.getBaseCurrency())) {
            localObject8 = "Balance_Summary_Reports_Bank_Total_Base_Currency_With_Currency_Conversion";
          } else {
            localObject8 = "Balance_Summary_Reports_Bank_Total_Base_Currency_No_Currency_Conversion";
          }
          localObject9 = new Object[1];
          localObject9[0] = paramSecureUser.getBaseCurrency();
          localObject10 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject8, (Object[])localObject9);
          localInteger1 = a((LocalizableString)localObject10, (HashMap)localObject5, (ReportResult)localObject2, localInteger1, i1, paramReportCriteria, paramHashMap, localAccount, null, paramSecureUser.getBaseCurrency(), 1);
        }
        a(paramSecureUser, (HashMap)localObject3, localHashMap1, false, null, paramHashMap);
        a(paramSecureUser, paramSecureUser.getBaseCurrency(), (HashMap)localObject5, paramSecureUser.getBaseCurrency(), null, localHashMap2, paramHashMap);
      }
      Object localObject1;
      if (a(i1, localInteger1, localReportResult))
      {
        localObject1 = localReportResult;
        return localObject1;
      }
      if (i2 > 1)
      {
        localObject1 = null;
        boolean bool2 = "CSV".equals(localProperties.getProperty("FORMAT"));
        if (bool2) {
          localObject1 = "Balance_Summary_Reports_Grand_Total_CSV_Format";
        } else {
          localObject1 = "Balance_Summary_Reports_Grand_Total_With_Currency_Code";
        }
        localInteger1 = a(paramSecureUser, (String)localObject1, localHashMap1, localReportResult, localInteger1, i1, paramReportCriteria, paramHashMap, null, null, 2);
        localObject3 = null;
        if (!isCurrencyAloneInMap(localHashMap1, paramSecureUser.getBaseCurrency())) {
          localObject3 = "Balance_Summary_Reports_Grand_Total_Base_Currency_With_Currency_Conversion";
        } else {
          localObject3 = "Balance_Summary_Reports_Grand_Total_Base_Currency_No_Currency_Conversion";
        }
        localObject4 = new Object[1];
        localObject4[0] = paramSecureUser.getBaseCurrency();
        localObject5 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject3, (Object[])localObject4);
        localInteger1 = a((LocalizableString)localObject5, localHashMap2, localReportResult, localInteger1, i1, paramReportCriteria, paramHashMap, null, null, paramSecureUser.getBaseCurrency(), 2);
      }
    }
    catch (RptException localRptException)
    {
      DebugLog.throwing("Unable to generate report.", localRptException);
      throw localRptException;
    }
    catch (DCException localDCException)
    {
      DebugLog.throwing("Unable to generate report.", localDCException);
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to generate Balance Report from database.", localSQLException);
    }
    finally
    {
      localReportResult.fini();
      DCAdapter.releaseDBConnection(localConnection);
    }
    return localReportResult;
  }
  
  private static int a(SecureUser paramSecureUser, com.ffusion.beans.DateTime paramDateTime1, com.ffusion.beans.DateTime paramDateTime2, Account paramAccount, ReportResult paramReportResult, ReportCriteria paramReportCriteria, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap1, HashMap paramHashMap2, ArrayList paramArrayList3, int paramInt1, int paramInt2, Connection paramConnection, HashMap paramHashMap3)
    throws DCException, RptException, CSILException
  {
    int i1 = 0;
    Locale localLocale = paramReportResult.getLocale();
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime((Calendar)paramDateTime1.clone(), localLocale);
    try
    {
      while ((localDateTime.before(paramDateTime2)) || (localDateTime.isSameDayInYearAs(paramDateTime2)))
      {
        Object localObject1 = null;
        Object localObject2 = null;
        if (!localDateTime.isSameDayInYearAs(paramDateTime1))
        {
          localObject1 = (Calendar)localDateTime.clone();
          ((Calendar)localObject1).set(11, 0);
          ((Calendar)localObject1).set(12, 0);
          ((Calendar)localObject1).set(13, 0);
          ((Calendar)localObject1).set(14, 0);
        }
        else
        {
          localObject1 = paramDateTime1;
        }
        if (!localDateTime.isSameDayInYearAs(paramDateTime2))
        {
          localObject2 = (Calendar)localDateTime.clone();
          ((Calendar)localObject2).add(6, 1);
          ((Calendar)localObject2).add(14, -1);
        }
        else
        {
          localObject2 = paramDateTime2;
        }
        HashMap localHashMap = jdField_if(paramAccount, paramReportCriteria.getSearchCriteria(), (Calendar)localObject1, (Calendar)localObject2, paramConnection, paramHashMap3);
        if (!a(localHashMap))
        {
          localDateTime.add(6, 1);
        }
        else
        {
          i1++;
          a(paramArrayList1, paramArrayList2, paramReportResult, paramReportCriteria, null, paramAccount);
          if (a(paramInt2, paramInt1, paramReportResult))
          {
            int i2 = paramInt1;
            return i2;
          }
          a(paramAccount.getCurrencyCode(), paramHashMap1, localDateTime, localHashMap, localLocale, paramHashMap3);
          a(paramSecureUser, paramAccount.getCurrencyCode(), paramHashMap2, localDateTime, localHashMap, localLocale, paramHashMap3);
          localDateTime.add(6, 1);
        }
      }
    }
    finally
    {
      paramArrayList3.add(new Integer(i1));
    }
    return paramInt1;
  }
  
  private static int a(com.ffusion.beans.DateTime paramDateTime1, com.ffusion.beans.DateTime paramDateTime2, Account paramAccount, ReportResult paramReportResult, int paramInt1, ArrayList paramArrayList1, ArrayList paramArrayList2, int paramInt2, int paramInt3, ReportCriteria paramReportCriteria, boolean paramBoolean, Connection paramConnection, HashMap paramHashMap)
    throws DCException, RptException, SQLException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = new Boolean(localProperties.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime((Calendar)paramDateTime1.clone(), localLocale);
    int i1 = 0;
    int i2 = paramInt2;
    int i3 = 0;
    while ((localDateTime.before(paramDateTime2)) || (localDateTime.isSameDayInYearAs(paramDateTime2)))
    {
      paramArrayList2.set(paramArrayList2.size() - 1, null);
      Object localObject1 = null;
      Object localObject2 = null;
      if (!localDateTime.isSameDayInYearAs(paramDateTime1))
      {
        localObject1 = (Calendar)localDateTime.clone();
        ((Calendar)localObject1).set(11, 0);
        ((Calendar)localObject1).set(12, 0);
        ((Calendar)localObject1).set(13, 0);
        ((Calendar)localObject1).set(14, 0);
      }
      else
      {
        localObject1 = paramDateTime1;
      }
      if (!localDateTime.isSameDayInYearAs(paramDateTime2))
      {
        localObject2 = (Calendar)localDateTime.clone();
        ((Calendar)localObject2).add(6, 1);
        ((Calendar)localObject2).add(14, -1);
      }
      else
      {
        localObject2 = paramDateTime2;
      }
      ReportResult localReportResult = null;
      PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
      ResultSet localResultSet = null;
      Currency localCurrency1 = new Currency("0", localLocale, paramAccount.getCurrencyCode());
      Currency localCurrency2 = new Currency("0", localLocale, paramAccount.getCurrencyCode());
      Integer localInteger1 = new Integer(0);
      Integer localInteger2 = new Integer(0);
      int i4 = 0;
      ReportCriteria localReportCriteria = new ReportCriteria();
      localReportCriteria.setLocale(paramReportCriteria.getLocale());
      ArrayList localArrayList = new ArrayList();
      localArrayList.add("AccountNumber");
      localArrayList.add("ProcessingDate");
      ReportSortCriteria localReportSortCriteria = a(paramReportCriteria.getSortCriteria(), localArrayList);
      localReportCriteria.setSortCriteria(localReportSortCriteria);
      localReportCriteria.setSearchCriteria(paramReportCriteria.getSearchCriteria());
      localReportCriteria.setReportOptions(paramReportCriteria.getReportOptions());
      int i7;
      if (paramInt1 == 1)
      {
        try
        {
          localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
          localResultSet = a(1, arrayOfPreparedStatement, paramAccount, new com.ffusion.beans.DateTime((Calendar)localObject1, localLocale), new com.ffusion.beans.DateTime((Calendar)localObject2, localLocale), localReportCriteria, paramConnection, paramHashMap);
          paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
          if (localResultSet == null)
          {
            int i5 = paramInt2;
            return i5;
          }
          if (a(localResultSet))
          {
            i4 = 1;
            a(paramArrayList1, paramArrayList2, paramReportResult, paramReportCriteria, null, paramAccount);
            localReportResult = (ReportResult)paramArrayList2.get(paramArrayList2.size() - 1);
            Vector localVector1 = new Vector();
            i7 = a(localReportResult, paramAccount, localResultSet, 1, paramInt2, paramInt3, i3, paramBoolean, bool, localVector1, null, paramReportCriteria);
            i3 += i7 - paramInt2;
            paramInt2 = i7;
            paramBoolean = (paramBoolean) && (paramInt2 == i2);
            localCurrency1 = (Currency)localVector1.get(0);
            localInteger1 = (Integer)localVector1.get(1);
          }
        }
        finally
        {
          DBUtil.closeResultSet(localResultSet);
          DBUtil.closeStatement(arrayOfPreparedStatement[0]);
        }
        if (a(paramInt3, paramInt2, paramReportResult)) {
          return paramInt2;
        }
      }
      else if (paramInt1 == 2)
      {
        try
        {
          arrayOfPreparedStatement = new PreparedStatement[1];
          localResultSet = a(2, arrayOfPreparedStatement, paramAccount, new com.ffusion.beans.DateTime((Calendar)localObject1, localLocale), new com.ffusion.beans.DateTime((Calendar)localObject2, localLocale), localReportCriteria, paramConnection, paramHashMap);
          if (localResultSet == null)
          {
            int i6 = paramInt2;
            return i6;
          }
          if (a(localResultSet))
          {
            a(paramArrayList1, paramArrayList2, paramReportResult, paramReportCriteria, null, paramAccount);
            i4 = 1;
            localReportResult = (ReportResult)paramArrayList2.get(paramArrayList2.size() - 1);
            Vector localVector2 = new Vector();
            i7 = a(localReportResult, paramAccount, localResultSet, 2, paramInt2, paramInt3, i3, paramBoolean, bool, localVector2, null, paramReportCriteria);
            i3 += i7 - paramInt2;
            paramInt2 = i7;
            paramBoolean = (paramBoolean) && (paramInt2 == i2);
            localCurrency2 = (Currency)localVector2.get(2);
            localInteger2 = (Integer)localVector2.get(3);
          }
        }
        finally
        {
          DBUtil.closeResultSet(localResultSet);
          DBUtil.closeStatement(arrayOfPreparedStatement[0]);
        }
      }
      if (i4 != 0) {
        paramInt2 = a(localReportResult, paramAccount, localCurrency1, localCurrency2, localInteger1, localInteger2, paramInt2, paramInt3, paramInt1, paramReportCriteria, localDateTime);
      }
      if (a(paramInt3, paramInt2, paramReportResult)) {
        return paramInt2;
      }
      localDateTime.add(6, 1);
    }
    return paramInt2;
  }
  
  private static IReportResult a(SecureUser paramSecureUser, ArrayList paramArrayList, ReportCriteria paramReportCriteria, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, HashMap paramHashMap)
    throws CSILException, RptException, DCException
  {
    Locale localLocale = paramSecureUser.getLocale();
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(new Integer(697));
    localArrayList1.add(new Integer(700));
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool1 = new Boolean(localProperties.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    int i1 = a(paramReportCriteria.getReportOptions());
    Object localObject1 = 0;
    String str1 = ReportConsts.getColumnName(699, localLocale);
    String str2 = ReportConsts.getColumnName(698, localLocale);
    String str3 = ReportConsts.getColumnName(734, localLocale);
    ReportResult localReportResult1 = new ReportResult(localLocale);
    localReportResult1.init(paramReportCriteria);
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      HashMap localHashMap1 = new HashMap();
      int i2 = 0;
      HashMap localHashMap2 = new HashMap();
      Business localBusiness = (Business)paramHashMap.get("BusinessForReport");
      if (localBusiness == null) {
        throw new DCException("The Business object was not found in extra.");
      }
      int i3 = localBusiness.getLocationIdPlacementValue();
      String str4 = null;
      String str5 = null;
      if (i3 == 1)
      {
        str4 = "BankReferenceNumber";
        str5 = "SearchForNullBankRefNum";
      }
      else if (i3 == 2)
      {
        str4 = "CustomerReferenceNumber";
        str5 = "SearchForNullCustRefNum";
      }
      else
      {
        str4 = null;
      }
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      for (int i4 = 0; i4 < paramArrayList.size(); i4++)
      {
        localObject2 = null;
        localObject3 = new HashMap();
        localObject4 = (Accounts)paramArrayList.get(i4);
        localObject5 = new HashMap();
        int i5 = 0;
        Account localAccount = null;
        Object localObject8;
        Object localObject9;
        Object localObject10;
        for (int i6 = 0; i6 < ((Accounts)localObject4).size(); i6++)
        {
          Object localObject7 = null;
          localAccount = (Account)((Accounts)localObject4).get(i6);
          localObject8 = a(localAccount, paramHashMap, paramReportCriteria);
          localObject9 = (com.ffusion.beans.DateTime)((ArrayList)localObject8).get(0);
          localObject10 = (com.ffusion.beans.DateTime)((ArrayList)localObject8).get(1);
          HashMap localHashMap3 = new HashMap();
          HashMap localHashMap4 = new HashMap();
          int i7 = 0;
          ReportResult localReportResult3 = null;
          Object localObject11;
          ArrayList localArrayList2;
          Object localObject12;
          Object localObject13;
          if (paramBoolean1)
          {
            localArrayList1 = new ArrayList();
            localArrayList1.add(new Integer(697));
            localArrayList1.add(new Integer(700));
            localArrayList1.add(new Integer(701));
            localObject11 = new ArrayList();
            ((ArrayList)localObject11).add(localObject2);
            ((ArrayList)localObject11).add(localObject7);
            ((ArrayList)localObject11).add(null);
            localArrayList2 = new ArrayList();
            localObject1 = a(paramSecureUser, (com.ffusion.beans.DateTime)localObject9, (com.ffusion.beans.DateTime)localObject10, localAccount, localReportResult1, paramReportCriteria, localArrayList1, (ArrayList)localObject11, localHashMap3, localHashMap4, localArrayList2, localObject1, i1, localConnection, paramHashMap);
            localObject2 = (ReportResult)((ArrayList)localObject11).get(0);
            localObject7 = (ReportResult)((ArrayList)localObject11).get(1);
            localReportResult3 = (ReportResult)((ArrayList)localObject11).get(2);
            localObject12 = (Integer)localArrayList2.get(0);
            i7 = ((Integer)localObject12).intValue();
            if (i7 >= 1)
            {
              localObject13 = (HashMap)localHashMap3.get(localAccount.getCurrencyCode());
              localObject1 = a(localReportResult3, localAccount, null, paramReportCriteria, (HashMap)localObject13, localObject1, i1, paramHashMap, localAccount.getCurrencyCode(), null, -1);
            }
          }
          if (a(i1, localObject1, localReportResult1))
          {
            localObject11 = localReportResult1;
            return localObject11;
          }
          if (paramBoolean2)
          {
            localObject11 = localObject1;
            localArrayList2 = new ArrayList();
            localArrayList2.add(localObject2);
            localArrayList2.add(localObject7);
            localArrayList2.add(localReportResult3);
            localObject12 = new Currency("0", localAccount.getCurrencyCode(), localLocale, "CURRENCY");
            localObject13 = new Currency("0", localAccount.getCurrencyCode(), localLocale, "CURRENCY");
            int i8 = 0;
            int i9 = 0;
            int i10 = paramReportCriteria.getSortCriteria().getOrdinalOfCriteriaByName("Location") == 2 ? 1 : 0;
            Object localObject14;
            Object localObject15;
            if ((i10 != 0) && (str4 != null) && (localAccount.isMaster()))
            {
              localObject14 = paramReportCriteria.getSearchCriteria();
              localObject15 = new Properties();
              ((Properties)localObject15).putAll((Map)localObject14);
              ArrayList localArrayList3 = DCAdapter.getLocationIds(localAccount, (Calendar)localObject9, (Calendar)localObject10, localBusiness.getLocationIdPlacementValue(), paramHashMap);
              for (int i11 = 0; i11 < localArrayList3.size(); i11++)
              {
                localObject15 = new Properties();
                ((Properties)localObject15).putAll((Map)localObject14);
                String str6 = (String)localArrayList3.get(i11);
                if (str6 == null)
                {
                  ((Properties)localObject15).remove(str4);
                  ((Properties)localObject15).put(str5, "IgnoreThisValue");
                }
                else
                {
                  ((Properties)localObject15).put(str4, str6);
                }
                paramReportCriteria.setSearchCriteria((Properties)localObject15);
                Vector localVector = new Vector();
                localVector.add(localObject12);
                localVector.add(localObject13);
                localVector.add(new Integer(i8));
                localVector.add(new Integer(i9));
                localObject1 = a(localAccount, paramReportCriteria, localConnection, localReportResult1, localVector, localArrayList2, paramBoolean3, paramBoolean4, localObject1, localObject11, i1, paramHashMap);
                if (a(i1, localObject1, localReportResult1))
                {
                  ReportResult localReportResult4 = localReportResult1;
                  return localReportResult4;
                }
                localObject12 = (Currency)localVector.get(0);
                localObject13 = (Currency)localVector.get(1);
                i8 = ((Integer)localVector.get(2)).intValue();
                i9 = ((Integer)localVector.get(3)).intValue();
              }
              paramReportCriteria.setSearchCriteria((Properties)localObject14);
            }
            else
            {
              localObject14 = new Vector();
              ((Vector)localObject14).add(localObject12);
              ((Vector)localObject14).add(localObject13);
              ((Vector)localObject14).add(new Integer(i8));
              ((Vector)localObject14).add(new Integer(i9));
              localObject1 = a(localAccount, paramReportCriteria, localConnection, localReportResult1, (Vector)localObject14, localArrayList2, paramBoolean3, paramBoolean4, localObject1, localObject11, i1, paramHashMap);
              if (a(i1, localObject1, localReportResult1))
              {
                localObject15 = localReportResult1;
                return localObject15;
              }
              localObject12 = (Currency)((Vector)localObject14).get(0);
              localObject13 = (Currency)((Vector)localObject14).get(1);
              i8 = ((Integer)((Vector)localObject14).get(2)).intValue();
              i9 = ((Integer)((Vector)localObject14).get(3)).intValue();
            }
            localObject2 = (ReportResult)localArrayList2.get(0);
            localObject7 = (ReportResult)localArrayList2.get(1);
            if ((!((Currency)localObject12).equals(0.0D)) || (!((Currency)localObject13).equals(0.0D)) || (i8 != 0) || (i9 != 0)) {
              localObject1 = a((ReportResult)localObject7, localAccount, (Currency)localObject12, (Currency)localObject13, new Integer(i8), new Integer(i9), localObject1, i1, paramReportCriteria, null);
            }
            if (a(i1, localObject1, localReportResult1))
            {
              localObject14 = localReportResult1;
              return localObject14;
            }
          }
          if (a(i1, localObject1, localReportResult1))
          {
            localObject11 = localReportResult1;
            return localObject11;
          }
          if (i7 > 0) {
            i5++;
          }
          a(paramSecureUser, localHashMap3, (HashMap)localObject3, false, null, paramHashMap);
          a(paramSecureUser, paramSecureUser.getBaseCurrency(), localHashMap4, paramSecureUser.getBaseCurrency(), null, (HashMap)localObject5, paramHashMap);
        }
        Object localObject6;
        if (a(i1, localObject1, localReportResult1))
        {
          localObject6 = localReportResult1;
          return localObject6;
        }
        if (i5 > 0) {
          i2++;
        }
        if (i5 > 1)
        {
          localObject6 = null;
          boolean bool3 = "CSV".equals(localProperties.getProperty("FORMAT"));
          if (bool3) {
            localObject6 = "Balance_Summary_Reports_Bank_Total_CSV_Format";
          } else {
            localObject6 = "Balance_Summary_Reports_Bank_Total_With_Currency_Code";
          }
          localObject1 = a(paramSecureUser, (String)localObject6, (HashMap)localObject3, (ReportResult)localObject2, localObject1, i1, paramReportCriteria, paramHashMap, localAccount, null, 1);
          localObject8 = null;
          if (!isCurrencyAloneInMap((HashMap)localObject3, paramSecureUser.getBaseCurrency())) {
            localObject8 = "Balance_Summary_Reports_Bank_Total_Base_Currency_With_Currency_Conversion";
          } else {
            localObject8 = "Balance_Summary_Reports_Bank_Total_Base_Currency_No_Currency_Conversion";
          }
          localObject9 = new Object[1];
          localObject9[0] = paramSecureUser.getBaseCurrency();
          localObject10 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject8, (Object[])localObject9);
          localObject1 = a((LocalizableString)localObject10, (HashMap)localObject5, (ReportResult)localObject2, localObject1, i1, paramReportCriteria, paramHashMap, localAccount, null, paramSecureUser.getBaseCurrency(), 1);
        }
        a(paramSecureUser, (HashMap)localObject3, localHashMap1, false, null, paramHashMap);
        a(paramSecureUser, paramSecureUser.getBaseCurrency(), (HashMap)localObject5, paramSecureUser.getBaseCurrency(), null, localHashMap2, paramHashMap);
      }
      if (a(i1, localObject1, localReportResult1))
      {
        ReportResult localReportResult2 = localReportResult1;
        return localReportResult2;
      }
      if (i2 > 1)
      {
        boolean bool2 = "CSV".equals(localProperties.getProperty("FORMAT"));
        localObject2 = null;
        if (bool2) {
          localObject2 = "Balance_Summary_Reports_Grand_Total_CSV_Format";
        } else {
          localObject2 = "Balance_Summary_Reports_Grand_Total_With_Currency_Code";
        }
        localObject1 = a(paramSecureUser, (String)localObject2, localHashMap1, localReportResult1, localObject1, i1, paramReportCriteria, paramHashMap, null, null, 2);
        localObject3 = null;
        if (!isCurrencyAloneInMap(localHashMap1, paramSecureUser.getBaseCurrency())) {
          localObject3 = "Balance_Summary_Reports_Grand_Total_Base_Currency_With_Currency_Conversion";
        } else {
          localObject3 = "Balance_Summary_Reports_Grand_Total_Base_Currency_No_Currency_Conversion";
        }
        localObject4 = new Object[1];
        localObject4[0] = paramSecureUser.getBaseCurrency();
        localObject5 = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject3, (Object[])localObject4);
        localObject1 = a((LocalizableString)localObject5, localHashMap2, localReportResult1, localObject1, i1, paramReportCriteria, paramHashMap, null, null, paramSecureUser.getBaseCurrency(), 2);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing("Unable to generate report.", localCSILException);
      throw localCSILException;
    }
    catch (DCException localDCException)
    {
      DebugLog.throwing("Unable to generate report.", localDCException);
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to generate Balance Report from database.", localSQLException);
    }
    finally
    {
      localReportResult1.fini();
      DCAdapter.releaseDBConnection(localConnection);
    }
    return localReportResult1;
  }
  
  private static int a(Account paramAccount, ReportCriteria paramReportCriteria, Connection paramConnection, ReportResult paramReportResult, Vector paramVector, ArrayList paramArrayList, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws SQLException, RptException, DCException
  {
    ArrayList localArrayList1 = a(paramAccount, paramHashMap, paramReportCriteria);
    com.ffusion.beans.DateTime localDateTime1 = (com.ffusion.beans.DateTime)localArrayList1.get(0);
    com.ffusion.beans.DateTime localDateTime2 = (com.ffusion.beans.DateTime)localArrayList1.get(1);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(new Integer(697));
    localArrayList2.add(new Integer(700));
    ReportResult localReportResult1 = (ReportResult)paramArrayList.get(0);
    ReportResult localReportResult2 = (ReportResult)paramArrayList.get(1);
    ReportResult localReportResult3 = (ReportResult)paramArrayList.get(2);
    ArrayList localArrayList3 = new ArrayList(3);
    localArrayList3.add(localReportResult1);
    localArrayList3.add(localReportResult2);
    localArrayList3.add(localReportResult3);
    Currency localCurrency1 = (Currency)paramVector.get(0);
    Currency localCurrency2 = (Currency)paramVector.get(1);
    int i1 = ((Integer)paramVector.get(2)).intValue();
    int i2 = ((Integer)paramVector.get(3)).intValue();
    int i3 = 0;
    ReportCriteria localReportCriteria = new ReportCriteria();
    localReportCriteria.setLocale(paramReportCriteria.getLocale());
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add("AccountNumber");
    ReportSortCriteria localReportSortCriteria = a(paramReportCriteria.getSortCriteria(), localArrayList4);
    localReportCriteria.setSortCriteria(localReportSortCriteria);
    localReportCriteria.setSearchCriteria(paramReportCriteria.getSearchCriteria());
    localReportCriteria.setReportOptions(paramReportCriteria.getReportOptions());
    Vector localVector = new Vector();
    int i4 = 0;
    int i5;
    Currency localCurrency3;
    Integer localInteger1;
    if (paramBoolean1)
    {
      if (paramBoolean2)
      {
        localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
        i5 = a(paramReportResult, paramAccount, null, localDateTime1, localDateTime2, 1, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList2, localVector, paramInt1, paramInt3, i4, paramInt1 == paramInt2, paramConnection, paramHashMap);
        paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
        i4 += i5 - paramInt1;
        paramInt1 = i5;
        if (localVector.size() >= 2)
        {
          localCurrency3 = (Currency)localVector.get(0);
          localInteger1 = (Integer)localVector.get(1);
          localCurrency1.addAmount(localCurrency3);
          i1 += localInteger1.intValue();
        }
        if (a(paramInt3, paramInt1, paramReportResult)) {
          return paramInt1;
        }
        localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
        i5 = a(paramReportResult, paramAccount, null, localDateTime1, localDateTime2, 2, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList2, localVector, paramInt1, paramInt3, i4, paramInt1 == paramInt2, paramConnection, paramHashMap);
        paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
        i4 += i5 - paramInt1;
        paramInt1 = i5;
        if (localVector.size() >= 4)
        {
          localCurrency3 = (Currency)localVector.get(2);
          localInteger1 = (Integer)localVector.get(3);
          localCurrency2.addAmount(localCurrency3);
          i2 += localInteger1.intValue();
        }
      }
      else
      {
        localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
        i5 = a(paramReportResult, paramAccount, null, localDateTime1, localDateTime2, 2, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList2, localVector, paramInt1, paramInt3, i4, paramInt1 == paramInt2, paramConnection, paramHashMap);
        paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
        i4 += i5 - paramInt1;
        paramInt1 = i5;
        if (localVector.size() >= 4)
        {
          localCurrency3 = (Currency)localVector.get(2);
          localInteger1 = (Integer)localVector.get(3);
          localCurrency2.addAmount(localCurrency3);
          i2 += localInteger1.intValue();
        }
        if (a(paramInt3, paramInt1, paramReportResult)) {
          return paramInt1;
        }
        localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
        i5 = a(paramReportResult, paramAccount, null, localDateTime1, localDateTime2, 1, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList2, localVector, paramInt1, paramInt3, i4, paramInt1 == paramInt2, paramConnection, paramHashMap);
        paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
        i4 += i5 - paramInt1;
        paramInt1 = i5;
        if (localVector.size() >= 2)
        {
          localCurrency3 = (Currency)localVector.get(0);
          localInteger1 = (Integer)localVector.get(1);
          localCurrency1.addAmount(localCurrency3);
          i1 += localInteger1.intValue();
        }
      }
      if (a(paramInt3, paramInt1, paramReportResult)) {
        return paramInt1;
      }
    }
    else
    {
      localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
      i5 = a(paramReportResult, paramAccount, null, localDateTime1, localDateTime2, 3, localReportCriteria, paramReportCriteria, localArrayList3, localArrayList2, localVector, paramInt1, paramInt3, i4, true, paramConnection, paramHashMap);
      paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
      i4 += i5 - paramInt1;
      paramInt1 = i5;
      if (localVector.size() >= 4)
      {
        localCurrency3 = (Currency)localVector.get(0);
        localInteger1 = (Integer)localVector.get(1);
        localCurrency1.addAmount(localCurrency3);
        i1 += localInteger1.intValue();
        Currency localCurrency4 = (Currency)localVector.get(2);
        Integer localInteger2 = (Integer)localVector.get(3);
        localCurrency2.addAmount(localCurrency4);
        i2 += localInteger2.intValue();
      }
    }
    paramVector.clear();
    paramVector.add(localCurrency1);
    paramVector.add(localCurrency2);
    paramVector.add(new Integer(i1));
    paramVector.add(new Integer(i2));
    paramArrayList.remove(0);
    paramArrayList.add(0, (ReportResult)localArrayList3.get(0));
    paramArrayList.remove(1);
    paramArrayList.add(1, (ReportResult)localArrayList3.get(1));
    paramArrayList.remove(2);
    paramArrayList.add(2, (ReportResult)localArrayList3.get(2));
    return paramInt1;
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, HashMap paramHashMap1, int paramInt1, int paramInt2, HashMap paramHashMap2, String paramString, LocalizableString paramLocalizableString, int paramInt3)
    throws RptException
  {
    int i1 = 0;
    Locale localLocale = paramReportResult.getLocale();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    Set localSet = paramHashMap1.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (String)localIterator.next();
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(Integer.parseInt((String)localObject1));
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("The BAI Type code " + (String)localObject1 + " does not have a type info associated with " + "it in the BAI Type Info cache.");
      }
      continue;
      Object localObject2 = paramHashMap1.get(localObject1);
      if (localObject2 != null)
      {
        Integer localInteger = new Integer((String)localObject1);
        if ((localBAITypeCodeInfo.getLevel() == 0) && (localBAITypeCodeInfo.getSubLevel().equals("Regular"))) {
          localHashMap1.put(localInteger, localObject2);
        } else if ((localBAITypeCodeInfo.getCode() == 100) || (localBAITypeCodeInfo.getCode() == 400)) {
          localHashMap1.put(localInteger, localObject2);
        } else if ((localBAITypeCodeInfo.getSubLevel().equals("Month-to-date")) || (localBAITypeCodeInfo.getSubLevel().equals("Year-to-date"))) {
          localHashMap2.put(localInteger, localObject2);
        } else if (localBAITypeCodeInfo.getLevel() == 1) {
          localHashMap3.put(localInteger, localObject2);
        }
      }
    }
    Object localObject1 = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(((Properties)localObject1).getProperty("FORMAT"));
    if (bool)
    {
      paramInt1 = a(paramReportResult, paramAccount, paramDateTime, paramReportCriteria, localHashMap1, paramInt1, paramInt2, paramString, paramLocalizableString, paramInt3);
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
      paramInt1 = jdField_do(paramReportResult, paramAccount, paramDateTime, paramReportCriteria, localHashMap2, paramInt1, paramInt2, paramString, paramLocalizableString, paramInt3);
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
      paramInt1 = jdField_if(paramReportResult, paramAccount, paramDateTime, paramReportCriteria, localHashMap3, paramInt1, paramInt2, paramString, paramLocalizableString, paramInt3);
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
    }
    else
    {
      paramInt1 = a(paramReportResult, paramReportCriteria, localHashMap1, ReportConsts.getText(781, localLocale), paramInt1, paramInt2);
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
      paramInt1 = jdField_if(paramReportResult, paramReportCriteria, localHashMap2, ReportConsts.getText(782, localLocale), paramInt1, paramInt2);
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
      paramInt1 = jdField_do(paramReportResult, paramReportCriteria, localHashMap3, ReportConsts.getText(783, localLocale), paramInt1, paramInt2);
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
    }
    return paramInt1;
  }
  
  private static int a(ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap, String paramString, int paramInt1, int paramInt2)
    throws RptException
  {
    if (paramHashMap.isEmpty()) {
      return paramInt1;
    }
    String str = (String)paramReportCriteria.getSearchCriteria().get("DataClassification");
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramString);
    a(localReportResult, c.length, -1);
    a.a(localReportResult, paramReportResult.getLocale(), c);
    int i1 = 1;
    HashMap localHashMap = new HashMap();
    boolean bool;
    if (str.equals("P"))
    {
      bool = false;
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 40, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 10, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 57, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 100, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 400, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 15, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 72, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 74, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 45, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    else if (str.equals("I"))
    {
      bool = false;
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 40, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 10, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 100, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 400, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 70, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 72, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 74, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 30, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 60, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    ArrayList localArrayList = new ArrayList(paramHashMap.keySet());
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      if (!localHashMap.containsKey(localInteger))
      {
        Object localObject = paramHashMap.get(localInteger);
        a(localReportResult, paramReportCriteria, i1, localInteger.intValue(), localObject, paramReportResult.getLocale());
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    return paramInt1;
  }
  
  private static int jdField_if(ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap, String paramString, int paramInt1, int paramInt2)
    throws RptException
  {
    if (paramHashMap.isEmpty()) {
      return paramInt1;
    }
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramString);
    a(localReportResult, c.length, -1);
    a.a(localReportResult, paramReportResult.getLocale(), c);
    ArrayList localArrayList = new ArrayList(paramHashMap.keySet());
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    int i1 = 1;
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      Object localObject = paramHashMap.get(localInteger);
      a(localReportResult, paramReportCriteria, i1, localInteger.intValue(), localObject, paramReportResult.getLocale());
      i1++;
      paramInt1++;
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
    }
    return paramInt1;
  }
  
  private static int jdField_do(ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap, String paramString, int paramInt1, int paramInt2)
    throws RptException
  {
    if (paramHashMap.isEmpty()) {
      return paramInt1;
    }
    String str = (String)paramReportCriteria.getSearchCriteria().get("DataClassification");
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, paramString);
    a(localReportResult, c.length, -1);
    a.a(localReportResult, paramReportResult.getLocale(), c);
    boolean bool = false;
    HashMap localHashMap = new HashMap();
    int i1;
    ArrayList localArrayList;
    Iterator localIterator;
    Integer localInteger;
    Object localObject;
    if (str.equals("P"))
    {
      i1 = 1;
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 110, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 140, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 170, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 190, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 270, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      localArrayList = new ArrayList(paramHashMap.keySet());
      Collections.sort(localArrayList);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        if ((!localHashMap.containsKey(localInteger)) && (a(localInteger.intValue())))
        {
          localObject = paramHashMap.get(localInteger);
          a(localReportResult, paramReportCriteria, i1, localInteger.intValue(), localObject, paramReportResult.getLocale());
          paramInt1++;
          i1++;
          if (a(paramInt2, paramInt1, null)) {
            return paramInt1;
          }
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 450, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 470, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 490, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramReportCriteria, i1, paramHashMap, 570, localHashMap, paramReportResult.getLocale());
      if (bool)
      {
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      localArrayList = new ArrayList(paramHashMap.keySet());
      Collections.sort(localArrayList);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        if ((!localHashMap.containsKey(localInteger)) && (!a(localInteger.intValue())))
        {
          localObject = paramHashMap.get(localInteger);
          a(localReportResult, paramReportCriteria, i1, localInteger.intValue(), localObject, paramReportResult.getLocale());
          paramInt1++;
          i1++;
          if (a(paramInt2, paramInt1, null)) {
            return paramInt1;
          }
        }
      }
    }
    else if (str.equals("I"))
    {
      i1 = 1;
      localArrayList = new ArrayList(paramHashMap.keySet());
      Collections.sort(localArrayList);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        localObject = paramHashMap.get(localInteger);
        a(localReportResult, paramReportCriteria, i1, localInteger.intValue(), localObject, paramReportResult.getLocale());
        paramInt1++;
        i1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    return paramInt1;
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, HashMap paramHashMap, int paramInt1, int paramInt2, String paramString, LocalizableString paramLocalizableString, int paramInt3)
    throws RptException
  {
    if (paramHashMap.isEmpty()) {
      return paramInt1;
    }
    String str = (String)paramReportCriteria.getSearchCriteria().get("DataClassification");
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, af.length, -1);
    a.a(localReportResult, paramReportResult.getLocale(), af);
    HashMap localHashMap = new HashMap();
    boolean bool = false;
    if (str.equals("P"))
    {
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 40, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 10, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 57, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 100, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 400, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 15, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 72, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 74, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 45, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    else if (str.equals("I"))
    {
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 40, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 10, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 100, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 400, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 70, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 72, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 74, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 30, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 60, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    ArrayList localArrayList = new ArrayList(paramHashMap.keySet());
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      if (!localHashMap.containsKey(localInteger))
      {
        Object localObject = paramHashMap.get(localInteger);
        jdField_if(localReportResult, paramAccount, paramDateTime, paramReportCriteria, localInteger.intValue(), localObject, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    return paramInt1;
  }
  
  private static int jdField_do(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, HashMap paramHashMap, int paramInt1, int paramInt2, String paramString, LocalizableString paramLocalizableString, int paramInt3)
    throws RptException
  {
    if (paramHashMap.isEmpty()) {
      return paramInt1;
    }
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, af.length, -1);
    a.a(localReportResult, paramReportResult.getLocale(), af);
    ArrayList localArrayList = new ArrayList(paramHashMap.keySet());
    Collections.sort(localArrayList);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      Object localObject = paramHashMap.get(localInteger);
      jdField_if(localReportResult, paramAccount, paramDateTime, paramReportCriteria, localInteger.intValue(), localObject, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      paramInt1++;
      if (a(paramInt2, paramInt1, null)) {
        return paramInt1;
      }
    }
    return paramInt1;
  }
  
  private static int jdField_if(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, HashMap paramHashMap, int paramInt1, int paramInt2, String paramString, LocalizableString paramLocalizableString, int paramInt3)
    throws RptException
  {
    if (paramHashMap.isEmpty()) {
      return paramInt1;
    }
    String str = (String)paramReportCriteria.getSearchCriteria().get("DataClassification");
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, af.length, -1);
    a.a(localReportResult, paramReportResult.getLocale(), af);
    HashMap localHashMap = new HashMap();
    boolean bool = false;
    ArrayList localArrayList;
    Iterator localIterator;
    Integer localInteger;
    Object localObject;
    if (str.equals("P"))
    {
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 110, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 140, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 170, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 190, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 270, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      localArrayList = new ArrayList(paramHashMap.keySet());
      Collections.sort(localArrayList);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        if ((!localHashMap.containsKey(localInteger)) && (a(localInteger.intValue())))
        {
          localObject = paramHashMap.get(localInteger);
          jdField_if(localReportResult, paramAccount, paramDateTime, paramReportCriteria, localInteger.intValue(), localObject, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
          paramInt1++;
          if (a(paramInt2, paramInt1, null)) {
            return paramInt1;
          }
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 450, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 470, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 490, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      bool = a(localReportResult, paramAccount, paramDateTime, paramReportCriteria, paramHashMap, 570, localHashMap, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
      if (bool)
      {
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
      localArrayList = new ArrayList(paramHashMap.keySet());
      Collections.sort(localArrayList);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        if ((!localHashMap.containsKey(localInteger)) && (!a(localInteger.intValue())))
        {
          localObject = paramHashMap.get(localInteger);
          jdField_if(localReportResult, paramAccount, paramDateTime, paramReportCriteria, localInteger.intValue(), localObject, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
          paramInt1++;
          if (a(paramInt2, paramInt1, null)) {
            return paramInt1;
          }
        }
      }
    }
    else if (str.equals("I"))
    {
      localArrayList = new ArrayList(paramHashMap.keySet());
      Collections.sort(localArrayList);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localInteger = (Integer)localIterator.next();
        localObject = paramHashMap.get(localInteger);
        jdField_if(localReportResult, paramAccount, paramDateTime, paramReportCriteria, localInteger.intValue(), localObject, paramReportResult.getLocale(), paramString, paramLocalizableString, paramInt3);
        paramInt1++;
        if (a(paramInt2, paramInt1, null)) {
          return paramInt1;
        }
      }
    }
    return paramInt1;
  }
  
  private static void a(ReportResult paramReportResult, ReportCriteria paramReportCriteria, int paramInt1, int paramInt2, Object paramObject, Locale paramLocale)
    throws RptException
  {
    String str1 = null;
    try
    {
      BAITypeCodeInfo localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramInt2);
      if (localBAITypeCodeInfo != null) {
        str1 = localBAITypeCodeInfo.getDescription(paramLocale);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("The BAI Type code " + paramInt2 + " does not have a type info associated with " + "it in the BAI Type Info cache.");
      return;
    }
    int i1 = -1;
    String str2 = null;
    if ((paramObject instanceof Date))
    {
      localObject = paramReportCriteria.getReportOptions().getProperty("TIMEFORMAT");
      str2 = DateFormatUtil.getFormatter((String)localObject).format((Date)paramObject);
    }
    else if ((paramObject instanceof SummaryValue))
    {
      i1 = ((SummaryValue)paramObject)._itemCount;
      str2 = ((SummaryValue)paramObject)._amount.toString();
    }
    else
    {
      str2 = paramObject.toString();
    }
    Object localObject = new ReportDataItems(paramReportResult.getLocale());
    ((ReportDataItems)localObject).add().setData(str1);
    ((ReportDataItems)localObject).add().setData(str2);
    if (i1 != -1)
    {
      ((ReportDataItems)localObject).add().setData(ReportConsts.getText(780, paramLocale));
      ((ReportDataItems)localObject).add().setData(Integer.toString(i1));
    }
    else
    {
      ((ReportDataItems)localObject).add().setData(null);
      ((ReportDataItems)localObject).add().setData(null);
    }
    a(paramReportResult, (ReportDataItems)localObject, paramInt1);
  }
  
  private static void jdField_if(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, int paramInt1, Object paramObject, Locale paramLocale, String paramString, LocalizableString paramLocalizableString, int paramInt2)
    throws RptException
  {
    if (paramInt2 != -1)
    {
      a(paramReportResult, paramAccount, paramDateTime, paramReportCriteria, paramInt1, paramObject, paramLocale, paramString, paramLocalizableString, paramInt2);
      return;
    }
    int i1 = -1;
    String str1 = null;
    if ((paramObject instanceof Date))
    {
      str2 = paramReportCriteria.getReportOptions().getProperty("TIMEFORMAT");
      str1 = DateFormatUtil.getFormatter(str2).format((Date)paramObject);
    }
    else if ((paramObject instanceof SummaryValue))
    {
      i1 = ((SummaryValue)paramObject)._itemCount;
      str1 = ((SummaryValue)paramObject)._amount.getCurrencyStringNoSymbol();
    }
    else if ((paramObject instanceof Currency))
    {
      str1 = ((Currency)paramObject).getCurrencyStringNoSymbol();
    }
    else
    {
      str1 = paramObject.toString();
    }
    String str2 = null;
    try
    {
      BAITypeCodeInfo localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramInt1);
      if (localBAITypeCodeInfo != null) {
        str2 = localBAITypeCodeInfo.getDescription(paramLocale);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("The BAI Type code " + paramInt1 + " does not have a type info associated with " + "it in the BAI Type Info cache.");
      return;
    }
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str3 = localProperties.getProperty("DATEFORMAT");
    String str4 = null;
    if (paramDateTime == null)
    {
      localObject = (String)paramReportCriteria.getSearchCriteria().get("Date Range Type");
      if ("Relative".equals(localObject)) {
        str4 = paramReportCriteria.getDisplayValue("Date Range");
      } else {
        str4 = a(paramReportCriteria, str3, paramLocale);
      }
    }
    else
    {
      if (str3 != null)
      {
        paramDateTime = (com.ffusion.beans.DateTime)paramDateTime.clone();
        paramDateTime.setFormat(str3);
      }
      str4 = paramDateTime.toString();
    }
    Object localObject = new ReportDataItems(paramReportResult.getLocale());
    ((ReportDataItems)localObject).add().setData(paramAccount.getRoutingNum());
    ((ReportDataItems)localObject).add().setData(paramAccount.getDisplayText("CSV"));
    ((ReportDataItems)localObject).add().setData(str4);
    ((ReportDataItems)localObject).add().setData(Integer.toString(paramInt1));
    ((ReportDataItems)localObject).add().setData(str2);
    ((ReportDataItems)localObject).add().setData(str1);
    if (i1 != -1) {
      ((ReportDataItems)localObject).add().setData(Integer.toString(i1));
    } else {
      ((ReportDataItems)localObject).add().setData(null);
    }
    ((ReportDataItems)localObject).add().setData(null);
    ((ReportDataItems)localObject).add().setData(null);
    ((ReportDataItems)localObject).add().setData(null);
    ((ReportDataItems)localObject).add().setData(paramString);
    ((ReportDataItems)localObject).add().setData(null);
    ((ReportDataItems)localObject).add().setData(null);
    ((ReportDataItems)localObject).add().setData(null);
    a(paramReportResult, (ReportDataItems)localObject, -1);
  }
  
  private static void a(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, int paramInt1, Object paramObject, Locale paramLocale, String paramString, LocalizableString paramLocalizableString, int paramInt2)
    throws RptException
  {
    int i1 = paramInt2 == 1 ? 1 : 0;
    int i2 = paramInt2 == 2 ? 1 : 0;
    int i3 = paramInt2 == 3 ? 1 : 0;
    Properties localProperties = paramReportCriteria.getReportOptions();
    int i4 = -1;
    String str1 = null;
    if ((paramObject instanceof Date))
    {
      str2 = paramReportCriteria.getReportOptions().getProperty("TIMEFORMAT");
      str1 = DateFormatUtil.getFormatter(str2).format((Date)paramObject);
    }
    else if ((paramObject instanceof SummaryValue))
    {
      i4 = ((SummaryValue)paramObject)._itemCount;
      str1 = ((SummaryValue)paramObject)._amount.getCurrencyStringNoSymbol();
    }
    else if ((paramObject instanceof Currency))
    {
      str1 = ((Currency)paramObject).getCurrencyStringNoSymbol();
    }
    else
    {
      str1 = paramObject.toString();
    }
    String str2 = null;
    try
    {
      BAITypeCodeInfo localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramInt1);
      if (localBAITypeCodeInfo != null) {
        str2 = localBAITypeCodeInfo.getDescription(paramLocale);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("The BAI Type code " + paramInt1 + " does not have a type info associated with " + "it in the BAI Type Info cache.");
      return;
    }
    ReportDataItems localReportDataItems = new ReportDataItems(paramReportResult.getLocale());
    if (((i1 != 0) || (i3 != 0)) && (paramAccount != null)) {
      localReportDataItems.add().setData(paramAccount.getRoutingNum());
    } else {
      localReportDataItems.add().setData(null);
    }
    if ((i3 != 0) && (paramAccount != null)) {
      localReportDataItems.add().setData(paramAccount.getDisplayText("CSV"));
    } else {
      localReportDataItems.add().setData(null);
    }
    String str3 = localProperties.getProperty("DATEFORMAT");
    String str4 = null;
    if (paramDateTime == null)
    {
      String str5 = (String)paramReportCriteria.getSearchCriteria().get("Date Range Type");
      if ("Relative".equals(str5)) {
        str4 = paramReportCriteria.getDisplayValue("Date Range");
      } else {
        str4 = a(paramReportCriteria, str3, paramLocale);
      }
    }
    else
    {
      if (str3 != null)
      {
        paramDateTime = (com.ffusion.beans.DateTime)paramDateTime.clone();
        paramDateTime.setFormat(str3);
      }
      str4 = paramDateTime.toString();
    }
    localReportDataItems.add().setData(str4);
    localReportDataItems.add().setData(paramLocalizableString == null ? null : paramLocalizableString.localize(paramLocale));
    localReportDataItems.add().setData(str2);
    localReportDataItems.add().setData(str1);
    if (i4 != -1) {
      localReportDataItems.add().setData(Integer.toString(i4));
    } else {
      localReportDataItems.add().setData(null);
    }
    localReportDataItems.add().setData(null);
    localReportDataItems.add().setData(null);
    localReportDataItems.add().setData(null);
    localReportDataItems.add().setData(paramString);
    localReportDataItems.add().setData(null);
    localReportDataItems.add().setData(null);
    localReportDataItems.add().setData(null);
    a(paramReportResult, localReportDataItems, -1);
  }
  
  private static String a(ReportCriteria paramReportCriteria, String paramString, Locale paramLocale)
  {
    String str1 = paramReportCriteria.getDisplayValue("StartDate");
    String str2 = paramReportCriteria.getDisplayValue("EndDate");
    com.ffusion.beans.DateTime localDateTime1 = null;
    com.ffusion.beans.DateTime localDateTime2 = null;
    try
    {
      if (str1 != null)
      {
        localDateTime1 = a(str1, paramString, paramLocale);
        if (localDateTime1 != null) {
          localDateTime1.setFormat(paramString);
        }
      }
    }
    catch (ParseException localParseException1) {}
    try
    {
      if (str2 != null)
      {
        localDateTime2 = a(str2, paramString, paramLocale);
        if (localDateTime2 != null) {
          localDateTime2.setFormat(paramString);
        }
      }
    }
    catch (ParseException localParseException2) {}
    String str3 = null;
    ArrayList localArrayList = new ArrayList();
    if ((localDateTime1 != null) && (localDateTime2 != null) && (localDateTime1.isSameDayInYearAs(localDateTime2)))
    {
      str3 = "Balance_Reports_Date_Range_Same_Start_End_Date_Format";
      localArrayList.add(localDateTime1);
    }
    else if ((localDateTime1 != null) && (localDateTime2 != null))
    {
      str3 = "Balance_Reports_Date_Range_Start_And_End_Date_Format";
      localArrayList.add(localDateTime1);
      localArrayList.add(localDateTime2);
    }
    else if (localDateTime1 != null)
    {
      str3 = "Balance_Reports_Date_Range_Start_Date_Only_Format";
      localArrayList.add(localDateTime1);
    }
    else if (localDateTime2 != null)
    {
      str3 = "Balance_Reports_Date_Range_End_Date_Only_Format";
      localArrayList.add(localDateTime2);
    }
    String str4 = "";
    if (str3 != null)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", str3, localArrayList.toArray());
      str4 = (String)localLocalizableString.localize(paramLocale);
    }
    return str4;
  }
  
  private static boolean a(int paramInt)
  {
    try
    {
      BAITypeCodeInfo localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(paramInt);
      if (localBAITypeCodeInfo != null)
      {
        if (localBAITypeCodeInfo.getTransaction() == 1) {
          return true;
        }
        if (localBAITypeCodeInfo.getTransaction() == 2) {
          return false;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("The BAI Type code " + paramInt + " does not have a type info associated with " + "it in the BAI Type Info cache.");
    }
    return paramInt < 400;
  }
  
  private static boolean a(ReportResult paramReportResult, ReportCriteria paramReportCriteria, int paramInt1, HashMap paramHashMap1, int paramInt2, HashMap paramHashMap2, Locale paramLocale)
    throws RptException
  {
    Integer localInteger = new Integer(paramInt2);
    if (!paramHashMap1.containsKey(localInteger)) {
      return false;
    }
    Object localObject = paramHashMap1.get(localInteger);
    a(paramReportResult, paramReportCriteria, paramInt1, paramInt2, localObject, paramLocale);
    paramHashMap2.put(localInteger, localObject);
    return true;
  }
  
  private static boolean a(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ReportCriteria paramReportCriteria, HashMap paramHashMap1, int paramInt1, HashMap paramHashMap2, Locale paramLocale, String paramString, LocalizableString paramLocalizableString, int paramInt2)
    throws RptException
  {
    Integer localInteger = new Integer(paramInt1);
    if (!paramHashMap1.containsKey(localInteger)) {
      return false;
    }
    Object localObject = paramHashMap1.get(localInteger);
    jdField_if(paramReportResult, paramAccount, paramDateTime, paramReportCriteria, paramInt1, localObject, paramLocale, paramString, paramLocalizableString, paramInt2);
    paramHashMap2.put(localInteger, localObject);
    return true;
  }
  
  private static boolean a(ArrayList paramArrayList)
  {
    boolean bool = false;
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      Object localObject = paramArrayList.get(i1);
      if (localObject != null)
      {
        bool = true;
        break;
      }
    }
    return bool;
  }
  
  private static boolean a(HashMap paramHashMap)
  {
    boolean bool = false;
    if (paramHashMap == null) {
      return false;
    }
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = paramHashMap.get(localIterator.next());
      if (localObject != null)
      {
        bool = true;
        break;
      }
    }
    return bool;
  }
  
  private static ArrayList a(Account paramAccount, Properties paramProperties, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = DCAdapter.getHistoryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (localArrayList2.size() != 0)
    {
      localArrayList3 = jdField_if(paramAccount, paramProperties, "Account History", (HashMap)localArrayList2.get(0), paramHashMap);
      if (localArrayList3.size() != 0) {
        localArrayList1.add(localArrayList3);
      } else {
        localArrayList1.add(null);
      }
    }
    else
    {
      localArrayList1.add(null);
    }
    ArrayList localArrayList3 = DCAdapter.getSummaryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (localArrayList3.size() != 0)
    {
      localArrayList4 = jdField_if(paramAccount, paramProperties, "Account Summary", (HashMap)localArrayList3.get(0), paramHashMap);
      if (localArrayList4.size() != 0) {
        localArrayList1.add(localArrayList4);
      } else {
        localArrayList1.add(null);
      }
    }
    else
    {
      localArrayList1.add(null);
    }
    ArrayList localArrayList4 = DCAdapter.getExtendedSummaryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    Object localObject5;
    for (int i1 = 0; i1 < localArrayList4.size(); i1++)
    {
      localObject2 = (HashMap)localArrayList4.get(i1);
      localObject3 = ((HashMap)localObject2).keySet();
      localObject4 = ((Set)localObject3).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = ((Iterator)localObject4).next();
        Object localObject6 = ((HashMap)localObject2).get(localObject5);
        if ((localObject6 instanceof SummaryValue)) {
          ((SummaryValue)localObject6).setItemCount(-1);
        }
      }
    }
    if (localArrayList4.size() != 0)
    {
      localObject1 = jdField_if(paramAccount, paramProperties, "Extended Account Summary", (HashMap)localArrayList4.get(0), paramHashMap);
      if (((ArrayList)localObject1).size() != 0) {
        localArrayList1.add(localObject1);
      } else {
        localArrayList1.add(null);
      }
    }
    else
    {
      localArrayList1.add(null);
    }
    Object localObject1 = new DisbursementAccount();
    ((DisbursementAccount)localObject1).setAccountID(paramAccount.getID());
    ((DisbursementAccount)localObject1).setBankID(paramAccount.getBankID());
    ((DisbursementAccount)localObject1).setRoutingNumber(paramAccount.getRoutingNum());
    ((DisbursementAccount)localObject1).setCurrencyType(paramAccount.getCurrencyCode());
    Object localObject2 = DCAdapter.getDisbursementSummaryMapList((DisbursementAccount)localObject1, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (((ArrayList)localObject2).size() != 0)
    {
      localObject3 = jdField_if(paramAccount, paramProperties, "Disbursement Summary", (HashMap)((ArrayList)localObject2).get(0), paramHashMap);
      if (((ArrayList)localObject3).size() != 0) {
        localArrayList1.add(localObject3);
      } else {
        localArrayList1.add(null);
      }
    }
    else
    {
      localArrayList1.add(null);
    }
    Object localObject3 = new LockboxAccount();
    ((LockboxAccount)localObject3).setAccountID(paramAccount.getID());
    ((LockboxAccount)localObject3).setBankID(paramAccount.getBankID());
    ((LockboxAccount)localObject3).setRoutingNumber(paramAccount.getRoutingNum());
    ((LockboxAccount)localObject3).setCurrencyType(paramAccount.getCurrencyCode());
    Object localObject4 = DCAdapter.getLockboxSummaryMapList((LockboxAccount)localObject3, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (((ArrayList)localObject4).size() != 0)
    {
      localObject5 = jdField_if(paramAccount, paramProperties, "Lockbox Summary", (HashMap)((ArrayList)localObject4).get(0), paramHashMap);
      if (((ArrayList)localObject5).size() != 0) {
        localArrayList1.add(localObject5);
      } else {
        localArrayList1.add(null);
      }
    }
    else
    {
      localArrayList1.add(null);
    }
    return localArrayList1;
  }
  
  private static HashMap jdField_if(Account paramAccount, Properties paramProperties, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    HashMap localHashMap1 = new HashMap();
    ArrayList localArrayList1 = DCAdapter.getHistoryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (localArrayList1.size() != 0)
    {
      localObject1 = a(paramAccount, paramProperties, "Account History", (HashMap)localArrayList1.get(0), paramHashMap);
      localHashMap1.putAll((Map)localObject1);
    }
    Object localObject1 = DCAdapter.getSummaryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (((ArrayList)localObject1).size() != 0)
    {
      localObject2 = a(paramAccount, paramProperties, "Account Summary", (HashMap)((ArrayList)localObject1).get(0), paramHashMap);
      localHashMap1.putAll((Map)localObject2);
    }
    Object localObject2 = DCAdapter.getExtendedSummaryMapList(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (((ArrayList)localObject2).size() != 0)
    {
      localObject3 = a(paramAccount, paramProperties, "Extended Account Summary", (HashMap)((ArrayList)localObject2).get(0), paramHashMap);
      localHashMap1.putAll((Map)localObject3);
    }
    Object localObject3 = new DisbursementAccount();
    ((DisbursementAccount)localObject3).setAccountID(paramAccount.getID());
    ((DisbursementAccount)localObject3).setBankID(paramAccount.getBankID());
    ((DisbursementAccount)localObject3).setRoutingNumber(paramAccount.getRoutingNum());
    ((DisbursementAccount)localObject3).setCurrencyType(paramAccount.getCurrencyCode());
    ArrayList localArrayList2 = DCAdapter.getDisbursementSummaryMapList((DisbursementAccount)localObject3, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (localArrayList2.size() != 0)
    {
      localObject4 = a(paramAccount, paramProperties, "Disbursement Summary", (HashMap)localArrayList2.get(0), paramHashMap);
      localHashMap1.putAll((Map)localObject4);
    }
    Object localObject4 = new LockboxAccount();
    ((LockboxAccount)localObject4).setAccountID(paramAccount.getID());
    ((LockboxAccount)localObject4).setBankID(paramAccount.getBankID());
    ((LockboxAccount)localObject4).setRoutingNumber(paramAccount.getRoutingNum());
    ((LockboxAccount)localObject4).setCurrencyType(paramAccount.getCurrencyCode());
    ArrayList localArrayList3 = DCAdapter.getLockboxSummaryMapList((LockboxAccount)localObject4, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    if (localArrayList3.size() != 0)
    {
      HashMap localHashMap2 = a(paramAccount, paramProperties, "Lockbox Summary", (HashMap)localArrayList3.get(0), paramHashMap);
      localHashMap1.putAll(localHashMap2);
    }
    return localHashMap1;
  }
  
  private static ArrayList jdField_if(Account paramAccount, Properties paramProperties, String paramString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    ArrayList localArrayList = new ArrayList();
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator = localSet.iterator();
    String str1 = (String)paramHashMap2.get("DataClassification");
    if (str1 == null) {
      str1 = "P";
    }
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Object localObject1 = localEntry.getKey();
      int i1 = -1;
      String str2 = null;
      if ((localObject1 instanceof Integer))
      {
        i1 = ((Integer)localObject1).intValue();
      }
      else
      {
        str2 = (String)localObject1;
        str2 = str2.toUpperCase();
        try
        {
          i1 = DataConsolidator.getBAICode(paramString, str2);
        }
        catch (CSILException localCSILException1)
        {
          DebugLog.log("Unable to map the following entry to a BAI type code:  Module Name= " + paramString + ", " + "Column Name = " + str2);
        }
        continue;
      }
      Object localObject2;
      if ((i1 == 10) && (!str1.equals("I")))
      {
        localObject2 = paramProperties.getProperty("Show Prev-day Opening Ledger", paramAccount.getShowPreviousDayOpeningLedger());
        if ((localObject2 != null) && (((String)localObject2).equals("N"))) {}
      }
      else
      {
        localObject2 = localEntry.getValue();
        BAITypeCodeInfo localBAITypeCodeInfo = null;
        try
        {
          localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i1);
        }
        catch (CSILException localCSILException2)
        {
          DebugLog.log("The current entry (Module Name= " + paramString + ", " + "Column Name = " + str2 + ") " + "that is mapped to BAI Type code " + i1 + " does not have a type info associated with " + "it in the BAI Type Info cache.");
        }
        continue;
        String str3 = localBAITypeCodeInfo.getDescription();
        int i2 = localBAITypeCodeInfo.getLevel();
        HashMap localHashMap = null;
        if (paramString.equals("Extended Account Summary"))
        {
          if (i2 == 0)
          {
            localHashMap = (HashMap)ArrayListUtil.getValueFromList(localArrayList, 1, null);
            if (localHashMap == null)
            {
              localHashMap = new HashMap();
              ArrayListUtil.setValueInList(localArrayList, 1, localHashMap, null);
            }
          }
          else if (i2 == 1)
          {
            localHashMap = (HashMap)ArrayListUtil.getValueFromList(localArrayList, 3, null);
            if (localHashMap == null)
            {
              localHashMap = new HashMap();
              ArrayListUtil.setValueInList(localArrayList, 3, localHashMap, null);
            }
          }
        }
        else if (i2 == 0)
        {
          localHashMap = (HashMap)ArrayListUtil.getValueFromList(localArrayList, 0, null);
          if (localHashMap == null)
          {
            localHashMap = new HashMap();
            ArrayListUtil.setValueInList(localArrayList, 0, localHashMap, null);
          }
        }
        else if (i2 == 1)
        {
          localHashMap = (HashMap)ArrayListUtil.getValueFromList(localArrayList, 2, null);
          if (localHashMap == null)
          {
            localHashMap = new HashMap();
            ArrayListUtil.setValueInList(localArrayList, 2, localHashMap, null);
          }
        }
        if (localHashMap == null) {
          DebugLog.log("The bai type code " + i1 + " for module " + paramString + " is mapped to an invalid " + "level (" + i2 + ")for use in the balance summary/detail report.");
        } else {
          localHashMap.put(str3, localObject2);
        }
      }
    }
    return localArrayList;
  }
  
  private static HashMap a(Account paramAccount, Properties paramProperties, String paramString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    HashMap localHashMap = new HashMap();
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator = localSet.iterator();
    String str1 = (String)paramHashMap2.get("DataClassification");
    if (str1 == null) {
      str1 = "P";
    }
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Object localObject1 = localEntry.getKey();
      int i1 = -1;
      String str2 = null;
      if ((localObject1 instanceof Integer))
      {
        i1 = ((Integer)localObject1).intValue();
      }
      else
      {
        str2 = (String)localObject1;
        str2 = str2.toUpperCase();
        try
        {
          i1 = DataConsolidator.getBAICode(paramString, str2);
        }
        catch (CSILException localCSILException)
        {
          DebugLog.log("Unable to map the following entry to a BAI type code:  Module Name= " + paramString + ", " + "Column Name = " + str2);
        }
        continue;
      }
      Object localObject2;
      if ((i1 == 10) && (!str1.equals("I")))
      {
        localObject2 = paramProperties == null ? paramAccount.getShowPreviousDayOpeningLedger() : paramProperties.getProperty("Show Prev-day Opening Ledger", paramAccount.getShowPreviousDayOpeningLedger());
        if ((localObject2 != null) && (((String)localObject2).equals("N"))) {}
      }
      else
      {
        localObject2 = localEntry.getValue();
        if (i1 != -1) {
          localHashMap.put(Integer.toString(i1), localObject2);
        }
      }
    }
    return localHashMap;
  }
  
  private static ArrayList a(ArrayList paramArrayList, HashMap paramHashMap1, HashMap paramHashMap2)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      ArrayList localArrayList2 = (ArrayList)paramArrayList.get(i1);
      Account localAccount = (Account)localArrayList2.get(0);
      String str = localAccount.getRoutingNum();
      Calendar localCalendar1 = (Calendar)paramHashMap1.get(str);
      Calendar localCalendar2 = (Calendar)paramHashMap2.get(str);
      if (i1 == 0)
      {
        localObject1 = localCalendar1;
        localObject2 = localCalendar2;
      }
      else
      {
        if ((localObject1 == null) && (localObject2 == null)) {
          break;
        }
        if ((localObject1 != null) && ((localCalendar1 == null) || (localCalendar1.before(localObject1)))) {
          localObject1 = localCalendar1;
        }
        if ((localObject2 != null) && ((localCalendar2 == null) || (localCalendar2.after(localObject2)))) {
          localObject2 = localCalendar2;
        }
      }
    }
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(localObject1);
    localArrayList1.add(localObject2);
    return localArrayList1;
  }
  
  private static void a(String paramString, HashMap paramHashMap1, HashMap paramHashMap2, Locale paramLocale)
    throws DCException
  {
    HashMap localHashMap = (HashMap)paramHashMap1.get(paramString);
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramHashMap1.put(paramString, localHashMap);
    }
    Set localSet = paramHashMap2.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      Object localObject = localEntry.getValue();
      a(paramString, str, localHashMap, localObject, paramLocale);
    }
  }
  
  private static HashMap a(SecureUser paramSecureUser, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    HashMap localHashMap1 = new HashMap();
    String str1 = paramSecureUser.getBaseCurrency();
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str2 = (String)localEntry.getKey();
      HashMap localHashMap2 = (HashMap)localEntry.getValue();
      if (localHashMap2 != null) {
        a(paramSecureUser, str2, localHashMap2, str1, paramDateTime, localHashMap1, paramHashMap2);
      }
    }
    return localHashMap1;
  }
  
  private static void a(SecureUser paramSecureUser, String paramString1, HashMap paramHashMap1, String paramString2, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap2, HashMap paramHashMap3)
    throws CSILException
  {
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Object localObject1 = localEntry.getValue();
      Object localObject2;
      Object localObject3;
      if ((localObject1 instanceof SummaryValue))
      {
        localObject2 = (SummaryValue)paramHashMap2.get(localEntry.getKey());
        if (localObject2 == null) {
          localObject2 = SummaryValue.getInstance(new Currency(new BigDecimal(0.0D), paramString2, ((SummaryValue)localObject1)._amount.getLocale()), ((SummaryValue)localObject1)._itemCount);
        } else if (((SummaryValue)localObject1)._itemCount != -1) {
          if (((SummaryValue)localObject2)._itemCount == -1) {
            ((SummaryValue)localObject2)._itemCount = ((SummaryValue)localObject1)._itemCount;
          } else {
            localObject2._itemCount += ((SummaryValue)localObject1)._itemCount;
          }
        }
        if (paramString1.equals(paramString2))
        {
          ((SummaryValue)localObject2)._amount.addAmount(((SummaryValue)localObject1)._amount);
        }
        else
        {
          localObject3 = FX.convertToBaseCurrency(paramSecureUser, ((SummaryValue)localObject1)._amount, paramDateTime, paramHashMap3);
          ((SummaryValue)localObject2)._amount.addAmount((Currency)localObject3);
        }
        paramHashMap2.put(localEntry.getKey(), localObject2);
      }
      else if ((localObject1 instanceof BigDecimal))
      {
        localObject2 = (SummaryValue)paramHashMap2.get(localEntry.getKey());
        if (localObject2 == null) {
          localObject2 = SummaryValue.getInstance(new Currency(new BigDecimal(0.0D), paramString2, paramSecureUser.getLocale()));
        }
        if (paramString1.equals(paramString2))
        {
          ((SummaryValue)localObject2)._amount.addAmount((BigDecimal)localObject1);
        }
        else
        {
          localObject3 = FX.convertToBaseCurrency(paramSecureUser, new Currency((BigDecimal)localObject1, paramString1, paramSecureUser.getLocale()), paramDateTime, paramHashMap3);
          ((SummaryValue)localObject2)._amount.addAmount((Currency)localObject3);
        }
        paramHashMap2.put(localEntry.getKey(), localObject2);
      }
      else if ((localObject1 instanceof Currency))
      {
        localObject2 = (SummaryValue)paramHashMap2.get(localEntry.getKey());
        if (localObject2 == null) {
          localObject2 = SummaryValue.getInstance(new Currency(new BigDecimal(0.0D), paramString2, ((Currency)localObject1).getLocale()));
        }
        if (paramString1.equals(paramString2))
        {
          ((SummaryValue)localObject2)._amount.addAmount((Currency)localObject1);
        }
        else
        {
          localObject3 = FX.convertToBaseCurrency(paramSecureUser, (Currency)localObject1, paramDateTime, paramHashMap3);
          ((SummaryValue)localObject2)._amount.addAmount((Currency)localObject3);
        }
        paramHashMap2.put(localEntry.getKey(), localObject2);
      }
      else if ((localObject1 instanceof Integer))
      {
        localObject2 = (Integer)paramHashMap2.get(localEntry.getKey());
        localObject3 = null;
        if (localObject2 == null) {
          localObject3 = (Integer)localObject1;
        } else {
          localObject3 = new Integer(((Integer)localObject2).intValue() + ((Integer)localObject1).intValue());
        }
        paramHashMap2.put(localEntry.getKey(), localObject3);
      }
    }
  }
  
  public static HashMap getFXRatesForCurrencyTotals(HashMap paramHashMap1, SecureUser paramSecureUser, com.ffusion.beans.DateTime paramDateTime, HashMap paramHashMap2)
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    HashSet localHashSet = new HashSet();
    Set localSet = paramHashMap1.entrySet();
    Iterator localIterator = localSet.iterator();
    String str;
    while (localIterator.hasNext())
    {
      localObject = (Map.Entry)localIterator.next();
      str = (String)((Map.Entry)localObject).getKey();
      if (!str.equals(paramSecureUser.getBaseCurrency())) {
        localHashSet.add(str);
      }
    }
    Object localObject = localHashSet.iterator();
    while (((Iterator)localObject).hasNext())
    {
      str = (String)((Iterator)localObject).next();
      FXRate localFXRate = FX.getFXRate(paramSecureUser, str, paramSecureUser.getBaseCurrency(), paramDateTime, paramHashMap2);
      if (localFXRate != null) {
        localHashMap.put(str, localFXRate);
      }
    }
    return localHashMap;
  }
  
  public static boolean isCurrencyAloneInMap(HashMap paramHashMap, String paramString)
  {
    boolean bool = true;
    Set localSet = paramHashMap.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      if (!str.equals(paramString))
      {
        bool = false;
        break;
      }
    }
    return bool;
  }
  
  private static ArrayList a(Accounts paramAccounts)
  {
    HashMap localHashMap = new HashMap();
    Accounts localAccounts = (Accounts)paramAccounts.clone();
    localAccounts.setSortedBy("ID");
    Object localObject2;
    for (int i1 = 0; i1 < localAccounts.size(); i1++)
    {
      localObject1 = (Account)localAccounts.get(i1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(((Account)localObject1).getBankName());
      localStringBuffer.append(" - ");
      localStringBuffer.append(((Account)localObject1).getRoutingNum());
      localObject2 = (Accounts)localHashMap.get(localStringBuffer.toString());
      if (localObject2 == null)
      {
        localObject2 = new Accounts(paramAccounts.getLocale());
        localHashMap.put(localStringBuffer.toString(), localObject2);
      }
      ((Accounts)localObject2).add(localObject1);
    }
    ArrayList localArrayList = new ArrayList(localHashMap.keySet());
    Qsort.sortStringsIgnoreCase(localArrayList, 1);
    Object localObject1 = new ArrayList();
    for (int i2 = 0; i2 < localArrayList.size(); i2++)
    {
      localObject2 = localArrayList.get(i2);
      ((ArrayList)localObject1).add(localHashMap.get(localObject2));
    }
    return localObject1;
  }
  
  public static boolean accountCriterionHasAllAccounts(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (("AllAccounts".equals(str)) || ("AllAccounts".equals(str))) {
        return true;
      }
    }
    return false;
  }
  
  public static void fillBAIBalanceDetailReport(ReportResult paramReportResult, Account paramAccount, boolean paramBoolean, com.ffusion.beans.DateTime paramDateTime1, com.ffusion.beans.DateTime paramDateTime2, ReportCriteria paramReportCriteria, Connection paramConnection, HashMap paramHashMap)
    throws DCException, RptException
  {
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    String str2 = (String)localProperties1.get("DataClassification");
    Properties localProperties3 = (Properties)localProperties1.clone();
    localProperties3.put("Accounts", a(paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum()));
    HashMap localHashMap = new HashMap();
    localHashMap.put(paramAccount.getRoutingNum(), paramDateTime1);
    paramHashMap.put("StartDates", localHashMap);
    localHashMap = new HashMap();
    localHashMap.put(paramAccount.getRoutingNum(), paramDateTime2);
    paramHashMap.put("EndDates", localHashMap);
    ResultSet localResultSet = null;
    Vector localVector = new Vector();
    localVector.add(paramReportCriteria.getLocale().toString());
    try
    {
      ReportCriteria localReportCriteria = new ReportCriteria();
      localReportCriteria.setSearchCriteria(localProperties3);
      localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
      String str3 = a(localReportCriteria, localVector, paramHashMap, "TransactionDetail");
      paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
      if (str3 == null) {
        return;
      }
      String str4 = a(localReportSortCriteria, str1, true);
      localResultSet = a(arrayOfPreparedStatement, "SELECT dcAccount.AccountID,dcAccount.BankID, dcAccount.RoutingNumber, dcAccount.CurrencyCode, dcTransactions.TransID, dcTransactions.TransTypeID, dcTransactions.TransCategoryID, dcTransactions.Description, dcTransactions.ReferenceNumber, dcTransactions.Memo, dcTransactions.Amount, dcTransactions.RunningBalance, dcTransactions.TransTrackingID, dcTransactions.DCTransactionIndex, dcTransactions.PostingDate, dcTransactions.DueDate, dcTransactions.FixedDepRate, dcTransactions.PayeePayor, dcTransactions.PayorNum, dcTransactions.OrigUser, dcTransactions.PONum, dcTransactions.ImmedAvailAmount, dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.DataDate, dcTransactions.InstNumber, dcTransactions.InstBankName, dcTransactions.ValueDateTime, dcTransactions.TransSubTypeID, dcTransactions.ExtendABeanXMLID, dcTransactions.TransDate, subTypeDesc.DESCRIPTION  FROM DC_Account dcAccount, DC_Transactions dcTransactions, DC_SUBTYPE_DESC subTypeDesc WHERE  dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ", str3, str4, localVector, paramConnection);
      a(paramReportResult, paramAccount, paramDateTime1, localResultSet, paramBoolean, paramConnection, str2);
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to generate BAI Balance Report from database.", localSQLException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(arrayOfPreparedStatement[0]);
    }
  }
  
  public static int fillByDateBalanceDetailReport(ReportResult paramReportResult, int paramInt1, Account paramAccount, com.ffusion.beans.DateTime paramDateTime1, com.ffusion.beans.DateTime paramDateTime2, ReportCriteria paramReportCriteria, ArrayList paramArrayList, boolean paramBoolean, int paramInt2, int paramInt3, Connection paramConnection, HashMap paramHashMap)
    throws DCException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    int i1 = paramInt2;
    String str1 = paramAccount.getID();
    String str2 = paramAccount.getBankID();
    String str3 = paramAccount.getRoutingNum();
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str4 = localProperties2.getProperty("REPORTTYPE");
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    String str5 = (String)localProperties1.get("DataClassification");
    boolean bool = new Boolean(localProperties2.getProperty("REPEAT_COLUMN_HEADER")).booleanValue();
    Properties localProperties3 = (Properties)localProperties1.clone();
    localProperties3.put("Accounts", a(str1, str2, str3));
    HashMap localHashMap = new HashMap();
    localHashMap.put(str3, paramDateTime1);
    paramHashMap.put("StartDates", localHashMap);
    localHashMap = new HashMap();
    localHashMap.put(str3, paramDateTime2);
    paramHashMap.put("EndDates", localHashMap);
    ResultSet localResultSet = null;
    Vector localVector1 = new Vector();
    localVector1.add(localLocale.toString());
    PreparedStatement[] arrayOfPreparedStatement = new PreparedStatement[1];
    try
    {
      ReportCriteria localReportCriteria = new ReportCriteria();
      localReportCriteria.setSearchCriteria(localProperties3);
      localReportCriteria.setHiddenSearchCriteria(paramReportCriteria);
      String str6 = a(localReportCriteria, localVector1, paramHashMap, "TransactionDetail");
      paramReportCriteria.setHiddenSearchCriteria(localReportCriteria);
      if (str6 == null)
      {
        int i2 = paramInt2;
        return i2;
      }
      String str7 = a(localReportSortCriteria, str4, true);
      Vector localVector2 = (Vector)localVector1.clone();
      localResultSet = a(arrayOfPreparedStatement, "SELECT dcAccount.AccountID, dcTransactions.PayeePayor, dcTransactions.Description, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.Amount, dcTransactions.DataDate, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount,dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION FROM DC_Transactions dcTransactions, DC_Account dcAccount, DC_SUBTYPE_DESC subTypeDesc WHERE dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ", str6 + " AND (dcTransactions.Amount >= 0 OR dcTransactions.Amount IS NULL) ", str7, localVector1, paramConnection);
      Currency localCurrency1 = new Currency("0", paramAccount.getCurrencyCode(), localLocale, "CURRENCY");
      Currency localCurrency2 = new Currency("0", paramAccount.getCurrencyCode(), localLocale, "CURRENCY");
      Integer localInteger1 = new Integer(0);
      Integer localInteger2 = new Integer(0);
      Vector localVector3 = new Vector();
      int i3 = 0;
      int i4 = 0;
      if (a(localResultSet))
      {
        a(paramBoolean, paramArrayList, paramReportResult, paramReportCriteria, paramDateTime1, paramAccount);
        int i5 = a((ReportResult)paramArrayList.get(paramInt1), paramAccount, localResultSet, 1, paramInt2, paramInt3, i4, paramInt2 == i1, bool, localVector3, ReportConsts.getColumnName(280, localLocale), paramReportCriteria);
        i4 += i5 - paramInt2;
        paramInt2 = i5;
        i3 = 1;
        localCurrency1 = (Currency)localVector3.get(0);
        localInteger1 = (Integer)localVector3.get(1);
      }
      try
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      }
      catch (Exception localException3)
      {
        DebugLog.throwing("Failed to close DB resultset or statement.", localException3);
      }
      int i6;
      if (a(paramInt3, paramInt2, null))
      {
        i6 = paramInt2;
        return i6;
      }
      str7 = a(localReportSortCriteria, str4, false);
      localResultSet = a(arrayOfPreparedStatement, "SELECT dcAccount.AccountID, dcTransactions.PayeePayor, dcTransactions.Description, dcTransactions.TransTypeID, dcTransactions.TransSubTypeID, dcTransactions.ReferenceNumber, dcTransactions.BankRefNum, dcTransactions.CustRefNum, dcTransactions.Amount, dcTransactions.DataDate, dcTransactions.ExtendABeanXMLID, dcTransactions.ImmedAvailAmount,dcTransactions.OneDayAvailAmount, dcTransactions.MoreOneDayAvailAm, subTypeDesc.DESCRIPTION FROM DC_Transactions dcTransactions, DC_Account dcAccount, DC_SUBTYPE_DESC subTypeDesc WHERE dcTransactions.TransSubTypeID = subTypeDesc.ID AND subTypeDesc.LOCALE = ? ", str6 + " AND (dcTransactions.Amount < 0 OR dcTransactions.Amount IS NULL) ", str7, localVector2, paramConnection);
      if (a(localResultSet))
      {
        localVector3.clear();
        a(paramBoolean, paramArrayList, paramReportResult, paramReportCriteria, paramDateTime1, paramAccount);
        i6 = a((ReportResult)paramArrayList.get(paramInt1), paramAccount, localResultSet, 2, paramInt2, paramInt3, i4, paramInt2 == i1, bool, localVector3, ReportConsts.getColumnName(281, localLocale), paramReportCriteria);
        i4 += i6 - paramInt2;
        paramInt2 = i6;
        i3 = 1;
        localCurrency2 = (Currency)localVector3.get(2);
        localInteger2 = (Integer)localVector3.get(3);
      }
      if (a(paramInt3, paramInt2, null))
      {
        i6 = paramInt2;
        return i6;
      }
      if (i3 != 0) {
        paramInt2 = a((ReportResult)paramArrayList.get(paramInt1), paramAccount, localCurrency1, localCurrency2, localInteger1, localInteger2, paramInt2, paramInt3, paramReportCriteria, paramDateTime1);
      }
      if (a(paramInt3, paramInt2, null))
      {
        i6 = paramInt2;
        return i6;
      }
      return paramInt2;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get balance detail report from database.", localSQLException);
    }
    finally
    {
      try
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(arrayOfPreparedStatement[0]);
      }
      catch (Exception localException7) {}
    }
  }
  
  private static void a(ReportResult paramReportResult, Account paramAccount, com.ffusion.beans.DateTime paramDateTime, ResultSet paramResultSet, boolean paramBoolean, Connection paramConnection, String paramString)
    throws RptException, DCException, SQLException
  {
    Locale localLocale = paramReportResult.getLocale();
    int i1 = 0;
    ReportResult localReportResult = null;
    int i2 = 0;
    while (paramResultSet.next())
    {
      if (paramBoolean)
      {
        paramBoolean = false;
        localObject1 = new ReportResult(localLocale);
        paramReportResult.addSubReport((ReportResult)localObject1);
        jdField_if((ReportResult)localObject1, "AccountSummary");
        a((ReportResult)localObject1, R.length, -1);
        a.a((ReportResult)localObject1, ((ReportResult)localObject1).getLocale(), R);
        localObject2 = new ReportDataItems(localLocale);
        ((ReportDataItems)localObject2).add().setData(paramDateTime);
        localObject3 = null;
        try
        {
          localObject3 = AccountUtil.buildAccountDisplayTextForExport("BAI2", paramAccount.getID(), paramAccount.getLocale());
        }
        catch (UtilException localUtilException)
        {
          localObject3 = paramAccount.getID();
        }
        ((ReportDataItems)localObject2).add().setData(localObject3);
        ((ReportDataItems)localObject2).add().setData(paramAccount.getBankID());
        ((ReportDataItems)localObject2).add().setData(paramAccount.getRoutingNum());
        ((ReportDataItems)localObject2).add().setData(paramAccount.getCurrencyCode());
        ((ReportDataItems)localObject2).add().setData(paramString);
        a((ReportResult)localObject1, (ReportDataItems)localObject2, -1);
      }
      Object localObject1 = new ReportDataItems(localLocale);
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(1));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(2));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(3));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(4));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(5));
      ((ReportDataItems)localObject1).add().setData(new Integer(paramResultSet.getInt(6)));
      ((ReportDataItems)localObject1).add().setData(new Integer(paramResultSet.getInt(7)));
      Object localObject2 = paramResultSet.getString(8);
      if ((localObject2 != null) && (((String)localObject2).trim().length() == 0)) {
        localObject2 = null;
      }
      ((ReportDataItems)localObject1).add().setData(localObject2);
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(9));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(10));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 11, localLocale, paramAccount.getCurrencyCode()));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 12, localLocale, paramAccount.getCurrencyCode()));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(13));
      ((ReportDataItems)localObject1).add().setData(new Long(paramResultSet.getLong(14)));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 15, localLocale));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 16, localLocale));
      ((ReportDataItems)localObject1).add().setData(new Float(paramResultSet.getFloat(17)));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(18));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(19));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(20));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(21));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 22, localLocale, paramAccount.getCurrencyCode()));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 23, localLocale, paramAccount.getCurrencyCode()));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 24, localLocale, paramAccount.getCurrencyCode()));
      jdField_if((ReportDataItems)localObject1, DCAdapter.unPadRefNum(paramResultSet.getString(25), DCAdapter.getBankReferenceNumberType()));
      jdField_if((ReportDataItems)localObject1, DCAdapter.unPadRefNum(paramResultSet.getString(26), DCAdapter.getCustomerReferenceNumberType()));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 27, localLocale));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(28));
      ((ReportDataItems)localObject1).add().setData(paramResultSet.getString(29));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 30, localLocale));
      ((ReportDataItems)localObject1).add().setData(new Integer(paramResultSet.getInt(31)));
      ((ReportDataItems)localObject1).add().setData(a(paramResultSet, 33, localLocale));
      ((ReportDataItems)localObject1).add().setData(paramString);
      Object localObject3 = new Transactions(localLocale);
      Transaction localTransaction = ((Transactions)localObject3).create();
      DCUtil.fillExtendABean(localTransaction, paramResultSet, 32);
      if (i1 == 0)
      {
        i1 = 1;
        localReportResult = new ReportResult(localLocale);
        paramReportResult.addSubReport(localReportResult);
        jdField_if(localReportResult, "Transactions");
        i2 = localTransaction.getHash().size();
        a(localReportResult, jdField_try.length + i2, -1);
        a.a(localReportResult, localLocale, jdField_try);
        for (int i3 = 0; i3 < i2; i3++)
        {
          localObject4 = new ReportColumn(localLocale);
          localObject5 = new ArrayList();
          ((ArrayList)localObject5).add("ExtendABean" + (i3 + 1));
          ((ReportColumn)localObject4).setLabels((ArrayList)localObject5);
          ((ReportColumn)localObject4).setDataType("java.lang.String");
          ((ReportColumn)localObject4).setWidthAsPercent(1);
          localReportResult.addColumn((ReportColumn)localObject4);
        }
      }
      HashMap localHashMap = localTransaction.getHash();
      Object localObject4 = new ArrayList();
      ((ArrayList)localObject4).addAll(localHashMap.keySet());
      Qsort.sortStrings((ArrayList)localObject4, 1);
      Object localObject5 = ((ArrayList)localObject4).iterator();
      for (int i4 = 0; i4 < i2; i4++) {
        if (((Iterator)localObject5).hasNext()) {
          ((ReportDataItems)localObject1).add().setData(localHashMap.get(((Iterator)localObject5).next()));
        } else {
          ((ReportDataItems)localObject1).add(null);
        }
      }
      a(localReportResult, (ReportDataItems)localObject1, -1);
    }
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, Currency paramCurrency1, Currency paramCurrency2, Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2, ReportCriteria paramReportCriteria, com.ffusion.beans.DateTime paramDateTime)
    throws RptException, SQLException, DCException
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    if (bool) {
      return jdField_if(paramReportResult, paramAccount, paramCurrency1, paramCurrency2, paramInteger1, paramInteger2, paramInt1, paramInt2, 3, paramReportCriteria, paramDateTime == null ? null : (com.ffusion.beans.DateTime)paramDateTime.clone());
    }
    return a(paramReportResult, paramAccount, paramCurrency1, paramCurrency2, paramInteger1, paramInteger2, paramInt1, paramInt2, 3, paramReportCriteria, paramDateTime);
  }
  
  private static int jdField_if(ReportResult paramReportResult, Account paramAccount, Currency paramCurrency1, Currency paramCurrency2, Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2, int paramInt3, ReportCriteria paramReportCriteria, com.ffusion.beans.DateTime paramDateTime)
    throws RptException, SQLException, DCException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str1 = localProperties.getProperty("DATEFORMAT");
    String str2 = ReportConsts.getColumnName(735, localLocale);
    String str3 = ReportConsts.getColumnName(736, localLocale);
    String str4 = ReportConsts.getColumnName(737, localLocale);
    String str5 = null;
    if (paramDateTime != null)
    {
      if (str1 != null) {
        paramDateTime.setFormat(str1);
      }
      str5 = paramDateTime.toString();
    }
    else
    {
      localObject = (String)paramReportCriteria.getSearchCriteria().get("Date Range Type");
      if ("Relative".equals(localObject)) {
        str5 = paramReportCriteria.getDisplayValue("Date Range");
      } else {
        str5 = a(paramReportCriteria, str1, localLocale);
      }
    }
    paramInt1++;
    if (a(paramInt2, paramInt1, null)) {
      return paramInt1;
    }
    Object localObject = new ReportResult(localLocale);
    paramReportResult.addSubReport((ReportResult)localObject);
    a((ReportResult)localObject, af.length, -1);
    a.a((ReportResult)localObject, localLocale, af);
    ReportDataItems localReportDataItems;
    if ((paramInt3 == 1) || (paramInt3 == 3))
    {
      localReportDataItems = new ReportDataItems(localLocale);
      jdField_if(localReportDataItems, paramAccount.getRoutingNum());
      jdField_if(localReportDataItems, paramAccount.getDisplayText("CSV"));
      jdField_if(localReportDataItems, str5);
      jdField_if(localReportDataItems, str2);
      jdField_if(localReportDataItems, str3);
      jdField_if(localReportDataItems, paramCurrency1 == null ? null : paramCurrency1.getCurrencyStringNoSymbol());
      jdField_if(localReportDataItems, paramInteger1.toString());
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, paramAccount.getCurrencyCode());
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      a((ReportResult)localObject, localReportDataItems, -1);
      paramInt1++;
    }
    if ((paramInt3 == 2) || (paramInt3 == 3))
    {
      localReportDataItems = new ReportDataItems(localLocale);
      jdField_if(localReportDataItems, paramAccount.getRoutingNum());
      jdField_if(localReportDataItems, paramAccount.getDisplayText("CSV"));
      jdField_if(localReportDataItems, str5);
      jdField_if(localReportDataItems, str2);
      jdField_if(localReportDataItems, str4);
      jdField_if(localReportDataItems, paramCurrency2 == null ? null : paramCurrency2.getCurrencyStringNoSymbol());
      jdField_if(localReportDataItems, paramInteger2.toString());
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, paramAccount.getCurrencyCode());
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, null);
      a((ReportResult)localObject, localReportDataItems, -1);
      paramInt1++;
    }
    return paramInt1;
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, Currency paramCurrency1, Currency paramCurrency2, Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2, int paramInt3, ReportCriteria paramReportCriteria, com.ffusion.beans.DateTime paramDateTime)
    throws RptException, SQLException, DCException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool = "CSV".equals(localProperties.getProperty("FORMAT"));
    if (bool) {
      return jdField_if(paramReportResult, paramAccount, paramCurrency1, paramCurrency2, paramInteger1, paramInteger2, paramInt1, paramInt2, paramInt3, paramReportCriteria, paramDateTime);
    }
    paramInt1++;
    if (a(paramInt2, paramInt1, null)) {
      return paramInt1;
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    jdField_if(localReportResult, "");
    a[] arrayOfa = null;
    if (paramInt3 == 1) {
      arrayOfa = e;
    } else if (paramInt3 == 2) {
      arrayOfa = k;
    } else {
      arrayOfa = y;
    }
    a(localReportResult, arrayOfa.length, -1);
    a.a(localReportResult, localLocale, arrayOfa);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    jdField_if(localReportDataItems, null);
    jdField_if(localReportDataItems, ReportConsts.getText(757, localLocale));
    if ((paramInt3 == 1) || (paramInt3 == 3)) {
      jdField_if(localReportDataItems, paramCurrency1.getCurrencyStringNoSymbol());
    }
    if ((paramInt3 == 2) || (paramInt3 == 3)) {
      jdField_if(localReportDataItems, paramCurrency2.getCurrencyStringNoSymbol());
    }
    jdField_if(localReportDataItems, null);
    a(localReportResult, localReportDataItems, -1);
    localReportDataItems = new ReportDataItems(localLocale);
    jdField_if(localReportDataItems, null);
    jdField_if(localReportDataItems, ReportConsts.getText(758, localLocale));
    if ((paramInt3 == 1) || (paramInt3 == 3)) {
      a(localReportDataItems, paramInteger1.toString());
    }
    if ((paramInt3 == 2) || (paramInt3 == 3)) {
      a(localReportDataItems, paramInteger2.toString());
    }
    jdField_if(localReportDataItems, null);
    a(localReportResult, localReportDataItems, -1);
    return paramInt1;
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, ResultSet paramResultSet, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, Vector paramVector, String paramString, ReportCriteria paramReportCriteria)
    throws RptException, SQLException, DCException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool1 = "CSV".equals(localProperties.getProperty("FORMAT"));
    String str1 = localProperties.getProperty("DATEFORMAT");
    Currency localCurrency1 = new Currency("0", localLocale);
    int i1 = 0;
    Currency localCurrency2 = new Currency("0", localLocale);
    int i2 = 0;
    a[] arrayOfa = af;
    Transactions localTransactions = new Transactions(localLocale);
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    a(localReportResult, af.length, -1);
    a.a(localReportResult, localLocale, af);
    while (paramResultSet.next())
    {
      paramInt2++;
      if (a(paramInt3, paramInt2, null)) {
        break;
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      jdField_if(localReportDataItems, paramAccount.getRoutingNum());
      jdField_if(localReportDataItems, paramAccount.getDisplayText("CSV"));
      Timestamp localTimestamp = paramResultSet.getTimestamp(10);
      com.ffusion.beans.DateTime localDateTime = DCUtil.getTimestampColumn(localTimestamp, localLocale);
      if ((str1 != null) && (localDateTime != null)) {
        localDateTime.setFormat(str1);
      }
      jdField_if(localReportDataItems, localDateTime.toString());
      int i3 = paramResultSet.getInt(5);
      jdField_if(localReportDataItems, Integer.toString(i3));
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i3);
      }
      catch (CSILException localCSILException) {}
      continue;
      String str2 = paramResultSet.getString(15);
      jdField_if(localReportDataItems, str2);
      Currency localCurrency3 = a(paramResultSet, 9, localLocale, paramAccount.getCurrencyCode());
      boolean bool2 = a(localBAITypeCodeInfo, localCurrency3);
      if (bool2) {
        i2++;
      } else {
        i1++;
      }
      if (localCurrency3 != null) {
        localReportDataItems.add().setData(localCurrency3.getCurrencyStringNoSymbol());
      } else {
        localReportDataItems.add().setData(null);
      }
      if (localCurrency3 != null) {
        if (paramInt1 == 3)
        {
          if (bool2) {
            localCurrency2.addAmount(localCurrency3);
          } else {
            localCurrency1.addAmount(localCurrency3);
          }
        }
        else if (bool2) {
          localCurrency2.addAmount(localCurrency3);
        } else {
          localCurrency1.addAmount(localCurrency3);
        }
      }
      jdField_if(localReportDataItems, null);
      jdField_if(localReportDataItems, DCAdapter.unPadRefNum(paramResultSet.getString(7), DCAdapter.getBankReferenceNumberType()));
      jdField_if(localReportDataItems, DCAdapter.unPadRefNum(paramResultSet.getString(8), DCAdapter.getCustomerReferenceNumberType()));
      String str3 = paramResultSet.getString(3);
      if ((str3 != null) && (str3.trim().length() == 0)) {
        str3 = null;
      }
      jdField_if(localReportDataItems, str3);
      jdField_if(localReportDataItems, paramAccount.getCurrencyCode());
      Currency localCurrency4 = a(paramResultSet, 12, localLocale, paramAccount.getCurrencyCode());
      Currency localCurrency5 = a(paramResultSet, 13, localLocale, paramAccount.getCurrencyCode());
      Currency localCurrency6 = a(paramResultSet, 14, localLocale, paramAccount.getCurrencyCode());
      jdField_if(localReportDataItems, localCurrency4 == null ? null : localCurrency4.getCurrencyStringNoSymbol());
      jdField_if(localReportDataItems, localCurrency5 == null ? null : localCurrency5.getCurrencyStringNoSymbol());
      jdField_if(localReportDataItems, localCurrency6 == null ? null : localCurrency6.getCurrencyStringNoSymbol());
      a(localReportResult, localReportDataItems, -1);
      localReportDataItems = null;
    }
    paramVector.clear();
    paramVector.add(localCurrency1);
    paramVector.add(new Integer(i1));
    paramVector.add(localCurrency2);
    paramVector.add(new Integer(i2));
    return paramInt2;
  }
  
  private static int a(ReportResult paramReportResult, Account paramAccount, ResultSet paramResultSet, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, Vector paramVector, String paramString, ReportCriteria paramReportCriteria)
    throws RptException, SQLException, DCException
  {
    Locale localLocale = paramReportResult.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    boolean bool1 = "CSV".equals(localProperties.getProperty("FORMAT"));
    if (bool1) {
      return a(paramReportResult, paramAccount, paramResultSet, paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2, paramVector, paramString, paramReportCriteria);
    }
    int i1 = 0;
    ReportResult localReportResult1 = null;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    Currency localCurrency1 = new Currency("0", paramAccount.getCurrencyCode(), localLocale, "CURRENCY");
    int i6 = 0;
    Currency localCurrency2 = new Currency("0", paramAccount.getCurrencyCode(), localLocale, "CURRENCY");
    int i7 = 0;
    a[] arrayOfa1;
    a[] arrayOfa2;
    if (paramInt1 == 1)
    {
      i4 = 12;
      i5 = 88;
      arrayOfa1 = ag;
      arrayOfa2 = a4;
    }
    else if (paramInt1 == 2)
    {
      i4 = 12;
      i5 = 88;
      arrayOfa1 = ak;
      arrayOfa2 = at;
    }
    else if (paramInt1 == 3)
    {
      i4 = 12;
      i5 = 88;
      arrayOfa1 = bi;
      arrayOfa2 = aX;
    }
    else
    {
      throw new DCException("A request was made (in DCServiceUtil.addBalanceDetail) to display an invalid type of transaction in a balance detail report.  The requested type identifier was " + paramInt1 + ". Please " + "ensure that report generation code only calls this method with a valid type " + "identifier.");
    }
    Transactions localTransactions = new Transactions(localLocale);
    if ((paramBoolean1) || (paramBoolean2)) {
      paramInt4 = 0;
    }
    while (paramResultSet.next())
    {
      int i8 = paramResultSet.getInt(5);
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(i8);
      }
      catch (CSILException localCSILException) {}
      continue;
      Currency localCurrency3 = a(paramResultSet, 9, localLocale, paramAccount.getCurrencyCode());
      boolean bool2 = a(localBAITypeCodeInfo, localCurrency3);
      if (bool2)
      {
        if (paramInt1 == 1) {
          continue;
        }
        i7++;
      }
      else
      {
        if (paramInt1 == 2) {
          continue;
        }
        i6++;
      }
      paramInt2++;
      if (a(paramInt3, paramInt2, null)) {
        break;
      }
      if (i2 == 0)
      {
        i2 = 1;
        localReportResult1 = new ReportResult(localLocale);
        if (paramBoolean1)
        {
          localReportResult2 = new ReportResult(localLocale);
          jdField_if(localReportResult2, "");
          localObject1 = new ReportDataDimensions(localLocale);
          ((ReportDataDimensions)localObject1).setNumColumns(1);
          ((ReportDataDimensions)localObject1).setNumRows(1);
          localReportResult2.setDataDimensions((ReportDataDimensions)localObject1);
          localObject2 = new ReportColumn(localLocale);
          ((ReportColumn)localObject2).setDataType("java.lang.String");
          ((ReportColumn)localObject2).setWidthAsPercent(100);
          localReportResult2.addColumn((ReportColumn)localObject2);
          paramReportResult.addSubReport(localReportResult2);
        }
        paramReportResult.addSubReport(localReportResult1);
        if (paramString != null) {
          jdField_if(localReportResult1, paramString);
        }
        a(localReportResult1, 0, 0);
      }
      ReportResult localReportResult2 = new ReportResult(localLocale);
      localReportResult1.addSubReport(localReportResult2);
      Object localObject1 = new ReportHeading(localLocale);
      localReportResult2.setHeading(null);
      a(localReportResult2, arrayOfa1.length, -1);
      if ((paramBoolean2) || (paramBoolean1))
      {
        a.a(localReportResult2, localLocale, arrayOfa1);
        paramBoolean1 = false;
      }
      else
      {
        a.a(localReportResult2, localLocale, arrayOfa2);
      }
      Object localObject2 = new ReportDataItems(localLocale);
      Timestamp localTimestamp = paramResultSet.getTimestamp(10);
      jdField_if((ReportDataItems)localObject2, DCUtil.getTimestampColumn(localTimestamp, localLocale));
      String str = paramResultSet.getString(15);
      jdField_if((ReportDataItems)localObject2, str);
      jdField_if((ReportDataItems)localObject2, DCAdapter.unPadRefNum(paramResultSet.getString(8), DCAdapter.getCustomerReferenceNumberType()));
      jdField_if((ReportDataItems)localObject2, DCAdapter.unPadRefNum(paramResultSet.getString(7), DCAdapter.getBankReferenceNumberType()));
      if (localCurrency3 == null)
      {
        ((ReportDataItems)localObject2).add();
        ((ReportDataItems)localObject2).add();
      }
      else if (paramInt1 == 3)
      {
        if (bool2)
        {
          ((ReportDataItems)localObject2).add();
          ((ReportDataItems)localObject2).add().setData(localCurrency3.getCurrencyStringNoSymbol());
          localCurrency2.addAmount(localCurrency3);
        }
        else
        {
          ((ReportDataItems)localObject2).add().setData(localCurrency3.getCurrencyStringNoSymbol());
          ((ReportDataItems)localObject2).add().setData("");
          localCurrency1.addAmount(localCurrency3);
        }
      }
      else if (bool2)
      {
        ((ReportDataItems)localObject2).add();
        ((ReportDataItems)localObject2).add().setData(localCurrency3.getCurrencyStringNoSymbol());
        localCurrency2.addAmount(localCurrency3);
      }
      else
      {
        ((ReportDataItems)localObject2).add().setData(localCurrency3.getCurrencyStringNoSymbol());
        ((ReportDataItems)localObject2).add();
        localCurrency1.addAmount(localCurrency3);
      }
      a(localReportResult2, (ReportDataItems)localObject2, paramInt4 + 1);
      localObject2 = null;
      Transaction localTransaction = localTransactions.create();
      DCUtil.fillExtendABean(localTransaction, paramResultSet, 11);
      Object localObject3 = localTransaction.getHash().entrySet().iterator();
      Object localObject4;
      Object localObject5;
      while (((Iterator)localObject3).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject3).next();
        if (localObject2 == null)
        {
          localObject2 = new ReportDataItems(localLocale);
          for (int i10 = i3; i10 > 1; i10--) {
            ((ReportDataItems)localObject2).add(null);
          }
        }
        localObject4 = new Object[2];
        localObject4[0] = localEntry.getKey();
        localObject4[1] = localEntry.getValue();
        localObject5 = new LocalizableString("com.ffusion.beans.reporting.reports", "Balance_Reports_ExtendABean_Field_Display_Format", (Object[])localObject4);
        ((ReportDataItems)localObject2).add().setData(((LocalizableString)localObject5).localize(localLocale));
        a(localReportResult2, (ReportDataItems)localObject2, paramInt4 + 1);
        localObject2 = null;
      }
      if (localObject2 != null)
      {
        ((ReportDataItems)localObject2).add(null);
        a(localReportResult2, (ReportDataItems)localObject2, paramInt4 + 1);
      }
      if ((paramResultSet.getString(12) != null) || (paramResultSet.getString(13) != null) || (paramResultSet.getString(14) != null))
      {
        localReportResult2 = new ReportResult(localLocale);
        localReportResult1.addSubReport(localReportResult2);
        localObject1 = new ReportHeading(localLocale);
        localReportResult2.setHeading(null);
        a(localReportResult2, 2, -1);
        localObject3 = new ReportColumn(localLocale);
        ((ReportColumn)localObject3).setLabels(null);
        ((ReportColumn)localObject3).setDataType("java.lang.String");
        ((ReportColumn)localObject3).setWidthAsPercent(i4);
        localReportResult2.addColumn((ReportColumn)localObject3);
        localObject3 = new ReportColumn(localLocale);
        ((ReportColumn)localObject3).setLabels(null);
        ((ReportColumn)localObject3).setDataType("java.lang.String");
        ((ReportColumn)localObject3).setWidthAsPercent(i5);
        localReportResult2.addColumn((ReportColumn)localObject3);
        for (int i9 = 12; i9 <= 14; i9++) {
          if (paramResultSet.getString(i9) != null)
          {
            localObject4 = null;
            if (i9 == 12) {
              localObject4 = "Balance_Reports_Detail_Immediate_Available_Amount_Format";
            } else if (i9 == 13) {
              localObject4 = "Balance_Reports_Detail_One_Day_Available_Amount_Format";
            } else if (i9 == 14) {
              localObject4 = "Balance_Reports_Detail_Two_Or_More_Day_Available_Amount_Format";
            }
            localObject2 = new ReportDataItems(localLocale);
            jdField_if((ReportDataItems)localObject2, "");
            localObject5 = a(paramResultSet, i9, localLocale, paramAccount.getCurrencyCode());
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = ((Currency)localObject5).getCurrencyStringNoSymbol();
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", (String)localObject4, arrayOfObject);
            jdField_if((ReportDataItems)localObject2, localLocalizableString.localize(localLocale));
            a(localReportResult2, (ReportDataItems)localObject2, paramInt4 + 1);
          }
        }
      }
      localObject3 = paramResultSet.getString(3);
      if ((localObject3 != null) && (((String)localObject3).length() > 0))
      {
        localReportResult2 = new ReportResult(localLocale);
        localReportResult1.addSubReport(localReportResult2);
        localObject1 = new ReportHeading(localLocale);
        localReportResult2.setHeading(null);
        a(localReportResult2, 2, -1);
        ReportColumn localReportColumn = new ReportColumn(localLocale);
        localReportColumn.setLabels(null);
        localReportColumn.setDataType("java.lang.String");
        localReportColumn.setWidthAsPercent(i4);
        localReportResult2.addColumn(localReportColumn);
        localReportColumn = new ReportColumn(localLocale);
        localReportColumn.setLabels(null);
        localReportColumn.setDataType("java.lang.String");
        localReportColumn.setWidthAsPercent(i5);
        localReportResult2.addColumn(localReportColumn);
        localObject4 = new Object[1];
        localObject4[0] = localObject3;
        localObject5 = new LocalizableString("com.ffusion.beans.reporting.reports", "Balance_Reports_Detail_Description_Prefix_Text", (Object[])localObject4);
        localObject2 = new ReportDataItems(localLocale);
        jdField_if((ReportDataItems)localObject2, "");
        ((ReportDataItems)localObject2).add().setData(((LocalizableString)localObject5).localize(localLocale));
        a(localReportResult2, (ReportDataItems)localObject2, paramInt4 + 1);
        localObject2 = null;
      }
      paramInt4++;
    }
    paramVector.clear();
    paramVector.add(localCurrency1);
    paramVector.add(new Integer(i6));
    paramVector.add(localCurrency2);
    paramVector.add(new Integer(i7));
    return paramInt2;
  }
  
  private static void a(boolean paramBoolean, ArrayList paramArrayList, ReportResult paramReportResult, ReportCriteria paramReportCriteria, com.ffusion.beans.DateTime paramDateTime, Account paramAccount)
    throws RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    String str1 = ReportConsts.getColumnName(700, localLocale);
    String str2 = ReportConsts.getColumnName(701, localLocale);
    String str3 = ReportConsts.getColumnName(697, localLocale);
    ReportResult localReportResult1 = (ReportResult)paramArrayList.get(0);
    ReportResult localReportResult2 = (ReportResult)paramArrayList.get(1);
    ReportResult localReportResult3 = (ReportResult)paramArrayList.get(2);
    String str4 = paramReportCriteria.getReportOptions().getProperty("FORMAT");
    StringBuffer localStringBuffer;
    if (paramBoolean)
    {
      if (localReportResult1 == null)
      {
        localReportResult1 = new ReportResult(localLocale);
        paramArrayList.set(0, localReportResult1);
        paramReportResult.addSubReport(localReportResult1);
        localStringBuffer = new StringBuffer(str3);
        localStringBuffer.append(" : ");
        if (paramAccount.getBankName() != null)
        {
          localStringBuffer.append(paramAccount.getBankName());
          localStringBuffer.append(" - ");
        }
        localStringBuffer.append(paramAccount.getRoutingNum());
        jdField_if(localReportResult1, localStringBuffer.toString());
        a(localReportResult1, 0, -1);
      }
      if (localReportResult2 == null)
      {
        localReportResult2 = new ReportResult(localLocale);
        paramArrayList.set(1, localReportResult2);
        localReportResult1.addSubReport(localReportResult2);
        localStringBuffer = new StringBuffer(str1);
        localStringBuffer.append(" : ");
        try
        {
          localStringBuffer.append(AccountUtil.buildAccountDisplayTextForExport(str4, paramAccount.getID(), localLocale));
        }
        catch (UtilException localUtilException1)
        {
          DebugLog.throwing("Error while constructing account display string.", localUtilException1);
          localStringBuffer.append(paramAccount.getID());
        }
        if ((paramAccount.getNickName() != null) && (paramAccount.getNickName().length() > 0))
        {
          localStringBuffer.append(" - ");
          localStringBuffer.append(paramAccount.getNickName());
        }
        if (paramAccount.getCurrencyCode() != null)
        {
          localStringBuffer.append(" - ");
          localStringBuffer.append(paramAccount.getCurrencyCode());
        }
        jdField_if(localReportResult2, localStringBuffer.toString());
        a(localReportResult2, 0, -1);
      }
      if (localReportResult3 == null)
      {
        localReportResult3 = new ReportResult(localLocale);
        paramArrayList.set(2, localReportResult3);
        localReportResult2.addSubReport(localReportResult3);
        a(localReportResult3, 0, -1);
      }
    }
    else
    {
      if (localReportResult3 == null)
      {
        localReportResult3 = new ReportResult(localLocale);
        paramArrayList.set(2, localReportResult3);
        paramReportResult.addSubReport(localReportResult3);
        a(localReportResult3, 0, -1);
      }
      if (localReportResult1 == null)
      {
        localReportResult1 = new ReportResult(localLocale);
        paramArrayList.set(0, localReportResult1);
        localReportResult3.addSubReport(localReportResult1);
        localStringBuffer = new StringBuffer(str3);
        localStringBuffer.append(" : ");
        if (paramAccount.getBankName() != null)
        {
          localStringBuffer.append(paramAccount.getBankName());
          localStringBuffer.append(" - ");
        }
        localStringBuffer.append(paramAccount.getRoutingNum());
        jdField_if(localReportResult1, localStringBuffer.toString());
        a(localReportResult1, 0, -1);
      }
      if (localReportResult2 == null)
      {
        localReportResult2 = new ReportResult(localLocale);
        paramArrayList.set(1, localReportResult2);
        localReportResult1.addSubReport(localReportResult2);
        localStringBuffer = new StringBuffer(str1);
        localStringBuffer.append(" : ");
        try
        {
          localStringBuffer.append(AccountUtil.buildAccountDisplayTextForExport(str4, paramAccount.getID(), localLocale));
        }
        catch (UtilException localUtilException2)
        {
          DebugLog.throwing("Error while constructing account display string.", localUtilException2);
          localStringBuffer.append(paramAccount.getID());
        }
        if ((paramAccount.getNickName() != null) && (paramAccount.getNickName().length() > 0))
        {
          localStringBuffer.append(" - ");
          localStringBuffer.append(paramAccount.getNickName());
        }
        if (paramAccount.getCurrencyCode() != null)
        {
          localStringBuffer.append(" - ");
          localStringBuffer.append(paramAccount.getCurrencyCode());
        }
        jdField_if(localReportResult2, localStringBuffer.toString());
        a(localReportResult2, 0, -1);
      }
    }
  }
  
  private static Accounts jdField_new(String paramString)
    throws DCException
  {
    Accounts localAccounts = new Accounts();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    int i1 = localStringTokenizer.countTokens();
    if (i1 < 1) {
      throw new DCException("Value for SearchCriteria key Accounts has incorrect format.");
    }
    for (int i2 = 0; i2 < i1; i2++)
    {
      String str = localStringTokenizer.nextToken();
      Account localAccount = getAccountFromAccountCriterion(str);
      localAccounts.add(localAccount);
    }
    return localAccounts;
  }
  
  public static Account getAccountFromAccountCriterion(String paramString)
    throws DCException
  {
    Account localAccount = new Account();
    FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer(paramString, ":");
    int i1 = localFFIStringTokenizer.countTokens();
    if (i1 < 2) {
      throw new DCException("The account id and bank ID were not correctly passed in as a report criteria. The proper format is AccountID:BankID:RoutingNumber , where RoutingNumber is optional");
    }
    localAccount.setID(localFFIStringTokenizer.nextToken());
    localAccount.setBankID(localFFIStringTokenizer.nextToken());
    if (i1 >= 3) {
      localAccount.setRoutingNum(localFFIStringTokenizer.nextToken());
    }
    if (i1 >= 4) {
      localAccount.setNickName(localFFIStringTokenizer.nextToken());
    }
    if (i1 >= 5) {
      localAccount.setCurrencyCode(localFFIStringTokenizer.nextToken());
    }
    return localAccount;
  }
  
  private static Account a(String paramString, Accounts paramAccounts)
    throws DCException
  {
    if ((paramAccounts == null) || (paramAccounts.isEmpty())) {
      return null;
    }
    FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer(paramString, ":");
    int i1 = localFFIStringTokenizer.countTokens();
    if (i1 < 2) {
      throw new DCException("The account id and bank ID were not correctly passed in as a report criteria. The proper format is AccountID:BankID:RoutingNumber , where RoutingNumber is optional");
    }
    String str1 = localFFIStringTokenizer.nextToken();
    String str2 = localFFIStringTokenizer.nextToken();
    String str3 = null;
    String str4 = null;
    String str5 = null;
    if (i1 >= 3) {
      str3 = localFFIStringTokenizer.nextToken();
    }
    if (i1 >= 4) {
      str4 = localFFIStringTokenizer.nextToken();
    }
    if (i1 >= 5) {
      str5 = localFFIStringTokenizer.nextToken();
    }
    for (int i2 = 0; i2 < paramAccounts.size(); i2++)
    {
      Account localAccount = (Account)paramAccounts.get(i2);
      if ((localAccount.getID().equals(str1)) && (localAccount.getBankID().equals(str2)) && ((str3 == null) || (str3.length() <= 0) || (str3.equals(localAccount.getRoutingNum()))) && ((str4 == null) || (str4.length() <= 0) || (str4.equals(localAccount.getNickName()))) && ((str5 == null) || (str5.length() <= 0) || (str5.equals(localAccount.getCurrencyCode())))) {
        return localAccount;
      }
    }
    return null;
  }
  
  private static void jdField_if(ReportResult paramReportResult, String paramString)
    throws RptException
  {
    ReportHeading localReportHeading = new ReportHeading(paramReportResult.getLocale());
    localReportHeading.setLabel(paramString);
    paramReportResult.setHeading(localReportHeading);
  }
  
  private static void a(ReportResult paramReportResult, String paramString)
    throws RptException
  {
    ReportHeading localReportHeading = new ReportHeading(paramReportResult.getLocale());
    localReportHeading.setLabel(paramString);
    paramReportResult.setSectionHeading(localReportHeading);
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, int paramInt2)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramReportResult.getLocale());
    localReportDataDimensions.setNumColumns(paramInt1);
    localReportDataDimensions.setNumRows(paramInt2);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  private static void a(ReportResult paramReportResult, ReportDataItems paramReportDataItems, int paramInt)
    throws RptException
  {
    ReportRow localReportRow = new ReportRow(paramReportResult.getLocale());
    if ((paramInt != -1) && (paramInt % 2 == 0)) {
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    }
    localReportRow.setDataItems(paramReportDataItems);
    paramReportResult.addRow(localReportRow);
  }
  
  private static void jdField_if(ReportDataItems paramReportDataItems, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItems.add(null);
    } else {
      paramReportDataItems.add().setData(paramObject);
    }
  }
  
  private static void a(ReportDataItems paramReportDataItems, Object paramObject)
  {
    jdField_if(paramReportDataItems, paramObject == null ? null : paramObject.toString());
  }
  
  private static int a(Properties paramProperties)
  {
    int i1 = -1;
    String str = paramProperties.getProperty("MAXDISPLAYSIZE");
    try
    {
      if (str != null) {
        i1 = Integer.parseInt(str);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      DebugLog.log("Error occured when trying to parse the maximum display size of a report.  The given maximum display size was '" + str + "'.");
    }
    return i1;
  }
  
  public static void hideEmptySearchCriteria(ReportCriteria paramReportCriteria)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties.propertyNames();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = paramReportCriteria.getDisplayValue(str1);
      if (str2.length() <= 0) {
        paramReportCriteria.setHiddenSearchCriterion(str1, true);
      }
    }
  }
  
  private static String a(ReportCriteria paramReportCriteria, Vector paramVector, HashMap paramHashMap, String paramString)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    if (localProperties != null) {
      localStringBuffer = a(paramReportCriteria, paramString, paramHashMap);
    }
    String str1 = new String(localStringBuffer).trim();
    if (str1.length() != 0) {
      localStringBuffer.append(" AND ");
    }
    String str2 = (String)localProperties.get("Accounts");
    if (str2 == null) {
      str2 = "AllAccounts";
    } else if (accountCriterionHasAllAccounts(str2)) {
      str2 = "AllAccounts";
    }
    boolean bool = false;
    if ((paramString.equals("DepositDetail")) || (paramString.equals("TransactionDetail")) || (paramString.equals("CreditReport")) || (paramString.equals("DebitReport")) || (paramString.equals("TransactionDetail")) || (paramString.equals("BalanceDetailOnlyReport")) || (paramString.equals("BalanceDetailReport"))) {
      bool = true;
    }
    String str3 = (String)localProperties.get("ZBA Display");
    String str4 = a(str2, paramString, paramVector, str3, bool, paramReportCriteria, paramHashMap);
    if (str4 == null) {
      return null;
    }
    if (str4.length() != 0)
    {
      localStringBuffer.append(str4);
      localStringBuffer.append(" AND ");
    }
    if (paramString.equals("CreditReport"))
    {
      localStringBuffer.append("dcTransactions.Amount >= 0");
      localStringBuffer.append(" AND ");
    }
    else if (paramString.equals("DebitReport"))
    {
      localStringBuffer.append("dcTransactions.Amount < 0");
      localStringBuffer.append(" AND ");
    }
    localStringBuffer.append("dcAccount.DCAccountID = dcTransactions.DCAccountID");
    String str5 = (String)localProperties.get("TransactionType");
    if (str5 != null)
    {
      int i1 = str5.indexOf("AllTransactionTypes") >= 0 ? 1 : 0;
      localObject = null;
      if ((i1 == 0) && ((paramString.equals("DepositDetail")) || (paramString.equals("TransactionDetail")) || (paramString.equals("CreditReport")) || (paramString.equals("DebitReport")) || (paramString.equals("TransactionDetail")) || (paramString.equals("BalanceDetailOnlyReport")) || (paramString.equals("BalanceDetailReport"))))
      {
        localObject = (String)paramHashMap.get("CustomTransGroupCodesForReport");
        if ((localObject != null) && (((String)localObject).length() != 0))
        {
          StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject, ",", false);
          while (localStringTokenizer.hasMoreTokens()) {
            try
            {
              String str8 = localStringTokenizer.nextToken();
              int i2 = Integer.parseInt(str8);
            }
            catch (NumberFormatException localNumberFormatException3)
            {
              throw new DCException((String)localObject + " does not contain integer values for custom transaction group codes.", localNumberFormatException3);
            }
          }
        }
      }
      if (i1 != 0) {
        paramReportCriteria.setHiddenSearchCriterion("TransactionType", true);
      }
      if ((localObject != null) && (((String)localObject).length() > 0))
      {
        if (localStringBuffer.length() > 0) {
          localStringBuffer.append(" AND ");
        }
        try
        {
          localStringBuffer.append(DBUtil.generateSQLNumericInClause((String)localObject, "dcTransactions.TransSubTypeID"));
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new DCException((String)localObject + " does not contain appropriate integer transaction group code values.", localNumberFormatException1);
        }
      }
    }
    String str6 = (String)localProperties.get("MaximumAmount");
    Object localObject = null;
    if ((str6 != null) && (!str6.equals(""))) {
      try
      {
        localObject = new BigDecimal(str6);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        throw new DCException(" The maximum transaction value specified ( " + str6 + " ) is not in valid " + "amount.", localNumberFormatException2);
      }
    }
    String str7 = (String)localProperties.get("MinimumAmount");
    BigDecimal localBigDecimal = null;
    if ((str7 != null) && (!str7.equals(""))) {
      try
      {
        localBigDecimal = new BigDecimal(str7);
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        throw new DCException(" The minimum transaction value specified ( " + str7 + " ) is not in valid " + "amount.", localNumberFormatException4);
      }
    }
    if ((localObject != null) || (localBigDecimal != null))
    {
      if (localStringBuffer.length() != 0) {
        localStringBuffer.append(" AND ");
      }
      if ((paramString.equals("CreditReport")) || (paramString.equals("AccountHistory")))
      {
        if (localBigDecimal != null) {
          localStringBuffer.append("dcTransactions.Amount").append(" >= ").append(localBigDecimal);
        }
        if (localObject != null)
        {
          if (localBigDecimal != null) {
            localStringBuffer.append(" AND ");
          }
          localStringBuffer.append("dcTransactions.Amount").append(" <= ").append(localObject);
        }
      }
      else if (paramString.equals("DebitReport"))
      {
        if (localBigDecimal != null) {
          localStringBuffer.append("dcTransactions.Amount").append(" <= ").append(localBigDecimal.negate());
        }
        if (localObject != null)
        {
          if (localBigDecimal != null) {
            localStringBuffer.append(" AND ");
          }
          localStringBuffer.append("dcTransactions.Amount").append(" >= ").append(((BigDecimal)localObject).negate());
        }
      }
      else
      {
        localStringBuffer.append(" ( ( ");
        if (localBigDecimal != null) {
          localStringBuffer.append("dcTransactions.Amount").append(" >= ").append(localBigDecimal);
        } else {
          localStringBuffer.append("dcTransactions.Amount").append(" >= ").append("0");
        }
        if (localObject != null)
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append("dcTransactions.Amount").append(" <= ").append(localObject);
        }
        localStringBuffer.append(" ) OR ( ");
        if (localBigDecimal != null) {
          localStringBuffer.append("dcTransactions.Amount").append(" <= ").append(localBigDecimal.negate());
        } else {
          localStringBuffer.append("dcTransactions.Amount").append(" <= ").append("0");
        }
        if (localObject != null)
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append("dcTransactions.Amount").append(" >= ").append(((BigDecimal)localObject).negate());
        }
        localStringBuffer.append(" ) ) ");
      }
    }
    return localStringBuffer.toString();
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria, String paramString, boolean paramBoolean)
    throws DCException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramReportSortCriteria != null)
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      ReportSortCriterion localReportSortCriterion = null;
      String str1 = null;
      String str2 = null;
      String str3 = null;
      int i1 = 0;
      int i2 = 0;
      while (localIterator.hasNext())
      {
        localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        str1 = localReportSortCriterion.getName();
        str2 = null;
        if (str1.equals("AccountNumber")) {
          str2 = "dcAccount.AccountID";
        } else if (str1.equals("RoutingNum")) {
          str2 = "dcAccount.RoutingNumber";
        } else if (str1.equals("ProcessingDate")) {
          str2 = "dcTransactions.DataDate";
        } else if (str1.equals("Amount"))
        {
          if (i2 == 0)
          {
            str2 = "dcTransactions.Amount";
            i2 = 1;
          }
        }
        else if (str1.equals("PayeePayor")) {
          str2 = "dcTransactions.PayeePayor";
        } else if (str1.equals("PayorNumber")) {
          str2 = "dcTransactions.PayorNum";
        } else if (str1.equals("OriginatingUser")) {
          str2 = "dcTransactions.OrigUser";
        } else if (str1.equals("PONumber")) {
          str2 = "dcTransactions.PONum";
        } else if (str1.equals("TransactionReferenceNumber")) {
          str2 = "dcTransactions.ReferenceNumber";
        } else if (str1.equals("BankReferenceNumber")) {
          str2 = "dcTransactions.BankRefNum";
        } else if (str1.equals("CustomerReferenceNumber")) {
          str2 = "dcTransactions.CustRefNum";
        } else if (str1.equals("TransactionType"))
        {
          if (paramString.equals("AccountHistory")) {
            str2 = "transTypeDesc.TRANS_TYPE_DESC";
          }
        }
        else if (str1.equals("TransactionSubType")) {
          str2 = "dcTransactions.TransSubTypeID";
        } else if (str1.equals("TransactionIndex")) {
          str2 = "dcTransactions.DCTransactionIndex";
        } else if (str1.equals("SubTypeDesc")) {
          str2 = "subTypeDesc.DESCRIPTION";
        } else if (str1.equals("Description")) {
          str2 = "dcTransactions.Description";
        }
        if (str2 != null)
        {
          if (i1 == 0) {
            i1 = 1;
          } else {
            localStringBuffer.append(", ");
          }
          if ((!paramBoolean) && (str2.equals("dcTransactions.Amount"))) {
            str3 = localReportSortCriterion.getAsc() ? "DESC" : "ASC";
          } else {
            str3 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
          }
          localStringBuffer.append(str2);
          localStringBuffer.append(" ");
          localStringBuffer.append(str3);
          if ((i2 == 0) && ((str2.equals("dcTransactions.BankRefNum")) || (str2.equals("dcTransactions.CustRefNum"))))
          {
            localStringBuffer.append(", ");
            localStringBuffer.append("dcTransactions.Amount");
            i2 = 1;
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  private static ResultSet a(PreparedStatement[] paramArrayOfPreparedStatement, String paramString1, String paramString2, String paramString3, Vector paramVector, Connection paramConnection)
    throws DCException
  {
    return a(paramArrayOfPreparedStatement, paramString1, paramString2, paramString3, paramVector, paramConnection, null, null);
  }
  
  private static ResultSet a(PreparedStatement[] paramArrayOfPreparedStatement, String paramString1, String paramString2, String paramString3, Vector paramVector, Connection paramConnection, String paramString4)
    throws DCException
  {
    return a(paramArrayOfPreparedStatement, paramString1, paramString2, paramString3, paramVector, paramConnection, paramString4, null);
  }
  
  private static ResultSet a(PreparedStatement[] paramArrayOfPreparedStatement, String paramString1, String paramString2, String paramString3, Vector paramVector, Connection paramConnection, String paramString4, String paramString5)
    throws DCException
  {
    String str = null;
    ResultSet localResultSet = null;
    try
    {
      str = a(paramString1, paramString2, paramString3, paramString5);
      paramArrayOfPreparedStatement[0] = DCUtil.prepareStatement(paramConnection, str, 1004, 1007);
      try
      {
        if (paramString4 != null) {
          paramArrayOfPreparedStatement[0].setMaxRows(Integer.parseInt(paramString4));
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new Exception("The report option: MaxDisplaySize, specified for the report is not a valid number ");
      }
      int i1 = paramVector.size();
      for (int i2 = 1; i2 <= i1; i2++) {
        DCUtil.fillValue(paramArrayOfPreparedStatement[0], i2, paramVector.remove(0));
      }
      localResultSet = DBUtil.executeQuery(paramArrayOfPreparedStatement[0], str);
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    return localResultSet;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      if (localStringBuffer.toString().indexOf("WHERE") == -1) {
        localStringBuffer.append(" WHERE ");
      } else {
        localStringBuffer.append(" AND ");
      }
      localStringBuffer.append(paramString2);
    }
    if ((paramString3 != null) && (paramString3.length() != 0))
    {
      localStringBuffer.append(" ORDER BY ");
      localStringBuffer.append(paramString3);
    }
    if (paramString4 != null) {
      localStringBuffer.insert(0, paramString4);
    }
    return localStringBuffer.toString();
  }
  
  private static StringBuffer a(ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    if (localProperties != null)
    {
      Set localSet = localProperties.keySet();
      Iterator localIterator = localSet.iterator();
      String str1 = null;
      String str2 = null;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str2 = localProperties.getProperty(str1);
        if (str2.trim().length() == 0)
        {
          paramReportCriteria.setHiddenSearchCriterion(str1, true);
        }
        else
        {
          str3 = null;
          str4 = null;
          if (!str1.equals("Accounts"))
          {
            if (str1.equals("DataClassification"))
            {
              str3 = jdField_do(paramString);
              if (str2.equals("I")) {
                str4 = "='I'";
              } else {
                str4 = "='P'";
              }
            }
            else if ((!str1.equals("StartDate")) && (!str1.equals("EndDate")) && (!str1.equals("MinimumAmount")) && (!str1.equals("MaximumAmount")))
            {
              if (str1.equals("PayeePayor"))
              {
                str3 = "dcTransactions.PayeePayor";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("PayorNumber"))
              {
                str3 = "dcTransactions.PayorNum";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("OriginatingUser"))
              {
                str3 = "dcTransactions.OrigUser";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("PONumber"))
              {
                str3 = "dcTransactions.PONum";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("TransactionReferenceNumber"))
              {
                str3 = "dcTransactions.ReferenceNumber";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("BankReferenceNumber"))
              {
                str3 = "dcTransactions.BankRefNum";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(DCAdapter.padRefNum(str2, DCAdapter.getBankReferenceNumberType())) + "'";
              }
              else if (str1.equals("Bank Reference Number (Min)"))
              {
                str3 = "dcTransactions.BankRefNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(DCAdapter.padRefNum(str2, DCAdapter.getBankReferenceNumberType())) + "'";
              }
              else if (str1.equals("Bank Reference Number (Max)"))
              {
                str3 = "dcTransactions.BankRefNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(DCAdapter.padRefNum(str2, DCAdapter.getBankReferenceNumberType())) + "'";
              }
              else if (str1.equals("CustomerReferenceNumber"))
              {
                str3 = "dcTransactions.CustRefNum";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(DCAdapter.padRefNum(str2, DCAdapter.getCustomerReferenceNumberType())) + "'";
              }
              else if (str1.equals("Customer Reference Number (Min)"))
              {
                str3 = "dcTransactions.CustRefNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(DCAdapter.padRefNum(str2, DCAdapter.getCustomerReferenceNumberType())) + "'";
              }
              else if (str1.equals("Customer Reference Number (Max)"))
              {
                str3 = "dcTransactions.CustRefNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(DCAdapter.padRefNum(str2, DCAdapter.getCustomerReferenceNumberType())) + "'";
              }
              else if (str1.equals("SearchForNullBankRefNum"))
              {
                str3 = "dcTransactions.BankRefNum";
                str4 = " is null ";
              }
              else if (str1.equals("SearchForNullCustRefNum"))
              {
                str3 = "dcTransactions.CustRefNum";
                str4 = " is null ";
              }
              else if (str1.equals("TransactionSubType"))
              {
                try
                {
                  if ("AllTransactionSubtypes".equals(str2.trim()))
                  {
                    paramReportCriteria.setHiddenSearchCriterion(str1, true);
                  }
                  else
                  {
                    str5 = new String(localStringBuffer).trim();
                    if (str5.length() != 0) {
                      localStringBuffer.append(" AND ");
                    }
                    localStringBuffer.append(DBUtil.generateSQLNumericInClause(str2, "dcTransactions.TransSubTypeID"));
                  }
                }
                catch (NumberFormatException localNumberFormatException)
                {
                  throw new DCException(str2 + " does not contain appropriate integer transaction subtype values.", localNumberFormatException);
                }
              }
              else
              {
                if (str1.equals("TransactionStatus"))
                {
                  paramReportCriteria.setHiddenSearchCriterion(str1, true);
                  continue;
                }
                if (str1.equals("TransactionType")) {
                  continue;
                }
                if (str1.equals("FixedDepositInstrumentNumber"))
                {
                  str3 = "dcTransactions.InstNumber";
                  str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
                }
                else if (str1.equals("FixedDepositInstrumentBankName"))
                {
                  str3 = "dcTransactions.InstBankName";
                  str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
                }
                else
                {
                  Date localDate;
                  String str6;
                  DCException localDCException;
                  if (str1.equals("Datasource Load Start Time"))
                  {
                    str3 = "dcTransactions.DataSourceLoadTime";
                    localDate = null;
                    try
                    {
                      localDate = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str2);
                    }
                    catch (ParseException localParseException1)
                    {
                      str6 = "The data source load start time is not in the correct format ('MM/dd/yyyy HH:mm:ss').";
                      localDCException = new DCException(365, str6);
                      DebugLog.throwing(str6, localDCException);
                      throw localDCException;
                    }
                    str4 = " >= " + DBUtil.fixDate(DCAdapter.CONNECTIONTYPE, localDate);
                  }
                  else
                  {
                    if (!str1.equals("Datasource Load End Time")) {
                      continue;
                    }
                    str3 = "dcTransactions.DataSourceLoadTime";
                    localDate = null;
                    try
                    {
                      localDate = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str2);
                    }
                    catch (ParseException localParseException2)
                    {
                      str6 = "The data source load end time is not in the correct format ('MM/dd/yyyy HH:mm:ss').";
                      localDCException = new DCException(365, str6);
                      DebugLog.throwing(str6, localDCException);
                      throw localDCException;
                    }
                    str4 = " <= " + DBUtil.fixDate(DCAdapter.CONNECTIONTYPE, localDate);
                  }
                }
              }
            }
            if ((str3 != null) && (str4 != null))
            {
              str5 = new String(localStringBuffer).trim();
              if (str5.length() != 0) {
                localStringBuffer.append(" AND ");
              }
              localStringBuffer.append(str3);
              localStringBuffer.append(str4);
            }
          }
        }
      }
    }
    return localStringBuffer;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    localStringBuffer.append(':').append(paramString2);
    if (paramString3 != null) {
      localStringBuffer.append(':').append(paramString3);
    }
    return localStringBuffer.toString();
  }
  
  private static Currency jdField_if(ResultSet paramResultSet, int paramInt, Locale paramLocale)
    throws SQLException
  {
    return DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(paramInt), paramLocale);
  }
  
  private static Currency a(ResultSet paramResultSet, int paramInt, Locale paramLocale, String paramString)
    throws SQLException
  {
    return DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(paramInt), paramString, paramLocale);
  }
  
  private static String jdField_do(String paramString)
  {
    if ((paramString.equals("BalanceSheetReport")) || (paramString.equals("BalanceSheetSummary")) || (paramString.equals("CashFlowForecastReport")) || (paramString.equals("CashFlowReport")) || (paramString.equals("BalanceSummaryReport")) || (paramString.equals("BalanceDetailOnlyReport"))) {
      return "dcAcctHistory.DataClassification";
    }
    return "dcTransactions.DataClassification";
  }
  
  private static String a(String paramString1, String paramString2, Vector paramVector, String paramString3, boolean paramBoolean, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    HashMap localHashMap = (HashMap)paramHashMap.get("StartDates");
    if (localHashMap == null)
    {
      localObject1 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log((String)localObject1);
      throw new DCException((String)localObject1);
    }
    Object localObject1 = (HashMap)paramHashMap.get("EndDates");
    if (localObject1 == null)
    {
      String str1 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log(str1);
      throw new DCException(str1);
    }
    boolean bool1 = (paramString3 == null) || (paramString3.length() <= 0);
    StringBuffer localStringBuffer = new StringBuffer("");
    Accounts localAccounts1 = null;
    if ((paramString1.equals("AllAccounts")) || (paramString1.equals("AllAccounts")))
    {
      if (paramHashMap == null) {
        throw new DCException("Extra hashmap is null.");
      }
      localAccounts1 = (Accounts)paramHashMap.get("Accounts");
      if ((localAccounts1 == null) || (localAccounts1.isEmpty())) {
        throw new DCException("The Accounts object was not found in extra.");
      }
    }
    else
    {
      if (paramHashMap == null) {
        throw new DCException("Extra hashmap is null.");
      }
      localObject2 = (Accounts)paramHashMap.get("AccountsForReport");
      if ((localObject2 == null) || (((Accounts)localObject2).isEmpty())) {
        throw new DCException("The Accounts object was not found in extra.");
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, ",");
      int i1 = localStringTokenizer.countTokens();
      if (i1 < 1) {
        throw new DCException("Value for SearchCriteria key Accounts has incorrect format.");
      }
      localAccounts1 = new Accounts(((Accounts)localObject2).getLocale());
      for (int i2 = 0; i2 < i1; i2++)
      {
        localObject3 = localStringTokenizer.nextToken();
        Account localAccount1 = a((String)localObject3, (Accounts)localObject2);
        if (localAccount1 == null) {
          throw new DCException("Account with search criterion value as " + (String)localObject3 + " does not exist in collection given.");
        }
        localAccounts1.add(localAccount1);
      }
    }
    Object localObject2 = paramReportCriteria.getSearchCriteria();
    boolean bool2 = new Boolean(((Properties)localObject2).getProperty("SupressSubAccounts")).booleanValue();
    String str2 = ((Properties)localObject2).getProperty("DisplayZBATransactions");
    Accounts localAccounts2 = null;
    int i4;
    if ((bool2) || (str2 != null))
    {
      localObject3 = (Business)paramHashMap.get("BusinessForReport");
      if (localObject3 == null) {
        throw new DCException("The Business object was not found in extra.");
      }
      try
      {
        localAccounts2 = TreasuryDirect.filterNonSubAccounts(null, (Business)localObject3, localAccounts1, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        throw new DCException("Unable to retrieve a list of sub-accounts for the given accounts.", localCSILException);
      }
      if ((bool2) && (localAccounts2.size() == localAccounts1.size()))
      {
        int i3 = 0;
        for (i4 = 0; i4 < localAccounts1.size(); i4++)
        {
          Account localAccount3 = (Account)localAccounts1.get(i4);
          if (localAccount3.isMaster())
          {
            i3 = 1;
            break;
          }
        }
        if (i3 == 0) {
          return null;
        }
      }
    }
    localStringBuffer.append("( ");
    Object localObject3 = localAccounts1.iterator();
    while (((Iterator)localObject3).hasNext())
    {
      Account localAccount2 = (Account)((Iterator)localObject3).next();
      i4 = 0;
      boolean bool4 = false;
      boolean bool3;
      if ((bool2) || (str2 != null))
      {
        bool4 = localAccounts2.contains(localAccount2);
        bool3 = localAccount2.isMaster();
      }
      if ((!bool2) || (!bool4) || (bool3))
      {
        localStringBuffer.append(a(paramString2, localAccount2, paramVector, (Calendar)localHashMap.get(localAccount2.getRoutingNum()), (Calendar)((HashMap)localObject1).get(localAccount2.getRoutingNum()), paramBoolean, bool1, paramReportCriteria, bool3, bool4));
        if (((Iterator)localObject3).hasNext()) {
          localStringBuffer.append(" OR ");
        }
      }
    }
    localStringBuffer.append(" ) ");
    return localStringBuffer.toString();
  }
  
  private static boolean a(int paramInt1, int paramInt2, ReportResult paramReportResult)
  {
    if ((paramInt1 > 0) && (paramInt2 > paramInt1))
    {
      if (paramReportResult != null)
      {
        HashMap localHashMap = paramReportResult.getProperties();
        if (localHashMap == null) {
          localHashMap = new HashMap();
        }
        localHashMap.put("TRUNCATED", Integer.toString(paramInt2 - 1));
        paramReportResult.setProperties(localHashMap);
      }
      return true;
    }
    return false;
  }
  
  private static String jdField_for(String paramString)
  {
    if ((paramString.equals("GeneralLedgerReport")) || (paramString.equals("CashFlowNetIncrease"))) {
      return "dcTransactions.DataDate";
    }
    if ((paramString.equals("DepositDetail")) || (paramString.equals("TransactionDetail")) || (paramString.equals("AccountHistory")) || (paramString.equals("CreditReport")) || (paramString.equals("DebitReport"))) {
      return "dcTransactions.DataDate";
    }
    if (paramString.equals("CustomSummaryReport")) {
      return "dcextaccsummary.DataDate";
    }
    return "dcAcctHistory.DataDate";
  }
  
  private static com.ffusion.beans.DateTime a(ResultSet paramResultSet, int paramInt, Locale paramLocale)
    throws SQLException
  {
    return DCUtil.getTimestampColumn(paramResultSet.getTimestamp(paramInt), paramLocale);
  }
  
  static int a(ReportCriteria paramReportCriteria)
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str = localProperties.getProperty("MAX_DAYS_IN_DATE_RANGE");
    int i1 = 31;
    if (str != null) {
      try
      {
        i1 = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.log(Level.WARNING, "The maximum number of days in a date range report option has been specified incorrectly. The value provided ( " + str + " ) is not a valid integer.");
      }
    }
    return i1;
  }
  
  private static String a(String paramString, Account paramAccount, Vector paramVector, Calendar paramCalendar1, Calendar paramCalendar2, boolean paramBoolean1, boolean paramBoolean2, ReportCriteria paramReportCriteria, boolean paramBoolean3, boolean paramBoolean4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("( ");
    localStringBuffer.append("dcAccount.AccountID");
    localStringBuffer.append(" = '");
    localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramAccount.getID()));
    localStringBuffer.append("' AND ");
    localStringBuffer.append("dcAccount.BankID");
    localStringBuffer.append(" = '");
    localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramAccount.getBankID()));
    if (paramAccount.getRoutingNum() != null)
    {
      localStringBuffer.append("' AND ");
      localStringBuffer.append("dcAccount.RoutingNumber");
      localStringBuffer.append(" = '");
      localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramAccount.getRoutingNum()));
    }
    localStringBuffer.append("'");
    if (paramCalendar1 != null)
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append(jdField_for(paramString));
      localStringBuffer.append(" >= ?");
      paramVector.add(paramCalendar1);
    }
    if (paramCalendar2 != null)
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append(jdField_for(paramString));
      localStringBuffer.append(" < ?");
      localObject = Calendar.getInstance();
      ((Calendar)localObject).setTimeInMillis(paramCalendar2.getTime().getTime() + 1L);
      paramVector.add(localObject);
    }
    Object localObject = paramReportCriteria.getSearchCriteria();
    String str1 = ((Properties)localObject).getProperty("DisplayZBATransactions");
    if ((str1 != null) && ((paramBoolean3) || (paramBoolean4)))
    {
      int i1 = 0;
      if (str1.equals("None")) {
        i1 = 1;
      } else if (str1.equals("SubAccounts"))
      {
        if (!paramBoolean4) {
          i1 = 1;
        }
      }
      else if ((str1.equals("MasterAccounts")) && (!paramBoolean3)) {
        i1 = 1;
      }
      if (i1 != 0)
      {
        localStringBuffer.append(" AND ");
        localStringBuffer.append("dcTransactions.TransSubTypeID");
        localStringBuffer.append(" NOT IN (275, 575) ");
      }
    }
    else
    {
      String str2 = (String)((Properties)localObject).get("ZBA Display");
      String str3 = null;
      if (paramBoolean1)
      {
        if (!paramBoolean2)
        {
          if ((str2 == null) || (str2.length() <= 0)) {
            str3 = "B";
          } else {
            str3 = str2;
          }
        }
        else if ((paramAccount.getZBAFlag() == null) || (paramAccount.getZBAFlag().length() <= 0)) {
          str3 = "B";
        } else {
          str3 = paramAccount.getZBAFlag();
        }
        if (str3.equalsIgnoreCase("C"))
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append("dcTransactions.TransSubTypeID");
          localStringBuffer.append(" NOT IN(575)");
        }
        else if (str3.equalsIgnoreCase("D"))
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append("dcTransactions.TransSubTypeID");
          localStringBuffer.append(" NOT IN(275)");
        }
        else if (str3.equalsIgnoreCase("N"))
        {
          localStringBuffer.append(" AND ");
          localStringBuffer.append("dcTransactions.TransSubTypeID");
          localStringBuffer.append(" NOT IN(275, 575)");
        }
      }
    }
    localStringBuffer.append(" ) ");
    return localStringBuffer.toString();
  }
  
  private static boolean a(ResultSet paramResultSet)
    throws SQLException
  {
    boolean bool1 = DCAdapter.CONNECTIONTYPE.equalsIgnoreCase("ASE");
    boolean bool2 = false;
    if (bool1)
    {
      bool2 = paramResultSet.next();
      paramResultSet.beforeFirst();
    }
    else
    {
      bool2 = paramResultSet.isBeforeFirst();
    }
    return bool2;
  }
  
  protected static ReportResult generateConsumerAcctHistReport(ReportCriteria paramReportCriteria, SecureUser paramSecureUser, HashMap paramHashMap)
    throws DCException
  {
    Locale localLocale = paramReportCriteria.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    paramReportCriteria.setHiddenSearchCriterion("DataClassification", true);
    String str1 = localProperties2.getProperty("DATEFORMAT");
    String str2 = localProperties1.getProperty("Date Range Type");
    Accounts localAccounts = new Accounts(paramReportCriteria.getLocale());
    String str3 = localProperties1.getProperty("Accounts");
    if (str3 == null) {
      str3 = "AllAccounts";
    }
    int i1;
    Object localObject3;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    if (accountCriterionHasAllAccounts(str3))
    {
      localAccounts = (Accounts)paramHashMap.get("Accounts");
      localAccounts.setLocale(paramReportCriteria.getLocale());
    }
    else
    {
      localObject1 = new StringTokenizer(str3, ",");
      i1 = ((StringTokenizer)localObject1).countTokens();
      localObject3 = (Accounts)paramHashMap.get("AccountsForReport");
      for (int i3 = 0; i3 < i1; i3++)
      {
        String str4 = ((StringTokenizer)localObject1).nextToken();
        localObject6 = new StringTokenizer(str4, ":");
        int i4 = ((StringTokenizer)localObject6).countTokens();
        if ((i4 == 3) || (i4 == 5))
        {
          localObject7 = ((StringTokenizer)localObject6).nextToken();
          localObject8 = ((StringTokenizer)localObject6).nextToken();
          String str5 = ((StringTokenizer)localObject6).nextToken();
          Iterator localIterator = ((Accounts)localObject3).iterator();
          while (localIterator.hasNext())
          {
            Account localAccount = (Account)localIterator.next();
            if ((((String)localObject7).equals(localAccount.getID())) && (((String)localObject8).equals(localAccount.getBankID())) && (str5.equals(localAccount.getRoutingNum())))
            {
              localAccounts.add(localAccount);
              break;
            }
          }
        }
        else
        {
          throw new DCException(313, "Value for SearchCriteria key Accounts has incorrect format.");
        }
      }
    }
    Object localObject1 = localProperties1.getProperty("TransactionType");
    Object localObject4;
    Object localObject5;
    Object localObject2;
    if (localObject1 != null) {
      if (!((String)localObject1).equals("AllTransactionTypes"))
      {
        i1 = Integer.parseInt(localProperties1.getProperty("TransactionType"));
        localObject3 = new HashMap();
        localObject4 = null;
        try
        {
          localObject4 = TransactionTypeCache.getTransactionTypes(1, paramHashMap);
        }
        catch (FFIUtilException localFFIUtilException)
        {
          localReportResult.setError(localFFIUtilException);
          throw new DCException(313, "A problem occured retrieving the Transaction Type Cache.", localFFIUtilException);
        }
        localObject5 = (TransactionTypeInfo)((HashMap)localObject4).get(new Integer(i1));
        if (localObject5 != null)
        {
          localObject6 = ((TransactionTypeInfo)localObject5).getDescription(localLocale);
          paramReportCriteria.setDisplayValue("TransactionType", (String)localObject6);
        }
      }
      else
      {
        localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "AllTransactionTypes", null);
        paramReportCriteria.setDisplayValue("TransactionType", (String)((LocalizableString)localObject2).localize(localLocale));
      }
    }
    updateConsAcctHistAccountDisplayValues(paramReportCriteria, localAccounts);
    try
    {
      localObject2 = localProperties2.getProperty("FORMAT");
      if (("QIF".equals(localObject2)) || ("QFX".equals(localObject2)) || ("OFX".equals(localObject2)))
      {
        paramReportCriteria.setDisplayValue("EndDate", localProperties1.getProperty("EndDate"));
        paramReportCriteria.setDisplayValue("StartDate", localProperties1.getProperty("StartDate"));
      }
      paramReportCriteria.setHiddenSearchCriterion("ShowBalance", true);
      paramReportCriteria.setHiddenSearchCriterion("ShowMemo", true);
      int i2 = localAccounts.size();
      if ("Since Last Export".equalsIgnoreCase(str2)) {
        if (i2 > 1)
        {
          localObject4 = new LocalizableString("com.ffusion.beans.reporting.reports", "Text_Since_Last_Export_Multi", null);
          paramReportCriteria.getSearchCriteria().setProperty("StartDate", "");
          paramReportCriteria.setDisplayValue("StartDate", (String)((LocalizableString)localObject4).localize(localLocale));
          paramReportCriteria.setHiddenSearchCriterion("Date Range", true);
          paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
        }
        else if (i2 == 1)
        {
          a(paramSecureUser, (Account)localAccounts.get(0), paramReportCriteria, paramHashMap);
        }
      }
      localReportResult.init(paramReportCriteria);
      localObject4 = new ReportDataDimensions(localLocale);
      ((ReportDataDimensions)localObject4).setNumColumns(0);
      ((ReportDataDimensions)localObject4).setNumRows(0);
      localReportResult.setDataDimensions((ReportDataDimensions)localObject4);
      localAccounts.setDateFormat(str1);
      localObject5 = localAccounts.iterator();
      localObject6 = new PagingContext(null, null);
      paramHashMap.put("PAGESIZE", "10000000");
      HashMap localHashMap = new HashMap();
      localHashMap.put("ReportCriteria", paramReportCriteria);
      ((PagingContext)localObject6).setMap(localHashMap);
      while (((Iterator)localObject5).hasNext())
      {
        localObject7 = (Account)((Iterator)localObject5).next();
        a(paramSecureUser, (Account)localObject7, paramReportCriteria, paramHashMap);
        localObject8 = Banking.getPagedTransactions(paramSecureUser, (Account)localObject7, (PagingContext)localObject6, paramHashMap);
        int i5 = 0;
        if (("QIF".equals(localObject2)) || ("QFX".equals(localObject2)) || ("OFX".equals(localObject2))) {
          i5 = 1;
        }
        if (i5 != 0) {
          fillConsumerAccountHistoryFull((Account)localObject7, (Transactions)localObject8, localReportResult);
        } else {
          fillConsumerAccountHistory((Account)localObject7, (Transactions)localObject8, localReportResult, paramReportCriteria, paramHashMap);
        }
      }
      localObject7 = localReportResult;
      return localObject7;
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      throw new DCException(313, "Could not generate report.", localException);
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (Throwable localThrowable2) {}
    }
  }
  
  private static void a(SecureUser paramSecureUser, Account paramAccount, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      String str = paramReportCriteria.getSearchCriteria().getProperty("Date Range Type");
      if ("Since Last Export".equalsIgnoreCase(str))
      {
        localObject1 = (HashMap)paramHashMap.get("LAST EXPORT DATES");
        localObject2 = paramAccount.getID() + ":" + paramAccount.getBankID() + ":" + paramAccount.getRoutingNum();
        com.ffusion.util.beans.DateTime localDateTime = (com.ffusion.util.beans.DateTime)((HashMap)localObject1).get(localObject2);
        paramHashMap.put("LAST EXPORT DATE", localDateTime);
      }
      paramHashMap.remove("CURRENT EXPORT DATE");
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, null, null, paramHashMap);
      localObject1 = (com.ffusion.util.beans.DateTime)paramHashMap.get("CURRENT EXPORT DATE");
      Object localObject2 = (HashMap)paramHashMap.get("CURRENT EXPORT DATES");
      if (localObject2 == null)
      {
        localObject2 = new HashMap();
        paramHashMap.put("CURRENT EXPORT DATES", localObject2);
      }
      if (localObject1 == null) {
        localObject1 = new com.ffusion.util.beans.DateTime(new Date(), paramAccount.getLocale());
      }
      ((HashMap)paramHashMap.get("CURRENT EXPORT DATES")).put(paramAccount, localObject1);
      paramHashMap.remove("CURRENT EXPORT DATE");
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log((String)localObject1);
      if (localCSILException.getCode() == -1009) {
        throw new BankingException(localCSILException.getServiceError());
      }
      throw new BankingException(localCSILException.getCode());
    }
  }
  
  protected static void updateConsAcctHistAccountDisplayValues(ReportCriteria paramReportCriteria, Accounts paramAccounts)
  {
    int i1 = paramAccounts.size();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i2 = 0; i2 < i1; i2++)
    {
      if (i2 != 0) {
        localStringBuffer.append(", ");
      }
      localStringBuffer.append(((Account)paramAccounts.get(i2)).getConsumerExportDisplayText());
    }
    paramReportCriteria.setDisplayValue("Accounts", localStringBuffer.toString());
  }
  
  protected static int fillConsumerAccountHistoryFull(Account paramAccount, Transactions paramTransactions, ReportResult paramReportResult)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    a[] arrayOfa = aV;
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(arrayOfa.length);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    a.a(localReportResult, localLocale, arrayOfa);
    int i1 = 0;
    Iterator localIterator = paramTransactions.iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportDataItems.add().setData(paramAccount.getID());
      localReportDataItems.add().setData(paramAccount.getBankID());
      localReportDataItems.add().setData(paramAccount.getRoutingNum());
      localReportDataItems.add().setData(paramAccount.getCurrencyCode());
      localReportDataItems.add().setData(localTransaction.getID());
      localReportDataItems.add().setData(new Integer(localTransaction.getTypeValue()));
      localReportDataItems.add().setData(new Integer(localTransaction.getCategory()));
      localReportDataItems.add().setData(localTransaction.getDescription());
      localReportDataItems.add().setData(localTransaction.getReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getMemo());
      localReportDataItems.add().setData(localTransaction.getAmountValue());
      localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      localReportDataItems.add().setData(localTransaction.getTrackingID());
      localReportDataItems.add().setData(new Integer((int)localTransaction.getTransactionIndex()));
      localReportDataItems.add().setData(localTransaction.getPostingDate());
      localReportDataItems.add().setData(localTransaction.getDueDate());
      localReportDataItems.add().setData(new Float(localTransaction.getFixedDepositRate()));
      localReportDataItems.add().setData(localTransaction.getPayeePayor());
      localReportDataItems.add().setData(localTransaction.getPayorNum());
      localReportDataItems.add().setData(localTransaction.getOrigUser());
      localReportDataItems.add().setData(localTransaction.getPONum());
      localReportDataItems.add().setData(localTransaction.getImmediateAvailAmount());
      localReportDataItems.add().setData(localTransaction.getOneDayAvailAmount());
      localReportDataItems.add().setData(localTransaction.getMoreThanOneDayAvailAmount());
      localReportDataItems.add().setData(localTransaction.getBankReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getCustomerReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getProcessingDate());
      localReportDataItems.add().setData(localTransaction.getInstrumentNumber());
      localReportDataItems.add().setData(localTransaction.getInstrumentBankName());
      localReportDataItems.add().setData(localTransaction.getValueDate());
      localReportDataItems.add().setData(new Integer(localTransaction.getSubTypeValue()));
      localReportDataItems.add().setData(localTransaction.getDateValue());
      localReportDataItems.add().setData(localTransaction.getDataClassification());
      localReportResult.addRow(localReportRow);
      i1++;
    }
    return i1;
  }
  
  protected static int fillConsumerAccountHistory(Account paramAccount, Transactions paramTransactions, ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    int i1 = 0;
    int i2 = 1;
    int i3 = 1;
    String str1 = (String)paramReportCriteria.getSearchCriteria().get("ShowBalance");
    if ((str1 == null) || (!str1.equalsIgnoreCase("true"))) {
      i2 = 0;
    }
    String str2 = (String)paramReportCriteria.getSearchCriteria().get("ShowMemo");
    if ((str2 == null) || (!str2.equalsIgnoreCase("true"))) {
      i3 = 0;
    }
    a[] arrayOfa1 = bh;
    a[] arrayOfa2 = bg;
    a[] arrayOfa3 = az;
    if (i2 == 0)
    {
      arrayOfa3 = be;
      arrayOfa2 = bu;
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if (paramTransactions.size() == 0)
    {
      arrayOfa3 = J;
      ReportResult localReportResult = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult);
      ReportHeading localReportHeading = new ReportHeading(localLocale);
      localObject1 = new Object[1];
      localObject1[0] = paramAccount.getConsumerExportDisplayText();
      localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Dyn_Account", (Object[])localObject1);
      localReportHeading.setLabel((String)((LocalizableString)localObject2).localize(paramReportCriteria.getLocale()));
      localReportResult.setHeading(localReportHeading);
      a(localReportResult, arrayOfa3.length, -1);
      addColumnsNoLabels(paramReportResult, localLocale, arrayOfa3);
      localObject3 = new ReportRow(localLocale);
      localObject4 = new ReportDataItems(localLocale);
      ((ReportRow)localObject3).setDataItems((ReportDataItems)localObject4);
      ((ReportDataItems)localObject4).add().setData(ReportConsts.getText(10504, paramReportCriteria.getLocale()));
      paramReportResult.addRow((ReportRow)localObject3);
      i1 = 2;
    }
    else
    {
      int i4 = 1;
      int i5 = 0;
      localObject1 = paramTransactions.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = new ReportResult(localLocale);
        paramReportResult.addSubReport((ReportResult)localObject2);
        if (i5 == 0)
        {
          localObject3 = new ReportHeading(localLocale);
          localObject4 = new Object[1];
          localObject4[0] = paramAccount.getConsumerExportDisplayText();
          localObject5 = new LocalizableString("com.ffusion.beans.reporting.reports", "Dyn_Account", (Object[])localObject4);
          ((ReportHeading)localObject3).setLabel((String)((LocalizableString)localObject5).localize(paramReportCriteria.getLocale()));
          ((ReportResult)localObject2).setHeading((ReportHeading)localObject3);
          a((ReportResult)localObject2, arrayOfa2.length, -1);
          a.a((ReportResult)localObject2, localLocale, arrayOfa3);
          i5 = 1;
        }
        else
        {
          ((ReportResult)localObject2).setHeading(null);
          a((ReportResult)localObject2, arrayOfa2.length, -1);
          addColumnsNoLabels((ReportResult)localObject2, localLocale, arrayOfa2);
        }
        localObject3 = (Transaction)((Iterator)localObject1).next();
        localObject4 = new ReportRow(localLocale);
        if (i4 % 2 == 0) {
          ((ReportRow)localObject4).set("CELLBACKGROUND", "reportDataShaded");
        }
        Object localObject5 = new ReportDataItems(localLocale);
        ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getProcessingDate());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getDescription());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getType());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getReferenceNumber());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getDebitValueAbsolute());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getCreditValue());
        if ((i2 != 0) && (!"P".equals(((Transaction)localObject3).getDataClassification()))) {
          ((ReportDataItems)localObject5).add().setData(null);
        } else if (i2 != 0) {
          ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getRunningBalanceValue());
        }
        ((ReportResult)localObject2).addRow((ReportRow)localObject4);
        i1++;
        if (i3 != 0)
        {
          localObject2 = new ReportResult(localLocale);
          paramReportResult.addSubReport((ReportResult)localObject2);
          ((ReportResult)localObject2).setHeading(null);
          a((ReportResult)localObject2, arrayOfa1.length, -1);
          addColumnsNoLabels((ReportResult)localObject2, localLocale, arrayOfa1);
          localObject4 = new ReportRow(localLocale);
          if (i4 % 2 == 0) {
            ((ReportRow)localObject4).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject5 = new ReportDataItems(localLocale);
          ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
          ((ReportDataItems)localObject5).add().setData(null);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = ((Transaction)localObject3).getMemo();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Dyn_Memo", arrayOfObject);
          ((ReportDataItems)localObject5).add().setData((String)localLocalizableString.localize(paramReportCriteria.getLocale()));
          ((ReportResult)localObject2).addRow((ReportRow)localObject4);
          i1++;
        }
        i4++;
      }
    }
    a(paramReportResult);
    return i1;
  }
  
  private static void a(ReportResult paramReportResult)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    a[] arrayOfa = J;
    paramReportResult.addSubReport(localReportResult);
    localReportResult.setHeading(null);
    a(localReportResult, arrayOfa.length, -1);
    addColumnsNoLabels(localReportResult, paramReportResult.getLocale(), arrayOfa);
    ReportRow localReportRow = new ReportRow(paramReportResult.getLocale());
    ReportDataItems localReportDataItems = new ReportDataItems(paramReportResult.getLocale());
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData(null);
    paramReportResult.addRow(localReportRow);
  }
  
  public static void addColumnsNoLabels(ReportResult paramReportResult, Locale paramLocale, a[] paramArrayOfa)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    for (int i1 = 0; i1 < paramArrayOfa.length; i1++)
    {
      a locala = paramArrayOfa[i1];
      localReportColumn.setLabels(null);
      localReportColumn.setDataType(locala.a);
      localReportColumn.setWidthAsPercent(locala.jdField_for);
      if (locala.jdField_if != null) {
        localReportColumn.setJustification(locala.jdField_if);
      }
      paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    }
  }
  
  private static class a
  {
    private int jdField_new;
    private String jdField_do;
    private String a;
    private int jdField_for;
    private String jdField_if;
    private String jdField_int;
    
    public a(String paramString1, String paramString2)
    {
      this(-1, paramString2, 0, null);
      this.jdField_do = paramString1;
    }
    
    public a(String paramString1, String paramString2, int paramInt)
    {
      this(-1, paramString2, paramInt, null);
      this.jdField_do = paramString1;
    }
    
    public a(String paramString1, String paramString2, int paramInt, String paramString3)
    {
      this(-1, paramString2, paramInt, paramString3);
      this.jdField_do = paramString1;
    }
    
    public a(int paramInt1, String paramString, int paramInt2)
    {
      this(paramInt1, paramString, paramInt2, null);
    }
    
    public a(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3)
    {
      this(paramInt1, paramString1, paramInt2, paramString2);
      this.jdField_int = paramString3;
    }
    
    public a(int paramInt1, String paramString1, int paramInt2, String paramString2)
    {
      this.jdField_new = paramInt1;
      this.a = paramString1;
      this.jdField_for = paramInt2;
      this.jdField_if = paramString2;
    }
    
    public int jdField_for()
    {
      return this.jdField_new;
    }
    
    public int jdField_do()
    {
      return this.jdField_for;
    }
    
    public String a()
    {
      return this.a;
    }
    
    public String jdField_if()
    {
      return this.jdField_if;
    }
    
    public String a(Locale paramLocale)
    {
      if (this.jdField_do == null) {
        return ReportConsts.getColumnName(this.jdField_new, paramLocale);
      }
      return this.jdField_do;
    }
    
    public void a(String paramString)
    {
      this.jdField_do = paramString;
    }
    
    public void a(int paramInt)
    {
      this.jdField_new = paramInt;
    }
    
    public static void a(ReportResult paramReportResult, Locale paramLocale, a[] paramArrayOfa)
      throws RptException
    {
      for (int i = 0; i < paramArrayOfa.length; i++)
      {
        ReportColumn localReportColumn = new ReportColumn(paramLocale);
        a locala = paramArrayOfa[i];
        ArrayList localArrayList = new ArrayList(1);
        if (locala.jdField_do != null) {
          localArrayList.add(locala.jdField_do);
        } else if (locala.jdField_new != -1) {
          localArrayList.add(ReportConsts.getColumnName(locala.jdField_new, paramLocale));
        } else {
          localArrayList = null;
        }
        localReportColumn.setLabels(localArrayList);
        localReportColumn.setDataType(locala.a);
        localReportColumn.setWidthAsPercent(locala.jdField_for);
        if (locala.jdField_if != null) {
          localReportColumn.setJustification(locala.jdField_if);
        }
        if (locala.jdField_int != null) {
          localReportColumn.setReportColumnProperty("DATASTYLE", locala.jdField_int);
        }
        paramReportResult.addColumn(localReportColumn);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dataconsolidator.DCServiceUtil
 * JD-Core Version:    0.7.0.1
 */