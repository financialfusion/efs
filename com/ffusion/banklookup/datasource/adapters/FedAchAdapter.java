/*   1:    */ package com.ffusion.banklookup.datasource.adapters;
/*   2:    */ 
/*   3:    */ import com.ffusion.banklookup.FinancialInstitutionException;
/*   4:    */ import com.ffusion.banklookup.adapters.FinancialInstitutionAdapter;
/*   5:    */ import com.ffusion.banklookup.adapters.GenericTableAdapter;
/*   6:    */ import com.ffusion.banklookup.adapters.GenericTableAdapterConsts;
/*   7:    */ import com.ffusion.banklookup.beans.FinancialInstitution;
/*   8:    */ import com.ffusion.banklookup.beans.FinancialInstitutions;
/*   9:    */ import com.ffusion.beans.Phone;
/*  10:    */ import com.ffusion.util.db.DBUtil;
/*  11:    */ import com.ffusion.util.logging.DebugLog;
/*  12:    */ import java.sql.Connection;
/*  13:    */ import java.sql.PreparedStatement;
/*  14:    */ import java.sql.ResultSet;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.logging.Level;
/*  17:    */ 
/*  18:    */ public class FedAchAdapter
/*  19:    */   implements GenericTableAdapterConsts, FedAchAdapterConsts
/*  20:    */ {
/*  21: 39 */   private static final String[] KEYS = { "FedInstitutionId" };
/*  22:    */   private static final String SEQUENCE_NAME = "FEDACH_ID";
/*  23: 41 */   private static final String[][] KEYSTOCHECK = { { "RoutingNumber", "Address", "City", "StateCode" } };
/*  24:    */   private static final String NULL_ROUTING_NUMBER = "000000000";
/*  25:    */   private static final String SELECT_FOR_MERGE = "SELECT FedInstitutionId,RoutingNumber,CustomerName,Address,City,StateCode,Zipcode,ZipcodeExtension,TelephoneAreaCode,TelephonePrefixNum,TelephoneSuffixNum,NewRoutingNumber,RecordType FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?";
/*  26:    */   private static final String SELECT_FOR_PURGING_OLD = "SELECT RoutingNumber FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?";
/*  27:    */   private static final String SELECT_FOR_PURGING_NEW = "SELECT NewRoutingNumber FROM LU_FedACH WHERE RoutingNumber<>'000000000'  AND FedInstitutionId>=? AND FedInstitutionId<=?";
/*  28:    */   private static final String SELECT_TO_GET_MIN_VALUE = "SELECT MIN(FedInstitutionId) FROM LU_FedACH";
/*  29:    */   private static final String SELECT_TO_GET_MAX_VALUE = "SELECT MAX(FedInstitutionId) FROM LU_FedACH";
/*  30:    */   private static final String DELETE_ROWS = "DELETE FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?";
/*  31: 75 */   private static GenericTableAdapter gta = new GenericTableAdapter();
/*  32: 76 */   private static boolean isInitialized = false;
/*  33:    */   
/*  34:    */   public static void initialize()
/*  35:    */     throws FinancialInstitutionException
/*  36:    */   {
/*  37: 87 */     if (!isInitialized)
/*  38:    */     {
/*  39: 89 */       gta.initialize(null, "LU_FedACH", FedAchAdapterConsts.COLUMNS, KEYS, true, "FEDACH_ID", KEYSTOCHECK);
/*  40:    */       
/*  41: 91 */       FinancialInstitutionAdapter.initialize(null);
/*  42: 92 */       isInitialized = true;
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void insert(HashMap data, HashMap extra)
/*  47:    */     throws FinancialInstitutionException
/*  48:    */   {
/*  49:110 */     insert(data, extra, true);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void insert(HashMap data, HashMap extra, boolean deep)
/*  53:    */     throws FinancialInstitutionException
/*  54:    */   {
/*  55:128 */     HashMap[] rows = { data };
/*  56:129 */     insert(rows, extra, deep);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static GenericTableAdapter getGenericTableAdapter()
/*  60:    */   {
/*  61:137 */     return gta;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void insertData(HashMap data, HashMap extra, boolean deep, Connection connection)
/*  65:    */   {
/*  66:157 */     HashMap[] rows = { data };
/*  67:    */     try
/*  68:    */     {
/*  69:159 */       insert(rows, extra, deep, connection);
/*  70:    */     }
/*  71:    */     catch (Throwable t)
/*  72:    */     {
/*  73:161 */       String institutionName = (String)data.get("CustomerName");
/*  74:162 */       DebugLog.log(Level.WARNING, "FedAchAdapter.insertData failed for Institution: " + institutionName + t.getMessage());
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void insert(HashMap[] rows, HashMap extra, boolean deep, Connection connection)
/*  79:    */     throws FinancialInstitutionException
/*  80:    */   {
/*  81:    */     
/*  82:    */     try
/*  83:    */     {
/*  84:191 */       for (int i = 0; i < rows.length; i++)
/*  85:    */       {
/*  86:192 */         HashMap row = rows[i];
/*  87:193 */         insert(row, extra, deep, connection);
/*  88:    */       }
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:196 */       if ((e instanceof FinancialInstitutionException)) {
/*  93:197 */         throw ((FinancialInstitutionException)e);
/*  94:    */       }
/*  95:199 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the FedAch table: " + e.getMessage());
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void insert(HashMap[] rows, HashMap extra, boolean deep)
/* 100:    */     throws FinancialInstitutionException
/* 101:    */   {
/* 102:226 */     checkInitialization();
/* 103:227 */     Connection connection = null;
/* 104:    */     try
/* 105:    */     {
/* 106:230 */       connection = GenericTableAdapter.getConnection();
/* 107:231 */       for (int i = 0; i < rows.length; i++)
/* 108:    */       {
/* 109:233 */         HashMap row = rows[i];
/* 110:234 */         insert(row, extra, deep, connection);
/* 111:    */       }
/* 112:236 */       connection.commit();
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:240 */       DBUtil.rollback(connection);
/* 117:241 */       if ((e instanceof FinancialInstitutionException)) {
/* 118:242 */         throw ((FinancialInstitutionException)e);
/* 119:    */       }
/* 120:244 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the FedWire table: " + e.getMessage());
/* 121:    */     }
/* 122:    */     finally
/* 123:    */     {
/* 124:251 */       GenericTableAdapter.releaseConnection(connection);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static void deleteByID(int data)
/* 129:    */     throws FinancialInstitutionException
/* 130:    */   {
/* 131:266 */     deleteByID(data, true);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void deleteByID(int data, boolean deep)
/* 135:    */     throws FinancialInstitutionException
/* 136:    */   {
/* 137:281 */     if (data <= 0) {
/* 138:282 */       return;
/* 139:    */     }
/* 140:283 */     Connection connection = null;
/* 141:    */     try
/* 142:    */     {
/* 143:286 */       connection = GenericTableAdapter.getConnection();
/* 144:287 */       deleteByID(data, deep, connection);
/* 145:288 */       connection.commit();
/* 146:    */     }
/* 147:    */     catch (Exception e)
/* 148:    */     {
/* 149:292 */       if (connection != null) {
/* 150:293 */         DBUtil.rollback(connection);
/* 151:    */       }
/* 152:    */     }
/* 153:    */     finally
/* 154:    */     {
/* 155:297 */       GenericTableAdapter.releaseConnection(connection);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static void purge()
/* 160:    */     throws FinancialInstitutionException
/* 161:    */   {
/* 162:310 */     purge(2147483646, true);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static void purge(int batchsize)
/* 166:    */     throws FinancialInstitutionException
/* 167:    */   {
/* 168:324 */     purge(batchsize, true);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static void purge(int batchsize, boolean deep)
/* 172:    */     throws FinancialInstitutionException
/* 173:    */   {
/* 174:339 */     checkInitialization();
/* 175:340 */     Connection connection = null;
/* 176:341 */     PreparedStatement pStmt = null;
/* 177:    */     try
/* 178:    */     {
/* 179:344 */       connection = GenericTableAdapter.getConnection();
/* 180:345 */       int minValue = gta.getKeyValue(false, connection);
/* 181:346 */       int maxValue = gta.getKeyValue(true, connection);
/* 182:347 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/* 183:    */       {
/* 184:349 */         Integer[] args = { new Integer(i), new Integer(i + batchsize) };
/* 185:350 */         if (deep)
/* 186:    */         {
/* 187:352 */           FinancialInstitutionAdapter.purgeAchRoutingNumbers("SELECT RoutingNumber FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?", args, connection);
/* 188:    */           
/* 189:354 */           FinancialInstitutionAdapter.purgeAchRoutingNumbers("SELECT NewRoutingNumber FROM LU_FedACH WHERE RoutingNumber<>'000000000'  AND FedInstitutionId>=? AND FedInstitutionId<=?", args, connection);
/* 190:    */         }
/* 191:357 */         pStmt = DBUtil.prepareStatement(connection, "DELETE FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?");
/* 192:358 */         GenericTableAdapter.fillParameters(pStmt, args);
/* 193:359 */         DBUtil.executeUpdate(pStmt, "DELETE FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?");
/* 194:360 */         connection.commit();
/* 195:    */       }
/* 196:    */     }
/* 197:    */     catch (Exception e)
/* 198:    */     {
/* 199:    */       try
/* 200:    */       {
/* 201:366 */         connection.rollback();
/* 202:    */       }
/* 203:    */       catch (Exception ex) {}
/* 204:370 */       DebugLog.log(Level.SEVERE, "FedAchAdapter purge failed. Error: " + e.getMessage());
/* 205:371 */       if ((e instanceof FinancialInstitutionException)) {
/* 206:372 */         throw ((FinancialInstitutionException)e);
/* 207:    */       }
/* 208:373 */       throw new FinancialInstitutionException(e, 9, "Cannot purge data: " + e.getMessage());
/* 209:    */     }
/* 210:    */     finally
/* 211:    */     {
/* 212:379 */       if (pStmt != null) {
/* 213:381 */         DBUtil.closeStatement(pStmt);
/* 214:    */       }
/* 215:385 */       GenericTableAdapter.releaseConnection(connection);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static void synchronize()
/* 220:    */     throws FinancialInstitutionException
/* 221:    */   {
/* 222:397 */     synchronize(2147483646);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static void synchronize(int batchsize)
/* 226:    */     throws FinancialInstitutionException
/* 227:    */   {
/* 228:410 */     checkInitialization();
/* 229:411 */     Connection connection = null;
/* 230:412 */     PreparedStatement pStmt = null;
/* 231:413 */     PreparedStatement pStmt1 = null;
/* 232:414 */     PreparedStatement pStmt2 = null;
/* 233:415 */     ResultSet results = null;
/* 234:416 */     Object[] args = new Object[2];
/* 235:    */     try
/* 236:    */     {
/* 237:419 */       connection = GenericTableAdapter.getConnection();
/* 238:    */       
/* 239:421 */       int maxValue = 0;
/* 240:422 */       int minValue = 0;
/* 241:    */       
/* 242:424 */       pStmt = DBUtil.prepareStatement(connection, "SELECT MAX(FedInstitutionId) FROM LU_FedACH");
/* 243:425 */       results = DBUtil.executeQuery(pStmt, "SELECT MAX(FedInstitutionId) FROM LU_FedACH");
/* 244:426 */       if (results.next()) {
/* 245:427 */         maxValue = results.getInt(1);
/* 246:    */       }
/* 247:428 */       DBUtil.closeResultSet(results);
/* 248:429 */       results = null;
/* 249:    */       
/* 250:431 */       pStmt1 = DBUtil.prepareStatement(connection, "SELECT MIN(FedInstitutionId) FROM LU_FedACH");
/* 251:432 */       results = DBUtil.executeQuery(pStmt1, "SELECT MIN(FedInstitutionId) FROM LU_FedACH");
/* 252:433 */       if (results.next()) {
/* 253:434 */         minValue = results.getInt(1);
/* 254:    */       }
/* 255:435 */       DBUtil.closeResultSet(results);
/* 256:436 */       results = null;
/* 257:438 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/* 258:    */       {
/* 259:440 */         pStmt2 = DBUtil.prepareStatement(connection, "SELECT FedInstitutionId,RoutingNumber,CustomerName,Address,City,StateCode,Zipcode,ZipcodeExtension,TelephoneAreaCode,TelephonePrefixNum,TelephoneSuffixNum,NewRoutingNumber,RecordType FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?");
/* 260:441 */         args[0] = new Integer(i);
/* 261:442 */         if (2147483647 - batchsize > i) {
/* 262:443 */           args[1] = new Integer(i - 1 + batchsize);
/* 263:    */         } else {
/* 264:445 */           args[1] = new Integer(2147483647);
/* 265:    */         }
/* 266:446 */         GenericTableAdapter.fillParameters(pStmt2, args);
/* 267:447 */         results = DBUtil.executeQuery(pStmt2, "SELECT FedInstitutionId,RoutingNumber,CustomerName,Address,City,StateCode,Zipcode,ZipcodeExtension,TelephoneAreaCode,TelephonePrefixNum,TelephoneSuffixNum,NewRoutingNumber,RecordType FROM LU_FedACH WHERE FedInstitutionId>=? AND FedInstitutionId<=?");
/* 268:448 */         boolean rowsFetched = false;
/* 269:449 */         while (results.next())
/* 270:    */         {
/* 271:451 */           rowsFetched = true;
/* 272:    */           
/* 273:453 */           int id = results.getInt(1);
/* 274:454 */           String routingNumber = results.getString(2);
/* 275:455 */           String institutionName = results.getString(3);
/* 276:456 */           String street = results.getString(4);
/* 277:457 */           String city = results.getString(5);
/* 278:458 */           String stateCode = results.getString(6);
/* 279:459 */           String zipCode = results.getString(7);
/* 280:460 */           String zipCodeExtension = results.getString(8);
/* 281:461 */           String areaCode = results.getString(9);
/* 282:462 */           String telPrefix = results.getString(10);
/* 283:463 */           String telSuffix = results.getString(11);
/* 284:464 */           String newRoutingNumber = results.getString(12);
/* 285:465 */           String recordType = results.getString(13);
/* 286:466 */           updateFinancialInstitution(id, routingNumber, institutionName, street, city, stateCode, zipCode, zipCodeExtension, areaCode, telPrefix, telSuffix, newRoutingNumber, recordType, connection);
/* 287:    */         }
/* 288:473 */         DBUtil.closeResultSet(results);
/* 289:474 */         results = null;
/* 290:475 */         connection.commit();
/* 291:    */       }
/* 292:    */     }
/* 293:    */     catch (FinancialInstitutionException fie)
/* 294:    */     {
/* 295:481 */       DebugLog.log(Level.SEVERE, "FedAchAdapter synchronize failed. Error: " + fie.getMessage());
/* 296:    */       
/* 297:483 */       DBUtil.rollback(connection);
/* 298:484 */       throw fie;
/* 299:    */     }
/* 300:    */     catch (Throwable e)
/* 301:    */     {
/* 302:488 */       DebugLog.log(Level.SEVERE, "FedAchAdapter synchronize failed. Error: " + e.getMessage());
/* 303:    */       
/* 304:490 */       DBUtil.rollback(connection);
/* 305:491 */       throw new FinancialInstitutionException(e, 4, "Cannot synchronize data: " + e.getMessage());
/* 306:    */     }
/* 307:    */     finally
/* 308:    */     {
/* 309:    */       try
/* 310:    */       {
/* 311:499 */         if (results != null)
/* 312:    */         {
/* 313:501 */           DBUtil.closeResultSet(results);
/* 314:502 */           results = null;
/* 315:    */         }
/* 316:    */       }
/* 317:    */       catch (Throwable e) {}
/* 318:509 */       if (pStmt != null) {
/* 319:511 */         DBUtil.closeStatement(pStmt);
/* 320:    */       }
/* 321:513 */       if (pStmt1 != null) {
/* 322:515 */         DBUtil.closeStatement(pStmt1);
/* 323:    */       }
/* 324:517 */       if (pStmt2 != null) {
/* 325:519 */         DBUtil.closeStatement(pStmt2);
/* 326:    */       }
/* 327:522 */       GenericTableAdapter.releaseConnection(connection);
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static void insert(HashMap data, HashMap extra, boolean deep, Connection connection)
/* 332:    */     throws FinancialInstitutionException
/* 333:    */   {
/* 334:552 */     gta.insertRow(data, extra, connection);
/* 335:553 */     if (!deep) {
/* 336:554 */       return;
/* 337:    */     }
/* 338:556 */     String routingNumber = (String)data.get("RoutingNumber");
/* 339:557 */     String institutionName = (String)data.get("CustomerName");
/* 340:558 */     String street = (String)data.get("Address");
/* 341:559 */     String city = (String)data.get("City");
/* 342:560 */     String stateCode = (String)data.get("StateCode");
/* 343:561 */     String zipCode = (String)data.get("Zipcode");
/* 344:562 */     String zipCodeExtension = (String)data.get("ZipcodeExtension");
/* 345:563 */     String areaCode = (String)data.get("TelephoneAreaCode");
/* 346:564 */     String telPrefix = (String)data.get("TelephonePrefixNum");
/* 347:565 */     String telSuffix = (String)data.get("TelephoneSuffixNum");
/* 348:566 */     String newRoutingNumber = (String)data.get("NewRoutingNumber");
/* 349:567 */     String recordType = (String)data.get("RecordType");
/* 350:    */     
/* 351:    */ 
/* 352:570 */     FinancialInstitution fi = new FinancialInstitution();
/* 353:571 */     fi.setAchRoutingNumber(routingNumber);
/* 354:572 */     fi.setInstitutionName(institutionName);
/* 355:573 */     fi.setStreet(street);
/* 356:574 */     fi.setCity(city);
/* 357:575 */     fi.setStateCode(stateCode);
/* 358:    */     
/* 359:577 */     FinancialInstitutions fis = FinancialInstitutionAdapter.get(fi, true, 1, connection);
/* 360:579 */     if ((fis != null) && (!fis.getSize().equals("0")) && (fis.get(0) != null)) {
/* 361:582 */       updateFinancialInstitution((FinancialInstitution)fis.get(0), 0, routingNumber, institutionName, street, city, stateCode, zipCode, zipCodeExtension, areaCode, telPrefix, telSuffix, newRoutingNumber, recordType, connection);
/* 362:    */     } else {
/* 363:591 */       updateFinancialInstitution(0, routingNumber, institutionName, street, city, stateCode, zipCode, zipCodeExtension, areaCode, telPrefix, telSuffix, newRoutingNumber, recordType, connection);
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   private static void updateFinancialInstitution(int id, String routingNumber, String institutionName, String street, String city, String stateCode, String zipCode, String zipCodeExtension, String areaCode, String telPrefix, String telSuffix, String newRoutingNumber, String recordType, Connection connection)
/* 368:    */     throws FinancialInstitutionException
/* 369:    */   {
/* 370:    */     try
/* 371:    */     {
/* 372:641 */       if (routingNumber != null) {
/* 373:642 */         routingNumber = routingNumber.trim().toUpperCase();
/* 374:    */       }
/* 375:643 */       if (institutionName != null) {
/* 376:644 */         institutionName = institutionName.trim().toUpperCase();
/* 377:    */       }
/* 378:645 */       if (street != null) {
/* 379:646 */         street = street.trim().toUpperCase();
/* 380:    */       }
/* 381:647 */       if (city != null) {
/* 382:648 */         city = city.trim().toUpperCase();
/* 383:    */       }
/* 384:649 */       if (stateCode != null) {
/* 385:650 */         stateCode = stateCode.trim().toUpperCase();
/* 386:    */       }
/* 387:651 */       if (zipCode != null) {
/* 388:652 */         zipCode = zipCode.trim().toUpperCase();
/* 389:    */       }
/* 390:653 */       if (zipCodeExtension != null)
/* 391:    */       {
/* 392:655 */         zipCodeExtension = zipCodeExtension.trim().toUpperCase();
/* 393:656 */         if (zipCode != null) {
/* 394:657 */           zipCode = zipCode.concat(zipCodeExtension);
/* 395:    */         }
/* 396:    */       }
/* 397:660 */       String phone = null;
/* 398:661 */       if (areaCode != null)
/* 399:    */       {
/* 400:663 */         areaCode = areaCode.trim().toUpperCase();
/* 401:664 */         if ((phone == null) && (!areaCode.equals(""))) {
/* 402:665 */           phone = areaCode;
/* 403:    */         }
/* 404:    */       }
/* 405:667 */       if (telPrefix != null)
/* 406:    */       {
/* 407:669 */         telPrefix = telPrefix.trim().toUpperCase();
/* 408:670 */         if (phone != null) {
/* 409:671 */           phone = phone.concat(telPrefix);
/* 410:672 */         } else if (!telPrefix.equals("")) {
/* 411:673 */           phone = telPrefix;
/* 412:    */         }
/* 413:    */       }
/* 414:675 */       if (telSuffix != null)
/* 415:    */       {
/* 416:677 */         telSuffix = telSuffix.trim().toUpperCase();
/* 417:678 */         if ((phone != null) && (!telSuffix.equals(""))) {
/* 418:679 */           phone = phone.concat(telSuffix);
/* 419:680 */         } else if (!telSuffix.equals("")) {
/* 420:681 */           phone = telSuffix;
/* 421:    */         }
/* 422:    */       }
/* 423:683 */       if (newRoutingNumber != null) {
/* 424:684 */         newRoutingNumber = newRoutingNumber.trim().toUpperCase();
/* 425:    */       }
/* 426:690 */       if (!recordType.equals("2"))
/* 427:    */       {
/* 428:691 */         FinancialInstitution fi = new FinancialInstitution();
/* 429:692 */         if (!newRoutingNumber.equals("000000000")) {
/* 430:693 */           fi.setAchRoutingNumber(newRoutingNumber);
/* 431:    */         } else {
/* 432:695 */           fi.setAchRoutingNumber(routingNumber);
/* 433:    */         }
/* 434:696 */         fi.addRoutingNumber(routingNumber);
/* 435:697 */         fi.setInstitutionName(institutionName);
/* 436:698 */         fi.setStreet(street);
/* 437:699 */         fi.setCity(city);
/* 438:700 */         fi.setStateCode(stateCode);
/* 439:701 */         fi.setZipCode(zipCode);
/* 440:702 */         fi.setPhone(phone);
/* 441:703 */         fi.setCountry("UNITED STATES");
/* 442:704 */         FinancialInstitutionAdapter.insert(fi, connection);
/* 443:    */       }
/* 444:    */     }
/* 445:    */     catch (Exception e)
/* 446:    */     {
/* 447:709 */       e.printStackTrace();
/* 448:710 */       DebugLog.log(Level.SEVERE, "+++ FedAchAdapter.synchronize failed. Error: " + e.getMessage());
/* 449:    */       
/* 450:712 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data: " + e.getMessage());
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   private static void updateFinancialInstitution(FinancialInstitution fi, int id, String routingNumber, String institutionName, String street, String city, String stateCode, String zipCode, String zipCodeExtension, String areaCode, String telPrefix, String telSuffix, String newRoutingNumber, String recordType, Connection connection)
/* 455:    */     throws FinancialInstitutionException
/* 456:    */   {
/* 457:    */     try
/* 458:    */     {
/* 459:758 */       boolean shouldUpdate = false;
/* 460:759 */       String val = null;
/* 461:760 */       val = fi.getInstitutionName();
/* 462:761 */       if (((val != null) && (!val.equals(institutionName.trim()))) || ((val == null) && (institutionName != null) && (!institutionName.equals(""))))
/* 463:    */       {
/* 464:765 */         fi.setInstitutionName(institutionName);
/* 465:766 */         shouldUpdate = true;
/* 466:    */       }
/* 467:769 */       val = fi.getStreet();
/* 468:770 */       if (((val != null) && (!val.equals(street.trim()))) || ((val == null) && (street != null) && (!street.equals(""))))
/* 469:    */       {
/* 470:773 */         fi.setStreet(street);
/* 471:774 */         shouldUpdate = true;
/* 472:    */       }
/* 473:777 */       val = fi.getCity();
/* 474:778 */       if (((val != null) && (!val.equals(city.trim()))) || ((val == null) && (city != null) && (!city.equals(""))))
/* 475:    */       {
/* 476:781 */         fi.setCity(city);
/* 477:782 */         shouldUpdate = true;
/* 478:    */       }
/* 479:785 */       val = fi.getStateCode();
/* 480:786 */       if (((val != null) && (!val.equals(stateCode.trim()))) || ((val == null) && (stateCode != null) && (!stateCode.equals(""))))
/* 481:    */       {
/* 482:789 */         fi.setStateCode(stateCode);
/* 483:790 */         shouldUpdate = true;
/* 484:    */       }
/* 485:793 */       val = fi.getZipCode();
/* 486:795 */       if (zipCode != null) {
/* 487:796 */         zipCode = zipCode.trim().toUpperCase();
/* 488:    */       }
/* 489:797 */       if (zipCodeExtension != null)
/* 490:    */       {
/* 491:799 */         zipCodeExtension = zipCodeExtension.trim().toUpperCase();
/* 492:800 */         if (zipCode != null) {
/* 493:801 */           zipCode = zipCode.concat(zipCodeExtension);
/* 494:    */         }
/* 495:    */       }
/* 496:804 */       if (((val != null) && (!val.equals(zipCode))) || ((val == null) && (zipCode != null) && (!zipCode.equals(""))))
/* 497:    */       {
/* 498:807 */         fi.setZipCode(zipCode);
/* 499:808 */         shouldUpdate = true;
/* 500:    */       }
/* 501:811 */       String phone = null;
/* 502:812 */       if (areaCode != null)
/* 503:    */       {
/* 504:814 */         areaCode = areaCode.trim().toUpperCase();
/* 505:815 */         if ((phone == null) && (!areaCode.equals(""))) {
/* 506:816 */           phone = areaCode;
/* 507:    */         }
/* 508:    */       }
/* 509:818 */       if (telPrefix != null)
/* 510:    */       {
/* 511:820 */         telPrefix = telPrefix.trim().toUpperCase();
/* 512:821 */         if (phone != null) {
/* 513:822 */           phone = phone.concat(telPrefix);
/* 514:823 */         } else if (!telPrefix.equals("")) {
/* 515:824 */           phone = telPrefix;
/* 516:    */         }
/* 517:    */       }
/* 518:826 */       if (telSuffix != null)
/* 519:    */       {
/* 520:828 */         telSuffix = telSuffix.trim().toUpperCase();
/* 521:829 */         if ((phone != null) && (!telSuffix.equals(""))) {
/* 522:830 */           phone = phone.concat(telSuffix);
/* 523:831 */         } else if (!telSuffix.equals("")) {
/* 524:832 */           phone = telSuffix;
/* 525:    */         }
/* 526:    */       }
/* 527:835 */       val = fi.getPhone();
/* 528:836 */       String formattedPhone = new Phone(phone).toString();
/* 529:837 */       if (((val != null) && (formattedPhone != null) && (!val.equals(formattedPhone))) || ((val == null) && (formattedPhone != null) && (!formattedPhone.equals(""))))
/* 530:    */       {
/* 531:840 */         fi.setPhone(phone);
/* 532:841 */         shouldUpdate = true;
/* 533:    */       }
/* 534:844 */       val = fi.getCountry();
/* 535:845 */       if ((val != null) && (!val.equals("UNITED STATES")))
/* 536:    */       {
/* 537:847 */         fi.setCountry("UNITED STATES");
/* 538:848 */         shouldUpdate = true;
/* 539:    */       }
/* 540:853 */       String oldAchRoutingNumber = fi.getAchRoutingNumber();
/* 541:854 */       if (!routingNumber.equals("000000000")) {
/* 542:856 */         if (!fi.existsRoutingNumber(routingNumber))
/* 543:    */         {
/* 544:858 */           fi.addRoutingNumber(routingNumber);
/* 545:859 */           fi.setAchRoutingNumber(routingNumber);
/* 546:860 */           shouldUpdate = true;
/* 547:    */         }
/* 548:    */       }
/* 549:863 */       if (!newRoutingNumber.equals("000000000"))
/* 550:    */       {
/* 551:865 */         if (!fi.existsRoutingNumber(newRoutingNumber))
/* 552:    */         {
/* 553:867 */           fi.addRoutingNumber(newRoutingNumber);
/* 554:868 */           shouldUpdate = true;
/* 555:    */         }
/* 556:870 */         fi.setAchRoutingNumber(newRoutingNumber);
/* 557:    */       }
/* 558:872 */       if (!oldAchRoutingNumber.equals(fi.getAchRoutingNumber())) {
/* 559:873 */         shouldUpdate = true;
/* 560:    */       }
/* 561:875 */       if (shouldUpdate) {
/* 562:877 */         FinancialInstitutionAdapter.update(fi, connection);
/* 563:    */       }
/* 564:    */     }
/* 565:    */     catch (Exception e)
/* 566:    */     {
/* 567:882 */       e.printStackTrace();
/* 568:883 */       DebugLog.log(Level.SEVERE, "+++ FedAchAdapter.updateFinancialInstitution failed. Error: " + e.getMessage());
/* 569:    */       
/* 570:885 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data");
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   private static void deleteByID(int data, boolean deep, Connection connection)
/* 575:    */     throws FinancialInstitutionException
/* 576:    */   {
/* 577:904 */     if (data <= 0) {
/* 578:905 */       return;
/* 579:    */     }
/* 580:906 */     HashMap map = new HashMap(1);
/* 581:907 */     map.put("FedInstitutionId", Integer.toString(data));
/* 582:908 */     if (deep)
/* 583:    */     {
/* 584:909 */       String[] args = { Integer.toString(data) };
/* 585:910 */       String[] row = gta.getRow(args, null);
/* 586:911 */       FinancialInstitutionAdapter.deleteAchRoutingNumber(row[1], connection);
/* 587:    */     }
/* 588:913 */     gta.deleteRow(map, null, connection);
/* 589:    */   }
/* 590:    */   
/* 591:    */   private static void checkInitialization()
/* 592:    */     throws FinancialInstitutionException
/* 593:    */   {
/* 594:925 */     if (!isInitialized) {
/* 595:927 */       throw new FinancialInstitutionException(2, "FedAchAdapter not initialized");
/* 596:    */     }
/* 597:    */   }
/* 598:    */ }


/* Location:           D:\drops\jd\jars\ffiblcustom.jar
 * Qualified Name:     com.ffusion.banklookup.datasource.adapters.FedAchAdapter
 * JD-Core Version:    0.7.0.1
 */