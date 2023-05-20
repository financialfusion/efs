package com.ffusion.systemadmin;

public abstract interface SAConstants
{
  public static final int RET_TYPE_INVALID = -1;
  public static final int RET_TYPE_GLOBAL = 0;
  public static final int RET_TYPE_BANK = 1;
  public static final int RET_TYPE_MARKET_SEGMENT = 2;
  public static final int RET_TYPE_SERVICE_PACKAGE = 3;
  public static final int RET_TYPE_BUSINESS = 4;
  public static final int RET_ID_INVALID = -1;
  public static final int RET_ID_GLOBAL = 0;
  public static final String DATA_TYPE_ACH_PAYMENTS = "AchPayments";
  public static final String DATA_TYPE_BILL_PAYMENTS = "BillPayments";
  public static final String DATA_TYPE_CASH_CONCENTRATION = "CashConcentration";
  public static final String DATA_TYPE_POSITIVE_PAY = "PositivePay";
  public static final String DATA_TYPE_TAX_PAYMENTS = "TaxPayments";
  public static final String DATA_TYPE_CHILD_SUPPORT = "ChildSupport";
  public static final String DATA_TYPE_TRANSFERS = "Transfers";
  public static final String DATA_TYPE_EXTERNAL_TRANSFERS = "ExternalTransfers";
  public static final String DATA_TYPE_WIRES = "Wires";
  public static final String DATA_TYPE_FX = "FX";
  public static final String DATA_CLASS_NA = " ";
  public static final int DEF_RETENTION_DAYS = 7;
  public static final int ALL_BANKS = 0;
  public static final int NUM_LEVELS = 5;
  public static final String EXTRA_DEFAULT_SETTINGS_FLAG = "DefaultSettingsFlag";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.systemadmin.SAConstants
 * JD-Core Version:    0.7.0.1
 */