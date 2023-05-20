package com.ffusion.ffs.util;

import java.io.PrintStream;

class FFSThreadPool$3
  implements Runnable
{
  private final String val$id;
  private final FFSThreadPool.c this$0;
  
  FFSThreadPool$3(FFSThreadPool.c paramc, String paramString)
  {
    this.this$0 = paramc;
    this.val$id = paramString;
  }
  
  public void run()
  {
    System.out.println("Starting " + this.val$id);
    try
    {
      Thread.currentThread();
      Thread.sleep(250L);
    }
    catch (InterruptedException localInterruptedException) {}
    System.out.println("Stoping " + this.val$id);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSThreadPool.3
 * JD-Core Version:    0.7.0.1
 */