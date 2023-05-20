/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import com.ffusion.util.logging.DebugLog;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.sql.CallableStatement;
/*   8:    */ import java.sql.Connection;
/*   9:    */ import java.sql.DatabaseMetaData;
/*  10:    */ import java.sql.DriverManager;
/*  11:    */ import java.sql.PreparedStatement;
/*  12:    */ import java.sql.ResultSet;
/*  13:    */ import java.sql.SQLException;
/*  14:    */ import java.sql.Statement;
/*  15:    */ import java.util.Arrays;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.Properties;
/*  18:    */ import java.util.logging.Level;
/*  19:    */ 
/*  20:    */ public class DBConnection
/*  21:    */   implements IBSErrConstants
/*  22:    */ {
/*  23:    */   public static final String JCONNECT4_DRIVER = "com.sybase.jdbc.SybDriver";
/*  24:    */   public static final String JCONNECT5_DRIVER = "com.sybase.jdbc2.jdbc.SybDriver";
/*  25:    */   public static final String JCONNECT6_DRIVER = "com.sybase.jdbc3.jdbc.SybDriver";
/*  26:    */   public static final String JDBC_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
/*  27:    */   public static final String JDBC_DB2_APP_DRIVER = "COM.ibm.db2.jdbc.app.DB2Driver";
/*  28:    */   public static final String JDBC_DB2_NET_DRIVER = "COM.ibm.db2.jdbc.net.DB2Driver";
/*  29:    */   public static final String JDBC_DB2_UN2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
/*  30:    */   public static final String JDBC_DB2390_DRIVER = "COM.ibm.db2.jdbc.app.DB2Driver";
/*  31:    */   public static final String JDBC_MSSQL_DRIVER = "com.jnetdirect.jsql.JSQLDriver";
/*  32:    */   static final int jdField_int = 1073741824;
/*  33:    */   static final String jdField_for = "JZ006";
/*  34:    */   static final String c = "JZ0F2";
/*  35:    */   static final int jdField_if = 3;
/*  36:    */   private static final String jdField_else = "set chained off";
/*  37:    */   private static final String b = "set chained on";
/*  38:    */   
/*  39:    */   private static class a
/*  40:    */   {
/*  41:    */     private static final int jdField_int = 50;
/*  42:    */     private static final int a = 89;
/*  43:    */     
/*  44:    */     a(DBConnection.1 param1)
/*  45:    */     {
/*  46: 66 */       this();
/*  47:    */     }
/*  48:    */     
/*  49: 76 */     private final HashMap jdField_for = new HashMap(89);
/*  50: 80 */     private final String[] jdField_do = new String[50];
/*  51: 81 */     private int jdField_if = 0;
/*  52:    */     
/*  53:    */     final PreparedStatement a(String paramString)
/*  54:    */     {
/*  55: 88 */       return (PreparedStatement)this.jdField_for.get(paramString);
/*  56:    */     }
/*  57:    */     
/*  58:    */     final void a(String paramString, PreparedStatement paramPreparedStatement)
/*  59:    */     {
/*  60: 95 */       if (this.jdField_for.size() == 50) {
/*  61:    */         try
/*  62:    */         {
/*  63: 99 */           PreparedStatement localPreparedStatement = (PreparedStatement)this.jdField_for.remove(this.jdField_do[this.jdField_if]);
/*  64:100 */           localPreparedStatement.close();
/*  65:    */         }
/*  66:    */         catch (SQLException localSQLException) {}
/*  67:    */       }
/*  68:105 */       this.jdField_for.put(paramString, paramPreparedStatement);
/*  69:106 */       this.jdField_do[this.jdField_if] = paramString;
/*  70:107 */       this.jdField_if = ((this.jdField_if + 1) % 50);
/*  71:    */     }
/*  72:    */     
/*  73:    */     final void a()
/*  74:    */     {
/*  75:113 */       this.jdField_if = 0;
/*  76:114 */       for (int i = 0; i < 50; i++) {
/*  77:115 */         if (this.jdField_do[i] != null)
/*  78:    */         {
/*  79:    */           try
/*  80:    */           {
/*  81:117 */             PreparedStatement localPreparedStatement = (PreparedStatement)this.jdField_for.remove(this.jdField_do[i]);
/*  82:118 */             if (localPreparedStatement != null) {
/*  83:119 */               localPreparedStatement.close();
/*  84:    */             }
/*  85:    */           }
/*  86:    */           catch (SQLException localSQLException) {}
/*  87:124 */           this.jdField_do[i] = null;
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:    */     
/*  92:    */     private a() {}
/*  93:    */   }
/*  94:    */   
/*  95:    */   private DBConnection(BSDBParams paramBSDBParams)
/*  96:    */   {
/*  97:137 */     this.jdField_byte = true;
/*  98:138 */     this.jdField_try = paramBSDBParams;
/*  99:139 */     this.jdField_goto = new Properties();
/* 100:140 */     this.jdField_goto.put("user", this.jdField_try.getUser());
/* 101:141 */     this.jdField_goto.put("password", this.jdField_try.getPassword());
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void finalize()
/* 105:    */   {
/* 106:148 */     close();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static DBConnection create(BSDBParams paramBSDBParams)
/* 110:    */     throws BSException
/* 111:    */   {
/* 112:156 */     DBConnection localDBConnection = null;
/* 113:157 */     switch (paramBSDBParams.getConnectionType())
/* 114:    */     {
/* 115:    */     case 1: 
/* 116:159 */       localDBConnection = jdField_for(paramBSDBParams);
/* 117:160 */       break;
/* 118:    */     case 2: 
/* 119:162 */       if (paramBSDBParams.isHA()) {
/* 120:163 */         localDBConnection = a(paramBSDBParams);
/* 121:    */       } else {
/* 122:165 */         localDBConnection = jdField_for(paramBSDBParams);
/* 123:    */       }
/* 124:167 */       break;
/* 125:    */     case 3: 
/* 126:169 */       localDBConnection = jdMethod_new(paramBSDBParams);
/* 127:170 */       break;
/* 128:    */     case 7: 
/* 129:172 */       localDBConnection = jdMethod_do(paramBSDBParams);
/* 130:173 */       break;
/* 131:    */     case 4: 
/* 132:175 */       localDBConnection = jdMethod_try(paramBSDBParams);
/* 133:176 */       break;
/* 134:    */     case 5: 
/* 135:178 */       localDBConnection = jdField_int(paramBSDBParams);
/* 136:179 */       break;
/* 137:    */     case 6: 
/* 138:182 */       localDBConnection = jdField_if(paramBSDBParams);
/* 139:183 */       break;
/* 140:    */     default: 
/* 141:186 */       a(2, "ERR_UNKNOWN_CONNECTION_TYPE");
/* 142:    */     }
/* 143:191 */     return localDBConnection;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static DBConnection a(BSDBParams paramBSDBParams)
/* 147:    */     throws BSException
/* 148:    */   {
/* 149:202 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 150:203 */     localDBConnection.jdField_case = ("jdbc:sybase:jndi:" + paramBSDBParams.getJNDIURL() + paramBSDBParams.getJNDICtx());
/* 151:    */     
/* 152:    */ 
/* 153:206 */     localDBConnection.jdField_goto.put("REQUEST_HA_SESSION", "true");
/* 154:207 */     localDBConnection.jdField_goto.put("java.naming.factory.initial", paramBSDBParams.getCtxFactory());
/* 155:208 */     localDBConnection.jdField_goto.put("java.naming.provider.url", paramBSDBParams.getJNDIURL());
/* 156:210 */     if (paramBSDBParams.getDBDriver() == null) {
/* 157:211 */       localDBConnection.jdField_long = "com.sybase.jdbc2.jdbc.SybDriver";
/* 158:    */     } else {
/* 159:213 */       localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 160:    */     }
/* 161:216 */     return localDBConnection;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private static DBConnection jdField_for(BSDBParams paramBSDBParams)
/* 165:    */     throws BSException
/* 166:    */   {
/* 167:226 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 168:227 */     localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 169:229 */     if (paramBSDBParams.getDBDriver() == null) {
/* 170:231 */       localDBConnection.jdField_long = "com.sybase.jdbc3.jdbc.SybDriver";
/* 171:    */     } else {
/* 172:234 */       localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 173:    */     }
/* 174:237 */     return localDBConnection;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private static DBConnection jdMethod_new(BSDBParams paramBSDBParams)
/* 178:    */     throws BSException
/* 179:    */   {
/* 180:247 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 181:248 */     if (paramBSDBParams.isNativeDriver())
/* 182:    */     {
/* 183:249 */       localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 184:250 */       if (paramBSDBParams.getDBDriver() == null) {
/* 185:251 */         localDBConnection.jdField_long = "COM.ibm.db2.jdbc.app.DB2Driver";
/* 186:    */       } else {
/* 187:253 */         localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 188:    */       }
/* 189:    */     }
/* 190:    */     else
/* 191:    */     {
/* 192:256 */       localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 193:257 */       if (paramBSDBParams.getDBDriver() == null) {
/* 194:258 */         localDBConnection.jdField_long = "COM.ibm.db2.jdbc.net.DB2Driver";
/* 195:    */       } else {
/* 196:260 */         localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 197:    */       }
/* 198:    */     }
/* 199:264 */     return localDBConnection;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static DBConnection jdMethod_do(BSDBParams paramBSDBParams)
/* 203:    */     throws BSException
/* 204:    */   {
/* 205:273 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 206:274 */     localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 207:275 */     if (paramBSDBParams.getDBDriver() == null) {
/* 208:276 */       localDBConnection.jdField_long = "com.ibm.db2.jcc.DB2Driver";
/* 209:    */     } else {
/* 210:278 */       localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 211:    */     }
/* 212:281 */     return localDBConnection;
/* 213:    */   }
/* 214:    */   
/* 215:    */   private static DBConnection jdField_int(BSDBParams paramBSDBParams)
/* 216:    */     throws BSException
/* 217:    */   {
/* 218:290 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 219:291 */     localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 220:292 */     if (paramBSDBParams.getDBDriver() == null) {
/* 221:293 */       localDBConnection.jdField_long = "COM.ibm.db2.jdbc.app.DB2Driver";
/* 222:    */     } else {
/* 223:295 */       localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 224:    */     }
/* 225:298 */     return localDBConnection;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private static DBConnection jdMethod_try(BSDBParams paramBSDBParams)
/* 229:    */     throws BSException
/* 230:    */   {
/* 231:308 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 232:    */     
/* 233:310 */     localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 234:311 */     if (paramBSDBParams.getDBDriver() == null) {
/* 235:312 */       localDBConnection.jdField_long = "oracle.jdbc.driver.OracleDriver";
/* 236:    */     } else {
/* 237:314 */       localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 238:    */     }
/* 239:317 */     return localDBConnection;
/* 240:    */   }
/* 241:    */   
/* 242:    */   private static DBConnection jdField_if(BSDBParams paramBSDBParams)
/* 243:    */     throws BSException
/* 244:    */   {
/* 245:327 */     DBConnection localDBConnection = new DBConnection(paramBSDBParams);
/* 246:    */     
/* 247:329 */     localDBConnection.jdField_case = paramBSDBParams.getDBUrl();
/* 248:330 */     if (paramBSDBParams.getDBDriver() == null) {
/* 249:331 */       localDBConnection.jdField_long = "com.jnetdirect.jsql.JSQLDriver";
/* 250:    */     } else {
/* 251:333 */       localDBConnection.jdField_long = paramBSDBParams.getDBDriver();
/* 252:    */     }
/* 253:335 */     return localDBConnection;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public synchronized void open()
/* 257:    */     throws BSException
/* 258:    */   {
/* 259:344 */     close();
/* 260:    */     try
/* 261:    */     {
/* 262:    */       try
/* 263:    */       {
/* 264:349 */         Class.forName(this.jdField_long);
/* 265:    */       }
/* 266:    */       catch (Exception localException1)
/* 267:    */       {
/* 268:355 */         if (this.jdField_long == "com.sybase.jdbc3.jdbc.SybDriver")
/* 269:    */         {
/* 270:356 */           this.jdField_long = "com.sybase.jdbc2.jdbc.SybDriver";
/* 271:    */           try
/* 272:    */           {
/* 273:358 */             Class.forName(this.jdField_long);
/* 274:    */           }
/* 275:    */           catch (Exception localException3)
/* 276:    */           {
/* 277:364 */             if (this.jdField_long == "com.sybase.jdbc2.jdbc.SybDriver")
/* 278:    */             {
/* 279:365 */               this.jdField_long = "com.sybase.jdbc.SybDriver";
/* 280:366 */               Class.forName(this.jdField_long);
/* 281:    */             }
/* 282:    */             else
/* 283:    */             {
/* 284:369 */               throw localException3;
/* 285:    */             }
/* 286:    */           }
/* 287:    */         }
/* 288:    */         else
/* 289:    */         {
/* 290:376 */           throw localException1;
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:    */     catch (Exception localException2)
/* 295:    */     {
/* 296:382 */       a(3, "ERR_JDBC_DRIVER_NOT_FOUND", this.jdField_long, localException2);
/* 297:    */     }
/* 298:    */     try
/* 299:    */     {
/* 300:389 */       this.jdField_null = DriverManager.getConnection(this.jdField_case, this.jdField_goto);
/* 301:    */       
/* 302:    */ 
/* 303:    */ 
/* 304:393 */       this.jdField_char = true;
/* 305:    */       
/* 306:    */ 
/* 307:    */ 
/* 308:397 */       this.jdField_void = this.jdField_null.getMetaData();
/* 309:398 */       if (this.jdField_void.supportsTransactionIsolationLevel(2)) {
/* 310:399 */         this.jdField_null.setTransactionIsolation(2);
/* 311:    */       }
/* 312:404 */       if ((this.jdField_try.getConnectionType() == 1) || (this.jdField_try.getConnectionType() == 2) || (this.jdField_try.getConnectionType() == 6))
/* 313:    */       {
/* 314:409 */         Statement localStatement = this.jdField_null.createStatement();
/* 315:410 */         localStatement.executeUpdate("set textsize 1073741824");
/* 316:411 */         localStatement.close();
/* 317:    */       }
/* 318:    */     }
/* 319:    */     catch (SQLException localSQLException)
/* 320:    */     {
/* 321:414 */       close();
/* 322:415 */       a(4, "ERR_COULD_NOT_CONNECT_TO_DB", this.jdField_long, this.jdField_case, localSQLException);
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public synchronized void close()
/* 327:    */   {
/* 328:429 */     if (this.jdField_char) {
/* 329:    */       try
/* 330:    */       {
/* 331:434 */         this.jdField_char = false;
/* 332:    */         
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:439 */         this.jdField_byte = true;
/* 337:    */         
/* 338:    */ 
/* 339:    */ 
/* 340:443 */         this.jdField_do.a();
/* 341:    */         
/* 342:445 */         this.jdField_null.close();
/* 343:    */       }
/* 344:    */       catch (SQLException localSQLException) {}
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public synchronized boolean setAutoCommit(boolean paramBoolean)
/* 349:    */     throws SQLException
/* 350:    */   {
/* 351:461 */     if (this.jdField_char)
/* 352:    */     {
/* 353:471 */       this.jdField_null.setAutoCommit(paramBoolean);
/* 354:472 */       this.jdField_byte = paramBoolean;
/* 355:473 */       return true;
/* 356:    */     }
/* 357:475 */     return false;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public synchronized boolean isAlive()
/* 361:    */   {
/* 362:    */     try
/* 363:    */     {
/* 364:    */       DBResultSet localDBResultSet;
/* 365:487 */       if (this.jdField_try.getConnectionType() == 4)
/* 366:    */       {
/* 367:489 */         localDBResultSet = prepareQuery("select 1 from dual");
/* 368:490 */         localDBResultSet.open();
/* 369:491 */         localDBResultSet.close();
/* 370:492 */         return true;
/* 371:    */       }
/* 372:493 */       if ((this.jdField_try.getConnectionType() == 3) || (this.jdField_try.getConnectionType() == 7))
/* 373:    */       {
/* 374:496 */         localDBResultSet = prepareQuery("select grantee from sysibm.sysdbauth");
/* 375:497 */         localDBResultSet.open();
/* 376:498 */         localDBResultSet.close();
/* 377:499 */         return true;
/* 378:    */       }
/* 379:501 */       boolean bool = this.jdField_byte;
/* 380:502 */       return (setAutoCommit(!bool)) && (setAutoCommit(bool));
/* 381:    */     }
/* 382:    */     catch (Exception localException) {}
/* 383:505 */     return false;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public synchronized boolean isAutoCommit()
/* 387:    */   {
/* 388:514 */     return this.jdField_byte;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public synchronized void commit()
/* 392:    */     throws SQLException
/* 393:    */   {
/* 394:522 */     if (this.jdField_char)
/* 395:    */     {
/* 396:523 */       DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.commit()");
/* 397:524 */       this.jdField_null.commit();
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   public synchronized void rollback()
/* 402:    */     throws SQLException
/* 403:    */   {
/* 404:533 */     if (this.jdField_char)
/* 405:    */     {
/* 406:534 */       DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.rollback()");
/* 407:535 */       this.jdField_null.rollback();
/* 408:    */     }
/* 409:    */   }
/* 410:    */   
/* 411:    */   public synchronized int executeUpdate(String paramString)
/* 412:    */     throws SQLException
/* 413:    */   {
/* 414:545 */     return executeUpdate(paramString, null);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public synchronized int executeUpdate(String paramString, Object[] paramArrayOfObject)
/* 418:    */     throws SQLException
/* 419:    */   {
/* 420:556 */     if (paramArrayOfObject != null) {
/* 421:557 */       DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.executeUpdate()\nsql: " + paramString + "\n" + "inArgs[]: " + Arrays.asList(paramArrayOfObject).toString());
/* 422:    */     } else {
/* 423:562 */       DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.executeUpdate()\nsql: " + paramString);
/* 424:    */     }
/* 425:568 */     PreparedStatement localPreparedStatement = this.jdField_do.a(paramString);
/* 426:569 */     for (int i = 0;; i++) {
/* 427:    */       try
/* 428:    */       {
/* 429:572 */         if (localPreparedStatement == null)
/* 430:    */         {
/* 431:573 */           String str = DBSqlUtils.parseStmt(paramString, this.jdField_try.getConnectionType());
/* 432:576 */           if (!str.equals(paramString)) {
/* 433:578 */             if (paramArrayOfObject != null) {
/* 434:579 */               DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.executeUpdate()\nMODIFIED sql: " + str + "\n" + "inArgs[]: " + Arrays.asList(paramArrayOfObject).toString());
/* 435:    */             } else {
/* 436:584 */               DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.executeUpdate()\nMODIFIED sql: " + str);
/* 437:    */             }
/* 438:    */           }
/* 439:589 */           if (str.trim().length() > 0)
/* 440:    */           {
/* 441:590 */             localPreparedStatement = this.jdField_null.prepareStatement(str);
/* 442:591 */             this.jdField_do.a(paramString, localPreparedStatement);
/* 443:    */           }
/* 444:    */         }
/* 445:595 */         if (localPreparedStatement == null) {
/* 446:596 */           return 0;
/* 447:    */         }
/* 448:598 */         DBSqlUtils.a(localPreparedStatement, paramArrayOfObject, this.jdField_try);
/* 449:599 */         return localPreparedStatement.executeUpdate();
/* 450:    */       }
/* 451:    */       catch (SQLException localSQLException)
/* 452:    */       {
/* 453:604 */         if ((!this.jdField_try.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
/* 454:608 */           throw localSQLException;
/* 455:    */         }
/* 456:610 */         if (i >= 3) {
/* 457:612 */           throw localSQLException;
/* 458:    */         }
/* 459:    */       }
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public synchronized DBResultSet prepareQuery(String paramString)
/* 464:    */     throws SQLException
/* 465:    */   {
/* 466:625 */     DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.prepareQuery()\nquery: " + paramString);
/* 467:    */     
/* 468:627 */     PreparedStatement localPreparedStatement = this.jdField_do.a(paramString);
/* 469:628 */     for (int i = 0;; i++) {
/* 470:    */       try
/* 471:    */       {
/* 472:630 */         if (localPreparedStatement == null)
/* 473:    */         {
/* 474:631 */           String str = DBSqlUtils.parseStmt(paramString, this.jdField_try.getConnectionType());
/* 475:    */           
/* 476:633 */           localPreparedStatement = this.jdField_null.prepareStatement(str);
/* 477:634 */           this.jdField_do.a(paramString, localPreparedStatement);
/* 478:    */         }
/* 479:636 */         return new DBResultSet(localPreparedStatement, this.jdField_try);
/* 480:    */       }
/* 481:    */       catch (SQLException localSQLException)
/* 482:    */       {
/* 483:640 */         if ((!this.jdField_try.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
/* 484:644 */           throw localSQLException;
/* 485:    */         }
/* 486:646 */         if (i >= 3) {
/* 487:647 */           throw localSQLException;
/* 488:    */         }
/* 489:    */       }
/* 490:    */     }
/* 491:    */   }
/* 492:    */   
/* 493:    */   public synchronized int executeStoredProcedure(String paramString, Object[] paramArrayOfObject)
/* 494:    */     throws SQLException
/* 495:    */   {
/* 496:661 */     DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBConnection.executeStoredProcedure()\nsql: " + paramString + "\n" + "inArgs[]: " + Arrays.asList(paramArrayOfObject).toString());
/* 497:    */     
/* 498:    */ 
/* 499:    */ 
/* 500:665 */     CallableStatement localCallableStatement = (CallableStatement)this.jdField_do.a(paramString);
/* 501:666 */     for (int i = 0;; i++) {
/* 502:    */       try
/* 503:    */       {
/* 504:668 */         if (localCallableStatement == null)
/* 505:    */         {
/* 506:669 */           String str = DBSqlUtils.parseStmt(paramString, this.jdField_try.getConnectionType());
/* 507:    */           
/* 508:671 */           localCallableStatement = this.jdField_null.prepareCall(str);
/* 509:672 */           this.jdField_do.a(paramString, localCallableStatement);
/* 510:    */         }
/* 511:675 */         DBSqlUtils.a(localCallableStatement, paramArrayOfObject, this.jdField_try);
/* 512:676 */         localCallableStatement.registerOutParameter(paramArrayOfObject.length + 1, 4);
/* 513:677 */         localCallableStatement.execute();
/* 514:678 */         return localCallableStatement.getInt(paramArrayOfObject.length + 1);
/* 515:    */       }
/* 516:    */       catch (SQLException localSQLException)
/* 517:    */       {
/* 518:682 */         if ((!this.jdField_try.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
/* 519:686 */           throw localSQLException;
/* 520:    */         }
/* 521:688 */         if (i >= 3) {
/* 522:690 */           throw localSQLException;
/* 523:    */         }
/* 524:    */       }
/* 525:    */     }
/* 526:    */   }
/* 527:    */   
/* 528:    */   public synchronized PreparedStatement prepareStatement(DBConnection paramDBConnection, String paramString)
/* 529:    */     throws Exception
/* 530:    */   {
/* 531:704 */     PreparedStatement localPreparedStatement = this.jdField_null.prepareStatement(paramString);
/* 532:705 */     if (localPreparedStatement == null) {
/* 533:706 */       throw new Exception("Couldn't prepare statement");
/* 534:    */     }
/* 535:707 */     return localPreparedStatement;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public static void closeStatement(Statement paramStatement)
/* 539:    */   {
/* 540:    */     try
/* 541:    */     {
/* 542:718 */       if (paramStatement != null) {
/* 543:719 */         paramStatement.close();
/* 544:    */       }
/* 545:    */     }
/* 546:    */     catch (Exception localException) {}
/* 547:    */   }
/* 548:    */   
/* 549:    */   public static int executeUpdate(PreparedStatement paramPreparedStatement, String paramString)
/* 550:    */     throws Exception
/* 551:    */   {
/* 552:732 */     long l = System.currentTimeMillis();
/* 553:733 */     int i = 0;
/* 554:    */     try
/* 555:    */     {
/* 556:735 */       i = paramPreparedStatement.executeUpdate();
/* 557:    */     }
/* 558:    */     finally {}
/* 559:739 */     return i;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public static ResultSet executeQuery(PreparedStatement paramPreparedStatement, String paramString)
/* 563:    */     throws Exception
/* 564:    */   {
/* 565:750 */     long l = System.currentTimeMillis();
/* 566:751 */     ResultSet localResultSet = null;
/* 567:    */     try
/* 568:    */     {
/* 569:753 */       localResultSet = paramPreparedStatement.executeQuery();
/* 570:    */     }
/* 571:    */     finally {}
/* 572:757 */     return localResultSet;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public synchronized void executeOracleThinLOBStream(String paramString, int paramInt, Object[] paramArrayOfObject)
/* 576:    */     throws SQLException, IOException
/* 577:    */   {
/* 578:780 */     if (new == null) {
/* 579:780 */       new = new OracleClassWrapperHack();
/* 580:    */     }
/* 581:781 */     new.a(this, paramString, paramInt, paramArrayOfObject);
/* 582:    */   }
/* 583:    */   
/* 584:    */   public final String getDriverType()
/* 585:    */   {
/* 586:786 */     return this.jdField_long;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public final boolean isOpened()
/* 590:    */   {
/* 591:791 */     return this.jdField_char;
/* 592:    */   }
/* 593:    */   
/* 594:    */   public final BSDBParams getParams()
/* 595:    */   {
/* 596:796 */     return this.jdField_try;
/* 597:    */   }
/* 598:    */   
/* 599:    */   final Connection a()
/* 600:    */   {
/* 601:804 */     return this.jdField_null;
/* 602:    */   }
/* 603:    */   
/* 604:    */   private static void a(int paramInt, String paramString)
/* 605:    */     throws BSException
/* 606:    */   {
/* 607:813 */     throw new BSException(paramInt, MessageText.getMessage(paramString));
/* 608:    */   }
/* 609:    */   
/* 610:    */   private static void a(int paramInt, String paramString, Throwable paramThrowable)
/* 611:    */     throws BSException
/* 612:    */   {
/* 613:822 */     throw new BSException(paramInt, MessageText.getMessage(paramString), paramThrowable);
/* 614:    */   }
/* 615:    */   
/* 616:    */   private static void a(int paramInt, String paramString1, String paramString2)
/* 617:    */     throws BSException
/* 618:    */   {
/* 619:831 */     throw new BSException(paramInt, MessageText.getMessage(paramString1, paramString2));
/* 620:    */   }
/* 621:    */   
/* 622:    */   private static void a(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
/* 623:    */     throws BSException
/* 624:    */   {
/* 625:841 */     throw new BSException(paramInt, MessageText.getMessage(paramString1, paramString2), paramThrowable);
/* 626:    */   }
/* 627:    */   
/* 628:    */   private static void a(int paramInt, String paramString1, String paramString2, String paramString3)
/* 629:    */     throws BSException
/* 630:    */   {
/* 631:853 */     throw new BSException(paramInt, MessageText.getMessage(paramString1, paramString2, paramString3));
/* 632:    */   }
/* 633:    */   
/* 634:    */   private static void a(int paramInt, String paramString1, String paramString2, String paramString3, Throwable paramThrowable)
/* 635:    */     throws BSException
/* 636:    */   {
/* 637:866 */     throw new BSException(paramInt, MessageText.getMessage(paramString1, paramString2, paramString3), paramThrowable);
/* 638:    */   }
/* 639:    */   
/* 640:870 */   private static OracleClassWrapperHack jdField_new = null;
/* 641:    */   private Connection jdField_null;
/* 642:    */   private DatabaseMetaData jdField_void;
/* 643:    */   private String jdField_case;
/* 644:    */   private String jdField_long;
/* 645:    */   private boolean jdField_char;
/* 646:    */   private boolean jdField_byte;
/* 647:    */   private BSDBParams jdField_try;
/* 648:    */   private Properties jdField_goto;
/* 649:880 */   private a jdField_do = new a(null);
/* 650:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBConnection
 * JD-Core Version:    0.7.0.1
 */