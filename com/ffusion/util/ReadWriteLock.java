package com.ffusion.util;

import com.ffusion.util.logging.DebugLog;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;

public final class ReadWriteLock
{
  private int jdField_for = 0;
  private int jdField_new = 0;
  private int jdField_if = 0;
  private Object jdField_try = new Object();
  private Object jdField_else = new Object();
  private Object jdField_byte = new Object();
  private Object jdField_char = new Object();
  private Vector jdField_case = new Vector();
  private static int a = 60000;
  private static long jdField_do = 120000L;
  private a jdField_int = new a(a);
  
  public void finalize()
  {
    this.jdField_int.a();
  }
  
  public void getReadLock()
  {
    LockWrapper localLockWrapper1 = new LockWrapper("acquiring read lock");
    if (this.jdField_case.contains(localLockWrapper1))
    {
      LockWrapper localLockWrapper2 = (LockWrapper)this.jdField_case.get(this.jdField_case.indexOf(localLockWrapper1));
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("The same thread is acquiring a second lock.\n");
      localStringBuffer.append("Lock 1: ").append(localLockWrapper2.jdField_if).append("\n");
      localStringBuffer.append("Acquired at: ").append(localLockWrapper2.a).append("\n");
      for (int i = 2; i < localLockWrapper2.jdField_for.length; i++) {
        localStringBuffer.append("\tat ").append(localLockWrapper2.jdField_for[i]).append("\n");
      }
      localStringBuffer.append("Lock 2: ").append(localLockWrapper1.jdField_if).append("\n");
      localStringBuffer.append("Attemped at: ").append(localLockWrapper1.a).append("\n");
      for (i = 2; i < localLockWrapper1.jdField_for.length; i++) {
        localStringBuffer.append("\tat ").append(localLockWrapper1.jdField_for[i]).append("\n");
      }
      DebugLog.log(Level.SEVERE, localStringBuffer.toString());
    }
    synchronized (this.jdField_byte)
    {
      for (;;)
      {
        synchronized (this.jdField_try)
        {
          if ((this.jdField_for >= 0) && (this.jdField_new == 0) && (this.jdField_if == 0))
          {
            this.jdField_for += 1;
            this.jdField_case.add(new LockWrapper("read lock"));
            return;
          }
        }
        try
        {
          this.jdField_byte.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
  }
  
  public void escalateLock()
  {
    LockWrapper localLockWrapper1 = new LockWrapper("trying to escalate a lock");
    LockWrapper localLockWrapper2 = (LockWrapper)this.jdField_case.get(this.jdField_case.indexOf(localLockWrapper1));
    if ((localLockWrapper2 != null) && (!localLockWrapper2.jdField_if.equals("read lock")))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("The same thread is acquiring a second lock.\n");
      localStringBuffer.append("Lock 1: ").append(localLockWrapper2.jdField_if).append("\n");
      localStringBuffer.append("Acquired at: ").append(localLockWrapper2.a).append("\n");
      for (j = 2; j < localLockWrapper2.jdField_for.length; j++) {
        localStringBuffer.append("\tat ").append(localLockWrapper2.jdField_for[j]).append("\n");
      }
      localStringBuffer.append("Lock 2: ").append(localLockWrapper1.jdField_if).append("\n");
      localStringBuffer.append("Attemped at: ").append(localLockWrapper1.a).append("\n");
      for (j = 2; j < localLockWrapper1.jdField_for.length; j++) {
        localStringBuffer.append("\tat ").append(localLockWrapper1.jdField_for[j]).append("\n");
      }
      DebugLog.log(Level.SEVERE, localStringBuffer.toString());
    }
    int i = 0;
    int j = 0;
    synchronized (this.jdField_char)
    {
      for (;;)
      {
        synchronized (this.jdField_try)
        {
          if (i == 0)
          {
            this.jdField_for -= 1;
            i = 1;
          }
          if (this.jdField_for == 0)
          {
            if (j != 0) {
              this.jdField_if -= 1;
            }
            this.jdField_for = -1;
            this.jdField_case.remove(new LockWrapper("read lock"));
            this.jdField_case.add(new LockWrapper("escalated lock"));
            return;
          }
          if (j == 0)
          {
            j = 1;
            this.jdField_if += 1;
          }
        }
        try
        {
          this.jdField_char.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
  }
  
  public void getWriteLock()
  {
    LockWrapper localLockWrapper1 = new LockWrapper("acquiring write lock");
    if (this.jdField_case.contains(localLockWrapper1))
    {
      LockWrapper localLockWrapper2 = (LockWrapper)this.jdField_case.get(this.jdField_case.indexOf(localLockWrapper1));
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("The same thread is acquiring a second lock.\n");
      localStringBuffer.append("Lock 1: ").append(localLockWrapper2.jdField_if).append("\n");
      localStringBuffer.append("Acquired at: ").append(localLockWrapper2.a).append("\n");
      for (int j = 2; j < localLockWrapper2.jdField_for.length; j++) {
        localStringBuffer.append("\tat ").append(localLockWrapper2.jdField_for[j]).append("\n");
      }
      localStringBuffer.append("Lock 2: ").append(localLockWrapper1.jdField_if).append("\n");
      localStringBuffer.append("Attemped at: ").append(localLockWrapper1.a).append("\n");
      for (j = 2; j < localLockWrapper1.jdField_for.length; j++) {
        localStringBuffer.append("\tat ").append(localLockWrapper1.jdField_for[j]).append("\n");
      }
      DebugLog.log(Level.SEVERE, localStringBuffer.toString());
    }
    int i = 0;
    synchronized (this.jdField_else)
    {
      for (;;)
      {
        synchronized (this.jdField_try)
        {
          if (this.jdField_for == 0)
          {
            if (i != 0) {
              this.jdField_new -= 1;
            }
            if (this.jdField_if == 0)
            {
              this.jdField_for = -1;
              this.jdField_case.add(new LockWrapper("write lock"));
              return;
            }
          }
          if (i == 0)
          {
            i = 1;
            this.jdField_new += 1;
          }
        }
        try
        {
          this.jdField_else.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }
  }
  
  public void deEscalateLock()
  {
    int i = 0;
    synchronized (this.jdField_try)
    {
      if (this.jdField_for == -1)
      {
        this.jdField_case.remove(new LockWrapper("deescalated lock"));
        this.jdField_case.add(new LockWrapper("read lock"));
        this.jdField_for = 1;
        i = 1;
      }
    }
    if (i != 0) {
      synchronized (this.jdField_byte)
      {
        this.jdField_byte.notifyAll();
      }
    }
  }
  
  public void releaseLock()
  {
    LockWrapper localLockWrapper = new LockWrapper("about to release lock");
    int j;
    if (!this.jdField_case.contains(localLockWrapper))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("We are attempting to release a lock that we don't have.\n");
      localStringBuffer.append("Lock: ").append(localLockWrapper.jdField_if).append("\n");
      localStringBuffer.append("Attemped at: ").append(localLockWrapper.a).append("\n");
      for (j = 2; j < localLockWrapper.jdField_for.length; j++) {
        localStringBuffer.append("\tat ").append(localLockWrapper.jdField_for[j]).append("\n");
      }
      DebugLog.log(Level.SEVERE, localStringBuffer.toString());
    }
    int i;
    int m;
    int k;
    synchronized (this.jdField_try)
    {
      this.jdField_case.remove(new LockWrapper("returned lock"));
      if (this.jdField_for == -1)
      {
        i = 1;
        m = 1;
        this.jdField_for = 0;
      }
      else
      {
        i = 0;
        this.jdField_for -= 1;
        m = this.jdField_for == 0 ? 1 : 0;
        if (this.jdField_for < 0) {
          DebugLog.log(Level.SEVERE, "The value of _locksHeld is negative.\n");
        }
      }
      k = this.jdField_if > 0 ? 1 : 0;
      j = this.jdField_new > 0 ? 1 : 0;
    }
    if (m != 0)
    {
      if (k != 0) {
        synchronized (this.jdField_char)
        {
          this.jdField_char.notify();
        }
      }
      if (j != 0) {
        synchronized (this.jdField_else)
        {
          this.jdField_else.notify();
        }
      }
      if (i != 0) {
        synchronized (this.jdField_byte)
        {
          this.jdField_byte.notifyAll();
        }
      }
    }
  }
  
  class a
    extends Thread
  {
    private boolean jdField_if;
    private int a;
    
    public a(int paramInt)
    {
      this.a = paramInt;
      this.jdField_if = true;
      start();
    }
    
    public void run()
    {
      long l1 = this.a;
      while (this.jdField_if)
      {
        try
        {
          if (l1 > 0L) {
            sleep(l1);
          }
        }
        catch (Exception localException) {}
        long l2 = System.currentTimeMillis();
        synchronized (ReadWriteLock.this.jdField_try)
        {
          Iterator localIterator = ReadWriteLock.this.jdField_case.iterator();
          while (localIterator.hasNext())
          {
            LockWrapper localLockWrapper = (LockWrapper)localIterator.next();
            long l3 = l2 - localLockWrapper.a;
            if (l3 > ReadWriteLock.do)
            {
              DebugLog.log(Level.SEVERE, "A " + localLockWrapper.jdField_if + " has been held for " + l3 + " milliseconds.");
              DebugLog.log(Level.SEVERE, localLockWrapper.toString());
            }
          }
        }
        l1 = this.a - (System.currentTimeMillis() - l2);
        if (l1 <= 0L) {
          l1 = 0L;
        }
      }
    }
    
    public void a()
    {
      this.jdField_if = false;
      interrupt();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ReadWriteLock
 * JD-Core Version:    0.7.0.1
 */