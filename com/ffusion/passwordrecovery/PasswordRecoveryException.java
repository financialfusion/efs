package com.ffusion.passwordrecovery;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PasswordRecoveryException
  extends Exception
{
  public Exception cause;
  public int code = -1;
  public static final int ERROR_PASSWORDRECOVERY_INITIALIZE = 33000;
  public static final int ERROR_PASSWORDRECOVERY_LOOKUP = 33001;
  public static final int ERROR_PASSWORDRECOVERY_VALIDATE = 33002;
  public static final int ERROR_PASSWORDRECOVERY_RESET = 33003;
  public static final int ERROR_PASSWORDRECOVERY_USER_LOCKED_OUT = 33004;
  public static final int ERROR_PASSWORDRECOVERY_PASSWORD_HISTORY_CHECK_FAILED = 33005;
  public static final int ERROR_PASSWORDRECOVERY_PASSWORD_CHANGE_TOO_SOON = 33006;
  public static final int ERROR_PASSWORDRECOVERY_PASSWORD_IN_HISTORY = 33007;
  public static final int ERROR_PASSWORDRECOVERY_USER_EXPIRED = 33008;
  
  public PasswordRecoveryException(int paramInt)
  {
    this.code = paramInt;
  }
  
  public PasswordRecoveryException(int paramInt, Exception paramException)
  {
    this.code = paramInt;
    this.cause = paramException;
  }
  
  public PasswordRecoveryException(int paramInt, String paramString)
  {
    super(paramString);
    this.code = paramInt;
  }
  
  public PasswordRecoveryException(int paramInt, String paramString, Exception paramException)
  {
    super(paramString);
    this.code = paramInt;
    this.cause = paramException;
  }
  
  public PasswordRecoveryException(String paramString, Exception paramException)
  {
    super(paramString);
    this.code = 0;
    this.cause = paramException;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = super.getMessage();
    if (str != null) {
      localStringBuffer.append(str);
    }
    if (this.code != 0) {
      localStringBuffer.append(": error code:").append(this.code);
    }
    if (this.cause != null) {
      localStringBuffer.append(" - ").append(this.cause.toString());
    }
    return localStringBuffer.toString();
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.cause != null) {
      this.cause.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.cause != null) {
      this.cause.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.cause != null) {
      this.cause.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.passwordrecovery.PasswordRecoveryException
 * JD-Core Version:    0.7.0.1
 */