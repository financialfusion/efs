package com.ffusion.efs.adapters.profile;

public class ProfileException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public static final int ERROR_NOT_INITIALIZED = 1;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 2;
  public static final int ERROR_INVALID_PARAM = 3;
  public static final int ERROR_INVALID_ID = 4;
  public static final int ERROR_INVALID_APPLICATION = 5;
  public static final int ERROR_INVALID_USER_NAME = 6;
  public static final int ERROR_USER_NOT_FOUND = 7;
  public static final int ERROR_INVALID_PASSWORD = 8;
  public static final int ERROR_PASSWORD_CHANGE_REQUIRED = 9;
  public static final int ERROR_PASSWORD_AUTHENTICATION_REQUIRED = 10;
  public static final int ERROR_USER_LOCKED_OUT = 11;
  public static final int ERROR_NO_DATA_FOUND = 12;
  public static final int ERROR_NOT_SUPPORTED = 13;
  public static final int ERROR_PASSWORD_HISTORY_CHECK_FAILED = 14;
  public static final int ERROR_BANK_EMPLOYEE_SELF_MODIFY = 15;
  public static final int ERROR_PASSWORD_CHANGE_TOO_SOON = 16;
  public static final int ERROR_PASSWORD_IN_HISTORY = 17;
  public static final int ERROR_WRONG_AFFILIATE_BANK = 18;
  public static final int ERROR_NO_PREFERRED_LANGUAGE = 19;
  public static final int ERROR_FAILED_TO_UPDATE_ENTITLEMENTS = 20;
  public static final int ERROR_FAILED_TO_CREATE_ENTITLEMENT_GROUP_MEMBER = 21;
  public static final int ERROR_INVALID_CREDENTIAL_RESPONSE = 22;
  public static final int ERROR_BUSINESS_NOT_ACTIVE = 23;
  public static final int SUCCESS_CODE = 0;
  public static final int INVALID_SIGNON = 3500;
  public static final int NO_DATABASE_CONNECTION = 3501;
  public static final int USER_NOT_FOUND = 3502;
  public static final int UNABLE_TO_COMPLETE_REQUEST = 3503;
  public static final int NO_DATA_FOUND = 3504;
  public static final int INVALID_DIRECTORY_ID = 3505;
  public static final int INVALID_INIT_FILE = 3506;
  public static final int INIT_FILE_NOT_FOUND = 3507;
  public static final int INVALID_BANK_EMPLOYEE_ID = 3533;
  public static final int INVALID_BANK_ID = 3534;
  public static final int INVALID_MESSAGE_ID = 3543;
  public static final int INVALID_MESSAGE_TYPE = 3544;
  public static final int INVALID_EMPLOYEE_ID = 3567;
  public static final int INVALID_QUEUE_ID = 3604;
  public static final int INVALID_QUEUE_NAME = 3605;
  public static final int INVALID_QUEUE_TYPE = 3606;
  public static final int INVALID_RESPONSE_ID = 3608;
  public static final int INVALID_RESPONSE_NAME = 3609;
  public static final int INVALID_RESPONSE_TEXT = 3610;
  public static final int INVALID_CUST_ID = 3615;
  public static final int INVALID_TO_ID = 3616;
  public static final int INVALID_TO_TYPE = 3617;
  public static final int INVALID_PASSWORD = 3619;
  public static final int INVALID_FROM_ID = 3620;
  public static final int INVALID_FROM_TYPE = 3621;
  public static final int INVALID_ITEM_ID = 3622;
  public static final int INVALID_ACCT_ID = 3624;
  public static final int NO_FIELDS_TO_MODIFY = 3625;
  public static final int INVALID_PRODUCT_ID = 3626;
  public static final int INVALID_CATEGORY_ID = 3627;
  public static final int INVALID_FORM_ID = 3628;
  public static final int INVALID_FORM_NAME = 3629;
  public static final int INVALID_FORM_COLUMNS = 3630;
  public static final int INVALID_STATUS_ID = 3631;
  public static final int INVALID_STATUS_NAME = 3632;
  public static final int INVALID_APP_ID = 3633;
  public static final int INVALID_PROCESSOR_PIN = 3637;
  public static final int INVALID_FEATURE_ID = 3640;
  public static final int INVALID_TAG_NAME = 3644;
  public static final int INVALID_PAYEE_ID = 3646;
  public static final int NO_PAYEES_TO_ADD = 3653;
  public static final int INVALID_FILTER_TEXT = 3656;
  public static final int INVALID_MODIFIER_ID = 3666;
  public static final int INVALID_MODIFIER_TYPE = 3667;
  public static final int NO_HISTORY_GROUP_TO_ADD = 3668;
  public static final int REQUEST_NOT_IN_SERVICE = 3669;
  public static final int INVALID_XML = 3670;
  public static final int SERVICE_NOT_REGISTERED = 3671;
  public static final int INVALID_ACCOUNT_ID = 3680;
  public static final int NO_ACCOUNT_TO_DEACTIVATE = 3684;
  public static final int INVALID_BILLER_ID = 3685;
  public static final int INVALID_CONSUMER_ID = 3686;
  public static final int NO_ACCOUNT_TO_ACTIVATE = 3687;
  public static final int NO_ACCOUNT_TO_MODIFY = 3688;
  public static final int INVALID_CONTACT_ID = 3691;
  public static final int INVALID_CONTACT_INFO = 3692;
  public static final int INVALID_PUBLISHER_ID = 3693;
  public static final int USER_LOCKED_OUT = 3704;
  public static final int PASSWORD_NOT_CHANGED = 3707;
  public static final int PASSWORD_CHANGE_REQUIRED = 3709;
  public static final int INVALID_USER_NAME = 3720;
  public static final int PASSWORD_AUTHENTICATE_REQUIRED = 3721;
  public static final int INVALID_TAX_ID = 3727;
  public static final int INVALID_TC_ID = 3731;
  public static final int UNABLE_TO_AUTHENTICATE_USER = 3735;
  public static final int INVALID_BUSINESS_ID = 3740;
  public static final int INVALID_USER_TYPE = 3741;
  public static final int NO_ENTITLEMENTS_TO_ADD = 3748;
  public static final int INVALID_ID = 3749;
  public static final int INVALID_NAMESPACE = 3750;
  public static final int INVALID_PROPERTY_OBJECT = 3750;
  public static final int EXCESSIVE_DATA = 3751;
  public static final int OBJECT_PROPERTY_NOT_EXIST = 3752;
  public static final int OBJECT_PROPERTY_ALREADY_EXIST = 3753;
  public static final int NOT_SUPPORTED = 3800;
  public static final int ERROR_INVALID_SIGNON = 3810;
  public static final int ERROR_INVALID_REQUEST = 3811;
  public static final int ERROR_ALERTS_FAILURE = 3850;
  public static final int ERROR_CLASSCAST_SETCUTOFF = 3851;
  public static final int SUCCESS = 0;
  public static final int ERROR_MUST_BE_SUPERUSER = 4000;
  public static final int ERROR_CANT_DELETE_SUPERUSER = 4001;
  public static final int ERROR_EMPLOYEE_ID_ALREADY_EXISTS = 4002;
  public static final int ERROR_EMPLOYEE_NOT_FOUND = 4003;
  public static final int ERROR_NO_DATABASE_CONNECTION = 4005;
  public static final int ERROR_INVALID_BUSINESS_ID = 4011;
  public static final int ERROR_INVALID_BANK_ID = 4022;
  public static final int ERROR_DUPLICATE_CUSTOMER_ID = 4023;
  public static final int ERROR_TRANSACTION_GROUP_ALREADY_EXISTS = 4024;
  public static final int ERROR_DUPLICATE_BUSINESS_CIF = 4025;
  public static final int INVAILD_CONTACT_ID = 4100;
  public static final int ERROR_MODIFYING_APPLICATION = 7004;
  public static final int ERROR_DELETING_APPLICATION = 7005;
  public static final int ERROR_GETTING_APPLICATIONS = 7006;
  public static final int ERROR_GETTING_APPLICATION = 7007;
  public static final int ERROR_ADDING_APPLICATION = 7008;
  public static final int ERROR_NO_APPLICATIONS = 7009;
  public static final int ERROR_GETTING_APPLICATION_COUNT = 7010;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7011;
  public static final int ERROR_INVALID_INIT_FILE = 7012;
  public static final int ERROR_MODIFYING_APPLICATIONS = 7014;
  public static final int ERROR_GETTING_CATEGORY = 7020;
  public static final int ERROR_NO_CATEGORIES = 7021;
  public static final int ERROR_GETTING_CATEGORIES = 7022;
  public static final int ERROR_GETTING_PRODUCTS = 7030;
  public static final int ERROR_GETTING_PRODUCT = 7031;
  public static final int ERROR_MODIFYING_PRODUCT = 7032;
  public static final int ERROR_DELETING_PRODUCT = 7033;
  public static final int ERROR_NO_PRODUCTS = 7034;
  public static final int ERROR_GETTING_STATUS = 7050;
  public static final int ERROR_DELETING_STATUS = 7051;
  public static final int ERROR_CREATING_STATUS = 7052;
  public static final int ERROR_NO_STATUSES = 7053;
  public static final int ERROR_MODIFYING_STATUS = 7054;
  public static final int ERROR_GETTING_FORM = 7060;
  public static final int ERROR_NO_FORM = 7061;
  public static final int ERROR_GETTING_FORM_FIELDS = 7062;
  public static final int ERROR_ADDING_PRODUCT_ACCESS = 7071;
  public static final int ERROR_DELETING_PRODUCT_ACCESS = 7072;
  public static final int ERROR_GETTING_PRODUCT_ACCESS = 7073;
  public static final int ERROR_NO_PRODUCT_ACCESSES = 7074;
  public static final int ERROR_GETTING_BANKS = 7080;
  public static final int ERROR_NO_BANKS = 7081;
  public static final int ERROR_NO_HISTORY = 7090;
  public static final int ERROR_NOTIFYING_EMPLOYEES = 7091;
  public static final int NO_BANKEMPLOYEES_ASSIGNED_TO_APPLICATION = 7092;
  public static final int INVALID_APPLICATION_OWNER = 7093;
  public static final int INVALID_APPLICATION_ID = 7094;
  public static final int INVALID_APPLICATION_STATUSID = 7095;
  public static final int UNABLE_TO_SEND_MESSAGE = 3812;
  public static final int ERROR_TEMPLATE_NAME_ALREADY_EXISTS = 3813;
  public static final int ERROR_INVALID_SENDER_ID = 3814;
  public static final int ERROR_INVALID_FROM_TYPE = 3815;
  public static final int ERROR_INVALID_TO_TYPE = 3816;
  public static final int ERROR_INVALID_MESSAGE_TYPE = 3817;
  public static final int ERROR_INVALID_RESPONSE_TEXT = 3818;
  public static final int ERROR_NO_GLOBAL_MSG_I18N_FROM_NAME = 3819;
  public static final int ERROR_NO_GLOBAL_MSG_I18N_TO_GROUP_NAME = 3820;
  public static final int ERROR_NO_GLOBAL_MSG_I18N_SUBJECT = 3821;
  public static final int ERROR_NO_GLOBAL_MSG_I18N_MSG_TEXT = 3822;
  public static final int ERROR_NO_GLOBAL_MSG_FOUND = 3823;
  public static final int ERROR_RETRIEVING_LAST_EXPORTED_DATETIME = 5000;
  public static final int ERROR_STORING_CURRENT_EXPORTED_DATETIME = 5001;
  public static final int ERROR_CONTACT_POINT_DNE = 5050;
  public static final int ERROR_INVALID_START_TIME = 6000;
  public static final int ERROR_INVALID_END_TIME = 6001;
  public static final int ERROR_QUEUE_NAME_IS_NOT_UNIQUE = 8619;
  public static final int ERROR_INVALID_QUEUE_NAME = 8620;
  public static final int ERROR_INVALID_QUEUE_TYPE = 8621;
  public static final int ERROR_INVALID_PRODUCT_ID = 8622;
  public static final int ERROR_INVALID_STATUS_ID = 8623;
  public static final int ERROR_INVALID_EMPLOYEE_ID = 8624;
  public static final int ERROR_INVALID_RESPONSE_NAME = 8625;
  public static final int ERROR_INVALID_QUEUE_ID = 8626;
  
  public ProfileException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public ProfileException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    super(paramException);
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public ProfileException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public ProfileException(int paramInt, Exception paramException)
  {
    super(paramException);
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
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
 * Qualified Name:     com.ffusion.efs.adapters.profile.ProfileException
 * JD-Core Version:    0.7.0.1
 */