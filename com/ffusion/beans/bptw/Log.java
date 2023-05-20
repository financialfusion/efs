package com.ffusion.beans.bptw;

public class Log
{
  private int jdField_do;
  private String jdField_if;
  private String a;
  private String jdField_int;
  private String jdField_for;
  
  public void setType(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public void setLogType(String paramString)
  {
    try
    {
      this.jdField_do = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getType()
  {
    return String.valueOf(this.jdField_do);
  }
  
  public int getTypeValue()
  {
    return this.jdField_do;
  }
  
  public void setDate(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getDate()
  {
    return this.jdField_if;
  }
  
  public void setInstructionType(String paramString)
  {
    this.a = paramString;
  }
  
  public String getInstructionType()
  {
    return this.a;
  }
  
  public void setInstructionID(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getInstructionID()
  {
    return this.jdField_int;
  }
  
  public void setContent(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getContent()
  {
    return this.jdField_for;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bptw.Log
 * JD-Core Version:    0.7.0.1
 */