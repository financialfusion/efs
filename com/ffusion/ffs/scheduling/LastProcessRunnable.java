package com.ffusion.ffs.scheduling;

import com.ffusion.ffs.util.FFSDebug;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class LastProcessRunnable
  implements Runnable
{
  private Object jdField_do;
  private Method a;
  private int jdField_if;
  
  public LastProcessRunnable(Object paramObject, Method paramMethod, int paramInt)
  {
    this.jdField_do = paramObject;
    this.a = paramMethod;
    this.jdField_if = paramInt;
  }
  
  public synchronized void done()
  {
    this.jdField_if -= 1;
    notify();
  }
  
  public synchronized void run()
  {
    while (this.jdField_if != 0) {
      try
      {
        wait();
      }
      catch (InterruptedException localInterruptedException)
      {
        FFSDebug.log("### LastProcessRunnable.run: InterruptedException _processCount= " + this.jdField_if);
      }
    }
    try
    {
      FFSDebug.log("### LastProcessRunnable.run: _processCount= " + this.jdField_if + " " + Calendar.getInstance().getTime().toString(), 6);
      this.a.invoke(this.jdField_do, new Object[] { "", "", new Integer(0), new Integer(2), null });
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** LastProcessRunnable.run failed :");
      FFSDebug.console("*** LastProcessRunnable.run failed :" + localException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.LastProcessRunnable
 * JD-Core Version:    0.7.0.1
 */