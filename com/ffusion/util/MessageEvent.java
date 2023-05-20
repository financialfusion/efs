package com.ffusion.util;

import java.util.EventObject;

public class MessageEvent
  extends EventObject
{
  private Object a;
  
  public MessageEvent(Object paramObject1, Object paramObject2)
  {
    super(paramObject1);
    this.a = paramObject2;
  }
  
  public Object getMessage()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.MessageEvent
 * JD-Core Version:    0.7.0.1
 */