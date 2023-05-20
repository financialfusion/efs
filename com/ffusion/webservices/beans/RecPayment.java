package com.ffusion.webservices.beans;

public class RecPayment
  extends Payment
{
  protected int numberPayments;
  protected int frequency = 4;
  
  public int getNumberPayments()
  {
    return this.numberPayments;
  }
  
  public void setNumberPayments(int paramInt)
  {
    this.numberPayments = paramInt;
  }
  
  public int getFrequency()
  {
    return this.frequency;
  }
  
  public void setFrequency(int paramInt)
  {
    this.frequency = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.RecPayment
 * JD-Core Version:    0.7.0.1
 */