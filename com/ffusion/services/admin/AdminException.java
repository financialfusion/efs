package com.ffusion.services.admin;

public class AdminException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public int serviceError = 0;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_NOT_SUPPORTED = 1;
  public static final int INVALID_SEARCH_CRITERIA = 10;
  public static final int INVALID_REPORT_TYPE = 11;
  public static final int INVALID_REPORT_OPTION = 12;
  public static final int INVALID_DATE_FORMAT = 13;
  public static final int INVALID_SORT_CRITERIA = 14;
  public static final int NO_USER_SPECIFIED = 15;
  public static final int ERROR_ENT_GROUP = 16;
  public static final int ERROR_ENT_RPT = 17;
  public static final int ERROR_ACCT_BE = 18;
  public static final int ERROR_BUSINESS_EMPLOYEES = 19;
  public static final int ERROR_BUSINESS = 20;
  public static final int ERROR_SUPERVISOR = 21;
  public static final int ERROR_REPORT_GENERATION_EXCEPTION = 22;
  public static final int ERROR_BANK_EMPLOYEE = 23;
  public static final int UNABLE_TO_GENERATE_REPORT = 24;
  public static final int INVALID_AFFILIATE_BANK = 25;
  
  public AdminException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public AdminException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public AdminException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public AdminException(int paramInt1, int paramInt2)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt1;
    this.serviceError = paramInt2;
  }
  
  public AdminException(String paramString, int paramInt, Exception paramException)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public AdminException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
  
  public int getServiceError()
  {
    return this.serviceError;
  }
  
  public void setServiceError(int paramInt)
  {
    this.serviceError = paramInt;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.admin.AdminException
 * JD-Core Version:    0.7.0.1
 */