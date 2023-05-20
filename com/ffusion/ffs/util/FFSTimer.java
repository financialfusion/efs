package com.ffusion.ffs.util;

import java.util.Date;

public class FFSTimer
{
  private TaskQueue jdField_if = new TaskQueue();
  private TimerThread a = new TimerThread(this.jdField_if);
  private Object jdField_do = new Object()
  {
    protected void finalize()
      throws Throwable
    {
      synchronized (FFSTimer.this.jdField_if)
      {
        FFSTimer.this.a.jdField_if = false;
        FFSTimer.this.jdField_if.notify();
      }
    }
  };
  
  public FFSTimer()
  {
    this.a.start();
  }
  
  public FFSTimer(boolean paramBoolean)
  {
    this.a.setDaemon(paramBoolean);
    this.a.start();
  }
  
  public void schedule(FFSTimerTask paramFFSTimerTask, long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Negative delay.");
    }
    a(paramFFSTimerTask, System.currentTimeMillis() + paramLong, 0L);
  }
  
  public void schedule(FFSTimerTask paramFFSTimerTask, Date paramDate)
  {
    a(paramFFSTimerTask, paramDate.getTime(), 0L);
  }
  
  public void schedule(FFSTimerTask paramFFSTimerTask, long paramLong1, long paramLong2)
  {
    if (paramLong1 < 0L) {
      throw new IllegalArgumentException("Negative delay.");
    }
    if (paramLong2 <= 0L) {
      throw new IllegalArgumentException("Non-positive period.");
    }
    a(paramFFSTimerTask, System.currentTimeMillis() + paramLong1, -paramLong2);
  }
  
  public void schedule(FFSTimerTask paramFFSTimerTask, Date paramDate, long paramLong)
  {
    if (paramLong <= 0L) {
      throw new IllegalArgumentException("Non-positive period.");
    }
    a(paramFFSTimerTask, paramDate.getTime(), -paramLong);
  }
  
  public void scheduleAtFixedRate(FFSTimerTask paramFFSTimerTask, long paramLong1, long paramLong2)
  {
    if (paramLong1 < 0L) {
      throw new IllegalArgumentException("Negative delay.");
    }
    if (paramLong2 <= 0L) {
      throw new IllegalArgumentException("Non-positive period.");
    }
    a(paramFFSTimerTask, System.currentTimeMillis() + paramLong1, paramLong2);
  }
  
  public void scheduleAtFixedRate(FFSTimerTask paramFFSTimerTask, Date paramDate, long paramLong)
  {
    if (paramLong <= 0L) {
      throw new IllegalArgumentException("Non-positive period.");
    }
    a(paramFFSTimerTask, paramDate.getTime(), paramLong);
  }
  
  private void a(FFSTimerTask paramFFSTimerTask, long paramLong1, long paramLong2)
  {
    if (paramLong1 < 0L) {
      throw new IllegalArgumentException("Illegal execution time.");
    }
    synchronized (this.jdField_if)
    {
      if (!this.a.jdField_if) {
        throw new IllegalStateException("FFSTimer already cancelled.");
      }
      synchronized (paramFFSTimerTask.jdField_int)
      {
        if (paramFFSTimerTask.jdField_try != 0) {
          throw new IllegalStateException("Task already scheduled or cancelled");
        }
        paramFFSTimerTask.jdField_for = paramLong1;
        paramFFSTimerTask.jdField_byte = paramLong2;
        paramFFSTimerTask.jdField_try = 1;
      }
      this.jdField_if.a(paramFFSTimerTask);
      if (this.jdField_if.jdField_do() == paramFFSTimerTask) {
        this.jdField_if.notify();
      }
    }
  }
  
  public void cancel()
  {
    synchronized (this.jdField_if)
    {
      this.a.jdField_if = false;
      this.jdField_if.a();
      this.jdField_if.notify();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSTimer
 * JD-Core Version:    0.7.0.1
 */