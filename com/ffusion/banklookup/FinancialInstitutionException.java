/*   1:    */ package com.ffusion.banklookup;
/*   2:    */ 
/*   3:    */ public class FinancialInstitutionException
/*   4:    */   extends Exception
/*   5:    */ {
/*   6: 12 */   private int _errCode = -1;
/*   7: 13 */   private String _errType = null;
/*   8: 15 */   private transient Throwable _nested = null;
/*   9:    */   public static final int FI_OKAY = 0;
/*  10:    */   public static final int FI_CANT_INITIALIZE = 1;
/*  11:    */   public static final int FI_NOT_INITIALIZED = 2;
/*  12:    */   public static final int FI_CANT_GET_CONNECTION = 3;
/*  13:    */   public static final int FI_CANT_GET_RESULTS = 4;
/*  14:    */   public static final int FI_CANT_INSERT_DATA = 5;
/*  15:    */   public static final int FI_CANT_MODIFY_DATA = 6;
/*  16:    */   public static final int FI_CANT_DELETE_DATA = 7;
/*  17:    */   public static final int FI_CANT_SYNCHRONIZE = 8;
/*  18:    */   public static final int FI_CANT_PURGE = 9;
/*  19:    */   public static final int FI_INVALID_ROUTING_NUMBER = 10;
/*  20:    */   public static final int FI_INVALID_BIC_CODE = 11;
/*  21:    */   public static final int FI_INVALID_CHIPS_UID_CODE = 12;
/*  22:    */   public static final int FI_NOT_YET_IMPLEMENTED = 13;
/*  23:    */   public static final int FI_INTERNAL_ERROR = 14;
/*  24:    */   public static final int FI_GETCOUNTRIES_ERROR = 15;
/*  25:    */   
/*  26:    */   public FinancialInstitutionException(int errCode, String errorEvent)
/*  27:    */   {
/*  28:122 */     super(errorEvent);
/*  29:123 */     this._errCode = errCode;
/*  30:124 */     this._errType = null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public FinancialInstitutionException(int errCode, String errorEvent, String errType)
/*  34:    */   {
/*  35:136 */     super(errorEvent);
/*  36:137 */     this._errCode = errCode;
/*  37:138 */     this._errType = errType;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public FinancialInstitutionException(Throwable nested, String s)
/*  41:    */   {
/*  42:149 */     super(s);
/*  43:150 */     this._nested = nested;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public FinancialInstitutionException(Throwable nested, int errCode, String errorEvent)
/*  47:    */   {
/*  48:162 */     super(errorEvent);
/*  49:163 */     this._nested = nested;
/*  50:164 */     this._errCode = errCode;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public FinancialInstitutionException(Throwable nested, String s, String errType)
/*  54:    */   {
/*  55:176 */     super(s);
/*  56:177 */     this._nested = nested;
/*  57:178 */     this._errType = errType;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getErrType()
/*  61:    */   {
/*  62:182 */     return this._errType;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getErrCode()
/*  66:    */   {
/*  67:186 */     return this._errCode;
/*  68:    */   }
/*  69:    */ }


/* Location:           D:\drops\jd\jars\ffiblcommon.jar
 * Qualified Name:     com.ffusion.banklookup.FinancialInstitutionException
 * JD-Core Version:    0.7.0.1
 */