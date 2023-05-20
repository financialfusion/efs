package com.ffusion.ffs.util;

class BlockingQueue$3
  extends Thread
{
  private final BlockingQueue.a this$0;
  
  BlockingQueue$3(BlockingQueue.a parama)
  {
    this.this$0 = parama;
  }
  
  public void run()
  {
    int i = 10;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      BlockingQueue.a.access$500().add("" + i);
    }
    BlockingQueue.a.access$500().addFinalItem(null);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.BlockingQueue.3
 * JD-Core Version:    0.7.0.1
 */