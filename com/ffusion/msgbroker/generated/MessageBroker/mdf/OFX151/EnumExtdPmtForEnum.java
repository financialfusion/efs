/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.BAD_PARAM;
/*  4:   */ import org.omg.CORBA.portable.IDLEntity;
/*  5:   */ 
/*  6:   */ public final class EnumExtdPmtForEnum
/*  7:   */   implements IDLEntity
/*  8:   */ {
/*  9:   */   private int _enum;
/* 10:   */   public static final int _BUSINESS = 0;
/* 11:   */   
/* 12:   */   private EnumExtdPmtForEnum(int paramInt)
/* 13:   */   {
/* 14:18 */     this._enum = paramInt;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int value()
/* 18:   */   {
/* 19:23 */     return this._enum;
/* 20:   */   }
/* 21:   */   
/* 22:28 */   public static final EnumExtdPmtForEnum BUSINESS = new EnumExtdPmtForEnum(0);
/* 23:   */   public static final int _INDIVIDUAL = 1;
/* 24:32 */   public static final EnumExtdPmtForEnum INDIVIDUAL = new EnumExtdPmtForEnum(1);
/* 25:   */   
/* 26:   */   public static EnumExtdPmtForEnum from_int(int paramInt)
/* 27:   */   {
/* 28:37 */     switch (paramInt)
/* 29:   */     {
/* 30:   */     case 0: 
/* 31:39 */       return BUSINESS;
/* 32:   */     case 1: 
/* 33:40 */       return INDIVIDUAL;
/* 34:   */     }
/* 35:41 */     throw new BAD_PARAM();
/* 36:   */   }
/* 37:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumExtdPmtForEnum
 * JD-Core Version:    0.7.0.1
 */