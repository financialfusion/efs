package com.ffusion.beans;

import com.ffusion.util.Formatable;
import com.ffusion.util.Stringable;
import java.io.Serializable;

public class Phone
  extends Formatable
  implements Serializable, Stringable, Cloneable
{
  public static final String PHONE_334P = "(3)3-4";
  public static final String PHONE_334S = "3-3-4";
  public static final String PHONE_334D = "3.3.4";
  public static final String PHONE_34S = "3-4";
  public static final String PHONE_34D = "3.4";
  private String a;
  private boolean jdField_if = true;
  
  protected Phone()
  {
    setFormat("(3)3-4");
  }
  
  public Phone(String paramString)
  {
    setFormat("(3)3-4");
    fromString(paramString);
  }
  
  public Phone(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.a = paramString;
      setFormat("");
    }
    else
    {
      setFormat("(3)3-4");
      fromString(paramString);
    }
  }
  
  public Phone(String paramString1, String paramString2)
  {
    setFormat(paramString1);
    fromString(paramString2);
  }
  
  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException) {}
    return null;
  }
  
  public void fromString(String paramString)
  {
    this.a = paramString;
    if ((paramString != null) && (paramString.length() > 0))
    {
      String str = removeFormatting(paramString);
      if (str.length() > 0) {
        a(str);
      } else {
        this.jdField_if = false;
      }
    }
    else
    {
      this.jdField_if = false;
    }
  }
  
  private void a(String paramString)
  {
    if ((this.bIsAllNumbers) && (!allNumbers(paramString)))
    {
      this.jdField_if = false;
    }
    else
    {
      StringBuffer localStringBuffer;
      if ((this.format.equals("(3)3-4")) && (paramString.length() == 10))
      {
        localStringBuffer = new StringBuffer("(");
        localStringBuffer.append(paramString.substring(0, 3));
        localStringBuffer.append(") ");
        localStringBuffer.append(paramString.substring(3, 6));
        localStringBuffer.append('-');
        localStringBuffer.append(paramString.substring(6, 10));
        this.a = localStringBuffer.toString();
        this.jdField_if = true;
      }
      else if ((this.format.equals("3-3-4")) && (paramString.length() == 10))
      {
        localStringBuffer = new StringBuffer();
        localStringBuffer.append(paramString.substring(0, 3));
        localStringBuffer.append("-");
        localStringBuffer.append(paramString.substring(3, 6));
        localStringBuffer.append('-');
        localStringBuffer.append(paramString.substring(6, 10));
        this.a = localStringBuffer.toString();
        this.jdField_if = true;
      }
      else if ((this.format.equals("3.3.4")) && (paramString.length() == 10))
      {
        localStringBuffer = new StringBuffer();
        localStringBuffer.append(paramString.substring(0, 3));
        localStringBuffer.append(".");
        localStringBuffer.append(paramString.substring(3, 6));
        localStringBuffer.append('.');
        localStringBuffer.append(paramString.substring(6, 10));
        this.a = localStringBuffer.toString();
        this.jdField_if = true;
      }
      else if ((this.format.equals("3-4")) && (paramString.length() == 7))
      {
        localStringBuffer = new StringBuffer();
        localStringBuffer.append(paramString.substring(0, 3));
        localStringBuffer.append("-");
        localStringBuffer.append(paramString.substring(3, 7));
        this.a = localStringBuffer.toString();
        this.jdField_if = true;
      }
      else if ((this.format.equals("3.4")) && (paramString.length() == 7))
      {
        localStringBuffer = new StringBuffer();
        localStringBuffer.append(paramString.substring(0, 3));
        localStringBuffer.append(".");
        localStringBuffer.append(paramString.substring(3, 7));
        this.a = localStringBuffer.toString();
        this.jdField_if = true;
      }
      else
      {
        this.jdField_if = formatUsingCustomPattern(paramString);
      }
    }
  }
  
  protected boolean formatUsingCustomPattern(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    boolean bool = super.formatUsingCustomPattern(localStringBuffer, paramString);
    if (bool == true) {
      this.a = localStringBuffer.toString();
    }
    return bool;
  }
  
  public boolean isValid()
  {
    return this.jdField_if;
  }
  
  public void setFormat(String paramString)
  {
    super.setFormat(paramString);
    if (this.a != null) {
      fromString(this.a);
    } else {
      this.jdField_if = false;
    }
  }
  
  public String toString()
  {
    return this.a;
  }
  
  public String getString()
  {
    return toString();
  }
  
  public void setString(String paramString)
  {
    fromString(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Phone
 * JD-Core Version:    0.7.0.1
 */