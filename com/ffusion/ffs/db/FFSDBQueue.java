package com.ffusion.ffs.db;

import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.LinkedList;
import java.util.ListIterator;

public class FFSDBQueue
  implements FFSConst
{
  LinkedList X = new LinkedList();
  
  public synchronized void add(Object paramObject)
  {
    this.X.add(paramObject);
    notify();
  }
  
  public synchronized Object get()
  {
    while (this.X.size() == 0)
    {
      FFSDebug.log("Waiting for a connection to be released....", 6);
      try
      {
        wait(1000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
    try
    {
      return this.X.removeFirst();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException.toString(), 0);
      localException.printStackTrace();
    }
    return null;
  }
  
  public synchronized Object get(long paramLong)
  {
    while (this.X.size() == 0)
    {
      try
      {
        if (paramLong > 0L) {
          wait(paramLong);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      if (this.X.size() == 0) {
        return null;
      }
    }
    try
    {
      return this.X.removeFirst();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException.toString(), 0);
      localException.printStackTrace();
    }
    return null;
  }
  
  public synchronized boolean peek(Object paramObject)
  {
    return this.X.contains(paramObject);
  }
  
  public synchronized void clear()
  {
    this.X.clear();
  }
  
  public synchronized Object peek(int paramInt)
  {
    try
    {
      return this.X.remove(paramInt);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    return null;
  }
  
  public synchronized boolean isEmpty()
  {
    return this.X.size() == 0;
  }
  
  public synchronized ListIterator elements()
  {
    return this.X.listIterator(0);
  }
  
  public synchronized ListIterator elementsFrom(int paramInt)
  {
    return this.X.listIterator(paramInt);
  }
  
  public synchronized int size()
  {
    return this.X.size();
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.db.FFSDBQueue
 * JD-Core Version:    0.7.0.1
 */