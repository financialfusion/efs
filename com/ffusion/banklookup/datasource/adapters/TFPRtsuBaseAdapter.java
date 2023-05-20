/*   1:    */ package com.ffusion.banklookup.datasource.adapters;
/*   2:    */ 
/*   3:    */ import com.ffusion.banklookup.FinancialInstitutionException;
/*   4:    */ import com.ffusion.banklookup.adapters.FinancialInstitutionAdapter;
/*   5:    */ import com.ffusion.banklookup.adapters.GenericTableAdapter;
/*   6:    */ import com.ffusion.banklookup.adapters.GenericTableAdapterConsts;
/*   7:    */ import com.ffusion.banklookup.beans.FinancialInstitution;
/*   8:    */ import com.ffusion.banklookup.beans.FinancialInstitutions;
/*   9:    */ import com.ffusion.beans.Phone;
/*  10:    */ import com.ffusion.beans.ZipCode;
/*  11:    */ import com.ffusion.util.db.DBUtil;
/*  12:    */ import com.ffusion.util.logging.DebugLog;
/*  13:    */ import java.sql.Connection;
/*  14:    */ import java.sql.PreparedStatement;
/*  15:    */ import java.sql.ResultSet;
/*  16:    */ import java.sql.Statement;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.logging.Level;
/*  19:    */ 
/*  20:    */ public class TFPRtsuBaseAdapter
/*  21:    */   implements GenericTableAdapterConsts, TFPRtsuBaseAdapterConsts
/*  22:    */ {
/*  23: 37 */   private static final String[] KEYS = { "TFPRTSUBASEID" };
/*  24:    */   private static final String SEQUENCE_NAME = "TFPRTSUBASE_ID";
/*  25: 40 */   private static final String[][] KEYSTOCHECK = { { "RoutingNumber", "Street", "City", "StateCode" }, { "FileKey" } };
/*  26:    */   private static final String SELECT_FOR_MERGE = "SELECT TFPRTSUBASEID,FileKey,RoutingNumber,InstitutionName,BranchOffice,Street,City,StateCode,ZipCode,ZipExtension,PhoneAreaCode,PhoneNumber,SWIFTAddress,BICAddress FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?";
/*  27:    */   private static final String SELECT_TO_GET_MIN_VALUE = "SELECT MIN(TFPRTSUBASEID) FROM LU_TFPRTSUBASE";
/*  28:    */   private static final String SELECT_TO_GET_MAX_VALUE = "SELECT MAX(TFPRTSUBASEID) FROM LU_TFPRTSUBASE";
/*  29:    */   private static final String DELETE_ROWS = "DELETE FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?";
/*  30:    */   private static final String SELECT_FOR_PURGING = "SELECT RoutingNumber FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?";
/*  31: 73 */   private static GenericTableAdapter gta = new GenericTableAdapter();
/*  32: 74 */   private static boolean isInitialized = false;
/*  33:    */   
/*  34:    */   public static void initialize()
/*  35:    */     throws FinancialInstitutionException
/*  36:    */   {
/*  37: 86 */     if (!isInitialized)
/*  38:    */     {
/*  39: 88 */       DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.initialize");
/*  40: 89 */       gta.initialize(null, "LU_TFPRTSUBASE", TFPRtsuBaseAdapterConsts.COLUMNS, KEYS, true, "TFPRTSUBASE_ID", KEYSTOCHECK);
/*  41:    */       
/*  42: 91 */       FinancialInstitutionAdapter.initialize(null);
/*  43: 92 */       isInitialized = true;
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void insert(HashMap data, HashMap extra)
/*  48:    */     throws FinancialInstitutionException
/*  49:    */   {
/*  50:110 */     insert(data, extra, true);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static void insert(HashMap data, HashMap extra, boolean deep)
/*  54:    */     throws FinancialInstitutionException
/*  55:    */   {
/*  56:128 */     HashMap[] rows = { data };
/*  57:129 */     insert(rows, extra, deep);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static void insert(HashMap[] rows, HashMap extra, boolean deep)
/*  61:    */     throws FinancialInstitutionException
/*  62:    */   {
/*  63:152 */     checkInitialization();
/*  64:153 */     Connection connection = GenericTableAdapter.getConnection();
/*  65:    */     try
/*  66:    */     {
/*  67:156 */       for (int i = 0; i < rows.length; i++)
/*  68:    */       {
/*  69:158 */         HashMap row = rows[i];
/*  70:159 */         insert(row, extra, deep, connection);
/*  71:    */       }
/*  72:161 */       DBUtil.commit(connection);
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76:165 */       DBUtil.rollback(connection);
/*  77:166 */       if ((e instanceof FinancialInstitutionException)) {
/*  78:167 */         throw ((FinancialInstitutionException)e);
/*  79:    */       }
/*  80:169 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the TFPRtsuBase table: " + e.getMessage());
/*  81:    */     }
/*  82:    */     finally
/*  83:    */     {
/*  84:176 */       GenericTableAdapter.releaseConnection(connection);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void deleteByID(int data)
/*  89:    */     throws FinancialInstitutionException
/*  90:    */   {
/*  91:191 */     deleteByID(data, true);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void deleteByID(int data, boolean deep)
/*  95:    */     throws FinancialInstitutionException
/*  96:    */   {
/*  97:206 */     if (data <= 0) {
/*  98:207 */       return;
/*  99:    */     }
/* 100:208 */     Connection connection = GenericTableAdapter.getConnection();
/* 101:    */     try
/* 102:    */     {
/* 103:211 */       deleteByID(data, deep, connection);
/* 104:212 */       DBUtil.commit(connection);
/* 105:    */     }
/* 106:    */     catch (Exception e)
/* 107:    */     {
/* 108:216 */       if (connection != null) {
/* 109:217 */         DBUtil.rollback(connection);
/* 110:    */       }
/* 111:    */     }
/* 112:    */     finally
/* 113:    */     {
/* 114:221 */       GenericTableAdapter.releaseConnection(connection);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void purge()
/* 119:    */     throws FinancialInstitutionException
/* 120:    */   {
/* 121:234 */     purge(2147483646, true);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void purge(int batchsize)
/* 125:    */     throws FinancialInstitutionException
/* 126:    */   {
/* 127:248 */     purge(batchsize, true);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void purge(int batchsize, boolean deep)
/* 131:    */     throws FinancialInstitutionException
/* 132:    */   {
/* 133:263 */     checkInitialization();
/* 134:264 */     DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.purge ");
/* 135:265 */     Connection connection = GenericTableAdapter.getConnection();
/* 136:266 */     PreparedStatement pStmt = null;
/* 137:    */     try
/* 138:    */     {
/* 139:269 */       int minValue = gta.getKeyValue(false, connection);
/* 140:270 */       int maxValue = gta.getKeyValue(true, connection);
/* 141:271 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/* 142:    */       {
/* 143:273 */         DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.purge: Purging - " + i + "," + (i + batchsize));
/* 144:    */         
/* 145:275 */         Integer[] args = { new Integer(i), new Integer(i + batchsize) };
/* 146:276 */         if (deep) {
/* 147:278 */           FinancialInstitutionAdapter.purgeAchRoutingNumbers("SELECT RoutingNumber FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?", args, connection);
/* 148:    */         }
/* 149:281 */         pStmt = DBUtil.prepareStatement(connection, "DELETE FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?");
/* 150:282 */         GenericTableAdapter.fillParameters(pStmt, args);
/* 151:283 */         DBUtil.executeUpdate(pStmt, "DELETE FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?");
/* 152:284 */         DBUtil.commit(connection);
/* 153:    */       }
/* 154:    */     }
/* 155:    */     catch (Exception e)
/* 156:    */     {
/* 157:    */       try
/* 158:    */       {
/* 159:290 */         DBUtil.rollback(connection);
/* 160:    */       }
/* 161:    */       catch (Exception ex) {}
/* 162:294 */       DebugLog.log(Level.SEVERE, "TFPRtsuBaseAdapter purge failed. Error: " + e.getMessage());
/* 163:296 */       if ((e instanceof FinancialInstitutionException)) {
/* 164:297 */         throw ((FinancialInstitutionException)e);
/* 165:    */       }
/* 166:298 */       throw new FinancialInstitutionException(e, 9, "Cannot purge data: " + e.getMessage());
/* 167:    */     }
/* 168:    */     finally
/* 169:    */     {
/* 170:304 */       if (pStmt != null) {
/* 171:306 */         DBUtil.closeStatement(pStmt);
/* 172:    */       }
/* 173:309 */       GenericTableAdapter.releaseConnection(connection);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static void synchronize()
/* 178:    */     throws FinancialInstitutionException
/* 179:    */   {
/* 180:321 */     synchronize(2147483646);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static void synchronize(int batchsize)
/* 184:    */     throws FinancialInstitutionException
/* 185:    */   {
/* 186:334 */     checkInitialization();
/* 187:335 */     DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.synchronize ");
/* 188:336 */     Connection connection = GenericTableAdapter.getConnection();
/* 189:337 */     Statement stmt = null;
/* 190:338 */     Statement stmt1 = null;
/* 191:339 */     PreparedStatement pStmt = null;
/* 192:340 */     ResultSet results = null;
/* 193:341 */     Object[] args = new Object[2];
/* 194:    */     try
/* 195:    */     {
/* 196:345 */       int maxValue = 0;
/* 197:346 */       int minValue = 0;
/* 198:347 */       stmt = DBUtil.createStatement(connection);
/* 199:348 */       results = DBUtil.executeQuery(stmt, "SELECT MAX(TFPRTSUBASEID) FROM LU_TFPRTSUBASE");
/* 200:349 */       if (results.next()) {
/* 201:350 */         maxValue = results.getInt(1);
/* 202:    */       }
/* 203:351 */       DBUtil.closeResultSet(results);
/* 204:352 */       results = null;
/* 205:    */       
/* 206:354 */       stmt1 = DBUtil.createStatement(connection);
/* 207:355 */       results = DBUtil.executeQuery(stmt1, "SELECT MIN(TFPRTSUBASEID) FROM LU_TFPRTSUBASE");
/* 208:356 */       if (results.next()) {
/* 209:357 */         minValue = results.getInt(1);
/* 210:    */       }
/* 211:358 */       DBUtil.closeResultSet(results);
/* 212:359 */       results = null;
/* 213:    */       
/* 214:361 */       pStmt = null;
/* 215:362 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/* 216:    */       {
/* 217:364 */         DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.synchronize. Synchronizing between " + i + " and " + (i - 1 + batchsize));
/* 218:    */         
/* 219:366 */         pStmt = DBUtil.prepareStatement(connection, "SELECT TFPRTSUBASEID,FileKey,RoutingNumber,InstitutionName,BranchOffice,Street,City,StateCode,ZipCode,ZipExtension,PhoneAreaCode,PhoneNumber,SWIFTAddress,BICAddress FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?");
/* 220:367 */         args[0] = new Integer(i);
/* 221:368 */         if (2147483647 - batchsize > i) {
/* 222:369 */           args[1] = new Integer(i - 1 + batchsize);
/* 223:    */         } else {
/* 224:371 */           args[1] = new Integer(2147483647);
/* 225:    */         }
/* 226:372 */         results = DBUtil.executeQuery(pStmt, "SELECT TFPRTSUBASEID,FileKey,RoutingNumber,InstitutionName,BranchOffice,Street,City,StateCode,ZipCode,ZipExtension,PhoneAreaCode,PhoneNumber,SWIFTAddress,BICAddress FROM LU_TFPRTSUBASE WHERE TFPRTSUBASEID>=? AND TFPRTSUBASEID<=?");
/* 227:373 */         boolean rowsFetched = false;
/* 228:374 */         while (results.next())
/* 229:    */         {
/* 230:376 */           rowsFetched = true;
/* 231:    */           
/* 232:378 */           int id = results.getInt(1);
/* 233:379 */           String fileKey = results.getString(2);
/* 234:380 */           String routingNumber = results.getString(3);
/* 235:381 */           String institutionName = results.getString(4);
/* 236:382 */           String branchOffice = results.getString(5);
/* 237:383 */           String street = results.getString(6);
/* 238:384 */           String city = results.getString(7);
/* 239:385 */           String stateCode = results.getString(8);
/* 240:386 */           String zipCode = results.getString(9);
/* 241:387 */           String zipCodeExtension = results.getString(10);
/* 242:388 */           String areaCode = results.getString(11);
/* 243:389 */           String telPrefix = results.getString(12);
/* 244:390 */           String swiftAddress = results.getString(13);
/* 245:391 */           String bicAddress = results.getString(14);
/* 246:    */           
/* 247:393 */           updateFinancialInstitution(id, fileKey, routingNumber, institutionName, branchOffice, street, city, stateCode, zipCode, zipCodeExtension, areaCode, telPrefix, swiftAddress, bicAddress, connection);
/* 248:    */         }
/* 249:400 */         DBUtil.closeResultSet(results);
/* 250:401 */         results = null;
/* 251:402 */         DBUtil.commit(connection);
/* 252:    */       }
/* 253:    */     }
/* 254:    */     catch (FinancialInstitutionException fie)
/* 255:    */     {
/* 256:407 */       DebugLog.log(Level.SEVERE, "TFPRtsuBaseAdapter synchronize failed. Error: " + fie.getMessage());
/* 257:    */       
/* 258:409 */       DBUtil.rollback(connection);
/* 259:410 */       throw fie;
/* 260:    */     }
/* 261:    */     catch (Throwable e)
/* 262:    */     {
/* 263:414 */       DebugLog.log(Level.SEVERE, "TFPRtsuBaseAdapter synchronize failed. Error: " + e.getMessage());
/* 264:    */       
/* 265:416 */       DBUtil.rollback(connection);
/* 266:417 */       throw new FinancialInstitutionException(e, 4, "Cannot synchronize data: " + e.getMessage());
/* 267:    */     }
/* 268:    */     finally
/* 269:    */     {
/* 270:    */       try
/* 271:    */       {
/* 272:425 */         if (results != null)
/* 273:    */         {
/* 274:427 */           DBUtil.closeResultSet(results);
/* 275:428 */           results = null;
/* 276:    */         }
/* 277:    */       }
/* 278:    */       catch (Throwable e) {}
/* 279:436 */       if (pStmt != null) {
/* 280:438 */         DBUtil.closeStatement(pStmt);
/* 281:    */       }
/* 282:440 */       if (stmt != null) {
/* 283:442 */         DBUtil.closeStatement(stmt);
/* 284:    */       }
/* 285:444 */       if (stmt1 != null) {
/* 286:446 */         DBUtil.closeStatement(stmt1);
/* 287:    */       }
/* 288:448 */       GenericTableAdapter.releaseConnection(connection);
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public static void insert(HashMap data, HashMap extra, boolean deep, Connection connection)
/* 293:    */     throws FinancialInstitutionException
/* 294:    */   {
/* 295:478 */     DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.insert process row");
/* 296:479 */     gta.insertRow(data, extra, connection);
/* 297:480 */     if (!deep) {
/* 298:481 */       return;
/* 299:    */     }
/* 300:483 */     String fileKey = (String)data.get("FileKey");
/* 301:484 */     String routingNumber = (String)data.get("RoutingNumber");
/* 302:485 */     String institutionName = (String)data.get("InstitutionName");
/* 303:486 */     String branchOffice = (String)data.get("BranchOffice");
/* 304:487 */     String street = (String)data.get("Street");
/* 305:488 */     String city = (String)data.get("City");
/* 306:489 */     String stateCode = (String)data.get("StateCode");
/* 307:490 */     String zipCode = (String)data.get("ZipCode");
/* 308:491 */     String zipCodeExtension = (String)data.get("ZipExtension");
/* 309:492 */     String areaCode = (String)data.get("PhoneAreaCode");
/* 310:493 */     String telPrefix = (String)data.get("PhoneNumber");
/* 311:494 */     String swiftAddress = (String)data.get("SWIFTAddress");
/* 312:495 */     String bicAddress = (String)data.get("BICAddress");
/* 313:    */     
/* 314:497 */     updateFinancialInstitution(0, fileKey, routingNumber, institutionName, branchOffice, street, city, stateCode, zipCode, zipCodeExtension, areaCode, telPrefix, swiftAddress, bicAddress, connection);
/* 315:    */   }
/* 316:    */   
/* 317:    */   private static void updateFinancialInstitution(int id, String fileKey, String routingNumber, String institutionName, String branchOffice, String street, String city, String stateCode, String zipCode, String zipCodeExtension, String areaCode, String telPrefix, String swiftAddress, String bicAddress, Connection connection)
/* 318:    */     throws FinancialInstitutionException
/* 319:    */   {
/* 320:    */     try
/* 321:    */     {
/* 322:552 */       if (routingNumber != null) {
/* 323:553 */         routingNumber = routingNumber.trim().toUpperCase();
/* 324:    */       }
/* 325:554 */       if (institutionName != null) {
/* 326:555 */         institutionName = institutionName.trim().toUpperCase();
/* 327:    */       }
/* 328:556 */       if (branchOffice != null) {
/* 329:557 */         branchOffice = branchOffice.trim().toUpperCase();
/* 330:    */       }
/* 331:558 */       if (street != null) {
/* 332:559 */         street = street.trim().toUpperCase();
/* 333:    */       }
/* 334:560 */       if (city != null) {
/* 335:561 */         city = city.trim().toUpperCase();
/* 336:    */       }
/* 337:562 */       if (stateCode != null) {
/* 338:563 */         stateCode = stateCode.trim().toUpperCase();
/* 339:    */       }
/* 340:564 */       if (zipCode != null) {
/* 341:565 */         zipCode = zipCode.trim().toUpperCase();
/* 342:    */       }
/* 343:566 */       if (zipCodeExtension != null)
/* 344:    */       {
/* 345:568 */         zipCodeExtension = zipCodeExtension.trim().toUpperCase();
/* 346:569 */         if (zipCode != null) {
/* 347:570 */           zipCode = zipCode.concat(zipCodeExtension);
/* 348:    */         }
/* 349:    */       }
/* 350:572 */       String phone = null;
/* 351:573 */       if (areaCode != null)
/* 352:    */       {
/* 353:575 */         areaCode = areaCode.trim().toUpperCase();
/* 354:576 */         if ((phone == null) && (!areaCode.equals(""))) {
/* 355:577 */           phone = areaCode;
/* 356:    */         }
/* 357:    */       }
/* 358:579 */       if (telPrefix != null)
/* 359:    */       {
/* 360:581 */         telPrefix = telPrefix.trim().toUpperCase();
/* 361:582 */         if ((phone != null) && (!telPrefix.equals(""))) {
/* 362:583 */           phone = phone.concat(telPrefix);
/* 363:584 */         } else if (!telPrefix.equals("")) {
/* 364:585 */           phone = telPrefix;
/* 365:    */         }
/* 366:    */       }
/* 367:587 */       if (swiftAddress != null)
/* 368:    */       {
/* 369:589 */         swiftAddress = swiftAddress.trim().toUpperCase();
/* 370:590 */         if (swiftAddress.equals("")) {
/* 371:591 */           swiftAddress = null;
/* 372:    */         }
/* 373:    */       }
/* 374:593 */       if (bicAddress != null)
/* 375:    */       {
/* 376:595 */         bicAddress = bicAddress.trim().toUpperCase();
/* 377:596 */         if (bicAddress.equals("")) {
/* 378:597 */           bicAddress = null;
/* 379:    */         }
/* 380:    */       }
/* 381:603 */       FinancialInstitution searchCriteria = new FinancialInstitution();
/* 382:604 */       searchCriteria.setInstitutionName(institutionName);
/* 383:605 */       searchCriteria.setStreet(street);
/* 384:606 */       searchCriteria.setCity(city);
/* 385:607 */       searchCriteria.setStateCode(stateCode);
/* 386:608 */       searchCriteria.setZipCode(zipCode);
/* 387:609 */       searchCriteria.setPhone(phone);
/* 388:610 */       searchCriteria.setCountry("UNITED STATES");
/* 389:611 */       FinancialInstitutions fis = FinancialInstitutionAdapter.get(searchCriteria, true, connection);
/* 390:613 */       if (fis.size() == 1)
/* 391:    */       {
/* 392:616 */         FinancialInstitution fi = (FinancialInstitution)fis.get(0);
/* 393:617 */         updateFinancialInstitution(fi, id, fileKey, routingNumber, institutionName, branchOffice, street, city, stateCode, zipCode, phone, swiftAddress, bicAddress, connection);
/* 394:    */         
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:622 */         return;
/* 399:    */       }
/* 400:626 */       FinancialInstitution fi = null;
/* 401:    */       
/* 402:628 */       fi = new FinancialInstitution();
/* 403:629 */       fi.setAchRoutingNumber(routingNumber);
/* 404:630 */       fi.setInstitutionName(institutionName);
/* 405:631 */       fi.setBranchName(branchOffice);
/* 406:632 */       fi.setStreet(street);
/* 407:633 */       fi.setCity(city);
/* 408:634 */       fi.setStateCode(stateCode);
/* 409:635 */       fi.setZipCode(zipCode);
/* 410:636 */       fi.setPhone(phone);
/* 411:637 */       fi.setCountry("UNITED STATES");
/* 412:638 */       if ((bicAddress != null) && (!bicAddress.equals(""))) {
/* 413:639 */         fi.setSwiftBIC(bicAddress);
/* 414:    */       }
/* 415:640 */       if ((swiftAddress != null) && (!swiftAddress.equals(""))) {
/* 416:641 */         fi.setSwiftBIC(swiftAddress);
/* 417:    */       }
/* 418:642 */       FinancialInstitutionAdapter.insert(fi, connection);
/* 419:    */     }
/* 420:    */     catch (Exception e)
/* 421:    */     {
/* 422:647 */       e.printStackTrace();
/* 423:648 */       DebugLog.log(Level.SEVERE, "+++ TFPRtsuBaseAdapter.synchronize failed. Error: " + e.getMessage());
/* 424:    */       
/* 425:650 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data: " + e.getMessage());
/* 426:    */     }
/* 427:    */   }
/* 428:    */   
/* 429:    */   private static void updateFinancialInstitution(FinancialInstitution fi, int id, String fileKey, String routingNumber, String institutionName, String branchOffice, String street, String city, String stateCode, String zipCode, String phone, String swiftAddress, String bicAddress, Connection connection)
/* 430:    */     throws FinancialInstitutionException
/* 431:    */   {
/* 432:    */     try
/* 433:    */     {
/* 434:702 */       boolean shouldUpdate = false;
/* 435:703 */       String val = null;
/* 436:704 */       val = fi.getInstitutionName();
/* 437:705 */       if (((val != null) && (!val.equals(institutionName))) || ((val == null) && (institutionName != null) && (!institutionName.equals(""))))
/* 438:    */       {
/* 439:709 */         fi.setInstitutionName(institutionName);
/* 440:710 */         shouldUpdate = true;
/* 441:    */       }
/* 442:713 */       val = fi.getBranchName();
/* 443:714 */       if (((val != null) && (!val.equals(branchOffice))) || ((val == null) && (branchOffice != null) && (!branchOffice.equals(""))))
/* 444:    */       {
/* 445:718 */         fi.setBranchName(branchOffice);
/* 446:719 */         shouldUpdate = true;
/* 447:    */       }
/* 448:722 */       val = fi.getStreet();
/* 449:723 */       if (((val != null) && (!val.equals(street))) || ((val == null) && (street != null) && (!street.equals(""))))
/* 450:    */       {
/* 451:726 */         fi.setStreet(street);
/* 452:727 */         shouldUpdate = true;
/* 453:    */       }
/* 454:730 */       val = fi.getCity();
/* 455:731 */       if (((val != null) && (!val.equals(city))) || ((val == null) && (city != null) && (!city.equals(""))))
/* 456:    */       {
/* 457:734 */         fi.setCity(city);
/* 458:735 */         shouldUpdate = true;
/* 459:    */       }
/* 460:738 */       val = fi.getStateCode();
/* 461:739 */       if (((val != null) && (!val.equals(stateCode))) || ((val == null) && (stateCode != null) && (!stateCode.equals(""))))
/* 462:    */       {
/* 463:742 */         fi.setStateCode(stateCode);
/* 464:743 */         shouldUpdate = true;
/* 465:    */       }
/* 466:746 */       val = fi.getZipCode();
/* 467:747 */       String zip = new ZipCode(zipCode).toString();
/* 468:748 */       if (((val != null) && (!val.equals(zip))) || ((val == null) && (zip != null) && (!zip.equals(""))))
/* 469:    */       {
/* 470:751 */         fi.setZipCode(zip);
/* 471:752 */         shouldUpdate = true;
/* 472:    */       }
/* 473:755 */       val = fi.getPhone();
/* 474:756 */       String ph = new Phone(phone).toString();
/* 475:757 */       if (((val != null) && (!val.equals(ph))) || ((val == null) && (ph != null) && (!ph.equals(""))))
/* 476:    */       {
/* 477:760 */         fi.setPhone(ph);
/* 478:761 */         shouldUpdate = true;
/* 479:    */       }
/* 480:764 */       val = fi.getCountry();
/* 481:765 */       if ((val != null) && (!val.equals("UNITED STATES")))
/* 482:    */       {
/* 483:767 */         fi.setCountry("UNITED STATES");
/* 484:768 */         shouldUpdate = true;
/* 485:    */       }
/* 486:771 */       String oldFedRoutingNumber = fi.getAchRoutingNumber();
/* 487:772 */       if (!routingNumber.equals("000000000")) {
/* 488:774 */         if (!fi.existsRoutingNumber(routingNumber))
/* 489:    */         {
/* 490:776 */           fi.addRoutingNumber(routingNumber);
/* 491:777 */           fi.setAchRoutingNumber(routingNumber);
/* 492:778 */           shouldUpdate = true;
/* 493:    */         }
/* 494:    */       }
/* 495:782 */       val = fi.getSwiftBIC();
/* 496:783 */       if (((val != null) && (!val.equals(bicAddress))) || ((val == null) && (bicAddress != null) && (!bicAddress.equals(""))))
/* 497:    */       {
/* 498:786 */         fi.setSwiftBIC(bicAddress);
/* 499:787 */         shouldUpdate = true;
/* 500:    */       }
/* 501:790 */       val = fi.getSwiftBIC();
/* 502:791 */       if (((val != null) && (!val.equals(swiftAddress))) || ((val == null) && (swiftAddress != null) && (!swiftAddress.equals(""))))
/* 503:    */       {
/* 504:794 */         fi.setSwiftBIC(swiftAddress);
/* 505:795 */         shouldUpdate = true;
/* 506:    */       }
/* 507:798 */       if (shouldUpdate)
/* 508:    */       {
/* 509:800 */         DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.updateFinancialInstitution  Updating " + fi.toXML());
/* 510:    */         
/* 511:802 */         FinancialInstitutionAdapter.update(fi, connection);
/* 512:    */       }
/* 513:    */       else
/* 514:    */       {
/* 515:806 */         DebugLog.log(Level.FINE, "+++ TFPRtsuBaseAdapter.updateFinancialInstitution  Skipping update of achrtn " + fi.getAchRoutingNumber());
/* 516:    */       }
/* 517:    */     }
/* 518:    */     catch (Exception e)
/* 519:    */     {
/* 520:813 */       e.printStackTrace();
/* 521:814 */       DebugLog.log(Level.SEVERE, "+++ TFPRtsuBaseAdapter.updateFinancialInstitution failed. Error: " + e.getMessage());
/* 522:    */       
/* 523:816 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data");
/* 524:    */     }
/* 525:    */   }
/* 526:    */   
/* 527:    */   private static void deleteByID(int data, boolean deep, Connection connection)
/* 528:    */     throws FinancialInstitutionException
/* 529:    */   {
/* 530:835 */     if (data <= 0) {
/* 531:836 */       return;
/* 532:    */     }
/* 533:837 */     HashMap map = new HashMap(1);
/* 534:838 */     map.put("TFPRTSUBASEID", Integer.toString(data));
/* 535:839 */     if (deep)
/* 536:    */     {
/* 537:840 */       String[] args = { Integer.toString(data) };
/* 538:841 */       String[] row = gta.getRow(args, null);
/* 539:842 */       FinancialInstitutionAdapter.deleteAchRoutingNumber(row[5], connection);
/* 540:    */     }
/* 541:844 */     gta.deleteRow(map, null, connection);
/* 542:    */   }
/* 543:    */   
/* 544:    */   private static void checkInitialization()
/* 545:    */     throws FinancialInstitutionException
/* 546:    */   {
/* 547:856 */     if (!isInitialized) {
/* 548:858 */       throw new FinancialInstitutionException(2, "TFPRtsuBaseAdapter not initialized");
/* 549:    */     }
/* 550:    */   }
/* 551:    */ }


/* Location:           D:\drops\jd\jars\ffiblcustom.jar
 * Qualified Name:     com.ffusion.banklookup.datasource.adapters.TFPRtsuBaseAdapter
 * JD-Core Version:    0.7.0.1
 */