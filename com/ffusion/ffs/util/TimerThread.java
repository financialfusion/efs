package com.ffusion.ffs.util;

class TimerThread
  extends Thread
{
  boolean jdField_if = true;
  private TaskQueue a;
  
  TimerThread(TaskQueue paramTaskQueue)
  {
    this.a = paramTaskQueue;
  }
  
  public void run()
  {
    try
    {
      a();
    }
    finally
    {
      synchronized (this.a)
      {
        this.jdField_if = false;
        this.a.a();
      }
    }
  }
  
  private void a()
  {
    try
    {
      for (;;)
      {
        FFSTimerTask localFFSTimerTask;
        int i;
        synchronized (this.a)
        {
          if ((this.a.jdField_if()) && (this.jdField_if))
          {
            this.a.wait();
            continue;
          }
          if (this.a.jdField_if()) {
            break;
          }
          localFFSTimerTask = this.a.jdMethod_do();
          long l1;
          long l2;
          synchronized (localFFSTimerTask.jdField_int)
          {
            if (localFFSTimerTask.jdField_try == 3)
            {
              this.a.jdMethod_for();
              continue;
            }
            l1 = System.currentTimeMillis();
            l2 = localFFSTimerTask.jdField_for;
            if ((i = l2 <= l1 ? 1 : 0) != 0) {
              if (localFFSTimerTask.jdField_byte == 0L)
              {
                this.a.jdMethod_for();
                localFFSTimerTask.jdField_try = 2;
              }
              else
              {
                this.a.a(localFFSTimerTask.jdField_byte < 0L ? l1 - localFFSTimerTask.jdField_byte : l2 + localFFSTimerTask.jdField_byte);
              }
            }
          }
          if (i == 0) {
            this.a.wait(l2 - l1);
          }
        }
        if (i != 0) {
          localFFSTimerTask.run();
        }
      }
    }
    catch (InterruptedException localInterruptedException) {}
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.TimerThread
 * JD-Core Version:    0.7.0.1
 */