package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.util.FFSDebug;

public class Semaphore
{
  public boolean logged = false;
  private boolean a = false;
  
  public synchronized void wait_for()
  {
    if (this.a)
    {
      FFSDebug.log("Semaphore2: Wake up was already called, no wait is needed.", 6);
      return;
    }
    try
    {
      wait();
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public synchronized void wait_for(long paramLong)
  {
    if (this.a)
    {
      FFSDebug.log("Semaphore2: Wake up was already called, no wait is needed.", 6);
      return;
    }
    try
    {
      wait(paramLong);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public synchronized void wake_up()
  {
    this.a = true;
    notify();
  }
  
  public synchronized void wake_upall()
  {
    this.a = true;
    notifyAll();
  }
  
  public synchronized void reset()
  {
    this.a = false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.Semaphore
 * JD-Core Version:    0.7.0.1
 */