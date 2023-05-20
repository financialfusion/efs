/*   1:    */ package com.ffusion.banklookup.datasource.adapters;
/*   2:    */ 
/*   3:    */ import com.ffusion.banklookup.FinancialInstitutionException;
/*   4:    */ import com.ffusion.banklookup.adapters.FinancialInstitutionAdapter;
/*   5:    */ import com.ffusion.banklookup.adapters.GenericTableAdapter;
/*   6:    */ import com.ffusion.banklookup.adapters.GenericTableAdapterConsts;
/*   7:    */ import com.ffusion.banklookup.beans.FinancialInstitution;
/*   8:    */ import com.ffusion.banklookup.beans.FinancialInstitutions;
/*   9:    */ import com.ffusion.util.db.DBUtil;
/*  10:    */ import com.ffusion.util.logging.DebugLog;
/*  11:    */ import java.sql.Connection;
/*  12:    */ import java.sql.PreparedStatement;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.logging.Level;
/*  15:    */ 
/*  16:    */ public class FedWireAdapter
/*  17:    */   implements GenericTableAdapterConsts, FedWireAdapterConsts
/*  18:    */ {
/*  19: 39 */   private static final String[] KEYS = { "InstitutionId" };
/*  20:    */   private static final String SEQUENCE_NAME = "FEDWIRE_ID";
/*  21: 41 */   private static final String[][] KEYSTOCHECK = { { "RoutingNumber", "City", "StateCode" } };
/*  22:    */   private static final String NULL_ROUTING_NUMBER = "000000000";
/*  23:    */   private static final String SELECT_FOR_MERGE = "SELECT InstitutionId,RoutingNumber,TelegraphicName,CustomerName,City,StateCode,FundsTransferStatus,FundsSettlementStatus,SecuritiesTransferStatus,ChangeDate FROM LU_FedWire WHERE InstitutionId>=? AND InstitutionId<=?";
/*  24:    */   private static final String SELECT_TO_GET_MIN_VALUE = "SELECT MIN(InstitutionId) FROM LU_FedWire";
/*  25:    */   private static final String SELECT_TO_GET_MAX_VALUE = "SELECT MAX(InstitutionId) FROM LU_FedWire";
/*  26:    */   private static final String DELETE_ROWS = "DELETE FROM LU_FedWire WHERE InstitutionId>=? AND InstitutionId<=?";
/*  27:    */   private static final String SELECT_FOR_PURGING = "SELECT RoutingNumber FROM LU_FedWire WHERE InstitutionId>=? AND InstitutionId<=?";
/*  28: 68 */   private static GenericTableAdapter gta = new GenericTableAdapter();
/*  29: 69 */   private static boolean isInitialized = false;
/*  30:    */   
/*  31:    */   public static void initialize()
/*  32:    */     throws FinancialInstitutionException
/*  33:    */   {
/*  34: 80 */     if (!isInitialized)
/*  35:    */     {
/*  36: 82 */       gta.initialize(null, "LU_FedWire", FedWireAdapterConsts.COLUMNS, KEYS, true, "FEDWIRE_ID", KEYSTOCHECK);
/*  37:    */       
/*  38: 84 */       FinancialInstitutionAdapter.initialize(null);
/*  39: 85 */       isInitialized = true;
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void insert(HashMap data, HashMap extra)
/*  44:    */     throws FinancialInstitutionException
/*  45:    */   {
/*  46:103 */     insert(data, extra, true);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void insert(HashMap data, HashMap extra, boolean deep)
/*  50:    */     throws FinancialInstitutionException
/*  51:    */   {
/*  52:121 */     HashMap[] rows = { data };
/*  53:122 */     insert(rows, extra, deep);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void insert(HashMap[] rows, HashMap extra, boolean deep)
/*  57:    */     throws FinancialInstitutionException
/*  58:    */   {
/*  59:145 */     checkInitialization();
/*  60:146 */     Connection connection = null;
/*  61:    */     try
/*  62:    */     {
/*  63:149 */       connection = GenericTableAdapter.getConnection();
/*  64:150 */       for (int i = 0; i < rows.length; i++)
/*  65:    */       {
/*  66:152 */         HashMap row = rows[i];
/*  67:153 */         insert(row, extra, deep, connection);
/*  68:    */       }
/*  69:155 */       connection.commit();
/*  70:    */     }
/*  71:    */     catch (Exception e)
/*  72:    */     {
/*  73:159 */       DBUtil.rollback(connection);
/*  74:160 */       if ((e instanceof FinancialInstitutionException)) {
/*  75:161 */         throw ((FinancialInstitutionException)e);
/*  76:    */       }
/*  77:163 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the FedWire table: " + e.getMessage());
/*  78:    */     }
/*  79:    */     finally
/*  80:    */     {
/*  81:170 */       GenericTableAdapter.releaseConnection(connection);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static GenericTableAdapter getGenericTableAdapter()
/*  86:    */   {
/*  87:179 */     return gta;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void insertData(HashMap data, HashMap extra, boolean deep, Connection connection)
/*  91:    */   {
/*  92:195 */     HashMap[] rows = { data };
/*  93:    */     try
/*  94:    */     {
/*  95:197 */       insert(rows, extra, deep, connection);
/*  96:    */     }
/*  97:    */     catch (Throwable t)
/*  98:    */     {
/*  99:199 */       String customerName = (String)data.get("CustomerName");
/* 100:200 */       DebugLog.log(Level.WARNING, "FedWireAdapter.insertData failed for customer: " + customerName + t.getMessage());
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void insert(HashMap[] rows, HashMap extra, boolean deep, Connection connection)
/* 105:    */     throws FinancialInstitutionException
/* 106:    */   {
/* 107:    */     
/* 108:    */     try
/* 109:    */     {
/* 110:229 */       for (int i = 0; i < rows.length; i++)
/* 111:    */       {
/* 112:231 */         HashMap row = rows[i];
/* 113:232 */         insert(row, extra, deep, connection);
/* 114:    */       }
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:237 */       if ((e instanceof FinancialInstitutionException)) {
/* 119:238 */         throw ((FinancialInstitutionException)e);
/* 120:    */       }
/* 121:240 */       throw new FinancialInstitutionException(e, 5, "Cannot update data into the FedWire table: " + e.getMessage());
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void deleteByID(int data)
/* 126:    */     throws FinancialInstitutionException
/* 127:    */   {
/* 128:258 */     deleteByID(data, true);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static void deleteByID(int data, boolean deep)
/* 132:    */     throws FinancialInstitutionException
/* 133:    */   {
/* 134:273 */     if (data <= 0) {
/* 135:274 */       return;
/* 136:    */     }
/* 137:275 */     Connection connection = null;
/* 138:    */     try
/* 139:    */     {
/* 140:278 */       connection = GenericTableAdapter.getConnection();
/* 141:279 */       deleteByID(data, deep, connection);
/* 142:280 */       connection.commit();
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:284 */       if (connection != null) {
/* 147:285 */         DBUtil.rollback(connection);
/* 148:    */       }
/* 149:    */     }
/* 150:    */     finally
/* 151:    */     {
/* 152:289 */       GenericTableAdapter.releaseConnection(connection);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void purge()
/* 157:    */     throws FinancialInstitutionException
/* 158:    */   {
/* 159:302 */     purge(2147483646);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void purge(int batchsize)
/* 163:    */     throws FinancialInstitutionException
/* 164:    */   {
/* 165:315 */     checkInitialization();
/* 166:316 */     Connection connection = null;
/* 167:317 */     PreparedStatement pStmt = null;
/* 168:    */     try
/* 169:    */     {
/* 170:320 */       connection = GenericTableAdapter.getConnection();
/* 171:321 */       int minValue = gta.getKeyValue(false, connection);
/* 172:322 */       int maxValue = gta.getKeyValue(true, connection);
/* 173:323 */       for (int i = minValue; i <= maxValue + batchsize; i += batchsize)
/* 174:    */       {
/* 175:326 */         Integer[] args = { new Integer(i), new Integer(i + batchsize) };
/* 176:327 */         FinancialInstitutionAdapter.purgeWireRoutingNumbers("SELECT RoutingNumber FROM LU_FedWire WHERE InstitutionId>=? AND InstitutionId<=?", args, connection);
/* 177:328 */         pStmt = DBUtil.prepareStatement(connection, "DELETE FROM LU_FedWire WHERE InstitutionId>=? AND InstitutionId<=?");
/* 178:329 */         GenericTableAdapter.fillParameters(pStmt, args);
/* 179:330 */         DBUtil.executeUpdate(pStmt, "DELETE FROM LU_FedWire WHERE InstitutionId>=? AND InstitutionId<=?");
/* 180:331 */         connection.commit();
/* 181:    */       }
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:    */       try
/* 186:    */       {
/* 187:337 */         connection.rollback();
/* 188:    */       }
/* 189:    */       catch (Exception ex) {}
/* 190:341 */       DebugLog.log(Level.SEVERE, "FedWireAdapter purge failed. Error: " + e.getMessage());
/* 191:342 */       if ((e instanceof FinancialInstitutionException)) {
/* 192:343 */         throw ((FinancialInstitutionException)e);
/* 193:    */       }
/* 194:344 */       throw new FinancialInstitutionException(e, 9, "Cannot purge data: " + e.getMessage());
/* 195:    */     }
/* 196:    */     finally
/* 197:    */     {
/* 198:350 */       if (pStmt != null) {
/* 199:352 */         DBUtil.closeStatement(pStmt);
/* 200:    */       }
/* 201:355 */       GenericTableAdapter.releaseConnection(connection);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static void insert(HashMap data, HashMap extra, boolean deep, Connection connection)
/* 206:    */     throws FinancialInstitutionException
/* 207:    */   {
/* 208:386 */     gta.insertRow(data, extra, connection);
/* 209:387 */     if (!deep) {
/* 210:388 */       return;
/* 211:    */     }
/* 212:390 */     String routingNumber = (String)data.get("RoutingNumber");
/* 213:391 */     String customerName = (String)data.get("CustomerName");
/* 214:392 */     String city = (String)data.get("City");
/* 215:393 */     String stateCode = (String)data.get("StateCode");
/* 216:394 */     String fundsTransferStatus = (String)data.get("FundsTransferStatus");
/* 217:395 */     String fundsSettlementStatus = (String)data.get("FundsSettlementStatus");
/* 218:396 */     String securitiesTransferStatus = (String)data.get("SecuritiesTransferStatus");
/* 219:    */     
/* 220:398 */     updateFinancialInstitution(0, routingNumber, customerName, city, stateCode, fundsTransferStatus, fundsSettlementStatus, securitiesTransferStatus, connection);
/* 221:    */   }
/* 222:    */   
/* 223:    */   private static void updateFinancialInstitution(int id, String routingNumber, String customerName, String city, String stateCode, String fundsTransferStatus, String fundsSettlementStatus, String securitiesTransferStatus, Connection connection)
/* 224:    */     throws FinancialInstitutionException
/* 225:    */   {
/* 226:    */     try
/* 227:    */     {
/* 228:440 */       if (fundsTransferStatus.toUpperCase().equals("Y"))
/* 229:    */       {
/* 230:442 */         if (routingNumber != null) {
/* 231:443 */           routingNumber = routingNumber.trim().toUpperCase();
/* 232:    */         }
/* 233:444 */         if (customerName != null) {
/* 234:445 */           customerName = customerName.trim().toUpperCase();
/* 235:    */         }
/* 236:446 */         if (city != null) {
/* 237:447 */           city = city.trim().toUpperCase();
/* 238:    */         }
/* 239:448 */         if (stateCode != null) {
/* 240:449 */           stateCode = stateCode.trim().toUpperCase();
/* 241:    */         }
/* 242:452 */         FinancialInstitution fi = new FinancialInstitution();
/* 243:453 */         fi.setWireRoutingNumberForSearch(routingNumber);
/* 244:454 */         fi.setInstitutionName(customerName);
/* 245:455 */         fi.setCity(city);
/* 246:456 */         fi.setStateCode(stateCode);
/* 247:458 */         if (!routingNumber.equals("000000000"))
/* 248:    */         {
/* 249:461 */           FinancialInstitutions fis = FinancialInstitutionAdapter.get(fi, true, 1, connection);
/* 250:465 */           if (fis.size() == 1)
/* 251:    */           {
/* 252:468 */             fi = (FinancialInstitution)fis.get(0);
/* 253:469 */             updateFinancialInstitution(fi, id, routingNumber, customerName, city, stateCode, fundsTransferStatus, fundsSettlementStatus, securitiesTransferStatus, connection);
/* 254:    */             
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:478 */             return;
/* 263:    */           }
/* 264:    */         }
/* 265:483 */         fi = new FinancialInstitution();
/* 266:484 */         if (!routingNumber.equals("000000000")) {
/* 267:485 */           fi.setWireRoutingNumber(routingNumber);
/* 268:    */         } else {
/* 269:487 */           return;
/* 270:    */         }
/* 271:488 */         fi.setInstitutionName(customerName);
/* 272:489 */         fi.setCity(city);
/* 273:490 */         fi.setStateCode(stateCode);
/* 274:491 */         fi.setCountry("UNITED STATES");
/* 275:492 */         FinancialInstitutionAdapter.insert(fi, connection);
/* 276:    */       }
/* 277:    */     }
/* 278:    */     catch (Exception e)
/* 279:    */     {
/* 280:497 */       e.printStackTrace();
/* 281:    */       
/* 282:499 */       DebugLog.log(Level.SEVERE, "+++ FedWireAdapter.synchronize failed. Error: " + e.getMessage());
/* 283:500 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data: " + e.getMessage());
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   private static void updateFinancialInstitution(FinancialInstitution fi, int id, String routingNumber, String customerName, String city, String stateCode, String fundsTransferStatus, String fundsSettlementStatus, String securitiesTransferStatus, Connection connection)
/* 288:    */     throws FinancialInstitutionException
/* 289:    */   {
/* 290:    */     try
/* 291:    */     {
/* 292:544 */       if (fundsTransferStatus.toUpperCase().equals("Y"))
/* 293:    */       {
/* 294:546 */         boolean shouldUpdate = false;
/* 295:547 */         String val = null;
/* 296:548 */         val = fi.getInstitutionName();
/* 297:549 */         if (((val != null) && (!val.equals(customerName))) || ((val == null) && (customerName != null) && (!customerName.equals(""))))
/* 298:    */         {
/* 299:553 */           fi.setInstitutionName(customerName);
/* 300:554 */           shouldUpdate = true;
/* 301:    */         }
/* 302:557 */         val = fi.getCity();
/* 303:558 */         if (((val != null) && (!val.equals(city))) || ((val == null) && (city != null) && (!city.equals(""))))
/* 304:    */         {
/* 305:561 */           fi.setCity(city);
/* 306:562 */           shouldUpdate = true;
/* 307:    */         }
/* 308:565 */         val = fi.getStateCode();
/* 309:566 */         if (((val != null) && (!val.equals(stateCode))) || ((val == null) && (stateCode != null) && (!stateCode.equals(""))))
/* 310:    */         {
/* 311:569 */           fi.setStateCode(stateCode);
/* 312:570 */           shouldUpdate = true;
/* 313:    */         }
/* 314:573 */         val = fi.getCountry();
/* 315:574 */         if ((val != null) && (!val.equals("UNITED STATES")))
/* 316:    */         {
/* 317:576 */           fi.setCountry("UNITED STATES");
/* 318:577 */           shouldUpdate = true;
/* 319:    */         }
/* 320:580 */         val = fi.getWireRoutingNumber();
/* 321:581 */         if ((val != null) && (!val.equals(routingNumber)) && (!routingNumber.equals("000000000")))
/* 322:    */         {
/* 323:582 */           fi.setWireRoutingNumber(routingNumber);
/* 324:583 */           shouldUpdate = true;
/* 325:    */         }
/* 326:586 */         if (shouldUpdate) {
/* 327:587 */           FinancialInstitutionAdapter.update(fi, connection);
/* 328:    */         }
/* 329:    */       }
/* 330:    */     }
/* 331:    */     catch (Exception e)
/* 332:    */     {
/* 333:592 */       e.printStackTrace();
/* 334:593 */       DebugLog.log(Level.SEVERE, "+++ FedWireAdapter.updateFinancialInstitution failed. Error: " + e.getMessage());
/* 335:594 */       throw new FinancialInstitutionException(e, 8, "Cannot synchronize data");
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   private static void deleteByID(int data, boolean deep, Connection connection)
/* 340:    */     throws FinancialInstitutionException
/* 341:    */   {
/* 342:613 */     if (data <= 0) {
/* 343:614 */       return;
/* 344:    */     }
/* 345:615 */     HashMap map = new HashMap(1);
/* 346:616 */     map.put("InstitutionId", Integer.toString(data));
/* 347:617 */     if (deep)
/* 348:    */     {
/* 349:618 */       String[] args = { Integer.toString(data) };
/* 350:619 */       String[] row = gta.getRow(args, null);
/* 351:620 */       FinancialInstitutionAdapter.deleteWireRoutingNumber(row[1], connection);
/* 352:    */     }
/* 353:622 */     gta.deleteRow(map, null, connection);
/* 354:    */   }
/* 355:    */   
/* 356:    */   private static void checkInitialization()
/* 357:    */     throws FinancialInstitutionException
/* 358:    */   {
/* 359:634 */     if (!isInitialized) {
/* 360:636 */       throw new FinancialInstitutionException(2, "FedWireAdapter not initialized");
/* 361:    */     }
/* 362:    */   }
/* 363:    */ }


/* Location:           D:\drops\jd\jars\ffiblcustom.jar
 * Qualified Name:     com.ffusion.banklookup.datasource.adapters.FedWireAdapter
 * JD-Core Version:    0.7.0.1
 */