package com.ffusion.util;

public abstract interface MessageBroadcaster
{
  public abstract void addMessageListener(MessageListener paramMessageListener);
  
  public abstract void removeMessageListener(MessageListener paramMessageListener);
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.MessageBroadcaster
 * JD-Core Version:    0.7.0.1
 */