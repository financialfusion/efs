package com.ffusion.ffs.bpw.purge;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.PaymentBatch;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.Purge;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.Semaphore;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.util.Calendar;
import java.util.Date;

public class PurgeRunnable
  implements Runnable, FFSConst, DBConsts, BPWResource
{
  private FFSConnectionHolder da;
  private Calendar dc;
  private int dh;
  private int df;
  private int dg;
  private int c9 = 14400;
  private boolean db = true;
  private String dd = null;
  public Semaphore _semaphore = null;
  private int de = 0;
  public boolean started = false;
  
  public PurgeRunnable(Calendar paramCalendar, int paramInt1, int paramInt2, int paramInt3)
  {
    this.dc = paramCalendar;
    this.dh = paramInt1;
    this.df = paramInt2;
    this.dg = paramInt3;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.dd = localPropertyConfig.otherProperties.getProperty("bpw.transactions.cleanup.data.retention.enabled", "N");
    this._semaphore = new Semaphore();
    this.de = ACHAdapterUtil.getPropertyInt("BatchSize", 1000);
  }
  
  public void run()
  {
    this.started = true;
    if (this.dd.trim().equalsIgnoreCase("Y"))
    {
      try
      {
        d();
      }
      catch (Throwable localThrowable1)
      {
        FFSDebug.log("PurgeRunnable:" + FFSDebug.stackTrace(localThrowable1), 0);
      }
      try
      {
        for (;;)
        {
          this._semaphore.wait_for();
          for (;;)
          {
            if (!this.db) {
              return;
            }
            this._semaphore.reset();
            int i = d();
            if (i == 0) {
              break;
            }
          }
        }
      }
      catch (Throwable localThrowable2)
      {
        if ((localThrowable2 instanceof InterruptedException)) {
          return;
        }
        FFSDebug.log("PurgeRunnable:" + FFSDebug.stackTrace(localThrowable2));
      }
    }
    while ((this.db) && (!Thread.interrupted()))
    {
      long l = e();
      Calendar localCalendar = Calendar.getInstance();
      FFSDebug.log("PurgeRunnable:now=" + localCalendar.getTime().toString(), 6);
      FFSDebug.log("PurgeRunnable:_nextTime=" + this.dc.getTime().toString() + ",sleep ms=" + l, 6);
      try
      {
        this._semaphore.wait_for(l);
        if (this.db)
        {
          FFSDebug.log("PurgeRunnable: Purge process started...");
          c();
          FFSDebug.log("PurgeRunnable: Purge process done.");
        }
        else
        {
          break;
        }
      }
      catch (Throwable localThrowable3)
      {
        if (!(localThrowable3 instanceof InterruptedException)) {
          FFSDebug.log("PurgeRunnable:" + FFSDebug.stackTrace(localThrowable3));
        }
      }
      this.dc.add(12, this.c9);
    }
    this.started = false;
  }
  
  private void c()
    throws Exception
  {
    this.da = new FFSConnectionHolder();
    this.da.conn = DBUtil.getValidConnection();
    if (this.da.conn == null)
    {
      FFSDebug.log("PurgeRunnable: Unable to get a database connection.");
      return;
    }
    try
    {
      jdMethod_do(this.da);
      jdMethod_int(this.da);
    }
    finally
    {
      DBUtil.freeConnection(this.da.conn);
    }
  }
  
  public synchronized void stop()
  {
    this.db = false;
    this._semaphore.wake_up();
    notifyAll();
  }
  
  private long e()
  {
    long l1 = System.currentTimeMillis();
    long l2 = this.dc.getTime().getTime() - l1;
    return l2;
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder)
  {
    try
    {
      XferInstruction.cleanup(paramFFSConnectionHolder, null, this.dh);
      PmtInstruction.cleanup(paramFFSConnectionHolder, null, this.dh);
      RecXferInstruction.cleanup(paramFFSConnectionHolder, null, this.dh);
      RecPmtInstruction.cleanup(paramFFSConnectionHolder, null, this.dh);
      PaymentBatch.cleanup(paramFFSConnectionHolder, null);
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.log(localException.getMessage());
    }
  }
  
  private void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder)
  {
    try
    {
      EventInfoLog.delete(paramFFSConnectionHolder, this.df);
      paramFFSConnectionHolder.conn.commit();
      Trans.deleteTransID(paramFFSConnectionHolder, this.df);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.log(localException.getMessage());
    }
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder) {}
  
  private int d()
    throws FFSException
  {
    String str = "PurgeRunnable.cleanup:";
    FFSDebug.log(str, " start...", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    Object localObject2 = null;
    int i = 0;
    Object localObject3 = null;
    Object localObject4 = null;
    int j = 0;
    int k = 0;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Purge.cleanup(localFFSConnectionHolder);
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** PurgeRunnable.cleanup(): failed:" + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** PurgeRunnable.cleanup(): failed:" + localException.toString());
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      localFFSConnectionHolder.conn = null;
    }
    FFSDebug.log("PurgeRunnable.cleanup(): end.", 6);
    return k;
  }
  
  public void wake_up()
  {
    if (!this.started) {
      FFSDebug.log("*** PurgeRunnable.wake_up(): failed: It is not running!");
    }
    try
    {
      this._semaphore.wake_up();
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PurgeRunnable.wake_up(): failed:" + localException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.purge.PurgeRunnable
 * JD-Core Version:    0.7.0.1
 */