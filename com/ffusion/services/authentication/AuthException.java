package com.ffusion.services.authentication;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AuthException
  extends Exception
{
  private int jdField_if = 0;
  private String jdField_do = null;
  private Throwable a = null;
  public static final int SERVICE_INIT_FAILED = 1;
  public static final int SERVICE_NOT_INITIALIZED = 2;
  public static final int MISSING_AUTHENTICATION_SETTING = 3;
  public static final int INVALID_AUTHENTICATION_SETTING = 4;
  public static final int MISSING_EXTRA_PARAMETER = 5;
  public static final int INVALID_CREDENTIAL_TYPE = 6;
  public static final int INVALID_CREDENTIAL_RESPONSE = 7;
  public static final int INVALID_USER = 8;
  public static final int RESOURCE_ERROR = 9;
  public static final int INVALID_SERVICE_PACKAGE = 10;
  
  public AuthException(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public AuthException(String paramString, int paramInt)
  {
    this.jdField_if = paramInt;
    this.jdField_do = paramString;
  }
  
  public AuthException(String paramString, int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.jdField_do = paramString;
    this.a = paramThrowable;
  }
  
  public AuthException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.a = paramThrowable;
  }
  
  public int getErrorCode()
  {
    return this.jdField_if;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.jdField_do != null) && (this.jdField_do.length() != 0)) {
      localStringBuffer.append(this.jdField_do);
    }
    localStringBuffer.append(": error code: ");
    localStringBuffer.append(this.jdField_if);
    if (this.a != null)
    {
      localStringBuffer.append(" ");
      localStringBuffer.append(this.a.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.a != null) {
      this.a.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.a != null) {
      this.a.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.a != null) {
      this.a.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.authentication.AuthException
 * JD-Core Version:    0.7.0.1
 */