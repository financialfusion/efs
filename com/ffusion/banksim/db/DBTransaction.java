/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSException;
/*   4:    */ import com.ffusion.beans.Currency;
/*   5:    */ import com.ffusion.beans.DateTime;
/*   6:    */ import com.ffusion.beans.accounts.Account;
/*   7:    */ import com.ffusion.beans.banking.Transaction;
/*   8:    */ import com.ffusion.beans.banking.Transactions;
/*   9:    */ import com.ffusion.beans.reporting.ReportCriteria;
/*  10:    */ import com.ffusion.beans.reporting.ReportSortCriteria;
/*  11:    */ import com.ffusion.beans.reporting.ReportSortCriterion;
/*  12:    */ import com.ffusion.efs.adapters.profile.Profile;
/*  13:    */ import com.ffusion.util.DateFormatUtil;
/*  14:    */ import com.ffusion.util.beans.PagingContext;
/*  15:    */ import com.ffusion.util.db.DBUtil;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.sql.Connection;
/*  18:    */ import java.sql.PreparedStatement;
/*  19:    */ import java.sql.ResultSet;
/*  20:    */ import java.sql.SQLException;
/*  21:    */ import java.text.DateFormat;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Calendar;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.Enumeration;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.Iterator;
/*  28:    */ import java.util.Locale;
/*  29:    */ import java.util.Properties;
/*  30:    */ import java.util.Set;
/*  31:    */ import java.util.Vector;
/*  32:    */ 
/*  33:    */ public class DBTransaction
/*  34:    */ {
/*  35: 41 */   private static HashMap jdField_int = new HashMap();
/*  36: 42 */   private static HashMap jdField_do = new HashMap();
/*  37: 43 */   private static HashMap jdField_new = new HashMap();
/*  38: 44 */   private static HashMap jdField_if = new HashMap();
/*  39:    */   private static final String a = "SELECT b.TransactionID, b.TransactionTypeID,  b.AccountID, b.TransactionDate, b.Amount, b.Memo, b.ReferenceNumber, b.DataClassification, b.RunningBalance, c.Description, a.RoutingNum FROM BS_Transactions b, BS_Account a, BS_TransactionType c WHERE a.AccountID = b.AccountID AND c.TransactionTypeID = b.TransactionTypeID ";
/*  40:    */   private static final String jdField_for = "INSERT into BS_Transactions( TransactionID, TransactionDate, TransactionTypeID, AccountID, FIID, Amount, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( ?,?,?,?,?,?,?,?,?,?,?,? )";
/*  41:    */   
/*  42:    */   public static final Enumeration getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection)
/*  43:    */     throws BSException
/*  44:    */   {
/*  45: 68 */     PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
/*  46: 69 */     ReportCriteria localReportCriteria = new ReportCriteria();
/*  47: 70 */     HashMap localHashMap1 = new HashMap();
/*  48: 71 */     localPagingContext.setMap(localHashMap1);
/*  49: 72 */     localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
/*  50: 73 */     DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
/*  51: 74 */     if (paramCalendar1 != null) {
/*  52: 75 */       localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
/*  53:    */     }
/*  54: 77 */     if (paramCalendar2 != null) {
/*  55: 78 */       localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
/*  56:    */     }
/*  57: 80 */     HashMap localHashMap2 = new HashMap();
/*  58:    */     
/*  59: 82 */     return getTransactions(paramAccount, localPagingContext, localHashMap2, paramDBConnection);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static final Enumeration getTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap, DBConnection paramDBConnection)
/*  63:    */     throws BSException
/*  64:    */   {
/*  65: 97 */     Enumeration localEnumeration = null;
/*  66: 99 */     if (!DBAccount.a(paramAccount, paramDBConnection)) {
/*  67:101 */       throw new BSException(1011, MessageText.getMessage("ERR_ACCOUNT_NOT_EXISTS"));
/*  68:    */     }
/*  69:105 */     ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
/*  70:    */     
/*  71:107 */     localReportCriteria.getSearchCriteria().put("Account", paramAccount.getID());
/*  72:108 */     localReportCriteria.getSearchCriteria().put("RoutingNum", paramAccount.getRoutingNum());
/*  73:    */     
/*  74:    */ 
/*  75:111 */     ResultSet localResultSet = null;
/*  76:112 */     PreparedStatement localPreparedStatement = null;
/*  77:    */     try
/*  78:    */     {
/*  79:114 */       StringBuffer localStringBuffer = new StringBuffer("SELECT b.TransactionID, b.TransactionTypeID,  b.AccountID, b.TransactionDate, b.Amount, b.Memo, b.ReferenceNumber, b.DataClassification, b.RunningBalance, c.Description, a.RoutingNum FROM BS_Transactions b, BS_Account a, BS_TransactionType c WHERE a.AccountID = b.AccountID AND c.TransactionTypeID = b.TransactionTypeID ");
/*  80:115 */       a(localStringBuffer, paramAccount, paramPagingContext, paramHashMap);
/*  81:116 */       a(localStringBuffer, paramPagingContext, paramHashMap);
/*  82:117 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, localStringBuffer.toString());
/*  83:118 */       int i = 1;
/*  84:    */       
/*  85:120 */       a(localPreparedStatement, i, paramAccount, paramPagingContext, paramHashMap);
/*  86:121 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, localStringBuffer.toString());
/*  87:    */       
/*  88:123 */       Transactions localTransactions = new Transactions();
/*  89:124 */       localEnumeration = a(localTransactions, localResultSet, paramAccount);
/*  90:125 */       localEnumeration = a(localTransactions, localEnumeration, paramAccount, paramPagingContext, paramHashMap);
/*  91:    */     }
/*  92:    */     catch (BSException localBSException)
/*  93:    */     {
/*  94:128 */       throw localBSException;
/*  95:    */     }
/*  96:    */     catch (Exception localException1)
/*  97:    */     {
/*  98:130 */       throw new BSException(1, localException1.toString());
/*  99:    */     }
/* 100:    */     finally
/* 101:    */     {
/* 102:132 */       localReportCriteria.getSearchCriteria().remove("Account");
/* 103:133 */       localReportCriteria.getSearchCriteria().remove("RoutingNum");
/* 104:    */       try
/* 105:    */       {
/* 106:135 */         localResultSet.close();
/* 107:136 */         localPreparedStatement.close();
/* 108:    */       }
/* 109:    */       catch (Exception localException2) {}
/* 110:    */     }
/* 111:140 */     return localEnumeration;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static Enumeration a(Transactions paramTransactions, ResultSet paramResultSet, Account paramAccount)
/* 115:    */     throws SQLException
/* 116:    */   {
/* 117:147 */     Date localDate = null;
/* 118:148 */     Currency localCurrency = null;
/* 119:149 */     Transaction localTransaction = null;
/* 120:150 */     Vector localVector = new Vector();
/* 121:    */     
/* 122:152 */     int i = 0;
/* 123:154 */     while (paramResultSet.next())
/* 124:    */     {
/* 125:155 */       localTransaction = paramTransactions.create();
/* 126:156 */       localTransaction.setID(paramResultSet.getString("TransactionID"));
/* 127:157 */       localTransaction.setType(paramResultSet.getInt("TransactionTypeID"));
/* 128:158 */       localTransaction.setMemo(paramResultSet.getString("Memo"));
/* 129:159 */       localTransaction.setDescription(paramResultSet.getString("Memo"));
/* 130:    */       
/* 131:    */ 
/* 132:162 */       localTransaction.setReferenceNumber(String.valueOf(paramResultSet.getInt("ReferenceNumber")));
/* 133:    */       
/* 134:    */ 
/* 135:165 */       localDate = new Date();
/* 136:166 */       localDate.setTime(paramResultSet.getLong("TransactionDate"));
/* 137:167 */       localTransaction.setDate(new DateTime(localDate, paramAccount.getLocale()));
/* 138:168 */       localTransaction.setProcessingDate(new DateTime(localDate, paramAccount.getLocale()));
/* 139:    */       
/* 140:    */ 
/* 141:171 */       String str = paramResultSet.getString("Amount");
/* 142:172 */       localCurrency = null;
/* 143:173 */       if (str != null) {
/* 144:174 */         localCurrency = new Currency(str.trim(), paramAccount.getCurrencyCode(), paramAccount.getLocale());
/* 145:    */       }
/* 146:176 */       localTransaction.setAmount(localCurrency);
/* 147:    */       
/* 148:    */ 
/* 149:179 */       BigDecimal localBigDecimal = paramResultSet.getBigDecimal("RunningBalance");
/* 150:180 */       localCurrency = null;
/* 151:181 */       if (localBigDecimal != null) {
/* 152:182 */         localCurrency = new Currency(paramResultSet.getBigDecimal("RunningBalance"), paramAccount.getCurrencyCode(), paramAccount.getLocale());
/* 153:    */       }
/* 154:184 */       localTransaction.setRunningBalance(localCurrency);
/* 155:    */       
/* 156:    */ 
/* 157:187 */       localTransaction.setDataClassification(paramResultSet.getString("DataClassification"));
/* 158:    */       
/* 159:189 */       localTransaction.setTransactionIndex(i++);
/* 160:    */       
/* 161:191 */       localVector.add(localTransaction);
/* 162:    */     }
/* 163:193 */     return localVector.elements();
/* 164:    */   }
/* 165:    */   
/* 166:    */   private static Enumeration a(Transactions paramTransactions, Enumeration paramEnumeration, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
/* 167:    */   {
/* 168:201 */     if (paramPagingContext == null) {
/* 169:202 */       return paramEnumeration;
/* 170:    */     }
/* 171:205 */     HashMap localHashMap = paramPagingContext.getMap();
/* 172:206 */     if (localHashMap == null) {
/* 173:207 */       return paramEnumeration;
/* 174:    */     }
/* 175:210 */     ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
/* 176:211 */     if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
/* 177:212 */       return paramEnumeration;
/* 178:    */     }
/* 179:214 */     ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
/* 180:215 */     Properties localProperties = localReportCriteria.getSearchCriteria();
/* 181:    */     
/* 182:217 */     paramTransactions = a(paramTransactions, localProperties, paramAccount.getLocale(), paramHashMap);
/* 183:    */     
/* 184:219 */     a(paramTransactions, localReportSortCriteria, paramHashMap);
/* 185:    */     
/* 186:221 */     Vector localVector = new Vector();
/* 187:222 */     for (int i = 0; i < paramTransactions.size(); i++) {
/* 188:223 */       localVector.add(paramTransactions.get(i));
/* 189:    */     }
/* 190:225 */     return localVector.elements();
/* 191:    */   }
/* 192:    */   
/* 193:    */   private static void a(Transactions paramTransactions, ReportSortCriteria paramReportSortCriteria, HashMap paramHashMap)
/* 194:    */   {
/* 195:233 */     String str = "";
/* 196:234 */     StringBuffer localStringBuffer = new StringBuffer();
/* 197:235 */     int i = 0;
/* 198:    */     
/* 199:    */ 
/* 200:238 */     int j = 0;
/* 201:    */     ReportSortCriterion localReportSortCriterion;
/* 202:239 */     for (int k = 0; k < paramReportSortCriteria.size(); k++)
/* 203:    */     {
/* 204:240 */       localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(k);
/* 205:241 */       str = localReportSortCriterion.getName();
/* 206:242 */       if ("Amount".equals(str)) {
/* 207:243 */         j = 1;
/* 208:    */       }
/* 209:    */     }
/* 210:246 */     if (j == 0) {
/* 211:247 */       return;
/* 212:    */     }
/* 213:250 */     for (k = 0; k < paramReportSortCriteria.size(); k++)
/* 214:    */     {
/* 215:251 */       i = 1;
/* 216:252 */       localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(k);
/* 217:253 */       str = localReportSortCriterion.getName();
/* 218:254 */       boolean bool = localReportSortCriterion.getAsc();
/* 219:255 */       if (k != 0) {
/* 220:256 */         localStringBuffer.append(",");
/* 221:    */       }
/* 222:258 */       if ("Amount".equals(str))
/* 223:    */       {
/* 224:259 */         if (bool) {
/* 225:260 */           localStringBuffer.append("AMOUNT");
/* 226:    */         } else {
/* 227:263 */           localStringBuffer.append("AMOUNT").append(",REVERSE");
/* 228:    */         }
/* 229:    */       }
/* 230:266 */       else if ("Date".equals(str))
/* 231:    */       {
/* 232:267 */         if (bool) {
/* 233:268 */           localStringBuffer.append("DATE");
/* 234:    */         } else {
/* 235:271 */           localStringBuffer.append("DATE").append(",REVERSE");
/* 236:    */         }
/* 237:    */       }
/* 238:274 */       else if ("Description".equals(str))
/* 239:    */       {
/* 240:275 */         if (bool) {
/* 241:276 */           localStringBuffer.append("DESCRIPTION");
/* 242:    */         } else {
/* 243:279 */           localStringBuffer.append("DESCRIPTION").append(",REVERSE");
/* 244:    */         }
/* 245:    */       }
/* 246:282 */       else if ("TransactionType".equals(str))
/* 247:    */       {
/* 248:283 */         if (bool) {
/* 249:284 */           localStringBuffer.append("TYPE").append("STRING");
/* 250:    */         } else {
/* 251:287 */           localStringBuffer.append("TYPE").append("STRING").append(",REVERSE");
/* 252:    */         }
/* 253:    */       }
/* 254:290 */       else if ("TransactionReferenceNumber".equals(str)) {
/* 255:291 */         if (bool) {
/* 256:292 */           localStringBuffer.append("REFERENCENUMBER");
/* 257:    */         } else {
/* 258:295 */           localStringBuffer.append("REFERENCENUMBER").append(",REVERSE");
/* 259:    */         }
/* 260:    */       }
/* 261:    */     }
/* 262:299 */     if (i != 0) {
/* 263:300 */       paramTransactions.setSortedBy(localStringBuffer.toString());
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private static Transactions a(Transactions paramTransactions, Properties paramProperties, Locale paramLocale, HashMap paramHashMap)
/* 268:    */   {
/* 269:309 */     String str1 = paramProperties.getProperty("MinimumAmount");
/* 270:310 */     String str2 = paramProperties.getProperty("MaximumAmount");
/* 271:311 */     if ((str1 == null) && (str2 == null)) {
/* 272:312 */       return paramTransactions;
/* 273:    */     }
/* 274:315 */     String str3 = paramProperties.getProperty("MinimumAmount");
/* 275:316 */     String str4 = paramProperties.getProperty("MaximumAmount");
/* 276:    */     Object localObject1;
/* 277:    */     Object localObject2;
/* 278:317 */     if ((str3 != null) && (str3.length() > 0))
/* 279:    */     {
/* 280:318 */       if ((str4 != null) && (str4.length() > 0))
/* 281:    */       {
/* 282:320 */         localObject1 = new Transactions();
/* 283:321 */         localObject2 = new Currency(str3, paramLocale);
/* 284:322 */         Currency localCurrency1 = new Currency(str4, paramLocale);
/* 285:323 */         BigDecimal localBigDecimal = ((Currency)localObject2).getAmountValue().negate();
/* 286:324 */         Currency localCurrency2 = new Currency(localBigDecimal, paramLocale);
/* 287:325 */         localBigDecimal = localCurrency1.getAmountValue().negate();
/* 288:326 */         Currency localCurrency3 = new Currency(localBigDecimal, paramLocale);
/* 289:327 */         for (int i = 0; i < paramTransactions.size(); i++)
/* 290:    */         {
/* 291:328 */           Transaction localTransaction = (Transaction)paramTransactions.get(i);
/* 292:    */           
/* 293:330 */           int j = localTransaction.getAmountValue().compareTo((Currency)localObject2);
/* 294:331 */           int k = localTransaction.getAmountValue().compareTo(localCurrency1);
/* 295:332 */           int m = localTransaction.getAmountValue().compareTo(localCurrency2);
/* 296:333 */           int n = localTransaction.getAmountValue().compareTo(localCurrency3);
/* 297:334 */           if (((j >= 0) && (k <= 0)) || ((m <= 0) && (n >= 0))) {
/* 298:335 */             ((Transactions)localObject1).add(localTransaction);
/* 299:    */           }
/* 300:    */         }
/* 301:338 */         return localObject1;
/* 302:    */       }
/* 303:340 */       localObject1 = "AMOUNT>=" + str3;
/* 304:341 */       str3 = '-' + str3;
/* 305:342 */       localObject2 = "AMOUNT<=" + str3;
/* 306:343 */       paramTransactions.setFilter((String)localObject1 + "," + (String)localObject2);
/* 307:344 */       return paramTransactions;
/* 308:    */     }
/* 309:346 */     if ((str4 != null) && (str4.length() > 0))
/* 310:    */     {
/* 311:347 */       localObject1 = "AMOUNT<=" + str4;
/* 312:348 */       str4 = '-' + str4;
/* 313:349 */       localObject2 = "AMOUNT>=" + str4;
/* 314:350 */       paramTransactions.setFilter((String)localObject1 + "," + (String)localObject2 + ",AND");
/* 315:351 */       return paramTransactions;
/* 316:    */     }
/* 317:354 */     return paramTransactions;
/* 318:    */   }
/* 319:    */   
/* 320:    */   private static void a(StringBuffer paramStringBuffer, PagingContext paramPagingContext, HashMap paramHashMap)
/* 321:    */   {
/* 322:364 */     ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
/* 323:365 */     ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
/* 324:    */     
/* 325:367 */     int i = 0;
/* 326:368 */     boolean bool = true;
/* 327:    */     
/* 328:370 */     int j = localReportSortCriteria.size();
/* 329:371 */     for (int k = 0; k < j; k++)
/* 330:    */     {
/* 331:372 */       ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(k);
/* 332:373 */       String str = a(localReportSortCriterion.getName());
/* 333:374 */       if (str != null)
/* 334:    */       {
/* 335:375 */         if (i == 0)
/* 336:    */         {
/* 337:376 */           paramStringBuffer.append(" ORDER BY ");
/* 338:377 */           i = 1;
/* 339:    */         }
/* 340:379 */         paramStringBuffer.append(str);
/* 341:380 */         bool = localReportSortCriterion.getAsc();
/* 342:382 */         if (bool) {
/* 343:383 */           paramStringBuffer.append(" ASC");
/* 344:    */         } else {
/* 345:385 */           paramStringBuffer.append(" DESC");
/* 346:    */         }
/* 347:388 */         if (k < j - 1) {
/* 348:389 */           paramStringBuffer.append(", ");
/* 349:    */         }
/* 350:    */       }
/* 351:    */     }
/* 352:    */   }
/* 353:    */   
/* 354:    */   private static String a(String paramString)
/* 355:    */   {
/* 356:400 */     if ("Date".equals(paramString)) {
/* 357:401 */       return new String("TransactionDate");
/* 358:    */     }
/* 359:402 */     if ("Description".equals(paramString)) {
/* 360:403 */       return new String("Memo");
/* 361:    */     }
/* 362:404 */     if ("TransactionType".equals(paramString)) {
/* 363:405 */       return new String("Description");
/* 364:    */     }
/* 365:406 */     if ("TransactionReferenceNumber".equals(paramString)) {
/* 366:407 */       return new String("ReferenceNumber");
/* 367:    */     }
/* 368:408 */     if ("RunningBalance".equals(paramString)) {
/* 369:409 */       return new String("RunningBalance");
/* 370:    */     }
/* 371:411 */     return null;
/* 372:    */   }
/* 373:    */   
/* 374:    */   private static void a(StringBuffer paramStringBuffer, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
/* 375:    */   {
/* 376:420 */     String str1 = "";
/* 377:422 */     if (paramPagingContext == null) {
/* 378:423 */       return;
/* 379:    */     }
/* 380:426 */     HashMap localHashMap = paramPagingContext.getMap();
/* 381:427 */     if (localHashMap == null) {
/* 382:428 */       return;
/* 383:    */     }
/* 384:431 */     ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
/* 385:432 */     if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
/* 386:433 */       return;
/* 387:    */     }
/* 388:436 */     Properties localProperties = localReportCriteria.getSearchCriteria();
/* 389:438 */     if (localProperties == null) {
/* 390:439 */       return;
/* 391:    */     }
/* 392:443 */     String str2 = localProperties.getProperty("Account");
/* 393:444 */     String str3 = localProperties.getProperty("RoutingNum");
/* 394:445 */     if ((str2 != null) && (str2.length() != 0)) {
/* 395:446 */       paramStringBuffer.append(" and b.AccountID = ? ");
/* 396:    */     }
/* 397:448 */     if ((str3 != null) && (str3.length() != 0)) {
/* 398:449 */       paramStringBuffer.append(" and a.RoutingNum = ? ");
/* 399:    */     }
/* 400:454 */     str1 = localProperties.getProperty("StartDate");
/* 401:455 */     if ((str1 != null) && (str1.length() > 0)) {
/* 402:456 */       paramStringBuffer.append(" and b.TransactionDate >= ? ");
/* 403:    */     }
/* 404:460 */     str1 = localProperties.getProperty("EndDate");
/* 405:461 */     if ((str1 != null) && (str1.length() > 0)) {
/* 406:462 */       paramStringBuffer.append(" and b.TransactionDate <= ? ");
/* 407:    */     }
/* 408:466 */     str1 = localProperties.getProperty("TransactionReferenceNumber");
/* 409:467 */     if ((str1 != null) && (str1.length() > 0)) {
/* 410:468 */       paramStringBuffer.append(" and b.ReferenceNumber = ?");
/* 411:    */     }
/* 412:472 */     str1 = localProperties.getProperty("TransactionType");
/* 413:473 */     if ((str1 != null) && (str1.length() > 0) && (str1.indexOf("AllTransactionTypes") == -1)) {
/* 414:474 */       paramStringBuffer.append(" and b.TransactionTypeID =? ");
/* 415:    */     }
/* 416:478 */     str1 = localProperties.getProperty("Description");
/* 417:479 */     if ((str1 != null) && (str1.length() > 0)) {
/* 418:480 */       paramStringBuffer.append(" and b.Memo LIKE '%" + DBUtil.escapeSQLStringLiteral(str1) + "%'");
/* 419:    */     }
/* 420:    */   }
/* 421:    */   
/* 422:    */   private static int a(PreparedStatement paramPreparedStatement, int paramInt, Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
/* 423:    */     throws Exception
/* 424:    */   {
/* 425:490 */     String str1 = "";
/* 426:492 */     if (paramPagingContext == null) {
/* 427:493 */       return paramInt;
/* 428:    */     }
/* 429:496 */     HashMap localHashMap = paramPagingContext.getMap();
/* 430:497 */     if (localHashMap == null) {
/* 431:498 */       return paramInt;
/* 432:    */     }
/* 433:501 */     ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
/* 434:502 */     if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
/* 435:503 */       return paramInt;
/* 436:    */     }
/* 437:506 */     Properties localProperties = localReportCriteria.getSearchCriteria();
/* 438:508 */     if (localProperties == null) {
/* 439:509 */       return paramInt;
/* 440:    */     }
/* 441:513 */     String str2 = localProperties.getProperty("Account");
/* 442:514 */     String str3 = localProperties.getProperty("RoutingNum");
/* 443:515 */     if ((str2 != null) && (str2.length() != 0)) {
/* 444:516 */       paramPreparedStatement.setString(paramInt++, str2);
/* 445:    */     }
/* 446:518 */     if ((str3 != null) && (str3.length() != 0)) {
/* 447:519 */       paramPreparedStatement.setString(paramInt++, str3);
/* 448:    */     }
/* 449:523 */     str1 = localProperties.getProperty("StartDate");
/* 450:    */     DateFormat localDateFormat;
/* 451:    */     Date localDate;
/* 452:    */     long l;
/* 453:524 */     if ((str1 != null) && (str1.length() > 0))
/* 454:    */     {
/* 455:526 */       localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
/* 456:527 */       localDate = localDateFormat.parse(str1);
/* 457:528 */       l = localDate.getTime();
/* 458:529 */       paramPreparedStatement.setLong(paramInt++, l);
/* 459:    */     }
/* 460:533 */     str1 = localProperties.getProperty("EndDate");
/* 461:534 */     if ((str1 != null) && (str1.length() > 0))
/* 462:    */     {
/* 463:535 */       localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
/* 464:536 */       localDate = localDateFormat.parse(str1);
/* 465:537 */       l = localDate.getTime();
/* 466:538 */       paramPreparedStatement.setLong(paramInt++, l);
/* 467:    */     }
/* 468:542 */     str1 = localProperties.getProperty("TransactionReferenceNumber");
/* 469:    */     try
/* 470:    */     {
/* 471:544 */       if ((str1 != null) && (str1.length() > 0))
/* 472:    */       {
/* 473:545 */         int i = Integer.parseInt(str1);
/* 474:546 */         paramPreparedStatement.setInt(paramInt++, i);
/* 475:    */       }
/* 476:    */     }
/* 477:    */     catch (Exception localException)
/* 478:    */     {
/* 479:549 */       throw new BSException(1080);
/* 480:    */     }
/* 481:553 */     str1 = localProperties.getProperty("TransactionType");
/* 482:554 */     if ((str1 != null) && (str1.length() > 0) && (str1.indexOf("AllTransactionTypes") == -1))
/* 483:    */     {
/* 484:555 */       int j = Integer.parseInt(str1);
/* 485:556 */       paramPreparedStatement.setInt(paramInt++, j);
/* 486:    */     }
/* 487:559 */     return paramInt;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public static final void openPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap, DBConnection paramDBConnection)
/* 491:    */     throws BSException
/* 492:    */   {
/* 493:573 */     Enumeration localEnumeration = getTransactions(paramAccount, paramPagingContext, paramHashMap, paramDBConnection);
/* 494:    */     
/* 495:575 */     ArrayList localArrayList = new ArrayList();
/* 496:576 */     int i = 0;
/* 497:577 */     while (localEnumeration.hasMoreElements())
/* 498:    */     {
/* 499:578 */       localArrayList.add(localEnumeration.nextElement());
/* 500:579 */       i++;
/* 501:    */     }
/* 502:585 */     synchronized (jdField_int)
/* 503:    */     {
/* 504:586 */       jdField_int.put(paramAccount, localArrayList);
/* 505:587 */       jdField_do.put(paramAccount, new Integer(i));
/* 506:588 */       jdField_new.put(paramAccount, Boolean.TRUE);
/* 507:589 */       jdField_if.put(paramAccount, new Integer(i));
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public static final void openPagedTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, DBConnection paramDBConnection)
/* 512:    */     throws BSException
/* 513:    */   {
/* 514:606 */     PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
/* 515:607 */     ReportCriteria localReportCriteria = new ReportCriteria();
/* 516:608 */     HashMap localHashMap1 = new HashMap();
/* 517:609 */     localPagingContext.setMap(localHashMap1);
/* 518:610 */     localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
/* 519:611 */     DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
/* 520:612 */     if (paramCalendar1 != null) {
/* 521:613 */       localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
/* 522:    */     }
/* 523:615 */     if (paramCalendar2 != null) {
/* 524:616 */       localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
/* 525:    */     }
/* 526:618 */     HashMap localHashMap2 = new HashMap();
/* 527:    */     
/* 528:620 */     openPagedTransactions(paramAccount, localPagingContext, localHashMap2, paramDBConnection);
/* 529:    */   }
/* 530:    */   
/* 531:    */   public static void closePagedTransactions(Account paramAccount)
/* 532:    */     throws BSException
/* 533:    */   {
/* 534:631 */     synchronized (jdField_int)
/* 535:    */     {
/* 536:633 */       if (!jdField_int.containsKey(paramAccount)) {
/* 537:634 */         throw new BSException(1013, MessageText.getMessage("ERR_ACCOUNT_NOT_PAGED"));
/* 538:    */       }
/* 539:638 */       jdField_int.remove(paramAccount);
/* 540:639 */       jdField_do.remove(paramAccount);
/* 541:640 */       jdField_if.remove(paramAccount);
/* 542:641 */       jdField_new.remove(paramAccount);
/* 543:    */     }
/* 544:    */   }
/* 545:    */   
/* 546:    */   public static int getNumberOfTransactions(Account paramAccount)
/* 547:    */     throws BSException
/* 548:    */   {
/* 549:653 */     if (!jdField_if.containsKey(paramAccount)) {
/* 550:654 */       throw new BSException(1013, MessageText.getMessage("ERR_ACCOUNT_NOT_PAGED"));
/* 551:    */     }
/* 552:658 */     Integer localInteger = (Integer)jdField_if.get(paramAccount);
/* 553:659 */     return localInteger.intValue();
/* 554:    */   }
/* 555:    */   
/* 556:    */   public static Enumeration getNextPage(Account paramAccount, int paramInt)
/* 557:    */     throws BSException
/* 558:    */   {
/* 559:671 */     return getNextPage(paramAccount, paramInt, -1);
/* 560:    */   }
/* 561:    */   
/* 562:    */   public static Enumeration getNextPage(Account paramAccount, int paramInt1, int paramInt2)
/* 563:    */     throws BSException
/* 564:    */   {
/* 565:686 */     Vector localVector = new Vector();
/* 566:688 */     synchronized (jdField_int)
/* 567:    */     {
/* 568:690 */       if (!jdField_int.containsKey(paramAccount)) {
/* 569:691 */         throw new BSException(1013, MessageText.getMessage("ERR_ACCOUNT_NOT_PAGED"));
/* 570:    */       }
/* 571:695 */       int i = paramInt2 == -1 ? ((Integer)jdField_do.get(paramAccount)).intValue() : paramInt2;
/* 572:    */       
/* 573:697 */       int j = i;
/* 574:698 */       ArrayList localArrayList = (ArrayList)jdField_int.get(paramAccount);
/* 575:702 */       for (; (j < i + paramInt1) && (j >= 0) && (j < localArrayList.size()); j++) {
/* 576:703 */         localVector.add(localArrayList.get(j));
/* 577:    */       }
/* 578:707 */       jdField_do.put(paramAccount, new Integer(j));
/* 579:    */       
/* 580:    */ 
/* 581:710 */       jdField_new.put(paramAccount, Boolean.TRUE);
/* 582:    */     }
/* 583:713 */     return localVector.elements();
/* 584:    */   }
/* 585:    */   
/* 586:    */   public static Enumeration getPrevPage(Account paramAccount, int paramInt)
/* 587:    */     throws BSException
/* 588:    */   {
/* 589:725 */     return getPrevPage(paramAccount, paramInt, -1);
/* 590:    */   }
/* 591:    */   
/* 592:    */   public static Enumeration getPrevPage(Account paramAccount, int paramInt1, int paramInt2)
/* 593:    */     throws BSException
/* 594:    */   {
/* 595:740 */     Vector localVector = new Vector();
/* 596:742 */     synchronized (jdField_int)
/* 597:    */     {
/* 598:744 */       if (!jdField_int.containsKey(paramAccount)) {
/* 599:745 */         throw new BSException(1013, MessageText.getMessage("ERR_ACCOUNT_NOT_PAGED"));
/* 600:    */       }
/* 601:749 */       int i = paramInt2 == -1 ? ((Integer)jdField_do.get(paramAccount)).intValue() : paramInt2;
/* 602:    */       
/* 603:751 */       int j = i - 1;
/* 604:752 */       ArrayList localArrayList = (ArrayList)jdField_int.get(paramAccount);
/* 605:756 */       for (; (j > i - paramInt1 - 1) && (j >= 0) && (j < localArrayList.size()); j--) {
/* 606:757 */         localVector.add(localArrayList.get(j));
/* 607:    */       }
/* 608:761 */       jdField_do.put(paramAccount, new Integer(j + 1));
/* 609:    */       
/* 610:    */ 
/* 611:764 */       jdField_new.put(paramAccount, Boolean.TRUE);
/* 612:    */     }
/* 613:767 */     return localVector.elements();
/* 614:    */   }
/* 615:    */   
/* 616:    */   public static void closeUnusedPagedTransactions()
/* 617:    */   {
/* 618:776 */     synchronized (jdField_int)
/* 619:    */     {
/* 620:777 */       Iterator localIterator = jdField_new.keySet().iterator();
/* 621:779 */       while (localIterator.hasNext())
/* 622:    */       {
/* 623:780 */         Account localAccount = (Account)localIterator.next();
/* 624:781 */         Boolean localBoolean = (Boolean)jdField_new.get(localAccount);
/* 625:783 */         if (!localBoolean.booleanValue()) {
/* 626:    */           try
/* 627:    */           {
/* 628:786 */             closePagedTransactions(localAccount);
/* 629:    */           }
/* 630:    */           catch (Exception localException) {}
/* 631:    */         }
/* 632:796 */         jdField_new.put(localAccount, Boolean.FALSE);
/* 633:    */       }
/* 634:    */     }
/* 635:    */   }
/* 636:    */   
/* 637:    */   public static void addTransaction(Account paramAccount, Transaction paramTransaction, HashMap paramHashMap)
/* 638:    */     throws Exception
/* 639:    */   {
/* 640:808 */     if ((paramAccount == null) || (paramTransaction == null)) {
/* 641:810 */       return;
/* 642:    */     }
/* 643:812 */     Profile.isInitialized();
/* 644:813 */     Connection localConnection = null;
/* 645:814 */     PreparedStatement localPreparedStatement = null;
/* 646:    */     try
/* 647:    */     {
/* 648:817 */       localConnection = DBUtil.getConnection(Profile.getPoolName(), false, 2);
/* 649:    */       
/* 650:819 */       BigDecimal localBigDecimal1 = null;
/* 651:820 */       String str = null;
/* 652:821 */       if (paramTransaction.getAmountValue() != null)
/* 653:    */       {
/* 654:822 */         localBigDecimal1 = paramTransaction.getAmountValue().getAmountValue();
/* 655:823 */         localBigDecimal1 = localBigDecimal1.setScale(2, 6);
/* 656:824 */         str = localBigDecimal1.toString();
/* 657:    */       }
/* 658:827 */       BigDecimal localBigDecimal2 = null;
/* 659:828 */       Object localObject1 = null;
/* 660:829 */       if (paramTransaction.getRunningBalanceValue() != null)
/* 661:    */       {
/* 662:830 */         localBigDecimal2 = paramTransaction.getRunningBalanceValue().getAmountValue();
/* 663:831 */         localBigDecimal2 = localBigDecimal2.setScale(2, 6);
/* 664:    */       }
/* 665:834 */       int i = paramTransaction.getTypeValue();
/* 666:835 */       long l = paramTransaction.getDateValue().getTimeInMillis();
/* 667:    */       
/* 668:837 */       localPreparedStatement = localConnection.prepareStatement("INSERT into BS_Transactions( TransactionID, TransactionDate, TransactionTypeID, AccountID, FIID, Amount, CurrencyCode, Memo, ChequeNumber, ReferenceNumber, DataClassification, RunningBalance ) values( ?,?,?,?,?,?,?,?,?,?,?,? )");
/* 669:838 */       int j = 1;
/* 670:839 */       localPreparedStatement.setInt(j++, Integer.parseInt(paramTransaction.getID()));
/* 671:840 */       localPreparedStatement.setLong(j++, l);
/* 672:841 */       localPreparedStatement.setInt(j++, i);
/* 673:842 */       localPreparedStatement.setString(j++, paramAccount.getID());
/* 674:843 */       localPreparedStatement.setString(j++, paramAccount.getBankID());
/* 675:844 */       localPreparedStatement.setString(j++, str);
/* 676:845 */       localPreparedStatement.setString(j++, paramAccount.getCurrencyCode());
/* 677:846 */       localPreparedStatement.setString(j++, paramTransaction.getMemo());
/* 678:847 */       localPreparedStatement.setString(j++, paramTransaction.getReferenceNumber());
/* 679:848 */       localPreparedStatement.setInt(j++, Integer.parseInt(paramTransaction.getReferenceNumber()));
/* 680:849 */       localPreparedStatement.setString(j++, paramTransaction.getDataClassification());
/* 681:850 */       localPreparedStatement.setBigDecimal(j++, localBigDecimal2);
/* 682:    */       
/* 683:852 */       localPreparedStatement.executeUpdate();
/* 684:    */       
/* 685:854 */       DBUtil.commit(localConnection); return;
/* 686:    */     }
/* 687:    */     catch (Exception localException2)
/* 688:    */     {
/* 689:856 */       DBUtil.rollback(localConnection);
/* 690:    */     }
/* 691:    */     finally
/* 692:    */     {
/* 693:858 */       DBUtil.returnConnection(Profile.getPoolName(), localConnection);
/* 694:    */       try
/* 695:    */       {
/* 696:860 */         localPreparedStatement.close();
/* 697:    */       }
/* 698:    */       catch (Exception localException4) {}
/* 699:    */     }
/* 700:    */   }
/* 701:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBTransaction
 * JD-Core Version:    0.7.0.1
 */