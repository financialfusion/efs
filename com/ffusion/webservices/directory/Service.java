package com.ffusion.webservices.directory;

import java.util.ArrayList;
import java.util.Date;

public class Service
{
  private int jdField_if;
  private String a;
  private String jdField_do;
  private Date jdField_int;
  private ArrayList jdField_for;
  
  public int getServiceID()
  {
    return this.jdField_if;
  }
  
  public void setServiceID(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public String getDesc()
  {
    return this.jdField_do;
  }
  
  public void setDesc(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public Date getUpdateDate()
  {
    return this.jdField_int;
  }
  
  public void setUpdateDate(Date paramDate)
  {
    this.jdField_int = paramDate;
  }
  
  public ArrayList getBindings()
  {
    return this.jdField_for;
  }
  
  public void setBindings(ArrayList paramArrayList)
  {
    this.jdField_for = paramArrayList;
  }
  
  public void addBinding(Binding paramBinding)
  {
    this.jdField_for.add(paramBinding);
  }
  
  public String getName()
  {
    return this.a;
  }
  
  public void setName(String paramString)
  {
    this.a = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.directory.Service
 * JD-Core Version:    0.7.0.1
 */