package com.ffusion.util.beans.accountgroups;

public class BusinessAccountGroupException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public static final int ERROR_NOT_INITIALIZED = 1;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 2;
  public static final int ERROR_ACCOUNT_GROUP_NAME_EXISTS = 3;
  public static final int ERROR_ACCOUNTS_LIST_EXISTS = 4;
  public static final int ERROR_UNABLE_TO_ADD_ACCOUNT_GROUP = 5;
  public static final int ERROR_UNABLE_TO_GET_ACCOUNT_GROUP_INFO = 6;
  public static final int ERROR_UNABLE_TO_MODIFY_ACCOUNT_GROUP = 7;
  public static final int ERROR_UNABLE_TO_DELETE_ACCOUNT_GROUP = 8;
  public static final int ERROR_MULTI_ENT_OBJECT_MISMATCH = 9;
  
  public BusinessAccountGroupException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public BusinessAccountGroupException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public BusinessAccountGroupException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public BusinessAccountGroupException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public int getErrorCode()
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


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.accountgroups.BusinessAccountGroupException
 * JD-Core Version:    0.7.0.1
 */