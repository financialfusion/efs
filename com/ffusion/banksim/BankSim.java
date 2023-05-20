/*    1:     */ package com.ffusion.banksim;
/*    2:     */ 
/*    3:     */ import com.ffusion.banksim.adapter.BSDsbSummary;
/*    4:     */ import com.ffusion.banksim.adapter.BSDsbTransactions;
/*    5:     */ import com.ffusion.banksim.adapter.BSLBCreditItems;
/*    6:     */ import com.ffusion.banksim.adapter.BSLBTransactions;
/*    7:     */ import com.ffusion.banksim.adapter.BSLockboxSummary;
/*    8:     */ import com.ffusion.banksim.db.DBAccount;
/*    9:     */ import com.ffusion.banksim.db.DBClient;
/*   10:     */ import com.ffusion.banksim.db.DBConnection;
/*   11:     */ import com.ffusion.banksim.db.DBConnectionPool;
/*   12:     */ import com.ffusion.banksim.db.DBCustomer;
/*   13:     */ import com.ffusion.banksim.db.DBFinancialInstitution;
/*   14:     */ import com.ffusion.banksim.db.DBMail;
/*   15:     */ import com.ffusion.banksim.db.DBTransaction;
/*   16:     */ import com.ffusion.banksim.db.DBTransfer;
/*   17:     */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   18:     */ import com.ffusion.banksim.interfaces.BSException;
/*   19:     */ import com.ffusion.beans.Bank;
/*   20:     */ import com.ffusion.beans.accounts.Account;
/*   21:     */ import com.ffusion.beans.banking.Transfer;
/*   22:     */ import com.ffusion.beans.disbursement.DisbursementAccount;
/*   23:     */ import com.ffusion.beans.disbursement.DisbursementAccounts;
/*   24:     */ import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
/*   25:     */ import com.ffusion.beans.disbursement.DisbursementSummaries;
/*   26:     */ import com.ffusion.beans.disbursement.DisbursementSummary;
/*   27:     */ import com.ffusion.beans.disbursement.DisbursementTransactions;
/*   28:     */ import com.ffusion.beans.lockbox.LockboxAccount;
/*   29:     */ import com.ffusion.beans.lockbox.LockboxCreditItems;
/*   30:     */ import com.ffusion.beans.lockbox.LockboxSummaries;
/*   31:     */ import com.ffusion.beans.lockbox.LockboxSummary;
/*   32:     */ import com.ffusion.beans.lockbox.LockboxTransactions;
/*   33:     */ import com.ffusion.beans.messages.Message;
/*   34:     */ import com.ffusion.beans.reporting.ReportCriteria;
/*   35:     */ import com.ffusion.beans.user.User;
/*   36:     */ import com.ffusion.reporting.IReportResult;
/*   37:     */ import com.ffusion.util.DateFormatUtil;
/*   38:     */ import com.ffusion.util.ResourceUtil;
/*   39:     */ import com.ffusion.util.beans.PagingContext;
/*   40:     */ import com.ffusion.util.logging.DebugLog;
/*   41:     */ import java.io.IOException;
/*   42:     */ import java.io.InputStream;
/*   43:     */ import java.text.DateFormat;
/*   44:     */ import java.util.Calendar;
/*   45:     */ import java.util.Enumeration;
/*   46:     */ import java.util.HashMap;
/*   47:     */ import java.util.Properties;
/*   48:     */ import javax.swing.Timer;
/*   49:     */ 
/*   50:     */ public class BankSim
/*   51:     */ {
/*   52:     */   private static DBConnectionPool a;
/*   53:  36 */   private static boolean jdField_do = false;
/*   54:     */   private static Timer jdField_for;
/*   55:     */   private static Properties jdField_if;
/*   56:     */   
/*   57:     */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean)
/*   58:     */     throws BSException
/*   59:     */   {
/*   60:  56 */     switch (paramInt)
/*   61:     */     {
/*   62:     */     case 3: 
/*   63:  58 */       if (paramBoolean) {
/*   64:  59 */         return BSDBParams.createDB2AppParams(paramString1, paramString2, paramString3);
/*   65:     */       }
/*   66:  61 */       return BSDBParams.createDB2NetParams(paramString1, paramString2, paramString3);
/*   67:     */     case 7: 
/*   68:  65 */       return BSDBParams.createDB2UN2Params(paramString1, paramString2, paramString3);
/*   69:     */     case 5: 
/*   70:  68 */       return BSDBParams.createDB2390Params(paramString1, paramString2, paramString3);
/*   71:     */     case 4: 
/*   72:  71 */       return BSDBParams.createOracleParams(paramString1, paramString2, paramString3, paramBoolean);
/*   73:     */     case 1: 
/*   74:  74 */       return BSDBParams.createASAJConnectParams(paramString1, paramString2, paramString3);
/*   75:     */     case 2: 
/*   76:  77 */       return BSDBParams.createASEJConnectParams(paramString1, paramString2, paramString3);
/*   77:     */     case 6: 
/*   78:  80 */       return BSDBParams.createMSSQLParams(paramString1, paramString2, paramString3);
/*   79:     */     }
/*   80:  86 */     throw new BSException(2, MessageText.getMessage("ERR_UNKNOWN_DB_TYPE"));
/*   81:     */   }
/*   82:     */   
/*   83:     */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, boolean paramBoolean)
/*   84:     */     throws BSException
/*   85:     */   {
/*   86: 111 */     switch (paramInt)
/*   87:     */     {
/*   88:     */     case 3: 
/*   89: 113 */       if (paramBoolean) {
/*   90: 114 */         return BSDBParams.createDB2AppParams(paramString1, paramString2, paramString3, paramString4, paramString5);
/*   91:     */       }
/*   92: 116 */       return BSDBParams.createDB2NetParams(paramString1, paramString2, paramString3, paramString4, paramString5);
/*   93:     */     case 7: 
/*   94: 120 */       return BSDBParams.createDB2UN2Params(paramString1, paramString2, paramString3, paramString4, paramString5);
/*   95:     */     case 5: 
/*   96: 123 */       return BSDBParams.createDB2390Params(paramString1, paramString2, paramString3, paramString4, paramString5);
/*   97:     */     case 4: 
/*   98: 126 */       return BSDBParams.createOracleParams(paramString1, paramString2, paramString3, paramString4, paramString5, paramBoolean);
/*   99:     */     case 1: 
/*  100: 129 */       return BSDBParams.createASAJConnectParams(paramString1, paramString2, paramString3, paramString4, paramString5);
/*  101:     */     case 2: 
/*  102: 132 */       return BSDBParams.createASEJConnectParams(paramString1, paramString2, paramString3, paramString4, paramString5);
/*  103:     */     case 6: 
/*  104: 135 */       return BSDBParams.createMSSQLParams(paramString1, paramString2, paramString3, paramString4, paramString5);
/*  105:     */     }
/*  106: 140 */     throw new BSException(2, MessageText.getMessage("ERR_UNKNOWN_DB_TYPE"));
/*  107:     */   }
/*  108:     */   
/*  109:     */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
/*  110:     */     throws BSException
/*  111:     */   {
/*  112: 161 */     if (paramString4.equals("ASA")) {
/*  113: 162 */       return getBSDBParams(paramString1, paramString2, paramString3, 1, paramBoolean);
/*  114:     */     }
/*  115: 164 */     if (paramString4.equals("ASE")) {
/*  116: 165 */       return getBSDBParams(paramString1, paramString2, paramString3, 2, paramBoolean);
/*  117:     */     }
/*  118: 167 */     if (paramString4.equals("DB2")) {
/*  119: 168 */       return getBSDBParams(paramString1, paramString2, paramString3, 3, paramBoolean);
/*  120:     */     }
/*  121: 170 */     if (paramString4.equals("DB2:UN2")) {
/*  122: 171 */       return getBSDBParams(paramString1, paramString2, paramString3, 7, true);
/*  123:     */     }
/*  124: 173 */     if (paramString4.equals("DB2390")) {
/*  125: 174 */       return getBSDBParams(paramString1, paramString2, paramString3, 5, paramBoolean);
/*  126:     */     }
/*  127: 176 */     if (paramString4.equals("ORACLE")) {
/*  128: 177 */       return getBSDBParams(paramString1, paramString2, paramString3, 4, paramBoolean);
/*  129:     */     }
/*  130: 179 */     if (paramString4.equals("MSSQL")) {
/*  131: 180 */       return getBSDBParams(paramString1, paramString2, paramString3, 6, paramBoolean);
/*  132:     */     }
/*  133: 185 */     throw new BSException(2, MessageText.getMessage("ERR_UNKNOWN_DB_TYPE"));
/*  134:     */   }
/*  135:     */   
/*  136:     */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean)
/*  137:     */     throws BSException
/*  138:     */   {
/*  139: 210 */     if (paramString6.equals("ASA")) {
/*  140: 211 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 1, paramBoolean);
/*  141:     */     }
/*  142: 213 */     if (paramString6.equals("ASE")) {
/*  143: 214 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 2, paramBoolean);
/*  144:     */     }
/*  145: 216 */     if (paramString6.equals("DB2")) {
/*  146: 217 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 3, paramBoolean);
/*  147:     */     }
/*  148: 219 */     if (paramString6.equals("DB2:UN2")) {
/*  149: 220 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 7, true);
/*  150:     */     }
/*  151: 222 */     if (paramString6.equals("DB2390")) {
/*  152: 223 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 5, paramBoolean);
/*  153:     */     }
/*  154: 225 */     if (paramString6.equals("ORACLE")) {
/*  155: 226 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 4, paramBoolean);
/*  156:     */     }
/*  157: 228 */     if (paramString6.equals("MSSQL")) {
/*  158: 229 */       return getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, 6, paramBoolean);
/*  159:     */     }
/*  160: 234 */     throw new BSException(2, MessageText.getMessage("ERR_UNKNOWN_DB_TYPE"));
/*  161:     */   }
/*  162:     */   
/*  163:     */   public static void initialize(BSDBParams paramBSDBParams, int paramInt)
/*  164:     */     throws BSException
/*  165:     */   {
/*  166: 247 */     initialize(paramBSDBParams, paramInt, null);
/*  167:     */   }
/*  168:     */   
/*  169:     */   public static DBConnection getDBConnection()
/*  170:     */     throws Exception
/*  171:     */   {
/*  172: 252 */     if (jdField_do) {
/*  173: 253 */       return a.getConnection();
/*  174:     */     }
/*  175: 255 */     BSException localBSException = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  176:     */     
/*  177: 257 */     DebugLog.throwing("com.ffusion.banksim.Banksim:signOn", localBSException);
/*  178: 258 */     throw localBSException;
/*  179:     */   }
/*  180:     */   
/*  181:     */   public static synchronized void initialize(BSDBParams paramBSDBParams, int paramInt, Properties paramProperties)
/*  182:     */     throws BSException
/*  183:     */   {
/*  184: 272 */     if (jdField_do)
/*  185:     */     {
/*  186: 273 */       localObject1 = new BSException(1001, MessageText.getMessage("ERR_ALREADY_INITIALIZED"));
/*  187:     */       
/*  188:     */ 
/*  189: 276 */       DebugLog.throwing("com.ffusion.banksim.Banksim:initialize", (Throwable)localObject1);
/*  190: 277 */       throw ((Throwable)localObject1);
/*  191:     */     }
/*  192: 280 */     a = new DBConnectionPool(paramBSDBParams, paramInt);
/*  193:     */     
/*  194: 282 */     Object localObject1 = a.getConnection();
/*  195:     */     try
/*  196:     */     {
/*  197: 288 */       DBCustomer.addDefaultCustomer((DBConnection)localObject1);
/*  198:     */     }
/*  199:     */     catch (BSException localBSException1)
/*  200:     */     {
/*  201: 291 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  202: 292 */       DebugLog.throwing("com.ffusion.banksim.Banksim:initialize", localBSException2);
/*  203: 293 */       throw localBSException2;
/*  204:     */     }
/*  205:     */     finally
/*  206:     */     {
/*  207: 295 */       a.releaseConnection((DBConnection)localObject1);
/*  208:     */     }
/*  209: 300 */     jdField_for = new Timer(900000, new PagedTransactionCleanup());
/*  210: 301 */     jdField_for.start();
/*  211: 304 */     if (paramProperties == null) {
/*  212: 305 */       jdField_if = new Properties();
/*  213:     */     } else {
/*  214: 307 */       jdField_if = new Properties(paramProperties);
/*  215:     */     }
/*  216:     */     try
/*  217:     */     {
/*  218: 312 */       InputStream localInputStream = null;
/*  219: 313 */       localInputStream = ResourceUtil.getResourceAsStream(new BankSim(), "banksim.properties");
/*  220: 314 */       if (localInputStream != null) {
/*  221: 315 */         jdField_if.load(localInputStream);
/*  222:     */       }
/*  223:     */     }
/*  224:     */     catch (IOException localIOException) {}
/*  225: 321 */     jdField_do = true;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public static boolean isInitialized()
/*  229:     */   {
/*  230: 329 */     return jdField_do;
/*  231:     */   }
/*  232:     */   
/*  233:     */   private static void a(String paramString, long paramLong)
/*  234:     */   {
/*  235: 334 */     String str1 = jdField_if.getProperty("GeneralMinTime");
/*  236: 335 */     if (str1 == null) {
/*  237: 336 */       str1 = "0";
/*  238:     */     }
/*  239: 339 */     String str2 = jdField_if.getProperty(paramString, str1);
/*  240:     */     try
/*  241:     */     {
/*  242: 342 */       long l1 = Long.parseLong(str2);
/*  243: 343 */       long l2 = l1 - (System.currentTimeMillis() - paramLong);
/*  244: 345 */       if (l2 > 0L) {
/*  245: 346 */         Thread.sleep(l2);
/*  246:     */       }
/*  247:     */     }
/*  248:     */     catch (InterruptedException localInterruptedException) {}
/*  249:     */   }
/*  250:     */   
/*  251:     */   public static User signOn(String paramString1, String paramString2)
/*  252:     */     throws BSException
/*  253:     */   {
/*  254: 365 */     long l = System.currentTimeMillis();
/*  255: 367 */     if (!jdField_do)
/*  256:     */     {
/*  257: 368 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  258:     */       
/*  259: 370 */       DebugLog.throwing("com.ffusion.banksim.Banksim:signOn", (Throwable)localObject1);
/*  260: 371 */       throw ((Throwable)localObject1);
/*  261:     */     }
/*  262: 373 */     Object localObject1 = null;
/*  263: 374 */     DBConnection localDBConnection = a.getConnection();
/*  264:     */     try
/*  265:     */     {
/*  266: 376 */       localObject1 = DBClient.signOn(paramString1, paramString2, localDBConnection);
/*  267:     */     }
/*  268:     */     catch (BSException localBSException1)
/*  269:     */     {
/*  270: 378 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  271: 379 */       DebugLog.throwing("com.ffusion.banksim.Banksim:signOn", localBSException2);
/*  272: 380 */       throw localBSException2;
/*  273:     */     }
/*  274:     */     finally
/*  275:     */     {
/*  276: 382 */       a.releaseConnection(localDBConnection);
/*  277:     */     }
/*  278: 385 */     a("SignOnMinTime", l);
/*  279: 386 */     return localObject1;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public static void setPassword(User paramUser, String paramString)
/*  283:     */     throws BSException
/*  284:     */   {
/*  285: 399 */     long l = System.currentTimeMillis();
/*  286: 401 */     if (!jdField_do)
/*  287:     */     {
/*  288: 402 */       localObject = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  289:     */       
/*  290: 404 */       DebugLog.throwing("com.ffusion.banksim.Banksim:setPassword", (Throwable)localObject);
/*  291: 405 */       throw ((Throwable)localObject);
/*  292:     */     }
/*  293: 407 */     paramUser.setPassword(paramString);
/*  294: 408 */     Object localObject = a.getConnection();
/*  295:     */     try
/*  296:     */     {
/*  297: 410 */       DBCustomer.updatePassword(paramUser, (DBConnection)localObject);
/*  298:     */     }
/*  299:     */     catch (BSException localBSException1)
/*  300:     */     {
/*  301: 412 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  302: 413 */       DebugLog.throwing("com.ffusion.banksim.Banksim:setPassword", localBSException2);
/*  303: 414 */       throw localBSException2;
/*  304:     */     }
/*  305: 417 */     a("SetPasswordMinTime", l);
/*  306:     */   }
/*  307:     */   
/*  308:     */   private static void jdField_if(Bank paramBank, boolean paramBoolean)
/*  309:     */     throws BSException
/*  310:     */   {
/*  311: 428 */     long l = System.currentTimeMillis();
/*  312: 430 */     if (!jdField_do)
/*  313:     */     {
/*  314: 431 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  315:     */       
/*  316: 433 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addBank", (Throwable)localObject1);
/*  317: 434 */       throw ((Throwable)localObject1);
/*  318:     */     }
/*  319: 437 */     Object localObject1 = a.getConnection();
/*  320:     */     try
/*  321:     */     {
/*  322: 439 */       DBFinancialInstitution.addBank(paramBank, (DBConnection)localObject1);
/*  323:     */     }
/*  324:     */     catch (BSException localBSException1)
/*  325:     */     {
/*  326: 441 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  327: 442 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addBank", localBSException2);
/*  328: 443 */       throw localBSException2;
/*  329:     */     }
/*  330:     */     finally
/*  331:     */     {
/*  332: 445 */       a.releaseConnection((DBConnection)localObject1);
/*  333:     */     }
/*  334: 448 */     if (paramBoolean) {
/*  335: 449 */       a("AddBankMinTime", l);
/*  336:     */     }
/*  337:     */   }
/*  338:     */   
/*  339:     */   public static void addBank(Bank paramBank)
/*  340:     */     throws BSException
/*  341:     */   {
/*  342: 459 */     jdField_if(paramBank, true);
/*  343:     */   }
/*  344:     */   
/*  345:     */   public static void addBanks(Bank[] paramArrayOfBank)
/*  346:     */     throws BSException
/*  347:     */   {
/*  348: 470 */     long l = System.currentTimeMillis();
/*  349: 472 */     for (int i = 0; i < paramArrayOfBank.length; i++) {
/*  350: 473 */       jdField_if(paramArrayOfBank[i], false);
/*  351:     */     }
/*  352: 476 */     a("AddBanksMinTime", l);
/*  353:     */   }
/*  354:     */   
/*  355:     */   public static Bank getBank(String paramString)
/*  356:     */     throws BSException
/*  357:     */   {
/*  358: 489 */     long l = System.currentTimeMillis();
/*  359: 491 */     if (!jdField_do)
/*  360:     */     {
/*  361: 492 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  362:     */       
/*  363: 494 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getBank", (Throwable)localObject1);
/*  364: 495 */       throw ((Throwable)localObject1);
/*  365:     */     }
/*  366: 498 */     Object localObject1 = null;
/*  367: 499 */     DBConnection localDBConnection = a.getConnection();
/*  368:     */     try
/*  369:     */     {
/*  370: 501 */       localObject1 = DBFinancialInstitution.getBank(paramString, localDBConnection);
/*  371:     */     }
/*  372:     */     catch (BSException localBSException1)
/*  373:     */     {
/*  374: 503 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  375: 504 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getBank", localBSException2);
/*  376: 505 */       throw localBSException2;
/*  377:     */     }
/*  378:     */     finally
/*  379:     */     {
/*  380: 507 */       a.releaseConnection(localDBConnection);
/*  381:     */     }
/*  382: 510 */     a("GetBankMinTime", l);
/*  383: 511 */     return localObject1;
/*  384:     */   }
/*  385:     */   
/*  386:     */   private static void a(Bank paramBank, boolean paramBoolean)
/*  387:     */     throws BSException
/*  388:     */   {
/*  389: 518 */     long l = System.currentTimeMillis();
/*  390: 520 */     if (!jdField_do)
/*  391:     */     {
/*  392: 521 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  393:     */       
/*  394: 523 */       DebugLog.throwing("com.ffusion.banksim.Banksim:updateBank", (Throwable)localObject1);
/*  395: 524 */       throw ((Throwable)localObject1);
/*  396:     */     }
/*  397: 526 */     Object localObject1 = a.getConnection();
/*  398:     */     try
/*  399:     */     {
/*  400: 528 */       DBFinancialInstitution.updateBank(paramBank, (DBConnection)localObject1);
/*  401:     */     }
/*  402:     */     catch (BSException localBSException1)
/*  403:     */     {
/*  404: 530 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  405: 531 */       DebugLog.throwing("com.ffusion.banksim.Banksim:updateBank", localBSException2);
/*  406: 532 */       throw localBSException2;
/*  407:     */     }
/*  408:     */     finally
/*  409:     */     {
/*  410: 534 */       a.releaseConnection((DBConnection)localObject1);
/*  411:     */     }
/*  412: 537 */     if (paramBoolean) {
/*  413: 538 */       a("UpdateBankMinTime", l);
/*  414:     */     }
/*  415:     */   }
/*  416:     */   
/*  417:     */   public static void updateBank(Bank paramBank)
/*  418:     */     throws BSException
/*  419:     */   {
/*  420: 549 */     a(paramBank, true);
/*  421:     */   }
/*  422:     */   
/*  423:     */   public static void updateBanks(Bank[] paramArrayOfBank)
/*  424:     */     throws BSException
/*  425:     */   {
/*  426: 556 */     long l = System.currentTimeMillis();
/*  427: 558 */     for (int i = 0; i < paramArrayOfBank.length; i++) {
/*  428: 559 */       a(paramArrayOfBank[i], false);
/*  429:     */     }
/*  430: 562 */     a("UpdateBanksMinTime", l);
/*  431:     */   }
/*  432:     */   
/*  433:     */   public static void deleteBank(Bank paramBank)
/*  434:     */     throws BSException
/*  435:     */   {
/*  436: 574 */     long l = System.currentTimeMillis();
/*  437: 576 */     if (!jdField_do)
/*  438:     */     {
/*  439: 577 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  440:     */       
/*  441: 579 */       DebugLog.throwing("com.ffusion.banksim.Banksim:deleteBank", (Throwable)localObject1);
/*  442: 580 */       throw ((Throwable)localObject1);
/*  443:     */     }
/*  444: 583 */     Object localObject1 = a.getConnection();
/*  445:     */     try
/*  446:     */     {
/*  447: 585 */       DBFinancialInstitution.deleteBank(paramBank, (DBConnection)localObject1);
/*  448:     */     }
/*  449:     */     catch (BSException localBSException1)
/*  450:     */     {
/*  451: 587 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  452: 588 */       DebugLog.throwing("com.ffusion.banksim.Banksim:deleteBank", localBSException2);
/*  453: 589 */       throw localBSException2;
/*  454:     */     }
/*  455:     */     finally
/*  456:     */     {
/*  457: 591 */       a.releaseConnection((DBConnection)localObject1);
/*  458:     */     }
/*  459: 594 */     a("DeleteBankMinTime", l);
/*  460:     */   }
/*  461:     */   
/*  462:     */   private static void a(User paramUser, boolean paramBoolean)
/*  463:     */     throws BSException
/*  464:     */   {
/*  465: 601 */     long l = System.currentTimeMillis();
/*  466: 603 */     if (!jdField_do)
/*  467:     */     {
/*  468: 604 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  469:     */       
/*  470: 606 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addCustomer", (Throwable)localObject1);
/*  471: 607 */       throw ((Throwable)localObject1);
/*  472:     */     }
/*  473: 609 */     Object localObject1 = a.getConnection();
/*  474:     */     try
/*  475:     */     {
/*  476: 611 */       DBCustomer.addCustomer(paramUser, (DBConnection)localObject1);
/*  477:     */     }
/*  478:     */     catch (BSException localBSException1)
/*  479:     */     {
/*  480: 613 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  481: 614 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addCustomer", localBSException2);
/*  482: 615 */       throw localBSException2;
/*  483:     */     }
/*  484:     */     finally
/*  485:     */     {
/*  486: 617 */       a.releaseConnection((DBConnection)localObject1);
/*  487:     */     }
/*  488: 620 */     if (paramBoolean) {
/*  489: 621 */       a("AddCustomerMinTime", l);
/*  490:     */     }
/*  491:     */   }
/*  492:     */   
/*  493:     */   public static void addCustomer(User paramUser)
/*  494:     */     throws BSException
/*  495:     */   {
/*  496: 631 */     a(paramUser, true);
/*  497:     */   }
/*  498:     */   
/*  499:     */   public static void addCustomers(User[] paramArrayOfUser)
/*  500:     */     throws BSException
/*  501:     */   {
/*  502: 638 */     long l = System.currentTimeMillis();
/*  503: 640 */     for (int i = 0; i < paramArrayOfUser.length; i++) {
/*  504: 641 */       a(paramArrayOfUser[i], false);
/*  505:     */     }
/*  506: 644 */     a("AddCustomersMinTime", l);
/*  507:     */   }
/*  508:     */   
/*  509:     */   private static void jdField_if(User paramUser, boolean paramBoolean)
/*  510:     */     throws BSException
/*  511:     */   {
/*  512: 651 */     long l = System.currentTimeMillis();
/*  513: 653 */     if (!jdField_do)
/*  514:     */     {
/*  515: 654 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  516:     */       
/*  517: 656 */       DebugLog.throwing("com.ffusion.banksim.Banksim:updateCustomer", (Throwable)localObject1);
/*  518: 657 */       throw ((Throwable)localObject1);
/*  519:     */     }
/*  520: 659 */     Object localObject1 = a.getConnection();
/*  521:     */     try
/*  522:     */     {
/*  523: 661 */       DBCustomer.updateCustomer(paramUser, (DBConnection)localObject1);
/*  524:     */     }
/*  525:     */     catch (BSException localBSException1)
/*  526:     */     {
/*  527: 663 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  528: 664 */       DebugLog.throwing("com.ffusion.banksim.Banksim:updateCustomer", localBSException2);
/*  529: 665 */       throw localBSException2;
/*  530:     */     }
/*  531:     */     finally
/*  532:     */     {
/*  533: 667 */       a.releaseConnection((DBConnection)localObject1);
/*  534:     */     }
/*  535: 670 */     if (paramBoolean) {
/*  536: 671 */       a("UpdateCustomerMinTime", l);
/*  537:     */     }
/*  538:     */   }
/*  539:     */   
/*  540:     */   public static void updateCustomer(User paramUser)
/*  541:     */     throws BSException
/*  542:     */   {
/*  543: 682 */     jdField_if(paramUser, true);
/*  544:     */   }
/*  545:     */   
/*  546:     */   public static void updateCustomers(User[] paramArrayOfUser)
/*  547:     */     throws BSException
/*  548:     */   {
/*  549: 689 */     long l = System.currentTimeMillis();
/*  550: 691 */     for (int i = 0; i < paramArrayOfUser.length; i++) {
/*  551: 692 */       jdField_if(paramArrayOfUser[i], false);
/*  552:     */     }
/*  553: 695 */     a("UpdateCustomersMinTime", l);
/*  554:     */   }
/*  555:     */   
/*  556:     */   public static void deleteCustomer(User paramUser)
/*  557:     */     throws BSException
/*  558:     */   {
/*  559: 707 */     long l = System.currentTimeMillis();
/*  560: 709 */     if (!jdField_do)
/*  561:     */     {
/*  562: 710 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  563:     */       
/*  564: 712 */       DebugLog.throwing("com.ffusion.banksim.Banksim:deleteCustomer", (Throwable)localObject1);
/*  565: 713 */       throw ((Throwable)localObject1);
/*  566:     */     }
/*  567: 716 */     Object localObject1 = a.getConnection();
/*  568:     */     try
/*  569:     */     {
/*  570: 718 */       DBCustomer.deleteCustomer(paramUser, (DBConnection)localObject1);
/*  571:     */     }
/*  572:     */     catch (BSException localBSException1)
/*  573:     */     {
/*  574: 720 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  575: 721 */       DebugLog.throwing("com.ffusion.banksim.Banksim:deleteCustomer", localBSException2);
/*  576: 722 */       throw localBSException2;
/*  577:     */     }
/*  578:     */     finally
/*  579:     */     {
/*  580: 724 */       a.releaseConnection((DBConnection)localObject1);
/*  581:     */     }
/*  582: 727 */     a("DeleteCustomerMinTime", l);
/*  583:     */   }
/*  584:     */   
/*  585:     */   private static void a(User paramUser, Account paramAccount, boolean paramBoolean)
/*  586:     */     throws BSException
/*  587:     */   {
/*  588: 736 */     long l = System.currentTimeMillis();
/*  589: 738 */     if (!jdField_do)
/*  590:     */     {
/*  591: 739 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  592:     */       
/*  593: 741 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addAccount", (Throwable)localObject1);
/*  594: 742 */       throw ((Throwable)localObject1);
/*  595:     */     }
/*  596: 745 */     Object localObject1 = a.getConnection();
/*  597:     */     try
/*  598:     */     {
/*  599: 747 */       DBAccount.addAccount(paramUser, paramAccount, (DBConnection)localObject1, false);
/*  600:     */     }
/*  601:     */     catch (BSException localBSException1)
/*  602:     */     {
/*  603: 749 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  604: 750 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addAccount", localBSException2);
/*  605: 751 */       throw localBSException2;
/*  606:     */     }
/*  607:     */     finally
/*  608:     */     {
/*  609: 753 */       a.releaseConnection((DBConnection)localObject1);
/*  610:     */     }
/*  611: 756 */     if (paramBoolean) {
/*  612: 757 */       a("AddAccountMinTime", l);
/*  613:     */     }
/*  614:     */   }
/*  615:     */   
/*  616:     */   public static void addAccount(User paramUser, Account paramAccount)
/*  617:     */     throws BSException
/*  618:     */   {
/*  619: 768 */     a(paramUser, paramAccount, true);
/*  620:     */   }
/*  621:     */   
/*  622:     */   public static void addAccounts(User paramUser, Account[] paramArrayOfAccount)
/*  623:     */     throws BSException
/*  624:     */   {
/*  625: 775 */     long l = System.currentTimeMillis();
/*  626: 777 */     for (int i = 0; i < paramArrayOfAccount.length; i++) {
/*  627: 778 */       a(paramUser, paramArrayOfAccount[i], false);
/*  628:     */     }
/*  629: 781 */     a("AddAccountsMinTime", l);
/*  630:     */   }
/*  631:     */   
/*  632:     */   public static Enumeration getAccounts(User paramUser)
/*  633:     */     throws BSException
/*  634:     */   {
/*  635: 794 */     long l = System.currentTimeMillis();
/*  636: 796 */     if (!jdField_do)
/*  637:     */     {
/*  638: 797 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  639:     */       
/*  640: 799 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getAccounts", (Throwable)localObject1);
/*  641: 800 */       throw ((Throwable)localObject1);
/*  642:     */     }
/*  643: 803 */     Object localObject1 = null;
/*  644: 804 */     DBConnection localDBConnection = a.getConnection();
/*  645:     */     try
/*  646:     */     {
/*  647: 806 */       localObject1 = DBAccount.getAccounts(paramUser, localDBConnection);
/*  648:     */     }
/*  649:     */     catch (BSException localBSException1)
/*  650:     */     {
/*  651: 808 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  652: 809 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getAccounts", localBSException2);
/*  653: 810 */       throw localBSException2;
/*  654:     */     }
/*  655:     */     finally
/*  656:     */     {
/*  657: 812 */       a.releaseConnection(localDBConnection);
/*  658:     */     }
/*  659: 815 */     a("GetAccountsMinTime", l);
/*  660: 816 */     return localObject1;
/*  661:     */   }
/*  662:     */   
/*  663:     */   public static Account getAccount(Account paramAccount)
/*  664:     */     throws BSException
/*  665:     */   {
/*  666: 829 */     long l = System.currentTimeMillis();
/*  667: 831 */     if (!jdField_do)
/*  668:     */     {
/*  669: 832 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  670:     */       
/*  671: 834 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getAccount", (Throwable)localObject1);
/*  672: 835 */       throw ((Throwable)localObject1);
/*  673:     */     }
/*  674: 838 */     Object localObject1 = a.getConnection();
/*  675:     */     try
/*  676:     */     {
/*  677: 840 */       paramAccount = DBAccount.getAccount(paramAccount, (DBConnection)localObject1);
/*  678:     */     }
/*  679:     */     catch (BSException localBSException1)
/*  680:     */     {
/*  681: 842 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  682: 843 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getAccount", localBSException2);
/*  683: 844 */       throw localBSException2;
/*  684:     */     }
/*  685:     */     finally
/*  686:     */     {
/*  687: 846 */       a.releaseConnection((DBConnection)localObject1);
/*  688:     */     }
/*  689: 849 */     a("GetAccountMinTime", l);
/*  690: 850 */     return paramAccount;
/*  691:     */   }
/*  692:     */   
/*  693:     */   private static void a(Account paramAccount, boolean paramBoolean)
/*  694:     */     throws BSException
/*  695:     */   {
/*  696: 857 */     long l = System.currentTimeMillis();
/*  697: 859 */     if (!jdField_do)
/*  698:     */     {
/*  699: 860 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  700:     */       
/*  701: 862 */       DebugLog.throwing("com.ffusion.banksim.Banksim:updateAccount", (Throwable)localObject1);
/*  702: 863 */       throw ((Throwable)localObject1);
/*  703:     */     }
/*  704: 865 */     Object localObject1 = a.getConnection();
/*  705:     */     try
/*  706:     */     {
/*  707: 867 */       DBAccount.updateAccount(paramAccount, (DBConnection)localObject1);
/*  708:     */     }
/*  709:     */     catch (BSException localBSException1)
/*  710:     */     {
/*  711: 869 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  712: 870 */       DebugLog.throwing("com.ffusion.banksim.Banksim:updateAccount", localBSException2);
/*  713: 871 */       throw localBSException2;
/*  714:     */     }
/*  715:     */     finally
/*  716:     */     {
/*  717: 873 */       a.releaseConnection((DBConnection)localObject1);
/*  718:     */     }
/*  719: 876 */     if (paramBoolean) {
/*  720: 877 */       a("UpdateAccountMinTime", l);
/*  721:     */     }
/*  722:     */   }
/*  723:     */   
/*  724:     */   public static void updateAccount(Account paramAccount)
/*  725:     */     throws BSException
/*  726:     */   {
/*  727: 888 */     a(paramAccount, true);
/*  728:     */   }
/*  729:     */   
/*  730:     */   public static void updateAccounts(Account[] paramArrayOfAccount)
/*  731:     */     throws BSException
/*  732:     */   {
/*  733: 895 */     long l = System.currentTimeMillis();
/*  734: 897 */     for (int i = 0; i < paramArrayOfAccount.length; i++) {
/*  735: 898 */       a(paramArrayOfAccount[i], false);
/*  736:     */     }
/*  737: 901 */     a("UpdateAccountsMinTime", l);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public static void deleteAccount(Account paramAccount)
/*  741:     */     throws BSException
/*  742:     */   {
/*  743: 913 */     long l = System.currentTimeMillis();
/*  744: 915 */     if (!jdField_do)
/*  745:     */     {
/*  746: 916 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  747:     */       
/*  748: 918 */       DebugLog.throwing("com.ffusion.banksim.Banksim:deleteAccount", (Throwable)localObject1);
/*  749: 919 */       throw ((Throwable)localObject1);
/*  750:     */     }
/*  751: 922 */     Object localObject1 = a.getConnection();
/*  752:     */     try
/*  753:     */     {
/*  754: 924 */       DBAccount.deleteAccount(paramAccount, (DBConnection)localObject1);
/*  755:     */     }
/*  756:     */     catch (BSException localBSException1)
/*  757:     */     {
/*  758: 926 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  759: 927 */       DebugLog.throwing("com.ffusion.banksim.Banksim:deleteAccount", localBSException2);
/*  760: 928 */       throw localBSException2;
/*  761:     */     }
/*  762:     */     finally
/*  763:     */     {
/*  764: 930 */       a.releaseConnection((DBConnection)localObject1);
/*  765:     */     }
/*  766: 933 */     a("DeleteAccountMinTime", l);
/*  767:     */   }
/*  768:     */   
/*  769:     */   private static Transfer a(Transfer paramTransfer, int paramInt, boolean paramBoolean)
/*  770:     */     throws BSException
/*  771:     */   {
/*  772: 942 */     long l = System.currentTimeMillis();
/*  773: 944 */     if (!jdField_do)
/*  774:     */     {
/*  775: 945 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  776:     */       
/*  777: 947 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addTransfer", (Throwable)localObject1);
/*  778: 948 */       throw ((Throwable)localObject1);
/*  779:     */     }
/*  780: 951 */     Object localObject1 = a.getConnection();
/*  781: 952 */     Transfer localTransfer = null;
/*  782:     */     try
/*  783:     */     {
/*  784: 954 */       localTransfer = DBTransfer.addTransfer(paramTransfer, paramInt, (DBConnection)localObject1);
/*  785:     */     }
/*  786:     */     catch (BSException localBSException1)
/*  787:     */     {
/*  788: 956 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  789: 957 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addTransfer", localBSException2);
/*  790: 958 */       throw localBSException2;
/*  791:     */     }
/*  792:     */     finally
/*  793:     */     {
/*  794: 960 */       a.releaseConnection((DBConnection)localObject1);
/*  795:     */     }
/*  796: 963 */     if (paramBoolean) {
/*  797: 964 */       a("AddTransferMinTime", l);
/*  798:     */     }
/*  799: 966 */     return localTransfer;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public static Transfer addTransfer(Transfer paramTransfer, int paramInt)
/*  803:     */     throws BSException
/*  804:     */   {
/*  805: 979 */     return a(paramTransfer, paramInt, true);
/*  806:     */   }
/*  807:     */   
/*  808:     */   public static Transfer[] addTransfers(Transfer[] paramArrayOfTransfer, int[] paramArrayOfInt)
/*  809:     */     throws BSException
/*  810:     */   {
/*  811: 986 */     long l = System.currentTimeMillis();
/*  812: 987 */     Transfer[] arrayOfTransfer = new Transfer[paramArrayOfTransfer.length];
/*  813: 989 */     for (int i = 0; i < paramArrayOfTransfer.length; i++) {
/*  814: 990 */       arrayOfTransfer[i] = a(paramArrayOfTransfer[i], paramArrayOfInt[i], false);
/*  815:     */     }
/*  816: 993 */     a("AddTransfersMinTime", l);
/*  817:     */     
/*  818: 995 */     return arrayOfTransfer;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public static Enumeration getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
/*  822:     */     throws BSException
/*  823:     */   {
/*  824:1013 */     long l = System.currentTimeMillis();
/*  825:1015 */     if (!jdField_do)
/*  826:     */     {
/*  827:1016 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  828:     */       
/*  829:1018 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getTransactions", (Throwable)localObject1);
/*  830:1019 */       throw ((Throwable)localObject1);
/*  831:     */     }
/*  832:1022 */     Object localObject1 = null;
/*  833:1023 */     DBConnection localDBConnection = a.getConnection();
/*  834:     */     try
/*  835:     */     {
/*  836:1025 */       localObject1 = DBTransaction.getTransactions(paramAccount, paramCalendar1, paramCalendar2, localDBConnection);
/*  837:     */     }
/*  838:     */     catch (BSException localBSException1)
/*  839:     */     {
/*  840:1027 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  841:1028 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getTransactions", localBSException2);
/*  842:1029 */       throw localBSException2;
/*  843:     */     }
/*  844:     */     finally
/*  845:     */     {
/*  846:1031 */       a.releaseConnection(localDBConnection);
/*  847:     */     }
/*  848:1034 */     a("GetTransactionsMinTime", l);
/*  849:1035 */     return localObject1;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public static void openPagedTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
/*  853:     */     throws BSException
/*  854:     */   {
/*  855:1051 */     PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
/*  856:1052 */     ReportCriteria localReportCriteria = new ReportCriteria();
/*  857:1053 */     HashMap localHashMap1 = new HashMap();
/*  858:1054 */     localPagingContext.setMap(localHashMap1);
/*  859:1055 */     localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
/*  860:1056 */     DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
/*  861:1057 */     if (paramCalendar1 != null) {
/*  862:1058 */       localReportCriteria.getSearchCriteria().setProperty("StartDate", localDateFormat.format(paramCalendar1.getTime()));
/*  863:     */     }
/*  864:1060 */     if (paramCalendar2 != null) {
/*  865:1061 */       localReportCriteria.getSearchCriteria().setProperty("EndDate", localDateFormat.format(paramCalendar2.getTime()));
/*  866:     */     }
/*  867:1063 */     HashMap localHashMap2 = new HashMap();
/*  868:     */     
/*  869:1065 */     openPagedTransactions(paramAccount, localPagingContext, localHashMap2);
/*  870:     */   }
/*  871:     */   
/*  872:     */   public static void openPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
/*  873:     */     throws BSException
/*  874:     */   {
/*  875:1081 */     long l = System.currentTimeMillis();
/*  876:1083 */     if (!jdField_do)
/*  877:     */     {
/*  878:1084 */       localObject1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  879:     */       
/*  880:1086 */       DebugLog.throwing("com.ffusion.banksim.Banksim:openPagedTransactions", (Throwable)localObject1);
/*  881:1087 */       throw ((Throwable)localObject1);
/*  882:     */     }
/*  883:1090 */     Object localObject1 = a.getConnection();
/*  884:     */     try
/*  885:     */     {
/*  886:1092 */       DBTransaction.openPagedTransactions(paramAccount, paramPagingContext, paramHashMap, (DBConnection)localObject1);
/*  887:     */     }
/*  888:     */     catch (BSException localBSException1)
/*  889:     */     {
/*  890:1094 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  891:1095 */       DebugLog.throwing("com.ffusion.banksim.Banksim:openPagedTransactions", localBSException2);
/*  892:1096 */       throw localBSException2;
/*  893:     */     }
/*  894:     */     finally
/*  895:     */     {
/*  896:1098 */       a.releaseConnection((DBConnection)localObject1);
/*  897:     */     }
/*  898:1101 */     a("OpenPagedTransactionsMinTime", l);
/*  899:     */   }
/*  900:     */   
/*  901:     */   public static void closePagedTransactions(Account paramAccount)
/*  902:     */     throws BSException
/*  903:     */   {
/*  904:1114 */     long l = System.currentTimeMillis();
/*  905:1116 */     if (!jdField_do)
/*  906:     */     {
/*  907:1117 */       BSException localBSException1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  908:     */       
/*  909:1119 */       DebugLog.throwing("com.ffusion.banksim.Banksim:closePagedTransactions", localBSException1);
/*  910:1120 */       throw localBSException1;
/*  911:     */     }
/*  912:     */     try
/*  913:     */     {
/*  914:1124 */       DBTransaction.closePagedTransactions(paramAccount);
/*  915:     */     }
/*  916:     */     catch (BSException localBSException2)
/*  917:     */     {
/*  918:1126 */       BSException localBSException3 = new BSException(localBSException2.getErrorCode(), localBSException2);
/*  919:1127 */       DebugLog.throwing("com.ffusion.banksim.Banksim:closePagedTransactions", localBSException3);
/*  920:1128 */       throw localBSException3;
/*  921:     */     }
/*  922:1131 */     a("ClosePagedTransactionsMinTime", l);
/*  923:     */   }
/*  924:     */   
/*  925:     */   public static int getNumberOfTransactions(Account paramAccount)
/*  926:     */     throws BSException
/*  927:     */   {
/*  928:1141 */     if (!jdField_do)
/*  929:     */     {
/*  930:1142 */       BSException localBSException1 = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  931:     */       
/*  932:1144 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getNextPage", localBSException1);
/*  933:1145 */       throw localBSException1;
/*  934:     */     }
/*  935:     */     try
/*  936:     */     {
/*  937:1149 */       return DBTransaction.getNumberOfTransactions(paramAccount);
/*  938:     */     }
/*  939:     */     catch (BSException localBSException2)
/*  940:     */     {
/*  941:1151 */       BSException localBSException3 = new BSException(localBSException2.getErrorCode(), localBSException2);
/*  942:1152 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getNextPage", localBSException3);
/*  943:1153 */       throw localBSException3;
/*  944:     */     }
/*  945:     */   }
/*  946:     */   
/*  947:     */   public static Enumeration getNextPage(Account paramAccount, int paramInt)
/*  948:     */     throws BSException
/*  949:     */   {
/*  950:1166 */     return getNextPage(paramAccount, paramInt, -1);
/*  951:     */   }
/*  952:     */   
/*  953:     */   public static Enumeration getNextPage(Account paramAccount, int paramInt1, int paramInt2)
/*  954:     */     throws BSException
/*  955:     */   {
/*  956:1182 */     long l = System.currentTimeMillis();
/*  957:1184 */     if (!jdField_do)
/*  958:     */     {
/*  959:1185 */       localObject = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  960:     */       
/*  961:1187 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getNextPage", (Throwable)localObject);
/*  962:1188 */       throw ((Throwable)localObject);
/*  963:     */     }
/*  964:1191 */     Object localObject = null;
/*  965:     */     try
/*  966:     */     {
/*  967:1193 */       localObject = DBTransaction.getNextPage(paramAccount, paramInt1, paramInt2);
/*  968:     */     }
/*  969:     */     catch (BSException localBSException1)
/*  970:     */     {
/*  971:1195 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/*  972:1196 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getNextPage", localBSException2);
/*  973:1197 */       throw localBSException2;
/*  974:     */     }
/*  975:1200 */     a("GetNextPageMinTime", l);
/*  976:1201 */     return localObject;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public static Enumeration getPrevPage(Account paramAccount, int paramInt)
/*  980:     */     throws BSException
/*  981:     */   {
/*  982:1213 */     return getPrevPage(paramAccount, paramInt, -1);
/*  983:     */   }
/*  984:     */   
/*  985:     */   public static Enumeration getPrevPage(Account paramAccount, int paramInt1, int paramInt2)
/*  986:     */     throws BSException
/*  987:     */   {
/*  988:1229 */     long l = System.currentTimeMillis();
/*  989:1231 */     if (!jdField_do)
/*  990:     */     {
/*  991:1232 */       localObject = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/*  992:     */       
/*  993:1234 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getPrevPage", (Throwable)localObject);
/*  994:1235 */       throw ((Throwable)localObject);
/*  995:     */     }
/*  996:1238 */     Object localObject = null;
/*  997:     */     try
/*  998:     */     {
/*  999:1240 */       localObject = DBTransaction.getPrevPage(paramAccount, paramInt1, paramInt2);
/* 1000:     */     }
/* 1001:     */     catch (BSException localBSException1)
/* 1002:     */     {
/* 1003:1242 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/* 1004:1243 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getPrevPage", localBSException2);
/* 1005:1244 */       throw localBSException2;
/* 1006:     */     }
/* 1007:1247 */     a("GetPrevPageMinTime", l);
/* 1008:1248 */     return localObject;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public static final void addMailMessage(User paramUser, Message paramMessage)
/* 1012:     */     throws BSException
/* 1013:     */   {
/* 1014:1261 */     long l = System.currentTimeMillis();
/* 1015:1263 */     if (!jdField_do)
/* 1016:     */     {
/* 1017:1264 */       localObject = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/* 1018:     */       
/* 1019:1266 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addMailMessage", (Throwable)localObject);
/* 1020:1267 */       throw ((Throwable)localObject);
/* 1021:     */     }
/* 1022:1270 */     Object localObject = a.getConnection();
/* 1023:     */     try
/* 1024:     */     {
/* 1025:1272 */       DBMail.addMailMessage(paramUser, paramMessage, (DBConnection)localObject);
/* 1026:     */     }
/* 1027:     */     catch (BSException localBSException1)
/* 1028:     */     {
/* 1029:1274 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/* 1030:1275 */       DebugLog.throwing("com.ffusion.banksim.Banksim:addMailMessage", localBSException2);
/* 1031:1276 */       throw localBSException2;
/* 1032:     */     }
/* 1033:1279 */     a("AddMailMessage", l);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public static final Enumeration getMailMessages(User paramUser)
/* 1037:     */     throws BSException
/* 1038:     */   {
/* 1039:1294 */     long l = System.currentTimeMillis();
/* 1040:1296 */     if (!jdField_do)
/* 1041:     */     {
/* 1042:1297 */       localObject = new BSException(1000, MessageText.getMessage("ERR_NOT_INITIALIZED"));
/* 1043:     */       
/* 1044:1299 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getMailMessages", (Throwable)localObject);
/* 1045:1300 */       throw ((Throwable)localObject);
/* 1046:     */     }
/* 1047:1303 */     Object localObject = a.getConnection();
/* 1048:1304 */     Enumeration localEnumeration = null;
/* 1049:     */     try
/* 1050:     */     {
/* 1051:1306 */       localEnumeration = DBMail.getMailMessages(paramUser, (DBConnection)localObject);
/* 1052:     */     }
/* 1053:     */     catch (BSException localBSException1)
/* 1054:     */     {
/* 1055:1308 */       BSException localBSException2 = new BSException(localBSException1.getErrorCode(), localBSException1);
/* 1056:1309 */       DebugLog.throwing("com.ffusion.banksim.Banksim:getMailMessages", localBSException2);
/* 1057:1310 */       throw localBSException2;
/* 1058:     */     }
/* 1059:1313 */     a("GetMailMessages", l);
/* 1060:     */     
/* 1061:1315 */     return localEnumeration;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public static void addLockboxSummary(LockboxSummary paramLockboxSummary, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/* 1065:     */     throws BSException
/* 1066:     */   {
/* 1067:1332 */     BSLockboxSummary.add(paramLockboxSummary, paramInt, paramDBConnection, paramHashMap);
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public static LockboxSummaries getLockboxSummaries(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1071:     */     throws BSException
/* 1072:     */   {
/* 1073:1347 */     DBConnection localDBConnection = a.getConnection();
/* 1074:     */     try
/* 1075:     */     {
/* 1076:1349 */       return BSLockboxSummary.getLockboxSummaries(paramLockboxAccount, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1077:     */     }
/* 1078:     */     finally
/* 1079:     */     {
/* 1080:1351 */       a.releaseConnection(localDBConnection);
/* 1081:     */     }
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public static void addDisbursementSummary(DisbursementSummary paramDisbursementSummary, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/* 1085:     */     throws BSException
/* 1086:     */   {
/* 1087:1368 */     BSDsbSummary.addDisbursementSummary(paramDisbursementSummary, paramInt, paramDBConnection, paramHashMap);
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public static DisbursementSummaries getDisbursementSummaries(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1091:     */     throws BSException
/* 1092:     */   {
/* 1093:1384 */     DBConnection localDBConnection = a.getConnection();
/* 1094:     */     try
/* 1095:     */     {
/* 1096:1386 */       return BSDsbSummary.getDisbursementSummaries(paramDisbursementAccount, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1097:     */     }
/* 1098:     */     finally
/* 1099:     */     {
/* 1100:1388 */       a.releaseConnection(localDBConnection);
/* 1101:     */     }
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public static DisbursementSummaries getDisbursementSummariesForPresentment(DisbursementAccount paramDisbursementAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1105:     */     throws BSException
/* 1106:     */   {
/* 1107:1404 */     DBConnection localDBConnection = a.getConnection();
/* 1108:     */     try
/* 1109:     */     {
/* 1110:1406 */       return BSDsbSummary.getDisbursementSummariesForPresentment(paramDisbursementAccount, paramString, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1111:     */     }
/* 1112:     */     finally
/* 1113:     */     {
/* 1114:1409 */       a.releaseConnection(localDBConnection);
/* 1115:     */     }
/* 1116:     */   }
/* 1117:     */   
/* 1118:     */   public static DisbursementPresentmentSummaries getDisbursementPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1119:     */     throws BSException
/* 1120:     */   {
/* 1121:1426 */     DBConnection localDBConnection = a.getConnection();
/* 1122:     */     try
/* 1123:     */     {
/* 1124:1428 */       return BSDsbSummary.getDisbursementPresentmentSummaries(paramDisbursementAccounts, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1125:     */     }
/* 1126:     */     finally
/* 1127:     */     {
/* 1128:1430 */       a.releaseConnection(localDBConnection);
/* 1129:     */     }
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public static void addDisbursementTransactions(DisbursementAccount paramDisbursementAccount, DisbursementTransactions paramDisbursementTransactions, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/* 1133:     */     throws BSException
/* 1134:     */   {
/* 1135:1449 */     BSDsbTransactions.addTransactions(paramDisbursementAccount, paramDisbursementTransactions, paramInt, paramDBConnection, paramHashMap);
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public static DisbursementTransactions getDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1139:     */     throws BSException
/* 1140:     */   {
/* 1141:1467 */     return getDisbursementTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public static DisbursementTransactions getDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
/* 1145:     */     throws BSException
/* 1146:     */   {
/* 1147:1486 */     DBConnection localDBConnection = a.getConnection();
/* 1148:     */     try
/* 1149:     */     {
/* 1150:1488 */       return BSDsbTransactions.getTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, localDBConnection, paramHashMap);
/* 1151:     */     }
/* 1152:     */     finally
/* 1153:     */     {
/* 1154:1490 */       a.releaseConnection(localDBConnection);
/* 1155:     */     }
/* 1156:     */   }
/* 1157:     */   
/* 1158:     */   public static DisbursementTransactions getPagedDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1159:     */     throws BSException
/* 1160:     */   {
/* 1161:1509 */     return getPagedDisbursementTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public static DisbursementTransactions getPagedDisbursementTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
/* 1165:     */     throws BSException
/* 1166:     */   {
/* 1167:1529 */     DBConnection localDBConnection = a.getConnection();
/* 1168:     */     try
/* 1169:     */     {
/* 1170:1531 */       return BSDsbTransactions.getPagedTransactions(paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, localDBConnection, paramHashMap);
/* 1171:     */     }
/* 1172:     */     finally
/* 1173:     */     {
/* 1174:1533 */       a.releaseConnection(localDBConnection);
/* 1175:     */     }
/* 1176:     */   }
/* 1177:     */   
/* 1178:     */   public static DisbursementTransactions getRecentDisbursementTransactions(DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
/* 1179:     */     throws BSException
/* 1180:     */   {
/* 1181:1548 */     DBConnection localDBConnection = a.getConnection();
/* 1182:     */     try
/* 1183:     */     {
/* 1184:1550 */       return BSDsbTransactions.getRecentTransactions(paramDisbursementAccount, localDBConnection, paramHashMap);
/* 1185:     */     }
/* 1186:     */     finally
/* 1187:     */     {
/* 1188:1552 */       a.releaseConnection(localDBConnection);
/* 1189:     */     }
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public static DisbursementTransactions getNextDisbursementTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
/* 1193:     */     throws BSException
/* 1194:     */   {
/* 1195:1569 */     DBConnection localDBConnection = a.getConnection();
/* 1196:     */     try
/* 1197:     */     {
/* 1198:1571 */       return BSDsbTransactions.getNextTransactions(paramDisbursementAccount, paramLong, localDBConnection, paramHashMap);
/* 1199:     */     }
/* 1200:     */     finally
/* 1201:     */     {
/* 1202:1573 */       a.releaseConnection(localDBConnection);
/* 1203:     */     }
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public static DisbursementTransactions getPreviousDisbursementTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
/* 1207:     */     throws BSException
/* 1208:     */   {
/* 1209:1590 */     DBConnection localDBConnection = a.getConnection();
/* 1210:     */     try
/* 1211:     */     {
/* 1212:1592 */       return BSDsbTransactions.getPreviousTransactions(paramDisbursementAccount, paramLong, localDBConnection, paramHashMap);
/* 1213:     */     }
/* 1214:     */     finally
/* 1215:     */     {
/* 1216:1594 */       a.releaseConnection(localDBConnection);
/* 1217:     */     }
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   public static void addLockboxTransactions(LockboxAccount paramLockboxAccount, LockboxTransactions paramLockboxTransactions, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/* 1221:     */     throws BSException
/* 1222:     */   {
/* 1223:1615 */     BSLBTransactions.addTransactions(paramLockboxAccount, paramLockboxTransactions, paramInt, paramDBConnection, paramHashMap);
/* 1224:     */   }
/* 1225:     */   
/* 1226:     */   public static LockboxTransactions getLockboxTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1227:     */     throws BSException
/* 1228:     */   {
/* 1229:1632 */     DBConnection localDBConnection = a.getConnection();
/* 1230:     */     try
/* 1231:     */     {
/* 1232:1634 */       return BSLBTransactions.getTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1233:     */     }
/* 1234:     */     finally
/* 1235:     */     {
/* 1236:1636 */       a.releaseConnection(localDBConnection);
/* 1237:     */     }
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public static LockboxTransactions getPagedLockboxTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1241:     */     throws BSException
/* 1242:     */   {
/* 1243:1656 */     DBConnection localDBConnection = a.getConnection();
/* 1244:     */     try
/* 1245:     */     {
/* 1246:1658 */       return BSLBTransactions.getPagedTransactions(paramLockboxAccount, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1247:     */     }
/* 1248:     */     finally
/* 1249:     */     {
/* 1250:1660 */       a.releaseConnection(localDBConnection);
/* 1251:     */     }
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public static LockboxTransactions getRecentLockboxTransactions(LockboxAccount paramLockboxAccount, HashMap paramHashMap)
/* 1255:     */     throws BSException
/* 1256:     */   {
/* 1257:1673 */     DBConnection localDBConnection = a.getConnection();
/* 1258:     */     try
/* 1259:     */     {
/* 1260:1675 */       return BSLBTransactions.getRecentTransactions(paramLockboxAccount, localDBConnection, paramHashMap);
/* 1261:     */     }
/* 1262:     */     finally
/* 1263:     */     {
/* 1264:1677 */       a.releaseConnection(localDBConnection);
/* 1265:     */     }
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   public static LockboxTransactions getNextLockboxTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
/* 1269:     */     throws BSException
/* 1270:     */   {
/* 1271:1693 */     DBConnection localDBConnection = a.getConnection();
/* 1272:     */     try
/* 1273:     */     {
/* 1274:1695 */       return BSLBTransactions.getNextTransactions(paramLockboxAccount, paramLong, localDBConnection, paramHashMap);
/* 1275:     */     }
/* 1276:     */     finally
/* 1277:     */     {
/* 1278:1697 */       a.releaseConnection(localDBConnection);
/* 1279:     */     }
/* 1280:     */   }
/* 1281:     */   
/* 1282:     */   public static LockboxTransactions getPreviousLockboxTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
/* 1283:     */     throws BSException
/* 1284:     */   {
/* 1285:1713 */     DBConnection localDBConnection = a.getConnection();
/* 1286:     */     try
/* 1287:     */     {
/* 1288:1715 */       return BSLBTransactions.getPreviousTransactions(paramLockboxAccount, paramLong, localDBConnection, paramHashMap);
/* 1289:     */     }
/* 1290:     */     finally
/* 1291:     */     {
/* 1292:1717 */       a.releaseConnection(localDBConnection);
/* 1293:     */     }
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   public static void addLockboxCreditItems(LockboxAccount paramLockboxAccount, LockboxCreditItems paramLockboxCreditItems, int paramInt, DBConnection paramDBConnection, HashMap paramHashMap)
/* 1297:     */     throws BSException
/* 1298:     */   {
/* 1299:1737 */     BSLBCreditItems.addItems(paramLockboxAccount, paramLockboxCreditItems, paramInt, paramDBConnection, paramHashMap);
/* 1300:     */   }
/* 1301:     */   
/* 1302:     */   public static LockboxCreditItems getLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1303:     */     throws BSException
/* 1304:     */   {
/* 1305:1757 */     DBConnection localDBConnection = a.getConnection();
/* 1306:     */     try
/* 1307:     */     {
/* 1308:1759 */       return BSLBCreditItems.getItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1309:     */     }
/* 1310:     */     finally
/* 1311:     */     {
/* 1312:1761 */       a.releaseConnection(localDBConnection);
/* 1313:     */     }
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public static LockboxCreditItems getPagedLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
/* 1317:     */     throws BSException
/* 1318:     */   {
/* 1319:1783 */     DBConnection localDBConnection = a.getConnection();
/* 1320:     */     try
/* 1321:     */     {
/* 1322:1785 */       return BSLBCreditItems.getPagedLockboxCreditItems(paramLockboxAccount, paramString, paramCalendar1, paramCalendar2, localDBConnection, paramHashMap);
/* 1323:     */     }
/* 1324:     */     finally
/* 1325:     */     {
/* 1326:1787 */       a.releaseConnection(localDBConnection);
/* 1327:     */     }
/* 1328:     */   }
/* 1329:     */   
/* 1330:     */   public static LockboxCreditItems getRecentLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
/* 1331:     */     throws BSException
/* 1332:     */   {
/* 1333:1802 */     DBConnection localDBConnection = a.getConnection();
/* 1334:     */     try
/* 1335:     */     {
/* 1336:1804 */       return BSLBCreditItems.getRecentItems(paramLockboxAccount, paramString, localDBConnection, paramHashMap);
/* 1337:     */     }
/* 1338:     */     finally
/* 1339:     */     {
/* 1340:1806 */       a.releaseConnection(localDBConnection);
/* 1341:     */     }
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public static LockboxCreditItems getNextLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
/* 1345:     */     throws BSException
/* 1346:     */   {
/* 1347:1823 */     DBConnection localDBConnection = a.getConnection();
/* 1348:     */     try
/* 1349:     */     {
/* 1350:1825 */       return BSLBCreditItems.getNextItems(paramLockboxAccount, paramString, paramLong, localDBConnection, paramHashMap);
/* 1351:     */     }
/* 1352:     */     finally
/* 1353:     */     {
/* 1354:1827 */       a.releaseConnection(localDBConnection);
/* 1355:     */     }
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   public static LockboxCreditItems getPreviousLockboxCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
/* 1359:     */     throws BSException
/* 1360:     */   {
/* 1361:1844 */     DBConnection localDBConnection = a.getConnection();
/* 1362:     */     try
/* 1363:     */     {
/* 1364:1846 */       return BSLBCreditItems.getPreviousItems(paramLockboxAccount, paramString, paramLong, localDBConnection, paramHashMap);
/* 1365:     */     }
/* 1366:     */     finally
/* 1367:     */     {
/* 1368:1848 */       a.releaseConnection(localDBConnection);
/* 1369:     */     }
/* 1370:     */   }
/* 1371:     */   
/* 1372:     */   public static IReportResult getDisbursementReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
/* 1373:     */     throws BSException
/* 1374:     */   {
/* 1375:1862 */     return null;
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public static IReportResult getLockboxReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
/* 1379:     */     throws BSException
/* 1380:     */   {
/* 1381:1875 */     return null;
/* 1382:     */   }
/* 1383:     */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.BankSim
 * JD-Core Version:    0.7.0.1
 */