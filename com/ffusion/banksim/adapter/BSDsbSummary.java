/*    1:     */ package com.ffusion.banksim.adapter;
/*    2:     */ 
/*    3:     */ import com.ffusion.banksim.db.DBConnection;
/*    4:     */ import com.ffusion.banksim.interfaces.BSException;
/*    5:     */ import com.ffusion.beans.Currency;
/*    6:     */ import com.ffusion.beans.DateTime;
/*    7:     */ import com.ffusion.beans.SecureUser;
/*    8:     */ import com.ffusion.beans.disbursement.DisbursementAccount;
/*    9:     */ import com.ffusion.beans.disbursement.DisbursementAccounts;
/*   10:     */ import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
/*   11:     */ import com.ffusion.beans.disbursement.DisbursementPresentmentSummary;
/*   12:     */ import com.ffusion.beans.disbursement.DisbursementSummaries;
/*   13:     */ import com.ffusion.beans.disbursement.DisbursementSummary;
/*   14:     */ import com.ffusion.dataconsolidator.adapter.DCUtil;
/*   15:     */ import com.ffusion.util.db.DBUtil;
/*   16:     */ import java.sql.PreparedStatement;
/*   17:     */ import java.sql.ResultSet;
/*   18:     */ import java.sql.Statement;
/*   19:     */ import java.util.ArrayList;
/*   20:     */ import java.util.Calendar;
/*   21:     */ import java.util.HashMap;
/*   22:     */ import java.util.Iterator;
/*   23:     */ import java.util.Set;
/*   24:     */ 
/*   25:     */ public class BSDsbSummary
/*   26:     */ {
/*   27:     */   private static final String m = "INSERT INTO BS_DsbSummary( AccountID, DataDate, DataSource, NumItemsPending, TotalDebits, TotalCredits, TotalDTCCredits, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, ChecksPaidEarly, ChecksPaidLate, ChecksPaidLast, FedEstimate, LateDebits, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*   28:     */   private static final String c = "UPDATE BS_DsbSummary SET AccountID=?, DataDate=?, DataSource=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, BAIFileIdentifier=? WHERE AccountID=? AND DataDate=? ";
/*   29:  40 */   private static String g = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.ExtendABeanXMLID FROM BS_Account a, BS_DsbSummary b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? ORDER by b.DataDate";
/*   30:  44 */   private static String f = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.ExtendABeanXMLID FROM BS_Account a, BS_DsbSummary b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? ORDER by b.DataDate";
/*   31:  48 */   private static String e = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.ExtendABeanXMLID FROM BS_Account a, BS_DsbSummary b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? ORDER by b.DataDate";
/*   32:  52 */   private static String d = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.ExtendABeanXMLID FROM BS_Account a, BS_DsbSummary b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER by b.DataDate";
/*   33:  56 */   private static String b = "SELECT b.DataDate, b.NumItemsPending, b.TotalDebits, b.TotalCredits, b.TotalDTCCredits, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.ChecksPaidEarly, b.ChecksPaidLate, b.ChecksPaidLast, b.FedEstimate, b.LateDebits, b.ExtendABeanXMLID FROM BS_Account a, BS_DsbSummary b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate=?";
/*   34:  63 */   private static String jdField_new = "SELECT a.presentment, b.presentment, c.presentment, a.count1, b.count2, c.count3, a.credits, b.debits FROM (SELECT presentment, count(*) AS count1, sum(amount) AS credits FROM bs_dsbtransactions WHERE amount >= 0 ";
/*   35:  67 */   private static String i = " GROUP BY presentment) a FULL OUTER JOIN (select presentment, count(*) AS count2, sum(amount) AS debits FROM bs_dsbtransactions WHERE amount < 0 ";
/*   36:  71 */   private static String jdField_char = " GROUP BY presentment) b ON a.presentment = b.presentment FULL OUTER JOIN (select presentment,  count(*) AS count3 FROM bs_dsbtransactions WHERE amount IS null ";
/*   37:  75 */   private static String jdField_case = " GROUP BY presentment) c ON a.presentment = c.presentment OR b.presentment = c.presentment ";
/*   38:  78 */   private static String l = " AND DataDate >= ? AND DataDate <= ? ";
/*   39:  79 */   private static String jdField_else = " AND DataDate >= ? ";
/*   40:  80 */   private static String j = " AND DataDate <= ? ";
/*   41:  85 */   private static String jdField_for = "SELECT Presentment, Presentment, Presentment, sum(count1) AS count1, sum(count2) AS count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits  FROM (SELECT Presentment, count(*) AS count1, 0 AS count2, 0 AS count3, sum(Amount) AS credits, 0 AS debits FROM BS_DsbTransactions WHERE Amount >= 0 ";
/*   42:  91 */   private static String jdField_int = "GROUP BY Presentment UNION ALL select Presentment, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits,  sum(Amount) AS debits FROM BS_DsbTransactions WHERE Amount < 0 ";
/*   43:  96 */   private static String jdField_if = "GROUP BY Presentment UNION ALL select Presentment, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits,  0 AS debits FROM BS_DsbTransactions WHERE Amount IS null ";
/*   44: 101 */   private static String jdField_try = " GROUP BY Presentment) a  GROUP BY Presentment ORDER BY Presentment";
/*   45: 104 */   private static String a = " AND AccountID IN ";
/*   46: 108 */   private static String jdField_void = "SELECT a.count1, b.count2, c.count3, a.credits, b.debits FROM (SELECT t.accountid, count(*) AS count1, sum(t.amount) AS credits FROM BS_DsbTransactions t, BS_Account acc WHERE t.amount >= 0 AND t.datadate >= ? AND t.datadate <= ? AND t.presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.accountid) a FULL OUTER JOIN (SELECT t2.accountid, count(*) AS count2, sum(t2.amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.amount < 0 AND t2.datadate >= ? AND t2.datadate <= ? AND t2.presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.accountid) b ON a.accountid = b.accountid FULL OUTER JOIN (SELECT t3.accountid, count(*) AS count3 FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.amount IS NULL AND t3.datadate >= ? AND t3.datadate <= ? AND t3.presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.accountid) c ON a.accountid = c.accountid OR b.accountid = c.accountid";
/*   47: 121 */   private static String jdField_null = "SELECT a.count1, b.count2, c.count3, a.credits, b.debits FROM (SELECT t.accountid, count(*) AS count1, sum(t.amount) AS credits FROM BS_DsbTransactions t, BS_Account acc WHERE t.amount >= 0 AND t.datadate >= ? AND t.presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.accountid) a FULL OUTER JOIN (SELECT t2.accountid, count(*) AS count2, sum(t2.amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.amount < 0 AND t2.datadate >= ? AND t2.presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.accountid) b ON a.accountid = b.accountid FULL OUTER JOIN (SELECT t3.accountid, count(*) AS count3 FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.amount IS NULL AND t3.datadate >= ? AND t3.presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.accountid) c ON a.accountid = c.accountid OR b.accountid = c.accountid";
/*   48: 133 */   private static String jdField_long = "SELECT a.count1, b.count2, c.count3, a.credits, b.debits FROM (SELECT t.AccountID, count(*) AS count1, sum(t.Amount) AS credits FROM BS_DsbTransactions t, BS_Account acc WHERE t.Amount >= 0 AND t.DataDate <= ? AND t.Presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.AccountID) a FULL OUTER JOIN (SELECT t2.AccountID, count(*) AS count2, sum(t2.Amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate <= ? AND t2.Presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.AccountID) b ON a.AccountID = b.AccountID FULL OUTER JOIN (SELECT t3.AccountID, count(*) AS count3 FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate <= ? AND t3.Presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.AccountID) c ON a.AccountID = c.AccountID OR b.AccountID = c.AccountID";
/*   49: 146 */   private static String jdField_goto = "SELECT a.count1, b.count2, c.count3, a.credits, b.debits FROM (SELECT t.AccountID, count(*) AS count1, sum(t.Amount) AS credits FROM BS_DsbTransactions t, BS_Account acc WHERE t.Amount >= 0 AND t.Presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.AccountID) a FULL OUTER JOIN (SELECT t2.AccountID, count(*) AS count2, sum(t2.Amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.Amount < 0 AND t2.Presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.AccountID) b ON a.AccountID = b.AccountID FULL OUTER JOIN (SELECT t3.AccountID, count(*) AS count3 FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.Amount IS NULL AND t3.Presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.AccountID) c ON a.AccountID = c.AccountID OR b.AccountID = c.AccountID";
/*   50: 159 */   private static String jdField_do = "SELECT sum(count1) AS count1, sum(count2) AS count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits FROM (SELECT t.AccountID, count(*) AS count1, 0  AS count2, 0 AS count3, sum(t.Amount) AS credits, 0 AS debits FROM BS_DsbTransactions t, BS_Account acc WHERE t.Amount >= 0 AND t.DataDate >= ? AND t.DataDate <= ? AND t.Presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.AccountID UNION ALL SELECT t2.AccountID, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits, sum(t2.Amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate >= ? AND t2.DataDate <= ? AND t2.Presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.AccountID UNION ALL SELECT t3.AccountID, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits, 0 AS debits FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate >= ? AND t3.DataDate <= ? AND t3.Presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.AccountID) a ";
/*   51: 174 */   private static String h = "SELECT sum(count1) AS count1, sum(count2) AS count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits FROM (SELECT t.AccountID, count(*) AS count1, 0  AS count2, 0 AS count3, sum(t.Amount) AS credits, 0 AS debits FROM BS_DsbTransactions t, BS_Account acc WHERE t.Amount >= 0 AND t.DataDate >= ? AND t.Presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.AccountID UNION ALL SELECT t2.AccountID, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits, sum(t2.Amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate >= ? AND t2.Presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.AccountID UNION ALL SELECT t3.AccountID, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits, 0 AS debits FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate >= ? AND t3.Presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.AccountID) a ";
/*   52: 189 */   private static String jdField_byte = "SELECT sum(count1) AS count1, sum(count2) AS count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits FROM (SELECT t.AccountID, count(*) AS count1, 0  AS count2, 0 AS count3, sum(t.Amount) AS credits, 0 AS debits FROM BS_DsbTransactions t, BS_Account acc WHERE t.Amount >= 0 AND t.DataDate <= ? AND t.Presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.AccountID UNION ALL SELECT t2.AccountID, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits, sum(t2.Amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.Amount < 0 AND t2.DataDate <= ? AND t2.Presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.AccountID UNION ALL SELECT t3.AccountID, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits, 0 AS debits FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.Amount IS NULL AND t3.DataDate <= ? AND t3.Presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.AccountID) a ";
/*   53: 204 */   private static String k = "SELECT sum(count1) AS count1, sum(count2) AS count2, sum(count3) AS count3, sum(credits) AS credits, sum(debits) AS debits FROM (SELECT t.AccountID, count(*) AS count1, 0  AS count2, 0 AS count3, sum(t.Amount) AS credits, 0 AS debits FROM BS_DsbTransactions t, BS_Account acc WHERE t.Amount >= 0 AND t.Presentment = ? AND acc.AccountID = t.AccountID AND acc.AccountID = ? AND acc.RoutingNum = ? GROUP BY t.AccountID UNION ALL SELECT t2.AccountID, 0 AS count1, count(*) AS count2, 0 AS count3, 0 AS credits, sum(t2.Amount) AS debits FROM BS_DsbTransactions t2, BS_Account acc2 WHERE t2.Amount < 0 AND t2.Presentment = ? AND acc2.AccountID = t2.AccountID AND acc2.AccountID = ? AND acc2.RoutingNum = ? GROUP BY t2.AccountID UNION ALL SELECT t3.AccountID, 0 AS count1, 0 AS count2, count(*) AS count3, 0 AS credits, 0 AS debits FROM BS_DsbTransactions t3, BS_Account acc3 WHERE t3.Amount IS NULL AND t3.Presentment = ? AND acc3.AccountID = t3.AccountID AND acc3.AccountID = ? AND acc3.RoutingNum = ? GROUP BY t3.AccountID) a ";
/*   54:     */   
/*   55:     */   public static void addDisbursementSummary(DisbursementSummary paramDisbursementSummary, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/*   56:     */     throws BSException
/*   57:     */   {
/*   58: 222 */     PreparedStatement localPreparedStatement = null;
/*   59:     */     try
/*   60:     */     {
/*   61: 226 */       String str1 = null;
/*   62: 227 */       if (paramHashMap != null) {
/*   63: 228 */         str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
/*   64:     */       }
/*   65: 231 */       DisbursementAccount localDisbursementAccount = paramDisbursementSummary.getAccount();
/*   66:     */       
/*   67: 233 */       String str2 = localDisbursementAccount.getAccountID();
/*   68:     */       
/*   69: 235 */       DisbursementSummary localDisbursementSummary1 = a(localDisbursementAccount, paramDisbursementSummary.getSummaryDate(), paramDBConnection);
/*   70: 236 */       DisbursementSummary localDisbursementSummary2 = null;
/*   71: 238 */       if (localDisbursementSummary1 == null)
/*   72:     */       {
/*   73: 239 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_DsbSummary( AccountID, DataDate, DataSource, NumItemsPending, TotalDebits, TotalCredits, TotalDTCCredits, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, ChecksPaidEarly, ChecksPaidLate, ChecksPaidLast, FedEstimate, LateDebits, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*   74: 240 */         localDisbursementSummary2 = paramDisbursementSummary;
/*   75:     */       }
/*   76:     */       else
/*   77:     */       {
/*   78: 242 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "UPDATE BS_DsbSummary SET AccountID=?, DataDate=?, DataSource=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, BAIFileIdentifier=? WHERE AccountID=? AND DataDate=? ");
/*   79: 243 */         localDisbursementSummary2 = localDisbursementSummary1;
/*   80:     */         
/*   81: 245 */         Currency localCurrency = null;
/*   82: 246 */         DateTime localDateTime = null;
/*   83: 247 */         int n = -1;
/*   84:     */         
/*   85: 249 */         n = paramDisbursementSummary.getNumItemsPending();
/*   86: 250 */         if (n != -1) {
/*   87: 251 */           localDisbursementSummary2.setNumItemsPending(n);
/*   88:     */         }
/*   89: 254 */         localCurrency = paramDisbursementSummary.getTotalDebits();
/*   90: 255 */         if (localCurrency != null) {
/*   91: 256 */           localDisbursementSummary2.setTotalDebits(localCurrency);
/*   92:     */         }
/*   93: 259 */         localCurrency = paramDisbursementSummary.getTotalCredits();
/*   94: 260 */         if (localCurrency != null) {
/*   95: 261 */           localDisbursementSummary2.setTotalCredits(localCurrency);
/*   96:     */         }
/*   97: 264 */         localCurrency = paramDisbursementSummary.getTotalDTCCredits();
/*   98: 265 */         if (localCurrency != null) {
/*   99: 266 */           localDisbursementSummary2.setTotalDTCCredits(localCurrency);
/*  100:     */         }
/*  101: 269 */         localCurrency = paramDisbursementSummary.getImmediateFundsNeeded();
/*  102: 270 */         if (localCurrency != null) {
/*  103: 271 */           localDisbursementSummary2.setImmediateFundsNeeded(localCurrency);
/*  104:     */         }
/*  105: 274 */         localCurrency = paramDisbursementSummary.getOneDayFundsNeeded();
/*  106: 275 */         if (localCurrency != null) {
/*  107: 276 */           localDisbursementSummary2.setOneDayFundsNeeded(localCurrency);
/*  108:     */         }
/*  109: 279 */         localCurrency = paramDisbursementSummary.getTwoDayFundsNeeded();
/*  110: 280 */         if (localCurrency != null) {
/*  111: 281 */           localDisbursementSummary2.setTwoDayFundsNeeded(localCurrency);
/*  112:     */         }
/*  113: 284 */         localDateTime = paramDisbursementSummary.getValueDateTime();
/*  114: 285 */         if (localDateTime != null) {
/*  115: 286 */           localDisbursementSummary2.setValueDateTime(localDateTime);
/*  116:     */         }
/*  117: 289 */         localCurrency = paramDisbursementSummary.getTotalChecksPaidEarlyAmount();
/*  118: 290 */         if (localCurrency != null) {
/*  119: 291 */           localDisbursementSummary2.setTotalChecksPaidEarlyAmount(localCurrency);
/*  120:     */         }
/*  121: 294 */         localCurrency = paramDisbursementSummary.getTotalChecksPaidLateAmount();
/*  122: 295 */         if (localCurrency != null) {
/*  123: 296 */           localDisbursementSummary2.setTotalChecksPaidLateAmount(localCurrency);
/*  124:     */         }
/*  125: 299 */         localCurrency = paramDisbursementSummary.getTotalChecksPaidLastAmount();
/*  126: 300 */         if (localCurrency != null) {
/*  127: 301 */           localDisbursementSummary2.setTotalChecksPaidLastAmount(localCurrency);
/*  128:     */         }
/*  129: 304 */         localCurrency = paramDisbursementSummary.getFedPresentmentEstimate();
/*  130: 305 */         if (localCurrency != null) {
/*  131: 306 */           localDisbursementSummary2.setFedPresentmentEstimate(localCurrency);
/*  132:     */         }
/*  133: 309 */         localCurrency = paramDisbursementSummary.getLateDebits();
/*  134: 310 */         if (localCurrency != null) {
/*  135: 311 */           localDisbursementSummary2.setLateDebits(localCurrency);
/*  136:     */         }
/*  137:     */       }
/*  138: 315 */       localPreparedStatement.setString(1, str2);
/*  139: 316 */       DCUtil.fillTimestampColumn(localPreparedStatement, 2, localDisbursementSummary2.getSummaryDate());
/*  140: 317 */       localPreparedStatement.setInt(3, paramInt);
/*  141: 318 */       localPreparedStatement.setInt(4, localDisbursementSummary2.getNumItemsPending());
/*  142: 319 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 5, localDisbursementSummary2.getTotalDebits());
/*  143: 320 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 6, localDisbursementSummary2.getTotalCredits());
/*  144: 321 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 7, localDisbursementSummary2.getTotalDTCCredits());
/*  145: 322 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localDisbursementSummary2.getImmediateFundsNeeded());
/*  146: 323 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localDisbursementSummary2.getOneDayFundsNeeded());
/*  147: 324 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 10, localDisbursementSummary2.getTwoDayFundsNeeded());
/*  148: 325 */       DCUtil.fillTimestampColumn(localPreparedStatement, 11, localDisbursementSummary2.getValueDateTime());
/*  149: 326 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 12, localDisbursementSummary2.getTotalChecksPaidEarlyAmount());
/*  150: 327 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 13, localDisbursementSummary2.getTotalChecksPaidLateAmount());
/*  151: 328 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localDisbursementSummary2.getTotalChecksPaidLastAmount());
/*  152: 329 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 15, localDisbursementSummary2.getFedPresentmentEstimate());
/*  153: 330 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 16, localDisbursementSummary2.getLateDebits());
/*  154: 332 */       if (localDisbursementSummary1 == null)
/*  155:     */       {
/*  156: 333 */         localPreparedStatement.setLong(17, BSExtendABeanXML.addExtendABeanXML(paramDBConnection, localDisbursementSummary2.getExtendABeanXML()));
/*  157: 334 */         localPreparedStatement.setString(18, null);
/*  158: 335 */         localPreparedStatement.setString(19, str1);
/*  159: 336 */         DBConnection.executeUpdate(localPreparedStatement, "INSERT INTO BS_DsbSummary( AccountID, DataDate, DataSource, NumItemsPending, TotalDebits, TotalCredits, TotalDTCCredits, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, ChecksPaidEarly, ChecksPaidLate, ChecksPaidLast, FedEstimate, LateDebits, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*  160:     */       }
/*  161:     */       else
/*  162:     */       {
/*  163: 338 */         localPreparedStatement.setString(17, str1);
/*  164: 339 */         localPreparedStatement.setString(18, str2);
/*  165: 340 */         DCUtil.fillTimestampColumn(localPreparedStatement, 19, localDisbursementSummary2.getSummaryDate());
/*  166: 341 */         DBConnection.executeUpdate(localPreparedStatement, "UPDATE BS_DsbSummary SET AccountID=?, DataDate=?, DataSource=?, NumItemsPending=?, TotalDebits=?, TotalCredits=?, TotalDTCCredits=?, ImmedFundsNeeded=?, OneDayFundsNeeded=?, TwoDayFundsNeeded=?, ValueDateTime=?, ChecksPaidEarly=?, ChecksPaidLate=?, ChecksPaidLast=?, FedEstimate=?, LateDebits=?, BAIFileIdentifier=? WHERE AccountID=? AND DataDate=? ");
/*  167:     */       }
/*  168:     */     }
/*  169:     */     catch (Exception localException)
/*  170:     */     {
/*  171: 345 */       throw new BSException(1, localException.getMessage());
/*  172:     */     }
/*  173:     */     finally
/*  174:     */     {
/*  175: 347 */       if (localPreparedStatement != null) {
/*  176: 348 */         DBConnection.closeStatement(localPreparedStatement);
/*  177:     */       }
/*  178:     */     }
/*  179:     */   }
/*  180:     */   
/*  181:     */   public static DisbursementSummaries getDisbursementSummaries(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  182:     */     throws BSException
/*  183:     */   {
/*  184: 369 */     PreparedStatement localPreparedStatement = null;
/*  185: 370 */     DisbursementSummaries localDisbursementSummaries1 = null;
/*  186:     */     
/*  187: 372 */     ResultSet localResultSet = null;
/*  188:     */     try
/*  189:     */     {
/*  190: 374 */       if (paramCalendar1 != null)
/*  191:     */       {
/*  192: 375 */         if (paramCalendar2 != null)
/*  193:     */         {
/*  194: 376 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, g);
/*  195: 377 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/*  196: 378 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/*  197: 379 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/*  198: 380 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/*  199: 381 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, g);
/*  200:     */         }
/*  201:     */         else
/*  202:     */         {
/*  203: 383 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, f);
/*  204: 384 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/*  205: 385 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/*  206: 386 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/*  207: 387 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, f);
/*  208:     */         }
/*  209:     */       }
/*  210: 389 */       else if (paramCalendar2 != null)
/*  211:     */       {
/*  212: 390 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, e);
/*  213: 391 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/*  214: 392 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/*  215: 393 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/*  216: 394 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, e);
/*  217:     */       }
/*  218:     */       else
/*  219:     */       {
/*  220: 396 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, d);
/*  221: 397 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/*  222: 398 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/*  223: 399 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, d);
/*  224:     */       }
/*  225: 403 */       localDisbursementSummaries1 = new DisbursementSummaries();
/*  226: 404 */       DisbursementSummary localDisbursementSummary = null;
/*  227: 405 */       while (localResultSet.next())
/*  228:     */       {
/*  229: 406 */         localDisbursementSummary = a(paramDisbursementAccount, localResultSet);
/*  230: 407 */         localDisbursementSummaries1.add(localDisbursementSummary);
/*  231:     */       }
/*  232: 409 */       DBUtil.closeResultSet(localResultSet);
/*  233: 410 */       return localDisbursementSummaries1;
/*  234:     */     }
/*  235:     */     catch (Exception localException)
/*  236:     */     {
/*  237: 413 */       throw new BSException(1, localException.getMessage());
/*  238:     */     }
/*  239:     */     finally
/*  240:     */     {
/*  241: 415 */       if (localPreparedStatement != null) {
/*  242: 416 */         DBConnection.closeStatement(localPreparedStatement);
/*  243:     */       }
/*  244: 419 */       if (paramDBConnection != null) {
/*  245: 420 */         paramDBConnection.close();
/*  246:     */       }
/*  247:     */     }
/*  248:     */   }
/*  249:     */   
/*  250:     */   private static DisbursementSummary a(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar, DBConnection paramDBConnection)
/*  251:     */     throws BSException
/*  252:     */   {
/*  253: 438 */     PreparedStatement localPreparedStatement = null;
/*  254: 439 */     Object localObject1 = null;
/*  255: 440 */     ResultSet localResultSet = null;
/*  256:     */     try
/*  257:     */     {
/*  258: 442 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, b);
/*  259: 443 */       localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/*  260: 444 */       localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/*  261: 445 */       DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
/*  262: 446 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, b);
/*  263:     */       
/*  264: 448 */       DisbursementSummary localDisbursementSummary1 = null;
/*  265: 449 */       if (localResultSet.next()) {
/*  266: 450 */         localDisbursementSummary1 = a(paramDisbursementAccount, localResultSet);
/*  267:     */       }
/*  268: 452 */       DBUtil.closeResultSet(localResultSet);
/*  269: 453 */       return localDisbursementSummary1;
/*  270:     */     }
/*  271:     */     catch (Exception localException)
/*  272:     */     {
/*  273: 456 */       throw new BSException(1, localException.getMessage());
/*  274:     */     }
/*  275:     */     finally
/*  276:     */     {
/*  277: 458 */       if (localPreparedStatement != null) {
/*  278: 459 */         DBConnection.closeStatement(localPreparedStatement);
/*  279:     */       }
/*  280:     */     }
/*  281:     */   }
/*  282:     */   
/*  283:     */   public static DisbursementSummaries getDisbursementSummariesForPresentment(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  284:     */     throws BSException
/*  285:     */   {
/*  286: 477 */     boolean bool = BSAdapter.CONNECTIONTYPE.equalsIgnoreCase("ASE");
/*  287: 478 */     if (bool) {
/*  288: 479 */       return getDisbursementSummariesForPresentmentASE(paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2, paramDBConnection, paramHashMap);
/*  289:     */     }
/*  290: 481 */     return a(paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2, paramDBConnection, paramHashMap);
/*  291:     */   }
/*  292:     */   
/*  293:     */   private static DisbursementSummaries a(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  294:     */     throws BSException
/*  295:     */   {
/*  296: 499 */     PreparedStatement localPreparedStatement = null;
/*  297: 500 */     DisbursementSummaries localDisbursementSummaries1 = null;
/*  298: 501 */     ResultSet localResultSet = null;
/*  299:     */     try
/*  300:     */     {
/*  301: 503 */       if (paramCalendar1 != null)
/*  302:     */       {
/*  303: 504 */         if (paramCalendar2 != null)
/*  304:     */         {
/*  305: 505 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_void);
/*  306: 506 */           DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramCalendar1);
/*  307: 507 */           DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar2);
/*  308: 508 */           localPreparedStatement.setString(3, paramString);
/*  309: 509 */           localPreparedStatement.setString(4, paramDisbursementAccount.getAccountID());
/*  310: 510 */           localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
/*  311: 511 */           DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar1);
/*  312: 512 */           DCUtil.fillTimestampColumn(localPreparedStatement, 7, paramCalendar2);
/*  313: 513 */           localPreparedStatement.setString(8, paramString);
/*  314: 514 */           localPreparedStatement.setString(9, paramDisbursementAccount.getAccountID());
/*  315: 515 */           localPreparedStatement.setString(10, paramDisbursementAccount.getRoutingNumber());
/*  316: 516 */           DCUtil.fillTimestampColumn(localPreparedStatement, 11, paramCalendar1);
/*  317: 517 */           DCUtil.fillTimestampColumn(localPreparedStatement, 12, paramCalendar2);
/*  318: 518 */           localPreparedStatement.setString(13, paramString);
/*  319: 519 */           localPreparedStatement.setString(14, paramDisbursementAccount.getAccountID());
/*  320: 520 */           localPreparedStatement.setString(15, paramDisbursementAccount.getRoutingNumber());
/*  321: 521 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_void);
/*  322:     */         }
/*  323:     */         else
/*  324:     */         {
/*  325: 523 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_null);
/*  326: 524 */           DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramCalendar1);
/*  327: 525 */           localPreparedStatement.setString(2, paramString);
/*  328: 526 */           localPreparedStatement.setString(3, paramDisbursementAccount.getAccountID());
/*  329: 527 */           localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
/*  330: 528 */           DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar1);
/*  331: 529 */           localPreparedStatement.setString(6, paramString);
/*  332: 530 */           localPreparedStatement.setString(7, paramDisbursementAccount.getAccountID());
/*  333: 531 */           localPreparedStatement.setString(8, paramDisbursementAccount.getRoutingNumber());
/*  334: 532 */           DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramCalendar1);
/*  335: 533 */           localPreparedStatement.setString(10, paramString);
/*  336: 534 */           localPreparedStatement.setString(11, paramDisbursementAccount.getAccountID());
/*  337: 535 */           localPreparedStatement.setString(12, paramDisbursementAccount.getRoutingNumber());
/*  338: 536 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_null);
/*  339:     */         }
/*  340:     */       }
/*  341: 538 */       else if (paramCalendar2 != null)
/*  342:     */       {
/*  343: 539 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_long);
/*  344: 540 */         DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramCalendar2);
/*  345: 541 */         localPreparedStatement.setString(2, paramString);
/*  346: 542 */         localPreparedStatement.setString(3, paramDisbursementAccount.getAccountID());
/*  347: 543 */         localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
/*  348: 544 */         DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar2);
/*  349: 545 */         localPreparedStatement.setString(6, paramString);
/*  350: 546 */         localPreparedStatement.setString(7, paramDisbursementAccount.getAccountID());
/*  351: 547 */         localPreparedStatement.setString(8, paramDisbursementAccount.getRoutingNumber());
/*  352: 548 */         DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramCalendar2);
/*  353: 549 */         localPreparedStatement.setString(10, paramString);
/*  354: 550 */         localPreparedStatement.setString(11, paramDisbursementAccount.getAccountID());
/*  355: 551 */         localPreparedStatement.setString(12, paramDisbursementAccount.getRoutingNumber());
/*  356: 552 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_long);
/*  357:     */       }
/*  358:     */       else
/*  359:     */       {
/*  360: 554 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_goto);
/*  361: 555 */         localPreparedStatement.setString(1, paramString);
/*  362: 556 */         localPreparedStatement.setString(2, paramDisbursementAccount.getAccountID());
/*  363: 557 */         localPreparedStatement.setString(3, paramDisbursementAccount.getRoutingNumber());
/*  364: 558 */         localPreparedStatement.setString(4, paramString);
/*  365: 559 */         localPreparedStatement.setString(5, paramDisbursementAccount.getAccountID());
/*  366: 560 */         localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
/*  367: 561 */         localPreparedStatement.setString(7, paramString);
/*  368: 562 */         localPreparedStatement.setString(8, paramDisbursementAccount.getAccountID());
/*  369: 563 */         localPreparedStatement.setString(9, paramDisbursementAccount.getRoutingNumber());
/*  370: 564 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_goto);
/*  371:     */       }
/*  372: 568 */       localDisbursementSummaries1 = new DisbursementSummaries();
/*  373: 569 */       DisbursementSummary localDisbursementSummary = null;
/*  374: 570 */       while (localResultSet.next())
/*  375:     */       {
/*  376: 571 */         localDisbursementSummary = a(paramDisbursementAccount, paramString, localResultSet);
/*  377: 572 */         localDisbursementSummaries1.add(localDisbursementSummary);
/*  378:     */       }
/*  379: 574 */       DBUtil.closeResultSet(localResultSet);
/*  380: 575 */       return localDisbursementSummaries1;
/*  381:     */     }
/*  382:     */     catch (Exception localException)
/*  383:     */     {
/*  384: 578 */       throw new BSException(1, localException.getMessage());
/*  385:     */     }
/*  386:     */     finally
/*  387:     */     {
/*  388: 580 */       if (localPreparedStatement != null) {
/*  389: 581 */         DBConnection.closeStatement(localPreparedStatement);
/*  390:     */       }
/*  391: 584 */       if (paramDBConnection != null) {
/*  392: 585 */         paramDBConnection.close();
/*  393:     */       }
/*  394:     */     }
/*  395:     */   }
/*  396:     */   
/*  397:     */   public static DisbursementSummaries getDisbursementSummariesForPresentmentASE(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  398:     */     throws BSException
/*  399:     */   {
/*  400: 603 */     PreparedStatement localPreparedStatement = null;
/*  401: 604 */     DisbursementSummaries localDisbursementSummaries1 = null;
/*  402: 605 */     ResultSet localResultSet = null;
/*  403:     */     try
/*  404:     */     {
/*  405: 607 */       if (paramCalendar1 != null)
/*  406:     */       {
/*  407: 608 */         if (paramCalendar2 != null)
/*  408:     */         {
/*  409: 609 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_do);
/*  410: 610 */           DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramCalendar1);
/*  411: 611 */           DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCalendar2);
/*  412: 612 */           localPreparedStatement.setString(3, paramString);
/*  413: 613 */           localPreparedStatement.setString(4, paramDisbursementAccount.getAccountID());
/*  414: 614 */           localPreparedStatement.setString(5, paramDisbursementAccount.getRoutingNumber());
/*  415: 615 */           DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCalendar1);
/*  416: 616 */           DCUtil.fillTimestampColumn(localPreparedStatement, 7, paramCalendar2);
/*  417: 617 */           localPreparedStatement.setString(8, paramString);
/*  418: 618 */           localPreparedStatement.setString(9, paramDisbursementAccount.getAccountID());
/*  419: 619 */           localPreparedStatement.setString(10, paramDisbursementAccount.getRoutingNumber());
/*  420: 620 */           DCUtil.fillTimestampColumn(localPreparedStatement, 11, paramCalendar1);
/*  421: 621 */           DCUtil.fillTimestampColumn(localPreparedStatement, 12, paramCalendar2);
/*  422: 622 */           localPreparedStatement.setString(13, paramString);
/*  423: 623 */           localPreparedStatement.setString(14, paramDisbursementAccount.getAccountID());
/*  424: 624 */           localPreparedStatement.setString(15, paramDisbursementAccount.getRoutingNumber());
/*  425: 625 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_do);
/*  426:     */         }
/*  427:     */         else
/*  428:     */         {
/*  429: 627 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, h);
/*  430: 628 */           DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramCalendar1);
/*  431: 629 */           localPreparedStatement.setString(2, paramString);
/*  432: 630 */           localPreparedStatement.setString(3, paramDisbursementAccount.getAccountID());
/*  433: 631 */           localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
/*  434: 632 */           DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar1);
/*  435: 633 */           localPreparedStatement.setString(6, paramString);
/*  436: 634 */           localPreparedStatement.setString(7, paramDisbursementAccount.getAccountID());
/*  437: 635 */           localPreparedStatement.setString(8, paramDisbursementAccount.getRoutingNumber());
/*  438: 636 */           DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramCalendar1);
/*  439: 637 */           localPreparedStatement.setString(10, paramString);
/*  440: 638 */           localPreparedStatement.setString(11, paramDisbursementAccount.getAccountID());
/*  441: 639 */           localPreparedStatement.setString(12, paramDisbursementAccount.getRoutingNumber());
/*  442: 640 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, h);
/*  443:     */         }
/*  444:     */       }
/*  445: 642 */       else if (paramCalendar2 != null)
/*  446:     */       {
/*  447: 643 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_byte);
/*  448: 644 */         DCUtil.fillTimestampColumn(localPreparedStatement, 1, paramCalendar2);
/*  449: 645 */         localPreparedStatement.setString(2, paramString);
/*  450: 646 */         localPreparedStatement.setString(3, paramDisbursementAccount.getAccountID());
/*  451: 647 */         localPreparedStatement.setString(4, paramDisbursementAccount.getRoutingNumber());
/*  452: 648 */         DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar2);
/*  453: 649 */         localPreparedStatement.setString(6, paramString);
/*  454: 650 */         localPreparedStatement.setString(7, paramDisbursementAccount.getAccountID());
/*  455: 651 */         localPreparedStatement.setString(8, paramDisbursementAccount.getRoutingNumber());
/*  456: 652 */         DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramCalendar2);
/*  457: 653 */         localPreparedStatement.setString(10, paramString);
/*  458: 654 */         localPreparedStatement.setString(11, paramDisbursementAccount.getAccountID());
/*  459: 655 */         localPreparedStatement.setString(12, paramDisbursementAccount.getRoutingNumber());
/*  460: 656 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_byte);
/*  461:     */       }
/*  462:     */       else
/*  463:     */       {
/*  464: 658 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, k);
/*  465: 659 */         localPreparedStatement.setString(1, paramString);
/*  466: 660 */         localPreparedStatement.setString(2, paramDisbursementAccount.getAccountID());
/*  467: 661 */         localPreparedStatement.setString(3, paramDisbursementAccount.getRoutingNumber());
/*  468: 662 */         localPreparedStatement.setString(4, paramString);
/*  469: 663 */         localPreparedStatement.setString(5, paramDisbursementAccount.getAccountID());
/*  470: 664 */         localPreparedStatement.setString(6, paramDisbursementAccount.getRoutingNumber());
/*  471: 665 */         localPreparedStatement.setString(7, paramString);
/*  472: 666 */         localPreparedStatement.setString(8, paramDisbursementAccount.getAccountID());
/*  473: 667 */         localPreparedStatement.setString(9, paramDisbursementAccount.getRoutingNumber());
/*  474: 668 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, k);
/*  475:     */       }
/*  476: 672 */       localDisbursementSummaries1 = new DisbursementSummaries();
/*  477: 673 */       DisbursementSummary localDisbursementSummary = null;
/*  478: 674 */       while (localResultSet.next())
/*  479:     */       {
/*  480: 675 */         localDisbursementSummary = a(paramDisbursementAccount, paramString, localResultSet);
/*  481: 676 */         localDisbursementSummaries1.add(localDisbursementSummary);
/*  482:     */       }
/*  483: 678 */       DBUtil.closeResultSet(localResultSet);
/*  484: 679 */       return localDisbursementSummaries1;
/*  485:     */     }
/*  486:     */     catch (Exception localException)
/*  487:     */     {
/*  488: 682 */       throw new BSException(1, localException.getMessage());
/*  489:     */     }
/*  490:     */     finally
/*  491:     */     {
/*  492: 684 */       if (localPreparedStatement != null) {
/*  493: 685 */         DBConnection.closeStatement(localPreparedStatement);
/*  494:     */       }
/*  495: 688 */       if (paramDBConnection != null) {
/*  496: 689 */         paramDBConnection.close();
/*  497:     */       }
/*  498:     */     }
/*  499:     */   }
/*  500:     */   
/*  501:     */   public static DisbursementPresentmentSummaries getDisbursementPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  502:     */     throws BSException
/*  503:     */   {
/*  504: 708 */     boolean bool = BSAdapter.CONNECTIONTYPE.equalsIgnoreCase("ASE");
/*  505: 709 */     if (bool) {
/*  506: 710 */       return jdField_if(paramDisbursementAccounts, paramCalendar1, paramCalendar2, paramDBConnection, paramHashMap);
/*  507:     */     }
/*  508: 712 */     return a(paramDisbursementAccounts, paramCalendar1, paramCalendar2, paramDBConnection, paramHashMap);
/*  509:     */   }
/*  510:     */   
/*  511:     */   private static DisbursementPresentmentSummaries jdField_if(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  512:     */     throws BSException
/*  513:     */   {
/*  514: 728 */     DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = new DisbursementPresentmentSummaries();
/*  515: 730 */     if (paramDisbursementAccounts.size() <= 0) {
/*  516: 731 */       return localDisbursementPresentmentSummaries;
/*  517:     */     }
/*  518: 735 */     HashMap localHashMap = new HashMap();
/*  519:     */     Object localObject2;
/*  520:     */     Object localObject3;
/*  521: 736 */     for (int n = 0; n < paramDisbursementAccounts.size(); n++)
/*  522:     */     {
/*  523: 737 */       localObject1 = (DisbursementAccount)paramDisbursementAccounts.get(n);
/*  524: 738 */       localObject2 = ((DisbursementAccount)localObject1).getCurrencyType();
/*  525: 740 */       if (localHashMap.containsKey(localObject2))
/*  526:     */       {
/*  527: 741 */         localObject3 = (ArrayList)localHashMap.get(localObject2);
/*  528: 742 */         ((ArrayList)localObject3).add(localObject1);
/*  529:     */       }
/*  530:     */       else
/*  531:     */       {
/*  532: 744 */         localObject3 = new ArrayList();
/*  533: 745 */         ((ArrayList)localObject3).add(localObject1);
/*  534: 746 */         localHashMap.put(localObject2, localObject3);
/*  535:     */       }
/*  536:     */     }
/*  537: 751 */     ResultSet localResultSet = null;
/*  538: 752 */     Object localObject1 = null;
/*  539:     */     try
/*  540:     */     {
/*  541: 755 */       localObject2 = localHashMap.keySet().iterator();
/*  542: 757 */       while (((Iterator)localObject2).hasNext())
/*  543:     */       {
/*  544: 758 */         localObject3 = (String)((Iterator)localObject2).next();
/*  545: 759 */         ArrayList localArrayList = (ArrayList)localHashMap.get(localObject3);
/*  546:     */         
/*  547:     */ 
/*  548: 762 */         StringBuffer localStringBuffer1 = new StringBuffer();
/*  549: 763 */         int i1 = 1;
/*  550:     */         Object localObject4;
/*  551: 764 */         for (int i2 = 0; i2 < localArrayList.size(); i2++)
/*  552:     */         {
/*  553: 765 */           localObject4 = (DisbursementAccount)localArrayList.get(i2);
/*  554: 766 */           if ((((DisbursementAccount)localObject4).getAccountID() != null) && (((DisbursementAccount)localObject4).getAccountID().length() != 0))
/*  555:     */           {
/*  556: 767 */             if (i1 == 0)
/*  557:     */             {
/*  558: 768 */               localStringBuffer1.append(", ");
/*  559:     */             }
/*  560:     */             else
/*  561:     */             {
/*  562: 770 */               i1 = 0;
/*  563: 771 */               localStringBuffer1.append(" ( ");
/*  564:     */             }
/*  565: 773 */             localStringBuffer1.append("'" + ((DisbursementAccount)localObject4).getAccountID() + "'");
/*  566:     */           }
/*  567:     */         }
/*  568: 777 */         if (localStringBuffer1.length() != 0)
/*  569:     */         {
/*  570: 778 */           localStringBuffer1.append(" ) ");
/*  571:     */           
/*  572:     */ 
/*  573:     */ 
/*  574:     */ 
/*  575: 783 */           StringBuffer localStringBuffer2 = new StringBuffer(jdField_for);
/*  576: 787 */           if (paramCalendar1 != null)
/*  577:     */           {
/*  578: 788 */             if (paramCalendar2 != null)
/*  579:     */             {
/*  580: 789 */               localStringBuffer2.append(l);
/*  581: 790 */               if (localStringBuffer1.length() != 0)
/*  582:     */               {
/*  583: 791 */                 localStringBuffer2.append(a);
/*  584: 792 */                 localStringBuffer2.append(localStringBuffer1);
/*  585:     */               }
/*  586: 794 */               localStringBuffer2.append(jdField_int);
/*  587: 795 */               localStringBuffer2.append(l);
/*  588: 796 */               if (localStringBuffer1.length() != 0)
/*  589:     */               {
/*  590: 797 */                 localStringBuffer2.append(a);
/*  591: 798 */                 localStringBuffer2.append(localStringBuffer1);
/*  592:     */               }
/*  593: 800 */               localStringBuffer2.append(jdField_if);
/*  594: 801 */               localStringBuffer2.append(l);
/*  595: 802 */               if (localStringBuffer1.length() != 0)
/*  596:     */               {
/*  597: 803 */                 localStringBuffer2.append(a);
/*  598: 804 */                 localStringBuffer2.append(localStringBuffer1);
/*  599:     */               }
/*  600: 806 */               localStringBuffer2.append(jdField_try);
/*  601: 807 */               localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  602: 808 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 1, paramCalendar1);
/*  603: 809 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 2, paramCalendar2);
/*  604: 810 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 3, paramCalendar1);
/*  605: 811 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 4, paramCalendar2);
/*  606: 812 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 5, paramCalendar1);
/*  607: 813 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 6, paramCalendar2);
/*  608: 814 */               localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  609:     */             }
/*  610:     */             else
/*  611:     */             {
/*  612: 816 */               localStringBuffer2.append(jdField_else);
/*  613: 817 */               if (localStringBuffer1.length() != 0)
/*  614:     */               {
/*  615: 818 */                 localStringBuffer2.append(a);
/*  616: 819 */                 localStringBuffer2.append(localStringBuffer1);
/*  617:     */               }
/*  618: 821 */               localStringBuffer2.append(jdField_int);
/*  619: 822 */               localStringBuffer2.append(jdField_else);
/*  620: 823 */               if (localStringBuffer1.length() != 0)
/*  621:     */               {
/*  622: 824 */                 localStringBuffer2.append(a);
/*  623: 825 */                 localStringBuffer2.append(localStringBuffer1);
/*  624:     */               }
/*  625: 827 */               localStringBuffer2.append(jdField_if);
/*  626: 828 */               localStringBuffer2.append(jdField_else);
/*  627: 829 */               if (localStringBuffer1.length() != 0)
/*  628:     */               {
/*  629: 830 */                 localStringBuffer2.append(a);
/*  630: 831 */                 localStringBuffer2.append(localStringBuffer1);
/*  631:     */               }
/*  632: 833 */               localStringBuffer2.append(jdField_try);
/*  633: 834 */               localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  634: 835 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 1, paramCalendar1);
/*  635: 836 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 2, paramCalendar1);
/*  636: 837 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 3, paramCalendar1);
/*  637: 838 */               localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  638:     */             }
/*  639:     */           }
/*  640: 840 */           else if (paramCalendar2 != null)
/*  641:     */           {
/*  642: 841 */             localStringBuffer2.append(j);
/*  643: 842 */             if (localStringBuffer1.length() != 0)
/*  644:     */             {
/*  645: 843 */               localStringBuffer2.append(a);
/*  646: 844 */               localStringBuffer2.append(localStringBuffer1);
/*  647:     */             }
/*  648: 846 */             localStringBuffer2.append(jdField_int);
/*  649: 847 */             localStringBuffer2.append(j);
/*  650: 848 */             if (localStringBuffer1.length() != 0)
/*  651:     */             {
/*  652: 849 */               localStringBuffer2.append(a);
/*  653: 850 */               localStringBuffer2.append(localStringBuffer1);
/*  654:     */             }
/*  655: 852 */             localStringBuffer2.append(jdField_if);
/*  656: 853 */             localStringBuffer2.append(j);
/*  657: 854 */             if (localStringBuffer1.length() != 0)
/*  658:     */             {
/*  659: 855 */               localStringBuffer2.append(a);
/*  660: 856 */               localStringBuffer2.append(localStringBuffer1);
/*  661:     */             }
/*  662: 858 */             localStringBuffer2.append(jdField_try);
/*  663:     */             
/*  664: 860 */             localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  665: 861 */             DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 1, paramCalendar2);
/*  666: 862 */             DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 2, paramCalendar2);
/*  667: 863 */             DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 3, paramCalendar2);
/*  668: 864 */             localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  669:     */           }
/*  670:     */           else
/*  671:     */           {
/*  672: 866 */             if (localStringBuffer1.length() != 0)
/*  673:     */             {
/*  674: 867 */               localStringBuffer2.append(a);
/*  675: 868 */               localStringBuffer2.append(localStringBuffer1);
/*  676: 869 */               localStringBuffer2.append(jdField_int);
/*  677: 870 */               localStringBuffer2.append(a);
/*  678: 871 */               localStringBuffer2.append(localStringBuffer1);
/*  679: 872 */               localStringBuffer2.append(jdField_if);
/*  680: 873 */               localStringBuffer2.append(a);
/*  681: 874 */               localStringBuffer2.append(localStringBuffer1);
/*  682:     */             }
/*  683:     */             else
/*  684:     */             {
/*  685: 876 */               localStringBuffer2.append(jdField_int);
/*  686: 877 */               localStringBuffer2.append(jdField_if);
/*  687:     */             }
/*  688: 879 */             localStringBuffer2.append(jdField_try);
/*  689:     */             
/*  690: 881 */             localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  691: 882 */             localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  692:     */           }
/*  693: 885 */           localObject4 = null;
/*  694: 886 */           while (localResultSet.next())
/*  695:     */           {
/*  696: 887 */             localObject4 = a(localResultSet, (String)localObject3);
/*  697:     */             
/*  698:     */ 
/*  699: 890 */             Iterator localIterator = localDisbursementPresentmentSummaries.iterator();
/*  700: 891 */             int i3 = 0;
/*  701:     */             DisbursementPresentmentSummary localDisbursementPresentmentSummary;
/*  702: 892 */             while (localIterator.hasNext())
/*  703:     */             {
/*  704: 893 */               localDisbursementPresentmentSummary = (DisbursementPresentmentSummary)localIterator.next();
/*  705: 895 */               if (localDisbursementPresentmentSummary.getPresentment().equals(((DisbursementPresentmentSummary)localObject4).getPresentment()))
/*  706:     */               {
/*  707: 896 */                 localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject4);
/*  708: 897 */                 i3 = 1;
/*  709: 898 */                 break;
/*  710:     */               }
/*  711:     */             }
/*  712: 901 */             if (i3 == 0)
/*  713:     */             {
/*  714: 903 */               localDisbursementPresentmentSummary = new DisbursementPresentmentSummary((SecureUser)paramHashMap.get("SECURE_USER"));
/*  715:     */               
/*  716: 905 */               localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject4);
/*  717:     */               
/*  718: 907 */               localDisbursementPresentmentSummaries.add(localDisbursementPresentmentSummary);
/*  719:     */             }
/*  720:     */           }
/*  721: 910 */           localResultSet.close();
/*  722:     */         }
/*  723:     */       }
/*  724: 912 */       return localDisbursementPresentmentSummaries;
/*  725:     */     }
/*  726:     */     catch (Exception localException)
/*  727:     */     {
/*  728: 915 */       throw new BSException(1, localException.getMessage());
/*  729:     */     }
/*  730:     */     finally
/*  731:     */     {
/*  732: 917 */       if (localObject1 != null) {
/*  733: 918 */         DBConnection.closeStatement((Statement)localObject1);
/*  734:     */       }
/*  735: 921 */       if (paramDBConnection != null) {
/*  736: 922 */         paramDBConnection.close();
/*  737:     */       }
/*  738:     */     }
/*  739:     */   }
/*  740:     */   
/*  741:     */   private static DisbursementPresentmentSummaries a(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/*  742:     */     throws BSException
/*  743:     */   {
/*  744: 939 */     DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = new DisbursementPresentmentSummaries();
/*  745: 941 */     if (paramDisbursementAccounts.size() <= 0) {
/*  746: 942 */       return localDisbursementPresentmentSummaries;
/*  747:     */     }
/*  748: 946 */     HashMap localHashMap = new HashMap();
/*  749:     */     Object localObject2;
/*  750:     */     Object localObject3;
/*  751: 947 */     for (int n = 0; n < paramDisbursementAccounts.size(); n++)
/*  752:     */     {
/*  753: 948 */       localObject1 = (DisbursementAccount)paramDisbursementAccounts.get(n);
/*  754: 949 */       localObject2 = ((DisbursementAccount)localObject1).getCurrencyType();
/*  755: 951 */       if (localHashMap.containsKey(localObject2))
/*  756:     */       {
/*  757: 952 */         localObject3 = (ArrayList)localHashMap.get(localObject2);
/*  758: 953 */         ((ArrayList)localObject3).add(localObject1);
/*  759:     */       }
/*  760:     */       else
/*  761:     */       {
/*  762: 955 */         localObject3 = new ArrayList();
/*  763: 956 */         ((ArrayList)localObject3).add(localObject1);
/*  764: 957 */         localHashMap.put(localObject2, localObject3);
/*  765:     */       }
/*  766:     */     }
/*  767: 962 */     ResultSet localResultSet = null;
/*  768: 963 */     Object localObject1 = null;
/*  769:     */     try
/*  770:     */     {
/*  771: 966 */       localObject2 = localHashMap.keySet().iterator();
/*  772: 968 */       while (((Iterator)localObject2).hasNext())
/*  773:     */       {
/*  774: 969 */         localObject3 = (String)((Iterator)localObject2).next();
/*  775: 970 */         ArrayList localArrayList = (ArrayList)localHashMap.get(localObject3);
/*  776:     */         
/*  777:     */ 
/*  778: 973 */         StringBuffer localStringBuffer1 = new StringBuffer();
/*  779: 974 */         int i1 = 1;
/*  780:     */         Object localObject4;
/*  781: 975 */         for (int i2 = 0; i2 < localArrayList.size(); i2++)
/*  782:     */         {
/*  783: 976 */           localObject4 = (DisbursementAccount)localArrayList.get(i2);
/*  784: 977 */           if ((((DisbursementAccount)localObject4).getAccountID() != null) && (((DisbursementAccount)localObject4).getAccountID().length() != 0))
/*  785:     */           {
/*  786: 978 */             if (i1 == 0)
/*  787:     */             {
/*  788: 979 */               localStringBuffer1.append(", ");
/*  789:     */             }
/*  790:     */             else
/*  791:     */             {
/*  792: 981 */               i1 = 0;
/*  793: 982 */               localStringBuffer1.append(" ( ");
/*  794:     */             }
/*  795: 984 */             localStringBuffer1.append("'" + ((DisbursementAccount)localObject4).getAccountID() + "'");
/*  796:     */           }
/*  797:     */         }
/*  798: 988 */         if (localStringBuffer1.length() != 0)
/*  799:     */         {
/*  800: 989 */           localStringBuffer1.append(" ) ");
/*  801:     */           
/*  802:     */ 
/*  803:     */ 
/*  804:     */ 
/*  805: 994 */           StringBuffer localStringBuffer2 = new StringBuffer(jdField_new);
/*  806: 996 */           if (paramCalendar1 != null)
/*  807:     */           {
/*  808: 997 */             if (paramCalendar2 != null)
/*  809:     */             {
/*  810: 998 */               localStringBuffer2.append(l);
/*  811: 999 */               if (localStringBuffer1.length() != 0)
/*  812:     */               {
/*  813:1000 */                 localStringBuffer2.append(a);
/*  814:1001 */                 localStringBuffer2.append(localStringBuffer1);
/*  815:     */               }
/*  816:1003 */               localStringBuffer2.append(i);
/*  817:1004 */               localStringBuffer2.append(l);
/*  818:1005 */               if (localStringBuffer1.length() != 0)
/*  819:     */               {
/*  820:1006 */                 localStringBuffer2.append(a);
/*  821:1007 */                 localStringBuffer2.append(localStringBuffer1);
/*  822:     */               }
/*  823:1009 */               localStringBuffer2.append(jdField_char);
/*  824:1010 */               localStringBuffer2.append(l);
/*  825:1011 */               if (localStringBuffer1.length() != 0)
/*  826:     */               {
/*  827:1012 */                 localStringBuffer2.append(a);
/*  828:1013 */                 localStringBuffer2.append(localStringBuffer1);
/*  829:     */               }
/*  830:1015 */               localStringBuffer2.append(jdField_case);
/*  831:1016 */               localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  832:1017 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 1, paramCalendar1);
/*  833:1018 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 2, paramCalendar2);
/*  834:1019 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 3, paramCalendar1);
/*  835:1020 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 4, paramCalendar2);
/*  836:1021 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 5, paramCalendar1);
/*  837:1022 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 6, paramCalendar2);
/*  838:1023 */               localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  839:     */             }
/*  840:     */             else
/*  841:     */             {
/*  842:1025 */               localStringBuffer2.append(jdField_else);
/*  843:1026 */               if (localStringBuffer1.length() != 0)
/*  844:     */               {
/*  845:1027 */                 localStringBuffer2.append(a);
/*  846:1028 */                 localStringBuffer2.append(localStringBuffer1);
/*  847:     */               }
/*  848:1030 */               localStringBuffer2.append(i);
/*  849:1031 */               localStringBuffer2.append(jdField_else);
/*  850:1032 */               if (localStringBuffer1.length() != 0)
/*  851:     */               {
/*  852:1033 */                 localStringBuffer2.append(a);
/*  853:1034 */                 localStringBuffer2.append(localStringBuffer1);
/*  854:     */               }
/*  855:1036 */               localStringBuffer2.append(jdField_char);
/*  856:1037 */               localStringBuffer2.append(jdField_else);
/*  857:1038 */               if (localStringBuffer1.length() != 0)
/*  858:     */               {
/*  859:1039 */                 localStringBuffer2.append(a);
/*  860:1040 */                 localStringBuffer2.append(localStringBuffer1);
/*  861:     */               }
/*  862:1042 */               localStringBuffer2.append(jdField_case);
/*  863:     */               
/*  864:1044 */               localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  865:1045 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 1, paramCalendar1);
/*  866:1046 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 2, paramCalendar1);
/*  867:1047 */               DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 3, paramCalendar1);
/*  868:1048 */               localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  869:     */             }
/*  870:     */           }
/*  871:1050 */           else if (paramCalendar2 != null)
/*  872:     */           {
/*  873:1051 */             localStringBuffer2.append(j);
/*  874:1052 */             if (localStringBuffer1.length() != 0)
/*  875:     */             {
/*  876:1053 */               localStringBuffer2.append(a);
/*  877:1054 */               localStringBuffer2.append(localStringBuffer1);
/*  878:     */             }
/*  879:1056 */             localStringBuffer2.append(i);
/*  880:1057 */             localStringBuffer2.append(j);
/*  881:1058 */             if (localStringBuffer1.length() != 0)
/*  882:     */             {
/*  883:1059 */               localStringBuffer2.append(a);
/*  884:1060 */               localStringBuffer2.append(localStringBuffer1);
/*  885:     */             }
/*  886:1062 */             localStringBuffer2.append(jdField_char);
/*  887:1063 */             localStringBuffer2.append(j);
/*  888:1064 */             if (localStringBuffer1.length() != 0)
/*  889:     */             {
/*  890:1065 */               localStringBuffer2.append(a);
/*  891:1066 */               localStringBuffer2.append(localStringBuffer1);
/*  892:     */             }
/*  893:1068 */             localStringBuffer2.append(jdField_case);
/*  894:     */             
/*  895:1070 */             localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  896:1071 */             DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 1, paramCalendar2);
/*  897:1072 */             DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 2, paramCalendar2);
/*  898:1073 */             DCUtil.fillTimestampColumn((PreparedStatement)localObject1, 3, paramCalendar2);
/*  899:1074 */             localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  900:     */           }
/*  901:     */           else
/*  902:     */           {
/*  903:1076 */             if (localStringBuffer1.length() != 0)
/*  904:     */             {
/*  905:1077 */               localStringBuffer2.append(a);
/*  906:1078 */               localStringBuffer2.append(localStringBuffer1);
/*  907:1079 */               localStringBuffer2.append(i);
/*  908:1080 */               localStringBuffer2.append(a);
/*  909:1081 */               localStringBuffer2.append(localStringBuffer1);
/*  910:1082 */               localStringBuffer2.append(jdField_char);
/*  911:1083 */               localStringBuffer2.append(a);
/*  912:1084 */               localStringBuffer2.append(localStringBuffer1);
/*  913:     */             }
/*  914:     */             else
/*  915:     */             {
/*  916:1086 */               localStringBuffer2.append(i);
/*  917:1087 */               localStringBuffer2.append(jdField_char);
/*  918:     */             }
/*  919:1089 */             localStringBuffer2.append(jdField_case);
/*  920:     */             
/*  921:1091 */             localObject1 = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer2.toString());
/*  922:1092 */             localResultSet = DBConnection.executeQuery((PreparedStatement)localObject1, localStringBuffer2.toString());
/*  923:     */           }
/*  924:1095 */           localObject4 = null;
/*  925:1096 */           while (localResultSet.next())
/*  926:     */           {
/*  927:1097 */             localObject4 = a(localResultSet, (String)localObject3);
/*  928:     */             
/*  929:     */ 
/*  930:1100 */             Iterator localIterator = localDisbursementPresentmentSummaries.iterator();
/*  931:1101 */             int i3 = 0;
/*  932:     */             DisbursementPresentmentSummary localDisbursementPresentmentSummary;
/*  933:1102 */             while (localIterator.hasNext())
/*  934:     */             {
/*  935:1103 */               localDisbursementPresentmentSummary = (DisbursementPresentmentSummary)localIterator.next();
/*  936:1105 */               if (localDisbursementPresentmentSummary.getPresentment().equals(((DisbursementPresentmentSummary)localObject4).getPresentment()))
/*  937:     */               {
/*  938:1106 */                 localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject4);
/*  939:1107 */                 i3 = 1;
/*  940:1108 */                 break;
/*  941:     */               }
/*  942:     */             }
/*  943:1111 */             if (i3 == 0)
/*  944:     */             {
/*  945:1113 */               localDisbursementPresentmentSummary = new DisbursementPresentmentSummary((SecureUser)paramHashMap.get("SECURE_USER"));
/*  946:     */               
/*  947:1115 */               localDisbursementPresentmentSummary.addSubSummary((DisbursementPresentmentSummary)localObject4);
/*  948:     */               
/*  949:1117 */               localDisbursementPresentmentSummaries.add(localDisbursementPresentmentSummary);
/*  950:     */             }
/*  951:     */           }
/*  952:1120 */           DBUtil.closeResultSet(localResultSet);
/*  953:     */         }
/*  954:     */       }
/*  955:1122 */       return localDisbursementPresentmentSummaries;
/*  956:     */     }
/*  957:     */     catch (Exception localException)
/*  958:     */     {
/*  959:1125 */       throw new BSException(1, localException.getMessage());
/*  960:     */     }
/*  961:     */     finally
/*  962:     */     {
/*  963:1127 */       if (localObject1 != null) {
/*  964:1128 */         DBConnection.closeStatement((Statement)localObject1);
/*  965:     */       }
/*  966:1131 */       if (paramDBConnection != null) {
/*  967:1132 */         paramDBConnection.close();
/*  968:     */       }
/*  969:     */     }
/*  970:     */   }
/*  971:     */   
/*  972:     */   private static DisbursementSummary a(DisbursementAccount paramDisbursementAccount, ResultSet paramResultSet)
/*  973:     */     throws Exception
/*  974:     */   {
/*  975:1141 */     DisbursementSummary localDisbursementSummary = new DisbursementSummary();
/*  976:     */     
/*  977:1143 */     localDisbursementSummary.setAccount(paramDisbursementAccount);
/*  978:1144 */     localDisbursementSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1)));
/*  979:1145 */     localDisbursementSummary.setNumItemsPending(paramResultSet.getInt(2));
/*  980:1146 */     localDisbursementSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramDisbursementAccount.getCurrencyType()));
/*  981:1147 */     localDisbursementSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramDisbursementAccount.getCurrencyType()));
/*  982:1148 */     localDisbursementSummary.setTotalDTCCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramDisbursementAccount.getCurrencyType()));
/*  983:1149 */     localDisbursementSummary.setImmediateFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramDisbursementAccount.getCurrencyType()));
/*  984:1150 */     localDisbursementSummary.setOneDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramDisbursementAccount.getCurrencyType()));
/*  985:1151 */     localDisbursementSummary.setTwoDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramDisbursementAccount.getCurrencyType()));
/*  986:1152 */     localDisbursementSummary.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(9)));
/*  987:1153 */     localDisbursementSummary.setTotalChecksPaidEarlyAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramDisbursementAccount.getCurrencyType()));
/*  988:1154 */     localDisbursementSummary.setTotalChecksPaidLateAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramDisbursementAccount.getCurrencyType()));
/*  989:1155 */     localDisbursementSummary.setTotalChecksPaidLastAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramDisbursementAccount.getCurrencyType()));
/*  990:1156 */     localDisbursementSummary.setFedPresentmentEstimate(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramDisbursementAccount.getCurrencyType()));
/*  991:1157 */     localDisbursementSummary.setLateDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramDisbursementAccount.getCurrencyType()));
/*  992:1158 */     DCUtil.fillExtendABean(localDisbursementSummary, paramResultSet, 15);
/*  993:     */     
/*  994:1160 */     return localDisbursementSummary;
/*  995:     */   }
/*  996:     */   
/*  997:     */   private static DisbursementSummary a(DisbursementAccount paramDisbursementAccount, String paramString, ResultSet paramResultSet)
/*  998:     */     throws Exception
/*  999:     */   {
/* 1000:1167 */     DisbursementSummary localDisbursementSummary = new DisbursementSummary();
/* 1001:     */     
/* 1002:1169 */     localDisbursementSummary.setAccount(paramDisbursementAccount);
/* 1003:1170 */     localDisbursementSummary.setPresentment(paramString);
/* 1004:1171 */     int n = paramResultSet.getInt(1);
/* 1005:1172 */     int i1 = paramResultSet.getInt(2);
/* 1006:1173 */     int i2 = paramResultSet.getInt(3);
/* 1007:1174 */     localDisbursementSummary.setNumItemsPending(n + i1 + i2);
/* 1008:1175 */     localDisbursementSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramDisbursementAccount.getCurrencyType()));
/* 1009:1176 */     localDisbursementSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramDisbursementAccount.getCurrencyType()));
/* 1010:     */     
/* 1011:1178 */     return localDisbursementSummary;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   private static DisbursementPresentmentSummary a(ResultSet paramResultSet, String paramString)
/* 1015:     */     throws Exception
/* 1016:     */   {
/* 1017:1184 */     DisbursementPresentmentSummary localDisbursementPresentmentSummary = new DisbursementPresentmentSummary();
/* 1018:1185 */     for (int n = 1; n <= 3; n++)
/* 1019:     */     {
/* 1020:1186 */       String str = paramResultSet.getString(n);
/* 1021:1187 */       if (str != null)
/* 1022:     */       {
/* 1023:1188 */         localDisbursementPresentmentSummary.setPresentment(str);
/* 1024:1189 */         break;
/* 1025:     */       }
/* 1026:     */     }
/* 1027:1192 */     n = paramResultSet.getInt(4);
/* 1028:1193 */     int i1 = paramResultSet.getInt(5);
/* 1029:1194 */     int i2 = paramResultSet.getInt(6);
/* 1030:1195 */     localDisbursementPresentmentSummary.setNumItemsPending(n + i1 + i2);
/* 1031:1196 */     localDisbursementPresentmentSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramString));
/* 1032:1197 */     localDisbursementPresentmentSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramString));
/* 1033:1198 */     return localDisbursementPresentmentSummary;
/* 1034:     */   }
/* 1035:     */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSDsbSummary
 * JD-Core Version:    0.7.0.1
 */