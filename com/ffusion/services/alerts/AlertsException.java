package com.ffusion.services.alerts;

public class AlertsException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_UNKNOWN_EXCEPTION = 1;
  public static final int ERROR_MISSING_CONTACT_POINTS = 2;
  public static final int ERROR_MISSING_CONTACT_TYPE = 3;
  public static final int ERROR_INVALID_CONTACT_TYPE = 4;
  public static final int ERROR_MISSING_CONTACT_CHANNEL = 5;
  public static final int ERROR_UNKNOWN_CONTACT_TYPE = 6;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 7;
  public static final int ERROR_UNABLE_TO_GET_USER_FOR_ALERT = 8;
  
  public AlertsException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public AlertsException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public AlertsException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public AlertsException(String paramString, int paramInt, Exception paramException)
  {
    super(paramException);
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public AlertsException(int paramInt, Exception paramException)
  {
    super(paramException);
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
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
    if (getCause() != null) {
      localStringBuffer.append(getCause().getMessage());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.alerts.AlertsException
 * JD-Core Version:    0.7.0.1
 */