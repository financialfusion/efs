/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.db.MessageText;
/*   5:    */ import com.ffusion.banksim.interfaces.BSConstants;
/*   6:    */ import com.ffusion.banksim.interfaces.BSException;
/*   7:    */ import com.ffusion.beans.lockbox.LockboxAccount;
/*   8:    */ import com.ffusion.beans.lockbox.LockboxCreditItem;
/*   9:    */ import com.ffusion.beans.lockbox.LockboxCreditItems;
/*  10:    */ import com.ffusion.dataconsolidator.adapter.DCUtil;
/*  11:    */ import com.ffusion.util.MapUtil;
/*  12:    */ import com.ffusion.util.db.DBUtil;
/*  13:    */ import java.sql.PreparedStatement;
/*  14:    */ import java.sql.ResultSet;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.HashMap;
/*  17:    */ 
/*  18:    */ public class BSLBCreditItems
/*  19:    */ {
/*  20:    */   private static final String jdField_char = "INSERT INTO BS_LBCreditItems( LockboxID, CreditItemIndex, DataDate, DataSource, ItemID, DocumentType, Payor, Amount, CheckNumber, CheckDate, CouponAccountNum, CouponAmount1, CouponAmount2, CouponDate1, CouponDate2, CouponRefNum, CheckRoutingNum, CheckAccountNum, LockboxWorkType, LockboxBatchNum, LockboxSeqNum, Memo, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
/*  21:    */   private static final String jdField_for = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex";
/*  22:    */   private static final String jdField_do = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex";
/*  23:    */   private static final String jdField_if = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex";
/*  24:    */   private static final String a = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex";
/*  25:    */   private static final String jdField_else = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  26:    */   private static final String jdField_case = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  27:    */   private static final String jdField_byte = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  28:    */   private static final String jdField_try = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  29:    */   private static final String d = "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ?";
/*  30:    */   private static final String c = "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ?";
/*  31:    */   private static final String b = "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ?";
/*  32:    */   private static final String jdField_void = "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ?";
/*  33:    */   private static final String jdField_long = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  34:    */   private static final String jdField_null = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  35:    */   private static final String jdField_goto = "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex";
/*  36:    */   private static final String jdField_new = "INSERT INTO BS_Lockbox( LockboxID, AccountID, LockboxNumber, ExtendABeanXMLID, Extra) VALUES(?, ?, ?, ?, ?)";
/*  37:    */   private static final String jdField_int = "SELECT MAX(LockboxID) FROM BS_Lockbox";
/*  38:    */   public static final String MINIMUM_TRANSACTION_INDEX_FOR_RANGE = "MINIMUM_TRANSACTION_INDEX_FOR_RANGE";
/*  39:    */   public static final String MAXIMUM_TRANSACTION_INDEX_FOR_RANGE = "MAXIMUM_TRANSACTION_INDEX_FOR_RANGE";
/*  40:    */   
/*  41:    */   public static void addItems(LockboxAccount paramLockboxAccount, LockboxCreditItems paramLockboxCreditItems, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/*  42:    */     throws BSException
/*  43:    */   {
/*  44:184 */     PreparedStatement localPreparedStatement1 = null;
/*  45:185 */     PreparedStatement localPreparedStatement2 = null;
/*  46:186 */     PreparedStatement localPreparedStatement3 = null;
/*  47:187 */     LockboxCreditItem localLockboxCreditItem = null;
/*  48:188 */     ResultSet localResultSet = null;
/*  49:189 */     long l1 = 0L;
/*  50:    */     try
/*  51:    */     {
/*  52:193 */       String str1 = null;
/*  53:194 */       if (paramHashMap != null) {
/*  54:195 */         str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
/*  55:    */       }
/*  56:199 */       String str2 = paramLockboxAccount.getAccountID();
/*  57:    */       
/*  58:    */ 
/*  59:202 */       long l2 = BSExtendABeanXML.addExtendABeanXML(paramDBConnection, paramLockboxAccount.getExtendABeanXML());
/*  60:    */       
/*  61:    */ 
/*  62:205 */       localPreparedStatement1 = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_LBCreditItems( LockboxID, CreditItemIndex, DataDate, DataSource, ItemID, DocumentType, Payor, Amount, CheckNumber, CheckDate, CouponAccountNum, CouponAmount1, CouponAmount2, CouponDate1, CouponDate2, CouponRefNum, CheckRoutingNum, CheckAccountNum, LockboxWorkType, LockboxBatchNum, LockboxSeqNum, Memo, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
/*  63:206 */       localPreparedStatement2 = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_Lockbox( LockboxID, AccountID, LockboxNumber, ExtendABeanXMLID, Extra) VALUES(?, ?, ?, ?, ?)");
/*  64:207 */       localPreparedStatement3 = paramDBConnection.prepareStatement(paramDBConnection, "SELECT MAX(LockboxID) FROM BS_Lockbox");
/*  65:209 */       for (int i = 0; i < paramLockboxCreditItems.size(); i++)
/*  66:    */       {
/*  67:210 */         localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(i);
/*  68:    */         
/*  69:    */ 
/*  70:213 */         int j = BSAdapter.getLockboxID(paramDBConnection, str2, localLockboxCreditItem.getLockboxNumber());
/*  71:215 */         if (j == -1)
/*  72:    */         {
/*  73:217 */           localResultSet = DBConnection.executeQuery(localPreparedStatement3, "SELECT MAX(LockboxID) FROM BS_Lockbox");
/*  74:218 */           if (localResultSet.next()) {
/*  75:219 */             j = localResultSet.getInt(1);
/*  76:    */           } else {
/*  77:222 */             j = 1;
/*  78:    */           }
/*  79:224 */           DBUtil.closeResultSet(localResultSet);
/*  80:    */           
/*  81:    */ 
/*  82:227 */           localPreparedStatement2.setInt(1, j);
/*  83:228 */           localPreparedStatement2.setString(2, str2);
/*  84:229 */           localPreparedStatement2.setString(3, localLockboxCreditItem.getLockboxNumber());
/*  85:230 */           localPreparedStatement2.setLong(4, l2);
/*  86:231 */           localPreparedStatement2.setString(5, null);
/*  87:232 */           DBConnection.executeUpdate(localPreparedStatement2, "INSERT INTO BS_Lockbox( LockboxID, AccountID, LockboxNumber, ExtendABeanXMLID, Extra) VALUES(?, ?, ?, ?, ?)");
/*  88:    */           
/*  89:    */ 
/*  90:235 */           BSRecordCounter.addNewCounter(paramDBConnection, 2, String.valueOf(j), "LOCKBOX_CREDITITEM_INDEX");
/*  91:    */         }
/*  92:239 */         if (localLockboxCreditItem.getItemIndex() != 0L) {
/*  93:240 */           throw new BSException(3000, MessageText.getMessage("ERR_INVALID_CREDIT_ITEM_INDEX"));
/*  94:    */         }
/*  95:243 */         l1 = BSRecordCounter.getNextIndex(paramDBConnection, 2, String.valueOf(j), "LOCKBOX_CREDITITEM_INDEX");
/*  96:    */         
/*  97:    */ 
/*  98:    */ 
/*  99:247 */         long l3 = BSExtendABeanXML.addExtendABeanXML(paramDBConnection, localLockboxCreditItem.getExtendABeanXML());
/* 100:    */         
/* 101:249 */         localPreparedStatement1.setInt(1, j);
/* 102:250 */         localPreparedStatement1.setLong(2, l1);
/* 103:251 */         DCUtil.fillTimestampColumn(localPreparedStatement1, 3, localLockboxCreditItem.getProcessingDate());
/* 104:252 */         localPreparedStatement1.setInt(4, paramInt);
/* 105:253 */         localPreparedStatement1.setInt(5, localLockboxCreditItem.getItemID());
/* 106:254 */         localPreparedStatement1.setString(6, localLockboxCreditItem.getDocumentType());
/* 107:255 */         localPreparedStatement1.setString(7, localLockboxCreditItem.getPayer());
/* 108:256 */         DCUtil.fillCurrencyColumn(localPreparedStatement1, 8, localLockboxCreditItem.getCheckAmount());
/* 109:257 */         localPreparedStatement1.setString(9, localLockboxCreditItem.getCheckNumber());
/* 110:258 */         DCUtil.fillTimestampColumn(localPreparedStatement1, 10, localLockboxCreditItem.getCheckDate());
/* 111:259 */         localPreparedStatement1.setString(11, localLockboxCreditItem.getCouponAccountNumber());
/* 112:260 */         DCUtil.fillCurrencyColumn(localPreparedStatement1, 12, localLockboxCreditItem.getCouponAmount1());
/* 113:261 */         DCUtil.fillCurrencyColumn(localPreparedStatement1, 13, localLockboxCreditItem.getCouponAmount2());
/* 114:262 */         DCUtil.fillTimestampColumn(localPreparedStatement1, 14, localLockboxCreditItem.getCouponDate1());
/* 115:263 */         DCUtil.fillTimestampColumn(localPreparedStatement1, 15, localLockboxCreditItem.getCouponDate2());
/* 116:264 */         localPreparedStatement1.setString(16, localLockboxCreditItem.getCouponReferenceNumber());
/* 117:265 */         localPreparedStatement1.setString(17, localLockboxCreditItem.getCheckRoutingNumber());
/* 118:266 */         localPreparedStatement1.setString(18, localLockboxCreditItem.getCheckAccountNumber());
/* 119:267 */         localPreparedStatement1.setString(19, localLockboxCreditItem.getLockboxWorkType());
/* 120:268 */         localPreparedStatement1.setString(20, localLockboxCreditItem.getLockboxBatchNumber());
/* 121:269 */         localPreparedStatement1.setString(21, localLockboxCreditItem.getLockboxSequenceNumber());
/* 122:270 */         localPreparedStatement1.setString(22, localLockboxCreditItem.getMemo());
/* 123:271 */         DCUtil.fillCurrencyColumn(localPreparedStatement1, 23, localLockboxCreditItem.getImmediateFloat());
/* 124:272 */         DCUtil.fillCurrencyColumn(localPreparedStatement1, 24, localLockboxCreditItem.getOneDayFloat());
/* 125:273 */         DCUtil.fillCurrencyColumn(localPreparedStatement1, 25, localLockboxCreditItem.getTwoDayFloat());
/* 126:274 */         DCUtil.fillTimestampColumn(localPreparedStatement1, 26, localLockboxCreditItem.getValueDateTime());
/* 127:275 */         localPreparedStatement1.setString(27, localLockboxCreditItem.getBankReferenceNumber());
/* 128:276 */         localPreparedStatement1.setString(28, localLockboxCreditItem.getCustomerReferenceNumber());
/* 129:277 */         localPreparedStatement1.setLong(29, l3);
/* 130:278 */         localPreparedStatement1.setString(30, null);
/* 131:279 */         localPreparedStatement1.setString(31, str1);
/* 132:    */         
/* 133:281 */         DBConnection.executeUpdate(localPreparedStatement1, "INSERT INTO BS_LBCreditItems( LockboxID, CreditItemIndex, DataDate, DataSource, ItemID, DocumentType, Payor, Amount, CheckNumber, CheckDate, CouponAccountNum, CouponAmount1, CouponAmount2, CouponDate1, CouponDate2, CouponRefNum, CheckRoutingNum, CheckAccountNum, LockboxWorkType, LockboxBatchNum, LockboxSeqNum, Memo, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, BankRefNum, CustRefNum, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
/* 134:    */       }
/* 135:    */     }
/* 136:    */     catch (Exception localException)
/* 137:    */     {
/* 138:284 */       throw new BSException(1, localException.getMessage());
/* 139:    */     }
/* 140:    */     finally
/* 141:    */     {
/* 142:286 */       if (localPreparedStatement1 != null) {
/* 143:287 */         DBConnection.closeStatement(localPreparedStatement1);
/* 144:    */       }
/* 145:290 */       if (localPreparedStatement2 != null) {
/* 146:291 */         DBConnection.closeStatement(localPreparedStatement2);
/* 147:    */       }
/* 148:294 */       if (localPreparedStatement3 != null) {
/* 149:295 */         DBConnection.closeStatement(localPreparedStatement3);
/* 150:    */       }
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static void a(LockboxAccount paramLockboxAccount, LockboxCreditItem paramLockboxCreditItem, ResultSet paramResultSet)
/* 155:    */     throws Exception
/* 156:    */   {
/* 157:311 */     paramLockboxCreditItem.setAccountID(paramLockboxAccount.getAccountID());
/* 158:312 */     paramLockboxCreditItem.setAccountNumber(paramLockboxAccount.getAccountNumber());
/* 159:313 */     paramLockboxCreditItem.setBankID(paramLockboxAccount.getBankID());
/* 160:314 */     paramLockboxCreditItem.setLockboxNumber(paramResultSet.getString(1));
/* 161:315 */     paramLockboxCreditItem.setItemIndex(paramResultSet.getLong(2));
/* 162:316 */     paramLockboxCreditItem.setProcessingDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(3)));
/* 163:317 */     paramLockboxCreditItem.setItemID(paramResultSet.getInt(4));
/* 164:318 */     paramLockboxCreditItem.setDocumentType(paramResultSet.getString(5));
/* 165:319 */     paramLockboxCreditItem.setPayer(paramResultSet.getString(6));
/* 166:320 */     paramLockboxCreditItem.setCheckAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7)));
/* 167:321 */     paramLockboxCreditItem.setCheckNumber(paramResultSet.getString(8));
/* 168:322 */     paramLockboxCreditItem.setCheckDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(9)));
/* 169:323 */     paramLockboxCreditItem.setCouponAccountNumber(paramResultSet.getString(10));
/* 170:324 */     paramLockboxCreditItem.setCouponAmount1(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11)));
/* 171:325 */     paramLockboxCreditItem.setCouponAmount2(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12)));
/* 172:326 */     paramLockboxCreditItem.setCouponDate1(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(13)));
/* 173:327 */     paramLockboxCreditItem.setCouponDate2(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(14)));
/* 174:328 */     paramLockboxCreditItem.setCouponReferenceNumber(paramResultSet.getString(15));
/* 175:329 */     paramLockboxCreditItem.setCheckRoutingNumber(paramResultSet.getString(16));
/* 176:330 */     paramLockboxCreditItem.setCheckAccountNumber(paramResultSet.getString(17));
/* 177:331 */     paramLockboxCreditItem.setLockboxWorkType(paramResultSet.getString(18));
/* 178:332 */     paramLockboxCreditItem.setLockboxBatchNumber(paramResultSet.getString(19));
/* 179:333 */     paramLockboxCreditItem.setLockboxSequenceNumber(paramResultSet.getString(20));
/* 180:334 */     paramLockboxCreditItem.setMemo(paramResultSet.getString(21));
/* 181:335 */     paramLockboxCreditItem.setImmediateFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(22)));
/* 182:336 */     paramLockboxCreditItem.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(23)));
/* 183:337 */     paramLockboxCreditItem.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(24)));
/* 184:338 */     paramLockboxCreditItem.setValueDateTime(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(25)));
/* 185:339 */     paramLockboxCreditItem.setBankReferenceNumber(paramResultSet.getString(26));
/* 186:340 */     paramLockboxCreditItem.setCustomerReferenceNumber(paramResultSet.getString(27));
/* 187:341 */     DCUtil.fillExtendABean(paramLockboxCreditItem, paramResultSet, 28);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static LockboxCreditItems getItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 191:    */     throws BSException
/* 192:    */   {
/* 193:361 */     PreparedStatement localPreparedStatement = null;
/* 194:362 */     LockboxCreditItems localLockboxCreditItems1 = null;
/* 195:363 */     ResultSet localResultSet = null;
/* 196:    */     try
/* 197:    */     {
/* 198:367 */       if (paramCalendar1 != null)
/* 199:    */       {
/* 200:368 */         if (paramCalendar2 != null)
/* 201:    */         {
/* 202:369 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 203:370 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 204:371 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 205:372 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 206:373 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 207:374 */           localPreparedStatement.setString(5, paramString);
/* 208:375 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 209:    */         }
/* 210:    */         else
/* 211:    */         {
/* 212:377 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 213:378 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 214:379 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 215:380 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 216:381 */           localPreparedStatement.setString(4, paramString);
/* 217:382 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 218:    */         }
/* 219:    */       }
/* 220:384 */       else if (paramCalendar2 != null)
/* 221:    */       {
/* 222:385 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 223:386 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 224:387 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 225:388 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 226:389 */         localPreparedStatement.setString(4, paramString);
/* 227:390 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:392 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 232:393 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 233:394 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 234:395 */         localPreparedStatement.setString(3, paramString);
/* 235:396 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? ORDER BY b.CreditItemIndex");
/* 236:    */       }
/* 237:399 */       localLockboxCreditItems1 = new LockboxCreditItems();
/* 238:400 */       LockboxCreditItem localLockboxCreditItem = null;
/* 239:401 */       while (localResultSet.next())
/* 240:    */       {
/* 241:402 */         localLockboxCreditItem = localLockboxCreditItems1.create();
/* 242:403 */         a(paramLockboxAccount, localLockboxCreditItem, localResultSet);
/* 243:    */       }
/* 244:405 */       DBUtil.closeResultSet(localResultSet);
/* 245:406 */       return localLockboxCreditItems1;
/* 246:    */     }
/* 247:    */     catch (Exception localException)
/* 248:    */     {
/* 249:409 */       throw new BSException(1, localException.getMessage());
/* 250:    */     }
/* 251:    */     finally
/* 252:    */     {
/* 253:411 */       if (localPreparedStatement != null) {
/* 254:412 */         DBConnection.closeStatement(localPreparedStatement);
/* 255:    */       }
/* 256:415 */       if (paramDBConnection != null) {
/* 257:416 */         paramDBConnection.close();
/* 258:    */       }
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static LockboxCreditItems getPagedLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 263:    */     throws BSException
/* 264:    */   {
/* 265:440 */     PreparedStatement localPreparedStatement = null;
/* 266:441 */     LockboxCreditItems localLockboxCreditItems1 = null;
/* 267:442 */     ResultSet localResultSet = null;
/* 268:    */     try
/* 269:    */     {
/* 270:445 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 271:446 */       long[] arrayOfLong = a(paramDBConnection, paramLockboxAccount, paramString, paramCalendar1, paramCalendar2);
/* 272:447 */       long l1 = arrayOfLong[0];
/* 273:448 */       long l2 = arrayOfLong[1];
/* 274:450 */       if (paramCalendar1 != null)
/* 275:    */       {
/* 276:451 */         if (paramCalendar2 != null)
/* 277:    */         {
/* 278:452 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 279:453 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 280:454 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 281:455 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 282:456 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 283:457 */           localPreparedStatement.setString(5, paramString);
/* 284:458 */           localPreparedStatement.setLong(6, l1);
/* 285:459 */           localPreparedStatement.setLong(7, l1 + (i - 1));
/* 286:460 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 287:    */         }
/* 288:    */         else
/* 289:    */         {
/* 290:462 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 291:463 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 292:464 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 293:465 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 294:466 */           localPreparedStatement.setString(4, paramString);
/* 295:467 */           localPreparedStatement.setLong(5, l1);
/* 296:468 */           localPreparedStatement.setLong(6, l1 + (i - 1));
/* 297:469 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 298:    */         }
/* 299:    */       }
/* 300:471 */       else if (paramCalendar2 != null)
/* 301:    */       {
/* 302:472 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 303:473 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 304:474 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 305:475 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 306:476 */         localPreparedStatement.setString(4, paramString);
/* 307:477 */         localPreparedStatement.setLong(5, l1);
/* 308:478 */         localPreparedStatement.setLong(6, l1 + (i - 1));
/* 309:479 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 310:    */       }
/* 311:    */       else
/* 312:    */       {
/* 313:481 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 314:482 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 315:483 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 316:484 */         localPreparedStatement.setString(3, paramString);
/* 317:485 */         localPreparedStatement.setLong(4, l1);
/* 318:486 */         localPreparedStatement.setLong(5, l1 + (i - 1));
/* 319:487 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 320:    */       }
/* 321:490 */       localLockboxCreditItems1 = new LockboxCreditItems();
/* 322:491 */       LockboxCreditItem localLockboxCreditItem = null;
/* 323:492 */       while (localResultSet.next())
/* 324:    */       {
/* 325:493 */         localLockboxCreditItem = localLockboxCreditItems1.create();
/* 326:494 */         a(paramLockboxAccount, localLockboxCreditItem, localResultSet);
/* 327:    */       }
/* 328:496 */       DBUtil.closeResultSet(localResultSet);
/* 329:    */       
/* 330:498 */       paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l1));
/* 331:499 */       paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(l2));
/* 332:    */       
/* 333:501 */       return localLockboxCreditItems1;
/* 334:    */     }
/* 335:    */     catch (Exception localException)
/* 336:    */     {
/* 337:504 */       throw new BSException(1, localException.getMessage());
/* 338:    */     }
/* 339:    */     finally
/* 340:    */     {
/* 341:506 */       if (localPreparedStatement != null) {
/* 342:507 */         DBConnection.closeStatement(localPreparedStatement);
/* 343:    */       }
/* 344:510 */       if (paramDBConnection != null) {
/* 345:511 */         paramDBConnection.close();
/* 346:    */       }
/* 347:    */     }
/* 348:    */   }
/* 349:    */   
/* 350:    */   public static LockboxCreditItems getRecentItems(LockboxAccount paramLockboxAccount, String paramString, DBConnection paramDBConnection, HashMap paramHashMap)
/* 351:    */     throws BSException
/* 352:    */   {
/* 353:527 */     PreparedStatement localPreparedStatement = null;
/* 354:528 */     LockboxCreditItems localLockboxCreditItems = new LockboxCreditItems();
/* 355:    */     try
/* 356:    */     {
/* 357:531 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 358:532 */       long l = BSRecordCounter.getIndex(paramDBConnection, paramLockboxAccount.getAccountID(), paramLockboxAccount.getRoutingNumber(), paramString, 2, "LOCKBOX_TRANSACTION_INDEX");
/* 359:    */       Object localObject1;
/* 360:537 */       if (l != -1L)
/* 361:    */       {
/* 362:538 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 363:539 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 364:540 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 365:541 */         localPreparedStatement.setString(3, paramString);
/* 366:542 */         localPreparedStatement.setLong(4, l - i + 1L);
/* 367:543 */         localPreparedStatement.setLong(5, l);
/* 368:544 */         localObject1 = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 369:    */         
/* 370:546 */         LockboxCreditItem localLockboxCreditItem = null;
/* 371:547 */         while (((ResultSet)localObject1).next())
/* 372:    */         {
/* 373:548 */           localLockboxCreditItem = localLockboxCreditItems.create();
/* 374:549 */           a(paramLockboxAccount, localLockboxCreditItem, (ResultSet)localObject1);
/* 375:    */         }
/* 376:551 */         DBUtil.closeResultSet((ResultSet)localObject1);
/* 377:    */       }
/* 378:553 */       return localLockboxCreditItems;
/* 379:    */     }
/* 380:    */     catch (Exception localException)
/* 381:    */     {
/* 382:556 */       throw new BSException(1, localException.getMessage());
/* 383:    */     }
/* 384:    */     finally
/* 385:    */     {
/* 386:558 */       if (localPreparedStatement != null) {
/* 387:559 */         DBConnection.closeStatement(localPreparedStatement);
/* 388:    */       }
/* 389:562 */       if (paramDBConnection != null) {
/* 390:563 */         paramDBConnection.close();
/* 391:    */       }
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   public static LockboxCreditItems getNextItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, DBConnection paramDBConnection, HashMap paramHashMap)
/* 396:    */     throws BSException
/* 397:    */   {
/* 398:583 */     PreparedStatement localPreparedStatement = null;
/* 399:584 */     LockboxCreditItems localLockboxCreditItems1 = null;
/* 400:    */     try
/* 401:    */     {
/* 402:587 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 403:    */       
/* 404:589 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 405:590 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 406:591 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 407:592 */       localPreparedStatement.setString(3, paramString);
/* 408:593 */       localPreparedStatement.setLong(4, paramLong);
/* 409:594 */       localPreparedStatement.setLong(5, paramLong + i - 1L);
/* 410:595 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 411:    */       
/* 412:597 */       localLockboxCreditItems1 = new LockboxCreditItems();
/* 413:598 */       LockboxCreditItem localLockboxCreditItem = null;
/* 414:599 */       while (localResultSet.next())
/* 415:    */       {
/* 416:600 */         localLockboxCreditItem = localLockboxCreditItems1.create();
/* 417:601 */         a(paramLockboxAccount, localLockboxCreditItem, localResultSet);
/* 418:    */       }
/* 419:603 */       DBUtil.closeResultSet(localResultSet);
/* 420:604 */       return localLockboxCreditItems1;
/* 421:    */     }
/* 422:    */     catch (Exception localException)
/* 423:    */     {
/* 424:607 */       throw new BSException(1, localException.getMessage());
/* 425:    */     }
/* 426:    */     finally
/* 427:    */     {
/* 428:609 */       if (localPreparedStatement != null) {
/* 429:610 */         DBConnection.closeStatement(localPreparedStatement);
/* 430:    */       }
/* 431:613 */       if (paramDBConnection != null) {
/* 432:614 */         paramDBConnection.close();
/* 433:    */       }
/* 434:    */     }
/* 435:    */   }
/* 436:    */   
/* 437:    */   public static LockboxCreditItems getPreviousItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, DBConnection paramDBConnection, HashMap paramHashMap)
/* 438:    */     throws BSException
/* 439:    */   {
/* 440:634 */     PreparedStatement localPreparedStatement = null;
/* 441:635 */     LockboxCreditItems localLockboxCreditItems1 = null;
/* 442:    */     try
/* 443:    */     {
/* 444:638 */       int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", BSConstants.PAGESIZE);
/* 445:    */       
/* 446:    */ 
/* 447:641 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 448:642 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 449:643 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 450:644 */       localPreparedStatement.setString(3, paramString);
/* 451:645 */       localPreparedStatement.setLong(4, paramLong - i + 1L);
/* 452:646 */       localPreparedStatement.setLong(5, paramLong);
/* 453:647 */       ResultSet localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT c.LockboxNumber, b.CreditItemIndex, b.DataDate, b.ItemID, b.DocumentType, b.Payor, b.Amount, b.CheckNumber, b.CheckDate, b.CouponAccountNum, b.CouponAmount1, b.CouponAmount2, b.CouponDate1, b.CouponDate2, b.CouponRefNum, b.CheckRoutingNum, b.CheckAccountNum, b.LockboxWorkType, b.LockboxBatchNum, b.LockboxSeqNum, b.Memo, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.BankRefNum, b.CustRefNum, b.ExtendABeanXMLID FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ? AND b.CreditItemIndex>=? AND b.CreditItemIndex<=? ORDER BY b.CreditItemIndex");
/* 454:    */       
/* 455:649 */       localLockboxCreditItems1 = new LockboxCreditItems();
/* 456:650 */       LockboxCreditItem localLockboxCreditItem = null;
/* 457:651 */       while (localResultSet.next())
/* 458:    */       {
/* 459:652 */         localLockboxCreditItem = localLockboxCreditItems1.create();
/* 460:653 */         a(paramLockboxAccount, localLockboxCreditItem, localResultSet);
/* 461:    */       }
/* 462:655 */       DBUtil.closeResultSet(localResultSet);
/* 463:656 */       return localLockboxCreditItems1;
/* 464:    */     }
/* 465:    */     catch (Exception localException)
/* 466:    */     {
/* 467:659 */       throw new BSException(1, localException.getMessage());
/* 468:    */     }
/* 469:    */     finally
/* 470:    */     {
/* 471:661 */       if (localPreparedStatement != null) {
/* 472:662 */         DBConnection.closeStatement(localPreparedStatement);
/* 473:    */       }
/* 474:665 */       if (paramDBConnection != null) {
/* 475:666 */         paramDBConnection.close();
/* 476:    */       }
/* 477:    */     }
/* 478:    */   }
/* 479:    */   
/* 480:    */   private static long[] a(DBConnection paramDBConnection, LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2)
/* 481:    */     throws Exception
/* 482:    */   {
/* 483:675 */     ResultSet localResultSet = null;
/* 484:676 */     PreparedStatement localPreparedStatement = null;
/* 485:678 */     if (paramCalendar1 != null)
/* 486:    */     {
/* 487:679 */       if (paramCalendar2 != null)
/* 488:    */       {
/* 489:680 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ?");
/* 490:681 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 491:682 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 492:683 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 493:684 */         DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 494:685 */         localPreparedStatement.setString(5, paramString);
/* 495:686 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? AND c.LockboxNumber = ?");
/* 496:    */       }
/* 497:    */       else
/* 498:    */       {
/* 499:688 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ?");
/* 500:689 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 501:690 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 502:691 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 503:692 */         localPreparedStatement.setString(4, paramString);
/* 504:693 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND c.LockboxNumber = ?");
/* 505:    */       }
/* 506:    */     }
/* 507:695 */     else if (paramCalendar2 != null)
/* 508:    */     {
/* 509:696 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ?");
/* 510:697 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 511:698 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 512:699 */       DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 513:700 */       localPreparedStatement.setString(4, paramString);
/* 514:701 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate<=?  AND c.LockboxNumber = ?");
/* 515:    */     }
/* 516:    */     else
/* 517:    */     {
/* 518:703 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ?");
/* 519:704 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 520:705 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 521:706 */       localPreparedStatement.setString(3, paramString);
/* 522:707 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT min( b.CreditItemIndex ),max( b.CreditItemIndex) FROM BS_Account a, BS_LBCreditItems b, BS_Lockbox c WHERE a.AccountID=c.AccountID AND b.LockboxID=c.LockboxID AND a.AccountID=? AND a.RoutingNum=? AND c.LockboxNumber = ?");
/* 523:    */     }
/* 524:710 */     long[] arrayOfLong = new long[2];
/* 525:711 */     if (!localResultSet.next()) {
/* 526:712 */       throw new BSException(3001, "Unable to retrieve the maximum transaction index for the given criteria");
/* 527:    */     }
/* 528:715 */     arrayOfLong[0] = localResultSet.getLong(1);
/* 529:716 */     arrayOfLong[1] = localResultSet.getLong(2);
/* 530:    */     
/* 531:718 */     return arrayOfLong;
/* 532:    */   }
/* 533:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSLBCreditItems
 * JD-Core Version:    0.7.0.1
 */