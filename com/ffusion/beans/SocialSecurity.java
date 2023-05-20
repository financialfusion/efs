package com.ffusion.beans;

import com.ffusion.util.Formatable;
import com.ffusion.util.Stringable;
import java.io.Serializable;

public class SocialSecurity
  extends Formatable
  implements Serializable, Stringable, Cloneable
{
  private String jdField_if;
  private boolean a = true;
  
  protected SocialSecurity() {}
  
  public SocialSecurity(String paramString)
  {
    fromString(paramString);
  }
  
  public SocialSecurity(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.jdField_if = paramString;
    } else {
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
    this.jdField_if = paramString;
    if ((paramString != null) && (paramString.length() > 0) && (this.format != null))
    {
      String str = removeFormatting(paramString);
      a(str);
    }
  }
  
  private void a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    boolean bool = super.formatUsingCustomPattern(localStringBuffer, paramString);
    if (bool == true) {
      this.jdField_if = localStringBuffer.toString();
    }
    this.a = bool;
  }
  
  public boolean isValid()
  {
    return this.a;
  }
  
  public String toString()
  {
    return this.jdField_if;
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
 * Qualified Name:     com.ffusion.beans.SocialSecurity
 * JD-Core Version:    0.7.0.1
 */