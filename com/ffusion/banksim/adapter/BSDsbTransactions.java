/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.db.MessageText;
/*   5:    */ import com.ffusion.banksim.interfaces.BSConstants;
/*   6:    */ import com.ffusion.banksim.interfaces.BSException;
/*   7:    */ import com.ffusion.beans.disbursement.DisbursementAccount;
/*   8:    */ import com.ffusion.beans.disbursement.DisbursementTransaction;
/*   9:    */ import com.ffusion.beans.disbursement.DisbursementTransactions;
/*  10:    */ import com.ffusion.dataconsolidator.adapter.DCUtil;
/*  11:    */ import com.ffusion.util.MapUtil;
/*  12:    */ import com.ffusion.util.db.DBUtil;
/*  13:    */ import java.sql.PreparedStatement;
/*  14:    */ import java.sql.ResultSet;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.HashMap;
/*  17:    */ 
/*  18:    */ public class BSDsbTransactions
/*  19:    */ {
/*  20:    */   private static final String jdField_new = "INSERT INTO BS_DsbTransactions( AccountID, TransactionIndex, DataDate, DataSource, TransID, CheckDate, Payee, Amount, CheckNumber, CheckRefNum, Memo, IssuedBy, ApprovedBy, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, Presentment, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
/*  21:    */   private static final String n = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? ORDER BY b.TransactionIndex";
/*  22:    */   private static final String l = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? ORDER BY b.TransactionIndex";
/*  23:    */   private static final String j = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? ORDER BY b.TransactionIndex";
/*  24:    */   private static final String h = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.TransactionIndex";
/*  25:    */   private static final String jdField_char = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  26:    */   private static final String jdField_case = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  27:    */   private static final String jdField_byte = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  28:    */   private static final String jdField_try = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  29:    */   private static final String d = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ?";
/*  30:    */   private static final String b = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ?";
/*  31:    */   private static final String jdField_null = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ?";
/*  32:    */   private static final String jdField_goto = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=?";
/*  33:    */   private static final String m = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate > ? AND b.DataDate < ?";
/*  34:    */   private static final String k = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate > ?";
/*  35:    */   private static final String i = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate < ?";
/*  36:    */   private static final String g = "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? ";
/*  37:    */   private static final String e = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  38:    */   private static final String a = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  39:    */   private static final String f = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  40:    */   private static final String jdField_int = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex";
/*  41:    */   private static final String jdField_for = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex";
/*  42:    */   private static final String jdField_do = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex";
/*  43:    */   private static final String jdField_if = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex";
/*  44:    */   private static final String c = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  45:    */   private static final String jdField_void = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  46:    */   private static final String jdField_long = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  47:    */   private static final String jdField_else = "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  48:    */   
/*  49:    */   public static void addTransactions(DisbursementAccount paramDisbursementAccount, DisbursementTransactions paramDisbursementTransactions, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/*  50:    */     throws BSException
/*  51:    */   {
/*  52:184 */     PreparedStatement localPreparedStatement = null;
/*  53:185 */     DisbursementTransaction localDisbursementTransaction = null;
/*  54:186 */     long l1 = 0L;
/*  55:    */     try
/*  56:    */     {
/*  57:189 */       String str1 = null;
/*  58:190 */       if (paramHashMap != null) {
/*  59:191 */         str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
/*  60:    */       }
/*  61:195 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_DsbTransactions( AccountID, TransactionIndex, DataDate, DataSource, TransID, CheckDate, Payee, Amount, CheckNumber, CheckRefNum, Memo, IssuedBy, ApprovedBy, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, Presentment, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
/*  62:196 */       String str2 = paramDisbursementAccount.getAccountID();
/*  63:197 */       for (int i1 = 0; i1 < paramDisbursementTransactions.size(); i1++)
/*  64:    */       {
/*  65:198 */         localDisbursementTransaction = (DisbursementTransaction)paramDisbursementTransactions.get(i1);
/*  66:200 */         if (localDisbursementTransaction.getTransactionIndex() != 0L) {
/*  67:201 */           throw new BSException(3001, MessageText.getMessage("ERR_INVALID_TRANSACTION_INDEX"));
/*  68:    */         }
/*  69:204 */         l1 = BSRecordCounter.getNextIndex(paramDBConnection, 1, str2, "DISBURSEMENT_TRANSACTION_INDEX");
/*  70:    */         
/*  71:    */ 
/*  72:    */ 
/*  73:208 */         long l2 = BSExtendABeanXML.addExtendABeanXML(paramDBConnection, localDisbursementTransaction.getExtendABeanXML());
/*  74:    */         
/*  75:210 */         localPreparedStatement.setString(1, str2);
/*  76:211 */         localPreparedStatement.setLong(2, l1);
/*  77:212 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, localDisbursementTransaction.getProcessingDate());
/*  78:213 */         localPreparedStatement.setInt(4, paramInt);
/*  79:214 */         localPreparedStatement.setInt(5, localDisbursementTransaction.getTransID());
/*  80:215 */         DCUtil.fillTimestampColumn(localPreparedStatement, 6, localDisbursementTransaction.getCheckDate());
/*  81:216 */         localPreparedStatement.setString(7, localDisbursementTransaction.getPayee());
/*  82:217 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localDisbursementTransaction.getCheckAmount());
/*  83:218 */         localPreparedStatement.setString(9, localDisbursementTransaction.getCheckNumber());
/*  84:219 */         localPreparedStatement.setString(10, localDisbursementTransaction.getCheckReferenceNumber());
/*  85:220 */         localPreparedStatement.setString(11, localDisbursementTransaction.getMemo());
/*  86:221 */         localPreparedStatement.setString(12, localDisbursementTransaction.getIssuedBy());
/*  87:222 */         localPreparedStatement.setString(13, localDisbursementTransaction.getApprovedBy());
/*  88:223 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localDisbursementTransaction.getImmediateFundsNeeded());
/*  89:224 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 15, localDisbursementTransaction.getOneDayFundsNeeded());
/*  90:225 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 16, localDisbursementTransaction.getTwoDayFundsNeeded());
/*  91:226 */         DCUtil.fillTimestampColumn(localPreparedStatement, 17, localDisbursementTransaction.getValueDateTime());
/*  92:227 */         localPreparedStatement.setString(18, localDisbursementTransaction.getBankReferenceNumber());
/*  93:228 */         localPreparedStatement.setString(19, localDisbursementTransaction.getCustomerReferenceNumber());
/*  94:229 */         localPreparedStatement.setLong(20, l2);
/*  95:230 */         localPreparedStatement.setString(21, null);
/*  96:231 */         localPreparedStatement.setString(22, localDisbursementTransaction.getPresentment());
/*  97:232 */         localPreparedStatement.setString(23, str1);
/*  98:    */         
/*  99:234 */         DBConnection.executeUpdate(localPreparedStatement, "INSERT INTO BS_DsbTransactions( AccountID, TransactionIndex, DataDate, DataSource, TransID, CheckDate, Payee, Amount, CheckNumber, CheckRefNum, Memo, IssuedBy, ApprovedBy, ImmedFundsNeeded, OneDayFundsNeeded, TwoDayFundsNeeded, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, Presentment, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
/* 100:    */       }
/* 101:    */     }
/* 102:    */     catch (Exception localException)
/* 103:    */     {
/* 104:237 */       throw new BSException(1, localException.getMessage());
/* 105:    */     }
/* 106:    */     finally
/* 107:    */     {
/* 108:239 */       if (localPreparedStatement != null) {
/* 109:240 */         DBConnection.closeStatement(localPreparedStatement);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static void a(DisbursementAccount paramDisbursementAccount, DisbursementTransaction paramDisbursementTransaction, ResultSet paramResultSet)
/* 115:    */     throws Exception
/* 116:    */   {
/* 117:255 */     paramDisbursementTransaction.setAccountID(paramDisbursementAccount.getAccountID());
/* 118:256 */     paramDisbursementTransaction.setAccountNumber(paramDisbursementAccount.getAccountNumber());
/* 119:257 */     paramDisbursementTransaction.setBankID(paramDisbursementAccount.getBankID());
/* 120:258 */     paramDisbursementTransaction.setTransactionIndex(paramResultSet.getLong(1));
/* 121:259 */     paramDisbursementTransaction.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(2)));
/* 122:260 */     paramDisbursementTransaction.setTransID(paramResultSet.getInt(3));
/* 123:261 */     paramDisbursementTransaction.setCheckDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(4)));
/* 124:262 */     paramDisbursementTransaction.setPayee(paramResultSet.getString(5));
/* 125:263 */     paramDisbursementTransaction.setCheckAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramDisbursementAccount.getCurrencyType()));
/* 126:264 */     paramDisbursementTransaction.setCheckNumber(paramResultSet.getString(7));
/* 127:265 */     paramDisbursementTransaction.setCheckReferenceNumber(paramResultSet.getString(8));
/* 128:266 */     paramDisbursementTransaction.setMemo(paramResultSet.getString(9));
/* 129:267 */     paramDisbursementTransaction.setIssuedBy(paramResultSet.getString(10));
/* 130:268 */     paramDisbursementTransaction.setApprovedBy(paramResultSet.getString(11));
/* 131:269 */     paramDisbursementTransaction.setImmediateFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramDisbursementAccount.getCurrencyType()));
/* 132:270 */     paramDisbursementTransaction.setOneDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramDisbursementAccount.getCurrencyType()));
/* 133:271 */     paramDisbursementTransaction.setTwoDayFundsNeeded(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramDisbursementAccount.getCurrencyType()));
/* 134:272 */     paramDisbursementTransaction.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(15)));
/* 135:273 */     paramDisbursementTransaction.setBankReferenceNumber(paramResultSet.getString(16));
/* 136:274 */     paramDisbursementTransaction.setCustomerReferenceNumber(paramResultSet.getString(17));
/* 137:275 */     DCUtil.fillExtendABean(paramDisbursementTransaction, paramResultSet, 18);
/* 138:276 */     paramDisbursementTransaction.setPresentment(paramResultSet.getString(19));
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 142:    */     throws BSException
/* 143:    */   {
/* 144:294 */     return getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramDBConnection, paramHashMap);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, DBConnection paramDBConnection, HashMap paramHashMap)
/* 148:    */     throws BSException
/* 149:    */   {
/* 150:315 */     PreparedStatement localPreparedStatement = null;
/* 151:316 */     DisbursementTransactions localDisbursementTransactions1 = null;
/* 152:317 */     ResultSet localResultSet = null;
/* 153:    */     try
/* 154:    */     {
/* 155:319 */       if (paramString == null)
/* 156:    */       {
/* 157:320 */         if (paramCalendar1 != null)
/* 158:    */         {
/* 159:321 */           if (paramCalendar2 != null)
/* 160:    */           {
/* 161:322 */             localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? ORDER BY b.TransactionIndex");
/* 162:323 */             localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 163:324 */             localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 164:325 */             DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 165:326 */             DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 166:327 */             localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? ORDER BY b.TransactionIndex");
/* 167:    */           }
/* 168:    */           else
/* 169:    */           {
/* 170:329 */             localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? ORDER BY b.TransactionIndex");
/* 171:330 */             localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 172:331 */             localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 173:332 */             DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 174:333 */             localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? ORDER BY b.TransactionIndex");
/* 175:    */           }
/* 176:    */         }
/* 177:335 */         else if (paramCalendar2 != null)
/* 178:    */         {
/* 179:336 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? ORDER BY b.TransactionIndex");
/* 180:337 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 181:338 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 182:339 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 183:340 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? ORDER BY b.TransactionIndex");
/* 184:    */         }
/* 185:    */         else
/* 186:    */         {
/* 187:342 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.TransactionIndex");
/* 188:343 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 189:344 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 190:345 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.TransactionIndex");
/* 191:    */         }
/* 192:    */       }
/* 193:350 */       else if (paramCalendar1 != null)
/* 194:    */       {
/* 195:351 */         if (paramCalendar2 != null)
/* 196:    */         {
/* 197:352 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 198:353 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 199:354 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 200:355 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 201:356 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 202:357 */           localPreparedStatement.setString(5, paramString);
/* 203:358 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 204:    */         }
/* 205:    */         else
/* 206:    */         {
/* 207:360 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 208:361 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 209:362 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 210:363 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 211:364 */           localPreparedStatement.setString(4, paramString);
/* 212:365 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 213:    */         }
/* 214:    */       }
/* 215:367 */       else if (paramCalendar2 != null)
/* 216:    */       {
/* 217:368 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 218:369 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 219:370 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 220:371 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 221:372 */         localPreparedStatement.setString(4, paramString);
/* 222:373 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 223:    */       }
/* 224:    */       else
/* 225:    */       {
/* 226:375 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 227:376 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 228:377 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 229:378 */         localPreparedStatement.setString(3, paramString);
/* 230:379 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.PRESENTMENT = ? ORDER BY b.TransactionIndex");
/* 231:    */       }
/* 232:383 */       localDisbursementTransactions1 = new DisbursementTransactions();
/* 233:384 */       DisbursementTransaction localDisbursementTransaction = null;
/* 234:385 */       while (localResultSet.next())
/* 235:    */       {
/* 236:386 */         localDisbursementTransaction = localDisbursementTransactions1.create();
/* 237:387 */         a(paramDisbursementAccount, localDisbursementTransaction, localResultSet);
/* 238:    */       }
/* 239:389 */       DBUtil.closeResultSet(localResultSet);
/* 240:390 */       return localDisbursementTransactions1;
/* 241:    */     }
/* 242:    */     catch (Exception localException)
/* 243:    */     {
/* 244:393 */       throw new BSException(1, localException.getMessage());
/* 245:    */     }
/* 246:    */     finally
/* 247:    */     {
/* 248:395 */       if (localPreparedStatement != null) {
/* 249:396 */         DBConnection.closeStatement(localPreparedStatement);
/* 250:    */       }
/* 251:399 */       if (paramDBConnection != null) {
/* 252:400 */         paramDBConnection.close();
/* 253:    */       }
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public static DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 258:    */     throws BSException
/* 259:    */   {
/* 260:421 */     return getPagedTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramDBConnection, paramHashMap);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, DBConnection paramDBConnection, HashMap paramHashMap)
/* 264:    */     throws BSException
/* 265:    */   {
/* 266:442 */     PreparedStatement localPreparedStatement = null;
/* 267:443 */     DisbursementTransactions localDisbursementTransactions1 = null;
/* 268:    */     
/* 269:445 */     ResultSet localResultSet = null;
/* 270:    */     try
/* 271:    */     {
/* 272:447 */       int i1 = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 273:448 */       long[] arrayOfLong = a(paramDBConnection, paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2);
/* 274:449 */       long l1 = arrayOfLong[0];
/* 275:450 */       long l2 = arrayOfLong[1];
/* 276:452 */       if (paramString == null)
/* 277:    */       {
/* 278:453 */         if (paramCalendar1 != null)
/* 279:    */         {
/* 280:454 */           if (paramCalendar2 != null)
/* 281:    */           {
/* 282:455 */             localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 283:456 */             localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 284:457 */             localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 285:458 */             DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 286:459 */             DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 287:460 */             localPreparedStatement.setLong(5, l1);
/* 288:461 */             localPreparedStatement.setLong(6, l1 + i1 - 1L);
/* 289:462 */             localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 290:    */           }
/* 291:    */           else
/* 292:    */           {
/* 293:464 */             localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 294:465 */             localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 295:466 */             localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 296:467 */             DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 297:468 */             localPreparedStatement.setLong(4, l1);
/* 298:469 */             localPreparedStatement.setLong(5, l1 + i1 - 1L);
/* 299:470 */             localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 300:    */           }
/* 301:    */         }
/* 302:472 */         else if (paramCalendar2 != null)
/* 303:    */         {
/* 304:473 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 305:474 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 306:475 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 307:476 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 308:477 */           localPreparedStatement.setLong(4, l1);
/* 309:478 */           localPreparedStatement.setLong(5, l1 + i1 - 1L);
/* 310:479 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 311:    */         }
/* 312:    */         else
/* 313:    */         {
/* 314:481 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 315:482 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 316:483 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 317:484 */           localPreparedStatement.setLong(3, l1);
/* 318:485 */           localPreparedStatement.setLong(4, l1 + i1 - 1L);
/* 319:486 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 320:    */         }
/* 321:    */       }
/* 322:491 */       else if (paramCalendar1 != null)
/* 323:    */       {
/* 324:492 */         if (paramCalendar2 != null)
/* 325:    */         {
/* 326:493 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 327:494 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 328:495 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 329:496 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 330:497 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 331:498 */           localPreparedStatement.setString(5, paramString);
/* 332:499 */           localPreparedStatement.setLong(6, l1);
/* 333:500 */           localPreparedStatement.setLong(7, l1 + i1 - 1L);
/* 334:501 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 335:    */         }
/* 336:    */         else
/* 337:    */         {
/* 338:503 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 339:504 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 340:505 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 341:506 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 342:507 */           localPreparedStatement.setString(4, paramString);
/* 343:508 */           localPreparedStatement.setLong(5, l1);
/* 344:509 */           localPreparedStatement.setLong(6, l1 + i1 - 1L);
/* 345:510 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 346:    */         }
/* 347:    */       }
/* 348:512 */       else if (paramCalendar2 != null)
/* 349:    */       {
/* 350:513 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 351:514 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 352:515 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 353:516 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 354:517 */         localPreparedStatement.setString(4, paramString);
/* 355:518 */         localPreparedStatement.setLong(5, l1);
/* 356:519 */         localPreparedStatement.setLong(6, l1 + i1 - 1L);
/* 357:520 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 358:    */       }
/* 359:    */       else
/* 360:    */       {
/* 361:522 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 362:523 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 363:524 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 364:525 */         localPreparedStatement.setString(3, paramString);
/* 365:526 */         localPreparedStatement.setLong(4, l1);
/* 366:527 */         localPreparedStatement.setLong(5, l1 + i1 - 1L);
/* 367:528 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.Presentment = ? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 368:    */       }
/* 369:532 */       localDisbursementTransactions1 = new DisbursementTransactions();
/* 370:533 */       DisbursementTransaction localDisbursementTransaction = null;
/* 371:534 */       while (localResultSet.next())
/* 372:    */       {
/* 373:535 */         localDisbursementTransaction = localDisbursementTransactions1.create();
/* 374:536 */         a(paramDisbursementAccount, localDisbursementTransaction, localResultSet);
/* 375:    */       }
/* 376:538 */       DBUtil.closeResultSet(localResultSet);
/* 377:    */       
/* 378:540 */       paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l1));
/* 379:541 */       paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l2));
/* 380:    */       
/* 381:543 */       return localDisbursementTransactions1;
/* 382:    */     }
/* 383:    */     catch (Exception localException)
/* 384:    */     {
/* 385:546 */       throw new BSException(1, localException.getMessage());
/* 386:    */     }
/* 387:    */     finally
/* 388:    */     {
/* 389:548 */       if (localPreparedStatement != null) {
/* 390:549 */         DBConnection.closeStatement(localPreparedStatement);
/* 391:    */       }
/* 392:551 */       if (paramDBConnection != null) {
/* 393:552 */         paramDBConnection.close();
/* 394:    */       }
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   public static DisbursementTransactions getRecentTransactions(DisbursementAccount paramDisbursementAccount, DBConnection paramDBConnection, HashMap paramHashMap)
/* 399:    */     throws BSException
/* 400:    */   {
/* 401:569 */     PreparedStatement localPreparedStatement = null;
/* 402:570 */     DisbursementTransactions localDisbursementTransactions = new DisbursementTransactions();
/* 403:    */     try
/* 404:    */     {
/* 405:573 */       int i1 = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 406:    */       
/* 407:575 */       long l1 = BSRecordCounter.getIndex(paramDBConnection, paramDisbursementAccount.getAccountID(), paramDisbursementAccount.getRoutingNumber(), 1, "DISBURSEMENT_TRANSACTION_INDEX");
/* 408:    */       Object localObject1;
/* 409:579 */       if (l1 != -1L)
/* 410:    */       {
/* 411:580 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 412:581 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 413:582 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 414:583 */         localPreparedStatement.setLong(3, l1 - i1 + 1L);
/* 415:584 */         localPreparedStatement.setLong(4, l1);
/* 416:585 */         localObject1 = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 417:    */         
/* 418:587 */         DisbursementTransaction localDisbursementTransaction = null;
/* 419:588 */         while (((ResultSet)localObject1).next())
/* 420:    */         {
/* 421:589 */           localDisbursementTransaction = localDisbursementTransactions.create();
/* 422:590 */           a(paramDisbursementAccount, localDisbursementTransaction, (ResultSet)localObject1);
/* 423:    */         }
/* 424:592 */         DBUtil.closeResultSet((ResultSet)localObject1);
/* 425:    */       }
/* 426:594 */       return localDisbursementTransactions;
/* 427:    */     }
/* 428:    */     catch (Exception localException)
/* 429:    */     {
/* 430:597 */       throw new BSException(1, localException.getMessage());
/* 431:    */     }
/* 432:    */     finally
/* 433:    */     {
/* 434:599 */       if (localPreparedStatement != null) {
/* 435:600 */         DBConnection.closeStatement(localPreparedStatement);
/* 436:    */       }
/* 437:603 */       if (paramDBConnection != null) {
/* 438:604 */         paramDBConnection.close();
/* 439:    */       }
/* 440:    */     }
/* 441:    */   }
/* 442:    */   
/* 443:    */   public static DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, DBConnection paramDBConnection, HashMap paramHashMap)
/* 444:    */     throws BSException
/* 445:    */   {
/* 446:623 */     PreparedStatement localPreparedStatement = null;
/* 447:624 */     DisbursementTransactions localDisbursementTransactions1 = null;
/* 448:    */     try
/* 449:    */     {
/* 450:627 */       int i1 = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 451:628 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 452:629 */       localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 453:630 */       localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 454:631 */       localPreparedStatement.setLong(3, paramLong);
/* 455:632 */       localPreparedStatement.setLong(4, paramLong + i1 - 1L);
/* 456:633 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 457:    */       
/* 458:635 */       localDisbursementTransactions1 = new DisbursementTransactions();
/* 459:636 */       DisbursementTransaction localDisbursementTransaction = null;
/* 460:637 */       while (localResultSet.next())
/* 461:    */       {
/* 462:638 */         localDisbursementTransaction = localDisbursementTransactions1.create();
/* 463:639 */         a(paramDisbursementAccount, localDisbursementTransaction, localResultSet);
/* 464:    */       }
/* 465:641 */       DBUtil.closeResultSet(localResultSet);
/* 466:642 */       return localDisbursementTransactions1;
/* 467:    */     }
/* 468:    */     catch (Exception localException)
/* 469:    */     {
/* 470:645 */       throw new BSException(1, localException.getMessage());
/* 471:    */     }
/* 472:    */     finally
/* 473:    */     {
/* 474:647 */       if (localPreparedStatement != null) {
/* 475:648 */         DBConnection.closeStatement(localPreparedStatement);
/* 476:    */       }
/* 477:651 */       if (paramDBConnection != null) {
/* 478:652 */         paramDBConnection.close();
/* 479:    */       }
/* 480:    */     }
/* 481:    */   }
/* 482:    */   
/* 483:    */   public static DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, DBConnection paramDBConnection, HashMap paramHashMap)
/* 484:    */     throws BSException
/* 485:    */   {
/* 486:671 */     PreparedStatement localPreparedStatement = null;
/* 487:672 */     DisbursementTransactions localDisbursementTransactions1 = null;
/* 488:    */     try
/* 489:    */     {
/* 490:675 */       int i1 = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 491:676 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 492:677 */       localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 493:678 */       localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 494:679 */       localPreparedStatement.setLong(3, paramLong - i1 + 1L);
/* 495:680 */       localPreparedStatement.setLong(4, paramLong);
/* 496:681 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.DataDate, b.TransID, b.CheckDate, b.Payee, b.Amount, b.CheckNumber, b.CheckRefNum, b.Memo, b.IssuedBy, b.ApprovedBy, b.ImmedFundsNeeded, b.OneDayFundsNeeded, b.TwoDayFundsNeeded, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID, b.Presentment FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 497:    */       
/* 498:683 */       localDisbursementTransactions1 = new DisbursementTransactions();
/* 499:684 */       DisbursementTransaction localDisbursementTransaction = null;
/* 500:685 */       while (localResultSet.next())
/* 501:    */       {
/* 502:686 */         localDisbursementTransaction = localDisbursementTransactions1.create();
/* 503:687 */         a(paramDisbursementAccount, localDisbursementTransaction, localResultSet);
/* 504:    */       }
/* 505:689 */       DBUtil.closeResultSet(localResultSet);
/* 506:690 */       return localDisbursementTransactions1;
/* 507:    */     }
/* 508:    */     catch (Exception localException)
/* 509:    */     {
/* 510:693 */       throw new BSException(1, localException.getMessage());
/* 511:    */     }
/* 512:    */     finally
/* 513:    */     {
/* 514:695 */       if (localPreparedStatement != null) {
/* 515:696 */         DBConnection.closeStatement(localPreparedStatement);
/* 516:    */       }
/* 517:699 */       if (paramDBConnection != null) {
/* 518:700 */         paramDBConnection.close();
/* 519:    */       }
/* 520:    */     }
/* 521:    */   }
/* 522:    */   
/* 523:    */   private static long[] a(DBConnection paramDBConnection, DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2)
/* 524:    */     throws Exception
/* 525:    */   {
/* 526:709 */     ResultSet localResultSet = null;
/* 527:710 */     PreparedStatement localPreparedStatement = null;
/* 528:712 */     if (paramString == null)
/* 529:    */     {
/* 530:713 */       if (paramCalendar1 != null)
/* 531:    */       {
/* 532:714 */         if (paramCalendar2 != null)
/* 533:    */         {
/* 534:715 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ?");
/* 535:716 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 536:717 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 537:718 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 538:719 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 539:720 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ? AND b.DataDate < ?");
/* 540:    */         }
/* 541:    */         else
/* 542:    */         {
/* 543:722 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ?");
/* 544:723 */           localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 545:724 */           localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 546:725 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 547:726 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate > ?");
/* 548:    */         }
/* 549:    */       }
/* 550:728 */       else if (paramCalendar2 != null)
/* 551:    */       {
/* 552:729 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ?");
/* 553:730 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 554:731 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 555:732 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 556:733 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate < ?");
/* 557:    */       }
/* 558:    */       else
/* 559:    */       {
/* 560:735 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=?");
/* 561:736 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 562:737 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 563:738 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=?");
/* 564:    */       }
/* 565:    */     }
/* 566:741 */     else if (paramCalendar1 != null)
/* 567:    */     {
/* 568:742 */       if (paramCalendar2 != null)
/* 569:    */       {
/* 570:743 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate > ? AND b.DataDate < ?");
/* 571:744 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 572:745 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 573:746 */         localPreparedStatement.setString(3, paramString);
/* 574:747 */         DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar1);
/* 575:748 */         DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramCalendar2);
/* 576:749 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate > ? AND b.DataDate < ?");
/* 577:    */       }
/* 578:    */       else
/* 579:    */       {
/* 580:751 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate > ?");
/* 581:752 */         localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 582:753 */         localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 583:754 */         localPreparedStatement.setString(3, paramString);
/* 584:755 */         DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar1);
/* 585:756 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate > ?");
/* 586:    */       }
/* 587:    */     }
/* 588:758 */     else if (paramCalendar2 != null)
/* 589:    */     {
/* 590:759 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate < ?");
/* 591:760 */       localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 592:761 */       localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 593:762 */       localPreparedStatement.setString(3, paramString);
/* 594:763 */       DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 595:764 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? AND b.DataDate < ?");
/* 596:    */     }
/* 597:    */     else
/* 598:    */     {
/* 599:766 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? ");
/* 600:767 */       localPreparedStatement.setString(1, paramDisbursementAccount.getAccountID());
/* 601:768 */       localPreparedStatement.setString(2, paramDisbursementAccount.getRoutingNumber());
/* 602:769 */       localPreparedStatement.setString(3, paramString);
/* 603:770 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min(b.TransactionIndex), max(b.TransactionIndex) FROM BS_Account a, BS_DsbTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum =? AND b.Presentment = ? ");
/* 604:    */     }
/* 605:774 */     long[] arrayOfLong = new long[2];
/* 606:775 */     if (!localResultSet.next()) {
/* 607:776 */       throw new BSException(3001, "Unable to retrieve the maximum transaction index for the given criteria");
/* 608:    */     }
/* 609:778 */     arrayOfLong[0] = localResultSet.getLong(1);
/* 610:779 */     arrayOfLong[1] = localResultSet.getLong(2);
/* 611:    */     
/* 612:781 */     return arrayOfLong;
/* 613:    */   }
/* 614:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSDsbTransactions
 * JD-Core Version:    0.7.0.1
 */