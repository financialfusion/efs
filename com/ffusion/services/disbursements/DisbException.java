package com.ffusion.services.disbursements;

public class DisbException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public int serviceError = 0;
  public static final int UNABLE_TO_GET_DISBURSEMENT_SUMMARY = 600;
  public static final int UNABLE_TO_ADD_DISBURSEMENT_SUMMARY = 601;
  public static final int UNABLE_TO_GET_DISBURSEMENT_SUMMARY_FOR_PRESENTMENT = 602;
  public static final int UNABLE_TO_CREATE_DISBURSEMENT_SUMMARY_WITH_DATA = 603;
  public static final int UNABLE_TO_GET_DISBURSEMENT_SUMMARY_DATA_FOR_DAYS = 604;
  public static final int INVALID_DISBURSEMENT_TRANSACTION_INDEX = 605;
  public static final int UNABLE_TO_ADD_DISBURSEMENT_TRANSACTIONS = 606;
  public static final int UNABLE_TO_GET_DISBURSEMENT_TRANSACTIONS = 607;
  public static final int UNABLE_TO_GET_DISBURSEMENT_TRANSACTION_ID = 608;
  public static final int ERROR_ACCOUNT_SERVICE_NOT_FOUND = 609;
  public static final int ERROR_ACCOUNT_RETRIEVAL_FAILED = 610;
  public static final int ERROR_BANKING_SERVICE_NOT_FOUND = 611;
  public static final int ERROR_NOT_SUPPORTED = 612;
  public static final int ERROR_REPORT_DATA = 613;
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
  
  public DisbException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public DisbException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public DisbException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public DisbException(int paramInt1, int paramInt2)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt1;
    this.serviceError = paramInt2;
  }
  
  public DisbException(String paramString, int paramInt, Exception paramException)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public DisbException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.disbursements.DisbException
 * JD-Core Version:    0.7.0.1
 */