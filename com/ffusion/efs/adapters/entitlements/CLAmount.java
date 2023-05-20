package com.ffusion.efs.adapters.entitlements;

import java.math.BigDecimal;

class CLAmount
{
  String a;
  BigDecimal jdField_if;
  
  CLAmount(BigDecimal paramBigDecimal, String paramString)
  {
    this.jdField_if = paramBigDecimal;
    this.a = paramString;
  }
  
  public BigDecimal getAmount()
  {
    return this.jdField_if;
  }
  
  public String getCurrencyCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.CLAmount
 * JD-Core Version:    0.7.0.1
 */