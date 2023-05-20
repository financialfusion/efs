package com.ffusion.ffs.util;

import com.ffusion.ffs.interfaces.FFSException;

public class FFSQueue
{
  public static final int UNLIMITTED = -1;
  public static final int DEFAULT_MIN_SIZE = 4096;
  public static final int DEFAULT_MAX_SIZE = 32768;
  private int jdField_if = 4096;
  private int jdField_for = 32768;
  private int jdField_new = 0;
  private int jdField_int = 0;
  private Object[] jdField_do = null;
  private Object a = new Object();
  
  public FFSQueue()
  {
    this.jdField_do = new Object[4096];
  }
  
  public FFSQueue(int paramInt1, int paramInt2)
  {
    this.jdField_do = new Object[paramInt1];
    this.jdField_if = paramInt1;
    this.jdField_for = paramInt2;
  }
  
  public void add(Object paramObject)
    throws FFSException
  {
    if (paramObject == null) {
      return;
    }
    synchronized (this.a)
    {
      if (this.jdField_int < this.jdField_do.length)
      {
        this.jdField_do[((this.jdField_new + this.jdField_int) % this.jdField_do.length)] = paramObject;
        this.jdField_int += 1;
      }
      else if (this.jdField_do.length < this.jdField_for)
      {
        if (this.jdField_for <= 0)
        {
          Object[] arrayOfObject1 = new Object[this.jdField_do.length * 2];
          a(this.jdField_do, arrayOfObject1);
          this.jdField_do = arrayOfObject1;
        }
        else
        {
          int i = this.jdField_do.length;
          Object[] arrayOfObject2;
          if (i * 2 <= this.jdField_for)
          {
            arrayOfObject2 = new Object[this.jdField_do.length * 2];
            a(this.jdField_do, arrayOfObject2);
            this.jdField_do = arrayOfObject2;
          }
          else
          {
            arrayOfObject2 = new Object[this.jdField_for];
            a(this.jdField_do, arrayOfObject2);
            this.jdField_do = arrayOfObject2;
          }
        }
        this.jdField_new = 0;
        this.jdField_do[this.jdField_int] = paramObject;
        this.jdField_int += 1;
      }
      else
      {
        throw new FFSException("Failed to enqueue an object: queue full");
      }
      this.a.notify();
    }
  }
  
  private void a(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
    throws FFSException
  {
    if ((paramArrayOfObject1 == null) || (paramArrayOfObject1.length <= 0)) {
      return;
    }
    if ((paramArrayOfObject2 == null) || (paramArrayOfObject2.length < paramArrayOfObject1.length)) {
      throw new FFSException("Copy operation failed: destination array too short or is null");
    }
    for (int i = 0; i < this.jdField_int; i++) {
      paramArrayOfObject2[i] = paramArrayOfObject1[((this.jdField_new + i) % paramArrayOfObject1.length)];
    }
  }
  
  public Object get()
  {
    synchronized (this.a)
    {
      for (;;)
      {
        if (this.jdField_int <= 0) {
          try
          {
            wait();
          }
          catch (InterruptedException localInterruptedException) {}
        }
      }
      if (this.jdField_int <= 0) {
        return null;
      }
      return a();
    }
  }
  
  private Object a()
  {
    Object localObject = null;
    if (this.jdField_int <= 0) {
      return null;
    }
    localObject = this.jdField_do[this.jdField_new];
    this.jdField_do[this.jdField_new] = null;
    this.jdField_new += 1;
    if (this.jdField_new >= this.jdField_do.length) {
      this.jdField_new -= this.jdField_do.length;
    }
    this.jdField_int -= 1;
    return localObject;
  }
  
  public int getMinSize()
  {
    return this.jdField_if;
  }
  
  public int getMaxSize()
  {
    return this.jdField_for;
  }
  
  public int size()
  {
    return this.jdField_int;
  }
  
  public boolean isEmpty()
  {
    return this.jdField_int <= 0;
  }
  
  public Object get(long paramLong)
  {
    Object localObject1 = null;
    synchronized (this.a)
    {
      if (this.jdField_int <= 0) {
        try
        {
          if (paramLong != 0L) {
            wait(paramLong);
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          return null;
        }
      }
      localObject1 = a();
    }
    return localObject1;
  }
  
  public boolean peek(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    for (int i = 0; i < this.jdField_do.length; i++) {
      if ((this.jdField_do[i] != null) && (this.jdField_do[i].equals(paramObject))) {
        return true;
      }
    }
    return false;
  }
  
  public Object[] removeAll()
  {
    Object[] arrayOfObject = null;
    synchronized (this.a)
    {
      arrayOfObject = new Object[this.jdField_int];
      for (int i = 0; i < this.jdField_int; i++)
      {
        int j = (this.jdField_new + i) % this.jdField_do.length;
        arrayOfObject[i] = this.jdField_do[j];
        this.jdField_do[j] = null;
      }
      this.jdField_new = 0;
      this.jdField_int = 0;
    }
    return arrayOfObject;
  }
  
  public Object peek(int paramInt)
  {
    return (paramInt < 0) || (paramInt > this.jdField_int) ? null : this.jdField_do[((this.jdField_new + paramInt) % this.jdField_do.length)];
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSQueue
 * JD-Core Version:    0.7.0.1
 */