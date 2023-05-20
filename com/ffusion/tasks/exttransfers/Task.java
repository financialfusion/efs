package com.ffusion.tasks.exttransfers;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final int ERROR_NO_EXTERNAL_TRANSFER_ACCOUNTS = 4201;
  public static final int ERROR_NO_EXTERNAL_TRANSFER_ACCOUNT = 4202;
  public static final int ERROR_NO_EXTERNAL_TRANSFER_COMPANIES = 4203;
  public static final int ERROR_NO_EXTERNAL_TRANSFER_COMPANY = 4204;
  public static final int ERROR_NO_SECURE_USER = 4205;
  public static final String ENTITLEMENT_ADD_EXTTRANSFERCOMPANY = "AddExtTransferCompany";
  public static final String ENTITLEMENT_GET_EXTTRANSFERCOMPANIES = "GetExtTransferCompanies";
  public static final String ENTITLEMENT_EDIT_EXTTRANSFERCOMPANY = "EditExtTransferCompany";
  public static final String ENTITLEMENT_DEL_EXTTRANSFERCOMPANY = "CancelExtTransferCompany";
  public static final int ERROR_EXTERNAL_TRANSFER_ACCOUNT_DOES_NOT_EXIST = 4206;
  public static final int ERROR_EXTERNAL_TRANSFER_COMPANY_DOES_NOT_EXIST = 4207;
  public static final int ERROR_FIID_NOT_SET = 4208;
  public static final int ERROR_COMPANY_NAME = 4209;
  public static final int ERROR_COMPANY_ID = 4210;
  public static final int ERROR_BATCH_DESCRIPTION = 4211;
  public static final int ERROR_RECIPIENT_NAME = 4212;
  public static final int ERROR_RECIPIENT_ID = 4213;
  public static final int ERROR_PROFILE_ACCOUNT_INFO = 4214;
  public static final int ERROR_BANK_NOT_SET_UP_FOR_DEPOSIT_VERIFY = 4215;
  public static final int ERROR_CANNOT_DEPOSIT_NOW = 4216;
  public static final int ERROR_VERIFY_AMOUNT_NOT_SET = 4217;
  public static final int ERROR_VERIFY_AMOUNT_IS_BAD = 4218;
  public static final int ERROR_YOU_MUST_AGREE = 4219;
  public static final int ERROR_CHECK_NUMBERS_DONT_MATCH = 4220;
  public static final int ERROR_ACCOUNT_NUMBER_DOES_NOT_MATCH = 4221;
  public static final int ERROR_ACCOUNT_ALREADY_EXISTS = 4222;
  public static final int ERROR_ACCOUNT_NOT_AUTHORIZED = 4223;
  public static final int ERROR_ACCOUNT_TYPE_NOT_SET = 4224;
  public static final int ERROR_ACCOUNT_ALREADY_DEPOSITED_TO = 4225;
  public static final int ERROR_ACCOUNT_IS_ON_BLOCKED_LIST = 4226;
  public static final int ERROR_ACCOUNT_NUMBER_EXISTS = 4227;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.Task
 * JD-Core Version:    0.7.0.1
 */