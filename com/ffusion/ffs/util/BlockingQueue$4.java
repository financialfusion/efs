package com.ffusion.ffs.util;

import java.io.PrintStream;

class BlockingQueue$4
  extends Thread
{
  private final BlockingQueue.a this$0;
  
  BlockingQueue$4(BlockingQueue.a parama)
  {
    this.this$0 = parama;
  }
  
  public void run()
  {
    try
    {
      String str;
      while ((str = (String)BlockingQueue.a.access$500().get()) != null) {
        System.out.println(str);
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      System.out.println("Unexpected InterruptedException");
    }
    int i = 0;
    try
    {
      BlockingQueue.a.access$500().add(null);
    }
    catch (BlockingQueue.c localc)
    {
      i = 1;
    }
    if (i != 0) {
      System.out.println("Close handled");
    } else {
      System.out.println("Error: Close failed");
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.BlockingQueue.4
 * JD-Core Version:    0.7.0.1
 */