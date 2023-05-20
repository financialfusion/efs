/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSException;
/*   4:    */ import com.ffusion.beans.Bank;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ 
/*   7:    */ public class DBFinancialInstitution
/*   8:    */ {
/*   9:    */   private static final String jdField_try = "select FIID from BS_FI where FIID=?";
/*  10:    */   private static final String jdField_int = "select Name, FIID from BS_FI where Name=?";
/*  11:    */   private static final String jdField_do = "insert into BS_FI( FIID, Name, Address1, Address2, City, State, PostalCode, Country, Phone, EMailAddress, Host, Port ) values( ?,?,?,?,?,?,?,?,?,?,?,? )";
/*  12:    */   private static final String jdField_byte = "Select FIID, Name, Address1, Address2, City, State, PostalCode, Country, Phone, EMailAddress, Host, Port from BS_FI where Name = ?";
/*  13:    */   private static final String jdField_if = "Delete from BS_FI where FIID = ?";
/*  14:    */   private static final String jdField_for = "Update BS_Transactions set DestFIID = NULL where DestFIID = ?";
/*  15:    */   private static final String jdField_new = "Delete from BS_Account where FIID = ?";
/*  16:    */   private static final String a = "Update BS_FI set Name = ?, Address1 = ?, Address2 = ?, City = ?, State = ?, PostalCode = ?, Country = ?, Phone = ?, EMailAddress = ? where FIID = ?";
/*  17:    */   
/*  18:    */   public static final boolean doesBankExist(String paramString, DBConnection paramDBConnection)
/*  19:    */   {
/*  20: 59 */     DBResultSet localDBResultSet = null;
/*  21:    */     try
/*  22:    */     {
/*  23: 61 */       localDBResultSet = paramDBConnection.prepareQuery("select FIID from BS_FI where FIID=?");
/*  24: 62 */       Object[] arrayOfObject = { paramString };
/*  25: 63 */       localDBResultSet.open(arrayOfObject);
/*  26: 64 */       if (localDBResultSet.getNextRow())
/*  27:    */       {
/*  28: 65 */         localDBResultSet.close();
/*  29: 66 */         return true;
/*  30:    */       }
/*  31: 68 */       localDBResultSet.close();
/*  32: 69 */       return false;
/*  33:    */     }
/*  34:    */     catch (Exception localException1)
/*  35:    */     {
/*  36:    */       try
/*  37:    */       {
/*  38: 73 */         localDBResultSet.close();
/*  39:    */       }
/*  40:    */       catch (Exception localException2) {}
/*  41:    */     }
/*  42: 75 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static final boolean doesBankNameExist(String paramString, DBConnection paramDBConnection)
/*  46:    */   {
/*  47: 87 */     DBResultSet localDBResultSet = null;
/*  48:    */     try
/*  49:    */     {
/*  50: 89 */       localDBResultSet = paramDBConnection.prepareQuery("select Name, FIID from BS_FI where Name=?");
/*  51: 90 */       Object[] arrayOfObject = { paramString };
/*  52: 91 */       localDBResultSet.open(arrayOfObject);
/*  53: 92 */       if (localDBResultSet.getNextRow())
/*  54:    */       {
/*  55: 93 */         localDBResultSet.close();
/*  56: 94 */         return true;
/*  57:    */       }
/*  58: 96 */       localDBResultSet.close();
/*  59: 97 */       return false;
/*  60:    */     }
/*  61:    */     catch (Exception localException1)
/*  62:    */     {
/*  63:    */       try
/*  64:    */       {
/*  65:101 */         localDBResultSet.close();
/*  66:    */       }
/*  67:    */       catch (Exception localException2) {}
/*  68:    */     }
/*  69:103 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static final void addBank(Bank paramBank, DBConnection paramDBConnection)
/*  73:    */     throws BSException
/*  74:    */   {
/*  75:115 */     boolean bool = false;
/*  76:    */     try
/*  77:    */     {
/*  78:120 */       bool = paramDBConnection.isAutoCommit();
/*  79:121 */       if (bool) {
/*  80:121 */         paramDBConnection.setAutoCommit(false);
/*  81:    */       }
/*  82:126 */       if (doesBankExist(paramBank.getID(), paramDBConnection)) {
/*  83:127 */         throw new BSException(1004, MessageText.getMessage("ERR_FI_EXISTS"));
/*  84:    */       }
/*  85:135 */       if (doesBankNameExist(paramBank.getName(), paramDBConnection)) {
/*  86:136 */         throw new BSException(1004, MessageText.getMessage("ERR_FI_NAME_EXISTS"));
/*  87:    */       }
/*  88:141 */       Object[] arrayOfObject = { paramBank.getID(), paramBank.getName(), paramBank.getStreet(), paramBank.getStreet2(), paramBank.getCity(), paramBank.getState(), paramBank.getZipCode(), paramBank.getCountry(), paramBank.getPhone(), paramBank.getEmail(), "localhost", new Integer(0) };
/*  89:    */       
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:156 */       paramDBConnection.executeUpdate("insert into BS_FI( FIID, Name, Address1, Address2, City, State, PostalCode, Country, Phone, EMailAddress, Host, Port ) values( ?,?,?,?,?,?,?,?,?,?,?,? )", arrayOfObject);
/* 104:    */       
/* 105:158 */       paramDBConnection.commit();
/* 106:    */     }
/* 107:    */     catch (SQLException localSQLException1)
/* 108:    */     {
/* 109:    */       try
/* 110:    */       {
/* 111:163 */         paramDBConnection.rollback();
/* 112:    */       }
/* 113:    */       catch (SQLException localSQLException2) {}
/* 114:168 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 115:    */     }
/* 116:    */     finally
/* 117:    */     {
/* 118:    */       try
/* 119:    */       {
/* 120:173 */         if (bool) {
/* 121:173 */           paramDBConnection.setAutoCommit(true);
/* 122:    */         }
/* 123:    */       }
/* 124:    */       catch (SQLException localSQLException3)
/* 125:    */       {
/* 126:175 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static final Bank getBank(String paramString, DBConnection paramDBConnection)
/* 132:    */     throws BSException
/* 133:    */   {
/* 134:190 */     Bank localBank = null;
/* 135:    */     try
/* 136:    */     {
/* 137:192 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("Select FIID, Name, Address1, Address2, City, State, PostalCode, Country, Phone, EMailAddress, Host, Port from BS_FI where Name = ?");
/* 138:193 */       Object[] arrayOfObject = { paramString };
/* 139:194 */       localDBResultSet.open(arrayOfObject);
/* 140:195 */       localBank = new Bank();
/* 141:196 */       if (localDBResultSet.getNextRow())
/* 142:    */       {
/* 143:198 */         localBank.setID(localDBResultSet.getColumnString(1));
/* 144:199 */         localBank.setName(localDBResultSet.getColumnString(2));
/* 145:    */         
/* 146:201 */         localBank.setStreet(localDBResultSet.getColumnString(3));
/* 147:202 */         localBank.setStreet2(localDBResultSet.getColumnString(4));
/* 148:203 */         localBank.setCity(localDBResultSet.getColumnString(5));
/* 149:204 */         localBank.setState(localDBResultSet.getColumnString(6));
/* 150:205 */         localBank.setZipCode(localDBResultSet.getColumnString(7));
/* 151:206 */         localBank.setCountry(localDBResultSet.getColumnString(8));
/* 152:207 */         localBank.setPhone(localDBResultSet.getColumnString(9));
/* 153:208 */         localBank.setEmail(localDBResultSet.getColumnString(10));
/* 154:    */       }
/* 155:212 */       localDBResultSet.close();
/* 156:    */     }
/* 157:    */     catch (SQLException localSQLException)
/* 158:    */     {
/* 159:214 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException));
/* 160:    */     }
/* 161:217 */     return localBank;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static final void deleteBank(Bank paramBank, DBConnection paramDBConnection)
/* 165:    */     throws BSException
/* 166:    */   {
/* 167:229 */     if (!doesBankExist(paramBank.getID(), paramDBConnection)) {
/* 168:231 */       throw new BSException(1005, MessageText.getMessage("ERR_FI_NOT_EXISTS"));
/* 169:    */     }
/* 170:234 */     boolean bool = false;
/* 171:    */     try
/* 172:    */     {
/* 173:239 */       bool = paramDBConnection.isAutoCommit();
/* 174:240 */       if (bool) {
/* 175:240 */         paramDBConnection.setAutoCommit(false);
/* 176:    */       }
/* 177:242 */       Object[] arrayOfObject = { paramBank.getID() };
/* 178:    */       
/* 179:    */ 
/* 180:245 */       paramDBConnection.executeUpdate("Delete from BS_Account where FIID = ?", arrayOfObject);
/* 181:    */       
/* 182:    */ 
/* 183:248 */       paramDBConnection.executeUpdate("Update BS_Transactions set DestFIID = NULL where DestFIID = ?", arrayOfObject);
/* 184:    */       
/* 185:    */ 
/* 186:251 */       paramDBConnection.executeUpdate("Delete from BS_FI where FIID = ?", arrayOfObject);
/* 187:    */       
/* 188:253 */       paramDBConnection.commit();
/* 189:    */     }
/* 190:    */     catch (SQLException localSQLException1)
/* 191:    */     {
/* 192:    */       try
/* 193:    */       {
/* 194:257 */         paramDBConnection.rollback();
/* 195:    */       }
/* 196:    */       catch (SQLException localSQLException2) {}
/* 197:262 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 198:    */     }
/* 199:    */     finally
/* 200:    */     {
/* 201:    */       try
/* 202:    */       {
/* 203:267 */         if (bool) {
/* 204:267 */           paramDBConnection.setAutoCommit(true);
/* 205:    */         }
/* 206:    */       }
/* 207:    */       catch (SQLException localSQLException3)
/* 208:    */       {
/* 209:269 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static final void updateBank(Bank paramBank, DBConnection paramDBConnection)
/* 215:    */     throws BSException
/* 216:    */   {
/* 217:283 */     boolean bool = false;
/* 218:    */     try
/* 219:    */     {
/* 220:288 */       bool = paramDBConnection.isAutoCommit();
/* 221:289 */       if (bool) {
/* 222:289 */         paramDBConnection.setAutoCommit(false);
/* 223:    */       }
/* 224:292 */       DBResultSet localDBResultSet = paramDBConnection.prepareQuery("select FIID from BS_FI where FIID=?");
/* 225:293 */       Object[] arrayOfObject1 = { paramBank.getID() };
/* 226:294 */       localDBResultSet.open(arrayOfObject1);
/* 227:295 */       if (!localDBResultSet.getNextRow())
/* 228:    */       {
/* 229:298 */         localDBResultSet.close();
/* 230:299 */         throw new BSException(1005, MessageText.getMessage("ERR_FI_NOT_EXISTS"));
/* 231:    */       }
/* 232:302 */       localDBResultSet.close();
/* 233:    */       
/* 234:304 */       localDBResultSet = paramDBConnection.prepareQuery("select Name, FIID from BS_FI where Name=?");
/* 235:305 */       Object[] arrayOfObject2 = { paramBank.getName() };
/* 236:306 */       localDBResultSet.open(arrayOfObject2);
/* 237:307 */       if (localDBResultSet.getNextRow()) {
/* 238:310 */         if (!localDBResultSet.getColumnString(2).equals(paramBank.getID()))
/* 239:    */         {
/* 240:317 */           localDBResultSet.close();
/* 241:318 */           throw new BSException(1004, MessageText.getMessage("ERR_FI_NAME_EXISTS"));
/* 242:    */         }
/* 243:    */       }
/* 244:322 */       localDBResultSet.close();
/* 245:    */       
/* 246:324 */       Object[] arrayOfObject3 = { paramBank.getName(), paramBank.getStreet(), paramBank.getStreet2(), paramBank.getCity(), paramBank.getState(), paramBank.getZipCode(), paramBank.getCountry(), paramBank.getPhone(), paramBank.getEmail(), paramBank.getID() };
/* 247:    */       
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:339 */       paramDBConnection.executeUpdate("Update BS_FI set Name = ?, Address1 = ?, Address2 = ?, City = ?, State = ?, PostalCode = ?, Country = ?, Phone = ?, EMailAddress = ? where FIID = ?", arrayOfObject3);
/* 262:    */       
/* 263:341 */       paramDBConnection.commit();
/* 264:    */     }
/* 265:    */     catch (SQLException localSQLException1)
/* 266:    */     {
/* 267:    */       try
/* 268:    */       {
/* 269:346 */         paramDBConnection.rollback();
/* 270:    */       }
/* 271:    */       catch (SQLException localSQLException2) {}
/* 272:351 */       throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException1));
/* 273:    */     }
/* 274:    */     finally
/* 275:    */     {
/* 276:    */       try
/* 277:    */       {
/* 278:356 */         if (bool) {
/* 279:356 */           paramDBConnection.setAutoCommit(true);
/* 280:    */         }
/* 281:    */       }
/* 282:    */       catch (SQLException localSQLException3)
/* 283:    */       {
/* 284:358 */         throw new BSException(1, DBSqlUtils.getRealSQLException(localSQLException3));
/* 285:    */       }
/* 286:    */     }
/* 287:    */   }
/* 288:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBFinancialInstitution
 * JD-Core Version:    0.7.0.1
 */