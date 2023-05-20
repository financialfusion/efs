package com.ffusion.alert.engine;

import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ChannelReceiver
  implements Runnable, IAEErrConstants, IAEStatusConstants
{
  private static final int dy = 5;
  private static final int dr = 50;
  private int dt;
  private String dA;
  private Thread dv;
  private IAEChannel dC;
  private boolean dz;
  private ArrayList dx = new ArrayList();
  private HashSet dw = new HashSet();
  private int du;
  private int ds;
  private int dE;
  private IStatusNotifier dD;
  private IAlertMessageQueue dB = new AlertMessageQueue();
  
  public ChannelReceiver(IStatusNotifier paramIStatusNotifier, int paramInt, String paramString)
  {
    this.dt = paramInt;
    this.dA = paramString;
    this.du = 5;
    this.ds = 50;
    a(paramIStatusNotifier);
  }
  
  public ChannelReceiver(int paramInt1, int paramInt2, IAEChannel paramIAEChannel, IStatusNotifier paramIStatusNotifier, int paramInt3, String paramString)
  {
    this.dt = paramInt3;
    this.dA = paramString;
    a(paramIStatusNotifier);
    this.du = (paramInt1 < 1 ? 1 : paramInt1);
    i(paramInt2);
    this.dC = paramIAEChannel;
    az();
  }
  
  public void a(IAEChannel paramIAEChannel)
  {
    this.dC = paramIAEChannel;
  }
  
  public String at()
  {
    return this.dA;
  }
  
  public void h(int paramInt)
  {
    this.du = (paramInt < 1 ? 1 : paramInt);
  }
  
  public final int aw()
  {
    return this.ds;
  }
  
  public final void i(int paramInt)
  {
    int i = paramInt > this.ds ? 1 : 0;
    this.ds = (paramInt < 1 ? 1 : paramInt);
    if (i != 0) {
      synchronized (this.dx)
      {
        this.dx.notify();
      }
    }
  }
  
  public final void a(IStatusNotifier paramIStatusNotifier)
  {
    this.dD = paramIStatusNotifier;
  }
  
  public final IStatusNotifier ax()
  {
    return this.dD;
  }
  
  public final void a(IAlertMessageQueue paramIAlertMessageQueue)
  {
    this.dB = paramIAlertMessageQueue;
  }
  
  public final IAlertMessageQueue ay()
  {
    return this.dB;
  }
  
  public boolean jdMethod_do(DeliveryAlert paramDeliveryAlert)
  {
    try
    {
      this.dB.a(paramDeliveryAlert);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  public void az()
  {
    for (int i = 0; i < this.du; i++)
    {
      DeliveryWorker localDeliveryWorker = new DeliveryWorker(this, this.dt, i);
      localDeliveryWorker.start();
      this.dx.add(localDeliveryWorker);
      this.dE += 1;
    }
    this.dv = new Thread(this, "ChannelReceiver " + this.dt);
    this.dv.start();
  }
  
  public void run()
  {
    for (;;)
    {
      if (this.dz)
      {
        aA();
        return;
      }
      DeliveryAlert localDeliveryAlert = null;
      try
      {
        localDeliveryAlert = (DeliveryAlert)this.dB.jdMethod_do();
      }
      catch (InterruptedException localInterruptedException) {}
      if (this.dz)
      {
        aA();
        return;
      }
      jdMethod_if(localDeliveryAlert);
    }
  }
  
  public void jdMethod_if(DeliveryAlert paramDeliveryAlert)
  {
    DeliveryWorker localDeliveryWorker = null;
    synchronized (this.dx)
    {
      while ((!this.dz) && (localDeliveryWorker == null))
      {
        int i = this.dx.size();
        if (i > 0)
        {
          localDeliveryWorker = (DeliveryWorker)this.dx.remove(i - 1);
        }
        else if ((i == 0) && (this.dE < this.ds))
        {
          localDeliveryWorker = new DeliveryWorker(this, this.dt, this.dE);
          localDeliveryWorker.start();
          this.dE += 1;
        }
        else
        {
          try
          {
            this.dx.wait();
          }
          catch (InterruptedException localInterruptedException) {}
        }
      }
    }
    if (localDeliveryWorker == null) {
      return;
    }
    synchronized (this.dw)
    {
      this.dw.add(localDeliveryWorker);
    }
    localDeliveryWorker.a(paramDeliveryAlert);
  }
  
  protected IAEChannel av()
  {
    return this.dC;
  }
  
  public boolean a(DeliveryWorker paramDeliveryWorker)
  {
    synchronized (this.dx)
    {
      synchronized (this.dw)
      {
        this.dw.remove(paramDeliveryWorker);
        this.dw.notify();
      }
      if ((this.dz) || (this.dE > this.ds))
      {
        this.dE -= 1;
        return false;
      }
      this.dx.add(paramDeliveryWorker);
      this.dx.notify();
      return true;
    }
  }
  
  public void au()
  {
    this.dz = true;
    this.dv.interrupt();
  }
  
  private void aA()
  {
    synchronized (this.dx)
    {
      int i = this.dx.size();
      Object localObject1;
      for (int j = 0; j < i; j++)
      {
        localObject1 = (DeliveryWorker)this.dx.get(j);
        ((DeliveryWorker)localObject1).interrupt();
      }
      synchronized (this.dw)
      {
        localObject1 = this.dw.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          DeliveryWorker localDeliveryWorker = (DeliveryWorker)((Iterator)localObject1).next();
          localDeliveryWorker.interrupt();
        }
      }
    }
    synchronized (this.dw)
    {
      while (this.dw.size() > 0) {
        try
        {
          this.dw.wait();
        }
        catch (InterruptedException localInterruptedException1) {}
      }
    }
    while (!this.dB.a())
    {
      ??? = null;
      try
      {
        ??? = (DeliveryAlert)this.dB.jdMethod_do();
      }
      catch (InterruptedException localInterruptedException2)
      {
        break;
      }
      this.dD.a(((DeliveryAlert)???).a, ((DeliveryAlert)???).jdField_if, 1000);
    }
    try
    {
      this.dC.stop();
    }
    catch (Throwable localThrowable) {}
    this.dC = null;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.ChannelReceiver
 * JD-Core Version:    0.7.0.1
 */