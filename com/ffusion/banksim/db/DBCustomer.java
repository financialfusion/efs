/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSException;
/*   4:    */ import com.ffusion.beans.accounts.Account;
/*   5:    */ import com.ffusion.beans.user.User;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ 
/*   9:    */ public class DBCustomer
/*  10:    */ {
/*  11:    */   private static final String jdField_for = "select CustomerID from BS_Customer where UserID=?";
/*  12:    */   private static final String jdField_int = "select CustomerID from BS_Customer where CustomerID=?";
/*  13:    */   private static final String jdField_new = "select CustomerID, UserID from BS_Customer where UserID=?";
/*  14:    */   private static final String jdField_do = "insert into BS_Customer( CustomerID, UserID, Password, FirstName, MiddleName, LastName, Address1, Address2, City, State, PostalCode, Country, DayPhone, EveningPhone, EMailAddress ) values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
/*  15:    */   private static final String jdField_try = "update BS_Customer set FirstName = ?, MiddleName = ?, LastName = ?, Address1 = ?, Address2 = ?, City = ?, State = ?, PostalCode = ?, Country = ?, DayPhone = ?, EveningPhone = ?, EMailAddress = ? where UserID = ?";
/*  16:    */   private static final String jdField_if = "update BS_Customer set Password = ? where UserID = ?";
/*  17:    */   private static final String a = "Delete from BS_Customer where UserID = ?";
/*  18:    */   public static final String DEFAULT_CUSTOMER = "BANKSIM";
/*  19:    */   public static final String DEFAULT_CUSTOMER_USERID = "banksim";
/*  20:    */   public static final String DEFAULT_CUSTOMER_PASSWORD = "banksim";
/*  21:    */   
/*  22:    */   public static final boolean doesCustomerExist(String paramString, DBConnection paramDBConnection)
/*  23:    */   {
/*  24: 68 */     DBResultSet localDBResultSet = null;
/*  25:    */     try
/*  26:    */     {
/*  27: 70 */       localDBResultSet = paramDBConnection.prepareQuery("select CustomerID from BS_Customer where UserID=?");
/*  28: 71 */       Object[] arrayOfObject = { paramString };
/*  29: 72 */       localDBResultSet.open(arrayOfObject);
/*  30: 73 */       if (localDBResultSet.getNextRow())
/*  31:    */       {
/*  32: 75 */         localDBResultSet.close();
/*  33: 76 */         return true;
/*  34:    */       }
/*  35: 78 */       localDBResultSet.close();
/*  36: 79 */       return false;
/*  37:    */     }
/*  38:    */     catch (Exception localException1)
/*  39:    */     {
/*  40:    */       try
/*  41:    */       {
/*  42: 83 */         localDBResultSet.close();
/*  43:    */       }
/*  44:    */       catch (Exception localException2) {}
/*  45:    */     }
/*  46: 85 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static final void addCustomer(User paramUser, DBConnection paramDBConnection)
/*  50:    */     throws BSException
/*  51:    */   {
/*  52: 97 */     boolean bool = false;
/*  53:    */     try
/*  54:    */     {
/*  55:102 */       bool = paramDBConnection.isAutoCommit();
/*  56:103 */       if (bool) {
/*  57:103 */         paramDBConnection.setAutoCommit(false);
/*  58:    */       }
/*  59:108 */       if (doesCustomerExist(paramUser.getUserName(), paramDBConnection)) {
/*  60:109 */         throw new BSException(1007, MessageText.getMessage("ERR_USERNAME_EXISTS"));
/*  61:    */       }
/*  62:114 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("select CustomerID from BS_Customer where CustomerID=?");
/*  63:115 */       Object[] arrayOfObject1 = { paramUser.getId() };
/*  64:    */       
/*  65:117 */       localDBResultSet.open(arrayOfObject1);
/*  66:118 */       if (localDBResultSet.getNextRow())
/*  67:    */       {
/*  68:120 */         localDBResultSet.close();
/*  69:121 */         throw new BSException(1003, MessageText.getMessage("ERR_CUSTOMER_EXISTS"));
/*  70:    */       }
/*  71:124 */       localDBResultSet.close();
/*  72:    */       
/*  73:126 */       Object[] arrayOfObject2 = { paramUser.getId(), paramUser.getUserName(), paramUser.getPassword(), paramUser.getFirstName(), paramUser.getMiddleName(), paramUser.getLastName(), paramUser.getStreet(), paramUser.getStreet2(), paramUser.getCity(), paramUser.getState(), paramUser.getZipCode(), paramUser.getCountry(), paramUser.getPhone(), paramUser.getPhone2(), paramUser.getEmail() };
/*  74:    */       
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:144 */       paramDBConnection.executeUpdate("insert into BS_Customer( CustomerID, UserID, Password, FirstName, MiddleName, LastName, Address1, Address2, City, State, PostalCode, Country, DayPhone, EveningPhone, EMailAddress ) values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )", arrayOfObject2);
/*  92:    */       
/*  93:146 */       paramDBConnection.commit();
/*  94:    */     }
/*  95:    */     catch (SQLException localSQLException1)
/*  96:    */     {
/*  97:    */       try
/*  98:    */       {
/*  99:151 */         paramDBConnection.rollback();
/* 100:    */       }
/* 101:    */       catch (SQLException localSQLException2) {}
/* 102:156 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 103:    */     }
/* 104:    */     finally
/* 105:    */     {
/* 106:    */       try
/* 107:    */       {
/* 108:161 */         if (bool) {
/* 109:161 */           paramDBConnection.setAutoCommit(true);
/* 110:    */         }
/* 111:    */       }
/* 112:    */       catch (SQLException localSQLException3)
/* 113:    */       {
/* 114:163 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static final void addDefaultCustomer(DBConnection paramDBConnection)
/* 120:    */     throws BSException
/* 121:    */   {
/* 122:177 */     if (doesCustomerExist("banksim", paramDBConnection)) {
/* 123:177 */       return;
/* 124:    */     }
/* 125:179 */     User localUser = new User();
/* 126:    */     
/* 127:181 */     localUser.setId("BANKSIM");
/* 128:182 */     localUser.setUserName("banksim");
/* 129:183 */     localUser.setPassword("banksim");
/* 130:184 */     localUser.setFirstName("Default");
/* 131:185 */     localUser.setMiddleName("BankSim");
/* 132:186 */     localUser.setLastName("User");
/* 133:187 */     localUser.setStreet("");
/* 134:188 */     localUser.setStreet2("");
/* 135:189 */     localUser.setCity("");
/* 136:190 */     localUser.setState("");
/* 137:191 */     localUser.setZipCode("");
/* 138:192 */     localUser.setCountry("");
/* 139:193 */     localUser.setPhone("(123)456-7890");
/* 140:194 */     localUser.setPhone2("(123)456-7890");
/* 141:195 */     localUser.setEmail("");
/* 142:    */     
/* 143:197 */     addCustomer(localUser, paramDBConnection);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static final void updateCustomer(User paramUser, DBConnection paramDBConnection)
/* 147:    */     throws BSException
/* 148:    */   {
/* 149:208 */     boolean bool = false;
/* 150:    */     try
/* 151:    */     {
/* 152:213 */       bool = paramDBConnection.isAutoCommit();
/* 153:214 */       if (bool) {
/* 154:214 */         paramDBConnection.setAutoCommit(false);
/* 155:    */       }
/* 156:217 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("select CustomerID, UserID from BS_Customer where UserID=?");
/* 157:218 */       Object[] arrayOfObject1 = { paramUser.getUserName() };
/* 158:219 */       localDBResultSet.open(arrayOfObject1);
/* 159:220 */       if (!localDBResultSet.getNextRow())
/* 160:    */       {
/* 161:222 */         localDBResultSet.close();
/* 162:223 */         throw new BSException(1008, MessageText.getMessage("ERR_CUSTOMER_NOT_EXISTS"));
/* 163:    */       }
/* 164:226 */       localDBResultSet.close();
/* 165:    */       
/* 166:228 */       Object[] arrayOfObject2 = { paramUser.getFirstName(), paramUser.getMiddleName(), paramUser.getLastName(), paramUser.getStreet(), paramUser.getStreet2(), paramUser.getCity(), paramUser.getState(), paramUser.getZipCode(), paramUser.getCountry(), paramUser.getPhone(), paramUser.getPhone2(), paramUser.getEmail(), paramUser.getUserName() };
/* 167:    */       
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:244 */       paramDBConnection.executeUpdate("update BS_Customer set FirstName = ?, MiddleName = ?, LastName = ?, Address1 = ?, Address2 = ?, City = ?, State = ?, PostalCode = ?, Country = ?, DayPhone = ?, EveningPhone = ?, EMailAddress = ? where UserID = ?", arrayOfObject2);
/* 183:    */       
/* 184:246 */       paramDBConnection.commit();
/* 185:    */     }
/* 186:    */     catch (SQLException localSQLException1)
/* 187:    */     {
/* 188:    */       try
/* 189:    */       {
/* 190:251 */         paramDBConnection.rollback();
/* 191:    */       }
/* 192:    */       catch (SQLException localSQLException2) {}
/* 193:256 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 194:    */     }
/* 195:    */     finally
/* 196:    */     {
/* 197:    */       try
/* 198:    */       {
/* 199:261 */         if (bool) {
/* 200:261 */           paramDBConnection.setAutoCommit(true);
/* 201:    */         }
/* 202:    */       }
/* 203:    */       catch (SQLException localSQLException3)
/* 204:    */       {
/* 205:263 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 206:    */       }
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static final void updatePassword(User paramUser, DBConnection paramDBConnection)
/* 211:    */     throws BSException
/* 212:    */   {
/* 213:277 */     boolean bool = false;
/* 214:    */     try
/* 215:    */     {
/* 216:282 */       bool = paramDBConnection.isAutoCommit();
/* 217:283 */       if (bool) {
/* 218:283 */         paramDBConnection.setAutoCommit(false);
/* 219:    */       }
/* 220:286 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("select CustomerID, UserID from BS_Customer where UserID=?");
/* 221:287 */       Object[] arrayOfObject1 = { paramUser.getUserName() };
/* 222:288 */       localDBResultSet.open(arrayOfObject1);
/* 223:289 */       if (!localDBResultSet.getNextRow())
/* 224:    */       {
/* 225:291 */         localDBResultSet.close();
/* 226:292 */         throw new BSException(1008, MessageText.getMessage("ERR_CUSTOMER_NOT_EXISTS"));
/* 227:    */       }
/* 228:295 */       localDBResultSet.close();
/* 229:    */       
/* 230:297 */       Object[] arrayOfObject2 = { paramUser.getPassword(), paramUser.getUserName() };
/* 231:    */       
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:302 */       paramDBConnection.executeUpdate("update BS_Customer set Password = ? where UserID = ?", arrayOfObject2);
/* 236:    */       
/* 237:304 */       paramDBConnection.commit();
/* 238:    */     }
/* 239:    */     catch (SQLException localSQLException1)
/* 240:    */     {
/* 241:    */       try
/* 242:    */       {
/* 243:309 */         paramDBConnection.rollback();
/* 244:    */       }
/* 245:    */       catch (SQLException localSQLException2) {}
/* 246:314 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 247:    */     }
/* 248:    */     finally
/* 249:    */     {
/* 250:    */       try
/* 251:    */       {
/* 252:319 */         if (bool) {
/* 253:319 */           paramDBConnection.setAutoCommit(true);
/* 254:    */         }
/* 255:    */       }
/* 256:    */       catch (SQLException localSQLException3)
/* 257:    */       {
/* 258:321 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 259:    */       }
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static final void deleteCustomer(User paramUser, DBConnection paramDBConnection)
/* 264:    */     throws BSException
/* 265:    */   {
/* 266:336 */     if (!doesCustomerExist(paramUser.getUserName(), paramDBConnection)) {
/* 267:338 */       throw new BSException(1008, MessageText.getMessage("ERR_CUSTOMER_NOT_EXISTS"));
/* 268:    */     }
/* 269:341 */     boolean bool = false;
/* 270:    */     try
/* 271:    */     {
/* 272:346 */       bool = paramDBConnection.isAutoCommit();
/* 273:347 */       if (bool) {
/* 274:347 */         paramDBConnection.setAutoCommit(false);
/* 275:    */       }
/* 276:350 */       Enumeration localEnumeration = DBAccount.getAccounts(paramUser, paramDBConnection);
/* 277:353 */       while (localEnumeration.hasMoreElements()) {
/* 278:355 */         DBAccount.deleteAccount((Account)localEnumeration.nextElement(), paramDBConnection);
/* 279:    */       }
/* 280:359 */       Object[] arrayOfObject = { paramUser.getUserName() };
/* 281:360 */       paramDBConnection.executeUpdate("Delete from BS_Customer where UserID = ?", arrayOfObject);
/* 282:    */       
/* 283:362 */       paramDBConnection.commit();
/* 284:    */     }
/* 285:    */     catch (SQLException localSQLException1)
/* 286:    */     {
/* 287:    */       try
/* 288:    */       {
/* 289:367 */         paramDBConnection.rollback();
/* 290:    */       }
/* 291:    */       catch (SQLException localSQLException2) {}
/* 292:372 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 293:    */     }
/* 294:    */     catch (BSException localBSException)
/* 295:    */     {
/* 296:376 */       throw new BSException(localBSException.getErrorCode(), localBSException);
/* 297:    */     }
/* 298:    */     finally
/* 299:    */     {
/* 300:    */       try
/* 301:    */       {
/* 302:380 */         if (bool) {
/* 303:380 */           paramDBConnection.setAutoCommit(true);
/* 304:    */         }
/* 305:    */       }
/* 306:    */       catch (SQLException localSQLException3)
/* 307:    */       {
/* 308:382 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 309:    */       }
/* 310:    */     }
/* 311:    */   }
/* 312:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBCustomer
 * JD-Core Version:    0.7.0.1
 */