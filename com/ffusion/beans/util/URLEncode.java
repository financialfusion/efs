package com.ffusion.beans.util;

import java.io.Serializable;
import java.net.URLEncoder;

public class URLEncode
  implements Serializable
{
  private String a;
  
  public void setStringToEncode(String paramString)
  {
    this.a = paramString;
  }
  
  public String getEncodedValue()
  {
    if (this.a == null) {
      return null;
    }
    return URLEncoder.encode(this.a);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.URLEncode
 * JD-Core Version:    0.7.0.1
 */