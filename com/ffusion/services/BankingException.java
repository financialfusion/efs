package com.ffusion.services;

public class BankingException
  extends Exception
{
  public static final int ERROR_NONE = 0;
  public static final int DC_EXCEPTION = 1;
  public static final int ACCOUNT_DNE = 2;
  public static final int PAGESIZE_FORMAT = 3;
  public static final int FDINSTRUMENT_DNE = 4;
  public static final int SERVICE_NOT_INIT = 5;
  public static final int EXCEPTION_BUILDING_AND_EXECUTING_QUERY = 312;
  public static final int MISSING_DATACLASSIFICATION = 330;
  public static final int INVALID_DATACLASSIFICATION = 331;
  public static final int INVALID_TRANSACTION_TYPE = 337;
  public static final int INVALID_CUSTOM_TRANSACTION_GROUP_CODES = 339;
  public static final int MISSING_CUSTOM_TRANSACTION_GROUP_CODES = 340;
  public static final int REPORT_CRITERIA_DNE = 401;
  public static final int REPORT_OPTIONS_DNE = 1022;
  public static final int SORT_CRITERIA_DNE = 402;
  public static final int SEARCH_CRITERIA_DNE = 403;
  public static final int SEARCH_CRITERIA_ACCOUNTS_DNE = 404;
  public static final int SEARCH_CRITERIA_ACCOUNTS_FORMAT = 405;
  public static final int SEARCH_CRITERIA_DATE_FORMAT = 406;
  public static final int REPORT_OPTIONS_REPORT_TYPE_DNE = 1023;
  public static final int REPORT_OPTIONS_REPORT_TYPE_FORMAT = 408;
  public static final int ACCOUNT_SERVICE_DNE = 409;
  public static final int ACCOUNT_SERVICE_EXCEPTION = 410;
  public static final int REPORT_TYPE_NOT_SUPPORTED = 411;
  public static final int BPW_SERVICE_DOWN = 412;
  public static final int UNABLE_TO_GENERATE_REPORT = 413;
  public static final int UNABLE_TO_GET_ACCOUNTS = 414;
  public static final int UNABLE_TO_RETRIEVE_DEPOSIT_INSTRUMENT = 415;
  public static final int UNABLE_TO_UPDATE_DEPOSIT_INSTRUMENT = 416;
  public static final int UNABLE_TO_RETRIEVE_RECENT_TRANSACTIONS = 417;
  public static final int UNABLE_TO_RETRIEVE_TRANSACTIONS = 418;
  public static final int UNABLE_TO_RETRIEVE_NUM_TRANSACTIONS = 419;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_HISTORY = 420;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_SUMMARY = 421;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_EXTD_SUMMARY = 422;
  public static final int ACCOUNT_ALREADY_EXISTS = 423;
  public static final int BANK_ID_DOES_NOT_EXIST = 424;
  public static final int ACCOUNT_ID_DOES_NOT_EXIST = 425;
  public static final int CURRENCY_CODE_DOES_NOT_EXIST = 426;
  public static final int UPABLE_TO_UPDATE_ACCOUNTS = 427;
  public static final int UNABLE_TO_DELETE_ACCOUNTS = 428;
  public static final int UNABLE_TO_GET_HISTORY = 429;
  public static final int UNABLE_TO_UPDATE_HISTORY = 430;
  public static final int UNABLE_TO_DELETE_HISTORY = 431;
  public static final int UNABLE_TO_CREATE_HISTORY = 432;
  public static final int HISTORY_DATE_DOES_NOT_EXIST = 433;
  public static final int UNABLE_TO_GET_BEAN_XML_ID = 434;
  public static final int UNABLE_TO_CREATE_HISTORY_LIST = 435;
  public static final int SUMMARY_DATE_DOES_NOT_EXIST = 436;
  public static final int UNABLE_TO_ADD_ACCOUNT_SUMMARY = 437;
  public static final int UNABLE_TO_GET_DEPOSIT_SUMMARY = 438;
  public static final int UNABLE_TO_GET_ASSET_SUMMARY = 439;
  public static final int UNABLE_TO_GET_CREDITCARD_SUMMARY = 440;
  public static final int UNABLE_TO_GET_LOAN_SUMMARY = 441;
  public static final int ERROR_NOT_ENTITLED_TO_ACCOUNT = 442;
  public static final int EXTRA_DNE = 443;
  public static final int REPORT_DATE_RANGE_TOO_WIDE = 444;
  public static final int UNABLE_TO_ADD_EXTENDED_ACCOUNT_SUMMARY = 451;
  public static final int UNABLE_TO_ADD_FIXED_DEPOSIT_INSTRUMENT = 452;
  public static final int ERROR_INVALID_REFERENCE_NUMBER = 1080;
  public static final int UNABLE_TO_LOAD_CONFIG_XML = 465;
  public static final int UNABLE_TO_PARSE_CONFIG_XML = 466;
  public static final int UNABLE_TO_LOAD_REALTIME_CLASS = 467;
  public static final int UNABLE_TO_LOAD_STORED_CLASS = 468;
  public static final int REALTIME_CLASS_INVALID_TYPE = 469;
  public static final int STORED_CLASS_INVALID_TYPE = 470;
  public static final int UNABLE_TO_INSTANTIATE_REALTIME_CLASS = 471;
  public static final int UNABLE_TO_INSTANTIATE_STORED_CLASS = 472;
  public static final int UNABLE_TO_LOCATE_METHOD = 473;
  public static final int UNABLE_TO_DETERMINE_USER_TYPE = 474;
  public static final int UNABLE_TO_INITIALIZE_REALTIME_SVC = 475;
  public static final int UNABLE_TO_INITIALIZE_STORED_SVC = 476;
  int a;
  
  public BankingException(int paramInt)
  {
    this(paramInt, null);
  }
  
  public BankingException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public BankingException(int paramInt, String paramString, Exception paramException)
  {
    super(paramString, paramException);
    this.a = paramInt;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.a != 0) {
      localStringBuffer.append("Error Code: ").append(this.a);
    }
    String str = super.getMessage();
    if (str != null) {
      localStringBuffer.append(' ').append(str);
    }
    return localStringBuffer.toString();
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
  
  public void setErrorCode(int paramInt)
  {
    this.a = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankingException
 * JD-Core Version:    0.7.0.1
 */