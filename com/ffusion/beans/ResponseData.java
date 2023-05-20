package com.ffusion.beans;

import java.util.ArrayList;

public class ResponseData
  extends ArrayList
{
  private int a;
  private String jdField_if;
  
  public ResponseData()
  {
    this.a = 0;
  }
  
  public ResponseData(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void setError(int paramInt)
  {
    this.a = paramInt;
  }
  
  public int getError()
  {
    return this.a;
  }
  
  public void setMessage(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getMessage()
  {
    return this.jdField_if;
  }
  
  public Object getObject()
  {
    return get(0);
  }
  
  public Object getObject(int paramInt)
  {
    return get(paramInt);
  }
  
  public void addObject(Object paramObject)
  {
    add(paramObject);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ResponseData
 * JD-Core Version:    0.7.0.1
 */