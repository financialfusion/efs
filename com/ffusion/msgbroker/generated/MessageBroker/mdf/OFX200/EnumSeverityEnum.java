/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.BAD_PARAM;
/*  4:   */ import org.omg.CORBA.portable.IDLEntity;
/*  5:   */ 
/*  6:   */ public final class EnumSeverityEnum
/*  7:   */   implements IDLEntity
/*  8:   */ {
/*  9:   */   private int _enum;
/* 10:   */   public static final int _ERROR = 0;
/* 11:   */   
/* 12:   */   private EnumSeverityEnum(int paramInt)
/* 13:   */   {
/* 14:18 */     this._enum = paramInt;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int value()
/* 18:   */   {
/* 19:23 */     return this._enum;
/* 20:   */   }
/* 21:   */   
/* 22:28 */   public static final EnumSeverityEnum ERROR = new EnumSeverityEnum(0);
/* 23:   */   public static final int _INFO = 1;
/* 24:32 */   public static final EnumSeverityEnum INFO = new EnumSeverityEnum(1);
/* 25:   */   public static final int _WARN = 2;
/* 26:36 */   public static final EnumSeverityEnum WARN = new EnumSeverityEnum(2);
/* 27:   */   
/* 28:   */   public static EnumSeverityEnum from_int(int paramInt)
/* 29:   */   {
/* 30:41 */     switch (paramInt)
/* 31:   */     {
/* 32:   */     case 0: 
/* 33:43 */       return ERROR;
/* 34:   */     case 1: 
/* 35:44 */       return INFO;
/* 36:   */     case 2: 
/* 37:45 */       return WARN;
/* 38:   */     }
/* 39:46 */     throw new BAD_PARAM();
/* 40:   */   }
/* 41:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum
 * JD-Core Version:    0.7.0.1
 */