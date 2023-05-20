package com.ffusion.alert.plugins;

import java.io.Serializable;

public abstract interface OnAlertConnectionHandler
  extends Serializable
{
  public abstract void initializePriorToCSILConnect();
  
  public abstract void initializePriorToStartingAlertThread();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.OnAlertConnectionHandler
 * JD-Core Version:    0.7.0.1
 */