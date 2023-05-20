package com.ffusion.entitlements;

import java.io.Serializable;
import java.util.HashMap;

public abstract interface Limit
  extends Serializable
{
  public abstract boolean isLimitExceeded(Limitable paramLimitable);
  
  public abstract boolean isLimitExceeded(Limit paramLimit);
  
  public abstract Limit copy();
  
  public abstract String getId();
  
  public abstract void setId(String paramString);
  
  public abstract String getName();
  
  public abstract void setName(String paramString);
  
  public abstract String getLimitValue();
  
  public abstract HashMap getNameValuePairs();
  
  public abstract void set(Limit paramLimit);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.Limit
 * JD-Core Version:    0.7.0.1
 */