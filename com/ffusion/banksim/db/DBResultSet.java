/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   4:    */ import com.ffusion.util.logging.DebugLog;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.sql.Blob;
/*   7:    */ import java.sql.Clob;
/*   8:    */ import java.sql.PreparedStatement;
/*   9:    */ import java.sql.ResultSet;
/*  10:    */ import java.sql.ResultSetMetaData;
/*  11:    */ import java.sql.SQLException;
/*  12:    */ import java.util.Arrays;
/*  13:    */ import java.util.logging.Level;
/*  14:    */ 
/*  15:    */ public class DBResultSet
/*  16:    */ {
/*  17:    */   private ResultSet jdField_for;
/*  18:    */   private PreparedStatement jdField_byte;
/*  19:    */   private int jdField_int;
/*  20:    */   private boolean a;
/*  21:    */   private ResultSetMetaData jdField_do;
/*  22:    */   private int jdField_try;
/*  23:    */   private int jdField_new;
/*  24:    */   private BSDBParams jdField_if;
/*  25:    */   
/*  26:    */   DBResultSet(PreparedStatement paramPreparedStatement, BSDBParams paramBSDBParams)
/*  27:    */   {
/*  28: 27 */     this.a = true;
/*  29: 28 */     this.jdField_for = null;
/*  30: 29 */     this.jdField_do = null;
/*  31: 30 */     this.jdField_byte = paramPreparedStatement;
/*  32: 31 */     this.jdField_if = paramBSDBParams;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void finalize()
/*  36:    */     throws SQLException
/*  37:    */   {
/*  38: 37 */     if (!this.a) {
/*  39: 38 */       close();
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final void close()
/*  44:    */     throws SQLException
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 49 */       if (this.jdField_for != null)
/*  49:    */       {
/*  50: 50 */         this.jdField_for.close();
/*  51: 51 */         this.jdField_for = null;
/*  52: 52 */         this.jdField_do = null;
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (SQLException localSQLException)
/*  56:    */     {
/*  57: 55 */       if ((!this.jdField_if.isHA()) || ((!localSQLException.getSQLState().equals("JZ0F2")) && (!localSQLException.getSQLState().equals("JZ006")))) {
/*  58: 60 */         throw localSQLException;
/*  59:    */       }
/*  60:    */     }
/*  61: 64 */     this.a = true;
/*  62: 65 */     this.jdField_try = 0;
/*  63: 66 */     this.jdField_new = 0;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final void open(Object[] paramArrayOfObject)
/*  67:    */     throws SQLException
/*  68:    */   {
/*  69: 75 */     if (paramArrayOfObject != null) {
/*  70: 76 */       DebugLog.log(Level.INFO, "com.ffusion.banksim.db.DBResultSet.open()\ninArgs[]: " + Arrays.asList(paramArrayOfObject).toString());
/*  71:    */     }
/*  72: 80 */     for (int i = 0;; i++) {
/*  73:    */       try
/*  74:    */       {
/*  75: 82 */         if (this.jdField_for != null)
/*  76:    */         {
/*  77: 83 */           this.jdField_for.close();
/*  78: 84 */           this.jdField_for = null;
/*  79: 85 */           this.jdField_do = null;
/*  80:    */         }
/*  81: 87 */         DBSqlUtils.a(this.jdField_byte, paramArrayOfObject, this.jdField_if);
/*  82: 88 */         a();
/*  83: 89 */         return;
/*  84:    */       }
/*  85:    */       catch (SQLException localSQLException)
/*  86:    */       {
/*  87: 91 */         a(localSQLException, false, i);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final void open()
/*  93:    */     throws SQLException
/*  94:    */   {
/*  95: 96 */     open(null);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void a()
/*  99:    */     throws SQLException
/* 100:    */   {
/* 101:101 */     this.jdField_for = this.jdField_byte.executeQuery();
/* 102:102 */     this.jdField_do = this.jdField_for.getMetaData();
/* 103:103 */     this.jdField_int = this.jdField_do.getColumnCount();
/* 104:104 */     this.a = false;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final boolean getNextRow()
/* 108:    */     throws SQLException
/* 109:    */   {
/* 110:115 */     for (int i = 0;; i++) {
/* 111:    */       try
/* 112:    */       {
/* 113:117 */         this.jdField_new = 0;
/* 114:119 */         if (this.jdField_for != null)
/* 115:    */         {
/* 116:120 */           boolean bool = this.jdField_for.next();
/* 117:121 */           this.jdField_try += 1;
/* 118:122 */           return bool;
/* 119:    */         }
/* 120:124 */         return false;
/* 121:    */       }
/* 122:    */       catch (SQLException localSQLException)
/* 123:    */       {
/* 124:126 */         a(localSQLException, false, i);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final ResultSetMetaData getResultSetMetaData()
/* 130:    */   {
/* 131:136 */     return this.jdField_do;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public final String[] getColumnsAsArray()
/* 135:    */     throws SQLException
/* 136:    */   {
/* 137:147 */     String[] arrayOfString = new String[this.jdField_int];
/* 138:149 */     for (int i = 0;; i++) {
/* 139:    */       try
/* 140:    */       {
/* 141:151 */         for (int j = 0; j < this.jdField_int; j++) {
/* 142:152 */           arrayOfString[j] = getColumnString(j + 1);
/* 143:    */         }
/* 144:154 */         return arrayOfString;
/* 145:    */       }
/* 146:    */       catch (SQLException localSQLException)
/* 147:    */       {
/* 148:156 */         a(localSQLException, false, i);
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final String getColumnString(int paramInt)
/* 154:    */     throws SQLException
/* 155:    */   {
/* 156:170 */     for (int i = 0;; i++) {
/* 157:    */       try
/* 158:    */       {
/* 159:172 */         this.jdField_new = paramInt;
/* 160:173 */         String str = this.jdField_for.getString(paramInt);
/* 161:175 */         if (!this.jdField_for.wasNull()) {
/* 162:177 */           if (this.jdField_do.getColumnType(paramInt) != 1) {}
/* 163:    */         }
/* 164:178 */         return str.trim();
/* 165:    */       }
/* 166:    */       catch (SQLException localSQLException)
/* 167:    */       {
/* 168:183 */         a(localSQLException, false, i);
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public final String getColumnStringNoTrim(int paramInt)
/* 174:    */     throws SQLException
/* 175:    */   {
/* 176:203 */     for (int i = 0;; i++) {
/* 177:    */       try
/* 178:    */       {
/* 179:205 */         this.jdField_new = paramInt;
/* 180:206 */         return this.jdField_for.getString(paramInt);
/* 181:    */       }
/* 182:    */       catch (SQLException localSQLException)
/* 183:    */       {
/* 184:210 */         a(localSQLException, false, i);
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public final String getColumnClobAsString(int paramInt)
/* 190:    */     throws SQLException
/* 191:    */   {
/* 192:224 */     if (this.jdField_if.getConnectionType() != 4) {
/* 193:228 */       return getColumnString(paramInt);
/* 194:    */     }
/* 195:231 */     for (int i = 0;; i++) {
/* 196:    */       try
/* 197:    */       {
/* 198:233 */         this.jdField_new = paramInt;
/* 199:234 */         Clob localClob = this.jdField_for.getClob(paramInt);
/* 200:235 */         if (this.jdField_for.wasNull()) {
/* 201:235 */           return null;
/* 202:    */         }
/* 203:238 */         return localClob.getSubString(1L, (int)localClob.length());
/* 204:    */       }
/* 205:    */       catch (SQLException localSQLException)
/* 206:    */       {
/* 207:242 */         a(localSQLException, false, i);
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public final byte[] getColumnBlobAsBytes(int paramInt)
/* 213:    */     throws SQLException
/* 214:    */   {
/* 215:257 */     if (this.jdField_if.getConnectionType() != 4) {
/* 216:261 */       return getColumnBytes(paramInt);
/* 217:    */     }
/* 218:265 */     for (int i = 0;; i++) {
/* 219:    */       try
/* 220:    */       {
/* 221:267 */         this.jdField_new = paramInt;
/* 222:268 */         Blob localBlob = this.jdField_for.getBlob(paramInt);
/* 223:269 */         if (this.jdField_for.wasNull()) {
/* 224:269 */           return null;
/* 225:    */         }
/* 226:273 */         return localBlob.getBytes(1L, (int)localBlob.length());
/* 227:    */       }
/* 228:    */       catch (SQLException localSQLException)
/* 229:    */       {
/* 230:277 */         a(localSQLException, false, i);
/* 231:    */       }
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public final int getColumnInt(int paramInt)
/* 236:    */     throws SQLException
/* 237:    */   {
/* 238:291 */     for (int i = 0;; i++) {
/* 239:    */       try
/* 240:    */       {
/* 241:293 */         this.jdField_new = paramInt;
/* 242:294 */         return this.jdField_for.getInt(paramInt);
/* 243:    */       }
/* 244:    */       catch (SQLException localSQLException)
/* 245:    */       {
/* 246:296 */         a(localSQLException, false, i);
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public final long getColumnLong(int paramInt)
/* 252:    */     throws SQLException
/* 253:    */   {
/* 254:309 */     for (int i = 0;; i++) {
/* 255:    */       try
/* 256:    */       {
/* 257:311 */         this.jdField_new = paramInt;
/* 258:312 */         return this.jdField_for.getLong(paramInt);
/* 259:    */       }
/* 260:    */       catch (SQLException localSQLException)
/* 261:    */       {
/* 262:314 */         a(localSQLException, false, i);
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public final boolean getColumnBool(int paramInt)
/* 268:    */     throws SQLException
/* 269:    */   {
/* 270:328 */     for (int i = 0;; i++) {
/* 271:    */       try
/* 272:    */       {
/* 273:330 */         this.jdField_new = paramInt;
/* 274:331 */         return this.jdField_for.getBoolean(paramInt);
/* 275:    */       }
/* 276:    */       catch (SQLException localSQLException)
/* 277:    */       {
/* 278:333 */         a(localSQLException, false, i);
/* 279:    */       }
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public final byte[] getColumnBytes(int paramInt)
/* 284:    */     throws SQLException
/* 285:    */   {
/* 286:347 */     for (int i = 0;; i++) {
/* 287:    */       try
/* 288:    */       {
/* 289:349 */         this.jdField_new = paramInt;
/* 290:350 */         return this.jdField_for.getBytes(paramInt);
/* 291:    */       }
/* 292:    */       catch (SQLException localSQLException)
/* 293:    */       {
/* 294:352 */         a(localSQLException, false, i);
/* 295:    */       }
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public final long getColumnBigIntAsLong(int paramInt)
/* 300:    */     throws SQLException
/* 301:    */   {
/* 302:367 */     for (int i = 0;; i++) {
/* 303:    */       try
/* 304:    */       {
/* 305:369 */         this.jdField_new = paramInt;
/* 306:370 */         BigDecimal localBigDecimal = this.jdField_for.getBigDecimal(paramInt, 0);
/* 307:371 */         if (this.jdField_for.wasNull()) {
/* 308:372 */           return 0L;
/* 309:    */         }
/* 310:374 */         return localBigDecimal.longValue();
/* 311:    */       }
/* 312:    */       catch (SQLException localSQLException)
/* 313:    */       {
/* 314:376 */         a(localSQLException, false, i);
/* 315:    */       }
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   public final Clob getColumnClob(int paramInt)
/* 320:    */     throws SQLException
/* 321:    */   {
/* 322:387 */     for (int i = 0;; i++) {
/* 323:    */       try
/* 324:    */       {
/* 325:389 */         this.jdField_new = paramInt;
/* 326:390 */         return this.jdField_for.getClob(paramInt);
/* 327:    */       }
/* 328:    */       catch (SQLException localSQLException)
/* 329:    */       {
/* 330:392 */         a(localSQLException, false, i);
/* 331:    */       }
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   public final Blob getColumnBlob(int paramInt)
/* 336:    */     throws SQLException
/* 337:    */   {
/* 338:402 */     for (int i = 0;; i++) {
/* 339:    */       try
/* 340:    */       {
/* 341:404 */         this.jdField_new = paramInt;
/* 342:405 */         return this.jdField_for.getBlob(paramInt);
/* 343:    */       }
/* 344:    */       catch (SQLException localSQLException)
/* 345:    */       {
/* 346:407 */         a(localSQLException, false, i);
/* 347:    */       }
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   public final boolean wasNull()
/* 352:    */     throws SQLException
/* 353:    */   {
/* 354:414 */     for (int i = 0;; i++) {
/* 355:    */       try
/* 356:    */       {
/* 357:416 */         return this.jdField_for.wasNull();
/* 358:    */       }
/* 359:    */       catch (SQLException localSQLException)
/* 360:    */       {
/* 361:418 */         a(localSQLException, true, i);
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public final BSDBParams getConnectionParams()
/* 367:    */   {
/* 368:428 */     return this.jdField_if;
/* 369:    */   }
/* 370:    */   
/* 371:    */   private void a(SQLException paramSQLException, boolean paramBoolean, int paramInt)
/* 372:    */     throws SQLException
/* 373:    */   {
/* 374:438 */     if ((!this.jdField_if.isHA()) || ((!paramSQLException.getSQLState().equals("JZ0F2")) && (!paramSQLException.getSQLState().equals("JZ006")))) {
/* 375:443 */       throw paramSQLException;
/* 376:    */     }
/* 377:446 */     if (paramInt >= 3) {
/* 378:448 */       throw paramSQLException;
/* 379:    */     }
/* 380:452 */     int i = this.jdField_try;
/* 381:453 */     int j = this.jdField_new;
/* 382:    */     
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:458 */     a();
/* 387:461 */     while (i != this.jdField_try) {
/* 388:462 */       if (!getNextRow()) {
/* 389:465 */         throw paramSQLException;
/* 390:    */       }
/* 391:    */     }
/* 392:470 */     if ((paramBoolean) && (j > 0)) {
/* 393:471 */       this.jdField_for.getString(j);
/* 394:    */     }
/* 395:    */   }
/* 396:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBResultSet
 * JD-Core Version:    0.7.0.1
 */