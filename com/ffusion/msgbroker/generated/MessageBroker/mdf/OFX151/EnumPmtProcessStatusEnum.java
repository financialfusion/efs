/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*  2:   */ 
/*  3:   */ import org.omg.CORBA.BAD_PARAM;
/*  4:   */ import org.omg.CORBA.portable.IDLEntity;
/*  5:   */ 
/*  6:   */ public final class EnumPmtProcessStatusEnum
/*  7:   */   implements IDLEntity
/*  8:   */ {
/*  9:   */   private int _enum;
/* 10:   */   public static final int _CANCELEDON = 0;
/* 11:   */   
/* 12:   */   private EnumPmtProcessStatusEnum(int paramInt)
/* 13:   */   {
/* 14:18 */     this._enum = paramInt;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int value()
/* 18:   */   {
/* 19:23 */     return this._enum;
/* 20:   */   }
/* 21:   */   
/* 22:28 */   public static final EnumPmtProcessStatusEnum CANCELEDON = new EnumPmtProcessStatusEnum(0);
/* 23:   */   public static final int _FAILEDON = 1;
/* 24:32 */   public static final EnumPmtProcessStatusEnum FAILEDON = new EnumPmtProcessStatusEnum(1);
/* 25:   */   public static final int _NOFUNDSON = 2;
/* 26:36 */   public static final EnumPmtProcessStatusEnum NOFUNDSON = new EnumPmtProcessStatusEnum(2);
/* 27:   */   public static final int _PROCESSEDON = 3;
/* 28:40 */   public static final EnumPmtProcessStatusEnum PROCESSEDON = new EnumPmtProcessStatusEnum(3);
/* 29:   */   public static final int _WILLPROCESSON = 4;
/* 30:44 */   public static final EnumPmtProcessStatusEnum WILLPROCESSON = new EnumPmtProcessStatusEnum(4);
/* 31:   */   
/* 32:   */   public static EnumPmtProcessStatusEnum from_int(int paramInt)
/* 33:   */   {
/* 34:49 */     switch (paramInt)
/* 35:   */     {
/* 36:   */     case 0: 
/* 37:51 */       return CANCELEDON;
/* 38:   */     case 1: 
/* 39:52 */       return FAILEDON;
/* 40:   */     case 2: 
/* 41:53 */       return NOFUNDSON;
/* 42:   */     case 3: 
/* 43:54 */       return PROCESSEDON;
/* 44:   */     case 4: 
/* 45:55 */       return WILLPROCESSON;
/* 46:   */     }
/* 47:56 */     throw new BAD_PARAM();
/* 48:   */   }
/* 49:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum
 * JD-Core Version:    0.7.0.1
 */