package com.ffusion.util.logging.a;

public class f
{
  public long jdField_int = 0L;
  public String jdField_for;
  public int a;
  public int jdField_do;
  public int jdField_new;
  public int jdField_if;
  
  public f(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.jdField_for = paramString;
    this.jdField_int = System.currentTimeMillis();
    this.a = paramInt1;
    this.jdField_do = paramInt2;
    this.jdField_new = paramInt4;
    this.jdField_if = paramInt3;
  }
  
  public long jdField_new()
  {
    return this.jdField_int;
  }
  
  public void a(long paramLong)
  {
    this.jdField_int = paramLong;
  }
  
  public String jdField_for()
  {
    return this.jdField_for;
  }
  
  public void a(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public int jdField_do()
  {
    return this.a;
  }
  
  public void a(int paramInt)
  {
    this.a = paramInt;
  }
  
  public int jdField_int()
  {
    return this.jdField_do;
  }
  
  public void jdField_for(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public int jdField_if()
  {
    return this.jdField_new;
  }
  
  public void jdField_if(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public int a()
  {
    return this.jdField_if;
  }
  
  public void jdField_do(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public String toString()
  {
    return "user=" + this.a + ", system=" + this.jdField_do + ", idle=" + this.jdField_if + ", wait=" + this.jdField_new;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.a.f
 * JD-Core Version:    0.7.0.1
 */