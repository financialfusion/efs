package com.ffusion.tasks.authentication;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String FAILED_CREDENTIAL_REQUESTS = "FAILED_CREDENTIAL_REQUESTS";
  public static final String SECRETS = "Secrets";
  public static final String CREDENTIALS = "Credentials";
  public static final String CREDENTIAL_RESPONSE_PREFIX = "CredentialResponse_";
  public static final int ERROR_NO_CREDENTIALS = 90000;
  public static final int ERROR_NO_FAILURE_URL = 90001;
  public static final int ERROR_NO_LOGOUT_URL = 90002;
  public static final int ERROR_NO_OPERATION_CODE = 90003;
  public static final int ERROR_INVALID_CREDENTIAL_RESPONSE = 90004;
  public static final int ERROR_NO_USER_TYPE = 90005;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.authentication.Task
 * JD-Core Version:    0.7.0.1
 */