/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.BAD_PARAM;
/*  4:   */ import org.omg.CORBA.portable.IDLEntity;
/*  5:   */ 
/*  6:   */ public final class EnumIDScopeEnum
/*  7:   */   implements IDLEntity
/*  8:   */ {
/*  9:   */   private int _enum;
/* 10:   */   public static final int _GLOBAL = 0;
/* 11:   */   
/* 12:   */   private EnumIDScopeEnum(int paramInt)
/* 13:   */   {
/* 14:18 */     this._enum = paramInt;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int value()
/* 18:   */   {
/* 19:23 */     return this._enum;
/* 20:   */   }
/* 21:   */   
/* 22:28 */   public static final EnumIDScopeEnum GLOBAL = new EnumIDScopeEnum(0);
/* 23:   */   public static final int _USER = 1;
/* 24:32 */   public static final EnumIDScopeEnum USER = new EnumIDScopeEnum(1);
/* 25:   */   
/* 26:   */   public static EnumIDScopeEnum from_int(int paramInt)
/* 27:   */   {
/* 28:37 */     switch (paramInt)
/* 29:   */     {
/* 30:   */     case 0: 
/* 31:39 */       return GLOBAL;
/* 32:   */     case 1: 
/* 33:40 */       return USER;
/* 34:   */     }
/* 35:41 */     throw new BAD_PARAM();
/* 36:   */   }
/* 37:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum
 * JD-Core Version:    0.7.0.1
 */