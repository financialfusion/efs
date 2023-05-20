package com.ffusion.beans;

import com.ffusion.util.LocaleUtil;

public class LogEntry
{
  private int jdField_byte;
  private int jdField_try;
  private String jdField_new;
  private String jdField_int;
  private int jdField_do;
  private String a;
  private DateTime jdField_if;
  private int jdField_for;
  
  public String getId()
  {
    return String.valueOf(this.jdField_byte);
  }
  
  public int getIdValue()
  {
    return this.jdField_byte;
  }
  
  public void setId(int paramInt)
  {
    this.jdField_byte = paramInt;
  }
  
  public void setCategoryId(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public String getCategoryId()
  {
    return String.valueOf(this.jdField_try);
  }
  
  public int getCategoryIdValue()
  {
    return this.jdField_try;
  }
  
  public void setMessage(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getMessage()
  {
    return this.jdField_int;
  }
  
  public void setLevelId(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getLevelId()
  {
    return String.valueOf(this.jdField_do);
  }
  
  public int getLevelIdValue()
  {
    return this.jdField_do;
  }
  
  public void setLevelName(String paramString)
  {
    this.a = paramString;
  }
  
  public String getLevelName()
  {
    return this.a;
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.jdField_if = paramDateTime;
  }
  
  public void setCreateDate(String paramString)
  {
    try
    {
      this.jdField_if = new DateTime(LocaleUtil.getDefaultLocale());
      this.jdField_if.fromString(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getCreateDate()
  {
    return this.jdField_if.toString();
  }
  
  public DateTime getCreateDateValue()
  {
    return this.jdField_if;
  }
  
  public void setCode(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public String getCode()
  {
    return String.valueOf(this.jdField_for);
  }
  
  public int getCodeValue()
  {
    return this.jdField_for;
  }
  
  public void setCategoryName(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getCategoryName()
  {
    return this.jdField_new;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.LogEntry
 * JD-Core Version:    0.7.0.1
 */