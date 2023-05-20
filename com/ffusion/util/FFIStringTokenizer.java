package com.ffusion.util;

import java.util.StringTokenizer;

public class FFIStringTokenizer
  extends StringTokenizer
{
  private static final String jdField_int = " \t\n\r\f";
  private boolean jdField_try;
  private String jdField_for = null;
  private String jdField_new = null;
  private boolean jdField_if = false;
  private int a;
  private int jdField_do = 0;
  
  public FFIStringTokenizer(String paramString)
  {
    this(paramString, " \t\n\r\f", false);
  }
  
  public FFIStringTokenizer(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, false);
  }
  
  public FFIStringTokenizer(String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramString2, true);
    this.jdField_for = paramString2;
    this.jdField_new = paramString1;
    this.jdField_try = paramBoolean;
    if ((paramString1 != null) && (!paramString1.equals("")))
    {
      this.a = (a(paramString1, 0) + 1);
      if (this.jdField_for.indexOf(paramString1.charAt(paramString1.length() - 1)) != -1) {
        this.jdField_if = true;
      }
    }
    else
    {
      this.a = 0;
    }
  }
  
  public boolean hasMoreTokens()
  {
    if (this.jdField_try) {
      return super.hasMoreTokens();
    }
    return this.a > 0;
  }
  
  public String nextToken()
  {
    if (this.jdField_try) {
      return super.nextToken();
    }
    this.a -= 1;
    if ((this.a == 0) && (this.jdField_if))
    {
      this.jdField_if = false;
      return "";
    }
    String str = super.nextToken();
    this.jdField_do += str.length();
    if (a(str)) {
      return "";
    }
    if (this.a > 0)
    {
      super.nextToken();
      this.jdField_do += 1;
    }
    return str;
  }
  
  public String nextToken(String paramString)
  {
    if (this.jdField_try) {
      return super.nextToken(paramString);
    }
    this.jdField_for = paramString;
    if (hasMoreTokens())
    {
      if (this.jdField_for.indexOf(this.jdField_new.charAt(this.jdField_new.length() - 1)) != -1) {
        this.jdField_if = true;
      } else if ((this.a != 1) || (!this.jdField_if)) {
        this.jdField_if = false;
      }
      this.a = (a(this.jdField_new, this.jdField_do) + 1);
    }
    return super.nextToken(paramString);
  }
  
  public boolean hasMoreElements()
  {
    return hasMoreTokens();
  }
  
  public Object nextElement()
  {
    return nextToken();
  }
  
  public int countTokens()
  {
    if (this.jdField_try) {
      return super.countTokens();
    }
    return this.a > 0 ? this.a : 0;
  }
  
  private boolean a(String paramString)
  {
    return (paramString.length() == 1) && (this.jdField_for.indexOf(paramString) != -1);
  }
  
  private int a(String paramString, int paramInt)
  {
    int i = 0;
    while (paramInt < paramString.length())
    {
      if (this.jdField_for.indexOf(paramString.charAt(paramInt)) != -1) {
        i++;
      }
      paramInt++;
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.FFIStringTokenizer
 * JD-Core Version:    0.7.0.1
 */