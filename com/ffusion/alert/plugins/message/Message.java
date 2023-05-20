package com.ffusion.alert.plugins.message;

import java.io.Serializable;

public abstract interface Message
  extends Serializable
{
  public abstract void setSubject(String paramString);
  
  public abstract void setContent(String paramString);
  
  public abstract void addContent(String paramString);
  
  public abstract String getSubject();
  
  public abstract String getContent();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.message.Message
 * JD-Core Version:    0.7.0.1
 */