package com.ffusion.beans.fileimporter;

public class ValidationError
  extends ErrorMessage
{
  protected String field = null;
  
  public ValidationError(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public ValidationError(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, paramString2);
    this.field = paramString3;
  }
  
  public String getField()
  {
    return this.field;
  }
  
  public String toString()
  {
    return this.title + ": " + this.message;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.ValidationError
 * JD-Core Version:    0.7.0.1
 */