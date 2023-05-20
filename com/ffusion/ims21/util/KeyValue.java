package com.ffusion.ims21.util;

public class KeyValue
{
  String a = null;
  String jdField_if = null;
  
  public KeyValue(String paramString)
  {
    paramString.trim();
    int i = paramString.indexOf("<");
    int j = paramString.indexOf("=");
    int k = paramString.indexOf(">");
    if ((i == -1) || (k == -1)) {
      return;
    }
    if (j != -1)
    {
      this.a = paramString.substring(i + 1, j);
      this.jdField_if = paramString.substring(j + 1, k);
      this.jdField_if = a(this.jdField_if);
    }
    else
    {
      this.a = paramString.substring(i + 1, k);
    }
  }
  
  static String a(String paramString)
  {
    int i = paramString.indexOf("\"");
    int j = paramString.lastIndexOf("\"");
    if ((i == -1) || (j == -1)) {
      return paramString;
    }
    return paramString.substring(i + 1, j);
  }
  
  public boolean isValid()
  {
    return this.a != null;
  }
  
  public String getKey()
  {
    return this.a;
  }
  
  public String getValue()
  {
    return this.jdField_if;
  }
  
  public String toString()
  {
    return "Key=" + this.a + " Value=" + this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.KeyValue
 * JD-Core Version:    0.7.0.1
 */