package com.ffusion.ffs.bpw.interfaces;

public class NonBusinessDay
  extends BPWInfoBase
{
  public int date;
  public int paydate;
  
  public NonBusinessDay() {}
  
  public NonBusinessDay(int paramInt1, int paramInt2)
  {
    this.date = paramInt1;
    this.paydate = paramInt2;
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    return "date = " + (this.date == 0 ? "null" : new StringBuffer().append("\"").append(this.date).append("\"").toString()) + str + "paydate = " + (this.paydate == 0 ? "null" : new StringBuffer().append("\"").append(this.paydate).append("\"").toString());
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.NonBusinessDay
 * JD-Core Version:    0.7.0.1
 */