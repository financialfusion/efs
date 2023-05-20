package com.ffusion.alert.plugins;

import java.io.Serializable;

public abstract interface AlertTransactionHandler
  extends Serializable
{
  public abstract boolean isValid(double paramDouble1, double paramDouble2);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.AlertTransactionHandler
 * JD-Core Version:    0.7.0.1
 */