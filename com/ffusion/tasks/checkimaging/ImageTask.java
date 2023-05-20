package com.ffusion.tasks.checkimaging;

import com.ffusion.tasks.Task;

public abstract interface ImageTask
  extends Task
{
  public static final int DEFAULT_MAX_AGE = 24;
  public static final int MAXIMAGETYPES = 4;
  public static final int PNG = 0;
  public static final int JPG = 1;
  public static final int GIF = 2;
  public static final int TIF = 3;
  public static final String[] MIMETYPES = { "png", "jpeg", "gif", "tiff" };
  public static final String[] FILEEXTENSIONS = { "png", "jpg", "gif", "tif" };
  public static final String IMAGESERVICE = "com.ffusion.service.CheckImageService";
  public static final String SECURE_USER = "SecureUser";
  public static final String USER = "User";
  public static final String ADMINUSER = "AdminUser";
  public static final String DATE_FORMAT = "MM-dd-yyyy HH:mm:ss";
  public static final String ACCOUNT = "Account";
  public static final String BUSINESSSERVICE = "BusinessService";
  public static final String IMAGEREQUEST = "ImageRequest";
  public static final String IMAGE_RESULT = "ImageResult";
  public static final String IMAGE_RESULTS = "ImageResults";
  public static final String PREVIOUS_RESULTS = "PreviousResults";
  public static final String OFFSET_RESULTS = "OffsetResults";
  public static final String AVAILABLE_IMAGES = "AvailableImages";
  public static final String CHECKED_IMAGES = "CheckedImages";
  public static final String CHECKED_IMAGES_ID = "CheckedImagesId";
  public static final String TRANSACTIONS = "Transactions";
  public static final String TRANSACTION = "Transaction";
  public static final String STATEMENT = "Statement";
  public static final String DEPOSIT_RESULT = "DepositResult";
  public static final String DEPOSIT_RESULTS = "DepositResults";
  public static final String OFFSET_PAGES = "OffsetPages";
  public static final String REQUESTED_CHECK_IMAGES = "RequestedCheckImages";
  public static final String EMAIL_IMAGE_RESULTS = "EmailImageResults";
  public static final String DELETE_IMAGE_RESULTS = "DeleteImages";
  public static final String PPAY_ISSUE = "PPayIssue";
  public static final String PPAY_ISSUES = "PPayIssues";
  public static final String REG_TRANSACTIONS = "RegisterTransactions";
  public static final String ESTIMATE_TWO_MINUTES = "2 Minutes";
  public static final String ESTIMATE_FOUR_MINUTES = "4 Minutes";
  public static final String ACCOUNTNUMBER = "ACCOUNTNUMBER";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String POSTINGDATEFROM = "POSTINGDATEFROM";
  public static final String POSTINGDATETO = "POSTINGDATETO";
  public static final String TRACEIDFROM = "TRACEIDFROM";
  public static final String TRACEIDTO = "TRACEIDTO";
  public static final String AMOUNTFROM = "AMOUNTFROM";
  public static final String AMOUNTTO = "AMOUNTTO";
  public static final String CHECKNUMBERFROM = "CHECKNUMBERFROM";
  public static final String CHECKNUMBERTO = "CHECKNUMBERTO";
  public static final String ROUTINGTRANSITNUMBER = "ROUTINGTRANSITNUMBER";
  public static final String DEPOSITTRACEID = "DEPOSITTRACEID";
  public static final String MINIMUMFIELDS = "MINIMUMFIELDS";
  public static final String TOADDRESS = "TOADDRESS";
  public static final String FROMADDRESS = "FROMADDRESS";
  public static final String DEBIT = "Debit";
  public static final String CREDIT = "Credit";
  public static final String IMAGE_LOG_ENTRY = "ImageLogEntry";
  public static final String IMAGEERROR = "ImageError";
  public static final int ERROR_ACCOUNT_NUMBER_NOT_NUMBERIC = 400001;
  public static final int ERROR_ACCOUNT_NUMBER_NOT_ENOUGH_DIGITS = 400002;
  public static final int ERROR_TRACEID_FROM_NOT_NUMERIC = 400003;
  public static final int ERROR_TRACEID_FROM_NOT_ENOUGH_DIGITS = 400004;
  public static final int ERROR_TRACEID_TO_NOT_NUMERIC = 400005;
  public static final int ERROR_TRACEID_TO_NOT_ENOUGH_DIGITS = 400006;
  public static final int ERROR_TRACEID_XML_FAIL = 400007;
  public static final int ERROR_CHECK_NUMBER_FROM_NOT_NUMERIC = 400008;
  public static final int ERROR_CHECK_NUMBER_FROM_NOT_ENOUGH_DIGITS = 400009;
  public static final int ERROR_CHECK_NUMBER_TO_NOT_NUMERIC = 400010;
  public static final int ERROR_CHECK_NUMBER_TO_NOT_ENOUGH_DIGITS = 400011;
  public static final int ERROR_ROUTING_TRANSIT_NUMBER_NOT_NUMERIC = 400012;
  public static final int ERROR_ROUTING_TRANSIT_NUMBER_NOT_ENOUGH_DIGITS = 400013;
  public static final int ERROR_DEPOSIT_TRACEID_NOT_NUMERIC = 400014;
  public static final int ERROR_DEPOSIT_TRACEID_NOT_ENOUGH_DIGITS = 400015;
  public static final int ERROR_INVALID_POSTING_DATE_FROM = 400016;
  public static final int ERROR_INVALID_POSTING_DATE_TO = 400017;
  public static final int ERROR_INVALID_AMOUNT_FROM = 400018;
  public static final int ERROR_INVALID_AMOUNT_TO = 400019;
  public static final int ERROR_BEFORE_SEARCH_DATE = 400020;
  public static final int ERROR_XML_NOT_CONFIGURED = 400021;
  public static final int ERROR_NOT_ENOUGH_FIELDS = 400022;
  public static final int ERROR_ACCOUNT_NOT_FOUND = 400023;
  public static final int ERROR_INVALID_EMAIL = 400024;
  public static final int ERROR_INVALID_INIT_URL = 400025;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 400026;
  public static final int ERROR_NO_IMAGE_REQUEST_IN_SESSION = 400027;
  public static final int ERROR_TO_ADDRESS_INVALID = 400028;
  public static final int ERROR_FROM_ADDRESS_INVALID = 400029;
  public static final int ERROR_NO_IMAGE_RESULT_FOUND = 400030;
  public static final int ERROR_TO_ADDRESS_REQUIRED = 400031;
  public static final int ERROR_FROM_ADDRESS_REQUIRED = 400032;
  public static final int ERROR_NO_LOCKBOX_CREDIT_ITEM_FOUND = 400033;
  public static final int ERROR_NO_LOCKBOX_CREDIT_ITEM_ID_FOUND = 400034;
  public static final int ERROR_NO_LOCKBOX_CREDIT_ITEMS_IN_SESSION = 400035;
  public static final int ERROR_NO_MODULE = 400036;
  public static final int ERROR_IMAGE_ID_NOT_FOUND = 400037;
  public static final int ERROR_TRANS_ID_NOT_FOUND = 400038;
  public static final int ERROR_NO_TRANSACTION_IN_SESSION = 400039;
  public static final int ERROR_BANK_ID_NOT_FOUND = 400040;
  public static final int ERROR_CHECK_NUMBER_NOT_FOUND = 400041;
  public static final int ERROR_PPAY_CHECK_RECORD_NOT_FOUND = 400042;
  public static final int ERROR_NO_PPAY_ISSUES_IN_SESSION = 400043;
  public static final int ERROR_INVALID_RECORD_ID = 400044;
  public static final int ERROR_INVALID_OR_NULL_CHECK_DATE = 400045;
  public static final int ERROR_INVALID_OR_NULL_CHECK_AMOUNT = 400046;
  public static final int ERROR_FROM_DATE_EALIER_THAN_TO_DATE = 400047;
  public static final int ERROR_IMAGEDISPLAY_UNABLE_TO_READ = 400050;
  public static final int ERROR_IMAGEDISPLAY_UNABLE_TO_FIND_READER = 400051;
  public static final int ERROR_IMAGEDISPLAY_UNABLE_TO_FIND_WRITER = 400052;
  public static final int ERROR_IMAGEDISPLAY_UNABLE_TO_WRITE = 400053;
  public static final int ERROR_IMAGEDISPLAY_UNABLE_TO_ROTATE = 400054;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.checkimaging.ImageTask
 * JD-Core Version:    0.7.0.1
 */