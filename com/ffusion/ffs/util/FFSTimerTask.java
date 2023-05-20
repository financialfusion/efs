package com.ffusion.ffs.util;

public abstract class FFSTimerTask
  implements Runnable
{
  final Object jdField_int = new Object();
  int jdField_try = 0;
  static final int a = 0;
  static final int jdField_new = 1;
  static final int jdField_do = 2;
  static final int jdField_if = 3;
  long jdField_for;
  long jdField_byte = 0L;
  
  public abstract void run();
  
  public boolean cancel()
  {
    synchronized (this.jdField_int)
    {
      boolean bool = this.jdField_try == 1;
      this.jdField_try = 3;
      return bool;
    }
  }
  
  public long scheduledExecutionTime()
  {
    synchronized (this.jdField_int)
    {
      return this.jdField_byte < 0L ? this.jdField_for + this.jdField_byte : this.jdField_for - this.jdField_byte;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSTimerTask
 * JD-Core Version:    0.7.0.1
 */