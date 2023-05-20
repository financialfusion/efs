package com.ffusion.csil;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FIException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public int serviceError = 0;
  public static final int ERROR_INVALID_TYPE = 0;
  public static final int ERROR_NULL_ADAPTER = 1;
  public static final int ERROR_FILE_UPLOAD = 2;
  public static final int ERROR_CLASS_INIT = 3;
  public static final int ERROR_INVALID_DIR = 4;
  public static final int ERROR_INVALID_STATUS = 5;
  public static final int ERROR_CLEANUP_DELETE = 6;
  public static final int ERROR_PROCESS_PATH = 7;
  public static final int ERROR_NOT_INITIALIZED = 8;
  public static final int ERROR_CANT_PURGE_DATA = 9500;
  public static final int ERROR_TRUNCATED_DATA = 9501;
  public static final int ERROR_LOADING_DATA = 9502;
  public static final int ERROR_CANT_CONNECT_TO_DB = 9600;
  public static final int ERROR_CANT_CREATE_MB_INSTANCE = 9601;
  public static final int ERROR_INVALID_CLASS = 9602;
  public static final int ERROR_UNABLE_TO_CREATE_PARSER = 9603;
  public static final int ERROR_UNABLE_TO_PARSE = 9604;
  public static final int ERROR_UNABLE_TO_LOAD_MSGSET = 9605;
  public static final int ERROR_CLASS_NOT_FOUND = 9606;
  public static final int ERROR_COULD_NOT_INSTANTIATE = 9607;
  public static final int ERROR_COULD_NOT_READ = 9608;
  public static final int ERROR_COULD_NOT_INSERT = 9609;
  public static final int ERROR_NULL_ADAPTER_INFO = 9610;
  public static final int ERROR_UNABLE_TO_CREATE_ADAPTER = 9611;
  public static final int ERROR_BAI_FILE_OPEN_ERROR = 9700;
  public static final int ERROR_BAI_FIELD_READ_ERROR = 9701;
  public static final int ERROR_BAI_FILE_PARSE_ERROR = 9702;
  public static final int ERROR_MISSING_BAI_FILE_HEADER = 9703;
  public static final int ERROR_INVALID_BAI_FILE_HEADER = 9704;
  public static final int ERROR_MISSING_BAI_GROUP_HEADER = 9705;
  public static final int ERROR_INVALID_BAI_GROUP_HEADER = 9706;
  public static final int ERROR_INVALID_BAI_AVAILABILITY_DISTRIBUTION = 9708;
  public static final int ERROR_INVALID_BAI_SUMMARY_STATUS = 9709;
  public static final int ERROR_INVALID_BAI_ACCOUNT_IDENTIFIER = 9710;
  public static final int ERROR_INVALID_BAI_ACCOUNT_TRAILER = 9711;
  public static final int ERROR_INVALID_BAI_GROUP_TRAILER = 9712;
  public static final int ERROR_INVALID_BAI_FILE_TRAILER = 9713;
  public static final int ERROR_INVALID_BAI_TRANSACTION_DETAIL = 9714;
  public static final int ERROR_PROCESS_BAI_FILE_FAILED = 9715;
  public static final int ERROR_INVALID_GROUP_STATUS = 9716;
  public static final int ERROR_UNKNOWN_BAI_RECORD_TYPE = 9717;
  public static final int ERROR_INVALID_BAI_FUNDS_TYPE = 9718;
  public static final int ERROR_AMOUNT_CONTROL_TOTAL = 9719;
  public static final int ERROR_ACCOUNT_CONTROL_TOTAL = 9719;
  public static final int ERROR_GROUP_CONTROL_TOTAL = 9719;
  public static final int ERROR_FILE_CONTROL_TOTAL = 9721;
  public static final int ERROR_MISSING_BAI_FILE_IDENTIFIER = 9722;
  public static final int ERROR_BAI_MISSING_ACCOUNT_TRAILER = 9723;
  public static final int ERROR_BAI_MISSING_GROUP_TRAILER = 9724;
  public static final int ERROR_BAI_MISSING_FILE_TRAILER = 9725;
  public static final int ERROR_MISSING_BAI_TYPE_CODE = 9726;
  public static final int ERROR_NEGATIVE_AMOUNT_IN_TRANSACTION_DETAIL = 9727;
  public static final int ERROR_FUNDS_TYPE_D_NEXT_FIELD_NAN = 9728;
  public static final int ERROR_NO_MAPPING_ADAPTER = 9800;
  public static final int ERROR_NO_MAPPING_DEFINITION = 9801;
  public static final int ERROR_INVALID_FORMAT_TYPE = 9802;
  public static final int ERROR_UNABLE_TO_PARSE_FILE = 9803;
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
  
  public FIException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public FIException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public FIException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public FIException(int paramInt1, int paramInt2)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt1;
    this.serviceError = paramInt2;
  }
  
  public FIException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public FIException(String paramString, int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.why = paramString;
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public FIException(String paramString1, int paramInt, String paramString2, Exception paramException)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.why = paramString2;
    this.childException = paramException;
  }
  
  public void setServiceError(int paramInt)
  {
    this.serviceError = paramInt;
  }
  
  public int getServiceError()
  {
    return this.serviceError;
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
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.childException != null) {
      this.childException.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.childException != null) {
      this.childException.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.childException != null) {
      this.childException.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.FIException
 * JD-Core Version:    0.7.0.1
 */