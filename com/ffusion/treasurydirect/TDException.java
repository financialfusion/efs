package com.ffusion.treasurydirect;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TDException
  extends Exception
{
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 80000;
  public static final int ERROR_MISSING_DATABASE_CONFIG = 80001;
  public static final int ERROR_INVALID_DATABASE_CONFIG = 80002;
  public static final int ERROR_NOT_INITIALIZED = 80003;
  public static final int ERROR_UNABLE_TO_ADD_SUB_ACCOUNT = 80004;
  public static final int ERROR_INVALID_BANK_ID = 80005;
  public static final int ERROR_INVALID_ROUTING_NUM = 80006;
  public static final int ERROR_INVALID_ACCOUNT_ID = 80007;
  public static final int ERROR_ILLEGAL_ARGUMENT = 80008;
  public static final int ERROR_UNABLE_TO_DELETE_SUB_ACCOUNTS = 80009;
  public static final int ERROR_UNABLE_TO_DELETE_MASTER_ACCOUNT = 80010;
  public static final int ERROR_UNABLE_TO_MODIFY_SUB_ACCOUNT = 80011;
  public static final int ERROR_INVALID_BUSINESS_ID = 80012;
  public static final int ERROR_INVALID_BUSINESS = 80013;
  public static final int ERROR_INVALID_SUB_ACCOUNT = 80014;
  public static final int ERROR_INVALID_MASTER_ACCOUNT = 80015;
  public static final int ERROR_INVALID_SECURE_USER = 80016;
  private Throwable jdField_if;
  private int a;
  
  public TDException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public TDException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public TDException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public TDException(int paramInt, Throwable paramThrowable)
  {
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintStream);
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintWriter);
    }
  }
  
  public Throwable getChained()
  {
    return this.jdField_if;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.treasurydirect.TDException
 * JD-Core Version:    0.7.0.1
 */