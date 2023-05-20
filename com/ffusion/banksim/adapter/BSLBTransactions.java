/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.db.MessageText;
/*   5:    */ import com.ffusion.banksim.interfaces.BSConstants;
/*   6:    */ import com.ffusion.banksim.interfaces.BSException;
/*   7:    */ import com.ffusion.beans.lockbox.LockboxAccount;
/*   8:    */ import com.ffusion.beans.lockbox.LockboxTransaction;
/*   9:    */ import com.ffusion.beans.lockbox.LockboxTransactions;
/*  10:    */ import com.ffusion.dataconsolidator.adapter.DCUtil;
/*  11:    */ import com.ffusion.util.MapUtil;
/*  12:    */ import com.ffusion.util.db.DBUtil;
/*  13:    */ import java.sql.PreparedStatement;
/*  14:    */ import java.sql.ResultSet;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.HashMap;
/*  17:    */ 
/*  18:    */ public class BSLBTransactions
/*  19:    */ {
/*  20:    */   private static final String jdField_void = "INSERT INTO BS_LBTransactions( AccountID, TransactionIndex, LockboxNumber, DataDate, DataSource, TransID, TransTypeID, Description, Amount, NumRejectedChecks, RejectedAmount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
/*  21:    */   private static final String jdField_null = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? ORDER BY b.TransactionIndex";
/*  22:    */   private static final String jdField_goto = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? ORDER BY b.TransactionIndex";
/*  23:    */   private static final String jdField_char = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? ORDER BY b.TransactionIndex";
/*  24:    */   private static final String jdField_byte = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.TransactionIndex";
/*  25:    */   private static final String b = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  26:    */   private static final String jdField_long = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  27:    */   private static final String jdField_else = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  28:    */   private static final String jdField_case = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  29:    */   private static final String jdField_int = "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=?";
/*  30:    */   private static final String jdField_do = "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=?";
/*  31:    */   private static final String jdField_if = "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?";
/*  32:    */   private static final String a = "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=?";
/*  33:    */   private static final String jdField_for = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex ";
/*  34:    */   private static final String jdField_try = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex";
/*  35:    */   private static final String jdField_new = "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex ";
/*  36:    */   
/*  37:    */   public static void addTransactions(LockboxAccount paramLockboxAccount, LockboxTransactions paramLockboxTransactions, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/*  38:    */     throws BSException
/*  39:    */   {
/*  40:139 */     PreparedStatement localPreparedStatement = null;
/*  41:140 */     LockboxTransaction localLockboxTransaction = null;
/*  42:141 */     long l1 = 0L;
/*  43:    */     try
/*  44:    */     {
/*  45:144 */       String str1 = null;
/*  46:145 */       if (paramHashMap != null) {
/*  47:146 */         str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
/*  48:    */       }
/*  49:149 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_LBTransactions( AccountID, TransactionIndex, LockboxNumber, DataDate, DataSource, TransID, TransTypeID, Description, Amount, NumRejectedChecks, RejectedAmount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
/*  50:150 */       String str2 = paramLockboxAccount.getAccountID();
/*  51:151 */       for (int i = 0; i < paramLockboxTransactions.size(); i++)
/*  52:    */       {
/*  53:152 */         localLockboxTransaction = (LockboxTransaction)paramLockboxTransactions.get(i);
/*  54:154 */         if (localLockboxTransaction.getTransactionIndex() != 0L) {
/*  55:155 */           throw new BSException(3001, MessageText.getMessage("ERR_INVALID_TRANSACTION_INDEX"));
/*  56:    */         }
/*  57:158 */         l1 = BSRecordCounter.getNextIndex(paramDBConnection, 2, str2, "LOCKBOX_TRANSACTION_INDEX");
/*  58:    */         
/*  59:    */ 
/*  60:    */ 
/*  61:162 */         long l2 = BSExtendABeanXML.addExtendABeanXML(paramDBConnection, localLockboxTransaction.getExtendABeanXML());
/*  62:    */         
/*  63:164 */         localPreparedStatement.setString(1, str2);
/*  64:165 */         localPreparedStatement.setLong(2, l1);
/*  65:166 */         localPreparedStatement.setString(3, localLockboxTransaction.getLockboxNumber());
/*  66:167 */         DCUtil.fillTimestampColumn(localPreparedStatement, 4, localLockboxTransaction.getProcessingDate());
/*  67:168 */         localPreparedStatement.setInt(5, paramInt);
/*  68:169 */         localPreparedStatement.setInt(6, localLockboxTransaction.getTransactionID());
/*  69:170 */         localPreparedStatement.setInt(7, localLockboxTransaction.getTransactionType());
/*  70:171 */         localPreparedStatement.setString(8, localLockboxTransaction.getDescription());
/*  71:172 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localLockboxTransaction.getAmount());
/*  72:173 */         localPreparedStatement.setInt(10, localLockboxTransaction.getNumRejectedChecks());
/*  73:174 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 11, localLockboxTransaction.getRejectedAmount());
/*  74:175 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 12, localLockboxTransaction.getImmediateFloat());
/*  75:176 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 13, localLockboxTransaction.getOneDayFloat());
/*  76:177 */         DCUtil.fillCurrencyColumn(localPreparedStatement, 14, localLockboxTransaction.getTwoDayFloat());
/*  77:178 */         DCUtil.fillTimestampColumn(localPreparedStatement, 15, localLockboxTransaction.getValueDateTime());
/*  78:179 */         localPreparedStatement.setString(16, localLockboxTransaction.getBankReferenceNumber());
/*  79:180 */         localPreparedStatement.setString(17, localLockboxTransaction.getCustomerReferenceNumber());
/*  80:181 */         localPreparedStatement.setLong(18, l2);
/*  81:182 */         localPreparedStatement.setString(19, null);
/*  82:183 */         localPreparedStatement.setString(20, str1);
/*  83:    */         
/*  84:185 */         DBConnection.executeUpdate(localPreparedStatement, "INSERT INTO BS_LBTransactions( AccountID, TransactionIndex, LockboxNumber, DataDate, DataSource, TransID, TransTypeID, Description, Amount, NumRejectedChecks, RejectedAmount, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
/*  85:    */       }
/*  86:    */     }
/*  87:    */     catch (Exception localException)
/*  88:    */     {
/*  89:188 */       throw new BSException(1, localException.getMessage());
/*  90:    */     }
/*  91:    */     finally
/*  92:    */     {
/*  93:190 */       if (localPreparedStatement != null) {
/*  94:191 */         DBConnection.closeStatement(localPreparedStatement);
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private static void a(LockboxAccount paramLockboxAccount, LockboxTransaction paramLockboxTransaction, ResultSet paramResultSet)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102:208 */     paramLockboxTransaction.setAccountID(paramLockboxAccount.getAccountID());
/* 103:209 */     paramLockboxTransaction.setAccountNumber(paramLockboxAccount.getAccountNumber());
/* 104:210 */     paramLockboxTransaction.setBankID(paramLockboxAccount.getBankID());
/* 105:211 */     paramLockboxTransaction.setTransactionIndex(paramResultSet.getLong(1));
/* 106:212 */     paramLockboxTransaction.setLockboxNumber(paramResultSet.getString(2));
/* 107:213 */     paramLockboxTransaction.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(3)));
/* 108:214 */     paramLockboxTransaction.setTransactionID(paramResultSet.getInt(4));
/* 109:215 */     paramLockboxTransaction.setTransactionType(paramResultSet.getInt(5));
/* 110:216 */     paramLockboxTransaction.setDescription(paramResultSet.getString(6));
/* 111:217 */     paramLockboxTransaction.setAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7)));
/* 112:218 */     paramLockboxTransaction.setNumRejectedChecks(paramResultSet.getInt(8));
/* 113:219 */     paramLockboxTransaction.setRejectedAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9)));
/* 114:220 */     paramLockboxTransaction.setImmediateFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10)));
/* 115:221 */     paramLockboxTransaction.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11)));
/* 116:222 */     paramLockboxTransaction.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12)));
/* 117:223 */     paramLockboxTransaction.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(13)));
/* 118:224 */     paramLockboxTransaction.setBankReferenceNumber(paramResultSet.getString(14));
/* 119:225 */     paramLockboxTransaction.setCustomerReferenceNumber(paramResultSet.getString(15));
/* 120:226 */     DCUtil.fillExtendABean(paramLockboxTransaction, paramResultSet, 16);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 124:    */     throws BSException
/* 125:    */   {
/* 126:244 */     PreparedStatement localPreparedStatement = null;
/* 127:245 */     LockboxTransactions localLockboxTransactions1 = null;
/* 128:246 */     ResultSet localResultSet = null;
/* 129:    */     try
/* 130:    */     {
/* 131:250 */       if (paramCalendar1 != null)
/* 132:    */       {
/* 133:251 */         if (paramCalendar2 != null)
/* 134:    */         {
/* 135:252 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? ORDER BY b.TransactionIndex");
/* 136:253 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 137:254 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 138:255 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 139:256 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 140:257 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? ORDER BY b.TransactionIndex");
/* 141:    */         }
/* 142:    */         else
/* 143:    */         {
/* 144:259 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? ORDER BY b.TransactionIndex");
/* 145:260 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 146:261 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 147:262 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 148:263 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? ORDER BY b.TransactionIndex");
/* 149:    */         }
/* 150:    */       }
/* 151:265 */       else if (paramCalendar2 != null)
/* 152:    */       {
/* 153:266 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? ORDER BY b.TransactionIndex");
/* 154:267 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 155:268 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 156:269 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 157:270 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? ORDER BY b.TransactionIndex");
/* 158:    */       }
/* 159:    */       else
/* 160:    */       {
/* 161:272 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.TransactionIndex");
/* 162:273 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 163:274 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 164:275 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.TransactionIndex");
/* 165:    */       }
/* 166:279 */       localLockboxTransactions1 = new LockboxTransactions();
/* 167:280 */       LockboxTransaction localLockboxTransaction = null;
/* 168:281 */       while (localResultSet.next())
/* 169:    */       {
/* 170:282 */         localLockboxTransaction = localLockboxTransactions1.create();
/* 171:283 */         a(paramLockboxAccount, localLockboxTransaction, localResultSet);
/* 172:    */       }
/* 173:285 */       DBUtil.closeResultSet(localResultSet);
/* 174:286 */       return localLockboxTransactions1;
/* 175:    */     }
/* 176:    */     catch (Exception localException)
/* 177:    */     {
/* 178:289 */       throw new BSException(1, localException.getMessage());
/* 179:    */     }
/* 180:    */     finally
/* 181:    */     {
/* 182:291 */       if (localPreparedStatement != null) {
/* 183:292 */         DBConnection.closeStatement(localPreparedStatement);
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 189:    */     throws BSException
/* 190:    */   {
/* 191:314 */     PreparedStatement localPreparedStatement = null;
/* 192:315 */     LockboxTransactions localLockboxTransactions1 = null;
/* 193:316 */     ResultSet localResultSet = null;
/* 194:    */     try
/* 195:    */     {
/* 196:319 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 197:320 */       long[] arrayOfLong = a(paramDBConnection, paramLockboxAccount, paramCalendar1, paramCalendar2);
/* 198:321 */       long l1 = arrayOfLong[0];
/* 199:322 */       long l2 = arrayOfLong[1];
/* 200:324 */       if (paramCalendar1 != null)
/* 201:    */       {
/* 202:325 */         if (paramCalendar2 != null)
/* 203:    */         {
/* 204:326 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 205:327 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 206:328 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 207:329 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 208:330 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 209:331 */           localPreparedStatement.setLong(5, l1);
/* 210:332 */           localPreparedStatement.setLong(6, l1 + i - 1L);
/* 211:333 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 212:    */         }
/* 213:    */         else
/* 214:    */         {
/* 215:335 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 216:336 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 217:337 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 218:338 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 219:339 */           localPreparedStatement.setLong(4, l1);
/* 220:340 */           localPreparedStatement.setLong(5, l1 + i - 1L);
/* 221:341 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 222:    */         }
/* 223:    */       }
/* 224:343 */       else if (paramCalendar2 != null)
/* 225:    */       {
/* 226:344 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 227:345 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 228:346 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 229:347 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 230:348 */         localPreparedStatement.setLong(4, l1);
/* 231:349 */         localPreparedStatement.setLong(5, l1 + i - 1L);
/* 232:350 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 233:    */       }
/* 234:    */       else
/* 235:    */       {
/* 236:352 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 237:353 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 238:354 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 239:355 */         localPreparedStatement.setLong(3, l1);
/* 240:356 */         localPreparedStatement.setLong(4, l1 + i - 1L);
/* 241:357 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 242:    */       }
/* 243:361 */       localLockboxTransactions1 = new LockboxTransactions();
/* 244:362 */       LockboxTransaction localLockboxTransaction = null;
/* 245:364 */       while (localResultSet.next())
/* 246:    */       {
/* 247:365 */         localLockboxTransaction = localLockboxTransactions1.create();
/* 248:366 */         a(paramLockboxAccount, localLockboxTransaction, localResultSet);
/* 249:    */       }
/* 250:369 */       paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l1));
/* 251:370 */       paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l2));
/* 252:    */       
/* 253:372 */       DBUtil.closeResultSet(localResultSet);
/* 254:373 */       return localLockboxTransactions1;
/* 255:    */     }
/* 256:    */     catch (Exception localException)
/* 257:    */     {
/* 258:376 */       throw new BSException(1, localException.getMessage());
/* 259:    */     }
/* 260:    */     finally
/* 261:    */     {
/* 262:378 */       if (localPreparedStatement != null) {
/* 263:379 */         DBConnection.closeStatement(localPreparedStatement);
/* 264:    */       }
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static LockboxTransactions getRecentTransactions(LockboxAccount paramLockboxAccount, DBConnection paramDBConnection, HashMap paramHashMap)
/* 269:    */     throws BSException
/* 270:    */   {
/* 271:395 */     PreparedStatement localPreparedStatement = null;
/* 272:396 */     LockboxTransactions localLockboxTransactions = new LockboxTransactions();
/* 273:    */     try
/* 274:    */     {
/* 275:399 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 276:400 */       long l = BSRecordCounter.getIndex(paramDBConnection, paramLockboxAccount.getAccountID(), paramLockboxAccount.getRoutingNumber(), 2, "LOCKBOX_TRANSACTION_INDEX");
/* 277:    */       Object localObject1;
/* 278:404 */       if (l != -1L)
/* 279:    */       {
/* 280:405 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 281:406 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 282:407 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 283:408 */         localPreparedStatement.setLong(3, l - i + 1L);
/* 284:409 */         localPreparedStatement.setLong(4, l);
/* 285:410 */         localObject1 = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex");
/* 286:    */         
/* 287:412 */         LockboxTransaction localLockboxTransaction = null;
/* 288:414 */         while (((ResultSet)localObject1).next())
/* 289:    */         {
/* 290:415 */           localLockboxTransaction = localLockboxTransactions.create();
/* 291:416 */           a(paramLockboxAccount, localLockboxTransaction, (ResultSet)localObject1);
/* 292:    */         }
/* 293:418 */         DBUtil.closeResultSet((ResultSet)localObject1);
/* 294:    */       }
/* 295:420 */       return localLockboxTransactions;
/* 296:    */     }
/* 297:    */     catch (Exception localException)
/* 298:    */     {
/* 299:423 */       throw new BSException(1, localException.getMessage());
/* 300:    */     }
/* 301:    */     finally
/* 302:    */     {
/* 303:425 */       if (localPreparedStatement != null) {
/* 304:426 */         DBConnection.closeStatement(localPreparedStatement);
/* 305:    */       }
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, long paramLong, DBConnection paramDBConnection, HashMap paramHashMap)
/* 310:    */     throws BSException
/* 311:    */   {
/* 312:443 */     PreparedStatement localPreparedStatement = null;
/* 313:444 */     LockboxTransactions localLockboxTransactions1 = null;
/* 314:    */     try
/* 315:    */     {
/* 316:447 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 317:    */       
/* 318:    */ 
/* 319:450 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex ");
/* 320:451 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 321:452 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 322:453 */       localPreparedStatement.setLong(3, paramLong);
/* 323:454 */       localPreparedStatement.setLong(4, paramLong + i - 1L);
/* 324:    */       
/* 325:456 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex ");
/* 326:    */       
/* 327:458 */       localLockboxTransactions1 = new LockboxTransactions();
/* 328:459 */       LockboxTransaction localLockboxTransaction = null;
/* 329:461 */       while (localResultSet.next())
/* 330:    */       {
/* 331:462 */         localLockboxTransaction = localLockboxTransactions1.create();
/* 332:463 */         a(paramLockboxAccount, localLockboxTransaction, localResultSet);
/* 333:    */       }
/* 334:465 */       DBUtil.closeResultSet(localResultSet);
/* 335:466 */       return localLockboxTransactions1;
/* 336:    */     }
/* 337:    */     catch (Exception localException)
/* 338:    */     {
/* 339:469 */       throw new BSException(1, localException.getMessage());
/* 340:    */     }
/* 341:    */     finally
/* 342:    */     {
/* 343:471 */       if (localPreparedStatement != null) {
/* 344:472 */         DBConnection.closeStatement(localPreparedStatement);
/* 345:    */       }
/* 346:    */     }
/* 347:    */   }
/* 348:    */   
/* 349:    */   public static LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, long paramLong, DBConnection paramDBConnection, HashMap paramHashMap)
/* 350:    */     throws BSException
/* 351:    */   {
/* 352:489 */     PreparedStatement localPreparedStatement = null;
/* 353:490 */     LockboxTransactions localLockboxTransactions1 = null;
/* 354:    */     try
/* 355:    */     {
/* 356:493 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 357:    */       
/* 358:    */ 
/* 359:496 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex ");
/* 360:497 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 361:498 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 362:499 */       localPreparedStatement.setLong(3, paramLong - i + 1L);
/* 363:500 */       localPreparedStatement.setLong(4, paramLong);
/* 364:501 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT b.TransactionIndex, b.LockboxNumber, b.DataDate, b.TransID, b.TransTypeID, b.Description, b.Amount, b.NumRejectedChecks, b.RejectedAmount, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.TransactionIndex>=? AND b.TransactionIndex<=? ORDER BY b.TransactionIndex ");
/* 365:    */       
/* 366:503 */       localLockboxTransactions1 = new LockboxTransactions();
/* 367:504 */       LockboxTransaction localLockboxTransaction = null;
/* 368:506 */       while (localResultSet.next())
/* 369:    */       {
/* 370:507 */         localLockboxTransaction = localLockboxTransactions1.create();
/* 371:508 */         a(paramLockboxAccount, localLockboxTransaction, localResultSet);
/* 372:    */       }
/* 373:510 */       DBUtil.closeResultSet(localResultSet);
/* 374:511 */       return localLockboxTransactions1;
/* 375:    */     }
/* 376:    */     catch (Exception localException)
/* 377:    */     {
/* 378:514 */       throw new BSException(1, localException.getMessage());
/* 379:    */     }
/* 380:    */     finally
/* 381:    */     {
/* 382:516 */       if (localPreparedStatement != null) {
/* 383:517 */         DBConnection.closeStatement(localPreparedStatement);
/* 384:    */       }
/* 385:    */     }
/* 386:    */   }
/* 387:    */   
/* 388:    */   private static long[] a(DBConnection paramDBConnection, LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2)
/* 389:    */     throws Exception
/* 390:    */   {
/* 391:525 */     ResultSet localResultSet = null;
/* 392:526 */     PreparedStatement localPreparedStatement = null;
/* 393:528 */     if (paramCalendar1 != null)
/* 394:    */     {
/* 395:529 */       if (paramCalendar2 != null)
/* 396:    */       {
/* 397:530 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=?");
/* 398:531 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 399:532 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 400:533 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 401:534 */         DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 402:535 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=?");
/* 403:    */       }
/* 404:    */       else
/* 405:    */       {
/* 406:537 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=?");
/* 407:538 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 408:539 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 409:540 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 410:541 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=?");
/* 411:    */       }
/* 412:    */     }
/* 413:543 */     else if (paramCalendar2 != null)
/* 414:    */     {
/* 415:544 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?");
/* 416:545 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 417:546 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 418:547 */       DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 419:548 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?");
/* 420:    */     }
/* 421:    */     else
/* 422:    */     {
/* 423:550 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=?");
/* 424:551 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 425:552 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 426:553 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.TransactionIndex ), max( b.TransactionIndex ) FROM BS_Account a, BS_LBTransactions b WHERE a.AccountID=b.AccountID AND a.AccountID=? AND a.RoutingNum=?");
/* 427:    */     }
/* 428:556 */     long[] arrayOfLong = new long[2];
/* 429:557 */     if (!localResultSet.next()) {
/* 430:558 */       throw new BSException(3001, "Unable to retrieve the maximum transaction index for the given criteria");
/* 431:    */     }
/* 432:561 */     arrayOfLong[0] = localResultSet.getLong(1);
/* 433:562 */     arrayOfLong[1] = localResultSet.getLong(2);
/* 434:    */     
/* 435:564 */     return arrayOfLong;
/* 436:    */   }
/* 437:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSLBTransactions
 * JD-Core Version:    0.7.0.1
 */