/*   1:    */ package com.ffusion.banksim.adapter;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.db.DBConnection;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import com.ffusion.util.db.DBUtil;
/*   6:    */ import java.sql.PreparedStatement;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ 
/*   9:    */ class BSRecordCounter
/*  10:    */ {
/*  11:    */   protected static final int TYPE_ACCOUNT = 1;
/*  12:    */   protected static final int TYPE_LOCKBOX = 2;
/*  13:    */   protected static final int TYPE_EXTENDABEAN = 3;
/*  14:    */   protected static final int EXTENDABEAN_ID = 0;
/*  15:    */   protected static final String LOCKBOX_CREDITITEM_INDEX = "LOCKBOX_CREDITITEM_INDEX";
/*  16:    */   protected static final String LOCKBOX_TRANSACTION_INDEX = "LOCKBOX_TRANSACTION_INDEX";
/*  17:    */   protected static final String DISBURSEMENT_TRANSACTION_INDEX = "DISBURSEMENT_TRANSACTION_INDEX";
/*  18:    */   protected static final String EXTENDABEAN_INDEX = "ExtendABeanIndex";
/*  19:    */   private static final String jdField_do = "INSERT INTO BS_RecordCounter( ObjectType, ObjectID, CounterName, NextIndex ) VALUES( ?, ?, ?, ? )";
/*  20:    */   private static final String jdField_if = "UPDATE BS_RecordCounter SET NextIndex=NextIndex+1 WHERE ObjectType=? AND ObjectID=? AND CounterName=?";
/*  21:    */   private static final String jdField_for = "DELETE FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?";
/*  22:    */   private static final String jdField_int = "SELECT NextIndex FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?";
/*  23:    */   private static final String jdField_try = "SELECT * FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?";
/*  24:    */   private static final String jdField_new = "SELECT NextIndex FROM BS_RecordCounter counter, BS_Account acc WHERE acc.AccountNumber=? AND acc.RoutingNum=? AND acc.AccountID = counter.ObjectID AND ObjectType=? AND CounterName=?";
/*  25:    */   private static final String a = "SELECT lockbox.LockboxID FROM BS_Lockbox lockbox, BS_Account acc WHERE acc.AccountID = ? AND acc.RoutingNum = ? AND lockbox.LockboxNumber = ? AND acc.AccountID = lockbox.AccountID";
/*  26:    */   
/*  27:    */   protected static long getIndex(DBConnection paramDBConnection, int paramInt1, int paramInt2, String paramString)
/*  28:    */     throws BSException
/*  29:    */   {
/*  30: 56 */     PreparedStatement localPreparedStatement = null;
/*  31: 57 */     ResultSet localResultSet = null;
/*  32:    */     try
/*  33:    */     {
/*  34: 59 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT NextIndex FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/*  35: 60 */       localPreparedStatement.setInt(1, paramInt1);
/*  36: 61 */       localPreparedStatement.setString(2, Integer.toString(paramInt2));
/*  37: 62 */       localPreparedStatement.setString(3, paramString);
/*  38: 63 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT NextIndex FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/*  39: 65 */       if (localResultSet.next())
/*  40:    */       {
/*  41: 66 */         long l1 = localResultSet.getLong(1);
/*  42: 67 */         DBUtil.closeResultSet(localResultSet);
/*  43: 68 */         return l1;
/*  44:    */       }
/*  45: 70 */       throw new BSException(1);
/*  46:    */     }
/*  47:    */     catch (Exception localException)
/*  48:    */     {
/*  49: 73 */       throw new BSException(1, localException.getMessage());
/*  50:    */     }
/*  51:    */     finally
/*  52:    */     {
/*  53: 76 */       if (localPreparedStatement != null) {
/*  54: 77 */         DBConnection.closeStatement(localPreparedStatement);
/*  55:    */       }
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected static long getIndex(DBConnection paramDBConnection, String paramString1, String paramString2, int paramInt, String paramString3)
/*  60:    */     throws BSException
/*  61:    */   {
/*  62: 86 */     PreparedStatement localPreparedStatement = null;
/*  63: 87 */     ResultSet localResultSet = null;
/*  64:    */     try
/*  65:    */     {
/*  66: 89 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT NextIndex FROM BS_RecordCounter counter, BS_Account acc WHERE acc.AccountNumber=? AND acc.RoutingNum=? AND acc.AccountID = counter.ObjectID AND ObjectType=? AND CounterName=?");
/*  67: 90 */       localPreparedStatement.setString(1, paramString1);
/*  68: 91 */       localPreparedStatement.setString(2, paramString2);
/*  69: 92 */       localPreparedStatement.setInt(3, paramInt);
/*  70: 93 */       localPreparedStatement.setString(4, paramString3);
/*  71: 94 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT NextIndex FROM BS_RecordCounter counter, BS_Account acc WHERE acc.AccountNumber=? AND acc.RoutingNum=? AND acc.AccountID = counter.ObjectID AND ObjectType=? AND CounterName=?");
/*  72:    */       long l1;
/*  73: 96 */       if (localResultSet.next())
/*  74:    */       {
/*  75: 97 */         l1 = localResultSet.getLong(1);
/*  76: 98 */         DBUtil.closeResultSet(localResultSet);
/*  77: 99 */         return l1;
/*  78:    */       }
/*  79:101 */       return -1L;
/*  80:    */     }
/*  81:    */     catch (Exception localException)
/*  82:    */     {
/*  83:104 */       return -1L;
/*  84:    */     }
/*  85:    */     finally
/*  86:    */     {
/*  87:107 */       if (localPreparedStatement != null) {
/*  88:108 */         DBConnection.closeStatement(localPreparedStatement);
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected static long getIndex(DBConnection paramDBConnection, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
/*  94:    */     throws BSException
/*  95:    */   {
/*  96:119 */     PreparedStatement localPreparedStatement = null;
/*  97:120 */     ResultSet localResultSet = null;
/*  98:    */     try
/*  99:    */     {
/* 100:122 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT lockbox.LockboxID FROM BS_Lockbox lockbox, BS_Account acc WHERE acc.AccountID = ? AND acc.RoutingNum = ? AND lockbox.LockboxNumber = ? AND acc.AccountID = lockbox.AccountID");
/* 101:123 */       localPreparedStatement.setString(1, paramString1);
/* 102:124 */       localPreparedStatement.setString(2, paramString2);
/* 103:125 */       localPreparedStatement.setString(3, paramString3);
/* 104:126 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT lockbox.LockboxID FROM BS_Lockbox lockbox, BS_Account acc WHERE acc.AccountID = ? AND acc.RoutingNum = ? AND lockbox.LockboxNumber = ? AND acc.AccountID = lockbox.AccountID");
/* 105:128 */       if (localResultSet.next())
/* 106:    */       {
/* 107:129 */         int i = localResultSet.getInt(1);
/* 108:130 */         DBUtil.closeResultSet(localResultSet);
/* 109:131 */         long l2 = getIndex(paramDBConnection, paramInt, i, paramString4);
/* 110:132 */         return l2;
/* 111:    */       }
/* 112:134 */       return -1L;
/* 113:    */     }
/* 114:    */     catch (Exception localException)
/* 115:    */     {
/* 116:137 */       throw new BSException(1, localException.getMessage());
/* 117:    */     }
/* 118:    */     finally
/* 119:    */     {
/* 120:139 */       if (localPreparedStatement != null) {
/* 121:140 */         DBConnection.closeStatement(localPreparedStatement);
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected static void addNewCounter(DBConnection paramDBConnection, int paramInt, String paramString1, String paramString2)
/* 127:    */     throws BSException
/* 128:    */   {
/* 129:150 */     PreparedStatement localPreparedStatement = null;
/* 130:    */     try
/* 131:    */     {
/* 132:152 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "INSERT INTO BS_RecordCounter( ObjectType, ObjectID, CounterName, NextIndex ) VALUES( ?, ?, ?, ? )");
/* 133:153 */       localPreparedStatement.setInt(1, paramInt);
/* 134:154 */       localPreparedStatement.setString(2, paramString1);
/* 135:155 */       localPreparedStatement.setString(3, paramString2);
/* 136:156 */       localPreparedStatement.setLong(4, 1L);
/* 137:157 */       DBConnection.executeUpdate(localPreparedStatement, "INSERT INTO BS_RecordCounter( ObjectType, ObjectID, CounterName, NextIndex ) VALUES( ?, ?, ?, ? )");
/* 138:    */     }
/* 139:    */     catch (Exception localException)
/* 140:    */     {
/* 141:159 */       throw new BSException(1, localException.getMessage());
/* 142:    */     }
/* 143:    */     finally
/* 144:    */     {
/* 145:161 */       if (localPreparedStatement != null) {
/* 146:162 */         DBConnection.closeStatement(localPreparedStatement);
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected static void deleteCounter(DBConnection paramDBConnection, int paramInt1, int paramInt2, String paramString)
/* 152:    */     throws BSException
/* 153:    */   {
/* 154:171 */     PreparedStatement localPreparedStatement = null;
/* 155:    */     try
/* 156:    */     {
/* 157:173 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "DELETE FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 158:174 */       localPreparedStatement.setInt(1, paramInt1);
/* 159:175 */       localPreparedStatement.setInt(2, paramInt2);
/* 160:176 */       localPreparedStatement.setString(3, paramString);
/* 161:177 */       DBConnection.executeUpdate(localPreparedStatement, "DELETE FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 162:    */     }
/* 163:    */     catch (Exception localException)
/* 164:    */     {
/* 165:179 */       throw new BSException(1, localException.getMessage());
/* 166:    */     }
/* 167:    */     finally
/* 168:    */     {
/* 169:181 */       if (localPreparedStatement != null) {
/* 170:182 */         DBConnection.closeStatement(localPreparedStatement);
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected static void incrementIndex(DBConnection paramDBConnection, int paramInt1, int paramInt2, String paramString)
/* 176:    */     throws BSException
/* 177:    */   {
/* 178:191 */     PreparedStatement localPreparedStatement = null;
/* 179:    */     try
/* 180:    */     {
/* 181:193 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "UPDATE BS_RecordCounter SET NextIndex=NextIndex+1 WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 182:194 */       localPreparedStatement.setInt(1, paramInt1);
/* 183:195 */       localPreparedStatement.setInt(2, paramInt2);
/* 184:196 */       localPreparedStatement.setString(3, paramString);
/* 185:197 */       DBConnection.executeUpdate(localPreparedStatement, "UPDATE BS_RecordCounter SET NextIndex=NextIndex+1 WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 186:    */     }
/* 187:    */     catch (Exception localException)
/* 188:    */     {
/* 189:199 */       throw new BSException(1, localException.getMessage());
/* 190:    */     }
/* 191:    */     finally
/* 192:    */     {
/* 193:201 */       if (localPreparedStatement != null) {
/* 194:202 */         DBConnection.closeStatement(localPreparedStatement);
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   protected static long getNextIndex(DBConnection paramDBConnection, int paramInt, String paramString1, String paramString2)
/* 200:    */     throws BSException
/* 201:    */   {
/* 202:249 */     PreparedStatement localPreparedStatement = null;
/* 203:250 */     long l1 = 0L;
/* 204:251 */     ResultSet localResultSet = null;
/* 205:    */     try
/* 206:    */     {
/* 207:253 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "UPDATE BS_RecordCounter SET NextIndex=NextIndex+1 WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 208:254 */       localPreparedStatement.setInt(1, paramInt);
/* 209:255 */       localPreparedStatement.setString(2, paramString1);
/* 210:256 */       localPreparedStatement.setString(3, paramString2);
/* 211:257 */       DBConnection.executeUpdate(localPreparedStatement, "UPDATE BS_RecordCounter SET NextIndex=NextIndex+1 WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 212:    */       
/* 213:    */ 
/* 214:260 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT NextIndex FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 215:261 */       localPreparedStatement.setInt(1, paramInt);
/* 216:262 */       localPreparedStatement.setString(2, paramString1);
/* 217:263 */       localPreparedStatement.setString(3, paramString2);
/* 218:264 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT NextIndex FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 219:    */       long l2;
/* 220:266 */       if (localResultSet.next())
/* 221:    */       {
/* 222:267 */         l1 = localResultSet.getLong(1);
/* 223:268 */         DBUtil.closeResultSet(localResultSet);
/* 224:    */       }
/* 225:    */       else
/* 226:    */       {
/* 227:270 */         addNewCounter(paramDBConnection, paramInt, paramString1, paramString2);
/* 228:271 */         return 1L;
/* 229:    */       }
/* 230:275 */       return l1;
/* 231:    */     }
/* 232:    */     catch (Exception localException)
/* 233:    */     {
/* 234:277 */       throw new BSException(1, localException.getMessage());
/* 235:    */     }
/* 236:    */     finally
/* 237:    */     {
/* 238:279 */       if (localPreparedStatement != null) {
/* 239:280 */         DBConnection.closeStatement(localPreparedStatement);
/* 240:    */       }
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   protected static boolean counterExists(DBConnection paramDBConnection, int paramInt1, int paramInt2, String paramString)
/* 245:    */     throws BSException
/* 246:    */   {
/* 247:289 */     PreparedStatement localPreparedStatement = null;
/* 248:290 */     ResultSet localResultSet = null;
/* 249:    */     try
/* 250:    */     {
/* 251:292 */       localPreparedStatement = paramDBConnection.prepareStatement(paramDBConnection, "SELECT * FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 252:293 */       localPreparedStatement.setInt(1, paramInt1);
/* 253:294 */       localPreparedStatement.setInt(2, paramInt2);
/* 254:295 */       localPreparedStatement.setString(3, paramString);
/* 255:296 */       localResultSet = DBConnection.executeQuery(localPreparedStatement, "SELECT * FROM BS_RecordCounter WHERE ObjectType=? AND ObjectID=? AND CounterName=?");
/* 256:    */       boolean bool;
/* 257:298 */       if (localResultSet.next())
/* 258:    */       {
/* 259:299 */         DBUtil.closeResultSet(localResultSet);
/* 260:300 */         return true;
/* 261:    */       }
/* 262:302 */       DBUtil.closeResultSet(localResultSet);
/* 263:303 */       return false;
/* 264:    */     }
/* 265:    */     catch (Exception localException)
/* 266:    */     {
/* 267:307 */       throw new BSException(1, localException.getMessage());
/* 268:    */     }
/* 269:    */     finally
/* 270:    */     {
/* 271:310 */       if (localPreparedStatement != null) {
/* 272:311 */         DBConnection.closeStatement(localPreparedStatement);
/* 273:    */       }
/* 274:    */     }
/* 275:    */   }
/* 276:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.adapter.BSRecordCounter
 * JD-Core Version:    0.7.0.1
 */