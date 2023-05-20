/*  1:   */ package com.ffusion.banksim.db;
/*  2:   */ 
/*  3:   */ import com.ffusion.banksim.shared.BSResourceBundle;
/*  4:   */ 
/*  5:   */ public class MessageText
/*  6:   */   implements IBSErrConstants
/*  7:   */ {
/*  8:   */   public static String getMessage(String paramString)
/*  9:   */   {
/* 10:21 */     return a.getMessage(paramString);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static String getMessage(String paramString, Throwable paramThrowable)
/* 14:   */   {
/* 15:26 */     return a.getMessage(paramString, paramThrowable);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static String getMessage(String paramString1, String paramString2)
/* 19:   */   {
/* 20:31 */     return a.getMessage(paramString1, paramString2);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static String getMessage(String paramString1, String paramString2, Throwable paramThrowable)
/* 24:   */   {
/* 25:36 */     return a.getMessage(paramString1, paramString2, paramThrowable);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static String getMessage(String paramString1, String paramString2, String paramString3)
/* 29:   */   {
/* 30:41 */     return a.getMessage(paramString1, paramString2, paramString3);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static String getMessage(String paramString1, String paramString2, String paramString3, Throwable paramThrowable)
/* 34:   */   {
/* 35:46 */     return a.getMessage(paramString1, paramString2, paramString3, paramThrowable);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static BSResourceBundle getMessages()
/* 39:   */   {
/* 40:51 */     return a;
/* 41:   */   }
/* 42:   */   
/* 43:55 */   private static BSResourceBundle a = new BSResourceBundle("com.ffusion.banksim.db.lang.BSErrStrings");
/* 44:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.MessageText
 * JD-Core Version:    0.7.0.1
 */