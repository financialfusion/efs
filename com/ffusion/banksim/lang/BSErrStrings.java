/*  1:   */ package com.ffusion.banksim.lang;
/*  2:   */ 
/*  3:   */ import com.ffusion.banksim.IBSErrConstants;
/*  4:   */ import java.util.ListResourceBundle;
/*  5:   */ 
/*  6:   */ public class BSErrStrings
/*  7:   */   extends ListResourceBundle
/*  8:   */   implements IBSErrConstants
/*  9:   */ {
/* 10:   */   public Object[][] getContents()
/* 11:   */   {
/* 12:16 */     return if;
/* 13:   */   }
/* 14:   */   
/* 15:19 */   private static final String jdField_do = System.getProperty("line.separator");
/* 16:21 */   static final Object[][] jdField_if = { { "ERR_NOT_INITIALIZED", "The Bank Simulator was not initialized properly." + jdField_do + "Use the BankSim.initialize() to initialize the Bank Simulator." }, { "ERR_ALREADY_INITIALIZED", "The Bank Simulator has already been initialized" }, { "ERR_UNKNOWN_DB_TYPE", "The database type value passed in is unknown" } };
/* 17:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.lang.BSErrStrings
 * JD-Core Version:    0.7.0.1
 */