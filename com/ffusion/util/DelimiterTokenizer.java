package com.ffusion.util;

import java.io.PrintStream;
import java.util.NoSuchElementException;

public class DelimiterTokenizer
{
  private String a;
  private char jdField_for;
  private int jdField_try;
  private int jdField_new;
  private boolean jdField_int;
  private int jdField_do;
  private int jdField_if;
  
  public DelimiterTokenizer(String paramString, char paramChar)
  {
    this.a = paramString;
    this.jdField_for = paramChar;
    this.jdField_try = this.a.length();
    this.jdField_new = -1;
    this.jdField_int = false;
  }
  
  public boolean hasMoreTokens()
  {
    if (!this.jdField_int) {
      a();
    }
    return this.jdField_int;
  }
  
  public String nextToken()
  {
    if (!this.jdField_int)
    {
      a();
      if (!this.jdField_int) {
        throw new NoSuchElementException();
      }
    }
    this.jdField_int = false;
    return this.a.substring(this.jdField_do, this.jdField_if);
  }
  
  public int countTokens()
  {
    if (this.jdField_new == this.jdField_try) {
      return 0;
    }
    int i = 0;
    for (int j = this.jdField_new + 1; j < this.jdField_try; j++) {
      if (this.a.charAt(j) == this.jdField_for) {
        i++;
      }
    }
    return i + 1;
  }
  
  private void a()
  {
    if (this.jdField_new == this.jdField_try)
    {
      this.jdField_int = false;
    }
    else
    {
      this.jdField_int = true;
      this.jdField_do = (++this.jdField_new);
      while ((this.jdField_new < this.jdField_try) && (this.a.charAt(this.jdField_new) != this.jdField_for)) {
        this.jdField_new += 1;
      }
      this.jdField_if = this.jdField_new;
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    DelimiterTokenizer localDelimiterTokenizer = new DelimiterTokenizer("this,is,a,,test,", ',');
    System.out.println("Token Count: " + localDelimiterTokenizer.countTokens());
    while (localDelimiterTokenizer.hasMoreTokens())
    {
      String str = localDelimiterTokenizer.nextToken();
      System.out.println("'" + str + "' (" + localDelimiterTokenizer.countTokens() + ") remaining");
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.DelimiterTokenizer
 * JD-Core Version:    0.7.0.1
 */