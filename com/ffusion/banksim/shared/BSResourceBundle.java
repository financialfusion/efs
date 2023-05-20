/*  1:   */ package com.ffusion.banksim.shared;
/*  2:   */ 
/*  3:   */ import java.text.MessageFormat;
/*  4:   */ import java.util.ResourceBundle;
/*  5:   */ 
/*  6:   */ public final class BSResourceBundle
/*  7:   */ {
/*  8:   */   public BSResourceBundle(String paramString)
/*  9:   */   {
/* 10:20 */     this.a = paramString;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public final String getMessage(String paramString)
/* 14:   */   {
/* 15:28 */     if (this.jdField_if == null) {
/* 16:30 */       this.jdField_if = ResourceBundle.getBundle(this.a);
/* 17:   */     }
/* 18:32 */     return this.jdField_if.getString(paramString);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public final String getMessage(String paramString, Object[] paramArrayOfObject)
/* 22:   */   {
/* 23:40 */     return MessageFormat.format(getMessage(paramString), paramArrayOfObject);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final String getMessage(String paramString, Object paramObject)
/* 27:   */   {
/* 28:48 */     return getMessage(paramString, new Object[] { paramObject });
/* 29:   */   }
/* 30:   */   
/* 31:   */   public final String getMessage(String paramString, Object paramObject1, Object paramObject2)
/* 32:   */   {
/* 33:56 */     return getMessage(paramString, new Object[] { paramObject1, paramObject2 });
/* 34:   */   }
/* 35:   */   
/* 36:   */   public final String getMessage(String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
/* 37:   */   {
/* 38:65 */     return getMessage(paramString, new Object[] { paramObject1, paramObject2, paramObject3 });
/* 39:   */   }
/* 40:   */   
/* 41:   */   public final String getMessage(String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
/* 42:   */   {
/* 43:74 */     return getMessage(paramString, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4 });
/* 44:   */   }
/* 45:   */   
/* 46:   */   public final String getMessage(String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5)
/* 47:   */   {
/* 48:83 */     return getMessage(paramString, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5 });
/* 49:   */   }
/* 50:   */   
/* 51:86 */   private ResourceBundle jdField_if = null;
/* 52:   */   private String a;
/* 53:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.shared.BSResourceBundle
 * JD-Core Version:    0.7.0.1
 */