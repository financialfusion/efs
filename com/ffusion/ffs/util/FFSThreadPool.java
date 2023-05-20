package com.ffusion.ffs.util;

import java.io.PrintStream;

public final class FFSThreadPool
  extends ThreadGroup
  implements FFSConst
{
  private final BlockingQueue B = new BlockingQueue();
  private int E = 0;
  private int x = 0;
  private volatile int C = 0;
  private volatile int v = 0;
  private boolean A = false;
  private boolean y = false;
  private int u = 0;
  private Object z = new Object();
  private static int w = 0;
  private static int D = 0;
  
  public FFSThreadPool(int paramInt1, int paramInt2)
  {
    super("FFSThreadPool" + w++);
    synchronized (this)
    {
      this.E = paramInt1;
      this.x = (paramInt2 > 0 ? paramInt2 : 2147483647);
      if (paramInt1 > this.x) {
        throw new IllegalArgumentException("FFSThreadPool: initialThreadCount > maximumSize");
      }
      int i = paramInt1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        new a().start();
      }
    }
    synchronized (this.z)
    {
      try
      {
        this.z.wait();
        this.z = null;
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  public FFSThreadPool()
  {
    super("FFSThreadPool" + w++);
    this.x = 0;
  }
  
  public synchronized void execute(Runnable paramRunnable)
    throws FFSThreadPool.b
  {
    if (this.A) {
      throw new b();
    }
    if ((this.v < this.x) && (this.B.waitingThreads() == 0)) {
      synchronized (this.B)
      {
        if ((this.v < this.x) && (this.B.waitingThreads() == 0)) {
          new a().start();
        }
      }
    }
    this.B.add(paramRunnable);
  }
  
  public final synchronized void execute(FFSCommand paramFFSCommand, Object paramObject)
    throws FFSThreadPool.b
  {
    execute(new Runnable()
    {
      private final FFSCommand val$action;
      private final Object val$argument;
      
      public void run()
      {
        this.val$action.execute(this.val$argument);
      }
    });
  }
  
  public synchronized void close()
  {
    this.A = true;
    this.B.close();
  }
  
  public synchronized void join()
  {
    try
    {
      this.u += 1;
      wait();
      this.u -= 1;
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public synchronized void deflate(boolean paramBoolean)
  {
    if ((this.y = paramBoolean)) {
      while (this.C > this.E) {
        this.B.add(new Runnable()
        {
          public void run()
          {
            Thread.currentThread().interrupt();
          }
        });
      }
    }
  }
  
  public static final class c
  {
    private static FFSThreadPool a = new FFSThreadPool(10, 10);
    
    public static void a(String[] paramArrayOfString)
    {
      c localc = new c();
      localc.jdMethod_if("hello");
      System.out.println("hello fired");
      localc.a("world");
      System.out.println("world fired, now waiting for idle pool");
      a.join();
      System.out.println("Pool is idle, closing");
      a.close();
      System.out.println("Pool closed");
    }
    
    private void jdMethod_if(String paramString)
    {
      a.execute(new FFSThreadPool.3(this, paramString));
    }
    
    private void a(String paramString)
    {
      a.execute(new FFSThreadPool.4(this), "world");
    }
  }
  
  public final class b
    extends RuntimeException
  {
    b()
    {
      super();
    }
  }
  
  private final class a
    extends Thread
  {
    public a()
    {
      super("T" + FFSThreadPool.D);
    }
    
    public void run()
    {
      synchronized (FFSThreadPool.this)
      {
        FFSThreadPool.access$104(FFSThreadPool.this);
        FFSThreadPool.access$204(FFSThreadPool.this);
        if ((FFSThreadPool.this.z != null) && (FFSThreadPool.this.E == FFSThreadPool.this.v)) {
          synchronized (FFSThreadPool.this.z)
          {
            FFSThreadPool.this.z.notify();
          }
        }
      }
      try
      {
        for (;;)
        {
          if ((!isInterrupted()) && (!FFSThreadPool.this.A)) {
            try
            {
              ??? = (Runnable)FFSThreadPool.this.B.get();
              synchronized (FFSThreadPool.this)
              {
                FFSThreadPool.access$106(FFSThreadPool.this);
              }
              ((Runnable)???).run();
            }
            catch (InterruptedException localInterruptedException) {}catch (BlockingQueue.c localc) {}catch (Exception localException)
            {
              FFSDebug.log("Ignoring exception thrown from  user-supplied thread-pool action:\n\t" + FFSDebug.stackTrace(localException));
            }
            finally
            {
              synchronized (FFSThreadPool.this)
              {
                FFSThreadPool.access$104(FFSThreadPool.this);
                if ((FFSThreadPool.this.u > 0) && (FFSThreadPool.this.C == FFSThreadPool.this.v)) {
                  FFSThreadPool.this.notify();
                }
              }
            }
          }
        }
      }
      finally
      {
        synchronized (FFSThreadPool.this)
        {
          FFSThreadPool.access$106(FFSThreadPool.this);
          FFSThreadPool.access$206(FFSThreadPool.this);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSThreadPool
 * JD-Core Version:    0.7.0.1
 */