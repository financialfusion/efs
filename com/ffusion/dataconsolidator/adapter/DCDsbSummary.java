package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummary;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.util.MapUtil;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

class DCDsbSummary
{
  private static final String m = "INSERT INTO DC_DsbSummary( DCAccountID, DataDate, DataSource, NumItemsPending, TotalDebits, TotalCredits, TotalDTCCredits, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, ChecksPaidEarly, ChecksPaidLate, ChecksPaidLast, FedEstimate, LateDebits, NUMDISBCREDITS, NUMDTCCREDITS, NUMCHECKPAIDEARLY, NUMCHECKPAIDLATE, NUMIMMEDFUNDNEED, NUMFEDESTIMATE, NUMLATEDEBITS, NUMCHECKPAIDLAST, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
  private static final String a = "UPDATE DC_DsbSummary SET DCAccountID=?, DataDate=?, DataSource=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, NUMDISBCREDITS=?, NUMDTCCREDITS=?, NUMCHECKPAIDEARLY=?, NUMCHECKPAIDLATE=?, NUMIMMEDFUNDNEED=?, NUMFEDESTIMATE=?, NUMLATEDEBITS=?, NUMCHECKPAIDLAST=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ";
  private static final String C = "UPDATE DC_DsbSummary SET DCAccountID=?, DataDate=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, NUMDISBCREDITS=?, NUMDTCCREDITS=?, NUMCHECKPAIDEARLY=?, NUMCHECKPAIDLATE=?, NUMIMMEDFUNDNEED=?, NUMFEDESTIMATE=?, NUMLATEDEBITS=?, NUMCHECKPAIDLAST=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ";
  private static String Q = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.NUMDISBCREDITS, b.NUMDTCCREDITS, b.NUMCHECKPAIDEARLY, b.NUMCHECKPAIDLATE, b.NUMIMMEDFUNDNEED, b.NUMFEDESTIMATE, b.NUMLATEDEBITS, b.NUMCHECKPAIDLAST, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND b.DataDate>=? AND b.DataDate<=? AND DataClassification=? ";
  private static String P = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.NUMDISBCREDITS, b.NUMDTCCREDITS, b.NUMCHECKPAIDEARLY, b.NUMCHECKPAIDLATE, b.NUMIMMEDFUNDNEED, b.NUMFEDESTIMATE, b.NUMLATEDEBITS, b.NUMCHECKPAIDLAST, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND DataClassification=? ";
  private static String N = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.NUMDISBCREDITS, b.NUMDTCCREDITS, b.NUMCHECKPAIDEARLY, b.NUMCHECKPAIDLATE, b.NUMIMMEDFUNDNEED, b.NUMFEDESTIMATE, b.NUMLATEDEBITS, b.NUMCHECKPAIDLAST, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND DataClassification=? ";
  private static String M = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.NUMDISBCREDITS, b.NUMDTCCREDITS, b.NUMCHECKPAIDEARLY, b.NUMCHECKPAIDLATE, b.NUMIMMEDFUNDNEED, b.NUMFEDESTIMATE, b.NUMLATEDEBITS, b.NUMCHECKPAIDLAST, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?  AND DataClassification=? ";
  private static String L = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.NUMDISBCREDITS, b.NUMDTCCREDITS, b.NUMCHECKPAIDEARLY, b.NUMCHECKPAIDLATE, b.NUMIMMEDFUNDNEED, b.NUMFEDESTIMATE, b.NUMLATEDEBITS, b.NUMCHECKPAIDLAST, b.ExtendABeanXMLID FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber=? AND b.DataDate=? AND DataClassification=? ";
  private static String z = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.NUMDISBCREDITS, b.NUMDTCCREDITS, b.NUMCHECKPAIDEARLY, b.NUMCHECKPAIDLATE, b.NUMIMMEDFUNDNEED, b.NUMFEDESTIMATE, b.NUMLATEDEBITS, b.NUMCHECKPAIDLAST, b.ExtendABeanXMLID FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber IS NULL AND b.DataDate=? AND b.DataClassification=? ";
  private static String o = "SELECT ExtendABeanXMLID FROM DC_DsbSummary WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String jdField_do = " ORDER BY b.DataDate desc ";
  private static String h = " a.RoutingNumber ";
  private static String jdField_new = " SELECT max(b.DataDate) FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_for = " SELECT max(b.DataDate) FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static String jdField_if = " SELECT max(b.DataDate)  FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String R = " SELECT max(b.DataDate) FROM DC_Account a, DC_DsbSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static String A = "SELECT a.presentment, b.presentment, c.presentment, a.count1, b.count2, c.count3, a.credits, b.debits FROM (SELECT presentment, count(*) AS count1, sum(amount) AS credits FROM dc_dsbtransactions WHERE amount >= 0 AND DataClassification=? ";
  private static String jdField_goto = " GROUP BY presentment) a FULL OUTER JOIN (select presentment, count(*) AS count2, sum(amount) AS debits FROM dc_dsbtransactions WHERE amount < 0 AND DataClassification=? ";
  private static String E = " GROUP BY presentment) b ON a.presentment = b.presentment FULL OUTER JOIN (select presentment,  count(*) AS count3 FROM dc_dsbtransactions WHERE amount IS null AND DataClassification=? ";
  private static String l = " GROUP BY presentment) c ON a.presentment = c.presentment OR b.presentment = c.presentment ";
  private static String i = " AND DataDate >= ? AND DataDate <= ? ";
  private static String B = " AND DataDate >= ? ";
  private static String jdField_try = " AND DataDate <= ? ";
  private static String t = " AND DCAccountID IN ";
  private static String b = "SELECT Presentment, Presentment, Presentment, sum(count1) AS count1 , sum(count2) AS count2, sum(count3) AS count3 , sum(credits) AS credits, sum(debits) AS debits FROM (SELECT Presentment, count(*) AS count1,0 AS count2, 0 AS count3, sum(Amount) AS credits, 0 AS debits FROM DC_DsbTransactions WHERE Amount >= 0 AND DataClassification=? ";
  private static String jdField_null = "GROUP BY Presentment UNION ALL SELECT Presentment, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits, sum(Amount) AS debits FROM DC_DsbTransactions WHERE Amount < 0 AND DataClassification=? ";
  private static String jdField_int = "GROUP BY Presentment UNION ALL SELECT Presentment, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits, 0 AS debits FROM DC_DsbTransactions WHERE Amount IS null AND DataClassification=? ";
  private static String jdField_else = "GROUP BY Presentment) a GROUP BY Presentment ";
  private static String c = " acc.RoutingNumber ";
  private static String k = " acc2.RoutingNumber ";
  private static String O = " acc3.RoutingNumber ";
  private static String x = " GROUP BY t2.dcaccountid) b ON a.dcaccountid = b.dcaccountid )";
  private static String p = " GROUP BY t3.dcaccountid, t3.datadate) c ON a.dcaccountid = c.dcaccountid OR b.dcaccountid = c.dcaccountid AND (a.datadate = c.datadate OR b.datadate = c.datadate)";
  private static String y = " GROUP BY t3.DCAccountID, t3.DataDate) a GROUP BY DCAccountID, DataDate";
  private static String j = " SELECT a.count1, b.count2, c.count3, a.credits, b.debits, a.datadate FROM (SELECT t.dcaccountid, t.datadate, count(*) AS count1, sum(t.amount) AS credits FROM DC_DsbTransactions t, DC_Account acc WHERE t.amount >= 0 AND t.datadate >= ? AND t.datadate <= ? AND t.presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String v = " SELECT sum(count1) AS count1, sum(count2) as count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits, DataDate FROM (SELECT t.DCAccountID, t.DataDate, count(*) AS count1, 0 AS count2, 0 AS count3, sum(t.Amount) AS credits, null AS debits FROM DC_DsbTransactions t, DC_Account acc WHERE t.Amount >= 0 AND t.DataDate >= ? AND t.DataDate <= ? AND t.Presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String K = " GROUP BY t.dcaccountid, t.datadate) a FULL OUTER JOIN (SELECT t2.dcaccountid, t2.datadate, count(*) AS count2, sum(t2.amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.amount < 0 AND t2.datadate >= ? AND t2.datadate <= ? AND t2.presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String H = " GROUP BY t.DCAccountID, t.DataDate UNION ALL SELECT t2.DCAccountID, t2.DataDate, 0 AS count1, count(*) AS count2, 0 AS count3, null AS credits, sum(t2.Amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate >= ? AND t2.DataDate <= ? AND t2.Presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String w = " GROUP BY t2.dcaccountid, t2.datadate) b ON a.dcaccountid = b.dcaccountid and a.datadate = b.datadate FULL OUTER JOIN ( SELECT t3.dcaccountid, t3.datadate, count(*) AS count3 FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.amount IS NULL AND t3.datadate >= ? AND t3.datadate <= ? AND t3.presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String jdField_case = " GROUP BY t2.DCAccountID, t2.DataDate UNION ALL SELECT t3.DCAccountID, t3.DataDate, 0 AS count1, 0 AS count2, count(*) AS count3, null AS credits, null AS debits FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate >= ? AND t3.DataDate <= ? AND t3.Presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String g = " SELECT a.count1, b.count2, c.count3, a.credits, b.debits, a.datadate FROM (SELECT t.dcaccountid, t.datadate, count(*) AS count1, sum(t.amount) AS credits FROM DC_DsbTransactions t, DC_Account acc WHERE t.amount >= 0 AND t.datadate >= ? AND t.presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String e = " SELECT sum(count1) AS count1, sum(count2) as count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits, DataDate FROM (SELECT t.DCAccountID, t.DataDate, count(*) AS count1, 0 AS count2, 0 AS count3, sum(t.Amount) AS credits, null AS debits FROM DC_DsbTransactions t, DC_Account acc WHERE t.Amount >= 0 AND t.DataDate >= ? AND t.Presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String J = " GROUP BY t.dcaccountid, t.datadate) a FULL OUTER JOIN (SELECT t2.dcaccountid, t2.datadate, count(*) AS count2, sum(t2.amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.amount < 0 AND t2.datadate >= ? AND t2.presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String q = " GROUP BY t.DCAccountID, t.DataDate UNION ALL SELECT t2.DCAccountID, t2.DataDate, 0 AS count1, count(*) AS count2, 0 AS count3, null AS credits, sum(t2.Amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate >= ? AND t2.Presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String u = " GROUP BY t2.dcaccountid, t2.datadate) b ON a.dcaccountid = b.dcaccountid AND a.datadate = b.datadate FULL OUTER JOIN ( SELECT t3.dcaccountid, t3.datadate, count(*) AS count3 FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.amount IS NULL AND t3.datadate >= ? AND t3.presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String D = " GROUP BY t2.DCAccountID, t2.DataDate UNION ALL SELECT t3.DCAccountID, t3.DataDate, 0 AS count1, 0 AS count2, count(*) AS count3, null AS credits, null AS debits FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate >= ? AND t3.Presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String f = " SELECT a.count1, b.count2, c.count3, a.credits, b.debits, a.datadate FROM (SELECT t.dcaccountid, t.datadate, count(*) AS count1, sum(t.amount) AS credits FROM DC_DsbTransactions t, DC_Account acc WHERE t.amount >= 0 AND t.datadate <= ? AND t.presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String jdField_char = " SELECT sum(count1) AS count1, sum(count2) as count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits, DataDate FROM (SELECT t.DCAccountID, t.DataDate, count(*) AS count1, 0 AS count2, 0 AS count3, sum(t.Amount) AS credits, null AS debits FROM DC_DsbTransactions t, DC_Account acc WHERE t.Amount >= 0 AND t.DataDate <= ? AND t.Presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String I = " GROUP BY t.dcaccountid, t.datadate) a FULL OUTER JOIN (SELECT t2.dcaccountid, t2.datadate, count(*) AS count2, sum(t2.amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.amount < 0 AND t2.datadate <= ? AND t2.presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String jdField_void = " GROUP BY t.DCAccountID, t.DataDate UNION ALL SELECT t2.DCAccountID, t2.DataDate, 0 AS count1, count(*) AS count2, 0 AS count3, null AS credits, sum(t2.Amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate <= ? AND t2.Presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String s = " GROUP BY t2.dcaccountid, t2.datadate) b ON a.dcaccountid = b.dcaccountid FULL OUTER JOIN ( SELECT t3.dcaccountid, t3.datadate, count(*) AS count3 FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.amount IS NULL AND t3.datadate <= ? AND t3.presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String n = " GROUP BY t2.DCAccountID, t2.DataDate UNION ALL SELECT t3.DCAccountID, t3.DataDate, 0 AS count1, 0 AS count2, count(*) AS count3, null AS credits, null AS debits FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate <= ? AND t3.Presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String d = " SELECT a.count1, b.count2, c.count3, a.credits, b.debits, a.datadate FROM (SELECT t.dcaccountid, t.datadate, count(*) AS count1, sum(t.amount) AS credits FROM DC_DsbTransactions t, DC_Account acc WHERE t.amount >= 0 AND t.presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String F = " SELECT sum(count1) AS count1, sum(count2) as count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits, DataDate FROM (SELECT t.DCAccountID, t.DataDate, count(*) AS count1, 0 AS count2, 0 AS count3, sum(t.Amount) AS credits, 0 AS debits FROM DC_DsbTransactions t, DC_Account acc WHERE t.Amount >= 0 AND t.Presentment = ? AND acc.DCAccountID = t.DCAccountID AND acc.AccountID = ? AND acc.BankID = ? AND t.DataClassification=? ";
  private static String G = " GROUP BY t.dcaccountid, t.datadate) a FULL OUTER JOIN (SELECT t2.dcaccountid, t2.datadate, count(*) AS count2, sum(t2.amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.amount < 0 AND t2.presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String jdField_byte = " GROUP BY t.DCAccountID, t.DataDate UNION ALL SELECT t2.DCAccountID, t2.DataDate, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits, sum(t2.Amount) AS debits FROM DC_DsbTransactions t2, DC_Account acc2 WHERE t2.Amount < 0 AND t2.Presentment = ? AND acc2.DCAccountID = t2.DCAccountID AND acc2.AccountID = ? AND acc2.BankID = ? AND t2.DataClassification=? ";
  private static String r = " GROUP BY t2.dcaccountid, t2.datadate) b ON a.dcaccountid = b.dcaccountid FULL OUTER JOIN ( SELECT t3.dcaccountid, t3.datadate, count(*) AS count3 FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.amount IS NULL AND t3.presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  private static String jdField_long = " GROUP BY t2.DCAccountID, t2.DataDate UNION ALL SELECT t3.DCAccountID, t3.DataDate, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits, 0 AS debits FROM DC_DsbTransactions t3, DC_Account acc3 WHERE t3.Amount IS NULL AND t3.Presentment = ? AND acc3.DCAccountID = t3.DCAccountID AND acc3.AccountID = ? AND acc3.BankID = ? AND t3.DataClassification=? ";
  
  protected static void addDisbursementSummary(DisbursementSummary paramDisbursementSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str1 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_IDENTIFIER", null);
      Calendar localCalendar = null;
      if (paramHashMap != null) {
        localCalendar = (Calendar)paramHashMap.get("BAI_FILE_DATE");
      }
      String str2 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_NAME", null);
      String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      DisbursementAccount localDisbursementAccount = paramDisbursementSummary.getAccount();
      Account localAccount = DCUtil.getAccount(localDisbursementAccount);
      if (!DCAccount.accountExists(localAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(localAccount, paramConnection, paramHashMap);
      }
      int i2 = DCAdapter.getDCAccountID(paramConnection, localDisbursementAccount.getAccountID(), localDisbursementAccount.getBankID(), localDisbursementAccount.getRoutingNumber());
      if (i2 == -1) {
        throw new DCException(414, "Invalid account.");
      }
      DisbursementSummary localDisbursementSummary1 = a(localDisbursementAccount, paramDisbursementSummary.getSummaryDate(), paramConnection, str3);
      DisbursementSummary localDisbursementSummary2 = null;
      if (localDisbursementSummary1 == null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_DsbSummary( DCAccountID, DataDate, DataSource, NumItemsPending, TotalDebits, TotalCredits, TotalDTCCredits, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, ChecksPaidEarly, ChecksPaidLate, ChecksPaidLast, FedEstimate, LateDebits, NUMDISBCREDITS, NUMDTCCREDITS, NUMCHECKPAIDEARLY, NUMCHECKPAIDLATE, NUMIMMEDFUNDNEED, NUMFEDESTIMATE, NUMLATEDEBITS, NUMCHECKPAIDLAST, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
        localDisbursementSummary2 = paramDisbursementSummary;
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_DsbSummary SET DCAccountID=?, DataDate=?, DataSource=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, NUMDISBCREDITS=?, NUMDTCCREDITS=?, NUMCHECKPAIDEARLY=?, NUMCHECKPAIDLATE=?, NUMIMMEDFUNDNEED=?, NUMFEDESTIMATE=?, NUMLATEDEBITS=?, NUMCHECKPAIDLAST=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
        localDisbursementSummary2 = localDisbursementSummary1;
        Currency localCurrency = null;
        DateTime localDateTime = null;
        int i3 = -1;
        i3 = paramDisbursementSummary.getNumItemsPending();
        if (i3 != -1) {
          localDisbursementSummary2.setNumItemsPending(i3);
        }
        localCurrency = paramDisbursementSummary.getTotalDebits();
        if (localCurrency != null) {
          localDisbursementSummary2.setTotalDebits(localCurrency);
        }
        localCurrency = paramDisbursementSummary.getTotalCredits();
        if (localCurrency != null) {
          localDisbursementSummary2.setTotalCredits(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumCredits();
        if (i3 != -1) {
          localDisbursementSummary2.setNumCredits(i3);
        }
        localCurrency = paramDisbursementSummary.getTotalDTCCredits();
        if (localCurrency != null) {
          localDisbursementSummary2.setTotalDTCCredits(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumDTCCredits();
        if (i3 != -1) {
          localDisbursementSummary2.setNumDTCCredits(i3);
        }
        localCurrency = paramDisbursementSummary.getImmediateFundsNeeded();
        if (localCurrency != null) {
          localDisbursementSummary2.setImmediateFundsNeeded(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumImmediateFundsNeeded();
        if (i3 != -1) {
          localDisbursementSummary2.setNumImmediateFundsNeeded(i3);
        }
        localCurrency = paramDisbursementSummary.getOneDayFundsNeeded();
        if (localCurrency != null) {
          localDisbursementSummary2.setOneDayFundsNeeded(localCurrency);
        }
        localCurrency = paramDisbursementSummary.getTwoDayFundsNeeded();
        if (localCurrency != null) {
          localDisbursementSummary2.setTwoDayFundsNeeded(localCurrency);
        }
        localDateTime = paramDisbursementSummary.getValueDateTime();
        if (localDateTime != null) {
          localDisbursementSummary2.setValueDateTime(localDateTime);
        }
        localCurrency = paramDisbursementSummary.getTotalChecksPaidEarlyAmount();
        if (localCurrency != null) {
          localDisbursementSummary2.setTotalChecksPaidEarlyAmount(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumChecksPaidEarly();
        if (i3 != -1) {
          localDisbursementSummary2.setNumChecksPaidEarly(i3);
        }
        localCurrency = paramDisbursementSummary.getTotalChecksPaidLateAmount();
        if (localCurrency != null) {
          localDisbursementSummary2.setTotalChecksPaidLateAmount(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumChecksPaidLate();
        if (i3 != -1) {
          localDisbursementSummary2.setNumChecksPaidLate(i3);
        }
        localCurrency = paramDisbursementSummary.getTotalChecksPaidLastAmount();
        if (localCurrency != null) {
          localDisbursementSummary2.setTotalChecksPaidLastAmount(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumChecksPaidLast();
        if (i3 != -1) {
          localDisbursementSummary2.setNumChecksPaidLast(i3);
        }
        localCurrency = paramDisbursementSummary.getFedPresentmentEstimate();
        if (localCurrency != null) {
          localDisbursementSummary2.setFedPresentmentEstimate(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumFedPresentmentEstimate();
        if (i3 != -1) {
          localDisbursementSummary2.setNumFedPresentmentEstimate(i3);
        }
        localCurrency = paramDisbursementSummary.getLateDebits();
        if (localCurrency != null) {
          localDisbursementSummary2.setLateDebits(localCurrency);
        }
        i3 = paramDisbursementSummary.getNumLateDebits();
        if (i3 != -1) {
          localDisbursementSummary2.setNumLateDebits(i3);
        }
      }
      localPreparedStatement.setInt(1, i2);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, localDisbursementSummary2.getSummaryDate());
      localPreparedStatement.setInt(3, paramInt);
      localPreparedStatement.setInt(4, localDisbursementSummary2.getNumItemsPending());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, localDisbursementSummary2.getTotalDebits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, localDisbursementSummary2.getTotalCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, localDisbursementSummary2.getTotalDTCCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localDisbursementSummary2.getImmediateFundsNeeded());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localDisbursementSummary2.getOneDayFundsNeeded());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 10, localDisbursementSummary2.getTwoDayFundsNeeded());
      DCUtil.fillTimestampColumn(localPreparedStatement, 11, localDisbursementSummary2.getValueDateTime());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 12, localDisbursementSummary2.getTotalChecksPaidEarlyAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 13, localDisbursementSummary2.getTotalChecksPaidLateAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localDisbursementSummary2.getTotalChecksPaidLastAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 15, localDisbursementSummary2.getFedPresentmentEstimate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 16, localDisbursementSummary2.getLateDebits());
      localPreparedStatement.setInt(17, localDisbursementSummary2.getNumCredits());
      localPreparedStatement.setInt(18, localDisbursementSummary2.getNumDTCCredits());
      localPreparedStatement.setInt(19, localDisbursementSummary2.getNumChecksPaidEarly());
      localPreparedStatement.setInt(20, localDisbursementSummary2.getNumChecksPaidLate());
      localPreparedStatement.setInt(21, localDisbursementSummary2.getNumImmediateFundsNeeded());
      localPreparedStatement.setInt(22, localDisbursementSummary2.getNumFedPresentmentEstimate());
      localPreparedStatement.setInt(23, localDisbursementSummary2.getNumLateDebits());
      localPreparedStatement.setInt(24, localDisbursementSummary2.getNumChecksPaidLast());
      if (localDisbursementSummary1 == null)
      {
        localPreparedStatement.setLong(25, DCExtendABeanXML.addExtendABeanXML(paramConnection, localDisbursementSummary2.getExtendABeanXML(), paramHashMap));
        localPreparedStatement.setString(26, null);
        localPreparedStatement.setString(27, str1);
        localPreparedStatement.setString(28, str3);
        localPreparedStatement.setString(29, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 30, localCalendar);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_DsbSummary( DCAccountID, DataDate, DataSource, NumItemsPending, TotalDebits, TotalCredits, TotalDTCCredits, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, ChecksPaidEarly, ChecksPaidLate, ChecksPaidLast, FedEstimate, LateDebits, NUMDISBCREDITS, NUMDTCCREDITS, NUMCHECKPAIDEARLY, NUMCHECKPAIDLATE, NUMIMMEDFUNDNEED, NUMFEDESTIMATE, NUMLATEDEBITS, NUMCHECKPAIDLAST, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
      }
      else
      {
        long l1 = a(paramConnection, i2, localDisbursementSummary2.getSummaryDate(), str3);
        DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
        a(localDisbursementSummary2, paramDisbursementSummary);
        long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, localDisbursementSummary2.getExtendABeanXML(), paramHashMap);
        localPreparedStatement.setString(25, str1);
        localPreparedStatement.setLong(26, l2);
        localPreparedStatement.setString(27, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 28, localCalendar);
        localPreparedStatement.setInt(29, i2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 30, localDisbursementSummary2.getSummaryDate());
        localPreparedStatement.setString(31, str3);
        DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_DsbSummary SET DCAccountID=?, DataDate=?, DataSource=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, NUMDISBCREDITS=?, NUMDTCCREDITS=?, NUMCHECKPAIDEARLY=?, NUMCHECKPAIDLATE=?, NUMIMMEDFUNDNEED=?, NUMFEDESTIMATE=?, NUMLATEDEBITS=?, NUMCHECKPAIDLAST=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add disbursement summary.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 601;
      throw new DCException(i1, "Failed to add disbursement summary", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void updateDisbursementSummary(Connection paramConnection, DisbursementSummary paramDisbursementSummary, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      DisbursementAccount localDisbursementAccount = paramDisbursementSummary.getAccount();
      int i2 = DCAdapter.getDCAccountID(paramConnection, localDisbursementAccount.getAccountID(), localDisbursementAccount.getBankID(), localDisbursementAccount.getRoutingNumber());
      if (i2 == -1) {
        throw new DCException(414, "Invalid account.");
      }
      DisbursementSummary localDisbursementSummary = a(localDisbursementAccount, paramDisbursementSummary.getSummaryDate(), paramConnection, str);
      if (localDisbursementSummary == null) {
        throw new DCException(613, "No account summary was found to update for the date " + paramDisbursementSummary.getSummaryDate().getTime().toString() + " , and account with accoutid=" + localDisbursementAccount.getAccountID() + ", bankid= " + localDisbursementAccount.getBankID() + ", routing number=" + localDisbursementAccount.getRoutingNumber() + ", and data classification '" + str + "'.");
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_DsbSummary SET DCAccountID=?, DataDate=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, NUMDISBCREDITS=?, NUMDTCCREDITS=?, NUMCHECKPAIDEARLY=?, NUMCHECKPAIDLATE=?, NUMIMMEDFUNDNEED=?, NUMFEDESTIMATE=?, NUMLATEDEBITS=?, NUMCHECKPAIDLAST=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
      localPreparedStatement.setInt(1, i2);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramDisbursementSummary.getSummaryDate());
      localPreparedStatement.setInt(3, paramDisbursementSummary.getNumItemsPending());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramDisbursementSummary.getTotalDebits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, paramDisbursementSummary.getTotalCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, paramDisbursementSummary.getTotalDTCCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, paramDisbursementSummary.getImmediateFundsNeeded());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, paramDisbursementSummary.getOneDayFundsNeeded());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, paramDisbursementSummary.getTwoDayFundsNeeded());
      DCUtil.fillTimestampColumn(localPreparedStatement, 10, paramDisbursementSummary.getValueDateTime());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 11, paramDisbursementSummary.getTotalChecksPaidEarlyAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 12, paramDisbursementSummary.getTotalChecksPaidLateAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 13, paramDisbursementSummary.getTotalChecksPaidLastAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 14, paramDisbursementSummary.getFedPresentmentEstimate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 15, paramDisbursementSummary.getLateDebits());
      localPreparedStatement.setInt(16, paramDisbursementSummary.getNumCredits());
      localPreparedStatement.setInt(17, paramDisbursementSummary.getNumDTCCredits());
      localPreparedStatement.setInt(18, paramDisbursementSummary.getNumChecksPaidEarly());
      localPreparedStatement.setInt(19, paramDisbursementSummary.getNumChecksPaidLate());
      localPreparedStatement.setInt(20, paramDisbursementSummary.getNumImmediateFundsNeeded());
      localPreparedStatement.setInt(21, paramDisbursementSummary.getNumFedPresentmentEstimate());
      localPreparedStatement.setInt(22, paramDisbursementSummary.getNumLateDebits());
      localPreparedStatement.setInt(23, paramDisbursementSummary.getNumChecksPaidLast());
      long l1 = a(paramConnection, i2, paramDisbursementSummary.getSummaryDate(), str);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, paramDisbursementSummary.getExtendABeanXML(), paramHashMap);
      localPreparedStatement.setLong(24, l2);
      localPreparedStatement.setInt(25, i2);
      DCUtil.fillTimestampColumn(localPreparedStatement, 26, paramDisbursementSummary.getSummaryDate());
      localPreparedStatement.setString(27, str);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_DsbSummary SET DCAccountID=?, DataDate=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, NUMDISBCREDITS=?, NUMDTCCREDITS=?, NUMCHECKPAIDEARLY=?, NUMCHECKPAIDLATE=?, NUMIMMEDFUNDNEED=?, NUMFEDESTIMATE=?, NUMLATEDEBITS=?, NUMCHECKPAIDLAST=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=? ");
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to update disbursement summary.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 601;
      throw new DCException(i1, "Failed to update disbursement summary", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static long a(Connection paramConnection, int paramInt, DateTime paramDateTime, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l1;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, o);
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramDateTime);
      localPreparedStatement.setString(3, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, o);
      if (localResultSet.next()) {
        l1 = localResultSet.getLong(1);
      } else {
        throw new DCException("Error retrieving EXTENDABEANXMLID: No records in DC_DSBSUMMARY table with DCACCOUNTID " + paramInt + " and DATADATE " + paramDateTime);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get ExtendABean XML ID for disbursement summary.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 434;
      throw new DCException(i1, "Failed to get ExtendABean XML ID for disbursement summary", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
    return l1;
  }
  
  private static void a(DisbursementSummary paramDisbursementSummary1, DisbursementSummary paramDisbursementSummary2)
  {
    if ((paramDisbursementSummary1 != null) && (paramDisbursementSummary2 != null)) {
      paramDisbursementSummary1.putAll(paramDisbursementSummary2);
    }
  }
  
  protected static DisbursementSummaries getDisbursementSummariesForPresentment(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    boolean bool = DCAdapter.CONNECTIONTYPE.equalsIgnoreCase("ASE");
    if (bool) {
      return a(paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return getDisbursementSummariesForPresentmentDB2Oracle(paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  protected static DisbursementSummaries getDisbursementSummariesForPresentmentDB2Oracle(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    DisbursementSummaries localDisbursementSummaries1 = null;
    Connection localConnection = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i1;
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(j);
          DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(K);
          DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(w);
          DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(p);
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          i1 = 1;
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(g);
          DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(J);
          DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(u);
          DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(p);
          i1 = 1;
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(f);
        DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(I);
        DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(s);
        DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(p);
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        i1 = 1;
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(d);
        DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(G);
        DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(r);
        DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(p);
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        i1 = 1;
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      localDisbursementSummaries1 = new DisbursementSummaries();
      DisbursementSummary localDisbursementSummary = null;
      while (localResultSet.next())
      {
        localDisbursementSummary = a(paramDisbursementAccount, paramString, localResultSet, paramDisbursementAccount.getCurrencyType());
        localDisbursementSummaries1.add(localDisbursementSummary);
      }
      DisbursementSummaries localDisbursementSummaries2 = localDisbursementSummaries1;
      return localDisbursementSummaries2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to get disbursement summaries for presentment", localSQLException);
    }
    catch (Exception localException)
    {
      int i2 = (localException instanceof SQLException) ? 302 : 602;
      throw new DCException(i2, "Failed to get disbursement summary for presentment.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static DisbursementSummaries a(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    DisbursementSummaries localDisbursementSummaries1 = null;
    Connection localConnection = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      int i1;
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(v);
          DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(H);
          DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(jdField_case);
          DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(y);
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          i1 = 1;
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(e);
          DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(q);
          DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(D);
          DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(y);
          i1 = 1;
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar1);
          localPreparedStatement.setString(i1++, paramString);
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
          localPreparedStatement.setString(i1++, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_char);
        DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(jdField_void);
        DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(n);
        DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(y);
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        i1 = 1;
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        DCUtil.fillTimestampColumn(localPreparedStatement, i1++, paramCalendar2);
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(F);
        DCUtil.appendNullWhereClause(localStringBuffer, c, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(jdField_byte);
        DCUtil.appendNullWhereClause(localStringBuffer, k, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(jdField_long);
        DCUtil.appendNullWhereClause(localStringBuffer, O, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(y);
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        i1 = 1;
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localPreparedStatement.setString(i1++, paramString);
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(i1++, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(i1++, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(i1++, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      localDisbursementSummaries1 = new DisbursementSummaries();
      DisbursementSummary localDisbursementSummary = null;
      while (localResultSet.next())
      {
        localDisbursementSummary = a(paramDisbursementAccount, paramString, localResultSet, paramDisbursementAccount.getCurrencyType());
        localDisbursementSummaries1.add(localDisbursementSummary);
      }
      DisbursementSummaries localDisbursementSummaries2 = localDisbursementSummaries1;
      return localDisbursementSummaries2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to get disbursement summaries for presentment", localSQLException);
    }
    catch (Exception localException)
    {
      int i2 = (localException instanceof SQLException) ? 302 : 602;
      throw new DCException(i2, "Failed to get disbursement summary for presentment.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  protected static DisbursementPresentmentSummaries getDisbursementPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    boolean bool = DCAdapter.CONNECTIONTYPE.equalsIgnoreCase("ASE");
    if (bool) {
      return jdField_if(paramDisbursementAccounts, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return a(paramDisbursementAccounts, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  private static DisbursementPresentmentSummaries a(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = new DisbursementPresentmentSummaries();
    if (paramDisbursementAccounts.size() <= 0) {
      return localDisbursementPresentmentSummaries;
    }
    HashMap localHashMap = new HashMap();
    Object localObject3;
    for (int i1 = 0; i1 < paramDisbursementAccounts.size(); i1++)
    {
      localObject1 = (DisbursementAccount)paramDisbursementAccounts.get(i1);
      localObject2 = ((DisbursementAccount)localObject1).getCurrencyType();
      if (localHashMap.containsKey(localObject2))
      {
        localObject3 = (ArrayList)localHashMap.get(localObject2);
        ((ArrayList)localObject3).add(localObject1);
      }
      else
      {
        localObject3 = new ArrayList();
        ((ArrayList)localObject3).add(localObject1);
        localHashMap.put(localObject2, localObject3);
      }
    }
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      localObject1 = DCAdapter.getDBConnection();
      localObject3 = localHashMap.keySet().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (String)((Iterator)localObject3).next();
        ArrayList localArrayList = (ArrayList)localHashMap.get(localObject4);
        StringBuffer localStringBuffer1 = new StringBuffer();
        int i3 = 1;
        Object localObject5;
        for (int i4 = 0; i4 < localArrayList.size(); i4++)
        {
          localObject5 = (DisbursementAccount)localArrayList.get(i4);
          int i5 = DCAdapter.getDCAccountID((Connection)localObject1, ((DisbursementAccount)localObject5).getAccountID(), ((DisbursementAccount)localObject5).getBankID(), ((DisbursementAccount)localObject5).getRoutingNumber());
          if (i5 != -1)
          {
            if (i3 == 0)
            {
              localStringBuffer1.append(", ");
            }
            else
            {
              i3 = 0;
              localStringBuffer1.append(" ( ");
            }
            localStringBuffer1.append(i5);
          }
        }
        if (localStringBuffer1.length() != 0)
        {
          localStringBuffer1.append(" ) ");
          StringBuffer localStringBuffer2 = new StringBuffer(A);
          if (paramCalendar1 != null)
          {
            if (paramCalendar2 != null)
            {
              localStringBuffer2.append(i);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_goto);
              localStringBuffer2.append(i);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(E);
              localStringBuffer2.append(i);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(l);
              localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
              localPreparedStatement.setString(1, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar1);
              DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
              localPreparedStatement.setString(4, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar1);
              DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar2);
              localPreparedStatement.setString(7, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 8, paramCalendar1);
              DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramCalendar2);
              localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
            }
            else
            {
              localStringBuffer2.append(B);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_goto);
              localStringBuffer2.append(B);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(E);
              localStringBuffer2.append(B);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(l);
              localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
              localPreparedStatement.setString(1, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar1);
              localPreparedStatement.setString(3, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar1);
              localPreparedStatement.setString(5, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar1);
              localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
            }
          }
          else if (paramCalendar2 != null)
          {
            localStringBuffer2.append(jdField_try);
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            localStringBuffer2.append(jdField_goto);
            localStringBuffer2.append(jdField_try);
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            localStringBuffer2.append(E);
            localStringBuffer2.append(jdField_try);
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            localStringBuffer2.append(l);
            localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
            localPreparedStatement.setString(1, str);
            DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar2);
            localPreparedStatement.setString(3, str);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str);
            DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar2);
            localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
          }
          else
          {
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
              localStringBuffer2.append(jdField_goto);
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
              localStringBuffer2.append(E);
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            else
            {
              localStringBuffer2.append(jdField_goto);
              localStringBuffer2.append(E);
            }
            localStringBuffer2.append(l);
            localPreparedStatement.setString(1, str);
            localPreparedStatement.setString(2, str);
            localPreparedStatement.setString(3, str);
            localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
            localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
          }
          localObject5 = null;
          while (((ResultSet)localObject2).next())
          {
            localObject5 = a((ResultSet)localObject2, (String)localObject4, paramDisbursementAccounts.getLocale());
            Iterator localIterator = localDisbursementPresentmentSummaries.iterator();
            int i6 = 0;
            DisbursementPresentmentSummary localDisbursementPresentmentSummary;
            while (localIterator.hasNext())
            {
              localDisbursementPresentmentSummary = (DisbursementPresentmentSummary)localIterator.next();
              if (localDisbursementPresentmentSummary.getPresentment().equals(((DisbursementPresentmentSummary)localObject5).getPresentment()))
              {
                localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject5);
                i6 = 1;
                break;
              }
            }
            if (i6 == 0)
            {
              localDisbursementPresentmentSummary = new DisbursementPresentmentSummary((SecureUser)paramHashMap.get("SECURE_USER"));
              localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject5);
              localDisbursementPresentmentSummaries.add(localDisbursementPresentmentSummary);
            }
          }
        }
      }
      Object localObject4 = localDisbursementPresentmentSummaries;
      return localObject4;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i2 = (localException instanceof SQLException) ? 302 : 600;
      throw new DCException(i2, "Unable to get disbursement summaries.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet((ResultSet)localObject2);
      if (localObject1 != null) {
        DCAdapter.releaseDBConnection((Connection)localObject1);
      }
    }
  }
  
  private static DisbursementPresentmentSummaries jdField_if(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = new DisbursementPresentmentSummaries();
    if (paramDisbursementAccounts.size() <= 0) {
      return localDisbursementPresentmentSummaries;
    }
    HashMap localHashMap = new HashMap();
    Object localObject3;
    for (int i1 = 0; i1 < paramDisbursementAccounts.size(); i1++)
    {
      localObject1 = (DisbursementAccount)paramDisbursementAccounts.get(i1);
      localObject2 = ((DisbursementAccount)localObject1).getCurrencyType();
      if (localHashMap.containsKey(localObject2))
      {
        localObject3 = (ArrayList)localHashMap.get(localObject2);
        ((ArrayList)localObject3).add(localObject1);
      }
      else
      {
        localObject3 = new ArrayList();
        ((ArrayList)localObject3).add(localObject1);
        localHashMap.put(localObject2, localObject3);
      }
    }
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      localObject1 = DCAdapter.getDBConnection();
      localObject3 = localHashMap.keySet().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (String)((Iterator)localObject3).next();
        ArrayList localArrayList = (ArrayList)localHashMap.get(localObject4);
        StringBuffer localStringBuffer1 = new StringBuffer();
        int i3 = 1;
        Object localObject5;
        for (int i4 = 0; i4 < localArrayList.size(); i4++)
        {
          localObject5 = (DisbursementAccount)localArrayList.get(i4);
          int i5 = DCAdapter.getDCAccountID((Connection)localObject1, ((DisbursementAccount)localObject5).getAccountID(), ((DisbursementAccount)localObject5).getBankID(), ((DisbursementAccount)localObject5).getRoutingNumber());
          if (i5 != -1)
          {
            if (i3 == 0)
            {
              localStringBuffer1.append(", ");
            }
            else
            {
              i3 = 0;
              localStringBuffer1.append(" ( ");
            }
            localStringBuffer1.append(i5);
          }
        }
        if (localStringBuffer1.length() != 0)
        {
          localStringBuffer1.append(" ) ");
          StringBuffer localStringBuffer2 = new StringBuffer(b);
          if (paramCalendar1 != null)
          {
            if (paramCalendar2 != null)
            {
              localStringBuffer2.append(i);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_null);
              localStringBuffer2.append(i);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_int);
              localStringBuffer2.append(i);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_else);
              localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
              localPreparedStatement.setString(1, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar1);
              DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
              localPreparedStatement.setString(4, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar1);
              DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar2);
              localPreparedStatement.setString(7, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 8, paramCalendar1);
              DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramCalendar2);
              localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
            }
            else
            {
              localStringBuffer2.append(B);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_null);
              localStringBuffer2.append(B);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_int);
              localStringBuffer2.append(B);
              if (localStringBuffer1.length() != 0)
              {
                localStringBuffer2.append(t);
                localStringBuffer2.append(localStringBuffer1);
              }
              localStringBuffer2.append(jdField_else);
              localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
              localPreparedStatement.setString(1, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar1);
              localPreparedStatement.setString(3, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar1);
              localPreparedStatement.setString(5, str);
              DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar1);
              localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
            }
          }
          else if (paramCalendar2 != null)
          {
            localStringBuffer2.append(jdField_try);
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            localStringBuffer2.append(jdField_null);
            localStringBuffer2.append(jdField_try);
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            localStringBuffer2.append(jdField_int);
            localStringBuffer2.append(jdField_try);
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            localStringBuffer2.append(jdField_else);
            localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
            localPreparedStatement.setString(1, str);
            DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar2);
            localPreparedStatement.setString(3, str);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str);
            DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar2);
            localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
          }
          else
          {
            if (localStringBuffer1.length() != 0)
            {
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
              localStringBuffer2.append(jdField_null);
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
              localStringBuffer2.append(jdField_int);
              localStringBuffer2.append(t);
              localStringBuffer2.append(localStringBuffer1);
            }
            else
            {
              localStringBuffer2.append(jdField_null);
              localStringBuffer2.append(jdField_int);
            }
            localStringBuffer2.append(jdField_else);
            localPreparedStatement.setString(1, str);
            localPreparedStatement.setString(2, str);
            localPreparedStatement.setString(3, str);
            localPreparedStatement = DCAdapter.getStatement((Connection)localObject1, localStringBuffer2.toString());
            localObject2 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer2.toString());
          }
          localObject5 = null;
          while (((ResultSet)localObject2).next())
          {
            localObject5 = a((ResultSet)localObject2, (String)localObject4, paramDisbursementAccounts.getLocale());
            Iterator localIterator = localDisbursementPresentmentSummaries.iterator();
            int i6 = 0;
            DisbursementPresentmentSummary localDisbursementPresentmentSummary;
            while (localIterator.hasNext())
            {
              localDisbursementPresentmentSummary = (DisbursementPresentmentSummary)localIterator.next();
              if (localDisbursementPresentmentSummary.getPresentment().equals(((DisbursementPresentmentSummary)localObject5).getPresentment()))
              {
                localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject5);
                i6 = 1;
                break;
              }
            }
            if (i6 == 0)
            {
              localDisbursementPresentmentSummary = new DisbursementPresentmentSummary((SecureUser)paramHashMap.get("SECURE_USER"));
              localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject5);
              localDisbursementPresentmentSummaries.add(localDisbursementPresentmentSummary);
            }
          }
        }
      }
      Object localObject4 = localDisbursementPresentmentSummaries;
      return localObject4;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i2 = (localException instanceof SQLException) ? 302 : 600;
      throw new DCException(i2, "Unable to get disbursement summaries.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet((ResultSet)localObject2);
      if (localObject1 != null) {
        DCAdapter.releaseDBConnection((Connection)localObject1);
      }
    }
  }
  
  protected static DisbursementSummaries getDisbursementSummaries(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    DisbursementSummaries localDisbursementSummaries1 = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localResultSet = a(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      localDisbursementSummaries1 = new DisbursementSummaries();
      DisbursementSummary localDisbursementSummary = null;
      while (localResultSet.next())
      {
        localDisbursementSummary = a(paramDisbursementAccount, localResultSet, paramConnection, paramDisbursementAccount.getCurrencyType());
        localDisbursementSummaries1.add(localDisbursementSummary);
      }
      DisbursementSummaries localDisbursementSummaries2 = localDisbursementSummaries1;
      return localDisbursementSummaries2;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbursement summaries: SQLException", localSQLException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static DisbursementSummary a(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    ResultSet localResultSet = null;
    try
    {
      String str = paramDisbursementAccount.getRoutingNumber();
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, L);
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(3, str);
        DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar);
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, L);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, z);
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, z);
      }
      DisbursementSummary localDisbursementSummary1 = null;
      if (localResultSet.next()) {
        localDisbursementSummary1 = a(paramDisbursementAccount, localResultSet, paramConnection, paramDisbursementAccount.getCurrencyType());
      }
      DisbursementSummary localDisbursementSummary2 = localDisbursementSummary1;
      return localDisbursementSummary2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get disbursement summary.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 600;
      throw new DCException(i1, "Failed to get disbursement summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static DisbursementSummary a(DisbursementAccount paramDisbursementAccount, ResultSet paramResultSet, Connection paramConnection, String paramString)
    throws DCException
  {
    try
    {
      DisbursementSummary localDisbursementSummary = new DisbursementSummary();
      localDisbursementSummary.setAccount(paramDisbursementAccount);
      localDisbursementSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramDisbursementAccount.getLocale()));
      i1 = paramResultSet.getInt(2);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumItemsPending(i1);
      } else {
        localDisbursementSummary.setNumItemsPending(-1);
      }
      localDisbursementSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTotalDTCCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setImmediateFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setOneDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTwoDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(9), paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTotalChecksPaidEarlyAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTotalChecksPaidLateAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTotalChecksPaidLastAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setFedPresentmentEstimate(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramString, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setLateDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramString, paramDisbursementAccount.getLocale()));
      i1 = paramResultSet.getInt(15);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumCredits(i1);
      } else {
        localDisbursementSummary.setNumCredits(-1);
      }
      i1 = paramResultSet.getInt(16);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumDTCCredits(i1);
      } else {
        localDisbursementSummary.setNumDTCCredits(-1);
      }
      i1 = paramResultSet.getInt(17);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumChecksPaidEarly(i1);
      } else {
        localDisbursementSummary.setNumChecksPaidEarly(-1);
      }
      i1 = paramResultSet.getInt(18);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumChecksPaidLate(i1);
      } else {
        localDisbursementSummary.setNumChecksPaidLate(-1);
      }
      i1 = paramResultSet.getInt(19);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumImmediateFundsNeeded(i1);
      } else {
        localDisbursementSummary.setNumImmediateFundsNeeded(-1);
      }
      i1 = paramResultSet.getInt(20);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumFedPresentmentEstimate(i1);
      } else {
        localDisbursementSummary.setNumFedPresentmentEstimate(-1);
      }
      i1 = paramResultSet.getInt(21);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumLateDebits(i1);
      } else {
        localDisbursementSummary.setNumLateDebits(-1);
      }
      i1 = paramResultSet.getInt(22);
      if (!paramResultSet.wasNull()) {
        localDisbursementSummary.setNumChecksPaidLast(i1);
      } else {
        localDisbursementSummary.setNumChecksPaidLast(-1);
      }
      DCUtil.fillExtendABean(paramConnection, localDisbursementSummary, paramResultSet, 23);
      return localDisbursementSummary;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to create disbursement summary using database record.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 603;
      throw new DCException(i1, "Failed to create disbursement summary with data.", localException);
    }
  }
  
  private static DisbursementSummary a(DisbursementAccount paramDisbursementAccount, String paramString1, ResultSet paramResultSet, String paramString2)
    throws DCException
  {
    try
    {
      DisbursementSummary localDisbursementSummary = new DisbursementSummary();
      localDisbursementSummary.setAccount(paramDisbursementAccount);
      localDisbursementSummary.setPresentment(paramString1);
      int i1 = paramResultSet.getInt(1);
      int i2 = paramResultSet.getInt(2);
      int i3 = paramResultSet.getInt(3);
      localDisbursementSummary.setNumItemsPending(i1 + i2 + i3);
      localDisbursementSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramString2, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramString2, paramDisbursementAccount.getLocale()));
      localDisbursementSummary.setSummaryDate(DCUtil.getDateColumn(paramResultSet.getDate(6), paramDisbursementAccount.getLocale()));
      return localDisbursementSummary;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to create disbursement summary for presentment.", localSQLException);
    }
  }
  
  private static DisbursementPresentmentSummary a(ResultSet paramResultSet, String paramString, Locale paramLocale)
    throws DCException
  {
    try
    {
      DisbursementPresentmentSummary localDisbursementPresentmentSummary = new DisbursementPresentmentSummary();
      for (int i1 = 1; i1 <= 3; i1++)
      {
        String str = paramResultSet.getString(i1);
        if (str != null)
        {
          localDisbursementPresentmentSummary.setPresentment(str);
          break;
        }
      }
      i1 = paramResultSet.getInt(4);
      int i2 = paramResultSet.getInt(5);
      int i3 = paramResultSet.getInt(6);
      localDisbursementPresentmentSummary.setNumItemsPending(i1 + i2 + i3);
      localDisbursementPresentmentSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramString, paramLocale));
      localDisbursementPresentmentSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramString, paramLocale));
      return localDisbursementPresentmentSummary;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to create disbursement summary.", localSQLException);
    }
  }
  
  private static ResultSet a(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(Q);
          DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(jdField_do);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(P);
          DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
          localStringBuffer.append(jdField_do);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(N);
        DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(jdField_do);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(M);
        DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
        localStringBuffer.append(jdField_do);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to create disbursement summary for presentment.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 600;
      throw new DCException(i1, "Failed to get disbursement summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static ArrayList getDisbursementSummaryMapList(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = a(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = a(localResultSet, paramDisbursementAccount, paramConnection);
      return localArrayList;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList a(ResultSet paramResultSet, DisbursementAccount paramDisbursementAccount, Connection paramConnection)
    throws DCException
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap1 = null;
    Object localObject = null;
    try
    {
      while (paramResultSet.next())
      {
        DateTime localDateTime = DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramDisbursementAccount.getLocale());
        if (localObject == null)
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        else if (!localDateTime.isSameDayInYearAs(localObject))
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        String str = paramResultSet.getString(24);
        a(localHashMap1, "DATADATE", localDateTime);
        int i2 = paramResultSet.getInt(2);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALDEBITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(15);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALCREDITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(16);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALDTCCREDITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(19);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "IMMEDFUNDSNEEDED", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), str, paramDisbursementAccount.getLocale()), i2));
        a(localHashMap1, "ONEDAYFUNDSNEEDED", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), str, paramDisbursementAccount.getLocale())));
        a(localHashMap1, "TWODAYFUNDSNEEDED", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), str, paramDisbursementAccount.getLocale())));
        a(localHashMap1, "VALUEDATETIME", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(9), paramDisbursementAccount.getLocale()));
        i2 = paramResultSet.getInt(17);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "CHECKSPAIDEARLY", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(18);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "CHECKSPAIDLATE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(22);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "CHECKSPAIDLAST", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(20);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "FEDESTIMATE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), str, paramDisbursementAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(21);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "LATEDEBITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), str, paramDisbursementAccount.getLocale()), i2));
        DisbursementSummary localDisbursementSummary = new DisbursementSummary();
        DCUtil.fillExtendABean(paramConnection, localDisbursementSummary, paramResultSet, 23);
        HashMap localHashMap2 = localDisbursementSummary.getHash();
        if (localHashMap2.size() != 0)
        {
          Iterator localIterator = localHashMap2.entrySet().iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            a(localHashMap1, localEntry.getKey(), localEntry.getValue());
          }
        }
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to create disbursement summary maps.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 604;
      throw new DCException(i1, "Failed to create disbursement summary maps.", localException);
    }
    return localArrayList;
  }
  
  private static void a(HashMap paramHashMap, Object paramObject1, Object paramObject2)
  {
    if ((paramObject2 != null) && (!paramHashMap.containsKey(paramObject1))) {
      paramHashMap.put(paramObject1, paramObject2);
    }
  }
  
  protected static DateTime getDisbursementSummariesMaxDate(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    Object localObject1 = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localResultSet = jdField_if(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      if (localResultSet.next())
      {
        localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(1), paramDisbursementAccount.getLocale());
        return localDateTime;
      }
      DateTime localDateTime = null;
      return localDateTime;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to get max date for disbursement summaries: SQLException", localSQLException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ResultSet jdField_if(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(jdField_new);
          DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(jdField_for);
          DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
          localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramDisbursementAccount.getRoutingNumber() != null) {
            localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
          }
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_if);
        DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(R);
        DCUtil.appendNullWhereClause(localStringBuffer, h, paramDisbursementAccount.getRoutingNumber());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
        localPreparedStatement.setString(2, paramDisbursementAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramDisbursementAccount.getRoutingNumber() != null) {
          localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
        }
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Unable to create disbursement summary for presentment.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 615;
      throw new DCException(i1, "Failed to get disbursement summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCDsbSummary
 * JD-Core Version:    0.7.0.1
 */