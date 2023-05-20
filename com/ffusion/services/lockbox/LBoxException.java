package com.ffusion.services.lockbox;

public class LBoxException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public int serviceError = 0;
  public static final int ERROR_GET_ACCOUNTS = 39500;
  public static final int ERROR_SUMMARIES = 39501;
  public static final int ERROR_CLASS_INIT = 39502;
  public static final int ERROR_GET_TRANSACTIONS = 39503;
  public static final int ERROR_CREDIT_ITEMS = 39504;
  public static final int ERROR_REPORT_DATA = 39505;
  public static final int MISSING_PARAMETERS = 39506;
  public static final int ERROR_NOT_SUPPORTED = 39507;
  public static final int SEARCH_CRITERIA_DNE = 39508;
  public static final int UNABLE_TO_GENERATE_REPORT = 39509;
  public static final int SEARCH_CRITERIA_ACCOUNTS_FORMAT = 39510;
  public static final int BANK_ID_DOES_NOT_EXIST = 39511;
  public static final int ACCOUNT_ID_DOES_NOT_EXIST = 39512;
  public static final int FAILED_TO_GET_LOCKBOX_SUMMARIES = 39513;
  public static final int FAILED_TO_ADD_LOCKBOX_SUMMARY = 39514;
  public static final int FAILED_TO_GET_LOCKBOX_CREDIT_ITEMS = 39515;
  public static final int FAILED_TO_ADD_LOCKBOX_CREDIT_ITEMS = 39516;
  public static final int INVALID_LOCKBOX_CREDIT_ITEM_ID = 39517;
  public static final int FAILED_TO_GET_LOCKBOX_TRANSACTIONS = 39518;
  public static final int FAILED_TO_ADD_LOCKBOX_TRANSACTIONS = 39519;
  public static final int INVALID_LOCKBOX_TRANSACTION_ID = 39520;
  public static final int EXTRA_DNE = 39521;
  public static final int REPORT_DATE_RANGE_TOO_WIDE = 39530;
  public static final int REPORT_EXTRA_MISSING_START_DATES = 39531;
  public static final int REPORT_EXTRA_MISSING_END_DATES = 39532;
  public static final int REPORT_MISSING_START_DATE = 39533;
  public static final int REPORT_MISSING_END_DATE = 39534;
  public static final int REPORT_INVALID_START_DATE = 39535;
  public static final int REPORT_INVALID_END_DATE = 39536;
  public static final int REPORT_OPTIONS_DNE = 39537;
  public static final int REPORT_SORT_CRITERIA_DNE = 39538;
  public static final int REPORT_TYPE_DNE = 39539;
  public static final int REPORT_SEARCH_CRITERIA_MISSING_LOCKBOX_SPECIFICATION = 39540;
  public static final int REPORT_EXTRA_MISSING_LOCKBOX_SPECIFICATION = 39541;
  public static final int ACCOUNT_MISSING_ROUTING_NUMBER = 39542;
  public static final int MISSING_DATACLASSIFICATION = 39543;
  public static final int INVALID_DATACLASSIFICATION = 39544;
  public static final int PAYOR_TOO_LONG = 39545;
  public static final int CHECK_NUMBER_TOO_LONG = 39546;
  public static final int DOCUMENT_TYPE_TOO_LONG = 39547;
  public static final int COUPON_ACC_NUM_TOO_LONG = 39548;
  public static final int COUPON_REF_NUM_TOO_LONG = 39549;
  public static final int CHECK_ROUTING_NUM_TOO_LONG = 39550;
  public static final int CHECK_ACC_NUM_TOO_LONG = 39551;
  public static final int INVALID_MINAMOUNT = 39552;
  public static final int INVALID_MAXAMOUNT = 39553;
  public static final int INVALID_COUPON_AMOUNT = 39554;
  public static final int INVALID_COUPON_DATE1 = 39555;
  public static final int INVALID_COUPON_DATE2 = 39556;
  public static final int LOCKBOX_WORK_TYPE_TOO_LONG = 39557;
  public static final int LOCKBOX_BATCH_NUM_TOO_LONG = 39558;
  public static final int LOCKBOX_SEQ_NUM_TOO_LONG = 39559;
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
  
  public LBoxException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public LBoxException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public LBoxException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public LBoxException(int paramInt1, int paramInt2)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt1;
    this.serviceError = paramInt2;
  }
  
  public LBoxException(String paramString, int paramInt, Exception paramException)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public LBoxException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public void setServiceError(int paramInt)
  {
    this.serviceError = paramInt;
  }
  
  public int getServiceError()
  {
    return this.serviceError;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.where != null) && (this.where.length() > 0)) {
      localStringBuffer.append(this.where);
    }
    if ((this.why != null) && (this.why.length() > 0)) {
      localStringBuffer.append(": ").append(this.why);
    }
    if (this.code != 0) {
      localStringBuffer.append(": error code:" + String.valueOf(this.code) + " ");
    }
    if (this.childException != null) {
      localStringBuffer.append(this.childException.getMessage());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.lockbox.LBoxException
 * JD-Core Version:    0.7.0.1
 */