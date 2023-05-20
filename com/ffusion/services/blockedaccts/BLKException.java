package com.ffusion.services.blockedaccts;

public class BLKException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public static final int CONNECTION_POOL_INITIALIZATION_FAILED = 1;
  public static final int UNABLE_TO_COMPLETE_REQUEST = 2;
  public static final int MISSING_DATABASE_CONFIG = 3;
  public static final int BLKACCT_ADAPTER_NOT_INITIALIZED = 4;
  public static final int ILLEGAL_ARGUMENT = 5;
  public static final int INVALID_ROUTING_NUMBER = 6;
  public static final int INVALID_ACCOUNT_NUMBER = 7;
  public static final int INVALID_BANK_NAME = 8;
  public static final int BLOCKED_ACCOUNT_NOT_FOUND = 9;
  public static final int INVALID_USER_DIRECTORY_ID = 10;
  public static final int BLOCKED_ACCOUNT_ALREADY_EXISTS = 11;
  public static final int INVALID_STRIPPED_ACCOUNT_NUMBER = 12;
  
  public BLKException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public BLKException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    super(paramException);
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public int getCode()
  {
    return this.code;
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
 * Qualified Name:     com.ffusion.services.blockedaccts.BLKException
 * JD-Core Version:    0.7.0.1
 */