package com.ffusion.beans.fileimporter;

public abstract class ErrorMessage
{
  protected String title;
  protected String message;
  
  public ErrorMessage(String paramString1, String paramString2)
  {
    this.title = paramString1;
    this.message = paramString2;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = encode(paramString);
  }
  
  public String getMessage()
  {
    return encode(this.message);
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public String encode(String paramString)
  {
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(i + i / 5);
    int j = 1;
    for (int k = 0; k < i; k++)
    {
      char c = paramString.charAt(k);
      switch (c)
      {
      case '&': 
        localStringBuffer.append("&amp;");
        j = 1;
        break;
      case '<': 
        localStringBuffer.append("&lt;");
        j = 1;
        break;
      case '>': 
        localStringBuffer.append("&gt;");
        j = 1;
        break;
      default: 
        localStringBuffer.append(c);
      }
    }
    return j != 0 ? localStringBuffer.toString() : paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.ErrorMessage
 * JD-Core Version:    0.7.0.1
 */