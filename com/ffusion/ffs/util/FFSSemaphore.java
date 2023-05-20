package com.ffusion.ffs.util;

abstract interface FFSSemaphore
{
  public static final long FOREVER = 9223372036854775807L;
  
  public abstract int id();
  
  public abstract boolean acquire(long paramLong)
    throws InterruptedException, FFSSemaphore.b;
  
  public abstract void release();
  
  public static final class a
    extends RuntimeException
  {
    public a()
    {
      super();
    }
  }
  
  public static final class b
    extends RuntimeException
  {
    public b()
    {
      super();
    }
    
    public b(String paramString)
    {
      super();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSSemaphore
 * JD-Core Version:    0.7.0.1
 */