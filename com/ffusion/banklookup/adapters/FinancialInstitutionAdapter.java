/*    1:     */ package com.ffusion.banklookup.adapters;
/*    2:     */ 
/*    3:     */ import com.ffusion.banklookup.FinancialInstitutionException;
/*    4:     */ import com.ffusion.banklookup.beans.FinancialInstitution;
/*    5:     */ import com.ffusion.banklookup.beans.FinancialInstitutions;
/*    6:     */ import com.ffusion.util.SimpleCache;
/*    7:     */ import com.ffusion.util.db.DBUtil;
/*    8:     */ import com.ffusion.util.logging.DebugLog;
/*    9:     */ import java.sql.Connection;
/*   10:     */ import java.sql.PreparedStatement;
/*   11:     */ import java.sql.ResultSet;
/*   12:     */ import java.sql.Statement;
/*   13:     */ import java.util.ArrayList;
/*   14:     */ import java.util.HashMap;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.Set;
/*   17:     */ import java.util.logging.Level;
/*   18:     */ 
/*   19:     */ public class FinancialInstitutionAdapter
/*   20:     */   implements FinancialInstitutionAdapterConsts, GenericTableAdapterConsts
/*   21:     */ {
/*   22:     */   private static final String ORDERED = " ORDER BY fi.InstitutionName,fi.InstitutionId ";
/*   23:     */   private static final String GET_ALL_ROWS = "SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId";
/*   24:     */   private static final String GET_ALL_ROWS_BY_ID = "SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.InstitutionId=? ORDER BY fi.InstitutionName,fi.InstitutionId ";
/*   25:     */   private static final String RTN_PREDICATE = " EXISTS (SELECT * FROM LU_FinancialInstRtn WHERE RoutingNumber=?  AND InstitutionId=fi.InstitutionId) ";
/*   26:     */   private static final String ACHRTN_PREDICATE = " EXISTS (SELECT * FROM LU_FinancialInstRtn WHERE AchRoutingNumber=?  AND InstitutionId=fi.InstitutionId) ";
/*   27:     */   private static final String RTN_LIKE_PREDICATE = " EXISTS (SELECT * FROM LU_FinancialInstRtn WHERE InstitutionId=fi.InstitutionId AND AchRoutingNumber LIKE ";
/*   28:     */   private static final String GET_ALL_ROWS_BY_WIRERTN = "SELECT  InstitutionId,InstitutionName,BranchName,AddressLine1,AddressLine2,AddressLine3,City,State,StateCode,PostalCode,Country,Phone,AchRoutingNumber,WireRoutingNumber,SwiftBIC,ChipsUID,NationalID, ''  FROM LU_FinancialInstitution WHERE WireRoutingNumber=?";
/*   29:     */   private static final String GET_ALL_ROWS_BY_ACHRTN = "SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND  EXISTS (SELECT * FROM LU_FinancialInstRtn WHERE AchRoutingNumber=?  AND InstitutionId=fi.InstitutionId)  ORDER BY fi.InstitutionName,fi.InstitutionId ";
/*   30:     */   private static final String GET_ALL_ROWS_BY_SWIFTBIC = "SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.SwiftBIC=? ORDER BY fi.InstitutionName,fi.InstitutionId ";
/*   31:     */   private static final String GET_ALL_ROWS_BY_CHIPSUID = "SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.ChipsUID=? ORDER BY fi.InstitutionName,fi.InstitutionId ";
/*   32:     */   private static final String INSERT_ROW = "INSERT INTO LU_FinancialInstitution ( InstitutionId,InstitutionName,BranchName,AddressLine1,AddressLine2,AddressLine3,City,State,StateCode,PostalCode,Country,Phone,AchRoutingNumber,WireRoutingNumber,SwiftBIC,ChipsUID,NationalID) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*   33:     */   private static final String INSERT_RTN_ROW = "INSERT INTO LU_FinancialInstRtn (InstitutionId,AchRoutingNumber) VALUES ( ?, ?)";
/*   34:     */   private static final String UPDATE_ROW = "UPDATE LU_FinancialInstitution SET InstitutionName = ?, BranchName = ?, AddressLine1 = ?, AddressLine2 = ?, AddressLine3 = ?, City = ?, State = ?, StateCode = ?, PostalCode = ?, Country = ?, Phone = ?, AchRoutingNumber = ?, WireRoutingNumber = ?, SwiftBIC = ?, ChipsUID = ?, NationalID = ?  WHERE InstitutionId = ? ";
/*   35:     */   private static final String DELETE_ROW = "DELETE FROM LU_FinancialInstitution WHERE InstitutionId =? ";
/*   36:     */   private static final String DELETE_RTN_ROWS_BY_ID = "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId = ? ";
/*   37:     */   private static final String DELETE_FI_ROWS = "DELETE FROM LU_FinancialInstitution WHERE InstitutionId>=? AND InstitutionId<=?";
/*   38:     */   private static final String DELETE_FIRTN_ROWS = "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId>=? AND InstitutionId<=?";
/*   39:     */   private static final String SELECT_COUNTRIES = "SELECT  DISTINCT Country FROM LU_FinancialInstitution ORDER BY 1";
/*   40:     */   private static final String SET_SWIFT_NULL = "UPDATE LU_FinancialInstitution SET SwiftBIC = NULL  WHERE SwiftBIC=?";
/*   41:     */   private static final String SET_WIRE_NULL = "UPDATE LU_FinancialInstitution SET WireRoutingNumber='000000000' WHERE WireRoutingNumber=?";
/*   42:     */   private static final String SET_ACH_NULL = "UPDATE LU_FinancialInstitution SET AchRoutingNumber='000000000' WHERE AchRoutingNumber=?";
/*   43:     */   private static final String DELETE_RTN_ROWS_WITH_SUBSELECT = "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN ( SELECT InstitutionId FROM LU_FinancialInstRtn WHERE AchRoutingNumber IN ";
/*   44:     */   private static final String SET_ACH_BULK_NULL = "UPDATE LU_FinancialInstitution SET AchRoutingNumber='000000000' WHERE AchRoutingNumber IN ";
/*   45:     */   private static final String SET_WIRE_BULK_NULL = "UPDATE LU_FinancialInstitution SET WireRoutingNumber='000000000' WHERE WireRoutingNumber IN ";
/*   46:     */   private static final String SET_SWIFT_BULK_NULL = "UPDATE LU_FinancialInstitution SET SwiftBIC = NULL  WHERE SwiftBIC IN ";
/*   47:     */   private static final String PURGE_NULLED_ACH_RTN_ROWS = "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE AchRoutingNumber = '000000000' AND  (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL )";
/*   48:     */   private static final String PURGE_NULLED_WIRE_RTN_ROWS = "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE WireRoutingNumber = '000000000' AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL )";
/*   49:     */   private static final String PURGE_NULLED_SWIFT_RTN_ROWS = "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE SwiftBIC IS NULL  AND (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND ChipsUID IS NULL  AND NationalID IS NULL )";
/*   50:     */   private static final String PURGE_NULLED_ACH_ROWS = "DELETE FROM LU_FinancialInstitution WHERE AchRoutingNumber = '000000000' AND  (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL ";
/*   51:     */   private static final String PURGE_NULLED_WIRE_ROWS = "DELETE FROM LU_FinancialInstitution WHERE WireRoutingNumber = '000000000' AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL ";
/*   52:     */   private static final String PURGE_NULLED_ROWS = "DELETE FROM LU_FinancialInstitution WHERE AchRoutingNumber IS NULL  AND WireRoutingNumber IS NULL  AND SwiftBIC IS NULL  AND ChipsUID IS NULL  AND NationalID IS NULL ";
/*   53:     */   private static final String ACHROUTINGNUMBER_CACHE = "ACHRTN";
/*   54:     */   private static final String WIREROUTINGNUMBER_CACHE = "WIRERTN";
/*   55:     */   private static final String SWIFT_BIC_CACHE = "BIC";
/*   56: 226 */   private static ArrayList countries = null;
/*   57: 227 */   private static GenericTableAdapter gta = new GenericTableAdapter();
/*   58: 228 */   private static boolean isInitialized = false;
/*   59: 229 */   private static HashMap caches = new HashMap(3);
/*   60: 230 */   private static HashMap pStmtCache = new HashMap();
/*   61: 231 */   private static String INSERT_RTN_ROW_STMT = "INSERT_RTN_ROW_STMT";
/*   62: 232 */   private static String INSERT_ROW_STMT = "INSERT_ROW_STMT";
/*   63:     */   
/*   64:     */   public static void initialize(HashMap props)
/*   65:     */     throws FinancialInstitutionException
/*   66:     */   {
/*   67:     */     try
/*   68:     */     {
/*   69: 248 */       if (!isInitialized)
/*   70:     */       {
/*   71: 251 */         gta.initialize(props, "LU_FinancialInstitution", FinancialInstitutionAdapterConsts.FI_COLUMNS, FinancialInstitutionAdapterConsts.FI_KEYS, true, "FinancialInstitution_ID", FinancialInstitutionAdapterConsts.FI_KEYS_TO_CHECK);
/*   72:     */         
/*   73:     */ 
/*   74: 254 */         SimpleCache achRoutingNumberCache = new SimpleCache();
/*   75: 255 */         achRoutingNumberCache.setCacheSize(20);
/*   76:     */         
/*   77: 257 */         SimpleCache wireRoutingNumberCache = new SimpleCache();
/*   78: 258 */         wireRoutingNumberCache.setCacheSize(20);
/*   79:     */         
/*   80: 260 */         SimpleCache bicCodeCache = new SimpleCache();
/*   81: 261 */         bicCodeCache.setCacheSize(20);
/*   82:     */         
/*   83: 263 */         caches.put("ACHRTN", achRoutingNumberCache);
/*   84: 264 */         caches.put("WIRERTN", wireRoutingNumberCache);
/*   85: 265 */         caches.put("BIC", bicCodeCache);
/*   86: 266 */         isInitialized = true;
/*   87:     */       }
/*   88:     */     }
/*   89:     */     catch (Exception e)
/*   90:     */     {
/*   91: 270 */       throw new FinancialInstitutionException(e, 1, "Could not initialize: " + e.getMessage());
/*   92:     */     }
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static FinancialInstitutions get(FinancialInstitution fi, boolean isConjunctive)
/*   96:     */     throws FinancialInstitutionException
/*   97:     */   {
/*   98: 296 */     return get(fi, isConjunctive, 2147483647);
/*   99:     */   }
/*  100:     */   
/*  101:     */   public static FinancialInstitutions get(FinancialInstitution fi, boolean isConjunctive, int maxBeans)
/*  102:     */     throws FinancialInstitutionException
/*  103:     */   {
/*  104: 322 */     Connection connection = null;
/*  105:     */     try
/*  106:     */     {
/*  107: 326 */       connection = GenericTableAdapter.getConnection();
/*  108: 327 */       FinancialInstitutions results = get(fi, isConjunctive, maxBeans, connection);
/*  109: 328 */       connection.commit();
/*  110: 329 */       return results;
/*  111:     */     }
/*  112:     */     catch (Exception e)
/*  113:     */     {
/*  114: 334 */       throw new FinancialInstitutionException(e, 4, "Could not get results in getByID");
/*  115:     */     }
/*  116:     */     finally
/*  117:     */     {
/*  118: 340 */       GenericTableAdapter.releaseConnection(connection);
/*  119:     */     }
/*  120:     */   }
/*  121:     */   
/*  122:     */   public static FinancialInstitution getByID(int id)
/*  123:     */     throws FinancialInstitutionException
/*  124:     */   {
/*  125: 358 */     Connection connection = null;
/*  126: 359 */     FinancialInstitution result = null;
/*  127:     */     try
/*  128:     */     {
/*  129: 362 */       connection = GenericTableAdapter.getConnection();
/*  130: 363 */       result = getByID(id, connection);
/*  131: 364 */       connection.commit();
/*  132: 365 */       return result;
/*  133:     */     }
/*  134:     */     catch (Exception e)
/*  135:     */     {
/*  136: 370 */       throw new FinancialInstitutionException(e, 4, "Could not get results in getByID");
/*  137:     */     }
/*  138:     */     finally
/*  139:     */     {
/*  140: 376 */       GenericTableAdapter.releaseConnection(connection);
/*  141:     */     }
/*  142:     */   }
/*  143:     */   
/*  144:     */   public static FinancialInstitutions getByAchRoutingNumber(String rtn)
/*  145:     */     throws FinancialInstitutionException
/*  146:     */   {
/*  147: 394 */     Connection connection = null;
/*  148: 395 */     FinancialInstitutions results = null;
/*  149:     */     try
/*  150:     */     {
/*  151: 398 */       connection = GenericTableAdapter.getConnection();
/*  152: 399 */       Object[] args = { rtn, rtn };
/*  153: 400 */       results = getByAchRoutingNumber(rtn, connection);
/*  154: 401 */       connection.commit();
/*  155: 402 */       return results;
/*  156:     */     }
/*  157:     */     catch (Exception e)
/*  158:     */     {
/*  159: 407 */       throw new FinancialInstitutionException(e, 4, "Could not get results in getByAchRoutingNumber");
/*  160:     */     }
/*  161:     */     finally
/*  162:     */     {
/*  163: 413 */       GenericTableAdapter.releaseConnection(connection);
/*  164:     */     }
/*  165:     */   }
/*  166:     */   
/*  167:     */   public static FinancialInstitutions getByWireRoutingNumber(String rtn)
/*  168:     */     throws FinancialInstitutionException
/*  169:     */   {
/*  170: 430 */     Connection connection = null;
/*  171: 431 */     FinancialInstitutions results = null;
/*  172:     */     try
/*  173:     */     {
/*  174: 434 */       connection = GenericTableAdapter.getConnection();
/*  175: 435 */       Object[] args = { rtn, rtn };
/*  176: 436 */       results = getByWireRoutingNumber(rtn, connection);
/*  177: 437 */       connection.commit();
/*  178: 438 */       return results;
/*  179:     */     }
/*  180:     */     catch (Exception e)
/*  181:     */     {
/*  182: 443 */       throw new FinancialInstitutionException(e, 4, "Could not get results in getByAcWireRoutingNumber");
/*  183:     */     }
/*  184:     */     finally
/*  185:     */     {
/*  186: 449 */       GenericTableAdapter.releaseConnection(connection);
/*  187:     */     }
/*  188:     */   }
/*  189:     */   
/*  190:     */   public static FinancialInstitutions getByBicCode(String bic)
/*  191:     */     throws FinancialInstitutionException
/*  192:     */   {
/*  193: 466 */     Connection connection = null;
/*  194: 467 */     FinancialInstitutions results = null;
/*  195:     */     try
/*  196:     */     {
/*  197: 470 */       connection = GenericTableAdapter.getConnection();
/*  198: 471 */       Object[] args = { bic, bic };
/*  199: 472 */       results = getByBicCode(bic, connection);
/*  200: 473 */       connection.commit();
/*  201: 474 */       return results;
/*  202:     */     }
/*  203:     */     catch (Exception e)
/*  204:     */     {
/*  205: 479 */       throw new FinancialInstitutionException(e, 4, "Could not get results in getByBicCode");
/*  206:     */     }
/*  207:     */     finally
/*  208:     */     {
/*  209: 485 */       GenericTableAdapter.releaseConnection(connection);
/*  210:     */     }
/*  211:     */   }
/*  212:     */   
/*  213:     */   public static FinancialInstitutions getByChipsUID(String chipsUID)
/*  214:     */     throws FinancialInstitutionException
/*  215:     */   {
/*  216: 502 */     return get("SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.ChipsUID=? ORDER BY fi.InstitutionName,fi.InstitutionId ", chipsUID, 2147483647);
/*  217:     */   }
/*  218:     */   
/*  219:     */   public static FinancialInstitutions getByBankName(String name)
/*  220:     */     throws FinancialInstitutionException
/*  221:     */   {
/*  222: 517 */     if (name != null) {
/*  223: 518 */       name = name.trim().toUpperCase();
/*  224:     */     }
/*  225: 519 */     HashMap searchCriteria = new HashMap(1);
/*  226: 520 */     searchCriteria.put("fi.InstitutionName", name);
/*  227: 521 */     return get(searchCriteria, false, 2147483647);
/*  228:     */   }
/*  229:     */   
/*  230:     */   public static FinancialInstitutions getByBankName(String name, int maxBeans)
/*  231:     */     throws FinancialInstitutionException
/*  232:     */   {
/*  233: 537 */     if (name != null) {
/*  234: 538 */       name = name.trim().toUpperCase();
/*  235:     */     }
/*  236: 539 */     HashMap searchCriteria = new HashMap(1);
/*  237: 540 */     searchCriteria.put("fi.InstitutionName", name);
/*  238: 541 */     return get(searchCriteria, false, maxBeans);
/*  239:     */   }
/*  240:     */   
/*  241:     */   public static void insert(FinancialInstitution fi)
/*  242:     */     throws FinancialInstitutionException
/*  243:     */   {
/*  244: 555 */     Connection connection = null;
/*  245:     */     try
/*  246:     */     {
/*  247: 558 */       connection = GenericTableAdapter.getConnection();
/*  248: 559 */       insert(fi, connection);
/*  249: 560 */       connection.commit();
/*  250:     */     }
/*  251:     */     catch (Exception e)
/*  252:     */     {
/*  253: 565 */       throw new FinancialInstitutionException(e, 4, "Could not get results");
/*  254:     */     }
/*  255:     */     finally
/*  256:     */     {
/*  257: 571 */       GenericTableAdapter.releaseConnection(connection);
/*  258:     */     }
/*  259:     */   }
/*  260:     */   
/*  261:     */   public static void update(FinancialInstitution fi)
/*  262:     */     throws FinancialInstitutionException
/*  263:     */   {
/*  264: 588 */     if (fi == null) {
/*  265: 590 */       throw new FinancialInstitutionException(6, "Cannot update data since object is null");
/*  266:     */     }
/*  267: 594 */     int id = fi.getInstitutionId();
/*  268: 595 */     Connection connection = null;
/*  269:     */     try
/*  270:     */     {
/*  271: 598 */       connection = GenericTableAdapter.getConnection();
/*  272: 599 */       update(fi, connection);
/*  273: 600 */       connection.commit();
/*  274:     */     }
/*  275:     */     catch (Exception e)
/*  276:     */     {
/*  277: 605 */       throw new FinancialInstitutionException(e, 6, "Cannot update data because of error: " + e.getMessage());
/*  278:     */     }
/*  279:     */     finally
/*  280:     */     {
/*  281: 612 */       GenericTableAdapter.releaseConnection(connection);
/*  282:     */     }
/*  283:     */   }
/*  284:     */   
/*  285:     */   public static void delete(FinancialInstitution fi)
/*  286:     */     throws FinancialInstitutionException
/*  287:     */   {
/*  288: 631 */     int id = fi.getInstitutionId();
/*  289: 632 */     Connection connection = null;
/*  290:     */     try
/*  291:     */     {
/*  292: 635 */       connection = GenericTableAdapter.getConnection();
/*  293: 636 */       deleteByID(id, connection);
/*  294: 637 */       connection.commit();
/*  295:     */     }
/*  296:     */     catch (Exception e)
/*  297:     */     {
/*  298: 642 */       throw new FinancialInstitutionException(e, 7, "Cannot delete data");
/*  299:     */     }
/*  300:     */     finally
/*  301:     */     {
/*  302: 648 */       GenericTableAdapter.releaseConnection(connection);
/*  303:     */     }
/*  304:     */   }
/*  305:     */   
/*  306:     */   public static void delete(FinancialInstitution fi, Connection connection)
/*  307:     */     throws FinancialInstitutionException
/*  308:     */   {
/*  309: 666 */     int id = fi.getInstitutionId();
/*  310:     */     try
/*  311:     */     {
/*  312: 669 */       deleteByID(id, connection);
/*  313:     */     }
/*  314:     */     catch (Exception e)
/*  315:     */     {
/*  316: 673 */       DebugLog.log("FinancialInstitutionAdapter: Could not delete row");
/*  317:     */     }
/*  318:     */   }
/*  319:     */   
/*  320:     */   public static void deleteByID(int id)
/*  321:     */     throws FinancialInstitutionException
/*  322:     */   {
/*  323: 688 */     Connection connection = null;
/*  324:     */     try
/*  325:     */     {
/*  326: 691 */       connection = GenericTableAdapter.getConnection();
/*  327: 692 */       deleteByID(id, connection);
/*  328: 693 */       connection.commit();
/*  329:     */     }
/*  330:     */     catch (Exception e)
/*  331:     */     {
/*  332: 698 */       throw new FinancialInstitutionException(e, 7, "Cannot delete data");
/*  333:     */     }
/*  334:     */     finally
/*  335:     */     {
/*  336: 704 */       GenericTableAdapter.releaseConnection(connection);
/*  337:     */     }
/*  338:     */   }
/*  339:     */   
/*  340:     */   public static FinancialInstitutions get(FinancialInstitution fi, boolean isConjunctive, Connection connection)
/*  341:     */     throws FinancialInstitutionException
/*  342:     */   {
/*  343: 736 */     return get(fi, isConjunctive, 2147483647, connection);
/*  344:     */   }
/*  345:     */   
/*  346:     */   public static FinancialInstitutions get(FinancialInstitution fi, boolean isConjunctive, int maxBeans, Connection connection)
/*  347:     */     throws FinancialInstitutionException
/*  348:     */   {
/*  349: 761 */     HashMap searchCriteria = new HashMap();
/*  350: 762 */     String val = null;
/*  351:     */     
/*  352: 764 */     String notNullColumn = (String)fi.get("NON_NULL_ROUTING_NUMBER");
/*  353: 765 */     if (notNullColumn == null) {
/*  354: 765 */       notNullColumn = "";
/*  355:     */     }
/*  356: 767 */     int id = fi.getInstitutionId();
/*  357: 768 */     if (id != 0) {
/*  358: 769 */       searchCriteria.put("fi.InstitutionId", Integer.toString(id));
/*  359:     */     }
/*  360: 772 */     val = fi.getInstitutionName();
/*  361: 773 */     if ((val != null) && (val.trim().length() > 0)) {
/*  362: 774 */       searchCriteria.put("fi.InstitutionName", val.trim());
/*  363:     */     }
/*  364: 776 */     val = fi.getBranchName();
/*  365: 778 */     if ((val != null) && (val.trim().length() > 0)) {
/*  366: 779 */       searchCriteria.put("fi.BranchName", val.trim());
/*  367:     */     }
/*  368: 781 */     val = fi.getStreet();
/*  369: 782 */     if ((val != null) && (val.trim().length() > 0)) {
/*  370: 783 */       searchCriteria.put("fi.AddressLine1", val.trim());
/*  371:     */     }
/*  372: 785 */     val = fi.getStreet2();
/*  373: 786 */     if ((val != null) && (val.trim().length() > 0)) {
/*  374: 787 */       searchCriteria.put("fi.AddressLine2", val.trim());
/*  375:     */     }
/*  376: 789 */     val = fi.getStreet3();
/*  377: 790 */     if ((val != null) && (val.trim().length() > 0)) {
/*  378: 791 */       searchCriteria.put("fi.AddressLine3", val.trim());
/*  379:     */     }
/*  380: 793 */     val = fi.getCity();
/*  381: 794 */     if ((val != null) && (val.trim().length() > 0)) {
/*  382: 795 */       searchCriteria.put("fi.City", val.trim());
/*  383:     */     }
/*  384: 797 */     val = fi.getState();
/*  385: 798 */     if ((val != null) && (val.trim().length() > 0)) {
/*  386: 799 */       searchCriteria.put("fi.State", val.trim());
/*  387:     */     }
/*  388: 801 */     val = fi.getStateCode();
/*  389: 802 */     if ((val != null) && (val.trim().length() > 0)) {
/*  390: 803 */       searchCriteria.put("fi.StateCode", val.trim());
/*  391:     */     }
/*  392: 805 */     val = fi.getZipCode();
/*  393: 806 */     if ((val != null) && (val.trim().length() > 0)) {
/*  394: 807 */       searchCriteria.put("fi.PostalCode", val.trim());
/*  395:     */     }
/*  396: 809 */     val = fi.getCountry();
/*  397: 810 */     if ((val != null) && (val.trim().length() > 0)) {
/*  398: 811 */       searchCriteria.put("fi.Country", val.trim());
/*  399:     */     }
/*  400: 813 */     val = fi.getPhone();
/*  401: 814 */     if ((val != null) && (val.trim().length() > 0)) {
/*  402: 815 */       searchCriteria.put("fi.Phone", val.trim());
/*  403:     */     }
/*  404: 817 */     val = fi.getAchRoutingNumber();
/*  405: 818 */     if ((val != null) && (val.trim().length() > 0) && (!val.equals("000000000"))) {
/*  406: 819 */       searchCriteria.put("fi.AchRoutingNumber", val);
/*  407: 820 */     } else if (notNullColumn.indexOf("fi.AchRoutingNumber") != -1) {
/*  408: 821 */       searchCriteria.put("fi.AchRoutingNumber", "IS_NOT_NULL");
/*  409:     */     }
/*  410: 823 */     val = fi.getWireRoutingNumber();
/*  411: 824 */     if ((val != null) && (val.trim().length() > 0) && (!val.equals("000000000"))) {
/*  412: 825 */       searchCriteria.put("fi.WireRoutingNumber", val);
/*  413: 826 */     } else if (notNullColumn.indexOf("fi.WireRoutingNumber") != -1) {
/*  414: 827 */       searchCriteria.put("fi.WireRoutingNumber", "IS_NOT_NULL");
/*  415:     */     }
/*  416: 829 */     val = fi.getSwiftBIC();
/*  417: 830 */     if ((val != null) && (val.trim().length() > 0)) {
/*  418: 831 */       searchCriteria.put("fi.SwiftBIC", val.trim());
/*  419: 832 */     } else if (notNullColumn.indexOf("fi.SwiftBIC") != -1) {
/*  420: 833 */       searchCriteria.put("fi.SwiftBIC", "IS_NOT_NULL");
/*  421:     */     }
/*  422: 835 */     val = fi.getChipsUID();
/*  423: 836 */     if ((val != null) && (val.trim().length() > 0)) {
/*  424: 837 */       searchCriteria.put("fi.ChipsUID", val.trim());
/*  425: 838 */     } else if (notNullColumn.indexOf("fi.ChipsUID") != -1) {
/*  426: 839 */       searchCriteria.put("fi.ChipsUID", "IS_NOT_NULL");
/*  427:     */     }
/*  428: 841 */     val = fi.getNationalID();
/*  429: 842 */     if ((val != null) && (val.trim().length() > 0)) {
/*  430: 843 */       searchCriteria.put("fi.NationalID", val.trim());
/*  431: 844 */     } else if (notNullColumn.indexOf("fi.NationalID") != -1) {
/*  432: 845 */       searchCriteria.put("fi.NationalID", "IS_NOT_NULL");
/*  433:     */     }
/*  434: 847 */     return get(searchCriteria, isConjunctive, maxBeans, connection);
/*  435:     */   }
/*  436:     */   
/*  437:     */   public static void insert(FinancialInstitution fi, Connection connection)
/*  438:     */     throws FinancialInstitutionException
/*  439:     */   {
/*  440: 869 */     if (fi.getInstitutionId() <= 0) {
/*  441:     */       try
/*  442:     */       {
/*  443: 873 */         int id = DBUtil.getNextId(connection, gta.getDbType(), "FinancialInstitution_ID");
/*  444: 874 */         fi.setInstitutionId(id);
/*  445:     */       }
/*  446:     */       catch (Exception e)
/*  447:     */       {
/*  448: 878 */         DebugLog.log(Level.SEVERE, "+++ FinancialInstitutionAdapter.insert failed. No id. Error: " + e.getMessage());
/*  449:     */         
/*  450: 880 */         throw new FinancialInstitutionException(e, 5, "Cannot get a unique id");
/*  451:     */       }
/*  452:     */     }
/*  453: 887 */     Integer institutionId = new Integer(fi.getInstitutionId());
/*  454: 889 */     if ((fi.getCountry() == null) || (fi.getCountry().equals(""))) {
/*  455: 890 */       fi.setCountry("UNKNOWN");
/*  456:     */     }
/*  457: 891 */     Object[] args = { institutionId, fi.getInstitutionName(), fi.getBranchName(), fi.getStreet(), fi.getStreet2(), fi.getStreet3(), fi.getCity(), fi.getState(), fi.getStateCode(), fi.getZipCode(), fi.getCountry(), fi.getPhone(), fi.getAchRoutingNumber(), fi.getWireRoutingNumber(), fi.getSwiftBIC(), fi.getChipsUID(), fi.getNationalID() };
/*  458:     */     
/*  459:     */ 
/*  460:     */ 
/*  461:     */ 
/*  462:     */ 
/*  463:     */ 
/*  464:     */ 
/*  465:     */ 
/*  466:     */ 
/*  467:     */ 
/*  468:     */ 
/*  469:     */ 
/*  470:     */ 
/*  471:     */ 
/*  472:     */ 
/*  473:     */ 
/*  474:     */ 
/*  475:     */ 
/*  476:     */ 
/*  477:     */ 
/*  478: 912 */     PreparedStatement pStmt = null;
/*  479: 913 */     PreparedStatement pStmt1 = null;
/*  480:     */     try
/*  481:     */     {
/*  482: 916 */       pStmt = (PreparedStatement)pStmtCache.get(INSERT_ROW_STMT);
/*  483: 917 */       if (pStmt == null)
/*  484:     */       {
/*  485: 918 */         pStmt = DBUtil.prepareStatement(connection, "INSERT INTO LU_FinancialInstitution ( InstitutionId,InstitutionName,BranchName,AddressLine1,AddressLine2,AddressLine3,City,State,StateCode,PostalCode,Country,Phone,AchRoutingNumber,WireRoutingNumber,SwiftBIC,ChipsUID,NationalID) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*  486: 919 */         pStmtCache.put(INSERT_ROW_STMT, pStmt);
/*  487:     */       }
/*  488: 922 */       for (int i = 0; i < args.length; i++)
/*  489:     */       {
/*  490: 923 */         Object obj = args[i];
/*  491: 924 */         if ((obj != null) && ((obj instanceof String)))
/*  492:     */         {
/*  493: 925 */           String objStr = (String)obj;
/*  494: 926 */           objStr = objStr.trim();
/*  495: 927 */           args[i] = objStr;
/*  496:     */         }
/*  497:     */       }
/*  498: 931 */       GenericTableAdapter.fillParameters(pStmt, args);
/*  499: 932 */       DBUtil.executeUpdate(pStmt, "INSERT INTO LU_FinancialInstitution ( InstitutionId,InstitutionName,BranchName,AddressLine1,AddressLine2,AddressLine3,City,State,StateCode,PostalCode,Country,Phone,AchRoutingNumber,WireRoutingNumber,SwiftBIC,ChipsUID,NationalID) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*  500:     */       
/*  501:     */ 
/*  502: 935 */       ArrayList routingNumbers = fi.getRoutingNumbers();
/*  503: 936 */       for (int i = 0; i < routingNumbers.size(); i++)
/*  504:     */       {
/*  505: 938 */         Object[] argsRtn = { institutionId, (String)routingNumbers.get(i) };
/*  506:     */         
/*  507: 940 */         pStmt1 = (PreparedStatement)pStmtCache.get(INSERT_RTN_ROW_STMT);
/*  508: 941 */         if (pStmt1 == null)
/*  509:     */         {
/*  510: 942 */           pStmt1 = DBUtil.prepareStatement(connection, "INSERT INTO LU_FinancialInstRtn (InstitutionId,AchRoutingNumber) VALUES ( ?, ?)");
/*  511: 943 */           pStmtCache.put(INSERT_RTN_ROW_STMT, pStmt1);
/*  512:     */         }
/*  513: 945 */         GenericTableAdapter.fillParameters(pStmt1, argsRtn);
/*  514: 946 */         DBUtil.executeUpdate(pStmt1, "INSERT INTO LU_FinancialInstRtn (InstitutionId,AchRoutingNumber) VALUES ( ?, ?)");
/*  515:     */       }
/*  516: 948 */       countries = null;
/*  517: 949 */       purgeCache();
/*  518:     */     }
/*  519:     */     catch (Exception e)
/*  520:     */     {
/*  521: 953 */       String err = "Error: " + e.getMessage() + " for data " + fi.toXML();
/*  522: 954 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter.insert failed. " + err);
/*  523: 955 */       throw new FinancialInstitutionException(e, 5, "Cannot insert data into LU_FinancialInstitution " + err);
/*  524:     */     }
/*  525:     */   }
/*  526:     */   
/*  527:     */   public static void closeAllPrepareStatements()
/*  528:     */   {
/*  529:     */     try
/*  530:     */     {
/*  531: 966 */       if (pStmtCache != null)
/*  532:     */       {
/*  533: 967 */         Set set = pStmtCache.keySet();
/*  534: 968 */         Iterator i = set.iterator();
/*  535: 969 */         while (i.hasNext())
/*  536:     */         {
/*  537: 970 */           String key = (String)i.next();
/*  538: 971 */           PreparedStatement ps = (PreparedStatement)pStmtCache.get(key);
/*  539: 972 */           DBUtil.closeStatement(ps);
/*  540: 973 */           ps = null;
/*  541: 974 */           pStmtCache = new HashMap();
/*  542:     */         }
/*  543:     */       }
/*  544:     */     }
/*  545:     */     catch (Exception e)
/*  546:     */     {
/*  547: 978 */       DebugLog.log("FinancialInstitutionAdapter.closeAllPrepareStatements: Error while closing preparedStatements: " + e.getMessage());
/*  548:     */     }
/*  549:     */   }
/*  550:     */   
/*  551:     */   public static void update(FinancialInstitution fi, Connection connection)
/*  552:     */     throws FinancialInstitutionException
/*  553:     */   {
/*  554:1006 */     int id = fi.getInstitutionId();
/*  555:1007 */     if ((id == 0) || (getByID(id, connection) == null))
/*  556:     */     {
/*  557:1009 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter modify failed. Error: No key specified");
/*  558:     */       
/*  559:1011 */       throw new FinancialInstitutionException(6, "Cannot update data in LU_FinancialInstitution");
/*  560:     */     }
/*  561:1018 */     Integer institutionId = new Integer(id);
/*  562:1020 */     if ((fi.getCountry() == null) || (fi.getCountry().equals(""))) {
/*  563:1021 */       fi.setCountry("UNKNOWN");
/*  564:     */     }
/*  565:1022 */     Object[] args = { fi.getInstitutionName(), fi.getBranchName(), fi.getStreet(), fi.getStreet2(), fi.getStreet3(), fi.getCity(), fi.getState(), fi.getStateCode(), fi.getZipCode(), fi.getCountry(), fi.getPhone(), fi.getAchRoutingNumber(), fi.getWireRoutingNumber(), fi.getSwiftBIC(), fi.getChipsUID(), fi.getNationalID(), institutionId };
/*  566:     */     
/*  567:     */ 
/*  568:     */ 
/*  569:     */ 
/*  570:     */ 
/*  571:     */ 
/*  572:     */ 
/*  573:     */ 
/*  574:     */ 
/*  575:     */ 
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:     */ 
/*  581:     */ 
/*  582:     */ 
/*  583:     */ 
/*  584:     */ 
/*  585:     */ 
/*  586:1043 */     PreparedStatement pStmt = null;
/*  587:1044 */     PreparedStatement pStmt1 = null;
/*  588:1045 */     PreparedStatement pStmt2 = null;
/*  589:     */     try
/*  590:     */     {
/*  591:1048 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET InstitutionName = ?, BranchName = ?, AddressLine1 = ?, AddressLine2 = ?, AddressLine3 = ?, City = ?, State = ?, StateCode = ?, PostalCode = ?, Country = ?, Phone = ?, AchRoutingNumber = ?, WireRoutingNumber = ?, SwiftBIC = ?, ChipsUID = ?, NationalID = ?  WHERE InstitutionId = ? ");
/*  592:1049 */       GenericTableAdapter.fillParameters(pStmt, args);
/*  593:1050 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET InstitutionName = ?, BranchName = ?, AddressLine1 = ?, AddressLine2 = ?, AddressLine3 = ?, City = ?, State = ?, StateCode = ?, PostalCode = ?, Country = ?, Phone = ?, AchRoutingNumber = ?, WireRoutingNumber = ?, SwiftBIC = ?, ChipsUID = ?, NationalID = ?  WHERE InstitutionId = ? ");
/*  594:     */       
/*  595:     */ 
/*  596:1053 */       Object[] argsRtn = { institutionId };
/*  597:1054 */       pStmt1 = DBUtil.prepareStatement(connection, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId = ? ");
/*  598:1055 */       GenericTableAdapter.fillParameters(pStmt1, argsRtn);
/*  599:1056 */       DBUtil.executeUpdate(pStmt1, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId = ? ");
/*  600:     */       
/*  601:1058 */       ArrayList routingNumbers = fi.getRoutingNumbers();
/*  602:1059 */       for (int i = 0; i < routingNumbers.size(); i++)
/*  603:     */       {
/*  604:1061 */         Object[] insertArgs = { institutionId, routingNumbers.get(i) };
/*  605:1062 */         pStmt2 = DBUtil.prepareStatement(connection, "INSERT INTO LU_FinancialInstRtn (InstitutionId,AchRoutingNumber) VALUES ( ?, ?)");
/*  606:1063 */         GenericTableAdapter.fillParameters(pStmt2, insertArgs);
/*  607:1064 */         DBUtil.executeUpdate(pStmt2, "INSERT INTO LU_FinancialInstRtn (InstitutionId,AchRoutingNumber) VALUES ( ?, ?)");
/*  608:     */       }
/*  609:1066 */       countries = null;
/*  610:1067 */       purgeCache();
/*  611:     */     }
/*  612:     */     catch (Exception e)
/*  613:     */     {
/*  614:1071 */       String err = "Error: " + e.getMessage() + " for data " + fi.toXML();
/*  615:1072 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter.update failed. " + err);
/*  616:1073 */       throw new FinancialInstitutionException(e, 6, "Cannot update data in LU_FinancialInstitution " + err);
/*  617:     */     }
/*  618:     */     finally
/*  619:     */     {
/*  620:1080 */       if (pStmt != null) {
/*  621:1082 */         DBUtil.closeStatement(pStmt);
/*  622:     */       }
/*  623:1084 */       if (pStmt1 != null) {
/*  624:1086 */         DBUtil.closeStatement(pStmt1);
/*  625:     */       }
/*  626:1088 */       if (pStmt2 != null) {
/*  627:1090 */         DBUtil.closeStatement(pStmt2);
/*  628:     */       }
/*  629:     */     }
/*  630:     */   }
/*  631:     */   
/*  632:     */   public static FinancialInstitution getByID(int id, Connection connection)
/*  633:     */     throws FinancialInstitutionException
/*  634:     */   {
/*  635:1114 */     Object[] args = { new Integer(id) };
/*  636:     */     
/*  637:1116 */     FinancialInstitutions fis = get("SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.InstitutionId=? ORDER BY fi.InstitutionName,fi.InstitutionId ", args, 2147483647, connection);
/*  638:1118 */     if (fis.size() == 0) {
/*  639:1119 */       return null;
/*  640:     */     }
/*  641:1120 */     if (fis.size() == 1) {
/*  642:1121 */       return (FinancialInstitution)fis.get(0);
/*  643:     */     }
/*  644:1122 */     throw new FinancialInstitutionException(4, "Cannot get data for unique key " + id);
/*  645:     */   }
/*  646:     */   
/*  647:     */   public static FinancialInstitutions getByAchRoutingNumber(String rtn, Connection connection)
/*  648:     */     throws FinancialInstitutionException
/*  649:     */   {
/*  650:1145 */     if ((rtn != null) && (rtn.length() == 9) && (!rtn.equals("000000000")))
/*  651:     */     {
/*  652:1148 */       FinancialInstitutions fis = null;
/*  653:1149 */       FinancialInstitution fi = getFromCache("ACHRTN", rtn);
/*  654:1150 */       if (fi != null)
/*  655:     */       {
/*  656:1152 */         fis = new FinancialInstitutions();
/*  657:1153 */         fis.add(fi);
/*  658:1154 */         return fis;
/*  659:     */       }
/*  660:1157 */       Object[] args = { rtn };
/*  661:1158 */       fis = get("SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND  EXISTS (SELECT * FROM LU_FinancialInstRtn WHERE AchRoutingNumber=?  AND InstitutionId=fi.InstitutionId)  ORDER BY fi.InstitutionName,fi.InstitutionId ", args, 2147483647, connection);
/*  662:1160 */       for (int i = 0; i < fis.size(); i++)
/*  663:     */       {
/*  664:1162 */         fi = (FinancialInstitution)fis.get(i);
/*  665:1163 */         addToCache("ACHRTN", rtn, fi);
/*  666:     */       }
/*  667:1165 */       return fis;
/*  668:     */     }
/*  669:1170 */     HashMap searchCriteria = new HashMap(1);
/*  670:1171 */     searchCriteria.put("AchRoutingNumber", rtn);
/*  671:1172 */     return get(searchCriteria, false, 2147483647, connection);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public static FinancialInstitutions getByWireRoutingNumber(String rtn, Connection connection)
/*  675:     */     throws FinancialInstitutionException
/*  676:     */   {
/*  677:1193 */     if ((rtn != null) && (rtn.length() == 9) && (!rtn.equals("000000000")))
/*  678:     */     {
/*  679:1196 */       FinancialInstitutions fis = null;
/*  680:1197 */       FinancialInstitution fi = getFromCache("WIRERTN", rtn);
/*  681:1198 */       if (fi != null)
/*  682:     */       {
/*  683:1200 */         fis = new FinancialInstitutions();
/*  684:1201 */         fis.add(fi);
/*  685:1202 */         return fis;
/*  686:     */       }
/*  687:1205 */       Object[] args = { rtn };
/*  688:1206 */       fis = get("SELECT  InstitutionId,InstitutionName,BranchName,AddressLine1,AddressLine2,AddressLine3,City,State,StateCode,PostalCode,Country,Phone,AchRoutingNumber,WireRoutingNumber,SwiftBIC,ChipsUID,NationalID, ''  FROM LU_FinancialInstitution WHERE WireRoutingNumber=?", args, 2147483647, connection);
/*  689:1208 */       for (int i = 0; i < fis.size(); i++)
/*  690:     */       {
/*  691:1210 */         fi = (FinancialInstitution)fis.get(i);
/*  692:1211 */         addToCache("WIRERTN", rtn, fi);
/*  693:     */       }
/*  694:1213 */       return fis;
/*  695:     */     }
/*  696:1218 */     HashMap searchCriteria = new HashMap(1);
/*  697:1219 */     searchCriteria.put("WireRoutingNumber", rtn);
/*  698:1220 */     return get(searchCriteria, false, 2147483647, connection);
/*  699:     */   }
/*  700:     */   
/*  701:     */   public static FinancialInstitutions getByBicCode(String bic, Connection connection)
/*  702:     */     throws FinancialInstitutionException
/*  703:     */   {
/*  704:1247 */     if (bic != null)
/*  705:     */     {
/*  706:1249 */       String bicCode = bic.trim();
/*  707:1250 */       if (bic.length() == 8) {
/*  708:1251 */         bicCode = bicCode.concat("XXX");
/*  709:     */       }
/*  710:1252 */       if (bicCode.length() == 11)
/*  711:     */       {
/*  712:1255 */         FinancialInstitutions fis = null;
/*  713:1256 */         FinancialInstitution fi = getFromCache("BIC", bicCode);
/*  714:1257 */         if (fi != null)
/*  715:     */         {
/*  716:1259 */           fis = new FinancialInstitutions();
/*  717:1260 */           fis.add(fi);
/*  718:1261 */           return fis;
/*  719:     */         }
/*  720:1263 */         Object[] args = { bicCode };
/*  721:1264 */         fis = get("SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.SwiftBIC=? ORDER BY fi.InstitutionName,fi.InstitutionId ", args, 2147483647, connection);
/*  722:1266 */         for (int i = 0; i < fis.size(); i++)
/*  723:     */         {
/*  724:1268 */           fi = (FinancialInstitution)fis.get(i);
/*  725:1269 */           addToCache("BIC", bicCode, fi);
/*  726:     */         }
/*  727:1271 */         return fis;
/*  728:     */       }
/*  729:     */     }
/*  730:1275 */     HashMap searchCriteria = new HashMap(1);
/*  731:1276 */     searchCriteria.put("SwiftBIC", bic);
/*  732:1277 */     return get(searchCriteria, false, 2147483647, connection);
/*  733:     */   }
/*  734:     */   
/*  735:     */   public static FinancialInstitutions getByChipsUID(String chipsUID, Connection connection)
/*  736:     */     throws FinancialInstitutionException
/*  737:     */   {
/*  738:1294 */     Object[] args = { chipsUID };
/*  739:1295 */     return get("SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId AND fi.ChipsUID=? ORDER BY fi.InstitutionName,fi.InstitutionId ", args, 2147483647, connection);
/*  740:     */   }
/*  741:     */   
/*  742:     */   public static FinancialInstitutions getByBankName(String name, Connection connection)
/*  743:     */     throws FinancialInstitutionException
/*  744:     */   {
/*  745:1312 */     HashMap searchCriteria = new HashMap(1);
/*  746:1313 */     searchCriteria.put("InstitutionName", name);
/*  747:1314 */     return get(searchCriteria, false, 2147483647, connection);
/*  748:     */   }
/*  749:     */   
/*  750:     */   public static ArrayList getCountries()
/*  751:     */     throws FinancialInstitutionException
/*  752:     */   {
/*  753:1327 */     if (countries != null) {
/*  754:1328 */       return countries;
/*  755:     */     }
/*  756:1329 */     Connection connection = null;
/*  757:1330 */     ArrayList countryList = new ArrayList();
/*  758:1331 */     ResultSet results = null;
/*  759:1332 */     Statement stmt = null;
/*  760:     */     try
/*  761:     */     {
/*  762:1335 */       connection = GenericTableAdapter.getConnection();
/*  763:1336 */       stmt = DBUtil.createStatement(connection);
/*  764:1337 */       results = DBUtil.executeQuery(stmt, "SELECT  DISTINCT Country FROM LU_FinancialInstitution ORDER BY 1");
/*  765:1338 */       while (results.next()) {
/*  766:1339 */         countryList.add(results.getString(1));
/*  767:     */       }
/*  768:1340 */       connection.commit();
/*  769:1341 */       if (countryList.size() > 0) {
/*  770:1342 */         countries = countryList;
/*  771:     */       }
/*  772:     */     }
/*  773:     */     catch (Exception e)
/*  774:     */     {
/*  775:     */       try
/*  776:     */       {
/*  777:1347 */         DBUtil.rollback(connection);
/*  778:     */       }
/*  779:     */       catch (Exception ex) {}
/*  780:1351 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter getCountries failed. Error: " + e.getMessage());
/*  781:     */       
/*  782:1353 */       throw new FinancialInstitutionException(e, 15, "Cannot get countries: " + e.getMessage());
/*  783:     */     }
/*  784:     */     finally
/*  785:     */     {
/*  786:1359 */       DBUtil.closeResultSet(results);
/*  787:1360 */       DBUtil.closeStatement(stmt);
/*  788:     */       
/*  789:     */ 
/*  790:1363 */       GenericTableAdapter.releaseConnection(connection);
/*  791:     */     }
/*  792:1365 */     return countries;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public static void purge()
/*  796:     */     throws FinancialInstitutionException
/*  797:     */   {
/*  798:1378 */     purge(2147483646);
/*  799:     */   }
/*  800:     */   
/*  801:     */   public static void purge(int batchsize)
/*  802:     */     throws FinancialInstitutionException
/*  803:     */   {
/*  804:1392 */     Connection connection = null;
/*  805:1393 */     PreparedStatement pStmt = null;
/*  806:1394 */     PreparedStatement pStmt1 = null;
/*  807:     */     try
/*  808:     */     {
/*  809:1397 */       connection = GenericTableAdapter.getConnection();
/*  810:1398 */       int minValue = gta.getKeyValue(false, connection);
/*  811:1399 */       int maxValue = gta.getKeyValue(true, connection);
/*  812:1400 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/*  813:     */       {
/*  814:1402 */         Integer[] args = { new Integer(i), new Integer(i + batchsize) };
/*  815:1403 */         pStmt = DBUtil.prepareStatement(connection, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId>=? AND InstitutionId<=?");
/*  816:1404 */         GenericTableAdapter.fillParameters(pStmt, args);
/*  817:1405 */         DBUtil.executeUpdate(pStmt, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId>=? AND InstitutionId<=?");
/*  818:     */         
/*  819:1407 */         pStmt1 = DBUtil.prepareStatement(connection, "DELETE FROM LU_FinancialInstitution WHERE InstitutionId>=? AND InstitutionId<=?");
/*  820:1408 */         GenericTableAdapter.fillParameters(pStmt1, args);
/*  821:1409 */         DBUtil.executeUpdate(pStmt1, "DELETE FROM LU_FinancialInstitution WHERE InstitutionId>=? AND InstitutionId<=?");
/*  822:1410 */         connection.commit();
/*  823:1411 */         countries = null;
/*  824:1412 */         purgeCache();
/*  825:     */       }
/*  826:     */     }
/*  827:     */     catch (Exception e)
/*  828:     */     {
/*  829:     */       try
/*  830:     */       {
/*  831:1418 */         DBUtil.rollback(connection);
/*  832:     */       }
/*  833:     */       catch (Exception ex) {}
/*  834:1422 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter purge failed. Error: " + e.getMessage());
/*  835:     */       
/*  836:1424 */       throw new FinancialInstitutionException(e, 9, "Cannot purge data: " + e.getMessage());
/*  837:     */     }
/*  838:     */     finally
/*  839:     */     {
/*  840:1430 */       if (pStmt != null) {
/*  841:1432 */         DBUtil.closeStatement(pStmt);
/*  842:     */       }
/*  843:1434 */       if (pStmt1 != null) {
/*  844:1436 */         DBUtil.closeStatement(pStmt1);
/*  845:     */       }
/*  846:1441 */       GenericTableAdapter.releaseConnection(connection);
/*  847:     */     }
/*  848:     */   }
/*  849:     */   
/*  850:     */   public static void deleteAchRoutingNumber(String rtn, Connection connection)
/*  851:     */     throws FinancialInstitutionException
/*  852:     */   {
/*  853:1456 */     PreparedStatement pStmt = null;
/*  854:1457 */     Statement stmt = null;
/*  855:1458 */     Statement stmt1 = null;
/*  856:     */     try
/*  857:     */     {
/*  858:1462 */       String[] args = { rtn };
/*  859:1463 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET AchRoutingNumber='000000000' WHERE AchRoutingNumber=?");
/*  860:1464 */       GenericTableAdapter.fillParameters(pStmt, args);
/*  861:1465 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET AchRoutingNumber='000000000' WHERE AchRoutingNumber=?");
/*  862:     */       
/*  863:1467 */       stmt = DBUtil.createStatement(connection);
/*  864:1468 */       DBUtil.executeUpdate(stmt, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE AchRoutingNumber = '000000000' AND  (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL )");
/*  865:     */       
/*  866:1470 */       stmt1 = DBUtil.createStatement(connection);
/*  867:1471 */       DBUtil.executeUpdate(stmt1, "DELETE FROM LU_FinancialInstitution WHERE AchRoutingNumber = '000000000' AND  (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL ");
/*  868:     */     }
/*  869:     */     catch (Exception e)
/*  870:     */     {
/*  871:1475 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter deleteAchRoutingNumber failed. Error: " + e.getMessage());
/*  872:     */       
/*  873:1477 */       throw new FinancialInstitutionException(e, 14, "Cannot delete based on routing number: " + e.getMessage());
/*  874:     */     }
/*  875:     */     finally
/*  876:     */     {
/*  877:1483 */       if (pStmt != null) {
/*  878:1485 */         DBUtil.closeStatement(pStmt);
/*  879:     */       }
/*  880:1487 */       if (stmt != null) {
/*  881:1489 */         DBUtil.closeStatement(stmt);
/*  882:     */       }
/*  883:1491 */       if (stmt1 != null) {
/*  884:1493 */         DBUtil.closeStatement(stmt1);
/*  885:     */       }
/*  886:     */     }
/*  887:     */   }
/*  888:     */   
/*  889:     */   public static void deleteWireRoutingNumber(String rtn, Connection connection)
/*  890:     */     throws FinancialInstitutionException
/*  891:     */   {
/*  892:1508 */     PreparedStatement pStmt = null;
/*  893:1509 */     Statement stmt = null;
/*  894:     */     try
/*  895:     */     {
/*  896:1513 */       String[] args = { rtn };
/*  897:1514 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET WireRoutingNumber='000000000' WHERE WireRoutingNumber=?");
/*  898:1515 */       GenericTableAdapter.fillParameters(pStmt, args);
/*  899:1516 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET WireRoutingNumber='000000000' WHERE WireRoutingNumber=?");
/*  900:     */       
/*  901:1518 */       stmt = DBUtil.createStatement(connection);
/*  902:1519 */       DBUtil.executeUpdate(stmt, "DELETE FROM LU_FinancialInstitution WHERE WireRoutingNumber = '000000000' AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL ");
/*  903:     */     }
/*  904:     */     catch (Exception e)
/*  905:     */     {
/*  906:1523 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter deleteWireRoutingNumber failed. Error: " + e.getMessage());
/*  907:     */       
/*  908:1525 */       throw new FinancialInstitutionException(e, 14, "Cannot delete based on routing number: " + e.getMessage());
/*  909:     */     }
/*  910:     */     finally
/*  911:     */     {
/*  912:1531 */       if (pStmt != null) {
/*  913:1533 */         DBUtil.closeStatement(pStmt);
/*  914:     */       }
/*  915:1535 */       if (stmt != null) {
/*  916:1537 */         DBUtil.closeStatement(stmt);
/*  917:     */       }
/*  918:     */     }
/*  919:     */   }
/*  920:     */   
/*  921:     */   public static void deleteSwiftCode(String code, Connection connection)
/*  922:     */     throws FinancialInstitutionException
/*  923:     */   {
/*  924:1553 */     PreparedStatement pStmt = null;
/*  925:1554 */     Statement stmt = null;
/*  926:     */     try
/*  927:     */     {
/*  928:1558 */       String[] args = { code };
/*  929:1559 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET SwiftBIC = NULL  WHERE SwiftBIC=?");
/*  930:1560 */       GenericTableAdapter.fillParameters(pStmt, args);
/*  931:1561 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET SwiftBIC = NULL  WHERE SwiftBIC=?");
/*  932:     */       
/*  933:     */ 
/*  934:     */ 
/*  935:1565 */       stmt = DBUtil.createStatement(connection);
/*  936:1566 */       DBUtil.executeUpdate(stmt, "DELETE FROM LU_FinancialInstitution WHERE AchRoutingNumber IS NULL  AND WireRoutingNumber IS NULL  AND SwiftBIC IS NULL  AND ChipsUID IS NULL  AND NationalID IS NULL ");
/*  937:     */     }
/*  938:     */     catch (Exception e)
/*  939:     */     {
/*  940:1570 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter deleteSwiftCode failed. Error: " + e.getMessage());
/*  941:     */       
/*  942:1572 */       throw new FinancialInstitutionException(e, 14, "Cannot delete based on swift code: " + e.getMessage());
/*  943:     */     }
/*  944:     */     finally
/*  945:     */     {
/*  946:1578 */       if (pStmt != null) {
/*  947:1580 */         DBUtil.closeStatement(pStmt);
/*  948:     */       }
/*  949:1582 */       if (stmt != null) {
/*  950:1584 */         DBUtil.closeStatement(stmt);
/*  951:     */       }
/*  952:     */     }
/*  953:     */   }
/*  954:     */   
/*  955:     */   public static void purgeAchRoutingNumbers(String subSelect, Object[] args, Connection connection)
/*  956:     */     throws FinancialInstitutionException
/*  957:     */   {
/*  958:1601 */     PreparedStatement pStmt = null;
/*  959:1602 */     Statement stmt = null;
/*  960:1603 */     Statement stmt1 = null;
/*  961:     */     try
/*  962:     */     {
/*  963:1609 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET AchRoutingNumber='000000000' WHERE AchRoutingNumber IN (" + subSelect + ")");
/*  964:1610 */       GenericTableAdapter.fillParameters(pStmt, args);
/*  965:1611 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET AchRoutingNumber='000000000' WHERE AchRoutingNumber IN (" + subSelect + ")");
/*  966:     */       
/*  967:1613 */       stmt = DBUtil.createStatement(connection);
/*  968:1614 */       DBUtil.executeUpdate(stmt, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE AchRoutingNumber = '000000000' AND  (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL )");
/*  969:     */       
/*  970:1616 */       stmt1 = DBUtil.createStatement(connection);
/*  971:1617 */       DBUtil.executeUpdate(stmt1, "DELETE FROM LU_FinancialInstitution WHERE AchRoutingNumber = '000000000' AND  (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL ");
/*  972:     */     }
/*  973:     */     catch (Exception e)
/*  974:     */     {
/*  975:1621 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter purgeAchRoutingNumbers failed. Error: " + e.getMessage());
/*  976:     */       
/*  977:1623 */       throw new FinancialInstitutionException(e, 14, "Cannot purge routing numbers: " + e.getMessage());
/*  978:     */     }
/*  979:     */     finally
/*  980:     */     {
/*  981:1629 */       if (pStmt != null) {
/*  982:1631 */         DBUtil.closeStatement(pStmt);
/*  983:     */       }
/*  984:1633 */       if (stmt != null) {
/*  985:1635 */         DBUtil.closeStatement(stmt);
/*  986:     */       }
/*  987:1637 */       if (stmt1 != null) {
/*  988:1639 */         DBUtil.closeStatement(stmt1);
/*  989:     */       }
/*  990:     */     }
/*  991:     */   }
/*  992:     */   
/*  993:     */   public static void purgeWireRoutingNumbers(String subSelect, Object[] args, Connection connection)
/*  994:     */     throws FinancialInstitutionException
/*  995:     */   {
/*  996:1657 */     PreparedStatement pStmt = null;
/*  997:1658 */     Statement stmt = null;
/*  998:1659 */     Statement stmt1 = null;
/*  999:     */     try
/* 1000:     */     {
/* 1001:1663 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET WireRoutingNumber='000000000' WHERE WireRoutingNumber IN (" + subSelect + ")");
/* 1002:1664 */       GenericTableAdapter.fillParameters(pStmt, args);
/* 1003:1665 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET WireRoutingNumber='000000000' WHERE WireRoutingNumber IN (" + subSelect + ")");
/* 1004:     */       
/* 1005:1667 */       stmt = DBUtil.createStatement(connection);
/* 1006:1668 */       DBUtil.executeUpdate(stmt, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE WireRoutingNumber = '000000000' AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL )");
/* 1007:     */       
/* 1008:1670 */       stmt1 = DBUtil.createStatement(connection);
/* 1009:1671 */       DBUtil.executeUpdate(stmt1, "DELETE FROM LU_FinancialInstitution WHERE WireRoutingNumber = '000000000' AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND SwiftBIC IS NULL  OR SwiftBIC= '' AND ChipsUID IS NULL  AND NationalID IS NULL ");
/* 1010:     */     }
/* 1011:     */     catch (Exception e)
/* 1012:     */     {
/* 1013:1675 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter purgeWireRoutingNumbers failed. Error: " + e.getMessage());
/* 1014:     */       
/* 1015:1677 */       throw new FinancialInstitutionException(e, 14, "Cannot purge routing numbers: " + e.getMessage());
/* 1016:     */     }
/* 1017:     */     finally
/* 1018:     */     {
/* 1019:1683 */       if (pStmt != null) {
/* 1020:1685 */         DBUtil.closeStatement(pStmt);
/* 1021:     */       }
/* 1022:1687 */       if (stmt != null) {
/* 1023:1689 */         DBUtil.closeStatement(stmt);
/* 1024:     */       }
/* 1025:1691 */       if (stmt1 != null) {
/* 1026:1693 */         DBUtil.closeStatement(stmt1);
/* 1027:     */       }
/* 1028:     */     }
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public static void purgeSwiftNumbers(String subSelect, Object[] args, Connection connection)
/* 1032:     */     throws FinancialInstitutionException
/* 1033:     */   {
/* 1034:1711 */     PreparedStatement pStmt = null;
/* 1035:1712 */     Statement stmt = null;
/* 1036:1713 */     Statement stmt1 = null;
/* 1037:     */     try
/* 1038:     */     {
/* 1039:1717 */       pStmt = DBUtil.prepareStatement(connection, "UPDATE LU_FinancialInstitution SET SwiftBIC = NULL  WHERE SwiftBIC IN (" + subSelect + ")");
/* 1040:1718 */       GenericTableAdapter.fillParameters(pStmt, args);
/* 1041:1719 */       DBUtil.executeUpdate(pStmt, "UPDATE LU_FinancialInstitution SET SwiftBIC = NULL  WHERE SwiftBIC IN (" + subSelect + ")");
/* 1042:     */       
/* 1043:1721 */       stmt = DBUtil.createStatement(connection);
/* 1044:1722 */       DBUtil.executeUpdate(stmt, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId IN (SELECT InstitutionId FROM LU_FinancialInstitution WHERE SwiftBIC IS NULL  AND (WireRoutingNumber IS NULL  OR WireRoutingNumber = '000000000') AND  (AchRoutingNumber IS NULL  OR AchRoutingNumber = '000000000') AND ChipsUID IS NULL  AND NationalID IS NULL )");
/* 1045:     */       
/* 1046:1724 */       stmt1 = DBUtil.createStatement(connection);
/* 1047:1725 */       DBUtil.executeUpdate(stmt1, "DELETE FROM LU_FinancialInstitution WHERE AchRoutingNumber IS NULL  AND WireRoutingNumber IS NULL  AND SwiftBIC IS NULL  AND ChipsUID IS NULL  AND NationalID IS NULL ");
/* 1048:     */     }
/* 1049:     */     catch (Exception e)
/* 1050:     */     {
/* 1051:1729 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter purgeRoutingNumbers failed. Error: " + e.getMessage());
/* 1052:     */       
/* 1053:1731 */       throw new FinancialInstitutionException(e, 14, "Cannot purge routing numbers: " + e.getMessage());
/* 1054:     */     }
/* 1055:     */     finally
/* 1056:     */     {
/* 1057:1737 */       if (pStmt != null) {
/* 1058:1739 */         DBUtil.closeStatement(pStmt);
/* 1059:     */       }
/* 1060:1741 */       if (stmt != null) {
/* 1061:1743 */         DBUtil.closeStatement(stmt);
/* 1062:     */       }
/* 1063:1745 */       if (stmt1 != null) {
/* 1064:1747 */         DBUtil.closeStatement(stmt1);
/* 1065:     */       }
/* 1066:     */     }
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   private static void deleteByID(int id, Connection connection)
/* 1070:     */     throws FinancialInstitutionException
/* 1071:     */   {
/* 1072:1775 */     Object[] args = { new Integer(id) };
/* 1073:1776 */     PreparedStatement pStmt = null;
/* 1074:1777 */     PreparedStatement pStmt1 = null;
/* 1075:     */     try
/* 1076:     */     {
/* 1077:1781 */       pStmt = DBUtil.prepareStatement(connection, "DELETE FROM LU_FinancialInstitution WHERE InstitutionId =? ");
/* 1078:1782 */       GenericTableAdapter.fillParameters(pStmt, args);
/* 1079:1783 */       DBUtil.executeUpdate(pStmt, "DELETE FROM LU_FinancialInstitution WHERE InstitutionId =? ");
/* 1080:     */       
/* 1081:1785 */       pStmt1 = DBUtil.prepareStatement(connection, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId = ? ");
/* 1082:1786 */       GenericTableAdapter.fillParameters(pStmt1, args);
/* 1083:1787 */       DBUtil.executeUpdate(pStmt1, "DELETE FROM LU_FinancialInstRtn WHERE InstitutionId = ? ");
/* 1084:1788 */       countries = null;
/* 1085:1789 */       purgeCache();
/* 1086:     */     }
/* 1087:     */     catch (Exception e)
/* 1088:     */     {
/* 1089:1793 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter delete failed. Delete failed. Error: " + e.getMessage());
/* 1090:     */       
/* 1091:     */ 
/* 1092:1796 */       throw new FinancialInstitutionException(e, 7, "Cannot delete data from LU_FinancialInstitution");
/* 1093:     */     }
/* 1094:     */     finally
/* 1095:     */     {
/* 1096:1802 */       if (pStmt != null) {
/* 1097:1804 */         DBUtil.closeStatement(pStmt);
/* 1098:     */       }
/* 1099:1806 */       if (pStmt1 != null) {
/* 1100:1808 */         DBUtil.closeStatement(pStmt1);
/* 1101:     */       }
/* 1102:     */     }
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   private static FinancialInstitutions get(HashMap searchCriteria, boolean isConjunctive, int maxBeans)
/* 1106:     */     throws FinancialInstitutionException
/* 1107:     */   {
/* 1108:1836 */     Connection connection = null;
/* 1109:1837 */     FinancialInstitutions results = null;
/* 1110:     */     try
/* 1111:     */     {
/* 1112:1840 */       connection = GenericTableAdapter.getConnection();
/* 1113:1841 */       results = get(searchCriteria, isConjunctive, maxBeans, connection);
/* 1114:1842 */       connection.commit();
/* 1115:1843 */       return results;
/* 1116:     */     }
/* 1117:     */     catch (Exception e)
/* 1118:     */     {
/* 1119:1848 */       throw new FinancialInstitutionException(e, 4, "Could not get results");
/* 1120:     */     }
/* 1121:     */     finally
/* 1122:     */     {
/* 1123:1854 */       GenericTableAdapter.releaseConnection(connection);
/* 1124:     */     }
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   private static FinancialInstitutions get(HashMap searchCriteria, boolean isConjunctive, int maxBeans, Connection connection)
/* 1128:     */     throws FinancialInstitutionException
/* 1129:     */   {
/* 1130:1884 */     StringBuffer buf = new StringBuffer("SELECT  fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber FROM LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId");
/* 1131:1885 */     StringBuffer clsIDs = new StringBuffer("");
/* 1132:1886 */     StringBuffer achWire = new StringBuffer("");
/* 1133:1887 */     StringBuffer clsOther = new StringBuffer("");
/* 1134:1888 */     Object[] args = null;
/* 1135:     */     try
/* 1136:     */     {
/* 1137:1891 */       String val = null;
/* 1138:1892 */       if (searchCriteria != null)
/* 1139:     */       {
/* 1140:1900 */         Iterator searchColumns = searchCriteria.keySet().iterator();
/* 1141:1901 */         String key = null;
/* 1142:1902 */         boolean enforceNotNull = true;
/* 1143:1903 */         boolean isACHRtnSet = false;
/* 1144:1905 */         while (searchColumns.hasNext())
/* 1145:     */         {
/* 1146:1906 */           key = (String)searchColumns.next();
/* 1147:1907 */           val = (String)searchCriteria.get(key);
/* 1148:1908 */           if ((val != null) && (val.trim().length() > 0)) {
/* 1149:1909 */             if (key.equals("fi.AchRoutingNumber"))
/* 1150:     */             {
/* 1151:1911 */               if (val.equals("IS_NOT_NULL"))
/* 1152:     */               {
/* 1153:1912 */                 if (clsIDs.length() != 0) {
/* 1154:1912 */                   clsIDs.append(" OR ");
/* 1155:     */                 }
/* 1156:1913 */                 clsIDs.append(key + " is not null ");
/* 1157:     */               }
/* 1158:     */               else
/* 1159:     */               {
/* 1160:1920 */                 if (achWire.length() != 0) {
/* 1161:1920 */                   achWire.append(isConjunctive ? " AND " : " OR ");
/* 1162:     */                 }
/* 1163:1921 */                 achWire.append(" EXISTS (SELECT * FROM LU_FinancialInstRtn WHERE InstitutionId=fi.InstitutionId AND AchRoutingNumber LIKE '" + replaceSingleQuotes(val) + "%'" + ") ");
/* 1164:     */                 
/* 1165:     */ 
/* 1166:1924 */                 enforceNotNull = false;
/* 1167:1925 */                 isACHRtnSet = true;
/* 1168:     */               }
/* 1169:     */             }
/* 1170:1928 */             else if ((key.equals("fi.WireRoutingNumber")) || (key.equals("fi.SwiftBIC")) || (key.equals("fi.ChipsUID")) || (key.equals("fi.NationalID")))
/* 1171:     */             {
/* 1172:1933 */               if (val.equals("IS_NOT_NULL"))
/* 1173:     */               {
/* 1174:1934 */                 if (clsIDs.length() != 0) {
/* 1175:1934 */                   clsIDs.append(" OR ");
/* 1176:     */                 }
/* 1177:1935 */                 clsIDs.append(key + " is not null ");
/* 1178:     */               }
/* 1179:     */               else
/* 1180:     */               {
/* 1181:1941 */                 if ((key.equals("fi.WireRoutingNumber")) && (isACHRtnSet))
/* 1182:     */                 {
/* 1183:1943 */                   if (achWire.length() != 0) {
/* 1184:1944 */                     achWire.append(" OR ");
/* 1185:     */                   }
/* 1186:1945 */                   achWire.append(key + " LIKE " + "'" + replaceSingleQuotes(val) + "%'");
/* 1187:     */                 }
/* 1188:     */                 else
/* 1189:     */                 {
/* 1190:1948 */                   if (clsOther.length() != 0) {
/* 1191:1949 */                     clsOther.append(isConjunctive ? " AND " : " OR ");
/* 1192:     */                   }
/* 1193:1952 */                   clsOther.append(key + " LIKE " + "'" + replaceSingleQuotes(val) + "%'");
/* 1194:     */                 }
/* 1195:1955 */                 enforceNotNull = false;
/* 1196:     */               }
/* 1197:     */             }
/* 1198:     */             else
/* 1199:     */             {
/* 1200:1959 */               if (clsOther.length() != 0) {
/* 1201:1959 */                 clsOther.append(isConjunctive ? " AND " : " OR ");
/* 1202:     */               }
/* 1203:1960 */               clsOther.append(key + " LIKE " + "'" + replaceSingleQuotes(val) + "%'");
/* 1204:     */             }
/* 1205:     */           }
/* 1206:     */         }
/* 1207:1967 */         if ((clsOther.length() != 0) && (clsIDs.length() != 0) && (enforceNotNull))
/* 1208:     */         {
/* 1209:1968 */           if (achWire.length() != 0) {
/* 1210:1969 */             buf.append(" AND (((" + achWire + ")" + (isConjunctive ? " AND " : " OR ") + clsOther + ") " + (isConjunctive ? " AND " : " OR ") + " (" + clsIDs + "))" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1211:     */           } else {
/* 1212:1971 */             buf.append(" AND ((" + clsOther + ") " + (isConjunctive ? " AND " : " OR ") + " (" + clsIDs + "))" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1213:     */           }
/* 1214:     */         }
/* 1215:1973 */         else if (clsOther.length() != 0)
/* 1216:     */         {
/* 1217:1974 */           if (achWire.length() != 0) {
/* 1218:1975 */             buf.append(" AND ((" + achWire + ")" + (isConjunctive ? " AND " : " OR ") + clsOther + ")" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1219:     */           } else {
/* 1220:1977 */             buf.append(" AND (" + clsOther + ")" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1221:     */           }
/* 1222:     */         }
/* 1223:1979 */         else if ((clsIDs.length() != 0) && (enforceNotNull))
/* 1224:     */         {
/* 1225:1980 */           if (achWire.length() != 0) {
/* 1226:1981 */             buf.append(" AND (" + achWire + ")" + (isConjunctive ? " AND " : " OR ") + "(" + clsIDs + ")" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1227:     */           } else {
/* 1228:1983 */             buf.append(" AND (" + clsIDs + ")" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1229:     */           }
/* 1230:     */         }
/* 1231:1984 */         else if (achWire.length() != 0) {
/* 1232:1985 */           buf.append(" AND (" + achWire + ")" + " ORDER BY fi.InstitutionName,fi.InstitutionId ");
/* 1233:     */         }
/* 1234:     */       }
/* 1235:     */     }
/* 1236:     */     catch (Throwable e)
/* 1237:     */     {
/* 1238:1992 */       e.printStackTrace();
/* 1239:1993 */       throw new FinancialInstitutionException(e, 4, "Could not get results");
/* 1240:     */     }
/* 1241:2001 */     return get(buf.toString(), args, maxBeans, connection);
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   private static FinancialInstitutions getPossibleMatches(FinancialInstitution fi, Connection connection)
/* 1245:     */     throws FinancialInstitutionException
/* 1246:     */   {
/* 1247:2022 */     String testVal = null;
/* 1248:     */     
/* 1249:2024 */     int id = fi.getInstitutionId();
/* 1250:2025 */     if (id != 0)
/* 1251:     */     {
/* 1252:2027 */       FinancialInstitution match = getByID(id, connection);
/* 1253:2028 */       if (match != null)
/* 1254:     */       {
/* 1255:2030 */         FinancialInstitutions fis = new FinancialInstitutions();
/* 1256:2031 */         fis.add(match);
/* 1257:2032 */         return fis;
/* 1258:     */       }
/* 1259:     */     }
/* 1260:2037 */     HashMap searchCriteria = new HashMap(6);
/* 1261:2038 */     String val = null;
/* 1262:2039 */     testVal = fi.getAchRoutingNumber();
/* 1263:2040 */     if ((testVal != null) && (!testVal.equals("")) && (!testVal.equals("000000000"))) {
/* 1264:2042 */       searchCriteria.put("AchRoutingNumber", testVal);
/* 1265:     */     }
/* 1266:2045 */     testVal = fi.getWireRoutingNumber();
/* 1267:2046 */     if ((testVal != null) && (!testVal.equals("")) && (!testVal.equals("000000000"))) {
/* 1268:2048 */       searchCriteria.put("WireRoutingNumber", testVal);
/* 1269:     */     }
/* 1270:2051 */     testVal = fi.getSwiftBIC();
/* 1271:2052 */     if ((testVal != null) && (!testVal.equals(""))) {
/* 1272:2053 */       searchCriteria.put("SwiftBIC", testVal);
/* 1273:     */     }
/* 1274:2055 */     testVal = fi.getChipsUID();
/* 1275:2056 */     if ((testVal != null) && (!testVal.equals(""))) {
/* 1276:2057 */       searchCriteria.put("ChipsUID", testVal);
/* 1277:     */     }
/* 1278:2060 */     val = fi.getInstitutionName();
/* 1279:2061 */     if ((val != null) && (!val.equals(""))) {
/* 1280:2062 */       searchCriteria.put("InstitutionName", val);
/* 1281:     */     }
/* 1282:2064 */     val = fi.getStreet();
/* 1283:2065 */     if ((val != null) && (!val.equals(""))) {
/* 1284:2066 */       searchCriteria.put("AddressLine1", val);
/* 1285:     */     }
/* 1286:2068 */     val = fi.getCity();
/* 1287:2069 */     if ((val != null) && (!val.equals(""))) {
/* 1288:2070 */       searchCriteria.put("City", val);
/* 1289:     */     }
/* 1290:2072 */     val = fi.getStateCode();
/* 1291:2073 */     if ((val != null) && (!val.equals(""))) {
/* 1292:2074 */       searchCriteria.put("StateCode", val);
/* 1293:     */     }
/* 1294:2076 */     val = fi.getZipCode();
/* 1295:2077 */     if ((val != null) && (!val.equals(""))) {
/* 1296:2078 */       searchCriteria.put("PostalCode", val);
/* 1297:     */     }
/* 1298:2080 */     val = fi.getCountry();
/* 1299:2081 */     if ((val != null) && (!val.equals(""))) {
/* 1300:2082 */       searchCriteria.put("Country", val);
/* 1301:     */     }
/* 1302:2084 */     return get(searchCriteria, true, 2147483647, connection);
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   private static FinancialInstitutions get(String selectStatement, String arg, int maxBeans)
/* 1306:     */     throws FinancialInstitutionException
/* 1307:     */   {
/* 1308:2104 */     Connection connection = null;
/* 1309:2105 */     FinancialInstitutions results = new FinancialInstitutions();
/* 1310:     */     try
/* 1311:     */     {
/* 1312:2108 */       connection = GenericTableAdapter.getConnection();
/* 1313:2109 */       Object[] args = { arg };
/* 1314:2110 */       results = get(selectStatement, args, maxBeans, connection);
/* 1315:2111 */       connection.commit();
/* 1316:     */     }
/* 1317:     */     catch (Exception e)
/* 1318:     */     {
/* 1319:2116 */       throw new FinancialInstitutionException(e, 4, "Could not get results");
/* 1320:     */     }
/* 1321:     */     finally
/* 1322:     */     {
/* 1323:2122 */       GenericTableAdapter.releaseConnection(connection);
/* 1324:     */     }
/* 1325:2124 */     return results;
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   private static FinancialInstitutions get(String selectStatement, Object[] args, int maxRows, Connection connection)
/* 1329:     */     throws FinancialInstitutionException
/* 1330:     */   {
/* 1331:2147 */     ResultSet results = null;
/* 1332:2148 */     PreparedStatement pStmt = null;
/* 1333:2149 */     FinancialInstitutions fis = new FinancialInstitutions();
/* 1334:     */     try
/* 1335:     */     {
/* 1336:2153 */       pStmt = DBUtil.prepareStatement(connection, selectStatement);
/* 1337:2154 */       GenericTableAdapter.fillParameters(pStmt, args);
/* 1338:2155 */       results = DBUtil.executeQuery(pStmt, selectStatement);
/* 1339:2156 */       FinancialInstitution prevFi = null;
/* 1340:2157 */       FinancialInstitution fi = null;
/* 1341:2158 */       int rows = 0;
/* 1342:2159 */       while (results.next())
/* 1343:     */       {
/* 1344:2161 */         fi = new FinancialInstitution();
/* 1345:2162 */         fi.setInstitutionId(results.getInt(1));
/* 1346:2163 */         fi.setInstitutionName(results.getString(2));
/* 1347:2164 */         fi.setBranchName(results.getString(3));
/* 1348:2165 */         fi.setStreet(results.getString(4));
/* 1349:2166 */         fi.setStreet2(results.getString(5));
/* 1350:2167 */         fi.setStreet3(results.getString(6));
/* 1351:2168 */         fi.setCity(results.getString(7));
/* 1352:2169 */         fi.setState(results.getString(8));
/* 1353:2170 */         fi.setStateCode(results.getString(9));
/* 1354:2171 */         fi.setZipCode(results.getString(10));
/* 1355:2172 */         fi.setCountry(results.getString(11));
/* 1356:2173 */         fi.setPhone(results.getString(12));
/* 1357:2174 */         fi.setAchRoutingNumber(results.getString(13));
/* 1358:2175 */         fi.setWireRoutingNumber(results.getString(14));
/* 1359:2176 */         fi.setSwiftBIC(results.getString(15));
/* 1360:2177 */         fi.setChipsUID(results.getString(16));
/* 1361:2178 */         fi.setNationalID(results.getString(17));
/* 1362:2179 */         String rtn = results.getString(18);
/* 1363:2180 */         if ((rtn != null) && (!rtn.equals(""))) {
/* 1364:2181 */           fi.addRoutingNumber(rtn);
/* 1365:     */         } else {
/* 1366:2183 */           rtn = null;
/* 1367:     */         }
/* 1368:2187 */         if ((prevFi != null) && (fi.getInstitutionId() == prevFi.getInstitutionId()) && (rtn != null))
/* 1369:     */         {
/* 1370:2189 */           prevFi.addRoutingNumber(rtn);
/* 1371:     */         }
/* 1372:     */         else
/* 1373:     */         {
/* 1374:2197 */           prevFi = fi;
/* 1375:2198 */           fis.add(prevFi);
/* 1376:2199 */           rows++;
/* 1377:     */         }
/* 1378:2201 */         if (rows >= maxRows) {
/* 1379:     */           break;
/* 1380:     */         }
/* 1381:     */       }
/* 1382:     */     }
/* 1383:     */     catch (Exception e)
/* 1384:     */     {
/* 1385:2207 */       e.printStackTrace();
/* 1386:2208 */       DebugLog.log(Level.SEVERE, "FinancialInstitutionAdapter get failed. Error: " + e.getMessage());
/* 1387:     */       
/* 1388:2210 */       throw new FinancialInstitutionException(e, 4, "Cannot get results back from LU_FinancialInstitution");
/* 1389:     */     }
/* 1390:     */     finally
/* 1391:     */     {
/* 1392:2216 */       if (results != null)
/* 1393:     */       {
/* 1394:2218 */         DBUtil.closeResultSet(results);
/* 1395:2219 */         results = null;
/* 1396:     */       }
/* 1397:2222 */       if (pStmt != null) {
/* 1398:2224 */         DBUtil.closeStatement(pStmt);
/* 1399:     */       }
/* 1400:     */     }
/* 1401:2228 */     return fis;
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   public static FinancialInstitutions get(String source, HashMap searchCriteria)
/* 1405:     */     throws FinancialInstitutionException
/* 1406:     */   {
/* 1407:2250 */     return get(source, searchCriteria, true);
/* 1408:     */   }
/* 1409:     */   
/* 1410:     */   public static FinancialInstitutions get(String source, HashMap searchCriteria, boolean isConjunctive)
/* 1411:     */     throws FinancialInstitutionException
/* 1412:     */   {
/* 1413:2273 */     throw new FinancialInstitutionException(13, "Method not yet implemented");
/* 1414:     */   }
/* 1415:     */   
/* 1416:     */   private static void addToCache(String cacheKey, String key, FinancialInstitution object)
/* 1417:     */   {
/* 1418:2288 */     SimpleCache cache = (SimpleCache)caches.get(cacheKey);
/* 1419:2289 */     synchronized (cache)
/* 1420:     */     {
/* 1421:2291 */       cache.put(key, object);
/* 1422:     */     }
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   private static FinancialInstitution getFromCache(String cacheKey, String key)
/* 1426:     */   {
/* 1427:2304 */     SimpleCache cache = (SimpleCache)caches.get(cacheKey);
/* 1428:2305 */     synchronized (cache)
/* 1429:     */     {
/* 1430:2307 */       return (FinancialInstitution)cache.get(key);
/* 1431:     */     }
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   private static void purgeCache()
/* 1435:     */   {
/* 1436:2317 */     SimpleCache cache = (SimpleCache)caches.get("ACHRTN");
/* 1437:2318 */     synchronized (cache)
/* 1438:     */     {
/* 1439:2320 */       cache.purge();
/* 1440:     */     }
/* 1441:2322 */     cache = (SimpleCache)caches.get("BIC");
/* 1442:2323 */     synchronized (cache)
/* 1443:     */     {
/* 1444:2325 */       cache.purge();
/* 1445:     */     }
/* 1446:     */   }
/* 1447:     */   
/* 1448:     */   private static String replaceSingleQuotes(String str)
/* 1449:     */   {
/* 1450:2339 */     if (str.indexOf('\'') >= 0)
/* 1451:     */     {
/* 1452:2340 */       StringBuffer s = new StringBuffer(str);
/* 1453:2341 */       int i = 0;
/* 1454:2342 */       while (i < s.length())
/* 1455:     */       {
/* 1456:2343 */         if (s.charAt(i) == '\'')
/* 1457:     */         {
/* 1458:2344 */           s = s.insert(i + 1, "'");
/* 1459:2345 */           i++;
/* 1460:     */         }
/* 1461:2347 */         i++;
/* 1462:     */       }
/* 1463:2349 */       str = s.toString();
/* 1464:     */     }
/* 1465:2351 */     return str;
/* 1466:     */   }
/* 1467:     */ }


/* Location:           D:\drops\jd\jars\ffiblcommon.jar
 * Qualified Name:     com.ffusion.banklookup.adapters.FinancialInstitutionAdapter
 * JD-Core Version:    0.7.0.1
 */