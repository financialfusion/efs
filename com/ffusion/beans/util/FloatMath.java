package com.ffusion.beans.util;

import java.io.Serializable;
import java.text.NumberFormat;

public class FloatMath
  implements Serializable
{
  protected double value1;
  protected double value2;
  protected NumberFormat nf = NumberFormat.getInstance();
  
  public FloatMath()
  {
    this.nf.setGroupingUsed(false);
    this.nf.setMinimumFractionDigits(2);
    this.nf.setMaximumFractionDigits(2);
  }
  
  public void setValue1(String paramString)
  {
    try
    {
      this.value1 = Double.parseDouble(paramString);
    }
    catch (Exception localException)
    {
      this.value1 = 0.0D;
    }
  }
  
  public void setValue2(String paramString)
  {
    try
    {
      this.value2 = Double.parseDouble(paramString);
    }
    catch (Exception localException)
    {
      this.value2 = 0.0D;
    }
  }
  
  public String getValue1()
  {
    return String.valueOf(this.nf.format(this.value1));
  }
  
  public String getValue2()
  {
    return String.valueOf(this.nf.format(this.value2));
  }
  
  public void setDecimal(String paramString)
  {
    int i = 2;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    this.nf.setMinimumFractionDigits(i);
    this.nf.setMaximumFractionDigits(i);
  }
  
  public String getAdd()
  {
    return String.valueOf(this.nf.format(this.value1 + this.value2));
  }
  
  public String getSubtract()
  {
    return String.valueOf(this.nf.format(this.value1 - this.value2));
  }
  
  public String getMultiply()
  {
    return String.valueOf(this.nf.format(this.value1 * this.value2));
  }
  
  public String getDivide()
  {
    if (this.value2 != 0.0D) {
      return String.valueOf(this.nf.format(this.value1 / this.value2));
    }
    return null;
  }
  
  public String getModulo()
  {
    if (this.value2 != 0.0D) {
      return String.valueOf(this.nf.format(this.value1 % this.value2));
    }
    return null;
  }
  
  public String getEquals()
  {
    return String.valueOf(this.value1 == this.value2);
  }
  
  public String getGreater()
  {
    return String.valueOf(this.value1 > this.value2);
  }
  
  public String getLess()
  {
    return String.valueOf(this.value1 < this.value2);
  }
  
  public String getMax()
  {
    if (this.value1 > this.value2) {
      return getValue1();
    }
    return getValue2();
  }
  
  public String getMin()
  {
    if (this.value1 < this.value2) {
      return getValue1();
    }
    return getValue2();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.FloatMath
 * JD-Core Version:    0.7.0.1
 */