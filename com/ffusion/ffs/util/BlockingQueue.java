package com.ffusion.ffs.util;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public final class BlockingQueue
  implements FFSConst
{
  private LinkedList jdField_new = new LinkedList();
  private boolean jdField_do = false;
  private boolean jdField_for = false;
  private int jdField_int = 0;
  private int jdField_if = 0;
  
  public BlockingQueue() {}
  
  public BlockingQueue(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public final synchronized void add(Object paramObject)
    throws BlockingQueue.c, BlockingQueue.d
  {
    if ((this.jdField_do) || (this.jdField_for)) {
      throw new c(null);
    }
    if ((this.jdField_if != 0) && (this.jdField_new.size() >= this.jdField_if)) {
      throw new d(null);
    }
    this.jdField_new.addLast(paramObject);
    notify();
  }
  
  public final synchronized void add(Object paramObject, long paramLong, Runnable paramRunnable)
  {
    add(paramObject);
    new Thread()
    {
      private final long val$timeout;
      private final Object val$newElement;
      private final Runnable val$onRemoval;
      
      public void run()
      {
        try
        {
          sleep(this.val$timeout);
          boolean bool;
          synchronized (BlockingQueue.this)
          {
            bool = BlockingQueue.this.jdField_new.remove(this.val$newElement);
            if ((bool) && (BlockingQueue.this.jdField_new.size() == 0) && (BlockingQueue.this.jdField_for)) {
              BlockingQueue.this.close();
            }
          }
          if ((bool) && (this.val$onRemoval != null)) {
            this.val$onRemoval.run();
          }
        }
        catch (InterruptedException localInterruptedException) {}
      }
    }.start();
  }
  
  public final synchronized void add(Object paramObject, long paramLong)
  {
    add(paramObject, paramLong, null);
  }
  
  public final synchronized void addFinalItem(Object paramObject)
    throws BlockingQueue.c
  {
    add(paramObject);
    this.jdField_for = true;
  }
  
  public final synchronized Object get(long paramLong)
    throws InterruptedException, BlockingQueue.c, FFSSemaphore.b
  {
    long l = paramLong == 9223372036854775807L ? 9223372036854775807L : System.currentTimeMillis() + paramLong;
    if (this.jdField_do) {
      throw new c(null);
    }
    try
    {
      if (this.jdField_new.size() <= 0)
      {
        this.jdField_int += 1;
        while (this.jdField_new.size() <= 0)
        {
          wait(paramLong);
          if (System.currentTimeMillis() > l)
          {
            this.jdField_int -= 1;
            throw new FFSSemaphore.b("Timed out waiting to get from BlockingQueue");
          }
          if (this.jdField_do)
          {
            this.jdField_int -= 1;
            throw new c(null);
          }
        }
        this.jdField_int -= 1;
      }
      Object localObject = this.jdField_new.removeFirst();
      if ((this.jdField_new.size() == 0) && (this.jdField_for)) {
        close();
      }
      return localObject;
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
      throw new Error("Internal error (com.ffusion.ffs.util.BlockingQueue)");
    }
  }
  
  public final synchronized Object get()
    throws InterruptedException, BlockingQueue.c, FFSSemaphore.b
  {
    return get(9223372036854775807L);
  }
  
  public final boolean isEmpty()
  {
    return this.jdField_new.size() <= 0;
  }
  
  public final int waitingThreads()
  {
    return this.jdField_int;
  }
  
  public synchronized void close()
  {
    this.jdField_do = true;
    this.jdField_new = null;
    notifyAll();
  }
  
  public static final class a
  {
    private static BlockingQueue a = new BlockingQueue();
    private boolean jdField_if = false;
    
    public static void a(String[] paramArrayOfString)
      throws InterruptedException
    {
      new a();
    }
    
    public a()
      throws InterruptedException
    {
      a.add("Enqueue this String", 2000L, new BlockingQueue.2(this));
      Thread.currentThread();
      Thread.sleep(2500L);
      if (!this.jdField_if) {
        System.out.println("Enqueue timeout failed.");
      }
      BlockingQueue.3 local3 = new BlockingQueue.3(this);
      BlockingQueue.4 local4 = new BlockingQueue.4(this);
      local4.start();
      local3.start();
    }
  }
  
  public class d
    extends BlockingQueue.b
  {
    private d()
    {
      super("Attempt to add item to full BlockingQueue.");
    }
    
    d(BlockingQueue.1 param1)
    {
      this();
    }
  }
  
  public class c
    extends BlockingQueue.b
  {
    private c()
    {
      super("Tried to access closed BlockingQueue");
    }
    
    c(BlockingQueue.1 param1)
    {
      this();
    }
  }
  
  public class b
    extends RuntimeException
  {
    public b(String paramString)
    {
      super();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.BlockingQueue
 * JD-Core Version:    0.7.0.1
 */