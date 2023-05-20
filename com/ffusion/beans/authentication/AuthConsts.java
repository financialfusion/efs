package com.ffusion.beans.authentication;

public abstract interface AuthConsts
{
  public static final int AUTH_TYPE_TOKEN = 1;
  public static final int AUTH_TYPE_SCRATCH = 2;
  public static final int AUTH_TYPE_CHALLENGE = 3;
  public static final int OPERATION_TYPE_LOGIN = 1;
  public static final int OPERATION_TYPE_TRANSACTION = 2;
  public static final int OPERATION_TYPE_PRIVACY = 3;
  public static final String USER_TYPE = "UserType";
  public static final String CONSUMER_USER_TYPE = "Consumer";
  public static final String BUSINESS_USER_TYPE = "Business";
  public static final String BANK_USER_TYPE = "BankEmployee";
  public static final String AUTH_SETTING_SCRATCH_CARD_ROWS = "Scratch Card Rows";
  public static final String AUTH_SETTING_SCRATCH_CARD_COLUMNS = "Scratch Card Columns";
  public static final String SCRATCH_CARD_QUESTION_RESOURCE_KEY = "SCRATCH_QUESTION";
  public static final int NUM_CHALLENGE_QUESTIONS = 2;
  public static final String OPERATION_TYPE_KEY = "Operation";
  public static final String ENT_CACHED_ADAPTER = "EntCachedAdapter";
  public static final String CREDENTIALS = "Credentials";
  public static final String EFS_SECRET_PICTURE_URL = "efs/multilang/jsp/secretgrafx/sample1.jpg";
  public static final String CB_SECRET_PICTURE_URL = "cb/multilang/jsp/secretgrafx/sample1.jpg";
  public static final String BC_SECRET_PICTURE_URL = "bc/multilang/jsp/secretgrafx/sample1.jpg";
  public static final String CREDENTIAL_FIELD_NAME_PREFIX = "CREDENTIAL_FIELD_NAME_";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.authentication.AuthConsts
 * JD-Core Version:    0.7.0.1
 */