package com.ffusion.alert.engine;

import com.ffusion.alert.db.AERepository;
import com.ffusion.alert.db.AlertInstance;
import com.ffusion.alert.db.DBConnection;
import com.ffusion.alert.db.DBSqlUtils;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.shared.AELog;
import com.ffusion.alert.shared.AEResourceBundle;
import com.ffusion.alert.shared.AEUtils;
import com.ffusion.alert.shared.IUnaryPredicate;
import java.sql.SQLException;
import java.util.ArrayList;

public class SchedulerEngine
  implements Runnable, EngineThreadConstants, IAEErrConstants
{
  private static final long c7 = 5000L;
  private static final long df = 60000L;
  private long c6;
  private long da;
  private long di;
  private int c9;
  private Thread dg = new Thread(this, "SchedulerEngine");
  private long dh;
  private long c5;
  private int dd;
  private int c8;
  private boolean dc;
  private boolean dj;
  private AERepository dl;
  private AEConfigManager de;
  private DBConnection dk;
  private Scheduler db;
  
  public SchedulerEngine(AERepository paramAERepository, AEConfigManager paramAEConfigManager, IDispatcher paramIDispatcher, long paramLong, int paramInt)
    throws AEException
  {
    this.dl = paramAERepository;
    this.de = paramAEConfigManager;
    try
    {
      this.db = new Scheduler(paramIDispatcher);
    }
    catch (Exception localException)
    {
      throw new AEException(1017, AEInstance.a.a("ERR_ENGINE_INIT_FAILED"), localException);
    }
    this.c8 = 0;
    this.di = paramLong;
    this.c9 = paramInt;
    long l = this.di / 1000L * 2L;
    this.dd = (l > 2147483647L ? 2147483647 : (int)l);
    if (this.dd < 1) {
      this.dd = 1;
    }
  }
  
  public boolean a(DBConnection paramDBConnection, AlertInstance paramAlertInstance, int paramInt, boolean paramBoolean)
    throws AEException
  {
    Object localObject = null;
    boolean bool = false;
    if (this.c8 != 1) {
      return false;
    }
    AELog.a("SchedulerEngine.schedule() id: ", paramAlertInstance.getId(), " seq: ", paramAlertInstance.getSequence(), 1);
    long l1 = paramAlertInstance.getNextRaised();
    if (!AEUtils.a(l1, this.da)) {
      return false;
    }
    if ((paramAlertInstance.getType() == 2) && (!paramBoolean)) {
      return true;
    }
    paramAlertInstance.o(paramAlertInstance.getSequence() + 1);
    paramAlertInstance.jdMethod_int(l1);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramAlertInstance);
    long l2 = 0L;
    if (paramAlertInstance.getType() == 2)
    {
      AEScheduleInfo localAEScheduleInfo = paramAlertInstance.getScheduleInfo();
      l2 = localAEScheduleInfo.getNextFromLast(l1);
      if (l2 == l1)
      {
        AELog.a("Failure in AEScheduleInfo.getNextFromLast() ", l2, true, 1);
        l2 = 0L;
      }
      try
      {
        while ((l2 != 0L) && (AEUtils.a(l2, this.da)))
        {
          if (localArrayList.size() >= paramInt)
          {
            bool = true;
            AELog.a("SchedulerEngine.schedule() hit max alert cap of: ", paramInt, 1);
            break;
          }
          paramAlertInstance = (AlertInstance)paramAlertInstance.clone();
          paramAlertInstance.o(paramAlertInstance.getSequence() + 1);
          paramAlertInstance.jdMethod_new(l2);
          AELog.a("SchedulerEngine.schedule() (repeating) for ", l2, true, 1);
          localArrayList.add(paramAlertInstance);
          long l3 = l2;
          l2 = localAEScheduleInfo.getNextFromLast(l3);
          if (l2 == l3)
          {
            AELog.a("Failure in AEScheduleInfo.getNextFromLast() ", l2, true, 1);
            l2 = 0L;
          }
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        localObject = localOutOfMemoryError;
        paramAlertInstance = (AlertInstance)localArrayList.get(localArrayList.size() - 1);
        long l4 = paramAlertInstance.getNextRaised();
        l2 = localAEScheduleInfo.getNextFromLast(l4);
        if (l2 == l4)
        {
          AELog.a("Failure in AEScheduleInfo.getNextFromLast() ", l2, true, 1);
          l2 = 0L;
        }
      }
    }
    paramAlertInstance.a(this.dl, paramDBConnection, l2);
    if ((paramAlertInstance.getType() == 2) && (paramBoolean)) {
      try
      {
        paramDBConnection.a0();
      }
      catch (SQLException localSQLException)
      {
        throw new AEException(1, DBSqlUtils.a(localSQLException));
      }
    }
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      this.db.a((AlertInstance)localArrayList.get(j));
    }
    if (localObject != null) {
      throw localObject;
    }
    return bool;
  }
  
  public synchronized void jdMethod_new(boolean paramBoolean)
    throws AEException
  {
    if (this.c8 != 0) {
      return;
    }
    this.c8 = (paramBoolean ? 2 : 1);
    this.dk = this.dl.aL();
    try
    {
      this.dk.jdMethod_try(false);
    }
    catch (SQLException localSQLException)
    {
      this.dl.k(this.dk);
      this.dk = null;
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
    this.db.jdMethod_for(paramBoolean);
    this.c5 = (this.c6 = System.currentTimeMillis());
    this.da = (this.c6 + this.di);
    this.dh = (this.c6 + this.di / 2L);
    this.dg.start();
  }
  
  public void jdMethod_int(boolean paramBoolean)
    throws AEException
  {
    synchronized (this)
    {
      if ((this.c8 != 1) && (this.c8 != 2)) {
        return;
      }
      this.c8 = 3;
    }
    this.dg.interrupt();
    try
    {
      this.dg.join(60000L);
      AELog.a("SchedulerEngine.stop() - thread has joined", 1);
    }
    catch (InterruptedException localInterruptedException) {}
    try
    {
      if (this.dk.aT()) {
        this.dk.jdMethod_try(true);
      } else {
        this.dk = this.dl.l(this.dk);
      }
    }
    catch (SQLException localSQLException)
    {
      this.dl.k(this.dk);
      this.dk = null;
      throw new AEException(1, DBSqlUtils.a(localSQLException));
    }
    Object[] arrayOfObject = this.db.an();
    if ((arrayOfObject != null) && (paramBoolean)) {
      a(arrayOfObject, this.dk, false);
    }
    this.dl.k(this.dk);
    this.dk = null;
  }
  
  public synchronized void ao()
  {
    if (this.c8 != 1) {
      return;
    }
    this.c8 = 2;
    this.db.ak();
    this.dg.interrupt();
  }
  
  public synchronized void aq()
  {
    if (this.c8 != 2) {
      return;
    }
    this.c8 = 1;
    this.db.al();
    this.dg.interrupt();
  }
  
  public synchronized ArrayList a(IUnaryPredicate paramIUnaryPredicate, DBConnection paramDBConnection, ArrayList paramArrayList, boolean paramBoolean)
    throws AEException
  {
    if (this.c8 == 1)
    {
      this.dj = true;
      this.dg.interrupt();
    }
    Thread localThread;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      AlertInstance localAlertInstance = (AlertInstance)paramArrayList.get(i);
      localAlertInstance.jdMethod_new(0L);
      localAlertInstance.jdMethod_if(this.dl, paramDBConnection);
    }
    ArrayList localArrayList = this.db.a(paramIUnaryPredicate);
    if ((localArrayList != null) && (localArrayList.size() > 0)) {
      a(localArrayList.toArray(), paramDBConnection, paramBoolean);
    }
    if (this.c8 == 1)
    {
      this.dj = false;
      this.dg.interrupt();
    }
    return localArrayList;
  }
  
  public boolean jdMethod_int(IUnaryPredicate paramIUnaryPredicate)
  {
    return this.db.jdMethod_do(paramIUnaryPredicate);
  }
  
  public ArrayList jdMethod_for(IUnaryPredicate paramIUnaryPredicate)
  {
    return this.db.jdMethod_if(paramIUnaryPredicate);
  }
  
  private final void a(Object[] paramArrayOfObject, DBConnection paramDBConnection, boolean paramBoolean)
    throws AEException
  {
    int i = paramArrayOfObject.length;
    if (i < 1) {
      return;
    }
    for (int j = i - 1; j >= 0; j--)
    {
      AlertInstance localAlertInstance = (AlertInstance)paramArrayOfObject[j];
      localAlertInstance.o(localAlertInstance.getSequence() - 1);
      localAlertInstance.a(this.dl, paramDBConnection, paramBoolean ? 0L : localAlertInstance.getNextRaised());
    }
  }
  
  public final synchronized void ar()
  {
    if (this.c8 == 1)
    {
      AELog.a("SchedulerEngine.wakupPollThread()", 1);
      this.dg.interrupt();
    }
  }
  
  private final void ap()
    throws InterruptedException
  {
    boolean bool = false;
    synchronized (this.dg)
    {
      if (this.dj) {
        try
        {
          this.dg.wait();
        }
        catch (InterruptedException localInterruptedException1) {}
      }
      if (!this.dc)
      {
        AELog.a("SchedulerEngine.scheduleAll() - start = ", this.c6, true, " End = ", this.da, true, 1);
        try
        {
          if (Thread.interrupted()) {
            return;
          }
          if (!this.dk.aT())
          {
            this.dk = this.dl.l(this.dk);
            this.dk.jdMethod_try(false);
          }
          int i = this.c9;
          ArrayList localArrayList = null;
          for (;;)
          {
            localArrayList = AlertInstance.a(this.dk, this.da, i);
            this.dk.a0();
            for (int j = 0; j < localArrayList.size(); j++)
            {
              if (Thread.interrupted()) {
                return;
              }
              AlertInstance localAlertInstance = (AlertInstance)localArrayList.get(j);
              this.de.a(localAlertInstance);
              bool |= a(this.dk, localAlertInstance, this.dd, true);
              if (localAlertInstance.getType() != 2) {
                this.dk.a0();
              }
            }
            if (localArrayList.size() < i) {
              break;
            }
          }
        }
        catch (SQLException localSQLException1)
        {
          try
          {
            this.dk.a1();
          }
          catch (SQLException localSQLException2) {}
          bool = true;
          AELog.a(localSQLException1, AEInstance.a, "ERR_LISTALERTS_DUE_FAILED", 1);
        }
        catch (AEException localAEException)
        {
          try
          {
            this.dk.a1();
          }
          catch (SQLException localSQLException3) {}
          bool = true;
          AELog.a(localAEException, AEInstance.a, "ERR_LISTALERTS_DUE_FAILED", 1);
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          bool = true;
          AELog.a("SchedulerEngine.scheduleAll() - Out of Memory Error ", 1);
        }
      }
      else
      {
        this.dc = false;
      }
      long l1 = System.currentTimeMillis();
      long l2 = this.dh - l1;
      if (l2 > 0L)
      {
        if (bool) {
          if (l2 > 5000L)
          {
            l2 = 5000L;
            AELog.a("SchedulerEngine.scheduleAll() - Using short sleep interval ", 1);
          }
          else
          {
            bool = false;
          }
        }
        try
        {
          AELog.a("SchedulerEngine.scheduleAll() - sleeping ", l2, false, 1);
          this.dg.wait(l2);
        }
        catch (InterruptedException localInterruptedException2)
        {
          throw localInterruptedException2;
        }
      }
      if (!bool)
      {
        this.c6 = this.da;
        this.da = (this.c6 + this.di);
        this.dh += this.di;
      }
    }
  }
  
  public void run()
  {
    AELog.a("SchedulerEngine.run() - Start up, interval = ", this.di, false, 1);
    for (;;)
    {
      if (this.c8 == 1)
      {
        try
        {
          ap();
        }
        catch (InterruptedException localInterruptedException1)
        {
          AELog.a("SchedulerEngine.scheduleAll() - Interrupted", 1);
        }
      }
      else
      {
        if (this.c8 == 3)
        {
          AELog.a("SchedulerEngine.run() - done - returning", 1);
          return;
        }
        while (this.c8 == 2) {
          try
          {
            synchronized (this)
            {
              AELog.a("SchedulerEngine.run() - about to suspend", 1);
              wait();
            }
          }
          catch (InterruptedException localInterruptedException2)
          {
            AELog.a("SchedulerEngine.run() - wake up from suspend", 1);
          }
        }
      }
    }
  }
  
  void jdMethod_for(long paramLong)
  {
    this.di = paramLong;
    long l = this.di / 1000L * 2L;
    this.dd = (l > 2147483647L ? 2147483647 : (int)l);
    if (this.dd < 1) {
      this.dd = 1;
    }
  }
  
  void g(int paramInt)
  {
    this.c9 = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.SchedulerEngine
 * JD-Core Version:    0.7.0.1
 */