package com.ffusion.alert.engine;

import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import com.ffusion.alert.shared.AELog;

public class DeliveryWorker
  extends Thread
  implements IAEErrConstants, IAEStatusConstants
{
  private IAEAlertInstance dm;
  private int dq;
  private ChannelReceiver dn;
  private IAEChannel dp;
  
  public DeliveryWorker(ChannelReceiver paramChannelReceiver, int paramInt1, int paramInt2)
  {
    super("DeliveryWorker " + paramInt1 + "," + paramInt2);
    this.dn = paramChannelReceiver;
    this.dp = paramChannelReceiver.av();
  }
  
  public synchronized void a(DeliveryAlert paramDeliveryAlert)
  {
    this.dm = paramDeliveryAlert.a;
    this.dq = paramDeliveryAlert.jdField_if;
    notify();
  }
  
  public synchronized void run()
  {
    do
    {
      try
      {
        if (this.dm == null) {
          wait();
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        as();
        return;
      }
      if (this.dm != null)
      {
        int i;
        try
        {
          i = this.dp.processAlert(this.dm, this.dm.getDeliveryInfo()[this.dq].getDeliveryProperties());
          if (i > 0) {
            i += 2000;
          }
        }
        catch (Throwable localThrowable)
        {
          i = 1002;
          AELog.a(localThrowable, AEInstance.a, "ERR_PROCESS_ALERT_EXCEPTION", this.dn.at(), 0);
        }
        this.dn.ax().a(this.dm, this.dq, i);
      }
      this.dm = null;
    } while ((this.dn == null) || (this.dn.a(this)));
    as();
  }
  
  private void as()
  {
    this.dm = null;
    this.dn = null;
    this.dp = null;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.DeliveryWorker
 * JD-Core Version:    0.7.0.1
 */