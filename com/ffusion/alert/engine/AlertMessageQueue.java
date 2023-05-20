package com.ffusion.alert.engine;

public class AlertMessageQueue
  implements IAlertMessageQueue
{
  private static final int jdField_if = 100;
  private Object[] jdField_new = new Object[100];
  private int jdField_for = 0;
  private int jdField_do = 0;
  private int a = 0;
  private static final Object[] jdField_int = new Object[0];
  
  public synchronized Object jdField_do()
    throws InterruptedException
  {
    while (this.jdField_for == 0) {
      wait();
    }
    Object localObject = this.jdField_new[this.jdField_do];
    this.jdField_new[this.jdField_do] = null;
    this.jdField_do = ((this.jdField_do + 1) % this.jdField_new.length);
    this.jdField_for -= 1;
    return localObject;
  }
  
  public synchronized Object[] jdField_if()
  {
    if (this.jdField_for == 0) {
      return jdField_int;
    }
    Object[] arrayOfObject = new Object[this.jdField_for];
    int i = this.jdField_do;
    int j = 0;
    while (j < this.jdField_for)
    {
      arrayOfObject[(j++)] = this.jdField_new[i];
      this.jdField_new[i] = null;
      i = (i + 1) % this.jdField_new.length;
    }
    this.jdField_for = (this.jdField_do = this.a = 0);
    return arrayOfObject;
  }
  
  public synchronized void a(Object paramObject)
  {
    if (this.jdField_for == this.jdField_new.length)
    {
      Object[] arrayOfObject = this.jdField_new;
      this.jdField_new = new Object[this.jdField_for << 1];
      if (this.jdField_do != 0)
      {
        int i = this.jdField_for - this.jdField_do;
        System.arraycopy(arrayOfObject, this.jdField_do, this.jdField_new, 0, i);
        System.arraycopy(arrayOfObject, 0, this.jdField_new, i, this.jdField_do);
        this.jdField_do = 0;
      }
      else
      {
        System.arraycopy(arrayOfObject, 0, this.jdField_new, 0, this.jdField_for);
      }
      this.a = this.jdField_for;
    }
    this.jdField_new[this.a] = paramObject;
    this.a = ((this.a + 1) % this.jdField_new.length);
    this.jdField_for += 1;
    notifyAll();
  }
  
  public synchronized boolean a()
  {
    return this.jdField_for == 0;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.AlertMessageQueue
 * JD-Core Version:    0.7.0.1
 */