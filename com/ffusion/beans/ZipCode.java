package com.ffusion.beans;

import com.ffusion.util.Formatable;
import com.ffusion.util.Stringable;
import java.io.Serializable;

public class ZipCode
  extends Formatable
  implements Serializable, Stringable, Cloneable
{
  public static final String ZIP_5 = "5";
  public static final String ZIP_54S = "5-4";
  public static final String ZIP_54D = "5.4";
  public static final String ZIP_5_OR_9 = "5OR9";
  private String a;
  private boolean jdField_if = true;
  
  protected ZipCode()
  {
    setFormat("5");
  }
  
  public ZipCode(String paramString)
  {
    setFormat("5");
    fromString(paramString);
  }
  
  public ZipCode(String paramString1, String paramString2)
  {
    setFormat(paramString1);
    fromString(paramString2);
  }
  
  public ZipCode(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.a = paramString;
      setFormat("");
    }
    else
    {
      setFormat("5");
      fromString(paramString);
    }
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
    else if ((this.format.equals("5")) && (paramString.length() == 5))
    {
      this.a = paramString;
      this.jdField_if = true;
    }
    else if ((this.format.equals("5.4")) && (paramString.length() == 9))
    {
      this.a = (paramString.substring(0, 5) + '.' + paramString.substring(5));
      this.jdField_if = true;
    }
    else if ((this.format.equals("5-4")) && (paramString.length() == 9))
    {
      this.a = (paramString.substring(0, 5) + '-' + paramString.substring(5));
      this.jdField_if = true;
    }
    else if ((this.format.equals("5OR9")) && ((paramString.length() == 9) || (paramString.length() == 5)))
    {
      if (paramString.length() == 5) {
        this.a = paramString;
      } else {
        this.a = (paramString.substring(0, 5) + '-' + paramString.substring(5));
      }
      this.jdField_if = true;
    }
    else
    {
      this.jdField_if = formatUsingCustomPattern(paramString);
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
  
  public String getFormat()
  {
    return this.format;
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
 * Qualified Name:     com.ffusion.beans.ZipCode
 * JD-Core Version:    0.7.0.1
 */