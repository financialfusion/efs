package com.ffusion.webservices.beans;

public class RecTransfer
  extends Transfer
{
  protected int numberTransfers;
  protected int frequency;
  
  public int getNumberTransfers()
  {
    return this.numberTransfers;
  }
  
  public void setNumberTransfers(int paramInt)
  {
    this.numberTransfers = paramInt;
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
 * Qualified Name:     com.ffusion.webservices.beans.RecTransfer
 * JD-Core Version:    0.7.0.1
 */