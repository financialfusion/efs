/*    1:     */ package com.ffusion.banklookup.adapters;
/*    2:     */ 
/*    3:     */ import com.ffusion.banklookup.FinancialInstitutionException;
/*    4:     */ import com.ffusion.util.db.ConnectionPool;
/*    5:     */ import com.ffusion.util.db.DBUtil;
/*    6:     */ import com.ffusion.util.db.PoolException;
/*    7:     */ import com.ffusion.util.logging.DebugLog;
/*    8:     */ import java.io.PrintWriter;
/*    9:     */ import java.io.StringWriter;
/*   10:     */ import java.sql.Connection;
/*   11:     */ import java.sql.Date;
/*   12:     */ import java.sql.PreparedStatement;
/*   13:     */ import java.sql.ResultSet;
/*   14:     */ import java.sql.ResultSetMetaData;
/*   15:     */ import java.sql.SQLException;
/*   16:     */ import java.sql.Time;
/*   17:     */ import java.sql.Timestamp;
/*   18:     */ import java.util.HashMap;
/*   19:     */ import java.util.Iterator;
/*   20:     */ import java.util.Properties;
/*   21:     */ import java.util.Set;
/*   22:     */ import java.util.logging.Level;
/*   23:     */ 
/*   24:     */ public class GenericTableAdapter
/*   25:     */   implements GenericTableAdapterConsts
/*   26:     */ {
/*   27:     */   public static final String MY_POOL = "LOOKUP_POOL";
/*   28:     */   public static final String BANKLOOKUP_SETTINGS_TAG = "BANKLOOKUP_SETTINGS";
/*   29:     */   public static final String DB_PROPERTIES_TAG = "DB_PROPERTIES";
/*   30:     */   private static final String INSERT_ROW = "INSERT";
/*   31:     */   private static final String DELETE_ROW_BY_KEY = "DELETE";
/*   32:     */   private static final String UPDATE_ROW_BY_KEY = "UPDATE";
/*   33:     */   private static final String GET_ROW_BY_KEY = "SELECTKEY";
/*   34:     */   private static final String GET_ROW_BASE = "SELECT";
/*   35:     */   private static final String GET_MAX_KEY = "SELECTFORMAX";
/*   36:     */   private static final String GET_MIN_KEY = "SELECTFORMIN";
/*   37:     */   private static final String COLUMNS = "COLUMNS";
/*   38:     */   private static final String KEYS = "KEYS";
/*   39:     */   private static final String HASSEQUENCE = "HASSEQUENCE";
/*   40:     */   private static final String SEQUENCE = "SEQUENCE";
/*   41:     */   private static final String KEYSTOCHECK = "KEYSTOCHECK";
/*   42:  75 */   private static HashMap resourceMap = new HashMap();
/*   43:  76 */   private static boolean isInitialized = false;
/*   44:     */   private String tableName;
/*   45:     */   private String[] columns;
/*   46:     */   private String[] keys;
/*   47:  82 */   private boolean hasSequenceKey = false;
/*   48:     */   private String sequenceName;
/*   49:     */   private String[][] keysToCheck;
/*   50:  85 */   private static HashMap pStmtMap = new HashMap();
/*   51:  88 */   static String connPoolName = null;
/*   52:  89 */   static String dbType = null;
/*   53:     */   
/*   54:     */   public void initialize(HashMap props, String tableName, String[] columns, String[] keys, boolean hasSequenceKey, String sequenceName, String[][] keysToCheck)
/*   55:     */     throws FinancialInstitutionException
/*   56:     */   {
/*   57: 121 */     DebugLog.log(Level.FINE, "+++ GenericTableAdapter.initialize");
/*   58:     */     try
/*   59:     */     {
/*   60: 124 */       if (resourceMap.get(tableName) == null)
/*   61:     */       {
/*   62: 126 */         if ((tableName == null) || (tableName.trim().equals(""))) {
/*   63: 128 */           throw new FinancialInstitutionException(1, "Generic Table Adapter cannot be initialized. Error: Invalid argument - Tablename is null or empty");
/*   64:     */         }
/*   65: 134 */         if ((columns == null) || (columns.length < 1)) {
/*   66: 136 */           throw new FinancialInstitutionException(1, "Generic Table Adapter cannot be initialized. Error: Invalid argument - Column array is null or empty");
/*   67:     */         }
/*   68: 142 */         if ((keys == null) || (keys.length < 1)) {
/*   69: 144 */           throw new FinancialInstitutionException(1, "Generic Table Adapter cannot be initialized. Error: Invalid argument - Key array is null or empty");
/*   70:     */         }
/*   71: 150 */         this.tableName = tableName;
/*   72: 151 */         this.columns = columns;
/*   73: 152 */         this.keys = keys;
/*   74: 153 */         this.hasSequenceKey = hasSequenceKey;
/*   75: 154 */         this.sequenceName = sequenceName;
/*   76: 155 */         this.keysToCheck = keysToCheck;
/*   77:     */         
/*   78: 157 */         HashMap stmts = new HashMap(5);
/*   79:     */         
/*   80: 159 */         stmts.put("INSERT", createInsertStmt(tableName, columns));
/*   81: 160 */         stmts.put("DELETE", createDeleteStmt(tableName, keys));
/*   82: 161 */         stmts.put("UPDATE", createUpdateStmt(tableName, columns, keys));
/*   83:     */         
/*   84: 163 */         stmts.put("SELECTKEY", createSelectByKeyStmt(tableName, columns, keys));
/*   85:     */         
/*   86: 165 */         stmts.put("SELECT", createSelectStmt(tableName, columns));
/*   87: 166 */         if (keys.length == 1)
/*   88:     */         {
/*   89: 168 */           stmts.put("SELECTFORMAX", createSelectMinMaxStmt(true, tableName, keys[0]));
/*   90:     */           
/*   91: 170 */           stmts.put("SELECTFORMIN", createSelectMinMaxStmt(false, tableName, keys[0]));
/*   92:     */         }
/*   93: 173 */         resourceMap.put(tableName, stmts);
/*   94:     */       }
/*   95: 175 */       if (!isInitialized)
/*   96:     */       {
/*   97: 177 */         if (props == null)
/*   98:     */         {
/*   99: 180 */           DebugLog.log(Level.SEVERE, "GenericTableAdapter has not been initialized");
/*  100: 181 */           throw new FinancialInstitutionException(2, "GenericTableAdapter not initialized");
/*  101:     */         }
/*  102: 189 */         HashMap dbSettingsMap = (HashMap)props.get("DB_PROPERTIES");
/*  103: 190 */         if (dbSettingsMap == null)
/*  104:     */         {
/*  105: 191 */           DebugLog.log(Level.SEVERE, "<DB_PROPERTIES> tag not found  in XML configuration file during intialization");
/*  106:     */           
/*  107: 193 */           throw new FinancialInstitutionException(1, "GenericTableAdapter cannot be initialized");
/*  108:     */         }
/*  109: 198 */         Properties dbProperties = getPropertiesFromHashMap(dbSettingsMap);
/*  110:     */         try
/*  111:     */         {
/*  112: 202 */           dbType = dbProperties.getProperty("ConnectionType");
/*  113: 203 */           connPoolName = ConnectionPool.init(dbProperties);
/*  114:     */         }
/*  115:     */         catch (PoolException exc)
/*  116:     */         {
/*  117: 205 */           DebugLog.throwing("Unable to create a DB Connection pool during initialization.", exc);
/*  118:     */           
/*  119: 207 */           throw new FinancialInstitutionException(exc, 1, "Unable to create a DB Connection pool" + exc.getMessage());
/*  120:     */         }
/*  121: 209 */         isInitialized = true;
/*  122:     */       }
/*  123:     */     }
/*  124:     */     catch (Exception e)
/*  125:     */     {
/*  126: 214 */       throw new FinancialInstitutionException(e, 1, "Generic Table Adapter cannot be initialized: " + e.getMessage());
/*  127:     */     }
/*  128:     */   }
/*  129:     */   
/*  130:     */   public String[] getRow(String[] key, HashMap extra)
/*  131:     */     throws FinancialInstitutionException
/*  132:     */   {
/*  133: 237 */     Connection connection = null;
/*  134: 238 */     String[] results = null;
/*  135:     */     try
/*  136:     */     {
/*  137: 241 */       connection = getConnection(false, 2);
/*  138: 242 */       results = getRow(key, extra, connection);
/*  139: 243 */       connection.commit();
/*  140:     */     }
/*  141:     */     catch (Exception e)
/*  142:     */     {
/*  143: 247 */       if (connection != null) {
/*  144: 248 */         DBUtil.rollback(connection);
/*  145:     */       }
/*  146:     */     }
/*  147:     */     finally
/*  148:     */     {
/*  149: 252 */       releaseConnection(connection);
/*  150:     */     }
/*  151: 254 */     return results;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public String[] getRow(String[] key, HashMap extra, Connection connection)
/*  155:     */     throws FinancialInstitutionException
/*  156:     */   {
/*  157: 272 */     ResultSet results = null;
/*  158: 273 */     PreparedStatement pStmt = null;
/*  159: 274 */     String[] data = null;
/*  160:     */     try
/*  161:     */     {
/*  162: 277 */       String stmt = (String)((HashMap)resourceMap.get(this.tableName)).get("SELECTKEY");
/*  163:     */       
/*  164: 279 */       pStmt = DBUtil.prepareStatement(connection, stmt);
/*  165: 281 */       if (key != null) {
/*  166: 282 */         for (int i = 0; i < key.length; i++) {
/*  167: 283 */           pStmt.setString(i + 1, key[i]);
/*  168:     */         }
/*  169:     */       }
/*  170: 286 */       results = DBUtil.executeQuery(pStmt, stmt);
/*  171: 287 */       if (results.next())
/*  172:     */       {
/*  173: 288 */         int noColumns = results.getMetaData().getColumnCount();
/*  174: 289 */         for (int i = 0; i < noColumns; i++) {
/*  175: 290 */           data[i] = results.getString(i + 1);
/*  176:     */         }
/*  177:     */       }
/*  178:     */     }
/*  179:     */     catch (Exception e)
/*  180:     */     {
/*  181: 297 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter getRow failed. Error: " + e.getMessage());
/*  182:     */       
/*  183: 299 */       throw new FinancialInstitutionException(e, 4, "Cannot get data by key for " + this.tableName + ": " + e.getMessage());
/*  184:     */     }
/*  185:     */     finally
/*  186:     */     {
/*  187:     */       try
/*  188:     */       {
/*  189: 308 */         if (results != null) {
/*  190: 309 */           DBUtil.closeResultSet(results);
/*  191:     */         }
/*  192:     */       }
/*  193:     */       catch (Exception e)
/*  194:     */       {
/*  195: 313 */         DebugLog.log(Level.SEVERE, "GenericTableAdapter getRow failed. Error: " + e.getMessage());
/*  196:     */         
/*  197: 315 */         throw new FinancialInstitutionException(e, 4, "Cannot get data by key for " + this.tableName + ": " + e.getMessage());
/*  198:     */       }
/*  199: 320 */       if (pStmt != null) {
/*  200: 322 */         DBUtil.closeStatement(pStmt);
/*  201:     */       }
/*  202:     */     }
/*  203: 325 */     return data;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void insertRow(HashMap data, HashMap extra)
/*  207:     */     throws FinancialInstitutionException
/*  208:     */   {
/*  209: 342 */     checkInitialization();
/*  210: 343 */     Connection connection = null;
/*  211:     */     try
/*  212:     */     {
/*  213: 346 */       connection = getConnection();
/*  214: 347 */       insertRow(data, extra, connection);
/*  215: 348 */       connection.commit();
/*  216:     */     }
/*  217:     */     catch (Exception e)
/*  218:     */     {
/*  219: 352 */       if (connection != null) {
/*  220: 353 */         DBUtil.rollback(connection);
/*  221:     */       }
/*  222: 354 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter insertRow failed. Error: " + e.getMessage());
/*  223:     */       
/*  224: 356 */       throw new FinancialInstitutionException(e, 5, "Cannot insert data into " + this.tableName + ": " + e.getMessage());
/*  225:     */     }
/*  226:     */     finally
/*  227:     */     {
/*  228: 363 */       if (connection != null) {
/*  229: 364 */         releaseConnection(connection);
/*  230:     */       }
/*  231:     */     }
/*  232:     */   }
/*  233:     */   
/*  234:     */   public void insertRow(HashMap data, HashMap extra, Connection connection)
/*  235:     */     throws FinancialInstitutionException
/*  236:     */   {
/*  237: 383 */     checkInitialization();
/*  238: 384 */     DebugLog.log(Level.FINE, "+++ GenericTableAdapter.insertRow with properties " + data.toString());
/*  239:     */     
/*  240: 386 */     PreparedStatement pStmt = null;
/*  241:     */     try
/*  242:     */     {
/*  243: 390 */       HashMap uniqueValue = getUniqueValue(this.keysToCheck, data, connection);
/*  244: 391 */       if (uniqueValue != null)
/*  245:     */       {
/*  246: 393 */         for (int i = 0; i < this.keys.length; i++) {
/*  247: 395 */           data.put(this.keys[i], uniqueValue.get(this.keys[i]));
/*  248:     */         }
/*  249: 397 */         updateRow(data, extra, connection);
/*  250: 398 */         return;
/*  251:     */       }
/*  252: 402 */       populateUniqueKey(data, connection);
/*  253:     */       
/*  254:     */ 
/*  255:     */ 
/*  256:     */ 
/*  257: 407 */       Object[] args = new Object[this.columns.length];
/*  258: 408 */       for (int i = 0; i < this.columns.length; i++)
/*  259:     */       {
/*  260: 409 */         Object argVal = data.get(this.columns[i]);
/*  261: 410 */         String argValStr = null;
/*  262: 411 */         if ((argVal != null) && ((argVal instanceof String)))
/*  263:     */         {
/*  264: 412 */           argValStr = (String)argVal;
/*  265: 413 */           argValStr = argValStr.trim();
/*  266: 414 */           args[i] = argValStr;
/*  267:     */         }
/*  268:     */         else
/*  269:     */         {
/*  270: 416 */           args[i] = data.get(this.columns[i]);
/*  271:     */         }
/*  272:     */       }
/*  273: 420 */       String sql = (String)((HashMap)resourceMap.get(this.tableName)).get("INSERT");
/*  274:     */       
/*  275: 422 */       pStmt = (PreparedStatement)pStmtMap.get(this.tableName + ":" + "INSERT");
/*  276: 423 */       if (pStmt == null)
/*  277:     */       {
/*  278: 424 */         pStmt = DBUtil.prepareStatement(connection, sql);
/*  279: 425 */         pStmtMap.put(this.tableName + ":" + "INSERT", pStmt);
/*  280:     */       }
/*  281: 428 */       fillParameters(pStmt, args);
/*  282:     */       
/*  283: 430 */       DBUtil.executeUpdate(pStmt, sql);
/*  284:     */     }
/*  285:     */     catch (Throwable e)
/*  286:     */     {
/*  287: 434 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter insertRow failed. Error: " + e.getMessage());
/*  288:     */       
/*  289: 436 */       throw new FinancialInstitutionException(e, 5, "Cannot insert data into " + this.tableName + ": " + e.getMessage());
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void insertSwiftBICRow(HashMap data, HashMap extra, Connection connection, String modificationFlag)
/*  294:     */     throws FinancialInstitutionException
/*  295:     */   {
/*  296: 460 */     if (modificationFlag == null) {
/*  297: 461 */       return;
/*  298:     */     }
/*  299: 464 */     checkInitialization();
/*  300: 465 */     PreparedStatement pStmt = null;
/*  301:     */     try
/*  302:     */     {
/*  303: 469 */       HashMap uniqueValue = getUniqueValue(this.keysToCheck, data, connection);
/*  304: 470 */       if (uniqueValue != null)
/*  305:     */       {
/*  306: 472 */         for (int i = 0; i < this.keys.length; i++) {
/*  307: 474 */           data.put(this.keys[i], uniqueValue.get(this.keys[i]));
/*  308:     */         }
/*  309: 477 */         if (modificationFlag.equals("U")) {
/*  310: 479 */           return;
/*  311:     */         }
/*  312: 480 */         if (modificationFlag.equals("D")) {
/*  313: 482 */           deleteRow(data, extra, connection);
/*  314: 483 */         } else if ((modificationFlag.equals("M")) || (modificationFlag.equals("A"))) {
/*  315: 485 */           updateRow(data, extra, connection);
/*  316:     */         }
/*  317: 488 */         return;
/*  318:     */       }
/*  319: 492 */       if (modificationFlag.equals("D")) {
/*  320: 493 */         return;
/*  321:     */       }
/*  322: 497 */       populateUniqueKey(data, connection);
/*  323:     */       
/*  324:     */ 
/*  325:     */ 
/*  326:     */ 
/*  327: 502 */       Object[] args = new Object[this.columns.length];
/*  328: 503 */       for (int i = 0; i < this.columns.length; i++)
/*  329:     */       {
/*  330: 504 */         Object argVal = data.get(this.columns[i]);
/*  331: 505 */         String argValStr = null;
/*  332: 506 */         if ((argVal != null) && ((argVal instanceof String)))
/*  333:     */         {
/*  334: 507 */           argValStr = (String)argVal;
/*  335: 508 */           argValStr = argValStr.trim();
/*  336: 509 */           args[i] = argValStr;
/*  337:     */         }
/*  338:     */         else
/*  339:     */         {
/*  340: 511 */           args[i] = data.get(this.columns[i]);
/*  341:     */         }
/*  342:     */       }
/*  343: 515 */       String sql = (String)((HashMap)resourceMap.get(this.tableName)).get("INSERT");
/*  344:     */       
/*  345: 517 */       pStmt = (PreparedStatement)pStmtMap.get(this.tableName + ":" + "INSERT");
/*  346: 518 */       if (pStmt == null)
/*  347:     */       {
/*  348: 519 */         pStmt = DBUtil.prepareStatement(connection, sql);
/*  349: 520 */         pStmtMap.put(this.tableName + ":" + "INSERT", pStmt);
/*  350:     */       }
/*  351: 523 */       fillParameters(pStmt, args);
/*  352:     */       
/*  353: 525 */       DBUtil.executeUpdate(pStmt, sql);
/*  354:     */     }
/*  355:     */     catch (Throwable e)
/*  356:     */     {
/*  357: 529 */       throw new FinancialInstitutionException(e, 5, "Cannot insert data into " + this.tableName + ": " + e.getMessage());
/*  358:     */     }
/*  359:     */   }
/*  360:     */   
/*  361:     */   public void updateRow(HashMap data, HashMap extra)
/*  362:     */     throws FinancialInstitutionException
/*  363:     */   {
/*  364: 548 */     checkInitialization();
/*  365:     */     
/*  366: 550 */     insertRow(data, extra);
/*  367:     */   }
/*  368:     */   
/*  369:     */   public void updateRow(HashMap data, HashMap extra, Connection connection)
/*  370:     */     throws FinancialInstitutionException
/*  371:     */   {
/*  372: 567 */     checkInitialization();
/*  373: 568 */     DebugLog.log(Level.FINE, "+++ GenericTableAdapter.updateRow with " + data.toString());
/*  374:     */     
/*  375: 570 */     PreparedStatement pStmt = null;
/*  376: 571 */     Object[] args = new Object[this.columns.length + this.keys.length];
/*  377: 572 */     for (int i = 0; i < this.columns.length; i++) {
/*  378: 573 */       args[i] = data.get(this.columns[i]);
/*  379:     */     }
/*  380: 574 */     for (int i = 0; i < this.keys.length; i++) {
/*  381: 575 */       args[(this.columns.length + i)] = data.get(this.keys[i]);
/*  382:     */     }
/*  383:     */     try
/*  384:     */     {
/*  385: 578 */       String sql = (String)((HashMap)resourceMap.get(this.tableName)).get("UPDATE");
/*  386: 579 */       pStmt = DBUtil.prepareStatement(connection, sql);
/*  387:     */       
/*  388: 581 */       fillParameters(pStmt, args);
/*  389:     */       
/*  390: 583 */       DBUtil.executeUpdate(pStmt, sql);
/*  391:     */     }
/*  392:     */     catch (Throwable e)
/*  393:     */     {
/*  394: 587 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter updateRow failed. Error: " + e.getMessage());
/*  395:     */       
/*  396: 589 */       throw new FinancialInstitutionException(e, 6, "Cannot update data in " + this.tableName + ": " + e.getMessage());
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   public void deleteRow(HashMap data, HashMap extra, Connection connection)
/*  401:     */     throws FinancialInstitutionException
/*  402:     */   {
/*  403: 610 */     checkInitialization();
/*  404: 611 */     DebugLog.log(Level.FINE, "+++ GenericTableAdapter.deleteRow with " + data.toString());
/*  405:     */     
/*  406: 613 */     PreparedStatement pStmt = null;
/*  407: 614 */     Object[] args = new Object[this.keys.length];
/*  408: 615 */     for (int i = 0; i < this.keys.length; i++) {
/*  409: 616 */       args[i] = data.get(this.keys[i]);
/*  410:     */     }
/*  411:     */     try
/*  412:     */     {
/*  413: 619 */       String sql = (String)((HashMap)resourceMap.get(this.tableName)).get("DELETE");
/*  414: 620 */       pStmt = DBUtil.prepareStatement(connection, sql);
/*  415:     */       
/*  416: 622 */       fillParameters(pStmt, args);
/*  417:     */       
/*  418: 624 */       DBUtil.executeUpdate(pStmt, sql);
/*  419:     */     }
/*  420:     */     catch (Exception e)
/*  421:     */     {
/*  422: 628 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter delete row failed. Error: " + e.getMessage());
/*  423:     */       
/*  424: 630 */       throw new FinancialInstitutionException(e, 7, "Cannot delete data from " + this.tableName + " : " + e.getMessage());
/*  425:     */     }
/*  426:     */     finally
/*  427:     */     {
/*  428: 637 */       if (pStmt != null) {
/*  429: 639 */         DBUtil.closeStatement(pStmt);
/*  430:     */       }
/*  431:     */     }
/*  432:     */   }
/*  433:     */   
/*  434:     */   public void deleteRow(HashMap data, HashMap extra)
/*  435:     */     throws FinancialInstitutionException
/*  436:     */   {
/*  437: 657 */     Connection connection = null;
/*  438:     */     try
/*  439:     */     {
/*  440: 660 */       connection = getConnection();
/*  441: 661 */       deleteRow(data, extra, connection);
/*  442: 662 */       connection.commit();
/*  443:     */     }
/*  444:     */     catch (Exception e)
/*  445:     */     {
/*  446: 666 */       if (connection != null) {
/*  447: 667 */         DBUtil.rollback(connection);
/*  448:     */       }
/*  449: 668 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter delete row failed. Error: " + e.getMessage());
/*  450:     */       
/*  451: 670 */       throw new FinancialInstitutionException(e, 7, "Cannot delete data from " + this.tableName + " : " + e.getMessage());
/*  452:     */     }
/*  453:     */     finally
/*  454:     */     {
/*  455: 677 */       if (connection != null) {
/*  456: 678 */         releaseConnection(connection);
/*  457:     */       }
/*  458:     */     }
/*  459:     */   }
/*  460:     */   
/*  461:     */   public int getKeyValue(boolean wantMax, Connection connection)
/*  462:     */     throws FinancialInstitutionException
/*  463:     */   {
/*  464: 694 */     ResultSet results = null;
/*  465:     */     
/*  466: 696 */     int value = 0;
/*  467:     */     try
/*  468:     */     {
/*  469:     */       String stmt;
/*  470:     */       String stmt;
/*  471: 701 */       if (wantMax) {
/*  472: 702 */         stmt = (String)((HashMap)resourceMap.get(this.tableName)).get("SELECTFORMAX");
/*  473:     */       } else {
/*  474: 704 */         stmt = (String)((HashMap)resourceMap.get(this.tableName)).get("SELECTFORMIN");
/*  475:     */       }
/*  476: 706 */       DebugLog.log("+++ GenericTableAdapter.getKeyValue: " + stmt);
/*  477:     */       
/*  478: 708 */       results = DBUtil.executeQuery(DBUtil.createStatement(connection), stmt);
/*  479: 709 */       if (results.next()) {
/*  480: 710 */         value = results.getInt(1);
/*  481:     */       }
/*  482:     */     }
/*  483:     */     catch (Exception e)
/*  484:     */     {
/*  485: 715 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter.getValue failed. Error " + e.getMessage());
/*  486:     */       
/*  487: 717 */       throw new FinancialInstitutionException(e, "GenericTableAdapter: Cannot get max/min id value - " + e.getMessage());
/*  488:     */     }
/*  489:     */     finally
/*  490:     */     {
/*  491:     */       try
/*  492:     */       {
/*  493: 724 */         if (results != null) {
/*  494: 725 */           DBUtil.closeResultSet(results);
/*  495:     */         }
/*  496:     */       }
/*  497:     */       catch (Exception e)
/*  498:     */       {
/*  499: 728 */         DebugLog.log(Level.SEVERE, "GenericTableAdapter.getValue failed. Error " + e.getMessage());
/*  500:     */         
/*  501: 730 */         throw new FinancialInstitutionException(e, "GenericTableAdapter: Cannot get max/min id value");
/*  502:     */       }
/*  503:     */     }
/*  504: 734 */     return value;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public static Connection getConnection(boolean bAutoCommit, int isolationLevel)
/*  508:     */     throws FinancialInstitutionException
/*  509:     */   {
/*  510: 747 */     checkInitialization();
/*  511: 748 */     Connection conn = null;
/*  512:     */     try
/*  513:     */     {
/*  514: 750 */       conn = DBUtil.getConnection(connPoolName, bAutoCommit, isolationLevel);
/*  515:     */     }
/*  516:     */     catch (Exception e)
/*  517:     */     {
/*  518: 753 */       DebugLog.log(Level.SEVERE, "GenericTableAdapter.getConnection failed. Error " + e.getMessage());
/*  519:     */       
/*  520: 755 */       throw new FinancialInstitutionException(e, "GenericTableAdapter: Cannot get connection");
/*  521:     */     }
/*  522: 758 */     return conn;
/*  523:     */   }
/*  524:     */   
/*  525:     */   public static Connection getConnection()
/*  526:     */     throws FinancialInstitutionException
/*  527:     */   {
/*  528: 770 */     return getConnection(false, 2);
/*  529:     */   }
/*  530:     */   
/*  531:     */   public static void releaseConnection(Connection conn)
/*  532:     */   {
/*  533: 783 */     DBUtil.returnConnection(connPoolName, conn);
/*  534:     */   }
/*  535:     */   
/*  536:     */   public static void closeAllPrepareStatements()
/*  537:     */   {
/*  538:     */     try
/*  539:     */     {
/*  540: 791 */       if (pStmtMap != null)
/*  541:     */       {
/*  542: 792 */         Set set = pStmtMap.keySet();
/*  543: 793 */         Iterator i = set.iterator();
/*  544: 794 */         while (i.hasNext())
/*  545:     */         {
/*  546: 795 */           String key = (String)i.next();
/*  547: 796 */           PreparedStatement ps = (PreparedStatement)pStmtMap.get(key);
/*  548: 797 */           DBUtil.closeStatement(ps);
/*  549: 798 */           ps = null;
/*  550: 799 */           pStmtMap = new HashMap();
/*  551:     */         }
/*  552:     */       }
/*  553:     */     }
/*  554:     */     catch (Exception e)
/*  555:     */     {
/*  556: 803 */       DebugLog.log("GenericTableAdapter.closeAllPrepareStatements: Error while closing preparedStatements: " + e.getMessage());
/*  557:     */     }
/*  558:     */   }
/*  559:     */   
/*  560:     */   private String createInsertStmt(String tableName, String[] columns)
/*  561:     */     throws FinancialInstitutionException
/*  562:     */   {
/*  563:     */     try
/*  564:     */     {
/*  565: 825 */       StringBuffer buf = new StringBuffer(1000);
/*  566: 826 */       StringBuffer values = new StringBuffer(32);
/*  567: 827 */       buf.append("INSERT INTO " + tableName + " (");
/*  568:     */       
/*  569: 829 */       buf.append(columns[0]);
/*  570: 830 */       values.append(" VALUES ( ?");
/*  571: 831 */       for (int i = 1; i < columns.length; i++)
/*  572:     */       {
/*  573: 833 */         buf.append("," + columns[i]);
/*  574: 834 */         values.append(",?");
/*  575:     */       }
/*  576: 836 */       buf.append(")");
/*  577: 837 */       values.append(")");
/*  578: 838 */       buf.append(values.toString());
/*  579: 839 */       return buf.toString();
/*  580:     */     }
/*  581:     */     catch (Throwable e)
/*  582:     */     {
/*  583: 843 */       StringWriter sw = new StringWriter();
/*  584: 844 */       e.printStackTrace(new PrintWriter(sw));
/*  585: 845 */       String err = "Failed to create an insert sql statement: " + sw.toString();
/*  586: 846 */       DebugLog.log(Level.FINE, err);
/*  587: 847 */       throw new FinancialInstitutionException(e, err);
/*  588:     */     }
/*  589:     */   }
/*  590:     */   
/*  591:     */   private String createDeleteStmt(String tableName, String[] keys)
/*  592:     */     throws FinancialInstitutionException
/*  593:     */   {
/*  594:     */     try
/*  595:     */     {
/*  596: 863 */       StringBuffer buf = new StringBuffer(1000);
/*  597: 864 */       buf.append("DELETE FROM ");
/*  598: 865 */       buf.append(tableName);
/*  599: 866 */       buf.append(" ");
/*  600: 867 */       buf.append(" WHERE ");
/*  601:     */       
/*  602: 869 */       buf.append(keys[0]);
/*  603: 870 */       buf.append("= ?");
/*  604: 871 */       for (int i = 1; i < keys.length; i++)
/*  605:     */       {
/*  606: 873 */         buf.append(" AND " + keys[i]);
/*  607: 874 */         buf.append("= ?");
/*  608:     */       }
/*  609: 876 */       return buf.toString();
/*  610:     */     }
/*  611:     */     catch (Exception e)
/*  612:     */     {
/*  613: 881 */       StringWriter sw = new StringWriter();
/*  614: 882 */       e.printStackTrace(new PrintWriter(sw));
/*  615: 883 */       String err = "Failed to create a delete sql statement: " + sw.toString();
/*  616: 884 */       DebugLog.log(Level.FINE, err);
/*  617: 885 */       throw new FinancialInstitutionException(e, err);
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   private String createUpdateStmt(String tableName, String[] columns, String[] keys)
/*  622:     */     throws FinancialInstitutionException
/*  623:     */   {
/*  624:     */     try
/*  625:     */     {
/*  626: 904 */       StringBuffer buf = new StringBuffer(1000);
/*  627: 905 */       buf.append("UPDATE ");
/*  628: 906 */       buf.append(tableName);
/*  629: 907 */       buf.append(" SET ");
/*  630:     */       
/*  631: 909 */       buf.append(columns[0]);
/*  632: 910 */       buf.append("= ?");
/*  633: 911 */       for (int i = 1; i < columns.length; i++)
/*  634:     */       {
/*  635: 913 */         buf.append("," + columns[i]);
/*  636: 914 */         buf.append("= ?");
/*  637:     */       }
/*  638: 917 */       buf.append(" WHERE ");
/*  639: 918 */       buf.append(keys[0]);
/*  640: 919 */       buf.append("= ?");
/*  641: 920 */       for (int i = 1; i < keys.length; i++)
/*  642:     */       {
/*  643: 922 */         buf.append(" AND " + keys[i]);
/*  644: 923 */         buf.append("= ?");
/*  645:     */       }
/*  646: 926 */       return buf.toString();
/*  647:     */     }
/*  648:     */     catch (Throwable e)
/*  649:     */     {
/*  650: 930 */       StringWriter sw = new StringWriter();
/*  651: 931 */       e.printStackTrace(new PrintWriter(sw));
/*  652: 932 */       String err = "Failed to create an insert statement: " + sw.toString();
/*  653: 933 */       DebugLog.log(Level.FINE, err);
/*  654: 934 */       throw new FinancialInstitutionException(e, err);
/*  655:     */     }
/*  656:     */   }
/*  657:     */   
/*  658:     */   private String createSelectByKeyStmt(String tableName, String[] columns, String[] keys)
/*  659:     */     throws FinancialInstitutionException
/*  660:     */   {
/*  661:     */     try
/*  662:     */     {
/*  663: 953 */       StringBuffer buf = new StringBuffer(1000);
/*  664: 954 */       buf.append("SELECT ");
/*  665:     */       
/*  666:     */ 
/*  667: 957 */       buf.append(columns[0]);
/*  668: 958 */       for (int i = 1; i < columns.length; i++) {
/*  669: 960 */         buf.append("," + columns[i]);
/*  670:     */       }
/*  671: 963 */       buf.append(" WHERE ");
/*  672: 964 */       buf.append(keys[0]);
/*  673: 965 */       buf.append("= ?");
/*  674: 966 */       for (int i = 1; i < keys.length; i++)
/*  675:     */       {
/*  676: 968 */         buf.append(" AND " + keys[i]);
/*  677: 969 */         buf.append("= ?");
/*  678:     */       }
/*  679: 972 */       return buf.toString();
/*  680:     */     }
/*  681:     */     catch (Throwable e)
/*  682:     */     {
/*  683: 976 */       StringWriter sw = new StringWriter();
/*  684: 977 */       e.printStackTrace(new PrintWriter(sw));
/*  685: 978 */       String err = "Failed to create an insert statement: " + sw.toString();
/*  686: 979 */       DebugLog.log(Level.FINE, err);
/*  687: 980 */       throw new FinancialInstitutionException(e, err);
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   private String createSelectStmt(String tableName, String[] columns)
/*  692:     */     throws FinancialInstitutionException
/*  693:     */   {
/*  694:     */     try
/*  695:     */     {
/*  696: 997 */       StringBuffer buf = new StringBuffer(1000);
/*  697: 998 */       buf.append("SELECT ");
/*  698:     */       
/*  699:     */ 
/*  700:1001 */       buf.append(columns[0]);
/*  701:1002 */       for (int i = 1; i < columns.length; i++) {
/*  702:1004 */         buf.append("," + columns[i]);
/*  703:     */       }
/*  704:1007 */       buf.append(" FROM " + tableName + " ");
/*  705:     */       
/*  706:1009 */       return buf.toString();
/*  707:     */     }
/*  708:     */     catch (Throwable e)
/*  709:     */     {
/*  710:1014 */       StringWriter sw = new StringWriter();
/*  711:1015 */       e.printStackTrace(new PrintWriter(sw));
/*  712:1016 */       String err = "Failed to create an insert statement: " + sw.toString();
/*  713:1017 */       DebugLog.log(Level.FINE, err);
/*  714:1018 */       throw new FinancialInstitutionException(e, err);
/*  715:     */     }
/*  716:     */   }
/*  717:     */   
/*  718:     */   private String createSelectMinMaxStmt(boolean forMax, String tableName, String key)
/*  719:     */     throws FinancialInstitutionException
/*  720:     */   {
/*  721:     */     try
/*  722:     */     {
/*  723:1037 */       return "SELECT " + (forMax ? " MAX(" : "MIN(") + key + ") " + " FROM " + tableName;
/*  724:     */     }
/*  725:     */     catch (Throwable e)
/*  726:     */     {
/*  727:1043 */       StringWriter sw = new StringWriter();
/*  728:1044 */       e.printStackTrace(new PrintWriter(sw));
/*  729:1045 */       String err = "Failed to statement for getting min/max: " + sw.toString();
/*  730:1046 */       DebugLog.log(Level.FINE, err);
/*  731:1047 */       throw new FinancialInstitutionException(e, err);
/*  732:     */     }
/*  733:     */   }
/*  734:     */   
/*  735:     */   private void populateUniqueKey(HashMap fi, Connection connection)
/*  736:     */     throws FinancialInstitutionException
/*  737:     */   {
/*  738:1061 */     DebugLog.log(Level.FINE, "+++ GenericTableAdapter.populateUniqueKey");
/*  739:1062 */     if (!this.hasSequenceKey) {
/*  740:1063 */       return;
/*  741:     */     }
/*  742:1065 */     String key = (String)fi.get(this.keys[0]);
/*  743:1066 */     DebugLog.log(Level.FINE, "+++ GenericTableAdapter.populateUniqueKey  Key is " + key);
/*  744:1068 */     if ((key == null) || (key.trim().equals(""))) {
/*  745:     */       try
/*  746:     */       {
/*  747:1072 */         String id = String.valueOf(DBUtil.getNextId(connection, dbType, this.sequenceName));
/*  748:1073 */         DebugLog.log(Level.FINE, "+++ GenericTableAdapter.populateUniqueKey  Id being set to " + id);
/*  749:     */         
/*  750:1075 */         fi.put(this.keys[0], new Integer(id));
/*  751:     */       }
/*  752:     */       catch (Exception e)
/*  753:     */       {
/*  754:1079 */         DebugLog.log(Level.SEVERE, "+++ GenericTableAdapter populateUniqueKey failed. No id. Error: " + e.getMessage());
/*  755:     */         
/*  756:1081 */         throw new FinancialInstitutionException(e, 5, "Cannot get a unique id: " + e.getMessage());
/*  757:     */       }
/*  758:     */     }
/*  759:     */   }
/*  760:     */   
/*  761:     */   private HashMap getUniqueValue(String[][] keysToCheck, HashMap fi, Connection connection)
/*  762:     */     throws FinancialInstitutionException
/*  763:     */   {
/*  764:     */     try
/*  765:     */     {
/*  766:1109 */       if (keysToCheck == null) {
/*  767:1110 */         return null;
/*  768:     */       }
/*  769:1111 */       for (int i = 0; i < keysToCheck.length; i++)
/*  770:     */       {
/*  771:1113 */         HashMap h = getUniqueValue(keysToCheck[i], fi, connection);
/*  772:1114 */         if (h != null) {
/*  773:1115 */           return h;
/*  774:     */         }
/*  775:     */       }
/*  776:1117 */       return null;
/*  777:     */     }
/*  778:     */     catch (FinancialInstitutionException e)
/*  779:     */     {
/*  780:1121 */       throw e;
/*  781:     */     }
/*  782:     */     catch (Exception e)
/*  783:     */     {
/*  784:1125 */       DebugLog.log(Level.SEVERE, "+++ GenericTableAdapter getUniqueValue failed. Error: " + e.getMessage());
/*  785:     */       
/*  786:1127 */       throw new FinancialInstitutionException(e, 4, "Cannot get unique value: " + e.getMessage());
/*  787:     */     }
/*  788:     */   }
/*  789:     */   
/*  790:     */   private HashMap getUniqueValue(String[] keyToCheck, HashMap fi, Connection connection)
/*  791:     */     throws FinancialInstitutionException
/*  792:     */   {
/*  793:     */     try
/*  794:     */     {
/*  795:1155 */       HashMap primaryKeyMap = null;
/*  796:1156 */       StringBuffer buf = new StringBuffer((String)((HashMap)resourceMap.get(this.tableName)).get("SELECT"));
/*  797:1158 */       if ((keyToCheck != null) && (keyToCheck.length > 0))
/*  798:     */       {
/*  799:1161 */         String key = null;
/*  800:1162 */         String value = null;
/*  801:1163 */         buf.append(" WHERE ");
/*  802:1164 */         key = keyToCheck[0];
/*  803:1165 */         value = replaceSingleQuotes((String)fi.get(key));
/*  804:1166 */         if (value != null) {
/*  805:1166 */           value = value.trim();
/*  806:     */         }
/*  807:1167 */         buf.append(key + " = " + "'" + value + "'");
/*  808:1171 */         for (int i = 1; i < keyToCheck.length; i++)
/*  809:     */         {
/*  810:1173 */           key = keyToCheck[i];
/*  811:1174 */           value = replaceSingleQuotes((String)fi.get(key));
/*  812:1175 */           if (value != null) {
/*  813:1175 */             value = value.trim();
/*  814:     */           }
/*  815:1176 */           buf.append(" AND " + key + " = " + "'" + value + "'");
/*  816:     */         }
/*  817:     */       }
/*  818:1184 */       ResultSet results = null;
/*  819:1185 */       String selectStatement = buf.toString();
/*  820:1186 */       DebugLog.log(Level.FINE, "+++ GenericTableAdapter.getUniqueValue " + selectStatement);
/*  821:     */       
/*  822:1188 */       PreparedStatement ps = null;
/*  823:     */       try
/*  824:     */       {
/*  825:1192 */         ps = DBUtil.prepareStatement(connection, selectStatement);
/*  826:1193 */         results = DBUtil.executeQuery(ps, selectStatement);
/*  827:1194 */         while (results.next())
/*  828:     */         {
/*  829:1196 */           if (primaryKeyMap != null) {
/*  830:1197 */             return null;
/*  831:     */           }
/*  832:1198 */           primaryKeyMap = new HashMap(2);
/*  833:1199 */           for (int i = 0; i < this.keys.length; i++) {
/*  834:1201 */             primaryKeyMap.put(this.keys[i], results.getString(this.keys[i]));
/*  835:     */           }
/*  836:     */         }
/*  837:     */       }
/*  838:     */       catch (Exception e)
/*  839:     */       {
/*  840:1208 */         DebugLog.log(Level.SEVERE, "+++ GenericTableAdapter getUniqueValue failed.  Error: " + e.getMessage());
/*  841:     */         
/*  842:1210 */         throw new FinancialInstitutionException(e, 4, "Cannot get unique value: " + e.getMessage());
/*  843:     */       }
/*  844:     */       finally
/*  845:     */       {
/*  846:     */         try
/*  847:     */         {
/*  848:1218 */           if (results != null)
/*  849:     */           {
/*  850:1220 */             DBUtil.closeResultSet(results);
/*  851:1221 */             results = null;
/*  852:     */           }
/*  853:1223 */           if (ps != null)
/*  854:     */           {
/*  855:1225 */             DBUtil.closeStatement(ps);
/*  856:1226 */             ps = null;
/*  857:     */           }
/*  858:     */         }
/*  859:     */         catch (Exception e)
/*  860:     */         {
/*  861:1231 */           DebugLog.log(Level.SEVERE, "GenericTableAdapter getUniqueValue failed.  Error: " + e.getMessage());
/*  862:     */           
/*  863:1233 */           throw new FinancialInstitutionException(e, 4, "Cannot get unique value: " + e.getMessage());
/*  864:     */         }
/*  865:     */       }
/*  866:1238 */       return primaryKeyMap;
/*  867:     */     }
/*  868:     */     catch (Exception e)
/*  869:     */     {
/*  870:1243 */       DebugLog.log(Level.SEVERE, "+++ GenericTableAdapter getUniqueValue failed.  Error: " + e.getMessage());
/*  871:     */       
/*  872:1245 */       throw new FinancialInstitutionException(e, 4, "Cannot get unique value: " + e.getMessage());
/*  873:     */     }
/*  874:     */   }
/*  875:     */   
/*  876:     */   private static void checkInitialization()
/*  877:     */     throws FinancialInstitutionException
/*  878:     */   {
/*  879:1261 */     if (!isInitialized) {
/*  880:1263 */       throw new FinancialInstitutionException(2, "GenericTableAdapter not initialized");
/*  881:     */     }
/*  882:     */   }
/*  883:     */   
/*  884:     */   private static String replaceSingleQuotes(String str)
/*  885:     */   {
/*  886:1279 */     if (str.indexOf('\'') >= 0)
/*  887:     */     {
/*  888:1280 */       StringBuffer s = new StringBuffer(str);
/*  889:1281 */       int i = 0;
/*  890:1282 */       while (i < s.length())
/*  891:     */       {
/*  892:1283 */         if (s.charAt(i) == '\'')
/*  893:     */         {
/*  894:1284 */           s = s.insert(i + 1, "'");
/*  895:1285 */           i++;
/*  896:     */         }
/*  897:1287 */         i++;
/*  898:     */       }
/*  899:1289 */       str = s.toString();
/*  900:     */     }
/*  901:1291 */     return str;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public static void fillParameters(PreparedStatement stmt, Object[] inArgs)
/*  905:     */     throws SQLException
/*  906:     */   {
/*  907:1306 */     if (inArgs != null) {
/*  908:1309 */       for (int i = 0; i < inArgs.length; i++)
/*  909:     */       {
/*  910:1310 */         Object obj = inArgs[i];
/*  911:1312 */         if ((obj == null) || ((obj instanceof String))) {
/*  912:1313 */           stmt.setString(i + 1, (String)obj);
/*  913:1315 */         } else if ((obj instanceof Integer)) {
/*  914:1316 */           stmt.setInt(i + 1, ((Integer)obj).intValue());
/*  915:1318 */         } else if ((obj instanceof Long)) {
/*  916:1319 */           stmt.setLong(i + 1, ((Long)obj).longValue());
/*  917:1323 */         } else if ((obj instanceof Timestamp)) {
/*  918:1324 */           stmt.setTimestamp(i + 1, (Timestamp)obj);
/*  919:1326 */         } else if ((obj instanceof Date)) {
/*  920:1327 */           stmt.setDate(i + 1, (Date)obj);
/*  921:1328 */         } else if ((obj instanceof Time)) {
/*  922:1329 */           stmt.setTime(i + 1, (Time)obj);
/*  923:     */         } else {
/*  924:1331 */           throw new SQLException("Error: unsupported datatype");
/*  925:     */         }
/*  926:     */       }
/*  927:     */     }
/*  928:     */   }
/*  929:     */   
/*  930:     */   public int getNextId(Connection con, String field)
/*  931:     */     throws Exception
/*  932:     */   {
/*  933:1344 */     return DBUtil.getNextId(con, dbType, field);
/*  934:     */   }
/*  935:     */   
/*  936:     */   public String getDbType()
/*  937:     */   {
/*  938:1348 */     return dbType;
/*  939:     */   }
/*  940:     */   
/*  941:     */   private Properties getPropertiesFromHashMap(HashMap map)
/*  942:     */   {
/*  943:1358 */     Properties returnProps = null;
/*  944:1359 */     if (map != null)
/*  945:     */     {
/*  946:1360 */       returnProps = new Properties();
/*  947:1361 */       Set keySet = map.keySet();
/*  948:1362 */       Iterator keyIterator = keySet.iterator();
/*  949:1363 */       while (keyIterator.hasNext())
/*  950:     */       {
/*  951:1365 */         String key = keyIterator.next().toString();
/*  952:1366 */         String value = map.get(key).toString();
/*  953:1367 */         returnProps.setProperty(key, value);
/*  954:     */       }
/*  955:     */     }
/*  956:1370 */     return returnProps;
/*  957:     */   }
/*  958:     */ }


/* Location:           D:\drops\jd\jars\ffiblcommon.jar
 * Qualified Name:     com.ffusion.banklookup.adapters.GenericTableAdapter
 * JD-Core Version:    0.7.0.1
 */