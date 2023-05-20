package com.ffusion.beans.util;

import com.ffusion.util.beans.ExtendABean;
import java.io.Serializable;

public class CountryFormatDefn
  extends ExtendABean
  implements Serializable
{
  private FieldValidationDefns a = new FieldValidationDefns();
  private FieldValidationDefns jdField_do = new FieldValidationDefns();
  private FieldValidationDefns jdField_if = new FieldValidationDefns();
  public static final String INVALID_ZIPCODE = "";
  public static final String INVALID_PHONE = "";
  public static final String INVALID_SSN = "";
  
  public void addPhoneFormat(FieldValidationDefn paramFieldValidationDefn)
  {
    this.a.add(paramFieldValidationDefn);
  }
  
  public FieldValidationDefns getPhoneFormats()
  {
    return this.a;
  }
  
  public void addZipCodeFormat(FieldValidationDefn paramFieldValidationDefn)
  {
    this.jdField_do.add(paramFieldValidationDefn);
  }
  
  public FieldValidationDefns getZipCodeFormats()
  {
    return this.jdField_do;
  }
  
  public void addSSNFormat(FieldValidationDefn paramFieldValidationDefn)
  {
    this.jdField_if.add(paramFieldValidationDefn);
  }
  
  public FieldValidationDefns getSSNFormats()
  {
    return this.jdField_if;
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("CountryFormatDefn Bean:" + str);
    localStringBuffer.append("   Phone Formats:" + str);
    localStringBuffer.append(this.a.toString());
    localStringBuffer.append("   Zip Code Formats:" + str);
    localStringBuffer.append(this.jdField_do.toString());
    localStringBuffer.append("   SSN Formats:" + str);
    localStringBuffer.append(this.jdField_if.toString());
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.CountryFormatDefn
 * JD-Core Version:    0.7.0.1
 */