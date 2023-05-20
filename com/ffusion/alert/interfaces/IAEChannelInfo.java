package com.ffusion.alert.interfaces;

import java.io.Serializable;
import java.util.Properties;

public abstract interface IAEChannelInfo
  extends Serializable
{
  public abstract int getId();
  
  public abstract String getChannelName();
  
  public abstract void setChannelName(String paramString);
  
  public abstract String getClassName();
  
  public abstract void setClassName(String paramString);
  
  public abstract int getNumWorkers();
  
  public abstract void setNumWorkers(int paramInt);
  
  public abstract Properties getInitInfo();
  
  public abstract void setInitInfo(Properties paramProperties);
  
  public abstract String getApplicationName();
  
  public abstract boolean isInstalled();
  
  public abstract boolean isLoadable();
  
  public abstract boolean isLoaded();
  
  public abstract boolean isFailed();
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEChannelInfo
 * JD-Core Version:    0.7.0.1
 */