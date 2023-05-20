/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import com.ffusion.beans.Currency;
/*   6:    */ import com.ffusion.beans.lockbox.LockboxAccount;
/*   7:    */ import com.ffusion.beans.lockbox.LockboxSummaries;
/*   8:    */ import com.ffusion.beans.lockbox.LockboxSummary;
/*   9:    */ import com.ffusion.dataconsolidator.adapter.DCUtil;
/*  10:    */ import com.ffusion.util.db.DBUtil;
/*  11:    */ import java.sql.PreparedStatement;
/*  12:    */ import java.sql.ResultSet;
/*  13:    */ import java.util.Calendar;
/*  14:    */ import java.util.HashMap;
/*  15:    */ 
/*  16:    */ public class BSLockboxSummary
/*  17:    */ {
/*  18:    */   private static final String jdField_do = "INSERT INTO BS_LockboxSummary( AccountID, DataDate, DataSource, TotalCredits, TotalDebits, TotalNumCredits, TotalNumDebits, ImmediateFloat, OneDayFloat, TwoDayFloat, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
/*  19:    */   private static final String jdField_if = "UPDATE BS_LockboxSummary SET AccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, BAIFileIdentifier=? WHERE AccountID=? AND DataDate=? ";
/*  20: 34 */   private static String jdField_new = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM BS_Account a, BS_LockboxSummary b WHERE b.AccountID= a.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? AND b.DataDate<=? ORDER BY b.DataDate";
/*  21: 38 */   private static String jdField_int = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM BS_Account a, BS_LockboxSummary b WHERE b.AccountID= a.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate>=? ORDER BY b.DataDate";
/*  22: 42 */   private static String jdField_for = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM BS_Account a, BS_LockboxSummary b WHERE b.AccountID= a.AccountID AND a.AccountID=? AND a.RoutingNum=? AND  b.DataDate<=? ORDER BY b.DataDate";
/*  23: 46 */   private static String a = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM BS_Account a, BS_LockboxSummary b WHERE b.AccountID= a.AccountID AND a.AccountID=? AND a.RoutingNum=? ORDER BY b.DataDate";
/*  24: 50 */   private static String jdField_try = "SELECT b.DataDate, b.TotalCredits, b.TotalDebits, b.TotalNumCredits, b.TotalNumDebits, b.ImmediateFloat, b.OneDayFloat, b.TwoDayFloat, b.ExtendABeanXMLID FROM BS_Account a, BS_LockboxSummary b WHERE b.AccountID= a.AccountID AND a.AccountID=? AND a.RoutingNum=? AND b.DataDate=?";
/*  25:    */   
/*  26:    */   public static void add(LockboxSummary paramLockboxSummary, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/*  27:    */     throws BSException
/*  28:    */   {
/*  29: 64 */     PreparedStatement localPreparedStatement = null;
/*  30: 65 */     LockboxAccount localLockboxAccount = paramLockboxSummary.getLockboxAccount();
/*  31:    */     try
/*  32:    */     {
/*  33: 69 */       String str1 = null;
/*  34: 70 */       if (paramHashMap != null) {
/*  35: 71 */         str1 = (String)paramHashMap.get("BAI_FILE_IDENTIFIER");
/*  36:    */       }
/*  37: 74 */       String str2 = localLockboxAccount.getAccountID();
/*  38:    */       
/*  39:    */ 
/*  40:    */ 
/*  41: 78 */       LockboxSummary localLockboxSummary1 = a(localLockboxAccount, paramLockboxSummary.getSummaryDate(), paramDBConnection);
/*  42: 79 */       LockboxSummary localLockboxSummary2 = null;
/*  43: 81 */       if (localLockboxSummary1 == null)
/*  44:    */       {
/*  45: 82 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_LockboxSummary( AccountID, DataDate, DataSource, TotalCredits, TotalDebits, TotalNumCredits, TotalNumDebits, ImmediateFloat, OneDayFloat, TwoDayFloat, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
/*  46: 83 */         localLockboxSummary2 = paramLockboxSummary;
/*  47:    */       }
/*  48:    */       else
/*  49:    */       {
/*  50: 85 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "UPDATE BS_LockboxSummary SET AccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, BAIFileIdentifier=? WHERE AccountID=? AND DataDate=? ");
/*  51: 86 */         localLockboxSummary2 = localLockboxSummary1;
/*  52:    */         
/*  53: 88 */         Currency localCurrency = null;
/*  54: 89 */         int i = -1;
/*  55:    */         
/*  56: 91 */         localCurrency = paramLockboxSummary.getTotalLockboxCredits();
/*  57: 92 */         if (localCurrency != null) {
/*  58: 93 */           localLockboxSummary2.setTotalLockboxCredits(localCurrency);
/*  59:    */         }
/*  60: 96 */         localCurrency = paramLockboxSummary.getTotalLockboxDebits();
/*  61: 97 */         if (localCurrency != null) {
/*  62: 98 */           localLockboxSummary2.setTotalLockboxDebits(localCurrency);
/*  63:    */         }
/*  64:101 */         i = paramLockboxSummary.getTotalNumLockboxCredits();
/*  65:102 */         if (i != -1) {
/*  66:103 */           localLockboxSummary2.setTotalNumLockboxCredits(i);
/*  67:    */         }
/*  68:106 */         i = paramLockboxSummary.getTotalNumLockboxDebits();
/*  69:107 */         if (i != -1) {
/*  70:108 */           localLockboxSummary2.setTotalNumLockboxDebits(i);
/*  71:    */         }
/*  72:111 */         localCurrency = paramLockboxSummary.getImmediateFloat();
/*  73:112 */         if (localCurrency != null) {
/*  74:113 */           localLockboxSummary2.setImmediateFloat(localCurrency);
/*  75:    */         }
/*  76:116 */         localCurrency = paramLockboxSummary.getOneDayFloat();
/*  77:117 */         if (localCurrency != null) {
/*  78:118 */           localLockboxSummary2.setOneDayFloat(localCurrency);
/*  79:    */         }
/*  80:121 */         localCurrency = paramLockboxSummary.getTwoDayFloat();
/*  81:122 */         if (localCurrency != null) {
/*  82:123 */           localLockboxSummary2.setTwoDayFloat(localCurrency);
/*  83:    */         }
/*  84:    */       }
/*  85:127 */       localPreparedStatement.setString(1, str2);
/*  86:128 */       DCUtil.fillTimestampColumn(localPreparedStatement, 2, localLockboxSummary2.getSummaryDate());
/*  87:129 */       localPreparedStatement.setInt(3, paramInt);
/*  88:130 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 4, localLockboxSummary2.getTotalLockboxCredits());
/*  89:131 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 5, localLockboxSummary2.getTotalLockboxDebits());
/*  90:132 */       localPreparedStatement.setInt(6, localLockboxSummary2.getTotalNumLockboxCredits());
/*  91:133 */       localPreparedStatement.setInt(7, localLockboxSummary2.getTotalNumLockboxDebits());
/*  92:134 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 8, localLockboxSummary2.getImmediateFloat());
/*  93:135 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 9, localLockboxSummary2.getOneDayFloat());
/*  94:136 */       DCUtil.fillCurrencyColumn(localPreparedStatement, 10, localLockboxSummary2.getTwoDayFloat());
/*  95:138 */       if (localLockboxSummary1 == null)
/*  96:    */       {
/*  97:139 */         localPreparedStatement.setLong(11, BSExtendABeanXML.addExtendABeanXML(paramDBConnection, localLockboxSummary2.getExtendABeanXML()));
/*  98:140 */         localPreparedStatement.setString(12, null);
/*  99:141 */         localPreparedStatement.setString(13, str1);
/* 100:142 */         DBConnection.executeUpdate(localPreparedStatement, "INSERT INTO BS_LockboxSummary( AccountID, DataDate, DataSource, TotalCredits, TotalDebits, TotalNumCredits, TotalNumDebits, ImmediateFloat, OneDayFloat, TwoDayFloat, ExtendABeanXMLID, Extra, BAIFileIdentifier ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:144 */         localPreparedStatement.setString(11, str1);
/* 105:145 */         localPreparedStatement.setString(12, str2);
/* 106:146 */         DCUtil.fillTimestampColumn(localPreparedStatement, 13, localLockboxSummary2.getSummaryDate());
/* 107:147 */         DBConnection.executeUpdate(localPreparedStatement, "UPDATE BS_LockboxSummary SET AccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalDebits=?, TotalNumCredits=?, TotalNumDebits=?, ImmediateFloat=?, OneDayFloat=?, TwoDayFloat=?, BAIFileIdentifier=? WHERE AccountID=? AND DataDate=? ");
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (Exception localException)
/* 111:    */     {
/* 112:151 */       throw new BSException(1, localException.getMessage());
/* 113:    */     }
/* 114:    */     finally
/* 115:    */     {
/* 116:153 */       if (localPreparedStatement != null) {
/* 117:154 */         DBConnection.closeStatement(localPreparedStatement);
/* 118:    */       }
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static LockboxSummaries getLockboxSummaries(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection, HashMap paramHashMap)
/* 123:    */     throws BSException
/* 124:    */   {
/* 125:173 */     PreparedStatement localPreparedStatement = null;
/* 126:174 */     LockboxSummaries localLockboxSummaries1 = null;
/* 127:    */     
/* 128:176 */     ResultSet localResultSet = null;
/* 129:    */     try
/* 130:    */     {
/* 131:179 */       if (paramCalendar1 != null)
/* 132:    */       {
/* 133:180 */         if (paramCalendar2 != null)
/* 134:    */         {
/* 135:181 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_new);
/* 136:182 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 137:183 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 138:184 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 139:185 */           DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
/* 140:186 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_new);
/* 141:    */         }
/* 142:    */         else
/* 143:    */         {
/* 144:188 */           localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_int);
/* 145:189 */           localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 146:190 */           localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 147:191 */           DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
/* 148:192 */           localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_int);
/* 149:    */         }
/* 150:    */       }
/* 151:194 */       else if (paramCalendar2 != null)
/* 152:    */       {
/* 153:195 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_for);
/* 154:196 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 155:197 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 156:198 */         DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
/* 157:199 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_for);
/* 158:    */       }
/* 159:    */       else
/* 160:    */       {
/* 161:201 */         localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, a);
/* 162:202 */         localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 163:203 */         localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 164:204 */         localResultSet = DBConnection.executeQuery(localPreparedStatement, a);
/* 165:    */       }
/* 166:207 */       localLockboxSummaries1 = new LockboxSummaries();
/* 167:208 */       LockboxSummary localLockboxSummary = null;
/* 168:209 */       while (localResultSet.next())
/* 169:    */       {
/* 170:210 */         localLockboxSummary = a(paramLockboxAccount, localResultSet);
/* 171:211 */         localLockboxSummaries1.add(localLockboxSummary);
/* 172:    */       }
/* 173:213 */       DBUtil.closeResultSet(localResultSet);
/* 174:214 */       return localLockboxSummaries1;
/* 175:    */     }
/* 176:    */     catch (Exception localException)
/* 177:    */     {
/* 178:217 */       throw new BSException(1, localException.getMessage());
/* 179:    */     }
/* 180:    */     finally
/* 181:    */     {
/* 182:219 */       if (localPreparedStatement != null) {
/* 183:220 */         DBConnection.closeStatement(localPreparedStatement);
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   private static LockboxSummary a(LockboxAccount paramLockboxAccount, Calendar paramCalendar, DBConnection paramDBConnection)
/* 189:    */     throws BSException
/* 190:    */   {
/* 191:237 */     PreparedStatement localPreparedStatement = null;
/* 192:238 */     ResultSet localResultSet = null;
/* 193:    */     try
/* 194:    */     {
/* 195:240 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, jdField_try);
/* 196:241 */       localPreparedStatement.setString(1, paramLockboxAccount.getAccountID());
/* 197:242 */       localPreparedStatement.setString(2, paramLockboxAccount.getRoutingNumber());
/* 198:243 */       DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
/* 199:244 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, jdField_try);
/* 200:    */       
/* 201:    */ 
/* 202:247 */       LockboxSummary localLockboxSummary1 = null;
/* 203:248 */       if (localResultSet.next()) {
/* 204:249 */         localLockboxSummary1 = a(paramLockboxAccount, localResultSet);
/* 205:    */       }
/* 206:251 */       DBUtil.closeResultSet(localResultSet);
/* 207:252 */       return localLockboxSummary1;
/* 208:    */     }
/* 209:    */     catch (Exception localException)
/* 210:    */     {
/* 211:255 */       throw new BSException(1, localException.getMessage());
/* 212:    */     }
/* 213:    */     finally
/* 214:    */     {
/* 215:257 */       if (localPreparedStatement != null) {
/* 216:258 */         DBConnection.closeStatement(localPreparedStatement);
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   private static LockboxSummary a(LockboxAccount paramLockboxAccount, ResultSet paramResultSet)
/* 222:    */     throws Exception
/* 223:    */   {
/* 224:266 */     LockboxSummary localLockboxSummary = new LockboxSummary();
/* 225:    */     
/* 226:268 */     localLockboxSummary.setLockboxAccount(paramLockboxAccount);
/* 227:269 */     localLockboxSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1)));
/* 228:270 */     localLockboxSummary.setTotalLockboxCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2)));
/* 229:271 */     localLockboxSummary.setTotalLockboxDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3)));
/* 230:272 */     localLockboxSummary.setTotalNumLockboxCredits(paramResultSet.getInt(4));
/* 231:273 */     localLockboxSummary.setTotalNumLockboxDebits(paramResultSet.getInt(5));
/* 232:274 */     localLockboxSummary.setImmediateFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6)));
/* 233:275 */     localLockboxSummary.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7)));
/* 234:276 */     localLockboxSummary.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8)));
/* 235:277 */     DCUtil.fillExtendABean(localLockboxSummary, paramResultSet, 9);
/* 236:    */     
/* 237:279 */     return localLockboxSummary;
/* 238:    */   }
/* 239:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSLockboxSummary
 * JD-Core Version:    0.7.0.1
 */