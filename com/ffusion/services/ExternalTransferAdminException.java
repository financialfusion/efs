package com.ffusion.services;

public class ExternalTransferAdminException
  extends Exception
{
  public static final int ERROR_NONE = 0;
  public static final int DC_EXCEPTION = 1;
  public static final int ACCOUNT_DNE = 2;
  public static final int PAGESIZE_FORMAT = 3;
  public static final int FDINSTRUMENT_DNE = 4;
  public static final int SERVICE_NOT_INIT = 5;
  public static final int REPORT_CRITERIA_DNE = 401;
  public static final int REPORT_OPTIONS_DNE = 402;
  public static final int SEARCH_CRITERIA_DNE = 403;
  public static final int SEARCH_CRITERIA_ACCOUNTS_DNE = 404;
  public static final int SEARCH_CRITERIA_ACCOUNTS_FORMAT = 405;
  public static final int SEARCH_CRITERIA_DATE_FORMAT = 406;
  public static final int REPORT_OPTIONS_REPORT_TYPE_DNE = 407;
  public static final int REPORT_OPTIONS_REPORT_TYPE_FORMAT = 408;
  public static final int ACCOUNT_SERVICE_DNE = 409;
  public static final int ACCOUNT_SERVICE_EXCEPTION = 410;
  public static final int REPORT_TYPE_NOT_SUPPORTED = 411;
  public static final int BPW_SERVICE_DOWN = 412;
  public static final int UNABLE_TO_GENERATE_REPORT = 413;
  public static final int ERROR_UNKNOWN = 1;
  public static final int ERROR_INIT_FILE_NOT_FOUND = 7;
  public static final int ERROR_INVALID_INIT_FILE = 8;
  public static final int ERROR_BPTW_NOT_AVAILABLE = 30000;
  public static final int ERROR_BPTW_INIT_FILE_NOT_VALID = 30001;
  int a;
  
  public ExternalTransferAdminException(int paramInt)
  {
    this(paramInt, null);
  }
  
  public ExternalTransferAdminException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.a != 0) {
      localStringBuffer.append("Error Code: ").append(this.a);
    }
    String str = super.getMessage();
    if (str != null) {
      localStringBuffer.append(' ').append(str);
    }
    return localStringBuffer.toString();
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ExternalTransferAdminException
 * JD-Core Version:    0.7.0.1
 */