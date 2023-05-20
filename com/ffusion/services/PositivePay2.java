package com.ffusion.services;

import com.ffusion.positivepay.PPayException;
import java.util.HashMap;

public abstract interface PositivePay2
  extends PositivePay
{
  public abstract void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws PPayException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PositivePay2
 * JD-Core Version:    0.7.0.1
 */