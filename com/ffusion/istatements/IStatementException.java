package com.ffusion.istatements;

public class IStatementException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  
  public IStatementException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public IStatementException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public IStatementException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public IStatementException(int paramInt, Exception paramException)
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
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.istatements.IStatementException
 * JD-Core Version:    0.7.0.1
 */