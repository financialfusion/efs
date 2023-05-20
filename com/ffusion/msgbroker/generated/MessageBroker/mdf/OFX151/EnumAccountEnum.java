/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  29:    */   public static final int _CREDIT = 4;
/*  30: 44 */   public static final EnumAccountEnum CREDIT = new EnumAccountEnum(4);
/*  31:    */   public static final int _CREDITLINE = 5;
/*  32: 48 */   public static final EnumAccountEnum CREDITLINE = new EnumAccountEnum(5);
/*  33:    */   public static final int _FIXEDDEPOSIT = 6;
/*  34: 52 */   public static final EnumAccountEnum FIXEDDEPOSIT = new EnumAccountEnum(6);
/*  35:    */   public static final int _HOMEEQUITY = 7;
/*  36: 56 */   public static final EnumAccountEnum HOMEEQUITY = new EnumAccountEnum(7);
/*  37:    */   public static final int _IRA = 8;
/*  38: 60 */   public static final EnumAccountEnum IRA = new EnumAccountEnum(8);
/*  39:    */   public static final int _LOAN = 9;
/*  40: 64 */   public static final EnumAccountEnum LOAN = new EnumAccountEnum(9);
/*  41:    */   public static final int _MONEYMRKT = 10;
/*  42: 68 */   public static final EnumAccountEnum MONEYMRKT = new EnumAccountEnum(10);
/*  43:    */   public static final int _MORTGAGE = 11;
/*  44: 72 */   public static final EnumAccountEnum MORTGAGE = new EnumAccountEnum(11);
/*  45:    */   public static final int _OTHER = 12;
/*  46: 76 */   public static final EnumAccountEnum OTHER = new EnumAccountEnum(12);
/*  47:    */   public static final int _SAVINGS = 13;
/*  48: 80 */   public static final EnumAccountEnum SAVINGS = new EnumAccountEnum(13);
/*  49:    */   public static final int _STOCKBOND = 14;
/*  50: 84 */   public static final EnumAccountEnum STOCKBOND = new EnumAccountEnum(14);
/*  51:    */   
/*  52:    */   public static EnumAccountEnum from_int(int paramInt)
/*  53:    */   {
/*  54: 89 */     switch (paramInt)
/*  55:    */     {
/*  56:    */     case 0: 
/*  57: 91 */       return BROKERAGE;
/*  58:    */     case 1: 
/*  59: 92 */       return BUSINESSLOAN;
/*  60:    */     case 2: 
/*  61: 93 */       return CD;
/*  62:    */     case 3: 
/*  63: 94 */       return CHECKING;
/*  64:    */     case 4: 
/*  65: 95 */       return CREDIT;
/*  66:    */     case 5: 
/*  67: 96 */       return CREDITLINE;
/*  68:    */     case 6: 
/*  69: 97 */       return FIXEDDEPOSIT;
/*  70:    */     case 7: 
/*  71: 98 */       return HOMEEQUITY;
/*  72:    */     case 8: 
/*  73: 99 */       return IRA;
/*  74:    */     case 9: 
/*  75:100 */       return LOAN;
/*  76:    */     case 10: 
/*  77:101 */       return MONEYMRKT;
/*  78:    */     case 11: 
/*  79:102 */       return MORTGAGE;
/*  80:    */     case 12: 
/*  81:103 */       return OTHER;
/*  82:    */     case 13: 
/*  83:104 */       return SAVINGS;
/*  84:    */     case 14: 
/*  85:105 */       return STOCKBOND;
/*  86:    */     }
/*  87:106 */     throw new BAD_PARAM();
/*  88:    */   }
/*  89:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum
 * JD-Core Version:    0.7.0.1
 */