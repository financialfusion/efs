package com.ffusion.alert.engine;

import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.shared.AELog;
import com.ffusion.alert.shared.AEUtils;
import com.ffusion.alert.shared.ISchedulable;
import com.ffusion.alert.shared.IUnaryPredicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class Scheduler
  implements Runnable, EngineThreadConstants
{
  private TreeSet c3 = new TreeSet();
  private Thread c4 = new Thread(this, "ScheduleDispatcher");
  private IDispatcher c1;
  private int c2;
  
  public Scheduler(IDispatcher paramIDispatcher)
    throws Exception
  {
    a(paramIDispatcher);
  }
  
  public synchronized void a(IDispatcher paramIDispatcher)
    throws NullPointerException
  {
    if (paramIDispatcher == null) {
      throw new NullPointerException();
    }
    this.c1 = paramIDispatcher;
  }
  
  public synchronized void jdMethod_for(boolean paramBoolean)
  {
    if (this.c2 != 0) {
      return;
    }
    this.c2 = (paramBoolean ? 2 : 1);
    this.c4.start();
  }
  
  public Object[] an()
  {
    synchronized (this)
    {
      if ((this.c2 != 1) && (this.c2 != 2)) {
        return null;
      }
      this.c2 = 3;
    }
    this.c4.interrupt();
    try
    {
      this.c4.join(5000L);
      AELog.a("SchedulerEngine.stop() - thread has joined", 1);
    }
    catch (InterruptedException localInterruptedException) {}
    return this.c3.toArray();
  }
  
  public synchronized void ak()
  {
    if (this.c2 != 1) {
      return;
    }
    this.c2 = 2;
    this.c4.interrupt();
  }
  
  public synchronized void al()
  {
    if (this.c2 != 2) {
      return;
    }
    this.c2 = 1;
    this.c4.interrupt();
  }
  
  public void a(ISchedulable paramISchedulable)
  {
    long l = paramISchedulable.getNextRaised();
    if (AEUtils.a(l, 0L, System.currentTimeMillis()))
    {
      jdMethod_if(paramISchedulable);
      return;
    }
    synchronized (this.c3)
    {
      this.c3.add(paramISchedulable);
      this.c3.notify();
    }
  }
  
  public ArrayList a(IUnaryPredicate paramIUnaryPredicate)
  {
    synchronized (this.c3)
    {
      ArrayList localArrayList = new ArrayList(this.c3.size());
      Iterator localIterator = this.c3.iterator();
      while (localIterator.hasNext())
      {
        Object localObject1 = localIterator.next();
        if (paramIUnaryPredicate.a(localObject1))
        {
          localArrayList.add(localObject1);
          localIterator.remove();
          AELog.a("Scheduler.removeAll() - removing ", localObject1.toString(), 1);
        }
      }
      localArrayList.trimToSize();
      return localArrayList;
    }
  }
  
  public boolean jdMethod_do(IUnaryPredicate paramIUnaryPredicate)
  {
    synchronized (this.c3)
    {
      Iterator localIterator = this.c3.iterator();
      while (localIterator.hasNext())
      {
        Object localObject1 = localIterator.next();
        if (paramIUnaryPredicate.a(localObject1)) {
          return true;
        }
      }
      return false;
    }
  }
  
  public ArrayList jdMethod_if(IUnaryPredicate paramIUnaryPredicate)
  {
    synchronized (this.c3)
    {
      ArrayList localArrayList = new ArrayList(this.c3.size());
      Iterator localIterator = this.c3.iterator();
      while (localIterator.hasNext())
      {
        Object localObject1 = localIterator.next();
        if (paramIUnaryPredicate.a(localObject1)) {
          localArrayList.add(localObject1);
        }
      }
      localArrayList.trimToSize();
      return localArrayList;
    }
  }
  
  private void jdMethod_if(ISchedulable paramISchedulable)
  {
    this.c1.a((IAEAlertInstance)paramISchedulable);
  }
  
  private void am()
    throws InterruptedException
  {
    synchronized (this.c3)
    {
      if (!this.c3.isEmpty())
      {
        AELog.a("Scheduler.run() -  Queue not empty", 1);
        ISchedulable localISchedulable = (ISchedulable)this.c3.first();
        long l1 = localISchedulable.getNextRaised();
        long l2 = System.currentTimeMillis();
        if ((l1 <= l2) || (AEUtils.jdMethod_if(l1, l2, 50L)))
        {
          AELog.a("Scheduler.run() -  raising @ ", l2, true, 1);
          Iterator localIterator = this.c3.iterator();
          localIterator.next();
          localIterator.remove();
          jdMethod_if(localISchedulable);
        }
        else
        {
          long l3 = l1 - l2;
          AELog.a("Scheduler.run() -  Waiting (ms) ", l3, false, 1);
          this.c3.wait(l3);
        }
      }
      else
      {
        AELog.a("Scheduler.run() -  Waiting on empty queue", 1);
        this.c3.wait();
      }
    }
  }
  
  public void run()
  {
    for (;;)
    {
      if (this.c2 == 1) {
        try
        {
          am();
        }
        catch (InterruptedException localInterruptedException1)
        {
          AELog.a("Scheduler.checkQueue() - Interrupted!", 1);
        }
      }
    }
    if (this.c2 == 3)
    {
      AELog.a("Scheduler.run() - done - returning!!", 1);
      return;
    }
    while (this.c2 == 2) {
      try
      {
        synchronized (this)
        {
          AELog.a("Scheduler.run() - about to suspend", 1);
          wait();
        }
      }
      catch (InterruptedException localInterruptedException2)
      {
        AELog.a("Scheduler.run() - wake up from suspend", 1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.Scheduler
 * JD-Core Version:    0.7.0.1
 */