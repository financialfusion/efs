/*  1:   */ package com.ffusion.banklookup.datasource.adapters;
/*  2:   */ 
/*  3:   */ public abstract interface FedAchAdapterConsts
/*  4:   */ {
/*  5:   */   public static final String TABLE = "LU_FedACH";
/*  6:   */   public static final String COLUMN_ID = "FedInstitutionId";
/*  7:   */   public static final String COLUMN_ROUTINGNUMBER = "RoutingNumber";
/*  8:   */   public static final String COLUMN_ACHROUTINGNUMBER = "ACHRoutingNumber";
/*  9:   */   public static final String COLUMN_OFFICECODE = "OfficeCode";
/* 10:   */   public static final String COLUMN_SERVICINGFRB = "ServicingFRB";
/* 11:   */   public static final String COLUMN_RECORDTYPE = "RecordType";
/* 12:   */   public static final String COLUMN_CHANGEDATE = "ChangeDate";
/* 13:   */   public static final String COLUMN_NEWROUTINGNUMBER = "NewRoutingNumber";
/* 14:   */   public static final String COLUMN_NEWACHROUTINGNUMBER = "NewACHRoutingNumber";
/* 15:   */   public static final String COLUMN_CUSTOMERNAME = "CustomerName";
/* 16:   */   public static final String COLUMN_ADDRESS = "Address";
/* 17:   */   public static final String COLUMN_CITY = "City";
/* 18:   */   public static final String COLUMN_STATECODE = "StateCode";
/* 19:   */   public static final String COLUMN_ZIPCODE = "Zipcode";
/* 20:   */   public static final String COLUMN_ZIPCODEEXTENSION = "ZipcodeExtension";
/* 21:   */   public static final String COLUMN_TELEPHONEAREACODE = "TelephoneAreaCode";
/* 22:   */   public static final String COLUMN_TELEPHONEPREFIXNUM = "TelephonePrefixNum";
/* 23:   */   public static final String COLUMN_TELEPHONESUFFIXNUM = "TelephoneSuffixNum";
/* 24:   */   public static final String COLUMN_INSTITUTIONSTATUSCODE = "InstitutionStatusCode";
/* 25:   */   public static final String COLUMN_DATAVIEWCODE = "DataViewCode";
/* 26:98 */   public static final String[] COLUMNS = { "FedInstitutionId", "RoutingNumber", "OfficeCode", "ServicingFRB", "RecordType", "ChangeDate", "NewRoutingNumber", "CustomerName", "Address", "City", "StateCode", "Zipcode", "ZipcodeExtension", "TelephoneAreaCode", "TelephonePrefixNum", "TelephoneSuffixNum", "InstitutionStatusCode", "DataViewCode" };
/* 27:   */ }


/* Location:           D:\drops\jd\jars\ffiblcustom.jar
 * Qualified Name:     com.ffusion.banklookup.datasource.adapters.FedAchAdapterConsts
 * JD-Core Version:    0.7.0.1
 */