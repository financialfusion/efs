/*    1:     */ package com.ffusion.banklookup.datasource.adapters;
/*    2:     */ 
/*    3:     */ import com.ffusion.banklookup.FinancialInstitutionException;
/*    4:     */ import com.ffusion.banklookup.adapters.FinancialInstitutionAdapter;
/*    5:     */ import com.ffusion.banklookup.adapters.GenericTableAdapter;
/*    6:     */ import com.ffusion.banklookup.adapters.GenericTableAdapterConsts;
/*    7:     */ import com.ffusion.banklookup.beans.FinancialInstitution;
/*    8:     */ import com.ffusion.banklookup.beans.FinancialInstitutions;
/*    9:     */ import com.ffusion.beans.ZipCode;
/*   10:     */ import com.ffusion.util.db.DBUtil;
/*   11:     */ import com.ffusion.util.logging.DebugLog;
/*   12:     */ import java.sql.Connection;
/*   13:     */ import java.sql.PreparedStatement;
/*   14:     */ import java.sql.ResultSet;
/*   15:     */ import java.util.HashMap;
/*   16:     */ import java.util.logging.Level;
/*   17:     */ 
/*   18:     */ public class SwiftAdapter
/*   19:     */   implements GenericTableAdapterConsts, SwiftAdapterConsts
/*   20:     */ {
/*   21:  41 */   private static final String[] KEYS = { "SwiftInstitutionId" };
/*   22:     */   private static final String SEQUENCE_NAME = "SWIFTBIC_ID";
/*   23:  43 */   private static final String[][] KEYSTOCHECK = { { "BIC", "BICBranchCode" } };
/*   24:     */   private static final String SELECT_FOR_MERGE = "SELECT SwiftInstitutionId,ModificationFlag,BIC,BICBranchCode,InstitutionName1,InstitutionName2,InstitutionName3,BranchInformation1,BranchInformation2,City,Address1,Address2,Address3,Address4,Location1,Location2,Location3,Country1,Country2 FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?";
/*   25:     */   private static final String SELECT_TO_GET_MIN_VALUE = "SELECT MIN(SwiftInstitutionId) FROM LU_SwiftBIC_FI";
/*   26:     */   private static final String SELECT_TO_GET_MAX_VALUE = "SELECT MAX(SwiftInstitutionId) FROM LU_SwiftBIC_FI";
/*   27:     */   private static final String DELETE_ROWS = "DELETE FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?";
/*   28:     */   private static final String SELECT_FOR_PURGING = "SELECT CONCAT(BIC,BICBranchCode)  FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?";
/*   29:     */   private static final String SELECT_FOR_PURGING_MSSQL = "SELECT BIC+BICBranchCode FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?";
/*   30:  87 */   private static GenericTableAdapter gta = new GenericTableAdapter();
/*   31:  88 */   private static boolean isInitialized = false;
/*   32:     */   private static final String SELECT_FOR_AMENDMENT = "SELECT SwiftInstitutionId FROM LU_SwiftBIC_FI WHERE BIC = ?  AND BICBranchCode = ? ";
/*   33:     */   private static final String UPDATE_FOR_AMENDMENT = "UPDATE LU_SwiftBIC_FI SET BIC = ? ,BICBranchCode = ?  WHERE SwiftInstitutionId = ? ";
/*   34:     */   
/*   35:     */   public static void initialize()
/*   36:     */     throws FinancialInstitutionException
/*   37:     */   {
/*   38: 105 */     if (!isInitialized)
/*   39:     */     {
/*   40: 107 */       DebugLog.log(Level.FINE, "+++ SwiftAdapter.initialize");
/*   41: 108 */       gta.initialize(null, "LU_SwiftBIC_FI", SwiftAdapterConsts.COLUMNS, KEYS, true, "SWIFTBIC_ID", KEYSTOCHECK);
/*   42:     */       
/*   43: 110 */       FinancialInstitutionAdapter.initialize(null);
/*   44: 111 */       isInitialized = true;
/*   45:     */     }
/*   46:     */   }
/*   47:     */   
/*   48:     */   public static void insert(HashMap data, HashMap extra)
/*   49:     */     throws FinancialInstitutionException
/*   50:     */   {
/*   51: 129 */     insert(data, extra, true);
/*   52:     */   }
/*   53:     */   
/*   54:     */   public static void insert(HashMap data, HashMap extra, boolean deep)
/*   55:     */     throws FinancialInstitutionException
/*   56:     */   {
/*   57: 147 */     HashMap[] rows = { data };
/*   58: 148 */     insert(rows, extra, deep);
/*   59:     */   }
/*   60:     */   
/*   61:     */   public static void insert(HashMap[] rows, HashMap extra, boolean deep)
/*   62:     */     throws FinancialInstitutionException
/*   63:     */   {
/*   64: 171 */     checkInitialization();
/*   65: 172 */     Connection connection = null;
/*   66:     */     try
/*   67:     */     {
/*   68: 175 */       connection = GenericTableAdapter.getConnection();
/*   69: 176 */       for (int i = 0; i < rows.length; i++)
/*   70:     */       {
/*   71: 178 */         HashMap row = rows[i];
/*   72: 179 */         insert(row, extra, deep, connection);
/*   73:     */       }
/*   74: 181 */       connection.commit();
/*   75:     */     }
/*   76:     */     catch (Exception e)
/*   77:     */     {
/*   78: 185 */       DBUtil.rollback(connection);
/*   79: 186 */       if ((e instanceof FinancialInstitutionException)) {
/*   80: 187 */         throw ((FinancialInstitutionException)e);
/*   81:     */       }
/*   82: 189 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the Swift table: " + e.getMessage());
/*   83:     */     }
/*   84:     */     finally
/*   85:     */     {
/*   86: 196 */       GenericTableAdapter.releaseConnection(connection);
/*   87:     */     }
/*   88:     */   }
/*   89:     */   
/*   90:     */   public static GenericTableAdapter getGenericTableAdapter()
/*   91:     */   {
/*   92: 205 */     return gta;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static void insertData(HashMap data, HashMap extra, boolean deep, Connection connection)
/*   96:     */   {
/*   97: 221 */     HashMap[] rows = { data };
/*   98:     */     try
/*   99:     */     {
/*  100: 223 */       insert(rows, extra, deep, connection);
/*  101:     */     }
/*  102:     */     catch (Throwable t)
/*  103:     */     {
/*  104: 225 */       String bicCode = (String)data.get("BIC");
/*  105: 226 */       String bicBranchCode = (String)data.get("BICBranchCode");
/*  106: 227 */       DebugLog.log("SwiftAdapter.insertData failed for bicCode: " + bicCode + " and bicBranchCode: " + bicBranchCode + ": " + t.getMessage());
/*  107:     */     }
/*  108:     */   }
/*  109:     */   
/*  110:     */   public static void insert(HashMap[] rows, HashMap extra, boolean deep, Connection connection)
/*  111:     */     throws FinancialInstitutionException
/*  112:     */   {
/*  113:     */     
/*  114:     */     try
/*  115:     */     {
/*  116: 255 */       for (int i = 0; i < rows.length; i++)
/*  117:     */       {
/*  118: 257 */         HashMap row = rows[i];
/*  119: 258 */         insert(row, extra, deep, connection);
/*  120:     */       }
/*  121:     */     }
/*  122:     */     catch (Exception e)
/*  123:     */     {
/*  124: 263 */       if ((e instanceof FinancialInstitutionException)) {
/*  125: 264 */         throw ((FinancialInstitutionException)e);
/*  126:     */       }
/*  127: 266 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the Swift table: " + e.getMessage());
/*  128:     */     }
/*  129:     */   }
/*  130:     */   
/*  131:     */   public static void deleteByID(int data)
/*  132:     */     throws FinancialInstitutionException
/*  133:     */   {
/*  134: 284 */     deleteByID(data, true);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public static void deleteByID(int data, boolean deep)
/*  138:     */     throws FinancialInstitutionException
/*  139:     */   {
/*  140: 299 */     if (data <= 0) {
/*  141: 300 */       return;
/*  142:     */     }
/*  143: 301 */     Connection connection = null;
/*  144:     */     try
/*  145:     */     {
/*  146: 304 */       connection = GenericTableAdapter.getConnection();
/*  147: 305 */       deleteByID(data, deep, connection);
/*  148: 306 */       connection.commit();
/*  149:     */     }
/*  150:     */     catch (Exception e)
/*  151:     */     {
/*  152: 310 */       if (connection != null) {
/*  153: 311 */         DBUtil.rollback(connection);
/*  154:     */       }
/*  155:     */     }
/*  156:     */     finally
/*  157:     */     {
/*  158: 315 */       GenericTableAdapter.releaseConnection(connection);
/*  159:     */     }
/*  160:     */   }
/*  161:     */   
/*  162:     */   public static void purge()
/*  163:     */     throws FinancialInstitutionException
/*  164:     */   {
/*  165: 328 */     purge(2147483646, true);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public static void purge(int batchsize)
/*  169:     */     throws FinancialInstitutionException
/*  170:     */   {
/*  171: 342 */     purge(batchsize, true);
/*  172:     */   }
/*  173:     */   
/*  174:     */   public static void purge(int batchsize, boolean deep)
/*  175:     */     throws FinancialInstitutionException
/*  176:     */   {
/*  177: 357 */     checkInitialization();
/*  178: 358 */     DebugLog.log(Level.FINE, "+++ SwiftAdapter.purge ");
/*  179: 359 */     Connection connection = null;
/*  180: 360 */     PreparedStatement pStmt = null;
/*  181:     */     try
/*  182:     */     {
/*  183: 363 */       connection = GenericTableAdapter.getConnection();
/*  184: 364 */       int minValue = gta.getKeyValue(false, connection);
/*  185: 365 */       int maxValue = gta.getKeyValue(true, connection);
/*  186: 366 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/*  187:     */       {
/*  188: 368 */         DebugLog.log(Level.FINE, "+++ SwiftAdapter.purge: Purging - " + i + "," + (i + batchsize));
/*  189:     */         
/*  190: 370 */         Integer[] args = { new Integer(i), new Integer(i + batchsize) };
/*  191: 371 */         if (deep) {
/*  192: 375 */           if ((gta.getDbType().startsWith("MSSQL:THIN")) || (gta.getDbType().startsWith("ASE"))) {
/*  193: 377 */             FinancialInstitutionAdapter.purgeSwiftNumbers("SELECT BIC+BICBranchCode FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?", args, connection);
/*  194:     */           } else {
/*  195: 379 */             FinancialInstitutionAdapter.purgeSwiftNumbers("SELECT CONCAT(BIC,BICBranchCode)  FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?", args, connection);
/*  196:     */           }
/*  197:     */         }
/*  198: 382 */         pStmt = DBUtil.prepareStatement(connection, "DELETE FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?");
/*  199: 383 */         GenericTableAdapter.fillParameters(pStmt, args);
/*  200: 384 */         DBUtil.executeUpdate(pStmt, "DELETE FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?");
/*  201: 385 */         connection.commit();
/*  202:     */       }
/*  203:     */     }
/*  204:     */     catch (Exception e)
/*  205:     */     {
/*  206:     */       try
/*  207:     */       {
/*  208: 391 */         connection.rollback();
/*  209:     */       }
/*  210:     */       catch (Exception ex) {}
/*  211: 395 */       DebugLog.log(Level.SEVERE, "SwiftAdapter purge failed. Error: " + e.getMessage());
/*  212: 397 */       if ((e instanceof FinancialInstitutionException)) {
/*  213: 398 */         throw ((FinancialInstitutionException)e);
/*  214:     */       }
/*  215: 399 */       throw new FinancialInstitutionException(e, 9, "Cannot purge data: " + e.getMessage());
/*  216:     */     }
/*  217:     */     finally
/*  218:     */     {
/*  219: 405 */       if (pStmt != null) {
/*  220: 407 */         DBUtil.closeStatement(pStmt);
/*  221:     */       }
/*  222: 410 */       GenericTableAdapter.releaseConnection(connection);
/*  223:     */     }
/*  224:     */   }
/*  225:     */   
/*  226:     */   public static void synchronize()
/*  227:     */     throws FinancialInstitutionException
/*  228:     */   {
/*  229: 422 */     synchronize(2147483646);
/*  230:     */   }
/*  231:     */   
/*  232:     */   public static void synchronize(int batchsize)
/*  233:     */     throws FinancialInstitutionException
/*  234:     */   {
/*  235: 435 */     checkInitialization();
/*  236: 436 */     DebugLog.log(Level.FINE, "+++ SwiftAdapter.synchronize ");
/*  237: 437 */     Connection connection = null;
/*  238: 438 */     PreparedStatement pStmt = null;
/*  239: 439 */     ResultSet results = null;
/*  240: 440 */     Object[] args = new Object[2];
/*  241:     */     try
/*  242:     */     {
/*  243: 443 */       connection = GenericTableAdapter.getConnection();
/*  244:     */       
/*  245: 445 */       int maxValue = 0;
/*  246: 446 */       int minValue = 0;
/*  247: 447 */       maxValue = gta.getKeyValue(true, connection);
/*  248: 448 */       minValue = gta.getKeyValue(false, connection);
/*  249: 449 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/*  250:     */       {
/*  251: 451 */         DebugLog.log(Level.FINE, "+++ SwiftAdapter.synchronize. Synchronizing between " + i + " and " + (i - 1 + batchsize));
/*  252:     */         
/*  253: 453 */         pStmt = DBUtil.prepareStatement(connection, "SELECT SwiftInstitutionId,ModificationFlag,BIC,BICBranchCode,InstitutionName1,InstitutionName2,InstitutionName3,BranchInformation1,BranchInformation2,City,Address1,Address2,Address3,Address4,Location1,Location2,Location3,Country1,Country2 FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?");
/*  254: 454 */         args[0] = new Integer(i);
/*  255: 455 */         if (2147483647 - batchsize > i) {
/*  256: 456 */           args[1] = new Integer(i - 1 + batchsize);
/*  257:     */         } else {
/*  258: 458 */           args[1] = new Integer(2147483647);
/*  259:     */         }
/*  260: 459 */         GenericTableAdapter.fillParameters(pStmt, args);
/*  261: 460 */         DBUtil.executeQuery(pStmt, "SELECT SwiftInstitutionId,ModificationFlag,BIC,BICBranchCode,InstitutionName1,InstitutionName2,InstitutionName3,BranchInformation1,BranchInformation2,City,Address1,Address2,Address3,Address4,Location1,Location2,Location3,Country1,Country2 FROM LU_SwiftBIC_FI WHERE SwiftInstitutionId>=? AND SwiftInstitutionId<=?");
/*  262: 461 */         boolean rowsFetched = false;
/*  263: 462 */         while (results.next())
/*  264:     */         {
/*  265: 464 */           rowsFetched = true;
/*  266:     */           
/*  267: 466 */           int id = results.getInt(1);
/*  268: 467 */           String modificationFlag = results.getString(2);
/*  269: 468 */           String bicCode = results.getString(3);
/*  270: 469 */           String bicBranchCode = results.getString(4);
/*  271: 470 */           String institution1 = results.getString(5);
/*  272: 471 */           String institution2 = results.getString(6);
/*  273: 472 */           String institution3 = results.getString(7);
/*  274: 473 */           String branch1 = results.getString(8);
/*  275: 474 */           String branch2 = results.getString(9);
/*  276: 475 */           String city = results.getString(10);
/*  277: 476 */           String address1 = results.getString(11);
/*  278: 477 */           String address2 = results.getString(12);
/*  279: 478 */           String address3 = results.getString(13);
/*  280: 479 */           String address4 = results.getString(14);
/*  281: 480 */           String location1 = results.getString(15);
/*  282: 481 */           String location2 = results.getString(16);
/*  283: 482 */           String location3 = results.getString(17);
/*  284: 483 */           String country1 = results.getString(18);
/*  285: 484 */           String country2 = results.getString(19);
/*  286: 485 */           updateFinancialInstitution(id, modificationFlag, bicCode, bicBranchCode, institution1, institution2, institution3, branch1, branch2, city, address1, address2, address3, address4, location1, location2, location3, country1, country2, connection);
/*  287:     */         }
/*  288: 494 */         DBUtil.closeResultSet(results);
/*  289: 495 */         results = null;
/*  290: 496 */         connection.commit();
/*  291:     */       }
/*  292:     */     }
/*  293:     */     catch (FinancialInstitutionException fie)
/*  294:     */     {
/*  295: 501 */       DBUtil.rollback(connection);
/*  296: 502 */       DebugLog.log(Level.SEVERE, "SwiftAdapter synchronize failed. Error: " + fie.getMessage());
/*  297:     */       
/*  298: 504 */       throw fie;
/*  299:     */     }
/*  300:     */     catch (Throwable e)
/*  301:     */     {
/*  302: 508 */       e.printStackTrace();
/*  303: 509 */       DBUtil.rollback(connection);
/*  304: 510 */       DebugLog.log(Level.SEVERE, "SwiftAdapter synchronize failed. Error: " + e.getMessage());
/*  305:     */       
/*  306: 512 */       throw new FinancialInstitutionException(e, 4, "Cannot synchronize data: " + e.getMessage());
/*  307:     */     }
/*  308:     */     finally
/*  309:     */     {
/*  310:     */       try
/*  311:     */       {
/*  312: 520 */         if (results != null)
/*  313:     */         {
/*  314: 522 */           DBUtil.closeResultSet(results);
/*  315: 523 */           results = null;
/*  316:     */         }
/*  317:     */       }
/*  318:     */       catch (Throwable e) {}
/*  319: 531 */       if (pStmt != null) {
/*  320: 533 */         DBUtil.closeStatement(pStmt);
/*  321:     */       }
/*  322: 535 */       GenericTableAdapter.releaseConnection(connection);
/*  323:     */     }
/*  324:     */   }
/*  325:     */   
/*  326:     */   public static void insert(HashMap data, HashMap extra, boolean deep, Connection connection)
/*  327:     */     throws FinancialInstitutionException
/*  328:     */   {
/*  329: 565 */     DebugLog.log("+++ SwiftAdapter.processRow ");
/*  330:     */     
/*  331:     */ 
/*  332: 568 */     String modificationFlag = (String)data.get("ModificationFlag");
/*  333: 569 */     String bicCode = (String)data.get("BIC");
/*  334: 570 */     String bicBranchCode = (String)data.get("BICBranchCode");
/*  335: 571 */     String institution1 = (String)data.get("InstitutionName1");
/*  336: 572 */     String institution2 = (String)data.get("InstitutionName2");
/*  337: 573 */     String institution3 = (String)data.get("InstitutionName3");
/*  338: 574 */     String branch1 = (String)data.get("BranchInformation1");
/*  339: 575 */     String branch2 = (String)data.get("BranchInformation2");
/*  340: 576 */     String city = (String)data.get("City");
/*  341: 577 */     String address1 = (String)data.get("Address1");
/*  342: 578 */     String address2 = (String)data.get("Address2");
/*  343: 579 */     String address3 = (String)data.get("Address3");
/*  344: 580 */     String address4 = (String)data.get("Address4");
/*  345: 581 */     String location1 = (String)data.get("Location1");
/*  346: 582 */     String location2 = (String)data.get("Location2");
/*  347: 583 */     String location3 = (String)data.get("Location3");
/*  348: 584 */     String country1 = (String)data.get("Country1");
/*  349: 585 */     String country2 = (String)data.get("Country2");
/*  350:     */     
/*  351: 587 */     gta.insertSwiftBICRow(data, extra, connection, modificationFlag);
/*  352: 588 */     if (!deep) {
/*  353: 589 */       return;
/*  354:     */     }
/*  355: 591 */     updateFinancialInstitution(0, modificationFlag, bicCode, bicBranchCode, institution1, institution2, institution3, branch1, branch2, city, address1, address2, address3, address4, location1, location2, location3, country1, country2, connection);
/*  356:     */   }
/*  357:     */   
/*  358:     */   private static void updateFinancialInstitution(int id, String modificationFlag, String bicCode, String bicBranchCode, String institution1, String institution2, String institution3, String branch1, String branch2, String city, String address1, String address2, String address3, String address4, String location1, String location2, String location3, String country1, String country2, Connection connection)
/*  359:     */     throws FinancialInstitutionException
/*  360:     */   {
/*  361:     */     try
/*  362:     */     {
/*  363: 653 */       DebugLog.log(Level.FINE, "+++ Adding swift data for " + bicCode + bicBranchCode);
/*  364: 654 */       if (bicCode != null) {
/*  365: 655 */         bicCode = bicCode.trim().toUpperCase();
/*  366:     */       }
/*  367: 656 */       if (bicBranchCode != null) {
/*  368: 657 */         bicBranchCode = bicBranchCode.trim().toUpperCase();
/*  369:     */       }
/*  370: 658 */       if ((bicCode != null) && (!bicCode.equals(""))) {
/*  371: 660 */         if ((bicBranchCode != null) && (!bicBranchCode.equals(""))) {
/*  372: 661 */           bicCode = bicCode.concat(bicBranchCode);
/*  373:     */         } else {
/*  374: 663 */           bicCode = bicCode.concat("XXX");
/*  375:     */         }
/*  376:     */       }
/*  377: 665 */       if (bicCode == null)
/*  378:     */       {
/*  379: 668 */         DebugLog.log(Level.SEVERE, "Error in SwiftAdapter.updateFinancialInstitution.  Error:  Invalid bic code.  Bic code is null");
/*  380:     */         
/*  381: 670 */         throw new FinancialInstitutionException(11, "SwiftAdapter: Invalid bic code specified.  Bic code cannot be null");
/*  382:     */       }
/*  383: 674 */       if (institution1 != null) {
/*  384: 675 */         institution1 = institution1.trim().toUpperCase();
/*  385:     */       }
/*  386: 676 */       if (institution2 != null) {
/*  387: 677 */         institution2 = institution2.trim().toUpperCase();
/*  388:     */       }
/*  389: 678 */       if (institution3 != null) {
/*  390: 679 */         institution3 = institution3.trim().toUpperCase();
/*  391:     */       }
/*  392: 680 */       if (branch1 != null) {
/*  393: 681 */         branch1 = branch1.trim().toUpperCase();
/*  394:     */       }
/*  395: 682 */       if (branch2 != null) {
/*  396: 683 */         branch2 = branch2.trim().toUpperCase();
/*  397:     */       }
/*  398: 684 */       if (city != null) {
/*  399: 685 */         city = city.trim().toUpperCase();
/*  400:     */       }
/*  401: 686 */       if (address1 != null) {
/*  402: 687 */         address1 = address1.trim().toUpperCase();
/*  403:     */       }
/*  404: 688 */       if (address2 != null) {
/*  405: 689 */         address2 = address2.trim().toUpperCase();
/*  406:     */       }
/*  407: 690 */       if (address3 != null) {
/*  408: 691 */         address3 = address3.trim().toUpperCase();
/*  409:     */       }
/*  410: 692 */       if (address4 != null) {
/*  411: 693 */         address4 = address4.trim().toUpperCase();
/*  412:     */       }
/*  413: 694 */       if (location1 != null) {
/*  414: 695 */         location1 = location1.trim().toUpperCase();
/*  415:     */       }
/*  416: 696 */       if (location2 != null) {
/*  417: 697 */         location2 = location2.trim().toUpperCase();
/*  418:     */       }
/*  419: 698 */       if (location3 != null) {
/*  420: 699 */         location3 = location3.trim().toUpperCase();
/*  421:     */       }
/*  422: 700 */       if (country1 != null) {
/*  423: 701 */         country1 = country1.trim().toUpperCase();
/*  424:     */       }
/*  425: 702 */       if (country2 != null) {
/*  426: 703 */         country2 = country2.trim().toUpperCase();
/*  427:     */       }
/*  428: 704 */       String institutionName = null;
/*  429: 705 */       if ((institution1 != null) && (!institution1.equals("")))
/*  430:     */       {
/*  431: 707 */         institutionName = institution1;
/*  432: 708 */         if ((institution2 != null) && (!institution2.equals(""))) {
/*  433: 709 */           institutionName = institutionName.concat(institution2);
/*  434:     */         }
/*  435: 710 */         if ((institution3 != null) && (!institution3.equals(""))) {
/*  436: 711 */           institutionName = institutionName.concat(institution3);
/*  437:     */         }
/*  438:     */       }
/*  439: 714 */       String branchName = null;
/*  440: 715 */       if ((branch1 != null) && (!branch1.equals("")))
/*  441:     */       {
/*  442: 717 */         branchName = branch1;
/*  443: 718 */         if ((branch2 != null) && (!branch2.equals(""))) {
/*  444: 719 */           branchName = branchName.concat(branch2);
/*  445:     */         }
/*  446:     */       }
/*  447: 721 */       String country = null;
/*  448: 722 */       if ((country1 != null) && (!country1.equals("")))
/*  449:     */       {
/*  450: 724 */         country = country1;
/*  451: 725 */         if ((country2 != null) && (!country2.equals(""))) {
/*  452: 726 */           country = country.concat(country2);
/*  453:     */         }
/*  454:     */       }
/*  455: 728 */       String street = new String();
/*  456: 729 */       String street2 = new String();
/*  457: 730 */       String street3 = new String();
/*  458: 731 */       if (address1 != null) {
/*  459: 733 */         if (street.length() == 0) {
/*  460: 734 */           street = street.concat(address1);
/*  461: 735 */         } else if (street2.length() == 0) {
/*  462: 736 */           street2 = street2.concat(address1);
/*  463:     */         } else {
/*  464: 738 */           street3 = street3.concat(address1);
/*  465:     */         }
/*  466:     */       }
/*  467: 741 */       if (address2 != null) {
/*  468: 743 */         if (street.length() == 0) {
/*  469: 744 */           street = street.concat(address2);
/*  470: 745 */         } else if (street2.length() == 0) {
/*  471: 746 */           street2 = street2.concat(address2);
/*  472:     */         } else {
/*  473: 748 */           street3 = street3.concat(address2);
/*  474:     */         }
/*  475:     */       }
/*  476: 751 */       if (address3 != null) {
/*  477: 753 */         if (street.length() == 0) {
/*  478: 754 */           street = street.concat(address3);
/*  479: 755 */         } else if (street2.length() == 0) {
/*  480: 756 */           street2 = street2.concat(address3);
/*  481:     */         } else {
/*  482: 758 */           street3 = street3.concat(address3);
/*  483:     */         }
/*  484:     */       }
/*  485: 762 */       if (address4 != null) {
/*  486: 764 */         if (street.length() == 0) {
/*  487: 765 */           street = street.concat(address4);
/*  488: 766 */         } else if (street2.length() == 0) {
/*  489: 767 */           street2 = street2.concat(address4);
/*  490:     */         } else {
/*  491: 769 */           street3 = street3.concat(address4);
/*  492:     */         }
/*  493:     */       }
/*  494: 773 */       if (street.length() == 0)
/*  495:     */       {
/*  496: 775 */         if ((address4 != null) && (location1 != null)) {
/*  497: 776 */           street = street.concat(location1);
/*  498:     */         }
/*  499: 778 */         if (location2 != null) {
/*  500: 780 */           if (street.length() == 0) {
/*  501: 781 */             street = street.concat(location2);
/*  502: 782 */           } else if (street2.length() == 0) {
/*  503: 783 */             street2 = street2.concat(location2);
/*  504:     */           } else {
/*  505: 785 */             street3 = street3.concat(location2);
/*  506:     */           }
/*  507:     */         }
/*  508: 788 */         if (location2 != null) {
/*  509: 790 */           if (street.length() == 0) {
/*  510: 791 */             street = street.concat(location3);
/*  511: 792 */           } else if (street2.length() == 0) {
/*  512: 793 */             street2 = street2.concat(location3);
/*  513:     */           } else {
/*  514: 795 */             street3 = street3.concat(location3);
/*  515:     */           }
/*  516:     */         }
/*  517:     */       }
/*  518: 803 */       String stateCode = null;
/*  519: 804 */       String zipCode = null;
/*  520: 805 */       boolean possiblyUS = false;
/*  521: 806 */       if ((country != null) && (country.equals("UNITED STATES"))) {
/*  522: 807 */         possiblyUS = true;
/*  523:     */       }
/*  524: 808 */       if ((bicCode != null) && (bicCode.length() >= 6))
/*  525:     */       {
/*  526: 810 */         String countryCode = bicCode.substring(4, 6);
/*  527: 811 */         if ((countryCode != null) && (countryCode.equals("US")))
/*  528:     */         {
/*  529: 814 */           country = "UNITED STATES";
/*  530: 815 */           possiblyUS = true;
/*  531:     */         }
/*  532:     */       }
/*  533: 818 */       if (possiblyUS)
/*  534:     */       {
/*  535: 820 */         stateCode = scrubForUSgetStateCode(city);
/*  536: 821 */         city = scrubForUSgetCity(city);
/*  537: 822 */         zipCode = scrubForUSgetZipCode(location1);
/*  538:     */       }
/*  539: 825 */       FinancialInstitution searchCriteria = new FinancialInstitution();
/*  540: 826 */       searchCriteria.setInstitutionName(institutionName);
/*  541: 827 */       searchCriteria.setCity(city);
/*  542: 828 */       searchCriteria.setCountry(country);
/*  543: 829 */       searchCriteria.setSwiftBIC(bicCode);
/*  544: 830 */       FinancialInstitutions fis = FinancialInstitutionAdapter.get(searchCriteria, true, connection);
/*  545: 832 */       if (fis.size() == 1)
/*  546:     */       {
/*  547: 835 */         FinancialInstitution fi = (FinancialInstitution)fis.get(0);
/*  548: 836 */         if (modificationFlag.equals("D"))
/*  549:     */         {
/*  550: 838 */           FinancialInstitutionAdapter.deleteSwiftCode(fi.getSwiftBIC(), connection);
/*  551: 839 */           FinancialInstitutionAdapter.delete(fi, connection);
/*  552:     */         }
/*  553:     */         else
/*  554:     */         {
/*  555: 843 */           if (modificationFlag.equals("U")) {
/*  556: 845 */             return;
/*  557:     */           }
/*  558: 848 */           updateFinancialInstitution(fi, id, bicCode, institutionName, branchName, city, stateCode, zipCode, country, street, street2, street3, connection);
/*  559:     */         }
/*  560:     */       }
/*  561:     */       else
/*  562:     */       {
/*  563: 857 */         if (modificationFlag.equals("D")) {
/*  564: 859 */           return;
/*  565:     */         }
/*  566: 861 */         FinancialInstitution fi = new FinancialInstitution();
/*  567: 862 */         fi.setSwiftBIC(bicCode);
/*  568: 863 */         fi.setInstitutionName(institutionName);
/*  569: 864 */         fi.setBranchName(branchName);
/*  570: 865 */         fi.setCity(city);
/*  571: 866 */         fi.setStateCode(stateCode);
/*  572: 867 */         fi.setZipCode(zipCode);
/*  573: 868 */         fi.setCountry(country);
/*  574: 869 */         fi.setStreet(street);
/*  575: 870 */         fi.setStreet2(street2);
/*  576: 871 */         fi.setStreet3(street3);
/*  577: 872 */         FinancialInstitutionAdapter.insert(fi, connection);
/*  578:     */       }
/*  579:     */     }
/*  580:     */     catch (Exception e)
/*  581:     */     {
/*  582: 877 */       e.printStackTrace();
/*  583: 878 */       DebugLog.log(Level.SEVERE, "SwiftAdapter synchronize failed. Error: " + e.getMessage());
/*  584:     */       
/*  585: 880 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data: " + e.getMessage());
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   private static void updateFinancialInstitution(FinancialInstitution fi, int id, String bicCode, String institutionName, String branchName, String city, String stateCode, String zipCode, String country, String street, String street2, String street3, Connection connection)
/*  590:     */     throws FinancialInstitutionException
/*  591:     */   {
/*  592:     */     try
/*  593:     */     {
/*  594: 923 */       boolean shouldUpdate = false;
/*  595: 924 */       String val = null;
/*  596: 925 */       val = fi.getSwiftBIC();
/*  597: 926 */       if (((val != null) && (!val.equals(bicCode))) || ((val == null) && (bicCode != null) && (!bicCode.equals(""))))
/*  598:     */       {
/*  599: 929 */         fi.setSwiftBIC(bicCode);
/*  600: 930 */         shouldUpdate = true;
/*  601:     */       }
/*  602: 933 */       val = fi.getInstitutionName();
/*  603: 934 */       if (((val != null) && (!val.equals(institutionName))) || ((val == null) && (institutionName != null) && (!institutionName.equals(""))))
/*  604:     */       {
/*  605: 938 */         fi.setInstitutionName(institutionName);
/*  606: 939 */         shouldUpdate = true;
/*  607:     */       }
/*  608: 942 */       val = fi.getBranchName();
/*  609: 943 */       if (((val != null) && (!val.equals(branchName))) || ((val == null) && (branchName != null) && (!branchName.equals(""))))
/*  610:     */       {
/*  611: 946 */         fi.setBranchName(branchName);
/*  612: 947 */         shouldUpdate = true;
/*  613:     */       }
/*  614: 950 */       val = fi.getCity();
/*  615: 951 */       if (((val != null) && (!val.equals(city))) || ((val == null) && (city != null) && (!city.equals(""))))
/*  616:     */       {
/*  617: 954 */         fi.setCity(city);
/*  618: 955 */         shouldUpdate = true;
/*  619:     */       }
/*  620: 959 */       val = fi.getStateCode();
/*  621: 960 */       if (((val != null) && (!val.equals(stateCode))) || ((val == null) && (stateCode != null) && (!stateCode.equals(""))))
/*  622:     */       {
/*  623: 963 */         fi.setStateCode(stateCode);
/*  624: 964 */         shouldUpdate = true;
/*  625:     */       }
/*  626: 967 */       val = fi.getStreet();
/*  627: 968 */       if (((val != null) && (!val.equals(street))) || ((val == null) && (street != null) && (!street.equals(""))))
/*  628:     */       {
/*  629: 971 */         fi.setStreet(street);
/*  630: 972 */         shouldUpdate = true;
/*  631:     */       }
/*  632: 975 */       val = fi.getStreet2();
/*  633: 976 */       if (((val != null) && (!val.equals(street2))) || ((val == null) && (street2 != null) && (!street2.equals(""))))
/*  634:     */       {
/*  635: 979 */         fi.setStreet2(street2);
/*  636: 980 */         shouldUpdate = true;
/*  637:     */       }
/*  638: 983 */       val = fi.getStreet3();
/*  639: 984 */       if (((val != null) && (!val.equals(street3))) || ((val == null) && (street3 != null) && (!street3.equals(""))))
/*  640:     */       {
/*  641: 987 */         fi.setStreet3(street3);
/*  642: 988 */         shouldUpdate = true;
/*  643:     */       }
/*  644: 991 */       val = fi.getZipCode();
/*  645: 992 */       String zip = new ZipCode(zipCode).toString();
/*  646: 993 */       if (((val != null) && (!val.equals(zip))) || ((val == null) && (zipCode != null) && (!zipCode.equals(""))))
/*  647:     */       {
/*  648: 996 */         fi.setZipCode(zip);
/*  649: 997 */         shouldUpdate = true;
/*  650:     */       }
/*  651:1000 */       val = fi.getCountry();
/*  652:1001 */       if (((val != null) && (!val.equals(country))) || ((val == null) && (country != null) && (!country.equals(""))))
/*  653:     */       {
/*  654:1004 */         fi.setCountry(country);
/*  655:1005 */         shouldUpdate = true;
/*  656:     */       }
/*  657:1009 */       if (shouldUpdate) {
/*  658:1010 */         FinancialInstitutionAdapter.update(fi, connection);
/*  659:     */       } else {
/*  660:1013 */         DebugLog.log(Level.FINE, "+++ SwiftAdapter.updateFinancialInstitution  Skipping update of bic " + fi.getSwiftBIC());
/*  661:     */       }
/*  662:     */     }
/*  663:     */     catch (Exception e)
/*  664:     */     {
/*  665:1020 */       e.printStackTrace();
/*  666:1021 */       DebugLog.log(Level.SEVERE, "+++ SwiftAdapter.updateFinancialInstitution  Error: " + e.getMessage());
/*  667:     */       
/*  668:1023 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data: " + e.getMessage());
/*  669:     */     }
/*  670:     */   }
/*  671:     */   
/*  672:     */   private static String scrubForUSgetStateCode(String city)
/*  673:     */   {
/*  674:1040 */     if (city != null)
/*  675:     */     {
/*  676:1042 */       int index = city.indexOf(',');
/*  677:1043 */       if (index > 0)
/*  678:     */       {
/*  679:1046 */         String actualCity = city.substring(0, index);
/*  680:1047 */         String stateCode = city.substring(index + 1, city.length());
/*  681:1048 */         stateCode = stateCode.trim();
/*  682:1049 */         if (stateCode.length() > 2) {
/*  683:1050 */           stateCode = stateCode.substring(0, 2);
/*  684:     */         }
/*  685:1051 */         return stateCode;
/*  686:     */       }
/*  687:     */     }
/*  688:1054 */     return null;
/*  689:     */   }
/*  690:     */   
/*  691:     */   private static String scrubForUSgetCity(String city)
/*  692:     */   {
/*  693:1067 */     if (city != null)
/*  694:     */     {
/*  695:1069 */       int index = city.indexOf(',');
/*  696:1070 */       if (index > 0)
/*  697:     */       {
/*  698:1073 */         String actualCity = city.substring(0, index);
/*  699:1074 */         actualCity = actualCity.trim();
/*  700:1075 */         return actualCity;
/*  701:     */       }
/*  702:     */     }
/*  703:1078 */     return null;
/*  704:     */   }
/*  705:     */   
/*  706:     */   private static String scrubForUSgetZipCode(String location)
/*  707:     */   {
/*  708:1091 */     if (location != null)
/*  709:     */     {
/*  710:1093 */       int index = location.lastIndexOf(' ');
/*  711:1094 */       if (index > 0)
/*  712:     */       {
/*  713:1097 */         String zipCode = location.substring(index, location.length());
/*  714:1098 */         zipCode = zipCode.trim();
/*  715:1099 */         return zipCode;
/*  716:     */       }
/*  717:     */     }
/*  718:1102 */     return null;
/*  719:     */   }
/*  720:     */   
/*  721:     */   private static void deleteByID(int data, boolean deep, Connection connection)
/*  722:     */     throws FinancialInstitutionException
/*  723:     */   {
/*  724:1118 */     if (data <= 0) {
/*  725:1119 */       return;
/*  726:     */     }
/*  727:1120 */     HashMap map = new HashMap(1);
/*  728:1121 */     map.put("SwiftInstitutionId", Integer.toString(data));
/*  729:1122 */     if (deep)
/*  730:     */     {
/*  731:1123 */       String[] args = { Integer.toString(data) };
/*  732:1124 */       String[] row = gta.getRow(args, null);
/*  733:     */       
/*  734:1126 */       FinancialInstitutionAdapter.deleteSwiftCode(row[2] + row[3], connection);
/*  735:     */     }
/*  736:1128 */     gta.deleteRow(map, null, connection);
/*  737:     */   }
/*  738:     */   
/*  739:     */   private static void checkInitialization()
/*  740:     */     throws FinancialInstitutionException
/*  741:     */   {
/*  742:1140 */     if (!isInitialized) {
/*  743:1142 */       throw new FinancialInstitutionException(2, "SwiftAdapter not initialized");
/*  744:     */     }
/*  745:     */   }
/*  746:     */ }


/* Location:           D:\drops\jd\jars\ffiblcustom.jar
 * Qualified Name:     com.ffusion.banklookup.datasource.adapters.SwiftAdapter
 * JD-Core Version:    0.7.0.1
 */