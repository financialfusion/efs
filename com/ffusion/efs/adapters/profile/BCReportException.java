package com.ffusion.efs.adapters.profile;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BCReportException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public static final int INVALID_SEARCH_CRITERIA = 10;
  public static final int INVALID_REPORT_TYPE = 11;
  public static final int INVALID_REPORT_OPTION = 12;
  public static final int INVALID_DATE_FORMAT = 13;
  public static final int INVALID_SORT_CRITERIA = 14;
  public static final int NO_USER_SPECIFIED = 15;
  public static final int ERROR_ENT_GROUP = 16;
  public static final int ERROR_BUSINESS_EMPLOYEES = 17;
  public static final int ERROR_BUSINESSES = 18;
  public static final int ERROR_AUDIT_RPT = 19;
  public static final int ERROR_BANK_EMPLOYEES = 20;
  public static final int ERROR_PERFORMANCE_RPT = 21;
  public static final int NO_DATABASE_CONNECTION = 22;
  public static final int ERROR_HISTORY_RPT = 23;
  public static final int ERROR_TRANSACTION_RPT = 24;
  public static final int ERROR_POSITIVE_PAY_RPT = 25;
  public static final int UNABLE_TO_GENERATE_REPORT = 26;
  public static final int NOT_ENTITLED = 27;
  public static final int ERROR_INACTIVE_BUSINESS_MODULE_RPT = 28;
  public static final int ERROR_INACTIVE_BUSINESS_RPT = 29;
  public static final int ERROR_INVALID_AFFILIATE_BANK = 30;
  public static final int INVALID_PAYEE_RPT_SEARCH_CRITERIA = 31;
  public static final int ERROR_TRACKING_ID_TOO_LONG = 32;
  public static final int INVALID_TRANSACTION_TYPE_FOR_MODULE = 33;
  
  public BCReportException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public BCReportException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public BCReportException(Exception paramException, String paramString, int paramInt)
  {
    this.childException = paramException;
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public BCReportException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public BCReportException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
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
 * Qualified Name:     com.ffusion.efs.adapters.profile.BCReportException
 * JD-Core Version:    0.7.0.1
 */