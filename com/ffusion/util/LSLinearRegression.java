package com.ffusion.util;

public class LSLinearRegression
{
  private double jdField_do;
  private double jdField_try;
  private double jdField_int;
  private double jdField_if;
  private double a;
  private double jdField_new;
  private double jdField_for;
  
  public LSLinearRegression()
  {
    reset();
  }
  
  public void reset()
  {
    this.jdField_do = 0.0D;
    this.jdField_try = 0.0D;
    this.jdField_int = 0.0D;
    this.jdField_if = 0.0D;
    this.a = 0.0D;
    this.jdField_new = 0.0D;
    this.jdField_for = 0.0D;
  }
  
  public void addPoint(double paramDouble1, double paramDouble2)
  {
    this.jdField_do += 1.0D;
    this.jdField_try += paramDouble1;
    this.jdField_int += paramDouble2;
    this.jdField_if += paramDouble1 * paramDouble1;
    this.a += paramDouble1 * paramDouble2;
  }
  
  public void calculateRegression()
  {
    if (this.jdField_do == 0.0D)
    {
      this.jdField_new = 0.0D;
      this.jdField_for = 0.0D;
      return;
    }
    if (this.jdField_do == 1.0D)
    {
      this.jdField_new = this.jdField_int;
      this.jdField_for = 0.0D;
      return;
    }
    double d1 = this.jdField_try / this.jdField_do;
    double d2 = this.jdField_if - this.jdField_try * d1;
    if (d2 == 0.0D)
    {
      this.jdField_new = 0.0D;
      this.jdField_for = 0.0D;
      return;
    }
    this.jdField_for = ((this.a - d1 * this.jdField_int) / d2);
    this.jdField_new = ((this.jdField_int - this.jdField_for * this.jdField_try) / this.jdField_do);
  }
  
  public double getEstimated(double paramDouble)
  {
    return this.jdField_new + this.jdField_for * paramDouble;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.LSLinearRegression
 * JD-Core Version:    0.7.0.1
 */