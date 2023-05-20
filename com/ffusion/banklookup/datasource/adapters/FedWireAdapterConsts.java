/*  1:   */ package com.ffusion.banklookup.datasource.adapters;
/*  2:   */ 
/*  3:   */ public abstract interface FedWireAdapterConsts
/*  4:   */ {
/*  5:   */   public static final String TABLE = "LU_FedWire";
/*  6:   */   public static final String COLUMN_ID = "InstitutionId";
/*  7:   */   public static final String COLUMN_ROUTINGNUMBER = "RoutingNumber";
/*  8:   */   public static final String COLUMN_TELEGRAPHICNAME = "TelegraphicName";
/*  9:   */   public static final String COLUMN_CUSTOMERNAME = "CustomerName";
/* 10:   */   public static final String COLUMN_CITY = "City";
/* 11:   */   public static final String COLUMN_STATECODE = "StateCode";
/* 12:   */   public static final String COLUMN_FUNDSTRANSFERSTATUS = "FundsTransferStatus";
/* 13:   */   public static final String COLUMN_FUNDSSETTLEMENTSTATUS = "FundsSettlementStatus";
/* 14:   */   public static final String COLUMN_SECURITIESTRANSFERSTATUS = "SecuritiesTransferStatus";
/* 15:   */   public static final String COLUMN_CHANGEDATE = "ChangeDate";
/* 16:57 */   public static final String[] COLUMNS = { "InstitutionId", "RoutingNumber", "TelegraphicName", "CustomerName", "City", "StateCode", "FundsTransferStatus", "FundsSettlementStatus", "SecuritiesTransferStatus", "ChangeDate" };
/* 17:   */ }


/* Location:           D:\drops\jd\jars\ffiblcustom.jar
 * Qualified Name:     com.ffusion.banklookup.datasource.adapters.FedWireAdapterConsts
 * JD-Core Version:    0.7.0.1
 */