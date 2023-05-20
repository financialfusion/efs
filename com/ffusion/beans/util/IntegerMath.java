package com.ffusion.beans.util;

import java.io.Serializable;
import java.util.Random;

public class IntegerMath
  implements Serializable
{
  private long jdField_if;
  private long a;
  
  public void setValue1(String paramString)
  {
    try
    {
      this.jdField_if = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      this.jdField_if = 0L;
    }
  }
  
  public void setValue2(String paramString)
  {
    try
    {
      this.a = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      this.a = 0L;
    }
  }
  
  public String getValue1()
  {
    return String.valueOf(this.jdField_if);
  }
  
  public String getValue2()
  {
    return String.valueOf(this.a);
  }
  
  public static String getRandom()
  {
    Random localRandom = new Random();
    int i = localRandom.nextInt();
    return String.valueOf(i * (i < 0 ? -1 : 1));
  }
  
  public String getAdd()
  {
    return String.valueOf(this.jdField_if + this.a);
  }
  
  public String getSubtract()
  {
    return String.valueOf(this.jdField_if - this.a);
  }
  
  public String getMultiply()
  {
    return String.valueOf(this.jdField_if * this.a);
  }
  
  public String getDivide()
  {
    if (this.a != 0L) {
      return String.valueOf(this.jdField_if / this.a);
    }
    return null;
  }
  
  public String getModulo()
  {
    if (this.a != 0L) {
      return String.valueOf(this.jdField_if % this.a);
    }
    return null;
  }
  
  public String getEquals()
  {
    return String.valueOf(this.jdField_if == this.a);
  }
  
  public String getGreater()
  {
    return String.valueOf(this.jdField_if > this.a);
  }
  
  public String getLess()
  {
    return String.valueOf(this.jdField_if < this.a);
  }
  
  public String getGreaterEquals()
  {
    return String.valueOf(this.jdField_if >= this.a);
  }
  
  public String getLessEquals()
  {
    return String.valueOf(this.jdField_if <= this.a);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.IntegerMath
 * JD-Core Version:    0.7.0.1
 */