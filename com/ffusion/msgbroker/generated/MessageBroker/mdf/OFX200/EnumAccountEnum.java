/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.BAD_PARAM;
/*   4:    */ import org.omg.CORBA.portable.IDLEntity;
/*   5:    */ 
/*   6:    */ public final class EnumAccountEnum
/*   7:    */   implements IDLEntity
/*   8:    */ {
/*   9:    */   private int _enum;
/*  10:    */   public static final int _BROKERAGE = 0;
/*  11:    */   
/*  12:    */   private EnumAccountEnum(int paramInt)
/*  13:    */   {
/*  14: 18 */     this._enum = paramInt;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int value()
/*  18:    */   {
/*  19: 23 */     return this._enum;
/*  20:    */   }
/*  21:    */   
/*  22: 28 */   public static final EnumAccountEnum BROKERAGE = new EnumAccountEnum(0);
/*  23:    */   public static final int _BUSINESSLOAN = 1;
/*  24: 32 */   public static final EnumAccountEnum BUSINESSLOAN = new EnumAccountEnum(1);
/*  25:    */   public static final int _CD = 2;
/*  26: 36 */   public static final EnumAccountEnum CD = new EnumAccountEnum(2);
/*  27:    */   public static final int _CHECKING = 3;
/*  28: 40 */   public static final EnumAccountEnum CHECKING = new EnumAccountEnum(3);
/*  29:    */   public static final int _CMA = 4;
/*  30: 44 */   public static final EnumAccountEnum CMA = new EnumAccountEnum(4);
/*  31:    */   public static final int _CREDIT = 5;
/*  32: 48 */   public static final EnumAccountEnum CREDIT = new EnumAccountEnum(5);
/*  33:    */   public static final int _CREDITLINE = 6;
/*  34: 52 */   public static final EnumAccountEnum CREDITLINE = new EnumAccountEnum(6);
/*  35:    */   public static final int _DDA = 7;
/*  36: 56 */   public static final EnumAccountEnum DDA = new EnumAccountEnum(7);
/*  37:    */   public static final int _FIXEDDEPOSIT = 8;
/*  38: 60 */   public static final EnumAccountEnum FIXEDDEPOSIT = new EnumAccountEnum(8);
/*  39:    */   public static final int _HOMEEQUITY = 9;
/*  40: 64 */   public static final EnumAccountEnum HOMEEQUITY = new EnumAccountEnum(9);
/*  41:    */   public static final int _IRA = 10;
/*  42: 68 */   public static final EnumAccountEnum IRA = new EnumAccountEnum(10);
/*  43:    */   public static final int _LOAN = 11;
/*  44: 72 */   public static final EnumAccountEnum LOAN = new EnumAccountEnum(11);
/*  45:    */   public static final int _MMA = 12;
/*  46: 76 */   public static final EnumAccountEnum MMA = new EnumAccountEnum(12);
/*  47:    */   public static final int _MONEYMRKT = 13;
/*  48: 80 */   public static final EnumAccountEnum MONEYMRKT = new EnumAccountEnum(13);
/*  49:    */   public static final int _MORTGAGE = 14;
/*  50: 84 */   public static final EnumAccountEnum MORTGAGE = new EnumAccountEnum(14);
/*  51:    */   public static final int _OTHER = 15;
/*  52: 88 */   public static final EnumAccountEnum OTHER = new EnumAccountEnum(15);
/*  53:    */   public static final int _SAVINGS = 16;
/*  54: 92 */   public static final EnumAccountEnum SAVINGS = new EnumAccountEnum(16);
/*  55:    */   public static final int _SDA = 17;
/*  56: 96 */   public static final EnumAccountEnum SDA = new EnumAccountEnum(17);
/*  57:    */   public static final int _STOCKBOND = 18;
/*  58:100 */   public static final EnumAccountEnum STOCKBOND = new EnumAccountEnum(18);
/*  59:    */   
/*  60:    */   public static EnumAccountEnum from_int(int paramInt)
/*  61:    */   {
/*  62:105 */     switch (paramInt)
/*  63:    */     {
/*  64:    */     case 0: 
/*  65:107 */       return BROKERAGE;
/*  66:    */     case 1: 
/*  67:108 */       return BUSINESSLOAN;
/*  68:    */     case 2: 
/*  69:109 */       return CD;
/*  70:    */     case 3: 
/*  71:110 */       return CHECKING;
/*  72:    */     case 4: 
/*  73:111 */       return CMA;
/*  74:    */     case 5: 
/*  75:112 */       return CREDIT;
/*  76:    */     case 6: 
/*  77:113 */       return CREDITLINE;
/*  78:    */     case 7: 
/*  79:114 */       return DDA;
/*  80:    */     case 8: 
/*  81:115 */       return FIXEDDEPOSIT;
/*  82:    */     case 9: 
/*  83:116 */       return HOMEEQUITY;
/*  84:    */     case 10: 
/*  85:117 */       return IRA;
/*  86:    */     case 11: 
/*  87:118 */       return LOAN;
/*  88:    */     case 12: 
/*  89:119 */       return MMA;
/*  90:    */     case 13: 
/*  91:120 */       return MONEYMRKT;
/*  92:    */     case 14: 
/*  93:121 */       return MORTGAGE;
/*  94:    */     case 15: 
/*  95:122 */       return OTHER;
/*  96:    */     case 16: 
/*  97:123 */       return SAVINGS;
/*  98:    */     case 17: 
/*  99:124 */       return SDA;
/* 100:    */     case 18: 
/* 101:125 */       return STOCKBOND;
/* 102:    */     }
/* 103:126 */     throw new BAD_PARAM();
/* 104:    */   }
/* 105:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum
 * JD-Core Version:    0.7.0.1
 */