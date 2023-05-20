package com.ffusion.beans.util;

import java.io.Serializable;
import java.util.StringTokenizer;

public class StringUtil
  implements Serializable
{
  private String jdField_do;
  private String jdField_if;
  private String jdField_int;
  private String jdField_for;
  private int jdField_new;
  private int a;
  
  public void setValue1(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getValue1()
  {
    return this.jdField_do;
  }
  
  public void setValue2(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getValue2()
  {
    return this.jdField_if;
  }
  
  public String getEquals()
  {
    return String.valueOf(this.jdField_do.equals(this.jdField_if));
  }
  
  public String getSelectedIfEquals()
  {
    if (this.jdField_do.equals(this.jdField_if)) {
      return "selected";
    }
    return "";
  }
  
  public String getCheckedIfEquals()
  {
    if (this.jdField_do.equals(this.jdField_if)) {
      return "checked";
    }
    return "";
  }
  
  public String getNotEquals()
  {
    return String.valueOf(!this.jdField_do.equals(this.jdField_if));
  }
  
  public String getSelectedIfNotEquals()
  {
    if (!this.jdField_do.equals(this.jdField_if)) {
      return "selected";
    }
    return "";
  }
  
  public String getCheckedIfNotEquals()
  {
    if (!this.jdField_do.equals(this.jdField_if)) {
      return "checked";
    }
    return "";
  }
  
  public String getNotEmpty()
  {
    if ((this.jdField_do != null) && (!this.jdField_do.equals(""))) {
      return this.jdField_do;
    }
    return this.jdField_if;
  }
  
  public String getEqualsIgnoreCase()
  {
    return String.valueOf(this.jdField_do.equalsIgnoreCase(this.jdField_if));
  }
  
  public String getNotEqualsIgnoreCase()
  {
    return String.valueOf(!this.jdField_do.equalsIgnoreCase(this.jdField_if));
  }
  
  public String getUppercase()
  {
    return this.jdField_do.toUpperCase();
  }
  
  public String getLowercase()
  {
    return this.jdField_do.toLowerCase();
  }
  
  public void setFind(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public void setReplace(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if ((this.jdField_do != null) && (this.jdField_int != null) && (this.jdField_for != null))
    {
      for (int j = this.jdField_do.indexOf(this.jdField_int); j != -1; j = this.jdField_do.indexOf(this.jdField_int, i))
      {
        localStringBuffer.append(this.jdField_do.substring(i, j));
        localStringBuffer.append(this.jdField_for);
        i = j + this.jdField_int.length();
      }
      localStringBuffer.append(this.jdField_do.substring(i));
    }
    return localStringBuffer.toString();
  }
  
  public String getChar()
  {
    char c = ' ';
    try
    {
      int i = Integer.parseInt(this.jdField_if);
      c = this.jdField_do.charAt(i);
    }
    catch (Exception localException) {}
    return new Character(c).toString();
  }
  
  public String getLength()
  {
    return String.valueOf(this.jdField_do.length());
  }
  
  public String getIndex()
  {
    return String.valueOf(this.jdField_do.indexOf(this.jdField_if));
  }
  
  public void setRange(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    try
    {
      String str = localStringTokenizer.nextToken();
      this.jdField_new = Integer.parseInt(str);
      if (localStringTokenizer.hasMoreTokens()) {
        str = localStringTokenizer.nextToken();
      } else {
        str = null;
      }
      if (str != null) {
        this.a = (Integer.parseInt(str) + 1);
      } else {
        this.a = this.jdField_do.length();
      }
    }
    catch (Exception localException) {}
  }
  
  public String getSubstring()
  {
    if (this.jdField_new < 0) {
      this.jdField_new = 0;
    } else if (this.jdField_new > this.jdField_do.length()) {
      this.jdField_new = 0;
    }
    if (this.a > this.jdField_do.length()) {
      this.a = this.jdField_do.length();
    }
    if (this.a < this.jdField_new) {
      this.a = this.jdField_do.length();
    }
    return this.jdField_do.substring(this.jdField_new, this.a);
  }
  
  public String getConcatenate()
  {
    String str = null;
    if (this.jdField_do != null)
    {
      if (this.jdField_if != null) {
        str = this.jdField_do + this.jdField_if;
      } else {
        str = this.jdField_do;
      }
    }
    else if (this.jdField_if != null) {
      str = this.jdField_if;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.StringUtil
 * JD-Core Version:    0.7.0.1
 */