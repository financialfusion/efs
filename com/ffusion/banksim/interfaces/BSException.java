/*   1:    */ package com.ffusion.banksim.interfaces;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ 
/*   6:    */ public class BSException
/*   7:    */   extends Exception
/*   8:    */ {
/*   9:    */   public static final int BSE_OKAY = 0;
/*  10:    */   public static final int BSE_DB_EXCEPTION = 1;
/*  11:    */   public static final int BSE_DB_UNKNOWN_CONN_TYPE = 2;
/*  12:    */   public static final int BSE_DB_DRIVER_NOT_FOUND = 3;
/*  13:    */   public static final int BSE_DB_COULD_NOT_CONNECT = 4;
/*  14:    */   public static final int BSE_DB_NO_REPOSITORY = 5;
/*  15:    */   public static final int BSE_DB_REPOSITORY_TOO_OLD = 6;
/*  16:    */   public static final int BSE_DB_REPOSITORY_TOO_NEW = 7;
/*  17:    */   public static final int BSE_DB_CREATING_REPOSITORY = 8;
/*  18:    */   public static final int BSE_DB_DESTROYING_REPOSITORY = 9;
/*  19:    */   public static final int BSE_DB_MAX_CONN_POOL_SIZE = 10;
/*  20:    */   public static final int BSE_DB_CONN_NOT_IN_POOL = 11;
/*  21:    */   public static final int BSE_DB_UNCOMMITTED_TRANS = 12;
/*  22:    */   public static final int BSE_NOT_INITIALIZED = 1000;
/*  23:    */   public static final int BSE_ALREADY_INITIALIZED = 1001;
/*  24:    */   public static final int BSE_ACCOUNT_EXISTS = 1002;
/*  25:    */   public static final int BSE_CUSTOMER_EXISTS = 1003;
/*  26:    */   public static final int BSE_FI_EXISTS = 1004;
/*  27:    */   public static final int BSE_FI_NOT_EXISTS = 1005;
/*  28:    */   public static final int BSE_INVALID_PASSWORD = 1006;
/*  29:    */   public static final int BSE_USERNAME_EXISTS = 1007;
/*  30:    */   public static final int BSE_CUSTOMER_NOT_EXISTS = 1008;
/*  31:    */   public static final int BSE_NSF = 1009;
/*  32:    */   public static final int BSE_AMOUNT_NOT_POSITIVE = 1010;
/*  33:    */   public static final int BSE_ACCOUNT_NOT_EXISTS = 1011;
/*  34:    */   public static final int BSE_ACCOUNTS_SAME = 1012;
/*  35:    */   public static final int BSE_ACCOUNT_NOT_PAGED = 1013;
/*  36:    */   public static final int BSE_INVALID_REFERENCE_NUM = 1080;
/*  37:    */   public static final int ERROR_GET_ACCOUNTS = 2000;
/*  38:    */   public static final int ERROR_SUMMARIES = 2001;
/*  39:    */   public static final int ERROR_CLASS_INIT = 2002;
/*  40:    */   public static final int ERROR_GET_TRANSACTIONS = 2003;
/*  41:    */   public static final int ERROR_ACCOUNT_SERVICE_NOT_FOUND = 2004;
/*  42:    */   public static final int ERROR_ACOUNT_RETRIEVAL_FAILED = 2005;
/*  43:    */   public static final int ERROR_REPORT_DATA = 2006;
/*  44:    */   public static final int ERROR_BANKING_SERVICE_NOT_FOUND = 2007;
/*  45:    */   public static final int BSE_INVALID_CREDIT_ITEM_INDEX = 3000;
/*  46:    */   public static final int BSE_INVALID_TRANSACTION_INDEX = 3001;
/*  47:    */   private Throwable jdField_if;
/*  48:    */   private int a;
/*  49:    */   
/*  50:    */   public BSException(int paramInt)
/*  51:    */   {
/*  52: 72 */     this.a = paramInt;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public BSException(int paramInt, String paramString)
/*  56:    */   {
/*  57: 76 */     super(paramString);
/*  58: 77 */     this.a = paramInt;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public BSException(int paramInt, String paramString, Throwable paramThrowable)
/*  62:    */   {
/*  63: 82 */     super(paramString);
/*  64: 83 */     this.a = paramInt;
/*  65: 84 */     this.jdField_if = paramThrowable;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public BSException(int paramInt, Throwable paramThrowable)
/*  69:    */   {
/*  70: 90 */     this.a = paramInt;
/*  71: 91 */     this.jdField_if = paramThrowable;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void printStackTrace()
/*  75:    */   {
/*  76:100 */     super.printStackTrace();
/*  77:101 */     if (this.jdField_if != null) {
/*  78:102 */       this.jdField_if.printStackTrace();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void printStackTrace(PrintWriter paramPrintWriter)
/*  83:    */   {
/*  84:108 */     super.printStackTrace(paramPrintWriter);
/*  85:109 */     if (this.jdField_if != null) {
/*  86:110 */       this.jdField_if.printStackTrace(paramPrintWriter);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void printStackTrace(PrintStream paramPrintStream)
/*  91:    */   {
/*  92:116 */     super.printStackTrace(paramPrintStream);
/*  93:117 */     if (this.jdField_if != null) {
/*  94:118 */       this.jdField_if.printStackTrace(paramPrintStream);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Throwable getChained()
/*  99:    */   {
/* 100:124 */     return this.jdField_if;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getErrorCode()
/* 104:    */   {
/* 105:129 */     return this.a;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getMessage()
/* 109:    */   {
/* 110:136 */     if ((super.getMessage() == null) && (this.jdField_if != null)) {
/* 111:136 */       return this.jdField_if.getMessage();
/* 112:    */     }
/* 113:140 */     return super.getMessage();
/* 114:    */   }
/* 115:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.interfaces.BSException
 * JD-Core Version:    0.7.0.1
 */