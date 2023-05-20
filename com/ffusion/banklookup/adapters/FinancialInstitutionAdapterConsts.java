/*   1:    */ package com.ffusion.banklookup.adapters;
/*   2:    */ 
/*   3:    */ public abstract interface FinancialInstitutionAdapterConsts
/*   4:    */ {
/*   5:    */   public static final String FI_TABLE_NAME = "LU_FinancialInstitution";
/*   6:    */   public static final String FI_RTN_TABLE_NAME = "LU_FinancialInstRtn";
/*   7:    */   public static final String FI_TABLE_NAME_ALIAS = "fi";
/*   8:    */   public static final String FI_RTN_TABLE_NAME_ALIAS = "firtn";
/*   9:    */   public static final String COLUMN_INSTITUTIONID = "InstitutionId";
/*  10:    */   public static final String COLUMN_INSTITUTIONNAME = "InstitutionName";
/*  11:    */   public static final String COLUMN_BRANCHNAME = "BranchName";
/*  12:    */   public static final String COLUMN_ADDRESSLINE1 = "AddressLine1";
/*  13:    */   public static final String COLUMN_ADDRESSLINE2 = "AddressLine2";
/*  14:    */   public static final String COLUMN_ADDRESSLINE3 = "AddressLine3";
/*  15:    */   public static final String COLUMN_CITY = "City";
/*  16:    */   public static final String COLUMN_STATE = "State";
/*  17:    */   public static final String COLUMN_STATECODE = "StateCode";
/*  18:    */   public static final String COLUMN_POSTALCODE = "PostalCode";
/*  19:    */   public static final String COLUMN_COUNTRY = "Country";
/*  20:    */   public static final String COLUMN_PHONE = "Phone";
/*  21:    */   public static final String COLUMN_ACHROUTINGNUMBER = "AchRoutingNumber";
/*  22:    */   public static final String COLUMN_WIREROUTINGNUMBER = "WireRoutingNumber";
/*  23:    */   public static final String COLUMN_SWIFTBIC = "SwiftBIC";
/*  24:    */   public static final String COLUMN_CHIPSUID = "ChipsUID";
/*  25:    */   public static final String COLUMN_NATIONALID = "NationalID";
/*  26:    */   public static final String COLUMN_ALIAS_INSTITUTIONID = "fi.InstitutionId";
/*  27:    */   public static final String COLUMN_ALIAS_INSTITUTIONNAME = "fi.InstitutionName";
/*  28:    */   public static final String COLUMN_ALIAS_BRANCHNAME = "fi.BranchName";
/*  29:    */   public static final String COLUMN_ALIAS_ADDRESSLINE1 = "fi.AddressLine1";
/*  30:    */   public static final String COLUMN_ALIAS_ADDRESSLINE2 = "fi.AddressLine2";
/*  31:    */   public static final String COLUMN_ALIAS_ADDRESSLINE3 = "fi.AddressLine3";
/*  32:    */   public static final String COLUMN_ALIAS_CITY = "fi.City";
/*  33:    */   public static final String COLUMN_ALIAS_STATE = "fi.State";
/*  34:    */   public static final String COLUMN_ALIAS_STATECODE = "fi.StateCode";
/*  35:    */   public static final String COLUMN_ALIAS_POSTALCODE = "fi.PostalCode";
/*  36:    */   public static final String COLUMN_ALIAS_COUNTRY = "fi.Country";
/*  37:    */   public static final String COLUMN_ALIAS_PHONE = "fi.Phone";
/*  38:    */   public static final String COLUMN_ALIAS_ACHROUTINGNUMBER = "fi.AchRoutingNumber";
/*  39:    */   public static final String COLUMN_ALIAS_WIREROUTINGNUMBER = "fi.WireRoutingNumber";
/*  40:    */   public static final String COLUMN_ALIAS_SWIFTBIC = "fi.SwiftBIC";
/*  41:    */   public static final String COLUMN_ALIAS_CHIPSUID = "fi.ChipsUID";
/*  42:    */   public static final String COLUMN_ALIAS_NATIONALID = "fi.NationalID";
/*  43:    */   public static final String COLUMN_ROUTINGNUMBER = "RoutingNumber";
/*  44:    */   public static final String COLUMN_ALIAS_ROUTINGNUMBER = "firtn.AchRoutingNumber";
/*  45:    */   public static final String FI_SEQUENCE_NAME = "FinancialInstitution_ID";
/*  46:    */   public static final String COLUMN_ALTERNATEROUTINGNUMBER = "AchRoutingNumber";
/*  47:    */   public static final String FINANCIAL_INSTITUTION_RTN_COLUMNS = "firtn.AchRoutingNumber";
/*  48:    */   public static final String FI_BASE_JOIN = "LU_FinancialInstitution fi,LU_FinancialInstRtn firtn  WHERE fi.InstitutionId = firtn.InstitutionId";
/*  49:    */   public static final String FINANCIAL_INSTITUTION_ALIAS_COLUMNS = " fi.InstitutionId,fi.InstitutionName,fi.BranchName,fi.AddressLine1,fi.AddressLine2,fi.AddressLine3,fi.City,fi.State,fi.StateCode,fi.PostalCode,fi.Country,fi.Phone,fi.AchRoutingNumber,fi.WireRoutingNumber,fi.SwiftBIC,fi.ChipsUID,fi.NationalID,firtn.AchRoutingNumber";
/*  50:    */   public static final String FINANCIAL_INSTITUTION_COLUMNS = " InstitutionId,InstitutionName,BranchName,AddressLine1,AddressLine2,AddressLine3,City,State,StateCode,PostalCode,Country,Phone,AchRoutingNumber,WireRoutingNumber,SwiftBIC,ChipsUID,NationalID";
/*  51:278 */   public static final String[] FI_COLUMNS = { "InstitutionId", "InstitutionName", "BranchName", "AddressLine1", "AddressLine2", "AddressLine3", "City", "State", "StateCode", "PostalCode", "Country", "Phone", "AchRoutingNumber", "WireRoutingNumber", "SwiftBIC", "ChipsUID", "NationalID" };
/*  52:301 */   public static final String[] FI_KEYS = { "InstitutionId" };
/*  53:306 */   public static final String[][] FI_KEYS_TO_CHECK = { { "InstitutionId" }, { "AchRoutingNumber" }, { "InstitutionName", "BranchName", "City", "StateCode", "PostalCode", "Country" } };
/*  54:    */ }


/* Location:           D:\drops\jd\jars\ffiblcommon.jar
 * Qualified Name:     com.ffusion.banklookup.adapters.FinancialInstitutionAdapterConsts
 * JD-Core Version:    0.7.0.1
 */