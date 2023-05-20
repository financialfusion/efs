package com.ffusion.beans.util;

import com.ffusion.util.beans.ExtendABean;

public class FieldValidationDefn
  extends ExtendABean
{
  private String jdField_if = null;
  private String a = null;
  
  public String getMask()
  {
    return this.a;
  }
  
  public void setMask(String paramString)
  {
    this.a = paramString;
  }
  
  public String getName()
  {
    return this.jdField_if;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("FieldValidationDefn Bean:" + str);
    localStringBuffer.append("   name:" + this.jdField_if + str);
    localStringBuffer.append("   mask:" + this.a + str);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.FieldValidationDefn
 * JD-Core Version:    0.7.0.1
 */