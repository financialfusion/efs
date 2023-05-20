package com.ffusion.beans.affiliatebank;

public abstract interface CutOffDefines
{
  public static final String PMT_GROUP_ACH_BATCH = "ACHBatch";
  public static final String PMT_GROUP_ACH_FILE_UPLOAD = "ACHFileUpload";
  public static final String PMT_GROUP_BILL_PAY = "BillPay";
  public static final String PMT_GROUP_BOOK_TRANSFER = "BookTransfer";
  public static final String PMT_GROUP_CASH_CON = "CashConcentration";
  public static final String PMT_GROUP_EXTERNAL_TRANSFER = "ExternalTransfer";
  public static final String PMT_GROUP_WIRES = "Wires";
  public static final String PMT_GROUP_SYSTEM = "System";
  public static final String PMT_GROUP_CUSTOM = "Other";
  public static final String PMT_GROUP_POSITIVE_PAY = "PositivePay";
  public static final String CUT_OFF_ACTIVE = "ACTIVE";
  public static final String CUT_OFF_INACTIVE = "INACTIVE";
  public static final String CUT_OFF_DELETED = "CANCELEDON";
  public static final int SCHEDULE_DEFUALT_INTERVAL = 1440;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.CutOffDefines
 * JD-Core Version:    0.7.0.1
 */