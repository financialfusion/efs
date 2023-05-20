package com.ffusion.beans.fileimporter;

public class FieldDefinition
  implements Cloneable
{
  private int jdField_if;
  private String jdField_do;
  private int jdField_new;
  private String jdField_int = "0";
  private String jdField_for = "0";
  private String a;
  
  public FieldDefinition() {}
  
  public Object clone()
  {
    FieldDefinition localFieldDefinition = new FieldDefinition();
    localFieldDefinition.setFieldID(this.jdField_if);
    localFieldDefinition.jdField_do = this.jdField_do;
    localFieldDefinition.setFieldNumber(this.jdField_new);
    localFieldDefinition.setFieldStart(this.jdField_int);
    localFieldDefinition.setFieldEnd(this.jdField_for);
    localFieldDefinition.a = this.a;
    return localFieldDefinition;
  }
  
  public void set(FieldDefinition paramFieldDefinition)
  {
    if (paramFieldDefinition == null)
    {
      this.jdField_if = 0;
      this.jdField_do = null;
      this.jdField_new = 0;
      this.jdField_int = "0";
      this.jdField_for = "0";
      this.a = null;
      return;
    }
    this.jdField_if = paramFieldDefinition.getFieldID();
    this.jdField_do = new String(paramFieldDefinition.getName());
    this.jdField_new = paramFieldDefinition.getFieldNumber();
    this.jdField_int = paramFieldDefinition.getFieldStartString();
    this.jdField_for = paramFieldDefinition.getFieldEndString();
    if (paramFieldDefinition.getDefaultValue() != null) {
      this.a = new String(paramFieldDefinition.getDefaultValue());
    }
  }
  
  public FieldDefinition(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public int getFieldID()
  {
    return this.jdField_if;
  }
  
  public void setFieldID(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public String getName()
  {
    return this.jdField_do;
  }
  
  public void setName(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public int getFieldNumber()
  {
    return this.jdField_new;
  }
  
  public void setFieldNumber(String paramString)
  {
    try
    {
      this.jdField_new = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.jdField_new = 0;
    }
  }
  
  public void setFieldNumber(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public int getFieldStart()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.jdField_int);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public String getFieldStartString()
  {
    return this.jdField_int;
  }
  
  public void setFieldStart(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public void setFieldStart(int paramInt)
  {
    this.jdField_int = Integer.toString(paramInt);
  }
  
  public int getFieldEnd()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.jdField_for);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public String getFieldEndString()
  {
    return this.jdField_for;
  }
  
  public void setFieldEnd(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public void setFieldEnd(int paramInt)
  {
    this.jdField_for = Integer.toString(paramInt);
  }
  
  public String getDefaultValue()
  {
    return this.a;
  }
  
  public void setDefaultValue(String paramString)
  {
    this.a = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.FieldDefinition
 * JD-Core Version:    0.7.0.1
 */