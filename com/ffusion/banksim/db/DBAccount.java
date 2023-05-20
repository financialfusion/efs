/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSException;
/*   4:    */ import com.ffusion.beans.Balance;
/*   5:    */ import com.ffusion.beans.Bank;
/*   6:    */ import com.ffusion.beans.Currency;
/*   7:    */ import com.ffusion.beans.DateTime;
/*   8:    */ import com.ffusion.beans.accounts.Account;
/*   9:    */ import com.ffusion.beans.accounts.Accounts;
/*  10:    */ import com.ffusion.beans.user.User;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.util.Enumeration;
/*  14:    */ import java.util.Vector;
/*  15:    */ 
/*  16:    */ public class DBAccount
/*  17:    */ {
/*  18:    */   private static final String jdField_int = "select AccountID from BS_Account where AccountNumber=?";
/*  19:    */   private static final String jdField_if = "select AccountNumber from BS_Account where AccountNumber=?";
/*  20:    */   private static final String jdField_goto = "select Balance from BS_Account where AccountNumber=?";
/*  21:    */   private static final String jdField_else = "select FIID from BS_FI where FIID=?";
/*  22:    */   private static final String jdField_char = "insert into BS_Account( AccountID, AccountName, AccountTypeID, CurrencyCode, FIID, AccountNumber, Balance, Status, Type, LastUpdated, Description, PrimaryAccount, CoreAccount, PersonalAccount, PositivePay, RoutingNum, BICNum ) values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
/*  23:    */   private static final String jdField_new = "insert into BS_CustomerAccount( CustomerID, AccountID ) values( ?,? )";
/*  24:    */   private static final String jdField_try = "Select BS_Account.AccountID, BS_Account.AccountName, BS_Account.AccountTypeID, BS_Account.CurrencyCode, BS_Account.FIID, BS_Account.AccountNumber, BS_Account.Balance, BS_Account.Status, BS_Account.Type, BS_Account.LastUpdated, BS_Account.Description, BS_Account.PrimaryAccount, BS_Account.CoreAccount, BS_Account.PersonalAccount, BS_Account.PositivePay, BS_Account.RoutingNum, BS_Account.BICNum, BS_FI.Name, BS_Account.StmtFlag FROM BS_Account, BS_FI where BS_FI.FIID = BS_Account.FIID AND AccountID in (Select AccountID from BS_CustomerAccount where CustomerID in (Select CustomerID from BS_Customer where UserID = ? and Password = ?))";
/*  25:    */   private static final String jdField_byte = "Select BS_Account.AccountName, BS_Account.AccountTypeID,  BS_Account.CurrencyCode, BS_Account.FIID, BS_Account.AccountID, BS_Account.Balance, BS_Account.Status, BS_Account.Type, BS_Account.LastUpdated, BS_Account.Description, BS_Account.PrimaryAccount, BS_Account.CoreAccount, BS_Account.PersonalAccount, BS_Account.PositivePay, BS_Account.RoutingNum, BS_Account.BICNum, BS_FI.Name, BS_Account.StmtFlag from BS_Account, BS_FI where BS_FI.FIID = BS_Account.FIID and AccountNumber = ? ";
/*  26:    */   private static final String a = "Update BS_Account set LastUpdated = ?, Balance = ? where AccountID = ?";
/*  27:    */   private static final String jdField_for = "Delete from BS_Account where AccountID = ?";
/*  28:    */   private static final String jdField_case = "Update BS_Transactions set DestAccountID = NULL where DestAccountID = ?";
/*  29:    */   private static final String jdField_do = "Update BS_Account set AccountNumber = ?, AccountName = ?, AccountTypeID = ?, CurrencyCode = ?, Status = ?, LastUpdated = ?, Balance = ?, Description = ? PrimaryAccount = ?, CoreAccount = ?, PersonalAccount = ?, PositivePay = ? RoutingNum = ?, BICNum = ? where AccountID = ?";
/*  30:    */   public static final String stmtFlagKey = "STMT_FLAG";
/*  31:    */   
/*  32:    */   static final boolean a(Account paramAccount, DBConnection paramDBConnection)
/*  33:    */   {
/*  34:102 */     DBResultSet localDBResultSet = null;
/*  35:    */     try
/*  36:    */     {
/*  37:105 */       localDBResultSet = paramDBConnection.prepareQuery("select AccountID from BS_Account where AccountNumber=?");
/*  38:106 */       Object[] arrayOfObject = { paramAccount.getNumber() };
/*  39:107 */       localDBResultSet.open(arrayOfObject);
/*  40:108 */       if (localDBResultSet.getNextRow())
/*  41:    */       {
/*  42:109 */         localDBResultSet.close();
/*  43:110 */         return true;
/*  44:    */       }
/*  45:112 */       localDBResultSet.close();
/*  46:113 */       return false;
/*  47:    */     }
/*  48:    */     catch (Exception localException1)
/*  49:    */     {
/*  50:    */       try
/*  51:    */       {
/*  52:117 */         localDBResultSet.close();
/*  53:    */       }
/*  54:    */       catch (Exception localException2) {}
/*  55:    */     }
/*  56:120 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static final void updateBalance(Account paramAccount, BigDecimal paramBigDecimal, DBConnection paramDBConnection)
/*  60:    */     throws SQLException
/*  61:    */   {
/*  62:136 */     boolean bool = paramDBConnection.isAutoCommit();
/*  63:137 */     if (bool) {
/*  64:137 */       paramDBConnection.setAutoCommit(false);
/*  65:    */     }
/*  66:139 */     Object[] arrayOfObject = { new Long(System.currentTimeMillis()), paramBigDecimal.toString(), paramAccount.getID() };
/*  67:    */     
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:145 */     paramDBConnection.executeUpdate("Update BS_Account set LastUpdated = ?, Balance = ? where AccountID = ?", arrayOfObject);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static final BigDecimal getCurrentBalance(Account paramAccount, DBConnection paramDBConnection)
/*  76:    */   {
/*  77:155 */     DBResultSet localDBResultSet = null;
/*  78:    */     try
/*  79:    */     {
/*  80:158 */       localDBResultSet = paramDBConnection.prepareQuery("select Balance from BS_Account where AccountNumber=?");
/*  81:159 */       Object[] arrayOfObject = { paramAccount.getNumber() };
/*  82:160 */       localDBResultSet.open(arrayOfObject);
/*  83:161 */       localDBResultSet.getNextRow();
/*  84:    */       
/*  85:163 */       BigDecimal localBigDecimal = new BigDecimal(localDBResultSet.getColumnString(1));
/*  86:    */       
/*  87:165 */       localDBResultSet.close();
/*  88:    */       
/*  89:167 */       return localBigDecimal;
/*  90:    */     }
/*  91:    */     catch (Exception localException1)
/*  92:    */     {
/*  93:    */       try
/*  94:    */       {
/*  95:171 */         localDBResultSet.close();
/*  96:    */       }
/*  97:    */       catch (Exception localException2) {}
/*  98:    */     }
/*  99:174 */     return new BigDecimal("0");
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static final void addAccount(User paramUser, Account paramAccount, DBConnection paramDBConnection, boolean paramBoolean)
/* 103:    */     throws BSException
/* 104:    */   {
/* 105:191 */     boolean bool = false;
/* 106:    */     try
/* 107:    */     {
/* 108:196 */       bool = paramDBConnection.isAutoCommit();
/* 109:197 */       if (bool) {
/* 110:197 */         paramDBConnection.setAutoCommit(false);
/* 111:    */       }
/* 112:202 */       if (a(paramAccount, paramDBConnection)) {
/* 113:203 */         throw new BSException(1002, MessageText.getMessage("ERR_ACCOUNT_EXISTS"));
/* 114:    */       }
/* 115:208 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("select FIID from BS_FI where FIID=?");
/* 116:209 */       Object[] arrayOfObject1 = { paramAccount.getBankID() };
/* 117:210 */       localDBResultSet.open(arrayOfObject1);
/* 118:211 */       int i = 0;
/* 119:212 */       while (localDBResultSet.getNextRow()) {
/* 120:213 */         i = 1;
/* 121:    */       }
/* 122:216 */       localDBResultSet.close();
/* 123:218 */       if ((i == 0) && (!paramBoolean)) {
/* 124:219 */         throw new BSException(1005, MessageText.getMessage("ERR_ACC_FI_NOT_EXIST"));
/* 125:    */       }
/* 126:    */       Object localObject1;
/* 127:221 */       if (i == 0)
/* 128:    */       {
/* 129:224 */         localObject1 = new Bank();
/* 130:225 */         ((Bank)localObject1).setID(paramAccount.getBankID());
/* 131:226 */         ((Bank)localObject1).setName(paramAccount.getBankID());
/* 132:    */         
/* 133:228 */         DBFinancialInstitution.addBank((Bank)localObject1, paramDBConnection);
/* 134:    */       }
/* 135:233 */       if ((paramAccount.getCurrentBalance() == null) || (paramAccount.getCurrentBalance().getAmount() == null))
/* 136:    */       {
/* 137:235 */         localObject1 = new BigDecimal("0.00");
/* 138:    */       }
/* 139:    */       else
/* 140:    */       {
/* 141:238 */         localObject1 = paramAccount.getCurrentBalance().getAmountValue().getAmountValue();
/* 142:239 */         localObject1 = ((BigDecimal)localObject1).setScale(2, 6);
/* 143:    */       }
/* 144:    */       String str;
/* 145:248 */       if (paramAccount.getNumber() != null)
/* 146:    */       {
/* 147:249 */         str = paramAccount.getNumber();
/* 148:    */       }
/* 149:    */       else
/* 150:    */       {
/* 151:251 */         int j = paramAccount.getID().lastIndexOf("-");
/* 152:252 */         if (j != -1) {
/* 153:253 */           str = paramAccount.getID().substring(0, j);
/* 154:    */         } else {
/* 155:255 */           str = paramAccount.getID();
/* 156:    */         }
/* 157:    */       }
/* 158:258 */       if (paramAccount.getBicAccount() == null) {
/* 159:259 */         paramAccount.setBicAccount("1234");
/* 160:    */       }
/* 161:260 */       if (paramAccount.getRoutingNum() == null) {
/* 162:261 */         paramAccount.setRoutingNum(paramAccount.getBankID());
/* 163:    */       }
/* 164:263 */       Object[] arrayOfObject2 = { paramAccount.getID(), paramAccount.getNickName(), new Integer(paramAccount.getTypeValue()), paramAccount.getCurrencyCode(), paramAccount.getBankID(), str, ((BigDecimal)localObject1).toString(), new Integer(paramAccount.getStatus()), paramAccount.getType(), new Long(System.currentTimeMillis()), "", paramAccount.getPrimaryAccount(), paramAccount.getCoreAccount(), paramAccount.getPersonalAccount(), paramAccount.getPositivePay(), paramAccount.getRoutingNum(), paramAccount.getBicAccount() };
/* 165:    */       
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:286 */       paramDBConnection.executeUpdate("insert into BS_Account( AccountID, AccountName, AccountTypeID, CurrencyCode, FIID, AccountNumber, Balance, Status, Type, LastUpdated, Description, PrimaryAccount, CoreAccount, PersonalAccount, PositivePay, RoutingNum, BICNum ) values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )", arrayOfObject2);
/* 188:    */       
/* 189:288 */       Object[] arrayOfObject3 = { paramUser.getId(), paramAccount.getID() };
/* 190:    */       
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:293 */       paramDBConnection.executeUpdate("insert into BS_CustomerAccount( CustomerID, AccountID ) values( ?,? )", arrayOfObject3);
/* 195:    */       
/* 196:295 */       paramDBConnection.commit();
/* 197:    */     }
/* 198:    */     catch (SQLException localSQLException1)
/* 199:    */     {
/* 200:    */       try
/* 201:    */       {
/* 202:300 */         paramDBConnection.rollback();
/* 203:    */       }
/* 204:    */       catch (SQLException localSQLException2) {}
/* 205:305 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 206:    */     }
/* 207:    */     finally
/* 208:    */     {
/* 209:    */       try
/* 210:    */       {
/* 211:310 */         if (bool) {
/* 212:310 */           paramDBConnection.setAutoCommit(true);
/* 213:    */         }
/* 214:    */       }
/* 215:    */       catch (SQLException localSQLException3)
/* 216:    */       {
/* 217:312 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 218:    */       }
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static final Enumeration getAccounts(User paramUser, DBConnection paramDBConnection)
/* 223:    */     throws BSException
/* 224:    */   {
/* 225:329 */     Enumeration localEnumeration = null;
/* 226:    */     try
/* 227:    */     {
/* 228:331 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("Select BS_Account.AccountID, BS_Account.AccountName, BS_Account.AccountTypeID, BS_Account.CurrencyCode, BS_Account.FIID, BS_Account.AccountNumber, BS_Account.Balance, BS_Account.Status, BS_Account.Type, BS_Account.LastUpdated, BS_Account.Description, BS_Account.PrimaryAccount, BS_Account.CoreAccount, BS_Account.PersonalAccount, BS_Account.PositivePay, BS_Account.RoutingNum, BS_Account.BICNum, BS_FI.Name, BS_Account.StmtFlag FROM BS_Account, BS_FI where BS_FI.FIID = BS_Account.FIID AND AccountID in (Select AccountID from BS_CustomerAccount where CustomerID in (Select CustomerID from BS_Customer where UserID = ? and Password = ?))");
/* 229:332 */       Object[] arrayOfObject = { paramUser.getUserName(), paramUser.getPassword() };
/* 230:333 */       localDBResultSet.open(arrayOfObject);
/* 231:    */       
/* 232:335 */       Vector localVector = new Vector();
/* 233:    */       
/* 234:337 */       Accounts localAccounts = new Accounts();
/* 235:338 */       Account localAccount = null;
/* 236:339 */       Balance localBalance = null;
/* 237:340 */       Currency localCurrency = null;
/* 238:341 */       DateTime localDateTime = null;
/* 239:342 */       while (localDBResultSet.getNextRow())
/* 240:    */       {
/* 241:343 */         localAccount = localAccounts.create(localDBResultSet.getColumnString(5), localDBResultSet.getColumnString(1), localDBResultSet.getColumnString(6), localDBResultSet.getColumnInt(3));
/* 242:344 */         localAccount.setAccountGroup(a(localDBResultSet.getColumnInt(3)));
/* 243:345 */         localAccount.setCurrencyCode(localDBResultSet.getColumnString(4));
/* 244:346 */         localAccount.setNickName(localDBResultSet.getColumnString(2));
/* 245:347 */         localAccount.setStatus(localDBResultSet.getColumnInt(8));
/* 246:    */         
/* 247:349 */         localBalance = new Balance();
/* 248:350 */         localCurrency = new Currency();
/* 249:351 */         localCurrency.setAmount(new BigDecimal(localDBResultSet.getColumnString(7)));
/* 250:352 */         localCurrency.setCurrencyCode(localAccount.getCurrencyCode());
/* 251:353 */         localBalance.setAmount(localCurrency);
/* 252:    */         
/* 253:355 */         localDateTime = new DateTime();
/* 254:356 */         localDateTime.setDate(localDBResultSet.getColumnString(10));
/* 255:357 */         localBalance.setDate(localDateTime);
/* 256:358 */         localAccount.setCurrentBalance(localBalance);
/* 257:359 */         localAccount.setAvailableBalance(localBalance);
/* 258:    */         
/* 259:    */ 
/* 260:362 */         localAccount.setPrimaryAccount(localDBResultSet.getColumnString(12));
/* 261:363 */         localAccount.setCoreAccount(localDBResultSet.getColumnString(13));
/* 262:364 */         localAccount.setPersonalAccount(localDBResultSet.getColumnString(14));
/* 263:365 */         localAccount.setPositivePay(localDBResultSet.getColumnString(15));
/* 264:366 */         localAccount.setRoutingNum(localDBResultSet.getColumnString(16));
/* 265:367 */         localAccount.setBicAccount(localDBResultSet.getColumnString(17));
/* 266:    */         
/* 267:369 */         localAccount.setBankName(localDBResultSet.getColumnString(18));
/* 268:    */         
/* 269:    */ 
/* 270:372 */         localAccount.set("STMT_FLAG", localDBResultSet.getColumnString(19));
/* 271:    */         
/* 272:    */ 
/* 273:375 */         a(localAccount);
/* 274:    */         
/* 275:377 */         localVector.add(localAccount);
/* 276:    */       }
/* 277:379 */       localDBResultSet.close();
/* 278:380 */       localEnumeration = localVector.elements();
/* 279:    */     }
/* 280:    */     catch (SQLException localSQLException)
/* 281:    */     {
/* 282:382 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException));
/* 283:    */     }
/* 284:385 */     return localEnumeration;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public static final Account getAccount(Account paramAccount, DBConnection paramDBConnection)
/* 288:    */     throws BSException
/* 289:    */   {
/* 290:396 */     Object localObject = null;
/* 291:    */     try
/* 292:    */     {
/* 293:399 */       if (!a(paramAccount, paramDBConnection)) {
/* 294:400 */         throw new BSException(1011, MessageText.getMessage("ERR_ACCOUNT_NOT_EXISTS"));
/* 295:    */       }
/* 296:404 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("Select BS_Account.AccountName, BS_Account.AccountTypeID,  BS_Account.CurrencyCode, BS_Account.FIID, BS_Account.AccountID, BS_Account.Balance, BS_Account.Status, BS_Account.Type, BS_Account.LastUpdated, BS_Account.Description, BS_Account.PrimaryAccount, BS_Account.CoreAccount, BS_Account.PersonalAccount, BS_Account.PositivePay, BS_Account.RoutingNum, BS_Account.BICNum, BS_FI.Name, BS_Account.StmtFlag from BS_Account, BS_FI where BS_FI.FIID = BS_Account.FIID and AccountNumber = ? ");
/* 297:405 */       Object[] arrayOfObject = { paramAccount.getNumber() };
/* 298:406 */       localDBResultSet.open(arrayOfObject);
/* 299:    */       
/* 300:408 */       Balance localBalance = null;
/* 301:409 */       Currency localCurrency = null;
/* 302:410 */       DateTime localDateTime = null;
/* 303:    */       
/* 304:    */ 
/* 305:413 */       localDBResultSet.getNextRow();
/* 306:414 */       paramAccount.setID(localDBResultSet.getColumnString(5));
/* 307:415 */       paramAccount.setType(localDBResultSet.getColumnInt(2));
/* 308:416 */       paramAccount.setCurrencyCode(localDBResultSet.getColumnString(3));
/* 309:417 */       paramAccount.setBankID(localDBResultSet.getColumnString(4));
/* 310:418 */       paramAccount.setNickName(localDBResultSet.getColumnString(1));
/* 311:419 */       paramAccount.setStatus(localDBResultSet.getColumnInt(7));
/* 312:    */       
/* 313:421 */       localBalance = new Balance();
/* 314:422 */       localCurrency = new Currency();
/* 315:423 */       localCurrency.setAmount(new BigDecimal(localDBResultSet.getColumnString(6)));
/* 316:424 */       localCurrency.setCurrencyCode(paramAccount.getCurrencyCode());
/* 317:425 */       localBalance.setAmount(localCurrency);
/* 318:    */       
/* 319:427 */       localDateTime = new DateTime();
/* 320:428 */       localDateTime.setDate(localDBResultSet.getColumnString(9));
/* 321:429 */       localBalance.setDate(localDateTime);
/* 322:430 */       paramAccount.setCurrentBalance(localBalance);
/* 323:431 */       paramAccount.setAvailableBalance(localBalance);
/* 324:    */       
/* 325:    */ 
/* 326:434 */       paramAccount.setPrimaryAccount(localDBResultSet.getColumnString(11));
/* 327:435 */       paramAccount.setCoreAccount(localDBResultSet.getColumnString(12));
/* 328:436 */       paramAccount.setPersonalAccount(localDBResultSet.getColumnString(13));
/* 329:437 */       paramAccount.setPositivePay(localDBResultSet.getColumnString(14));
/* 330:438 */       paramAccount.setRoutingNum(localDBResultSet.getColumnString(15));
/* 331:439 */       paramAccount.setBicAccount(localDBResultSet.getColumnString(16));
/* 332:    */       
/* 333:441 */       paramAccount.setBankName(localDBResultSet.getColumnString(17));
/* 334:    */       
/* 335:    */ 
/* 336:444 */       paramAccount.set("STMT_FLAG", localDBResultSet.getColumnString(18));
/* 337:    */       
/* 338:    */ 
/* 339:447 */       a(paramAccount);
/* 340:    */       
/* 341:449 */       localDBResultSet.close();
/* 342:    */     }
/* 343:    */     catch (SQLException localSQLException)
/* 344:    */     {
/* 345:451 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException));
/* 346:    */     }
/* 347:454 */     return paramAccount;
/* 348:    */   }
/* 349:    */   
/* 350:    */   private static final void a(Account paramAccount)
/* 351:    */   {
/* 352:458 */     int i = paramAccount.getTypeValue();
/* 353:    */     
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:466 */     paramAccount.setFilterable("Transactions");
/* 361:468 */     if (i == 1)
/* 362:    */     {
/* 363:470 */       paramAccount.setFilterable("TransferTo");
/* 364:471 */       paramAccount.setFilterable("TransferFrom");
/* 365:472 */       paramAccount.setFilterable("BillPay");
/* 366:    */     }
/* 367:474 */     else if (i == 2)
/* 368:    */     {
/* 369:476 */       paramAccount.setFilterable("TransferTo");
/* 370:477 */       paramAccount.setFilterable("TransferFrom");
/* 371:    */     }
/* 372:479 */     else if (i == 3)
/* 373:    */     {
/* 374:483 */       paramAccount.setFilterable("TransferTo");
/* 375:484 */       paramAccount.setFilterable("TransferFrom");
/* 376:    */     }
/* 377:    */     else
/* 378:    */     {
/* 379:488 */       paramAccount.setFilterable("TransferTo");
/* 380:489 */       paramAccount.setFilterable("TransferFrom");
/* 381:490 */       paramAccount.setFilterable("BillPay");
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   public static final void deleteAccount(Account paramAccount, DBConnection paramDBConnection)
/* 386:    */     throws BSException
/* 387:    */   {
/* 388:502 */     if (!a(paramAccount, paramDBConnection)) {
/* 389:503 */       throw new BSException(1011, MessageText.getMessage("ERR_ACCOUNT_NOT_EXISTS"));
/* 390:    */     }
/* 391:506 */     boolean bool = false;
/* 392:    */     try
/* 393:    */     {
/* 394:511 */       bool = paramDBConnection.isAutoCommit();
/* 395:512 */       if (bool) {
/* 396:512 */         paramDBConnection.setAutoCommit(false);
/* 397:    */       }
/* 398:514 */       Object[] arrayOfObject = { paramAccount.getID() };
/* 399:    */       
/* 400:    */ 
/* 401:517 */       paramDBConnection.executeUpdate("Update BS_Transactions set DestAccountID = NULL where DestAccountID = ?", arrayOfObject);
/* 402:    */       
/* 403:    */ 
/* 404:520 */       paramDBConnection.executeUpdate("Delete from BS_Account where AccountID = ?", arrayOfObject);
/* 405:    */       
/* 406:522 */       paramDBConnection.commit();
/* 407:    */     }
/* 408:    */     catch (SQLException localSQLException1)
/* 409:    */     {
/* 410:    */       try
/* 411:    */       {
/* 412:527 */         paramDBConnection.rollback();
/* 413:    */       }
/* 414:    */       catch (SQLException localSQLException2) {}
/* 415:532 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 416:    */     }
/* 417:    */     finally
/* 418:    */     {
/* 419:    */       try
/* 420:    */       {
/* 421:537 */         if (bool) {
/* 422:537 */           paramDBConnection.setAutoCommit(true);
/* 423:    */         }
/* 424:    */       }
/* 425:    */       catch (SQLException localSQLException3)
/* 426:    */       {
/* 427:539 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 428:    */       }
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   public static final void updateAccount(Account paramAccount, DBConnection paramDBConnection)
/* 433:    */     throws BSException
/* 434:    */   {
/* 435:552 */     boolean bool = false;
/* 436:    */     try
/* 437:    */     {
/* 438:557 */       bool = paramDBConnection.isAutoCommit();
/* 439:558 */       if (bool) {
/* 440:558 */         paramDBConnection.setAutoCommit(false);
/* 441:    */       }
/* 442:561 */       if (!a(paramAccount, paramDBConnection)) {
/* 443:562 */         throw new BSException(1011, MessageText.getMessage("ERR_ACCOUNT_NOT_EXISTS"));
/* 444:    */       }
/* 445:568 */       BigDecimal localBigDecimal = paramAccount.getCurrentBalance().getAmountValue().getAmountValue();
/* 446:569 */       localBigDecimal = localBigDecimal.setScale(2, 6);
/* 447:    */       
/* 448:571 */       Object[] arrayOfObject = { paramAccount.getNumber(), paramAccount.getNickName(), new Integer(paramAccount.getTypeValue()), paramAccount.getCurrencyCode(), new Integer(paramAccount.getStatus()), new Long(System.currentTimeMillis()), localBigDecimal.toString(), "", paramAccount.getPrimaryAccount(), paramAccount.getCoreAccount(), paramAccount.getPersonalAccount(), paramAccount.getPositivePay(), paramAccount.getRoutingNum(), paramAccount.getBicAccount(), paramAccount.getID() };
/* 449:    */       
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:    */ 
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:590 */       paramDBConnection.executeUpdate("Update BS_Account set AccountNumber = ?, AccountName = ?, AccountTypeID = ?, CurrencyCode = ?, Status = ?, LastUpdated = ?, Balance = ?, Description = ? PrimaryAccount = ?, CoreAccount = ?, PersonalAccount = ?, PositivePay = ? RoutingNum = ?, BICNum = ? where AccountID = ?", arrayOfObject);
/* 468:    */       
/* 469:592 */       paramDBConnection.commit();
/* 470:    */     }
/* 471:    */     catch (SQLException localSQLException1)
/* 472:    */     {
/* 473:    */       try
/* 474:    */       {
/* 475:597 */         paramDBConnection.rollback();
/* 476:    */       }
/* 477:    */       catch (SQLException localSQLException2) {}
/* 478:602 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 479:    */     }
/* 480:    */     finally
/* 481:    */     {
/* 482:    */       try
/* 483:    */       {
/* 484:607 */         if (bool) {
/* 485:607 */           paramDBConnection.setAutoCommit(true);
/* 486:    */         }
/* 487:    */       }
/* 488:    */       catch (SQLException localSQLException3)
/* 489:    */       {
/* 490:609 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 491:    */       }
/* 492:    */     }
/* 493:    */   }
/* 494:    */   
/* 495:    */   private static int a(int paramInt)
/* 496:    */   {
/* 497:624 */     return Account.getAccountGroupFromType(paramInt);
/* 498:    */   }
/* 499:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBAccount
 * JD-Core Version:    0.7.0.1
 */