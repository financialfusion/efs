package com.ffusion.webservices.directory;

public class Binding
{
  private int jdField_do;
  private String jdField_for;
  private String jdField_int;
  private String a;
  private int jdField_if;
  
  public int getBindingID()
  {
    return this.jdField_do;
  }
  
  public void setBindingID(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getProtocol()
  {
    return this.jdField_for;
  }
  
  public void setProtocol(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getTransport()
  {
    return this.jdField_int;
  }
  
  public void setTransport(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getAddress()
  {
    return this.a;
  }
  
  public void setAddress(String paramString)
  {
    this.a = paramString;
  }
  
  public int getPriority()
  {
    return this.jdField_if;
  }
  
  public void setPriority(int paramInt)
  {
    this.jdField_if = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.directory.Binding
 * JD-Core Version:    0.7.0.1
 */