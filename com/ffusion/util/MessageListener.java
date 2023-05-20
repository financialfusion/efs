package com.ffusion.util;

import java.util.EventListener;

public abstract interface MessageListener
  extends EventListener
{
  public abstract void handleMessage(MessageEvent paramMessageEvent);
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.MessageListener
 * JD-Core Version:    0.7.0.1
 */