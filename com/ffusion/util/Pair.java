package com.ffusion.util;

public class Pair
{
  Object jdField_if = null;
  Object a = null;
  
  public Pair() {}
  
  public Pair(Object paramObject)
  {
    this();
    this.jdField_if = paramObject;
  }
  
  public Pair(Object paramObject1, Object paramObject2)
  {
    this(paramObject1);
    this.a = paramObject2;
  }
  
  public void setKey(Object paramObject)
  {
    this.jdField_if = paramObject;
  }
  
  public void setValue(Object paramObject)
  {
    this.a = paramObject;
  }
  
  public Object getKey()
  {
    return this.jdField_if;
  }
  
  public Object getValue()
  {
    return this.a;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((!(paramObject instanceof Pair)) || (paramObject == null)) {
      return false;
    }
    return (this.jdField_if.equals(((Pair)paramObject).getKey())) && (this.a.equals(((Pair)paramObject).getValue()));
  }
  
  public String toString()
  {
    return "key=" + this.jdField_if.toString() + ", value=" + this.a.toString();
  }
  
  public int hashCode()
  {
    return this.jdField_if.hashCode();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Pair
 * JD-Core Version:    0.7.0.1
 */