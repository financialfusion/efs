package com.ffusion.dataconsolidator.adapter;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DCException
  extends Exception
{
  public static final int GENERAL_ERROR = 300;
  public static final int UNABLE_TO_GET_DB_CONNECTION = 301;
  public static final int INTERNAL_ERROR_SQLEXCEPTION = 302;
  public static final int FAILED_TO_GENERATE_ID = 303;
  public static final int FAILED_TO_GET_SQL_STATEMENT = 304;
  public static final int UNABLE_TO_RELEASE_CONNECTION = 305;
  public static final int FAILED_TO_COMMIT = 306;
  public static final int FAILED_TO_ADD_EXTENDABEANXML = 307;
  public static final int FAILED_TO_DELETE_EXTENDABEANXML = 308;
  public static final int FAILED_TO_GET_EXTENDABEANXML = 309;
  public static final int FAILED_TO_FILL_EXTENDABEAN = 310;
  public static final int FAILED_TO_CHECK_RECORD_COUNTER = 311;
  public static final int EXCEPTION_BUILDING_AND_EXECUTING_QUERY = 312;
  public static final int EXCEPTION_GETTING_REPORT_DATA = 313;
  public static final int MISSING_SEARCH_CRITERIA = 314;
  public static final int MISSING_REPORT_OPTIONS = 315;
  public static final int MISSING_SORT_CRITERIA = 316;
  public static final int MISSING_EXTRA = 317;
  public static final int MISSING_REPORT_TYPE = 318;
  public static final int INVALID_REPORT_TYPE = 319;
  public static final int MISSING_START_DATE = 320;
  public static final int MISSING_END_DATE = 321;
  public static final int MISSING_START_DATES_FOR_REPORT = 322;
  public static final int MISSING_END_DATES_FOR_REPORT = 323;
  public static final int MISSING_FORECAST_DATE = 324;
  public static final int INVALID_FORECAST_DATE = 325;
  public static final int NO_ACCOUNTS_SPECIFIED_IN_SEARCH_CRITERIA = 326;
  public static final int NO_ACCOUNTS_SPECIFIED_IN_EXTRA = 327;
  public static final int ACCOUNT_MISSING_ROUTING_NUMBER = 328;
  public static final int INVALID_ACCOUNT_SPECIFICATION_IN_SEARCH_CRITERIA = 329;
  public static final int MISSING_DATACLASSIFICATION = 330;
  public static final int INVALID_DATACLASSIFICATION = 331;
  public static final int INVALID_MINAMOUNT = 332;
  public static final int INVALID_MAXAMOUNT = 333;
  public static final int CUSTOMER_REFERENCE_NUMBER_TOO_LONG = 334;
  public static final int BANK_REFERENCE_NUMBER_TOO_LONG = 335;
  public static final int INVALID_SUMMARY_CODE = 336;
  public static final int INVALID_TRANSACTION_TYPE = 337;
  public static final int INVALID_TRANSACTION_SUBTYPE = 338;
  public static final int INVALID_CUSTOM_TRANSACTION_GROUP_CODES = 339;
  public static final int MISSING_CUSTOM_TRANSACTION_GROUP_CODES = 340;
  public static final int TRANSACTIONS_OR_CUSTOM_TRANSACTION_GROUPS_IMPROPERLY_SPECIFIED = 341;
  public static final int NO_LOCKBOXES_SPECIFIED_IN_SEARCH_CRITERIA = 342;
  public static final int NO_LOCKBOXES_SPECIFIED_IN_EXTRA = 343;
  public static final int PAYOR_TOO_LONG = 344;
  public static final int CHECK_NUMBER_TOO_LONG = 345;
  public static final int DOC_TYPE_TOO_LONG = 346;
  public static final int COUPON_ACC_NUM_TOO_LONG = 347;
  public static final int INVALID_COUPON_AMOUNT = 348;
  public static final int COUPON_REF_NUM_TOO_LONG = 349;
  public static final int CHECK_ROUTING_NUM_TOO_LONG = 350;
  public static final int CHECK_ACC_NUM_TOO_LONG = 351;
  public static final int LOCKBOX_WORK_TYPE_TOO_LONG = 352;
  public static final int LOCKBOX_BATCH_NUM_TOO_LONG = 353;
  public static final int LOCKBOX_SEQ_NUM_TOO_LONG = 354;
  public static final int INVALID_COUPON_DATE1 = 355;
  public static final int INVALID_COUPON_DATE2 = 356;
  public static final int DATE_RANGE_TOO_WIDE = 357;
  public static final int INVALID_START_DATE = 358;
  public static final int INVALID_END_DATE = 359;
  public static final int SMART_CALENDAR_FAIL_ON_RETRIEVAL = 360;
  public static final int AFFILIATE_BANK_RESTRICTED_CALCULATION_RETRIEVAL_FAILED = 361;
  public static final int ACCOUNT_RESTRICTED_CALCULATION_RETRIEVAL_FAILED = 362;
  public static final int FAILED_TO_RETRIEVE_BUSINESS_EMPLOYEE = 363;
  public static final int FAILED_TO_UPDATE_BUSINESS_EMPLOYEE = 364;
  public static final int INVALID_DATASOURCE_LOAD_TIME = 365;
  public static final int INVALID_AMOUNT_RANGE = 366;
  public static final int UNABLE_TO_GET_ACCOUNTS = 414;
  public static final int UNABLE_TO_RETRIEVE_DEPOSIT_INSTRUMENT = 415;
  public static final int UNABLE_TO_UPDATE_DEPOSIT_INSTRUMENT = 416;
  public static final int UNABLE_TO_RETRIEVE_RECENT_TRANSACTIONS = 417;
  public static final int UNABLE_TO_RETRIEVE_NUM_TRANSACTIONS = 418;
  public static final int UNABLE_TO_RETRIEVE_TRANSACTIONS = 419;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_HISTORY = 420;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_SUMMARY = 421;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_EXTD_SUMMARY = 422;
  public static final int ACCOUNT_ALREADY_EXISTS = 423;
  public static final int BANK_ID_DOES_NOT_EXIST = 424;
  public static final int ACCOUNT_ID_DOES_NOT_EXIST = 425;
  public static final int CURRENCY_CODE_DOES_NOT_EXIST = 426;
  public static final int UNABLE_TO_UPDATE_ACCOUNTS = 427;
  public static final int UNABLE_TO_DELETE_ACCOUNTS = 428;
  public static final int UNABLE_TO_GET_ACCOUNT_HISTORY = 429;
  public static final int UNABLE_TO_UPDATE_ACCOUNT_HISTORY = 430;
  public static final int UNABLE_TO_DELETE_ACCOUNT_HISTORY = 431;
  public static final int UNABLE_TO_CREATE_ACCOUNT_HISTORY = 432;
  public static final int HISTORY_DATE_DOES_NOT_EXIST = 433;
  public static final int UNABLE_TO_GET_EXTENDABEANXMLID = 434;
  public static final int UNABLE_TO_CREATE_HISTORY_MAPS = 435;
  public static final int SUMMARY_DATE_DOES_NOT_EXIST = 436;
  public static final int UNABLE_TO_ADD_ACCOUNT_SUMMARY = 437;
  public static final int UNABLE_TO_GET_DEPOSIT_SUMMARY = 438;
  public static final int UNABLE_TO_GET_ASSET_SUMMARY = 439;
  public static final int UNABLE_TO_GET_CREDITCARD_SUMMARY = 440;
  public static final int UNABLE_TO_GET_LOAN_SUMMARY = 441;
  public static final int UNABLE_TO_ADD_EXTENDED_ACCOUNT_SUMMARY = 451;
  public static final int UNABLE_TO_ADD_FIXED_DEPOSIT_INSTRUMENT = 452;
  public static final int UNABLE_TO_UPDATE_TRANSACTIONS = 453;
  public static final int UNABLE_TO_DELETE_EXTENDED_ACCOUNT_SUMMARY = 454;
  public static final int UNABLE_TO_REPLACE_ACCOUNT_HISTORY = 455;
  public static final int UNABLE_TO_UPDATE_ACCOUNT_SUMMARY = 456;
  public static final int UNABLE_TO_UPDATE_DATASOURCE_LOAD_TIME = 457;
  public static final int INVALID_ARGUMENT = 458;
  public static final int UNABLE_TO_GET_DEPOSIT_SUMMARY_MAXDATE = 459;
  public static final int UNABLE_TO_GET_ASSET_SUMMARY_MAXDATE = 460;
  public static final int UNABLE_TO_GET_CREDITCARD_SUMMARY_MAXDATE = 461;
  public static final int UNABLE_TO_GET_LOAN_SUMMARY_MAXDATE = 462;
  public static final int UNABLE_TO_GET_ACCOUNT_HISTORY_MAXDATE = 463;
  public static final int UNABLE_TO_RETRIEVE_ACCOUNT_EXTD_SUMMARY_MAXDATE = 464;
  public static final int UNABLE_TO_GET_DISBURSEMENT_SUMMARY = 600;
  public static final int UNABLE_TO_ADD_DISBURSEMENT_SUMMARY = 601;
  public static final int UNABLE_TO_GET_DISBURSEMENT_SUMMARY_FOR_PRESENTMENT = 602;
  public static final int UNABLE_TO_CREATE_DISBURSEMENT_SUMMARY_FROM_DATA = 603;
  public static final int UNABLE_TO_CREATE_DISBURSEMENT_SUMMARY_MAPS = 604;
  public static final int INVALID_DISBURSEMENT_TRANSACTION_INDEX = 605;
  public static final int UNABLE_TO_ADD_DISBURSEMENT_TRANSACTIONS = 606;
  public static final int UNABLE_TO_GET_DISBURSEMENT_TRANSACTIONS = 607;
  public static final int UNABLE_TO_GENERATE_DISBURSEMENT_TRANSACTION_ID = 608;
  public static final int FAILED_TO_UPDATE_DISBURSEMENT_SUMMARY = 613;
  public static final int FAILED_TO_UPDATE_EXTENDED_ACCOUNT_SUMMARY = 614;
  public static final int UNABLE_TO_GET_DISBURSEMENT_SUMMARY_MAXDATE = 615;
  public static final int LOCKBOX_BANK_ID_DOES_NOT_EXIST = 39511;
  public static final int LOCKBOX_ACCOUNT_ID_DOES_NOT_EXIST = 39512;
  public static final int FAILED_TO_GET_LOCKBOX_SUMMARIES = 39513;
  public static final int FAILED_TO_ADD_LOCKBOX_SUMMARY = 39514;
  public static final int FAILED_TO_GET_LOCKBOX_CREDIT_ITEMS = 39515;
  public static final int FAILED_TO_ADD_LOCKBOX_CREDIT_ITEMS = 39516;
  public static final int INVALID_LOCKBOX_CREDIT_ITEM_ID = 39517;
  public static final int FAILED_TO_GET_LOCKBOX_TRANSACTIONS = 39518;
  public static final int FAILED_TO_ADD_LOCKBOX_TRANSACTIONS = 39519;
  public static final int INVALID_LOCKBOX_TRANSACTION_ID = 39520;
  public static final int FAILED_TO_UPDATE_LOCKBOX_SUMMARY = 39522;
  public static final int FAILED_TO_GET_LOCKBOX_SUMMARIES_MAXDATE = 39523;
  public static final int ERROR_NO_SEARCH_CRITERIA = 1017;
  public static final int ERROR_NO_REPORT_OPTIONS = 1022;
  public static final int UNABLE_TO_CLEAR_TRANSACTION_TYPES = 1023;
  public static final int UNABLE_TO_LOAD_TRANSACTION_TYPES = 1024;
  public static final int INVALID_PAGING_SORT_CRITERIA = 1025;
  public static final int ERROR_NO_REPORT_CRITERIA = 1021;
  public static final int ERROR_INCORRECT_START_DATE = 21112;
  public static final int ERROR_INCORRECT_END_DATE = 21113;
  public static final int ERROR_INVALID_CONNECTION_SUPPLIED = 21200;
  public static final int ERROR_INVALID_ASOFDATE_SUPPLIED = 21201;
  public static final int ERROR_INVALID_MASTERSUB_REL_SUPPLIED = 21202;
  public Exception _childException = null;
  protected int _errorCode = 300;
  
  public DCException(Exception paramException)
  {
    super(paramException.getMessage());
    this._childException = paramException;
  }
  
  public DCException(String paramString)
  {
    super(paramString);
  }
  
  public DCException(String paramString, Exception paramException)
  {
    super(paramString);
    this._childException = paramException;
  }
  
  public DCException(int paramInt, String paramString, Exception paramException)
  {
    super(paramString);
    this._childException = paramException;
    this._errorCode = paramInt;
  }
  
  public DCException(int paramInt, String paramString)
  {
    super(paramString);
    this._errorCode = paramInt;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this._childException != null) {
      this._childException.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this._childException != null) {
      this._childException.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this._childException != null) {
      this._childException.printStackTrace(paramPrintStream);
    }
  }
  
  public void setCode(int paramInt)
  {
    this._errorCode = paramInt;
  }
  
  public int getCode()
  {
    return this._errorCode;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCException
 * JD-Core Version:    0.7.0.1
 */