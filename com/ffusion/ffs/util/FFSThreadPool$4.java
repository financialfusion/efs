package com.ffusion.ffs.util;

import java.io.PrintStream;

class FFSThreadPool$4
  implements FFSCommand
{
  private final FFSThreadPool.c this$0;
  
  FFSThreadPool$4(FFSThreadPool.c paramc)
  {
    this.this$0 = paramc;
  }
  
  public void execute(Object paramObject)
  {
    System.out.println("Starting " + paramObject);
    try
    {
      Thread.currentThread();
      Thread.sleep(250L);
    }
    catch (InterruptedException localInterruptedException) {}
    System.out.println("Stoping " + paramObject);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSThreadPool.4
 * JD-Core Version:    0.7.0.1
 */