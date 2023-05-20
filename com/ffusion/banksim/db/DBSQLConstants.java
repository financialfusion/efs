/*  1:   */ package com.ffusion.banksim.db;
/*  2:   */ 
/*  3:   */ public abstract interface DBSQLConstants
/*  4:   */ {
/*  5:   */   public static final int ORACLE_MAX_NON_LOB_LEN = 4000;
/*  6:   */   public static final String ORACLE_SMALL_STRING = " ";
/*  7:20 */   public static final byte[] ORACLE_SMALL_BYTES = { 0 };
/*  8:   */   public static final int NAME_LEN = 255;
/*  9:   */   public static final String NULLABLE = " {null|null||null||null} ";
/* 10:   */   public static final String IDENTITY = " {int|numeric(8,0)|int|int|int|int} ";
/* 11:   */   public static final String IDENTITY_COL = " {identity not null primary key|identity not null primary key|not null primary key generated always as identity|not null primary key|not null primary key generated always as identity|identity not null primary key}  ";
/* 12:   */   public static final String VARCHAR = " varchar{|||2||}";
/* 13:   */   public static final String REFERENCES = " {references||references|references|references|references} ";
/* 14:   */   public static final String DEL_CASCADE = " {on delete cascade||on delete cascade|on delete cascade|on delete cascade|on delete cascade} ";
/* 15:   */   public static final String CLOB = " {long varchar|text|clob(1G)|clob|clob(1G)|text} ";
/* 16:   */   public static final String BLOB = " {long binary|image|blob(1G)|blob|blob(1G)|image} ";
/* 17:   */   public static final String NAME = " varchar{|||2||}(255) ";
/* 18:   */   public static final String NAME_ARG = " {?|?|?|rpad(?,255)|?|?} ";
/* 19:   */   public static final String INT64 = " {bigint|numeric(19,0)|bigint|number(19,0)|bigint|numeric(19,0)} ";
/* 20:   */   public static final String ASE_NEWLINE = "\r\n";
/* 21:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBSQLConstants
 * JD-Core Version:    0.7.0.1
 */