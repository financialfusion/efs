/*   1:    */ package com.ffusion.banksim.proxy;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import com.ffusion.beans.Bank;
/*   6:    */ import com.ffusion.beans.Currency;
/*   7:    */ import com.ffusion.beans.accounts.Account;
/*   8:    */ import com.ffusion.beans.accounts.Accounts;
/*   9:    */ import com.ffusion.beans.banking.Transfer;
/*  10:    */ import com.ffusion.beans.messages.Message;
/*  11:    */ import com.ffusion.beans.user.User;
/*  12:    */ import com.ffusion.beans.util.BeansConverter;
/*  13:    */ import com.ffusion.util.beans.PagingContext;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.Enumeration;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.Properties;
/*  19:    */ 
/*  20:    */ public class BankSim
/*  21:    */ {
/*  22:    */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean)
/*  23:    */     throws BSException
/*  24:    */   {
/*  25: 46 */     return com.ffusion.banksim.BankSim.getBSDBParams(paramString1, paramString2, paramString3, paramInt, paramBoolean);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, boolean paramBoolean)
/*  29:    */     throws BSException
/*  30:    */   {
/*  31: 69 */     return com.ffusion.banksim.BankSim.getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt, paramBoolean);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
/*  35:    */     throws BSException
/*  36:    */   {
/*  37: 94 */     return com.ffusion.banksim.BankSim.getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramBoolean);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static BSDBParams getBSDBParams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean)
/*  41:    */     throws BSException
/*  42:    */   {
/*  43:117 */     return com.ffusion.banksim.BankSim.getBSDBParams(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramBoolean);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void initialize(BSDBParams paramBSDBParams, int paramInt)
/*  47:    */     throws BSException
/*  48:    */   {
/*  49:134 */     com.ffusion.banksim.BankSim.initialize(paramBSDBParams, paramInt);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void initialize(BSDBParams paramBSDBParams, int paramInt, Properties paramProperties)
/*  53:    */     throws BSException
/*  54:    */   {
/*  55:147 */     com.ffusion.banksim.BankSim.initialize(paramBSDBParams, paramInt, paramProperties);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static boolean isInitialized()
/*  59:    */   {
/*  60:155 */     return com.ffusion.banksim.BankSim.isInitialized();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static User signOn(String paramString1, String paramString2)
/*  64:    */     throws BSException
/*  65:    */   {
/*  66:167 */     return com.ffusion.banksim.BankSim.signOn(paramString1, paramString2);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static void setPassword(User paramUser, String paramString)
/*  70:    */     throws BSException
/*  71:    */   {
/*  72:178 */     com.ffusion.banksim.BankSim.setPassword(paramUser, paramString);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void addBank(Bank paramBank)
/*  76:    */     throws BSException
/*  77:    */   {
/*  78:187 */     com.ffusion.banksim.BankSim.addBank(paramBank);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void addBanks(Bank[] paramArrayOfBank)
/*  82:    */     throws BSException
/*  83:    */   {
/*  84:196 */     com.ffusion.banksim.BankSim.addBanks(paramArrayOfBank);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static Bank getBank(String paramString)
/*  88:    */     throws BSException
/*  89:    */   {
/*  90:207 */     return com.ffusion.banksim.BankSim.getBank(paramString);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void updateBank(Bank paramBank)
/*  94:    */     throws BSException
/*  95:    */   {
/*  96:217 */     com.ffusion.banksim.BankSim.updateBank(paramBank);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void updateBanks(Bank[] paramArrayOfBank)
/* 100:    */     throws BSException
/* 101:    */   {
/* 102:227 */     com.ffusion.banksim.BankSim.updateBanks(paramArrayOfBank);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static void deleteBank(Bank paramBank)
/* 106:    */     throws BSException
/* 107:    */   {
/* 108:237 */     com.ffusion.banksim.BankSim.deleteBank(paramBank);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static void addCustomer(User paramUser)
/* 112:    */     throws BSException
/* 113:    */   {
/* 114:246 */     com.ffusion.banksim.BankSim.addCustomer(paramUser);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static void addCustomers(User[] paramArrayOfUser)
/* 118:    */     throws BSException
/* 119:    */   {
/* 120:255 */     com.ffusion.banksim.BankSim.addCustomers(paramArrayOfUser);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static void updateCustomer(User paramUser)
/* 124:    */     throws BSException
/* 125:    */   {
/* 126:265 */     com.ffusion.banksim.BankSim.updateCustomer(paramUser);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void updateCustomers(User[] paramArrayOfUser)
/* 130:    */     throws BSException
/* 131:    */   {
/* 132:275 */     com.ffusion.banksim.BankSim.updateCustomers(paramArrayOfUser);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static void deleteCustomer(User paramUser)
/* 136:    */     throws BSException
/* 137:    */   {
/* 138:285 */     com.ffusion.banksim.BankSim.deleteCustomer(paramUser);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static void addAccount(User paramUser, Account paramAccount)
/* 142:    */     throws BSException
/* 143:    */   {
/* 144:295 */     com.ffusion.banksim.BankSim.addAccount(paramUser, paramAccount);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static void addAccounts(User paramUser, Account[] paramArrayOfAccount)
/* 148:    */     throws BSException
/* 149:    */   {
/* 150:305 */     com.ffusion.banksim.BankSim.addAccounts(paramUser, paramArrayOfAccount);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static Enumeration getAccounts(User paramUser)
/* 154:    */     throws BSException
/* 155:    */   {
/* 156:316 */     return com.ffusion.banksim.BankSim.getAccounts(paramUser);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static Account getAccount(Account paramAccount)
/* 160:    */     throws BSException
/* 161:    */   {
/* 162:327 */     return com.ffusion.banksim.BankSim.getAccount(paramAccount);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static void updateAccount(Account paramAccount)
/* 166:    */     throws BSException
/* 167:    */   {
/* 168:337 */     com.ffusion.banksim.BankSim.updateAccount(paramAccount);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static void updateAccounts(Account[] paramArrayOfAccount)
/* 172:    */     throws BSException
/* 173:    */   {
/* 174:347 */     com.ffusion.banksim.BankSim.updateAccounts(paramArrayOfAccount);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static void deleteAccount(Account paramAccount)
/* 178:    */     throws BSException
/* 179:    */   {
/* 180:357 */     com.ffusion.banksim.BankSim.deleteAccount(paramAccount);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static Transfer addTransfer(Transfer paramTransfer, int paramInt)
/* 184:    */     throws BSException
/* 185:    */   {
/* 186:370 */     return com.ffusion.banksim.BankSim.addTransfer(paramTransfer, paramInt);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static void addBPWTransfer(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
/* 190:    */     throws BSException
/* 191:    */   {
/* 192:375 */     addBPWTransfer(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, null, null, 16);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static void addBPWTransfer(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt)
/* 196:    */     throws BSException
/* 197:    */   {
/* 198:380 */     Transfer localTransfer = new Transfer();
/* 199:    */     
/* 200:    */ 
/* 201:383 */     localTransfer.setAmount(new Currency(paramString6, paramString7, localTransfer.getLocale()));
/* 202:386 */     if (a(paramString8)) {
/* 203:387 */       localTransfer.setToAmount(new Currency(paramString8, paramString9, localTransfer.getLocale()));
/* 204:    */     }
/* 205:390 */     localTransfer.setMemo("From the BPW Intra-Bank Transaction Handler");
/* 206:    */     
/* 207:    */ 
/* 208:393 */     Accounts localAccounts = new Accounts();
/* 209:    */     
/* 210:395 */     Account localAccount1 = localAccounts.create(paramString1, paramString4, BeansConverter.getBPWAccountType(paramString5));
/* 211:396 */     localAccount1.setCurrencyCode(paramString7);
/* 212:    */     
/* 213:398 */     Account localAccount2 = localAccounts.create(paramString1, paramString2, BeansConverter.getBPWAccountType(paramString3));
/* 214:399 */     if ((paramString9 == null) || (paramString9.length() == 0)) {
/* 215:400 */       localAccount2.setCurrencyCode(paramString7);
/* 216:    */     } else {
/* 217:402 */       localAccount2.setCurrencyCode(paramString9);
/* 218:    */     }
/* 219:405 */     localTransfer.setFromAccount(localAccount1);
/* 220:406 */     localTransfer.setToAccount(localAccount2);
/* 221:    */     
/* 222:408 */     addTransfer(localTransfer, paramInt);
/* 223:    */   }
/* 224:    */   
/* 225:    */   private static boolean a(String paramString)
/* 226:    */   {
/* 227:413 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 228:414 */       return false;
/* 229:    */     }
/* 230:    */     try
/* 231:    */     {
/* 232:418 */       BigDecimal localBigDecimal = new BigDecimal(paramString);
/* 233:419 */       return localBigDecimal.compareTo(new BigDecimal(0.0D)) > 0;
/* 234:    */     }
/* 235:    */     catch (NumberFormatException localNumberFormatException) {}
/* 236:423 */     return false;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public static Transfer[] addTransfers(Transfer[] paramArrayOfTransfer, int[] paramArrayOfInt)
/* 240:    */     throws BSException
/* 241:    */   {
/* 242:438 */     return com.ffusion.banksim.BankSim.addTransfers(paramArrayOfTransfer, paramArrayOfInt);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public static Enumeration getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
/* 246:    */     throws BSException
/* 247:    */   {
/* 248:454 */     return com.ffusion.banksim.BankSim.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static void openPagedTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
/* 252:    */     throws BSException
/* 253:    */   {
/* 254:470 */     com.ffusion.banksim.BankSim.openPagedTransactions(paramAccount, paramCalendar1, paramCalendar2);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public static void openPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
/* 258:    */     throws BSException
/* 259:    */   {
/* 260:484 */     com.ffusion.banksim.BankSim.openPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static void closePagedTransactions(Account paramAccount)
/* 264:    */     throws BSException
/* 265:    */   {
/* 266:495 */     com.ffusion.banksim.BankSim.closePagedTransactions(paramAccount);
/* 267:    */   }
/* 268:    */   
/* 269:    */   public static int getNumberOfTransactions(Account paramAccount)
/* 270:    */     throws BSException
/* 271:    */   {
/* 272:505 */     return com.ffusion.banksim.BankSim.getNumberOfTransactions(paramAccount);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public static Enumeration getNextPage(Account paramAccount, int paramInt)
/* 276:    */     throws BSException
/* 277:    */   {
/* 278:517 */     return com.ffusion.banksim.BankSim.getNextPage(paramAccount, paramInt);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public static Enumeration getNextPage(Account paramAccount, int paramInt1, int paramInt2)
/* 282:    */     throws BSException
/* 283:    */   {
/* 284:531 */     return com.ffusion.banksim.BankSim.getNextPage(paramAccount, paramInt1, paramInt2);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public static Enumeration getPrevPage(Account paramAccount, int paramInt)
/* 288:    */     throws BSException
/* 289:    */   {
/* 290:543 */     return com.ffusion.banksim.BankSim.getPrevPage(paramAccount, paramInt);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static Enumeration getPrevPage(Account paramAccount, int paramInt1, int paramInt2)
/* 294:    */     throws BSException
/* 295:    */   {
/* 296:557 */     return com.ffusion.banksim.BankSim.getPrevPage(paramAccount, paramInt1, paramInt2);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public static final void addMailMessage(User paramUser, Message paramMessage)
/* 300:    */     throws BSException
/* 301:    */   {
/* 302:569 */     com.ffusion.banksim.BankSim.addMailMessage(paramUser, paramMessage);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static final Enumeration getMailMessages(User paramUser)
/* 306:    */     throws BSException
/* 307:    */   {
/* 308:583 */     return com.ffusion.banksim.BankSim.getMailMessages(paramUser);
/* 309:    */   }
/* 310:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.proxy.BankSim
 * JD-Core Version:    0.7.0.1
 */