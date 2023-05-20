package com.ffusion.alert.interfaces;

import java.io.PrintWriter;
import java.util.Properties;

public abstract interface IAEChannel
  extends IAEStatusConstants
{
  public abstract void init(Properties paramProperties, IAEAlertEngine paramIAEAlertEngine, PrintWriter paramPrintWriter)
    throws Exception;
  
  public abstract int processAlert(IAEAlertInstance paramIAEAlertInstance, Properties paramProperties);
  
  public abstract void stop();
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEChannel
 * JD-Core Version:    0.7.0.1
 */