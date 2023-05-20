package com.ffusion.ffs.util;

import java.io.PrintStream;

class BlockingQueue$2
  implements Runnable
{
  private final BlockingQueue.a this$0;
  
  BlockingQueue$2(BlockingQueue.a parama)
  {
    this.this$0 = parama;
  }
  
  public void run()
  {
    System.out.println("Enqueue timeout worked.");
    BlockingQueue.a.access$402(this.this$0, true);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.BlockingQueue.2
 * JD-Core Version:    0.7.0.1
 */