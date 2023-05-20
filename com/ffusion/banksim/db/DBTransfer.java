/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import com.ffusion.beans.Balance;
/*   6:    */ import com.ffusion.beans.Currency;
/*   7:    */ import com.ffusion.beans.DateTime;
/*   8:    */ import com.ffusion.beans.accounts.Account;
/*   9:    */ import com.ffusion.beans.banking.Transfer;
/*  10:    */ import com.ffusion.util.db.DBUtil;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.Date;
/*  14:    */ 
/*  15:    */ public class DBTransfer
/*  16:    */ {
/*  17:    */   private static final int jdField_for = 40;
/*  18:    */   private static final String jdField_new = "select AccountID from BS_Account where AccountID=?";
/*  19:    */   private static final String jdField_int = "insert into BS_Transactions( {|TransactionID,|TransactionID,|TransactionID,|||TransactionID,} TransactionDate, TransactionTypeID, AccountID, FIID, Amount, DestAccountID, DestFIID, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( {|?,|NEXTVAL FOR BS_TransactionIDSequence,|BS_TransactionIDSequence.NEXTVAL,|||NEXTVAL FOR BS_TransactionIDSequence,} ?,?,?,?,?,?,?,?,?,?,{|0|NEXTVAL FOR BS_ReferenceNumberSequence|BS_ReferenceNumberSequence.NEXTVAL|0|0|NEXTVAL FOR BS_ReferenceNumberSequence},?,? )";
/*  20:    */   private static final String jdField_if = "insert into BS_Transactions( {|TransactionID,|TransactionID,|TransactionID,|||TransactionID,} TransactionDate, TransactionTypeID, AccountID, FIID, Amount, DestAccountID, DestFIID, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( {|?,|NEXTVAL FOR BS_TransactionIDSequence,|BS_TransactionIDSequence.NEXTVAL,|||NEXTVAL FOR BS_TransactionIDSequence,} ?,?,?,?,?,?,?,?,?,?,{|0|PREVVAL FOR BS_ReferenceNumberSequence|BS_ReferenceNumberSequence.CURRVAL|0|0|PREVVAL FOR BS_ReferenceNumberSequence},?,? )";
/*  21:    */   private static final String jdField_try = "select * from BS_Transactions where TransactionDate = ? FETCH FIRST ROW ONLY";
/*  22:    */   private static final String jdField_case = "select * from BS_Transactions where TransactionDate = ? ";
/*  23:    */   private static final String jdField_do = "update BS_Transactions set ReferenceNumber = ? where TransactionDate = ?";
/*  24:    */   private static final String jdField_byte = "{||VALUES PREVVAL FOR BS_ReferenceNumberSequence|SELECT BS_ReferenceNumberSequence.CURRVAL FROM dual|||VALUES PREVVAL FOR BS_ReferenceNumberSequence}";
/*  25:    */   private static final String a = "BS_TRANSACTIONIDSEQUENCE";
/*  26:    */   
/*  27:    */   public static final Transfer addTransfer(Transfer paramTransfer, int paramInt, DBConnection paramDBConnection)
/*  28:    */     throws BSException
/*  29:    */   {
/*  30: 90 */     boolean bool = false;
/*  31:    */     try
/*  32:    */     {
/*  33: 95 */       bool = paramDBConnection.isAutoCommit();
/*  34: 96 */       if (bool) {
/*  35: 96 */         paramDBConnection.setAutoCommit(false);
/*  36:    */       }
/*  37:100 */       if (!DBAccount.a(paramTransfer.getFromAccount(), paramDBConnection)) {
/*  38:101 */         throw new BSException(1011, MessageText.getMessage("ERR_FROM_ACC_NOT_EXIST"));
/*  39:    */       }
/*  40:107 */       if (!DBAccount.a(paramTransfer.getToAccount(), paramDBConnection)) {
/*  41:108 */         DBAccount.addAccount(DBClient.signOn("banksim", "banksim", paramDBConnection), paramTransfer.getToAccount(), paramDBConnection, true);
/*  42:115 */       } else if (paramTransfer.getFromAccount().getID().equals(paramTransfer.getToAccount().getID())) {
/*  43:117 */         throw new BSException(1012, MessageText.getMessage("ERR_ACCOUNTS_SAME"));
/*  44:    */       }
/*  45:123 */       BigDecimal localBigDecimal1 = paramTransfer.getAmountValue().getAmountValue();
/*  46:124 */       localBigDecimal1 = localBigDecimal1.setScale(2, 6);
/*  47:    */       
/*  48:    */ 
/*  49:127 */       BigDecimal localBigDecimal2 = paramTransfer.getAmountValue().getAmountValue();
/*  50:128 */       localBigDecimal2 = localBigDecimal2.setScale(2, 6);
/*  51:131 */       if (a(paramTransfer.getToAmountValue()))
/*  52:    */       {
/*  53:133 */         localBigDecimal2 = paramTransfer.getToAmountValue().getAmountValue();
/*  54:134 */         localBigDecimal2 = localBigDecimal2.setScale(2, 6);
/*  55:    */       }
/*  56:138 */       BigDecimal localBigDecimal3 = DBAccount.getCurrentBalance(paramTransfer.getFromAccount(), paramDBConnection);
/*  57:139 */       BigDecimal localBigDecimal4 = DBAccount.getCurrentBalance(paramTransfer.getToAccount(), paramDBConnection);
/*  58:    */       
/*  59:    */ 
/*  60:    */ 
/*  61:143 */       localBigDecimal3 = localBigDecimal3.setScale(2, 6);
/*  62:144 */       localBigDecimal4 = localBigDecimal4.setScale(2, 6);
/*  63:147 */       if (localBigDecimal3.compareTo(localBigDecimal1) < 0) {
/*  64:148 */         throw new BSException(1009, MessageText.getMessage("ERR_FROM_ACC_NSF"));
/*  65:    */       }
/*  66:153 */       if (localBigDecimal2.compareTo(new BigDecimal("0")) < 0) {
/*  67:154 */         throw new BSException(1010, MessageText.getMessage("ERR_AMOUNT_NOT_POSITIVE"));
/*  68:    */       }
/*  69:158 */       Long localLong = new Long(System.currentTimeMillis());
/*  70:    */       String str;
/*  71:160 */       if (paramInt == 3) {
/*  72:162 */         str = paramTransfer.getReferenceNumber();
/*  73:    */       } else {
/*  74:164 */         str = null;
/*  75:    */       }
/*  76:170 */       if ((paramTransfer.getMemo() != null) && (paramTransfer.getMemo().length() > 40)) {
/*  77:172 */         paramTransfer.setMemo(paramTransfer.getMemo().substring(0, 40));
/*  78:    */       }
/*  79:177 */       if (paramDBConnection.getParams().getConnectionType() == 2)
/*  80:    */       {
/*  81:179 */         localObject1 = new Object[] { new Integer(DBUtil.getNextId(paramDBConnection.a(), "ASE", "BS_TRANSACTIONIDSEQUENCE")), localLong, new Integer(paramInt), paramTransfer.getToAccount().getID(), paramTransfer.getToAccount().getBankID(), localBigDecimal2.toString(), paramTransfer.getFromAccount().getID(), paramTransfer.getFromAccount().getBankID(), paramTransfer.getToAccount().getCurrencyCode(), paramTransfer.getMemo(), str, "P", localBigDecimal3.subtract(localBigDecimal1) };
/*  82:    */         
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:197 */         paramDBConnection.executeUpdate("insert into BS_Transactions( {|TransactionID,|TransactionID,|TransactionID,|||TransactionID,} TransactionDate, TransactionTypeID, AccountID, FIID, Amount, DestAccountID, DestFIID, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( {|?,|NEXTVAL FOR BS_TransactionIDSequence,|BS_TransactionIDSequence.NEXTVAL,|||NEXTVAL FOR BS_TransactionIDSequence,} ?,?,?,?,?,?,?,?,?,?,{|0|NEXTVAL FOR BS_ReferenceNumberSequence|BS_ReferenceNumberSequence.NEXTVAL|0|0|NEXTVAL FOR BS_ReferenceNumberSequence},?,? )", (Object[])localObject1);
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:201 */         localObject1 = new Object[] { localLong, new Integer(paramInt), paramTransfer.getToAccount().getID(), paramTransfer.getToAccount().getBankID(), localBigDecimal2.toString(), paramTransfer.getFromAccount().getID(), paramTransfer.getFromAccount().getBankID(), paramTransfer.getToAccount().getCurrencyCode(), paramTransfer.getMemo(), str, "P", localBigDecimal3.subtract(localBigDecimal1) };
/* 104:    */         
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:216 */         paramDBConnection.executeUpdate("insert into BS_Transactions( {|TransactionID,|TransactionID,|TransactionID,|||TransactionID,} TransactionDate, TransactionTypeID, AccountID, FIID, Amount, DestAccountID, DestFIID, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( {|?,|NEXTVAL FOR BS_TransactionIDSequence,|BS_TransactionIDSequence.NEXTVAL,|||NEXTVAL FOR BS_TransactionIDSequence,} ?,?,?,?,?,?,?,?,?,?,{|0|NEXTVAL FOR BS_ReferenceNumberSequence|BS_ReferenceNumberSequence.NEXTVAL|0|0|NEXTVAL FOR BS_ReferenceNumberSequence},?,? )", (Object[])localObject1);
/* 119:    */       }
/* 120:222 */       if (paramInt == 6) {
/* 121:223 */         paramInt = 7;
/* 122:    */       }
/* 123:225 */       if (paramInt == 8) {
/* 124:226 */         paramInt = 9;
/* 125:    */       }
/* 126:231 */       if (paramDBConnection.getParams().getConnectionType() == 2)
/* 127:    */       {
/* 128:233 */         localObject1 = new Object[] { new Integer(DBUtil.getNextId(paramDBConnection.a(), "ASE", "BS_TRANSACTIONIDSEQUENCE")), localLong, new Integer(paramInt), paramTransfer.getFromAccount().getID(), paramTransfer.getFromAccount().getBankID(), localBigDecimal1.negate().toString(), paramTransfer.getToAccount().getID(), paramTransfer.getToAccount().getBankID(), paramTransfer.getFromAccount().getCurrencyCode(), paramTransfer.getMemo(), str, "P", localBigDecimal4.add(localBigDecimal2) };
/* 129:    */         
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:251 */         paramDBConnection.executeUpdate("insert into BS_Transactions( {|TransactionID,|TransactionID,|TransactionID,|||TransactionID,} TransactionDate, TransactionTypeID, AccountID, FIID, Amount, DestAccountID, DestFIID, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( {|?,|NEXTVAL FOR BS_TransactionIDSequence,|BS_TransactionIDSequence.NEXTVAL,|||NEXTVAL FOR BS_TransactionIDSequence,} ?,?,?,?,?,?,?,?,?,?,{|0|PREVVAL FOR BS_ReferenceNumberSequence|BS_ReferenceNumberSequence.CURRVAL|0|0|PREVVAL FOR BS_ReferenceNumberSequence},?,? )", (Object[])localObject1);
/* 147:    */       }
/* 148:    */       else
/* 149:    */       {
/* 150:255 */         localObject1 = new Object[] { localLong, new Integer(paramInt), paramTransfer.getFromAccount().getID(), paramTransfer.getFromAccount().getBankID(), localBigDecimal1.negate().toString(), paramTransfer.getToAccount().getID(), paramTransfer.getToAccount().getBankID(), paramTransfer.getFromAccount().getCurrencyCode(), paramTransfer.getMemo(), str, "P", localBigDecimal4.add(localBigDecimal2) };
/* 151:    */         
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:270 */         paramDBConnection.executeUpdate("insert into BS_Transactions( {|TransactionID,|TransactionID,|TransactionID,|||TransactionID,} TransactionDate, TransactionTypeID, AccountID, FIID, Amount, DestAccountID, DestFIID, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( {|?,|NEXTVAL FOR BS_TransactionIDSequence,|BS_TransactionIDSequence.NEXTVAL,|||NEXTVAL FOR BS_TransactionIDSequence,} ?,?,?,?,?,?,?,?,?,?,{|0|PREVVAL FOR BS_ReferenceNumberSequence|BS_ReferenceNumberSequence.CURRVAL|0|0|PREVVAL FOR BS_ReferenceNumberSequence},?,? )", (Object[])localObject1);
/* 166:    */       }
/* 167:274 */       DBAccount.updateBalance(paramTransfer.getFromAccount(), localBigDecimal3.subtract(localBigDecimal1), paramDBConnection);
/* 168:275 */       DBAccount.updateBalance(paramTransfer.getToAccount(), localBigDecimal4.add(localBigDecimal2), paramDBConnection);
/* 169:279 */       if ((paramDBConnection.getParams().getConnectionType() == 5) || (paramDBConnection.getParams().getConnectionType() == 6) || (paramDBConnection.getParams().getConnectionType() == 2))
/* 170:    */       {
/* 171:285 */         localObject1 = new Object[] { localLong };
/* 172:    */         
/* 173:287 */         DBResultSet localDBResultSet = null;
/* 174:289 */         if ((paramDBConnection.getParams().getConnectionType() == 6) || (paramDBConnection.getParams().getConnectionType() == 2)) {
/* 175:292 */           localDBResultSet = paramDBConnection.prepareQuery("select * from BS_Transactions where TransactionDate = ? ");
/* 176:    */         } else {
/* 177:296 */           localDBResultSet = paramDBConnection.prepareQuery("select * from BS_Transactions where TransactionDate = ? FETCH FIRST ROW ONLY");
/* 178:    */         }
/* 179:300 */         localDBResultSet.open((Object[])localObject1);
/* 180:301 */         localDBResultSet.getNextRow();
/* 181:302 */         int j = localDBResultSet.getColumnInt(1);
/* 182:303 */         localDBResultSet.close();
/* 183:304 */         paramTransfer.setID(Integer.toString(j));
/* 184:    */         
/* 185:306 */         localObject2 = new Object[] { new Integer(j), localLong };
/* 186:307 */         paramDBConnection.executeUpdate("update BS_Transactions set ReferenceNumber = ? where TransactionDate = ?", (Object[])localObject2);
/* 187:309 */         if (paramInt != 3) {
/* 188:310 */           paramTransfer.setReferenceNumber(Integer.toString(j));
/* 189:    */         }
/* 190:    */       }
/* 191:314 */       else if (paramInt != 3)
/* 192:    */       {
/* 193:315 */         localObject1 = null;
/* 194:    */         
/* 195:317 */         localObject1 = paramDBConnection.prepareQuery("{||VALUES PREVVAL FOR BS_ReferenceNumberSequence|SELECT BS_ReferenceNumberSequence.CURRVAL FROM dual|||VALUES PREVVAL FOR BS_ReferenceNumberSequence}");
/* 196:318 */         ((DBResultSet)localObject1).open();
/* 197:319 */         ((DBResultSet)localObject1).getNextRow();
/* 198:320 */         int i = ((DBResultSet)localObject1).getColumnInt(1);
/* 199:321 */         paramTransfer.setReferenceNumber(Integer.toString(i));
/* 200:322 */         paramTransfer.setID(Integer.toString(i));
/* 201:323 */         ((DBResultSet)localObject1).close();
/* 202:    */       }
/* 203:328 */       paramTransfer.setStatus(5);
/* 204:    */       
/* 205:    */ 
/* 206:    */ 
/* 207:332 */       Object localObject1 = new DateTime();
/* 208:333 */       Date localDate = new Date(localLong.longValue());
/* 209:334 */       ((DateTime)localObject1).setTime(localDate);
/* 210:    */       
/* 211:    */ 
/* 212:337 */       paramTransfer.setStatus(5);
/* 213:338 */       paramTransfer.setDate((DateTime)localObject1);
/* 214:    */       
/* 215:340 */       Balance localBalance1 = new Balance();
/* 216:341 */       Object localObject2 = new Currency();
/* 217:342 */       ((Currency)localObject2).setAmount(localBigDecimal3.subtract(localBigDecimal1));
/* 218:343 */       localBalance1.setAmount((Currency)localObject2);
/* 219:344 */       localBalance1.setDate((DateTime)localObject1);
/* 220:345 */       paramTransfer.getFromAccount().setCurrentBalance(localBalance1);
/* 221:    */       
/* 222:347 */       Balance localBalance2 = new Balance();
/* 223:348 */       Currency localCurrency = new Currency();
/* 224:349 */       localCurrency.setAmount(localBigDecimal4.add(localBigDecimal2));
/* 225:350 */       localBalance2.setAmount(localCurrency);
/* 226:351 */       localBalance2.setDate((DateTime)localObject1);
/* 227:352 */       paramTransfer.getToAccount().setCurrentBalance(localBalance2);
/* 228:    */       
/* 229:354 */       paramDBConnection.commit();
/* 230:    */       
/* 231:356 */       return paramTransfer;
/* 232:    */     }
/* 233:    */     catch (SQLException localSQLException1)
/* 234:    */     {
/* 235:    */       try
/* 236:    */       {
/* 237:361 */         paramDBConnection.rollback();
/* 238:    */       }
/* 239:    */       catch (SQLException localSQLException2) {}
/* 240:366 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 241:    */     }
/* 242:    */     catch (BSException localBSException)
/* 243:    */     {
/* 244:    */       try
/* 245:    */       {
/* 246:374 */         paramDBConnection.rollback();
/* 247:    */       }
/* 248:    */       catch (SQLException localSQLException3) {}
/* 249:379 */       throw localBSException;
/* 250:    */     }
/* 251:    */     catch (Throwable localThrowable)
/* 252:    */     {
/* 253:    */       try
/* 254:    */       {
/* 255:384 */         paramDBConnection.rollback();
/* 256:    */       }
/* 257:    */       catch (SQLException localSQLException4) {}
/* 258:389 */       throw new BSException(1, localThrowable.toString());
/* 259:    */     }
/* 260:    */     finally
/* 261:    */     {
/* 262:    */       try
/* 263:    */       {
/* 264:394 */         if (bool) {
/* 265:394 */           paramDBConnection.setAutoCommit(true);
/* 266:    */         }
/* 267:    */       }
/* 268:    */       catch (SQLException localSQLException5)
/* 269:    */       {
/* 270:396 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException5));
/* 271:    */       }
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   private static boolean a(Currency paramCurrency)
/* 276:    */   {
/* 277:406 */     if (paramCurrency == null) {
/* 278:407 */       return false;
/* 279:    */     }
/* 280:409 */     return paramCurrency.getAmountValue().compareTo(new BigDecimal(0.0D)) > 0;
/* 281:    */   }
/* 282:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBTransfer
 * JD-Core Version:    0.7.0.1
 */