package com.ffusion.positivepay;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PPayException
  extends Exception
{
  private long jdField_if = 0L;
  private String jdField_for = null;
  private String jdField_int = null;
  private int a = -1;
  private Exception jdField_do = null;
  public static final int ERROR_NOT_INITIALIZED = 1;
  public static final int ISSUE_ALREADY_EXISTS = 2;
  public static final int DECISION_TYPE_NOT_SUPPORTED = 3;
  public static final int ISSUE_NOT_FOUND = 4;
  public static final int MISSING_PARAMETERS = 5;
  public static final int NO_DATABASE_CONNECTION = 6;
  public static final int DATABASE_ERR = 7;
  public static final int INVALID_AGE = 8;
  public static final int UNABLE_TO_READ_CONFIG_FILE = 9;
  public static final int INVALID_CONFIG_FILE = 10;
  public static final int INVALID_ADAPTER_CLASS = 11;
  public static final int CANT_WRITE_DECISION_FILE = 12;
  public static final int UNABLE_TO_CREATE_MB_INSTANCE = 13;
  public static final int UNABLE_TO_LOAD_MB_MESSAGE_SET = 14;
  public static final int MB_GENERATION_ERROR_DECISION = 15;
  public static final int UNABLE_TO_INIT_FILE_IMPORT_SERVICE = 16;
  public static final int FILE_IMPORTER_SERVICE_EXCEPTION = 17;
  public static final int SERVICE_NOT_INITIALIZED = 18;
  public static final int PPAY_RPT_TYPE_NOT_SUPPORTED = 19;
  public static final int ACCOUNT_SERVICE_NOT_FOUND = 20;
  public static final int BUSINESS_ACOUNT_RETRIEVAL_FAILED = 21;
  public static final int ERROR_DECISION_DIRECTORY = 22;
  public static final int MB_GENERATION_ERROR_CHECK_RECORD = 23;
  public static final int UNABLE_TO_GENERATE_REPORT = 24;
  public static final int INVALID_DECISION = 25;
  public static final int ERROR_RETRIEVING_USER = 26;
  public static final int ACCOUNTS_ENTITLEMENT_CHECK_FAILED = 27;
  public static final int PPAY_RPT_DATE_RANGE_TOO_WIDE = 28;
  public static final int PPAY_INVALID_ROUTING_NUMBER = 29;
  public static final int GENERAL_ERROR = 1000;
  
  public PPayException(String paramString1, int paramInt, String paramString2)
  {
    this.jdField_for = paramString1;
    this.jdField_if = System.currentTimeMillis();
    this.jdField_int = paramString2;
    this.a = paramInt;
  }
  
  public PPayException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    this.jdField_do = paramException;
    this.jdField_for = paramString1;
    this.jdField_if = System.currentTimeMillis();
    this.jdField_int = paramString2;
    this.a = paramInt;
  }
  
  public PPayException(int paramInt)
  {
    this.jdField_if = System.currentTimeMillis();
    this.a = paramInt;
  }
  
  public PPayException(int paramInt, Exception paramException)
  {
    this.jdField_if = System.currentTimeMillis();
    this.a = paramInt;
    this.jdField_do = paramException;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.jdField_for != null) && (this.jdField_for.length() > 0)) {
      localStringBuffer.append(this.jdField_for);
    }
    if ((this.jdField_int != null) && (this.jdField_int.length() > 0)) {
      localStringBuffer.append(": ").append(this.jdField_int);
    }
    if (this.a != 0) {
      localStringBuffer.append(": error code:" + String.valueOf(this.a) + " ");
    }
    if (this.jdField_do != null) {
      localStringBuffer.append(this.jdField_do.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_do != null) {
      this.jdField_do.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_do != null) {
      this.jdField_do.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_do != null) {
      this.jdField_do.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.positivepay.PPayException
 * JD-Core Version:    0.7.0.1
 */