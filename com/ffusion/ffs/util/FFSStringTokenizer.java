package com.ffusion.ffs.util;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class FFSStringTokenizer
  extends StringTokenizer
{
  private String jdField_new = null;
  private String a = null;
  private int jdField_do = 0;
  private int jdField_int = 0;
  private int jdField_for = 0;
  private boolean jdField_if = false;
  private boolean jdField_try = false;
  
  public FFSStringTokenizer(String paramString)
  {
    super(paramString);
  }
  
  public FFSStringTokenizer(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public FFSStringTokenizer(String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramString2, paramBoolean);
  }
  
  public FFSStringTokenizer(String paramString, int paramInt)
  {
    super(paramString);
    this.jdField_new = paramString;
    this.jdField_for = paramInt;
    this.jdField_if = true;
    if (this.jdField_new != null) {
      this.jdField_do = this.jdField_new.length();
    }
  }
  
  public int countTokens()
  {
    int i = 0;
    if (this.jdField_try) {
      i = 1;
    }
    if (!this.jdField_if) {
      return i + super.countTokens();
    }
    if ((this.jdField_do == 0) || (this.jdField_for <= 0)) {
      return 0;
    }
    return i + (int)Math.ceil((this.jdField_do - this.jdField_int) / this.jdField_for);
  }
  
  public boolean hasMoreTokens()
  {
    boolean bool = false;
    if (!this.jdField_if)
    {
      bool = super.hasMoreTokens();
    }
    else
    {
      if ((this.jdField_do == 0) || (this.jdField_for <= 0)) {
        return false;
      }
      bool = this.jdField_int < this.jdField_do;
    }
    return (this.jdField_try) || (bool);
  }
  
  public boolean hasMoreElements()
  {
    boolean bool = false;
    if (!this.jdField_if) {
      bool = super.hasMoreElements();
    } else {
      bool = hasMoreTokens();
    }
    return (this.jdField_try) || (bool);
  }
  
  public String nextToken()
    throws NoSuchElementException
  {
    String str = null;
    if (this.jdField_try)
    {
      this.jdField_try = false;
      return this.a;
    }
    if (!this.jdField_if)
    {
      str = super.nextToken();
      this.a = str;
      return str;
    }
    if ((this.jdField_do == 0) || (this.jdField_for <= 0)) {
      throw new NoSuchElementException("No more element");
    }
    if (this.jdField_int >= this.jdField_do) {
      throw new NoSuchElementException("No more element");
    }
    if (this.jdField_int + this.jdField_for < this.jdField_do) {
      str = this.jdField_new.substring(this.jdField_int, this.jdField_int + this.jdField_for);
    } else {
      str = this.jdField_new.substring(this.jdField_int);
    }
    this.jdField_int += this.jdField_for;
    this.a = str;
    return str;
  }
  
  public Object nextElement()
    throws NoSuchElementException
  {
    Object localObject = null;
    if (this.jdField_try)
    {
      this.jdField_try = false;
      return this.a;
    }
    if (!this.jdField_if)
    {
      localObject = super.nextElement();
      this.a = ((String)localObject);
      return localObject;
    }
    localObject = nextToken();
    this.a = ((String)localObject);
    return localObject;
  }
  
  public void moveToPrevious()
  {
    this.jdField_try = true;
  }
  
  public static boolean hasDelimeter(String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2, true);
    return localStringTokenizer.countTokens() > 1;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSStringTokenizer
 * JD-Core Version:    0.7.0.1
 */