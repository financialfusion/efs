package com.ffusion.alert.engine;

public abstract interface IAlertMessageQueue
{
  public abstract Object jdMethod_do()
    throws InterruptedException;
  
  public abstract Object[] jdMethod_if();
  
  public abstract void a(Object paramObject);
  
  public abstract boolean a();
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.IAlertMessageQueue
 * JD-Core Version:    0.7.0.1
 */