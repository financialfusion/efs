package com.ffusion.ffs.bpw.backend;

import com.ffusion.ffs.util.FFSDebug;

public class MonitorBackend
  implements Runnable
{
  private int a;
  
  public MonitorBackend(int paramInt)
  {
    this.a = paramInt;
  }
  
  public synchronized void done()
  {
    notify();
  }
  
  public synchronized void run()
  {
    try
    {
      wait(this.a);
    }
    catch (InterruptedException localInterruptedException)
    {
      FFSDebug.log("### MonitorBackend.run: InterruptedException");
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.backend.MonitorBackend
 * JD-Core Version:    0.7.0.1
 */