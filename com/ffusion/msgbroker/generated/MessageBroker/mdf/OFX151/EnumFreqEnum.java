/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.BAD_PARAM;
/*  4:   */ import org.omg.CORBA.portable.IDLEntity;
/*  5:   */ 
/*  6:   */ public final class EnumFreqEnum
/*  7:   */   implements IDLEntity
/*  8:   */ {
/*  9:   */   private int _enum;
/* 10:   */   public static final int _ANNUALLY = 0;
/* 11:   */   
/* 12:   */   private EnumFreqEnum(int paramInt)
/* 13:   */   {
/* 14:18 */     this._enum = paramInt;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int value()
/* 18:   */   {
/* 19:23 */     return this._enum;
/* 20:   */   }
/* 21:   */   
/* 22:28 */   public static final EnumFreqEnum ANNUALLY = new EnumFreqEnum(0);
/* 23:   */   public static final int _ANUALLY = 1;
/* 24:32 */   public static final EnumFreqEnum ANUALLY = new EnumFreqEnum(1);
/* 25:   */   public static final int _BIMONTHLY = 2;
/* 26:36 */   public static final EnumFreqEnum BIMONTHLY = new EnumFreqEnum(2);
/* 27:   */   public static final int _BIWEEKLY = 3;
/* 28:40 */   public static final EnumFreqEnum BIWEEKLY = new EnumFreqEnum(3);
/* 29:   */   public static final int _FOURWEEKS = 4;
/* 30:44 */   public static final EnumFreqEnum FOURWEEKS = new EnumFreqEnum(4);
/* 31:   */   public static final int _MONTHLY = 5;
/* 32:48 */   public static final EnumFreqEnum MONTHLY = new EnumFreqEnum(5);
/* 33:   */   public static final int _QUARTERLY = 6;
/* 34:52 */   public static final EnumFreqEnum QUARTERLY = new EnumFreqEnum(6);
/* 35:   */   public static final int _SEMIANNUALLY = 7;
/* 36:56 */   public static final EnumFreqEnum SEMIANNUALLY = new EnumFreqEnum(7);
/* 37:   */   public static final int _TRIANNUALLY = 8;
/* 38:60 */   public static final EnumFreqEnum TRIANNUALLY = new EnumFreqEnum(8);
/* 39:   */   public static final int _TWICEMONTHLY = 9;
/* 40:64 */   public static final EnumFreqEnum TWICEMONTHLY = new EnumFreqEnum(9);
/* 41:   */   public static final int _WEEKLY = 10;
/* 42:68 */   public static final EnumFreqEnum WEEKLY = new EnumFreqEnum(10);
/* 43:   */   
/* 44:   */   public static EnumFreqEnum from_int(int paramInt)
/* 45:   */   {
/* 46:73 */     switch (paramInt)
/* 47:   */     {
/* 48:   */     case 0: 
/* 49:75 */       return ANNUALLY;
/* 50:   */     case 1: 
/* 51:76 */       return ANUALLY;
/* 52:   */     case 2: 
/* 53:77 */       return BIMONTHLY;
/* 54:   */     case 3: 
/* 55:78 */       return BIWEEKLY;
/* 56:   */     case 4: 
/* 57:79 */       return FOURWEEKS;
/* 58:   */     case 5: 
/* 59:80 */       return MONTHLY;
/* 60:   */     case 6: 
/* 61:81 */       return QUARTERLY;
/* 62:   */     case 7: 
/* 63:82 */       return SEMIANNUALLY;
/* 64:   */     case 8: 
/* 65:83 */       return TRIANNUALLY;
/* 66:   */     case 9: 
/* 67:84 */       return TWICEMONTHLY;
/* 68:   */     case 10: 
/* 69:85 */       return WEEKLY;
/* 70:   */     }
/* 71:86 */     throw new BAD_PARAM();
/* 72:   */   }
/* 73:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum
 * JD-Core Version:    0.7.0.1
 */