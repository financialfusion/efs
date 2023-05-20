package com.ffusion.webservices.api;

public class WSException
  extends Exception
{
  public static final int UNKNOWN_ERROR = 1;
  public static final int INIT_ERROR = 2;
  public static final int PROCESS_ERROR = 3;
  public static final int TASK_ERROR = 4;
  public static final int SERVICE_ERROR = 5;
  public static final int INVALID_SESSION_ERROR = 6;
  public static final int INVALID_TRANSFER_ERROR = 7;
  public static final int SESSION_NOT_FOUND_ERROR = 8;
  public static final int ACCOUNTS_NOT_FOUND_ERROR = 9;
  public static final int ACCOUNT_NOT_FOUND_ERROR = 10;
  public static final int INVALID_ACCOUNT_ERROR = 11;
  public static final int INVALID_BANK_ERROR = 12;
  public static final int INVALID_PAYMENT_ERROR = 13;
  public static final int INVALID_PAYEE_ERROR = 14;
  public static final int PAYEE_NOT_FOUND_ERROR = 15;
  public static final int PAYMENT_NOT_FOUND_ERROR = 16;
  public static final int TRANSFER_NOT_FOUND_ERROR = 17;
  public static final String INIT_TXT = "Initialization Error";
  public static final String IO_TXT = "IO error occured, unable to process request";
  public static final String SERVICE_INIT_TXT = "Service Initialization Error";
  public static final String AUTHENTICATE_TXT = "Unable to process authentication, authentication failed";
  public static final String PROCESS_TXT = "Unable to process request";
  public static final String INVALID_SESSION_TXT = "Invalid session or session not provided";
  public static final String INVALID_TRANSFER_TXT = "Invalid transfer or transfer not provided";
  public static final String INVALID_ACCOUNT_TXT = "Invalid account id or account id not provided";
  public static final String INVALID_BANK_TXT = "Invalid bank id or bank id not provided";
  public static final String SESSION_NOT_FOUND_TXT = "Requested session is not found or has timed out";
  public static final String ACCOUNTS_NOT_FOUND_TXT = "User has not accounts or get accounts failed, no accounts found";
  public static final String ACCOUNT_NOT_FOUND_TXT = "Account not found or id : ";
  public static final String INVALID_PAYMENT_TXT = "Invalid payment or payment not provided";
  public static final String INVALID_PAYEE_TXT = "Invalid payee or payee not provided";
  public static final String PAYEE_NOT_FOUND_TXT = "Payee not found : ";
  public static final String PAYMENT_NOT_FOUND_TXT = "Payment not found : ";
  public static final String TRANSFER_NOT_FOUND_TXT = "Transfer not found : ";
  private int a;
  private int jdField_if;
  
  public WSException(String paramString, int paramInt)
  {
    super(paramString);
    this.a = paramInt;
    this.jdField_if = 0;
  }
  
  public WSException(String paramString, int paramInt1, int paramInt2)
  {
    super(paramString);
    this.a = paramInt1;
    this.jdField_if = paramInt2;
  }
  
  public int getCode()
  {
    return this.a;
  }
  
  public int getTaskCode()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.api.WSException
 * JD-Core Version:    0.7.0.1
 */