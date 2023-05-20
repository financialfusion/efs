package com.ffusion.alert.interfaces;

import java.io.Serializable;
import java.util.Properties;

public abstract interface IAEDeliveryInfo
  extends Serializable
{
  public static final int DI_ORDER_PRIMARY = 0;
  public static final int DI_ORDER_SECONDARY = 1;
  public static final int DI_MAXDELAY_INFINITE = -1;
  public static final int DI_MAXDELAY_NODELAY = 0;
  
  public abstract int getId();
  
  public abstract int getDeliveryOrder();
  
  public abstract void setDeliveryOrder(int paramInt);
  
  public abstract String getDeliveryChannelName();
  
  public abstract void setDeliveryChannelName(String paramString);
  
  public abstract Properties getDeliveryProperties();
  
  public abstract void setDeliveryProperties(Properties paramProperties);
  
  public abstract boolean isSuspended();
  
  public abstract void setSuspended(boolean paramBoolean);
  
  public abstract long getMaxDelay();
  
  public abstract void setMaxDelay(long paramLong);
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.IAEDeliveryInfo
 * JD-Core Version:    0.7.0.1
 */