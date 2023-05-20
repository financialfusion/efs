/*  1:   */ package com.ffusion.banklookup.beans;
/*  2:   */ 
/*  3:   */ import com.ffusion.util.FilteredList;
/*  4:   */ 
/*  5:   */ public class FinancialInstitutions
/*  6:   */   extends FilteredList
/*  7:   */ {
/*  8:   */   /**
/*  9:   */    * @deprecated
/* 10:   */    */
/* 11:   */   public FinancialInstitution getByID(String fiId)
/* 12:   */   {
/* 13:22 */     return (FinancialInstitution)getFirstByFilter("ID=" + fiId);
/* 14:   */   }
/* 15:   */ }


/* Location:           D:\drops\jd\jars\ffiblcommon.jar
 * Qualified Name:     com.ffusion.banklookup.beans.FinancialInstitutions
 * JD-Core Version:    0.7.0.1
 */